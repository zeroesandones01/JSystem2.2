package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXPanel;

import com.linuxense.javadbf.DBFException;
import com.toedter.calendar.JTextFieldDateEditor;

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncExport;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelNOATagging2;
import tablemodel.modelPST_DOA_Signed;
import tablemodel.modelPST_ForPostApproval;
import tablemodel.modelPST_ForTCTAnnotation;
import tablemodel.modelPST_LoanFiled;
import tablemodel.modelPST_With_Annotated_TCT;
import tablemodel.modelPST_With_EPEB;
import tablemodel.model_hdmf_NOASigning;
import tablemodel.model_hdmf_ci_tagging;
import tablemodel.model_hdmf_loanReturned;
import tablemodel.model_hdmf_msvs_reverified;
import tablemodel.model_hdmf_noaextension;
import tablemodel.model_hdmf_postInspection;


public class hdmfMon_statusTagging extends JXPanel implements _GUI, MouseListener {

	private static final long serialVersionUID = -5963216764262833394L;
	private PagibigStatusMonitoring_v2 hdmfMainMod;
	static Dimension size = new Dimension(738, 351);
	
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JLabel lblStatusDate;
	private _JDateChooser dteTransDate;
	private JLabel lblStage;
	private JComboBox cmbStage;
	private JPanel pnlNorthEast;
	
	private JPanel pnlCenter;
	private JScrollPane scrollStatusTagging;
	
	private modelPST_LoanFiled modelLoanFiled;
	private modelNOATagging2 modelNOAReleased;
	private modelPST_DOA_Signed modelDOASigned;
	private modelPST_ForTCTAnnotation modelForTCTAnnotation;
	//private modelPST_NOA_Expiring modelNOAExpiring;
	private model_hdmf_noaextension modelNOAExtension;
	private modelPST_With_EPEB modelWithEPEB;
	private modelPST_With_Annotated_TCT modelWithAnnotatedTCT;
	private modelPST_ForPostApproval modelForPostApproval;
	private model_hdmf_msvs_reverified modelMSVS;
	private model_hdmf_postInspection modelHouseInspection;
	private model_hdmf_NOASigning modelNOASigning;
	private model_hdmf_loanReturned modelLoanReturned; 
	private model_hdmf_ci_tagging modelCITagging;

	private _JTableMain tblStatusTagging;
	private JList rowHeaderStatusTagging;
	private _JDateChooser dteEPEB_Date;
	private _JDateChooser dteApproved;
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
	SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy"); 
	
	private _JLookup txtBatch;
	
	public hdmfMon_statusTagging(PagibigStatusMonitoring_v2 psm) {
		this.hdmfMainMod = psm;
		initGUI();
	}

	public hdmfMon_statusTagging(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public hdmfMon_statusTagging(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public hdmfMon_statusTagging(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@SuppressWarnings("unchecked")
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setPreferredSize(new Dimension(0, 60));
			{
				{
					JXPanel panNorth1 = new JXPanel(new GridLayout(1, 2, 5, 5));
					pnlNorth.add(panNorth1, BorderLayout.LINE_START);
					panNorth1.setPreferredSize(new Dimension(600, 0));
					panNorth1.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						{
							pnlNorthWest = new JPanel(new BorderLayout(5, 5));
							panNorth1.add(pnlNorthWest);
							{
								lblStatusDate = new JLabel("Date: ", JLabel.TRAILING); 
								pnlNorthWest.add(lblStatusDate, BorderLayout.WEST);
								lblStatusDate.setHorizontalAlignment(JTextField.LEFT);
							}
							{
								dteTransDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnlNorthWest.add(dteTransDate, BorderLayout.CENTER);
								dteTransDate.setDate(Calendar.getInstance().getTime());
								
							}
						}
						{
							pnlNorthEast = new JPanel(new BorderLayout(5, 5));
							panNorth1.add(pnlNorthEast);
							{
								lblStage = new JLabel("Stage", JLabel.TRAILING);
								pnlNorthEast.add(lblStage, BorderLayout.WEST);
								lblStage.setHorizontalAlignment(JTextField.CENTER);
								lblStage.setPreferredSize(new Dimension(75, 0));
							}
							{
								cmbStage = new JComboBox(new String [] {
									"", 
									"MSVS Reverified", 					//	1
									"Loan Filed",						//	2
									"Loan Filed G to G",				//	3
									"NOA Released", 				    //  4
									"With DOA Signed",					//	5
									"For TCT Annotation", 				//	6
									"With NOA Extension", 				//	7
									"With EPEB", 						//	8
									"With Annotated TCT", 				//	9
									"For Post Approval", 				//	10
									"For Post Approval G to G", 		//	11
									"With Confirmation Of Appraisal", 	//	12
									"NOA Signed",						//	13
									"Loan Returned(First Filing)", 		//	14
									"Loan Returned(Post Approval)", 	//	15
									"With C.I. Report"					//	16
								});
								
								pnlNorthEast.add(cmbStage, BorderLayout.CENTER);
								cmbStage.addItemListener(new ItemListener() {

									public void itemStateChanged(ItemEvent e) {
										int selected_item = cmbStage.getSelectedIndex();
										
										PagibigStatusMonitoring_v2.btnState(false, false, false);
										
										rowHeaderStatusTagging.setModel(new DefaultListModel());
										scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
										dteTransDate.setEditable(true);
										dteTransDate.getCalendarButton().setEnabled(true);
										
										if (selected_item==4) {
											txtBatch.setLookupSQL("select distinct ON (trans_no, tran_date) coalesce(trans_no, '') as batch_no, \n" + 
													"tran_date::Date as noa_released_date\n" + 
													"from rf_buyer_status\n" + 
													"where byrstatus_id = '35'\n" + 
													"order by tran_date desc");
											txtBatch.setEnabled(true);
										} else if (selected_item==12) {
											txtBatch.setLookupSQL("select distinct ON (trans_no, tran_date) coalesce(trans_no, '') as batch_no, \n" + 
													"tran_date::Date as noa_released_date\n" + 
													"from rf_buyer_status\n" + 
													"where byrstatus_id = '58'\n" + 
													"order by tran_date desc");
											txtBatch.setEnabled(true);
										} else if(selected_item == 9) {
											txtBatch.setLookupSQL(sqlBatch("46"));
											txtBatch.setEnabled(true);
										} else if(selected_item == 10) {
											txtBatch.setLookupSQL(sqlBatch("48"));
											txtBatch.setEnabled(true);
										} else if(selected_item == 11) {
											txtBatch.setLookupSQL(sqlBatch("144"));
											txtBatch.setEnabled(true);
										} else if(selected_item == 14) {
											txtBatch.setLookupSQL("select distinct ON (trans_no, tran_date) coalesce(trans_no, '') as batch_no, \n" + 
													"tran_date::Date as noa_released_date\n" + 
													"from rf_buyer_status\n" + 
													"where byrstatus_id = '43'\n" + 
													"order by tran_date desc");
											txtBatch.setEnabled(true);
										} else if(selected_item == 15) {
											txtBatch.setEnabled(true);
										} else if(selected_item == 16) {
											txtBatch.setEnabled(true);
											txtBatch.setLookupSQL("select distinct coalesce(trans_no, '') as batch_no, \n" + 
													"tran_date::Date as ci_date \n" + 
													"from rf_buyer_status \n" + 
													"where byrstatus_id = '136' \n" + 
													"order by coalesce(trans_no, '') desc, tran_date::date desc");
										} else {
											txtBatch.setEnabled(false);
										}
										
										txtBatch.setValue("");
										
										if (selected_item==1) {
											modelMSVS = new model_hdmf_msvs_reverified();
											
											tblStatusTagging = new _JTableMain(modelMSVS);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											
											tblStatusTagging.getColumnModel().getColumn(0).setPreferredWidth(25);
											
											tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(50);
											tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(50);
											tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(50);
											tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(75);
											
											tblStatusTagging.getColumnModel().getColumn(5).setPreferredWidth(125);
											tblStatusTagging.getColumnModel().getColumn(6).setPreferredWidth(125);
											tblStatusTagging.getColumnModel().getColumn(7).setPreferredWidth(125);
											
											tblStatusTagging.getColumnModel().getColumn(8).setPreferredWidth(125);
											tblStatusTagging.getColumnModel().getColumn(9).setPreferredWidth(125);
											
											_PagibigStatusMonitoring.displayMSVSReverified(
													hdmfMainMod.txtCoID.getValue(), 
													hdmfMainMod.txtProID.getValue(), 
													hdmfMainMod.txtPhaseID.getValue(), 
													dteTransDate.getDate(), 
													modelMSVS, 
													rowHeaderStatusTagging, false);
											scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
											PagibigStatusMonitoring_v2.btnState(true, false, false);
											
											System.out.println("");
											System.out.println("tblStatusTagging.isEnabled(): " + tblStatusTagging.isEnabled());
											System.out.println("tblStatusTagging.isEditable(): " + tblStatusTagging.isEditable());
											
											tblStatusTagging.hideColumns("entity_id", "projcode", "pbl_id", "seq_no");
											tblStatusTagging.getTableHeader().setEnabled(false);
											tblStatusTagging.addMouseListener(hdmfMon_statusTagging.this);
											
											tblStatusTagging.setSortable(false);
										}
										
										if(selected_item == 2){
											modelLoanFiled = new modelPST_LoanFiled();
											modelLoanFiled.clear();
											tblStatusTagging = new _JTableMain(modelLoanFiled);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											scrollStatusTagging.setRowHeaderView(rowHeaderStatusTagging);
											tblStatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											//tblStatusTagging.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(sdf2));
											//lblStatusDate.setText("Loan Filed Date");
										}
										
										if(selected_item == 3){
											modelLoanFiled = new modelPST_LoanFiled();
											modelLoanFiled.clear();
											tblStatusTagging = new _JTableMain(modelLoanFiled);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											scrollStatusTagging.setRowHeaderView(rowHeaderStatusTagging);
											tblStatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											//tblStatusTagging.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(sdf2));
											//lblStatusDate.setText("Loan Filed Date");
										}
										
										/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
										/*									 */
										/*			NOA Released			 */
										/*									 */
										/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
										if(selected_item == 4){
											modelNOAReleased = new modelNOATagging2();
											modelNOAReleased.clear();
											tblStatusTagging = new _JTableMain(modelNOAReleased);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("Client ID", "Proj. ID", "PBL ID", "Seq." ,"BuyerType", "Project");
											//lblStatusDate.setText("NOA Released Date");
										}
										
										/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
										/*									 */
										/*			DOA Signed				 */
										/*									 */
										/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
										if(selected_item == 5){
											modelDOASigned = new modelPST_DOA_Signed();
											modelDOASigned.clear();
											tblStatusTagging = new _JTableMain(modelDOASigned);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("Proj. Name", "Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											//lblStatusDate.setText("DOA Signed Date");
										}
										
										if(selected_item == 6){//For TCT Annotation
											modelForTCTAnnotation = new modelPST_ForTCTAnnotation();
											modelForTCTAnnotation.clear();
											tblStatusTagging = new _JTableMain(modelForTCTAnnotation);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("Proj. Name", "Entity ID", "Proj. ID", "PBL ID", "Seq", "Aff. Ratio", "% Constructed","NSP","Loan Amt","House Model");
											//lblStatusDate.setText("TCT Forwarded Date");
										}
										
										if(selected_item == 7){
											/*	Modified by Mann2x; Date Modified: December 12, 2017; 
											/*
											modelNOAExpiring = new modelPST_NOA_Expiring();
											modelNOAExpiring.clear();
											tblStatusTagging = new _JTableMain(modelNOAExpiring);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("No.", "Proj. Alias", "Proj. Name" ,"Entity ID", "Proj. ID", "PBL ID", "Seq");
											dteTransDate.setDate(Calendar.getInstance().getTime());
											dteTransDate.setEditable(false);
											dteTransDate.getCalendarButton().setEnabled(false);
											*/
											
											modelNOAExtension = new model_hdmf_noaextension();
											modelNOAExtension.clear();
											tblStatusTagging = new _JTableMain(modelNOAExtension);
											
											tblStatusTagging.getColumnModel().getColumn(0).setPreferredWidth(50);
											tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(35);
											tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(265);
											tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(123);
											tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(155);
											tblStatusTagging.getColumnModel().getColumn(5).setPreferredWidth(200);
											tblStatusTagging.getColumnModel().getColumn(6).setPreferredWidth(143);
											
											tblStatusTagging.hideColumns("No.", "entity_id", "projcode", "pbl_id", "seq_no");
											tblStatusTagging.getTableHeader().setEnabled(false);
											
											scrollStatusTagging.setViewportView(tblStatusTagging);
											dteTransDate.setDate(Calendar.getInstance().getTime());
											dteTransDate.setEditable(false);
											dteTransDate.getCalendarButton().setEnabled(false);
										}
										
										if(selected_item == 8){
											modelWithEPEB = new modelPST_With_EPEB();
											
											tblStatusTagging = new _JTableMain(modelWithEPEB);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("Proj. Alias", "Proj. Name" ,"Entity ID", "Proj. ID", "PBL ID", "Seq");
											tblStatusTagging.addMouseListener(hdmfMon_statusTagging.this);
											dteTransDate.setEditable(false);
											dteTransDate.getCalendarButton().setEnabled(false);
											//lblStatusDate.setText("Trans. Date");
										}
										
										if(selected_item == 9){
											modelWithAnnotatedTCT = new modelPST_With_Annotated_TCT();
											
											tblStatusTagging = new _JTableMain(modelWithAnnotatedTCT);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("Proj. Alias", "Proj. Name" ,"Entity ID", "Proj. ID", "PBL ID", "Seq");
										}
										
										if(selected_item == 10 || selected_item == 11){
											modelForPostApproval = new modelPST_ForPostApproval();
											
											tblStatusTagging = new _JTableMain(modelForPostApproval);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("Proj. Alias", "Proj. Name" ,"Entity ID", "Proj. ID", "PBL ID", "Seq");
										}
										
										
										/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
										/*									 */
										/*	With Confirmation of Appraisal   */
										/*									 */
										/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
										if (selected_item == 12) {
											modelHouseInspection = new model_hdmf_postInspection();
											tblStatusTagging = new _JTableMain(modelHouseInspection);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("Proj. Alias", "Proj. Name" ,"Entity ID", "Proj. ID", "PBL ID", "Seq");
											
											tblStatusTagging.getColumnModel().getColumn(0).setPreferredWidth(250);
											
											tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(35);
											tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(50);
											tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(50);
											tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(50);
											tblStatusTagging.getColumnModel().getColumn(5).setPreferredWidth(100);
											tblStatusTagging.getColumnModel().getColumn(6).setPreferredWidth(150);
											tblStatusTagging.getColumnModel().getColumn(7).setPreferredWidth(200);
											
											tblStatusTagging.getColumnModel().getColumn(8).setPreferredWidth(250);
											tblStatusTagging.getTableHeader().setEnabled(false);
											//lblStatusDate.setText("Actual Date");
											/*
											_PagibigStatusMonitoring.displayForTaggingOfCOA(modelHouseInspection, rowHeaderStatusTagging, "");
											scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
											PagibigStatusMonitoring_v2.btnState(true, false, false);
											*/
										}
										
										/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
										/*									 */
										/*			NOA Signed				 */
										/*									 */
										/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
										if(selected_item == 13){
											modelNOASigning = new model_hdmf_NOASigning();
											
											tblStatusTagging = new _JTableMain(modelNOASigning);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");
											tblStatusTagging.setSortable(false);
											
											tblStatusTagging.getColumnModel().getColumn(0).setPreferredWidth(30);
											tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(240);
											tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(124);
											tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(90);
											tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(110);
											tblStatusTagging.getColumnModel().getColumn(5).setPreferredWidth(110);
											
										}
										
										/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
										/*									 */
										/*			Loan Returned			 */
										/*									 */
										/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
										if(selected_item==14 || selected_item==15){
											modelLoanReturned = new model_hdmf_loanReturned();
											
											tblStatusTagging = new _JTableMain(modelLoanReturned);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");
											tblStatusTagging.setSortable(false);
											
											tblStatusTagging.getColumnModel().getColumn(0).setPreferredWidth(30);
											tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(225);
											tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(125);
											tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(125);
											tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(250);
											tblStatusTagging.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(sdf2));
										}
										
										if (selected_item == 16) {
											modelCITagging = new model_hdmf_ci_tagging();
											
											tblStatusTagging = new _JTableMain(modelCITagging);
											scrollStatusTagging.setViewportView(tblStatusTagging);
											tblStatusTagging.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");
											tblStatusTagging.setSortable(false);
											
											tblStatusTagging.getColumnModel().getColumn(0).setPreferredWidth(30);
											tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(249);
											tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(175);
											tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(125);
											tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(125);
											tblStatusTagging.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(sdf2));
											
											/*
											tblStatusTagging.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
												public void valueChanged(ListSelectionEvent e) {
													if ((Boolean) tblStatusTagging.getValueAt(tblStatusTagging.getSelectedRow(), 0)) {
														System.out.println("");
														System.out.println("Tagged!");
													} else {
														System.out.println("");
														System.out.println("Not tagged!");
													}
												}
											});
											*/
										}										
										
										rowHeaderStatusTagging = tblStatusTagging.getRowHeader();
										rowHeaderStatusTagging.setModel(new DefaultListModel());
										scrollStatusTagging.setRowHeaderView(rowHeaderStatusTagging);
										scrollStatusTagging.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								});
							}
						}
					}
				}
				{
					JXPanel panNorth2 = new JXPanel(new GridLayout(1, 2, 5, 5));
					pnlNorth.add(panNorth2, BorderLayout.CENTER);
					panNorth2.setBorder(JTBorderFactory.createTitleBorder("Batch", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						/*
						txtSearch = new JTextField("");
						panNorth2.add(txtSearch, BorderLayout.CENTER);
						txtSearch.setEnabled(false);
						txtSearch.setHorizontalAlignment(JTextField.LEADING);
						*/
						txtBatch = new _JLookup("");
						panNorth2.add(txtBatch, BorderLayout.CENTER);
						txtBatch.setEnabled(false);
						txtBatch.setHorizontalAlignment(JTextField.CENTER);
						txtBatch.setReturnColumn(0);
						txtBatch.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									String batch_no = (String) data[0];
									if (cmbStage.getSelectedIndex()==4) {
										generate_noa gen_noa = new generate_noa();
										Thread thread = new Thread(gen_noa);
										thread.start();
										try {
											thread.join();
										} catch (InterruptedException e) {

										}
										PagibigStatusMonitoring_v2.btnState(false, true, false);	
									
									} else if (cmbStage.getSelectedIndex()==9) {
										_PagibigStatusMonitoring.displayPST_WithAnnotatedTCT(hdmfMainMod.txtCoID.getValue(), hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), batch_no ,modelWithAnnotatedTCT, rowHeaderStatusTagging);
										scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
										tblStatusTagging.packAll();
										PagibigStatusMonitoring_v2.btnState(false, true, false);
									} else if(cmbStage.getSelectedIndex() == 10 ){
										_PagibigStatusMonitoring.displayPST_ForApproval(hdmfMainMod.txtCoID.getValue(), hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), batch_no ,modelForPostApproval, rowHeaderStatusTagging);
										scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
										tblStatusTagging.packAll();
										PagibigStatusMonitoring_v2.btnState(false, true, false);
									} else if(cmbStage.getSelectedIndex() == 11 ){
										_PagibigStatusMonitoring.displayPST_ForApproval_GtoG(hdmfMainMod.txtCoID.getValue(), hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), batch_no ,modelForPostApproval, rowHeaderStatusTagging);
										scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
										tblStatusTagging.packAll();
										PagibigStatusMonitoring_v2.btnState(false, true, false);
									} else if(cmbStage.getSelectedIndex() == 12){
										_PagibigStatusMonitoring.displayForTaggingOfCOA(modelHouseInspection, rowHeaderStatusTagging, txtBatch.getValue(), hdmfMainMod.txtCoID.getValue(), hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue());
										PagibigStatusMonitoring_v2.btnState(false, false, false);
									} else if(cmbStage.getSelectedIndex() == 14){
										_PagibigStatusMonitoring.displayForTaggingOfLoanReturnedFF(modelLoanReturned, rowHeaderStatusTagging, txtBatch.getValue());
										PagibigStatusMonitoring_v2.btnState(false, false, false);
									} else if(cmbStage.getSelectedIndex() == 16){
										_PagibigStatusMonitoring.displayForTaggingOfCI(modelCITagging, rowHeaderStatusTagging, txtBatch.getValue());
										PagibigStatusMonitoring_v2.btnState(false, false, false);
									}
								}
							}
						});
					}
				}
			}
		}
		{
			pnlCenter = new JPanel(new BorderLayout(5, 5));
			this.add(pnlCenter, BorderLayout.CENTER);
			{/*
				dteEPEB_Date = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
				pnlCenter.add(dteEPEB_Date, BorderLayout.NORTH);
				dteEPEB_Date.setDate(null);
				((JTextFieldDateEditor)dteEPEB_Date.getDateEditor()).setEditable(false);
				dteEPEB_Date.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				((JTextFieldDateEditor)dteEPEB_Date.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
					public void insertUpdate(DocumentEvent evt) {
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
						System.out.println("Dumaan dito sa Date");
						
						int selected_row = tblStatusTagging.getSelectedRow();
						
						Boolean isSelected = (Boolean) modelWithEPEB.getValueAt(selected_row, 0);
						
						if(modelWithEPEB.getValueAt(tblStatusTagging.getSelectedRow(),0).equals(true)){
							modelWithEPEB.setValueAt(""+sdf.format(dteEPEB_Date.getDate()),tblStatusTagging.getSelectedRow(),10);
						}
					}
					public void changedUpdate(DocumentEvent e) {}
					public void removeUpdate(DocumentEvent e) {}
				});
			*/}
			
			{

				dteEPEB_Date = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
				pnlCenter.add(dteEPEB_Date);
				dteEPEB_Date.setDate(null);
				((JTextFieldDateEditor)dteEPEB_Date.getDateEditor()).setEditable(false);
				dteEPEB_Date.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				((JTextFieldDateEditor)dteEPEB_Date.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
					public void insertUpdate(DocumentEvent evt) {
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
						if (modelWithEPEB.getValueAt(tblStatusTagging.getSelectedRow(),0).equals(true)) {
							modelWithEPEB.setValueAt(""+sdf.format(dteEPEB_Date.getDate()),tblStatusTagging.getSelectedRow(),4);
						}
					}
					public void changedUpdate(DocumentEvent e) {}
					public void removeUpdate(DocumentEvent e) {}
				});
			
			}
			{
				dteApproved = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
				pnlCenter.add(dteApproved);
				dteApproved.setDate(null);
				((JTextFieldDateEditor)dteApproved.getDateEditor()).setEditable(false);
				dteApproved.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				((JTextFieldDateEditor)dteApproved.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
					public void insertUpdate(DocumentEvent evt) {
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						modelMSVS.setValueAt(""+sdf.format(dteApproved.getDate()), tblStatusTagging.getSelectedRow(), 8);
					}
					public void changedUpdate(DocumentEvent e) {
						
					}
					public void removeUpdate(DocumentEvent e) {
						
					}
				});
			
			}
			{
				scrollStatusTagging = new JScrollPane();
				pnlCenter.add(scrollStatusTagging, BorderLayout.CENTER);
				{
					modelDOASigned = new modelPST_DOA_Signed();
					modelForTCTAnnotation = new modelPST_ForTCTAnnotation();
					modelNOAExtension = new model_hdmf_noaextension();
					modelWithEPEB = new modelPST_With_EPEB();
					modelWithAnnotatedTCT = new modelPST_With_Annotated_TCT();
					
					tblStatusTagging = new _JTableMain(modelDOASigned);
					scrollStatusTagging.setViewportView(tblStatusTagging);
					scrollStatusTagging.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					tblStatusTagging.hideColumns("Proj. Name", "Entity ID", "Proj. ID", "PBL ID", "Seq. No");
					tblStatusTagging.setSortable(false);
					//tblStatusTagging.setSelectionMode(FncTables.SING);
				}
				{
					rowHeaderStatusTagging = tblStatusTagging.getRowHeader();
					rowHeaderStatusTagging.setModel(new DefaultListModel());
					scrollStatusTagging.setRowHeaderView(rowHeaderStatusTagging);
					scrollStatusTagging.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}
	}
	
	private void previewLoanFiled(){
		
		ArrayList<TD_PST_LoanFiled> listLoanFiled = new ArrayList<TD_PST_LoanFiled>();
		
		for(int x= 0; x<modelLoanFiled.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelLoanFiled.getValueAt(x, 0);
		
			if(isSelected){
				
				String unit_desc = (String) modelLoanFiled.getValueAt(x, 1);
				String client_name = (String) modelLoanFiled.getValueAt(x, 2);
				Date date_completed = (Date) modelLoanFiled.getValueAt(x, 3);
				String pct_constructed = (String) modelLoanFiled.getValueAt(x, 4);
				Integer dp_term = (Integer) modelLoanFiled.getValueAt(x, 5);
				String pmt_stage = (String) modelLoanFiled.getValueAt(x, 6);
			
				listLoanFiled.add(new TD_PST_LoanFiled(unit_desc, client_name, date_completed, pct_constructed, dp_term, pmt_stage));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("listLoanFiled", listLoanFiled);
		mapParameters.put("prepared_by", UserInfo.FullName2);
		mapParameters.put("co_name", hdmfMainMod.txtCo.getText());
		
		FncReport.generateReport("/Reports/rptPST_LoanFiledAccts.jasper", "Loan Filed Accounts", mapParameters);
	}
	
	private void previewLoanFiled_GtoG(){
		
		ArrayList<TD_PST_LoanFiled> listLoanFiled = new ArrayList<TD_PST_LoanFiled>();
		
		for(int x= 0; x<modelLoanFiled.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelLoanFiled.getValueAt(x, 0);
		
			if(isSelected){
				
				String unit_desc = (String) modelLoanFiled.getValueAt(x, 1);
				String client_name = (String) modelLoanFiled.getValueAt(x, 2);
				Date date_completed = (Date) modelLoanFiled.getValueAt(x, 3);
				String pct_constructed = (String) modelLoanFiled.getValueAt(x, 4);
				Integer dp_term = (Integer) modelLoanFiled.getValueAt(x, 5);
				String pmt_stage = (String) modelLoanFiled.getValueAt(x, 6);
			
				listLoanFiled.add(new TD_PST_LoanFiled(unit_desc, client_name, date_completed, pct_constructed, dp_term, pmt_stage));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("listLoanFiled", listLoanFiled);
		mapParameters.put("prepared_by", UserInfo.FullName2);
		mapParameters.put("co_name", hdmfMainMod.txtCo.getText());
		
		FncReport.generateReport("/Reports/rptPST_LoanFiledAccts_gtog.jasper", "Loan Filed Accounts(G to G)", mapParameters);
	}
	
	private void previewForApproval_TCTAnnotated_Transmittal(String batch_no){
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company_name", hdmfMainMod.txtCo.getText());
		mapParameters.put("proj_name", hdmfMainMod.txtPro.getText());
		mapParameters.put("prepared_by", UserInfo.FullName2);
		mapParameters.put("batch_no", batch_no);
		
		FncReport.generateReport("/Reports/rptForPostApproval_TCTAnnotated_Transmittal_v2.jasper", "TCT Annotated Accounts", mapParameters);
	}
	
	private void previewForApproval_TCTAnnotated_Transmittal_GtoG(String batch_no){
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company_name", hdmfMainMod.txtCo.getText());
		mapParameters.put("proj_name", hdmfMainMod.txtPro.getText());
		mapParameters.put("prepared_by", UserInfo.FullName2);
		mapParameters.put("batch_no", batch_no);
		
		FncReport.generateReport("/Reports/rptForPostApproval_TCTAnnotated_Transmittal_v2_gtog.jasper", "TCT Annotated Accounts (G to G)", mapParameters);
	}
	
	private void previewForApproval_EPEB_Transmittal(String batch_no){
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company_name", hdmfMainMod.txtCo.getText());
		mapParameters.put("proj_name", hdmfMainMod.txtPro.getText());
		mapParameters.put("prepared_by", UserInfo.FullName2);
		mapParameters.put("batch_no", batch_no);
		
		FncReport.generateReport("/Reports/rptForPostApproval_withEPEB_Transmittal_v2.jasper", "For Approval Accounts", mapParameters);
	}
	
//	private void previewForApproval_EPEB_Transmittal_GtoG(String batch_no){
//		Map<String, Object> mapParameters = new HashMap<String, Object>();
//		mapParameters.put("company_name", hdmfMainMod.txtCo.getText());
//		mapParameters.put("proj_name", hdmfMainMod.txtPro.getText());
//		mapParameters.put("prepared_by", UserInfo.FullName2);
//		mapParameters.put("batch_no", batch_no);
//		
//		FncReport.generateReport("/Reports/rptForPostApproval_withEPEB_Transmittal_v2_gtog.jasper", "For Approval Accounts (G To G)", mapParameters);
//	}
	
	private void previewDOASigned(){
		ArrayList<TD_PST_WithDOASigned> listDOASignedAccts = new ArrayList<TD_PST_WithDOASigned>();
		
		for(int x = 0; x < modelDOASigned.getRowCount(); x++){
			
			Boolean isSelected = (Boolean) modelDOASigned.getValueAt(x, 0);
			
			if(isSelected){
				String entity_name = (String) modelDOASigned.getValueAt(x, 4);
				String unit_desc = (String) modelDOASigned.getValueAt(x, 2);
				Date noa_released_date = (Date) modelDOASigned.getValueAt(x, 8);
				listDOASignedAccts.add(new TD_PST_WithDOASigned(unit_desc, entity_name, noa_released_date));
			}
		}
		
		Map<String, ArrayList> mapParameters = new HashMap<String, ArrayList>();
		mapParameters.put("listDOASignedAccts", listDOASignedAccts);
		
		FncReport.generateReport("/Reports/rptPST_DOASignedAccts.jasper", "DOA Signed Accounts", mapParameters);
	}
	
	private void previewForTCTAnnotation(){
		ArrayList<TD_PST_TCT_ForwardedToRD> listTCTForwardedToRD = new ArrayList<TD_PST_TCT_ForwardedToRD>();
	
		for(int x= 0; x<modelForTCTAnnotation.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelForTCTAnnotation.getValueAt(x, 0);
		
			if(isSelected){
				
				String unit_desc = (String) modelForTCTAnnotation.getValueAt(x, 2);
				String entity_name = (String) modelForTCTAnnotation.getValueAt(x, 4);
				BigDecimal nsp = (BigDecimal) modelForTCTAnnotation.getValueAt(x, 8);
				BigDecimal loan_amt = (BigDecimal) modelForTCTAnnotation.getValueAt(x, 9);
				String house_model = (String) modelForTCTAnnotation.getValueAt(x, 11);
				String acct_status = (String) modelForTCTAnnotation.getValueAt(x, 16);
				
				System.out.printf("Display value of house model: %s%n", house_model);
				
				listTCTForwardedToRD.add(new TD_PST_TCT_ForwardedToRD(entity_name, unit_desc, nsp, loan_amt, house_model, acct_status));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("listTCTForwardedToRD", listTCTForwardedToRD);
		
		FncReport.generateReport("/Reports/rptPST_TCT_Forwarded.jasper", "TCT Forwarded to RD", mapParameters);
	}
	
	private void previewWithEPEB(){
		ArrayList<TD_PST_WithEPEB> listWithEPEB = new ArrayList<TD_PST_WithEPEB>();
		
		for(int x= 0; x<modelWithEPEB.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelWithEPEB.getValueAt(x, 0);
			
			if(isSelected){
				String unit_desc = (String) modelWithEPEB.getValueAt(x, 1);
				String entity_name = (String) modelWithEPEB.getValueAt(x, 2);
				String epeb_no = (String) modelWithEPEB.getValueAt(x, 3);
				String epeb_date = (String) modelWithEPEB.getValueAt(x, 4);
				
				System.out.printf("Display epeb date: %s%n", epeb_date);
				
				listWithEPEB.add(new TD_PST_WithEPEB(entity_name, unit_desc, epeb_no, epeb_date));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("listWithEPEB", listWithEPEB);
		
		FncReport.generateReport("/Reports/rptPST_With_EPEB.jasper", "With EPEB", mapParameters);
	}
	
	private void previewWithAnnotatedTCT(){
		ArrayList<TD_PST_WithAnnotatedTCT> listWithAnnotatedTCT = new ArrayList<TD_PST_WithAnnotatedTCT>();
		
		for(int x=0; x<modelWithAnnotatedTCT.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelWithAnnotatedTCT.getValueAt(x, 0);
		
			if(isSelected){
				String unit_desc = (String) modelWithAnnotatedTCT.getValueAt(x, 1);
				String entity_name = (String) modelWithAnnotatedTCT.getValueAt(x, 2);
				Date tct_forwarded_date = (Date) modelWithAnnotatedTCT.getValueAt(x, 3);
				
				listWithAnnotatedTCT.add(new TD_PST_WithAnnotatedTCT(entity_name, unit_desc, tct_forwarded_date));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("listWithAnnotatedTCT", listWithAnnotatedTCT);
		
		FncReport.generateReport("/Reports/rptPST_WithAnnotatedTCT.jasper", "TCT Annotated", mapParameters);
	}
	
	private void previewForPostApproval(){
		ArrayList<TD_PST_ForPostApproval> listForPostApproval = new ArrayList<TD_PST_ForPostApproval>();
	
		for(int x= 0; x<modelForPostApproval.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelForPostApproval.getValueAt(x, 0);
		
			if(isSelected){
				String unit_desc = (String) modelForPostApproval.getValueAt(x, 1);
				String entity_name = (String) modelForPostApproval.getValueAt(x, 2);
				Date tct_annotated = (Date) modelForPostApproval.getValueAt(x, 4);
			
				listForPostApproval.add(new TD_PST_ForPostApproval(entity_name, unit_desc, tct_annotated));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("listForPostApproval", listForPostApproval);
		
		FncReport.generateReport("/Reports/rptPST_ForPostApproval.jasper", "For Post Approval", mapParameters);
	}
	
	private void previewForPostApproval_GtoG(){
		ArrayList<TD_PST_ForPostApproval> listForPostApproval = new ArrayList<TD_PST_ForPostApproval>();
	
		for(int x= 0; x<modelForPostApproval.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelForPostApproval.getValueAt(x, 0);
		
			if(isSelected){
				String unit_desc = (String) modelForPostApproval.getValueAt(x, 1);
				String entity_name = (String) modelForPostApproval.getValueAt(x, 2);
				Date tct_annotated = (Date) modelForPostApproval.getValueAt(x, 4);
			
				listForPostApproval.add(new TD_PST_ForPostApproval(entity_name, unit_desc, tct_annotated));
			}
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("listForPostApproval", listForPostApproval);
		
		FncReport.generateReport("/Reports/rptPST_ForPostApproval_gtog.jasper", "For Post Approval (G to G)", mapParameters);
	}
	
	private void createAPMASTER(String directory) throws DBFException, IOException{
		String col_names [] = {"DEVCODE", "PROJCODE", "APPLICNO", "APPLICDATE", "GUIDELINE", "SHLF" , "PROG_CD", "LNAME", "FNAME", 
				"MID", "EXTNAME", "PURP_CD", "PAYMODE", "AMTAPPLIED", "TERM", "REGHOLDER", "ISMORT", "LANDAREA", 
				"EXIST_STRY", "PROP_STRY", "EXIST_FLR", "PROP_FLR", "TCTNO", "TAXDECLOT", "TAXDECBLDG", "LOT_UNIT", 
				"BLK_BLDG", "HOUSEAGE", "HDMFID", "BDAY", "GENDER", "STATUS", "OTHER", "EMAILADDR", "OWNERSHIP", "RENT", 
				"YRSTAY", "SSS_GSISID", "TIN", "CELLPHONE", "BEEPERNO","NATURE_BUS", "POSITION", "YREMP_BUS", "FILIPINO", 
				"DEPENDENTS"};
		
		Integer char_columns [] = {2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 21 ,22, 23, 24, 25, 26, 28, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42};
		Integer numeric_columns [] = {0, 1, 13, 14, 17, 18, 19, 20, 27, 35, 36, 43, 45};
		Integer bool_columns [] = {16, 44};
		Integer date_columns [] = {3, 29};
		
		FncExport.createDBF_FromModelWithQuery(col_names, char_columns, bool_columns, date_columns, numeric_columns ,modelLoanFiled, "APMASTER.DBF", directory);
		
	}
	
	private void createADDRESSA(String directory) throws DBFException, IOException{
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "UNIT_NO", "BLDG_NAME", "STREET", "VILLAGE", "TOWN_CITY", "STATE", "COUNTRY", "ZIPCODE"};
		
		Integer char_columns [] = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer numeric_columns [] = {1};
		
		FncExport.createADDRESSA_DBF_FromModelWithQuery(col_names, char_columns, numeric_columns, modelLoanFiled, "ADDRESSB.DBF", directory);
	}
	
	private void createADDRESSB(String directory) throws DBFException, IOException{
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "TELNO", "HOUSE_NO", "STREET", "SUBD", "BRGY_VILL", "TOWN_CITY", "PROVINCE", "REGION", "ZIPCODE"};
		
		Integer char_columns [] = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
		Integer numeric_columns [] = {1};
		
		FncExport.createADDRESSB_DBF_FromModelWithQuery(col_names, char_columns, numeric_columns, modelLoanFiled, "ADDRESSB.DBF", directory);
		
	}
	
	private void createADDRESSE(String directory) throws DBFException, IOException{
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "EYERID", "BUS_NAME", "TELNO", "RM_FLR", "BLDG_NAME", "STREET", "BRGY_VILL", "TOWN_CITY", "PROVINCE", "REGION", "ZIPCODE"};
		
		Integer char_columns [] = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		Integer numeric_columns [] = {1};
		
		FncExport.createADDRESSE_DBF_FromModelWithQuery(col_names, char_columns, numeric_columns, modelLoanFiled, "ADDRESSE.DBF", directory);
	}
	
	private void createCHAR_REF(String directory) throws DBFException, IOException{
		String col_names [] = {"APPLICNO", "BCD", "SEQ", "NAME", "ADDRESS", "TELNO"};
		
		Integer char_columns [] = {0, 3, 4, 5};
		Integer numeric_columns [] = {1, 2};
		
		FncExport.createCHAR_REF_DBF_FromModelWithQuery(col_names, char_columns, numeric_columns, modelLoanFiled, "CHAR_REF.DBF", directory);
	}
	
	private void createDOA_DATA(String directory) throws DBFException, IOException{
		String col_names [] = {"APPLICNO", "SEL_DATE", "SEL_DOCNO", "SEL_PGNO", "SEL_BOOKNO", "SEL_SERIES", "NOTARY_PUB", "ADA_DATE"};
		
		Integer char_columns [] = {0, 2, 3, 4, 5, 6};
		Integer date_columns [] = {1, 7};
		
		FncExport.createDOADATA_DBF_FromModelWithQuery(col_names, char_columns, date_columns, modelLoanFiled, "DOADATA.DBF", directory);
		
		//FncExport.createADDRESSDBF_FromModelWithQuery(col_names, char_columns, numeric_columns, modelLoanFiled, "DOA_DATA.DBF");
	}
	
	private void createINSURE(String directory) throws DBFException, IOException{
		String col_names [] = {"APPLICNO", "GBCODE", "GBTYPE", "REFERNO", "REFDATE"};
		
		Integer char_columns [] = {0, 2, 3};
		Integer numeric_columns [] = {1};
		Integer date_columns [] = {4};
		
		FncExport.createINSURE_DBF_FromModelWithQuery(col_names, char_columns, date_columns, numeric_columns, modelLoanFiled, "INSURE.DBF", directory);
	}
	
	//private void create
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void Post() throws DBFException, IOException{
		
		if(cmbStage.getSelectedIndex() == 1){
			_PagibigStatusMonitoring.saveMSVSReverified(modelMSVS, dteTransDate.getDate());
			PagibigStatusMonitoring_v2.btnState(false, true, false);
		}
		
		if(cmbStage.getSelectedIndex() == 2){
			_PagibigStatusMonitoring.saveLoanFiledAccts(dteTransDate.getDate(), modelLoanFiled);
			
			try {
				String strDir = _RealTimeDebit.OpenDir("Folder");
				
				createADDRESSA(strDir);
				createADDRESSB(strDir);
				createADDRESSE(strDir);
				createAPMASTER(strDir);
				createCHAR_REF(strDir);
				createDOA_DATA(strDir);
				createINSURE(strDir);
				
			} catch (IOException e) {

			}
			
			modelLoanFiled.clear();
			rowHeaderStatusTagging.setModel(new DefaultListModel());
			PagibigStatusMonitoring_v2.btnState(false, false, false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Post", JOptionPane.INFORMATION_MESSAGE, null);
		}
		
		if(cmbStage.getSelectedIndex() == 3){
			_PagibigStatusMonitoring.saveLoanFiledAccts_GtoG(dteTransDate.getDate(), modelLoanFiled);
			
			try {
				String strDir = _RealTimeDebit.OpenDir("Folder");
				
				createADDRESSA(strDir);
				createADDRESSB(strDir);
				createADDRESSE(strDir);
				createAPMASTER(strDir);
				createCHAR_REF(strDir);
				createDOA_DATA(strDir);
				createINSURE(strDir);
				
			} catch (IOException e) {

			}
			
			modelLoanFiled.clear();
			rowHeaderStatusTagging.setModel(new DefaultListModel());
			PagibigStatusMonitoring_v2.btnState(false, false, false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Post", JOptionPane.INFORMATION_MESSAGE, null);
		}
				
		if (cmbStage.getSelectedIndex() == 4) {
			boolean blnSuccess = true;
			try {
				_PagibigStatusMonitoring.saveNOAReleasedAccts(dteTransDate.getDate(), modelNOAReleased);
			} catch (Exception ex) {
				blnSuccess = false;
				JOptionPane.showMessageDialog(null, "Set the actual date.");
			}
			
			if (blnSuccess) {
				modelNOAReleased.clear();
				rowHeaderStatusTagging.setModel(new DefaultListModel());
				PagibigStatusMonitoring_v2.btnState(false, false, false);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Post", JOptionPane.INFORMATION_MESSAGE);
				
				txtBatch.setValue(FncGlobal.GetString("select trans_no from rf_buyer_status where byrstatus_id = '35' and status_id = 'A' order by getinteger(trans_no) desc limit 1;"));
				generate_noa gen_noa = new generate_noa();
				Thread thread = new Thread(gen_noa);
				thread.start();
				try {
					thread.join();
				} catch (InterruptedException e) {
	
				}
			} else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts not posted", "Post", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		if(cmbStage.getSelectedIndex() == 5){
			_PagibigStatusMonitoring.saveDOASignedAccounts(FncGlobal.dateFormat(dteTransDate.getTimestamp().toString()), modelDOASigned);
			previewDOASigned();
			modelDOASigned.clear();
			rowHeaderStatusTagging.setModel(new DefaultListModel());
			PagibigStatusMonitoring_v2.btnState(false, false, false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Post", JOptionPane.INFORMATION_MESSAGE, null);
		}
		
		if(cmbStage.getSelectedIndex() == 6){
			_PagibigStatusMonitoring.saveTCTAnnotationAccounts(dteTransDate.getDate(), modelForTCTAnnotation);
			previewForTCTAnnotation();
			modelForTCTAnnotation.clear();
			rowHeaderStatusTagging.setModel(new DefaultListModel());
			PagibigStatusMonitoring_v2.btnState(false, false, false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Post", JOptionPane.INFORMATION_MESSAGE, null);
		}
		
		if(cmbStage.getSelectedIndex() == 7){
			if (_PagibigStatusMonitoring.saveNOAExtension(modelNOAExtension)) {
				PagibigStatusMonitoring_v2.btnState(false, true, false);
				
				for (int x = 0; x < modelNOAExtension.getRowCount(); x++) {
					if (!(Boolean) modelNOAExtension.getValueAt(x, 1)) {
						modelNOAExtension.removeRow(x);
						x = 0; 
					}
				}
				
				for (int x = 0; x < modelNOAExtension.getRowCount(); x++) {
					if (!(Boolean) modelNOAExtension.getValueAt(x, 1)) {
						modelNOAExtension.removeRow(x);
						x = 0; 
					}
				}
				
			} else {
				PagibigStatusMonitoring_v2.btnState(true, true, false);
			}
		}
		
		if(cmbStage.getSelectedIndex() == 8){
			
			_PagibigStatusMonitoring.saveWithEPEB(modelWithEPEB);
			previewWithEPEB();
			modelWithEPEB.clear();
			rowHeaderStatusTagging.setModel(new DefaultListModel());
			PagibigStatusMonitoring_v2.btnState(false, false, false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Post", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(cmbStage.getSelectedIndex() == 9){
			
			String batch_no =  _PagibigStatusMonitoring.saveWithAnnotatedTCT(modelWithAnnotatedTCT);
			
			previewWithAnnotatedTCT();
			modelWithAnnotatedTCT.clear();
			rowHeaderStatusTagging.setModel(new DefaultListModel());
			PagibigStatusMonitoring_v2.btnState(false, false, false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Post", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(cmbStage.getSelectedIndex() == 10){			
			String batch_no = _PagibigStatusMonitoring.saveForPostApproval(modelForPostApproval);
			previewForApproval_TCTAnnotated_Transmittal(batch_no);
			previewForApproval_EPEB_Transmittal(batch_no);
			previewForPostApproval();
			modelForPostApproval.clear();
			rowHeaderStatusTagging.setModel(new DefaultListModel());
			PagibigStatusMonitoring_v2.btnState(false, false, false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Post", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(cmbStage.getSelectedIndex() == 11){			
			String batch_no = _PagibigStatusMonitoring.saveForPostApproval_GtoG(FncGlobal.dateFormat(dteTransDate.getTimestamp().toString()),modelForPostApproval);
			previewForApproval_TCTAnnotated_Transmittal_GtoG(batch_no);
			//previewForApproval_EPEB_Transmittal_GtoG(batch_no);
			previewForPostApproval_GtoG();
			modelForPostApproval.clear();
			rowHeaderStatusTagging.setModel(new DefaultListModel());
			PagibigStatusMonitoring_v2.btnState(false, false, false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Post", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(cmbStage.getSelectedIndex() == 12){
			try {
				txtBatch.setValue(_PagibigStatusMonitoring.saveCOA(modelHouseInspection, dteTransDate.getDate()));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Set the actual date.");
			}
			String strcoid = "";
			String strproid = "";
			String strphase = "";
			String strbatch = "";
			if (txtBatch.getValue()==null || txtBatch.getValue()=="null" || txtBatch.getValue()=="") {
				strbatch = "";
			} else {
				strbatch = txtBatch.getValue();
			}
			
			txtBatch.setValue(FncGlobal.GetString("select trans_no from rf_buyer_status where byrstatus_id = '58' and status_id = 'A' order by trans_no::int desc limit 1;"));
			_PagibigStatusMonitoring.displayForTaggingOfCOA(modelHouseInspection, rowHeaderStatusTagging, strbatch, strcoid, strproid, strbatch);
			PagibigStatusMonitoring_v2.btnState(false, false, false);
		}
		
		if(cmbStage.getSelectedIndex() == 13){
			if (JOptionPane.showConfirmDialog(null, "Use " + dteTransDate.getDateString() + " as signed date?", "Proceed", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				try {
					_PagibigStatusMonitoring.saveNOASigned(modelNOASigning, dteTransDate.getDate());
					for (int x = 0; x < modelNOASigning.getRowCount(); x++) {
						if (!(Boolean) modelNOASigning.getValueAt(x, 0)) {
							modelNOASigning.removeRow(x);
							x = 0;
						}
					}
					JOptionPane.showMessageDialog(null, "Success!");
					PagibigStatusMonitoring_v2.btnState(false, true, false);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Something went wrong.");
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				} 
			}
		}
		
		if(cmbStage.getSelectedIndex() == 14){
			pgUpdate dbExec = new pgUpdate();
			
			dbExec = new pgUpdate();
			dbExec.executeUpdate("delete from tmp_hdmf", true);
			dbExec.commit();
			
			for (int x = 0; x < modelLoanReturned.getRowCount(); x++) {
				if ((Boolean) modelLoanReturned.getValueAt(x, 0)) {
					dbExec = new pgUpdate();
					dbExec.executeUpdate("insert into tmp_hdmf values ('"+modelLoanReturned.getValueAt(x, 1)+"', '"+UserInfo.EmployeeCode+"')", true);
					dbExec.commit();
				}
			}
			
			String strBatch = ""; 
			txtBatch.setValue(_PagibigStatusMonitoring.saveLoanReturned(modelLoanReturned, dteTransDate.getDate()));

			_PagibigStatusMonitoring.displayForTaggingOfLoanReturnedFF(modelLoanReturned, rowHeaderStatusTagging, txtBatch.getValue().toString());
			scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
			
			strBatch = _PagibigStatusMonitoring.CreateNoticeReturnedFromHDMF(modelLoanReturned, "139"); 
			
			_PagibigStatusMonitoring.GenerateHDMFReturnedNotice(strBatch);
			_PagibigStatusMonitoring.GenerateHDMFReturnedNoticeTransmittal(strBatch);
			_PagibigStatusMonitoring.GenerateHDMFReturnedNoticePhilPostTransmittal(strBatch);
			
			JOptionPane.showMessageDialog(null, "Success!");
			PagibigStatusMonitoring_v2.btnState(false, false, false);

		}
		
		if(cmbStage.getSelectedIndex() == 15){
			String strBatch = ""; 
			txtBatch.setValue(_PagibigStatusMonitoring.saveLoanReturnedPostFiling(modelLoanReturned, dteTransDate.getDate()));

			_PagibigStatusMonitoring.displayForTaggingOfLoanReturnedPF(modelLoanReturned, rowHeaderStatusTagging, txtBatch.getValue().toString());
			scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
			
			JOptionPane.showMessageDialog(null, "Success!");
			PagibigStatusMonitoring_v2.btnState(false, false, false);
		}
		
		if(cmbStage.getSelectedIndex() == 16){
			try {
				txtBatch.setValue(_PagibigStatusMonitoring.saveCI(modelCITagging, dteTransDate.getDate()));
				JOptionPane.showMessageDialog(null, "Success!");
				PagibigStatusMonitoring_v2.btnState(false, false, false);
			} catch (Exception ex) {
				
				JOptionPane.showMessageDialog(null, "Something went wrong.");
				PagibigStatusMonitoring_v2.btnState(true, false, false);
			} 
			
			_PagibigStatusMonitoring.displayForTaggingOfCI(modelCITagging, rowHeaderStatusTagging, txtBatch.getValue());
		}
		
	}
	
	public void Preview(){
		//String company_logo = RequestForPayment.sql_getCompanyLogo();
		String company_logo = _PagibigStatusMonitoring.sqlCompanyLogo(hdmfMainMod.txtCoID.getValue());
		
		if(cmbStage.getSelectedIndex() == 1){
			String strTmp = "";
			
			pgUpdate del = new pgUpdate();
			strTmp = "delete from tmp_hdmf";
			del.executeUpdate(strTmp, false);
			del.commit();
			
			System.out.println("");
			System.out.println(strTmp);
			
			for (int x = 0; x < tblStatusTagging.getRowCount(); x++) {
				pgUpdate ins = new pgUpdate();
				
				String strName = FncGlobal.GetString("select entity_name from rf_entity where entity_id = '"+modelMSVS.getValueAt(x, 10)+"'");
				
				strTmp = "insert into tmp_hdmf (client_name, emp_code) values ('"+strName+"', '"+UserInfo.EmployeeCode+"')";
				ins.executeUpdate(strTmp, false);
				ins.commit();
				
				System.out.println("");
				System.out.println(strTmp);
			}
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
			mapParameters.put("02_AsOfDate", dteTransDate.getDate());
			mapParameters.put("03_CoID", hdmfMainMod.txtCoID.getText());
			mapParameters.put("04_ProID", hdmfMainMod.txtProID.getText());
			mapParameters.put("06_Project", hdmfMainMod.txtPro.getText());
			mapParameters.put("07_User",  _RealTimeDebit.GetName(UserInfo.EmployeeCode));
			mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
			mapParameters.put("09_Phase", hdmfMainMod.txtPhaseID.getText());
			mapParameters.put("11_To", dteTransDate.getDate());
			mapParameters.put("12_Boolean", true);
			FncReport.generateReport("/Reports/rpt_hdmf_msvs_status_verified.jasper", "Status of MSVS Reverification", "", mapParameters);
		}
		
		if(cmbStage.getSelectedIndex() == 2){//Loan Filed
			previewLoanFiled();
		}
		
		if(cmbStage.getSelectedIndex() == 3){//Loan Filed G to G
			previewLoanFiled_GtoG();
		}
		
		if(cmbStage.getSelectedIndex() == 4){//
			if (txtBatch.getValue()==null || txtBatch.getValue()=="null" || txtBatch.getValue().isEmpty() || txtBatch.getValue()=="") {
				JOptionPane.showMessageDialog(null, "No batch was selected.");
			} else {
				preview_noa_tagged preview_noa = new preview_noa_tagged();
				Thread thread = new Thread(preview_noa);
				thread.start();
				try {
					thread.join();
				} catch (InterruptedException e) {

				}
			}
		}
		
		if(cmbStage.getSelectedIndex() == 7){//NOA EXPIRING
			/*
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			mapParameters.put("company_name", hdmfMainMod.txtCo.getText().trim());
			mapParameters.put("proj_id", hdmfMainMod.txtProID.getValue().trim());
			mapParameters.put("phase", hdmfMainMod.txtPhaseID.getValue().trim());
			mapParameters.put("date_cut_off", dteTransDate.getDate());
			
			FncReport.generateReport("/Reports/rptPagibigStatusMonitoring_PST_NOA_Expiring.jasper", "Expiring NOA", mapParameters);
			*/
			JOptionPane.showMessageDialog(null, "Preview is not avaialble for this stage.");
		}
		
		if(cmbStage.getSelectedIndex() == 8){
			
		}
		
		if(cmbStage.getSelectedIndex() == 9){
			
		}
		
		if(cmbStage.getSelectedIndex() == 10){
			previewForApproval_TCTAnnotated_Transmittal(txtBatch.getValue());
			previewForApproval_EPEB_Transmittal(txtBatch.getValue());
		}
		 
		if(cmbStage.getSelectedIndex() == 11){
			previewForApproval_TCTAnnotated_Transmittal_GtoG(txtBatch.getValue());
			//previewForApproval_EPEB_Transmittal_GtoG(txtBatch.getValue());
		}
		
		if(cmbStage.getSelectedIndex() == 13){
			pgUpdate db = new pgUpdate();
			
			try {
				db.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", true);
				db.commit();
				
				for (int x = 0; x < modelNOASigning.getRowCount(); x++) {
					if ((Boolean) modelNOASigning.getValueAt(x, 0)) {
						db = new pgUpdate();
						db.executeUpdate("insert into tmp_hdmf (client_name, emp_code)"
								+ "values ('"+modelNOASigning.getValueAt(x, 1).toString()+"', "
								+ "'"+UserInfo.EmployeeCode+"')", true);
						db.commit();
					}
				}
				
				preview_noa_signed preview_noa = new preview_noa_signed();
				Thread thread = new Thread(preview_noa);
				thread.start();
				try {
					thread.join();
				} catch (InterruptedException e) {

				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Something went wrong.");
			}

		}
	}
		
	public void export(){
		if(cmbStage.getSelectedIndex() == 2 || cmbStage.getSelectedIndex() == 3 ){ //Loan Filed
			
			String col_names [] = {"Unit", "Name", "Date Completed", "% Constructed", "DP Term", "Pmt Stage"};
			String hidden_columns [] = {"Select","Entity ID", "Proj. ID", "PBL ID", "Seq. No"};
			
			FncGlobal.startProgress("Creating Spreadsheet");
			FncGlobal.CreateXLSFromModel(col_names, hidden_columns , modelLoanFiled, "Loan Filed Accts");
			FncExport.createDBF_FromModel(col_names, hidden_columns, modelLoanFiled, "Loan Filed DBF", "Liberation Sans");
			
			FncGlobal.stopProgress();
			
		}
	}
	
	
	public void Generate(){
		new Thread(new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating Qualified Accounts");
				
				if(cmbStage.getSelectedIndex()==1){
					System.out.println("");
					System.out.println("Display MSVS Reverified");
					
					_PagibigStatusMonitoring.displayMSVSReverified(
							hdmfMainMod.txtCoID.getValue(), 
							hdmfMainMod.txtProID.getValue(), 
							hdmfMainMod.txtPhaseID.getValue(), 
							dteTransDate.getDate(), 
							modelMSVS, 
							rowHeaderStatusTagging, false);
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				}
				
				if(cmbStage.getSelectedIndex() == 2){//Loan Filed
					_PagibigStatusMonitoring.displayPST_LoanField(hdmfMainMod.txtCoID.getValue(),hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), modelLoanFiled, rowHeaderStatusTagging);
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(150);
					tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(250);
					tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(150);
					tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(100);
					tblStatusTagging.getColumnModel().getColumn(5).setPreferredWidth(70);
					tblStatusTagging.getColumnModel().getColumn(6).setPreferredWidth(70);
					
					PagibigStatusMonitoring_v2.btnState(true, true, true);
				}
				
				if(cmbStage.getSelectedIndex() == 3){//Loan Filed G to G
					_PagibigStatusMonitoring.displayPST_LoanField_GtoG(hdmfMainMod.txtCoID.getValue(),hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), modelLoanFiled, rowHeaderStatusTagging);
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(150);
					tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(250);
					tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(150);
					tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(100);
					tblStatusTagging.getColumnModel().getColumn(5).setPreferredWidth(70);
					tblStatusTagging.getColumnModel().getColumn(6).setPreferredWidth(70);
					
					PagibigStatusMonitoring_v2.btnState(true, true, true);
				}
				
				if(cmbStage.getSelectedIndex() == 4){//NOA Released
					/*
					_PagibigStatusMonitoring.displatPST_NOA_Released(hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), modelNOAReleased, rowHeaderStatusTagging, txtBatch.getValue());
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					
					tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(250);
					tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(150);
					tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(100);
					tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(150);
					
					PagibigStatusMonitoring_v2.btnState(true, true, false);
					*/
					txtBatch.setValue("");
					
					generate_noa gen_noa = new generate_noa();
					Thread thread = new Thread(gen_noa);
					thread.start();
					try {
						thread.join();
					} catch (InterruptedException e) {

					}
				}
				
				if(cmbStage.getSelectedIndex() == 5){
					_PagibigStatusMonitoring.displayPST_WithDOASigned(hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), dteTransDate.getDate(), modelDOASigned, rowHeaderStatusTagging);
					tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(200);
					tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(150);
					tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(150);
					PagibigStatusMonitoring_v2.btnState(true, true, false);
				}
				
				if(cmbStage.getSelectedIndex() == 6){
					_PagibigStatusMonitoring.displayPQA_ForTCTAnnotation(hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), dteTransDate.getDate(), modelForTCTAnnotation, rowHeaderStatusTagging);
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(150);
					tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(200);
					tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(100);
					tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(100);
					tblStatusTagging.getColumnModel().getColumn(5).setPreferredWidth(100);
					tblStatusTagging.getColumnModel().getColumn(6).setPreferredWidth(70);
//					tblStatusTagging.getColumnModel().getColumn(7).setPreferredWidth(170);
					PagibigStatusMonitoring_v2.btnState(true, true, false);
				}
				
				if(cmbStage.getSelectedIndex() == 7){
					_PagibigStatusMonitoring.displayNOAExtension(hdmfMainMod.txtCoID.getValue(), hdmfMainMod.txtProID.getValue(), modelNOAExtension, rowHeaderStatusTagging);
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					PagibigStatusMonitoring_v2.btnState(true, true, false);
				}
				
				if(cmbStage.getSelectedIndex() == 8){
					
					_PagibigStatusMonitoring.displayPST_WithEPEB(hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), dteTransDate.getDate(), modelWithEPEB, rowHeaderStatusTagging);
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					tblStatusTagging.packAll();
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				}
				
				if(cmbStage.getSelectedIndex() == 9){
					txtBatch.setValue(null);
					_PagibigStatusMonitoring.displayPST_WithAnnotatedTCT(hdmfMainMod.txtCoID.getValue(), hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), txtBatch.getValue() ,modelWithAnnotatedTCT, rowHeaderStatusTagging);
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					tblStatusTagging.packAll();
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				}
				
				if(cmbStage.getSelectedIndex() == 10){
					txtBatch.setValue(null);
					_PagibigStatusMonitoring.displayPST_ForApproval(hdmfMainMod.txtCoID.getValue(), hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), txtBatch.getValue(), modelForPostApproval, rowHeaderStatusTagging);
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					tblStatusTagging.packAll();
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				}
				
				if(cmbStage.getSelectedIndex() == 11){
					txtBatch.setValue(null);
					_PagibigStatusMonitoring.displayPST_ForApproval_GtoG(hdmfMainMod.txtCoID.getValue(), hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), txtBatch.getValue(), modelForPostApproval, rowHeaderStatusTagging);
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					tblStatusTagging.packAll();
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				}
				
				
				if(cmbStage.getSelectedIndex() == 12){
					/*
					String strbatch = "";
					if (txtBatch.getValue()==null || txtBatch.getValue()=="null" || txtBatch.getValue()=="") {
						strbatch = "";
					} else {
						strbatch = txtBatch.getValue();
					}
					*/
					
					txtBatch.setValue("");
					_PagibigStatusMonitoring.displayForTaggingOfCOA(modelHouseInspection, rowHeaderStatusTagging, "", hdmfMainMod.txtCoID.getValue(), hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue());
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				}
				
				if(cmbStage.getSelectedIndex() == 13){
					_PagibigStatusMonitoring.displayForTaggingOfNOASigned(hdmfMainMod.txtCoID.getValue(), hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), modelNOASigning, rowHeaderStatusTagging);
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				}
				
				if(cmbStage.getSelectedIndex() == 14){
					txtBatch.setValue("");
					
					_PagibigStatusMonitoring.displayForTaggingOfLoanReturnedFF(modelLoanReturned, rowHeaderStatusTagging, txtBatch.getValue().toString());
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				}
				
				if(cmbStage.getSelectedIndex() == 15){
					txtBatch.setValue("");
					
					_PagibigStatusMonitoring.displayForTaggingOfLoanReturnedPF(modelLoanReturned, rowHeaderStatusTagging, txtBatch.getValue().toString());
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				}
				
				if(cmbStage.getSelectedIndex() == 16){
					txtBatch.setValue("");
					
					_PagibigStatusMonitoring.displayForTaggingOfCI(modelCITagging, rowHeaderStatusTagging, txtBatch.getValue().toString());
					scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
					PagibigStatusMonitoring_v2.btnState(true, false, false);
				}

				FncGlobal.stopProgress();
			}
		}).start();
	}

	@SuppressWarnings("unused")
	private void GridLookup() {
		int iCol = tblStatusTagging.getSelectedColumn();
		int iRow = tblStatusTagging.getSelectedRow();

		System.out.println("");
		System.out.println("iCol: " + iCol);
		System.out.println("iRow: " + iRow);
		
		if (iCol == 8) {
			String strDate1 = "";
			String strDate2 = "";
			_JDateChooser dteDate = new _JDateChooser(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			String message ="Set Date Received:\n";
			
			Object[] params = {message, dteDate};
			JOptionPane.showConfirmDialog(null, params, "Date Received", JOptionPane.PLAIN_MESSAGE);

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			strDate2 = sdf.format(((_JDateChooser) params[1]).getDate()).toString();
			
			System.out.println("");
			System.out.println("Output 1: " + strDate1);
			System.out.println("Output 2: " + strDate2);
			
			tblStatusTagging.setValueAt(strDate2, iRow, iCol);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		System.out.println("");
		System.out.println("Panel Index: " + cmbStage.getSelectedIndex());
		
		if (e.getSource().equals(tblStatusTagging)) {
			if (cmbStage.getSelectedIndex()==1) {
				int iCol = tblStatusTagging.getSelectedColumn();
				int iRow = tblStatusTagging.getSelectedRow();
				
				System.out.println("");
				System.out.println("iCol: " + iCol);
				System.out.println("iRow: " + iRow);
				
				int selected_row = tblStatusTagging.convertRowIndexToModel(tblStatusTagging.getSelectedRow());
				Boolean blnSel = (Boolean) tblStatusTagging.getValueAt(selected_row, 0);
				
				if (tblStatusTagging.convertRowIndexToModel(tblStatusTagging.getSelectedColumn())==8) {
					if (e.getClickCount() == 2) {
						if (blnSel) {
							System.out.println("Dumaan dito sa Double Click");
							dteApproved.setBounds((int) pnlCenter.getMousePosition().getX(), (int) pnlCenter.getMousePosition().getY(), 0, 0);
							dteApproved.getCalendarButton().doClick();
						} else {
							JOptionPane.showMessageDialog(null, "Account is not tagged. Date cannot be changed for not selected clients.");
						}
					}
				}	
			} else {
				int selected_row = tblStatusTagging.convertRowIndexToModel(tblStatusTagging.getSelectedRow());
				
				Boolean isSelected2 = (Boolean) modelWithEPEB.getValueAt(selected_row, 0);
				System.out.println("Dumaan dito sa MouseListener ng tblStatusTagging");
				
				if (isSelected2) {
					System.out.printf("Display value of selected column: %s%n", tblStatusTagging.getSelectedColumn());
					
					if (tblStatusTagging.convertRowIndexToModel(tblStatusTagging.getSelectedColumn()) == 4) {
						if (e.getClickCount() == 2) {
							System.out.println("Dumaan dito sa Double Click");
							dteEPEB_Date.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
							dteEPEB_Date.getCalendarButton().doClick();
						}
					}
				}	
			}
		}
		
		/*
		if (cmbStage.getSelectedIndex()==7) {edapostol
			System.out.println("");
			System.out.println("Click: " + e.getClickCount());
			
			if ((e.getClickCount() >= 1)) {
				GridLookup();
			}
		} else {
			if(e.getSource().equals(tblStatusTagging)){
				
				int selected_row = tblStatusTagging.convertRowIndexToModel(tblStatusTagging.getSelectedRow());
				
				Boolean isSelected2 = (Boolean) modelWithEPEB.getValueAt(selected_row, 0);
				System.out.println("Dumaan dito sa MouseListener ng tblStatusTagging");
				
				if(isSelected2){
					System.out.printf("Display value of selected column: %s%n", tblStatusTagging.getSelectedColumn());
					if (tblStatusTagging.convertRowIndexToModel(tblStatusTagging.getSelectedColumn()) == 4) {
						if (e.getClickCount() == 2) {
							System.out.println("Dumaan dito sa Double Click");
							dteEPEB_Date.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
							dteEPEB_Date.getCalendarButton().doClick();
						}
					}
				}
			}	
		}
		*/
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
	
	}

	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
	
	}
	
	@SuppressWarnings("unused")
	private void previewMSVSReverified(){
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
		mapParameters.put("02_AsOfDate", dteTransDate.getDate());
		mapParameters.put("03_CoID", hdmfMainMod.txtCoID.getText());
		mapParameters.put("04_ProID", hdmfMainMod.txtProID.getText());
		mapParameters.put("06_Project", hdmfMainMod.txtPro.getText());
		mapParameters.put("07_User",  _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("09_Phase", hdmfMainMod.txtPhaseID.getText());
		mapParameters.put("11_To", dteTransDate.getDate());
		mapParameters.put("12_Boolean", true);
		FncReport.generateReport("/Reports/rpt_hdmf_msvs_status_verified.jasper", "Status of MSVS Reverification", "", mapParameters);
	}
	
	private void preview_noa() {
		String strBatch = "";
		if (txtBatch.getValue()==null || txtBatch.getValue()=="null" || txtBatch.getValue()=="") {
			strBatch = "";
		} else {
			strBatch = txtBatch.getValue();
		}
		
		_PagibigStatusMonitoring.displatPST_NOA_Released(hdmfMainMod.txtProID.getValue(), hdmfMainMod.txtPhaseID.getValue(), modelNOAReleased, rowHeaderStatusTagging, strBatch);
		scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblStatusTagging.getRowCount())));
		
		tblStatusTagging.getColumnModel().getColumn(1).setPreferredWidth(250);
		tblStatusTagging.getColumnModel().getColumn(2).setPreferredWidth(150);
		tblStatusTagging.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblStatusTagging.getColumnModel().getColumn(4).setPreferredWidth(150);
		
		PagibigStatusMonitoring_v2.btnState(true, true, false);
	}
	
	private void print_noa() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String strDateParam = "";
		String strDateFilter = "";
		
		strDateParam = df.format(FncGlobal.getDateToday());
		strDateParam = "As Of: " + strDateParam;
		strDateFilter = FncGlobal.getDateSQL();
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", hdmfMainMod.txtCoID.getValue());
		mapParameters.put("co_name", hdmfMainMod.txtCo.getText());
		mapParameters.put("dateFrom", strDateFilter);
		mapParameters.put("dateParam", strDateParam);
		mapParameters.put("project_id", hdmfMainMod.txtProID.getValue());
		mapParameters.put("user_name", GetName(UserInfo.EmployeeCode));
		mapParameters.put("project", hdmfMainMod.txtPro.getText());
		mapParameters.put("status", "35");
		mapParameters.put("phase", "");
		mapParameters.put("batch", txtBatch.getValue());
		mapParameters.put("user_id", UserInfo.EmployeeCode);
		FncReport.generateReport("/Reports/rpt_hdmf_noa_transmittal.jasper", "Notice of Approval(NOA) Transmittal", "", mapParameters);
	}
	
	private void print_noa_signed() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String strDateParam = "";
		String strDateFilter = "";
		
		strDateParam = df.format(FncGlobal.getDateToday());
		strDateParam = "As Of: " + strDateParam;
		strDateFilter = FncGlobal.getDateSQL();
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", hdmfMainMod.txtCoID.getValue());
		mapParameters.put("co_name", hdmfMainMod.txtCo.getText());
		mapParameters.put("dateFrom", strDateFilter);
		mapParameters.put("dateParam", strDateParam);
		mapParameters.put("project_id", hdmfMainMod.txtProID.getValue());
		mapParameters.put("user_name", GetName(UserInfo.EmployeeCode));
		mapParameters.put("project", hdmfMainMod.txtPro.getText());
		mapParameters.put("status", "49");
		mapParameters.put("phase", "");
		mapParameters.put("user_id", UserInfo.EmployeeCode);
		FncReport.generateReport("/Reports/rpt_hdmf_noasigned_transmittal.jasper", "NOA Signed Transmittal", "", mapParameters);
	}

	private class generate_noa extends Thread {

		public void run() {
			FncGlobal.startProgress("Generating NOA Qualified List..");
			preview_noa();
	        FncGlobal.stopProgress();
		}

	}
	
	private class preview_noa_tagged extends Thread {

		public void run() {
			FncGlobal.startProgress("Generating NOA Report..");
			print_noa();
	        FncGlobal.stopProgress();
		}

	}
	
	private class preview_noa_signed extends Thread {

		public void run() {
			FncGlobal.startProgress("Generating NOA Signed Report..");
			print_noa_signed();
	        FncGlobal.stopProgress();
		}

	}
	
	private String sqlBatch(String byrstatus_id){
		return "SELECT trans_no as \"Batch No.\", \n"+
			   "actual_date::DATE as \"Actual Date\" \n"+
			   "FROM rf_buyer_status \n"+
			   "WHERE byrstatus_id = '"+byrstatus_id+"' \n"+
			   "AND status_id = 'A' \n"+
			   "GROUP BY trans_no, actual_date::DATE";
	}
	
	public static String GetName(String emp_code){
		String entityid = "";

		String SQL = "SELECT B.entity_name\n" + 
		"FROM em_employee A\n" + 
		"INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + 
		"WHERE a.emp_code = '"+emp_code+"'";
		
		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			entityid = (String) sqlExec.getResult()[0][0];
		}else{
			entityid = "";
		}

		return entityid;
	}
}
