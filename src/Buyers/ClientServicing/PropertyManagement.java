package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import Functions.FncTables;
import components.JTBorderFactory;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import interfaces._GUI;
import tablemodel.modelCARD_Electricity;
import tablemodel.modelCARD_Facilities;
import tablemodel.modelCARD_MonthlySOA;
import tablemodel.modelCARD_SubdivisionDues;
import tablemodel.modelCARD_UCard;
import tablemodel.modelCARD_WaterMeterReading;
import tablemodel.model_hdmf_payments;
import tablemodel.model_hdmf_postInspection_card;
import tablemodel.model_hdmf_schedule;
import tablemodel.model_hdmfinfo_loanfilingstatus;

public class PropertyManagement extends JXPanel implements _GUI {

	private static final long serialVersionUID = 1770006858199175887L;
	private CARD main_card;
	private ClientRequestOldDetails crod;
	static Dimension size = new Dimension(738, 351);

	static Border line = BorderFactory.createLineBorder(Color.GRAY);

	private _JTabbedPane tabPM;
	private _JTabbedPane tabHDMF_hdmfDetails;

	private JXPanel panWater;
	private JXPanel panHDMFUnitInspection;
	private JXPanel panHDMFDetails;

	private JXPanel panHDMFDetails_schedule;
	private JXPanel panHDMFDetails_payments;

	private JScrollPane scrollPostInsp;
	public static JList rowPostInsp;
	private _JTableMain tblPostInsp;
	private static model_hdmf_postInspection_card modelPostInsp;

	private JScrollPane scrollLoanFilingStatus;
	public static JList rowLoanFilingStatus;
	private _JTableMain tblLoanFilingStatus;
	private static model_hdmfinfo_loanfilingstatus modelLoanFilingStatus;

	private JScrollPane scrollHDMFSchedule;
	public static JList rowHDMFSchedule;
	private _JTableMain tblHDMFSchedule;
	private static model_hdmf_schedule modelHDMFSchedule;

	private JScrollPane scrollHDMFPayments;
	public static JList rowHDMFPayments;
	private _JTableMain tblHDMFPayments;
	private static model_hdmf_payments modelHDMFPayments;

	private _JTabbedPane tabWater;
	private JPanel pnlWaterMeterReading;
	public static JCheckBox chkPreviewOtherLot;
	public static JComboBox cmbPreviewLot;
	private JScrollPane scrollWaterMeterReading;
	private static JList rowHeaderWaterMeterReading;
	private static _JTableMain tblWaterMeterReading;
	private static modelCARD_WaterMeterReading modelWaterReading;

	private JScrollPane scrollFacilities;
	private static JList rowHeaderFacilities;
	private static _JTableMain tblFacilities;
	private static modelCARD_Facilities modelFacilities;

	private JPanel pnlUCard;
	public static JCheckBox chkPreviewOtherUCARD;
	private _JScrollPaneMain scrollUCard;
	private static JList rowHeaderUCard;
	private static _JTableMain tblUCard;
	private static modelCARD_UCard modelUCARD;
	private static _JScrollPaneTotal scrollUCARD_Total;
	private static modelCARD_UCard modelUCARD_Total;
	private _JTableTotal tblUCARD_Total;

	private _JScrollPaneMain scrollSubdivisionDues;
	private static JList rowHeaderSubdivisionDues;
	private static _JTableMain tblSubdivisionDues;
	private static modelCARD_SubdivisionDues modelSubdivisionDues;

	private static _JScrollPaneTotal scrollSubdivisionDuesTotal;
	private _JTableTotal tblSubdivisionDuesTotal;
	private static modelCARD_SubdivisionDues modelSubdivisionDuesTotal;

	public static JCheckBox chkPreviewOtherLotSOA;
	private _JScrollPaneMain scrollMonthlySOA;
	private static JList rowHeaderMonthlySOA;
	private static _JTableMain tblMonthlySOA;
	private static modelCARD_MonthlySOA modelMonthlySOA;

	private static _JScrollPaneTotal scrollMonthlySOA_Total;
	private _JTableTotal tblMonthlySOA_Total;
	private static modelCARD_MonthlySOA modelMonthlySOA_Total;

	private _JScrollPaneMain scrollElectricity;
	private static JList rowHeaderElectricity;
	private static _JTableMain tblElectricity;
	private static modelCARD_Electricity modelElectricity;

	private static _JScrollPaneTotal scrollElectricityTotal;
	private _JTableTotal tblElectricityTotal;
	private static modelCARD_Electricity modelElectricityTotal;

	private JLabel lblSriDocStamp;
	private JLabel lblFire;
	private JLabel lblProcessingFee;
	private JLabel lblAppraisalFee;
	private JLabel lblInterimMRI;
	private JLabel lblRetentionFee;
	private JLabel lblFirstMA;
	private JLabel lblRefilingFee;
	private JLabel lblRecapTotal;

	private static _JXFormattedTextField txtSriDocStamp;
	private static _JXFormattedTextField txtFire;
	private static _JXFormattedTextField txtProcessingFee;
	private static _JXFormattedTextField txtAppraisalFee;
	private static _JXFormattedTextField txtInterimMRI;
	private static _JXFormattedTextField txtPctgRetentionFee;
	private static _JXFormattedTextField txtRetentionFee;
	private static _JXFormattedTextField txtFirstMA;
	private static _JXFormattedTextField txtRefilingFee;
	private static _JXFormattedTextField txtRecapTotal;

	private JLabel lblRet;
	private JLabel lblEPEB;
	private JLabel lblETD;
	private JLabel lblCOC;
	private JLabel lblMERL;
	private JLabel lblCOM5;
	private JLabel lblCOM3;
	private JLabel lblROW;
	private JLabel lblRetTotal;

	private static _JXFormattedTextField txtRet;
	private static _JXFormattedTextField txtEPEB;
	private static _JXFormattedTextField txtETD;
	private static _JXFormattedTextField txtCOC;
	private static _JXFormattedTextField txtMERL;
	private static _JXFormattedTextField txtCOM5;
	private static _JXFormattedTextField txtCOM3;
	private static _JXFormattedTextField txtROW;
	private static _JXFormattedTextField txtRetTotal;

	private static _JXFormattedTextField txtPctgEPEB;
	private static _JXFormattedTextField txtPctgETD;
	private static _JXFormattedTextField txtPctgCOC;
	private static _JXFormattedTextField txtPctgMERL;
	private static _JXFormattedTextField txtPctgCOM5;
	private static _JXFormattedTextField txtPctgROW;

	private static _JXFormattedTextField txtPctgRetEPEB;
	private static _JXFormattedTextField txtPctgRetETD;
	private static _JXFormattedTextField txtPctgRetCOC;
	private static _JXFormattedTextField txtPctgRetMERL;
	private static _JXFormattedTextField txtPctgRetCOM5;
	private static _JXFormattedTextField txtPctgRetROW;

	private static _JXFormattedTextField txtRetRet;
	private static _JXFormattedTextField txtRetEPEB;
	private static _JXFormattedTextField txtRetETD;
	private static _JXFormattedTextField txtRetCOC;
	private static _JXFormattedTextField txtRetMERL;
	private static _JXFormattedTextField txtRetCOM5;
	private static _JXFormattedTextField txtRetCOM3;
	private static _JXFormattedTextField txtRetROW;
	private static _JXFormattedTextField txtRetRetTotal;

	private static JSplitPane splitReleasedLoanDetail;

	private JLabel lblLoanable;
	private static JLabel lblLoanableAmount;
	private JLabel lblSummRecapitulation;
	private static JLabel lblSummRecapitulationAmount;
	private JLabel lblSummRetention;
	private static JLabel lblSummRetentionAmount;
	private JLabel lblNetProceeds;
	private static JLabel lblNetProceedsAmount;
	private JLabel lblSummReturnedRetention;
	private static JLabel lblSummReturnedRetentionAmount;
	private JLabel lblTotalNetProceeds;
	private static JLabel lblTotalNetProceedsAmount;
	private static JLabel lblUtilityCARD;

	private Font fontPlainSanSerNine = new Font("SansSerif", Font.PLAIN, 9);
	private Font fontBoldSanSerEleven = new Font("SansSerif", Font.BOLD, 11);
	private Font fontBoldSanSerTens = new Font("SansSerif", Font.BOLD, 10);
	private Font fontPlainSanSerEleven = new Font("SansSerif", Font.PLAIN, 11);
	private static String entity_id;
	private static String proj_id;
	private static String pbl_id;
	private static Integer seq_no;

	public PropertyManagement(CARD card) {
		this.main_card = card;
		initGUI();
	}

	public PropertyManagement(ClientRequestOldDetails crod) {
		this.crod = crod;
		initGUI();
	}

	public PropertyManagement(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public PropertyManagement(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public PropertyManagement(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			this.add(panMain, BorderLayout.CENTER);
			panMain.setBorder(new EmptyBorder(0, 5, 5, 5));
			/*
			 * { JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			 * panMain.add(panCenter, BorderLayout.CENTER); {
			 */
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(5, 5));
				panMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(new EmptyBorder(5, 5, 5, 5));
				{
					JLabel lblPreviewLot = new JLabel("Select Lot to Preview");
					pnlNorth.add(lblPreviewLot, BorderLayout.WEST);
				}
				{
					JPanel pnlPreviewLot = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlPreviewLot, BorderLayout.CENTER);
					{
						cmbPreviewLot = new JComboBox();
						pnlPreviewLot.add(cmbPreviewLot, BorderLayout.WEST);
						cmbPreviewLot.setPreferredSize(new Dimension(200, 30));
						cmbPreviewLot.addItemListener(new ItemListener() {

							@Override
							public void itemStateChanged(ItemEvent e) {
								int selected_index = ((JComboBox) e.getSource()).getSelectedIndex();

								if (selected_index == 0) {
									displayWaterMeterReading(entity_id, proj_id, pbl_id, String.valueOf(seq_no), false);
									displayMonthlySOA(entity_id, proj_id, pbl_id, String.valueOf(seq_no), false);
									displayUCARD(entity_id, proj_id, pbl_id, String.valueOf(seq_no), false);
								}

								if (selected_index == 1) {
									displayWaterMeterReading(entity_id, proj_id, pbl_id, String.valueOf(seq_no), true);
									displayMonthlySOA(entity_id, proj_id, pbl_id, String.valueOf(seq_no), true);
									displayUCARD(entity_id, proj_id, pbl_id, String.valueOf(seq_no), true);
								}

							}
						});
					}
				}
			}
			{
				tabPM = new _JTabbedPane();
				panMain.add(tabPM, BorderLayout.CENTER);
				tabPM.setBorder(new EmptyBorder(5, 5, 5, 5));
				{
					// CreateHDMFDetailsTab();
					// CreateHDMFUnitInspectionTab();
					// CreateReleasedLoanDetailTab();

					// tabWater = new _JTabbedPane();
					// createWaterTab();
					tabWater = new _JTabbedPane();
					tabPM.addTab("      WATER      ", null, tabWater, null);
					{
						JPanel pnlMeterReading = new JPanel(new BorderLayout(3, 3));
						tabWater.addTab("Meter Reading", pnlMeterReading);
						/*
						 * { JPanel pnlMR_North = new JPanel(new BorderLayout(3, 3));
						 * pnlMeterReading.add(pnlMR_North, BorderLayout.NORTH); { chkPreviewOtherLot =
						 * new JCheckBox("Preview Reading Other Lot");
						 * pnlMR_North.add(chkPreviewOtherLot); chkPreviewOtherLot.addItemListener(new
						 * ItemListener() {
						 * 
						 * @Override public void itemStateChanged(ItemEvent e) { Boolean selected =
						 * e.getStateChange() == ItemEvent.SELECTED;
						 * 
						 * if(selected){ displayWaterMeterReading(entity_id, proj_id, pbl_id,
						 * String.valueOf(seq_no), true); }else{ displayWaterMeterReading(entity_id,
						 * proj_id, pbl_id, String.valueOf(seq_no), false); } } }); } }
						 */
						{
							JPanel pnlMRCenter = new JPanel(new BorderLayout(3, 3));
							pnlMeterReading.add(pnlMRCenter, BorderLayout.CENTER);
							{
								scrollWaterMeterReading = new JScrollPane();
								pnlMRCenter.add(scrollWaterMeterReading, BorderLayout.CENTER);
								scrollWaterMeterReading
								.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
								scrollWaterMeterReading
								.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
								scrollWaterMeterReading.setBorder(line);
								{
									{
										modelWaterReading = new modelCARD_WaterMeterReading();
										tblWaterMeterReading = new _JTableMain(modelWaterReading);

										rowHeaderWaterMeterReading = tblWaterMeterReading.getRowHeader();
										scrollWaterMeterReading.setViewportView(tblWaterMeterReading);

									}
									{
										rowHeaderWaterMeterReading = tblWaterMeterReading.getRowHeader();
										rowHeaderWaterMeterReading.setModel(new DefaultListModel());
										scrollWaterMeterReading.setRowHeaderView(rowHeaderWaterMeterReading);
										scrollWaterMeterReading.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
												FncTables.getRowHeader_Header());
									}
								}
							}
						}
					}

				}
				{
					JPanel pnlElectricity = new JPanel(new BorderLayout(3, 3));
					tabPM.addTab("     ELECTRICITY     ", pnlElectricity);
					{
						scrollElectricity = new _JScrollPaneMain();
						pnlElectricity.add(scrollElectricity, BorderLayout.CENTER);
						scrollElectricity.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
						scrollElectricity.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrollElectricity.setBorder(line);
						{
							modelElectricity = new modelCARD_Electricity();
							tblElectricity = new _JTableMain(modelElectricity);
							scrollElectricity.setViewportView(tblElectricity);

							tblElectricity.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if (tblSubdivisionDues.rowAtPoint(e.getPoint()) == -1) {
										tblSubdivisionDuesTotal.clearSelection();
									} else {
										tblSubdivisionDues.setCellSelectionEnabled(true);
									}
								}

								public void mouseReleased(MouseEvent e) {
									if (tblSubdivisionDues.rowAtPoint(e.getPoint()) == -1) {
										tblSubdivisionDuesTotal.clearSelection();
									} else {
										tblSubdivisionDues.setCellSelectionEnabled(true);
									}
								}
							});

							// Process after row add in tables
							modelElectricity.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if (e.getType() == TableModelEvent.INSERT) {
										((DefaultListModel) rowHeaderElectricity.getModel())
										.addElement(modelElectricity.getRowCount());
										scrollElectricity.setRowHeaderView(FncTables
												.getRowHeader_Footer(Integer.toString(modelElectricity.getRowCount())));
									}
									if (e.getType() == TableModelEvent.DELETE) {
										if (modelElectricity.getRowCount() == 0) {
											rowHeaderElectricity.setModel(new DefaultListModel());
											scrollElectricity.setRowHeaderView(FncTables.getRowHeader_Footer(
													Integer.toString(modelElectricity.getRowCount())));
										}
									}
								}
							});

						}
						{
							rowHeaderElectricity = tblElectricity.getRowHeader();
							rowHeaderElectricity.setModel(new DefaultListModel());
							scrollElectricity.setRowHeaderView(rowHeaderElectricity);
							scrollElectricity.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
						}
					}
					{
						scrollElectricityTotal = new _JScrollPaneTotal(scrollElectricity);
						pnlElectricity.add(scrollElectricityTotal, BorderLayout.SOUTH);
						{
							modelElectricityTotal = new modelCARD_Electricity();
							modelElectricityTotal.addRow(
									new Object[] { "Total", null, null, null, null, 0.00, 0.00, 0.00, null, null });

							tblElectricityTotal = new _JTableTotal(modelElectricityTotal, tblElectricity);
							scrollElectricityTotal.setViewportView(tblElectricityTotal);

							tblElectricityTotal.setTotalLabel(0);
						}
					}
				}
				{
					JPanel pnlFacilities = new JPanel(new BorderLayout(3, 3));
					tabPM.addTab("     FACILITIES      ", pnlFacilities);
					{
						JPanel pnlFacilitiesCenter = new JPanel(new BorderLayout(3, 3));
						pnlFacilities.add(pnlFacilitiesCenter);
						{
							scrollFacilities = new JScrollPane();
							pnlFacilitiesCenter.add(scrollFacilities, BorderLayout.CENTER);
							scrollFacilities.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
							scrollFacilities.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							scrollFacilities.setBorder(line);
							{
								{
									modelFacilities = new modelCARD_Facilities();
									tblFacilities = new _JTableMain(modelFacilities);

									rowHeaderFacilities = tblFacilities.getRowHeader();
									scrollFacilities.setViewportView(tblFacilities);

								}
								{
									rowHeaderFacilities = tblFacilities.getRowHeader();
									rowHeaderFacilities.setModel(new DefaultListModel());
									scrollFacilities.setRowHeaderView(rowHeaderFacilities);
									scrollFacilities.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
											FncTables.getRowHeader_Header());
								}
							}
						}
					}
				}
				{
					JPanel pnlSubdivisionDues = new JPanel(new GridLayout(1, 2, 3, 3));
					tabPM.addTab("        SUBDIVISION Dues          ", pnlSubdivisionDues);
					{
						JPanel pnlSubdivisionDuesWest = new JPanel(new BorderLayout(3, 3));
						pnlSubdivisionDues.add(pnlSubdivisionDuesWest);
						{
							scrollSubdivisionDues = new _JScrollPaneMain();
							pnlSubdivisionDuesWest.add(scrollSubdivisionDues, BorderLayout.CENTER);
							scrollSubdivisionDues.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
							scrollSubdivisionDues
							.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							scrollSubdivisionDues.setBorder(line);
							{
								modelSubdivisionDues = new modelCARD_SubdivisionDues();
								tblSubdivisionDues = new _JTableMain(modelSubdivisionDues);
								scrollSubdivisionDues.setViewportView(tblSubdivisionDues);

								tblSubdivisionDues.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if (tblSubdivisionDues.rowAtPoint(e.getPoint()) == -1) {
											tblSubdivisionDuesTotal.clearSelection();
										} else {
											tblSubdivisionDues.setCellSelectionEnabled(true);
										}
									}

									public void mouseReleased(MouseEvent e) {
										if (tblSubdivisionDues.rowAtPoint(e.getPoint()) == -1) {
											tblSubdivisionDuesTotal.clearSelection();
										} else {
											tblSubdivisionDues.setCellSelectionEnabled(true);
										}
									}
								});

								// Process after row add in tables
								modelSubdivisionDues.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if (e.getType() == TableModelEvent.INSERT) {
											((DefaultListModel) rowHeaderSubdivisionDues.getModel())
											.addElement(modelSubdivisionDues.getRowCount());
											scrollSubdivisionDuesTotal.setRowHeaderView(FncTables.getRowHeader_Footer(
													Integer.toString(modelSubdivisionDues.getRowCount())));
										}
										if (e.getType() == TableModelEvent.DELETE) {
											if (modelSubdivisionDues.getRowCount() == 0) {
												rowHeaderSubdivisionDues.setModel(new DefaultListModel());
												scrollSubdivisionDuesTotal
												.setRowHeaderView(FncTables.getRowHeader_Footer(
														Integer.toString(modelSubdivisionDues.getRowCount())));
											}
										}
									}
								});

							}
							{
								rowHeaderSubdivisionDues = tblSubdivisionDues.getRowHeader();
								rowHeaderSubdivisionDues.setModel(new DefaultListModel());
								scrollSubdivisionDues.setRowHeaderView(rowHeaderSubdivisionDues);
								scrollSubdivisionDues.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
						{
							scrollSubdivisionDuesTotal = new _JScrollPaneTotal(scrollSubdivisionDues);
							pnlSubdivisionDuesWest.add(scrollSubdivisionDuesTotal, BorderLayout.SOUTH);
							{
								modelSubdivisionDuesTotal = new modelCARD_SubdivisionDues();
								modelSubdivisionDuesTotal.addRow(new Object[] { "Total", null, 0.00, null });

								tblSubdivisionDuesTotal = new _JTableTotal(modelSubdivisionDuesTotal,
										tblSubdivisionDues);
								scrollSubdivisionDuesTotal.setViewportView(tblSubdivisionDuesTotal);

								tblSubdivisionDuesTotal.setTotalLabel(0);
							}
						}
					}
					{
						JPanel pnlSubdivisionDuesEast = new JPanel(new BorderLayout(3, 3));
						pnlSubdivisionDues.add(pnlSubdivisionDuesEast);
						{

						}
					}
				}
				{
					JPanel pnlMonthlySOA = new JPanel(new GridLayout(1, 1, 3, 3));
					tabPM.addTab("        Monthly SOA          ", pnlMonthlySOA);
					{
						JPanel pnlMonthlySOA_Center = new JPanel(new BorderLayout(3, 3));
						pnlMonthlySOA.add(pnlMonthlySOA_Center, BorderLayout.CENTER);
						/*
						 * { JPanel pnlMonthlySOA_North = new JPanel(new BorderLayout(3, 3));
						 * pnlMonthlySOA_Center.add(pnlMonthlySOA_North, BorderLayout.NORTH); {
						 * chkPreviewOtherLotSOA = new JCheckBox("Preview Monthly SOA Other Lot");
						 * pnlMonthlySOA_North.add(chkPreviewOtherLotSOA);
						 * chkPreviewOtherLotSOA.addItemListener(new ItemListener() {
						 * 
						 * @Override public void itemStateChanged(ItemEvent e) { Boolean selected =
						 * e.getStateChange() == ItemEvent.SELECTED;
						 * 
						 * if(selected){ displayMonthlySOA(entity_id, proj_id, pbl_id,
						 * String.valueOf(seq_no), true); }else{ displayMonthlySOA(entity_id, proj_id,
						 * pbl_id, String.valueOf(seq_no), false); } } }); } }
						 */
						{
							scrollMonthlySOA = new _JScrollPaneMain();
							pnlMonthlySOA_Center.add(scrollMonthlySOA, BorderLayout.CENTER);
							scrollMonthlySOA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
							scrollMonthlySOA.setBorder(line);
							{
								modelMonthlySOA = new modelCARD_MonthlySOA();
								tblMonthlySOA = new _JTableMain(modelMonthlySOA);
								scrollMonthlySOA.setViewportView(tblMonthlySOA);

								tblMonthlySOA.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if (tblMonthlySOA.rowAtPoint(e.getPoint()) == -1) {
											tblMonthlySOA_Total.clearSelection();
										} else {
											tblMonthlySOA.setCellSelectionEnabled(true);
										}
									}

									public void mouseReleased(MouseEvent e) {
										if (tblMonthlySOA.rowAtPoint(e.getPoint()) == -1) {
											tblMonthlySOA_Total.clearSelection();
										} else {
											tblMonthlySOA.setCellSelectionEnabled(true);
										}
									}
								});

								// Process after row add in tables
								modelMonthlySOA.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if (e.getType() == TableModelEvent.INSERT) {
											((DefaultListModel) rowHeaderMonthlySOA.getModel())
											.addElement(modelMonthlySOA.getRowCount());
											scrollMonthlySOA_Total.setRowHeaderView(FncTables.getRowHeader_Footer(
													Integer.toString(modelMonthlySOA.getRowCount())));
										}
										if (e.getType() == TableModelEvent.DELETE) {
											if (modelMonthlySOA.getRowCount() == 0) {
												rowHeaderMonthlySOA.setModel(new DefaultListModel());
												scrollMonthlySOA.setRowHeaderView(FncTables.getRowHeader_Footer(
														Integer.toString(modelMonthlySOA.getRowCount())));
											}
										}
									}
								});

							}
							{
								rowHeaderMonthlySOA = tblMonthlySOA.getRowHeader();
								rowHeaderMonthlySOA.setModel(new DefaultListModel());
								scrollMonthlySOA.setRowHeaderView(rowHeaderMonthlySOA);
								scrollMonthlySOA.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
						{
							scrollMonthlySOA_Total = new _JScrollPaneTotal(scrollMonthlySOA);
							pnlMonthlySOA_Center.add(scrollMonthlySOA_Total, BorderLayout.SOUTH);
							{
								modelMonthlySOA_Total = new modelCARD_MonthlySOA();
								modelMonthlySOA_Total.addRow(new Object[] { null, null, null, null, null, "Total", 0.00,
										0.00, null, null, null, null, null, null });

								tblMonthlySOA_Total = new _JTableTotal(modelMonthlySOA_Total, tblMonthlySOA);
								scrollMonthlySOA_Total.setViewportView(tblMonthlySOA_Total);

								tblMonthlySOA_Total.setTotalLabel(5);
							}
						}
					}
				}
				{
					JPanel pnlUCARD = new JPanel(new GridLayout(1, 2, 3, 3));
					tabPM.addTab("        UCard Reloads/OB          ", pnlUCARD);
					{
						JPanel pnlUCARD_West = new JPanel(new BorderLayout(3, 3));
						pnlUCARD.add(pnlUCARD_West);
						/*
						 * { JPanel pnlUCARD_North = new JPanel(new BorderLayout(3, 3));
						 * pnlUCARD_West.add(pnlUCARD_North, BorderLayout.NORTH); { chkPreviewOtherUCARD
						 * = new JCheckBox("Preview UCARD Other Lot");
						 * pnlUCARD_North.add(chkPreviewOtherUCARD);
						 * chkPreviewOtherUCARD.addItemListener(new ItemListener() {
						 * 
						 * @Override public void itemStateChanged(ItemEvent e) { Boolean selected =
						 * e.getStateChange() == ItemEvent.SELECTED;
						 * 
						 * if(selected){ //displayMonthlySOA(entity_id, proj_id, pbl_id,
						 * String.valueOf(seq_no), true); displayUCARD(entity_id, proj_id, pbl_id,
						 * String.valueOf(seq_no), true); }else{ //displayMonthlySOA(entity_id, proj_id,
						 * pbl_id, String.valueOf(seq_no), false); displayUCARD(entity_id, proj_id,
						 * pbl_id, String.valueOf(seq_no), false); } } }); } }
						 */
						{
							scrollUCard = new _JScrollPaneMain();
							pnlUCARD_West.add(scrollUCard, BorderLayout.CENTER);
							scrollUCard.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
							// scrollUCard.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							scrollUCard.setBorder(line);
							{
								modelUCARD = new modelCARD_UCard();
								tblUCard = new _JTableMain(modelUCARD);
								scrollUCard.setViewportView(tblUCard);

								tblUCard.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if (tblUCard.rowAtPoint(e.getPoint()) == -1) {
											tblUCARD_Total.clearSelection();
										} else {
											tblUCard.setCellSelectionEnabled(true);
										}
									}

									public void mouseReleased(MouseEvent e) {
										if (tblUCard.rowAtPoint(e.getPoint()) == -1) {
											tblUCARD_Total.clearSelection();
										} else {
											tblUCard.setCellSelectionEnabled(true);
										}
									}
								});

								// Process after row add in tables
								modelUCARD.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if (e.getType() == TableModelEvent.INSERT) {
											((DefaultListModel) rowHeaderUCard.getModel())
											.addElement(modelUCARD.getRowCount());
											scrollUCARD_Total.setRowHeaderView(FncTables
													.getRowHeader_Footer(Integer.toString(modelUCARD.getRowCount())));
										}
										if (e.getType() == TableModelEvent.DELETE) {
											if (modelUCARD.getRowCount() == 0) {
												rowHeaderUCard.setModel(new DefaultListModel());
												scrollUCARD_Total.setRowHeaderView(FncTables.getRowHeader_Footer(
														Integer.toString(modelUCARD.getRowCount())));
											}
										}
									}
								});

							}
							{
								rowHeaderUCard = tblUCard.getRowHeader();
								rowHeaderUCard.setModel(new DefaultListModel());
								scrollUCard.setRowHeaderView(rowHeaderUCard);
								scrollUCard.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
						{
							scrollUCARD_Total = new _JScrollPaneTotal(scrollUCard);
							pnlUCARD_West.add(scrollUCARD_Total, BorderLayout.SOUTH);
							{
								modelUCARD_Total = new modelCARD_UCard();
								modelUCARD_Total.addRow(new Object[] { "Total", null, 0.00, null });

								tblUCARD_Total = new _JTableTotal(modelUCARD_Total, tblUCard);
								scrollUCARD_Total.setViewportView(tblUCARD_Total);

								tblUCARD_Total.setTotalLabel(0);
							}
						}
					}
					{
						JPanel pnlUCARD_East = new JPanel(new BorderLayout(3, 3));
						pnlUCARD.add(pnlUCARD_East);
						{
							JPanel pnlUCARD_East_North = new JPanel(new BorderLayout(3, 3));
							pnlUCARD_East.add(pnlUCARD_East_North, BorderLayout.NORTH);
							{
								lblUtilityCARD = new JLabel("");
								pnlUCARD_East_North.add(lblUtilityCARD);
							}
						}
						{
							JPanel pnlUCARD_East_Center = new JPanel(new BorderLayout(3, 3));
							pnlUCARD_East.add(pnlUCARD_East_Center, BorderLayout.CENTER);
						}
					}
				}
			}
			// }
			// }
		}
	}

	private void CreateReleasedLoanDetailTab() {
		splitReleasedLoanDetail = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitReleasedLoanDetail.setOneTouchExpandable(true);
		splitReleasedLoanDetail.setResizeWeight(.7d);
		{
			{
				JXPanel panRecRet = new JXPanel(new GridLayout(1, 2, 5, 5));
				splitReleasedLoanDetail.add(panRecRet);
				{
					JXPanel panSep1 = new JXPanel(new BorderLayout(5, 5));
					panRecRet.add(panSep1, BorderLayout.CENTER);
					{
						{
							JXPanel panRecap = new JXPanel(new GridLayout(11, 3, 5, 3));
							panSep1.add(panRecap, BorderLayout.CENTER);
							{
								{
									JLabel lblRecapitulation = new JLabel("Recapitulation");
									panRecap.add(lblRecapitulation);
									lblRecapitulation.setFont(fontBoldSanSerEleven);
								}
								{
									JLabel lblRecapitulation = new JLabel("");
									panRecap.add(lblRecapitulation);
								}
								{
									lblSriDocStamp = new JLabel("SRI & Doc. Stamp");
									panRecap.add(lblSriDocStamp);
									lblSriDocStamp.setFont(fontPlainSanSerNine);
								}
								{
									txtSriDocStamp = new _JXFormattedTextField("0.00");
									panRecap.add(txtSriDocStamp, BorderLayout.CENTER);
									txtSriDocStamp.setHorizontalAlignment(JTextField.TRAILING);
									txtSriDocStamp.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtSriDocStamp.setEnabled(true);
									txtSriDocStamp.setEditable(false);
									txtSriDocStamp.setFont(fontPlainSanSerEleven);
								}
								{
									lblFire = new JLabel("Fire");
									panRecap.add(lblFire);
									lblFire.setFont(fontPlainSanSerNine);
								}
								{
									txtFire = new _JXFormattedTextField("0.00");
									panRecap.add(txtFire, BorderLayout.CENTER);
									txtFire.setHorizontalAlignment(JTextField.TRAILING);
									txtFire.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtFire.setEnabled(true);
									txtFire.setEditable(false);
									txtFire.setFont(fontPlainSanSerEleven);
								}
								{
									lblProcessingFee = new JLabel("Processing Fee");
									panRecap.add(lblProcessingFee);
									lblProcessingFee.setFont(fontPlainSanSerNine);
								}
								{
									txtProcessingFee = new _JXFormattedTextField("0.00");
									panRecap.add(txtProcessingFee, BorderLayout.CENTER);
									txtProcessingFee.setHorizontalAlignment(JTextField.TRAILING);
									txtProcessingFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtProcessingFee.setEnabled(true);
									txtProcessingFee.setEditable(false);
									txtProcessingFee.setFont(fontPlainSanSerEleven);
								}
								{
									lblAppraisalFee = new JLabel("Appraisal Fee");
									panRecap.add(lblAppraisalFee);
									lblAppraisalFee.setFont(fontPlainSanSerNine);
								}
								{
									txtAppraisalFee = new _JXFormattedTextField("0.00");
									panRecap.add(txtAppraisalFee, BorderLayout.CENTER);
									txtAppraisalFee.setHorizontalAlignment(JTextField.TRAILING);
									txtAppraisalFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtAppraisalFee.setEnabled(true);
									txtAppraisalFee.setEditable(false);
									txtAppraisalFee.setFont(fontPlainSanSerEleven);
								}
								{
									lblInterimMRI = new JLabel("Interim MRI");
									panRecap.add(lblInterimMRI);
									lblInterimMRI.setFont(fontPlainSanSerNine);
								}
								{
									txtInterimMRI = new _JXFormattedTextField("0.00");
									panRecap.add(txtInterimMRI, BorderLayout.CENTER);
									txtInterimMRI.setHorizontalAlignment(JTextField.TRAILING);
									txtInterimMRI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtInterimMRI.setEnabled(true);
									txtInterimMRI.setEditable(false);
									txtInterimMRI.setFont(fontPlainSanSerEleven);
								}
								{
									lblRetentionFee = new JLabel("Retention Fee");
									panRecap.add(lblRetentionFee);
									lblRetentionFee.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panRETFEE = new JXPanel(new BorderLayout(3, 3));
									panRecap.add(panRETFEE, BorderLayout.CENTER);
									{
										{
											txtPctgRetentionFee = new _JXFormattedTextField("0.00%");
											panRETFEE.add(txtPctgRetentionFee, BorderLayout.LINE_START);
											txtPctgRetentionFee.setHorizontalAlignment(JTextField.TRAILING);
											txtPctgRetentionFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtPctgRetentionFee.setEnabled(true);
											txtPctgRetentionFee.setEditable(false);
											txtPctgRetentionFee.setPreferredSize(new Dimension(40, 0));
											txtPctgRetentionFee.setFont(fontPlainSanSerEleven);
											txtPctgRetentionFee.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtRetentionFee = new _JXFormattedTextField("0.00");
											panRETFEE.add(txtRetentionFee, BorderLayout.CENTER);
											txtRetentionFee.setHorizontalAlignment(JTextField.TRAILING);
											txtRetentionFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRetentionFee.setEnabled(true);
											txtRetentionFee.setEditable(false);
											txtRetentionFee.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblFirstMA = new JLabel("First MA");
									panRecap.add(lblFirstMA);
									lblFirstMA.setFont(fontPlainSanSerNine);
								}
								{
									txtFirstMA = new _JXFormattedTextField("0.00");
									panRecap.add(txtFirstMA, BorderLayout.CENTER);
									txtFirstMA.setHorizontalAlignment(JTextField.TRAILING);
									txtFirstMA.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtFirstMA.setEnabled(true);
									txtFirstMA.setEditable(false);
									txtFirstMA.setFont(fontPlainSanSerEleven);
								}
								{
									lblRefilingFee = new JLabel("Refiling Fee");
									panRecap.add(lblRefilingFee);
									lblRefilingFee.setFont(fontPlainSanSerNine);
								}
								{
									txtRefilingFee = new _JXFormattedTextField("0.00");
									panRecap.add(txtRefilingFee, BorderLayout.CENTER);
									txtRefilingFee.setHorizontalAlignment(JTextField.TRAILING);
									txtRefilingFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtRefilingFee.setEnabled(true);
									txtRefilingFee.setEditable(false);
									txtRefilingFee.setFont(fontPlainSanSerEleven);
								}
								{
									panRecap.add(new JSeparator(JSeparator.HORIZONTAL));
									panRecap.add(new JSeparator(JSeparator.HORIZONTAL));
								}
								{
									lblRecapTotal = new JLabel("Total");
									panRecap.add(lblRecapTotal);
									lblRecapTotal.setFont(fontBoldSanSerEleven);
								}
								{
									txtRecapTotal = new _JXFormattedTextField("0.00");
									panRecap.add(txtRecapTotal, BorderLayout.CENTER);
									txtRecapTotal.setHorizontalAlignment(JTextField.TRAILING);
									txtRecapTotal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtRecapTotal.setEnabled(true);
									txtRecapTotal.setEditable(false);
								}
							}
						}
					}
				}
				{
					JXPanel panSep2 = new JXPanel(new BorderLayout(5, 5));
					panRecRet.add(panSep2, BorderLayout.CENTER);
					{
						{
							JSeparator sep = new JSeparator();
							panSep2.add(sep, BorderLayout.LINE_START);
							sep.setOrientation(JSeparator.VERTICAL);
							sep.setPreferredSize(new Dimension(10, 0));
						}
						{
							JXPanel panRetentions = new JXPanel(new GridLayout(11, 2, 5, 3));
							panSep2.add(panRetentions, BorderLayout.CENTER);
							{
								{
									JLabel lblRetentions = new JLabel("Retention");
									panRetentions.add(lblRetentions);
									lblRetentions.setFont(fontBoldSanSerEleven);
								}
								{
									JLabel lblRetentions = new JLabel("");
									panRetentions.add(lblRetentions);
								}
								{
									lblEPEB = new JLabel("EPEB");
									panRetentions.add(lblEPEB);
									lblEPEB.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panEPEB = new JXPanel(new BorderLayout(3, 3));
									panRetentions.add(panEPEB, BorderLayout.CENTER);
									{
										{
											txtPctgEPEB = new _JXFormattedTextField("0.00%");
											panEPEB.add(txtPctgEPEB, BorderLayout.LINE_START);
											txtPctgEPEB.setHorizontalAlignment(JTextField.TRAILING);
											txtPctgEPEB.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtPctgEPEB.setEnabled(true);
											txtPctgEPEB.setEditable(false);
											txtPctgEPEB.setPreferredSize(new Dimension(40, 0));
											txtPctgEPEB.setFont(fontPlainSanSerEleven);
											txtPctgEPEB.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtEPEB = new _JXFormattedTextField("0.00");
											panEPEB.add(txtEPEB, BorderLayout.CENTER);
											txtEPEB.setHorizontalAlignment(JTextField.TRAILING);
											txtEPEB.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtEPEB.setEnabled(true);
											txtEPEB.setEditable(false);
											txtEPEB.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblETD = new JLabel("ETD");
									panRetentions.add(lblETD);
									lblETD.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panETD = new JXPanel(new BorderLayout(3, 3));
									panRetentions.add(panETD, BorderLayout.CENTER);
									{
										{
											txtPctgETD = new _JXFormattedTextField("0.00%");
											panETD.add(txtPctgETD, BorderLayout.LINE_START);
											txtPctgETD.setHorizontalAlignment(JTextField.TRAILING);
											txtPctgETD.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtPctgETD.setEnabled(true);
											txtPctgETD.setEditable(false);
											txtPctgETD.setPreferredSize(new Dimension(40, 0));
											txtPctgETD.setFont(fontPlainSanSerEleven);
											txtPctgETD.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtETD = new _JXFormattedTextField("0.00");
											panETD.add(txtETD, BorderLayout.CENTER);
											txtETD.setHorizontalAlignment(JTextField.TRAILING);
											txtETD.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtETD.setEnabled(true);
											txtETD.setEditable(false);
											txtETD.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblCOC = new JLabel("COC");
									panRetentions.add(lblCOC);
									lblCOC.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panCOC = new JXPanel(new BorderLayout(3, 3));
									panRetentions.add(panCOC, BorderLayout.CENTER);
									{
										{
											txtPctgCOC = new _JXFormattedTextField("0.00%");
											panCOC.add(txtPctgCOC, BorderLayout.LINE_START);
											txtPctgCOC.setHorizontalAlignment(JTextField.TRAILING);
											txtPctgCOC.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtPctgCOC.setEnabled(true);
											txtPctgCOC.setEditable(false);
											txtPctgCOC.setPreferredSize(new Dimension(40, 0));
											txtPctgCOC.setFont(fontPlainSanSerEleven);
											txtPctgCOC.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtCOC = new _JXFormattedTextField("0.00");
											panCOC.add(txtCOC, BorderLayout.CENTER);
											txtCOC.setHorizontalAlignment(JTextField.TRAILING);
											txtCOC.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtCOC.setEnabled(true);
											txtCOC.setEditable(false);
											txtCOC.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblMERL = new JLabel("Meralco");
									panRetentions.add(lblMERL);
									lblMERL.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panMERL = new JXPanel(new BorderLayout(3, 3));
									panRetentions.add(panMERL, BorderLayout.CENTER);
									{
										{
											txtPctgMERL = new _JXFormattedTextField("0.00%");
											panMERL.add(txtPctgMERL, BorderLayout.LINE_START);
											txtPctgMERL.setHorizontalAlignment(JTextField.TRAILING);
											txtPctgMERL.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtPctgMERL.setEnabled(true);
											txtPctgMERL.setEditable(false);
											txtPctgMERL.setPreferredSize(new Dimension(40, 0));
											txtPctgMERL.setFont(fontPlainSanSerEleven);
											txtPctgMERL.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtMERL = new _JXFormattedTextField("0.00");
											panMERL.add(txtMERL, BorderLayout.CENTER);
											txtMERL.setHorizontalAlignment(JTextField.TRAILING);
											txtMERL.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtMERL.setEnabled(true);
											txtMERL.setEditable(false);
											txtMERL.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblCOM5 = new JLabel("Comm. Fac.");
									panRetentions.add(lblCOM5);
									lblCOM5.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panCOM5 = new JXPanel(new BorderLayout(3, 3));
									panRetentions.add(panCOM5, BorderLayout.CENTER);
									{
										{
											txtPctgCOM5 = new _JXFormattedTextField("0.00%");
											panCOM5.add(txtPctgCOM5, BorderLayout.LINE_START);
											txtPctgCOM5.setHorizontalAlignment(JTextField.CENTER);
											txtPctgCOM5.setEnabled(true);
											txtPctgCOM5.setEditable(false);
											txtPctgCOM5.setPreferredSize(new Dimension(40, 0));
											txtPctgCOM5.setFont(fontPlainSanSerEleven);
											txtPctgCOM5.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtCOM5 = new _JXFormattedTextField("0.00");
											panCOM5.add(txtCOM5, BorderLayout.CENTER);
											txtCOM5.setHorizontalAlignment(JTextField.TRAILING);
											txtCOM5.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtCOM5.setEnabled(true);
											txtCOM5.setEditable(false);
											txtCOM5.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblROW = new JLabel("Right of Way");
									panRetentions.add(lblROW);
									lblROW.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panROW = new JXPanel(new BorderLayout(3, 3));
									panRetentions.add(panROW, BorderLayout.CENTER);
									{
										{
											txtPctgROW = new _JXFormattedTextField("0.00%");
											panROW.add(txtPctgROW, BorderLayout.LINE_START);
											txtPctgROW.setHorizontalAlignment(JTextField.CENTER);
											txtPctgROW.setEnabled(true);
											txtPctgROW.setEditable(false);
											txtPctgROW.setPreferredSize(new Dimension(40, 0));
											txtPctgROW.setFont(fontPlainSanSerEleven);
											txtPctgROW.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtROW = new _JXFormattedTextField("0.00");
											panROW.add(txtROW, BorderLayout.CENTER);
											txtROW.setHorizontalAlignment(JTextField.TRAILING);
											txtROW.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtROW.setEnabled(true);
											txtROW.setEditable(false);
											txtROW.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblRet = new JLabel("");
									panRetentions.add(lblRet);
									lblRet.setFont(fontPlainSanSerNine);
								}
								{
									txtRet = new _JXFormattedTextField("0.00");
									panRetentions.add(txtRet, BorderLayout.CENTER);
									txtRet.setHorizontalAlignment(JTextField.TRAILING);
									txtRet.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtRet.setEnabled(true);
									txtRet.setEditable(false);
									txtRet.setVisible(false);
								}
								{
									lblCOM3 = new JLabel("");
									panRetentions.add(lblCOM3);
								}
								{
									txtCOM3 = new _JXFormattedTextField("0.00");
									panRetentions.add(txtCOM3, BorderLayout.CENTER);
									txtCOM3.setHorizontalAlignment(JTextField.TRAILING);
									txtCOM3.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtCOM3.setEnabled(true);
									txtCOM3.setEditable(false);
									txtCOM3.setVisible(false);
								}
								{
									panRetentions.add(new JSeparator(JSeparator.HORIZONTAL));
									panRetentions.add(new JSeparator(JSeparator.HORIZONTAL));
								}
								{
									lblRetTotal = new JLabel("Total");
									panRetentions.add(lblRetTotal);
									lblRetTotal.setFont(fontBoldSanSerEleven);
								}
								{
									txtRetTotal = new _JXFormattedTextField("0.00");
									panRetentions.add(txtRetTotal, BorderLayout.CENTER);
									txtRetTotal.setHorizontalAlignment(JTextField.TRAILING);
									txtRetTotal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtRetTotal.setEnabled(true);
									txtRetTotal.setEditable(false);
								}
							}
						}
					}
				}
				{
					JXPanel panSep3 = new JXPanel(new BorderLayout(5, 5));
					panRecRet.add(panSep3, BorderLayout.CENTER);
					{
						{
							JSeparator sep = new JSeparator();
							panSep3.add(sep, BorderLayout.LINE_START);
							sep.setOrientation(JSeparator.VERTICAL);
							sep.setPreferredSize(new Dimension(10, 0));
						}
						{
							JXPanel panRetRet = new JXPanel(new GridLayout(11, 2, 5, 3));
							panSep3.add(panRetRet, BorderLayout.CENTER);
							{
								{
									JLabel lblReturnedRetentions = new JLabel("Returned Ret. Fee");
									panRetRet.add(lblReturnedRetentions);
									lblReturnedRetentions.setFont(fontBoldSanSerTens);
								}
								{
									JLabel lblReturnedRetentions = new JLabel("");
									panRetRet.add(lblReturnedRetentions);
								}
								{
									lblEPEB = new JLabel("EPEB");
									panRetRet.add(lblEPEB);
									lblEPEB.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panRetEPEB = new JXPanel(new BorderLayout(3, 3));
									panRetRet.add(panRetEPEB, BorderLayout.CENTER);
									{
										{
											txtPctgRetEPEB = new _JXFormattedTextField("0.00%");
											panRetEPEB.add(txtPctgRetEPEB, BorderLayout.LINE_START);
											txtPctgRetEPEB.setHorizontalAlignment(JTextField.CENTER);
											txtPctgRetEPEB.setEnabled(true);
											txtPctgRetEPEB.setEditable(false);
											txtPctgRetEPEB.setPreferredSize(new Dimension(40, 0));
											txtPctgRetEPEB.setFont(fontPlainSanSerEleven);
											txtPctgRetEPEB.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtRetEPEB = new _JXFormattedTextField("0.00");
											panRetEPEB.add(txtRetEPEB, BorderLayout.CENTER);
											txtRetEPEB.setHorizontalAlignment(JTextField.TRAILING);
											txtRetEPEB.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRetEPEB.setEnabled(true);
											txtRetEPEB.setEditable(false);
											txtRetEPEB.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblETD = new JLabel("ETD");
									panRetRet.add(lblETD);
									lblETD.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panRetETD = new JXPanel(new BorderLayout(3, 3));
									panRetRet.add(panRetETD, BorderLayout.CENTER);
									{
										{
											txtPctgRetETD = new _JXFormattedTextField("0.00%");
											panRetETD.add(txtPctgRetETD, BorderLayout.LINE_START);
											txtPctgRetETD.setHorizontalAlignment(JTextField.CENTER);
											txtPctgRetETD.setEnabled(true);
											txtPctgRetETD.setEditable(false);
											txtPctgRetETD.setPreferredSize(new Dimension(40, 0));
											txtPctgRetETD.setFont(fontPlainSanSerEleven);
											txtPctgRetETD.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtRetETD = new _JXFormattedTextField("0.00");
											panRetETD.add(txtRetETD, BorderLayout.CENTER);
											txtRetETD.setHorizontalAlignment(JTextField.TRAILING);
											txtRetETD.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRetETD.setEnabled(true);
											txtRetETD.setEditable(false);
											txtRetETD.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblCOC = new JLabel("COC");
									panRetRet.add(lblCOC);
									lblCOC.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panCOC = new JXPanel(new BorderLayout(3, 3));
									panRetRet.add(panCOC, BorderLayout.CENTER);
									{
										{
											txtPctgRetCOC = new _JXFormattedTextField("0.00%");
											panCOC.add(txtPctgRetCOC, BorderLayout.LINE_START);
											txtPctgRetCOC.setHorizontalAlignment(JTextField.CENTER);
											txtPctgRetCOC.setEnabled(true);
											txtPctgRetCOC.setEditable(false);
											txtPctgRetCOC.setPreferredSize(new Dimension(40, 0));
											txtPctgRetCOC.setFont(fontPlainSanSerEleven);
											txtPctgRetCOC.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtRetCOC = new _JXFormattedTextField("0.00");
											panCOC.add(txtRetCOC, BorderLayout.CENTER);
											txtRetCOC.setHorizontalAlignment(JTextField.TRAILING);
											txtRetCOC.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRetCOC.setEnabled(true);
											txtRetCOC.setEditable(false);
											txtRetCOC.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblMERL = new JLabel("Meralco");
									panRetRet.add(lblMERL);
									lblMERL.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panMERL = new JXPanel(new BorderLayout(3, 3));
									panRetRet.add(panMERL, BorderLayout.CENTER);
									{
										{
											txtPctgRetMERL = new _JXFormattedTextField("0.00%");
											panMERL.add(txtPctgRetMERL, BorderLayout.LINE_START);
											txtPctgRetMERL.setHorizontalAlignment(JTextField.CENTER);
											txtPctgRetMERL.setEnabled(true);
											txtPctgRetMERL.setEditable(false);
											txtPctgRetMERL.setPreferredSize(new Dimension(40, 0));
											txtPctgRetMERL.setFont(fontPlainSanSerEleven);
											txtPctgRetMERL.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtRetMERL = new _JXFormattedTextField("0.00");
											panMERL.add(txtRetMERL, BorderLayout.CENTER);
											txtRetMERL.setHorizontalAlignment(JTextField.TRAILING);
											txtRetMERL.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRetMERL.setEnabled(true);
											txtRetMERL.setEditable(false);
											txtRetMERL.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblCOM5 = new JLabel("Comm. Fac.");
									panRetRet.add(lblCOM5, BorderLayout.LINE_START);
									lblCOM5.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panRetCOM5 = new JXPanel(new BorderLayout(3, 3));
									panRetRet.add(panRetCOM5, BorderLayout.CENTER);
									{
										{
											txtPctgRetCOM5 = new _JXFormattedTextField("0.00%");
											panRetCOM5.add(txtPctgRetCOM5, BorderLayout.LINE_START);
											txtPctgRetCOM5.setHorizontalAlignment(JTextField.CENTER);
											txtPctgRetCOM5.setEnabled(true);
											txtPctgRetCOM5.setEditable(false);
											txtPctgRetCOM5.setPreferredSize(new Dimension(40, 0));
											txtPctgRetCOM5.setFont(fontPlainSanSerEleven);
											txtPctgRetCOM5.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtRetCOM5 = new _JXFormattedTextField("0.00");
											panRetCOM5.add(txtRetCOM5, BorderLayout.CENTER);
											txtRetCOM5.setHorizontalAlignment(JTextField.TRAILING);
											txtRetCOM5.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRetCOM5.setEnabled(true);
											txtRetCOM5.setEditable(false);
											txtRetCOM5.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblROW = new JLabel("Right of Way");
									panRetRet.add(lblROW);
									lblROW.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panRetRow = new JXPanel(new BorderLayout(3, 3));
									panRetRet.add(panRetRow, BorderLayout.CENTER);
									{
										{
											txtPctgRetROW = new _JXFormattedTextField("0.00%");
											panRetRow.add(txtPctgRetROW, BorderLayout.LINE_START);
											txtPctgRetROW.setHorizontalAlignment(JTextField.CENTER);
											txtPctgRetROW.setEnabled(true);
											txtPctgRetROW.setEditable(false);
											txtPctgRetROW.setPreferredSize(new Dimension(40, 0));
											txtPctgRetROW.setFont(fontPlainSanSerEleven);
											txtPctgRetROW.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtRetROW = new _JXFormattedTextField("0.00");
											panRetRow.add(txtRetROW, BorderLayout.CENTER);
											txtRetROW.setHorizontalAlignment(JTextField.TRAILING);
											txtRetROW.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRetROW.setEnabled(true);
											txtRetROW.setEditable(false);
											txtRetROW.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblCOM3 = new JLabel("");
									panRetRet.add(lblCOM3);
									lblCOM3.setFont(fontPlainSanSerNine);
								}
								{
									txtRetCOM3 = new _JXFormattedTextField("0.00");
									panRetRet.add(txtRetCOM3, BorderLayout.CENTER);
									txtRetCOM3.setHorizontalAlignment(JTextField.TRAILING);
									txtRetCOM3.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtRetCOM3.setEditable(false);
									txtRetCOM3.setVisible(false);
								}
								{
									lblRet = new JLabel("");
									panRetRet.add(lblRet);
									lblRet.setFont(fontPlainSanSerNine);
								}
								{
									txtRetRet = new _JXFormattedTextField("0.00");
									panRetRet.add(txtRetRet, BorderLayout.CENTER);
									txtRetRet.setHorizontalAlignment(JTextField.TRAILING);
									txtRetRet.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtRetRet.setEnabled(true);
									txtRetRet.setEditable(false);
									txtRetRet.setVisible(false);
								}
								{
									panRetRet.add(new JSeparator(JSeparator.HORIZONTAL));
									panRetRet.add(new JSeparator(JSeparator.HORIZONTAL));
								}
								{
									lblRetTotal = new JLabel("Total");
									panRetRet.add(lblRetTotal);
									lblRetTotal.setFont(fontBoldSanSerEleven);
								}
								{
									txtRetRetTotal = new _JXFormattedTextField("0.00");
									panRetRet.add(txtRetRetTotal, BorderLayout.CENTER);
									txtRetRetTotal.setHorizontalAlignment(JTextField.TRAILING);
									txtRetRetTotal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtRetRetTotal.setEnabled(true);
									txtRetRetTotal.setEditable(false);
								}
							}
						}
					}
				}
			}
			{
				JXPanel panSummary = new JXPanel(new BorderLayout(5, 5));
				splitReleasedLoanDetail.add(panSummary);
				panSummary.setPreferredSize(new Dimension(300, 0));
				panSummary.setBorder(
						JTBorderFactory.createTitleBorder("Summary", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JXPanel panSummAll = new JXPanel(new GridLayout(11, 1, 5, 3));
						panSummary.add(panSummAll, BorderLayout.CENTER);
						{

							{
								JXPanel panLine0 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine0, BorderLayout.CENTER);
								{

								}
							}
							{
								JXPanel panLine1 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine1, BorderLayout.CENTER);
								{
									{
										lblLoanable = new JLabel("Loanable Amount");
										panLine1.add(lblLoanable);
										lblLoanable.setFont(fontPlainSanSerEleven);
									}
									{
										lblLoanableAmount = new JLabel("0.00");
										panLine1.add(lblLoanableAmount);
										lblLoanableAmount.setHorizontalAlignment(JLabel.TRAILING);
										lblLoanableAmount.setFont(fontBoldSanSerEleven);
									}
								}
							}
							{
								JXPanel panLine2 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine2, BorderLayout.CENTER);
								{
									{
										panLine2.add(new JLabel(""));
									}
									{
										panLine2.add(new JLabel(""));
									}
								}
							}
							{
								JXPanel panLine3 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine3, BorderLayout.CENTER);
								{
									{
										lblSummRecapitulation = new JLabel("Recapitulation");
										panLine3.add(lblSummRecapitulation);
										lblSummRecapitulation.setHorizontalAlignment(JLabel.LEADING);
										lblSummRecapitulation.setFont(fontPlainSanSerEleven);
									}
									{
										lblSummRecapitulationAmount = new JLabel("0.00");
										panLine3.add(lblSummRecapitulationAmount);
										lblSummRecapitulationAmount.setHorizontalAlignment(JLabel.TRAILING);
										lblSummRecapitulationAmount.setFont(fontBoldSanSerEleven);
									}
								}
							}
							{
								JXPanel panLine4 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine4, BorderLayout.CENTER);
								{
									{
										lblSummRetention = new JLabel("Retention");
										panLine4.add(lblSummRetention);
										lblSummRetention.setHorizontalAlignment(JLabel.LEADING);
										lblSummRetention.setFont(fontPlainSanSerEleven);
									}
									{
										lblSummRetentionAmount = new JLabel("0.00");
										panLine4.add(lblSummRetentionAmount);
										lblSummRetentionAmount.setHorizontalAlignment(JLabel.TRAILING);
										lblSummRetentionAmount.setFont(fontBoldSanSerEleven);
									}
								}
							}
							{
								JXPanel panLine4 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine4, BorderLayout.CENTER);
								{
									{
										JLabel label = new JLabel("");
										panLine4.add(label);
									}
									{
										panLine4.add(new JSeparator(JSeparator.HORIZONTAL));
									}
								}
							}
							{
								JXPanel panLine5 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine5, BorderLayout.CENTER);
								{
									{
										lblNetProceeds = new JLabel("Net Proceeds");
										panLine5.add(lblNetProceeds);
										lblNetProceeds.setHorizontalAlignment(JLabel.LEADING);
										lblNetProceeds.setFont(fontPlainSanSerEleven);
									}
									{
										lblNetProceedsAmount = new JLabel("0.00");
										panLine5.add(lblNetProceedsAmount);
										lblNetProceedsAmount.setHorizontalAlignment(JLabel.TRAILING);
										lblNetProceedsAmount.setFont(fontBoldSanSerEleven);
									}
								}
							}
							{
								JXPanel panLine6 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine6, BorderLayout.CENTER);
								{
									{
										lblSummReturnedRetention = new JLabel("Returned Ret.");
										panLine6.add(lblSummReturnedRetention);
										lblSummReturnedRetention.setHorizontalAlignment(JLabel.LEADING);
										lblSummReturnedRetention.setFont(fontPlainSanSerEleven);
									}
									{
										lblSummReturnedRetentionAmount = new JLabel("0.00");
										panLine6.add(lblSummReturnedRetentionAmount);
										lblSummReturnedRetentionAmount.setHorizontalAlignment(JLabel.TRAILING);
										lblSummReturnedRetentionAmount.setFont(fontBoldSanSerEleven);
									}
								}
							}
							{
								JXPanel panLine7 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine7, BorderLayout.CENTER);
								{
									{
										JLabel label = new JLabel("");
										panLine7.add(label);
									}
									{
										panLine7.add(new JSeparator(JSeparator.HORIZONTAL));
									}
								}
							}
							{
								JXPanel panLine8 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine8, BorderLayout.CENTER);
								{
									{
										lblTotalNetProceeds = new JLabel("Total Net Proceeds");
										panLine8.add(lblTotalNetProceeds);
										lblTotalNetProceeds.setFont(fontPlainSanSerEleven);
									}
									{
										lblTotalNetProceedsAmount = new JLabel("0.00");
										panLine8.add(lblTotalNetProceedsAmount);
										lblTotalNetProceedsAmount.setHorizontalAlignment(JLabel.TRAILING);
										lblTotalNetProceedsAmount.setFont(fontBoldSanSerEleven);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void CreateHDMFUnitInspectionTab() {
		panHDMFUnitInspection = new JXPanel(new BorderLayout(5, 5));
		panHDMFUnitInspection.setOpaque(isOpaque());
		{
			scrollPostInsp = new JScrollPane();
			panHDMFUnitInspection.add(scrollPostInsp, BorderLayout.CENTER);
			scrollPostInsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPostInsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPostInsp.setBorder(line);
			{
				{
					modelPostInsp = new model_hdmf_postInspection_card();
					tblPostInsp = new _JTableMain(modelPostInsp);

					rowPostInsp = tblPostInsp.getRowHeader();
					scrollPostInsp.setViewportView(tblPostInsp);

					tblPostInsp.getColumnModel().getColumn(0).setPreferredWidth(136);
					tblPostInsp.getColumnModel().getColumn(1).setPreferredWidth(225);
					tblPostInsp.getColumnModel().getColumn(2).setPreferredWidth(136);
					tblPostInsp.getColumnModel().getColumn(3).setPreferredWidth(225);
					tblPostInsp.getColumnModel().getColumn(4).setPreferredWidth(275);
				}
				{
					rowPostInsp = tblPostInsp.getRowHeader();
					rowPostInsp.setModel(new DefaultListModel());
					scrollPostInsp.setRowHeaderView(rowPostInsp);
					scrollPostInsp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}
	}

	private void CreateHDMFDetailsTab() {
		panHDMFDetails = new JXPanel(new BorderLayout(5, 5));
		panHDMFDetails.setOpaque(isOpaque());
		{
			tabHDMF_hdmfDetails = new _JTabbedPane();
			panHDMFDetails.add(tabHDMF_hdmfDetails, BorderLayout.CENTER);
			tabHDMF_hdmfDetails.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				{
					panHDMFDetails_schedule = new JXPanel(new BorderLayout(5, 5));
					tabHDMF_hdmfDetails.add("                       HDMF Schedule                                     ",
							panHDMFDetails_schedule);
					panHDMFDetails_schedule.setOpaque(isOpaque());
					{
						scrollHDMFSchedule = new JScrollPane();
						panHDMFDetails_schedule.add(scrollHDMFSchedule, BorderLayout.CENTER);
						scrollHDMFSchedule.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
						scrollHDMFSchedule.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrollHDMFSchedule.setBorder(line);
						{
							{
								modelHDMFSchedule = new model_hdmf_schedule();
								tblHDMFSchedule = new _JTableMain(modelHDMFSchedule);

								rowHDMFSchedule = tblHDMFSchedule.getRowHeader();
								scrollHDMFSchedule.setViewportView(tblHDMFSchedule);

								tblHDMFSchedule.getColumnModel().getColumn(0).setPreferredWidth(98);
								tblHDMFSchedule.getColumnModel().getColumn(1).setPreferredWidth(97);
								tblHDMFSchedule.getColumnModel().getColumn(2).setPreferredWidth(97);
								tblHDMFSchedule.getColumnModel().getColumn(3).setPreferredWidth(97);
								tblHDMFSchedule.getColumnModel().getColumn(4).setPreferredWidth(70);
								tblHDMFSchedule.getColumnModel().getColumn(5).setPreferredWidth(70);
								tblHDMFSchedule.getColumnModel().getColumn(6).setPreferredWidth(97);
								tblHDMFSchedule.getColumnModel().getColumn(7).setPreferredWidth(97);
								tblHDMFSchedule.getColumnModel().getColumn(8).setPreferredWidth(97);
								tblHDMFSchedule.getColumnModel().getColumn(9).setPreferredWidth(97);
								tblHDMFSchedule.getColumnModel().getColumn(10).setPreferredWidth(70);

								tblHDMFSchedule.setSortable(false);
							}
							{
								rowHDMFSchedule = tblHDMFSchedule.getRowHeader();
								rowHDMFSchedule.setModel(new DefaultListModel());
								scrollHDMFSchedule.setRowHeaderView(rowHDMFSchedule);
								scrollHDMFSchedule.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
					}
				}
				{
					panHDMFDetails_payments = new JXPanel(new BorderLayout(5, 5));
					tabHDMF_hdmfDetails.add("                       HDMF Paments                                      ",
							panHDMFDetails_payments);
					panHDMFDetails_payments.setOpaque(isOpaque());
					{
						scrollHDMFPayments = new JScrollPane();
						panHDMFDetails_payments.add(scrollHDMFPayments, BorderLayout.CENTER);
						scrollHDMFPayments.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
						scrollHDMFPayments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrollHDMFPayments.setBorder(line);
						{
							{
								modelHDMFPayments = new model_hdmf_payments();
								tblHDMFPayments = new _JTableMain(modelHDMFPayments);

								rowHDMFPayments = tblHDMFPayments.getRowHeader();
								scrollHDMFPayments.setViewportView(tblHDMFPayments);

								tblHDMFPayments.getColumnModel().getColumn(0).setPreferredWidth(97);
								tblHDMFPayments.getColumnModel().getColumn(1).setPreferredWidth(97);
								tblHDMFPayments.getColumnModel().getColumn(2).setPreferredWidth(97);
								tblHDMFPayments.getColumnModel().getColumn(3).setPreferredWidth(97);
								tblHDMFPayments.getColumnModel().getColumn(4).setPreferredWidth(97);

								tblHDMFPayments.getColumnModel().getColumn(5).setPreferredWidth(97);
								tblHDMFPayments.getColumnModel().getColumn(6).setPreferredWidth(97);
								tblHDMFPayments.getColumnModel().getColumn(7).setPreferredWidth(97);
								tblHDMFPayments.getColumnModel().getColumn(8).setPreferredWidth(211);
								tblHDMFPayments.getColumnModel().getColumn(9).setPreferredWidth(97);

								tblHDMFPayments.setSortable(false);
							}
							{
								rowHDMFPayments = tblHDMFPayments.getRowHeader();
								rowHDMFPayments.setModel(new DefaultListModel());
								scrollHDMFPayments.setRowHeaderView(rowHDMFPayments);
								scrollHDMFPayments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}
						}
					}
				}
			}
		}
	}

	public static Boolean displayLoanFilingStatus(String entity_id, String proj_id, String pbl_id, String seq_no) {
		Boolean blnRev = false;
		FncTables.clearTable(modelLoanFilingStatus);
		DefaultListModel listModel = new DefaultListModel();
		rowLoanFilingStatus.setModel(listModel);

		pgSelect db = new pgSelect();
		db.select("select * from view_hdmf_info_loanfilingstatus('" + entity_id + "', '" + proj_id + "', '" + pbl_id
				+ "', '" + seq_no + "')");
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelLoanFilingStatus.addRow(db.getResult()[x]);
				listModel.addElement(modelLoanFilingStatus.getRowCount());
			}
			blnRev = true;
		} else {
			blnRev = false;
		}
		;

		return blnRev;
	};

	public static Boolean displayHDMFUnitInspection(String entity_id, String proj_id, String pbl_id, String seq_no) {
		Boolean blnRev = false;
		FncTables.clearTable(modelPostInsp);
		DefaultListModel listModel = new DefaultListModel();
		rowPostInsp.setModel(listModel);

		pgSelect db = new pgSelect();
		db.select("select a.date_created as trans_date, d.status_desc as status, \n"
				+ "a.trans_date as date_inspected, a.hdmf_rep, a.remarks \n" + "from rf_hdmf_insp_detail a \n"
				+ "inner join rf_entity b on a.entity_id = b.entity_id \n"
				+ "inner join mf_unit_info c on a.pbl_id = c.pbl_id and c.proj_id = '" + proj_id + "' \n"
				+ "inner join mf_house_inspection_status d on a.insp_status_id = d.status_id \n"
				+ "where a.entity_id = '" + entity_id + "' and a.pbl_id = '" + pbl_id + "' and a.status_id = 'A' \n"
				+ "order by insp_detail_rec_id desc, a.trans_date desc");
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelPostInsp.addRow(db.getResult()[x]);
				listModel.addElement(modelPostInsp.getRowCount());
			}
			blnRev = true;
		} else {
			blnRev = false;
		}
		;

		return blnRev;
	};

	public static Boolean displayHDMFPayments(String entity_id, String proj_id, String pbl_id, String seq_no) {
		Boolean blnRev = false;
		FncTables.clearTable(modelHDMFPayments);
		DefaultListModel listModel = new DefaultListModel();
		rowHDMFPayments.setModel(listModel);

		pgSelect db = new pgSelect();
		db.select("select * from view_hdmf_payments('" + entity_id + "', '" + proj_id + "', '" + pbl_id + "', '"
				+ seq_no + "'); ");

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelHDMFPayments.addRow(db.getResult()[x]);
				listModel.addElement(modelHDMFPayments.getRowCount());
			}
			blnRev = true;
		} else {
			blnRev = false;
		}
		;

		return blnRev;
	};

	public static Boolean displayHDMFSchedule(String entity_id, String proj_id, String pbl_id, String seq_no) {
		Boolean blnRev = false;
		FncTables.clearTable(modelHDMFSchedule);
		DefaultListModel listModel = new DefaultListModel();
		rowHDMFSchedule.setModel(listModel);

		pgSelect db = new pgSelect();
		db.select("select * from view_hdmf_schedule('" + entity_id + "', '" + proj_id + "', '" + pbl_id + "', '"
				+ seq_no + "'); ");

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelHDMFSchedule.addRow(db.getResult()[x]);
				listModel.addElement(modelHDMFSchedule.getRowCount());
			}
			blnRev = true;
		} else {
			blnRev = false;
		}
		;

		return blnRev;
	};

	//Done
	public static void displayWaterMeterReading(String entity_id, String proj_id, String pbl_id, String seq_no,
			Boolean other_lot) {
		PropertyManagement.entity_id = entity_id;
		PropertyManagement.proj_id = proj_id;
		PropertyManagement.pbl_id = pbl_id;
		PropertyManagement.seq_no = Integer.valueOf(seq_no);

		modelWaterReading.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHeaderWaterMeterReading.setModel(listModel);

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_water_meter_reading('" + entity_id + "', '" + proj_id + "', '" + pbl_id
				+ "', " + seq_no + ", " + other_lot + ")";
		db.select(SQL);
		FncSystem.out("SQL Water Meter Reading", SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelWaterReading.addRow(db.getResult()[x]);
				listModel.addElement(modelWaterReading.getRowCount());
			}
		}
		tblWaterMeterReading.packAll();
	}

	public static void displayFacilities(String entity_id, String proj_id, String pbl_id, String seq_no) {
		modelFacilities.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHeaderFacilities.setModel(listModel);

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_facilities('" + entity_id + "', '" + proj_id + "', '" + pbl_id + "', "
				+ seq_no + ")";
		db.select(SQL);

		FncSystem.out("SQL Facilities", SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelFacilities.addRow(db.getResult()[x]);
				listModel.addElement(modelFacilities.getRowCount());
			}
		}
		tblFacilities.packAll();
	}

	//Done
	public static void displayUCARD(String entity_id, String proj_id, String pbl_id, String seq_no, Boolean other_lot) {
		modelUCARD.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHeaderUCard.setModel(listModel);

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_ucard('" + entity_id + "', '" + proj_id + "', '" + pbl_id + "', " + seq_no
				+ ", " + other_lot + ")";
		db.select(SQL);
		FncSystem.out("SQL UCARD", SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelUCARD.addRow(db.getResult()[x]);
				// listModel.addElement(modelUCARD.getRowCount());
			}
			totalUCARD(modelUCARD, modelUCARD_Total);
		} else {
			modelUCARD_Total.setValueAt(FncBigDecimal.zeroValue(), 0, 2);
		}
		// totalMonthlySOA(modelMonthlySOA, modelMonthlySOA_Total);
		tblUCard.packAll();
	}

	private static void totalUCARD(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amount = new BigDecimal("0.00");

		for (int x = 0; x < modelMain.getRowCount(); x++) {
			amount = amount.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 2) == null ? new BigDecimal("0.00")
					: modelMain.getValueAt(x, 2)));
		}
		modelTotal.setValueAt(amount, 0, 2);
	}

	//Done
	public static void displaySubdivisionDues(String entity_id, String proj_id, String pbl_id, String seq_no) {
		modelSubdivisionDues.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHeaderSubdivisionDues.setModel(listModel);

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_subdivision_dues('" + entity_id + "', '" + proj_id + "', '" + pbl_id
				+ "', " + seq_no + ")";
		db.select(SQL);
		FncSystem.out("SQL Subdivision Dues", SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelSubdivisionDues.addRow(db.getResult()[x]);
			}
			totalSubdivisionDues(modelSubdivisionDues, modelSubdivisionDuesTotal);
		} else {
			modelSubdivisionDuesTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 1);
			modelSubdivisionDuesTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 2);
		}
		tblSubdivisionDues.packAll();
	}

	private static void totalSubdivisionDues(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amt_due = new BigDecimal("0.00");
		BigDecimal amt_paid = new BigDecimal("0.00");
		for (int x = 0; x < modelMain.getRowCount(); x++) {
			amt_due = amt_due.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 1) == null ? new BigDecimal("0.00")
					: modelMain.getValueAt(x, 1)));
			amt_paid = amt_paid
					.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 2) == null ? new BigDecimal("0.00")
							: modelMain.getValueAt(x, 2)));
		}
		modelTotal.setValueAt(amt_due, 0, 1);
		modelTotal.setValueAt(amt_paid, 0, 2);
	}

	//Done
	public static void displayMonthlySOA(String entity_id, String proj_id, String pbl_id, String seq_no,
			Boolean other_lot) {
		modelMonthlySOA.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHeaderMonthlySOA.setModel(listModel);

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_monthly_soa_v2('" + entity_id + "', '" + proj_id + "', '" + pbl_id + "', "
				+ seq_no + ", " + other_lot + ")";
		db.select(SQL);

		FncSystem.out("SQL Monthly SOA", SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMonthlySOA.addRow(db.getResult()[x]);
			}

		} else {
			modelMonthlySOA_Total.setValueAt(FncBigDecimal.zeroValue(), 0, 9);
			modelMonthlySOA_Total.setValueAt(FncBigDecimal.zeroValue(), 0, 10);
		}
		totalMonthlySOA(modelMonthlySOA, modelMonthlySOA_Total);
		tblMonthlySOA.packAll();
	}

	private static void totalMonthlySOA(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amt_due = new BigDecimal("0.00");
		BigDecimal amt_paid = new BigDecimal("0.00");
		BigDecimal excess = new BigDecimal("0.00");
		
		lblUtilityCARD.setText(String.format("<html><center><b>This account has not been<br>issued with Utility CARD.<br><br>Outstanding Balance: <br>0.00</b></center></html>"));

		for (int x = 0; x < modelMain.getRowCount(); x++) {
			amt_due = amt_due.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 9) == null ? new BigDecimal("0.00") : modelMain.getValueAt(x, 9)));
			amt_paid = amt_paid.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 10) == null ? new BigDecimal("0.00") : modelMain.getValueAt(x, 10)));
		}
		modelTotal.setValueAt(amt_due, 0, 9);
		
		for(int x = 0; x < modelMain.getRowCount(); x++) {
			
			Date due_for = (Date) modelMain.getValueAt(x, 2);
			
			if (due_for == null) {
				excess = excess.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 10) == null ? new BigDecimal("0.00") : modelMain.getValueAt(x, 10)));
			}
		}
		// modelTotal.setValueAt(amt_paid, 0, 7);
		System.out.printf("Display value of amt paid: %s%n", amt_paid);
		System.out.printf("Display value of amt due: %s%n", amt_due);
		System.out.printf("Display value of excess: %s%n", excess);
		System.out.printf("Display value of compare: %s%n", amt_paid.compareTo(amt_due));
		System.out.printf("Display value of compare: %s%n", amt_paid.compareTo(amt_due));

		if(amt_paid.compareTo(amt_due) == -1 || amt_paid.compareTo(amt_due) == 0){
			if((amt_paid.subtract(amt_due)).compareTo(FncBigDecimal.zeroValue())== -1) {
				System.out.println("Dumaan dito sa if");
				modelTotal.setValueAt(String.format("[OB] %s", amt_paid.subtract(amt_due)),0, 10); 
				lblUtilityCARD.setText(String.format("<html><center><b>This account has not been<br>issued with Utility CARD.<br><br>Outstanding Balance:<br>%s</b></center></html>", amt_paid.subtract(amt_due.add(excess))));
			}else {
				modelTotal.setValueAt(String.format("[OB] %s", amt_paid.subtract(amt_due)),0, 10); 
				lblUtilityCARD.setText(String.format("<html><center><b>This account has not been<br>issued with Utility CARD.<br><br>Outstanding Balance:<br>%s</b></center></html>", amt_paid.subtract(amt_due)));
			}
		}else{
			modelTotal.setValueAt(String.format("[OB] %s", amt_paid.subtract(amt_due)),0, 10); 
			lblUtilityCARD.setText(String.format("<html><center><b>This account has not been<br>issued with Utility CARD.<br><br>Outstanding Balance:<br>%s</b></center></html>", amt_paid.subtract(amt_due))); 
		}
		
	}

	//Done
	public static void displayElectricity(String entity_id, String proj_id, String pbl_id, String seq_no) {
		modelElectricity.clear();
		DefaultListModel listModel = new DefaultListModel();
		rowHeaderElectricity.setModel(listModel);

		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_card_electricity('" + entity_id + "', '" + proj_id + "', '" + pbl_id + "', "
				+ seq_no + ")";
		db.select(SQL);

		FncSystem.out("SQL Electricity", SQL);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelElectricity.addRow(db.getResult()[x]);
			}
		} else {
			modelElectricityTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 5);
			modelElectricityTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 6);
			modelElectricityTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 7);
		}
		tblElectricity.packAll();
	}

	private static String GetPercentage(String strAmt1, String strAmt2) {
		String strPctg = "";

		strAmt1 = strAmt1.replace(",", "");
		strAmt2 = strAmt2.replace(",", "");

		strPctg = FncGlobal.GetString("select (case when ((1 - (('" + strAmt1 + "'::numeric(19, 2) - '" + strAmt2
				+ "'::numeric(19, 2)) / '" + strAmt1 + "'::numeric(19, 2))) * 100) >= 10 \n" + "then ((1 - (('"
				+ strAmt1 + "'::numeric(19, 2) - '" + strAmt2 + "'::numeric(19, 2)) / '" + strAmt1
				+ "'::numeric(19, 2))) * 100)::numeric(19, 1)::varchar || '%'::Varchar \n" + "else ((1 - (('" + strAmt1
				+ "'::numeric(19, 2) - '" + strAmt2 + "'::numeric(19, 2)) / '" + strAmt1
				+ "'::numeric(19, 2))) * 100)::numeric(19, 2) || '%'::Varchar end)");

		return strPctg;
	}
}
