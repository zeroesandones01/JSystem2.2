package Projects.SalesandMarketing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import interfaces._GUI;

public class pnlTripping extends JPanel implements _GUI,ActionListener,LookupListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tripping_Cost tc;
	public JPanel pnlMain;
	private JPanel pnlWest;
	private JPanel pnlCenter;
	private JLabel lblTotalHrs;
	private JXTextField txtTotalHrs;
	private JLabel lblTotalKM;
	private JXTextField txtTotalKM;
	private JLabel lblStandRate;
	private JLabel lblHrs;
	private _JXFormattedTextField txtHrs;
	private JLabel lblHrsExcess;
	private _JXFormattedTextField txtHrsExcess;
	private JLabel lblKMStand;
	private _JXFormattedTextField txtKMStand;
	private _JXFormattedTextField txtTotalStand;
	private JLabel lblTotalStand;
	private _JXFormattedTextField txtTotalExcess;
	private JLabel lblTotalExcess;
	private JPanel blank;
	private JLabel lblExcessKM;
	private _JXFormattedTextField txtExcessKM;
	private _JXFormattedTextField txtTotalExcessKM;
	private JLabel lblTotalExcessKM;
	private JButton btnCompute;
	public static _JXFormattedTextField txtTotal;
	private JLabel lblTotal;
	private JLabel lblRPLFNo;
	private JXTextField txtRPLF;
	private JLabel lblVehicle;
	private DefaultComboBoxModel cmbVehicleModel;
	private JComboBox cmbVehicle;
	private JLabel lblRateCode;
	private JLabel lblMinimumHrs;
	private JLabel lblMinimumKM;
	private JXTextField txtMinimumHrs;
	private JXTextField txtMinimumKM;
	private JLabel lblStandardRate;
	private JLabel lblExcessHrsRate;
	private JLabel lblExcessKMRate;
	private JXTextField txtStandardRate;
	private JXTextField txtExcessHrsRate;
	private JXTextField txtExcessKMRate;
	private Boolean is_cancelled = false;
	//private BigDecimal exHrPesos;
	//private BigDecimal exKMPesos;
	private String drivercode;
	private String rplf_no;
	private JLabel lblRPLFnoStatus;
	private String rplf_no_status;
	public JLabel lblFixed;
	private JLabel lblTollFee;
	private _JXFormattedTextField txtTotalTollfee;
	private JLabel lblTotalTollfee;
	private _JLookup lookupRateCode;
	private pgSelect db = new pgSelect();
	private BigDecimal total_cost;
	
	public pnlTripping(Tripping_Cost tc ) {
		this.tc = tc;
		initGUI();

		tc.setComponentsEnabled(pnlMain, false);
	}

	public pnlTripping() {
		initGUI();
	}

	public pnlTripping(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlTripping(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlTripping(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		
		{
			pnlMain = new JPanel();
			this.add(pnlMain,BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setLayout(new BorderLayout(3,3));
			{
				pnlWest = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				pnlWest.setBorder(components.JTBorderFactory.createTitleBorder("Cost"));
				pnlWest.setPreferredSize(new Dimension(500, 0));
				{
					JPanel pnlTotal = new  JPanel(new BorderLayout(3, 3));
					pnlWest.add(pnlTotal, BorderLayout.NORTH);
					pnlTotal.setPreferredSize(new Dimension(0, 25));
					{

						JPanel pnlTotalCenter = new JPanel(new GridLayout(1, 2, 3, 3));
						pnlTotal.add(pnlTotalCenter, BorderLayout.CENTER);
						{

							JPanel pnlHrs = new JPanel(new BorderLayout(3, 3));
							pnlTotalCenter.add(pnlHrs);
							{
								lblTotalHrs = new JLabel("Total Hrs :");
								pnlHrs.add(lblTotalHrs,BorderLayout.WEST);

							}
							{
								txtTotalHrs = new JXTextField("Hrs");
								pnlHrs.add(txtTotalHrs,BorderLayout.CENTER);
								txtTotalHrs.setHorizontalAlignment(JXTextField.CENTER);
								txtTotalHrs.setEditable(false);
							}

							JPanel pnlKM = new JPanel(new BorderLayout(3, 3));
							pnlTotalCenter.add(pnlKM);
							{
								lblTotalKM = new JLabel("Total KM :");
								pnlKM.add(lblTotalKM,BorderLayout.WEST);

							}
							{
								txtTotalKM = new JXTextField("KM");
								pnlKM.add(txtTotalKM,BorderLayout.CENTER);
								txtTotalKM.setHorizontalAlignment(JXTextField.CENTER);
								txtTotalKM.setEditable(false);
							}
						}
					}
					JPanel pnlComp = new  JPanel(new BorderLayout(3, 3));
					pnlWest.add(pnlComp, BorderLayout.CENTER);
					pnlComp.setPreferredSize(new Dimension(0, 25));
					{
						JPanel pnlLabel = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlComp.add(pnlLabel, BorderLayout.WEST);
						pnlLabel.setPreferredSize(new Dimension(110, 25));
						{
							lblStandRate = new JLabel("Standard Rate");
							pnlLabel.add(lblStandRate);

							lblStandRate = new JLabel("Excess Hours");
							pnlLabel.add(lblStandRate);

							lblStandRate = new JLabel("Excess KM");
							pnlLabel.add(lblStandRate);
							
							lblTollFee = new JLabel("Toll Fee");
							pnlLabel.add(lblTollFee);
						}
						JPanel pnlText = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlComp.add(pnlText, BorderLayout.CENTER);

						{
							JPanel pnlHrsStand = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlHrsStand);
							{
								JPanel pnlHrsLabel = new JPanel(new BorderLayout(3, 3));
								pnlHrsStand.add(pnlHrsLabel,BorderLayout.WEST);
								pnlHrsLabel.setPreferredSize(new Dimension(105, 25));
								{
									lblHrs = new JLabel("Hrs");
									pnlHrsLabel.add(lblHrs,BorderLayout.WEST);
									lblHrs.setPreferredSize(new Dimension(25, 25));

									txtHrs = new _JXFormattedTextField("Hrs");
									pnlHrsLabel.add(txtHrs,BorderLayout.CENTER);
									txtHrs.setPreferredSize(new Dimension(120, 25));
									txtHrs.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
									txtHrs.setEditable(false);
								}

								JPanel pnlKMLabel = new JPanel(new BorderLayout(3, 3));
								pnlHrsStand.add(pnlKMLabel,BorderLayout.CENTER);
								pnlKMLabel.setPreferredSize(new Dimension(110, 25));
								{
									lblKMStand = new JLabel("KM");
									pnlKMLabel.add(lblKMStand,BorderLayout.WEST);
									lblKMStand.setPreferredSize(new Dimension(25, 25));

									txtKMStand = new _JXFormattedTextField("KM");
									pnlKMLabel.add(txtKMStand,BorderLayout.CENTER);
									txtKMStand.setPreferredSize(new Dimension(50, 25));
									txtKMStand.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
									txtKMStand.setEditable(false);
								}

								JPanel pnlTotalStand = new JPanel(new BorderLayout(3, 3));
								pnlHrsStand.add(pnlTotalStand,BorderLayout.EAST);
								pnlTotalStand.setPreferredSize(new Dimension(150, 25));
								{

									txtTotalStand = new _JXFormattedTextField("");
									pnlTotalStand.add(txtTotalStand,BorderLayout.WEST);
									txtTotalStand.setPreferredSize(new Dimension(100, 25));
									txtTotalStand.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
									txtTotalStand.setEditable(false);

									lblTotalStand = new JLabel("pesos");
									pnlTotalStand.add(lblTotalStand,BorderLayout.CENTER);
									lblTotalStand.setPreferredSize(new Dimension(25, 25));

								}
							}

							JPanel pnlHrsExcess= new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlHrsExcess);
							{
								JPanel pnlHrsLabelEx = new JPanel(new BorderLayout(3, 3));
								pnlHrsExcess.add(pnlHrsLabelEx,BorderLayout.WEST);
								pnlHrsLabelEx.setPreferredSize(new Dimension(105, 25));
								{
									lblHrsExcess = new JLabel("Hrs");
									pnlHrsLabelEx.add(lblHrsExcess,BorderLayout.WEST);
									lblHrsExcess.setPreferredSize(new Dimension(25, 25));

									txtHrsExcess = new _JXFormattedTextField("Hrs");
									pnlHrsLabelEx.add(txtHrsExcess,BorderLayout.CENTER);
									txtHrsExcess.setPreferredSize(new Dimension(50, 25));
									txtHrsExcess.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
									txtHrsExcess.setEditable(false);
								}

								JPanel pnlKMLabel = new JPanel(new BorderLayout(3, 3));
								pnlHrsExcess.add(pnlKMLabel,BorderLayout.CENTER);
								pnlKMLabel.setPreferredSize(new Dimension(110, 25));
								{
									JLabel lblKMStand = new JLabel("KM");
									pnlKMLabel.add(lblKMStand,BorderLayout.WEST);
									lblKMStand.setPreferredSize(new Dimension(25, 25));
									lblKMStand.setVisible(false);

									_JXFormattedTextField txtKMStand = new _JXFormattedTextField("KM");
									pnlKMLabel.add(txtKMStand,BorderLayout.CENTER);
									txtKMStand.setPreferredSize(new Dimension(50, 25));
									txtKMStand.setVisible(false);
									txtKMStand.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
									txtKMStand.setEditable(false);
								}

								JPanel pnlTotalExcess = new JPanel(new BorderLayout(3, 3));
								pnlHrsExcess.add(pnlTotalExcess,BorderLayout.EAST);
								pnlTotalExcess.setPreferredSize(new Dimension(150, 25));
								{

									txtTotalExcess = new _JXFormattedTextField("");
									pnlTotalExcess.add(txtTotalExcess,BorderLayout.WEST);
									txtTotalExcess.setPreferredSize(new Dimension(100, 25));
									txtTotalExcess.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
									txtTotalExcess.setEditable(false);

									lblTotalExcess = new JLabel("pesos");
									pnlTotalExcess.add(lblTotalExcess,BorderLayout.CENTER);
									lblTotalExcess.setPreferredSize(new Dimension(25, 25));


								}
							}

							JPanel pnl = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnl);
							{
								blank = new JPanel(new BorderLayout(3, 3));
								pnl.add(blank,BorderLayout.WEST);
								blank.setPreferredSize(new Dimension(105, 25));
								{
									JLabel lblKMs = new JLabel("KM");
									blank.add(lblKMs,BorderLayout.WEST);
									lblKMs.setPreferredSize(new Dimension(25, 25));
									lblKMs.setVisible(false);

									_JXFormattedTextField txtKMs = new _JXFormattedTextField("KM");
									blank.add(txtKMs,BorderLayout.CENTER);
									txtKMs.setPreferredSize(new Dimension(50, 25));
									txtKMs.setVisible(false);
									txtKMs.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
									txtKMs.setEditable(false);
								}

								JPanel pnlKMLabel = new JPanel(new BorderLayout(3, 3));
								pnl.add(pnlKMLabel,BorderLayout.CENTER);
								pnlKMLabel.setPreferredSize(new Dimension(110, 25));
								{
									lblExcessKM = new JLabel("KM");
									pnlKMLabel.add(lblExcessKM,BorderLayout.WEST);
									lblExcessKM.setPreferredSize(new Dimension(25, 25));

									txtExcessKM = new _JXFormattedTextField("KM");
									pnlKMLabel.add(txtExcessKM,BorderLayout.CENTER);
									txtExcessKM.setPreferredSize(new Dimension(50, 25));
									txtExcessKM.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
									txtExcessKM.setEditable(false);
								}

								JPanel pnlTotalExcessKM = new JPanel(new BorderLayout(3, 3));
								pnl.add(pnlTotalExcessKM,BorderLayout.EAST);
								pnlTotalExcessKM.setPreferredSize(new Dimension(150, 25));
								{

									txtTotalExcessKM = new _JXFormattedTextField("");
									pnlTotalExcessKM.add(txtTotalExcessKM,BorderLayout.WEST);
									txtTotalExcessKM.setPreferredSize(new Dimension(100, 25));
									txtTotalExcessKM.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
									txtTotalExcessKM.setEditable(false);

									lblTotalExcessKM = new JLabel("pesos");
									pnlTotalExcessKM.add(lblTotalExcessKM,BorderLayout.CENTER);
									lblTotalExcessKM.setPreferredSize(new Dimension(25, 25));
								}
							}
							
							JPanel pnlTollFee = new JPanel(new BorderLayout(3, 3));
							pnlText.add(pnlTollFee);
							{
								JPanel pnlHrsLabelEx = new JPanel(new BorderLayout(3, 3));
								pnlTollFee.add(pnlHrsLabelEx,BorderLayout.WEST);
								pnlHrsLabelEx.setPreferredSize(new Dimension(105, 25));
								{
									JLabel lblHrsExcess = new JLabel("Hrs");
									pnlHrsLabelEx.add(lblHrsExcess,BorderLayout.WEST);
									lblHrsExcess.setPreferredSize(new Dimension(25, 25));
									lblHrsExcess.setVisible(false);

									_JXFormattedTextField txtHrsExcess = new _JXFormattedTextField("Hrs");
									pnlHrsLabelEx.add(txtHrsExcess,BorderLayout.CENTER);
									txtHrsExcess.setPreferredSize(new Dimension(50, 25));
									txtHrsExcess.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
									txtHrsExcess.setVisible(false);
								}

								JPanel pnlKMLabel = new JPanel(new BorderLayout(3, 3));
								pnlTollFee.add(pnlKMLabel,BorderLayout.CENTER);
								pnlKMLabel.setPreferredSize(new Dimension(110, 25));
								{
									JLabel lblKMStand = new JLabel("KM");
									pnlKMLabel.add(lblKMStand,BorderLayout.WEST);
									lblKMStand.setPreferredSize(new Dimension(25, 25));
									lblKMStand.setVisible(false);

									_JXFormattedTextField txtKMStand = new _JXFormattedTextField("KM");
									pnlKMLabel.add(txtKMStand,BorderLayout.CENTER);
									txtKMStand.setPreferredSize(new Dimension(50, 25));
									txtKMStand.setVisible(false);
									txtKMStand.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
								}

								JPanel pnlTotalExcess = new JPanel(new BorderLayout(3, 3));
								pnlTollFee.add(pnlTotalExcess,BorderLayout.EAST);
								pnlTotalExcess.setPreferredSize(new Dimension(150, 25));
								{

									txtTotalTollfee = new _JXFormattedTextField("");
									pnlTotalExcess.add(txtTotalTollfee,BorderLayout.WEST);
									txtTotalTollfee.setPreferredSize(new Dimension(100, 25));
									txtTotalTollfee.setHorizontalAlignment(_JXFormattedTextField.RIGHT);

									lblTotalTollfee = new JLabel("pesos");
									pnlTotalExcess.add(lblTotalTollfee,BorderLayout.CENTER);
									lblTotalTollfee.setPreferredSize(new Dimension(25, 25));


								}
							}
						}


					}
					JPanel pnlCompSouth = new  JPanel(new BorderLayout(3, 3));
					pnlWest.add(pnlCompSouth, BorderLayout.SOUTH);
					pnlCompSouth.setPreferredSize(new Dimension(0, 50));

					{

						JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
						pnlCompSouth.add(pnlNorth,BorderLayout.NORTH);
						pnlNorth.setPreferredSize(new Dimension(150, 25));
						{
							btnCompute = new  JButton("Compute Tripping Cost =>");
							pnlNorth.add(btnCompute, BorderLayout.WEST);
							btnCompute.addActionListener(this);
							btnCompute.setEnabled(true);

						}

						JPanel pnlGrandTotal = new JPanel(new BorderLayout(3, 3));
						pnlNorth.add(pnlGrandTotal,BorderLayout.EAST);
						pnlGrandTotal.setPreferredSize(new Dimension(150, 25));
						{

							txtTotal = new _JXFormattedTextField("");
							pnlGrandTotal.add(txtTotal,BorderLayout.WEST);
							txtTotal.setPreferredSize(new Dimension(100, 25));
							txtTotal.setHorizontalAlignment(_JXFormattedTextField.RIGHT);
							txtTotal.setFormatterFactory(_JXFormattedTextField.DECIMAL);

							lblTotal= new JLabel("pesos");
							pnlGrandTotal.add(lblTotal,BorderLayout.CENTER);
							lblTotal.setPreferredSize(new Dimension(25, 25));


						}


						JPanel pnlCenter = new JPanel(new BorderLayout(3, 3));
						pnlCompSouth.add(pnlCenter,BorderLayout.CENTER);
						pnlCenter.setPreferredSize(new Dimension(150, 25));
						{


							lblRPLFNo = new JLabel("RPLF No.");
							pnlCenter.add(lblRPLFNo,BorderLayout.WEST);
							//lblRPLFNo.setPreferredSize(new Dimension(100, 25));
							{

								JPanel pnlRPLF = new JPanel(new BorderLayout(3, 3));
								pnlCenter.add(pnlRPLF,BorderLayout.CENTER);
								{
									txtRPLF = new JXTextField("");
									pnlRPLF.add(txtRPLF,BorderLayout.WEST);
									txtRPLF.setPreferredSize(new Dimension(100, 25));
									txtRPLF.setHorizontalAlignment(JXTextField.RIGHT);


									lblRPLFnoStatus = new JLabel("[ ]");
									pnlRPLF.add(lblRPLFnoStatus,BorderLayout.CENTER);
									lblRPLFnoStatus.setPreferredSize(new Dimension(25, 25));

								}
							}
						}
					}
				}

			}
			{
				pnlCenter = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Tripping Info"));
				pnlCenter.setPreferredSize(new Dimension(500, 0));
				{
					JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new Dimension(0, 30));
					{
						lblVehicle = new JLabel("Vehicle Make/Brand");
						pnlNorth.add(lblVehicle, BorderLayout.WEST);


						cmbVehicleModel = new DefaultComboBoxModel(new String[] {""});
						cmbVehicle = new JComboBox();
						pnlNorth.add(cmbVehicle,BorderLayout.CENTER);
						cmbVehicle.setModel(cmbVehicleModel);
						cmbVehicle.setSelectedItem(null);
					}

					JPanel pnlRate = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlRate,BorderLayout.CENTER);
					{
						JPanel pnlWest = new JPanel(new BorderLayout(3, 3));
						pnlRate.add(pnlWest,BorderLayout.WEST);
						pnlWest.setPreferredSize(new Dimension(290, 0));
						{
							JPanel pnlLabel = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlWest.add(pnlLabel,BorderLayout.WEST);
							{

								lblRateCode = new JLabel("Rate Code");
								pnlLabel.add(lblRateCode);

								lblMinimumHrs = new JLabel("Minimum Hours");
								pnlLabel.add(lblMinimumHrs);

								lblMinimumKM = new JLabel("Minimum Kilometers");
								pnlLabel.add(lblMinimumKM);
							}

							JPanel pnlText = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlWest.add(pnlText,BorderLayout.CENTER);
							pnlText.setPreferredSize(new Dimension(50, 0));
							{

								JPanel pnlFixedRate = new JPanel(new BorderLayout(3, 3));
								pnlText.add(pnlFixedRate,BorderLayout.CENTER);
								{

									lookupRateCode = new _JLookup("", "Rate Code", 0) ; /// XXX lookupRateCode 
									pnlFixedRate.add(lookupRateCode,BorderLayout.WEST);
									lookupRateCode.setLookupSQL(getRateCode());
									lookupRateCode.setPreferredSize(new Dimension(70, 25));
									lookupRateCode.setReturnColumn(0);
									lookupRateCode.addLookupListener(this);
								
									
									lblFixed = new JLabel("[ FIXED RATE ]");
									pnlFixedRate.add(lblFixed,BorderLayout.CENTER);
									lblFixed.setVisible(false);
									lblFixed.setForeground(Color.RED);
								}
								

								txtMinimumHrs = new JXTextField("Minimum Hours");
								pnlText.add(txtMinimumHrs);
								txtMinimumHrs.setHorizontalAlignment(JXTextField.RIGHT);

								txtMinimumKM = new JXTextField("Minimum Kilometers");
								pnlText.add(txtMinimumKM);
								txtMinimumKM.setHorizontalAlignment(JXTextField.RIGHT);
							}
						}
					}
					{

						JPanel pnlEast = new JPanel(new BorderLayout(3, 3));
						pnlRate.add(pnlEast,BorderLayout.EAST);
						pnlEast.setPreferredSize(new Dimension(230, 0));
						{
							JPanel pnlLabel = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlEast.add(pnlLabel,BorderLayout.WEST);
							{

								lblStandardRate = new JLabel("Standard Rate");
								pnlLabel.add(lblStandardRate);

								lblExcessHrsRate = new JLabel("Excess Hour Rate");
								pnlLabel.add(lblExcessHrsRate);

								lblExcessKMRate = new JLabel("Excess Kilometer Rate");
								pnlLabel.add(lblExcessKMRate);
							}

							JPanel pnlText = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlEast.add(pnlText,BorderLayout.CENTER);
							{

								txtStandardRate = new JXTextField("Standard Rate");
								pnlText.add(txtStandardRate);
								txtStandardRate.setHorizontalAlignment(JXTextField.RIGHT);

								txtExcessHrsRate = new JXTextField("Excess Hour Rate");
								pnlText.add(txtExcessHrsRate);
								txtExcessHrsRate.setHorizontalAlignment(JXTextField.RIGHT);

								txtExcessKMRate = new JXTextField("Excess Kilometer Rate");
								pnlText.add(txtExcessKMRate);
								txtExcessKMRate.setHorizontalAlignment(JXTextField.RIGHT);
							}
						}

					}
				}
			}
		}
	}


	public void setTrippingDetails(Object [] data){

		String tripcode = (String) data[0];
		BigDecimal standard_rate = (BigDecimal) data[1];
		BigDecimal min_hrs = (BigDecimal) data[2];
		BigDecimal min_km = (BigDecimal) data[3];
		BigDecimal excess_hr_rate = (BigDecimal) data[4];
		BigDecimal excess_km_rate = (BigDecimal) data[5];

		BigDecimal total_hrs = (BigDecimal) data[6];
		BigDecimal total_km = (BigDecimal) data[7];

	//	BigDecimal excess_hrs_no = (BigDecimal) data[8];
	//	BigDecimal excess_hrs_pesos = (BigDecimal) data[9];
	//	BigDecimal excess_km_no = (BigDecimal) data[10];
	//	BigDecimal excess_km_pesos = (BigDecimal) data[11];
		Boolean cancelled_rate = (Boolean) data[12];
		BigDecimal grand_total = (BigDecimal) data[18];

		/*		= data[0] == null ? null : data[0].toString();
		proguide = data[1] == null ? null : data[1].toString();
		internal = data[2] == null ? null : data[2].toString();
		defterms= data[3] == null ? null : data[3].toString();
		effectdata = (Date) ((Date) data[4]  == null ? null : data[4]);
		approval = data[5] == null ? null : data[5].toString();
		attach = data[6] == null ? null : data[6].toString();
		 */
		lookupRateCode.setValue(tripcode);
		txtMinimumHrs.setText(String.valueOf(min_hrs));
		txtMinimumKM.setText(String.valueOf(min_km));

		txtStandardRate.setText(String.valueOf(standard_rate));
		txtExcessHrsRate.setText(String.valueOf(excess_hr_rate));
		txtExcessKMRate.setText(String.valueOf(excess_km_rate));

		txtHrs.setValue(min_hrs);
		txtKMStand.setValue(min_km);
		txtTotalStand.setValue(standard_rate);

		txtTotalKM.setText(String.valueOf(total_km));
		this.is_cancelled = cancelled_rate;
		drivercode = (String)data[13];
		
		getVechile(drivercode);
		
		rplf_no = (String) (data[14] == null ? "" :data[14]);
		rplf_no_status = (String)data[15];
		BigDecimal toll_fee = (BigDecimal) data[16];
		
		txtTotalTollfee.setValue(FncBigDecimal.format(toll_fee));
		
		if (rplf_no.equals("")|| rplf_no == null) {
			lblRPLFnoStatus.setText("[ ]");
			txtRPLF.setText("");
		}else{
			txtRPLF.setText(rplf_no);
			lblRPLFnoStatus.setText(rplf_no_status);
		}
		
		txtTotalHrs.setText(String.valueOf(total_hrs));
		
		if (grand_total.equals(0.00)) {
			System.out.println("*************COMPUTE 11 " + total_hrs);
			if (!txtTotalHrs.getText().equals("") || txtTotalHrs.getText() == null) {
				
				
				System.out.println("*************COMPUTE");
				ComputeCost();	
			}
		}else{
			txtTotal.setValue(grand_total);
		}
		
	}

	public void setTotalHrs(String totalHrs){

		txtTotalHrs.setText(totalHrs);
	}

	public void setTotalKM(String totalKM,Boolean is_cancelled){

		if (is_cancelled == false) {
			txtTotalKM.setText(totalKM);
		}else{
			txtTotalKM.setText(totalKM);
			txtExcessKM.setValue(totalKM);
		}
	}

	public Object[] getTotalCost() {
		
		
		return new Object[]{
				total_cost	
		};
	}
	
	public Object[] getData() {//GETS THE DATA 
		
		String plate_no = cmbVehicle.getSelectedItem().toString().split("-")[0];
		String make_brand = cmbVehicle.getSelectedItem().toString().split("-")[1];
		
		BigDecimal totalcost = new BigDecimal(txtTotal.getText().replace(",", ""));
		
		
		BigDecimal hr_excess = new BigDecimal(txtHrsExcess.getText().replace(",", ""));
		BigDecimal hr_excess_total = new BigDecimal(txtTotalExcess.getText().replace(",", ""));
		BigDecimal excess_km = new BigDecimal(txtExcessKM.getText().replace(",", ""));
		BigDecimal total_excess_km = new BigDecimal(txtTotalExcessKM.getText().replace(",", ""));
		
		
		return new Object[]{
				txtTotalHrs.getText(),//0
				txtTotalKM.getText(),//1
				txtHrs.getValue(),//2
				txtKMStand.getValue(),//3
				txtTotalStand.getValue(),//4
				hr_excess,//5
				hr_excess_total,//6
				excess_km,//7
				total_excess_km,//8
				totalcost,//9
				plate_no,//10
				make_brand,//11
				lookupRateCode.getText(),//12
				txtMinimumHrs.getText(),//13
				txtMinimumKM.getText(),//14
				txtExcessHrsRate.getText(),//15
				txtExcessKMRate.getText(),//16
				txtTotalTollfee.getText()//17
				
		};  
	}

	private Boolean forExcess(String totalhrs, BigDecimal min){
		pgSelect db = new pgSelect();

		db.select("SELECT "+totalhrs+" > "+min+"");

		return (Boolean) db.Result[0][0];

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnCompute)) {
			ComputeCost();

		}
	} 

	private void ComputeCost(){
		
	  /*if ((tc.getModelListSellingUnit().getRowCount() == 0 && tc.getModelListSalesGrp().getRowCount() == 0)) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please setup authority to deduct/group charging.", "Compute", JOptionPane.WARNING_MESSAGE);
			return;
		}*/
		
		BigDecimal min_hrs;
		BigDecimal min_km;
		BigDecimal standrate;
		BigDecimal totalhr;
		BigDecimal exHr_Rate;
		BigDecimal totalkm;
		BigDecimal exkm_Rate;
		BigDecimal toll_fee;
		BigDecimal exHrPesos = new BigDecimal(0.00);
		BigDecimal exKMPesos = new BigDecimal(0.00);
		BigDecimal exHr = new BigDecimal(0.00);
		
			min_hrs = new BigDecimal(txtHrs.getText());
			min_km = new BigDecimal(txtKMStand.getText());

			standrate = (BigDecimal) txtTotalStand.getValued();
			totalhr = new BigDecimal(txtTotalHrs.getText());
			exHr_Rate = new BigDecimal(txtExcessHrsRate.getText());

			totalkm = new BigDecimal(txtTotalKM.getText());
			exkm_Rate = new BigDecimal(txtExcessKMRate.getText());
			toll_fee = new BigDecimal(txtTotalTollfee.getText());
			
			
			
			

		/**for excess hrs*/
		if (forExcess(txtTotalHrs.getText(),min_hrs)) {
			System.out.println("**********" +forExcess(txtTotalHrs.getText(),min_hrs));

			exHr = totalhr.subtract(min_hrs);
			 exHrPesos = exHr.multiply(exHr_Rate);
			 
			
		}
		
		txtHrsExcess.setValue(exHr);
		txtTotalExcess.setValue(exHrPesos);

		/**for excess km*/
		if (!is_cancelled) {
			if (forExcess(txtTotalKM.getText(),min_km)) {

				
				BigDecimal exKM = new BigDecimal(0.00);
				exKM = totalkm.subtract(min_km);
			    exKMPesos = exKM.multiply(exkm_Rate);

			   
				txtExcessKM.setValue(exKM);
				txtTotalExcessKM.setValue(exKMPesos);
			}else{
				
				txtExcessKM.setValue(0.00);
				txtTotalExcessKM.setValue(0.00);
			}
			
		}

		/**for total cost*/
		System.out.println("*************exhr" + txtHrsExcess.getText());
		System.out.println("*************COMPUTE" + standrate+"***"+exHrPesos+"*****"+exKMPesos);
		//txtTotal = Format(Conv2Cur(txt_StPesos) + Conv2Cur(txt_ExHrPesos) + Conv2Cur(txt_ExKMPesos), "#,##0.00")*/
		total_cost =  TotalCost(standrate, exHrPesos, exKMPesos,toll_fee);
		txtTotal.setValue(total_cost);
		//tc.setDivideTotal((BigDecimal) txtTotal.getValued());
		
	}

	private BigDecimal TotalCost(BigDecimal totastand ,BigDecimal exHrsPesos, BigDecimal exKMPesos,BigDecimal tollfee ){
		pgSelect db = new pgSelect();
		
		String SQL ="SELECT (COALESCE("+totastand+",0.00) + COALESCE("+exHrsPesos+",0.00) + COALESCE("+exKMPesos+",0.00) + COALESCE("+tollfee+",0.00) )::numeric"; 
		
		
		db.select(SQL);
		
		System.out.println("SQL  " + SQL);
		
		return (BigDecimal) db.Result[0][0];

	}

	private void getVechile(String driver_code){
		
		pgSelect db = new pgSelect();
		List<String> list = new ArrayList<String>();
		db.select("select plate_no ||' - '|| make_brand  from rf_tripticket_driver_vehicles where entity_id = '"+driver_code+"' and status_id = 'A'");
		
		for (int i = 0; i < db.getRowCount(); i++) {
			list.add((String) db.Result[i][0]);
		}
		
		
		String[] array = list.toArray(new String[list.size()]) ;                           
		cmbVehicleModel = new DefaultComboBoxModel(array);
		cmbVehicle.setModel(cmbVehicleModel);
	}
	
	public void getVechileClear(){
		cmbVehicleModel = new DefaultComboBoxModel(new String[] {""});
		cmbVehicle.setModel(cmbVehicleModel);
		
	}

	@Override
	public void lookupPerformed(LookupEvent e) {
		
		if (e.getSource().equals(lookupRateCode)) { //XXX lookupRateCode
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){
				
				txtTotalHrs.setText("0.00");
				txtTotalKM.setText("0.00");
				txtTotal.setValue(0.00);
				txtTotalTollfee.setValue(0.00);
				
				String ratecode = (String) data[0];
				BigDecimal standard_rate = (BigDecimal) data[1];
				BigDecimal min_hrs = (BigDecimal) data[2];
				BigDecimal min_km = (BigDecimal) data[3];
				BigDecimal excess_hr_rate = (BigDecimal) data[4];
				BigDecimal excess_km_rate = (BigDecimal) data[5];
				Boolean cancelled_rate =  (Boolean) data[6];
				Boolean fixed_rate =  (Boolean) data[7];
				
				lookupRateCode.setValue(ratecode);
				
				txtMinimumHrs.setText(String.valueOf(min_hrs));
				txtMinimumKM.setText(String.valueOf(min_km));

				txtStandardRate.setText(String.valueOf(standard_rate));
				txtExcessHrsRate.setText(String.valueOf(excess_hr_rate));
				txtExcessKMRate.setText(String.valueOf(excess_km_rate));

				txtHrs.setValue(min_hrs);
				txtKMStand.setValue(min_km);
				txtTotalStand.setValue(standard_rate);
				
				lblFixed.setVisible(fixed_rate);
				btnCompute.setEnabled(!fixed_rate);
				txtTotalTollfee.setEditable(!fixed_rate);
				
				if (cancelled_rate == true) {
					
					tc.toChangeKM(cancelled_rate);
					txtTotalKM.setText(null);
					
					String timeFrom = new SimpleDateFormat("hh:mm:ss aa").format(tc.getSpnrFrom().getValue());
					String timeTo = new SimpleDateFormat("hh:mm:ss aa").format(tc.getSpnrTo().getValue());

					db.select("SELECT (DATE_PART('hour', '"+timeTo+"'::time - '"+timeFrom+"'::time) * 60 +  DATE_PART('minute', '"+timeTo+"'::time - '"+timeFrom+"'::time)) / 60;");

					txtHrsExcess.setValue(db.Result[0][0]);
					
				}else{
					
					txtHrsExcess.setValue(0.00);
					txtTotalExcess.setValue(0.00);
					txtExcessKM.setValue(0.00);
					txtTotalExcessKM.setValue(0.00);
					txtTotal.setValue(0.00);
					
				}
			}
		}
	}
	
	private String getRateCode(){
		
		String SQL = "SELECT a.rate_code,\n" + 
				"COALESCE(a.standard_rate,0.00) as standard_rate,\n" + 
				"COALESCE(a.min_hrs,0.00) as min_hrs, \n" + 
				"COALESCE(a.min_km,0.00) as min_km,\n" + 
				"COALESCE(a.excess_hr_rate,0.00) as excess_hr_rate,\n" + 
				"COALESCE(a.excess_km_rate,0.00) as excess_km_rate, \n" + 
				"(case COALESCE(a.cancelled_rate,false) when false then false else true end) as cancel_rate, \n" + 
				"(case COALESCE(a.fixed_rate,false) when false then false else true end) as fixed_rate \n" + 
				"FROM mf_tripping_rate A \n" + 
				"where a.status_id = 'A'\n" + 
				"order by rate_code";
		
		return SQL;
		
	}

	
}
