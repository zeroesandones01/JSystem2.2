/**
 * 
 */
package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.table.NumberEditorExt;

import Database.pgSelect;
import tablemodel.modelCreditofPayment_CrdtPymntOther;
import tablemodel.modelCreditofPayment_CrdtToLedger;
import tablemodel.modelROP_OtherPmt;
import tablemodel.modelROP_Miscellaneous;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JRadioButton;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class CreditOfPayment extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -2764313576428698032L;
	private static String title = "Credit of Payment";
	static Dimension SIZE = new Dimension(1200, 500);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNECenter;
	private JPanel pnlReqDetails;
	private JPanel pnlReqDetailsWest;
	private JPanel pnlReqDetailsCenter;
	private JLabel lblReqNo;
	private _JXTextField txtReqNo;
	private JLabel lblReqStat;
	private _JXTextField txtReqStat;

	private JPanel pnlCredit;
	private JPanel pnlCreditFrm;
	private JPanel pnlCreditFrmWest;
	private JLabel lblCreditFrmClient;
	private JLabel lblCreditFrmUnit;
	private JLabel lblCreditFrmProject;
	private JLabel lblCreditFrmSeq;

	private JPanel pnlCreditFrmCenter;
	private JPanel pnlCreditFrmClient;
	private _JLookup lookupCrdtFrmClient;
	private _JXTextField txtCrdtFrmClient;

	private JPanel pnlCreditFrmUnit;
	private _JXTextField txtCreditFrmUnitID;
	private _JXTextField txtCreditFrmUnitDesc;

	private JPanel pnlCreditFrmProject;
	private _JXTextField txtCreditFrmProjID;
	private JTextField txtCreditFrmProject;

	private JPanel pnlCreditFrmSeq;
	private _JXTextField txtCreditFrmSeq;
	private JLabel lblCreditFrmStat;
	private JTextField txtCreditFrmStat;

	private JPanel pnlCreditTo;
	private JPanel pnlCreditToWest;
	private JLabel lblCreditToClient;
	private JLabel lblCreditToUnit;
	private JLabel lblCreditToProject;
	private JLabel lblCreditToSeq;

	private JPanel pnlCreditToCenter;
	private JPanel pnlCreditToClient;
	private _JLookup lookupCrdtToClient;
	private _JXTextField txtCrdtToClient;

	private JPanel pnlCreditToUnit;
	private _JXTextField txtCreditToUnitID;
	private _JXTextField txtCreditToUnitDesc;

	private JPanel pnlCreditToProject;
	private _JXTextField txtCreditToProjID;
	private JTextField txtCreditToProject;

	private JPanel pnlCreditToSeq;
	private _JXTextField txtCreditToSeq;
	private JLabel lblCreditToStat;
	private JTextField txtCreditToStat;

	/*private JLabel lblClientNameCrdtTo;
	private _JLookup lookupClientCrdtTo;
	private JTextField txtCrdtToClientName;
	private JLabel lblCrdtToUnitID;
	private JTextField txtCrdtToUnitID;
	private JLabel lblCreditFrmAcctPBL;
	private JTextField txtCreditFrmAcctPBL;

	//private JPanel pnlCreditToUnit;
	private JLabel lblCreditToUnitPBL;
	private JTextField txtCreditToUnitPBL;
	private JLabel lblCreditToUnitProject;
	private JTextField txtCreditToUnitProject;
	private JLabel lblCreditToUnitStat;
	private JTextField txtCreditToUnitStat;*/

	private JPanel pnlNorthEast;
	private JLabel lblCreditPaymentFrmAcct;//TODO check
	private _JRadioButton rbtnCrdtFrmLedgerPymnt;
	private _JRadioButton rbtnCrdtFrmOtherPymnt;
	public static _JTableMain tblCreditsPaymentsFrm;
	private JScrollPane scrollCreditsPaymentsFrm;
	private JLabel lblSep1;
	private JLabel lbltotalPymnt;
	private _JXFormattedTextField txtTotalPymnt; 
	private JLabel lblCreditPymnt; //TODO chec
	private _JRadioButton rbtnCrdtPymntToLedger;
	private _JRadioButton rbtnCrdtPymntToOther;
	private _JTableMain tblCreditsPaymentsTo;
	private JScrollPane scrollCreditsPaymentsTo;
	private JLabel lblAmntToCredit;
	private _JXFormattedTextField txtAmntToCredit;
	private JPanel pnlCreditPayment;

	private JPanel pnlSouth;
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnCancelReq;
	private JButton btnSearch;
	private JButton btnPost;
	private JButton btnClear;
	private JButton btnPreview;

	private ButtonGroup btngrpCrdtPymntFrm = new ButtonGroup();
	private ButtonGroup btngrpCrdtPymntTo = new ButtonGroup();

	private _JTabbedPane tabCreditPmtFrom;

	private JPanel pnlCreditFromLedger;
	private _JTableMain tblCrdtFromLedger;
	private JList rowHeaderCrdtFromLedger;
	private _JScrollPaneMain scrollCreditFromLedger;
	private modelROP_OtherPmt modelCrdtFromLedger;
	private _JScrollPaneTotal scrollCreditFromLedgerTotal;
	private _JTableTotal tblCrdtFromLedgerTotal;
	private modelROP_OtherPmt modelCrdtFromLedgerTotal;

	private JPanel pnlCreditFromOther;
	private _JTableMain tblCrdtFromOther;
	private JList rowHeaderCrdtFrmOther;
	private _JScrollPaneMain scrollCreditFromOther;
	private modelROP_Miscellaneous modelCrdtFromOther;
	private _JScrollPaneTotal scrollCreditFromOtherTotal;
	private _JTableTotal tblCrdtFromOtherTotal;
	private modelROP_Miscellaneous modelCrdtFromOtherTotal;

	private _JTabbedPane tabCreditPmtTo;

	private JPanel pnlCreditToLegder;
	private _JTableMain tblCrdtToLedger;
	private JList rowHeaderCrdtToLedger;
	private JScrollPane scrollCreditToLedger;
	private modelCreditofPayment_CrdtToLedger modelCrdtToLedger;

	private JPanel pnlCreditToOther;
	private _JTableMain tblCrdtToOther;
	private JList rowHeaderCrdtToOther;
	private JScrollPane scrollCreditToOther;
	private modelCreditofPayment_CrdtPymntOther modelCrdtToOther;
	
	private _JXTextField txtRequestBy;
	private _JXTextField txtRemarks;
	private JLabel lblRequestBy;
	private JLabel lblRemarks;

	private JPanel pnlAmtToCredit;
	private Map<String, BigDecimal> mapParticulars= new HashMap<String, BigDecimal>();

	protected static DecimalFormat df = new DecimalFormat("#,##0.00");

	public CreditOfPayment() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CreditOfPayment(String title) {
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
		//pnlMain.setLayout(new GridLayout(2, 1, 3, 3));
		//pnlMain.setLayout(new SpringLayout());
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlNorth, BorderLayout.CENTER);
			//pnlNorth.setBorder(lineBorder);
			pnlNorth.setLayout(new GridLayout(1, 2, 5, 5));
			{
				pnlNorthWest = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				//pnlNorthWest.setLayout(new SpringLayout());
				//pnlNorthWest.setLayout(new GridLayout(2, 1, 3, 3));
				{
					pnlReqDetails = new JPanel(new BorderLayout(3, 3));
					pnlNorthWest.add(pnlReqDetails, BorderLayout.NORTH);
					pnlReqDetails.setLayout(new SpringLayout());
					pnlReqDetails.setPreferredSize(new Dimension(100, 150));
					pnlReqDetails.setBorder(JTBorderFactory.createTitleBorder("Request Details"));
					/*{
						pnlReqDetailsWest = new JPanel();
						pnlReqDetails.add(pnlReqDetailsWest, BorderLayout.WEST);
						pnlReqDetailsWest.setLayout(new GridLayout(2, 1, 3, 3));
						{
							lblReqNo = new JLabel("Request No");
							pnlReqDetailsWest.add(lblReqNo);
						}
						{
							lblReqStat = new JLabel("Request Status");
							pnlReqDetailsWest.add(lblReqStat);
						}
					}
					{
						pnlReqDetailsCenter = new JPanel();
						pnlReqDetails.add(pnlReqDetailsCenter, BorderLayout.CENTER);
						pnlReqDetailsCenter.setLayout(new GridLayout(2, 1, 3, 3));
						{
							txtReqNo = new _JXTextField("Request No.");
							pnlReqDetailsCenter.add(txtReqNo);
						}
						{
							txtReqStat = new _JXTextField("Request Status");
							pnlReqDetailsCenter.add(txtReqStat);
						}
					}*/
					{
						lblReqNo = new JLabel("Request No");
						pnlReqDetails.add(lblReqNo);
					}
					{
						txtReqNo = new _JXTextField();
						pnlReqDetails.add(txtReqNo);
						txtReqNo.setPreferredSize(new Dimension(90, 0));
					}
					{
						lblReqStat = new JLabel("Request Status");
						pnlReqDetails.add(lblReqStat);
					}
					{
						txtReqStat = new _JXTextField();
						pnlReqDetails.add(txtReqStat);
					}
					{
						lblRequestBy = new JLabel("Request By");
						pnlReqDetails.add(lblRequestBy);
					}
					{
						txtRequestBy = new _JXTextField();
						pnlReqDetails.add(txtRequestBy);
					}
					{
						lblRemarks = new JLabel("Remarks");
						pnlReqDetails.add(lblRemarks);
					}
					{
						txtRemarks = new _JXTextField();
						pnlReqDetails.add(txtRemarks);
					}
					SpringUtilities.makeCompactGrid(pnlReqDetails, 4, 2, 3, 3, 3, 3, false);
				}
				{
					pnlCredit = new JPanel(new BorderLayout(5, 5));
					pnlNorthWest.add(pnlCredit, BorderLayout.CENTER);
					pnlCredit.setLayout(new SpringLayout());
					{
						pnlCreditFrm = new JPanel(new BorderLayout(5, 5));
						pnlCredit.add(pnlCreditFrm);
						//pnlCreditFrmAcct.setLayout(new SpringLayout());
						//pnlCreditFrm.setLayout(new GridLayout(1, 2, 3, 3));
						pnlCreditFrm.setBorder(JTBorderFactory.createTitleBorder("Credit From Account"));
						{
							pnlCreditFrmWest = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlCreditFrm.add(pnlCreditFrmWest, BorderLayout.WEST);
							{
								lblCreditFrmClient = new JLabel("Client");
								pnlCreditFrmWest.add(lblCreditFrmClient);
							}
							{
								lblCreditFrmUnit = new JLabel("Unit");
								pnlCreditFrmWest.add(lblCreditFrmUnit);
							}
							{
								lblCreditFrmProject = new JLabel("Project");
								pnlCreditFrmWest.add(lblCreditFrmProject);
							}
							{
								lblCreditFrmSeq = new JLabel("Seq. No");
								pnlCreditFrmWest.add(lblCreditFrmSeq);
							}
						}
						{
							pnlCreditFrmCenter = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlCreditFrm.add(pnlCreditFrmCenter, BorderLayout.CENTER);
							{
								pnlCreditFrmClient = new JPanel(new BorderLayout(5, 5));
								pnlCreditFrmCenter.add(pnlCreditFrmClient);
								{
									lookupCrdtFrmClient = new _JLookup(null, "Select Client", 0);
									pnlCreditFrmClient.add(lookupCrdtFrmClient, BorderLayout.WEST);
									lookupCrdtFrmClient.setPreferredSize(new Dimension(100, 0));
									lookupCrdtFrmClient.setLookupSQL(_CreditOfPayment.sqlCreditFrom());
									lookupCrdtFrmClient.setFilterName(true);
									lookupCrdtFrmClient.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){
												FncSystem.out("Display sql client", _CreditOfPayment.sqlCreditFrom());
												//clearCreditFrom();
												String entity_id = (String) data[0];
												String entity_name = (String) data[1];
												String unit_id = (String) data[2];
												String proj_id = (String) data[3];
												Integer seq_no = (Integer) data[4];
												String unit_desc = (String) data[5];
												String unit_status = (String) data[6];
												String proj_name = (String) data[7];

												lookupCrdtFrmClient.setValue(entity_id);
												txtCrdtFrmClient.setText(entity_name);
												txtCreditFrmUnitID.setText(unit_id);
												txtCreditFrmUnitDesc.setText(unit_desc);
												txtCreditFrmProjID.setText(proj_id);
												txtCreditFrmProject.setText(proj_name);
												txtCreditFrmSeq.setText(seq_no.toString());
												txtCreditFrmStat.setText(unit_status);
												//btngrpCrdtPymntFrm.clearSelection();
												//clearCreditTo();
												
												System.out.println(entity_id.equals("8826336945")); 
												System.out.println(entity_name);
												
												if (entity_id.equals("8826336945")) { //SPECIAL CASE; CREDIT OF PAYMENT DTD 06-05-2023
													
													System.out.println("pumasok dito!");
													_CreditOfPayment.displayLedgerPaymentSpecialCase(modelCrdtFromLedger, rowHeaderCrdtFromLedger, entity_id, unit_id, proj_id, seq_no.toString());
													scrollCreditFromLedgerTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromLedger.getRowCount())));
													//scrollCreditFromLedger.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromLedger.getRowCount())));
													tblCrdtFromLedger.packAll();
												}else {
													System.out.println("pumasok dito2!");
												_CreditOfPayment.displayLedgerPayment(modelCrdtFromLedger, rowHeaderCrdtFromLedger, entity_id, unit_id, proj_id, seq_no.toString());
												scrollCreditFromLedgerTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromLedger.getRowCount())));
												//scrollCreditFromLedger.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromLedger.getRowCount())));
												tblCrdtFromLedger.packAll();
												}
												
												_CreditOfPayment.displayOtherPayment(modelCrdtFromOther, rowHeaderCrdtFrmOther, entity_id, proj_id ,unit_id, seq_no.toString());
												scrollCreditFromOtherTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromOther.getRowCount())));
												//scrollCreditFromOtherTotal.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromOther.getRowCount())));
												tblCrdtFromOther.packAll();
												lookupCrdtToClient.setLookupSQL(_CreditOfPayment.sqlCreditTo(unit_id));

												computeTotalAmt();
											}
										}
									});
								}
								{
									txtCrdtFrmClient = new _JXTextField("Name");
									pnlCreditFrmClient.add(txtCrdtFrmClient, BorderLayout.CENTER);
								}
							}
							{
								pnlCreditFrmUnit = new JPanel(new BorderLayout(5, 5));
								pnlCreditFrmCenter.add(pnlCreditFrmUnit);
								{
									txtCreditFrmUnitID = new _JXTextField("ID");
									pnlCreditFrmUnit.add(txtCreditFrmUnitID, BorderLayout.WEST);
									txtCreditFrmUnitID.setPreferredSize(new Dimension(100, 0));
									txtCreditFrmUnitID.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									txtCreditFrmUnitDesc = new _JXTextField("Description");
									pnlCreditFrmUnit.add(txtCreditFrmUnitDesc, BorderLayout.CENTER);
								}
							}
							{
								pnlCreditFrmProject = new JPanel(new BorderLayout(5, 5));
								pnlCreditFrmCenter.add(pnlCreditFrmProject);
								{
									txtCreditFrmProjID = new _JXTextField("ID");
									pnlCreditFrmProject.add(txtCreditFrmProjID, BorderLayout.WEST);
									txtCreditFrmProjID.setPreferredSize(new Dimension(50, 0));
									txtCreditFrmProjID.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									txtCreditFrmProject = new _JXTextField("Proj. Name");
									pnlCreditFrmProject.add(txtCreditFrmProject, BorderLayout.CENTER);
								}
							}
							{
								pnlCreditFrmSeq = new JPanel(new BorderLayout(5, 5));
								pnlCreditFrmCenter.add(pnlCreditFrmSeq);
								{
									txtCreditFrmSeq = new _JXTextField("Seq");
									pnlCreditFrmSeq.add(txtCreditFrmSeq, BorderLayout.WEST);
									txtCreditFrmSeq.setPreferredSize(new Dimension(50, 0));
									txtCreditFrmSeq.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									lblCreditFrmStat = new JLabel("Status", JLabel.TRAILING);
									pnlCreditFrmSeq.add(lblCreditFrmStat);
								}
								{
									txtCreditFrmStat = new _JXTextField("Status");
									pnlCreditFrmSeq.add(txtCreditFrmStat, BorderLayout.EAST);
									txtCreditFrmStat.setPreferredSize(new Dimension(200, 0));
								}
							}
						}
					}
					{
						pnlCreditTo = new JPanel(new BorderLayout(5, 5));
						pnlCredit.add(pnlCreditTo);
						//pnlCreditFrmAcct.setLayout(new SpringLayout());
						//pnlCreditFrm.setLayout(new GridLayout(1, 2, 3, 3));
						//pnlCreditTo.setLayout(new GridLayout(1, 2, 3, 3));
						pnlCreditTo.setBorder(JTBorderFactory.createTitleBorder("Credit To Unit"));
						{
							pnlCreditToWest = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlCreditTo.add(pnlCreditToWest, BorderLayout.WEST);
							{
								lblCreditToClient = new JLabel("Client");
								pnlCreditToWest.add(lblCreditToClient);
							}
							{
								lblCreditToUnit = new JLabel("Unit");
								pnlCreditToWest.add(lblCreditToUnit);
							}
							{
								lblCreditToProject = new JLabel("Project");
								pnlCreditToWest.add(lblCreditToProject);
							}
							{
								lblCreditToSeq = new JLabel("Seq. No");
								pnlCreditToWest.add(lblCreditToSeq);
							}
						}
						{
							pnlCreditToCenter = new JPanel(new GridLayout(4, 1, 3, 3));
							pnlCreditTo.add(pnlCreditToCenter, BorderLayout.CENTER);
							{
								pnlCreditToClient = new JPanel(new BorderLayout(5, 5));
								pnlCreditToCenter.add(pnlCreditToClient, BorderLayout.CENTER);
								{
									lookupCrdtToClient = new _JLookup(null, "Select Client", 0);
									pnlCreditToClient.add(lookupCrdtToClient, BorderLayout.WEST);
									//lookupCrdtToClient.setEditable(setlookupEnabled());
									lookupCrdtToClient.setFilterName(true);
									lookupCrdtToClient.setPreferredSize(new Dimension(100, 0));
									lookupCrdtToClient.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){
												FncSystem.out("Display sql for the Client Credit to Unit", lookupCrdtToClient.getLookupSQL());
												
												String entity_id = (String) data[0];
												String entity_name = (String) data[1];
												String unit_id = (String) data[2];
												String proj_id = (String) data[3];
												Integer seq_no = (Integer) data[4];
												String unit_desc = (String) data[5];
												String unit_status = (String) data[6];
												String proj_name = (String) data[7];
												String pmt_scheme_id = (String) data[8];

												txtCrdtToClient.setText(entity_name);
												txtCreditToUnitID.setText(unit_id);
												txtCreditToUnitDesc.setText(unit_desc);
												txtCreditToProjID.setText(proj_id);
												txtCreditToProject.setText(proj_name);
												txtCreditToSeq.setText(seq_no.toString());
												txtCreditToStat.setText(unit_status);
												
											// MODIFIED BY MONIQUE DTD 2023-03-03 TO AUTOMATE COP PARTICULARS BASED ON BUYERTYPE	
											if(isSpotCash(lookupCrdtToClient.getValue(), txtCreditToProjID.getText(), txtCreditToUnitID.getText(), txtCreditToSeq.getText())) {	
												_CreditOfPayment.displayLedgerCrdtToSpotCash(modelCrdtToLedger, rowHeaderCrdtToLedger, lookupCrdtToClient.getValue(), txtCreditToUnitID.getText(), txtCreditToProjID.getText(), txtCreditToSeq.getText());
												scrollCreditToLedger.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtToLedger.getRowCount())));
												tblCrdtToLedger.packAll();
		
												_CreditOfPayment.displayCrdtToOther(modelCrdtToOther, rowHeaderCrdtToOther);
												tblCrdtToOther.packAll();
											
											}else {		
												//_CreditOfPayment.displayLedgerCrdtTo(modelCrdtToLedger, rowHeaderCrdtToLedger, lookupCrdtFrmClient.getValue(), txtCreditFrmUnitID.getText(), txtCreditFrmProjID.getText(), txtCreditFrmSeq.getText());
												_CreditOfPayment.displayLedgerCrdtTo(modelCrdtToLedger, rowHeaderCrdtToLedger, lookupCrdtToClient.getValue(), txtCreditToUnitID.getText(), txtCreditToProjID.getText(), txtCreditToSeq.getText());
												scrollCreditToLedger.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtToLedger.getRowCount())));
												tblCrdtToLedger.packAll();
												
												/*AutoApplyCredit(entity_id, proj_id, unit_id, seq_no.toString(), pmt_scheme_id);
												tblCrdtToLedger.packAll();*/
												
												_CreditOfPayment.displayCrdtToOther(modelCrdtToOther, rowHeaderCrdtToOther);
												tblCrdtToOther.packAll();
											}
											
												if(tabCreditPmtTo.getSelectedIndex() == 1){
													txtAmntToCredit.setValue(new BigDecimal("0.00"));
													txtAmntToCredit.setEditable(false);
												}else{
													txtAmntToCredit.setEditable(true);
												}
											}
										}
									});
								}
								{
									txtCrdtToClient = new _JXTextField("Name");
									pnlCreditToClient.add(txtCrdtToClient, BorderLayout.CENTER);
								}
							}
							{
								pnlCreditToUnit = new JPanel(new BorderLayout(5, 5));
								pnlCreditToCenter.add(pnlCreditToUnit, BorderLayout.CENTER);
								{
									txtCreditToUnitID = new _JXTextField("ID");
									pnlCreditToUnit.add(txtCreditToUnitID, BorderLayout.WEST);
									txtCreditToUnitID.setPreferredSize(new Dimension(100, 0));
									txtCreditToUnitID.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									txtCreditToUnitDesc = new _JXTextField("Description");
									pnlCreditToUnit.add(txtCreditToUnitDesc, BorderLayout.CENTER);
								}
							}
							{
								pnlCreditToProject = new JPanel(new BorderLayout(5, 5));
								pnlCreditToCenter.add(pnlCreditToProject, BorderLayout.CENTER);
								{
									txtCreditToProjID = new _JXTextField("ID");
									pnlCreditToProject.add(txtCreditToProjID, BorderLayout.WEST);
									txtCreditToProjID.setPreferredSize(new Dimension(50, 0));
									txtCreditToProjID.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									txtCreditToProject = new _JXTextField("Proj. Name");
									pnlCreditToProject.add(txtCreditToProject, BorderLayout.CENTER);
								}
							}
							{
								pnlCreditToSeq = new JPanel(new BorderLayout(5, 5));
								pnlCreditToCenter.add(pnlCreditToSeq, BorderLayout.CENTER);
								{
									txtCreditToSeq = new _JXTextField("Seq");
									pnlCreditToSeq.add(txtCreditToSeq, BorderLayout.WEST);
									txtCreditToSeq.setPreferredSize(new Dimension(50, 0));
									txtCreditToSeq.setHorizontalAlignment(JXTextField.CENTER);
								}
								{
									lblCreditToStat = new JLabel("Status", JLabel.TRAILING);
									pnlCreditToSeq.add(lblCreditToStat);
								}
								{
									txtCreditToStat = new _JXTextField("Status");
									pnlCreditToSeq.add(txtCreditToStat, BorderLayout.EAST);
									txtCreditToStat.setPreferredSize(new Dimension(200, 0));
								}
							}
						}	
					}
					SpringUtilities.makeCompactGrid(pnlCredit, 2, 1, 3, 3, 3, 3, false);
				}
				//SpringUtilities.makeCompactGrid(pnlNorthWest, 2, 1, 3, 3, 3, 3, false);
			}
			{
				pnlNorthEast = new JPanel();
				pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
				pnlNorthEast.setBorder(JTBorderFactory.createTitleBorder("Set Up Credit of Payment Details"));
				pnlNorthEast.setLayout(new GridLayout(2, 1, 3, 3));
				{
					tabCreditPmtFrom = new _JTabbedPane();
					pnlNorthEast.add(tabCreditPmtFrom, BorderLayout.NORTH);
					{
						pnlCreditFromLedger = new JPanel(new BorderLayout(5, 5));
						tabCreditPmtFrom.add("Credit From Ledger", pnlCreditFromLedger);

						{
							scrollCreditFromLedger = new _JScrollPaneMain();
							pnlCreditFromLedger.add(scrollCreditFromLedger, BorderLayout.CENTER);
							{
								modelCrdtFromLedger = new modelROP_OtherPmt();

								tblCrdtFromLedger = new _JTableMain(modelCrdtFromLedger);
								scrollCreditFromLedger.setViewportView(tblCrdtFromLedger);

							}
							{
								rowHeaderCrdtFromLedger = tblCrdtFromLedger.getRowHeader();
								rowHeaderCrdtFromLedger.setModel(new DefaultListModel());
								scrollCreditFromLedger.setRowHeaderView(rowHeaderCrdtFromLedger);
								scrollCreditFromLedger.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							scrollCreditFromLedgerTotal = new _JScrollPaneTotal(scrollCreditFromLedger);
							pnlCreditFromLedger.add(scrollCreditFromLedgerTotal, BorderLayout.SOUTH);
							{
								modelCrdtFromLedgerTotal = new modelROP_OtherPmt();
								modelCrdtFromLedgerTotal.addRow(new Object[] { null, "Total", null, null, null});

								tblCrdtFromLedgerTotal = new _JTableTotal(modelCrdtFromLedgerTotal, tblCrdtFromLedger);
								scrollCreditFromLedgerTotal.setViewportView(tblCrdtFromLedgerTotal);

								tblCrdtFromLedgerTotal.setTotalLabel(1);
							}
						}
					}
					{
						pnlCreditFromOther = new JPanel(new BorderLayout(5, 5));
						tabCreditPmtFrom.add("Credit From Other", pnlCreditFromOther);
						{
							scrollCreditFromOther = new _JScrollPaneMain();
							pnlCreditFromOther.add(scrollCreditFromOther, BorderLayout.CENTER);
							//scrollCreditFromOther.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

							{
								modelCrdtFromOther = new modelROP_Miscellaneous();
								tblCrdtFromOther = new _JTableMain(modelCrdtFromOther);
								scrollCreditFromOther.setViewportView(tblCrdtFromOther);

							}
							{
								rowHeaderCrdtFrmOther = tblCrdtFromOther.getRowHeader();
								rowHeaderCrdtFrmOther.setModel(new DefaultListModel());
								scrollCreditFromOther.setRowHeaderView(rowHeaderCrdtFrmOther);
								scrollCreditFromOther.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							scrollCreditFromOtherTotal = new _JScrollPaneTotal(scrollCreditFromOther);
							pnlCreditFromOther.add(scrollCreditFromOtherTotal, BorderLayout.SOUTH);
							{
								modelCrdtFromOtherTotal = new modelROP_Miscellaneous();
								modelCrdtFromOtherTotal.addRow(new Object[] { null, "Total", null, null, null});

								tblCrdtFromOtherTotal = new _JTableTotal(modelCrdtFromOtherTotal, tblCrdtFromLedger);
								scrollCreditFromOtherTotal.setViewportView(tblCrdtFromOtherTotal);

								tblCrdtFromOtherTotal.setTotalLabel(1);
							}
						}
					}
					{
						/*pnlCreditPayment = new JPanel();
						pnlNorthEast.add(pnlCreditPayment, BorderLayout.SOUTH);*/
						pnlCreditPayment = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlCreditPayment, BorderLayout.SOUTH);
						//pnlCreditPayment.setLayout(new GridLayout(2, 1, 3, 3));
						{
							tabCreditPmtTo = new _JTabbedPane();
							pnlCreditPayment.add(tabCreditPmtTo, BorderLayout.CENTER);

							{
								pnlCreditToLegder = new JPanel(new BorderLayout(5, 5));
								tabCreditPmtTo.add("Credit to Ledger", pnlCreditToLegder);
								{
									scrollCreditToLedger = new JScrollPane();
									pnlCreditToLegder.add(scrollCreditToLedger, BorderLayout.CENTER);
									scrollCreditToLedger.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									{
										modelCrdtToLedger = new modelCreditofPayment_CrdtToLedger();
										tblCrdtToLedger = new _JTableMain(modelCrdtToLedger);
										scrollCreditToLedger.setViewportView(tblCrdtToLedger);
										tblCrdtToLedger.hideColumns("Pay Part ID", "Part ID", "Receipt No");
										tblCrdtToLedger.addPropertyChangeListener(new PropertyChangeListener() {

											public void propertyChange(PropertyChangeEvent arg0) {
												_JTableMain table = (_JTableMain) arg0.getSource();
												String propertyName = arg0.getPropertyName();

												if (!propertyName.equals("swingx.rollover")) {
													// System.out.printf("%n%n%s%n",
													// propertyName);
												}
												if (propertyName.equals("tableCellEditor")) {
													int column = table.convertColumnIndexToModel(table.getEditingColumn());

													if (column != -1 && modelCrdtToLedger.getColumnClass(column).equals(BigDecimal.class)) {
														Object oldValue = null;
														try {
															oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
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
															computeAmtToCredit();
															tblCrdtToLedger.packAll();
														}
													}
												}
											}
										});

										modelCrdtToLedger.addTableModelListener(new TableModelListener() {

											@Override
											public void tableChanged(TableModelEvent arg0) {

												/*if(arg0.getType() == TableModelEvent.INSERT){
												((DefaultListModel) rowHeaderCrdtToLedger.getModel()).addElement(modelCrdtToLedger.getRowCount());
											}*/

												if(modelCrdtToLedger.getRowCount() == 0){
													rowHeaderCrdtToLedger.setModel(new DefaultListModel());
												}

											}
										});

									}
									{
										rowHeaderCrdtToLedger = tblCrdtToLedger.getRowHeader();
										rowHeaderCrdtToLedger.setModel(new DefaultListModel());
										scrollCreditToLedger.setRowHeaderView(rowHeaderCrdtToLedger);
										scrollCreditToLedger.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								}
							}
							{
								pnlCreditToOther = new JPanel(new BorderLayout(5, 5));
								tabCreditPmtTo.add("Credit to Other", pnlCreditToOther);
								{
									scrollCreditToOther = new JScrollPane();
									pnlCreditToOther.add(scrollCreditToOther, BorderLayout.CENTER);
									scrollCreditToOther.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

									{
										modelCrdtToOther = new modelCreditofPayment_CrdtPymntOther();
										tblCrdtToOther = new _JTableMain(modelCrdtToOther);
										scrollCreditToOther.setViewportView(tblCrdtToOther);
										
										tblCrdtToOther.addPropertyChangeListener(new PropertyChangeListener() {

											public void propertyChange(PropertyChangeEvent arg0) {
												_JTableMain table = (_JTableMain) arg0.getSource();
												String propertyName = arg0.getPropertyName();

												if (!propertyName.equals("swingx.rollover")) {
													// System.out.printf("%n%n%s%n",
													// propertyName);
												}
												if (propertyName.equals("tableCellEditor")) {
													int column = table.convertColumnIndexToModel(table.getEditingColumn());

													if (column != -1 && modelCrdtToOther.getColumnClass(column).equals(BigDecimal.class)) {
														Object oldValue = null;
														try {
															oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
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
															computeAmtToCredit();
														}
													}
												}
											}
										});

										modelCrdtToOther.addTableModelListener(new TableModelListener() {

											@Override
											public void tableChanged(TableModelEvent e) {

												/*if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel) rowHeaderCrdtToOther.getModel()).addElement(modelCrdtToOther.getRowCount());
											}*/

												if(modelCrdtToOther.getRowCount() == 0){
													rowHeaderCrdtToOther.setModel(new DefaultListModel());
												}

											}
										});
									}
									
									
									{
										rowHeaderCrdtToOther = tblCrdtToOther.getRowHeader();
										rowHeaderCrdtToOther.setModel(new DefaultListModel());
										scrollCreditToOther.setRowHeaderView(rowHeaderCrdtToOther);
										scrollCreditToOther.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
									
									
								}
							}
						}
						{
							pnlAmtToCredit = new JPanel(new BorderLayout(5, 5));
							pnlCreditPayment.add(pnlAmtToCredit, BorderLayout.SOUTH);
							{
								lblAmntToCredit = new JLabel("Amount to Credit", JLabel.TRAILING);
								pnlAmtToCredit.add(lblAmntToCredit);
							}
							{
								txtAmntToCredit = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlAmtToCredit.add(txtAmntToCredit, BorderLayout.EAST);
								txtAmntToCredit.setPreferredSize(new Dimension(100, 0));
								txtAmntToCredit.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtAmntToCredit.setValue(new BigDecimal("0.00"));
								txtAmntToCredit.addKeyListener(new KeyAdapter() {
									
									public void keyReleased(KeyEvent e){
										BigDecimal credit_amt = (BigDecimal) ((_JXFormattedTextField) e.getSource()).getValued();
										
										if(computeTotalPmt().doubleValue() >= credit_amt.doubleValue()){
											
										}else{
											JOptionPane.showMessageDialog(pnlNorth, "Amount to Credit Cannot be larger than total payments");
											txtAmntToCredit.setValue(new BigDecimal("0.00"));
										}
									}
								});
							}
						}
					}
				}
			}
			//SpringUtilities.makeCompactGrid(pnlNorth, 1, 2, 3, 3, 3, 3, false);
		}
		{
			pnlSouth = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new GridLayout(1, 5, 5, 5));
			{
				btnNew = new JButton("New");
				pnlSouth.add(btnNew);
				btnNew.setActionCommand(btnNew.getText());
				btnNew.setMnemonic(KeyEvent.VK_N);
				btnNew.addActionListener(this);
			}
			{
				btnSave = new JButton("Save");
				pnlSouth.add(btnSave);
				btnSave.setActionCommand(btnSave.getText());
				btnSave.setMnemonic(KeyEvent.VK_V);
				btnSave.addActionListener(this);
			}
			{
				btnCancelReq = new JButton("Cancel Request");
				pnlSouth.add(btnCancelReq);
				btnCancelReq.setActionCommand(btnCancelReq.getText());
				btnCancelReq.setMnemonic(KeyEvent.VK_C);
				btnCancelReq.addActionListener(this);
			}
			{
				btnSearch = new JButton("Search");
				pnlSouth.add(btnSearch);
				btnSearch.setActionCommand(btnSearch.getText());
				btnSearch.setMnemonic(KeyEvent.VK_S);
				btnSearch.addActionListener(this);
			}
			{
				btnPost = new JButton("Post");
				pnlSouth.add(btnPost);
				btnPost.setActionCommand(btnPost.getText());
				btnPost.setMnemonic(KeyEvent.VK_R);
				btnPost.addActionListener(this);
			}
			{
				btnClear = new JButton("Clear");
				pnlSouth.add(btnClear);
				btnClear.setActionCommand(btnClear.getText());
				btnClear.setMnemonic(KeyEvent.VK_E);
				btnClear.addActionListener(this);
			}
			{
				btnPreview = new JButton("Preview");
				pnlSouth.add(btnPreview);
				btnPreview.setActionCommand(btnPreview.getText());
				btnPreview.setMnemonic(KeyEvent.VK_W);
				btnPreview.addActionListener(this);
				btnPreview.setEnabled(false);
			}
		}
		//SpringUtilities.makeCompactGrid(pnlMain, 2, 1, 3, 3, 3, 3, false);
		//this.setComponentsEditable(pnlNorth, false);
		btnState(true, false, false, true, false, false);
		//rbtnState(false, false, false, false);
		loadComponents();
	}//XXX END OF INIT GUI

	public void loadComponents(){
		//this.setComponentsEnabled(pnlNorth, false);
		this.setComponentsEditable(pnlNorth, false);
		btnState(true, false, false, true, false, false);
		//rbtnState(false, false, false, false);
	}

	public void btnState(Boolean sNew, Boolean sSave, Boolean sCancelReq, Boolean sSearch, Boolean sPost, Boolean sClear){
		btnNew.setEnabled(sNew);
		btnSave.setEnabled(sSave);
		btnCancelReq.setEnabled(sCancelReq);
		btnSearch.setEnabled(sSearch);
		btnPost.setEnabled(sPost);
		btnClear.setEnabled(sClear);
	}

	private void rbtnState(Boolean sFromLegder, Boolean sFromOther, Boolean sToLedger, Boolean sToOther){
		rbtnCrdtFrmLedgerPymnt.setEnabled(sFromLegder);
		rbtnCrdtFrmOtherPymnt.setEnabled(sFromOther);
		//rbtnCrdtPymntToLedger.setEnabled(sToLedger);
		//rbtnCrdtPymntToOther.setEnabled(sToOther);
	}

	public void newCOP(){
		btnState(false, true, false, false, false, true);
		//rbtnState(true, true, true, true);
		lookupCrdtFrmClient.setEditable(true);
		lookupCrdtToClient.setEditable(true);
		txtAmntToCredit.setEditable(true);
		tabCreditPmtFrom.setEnabled(true);
		tabCreditPmtTo.setEnabled(true);
		txtRequestBy.setEditable(true);
		txtRemarks.setEditable(true);
		/*txtTotalPymnt.setEditable(false);
		txtAmntToCredit.setEditable(false);*/
	}

	public void clearCOP(){
		loadComponents();
		clearCreditFrom();
		clearCreditTo();
		txtReqNo.setText("");
		txtReqStat.setText("");
		txtRequestBy.setText("");
		txtRemarks.setText("");
		txtRequestBy.setEditable(false);
		txtRemarks.setEditable(false);
		btnPreview.setEnabled(false);
	}

	private void clearCreditFrom(){
		lookupCrdtFrmClient.setValue(null);
		txtCrdtFrmClient.setText("");
		txtCreditFrmUnitID.setText("");
		txtCreditFrmUnitDesc.setText("");
		txtCreditFrmProjID.setText("");
		txtCreditFrmProject.setText("");
		txtCreditFrmSeq.setText("");
		txtCreditFrmStat.setText("");

		//txtTotalPymnt.setValue(new BigDecimal("0.00"));
		/*btngrpCrdtPymntFrm.clearSelection();
		try{
			scrollCreditsPaymentsFrm.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderCrdtFromLedger = tblCreditsPaymentsFrm.getRowHeader();
			scrollCreditsPaymentsFrm.setRowHeaderView(rowHeaderCrdtFromLedger);
		} catch (NullPointerException e) {}*/
		
		modelCrdtFromLedger.clear();
		rowHeaderCrdtFromLedger.setModel(new DefaultListModel());
		scrollCreditFromLedgerTotal.setRowHeaderView(FncTables.getRowHeader_Footer(""));

		modelCrdtFromOther.clear();
		rowHeaderCrdtFrmOther.setModel(new DefaultListModel());
		scrollCreditFromOtherTotal.setRowHeaderView(FncTables.getRowHeader_Footer(""));
		
		modelCrdtFromLedgerTotal.setValueAt(new BigDecimal("0.00"), 0, 3);
		modelCrdtFromOtherTotal.setValueAt(new BigDecimal("0.00"), 0, 3);
		
	}

	private void clearCreditTo(){
		lookupCrdtToClient.setValue(null);
		txtCrdtToClient.setText("");
		txtCreditToUnitID.setText("");
		txtCreditToUnitDesc.setText("");
		txtCreditToProjID.setText("");
		txtCreditToProject.setText("");
		txtCreditToSeq.setText("");
		txtCreditToStat.setText("");

		/*txtAmntToCredit.setValue(new BigDecimal("0.00"));
		btngrpCrdtPymntTo.clearSelection();
		try{
			scrollCreditsPaymentsTo.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
			rowHeaderCrdtToLedger = tblCreditsPaymentsTo.getRowHeader();
			scrollCreditsPaymentsTo.setRowHeaderView(rowHeaderCrdtToLedger);
		} catch (NullPointerException e) {}*/
		txtAmntToCredit.setValue(new BigDecimal("0.00"));
		
		modelCrdtToLedger.clear();
		modelCrdtToOther.clear();
		
	}

	public String getRequestNo(){//Get the next request no from req_rt_request_header 
		pgSelect db = new pgSelect();

		/*String sql = "select substring (extract(year from current_date)::text from 3 for 2)|| \n" + 
				"trim(to_char(extract(month from current_date),'00'))||trim(to_char(extract(day from current_date),'00'))||'-'||\n" + 
				"trim(to_char(count(request_no)+1,'00000')) from req_rt_request_header where request_date::date  = current_date";*/
		String sql = "select trim(get_new_request_no())";

		FncSystem.out("Display request no", sql);
		db.select(sql);
		return (String) db.getResult()[0][0];
	}

	public void computeAmtToCredit(){//Computation of amount to credit in the Credit to payments Table 
		BigDecimal amt_to_credit = new BigDecimal("0.00");

		/*if(rbtnCrdtPymntToOther.isSelected()){
			for(int x = 0; x<modelCrdtToOther.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelCrdtToOther.getValueAt(x, 0);
				if(isSelected){
					amt_to_credit = amt_to_credit.add((BigDecimal) modelCrdtToOther.getValueAt(x, 3));
					amt_to_credit = amt_to_credit.add(new BigDecimal("0.00"));
				}
			}
		}
		if(rbtnCrdtPymntToLedger.isSelected()){
			for (int x= 0; x<modelCrdtToLedger.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelCrdtToLedger.getValueAt(x, 0);
				if(isSelected){
					amt_to_credit = amt_to_credit.add((BigDecimal) modelCrdtToLedger.getValueAt(x, 3));
					amt_to_credit = amt_to_credit.add(new BigDecimal("0.00"));
				}
			}
		}*/
		if(tabCreditPmtTo.getSelectedIndex() == 0){
			for(int x= 0; x<modelCrdtToLedger.getRowCount(); x++){
				if(isSelected){
					amt_to_credit = amt_to_credit.add((BigDecimal) modelCrdtToLedger.getValueAt(x, 2));
					amt_to_credit = amt_to_credit.add(new BigDecimal("0.00"));
				}
			}
			
		}else{
			for(int x= 0; x<modelCrdtToOther.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelCrdtToOther.getValueAt(x, 0);
				if(isSelected){
					amt_to_credit = amt_to_credit.add((BigDecimal) modelCrdtToOther.getValueAt(x, 3));
					amt_to_credit = amt_to_credit.add(new BigDecimal("0.00"));
				}
			}
		}
		
		txtAmntToCredit.setValue(amt_to_credit);
	}
	
	public BigDecimal computeTotalPmt(){
		BigDecimal total_pmt = new BigDecimal("0.00");
		
		if(tabCreditPmtFrom.getSelectedIndex() == 0){
			for(int x = 0; x<modelCrdtFromLedger.getRowCount(); x++){
				total_pmt = total_pmt.add((BigDecimal) modelCrdtFromLedger.getValueAt(x, 3));
				total_pmt = total_pmt.add(new BigDecimal("0.00"));
			}
		}
		if(tabCreditPmtFrom.getSelectedIndex() == 1){
			for (int x = 0; x<modelCrdtFromOther.getRowCount(); x++){
				total_pmt = total_pmt.add((BigDecimal) modelCrdtFromOther.getValueAt(x, 3));
				total_pmt = total_pmt.add(new BigDecimal("0.00"));
			}
		}
		System.out.printf("Display Total PmtP: %s%n", total_pmt);
		return total_pmt;
	}

	private void AutoApplyCredit(String entity_id, String proj_id, String unit_id, String seq_no, String pmt_scheme_id){//auto apply the credit
		pgSelect db = new pgSelect();
		System.out.println("Dumaan dito sa Auto Apply Credit");
		
		/*String sql = "select pay_part_id,\n" + 
				"(case when pay_part_id = '106' then get_res_of_scheme('"+pmt_scheme_id+"') - (select coalesce(sum(amount), 0.00) from rf_payments where entity_id = '"+entity_id+"' and pbl_id = getinteger('"+unit_id+"')::VARCHAR and proj_id = '"+proj_id+"' and seq_no = "+seq_no+" and pay_part_id = '106' and status_id = 'A')\n" + 
				"      when pay_part_id = '033' then get_dp_amount('"+entity_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '"+proj_id+"') - (select coalesce(sum(amount), 0.00) from rf_payments where entity_id = '"+entity_id+"' and pbl_id = getinteger('"+unit_id+"')::VARCHAR and proj_id = '"+proj_id+"' and seq_no = "+seq_no+" and pay_part_id = '033' and status_id = 'A')\n" + 
				"      when pay_part_id = '040' then get_loan_availed('"+entity_id+"', getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '"+proj_id+"') - (select coalesce(sum(amount), 0.00) from rf_payments where entity_id = '"+entity_id+"' and pbl_id = getinteger('"+unit_id+"')::VARCHAR and proj_id = '"+proj_id+"' and seq_no = "+seq_no+" and pay_part_id = '040' and status_id = 'A')\n" + 
				"      else 0.00 end)\n" + 
				"from mf_pay_particular \n" + 
				"where pay_part_id in ('106', '033', '040')\n" + 
				"order by apply_order;";*/
		
		String sql = "SELECT a.part_id, \n"+
				 	 "COALESCE(SUM(amount) - (SELECT COALESCE(SUM(amount), 0.00) \n" + 
				 	 "				 		  FROM rf_client_ledger \n" + 
				 	 "				 		  WHERE entity_id = a.entity_id\n" + 
				 	 "				 		  AND proj_id = a.proj_id\n" + 
				 	 "				 		  AND pbl_id = a.pbl_id\n" + 
				 	 "				 		  AND seq_no = a.seq_no\n" + 
				 	 "				 		  AND part_id = a.part_id \n" + 
				 	 "				 		  AND status_id = 'A'), 0.00)\n" + 
				 	 "FROM rf_client_schedule a \n" + 
				 	 "WHERE a.entity_id = '"+entity_id+"' \n" + 
				 	 "AND a.proj_id = '"+proj_id+"' \n" + 
				 	 "AND a.pbl_id = getinteger('"+unit_id+"')::VARCHAR \n" + 
				 	 "AND a.seq_no = "+seq_no+" \n" + 
				 	 "AND a.status_id = 'A' \n" + 
				 	 "GROUP BY a.part_id, a.entity_id, a.proj_id, a.pbl_id, a.seq_no\n" + 
				 	 "ORDER BY a.part_id";

		db.select(sql);

		//FncSystem.out("Display Credit Payments", sql);
		
		if(db.isNotNull()){
			for(int x= 0; x<modelCrdtToLedger.getRowCount(); x++){

				String pay_part_id = (String) db.getResult()[x][0];
				if(pay_part_id != null){
					BigDecimal amount = (BigDecimal) db.getResult()[x][1];

					mapParticulars.put(pay_part_id, amount);
				}
			}
		}

		BigDecimal amt_for_credit = (BigDecimal) txtAmntToCredit.getValued();


		for(int x=0; x<modelCrdtToLedger.getRowCount(); x++){
			String part_id = (String) modelCrdtToLedger.getValueAt(x, 1);
			String pay_part_id = (String) modelCrdtToLedger.getValueAt(x, 0);
			BigDecimal deduction = mapParticulars.get(pay_part_id);
			
			//System.out.printf("Display x: %s%n", x);
			
			if(deduction != null){
				if(amt_for_credit.compareTo(deduction) > 0){
					modelCrdtToLedger.setValueAt(deduction, x, 2);
					amt_for_credit = amt_for_credit.subtract(deduction);
				}else{
					modelCrdtToLedger.setValueAt(amt_for_credit, x, 2);
					amt_for_credit = amt_for_credit.subtract(amt_for_credit);
				}
			}
		}
	}

	public void computeTotalAmt(){//Computation of of Total Payment in the Credit from payments Table based on the selected radiobutton
		BigDecimal total_pymnt_ledger = new BigDecimal("0.00");
		BigDecimal total_pmt_other = new BigDecimal("0.00");

		for(int x = 0; x< modelCrdtFromLedger.getRowCount(); x++){
			total_pymnt_ledger = total_pymnt_ledger.add((BigDecimal) modelCrdtFromLedger.getValueAt(x, 3));
			total_pymnt_ledger = total_pymnt_ledger.add(new BigDecimal("0.00"));
		}
		
		for(int x = 0; x< modelCrdtFromOther.getRowCount(); x++){
			/*Boolean isSelected = (Boolean) modelCrdtFromOther.getValueAt(x, 0);
			if(isSelected){*/
				total_pmt_other = total_pmt_other.add((BigDecimal) modelCrdtFromOther.getValueAt(x, 3));
				total_pmt_other = total_pmt_other.add(new BigDecimal("0.00"));
			//}
		}

		modelCrdtFromLedgerTotal.setValueAt(total_pymnt_ledger, 0, 3);
		modelCrdtFromOtherTotal.setValueAt(total_pmt_other, 0, 3);
	}

	private String getCreditType(){//Assigning of Credit Type Based on the Credited Account From and the Unit Credit To
		String crdt_type = "";
		/*if(rbtnCrdtFrmLedgerPymnt.isSelected() && rbtnCrdtPymntToOther.isSelected()){ //From Ledger to Other
			crdt_type = "A";
		}
		if(rbtnCrdtFrmLedgerPymnt.isSelected() && rbtnCrdtPymntToLedger.isSelected()){ //From Ledger to Ledger
			crdt_type = "B";
		}
		if(rbtnCrdtFrmOtherPymnt.isSelected() && rbtnCrdtPymntToLedger.isSelected()){ //From Other to Ledger	
			crdt_type = "C";
		}
		if(rbtnCrdtFrmOtherPymnt.isSelected() && rbtnCrdtPymntToOther.isSelected()){ //From Other to Other
			crdt_type = "D";
		}*/
		if(tabCreditPmtFrom.getSelectedIndex() == 0 && tabCreditPmtTo.getSelectedIndex() == 1){// FROM LEDGER TO OTHER	
			crdt_type = "A"; 
		}
		if(tabCreditPmtFrom.getSelectedIndex() == 0 && tabCreditPmtTo.getSelectedIndex() == 0){//FROM LEDGER TO LEDGER
			crdt_type = "B"; //WALA PANG SAMPLE DATA DITO
		}
		if(tabCreditPmtFrom.getSelectedIndex() == 1 && tabCreditPmtTo.getSelectedIndex() == 0){//FROM OTHER TO LEDGER
			crdt_type = "C"; 
		}
		if(tabCreditPmtFrom.getSelectedIndex() == 1 && tabCreditPmtTo.getSelectedIndex() == 1){//FROM OTHER TO OTHER
			crdt_type = "D"; 
		}
		return crdt_type;
	}

	private boolean isOtherPaySelectedHasValue() {//Check to see if the amount in the Credit to Payment (Other Payment Table) has value
		int count  =0;
		String particulars = "";
		for (int x  =0;x<tblCreditsPaymentsTo.getRowCount();x++){
			if (tblCreditsPaymentsTo.getValueAt(x, 0).toString().equals("true")&&
					Double.valueOf(tblCreditsPaymentsTo.getValueAt(x, 3).toString())==0)
			{
				particulars  = particulars +"\n" + tblCreditsPaymentsTo.getValueAt(x, 2).toString();
				count++;
			}
		}
		if (count ==0){
			return true;
		}
		JOptionPane.showMessageDialog(null, "The ff. particulars selected must have value :\n"+particulars,"Save",JOptionPane.ERROR_MESSAGE);
		return false;
	}

	private boolean paymentsequaltocredit() { //Checks if Total Payment is equal to Amount to Credit
		return  Double.valueOf(txtAmntToCredit.getText()).equals(Double.valueOf(txtTotalPymnt.getText()));
	}

	private String getResFee(String entity_id, String unit_id, String proj_id, String seq_no){//Compute reservation fee based on the entity_id and unit_id

		String sql = "select cast(sum(amount) as char(20)) from rf_client_schedule where entity_id  = '"+entity_id+"' and unit_id = '"+unit_id+"' and proj_id = '"+proj_id+"' and seq_no = "+seq_no+" and part_id  = '012'";
		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display reservation", sql);
		return (String) db.getResult()[0][0];

	}

	public boolean toSave(){//Checks to see if required fields or conditions have been met before saving

		BigDecimal total_payment = 	computeTotalPmt();
		BigDecimal amt_to_credit = (BigDecimal) txtAmntToCredit.getValued();
		
		//Double resfee =  Double.valueOf(getResFee(lookupCrdtFrmClient.getValue(), txtCreditFrmUnitID.getText(), txtCreditFrmProjID.getText(), txtCreditFrmSeq.getText())).doubleValue();
		//Double final_amount = total_payment - resfee;

		if(lookupCrdtFrmClient.getValue() == null || lookupCrdtToClient.getValue() == null){ 
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Complete Details to Continue:\nCredit From Account and Credit to Unit"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*if(btngrpCrdtPymntFrm.isSelected(null)){ //Checks if the a radio button from Credit Payments From is selected
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please select Credits Payments From"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(btngrpCrdtPymntTo.isSelected(null)){//Checks if the a radio button from Credit Payments to is selected
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please select Credits Payments To"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		if(computeTotalPmt().doubleValue() == 0){//Checks if the Total Payments made is = 0
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Total Payment is ZERO\nCannot Save"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(amt_to_credit.doubleValue() == 0){//Check if the amount to credti = 0
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Amount to Credit"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*if(!isOtherPaySelectedHasValue()){//CHECKS IF THE AMOUNT OF THE SELECTED PARTICULAR IN THE CREDIT TO UNIT OTHER PAYMENTS IS EQUAL TO 0
			return false;
		}*/
		if(amt_to_credit.doubleValue() > total_payment.doubleValue()){//Check if the value of amount to credit is greater than the total payments made
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Amount for Credit cannot be greater than total payments"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		//if(getCrdtPymntFrm().equals("FROMLEGDER") && getCrdtPymntTo().equals("TOLEDGER") 
		if(getCreditType().equals("B") && txtCreditFrmUnitID.getText().equals(txtCreditToUnitID.getText()) && !txtCreditFrmStat.getText().equals("Cancelled")){
			JOptionPane.showMessageDialog(null, "Unit must be cancelled first in order to apply from ledger to ledger","Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		//if(getCrdtPymntFrm().equals("FROMLEGDER") && getCrdtPymntTo().equals("TOLEDGER")
		if(getCreditType().equals("B") && txtCreditFrmUnitID.getText().equals(txtCreditToUnitID.getText()) && !txtCreditFrmStat.getText().equals("Cancelled") && !paymentsequaltocredit()){
			JOptionPane.showMessageDialog(null, "When Applying from ledger to ledger with same unit, Amount to Credit must be equal to Total Payment Amount.","Save",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		/*if((getCreditType().equals("A") || getCreditType().equals("B")) && (!txtCreditFrmStat.getText().equals("Cancelled") && (amt_to_credit > final_amount))){
			JOptionPane.showMessageDialog(null, "Reservation fee can only be credited if account is cancelled\nPlease reduce amount to credit to continue\n" +
					"Maximum allowed is : " + final_amount ,"Save",JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		
		return true;
	}

	private void displayCreditToUnit(String req_no){
		pgSelect db = new pgSelect();
		String sql = "select trim(a.new_entity_id) as \"Entity ID\", trim(get_client_name(a.new_entity_id)) as \"Name\", \n" + 
				"trim(a.new_unit_id) as \"Unit ID\", trim(a.new_proj_id) as \"Proj. ID\", \n" + 
				"a.new_seq_no as \"Seq. No\", trim(get_unit_description(a.new_unit_id)) as \"Description\", \n" + 
				"trim(get_project_name(a.new_proj_id)) as \"Proj. Name\", trim(c.status_desc) as \"Status Desc\" \n" + 
				"from req_rt_request_header a\n" + 
				"left join rf_sold_unit b on b.entity_id = a.new_entity_id and b.pbl_id = getinteger(a.new_unit_id)::VARCHAR and b.projcode = a.new_proj_id and b.seq_no = a.new_seq_no\n" + 
				"left join mf_buyer_status c on c.byrstatus_id = b.currentstatus\n" + 
				"where request_no = '"+req_no+"'";
		
		FncSystem.out("Display Credit to Unit Details", sql);
		db.select(sql);
		
		if(db.isNotNull()){
			String entity_id = (String) db.getResult()[0][0];
			String entity_name = (String) db.getResult()[0][1];
			String unit_id = (String) db.getResult()[0][2];
			String proj_id = (String) db.getResult()[0][3];
			Integer seq_no = (Integer) db.getResult()[0][4];
			String unit_desc = (String) db.getResult()[0][5];
			String proj_name = (String) db.getResult()[0][6];
			String unit_status = (String) db.getResult()[0][7];

			lookupCrdtToClient.setValue(entity_id);
			txtCrdtToClient.setText(entity_name);
			txtCreditToUnitID.setText(unit_id);
			txtCreditToUnitDesc.setText(unit_desc);
			txtCreditToProjID.setText(proj_id);
			txtCreditToProject.setText(proj_name);
			txtCreditToSeq.setText(seq_no.toString());
			txtCreditToStat.setText(unit_status);
			System.out.println("Dumaan dito");
		}
	}
	
	// ADDED BY MONIQUE DTD 2023-03-03; COP PARTICULARS FOR SPOTCASH BUYER 
	public static Boolean isSpotCash(String entity_id, String proj_id, String unit_id, String seq_no) {
		
		String SQL = "SELECT EXISTS (SELECT * FROM rf_sold_unit \n" +
				     "WHERE entity_id = '"+entity_id+"' \n" +
				     "AND projcode = '"+proj_id+"'  \n" + 
				     "AND pbl_id = getinteger('"+unit_id+"')::VARCHAR  \n" + 
				     "AND seq_no::VARCHAR = '"+seq_no+"'  \n" + 
				     "AND buyertype = '02' \n" +
					 "AND TRIM(status_id) = 'A')"; 
		
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		return (Boolean) db.getResult()[0][0];
		
	}
	
	private void previewCreditOfPayment(){
		Map<String, Object> mapRequest = new HashMap<String, Object>();
		mapRequest.put("request_no", txtReqNo.getText().trim());
		mapRequest.put("request_type", "Credit of Payment");
		
		FncReport.generateReport("/Reports/rptCreditOfPayment.jasper", String.format("Credit of Payment"), mapRequest);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("New")){
			newCOP();
		}

		if(actionCommand.equals("Save")){
			if(toSave()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to save request?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					String req_no = getRequestNo();
					_CreditOfPayment.saveRtReqHeader(req_no, txtRequestBy.getText(), lookupCrdtFrmClient.getValue(), txtCreditFrmProjID.getText(), txtCreditFrmUnitID.getText(), txtCreditFrmSeq.getText(),
							lookupCrdtToClient.getValue(),txtCreditToProjID.getText(), txtCreditToUnitID.getText(), txtCreditToSeq.getText(), txtRemarks.getText()); 
					_CreditOfPayment.saveReqCreditHD(req_no, lookupCrdtFrmClient.getValue(), txtCreditFrmUnitID.getText(),txtCreditFrmProjID.getText(), txtCreditFrmSeq.getText(), txtCreditToUnitID.getText(), getCreditType(), (BigDecimal) txtAmntToCredit.getValued());
					_CreditOfPayment.saveReqCreditDL(req_no, getCreditType(), modelCrdtFromOther, modelCrdtToLedger , modelCrdtToOther, (BigDecimal) txtAmntToCredit.getValued());

					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Request has been Saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
					clearCOP();
				}
			}
		}
		
		if(actionCommand.equals("Cancel Request")){//CANCELATION OF REQUEST
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to cancel request?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				_CreditOfPayment.cancelCOP(txtReqNo.getText());
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Request was succesfully cancelled", actionCommand, JOptionPane.INFORMATION_MESSAGE);
				clearCOP();
			}
		}
		
		if(actionCommand.equals("Search")){
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null , "Request", _CreditOfPayment.sqlSearch(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object [] data = dlg.getReturnDataSet();

			FncSystem.out("Display sql For the Search", _CreditOfPayment.sqlSearch());	
			try{
				String req_no = (String) data[0];
				String entity_id = (String) data[1];
				String entity_name = (String) data[2];
				String unit_id = (String) data[3];
				String proj_id = (String) data[4];
				Integer seq_no = (Integer) data[5];
				String unit_desc = (String) data[6];
				String proj_name = (String) data[7];
				String unit_status = (String) data[8];
				String req_status = (String) data[9];
				String crdt_type = (String) data[11];
				BigDecimal crdt_amt = (BigDecimal) data[12];
				String request_by = (String) data[13];
				String remarks = (String) data[14];

				txtReqNo.setText(req_no);
				txtReqStat.setText(req_status);
				lookupCrdtFrmClient.setValue(entity_id);
				txtCrdtFrmClient.setText(entity_name);
				txtCreditFrmUnitID.setText(unit_id);
				txtCreditFrmUnitDesc.setText(unit_desc);
				txtCreditFrmProjID.setText(proj_id);
				txtCreditFrmProject.setText(proj_name);
				txtCreditFrmSeq.setText(seq_no.toString());
				txtCreditFrmStat.setText(unit_status);
				txtAmntToCredit.setValue(crdt_amt);
				txtRequestBy.setText(request_by);
				txtRemarks.setText(remarks);

				displayCreditToUnit(req_no);
				btnPreview.setEnabled(true);

				if(crdt_type.equals("A")){//LEDGER TO OTHER
					//rbtnCrdtFrmLedgerPymnt.setSelected(true);
					txtAmntToCredit.setEditable(false);
					//rbtnCrdtPymntToOther.setSelected(true);
					tabCreditPmtFrom.setSelectedIndex(0);
					tabCreditPmtTo.setSelectedIndex(1);
					tabCreditPmtFrom.setEnabled(false);
					tabCreditPmtTo.setEnabled(false);
					
					_CreditOfPayment.displayLedgerPayment(modelCrdtFromLedger, rowHeaderCrdtFromLedger, entity_id, unit_id, proj_id, seq_no.toString());
					scrollCreditFromLedgerTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromLedger.getRowCount())));
					//scrollCreditFromLedger.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromLedger.getRowCount())));
					tblCrdtFromLedger.packAll();
					computeTotalAmt();
					
					_CreditOfPayment.displaySavedCrdtToOther(modelCrdtToOther, rowHeaderCrdtToOther, req_no);
					scrollCreditToOther.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtToOther.getRowCount())));
					tblCrdtToOther.packAll();
					
					//computeAmtToCredit();
				}
				if(crdt_type.equals("B")){//LEDGER TO LEDGER
					//rbtnCrdtFrmLedgerPymnt.setSelected(true);
					
					txtAmntToCredit.setEditable(false);
					//rbtnCrdtPymntToLedger.setSelected(true);
					tabCreditPmtFrom.setSelectedIndex(0);
					tabCreditPmtTo.setSelectedIndex(0);
					tabCreditPmtFrom.setEnabled(false);
					tabCreditPmtTo.setEnabled(false);
					
					_CreditOfPayment.displayLedgerPayment(modelCrdtFromLedger, rowHeaderCrdtFromLedger, entity_id, unit_id, proj_id, seq_no.toString());
					scrollCreditFromLedgerTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromLedger.getRowCount())));
					//scrollCreditFromLedger.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromLedger.getRowCount())));
					tblCrdtFromLedger.packAll();
					computeTotalAmt();
					
					_CreditOfPayment.displaySavedCrdtToLedger(modelCrdtToLedger, rowHeaderCrdtToLedger, req_no); //XXX UNCOMMENT IF WRONG
					//_CreditOfPayment.displayLedgerCrdtTo(modelCrdtToLedger, rowHeaderCrdtToLedger, lookupCrdtToClient.getValue(), txtCreditToUnitID.getText());
					scrollCreditToLedger.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtToLedger.getRowCount())));
					tblCrdtToLedger.packAll();
				}
				if(crdt_type.equals("C")){//OTHER TO LEDGER
					tabCreditPmtFrom.setSelectedIndex(1);
					tabCreditPmtTo.setSelectedIndex(0);
					tabCreditPmtFrom.setEnabled(false);
					tabCreditPmtTo.setEnabled(false);
					
					_CreditOfPayment.displayOtherPayment(modelCrdtFromOther, rowHeaderCrdtFrmOther, lookupCrdtFrmClient.getValue(), txtCreditFrmProjID.getText(), txtCreditFrmUnitID.getText(), txtCreditFrmSeq.getText());
					scrollCreditFromOtherTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromLedger.getRowCount())));
					//scrollCreditFromLedger.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromLedger.getRowCount())));
					tblCrdtFromOther.packAll();
					computeTotalAmt();
					
					_CreditOfPayment.displaySavedCrdtToLedger(modelCrdtToLedger, rowHeaderCrdtToLedger, req_no);//XXX UNCOMMENT ME IF WRONG
					//_CreditOfPayment.displayLedgerCrdtTo(modelCrdtToLedger, rowHeaderCrdtToLedger, lookupCrdtToClient.getValue(), txtCreditToUnitID.getText());
					scrollCreditToLedger.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromOther.getRowCount())));
					tblCrdtToLedger.packAll();
				}
				if(crdt_type.equals("D")){//OTHER TO OTHER
					tabCreditPmtFrom.setSelectedIndex(1);
					tabCreditPmtTo.setSelectedIndex(1);
					tabCreditPmtFrom.setEnabled(false);
					tabCreditPmtTo.setEnabled(false);
					
					_CreditOfPayment.displayOtherPayment(modelCrdtFromOther, rowHeaderCrdtFrmOther, lookupCrdtFrmClient.getValue(), txtCreditFrmProjID.getText(), txtCreditFrmUnitID.getText(), txtCreditFrmSeq.getText());
					scrollCreditFromOtherTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblCrdtFromOther.getRowCount())));
					tblCrdtFromOther.packAll();
					computeTotalAmt();
					
					_CreditOfPayment.displaySavedCrdtToOther(modelCrdtToOther, rowHeaderCrdtToOther, txtReqNo.getText());
					scrollCreditToLedger.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCrdtToOther.getRowCount())));
					tblCrdtToOther.packAll();
					
					tblCrdtToOther.setEditable(false);

				}
				if(req_status.equals("ACTIVE")){//Conditon for the enabling of button only when the request status is active
					//btnState(false, false, true, false, true, false);
					btnState(false, false, true, false, true, true);
				}else{
					btnState(false, false, false, false, false, true);
					//btnState(false, fal, sCancelReq, sSearch, sPost, sClear);
				}
				tblCreditsPaymentsFrm.setEnabled(false);
				tblCreditsPaymentsFrm.setEditable(false);
				tblCreditsPaymentsTo.setEnabled(false);
				tblCreditsPaymentsTo.setEditable(false);
			}catch (NullPointerException e){}
		}
		if(actionCommand.equals("Post")){//POSTING OF CREDIT OF PAYMENT 
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to post request?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				//XXX PUT POSTING OF PAYMENTS HERE

				_CreditOfPayment.postCreditOfPayment(txtReqNo.getText(), getCreditType());

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Payments Succesfully Credited", actionCommand, JOptionPane.INFORMATION_MESSAGE);

				clearCOP();
			}
		}
		if(actionCommand.equals("Clear")){
			clearCOP();

		}
		
		if(actionCommand.equals("Preview")){
			previewCreditOfPayment();
		}
		
	}
}
