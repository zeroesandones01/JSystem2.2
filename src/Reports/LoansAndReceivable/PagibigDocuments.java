package Reports.LoansAndReceivable;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;

import tablemodel.model_lrmdReports;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;

public class PagibigDocuments extends _JInternalFrame implements
ActionListener, _GUI {

	private static final long serialVersionUID = -5536131520425866847L;
	static String title = "PAGIBIG Documents (SCD)";
	Dimension frameSize = new Dimension(820, 605);
	static Border line = BorderFactory.createLineBorder(Color.RED);

	private JLabel lblName;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSeq;

	private JLabel lblDocNo;
	private JLabel lblPageNo;
	private JLabel lblBookNo;
	private JLabel lblSeriesNo;
	private JLabel lblAtty;
	private JLabel lblCo;
	private JLabel lblSpouse;

	static JTextField txtProjID;
	static JTextField txtUnitID;
	static JTextField txtName;
	static JTextField txtProject;
	static JTextField txtUnit;
	static JTextField txtSeq;
	static JTextField txtDocument;
	static JTextField txtCo;
	static JTextField txtCoName;
	static JTextField txtSpouse;
	static JTextField txtSpouseName;

	static JTextField txtDocNo;
	static JTextField txtPageNo;
	static JTextField txtBookNo;
	static JTextField txtSeriesNo;

	static JComboBox txtAtty;

	static _JLookup txtID;
	static _JLookup txtDocID;

	private JXPanel pnlTab1;
	private JXPanel pnlTab2;
	private JXPanel pnlTab3;

	private JXPanel panParamDOA;

	private JScrollPane scrDoc;
	private model_lrmdReports modelDoc;	
	public static _JTableMain tblDoc;
	public static JList rowDoc;

	private JScrollPane scrollRate;
	//private model_pagibig_rate modelRate;	
	private _JTableMain tblRate;
	private JList rowRate;
	static JTextField txtProj;
	static JXFormattedTextField txtRate;
	static String rateRecId = "";
	static _JLookup lookProj;

	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnPreview;
	private JButton btnOkay;

	private _JDateChooser dteDate;

	private _JTabbedPane tabDocs;

	private Boolean blnState = true;
	private Boolean blnAdd = true;

	private JLabel lblDocNo_cts;
	private JLabel lblPageNo_cts;
	private JLabel lblBookNo_cts;
	private JLabel lblSeriesNo_cts;
	private JLabel lblAtty_cts;

	static JTextField txtDocNo_cts;
	static JTextField txtPageNo_cts;
	static JTextField txtBookNo_cts;
	static JTextField txtSeriesNo_cts;
	static JTextField txtAtty_cts;
	private _JDateChooser dteDate_cts;

	String strDocNo;
	String strPageNo;
	String strBookNo;
	String strSeriesNo;
	String strAtty;
	String strParamDate;

	private JComboBox cboPrinter; 

	static JTextField txtPlanNo;
	static JTextField txtLRC;
	static JTextArea txtPortion;
	static JTextArea txtTechDesc; 

	public PagibigDocuments() {
		super(title, true, true, true, true);
		initGUI();
	}

	public PagibigDocuments(String title) {
		super(title);
		initGUI();
	}

	public PagibigDocuments(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
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
			JXPanel pnlCenter = new JXPanel(new GridBagLayout());
			panMain.add(pnlCenter,BorderLayout.CENTER);
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;
				{
					gbc.weightx = 1;
					gbc.weighty = 1;

					gbc.gridx = 0;
					gbc.gridy = 0;

					JXPanel panClient = new JXPanel(new GridBagLayout());
					pnlCenter.add(panClient,gbc);
					panClient.setBorder(JTBorderFactory.createTitleBorder("Client Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagOne = new GridBagConstraints();
						bagOne.fill = GridBagConstraints.BOTH;
						bagOne.insets = new Insets(5,3,5,3);
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 0;

							lblName = new JLabel("Name");
							panClient.add(lblName,bagOne);
							lblName.setHorizontalAlignment(JTextField.LEADING);
							lblName.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 0;

							txtID = new _JLookup("");
							panClient.add(txtID,bagOne);
							txtID.setHorizontalAlignment(JTextField.CENTER);
							txtID.setReturnColumn(0);
							txtID.setLookupSQL(Client());
							txtID.setFilterName(true);
							txtID.setFont(FncGlobal.sizeTextValue);
							txtID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									try {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											txtName.setText(data[1].toString());
											txtProjID.setText(data[4].toString());
											txtProject.setText(data[2].toString());
											txtUnitID.setText(data[5].toString());
											txtUnit.setText(data[3].toString());
											txtSeq.setText(data[6].toString());

											System.out.println("");
											System.out.println("txtID.getValue(): " + txtID.getValue());  
										}
									} catch (NullPointerException ex) {
										JOptionPane.showMessageDialog(getContentPane(), "Null pointer exception caught", "Warning", JOptionPane.ERROR_MESSAGE);
									}

									RetrieveCo(txtID.getValue());
									RetrieveSpouse(txtID.getValue());
									RetrieveNotarialEntries(txtID.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText()); 
								}
							});
						}
						{
							bagOne.weightx = 1.25;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 0;
							bagOne.gridwidth = 3;

							txtName = new JTextField("");
							panClient.add(txtName,bagOne);
							txtName.setHorizontalAlignment(JTextField.CENTER);
							txtName.setEditable(false);
							txtName.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 1;
							bagOne.gridwidth = 1;

							lblProject = new JLabel("Project");
							panClient.add(lblProject,bagOne);
							lblProject.setHorizontalAlignment(JTextField.LEADING);
							lblProject.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 1;


							txtProjID = new JTextField("");
							panClient.add(txtProjID,bagOne);
							txtProjID.setHorizontalAlignment(JTextField.CENTER);
							txtProjID.setEditable(false);
							txtProjID.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 1.25;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 1;
							bagOne.gridwidth = 3;

							txtProject = new JTextField("");
							panClient.add(txtProject,bagOne);
							txtProject.setHorizontalAlignment(JTextField.CENTER);
							txtProject.setEditable(false);
							txtProject.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 2;
							bagOne.gridwidth = 1;

							lblUnit = new JLabel("Unit");
							panClient.add(lblUnit,bagOne);
							lblUnit.setHorizontalAlignment(JTextField.LEADING);
							lblUnit.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 2;

							txtUnitID = new JTextField("");
							panClient.add(txtUnitID,bagOne);
							txtUnitID.setHorizontalAlignment(JTextField.CENTER);
							txtUnitID.setEditable(false);
							txtUnitID.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0.25;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 2;

							txtUnit = new JTextField("");
							panClient.add(txtUnit,bagOne);
							txtUnit.setHorizontalAlignment(JTextField.CENTER);
							txtUnit.setHorizontalAlignment(JTextField.CENTER);
							txtUnit.setEditable(false);
							txtUnit.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 3;
							bagOne.gridy = 2;

							lblSeq = new JLabel("Sequence");
							panClient.add(lblSeq,bagOne);
							lblSeq.setHorizontalAlignment(JTextField.CENTER);
							lblSeq.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 4;
							bagOne.gridy = 2;

							txtSeq = new JTextField("");
							panClient.add(txtSeq,bagOne );
							txtSeq.setPreferredSize(new Dimension(50, 0));
							txtSeq.setHorizontalAlignment(JTextField.CENTER);
							txtSeq.setEditable(false);
							txtSeq.setFont(FncGlobal.sizeTextValue);
						}
					}
				}
				{
					gbc.weightx = 1;
					gbc.weighty = 1;

					gbc.gridx = 0;
					gbc.gridy = 1;

					JXPanel panCo = new JXPanel(new GridBagLayout());
					pnlCenter.add(panCo,gbc);
					panCo.setBorder(JTBorderFactory.createTitleBorder("Connections", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagTwo = new GridBagConstraints();
						bagTwo.fill = GridBagConstraints.BOTH;
						bagTwo.insets = new Insets(5,3,5,3);
						{
							bagTwo.weightx = 0;
							bagTwo.weighty= 1;

							bagTwo.gridx = 0;
							bagTwo.gridy = 0;

							lblCo = new JLabel("Co-Borrower");
							panCo.add(lblCo,bagTwo);
							lblCo.setHorizontalAlignment(JTextField.LEADING);
							lblCo.setFont(FncGlobal.sizeLabel);

						}
						{
							bagTwo.weightx = 0.5;
							bagTwo.weighty= 1;

							bagTwo.gridx = 1;
							bagTwo.gridy = 0;

							txtCo = new JTextField("");
							panCo.add(txtCo,bagTwo);
							txtCo.setHorizontalAlignment(JTextField.CENTER);
							txtCo.setEditable(false);
							txtCo.setFont(FncGlobal.sizeTextValue);

						}
						{
							bagTwo.weightx = 1.25;
							bagTwo.weighty= 1;

							bagTwo.gridx = 2;
							bagTwo.gridy = 0;

							txtCoName = new JTextField("");
							panCo.add(txtCoName,bagTwo);
							txtCoName.setHorizontalAlignment(JTextField.CENTER);
							txtCoName.setEditable(false);	
							txtCoName.setFont(FncGlobal.sizeTextValue);

						}
						{
							bagTwo.weightx = 0;
							bagTwo.weighty= 1;

							bagTwo.gridx = 0;
							bagTwo.gridy = 1;

							lblSpouse = new JLabel("Spouse");
							panCo.add(lblSpouse,bagTwo);
							lblSpouse.setHorizontalAlignment(JTextField.LEADING);
							lblSpouse.setFont(FncGlobal.sizeLabel);

						}
						{
							bagTwo.weightx = 0.5;
							bagTwo.weighty= 1;

							bagTwo.gridx = 1;
							bagTwo.gridy = 1;

							txtSpouse = new JTextField("");
							panCo.add(txtSpouse,bagTwo);
							txtSpouse.setHorizontalAlignment(JTextField.CENTER);
							txtSpouse.setEditable(false);
							txtSpouse.setFont(FncGlobal.sizeTextValue);

						}
						{
							bagTwo.weightx = 1.25;
							bagTwo.weighty= 1;

							bagTwo.gridx = 2;
							bagTwo.gridy = 1;

							txtSpouseName = new JTextField("");
							panCo.add(txtSpouseName, bagTwo);
							txtSpouseName.setHorizontalAlignment(JTextField.CENTER);
							txtSpouseName.setEditable(false);
							txtSpouseName.setFont(FncGlobal.sizeTextValue);

						}


					}
				}
				{
					gbc.weightx = 1;
					gbc.weighty = 1;

					gbc.gridx = 0;
					gbc.gridy = 2;
					gbc.gridheight = 3;


					JXPanel panCenter = new JXPanel(new GridBagLayout());
					pnlCenter.add(panCenter, gbc);
					{
						GridBagConstraints bagThree = new GridBagConstraints();
						bagThree.fill = GridBagConstraints.BOTH;
						bagThree.insets = new Insets(5,3,5,3);
						{
							bagThree.weightx = 5;
							bagThree.weighty = 3;

							bagThree.gridx = 0;
							bagThree.gridy = 0;

							CreateTable();
							tabDocs = new _JTabbedPane();
							panCenter.add(tabDocs, bagThree);
							{
								tabDocs.addTab("HDMF Reports", pnlTab1);
								tabDocs.addTab("Notarial Entries", pnlTab2);
								tabDocs.addTab("Pag-ibig Rates", pnlTab3);
								tabDocs.addTab("Technical Description", panTD());
							}
							tabDocs.addChangeListener(new ChangeListener() {
								public void stateChanged(ChangeEvent action) {
									btnPreview.setEnabled(tabDocs.getSelectedIndex()==0);

									if (blnState) {
										ButtonState(true, true, false, false);
									} else {
										ButtonState(false, false, true, true);
									}
									RetrieveNotarialEntries();
								}	
							});
							tabDocs.setFont(FncGlobal.sizeTextValue);
						}

					}

				}
			}



			{
				JXPanel panEnd = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panEnd, BorderLayout.PAGE_END);
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnPreview = new JButton("Preview");
						panEnd.add(btnPreview, BorderLayout.CENTER);
						btnPreview.setFont(FncGlobal.sizeControls);
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Boolean blnWithOtherLot = FncGlobal.GetBoolean("select exists(\n" + 
										"select y.lot \n" + 
										"from hs_sold_other_lots x \n" + 
										"inner join mf_unit_info y on x.oth_pbl_id = y.pbl_id \n" + 
										"INNER join (select sub.pbl_id, sub.unit_id from mf_unit_info sub) z on x.pbl_id = z.pbl_id\n" + 
										"where x.entity_id = '"+txtID.getValue()+"' and x.proj_id = '"+txtProjID.getText()+"' and x.pbl_id = '"+txtUnitID.getText()+"' and x.seq_no::int = '"+txtSeq.getText()+"'::int and x.status_id = 'A')"); 
								Boolean checkTrue = FncGlobal.GetBoolean("select exists(select * from rf_techdesc_breakdown where checked = true and pbl_id = '"+txtUnitID.getText()+"' )");

								if (cboPrinter.getSelectedIndex()==0) {
									for (int x = 0; x < modelDoc.getRowCount(); x++) {

										if ((Boolean) modelDoc.getValueAt(x, 0)) {
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CONTRACT TO SELL FLH240A")) {
												Map<String, Object> mapParameters2 = new HashMap<String, Object>();
												mapParameters2.put("01_clientID", txtID.getValue());
												mapParameters2.put("02_projID", txtProjID.getText());
												mapParameters2.put("03_pblID", txtUnitID.getText());
												mapParameters2.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_cts_flh240a_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_cts_flh240a_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);	
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CONTRACT TO SELL HQP-HLF-161 (V02, 06/2018)")) {
												Map<String, Object> mapParameters2 = new HashMap<String, Object>();
												mapParameters2.put("01_clientID", txtID.getValue());
												mapParameters2.put("02_projID", txtProjID.getText());
												mapParameters2.put("03_pblID", txtUnitID.getText());
												mapParameters2.put("04_seqno", txtSeq.getText());

												FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v02_062018_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v02_062018_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);	
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CONTRACT TO SELL HQP-HLF-161 (V03, 02/2019)")) {
												Map<String, Object> mapParameters2 = new HashMap<String, Object>();
												mapParameters2.put("01_clientID", txtID.getValue());
												mapParameters2.put("02_projID", txtProjID.getText());
												mapParameters2.put("03_pblID", txtUnitID.getText());
												mapParameters2.put("04_seqno", txtSeq.getText());
												System.out.println("client:"+txtID.getValue());
												System.out.println("ProjID:"+txtProjID.getText());
												System.out.println("PBLID:"+txtUnitID.getText());
												System.out.println("SEQNO:"+txtSeq.getText());

												FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v03_022019_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v03_022019_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);	
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CONTRACT TO SELL HQP-HLF-161 (V03, 02/2019) (SMALL)")) {
												Map<String, Object> mapParameters2 = new HashMap<String, Object>();
												mapParameters2.put("01_clientID", txtID.getValue());
												mapParameters2.put("02_projID", txtProjID.getText());
												mapParameters2.put("03_pblID", txtUnitID.getText());
												mapParameters2.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v03_022019_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v03_022019_small_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);	
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CONTRACT TO SELL HQP-HLF-161 (V04, 12/2020)")) {
												if(checkTrue) {
													Map<String, Object> mapParameters2 = new HashMap<String, Object>();
													mapParameters2.put("01_clientID", txtID.getValue());
													mapParameters2.put("02_projID", txtProjID.getText());
													mapParameters2.put("03_pblID", txtUnitID.getText());
													mapParameters2.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v03_022019_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);
//													
													if (txtProjID.getText().equals("018")) {
														FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v04_122020_page2_eve.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);
													}else {
														FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v04_122020_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);
													}
													Map<String, Object> mapParameters3 = new HashMap<String, Object>();
													mapParameters3.put("01_clientID", txtID.getValue());
													mapParameters3.put("02_projID", txtProjID.getText());
													mapParameters3.put("03_pblID", txtUnitID.getText());
													mapParameters3.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v04_122020_page5.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 5", "", mapParameters3);

													Map<String, Object> mapParameters4 = new HashMap<String, Object>();
													mapParameters4.put("01_clientID", txtID.getValue());
													mapParameters4.put("02_projID", txtProjID.getText());
													mapParameters4.put("03_pblID", txtUnitID.getText());
													mapParameters4.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v04_122020_page6.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 6", "", mapParameters4);


													Map<String, Object> mapParameters = new HashMap<String, Object>();
													mapParameters.put("01_clientID", txtID.getValue());
													mapParameters.put("02_projID", txtProjID.getText());
													mapParameters.put("03_pblID", txtUnitID.getText());
													mapParameters.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v04_122020_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);
												}
												else {
													JOptionPane.showMessageDialog(getContentPane(), "Technical description not yet set up","Information", JOptionPane.INFORMATION_MESSAGE);
												}

											}
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CONTRACT TO SELL HQP-HLF-161 (V05, 08/2023)")) {
												if(checkTrue) {
													Map<String, Object> mapParameters2 = new HashMap<String, Object>();
													mapParameters2.put("01_clientID", txtID.getValue());
													mapParameters2.put("02_projID", txtProjID.getText());
													mapParameters2.put("03_pblID", txtUnitID.getText());
													mapParameters2.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v05_082023_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);
													
													Map<String, Object> mapParameters3 = new HashMap<String, Object>();
													mapParameters3.put("01_clientID", txtID.getValue());
													mapParameters3.put("02_projID", txtProjID.getText());
													mapParameters3.put("03_pblID", txtUnitID.getText());
													mapParameters3.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v05_082023_page3.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 5", "", mapParameters3);

													Map<String, Object> mapParameters4 = new HashMap<String, Object>();
													mapParameters4.put("01_clientID", txtID.getValue());
													mapParameters4.put("02_projID", txtProjID.getText());
													mapParameters4.put("03_pblID", txtUnitID.getText());
													mapParameters4.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v05_082023_page4.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 6", "", mapParameters4);


													Map<String, Object> mapParameters = new HashMap<String, Object>();
													mapParameters.put("01_clientID", txtID.getValue());
													mapParameters.put("02_projID", txtProjID.getText());
													mapParameters.put("03_pblID", txtUnitID.getText());
													mapParameters.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v05_082023_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);
												}
						
												else {
													JOptionPane.showMessageDialog(getContentPane(), "Technical description not yet set up","Information", JOptionPane.INFORMATION_MESSAGE);
												}

											}
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CONTRACT TO SELL HQP-HLF-161 (V06, 12/2023)")) {
												if(checkTrue) {
													Map<String, Object> mapParameters2 = new HashMap<String, Object>();
													mapParameters2.put("01_clientID", txtID.getValue());
													mapParameters2.put("02_projID", txtProjID.getText());
													mapParameters2.put("03_pblID", txtUnitID.getText());
													mapParameters2.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v06_122023_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);
													
													Map<String, Object> mapParameters3 = new HashMap<String, Object>();
													mapParameters3.put("01_clientID", txtID.getValue());
													mapParameters3.put("02_projID", txtProjID.getText());
													mapParameters3.put("03_pblID", txtUnitID.getText());
													mapParameters3.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v06_122023_page3.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 5", "", mapParameters3);

													Map<String, Object> mapParameters4 = new HashMap<String, Object>();
													mapParameters4.put("01_clientID", txtID.getValue());
													mapParameters4.put("02_projID", txtProjID.getText());
													mapParameters4.put("03_pblID", txtUnitID.getText());
													mapParameters4.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v06_122023_page4.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 6", "", mapParameters4);


													Map<String, Object> mapParameters = new HashMap<String, Object>();
													mapParameters.put("01_clientID", txtID.getValue());
													mapParameters.put("02_projID", txtProjID.getText());
													mapParameters.put("03_pblID", txtUnitID.getText());
													mapParameters.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_cts_hqphlf161_v06_122023_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);
												}
												else {
													JOptionPane.showMessageDialog(getContentPane(), "Technical description not yet set up","Information", JOptionPane.INFORMATION_MESSAGE);
												}

											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("HOUSING LOAN APPLICATION HQP-HLF-068 (V05, 01/2017)")) {
												Map<String, Object> mapParameter2 = new HashMap<String, Object>();
												mapParameter2.put("01_clientID", txtID.getValue());
												mapParameter2.put("02_projID", txtProjID.getText());
												mapParameter2.put("03_pblID", txtUnitID.getText());
												mapParameter2.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf068a_v05_012017_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameter2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf068a_v05_012017_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);	
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("HOUSING LOAN APPLICATION HQP-HLF-068 (V07, 02/2018)")) {
												Map<String, Object> mapParameter2 = new HashMap<String, Object>();
												mapParameter2.put("01_clientID", txtID.getValue());
												mapParameter2.put("02_projID", txtProjID.getText());
												mapParameter2.put("03_pblID", txtUnitID.getText());
												mapParameter2.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf068a_v07_022018_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameter2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());	
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf068a_v07_022018_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("HOUSING LOAN APPLICATION HQP-HLF-068 (V08, 02/2020)")) {//added by erick dated 06/09/2020
												Map<String, Object> mapParameter2 = new HashMap<String, Object>();
												mapParameter2.put("01_clientID", txtID.getValue());
												mapParameter2.put("02_projID", txtProjID.getText());
												mapParameter2.put("03_pblID", txtUnitID.getText());
												mapParameter2.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf068a_v08_022020_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameter2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());	
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf068a_v08_022020_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("DEED OF ASSIGNMENT OF CONTRACT TO SELL FLH250-2A")) {
												int scanOption = JOptionPane.showOptionDialog(getContentPane(), panParamDOA, "Memorandum of Agreement",
														JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

												if (scanOption==JOptionPane.CLOSED_OPTION) {
													try {

													} catch (ArrayIndexOutOfBoundsException arrEx) {

													}			
												}

												Map<String, Object> mapParameters2 = new HashMap<String, Object>();
												mapParameters2.put("01_clientID", txtID.getValue());
												mapParameters2.put("02_projID", txtProjID.getText());
												mapParameters2.put("03_pblID", txtUnitID.getText());
												mapParameters2.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_doa_flh2502a_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												mapParameters.put("05_DocNo", strDocNo);
												mapParameters.put("06_PageNo", strPageNo);
												mapParameters.put("07_BookNo", strBookNo);
												mapParameters.put("08_SeriesNo", strSeriesNo);
												mapParameters.put("09_Atty", strAtty);
												mapParameters.put("10_Date", strParamDate);
												FncReport.generateReport("/Reports/rpt_hdmf_doa_flh2502a_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("DEED OF ASSIGNMENT OF CONTRACT TO SELL WITH SPECIAL POWER OF ATTORNEY HQP-HLF-524 (V02, 08/2018)")) {
												int scanOption = JOptionPane.showOptionDialog(getContentPane(), panParamDOA, "Memorandum of Agreement",
														JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

												if (scanOption==JOptionPane.CLOSED_OPTION) {
													try {

													} catch (ArrayIndexOutOfBoundsException arrEx) {

													}			
												}

												Map<String, Object> mapParameters2 = new HashMap<String, Object>();
												mapParameters2.put("01_clientID", txtID.getValue());
												mapParameters2.put("02_projID", txtProjID.getText());
												mapParameters2.put("03_pblID", txtUnitID.getText());
												mapParameters2.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v02082018_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												mapParameters.put("05_DocNo", strDocNo);
												mapParameters.put("06_PageNo", strPageNo);
												mapParameters.put("07_BookNo", strBookNo);
												mapParameters.put("08_SeriesNo", strSeriesNo);
												mapParameters.put("09_Atty", strAtty);
												mapParameters.put("10_Date", strParamDate);
												FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v02082018_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("DEED OF ASSIGNMENT OF CONTRACT TO SELL WITH SPECIAL POWER OF ATTORNEY HQP-HLF-524 (V02, 08/2018) (SMALL)")) {
												int scanOption = JOptionPane.showOptionDialog(getContentPane(), panParamDOA, "Memorandum of Agreement",
														JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

												if (scanOption==JOptionPane.CLOSED_OPTION) {
													try {

													} catch (ArrayIndexOutOfBoundsException arrEx) {

													}			
												}

												Map<String, Object> mapParameters2 = new HashMap<String, Object>();
												mapParameters2.put("01_clientID", txtID.getValue());
												mapParameters2.put("02_projID", txtProjID.getText());
												mapParameters2.put("03_pblID", txtUnitID.getText());
												mapParameters2.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v02082018_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												mapParameters.put("05_DocNo", strDocNo);
												mapParameters.put("06_PageNo", strPageNo);
												mapParameters.put("07_BookNo", strBookNo);
												mapParameters.put("08_SeriesNo", strSeriesNo);
												mapParameters.put("09_Atty", strAtty);
												mapParameters.put("10_Date", strParamDate);
												FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v02082018_small_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("DEED OF ASSIGNMENT OF CONTRACT TO SELL WITH SPECIAL POWER OF ATTORNEY HQP-HLF-524 (V02, 08/2018) (TD ADJUSTMENT)")) {
												int scanOption = JOptionPane.showOptionDialog(getContentPane(), panParamDOA, "Memorandum of Agreement",
														JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		
												if(checkTrue) {
													if (scanOption==JOptionPane.CLOSED_OPTION) {
														try {

														} catch (ArrayIndexOutOfBoundsException arrEx) {

														}			
													}

													Map<String, Object> mapParameters2 = new HashMap<String, Object>();
													mapParameters2.put("01_clientID", txtID.getValue());
													mapParameters2.put("02_projID", txtProjID.getText());
													mapParameters2.put("03_pblID", txtUnitID.getText());
													mapParameters2.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v02082018_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);
													FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v02082018_new_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);

													Map<String, Object> mapParameters = new HashMap<String, Object>();
													mapParameters.put("01_clientID", txtID.getValue());
													mapParameters.put("02_projID", txtProjID.getText());
													mapParameters.put("03_pblID", txtUnitID.getText());
													mapParameters.put("04_seqno", txtSeq.getText());
													mapParameters.put("05_DocNo", strDocNo);
													mapParameters.put("06_PageNo", strPageNo);
													mapParameters.put("07_BookNo", strBookNo);
													mapParameters.put("08_SeriesNo", strSeriesNo);
													mapParameters.put("09_Atty", strAtty);
													mapParameters.put("10_Date", strParamDate);
													//													FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v02082018_small_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);
													FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v02082018_new_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);

													Map<String, Object> mapParameters3 = new HashMap<String, Object>();
													mapParameters3.put("01_clientID", txtID.getValue());
													mapParameters3.put("02_projID", txtProjID.getText());
													mapParameters3.put("03_pblID", txtUnitID.getText());
													mapParameters3.put("04_seqno", txtSeq.getText());
													mapParameters3.put("05_DocNo", strDocNo);
													mapParameters3.put("06_PageNo", strPageNo);
													mapParameters3.put("07_BookNo", strBookNo);
													mapParameters3.put("08_SeriesNo", strSeriesNo);
													mapParameters3.put("09_Atty", strAtty);
													mapParameters3.put("10_Date", strParamDate);
													FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v02082018_new_page3.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 3", "", mapParameters3);

													Map<String, Object> mapParameters4 = new HashMap<String, Object>();
													mapParameters4.put("01_clientID", txtID.getValue());
													mapParameters4.put("02_projID", txtProjID.getText());
													mapParameters4.put("03_pblID", txtUnitID.getText());
													mapParameters4.put("04_seqno", txtSeq.getText());
													mapParameters4.put("05_DocNo", strDocNo);
													mapParameters4.put("06_PageNo", strPageNo);
													mapParameters4.put("07_BookNo", strBookNo);
													mapParameters4.put("08_SeriesNo", strSeriesNo);
													mapParameters4.put("09_Atty", strAtty);
													mapParameters4.put("10_Date", strParamDate);
													FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v02082018_new_page4.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 4", "", mapParameters4);    
												}
												else {
													JOptionPane.showMessageDialog(getContentPane(), "Technical description not yet set up","Information", JOptionPane.INFORMATION_MESSAGE);
												}

											}
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("DEED OF ASSIGNMENT OF CTS WITH SPA, HQP-HLF-524, (V03, 07/2022)")) {
												int scanOption = JOptionPane.showOptionDialog(getContentPane(), panParamDOA, "Memorandum of Agreement",
														JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		
												if(checkTrue) {
													if (scanOption==JOptionPane.CLOSED_OPTION) {
														try {

														} catch (ArrayIndexOutOfBoundsException arrEx) {

														}			
													}

													Map<String, Object> mapParameters2 = new HashMap<String, Object>();
													mapParameters2.put("01_clientID", txtID.getValue());
													mapParameters2.put("02_projID", txtProjID.getText());
													mapParameters2.put("03_pblID", txtUnitID.getText());
													mapParameters2.put("04_seqno", txtSeq.getText());
													FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v03072022_new_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameters2);

													Map<String, Object> mapParameters = new HashMap<String, Object>();
													mapParameters.put("01_clientID", txtID.getValue());
													mapParameters.put("02_projID", txtProjID.getText());
													mapParameters.put("03_pblID", txtUnitID.getText());
													mapParameters.put("04_seqno", txtSeq.getText());
													mapParameters.put("05_DocNo", strDocNo);
													mapParameters.put("06_PageNo", strPageNo);
													mapParameters.put("07_BookNo", strBookNo);
													mapParameters.put("08_SeriesNo", strSeriesNo);
													mapParameters.put("09_Atty", strAtty);
													mapParameters.put("10_Date", strParamDate);
													FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v03072022_new_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);

													Map<String, Object> mapParameters3 = new HashMap<String, Object>();
													mapParameters3.put("01_clientID", txtID.getValue());
													mapParameters3.put("02_projID", txtProjID.getText());
													mapParameters3.put("03_pblID", txtUnitID.getText());
													mapParameters3.put("04_seqno", txtSeq.getText());
													mapParameters3.put("05_DocNo", strDocNo);
													mapParameters3.put("06_PageNo", strPageNo);
													mapParameters3.put("07_BookNo", strBookNo);
													mapParameters3.put("08_SeriesNo", strSeriesNo);
													mapParameters3.put("09_Atty", strAtty);
													mapParameters3.put("10_Date", strParamDate);
													FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v03072022_new_page3.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 3", "", mapParameters3);

													Map<String, Object> mapParameters4 = new HashMap<String, Object>();
													mapParameters4.put("01_clientID", txtID.getValue());
													mapParameters4.put("02_projID", txtProjID.getText());
													mapParameters4.put("03_pblID", txtUnitID.getText());
													mapParameters4.put("04_seqno", txtSeq.getText());
													mapParameters4.put("05_DocNo", strDocNo);
													mapParameters4.put("06_PageNo", strPageNo);
													mapParameters4.put("07_BookNo", strBookNo);
													mapParameters4.put("08_SeriesNo", strSeriesNo);
													mapParameters4.put("09_Atty", strAtty);
													mapParameters4.put("10_Date", strParamDate);
													FncReport.generateReport("/Reports/rpt_hdmf_doa_hqphlf524_v03072022_new_page4.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 4", "", mapParameters4);    
												}
												else {
													JOptionPane.showMessageDialog(getContentPane(), "Technical description not yet set up","Information", JOptionPane.INFORMATION_MESSAGE);
												}

											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("ASSIGNMENT OF LOAN PROCEEDS")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_alp.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("HOUSING LOAN APPLICATION HQP-HLF-069 (V03, 01/2017)")) {
												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf069_v03_012017_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf069_v03_012017_page1.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 1", "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("HOUSING LOAN APPLICATION HQP-HLF-069 (V05, 02/2018)")) {
												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf069_v05_022018_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf069_v05_022018_page1.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 1", "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("HOUSING LOAN APPLICATION HQP-HLF-069 (V06, 02/2020)")) {
												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf069_v06_022020_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf069_v06_022020_page1.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 1", "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CERTIFICATE OF ACCEPTANCE")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_certificate_of_acceptance.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CERTIFICATE OF ACCEPTANCE HQP-HLF-083 (V02, 07/2017)") || (Boolean) modelDoc.getValueAt(x, 1).equals("CERTIFICATE OF ACCEPTANCE HQP-HLF-083 (V03, 08/2017)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_certificate_of_acceptance_hqphlf083_v02_072017.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("BORROWER`S CONFORMITY")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_borrower_conformity.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("BORROWER`S CONFORMITY V2")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_borrower_conformity_v2.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("PROMISSORY NOTE HQP-HLF-086 (CIRCULAR 310)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf086.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("PROMISSORY NOTE HQP-HLF-086 (V04, 10/2018)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf086_v04_102018.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("PROMISSORY NOTE HQP-HLF-086 (V05, 12/2020)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												//												FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf086_v04_102018.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
												if(txtProjID.getText().equals("018")) {
													FncReport.generateReport("/Reports/rpt_hdmf_pn_hqhlf087_v05_122020_eve.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
												}else {
												FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf086_v05_122020.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
												}
											}
											
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("PROMISSORY NOTE HQP-HLF-086 (V06, 02/2024)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												//FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf087.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											
													FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf086_v06_022024.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);	
											}	
											
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("LOAN AND MORTGAGE AGREEMENT HQP-HLF-162")) {
												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("LOAN AND MORTGAGE AGREEMENT HQP-HLF-162 (V05, 10/2018)")) {
												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v05_102018_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v05_102018_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("LOAN AND MORTGAGE AGREEMENT HQP-HLF-162 (V05, 10/2018) (SMALL)")) {
												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v05_102018_small_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v05_102018_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("LOAN AND MORTGAGE AGREEMENT HQP-HLF-162 (V02, 07/2016)")) {
												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v02_072016_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2 Version 2", "", mapParameters1);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v02_072016_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1 Version 2", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("LOAN AND MORTGAGE AGREEMENT HQP-HLF-162 (V06, 12/2020)")) {
												if(checkTrue){
													Map<String, Object> mapParameters1 = new HashMap<String, Object>();
													mapParameters1.put("01_clientID", txtID.getValue());
													mapParameters1.put("02_projID", txtProjID.getText());
													mapParameters1.put("03_pblID", txtUnitID.getText());
													mapParameters1.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v02_072016_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2 Version 2", "", mapParameters1);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v06_122020_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2 ", "", mapParameters1);

													Map<String, Object> mapParameters = new HashMap<String, Object>();
													mapParameters.put("01_clientID", txtID.getValue());
													mapParameters.put("02_projID", txtProjID.getText());
													mapParameters.put("03_pblID", txtUnitID.getText());
													mapParameters.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v02_072016_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1 Version 2", mapParameters);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v06_122020_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);

													Map<String, Object> mapParameters2 = new HashMap<String, Object>();
													mapParameters2.put("01_clientID", txtID.getValue());
													mapParameters2.put("02_projID", txtProjID.getText());
													mapParameters2.put("03_pblID", txtUnitID.getText());
													mapParameters2.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v02_072016_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1 Version 2", mapParameters);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v06_122020_page6.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 6 ", "", mapParameters2);
												}
												else {
													JOptionPane.showMessageDialog(getContentPane(), "Technical description not yet set up","Information", JOptionPane.INFORMATION_MESSAGE);
												}


											}
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("LOAN AND MORTGAGE AGREEMENT HQP-HLF-162 (V06, 12/2020)- ADJUSTED TECH")) {
												if(checkTrue){
													Map<String, Object> mapParameters1 = new HashMap<String, Object>();
													mapParameters1.put("01_clientID", txtID.getValue());
													mapParameters1.put("02_projID", txtProjID.getText());
													mapParameters1.put("03_pblID", txtUnitID.getText());
													mapParameters1.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v02_072016_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2 Version 2", "", mapParameters1);
													if(txtProjID.getText().equals("018")) {
														FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v06_122020_adjusted_page2_eve.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2 ", "", mapParameters1);
													}else {
														FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v06_122020_adjusted_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2 ", "", mapParameters1);
													}

													Map<String, Object> mapParameters = new HashMap<String, Object>();
													mapParameters.put("01_clientID", txtID.getValue());
													mapParameters.put("02_projID", txtProjID.getText());
													mapParameters.put("03_pblID", txtUnitID.getText());
													mapParameters.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v02_072016_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1 Version 2", mapParameters);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v06_122020_adjusted_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);

													Map<String, Object> mapParameters2 = new HashMap<String, Object>();
													mapParameters2.put("01_clientID", txtID.getValue());
													mapParameters2.put("02_projID", txtProjID.getText());
													mapParameters2.put("03_pblID", txtUnitID.getText());
													mapParameters2.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v02_072016_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1 Version 2", mapParameters);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v06_122020_adjusted_page6.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 6 ", "", mapParameters2);

													Map<String, Object> mapParameters3 = new HashMap<String, Object>();
													mapParameters3.put("01_clientID", txtID.getValue());
													mapParameters3.put("02_projID", txtProjID.getText());
													mapParameters3.put("03_pblID", txtUnitID.getText());
													mapParameters3.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v02_072016_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1 Version 2", mapParameters);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf162_v06_122020_adjusted_page3.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 3 ", "", mapParameters3);

												}
												else {
													JOptionPane.showMessageDialog(getContentPane(), "Technical description not yet set up","Information", JOptionPane.INFORMATION_MESSAGE);
												}


											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("LOAN AND MORTGAGE AGREEMENT HQP-HLF-163")) {
												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("LOAN AND MORTGAGE AGREEMENT HQP-HLF-163 (SMALL)")) {
												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_small_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("LOAN AND MORTGAGE AGREEMENT HQP-HLF-163 (V05, 12/2020)")) {
												if(checkTrue) {
													Map<String, Object> mapParameters2 = new HashMap<String, Object>();
													mapParameters2.put("01_clientID", txtID.getValue());
													mapParameters2.put("02_projID", txtProjID.getText());
													mapParameters2.put("03_pblID", txtUnitID.getText());
													mapParameters2.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_small_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_v05_122020_page6.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 6", "", mapParameters2);


													Map<String, Object> mapParameters1 = new HashMap<String, Object>();
													mapParameters1.put("01_clientID", txtID.getValue());
													mapParameters1.put("02_projID", txtProjID.getText());
													mapParameters1.put("03_pblID", txtUnitID.getText());
													mapParameters1.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_small_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_v05_122020_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);

													Map<String, Object> mapParameters = new HashMap<String, Object>();
													mapParameters.put("01_clientID", txtID.getValue());
													mapParameters.put("02_projID", txtProjID.getText());
													mapParameters.put("03_pblID", txtUnitID.getText());
													mapParameters.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_v05_122020_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
												}
												else {
													JOptionPane.showMessageDialog(getContentPane(), "Technical description not yet set up","Information", JOptionPane.INFORMATION_MESSAGE);
												}

											}
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("LOAN AND MORTGAGE AGREEMENT HQP-HLF-163 (V05, 12/2020) - ADJUSTED TECH")) {
												if(checkTrue) {
													Map<String, Object> mapParameters3 = new HashMap<String, Object>();
													mapParameters3.put("01_clientID", txtID.getValue());
													mapParameters3.put("02_projID", txtProjID.getText());
													mapParameters3.put("03_pblID", txtUnitID.getText());
													mapParameters3.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_small_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_v05_122020_adjusted_page3.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 3", "", mapParameters3);


													Map<String, Object> mapParameters2 = new HashMap<String, Object>();
													mapParameters2.put("01_clientID", txtID.getValue());
													mapParameters2.put("02_projID", txtProjID.getText());
													mapParameters2.put("03_pblID", txtUnitID.getText());
													mapParameters2.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_small_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_v05_122020_adjusted_page6.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 6", "", mapParameters2);

													Map<String, Object> mapParameters1 = new HashMap<String, Object>();
													mapParameters1.put("01_clientID", txtID.getValue());
													mapParameters1.put("02_projID", txtProjID.getText());
													mapParameters1.put("03_pblID", txtUnitID.getText());
													mapParameters1.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_small_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_v05_122020_adjusted_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);

													Map<String, Object> mapParameters = new HashMap<String, Object>();
													mapParameters.put("01_clientID", txtID.getValue());
													mapParameters.put("02_projID", txtProjID.getText());
													mapParameters.put("03_pblID", txtUnitID.getText());
													mapParameters.put("04_seqno", txtSeq.getText());
													//													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
													FncReport.generateReport("/Reports/rpt_hdmf_lma_hqphlf163_v05_122020_adjusted_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
												}
												else {
													JOptionPane.showMessageDialog(getContentPane(), "Technical description not yet set up","Information", JOptionPane.INFORMATION_MESSAGE);
												}

											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("DEED OF ABSOLUTE SALE H4-75") || (Boolean) modelDoc.getValueAt(x, 1).equals("DEED OF ABSOLUTE SALE HQP-HLF-525 (V01, 08/2017)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());

												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());

												if (blnWithOtherLot) {
													pgSelect dbExec = new pgSelect(); 
													dbExec.select("SELECT d.description, coalesce((select y.description from hs_sold_other_lots x inner join mf_unit_info y on x.oth_pbl_id = y.pbl_id  \n" + 
															"INNER join (select sub.pbl_id, sub.unit_id from mf_unit_info sub where sub.unit_id = d.unit_id) z on x.pbl_id = z.pbl_id), '') as other_description\n" + 
															"FROM rf_entity a  \n" + 
															"INNER JOIN rf_sold_unit B ON a.entity_id = b.entity_id  \n" + 
															"INNER JOIN mf_project C ON b.projcode = c.proj_id  \n" + 
															"INNER JOIN mf_unit_info D ON b.projcode = d.proj_id and b.pbl_id = d.pbl_id  \n" + 
															"INNER JOIN mf_buyer_type E on b.buyertype = e.type_id  \n" + 
															"WHERE b.status_id = 'A' and e.type_group_id = '04' and a.entity_id = '"+txtID.getValue()+"' and b.projcode = '"+txtProjID.getText()+"' \n" +
															"and b.pbl_id = '"+txtUnitID.getText()+"' and b.seq_no::int = '"+txtSeq.getText()+"'::int \n" + 
															"ORDER BY a.entity_name");

													int scanOption = 10; 
													scanOption = JOptionPane.showOptionDialog(null, "Which unit should be printed?", "Unit Selection", 
															JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
															new Object[] {(String) dbExec.Result[0][0], (String) dbExec.Result[0][1], "Mixed"}, JOptionPane.CANCEL_OPTION);

													if (scanOption==JOptionPane.YES_OPTION) {
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);		
													} else if (scanOption==JOptionPane.NO_OPTION) {
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_otherLot_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Other Lot Page 2", "", mapParameters1);
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_otherLot_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Other Lot Page 1", "", mapParameters);		
													} else {
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);												
													}
												} else {
													FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
													FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
												}
											}
											if (  (Boolean) modelDoc.getValueAt(x, 1).equals("DEED OF ABSOLUTE SALE HQP-HLF-525 (V02, 12/2020)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());

												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());
												if(checkTrue) {
													if (blnWithOtherLot) {
														pgSelect dbExec = new pgSelect(); 
														dbExec.select("SELECT d.description, coalesce((select y.description from hs_sold_other_lots x inner join mf_unit_info y on x.oth_pbl_id = y.pbl_id  and coalesce(y.server_id,'') = coalesce(x.server_id,'') \n" + 
																"INNER join (select sub.pbl_id, sub.unit_id from mf_unit_info sub where sub.unit_id = d.unit_id) z on x.pbl_id = z.pbl_id), '') as other_description\n" + 
																"FROM rf_entity a  \n" + 
																"INNER JOIN rf_sold_unit B ON a.entity_id = b.entity_id and coalesce(a.server_id,'') = coalesce(b.server_id,'') \n" + 
																"INNER JOIN mf_project C ON b.projcode = c.proj_id and coalesce(a.server_id,'') = coalesce(c.server_id,'') \n" + 
																"INNER JOIN mf_unit_info D ON b.projcode = d.proj_id and b.pbl_id = d.pbl_id and coalesce(a.server_id,'') = coalesce(d.server_id,'') \n" + 
																"INNER JOIN mf_buyer_type E on b.buyertype = e.type_id  \n" + 
																"WHERE b.status_id = 'A' and e.type_group_id = '04' and a.entity_id = '"+txtID.getValue()+"' and b.projcode = '"+txtProjID.getText()+"' \n" +
																"and b.pbl_id = '"+txtUnitID.getText()+"' and b.seq_no::int = '"+txtSeq.getText()+"'::int \n" + 
																"ORDER BY a.entity_name");

														int scanOption = 10; 
														scanOption = JOptionPane.showOptionDialog(null, "Which unit should be printed?", "Unit Selection", 
																JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
																new Object[] {(String) dbExec.Result[0][0], (String) dbExec.Result[0][1], "Mixed"}, JOptionPane.CANCEL_OPTION);

														if (scanOption==JOptionPane.YES_OPTION) {
															//															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
															//															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);	
															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_new_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_new_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);		
														} else if (scanOption==JOptionPane.NO_OPTION) {
															//															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_otherLot_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Other Lot Page 2", "", mapParameters1);
															//															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_otherLot_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Other Lot Page 1", "", mapParameters);	
															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_otherLot_new_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Other Lot Page 2", "", mapParameters1);
															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_otherLot_new_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Other Lot Page 1", "", mapParameters);		
														} else {
															//															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
															//															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);	
															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_new_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
															FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_new_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);												
														}
													} else {
														//														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
														//														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_new_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_new_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
													}
												}
												else {
													JOptionPane.showMessageDialog(getContentPane(), "Technical description not yet set up","Information", JOptionPane.INFORMATION_MESSAGE);
												}

											}
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("DEED OF ABSOLUTE SALE H4-75 (SMALL)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());

												Map<String, Object> mapParameters1 = new HashMap<String, Object>();
												mapParameters1.put("01_clientID", txtID.getValue());
												mapParameters1.put("02_projID", txtProjID.getText());
												mapParameters1.put("03_pblID", txtUnitID.getText());
												mapParameters1.put("04_seqno", txtSeq.getText());

												if (blnWithOtherLot) {
													pgSelect dbExec = new pgSelect(); 
													dbExec.select("SELECT d.description, coalesce((select y.description from hs_sold_other_lots x inner join mf_unit_info y on x.oth_pbl_id = y.pbl_id  \n" + 
															"INNER join (select sub.pbl_id, sub.unit_id from mf_unit_info sub where sub.unit_id = d.unit_id) z on x.pbl_id = z.pbl_id), '') as other_description\n" + 
															"FROM rf_entity a  \n" + 
															"INNER JOIN rf_sold_unit B ON a.entity_id = b.entity_id  \n" + 
															"INNER JOIN mf_project C ON b.projcode = c.proj_id  \n" + 
															"INNER JOIN mf_unit_info D ON b.projcode = d.proj_id and b.pbl_id = d.pbl_id  \n" + 
															"INNER JOIN mf_buyer_type E on b.buyertype = e.type_id  \n" + 
															"WHERE b.status_id = 'A' and e.type_group_id = '04' and a.entity_id = '"+txtID.getValue()+"' and b.projcode = '"+txtProjID.getText()+"' \n" +
															"and b.pbl_id = '"+txtUnitID.getText()+"' and b.seq_no::int = '"+txtSeq.getText()+"'::int \n" + 
															"ORDER BY a.entity_name");

													int scanOption = 10; 
													scanOption = JOptionPane.showOptionDialog(null, "Which unit should be printed?", "Unit Selection", 
															JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, 
															new Object[] {(String) dbExec.Result[0][0], (String) dbExec.Result[0][1], "Mixed"}, JOptionPane.CANCEL_OPTION);

													if (scanOption==JOptionPane.YES_OPTION) {
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_small_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);		
													} else if (scanOption==JOptionPane.NO_OPTION) {
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_otherLot_page2.jasper", modelDoc.getValueAt(x, 1).toString() + " Other Lot Page 2", "", mapParameters1);
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_otherLot_small_page1.jasper", modelDoc.getValueAt(x, 1).toString() + " Other Lot Page 1", "", mapParameters);		
													} else {
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
														FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);												
													}
												} else {
													FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_page2.jasper", modelDoc.getValueAt(x, 1).toString() + "Page 2", "", mapParameters1);
													FncReport.generateReport("/Reports/rpt_hdmf_doas_h475_mainLot_small_page1.jasper", modelDoc.getValueAt(x, 1).toString(), "Page 1", mapParameters);
												}
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("PROMISSORY NOTE HQP-HLF-087 (AFFORDABLE HOUSING)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf087.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("PROMISSORY NOTE HQP-HLF-087 (V05, 12/2020)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												//FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf087.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											
													FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf087_v05_122020.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);	
											}	
											
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("PROMISSORY NOTE HQP-HLF-087 (V06, 02/2024)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												//FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf087.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											
													FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf087_v06_022024.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);	
											}	
//									
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("AFFIDAVIT (SWORN OF AFFIDAVIT)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
											
												FncReport.generateReport("/Reports/rpt_hdmf_affidavit.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);	
											}	
//
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CERTIFICATE OF HOUSE & LOT ACCEPTANCE/COMPLETION")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												/*if(UserInfo.EmployeeCode.equals("900876") || UserInfo.EmployeeCode.equals("901172")) {
													//FncReport.generateReport("/Reports/sample.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
												//FncReport.reportViewer("/Reports/sample.jrxml", "", "", mapParameters);
													FncReport.generateReport("/Reports/sample.jasper", "", "", mapParameters);

												}else {*/
												if(txtProjID.getText().equals("018")) {
													FncReport.generateReport("/Reports/rpt_hdmf_chlac_eve.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
												}
												else if(txtProjID.getText().equals("019")) {
													FncReport.generateReport("/Reports/rpt_hdmf_chlac_er1b.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
												}
												else if(txtProjID.getText().equals("015")) {
													FncReport.generateReport("/Reports/rpt_hdmf_chlac_tv5.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
												}
												else {
													FncReport.generateReport("/Reports/rpt_hdmf_chlac.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
												}
											}
										}
									}
								} else {
									for (int x = 0; x < modelDoc.getRowCount(); x++) {
										System.out.println("");
										System.out.println("x: "+x);
										System.out.println("Report Name: " + modelDoc.getValueAt(x, 1));

										if ((Boolean) modelDoc.getValueAt(x, 0)) {
											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CERTIFICATE OF HOUSE & LOT ACCEPTANCE/COMPLETION")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_chlac_lq310.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("ASSIGNMENT OF LOAN PROCEEDS")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_alp_lq310.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("CERTIFICATE OF ACCEPTANCE HQP-HLF-083 (V02, 07/2017)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_certificate_of_acceptance_hqphlf083_v02_072017_lq310.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("PROMISSORY NOTE HQP-HLF-086 (V04, 10/2018)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf086_v04_102018_lq310.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);

											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("PROMISSORY NOTE HQP-HLF-086 (V05, 12/2020)")) {
												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_pn_hqphlf086_v04_102018_lq310.jasper", modelDoc.getValueAt(x, 1).toString(), "", mapParameters);	
											}

											if ((Boolean) modelDoc.getValueAt(x, 1).equals("HOUSING LOAN APPLICATION HQP-HLF-068 (V07, 02/2018)")) {
												Map<String, Object> mapParameter2 = new HashMap<String, Object>();
												mapParameter2.put("01_clientID", txtID.getValue());
												mapParameter2.put("02_projID", txtProjID.getText());
												mapParameter2.put("03_pblID", txtUnitID.getText());
												mapParameter2.put("04_seqno", txtSeq.getText());
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf068a_v07_022018_page2_lq310.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 2", "", mapParameter2);

												Map<String, Object> mapParameters = new HashMap<String, Object>();
												mapParameters.put("01_clientID", txtID.getValue());
												mapParameters.put("02_projID", txtProjID.getText());
												mapParameters.put("03_pblID", txtUnitID.getText());
												mapParameters.put("04_seqno", txtSeq.getText());	
												FncReport.generateReport("/Reports/rpt_hdmf_hla_hqphlf068a_v07_022018_page1_lq310.jasper", modelDoc.getValueAt(x, 1).toString() + " Page 1", "", mapParameters);
											}
										}
									}
								}
							}
						});
					}
					{
						cboPrinter = new JComboBox(new String[] {
								"FX-2175 Series", 
								"LQ/LX 300 Series"

						}); 
						panEnd.add(cboPrinter, BorderLayout.LINE_END); 
						cboPrinter.setPreferredSize(new Dimension(200, 0));
						cboPrinter.setSelectedIndex(0);
						cboPrinter.setEnabled(true);
						cboPrinter.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
								for (int x=0; x<modelDoc.getRowCount(); x++) {
									modelDoc.setValueAt(false, x, 0);
								}

								LoadReports(modelDoc, rowDoc);
							}
						});

						cboPrinter.setFont(FncGlobal.sizeTextValue);
					}
				}
			}
		}
		{
			panParamDOA = new JXPanel(new BorderLayout(5, 5));
			panParamDOA.setPreferredSize(new Dimension(350, 200));
			{
				JXPanel panTabCenter = new JXPanel(new BorderLayout(5, 5));
				panParamDOA.add(panTabCenter, BorderLayout.CENTER);
				{
					JXPanel panFields = new JXPanel(new BorderLayout(5, 5));
					panTabCenter.add(panFields, BorderLayout.CENTER);
					panFields.setBorder(JTBorderFactory.createTitleBorder("Notarial Seal", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panDet1 = new JXPanel(new GridLayout(1, 2, 20, 5));
						panFields.add(panDet1, BorderLayout.CENTER);
						{
							{
								JXPanel panDetails1 = new JXPanel(new BorderLayout(5, 5));
								panDet1.add(panDetails1, BorderLayout.CENTER);
								{
									JXPanel panDetails1_div = new JXPanel(new GridLayout(3, 1, 5, 5));
									panDetails1.add(panDetails1_div, BorderLayout.CENTER);
									{
										JXPanel panDocNo = new JXPanel(new BorderLayout(5, 5));
										panDetails1_div.add(panDocNo, BorderLayout.CENTER);
										{
											{
												lblDocNo_cts = new JLabel("Doc. No.");
												panDocNo.add(lblDocNo_cts, BorderLayout.LINE_START);
												lblDocNo_cts.setPreferredSize(new Dimension(75, 0));
											}
											{
												txtDocNo_cts = new JTextField("");
												panDocNo.add(txtDocNo_cts, BorderLayout.CENTER);
												txtDocNo_cts.setHorizontalAlignment(JTextField.CENTER);
												txtDocNo_cts.setText("282");
											}
										}
										JXPanel panPageNo = new JXPanel(new BorderLayout(5, 5));
										panDetails1_div.add(panPageNo, BorderLayout.CENTER);
										{
											{
												lblPageNo_cts = new JLabel("Page No.");
												panPageNo.add(lblPageNo_cts, BorderLayout.LINE_START);
												lblPageNo_cts.setPreferredSize(new Dimension(75, 0));
											}
											{
												txtPageNo_cts = new JTextField("");
												panPageNo.add(txtPageNo_cts, BorderLayout.CENTER);
												txtPageNo_cts.setHorizontalAlignment(JTextField.CENTER);
												txtPageNo_cts.setText("57");
											}
										}
										JXPanel panBookNo = new JXPanel(new BorderLayout(5, 5));
										panDetails1_div.add(panBookNo, BorderLayout.CENTER);
										{
											{
												lblBookNo_cts = new JLabel("Book No.");
												panBookNo.add(lblBookNo_cts, BorderLayout.LINE_START);
												lblBookNo_cts.setPreferredSize(new Dimension(75, 0));
											}
											{
												txtBookNo_cts = new JTextField("");
												panBookNo.add(txtBookNo_cts, BorderLayout.CENTER);
												txtBookNo_cts.setHorizontalAlignment(JTextField.CENTER);
												txtBookNo_cts.setText("1");
											}
										}
									}
								}
							}
							{
								JXPanel panDetails2 = new JXPanel(new BorderLayout(5, 5));
								panDet1.add(panDetails2, BorderLayout.CENTER);
								{
									JXPanel panDetails2_div = new JXPanel(new GridLayout(3, 1, 5, 5));
									panDetails2.add(panDetails2_div, BorderLayout.CENTER);
									{
										JXPanel panSeries = new JXPanel(new BorderLayout(5, 5));
										panDetails2_div.add(panSeries, BorderLayout.CENTER);
										{
											{
												lblSeriesNo_cts = new JLabel("Series Of");
												panSeries.add(lblSeriesNo_cts, BorderLayout.LINE_START);
												lblSeriesNo_cts.setPreferredSize(new Dimension(75, 0));
											}
											{
												txtSeriesNo_cts = new JTextField("");
												panSeries.add(txtSeriesNo_cts, BorderLayout.CENTER);
												txtSeriesNo_cts.setHorizontalAlignment(JTextField.CENTER);
												txtSeriesNo_cts.setText("2015");
											}
										}
										JXPanel panNothing = new JXPanel(new BorderLayout(5, 5));
										panDetails2_div.add(panNothing, BorderLayout.CENTER);
										{

										}
										JXPanel panDate = new JXPanel(new BorderLayout(5, 5));
										panDetails2_div.add(panDate, BorderLayout.CENTER);
										{
											dteDate_cts = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
											panDate.add(dteDate_cts);
											dteDate_cts.getCalendarButton().setVisible(true);
											dteDate_cts.setDate(FncGlobal.dateFormat("2015-01-06"));
										}
									}
								}
							}
						}
					}
					{
						JXPanel panDet2 = new JXPanel(new BorderLayout(5, 5));
						panFields.add(panDet2, BorderLayout.PAGE_END);
						panDet2.setPreferredSize(new Dimension(0, 28));
						{
							{
								lblAtty_cts = new JLabel("Attorney:");
								panDet2.add(lblAtty_cts, BorderLayout.LINE_START);
								lblAtty_cts.setPreferredSize(new Dimension(75, 0));
							}
							{
								txtAtty_cts = new JTextField("");
								panDet2.add(txtAtty_cts, BorderLayout.CENTER);
								txtAtty_cts.setHorizontalAlignment(JTextField.CENTER);
								txtAtty_cts.setText("Atty. Cherry Lynne S. Danao"); 
							}
						}
					}
					EnableFields(false);
				}
				{
					btnOkay = new JButton("CONFIRM");
					panParamDOA.add(btnOkay, BorderLayout.PAGE_END);
					btnOkay.setPreferredSize(new Dimension(0, 30));
					btnOkay.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							Window optionPaneWindow = SwingUtilities.getWindowAncestor(panParamDOA);
							optionPaneWindow.dispose();

							strDocNo = txtDocNo_cts.getText();
							strPageNo = txtPageNo_cts.getText();
							strBookNo = txtBookNo_cts.getText();
							strSeriesNo = txtSeriesNo_cts.getText();
							strAtty = txtAtty_cts.getText();

							SimpleDateFormat sdfFrom = new SimpleDateFormat("MMMMM dd, yyyy");
							strParamDate = (String) sdfFrom.format(dteDate_cts.getDate());
						}
					});
				}
			}	
		}
	}

	private String Client() {
		return "SELECT a.entity_id, a.entity_name, c.proj_name, d.description || coalesce( \n" + 
				"'/' || (select y.lot from hs_sold_other_lots x inner join mf_unit_info y on x.oth_pbl_id = y.pbl_id and x.server_id = y.server_id\n" + 
				"INNER join (select sub.pbl_id, sub.unit_id from mf_unit_info sub where sub.unit_id = d.unit_id and B.projcode = sub.proj_id and B.pbl_id = sub.pbl_id and sub.server_id is null and proj_server is not null) z on x.pbl_id = z.pbl_id), ''), \n" +
				"c.proj_id, d.pbl_id, b.seq_no, f.co_name \n" + 
				"FROM rf_entity a \n" + 
				"INNER JOIN rf_sold_unit B ON a.entity_id = b.entity_id \n" + 
				"INNER JOIN mf_project C ON b.projcode = c.proj_id \n" + 
				"INNER JOIN mf_unit_info D ON b.projcode = d.proj_id and b.pbl_id = d.pbl_id \n" + 
				"INNER JOIN mf_buyer_type E on b.buyertype = e.type_id \n" + 
				"left join \n" + 
				"(select coalesce(y.last_name, x.connection_last_name) || ', ' || coalesce(y.first_name, x.connection_first_name) || ' ' || \n" + 
				"coalesce(y.middle_name, x.connection_middle_name) as co_name, x.entity_id\n" + 
				"from rf_entity_connect x\n" + 
				"left join rf_entity y on x.connect_id = y.entity_id \n" + 
				"where x.connect_type = 'CO' ) F on a.entity_id = f.entity_id \n" +
				"WHERE b.status_id = 'A' and B.currentstatus IS NOT NULL and e.type_group_id = '04' \n" + 
				"ORDER BY a.entity_name";
	}

	public void CreateTable() {
		pnlTab1 = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlTab1.setOpaque(isOpaque());
		pnlTab1.setBorder(line);
		pnlTab1.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			scrDoc = new JScrollPane();
			pnlTab1.add(scrDoc, BorderLayout.CENTER);
			scrDoc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelDoc = new model_lrmdReports();
				modelDoc.setEditable(false);

				tblDoc = new _JTableMain(modelDoc);
				tblDoc.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblDoc.getSelectedRow());
						}
					}
				});

				rowDoc = tblDoc.getRowHeader();
				scrDoc.setViewportView(tblDoc);

				tblDoc.getColumnModel().getColumn(0).setPreferredWidth(40);
				tblDoc.getColumnModel().getColumn(1).setPreferredWidth(437);

				rowDoc = tblDoc.getRowHeader();
				rowDoc.setModel(new DefaultListModel());
				scrDoc.setRowHeaderView(rowDoc);
				scrDoc.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
			LoadReports(modelDoc, rowDoc);
		}

		pnlTab2 = new JXPanel(new BorderLayout(5, 5));
		pnlTab2.setOpaque(isOpaque());
		pnlTab2.setBorder(line);
		pnlTab2.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panTabCenter = new JXPanel(new BorderLayout(5, 5));
			pnlTab2.add(panTabCenter, BorderLayout.CENTER);
			{
				{
					JXPanel panRep = new JXPanel(new BorderLayout(5, 5));
					panTabCenter.add(panRep, BorderLayout.PAGE_START);
					panRep.setPreferredSize(new Dimension(0, 60));
					panRep.setBorder(JTBorderFactory.createTitleBorder("Report Type", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						{
							txtDocID = new _JLookup();
							panRep.add(txtDocID, BorderLayout.LINE_START);
							txtDocID.setHorizontalAlignment(JTextField.CENTER);
							txtDocID.setPreferredSize(new Dimension(100, 0));
							txtDocID.setLookupSQL(docSQL());
							txtDocID.setReturnColumn(0);
							txtDocID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										txtDocument.setText(data[1].toString());
										RetrieveNotarialEntries(txtID.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText());
										/*
										try {
											RetrieveNotarialEntries(txtID.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeq.getText());	
										} catch (Exception ex) {
											RetrieveNotarialEntries();
										}
										 */
									}
								}
							});
							txtDocID.setValue("169");
						}
						{
							txtDocument = new JTextField("CONTRACT TO SELL (NEWFLH240-A)");
							panRep.add(txtDocument, BorderLayout.CENTER);
							txtDocument.setHorizontalAlignment(JTextField.CENTER);
							txtDocument.setEditable(false);
						}
					}
				}
				{
					JXPanel panFields = new JXPanel(new BorderLayout(5, 5));
					panTabCenter.add(panFields, BorderLayout.CENTER);
					panFields.setBorder(JTBorderFactory.createTitleBorder("Notarial Seal", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panDet1 = new JXPanel(new GridLayout(1, 2, 20, 5));
						panFields.add(panDet1, BorderLayout.CENTER);
						{
							{
								JXPanel panDetails1 = new JXPanel(new BorderLayout(5, 5));
								panDet1.add(panDetails1, BorderLayout.CENTER);
								{
									JXPanel panDetails1_div = new JXPanel(new GridLayout(3, 1, 5, 5));
									panDetails1.add(panDetails1_div, BorderLayout.CENTER);
									{
										JXPanel panDocNo = new JXPanel(new BorderLayout(5, 5));
										panDetails1_div.add(panDocNo, BorderLayout.CENTER);
										{
											{
												lblDocNo = new JLabel("Doc. No.");
												panDocNo.add(lblDocNo, BorderLayout.LINE_START);
												lblDocNo.setPreferredSize(new Dimension(100, 0));
											}
											{
												txtDocNo = new JTextField("");
												panDocNo.add(txtDocNo, BorderLayout.CENTER);
												txtDocNo.setHorizontalAlignment(JTextField.CENTER);
											}
										}
										JXPanel panPageNo = new JXPanel(new BorderLayout(5, 5));
										panDetails1_div.add(panPageNo, BorderLayout.CENTER);
										{
											{
												lblPageNo = new JLabel("Page No.");
												panPageNo.add(lblPageNo, BorderLayout.LINE_START);
												lblPageNo.setPreferredSize(new Dimension(100, 0));
											}
											{
												txtPageNo = new JTextField("");
												panPageNo.add(txtPageNo, BorderLayout.CENTER);
												txtPageNo.setHorizontalAlignment(JTextField.CENTER);
											}
										}
										JXPanel panBookNo = new JXPanel(new BorderLayout(5, 5));
										panDetails1_div.add(panBookNo, BorderLayout.CENTER);
										{
											{
												lblBookNo = new JLabel("Book No.");
												panBookNo.add(lblBookNo, BorderLayout.LINE_START);
												lblBookNo.setPreferredSize(new Dimension(100, 0));
											}
											{
												txtBookNo = new JTextField("");
												panBookNo.add(txtBookNo, BorderLayout.CENTER);
												txtBookNo.setHorizontalAlignment(JTextField.CENTER);
											}
										}
									}
								}
							}
							{
								JXPanel panDetails2 = new JXPanel(new BorderLayout(5, 5));
								panDet1.add(panDetails2, BorderLayout.CENTER);
								{
									JXPanel panDetails2_div = new JXPanel(new GridLayout(3, 1, 5, 5));
									panDetails2.add(panDetails2_div, BorderLayout.CENTER);
									{
										JXPanel panSeries = new JXPanel(new BorderLayout(5, 5));
										panDetails2_div.add(panSeries, BorderLayout.CENTER);
										{
											{
												lblSeriesNo = new JLabel("Series Of");
												panSeries.add(lblSeriesNo, BorderLayout.LINE_START);
												lblSeriesNo.setPreferredSize(new Dimension(100, 0));
											}
											{
												txtSeriesNo = new JTextField("");
												panSeries.add(txtSeriesNo, BorderLayout.CENTER);
												txtSeriesNo.setHorizontalAlignment(JTextField.CENTER);
											}
										}
										JXPanel panNothing = new JXPanel(new BorderLayout(5, 5));
										panDetails2_div.add(panNothing, BorderLayout.CENTER);
										{

										}
										JXPanel panDate = new JXPanel(new BorderLayout(5, 5));
										panDetails2_div.add(panDate, BorderLayout.CENTER);
										{
											dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
											panDate.add(dteDate);
											dteDate.getCalendarButton().setVisible(true);
										}
									}
								}
							}
						}
					}
					{
						JXPanel panDet2 = new JXPanel(new BorderLayout(5, 5));
						panFields.add(panDet2, BorderLayout.PAGE_END);
						panDet2.setPreferredSize(new Dimension(0, 28));
						{
							{
								lblAtty = new JLabel("Attorney:");
								panDet2.add(lblAtty, BorderLayout.LINE_START);
								lblAtty.setPreferredSize(new Dimension(100, 0));
							}
							{
								txtAtty = new JComboBox(new String[] {
										"Atty. Cherry Lynne S. Danao", 
										"Atty. Eranio G. Cedillo"
								});
								panDet2.add(txtAtty, BorderLayout.CENTER);
							}
						}
					}
					EnableFields(false);
				}
			}
			JXPanel panTabEnd = new JXPanel(new GridLayout(1, 3, 5, 5));
			pnlTab2.add(panTabEnd, BorderLayout.PAGE_END);
			panTabEnd.setPreferredSize(new Dimension(0, 30));
			{
				{
					btnAdd = new JButton("Add");
					//panTabEnd.add(btnAdd, BorderLayout.CENTER);
					btnAdd.setActionCommand("Add");
					btnAdd.addActionListener(this);
				}
				{
					btnEdit = new JButton("Edit");
					panTabEnd.add(btnEdit, BorderLayout.CENTER);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					panTabEnd.add(btnSave, BorderLayout.CENTER);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					panTabEnd.add(btnCancel, BorderLayout.CENTER);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
				}
			}
		}

		pnlTab3 = new JXPanel(new BorderLayout(5, 5));
		pnlTab3.setOpaque(isOpaque());
		pnlTab3.setBorder(line);
		pnlTab3.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panPgStart = new JXPanel(new BorderLayout(5, 5));
			pnlTab3.add(panPgStart, BorderLayout.PAGE_START);
			panPgStart.setPreferredSize(new Dimension(0, 130));
			panPgStart.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				JPanel pnlNorthRate = new JPanel(new BorderLayout(5,5));
				pnlNorthRate.setBorder(line);
				pnlNorthRate.setBorder(new EmptyBorder(5,5,5,5));
				panPgStart.add(pnlNorthRate,BorderLayout.NORTH);
				{
					JXPanel pnlProj = new JXPanel(new BorderLayout(5,5));
					pnlNorthRate.add(pnlProj,BorderLayout.NORTH);
					pnlProj.setBorder(new EmptyBorder(5,5,5,5));
					{
						{
							lookProj = new _JLookup();
							pnlProj.add(lookProj,BorderLayout.LINE_START);
							lookProj.setPreferredSize(new Dimension(100,30));
							lookProj.setHorizontalAlignment(JTextField.CENTER);
							lookProj.setLookupSQL(sqlProj());
							lookProj.setReturnColumn(0);
							lookProj.setHorizontalAlignment(JTextField.CENTER);
							lookProj.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										txtProj.setText(data[1].toString());
										txtRate.setText(data[2].toString());
										rateRecId = data[3].toString();

									}
								}
							});
							//							lookProj.setValue("15");
						}

					}
					txtProj = new JTextField();
					pnlProj.add(txtProj,BorderLayout.CENTER);
					//					txtProj.setText("TERRAVERDE RESIDENCES");
					txtProj.setHorizontalAlignment(JTextField.CENTER);
				}
				{
					JXPanel pnlRate = new JXPanel(new BorderLayout(5,5));
					pnlNorthRate.add(pnlRate,BorderLayout.SOUTH);
					pnlRate.setBorder(new EmptyBorder(5,5,5,5));


					{
						JLabel lblRate = new JLabel("Rate:");
						pnlRate.add(lblRate,BorderLayout.LINE_START);
					}
					{
						JXPanel pnlRateComp = new JXPanel(new BorderLayout(5,5));
						pnlRate.add(pnlRateComp,BorderLayout.CENTER);

						{
							txtRate = new JXFormattedTextField();
							pnlRateComp.add(txtRate,BorderLayout.WEST);
							txtRate.setPreferredSize(new Dimension(100,30));
							txtRate.setHorizontalAlignment(JTextField.CENTER);
						}
						{
							JButton btnSaveRate = new JButton("Save");
							pnlRateComp.add(btnSaveRate,BorderLayout.CENTER);
							btnSaveRate.setPreferredSize(new Dimension(10,30));
							btnSaveRate.addActionListener(new ActionListener() {


								public void actionPerformed(ActionEvent e) {
									if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to save it?", 
											"Save",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
										saveRate();
										JOptionPane.showMessageDialog(getContentPane(), "Successfully Updated", "Update", JOptionPane.INFORMATION_MESSAGE);
										txtProj.setText("");
										txtRate.setText("");
										lookProj.setValue(null);
									}


								}
							});

						}
					}

				}
			}

			//				{
			//					scrollRate = new JScrollPane();
			//					pnlTab3.add(scrollRate, BorderLayout.CENTER);
			//					scrollRate.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			//					{
			//						modelRate = new model_pagibig_rate();
			//						tblRate = new _JTableMain(modelRate);
			//						rowRate = tblRate.getRowHeader();
			//						scrollRate.setViewportView(tblRate);
			//						
			//						tblRate.getColumnModel().getColumn(0).setPreferredWidth(80);
			//						tblRate.getColumnModel().getColumn(1).setPreferredWidth(380);
			//
			//						rowRate = tblRate.getRowHeader();
			//						rowRate.setModel(new DefaultListModel());
			//						scrollRate.setRowHeaderView(rowRate);
			//						scrollRate.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			//					}
			//					
			//				
			//			}
			//				RateTable(modelRate, rowRate);
		}
		ButtonState(true, true, false, false);
		blnState = true;
		tblDoc.setFont(FncGlobal.sizeTextValue);
	}

	private void LoadReports(DefaultTableModel modelMain, JList rowHeader) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 

		String SQL = ""; 

		try {
			SQL = "select false, upper(report_name) as report_name \n" +
					"from mf_hdmf_report \n" +
					"where '"+cboPrinter.getSelectedItem()+"' = ANY(printer) \n" + 
					" order by report_name";	
		} catch (Exception ex) {
			SQL = "select false, upper(report_name) as report_name \n" +
					"from mf_hdmf_report \n" +
					"where 'FX-2175 Series' = ANY(printer) \n" + 
					" order by report_name";
		}

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
		tblDoc.packAll();
	}

	private String docSQL() {
		return "select *\n" + 
				"from mf_documents_with_notarial_entries \n" + 
				"order by doc_id";
	}

	private void EnableFields(Boolean blnDo) {
		txtDocNo.setEditable(blnDo);
		txtPageNo.setEditable(blnDo);
		txtBookNo.setEditable(blnDo);
		txtSeriesNo.setEditable(blnDo);
		dteDate.setEnabled(blnDo);
		txtAtty.setEnabled(blnDo);
	}

	private void ButtonState(Boolean blnAdd, Boolean blnEdit, Boolean blnSave, Boolean blnCancel) {
		btnAdd.setEnabled(blnAdd);
		btnEdit.setEnabled(blnEdit);
		btnSave.setEnabled(blnSave);
		btnCancel.setEnabled(blnCancel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Add")) {
			blnAdd = true;
			EnableFields(true);
			ButtonState(false, false, true, true);
			blnState = false;
		} else if (e.getActionCommand().equals("Edit")) {
			blnAdd = false;
			EnableFields(true);
			ButtonState(false, false, true, true);
			blnState = false;			
		} else if (e.getActionCommand().equals("Save")) {
			String strSQL = "";
			pgUpdate db = new pgUpdate();

			Integer inCountRec = 0;
			inCountRec = FncGlobal.GetInteger("select count(*)::int from rf_hdmf_notarial_entries where entity_id = '"+txtID.getValue()+"' and proj_id = '"+txtProjID.getText()+"' and pbl_id = '"+txtUnitID.getText()+"' and seq_no::int = '"+txtSeq.getText()+"'::int and doc_id = '"+txtDocID.getValue()+"' and status_id = 'A'");

			if (inCountRec==0) {
				strSQL = "insert into rf_hdmf_notarial_entries \n" +
						"(doc_id, doc_no, page_no, book_no, series_no, date, atty_id, atty_name, status_id, entity_id, proj_id, pbl_id, seq_no) \n"+
						"values ('"+txtDocID.getValue()+"', '"+txtDocNo.getText()+"', '"+txtPageNo.getText()+"', '"+txtBookNo.getText()+"', '"+txtSeriesNo.getText()+"', \n" +
						"'"+dteDate.getDate().toString()+"'::DATE, '', '"+txtAtty.getSelectedItem()+"', 'A', '"+txtID.getValue()+"', '"+txtProjID.getText()+"', '"+txtUnitID.getText()+"', '"+txtSeq.getText()+"'::int)";
			} else {
				strSQL = "update rf_hdmf_notarial_entries \n" +
						"set doc_no = '"+txtDocNo.getText()+"', page_no = '"+txtPageNo.getText()+"', book_no = '"+txtBookNo.getText()+"', series_no = '"+txtSeriesNo.getText()+"', \n" +
						"date = '"+dteDate.getDate().toString()+"'::DATE, atty_name = '"+txtAtty.getSelectedItem()+"' \n" +
						"where entity_id = '"+txtID.getValue()+"' and proj_id = '"+txtProjID.getText()+"' \n" +
						"and pbl_id = '"+txtUnitID.getText()+"' and seq_no::int = '"+txtSeq.getText()+"'::int and doc_id = '"+txtDocID.getValue()+"'";
			}

			System.out.println("");
			System.out.println("strSQL: " + strSQL);

			db.executeUpdate(strSQL, true);
			db.commit();

			blnAdd = true;
			EnableFields(false);
			ButtonState(true, true, false, false);
			RetrieveNotarialEntries();
			blnState = true;

			JOptionPane.showMessageDialog(getContentPane(), "Notarial entries saved!");
		} else if (e.getActionCommand().equals("Cancel")) {
			blnAdd = true;
			EnableFields(false);
			ButtonState(true, true, false, false);
			RetrieveNotarialEntries();
			blnState = true;			
		}
	}

	private void RetrieveNotarialEntries() {
		Integer intCount = 0;
		intCount = FncGlobal.GetInteger("select count(*)::int from rf_hdmf_notarial_entries where doc_id = '"+txtDocID.getValue().toString()+"' and status_id = 'A'");

		if (intCount > 0) {
			/*
			txtDocNo.setText(FncGlobal.GetString("select doc_no from rf_hdmf_notarial_entries where doc_id = '"+txtDocID.getValue().toString()+"' and status_id = 'A'"));
			txtPageNo.setText(FncGlobal.GetString("select page_no from rf_hdmf_notarial_entries where doc_id = '"+txtDocID.getValue().toString()+"' and status_id = 'A'"));
			txtBookNo.setText(FncGlobal.GetString("select book_no from rf_hdmf_notarial_entries where doc_id = '"+txtDocID.getValue().toString()+"' and status_id = 'A'"));
			txtSeriesNo.setText(FncGlobal.GetString("select series_no from rf_hdmf_notarial_entries where doc_id = '"+txtDocID.getValue().toString()+"' and status_id = 'A'"));
			dteDate.setDate(FncGlobal.GetDate("select date::date from rf_hdmf_notarial_entries where doc_id = '"+txtDocID.getValue().toString()+"' and status_id = 'A'"));
			 */
			txtAtty.setSelectedIndex(0);
			btnAdd.setEnabled(false);
		} else {
			/*
			txtDocNo.setText("");
			txtPageNo.setText("");
			txtBookNo.setText("");
			txtSeriesNo.setText("");
			dteDate.setDate(null);
			 */
			txtAtty.setSelectedIndex(0);
			btnAdd.setEnabled(true);
		}
	}

	private void RetrieveCo(String strID) {
		txtCo.setText(FncGlobal.GetString("select a.connect_id as id \n" +
				"from rf_entity_connect a \n" +
				"left join rf_entity b on a.connect_id = b.entity_id \n" +
				"where a.entity_id = '"+strID+"' and a.connect_type = 'CO'"));
		txtCoName.setText(FncGlobal.GetString("select coalesce(b.last_name, a.connection_last_name) || ', ' || coalesce(b.first_name, a.connection_first_name) || ' ' || coalesce(b.middle_name, a.connection_middle_name) as name \n" +
				"from rf_entity_connect a \n" +
				"left join rf_entity b on a.connect_id = b.entity_id \n" +
				"where a.entity_id = '"+strID+"' and a.connect_type = 'CO'"));
	}

	private void RetrieveSpouse(String strID) {
		txtSpouse.setText(FncGlobal.GetString("select a.connect_id as id \n" +
				"from rf_entity_connect a \n" +
				"left join rf_entity b on a.connect_id = b.entity_id \n" +
				"where a.entity_id = '"+strID+"' and a.connect_type in ('SC', 'SP') and (a.relation = '20' or a.relation is null)"));
		txtSpouseName.setText(FncGlobal.GetString("select coalesce(b.last_name, a.connection_last_name) || ', ' || coalesce(b.first_name, a.connection_first_name) || ' ' || coalesce(b.middle_name, a.connection_middle_name) as name \n" +
				"from rf_entity_connect a \n" +
				"left join rf_entity b on a.connect_id = b.entity_id \n" +
				"where a.entity_id = '"+strID+"' and a.connect_type in ('SC', 'SP') and (a.relation = '20' or a.relation is null)"));
	}

	private void RetrieveNotarialEntries(String strID, String strProjID, String strUnitID, String strSeq) {

		txtDocNo.setText(FncGlobal.GetString("select coalesce(doc_no, '') from rf_hdmf_notarial_entries where entity_id = '"+strID+"' and proj_id = '"+strProjID+"' and pbl_id = '"+strUnitID+"' and seq_no::int = '"+strSeq+"'::int and doc_id = '"+txtDocID.getValue()+"' and status_id = 'A'")); 
		txtPageNo.setText(FncGlobal.GetString("select coalesce(page_no, '') from rf_hdmf_notarial_entries where entity_id = '"+strID+"' and proj_id = '"+strProjID+"' and pbl_id = '"+strUnitID+"' and seq_no::int = '"+strSeq+"'::int and doc_id = '"+txtDocID.getValue()+"' and status_id = 'A'"));
		txtBookNo.setText(FncGlobal.GetString("select coalesce(book_no, '') from rf_hdmf_notarial_entries where entity_id = '"+strID+"' and proj_id = '"+strProjID+"' and pbl_id = '"+strUnitID+"' and seq_no::int = '"+strSeq+"'::int and doc_id = '"+txtDocID.getValue()+"' and status_id = 'A'"));
		txtSeriesNo.setText(FncGlobal.GetString("select coalesce(series_no, '') from rf_hdmf_notarial_entries where entity_id = '"+strID+"' and proj_id = '"+strProjID+"' and pbl_id = '"+strUnitID+"' and seq_no::int = '"+strSeq+"'::int and doc_id = '"+txtDocID.getValue()+"' and status_id = 'A'")); 
		txtAtty.setSelectedItem(FncGlobal.GetString("select coalesce(atty_name, '') from rf_hdmf_notarial_entries where entity_id = '"+strID+"' and proj_id = '"+strProjID+"' and pbl_id = '"+strUnitID+"' and seq_no::int = '"+strSeq+"'::int and doc_id = '"+txtDocID.getValue()+"' and status_id = 'A'"));

		try {
			dteDate.setDate(FncGlobal.GetDate("select x.date::date from rf_hdmf_notarial_entries x where x.entity_id = '"+strID+"' and x.proj_id = '"+strProjID+"' and x.pbl_id = '"+strUnitID+"' and x.seq_no::int = '"+strSeq+"'::int and doc_id = '"+txtDocID.getValue()+"' and x.status_id = 'A'"));
		} catch (NullPointerException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private String sqlProj() {
		return "select a.proj_id,b.proj_name,a.interest_rate,a.rec_id\n" + 
				"from mf_hdmf_interest_rate a\n" + 
				"left join mf_project b on a.proj_id = b.proj_id where a.status_id != 'I' order by a.proj_id";
	}

	private static void saveRate(){
		pgSelect db = new pgSelect();
		String query = "select save_new_pagibig_rate("+rateRecId+","+txtRate.getText()+","
				+ "'"+lookProj.getValue()+"','"+UserInfo.EmployeeCode+"')";
		db.select(query);
	}

	private JPanel panTD() {
		JPanel panMain = new JPanel(new GridBagLayout()); 
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(10, 10, 10, 10); 

		/*	LINE 1	*/
		{
			gbc.weightx = 0.25;
			gbc.weighty = 1;

			gbc.gridx = 0;
			gbc.gridy = 0;

			JLabel label1 = new JLabel("Plan No."); 
			panMain.add(label1, gbc); 
			label1.setFont(FncGlobal.sizeLabel);
		}
		{
			gbc.weightx = 1;
			gbc.weighty = 1;

			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.gridwidth = 2; 

			txtPlanNo = new JTextField(""); 
			panMain.add(txtPlanNo, gbc); 
			txtPlanNo.setHorizontalAlignment(JTextField.CENTER);
			txtPlanNo.setFont(FncGlobal.sizeTextValue);
			txtPlanNo.setEditable(false);

			gbc.gridwidth = 1; 
		}

		/*	LINE 2	*/
		{
			gbc.weightx = 0.25;
			gbc.weighty = 1;

			gbc.gridx = 0;
			gbc.gridy = 1;

			JLabel label1 = new JLabel("LRC Record No."); 
			panMain.add(label1, gbc); 
			label1.setFont(FncGlobal.sizeLabel);
		}
		{
			gbc.weightx = 1;
			gbc.weighty = 1;

			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.gridwidth = 2; 

			txtLRC = new JTextField(""); 
			panMain.add(txtLRC, gbc); 
			txtLRC.setHorizontalAlignment(JTextField.CENTER);
			txtLRC.setFont(FncGlobal.sizeTextValue);
			txtLRC.setEditable(false);

			gbc.gridwidth = 1; 
		}


		/*	LINE 3	*/
		{
			gbc.weightx = 0.25;
			gbc.weighty = 1;

			gbc.gridx = 0;
			gbc.gridy = 2;

			JLabel label1 = new JLabel("Portion of"); 
			panMain.add(label1, gbc); 
			label1.setFont(FncGlobal.sizeLabel);

			gbc.weightx = 0.25;
			gbc.weighty = 1;

			gbc.gridx = 0;
			gbc.gridy = 3;

			JLabel label2 = new JLabel(""); 
			panMain.add(label2, gbc); 
			label2.setFont(FncGlobal.sizeLabel);
		}
		{
			gbc.weightx = 1;
			gbc.weighty = 1;

			gbc.gridx = 1;
			gbc.gridy = 2;

			gbc.gridwidth = 2;
			gbc.gridheight = 2; 

			txtPortion = new JTextArea(""); 
			txtPortion.setLineWrap(true);
			txtPortion.setWrapStyleWord(true);
			txtPortion.setFont(FncGlobal.sizeTextValue);
			txtPortion.setEditable(false);

			JScrollPane scr = new JScrollPane(txtPortion); 
			panMain.add(scr, gbc); 

			gbc.gridwidth = 1;
			gbc.gridheight = 1; 
		}

		/*	LINE 4	*/
		{
			gbc.weightx = 0.25;
			gbc.weighty = 1;

			gbc.gridx = 0;
			gbc.gridy = 4;

			JLabel label1 = new JLabel("Technical Description"); 
			panMain.add(label1, gbc); 
			label1.setFont(FncGlobal.sizeLabel);

			gbc.weightx = 0.25;
			gbc.weighty = 1;

			gbc.gridx = 0;
			gbc.gridy = 5;

			JLabel label2 = new JLabel(""); 
			panMain.add(label2, gbc); 
			label2.setFont(FncGlobal.sizeLabel);
		}
		{
			gbc.weightx = 1;
			gbc.weighty = 1;

			gbc.gridx = 1;
			gbc.gridy = 4;
			gbc.gridwidth = 2;
			gbc.gridheight = 2; 

			txtTechDesc = new JTextArea(""); 
			txtTechDesc.setLineWrap(true);
			txtTechDesc.setWrapStyleWord(true);
			txtTechDesc.setFont(FncGlobal.sizeTextValue);
			txtTechDesc.setEditable(false);

			JScrollPane scr = new JScrollPane(txtTechDesc); 
			panMain.add(scr, gbc); 

			gbc.gridwidth = 1; 
		}
		return panMain; 
	}
}
