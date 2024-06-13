package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;

public class PagibigStatusMonitoring_v2 extends _JInternalFrame implements ActionListener {

	/*** Author: Emman ***/
	private static final long serialVersionUID = 10362993502709257L;
	Dimension size = new Dimension(800, 625);
	static String title = "PAGIBIG Status Monitoring";
	Border lineRed = BorderFactory.createLineBorder(Color.RED);
	Border lineGreen = BorderFactory.createLineBorder(Color.GRAY);

	hdmfMon_qualifyAccounts panQA = new hdmfMon_qualifyAccounts(this);
	hdmfMon_msvsMonitoring panMSVS = new hdmfMon_msvsMonitoring(this);
	hdmfMon_houseInspection panInsp = new hdmfMon_houseInspection(this);
	hdmfMon_borrowerValidation panBorVal = new hdmfMon_borrowerValidation(this);
	hdmfMon_accountQuery panAcct = new hdmfMon_accountQuery(this);
	hdmfMon_statusTagging panTag = new hdmfMon_statusTagging(this);
	hdmfMon_REM panREM = new hdmfMon_REM(this);
	hdmfMon_REMGG panREMGG = new hdmfMon_REMGG(this);
	hdmfMon_advancedCI panCI = new hdmfMon_advancedCI(this);

	private JLabel lblCo;
	private JLabel lblPro;
	private JLabel lblPhase;

	public static _JLookup txtCoID;
	public _JLookup txtProID;
	public _JLookup txtPhaseID;
	public _JLookup txtBatch;

	public JTextField txtCo;
	public JTextField txtPro;
	public JTextField txtPhase;

	public static JButton btnGen;
	public static JButton btnPost;
	public static JButton btnPreview;
	public static JButton btnExport;

	private _JTabbedPane tabHDMF;

	public Integer intQA = 1;
	public Integer intMSVS = 1;
	public Integer intInsp = 1;
	public Integer intBorVal = 1;
	public Integer intAcct = 1;
	public Integer intTag = 1;
	public Integer intREM = 1;
	public Integer intCI = 4;
	public Integer intREMGG = 1;

	public String co_id;
	
	public static String company_id;
	public static String project_id;
	public static String phase_id;

	public PagibigStatusMonitoring_v2() {
		super(title, true, true, false, false);
		initGUI();
	}

	public PagibigStatusMonitoring_v2(String title) {
		super(title);
		initGUI();
	}

	public PagibigStatusMonitoring_v2(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(size);
		this.setPreferredSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(panMain, BorderLayout.CENTER);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panPage, BorderLayout.PAGE_START);
				panPage.setPreferredSize(new Dimension(0, 120));
				{
					JXPanel panDiv1 = new JXPanel(new BorderLayout(5, 5));
					panPage.add(panDiv1, BorderLayout.LINE_START);
					panDiv1.setPreferredSize(new Dimension(550, 0));
					panDiv1.setBorder(JTBorderFactory.createTitleBorder("Company, Project and Phase",
							FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panPrimeLabel = new JXPanel(new GridLayout(3, 2, 5, 5));
						panDiv1.add(panPrimeLabel, BorderLayout.LINE_START);
						panPrimeLabel.setPreferredSize(new Dimension(200, 0));
						{
							lblCo = new JLabel("Company");
							panPrimeLabel.add(lblCo, BorderLayout.CENTER);
							lblCo.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtCoID = new _JLookup();
							panPrimeLabel.add(txtCoID, BorderLayout.CENTER);
							txtCoID.setReturnColumn(0);
							txtCoID.setLookupSQL(SQL_COMPANY());
							txtCoID.setHorizontalAlignment(JTextField.CENTER);
							txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();

									if (data != null) {
//										txtCoID.setValue(data[0].toString());
//										txtCo.setText(data[1].toString());
										co_id = (String) data[0];
										company_id = (String) data[0];
										project_id = "";
										phase_id = "";
										String name = (String) data[1];
										txtCo.setText(name);
										txtProID.setLookupSQL(SQL_PROJECT(txtCoID.getText()));
										txtProID.setValue("");
										txtPro.setText("");
										txtPhaseID.setValue("");
										txtPhase.setText("");
									}
								}
							});
						}

						{
							lblPro = new JLabel("Project");
							panPrimeLabel.add(lblPro, BorderLayout.CENTER);
							lblPro.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtProID = new _JLookup();
							panPrimeLabel.add(txtProID, BorderLayout.CENTER);
							txtProID.setReturnColumn(0);
							txtProID.setHorizontalAlignment(JTextField.CENTER);
							co_id = txtCoID.getText();
							txtProID.setLookupSQL(SQL_PROJECT(co_id));
							txtProID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();

									if (data != null) {
//										txtProID.setValue(data[0].toString());
										project_id = (String) data[0];
										phase_id = "";
										txtPro.setText(data[1].toString());
										txtPhaseID.setLookupSQL(SQL_PHASE(txtProID.getText()));
										txtPhaseID.setValue("");
										txtPhase.setText("");
									}
								}
							});
						}
						{
							lblPhase = new JLabel("Phase");
							panPrimeLabel.add(lblPhase, BorderLayout.CENTER);
							lblPhase.setHorizontalAlignment(JTextField.LEADING);
						}
						{
							txtPhaseID = new _JLookup();
							panPrimeLabel.add(txtPhaseID, BorderLayout.CENTER);
							txtPhaseID.setReturnColumn(0);
							txtPhaseID.setLookupSQL("");
							txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
							txtPhaseID.setLookupSQL(SQL_PHASE(txtProID.getValue()));
							txtPhaseID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object data[] = ((_JLookup) event.getSource()).getDataSet();

									if (data != null) {
										phase_id = (String) data[0];
										txtPhaseID.setText(data[0].toString());
										txtPhase.setText(data[1].toString());
									}
								}
							});
						}
						JXPanel panPrimeBox = new JXPanel(new GridLayout(3, 1, 5, 5));
						panDiv1.add(panPrimeBox, BorderLayout.CENTER);
						{
							txtCo = new JTextField();
							panPrimeBox.add(txtCo, BorderLayout.CENTER);
							txtCo.setHorizontalAlignment(JTextField.CENTER);
							txtCo.setEditable(false);
						
						}
						{
							txtPro = new JTextField();
							panPrimeBox.add(txtPro, BorderLayout.CENTER);
							txtPro.setHorizontalAlignment(JTextField.CENTER);
							txtPro.setEditable(false);
						}
						{
							txtPhase = new JTextField();
							panPrimeBox.add(txtPhase, BorderLayout.CENTER);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
							txtPhase.setEditable(false);
						}
//						Setdefaults(txtCoID, txtCo, txtProID,txtPro);
					}
					JXPanel panDiv2 = new JXPanel(new BorderLayout(5, 5));
					panPage.add(panDiv2, BorderLayout.CENTER);
					{
						btnGen = new JButton("Generate");
						panDiv2.add(btnGen, BorderLayout.CENTER);
						btnGen.setActionCommand("Generate");
						btnGen.addActionListener(this);
						btnGen.setEnabled(false);
					}
				}
				JXPanel panCenter = new JXPanel(new BorderLayout(0, 0));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					tabHDMF = new _JTabbedPane();
					panCenter.add(tabHDMF, BorderLayout.CENTER);
					{
						panQA = new hdmfMon_qualifyAccounts(this);
						tabHDMF.addTab("  Qualify Accounts   ", null, panQA, null);
					}
					{
						panMSVS = new hdmfMon_msvsMonitoring(this);
						tabHDMF.addTab("        MSVS         ", null, panMSVS, null);
					}
					{
						panInsp = new hdmfMon_houseInspection(this);
						tabHDMF.addTab("  House Inspection   ", null, panInsp, null);
					}
					{
						panBorVal = new hdmfMon_borrowerValidation(this);
						tabHDMF.addTab("Borrower's Validation", null, panBorVal, null);
					}
					/*
					 * { panAcct = new hdmfMon_accountQuery(this); tabHDMF.addTab("Account Query",
					 * null, panAcct, null); }
					 */
					{
						panTag = new hdmfMon_statusTagging(this);
						tabHDMF.addTab("   Status Tagging    ", null, panTag, null);
					}
					{
						panREM = new hdmfMon_REM(this);
						tabHDMF.addTab("   REM Conversion    ", null, panREM, null);
					}
					{
						panCI = new hdmfMon_advancedCI(this);
						tabHDMF.addTab("  Advanced CI/MSVS   ", null, panCI, null);
					}
					{
						panREMGG = new hdmfMon_REMGG(this);
						tabHDMF.addTab("  REM Conversion (G-G)   ", null, panREMGG, null);
					}

					tabHDMF.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent act) {
							if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Qualify Accounts")) {
								CtrlLock_0(intQA);
							} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("MSVS")) {
								CtrlLock_0(intMSVS);
							} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim()
									.equals("House Inspection")) {
								CtrlLock_1(intInsp);
							} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim()
									.equals("Borrower's Validation")) {
								CtrlLock_1(intBorVal);
							} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Account Query")) {
								CtrlLock_0(intAcct);
							} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Status Tagging")) {
								CtrlLock_0(intTag);
							} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("REM Conversion")) {
								CtrlLock_1(intREM);
							} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim()
									.equals("Advanced CI/MSVS")) {
								CtrlLock_0(intCI);
							} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("REM Conversion (G-G)")) {
								CtrlLock_1(intREMGG);
							}
						}
					});
				}
				{
					JXPanel panEnd = new JXPanel(new GridLayout(1, 3, 5, 5));
					panMain.add(panEnd, BorderLayout.PAGE_END);
					panEnd.setPreferredSize(new Dimension(0, 30));
					{
						btnPost = new JButton("Post");
						panEnd.add(btnPost, BorderLayout.CENTER);
						btnPost.setActionCommand("Post");
						btnPost.addActionListener(this);
						btnPost.setEnabled(false);
					}
					{
						btnPreview = new JButton("Preview");
						panEnd.add(btnPreview, BorderLayout.CENTER);
						btnPreview.setActionCommand("Preview");
						btnPreview.addActionListener(this);
						btnPreview.setEnabled(false);
					}
					{
						btnExport = new JButton("Export");
						panEnd.add(btnExport, BorderLayout.CENTER);
						btnExport.setActionCommand("Export");
						btnExport.addActionListener(this);
						btnExport.setEnabled(false);
					}
				}

				CtrlLock_0(1);
			}
		}
	}

	private void Setdefaults(_JLookup _txtCoID, JTextField _txtCo, _JLookup _txtProID, JTextField _txtPro) {
		String strCount = "";
		Integer intCount = 0;

		/* Company Default */
		strCount = FncGlobal.GetString("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			_txtCoID.setValue("");
			_txtCo.setText("[ Company ]");
		} else {
			_txtCoID.setValue(FncGlobal.GetString("SELECT co_id FROM mf_company LIMIT 1"));

			try {
				String strCo = FncGlobal
						.GetString("SELECT company_name FROM mf_company WHERE co_id = '" + _txtCoID.getValue() + "'");
				System.out.println(strCo);
				_txtCo.setText(strCo);
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtCoID: " + _txtCoID.getValue());
				System.out.println("txtCoName: " + FncGlobal
						.GetString("SELECT company_name FROM mf_company WHERE co_id = '" + _txtCoID.getValue() + "'"));
			}
		}

		/* Project Default */
		strCount = FncGlobal.GetString("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);

		if (intCount > 1) {
			_txtProID.setValue("");
			_txtPro.setText("");
		} else {
			_txtProID.setValue(FncGlobal.GetString("SELECT proj_id FROM mf_project LIMIT 1"));

			try {
				String strPro = FncGlobal
						.GetString("SELECT proj_name FROM mf_project WHERE proj_id = '" + _txtProID.getValue() + "'");
				System.out.println(strPro);
				_txtPro.setText(strPro);
			} catch (NullPointerException e) {
				System.out.println("");
				System.out.println("txtProjectID: " + _txtProID.getValue());
				System.out.println(FncGlobal
						.GetString("SELECT proj_name FROM mf_project WHERE proj_id = '" + _txtProID.getValue() + "'"));
			}
		}
//		_txtCoID.setValue("02");
//		_txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");

//		_txtProID.setValue("015");
//		_txtPro.setText("TERRAVERDE RESIDENCES");

		// System.out.printf("Display value of Proj. ID: %s%n", txtProID.getValue());
		txtPhaseID.setLookupSQL(SQL_PHASE(txtProID.getValue()));
		/*
		 * txtPhaseID.setValue("1"); txtPhase.setText("Phase 1");
		 */
	}

	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent act) {
		if (act.getActionCommand().equals("Generate")) {
			if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Qualify Accounts")) {
				panQA.Generate();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("MSVS")) {
				panMSVS.txtBatch.setValue("");
				panMSVS.Generate();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("House Inspection")) {
				panInsp.Generate();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Borrower's Validation")) {
				panBorVal.Generate();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Account Query")) {

			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Status Tagging")) {
				panTag.Generate();
				btnState(true, false, false);
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("REM Conversion")) {
				panREM.Generate();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Advanced CI/MSVS")) {
				panCI.Generate();
			}else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("REM Conversion (G-G)")) {
				panREMGG.Generate();
			}
		} else if (act.getActionCommand().equals("Post")) {
			/*
			 * if(){
			 * 
			 * }
			 */
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Post",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Qualify Accounts")) {
					panQA.Post();
				} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("MSVS")) {
					panMSVS.Post();
				} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("House Inspection")) {
					panInsp.Post();
				} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Borrower's Validation")) {
					panBorVal.Post();
				} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Account Query")) {

				} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Status Tagging")) {
					try {
						panTag.Post();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("REM Conversion")) {
					panREM.Post();
				} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("REM Conversion (G-G)")) {
					panREMGG.Post();
				}
			}
		} else if (act.getActionCommand().equals("Preview")) {
			if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Qualify Accounts")) {
				panQA.Preview();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("MSVS")) {
				panMSVS.Preview();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("House Inspection")) {
				panInsp.Preview();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Borrower's Validation")) {
				panBorVal.Preview();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Account Query")) {

			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Status Tagging")) {
				panTag.Preview();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("REM Conversion")) {
				panREM.Preview();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Advanced CI/MSVS")) {
				panCI.Preview();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("REM Conversion (G-G)")) {
				panREMGG.Preview();
			}
		} else if (act.getActionCommand().trim().equals("Export")) {
			if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Qualify Accounts")) {
				panQA.Export();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("MSVS")) {
				panMSVS.Export();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("House Inspection")) {
				panInsp.Export();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Borrower's Validation")) {
				panBorVal.Export();
			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Account Query")) {

			} else if (tabHDMF.getTitleAt(tabHDMF.getSelectedIndex()).trim().equals("Status Tagging")) {
				panTag.export();
			}
		}
	}

	public void CtrlLock_0(Integer i) {
		if (i == 1) { /*** Initial State ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(false);
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
		} else if (i == 2) { /*** Generate is pressed ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(true);
			btnPreview.setEnabled(true);
			btnExport.setEnabled(true);
		} else if (i == 3) { /*** Post is pressed ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(false);
			btnPreview.setEnabled(true);
			btnExport.setEnabled(true);
		} else if (i == 4) { /*** Post is pressed ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(false);
			btnPreview.setEnabled(true);
			btnExport.setEnabled(false);
		} else if (i == 5) { /*** Post is pressed ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(true);
			btnPreview.setEnabled(true);
			btnExport.setEnabled(false);
		} else if (i == 6) {
			btnGen.setEnabled(true);
			btnPost.setEnabled(true);
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
		}
	}

	public void CtrlLock_1(Integer i) {
		if (i == 1) { /*** Initial State ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(false);
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
		} else if (i == 2) { /*** Generate is pressed ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(true);
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
		} else if (i == 3) { /*** Batch is retrieved/Post is pressed ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(false);
			btnPreview.setEnabled(true);
			btnExport.setEnabled(true);
		} else if (i == 4) { /*** Batch is retrieved/Post is pressed ***/
			btnGen.setEnabled(false);
			btnPost.setEnabled(false);
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
		}
	}

	public void CtrlLock_2(Integer i) {
		if (i == 1) { /*** Initial State ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(false);
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
		} else if (i == 2) { /*** Generate is pressed ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(true);
			btnPreview.setEnabled(false);
			btnExport.setEnabled(false);
		} else if (i == 3) { /*** Generate is pressed ***/
			btnGen.setEnabled(false);
			btnPost.setEnabled(false);
			btnPreview.setEnabled(true);
			btnExport.setEnabled(false);
		} else if (i == 4) { /*** Generate is pressed ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(false);
			btnPreview.setEnabled(true);
			btnExport.setEnabled(false);
		} else if (i == 5) { /*** Generate is pressed ***/
			btnGen.setEnabled(true);
			btnPost.setEnabled(true);
			btnPreview.setEnabled(true);
			btnExport.setEnabled(false);
		}
	}

	public static void btnState(Boolean sPost, Boolean sPreview, Boolean sExport) {
		btnPost.setEnabled(sPost);
		btnPreview.setEnabled(sPreview);
		btnExport.setEnabled(sExport);
	}

	public String GetBatch(String strType) {
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
					+ "FROM rf_buyer_status \n" + "WHERE byrstatus_id = '107' AND status_id = 'A' and server_id is null";

			dbBatch.select(strSQL);

			if (dbBatch.isNotNull()) {
				strBat = (String) dbBatch.getResult()[0][0];
			} else {
				strBat = "000001";
			}
		}

		return strBat;
	}
}
