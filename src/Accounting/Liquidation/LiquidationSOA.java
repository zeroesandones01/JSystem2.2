package Accounting.Liquidation;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.JRadioButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import tablemodel.modelLiquidationSOA_2;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class LiquidationSOA extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Liquidation SOA";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	private JPanel pnlLiqSOADtl_1;
	private JPanel pnlLiqSOADtl_2;
	private JPanel pnlLiqSOADtl_1a;
	private JPanel pnlLiqSOADtl_1b;
	private JPanel pnlTable;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlLiqSOA;
	private JPanel pnlLiqSOA_part;
	private JPanel pnlLiqSOADtl;
	private JPanel pnlLiqSOAInfo;
	private JPanel pnlLiqSOAInfo_1;
	private JPanel pnlLiqSOADtl_2a;
	private JPanel pnlSouthCenterb;
	private JPanel pnlDeptDivAlias;
	private JPanel pnlDeptDivAlias1;

	private JLabel lblCompany;
	private static JLabel lblDivision;
	private static JLabel lblAvailerType;
	private static JLabel lblDepartment;
	private static JLabel lblAvailer;

	static _JLookup lookupCompany;
	static _JLookup lookupAvailerType;
	static _JLookup lookupDiv;
	static _JLookup lookupDep;
	static _JLookup lookupAvailer;

	private static _JScrollPaneMain scrollLiq_part;
	private static _JScrollPaneTotal scrollLiq_part_total;

	private static modelLiquidationSOA_2 modelLiq_part;
	private static modelLiquidationSOA_2 modelLiq_part_total;

	private static _JTableTotal tblLiq_part_total;	
	private static _JTableMain tblLiq_part;	

	private static JList rowHeaderLiq_part;
	private _JTabbedPane tabCenter;	

	static _JTagLabel tagCompany;
	private static _JTagLabel tagDivision;
	private static _JTagLabel tagDepartment;
	private static _JTagLabel tagAvailerType;
	private static _JTagLabel tagAvailer;	

	private static JButton btnCancel;
	private static JButton btnGenerateSelected;
	private static JButton btnGenerateAll;
	private static JButton btnPreviewSelected;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JDateChooser dteCutoff;

	private JComboBox cmbYear;
	private JComboBox cmbDueDays;
	private static JRadioButton rbtnDueDays;
	private static JRadioButton rbtnAll;
	private static JRadioButton rbtnCutoffDate;
	private static JRadioButton rbtnYear;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);	

	static String co_id = "";	
	static String company = "";
	static String company_logo = "";	
	static String for_atd = "no";	
	String overdue_deduction;
	private JPanel pnlForATD;
	private JPanel pnlForATD_a;
	private JPanel pnlForATD_b;
	private static JButton btnPreviewSelectedAll;
	private static JRadioButton rbtnForATD;

	public LiquidationSOA() {
		//super(title, true, true, true, true);
		super(title, true, true, false, false);
		initGUI();
	}

	public LiquidationSOA(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

//	public LiquidationSOA(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
//		super(title, resizable, closable, maximizable, iconifiable);
//		// TODO Auto-generated constructor stub
//	}

	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(935, 586));
		this.setBounds(0, 0, 935, 586);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlLiqSOA = new JPanel();
			pnlTable.add(pnlLiqSOA, BorderLayout.CENTER);
			pnlLiqSOA.setLayout(new BorderLayout(5, 5));
			pnlLiqSOA.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlLiqSOA.setBorder(lineBorder);		

			tabCenter = new _JTabbedPane();
			pnlLiqSOA.add(tabCenter, BorderLayout.CENTER);
			{			
				pnlLiqSOA_part = new JPanel(new BorderLayout());
				tabCenter.addTab("SOA LIST", null, pnlLiqSOA_part, null);
				pnlLiqSOA_part.setPreferredSize(new java.awt.Dimension(1183, 365));					

				{
					scrollLiq_part = new _JScrollPaneMain();
					pnlLiqSOA_part.add(scrollLiq_part, BorderLayout.CENTER);
					{
						modelLiq_part = new modelLiquidationSOA_2();

						tblLiq_part = new _JTableMain(modelLiq_part);
						scrollLiq_part.setViewportView(tblLiq_part);
						tblLiq_part.getColumnModel().getColumn(1).setPreferredWidth(100);
						tblLiq_part.getColumnModel().getColumn(2).setPreferredWidth(300);

						tblLiq_part.addMouseListener(this);	
//						tblLiq_part.addMouseListener(new MouseAdapter() {
//							public void mousePressed(MouseEvent e) {
//								if(tblLiq_part.rowAtPoint(e.getPoint()) == -1){
//									tblLiq_part_total.clearSelection();
//								}else{
//									tblLiq_part.setCellSelectionEnabled(true);
//								}
//							}
//							public void mouseReleased(MouseEvent e) {
//								if(tblLiq_part.rowAtPoint(e.getPoint()) == -1){
//									tblLiq_part_total.clearSelection();
//								}else{
//									tblLiq_part.setCellSelectionEnabled(true);
//								}
//							}
//						});
						tblLiq_part.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							
							@Override
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){
								enableButtons(false, false, true, true, false);
								}
							}

							
						});
					}
					{
						rowHeaderLiq_part = tblLiq_part.getRowHeader22();
						scrollLiq_part.setRowHeaderView(rowHeaderLiq_part);
						scrollLiq_part.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollLiq_part_total = new _JScrollPaneTotal(scrollLiq_part);
					pnlLiqSOA_part.add(scrollLiq_part_total, BorderLayout.SOUTH);
					{
						modelLiq_part_total = new modelLiquidationSOA_2();
						modelLiq_part_total.addRow(new Object[] { "", "Total", null, null, new BigDecimal(0.00)});

						tblLiq_part_total = new _JTableTotal(modelLiq_part_total, tblLiq_part);
						tblLiq_part_total.setFont(dialog11Bold);
						scrollLiq_part_total.setViewportView(tblLiq_part_total);
						((_JTableTotal) tblLiq_part_total).setTotalLabel(1);
					}
				}

			}
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(923, 35));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{
				{
					btnGenerateSelected = new JButton("Generate Selected");
					pnlSouthCenterb.add(btnGenerateSelected);
					btnGenerateSelected.setActionCommand("GenerateSelected");
					btnGenerateSelected.addActionListener(this);
					btnGenerateSelected.setEnabled(false);
				}
				{
					btnGenerateAll = new JButton("Generate All Unliquidated");
					pnlSouthCenterb.add(btnGenerateAll);
					btnGenerateAll.setActionCommand("GenerateAll");
					btnGenerateAll.addActionListener(this);
					btnGenerateAll.setEnabled(false);
				}
				{
					btnPreviewSelected= new JButton("Preview");
					pnlSouthCenterb.add(btnPreviewSelected);
					btnPreviewSelected.setActionCommand("PreviewSelected");
					btnPreviewSelected.addActionListener(this);
					btnPreviewSelected.setEnabled(false);
				}
				{
					btnPreviewSelectedAll= new JButton("Preview All");
					pnlSouthCenterb.add(btnPreviewSelectedAll);
					btnPreviewSelectedAll.setActionCommand("PreviewAll");
					btnPreviewSelectedAll.addActionListener(this);
					btnPreviewSelectedAll.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}			
		}
		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(0,0));
			pnlNorth.setBorder(lineBorder);		
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 190));

			pnlComp = new JPanel(new BorderLayout(0,0));
			pnlNorth.add(pnlComp, BorderLayout.NORTH);	

			{
				//company
				pnlComp_a = new JPanel(new BorderLayout(0,0));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				pnlComp_a.setPreferredSize(new java.awt.Dimension(921, 37));	
				pnlComp_a.setBorder(lineBorder);

				pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
				pnlComp_a1.setPreferredSize(new java.awt.Dimension(209, 30));
				pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

				{
					lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
					pnlComp_a1.add(lblCompany);
					lblCompany.setBounds(8, 11, 62, 12);
					lblCompany.setPreferredSize(new java.awt.Dimension(105, 25));
					lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}
				{
					lookupCompany = new _JLookup(null, "Company",0,2);
					pnlComp_a1.add(lookupCompany);
					lookupCompany.setLookupSQL(SQL_COMPANY());
					lookupCompany.setReturnColumn(0);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){

								co_id 		= (String) data[0];	
								tagCompany.setTag((String) data[1]);
								company		= (String) data[1];	
								company_logo = (String) data[3];

								enableButtons(true, true, false, true, false);
								enable_fields(true);

								lookupDiv.setLookupSQL(getDivision());
								lookupDep.setLookupSQL( getDepartment());
								lookupAvailerType.setLookupSQL(getAvailerType());
								lookupAvailer.setLookupSQL(getEntityList());

								rbtnForATD.setEnabled(true);
							}
						}
					});
				}			

				pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
				pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
				pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
				pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

				{
					tagCompany = new _JTagLabel("[ ]");
					pnlComp_a2.add(tagCompany);
					tagCompany.setBounds(209, 27, 700, 22);
					tagCompany.setEnabled(true);	
					tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
				}		
			}

			pnlLiqSOA = new JPanel(new BorderLayout(0,0));
			pnlNorth.add(pnlLiqSOA, BorderLayout.CENTER);				
			pnlLiqSOA.setPreferredSize(new java.awt.Dimension(921, 55));
			pnlLiqSOA.setBorder(JTBorderFactory.createTitleBorder("Contract Details"));
			pnlLiqSOA.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			pnlLiqSOA.setBorder(lineBorder);

			{
				pnlLiqSOADtl = new JPanel(new BorderLayout(0, 0));
				pnlLiqSOA.add(pnlLiqSOADtl, BorderLayout.WEST);	
				pnlLiqSOADtl.setPreferredSize(new java.awt.Dimension(911, 129));

				pnlLiqSOADtl_1 = new JPanel(new BorderLayout(0, 5));
				pnlLiqSOADtl.add(pnlLiqSOADtl_1, BorderLayout.WEST);	
				pnlLiqSOADtl_1.setPreferredSize(new java.awt.Dimension(214, 127));
				pnlLiqSOADtl_1.setBorder(BorderFactory.createEmptyBorder(5,0,5, 0));

				pnlLiqSOADtl_1a = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlLiqSOADtl_1.add(pnlLiqSOADtl_1a, BorderLayout.WEST);	
				pnlLiqSOADtl_1a.setPreferredSize(new java.awt.Dimension(102, 127));
				pnlLiqSOADtl_1a.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));							

				{
					lblDivision = new JLabel("Division", JLabel.TRAILING);
					pnlLiqSOADtl_1a.add(lblDivision);
					lblDivision.setEnabled(false);	
				}	
				{
					lblDepartment = new JLabel("Department", JLabel.TRAILING);
					pnlLiqSOADtl_1a.add(lblDepartment);
					lblDepartment.setEnabled(false);	
				}
				{
					lblAvailerType = new JLabel("Availer Type", JLabel.TRAILING);
					pnlLiqSOADtl_1a.add(lblAvailerType);
					lblAvailerType.setEnabled(false);	
				}	
				{
					lblAvailer = new JLabel("Availer", JLabel.TRAILING);
					pnlLiqSOADtl_1a.add(lblAvailer);
					lblAvailer.setEnabled(false);	
				}


				pnlLiqSOADtl_1b = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlLiqSOADtl_1.add(pnlLiqSOADtl_1b, BorderLayout.CENTER);	
				pnlLiqSOADtl_1b.setPreferredSize(new java.awt.Dimension(105, 87));
				pnlLiqSOADtl_1b.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

				{
					lookupDiv = new _JLookup(null, "Division", 2, 2);
					pnlLiqSOADtl_1b.add(lookupDiv);
					lookupDiv.setBounds(20, 27, 20, 25);
					lookupDiv.setReturnColumn(0);
					lookupDiv.setFilterName(true);
					lookupDiv.setEnabled(false);	
					lookupDiv.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupDiv.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){								

								tagDivision.setTag((String) data[1]);
								lookupDep.setValue("");
								tagDepartment.setText("[ ]");
								lookupDep.setLookupSQL( getDepartment());
								lookupAvailer.setLookupSQL(getEntityList());
								lookupAvailer.setValue("");
								tagAvailer.setText("[ ]");
								refresh_tablesMain();
								//enableButtons(true, false, false, true, false);
								enableButtons(false, true, false, true, false);
							}
						}
					});	
				}		
				{
					lookupDep = new _JLookup(null, "Department", 2, 2);
					pnlLiqSOADtl_1b.add(lookupDep);
					lookupDep.setBounds(20, 27, 20, 25);
					lookupDep.setReturnColumn(0);
					lookupDep.setFilterName(true);
					lookupDep.setEnabled(false);	
					lookupDep.setPreferredSize(new java.awt.Dimension(102, 29));
					lookupDep.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){									
								tagDepartment.setTag((String) data[1]);
								lookupAvailer.setLookupSQL(getEntityList());
								lookupAvailer.setValue("");
								tagAvailer.setText("[ ]");
								refresh_tablesMain();
								//enableButtons(true, false, false, true, false);
								enableButtons(false, true, false, true, false);
							}
						}
					});	
				}	
				{
					lookupAvailerType = new _JLookup(null, "Availer Type", 2, 2);
					pnlLiqSOADtl_1b.add(lookupAvailerType);
					lookupAvailerType.setBounds(20, 27, 20, 25);
					lookupAvailerType.setReturnColumn(0);
					lookupAvailerType.setFilterName(true);
					lookupAvailerType.setEnabled(false);	
					lookupAvailerType.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupAvailerType.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){						
								tagAvailerType.setTag((String) data[1]);
								lookupAvailer.setLookupSQL(getEntityList());
								lookupAvailer.setValue("");
								tagAvailer.setText("[ ]");
								lookupAvailer.setLookupSQL(getEntityList());
								lookupAvailer.setEnabled(true);//**ADDED BY JED 2019-01-31**//
								refresh_tablesMain();
								enableButtons(true, false, false, true, false);
								//enableButtons(true, true, false, true, false);
							}
						}
					});	
				}
				{
					lookupAvailer= new _JLookup(null, "Availer", 2, 2);
					pnlLiqSOADtl_1b.add(lookupAvailer);
					lookupAvailer.setBounds(20, 27, 20, 25);
					lookupAvailer.setReturnColumn(0);
					lookupAvailer.setFilterName(true);
					lookupAvailer.setEnabled(false);	
					lookupAvailer.setPreferredSize(new java.awt.Dimension(157, 22));
					lookupAvailer.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){						
								tagAvailer.setTag((String) data[1]);
								refresh_tablesMain();
								//enableButtons(true, false, false, true, false);
								enableButtons(true, false, false, true, false);
							}
						}
					});	
				}


				//Start of Left Panel 
				pnlDeptDivAlias = new JPanel(new BorderLayout(0,0));
				pnlLiqSOADtl.add(pnlDeptDivAlias, BorderLayout.CENTER);
				pnlDeptDivAlias.setPreferredSize(new java.awt.Dimension(136, 72));
				pnlDeptDivAlias.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

				pnlDeptDivAlias1 = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlDeptDivAlias.add(pnlDeptDivAlias1, BorderLayout.EAST);
				pnlDeptDivAlias1.setPreferredSize(new java.awt.Dimension(424, 116));
				pnlDeptDivAlias1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

				{
					tagDivision = new _JTagLabel("[ ]");
					pnlDeptDivAlias1.add(tagDivision);
					tagDivision.setBounds(209, 27, 700, 22);
					tagDivision.setEnabled(false);						
					tagDivision.setPreferredSize(new java.awt.Dimension(27, 33));
				}					
				{
					tagDepartment = new _JTagLabel("[ ]");
					pnlDeptDivAlias1.add(tagDepartment);
					tagDepartment.setBounds(209, 27, 700, 22);
					tagDepartment.setEnabled(false);						
					tagDepartment.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagAvailerType = new _JTagLabel("[ ]");
					pnlDeptDivAlias1.add(tagAvailerType);
					tagAvailerType.setBounds(209, 27, 700, 22);
					tagAvailerType.setEnabled(false);						
					tagAvailerType.setPreferredSize(new java.awt.Dimension(27, 33));
				}
				{
					tagAvailer = new _JTagLabel("[ ]");
					pnlDeptDivAlias1.add(tagAvailer);
					tagAvailer.setBounds(209, 27, 700, 22);
					tagAvailer.setEnabled(false);	
					tagAvailer.setPreferredSize(new java.awt.Dimension(27, 33));
				}


				pnlLiqSOAInfo = new JPanel(new BorderLayout(0,0));
				pnlLiqSOADtl.add(pnlLiqSOAInfo, BorderLayout.EAST);
				pnlLiqSOAInfo.setPreferredSize(new java.awt.Dimension(273, 116));
				pnlLiqSOAInfo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

				pnlLiqSOAInfo_1 = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlLiqSOAInfo.add(pnlLiqSOAInfo_1, BorderLayout.WEST);
				pnlLiqSOAInfo_1.setPreferredSize(new java.awt.Dimension(101, 116));
				pnlLiqSOAInfo_1.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

				{
					rbtnCutoffDate = new JRadioButton();
					pnlLiqSOAInfo_1.add(rbtnCutoffDate);
					rbtnCutoffDate.setText("Cutoff Date");
					rbtnCutoffDate.setBounds(277, 12, 77, 18);
					rbtnCutoffDate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					rbtnCutoffDate.setSelected(false);
					rbtnCutoffDate.setEnabled(false);
					rbtnCutoffDate.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae){
							
							String availer_type = lookupAvailerType.getValue();
							String availer = lookupAvailer.getValue();
							
							rbtnCutoffDate.setSelected(true);	
							rbtnYear.setSelected(false);
							rbtnDueDays.setSelected(false);		
							rbtnAll.setSelected(false);
							dteCutoff.setEnabled(true);
							cmbYear.setEnabled(false);
							cmbDueDays.setEnabled(false);	
							dteCutoff.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							//enableButtons(true, false, false, true, false);
							//if(!lookupAvailer.getValue().equals("") || !lookupAvailerType.getValue().equals("")){
							if(availer != null || availer_type != null){	
								enableButtons(true, false, false, true, false);
							}else{
								enableButtons(false, true, false, true, false);
								//enableButtons(false, false, false, true, false);
							}
						}});					
				}
				{
					rbtnYear = new JRadioButton();
					pnlLiqSOAInfo_1.add(rbtnYear);
					rbtnYear.setText("Year");
					rbtnYear.setBounds(277, 12, 77, 18);
					rbtnYear.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					rbtnYear.setSelected(false);
					rbtnYear.setEnabled(false);
					rbtnYear.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae){									
							rbtnCutoffDate.setSelected(false);	rbtnYear.setSelected(true);
							rbtnDueDays.setSelected(false);		rbtnAll.setSelected(false);
							dteCutoff.setEnabled(false);
							cmbYear.setEnabled(true);
							cmbDueDays.setEnabled(false);	
							dteCutoff.setDate(null);
							//enableButtons(true, false, false, true, false);
							enableButtons(false, false, false, true, false);
						}});
				}
				{
					rbtnDueDays = new JRadioButton();
					pnlLiqSOAInfo_1.add(rbtnDueDays);
					rbtnDueDays.setText("Due Days");
					rbtnDueDays.setBounds(277, 12, 77, 18);
					rbtnDueDays.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					rbtnDueDays.setSelected(false);
					rbtnDueDays.setEnabled(false);
					rbtnDueDays.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae){									
							rbtnCutoffDate.setSelected(false);	rbtnYear.setSelected(false);
							rbtnDueDays.setSelected(true);		rbtnAll.setSelected(false);
							dteCutoff.setEnabled(false);
							cmbYear.setEnabled(false);
							cmbDueDays.setEnabled(true);	
							dteCutoff.setDate(null);
							//enableButtons(true, false, false, true, false);
							enableButtons(false, false, false, true, false);
						}});
				}
				{
					rbtnAll = new JRadioButton();
					pnlLiqSOAInfo_1.add(rbtnAll);
					rbtnAll.setText("All CA Date");
					rbtnAll.setBounds(277, 12, 77, 18);
					rbtnAll.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					rbtnAll.setSelected(true);
					rbtnAll.setEnabled(false);
					rbtnAll.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae){									
							rbtnCutoffDate.setSelected(false);	rbtnYear.setSelected(false);
							rbtnDueDays.setSelected(false);		rbtnAll.setSelected(true);
							dteCutoff.setEnabled(false);
							cmbYear.setEnabled(false);
							cmbDueDays.setEnabled(false);
							dteCutoff.setDate(null);
							refresh_tablesMain();
						}});
				}


				pnlLiqSOADtl_2 = new JPanel(new BorderLayout(5,0));
				pnlLiqSOAInfo.add(pnlLiqSOADtl_2, BorderLayout.CENTER);
				pnlLiqSOADtl_2.setPreferredSize(new java.awt.Dimension(203, 118));
				pnlLiqSOADtl_2.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));

				pnlLiqSOADtl_2a = new JPanel(new GridLayout(4, 1, 0, 5));
				pnlLiqSOADtl_2.add(pnlLiqSOADtl_2a, BorderLayout.WEST);
				pnlLiqSOADtl_2a.setPreferredSize(new java.awt.Dimension(102, 87));
				pnlLiqSOADtl_2a.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
				{							
					dteCutoff = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlLiqSOADtl_2a.add(dteCutoff);
					dteCutoff.setBounds(100, 7, 125, 21);
					dteCutoff.setDate(null);
					dteCutoff.setEnabled(false);
					dteCutoff.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor)dteCutoff.getDateEditor()).setEditable(false);
					dteCutoff.setPreferredSize(new java.awt.Dimension(626, 33));	
					dteCutoff.addPropertyChangeListener( new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {
							refresh_tablesMain();							
						}
					});	
				}	
				{
					String status[] = {"2015","2016","2017","2018"};					
					cmbYear = new JComboBox(status);
					pnlLiqSOADtl_2a.add(cmbYear);
					cmbYear.setSelectedItem(null);
					cmbYear.setBounds(537, 15, 160, 21);	
					cmbYear.setEnabled(false);
					cmbYear.setSelectedIndex(0);
					cmbYear.setSelectedItem(getMonthYear());
					((JLabel)cmbYear.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
					cmbYear.setPreferredSize(new java.awt.Dimension(89, 22));
					cmbYear.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{
							refresh_tablesMain();
						}
					});		
				}
				{
					String status[] = {"0 - 5","6 - 30","31 - 60","61 - 90","91 - 120","121 - 150","151 - 180","> 180"};					
					cmbDueDays = new JComboBox(status);
					pnlLiqSOADtl_2a.add(cmbDueDays);
					cmbDueDays.setSelectedItem(null);
					cmbDueDays.setBounds(537, 15, 160, 21);	
					cmbDueDays.setEnabled(false);
					((JLabel)cmbDueDays.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
					cmbDueDays.setSelectedIndex(0);	
					cmbDueDays.setPreferredSize(new java.awt.Dimension(89, 22));
					cmbDueDays.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{
							refresh_tablesMain();
						}
					});		
				}
			}

			pnlForATD = new JPanel(new BorderLayout(0,0));
			pnlNorth.add(pnlForATD, BorderLayout.SOUTH);	
			pnlForATD.setPreferredSize(new java.awt.Dimension(921, 29));
			pnlForATD.setBorder(lineBorder);			

			{
				pnlForATD_a = new JPanel(new BorderLayout(5, 5));
				pnlForATD.add(pnlForATD_a, BorderLayout.WEST);	
				pnlForATD_a.setPreferredSize(new java.awt.Dimension(86, 30));	
			}
			{
				pnlForATD_b = new JPanel(new BorderLayout(5, 5));
				pnlForATD.add(pnlForATD_b, BorderLayout.CENTER);	
				pnlForATD_b.setPreferredSize(new java.awt.Dimension(610, 30));	
				{
					rbtnForATD = new JRadioButton();
					pnlForATD_b.add(rbtnForATD);
					rbtnForATD.setText("For ATD (requests with same availer and check payee)");
					rbtnForATD.setBounds(277, 12, 77, 18);
					rbtnForATD.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
					rbtnForATD.setSelected(false);
					rbtnForATD.setEnabled(false);
					rbtnForATD.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae){									
							if (rbtnForATD.isSelected()==true){for_atd = "yes";} else {for_atd = "no";}				
						}});
				}		
			}




		}		

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();

	}


	//display all unliquidated payments
	public void displayLiquidationSOA_all(final DefaultTableModel modelMain, final JList rowHeader, final DefaultTableModel modelTotal, final java.util.Date cur_date, final String soa_year, 
			final String for_atd, final String availer, final String availer_type, final Integer soa_index, final Integer over_min, final Integer over_max, final String co_id, final Boolean ifAll) {//
		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");
				
				FncTables.clearTable(modelMain);//Code to clear modelMain.		
				DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
				rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

//				String sql = 			
//					"----------display Liquidation SOA(All)  \r\n" + 
//
//					"select \r\n" + 
//					"false,\r\n" + 
//					"a.entity_id2,\r\n" + 
//					"a.availer,\r\n" + 
//					"b.cnt,\r\n" + 
//					"a.amount,\r\n" +
//					"a.rplf_no" + //**ADDED BY JED 2019-01-03: TO DISPLAY RPLF NO. IN TABLE**//
//					"\n\n" + 
//
//					"from (" +			
//					"	select \r\n" + 			
//					"	a.entity_id2,\r\n" + 
//					"	a.availer,\r\n" + 
//					"	a.amount as amount,\r\n" +
//					"	a.rplf_no" +
//
//					"	from (\r\n" + 			
//					"		select\r\n" + 			
//					"		a.rplf_no,\r\n" + 
//					"		c.pv_no,\r\n" + 
//					"		c.cv_no,\r\n" + 
//					"		a.entity_id2,\r\n" + 
//					"		upper(trim(f.entity_name)) as availer,\r\n" + 
//					"		d.date_paid,\r\n" + 
//					"		((now() - d.date_paid - '1 days') - " +
//					"			((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//					"			( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//					"			else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	+						
//					"		) as run_due_days ,\r\n" + 
//					"		b.line_no,\r\n" + 
//					"		b.amount,\r\n" + 
//					"		b.part_desc,\r\n" + 
//					"		b.acct_id,\r\n" + 
//					"		a.rplf_date, \n" +
//					"		d.proc_id \n\n" +
//
//					"		from (select * from rf_request_header " +
//					"			where co_id = '"+co_id+"' \n" +
//					"			and (rplf_no, co_id) not in (select '000000380','02' union all select '000000381','02' union all select '000000040','02')  \n" +
//					//"			and entity_id2 in (select entity_id from em_employee) \n"		+
//					"			and rplf_no in (select pv_no from rf_pv_header \n" + 
//					"				where cv_no in (select cv_no from rf_cv_header where date_paid is not null \n" +
//					"					and status_id not in ('I','D')) \n" +
//					//"				and co_id = '"+co_id+"' \n" +
//					//"				and status_id = 'P' " +
//					"			) \n" + 
//					"			and trim(rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) )\n" + 
//					"			and status_id not in ('I','D')) a \r\n" + 
//					"		left join (select * from rf_request_detail where status_id != 'I' and co_id = '"+co_id+"') b on trim(a.rplf_no) = trim(b.rplf_no) and a.co_id = b.co_id \r\n" + 
//					"		join (select * from rf_pv_header " +
//					"				where co_id = '"+co_id+"'" +
//							"		) c on trim(a.rplf_no) = trim(c.pv_no) and a.co_id = c.co_id \r\n" + 
//					"		join (select * from rf_cv_header where date_paid is not null and co_id = '"+co_id+"' )  d on trim(c.cv_no) = trim(d.cv_no) and c.co_id = d.co_id \r\n" + 
//					"		left join rf_entity f on trim(b.entity_id) = trim(f.entity_id)  \n\n" +
//
//					"		where a.rplf_type_id in ( '02', '07', '12', '13' )\r\n" + 
//					"		and trim(a.rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) )\r\n" + 
//					"		and d.status_id = 'P' \n" +
//					"		and d.proc_id = '5' \n" ;
//
//				if (rbtnForATD.isSelected()==true) {sql = sql + "       and d.entity_id1 = b.entity_id \n";} else {}
//				sql = sql +						
//
//				/* //Removed : See DCRF No. 584
//				"		and ((now() - d.date_paid - '1 days') - " +
//				"			((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//				"			( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000',  '04007001' ) then interval '30 day'\r\n" + 
//				"			else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	+						
//				"		) >= '0 day' \n\n" +  */
//
//				"		order by f.entity_name, a.rplf_no  \r\n" + 		
//				"		) a \n\n" + 			
//				//"	where a.run_due_days >= '0 day'\r\n" + 			
//				"	group by a.entity_id2, a.availer, amount, a.rplf_no \r\n" +  //**ADDED BY JED 2019-01-03: TO DISPLAY RPLF NO. IN TABLE**//			
//				"	order by a.entity_id2 )  a \n\n" +
//
//				"left join  ( select distinct on (a.availer) a.availer, count(a.rplf_no) as cnt from ( \n\n" + 
//
//				"	select --distinct on (a.rplf_no)\r\n" + 
//				"	a.rplf_no,\r\n" + 
//				"	upper(trim(f.entity_name)) as availer \n\n" + 
//
//				"	from (select * from rf_request_header where co_id = '"+co_id+"' " +
//				"		and rplf_no in (select pv_no from rf_pv_header where cv_no in \n" + 
//				"			(select cv_no from rf_cv_header where date_paid is not null and status_id not in ('I','D') )) \n" + 
//				"			and trim(rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) ) \n" +
//				"			and status_id not in ('I','D')) a\r\n" + 
//				"	left join (select * from rf_request_detail where status_id != 'I' and co_id = '"+co_id+"') b on trim(a.rplf_no) = trim(b.rplf_no) and a.co_id = b.co_id \r\n" + 
//				"	left join (select * from rf_pv_header where co_id = '"+co_id+"') c on trim(a.rplf_no) = trim(c.pv_no) and a.co_id = c.co_id \r\n" + 
//				"	join (select * from rf_cv_header where date_paid is not null and co_id = '"+co_id+"') d on trim(c.cv_no) = trim(d.cv_no) and a.co_id = d.co_id \r\n" + 
//				"	left join rf_entity f on trim(b.entity_id) = trim(f.entity_id)  \r\n" + 
//				"	where a.rplf_type_id in ( '02', '07', '12', '13' )\r\n" + 
//				"	and d.status_id = 'P' 	\r\n" + 
//				"	and d.proc_id = '5' \r\n" ;
//
//				if (rbtnForATD.isSelected()==true) {sql = sql + "       and d.entity_id1 = b.entity_id \n";} else {}
//				sql = sql +		
//
//				/* //Removed : See DCRF No. 584
//				"	and ((now() - d.date_paid - '1 days') - " +
//				"		((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//				"		( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//				"		else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end ) \n" +
//				"		) >= '0 day'   \n\n"	+	
//				*/  
//				
//				"	group by a.rplf_no, f.entity_name\r\n" + 
//				"	order by a.rplf_no  ) a\r\n" + 
//				"	group by a.availer \n\n" + 
//
//				") b on a.availer = b.availer order by a.availer " +
//
//				"\n\n" ;

				
				String sql = "select * from view_unliquidated_soa('"+cur_date+"', '"+soa_year+"', '"+for_atd+"', '"+availer+"', '"+availer_type+"', "+soa_index+", "+over_min+", "+over_max+", '"+co_id+"', "+ifAll+")";
				
				FncSystem.out("SQL for Generate All Unliquidated:", sql);
				pgSelect db = new pgSelect();
				db.select(sql);
				if(db.isNotNull()){
					for(int x=0; x < db.getRowCount(); x++){
						// Adding of row in table
						modelMain.addRow(db.getResult()[x]);
						listModel.addElement(modelMain.getRowCount());				
					}	

					totalSOAliqui(modelMain, modelTotal);				
				}		

				else {
					modelLiq_part_total = new modelLiquidationSOA_2();
					modelLiq_part_total.addRow(new Object[] { "", "Total",null, null,new BigDecimal(0.00)});

					tblLiq_part_total = new _JTableTotal(modelLiq_part, tblLiq_part);
					tblLiq_part_total.setFont(dialog11Bold);
					scrollLiq_part_total.setViewportView(tblLiq_part_total);
					((_JTableTotal) tblLiq_part_total).setTotalLabel(0);
					JOptionPane.showMessageDialog(getContentPane(),"No unliquidated CA as of the current date","Information",JOptionPane.INFORMATION_MESSAGE);
				}

				adjustRowHeight();
				tblLiq_part.packAll();
					
				FncGlobal.stopProgress();
				return null;
			}
		};
		sw.execute();
	}	
	
	public void displayLiquidationSOA_selected_v4(final DefaultTableModel modelMain, final JList rowHeader, final DefaultTableModel modelTotal, final java.util.Date cur_date, final String soa_year, 
			final String for_atd, final String availer, final String availer_type, final Integer soa_index, final Integer over_min, final Integer over_max, final String co_id, final Boolean ifAll) {
		SwingWorker sw = new SwingWorker() {
			protected Object doInBackground()
					throws FileNotFoundException, IOException, InterruptedException {
				FncGlobal.startProgress("Loading...");		

				FncTables.clearTable(modelMain);//Code to clear modelMain.		
				DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
				rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
				
				
				String sql = "select * from view_unliquidated_soa('"+cur_date+"', '"+soa_year+"', '"+for_atd+"', '"+availer+"', '"+availer_type+"', "+soa_index+", "+over_min+", "+over_max+", '"+co_id+"', "+ifAll+")";
					

				FncSystem.out("SQL for Liquidation SOA", sql);
				pgSelect db = new pgSelect();
				db.select(sql);
				if(db.isNotNull()){
					for(int x=0; x < db.getRowCount(); x++){
						// Adding of row in table
						modelMain.addRow(db.getResult()[x]);
						listModel.addElement(modelMain.getRowCount());				
					}	

					totalSOAliqui(modelMain, modelTotal);				
				}		

				else {
					modelLiq_part_total = new modelLiquidationSOA_2();
					modelLiq_part_total.addRow(new Object[] { "", "Total",null, null,new BigDecimal(0.00)});

					tblLiq_part_total = new _JTableTotal(modelLiq_part, tblLiq_part);
					tblLiq_part_total.setFont(dialog11Bold);
					scrollLiq_part_total.setViewportView(tblLiq_part_total);
					((_JTableTotal) tblLiq_part_total).setTotalLabel(0);
					JOptionPane.showMessageDialog(getContentPane(),"No unliquidated CA as of the current date","Information",JOptionPane.INFORMATION_MESSAGE);
				}

				adjustRowHeight();
				tblLiq_part.packAll();
					
				FncGlobal.stopProgress();
				return null;
			}
		};
		sw.execute();	
	}

//	public void displayLiquidationSOA_selected(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//**ORIGINAL SQL**//
//
//		String availer = lookupAvailer.getText().trim();
//		String availer_type = lookupAvailerType.getText().trim();
//		String dept_id = lookupDep.getText().trim();
//		String div_id  = lookupDiv.getText().trim();
//
//		Integer soa_cutoff 	= null;
//		String soa_year   	= null;
//		Integer soa_due_days   = null;
//
//		Date cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());
//
//		if (rbtnCutoffDate.isSelected()) { soa_cutoff = 1; }
//		else if (rbtnYear.isSelected()) { soa_year = (String) cmbYear.getSelectedItem(); }
//		else if (rbtnDueDays.isSelected()) {soa_due_days = 1;}
//		else if (rbtnAll.isSelected()) {}		
//
//		overdue_deduction = 
//			"		( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//			"		else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	;
//
//		FncTables.clearTable(modelMain);//Code to clear modelMain.		
//		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
//		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
//
//		String sql = 			
//			"----------display Liquidation SOA(All) original sql  \r\n" + 
//
//			"select \r\n" + 
//			"false,\r\n" + 
//			"a.entity_id2,\r\n" + 
//			"a.availer,\r\n" + 
//			"b.cnt,\r\n" + 
//			"a.amount \r\n" + 
//			"\n\n" + 
//
//			"from (" +			
//			"	select \r\n" + 			
//			"	a.entity_id2,\r\n" + 
//			"	a.availer,\r\n" + 
//			"	a.amount as amount \n\n" + 
//
//			"	from (\r\n" + 			
//			"		select\r\n" + 			
//			"		a.rplf_no,\r\n" + 
//			"		c.pv_no,\r\n" + 
//			"		c.cv_no,\r\n" + 
//			//"		e.liq_no,\r\n" + 
//			"		a.entity_id2,\r\n" + 
//			"		upper(trim(f.entity_name)) as availer,\r\n" + 
//			//"		e.status_id,\r\n" + 
//			"		d.date_paid,\r\n" + 
//			"		((now() - d.date_paid - '1 day') - " +
//			"			((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//			"			( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//			"			else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	+						
//			"		) as run_due_days ,\r\n" + 
//			"		b.line_no,\r\n" + 
//			"		--b.amount,\r\n" + 
//			"		b.pv_amt as amount,\r\n" + 
//			"		b.part_desc,\r\n" + 
//			"		b.acct_id,\r\n" + 
//			"		a.rplf_date, \n" +
//			"		d.proc_id \n\n" +
//
//			"		from (select * from rf_request_header " +
//			"			where co_id = '"+co_id+"' \n" +
//			"			and (rplf_no, co_id) not in (select '000000380','02' union all select '000000381','02' union all select '000000040','02') " +
//			//"			and entity_id1 in (select entity_id from em_employee) \n"		+
//			"		)a \r\n" + 
//			"		left join (select * from rf_request_detail where co_id = '"+co_id+"') b on trim(a.rplf_no) = trim(b.rplf_no) and a.co_id = b.co_id and b.status_id = 'A'\r\n" + 
//			"		left join (select * from rf_pv_header where co_id = '"+co_id+"') c on trim(a.rplf_no) = trim(c.pv_no) and a.co_id = c.co_id \r\n" + 
//			"		left join rf_cv_header d on trim(c.cv_no) = trim(d.cv_no) and a.co_id = d.co_id \r\n" + 
//			//"		left join rf_liq_header e on trim(a.rplf_no) = trim(e.rplf_no) and a.co_id = e.co_id \r\n" + 
//			"		left join rf_entity f on trim(a.entity_id2) = trim(f.entity_id)  \n\n" +
//			"		left join em_employee g on trim(a.entity_id2) = trim(g.entity_id)  \r\n" + 
//
//			"		where a.rplf_type_id in ( '02', '07', '12', '13' )\r\n" + 
//			"		and trim(a.rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) )\r\n" + 
//			"		and d.status_id = 'P' \n" +
//			"		and d.date_paid is not null \n";
//		
//			//" 		and e.liq_no is null  \n" ;
//
//		if (rbtnForATD.isSelected()==true) {sql = sql + "       and d.entity_id1 = a.entity_id2 \n";} 
//		else {}
//
//		if (availer.equals("")){sql = sql + "";} 
//		else {sql = sql + "		and a.entity_id2 = '"+availer+"' ";}
//
//		if (availer_type.equals("")){sql = sql + "";} 
//		else {sql = sql + "		and a.entity_type_id = '"+availer_type+"' ";}
//
//		if (dept_id.equals("")){sql = sql + "";} 
//		else {sql = sql + "		and g.dept_code = '"+dept_id+"' ";}
//
//		if (div_id.equals("")){sql = sql + "";} 
//		else {sql = sql + "		and g.div_code = '"+div_id+"' ";}
//
//		if (soa_cutoff==null){sql = sql + "";} 
//		else {sql = sql + "		and d.date_paid + interval '1 day' <= '"+dteCutoff.getDate()+"'  \n";}
//
//		if (soa_year==null){sql = sql + "";} 
//		else {sql = sql + "		and substring(rplf_date::text, 0, 5)::int = '"+soa_year+"'  \n";}
//
//		if (soa_due_days==null){sql = sql + "";} 
//		else if (cmbDueDays.getSelectedIndex()==0) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - "+overdue_deduction+" )/86400)::int)))  >=  0	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  5	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==1) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  6	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  30	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==2) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  31	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  60	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==3) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  61	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  90	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==4) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  91	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  120	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==5) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  121	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  150	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==6) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  151	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  180	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==7) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >  180	  \n";}
//
//		sql = sql +				
//
//		/*
//		"		and ((now() - d.date_paid - '1 days') - " +
//		"			((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//		"			( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//		"			else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	+						
//		"		) >= '0 day' \n\n" + 
//		*/
//
//		"		order by f.entity_name, a.rplf_no  \r\n" + 		
//		"		) a \n\n" + 			
//		//"	where a.run_due_days >= '0 day'\r\n" + 			
//		"	group by a.entity_id2, a.availer, amount\r\n" + 			
//		"	order by a.entity_id2 )  a \n\n" +
//
//		"left join  ( select distinct on (a.availer) a.availer, count(a.rplf_no) as cnt from ( \n\n" + 
//
//		"	select --distinct on (a.rplf_no)\r\n" + 
//		"	a.rplf_no,\r\n" + 
//		"	upper(trim(f.entity_name)) as availer \n\n" + 
//
//		"	from (select * from rf_request_header " +
//		"			where co_id = '"+co_id+"' \n" +
//		"		)a \r\n" + 
//		"	left join (select * from rf_request_detail where co_id = '"+co_id+"') b on trim(a.rplf_no) = trim(b.rplf_no) and a.co_id = b.co_id and b.status_id = 'A'\r\n" + 
//		"	left join (select * from rf_pv_header where co_id = '"+co_id+"') c on trim(a.rplf_no) = trim(c.pv_no) and a.co_id = c.co_id \r\n" + 
//		"	left join rf_cv_header d on trim(c.cv_no) = trim(d.cv_no) and a.co_id = d.co_id \r\n" + 
//		//"	left join rf_liq_header e on trim(a.rplf_no) = trim(e.rplf_no) and a.co_id = e.co_id \r\n" + 
//		"	left join rf_entity f on trim(a.entity_id2) = trim(f.entity_id)  \n" +
//		"	left join em_employee g on trim(a.entity_id2) = trim(g.entity_id)   \n\n" + 
//
//		"	where a.rplf_type_id in ( '02', '07', '12', '13' )\r\n" + 
//		"	and d.status_id = 'P' 	\r\n" + 
//		"	and d.date_paid is not null \r\n" ;
//		//" 	and e.liq_no is null  \r\n" ;
//
//		if (rbtnForATD.isSelected()==true) {sql = sql + "       and d.entity_id1 = a.entity_id2 \n";} 
//		else {}
//
//		if (availer.equals("")){sql = sql + "";} 
//		else {sql = sql + "and a.entity_id2 = '"+availer+"' ";}
//
//		if (availer_type.equals("")){sql = sql + "";} 
//		else {sql = sql + "and a.entity_type_id = '"+availer_type+"' ";}
//
//		if (dept_id.equals("")){sql = sql + "";} 
//		else {sql = sql + "and g.dept_code = '"+dept_id+"' ";}
//
//		if (div_id.equals("")){sql = sql + "";} 
//		else {sql = sql + "and g.div_code = '"+div_id+"' ";}
//
//		if (soa_cutoff==null){sql = sql + "";} 
//		else {sql = sql + "and d.date_paid + interval '1 day' <= '"+dteCutoff.getDate()+"'  \n";}
//
//		if (soa_year==null){sql = sql + "";} 
//		else {sql = sql + "and substring(rplf_date::text, 0, 5)::int = '"+soa_year+"'  \n";}
//
//		if (soa_due_days==null){sql = sql + "";} 
//		else if (cmbDueDays.getSelectedIndex()==0) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - "+overdue_deduction+" )/86400)::int)))  >=  0	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  5	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==1) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  6	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  30	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==2) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  31	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  60	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==3) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  61	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  90	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==4) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  91	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  120	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==5) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  121	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  150	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==6) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  151	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  180	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==7) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >  180	  \n";}
//
//		sql = sql +				
//
//		/*
//		"	and ((now() - d.date_paid - '1 days') - " +
//		"		((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//		"		( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//		"		else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end ) \n" +
//		"		) >= '0 day'   \n\n"	+	
//		*/
//
//		"	group by a.rplf_no, f.entity_name\r\n" + 
//		"	order by a.rplf_no  ) a\r\n" + 
//		"	group by a.availer \n\n" + 
//
//		") b on a.availer = b.availer \n\n" +
//		"order by a.availer" ;
//
//		System.out.printf("SQL #1: %s", sql);
//		pgSelect db = new pgSelect();
//		db.select(sql);
//		if(db.isNotNull()){
//			for(int x=0; x < db.getRowCount(); x++){
//				// Adding of row in table
//				modelMain.addRow(db.getResult()[x]);
//				listModel.addElement(modelMain.getRowCount());				
//			}	
//
//			totalSOAliqui(modelMain, modelTotal);				
//		}		
//
//		else {
//			modelLiq_part_total = new modelLiquidationSOA_2();
//			modelLiq_part_total.addRow(new Object[] { "", "Total",null, null,new BigDecimal(0.00)});
//
//			tblLiq_part_total = new _JTableTotal(modelLiq_part, tblLiq_part);
//			tblLiq_part_total.setFont(dialog11Bold);
//			scrollLiq_part_total.setViewportView(tblLiq_part_total);
//			((_JTableTotal) tblLiq_part_total).setTotalLabel(0);
//			JOptionPane.showMessageDialog(getContentPane(),"No unliquidated CA as of the current date","Information",JOptionPane.INFORMATION_MESSAGE);
//		}
//
//		adjustRowHeight();
//		tblLiq_part.packAll();
//		
//	}
	
//	public void displayLiquidationSOA_selected_v3(final DefaultTableModel modelMain, final JList rowHeader, final DefaultTableModel modelTotal) {//**REVISION 3**//
//		SwingWorker sw = new SwingWorker() {
//			protected Object doInBackground()
//					throws FileNotFoundException, IOException, InterruptedException {
//				FncGlobal.startProgress("Loading...");
//				
//				String availer = lookupAvailer.getText().trim();
//				String availer_type = lookupAvailerType.getText().trim();
//				String dept_id = lookupDep.getText().trim();
//				String div_id  = lookupDiv.getText().trim();
//
//				Integer soa_cutoff 	= null;
//				String soa_year   	= null;
//				Integer soa_due_days   = null;
//
//				Date cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());
//
//				if (rbtnCutoffDate.isSelected()) { soa_cutoff = 1; }
//				else if (rbtnYear.isSelected()) { soa_year = (String) cmbYear.getSelectedItem(); }
//				else if (rbtnDueDays.isSelected()) {soa_due_days = 1;}
//				else if (rbtnAll.isSelected()) {}		
//
//				overdue_deduction = 
//					"		( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//					"		else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	;
//
//				FncTables.clearTable(modelMain);//Code to clear modelMain.		
//				DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
//				rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
//
//				String sql = 			
//					"----------display Liquidation SOA(All) original sql  \r\n" + 
//
//					"select \r\n" + 
//					"false,\r\n" + 
//					"a.entity_id2,\r\n" + 
//					"a.availer,\r\n" + 
//					"b.cnt,\r\n" + 
//					"a.amount \r\n" + 
//					"\n\n" + 
//
//					"from (" +			
//					"	select \r\n" + 			
//					"	a.entity_id2,\r\n" + 
//					"	a.availer,\r\n" + 
//					"	b.amount as amount \n\n" + 
//
//					"	from (\r\n" + 			
//					"		select\r\n" + 			
//					"		a.rplf_no,\r\n" + 
//					"		c.pv_no,\r\n" + 
//					"		c.cv_no,\r\n" + 
//					//"		e.liq_no,\r\n" + 
//					"		a.entity_id2,\r\n" + 
//					"		upper(trim(f.entity_name)) as availer,\r\n" + 
//					//"		e.status_id,\r\n" + 
//					"		d.date_paid,\r\n" + 
//					"		--((now() - d.date_paid - '1 day') - " +
//					"			--((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//					"			--( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//					"			--else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	+						
//					"		--) as run_due_days ,\r\n" + 
//					"		--b.line_no,\r\n" + 
//					"		--b.amount,\r\n" + 
//					"		--b.pv_amt as amount,\r\n" + 
//					"		--b.part_desc,\r\n" + 
//					"		--b.acct_id,\r\n" + 
//					"		a.rplf_date, \n" +
//					"		d.proc_id \n\n" +
//
//					"		from (select * from rf_request_header " +
//					"			where co_id = '"+co_id+"' \n" +
//					"			and (rplf_no, co_id) not in (select '000000380','02' union all select '000000381','02' union all select '000000040','02') " +
//					//"			and entity_id1 in (select entity_id from em_employee) \n"		+
//					"		)a \r\n" + 
//					"		--left join (select * from rf_request_detail where co_id = '"+co_id+"') b on trim(a.rplf_no) = trim(b.rplf_no) and a.co_id = b.co_id and b.status_id = 'A'\r\n" + 
//					"		left join (select * from rf_pv_header where co_id = '"+co_id+"') c on trim(a.rplf_no) = trim(c.pv_no) and a.co_id = c.co_id \r\n" + 
//					"		left join rf_cv_header d on trim(c.cv_no) = trim(d.cv_no) and a.co_id = d.co_id \r\n" + 
//					//"		left join rf_liq_header e on trim(a.rplf_no) = trim(e.rplf_no) and a.co_id = e.co_id \r\n" + 
//					"		left join rf_entity f on trim(a.entity_id2) = trim(f.entity_id)  \n\n" +
//					"		left join em_employee g on trim(a.entity_id2) = trim(g.entity_id)  \r\n" + 
//
//					"		where a.rplf_type_id in ( '02', '07', '12', '13' )\r\n" + 
//					"		and trim(a.rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) )\r\n" + 
//					"		and d.status_id = 'P' \n" +
//					"		and d.date_paid is not null \n";
//				
//					//" 		and e.liq_no is null  \n" ;
//
//				if (rbtnForATD.isSelected()==true) {sql = sql + "       and d.entity_id1 = a.entity_id2 \n";} 
//				else {}
//
//				if (availer.equals("")){sql = sql + "";} 
//				else {sql = sql + "		and a.entity_id2 = '"+availer+"' ";}
//
//				//if (availer_type.equals("")){sql = sql + "";} 
//				//else {sql = sql + "		and a.entity_type_id = '"+availer_type+"' ";}
//
//				if (dept_id.equals("")){sql = sql + "";} 
//				else {sql = sql + "		and g.dept_code = '"+dept_id+"' ";}
//
//				if (div_id.equals("")){sql = sql + "";} 
//				else {sql = sql + "		and g.div_code = '"+div_id+"' ";}
//
//				if (soa_cutoff==null){sql = sql + "";} 
//				else {sql = sql + "		and d.date_paid + interval '1 day' <= '"+dteCutoff.getDate()+"'  \n";}
//
//				if (soa_year==null){sql = sql + "";} 
//				else {sql = sql + "		and substring(rplf_date::text, 0, 5)::int = '"+soa_year+"'  \n";}
//
//				if (soa_due_days==null){sql = sql + "";} 
//				else if (cmbDueDays.getSelectedIndex()==0) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - "+overdue_deduction+" )/86400)::int)))  >=  0	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  5	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==1) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  6	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  30	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==2) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  31	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  60	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==3) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  61	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  90	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==4) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  91	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  120	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==5) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  121	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  150	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==6) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  151	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  180	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==7) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >  180	  \n";}
//
//				sql = sql +				
//
//				/*
//				"		and ((now() - d.date_paid - '1 days') - " +
//				"			((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//				"			( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//				"			else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	+						
//				"		) >= '0 day' \n\n" + 
//				*/
//
//				"		order by f.entity_name, a.rplf_no  \r\n" + 		
//				"		) a \n\n" + 			
//				//"	where a.run_due_days >= '0 day'\r\n" + 
//				"left join (\n" + 
//				"						select \n" + 
//				"						sum(pv_amt) as amount,\n" + 
//				"						rplf_no,\n" + 
//				"						co_id\n" + 
//				"						from rf_request_detail\n" + 
//				"						where co_id = '"+co_id+"'\n" + 
//				"						and status_id = 'A'\n" + 
//				"						group by rplf_no, co_id\n" + 
//				"					) b on a.rplf_no = b.rplf_no\n" +
//				"	--group by a.entity_id2, a.availer, amount\r\n" + 			
//				"	order by a.availer )  a \n\n" +
//
//				"left join  ( select distinct on (a.availer) a.availer, count(a.rplf_no) as cnt from ( \n\n" + 
//
//				"	select --distinct on (a.rplf_no)\r\n" + 
//				"	a.rplf_no,\r\n" + 
//				"	upper(trim(f.entity_name)) as availer \n\n" + 
//
//				"	from (select * from rf_request_header " +
//				"			where co_id = '"+co_id+"' \n" +
//				"		)a \r\n" + 
//				"	left join (select * from rf_request_detail where co_id = '"+co_id+"') b on trim(a.rplf_no) = trim(b.rplf_no) and a.co_id = b.co_id and b.status_id = 'A'\r\n" + 
//				"	left join (select * from rf_pv_header where co_id = '"+co_id+"') c on trim(a.rplf_no) = trim(c.pv_no) and a.co_id = c.co_id \r\n" + 
//				"	left join rf_cv_header d on trim(c.cv_no) = trim(d.cv_no) and a.co_id = d.co_id \r\n" + 
//				//"	left join rf_liq_header e on trim(a.rplf_no) = trim(e.rplf_no) and a.co_id = e.co_id \r\n" + 
//				"	left join rf_entity f on trim(a.entity_id2) = trim(f.entity_id)  \n" +
//				"	left join em_employee g on trim(a.entity_id2) = trim(g.entity_id)   \n\n" + 
//
//				"	where a.rplf_type_id in ( '02', '07', '12', '13' )\r\n" + 
//				"	and d.status_id = 'P' 	\r\n" + 
//				"	and d.date_paid is not null \r\n" ;
//				//" 	and e.liq_no is null  \r\n" ;
//
//				if (rbtnForATD.isSelected()==true) {sql = sql + "       and d.entity_id1 = a.entity_id2 \n";} 
//				else {}
//
//				if (availer.equals("")){sql = sql + "";} 
//				else {sql = sql + "and a.entity_id2 = '"+availer+"' ";}
//
//				//if (availer_type.equals("")){sql = sql + "";} 
//				//else {sql = sql + "and a.entity_type_id = '"+availer_type+"' ";}
//
//				if (dept_id.equals("")){sql = sql + "";} 
//				else {sql = sql + "and g.dept_code = '"+dept_id+"' ";}
//
//				if (div_id.equals("")){sql = sql + "";} 
//				else {sql = sql + "and g.div_code = '"+div_id+"' ";}
//
//				if (soa_cutoff==null){sql = sql + "";} 
//				else {sql = sql + "and d.date_paid + interval '1 day' <= '"+dteCutoff.getDate()+"'  \n";}
//
//				if (soa_year==null){sql = sql + "";} 
//				else {sql = sql + "and substring(rplf_date::text, 0, 5)::int = '"+soa_year+"'  \n";}
//
//				if (soa_due_days==null){sql = sql + "";} 
//				else if (cmbDueDays.getSelectedIndex()==0) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - "+overdue_deduction+" )/86400)::int)))  >=  0	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  5	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==1) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  6	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  30	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==2) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  31	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  60	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==3) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  61	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  90	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==4) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  91	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  120	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==5) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  121	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  150	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==6) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  151	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  180	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==7) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >  180	  \n";}
//
//				sql = sql +				
//
//				/*
//				"	and ((now() - d.date_paid - '1 days') - " +
//				"		((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//				"		( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//				"		else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end ) \n" +
//				"		) >= '0 day'   \n\n"	+	
//				*/
//
//				"	group by a.rplf_no, f.entity_name\r\n" + 
//				"	order by a.rplf_no  ) a\r\n" + 
//				"	group by a.availer \n\n" + 
//
//				") b on a.availer = b.availer \n\n" +
//				"order by a.availer" ;
//
//				System.out.printf("SQL #1: %s", sql);
//				pgSelect db = new pgSelect();
//				db.select(sql);
//				if(db.isNotNull()){
//					for(int x=0; x < db.getRowCount(); x++){
//						// Adding of row in table
//						modelMain.addRow(db.getResult()[x]);
//						listModel.addElement(modelMain.getRowCount());				
//					}	
//
//					totalSOAliqui(modelMain, modelTotal);				
//				}		
//
//				else {
//					modelLiq_part_total = new modelLiquidationSOA_2();
//					modelLiq_part_total.addRow(new Object[] { "", "Total",null, null,new BigDecimal(0.00)});
//
//					tblLiq_part_total = new _JTableTotal(modelLiq_part, tblLiq_part);
//					tblLiq_part_total.setFont(dialog11Bold);
//					scrollLiq_part_total.setViewportView(tblLiq_part_total);
//					((_JTableTotal) tblLiq_part_total).setTotalLabel(0);
//					JOptionPane.showMessageDialog(getContentPane(),"No unliquidated CA as of the current date","Information",JOptionPane.INFORMATION_MESSAGE);
//				}
//
//				adjustRowHeight();
//				tblLiq_part.packAll();
//					
//				FncGlobal.stopProgress();
//				return null;
//			}
//		};
//		sw.execute();	
//	}
	
//	public void displayLiquidationSOA_selected(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//**REVISION 1**//
//
//		String availer = lookupAvailer.getText().trim();
//		String availer_type = lookupAvailerType.getText().trim();
//		String dept_id = lookupDep.getText().trim();
//		String div_id  = lookupDiv.getText().trim();
//
//		Integer soa_cutoff 	= null;
//		String soa_year   	= null;
//		Integer soa_due_days   = null;
//
//		Date cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());
//
//		if (rbtnCutoffDate.isSelected()) { soa_cutoff = 1; }
//		else if (rbtnYear.isSelected()) { soa_year = (String) cmbYear.getSelectedItem(); }
//		else if (rbtnDueDays.isSelected()) {soa_due_days = 1;}
//		else if (rbtnAll.isSelected()) {}		
//
//		overdue_deduction = 
//			"		( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//			"		else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	;
//
//		FncTables.clearTable(modelMain);//Code to clear modelMain.		
//		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
//		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
//
//		String sql = 			
//			"----------display Liquidation SOA(All)  \r\n" + 
//
//			"select\n" + 
//			"false,\n" + 
//			"a.entity_id2,\n" + 
//			"get_client_name(a.entity_id2),\n" + 
//			"h.cnt,\n" + 
//			"a.amount\n" + 
//			"\n" + 
//			"\n" + 
//			"		from (select\n" + 
//			"\n" + 
//			"		a.entity_id2,\n" + 
//			"		c.cv_no,\n" + 
//			"		bb.amount,\n" + 
//			"		f.entity_name,\n" + 
//			"		b.days_overdue\n" + 
//			"\n" + 
//			"		from (select * from rf_request_header \n" + 
//			"			where co_id = '02'\n" + 
//			"			and (rplf_no, co_id) not in (select '000000380','02' union all select '000000381','02' union all select '000000040','02')\n" + 
//			"			and rplf_type_id in ( '02', '07', '12', '13' )\n" + 
//			"			and trim(rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) and co_id = '02')\n" + 
//			"			) a\n" + 
//			"		/*EDITED BY JED 2018-12-17 : ACCT. ID REMOVED DUE TO DOUBLE ROW WAS RETURNED*/\n" + 
//			"		left join ( select distinct on (rplf_no, /*acct_id,*/ co_id) rplf_no, co_id, entity_id, sum(amount) as amount/*, acct_id*/\n" + 
//			"			from rf_request_detail \n" + 
//			"			where rplf_no != ''  \n" + 
//			"			and status_id = 'A' \n" + 
//			"			and co_id = '02'			\n" + 
//			"			group by rplf_no, co_id, entity_id/*, acct_id*/\n" + 
//			"		) bb on trim(a.rplf_no) = trim(bb.rplf_no) and a.co_id = bb.co_id\n" + 
//			"\n" + 
//			"		left join (\n" + 
//			"		select distinct on (ax.rplf_no, ax.co_id) ax.rplf_no, ax.co_id, ax.days_overdue \n" + 
//			"		from (select\n" + 
//			"					ay.rplf_no,\n" + 
//			"					( \n" + 
//			"					/*('2018-12-10' - dy.date_paid - ((ceiling(EXTRACT(days FROM ('2018-12-10' - dy.date_paid)) / 7)::INTEGER * interval '1 day')*2) - \n" + 
//			"					(select sum(gy.count)::INT from(select distinct on (ho_day) 1 as count, ho_day from mf_holidays where ho_day >= dy.date_paid)gy)::INTEGER * interval '1 day' )*/\n" + 
//			"					make_interval(days := get_no_of_working_days(dy.date_paid,'2018-12-10'))  -\n" + 
//			"					/*- interval '1 day'*/\n" + 
//			"					--makget_no_of_working_days('2018-12-10', dy.date_paid) - \n" + 
//			"					( case when (acct_id in ('03-02-05-000', '01-03-06-001', '04007001' ) and ey.dept_code in ('18')) then interval '30 days'\n" + 
//			"					else ( case when (ey.dept_code in ('18') and (acct_id like  '08-01-05%' or acct_id like  '08-02-05%' or acct_id = '03-02-04-000')) then interval '60 days' else interval '0 day' end ) end )\n" + 
//			"					) as days_overdue,\n" + 
//			"					ay.co_id\n" + 
//			"					from (select distinct on (rplf_no, co_id) * from rf_request_detail where status_id = 'A' and co_id = '02') ay\n" + 
//			"					left join (select * from rf_request_header where co_id = '02') aa on trim(ay.rplf_no) = trim(aa.rplf_no) and ay.co_id = aa.co_id\n" + 
//			"					left join em_employee ey on aa.entity_id2 = ey.entity_id \n" + 
//			"					left join (select * from rf_pv_header where co_id = '02') cy on trim(ay.rplf_no) = trim(cy.pv_no) and ay.co_id = cy.co_id\n" + 
//			"					left join rf_cv_header dy on trim(cy.cv_no) = trim(dy.cv_no) and ay.co_id = dy.co_id\n" + 
//			"					WHERE  aa.entity_id2 = '"+availer+"' and dy.date_paid is not null\n" + 
//			"			) ax\n" + 
//			"		) b on trim(a.rplf_no) = trim(b.rplf_no) and a.co_id = b.co_id\n" + 
//			"		\n" + 
//			"		left join (select * from rf_pv_header where co_id = '02') c on trim(a.rplf_no) = trim(c.pv_no) and a.co_id = c.co_id\n" + 
//			"		left join (select * from rf_cv_header where co_id = '02') d on trim(c.cv_no) = trim(d.cv_no) and a.co_id = d.co_id\n" + 
//			"		left join (select * from rf_liq_header where co_id = '02') e on trim(a.rplf_no) = trim(e.rplf_no) and a.co_id = e.co_id\n" + 
//			"		left join rf_entity f on trim(bb.entity_id) = trim(f.entity_id)\n" + 
//			"		left join em_employee g on trim(f.entity_id) = trim(g.entity_id)\n" + 
//			"\n" + 
//			"		where a.rplf_type_id in ( '02', '07', '12', '13' )\n" + 
//			"		and trim(a.rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) and co_id = '02' )\n" + 
//			"		and d.status_id = 'P'\n" + 
//			"		and d.date_paid is not null\n" + 
//			"		and ( case when null = '' or null is null then true else substring(d.date_paid::text, 0, 5) = null  end )\n" + 
//			"		and ( case when 'no' = 'no' then true else d.entity_id1 = a.entity_id2 end )\n" + 
//			"		and b.days_overdue >= '0'::interval \n" ;
//		
//		
//			//" 		and e.liq_no is null  \n" ;
//
//		if (rbtnForATD.isSelected()==true) {sql = sql + "       and d.entity_id1 = a.entity_id2 \n";} 
//		else {}
//
//		if (availer.equals("")){sql = sql + "";} 
//		else {sql = sql + "		and a.entity_id2 = '"+availer+"' ";}
//
//		//if (availer_type.equals("")){sql = sql + "";} 
//		//else {sql = sql + "		and a.entity_type_id = '"+availer_type+"' ";}
//
//		if (dept_id.equals("")){sql = sql + "";} 
//		else {sql = sql + "		and g.dept_code = '"+dept_id+"' ";}
//
//		if (div_id.equals("")){sql = sql + "";} 
//		else {sql = sql + "		and g.div_code = '"+div_id+"' ";}
//
//		if (soa_cutoff==null){sql = sql + "";} 
//		else {sql = sql + "		and d.date_paid + interval '1 day' <= '"+dteCutoff.getDate()+"'  \n";}
//
//		if (soa_year==null){sql = sql + "";} 
//		else {sql = sql + "		and substring(rplf_date::text, 0, 5)::int = '"+soa_year+"'  \n";}
//
//		if (soa_due_days==null){sql = sql + "";} 
//		else if (cmbDueDays.getSelectedIndex()==0) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - "+overdue_deduction+" )/86400)::int)))  >=  0	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  5	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==1) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  6	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  30	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==2) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  31	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  60	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==3) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  61	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  90	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==4) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  91	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  120	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==5) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  121	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  150	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==6) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  151	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  180	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==7) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >  180	  \n";}
//
//		sql = sql +				
//
//		/*
//		"		and ((now() - d.date_paid - '1 days') - " +
//		"			((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//		"			( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//		"			else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	+						
//		"		) >= '0 day' \n\n" + 
//		*/
//
//		"order by a.rplf_no\n" + 
//		"		) a\n" + 
//		"\n" + 
//		"		left join ( select distinct on (b.availer) b.availer, count(b.rplf_no) as cnt from ( \n" + 
//		"\n" + 
//		"					select --distinct on (a.rplf_no)\n" + 
//		"					a.rplf_no,\n" + 
//		"					upper(trim(f.entity_name)) as availer \n" + 
//		"\n" + 
//		"					from (select * from rf_request_header 			where co_id = '02' \n" + 
//		"						)a \n" + 
//		"					left join (select * from rf_request_detail where co_id = '02') b on trim(a.rplf_no) = trim(b.rplf_no) and a.co_id = b.co_id and b.status_id = 'A'\n" + 
//		"					left join (select * from rf_pv_header where co_id = '02') c on trim(a.rplf_no) = trim(c.pv_no) and a.co_id = c.co_id \n" + 
//		"					left join rf_cv_header d on trim(c.cv_no) = trim(d.cv_no) and a.co_id = d.co_id \n" + 
//		"					left join rf_entity f on trim(a.entity_id2) = trim(f.entity_id)  \n" + 
//		"					left join em_employee g on trim(a.entity_id2) = trim(g.entity_id)   \n" + 
//		"\n" + 
//		"					where a.rplf_type_id in ( '02', '07', '12', '13' )\n" + 
//		"					and d.status_id = 'P' 	\n" + 
//		"					and d.date_paid is not null \n" ;
//		//" 	and e.liq_no is null  \r\n" ;
//
//		if (rbtnForATD.isSelected()==true) {sql = sql + "       and d.entity_id1 = a.entity_id2 \n";} 
//		else {}
//
//		if (availer.equals("")){sql = sql + "";} 
//		else {sql = sql + "and a.entity_id2 = '"+availer+"' ";}
//
//		if (availer_type.equals("")){sql = sql + "";} 
//		else {sql = sql + "and a.entity_type_id = '"+availer_type+"' ";}
//
//		if (dept_id.equals("")){sql = sql + "";} 
//		else {sql = sql + "and g.dept_code = '"+dept_id+"' ";}
//
//		if (div_id.equals("")){sql = sql + "";} 
//		else {sql = sql + "and g.div_code = '"+div_id+"' ";}
//
//		if (soa_cutoff==null){sql = sql + "";} 
//		else {sql = sql + "and d.date_paid + interval '1 day' <= '"+dteCutoff.getDate()+"'  \n";}
//
//		if (soa_year==null){sql = sql + "";} 
//		else {sql = sql + "and substring(rplf_date::text, 0, 5)::int = '"+soa_year+"'  \n";}
//
//		if (soa_due_days==null){sql = sql + "";} 
//		else if (cmbDueDays.getSelectedIndex()==0) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - "+overdue_deduction+" )/86400)::int)))  >=  0	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  5	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==1) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  6	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  30	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==2) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  31	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  60	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==3) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  61	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  90	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==4) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  91	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  120	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==5) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  121	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  150	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==6) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  151	  \n" +
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  180	  \n";}
//		else if (cmbDueDays.getSelectedIndex()==7) {sql = sql + 
//			"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >  180	  \n";}
//
//		sql = sql +				
//
//		/*
//		"	and ((now() - d.date_paid - '1 days') - " +
//		"		((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//		"		( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//		"		else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end ) \n" +
//		"		) >= '0 day'   \n\n"	+	
//		*/
//
//		"	group by a.rplf_no, f.entity_name\r\n" + 
//		"	order by a.rplf_no  ) b\r\n" + 
//		"	group by b.availer \n\n" + 
//
//		") h on a.entity_name = h.availer \n\n";
//
//		System.out.printf("SQL #1: %s", sql);
//		pgSelect db = new pgSelect();
//		db.select(sql);
//		if(db.isNotNull()){
//			for(int x=0; x < db.getRowCount(); x++){
//				// Adding of row in table
//				modelMain.addRow(db.getResult()[x]);
//				listModel.addElement(modelMain.getRowCount());				
//			}	
//
//			totalSOAliqui(modelMain, modelTotal);				
//		}		
//
//		else {
//			modelLiq_part_total = new modelLiquidationSOA_2();
//			modelLiq_part_total.addRow(new Object[] { "", "Total",null, null,new BigDecimal(0.00)});
//
//			tblLiq_part_total = new _JTableTotal(modelLiq_part, tblLiq_part);
//			tblLiq_part_total.setFont(dialog11Bold);
//			scrollLiq_part_total.setViewportView(tblLiq_part_total);
//			((_JTableTotal) tblLiq_part_total).setTotalLabel(0);
//			JOptionPane.showMessageDialog(getContentPane(),"No unliquidated CA as of the current date","Information",JOptionPane.INFORMATION_MESSAGE);
//		}
//
//		adjustRowHeight();
//		tblLiq_part.packAll();
//	}
	
//	public void displayLiquidationSOA_selected_v2(final DefaultTableModel modelMain, final JList rowHeader, final DefaultTableModel modelTotal) {//**REVISION 2**//
//		
//		SwingWorker sw = new SwingWorker() {
//			protected Object doInBackground()
//					throws FileNotFoundException, IOException, InterruptedException {
//				FncGlobal.startProgress("Loading...");
//				
//				String availer = lookupAvailer.getText().trim();
//				String availer_type = lookupAvailerType.getText().trim();
//				String dept_id = lookupDep.getText().trim();
//				String div_id  = lookupDiv.getText().trim();
//
//				Integer soa_cutoff 	= null;
//				String soa_year   	= null;
//				Integer soa_due_days   = null;
//
//				Date cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());
//
//				if (rbtnCutoffDate.isSelected()) { soa_cutoff = 1; }
//				else if (rbtnYear.isSelected()) { soa_year = (String) cmbYear.getSelectedItem(); }
//				else if (rbtnDueDays.isSelected()) {soa_due_days = 1;}
//				else if (rbtnAll.isSelected()) {}		
//
//				overdue_deduction = 
//					"		( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//					"		else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	;
//
//				FncTables.clearTable(modelMain);//Code to clear modelMain.		
//				DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
//				rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
//
//				String sql = 			
//					"----------display Liquidation SOA(Selected)  \r\n" + 
//
//					"select\n" + 
//					"false,\n" + 
//					"a.entity_id2,\n" + 
//					"get_client_name(a.entity_id2),\n" + 
//					"c.cnt,\n" + 
//					"a.amount,\n" + 
//					"a.rplf_no\n" + 
//					"\n" + 
//					"\n" + 
//					"		from (select\n" + 
//					"\n" + 
//					"		a.entity_id2,\n" + 
//					"		c.cv_no,\n" + 
//					"		bb.amount,\n" + 
//					"		f.entity_name,\n" + 
//					"		b.days_overdue,\n" + 
//					"		a.rplf_no\n" + 
//					"\n" + 
//					"		from (select * from rf_request_header \n" + 
//					"			where co_id = '02'\n" + 
//					"			and (rplf_no, co_id) not in (select '000000380','02' union all select '000000381','02' union all select '000000040','02')\n" + 
//					"			and rplf_type_id in ( '02', '07', '12', '13' )\n" + 
//					"			and trim(rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) and co_id = '02')\n" + 
//					"			) a\n" + 
//					"		/*EDITED BY JED 2018-12-17 : ACCT. ID REMOVED DUE TO DOUBLE ROW WAS RETURNED*/\n" + 
//					"		left join ( select distinct on (rplf_no, /*acct_id,*/ co_id) rplf_no, co_id, entity_id, sum(/*amount*/pv_amt) as amount/*, acct_id*/\n" + 
//					"			from rf_request_detail \n" + 
//					"			where rplf_no != ''  \n" + 
//					"			and status_id = 'A' \n" + 
//					"			and co_id = '02'			\n" + 
//					"			group by rplf_no, co_id, entity_id/*, acct_id*/\n" + 
//					"		) bb on trim(a.rplf_no) = trim(bb.rplf_no) and a.co_id = bb.co_id\n" + 
//					"\n" + 
//					"		left join (\n" + 
//					"		select distinct on (ax.rplf_no, ax.co_id) ax.rplf_no, ax.co_id, ax.days_overdue \n" + 
//					"		from (select\n" + 
//					"					ay.rplf_no,\n" + 
//					"					( \n" + 
//					"					/*('2018-12-10' - dy.date_paid - ((ceiling(EXTRACT(days FROM ('2018-12-10' - dy.date_paid)) / 7)::INTEGER * interval '1 day')*2) - \n" + 
//					"					(select sum(gy.count)::INT from(select distinct on (ho_day) 1 as count, ho_day from mf_holidays where ho_day >= dy.date_paid)gy)::INTEGER * interval '1 day' )*/\n" + 
//					"					make_interval(days := get_no_of_working_days(dy.date_paid,now()::DATE))  -\n" + 
//					"					/*- interval '1 day'*/\n" + 
//					"					--makget_no_of_working_days('2018-12-10', dy.date_paid) - \n" + 
//					"					( case when (acct_id in ('03-02-05-000', '01-03-06-001', '04007001' ) and ey.dept_code in ('18')) then interval '30 days'\n" + 
//					"					else ( case when (ey.dept_code in ('18') and (acct_id like  '08-01-05%' or acct_id like  '08-02-05%' or acct_id = '03-02-04-000')) then interval '60 days' else interval '0 day' end ) end )\n" + 
//					"					) as days_overdue,\n" + 
//					"					ay.co_id\n" + 
//					"					from (select distinct on (rplf_no, co_id) * from rf_request_detail where status_id = 'A' and co_id = '02') ay\n" + 
//					"					left join (select * from rf_request_header where co_id = '02') aa on trim(ay.rplf_no) = trim(aa.rplf_no) and ay.co_id = aa.co_id\n" + 
//					"					left join em_employee ey on aa.entity_id2 = ey.entity_id \n" + 
//					"					left join (select * from rf_pv_header where co_id = '02') cy on trim(ay.rplf_no) = trim(cy.pv_no) and ay.co_id = cy.co_id\n" + 
//					"					left join rf_cv_header dy on trim(cy.cv_no) = trim(dy.cv_no) and ay.co_id = dy.co_id\n" + 
//					"					WHERE  aa.entity_id2 = '"+availer+"' and dy.date_paid is not null\n" + 
//					"			) ax\n" + 
//					"		) b on trim(a.rplf_no) = trim(b.rplf_no) and a.co_id = b.co_id\n" + 
//					"		\n" + 
//					"		left join (select * from rf_pv_header where co_id = '02') c on trim(a.rplf_no) = trim(c.pv_no) and a.co_id = c.co_id\n" + 
//					"		left join (select * from rf_cv_header where co_id = '02') d on trim(c.cv_no) = trim(d.cv_no) and a.co_id = d.co_id\n" + 
//					"		left join (select * from rf_liq_header where co_id = '02') e on trim(a.rplf_no) = trim(e.rplf_no) and a.co_id = e.co_id\n" + 
//					"		left join rf_entity f on trim(bb.entity_id) = trim(f.entity_id)\n" + 
//					"		left join em_employee g on trim(f.entity_id) = trim(g.entity_id)\n" + 
//					"\n" + 
//					"		where a.rplf_type_id in ( '02', '07', '12', '13' )\n" + 
//					"		and trim(a.rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) and co_id = '02' )\n" + 
//					"		and d.status_id = 'P'\n" + 
//					"		and d.date_paid is not null\n" + 
//					"		and ( case when null = '' or null is null then true else substring(d.date_paid::text, 0, 5) = null  end )\n" + 
//					"		and ( case when 'no' = 'no' then true else d.entity_id1 = a.entity_id2 end )\n" +
//					"		--and b.days_overdue >= '0'::interval\n" ;
//				
//				
//					//" 		and e.liq_no is null  \n" ;
//
//				if (rbtnForATD.isSelected()==true) {sql = sql + "       and d.entity_id1 = a.entity_id2 \n";} 
//				else {}
//
//				if (availer.equals("")){sql = sql + "";} 
//				else {sql = sql + "		and a.entity_id2 = '"+availer+"' ";}
//
//				//if (availer_type.equals("")){sql = sql + "";} 
//				//else {sql = sql + "		and a.entity_type_id = '"+availer_type+"' ";}
//
//				if (dept_id.equals("")){sql = sql + "";} 
//				else {sql = sql + "		and g.dept_code = '"+dept_id+"' ";}
//
//				if (div_id.equals("")){sql = sql + "";} 
//				else {sql = sql + "		and g.div_code = '"+div_id+"' ";}
//
//				if (soa_cutoff==null){sql = sql + "";} 
//				else {sql = sql + "		and d.date_paid + interval '1 day' <= '"+dteCutoff.getDate()+"'  \n";}
//
//				if (soa_year==null){sql = sql + "";} 
//				else {sql = sql + "		and substring(rplf_date::text, 0, 5)::int = '"+soa_year+"'  \n";}
//
//				if (soa_due_days==null){sql = sql + "";} 
//				else if (cmbDueDays.getSelectedIndex()==0) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - "+overdue_deduction+" )/86400)::int)))  >=  0	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  5	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==1) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  6	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  30	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==2) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  31	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  60	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==3) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  61	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  90	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==4) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  91	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  120	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==5) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  121	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  150	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==6) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  151	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  180	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==7) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >  180	  \n";}
//
//				sql = sql +				
//
//				/*
//				"		and ((now() - d.date_paid - '1 days') - " +
//				"			((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//				"			( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//				"			else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end )  \n"	+						
//				"		) >= '0 day' \n\n" + 
//				*/
//
//				"order by a.rplf_no\n" + 
//				"		) a\n" + 
//				"\n" + 
//				"		left join (Select distinct on (z.entity_name)\n" + 
//				"		z.entity_name,\n" + 
//				"		count(z.cv_no) as cnt from\n" + 
//				"		(select\n" + 
//				"				f.entity_name,\n" + 
//				"				c.cv_no,\n" + 
//				"				a.rplf_no\n" + 
//				"\n" + 
//				"				from (select * from rf_request_header \n" + 
//				"					where co_id = '02'\n" + 
//				"					and (rplf_no, co_id) not in (select '000000380','02' union all select '000000381','02' union all select '000000040','02')\n" + 
//				"					and rplf_type_id in ( '02', '07', '12', '13' )\n" + 
//				"					and trim(rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) and co_id = '02')\n" + 
//				"					) a\n" + 
//				"				/*EDITED BY JED 2018-12-17 : ACCT. ID REMOVED DUE TO DOUBLE ROW WAS RETURNED*/\n" + 
//				"				left join ( select distinct on (rplf_no, co_id) rplf_no, co_id, entity_id\n" + 
//				"					from rf_request_detail \n" + 
//				"					where rplf_no != ''  \n" + 
//				"					and status_id = 'A' \n" + 
//				"					and co_id = '02'			\n" + 
//				"					--and entity_id = '0000000108'			\n" + 
//				"					group by rplf_no, co_id, entity_id\n" + 
//				"				) bb on trim(a.rplf_no) = trim(bb.rplf_no) and a.co_id = bb.co_id\n" + 
//				"				\n" + 
//				"				left join (select * from rf_pv_header where co_id = '02') c on trim(a.rplf_no) = trim(c.pv_no) and a.co_id = c.co_id\n" + 
//				"				left join (select * from rf_cv_header where co_id = '02') d on trim(c.cv_no) = trim(d.cv_no) and a.co_id = d.co_id\n" + 
//				"				left join (select * from rf_liq_header where co_id = '02') e on trim(a.rplf_no) = trim(e.rplf_no) and a.co_id = e.co_id\n" + 
//				"				left join rf_entity f on trim(bb.entity_id) = trim(f.entity_id)\n" + 
//				"				left join em_employee g on trim(f.entity_id) = trim(g.entity_id)\n" + 
//				"\n" + 
//				"				where a.rplf_type_id in ( '02', '07', '12', '13' )\n" + 
//				"				and trim(a.rplf_no) not in ( select trim(rplf_no) from rf_liq_header where status_id in ( 'G', 'A', 'P' ) and co_id = '02' )\n" + 
//				"				and d.status_id = 'P'\n" + 
//				"				and d.date_paid is not null\n" + 
//				"				and ( case when null = '' or null is null then true else substring(d.date_paid::text, 0, 5) = null  end )\n" + 
//				"				and ( case when 'no' = 'no' then true else d.entity_id1 = a.entity_id2 end )\n";
//				
//				//" 	and e.liq_no is null  \r\n" ;
//
//				if (rbtnForATD.isSelected()==true) {sql = sql + "       and d.entity_id1 = a.entity_id2 \n";} 
//				else {}
//
//				if (availer.equals("")){sql = sql + "";} 
//				else {sql = sql + "and a.entity_id2 = '"+availer+"' ";}
//
//				//if (availer_type.equals("")){sql = sql + "";} 
//				//else {sql = sql + "and a.entity_type_id = '"+availer_type+"' ";}
//
//				if (dept_id.equals("")){sql = sql + "";} 
//				else {sql = sql + "and g.dept_code = '"+dept_id+"' ";}
//
//				if (div_id.equals("")){sql = sql + "";} 
//				else {sql = sql + "and g.div_code = '"+div_id+"' ";}
//
//				if (soa_cutoff==null){sql = sql + "";} 
//				else {sql = sql + "and d.date_paid + interval '1 day' <= '"+dteCutoff.getDate()+"'  \n";}
//
//				if (soa_year==null){sql = sql + "";} 
//				else {sql = sql + "and substring(rplf_date::text, 0, 5)::int = '"+soa_year+"'  \n";}
//
//				if (soa_due_days==null){sql = sql + "";} 
//				else if (cmbDueDays.getSelectedIndex()==0) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - "+overdue_deduction+" )/86400)::int)))  >=  0	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  5	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==1) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  6	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  30	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==2) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  31	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  60	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==3) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  61	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  90	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==4) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  91	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  120	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==5) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  121	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  150	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==6) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >=  151	  \n" +
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  <=  180	  \n";}
//				else if (cmbDueDays.getSelectedIndex()==7) {sql = sql + 
//					"and (round((extract(epoch from('"+cur_date+"' - ( d.date_paid + interval '1 day') - (((('"+cur_date+"' - d.date_paid))/7)*2) - interval '1 day' - "+overdue_deduction+")/86400)::int)))  >  180	  \n";}
//
//				sql = sql +				
//
//				/*
//				"	and ((now() - d.date_paid - '1 days') - " +
//				"		((((now() - d.date_paid))/7)*2) - interval '1 day' -" +
//				"		( case when acct_id in ( '03-02-04-000', '03-02-05-000', '01-03-06-001', '01-03-06-000', '04007001' ) then interval '30 day'\r\n" + 
//				"		else ( case when acct_id in ( '08-01-05-000', '08-02-05-000' ) then interval '60 day' else interval '1 day' end ) end ) \n" +
//				"		) >= '0 day'   \n\n"	+	
//				*/
//
//				"order by c.cv_no\n" + 
//				"		) z\n" + 
//				"\n" + 
//				"		group by z.entity_name ) c on c.entity_name = a.entity_name \n";
//
//				System.out.printf("SQL for preview selected: %s", sql);
//				pgSelect db = new pgSelect();
//				db.select(sql);
//				if(db.isNotNull()){
//					for(int x=0; x < db.getRowCount(); x++){
//						// Adding of row in table
//						modelMain.addRow(db.getResult()[x]);
//						listModel.addElement(modelMain.getRowCount());				
//					}	
//
//					totalSOAliqui(modelMain, modelTotal);				
//				}		
//
//				else {
//					modelLiq_part_total = new modelLiquidationSOA_2();
//					modelLiq_part_total.addRow(new Object[] { "", "Total",null, null,new BigDecimal(0.00)});
//
//					tblLiq_part_total = new _JTableTotal(modelLiq_part, tblLiq_part);
//					tblLiq_part_total.setFont(dialog11Bold);
//					scrollLiq_part_total.setViewportView(tblLiq_part_total);
//					((_JTableTotal) tblLiq_part_total).setTotalLabel(0);
//					JOptionPane.showMessageDialog(getContentPane(),"No unliquidated CA as of the current date","Information",JOptionPane.INFORMATION_MESSAGE);
//				}
//
//				adjustRowHeight();
//				tblLiq_part.packAll();
//
//				FncGlobal.stopProgress();
//				return null;
//			}
//		};
//		sw.execute();
//		
//	}


	//Enable/Disable all components inside JPanel
	public static void enable_fields(Boolean x){//used

		lblDivision.setEnabled(x);	
		lblDepartment.setEnabled(x);	
		lblAvailerType.setEnabled(x);	
		lblAvailer.setEnabled(x);	

		lookupDiv.setEnabled(x);
		lookupDep.setEnabled(x);	
		lookupAvailerType.setEnabled(x);	
		//lookupAvailer.setEnabled(x);	

		tagDivision.setEnabled(x);			
		tagDepartment.setEnabled(x);					
		tagAvailer.setEnabled(x);	
		tagAvailerType.setEnabled(x);	

		rbtnDueDays.setEnabled(x);
		rbtnYear.setEnabled(x);
		rbtnCutoffDate.setEnabled(x);
		rbtnAll.setEnabled(x);

	}

	public void refresh_fields(){//used

		lookupDiv.setValue(null);	
		lookupDep.setValue(null);	
		lookupAvailerType.setValue(null);	
		lookupAvailer.setValue(null);	

		tagDivision.setTag("");
		tagDepartment.setTag("");
		tagAvailerType.setTag("");	
		tagAvailer.setTag("");

		rbtnCutoffDate.setSelected(false);
		rbtnYear.setSelected(false);
		rbtnDueDays.setSelected(false);
		rbtnAll.setSelected(true);

		dteCutoff.setDate(null);
		cmbYear.setSelectedIndex(0);	
		cmbDueDays.setSelectedIndex(0);		

	}

	public void refresh_tablesMain(){//used

		//reset table 1
		FncTables.clearTable(modelLiq_part);
		FncTables.clearTable(modelLiq_part_total);			
		rowHeaderLiq_part = tblLiq_part.getRowHeader22();
		scrollLiq_part.setRowHeaderView(rowHeaderLiq_part);	
		modelLiq_part_total.addRow(new Object[] { "", "Total", null, null, new BigDecimal(0.00) });		
		tblLiq_part_total = new _JTableTotal(modelLiq_part_total, tblLiq_part);
		tblLiq_part_total.setFont(dialog11Bold);
		scrollLiq_part_total.setViewportView(tblLiq_part_total);
		((_JTableTotal) tblLiq_part_total).setTotalLabel(0);

	}

	public static void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d, Boolean e ){//used
		btnGenerateSelected.setEnabled(a);
		btnGenerateAll.setEnabled(b);
		btnPreviewSelected.setEnabled(c);
		btnCancel.setEnabled(d);	
		btnPreviewSelectedAll.setEnabled(e);
	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();

		//enableButtons(true, true, false, true, false);
		//enableButtons(false, false, false, false, false);
		enableButtons(false, true, false, false, false);
		enable_fields(true);

		lookupDiv.setLookupSQL(getDivision());
		lookupDep.setLookupSQL( getDepartment());
		lookupAvailerType.setLookupSQL(getAvailerType());
		lookupAvailer.setLookupSQL(getEntityList());

		rbtnForATD.setEnabled(true);
		lookupCompany.setValue(co_id);
	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//used

		if(e.getActionCommand().equals("GenerateAll") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "9")==true){ generateAll(); }
		else if (e.getActionCommand().equals("GenerateAll") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "9")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview/print SOA.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if (e.getActionCommand().equals("GenerateSelected") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "9")==true) { generateSelected(); } //ok  
		else if (e.getActionCommand().equals("GenerateSelected") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "9")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview/print SOA.","Information",JOptionPane.INFORMATION_MESSAGE); }

		if(e.getActionCommand().equals("Cancel")){ cancel(); } //ok

		if(e.getActionCommand().equals("PreviewSelected") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "9")==true){ preview();	}

			/*int row = tblLiq_part.getSelectedRow();	

			if (row==-1){				

				JOptionPane.showMessageDialog(getContentPane(),"Please select row to preview.","Information",JOptionPane.INFORMATION_MESSAGE);

			}
			else {

				preview();	} //ok 
		}*/
		
		if(e.getActionCommand().equals("PreviewAll") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "9")==true){ previewAll();	}
		
		else if (e.getActionCommand().equals("PreviewSelected") && FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "9")==false ) 
		{ JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to preview/print SOA.","Information",JOptionPane.INFORMATION_MESSAGE); }


	}

	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 2)) {

		}	
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

	private void generateAll(){

		/*if (JOptionPane.showConfirmDialog(getContentPane(), "Generate all unliquidated CAs? \n" +
				"This will refresh all selected fields.", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			enable_fields(false);
			refresh_fields();
			displayLiquidationSOA_all(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total );
			enableButtons(false, false, true, true);} //ok

		else {}		
		 */
		
//		enable_fields(false);
//		lookupAvailer.setEnabled(false);
//		refresh_fields();
//		displayLiquidationSOA_all(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total );
//		//enableButtons(false, false, true, true, true);
//		enableButtons(false, false, false, true, true);
		
		
		String availer_type = "";
		String availer = "";
		
		Date cur_date 		= null;
		String soa_year   	= "";
		Integer over_min	= null;
		Integer over_max	= null;
		Integer soa_index	= cmbDueDays.getSelectedIndex() +1 ;

		if (rbtnCutoffDate.isSelected()) { cur_date = dteCutoff.getDate(); over_min=0; over_max=1000; soa_index = 0; }
		else if (rbtnYear.isSelected()) { soa_year = (String) cmbYear.getSelectedItem(); cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());  over_min=0;over_max=1000; soa_index = 0;}
		else if (rbtnDueDays.isSelected()) {cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL()); 

		if(soa_index==1){over_min=0;	over_max=5;}
		if(soa_index==2){over_min=6;	over_max=30;}
		if(soa_index==3){over_min=31;	over_max=60;}
		if(soa_index==4){over_min=61;	over_max=90;}
		if(soa_index==5){over_min=91;	over_max=120;}
		if(soa_index==6){over_min=121;	over_max=150;}
		if(soa_index==7){over_min=151;	over_max=180;}
		if(soa_index==8){over_min=180;	over_max=1000;}				

		}
		else if (rbtnAll.isSelected()) {cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());  over_min=0;over_max=1000; soa_index = 0;}	
		
		displayLiquidationSOA_all(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total, cur_date, soa_year, for_atd, availer, availer_type, soa_index, over_min, over_max, co_id, true);
		tblLiq_part.setEnabled(false);
		enable_fields(false);
		lookupAvailer.setEnabled(false);
		refresh_fields();
		enableButtons(false, false, false, true, true);
		
	}

	private void generateSelected(){
		
		String availer_type = lookupAvailerType.getValue();
		String availer = lookupAvailer.getValue();
		
		Date cur_date 		= null;
		String soa_year   	= "";
		Integer over_min	= null;
		Integer over_max	= null;
		Integer soa_index	= cmbDueDays.getSelectedIndex() +1 ;

		if (rbtnCutoffDate.isSelected()) { cur_date = dteCutoff.getDate(); over_min=0; over_max=1000; soa_index = 0; }
		else if (rbtnYear.isSelected()) { soa_year = (String) cmbYear.getSelectedItem(); cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());  over_min=0;over_max=1000; soa_index = 0;}
		else if (rbtnDueDays.isSelected()) {cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL()); 

		if(soa_index==1){over_min=0;	over_max=5;}
		if(soa_index==2){over_min=6;	over_max=30;}
		if(soa_index==3){over_min=31;	over_max=60;}
		if(soa_index==4){over_min=61;	over_max=90;}
		if(soa_index==5){over_min=91;	over_max=120;}
		if(soa_index==6){over_min=121;	over_max=150;}
		if(soa_index==7){over_min=151;	over_max=180;}
		if(soa_index==8){over_min=180;	over_max=1000;}				

		}
		else if (rbtnAll.isSelected()) {cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());  over_min=0;over_max=1000; soa_index = 0;}	
		
		displayLiquidationSOA_selected_v4(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total, cur_date, soa_year, for_atd, availer, availer_type, soa_index, over_min, over_max, co_id, false);
		tblLiq_part.setEnabled(true);
//		//***ADDED by jed 2019-01-30***//
//		if (!availer_type.equals(null) && availer.equals("")){
//			//displayLiquidationSOA_selected(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total );
//			displayLiquidationSOA_selected_v3(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total );
//			enableButtons(false, false, false, true, false);
//		}
//		else if (!availer_type.equals("") && !availer.equals("")){
//			displayLiquidationSOA_selected_v2(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total );
//			enableButtons(false, false, false, true, false);
//		}
//		else{
//			displayLiquidationSOA_selected_v2(modelLiq_part, rowHeaderLiq_part, modelLiq_part_total );
//			enableButtons(false, false, false, true, false);
//		}
		
		//enableButtons(false, false, true, true, true);
	}

	private void cancel(){
		refresh_fields(); 
		enable_fields(true);
		lookupAvailer.setEnabled(false);//**ADDED BY JED 2019-01-31**//
		refresh_tablesMain(); 
		//enableButtons(true, true, false, true, false);
		//enableButtons(false, false, false, false, false);
		enableButtons(false, true, false, false, false);
		lookupDiv.setLookupSQL(getDivision());
		lookupDep.setLookupSQL( getDepartment());
		lookupAvailerType.setLookupSQL(getAvailerType());
		lookupAvailer.setLookupSQL(getEntityList());
		rbtnCutoffDate.setSelected(false);	rbtnYear.setSelected(false);
		rbtnDueDays.setSelected(false);		rbtnAll.setSelected(true);
		cmbYear.setEnabled(false);
		dteCutoff.setEnabled(false);
		cmbDueDays.setEnabled(false);	
		tblLiq_part.setEnabled(true);
	}

	private void preview(){

		Date cur_date 		= null;
		String soa_year   	= "";
		Integer soa_due     = null;
		Integer over_min	= null;
		Integer over_max	= null;
		Integer soa_index	= cmbDueDays.getSelectedIndex() +1 ;

		if (rbtnCutoffDate.isSelected()) { cur_date = dteCutoff.getDate(); over_min=0; over_max=1000; soa_index = 0; }
		else if (rbtnYear.isSelected()) { soa_year = (String) cmbYear.getSelectedItem(); cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());  over_min=0;over_max=1000; soa_index = 0;}
		else if (rbtnDueDays.isSelected()) {cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL()); 

		if(soa_index==1){over_min=0;	over_max=5;}
		if(soa_index==2){over_min=6;	over_max=30;}
		if(soa_index==3){over_min=31;	over_max=60;}
		if(soa_index==4){over_min=61;	over_max=90;}
		if(soa_index==5){over_min=91;	over_max=120;}
		if(soa_index==6){over_min=121;	over_max=150;}
		if(soa_index==7){over_min=151;	over_max=180;}
		if(soa_index==8){over_min=180;	over_max=1000;}				

		}
		else if (rbtnAll.isSelected()) {cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());  over_min=0;over_max=1000; soa_index = 0;}		

		String criteria = "Unliquidated CA Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		int row = tblLiq_part.getSelectedRow();	
		String entity_id	= (modelLiq_part.getValueAt(row,1).toString());	
		Object[] soa_hdr 	= getDivDeptDetails(entity_id);	
		String entity		= (modelLiq_part.getValueAt(row,2).toString());
		
		//--edited by jed 2018-11-13 no dcrf : clients with no dept and div code--// 
		String div			= "";
		String dept_name	= "";
		String dept_alias	= "";
		
		if (soa_hdr == null){}
		else{
				div			= (String) soa_hdr[0];
				dept_name	= (String) soa_hdr[1];
				dept_alias	= (String) soa_hdr[2];
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cur_date",  cur_date);
		mapParameters.put("payee1", entity);
		mapParameters.put("dept", dept_name);
		mapParameters.put("dept_alias", dept_alias);
		mapParameters.put("div", div);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("soa_year", soa_year);
		mapParameters.put("soa_due", soa_due);
		mapParameters.put("over_min", over_min);
		mapParameters.put("over_max", over_max);
		mapParameters.put("soa_index", soa_index);
		mapParameters.put("overdue_deduction", overdue_deduction);
		mapParameters.put("for_atd", for_atd);
		
		FncSystem.out("Report SQL", "SELECT  * FROM view_liquidation_soa_v3('"+cur_date+"', '"+soa_year+"', '"+for_atd+"', '"+entity_id+"', "+soa_index+", "+over_min+", "+over_max+",'"+co_id+"')");
		
		if (rbtnForATD.isSelected()==true) {
			FncReport.generateReport("/Reports/rptSOAatd.jasper", reportTitle, company, mapParameters);	
		} else {
			FncReport.generateReport("/Reports/rptSOA_liqui.jasper", reportTitle, company, mapParameters);	
		}
		
		/*String criteria = "Unliquidated CA Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		ArrayList<String> iftrue = new ArrayList<String>();
		ArrayList<String> entity_idv2_ = new ArrayList<String>();
		//ArrayList<String> entity = new ArrayList<String>();
		
		String div_ = "";
		String dept_name = "";
		String entity_ = "";
		
		Date cur_date 		= null;
		String soa_year   	= null;
		Integer soa_due     = null;
		Integer over_min	= null;
		Integer over_max	= null;
		Integer soa_index	= cmbDueDays.getSelectedIndex() +1 ;
		
		if (rbtnCutoffDate.isSelected()) { cur_date = dteCutoff.getDate(); over_min=0; over_max=1000; soa_index = 0; }
		else if (rbtnYear.isSelected()) { soa_year = (String) cmbYear.getSelectedItem(); cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());  over_min=0;over_max=1000; soa_index = 0;}
		else if (rbtnDueDays.isSelected()) {cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL()); 

		if(soa_index==1){over_min=0;	over_max=5;}
		if(soa_index==2){over_min=6;	over_max=30;}
		if(soa_index==3){over_min=31;	over_max=60;}
		if(soa_index==4){over_min=61;	over_max=90;}
		if(soa_index==5){over_min=91;	over_max=120;}
		if(soa_index==6){over_min=121;	over_max=150;}
		if(soa_index==7){over_min=151;	over_max=180;}
		if(soa_index==8){over_min=180;	over_max=1000;}				

		}
		else if (rbtnAll.isSelected())
			{
				cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL()); 
				over_min=0;over_max=1000; soa_index = 0;
			}
		
		for (int i = 0; i < modelLiq_part.getRowCount(); i++) {
			
			Boolean SelectedItem = (Boolean) modelLiq_part.getValueAt(i, 0);
			String entity_id1 = (String) modelLiq_part.getValueAt(i, 1);
			//String entity1 = (String) modelLiq_part.getValueAt(i, 2);
			
			Object[] soa_hdr 	= getDivDeptDetails(entity_);	
			div_			= (String) soa_hdr[0];
			dept_name	= (String) soa_hdr[1];

			if (SelectedItem) {
				iftrue.add(modelLiq_part.getValueAt(i, 1).toString());
				entity_idv2_.add(entity_id1);
				//entity.add(entity1);
				
			}
		}
		if (iftrue.isEmpty()) {
			JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
			return;
		}
		
		System.out.printf("\n%s\n", entity_idv2_);
		
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cur_date",  cur_date);
		//mapParameters.put("payee1", entity.toString().replaceAll("\\[|\\]", ""));
		//mapParameters.put("dept", dept_name);
		//mapParameters.put("div", div_);
		mapParameters.put("entity_idv2", entity_idv2_);
		mapParameters.put("entity_id", entity_);
		mapParameters.put("soa_year", soa_year);
		mapParameters.put("soa_due", soa_due);
		mapParameters.put("over_min", over_min);
		mapParameters.put("over_max", over_max);
		mapParameters.put("soa_index", soa_index);
		mapParameters.put("overdue_deduction", overdue_deduction);
		mapParameters.put("for_atd", for_atd);

		FncReport.generateReport("/Reports/rptSOA_liqui.jasper", reportTitle, company, mapParameters);*/
	}
	
	private void previewAll(){

		Date cur_date 		= null;
		String soa_year   	= null;
		Integer soa_due     = null;
		Integer over_min	= null;
		Integer over_max	= null;
		Integer soa_index	= cmbDueDays.getSelectedIndex() +1 ;

		if (rbtnCutoffDate.isSelected()) { cur_date = dteCutoff.getDate(); over_min=0; over_max=1000; soa_index = 0; }
		else if (rbtnYear.isSelected()) { soa_year = (String) cmbYear.getSelectedItem(); cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());  over_min=0;over_max=1000; soa_index = 0;}
		else if (rbtnDueDays.isSelected()) {cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL()); 

		if(soa_index==1){over_min=0;	over_max=5;}
		if(soa_index==2){over_min=6;	over_max=30;}
		if(soa_index==3){over_min=31;	over_max=60;}
		if(soa_index==4){over_min=61;	over_max=90;}
		if(soa_index==5){over_min=91;	over_max=120;}
		if(soa_index==6){over_min=121;	over_max=150;}
		if(soa_index==7){over_min=151;	over_max=180;}
		if(soa_index==8){over_min=180;	over_max=1000;}				

		}
		else if (rbtnAll.isSelected()) {cur_date = FncGlobal.dateFormat(FncGlobal.getDateSQL());  over_min=0;over_max=1000; soa_index = 0;}		

		String criteria = "Unliquidated CA Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		

		int row = tblLiq_part.getSelectedRow();	

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
		mapParameters.put("prepared_by", UserInfo.FullName);
		mapParameters.put("cur_date",  cur_date);
		mapParameters.put("payee1", "");
		mapParameters.put("dept", "");
		mapParameters.put("dept_alias", "");
		mapParameters.put("div", "");
		mapParameters.put("entity_id", "");
		mapParameters.put("soa_year", soa_year);
		mapParameters.put("soa_due", soa_due);
		mapParameters.put("over_min", over_min);
		mapParameters.put("over_max", over_max);
		mapParameters.put("soa_index", soa_index);
		mapParameters.put("overdue_deduction", overdue_deduction);
		mapParameters.put("for_atd", for_atd);
		
		if (rbtnForATD.isSelected()==true) {
			FncReport.generateReport("/Reports/rptSOAatd.jasper", reportTitle, company, mapParameters);	
		} else {
			FncReport.generateReport("/Reports/rptSOA_liqui_all.jasper", reportTitle, company, mapParameters);	
		}
	
	}


	//select, lookup and get statement	
	public static String getDivision(){//used

		String sql = "select division_code as \"Div Code\", " +
		"trim(division_name) as \"Div Name\", " +
		"trim(division_alias) as \"Div Alias\" " +
		"from mf_division " ;		
		return sql;

	}	

	public static String getDepartment(){//used

		String div = lookupDiv.getText();

		String sql = "select dept_code as \"Dept Code\", " +
		"trim(dept_name) as \"Dept Name\", " +
		"trim(dept_alias) as \"Dept Alias\" " +
		"from mf_department " ;
		if (div.equals("")){sql = sql + "";} 
		else {sql = sql + "where division_code = '"+div+"' ";}

		return sql;

	}	

	public static String getAvailerType(){//used

		return

		"select entity_type_id as \"Entity Type ID\" ,  " +
		"trim(entity_type_desc) as \"Entity Type Desc.\" , " +
		"wtax_id as \"Tax ID\" \n" + 
		"from mf_entity_type where status_id = 'A'" +
		"order by entity_type_id \r\n" ;
	}	

	public static String getEntityList(){//used

		String entity_type_id = lookupAvailerType.getText().trim();
		String div_id		  = lookupDiv.getText().trim();
		String dept_id  	  = lookupDep.getText().trim();

		String sql = 
			"select \r\n" + 

			"distinct on (a.entity_id) " +
			"a.entity_id as \"Entity ID\"  ,\r\n" + 
			"upper(trim(a.entity_name))  as \"Entity Name\",\r\n" + 
			"upper(trim(a.entity_alias)) as \"Entity Alias\" \r\n" + 

			"from rf_entity a\r\n" + 				
			"left join rf_entity_assigned_type b on a.entity_id=b.entity_id\r\n" + 
			"left join em_employee c on a.entity_id = c.entity_id\r\n" + 

			"where a.status_id = 'A'\r\n" ; 
		if (entity_type_id.equals("")){sql = sql + "";} 
		else {sql = sql + "and b.entity_type_id = '"+entity_type_id+"' ";}
		if (div_id.equals("")){sql = sql + "";} 
		else {sql = sql + "and c.div_code = '"+div_id+"' ";}
		if (dept_id.equals("")){sql = sql + "";} 
		else {sql = sql + "and c.dept_code = '"+dept_id+"' ";}
		sql = sql +
		"order by a.entity_id, a.entity_name \r\n" + 
		"   ";		
		return sql;

	}

	private Object [] getDivDeptDetails(String string) {//used
		
		String SQL = 
			"select upper(c.division_alias), upper(trim(b.dept_name)), upper(trim(b.dept_alias))  \n" +
			"from em_employee a \n" +
			"left join mf_department b on trim(a.dept_code)=trim(b.dept_code)  \n"+
			"left join mf_division c on trim(a.div_code)=trim(c.division_code)  \n"+
			"where a.entity_id = '"+string+"' " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}


	//table operations
	public void totalSOAliqui(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//used

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal trans_amt 	= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { trans_amt 	= trans_amt.add(((BigDecimal) modelMain.getValueAt(x,4)));} 
			catch (NullPointerException e) { trans_amt 	= trans_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "", "Total", null,  null, trans_amt});
	}

	private void adjustRowHeight(){//used

		int rw = tblLiq_part.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblLiq_part.setRowHeight(x, 22);			
			x++;
		}
	}


	//others
	private static String getMonthYear() {//used		

		String x = "";

		DateFormat df    = new SimpleDateFormat("MM-dd-yyyy");	
		String year_str	 = df.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()));	
		String year_full=  year_str.substring(6);

		x = year_full;

		return x;

	}

}