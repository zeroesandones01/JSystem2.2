package Reports.Accounting;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class PDCWarehousing_Reports extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = 6361342136929135969L;
	static String title = "PDC Warehousing Reports";
	Dimension frameSize = new Dimension(500, 367);
	
	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtPhaseID;
	private _JLookup txtBranchID;
	
	private JTextField txtCo;
	private JTextField txtPro;
	private JTextField txtPhase;
	private JTextField txtBranch;
	
	private JComboBox cboRep;
	
	private JButton btnPreview;
	private JButton btnExportXLS;
	private JButton btnExportPDF;
	
	private _JDateChooser dteFrom;
	private _JDateChooser dteTo;
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public PDCWarehousing_Reports() {
		super(title, false, true, false, false);
		initGUI(); 
	}

	public PDCWarehousing_Reports(String title) {
		super(title);
		initGUI(); 
	}

	public PDCWarehousing_Reports(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(frameSize);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					JXPanel panMainParams = new JXPanel(new GridLayout(4, 1, 5, 5));
					panCenter.add(panMainParams, BorderLayout.PAGE_START);
					panMainParams.setPreferredSize(new Dimension(0, 159));
					panMainParams.setBorder(JTBorderFactory.createTitleBorder("Project, Company and Phase", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panCom = new JXPanel(new BorderLayout(5, 5));
						panMainParams.add(panCom, BorderLayout.CENTER);
						{
							JXPanel panComLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
							panCom.add(panComLabel, BorderLayout.LINE_START);
							panComLabel.setPreferredSize(new Dimension(150, 0));
							{
								{
									JLabel lblCo = new JLabel("Company");
									panComLabel.add(lblCo, BorderLayout.CENTER);
									lblCo.setHorizontalAlignment(JTextField.LEADING);
								}
								{
									txtCoID = new _JLookup();
									panComLabel.add(txtCoID, BorderLayout.CENTER);
									txtCoID.setReturnColumn(0);
									txtCoID.setLookupSQL(lookupMethods.getCompany());
									txtCoID.setHorizontalAlignment(JTextField.CENTER);
									txtCoID.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											
											if (data!=null) {
												txtCo.setText(data[1].toString());
											}
											
											txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));
										}
									});
									txtCoID.addKeyListener(new KeyListener() {
										public void keyTyped(KeyEvent e) {
											if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
												txtCoID.setValue("");
												txtCo.setText("All Companies");
											}
										}
										
										public void keyReleased(KeyEvent arg0) {
											
										}

										public void keyPressed(KeyEvent arg0) {
											
										}
									});
								}
							}
							{
								txtCo = new JTextField("");
								panCom.add(txtCo, BorderLayout.CENTER);
								txtCo.setHorizontalAlignment(JTextField.CENTER);
								txtCo.setEditable(false);
							}
						}
						JXPanel panProject = new JXPanel(new BorderLayout(5, 5));
						panMainParams.add(panProject, BorderLayout.CENTER);
						{
							JXPanel panProLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
							panProject.add(panProLabel, BorderLayout.LINE_START);
							panProLabel.setPreferredSize(new Dimension(150, 0));
							{
								JLabel lblPro = new JLabel("Project");
								panProLabel.add(lblPro, BorderLayout.LINE_START);
								lblPro.setHorizontalTextPosition(JTextField.LEADING);
								lblPro.setPreferredSize(new Dimension(75, 0));
							}
							{
								txtProID = new _JLookup();
								panProLabel.add(txtProID, BorderLayout.CENTER);
								txtProID.setReturnColumn(0);
								txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));
								txtProID.setHorizontalAlignment(JTextField.CENTER);
								txtProID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										
										if (data!=null) {
											txtPro.setText(data[1].toString());
										}
										
										txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
									}
								});
								txtProID.addKeyListener(new KeyListener() {
									public void keyTyped(KeyEvent e) {
										if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
											txtProID.setValue("");
											txtPro.setText("All Projects");
										}
									}
									
									public void keyReleased(KeyEvent e) {
										
									}
									
									public void keyPressed(KeyEvent e) {
										
									}
								});
							}
							{
								txtPro = new JTextField("");
								panProject.add(txtPro, BorderLayout.CENTER);
								txtPro.setHorizontalAlignment(JTextField.CENTER);
								txtPro.setEditable(false);
							}
						}
						JXPanel panPhase = new JXPanel(new BorderLayout(5, 5));
						panMainParams.add(panPhase, BorderLayout.CENTER);
						{
							JXPanel panPhLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
							panPhase.add(panPhLabel, BorderLayout.LINE_START);
							panPhLabel.setPreferredSize(new Dimension(150, 0));
							{
								JLabel lblPhase = new JLabel("Phase");
								panPhLabel.add(lblPhase, BorderLayout.LINE_START);
								lblPhase.setHorizontalTextPosition(JTextField.LEADING);
								lblPhase.setPreferredSize(new Dimension(75, 0));
							}
							{
								txtPhaseID = new _JLookup();
								panPhLabel.add(txtPhaseID, BorderLayout.CENTER);
								txtPhaseID.setReturnColumn(0);
								txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
								txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
								txtPhaseID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data!=null) {
											txtPhase.setText(data[1].toString());
										}
									}
								});
								txtPhaseID.addKeyListener(new KeyListener() {
									public void keyTyped(KeyEvent e) {
										if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
											txtPhaseID.setValue("");
											txtPhase.setText("All Phase");
										}
									}
									
									public void keyReleased(KeyEvent e) {
										
									}
									
									public void keyPressed(KeyEvent e) {
										
									}
								});
							}
							{
								txtPhase = new JTextField("");
								panPhase.add(txtPhase, BorderLayout.CENTER);
								txtPhase.setHorizontalAlignment(JTextField.CENTER);
								txtPhase.setEditable(false);
							}
						}
						JXPanel panBranch = new JXPanel(new BorderLayout(5, 5));
						panMainParams.add(panBranch, BorderLayout.CENTER);
						{
							JXPanel panBranchLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
							panBranch.add(panBranchLabel, BorderLayout.LINE_START);
							panBranchLabel.setPreferredSize(new Dimension(150, 0));
							{
								JLabel lblBranch = new JLabel("Branch");
								panBranchLabel.add(lblBranch, BorderLayout.LINE_START);
								lblBranch.setHorizontalTextPosition(JTextField.LEADING);
								lblBranch.setPreferredSize(new Dimension(75, 0));
							}
							{
								txtBranchID = new _JLookup();
								panBranchLabel.add(txtBranchID, BorderLayout.CENTER);
								txtBranchID.setReturnColumn(0);
								txtBranchID.setLookupSQL(lookupMethods.getOfficeBranch());
								txtBranchID.setHorizontalAlignment(JTextField.CENTER);
								txtBranchID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data!=null) {
											txtBranch.setText(data[1].toString());
										}
									}
								});
								txtBranchID.addKeyListener(new KeyListener() {
									public void keyTyped(KeyEvent e) {
										if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
											txtBranchID.setValue("");
											txtBranch.setText("All Branches");
										}
									}
									
									public void keyReleased(KeyEvent e) {
										
									}
									
									public void keyPressed(KeyEvent e) {
										
									}
								});
							}
							{
								txtBranch = new JTextField("");
								panBranch.add(txtBranch, BorderLayout.CENTER);
								txtBranch.setHorizontalAlignment(JTextField.CENTER);
								txtBranch.setEditable(false);
							}
						}
					}
					JXPanel panRepDate = new JXPanel(new GridLayout(2, 1, 5, 5));
					panCenter.add(panRepDate, BorderLayout.CENTER);
					{
						JXPanel panRep = new JXPanel(new BorderLayout(5, 5));
						panRepDate.add(panRep, BorderLayout.CENTER);
						panRep.setBorder(JTBorderFactory.createTitleBorder("Report Type", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								cboRep = new JComboBox(new String[] {
										"PDC Warehousing due for the day"
								});

								panRep.add(cboRep, BorderLayout.CENTER);
								cboRep.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent arg0) {

									}
								});
							}	
						}
					}
					{
						JXPanel panDateMode = new JXPanel(new GridLayout(1, 2, 5, 5));
						panRepDate.add(panDateMode, BorderLayout.CENTER);
						panDateMode.setBorder(JTBorderFactory.createTitleBorder("Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JXPanel panFrom = new JXPanel(new BorderLayout(5, 5));
								panDateMode.add(panFrom, BorderLayout.CENTER);
								{
									{
										JLabel lblFrom = new JLabel("From");
										panFrom.add(lblFrom, BorderLayout.LINE_START);
										lblFrom.setPreferredSize(new Dimension(50, 0));
									}
									{
										dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										panFrom.add(dteFrom, BorderLayout.CENTER);
										dteFrom.getCalendarButton().setVisible(true);
										dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									}
								}
							}
							{
								JXPanel panTo = new JXPanel(new BorderLayout(5, 5));
								panDateMode.add(panTo, BorderLayout.CENTER);
								{
									{
										JLabel lblTo = new JLabel("To");
										panTo.add(lblTo, BorderLayout.LINE_START);
										lblTo.setPreferredSize(new Dimension(50, 0));
										lblTo.setHorizontalAlignment(JTextField.CENTER);
									}
									{
										dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										panTo.add(dteTo, BorderLayout.CENTER);
										dteTo.getCalendarButton().setVisible(true);
										dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									}
								}	
							}
						}
					}
				}
			}
			{
				JXPanel panEnd = new JXPanel(new GridLayout(1, 3, 5, 5));
				panMain.add(panEnd, BorderLayout.PAGE_END);
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnPreview = new JButton("Preview");
						panEnd.add(btnPreview, BorderLayout.CENTER);
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								String strDate = "";
								
								if (dteFrom.getDate().equals(dteTo.getDate())) {
									strDate = df.format(dteFrom.getDate());
								} else {
									strDate = df.format(dteFrom.getDate()).toString() + " to " + df.format(dteTo.getDate()).toString();  
								}
								
								Map<String, Object> mapParameters = new HashMap<String, Object>();
								mapParameters.put("01_Company", txtCo.getText());
								mapParameters.put("02_CoID", txtCoID.getText());
								mapParameters.put("03_ProID", txtProID.getValue());
								mapParameters.put("04_Project", txtPro.getText());
								mapParameters.put("05_User", UserInfo.FullName);
								mapParameters.put("07_phase", txtPhaseID.getValue());
								mapParameters.put("08_dateFrom", dteFrom.getDate());
								mapParameters.put("09_dateTo", dteTo.getDate());
								mapParameters.put("10_AsOfDate", strDate);
								mapParameters.put("11_branch", txtBranchID.getValue()); 
								FncReport.generateReport("/Reports/rpt_pdcwarehousing_due.jasper", "PDC Warehousing Due", "", mapParameters);
							}
						});
						
					}
					{
						btnExportXLS = new JButton("Export to XLS");
						panEnd.add(btnExportXLS, BorderLayout.CENTER);
						btnExportXLS.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
							}
						});
						btnExportXLS.setEnabled(false);
					}
					{
						btnExportPDF = new JButton("Export to PDF");
						panEnd.add(btnExportPDF, BorderLayout.CENTER);
						btnExportPDF.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								
							}
						});
						btnExportPDF.setEnabled(false);
					}
				}
			}
		}

		setDefaults();
	}

	private void setDefaults() {
		txtCoID.setValue("02");
		txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");

		txtProID.setValue("015");
		txtPro.setText("TERRAVERDE RESIDENCES");	
		
		txtBranchID.setValue("");
		txtBranch.setText("");
		
		txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
	}
}
