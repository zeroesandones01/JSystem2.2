package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import com.vdc.glasspane.JGlassLoading;

import Accounting.Commissions.CreateCommissionSchedule;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Dialogs.dlg_CR_PW_Entry;
import Dialogs.dlg_ClientRequestDP1;
import FormattedTextField.InputEvent;
import FormattedTextField.InputListener;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components.JTBorderFactory;
import components._ButtonGroup;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import net.sf.jasperreports.data.cache.DoubleArrayValues;
import tablemodel.modelOtherRequest_RequestType;

/**
 * @author John Lester Fatallo
 */

public class OtherRequest extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = -7158744628095903452L;
	private static String title = "Other Client Request";
	static Dimension SIZE = new Dimension(1110, 600);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlNorth;

	private JPanel pnlReqPurpose;

	private JPanel pnlOldData;
	private JPanel pnlODWest;
	private JLabel lblODClient;
	private JLabel lblCurrentStat;
	private JLabel lblReserDate;
	private JLabel lblODProject;
	private JLabel lblODUnitPBL;
	private JLabel lblODLotArea;
	private JLabel lblODHouseModel;
	private JLabel lblODSellingAgent;
	private JLabel lblODBuyerType;
	private JLabel lblODPmtScheme;
	private JLabel lblODGSP;
	private JLabel lblODDiscAmt;
	private JLabel lblODVAT;
	private JLabel lblODNSP;
	private JLabel lblODDPAmnt;
	private JLabel lblODDPTerms;
	private JLabel lblODLoanAvailed;
	private JLabel lblODMATerms;

	private JPanel pnlODCenter;
	private JPanel pnlClient;
	private _JLookup lookupODClient;
	private _JXTextField txtODClientName;

	private _JXTextField txtCurrentStatDesc;

	private JPanel pnlReservation;
	private _JDateChooser dteReserDate;
	private JLabel lblRequestDate;
	private _JDateChooser dteRequestDate;

	private _JXTextField txtODProjName;

	private _JXTextField txtODUnitDesc;

	private JPanel pnlODLotArea;
	private JPanel pnlODLotAreaWest;
	private _JXTextField txtODLotArea;
	private JLabel lblODSeqNo;
	private _JXTextField txtODSeqNo;
	private JPanel pnlODLotAreaCenter;
	private JLabel lblODCivilStatus;
	private JComboBox cmbODCivilStatus;

	private _JXTextField txtODHouseModel;

	private _JXTextField txtODAgentName;

	private _JXTextField txtODBuyerType;

	private _JXTextField txtODPmtScheme;

	private _JXFormattedTextField txtODGSP;

	private JPanel pnlODDiscount;
	private _JXFormattedTextField txtODDiscAmt;
	private JLabel lblODDiscRate;
	private _JXFormattedTextField txtODDiscRate;

	private JPanel pnlODVAT;
	private _JXFormattedTextField txtODVat;
	private JLabel lblODVatRate;
	private _JXFormattedTextField txtODVatRate;

	private _JXFormattedTextField txtODNSP;

	private JPanel pnlODDownPayment;
	private _JXFormattedTextField txtODDownpayment;
	private JLabel lblODDPRate;
	private _JXFormattedTextField txtODDPRate;

	private JPanel pnlODDPTerm;
	private _JXTextField txtODDPTerm;
	private _JXTextField txtODMATerm;

	private JPanel pnlODLoanAvailed;
	private _JXFormattedTextField txtODLAAmt;
	private JLabel lblODLARate;
	private _JXFormattedTextField txtODLARate;

	private JSplitPane splitNewData;
	private JButton btnRequest;

	private JPanel pnlNewData;
	private JPanel pnlNDNorth;
	private JScrollPane scrollReqType;
	private _JTableMain tblReqType;
	private JPopupMenu menuReq;
	private JMenuItem menuRemove;

	private JPanel pnlNDCenter;
	private JPanel pnlNDCWest;
	private JPanel pnlNDCEast;

	private JLabel lblNDClient;
	private JLabel lblNDproject;
	private JLabel lblNDUnit;
	private JLabel lblNDLotArea;
	private JLabel lblNDHouseModel;
	private JLabel lblNDSellingAgent;
	private JLabel lblNDBuyerType;
	private JLabel lblNDPmtScheme;
	private JLabel lblNDGSP;
	private JLabel lblNDDiscount;
	private JLabel lblNDVAT;
	private JLabel lblNDNSP;
	private JLabel lblNDDPAmt;
	private JLabel lblNDDPTerm;
	private JLabel lblNDLoanAvailed;
	private JLabel lblNDMATerm;

	private JPanel pnlNDClient;
	private _JLookup lookupNDClient;
	private _JXTextField txtNDFirstName;
	private _JXTextField txtNDMiddleName;
	private _JXTextField txtNDLastName;

	private JPanel pnlNDProject;
	private _JLookup lookupNDProject;
	private _JXTextField txtNDProject;

	private JPanel pnlNDUnit;
	private _JLookup lookupNDUnit;
	private _JXTextField txtNDUnit;

	private JPanel pnlNDLotArea;
	private JPanel pnlNDLotAreaWest;
	private _JXTextField txtNDLotArea;

	private JPanel pnlNDLotAreaCenter;
	private JLabel lblNDCivilStatus;
	private JComboBox cmbNDCivilStatus;

	/*private JLabel lblNDSeqNo;
	private _JXTextField txtNDSeqNo;*/

	private JPanel pnlNDHouseModel;
	private _JLookup lookupNDHouseModel;
	private _JXTextField txtNDHouseModel;

	private JPanel pnlNDAgent;
	private _JLookup lookupNDAgent;
	private _JXTextField txtNDAgent;

	private JPanel pnlNDBuyerType;
	private _JLookup lookupNDBuyerType;
	private _JXTextField txtNDBuyerType;

	private JPanel pnlNDPmtScheme;
	private _JLookup lookupNDPmtScheme;
	private _JXTextField txtNDPmtScheme;

	private _JXFormattedTextField txtNDGSP;

	private JPanel pnlNDDiscount;
	private _JXFormattedTextField txtNDDiscount;
	private JLabel lblNDDiscRate;
	private _JXFormattedTextField txtNDDiscRate;

	private JPanel pnlNDVAT;
	private _JXFormattedTextField txtNDVat;
	private JLabel lblNDVatRate;
	private _JXFormattedTextField txtNDVatRate;

	private _JXFormattedTextField txtNDNSP;

	private JPanel pnlNDDownpayment;
	private JPanel pnlNDDPWest;
	private _JXFormattedTextField txtNDDownpayment;
	private JLabel lblNDDPRate;
	private _JXFormattedTextField txtNDDPRate;
	private JPanel pnlNDDPEast;
	private JLabel lblNDAdditionalDP;
	private _JXFormattedTextField txtNDAdditionalDP;

	private JPanel pnlNDDPTerm;
	private _JXTextField txtNDDPTerms;

	private JPanel pnlNDLoanAvailed;
	private _JXFormattedTextField txtNDLAAmt;
	private JLabel lblNDLARate;
	private _JXFormattedTextField txtNDLARate;

	private JPanel pnlNDMATerm;
	private _JXTextField txtNDMATerms;

	private JLabel lblPagibigContribution;
	private _JXFormattedTextField txtPagibigCotri;

	private JPanel pnlSouth;
	private JPanel pnlSouthUpper;
	private JLabel lblReqNo;
	private JTextField txtReqNo;
	private JLabel lblReqCli;
	private JTextField txtReqCli;
	private JLabel lblReqStatus;
	private JTextField txtReqStatus;
	private JLabel lblRemarks;
	private JTextField txtRemarks;
	private JLabel lblReqPurpose;
	private JComboBox cmbReqPurpose;
	private JLabel lblNDCreditAmt;
	private _JXFormattedTextField txtNDCreditAmt;
	private JPanel pnlSouthLower;
	private JButton btnNew;
	private JButton btnSearch;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnClear;
	private JButton btnCancelReq;
	private JButton btnApprove;
	private JButton btnPreviewReq;
	private _ButtonGroup grpNewEdit = new _ButtonGroup();

	private String old_entity_id;
	private String old_unit_status;
	private String old_proj_id;
	private String old_unit_id;
	private String old_seq_no;
	private String old_model_id;
	private String old_agent_id;
	private String old_buyer_type_id;
	private String old_pmt_scheme_id;
	private BigDecimal old_fire_amt;
	private BigDecimal credit_amt;

	private String FinalEntityID;
	private String FinalCivilStatus;
	private String FinalUnitID;
	private String FinalProjID;
	private String FinalSeqNo;
	private String FinalLotArea;
	private String FinalHouseModel;
	private String FinalAgent;
	private String FinalBuyerType;
	private String FinalPmtScheme;
	private BigDecimal FinalGSP;
	private BigDecimal FinalDiscAmt;
	private BigDecimal FinalDiscRate;
	private BigDecimal FinalVatAmt;
	private BigDecimal FinalVatRate;
	private BigDecimal FinalNSP;
	private BigDecimal FinalDPAmt;
	private BigDecimal FinalDPRate;
	private String FinalDPTerm;
	private Date FinalDPDueDate;
	private BigDecimal FinalLoanAmt;
	private BigDecimal FinalLoanRate;
	private String FinalMATerm;
	private BigDecimal FinalIntRate;
	private BigDecimal FinalFireAmt;
	private String req_no;

	private JGlassLoading glass;

	private modelOtherRequest_RequestType modelReqType;
	private JList rowHeaderReqType;
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");

	public OtherRequest() {
		super(title, true, true, true, true);
		initGUI();
	}

	public OtherRequest(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);

		glass = new JGlassLoading(getJMenuBar(), getContentPane(), this.getWidth(), this.getWidth());
		this.setGlassPane(glass);

		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JPanel(new GridLayout(1, 2, 3, 3));
			pnlMain.add(pnlNorth, BorderLayout.CENTER);
			{
				pnlOldData = new JPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlOldData, BorderLayout.WEST);
				pnlOldData.setBorder(JTBorderFactory.createTitleBorder("Old Data"));
				{
					pnlODWest = new JPanel(new GridLayout(17, 1, 3, 3));
					pnlOldData.add(pnlODWest, BorderLayout.WEST);
					{
						lblODClient = new JLabel("Client");
						pnlODWest.add(lblODClient);
					}
					{
						lblCurrentStat = new JLabel("Current Status");
						pnlODWest.add(lblCurrentStat);
					}
					{
						lblReserDate = new JLabel("Reservation Date");
						pnlODWest.add(lblReserDate);
					}
					{
						lblODProject = new JLabel("Project");
						pnlODWest.add(lblODProject);
					}
					{
						lblODUnitPBL = new JLabel("Unit");
						pnlODWest.add(lblODUnitPBL);
					}
					{
						lblODLotArea = new JLabel("Lot Area");
						pnlODWest.add(lblODLotArea);
					}
					{
						lblODHouseModel = new JLabel("House Model");
						pnlODWest.add(lblODHouseModel);
					}
					{
						lblODSellingAgent = new JLabel("Selling Agent");
						pnlODWest.add(lblODSellingAgent);
					}
					{
						lblODBuyerType = new JLabel("Buyer Type");
						pnlODWest.add(lblODBuyerType);
					}
					{
						lblODPmtScheme = new JLabel("Payment Scheme");
						pnlODWest.add(lblODPmtScheme);
					}
					{
						lblODGSP = new JLabel("Selling Price");
						pnlODWest.add(lblODGSP);
					}
					{
						lblODDiscAmt = new JLabel("Discount");
						pnlODWest.add(lblODDiscAmt);
					}
					{
						lblODVAT = new JLabel("VAT");
						pnlODWest.add(lblODVAT);
					}
					{
						lblODNSP = new JLabel("Net Selling Price");
						pnlODWest.add(lblODNSP);
					}
					{
						lblODDPAmnt = new JLabel("DP / Equity");
						pnlODWest.add(lblODDPAmnt);
					}
					{
						lblODLoanAvailed = new JLabel("Loanable Amt");
						pnlODWest.add(lblODLoanAvailed);
					}
					{
						lblODDPTerms = new JLabel("DP Term");
						pnlODWest.add(lblODDPTerms);
					}
					/*{
						lblODMATerms = new JLabel("MA Terms");
						pnlODWest.add(lblODMATerms);
					}
					{
						lblODStartDate = new JLabel("Start Date");
						pnlODWest.add(lblODStartDate);
					}
					{
						lblODIntRate = new JLabel("Int Rate");
						pnlODWest.add(lblODIntRate);
					}*/
				}
				{
					pnlODCenter = new JPanel(new GridLayout(17, 1, 3, 3));
					pnlOldData.add(pnlODCenter, BorderLayout.CENTER);
					{
						pnlClient = new JPanel(new BorderLayout(3, 3));
						pnlODCenter.add(pnlClient);
						{
							lookupODClient = new _JLookup(null, "Client", 0);
							pnlClient.add(lookupODClient, BorderLayout.WEST);
							lookupODClient.setFilterName(true);
							lookupODClient.setPreferredSize(new Dimension(100, 0));
							lookupODClient.setLookupSQL(_OtherRequest.sqlEntityLookUp());
							lookupODClient.setFilterName(true);
							lookupODClient.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									FncSystem.out("Display sql lookup for CLient old Data",
											lookupODClient.getLookupSQL());

									if (data != null) {

										String entity_id = (String) data[0];
										String entity_name = (String) data[1];
										String pbl_id = (String) data[3];
										Integer seq_no = (Integer) data[4];
										String proj_id = (String) data[5];
										String proj_name = (String) data[6];
										String unit_id = (String) data[7];
										String unit_desc = (String) data[8];
										String byrstatus_id = (String) data[9];
										String byrstatus_desc = (String) data[10];
										//Date reservation_date = _OtherRequest.getReserDate(entity_id, unit_id);
										//displayODDetails(entity_id, unit_id, proj_id, seq_no);

										lookupODClient.setEditable(false);
										txtODClientName.setText(entity_name);
										//txtCurrentStatID.setText(byrstatus_id); //XXX REMOVE
										txtCurrentStatDesc.setText(byrstatus_desc);
										//txtODUnitID.setText(unit_id); //XXX REMOVE
										txtODUnitDesc.setText(unit_desc);
										//txtODProjID.setText(proj_id); //XXX REMOVE
										txtODProjName.setText(proj_name);
										
										displayOldData(entity_id, pbl_id, proj_id, seq_no);
										//btnState(false, false, false, true, true, false, false, false, false, false);
										btnState(false, false, false, true, true, false, false, false);
										btnRequest.setEnabled(true);

									}
								}
							});
						}
						{
							txtODClientName = new _JXTextField("Name");
							pnlClient.add(txtODClientName, BorderLayout.CENTER);
						}
					}
					{
						txtCurrentStatDesc = new _JXTextField("Unit Status");
						pnlODCenter.add(txtCurrentStatDesc);
					}
					/*{
						pnlCurrentStat = new JPanel(new BorderLayout(5, 5));
						pnlODCenter.add(pnlCurrentStat);
						{
							txtCurrentStatID = new _JXTextField("ID");
							pnlCurrentStat.add(txtCurrentStatID, BorderLayout.WEST);
							txtCurrentStatID.setHorizontalAlignment(JXTextField.CENTER);
							txtCurrentStatID.setPreferredSize(new Dimension(50, 0));
						}
						{
							txtCurrentStatDesc = new _JXTextField("Unit Status");
							pnlCurrentStat.add(txtCurrentStatDesc, BorderLayout.CENTER);
						}
					}*/
					{
						pnlReservation = new JPanel(new BorderLayout(5, 5));
						pnlODCenter.add(pnlReservation);
						{
							dteReserDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlReservation.add(dteReserDate, BorderLayout.WEST);
							dteReserDate.getCalendarButton().setVisible(false);
							dteReserDate.setPreferredSize(new Dimension(100, 0));
						}
						{
							lblRequestDate = new JLabel("Request Date", JLabel.TRAILING);
							pnlReservation.add(lblRequestDate);
						}
						{
							dteRequestDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlReservation.add(dteRequestDate, BorderLayout.EAST);
							dteRequestDate.getCalendarButton().setVisible(false);
							dteRequestDate.setPreferredSize(new Dimension(100, 0));
						}
					}
					{
						txtODProjName = new _JXTextField("Project Name");
						pnlODCenter.add(txtODProjName);
					}
					/*{
						pnlODProject = new JPanel(new BorderLayout(5, 5));
						pnlODCenter.add(pnlODProject);
						{
							txtODProjID = new _JXTextField("ID");
							pnlODProject.add(txtODProjID, BorderLayout.WEST);
							txtODProjID.setHorizontalAlignment(JXTextField.CENTER);
							txtODProjID.setPreferredSize(new Dimension(50, 0));
						}
						{
							txtODProjName = new _JXTextField("Project Name");
							pnlODProject.add(txtODProjName, BorderLayout.CENTER);
						}
					}*/
					txtODUnitDesc = new _JXTextField("Unit Desc");
					pnlODCenter.add(txtODUnitDesc);
					/*{
						pnlODUnit = new JPanel(new BorderLayout(5, 5));
						pnlODCenter.add(pnlODUnit);
						{
							txtODUnitID = new _JXTextField("ID");
							pnlODUnit.add(txtODUnitID, BorderLayout.WEST);
							txtODUnitID.setHorizontalAlignment(JXTextField.CENTER);
							txtODUnitID.setPreferredSize(new Dimension(100, 0));
						}
						{
							txtODUnitDesc = new _JXTextField("Unit Desc");
							pnlODUnit.add(txtODUnitDesc, BorderLayout.CENTER);
						}
					}*/
					{
						pnlODLotArea = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlODCenter.add(pnlODLotArea);
						{
							pnlODLotAreaWest = new JPanel(new BorderLayout(3, 3));
							pnlODLotArea.add(pnlODLotAreaWest);
							{
								txtODLotArea = new _JXTextField("Lot Area");
								pnlODLotAreaWest.add(txtODLotArea, BorderLayout.WEST);
								txtODLotArea.setPreferredSize(new Dimension(100, 0));
								txtODLotArea.setHorizontalAlignment(JXTextField.CENTER);
							}
							{
								lblODSeqNo = new JLabel("Seq. No", JLabel.TRAILING);
								pnlODLotAreaWest.add(lblODSeqNo);
							}
							{
								txtODSeqNo = new _JXTextField();
								pnlODLotAreaWest.add(txtODSeqNo, BorderLayout.EAST);
								txtODSeqNo.setPreferredSize(new Dimension(50, 0));
								txtODSeqNo.setHorizontalAlignment(JXTextField.CENTER);
							}
						}
						{
							pnlODLotAreaCenter = new JPanel(new BorderLayout(3, 3));
							pnlODLotArea.add(pnlODLotAreaCenter);
							{
								lblODCivilStatus = new JLabel("Civil Status", JLabel.TRAILING);
								pnlODLotAreaCenter.add(lblODCivilStatus, BorderLayout.WEST);
							}
							{
								cmbODCivilStatus = new JComboBox(_JInternalFrame.CIVIL_STATUS().values().toArray());
								pnlODLotAreaCenter.add(cmbODCivilStatus, BorderLayout.CENTER);
								cmbODCivilStatus.setEnabled(false);
							}
						}
					}
					{
						txtODHouseModel = new _JXTextField("Model Name");
						pnlODCenter.add(txtODHouseModel);
					}
					{
						txtODAgentName = new _JXTextField("Agent Name");
						pnlODCenter.add(txtODAgentName);
					}
					{
						txtODBuyerType = new _JXTextField("Buyer Type");
						pnlODCenter.add(txtODBuyerType);
					}
					{
						txtODPmtScheme = new _JXTextField("Pmt Scheme");
						pnlODCenter.add(txtODPmtScheme);
					}
					{
						txtODGSP = new _JXFormattedTextField(JXTextField.RIGHT);
						pnlODCenter.add(txtODGSP);
						txtODGSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					}
					{
						pnlODDiscount = new JPanel(new BorderLayout(5, 5));
						pnlODCenter.add(pnlODDiscount);
						{
							txtODDiscAmt = new _JXFormattedTextField(JXTextField.RIGHT);
							pnlODDiscount.add(txtODDiscAmt, BorderLayout.WEST);
							txtODDiscAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtODDiscAmt.setPreferredSize(new Dimension(100, 0));
						}
						{
							lblODDiscRate = new JLabel("Disc. Rate", JLabel.TRAILING);
							pnlODDiscount.add(lblODDiscRate);
						}
						{
							txtODDiscRate = new _JXFormattedTextField(JXTextField.RIGHT);
							pnlODDiscount.add(txtODDiscRate, BorderLayout.EAST);
							txtODDiscRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtODDiscRate.setPreferredSize(new Dimension(100, 0));
						}
					}
					{
						pnlODVAT = new JPanel(new BorderLayout(5, 5));
						pnlODCenter.add(pnlODVAT);
						{
							txtODVat = new _JXFormattedTextField(JXTextField.RIGHT);
							pnlODVAT.add(txtODVat, BorderLayout.WEST);
							txtODVat.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtODVat.setPreferredSize(new Dimension(100, 0));
						}
						{
							lblODVatRate = new JLabel("VAT Rate", JLabel.TRAILING);
							pnlODVAT.add(lblODVatRate);
						}
						{
							txtODVatRate = new _JXFormattedTextField(JXTextField.RIGHT);
							pnlODVAT.add(txtODVatRate, BorderLayout.EAST);
							txtODVatRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtODVatRate.setPreferredSize(new Dimension(100, 0));
						}
					}
					{
						txtODNSP = new _JXFormattedTextField(JXTextField.RIGHT);
						pnlODCenter.add(txtODNSP);
						txtODNSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					}
					{
						pnlODDownPayment = new JPanel(new BorderLayout(5, 5));
						pnlODCenter.add(pnlODDownPayment);
						{
							txtODDownpayment = new _JXFormattedTextField(JXTextField.RIGHT);
							pnlODDownPayment.add(txtODDownpayment, BorderLayout.WEST);
							txtODDownpayment.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtODDownpayment.setPreferredSize(new Dimension(100, 0));
						}
						{
							lblODDPRate = new JLabel("DP Rate", JLabel.TRAILING);
							pnlODDownPayment.add(lblODDPRate);
						}
						{
							txtODDPRate = new _JXFormattedTextField(JXTextField.RIGHT);
							pnlODDownPayment.add(txtODDPRate, BorderLayout.EAST);
							txtODDPRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtODDPRate.setPreferredSize(new Dimension(100, 0));
						}
					}

					{
						pnlODLoanAvailed = new JPanel(new BorderLayout(5, 5));
						pnlODCenter.add(pnlODLoanAvailed);
						{
							txtODLAAmt = new _JXFormattedTextField(JXTextField.RIGHT);
							pnlODLoanAvailed.add(txtODLAAmt, BorderLayout.WEST);
							txtODLAAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtODLAAmt.setPreferredSize(new Dimension(100, 0));
						}
						{
							lblODLARate = new JLabel("Loanable Amt Rate", JLabel.TRAILING);
							pnlODLoanAvailed.add(lblODLARate);
						}
						{
							txtODLARate = new _JXFormattedTextField(JXTextField.RIGHT);
							pnlODLoanAvailed.add(txtODLARate, BorderLayout.EAST);
							txtODLARate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtODLARate.setPreferredSize(new Dimension(100, 0));
						}
					}

					{
						pnlODDPTerm = new JPanel(new BorderLayout(5, 5));
						pnlODCenter.add(pnlODDPTerm);
						{
							txtODDPTerm = new _JXTextField();
							pnlODDPTerm.add(txtODDPTerm, BorderLayout.WEST);
							txtODDPTerm.setHorizontalAlignment(JXTextField.CENTER);
							txtODDPTerm.setPreferredSize(new Dimension(100, 0));
						}
						{
							lblODMATerms = new JLabel("MA Term", JLabel.TRAILING);
							pnlODDPTerm.add(lblODMATerms);
						}
						{
							txtODMATerm = new _JXTextField();
							pnlODDPTerm.add(txtODMATerm, BorderLayout.EAST);
							txtODMATerm.setHorizontalAlignment(JXTextField.CENTER);
							txtODMATerm.setPreferredSize(new Dimension(100, 0));
						}
					}
				}
			}
			{
				pnlNewData = new JPanel(new BorderLayout(3, 3));
				pnlNorth.add(pnlNewData);
				pnlNewData.setBorder(JTBorderFactory.createTitleBorder("New Data"));
				{
					splitNewData = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
					pnlNewData.add(splitNewData);
					splitNewData.setOneTouchExpandable(true);
					{
						pnlNDNorth = new JPanel(new BorderLayout(1, 1));
						splitNewData.add(pnlNDNorth, JSplitPane.LEFT);
						pnlNDNorth.setMinimumSize(new Dimension(100, 100));
						{
							btnRequest = new JButton("Select Request Here", getSearchIcon());
							pnlNDNorth.add(btnRequest, BorderLayout.NORTH);
							btnRequest.setEnabled(false);
							btnRequest.setToolTipText("Click to Select Request Here");
							btnRequest.setActionCommand("Select Request");
							btnRequest.addActionListener(this);
						}
						{
							scrollReqType = new JScrollPane();
							pnlNDNorth.add(scrollReqType, BorderLayout.CENTER);
							{
								modelReqType = new modelOtherRequest_RequestType();
								tblReqType = new _JTableMain(modelReqType);
								scrollReqType.setViewportView(tblReqType);
								scrollReqType.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								tblReqType.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								modelReqType.setEditable(true);

								modelReqType.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {

										/*if(e.getType() == TableModelEvent.INSERT && modelReqType.getRowCount() != 0){
											((DefaultListModel)rowHeaderReqType.getModel()).addElement(modelReqType.getRowCount());
											System.out.println("Dumaan dito");
										}*/

										if (modelReqType.getRowCount() == 0) {
											rowHeaderReqType.setModel(new DefaultListModel());
										}
									}
								});
							}
							{ //REMOVE THIS
								menuReq = new JPopupMenu("Popup");
								menuRemove = new JMenuItem("Remove");
								menuReq.add(menuRemove);

								menuRemove.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										if (tblReqType.getSelectedRows().length == 1) {
											int x = tblReqType.getSelectedRow();
											DefaultListModel listRequest = new DefaultListModel();
											rowHeaderReqType.setModel(listRequest);

											if (modelReqType.getRowCount() != 0) {
												String req_id = (String) modelReqType.getValueAt(x, 0);
												setUIOnRemove(req_id);

												tblReqType.setToolTipText(
														"Right click and select remove to remove request from the table");
												if (modelReqType.getRowCount() != 0) {
													listRequest.removeAllElements();
												}

												modelReqType.removeRow(x);
												for (int y = 1; y <= modelReqType.getRowCount(); y++) {
													((DefaultListModel) rowHeaderReqType.getModel()).addElement(y);
												}
											}

											scrollReqType.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables
													.getRowHeader_Footer(Integer.toString(tblReqType.getRowCount())));
											tblReqType.packAll();
										}
									}
								});
							}
							/*{
								tblReqType.addMouseListener(new PopupTriggerListener_panel());
							
								tblReqType.addMouseListener(new MouseListener() {
							
									@Override
									public void mouseReleased(MouseEvent e) {
										checkForTrigger(e);
							
									}
							
									public void checkForTrigger(MouseEvent e){
							
									}
							
									@Override
									public void mousePressed(MouseEvent e) {
										checkForTrigger(e);
							
									}
							
									@Override
									public void mouseExited(MouseEvent e) {
										checkForTrigger(e);
							
									}
							
									@Override
									public void mouseEntered(MouseEvent e) {
										checkForTrigger(e);
							
									}
							
									@Override
									public void mouseClicked(MouseEvent e) {
										checkForTrigger(e);
									}
								});
							}*/
							{
								rowHeaderReqType = tblReqType.getRowHeader();
								rowHeaderReqType.setModel(new DefaultListModel());
								scrollReqType.setRowHeaderView(rowHeaderReqType);
								scrollReqType.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
					}
					{
						pnlNDCenter = new JPanel(new BorderLayout(3, 3));
						splitNewData.add(pnlNDCenter, JSplitPane.RIGHT);
						{
							pnlNDCWest = new JPanel(new GridLayout(16, 1, 3, 3));
							pnlNDCenter.add(pnlNDCWest, BorderLayout.WEST);
							{
								lblNDClient = new JLabel("Client");
								pnlNDCWest.add(lblNDClient);
							}
							{
								lblNDproject = new JLabel("Project");
								pnlNDCWest.add(lblNDproject);
							}
							{
								lblNDUnit = new JLabel("Unit");
								pnlNDCWest.add(lblNDUnit);
							}
							{
								lblNDLotArea = new JLabel("Lot Area");
								pnlNDCWest.add(lblNDLotArea);
							}
							{
								lblNDHouseModel = new JLabel("House Model");
								pnlNDCWest.add(lblNDHouseModel);
							}
							{
								lblNDSellingAgent = new JLabel("Selling Agent");
								pnlNDCWest.add(lblNDSellingAgent);
							}
							{
								lblNDBuyerType = new JLabel("Buyer Type");
								pnlNDCWest.add(lblNDBuyerType);
							}
							{
								lblNDPmtScheme = new JLabel("Payment Scheme");
								pnlNDCWest.add(lblNDPmtScheme);
							}
							{
								lblNDGSP = new JLabel("Selling Price");
								pnlNDCWest.add(lblNDGSP);
							}
							/*{
								lblNDDiscRate = new JLabel("Discount Rate");
								pnlNDCWest.add(lblNDDiscRate);
							}*/
							{
								lblNDDiscount = new JLabel("Discount Amt");
								pnlNDCWest.add(lblNDDiscount);
							}
							{
								lblNDVAT = new JLabel("VAT");
								pnlNDCWest.add(lblNDVAT);
							}
							{
								lblNDNSP = new JLabel("Net Selling Price");
								pnlNDCWest.add(lblNDNSP);
							}
							{
								lblNDDPAmt = new JLabel("DP / Equity");
								pnlNDCWest.add(lblNDDPAmt);
							}
							{
								lblNDLoanAvailed = new JLabel("Loanable Amount");
								pnlNDCWest.add(lblNDLoanAvailed);
							}
							{
								lblNDDPTerm = new JLabel("DP Term");
								pnlNDCWest.add(lblNDDPTerm);
							}
							/*{
								lblNDMATerm = new JLabel("MA Term");
								pnlNDCWest.add(lblNDMATerm);
							}
							{
								lblNDIntRate = new JLabel("Int. Rate");
								pnlNDCWest.add(lblNDIntRate);
							}*/
							{
								lblReqPurpose = new JLabel("Request Purpose");
								pnlNDCWest.add(lblReqPurpose);
							}
						}
						{
							pnlNDCEast = new JPanel(new GridLayout(16, 1, 3, 3));
							pnlNDCenter.add(pnlNDCEast, BorderLayout.CENTER);
							{
								pnlNDClient = new JPanel(new GridLayout(1, 4, 2, 2));
								pnlNDCEast.add(pnlNDClient);
								{
									lookupNDClient = new _JLookup(null, "Select Client", 0);
									pnlNDClient.add(lookupNDClient);
									lookupNDClient.setFilterName(true);
									lookupNDClient.setPrompt("ID");
									lookupNDClient.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												String entity_id = (String) data[0];
												setClientName(entity_id);
												setFinalVariables();
											}
										}
									});
								}
								{
									txtNDLastName = new _JXTextField("Last Name");
									pnlNDClient.add(txtNDLastName);
								}
								{
									txtNDFirstName = new _JXTextField("First Name");
									pnlNDClient.add(txtNDFirstName);
								}
								{
									txtNDMiddleName = new _JXTextField("Middle Name");
									pnlNDClient.add(txtNDMiddleName);
								}
							}
							{
								pnlNDProject = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDProject);
								{
									lookupNDProject = new _JLookup(null, "Select Project", 0);
									pnlNDProject.add(lookupNDProject, BorderLayout.WEST);
									lookupNDProject.setPrompt("ID");
									lookupNDProject.setPreferredSize(new Dimension(50, 0));
									lookupNDProject.setLookupSQL(_OtherRequest.sqlProject());
									lookupNDProject.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											if (data != null) {
												txtNDProject.setText((String) data[1]);
												FncSystem.out("Display sql for project",
														lookupNDProject.getLookupSQL());
											}
										}
									});
								}
								{
									txtNDProject = new _JXTextField("Name");
									pnlNDProject.add(txtNDProject, BorderLayout.CENTER);
								}
							}
							{
								pnlNDUnit = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDUnit);
								{
									lookupNDUnit = new _JLookup(null, "Select Unit", 0);
									lookupNDUnit.setPrompt("ID");
									pnlNDUnit.add(lookupNDUnit, BorderLayout.WEST);
									lookupNDUnit.setPreferredSize(new Dimension(100, 0));
									/*lookupNDUnit.addMouseListener(new MouseAdapter() {
									
										public void mouseClicked(MouseEvent evt){
											setSQLLookUps(_OtherRequest.setFinalVariables(getRequestData()));
										}
									});*/
									lookupNDUnit.addFocusListener(new FocusListener() {

										@Override
										public void focusLost(FocusEvent e) {
											// TODO Auto-generated method stub
										}

										@Override
										public void focusGained(FocusEvent e) {
											setSQLLookUps(_OtherRequest.setFinalVariables(getRequestData()));
										}
									});
									lookupNDUnit.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											FncSystem.out("Display sql for unit", lookupNDUnit.getLookupSQL());

											if (data != null) {

												String unit_id = (String) data[0];
												String unit_name = (String) data[1];
												String model_id = (String) data[4];
												String model_name = (String) data[5];
												String lot_area = (String) data[6];

												txtNDUnit.setText(unit_name);
												lookupNDHouseModel.setValue(model_id);
												txtNDHouseModel.setText(model_name);
												txtNDLotArea.setText(lot_area);

												///SET SEQ NO HERE
												computeGSPOnHouseModel(); //IF PAGIBIG
												setNewVatRate(unit_id, (BigDecimal) txtNDGSP.getValued());
												setDPMATerms(old_pmt_scheme_id);
											}
										}
									});
								}
								{
									txtNDUnit = new _JXTextField("Unit Description");
									pnlNDUnit.add(txtNDUnit, BorderLayout.CENTER);
								}
							}
							{
								pnlNDLotArea = new JPanel(new GridLayout(1, 2, 5, 5));
								pnlNDCEast.add(pnlNDLotArea);
								{
									pnlNDLotAreaWest = new JPanel(new BorderLayout(3, 3));
									pnlNDLotArea.add(pnlNDLotAreaWest);
									{
										txtNDLotArea = new _JXTextField();
										pnlNDLotAreaWest.add(txtNDLotArea, BorderLayout.WEST);
										txtNDLotArea.setPreferredSize(new Dimension(100, 0));
										txtNDLotArea.setHorizontalAlignment(JXTextField.CENTER);
									}
									/*{
										lblNDSeqNo = new JLabel("Seq. No", JLabel.TRAILING);
										pnlNDLotAreaWest.add(lblNDSeqNo);
									}
									{
										txtNDSeqNo = new _JXTextField();
										pnlNDLotAreaWest.add(txtNDSeqNo, BorderLayout.EAST);
										txtNDSeqNo.setPreferredSize(new Dimension(50, 0));
									}*/
								}
								{
									pnlNDLotAreaCenter = new JPanel(new BorderLayout(3, 3));
									pnlNDLotArea.add(pnlNDLotAreaCenter);
									{
										lblNDCivilStatus = new JLabel("Civil Status", JLabel.TRAILING);
										pnlNDLotAreaCenter.add(lblNDCivilStatus, BorderLayout.WEST);
									}
									{
										cmbNDCivilStatus = new JComboBox(
												_JInternalFrame.CIVIL_STATUS().values().toArray());
										pnlNDLotAreaCenter.add(cmbNDCivilStatus, BorderLayout.CENTER);
										cmbNDCivilStatus.setEnabled(false);
										cmbNDCivilStatus.setSelectedItem(null);
										cmbNDCivilStatus.addItemListener(new ItemListener() {

											@Override
											public void itemStateChanged(ItemEvent e) {
												//int selected_index = ((JComboBox) e.getSource()).getSelectedIndex();

												/*if(selected_index == 1){
													if(_OtherRequest.checkIfWithSpouse(entity_id)){
												
													}else{
														cmbNDCivilStatus.setSelectedItem(null);
														JOptionPane.showMessageDialog(OtherRequest.this.getTopLevelAncestor(), "Please input spouse for client", "Status", JOptionPane.WARNING_MESSAGE);
													}
												}*/
											}
										});
									}
								}
							}
							{
								pnlNDHouseModel = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDHouseModel);
								{
									lookupNDHouseModel = new _JLookup(null, "Select house Model", 0);
									pnlNDHouseModel.add(lookupNDHouseModel, BorderLayout.WEST);
									lookupNDHouseModel.setPrompt("ID");
									lookupNDHouseModel.setPreferredSize(new Dimension(100, 0));
									lookupNDHouseModel.addFocusListener(new FocusListener() {

										@Override
										public void focusLost(FocusEvent e) {
											// TODO Auto-generated method stub
										}

										@Override
										public void focusGained(FocusEvent e) {
											// TODO Auto-generated method stub
										}
									});
									lookupNDHouseModel.addMouseListener(new MouseAdapter() {

										public void mouseClicked(MouseEvent evt) {
											setSQLLookUps(_OtherRequest.setFinalVariables(getRequestData()));
										}
									});

									lookupNDHouseModel.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											FncSystem.out("Display sql for the House Model",
													lookupNDHouseModel.getLookupSQL());
											if (data != null) {
												txtNDHouseModel.setText((String) data[1]);

												computeGSPOnHouseModel();
											}
										}
									});
								}
								{
									txtNDHouseModel = new _JXTextField("Model Name");
									pnlNDHouseModel.add(txtNDHouseModel, BorderLayout.CENTER);
								}
							}
							{
								pnlNDAgent = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDAgent);
								{
									lookupNDAgent = new _JLookup(null, "Select Agent", 0);
									pnlNDAgent.add(lookupNDAgent, BorderLayout.WEST);
									lookupNDAgent.setPrompt("ID");
									lookupNDAgent.setPreferredSize(new Dimension(100, 0));
									lookupNDAgent.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											if (data != null) {
												txtNDAgent.setText((String) data[1]);
												FncSystem.out("Display sql for agent", lookupNDAgent.getLookupSQL());
											}
										}
									});
								}
								{
									txtNDAgent = new _JXTextField("Selling Agent");
									pnlNDAgent.add(txtNDAgent, BorderLayout.CENTER);
								}
							}
							{
								pnlNDBuyerType = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDBuyerType);
								{
									lookupNDBuyerType = new _JLookup(null, "Select Client Class", 0);
									pnlNDBuyerType.add(lookupNDBuyerType, BorderLayout.WEST);
									lookupNDBuyerType.setPrompt("ID");
									lookupNDBuyerType.setPreferredSize(new Dimension(100, 0));
									lookupNDBuyerType.addMouseListener(new MouseAdapter() {

										public void mouseClicked(MouseEvent evt) {
											setSQLLookUps(_OtherRequest.setFinalVariables(getRequestData()));
										}
									});

									lookupNDBuyerType.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											FncSystem.out("Display sql for buyer type",
													lookupNDBuyerType.getLookupSQL());

											if (data != null) {
												txtNDBuyerType.setText((String) data[1]);

												setFinalVariables();

												lookupNDPmtScheme.setValue(null);
												txtNDPmtScheme.setText("");
											}
										}
									});
								}
								{
									txtNDBuyerType = new _JXTextField("Buyer Type");
									pnlNDBuyerType.add(txtNDBuyerType, BorderLayout.CENTER);
								}
							}
							{
								pnlNDPmtScheme = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDPmtScheme);
								{
									lookupNDPmtScheme = new _JLookup(null, "Select Payment Scheme", 0);
									pnlNDPmtScheme.add(lookupNDPmtScheme, BorderLayout.WEST);
									lookupNDPmtScheme.setPrompt("ID");
									lookupNDPmtScheme.setPreferredSize(new Dimension(100, 0));
									lookupNDPmtScheme.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent evt) {
											setSQLLookUps(_OtherRequest.setFinalVariables(getRequestData()));
										}
									});

									lookupNDPmtScheme.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											FncSystem.out("Display sql for Payment Scheme",
													lookupNDPmtScheme.getLookupSQL());

											if (data != null) {
												txtNDPmtScheme.setText((String) data[1]);
												setDPMATerms((String) data[0]);
												setDPMAmount();
												//txtNDIntRate.setValue(_OtherRequest.setNewIntRate((String) data[0]));
												computeOnDiscRate();
												setFinalVariables();

												if (_OtherRequest.getGroupID(FinalBuyerType).equals("05")) {
													computeBankFinance(FinalUnitID);
												}

												/*if(_OtherRequest.isLoanableValid(_OtherRequest.setFinalVariables(getRequestData())) == false && _OtherRequest.getGroupID(FinalBuyerType).equals("04")){
													JOptionPane.showMessageDialog(pnlNewData.getTopLevelAncestor(), "Loanable amount is not valid for the affordability ratio of client", "Request", JOptionPane.WARNING_MESSAGE);
												}*/
											}
										}
									});
								}
								{
									txtNDPmtScheme = new _JXTextField("Payment Scheme");
									pnlNDPmtScheme.add(txtNDPmtScheme, BorderLayout.CENTER);
								}
							}
							{
								txtNDGSP = new _JXFormattedTextField(JXTextField.RIGHT);
								pnlNDCEast.add(txtNDGSP);
								txtNDGSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtNDGSP.addKeyListener(new KeyAdapter() {

									public void keyReleased(KeyEvent e) {
										computeAmounts();
									}
								});
							}
							{
								pnlNDDiscount = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDDiscount);
								/*{
									lblNDDiscount = new JLabel("Discount Amt", JLabel.TRAILING);
									pnlNDDiscount.add(lblNDDiscount);
								}*/
								{
									txtNDDiscount = new _JXFormattedTextField(JXTextField.RIGHT);
									pnlNDDiscount.add(txtNDDiscount, BorderLayout.WEST);
									txtNDDiscount.setPreferredSize(new Dimension(100, 0));
									txtNDDiscount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtNDDiscount.addKeyListener(new KeyAdapter() {

										public void keyReleased(KeyEvent e) {
											computeONDiscAmount();
										}
									});
								}
								{
									lblNDDiscRate = new JLabel("Discount Rate", JLabel.TRAILING);
									pnlNDDiscount.add(lblNDDiscRate);
								}
								{
									txtNDDiscRate = new _JXFormattedTextField(JXTextField.RIGHT);
									pnlNDDiscount.add(txtNDDiscRate, BorderLayout.EAST);
									txtNDDiscRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtNDDiscRate.setPreferredSize(new Dimension(100, 0));
									txtNDDiscRate.addKeyListener(new KeyAdapter() {

										public void keyReleased(KeyEvent e) {
											computeOnDiscRate();
										}
									});
								}
							}
							{
								pnlNDVAT = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDVAT);
								{
									txtNDVat = new _JXFormattedTextField(JXTextField.RIGHT);
									pnlNDVAT.add(txtNDVat, BorderLayout.WEST);
									txtNDVat.setPreferredSize(new Dimension(100, 0));
									txtNDVat.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									lblNDVatRate = new JLabel("VAT Rate", JLabel.TRAILING);
									pnlNDVAT.add(lblNDVatRate);
								}
								{
									txtNDVatRate = new _JXFormattedTextField(JXTextField.RIGHT);
									pnlNDVAT.add(txtNDVatRate, BorderLayout.EAST);
									txtNDVatRate.setPreferredSize(new Dimension(100, 0));
									txtNDVatRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
							}
							{
								txtNDNSP = new _JXFormattedTextField(JXTextField.RIGHT);
								pnlNDCEast.add(txtNDNSP);
								txtNDNSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								pnlNDDownpayment = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDDownpayment);
								{
									pnlNDDPWest = new JPanel(new BorderLayout(3, 3));
									pnlNDDownpayment.add(pnlNDDPWest, BorderLayout.WEST);
									{
										txtNDDownpayment = new _JXFormattedTextField(JXTextField.RIGHT);
										pnlNDDPWest.add(txtNDDownpayment, BorderLayout.WEST);
										txtNDDownpayment.setPreferredSize(new Dimension(100, 0));
										txtNDDownpayment.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtNDDownpayment.addKeyListener(new KeyAdapter() {

											public void keyReleased(KeyEvent e) {
												computeAvailedOnDP();
											}
										});
									}
									{
										lblNDDPRate = new JLabel("DP Rate", JLabel.TRAILING);
										pnlNDDPWest.add(lblNDDPRate);
									}
									{
										txtNDDPRate = new _JXFormattedTextField(JXTextField.RIGHT);
										pnlNDDPWest.add(txtNDDPRate, BorderLayout.EAST);
										txtNDDPRate.setPreferredSize(new Dimension(50, 0));
										txtNDDPRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtNDDPRate.addKeyListener(new KeyAdapter() {

											public void keyReleased(KeyEvent e) {
												computeOnDPRate();
											}
										});
									}
								}
								{
									pnlNDDPEast = new JPanel(new BorderLayout(3, 3));
									pnlNDDownpayment.add(pnlNDDPEast, BorderLayout.CENTER);
									{
										lblNDAdditionalDP = new JLabel("Additional DP");
										pnlNDDPEast.add(lblNDAdditionalDP, BorderLayout.WEST);
									}
									{
										txtNDAdditionalDP = new _JXFormattedTextField(JXTextField.RIGHT);
										pnlNDDPEast.add(txtNDAdditionalDP, BorderLayout.CENTER);
										txtNDAdditionalDP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtNDAdditionalDP.setValue(new BigDecimal("0.00"));
										txtNDAdditionalDP.addKeyListener(new KeyAdapter() {

											public void keyPressed(KeyEvent e) {
												if (e.getKeyCode() == KeyEvent.VK_ENTER) {
													computeAvailedOnDPAdditional();
												}
											}
										});
									}
								}
							}
							{
								pnlNDLoanAvailed = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDLoanAvailed);
								{
									txtNDLAAmt = new _JXFormattedTextField(JXTextField.RIGHT);
									pnlNDLoanAvailed.add(txtNDLAAmt, BorderLayout.WEST);
									txtNDLAAmt.setPreferredSize(new Dimension(100, 0));
									txtNDLAAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtNDLAAmt.addKeyListener(new KeyAdapter() {

										public void keyReleased(KeyEvent e) {
											setFinalVariables();

											//if(_OtherRequest.isLoanableValid(FinalEntityID, FinalPmtScheme, FinalLoanAmt)){
											computeOnAvailedAmt();
											/*}else{
												JOptionPane.showMessageDialog(OtherRequest.this.getTopLevelAncestor(), "Loanable Amount not valid", "Prompt", JOptionPane.WARNING_MESSAGE);
											}*/
										}
									});
								}
								{
									lblNDLARate = new JLabel("Loanable Amt Rate", JLabel.TRAILING);
									pnlNDLoanAvailed.add(lblNDLARate);
								}
								{
									txtNDLARate = new _JXFormattedTextField(JXTextField.RIGHT);
									pnlNDLoanAvailed.add(txtNDLARate, BorderLayout.EAST);
									txtNDLARate.setPreferredSize(new Dimension(100, 0));
									txtNDLARate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtNDLARate.addKeyListener(new KeyAdapter() {

										public void keyReleased(KeyEvent e) {
											computeOnAvailedRate();
										}
									});
								}
							}
							{
								pnlNDDPTerm = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDDPTerm);
								{
									txtNDDPTerms = new _JXTextField();
									pnlNDDPTerm.add(txtNDDPTerms, BorderLayout.WEST);
									txtNDDPTerms.setHorizontalAlignment(JXTextField.CENTER);
									txtNDDPTerms.setPreferredSize(new Dimension(100, 0));
								}
								{
									lblNDMATerm = new JLabel("MA Term", JLabel.TRAILING);
									pnlNDDPTerm.add(lblNDMATerm);
								}
								{
									txtNDMATerms = new _JXTextField();
									pnlNDDPTerm.add(txtNDMATerms, BorderLayout.EAST);
									txtNDMATerms.setHorizontalAlignment(JXTextField.CENTER);
									txtNDMATerms.setPreferredSize(new Dimension(100, 0));
									txtNDMATerms.addActionListener(new ActionListener() {

										public void actionPerformed(ActionEvent e) {
											try {
												Integer ma_term = Integer.parseInt(txtNDMATerms.getText());
												if (ma_term % 12 != 0) {
													JOptionPane.showMessageDialog(pnlNorth,
															"Please input a number divisible by twelve");
													txtNDMATerms.setText("");
												}
											} catch (NumberFormatException a) {
											}
										}
									});
								}
							}
							/*{
								pnlNDMATerm = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDMATerm);
								{
									txtNDMATerms = new _JXTextField();
									pnlNDMATerm.add(txtNDMATerms, BorderLayout.WEST);
									txtNDMATerms.setPreferredSize(new Dimension(100, 0));
									txtNDMATerms.setHorizontalAlignment(JXTextField.CENTER);
									txtNDMATerms.addActionListener(new ActionListener() {
							
										public void actionPerformed(ActionEvent e) {
											try{
												Integer ma_term = Integer.parseInt(txtNDMATerms.getText());
												if(ma_term % 12 != 0){
													JOptionPane.showMessageDialog(pnlNorth, "Please input a number divisible by twelve");
													txtNDMATerms.setText("");
												}
											} catch (NumberFormatException a) {}
										}
									});
								}
							}*/
							/*{
								pnlNDIntRate = new JPanel(new BorderLayout(5, 5));
								pnlNDCEast.add(pnlNDIntRate);
								{
									txtNDIntRate = new _JXFormattedTextField(JXTextField.RIGHT);
									pnlNDIntRate.add(txtNDIntRate, BorderLayout.WEST);
									txtNDIntRate.setPreferredSize(new Dimension(100, 0));
									txtNDIntRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								{
									lblNDFireAmt = new JLabel("Fire Amount", JLabel.TRAILING);
									pnlNDIntRate.add(lblNDFireAmt);
								}
								{
									txtNDFireAmt = new _JXFormattedTextField(JXTextField.RIGHT);
									pnlNDIntRate.add(txtNDFireAmt, BorderLayout.EAST);
									txtNDFireAmt.setPreferredSize(new Dimension(100, 0));
									txtNDFireAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
							}*/
							{
								pnlReqPurpose = new JPanel(new BorderLayout(3, 3));
								pnlNDCEast.add(pnlReqPurpose);
								{
									cmbReqPurpose = new JComboBox(
											new String[] { "", "Change to Spouse", "Transfer of Rights" });
									pnlReqPurpose.add(cmbReqPurpose, BorderLayout.WEST);
									cmbReqPurpose.setPreferredSize(new Dimension(150, 0));
									cmbReqPurpose.setEnabled(false);
								}
								{
									lblPagibigContribution = new JLabel("Pag-IBIG Contri:", JLabel.TRAILING);
									pnlReqPurpose.add(lblPagibigContribution);
								}
								{
									txtPagibigCotri = new _JXFormattedTextField(JXTextField.RIGHT);
									pnlReqPurpose.add(txtPagibigCotri, BorderLayout.EAST);
									txtPagibigCotri.setPreferredSize(new Dimension(150, 0));
									txtPagibigCotri.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
								/*{
									lblNDCreditAmt = new JLabel("Credit Amt", JLabel.TRAILING);
									pnlReqPurpose.add(lblNDCreditAmt);
								}
								{
									txtNDCreditAmt = new _JXFormattedTextField(JXTextField.RIGHT);
									pnlReqPurpose.add(txtNDCreditAmt, BorderLayout.EAST);
									txtNDCreditAmt.setPreferredSize(new Dimension(100, 0));
									txtNDCreditAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtNDCreditAmt.addKeyListener(new KeyAdapter() {
										public void keyReleased(KeyEvent e){
											BigDecimal credit_amt = (BigDecimal) ((_JXFormattedTextField) e.getSource()).getValued();
								
											if(credit_amt != new BigDecimal("0.00")){
												BigDecimal loanable_amt = _OtherRequest.computeMaximizedLoanableAmt(_OtherRequest.setFinalVariables(getRequestData()), credit_amt);
								
												txtNDLAAmt.setValue(loanable_amt);
												setFinalVariables();
												computeOnAvailedAmt();
											}
										}
									});
								}*/
							}
						}
					}
				}
			}
		}
		{
			pnlSouth = new JPanel(new GridLayout(2, 1, 3, 3));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setBorder(lineBorder);

			{
				pnlSouthUpper = new JPanel(new SpringLayout());
				pnlSouth.add(pnlSouthUpper);
				{
					lblReqNo = new JLabel("Request No");
					pnlSouthUpper.add(lblReqNo);
				}
				{
					txtReqNo = new JTextField();
					pnlSouthUpper.add(txtReqNo);
				}
				{
					lblReqCli = new JLabel("*Requesting client");
					pnlSouthUpper.add(lblReqCli);
				}
				{
					txtReqCli = new JTextField();
					pnlSouthUpper.add(txtReqCli);
				}
				{
					lblReqStatus = new JLabel("Request Status");
					pnlSouthUpper.add(lblReqStatus);
				}
				{
					txtReqStatus = new JTextField();
					pnlSouthUpper.add(txtReqStatus);
				}
				{
					lblRemarks = new JLabel("Remarks");
					pnlSouthUpper.add(lblRemarks);
				}
				{
					txtRemarks = new JTextField();
					pnlSouthUpper.add(txtRemarks);
				}
				SpringUtilities.makeCompactGrid(pnlSouthUpper, 2, 4, 3, 3, 3, 3, false);
			}
			{
				pnlSouthLower = new JPanel(new GridLayout(2, 5, 5, 5));
				pnlSouth.add(pnlSouthLower);
				pnlSouthLower.setPreferredSize(new Dimension(300, 30));
				{
					btnNew = new JButton("New");
					pnlSouthLower.add(btnNew);
					btnNew.setActionCommand(btnNew.getText());
					btnNew.setMnemonic(KeyEvent.VK_N);
					btnNew.addActionListener(this);
					grpNewEdit.add(btnNew);
				}
				{
					btnSearch = new JButton("Search");
					pnlSouthLower.add(btnSearch);
					btnSearch.setActionCommand(btnSearch.getText());
					btnSearch.setMnemonic(KeyEvent.VK_R);
					btnSearch.addActionListener(this);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouthLower.add(btnEdit);
					btnEdit.setActionCommand(btnEdit.getText());
					btnEdit.setMnemonic(KeyEvent.VK_E);
					btnEdit.addActionListener(this);
					grpNewEdit.add(btnEdit);
				}
				{
					btnSave = new JButton("Save");
					pnlSouthLower.add(btnSave);
					btnSave.setActionCommand(btnSave.getText());
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
				/*{
					btnChangeSchedule = new JButton("Change Schedule");
					pnlSouthLower.add(btnChangeSchedule);
					btnChangeSchedule.setActionCommand(btnChangeSchedule.getText());
					btnChangeSchedule.setMnemonic(KeyEvent.VK_V);
					btnChangeSchedule.addActionListener(this);
				}*/
				/*{
					pnlSouthLower.add(Box.createHorizontalBox());
				}*/
				{
					btnCancelReq = new JButton("Cancel Request");
					pnlSouthLower.add(btnCancelReq);
					btnCancelReq.setActionCommand(btnCancelReq.getText());
					btnCancelReq.setMnemonic(KeyEvent.VK_L);
					btnCancelReq.addActionListener(this);
				}
				{
					btnApprove = new JButton("Approve");
					pnlSouthLower.add(btnApprove);
					btnApprove.setActionCommand(btnApprove.getText());
					btnApprove.setMnemonic(KeyEvent.VK_A);
					btnApprove.addActionListener(this);
				}
				{
					btnPreviewReq = new JButton("Preview Request");
					pnlSouthLower.add(btnPreviewReq);
					btnPreviewReq.setActionCommand(btnPreviewReq.getText());
					btnPreviewReq.setMnemonic(KeyEvent.VK_I);
					btnPreviewReq.addActionListener(this);
				}
				{
					btnClear = new JButton("Clear");
					pnlSouthLower.add(btnClear);
					btnClear.setActionCommand(btnClear.getText());
					btnClear.setMnemonic(KeyEvent.VK_C);
					btnClear.addActionListener(this);
				}
				/*{
					btnCreditPayment = new JButton("Credit Payment");
					pnlSouthLower.add(btnCreditPayment);
					btnCreditPayment.setActionCommand(btnCreditPayment.getText());
					btnCreditPayment.setMnemonic(KeyEvent.VK_R);
					btnCreditPayment.addActionListener(this);
				}*/
			}
		}
		//this.setComponentsEditable(pnlMain, false);
		txtReqCli.setEditable(true);
		txtRemarks.setEditable(true);
		clearOtherRequest();
		/*BigDecimal misc_fees = new BigDecimal("10948.73304000000000000").setScale(2, RoundingMode.UP);
		System.out.printf("Display value of Misc Fees: %s%n", misc_fees);*/
	}//XXX END OF INIT GUI

	public void btnState(Boolean sNew, Boolean sSearch, Boolean sEdit, Boolean sSave, Boolean sClear,
			Boolean sCancelReq, Boolean sApprove, Boolean sPreviewReq) {

		btnNew.setEnabled(sNew);
		btnSearch.setEnabled(sSearch);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnClear.setEnabled(sClear);
		btnCancelReq.setEnabled(sCancelReq);
		btnApprove.setEnabled(sApprove);
		btnPreviewReq.setEnabled(sPreviewReq);
		//btnCreditPayment.setEnabled(sCreditPayment);

	}

	private Icon getSearchIcon() {
		Image img = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/search1.png")).getImage();
		img = img.getScaledInstance(19, 19, Image.SCALE_SMOOTH);

		return new ImageIcon(img);
	}

	/*************** SET VALUE Methods *******************/
	//Displays the Details in the Old Data of the Client
	public void displayOldData(String entity_id, String pbl_id, String proj_id, Integer seq_no) {
		//CHECK IF CORRECT ADD THE OLD CIVIL STATUS OF THE CLIENT
		String sql = "select * from get_client_details('" + entity_id + "', '" + pbl_id + "', '" + proj_id + "', "
				+ seq_no + ")";

		FncSystem.out("Display OLD Data", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if (db.isNotNull()) {

			cmbODCivilStatus.setSelectedItem(db.getResult()[0][2]);
			//txtCurrentStatID.setText((String) db.getResult()[0][3]);
			txtCurrentStatDesc.setText((String) db.getResult()[0][4]);
			dteReserDate.setDate((Date) db.getResult()[0][5]);
			dteRequestDate.setDate((Date) db.getResult()[0][6]);
			//txtODProjID.setText((String) db.getResult()[0][7]);
			txtODProjName.setText((String) db.getResult()[0][8]);
			//txtODUnitID.setText((String) db.getResult()[0][9]);
			txtODUnitDesc.setText((String) db.getResult()[0][10]);
			txtODLotArea.setText(db.getResult()[0][12].toString());
			txtODSeqNo.setText(db.getResult()[0][11].toString());
			//txtODHouseModelID.setText((String) db.getResult()[0][13]);
			txtODHouseModel.setText((String) db.getResult()[0][14]);
			//txtODAgentID.setText((String) db.getResult()[0][15]);
			txtODAgentName.setText((String) db.getResult()[0][16]);
			//txtODBuyertypeID.setText((String) db.getResult()[0][17]);
			txtODBuyerType.setText((String) db.getResult()[0][18]);
			//txtODPmtSchemeID.setText((String) db.getResult()[0][19]);
			txtODPmtScheme.setText((String) db.getResult()[0][20]);
			txtODGSP.setValue(db.getResult()[0][21]);
			txtODDiscAmt.setValue(db.getResult()[0][22]);
			txtODDiscRate.setValue(db.getResult()[0][23]);
			txtODVat.setValue(db.getResult()[0][24]);
			txtODVatRate.setValue(db.getResult()[0][25]);
			txtODNSP.setValue(db.getResult()[0][26]);
			txtODDownpayment.setValue(db.getResult()[0][27]);
			txtODDPRate.setValue(db.getResult()[0][28]);
			txtODDPTerm.setText(db.getResult()[0][29].toString());
			txtODLAAmt.setValue(db.getResult()[0][31]);
			txtODLARate.setValue(db.getResult()[0][32]);
			txtODMATerm.setText(db.getResult()[0][33].toString());

			old_entity_id = (String) db.getResult()[0][0];
			old_unit_status = (String) db.getResult()[0][3];
			old_proj_id = (String) db.getResult()[0][7];
			old_unit_id = (String) db.getResult()[0][9];
			old_seq_no = (String) db.getResult()[0][11].toString();
			old_model_id = (String) db.getResult()[0][13];
			old_agent_id = (String) db.getResult()[0][15];
			old_buyer_type_id = (String) db.getResult()[0][17];
			old_pmt_scheme_id = (String) db.getResult()[0][19];
			old_fire_amt = (BigDecimal) db.getResult()[0][38];

		}
	}

	//DISPLAYS THE NEW DATA BASED ON REQUEST NO
	private void displayNewData(String req_no) {
		String sql = "select\n" + "a.request_by, \n" + //0
				"a.request_remarks, \n" + //1 
				"a.new_dp_duedate, \n" + //2
				"a.new_ma_duedate, \n" + //3
				"a.new_entity_id, \n" + //4
				"get_client_name(a.new_entity_id), \n" + //5 
				"a.new_last_name, \n" + //6
				"a.new_first_name, \n" + //7
				"a.new_mi_name, \n" + //8
				"a.new_entity_class, \n" + //9 
				"b.type_desc, \n" + //10
				"a.new_agent, \n" + //11
				"get_client_name(a.new_agent), \n" + //12
				"a.new_proj_id, \n" + //13
				"get_project_name(a.new_proj_id),\n" + //14
				"a.new_unit_id, \n" + //15
				"get_unit_description(a.new_unit_id),\n" + //16
				"a.new_sprice, \n" + //17
				"a.new_pmt_scheme_id, \n" + //18
				"c.pmt_scheme_desc,\n" + //19
				"a.new_model, \n" + //20
				"d.model_desc, \n" + //21
				"a.new_disc, \n" + //22
				"a.new_ma_term, \n" + //23
				"a.new_dp_term, \n" + //24
				"a.new_dp_amount, \n" + //25
				"a.new_availed, \n" + //26
				"a.new_dp_rate, \n" + //27
				"a.new_availed_rate, \n" + // 28
				"a.new_disc_rate, \n" + //29
				"a.new_lotarea::char(20), \n" + //30 
				"a.new_fire, \n" + //31
				"a.old_entity_class, \n" + //32 
				"e.civil_status_desc , a.req_purpose,  \n" + //33 //34
				"(CASE WHEN get_group_id(a.new_entity_class) IN ('04', '03') THEN (a.new_dp_amount + a.new_availed) ELSE a.new_sprice end) as new_nsp, \n"
				+ "a.additional, \n" + //36
				"a.new_seq_no, a.pagibig_cont \n" + "from req_rt_request_header a\n" + //35
				"left join mf_buyer_type b on b.type_id = a.new_entity_class \n"
				+ "left join mf_payment_scheme c on c.pmt_scheme_id = a.new_pmt_scheme_id \n"
				+ "left join mf_product_model d on d.model_id = a.new_model \n"
				+ "left join mf_civil_status e on e.civil_status_code = a.new_civil_status \n" + "where request_no = '"
				+ req_no + "'";

		FncSystem.out("Display New Data", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if (db.isNotNull()) {//REMOVE ALL CONDITIONS HERE IN THE DISPLAY

			if (_OtherRequest.isInTable("02", modelReqType)) {//OK NA DITO
				lookupNDBuyerType.setValue((String) db.getResult()[0][9]);
				txtNDBuyerType.setText((String) db.getResult()[0][10]);
				//txtNDFireAmt.setValue(db.getResult()[0][31]);
			}

			if (_OtherRequest.isInTable("03", modelReqType)) {//OK NA DITO
				txtNDFirstName.setText((String) db.getResult()[0][7]);
				txtNDMiddleName.setText((String) db.getResult()[0][8]);
				txtNDLastName.setText((String) db.getResult()[0][6]);
			}

			if (_OtherRequest.isInTable("05", modelReqType)) {//OK NA DITO
				lookupNDClient.setValue((String) db.getResult()[0][4]);
				setClientName((String) db.getResult()[0][4]);
				cmbReqPurpose.setSelectedItem(db.getResult()[0][34]);

				//System.out.println("Dumaan dito sa Change Principal");
			}
			if (_OtherRequest.isInTable("06", modelReqType)) {//OK NA DITO
				lookupNDBuyerType.setValue((String) db.getResult()[0][9]);
				//txtNDBuyerType.setText((String) db.getResult()[0][10]);
				//lookupNDPmtScheme.setValue((String) db.getResult()[0][18]);
				//txtNDPmtScheme.setText((String) db.getResult()[0][19]);
				txtNDGSP.setValue(db.getResult()[0][17]);
				//txtNDDiscount.setValue(db.getResult()[0][22]);
				//txtNDDiscRate.setValue(db.getResult()[0][29]);
				setNewVatRate((String) db.getResult()[0][15], (BigDecimal) db.getResult()[0][17]);
				txtNDNSP.setValue(db.getResult()[0][35]);
				//txtNDIntRate.setValue(_OtherRequest.setNewIntRate((String) db.getResult()[0][18]));
				lookupNDUnit.setValue((String) db.getResult()[0][15]);
				txtNDUnit.setText((String) db.getResult()[0][16]);
				txtNDLotArea.setText((String) db.getResult()[0][30]);
				lookupNDHouseModel.setValue((String) db.getResult()[0][20]);
				txtNDHouseModel.setText((String) db.getResult()[0][21]);
				txtNDLAAmt.setValue(db.getResult()[0][26]);
				txtNDLARate.setValue(db.getResult()[0][28]);
				txtNDDownpayment.setValue(db.getResult()[0][25]);
				txtNDDPRate.setValue(db.getResult()[0][27]);
				txtNDDPTerms.setText(db.getResult()[0][24].toString());
				txtNDMATerms.setText(db.getResult()[0][23].toString());
				//txtNDFireAmt.setValue(db.getResult()[0][31]);

				//computeOnAvailedRate();

			}
			if (_OtherRequest.isInTable("07", modelReqType)) {//OK NA DITO

				lookupNDAgent.setValue((String) db.getResult()[0][11]);
				txtNDAgent.setText((String) db.getResult()[0][12]);
				txtNDGSP.setValue(db.getResult()[0][17]);
				txtNDNSP.setValue(db.getResult()[0][35]);
				txtNDLAAmt.setValue(db.getResult()[0][26]);
				txtNDLARate.setValue(db.getResult()[0][28]);
				txtNDDownpayment.setValue(db.getResult()[0][25]);
				txtNDDPRate.setValue(db.getResult()[0][27]);

			}
			if (_OtherRequest.isInTable("08", modelReqType)) {//OK NA DITO
				txtNDDiscount.setValue(db.getResult()[0][22]);
				txtNDDiscRate.setValue(db.getResult()[0][29]);
				//XXX ADD THE VALUE OF THE VAT HERE WHEN COMPUTING FOR THE NET SELLING PRICE

				txtNDNSP.setValue(db.getResult()[0][35]);
				txtNDDownpayment.setValue(db.getResult()[0][25]);
				txtNDDPRate.setValue(db.getResult()[0][27]);
				txtNDLAAmt.setValue(db.getResult()[0][26]);
				txtNDLARate.setValue(db.getResult()[0][28]);
				//computeOnAvailedRate();
			}
			if (_OtherRequest.isInTable("12", modelReqType)) {//OK NA DITO
				lookupNDAgent.setValue((String) db.getResult()[0][11]);
				txtNDAgent.setText((String) db.getResult()[0][12]);
				lookupNDProject.setValue((String) db.getResult()[0][13]);
				txtNDProject.setText((String) db.getResult()[0][14]);

				/*lookupNDBuyerType.setValue((String) db.getResult()[0][9]);
				txtNDBuyerType.setText((String) db.getResult()[0][10]);
				lookupNDPmtScheme.setValue((String) db.getResult()[0][18]);
				txtNDPmtScheme.setText((String) db.getResult()[0][19]);*/
				txtNDGSP.setValue(db.getResult()[0][17]);
				//txtNDDiscount.setValue(db.getResult()[0][22]);
				//txtNDDiscRate.setValue(db.getResult()[0][29]);
				setNewVatRate((String) db.getResult()[0][15], (BigDecimal) db.getResult()[0][17]);
				txtNDNSP.setValue(db.getResult()[0][35]);
				//txtNDIntRate.setValue(_OtherRequest.setNewIntRate((String) db.getResult()[0][18]));
				lookupNDUnit.setValue((String) db.getResult()[0][15]);
				txtNDUnit.setText((String) db.getResult()[0][16]);
				txtNDLotArea.setText((String) db.getResult()[0][30]);
				lookupNDHouseModel.setValue((String) db.getResult()[0][20]);
				txtNDHouseModel.setText((String) db.getResult()[0][21]);
				txtNDLAAmt.setValue(db.getResult()[0][26]);
				txtNDLARate.setValue(db.getResult()[0][28]);
				txtNDDownpayment.setValue(db.getResult()[0][25]);
				txtNDDPRate.setValue(db.getResult()[0][27]);
				txtNDDPTerms.setText(db.getResult()[0][24].toString());
				txtNDMATerms.setText(db.getResult()[0][23].toString());
				txtNDAdditionalDP.setValue(db.getResult()[0][36]);
				//txtNDFireAmt.setValue(db.getResult()[0][31]);

				//computeOnAvailedRate();
			}
			if (_OtherRequest.isInTable("13", modelReqType)) {//OK NA DITO
				lookupNDAgent.setValue((String) db.getResult()[0][11]);
				txtNDAgent.setText((String) db.getResult()[0][12]);
			}
			if (_OtherRequest.isInTable("15", modelReqType)) {//OK NA DITO
				lookupNDPmtScheme.setValue((String) db.getResult()[0][18]);
				txtNDPmtScheme.setText((String) db.getResult()[0][19]);
				txtNDGSP.setValue(db.getResult()[0][17]);
				txtNDDiscount.setValue(db.getResult()[0][22]);
				txtNDDiscRate.setValue(db.getResult()[0][29]);
				txtNDNSP.setValue(db.getResult()[0][35]);
				txtNDDownpayment.setValue(db.getResult()[0][25]);
				txtNDDPRate.setValue(db.getResult()[0][27]);
				txtNDDPTerms.setText(db.getResult()[0][24].toString());
				txtNDLAAmt.setValue(db.getResult()[0][26]);
				txtNDLARate.setValue(db.getResult()[0][28]);
				txtNDMATerms.setText(db.getResult()[0][23].toString());
				//txtNDIntRate.setValue(_OtherRequest.setNewIntRate((String) db.getResult()[0][18]));
				//computeOnAvailedRate();
				//computeAmounts();
				//System.out.println("Dumaan dito sa Change Payment Scheme");
			}

			if (_OtherRequest.isInTable("16", modelReqType)) {//OK NA DITO
				lookupNDHouseModel.setValue((String) db.getResult()[0][20]);
				txtNDHouseModel.setText((String) db.getResult()[0][21]);
				txtNDGSP.setValue(db.getResult()[0][17]);
				txtNDDiscount.setValue(db.getResult()[0][22]);
				txtNDDiscRate.setValue(db.getResult()[0][29]);
				txtNDNSP.setValue(db.getResult()[0][35]);
				txtNDDownpayment.setValue(db.getResult()[0][25]);
				txtNDDPRate.setValue(db.getResult()[0][27]);
				txtNDLAAmt.setValue(db.getResult()[0][26]);
				txtNDLARate.setValue(db.getResult()[0][28]);
				//computeOnAvailedRate();

				//txtNDFireAmt.setValue(db.getResult()[0][31]);
			}

			if (_OtherRequest.isInTable("25", modelReqType)) {//OK NA DITO
				cmbNDCivilStatus.setSelectedItem(db.getResult()[0][33]);
				txtNDLastName.setText((String) db.getResult()[0][6]);
				txtNDFirstName.setText((String) db.getResult()[0][7]);
				txtNDMiddleName.setText((String) db.getResult()[0][8]);
				//setClientName((String) db.getResult()[0][4]);
				System.out.printf("Display Last Name: %s%n", db.getResult()[0][6]);
				System.out.printf("Display First Name: %s%n", db.getResult()[0][7]);
				System.out.printf("Display Middle Name: %s%n", db.getResult()[0][8]);
			}

			if (_OtherRequest.isInTable("26", modelReqType)) {
				txtNDLAAmt.setValue(db.getResult()[0][26]);
				txtNDDPTerms.setText(db.getResult()[0][24].toString());
				txtNDLARate.setValue(db.getResult()[0][28]);
				txtNDDownpayment.setValue(db.getResult()[0][25]);
				txtNDDPRate.setValue(db.getResult()[0][27]);
				txtNDMATerms.setText(db.getResult()[0][23].toString());
				txtNDAdditionalDP.setValue(db.getResult()[0][36]);
			}

			if (_OtherRequest.isInTable("27", modelReqType)) {
				txtNDDPTerms.setText(db.getResult()[0][24].toString());
			}

			if (_OtherRequest.isInTable("28", modelReqType)) {
				txtNDMATerms.setText(db.getResult()[0][23].toString());
			}

			if (_OtherRequest.isInTable("18", modelReqType)) {
				txtPagibigCotri.setValue(db.getResult()[0][38]);
			}

			if (_OtherRequest.isInTable("04", modelReqType)) {
				txtNDGSP.setValue(db.getResult()[0][17]);
				txtNDDiscount.setValue(db.getResult()[0][22]);
				txtNDDiscRate.setValue(db.getResult()[0][29]);
				txtNDDownpayment.setValue(db.getResult()[0][25]);
				txtNDDPRate.setValue(db.getResult()[0][27]);
				txtNDLAAmt.setValue(db.getResult()[0][26]);
				txtNDLARate.setValue(db.getResult()[0][28]);
				txtNDNSP.setValue(db.getResult()[0][35]);
			}

			if (_OtherRequest.getGroupID(((String) db.getResult()[0][9])).equals("03")) {
				txtNDDiscount.setValue(db.getResult()[0][22]);
				txtNDDiscRate.setValue(db.getResult()[0][29]);
			}

			txtReqCli.setText((String) db.getResult()[0][0]);
			txtRemarks.setText((String) db.getResult()[0][1]);
			txtReqCli.setEditable(false);
			txtRemarks.setEditable(false);
		}
	}

	private Object[] displayNewData2(String request_no) {
		pgSelect db = new pgSelect();

		String SQL = "SELECT * FROM view_request_new_data('" + request_no + "')";
		db.select(SQL);

		FncSystem.out("Display Request New Data", SQL);

		String request_by = (String) db.getResult()[0][0];
		String request_remarks = (String) db.getResult()[0][1];
		Date new_dp_due_date = (Date) db.getResult()[0][2];
		Date new_ma_due_date = (Date) db.getResult()[0][3];
		String new_entity_id = (String) db.getResult()[0][4];
		String new_entity_name = (String) db.getResult()[0][5];
		String new_lname = (String) db.getResult()[0][6];
		String new_fname = (String) db.getResult()[0][7];
		String new_mname = (String) db.getResult()[0][8];
		String new_buyer_type = (String) db.getResult()[0][9];
		String new_buyer_type_desc = (String) db.getResult()[0][10];
		String new_agent_id = (String) db.getResult()[0][11];
		String new_agent_name = (String) db.getResult()[0][12];
		String new_proj_id = (String) db.getResult()[0][13];
		String new_proj_name = (String) db.getResult()[0][14];
		String new_unit_id = (String) db.getResult()[0][15];
		String new_unit_desc = (String) db.getResult()[0][16];
		BigDecimal new_gsp = (BigDecimal) db.getResult()[0][17];
		String new_pmt_scheme_id = (String) db.getResult()[0][18];
		String new_pmt_scheme_desc = (String) db.getResult()[0][19];
		String new_model_id = (String) db.getResult()[0][20];
		String new_model_desc = (String) db.getResult()[0][21];
		BigDecimal new_disc_amt = (BigDecimal) db.getResult()[0][22];
		String new_ma_term = db.getResult()[0][23].toString();
		String new_dp_term = db.getResult()[0][24].toString();
		BigDecimal new_dp_amt = (BigDecimal) db.getResult()[0][25];
		BigDecimal new_loan_amt = (BigDecimal) db.getResult()[0][26];
		BigDecimal new_dp_rate = (BigDecimal) db.getResult()[0][27];
		BigDecimal new_loan_rate = (BigDecimal) db.getResult()[0][28];
		String new_lot_area = (String) db.getResult()[0][29];
		BigDecimal new_fire_amt = (BigDecimal) db.getResult()[0][30];
		String old_buyer_type = (String) db.getResult()[0][31];
		String new_civil_status = (String) db.getResult()[0][32];
		String req_purpose = (String) db.getResult()[0][33];
		BigDecimal new_nsp = (BigDecimal) db.getResult()[0][34];
		BigDecimal additional = (BigDecimal) db.getResult()[0][35];
		String new_seq_no = db.getResult()[0][36].toString();

		return new Object[] { request_by, request_remarks, new_dp_due_date, new_ma_due_date, new_entity_id,
				new_entity_name, new_lname, new_fname, new_mname, new_buyer_type, new_buyer_type_desc, new_agent_id,
				new_agent_name, new_proj_id, new_proj_name, new_unit_id, new_unit_desc, new_gsp, new_pmt_scheme_id,
				new_pmt_scheme_desc, new_model_id, new_model_desc, new_disc_amt, new_ma_term, new_dp_term, new_dp_amt,
				new_loan_amt, new_dp_rate, new_loan_rate, new_lot_area, new_fire_amt, old_buyer_type, new_civil_status,
				req_purpose, new_nsp, additional, new_seq_no };
	}

	//RETURN THE STRING VALUE OF THE SELECTED REQUESTS IN THE TABLE TO BE USED IN INSERT
	private String getSelectedRequest() {

		String req_id = "";
		for (int x = 0; x < modelReqType.getRowCount(); x++) {
			req_id = req_id.concat(modelReqType.getValueAt(x, 0).toString()).concat(",");
		}

		return req_id;
	}

	//DISPLAYS THE REQUESTS TO BE CHOSEN AND ADDED TO THE TABLE
	public void addRequestToTable() {
		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Request", _OtherRequest.sqlRequest(), true);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		FncSystem.out("Display sql for selection of Request", _OtherRequest.sqlRequest());

		Object[][] data = dlg.getResult();

		if (data != null) {
			for (int x = 0; x < data.length; x++) {
				String req_id = (String) data[x][0];
				String req_desc = (String) data[x][1];
				//String byrstatus_id = (String) data[x][2];

				if (isRequestValid(req_id, req_desc)) {
					if (_OtherRequest.isInTable(req_id, modelReqType) == false) {
						modelReqType.addRow(new Object[] { data[x][0], data[x][1], data[x][2] });

						rowHeaderReqType.setModel(new DefaultListModel());

						for (int y = 1; y <= modelReqType.getRowCount(); y++) {
							((DefaultListModel) rowHeaderReqType.getModel()).addElement(y);
						}
						setUiOnRequest(req_id);
					}
				}
			}

			/*if(_OtherRequest.forCredit(modelReqType) && _OtherRequest.isTotalPaymentsZero(old_entity_id, old_proj_id, old_unit_id, old_seq_no) == false){
				lblNDCreditAmt.setEnabled(true);
				txtNDCreditAmt.setEnabled(true);
				txtNDCreditAmt.setEditable(true);
			}*/

			scrollReqType.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
					FncTables.getRowHeader_Footer(Integer.toString(tblReqType.getRowCount())));
			tblReqType.packAll();
		}
	}

	public Boolean isRequestValid(String req_id, String req_desc) {//CHECKS IF THE REQUEST SELECTED IS VALID
		setFinalVariables();

		Boolean isValid = true;
		System.out.printf("Display old unit status: %s%n", old_unit_status);

		if (old_unit_status.equals("02")) {
			if (req_id.equals("07")) {
				if (_OtherRequest.isInTable("12", modelReqType)) {
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							String.format("Cannot select both Transfer-Reapply and %s", req_desc), "Request",
							JOptionPane.WARNING_MESSAGE);
				} else {
					if (_OtherRequest.getUnitStatus(old_unit_id).equals("R")) {
						isValid = false;
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
								"Cannot select request....Unit is already taken by another client", "Request",
								JOptionPane.WARNING_MESSAGE);
					}
					if (_OtherRequest.getUnitStatus(old_unit_id).equals("Q")) {
						isValid = false;
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
								"Cannot select request....Unit has pending client request", "Request",
								JOptionPane.WARNING_MESSAGE);
					}
					//XXX COMMENTED BY LESTER 2021-11-11 TO BE DISCUSSED WITH MKTG FOR OR ACCOUNT BEFORE CANCELLED
					/*if(_OtherRequest.isCommissionReleased(old_agent_id, old_unit_id, old_proj_id, old_seq_no)){
						isValid = false;
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cannot select request...\nPlease coordinate with FAD for reversal", "Request", JOptionPane.WARNING_MESSAGE);
					}*/
				}
			} else if (req_id.equals("12")) {
				if (_OtherRequest.isInTable("07", modelReqType)) {
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							String.format("Cannot select both Reapplication and %s", req_desc), "Request",
							JOptionPane.WARNING_MESSAGE);
				}
			} else {
				if (_OtherRequest.isInTable("07", modelReqType) || _OtherRequest.isInTable("12", modelReqType)) {
					isValid = true;
				} else {
					isValid = false;
					JOptionPane.showMessageDialog(pnlNewData, "Account is cancelled cannot select request", "Request",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		} else {

			if (req_id.equals("07") || req_id.equals("12")) {//FOR REAPPLICATION AND TRANSFER REAPPLY
				isValid = false;
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
						"Cannot select " + req_desc + " valid only on cancelled accounts", "Request",
						JOptionPane.WARNING_MESSAGE);
			}

			/*if(req_id.equals("02")){
				if(_OtherRequest.isInTable("06", modelReqType) == false && _OtherRequest.getGroupID(FinalBuyerType).equals("04")){
					isValid = true;
				}else{
					isValid = false;
					JOptionPane.showMessageDialog(pnlNewData, "Cannot select Change Client Class Individually", "Request", JOptionPane.WARNING_MESSAGE);
				}
			}*/

			if (req_id.equals("03")) {//FOR CORRECTION OF NAME
				if (_OtherRequest.isInTable("05", modelReqType) == false) {
					isValid = true;
				} else {
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							"Cannot select " + req_desc + " when Change of Principal Applicant is Selected", "Request",
							JOptionPane.WARNING_MESSAGE);
				}
			}

			if (req_id.equals("05")) {//FOR THE CHANGE OF PRINCIPAL APPLICANT
				if (_OtherRequest.isInTable("03", modelReqType) == false
						|| _OtherRequest.isInTable("25", modelReqType) == false) {
					isValid = true;
				} else if (_OtherRequest.isInTable("03", modelReqType)) {
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							"Cannot select " + req_desc + " when Correction of Name is selected", "Request",
							JOptionPane.WARNING_MESSAGE);
				} else if (_OtherRequest.isInTable("25", modelReqType)) {
					isValid = true;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							"Cannot select " + req_desc + " when Change of Civil Status is selected", "Select Request",
							JOptionPane.WARNING_MESSAGE);
				}
			}

			/*if(req_id.equals("06")){//FOR TRANSFER OF UNIT
				if(_OtherRequest.isInTable("02", modelReqType) == false && _OtherRequest.isInTable("15", modelReqType) == false){
					isValid = true;
					System.out.println("Dumaan dito sa validation ng transfer of unit");
				}else if (_OtherRequest.isInTable("15", modelReqType)){
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cannot select "+req_desc+" when Change of Payment Scheme is selected", "Request", JOptionPane.WARNING_MESSAGE);
				}else if(_OtherRequest.isInTable("02", modelReqType)){
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cannot select "+req_desc+" when Change of Client Class is selected", "Request", JOptionPane.WARNING_MESSAGE);
				}
			}*/

			/*if(req_id.equals("02")){//FOR CHANGE OF CLIENT CLASS
				if(_OtherRequest.isInTable("06", modelReqType) == false){
					isValid = true;
				}else{
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cannot select "+req_desc+" when Transfer of unit is selected", "Request", JOptionPane.WARNING_MESSAGE);
				}
			}*/

			if (req_id.equals("03")) {//FOR CORRECTION OF NAME
				if (_OtherRequest.isInTable("25", modelReqType) == false
						&& _OtherRequest.isInTable("05", modelReqType) == false) {
					isValid = true;
				} else {
					isValid = false;
					if (_OtherRequest.isInTable("05", modelReqType)) {
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
								"Cannot select " + req_desc + " when Change of Principal Applicant is Selected",
								"Request", JOptionPane.WARNING_MESSAGE);
					} else if (_OtherRequest.isInTable("25", modelReqType)) {
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
								"Cannot select " + req_desc + " when Change of Civil Status is Selected", "Request",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}

			if (req_id.equals("15")) {
				if (_OtherRequest.isInTable("08", modelReqType)) {
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							"Cannot select " + req_desc + " when Reconsideration of Discount is selected", "Request",
							JOptionPane.WARNING_MESSAGE);
				}
			}

			if (req_id.equals("16")) {//CHANGE HOUSE MODEL
				if (_OtherRequest.getGroupID(FinalBuyerType).equals("04")) {
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							"Cannot select " + req_desc + " when Account Type is Pagibig", "Request",
							JOptionPane.WARNING_MESSAGE);
				}
			}

			if (req_id.equals("25")) {//FOR CHANGE OF CIVIL STATUS
				if (_OtherRequest.isInTable("03", modelReqType)) {
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							"Cannot select " + req_desc + " when Correction of Name is Selected", "Request",
							JOptionPane.WARNING_MESSAGE);
				} else if (_OtherRequest.isInTable("05", modelReqType)) {
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							"Cannot select " + req_desc + " when Change of Principal is Selected", "Request",
							JOptionPane.WARNING_MESSAGE);
				} else {
					isValid = true;
				}
			}

			if (req_id.equals("26")) {
				if (_OtherRequest.getGroupID(FinalBuyerType).equals("04")
						|| _OtherRequest.getGroupID(FinalBuyerType).equals("05")) {
					/*if(_OtherRequest.isLoanFiled(old_entity_id, old_proj_id, old_unit_id, old_seq_no)){
						isValid = false;
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cannot select "+req_desc+" when Loan Filed", "Request", JOptionPane.WARNING_MESSAGE);
					}*/
				} else {
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
							"Cannot select " + req_desc + " valid only for Pagibig", "Request",
							JOptionPane.WARNING_MESSAGE);
				}
			}

			/*if(req_id.equals("28") && _OtherRequest.getGroupID(FinalBuyerType).equals("04")){
				isValid = false;
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cannot select "+req_desc+" on Pagibig Accounts", "Request", JOptionPane.WARNING_MESSAGE);
			}*/

			/*if(req_id.equals("01")){
				if(_OtherRequest.isValidChangeSched(old_entity_id, old_proj_id, old_unit_id, old_seq_no)){
			
				}else{
					isValid = false;
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Cannot select "+req_desc+" valid only on TR Stage", "Request", JOptionPane.WARNING_MESSAGE);
				}
			}*/
		}
		return isValid;
	}

	private void setUiOnRequest(String req_id) {//Enables the components for each of the requests added to the table

		if (req_id.equals("02")) { //CHANGE OF CLIENT CLASS
			lblNDBuyerType.setText("*Buyer Type");
			lblNDBuyerType.setEnabled(true);

			//lblNDPmtScheme.setText("*Payment Scheme");
			/*lblNDDPAmt.setText("*DP / Equity");
			lblNDLoanAvailed.setText("*Loan Availed");
			lblNDDPRate.setText("*DP Rate");
			lblNDLARate.setText("*Loan Availed Rate");*/

			this.setComponentsEnabled(pnlNDBuyerType, true);
			//this.setComponentsEnabled(pnlNDDownpayment, true);
		}

		if (req_id.equals("03")) {//CORRECTION OF NAME
			lblNDClient.setText("*Client");
			lblNDClient.setEnabled(true);

			if (txtReqStatus.getText().isEmpty()) {
				setClientName(lookupODClient.getValue());
			}

			txtNDFirstName.setEnabled(true);
			txtNDMiddleName.setEnabled(true);
			txtNDLastName.setEnabled(true);
			txtNDFirstName.setEditable(true);
			txtNDMiddleName.setEditable(true);
			txtNDLastName.setEditable(true);
		}

		if (req_id.equals("05")) {//Change of Principal Applicant
			lblNDClient.setText("*Client");
			lblReqPurpose.setText("*Request Purpose");
			lblNDClient.setEnabled(true);
			lblReqPurpose.setEnabled(true);

			lookupNDClient.setLookupSQL(_OtherRequest.sqlChangePrincipalApp(lookupODClient.getValue()));
			cmbReqPurpose.setEnabled(true);
			lookupNDClient.setEnabled(true);
		}

		if (req_id.equals("06")) {//TRANSFER OF UNIT

			lblNDUnit.setText("*Unit");
			lblNDUnit.setEnabled(true);

			this.setComponentsEnabled(pnlNDUnit, true);
		}

		if (req_id.equals("04")) {
			//this.setComponentsEnabled(pnlS, enable);
		}

		if (req_id.equals("07")) {//OK NA DITO REAPPLICATION

			if (_OtherRequest.hasNewPriceList(old_unit_id)) {

				pgSelect db = new pgSelect();
				String SQL = "select tcp_discounted_roundoff, equity, loanable_amt_final, ROUND(((loanable_amt_final/tcp_discounted_roundoff) * 100), 2)\n"
						+ "FROM rf_marketing_scheme_pricelist where phase = '1' and block = '13' and lot = '9' AND status_id = 'A' ORDER BY version_no desc limit 1;";
				db.select(SQL);

				if (db.isNotNull()) {
					for (int x = 0; x < db.getRowCount(); x++) {
						BigDecimal nsp = (BigDecimal) db.getResult()[x][0];
						BigDecimal equity = (BigDecimal) db.getResult()[x][1];
						BigDecimal loanable_amt = (BigDecimal) db.getResult()[x][2];
						BigDecimal loanable_rate = (BigDecimal) db.getResult()[x][3];
						BigDecimal dp_rate = new BigDecimal("100").subtract(loanable_rate);
						txtNDGSP.setValue(nsp);
						txtNDDownpayment.setValue(equity);
						txtNDLAAmt.setValue(loanable_amt);
						txtNDDPRate.setValue(dp_rate);
						txtNDLARate.setValue(loanable_rate);
					}
				}
				/*if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Use old selling price?", "Reapplication", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					System.out.println("Use Old Selling Price");
				
					txtNDGSP.setValue(txtODGSP.getValued());
				
					lookupNDAgent.setLookupSQL(_OtherRequest.sqlSellingAgent(old_agent_id, getSelectedRequest()));
					//lookupNDAgent.setEditable(true);
					this.setComponentsEnabled(pnlNDAgent, true);
				
					lblNDAdditionalDP.setEnabled(true);
					txtNDAdditionalDP.setEnabled(true);
				
					if(_OtherRequest.isFullComm(old_unit_id)){
						lookupNDAgent.setValue(old_agent_id);
						txtNDAgent.setText(txtODAgentName.getText());
						lookupNDAgent.setEditable(false);
						txtNDAgent.setEditable(false);
						lblNDSellingAgent.setText("Selling Agent");
				
					}else{
						lblNDSellingAgent.setText("*Selling Agent");
					}
				}else{
				
					System.out.println("Use Latest Selling Price");
				
					lookupNDAgent.setLookupSQL(_OtherRequest.sqlSellingAgent(old_agent_id, getSelectedRequest()));
					//lookupNDAgent.setEditable(true);
					this.setComponentsEnabled(pnlNDAgent, true);
					lblNDAdditionalDP.setEnabled(true);
					txtNDAdditionalDP.setEnabled(true);
				
					if(_OtherRequest.isFullComm(old_unit_id)){
						lookupNDAgent.setValue(old_agent_id);
						txtNDAgent.setText(txtODAgentName.getText());
						lookupNDAgent.setEditable(false);
						txtNDAgent.setEditable(false);
						lblNDSellingAgent.setText("Selling Agent");
				
					}else{
						lblNDSellingAgent.setText("*Selling Agent");
					}
				}*/
			} else {
				/*lookupNDAgent.setLookupSQL(_OtherRequest.sqlSellingAgent(old_agent_id, getSelectedRequest()));
				this.setComponentsEnabled(pnlNDAgent, true);*/

				/*lblNDAdditionalDP.setEnabled(true);
				txtNDAdditionalDP.setEnabled(true);*/

				/*if(_OtherRequest.isFullComm(old_unit_id)){
					lookupNDAgent.setValue(old_agent_id);
					txtNDAgent.setText(txtODAgentName.getText());
					lookupNDAgent.setEditable(false);
					txtNDAgent.setEditable(false);
					lblNDSellingAgent.setText("Selling Agent");
				
				}else{
					lblNDSellingAgent.setText("*Selling Agent");
				}*/
			}
		}

		if (req_id.equals("08")) {//OK NA DITO RECONSIDERATION OF DISCOUNT OR PROMO
			lblNDDiscRate.setText("*Discount Rate");
			lblNDDiscRate.setEnabled(true);

			txtNDDiscRate.setEnabled(true);
		}

		if (req_id.equals("12")) {//TRANSFER REAPPLY OK NA DITO LAST UPDATE

			lblNDproject.setText("*Project");
			lblNDUnit.setText("*Unit");

			lblNDproject.setEnabled(true);
			lblNDUnit.setEnabled(true);

			lookupNDProject.setLookupSQL(_OtherRequest.sqlProject());
			lookupNDProject.setEditable(true);
			lookupNDUnit.setEditable(true);
			lblNDAdditionalDP.setEnabled(true);
			txtNDAdditionalDP.setEnabled(true);

			this.setComponentsEnabled(pnlNDUnit, true);
			this.setComponentsEnabled(pnlNDProject, true);

			/*if(_OtherRequest.isFullComm(old_unit_id)){
				lookupNDAgent.setValue(old_agent_id);
				txtNDAgent.setText(txtODAgentName.getText());
				lookupNDAgent.setEditable(false);
				txtNDAgent.setEditable(false);
				lblNDSellingAgent.setText("Selling Agent");
			}else{
				lblNDSellingAgent.setText("*Selling Agent");
			}*/
		}

		if (req_id.equals("13")) {// OK NA DITO
			lblNDSellingAgent.setText("*Selling Agent");
			lblNDSellingAgent.setEnabled(true);

			lookupNDAgent.setLookupSQL(_OtherRequest.sqlSellingAgent(old_agent_id, getSelectedRequest()));

			this.setComponentsEnabled(pnlNDAgent, true);
			lookupNDAgent.setEditable(true);
		}

		if (req_id.equals("15")) {//OK NA DITO
			lblNDPmtScheme.setText("*Payment Scheme");
			lblNDPmtScheme.setEnabled(true);

			this.setComponentsEnabled(pnlNDPmtScheme, true);

			lblNDAdditionalDP.setEnabled(false);
			txtNDAdditionalDP.setEnabled(false);
		}

		if (req_id.equals("16")) {//OK NA DITO 
			lblNDHouseModel.setText("*House Model");
			lblNDHouseModel.setEnabled(true);

			lblNDAdditionalDP.setEnabled(true);
			txtNDAdditionalDP.setEnabled(true);

			this.setComponentsEnabled(pnlNDHouseModel, true);
		}

		if (req_id.equals("25")) {// CHANGE OF CIVIL; STATUS 
			lblNDCivilStatus.setText("*Civil Status");
			lblNDCivilStatus.setEnabled(true);

			lblNDClient.setEnabled(true);
			lblNDClient.setText("*Client");

			cmbNDCivilStatus.setEnabled(true);

			if (txtReqStatus.getText().isEmpty()) {
				//if(setClientName(lookupODClient.getValue())/*.equals("F")*/){
				setClientName(lookupODClient.getValue());
				txtNDFirstName.setEditable(true);
				txtNDMiddleName.setEditable(true);
				txtNDLastName.setEditable(true);
				//}
			}

			txtNDFirstName.setEnabled(true);
			txtNDMiddleName.setEnabled(true);
			txtNDLastName.setEnabled(true);
			txtNDFirstName.setEditable(true);
			txtNDMiddleName.setEditable(true);
			txtNDLastName.setEditable(true);

		}

		if (req_id.equals("26")) {//CHANGE OF LOANABLE AMOUNT
			lblNDLoanAvailed.setText("*Loan Availed");
			lblNDLoanAvailed.setEnabled(true);
			lblNDAdditionalDP.setEnabled(true);
			txtNDAdditionalDP.setEnabled(true);
			this.setComponentsEnabled(pnlNDLoanAvailed, true);
		}

		if (req_id.equals("27")) {//CHANGE OF DP TERM
			lblNDDPTerm.setText("*DP Term");

			lblNDDPTerm.setEnabled(true);

			txtNDDPTerms.setEnabled(true);
			txtNDDPTerms.setEditable(true);
		}

		if (req_id.equals("28")) {//CHANGE OF MA TERM

			lblNDMATerm.setEnabled(true);
			lblNDMATerm.setText("*MA Term");

			txtNDMATerms.setEnabled(true);
			txtNDMATerms.setEditable(true);
		}

		if (req_id.equals("18")) {

			lblPagibigContribution.setEnabled(true);
			lblPagibigContribution.setText("Pag-IBIG Contri:");

			txtPagibigCotri.setEnabled(true);
			txtPagibigCotri.setEditable(true);
		}

		if (req_id.equals("04")) {
			lblNDGSP.setEnabled(true);
			txtNDGSP.setEnabled(true);
		}
	}

	//Check if 
	private void setUIOnRemove(String req_id) {//DISABLES THE COMPONENTS BASED ON THE REMOVED REQUEST

		if (req_id.equals("02")) {
			this.setComponentsEnabled(pnlNDBuyerType, false);
			this.setComponentsEnabled(pnlNDPmtScheme, false);
			this.setComponentsEnabled(pnlNDDownpayment, false);
			this.setComponentsEnabled(pnlNDLoanAvailed, false);
			this.setComponentsEnabled(pnlNDDPTerm, false);

			txtNDDPTerms.setEditable(false);
			txtNDMATerms.setEditable(false);

			lblNDBuyerType.setText("Buyer Type");
			lblNDPmtScheme.setText("Payment Scheme");
			lblNDDPAmt.setText("DP / Equity");
			lblNDLoanAvailed.setText("Loan Availed");
			lblNDDPRate.setText("DP Rate");
			lblNDLARate.setText("Loan Availed Rate");

			lookupNDBuyerType.setValue(null);
			txtNDBuyerType.setText("");
			lookupNDPmtScheme.setValue(null);
			txtNDPmtScheme.setText("");
			txtNDDiscount.setValue(new BigDecimal("0"));
			txtNDDiscRate.setValue(new BigDecimal("0"));
			txtNDDownpayment.setValue(new BigDecimal("0.00"));
			txtNDDPRate.setValue(new BigDecimal("0.00"));
			txtNDDPTerms.setText("");
			txtNDLAAmt.setValue(new BigDecimal("0.00"));
			txtNDLARate.setValue(new BigDecimal("0.00"));
			txtNDMATerms.setText("");
		}

		if (req_id.equals("03")) {//CORRECTION OF NAME
			//this.setComponentsEditable(pnlNDClient, false);

			txtNDFirstName.setEnabled(false);
			txtNDMiddleName.setEnabled(false);
			txtNDLastName.setEnabled(false);

			txtNDFirstName.setText("");
			txtNDMiddleName.setText("");
			txtNDLastName.setText("");

		}

		if (req_id.equals("05")) {
			lookupNDClient.setEnabled(false);

			lookupNDClient.setValue(null);
			txtNDFirstName.setText("");
			txtNDMiddleName.setText("");
			txtNDLastName.setText("");
			cmbReqPurpose.setEnabled(false);
		}

		if (req_id.equals("06")) {

			this.setComponentsEditable(pnlNDUnit, false);
			this.setComponentsEditable(pnlNDLotArea, false);
			this.setComponentsEditable(pnlNDBuyerType, false);
			this.setComponentsEditable(pnlNDPmtScheme, false);
			this.setComponentsEditable(pnlNDDPTerm, false);
			this.setComponentsEditable(pnlNDMATerm, false);
			this.setComponentsEditable(pnlNDLoanAvailed, false);
			this.setComponentsEditable(pnlNDDownpayment, false);

			lookupNDUnit.setValue(null);
			txtNDUnit.setText("");
			txtNDLotArea.setText("");
			lookupNDHouseModel.setValue(null);
			txtNDHouseModel.setText("");
			lookupNDBuyerType.setValue(null);
			txtNDBuyerType.setText("");
			lookupNDPmtScheme.setValue(null);
			txtNDPmtScheme.setText("");
			txtNDDPTerms.setText("");
			txtNDDownpayment.setValue(new BigDecimal("0.00"));
			txtNDMATerms.setText("");
			txtNDLAAmt.setValue(new BigDecimal("0.00"));
		}

		if (req_id.equals("07")) {

			this.setComponentsEditable(pnlNDAgent, false);
			this.setComponentsEditable(pnlNDBuyerType, false);
			this.setComponentsEditable(pnlNDPmtScheme, false);
			this.setComponentsEditable(pnlNDDownpayment, false);
			this.setComponentsEditable(pnlNDDPTerm, false);
			this.setComponentsEditable(pnlNDLoanAvailed, false);
			this.setComponentsEditable(pnlNDMATerm, false);

			lookupNDAgent.setValue(null);
			txtNDAgent.setText("");
			lookupNDBuyerType.setValue(null);
			txtNDAgent.setText("");
			lookupNDPmtScheme.setValue(null);
			txtNDPmtScheme.setText("");
			txtNDDownpayment.setValue(new BigDecimal("0.00"));
			txtNDDPRate.setValue(new BigDecimal("0.00"));
			txtNDDPTerms.setText("");
			txtNDLAAmt.setValue(new BigDecimal("0.00"));
			txtNDLARate.setValue(new BigDecimal("0.00"));
			txtNDMATerms.setText("");

		}

		if (req_id.equals("08")) {
			//this.setComponentsEditable(pnlNDDiscount, false);
			this.setComponentsEnabled(pnlNDDiscount, false);

			txtNDDiscount.setValue(new BigDecimal("0.00"));
			txtNDDiscRate.setValue(new BigDecimal("0.00"));

		}

		if (req_id.equals("12")) {
			this.setComponentsEditable(pnlNDProject, false);
			this.setComponentsEditable(pnlNDUnit, false);
			this.setComponentsEditable(pnlNDBuyerType, false);
			this.setComponentsEditable(pnlNDPmtScheme, false);

			lookupNDProject.setValue(null);
			txtNDProject.setText("");
			lookupNDUnit.setValue(null);
			txtNDUnit.setText("");
			lookupNDBuyerType.setValue(null);
			txtNDBuyerType.setText("");
			lookupNDPmtScheme.setValue(null);
			txtNDPmtScheme.setText("");
			txtNDGSP.setValue(new BigDecimal("0.00"));
			txtNDDiscount.setValue(new BigDecimal("0.00"));
			txtNDDiscRate.setValue(new BigDecimal("0.00"));
			txtNDVat.setValue(new BigDecimal("0.00"));
			txtNDVatRate.setValue(new BigDecimal("0.00"));
			txtNDNSP.setValue(new BigDecimal("0.00"));
			txtNDDownpayment.setValue(new BigDecimal("0.00"));
			txtNDDPRate.setValue(new BigDecimal("0.00"));
			txtNDDPTerms.setText("");
			txtNDLAAmt.setValue(new BigDecimal("0.00"));
			txtNDLARate.setValue(new BigDecimal("0.00"));
			txtNDMATerms.setText("");

		}

		if (req_id.equals("13")) {
			//this.setComponentsEditable(pnlNDAgent, false);
			this.setComponentsEnabled(pnlNDAgent, false);

			lookupNDAgent.setValue(null);
			txtNDAgent.setText("");
		}

		if (req_id.equals("15")) {

			this.setComponentsEnabled(pnlNDPmtScheme, false);
			this.setComponentsEnabled(pnlNDDownpayment, false);
			this.setComponentsEnabled(pnlNDDPTerm, false);

			lookupNDPmtScheme.setValue(null);
			txtNDPmtScheme.setText("");
			txtNDDownpayment.setValue(new BigDecimal("0.00"));
			txtNDDPRate.setValue(new BigDecimal("0.00"));
			txtNDDPTerms.setText("");
			txtNDLAAmt.setValue(new BigDecimal("0.00"));
			txtNDLARate.setValue(new BigDecimal("0.00"));
			txtNDMATerms.setText("");
			//txtNDIntRate.setValue(new BigDecimal("0.00"));
			txtNDNSP.setValue(new BigDecimal("0.00"));

		}

		if (req_id.equals("16")) {
			//this.setComponentsEditable(pnlNDHouseModel, false);

			this.setComponentsEnabled(pnlNDHouseModel, false);
			lookupNDHouseModel.setValue(null);
			txtNDHouseModel.setText("");
		}

		if (req_id.equals("25")) {

			cmbNDCivilStatus.setEnabled(false);
			cmbNDCivilStatus.setSelectedItem(null);
			txtNDFirstName.setText("");
			txtNDMiddleName.setText("");
			txtNDLastName.setText("");

		}
	}

	public boolean toSave() {//CHECKS THE REQUIRED FIELDS BASED ON THE SELECTED REQUEST
		String required_fields = "";
		Boolean toSave = true;

		if (tblReqType.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please Select Request", "Save",
					JOptionPane.WARNING_MESSAGE);
			toSave = false;
		} else {

			if (txtReqCli.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Requesting Client", "Save",
						JOptionPane.WARNING_MESSAGE);
				toSave = false;
			}

			/*if(_OtherRequest.isLoanableValid(_OtherRequest.setFinalVariables(getRequestData())) == false){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Loanable Amount is not valid for affordability ratio of client", "Save", JOptionPane.WARNING_MESSAGE);
				toSave = false;
			}*/

			if (_OtherRequest.isInTable("02", modelReqType)) {//CHANGE OF CLIENT CLASS
				if (lookupNDBuyerType.getValue() == null) {
					required_fields = required_fields + "Buyer Type \n";
					toSave = false;
				}
				if (lookupNDPmtScheme.getValue() == null) {
					required_fields = required_fields + "Payment Scheme \n";
					toSave = false;
				}
				/*if(txtNDDPTerms.getText().isEmpty()){
					required_fields = required_fields + "DP Terms \n";
					toSave = false;
				}
				if(txtNDMATerms.getText().isEmpty()){
					required_fields = required_fields + "MA Terms \n";
					toSave = false;
				}
				
				if(((BigDecimal)txtNDDownpayment.getValued()).doubleValue() == 0 || txtNDDownpayment.getValued() == null){
					required_fields = required_fields + "DP / Equity \n";
					toSave = false;
				}
				if(((BigDecimal) txtNDDPRate.getValued()).doubleValue() ==0 || txtNDDPRate.getValued() == null){
					required_fields = required_fields + "DP Rate \n";
					toSave = false;
				}*/
				/*if(((BigDecimal) txtNDLAAmt.getValued()).doubleValue() == 0 || txtNDLAAmt.getValued() == null){
				required_fields = required_fields + "Loan Availed \n";
				toSave = false;
				}
				if(((BigDecimal) txtNDLARate.getValue()).doubleValue() == 0 || txtNDLARate.getValued() == null){
				required_fields = required_fields + "Loan Availed Rate \n";
				toSave = false;
				}*/
			}

			if (_OtherRequest.isInTable("03", modelReqType)) {//CORRECTION NAME
				if (txtNDFirstName.getText().isEmpty()) {
					required_fields = required_fields + "First Name \n";
					toSave = false;
				}
				if (txtNDMiddleName.getText().isEmpty()) {
					required_fields = required_fields + "Middle Name \n";
					toSave = false;
				}
				if (txtNDLastName.getText().isEmpty()) {
					required_fields = required_fields + "Last Name \n";
					toSave = false;
				}
			}

			if (_OtherRequest.isInTable("04", modelReqType)) {//CHANGE OF SELLING PRICE
				if (txtNDGSP.getValued() == null || txtNDGSP.getValued().equals(new BigDecimal("0.00"))) {
					required_fields = required_fields + "Selling Price \n";
					toSave = false;
				}
			}

			if (_OtherRequest.isInTable("05", modelReqType)) {//CHANGE OF PRINCIPAL APPLICANT
				if (lookupNDClient.getValue() == null || lookupNDClient.getValue().isEmpty()) {
					required_fields = required_fields + "Client \n";
					toSave = false;
				}
				if (cmbReqPurpose.getSelectedIndex() == 0) {
					required_fields = required_fields + "Request purpose \n";
					toSave = false;
				}
			}

			if (_OtherRequest.isInTable("06", modelReqType)) {//TRANSFER OF UNIT
				if (lookupNDUnit.getValue() == null || lookupNDUnit.getValue().isEmpty()) {
					required_fields = required_fields + "Unit \n";
					toSave = false;
				}
			}

			/*if(_OtherRequest.isInTable("07", modelReqType)){//REAPPLICATION
				if(lookupNDAgent.getValue() == null){
					required_fields = required_fields + "Selling Agent \n";
					toSave = false;
				}
			}*/

			/*if(_OtherRequest.isInTable("08", modelReqType)){//RECONSIDERATION OF DISCOUNT
				if(txtNDDiscount.getValued() == null || txtNDDiscount.getValued().equals(new BigDecimal("0.00"))){
					required_fields = required_fields + "Discount Amount \n";
					toSave = false;
				}
				if(txtNDDiscRate.getValued() == null || txtNDDiscRate.getValued().equals(new BigDecimal("0.00"))){
					required_fields = required_fields + "Discount Rate \n";
					toSave = false;
				}
			}*/

			if (_OtherRequest.isInTable("12", modelReqType)) {//TRANSFER REAPPLY
				if (lookupNDUnit.getValue() == null || lookupNDUnit.getValue().isEmpty()) {
					required_fields = required_fields + "Unit \n";
					toSave = false;
				}
				/*if(txtNDDPTerms.getText().isEmpty() || txtNDDPTerms.getText().equals("")){
					required_fields = required_fields + "DP Term \n";
					toSave = false;
				}
				if(txtNDMATerms.getText().isEmpty() || txtNDMATerms.getText().equals("")){
					required_fields = required_fields + "MA Terms \n";
					toSave = false;
				}*/
			}

			if (_OtherRequest.isInTable("13", modelReqType)) {//CHANGE OF SELLING AGENT
				if (lookupNDAgent.getValue() == null || lookupNDAgent.getValue().isEmpty()) {
					required_fields = required_fields + "Agent \n";
					toSave = false;
				}
			}

			if (_OtherRequest.isInTable("15", modelReqType)) {//CHANGE PAYMENT SCHEME
				if (lookupNDPmtScheme.getValue() == null || lookupNDPmtScheme.getValue().isEmpty()) {
					required_fields = required_fields + "Payment Scheme \n";
					toSave = false;
				}
				if (txtNDDPTerms.getText().isEmpty() || txtNDDPTerms.getText().equals("")) {
					required_fields = required_fields + "DP Terms \n";
					toSave = false;
				}
				if (txtNDMATerms.getText().isEmpty() || txtNDMATerms.getText().equals("")) {
					required_fields = required_fields + "MA Terms \n";
					toSave = false;
				}
			}

			if (_OtherRequest.isInTable("27", modelReqType)) {
				if (txtNDDPTerms.getText().trim().isEmpty()) {
					required_fields = required_fields + "DP Terms \n";
					toSave = false;
				}
			}

			if (_OtherRequest.isInTable("16", modelReqType)) {//CHANGE HOUSE MODEL
				if (lookupNDHouseModel.getValue() == null || lookupNDHouseModel.getValue().isEmpty()) {
					required_fields = required_fields + "House Model \n";
					toSave = false;
				}
			}

			if (toSave == false && required_fields != "") {
				JOptionPane.showMessageDialog(null, "Please fill up all required fields:\n" + required_fields, "Save",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}

			/*if(_OtherRequest.forCredit(modelReqType) && _OtherRequest.isTotalPaymentsZero(lookupODClient.getValue(), txtODProjID.getText(), txtODUnitID.getText(), txtODSeqNo.getText()) == false){
				if(_OtherRequest.isCredited(txtReqNo.getText()) == false){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please credit payments before saving", "Save", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}*/
		}
		return toSave;
	}

	public String setClientName(String entity_id) {//OK NA DITO
		pgSelect db = new pgSelect();
		String sql = "select trim(first_name), trim(middle_name), trim(last_name), gender from rf_entity where entity_id = '"
				+ entity_id + "'";
		db.select(sql);

		//FncSystem.out("Display sql for set name", sql);

		String first_name = (String) db.getResult()[0][0];
		String middle_name = (String) db.getResult()[0][1];
		String last_name = (String) db.getResult()[0][2];
		String gender = (String) db.getResult()[0][3];

		txtNDFirstName.setText(first_name);
		txtNDMiddleName.setText(middle_name);
		txtNDLastName.setText(last_name);
		//System.out.printf("Display Gender: %s%n", gender);

		return gender;
	}

	public void setDPMATerms(String pmt_scheme_id) {//OK NA DITO
		pgSelect db = new pgSelect();
		setFinalVariables();

		System.out.printf("Display value of old buyer type_id: %s%n", _OtherRequest.getGroupID(old_buyer_type_id));
		System.out.printf("Display value of new buyer type id: %s%n", (_OtherRequest.getGroupID(FinalBuyerType)));
		System.out.printf("Display value of condition: %s%n", (_OtherRequest.isInTable("15", modelReqType)
				&& !_OtherRequest.getGroupID(old_buyer_type_id).equals(_OtherRequest.getGroupID(FinalBuyerType))));

		if (_OtherRequest.isInTable("15", modelReqType) /*&& modelReqType.getRowCount() > 1*/) {
			//&& !_OtherRequest.getGroupID(old_buyer_type_id).equals(_OtherRequest.getGroupID(FinalBuyerType)) || (_OtherRequest.isInTable("15", modelReqType) /*&& !_OtherRequest.getGroupID(old_buyer_type_id).equals(_OtherRequest.getGroupID(FinalBuyerType))*/ ) ){ 
			String sqlDPTerm = "select term from mf_pay_scheme_detail where pmt_scheme_id = '" + pmt_scheme_id
					+ "' and part_id = '013'";
			db.select(sqlDPTerm);
			//FncSystem.out("Display sql for dp term", sqlDPTerm);

			if (db.isNotNull()) {
				txtNDDPTerms.setText(db.getResult()[0][0].toString());
			} else {
				txtNDDPTerms.setText("0");
			}

			String sqlMATerm = "SELECT sp_compute_payment_term('" + FinalEntityID + "', '" + FinalBuyerType
					+ "')::INT * 12 FROM mf_pay_scheme_detail WHERE pmt_scheme_id = '" + pmt_scheme_id
					+ "' AND part_id = '014'";
			db.select(sqlMATerm);
			//FncSystem.out("Display sql for ma term", sqlMATerm);

			if (db.isNotNull()) {
				txtNDMATerms.setText(db.getResult()[0][0].toString());
			} else {
				txtNDMATerms.setText("0");
			}

			if (_OtherRequest.getGroupID(FinalBuyerType).equals(_OtherRequest.getGroupID(old_buyer_type_id))) {
				if (_OtherRequest.isInTable("06", modelReqType) == false
						&& _OtherRequest.isInTable("12", modelReqType) == false
						&& _OtherRequest.isInTable("07", modelReqType) == false) {
					//txtNDDPTerms.setText(txtODDPTerm.getText());
					//txtNDMATerms.setText(txtODMATerm.getText());
				}
				if (_OtherRequest.isInTable("28", modelReqType) == false) {
					txtNDMATerms.setText(txtODMATerm.getText());
				}
			} else {

			}

		} else {
			System.out.println("Parehas yung dp and ma term dun sa luma");
			txtNDDPTerms.setText(txtODDPTerm.getText());
			txtNDMATerms.setText(txtODMATerm.getText());
		}

		String sqlDiscRate = "select (case when pmt_scheme_desc ~*'SPOT DP' THEN 8 when pmt_scheme_desc ~*'SPOT CASH' then 12 when pmt_scheme_desc ~*'DEFERRED CASH' then 5 ELSE 0 END)::NUMERIC \n"
				+ "from mf_payment_scheme where pmt_scheme_id = '" + pmt_scheme_id + "'\n";
		db.select(sqlDiscRate);

		BigDecimal disc_rate = (BigDecimal) db.getResult()[0][0];

		txtNDDiscRate.setValue(disc_rate);

		if (disc_rate.doubleValue() != 0) {
			txtNDDiscRate.setEnabled(true);
		}
	}

	private void setDPMAmount() {
		setFinalVariables();
		pgSelect db = new pgSelect();
		BigDecimal dp_rate = new BigDecimal("0.00");

		String sql = "select COALESCE(round(rate, 2), 0.00) from mf_pay_scheme_detail where pmt_scheme_id = '"
				+ FinalPmtScheme + "' and part_id = '013'";
		db.select(sql);
		//FncSystem.out("Display sql for dp rate", sql);

		if (db.isNotNull()) {
			dp_rate = (BigDecimal) db.getResult()[0][0];
		}

		//System.out.printf("Display dp_rate: %s%n", dp_rate);

		if (dp_rate.doubleValue() > 0) {
			txtNDDPRate.setValue(dp_rate);
			computeOnDPRate();
		} else {
			if (_OtherRequest.isInTable("06", modelReqType) || _OtherRequest.isInTable("12", modelReqType)
					|| _OtherRequest.isInTable("07", modelReqType)) {
				txtNDLAAmt.setValue(_OtherRequest.getPagibigLoanableAmt(FinalUnitID, FinalHouseModel, FinalNSP));
			} else {
				txtNDLAAmt.setValue(txtODLAAmt.getValued());
				System.out.println("Old Loanable");
			}

			computeOnAvailedAmt();
		}

		if (_OtherRequest.getGroupID(FinalBuyerType).equals("05")) {
			String SQL = "";
		}
	}

	/****************** COMPUTATIONS *******************/
	//COMPUTES THE DP AND THE LOANABLE AMOUNT FOR PAGIBIG, INHOUSE AND CASH
	public void computeAmounts() {
		setFinalVariables();

		BigDecimal gsp = FinalGSP;
		BigDecimal disc_amt = new BigDecimal("0.00");

		if (_OtherRequest.isInTable("08", modelReqType)) {
			disc_amt = (BigDecimal) txtNDDiscount.getValued();
		} else {
			disc_amt = FinalDiscAmt;
		}

		//XXX check IF FINAL DISCOUNT RATE DAPAT DITO

		String old_buyer_type = old_buyer_type_id;

		FinalNSP = BigDecimal
				.valueOf((gsp.doubleValue() - disc_amt.doubleValue()) / (1 + (FinalVatRate.doubleValue() / 100)));
		System.out.printf("Display Value Of Disc Amount: %s%n", disc_amt);
		System.out.printf("Display Value of Final NSP: %s%n", FinalNSP);

		BigDecimal vat_amt = gsp.subtract(FinalNSP.add(disc_amt));
		txtNDVat.setValue(vat_amt);
		txtNDVatRate.setValue(FinalVatRate);

		BigDecimal dp_rate = new BigDecimal("0.00");
		BigDecimal dp_amt = new BigDecimal("0.00");
		BigDecimal availed_amt = new BigDecimal("0.00");
		BigDecimal availed_rate = new BigDecimal("0.00");
		BigDecimal dp_paid_total = _OtherRequest.getDPPaidTotal(old_entity_id, old_proj_id, old_unit_id, old_seq_no);
		System.out.printf("Display value of Final Buyer Type: %s%n", _OtherRequest.getGroupID(FinalBuyerType));

		if (_OtherRequest.getGroupID(FinalBuyerType).equals("04")) {
			if (_OtherRequest.hasMergeUnit(old_unit_id)) {

			} else {
				/*if(_OtherRequest.getGroupID(old_buyer_type).equals(_OtherRequest.getGroupID(FinalBuyerType)) == false || _OtherRequest.isInTable("16", modelReqType)){
				
					//System.out.println("Dumaan dito sa nagbago yung loanable amount");
				}else{
					availed_amt = (BigDecimal) txtODLAAmt.getValued();
					//System.out.println("Dumaan dito sa di nagbago yung loanable");
				}*/
				if (_OtherRequest.isInTable("16", modelReqType) || _OtherRequest.isInTable("02", modelReqType)
						|| _OtherRequest.isInTable("12", modelReqType) || _OtherRequest.isInTable("06", modelReqType)) {
					if (_OtherRequest.getGroupID(old_buyer_type_id).equals("03")) {
						availed_amt = _OtherRequest.getPagibigLoanableAmt(FinalUnitID, FinalHouseModel,
								(BigDecimal) txtODGSP.getValued());
					} else {
						if (_OtherRequest.withExistingChangeLoanable(old_entity_id, old_proj_id, old_unit_id,
								old_seq_no)) {
							if (_OtherRequest.isInTable("06", modelReqType)
									|| _OtherRequest.isInTable("12", modelReqType)
									|| _OtherRequest.isInTable("07", modelReqType)) {
								availed_amt = _OtherRequest.getPagibigLoanableAmt(FinalUnitID, FinalHouseModel,
										FinalNSP);
							} else {
								availed_amt = (BigDecimal) txtODLAAmt.getValued();
								System.out.println("Dumaan dito sa with existing change loanable");
							}
						} else {
							//availed_amt = _OtherRequest.getPagibigLoanableAmt(FinalUnitID, FinalHouseModel, FinalNSP);
							if (_OtherRequest.isInTable("06", modelReqType)
									|| _OtherRequest.isInTable("12", modelReqType)
									|| _OtherRequest.isInTable("07", modelReqType)) {
								availed_amt = _OtherRequest.getPagibigLoanableAmt(FinalUnitID, FinalHouseModel,
										FinalNSP);
							} else {
								availed_amt = (BigDecimal) txtODLAAmt.getValued();
								System.out.println("Dumaan dito sa with existing change loanable");
							}
						}
					}
				} else {
					if (_OtherRequest.isInTable("08", modelReqType)) {
						availed_amt = _OtherRequest.getPagibigLoanableAmt(FinalUnitID, FinalHouseModel, FinalNSP);
					} else {
						availed_amt = (BigDecimal) txtODLAAmt.getValued();
					}

				}
				/*if(_OtherRequest.isInTable("02", modelReqType)){
					System.out.println("Dumaan dito sa nagbago yung loanable amount");
					availed_amt = _OtherRequest.getPagibigLoanableAmt(FinalUnitID, FinalHouseModel, FinalNSP);
				}else{
					System.out.println("Dumaan dito sa di nagbago yung loanable");
					availed_amt = (BigDecimal) txtODLAAmt.getValued();
				}*/
			}
			//System.out.printf("Display availed amount sa Compute Amouts: %s", availed_amt);

			if (_OtherRequest.getGroupID(old_buyer_type_id).equals("03")) {
				dp_amt = ((BigDecimal) txtODGSP.getValued()).subtract(availed_amt);
			} else {
				dp_amt = FinalNSP.subtract(availed_amt);
			}

			//XXX COMMENT THIS SECTION (EXECUTE ONLY IF CLIENT HAS PAID DP)

			/*if(dp_paid_total.doubleValue()>= dp_amt.doubleValue() && (_OtherRequest.isInTable("06", modelReqType) 
					|| _OtherRequest.hasChangedClientClass(modelReqType) || _OtherRequest.hasChangedPrincipal(modelReqType)) == false ){
				dp_amt = dp_paid_total;
				availed_amt = FinalNSP.subtract(dp_amt);
				System.out.printf("Display availed amt: %s%n", availed_amt);
			}*/

			//dp_rate = _OtherRequest.roundNumber((dp_amt.divide(FinalNSP)).multiply(new BigDecimal("100")));
			dp_rate = BigDecimal.valueOf((dp_amt.doubleValue() / FinalNSP.doubleValue()) * 100);

			//System.out.printf("Display dp_rate: %s%n", dp_rate);
			availed_rate = new BigDecimal("100").subtract(dp_rate);

		} else {//FOR INHOUSE AND CASH SCHEDULE

			//System.out.printf("Display Disc Rate: %s%n", FinalDPRate);
			dp_rate = FinalDPRate;
			dp_amt = FinalDPAmt;
			BigDecimal dp_rate_amt = new BigDecimal("0.00");
			dp_rate_amt = FinalDPAmt;
			if (dp_rate.doubleValue() > 0) {
				//System.out.printf("Display Final NSP: %s%n", FinalNSP);
				dp_amt = BigDecimal.valueOf(FinalNSP.doubleValue() * (dp_rate.doubleValue() / 100));
				//System.out.printf("Display DP AMount: %s%n", dp_amt);
			} else {
				dp_amt = dp_rate_amt;
			}

			availed_amt = FinalNSP.subtract(dp_amt);
			availed_rate = new BigDecimal("100.00").subtract(dp_rate);

			//XXX EXECUTE ONLY IF CLIENT HAS PAID DP
			if (dp_paid_total.doubleValue() >= dp_amt.doubleValue() && (_OtherRequest.isInTable("06", modelReqType)
					|| _OtherRequest.hasChangedClientClass(modelReqType)) == false) {
				dp_amt = dp_paid_total;
				availed_amt = FinalNSP.subtract(dp_amt);
				//dp_rate = (dp_amt.divide(FinalNSP)).multiply(new BigDecimal("100"));
				dp_rate = BigDecimal.valueOf((dp_amt.doubleValue() / FinalNSP.doubleValue()) * 100);
				availed_rate = new BigDecimal("100").subtract(dp_rate);
			}

			if (_OtherRequest.getGroupID(old_buyer_type_id).equals("03")) {
				disc_amt = BigDecimal.valueOf(gsp.doubleValue() * (FinalDiscRate.doubleValue() / 100));
				txtNDDiscount.setValue(disc_amt);
			}

			FinalNSP = BigDecimal
					.valueOf((gsp.doubleValue() - disc_amt.doubleValue()) / (1 + (FinalVatRate.doubleValue() / 100)));
			dp_amt = FinalNSP;
		}
		System.out.printf("Display value of FinalNSP: %s%n", FinalNSP);
		txtNDDownpayment.setValue(dp_amt);
		//txtNDDownpayment.setValue(FinalNSP);
		txtNDLAAmt.setValue(availed_amt);
		if (_OtherRequest.getGroupID(old_buyer_type_id).equals("03")
				&& old_pmt_scheme_id.equals(lookupNDPmtScheme.getValue())) {
			txtNDNSP.setValue(txtODGSP.getValued());
		} else {
			txtNDNSP.setValue(FinalNSP);
		}
		txtNDDPRate.setValue(dp_rate);
		txtNDLARate.setValue(availed_rate);
	}

	private void computeGSPOnHouseModel() {//COMPUTES THE SELLING PRICE BASED ON THE HOUSE MODEL
		setFinalVariables();

		pgSelect db = new pgSelect();

		String sql = "select compute_selling_price('" + FinalUnitID + "', '" + FinalHouseModel + "')";
		db.select(sql);

		FncSystem.out("Display sql for Selling Price", sql);

		if (db.isNotNull()) {
			BigDecimal gsp = (BigDecimal) db.getResult()[0][0];
			txtNDGSP.setValue(gsp);
		}
		computeAmounts();
		computeDPONAvailed();
	}

	private void computeBankFinance(String unit_id) {
		pgSelect db = new pgSelect();
		String SQL = "select * from compute_bank_finance_default_amts('" + unit_id + "');";
		db.select(SQL);

		if (db.isNotNull()) {
			BigDecimal dp_equity = new BigDecimal("0.00");
			BigDecimal loan_amt = new BigDecimal("0.00");
			BigDecimal dp_rate = new BigDecimal("0.00");
			BigDecimal loan_amt_rate = new BigDecimal("0.00");

			for (int x = 0; x < db.getRowCount(); x++) {
				dp_equity = (BigDecimal) db.getResult()[x][0];
				loan_amt = (BigDecimal) db.getResult()[x][1];
				dp_rate = (BigDecimal) db.getResult()[x][2];
				loan_amt_rate = (BigDecimal) db.getResult()[x][3];

			}
			txtNDDownpayment.setValue(dp_equity);
			txtNDLAAmt.setValue(loan_amt);
			txtNDDPRate.setValue(dp_rate);
			txtNDLARate.setValue(loan_amt_rate);
		}
	}

	private void computeOnDiscRate() {//Computes the Discount Amount as the Disc rate Changes 
		setFinalVariables();

		if (((BigDecimal) txtNDDiscRate.getValued()).doubleValue() >= 100) {
			JOptionPane.showMessageDialog(null, "Invalid Rate");
			txtNDDiscRate.setValue(new BigDecimal("0.00"));
		} else {
			BigDecimal disc_rate = new BigDecimal("0.00");
			disc_rate = ((BigDecimal) txtNDDiscRate.getValued()).divide(new BigDecimal("100"));
			//disc_rate = (BigDecimal) txtNDDiscRate.getValued();
			//BigDecimal disc_rate_amt= BigDecimal.valueOf(disc_rate.doubleValue()/100);
			//System.out.printf("Display final gsp: %s%n", FinalGSP);
			BigDecimal disc_amt = FinalGSP.multiply(disc_rate);
			txtNDDiscount.setValue(disc_amt);

			computeAmounts();
		}
	}

	private void computeONDiscAmount() {//COMPUTES BASED ON THE DISCOUNT AMOUNT
		setFinalVariables();

		if (((BigDecimal) txtNDDiscount.getValued()).doubleValue() >= FinalNSP.doubleValue()) {
			JOptionPane.showMessageDialog(null, "Invalid Amount");
			txtNDDiscount.setValue(new BigDecimal("0.00"));
		} else {
			BigDecimal disc_amt = new BigDecimal("0.00");
			disc_amt = (BigDecimal) txtNDDiscount.getValued();
			//System.out.printf("Display Final GSP: %s%n", FinalGSP);

			BigDecimal disc_rate = BigDecimal.valueOf((disc_amt.doubleValue() / FinalGSP.doubleValue()) * 100);
			txtNDDiscRate.setValue(disc_rate);
			computeAmounts();
		}
	}

	private void computeOnDPRate() {//COMPUTES AS THE DP RATE CHANGES
		setFinalVariables();

		if (FinalDPRate.doubleValue() > 100) {
			JOptionPane.showMessageDialog(pnlNewData, "Invalid Rate");
			txtNDDPRate.setValue(new BigDecimal("0.00"));
		} else {
			BigDecimal dp_amt = (FinalDPRate.divide(new BigDecimal("100"))).multiply(FinalNSP);
			txtNDDownpayment.setValue(dp_amt);

			//System.out.printf("Display final nsp: %s%n", FinalNSP);
			BigDecimal availed_amt = FinalNSP.subtract(dp_amt);
			txtNDLAAmt.setValue(availed_amt);

			BigDecimal availed_rate = new BigDecimal(100).subtract(FinalDPRate);
			txtNDLARate.setValue(availed_rate);
		}
	}

	private void computeAvailedOnDP() {// COMPUTES AS THE DP AMOUNT CHANGES
		setFinalVariables();

		BigDecimal availed_amt = FinalNSP.subtract(FinalDPAmt);

		txtNDLAAmt.setValue(availed_amt);
		computeDPAvailedRate();
	}

	private void computeOnAvailedRate() {//COMPUTES AS THE AVAILED RATE CHANGES

		BigDecimal availed_rate = (BigDecimal) txtNDLARate.getValued();
		BigDecimal net_sell_price = (BigDecimal) txtNDNSP.getValued();

		//BigDecimal dp_rate = new BigDecimal("100.00").subtract(availed_rate);
		BigDecimal dp_rate = BigDecimal.valueOf(100 - availed_rate.doubleValue());
		//BigDecimal availed_amt = (new BigDecimal(".01").multiply(availed_rate)).multiply(net_sell_price);
		BigDecimal availed_amt = BigDecimal.valueOf((.01 * availed_rate.doubleValue()) * net_sell_price.doubleValue());
		BigDecimal dp_amt = net_sell_price.subtract(availed_amt);

		txtNDDPRate.setValue(dp_rate);
		txtNDLAAmt.setValue(availed_amt);
		txtNDDownpayment.setValue(dp_amt);

		//System.out.printf("Display Availed Amt: %s%n", availed_amt);
	}

	private void computeAvailedOnDPAdditional() {
		setFinalVariables();

		BigDecimal additional_dp = (BigDecimal) txtNDAdditionalDP.getValued();

		BigDecimal availed_amt = FinalNSP.subtract(FinalDPAmt.add(additional_dp));
		txtNDLAAmt.setValue(availed_amt);
		computeOnAvailedAmt();
		//computeDPAdditionalAvailedRate();
	}

	private void computeDPAdditionalAvailedRate() {
		BigDecimal availed_rate = (BigDecimal) txtNDLARate.getValued();
		BigDecimal net_sell_price = (BigDecimal) txtNDNSP.getValued();

		//BigDecimal dp_rate = new BigDecimal("100.00").subtract(availed_rate);
		BigDecimal dp_rate = BigDecimal.valueOf(100 - availed_rate.doubleValue());
		//BigDecimal availed_amt = (new BigDecimal(".01").multiply(availed_rate)).multiply(net_sell_price);
		BigDecimal availed_amt = BigDecimal.valueOf((.01 * availed_rate.doubleValue()) * net_sell_price.doubleValue());
		BigDecimal dp_amt = net_sell_price.subtract(availed_amt);

		txtNDDPRate.setValue(dp_rate);
		txtNDLAAmt.setValue(availed_amt);
		txtNDDownpayment.setValue(dp_amt);
	}

	private void computeOnAvailedAmt() {
		setFinalVariables();
		BigDecimal dp_amt = new BigDecimal("0.00");
		System.out.printf("Display value of Final NSP: %s%n", FinalNSP);
		System.out.printf("Display value of Final Loan Amt: %s%n", FinalLoanAmt);

		if (_OtherRequest.getGroupID(FinalBuyerType).equals("05")) {
			dp_amt = _OtherRequest.computeEquityFinance(FinalUnitID, FinalLoanAmt);
		} else {

			dp_amt = FinalNSP.subtract(FinalLoanAmt);
		}

		txtNDDownpayment.setValue(dp_amt);

		computeDPAvailedRate();
	}

	private void computeDPONAvailed() {
		setFinalVariables();

		BigDecimal dp_amt = FinalNSP.subtract(FinalLoanAmt);
		txtNDDownpayment.setValue(dp_amt);
		//System.out.printf("Display dp amount: %s%n", dp_amt);
		computeDPAvailedRate();
	}

	/*************** END OF COMPUTATIONS **********************/

	public void setNewVatRate(String unit_id, BigDecimal final_gsp) {//PUT THIS IN THE 
		pgSelect db = new pgSelect();
		String sql = "select case when vat = true then 12.00 else 0.00 end from mf_unit_info where unit_id = '"
				+ unit_id + "'";
		db.select(sql);

		BigDecimal vat_rate = (BigDecimal) db.getResult()[0][0];
		BigDecimal vat_amt = BigDecimal
				.valueOf(final_gsp.doubleValue() - (final_gsp.doubleValue() / (1 + vat_rate.doubleValue())));

		txtNDVatRate.setValue(vat_rate);
		txtNDVat.setValue(vat_amt);
	}

	public Object[] getRequestData() {//RETURNS THE COMBINED DATA FROM THE COMPONENTS IN THE OLD AND NEW DATA

		return new Object[] { lookupODClient.getValue(), //0
				txtODClientName.getText(), //1
				_JInternalFrame.GET_CIVIL_STATUS_CODE((String) cmbODCivilStatus.getSelectedItem()), //2
				old_proj_id, //3
				old_unit_id, //4
				old_seq_no, //5
				txtODLotArea.getText(), //6
				old_model_id, //7
				old_agent_id, //8
				old_buyer_type_id, //9
				old_pmt_scheme_id, //10
				txtODGSP.getValued(), //11
				txtODDiscAmt.getValued(), //12
				txtODDiscRate.getValued(), //13
				txtODVat.getValued(), //14
				txtODVatRate.getValued(), //15
				txtODNSP.getValued(), //16
				txtODDownpayment.getValued(), //17
				txtODDPRate.getValued(), //18
				txtODDPTerm.getText(), //19
				null, //20				//OLD DP DUE DATE
				txtODLAAmt.getValued(), //21
				txtODLARate.getValued(), //22
				txtODMATerm.getText(), //23
				null, //24				//OLD MA DUE DATE
				null, //25				//OLD START DATE
				null, //26				//OLD END DATE
				new BigDecimal("0.00"), //27  	//OLD INT RATE
				old_fire_amt, //28	//OLD FIRE AMT
				//NEW DATA
				getSelectedRequest(), //29
				lookupNDClient.getValue(), //30
				txtNDFirstName.getText().toUpperCase(), //31
				txtNDMiddleName.getText().toUpperCase(), //32
				txtNDLastName.getText().toUpperCase(), //33
				_JInternalFrame.GET_CIVIL_STATUS_CODE((String) cmbNDCivilStatus.getSelectedItem()), //34
				lookupNDProject.getValue(), //35
				lookupNDUnit.getValue(), //36
				"", //37
				txtNDLotArea.getText(), //38
				lookupNDHouseModel.getValue(), //39
				lookupNDAgent.getValue(), //40
				lookupNDBuyerType.getValue(), //41
				lookupNDPmtScheme.getValue(), //42
				txtNDGSP.getValued(), //43
				txtNDDiscount.getValued(), //44
				txtNDDiscRate.getValued(), //45
				txtNDVat.getValued(), //46
				txtNDVatRate.getValued(), //47
				txtNDNSP.getValued(), //48
				txtNDDownpayment.getValued(), //49
				txtNDDPRate.getValued(), //50
				txtNDDPTerms.getText(), //51
				null, //52					//NEW DP DUE DATE
				txtNDLAAmt.getValued(), //53
				txtNDLARate.getValued(), //54
				txtNDMATerms.getText(), //55
				null, //dateNDDueDateMA.getDate(), //56    //NEW MA DUE DATE
				new BigDecimal("0.00"), //57			//NEW INT RATE
				new BigDecimal("0.00"), //58 
				txtReqCli.getText(), //59
				txtRemarks.getText().replace("'", "''"), //60
				cmbReqPurpose.getSelectedItem(), //61
				txtNDAdditionalDP.getValued(), //62
				txtPagibigCotri.getValued() //63 

		};
	}

	//SETS THE VALUE OF THE FIRST, MIDDLE AND LASTNAME IN THE NEW DATA IF CORRECTION OF NAME IS SELECTED

	public void newOtherRequest() {//NEW CLIENT REQUEST
		lookupODClient.setEditable(true);
		txtReqCli.setEditable(true);
		txtRemarks.setEditable(true);
		//splitNewData.setEnabled(true);
		//txtReqNo.setText(_OtherRequest.getRequestNo());
		//btnState(false, false, false, false, true, false, false, false, false, false);
		btnState(false, false, false, true, true, false, false, false);
	}

	public void clearOtherRequest() {// CLEARS THE COMPONENT OF THE OLD AND THE NEW DATA

		lookupODClient.setEditable(false);
		this.setComponentsEditable(pnlOldData, false);
		this.setComponentsEditable(pnlSouthUpper, false);

		clearOldData();
		clearNewData();

		txtReqNo.setText("");
		txtReqStatus.setText("");
		txtReqCli.setText("");
		txtRemarks.setText("");

		//CLEARS THE TABLEMODEL
		modelReqType.clear();
		scrollReqType.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		rowHeaderReqType = tblReqType.getRowHeader();
		scrollReqType.setRowHeaderView(rowHeaderReqType);

		tblReqType.setEnabled(true);
		btnState(true, true, false, false, false, false, false, false);
		grpNewEdit.clearSelection();
	}

	public void clearOldData() { //CLEARS THE FIELDS IN THE OLD DATA PANEL

		old_entity_id = null;
		old_unit_status = null;
		old_proj_id = null;
		old_unit_id = null;
		old_model_id = null;
		old_agent_id = null;
		old_buyer_type_id = null;
		old_pmt_scheme_id = null;

		lookupODClient.setValue(null);
		txtODClientName.setText("");
		//txtCurrentStatID.setText("");
		txtCurrentStatDesc.setText("");
		dteReserDate.setDate(null);
		dteRequestDate.setDate(null);
		//txtODProjID.setText("");
		txtODProjName.setText("");
		//txtODUnitID.setText("");
		txtODUnitDesc.setText("");
		txtODLotArea.setText("");
		txtODSeqNo.setText("");
		cmbODCivilStatus.setSelectedItem(null);
		//txtODHouseModelID.setText("");
		txtODHouseModel.setText("");
		//txtODAgentID.setText("");
		txtODAgentName.setText("");
		//txtODBuyertypeID.setText("");
		txtODBuyerType.setText("");
		//txtODPmtSchemeID.setText("");
		txtODPmtScheme.setText("");
		txtODGSP.setValue(new BigDecimal("0.00"));
		txtODDiscAmt.setValue(new BigDecimal("0.00"));
		txtODDiscRate.setValue(new BigDecimal("0.00"));
		txtODVat.setValue(new BigDecimal("0.00"));
		txtODVatRate.setValue(new BigDecimal("0.00"));
		txtODNSP.setValue(new BigDecimal("0.00"));
		txtODDownpayment.setValue(new BigDecimal("0.00"));
		txtODDPRate.setValue(new BigDecimal("0.00"));
		txtODDPTerm.setText("");
		//dateODDueDateDP.setDate(null);
		txtODLAAmt.setValue(new BigDecimal("0.00"));
		txtODLARate.setValue(new BigDecimal("0.00"));
		txtODMATerm.setText("");
		//dateODDueDateMA.setDate(null);
		//txtODIntRate.setValue(new BigDecimal("0.00"));
		//txtODFireAmt.setValue(new BigDecimal("0.00"));
		//dteODStart.setDate(null);
		//dteODEnd.setDate(null);
	}

	public void clearNewData() {//CLEARS THE FIELDS IN THE NEW DATA PANEL

		this.setComponentsEnabled(pnlNDCEast, false);
		this.setComponentsEnabled(pnlNDCWest, false);

		btnRequest.setEnabled(false);
		lblNDClient.setText("Client");
		lblNDproject.setText("Project");
		lblNDUnit.setText("Unit");
		lblNDCivilStatus.setText("Civil Status");
		lblNDHouseModel.setText("House Model");
		lblNDSellingAgent.setText("Selling Agent");
		lblNDBuyerType.setText("Buyer Type");
		lblNDPmtScheme.setText("Payment Scheme");
		lblNDGSP.setText("Selling Price");
		lblNDDiscRate.setText("Discount Rate");
		lblNDDiscount.setText("Discount");
		lblNDDPAmt.setText("DP / Equity");
		lblNDDPRate.setText("DP Rate");
		lblNDDPTerm.setText("DP Term");
		//lblNDDueDateDP.setText("DP Due Date");
		lblNDLoanAvailed.setText("Loan Availed");
		lblNDLARate.setText("Loan Availed Rate");
		lblNDMATerm.setText("MA Term");
		lblReqPurpose.setText("Request Purpose");
		//lblNDDueDateMA.setText("MA Due Date");

		lookupNDClient.setValue(null);
		txtNDFirstName.setText("");
		txtNDMiddleName.setText("");
		txtNDLastName.setText("");
		cmbNDCivilStatus.setSelectedItem(null);
		cmbNDCivilStatus.setEnabled(false);
		lookupNDProject.setValue(null);
		txtNDProject.setText("");
		lookupNDUnit.setValue(null);
		txtNDUnit.setText("");
		txtNDLotArea.setText("");
		lookupNDHouseModel.setValue(null);
		txtNDHouseModel.setText("");
		lookupNDAgent.setValue(null);
		txtNDAgent.setText("");
		lookupNDBuyerType.setValue(null);
		txtNDBuyerType.setText("");
		lookupNDPmtScheme.setValue(null);
		txtNDPmtScheme.setText("");
		txtNDGSP.setValue(new BigDecimal("0.00"));
		txtNDDiscount.setValue(new BigDecimal("0.00"));
		txtNDDiscRate.setValue(new BigDecimal("0.00"));
		txtNDVat.setValue(new BigDecimal("0.00"));
		txtNDVatRate.setValue(new BigDecimal("0.00"));
		txtNDNSP.setValue(new BigDecimal("0.00"));
		txtNDDownpayment.setValue(new BigDecimal("0.00"));
		txtNDDPRate.setValue(new BigDecimal("0.00"));
		txtNDDPTerms.setText("");
		txtNDLAAmt.setValue(new BigDecimal("0.00"));
		txtNDLARate.setValue(new BigDecimal("0.00"));
		txtNDMATerms.setText("");
		cmbReqPurpose.setEnabled(false);

		//txtNDCreditAmt.setValue(new BigDecimal("0.00"));
		txtNDAdditionalDP.setValue(new BigDecimal("0.00"));
		txtPagibigCotri.setValue(FncBigDecimal.zeroValue());
	}

	public void setSQLLookUps(Object[] data) {// 
		String final_entity_id = (String) data[0];
		String final_proj_id = (String) data[2];
		String final_unit_id = (String) data[3];
		String final_seq_no = (String) data[4];
		String final_model_id = (String) data[6];
		String final_buyer_type = (String) data[8];
		//System.out.printf("Display final buyertype %s%n", final_buyer_type);

		lookupNDUnit.setLookupSQL(_OtherRequest.sqlUnit(final_proj_id, old_unit_id));
		lookupNDHouseModel.setLookupSQL(_OtherRequest.sqlHouseModel(final_unit_id));
		lookupNDBuyerType
				.setLookupSQL(_OtherRequest.sqlBuyerType(final_unit_id, old_buyer_type_id, getSelectedRequest()));
		lookupNDPmtScheme.setLookupSQL(_OtherRequest.sqlPaymentScheme(final_unit_id, final_buyer_type));

	}

	private void computeDPAvailedRate() {

		BigDecimal dp_amt = (BigDecimal) txtNDDownpayment.getValued();
		//BigDecimal dp_rate = (dp_amt.divide(FinalNSP)).multiply(new BigDecimal("100"));
		BigDecimal dp_rate = BigDecimal.valueOf((dp_amt.doubleValue() / FinalNSP.doubleValue()) * 100);
		txtNDDPRate.setValue(dp_rate);

		BigDecimal availed_rate = new BigDecimal("100").subtract(dp_rate);
		txtNDLARate.setValue(availed_rate);

	}

	public boolean hasChangedClientClass() { //RETURNS TRUE IF CHANGE OF CLIENT CLASS WAS SELECTED

		if (_OtherRequest.isInTable("02", modelReqType) || _OtherRequest.isInTable("15", modelReqType)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasChangedPrincipal() { //RETURNS TRUE IF CHANGE OF PRINCIPAL APPLICANT WAS SELECTED
		if (_OtherRequest.isInTable("05", modelReqType)) {
			return true;
		} else {
			return false;
		}
	}

	public void setFinalVariables() {//

		if (lookupNDClient.getValue() != null) {
			FinalEntityID = lookupNDClient.getValue();
		} else {
			FinalEntityID = lookupODClient.getValue();
		}

		if (lookupNDProject.getValue() != null) {
			FinalProjID = lookupNDProject.getValue();
		} else {
			FinalProjID = old_proj_id;
		}

		/*if(dateNDDueDateDP.getDate() == null){
			FinalDPDueDate = dateODDueDateDP.getDate();
		}else{
			FinalDPDueDate = dateNDDueDateDP.getDate();
		}
		
		if(dateNDDueDateMA.getDate() == null){
			FinalMADueDate = dateODDueDateMA.getDate();
		}else{
			FinalMADueDate = dateNDDueDateMA.getDate();
		}*/

		if (lookupNDUnit.getValue() != null) {
			FinalUnitID = lookupNDUnit.getValue();
			FinalLotArea = txtNDLotArea.getText();

		} else {

			FinalUnitID = old_unit_id;
			FinalLotArea = txtODLotArea.getText();
			FinalSeqNo = txtODSeqNo.getText();
		}

		if (lookupNDHouseModel.getValue() != null) {
			FinalHouseModel = lookupNDHouseModel.getValue();
		} else {
			FinalHouseModel = old_model_id;
		}

		if (lookupNDAgent.getValue() != null) {
			FinalAgent = lookupNDAgent.getValue();
		} else {
			FinalAgent = old_agent_id;
		}

		if (lookupNDBuyerType.getValue() != null) {
			FinalBuyerType = lookupNDBuyerType.getValue();
			//FinalFireAmt = (BigDecimal) txtNDFireAmt.getValued();

		} else {
			FinalBuyerType = old_buyer_type_id;
			//FinalFireAmt = (BigDecimal) txtODFireAmt.getValued();
		}

		if (lookupNDPmtScheme.getValue() != null) {
			FinalPmtScheme = lookupNDPmtScheme.getValue();
			//FinalIntRate = (BigDecimal) txtNDIntRate.getValued();
		} else {
			FinalPmtScheme = old_pmt_scheme_id;
			//FinalIntRate = (BigDecimal) txtODIntRate.getValued();
		}

		if (((BigDecimal) txtNDGSP.getValued()).doubleValue() == 0) {
			FinalGSP = (BigDecimal) txtODGSP.getValued();
		} else {
			FinalGSP = (BigDecimal) txtNDGSP.getValued();
		}

		if (((BigDecimal) txtNDDiscount.getValued()).doubleValue() == 0 /*&& lookupNDPmtScheme.getValue() == null*/) {
			FinalDiscAmt = (BigDecimal) txtODDiscAmt.getValued();
			FinalDiscRate = (BigDecimal) txtODDiscRate.getValued();

		} else {
			FinalDiscAmt = (BigDecimal) txtNDDiscount.getValued();
			FinalDiscRate = (BigDecimal) txtNDDiscRate.getValued();
		}

		//FinalDiscAmt = (BigDecimal) txtNDDiscount.getValued();

		if (((BigDecimal) txtNDVat.getValued()).doubleValue() == 0 || txtNDVat.getValued() == null) {
			FinalVatAmt = (BigDecimal) txtODVat.getValued();
			FinalVatRate = (BigDecimal) txtODVatRate.getValued();
		} else {
			FinalVatAmt = (BigDecimal) txtNDVat.getValued();
			FinalVatRate = (BigDecimal) txtNDVatRate.getValued();
		}

		if (((BigDecimal) txtNDNSP.getValued()).doubleValue() == 0 || txtNDNSP.getValued() == null) {
			FinalNSP = (BigDecimal) txtODNSP.getValued();
		} else {
			FinalNSP = (BigDecimal) txtNDNSP.getValued();
		}

		if (((BigDecimal) txtNDDownpayment.getValued()).doubleValue() == 0) {
			FinalDPAmt = (BigDecimal) txtODDownpayment.getValued();
		} else {
			FinalDPAmt = (BigDecimal) txtNDDownpayment.getValued();
		}

		if (((BigDecimal) txtNDDPRate.getValued()).doubleValue() == 0 || txtNDDPRate.getValued() == null) {
			FinalDPRate = (BigDecimal) txtODDPRate.getValued();
		} else {
			FinalDPRate = (BigDecimal) txtNDDPRate.getValued();
		}

		if (txtNDDPTerms.getText().isEmpty() || txtNDDPTerms.getText().equals("")) {
			FinalDPTerm = txtODDPTerm.getText();
		} else {
			FinalDPTerm = txtNDDPTerms.getText();
		}
		//System.out.printf("Display Final Buyer Type: %s%n", FinalBuyerType);

		if (((BigDecimal) txtNDLAAmt.getValued()).doubleValue() == 0) {
			FinalLoanAmt = (BigDecimal) txtODLAAmt.getValued();
		} else {
			FinalLoanAmt = (BigDecimal) txtNDLAAmt.getValued();
		}

		if (((BigDecimal) txtNDLARate.getValued()).doubleValue() == 0) {
			FinalLoanRate = (BigDecimal) txtODLARate.getValued();
		} else {
			FinalLoanRate = (BigDecimal) txtNDLARate.getValued();
		}

		if (txtNDMATerms.getText().isEmpty() || txtNDMATerms.getText().equals("")) {
			FinalMATerm = txtODMATerm.getText();
		} else {
			FinalMATerm = txtNDMATerms.getText();
		}
	}

	private void createCommissionSched(String request_no) {

		if (CreateCommissionSchedule.isClientRequestHasEffectOnCommission(request_no)) {
			Object[] data = displayNewData2(request_no);

			String new_entity_id = (String) data[4];
			String new_agent_id = (String) data[11];

			String new_proj_id = (String) data[13];
			String new_unit_id = (String) data[15];
			BigDecimal new_gsp = (BigDecimal) data[17];
			String new_pmt_scheme_id = (String) data[18];
			String new_model_id = (String) data[20];
			String new_seq_no = (String) data[36];
			//setFinalVariables();

			System.out.printf("Display Final Seq No: %s%n", FinalSeqNo);
			System.out.printf("Display old seq_no: %s%n", old_seq_no);
			System.out.printf("Display new seq_no: %s%n", new_seq_no);

			//Cancel Commission Schedule of Previous Unit
			//Old Details here
			CreateCommissionSchedule.cancelCommSchedule(_OtherRequest.getPBL(old_unit_id), Integer.valueOf(old_seq_no),
					old_entity_id);
			//Re-create Commission Schedule
			//New Details Here
			//			Object[] unit_dtl = CreateCommissionSchedule.getUnitDetails(_OtherRequest.getPBL(new_unit_id), Integer.valueOf(new_seq_no));
			//			//String agent_id = unit_dtl[3].toString();
			//
			//			String co_id = unit_dtl[1].toString();
			//			String datersvd = _OtherRequest.getReserVationDate(new_entity_id, new_proj_id, new_unit_id, Integer.valueOf(new_seq_no)).toString();
			//			String phase_code = unit_dtl[7].toString();
			//			Integer frequency_no = _OtherRequest.getFrequencyNo(new_entity_id, new_proj_id, new_unit_id, Integer.valueOf(new_seq_no));
			//
			//			CreateCommissionSchedule.createCommHeader(new_agent_id, new_pmt_scheme_id, new_entity_id, new_proj_id, _OtherRequest.getPBL(new_unit_id), 
			//					//seq_no, sellingprice, co_id, datersvd, hse_model_id, phase_code, 1  );
			//					Integer.valueOf(new_seq_no), new_gsp.doubleValue() , co_id, datersvd, new_model_id, phase_code, frequency_no);

		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if (actionCommand.equals("New")) {//NEW CLIENT REQUEST
			grpNewEdit.setSelectedButton(arg0);
			newOtherRequest();
		}

		if (actionCommand.equals("Search")) {//SEARCH FOR THE REQUEST AND ITS DETAILS
			clearOtherRequest();
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Request", _OtherRequest.sqlSearch(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			FncSystem.out("Display sql for Search", _OtherRequest.sqlSearch());

			Object[] data = dlg.getReturnDataSet();

			try {
				String req_no = (String) data[0];
				String req_stat = (String) data[1];
				Date req_date = (Date) data[2];
				String entity_id = (String) data[3];
				String proj_id = (String) data[5];
				String pbl_id = (String) data[6];
				String unit_id = (String) data[7];
				String unit_desc = (String) data[8];
				Integer seq_no = (Integer) data[9];

				displayOldData(entity_id, pbl_id, proj_id, seq_no);
				txtReqNo.setText((String) data[0]);
				lookupODClient.setValue((String) data[3]);
				txtODClientName.setText((String) data[4]);
				txtReqStatus.setText(req_stat);
				dteRequestDate.setDate(req_date);

				_OtherRequest.displaySelectedReq((String) data[0], modelReqType, rowHeaderReqType);
				scrollReqType.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
						FncTables.getRowHeader_Footer(Integer.toString(tblReqType.getRowCount())));
				tblReqType.packAll();

				displayNewData(req_no);

				tblReqType.setEnabled(false);
				rowHeaderReqType.setEnabled(false);

				if (req_stat.equals("ACTIVE")) {
					if (UserInfo.EmployeeCode.equals("900298")
							|| UserInfo.EmployeeCode.equals("900421") /*|| UserInfo.EmployeeCode.equals("900462")*/
							|| _OtherRequest.canApprove()) {
						if (_OtherRequest.requestAddBy(req_no).equals(UserInfo.EmployeeCode)) {
							btnState(false, false, true, false, true, true, true, true);
						} else {
							btnState(false, false, false, false, true, true, true, true);
						}
					} else if (UserInfo.ADMIN) {
						btnState(false, false, true, false, true, true, true, true);
					} else {
						btnState(false, false, true, false, true, true, false, true);
					}
				} else {
					btnState(false, false, false, false, true, false, false, true);
				}

				/*if(req_no.equals("180115-00001")){
					txtNDAdditionalDP.setValue(new BigDecimal("103000"));
				}*/

				/*if(req_no.trim().equals("171127-00002")){
					btnState(false, false, true, false, true, false, false, true);
				}*/

			} catch (NullPointerException a) {
			}
		}

		if (actionCommand.equals("Edit")) {//SETS THE COMPONENTS TO BE EDITABLE BASED ON THE SELECTED REQUEST
			for (int x = 0; x < modelReqType.getRowCount(); x++) {
				String req_id = (String) modelReqType.getValueAt(x, 0);
				setUiOnRequest(req_id);
			}

			/*if(txtReqNo.getText().trim().equals("171013-00005")){
				btnState(false, false, true, false, true, false, false, true);
				computeGSPOnHouseModel();
			}*/

			/*if(_OtherRequest.forCredit(modelReqType) && _OtherRequest.isTotalPaymentsZero(old_entity_id, old_proj_id, old_unit_id, old_seq_no) == false){ 
				lblNDCreditAmt.setEnabled(true);
				txtNDCreditAmt.setEnabled(true);
			}*/

			grpNewEdit.setSelectedButton(arg0);
			txtReqCli.setEditable(true);
			txtRemarks.setEditable(true);
			btnState(false, false, false, true, true, false, false, false);
		}

		if (actionCommand.equals("Save")) {//SAVING OF THE REQUEST
			//String req_no = "";

			if (txtReqNo.getText().isEmpty() || txtReqNo.getText().equals("")) {
				req_no = _OtherRequest.getRequestNo();
			} else if (txtReqNo.getText().isEmpty() == false /*&& txtReqStatus.getText().equals("ACTIVE")*/) {
				req_no = txtReqNo.getText();
			}

			if (toSave()) {
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to save request?",
						actionCommand, JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					new Thread(new Runnable() {

						@Override
						public void run() {
							glass.start("Saving Request...");
							//System.out.printf("Display Array Length: %s%n", _OtherRequest.setFinalVariables(getRequestData()).length);

							/*for(int x =0; x<modelReqType.getRowCount(); x++){
								String request_id = (String) modelReqType.getValueAt(x, 1);
							}*/

							Boolean existing = _OtherRequest.saveRtRequestHeader(req_no,
									_OtherRequest.setFinalVariables(getRequestData()));

							//XXX 2016-08-19 COMMENTED BY JOHN LESTER FATALLO
							if (_OtherRequest.forCredit(modelReqType)
									&& _OtherRequest.isTotalPaymentsZero(old_entity_id, old_proj_id, old_unit_id,
											old_seq_no) == false) {
								pnlOtherReq_CreditPayment cp = new pnlOtherReq_CreditPayment(FncGlobal.homeMDI,
										"Credit Payment");
								cp.setCreditdetails(req_no);
								cp.displayCreditDocuments(req_no);
								cp.setLocationRelativeTo(null);
								cp.setVisible(true);
							}

							if (_OtherRequest.affectSched(modelReqType)) {
								pnlOtherReq_PreviewSchedule ps = new pnlOtherReq_PreviewSchedule(FncGlobal.homeMDI,
										"Change Schedule", _OtherRequest.setFinalVariables(getRequestData()), true,
										req_no);
								ps.setLocationRelativeTo(null);
								ps.setVisible(true);
								ps.setModalityType(ModalityType.MODELESS);
								//ps.setModalityType(Dialog.ModalityType.MODELESS);
							}

							if (existing) {
								JOptionPane.showMessageDialog(OtherRequest.this, String.format(
										"Request Updated\nRequest No: %s\nCLIENT REQUEST WILL TAKE EFFECT UPON POSTING/APPROVAL",
										req_no), "Save", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(OtherRequest.this, String.format(
										"Request Saved\nRequest No: %s\nCLIENT REQUEST WILL TAKE EFFECT UPON POSTING/APPROVAL",
										req_no), "Save", JOptionPane.INFORMATION_MESSAGE);
							}

							glass.stop();
							clearOtherRequest();
						}
					}).start();
				}
			}
		}

		if (actionCommand.equals("Clear")) {//CLEARS THE COMPONENTS IN THE OLD AND THE NEW DATA
			clearOtherRequest();
			grpNewEdit.clearSelection();
		}

		if (actionCommand.equals("Cancel Request")) {//CANCELATION OF THE REQUEST
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to cancel request?",
					actionCommand, JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				_OtherRequest.cancelRequest(txtReqNo.getText());
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Request succesfully cancelled",
						actionCommand, JOptionPane.INFORMATION_MESSAGE);
				clearOtherRequest();

			}
		}

		if (actionCommand.equals("Approve")) {//APPROVING OF THE REQUEST
			JOptionPane.showMessageDialog(getContentPane(), "Please input password to post request", "Post",
					JOptionPane.INFORMATION_MESSAGE);

			dlg_CR_PW_Entry pw = new dlg_CR_PW_Entry(FncGlobal.homeMDI, "Password");
			pw.setLocationRelativeTo(null);
			pw.setVisible(true);

			String pw_entered = pw.getPassword();

			if (pw_entered.equals(FncGlobal.connectionPassword)) {
				if (_OtherRequest.affectSched(modelReqType)) {

					new Thread(new Runnable() {
						@Override
						public void run() {
							glass.start("Posting Request");
							_OtherRequest.postRequest(txtReqNo.getText());
							Object[] data = displayNewData2(txtReqNo.getText());

							String new_entity_id = (String) data[4];
							String new_proj_id = (String) data[13];
							String new_unit_id = (String) data[15];
							String new_seq_no = (String) data[36];

							/*if(_OtherRequest.isOR(new_entity_id, new_proj_id, new_unit_id, Integer.valueOf(new_seq_no))){
								createCommissionSched(txtReqNo.getText());
							}*/

							glass.stop();
							JOptionPane.showMessageDialog(OtherRequest.this.getTopLevelAncestor(),
									"Request Successfully posted", "Post", JOptionPane.INFORMATION_MESSAGE);
							clearOtherRequest();
						}
					}).start();

				} else {
					if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to post request?",
							actionCommand, JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								glass.start("Posting Request");
								_OtherRequest.postRequest(txtReqNo.getText());

								Object[] data = displayNewData2(txtReqNo.getText());

								String new_entity_id = (String) data[4];
								String new_proj_id = (String) data[13];
								String new_unit_id = (String) data[15];
								String new_seq_no = (String) data[36];

								/*if(_OtherRequest.isOR(new_entity_id, new_proj_id, new_unit_id, Integer.valueOf(new_seq_no))){
									createCommissionSched(txtReqNo.getText());
								}*/

								glass.stop();
								JOptionPane.showMessageDialog(OtherRequest.this.getTopLevelAncestor(),
										"Request Successfully posted", "Post", JOptionPane.INFORMATION_MESSAGE);
								clearOtherRequest();
							}
						}).start();
					}
				}
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Invalid password", "Post",
						JOptionPane.WARNING_MESSAGE);
			}

			//XXX put prompt here for posting of pending request before posting current request

		}

		if (actionCommand.equals("Preview Request")) {//PREVIEW OF THE REPORT FOR THE CLIENT REQUEST
			String request_no = txtReqNo.getText();
			Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("request_no", request_no);
			mapParameters.put("prepared_by",
					"" + UserInfo.FirstName + " " + UserInfo.MiddleName + " " + UserInfo.LastName + "");
			mapParameters.put("connection", FncGlobal.connection);
			mapParameters.put("from_card", false);

			FncReport.generateReport("/Reports/rptClientRequest.jasper", "Preview Client request", mapParameters);

			if (_OtherRequest.affectSched(modelReqType)) {
				pnlOtherReq_PreviewSchedule ps = new pnlOtherReq_PreviewSchedule(FncGlobal.homeMDI, "Preview Schedule",
						_OtherRequest.setFinalVariables(getRequestData()), false, txtReqNo.getText());
				ps.setLocationRelativeTo(null);
				ps.setVisible(true);
			}
		}

		if (actionCommand.equals("Select Request")) {
			addRequestToTable();
		}
	}

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menuReq.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}

		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menuReq.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}