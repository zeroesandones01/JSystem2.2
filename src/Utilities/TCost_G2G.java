package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import com.toedter.calendar.JTextFieldDateEditor;

import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_garbage_fee_for_issuance;

public class TCost_G2G extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 959523740860207910L;
	
	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;
	JPanel pnlSelect;
	JPanel pnlDate;
	JPanel pnllblDateTo;
	JPanel pnlDateTo;
	JPanel pnlUploading;
	JPanel pnlGenerateDetails;

	JButton btnGenerate;
	JButton btnPreview;
	JButton btnImport;

	JCheckBox chkPBL;

	JLabel lblDateTo;
	JLabel lblDateFr;
	JLabel lblWithPBL;

	JXTextField tagSelectFile;

	_JDateChooser dateTo;
	_JDateChooser dateFr;

	_JTableMain tblGarbageFee;
	JTabbedPane tabCenter;

	JScrollPane scrollG2GTcostTagging;

	JList rowheaderGarbageFee;

	JFileChooser fileChooser;

	DefaultTableModel modelGarbageFee;
	
	private model_garbage_fee_for_issuance model_garbage_fee_for_issuance;
	JList row_header_garbage_fee_for_issuance;

	_JLookup lookupCompany;
	JTextField txtCompany;

	private _JLookup lookupProject; 
	private _JXTextField txtProject;
	
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;
	
	private JComboBox cmbEPEBType;
	
	private _JLookup lookupBatchNo;
	private _JXTextField txtStatus;
	
	private _JXTextField txtJVNo;
	
	_JLookup lookupBatch;
	JTextField txtBatch;

	String pbl_id = null;

	protected Boolean pressedShift = false;

	static String title = "G to G Tcost Tagging";
	Dimension frameSize = new Dimension(800, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTableMain tbl_garbage_fee_for_isuance;

	private JLabel lblForIssuance;

	private JTextField txtForIssuance;

	private JLabel lblForIssuanceCount;

	private JLabel lblForIssuanceDetails;

	private String batch_no;

	private JButton btnViewRemaining;

	private Object count;
	
	public TCost_G2G() {
		super(title, false, true, false, true);
		initGUI();
	}

	public TCost_G2G(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public TCost_G2G(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setVisible(true);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			// pnlMain.setPreferredSize(new java.awt.Dimension(798, 85));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(5, 5));
				pnlNorth.setBorder(null);
				pnlNorth.setPreferredSize(new java.awt.Dimension(1000, 150));
				{
					pnlGenerateDetails = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlGenerateDetails, BorderLayout.CENTER);
					pnlGenerateDetails.setBorder(JTBorderFactory.createTitleBorder("Details"));
					
					// File Upload Labels
					JPanel pnlLabels = new JPanel(new BorderLayout(3, 3));
					{
						pnlLabels = new JPanel(new GridLayout(5, 1, 10, 3));
						pnlGenerateDetails.add(pnlLabels, BorderLayout.WEST);
						pnlLabels.add(new JLabel("Company"));
						pnlLabels.add(new JLabel("Project"));
						pnlLabels.add(new JLabel("Phase"));
						pnlLabels.add(new JLabel("EPEB Type"));
						pnlLabels.add(new JLabel("Batch No"));
					}
					
					// File Upload Center
					{
						pnlUploading = new JPanel(new GridLayout(5, 1, 10, 3));
						pnlGenerateDetails.add(pnlUploading, BorderLayout.CENTER);
						
						// Project
						{
							JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
							pnlUploading.add(pnlCompany);
							{
								lookupCompany = new _JLookup();
								pnlCompany.add(lookupCompany, BorderLayout.WEST);
								lookupCompany.setPreferredSize(new Dimension(50, 100));
								lookupCompany.setReturnColumn(0);
								lookupCompany.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String company_name = (String) data[1];
											txtCompany.setText(company_name);
										}
									}
								});
							}
							
							{
								txtCompany = new JTextField();
								pnlCompany.add(txtCompany, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlProj = new JPanel(new BorderLayout(3, 3));
							pnlUploading.add(pnlProj);
							{
								lookupProject = new _JLookup();
								pnlProj.add(lookupProject, BorderLayout.WEST);
								lookupProject.setPreferredSize(new Dimension(50, 100));
								lookupProject.setReturnColumn(0);
								lookupProject.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String proj_name = (String) data[1];
											txtProject.setText(proj_name);
										}
									}
								});
							}
							
							{
								txtProject = new _JXTextField();
								pnlProj.add(txtProject, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlPhase = new JPanel(new BorderLayout(3, 3));
							pnlUploading.add(pnlPhase);
							{
								lookupPhase = new _JLookup();
								pnlPhase.add(lookupPhase, BorderLayout.WEST);
								lookupPhase.setPreferredSize(new Dimension(50, 100));
								lookupPhase.setReturnColumn(0);
								lookupPhase.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											String phase = (String) data[1];
											txtPhase.setText(phase);
										}
									}
								});
							}
							
							{
								txtPhase = new _JXTextField();
								pnlPhase.add(txtPhase, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlEPEBType = new JPanel(new BorderLayout(3, 3));
							pnlUploading.add(pnlEPEBType);
							{
								cmbEPEBType = new JComboBox(new String[] {"Mortgage", "Sale"});
								pnlEPEBType.add(cmbEPEBType, BorderLayout.WEST);
								cmbEPEBType.setPreferredSize(new Dimension(150, 0));
								
							}
						}
						{
							JPanel pnlBatch = new JPanel(new BorderLayout(3, 3));
							pnlUploading.add(pnlBatch);
							{
								txtBatch = new _JXTextField();
								pnlBatch.add(txtBatch, BorderLayout.WEST);
								txtBatch.setPreferredSize(new Dimension(150, 0));
								
							}
							{
								JPanel pnlJVNo = new JPanel(new BorderLayout(3, 3));
								pnlBatch.add(pnlJVNo, BorderLayout.EAST);
								pnlJVNo.setPreferredSize(new Dimension(200, 0));
								{
									JLabel lblJVNo = new JLabel("JV No");
									pnlJVNo.add(lblJVNo, BorderLayout.WEST);
									lblJVNo.setPreferredSize(new Dimension(100, 0));
								}
								{
									txtJVNo = new _JXTextField();
									pnlJVNo.add(txtJVNo, BorderLayout.CENTER);
								}
							}
						}
						
						
						
						
					}
					// File Upload Buttons
				}
				{
					btnGenerate = new JButton("Generate");
					pnlNorth.add(btnGenerate, BorderLayout.EAST);
					btnGenerate.setActionCommand("Generate");
					btnGenerate.setMnemonic(KeyEvent.VK_R);
					btnGenerate.addActionListener(this);
					btnGenerate.setPreferredSize(new java.awt.Dimension(200, 40));
				}
				{
					pnlCenter = new JPanel();
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					pnlCenter.setLayout(new BorderLayout(5, 5));
					pnlCenter.setBorder(lineBorder);
					{
						tabCenter = new JTabbedPane();
						pnlCenter.add(tabCenter, BorderLayout.CENTER);
						//displayGarbageFeeForIssuance();
						
						{
							JPanel pnlForIssuance = new JPanel(new BorderLayout(3, 3));
							tabCenter.addTab("For Issuance", pnlForIssuance);
							{
								scrollG2GTcostTagging = new JScrollPane();
								pnlForIssuance.add(scrollG2GTcostTagging, BorderLayout.CENTER);
								scrollG2GTcostTagging.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
								scrollG2GTcostTagging.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
								scrollG2GTcostTagging.setBorder(BorderFactory.createLineBorder(Color.GRAY));
								{
									model_garbage_fee_for_issuance = new model_garbage_fee_for_issuance();
									tbl_garbage_fee_for_isuance = new _JTableMain(model_garbage_fee_for_issuance);
									row_header_garbage_fee_for_issuance = tbl_garbage_fee_for_isuance.getRowHeader();
									scrollG2GTcostTagging.setViewportView(tbl_garbage_fee_for_isuance);
									
									
								}
								{
									row_header_garbage_fee_for_issuance = tbl_garbage_fee_for_isuance.getRowHeader();
									row_header_garbage_fee_for_issuance.setModel(new DefaultListModel());
									scrollG2GTcostTagging.setRowHeaderView(row_header_garbage_fee_for_issuance);
									scrollG2GTcostTagging.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
					}
				}
				{
					pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
					// pnlSouth.setBorder(lineBorder);
					pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX
					{
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
					}
					{
						btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview);
						btnPreview.setActionCommand("Preview");
						btnPreview.setMnemonic(KeyEvent.VK_V);
						btnPreview.addActionListener(this);
					}
					{
						btnImport = new JButton("Issue");
						pnlSouth.add(btnImport);
						btnImport.setActionCommand("Issue");
						btnImport.setMnemonic(KeyEvent.VK_I);
						btnImport.addActionListener(this);
					}
				}
			}
		}

	}

}
