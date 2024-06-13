package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JXTextField;

public class pnlOtherRequest_OldData extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7554694292467545869L;

	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlWest;
	private JLabel lblClient;
	private JLabel lblCivilStatus;
	private JLabel lblCurrentStatus;
	private JLabel lblReservationDate;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblLotArea;
	private JLabel lblHouseModel;
	private JLabel lblAgent;
	private JLabel lblBuyerType;
	private JLabel lblPaymentScheme;
	private JLabel lblSellingPrice;
	private JLabel lblDiscount;
	private JLabel lblVAT;
	private JLabel lblNSP;
	private JLabel lblDownpayment;
	private JLabel lblLoanAvailed;
	private JLabel lblDPTerm;
	private JLabel lblMaTerm;
	private JLabel lblStartDate;
	private JLabel lblIntRate;
	private JLabel lblDateCancelled;

	private JPanel pnlCenter;

	private JPanel pnlClient;
	private _JLookup lookupClientID;
	private _JXTextField txtClientName;

	private JPanel pnlCivilStatus;
	private JComboBox cmbCivilStatus;

	private JPanel pnlCurrentStatus;
	private _JXTextField txtCurrentStatusID;
	private _JXTextField txtCurrentStatusName;

	private JPanel pnlReservationDate;
	private _JDateChooser dateReservation;
	private JLabel lblRequestDate;
	private _JDateChooser dateRequest;

	private JPanel pnlProject;
	private _JXTextField txtProjectID;
	private _JXTextField txtProjectName;

	private JPanel pnlUnit;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitName;

	private JPanel pnlLotArea;
	//private _JXFormattedTextField txtLotArea;
	private JPanel pnlLotAreaWest;
	private _JXTextField txtLotArea;
	private JLabel lblSeqNo;
	//private _JXFormattedTextField txtSeqNo;
	private _JXTextField txtSeqNo;
	private JPanel pnlLotAreaCenter;

	private JPanel pnlModel;
	private _JXTextField txtModelID;
	private _JXTextField txtModelName;

	private JPanel pnlAgent;
	private _JXTextField txtAgentID;
	private _JXTextField txtAgentName;

	private JPanel pnlBuyerType;
	private _JXTextField txtBuyerTypeID;
	private _JXTextField txtBuyerTypeName;

	private JPanel pnlPaymentScheme;
	private _JXTextField txtPaymentSchemeID;
	private _JXTextField txtPaymentSchemeName;

	private _JXFormattedTextField txtSellingPrice;

	private JPanel pnlDiscount;
	private _JXFormattedTextField txtDiscount;
	private JLabel lblDiscountRate;
	private _JXFormattedTextField txtDiscountRate;

	private JPanel pnlVAT;
	private _JXFormattedTextField txtVAT;
	private JLabel lblVATRate;
	private _JXFormattedTextField txtVATRate;

	private _JXFormattedTextField txtNSP;

	private JPanel pnlDownpayment;
	private _JXFormattedTextField txtDownpayment;
	private JLabel lblDownpaymentRate;
	private _JXFormattedTextField txtDownpaymentRate;

	private JPanel pnlDPDueDate;
	//private _JXFormattedTextField txtDPTerm;
	private _JXTextField txtDPTerm;
	private JLabel lblDPDueDate;
	private _JXTextField txtDPDueDate;
	private _JDateChooser dteDPDueDate; //USE ME
	private _JDateChooser dteMADueDate; //USE ME
	private JPanel pnlLoanAvailed;
	private _JXFormattedTextField txtLoanAvailed;
	private JLabel lblLoanAvailedRate;
	private _JXFormattedTextField txtLoanAvailedRate;

	private JPanel pnlMADueDate;
	//private _JXFormattedTextField txtMATerm;
	private _JXTextField txtMATerm;
	private JLabel lblMADueDate;
	private _JXTextField txtMADueDate;

	private JPanel pnlStartDate;
	private _JDateChooser dteStartDate;
	private JLabel lblEndDate;
	private _JDateChooser dteEndDate;

	private JPanel pnlIntRate;
	private _JXFormattedTextField txtIntRate;
	private JLabel lblFireAmt;
	private _JXFormattedTextField txtFireAmt;

	private _JDateChooser dteCancelled;
	private OtherRequest2 or;

	public pnlOtherRequest_OldData(OtherRequest2 or) {
		this.or=or;
		initGUI();
	}

	public pnlOtherRequest_OldData(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlOtherRequest_OldData(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlOtherRequest_OldData(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout(3, 3));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(500, 500));
		{
			pnlWest = new JPanel(new GridLayout(20, 1, 3, 3));
			this.add(pnlWest, BorderLayout.WEST);
			//pnlWest.setBorder(LINE_BORDER);
			//pnlWest.setPreferredSize(new Dimension(142, 500));
			{
				lblClient = new JLabel("Client");
				pnlWest.add(lblClient);
			}
			{
				lblCurrentStatus = new JLabel("Current Status");
				pnlWest.add(lblCurrentStatus);
			}
			{
				lblRequestDate = new JLabel("Request Date"/*, JLabel.TRAILING*/);
				pnlWest.add(lblRequestDate/*, BorderLayout.CENTER*/);
			}
			/*{
				lblDateCancelled = new JLabel("Date Cancelled");
				pnlWest.add(lblDateCancelled);
			}*/
			{
				lblProject = new JLabel("Project");
				pnlWest.add(lblProject);
			}
			{
				lblUnit = new JLabel("Unit");
				pnlWest.add(lblUnit);
			}
			{
				lblLotArea = new JLabel("Lot Area");
				pnlWest.add(lblLotArea);
			}
			{
				lblHouseModel = new JLabel("House Model");
				pnlWest.add(lblHouseModel);
			}
			{
				lblAgent = new JLabel("Selling Agent");
				pnlWest.add(lblAgent);
			}
			{
				lblBuyerType = new JLabel("Buyer Type");
				pnlWest.add(lblBuyerType);
			}
			{
				lblPaymentScheme = new JLabel("Payment Scheme");
				pnlWest.add(lblPaymentScheme);

			}
			{
				lblSellingPrice = new JLabel("Selling Price");
				pnlWest.add(lblSellingPrice);
			}
			{
				lblDiscount = new JLabel("Discount");
				pnlWest.add(lblDiscount);
			}
			{
				lblVAT = new JLabel("VAT");
				pnlWest.add(lblVAT);
			}
			{
				lblNSP = new JLabel("Net Selling Price");
				pnlWest.add(lblNSP);
			}
			{
				lblDownpayment = new JLabel("Downpayment");
				pnlWest.add(lblDownpayment);
			}
			{
				lblDPTerm = new JLabel("DP Term");
				pnlWest.add(lblDPTerm);
			}
			{
				lblLoanAvailed = new JLabel("Loan Availed");
				pnlWest.add(lblLoanAvailed);
			}
			{
				lblMaTerm = new JLabel("MA Term");
				pnlWest.add(lblMaTerm);
			}
			{
				lblStartDate = new JLabel("Start Date");
				pnlWest.add(lblStartDate);
			}
			{
				lblIntRate = new JLabel("Interest Rate");
				pnlWest.add(lblIntRate);
			}
		}
		{
			pnlCenter = new JPanel(new GridLayout(20, 1, 3, 3));
			this.add(pnlCenter, BorderLayout.CENTER);
			{
				pnlClient = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlClient);
				{
					lookupClientID = new _JLookup(null, "Select Client", 0);
					pnlClient.add(lookupClientID, BorderLayout.WEST);
					lookupClientID.setPreferredSize(new Dimension(100, 0));
					lookupClientID.setPrompt("Entity ID");
					lookupClientID.setLookupSQL(_OtherRequest2.sqlEntityLookUp());
					lookupClientID.setFilterName(true);
					lookupClientID.addLookupListener(new LookupListener() {

						public void lookupPerformed(LookupEvent event) {
							Object [] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								FncSystem.out("Display entity lookup", _OtherRequest2.sqlEntityLookUp());
								String entity_id = (String) data[0];
								String entity_name = (String) data[1];
								String pbl_id = (String) data[2];
								Integer seq_no = (Integer) data[3];
								String proj_id = (String) data[4];
								String proj_name = (String) data[5];
								String unit_id = (String) data[6];
								String unit_desc = (String) data[7];
								String byrstatus_id = (String) data[8];
								String byrstatus_desc = (String) data[9];

								txtClientName.setText(entity_name);
								txtCurrentStatusID.setText(byrstatus_id);
								txtCurrentStatusName.setText(byrstatus_desc);
								txtUnitID.setText(unit_id);
								txtUnitName.setText(unit_desc);
								txtProjectID.setText(proj_id);
								txtProjectName.setText(proj_name);
								txtSeqNo.setText(seq_no.toString());

								displayOldData(entity_id, pbl_id, seq_no, proj_id);
								Date reser_date = _OtherRequest2.getReserDate(entity_id, unit_id, 1);
								//dateReservation.setDate(reser_date);

								lookupClientID.setEditable(false);
								or.setReqEnabled(true);
								//or.btnState(sNew, sSearch, sPrevSched, sCrdtPymnt, sSave, sClear, sCancelReq, sApprove, sPrintReq);
								//or.btnState(false, true, true, true, true, true, false, false, false);
								//XXX FIX ME or.btnState(false, false, true, false, true, true, false, false, false);
								or.btnState(false, false, false, true, true, true, false, false, false, false);

							}
						}
					});
				}
				{
					txtClientName = new _JXTextField("Client Name");
					pnlClient.add(txtClientName, BorderLayout.CENTER);
				}
			}
			{
				pnlCurrentStatus = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlCurrentStatus);
				{
					txtCurrentStatusID = new _JXTextField("ID");
					pnlCurrentStatus.add(txtCurrentStatusID, BorderLayout.WEST);
					txtCurrentStatusID.setHorizontalAlignment(JXTextField.CENTER);
					txtCurrentStatusID.setPreferredSize(new Dimension(50, 0));
				}
				{
					txtCurrentStatusName = new _JXTextField("Status Name");
					pnlCurrentStatus.add(txtCurrentStatusName, BorderLayout.CENTER);
				}
			}
			{
				pnlReservationDate = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlReservationDate);
				{
					dateRequest = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlReservationDate.add(dateRequest, BorderLayout.WEST);
					dateRequest.getCalendarButton().setVisible(false);
					dateRequest.setPreferredSize(new Dimension(100, 0));
				}
				{
					lblReservationDate = new JLabel("Reservation/Cancellation Date", JLabel.TRAILING);
					pnlReservationDate.add(lblReservationDate);
				}
				{
					dateReservation = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlReservationDate.add(dateReservation, BorderLayout.EAST);
					dateReservation.getCalendarButton().setVisible(false);
					dateReservation.setPreferredSize(new Dimension(100, 0));
				}
			}
			/*{
				dteCancelled = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
				pnlCenter.add(dteCancelled, BorderLayout.WEST);
				dteCancelled.getCalendarButton().setVisible(false);
				dteCancelled.setPreferredSize(new Dimension(100, 0));
			}*/
			{
				pnlProject = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlProject);
				{
					txtProjectID = new _JXTextField("ID");
					pnlProject.add(txtProjectID, BorderLayout.WEST);
					txtProjectID.setHorizontalAlignment(JXTextField.CENTER);
					txtProjectID.setPreferredSize(new Dimension(50, 0));
				}
				{
					txtProjectName = new _JXTextField("Project Name");
					pnlProject.add(txtProjectName, BorderLayout.CENTER);
				}
			}
			{
				pnlUnit = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlUnit);
				{
					txtUnitID = new _JXTextField("ID");
					pnlUnit.add(txtUnitID, BorderLayout.WEST);
					txtUnitID.setHorizontalAlignment(JXTextField.CENTER);
					txtUnitID.setPreferredSize(new Dimension(100, 0));
				}
				{
					txtUnitName = new _JXTextField("Unit Name");
					pnlUnit.add(txtUnitName, BorderLayout.CENTER);
				}
			}
			{
				pnlLotArea = new JPanel(new GridLayout(1, 2, 3, 3));
				pnlCenter.add(pnlLotArea);
				{
					pnlLotAreaWest = new JPanel(new BorderLayout(5, 5));
					pnlLotArea.add(pnlLotAreaWest, BorderLayout.WEST);
					{
						txtLotArea = new _JXTextField("Lot Area");
						pnlLotAreaWest.add(txtLotArea, BorderLayout.WEST);
						txtLotArea.setHorizontalAlignment(JXTextField.CENTER);
						txtLotArea.setPreferredSize(new Dimension(70, 0));	
					}
					{
						lblSeqNo = new JLabel("Seq. No", JLabel.TRAILING);
						pnlLotAreaWest.add(lblSeqNo, BorderLayout.CENTER);
					}
					{
						txtSeqNo = new _JXTextField("Seq. No");
						pnlLotAreaWest.add(txtSeqNo, BorderLayout.EAST);
						txtSeqNo.setHorizontalAlignment(JXTextField.CENTER);
						txtSeqNo.setPreferredSize(new Dimension(70, 0));
					}
				}
				{
					pnlLotAreaCenter = new JPanel(new BorderLayout(5, 5));
					pnlLotArea.add(pnlLotAreaCenter, BorderLayout.EAST);
					{
						lblCivilStatus = new JLabel("Civil Status", JLabel.TRAILING);
						pnlLotAreaCenter.add(lblCivilStatus, BorderLayout.WEST);
					}
					{
						cmbCivilStatus = new JComboBox(_JInternalFrame.CIVIL_STATUS().values().toArray());
						pnlLotAreaCenter.add(cmbCivilStatus, BorderLayout.CENTER);
						cmbCivilStatus.setPreferredSize(new Dimension(100, 0));
						cmbCivilStatus.setEnabled(false);
					}
				}
			}
			{
				pnlModel = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlModel);
				{
					txtModelID = new _JXTextField("ID");
					pnlModel.add(txtModelID, BorderLayout.WEST);
					txtModelID.setHorizontalAlignment(JXTextField.CENTER);
					txtModelID.setPreferredSize(new Dimension(50, 0));
				}
				{
					txtModelName = new _JXTextField("Model Name");
					pnlModel.add(txtModelName, BorderLayout.CENTER);
				}
			}
			{
				pnlAgent = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlAgent);
				{
					txtAgentID = new _JXTextField("ID");
					pnlAgent.add(txtAgentID, BorderLayout.WEST);
					txtAgentID.setHorizontalAlignment(JXTextField.CENTER);
					txtAgentID.setPreferredSize(new Dimension(100, 0));
				}
				{
					txtAgentName = new _JXTextField("Agent Name");
					pnlAgent.add(txtAgentName, BorderLayout.CENTER);
				}
			}
			{
				pnlBuyerType = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlBuyerType);
				{
					txtBuyerTypeID = new _JXTextField("ID");
					pnlBuyerType.add(txtBuyerTypeID, BorderLayout.WEST);
					txtBuyerTypeID.setHorizontalAlignment(JXTextField.CENTER);
					txtBuyerTypeID.setPreferredSize(new Dimension(50, 0));
				}
				{
					txtBuyerTypeName = new _JXTextField("Buyer Type Name");
					pnlBuyerType.add(txtBuyerTypeName, BorderLayout.CENTER);
				}
			}
			{
				pnlPaymentScheme = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlPaymentScheme);
				{
					txtPaymentSchemeID = new _JXTextField("ID");
					pnlPaymentScheme.add(txtPaymentSchemeID, BorderLayout.WEST);
					txtPaymentSchemeID.setHorizontalAlignment(JXTextField.CENTER);
					txtPaymentSchemeID.setPreferredSize(new Dimension(50, 0));
				}
				{
					txtPaymentSchemeName = new _JXTextField("Payment Scheme Name");
					pnlPaymentScheme.add(txtPaymentSchemeName, BorderLayout.CENTER);
				}
			}
			{
				txtSellingPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
				pnlCenter.add(txtSellingPrice);
				txtSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
			}
			{
				pnlDiscount = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlDiscount);
				{
					txtDiscount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlDiscount.add(txtDiscount, BorderLayout.WEST);
					txtDiscount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtDiscount.setPreferredSize(new Dimension(100, 0));
				}
				{
					lblDiscountRate = new JLabel("Disc. Rate", JLabel.TRAILING);
					pnlDiscount.add(lblDiscountRate, BorderLayout.CENTER);
				}
				{
					txtDiscountRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlDiscount.add(txtDiscountRate, BorderLayout.EAST);
					txtDiscountRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtDiscountRate.setPreferredSize(new Dimension(100, 0));
				}
			}
			{
				pnlVAT = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlVAT);
				{
					txtVAT = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlVAT.add(txtVAT, BorderLayout.WEST);
					txtVAT.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtVAT.setPreferredSize(new Dimension(100, 0));
				}
				{
					lblVATRate = new JLabel("VAT Rate", JLabel.TRAILING);
					pnlVAT.add(lblVATRate, BorderLayout.CENTER);
				}
				{
					txtVATRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlVAT.add(txtVATRate, BorderLayout.EAST);
					txtVATRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtVATRate.setPreferredSize(new Dimension(100, 0));
				}
			}
			{
				txtNSP = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
				pnlCenter.add(txtNSP);
				txtNSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
			}
			{
				pnlDownpayment = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlDownpayment);
				{
					txtDownpayment = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlDownpayment.add(txtDownpayment, BorderLayout.WEST);
					txtDownpayment.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtDownpayment.setPreferredSize(new Dimension(100, 0));
				}
				{
					lblDownpaymentRate = new JLabel("DP Rate", JLabel.TRAILING);
					pnlDownpayment.add(lblDownpaymentRate, BorderLayout.CENTER);
				}
				{
					txtDownpaymentRate = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlDownpayment.add(txtDownpaymentRate, BorderLayout.EAST);
					txtDownpaymentRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtDownpaymentRate.setPreferredSize(new Dimension(100, 0));
				}
			}
			{
				pnlDPDueDate = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlDPDueDate);
				/*{
					//txtDPTerm = new _JXTextField();
					txtDPTerm = new _JXFormattedTextField(JXTextField.RIGHT);
					pnlDPDueDate.add(txtDPTerm, BorderLayout.WEST);
					txtDPTerm.setPreferredSize(new Dimension(100, 0));
				}*/
				{
					txtDPTerm = new _JXTextField();
					pnlDPDueDate.add(txtDPTerm, BorderLayout.WEST);
					txtDPTerm.setPreferredSize(new Dimension(100, 0));
					txtDPTerm.setHorizontalAlignment(JXTextField.CENTER);
				}
				{
					lblDPDueDate = new JLabel("DP Due Date", JLabel.TRAILING);
					pnlDPDueDate.add(lblDPDueDate, BorderLayout.CENTER);
				}
				/*{
					txtDPDueDate = new _JXTextField(); 
					pnlDPDueDate.add(txtDPDueDate, BorderLayout.EAST);
					txtDPDueDate.setPreferredSize(new Dimension(100, 0));
				}*/
				{
					dteDPDueDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlDPDueDate.add(dteDPDueDate, BorderLayout.EAST);
					dteDPDueDate.getCalendarButton().setVisible(false);
					dteDPDueDate.setPreferredSize(new Dimension(100, 0));
				}
			}
			{
				pnlLoanAvailed = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlLoanAvailed);
				{
					txtLoanAvailed = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlLoanAvailed.add(txtLoanAvailed, BorderLayout.WEST);
					txtLoanAvailed.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtLoanAvailed.setPreferredSize(new Dimension(100, 0));
				}
				{
					lblLoanAvailedRate = new JLabel("Loan Availed Rate", JLabel.TRAILING);
					pnlLoanAvailed.add(lblLoanAvailedRate, BorderLayout.CENTER);
				}
				{
					txtLoanAvailedRate = new _JXFormattedTextField(JXFormattedTextField.CENTER);
					pnlLoanAvailed.add(txtLoanAvailedRate, BorderLayout.EAST);
					txtLoanAvailedRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtLoanAvailedRate.setPreferredSize(new Dimension(100, 0));
				}
			}
			{
				pnlMADueDate = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlMADueDate);
				/*{
					txtMATerm = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlMADueDate.add(txtMATerm, BorderLayout.WEST);
					txtMATerm.setPreferredSize(new Dimension(100, 0));
				}*/
				{
					txtMATerm = new _JXTextField();
					pnlMADueDate.add(txtMATerm, BorderLayout.WEST);
					txtMATerm.setPreferredSize(new Dimension(100, 0));
					txtMATerm.setHorizontalAlignment(JXTextField.CENTER);
				}
				{
					lblMADueDate = new JLabel("MA Due Date", JLabel.TRAILING);
					pnlMADueDate.add(lblMADueDate, BorderLayout.CENTER);
				}
				/*{
					txtMADueDate = new _JXTextField();
					pnlMADueDate.add(txtMADueDate, BorderLayout.EAST);
					txtMADueDate.setPreferredSize(new Dimension(100, 0));
				}*/
				{
					dteMADueDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlMADueDate.add(dteMADueDate, BorderLayout.EAST);
					dteMADueDate.getCalendarButton().setVisible(false);
					dteMADueDate.setPreferredSize(new Dimension(100, 0));
				}
			}
			{
				pnlStartDate = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlStartDate);
				{
					dteStartDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlStartDate.add(dteStartDate, BorderLayout.WEST);
					dteStartDate.getCalendarButton().setVisible(false);
					dteStartDate.setPreferredSize(new Dimension(100, 0));
				}
				{
					lblEndDate = new JLabel("End Date", JLabel.TRAILING);
					pnlStartDate.add(lblEndDate, BorderLayout.CENTER);
				}
				{
					dteEndDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlStartDate.add(dteEndDate, BorderLayout.EAST);
					dteEndDate.getCalendarButton().setVisible(false);
					dteEndDate.setPreferredSize(new Dimension(100, 0));
				}
			}
			{
				pnlIntRate = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlIntRate);
				{
					txtIntRate = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlIntRate.add(txtIntRate, BorderLayout.WEST);
					txtIntRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtIntRate.setPreferredSize(new Dimension(100, 0));
				}
				{
					lblFireAmt= new JLabel("Fire Amount", JLabel.TRAILING);
					pnlIntRate.add(lblFireAmt, BorderLayout.CENTER);
				}
				{
					txtFireAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
					pnlIntRate.add(txtFireAmt, BorderLayout.EAST);
					txtFireAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					txtFireAmt.setPreferredSize(new Dimension(100, 0));
				}
			}
		}
	}//END OF INIT GUI
	//XXX FIX ME
	public void displayOldData(String entity_id, String pbl_id, Integer seq_no, String proj_id){
		//DISPLAYS THE OLD DATA BASED ON THE SELECTED ENTITY ID
		//XXX ADD DATE CANCELLED HERE IF NEED TO DISPLAY IN OLD DATA AND TRY TO MAKE THIS A VIEW TO EASILY ACCESS WHEN TRYING THE PREVIEW

		String sql = "select * from get_client_details('"+entity_id+"', '"+pbl_id+"', '"+proj_id+"', "+seq_no+")";
		FncSystem.out("Display OLD Data", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			cmbCivilStatus.setSelectedItem(db.getResult()[0][2]);
			txtCurrentStatusID.setText((String) db.getResult()[0][3]);
			txtCurrentStatusName.setText((String) db.getResult()[0][4]);
			dateReservation.setDate((Date) db.getResult()[0][5]);
			dateRequest.setDate((Date) db.getResult()[0][6]);
			txtProjectID.setText((String) db.getResult()[0][7]);
			txtProjectName.setText((String) db.getResult()[0][8]);
			txtUnitName.setText((String) db.getResult()[0][10]);
			txtLotArea.setText(db.getResult()[0][12].toString());
			txtSeqNo.setText(db.getResult()[0][11].toString());
			txtModelID.setText((String) db.getResult()[0][13]);
			txtModelName.setText((String) db.getResult()[0][14]);
			txtAgentID.setText((String) db.getResult()[0][15]);
			txtAgentName.setText((String) db.getResult()[0][16]);
			txtBuyerTypeID.setText((String) db.getResult()[0][17]);
			txtBuyerTypeName.setText((String) db.getResult()[0][18]);
			txtPaymentSchemeID.setText((String) db.getResult()[0][19]);
			txtPaymentSchemeName.setText((String) db.getResult()[0][20]);
			txtSellingPrice.setValue(db.getResult()[0][21]);
			txtDiscount.setValue(db.getResult()[0][22]);
			txtDiscountRate.setValue(db.getResult()[0][23]);
			txtVAT.setValue(db.getResult()[0][24]);
			txtVATRate.setValue(db.getResult()[0][25]);
			txtNSP.setValue(db.getResult()[0][26]);
			txtDownpayment.setValue(db.getResult()[0][27]);
			txtDownpaymentRate.setValue(db.getResult()[0][28]);
			txtDPTerm.setText(db.getResult()[0][29].toString());
			dteDPDueDate.setDate((Date) db.getResult()[0][30]);
			txtLoanAvailed.setValue(db.getResult()[0][31]);
			txtLoanAvailedRate.setValue(db.getResult()[0][32]);
			txtMATerm.setText(db.getResult()[0][33].toString());
			dteMADueDate.setDate((Date) db.getResult()[0][34]);
			dteStartDate.setDate((Date) db.getResult()[0][35]);
			dteEndDate.setDate((Date) db.getResult()[0][36]);
			txtIntRate.setValue(db.getResult()[0][37]);
			txtFireAmt.setValue(db.getResult()[0][38]);

		}
	}

	public void clearOldData(){//CLEARS THE FIELDS FOR THE OLD DATA
		or.setComponentsEditable(this, false);
		
		lookupClientID.setValue(null);
		txtClientName.setText("");
		txtCurrentStatusID.setText("");
		cmbCivilStatus.setSelectedItem(null);
		txtCurrentStatusName.setText("");
		dateReservation.setDate(null);
		dateRequest.setDate(null);
		txtProjectID.setText("");
		txtProjectName.setText("");
		txtUnitID.setText("");
		txtUnitName.setText("");
		txtModelID.setText("");
		txtModelName.setText("");
		txtAgentID.setText("");
		txtAgentName.setText("");
		txtBuyerTypeID.setText("");
		txtBuyerTypeName.setText("");
		txtPaymentSchemeID.setText("");
		txtPaymentSchemeName.setText("");
		txtSellingPrice.setValue(new BigDecimal("0.00"));
		txtDiscount.setValue(new BigDecimal("0.00"));
		txtDiscountRate.setValue(new BigDecimal("0"));
		txtVAT.setValue(new BigDecimal("0.00"));
		txtVATRate.setValue(new BigDecimal("0"));
		txtNSP.setValue(new BigDecimal("0.00"));
		txtDownpayment.setValue(new BigDecimal("0.00"));
		txtDownpaymentRate.setValue(new BigDecimal("0"));
		txtLoanAvailed.setValue(new BigDecimal("0.00"));
		txtLoanAvailedRate.setValue(new BigDecimal("0"));
		//txtDPTerm.setValue(null);
		txtDPTerm.setText("");
		//txtDPDueDate.setText("");
		dteDPDueDate.setDate(null);
		//txtMATerm.setValue(null);
		txtMATerm.setText("");
		//txtMADueDate.setText("");
		dteMADueDate.setDate(null);
		txtLotArea.setText("");
		txtSeqNo.setText("");
		dteStartDate.setDate(null);
		dteEndDate.setDate(null);
		txtIntRate.setValue(new BigDecimal("0.0"));
		txtFireAmt.setValue(new BigDecimal("0.00"));
	}

	public void newOldData(){

		clearOldData();
		lookupClientID.setEditable(true);
		lookupClientID.setValue(null);
		txtClientName.setText("");
		txtCurrentStatusID.setText("");
		txtCurrentStatusName.setText("");
		dateReservation.setDate(null);
		dateRequest.setDate(null);
	}

	public void search(String old_entity_id, String old_entity_name, String old_unit_id, Date req_date){
		lookupClientID.setValue(old_entity_id);
		txtUnitID.setText(old_unit_id);
		txtClientName.setText(old_entity_name);
		dateReservation.setDate(_OtherRequest2.getReserDate(old_entity_id, old_unit_id, 1));
		dateRequest.setDate(req_date);
	}

	public String getOtherLot(String unit_id){
		pgSelect db = new pgSelect();
		String sql = "select get_other_merge_lot('"+unit_id+"')";
		db.select(sql);
		String other_lot = (String) db.getResult()[0][0];

		return " " + other_lot;
	}

	public Boolean setReqEnabled(){
		if(lookupClientID.getValue() != null){
			return true;
		}else{
			return false;
		}
	}

	private String getCivilStatus(String entity_id){
		pgSelect db = new pgSelect();
		String sql = "select trim(civil_status_code) from rf_entity where entity_id = '"+entity_id+"'";
		db.select(sql);

		return (String) db.getResult()[0][0];
	}


	public Object[] getDataOld() {//GETS THE DATA 

		return new Object[]{lookupClientID.getValue(), //0
				txtClientName.getText(), //1
				txtBuyerTypeID.getText(), //2
				txtAgentID.getText(), //3
				txtProjectID.getText(), //4
				txtUnitID.getText(), //5
				(BigDecimal) txtSellingPrice.getValued(), //6 
				txtPaymentSchemeID.getText(), //7
				dteDPDueDate.getDate(), //txtDPDueDate.getText(), //8 //
				dteMADueDate.getDate(), //txtMADueDate.getText(), //9
				txtModelID.getText(), //10
				(BigDecimal) txtDiscount.getValued(), //11
				txtMATerm.getText(),//(Integer) txtMATerm.getValued(), //12
				txtDPTerm.getText(), //(Integer) txtDPTerm.getValued(), //13
				(BigDecimal) txtDownpayment.getValued(), //14 
				(BigDecimal) txtLoanAvailed.getValued(), //15
				(BigDecimal) txtDiscountRate.getValued(), //16
				(BigDecimal) txtNSP.getValued(), //17
				(BigDecimal) txtVAT.getValued(), //18
				(BigDecimal) txtVATRate.getValued(), //19
				dteStartDate.getDate(), //20
				dteEndDate.getDate(), //21
				(BigDecimal) txtDownpaymentRate.getValued(), //22
				(BigDecimal) txtLoanAvailedRate.getValued(), //23
				(BigDecimal) txtFireAmt.getValued(), //24
				(BigDecimal) txtIntRate.getValued(), //25
				txtSeqNo.getText(), //26
				txtLotArea.getText()}; //27 

	}
}
