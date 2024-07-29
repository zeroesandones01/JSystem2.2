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
	JPanel pnlFileUploading;

	JButton btnProcess;
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

	JScrollPane scrollGarbageFee;

	JList rowheaderGarbageFee;

	JFileChooser fileChooser;

	DefaultTableModel modelGarbageFee;
	
	private model_garbage_fee_for_issuance model_garbage_fee_for_issuance;
	JList row_header_garbage_fee_for_issuance;

	_JLookup lookupProj;
	JTextField txtProj;
	
	_JLookup lookupBatch;
	JTextField txtBatch;

	String pbl_id = null;

	protected Boolean pressedShift = false;

	static String title = "Garbage Fee";
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
				pnlNorth.setPreferredSize(new java.awt.Dimension(1000, 80));
				{
					pnlDate = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlDate, BorderLayout.WEST);
					pnlDate.setBorder(JTBorderFactory.createTitleBorder("Transaction Date"));
					pnlDate.setPreferredSize(new java.awt.Dimension(197, 80));
					{
						pnllblDateTo = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlDate.add(pnllblDateTo, BorderLayout.WEST);
						{
							lblDateFr = new JLabel("From:");
							pnllblDateTo.add(lblDateFr);
						}
						{
							lblDateTo = new JLabel("To:");
							pnllblDateTo.add(lblDateTo);
						}
						
						{
							lblForIssuanceCount = new JLabel("0");
							pnllblDateTo.add(lblForIssuanceCount);
						}
						
					}
					{
						pnlDateTo = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlDate.add(pnlDateTo, BorderLayout.CENTER);
						{
							dateFr = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlDateTo.add(dateFr);
							dateFr.setDate(FncGlobal.getDateToday());
							dateFr.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor) dateFr.getDateEditor()).setEditable(false);
							

						}
						{
							dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnlDateTo.add(dateTo);
							dateTo.setDate(FncGlobal.getDateToday());
							dateTo.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor) dateTo.getDateEditor()).setEditable(false);
							dateTo.addDateListener(new DateListener() {
								
								@Override
								public void datePerformed(DateEvent event) {
								}
							});
						}
						{
							JPanel pnlRemaining = new JPanel(new BorderLayout(5, 5));
							lblForIssuanceDetails = new JLabel(" Remaining");
							pnlRemaining.add(lblForIssuanceDetails, BorderLayout.WEST);
							btnViewRemaining = new JButton("View");
							pnlRemaining.add(btnViewRemaining, BorderLayout.CENTER);
							
							btnViewRemaining.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									String date_fr = dateFr.getDateString();
									String date_to = dateTo.getDateString();
									int confirmation = JOptionPane.showConfirmDialog(null,
											"View Pending " + count +" Garbage ?");
									if (confirmation == 0) {
										System.out.println("Preview");
										Map<String, Object> mapParameters = new HashMap<String, Object>();
//										System.out.println("p_date: "+date);
										FncReport.generateReport("/Reports/rpt_for_issuance_garbage_fee.jasper", "Pending Garbage Fee", "", mapParameters);
									} else {
										System.out.println("Cancel");
									}
								}
							});
							
							pnlDateTo.add(pnlRemaining);
						}
						
					}
					{
						pnlFileUploading = new JPanel(new BorderLayout(5, 5));
						pnlNorth.add(pnlFileUploading, BorderLayout.CENTER);
						pnlNorth.setPreferredSize(new Dimension(0, 100));
						pnlFileUploading.setBorder(JTBorderFactory.createTitleBorder("Upload File"));
						pnlFileUploading.setPreferredSize(new java.awt.Dimension(504, 80));
						
						// File Upload Labels
						JPanel pnlLabels = new JPanel();
						{
							pnlLabels = new JPanel(new GridLayout(3, 1, 10, 3));
							pnlFileUploading.add(pnlLabels, BorderLayout.WEST);
							pnlLabels.add(new JLabel("Project ID"));
							pnlLabels.add(new JLabel("Select File"));
							pnlLabels.add(new JLabel("Select Batch"));
						}
						
						// File Upload Center
						{
							pnlUploading = new JPanel(new GridLayout(3, 1, 10, 3));
							pnlFileUploading.add(pnlUploading, BorderLayout.CENTER);
							
							// Project
							{
								JPanel pnlProj = new JPanel(new BorderLayout(5, 5));
								pnlUploading.add(pnlProj);
								
								{
									lookupProj = new _JLookup();
									pnlProj.add(lookupProj, BorderLayout.WEST);
									lookupProj.setPreferredSize(new Dimension(50, 100));
									lookupProj.setReturnColumn(0);
									lookupProj.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												String proj_id = (String) data[0];
												String proj_name = (String) data[1];
												lookupProj.setText(proj_id);
												txtProj.setText(proj_name);
											}

										}
									});
								}
								{
									txtProj = new JTextField();
									pnlProj.add(txtProj, BorderLayout.CENTER);
								}
								{
									tagSelectFile = new JXTextField();
									pnlUploading.add(tagSelectFile);
									tagSelectFile.setEditable(false);
									tagSelectFile.setPreferredSize(new java.awt.Dimension(415, 0));
								}
							}
							
							// Batch
							{
								JPanel pnlBatch = new JPanel(new BorderLayout(5, 5));
								pnlUploading.add(pnlBatch);
							
								{
									txtBatch = new JTextField();
									pnlBatch.add(txtBatch, BorderLayout.CENTER);
								}
							}
						}
						
						// File Upload Buttons
						{
							pnlSelect = new JPanel(new GridLayout(3, 1, 10, 3));
							pnlFileUploading.add(pnlSelect, BorderLayout.EAST);
							{
								pnlSelect.add(Box.createVerticalBox());

							}
							{
								JButton btnSelectFile = new JButton("Search");
								pnlSelect.add(btnSelectFile);
								btnSelectFile.setActionCommand("Search");
								btnSelectFile.addActionListener(this);
								btnSelectFile.setPreferredSize(new java.awt.Dimension(65, 0));
							}
							{
								JButton btnSelectFile = new JButton("View");
								pnlSelect.add(btnSelectFile);
								btnSelectFile.setActionCommand("previewBatch");
								btnSelectFile.addActionListener(this);
								btnSelectFile.setPreferredSize(new java.awt.Dimension(65, 0));
							}
						}
					}
					{
						btnProcess = new JButton("Process");
						pnlNorth.add(btnProcess, BorderLayout.EAST);
						btnProcess.setActionCommand("Process");
						btnProcess.setMnemonic(KeyEvent.VK_R);
						btnProcess.addActionListener(this);
						btnProcess.setPreferredSize(new java.awt.Dimension(80, 40));
					}
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
								scrollGarbageFee = new JScrollPane();
								pnlForIssuance.add(scrollGarbageFee, BorderLayout.CENTER);
								scrollGarbageFee.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
								scrollGarbageFee.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
								scrollGarbageFee.setBorder(BorderFactory.createLineBorder(Color.GRAY));
								{
									model_garbage_fee_for_issuance = new model_garbage_fee_for_issuance();
									tbl_garbage_fee_for_isuance = new _JTableMain(model_garbage_fee_for_issuance);
									row_header_garbage_fee_for_issuance = tbl_garbage_fee_for_isuance.getRowHeader();
									scrollGarbageFee.setViewportView(tbl_garbage_fee_for_isuance);
									
									
								}
								{
									row_header_garbage_fee_for_issuance = tbl_garbage_fee_for_isuance.getRowHeader();
									row_header_garbage_fee_for_issuance.setModel(new DefaultListModel());
									scrollGarbageFee.setRowHeaderView(row_header_garbage_fee_for_issuance);
									scrollGarbageFee.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
							}
						}
						
						
						
						tabCenter.addTab("Garbage Fee", null, new JScrollPane(tblGarbageFee), null);
						System.out.println("Current tab index: "+tabCenter.getSelectedIndex());
						
						tabCenter.addChangeListener(new ChangeListener() {
							
							@Override
							public void stateChanged(ChangeEvent e) {
								System.out.println(tabCenter.getSelectedIndex());
								{
									if(tabCenter.getSelectedIndex() == 0) {
										btnImport.setText("Issue");
										pnlSouth.add(btnImport);
										btnImport.setActionCommand("Issue");
										btnImport.setMnemonic(KeyEvent.VK_I);
									}if(tabCenter.getSelectedIndex() == 1) {
										btnImport.setText("Import");
										pnlSouth.add(btnImport);
										btnImport.setActionCommand("Import");
										btnImport.setMnemonic(KeyEvent.VK_I);
									}
								}
								pnlSouth.repaint();
							}
						});
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
