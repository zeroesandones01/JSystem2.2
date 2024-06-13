package Projects.SalesandMarketing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Projects.BiddingandAwarding._PaymentScheme;
import components._ButtonGroup;
import components._JInternalFrame;
import components._JXTextField;

/**
 * @author Alvin Gonzales
 *
 */
public class PaymentScheme extends _JInternalFrame implements _GUI, ActionListener, AncestorListener {

	private static final long serialVersionUID = -4658997705416710920L;

	static String title = "Payment Scheme";
	Dimension frameSize = new Dimension(800, 420);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;

	private JPanel pnlNorthCenter;
	private JPanel pnlNorthEast;


	private JLabel lblSchemeName;
	private _JLookup lookupSchema;
	private _JXTextField txtSchemeName;

	private JLabel lblBuyerType;
	private _JLookup lookupBuyerType;
	private _JXTextField txtBuyerTypeName;

	private JLabel lblProject;
	private _JXTextField txtProject;

	private JLabel lblPhase;
	private _JXTextField txtPhase;

	private JLabel lblModel;
	private _JXTextField txtModel;

	private JCheckBox chkSellingPriceIfApplicable;

	private JPanel pnlFrom;
	private JLabel lblSPFrom;
	private _JDateChooser dateSPFrom;

	private JPanel pnlTo;
	private JLabel lblSPTo;
	private _JDateChooser dateSPTo;

	private JPanel pnlDiscRate;
	private JLabel lblDiscRate;
	private _JXFormattedTextField txtSPDiscRate;
	private JLabel lblDiscRatePercent;

	private JPanel pnlIntRate;
	private JLabel lblSPIntRate;
	private _JXFormattedTextField txtSPIntRate;
	private JLabel lblSPIntRatePercent;

	private JPanel pnlCenter;

	private JPanel pnlSchemeDetail;

	private JPanel pnlReservation;
	private JLabel lblReservation;
	private JPanel pnlReservationCenter;

	private JPanel pnlTRAmount;
	private JLabel lblTRAmount;
	private _JXFormattedTextField txtTRAmount;

	private JPanel pnlORAmount;
	private JLabel lblORAmount;
	private _JXFormattedTextField txtORAmount;

	private JPanel pnlTotalAmount;
	private JLabel lblTotalAmount;
	private _JXFormattedTextField txtDaysInterval;
	private JLabel lblDaysInterval;
	private _JXFormattedTextField txtTotalAmount;

	private JPanel pnlDaysInterval;

	private JPanel pnlDownpayment;
	private JLabel lblDownpayment;

	private JPanel pnlDownpaymentCenter;

	private JPanel pnlDPAMount;
	private JLabel lblDPAmount;
	private _JXFormattedTextField txtDPAmount;

	private JPanel pnlDPRate;
	private JLabel lblDPRate;
	private _JXFormattedTextField txtDPRate;

	private JPanel pnlDPTerms;
	private JLabel lblDPTerms;
	private _JXFormattedTextField txtDPTerms;

	private JPanel pnlDPIntervalTerms;
	private JLabel lblDPIntervalTerms;
	private _JXFormattedTextField txtDPIntervalTerms;

	private JPanel pnlMonthlyAmortization;
	private JLabel lblMonthlyAmortization;

	private JPanel pnlMonthlyAmortizationCenter;

	private JPanel pnlMAAMount;
	private JLabel lblMAAmount;
	private _JXFormattedTextField txtMAAmount;

	private JPanel pnlMARate;
	private JLabel lblMARate;
	private _JXFormattedTextField txtMARate;

	private JPanel pnlMATerms;
	private JLabel lblMATerms;
	private _JXFormattedTextField txtMATerms;

	private JPanel pnlMACompFactor;
	private JLabel lblMACompFactor;
	private _JXFormattedTextField txtMACompFactor;

	private JPanel pnlCommissionDetails;

	private JPanel pnlDefaultInHouse;
	private JLabel lblDefaultInHouse;

	private JPanel pnlDefaultInHouseCenter;

	private JPanel pnlDefaultInHouseRA;
	private JPanel pnlDefaultInHouseRALookup;
	private JLabel lblIHFRegularAccount;
	private _JLookup lookupIHFRegularAccount;
	private _JXTextField txtIHFRegularAccount;

	private JPanel pnlDefaultInHouseSA;
	private JPanel pnlDefaultInHouseSALookup;
	private JLabel lblIHFSpecialAccount;
	private _JLookup lookupIHFSpecialAccount;
	private _JXTextField txtIHFSpecialAccount;

	private JPanel pnlDefaultBrokers;
	private JLabel lblDefaultBrokers;

	private JPanel pnlDefaultBrokersCenter;

	private JPanel pnlDefaultBrokersRA;
	private JPanel pnlDefaultBrokersRALookup;
	private JLabel lblBrokersRegularAccount;
	private _JLookup lookupBrokersRegularAccount;
	private _JXTextField txtBrokersRegularAccount;

	private JPanel pnlDefaultBrokersSA;
	private JPanel pnlDefaultBrokersSALookup;
	private JLabel lblBrokersSpecialAccount;
	private _JLookup lookupBrokersSpecialAccount;
	private _JXTextField txtBrokersSpecialAccount;

	private JPanel pnlSouth;
	private JPanel pnlNavigation;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnCancel;
	private _ButtonGroup grpNewEdit = new _ButtonGroup();

	public PaymentScheme() {
		super(title, true, true, true, true);
		initGUI();
	}

	public PaymentScheme(String title) {
		super(title);
		initGUI();
	}

	public PaymentScheme(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setMinimumSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 0));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Main Details"));
				pnlNorth.setPreferredSize(new Dimension(788, 170));
				{
					pnlNorthWest = new JPanel(new GridLayout(5, 1, 3, 3));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					{
						lblSchemeName = new JLabel("*Scheme");
						pnlNorthWest.add(lblSchemeName);
					}
					{
						lblBuyerType = new JLabel("*Buyer Type");
						pnlNorthWest.add(lblBuyerType);
					}
					{
						lblProject = new JLabel("*Project");
						pnlNorthWest.add(lblProject);
					}
					{
						lblPhase = new JLabel("*Phase");
						pnlNorthWest.add(lblPhase);
					}
					{
						lblModel = new JLabel("*Model");
						pnlNorthWest.add(lblModel);
					}
				}
				{
					pnlNorthCenter = new JPanel(new BorderLayout(3, 0));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					{
						JPanel pnlNorthCenterCenter = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlNorthCenter.add(pnlNorthCenterCenter, BorderLayout.CENTER);
						{
							JPanel pnlScheme = new JPanel(new BorderLayout(3, 3));
							pnlNorthCenterCenter.add(pnlScheme);
							{
								lookupSchema = new _JLookup(null, "Payment Scheme");
								pnlScheme.add(lookupSchema, BorderLayout.WEST);
								lookupSchema.setPrompt("ID");
								lookupSchema.setReturnColumn(0);
								lookupSchema.setLookupSQL(_PaymentScheme.SQL_SCHEME());
								lookupSchema.setPreferredSize(new Dimension(45, 25));
								lookupSchema.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){//XXX
											txtSchemeName.setText((String) data[1]);
											lookupBuyerType.setValue((String) data[2]);
											txtBuyerTypeName.setText((String) data[3]);
											txtProject.setText((String) data[4]);
											txtPhase.setText((String) data[5]);
											txtModel.setText((String) data[6]);
											dateSPFrom.setDate((Date) data[7]);
											dateSPTo.setDate((Date) data[8]);
											txtSPIntRate.setValue((BigDecimal) data[9]);
											
											lookupIHFRegularAccount.setValue((String) data[10]);
											txtIHFRegularAccount.setText((String) data[11]);
											txtIHFRegularAccount.setCaretPosition(0);
											lookupIHFSpecialAccount.setValue((String) data[12]);
											txtIHFSpecialAccount.setText((String) data[13]);
											txtIHFSpecialAccount.setCaretPosition(0);
											
											lookupBrokersRegularAccount.setValue((String) data[14]);
											txtBrokersRegularAccount.setText((String) data[15]);
											txtBrokersRegularAccount.setCaretPosition(0);
											lookupBrokersSpecialAccount.setValue((String) data[16]);
											txtBrokersSpecialAccount.setText((String) data[17]);
											txtBrokersSpecialAccount.setCaretPosition(0);

											displaySchemeDetails((String) data[0]);

											btnNavigation(true, true, false, false, false);
										}
									}
								});
							}
							{
								txtSchemeName = new _JXTextField("Scheme Name");
								pnlScheme.add(txtSchemeName, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlBuyerType = new JPanel(new BorderLayout(3, 3));
							pnlNorthCenterCenter.add(pnlBuyerType);
							{
								lookupBuyerType = new _JLookup(null, "Buyer Type");
								pnlBuyerType.add(lookupBuyerType, BorderLayout.WEST);
								lookupBuyerType.setPrompt("ID");
								lookupBuyerType.setReturnColumn(0);
								lookupBuyerType.setLookupSQL(_PaymentScheme.SQL_BUYERTYPE());
								lookupBuyerType.setPreferredSize(new Dimension(45, 25));
								lookupBuyerType.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											txtBuyerTypeName.setText((String) data[1]);

											setRequiredInSchemeDetails((String) data[3]);
										}
									}
								});
							}
							{
								txtBuyerTypeName = new _JXTextField("Buyer Type Name");
								pnlBuyerType.add(txtBuyerTypeName, BorderLayout.CENTER);
								txtBuyerTypeName.setEditable(false);
							}
						}
						{
							txtProject = new _JXTextField("List of Project ID's");
							pnlNorthCenterCenter.add(txtProject);
							txtProject.setEditable(false);
						}
						{
							txtPhase = new _JXTextField("List of Phase");
							pnlNorthCenterCenter.add(txtPhase);
							txtPhase.setEditable(false);
						}
						{
							txtModel = new _JXTextField("List of Model ID's");
							pnlNorthCenterCenter.add(txtModel);
							txtModel.setEditable(false);
						}
					}
					{
						JPanel pnlNorthCenterEast = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlNorthCenter.add(pnlNorthCenterEast, BorderLayout.EAST);
						{
							pnlNorthCenterEast.add(Box.createHorizontalGlue());
						}
						{
							pnlNorthCenterEast.add(Box.createHorizontalGlue());
						}
						{
							JButton btnProject = new JButton(getSearchIcon());
							pnlNorthCenterEast.add(btnProject);
							btnProject.setActionCommand("Project");
							btnProject.addActionListener(this);
						}
						{
							JButton btnPhase = new JButton(getSearchIcon());
							pnlNorthCenterEast.add(btnPhase);
							btnPhase.setActionCommand("Phase");
							btnPhase.addActionListener(this);
						}
						{
							JButton btnModel = new JButton(getSearchIcon());
							pnlNorthCenterEast.add(btnModel);
							btnModel.setActionCommand("Model");
							btnModel.addActionListener(this);
						}
					}
				}
				{
					pnlNorthEast = new JPanel(new GridLayout(5, 1, 3, 3));
					pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
					pnlNorthEast.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
					//pnlNorthEast.setBorder(components.JTBorderFactory.createTitleComponentBorder(new JCheckBox("Selling Price (if applicable)"), pnlNorthEast));
					pnlNorthEast.setPreferredSize(new Dimension(220, 0));
					{
						chkSellingPriceIfApplicable = new JCheckBox("Selling Price (if applicable)");
						//pnlNorthEast.add(chkSellingPriceIfApplicable);
						chkSellingPriceIfApplicable.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent arg0) {
								Boolean isSelected = arg0.getStateChange() == ItemEvent.SELECTED;
								setSellingPriceIfApplicable(isSelected);
							}
						});
					}
					{
						pnlFrom = new JPanel(new BorderLayout(5, 0));
						pnlNorthEast.add(pnlFrom);
						{
							lblSPFrom = new JLabel("*From");
							pnlFrom.add(lblSPFrom, BorderLayout.WEST);
							lblSPFrom.setPreferredSize(new Dimension(70, 0));
						}
						{
							dateSPFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							pnlFrom.add(dateSPFrom, BorderLayout.CENTER);
						}
					}
					{
						pnlTo = new JPanel(new BorderLayout(5, 0));
						pnlNorthEast.add(pnlTo);
						{
							lblSPTo = new JLabel("*To");
							pnlTo.add(lblSPTo, BorderLayout.WEST);
							lblSPTo.setPreferredSize(new Dimension(70, 0));
						}
						{
							dateSPTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							pnlTo.add(dateSPTo, BorderLayout.CENTER);
						}
					}
					{
						pnlDiscRate = new JPanel(new BorderLayout(5, 0));
						//pnlNorthEast.add(pnlDiscRate);
						{
							lblDiscRate = new JLabel("Disc. Rate");
							pnlDiscRate.add(lblDiscRate, BorderLayout.WEST);
							lblDiscRate.setPreferredSize(new Dimension(70, 23));
						}
						{
							txtSPDiscRate = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
							pnlDiscRate.add(txtSPDiscRate, BorderLayout.CENTER);
							txtSPDiscRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						}
						{
							lblDiscRatePercent = new JLabel("%", JLabel.CENTER);
							pnlDiscRate.add(lblDiscRatePercent, BorderLayout.EAST);
							lblDiscRatePercent.setPreferredSize(new Dimension(23, 0));
						}
					}
					{
						pnlIntRate = new JPanel(new BorderLayout(5, 0));
						pnlNorthEast.add(pnlIntRate);
						{
							lblSPIntRate = new JLabel();
							pnlIntRate.add(lblSPIntRate, BorderLayout.WEST);
							lblSPIntRate.setText("Int. Rate");
							lblSPIntRate.setPreferredSize(new Dimension(70, 23));
						}
						{
							txtSPIntRate = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
							pnlIntRate.add(txtSPIntRate, BorderLayout.CENTER);
							txtSPIntRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						}
						{
							lblSPIntRatePercent = new JLabel("%", JLabel.CENTER);
							pnlIntRate.add(lblSPIntRatePercent, BorderLayout.EAST);
							lblSPIntRatePercent.setPreferredSize(new Dimension(23, 0));
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlSchemeDetail = new JPanel(new GridLayout(1, 3, 10, 5));
					pnlCenter.add(pnlSchemeDetail, BorderLayout.NORTH);
					pnlSchemeDetail.setBorder(components.JTBorderFactory.createTitleBorder("Scheme Details"));
					pnlSchemeDetail.setPreferredSize(new Dimension(788, 170));
					{
						pnlReservation = new JPanel(new BorderLayout());
						pnlSchemeDetail.add(pnlReservation);
						//pnlReservation.setBorder(lineBorder);
						{
							lblReservation = new JLabel("Reservation");
							pnlReservation.add(lblReservation, BorderLayout.NORTH);
							lblReservation.setFont(FncLookAndFeel.systemFont_Bold);
						}
						{
							pnlReservationCenter = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlReservation.add(pnlReservationCenter, BorderLayout.CENTER);
							pnlReservationCenter.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
							{
								pnlTRAmount = new JPanel(new BorderLayout(5, 0));
								pnlReservationCenter.add(pnlTRAmount);
								//pnlTRAmount.setBorder(lineBorder);
								{
									lblTRAmount = new JLabel("TR Amount");
									pnlTRAmount.add(lblTRAmount, BorderLayout.WEST);
									lblTRAmount.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtTRAmount = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
									pnlTRAmount.add(txtTRAmount, BorderLayout.CENTER);
									txtTRAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
							}
							{
								pnlORAmount = new JPanel(new BorderLayout(5, 0));
								pnlReservationCenter.add(pnlORAmount);
								//pnlORAmount.setBorder(lineBorder);
								{
									lblORAmount = new JLabel("OR Amount");
									pnlORAmount.add(lblORAmount, BorderLayout.WEST);
									lblORAmount.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtORAmount = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
									pnlORAmount.add(txtORAmount, BorderLayout.CENTER);
									txtORAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
							}
							{
								pnlDaysInterval = new JPanel(new BorderLayout(5, 0));
								pnlReservationCenter.add(pnlDaysInterval);
								//pnlDaysInterval.setBorder(lineBorder);
								{
									lblDaysInterval = new JLabel("Days Interval");
									pnlDaysInterval.add(lblDaysInterval, BorderLayout.WEST);
									lblDaysInterval.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtDaysInterval = new _JXFormattedTextField(_JXFormattedTextField.CENTER);
									pnlDaysInterval.add(txtDaysInterval, BorderLayout.CENTER);
									txtDaysInterval.setFormatterFactory(_JXFormattedTextField.INTEGER);
								}
							}
							{
								pnlTotalAmount = new JPanel(new BorderLayout(5, 0));
								pnlReservationCenter.add(pnlTotalAmount);
								//pnlTotalAmount.setBorder(lineBorder);
								{
									lblTotalAmount = new JLabel("Total Amount");
									//pnlTotalAmount.add(lblTotalAmount, BorderLayout.WEST);
									lblTotalAmount.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtTotalAmount = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
									//pnlTotalAmount.add(txtTotalAmount, BorderLayout.CENTER);
									txtTotalAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
							}
						}
					}
					{
						pnlDownpayment = new JPanel(new BorderLayout());
						pnlSchemeDetail.add(pnlDownpayment);
						{
							lblDownpayment = new JLabel("Downpayment");
							pnlDownpayment.add(lblDownpayment, BorderLayout.NORTH);
							lblDownpayment.setFont(FncLookAndFeel.systemFont_Bold);
						}
						{
							pnlDownpaymentCenter = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlDownpayment.add(pnlDownpaymentCenter, BorderLayout.CENTER);
							pnlDownpaymentCenter.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
							{
								pnlDPRate = new JPanel(new BorderLayout(5, 0));
								pnlDownpaymentCenter.add(pnlDPRate);
								{
									lblDPRate = new JLabel("Rate");
									pnlDPRate.add(lblDPRate, BorderLayout.WEST);
									lblDPRate.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtDPRate = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
									pnlDPRate.add(txtDPRate, BorderLayout.CENTER);
									txtDPRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
							}
							{
								pnlDPTerms = new JPanel(new BorderLayout(5, 0));
								pnlDownpaymentCenter.add(pnlDPTerms);
								{
									lblDPTerms = new JLabel("Terms");
									pnlDPTerms.add(lblDPTerms, BorderLayout.WEST);
									lblDPTerms.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtDPTerms = new _JXFormattedTextField(_JXFormattedTextField.CENTER);
									pnlDPTerms.add(txtDPTerms, BorderLayout.CENTER);
									txtDPTerms.setFormatterFactory(_JXFormattedTextField.INTEGER);
								}
							}
							{
								pnlDPIntervalTerms = new JPanel(new BorderLayout(5, 0));
								pnlDownpaymentCenter.add(pnlDPIntervalTerms);
								{
									lblDPIntervalTerms = new JLabel("Int. Terms (from OR)");
									//pnlDPIntervalTerms.add(lblDPIntervalTerms, BorderLayout.WEST);
									lblDPIntervalTerms.setToolTipText("Interval Terms (from OR)");
									lblDPIntervalTerms.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtDPIntervalTerms = new _JXFormattedTextField(_JXFormattedTextField.CENTER);
									//pnlDPIntervalTerms.add(txtDPIntervalTerms, BorderLayout.CENTER);
									txtDPIntervalTerms.setFormatterFactory(_JXFormattedTextField.INTEGER);
								}
							}
							{
								pnlDPAMount = new JPanel(new BorderLayout(5, 0));
								pnlDownpaymentCenter.add(pnlDPAMount);
								{
									lblDPAmount = new JLabel("Amount");
									//pnlDPAMount.add(lblDPAmount, BorderLayout.WEST);
									lblDPAmount.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtDPAmount = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
									//pnlDPAMount.add(txtDPAmount, BorderLayout.CENTER);
									txtDPAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
							}
						}
					}
					{
						pnlMonthlyAmortization = new JPanel(new BorderLayout());
						pnlSchemeDetail.add(pnlMonthlyAmortization);
						{
							lblMonthlyAmortization = new JLabel("Monthly Amortization");
							pnlMonthlyAmortization.add(lblMonthlyAmortization, BorderLayout.NORTH);
							lblMonthlyAmortization.setFont(FncLookAndFeel.systemFont_Bold);
						}
						{
							pnlMonthlyAmortizationCenter = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlMonthlyAmortization.add(pnlMonthlyAmortizationCenter, BorderLayout.CENTER);
							pnlMonthlyAmortizationCenter.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
							{
								pnlMARate = new JPanel(new BorderLayout(5, 0));
								pnlMonthlyAmortizationCenter.add(pnlMARate);
								{
									lblMARate = new JLabel("Rate");
									pnlMARate.add(lblMARate, BorderLayout.WEST);
									lblMARate.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtMARate = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
									pnlMARate.add(txtMARate, BorderLayout.CENTER);
									txtMARate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
							}
							{
								pnlMATerms = new JPanel(new BorderLayout(5, 0));
								pnlMonthlyAmortizationCenter.add(pnlMATerms);
								{
									lblMATerms = new JLabel("Terms");
									pnlMATerms.add(lblMATerms, BorderLayout.WEST);
									lblMATerms.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtMATerms = new _JXFormattedTextField(_JXFormattedTextField.CENTER);
									pnlMATerms.add(txtMATerms, BorderLayout.CENTER);
									txtMATerms.setFormatterFactory(_JXFormattedTextField.INTEGER);
								}
							}
							{
								pnlMACompFactor = new JPanel(new BorderLayout(5, 0));
								pnlMonthlyAmortizationCenter.add(pnlMACompFactor);
								{
									lblMACompFactor = new JLabel("Comp. Factor");
									//pnlMACompFactor.add(lblMACompFactor, BorderLayout.WEST);
									lblMACompFactor.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtMACompFactor = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
									//pnlMACompFactor.add(txtMACompFactor, BorderLayout.CENTER);
									txtMACompFactor.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
							}
							{
								pnlMAAMount = new JPanel(new BorderLayout(5, 0));
								pnlMonthlyAmortizationCenter.add(pnlMAAMount);
								{
									lblMAAmount = new JLabel("Amount");
									//pnlMAAMount.add(lblMAAmount, BorderLayout.WEST);
									lblMAAmount.setPreferredSize(new Dimension(130, 35));
								}
								{
									txtMAAmount = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
									//pnlMAAMount.add(txtMAAmount, BorderLayout.CENTER);
									txtMAAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								}
							}
						}
					}
				}
				{
					pnlCommissionDetails = new JPanel(new GridLayout(1, 2, 5, 5));
					//pnlCenter.add(pnlCommissionDetails, BorderLayout.CENTER);
					pnlCommissionDetails.setBorder(components.JTBorderFactory.createTitleBorder("Commission Details"));
					{
						pnlDefaultInHouse = new JPanel(new BorderLayout(5, 0));
						pnlCommissionDetails.add(pnlDefaultInHouse);
						{
							lblDefaultInHouse = new JLabel("Default In-House Category Comm. Scheme");
							pnlDefaultInHouse.add(lblDefaultInHouse, BorderLayout.NORTH);
							lblDefaultInHouse.setFont(FncLookAndFeel.systemFont_Bold);
						}
						{
							pnlDefaultInHouseCenter = new JPanel(new GridLayout(2, 1, 3, 10));
							pnlDefaultInHouse.add(pnlDefaultInHouseCenter, BorderLayout.CENTER);
							pnlDefaultInHouseCenter.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
							{
								pnlDefaultInHouseRA = new JPanel(new BorderLayout(3, 3));
								pnlDefaultInHouseCenter.add(pnlDefaultInHouseRA);
								{
									pnlDefaultInHouseRALookup = new JPanel(new BorderLayout(3, 3));
									pnlDefaultInHouseRA.add(pnlDefaultInHouseRALookup, BorderLayout.WEST);
									pnlDefaultInHouseRALookup.setPreferredSize(new Dimension(150, 64));
									{
										lblIHFRegularAccount = new JLabel("Regular Acct.");
										pnlDefaultInHouseRALookup.add(lblIHFRegularAccount, BorderLayout.WEST);
										lblIHFRegularAccount.setPreferredSize(new Dimension(90, 41));
									}
									{
										lookupIHFRegularAccount = new _JLookup(null, "IHF Regular Account");
										pnlDefaultInHouseRALookup.add(lookupIHFRegularAccount, BorderLayout.CENTER);
										lookupIHFRegularAccount.setReturnColumn(0);
										lookupIHFRegularAccount.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){
													txtIHFRegularAccount.setText((String) data[1]);
													txtIHFRegularAccount.setCaretPosition(0);
												}
											}
										});
									}
								}
								{
									txtIHFRegularAccount = new _JXTextField();
									pnlDefaultInHouseRA.add(txtIHFRegularAccount, BorderLayout.CENTER);
								}
							}
							{
								pnlDefaultInHouseSA = new JPanel(new BorderLayout(3, 3));
								pnlDefaultInHouseCenter.add(pnlDefaultInHouseSA);
								{
									pnlDefaultInHouseSALookup = new JPanel(new BorderLayout(3, 3));
									pnlDefaultInHouseSA.add(pnlDefaultInHouseSALookup, BorderLayout.WEST);
									pnlDefaultInHouseSALookup.setPreferredSize(new Dimension(150, 64));
									{
										lblIHFSpecialAccount = new JLabel("Special Acct.");
										pnlDefaultInHouseSALookup.add(lblIHFSpecialAccount, BorderLayout.WEST);
										lblIHFSpecialAccount.setPreferredSize(new Dimension(90, 0));
									}
									{
										lookupIHFSpecialAccount = new _JLookup(null, "IHF Special Account");
										pnlDefaultInHouseSALookup.add(lookupIHFSpecialAccount, BorderLayout.CENTER);
										lookupIHFSpecialAccount.setReturnColumn(0);
										lookupIHFSpecialAccount.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){
													txtIHFSpecialAccount.setText((String) data[1]);
													txtIHFSpecialAccount.setCaretPosition(0);
												}
											}
										});
									}
								}
								{
									txtIHFSpecialAccount = new _JXTextField();
									pnlDefaultInHouseSA.add(txtIHFSpecialAccount, BorderLayout.CENTER);
								}
							}
						}
					}
					{
						pnlDefaultBrokers = new JPanel(new BorderLayout(5, 0));
						pnlCommissionDetails.add(pnlDefaultBrokers);
						{
							lblDefaultBrokers = new JLabel("Default Brokers Category Comm. Scheme");
							pnlDefaultBrokers.add(lblDefaultBrokers, BorderLayout.NORTH);
							lblDefaultBrokers.setFont(FncLookAndFeel.systemFont_Bold);
						}
						{
							pnlDefaultBrokersCenter = new JPanel(new GridLayout(2, 1, 3, 10));
							pnlDefaultBrokers.add(pnlDefaultBrokersCenter, BorderLayout.CENTER);
							pnlDefaultBrokersCenter.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
							{
								pnlDefaultBrokersRA = new JPanel(new BorderLayout(3, 3));
								pnlDefaultBrokersCenter.add(pnlDefaultBrokersRA);
								{
									pnlDefaultBrokersRALookup = new JPanel(new BorderLayout(3, 3));
									pnlDefaultBrokersRA.add(pnlDefaultBrokersRALookup, BorderLayout.WEST);
									pnlDefaultBrokersRALookup.setPreferredSize(new Dimension(150, 64));
									{
										lblBrokersRegularAccount = new JLabel("Regular Acct.");
										pnlDefaultBrokersRALookup.add(lblBrokersRegularAccount, BorderLayout.WEST);
										lblBrokersRegularAccount.setPreferredSize(new Dimension(90, 41));
									}
									{
										lookupBrokersRegularAccount = new _JLookup(null, "Brokers Regular Account");
										pnlDefaultBrokersRALookup.add(lookupBrokersRegularAccount, BorderLayout.CENTER);
										lookupBrokersRegularAccount.setReturnColumn(0);
										lookupBrokersRegularAccount.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){
													txtBrokersRegularAccount.setText((String) data[1]);
													txtBrokersRegularAccount.setCaretPosition(0);
												}
											}
										});
									}
								}
								{
									txtBrokersRegularAccount = new _JXTextField();
									pnlDefaultBrokersRA.add(txtBrokersRegularAccount, BorderLayout.CENTER);
								}
							}
							{
								pnlDefaultBrokersSA = new JPanel(new BorderLayout(3, 3));
								pnlDefaultBrokersCenter.add(pnlDefaultBrokersSA);
								{
									pnlDefaultBrokersSALookup = new JPanel(new BorderLayout(3, 3));
									pnlDefaultBrokersSA.add(pnlDefaultBrokersSALookup, BorderLayout.WEST);
									pnlDefaultBrokersSALookup.setPreferredSize(new Dimension(150, 64));
									{
										lblBrokersSpecialAccount = new JLabel("Special Acct.");
										pnlDefaultBrokersSALookup.add(lblBrokersSpecialAccount, BorderLayout.WEST);
										lblBrokersSpecialAccount.setPreferredSize(new Dimension(90, 0));
									}
									{
										lookupBrokersSpecialAccount = new _JLookup(null, "Brokers Special Account");
										pnlDefaultBrokersSALookup.add(lookupBrokersSpecialAccount, BorderLayout.CENTER);
										lookupBrokersSpecialAccount.setReturnColumn(0);
										lookupBrokersSpecialAccount.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup)event.getSource()).getDataSet();
												if(data != null){
													txtBrokersSpecialAccount.setText((String) data[1]);
													txtBrokersSpecialAccount.setCaretPosition(0);
												}
											}
										});
									}
								}
								{
									txtBrokersSpecialAccount = new _JXTextField();
									pnlDefaultBrokersSA.add(txtBrokersSpecialAccount, BorderLayout.CENTER);
								}
							}
						}
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout());
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(788, 31));
				{
					pnlNavigation = new JPanel(new GridLayout(1, 5, 3, 3));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setPreferredSize(new Dimension(537, 31));
					{
						btnNew = new JButton("New");
						pnlNavigation.add(btnNew);
						btnNew.setActionCommand("New");
						btnNew.addActionListener(this);
						grpNewEdit.add(btnNew);
					}
					{
						btnEdit = new JButton("Edit");
						pnlNavigation.add(btnEdit);
						btnEdit.setActionCommand("Edit");
						btnEdit.addActionListener(this);
						grpNewEdit.add(btnEdit);
					}
					{
						btnDelete = new JButton("Delete");
						//pnlNavigation.add(btnDelete);
						btnDelete.addActionListener(this);
					}
					{
						btnSave = new JButton("Save");
						pnlNavigation.add(btnSave);
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			}
		}

		setSellingPriceIfApplicable(false);
		btnNavigation(true, false, false, false, false);
		cancelPaymentScheme();
	}

	private void btnNavigation(Boolean sNew, Boolean sEdit, Boolean sDelete, Boolean sSave, Boolean sCancel) {
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnDelete.setEnabled(sDelete);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void clearPaymentScheme() {
		lookupSchema.setValue(null);
		txtSchemeName.setText("");
		lookupBuyerType.setValue(null);
		txtBuyerTypeName.setText("");
		txtProject.setText("");
		txtPhase.setText("");
		txtModel.setText("");

		chkSellingPriceIfApplicable.setSelected(false);
		dateSPFrom.setDate(null);
		dateSPTo.setDate(null);
		txtSPDiscRate.setValue(null);
		txtSPIntRate.setValue(null);

		txtTRAmount.setValue(null);
		txtORAmount.setValue(null);
		txtTotalAmount.setValue(null);
		txtDaysInterval.setValue(null);

		txtDPAmount.setValue(null);
		txtDPRate.setValue(null);
		txtDPTerms.setValue(null);
		txtDPIntervalTerms.setValue(null);

		txtMAAmount.setValue(null);
		txtMARate.setValue(null);
		txtMATerms.setValue(null);
		txtMACompFactor.setValue(null);

		lookupIHFRegularAccount.setValue(null);
		txtIHFRegularAccount.setText("");
		lookupIHFSpecialAccount.setValue(null);
		txtIHFSpecialAccount.setText("");
		lookupBrokersRegularAccount.setValue(null);
		txtBrokersRegularAccount.setText("");
		lookupBrokersSpecialAccount.setValue(null);
		txtBrokersSpecialAccount.setText("");
	}

	private void newPaymentScheme() {
		clearPaymentScheme();

		setComponentsEnabled(pnlNorth, true);
		setComponentsEnabled(pnlSchemeDetail, false);
		setComponentsEnabled(pnlCommissionDetails, false);

		lookupSchema.setEnabled(true);
		lookupSchema.setEditable(false);
		lookupSchema.setValue(_PaymentScheme.getNewPaymentSchemeID());
		txtSchemeName.setEditable(true);
		dateSPFrom.setEnabled(true);
		dateSPTo.setEnabled(true);
		txtSPDiscRate.setEnabled(true);
		txtSPIntRate.setEnabled(true);

		Calendar cal = Calendar.getInstance();

		cal.set(2013, 0, 23);
		dateSPFrom.setDate(cal.getTime());

		cal.set(2020, 11, 31);
		dateSPTo.setDate(cal.getTime());
		
		lookupIHFRegularAccount.setLookupSQL(null);
		lookupIHFSpecialAccount.setLookupSQL(null);
		lookupBrokersRegularAccount.setLookupSQL(null);
		lookupBrokersSpecialAccount.setLookupSQL(null);

		btnNavigation(false, false, false, true, true);
	}

	private void editPaymentScheme() {
		setComponentsEnabled(pnlNorth, true);
		setComponentsEnabled(pnlSchemeDetail, true);
		setComponentsEnabled(pnlCommissionDetails, false);

		lookupSchema.setEnabled(true);
		lookupSchema.setEditable(false);
		txtSchemeName.setEditable(true);
		dateSPFrom.setEnabled(true);
		dateSPTo.setEnabled(true);
		txtSPDiscRate.setEnabled(true);
		txtSPIntRate.setEnabled(true);

		setRequiredInSchemeDetails(_PaymentScheme.getBuyerTypeGroupID(lookupBuyerType.getValue()));

		btnNavigation(false, false, false, true, true);
	}

	private void savePaymentScheme() {//XXX
		if(toSave()){
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				String selection = grpNewEdit.getActionCommand();
				Boolean isSaved = false;
				if (selection.equals("New")) {
					isSaved = _PaymentScheme.savePaymentScheme(lookupSchema.getValue(), txtSchemeName.getText(), lookupBuyerType.getValue(), txtProject.getText(), txtModel.getText(), dateSPFrom.getDate(), dateSPTo.getDate(),
							(BigDecimal) txtSPIntRate.getValued(), lookupIHFRegularAccount.getValue(), lookupIHFSpecialAccount.getValue(), lookupBrokersRegularAccount.getValue(), lookupBrokersSpecialAccount.getValue(), txtPhase.getText(),
							(BigDecimal) txtTRAmount.getValued(), (BigDecimal) txtORAmount.getValued(), txtDaysInterval.getIntegerValue(), (BigDecimal) txtDPRate.getValued(), txtDPTerms.getIntegerValue(),
							(BigDecimal) txtMARate.getValued(), txtMATerms.getIntegerValue(), (BigDecimal) txtMACompFactor.getValued());
				}
				if (selection.equals("Edit")) {
					isSaved = _PaymentScheme.updatePaymentScheme(lookupSchema.getValue(), txtSchemeName.getText(), lookupBuyerType.getValue(), txtProject.getText(), txtModel.getText(), dateSPFrom.getDate(), dateSPTo.getDate(),
							(BigDecimal) txtSPIntRate.getValued(), lookupIHFRegularAccount.getValue(), lookupIHFSpecialAccount.getValue(), lookupBrokersRegularAccount.getValue(), lookupBrokersSpecialAccount.getValue(), txtPhase.getText(),
							(BigDecimal) txtTRAmount.getValued(), (BigDecimal) txtORAmount.getValued(), txtDaysInterval.getIntegerValue(), (BigDecimal) txtDPRate.getValued(), txtDPTerms.getIntegerValue(),
							(BigDecimal) txtMARate.getValued(), txtMATerms.getIntegerValue(), (BigDecimal) txtMACompFactor.getValued());
				}

				if(isSaved){
					if (selection.equals("New")) {
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "New Payment Scheme has been saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
					}
					if (selection.equals("Edit")) {
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Payment Scheme has been edited.", "Save", JOptionPane.INFORMATION_MESSAGE);
					}

					setComponentsEnabled(pnlNorth, false);
					setComponentsEnabled(pnlSchemeDetail, false);
					setComponentsEnabled(pnlCommissionDetails, false);

					lblSchemeName.setEnabled(true);
					lookupSchema.setEditable(true);
					lookupSchema.setEnabled(true);
					txtSchemeName.setEnabled(true);
					txtSchemeName.setEditable(false);

					btnNavigation(true, true, false, false, false);
				}else{

				}
			}
		}
	}

	private void cancelPaymentScheme() {
		clearPaymentScheme();

		setComponentsEnabled(pnlNorth, false);
		setComponentsEnabled(pnlSchemeDetail, false);
		setComponentsEnabled(pnlCommissionDetails, false);

		lblSchemeName.setEnabled(true);
		lookupSchema.setEditable(true);
		lookupSchema.setEnabled(true);
		txtSchemeName.setEnabled(true);
		txtSchemeName.setEditable(false);
		
		lookupIHFRegularAccount.setLookupSQL(null);
		lookupIHFSpecialAccount.setLookupSQL(null);
		lookupBrokersRegularAccount.setLookupSQL(null);
		lookupBrokersSpecialAccount.setLookupSQL(null);

		setRequiredInSchemeDetails("00");

		btnNavigation(true, false, false, false, false);
	}

	private Icon getSearchIcon() {
		Image img = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/search1.png")).getImage();
		img = img.getScaledInstance(19, 19, Image.SCALE_SMOOTH);

		return new ImageIcon(img);
	}

	private void setSellingPriceIfApplicable(Boolean enabled) {
		dateSPFrom.setEnabled(enabled);
		dateSPTo.setEnabled(enabled);
		txtSPDiscRate.setEnabled(enabled);
		txtSPIntRate.setEnabled(enabled);
	}

	private void setRequiredInSchemeDetails(String group_type_id) {//XXX setting of required fields
		if(group_type_id.equals("02")){ //In-House
			lblTRAmount.setText("*TR Amount");
			lblORAmount.setText("*OR Amount");
			lblDaysInterval.setText("*Days Interval");
			lblDPRate.setText("*Rate");
			lblDPTerms.setText("*Terms");
			lblMARate.setText("*Rate");
			lblMATerms.setText("*Terms");
			//txtDPRate.setValue(null);
			setComponentsEnabled(pnlSchemeDetail, true);
			setComponentsEnabled(pnlCommissionDetails, true);
		}
		if(group_type_id.equals("03")){ //Cash
			lblTRAmount.setText("*TR Amount");
			lblORAmount.setText("*OR Amount");
			lblDaysInterval.setText("*Days Interval");
			lblDPRate.setText("*Rate");
			lblDPTerms.setText("*Terms");
			lblMARate.setText("Rate");
			lblMATerms.setText("Terms");
			txtDPRate.setValue(new BigDecimal(100));
			setComponentsEnabled(pnlSchemeDetail, true);
			setComponentsEnabled(pnlCommissionDetails, true);
		}
		if(group_type_id.equals("04")){ //Pag-IBIG
			lblTRAmount.setText("*TR Amount");
			lblORAmount.setText("*OR Amount");
			lblDaysInterval.setText("*Days Interval");
			lblDPRate.setText("Rate");
			lblDPTerms.setText("*Terms");
			lblMARate.setText("Rate");
			lblMATerms.setText("*Terms");
			txtDPRate.setValue(null);
			setComponentsEnabled(pnlSchemeDetail, true);
			setComponentsEnabled(pnlCommissionDetails, true);
		}

		if(group_type_id.equals("00")){ //NULL
			lblTRAmount.setText("TR Amount");
			lblORAmount.setText("OR Amount");
			lblDaysInterval.setText("Days Interval");
			lblDPRate.setText("Rate");
			lblDPTerms.setText("Terms");
			lblMARate.setText("Rate");
			lblMATerms.setText("Terms");
			txtDPRate.setValue(null);
			setComponentsEnabled(pnlSchemeDetail, false);
			setComponentsEnabled(pnlCommissionDetails, false);
		}
		
		lookupIHFRegularAccount.setLookupSQL(_PaymentScheme.getIHFRegularAccount(lookupBuyerType.getValue()));
		lookupIHFSpecialAccount.setLookupSQL(_PaymentScheme.getIHFSpecialAccount(lookupBuyerType.getValue()));
		lookupBrokersRegularAccount.setLookupSQL(_PaymentScheme.getBrokerRegularAccount(lookupBuyerType.getValue()));
		lookupBrokersSpecialAccount.setLookupSQL(_PaymentScheme.getBrokerSpecialAccount(lookupBuyerType.getValue()));
	}

	@Override
	public void actionPerformed(ActionEvent evt) {//XXX ACTION
		String action = evt.getActionCommand();

		if(action.equals("Project")){
			lookupProject();
		}
		if(action.equals("Phase")){
			lookupPhase();
		}
		if(action.equals("Model")){
			lookupModel();
		}

		if(action.equals("New")){
			grpNewEdit.setSelectedButton(evt);
			newPaymentScheme();
		}

		if(action.equals("Edit")){
			grpNewEdit.setSelectedButton(evt);
			editPaymentScheme();
		}

		if(action.equals("Save")){
			savePaymentScheme();
		}

		if(action.equals("Cancel")){
			grpNewEdit.clearSelection();
			cancelPaymentScheme();
		}
	}

	private void lookupProject() {
		String selectedProjectID = txtProject.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Project", _PaymentScheme.getProject(selectedProjectID), true);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[][] data = dlg.getResult();
		try {
			if(data.length > 0){
				ArrayList<String> listTypeID = new ArrayList<String>();
				for (int x = 0; x < data.length; x++) {
					String type_id = (String) data[x][0];
					listTypeID.add(type_id);
				}
				txtProject.setText(listTypeID.toString());
				txtPhase.setText("");
			}else{
				txtProject.setText("");
				txtPhase.setText("");
			}
		} catch (NullPointerException e) { }
	}

	private void lookupPhase() {
		String selectedProjectID = txtProject.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");

		if(selectedProjectID.equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project first.", "Phase", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String selectedPhase = txtPhase.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Phase", _PaymentScheme.getPhase(selectedProjectID, selectedPhase), true);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[][] data = dlg.getResult();
		try {
			if(data.length > 0){
				ArrayList<String> listTypeID = new ArrayList<String>();
				for (int x = 0; x < data.length; x++) {
					//Integer type_id = (String) data[x][0];
					listTypeID.add((String) data[x][0]);
				}
				txtPhase.setText(listTypeID.toString());
			}else{
				txtPhase.setText("");
			}
		} catch (NullPointerException e) { }
	}

	private void lookupModel() {
		String selectedModelID = txtModel.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Model", _PaymentScheme.getModel(selectedModelID), true);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[][] data = dlg.getResult();
		try {
			if(data.length > 0){
				ArrayList<String> listTypeID = new ArrayList<String>();
				for (int x = 0; x < data.length; x++) {
					String type_id = (String) data[x][0];
					listTypeID.add(type_id);
				}
				txtModel.setText(listTypeID.toString());
			}else{
				txtModel.setText("");
			}
		} catch (NullPointerException e) { }
	}

	private void displaySchemeDetails(String pmt_scheme_id) {
		Object[] details = _PaymentScheme.getSchemeDetails(pmt_scheme_id);

		txtTRAmount.setValue((BigDecimal) details[0]);
		txtORAmount.setValue((BigDecimal) details[1]);
		txtDaysInterval.setValue((Integer) details[2]);

		txtDPRate.setValue((BigDecimal) details[3]);
		txtDPTerms.setValue((Integer) details[4]);

		txtMARate.setValue((BigDecimal) details[5]);
		txtMATerms.setValue((Integer) details[6]);
	}

	private Boolean toSave() {
		BigDecimal zero = new BigDecimal(0);

		if (txtSchemeName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Scheme Name", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupBuyerType.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Buyer Type", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtProject.getText().equals("")) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtPhase.getText().equals("")) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Phase", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (txtModel.getText().equals("")) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Model", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (dateSPFrom.getDate() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Date From", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (dateSPTo.getDate() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Date To", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		BigDecimal tr_amount = (BigDecimal) txtTRAmount.getValued();
		if (lblTRAmount.getText().contains("*") && (tr_amount == null || tr_amount.compareTo(zero) <= 0)) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input TR Amount", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		BigDecimal or_amount = (BigDecimal) txtORAmount.getValued();
		if (lblORAmount.getText().contains("*") && (or_amount == null || or_amount.compareTo(zero) <= 0)) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input OR Amount", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		Integer days_interval = (Integer) txtDaysInterval.getIntegerValue();
		if (lblDaysInterval.getText().contains("*") && days_interval <= 0) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Days Interval", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		BigDecimal dp_rate = (BigDecimal) txtDPRate.getValued();
		if (lblDPRate.getText().contains("*") && (dp_rate == null || dp_rate.compareTo(zero) <= 0)) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input DP Rate", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		Integer dp_terms = (Integer) txtDPTerms.getIntegerValue();
		if (lblDPTerms.getText().contains("*") && (dp_terms == null || dp_terms <= 0)) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input DP Terms", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		BigDecimal ma_rate = (BigDecimal) txtMARate.getValued();
		if (lblMARate.getText().contains("*") && (ma_rate == null || ma_rate.compareTo(zero) <= 0)) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input MA Rate", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		Integer ma_terms = (Integer) txtMATerms.getIntegerValue();
		if (lblMATerms.getText().contains("*") && (ma_terms == null || ma_terms <= 0)) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input MA Terms", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	@Override
	public void ancestorAdded(AncestorEvent arg0) {
		lookupSchema.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent arg0) { }

	@Override
	public void ancestorRemoved(AncestorEvent arg0) { }

}
