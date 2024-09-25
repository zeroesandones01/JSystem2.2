package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
import Dialogs.dlg_G2GTcost_Amt;
import Dialogs.dlg_MotherMaidenName;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTabbedPane;
import components._JTableMain;
import components._JTableTotal;
import interfaces._GUI;
import tablemodel.modelEPEBG2G;
import tablemodel.modelNTPWorkItems;
import tablemodel.model_hdmf_payments;
import tablemodel.model_hdmf_postInspection_card;
import tablemodel.model_hdmf_schedule;
import tablemodel.model_hdmfinfo_loanfilingstatus;

public class hdmfInfo_maintab extends JXPanel implements _GUI {
	
	private static final long serialVersionUID = 1770006858199175887L;
	private CARD main_card;
	static Dimension size = new Dimension(738, 351);
	
	static Border line = BorderFactory.createLineBorder(Color.GRAY);
	
	private _JTabbedPane tabHDMF;
	private _JTabbedPane tabHDMF_hdmfDetails;
	
	private JXPanel panLoanFilingStatusTab;
	private JXPanel panHDMFUnitInspection;
	private JXPanel panHDMFDetails;
	private JXPanel panHDMFREM;
	private JXPanel pnlEPEBG2G;
	private _JScrollPaneMain scrollEPEBG2G;
	private static JList rowHeaderEPEBG2G;
	private static _JTableMain tblEPEBG2G;
	private static modelEPEBG2G modelEPEB;
	
	private _JScrollPaneTotal scrollG2GReceiptTotal;
	private _JTableTotal tblG2GReceiptTotal;
	private static modelEPEBG2G modelG2GReceiptTotal;
	
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
	private static JLabel lblEPEB; 
	private JLabel lblETD; 
	private JLabel lblCOC; 
	private JLabel lblCOM5; 
	private JLabel lblCOM3; 
	private JLabel lblROW;
	private JLabel lblWS;
	private JLabel lblTD;
	private JLabel lblRetTotal; 
	private JLabel lblEPEBret;
	
	private static _JXFormattedTextField txtRet; 
	private static _JXFormattedTextField txtEPEB; 
	private static _JXFormattedTextField txtETD; 
	private static _JXFormattedTextField txtCOC; 
	private static _JXFormattedTextField txtCOM5; 
	private static _JXFormattedTextField txtCOM3; 
	private static _JXFormattedTextField txtROW;
	private static _JXFormattedTextField txtWS;
	private static _JXFormattedTextField txtTD;
	private static _JXFormattedTextField txtAMA;
	private static _JXFormattedTextField txtServiceFee;
	private static _JXFormattedTextField txtRetTotal; 
	
	private static _JXFormattedTextField txtPctgEPEB; 
	private static _JXFormattedTextField txtPctgETD; 
	private static _JXFormattedTextField txtPctgCOC; 
	private static _JXFormattedTextField txtPctgCOM5;
	private static _JXFormattedTextField txtPctgROW;
	private static _JXFormattedTextField txtPctgWS;
	private static _JXFormattedTextField txtPctgTD;
	private static _JXFormattedTextField txtPctgAMA;
	
	private static _JXFormattedTextField txtPctgRetEPEB; 
	private static _JXFormattedTextField txtPctgRetETD; 
	private static _JXFormattedTextField txtPctgRetCOC; 
	private static _JXFormattedTextField txtPctgRetCOM5;
	private static _JXFormattedTextField txtPctgRetROW;
	private static _JXFormattedTextField txtPctgRetWS;
	private static _JXFormattedTextField txtPctgRetTD;
	private static _JXFormattedTextField txtPctgRetAMA;
	
	private static _JXFormattedTextField txtRetRet; 
	private static _JXFormattedTextField txtRetEPEB; 
	private static _JXFormattedTextField txtRetETD; 
	private static _JXFormattedTextField txtRetCOC; 
	private static _JXFormattedTextField txtRetCOM5; 
	private static _JXFormattedTextField txtRetCOM3; 
	private static _JXFormattedTextField txtRetROW;
	private static _JXFormattedTextField txtRetWS;
	private static _JXFormattedTextField txtRetTD;
	private static _JXFormattedTextField txtRetAMA;
	private static _JXFormattedTextField txtRetRetTotal; 
	
	private static JSplitPane splitReleasedLoanDetail; 
	
	private JLabel lblLoanable;
	private static JLabel lblLoanableAmount;
	private static JLabel lblVAT;
	private static JLabel lblLoanableAmountNetVAT;
	
	private JLabel lblSummRecapitulation;
	private static JLabel lblSummRecapitulationAmount;
	private JLabel lblSummRetention;
	private static JLabel lblSummRetentionAmount;
	private JLabel lblNetProceeds;
	private static JLabel lblNetProceedsAmount;
	private JLabel lblSummReturnedRetention;
	private static JLabel lblSummReturnedRetentionAmount;
	private static JLabel lblSummReturnedAMA;
	private JLabel lblTotalNetProceeds;
	private static JLabel lblTotalNetProceedsAmount;
	
	private Font fontPlainSanSerNine = new Font("SansSerif", Font.PLAIN, 9);
	private Font fontPlainSanSerTens = new Font("SansSerif", Font.PLAIN, 10);
	private Font fontBoldSanSerEleven = new Font("SansSerif", Font.BOLD, 11);
	private Font fontBoldSanSerTens = new Font("SansSerif", Font.BOLD, 10);
	private Font fontPlainSanSerEleven = new Font("SansSerif", Font.PLAIN, 11);
	private dlg_G2GTcost_Amt updateTcostAmt;
	
	private static String entity_id;
	private static String proj_id;
	private static String pbl_id;
	private static String seq_no;
	
	public hdmfInfo_maintab(CARD card) {
		this.main_card = card;
		initGUI(); 
	}

	public hdmfInfo_maintab(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public hdmfInfo_maintab(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public hdmfInfo_maintab(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	private static JLabel lblRatSales; // ADDED BY MONIQUE DTD 10-23-2023; REFER TO DCRF#2766
	
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			this.add(panMain, BorderLayout.CENTER);
			panMain.setBorder(new EmptyBorder(0, 5, 5, 5));
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					tabHDMF = new _JTabbedPane();
					panCenter.add(tabHDMF, BorderLayout.CENTER);
					tabHDMF.setBorder(new EmptyBorder(5, 5, 5, 5));
					if(UserInfo.EmployeeCode.equals("000645")){
						tabHDMF.setFont(new Font("Tahoma", Font.BOLD, 7).deriveFont(1));
					}
					{
						CreateHDMFDetailsTab();
						CreateHDMFUnitInspectionTab();
						CreateReleasedLoanDetailTab();
						CreateLoanFilingStatusTab();
						createEPEBG2G();
						
						panHDMFREM = new tab_remConversion(this); 
						
						tabHDMF.add("Loan Filing Status", panLoanFilingStatusTab);
						tabHDMF.add("Released Loan Detail", splitReleasedLoanDetail);
						tabHDMF.add("HDMF Unit Inpection", panHDMFUnitInspection);
						tabHDMF.add("HDMF Details", panHDMFDetails);
						tabHDMF.add("REM Conversion Status", panHDMFREM);
						tabHDMF.add("G2G Receipts", pnlEPEBG2G);
						
					}
				}
			}
		}
	}
	
	private void createEPEBG2G(){
		pnlEPEBG2G = new JXPanel(new BorderLayout(5, 5));
		{// **ADDED BY JED 2019-04-16 : TO SELECT AND DISPLAY 2ND LOTS**//
			JPanel pnlG2GNorth = new JPanel(new BorderLayout(5, 5));
			pnlEPEBG2G.add(pnlG2GNorth, BorderLayout.NORTH);
			pnlG2GNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			{
				JPanel pnlWestLabel = new JPanel(new BorderLayout(5, 5));
				pnlG2GNorth.add(pnlWestLabel, BorderLayout.WEST);
				{
					JLabel lblLot = new JLabel("Select Lot to Preview");
					pnlWestLabel.add(lblLot);
				}
			}
			{
				JPanel pnlG2GCenter = new JPanel(new GridLayout(1, 4, 5, 5));
				pnlG2GNorth.add(pnlG2GCenter, BorderLayout.CENTER);
				{
					JComboBox cmblotG2G = new JComboBox();
					pnlG2GCenter.add(cmblotG2G);
					cmblotG2G.setModel(new DefaultComboBoxModel(getLots(entity_id, proj_id, pbl_id, seq_no)));
					cmblotG2G.addItemListener(new ItemListener() {

						@Override
						public void itemStateChanged(ItemEvent e) {
							int selected_index = ((JComboBox) e.getSource()).getSelectedIndex();
							String entity_id = hdmfInfo_maintab.entity_id;
							String pbl_id = hdmfInfo_maintab.pbl_id;
							String proj_id = hdmfInfo_maintab.proj_id;

							if (selected_index == 0) {
								/*
								 * System.out.printf("The value of seq_no is: %s\n", seq_no);
								 * System.out.printf("The value of pbl_id is: %s\n", pbl_id);
								 * System.out.printf("The value of entity_id is: %s\n", entity_id);
								 * System.out.printf("The value of proj_id is: %s\n", proj_id);
								 */
								_CARD.displayTCostDetails(modelTCT, modelTCTTotal, entity_id,
										proj_id, pbl_id, seq_no);
							}

							if (selected_index == 1) {
								String other_pbl_id = checkOtherLots(entity_id, pbl_id, proj_id,
										seq_no);
								/*
								 * System.out.printf("The value of seq_no is: %s\n", seq_no);
								 * System.out.printf("The value of other_pbl_id is: %s\n",
								 * other_pbl_id);
								 * System.out.printf("The value of entity_id is: %s\n", entity_id);
								 * System.out.printf("The value of proj_id is: %s\n", proj_id);
								 */
								_CARD.displayTCostDetails(modelTCT, modelTCTTotal, entity_id,
										proj_id, other_pbl_id, seq_no);
							}
						}
					});
				}
				{
					pnlG2GCenter.add(Box.createHorizontalBox());
					pnlG2GCenter.add(Box.createHorizontalBox());
					pnlG2GCenter.add(Box.createHorizontalBox());
				}
			}
		}
	
		{
			scrollEPEBG2G = new _JScrollPaneMain();
			pnlEPEBG2G.add(scrollEPEBG2G, BorderLayout.CENTER);
			scrollEPEBG2G.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollEPEBG2G.setBorder(line);
			{
				{
					modelEPEB = new modelEPEBG2G();
					tblEPEBG2G = new _JTableMain(modelEPEB);
					
					rowHeaderEPEBG2G = tblEPEBG2G.getRowHeader();
					scrollEPEBG2G.setViewportView(tblEPEBG2G);
					
					modelEPEB.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							if (e.getType() == TableModelEvent.INSERT) {
								((DefaultListModel) rowHeaderEPEBG2G.getModel())
								.addElement(modelEPEB.getRowCount());
								scrollG2GReceiptTotal.setRowHeaderView(FncTables
										.getRowHeader_Footer(Integer.toString(modelEPEB.getRowCount())));
							}
							if (e.getType() == TableModelEvent.DELETE) {
								if (modelEPEB.getRowCount() == 0) {
									rowHeaderEPEBG2G.setModel(new DefaultListModel());
									scrollG2GReceiptTotal.setRowHeaderView(FncTables.getRowHeader_Footer(
											Integer.toString(modelEPEB.getRowCount())));
								}
							}
						}
					});
					
					tblEPEBG2G.hideColumns("Rec. ID");
					
					tblEPEBG2G.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e){
							if(e.isPopupTrigger()){
								try {
									initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
								} catch (NullPointerException e1) { }
							}
						}

						public void mousePressed(MouseEvent e){
							if(e.isPopupTrigger()){
								try {
									initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
								} catch (NullPointerException e1) { }
							}
						}

						public JPopupMenu initializeMenu(MouseEvent e){
							_JTableMain table = (_JTableMain) e.getSource();
							//int[] rows = table.getSelectedRows();
							int row = table.getModelRow(table.getSelectedRow());
							int column = table.convertColumnIndexToModel(0);
							//final String status = (String) modelAccountStatusHistory.getValueAt(row, table.convertColumnIndexToModel(1));
							final Integer rec_id = (Integer) modelEPEB.getValueAt(row, 0);
							final String jv_no = (String) modelEPEB.getValueAt(row, 5);
							final String batch_no = (String) modelEPEB.getValueAt(row, 6);

							JPopupMenu menu = new JPopupMenu();

							JMenuItem menuViewRequests = new JMenuItem("Update Tcost Amt");
							menu.add(menuViewRequests);
							menuViewRequests.setFont(menuViewRequests.getFont().deriveFont(10f));
							menuViewRequests.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									updateTcostAmt = new dlg_G2GTcost_Amt(FncGlobal.homeMDI, "Tcost Amt", rec_id, entity_id, proj_id, pbl_id, seq_no, batch_no);				
									updateTcostAmt.setLocationRelativeTo(FncGlobal.homeMDI);
									updateTcostAmt.setVisible(true);
								
								}
							});

							if(jv_no != null){
								return null;
							}else{
								return menu;
							}
						}
					});
					
				}
				{
					rowHeaderEPEBG2G = tblEPEBG2G.getRowHeader();
					rowHeaderEPEBG2G.setModel(new DefaultListModel());
					scrollEPEBG2G.setRowHeaderView(rowHeaderEPEBG2G);
					scrollEPEBG2G.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
				}	
			}
		}
		{
			scrollG2GReceiptTotal = new _JScrollPaneTotal(scrollEPEBG2G);
			pnlEPEBG2G.add(scrollG2GReceiptTotal, BorderLayout.SOUTH);
			{
				modelG2GReceiptTotal = new modelEPEBG2G();
				modelG2GReceiptTotal.addRow(new Object[] {null, "Total", null, null, null, null});

				tblG2GReceiptTotal = new _JTableTotal(modelG2GReceiptTotal, tblEPEBG2G);
				scrollG2GReceiptTotal.setViewportView(tblG2GReceiptTotal);
				tblG2GReceiptTotal.hideColumns("Rec. ID");
				tblG2GReceiptTotal.setTotalLabel(1);
			}
		}
		
	}
	
	private static void totalG2GReceipt(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		BigDecimal amount = new BigDecimal("0.00");

		for (int x = 0; x < modelMain.getRowCount(); x++) {
			amount = amount.add((BigDecimal) ((BigDecimal) modelMain.getValueAt(x, 3) == null ? new BigDecimal("0.00")
					: modelMain.getValueAt(x, 3)));
		}
		modelTotal.setValueAt(amount, 0, 3);
	}

	private void CreateLoanFilingStatusTab() {
		panLoanFilingStatusTab = new JXPanel(new BorderLayout(5, 5));
		panLoanFilingStatusTab.setOpaque(isOpaque());
		{
			scrollLoanFilingStatus = new JScrollPane();
			panLoanFilingStatusTab.add(scrollLoanFilingStatus, BorderLayout.CENTER);
			scrollLoanFilingStatus.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollLoanFilingStatus.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollLoanFilingStatus.setBorder(line);
			{
				{
					modelLoanFilingStatus = new model_hdmfinfo_loanfilingstatus();
					tblLoanFilingStatus = new _JTableMain(modelLoanFilingStatus);
					
					rowLoanFilingStatus = tblLoanFilingStatus.getRowHeader();
					scrollLoanFilingStatus.setViewportView(tblLoanFilingStatus);
					
					tblLoanFilingStatus.getColumnModel().getColumn(0).setPreferredWidth(227);
					tblLoanFilingStatus.getColumnModel().getColumn(1).setPreferredWidth(130);
					tblLoanFilingStatus.getColumnModel().getColumn(2).setPreferredWidth(130);
					tblLoanFilingStatus.getColumnModel().getColumn(3).setPreferredWidth(100);
					tblLoanFilingStatus.getColumnModel().getColumn(4).setPreferredWidth(75);
					tblLoanFilingStatus.getColumnModel().getColumn(5).setPreferredWidth(205);
					tblLoanFilingStatus.getColumnModel().getColumn(6).setPreferredWidth(130);
					
					tblLoanFilingStatus.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
					tblLoanFilingStatus.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
					tblLoanFilingStatus.getColumnModel().getColumn(6).setCellRenderer(new DateRenderer(sdf));
					
					tblLoanFilingStatus.hideColumns("Status Sequence", "entity_id", "proj_id", "pbl_id", "seq_no");
				}
				{
					rowLoanFilingStatus = tblLoanFilingStatus.getRowHeader();
					rowLoanFilingStatus.setModel(new DefaultListModel());
					scrollLoanFilingStatus.setRowHeaderView(rowLoanFilingStatus);
					scrollLoanFilingStatus.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
				}	
			}
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
							JLabel lblRecapitulation = new JLabel("   Recapitulation"); 
							panSep1.add(lblRecapitulation, BorderLayout.PAGE_START);
							lblRecapitulation.setFont(fontBoldSanSerTens);
							lblRecapitulation.setPreferredSize(new Dimension(0, 20));
						}
						{
							JXPanel panRecap = new JXPanel(new GridLayout(11, 2, 5, 3));
							panSep1.add(panRecap, BorderLayout.CENTER);
							{
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
									lblProcessingFee = new JLabel("Filing Fee");
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
											txtPctgRetentionFee = new _JXFormattedTextField("0.0%");
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
									JLabel lblAdvMA = new JLabel("Advanced MA");
									panRecap.add(lblAdvMA);
									lblAdvMA.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panAdvMA = new JXPanel(new BorderLayout(3, 3)); 
									panRecap.add(panAdvMA, BorderLayout.CENTER); 
									{
										{
											txtPctgAMA = new _JXFormattedTextField("0.0%");
											panAdvMA.add(txtPctgAMA, BorderLayout.LINE_START);
											txtPctgAMA.setHorizontalAlignment(JTextField.TRAILING);
											txtPctgAMA.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtPctgAMA.setEnabled(true);
											txtPctgAMA.setEditable(false);	
											txtPctgAMA.setPreferredSize(new Dimension(40, 0));
											txtPctgAMA.setFont(fontPlainSanSerEleven);
											txtPctgAMA.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtAMA = new _JXFormattedTextField("0.00");
											panAdvMA.add(txtAMA, BorderLayout.CENTER);
											txtAMA.setHorizontalAlignment(JTextField.TRAILING);
											txtAMA.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtAMA.setEnabled(true);
											txtAMA.setEditable(false);
											txtAMA.setFont(fontPlainSanSerEleven);	
										}
									} 
								}
								{
									JLabel lblServiceFee = new JLabel("Service Fee");
									panRecap.add(lblServiceFee);
									lblServiceFee.setFont(fontPlainSanSerNine);
								}
								{
									txtServiceFee = new _JXFormattedTextField("0.00");
									panRecap.add(txtServiceFee, BorderLayout.CENTER);
									txtServiceFee.setHorizontalAlignment(JTextField.TRAILING);
									txtServiceFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtServiceFee.setEnabled(true);
									txtServiceFee.setEditable(false);
									txtServiceFee.setFont(fontPlainSanSerEleven); 
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
									txtRecapTotal.setFont(fontPlainSanSerEleven);
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
							JLabel lblRetentions = new JLabel("   Undertaking Deduction"); 
							panSep2.add(lblRetentions, BorderLayout.PAGE_START);
							lblRetentions.setFont(fontBoldSanSerTens); 
							lblRetentions.setPreferredSize(new Dimension(0, 20));
						}
						{
							JXPanel panRetentions = new JXPanel(new GridLayout(10, 2, 5, 3));
							panSep2.add(panRetentions, BorderLayout.CENTER);
							{
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
											txtPctgEPEB = new _JXFormattedTextField("0.0%");
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
											txtPctgETD = new _JXFormattedTextField("0.0%");
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
											txtPctgCOC = new _JXFormattedTextField("0.0%");
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
									lblCOM5 = new JLabel("Comm. Fac.");
									panRetentions.add(lblCOM5);
									lblCOM5.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panCOM5 = new JXPanel(new BorderLayout(3, 3)); 
									panRetentions.add(panCOM5, BorderLayout.CENTER); 
									{
										{
											txtPctgCOM5 = new _JXFormattedTextField("0.0%");
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
											txtPctgROW = new _JXFormattedTextField("0.0%");
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
									lblWS = new JLabel("Water Supply");
									panRetentions.add(lblWS);
									lblWS.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panWS = new JXPanel(new BorderLayout(3, 3)); 
									panRetentions.add(panWS, BorderLayout.CENTER); 
									{
										{
											txtPctgWS = new _JXFormattedTextField("0.0%");
											panWS.add(txtPctgWS, BorderLayout.LINE_START);
											txtPctgWS.setHorizontalAlignment(JTextField.TRAILING);
											txtPctgWS.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtPctgWS.setEnabled(true);
											txtPctgWS.setEditable(false);
											txtPctgWS.setPreferredSize(new Dimension(40, 0));
											txtPctgWS.setFont(fontPlainSanSerEleven);
											txtPctgWS.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtWS = new _JXFormattedTextField("0.00");
											panWS.add(txtWS, BorderLayout.CENTER);
											txtWS.setHorizontalAlignment(JTextField.TRAILING);
											txtWS.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtWS.setEnabled(true);
											txtWS.setEditable(false);	
											txtWS.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblTD = new JLabel("Tax Declaration");
									panRetentions.add(lblTD);
									lblTD.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panTD = new JXPanel(new BorderLayout(3, 3)); 
									panRetentions.add(panTD, BorderLayout.CENTER); 
									{
										{
											txtPctgTD = new _JXFormattedTextField("0.0%");
											panTD.add(txtPctgTD, BorderLayout.LINE_START);
											txtPctgTD.setHorizontalAlignment(JTextField.TRAILING);
											txtPctgTD.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtPctgTD.setEnabled(true);
											txtPctgTD.setEditable(false);
											txtPctgTD.setPreferredSize(new Dimension(40, 0));
											txtPctgTD.setFont(fontPlainSanSerEleven);
											txtPctgTD.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtTD = new _JXFormattedTextField("0.00");
											panTD.add(txtTD, BorderLayout.CENTER);
											txtTD.setHorizontalAlignment(JTextField.TRAILING);
											txtTD.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtTD.setEnabled(true);
											txtTD.setEditable(false);	
											txtTD.setFont(fontPlainSanSerEleven);
										}
									}
								}
//								{
//									JLabel lblAdvMA = new JLabel("");
//									panRetentions.add(lblAdvMA);
//									lblAdvMA.setFont(fontPlainSanSerNine);
//								}
								// MODIFIED BY MONIQUE DTD 10-23-2023; REFER TO DCRF#2766
								{
									lblRatSales = new JLabel("");
									panRetentions.add(lblRatSales);
									lblRatSales.setFont(new Font("SansSerif", Font.PLAIN, 8));
								}
								{
									JXPanel panAMA = new JXPanel(new BorderLayout(3, 3)); 
									panRetentions.add(panAMA, BorderLayout.CENTER); 
									{	

									}
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
									txtRetTotal.setFont(fontPlainSanSerEleven);
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
							JLabel lblReturnedRetentions = new JLabel("   Returned Undertaking Deduction"); 
							panSep3.add(lblReturnedRetentions, BorderLayout.PAGE_START);
							lblReturnedRetentions.setFont(fontBoldSanSerTens); 
							lblReturnedRetentions.setPreferredSize(new Dimension(0, 20));
						}
						{
							JXPanel panRetRet = new JXPanel(new GridLayout(10, 2, 5, 3)); 
							panSep3.add(panRetRet, BorderLayout.CENTER);
							{
								// MODIFIED BY MONIQUE DTD 10-24-2023; AFFECTED WITH DCRF##2766
								/*{
									lblEPEB = new JLabel("EPEB");
									panRetRet.add(lblEPEB);
									lblEPEB.setFont(fontPlainSanSerNine);
								}*/
								
								{
									lblEPEBret = new JLabel("EPEB");
									panRetRet.add(lblEPEBret);
									lblEPEBret.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panRetEPEB = new JXPanel(new BorderLayout(3, 3));
									panRetRet.add(panRetEPEB, BorderLayout.CENTER);
									{
										{
											txtPctgRetEPEB = new _JXFormattedTextField("0.0%");
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
											txtPctgRetETD = new _JXFormattedTextField("0.0%");
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
											txtPctgRetCOC = new _JXFormattedTextField("0.0%");
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
									lblCOM5 = new JLabel("Comm. Fac.");
									panRetRet.add(lblCOM5, BorderLayout.LINE_START);
									lblCOM5.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panRetCOM5 = new JXPanel(new BorderLayout(3, 3)); 
									panRetRet.add(panRetCOM5, BorderLayout.CENTER); 
									{
										{
											txtPctgRetCOM5 = new _JXFormattedTextField("0.0%");
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
											txtPctgRetROW = new _JXFormattedTextField("0.0%");
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
									lblWS = new JLabel("Water Supply");
									panRetRet.add(lblWS);
									lblWS.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panWS = new JXPanel(new BorderLayout(3, 3));
									panRetRet.add(panWS, BorderLayout.CENTER); 
									{
										{
											txtPctgRetWS = new _JXFormattedTextField("0.0%");
											panWS.add(txtPctgRetWS, BorderLayout.LINE_START); 
											txtPctgRetWS.setHorizontalAlignment(JTextField.CENTER);
											txtPctgRetWS.setEnabled(true);
											txtPctgRetWS.setEditable(false); 
											txtPctgRetWS.setPreferredSize(new Dimension(40, 0));
											txtPctgRetWS.setFont(fontPlainSanSerEleven);
											txtPctgRetWS.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtRetWS = new _JXFormattedTextField("0.00");
											panWS.add(txtRetWS, BorderLayout.CENTER);
											txtRetWS.setHorizontalAlignment(JTextField.TRAILING);
											txtRetWS.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRetWS.setEnabled(true);
											txtRetWS.setEditable(false);
											txtRetWS.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									lblTD = new JLabel("Tax Declaration");
									panRetRet.add(lblTD);
									lblTD.setFont(fontPlainSanSerNine);
								}
								{
									JXPanel panTD = new JXPanel(new BorderLayout(3, 3));
									panRetRet.add(panTD, BorderLayout.CENTER); 
									{
										{
											txtPctgRetTD = new _JXFormattedTextField("0.0%");
											panTD.add(txtPctgRetTD, BorderLayout.LINE_START); 
											txtPctgRetTD.setHorizontalAlignment(JTextField.CENTER);
											txtPctgRetTD.setEnabled(true);
											txtPctgRetTD.setEditable(false); 
											txtPctgRetTD.setPreferredSize(new Dimension(40, 0));
											txtPctgRetTD.setFont(fontPlainSanSerEleven);
											txtPctgRetTD.setHorizontalAlignment(JTextField.CENTER);
										}
										{
											txtRetTD = new _JXFormattedTextField("0.00");
											panTD.add(txtRetTD, BorderLayout.CENTER);
											txtRetTD.setHorizontalAlignment(JTextField.TRAILING);
											txtRetTD.setFormatterFactory(_JXFormattedTextField.DECIMAL);
											txtRetTD.setEnabled(true);
											txtRetTD.setEditable(false);
											txtRetTD.setFont(fontPlainSanSerEleven);
										}
									}
								}
								{
									JLabel lblRetAMA = new JLabel("");
									panRetRet.add(lblRetAMA);
									lblRetAMA.setFont(fontPlainSanSerNine);
								}						
								{
									JXPanel panRetAMA = new JXPanel(new BorderLayout(3, 3)); 
									panRetRet.add(panRetAMA, BorderLayout.CENTER); 
									{

									}
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
									txtRetRetTotal.setFont(fontPlainSanSerEleven);
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
				panSummary.setBorder(JTBorderFactory.createTitleBorder("Summary", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JXPanel panSummAll = new JXPanel(new GridLayout(12, 1, 5, 3)); 
						panSummary.add(panSummAll, BorderLayout.CENTER);
						{

							{
								JXPanel panLine0 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine0, BorderLayout.CENTER); 
								{
									{
										JLabel label = new JLabel("Loanable Amount (Gross of VAT)");
										panLine0.add(label);
										label.setFont(fontBoldSanSerEleven);
										label.setForeground(Color.BLACK);
									}
									{
										lblLoanableAmount = new JLabel("0.00"); 
										panLine0.add(lblLoanableAmount);
										lblLoanableAmount.setHorizontalAlignment(JLabel.TRAILING); 
										lblLoanableAmount.setFont(fontBoldSanSerEleven);
										lblLoanableAmount.setForeground(Color.DARK_GRAY);
									}
								}
							}
							{
								JXPanel panLine1 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine1, BorderLayout.CENTER); 
								{
									{
										JLabel label = new JLabel("VAT on O/S");
										panLine1.add(label);
										label.setFont(fontBoldSanSerEleven);
										label.setForeground(Color.BLACK);
									}
									{
										lblVAT = new JLabel("0.00"); 
										panLine1.add(lblVAT);
										lblVAT.setHorizontalAlignment(JLabel.TRAILING); 
										lblVAT.setFont(fontBoldSanSerEleven);
										lblVAT.setForeground(Color.DARK_GRAY);
									}
								}
							}
							{
								JXPanel panLine2 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine2, BorderLayout.CENTER); 
								{
									{
										JLabel label = new JLabel("Loanable Amount (Net of VAT)");
										panLine2.add(label);
										label.setFont(fontBoldSanSerEleven);
										label.setForeground(Color.BLACK);
									}
									{
										lblLoanableAmountNetVAT = new JLabel("0.00"); 
										panLine2.add(lblLoanableAmountNetVAT);
										lblLoanableAmountNetVAT.setHorizontalAlignment(JLabel.TRAILING); 
										lblLoanableAmountNetVAT.setFont(fontBoldSanSerEleven);
										lblLoanableAmountNetVAT.setForeground(Color.DARK_GRAY);
									}
								}
							}
							{
								JXPanel panLine21 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine21, BorderLayout.CENTER); 
								{

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
										lblSummRecapitulation.setFont(fontPlainSanSerTens);
										lblSummRecapitulation.setForeground(Color.BLACK);
									}
									{
										lblSummRecapitulationAmount = new JLabel("0.00"); 
										panLine3.add(lblSummRecapitulationAmount);
										lblSummRecapitulationAmount.setHorizontalAlignment(JLabel.TRAILING); 
										lblSummRecapitulationAmount.setFont(fontBoldSanSerEleven);
										lblSummRecapitulationAmount.setForeground(Color.RED);
									}
								}
							}
							{
								JXPanel panLine4 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine4, BorderLayout.CENTER); 
								{
									{
										lblSummRetention = new JLabel("Undertaking Deduction"); 
										panLine4.add(lblSummRetention);
										lblSummRetention.setHorizontalAlignment(JLabel.LEADING);
										lblSummRetention.setFont(fontPlainSanSerTens);
										lblSummRetention.setForeground(Color.BLACK);
									}
									{
										lblSummRetentionAmount = new JLabel("0.00"); 
										panLine4.add(lblSummRetentionAmount);
										lblSummRetentionAmount.setHorizontalAlignment(JLabel.TRAILING); 
										lblSummRetentionAmount.setFont(fontBoldSanSerEleven);
										lblSummRetentionAmount.setForeground(Color.RED);
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
										lblNetProceeds.setFont(fontPlainSanSerTens);
										lblNetProceeds.setForeground(Color.BLACK);
									}
									{
										lblNetProceedsAmount = new JLabel("0.00"); 
										panLine5.add(lblNetProceedsAmount); 
										lblNetProceedsAmount.setHorizontalAlignment(JLabel.TRAILING); 
										lblNetProceedsAmount.setFont(fontBoldSanSerEleven);
										lblNetProceedsAmount.setForeground(Color.DARK_GRAY);
									}
								}
							}
							{
								JXPanel panLine6 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine6, BorderLayout.CENTER); 
								{
									{
										lblSummReturnedRetention = new JLabel("Retd. Undertaking Deduction"); 
										panLine6.add(lblSummReturnedRetention);
										lblSummReturnedRetention.setHorizontalAlignment(JLabel.LEADING);
										lblSummReturnedRetention.setFont(fontPlainSanSerTens);
										lblSummReturnedRetention.setForeground(Color.BLACK);
									}
									{
										lblSummReturnedRetentionAmount = new JLabel("0.00"); 
										panLine6.add(lblSummReturnedRetentionAmount); 
										lblSummReturnedRetentionAmount.setHorizontalAlignment(JLabel.TRAILING); 
										lblSummReturnedRetentionAmount.setFont(fontBoldSanSerEleven);
										lblSummReturnedRetentionAmount.setForeground(new Color(0, 153, 0));
									}
								}
							}
							{
								JXPanel panLine7 = new JXPanel(new GridLayout(1, 2, 5, 5));
								panSummAll.add(panLine7, BorderLayout.CENTER); 
								{
									{
										JLabel label = new JLabel("Retd. Advanced MA"); 
										panLine7.add(label);
										label.setHorizontalAlignment(JLabel.LEADING);
										label.setFont(fontPlainSanSerTens);
										label.setForeground(Color.BLACK);
									}
									{
										lblSummReturnedAMA = new JLabel("0.00"); 
										panLine7.add(lblSummReturnedAMA); 
										lblSummReturnedAMA.setHorizontalAlignment(JLabel.TRAILING); 
										lblSummReturnedAMA.setFont(fontBoldSanSerEleven);
										lblSummReturnedAMA.setForeground(new Color(0, 153, 0));
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
										lblTotalNetProceeds.setFont(fontBoldSanSerEleven);
										lblTotalNetProceeds.setForeground(Color.BLACK);
									}
									{
										lblTotalNetProceedsAmount = new JLabel("0.00"); 
										panLine8.add(lblTotalNetProceedsAmount); 
										lblTotalNetProceedsAmount.setHorizontalAlignment(JLabel.TRAILING); 
										lblTotalNetProceedsAmount.setFont(fontBoldSanSerEleven);
										lblTotalNetProceedsAmount.setForeground(Color.DARK_GRAY);
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
					
					tblPostInsp.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));
					tblPostInsp.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));
				}
				{
					rowPostInsp = tblPostInsp.getRowHeader();
					rowPostInsp.setModel(new DefaultListModel());
					scrollPostInsp.setRowHeaderView(rowPostInsp);
					scrollPostInsp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
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
					tabHDMF_hdmfDetails.add("                       HDMF Schedule                                     ", panHDMFDetails_schedule);
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
								
								tblHDMFSchedule.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));
							}
							{
								rowHDMFSchedule = tblHDMFSchedule.getRowHeader();
								rowHDMFSchedule.setModel(new DefaultListModel());
								scrollHDMFSchedule.setRowHeaderView(rowHDMFSchedule);
								scrollHDMFSchedule.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}	
						}
					}
				}
				{
					panHDMFDetails_payments = new JXPanel(new BorderLayout(5, 5));
					tabHDMF_hdmfDetails.add("                        HDMF Payments                                    ", panHDMFDetails_payments);
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
								
								tblHDMFPayments.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));
								tblHDMFPayments.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
								tblHDMFPayments.getColumnModel().getColumn(4).setCellRenderer(new DateRenderer(sdf));
							}
							{
								rowHDMFPayments = tblHDMFPayments.getRowHeader();
								rowHDMFPayments.setModel(new DefaultListModel());
								scrollHDMFPayments.setRowHeaderView(rowHDMFPayments);
								scrollHDMFPayments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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
		db.select("select * from view_hdmf_info_loanfilingstatus('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+seq_no+"')");
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelLoanFilingStatus.addRow(db.getResult()[x]);
				listModel.addElement(modelLoanFilingStatus.getRowCount());
			}
			blnRev = true;
		} else{
			blnRev = false;
		};
		
		return blnRev;
	};
	
	public static Boolean displayEPEBG2G(String entity_id, String proj_id, String pbl_id, String seq_no) {
		Boolean blnRev = false;
		FncTables.clearTable(modelEPEB);
		hdmfInfo_maintab.entity_id = entity_id;
		hdmfInfo_maintab.proj_id = proj_id;
		hdmfInfo_maintab.pbl_id = pbl_id;
		hdmfInfo_maintab.seq_no = seq_no;
	
		pgSelect db = new pgSelect();
		db.select("select * from view_card_epeb_g2g_tcost_v2('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+seq_no+"')");
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelEPEB.addRow(db.getResult()[x]);
			}
			blnRev = true;
			
			totalG2GReceipt(modelEPEB, modelG2GReceiptTotal);
		} else{
			blnRev = false;
			modelG2GReceiptTotal.setValueAt(FncBigDecimal.zeroValue(), 0, 2);
		};
		
		tblEPEBG2G.packAll();
				
		return blnRev;
	}
	
	public static Boolean displayHDMFUnitInspection(String entity_id, String proj_id, String pbl_id, String seq_no) {
		Boolean blnRev = false;
		FncTables.clearTable(modelPostInsp);
		DefaultListModel listModel = new DefaultListModel();
		rowPostInsp.setModel(listModel); 
	
		pgSelect db = new pgSelect();
		db.select("select a.date_created as trans_date, d.status_desc as status, \n" + 
				"(case when d.status_id = '01' then null else a.trans_date end) as date_inspected, \n" +
				"a.hdmf_rep, a.remarks \n" +
				"from rf_hdmf_insp_detail a \n" +
				"inner join rf_entity b on a.entity_id = b.entity_id \n" +
				"inner join mf_unit_info c on a.pbl_id = c.pbl_id and c.proj_id = '"+proj_id+"' \n" +
				"inner join mf_house_inspection_status d on a.insp_status_id = d.status_id \n" +
				"where a.entity_id = '"+entity_id+"' and a.pbl_id = '"+pbl_id+"' and a.status_id = 'A' \n" +
				"order by insp_detail_rec_id desc, a.trans_date desc");
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelPostInsp.addRow(db.getResult()[x]);
				listModel.addElement(modelPostInsp.getRowCount());
			}
			blnRev = true;
		} else{
			blnRev = false;
		};
		
		return blnRev;
	};
	
	public static Boolean displayHDMFPayments(String entity_id, String proj_id, String pbl_id, String seq_no) {
		Boolean blnRev = false;
		FncTables.clearTable(modelHDMFPayments);
		DefaultListModel listModel = new DefaultListModel();
		rowHDMFPayments.setModel(listModel); 
	
		pgSelect db = new pgSelect();
		db.select("select * from view_hdmf_payments('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+seq_no+"'); ");

		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelHDMFPayments.addRow(db.getResult()[x]);
				listModel.addElement(modelHDMFPayments.getRowCount());
			}
			blnRev = true;
		} else{
			blnRev = false;
		};
		
		return blnRev;
	};
	
	public static Boolean displayHDMFSchedule(String entity_id, String proj_id, String pbl_id, String seq_no) {
		Boolean blnRev = false;
		FncTables.clearTable(modelHDMFSchedule);
		DefaultListModel listModel = new DefaultListModel();
		rowHDMFSchedule.setModel(listModel); 
	
		pgSelect db = new pgSelect();
		db.select("select * from view_hdmf_schedule('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+seq_no+"'); "); 

		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelHDMFSchedule.addRow(db.getResult()[x]);
				listModel.addElement(modelHDMFSchedule.getRowCount());
			}
			blnRev = true;
		} else{
			blnRev = false;
		};
		
		return blnRev;
	};
	
	public static Boolean displayHDMFLoanReleasedDetail(String entity_id, String proj_id, String pbl_id, String seq_no) {
		pgSelect db = new pgSelect();
		db.select("select * from view_hdmf_releasedloan_detail('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+seq_no+"'); "); 
		System.out.println("\n"+"select * from view_hdmf_releasedloan_detail('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+seq_no+"'); ");
		
		BigDecimal bdLoanAmt = new BigDecimal("0.00");
		try {			
			lblLoanableAmount.setText(String.format("%,.2f", new BigDecimal((String) db.getResult()[0][29].toString()).setScale(2, RoundingMode.DOWN)));
			lblVAT.setText(String.format("%,.2f", new BigDecimal((String) db.getResult()[0][30].toString()).setScale(2, RoundingMode.DOWN)));
			lblLoanableAmountNetVAT.setText(String.format("%,.2f", new BigDecimal((String) db.getResult()[0][31].toString()).setScale(2, RoundingMode.DOWN)));

			
			 bdLoanAmt = 
			
					(
							(
									new BigDecimal((String) db.getResult()[0][31].toString()).setScale(2, RoundingMode.DOWN).compareTo(new BigDecimal("0.00"))==1
							)
							?
							new BigDecimal((String) db.getResult()[0][31].toString()).setScale(2, RoundingMode.DOWN)
							:
							new BigDecimal((String) db.getResult()[0][29].toString()).setScale(2, RoundingMode.DOWN)
					);	
									
		} catch (NumberFormatException numex) {
			bdLoanAmt = new BigDecimal("0.00");
			lblLoanableAmount.setText("0.00");
			lblVAT.setText("0.00");
			lblLoanableAmountNetVAT.setText("0.00");
		} catch (NullPointerException nullex) {
			lblLoanableAmount.setText("0.00");
			lblVAT.setText("0.00");
			lblLoanableAmountNetVAT.setText("0.00");
		}
		
		BigDecimal bdSumRecap = new BigDecimal("0.00");
		BigDecimal bdSumRet = new BigDecimal("0.00");
		BigDecimal bdNetProceeds = new BigDecimal("0.00");
		BigDecimal bdSumRetRet = new BigDecimal("0.00");
		BigDecimal bdSumRetAMA = new BigDecimal("0.00");
		BigDecimal bdTotalNetProceeds = new BigDecimal("0.00");
		
		
		
		if (db.isNotNull()) {
			BigDecimal total_recapitulation = new BigDecimal("0.00");
			BigDecimal total_retention = new BigDecimal("0.00");
			
			//ADDED BY MONIQUE DTD 2023-08-16; REFER TO DCRF#2656	
			BigDecimal bdLoanAmtRET = new BigDecimal((String) db.getResult()[0][29].toString()).setScale(2, RoundingMode.DOWN);							
			
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			/*		Recapitulation			 */
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			txtSriDocStamp.setValue(new BigDecimal((String) db.getResult()[0][0].toString())); 
			txtFire.setValue(new BigDecimal((String) db.getResult()[0][1].toString()));
			txtProcessingFee.setValue(new BigDecimal((String) db.getResult()[0][2].toString()));
			txtAppraisalFee.setValue(new BigDecimal((String) db.getResult()[0][9].toString()));
			txtInterimMRI.setValue(new BigDecimal((String) db.getResult()[0][3].toString()));
			txtRetentionFee.setValue(new BigDecimal((String) db.getResult()[0][4].toString()));
			
			//MODIFIED BY MONIQUE DTD 2023-08-16; REFER TO DCRF#2656
//			txtPctgRetentionFee.setText(GetPercentage(bdLoanAmt.toString(), txtRetentionFee.getValue().toString())); 			
			txtPctgRetentionFee.setText(GetPercentage(bdLoanAmtRET.toString(), txtRetentionFee.getValue().toString()));
			
			txtFirstMA.setValue(new BigDecimal((String) db.getResult()[0][5].toString()));
			txtAMA.setValue(new BigDecimal((String) db.getResult()[0][27].toString()));
			txtServiceFee.setValue(new BigDecimal(db.getResult()[0][32].toString()));
			txtPctgAMA.setText(GetPercentage(bdLoanAmt.toString(), txtAMA.getValue().toString()));

			total_recapitulation = total_recapitulation.add(new BigDecimal(txtSriDocStamp.getValue().toString())); 
			total_recapitulation = total_recapitulation.add(new BigDecimal(txtFire.getValue().toString())); 
			total_recapitulation = total_recapitulation.add(new BigDecimal(txtProcessingFee.getValue().toString()));
			total_recapitulation = total_recapitulation.add(new BigDecimal(txtAppraisalFee.getValue().toString())); 
			total_recapitulation = total_recapitulation.add(new BigDecimal(txtInterimMRI.getValue().toString()));
			total_recapitulation = total_recapitulation.add(new BigDecimal(txtRetentionFee.getValue().toString()));
			total_recapitulation = total_recapitulation.add(new BigDecimal(txtFirstMA.getValue().toString())); 
			total_recapitulation = total_recapitulation.add(new BigDecimal(txtAMA.getValue().toString()));
			total_recapitulation = total_recapitulation.add(new BigDecimal(txtServiceFee.getValue().toString()));

			txtRecapTotal.setValue(total_recapitulation);
			
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			/*			Retention		 	 */
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			txtEPEB.setValue(new BigDecimal((String) db.getResult()[0][10].toString())); 
			txtETD.setValue(new BigDecimal((String) db.getResult()[0][11].toString())); 
			txtCOC.setValue(new BigDecimal((String) db.getResult()[0][12].toString())); 
			txtWS.setValue(new BigDecimal((String) db.getResult()[0][13].toString())); 
			txtCOM5.setValue((new BigDecimal((String) db.getResult()[0][14].toString())));
			txtROW.setValue((new BigDecimal((String) db.getResult()[0][16].toString())));
			txtWS.setValue((new BigDecimal((String) db.getResult()[0][23].toString())));
			txtTD.setValue((new BigDecimal((String) db.getResult()[0][24].toString())));
			
			txtPctgEPEB.setText(GetPercentage(bdLoanAmt.toString(), txtEPEB.getValue().toString()));
			txtPctgETD.setText(GetPercentage(bdLoanAmt.toString(), txtETD.getValue().toString()));
			txtPctgCOC.setText(GetPercentage(bdLoanAmt.toString(), txtCOC.getValue().toString()));
			txtPctgWS.setText(GetPercentage(bdLoanAmt.toString(), txtWS.getValue().toString()));
			txtPctgCOM5.setText(GetPercentage(bdLoanAmt.toString(), txtCOM5.getValue().toString()));
			txtPctgROW.setText(GetPercentage(bdLoanAmt.toString(), txtROW.getValue().toString()));
			
			txtPctgWS.setText(GetPercentage(bdLoanAmt.toString(), txtWS.getValue().toString()));
			txtPctgTD.setText(GetPercentage(bdLoanAmt.toString(), txtTD.getValue().toString()));
			
			total_retention = total_retention.add(new BigDecimal(txtEPEB.getValue().toString()));
			total_retention = total_retention.add(new BigDecimal(txtETD.getValue().toString())); 
			total_retention = total_retention.add(new BigDecimal(txtCOC.getValue().toString())); 
			total_retention = total_retention.add(new BigDecimal(txtCOM5.getValue().toString()));
			total_retention = total_retention.add(new BigDecimal(txtROW.getValue().toString()));
			total_retention = total_retention.add(new BigDecimal(txtWS.getValue().toString()));
			total_retention = total_retention.add(new BigDecimal(txtTD.getValue().toString()));
			
			txtRetTotal.setValue(total_retention);
			
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			/*		Returned Retention		 */
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			txtRetEPEB.setValue(new BigDecimal((String) db.getResult()[0][17].toString())); 
			txtRetETD.setValue(new BigDecimal((String) db.getResult()[0][18].toString())); 
			txtRetCOC.setValue(new BigDecimal((String) db.getResult()[0][19].toString())); 
			txtRetWS.setValue(new BigDecimal((String) db.getResult()[0][20].toString())); 
			txtRetCOM5.setValue(new BigDecimal((String) db.getResult()[0][21].toString()));
			txtRetROW.setValue(new BigDecimal((String) db.getResult()[0][22].toString()));
			
			txtRetWS.setValue(new BigDecimal((String) db.getResult()[0][25].toString()));
			txtRetTD.setValue(new BigDecimal((String) db.getResult()[0][26].toString()));
			//txtRetAMA.setValue(new BigDecimal((String) db.getResult()[0][28].toString()));
			
			txtPctgRetEPEB.setText(GetPercentage(bdLoanAmt.toString(), txtRetEPEB.getValue().toString()));
			txtPctgRetETD.setText(GetPercentage(bdLoanAmt.toString(), txtRetETD.getValue().toString()));
			txtPctgRetCOC.setText(GetPercentage(bdLoanAmt.toString(), txtRetCOC.getValue().toString()));
			txtPctgRetCOM5.setText(GetPercentage(bdLoanAmt.toString(), txtRetCOM5.getValue().toString()));
			txtPctgRetROW.setText(GetPercentage(bdLoanAmt.toString(), txtRetROW.getValue().toString()));
			
			txtPctgRetWS.setText(GetPercentage(bdLoanAmt.toString(), txtRetWS.getValue().toString()));
			txtPctgRetTD.setText(GetPercentage(bdLoanAmt.toString(), txtRetTD.getValue().toString()));
			//txtPctgRetAMA.setText(GetPercentage(bdLoanAmt.toString(), txtRetAMA.getValue().toString()));
			
			total_retention = new BigDecimal("0.00"); 
			total_retention = total_retention.add(new BigDecimal(txtRetEPEB.getValue().toString()));
			total_retention = total_retention.add(new BigDecimal(txtRetETD.getValue().toString())); 
			total_retention = total_retention.add(new BigDecimal(txtRetCOC.getValue().toString()));  
			total_retention = total_retention.add(new BigDecimal(txtRetCOM5.getValue().toString()));
			total_retention = total_retention.add(new BigDecimal(txtRetROW.getValue().toString()));
			total_retention = total_retention.add(new BigDecimal(txtRetWS.getValue().toString()));
			total_retention = total_retention.add(new BigDecimal(txtRetTD.getValue().toString()));
			
			txtRetRetTotal.setValue(total_retention);
			
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			/*			Summary				 */
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			try {
				bdSumRecap = new BigDecimal(txtRecapTotal.getValue().toString());
				lblSummRecapitulationAmount.setText("(" + String.format("%,.2f", bdSumRecap.setScale(2, RoundingMode.DOWN)) + ")");
				lblSummRecapitulationAmount.setForeground(Color.RED);
			} catch (Exception ex) {
				bdSumRecap = new BigDecimal("0.00");
				lblSummRecapitulationAmount.setText("0.00");
				lblSummRecapitulationAmount.setForeground(Color.DARK_GRAY);
			}

			try {
				bdSumRet = new BigDecimal(txtRetTotal.getValue().toString());
				lblSummRetentionAmount.setText("(" + String.format("%,.2f", bdSumRet.setScale(2, RoundingMode.DOWN)) + ")");
				lblSummRetentionAmount.setForeground(Color.RED);
			} catch (Exception ex) {
				bdSumRet = new BigDecimal("0.00");
				lblSummRetentionAmount.setText("0.00");
				lblSummRetentionAmount.setForeground(Color.DARK_GRAY);
			}

			bdLoanAmt = bdLoanAmt.subtract(bdSumRecap);
			bdNetProceeds = bdLoanAmt.subtract(bdSumRet); 
			lblNetProceedsAmount.setText(String.format("%,.2f", bdNetProceeds.setScale(2, RoundingMode.DOWN)));

			try {
				bdSumRetRet = new BigDecimal(txtRetRetTotal.getValue().toString());
				lblSummReturnedRetentionAmount.setText(String.format("%,.2f", bdSumRetRet.setScale(2, RoundingMode.DOWN)));
				lblSummReturnedRetentionAmount.setForeground(new Color(0, 153, 0));
			} catch (Exception ex) {
				bdSumRetRet = new BigDecimal("0.00");
				lblSummReturnedRetentionAmount.setText("0.00");
				lblSummReturnedRetentionAmount.setForeground(Color.DARK_GRAY);
			}

			try {
				bdSumRetAMA = new BigDecimal((String) db.getResult()[0][28].toString());
				lblSummReturnedAMA.setText(String.format("%,.2f", bdSumRetAMA.setScale(2, RoundingMode.DOWN)));
				lblSummReturnedAMA.setForeground(new Color(0, 153, 0));
			} catch (Exception ex) {
				bdSumRetAMA = new BigDecimal("0.00");
				lblSummReturnedAMA.setText("0.00");
				lblSummReturnedAMA.setForeground(Color.DARK_GRAY);
			}
			
			bdTotalNetProceeds = bdNetProceeds.add(bdSumRetRet).add(bdSumRetAMA); 
			lblTotalNetProceedsAmount.setText(String.format("%,.2f", bdTotalNetProceeds.setScale(2, RoundingMode.DOWN)));
		} else {
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			/*		Recapitulation			 */
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			txtSriDocStamp.setValue(new BigDecimal("0.00")); 
			txtFire.setValue(new BigDecimal("0.00"));
			txtProcessingFee.setValue(new BigDecimal("0.00"));
			txtAppraisalFee.setValue(new BigDecimal("0.00"));
			txtInterimMRI.setValue(new BigDecimal("0.00"));
			txtRetentionFee.setValue(new BigDecimal("0.00"));
			txtPctgRetentionFee.setText("0.0%");
			txtFirstMA.setValue(new BigDecimal("0.00"));
			txtAMA.setValue(new BigDecimal("0.00"));
			txtPctgAMA.setText("0.0%");
			
			txtRecapTotal.setValue(new BigDecimal("0.00"));
			
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			/*			Retention		 	 */
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			txtEPEB.setValue(new BigDecimal("0.00")); 
			txtETD.setValue(new BigDecimal("0.00")); 
			txtCOC.setValue(new BigDecimal("0.00")); 
			txtWS.setValue(new BigDecimal("0.00")); 
			txtCOM5.setValue(new BigDecimal("0.00"));
			txtROW.setValue(new BigDecimal("0.00"));
			
			txtWS.setValue(new BigDecimal("0.00"));
			txtTD.setValue(new BigDecimal("0.00"));
			
			txtPctgEPEB.setText("0.0%");
			txtPctgETD.setText("0.0%");
			txtPctgCOC.setText("0.0%");
			txtPctgWS.setText("0.0%");
			txtPctgCOM5.setText("0.0%");
			txtPctgROW.setText("0.0%");
			
			txtPctgWS.setText("0.0%");
			txtPctgTD.setText("0.0%");

			txtRetTotal.setValue(new BigDecimal("0.00"));
			
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			/*		Returned Retention		 */
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			txtRetEPEB.setValue(new BigDecimal("0.00")); 
			txtRetETD.setValue(new BigDecimal("0.00")); 
			txtRetCOC.setValue(new BigDecimal("0.00")); 
			txtRetWS.setValue(new BigDecimal("0.00")); 
			txtRetCOM5.setValue(new BigDecimal("0.00"));
			txtRetROW.setValue(new BigDecimal("0.00"));
			
			txtRetWS.setValue(new BigDecimal("0.00"));
			txtRetTD.setValue(new BigDecimal("0.00"));
			
			txtPctgRetEPEB.setText("0.0%");
			txtPctgRetETD.setText("0.0%");
			txtPctgRetCOC.setText("0.0%");
			txtPctgRetWS.setText("0.0%");
			txtPctgRetCOM5.setText("0.0%");
			txtPctgRetROW.setText("0.0%");
			
			txtPctgRetWS.setText("0.0%");
			txtPctgRetTD.setText("0.0%");
			
			txtRetRetTotal.setValue(new BigDecimal("0.00"));

			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			/*			Summary				 */
			/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
			lblSummRecapitulationAmount.setText("0.00");
			lblSummRecapitulationAmount.setForeground(Color.DARK_GRAY);
			lblSummRetentionAmount.setText("0.00");
			lblSummRetentionAmount.setForeground(Color.DARK_GRAY);
			
			lblNetProceedsAmount.setText(String.format("%,.2f", bdLoanAmt.setScale(2, RoundingMode.DOWN)));
			
			lblSummReturnedRetentionAmount.setText("0.00");
			lblSummReturnedRetentionAmount.setForeground(Color.DARK_GRAY);
			lblSummReturnedAMA.setText("0.00");
			lblSummReturnedAMA.setForeground(Color.DARK_GRAY);
			
			lblTotalNetProceedsAmount.setText(String.format("%,.2f", bdLoanAmt.setScale(2, RoundingMode.DOWN)));
		};
		
		return true;
	};
	
	private static String GetPercentage(String strAmt1, String strAmt2) {
		String strPctg = ""; 

		if (!strAmt1.equals("0.00")) {
			strAmt1 = strAmt1.replace(",", "");
			strAmt2 = strAmt2.replace(",", ""); 
			
			try {
				strPctg = FncGlobal.GetString("select (case when ((1 - (('"+strAmt1+"'::numeric(19, 2) - '"+strAmt2+"'::numeric(19, 2)) / '"+strAmt1+"'::numeric(19, 2))) * 100) >= 10 \n" + 
						"then ((1 - (('"+strAmt1+"'::numeric(19, 2) - '"+strAmt2+"'::numeric(19, 2)) / '"+strAmt1+"'::numeric(19, 2))) * 100)::numeric(19, 1)::varchar || '%'::Varchar \n" + 
						"else ((1 - (('"+strAmt1+"'::numeric(19, 2) - '"+strAmt2+"'::numeric(19, 2)) / '"+strAmt1+"'::numeric(19, 2))) * 100)::numeric(19, 1) || '%'::Varchar end)");	
			} catch (Exception e) {
				return "0.00%";
			}
		} else {
			return "0.00%";
		}

		return strPctg; 
	}
	
	// ADDED BY MONIQUE DTD 10-23-2023; REFER TO DCRF#2766
	public static void Note(String phase) { 
		
		//System.out.println("Value of txtEPEB: %s", txtEPEB.getValued());
		System.out.printf("Check if txtEPEB is not equal to 0: %s", txtEPEB.getValued() != new BigDecimal("0"));
		
		BigDecimal epeb = (BigDecimal) txtEPEB.getValued();
		
		if (phase == "ER1-B" && epeb.compareTo(new BigDecimal("0")) == 1) {
			lblRatSales.setText("*Ratification of Sales");
			lblEPEB.setText("*EPEB");
			
//			System.out.printf("txtEPEB: %s", txtEPEB.getText().toString());
		} else {
			lblRatSales.setText("");
	
		}
	}
	
	private Object[] getLots(String entity, String proj_id, String pbl_id, String seq_no) {//ARRAYLIST FOR THE CIVIL STATUS
		ArrayList<Object> lots = new ArrayList<Object>();

		pgSelect db = new pgSelect();
		String SQL = "SELECT FORMAT('LOT %s', b.lot)\n" + 
				"FROM rf_sold_unit a\n" + 
				"LEFT JOIN mf_unit_info b on b.proj_id = a.projcode and b.pbl_id = a.pbl_id \n" + 
				"WHERE a.entity_id = '"+entity+"'\n" + 
				"AND a.projcode = '"+proj_id+"'\n" + 
				"AND a.pbl_id = '"+pbl_id+"'\n" + 
				"AND a.seq_no = "+seq_no+"\n" + 
				"UNION \n" + 
				"SELECT FORMAT('LOT %s', b.lot)\n" + 
				"FROM hs_sold_other_lots a\n" + 
				"LEFT JOIN mf_unit_info b on b.proj_id = a.proj_id and b.pbl_id = a.oth_pbl_id \n" + 
				"WHERE a.entity_id = '"+entity+"'\n" + 
				"AND a.proj_id = '"+proj_id+"'\n" + 
				"AND a.pbl_id = '"+pbl_id+"'\n" + 
				"AND a.seq_no = "+seq_no+" ";
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				lots.add(db.getResult()[x][0]);
			}
		}
		return lots.toArray();
	}
	

}
