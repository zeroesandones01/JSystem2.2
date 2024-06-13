package Projects.SalesandMarketing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;
import org.jdom.Attribute;
import org.jopendocument.dom.spreadsheet.Column;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import tablemodel.modelPricelistPaymentScheme;
import tablemodel.modelPricelist_complete;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.CheckBoxHeader;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncODSFileFilter;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Lookup._JLookupTable.RolloverMouseAdapter;
import Renderer.DateRenderer;
import Renderer.NumberRenderer;
import Renderer.TextRenderer;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JTagLabel;
import components._JXTextField;

public class Pricelist extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9032673356061487100L;
	static String title = "Pricelist";	

	private JPanel pnlMain;
	private JPanel pnlPricelist;
	private JPanel pnlNorth;
	private JPanel pnlLookups;
	private JPanel pnlTags;
	private JPanel pnlMonitoring;
	private JPanel pnlSouth;
	private JPanel pnlMonitoringSouth;
	private JPanel pnlMonitoringNavigation;
	private JPanel pnlSchemeMain;
	private JPanel pnlSchemeNorth;
	private JPanel pnlMonitoringNorth_b;
	private JPanel pnlNorth_b1;
	private JPanel pnlNorth_b2;
	private JPanel pnlNorth_b3;
	private JPanel pnlMainDetails;
	private JPanel pnlNorth_a;
	private JPanel pnlMainDtlNorth;
	private JPanel pnlNorthLabels;
	private JPanel pnlMainDtlLookup;
	private JPanel pnlNorth_b;
	private JPanel pnlSchemeNorthCommRate;
	private JPanel pnlSchemeNorthCommRate_a;
	private JPanel pnlSchemeNorthCommRate_b;
	private JPanel pnlSouthEast;
	private JPanel pnlMonitoringLabels_int;
	private JPanel pnlMonitoringLookups_int;	
	private JPanel pnlMonitoringLabels_ext;
	private JPanel pnlMonitoringLookups_ext;	
	private JPanel pnlMainSouth;
	private JPanel pnlMainSouth_a;	
	private JPanel pnlSchemeSouth;
	private JPanel pnlSchemeSouth_a;	
	private JPanel pnlSchemeSouth_b;
	private JPanel pnlAddNewPmtScheme;	
	private JPanel pnlAddNewPmtScheme_a;
	private JPanel pnlAddNewPmtScheme_a1;
	private JPanel pnlAddNewPmtScheme_a2;
	private JPanel pnlAddNewPmtScheme_c;

	private JTabbedPane tabMain;
	private JTabbedPane tabCenter;

	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblSelectFile;
	private JLabel lblMonitoringCompany;
	private JLabel lblMonitoringProject;
	private JLabel lblUploadedBy;
	private JLabel lblUploadedDate;
	private JLabel lblApprovedBy;
	private JLabel lblApprovedDate;
	private JLabel lblStatus;
	private JLabel lblPmtScheme;
	private JLabel lblPmtSchemeName;
	private JLabel lblCommSchInt;
	private JLabel lblCommSchExt;

	private _JTagLabel tagCompany;
	private _JTagLabel tagProject;
	private _JTagLabel tagPhase;
	private _JTagLabel tagX;
	private _JTagLabel tagUploaded;
	private _JTagLabel tagApproved;
	private _JTagLabel tagStatus;

	private _JLookup lookupPhase;
	private _JLookup lookupCompany;
	private _JLookup lookupProject;
	private _JLookup lookupPmtScheme;
	private _JLookup lookupCommSchemeInt;
	private _JLookup lookupCommSchemeExt;

	private JLabel lblTotalUnits;
	private JButton btnSelectFile;
	private JButton btnUploadSave;
	private JButton btnCancel;
	private JButton btnMonitoringSave;
	private JButton btnMonitoringPreview;
	private JButton btnMonitoringAddPmtSchme;
	private JButton btnApprove;
	private JButton btnSchemePreview;
	private JButton btnSchemeSave;
	private JButton btnAddPmtScheme;
	private JButton btnRefresh;

	private JXTextField tagSelectFile;
	private JXTextField txtPmtSchName;
	private _JXTextField txtStatus;
	private _JXTextField txtUploadedBy;
	private _JXTextField txtApprovedBy;	

	private JComboBox cmbSelectSheet;	

	private _JXFormattedTextField txtCommissionRate;
	private _JXFormattedTextField txtLowCostInt;
	private _JXFormattedTextField txtSocializedInt;
	private _JXFormattedTextField txtLowCostExt;
	private _JXFormattedTextField txtSocializedExt;	

	private JFileChooser fileChooser;

	private JScrollPane scrollMonitoringUnits;
	private JScrollPane scrollScheme;

	private _JTableMain tblMonitoringUnits;	
	private _JTableMain tblScheme;

	private modelPricelist_complete modelMonitoringUnits;
	private modelPricelistPaymentScheme modelScheme;

	private JList rowheaderMonitoringUnits;
	private JList rowheaderScheme;

	private _JDateChooser dteUploaded;
	private _JDateChooser dteApproved;

	private JPopupMenu menu;	
	private JPopupMenu menu2;

	private JMenuItem mnidelete;

	private String co_id = "";	
	private String co_name = "";	
	private String proj_id = "";
	private String proj_name = "";
	private String sub_proj_id = "";
	private String status_id = "";
	private String rec_id = null;
	private String phase = "";
	private String version = "";
	private String status_name = "";
	private String version_status = "";
	public static String company_logo;	
	int current_version_no = 0;
	private String buyer_type = "";

	protected Boolean pressedShift = false;
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Border LINE_BORDER  = BorderFactory.createLineBorder(Color.GRAY);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private JMenuItem mnideleteUnit;
	private JPanel pnlDate;
	private JLabel lblVersion;
	private JButton btnOK;
	private _JLookup lookupVersion;
	private JButton btnUploadNewVersion;
	private JComboBox cmbPriceListType;

	public Pricelist() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Pricelist(String title) {
		super(title);
		initGUI();
	}

	public Pricelist(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));


		{
			menu = new JPopupMenu("Popup");
			mnidelete = new JMenuItem("Remove Payment Scheme");
			menu.add(mnidelete);
			mnidelete.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					removePmtScheme();				
				}
			});
		}
		{
			menu2 = new JPopupMenu("Popup");
			mnideleteUnit = new JMenuItem("Remove Unit");
			menu2.add(mnideleteUnit);
			mnideleteUnit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					removeUnit();				
				}
			});
		}




		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		{
			pnlMainDetails = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlMainDetails, BorderLayout.NORTH);
			pnlMainDetails.setPreferredSize(new java.awt.Dimension(997, 149));

			{
				pnlMainDtlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMainDetails.add(pnlMainDtlNorth, BorderLayout.NORTH);
				pnlMainDtlNorth.setPreferredSize(new java.awt.Dimension(997, 147));

				{
					pnlNorth_a = new JPanel(new BorderLayout(5, 5));
					pnlMainDtlNorth.add(pnlNorth_a, BorderLayout.WEST);
					pnlNorth_a.setBorder(components.JTBorderFactory.createTitleBorder("Project Details"));
					pnlNorth_a.setPreferredSize(new java.awt.Dimension(468, 113));

					{
						pnlNorthLabels = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlNorth_a.add(pnlNorthLabels, BorderLayout.WEST);
						pnlNorthLabels.setPreferredSize(new Dimension(60, 0));

						{
							lblCompany = new JLabel("Company");
							pnlNorthLabels.add(lblCompany);
						}
						{
							lblProject = new JLabel("Project");
							pnlNorthLabels.add(lblProject);
							lblProject.setEnabled(false);
						}
						{
							lblPhase = new JLabel("Phase");
							pnlNorthLabels.add(lblPhase);
							lblPhase.setEnabled(false);
						}
						{
							lblStatus = new JLabel("Status");
							pnlNorthLabels.add(lblStatus);
							lblStatus.setEnabled(false);
						}
					}
					{
						pnlMainDtlLookup = new JPanel(new BorderLayout(3, 0));
						pnlNorth_a.add(pnlMainDtlLookup, BorderLayout.CENTER);
						{
							pnlLookups = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlMainDtlLookup.add(pnlLookups, BorderLayout.WEST);
							pnlLookups.setPreferredSize(new Dimension(45, 80));
							{
								lookupCompany = new _JLookup(null, "Company", 0);
								pnlLookups.add(lookupCompany);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											co_id = (String) data[0];
											co_name = (String) data[1];
											tagCompany.setTag(co_name);
											company_logo = (String) data[3];

											lblProject.setEnabled(true);
											lookupProject.setEnabled(true);
											tagProject.setEnabled(true);											

											lookupProject.setValue("");
											tagProject.setTag("");
											lookupPhase.setValue("");
											tagPhase.setTag("");
											txtStatus.setText("");
											tagStatus.setTag("");
											dteUploaded.setDate(null);
											dteApproved.setDate(null);
											refreshFields();
											refresh_tables();

											lblPhase.setEnabled(false);
											lookupPhase.setEnabled(false);
											tagPhase.setEnabled(false);	

											lblStatus.setEnabled(false);
											txtStatus.setEnabled(false);
											tagStatus.setEnabled(false);												

											proj_id = "";

											lookupProject.setLookupSQL(SQL_PROJECT(co_id));
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								lookupProject = new _JLookup(null, "Project", 0);
								pnlLookups.add(lookupProject);
								lookupProject.setEnabled(false);
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){

											refresh_mainFields();

											proj_id = (String) data[0];
											proj_name = (String) data[1];
											String proj_name = (String) data[1];
											tagProject.setTag(proj_name);

											lblPhase.setEnabled(true);
											lookupPhase.setEnabled(true);
											tagPhase.setEnabled(true);												

											lblStatus.setEnabled(false);
											txtStatus.setEnabled(false);
											tagStatus.setEnabled(false);	

											lookupPhase.setLookupSQL(sql_phase(proj_id));
											KEYBOARD_MANAGER.focusNextComponent();

											btnCancel.setEnabled(true);
											tabCenter.removeAll();
											lookupPhase.setValue("");
											tagPhase.setTag("");
											btnSelectFile.setEnabled(false);
										}
									}
								});
							}
							{
								lookupPhase = new _JLookup(null, "Phase", 0);
								pnlLookups.add(lookupPhase);
								lookupPhase.setEnabled(false);
								lookupPhase.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											phase = data[0].toString();
											sub_proj_id = (String) data[3];
											String description = (String) data[1];
											tagPhase.setTag(description);
											KEYBOARD_MANAGER.focusNextComponent();
											refreshFields();
											setPricelist_mainDtls();
											displayUnits();
											tabMain.setSelectedIndex(1);

											lblStatus.setEnabled(true);
											txtStatus.setEnabled(true);
											tagStatus.setEnabled(true);												

											if(status_id.equals("A")) {
												enableFieldsForEditing(true);
												//btnUploadNewVersion.setEnabled(false);
											}else{
												enableFieldsForEditing(false);
												//btnUploadNewVersion.setEnabled(true);
											}
											
											btnMonitoringAddPmtSchme.setEnabled(true);

											displayPmtSchemeList(modelScheme, rowheaderScheme);
											btnRefresh.setEnabled(true);
											tabCenter.removeAll();
											btnSelectFile.setEnabled(true);

											if (tblScheme.getRowCount()==0&&status_id.equals("A"))
											{
												txtLowCostInt.setEnabled(true);
												txtSocializedInt.setEnabled(true);
												txtLowCostExt.setEnabled(true);
												txtSocializedExt.setEnabled(true);
												btnMonitoringAddPmtSchme.setEnabled(true);	
												btnSchemeSave.setEnabled(true);
												btnSelectFile.setEnabled(true);
												btnUploadNewVersion.setEnabled(true);
											}
											else if (tblScheme.getRowCount()!=0&&status_id.equals("A"))
											{
												txtLowCostInt.setEnabled(false);
												txtSocializedInt.setEnabled(false);
												txtLowCostExt.setEnabled(false);
												txtSocializedExt.setEnabled(false);
												btnMonitoringAddPmtSchme.setEnabled(true);	
												btnSchemeSave.setEnabled(false);
												btnSelectFile.setEnabled(true);
												btnUploadNewVersion.setEnabled(true);
											}
											else if (status_id.equals("P"))
											{
												txtLowCostInt.setEnabled(false);
												txtSocializedInt.setEnabled(false);
												txtLowCostExt.setEnabled(false);
												txtSocializedExt.setEnabled(false);
												btnMonitoringAddPmtSchme.setEnabled(false);	
												btnSchemeSave.setEnabled(false);
												btnSelectFile.setEnabled(false);
												btnUploadNewVersion.setEnabled(true);
											}
										}

										lookupVersion.setLookupSQL(sql_version());
									}
								});
							}
							{
								txtStatus = new _JXTextField("");
								pnlLookups.add(txtStatus);
								txtStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
								txtStatus.setHorizontalAlignment(JXTextField.CENTER);
							}
						}
						{
							pnlTags = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlMainDtlLookup.add(pnlTags, BorderLayout.CENTER);
							{
								tagCompany = new _JTagLabel("[ ]");
								pnlTags.add(tagCompany);
							}
							{
								tagProject = new _JTagLabel("[ ]");
								pnlTags.add(tagProject);
								tagProject.setEnabled(false);
							}
							{
								tagPhase = new _JTagLabel("[ ]");
								pnlTags.add(tagPhase);
								tagPhase.setEnabled(false);
							}
							{
								JPanel pnlStatus = new JPanel(new BorderLayout(3, 3));
								pnlTags.add(pnlStatus);
								{
									tagStatus = new _JTagLabel("[ ]");
									pnlStatus.add(tagStatus, BorderLayout.CENTER);
									tagStatus.setEnabled(false);
								}
								{
									cmbPriceListType = new JComboBox(new String[] { "JSystem", "ItsReal" });
									pnlStatus.add(cmbPriceListType, BorderLayout.EAST);
									cmbPriceListType.setPreferredSize(new Dimension(150, 0));
									cmbPriceListType.setEnabled(false);
								}
							}
						}
					}						
				}

				{
					pnlNorth_b = new JPanel(new BorderLayout(5, 5));
					pnlMainDtlNorth.add(pnlNorth_b, BorderLayout.CENTER);
					pnlNorth_b.setBorder(components.JTBorderFactory.createTitleBorder("Approval Details"));
					pnlNorth_b.setPreferredSize(new Dimension(783, 113));

					{
						pnlNorth_b1 = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlNorth_b.add(pnlNorth_b1, BorderLayout.WEST);
						pnlNorth_b1.setPreferredSize(new java.awt.Dimension(89, 147));
						pnlNorth_b1.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

						{
							lblUploadedBy = new JLabel("Uploaded By");
							pnlNorth_b1.add(lblUploadedBy);
						}
						{
							lblUploadedDate = new JLabel("Uploaded Date");
							pnlNorth_b1.add(lblUploadedDate);
						}
						{
							lblApprovedBy = new JLabel("Approved By");
							pnlNorth_b1.add(lblApprovedBy);
						}
						{
							lblApprovedDate= new JLabel("Approved Date");
							pnlNorth_b1.add(lblApprovedDate);
						}
					}
					{
						pnlNorth_b2 = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlNorth_b.add(pnlNorth_b2, BorderLayout.CENTER);
						pnlNorth_b2.setPreferredSize(new java.awt.Dimension(238, 147));

						{
							txtUploadedBy = new _JXTextField("");
							pnlNorth_b2.add(txtUploadedBy);
							txtUploadedBy.setFont(new Font("Tahoma", Font.PLAIN, 12));
							txtUploadedBy.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							dteUploaded = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlNorth_b2.add(dteUploaded);
							dteUploaded.setBounds(485, 7, 125, 21);
							dteUploaded.setDate(null);
							dteUploaded.setEnabled(false);
							dteUploaded.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor)dteUploaded.getDateEditor()).setEditable(false);
						}
						{
							txtApprovedBy = new _JXTextField("");
							pnlNorth_b2.add(txtApprovedBy);
							txtApprovedBy.setFont(new Font("Tahoma", Font.PLAIN, 12));
							txtApprovedBy.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							dteApproved = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlNorth_b2.add(dteApproved);
							dteApproved.setBounds(485, 7, 125, 21);
							dteApproved.setDate(null);
							dteApproved.setEnabled(false);
							dteApproved.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor)dteUploaded.getDateEditor()).setEditable(false);
						}
					}
					{
						pnlNorth_b3 = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlNorth_b.add(pnlNorth_b3, BorderLayout.EAST);
						pnlNorth_b3.setPreferredSize(new java.awt.Dimension(283, 147));

						{
							tagUploaded = new _JTagLabel("[ ]");
							pnlNorth_b3.add(tagUploaded);
						}
						{
							tagX = new _JTagLabel("[ ]");
							pnlNorth_b3.add(tagX);
							tagX.setVisible(false);
						}
						{
							tagApproved = new _JTagLabel("[ ]");
							pnlNorth_b3.add(tagApproved);
						}
						{
							tagX = new _JTagLabel("[ ]");
							pnlNorth_b3.add(tagX);
							tagX.setVisible(false);
						}
					}

				}
			}
		}
		{
			tabMain = new JTabbedPane();
			pnlMain.add(tabMain, BorderLayout.CENTER);
			tabMain.setPreferredSize(new java.awt.Dimension(997, 330));

			{
				pnlPricelist = new JPanel(new BorderLayout(5, 5));
				tabMain.addTab("Pricelist Uploading", null, pnlPricelist, null);
				pnlPricelist.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlNorth = new JPanel(new BorderLayout(0, 0));
					pnlPricelist.add(pnlNorth, BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new java.awt.Dimension(982, 69));				

					{
						JPanel pnlFile = new JPanel(new BorderLayout(5, 0));
						pnlNorth.add(pnlFile, BorderLayout.CENTER);
						pnlFile.setBorder(JTBorderFactory.createTitleBorder("File"));
						{
							JPanel pnlFileLabels = new JPanel(new GridLayout(1, 1));
							pnlFile.add(pnlFileLabels, BorderLayout.WEST);
							{
								lblSelectFile = new JLabel("Select File");
								pnlFileLabels.add(lblSelectFile);
							}
						}
						{
							JPanel pnlFileCenter = new JPanel(new GridLayout(1, 0, 0, 3));
							pnlFile.add(pnlFileCenter, BorderLayout.CENTER);
							{
								JPanel pnlSelectFile = new JPanel(new BorderLayout(3, 0));
								pnlFileCenter.add(pnlSelectFile);
								{
									tagSelectFile = new JXTextField();
									pnlSelectFile.add(tagSelectFile, BorderLayout.CENTER);
									tagSelectFile.setEditable(false);
								}
								{
									btnSelectFile = new JButton("Select");
									pnlSelectFile.add(btnSelectFile, BorderLayout.EAST);
									btnSelectFile.setActionCommand("Select");
									btnSelectFile.addActionListener(this);
									btnSelectFile.setEnabled(false);
								}
							}
							/*{
								JPanel pnlCommissionRate = new JPanel(new BorderLayout(3, 0));
								pnlFileCenter.add(pnlCommissionRate);
								{
									txtCommissionRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlCommissionRate.add(txtCommissionRate, BorderLayout.WEST);
									txtCommissionRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtCommissionRate.setPreferredSize(new Dimension(157, 23));
								}
							}*/
						}
					}
				}
				{
					tabCenter = new JTabbedPane();
					pnlPricelist.add(tabCenter, BorderLayout.CENTER);
					tabCenter.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent arg0) {
							JTabbedPane tab = ((JTabbedPane)arg0.getSource());

							try {
								_JTableMain table = (_JTableMain) ((JScrollPane)tab.getSelectedComponent()).getViewport().getView();
								lblTotalUnits.setText(String.format("Table Unit(s): %s", table.getRowCount()));
							} catch (NullPointerException e) { }
						}
					});
				}
				{
					pnlSouth = new JPanel(new BorderLayout());
					pnlPricelist.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setPreferredSize(new Dimension(788, 30));
					{
						lblTotalUnits = new JLabel("Total Unit(s):");
						pnlSouth.add(lblTotalUnits, BorderLayout.WEST);
						lblTotalUnits.setPreferredSize(new Dimension(222, 30));
					}
					{
						pnlSouthEast = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlSouth.add(pnlSouthEast, BorderLayout.EAST);
						pnlSouthEast.setPreferredSize(new java.awt.Dimension(298, 30));
						{
							btnUploadSave = new JButton("Save");
							pnlSouthEast.add(btnUploadSave);
							btnUploadSave.addActionListener(this);
							btnUploadSave.setEnabled(false);
							btnUploadSave.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {	
									savePricelist();
								}
							});
						}
						{
							btnUploadNewVersion = new JButton("Upload New Version");
							pnlSouthEast.add(btnUploadNewVersion);
							btnUploadNewVersion.addActionListener(this);
							btnUploadNewVersion.setEnabled(false);
							btnUploadNewVersion.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {	
									btnSelectFile.setEnabled(true);
									btnUploadNewVersion.setEnabled(false);
									//savePricelist();
								}
							});
						}
					}
				}
			}
			{
				pnlMonitoring = new JPanel(new BorderLayout(5, 5));
				tabMain.addTab("Pricelist Monitoring", null, pnlMonitoring, null);
				pnlMonitoring.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				{
					scrollMonitoringUnits = new JScrollPane();
					pnlMonitoring.add(scrollMonitoringUnits, BorderLayout.CENTER);
					scrollMonitoringUnits.setBorder(components.JTBorderFactory.createTitleBorder("Units"));
					scrollMonitoringUnits.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelMonitoringUnits = new modelPricelist_complete();

						tblMonitoringUnits = new _JTableMain(modelMonitoringUnits);						
						scrollMonitoringUnits.setViewportView(tblMonitoringUnits);
						tblMonitoringUnits.addMouseListener(new PopupTriggerListener_panel2());
						tblMonitoringUnits.setSortable(false);
						tblMonitoringUnits.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {btnMonitoringSave.setEnabled(true);}							
							public void keyPressed(KeyEvent e) {btnMonitoringSave.setEnabled(true);}	

						}); 
						tblMonitoringUnits.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if ((e.getClickCount() >= 1)) {
									btnMonitoringSave.setEnabled(true);
								}
								if ((e.getClickCount() >= 2)) {
									//clickTableColumn2();
								}

							}
							public void mouseReleased(MouseEvent e) {
								if ((e.getClickCount() >= 1)) {
									btnMonitoringSave.setEnabled(true);
								}
								if ((e.getClickCount() >= 2)) {
									//clickTableColumn2();
								}
							}
						});
					}
					{
						rowheaderMonitoringUnits = tblMonitoringUnits.getRowHeader();
						scrollMonitoringUnits.setRowHeaderView(rowheaderMonitoringUnits);
						scrollMonitoringUnits.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					pnlMonitoringSouth = new JPanel(new BorderLayout());
					pnlMonitoring.add(pnlMonitoringSouth, BorderLayout.SOUTH);
					pnlMonitoringSouth.setPreferredSize(new Dimension(0, 30));
					{
						pnlMonitoringNavigation = new JPanel(new GridLayout(1, 3, 5, 5));
						pnlMonitoringSouth.add(pnlMonitoringNavigation, BorderLayout.EAST);
						pnlMonitoringNavigation.setPreferredSize(new java.awt.Dimension(429, 30));

						{
							btnMonitoringSave = new JButton("Update");
							pnlMonitoringNavigation.add(btnMonitoringSave);
							btnMonitoringSave.setEnabled(false);
							btnMonitoringSave.setVisible(false);
							btnMonitoringSave.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {	

									if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Update unit info.?", "Confirmation", 
											JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

										pgUpdate db = new pgUpdate();	
										updateUnitInfoDetails(db);
									}
								}
							});
						}
						{
							btnMonitoringPreview = new JButton("Preview");
							pnlMonitoringNavigation.add(btnMonitoringPreview);
							btnMonitoringPreview.addActionListener(this);
							btnMonitoringPreview.setEnabled(false);
							btnMonitoringPreview.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {	

									int scanOption = JOptionPane.showOptionDialog(Pricelist.this.getTopLevelAncestor(), pnlDate, "Version",
											JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

									if ( scanOption == JOptionPane.CLOSED_OPTION ) {
										try {	

										} catch ( java.lang.ArrayIndexOutOfBoundsException ev ) {}				
									} // CLOSED_OPTION

								}
							});
						}
					}
				}
			}
			{
				pnlSchemeMain = new JPanel(new BorderLayout(5, 5));
				tabMain.addTab("Payment/Commission Scheme", null, pnlSchemeMain, null);
				pnlSchemeMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				{
					pnlSchemeNorth = new JPanel(new BorderLayout(5, 5));
					pnlSchemeMain.add(pnlSchemeNorth, BorderLayout.NORTH);
					pnlSchemeNorth.setPreferredSize(new java.awt.Dimension(982, 92));
					pnlSchemeNorth.setVisible(false);

					pnlSchemeNorthCommRate = new JPanel(new BorderLayout(5, 5));
					pnlSchemeNorth.add(pnlSchemeNorthCommRate, BorderLayout.WEST);
					pnlSchemeNorthCommRate.setPreferredSize(new java.awt.Dimension(465, 69));

					{
						pnlSchemeNorthCommRate_a = new JPanel(new BorderLayout(5, 5));
						pnlSchemeNorthCommRate.add(pnlSchemeNorthCommRate_a, BorderLayout.WEST);
						pnlSchemeNorthCommRate_a.setBorder(components.JTBorderFactory.createTitleBorder("Commission Rate (In-House)"));
						pnlSchemeNorthCommRate_a.setPreferredSize(new java.awt.Dimension(222, 69));

						{
							pnlMonitoringLabels_int = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlSchemeNorthCommRate_a.add(pnlMonitoringLabels_int, BorderLayout.WEST);
							pnlMonitoringLabels_int.setPreferredSize(new java.awt.Dimension(121, 69));

							{
								lblMonitoringCompany = new JLabel("Low Cost");
								pnlMonitoringLabels_int.add(lblMonitoringCompany);
							}
							{
								lblMonitoringProject = new JLabel("Socialized");
								pnlMonitoringLabels_int.add(lblMonitoringProject);
							}
						}
						{
							pnlMonitoringLookups_int = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlSchemeNorthCommRate_a.add(pnlMonitoringLookups_int, BorderLayout.CENTER);
							pnlMonitoringLookups_int.setPreferredSize(new java.awt.Dimension(88, 147));
							{
								txtLowCostInt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
								pnlMonitoringLookups_int.add(txtLowCostInt);
								txtLowCostInt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtLowCostInt.setText("5.00");
								txtLowCostInt.setEnabled(false);
								txtLowCostInt.setBounds(120, 0, 72, 22);
								txtLowCostInt.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {				
										btnSchemeSave.setEnabled(true);
									}				 
								});		
							}
							{
								txtSocializedInt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
								pnlMonitoringLookups_int.add(txtSocializedInt);
								txtSocializedInt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSocializedInt.setText("6.00");
								txtSocializedInt.setEnabled(false);
								txtSocializedInt.setBounds(120, 0, 72, 22);	
								txtSocializedInt.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {				
										btnSchemeSave.setEnabled(true);
									}				 
								});	
							}
						}		
					}
					{
						pnlSchemeNorthCommRate_b = new JPanel(new BorderLayout(5, 5));
						pnlSchemeNorthCommRate.add(pnlSchemeNorthCommRate_b, BorderLayout.CENTER);
						pnlSchemeNorthCommRate_b.setBorder(components.JTBorderFactory.createTitleBorder("Commission Rate (External)"));
						pnlSchemeNorthCommRate_b.setPreferredSize(new java.awt.Dimension(392, 69));
						{
							pnlMonitoringLabels_ext = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlSchemeNorthCommRate_b.add(pnlMonitoringLabels_ext, BorderLayout.WEST);
							pnlMonitoringLabels_ext.setPreferredSize(new java.awt.Dimension(133, 69));

							{
								lblMonitoringCompany = new JLabel("Low Cost");
								pnlMonitoringLabels_ext.add(lblMonitoringCompany);
							}
							{
								lblMonitoringProject = new JLabel("Socialized");
								pnlMonitoringLabels_ext.add(lblMonitoringProject);
							}
						}
						{
							pnlMonitoringLookups_ext = new JPanel(new GridLayout(2, 1, 3, 3));
							pnlSchemeNorthCommRate_b.add(pnlMonitoringLookups_ext, BorderLayout.CENTER);
							pnlMonitoringLookups_ext.setPreferredSize(new java.awt.Dimension(88, 147));

							{
								txtLowCostExt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
								pnlMonitoringLookups_ext.add(txtLowCostExt);
								txtLowCostExt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtLowCostExt.setText("6.00");
								txtLowCostExt.setEnabled(false);
								txtLowCostExt.setBounds(120, 0, 72, 22);	
								txtLowCostExt.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {				
										btnSchemeSave.setEnabled(true);
									}				 
								});	
							}
							{
								txtSocializedExt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
								pnlMonitoringLookups_ext.add(txtSocializedExt);
								txtSocializedExt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtSocializedExt.setText("7.00");
								txtSocializedExt.setEnabled(false);
								txtSocializedExt.setBounds(120, 0, 72, 22);	
								txtSocializedExt.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {				
										btnSchemeSave.setEnabled(true);
									}				 
								});	
							}
						}		
					}

					{
						pnlMonitoringNorth_b = new JPanel(new BorderLayout(5, 5));
						pnlSchemeNorth.add(pnlMonitoringNorth_b, BorderLayout.CENTER);
						pnlMonitoringNorth_b.setPreferredSize(new Dimension(783, 113));

					}
				}
				{
					scrollScheme = new JScrollPane();
					pnlSchemeMain.add(scrollScheme, BorderLayout.CENTER);
					scrollScheme.setBorder(components.JTBorderFactory.createTitleBorder("Applicable Payment Scheme"));
					scrollScheme.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelScheme = new modelPricelistPaymentScheme();

						tblScheme = new _JTableMain(modelScheme);
						scrollScheme.setViewportView(tblScheme);
						tblScheme.addMouseListener(new PopupTriggerListener_panel());
						tblScheme.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {
									clickTableColumn();
								}
							}
							public void mouseReleased(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {
									clickTableColumn();
								}
							}
						});
					}
					{
						rowheaderScheme = tblScheme.getRowHeader();
						scrollScheme.setRowHeaderView(rowheaderScheme);
						scrollScheme.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					pnlSchemeSouth = new JPanel(new BorderLayout());
					pnlSchemeMain.add(pnlSchemeSouth, BorderLayout.SOUTH);
					pnlSchemeSouth.setPreferredSize(new Dimension(0, 30));

					{
						pnlSchemeSouth_b = new JPanel(new GridLayout(1, 3, 5, 5));
						pnlSchemeSouth.add(pnlSchemeSouth_b, BorderLayout.WEST);
						pnlSchemeSouth_b.setPreferredSize(new java.awt.Dimension(191, 30));
						{
							btnMonitoringAddPmtSchme = new JButton("Add Payment Scheme");
							pnlSchemeSouth_b.add(btnMonitoringAddPmtSchme);
							btnMonitoringAddPmtSchme.setPreferredSize(new java.awt.Dimension(206, 30));
							btnMonitoringAddPmtSchme.addActionListener(this);
							btnMonitoringAddPmtSchme.setEnabled(false);
							btnMonitoringAddPmtSchme.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									lookupPmtScheme.setLookupSQL(sql_paymentScheme());									

									int scanOption = JOptionPane.showOptionDialog(Pricelist.this.getTopLevelAncestor(), pnlAddNewPmtScheme, "Add Payment Scheme",
											JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

									if ( scanOption == JOptionPane.CLOSED_OPTION ) {
										try {		
											buyer_type = ""; //to refresh the buyer_type so that when user edit on the payment scheme table, it will get the buyer type of the selected row
											lookupPmtScheme.setValue("");
											txtPmtSchName.setText("");
											lookupCommSchemeInt.setValue("");
											lookupCommSchemeExt.setValue("");

										} catch ( java.lang.ArrayIndexOutOfBoundsException ev ) {}				
									} // CLOSED_OPTION
								}
							});
						}
					}

					{
						pnlSchemeSouth_a = new JPanel(new GridLayout(1, 3, 5, 5));
						pnlSchemeSouth.add(pnlSchemeSouth_a, BorderLayout.EAST);
						pnlSchemeSouth_a.setPreferredSize(new java.awt.Dimension(429, 30));

						{
							btnSchemeSave = new JButton("Update");
							pnlSchemeSouth_a.add(btnSchemeSave);
							btnSchemeSave.setEnabled(false);
							btnSchemeSave.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									pgUpdate db = new pgUpdate();
									updateCommissionRate(db);
									updatePaymentSchemeDetails(db);	
									db.commit();
									setPricelist_mainDtls();
									displayPmtSchemeList(modelScheme, rowheaderScheme);
									btnSchemeSave.setEnabled(false);
								}
							});
						}
						{
							btnSchemePreview = new JButton("Preview");
							pnlSchemeSouth_a.add(btnSchemePreview);
							btnSchemePreview.addActionListener(this);
							btnSchemePreview.setEnabled(false);
							btnSchemePreview.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									preview_scheme();
								}
							});						
						}
					}					
				}
			}
		}
		{
			pnlMainSouth = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlMainSouth, BorderLayout.SOUTH);
			pnlMainSouth.setPreferredSize(new java.awt.Dimension(997, 31));
			{
				pnlMainSouth_a = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlMainSouth.add(pnlMainSouth_a, BorderLayout.EAST);
				pnlMainSouth_a.setPreferredSize(new java.awt.Dimension(525, 31));
				{
					btnApprove = new JButton("Approve");
					pnlMainSouth_a.add(btnApprove);
					btnApprove.addActionListener(this);
					btnApprove.setEnabled(false);
					btnApprove.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {							
							approveMarketingScheme();
						}
					});
				}
				{
					btnRefresh = new JButton("Refresh");
					pnlMainSouth_a.add(btnRefresh);
					btnRefresh.setEnabled(false);
					btnRefresh.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {	
							refresh_tables();
							refreshFields();
							setPricelist_mainDtls();
							displayUnits();
							tabMain.setSelectedIndex(1);
							JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(), "Data refreshed.", "Refresh", JOptionPane.INFORMATION_MESSAGE);
						}
					});
				}
				{
					btnCancel = new JButton("Cancel");
					pnlMainSouth_a.add(btnCancel);
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if(btnUploadSave.isEnabled()==true||btnMonitoringSave.isEnabled()==true||btnSchemeSave.isEnabled()==true)  
							{if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Cancel unsaved data?", "Cancel", 
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
								refresh_mainFields();
								disableMainFields();
							}
							}
							else
							{
								refresh_mainFields();
								disableMainFields();
							}
						}
					});
				}
			}
		}
		{
			pnlAddNewPmtScheme = new JPanel();
			pnlAddNewPmtScheme.setLayout(new BorderLayout(5, 5));
			pnlAddNewPmtScheme.setBorder(lineBorder);		
			pnlAddNewPmtScheme.setPreferredSize(new java.awt.Dimension(500, 200));		

			{		
				pnlAddNewPmtScheme_a = new JPanel(new BorderLayout(5, 5));
				pnlAddNewPmtScheme.add(pnlAddNewPmtScheme_a, BorderLayout.NORTH);	
				pnlAddNewPmtScheme_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewPmtScheme_a.setPreferredSize(new java.awt.Dimension(380, 140));		

				{
					pnlAddNewPmtScheme_a1 = new JPanel(new GridLayout(4, 5, 5, 5));
					pnlAddNewPmtScheme_a.add(pnlAddNewPmtScheme_a1, BorderLayout.WEST);				
					pnlAddNewPmtScheme_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlAddNewPmtScheme_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewPmtScheme_a1.setPreferredSize(new java.awt.Dimension(140, 120));		

					{
						lblPmtScheme = new JLabel("Pmt. Scheme", JLabel.TRAILING);
						pnlAddNewPmtScheme_a1.add(lblPmtScheme);
						lblPmtScheme.setEnabled(true);	
						lblPmtScheme.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblPmtSchemeName = new JLabel("Pmt. Scheme Name", JLabel.TRAILING);
						pnlAddNewPmtScheme_a1.add(lblPmtSchemeName);
						lblPmtSchemeName.setEnabled(true);	
						lblPmtSchemeName.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblCommSchInt = new JLabel("Comm. Rate (Inhouse)", JLabel.TRAILING);
						pnlAddNewPmtScheme_a1.add(lblCommSchInt);
						lblCommSchInt.setEnabled(true);	
						lblCommSchInt.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblCommSchExt = new JLabel("Comm. Rate (External)", JLabel.TRAILING);
						pnlAddNewPmtScheme_a1.add(lblCommSchExt);
						lblCommSchExt.setEnabled(true);	
						lblCommSchExt.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
				}	
				{
					pnlAddNewPmtScheme_a2 = new JPanel(new GridLayout(4,5,5, 5));
					pnlAddNewPmtScheme_a.add(pnlAddNewPmtScheme_a2, BorderLayout.CENTER);	
					pnlAddNewPmtScheme_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlAddNewPmtScheme_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						lookupPmtScheme = new _JLookup(null, "Payment Scheme", 2, 2);
						pnlAddNewPmtScheme_a2.add(lookupPmtScheme);
						lookupPmtScheme.setBounds(20, 27, 20, 25);
						lookupPmtScheme.setReturnColumn(0);
						lookupPmtScheme.setEnabled(true);	
						lookupPmtScheme.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupPmtScheme.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									String pmt_scheme_name = (String) data[1];
									txtPmtSchName.setText(pmt_scheme_name);
									buyer_type = data[2].toString();

									lookupCommSchemeInt.setLookupSQL(sql_commSchemeInt());
									lookupCommSchemeExt.setLookupSQL(sql_commSchemeExt());

									lookupCommSchemeInt.setValue("");
									lookupCommSchemeExt.setValue("");
								}
							}
						});	
					}	
					{
						txtPmtSchName = new JXTextField("");
						pnlAddNewPmtScheme_a2.add(txtPmtSchName);
						txtPmtSchName.setEnabled(true);	
						txtPmtSchName.setEditable(true);
						txtPmtSchName.setBounds(120, 25, 300, 22);	
						txtPmtSchName.setHorizontalAlignment(JTextField.CENTER);
						txtPmtSchName.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,11));
					}	
					{
						lookupCommSchemeInt = new _JLookup(null, "Comm. Scheme (Internal)", 2, 2);
						pnlAddNewPmtScheme_a2.add(lookupCommSchemeInt);
						lookupCommSchemeInt.setBounds(20, 27, 20, 25);						
						lookupCommSchemeInt.setEnabled(true);	
						lookupCommSchemeInt.setReturnColumn(0);
						lookupCommSchemeInt.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupCommSchemeInt.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
								}
							}
						});	
					}			
					{
						lookupCommSchemeExt = new _JLookup(null, "Comm. Scheme (External)", 2, 2);
						pnlAddNewPmtScheme_a2.add(lookupCommSchemeExt);
						lookupCommSchemeExt.setBounds(20, 27, 20, 25);
						lookupCommSchemeExt.setReturnColumn(0);
						lookupCommSchemeExt.setEnabled(true);	
						lookupCommSchemeExt.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupCommSchemeExt.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

								}
							}
						});	
					}			
				}

				pnlAddNewPmtScheme_c = new JPanel(new BorderLayout(5, 5));
				pnlAddNewPmtScheme.add(pnlAddNewPmtScheme_c, BorderLayout.SOUTH);	
				pnlAddNewPmtScheme_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlAddNewPmtScheme_c.setPreferredSize(new java.awt.Dimension(500, 40));	

				{
					btnAddPmtScheme = new JButton("Add Payment Scheme");
					pnlAddNewPmtScheme_c.add(btnAddPmtScheme);
					btnAddPmtScheme.setActionCommand("SaveAgentDoc");
					btnAddPmtScheme.addActionListener(this);
					btnAddPmtScheme.setEnabled(true);
					btnAddPmtScheme.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							if (incompleteDetails()==true)
							{								
								JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(),"Incomplete details","Error",JOptionPane.ERROR_MESSAGE);
							}
							else
							{
								if (schemeHasDuplicate()==true) 
								{
									JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(),"Scheme already exists.","Error",JOptionPane.ERROR_MESSAGE);
								}
								else
								{		
									insertPaymentSchemeDetails();
									displayPmtSchemeList(modelScheme, rowheaderScheme);
									tblScheme.packAll();
									lookupPmtScheme.setLookupSQL(sql_paymentScheme());
								}
							}
						}
					});
				}
			}
		}
		{
			pnlDate= new JPanel();
			pnlDate.setLayout(null);
			pnlDate.setPreferredSize(new java.awt.Dimension(267, 89));
			{
				lblVersion = new JLabel();
				pnlDate.add(lblVersion);
				lblVersion.setBounds(22, 16, 73, 25);
				lblVersion.setText("Version :");
			}
			{
				lookupVersion = new _JLookup(null, "Version No.", 0);
				pnlDate.add(lookupVersion);
				lookupVersion.setBounds(95, 16, 160, 25);				
				lookupVersion.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup)event.getSource()).getDataSet();
						if(data != null){							

							version = data[0].toString();								
							version_status = data[3].toString();		
							lookupVersion.setValue(version);

						}
					}
				});
			}
			{
				btnOK = new JButton();
				pnlDate.add(btnOK);
				btnOK.setBounds(95, 55, 69, 25);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
						optionPaneWindow.dispose();

						preview_unit();
						preview_unit_cont_1();
						preview_unit_cont_2();

					}
				});
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, btnSelectFile, tagSelectFile, cmbSelectSheet, txtCommissionRate, btnUploadSave, btnCancel));
		this.setPreferredSize(new java.awt.Dimension(1009, 550));
		this.setBounds(0, 0, 1009, 550);

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}


	//display
	private void displayUnits() {
		_Pricelist.displayMonitoringUnits(modelMonitoringUnits, rowheaderMonitoringUnits, proj_id, phase);
		scrollMonitoringUnits.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblMonitoringUnits.getRowCount())));
		tblMonitoringUnits.packAll();
	}

	public static void creatPmtSchemetable(DefaultTableModel modelMain, JList rowHeader) {//used

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = "select '', '', '', '', '', '' \r\n" ;

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}		
	}

	public void displayPmtSchemeList(DefaultTableModel modelMain, JList rowHeader) {
		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select\r\n" + 
			"\r\n" + 
			"a.pmt_scheme_id,\r\n" + 
			"b.pmt_scheme_desc,\r\n" + 
			"a.cm_scheme_id_internal,\r\n" + 
			"a.cm_scheme_id_external,\r\n" + 
			"(case when a.edited_by is null then a.created_by else a.edited_by end),\r\n" + 
			"(case when a.date_edited is null then a.date_created else a.date_edited end)\r\n" + 
			"\r\n" + 
			"from rf_marketing_scheme_detail a\r\n" + 
			"left join mf_payment_scheme b on a.pmt_scheme_id = b.pmt_scheme_id\r\n" + 
			"\r\n" ;

		if (rec_id==null) {sql = sql + "where a.rec_id = null ";}
		else {sql = sql + "where a.rec_id = '"+rec_id+"' ";}

		sql = sql +

		"and a.status_id = 'A' " +
		"order by a.pmt_scheme_id";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}	
		tblScheme.packAll();	
	}	


	//
	protected JTable streamSheet(Sheet sheet) {//XXX streamSheet

		int colCount = sheet.getColumnCount();
		int rowCount = sheet.getRowCount();

		ArrayList<Integer> deleteColumn = new ArrayList<Integer>();

		Object[] columnNames = null;
		Object[][] rowValue = null;

		rowValue = new Object[rowCount][colCount + 1];

		//System.out.printf("     Column: %s%n", colCount);
		//System.out.printf("     Row: %s%n", rowCount);

		//kukunin kung anong row ung table header gamit ung word na PHASE
		int colnameRow = 0;
		for(int r=0;r<rowCount;r++){
			Boolean isBreak = false;
			for(int c=0;c<colCount;c++){
				if(sheet.getValueAt(c, r).toString().trim().toUpperCase().equals("PHASE")) {
					colnameRow = r;
					isBreak = true;
				}
			}
			if(isBreak){
				break;
			}
		}
		//System.out.printf("Column Name Row: %s%n", colnameRow);

		ArrayList<Integer> listHiddenColumns = new ArrayList<Integer>();
		for(int x=0; x < colCount; x++){
			Column<SpreadSheet> col = sheet.getColumn(x);

			//System.out.printf("%nColumn #%s: %s%n", x, col.getElement().getAttributes());
			for(Object obj : col.getElement().getAttributes()){
				Attribute att = (Attribute) obj;

				if(att.getName().equals("visibility")){
					//System.out.printf("Row #%s %s: %s%n", x, att.getName(), att.getValue());
					listHiddenColumns.add(x);
				}
			}

			/*for(Object obj : col.getPrivateStyle().getElement().getAttributes()){
				Attribute att = (Attribute) obj;
				System.out.printf("Row #%s %s: %s%n", x, att.getName(), att.getValue());
			}*/
		}

		//buburahin ung may mga walang laman na column
		deleteColumn.clear();
		for(int c=0;c<colCount;c++){
			if(sheet.getValueAt(c, colnameRow).toString().equals("") || sheet.getValueAt(c, colnameRow)==null){
				deleteColumn.add((c+1));
				//System.out.println("Col: "+(c+1)+" Value: "+columnNames[c+1]);
			}else deleteColumn.clear();
		}
		colCount = (deleteColumn.get(0) - 1);
		columnNames = new Object[colCount + 1];

		//String addMRI = " per year", addFIRE = " per year";
		//ihfDP = 0;
		//hdmfDP = 0;

		for(int c=0;c<colCount;c++){
			/*if(sheet.getValueAt(c, colnameRow).equals("MRI") && sheet.getName().equals("HDMF")){
				columnNames[c+1] = sheet.getValueAt(c, colnameRow).toString().trim()+addMRI;
				addMRI = "";
			}else if(sheet.getValueAt(c, colnameRow).equals("FI") && sheet.getName().equals("HDMF")){
				columnNames[c+1] = sheet.getValueAt(c, colnameRow).toString().trim()+addFIRE;
				addFIRE = "";
			}else{
				columnNames[c+1] = sheet.getValueAt(c, colnameRow).toString().trim();
				sheet.getColumn(c).getWidth();

				if(!((String)columnNames[c+1]).equals("")){
					//System.out.printf("%s (%s)%n", sheet.getValueAt(c, colnameRow).toString().trim(), sheet.getValueAt(c, colnameRow).getClass());
				}
			}*/

			columnNames[c+1] = sheet.getValueAt(c, colnameRow).toString().trim();
			//sheet.getColumn(c).getWidth();

			if(!((String)columnNames[c+1]).equals("")){
				//System.out.printf("%s (%s)%n", sheet.getValueAt(c, colnameRow).toString().trim(), sheet.getValueAt(c, colnameRow).getClass());
			}
			getIHF(sheet.getValueAt(c, colnameRow).toString());
			getHDMF(sheet.getValueAt(c, colnameRow).toString());
			//System.out.println("IHF: "+ihfDP+" - HDMF: "+hdmfDP);
		}
		//System.out.println("Total: "+deleteColumn.size());

		for(int r=0;r<rowCount;r++){
			Object phase = sheet.getValueAt(1, r);

			if(lookupPhase.getValue().equals(phase.toString())){
				for(int c=0;c<colCount;c++){
					rowValue[0][0] = false;
					rowValue[r][0] = false;
					rowValue[r][c+1] = sheet.getValueAt(c, r);
				}
			}
		}

		DefaultTableModel modelAddUnit = new DefaultTableModel(rowValue, columnNames){
			private static final long serialVersionUID = 3174178548239382080L;
			public boolean isCellEditable(int rowIndex, int columnIndex) { 
				Boolean to = null;
				if(columnIndex==0) to = true;
				else to = false;
				return to;
			}
		};

		final _JTableMain tblAddUnit = new _JTableMain(modelAddUnit);
		tblAddUnit.getColumnModel().getColumn(0).setCellEditor(tblAddUnit.getDefaultEditor(Boolean.class));
		tblAddUnit.getColumnModel().getColumn(0).setCellRenderer(tblAddUnit.getDefaultRenderer(Boolean.class));
		tblAddUnit.getColumnModel().getColumn(0).setWidth(35);
		tblAddUnit.getColumnModel().getColumn(0).setMinWidth(35);
		tblAddUnit.getColumnModel().getColumn(0).setMaxWidth(35);

		tblAddUnit.setDefaultRenderer(Date.class, DateRenderer.getDateRenderer());
		tblAddUnit.setDefaultRenderer(Time.class, DateRenderer.getTimeRenderer());
		tblAddUnit.setDefaultRenderer(Timestamp.class, DateRenderer.getDateRenderer());
		tblAddUnit.setDefaultRenderer(String.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));
		tblAddUnit.setDefaultRenderer(Integer.class, NumberRenderer.getIntegerRenderer(SwingConstants.CENTER));
		tblAddUnit.setDefaultRenderer(BigDecimal.class, NumberRenderer.getMoneyRenderer());
		tblAddUnit.setDefaultRenderer(_JLookup.class, TextRenderer.getTextRenderer(SwingConstants.CENTER));

		RolloverMouseAdapter rolloverAdapter = new RolloverMouseAdapter(tblAddUnit);
		//RolloverBooleanRenderer renderer = new RolloverBooleanRenderer(rolloverAdapter);

		CheckBoxHeader.RolloverAdapter ma = new CheckBoxHeader.RolloverAdapter(tblAddUnit);
		tblAddUnit.getTableHeader().addMouseListener(ma);
		tblAddUnit.getTableHeader().addMouseMotionListener(ma);

		tblAddUnit.getColumnModel().getColumn(0).setHeaderRenderer(new CheckBoxHeader(tblAddUnit, ma));
		tblAddUnit.getColumnModel().getColumn(0).setMaxWidth(35);
		tblAddUnit.getColumnModel().getColumn(0).setMinWidth(35);
		tblAddUnit.getColumnModel().getColumn(0).setPreferredWidth(35);

		tblAddUnit.addMouseListener(rolloverAdapter);
		tblAddUnit.addMouseMotionListener(rolloverAdapter);

		//this.getColumnModel().getColumn(0).setCellRenderer(renderer);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tblAddUnit.getModel());
		tblAddUnit.setRowSorter(sorter);
		sorter.setSortable(0, false);

		//modelAddUnit.removeRow(colnameRow);
		for(int x = (modelAddUnit.getRowCount() -1); x >= 0; x--){
			Object value = modelAddUnit.getValueAt(x, 1);
			if(value == null){
				modelAddUnit.removeRow(x);
			}
		}

		tblAddUnit.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == 16){
					pressedShift = true;
				}
			}
			public void keyReleased(KeyEvent arg0) {
				pressedShift = false;
			}
		});

		tblAddUnit.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				tblAddUnit.requestFocus();
				for(int x=0;x<(tblAddUnit.getSelectedRows().length);x++){
					if(pressedShift){
						tblAddUnit.setValueAt(true, tblAddUnit.getSelectedRows()[x], 0);
					}	
				}
			}
			public void mouseReleased(MouseEvent arg0) {
				tblAddUnit.requestFocus();
			}
		});

		tblAddUnit.packColumns(5, "");

		//Remove hidden columns
		for(int x = tblAddUnit.getRowCount() -1; x >= 0 ; x--){
			if(listHiddenColumns.contains(x)){
				tblAddUnit.removeColumn(tblAddUnit.getColumn(x + 1));
			}
		}

		for(int x=0; x < tblAddUnit.getColumnCount(); x++){
			//System.out.printf("Column #%s: %s%n", x, tblAddUnit.getColumnClass(x));
		}
		//evaluate();

		return tblAddUnit;
	}

	protected boolean getIHF(String string){
		char[] c = string.toCharArray();
		for(int i=0; i < string.length(); i++){
			if ( !Character.isDigit(c[i])){
				return false;
			}else{
				if(string.trim().substring(i+1).trim().equals("down Payment Zero Interest")){
					//ihfDP = Integer.parseInt(Character.toString(c[i]));
					//System.out.println("IHF DP: "+c[i]);
					//System.out.println("Particular: "+string.trim().substring(i+1).trim());
				}else if(string.trim().substring(i+1).trim().equals("years Monthly Amortization @ 18% per anum fix for 5 yrs.")){
					//ihfMA = (c[i-1]+c[i] * 12);
					//System.out.println("IHF MA: "+c[i-1]+c[i]);
					//System.out.println("Particular: "+string.trim().substring(i+1).trim());
				}
			}
		}
		return true;
	}

	protected boolean getHDMF(String string){
		char[] c = string.toCharArray();
		for(int i=0; i < string.length(); i++){
			if ( !Character.isDigit(c[i])){
				return false;
			}else{
				if(string.trim().substring(i+1).trim().equals("Months Cash Outlay")){
					//hdmfDP = Integer.parseInt(Character.toString(c[i-1])+Character.toString(c[i]));
					//System.out.println("HDMF DP: "+hdmfDP);
					//System.out.println("Particular: "+string.trim().substring(i+1).trim());
				}else if(string.trim().substring(i+1).trim().equals("yrs to pay @ 7% interest per annum Paid Within Due Date")){
					//hdmfMA = (c[i-1]+c[i] * 12);
					//System.out.println("HDMF MA: "+c[i-1]+c[i]);
					//System.out.println("Particular: "+string.trim().substring(i+1).trim());
				}
			}
		}
		return true;
	}

	protected boolean hasSelectedUnit() {
		_JTableMain table = (_JTableMain) ((JScrollPane)tabCenter.getSelectedComponent()).getViewport().getView();

		ArrayList<Boolean> listSelectedUnits = new ArrayList<Boolean>();
		for(int x=0; x < table.getRowCount(); x++){
			listSelectedUnits.add((Boolean) table.getValueAt(x, 0));
		}
		return listSelectedUnits.contains(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Select")){
			if(fileChooser == null){
				fileChooser = new JFileChooser();
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(new FncODSFileFilter(new String[] { "ods" }, "Spreadsheets (*.ods, *.odc, *.ots)"));

				Action details = fileChooser.getActionMap().get("viewTypeDetails");
				details.actionPerformed(null);
			}
			fileChooser.setSelectedFile(new File(""));
			int status = fileChooser.showOpenDialog(Pricelist.this.getTopLevelAncestor());
			if (status == JFileChooser.APPROVE_OPTION) {
				if(fileChooser.getSelectedFile().equals(null)){
					JOptionPane.showMessageDialog(getParent(), "No Selected Document");
					return;
				}

				//System.out.printf("Selected File: %s%n", fileChooser.getSelectedFile().toString());
				String selectedFile = fileChooser.getSelectedFile().toString();
				File fileSelected = new File(selectedFile);
				tagSelectFile.setText(selectedFile);

				tabCenter.removeAll();
				try {
					int sheetCount = SpreadSheet.createFromFile(fileSelected).getSheetCount();
					for(int x=0; x < sheetCount; x++){
						Sheet sheet = SpreadSheet.createFromFile(fileSelected).getSheet(x);

						String sheetName = sheet.getName().toString();
						//System.out.printf("Sheet Name: %s%n", sheetName);

						JTable table = streamSheet(sheet);
						tabCenter.addTab(sheetName, null, new JScrollPane(table), null);
						btnUploadSave.setEnabled(true);
						hasNSPexceed3M(table);
					}

					_JTableMain table = (_JTableMain) ((JScrollPane)tabCenter.getSelectedComponent()).getViewport().getView();
					lblTotalUnits.setText(String.format("Table Unit(s): %s", table.getRowCount()));
					cmbPriceListType.setEnabled(true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		if(actionCommand.equals("MonitoringEdit")){
			modelMonitoringUnits.setEditable(true);
		}
	}

	public void evaluate(){
		try {
			FileInputStream fis = new FileInputStream("/home/PC-115l/EAST MERIDIAN FOR MAM RHEA.ods");
			Workbook wb = new HSSFWorkbook(fis);
			org.apache.poi.ss.usermodel.Sheet sheet = (org.apache.poi.ss.usermodel.Sheet) wb.getSheetAt(0);
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();

			// suppose your formula is in B3
			CellReference cellReference = new CellReference("B3"); 
			Row row = (Row) sheet.getRow(cellReference.getRow());
			Cell cell = row.getCell(cellReference.getCol()); 

			if (cell!=null) {
				switch (evaluator.evaluateFormulaCell(cell)) {
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.println(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.println(cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					System.out.println(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BLANK:
					break;
				case Cell.CELL_TYPE_ERROR:
					System.out.println(cell.getErrorCellValue());
					break;

					// CELL_TYPE_FORMULA will never occur
				case Cell.CELL_TYPE_FORMULA: 
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()&&tblScheme.isEnabled()==true) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()&&tblScheme.isEnabled()==true) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()&&tblMonitoringUnits.isEnabled()==true) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()&&tblMonitoringUnits.isEnabled()==true) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	private void savePricelist(){

		_JTableMain table = (_JTableMain) ((JScrollPane)tabCenter.getSelectedComponent()).getViewport().getView();		

			if(table == null){
				JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(), "Please select file.", "Save", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if(!hasSelectedUnit()){
				JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(), "Please select unit(s) to save.", "Save", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if(!_Pricelist.hasFileHasCorrectColumns(table)){
				return;
			}

			Boolean hasCurrentVersion = sql_getCurrentVersion() > 0;
			if (hasCurrentVersion) {//XXX SAVE
				if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "An existing version already exists?\nWould you like to overwrite?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION) {
					return;
				}
			}

			if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				System.out.println("Dumaan dito before saving");
				if (_Pricelist.saveUnits_TV(table, lookupCompany.getValue(), lookupProject.getValue(), cmbPriceListType.getSelectedItem().toString() ,hasCurrentVersion))
				{
					JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(), "Unit(s) has been saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
					displayUnits();
					
					txtLowCostInt.setEnabled(true);
					txtSocializedInt.setEnabled(true);
					txtLowCostExt.setEnabled(true);
					txtSocializedExt.setEnabled(true);
					btnMonitoringAddPmtSchme.setEnabled(true);
					btnSchemeSave.setEnabled(true);
					modelScheme.setEditable(true);
					displayPmtSchemeList(modelScheme, rowheaderScheme);
					rec_id = sql_getRecID().toString();
					if(!hasCurrentVersion){
						txtStatus.setText("A");
						tagStatus.setTag("ACTIVE");
					}
				}
			}
		//}

		/*if (sql_getCurrentVersion()==0) {// commented by Alvin Gonzales (2015-07-28)
			if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				_Pricelist.saveUnits(table, lookupCompany.getValue(), lookupProject.getValue(), false);

				pgUpdate db = new pgUpdate();	
				_Pricelist.savePricelist(table, lookupProject.getValue(), db);
				if (mktgSchmeExists()==true){

				} else {
					insertMktgSchemeMain();
				}

				JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(), "Unit(s) has been saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
				db.commit();
				displayUnits();
				txtStatus.setText("A");
				tagStatus.setTag("ACTIVE");
			}
		} else {
			if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "An existing version already exists?\nWould you like to overwrite?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					_Pricelist.saveUnits(table, lookupCompany.getValue(), lookupProject.getValue(), true);

					pgUpdate db = new pgUpdate();	
					setPreviousVersionInactive(db);
					_Pricelist.savePricelist(table, lookupProject.getValue(), db);
					if (mktgSchmeExists()==true){} else {insertMktgSchemeMain();}

					JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(), "Unit(s) has been saved.", "Save", JOptionPane.INFORMATION_MESSAGE);					
					db.commit();
					displayUnits();
				}
			}
		}*/
	}


	//display
	public void setPricelist_mainDtls(){

		Object[] pricelist_dtl = getPricelistMainDtls();		

		status_name 	= "";
		String created_by 	= "";
		Date created_date  	= null;
		String posted_by	= "";
		Date posted_date  	= null;		
		String created_by_id 	= "";
		String posted_by_id 	= "";
		rec_id 				= "";

		//Removed temporarily (02-03-2016); in the future when commission rate per project/phase varies, then I may have to bring this back
		/*String comm_rate_lowcost_internal = "";
		String comm_rate_socialized_internal = "";
		String comm_rate_lowcost_external = "";
		String comm_rate_socialized_external = "";*/

		try { status_id = pricelist_dtl[0].toString();} 	catch (NullPointerException e) { status_id = "" ; }
		try { status_name = pricelist_dtl[1].toString();} 	catch (NullPointerException e) { status_name = "" ; }		
		try { created_by = pricelist_dtl[2].toString();} 	catch (NullPointerException e) { created_by = "" ; }
		try { posted_by = pricelist_dtl[4].toString();} 	catch (NullPointerException e) { posted_by = "" ; }
		/*try { comm_rate_lowcost_internal = pricelist_dtl[6].toString();} 		catch (NullPointerException e) { comm_rate_lowcost_internal = "0.00" ; }
		try { comm_rate_socialized_internal = pricelist_dtl[7].toString();} 	catch (NullPointerException e) { comm_rate_socialized_internal = "0.00" ; }
		try { comm_rate_lowcost_external = pricelist_dtl[8].toString();} 		catch (NullPointerException e) { comm_rate_lowcost_external = "0.00" ; }
		try { comm_rate_socialized_external = pricelist_dtl[9].toString();} 		catch (NullPointerException e) { comm_rate_socialized_external = "0.00"; }*/
		try { created_date = (Date) pricelist_dtl[3];} 		catch (NullPointerException e) { created_date = null; }
		try { posted_date = (Date) pricelist_dtl[5];} 		catch (NullPointerException e) { posted_date = null; }
		try { created_by_id = pricelist_dtl[10].toString();} 	catch (NullPointerException e) { created_by_id = "" ; }
		try { posted_by_id = pricelist_dtl[11].toString();} 	catch (NullPointerException e) { posted_by_id = "" ; }
		try { rec_id = pricelist_dtl[12].toString();} 	catch (NullPointerException e) { rec_id = null ; }

		txtStatus.setText(status_id);	
		tagStatus.setTag(status_name);
		tagUploaded.setTag(created_by);	
		tagApproved.setTag(posted_by);				
		dteUploaded.setDate(created_date);
		dteApproved.setDate(posted_date);
		//txtLowCostInt.setText(comm_rate_lowcost_internal);
		//txtSocializedInt.setText(comm_rate_socialized_internal);
		//txtLowCostExt.setText(comm_rate_lowcost_external);
		//txtSocializedExt.setText(comm_rate_socialized_external);
		txtUploadedBy.setText(created_by_id);	
		txtApprovedBy.setText(posted_by_id);	

	}


	//enable-disable
	private void enableFieldsForEditing(Boolean x){

		btnUploadSave.setEnabled(false);
		btnApprove.setEnabled(x);
		btnCancel.setEnabled(true);
		btnMonitoringSave.setEnabled(false);
		btnMonitoringPreview.setEnabled(true);
		btnMonitoringAddPmtSchme.setEnabled(x);
		btnSchemeSave.setEnabled(false);
		btnSchemePreview.setEnabled(true);
		btnSelectFile.setEnabled(true);
		modelScheme.setEditable((x));
		modelMonitoringUnits.setEditable((x));

		txtLowCostInt.setEnabled(x);
		txtSocializedInt.setEnabled(x);
		txtLowCostExt.setEnabled(x);
		txtSocializedExt.setEnabled(x);
	}

	private void refreshFields(){

		//txtLowCostInt.setText("0.00");
		//txtSocializedInt.setText("0.00");
		//txtLowCostExt.setText("0.00");
		//txtSocializedExt.setText("0.00");
		txtUploadedBy.setText("");
		tagUploaded.setTag("");
		txtApprovedBy.setText("");
		tagApproved.setTag("");
		tagSelectFile.setText("");		

	}

	public void refresh_tables(){

		//reset table 1
		FncTables.clearTable(modelMonitoringUnits);	
		rowheaderMonitoringUnits = tblMonitoringUnits.getRowHeader();
		scrollMonitoringUnits.setRowHeaderView(rowheaderMonitoringUnits);

		//reset table 2
		FncTables.clearTable(modelScheme);	
		rowheaderScheme = tblScheme.getRowHeader();
		scrollScheme.setRowHeaderView(rowheaderScheme);

		tabCenter.removeAll();

	}

	public void refresh_mainFields(){

		refreshFields();
		refresh_tables();
		lookupProject.setText("");
		lookupPhase.setText("");
		txtStatus.setText("");
		tagProject.setTag("");
		tagPhase.setTag("");
		tagStatus.setTag("");
		tagSelectFile.setText("");

		dteUploaded.setDate(null);
		dteApproved.setDate(null);

		btnUploadSave.setEnabled(false);
		btnUploadNewVersion.setEnabled(false);
		btnApprove.setEnabled(false);
		btnCancel.setEnabled(false);
		btnMonitoringSave.setEnabled(false);
		btnMonitoringPreview.setEnabled(false);
		btnMonitoringAddPmtSchme.setEnabled(false);
		btnSchemeSave.setEnabled(false);
		btnSchemePreview.setEnabled(false);
		btnRefresh.setEnabled(false);
		btnSelectFile.setEnabled(false);

	}

	public void disableMainFields(){

		lblPhase.setEnabled(false);
		lookupPhase.setEnabled(false);
		tagPhase.setEnabled(false);	

		lblStatus.setEnabled(false);
		txtStatus.setEnabled(false);
		tagStatus.setEnabled(false);	

	}

	public void initialize_comp(){		

		co_id 		= "02";	
		co_name		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(co_name);
		company_logo = RequestForPayment.sql_getCompanyLogo();	

		lblProject.setEnabled(true);
		lookupProject.setEnabled(true);
		tagProject.setEnabled(true);											

		lookupProject.setValue("");
		tagProject.setTag("");
		lookupPhase.setValue("");
		tagPhase.setTag("");
		txtStatus.setText("");
		tagStatus.setTag("");
		dteUploaded.setDate(null);
		dteApproved.setDate(null);
		refreshFields();
		refresh_tables();

		lblPhase.setEnabled(false);
		lookupPhase.setEnabled(false);
		tagPhase.setEnabled(false);	

		lblStatus.setEnabled(false);
		txtStatus.setEnabled(false);
		tagStatus.setEnabled(false);												

		proj_id = "";

		lookupProject.setLookupSQL(SQL_PROJECT(co_id));
		KEYBOARD_MANAGER.focusNextComponent();

		lookupCompany.setValue(co_id);
	}


	//lookup / SQL
	public String sql_phase(String proj_id) {//XXX Phase
		String SQL = "select\n" +
		//"getinteger(a.phase) as \"Phase\",\n" +
		"trim(a.phase) as \"Phase\",\n" +
		//"'Phase ' || trim(array_to_string(array_agg(trim(a.phase)), ' & ')) as \"Description\",\n" +
		"a.sub_proj_name as \"Description\",\n" +
		"b.proj_alias || getinteger(a.phase) as \"Alias\"," +
		"a.sub_proj_id as \"SubProj ID\" \n" +
		"from mf_sub_project a\n" +
		"left join mf_project b on a.proj_id = b.proj_id\n" +
		"where a.proj_id = '"+ proj_id +"'\n" +
		"and a.status_id = 'A'\n" +
		//"group by getinteger(a.phase), b.proj_alias, a.sub_proj_id, a.sub_proj_name \n" +
		"order by getinteger(a.phase);";
		return SQL;
	}

	public Object [] getPricelistMainDtls() {

		String strSQL = 
			"select \r\n" + 
			"a.status_id,\r\n" + 								//0
			"upper(trim(b.status_desc)) as status_desc,\r\n" + 
			"upper(trim(d.entity_name)) as created_by,\r\n" + 
			"a.date_created,\r\n" + 
			"upper(trim(f.entity_name)) as posted_by,\r\n" + 
			"a.date_posted,\r\n" + 								//5
			"a.comm_rate_lowcost_internal,\r\n" + 
			"a.comm_rate_socialized_internal,\r\n" + 
			"a.comm_rate_lowcost_external,\r\n" + 
			"a.comm_rate_socialized_external," +
			"a.created_by ," +		//10				
			"a.posted_by," +
			"a.rec_id  " +
			"\r\n" + 
			"from (select distinct on (proj_id, sub_proj_id) * from rf_marketing_scheme_main " +
			"	order by proj_id, sub_proj_id, version_no desc, date_created desc) a\r\n" + 
			"left join mf_record_status b on a.status_id = b.status_id\r\n" + 
			"left join em_employee c on a.created_by = c.emp_code \r\n" + 
			"left join rf_entity d on c.entity_id = d.entity_id\r\n" + 
			"left join em_employee e on a.posted_by = e.emp_code \r\n" + 
			"left join rf_entity f on e.entity_id = f.entity_id\r\n" + 
			"where a.co_id = '"+co_id+"' \r\n" + 
			"and a.proj_id = '"+proj_id+"' \r\n" + 
			"and a.sub_proj_id = '"+sub_proj_id+"'" ;

		System.out.printf("SQL #1 getPricelistMainDtls: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	public String sql_paymentScheme() {//XXX Phase

		String scheme_list = "'0'";
		int row = tblScheme.getRowCount();
		int x = 0;

		while (x<row){
			String scheme_id = tblScheme.getValueAt(x,0).toString().trim();	
			scheme_list = scheme_list + ",'"+scheme_id+"'" ;

			x++;
		}		

		String SQL = 
			"select pmt_scheme_id as \"Scheme ID\", " +
			"pmt_scheme_desc as \"Scheme Desc\"," +
			"unnest(type_id) as \"Type ID\" " +
			"from mf_payment_scheme " +
			"where status_id = 'A' " +
			"and pmt_scheme_id not in ("+scheme_list+")  \n"  +
			"order by pmt_scheme_id\r\n" + 
			"";

		System.out.printf("SQL (List of Pmt. Scheme): " + SQL);
		return SQL;
	}

	public String sql_houseModel() {//XXX Phase
		String SQL = 
			"select model_id as \"Model ID\", " +
			"trim(model_desc) as \"Model Desc\" " +
			"from mf_product_model " +
			"where status_id = 'A' " +
			"order by model_id \r\n" + 
			"";
		return SQL;
	}

	public String sql_commSchemeInt() {//XXX Phase

		if (lookupPmtScheme.getText().equals("")){
			Integer row = tblScheme.getSelectedRow();
			String pmt_scheme = "";	
			try { pmt_scheme = tblScheme.getValueAt(row,0).toString().trim();} catch (NullPointerException e) { pmt_scheme 	= ""; }
			sql_getBuyerType(pmt_scheme);		
		}

		String SQL = 
			"select scheme_id as \"Comm Scheme ID\", " +
			"scheme_desc as \"Comm Scheme Desc.\"" +
			"from cm_scheme_hd a  " +
			"where status_id = 'A' " +
			"and agent_type = '001' " +
			"and buyer_type = '"+buyer_type+"'  \n"+
			"order by scheme_id\r\n" + 
			"";
		return SQL;
	}

	public String sql_commSchemeExt() {//XXX Phase

		if (lookupPmtScheme.getText().equals("")){
			Integer row = tblScheme.getSelectedRow();
			String pmt_scheme = "";	
			try { pmt_scheme = tblScheme.getValueAt(row,0).toString().trim();} catch (NullPointerException e) { pmt_scheme 	= ""; }
			sql_getBuyerType(pmt_scheme);		
		}

		String SQL = 
			"select scheme_id as \"Comm Scheme ID\", " +
			"scheme_desc as \"Comm Scheme Desc.\"" +
			"from cm_scheme_hd " +
			"where status_id = 'A' " +
			"and agent_type = '002' " +
			"and buyer_type = '"+buyer_type+"'  \n"+
			"order by scheme_id \r\n" + 
			"";
		return SQL;
	}

	public Boolean pmtSchmeExists(String pmt_scheme_id) {//ok	

		Boolean exists = false;

		String SQL = 
			"select rec_id from rf_marketing_scheme_detail " +
			"where rec_id = "+rec_id+" " +
			"and pmt_scheme_id = '"+pmt_scheme_id+"'  " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			exists = true;
		}else{			
		}

		return exists;
	}

	public Boolean mktgSchmeExists() {//ok	

		Boolean exists = false;

		String SQL = 
			"select rec_id from rf_marketing_scheme_detail " +
			"where rec_id = "+rec_id+" " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){exists = true;} 
		else{}

		return exists;
	}

	public Integer sql_getNextRecID() {
		int next_no = 1;

		String SQL = "select max(rec_id+1) from rf_marketing_scheme_main where co_id = '" + co_id + "'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if (db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				next_no = 1;
			} else {
				next_no = Integer.parseInt(db.getResult()[0][0].toString());
			}
		} else {

		}

		return next_no;
	}

	public static String sql_getModelId(String model_desc) {

		String model_id = "";

		String SQL = 
			"select trim(model_id) from mf_product_model where trim(model_desc) = '"+model_desc.trim()+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {model_id = "";}
			else {model_id = (String) db.getResult()[0][0]; }

		}else{
			model_id = "";
		}

		return model_id;
	}

	public String sql_version() {//XXX Phase
		String SQL = 
			"select distinct on (a.version_no) " +
			"a.version_no as \"Version No.\", \r\n" + 
			"to_char(a.date_created,'MM-dd-yyyy') as \"Date Created\", \r\n" + 
			"upper(trim(c.entity_name)) as \"Created by\",\r\n" + 
			"(case when a.status_id = 'I' then 'INACTIVE' else'ACTIVE' end) as \"Status\"   \r\n" + 
			"\r\n" + 
			"from rf_marketing_scheme_pricelist a\r\n" + 
			"left join em_employee b on a.created_by = b.emp_code\r\n" + 
			"left join rf_entity c on b.entity_id = c.entity_id \r\n" + 
			"\r\n" + 
			"where a.proj_id = '"+proj_id+"' and a.phase = '"+phase+"'";

		System.out.printf("sql_version :"+ SQL);
		return SQL;
	}

	public Integer sql_getCurrentVersion() {		
		String SQL = "SELECT MAX(COALESCE(version_no,0)) FROM rf_marketing_scheme_pricelist WHERE proj_id = '"+proj_id+"' AND phase = '"+phase+"';";

		//System.out.printf("sql_getCurrentVersion :"+ SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if (db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				current_version_no = 0;
			} else {
				current_version_no = Integer.parseInt(db.getResult()[0][0].toString());
			}
		} else {

		}

		return current_version_no;
	}

	public Integer sql_getRecID() {

		int x = 1;

		String SQL = "select rec_id from rf_marketing_scheme_main where sub_proj_id = '" + sub_proj_id + "'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if (db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				x = 1;
			} else {
				x = Integer.parseInt(db.getResult()[0][0].toString());
			}
		} else {

		}

		return x;
	}


	//checking
	private Boolean schemeHasDuplicate(){

		Boolean w = false;

		if (modelScheme.getRowCount()>0)
		{
			int x = modelScheme.getRowCount();
			int y = 0;

			while (y<x)
			{				
				String schm_id = modelScheme.getValueAt(y,0).toString().trim();					
				if (schm_id.equals(lookupPmtScheme.getText().trim()))
				{
					w = true;
				}
				else
				{}
				y++;
			}
		}
		else {}		
		return w;		
	}

	private Boolean incompleteDetails(){

		boolean x = false;		

		String scheme_id, comm_scheme_id_int, comm_scheme_id_ext;

		try { scheme_id = lookupPmtScheme.getText().toString();} catch (NullPointerException e) { scheme_id 	= ""; }
		try { comm_scheme_id_int = lookupCommSchemeInt.getText().toString();} catch (NullPointerException e) { comm_scheme_id_int	= ""; }
		try { comm_scheme_id_ext = lookupCommSchemeExt.getText().toString();} catch (NullPointerException e) { comm_scheme_id_ext	= ""; }

		if (scheme_id.equals("") || comm_scheme_id_int.equals("")||comm_scheme_id_ext.equals("")) {x=true;} 
		else {}		

		return x;
	}

	public String sql_getBuyerType(String pmt_scheme) {//XXX Phase

		buyer_type = "";

		String SQL = 
			"select unnest (type_id) from mf_payment_scheme where pmt_scheme_id = '"+pmt_scheme+"' ";

		System.out.printf("sql_getBuyerType :"+ SQL + "\n");

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if (db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {buyer_type = "";} 
			else {buyer_type = db.getResult()[0][0].toString();}
		} else {}

		return buyer_type;
	}



	//save
	@SuppressWarnings("unused")
	private void insertMktgSchemeMain(){	
		if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Add marketing scheme?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			pgUpdate db = new pgUpdate();	

			String sqlDetail = 
				"INSERT into rf_marketing_scheme_main values (" +			
				""+sql_getNextRecID()+",  \n" + //0
				"'"+co_id+"',  \n" + //1
				"'"+proj_id+"',  \n" + //1
				"'"+sub_proj_id+"',  \n" + //1
				"0.00,  \n" + //1
				"0.00,  \n" + //1
				"0.00,  \n" + //1
				"0.00,  \n" + //1
				"'A', " + 				//4
				"'"+UserInfo.EmployeeCode+"', \n" +				//5
				"'"+Calendar.getInstance().getTime()+"',  \n" + //6
				"null, \n" +									//7
				"null," +
				"null," +
				"null \n" +			
				")   " ;

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	
			db.commit();
		}				
	}
		
	private void insertPaymentSchemeDetails(){	

		if (JOptionPane.showConfirmDialog(pnlAddNewPmtScheme, "Add payment scheme?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();	

			//insert into rf_marketing_scheme_detail
			String scheme_id = lookupPmtScheme.getText().trim();	
			String comm_scheme_int = lookupCommSchemeInt.getText().trim();	
			String comm_scheme_ext = lookupCommSchemeExt.getText().trim();	

			String sqlDetail = 
				"INSERT into rf_marketing_scheme_detail values (" +			
				""+rec_id+",  \n" + 	 //0
				"'"+scheme_id+"',  \n" + //1
				"'"+comm_scheme_int+"',  \n" + 	//2
				"'"+comm_scheme_ext+"',  \n" +  //3
				"'A', " + 				//4
				"'"+UserInfo.EmployeeCode+"', \n" +				//5
				"'"+Calendar.getInstance().getTime()+"',  \n" + //6
				"null, \n" +									//7
				"null \n" +			
				")   " ;

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	

			//update payment scheme
			String sqlDetail2 = 
				"update mf_payment_scheme set phase = array_append(phase,'"+sub_proj_id+"')  " +
				"where trim(pmt_scheme_id) = '"+scheme_id+"' " ;

			System.out.printf("SQL #2: %s", sqlDetail2);
			db.executeUpdate(sqlDetail2, false);	

			db.commit();

			JOptionPane.showMessageDialog(pnlAddNewPmtScheme,"Payment scheme saved.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void updatePaymentSchemeDetails(pgUpdate db){

		if(modelScheme.getRowCount()>=0)
		{		
			if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				int x = modelScheme.getRowCount();
				int y = 0;

				while (y<x)
				{				

					String scheme_id = modelScheme.getValueAt(y,0).toString().trim();	
					String comm_scheme_int = modelScheme.getValueAt(y,2).toString().trim();	
					String comm_scheme_ext = modelScheme.getValueAt(y,3).toString().trim();	

					String sqlDetail = 
						"update rf_marketing_scheme_detail set " +						
						"pmt_scheme_id = '"+scheme_id+"',  \n" + //1
						"cm_scheme_id_internal = '"+comm_scheme_int+"',  \n" + 	//2
						"cm_scheme_id_external = '"+comm_scheme_ext+"',  \n" +  //3
						"edited_by = '"+UserInfo.EmployeeCode+"', \n" +			//4
						"date_edited = '"+Calendar.getInstance().getTime()+"'  \n" + //5
						"where rec_id = "+rec_id+" " +
						"and pmt_scheme_id = '"+scheme_id+"'   " ;

					System.out.printf("SQL #1: %s", sqlDetail);
					db.executeUpdate(sqlDetail, false);					

					y++;
				}
			}
		}


		JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(),"Payment scheme updated.","Information",JOptionPane.INFORMATION_MESSAGE);				
	}

	private void updatePaymentSchemeStatus(String scheme_id){

		pgUpdate db = new pgUpdate();	

		String sqlDetail = 
			"update rf_marketing_scheme_detail set " +						
			"status_id = 'I'  \n" + 
			"where rec_id = "+rec_id+" " +
			"and pmt_scheme_id = '"+scheme_id+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	


		//update payment scheme
		String sqlDetail2 = 
			"update mf_payment_scheme set phase = array_remove(phase,'"+sub_proj_id+"')  " +
			"where trim(pmt_scheme_id) = '"+scheme_id+"' " ;

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);	


		db.commit();	
		JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(),"Payment scheme removed.","Information",JOptionPane.INFORMATION_MESSAGE);

	}

	private void updateCommissionRate(pgUpdate db){

		String sqlDetail = 
			"update rf_marketing_scheme_main set " +						
			"comm_rate_lowcost_internal = "+txtLowCostInt.getText().replace(",","")+",  \n" + 
			"comm_rate_socialized_internal = "+txtSocializedInt.getText().replace(",","")+",  \n" + 
			"comm_rate_lowcost_external = "+txtLowCostExt.getText().replace(",","")+",  \n" + 
			"comm_rate_socialized_external = "+txtSocializedExt.getText().replace(",","")+"  \n" + 
			"where rec_id = "+rec_id+" " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	

	}

	private void approveMarketingScheme(){		

		if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Are you sure you want to approve this marketing scheme?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();	
			String sqlDetail = 
				"update rf_marketing_scheme_main set " +						
				"status_id = 'P',  \n" + 
				"posted_by = '"+UserInfo.EmployeeCode+"', \n" +				//5
				"date_posted = '"+Calendar.getInstance().getTime()+"'  \n" + //6
				"where rec_id = "+rec_id+" "  ;

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	
			db.commit();	
			JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(),"Marketing scheme approved.","Information",JOptionPane.INFORMATION_MESSAGE);
			refreshFields();
			setPricelist_mainDtls();
			displayUnits();
			enableFieldsForEditing(false);
			txtStatus.setText("P");
			tagStatus.setTag("POSTED");
		}
	}

	private void deleteUnit(String unit_id){		

		if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Are you sure you want to remove this unit?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();	
			String sqlDetail = 
				"update mf_unit_info set " +						
				"status_id = 'D',  \n" + 
				"edited_by = '"+UserInfo.EmployeeCode+"', \n" +				//5
				"date_edited = '"+Calendar.getInstance().getTime()+"'  \n" + //6
				"where trim(unit_id) = '"+unit_id.trim()+"' "  ;

			System.out.printf("SQL #1: %s", sqlDetail);
			db.executeUpdate(sqlDetail, false);	
			db.commit();	
			JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(),"Unit removed.","Information",JOptionPane.INFORMATION_MESSAGE);
			displayUnits();
		}

	}

	private void updateUnitInfoDetails(pgUpdate db){

		if(modelMonitoringUnits.getRowCount()>=0)
		{		
			if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				int x = modelMonitoringUnits.getRowCount();
				int y = 0;

				while (y<x)
				{								
					String phase 	= modelMonitoringUnits.getValueAt(y,0).toString().trim();		
					String block 	= modelMonitoringUnits.getValueAt(y,1).toString().trim();		
					String lot 		= modelMonitoringUnits.getValueAt(y,2).toString().trim();		
					String model_alias = modelMonitoringUnits.getValueAt(y,4).toString().trim();	

					Double lot_area = 0.00;	
					Double total_factor = 0.00;	
					Double house_apv = 0.00;	
					Double appraise_value_apv = 0.00;	
					Double lot_apv = 0.00;	
					Double total_apv = 0.00;		
					Double house_sp = 0.00;	
					Double lot_sp = 0.00;	
					Double other_factor = 0.00;		
					Double TCP = 0.00;	
					Double disc = 0.00;		
					Double tcp_discounted = 0.00;		
					Double tcp_discounted_roundoff = 0.00;	
					Double loanable_amt_90 = 0.00;	
					Double loanable_amt_final = 0.00;		
					Double perc_LA_to_APV = 0.00;		
					Double perc_LA_to_TCP = 0.00;	
					Double equity = 0.00;	
					Double mri = 0.00;	
					Double fi = 0.00;	
					Double filing_fee_release = 0.00;	
					Double docs_stamps = 0.00;		
					Double inspection_fee = 0.00;		
					Double commit_fee = 0.00;	
					Double addl_hdmf_retention = 0.00;		
					Double filing_fee_filing = 0.00;		
					Double int_on_cts =0.00;		
					Double upico = 0.00;		
					Double misc_fees = 0.00;	
					Double tot_cash_outlay = 0.00;	
					Double months_cash_outlay_11mos = 0.00;	
					Double monthly_int_6pt5 = 0.00;	
					Double monthly_mri_6pt5 = 0.00;	
					Double monthly_fi_6pt5 = 0.00;	
					Double monthly_addl_hdmf_premium_6pt5 = 0.00;		
					Double monthly_total_6pt5 = 0.00;	
					Double required_monthly_net_income_6pt5 = 0.00;	
					Double monthly_int_7pt9 = 0.00;	
					Double monthly_mri_7pt9 = 0.00;	
					Double monthly_fi_7pt9 = 0.00;	
					Double monthly_addl_hdmf_premium_7pt9 = 0.00;		
					Double monthly_total_7pt9 = 0.00;	
					Double required_monthly_net_income_7pt9 = 0.00;		

					try { lot_area = Double.parseDouble( modelMonitoringUnits.getValueAt(y,3).toString().trim().replace(",","")); } catch (NullPointerException e) { }
					try { total_factor = Double.parseDouble( modelMonitoringUnits.getValueAt(y,5).toString().trim().replace(",","")); } catch (NullPointerException e) { }
					try { house_apv = Double.parseDouble( modelMonitoringUnits.getValueAt(y,6).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { appraise_value_apv = Double.parseDouble( modelMonitoringUnits.getValueAt(y,7).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { lot_apv = Double.parseDouble( modelMonitoringUnits.getValueAt(y,8).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { total_apv = Double.parseDouble( modelMonitoringUnits.getValueAt(y,9).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { house_sp = Double.parseDouble( modelMonitoringUnits.getValueAt(y,10).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { lot_sp = Double.parseDouble( modelMonitoringUnits.getValueAt(y,11).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { other_factor = Double.parseDouble( modelMonitoringUnits.getValueAt(y,12).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { TCP = Double.parseDouble( modelMonitoringUnits.getValueAt(y,13).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { disc = Double.parseDouble( modelMonitoringUnits.getValueAt(y,14).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { tcp_discounted = Double.parseDouble( modelMonitoringUnits.getValueAt(y,15).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { tcp_discounted_roundoff = Double.parseDouble( modelMonitoringUnits.getValueAt(y,17).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { loanable_amt_90 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,16).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { loanable_amt_final = Double.parseDouble( modelMonitoringUnits.getValueAt(y,18).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { perc_LA_to_APV = Double.parseDouble( modelMonitoringUnits.getValueAt(y,19).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { perc_LA_to_TCP = Double.parseDouble( modelMonitoringUnits.getValueAt(y,20).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { equity = Double.parseDouble( modelMonitoringUnits.getValueAt(y,21).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { mri = Double.parseDouble( modelMonitoringUnits.getValueAt(y,22).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { fi = Double.parseDouble( modelMonitoringUnits.getValueAt(y,23).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { filing_fee_release = Double.parseDouble( modelMonitoringUnits.getValueAt(y,24).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { docs_stamps = Double.parseDouble( modelMonitoringUnits.getValueAt(y,25).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { inspection_fee = Double.parseDouble( modelMonitoringUnits.getValueAt(y,26).toString().trim().replace(",",""));} catch (NullPointerException e) { }					
					try { commit_fee = Double.parseDouble( modelMonitoringUnits.getValueAt(y,27).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { addl_hdmf_retention = Double.parseDouble( modelMonitoringUnits.getValueAt(y,28).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { filing_fee_filing = Double.parseDouble( modelMonitoringUnits.getValueAt(y,29).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { int_on_cts = Double.parseDouble( modelMonitoringUnits.getValueAt(y,30).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { upico = Double.parseDouble( modelMonitoringUnits.getValueAt(y,31).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { misc_fees = Double.parseDouble( modelMonitoringUnits.getValueAt(y,32).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { tot_cash_outlay = Double.parseDouble( modelMonitoringUnits.getValueAt(y,34).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { months_cash_outlay_11mos = Double.parseDouble( modelMonitoringUnits.getValueAt(y,35).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { monthly_int_6pt5 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,36).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { monthly_mri_6pt5 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,37).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { monthly_fi_6pt5 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,38).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { monthly_addl_hdmf_premium_6pt5 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,39).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { monthly_total_6pt5 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,40).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { required_monthly_net_income_6pt5 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,41).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { monthly_int_7pt9 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,42).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { monthly_mri_7pt9 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,43).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { monthly_fi_7pt9 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,44).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { monthly_addl_hdmf_premium_7pt9 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,45).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { monthly_total_7pt9 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,46).toString().trim().replace(",",""));} catch (NullPointerException e) { }
					try { required_monthly_net_income_7pt9 = Double.parseDouble( modelMonitoringUnits.getValueAt(y,47).toString().trim().replace(",",""));} catch (NullPointerException e) { }

					String SQL = 
						"update rf_marketing_scheme_pricelist set \n" +
						"lotarea = "+lot_area+", \n" +		//lot_area		
						"model_alias = '"+model_alias+"', \n" +	//model_alias					
						"total_factor = "+total_factor+", \n" +	//total_factor
						"house_apv = "+house_apv+", \n" +		//house_apv
						"appraise_value_apv = "+appraise_value_apv+", \n" +	//appraise_value_apv
						"lot_apv = "+lot_apv+", \n" +			//lot_apv
						"total_apv = "+total_apv+", \n" +		//total_apv	
						"house_sp = "+house_sp+", \n" +			//house_sp
						"lot_sp = "+lot_sp+", \n" +				//lot_sp
						"other_factor = "+other_factor+", \n" +	//other_factor
						"TCP = "+TCP+", \n" +					//tcp
						"disc = "+disc+", \n" +					//disc 
						"tcp_discounted = "+tcp_discounted+", \n" +	//tcp_discounted
						"tcp_discounted_roundoff = "+tcp_discounted_roundoff+", \n" +		//tcp_discounted_roundoff
						"loanable_amt_90 = "+loanable_amt_90+", \n" +				//loanable_amt_90
						"loanable_amt_final = "+loanable_amt_final+", \n" +			//loanable_amt_final
						"perc_LA_to_APV = "+perc_LA_to_APV+", \n" +//perc_la_to_apv
						"perc_LA_to_TCP = "+perc_LA_to_TCP+", \n" +				//perc_la_to_tcp
						"equity = "+equity+", \n" +			//equity					
						"mri = "+mri+", \n" +				//mri
						"fi = "+fi+", \n" +					//fi
						"filing_fee_release = "+filing_fee_release+", \n" +			//filing_fee_release
						"docs_stamps = "+docs_stamps+", \n" +		//docs_stamps
						"inspection_fee = "+inspection_fee+", \n" +	//inspection_fee
						"commit_fee = "+commit_fee+", \n" +	//commit_fee
						"addl_hdmf_retention = "+addl_hdmf_retention+", \n" +		//addl_hdmf_retention					
						"filing_fee_filing = "+filing_fee_filing+", \n" +			//filing_fee_filing
						"int_on_cts = "+int_on_cts+", \n" +	//int_on_cts
						"upico = "+upico+", \n" +			//upico					
						"misc_fees = "+misc_fees+", \n" +	//misc_fees
						"tot_cash_outlay = "+tot_cash_outlay+", \n" +			//tot_cash_outlay
						"months_cash_outlay_11mos = "+months_cash_outlay_11mos+", \n" +	//months_cash_outlay_11mos
						"monthly_int_6pt5 = "+monthly_int_6pt5+", \n" +			//monthly_int_6pt5
						"monthly_mri_6pt5 = "+monthly_mri_6pt5+", \n" +			//monthly_mri_6pt5
						"monthly_fi_6pt5 = "+monthly_fi_6pt5+", \n" +			//monthly_fi_6pt5
						"monthly_addl_hdmf_premium_6pt5 ="+monthly_addl_hdmf_premium_6pt5+", \n" +		//monthly_addl_hdmf_premium_6pt5
						"monthly_total_6pt5 = "+monthly_total_6pt5+", \n" +		//monthly_total_6pt5
						"required_monthly_net_income_6pt5 = "+required_monthly_net_income_6pt5+", \n" +	//required_monthly_net_income_6pt5
						"monthly_int_7pt9 = "+monthly_int_7pt9+", \n" +			//monthly_int_7pt9					
						"monthly_mri_7pt9 = "+monthly_mri_7pt9+", \n" +			//monthly_mri_7pt9
						"monthly_fi_7pt9 = "+monthly_fi_7pt9+", \n" +			//monthly_fi_7pt9
						"monthly_addl_hdmf_premium_7pt9 = "+monthly_addl_hdmf_premium_7pt9+", \n" +		 //monthly_addl_hdmf_premium_7pt9
						"monthly_total_7pt9 = "+monthly_total_7pt9+", \n" +		//monthly_total_7pt9
						"required_monthly_net_income_7pt9 = "+required_monthly_net_income_7pt9+", \n" +	//required_monthly_net_income_7pt9
						"edited_by = '"+UserInfo.EmployeeCode+"', \n" +			//created_by
						"date_edited = '"+Calendar.getInstance().getTime()+"' \n" +	//date_created
						"where proj_id = '"+proj_id+"' \n" +		
						"and phase = '"+phase+"' \n" +				
						"and block = '"+block+"' \n" +		
						"and lot = '"+lot+"' \n" +
						"and version_no = "+sql_getCurrentVersion()+"  "+							
						" \n";


					System.out.printf("SQL #1: %s", SQL);
					db.executeUpdate(SQL, false);					

					y++;
				}
			}
		}

		db.commit();
		JOptionPane.showMessageDialog(Pricelist.this.getTopLevelAncestor(),"Unit pricelist updated.","Information",JOptionPane.INFORMATION_MESSAGE);
		displayUnits();
	}

	@SuppressWarnings("unused")
	private void setPreviousVersionInactive(pgUpdate db){	
		String sqlDetail = 
			"update rf_marketing_scheme_pricelist set \n" +
			"status_id = 'I' \n" +	
			"where proj_id = '"+proj_id+"' \n" +		
			"and phase = '"+phase+"' " +
			"and version_no =  "+current_version_no+"  \n" +					
			" \n";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}



	//table
	private void clickTableColumn(){

		int column = tblScheme.getSelectedColumn();
		int row = tblScheme.getSelectedRow();		

		Integer x[] = {0,1,2,3,4,5};
		String sql[] = {sql_paymentScheme(),"",sql_commSchemeInt(),sql_commSchemeExt()};
		String lookup_name[] = {"Payment Scheme","","Comm. Scheme Internal","Comm. Scheme External"};			

		if (column == x[column] && modelScheme.isEditable() && sql[column]!="") {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 0) {	
				modelScheme.setValueAt(data[0], row, 0);
				modelScheme.setValueAt(data[1], row, 1);
				modelScheme.setValueAt("", row, 2);
				modelScheme.setValueAt("", row, 3);
				modelScheme.setValueAt("", row, 4);
				modelScheme.setValueAt(null, row, 5);
				btnSchemeSave.setEnabled(true);
				buyer_type = data[2].toString();
			}				
			if (data != null && column == 2) {	
				modelScheme.setValueAt(data[0], row, 2);
				btnSchemeSave.setEnabled(true);
				buyer_type = "";
			}				
			if (data != null && column == 3) {	
				modelScheme.setValueAt(data[0], row, 3);
				btnSchemeSave.setEnabled(true);
				buyer_type = "";
			}	
		}		

		else {}		
	}

	private void removePmtScheme(){//used

		int r  = tblScheme.getSelectedRow();		
		String scheme_id = modelScheme.getValueAt(r,0).toString().trim();	

		if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Remove row?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			
			((DefaultTableModel) tblScheme.getModel()).removeRow(r);  
			updatePaymentSchemeStatus(scheme_id);
			displayPmtSchemeList(modelScheme, rowheaderScheme);
		}


	}

	private void removeUnit(){//used
		int r  = tblMonitoringUnits.getSelectedRow();		
		String unit_id = tblMonitoringUnits.getValueAt(r,0).toString().trim();	

		if (JOptionPane.showConfirmDialog(Pricelist.this.getTopLevelAncestor(), "Remove unit?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {			
			((DefaultTableModel) tblMonitoringUnits.getModel()).removeRow(r);  
			deleteUnit(unit_id);
			displayUnits();
		}
	}

	private Boolean NSPexceed3M(_JTableMain tblAddUnit){//used

		Boolean x = false;

		int r  = tblAddUnit.getRowCount();		
		int w = 0;

		while (w<r)
		{			
			Double nsp = Double.parseDouble((String) tblAddUnit.getValueAt(w, 27));	

			if (nsp> 3000000.00)
			{x = true; break;}			
			w++;
		}

		return x;
	}
	
	private void hasNSPexceed3M(JTable table){//used

		int r  = table.getRowCount();		
		int w = 0;

		while (w<r)
		{			
			Double nsp = Double.parseDouble(table.getValueAt(w, 27).toString());	

			if (nsp> 3000000.00)
			{
				btnUploadSave.setEnabled(false);
				JOptionPane.showMessageDialog(null,"NSP exceed 3M.","Warning",JOptionPane.WARNING_MESSAGE);
				btnUploadSave.setEnabled(true);
				break;
			}	
			else
			{
				btnUploadSave.setEnabled(true);
				System.out.println("Upload Enabled");
			}
			w++;
		}
		
	}



	//preview
	private void preview_unit(){//used
		
		String criteria = "Unit Pricelist Report(PagIBIG)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", co_name);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase", phase);
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("prepared_by_alias", UserInfo.Alias);
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("phase_status", status_name);
		mapParameters.put("version", version);
		mapParameters.put("version_status", version_status);
		FncReport.generateReport("/Reports/rptPricelist_PabIBIG_Units.jasper", reportTitle, co_name, mapParameters);  
		
		
		String criteria2 = "Unit Pricelist Report(Spot Cash)";		
		String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());		
		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("company", co_name);
		mapParameters2.put("proj_id", proj_id);
		mapParameters2.put("phase", phase);
		mapParameters2.put("prepared_by", UserInfo.FullName);
		mapParameters2.put("prepared_by_alias", UserInfo.Alias);
		mapParameters2.put("proj_name", proj_name);
		mapParameters2.put("phase_status", status_name);
		mapParameters2.put("version", version);
		mapParameters2.put("version_status", version_status);
		FncReport.generateReport("/Reports/rptPricelist_SpotCash.jasper", reportTitle2, co_name, mapParameters2); 
		
		/*
		String criteria3 = "Unit Pricelist Report(Bank Financing)";		
		String reportTitle3 = String.format("%s (%s)", title.replace(" Report", ""), criteria3.toUpperCase());
		Map<String, Object> mapParameters3 = new HashMap<String, Object>();
		mapParameters3.put("company", co_name);
		mapParameters3.put("proj_id", proj_id);
		mapParameters3.put("phase", phase);
		mapParameters3.put("prepared_by", UserInfo.FullName);
		mapParameters3.put("prepared_by_alias", UserInfo.Alias);
		mapParameters3.put("proj_name", proj_name);
		mapParameters3.put("phase_status", status_name);
		mapParameters3.put("version", version);
		mapParameters3.put("version_status", version_status);
		FncReport.generateReport("/Reports/rptPricelist_units_Bank_Fin_part1.jasper", reportTitle3, co_name, mapParameters3);
		*/
	}

	private void preview_unit_cont_1(){//used

		String criteria = "Unit Pricelist Report (PagIBIG Part2)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", co_name);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase", phase);
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("prepared_by_alias", UserInfo.Alias);
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("phase_status", status_name);
		mapParameters.put("version", version);
		mapParameters.put("version_status", version_status);
		FncReport.generateReport("/Reports/rptPricelist_PagIBIG_part2.jasper", reportTitle, co_name, mapParameters); 
		
		/*
		String criteria2 = "Unit Pricelist Report (Bank Financing Part2)";		
		String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());
		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("company", co_name);
		mapParameters2.put("proj_id", proj_id);
		mapParameters2.put("phase", phase);
		mapParameters2.put("prepared_by", UserInfo.FullName);
		mapParameters2.put("prepared_by_alias", UserInfo.Alias);
		mapParameters2.put("proj_name", proj_name);
		mapParameters2.put("phase_status", status_name);
		mapParameters2.put("version", version);
		mapParameters2.put("version_status", version_status);
		FncReport.generateReport("/Reports/rptPricelist_Bank_part2.jasper", reportTitle2, co_name, mapParameters2); 
		*/
	}

	private void preview_unit_cont_2(){//used

		String criteria = "Unit Pricelist Report (PagIBIG Part 3)";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());	
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", co_name);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("phase", phase);
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("prepared_by_alias", UserInfo.Alias);
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("phase_status", status_name);
		mapParameters.put("version", version);
		mapParameters.put("version_status", version_status);
		FncReport.generateReport("/Reports/rptPricelist_PagIBIG_part3.jasper", reportTitle, co_name, mapParameters); 
		
		/*
		String criteria2 = "Unit Pricelist Report (Bank Financing Part3)";		
		String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());	
		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("company", co_name);
		mapParameters2.put("proj_id", proj_id);
		mapParameters2.put("phase", phase);
		mapParameters2.put("prepared_by", UserInfo.FullName);
		mapParameters2.put("prepared_by_alias", UserInfo.Alias);
		mapParameters2.put("proj_name", proj_name);
		mapParameters2.put("phase_status", status_name);
		mapParameters2.put("version", version);
		mapParameters2.put("version_status", version_status);
		FncReport.generateReport("/Reports/rptPricelist_BankFin_part3.jasper", reportTitle2, co_name, mapParameters2); 
		*/
	}

	private void preview_scheme(){//used

		String criteria = "Marketing Scheme";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());			

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", co_name);
		mapParameters.put("rec_id", rec_id);
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("prepared_by_alias", UserInfo.Alias);
		mapParameters.put("comm_rate_soclzd_int", txtSocializedInt.getText());
		mapParameters.put("comm_rate_lowcost_int", txtLowCostInt.getText());
		mapParameters.put("comm_rate_soclzd_ext", txtSocializedExt.getText());
		mapParameters.put("comm_rate_lowcost_ext", txtLowCostExt.getText());
		mapParameters.put("proj_name", proj_name);
		mapParameters.put("phase", phase);
		mapParameters.put("status_name", status_name);

		FncReport.generateReport("/Reports/rptPricelistPaymentScheme.jasper", reportTitle, co_name, mapParameters); 

	}

}