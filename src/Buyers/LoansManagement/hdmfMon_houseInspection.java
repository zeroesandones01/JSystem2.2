package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import interfaces._GUI;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import tablemodel.model_hdmf_postInspection;
import tablemodel.model_hdmf_postInspection_batch;
import tablemodel.model_hdmf_preInspection;
import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTabbedPane;
import components._JTableMain;

public class hdmfMon_houseInspection extends JXPanel implements _GUI, MouseListener {

	private static final long serialVersionUID = 6436065544864798263L;
	private PagibigStatusMonitoring_v2 hdmfMainMod;
	static Dimension size = new Dimension(738, 351);

	/***	Table Declarations		***/
	private JScrollPane scrollPreInsp;
	public static JList rowPreInsp;
	private _JTableMain tblPreInsp;
	private model_hdmf_preInspection modelPreInsp;
	
	private JScrollPane scrollPostInsp;
	public static JList rowPostInsp;
	private _JTableMain tblPostInsp;
	private model_hdmf_postInspection modelPostInsp;
	
	private JScrollPane scrollInspBatch;
	public static JList rowInspBatch;
	private _JTableMain tblInspBatch;
	private model_hdmf_postInspection_batch modelInspBatch;
	/***	Table Declarations		***/
	
	private JLabel lblFrom;
	
	private _JLookup txtBat;
	private _JLookup txtClientID;
	private _JLookup txtBat_2;
	
	private static _JDateChooser dteFrom;
	private static _JDateChooser dteFrom_2;
	
	private _JTabbedPane tabInsp;

	private JXPanel panReady;
	private JXPanel panInspPerAcct;
	private JXPanel panInspPerBatch;

	private JLabel lblClient;
	private JLabel lblDate;
	private JLabel lblStatus;
	private JLabel lblRep;
	private JLabel lblRemarks;
	
	private JTextField txtUnit;
	private JTextField txtClient;
	private JTextField txtRep;
	private JTextField txtRemarks;
	private String global_pbl = "";
	
	private _JDateChooser dteDate;
	
	private JComboBox cboStatus;
	private JComboBox cboStatus_2;
	
	private JButton btnAdd;
	private JButton btnDel;
	private JButton btnSave;
	private JButton btnCancel;
	
	private JButton btnAdd_2;
	private JButton btnApply;
	private JButton btnSave_2;
	private JButton btnCancel_2;
	
	private Integer intPerAcctControlState = 1;
	private Integer intPerBatControlState = 1;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	Boolean blnRet = false;
	
	public hdmfMon_houseInspection(PagibigStatusMonitoring_v2 psm) {
		this.hdmfMainMod = psm;
		initGUI();
	}

	public hdmfMon_houseInspection(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public hdmfMon_houseInspection(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public hdmfMon_houseInspection(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			this.add(panMain, BorderLayout.CENTER);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					tabInsp = new _JTabbedPane();
					panCenter.add(tabInsp, BorderLayout.CENTER);
					tabInsp.setBorder(new EmptyBorder(5, 5, 5, 5));
					{
						CreateReadyTab();
						CreateInspPerAcctTab();
						CreateInspPerBatchTab();
						
						tabInsp.add("Accounts Ready for HDMF Inspection", panReady);
						tabInsp.add("Inspection Status Per Account", panInspPerAcct);
						tabInsp.add("Inspection Status Per Batch", panInspPerBatch);
					}
					tabInsp.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent arg0) {
							if (tabInsp.getTitleAt(tabInsp.getSelectedIndex()).equals("Accounts Ready for HDMF Inspection")) {
								hdmfMainMod.CtrlLock_1(hdmfMainMod.intInsp);
							} else if (tabInsp.getTitleAt(tabInsp.getSelectedIndex()).equals("Inspection Status Per Account")) {
								hdmfMainMod.CtrlLock_1(4);
								subCtrl(intPerAcctControlState);
							} else if (tabInsp.getTitleAt(tabInsp.getSelectedIndex()).equals("Inspection Status Per Batch")) {
								hdmfMainMod.CtrlLock_1(4);
								LocalCtrl_2(intPerBatControlState);
							}
						}
					});
				}
			}
		}
	}
	
	private void CreateReadyTab() {
		panReady = new JXPanel(new BorderLayout(5, 5));
		panReady.setOpaque(isOpaque());
		{
			JXPanel panPage = new JXPanel(new GridLayout(1, 2, 5, 5));
			panReady.add(panPage, BorderLayout.PAGE_START);
			panPage.setPreferredSize(new Dimension(0, 60));
			{
				{
					JXPanel panDate = new JXPanel(new BorderLayout(5, 5));
					panPage.add(panDate, BorderLayout.LINE_START);
					panDate.setPreferredSize(new Dimension(350, 0));
					panDate.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel panDate1 = new JXPanel(new BorderLayout(5, 5));
						panDate.add(panDate1, BorderLayout.CENTER);
						{
							lblFrom = new JLabel("Inspection Date");
							panDate1.add(lblFrom, BorderLayout.LINE_START);
							lblFrom.setHorizontalAlignment(JTextField.LEADING);
							lblFrom.setPreferredSize(new Dimension(150, 0));
						}
						{
							dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							panDate1.add(dteFrom);
							dteFrom.getCalendarButton().setVisible(true);
							dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteFrom.setEnabled(false);
						}
					}
				}
				{
					JXPanel panBatch = new JXPanel(new BorderLayout(5, 5));
					panPage.add(panBatch, BorderLayout.CENTER);
					panBatch.setBorder(JTBorderFactory.createTitleBorder("Batch No.", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						txtBat = new _JLookup();
						panBatch.add(txtBat, BorderLayout.CENTER);
						txtBat.setReturnColumn(0);
						txtBat.setLookupSQL(_PagibigStatusMonitoring.SQL_BATCH("Inspection"));
						txtBat.setHorizontalAlignment(JTextField.CENTER);
						txtBat.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data!=null) {
									DisplayPosted();
									hdmfMainMod.CtrlLock_1(3);
									hdmfMainMod.intInsp = 3;
								}
							}
						});
					}
				}
			}
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panReady.add(panCenter, BorderLayout.CENTER);
			{
				scrollPreInsp = new JScrollPane();
				panReady.add(scrollPreInsp, BorderLayout.CENTER);
				scrollPreInsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPreInsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				{
					modelPreInsp = new model_hdmf_preInspection();
					tblPreInsp = new _JTableMain(modelPreInsp);
					
					rowPreInsp = tblPreInsp.getRowHeader();
					scrollPreInsp.setViewportView(tblPreInsp);

					tblPreInsp.getColumnModel().getColumn(0).setPreferredWidth(250);
					tblPreInsp.getColumnModel().getColumn(1).setPreferredWidth(50);
					tblPreInsp.getColumnModel().getColumn(2).setPreferredWidth(60);
					tblPreInsp.getColumnModel().getColumn(3).setPreferredWidth(60);
					tblPreInsp.getColumnModel().getColumn(4).setPreferredWidth(60);
					
					tblPreInsp.getColumnModel().getColumn(5).setPreferredWidth(100);
					tblPreInsp.getColumnModel().getColumn(6).setPreferredWidth(100);
					tblPreInsp.getColumnModel().getColumn(7).setPreferredWidth(75);
					
					tblPreInsp.getColumnModel().getColumn(8).setPreferredWidth(100);
					tblPreInsp.getColumnModel().getColumn(9).setPreferredWidth(100);
					
					tblPreInsp.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(sdf));
					tblPreInsp.getColumnModel().getColumn(6).setCellRenderer(new DateRenderer(sdf));
					tblPreInsp.getColumnModel().getColumn(8).setCellRenderer(new DateRenderer(sdf));				
					tblPreInsp.getColumnModel().getColumn(9).setCellRenderer(new DateRenderer(sdf));
					
					tblPreInsp.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");
//					tblPreInsp.getTableHeader().setEnabled(false);
				}
				{
					rowPreInsp = tblPreInsp.getRowHeader();
					rowPreInsp.setModel(new DefaultListModel());
					scrollPreInsp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
				}
			}
		}
	}
	
	private void CreateInspPerAcctTab() {
		panInspPerAcct = new JXPanel(new BorderLayout(5, 5));
		panInspPerAcct.setOpaque(isOpaque());
		{
			JXPanel panPage = new JXPanel(new GridLayout(1, 2, 5, 5));
			panInspPerAcct.add(panPage, BorderLayout.PAGE_START);
			panPage.setPreferredSize(new Dimension(0, 60));
			{
				JXPanel panClient = new JXPanel(new BorderLayout(5, 5));
				panPage.add(panClient, BorderLayout.CENTER);
				panClient.setPreferredSize(new Dimension(450, 0));
				panClient.setBorder(JTBorderFactory.createTitleBorder("Client Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					JXPanel panLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
					panClient.add(panLabel, BorderLayout.LINE_START);
					panLabel.setPreferredSize(new Dimension(200, 0));
					{
						lblClient = new JLabel("Client");
						panLabel.add(lblClient, BorderLayout.CENTER);
						lblClient.setHorizontalAlignment(JTextField.LEADING);
					}
					{
						txtClientID = new _JLookup();
						panLabel.add(txtClientID, BorderLayout.CENTER);
						txtClientID.setReturnColumn(2);
						txtClientID.setLookupSQL(_PagibigStatusMonitoring.CLIENT());
						txtClientID.setHorizontalAlignment(JTextField.CENTER);
						txtClientID.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								
								if (data!=null) {
									txtClient.setText(data[1].toString());
									txtUnit.setText(data[0].toString());
									global_pbl = (data[3].toString());
									FillInsCombo();
									displayClient();
									
									ClearBox();
									LocalCtrl(1);
								}
							}
						});
					}
					JXPanel panBox = new JXPanel(new BorderLayout(5, 5));
					panClient.add(panBox, BorderLayout.CENTER);
					{
						txtClient = new JTextField("");
						panBox.add(txtClient, BorderLayout.CENTER);
						txtClient.setHorizontalAlignment(JTextField.CENTER);
						txtClient.setEditable(false);
					}
					{
						txtUnit = new JTextField("");
						panBox.add(txtUnit, BorderLayout.LINE_END);
						txtUnit.setPreferredSize(new Dimension(200, 0));
						txtUnit.setHorizontalAlignment(JTextField.CENTER);
						txtUnit.setEditable(false);
					}
				}
			}
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panInspPerAcct.add(panCenter, BorderLayout.CENTER);
			panCenter.setBorder(JTBorderFactory.createTitleBorder("Inspection Status", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				JXPanel panDet = new JXPanel(new BorderLayout(5, 5));
				panCenter.add(panDet, BorderLayout.CENTER);
				{
					JXPanel panDetDiv1 = new JXPanel(new GridLayout(3, 1, 5, 5));
					panDet.add(panDetDiv1, BorderLayout.PAGE_START);
					panDetDiv1.setPreferredSize(new Dimension(0, 90));
					{
						JXPanel panFirst = new JXPanel(new GridLayout(1, 2, 5, 5));
						panDetDiv1.add(panFirst, BorderLayout.CENTER);
						{
							JXPanel panDateInsp = new JXPanel(new BorderLayout(5, 5));
							panFirst.add(panDateInsp, BorderLayout.CENTER);
							{
								lblDate = new JLabel("Insp. Date");
								panDateInsp.add(lblDate, BorderLayout.LINE_START);
								lblDate.setPreferredSize(new Dimension(100, 0));
							}
							{
								dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panDateInsp.add(dteDate, BorderLayout.CENTER);
								dteDate.getCalendarButton().setVisible(true);
								dteDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								dteDate.setEnabled(false);
							}
							JXPanel panStat = new JXPanel(new BorderLayout(5, 5));
							panFirst.add(panStat, BorderLayout.CENTER);
							{
								lblStatus = new JLabel("Status");
								panStat.add(lblStatus, BorderLayout.LINE_START);
								lblStatus.setPreferredSize(new Dimension(100, 0));
								lblStatus.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								cboStatus = new JComboBox();
								panStat.add(cboStatus, BorderLayout.CENTER);
								cboStatus.setEnabled(false);
							}
							FillInsCombo();
							cboStatus.setSelectedIndex(0);
						}
						JXPanel panSecond = new JXPanel(new BorderLayout(5, 5));
						panDetDiv1.add(panSecond, BorderLayout.CENTER);
						{
							lblRep = new JLabel("HDMF Rep.");
							panSecond.add(lblRep, BorderLayout.LINE_START);
							lblRep.setPreferredSize(new Dimension(100, 0));
						}
						{
							txtRep = new JXTextField();
							panSecond.add(txtRep, BorderLayout.CENTER);
							txtRep.setHorizontalAlignment(JTextField.CENTER);
							txtRep.setEditable(false);
						}
						JXPanel panThird = new JXPanel(new BorderLayout(5, 5));
						panDetDiv1.add(panThird, BorderLayout.CENTER);
						{
							lblRemarks = new JLabel("Remarks");
							panThird.add(lblRemarks, BorderLayout.LINE_START);
							lblRemarks.setVerticalAlignment(JTextField.TOP);
							lblRemarks.setPreferredSize(new Dimension(100, 0));
						}
						{
							txtRemarks = new JTextField();
							panThird.add(txtRemarks, BorderLayout.CENTER);
							txtRemarks.setHorizontalAlignment(JTextField.CENTER);
							txtRemarks.setEditable(false);
						}
					}
					JXPanel panDetDiv2 = new JXPanel(new BorderLayout(5, 5));
					panDet.add(panDetDiv2, BorderLayout.CENTER);
					{
						JXPanel panPostInsp = new JXPanel(new BorderLayout(5, 5));
						panDetDiv2.add(panPostInsp, BorderLayout.CENTER);
						{
							scrollPostInsp = new JScrollPane();
							panPostInsp.add(scrollPostInsp, BorderLayout.CENTER);
							scrollPostInsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
							scrollPostInsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							{
								modelPostInsp = new model_hdmf_postInspection();
								tblPostInsp = new _JTableMain(modelPostInsp);
								
								rowPostInsp = tblPostInsp.getRowHeader();
								scrollPostInsp.setViewportView(tblPostInsp);
								
								tblPostInsp.getColumnModel().getColumn(5).setPreferredWidth(100);
								tblPostInsp.getColumnModel().getColumn(6).setPreferredWidth(150);
								tblPostInsp.getColumnModel().getColumn(7).setPreferredWidth(200);
								tblPostInsp.getColumnModel().getColumn(8).setPreferredWidth(250);
								
								tblPostInsp.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(sdf));
								
								tblPostInsp.hideColumns("Name", "Tag", "Phase", "Block", "Lot");
//								tblPostInsp.getTableHeader().setEnabled(false);
							}
							{
								rowPostInsp = tblPostInsp.getRowHeader();
								rowPreInsp.setModel(new DefaultListModel());
								scrollPostInsp.setRowHeaderView(rowPostInsp);
								scrollPostInsp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
							}
						}
						JXPanel panPostBut = new JXPanel(new GridLayout(4, 1, 5, 5));
						panDetDiv2.add(panPostBut, BorderLayout.LINE_END);
						panPostBut.setPreferredSize(new Dimension(150, 0));
						{
							btnAdd = new JButton("Add");
							panPostBut.add(btnAdd, BorderLayout.CENTER);
							btnAdd.setEnabled(false);
							btnAdd.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent act) {
									ClearBox();
									EnableBox(true);
									subCtrl(2);
									intPerAcctControlState = 2;
								}
							});
						}
						{
							btnDel = new JButton("Delete");
							panPostBut.add(btnDel, BorderLayout.CENTER);
							btnDel.setEnabled(false);
							btnDel.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent act) {
									if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this detail?", "Confirm", 
											JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
										try {
											String strUnit = GetValue("select pbl_id from mf_unit_info where trim(description) = '"+txtUnit.getText()+"'");
											Integer intRow = tblPostInsp.convertRowIndexToModel(tblPostInsp.getSelectedRow());
											String strStat = GetValue("select status_id from mf_house_inspection_status where trim(status_desc) = trim('"+modelPostInsp.getValueAt(intRow, 6).toString()+"')");
											
											System.out.println("");
											System.out.println("strUnit: " + strUnit);
											System.out.println("intRow: " + intRow);
											System.out.println("strStat: " + strStat);
										
											pgUpdate dbRev = new pgUpdate();
											String strSQL = "delete from rf_hdmf_insp_detail where entity_id = '"+txtClientID.getText()+"' and pbl_id = '"+strUnit+"' and insp_status_id = '"+strStat+"'";
											
											dbRev.executeUpdate(strSQL, true);
											dbRev.commit();
											
											displayClient();
											JOptionPane.showMessageDialog(null, "Status successfully deleted!");	
										} catch (IndexOutOfBoundsException idx) {
											JOptionPane.showMessageDialog(null, "No row was selected.");
										}
									}
									FillInsCombo();
								}
							});
						}
						{
							btnSave = new JButton("Save");
							panPostBut.add(btnSave, BorderLayout.CENTER);
							btnSave.setEnabled(false);
							btnSave.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									if (JOptionPane.showConfirmDialog(null, "Are you sure you want to save these details?", "Confirm", 
											JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
										
										String strStat = "";
										strStat = cboStatus.getSelectedItem().toString().substring(0, 2);
										String strUnit = GetValue("select pbl_id from mf_unit_info where trim(description) = '"+txtUnit.getText()+"' and pbl_id = '"+global_pbl+"'");
										
										pgUpdate dbRev = new pgUpdate();
										String strSQL = "insert into rf_hdmf_insp_detail (insp_detail_rec_id, entity_id, pbl_id, insp_status_id, trans_date, \n" +
												"created_by, date_created, hdmf_rep, remarks, status_id) values ("+_PagibigStatusMonitoring.GetRec("Inspected")+", '"+txtClientID.getValue()+"',  \n" +
												"'"+strUnit+"', '"+strStat+"', '"+dteDate.getText()+"', \n" +
												"'"+UserInfo.EmployeeCode+"', CURRENT_DATE, '"+txtRep.getText()+"', '"+txtRemarks.getText()+"', 'A')";
										
										dbRev.executeUpdate(strSQL, true);
										dbRev.commit();
										
										displayClient();
										JOptionPane.showMessageDialog(null, "Saving successful!");
									}
									FillInsCombo();
									ClearBox();
									EnableBox(false);
									subCtrl(1);
									intPerAcctControlState = 1;
								}
							});
						}
						{
							btnCancel = new JButton("Cancel");
							panPostBut.add(btnCancel, BorderLayout.CENTER);
							btnCancel.setEnabled(false);
							btnCancel.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									ClearBox();
									EnableBox(false);
									subCtrl(1);	
									intPerAcctControlState = 1;
								}
							});
						}
					}
				}
			}
		}
	}
	
	private void CreateInspPerBatchTab() {
		panInspPerBatch = new JXPanel(new BorderLayout(5, 5));
		panInspPerBatch.setOpaque(isOpaque());
		{
			JXPanel panPage = new JXPanel(new GridLayout(1, 3, 5, 5));
			panInspPerBatch.add(panPage, BorderLayout.PAGE_START);
			panPage.setPreferredSize(new Dimension(0, 60));
			{
				JXPanel panBatch = new JXPanel(new BorderLayout(5, 5));
				panPage.add(panBatch, BorderLayout.CENTER);
				panBatch.setBorder(JTBorderFactory.createTitleBorder("Batch No.", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					txtBat_2 = new _JLookup();
					panBatch.add(txtBat_2, BorderLayout.CENTER);
					txtBat_2.setReturnColumn(0);
					txtBat_2.setLookupSQL(_PagibigStatusMonitoring.SQL_BATCH("Inspection"));
					txtBat_2.setHorizontalAlignment(JTextField.CENTER);
					txtBat_2.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data!=null) {
								DisplayBatch(true);
								hdmfMainMod.CtrlLock_1(4);
								LocalCtrl_2(1);
								intPerBatControlState = 1;
							}
						}
					});
				}
				JXPanel panDate = new JXPanel(new BorderLayout(5, 5));
				panPage.add(panDate, BorderLayout.CENTER);
				panDate.setBorder(JTBorderFactory.createTitleBorder("Inspection Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					dteFrom_2 = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
					panDate.add(dteFrom_2, BorderLayout.CENTER);
					dteFrom_2.getCalendarButton().setVisible(true);
					dteFrom_2.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteFrom_2.setEnabled(false);
				}
				JXPanel panStatus = new JXPanel(new BorderLayout(5, 5));
				panPage.add(panStatus, BorderLayout.CENTER);
				panStatus.setBorder(JTBorderFactory.createTitleBorder("Status", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					cboStatus_2 = new JComboBox();
					panStatus.add(cboStatus_2, BorderLayout.CENTER);
					cboStatus_2.setEnabled(false);
					FillInsCombo_2();
				}
			}
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panInspPerBatch.add(panCenter, BorderLayout.CENTER);
			{
				scrollInspBatch = new JScrollPane();
				panCenter.add(scrollInspBatch, BorderLayout.CENTER);
				scrollInspBatch.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollInspBatch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				{
					modelInspBatch = new model_hdmf_postInspection_batch();
					tblInspBatch = new _JTableMain(modelInspBatch);
					
					rowInspBatch = tblInspBatch.getRowHeader();
					scrollInspBatch.setViewportView(tblInspBatch);

					tblInspBatch.getColumnModel().getColumn(0).setPreferredWidth(250);	//	Name
					tblInspBatch.getColumnModel().getColumn(1).setPreferredWidth(35);	//	Tag
					tblInspBatch.getColumnModel().getColumn(2).setPreferredWidth(114);	//	Unit
					tblInspBatch.getColumnModel().getColumn(3).setPreferredWidth(150);	//	Status
					tblInspBatch.getColumnModel().getColumn(4).setPreferredWidth(150);	//	New Status
					tblInspBatch.getColumnModel().getColumn(5).setPreferredWidth(150);	//	Date
					tblInspBatch.getColumnModel().getColumn(6).setPreferredWidth(250);	//	Representative
					tblInspBatch.getColumnModel().getColumn(7).setPreferredWidth(300);	//	Remarks
					
					tblInspBatch.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");
//					tblInspBatch.getTableHeader().setEnabled(false);
					tblInspBatch.addMouseListener(this);
				}
				{
					rowInspBatch = tblInspBatch.getRowHeader();
					rowInspBatch.setModel(new DefaultListModel());
					scrollInspBatch.setRowHeaderView(rowInspBatch);
					scrollInspBatch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
				}
			}
			JXPanel panEnd = new JXPanel(new GridLayout(1, 2, 5, 5));
			panInspPerBatch.add(panEnd, BorderLayout.PAGE_END);
			panEnd.setPreferredSize(new Dimension(0, 30));
			{
				btnAdd_2 = new JButton("Add");
				panEnd.add(btnAdd_2, BorderLayout.CENTER);
				btnAdd_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DisplayBatch(false);
						LocalCtrl_2(2);
						intPerBatControlState = 2;
					}
				});
				btnAdd_2.setEnabled(false);
			}
			{
				btnApply = new JButton("Apply");
				panEnd.add(btnApply, BorderLayout.CENTER);
				btnApply.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						for (int x = 0; x < tblInspBatch.getRowCount(); x++) {
							if ((Boolean) modelInspBatch.getValueAt(x, 1)) {
								modelInspBatch.setValueAt(cboStatus_2.getSelectedItem().toString().substring(5, cboStatus_2.getSelectedItem().toString().length()), x, 4);
								modelInspBatch.setValueAt(dteFrom_2.getDate(), x, 5);
							}
						}
					}
				});
				btnApply.setEnabled(false);
				btnApply.setToolTipText("Pressing the Apply button propagates the selected values of the date and status all throughout the grid contents.");
			}
			{
				btnSave_2 = new JButton("Save");
				panEnd.add(btnSave_2, BorderLayout.CENTER);
				btnSave_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (JOptionPane.showConfirmDialog(null, "Are you sure you want to save these details?", "Confirm", 
							JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
							
							System.out.println("");
							System.out.println("I went here!");
							
							for (int x = 0; x < tblInspBatch.getRowCount(); x++) {
								if ((Boolean) modelInspBatch.getValueAt(x, 1) && !(Boolean) modelInspBatch.getValueAt(x, 4).equals("")) {
									pgUpdate dbRev = new pgUpdate();
									
									System.out.println("");
									System.out.println("New Status: " + GetValue("select status_id from mf_house_inspection_status where status_desc = '"+modelInspBatch.getValueAt(x, 4)+"'"));
									
									String strSQL = "insert into rf_hdmf_insp_detail (insp_detail_rec_id, entity_id, pbl_id, insp_status_id, trans_date, \n" +
											"created_by, date_created, hdmf_rep, remarks, status_id) values \n" +
											"("+_PagibigStatusMonitoring.GetRec("Inspected")+", \n" + 
											"'"+modelInspBatch.getValueAt(x, 8)+"', \n" +
											"'"+modelInspBatch.getValueAt(x, 10)+"', \n" + 
											"'"+GetValue("select status_id from mf_house_inspection_status where status_desc = '"+modelInspBatch.getValueAt(x, 4)+"'")+"', \n" +
											"'"+modelInspBatch.getValueAt(x, 5)+"', \n" +
											"'"+UserInfo.EmployeeCode+"', \n" +
											"CURRENT_DATE, \n" +
											"'"+modelInspBatch.getValueAt(x, 6)+"', \n" +
											"'"+modelInspBatch.getValueAt(x, 7)+"', \n" + 
											"'A')";
									
									dbRev.executeUpdate(strSQL, true);
									dbRev.commit();
								}
							}
						}
						JOptionPane.showMessageDialog(null, "Saving successful!");
						LocalCtrl_2(1);
						intPerBatControlState = 1;
						DisplayBatch(true);
					}
				});
				btnSave_2.setEnabled(false);
			}
			{
				btnCancel_2 = new JButton("Cancel");
				panEnd.add(btnCancel_2, BorderLayout.CENTER);
				btnCancel_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						LocalCtrl_2(1);
						intPerBatControlState = 1;
						DisplayBatch(true);
					}
				});
				btnCancel_2.setEnabled(false);
			}
		}
	}
	
	private void FillInsCombo() {
		String strID = txtClientID.getText();
//		String strUnit = GetValue("select pbl_id from mf_unit_info where trim(description) = '"+txtUnit.getText()+"'");
		String strUnit = global_pbl;
		pgSelect dbCbo = new pgSelect();
	
		System.out.println("sql:" + strUnit);

		/*
		String SQL = "select trim(status_id) || ' - ' || status_desc \n" +
				"from mf_house_inspection_status x \n" +
				"where x.status_id not in (select insp_status_id from rf_hdmf_insp_detail where entity_id = '"+strID+"' and status_id = 'A' and insp_status_id != '02')";
		*/
		
		String SQL = "select trim(status_id) || ' - ' || status_desc as status \n" +
				"from mf_house_inspection_status x \n" +
				"where (case when (select count(*) from rf_hdmf_insp_detail where insp_status_id = '03' and entity_id = '"+strID+"' and pbl_id = '"+strUnit+"' and status_id = 'A') > 0 \n" +
				"then x.status_id not in (select insp_status_id from rf_hdmf_insp_detail where entity_id = '"+strID+"' and pbl_id = '"+strUnit+"' and status_id = 'A') else x.status_id \n" +
				"not in (select insp_status_id from rf_hdmf_insp_detail where entity_id = '"+strID+"' and pbl_id = '"+strUnit+"' and status_id = 'A' and insp_status_id not in ('02', '04')) end)";
		
		System.out.println("sql:" + SQL);
		dbCbo.select(SQL);
		cboStatus.removeAllItems();
		if (dbCbo.isNotNull()) {
			for (int x = 0; x < dbCbo.getRowCount(); x++) {
				cboStatus.addItem((String) dbCbo.getResult()[x][0]);
			}
		}
	}
	
	private void FillInsCombo_2() {
		pgSelect dbCbo = new pgSelect();

		String SQL = "select trim(status_id) || ' - ' || status_desc as status from mf_house_inspection_status order by status_id";
		
		dbCbo.select(SQL);
		cboStatus_2.removeAllItems();
		if (dbCbo.isNotNull()) {
			for (int x = 0; x < dbCbo.getRowCount(); x++) {
				cboStatus_2.addItem((String) dbCbo.getResult()[x][0]);
			}
		}
	}
	
	public Boolean Generate() {
		new Thread(new Runnable() {
			public void run() {
				FncTables.clearTable(modelPreInsp);
				DefaultListModel listModel = new DefaultListModel();
				rowPreInsp.setModel(listModel); 
				
//				String SQL = "select * from view_hdmf_pre_inspection('') order by name";
				String SQL = "select * from view_hdmf_pre_inspection_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"') order by name";
				
				System.out.println("");
				System.out.println(SQL);
				
				FncGlobal.startProgress("Accounts Ready for Inspection");
				hdmfMainMod.CtrlLock_1(4);
				
				pgSelect db = new pgSelect();
				db.select(SQL);
				if (db.isNotNull()){
					for (int x = 0; x < db.getRowCount(); x++) {
						modelPreInsp.addRow(db.getResult()[x]);
						listModel.addElement(modelPreInsp.getRowCount());
					}
					blnRet = true;
				} else {
					JOptionPane.showMessageDialog(null, "No records were returned for posting.");
					blnRet = false;
				};
				
				FncGlobal.stopProgress();
				hdmfMainMod.CtrlLock_1(2);
				hdmfMainMod.intInsp = 2;
				txtBat.setEnabled(true);
				txtBat.setValue("");
			}
		}).start();
		return blnRet;
	}
	
	public Boolean DisplayPosted() {
		Boolean blnRet = false;
		FncTables.clearTable(modelPreInsp);
		DefaultListModel listModel = new DefaultListModel();
		rowPreInsp.setModel(listModel); 
		
		String SQL = "select * from view_hdmf_pre_inspection('"+txtBat.getText()+"') order by name";
		
		System.out.println("");
		System.out.println(SQL);
		
		FncGlobal.startProgress("Accounts Ready for Inspection");
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelPreInsp.addRow(db.getResult()[x]);
				listModel.addElement(modelPreInsp.getRowCount());
			}
			blnRet = true;
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned for posting.");
			blnRet = false;
		};
		
		FncGlobal.stopProgress();
		return blnRet;
	}
	
	public void Post() {
		String strBat = "";
		String strDate = "";
		
		FncGlobal.startProgress("Posting");
		
		Boolean blnSel = false;
		for (int x = 0; x < modelPreInsp.getRowCount(); x++) {
			if ((Boolean) modelPreInsp.getValueAt(x, 1)) {
				blnSel = true;
			}
		}
		
		if (blnSel) {
			SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
			strDate = (String) sdfTo.format(dteDate.getDate());
			
			if (JOptionPane.showConfirmDialog(null, strDate + " will be set as inspection date. Proceed?", "Confirm", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				if (tblPreInsp.getRowCount()>0) {
					strBat = GetBatch("Inspection");
					for (int x = 0; x < modelPreInsp.getRowCount(); x++) {
						if ((Boolean) modelPreInsp.getValueAt(x, 1)) {
							pgUpdate dbIns = new pgUpdate();
							String strIns = "insert into rf_hdmf_insp_header (insp_rec_id, batch_no, entity_id, projcode, pbl_id, seq_no, date_filed, created_by, date_created, edited_by, date_edited, status_id) \n" +
									"values ("+_PagibigStatusMonitoring.GetRec("Inspection")+", '"+strBat+"', '"+modelPreInsp.getValueAt(x, 10).toString()+"', '"+modelPreInsp.getValueAt(x, 11).toString()+"', \n" +
									"'"+modelPreInsp.getValueAt(x, 12).toString()+"', "+modelPreInsp.getValueAt(x, 13).toString()+", '"+strDate+"'::date, '"+UserInfo.EmployeeCode+"', CURRENT_DATE::date, null, null, 'A')";
							dbIns.executeUpdate(strIns, false);
							dbIns.commit();
							
							pgUpdate dbRev = new pgUpdate();
							String strSQL = "insert into rf_hdmf_insp_detail (insp_detail_rec_id, entity_id, pbl_id, insp_status_id, trans_date, \n" +
									"created_by, date_created, hdmf_rep, remarks, status_id) values ("+_PagibigStatusMonitoring.GetRec("Inspected")+", '"+modelPreInsp.getValueAt(x, 10).toString()+"',  \n" +
									"'"+modelPreInsp.getValueAt(x, 12).toString()+"', '01', '"+dteDate.getText()+"', \n" +
									"'"+UserInfo.EmployeeCode+"', CURRENT_DATE, '', '', 'A')";
							dbRev.executeUpdate(strSQL, true);
							dbRev.commit();
						}
					}
					txtBat.setText(strBat);
					DisplayPosted();
				}
				hdmfMainMod.CtrlLock_1(3);
				hdmfMainMod.intInsp = 3;
				txtBat.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Ready for inspection batch created.");	
			}
		} else {
			JOptionPane.showMessageDialog(null, "No record was selected.");
		}
				
		FncGlobal.stopProgress();
	}
	
	public void Preview() {
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		String strDate = (String) sdfTo.format(dteDate.getDate());
		
		strDate = "As Of: " + strDate;
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
		mapParameters.put("02_AsOfDate", strDate);
		mapParameters.put("03_CoID", hdmfMainMod.txtCoID.getValue());
		mapParameters.put("04_ProID", hdmfMainMod.txtProID.getValue());
		mapParameters.put("06_Project", hdmfMainMod.txtPro.getText());
		mapParameters.put("07_User",  _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("09_Batch", txtBat.getText());
		FncReport.generateReport("/Reports/rpt_hdmf_pre_inspection.jasper", "HDMF Inspection Report", "", mapParameters);
	}
	
	public void Export() {
		FncGlobal.startProgress("Accounts for HDMF Inspection");
		String[] strCol = {"Name", "Phase", "Block", "Lot", "Date Filed", "Noa Released", "House %", "to CMD", "CMD to PMD"};
		String strSQL = "select name, Phase, Block, Lot, date_filed, noa_released, pctg, to_cmd, cmd_pmd from view_hdmf_pre_inspection('"+txtBat.getText()+"') order by name";
		
		FncGlobal.startProgress("Posting");
		FncGlobal.CreateXLS(strCol, strSQL, "HDMF Inspection");
		FncGlobal.stopProgress();
	}
	
	private static String GetBatch(String strType) {
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
			
			if (strBat==null) {
				strBat = "000001";
			}
		} else if (strType.equals("Validation")) {
			System.out.println("");
			System.out.println("Inspection");
			
			strSQL = "SELECT CASE WHEN TRIM(to_char(max(COALESCE(trans_no::INT, 0)) + 1, '000000')) \n" +
				"IS NULL THEN '000001' ELSE TRIM(to_char(max(COALESCE(trans_no::INT, 0)) + 1, '000000')) END \n" +
				"FROM rf_buyer_status \n" +
				"WHERE byrstatus_id = '107' AND status_id = 'A'";
			
			dbBatch.select(strSQL);
			
			if (dbBatch.isNotNull()) {
				strBat = (String) dbBatch.getResult()[0][0];
			} else {
				strBat = "000001";
			}
		}
		
		return strBat;
	}
	
	private void LocalCtrl(Integer i) {
		if (i==1) {								/***		Initial state		***/
			btnAdd.setEnabled(true);
			btnDel.setEnabled(true);
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
		} else {								/***		Add is pressed		***/
			btnAdd.setEnabled(false);
			btnDel.setEnabled(false);
			btnSave.setEnabled(true);
			btnCancel.setEnabled(true);			
		}
	}
	
	private void ClearBox() {
		dteDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		
		try {
			cboStatus.setSelectedIndex(0);	
		} catch (IllegalArgumentException ex) {
			System.out.println("");
			System.out.println("Combo box is empty.");
		}
		
		txtRep.setText("");
		txtRemarks.setText("");
	}
	
	private void subCtrl(Integer intBut) {
		if (intBut==1) {					/*		Initial state		*/
			btnAdd.setEnabled(true);
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
		} else {							/*		Add is pressed		*/
			btnAdd.setEnabled(false);
			btnSave.setEnabled(true);
			btnCancel.setEnabled(true);			
		}
	}
	
	private void EnableBox(Boolean blnDo) {
		dteDate.setEnabled(blnDo);
		cboStatus.setEnabled(blnDo);
		txtRep.setEditable(blnDo);
		txtRemarks.setEditable(blnDo);
	}
	
	private void displayClient() {
		String strUnit = GetValue("select pbl_id from mf_unit_info where trim(description) = '"+txtUnit.getText()+"'");
		String strSQL = "select b.entity_name as name, false, c.phase, c.block, c.lot, \n" + 
				"a.trans_date, d.status_desc as status, a.hdmf_rep, a.remarks \n" +
				"from rf_hdmf_insp_detail a \n" +
				"inner join rf_entity b on a.entity_id = b.entity_id \n" +
				"inner join mf_unit_info c on a.pbl_id = c.pbl_id and c.proj_id = '"+hdmfMainMod.txtProID.getValue()+"' \n" +
				"inner join mf_house_inspection_status d on a.insp_status_id = d.status_id \n" +
				"where a.entity_id = '"+txtClientID.getText()+"' and a.pbl_id = '"+strUnit+"' and a.status_id = 'A' \n" +
				"order by insp_detail_rec_id desc, a.trans_date";
		
		FncTables.clearTable(modelPostInsp);
		DefaultListModel listModel = new DefaultListModel();
		rowPostInsp.setModel(listModel);
		
		pgSelect db = new pgSelect();
		db.select(strSQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelPostInsp.addRow(db.getResult()[x]);
				listModel.addElement(modelPostInsp.getRowCount());
			}
		}
	}
	
	public Boolean DisplayBatch(Boolean blnDo) {
		Boolean blnRet = false;
		FncTables.clearTable(modelInspBatch);
		DefaultListModel listModel = new DefaultListModel();
		rowInspBatch.setModel(listModel); 
		
		String SQL = "";
		if (blnDo) {
			SQL = "select a.name, true as tag, b.description as unit, \n" + 
					"(select y.status_desc from rf_hdmf_insp_detail x inner join mf_house_inspection_status y on x.insp_status_id = y.status_id where x.entity_id = a.entity_id and x.pbl_id = a.pbl_id order by x.trans_date::date desc, x.insp_detail_rec_id desc limit 1) as status, \n" + 
					"'' as newstatus, \n" +
					"null::date as date, \n" +
					"(select x.hdmf_rep from rf_hdmf_insp_detail x inner join mf_house_inspection_status y on x.insp_status_id = y.status_id where x.entity_id = a.entity_id and x.pbl_id = a.pbl_id order by x.trans_date::date desc, x.insp_detail_rec_id desc limit 1) as hdmf_rep, \n" +
					"(select x.remarks from rf_hdmf_insp_detail x inner join mf_house_inspection_status y on x.insp_status_id = y.status_id where x.entity_id = a.entity_id and x.pbl_id = a.pbl_id order by x.trans_date::date desc, x.insp_detail_rec_id desc limit 1) as remarks, \n" +
					"a.entity_id, a.projcode, a.pbl_id, a.seq_no \n" +
					"from view_hdmf_pre_inspection_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"') a \n" +
					"inner join mf_unit_info b on a.projcode = b.proj_id and a.pbl_id = b.pbl_id \n" +
					"order by a.name";	
		} else {
			SQL = "select a.name, true as tag, b.description as unit, \n" + 
					"(select y.status_desc from rf_hdmf_insp_detail x inner join mf_house_inspection_status y on x.insp_status_id = y.status_id where x.entity_id = a.entity_id and x.pbl_id = a.pbl_id order by x.trans_date::date desc, x.insp_detail_rec_id desc limit 1) as status, \n" + 
					"'' as newstatus, \n" +
					"'"+dteFrom_2.getDate()+"'::date as date, \n" +
					"'' as hdmf_rep, \n" +
					"'' as remarks, \n" +
					"a.entity_id, a.projcode, a.pbl_id, a.seq_no \n" +
					"from view_hdmf_pre_inspection_v2('"+txtBat.getText()+"','"+PagibigStatusMonitoring_v2.company_id+"','"+PagibigStatusMonitoring_v2.project_id+"','"+PagibigStatusMonitoring_v2.phase_id+"') a \n" +
					"inner join mf_unit_info b on a.projcode = b.proj_id and a.pbl_id = b.pbl_id \n" +
					"order by a.name";
		}
		
		System.out.println("");
		System.out.println(SQL);
		
		FncGlobal.startProgress("Accounts Ready for Inspection");
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelInspBatch.addRow(db.getResult()[x]);
				listModel.addElement(modelInspBatch.getRowCount());
			}
			blnRet = true;
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned for posting.");
			blnRet = false;
		};
		
		FncGlobal.stopProgress();
		return blnRet;
	}
	
	private void GridLookup() {
		int iCol = tblInspBatch.getSelectedColumn();
		int iRow = tblInspBatch.getSelectedRow();

		System.out.println("");
		System.out.println("iCol: " + iCol);
		
		if (iCol == 4) {
			System.out.println("");
			System.out.println("Double-click!");
			
			String strStat = "select status_id, status_desc from mf_house_inspection_status order by status_id";
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "New Status", strStat, false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelInspBatch.setValueAt(data[1], iRow, iCol);
			}	
		}
	}
	
	private void LocalCtrl_2(Integer i) {
		if (i==1) {								/***		Initial state		***/
			btnAdd_2.setEnabled(true);
			btnApply.setEnabled(false);
			btnSave_2.setEnabled(false);
			btnCancel_2.setEnabled(false);
			
			cboStatus_2.setEnabled(false);
			dteFrom_2.setEnabled(false);
			tblInspBatch.setEditable(false);
			txtBat_2.setEnabled(true);
		} else {								/***		Add is pressed		***/
			btnAdd_2.setEnabled(false);
			btnApply.setEnabled(true);
			btnSave_2.setEnabled(true);
			btnCancel_2.setEnabled(true);
			
			cboStatus_2.setEnabled(true);
			dteFrom_2.setEnabled(true);
			tblInspBatch.setEditable(true);
			txtBat_2.setEnabled(false);
		}
	}
	
	public void mouseClicked(MouseEvent evt) {
		System.out.println("");
		System.out.println("Click: " + evt.getClickCount());
		
		if ((evt.getClickCount() >= 1)) {
			GridLookup();
		}
	}
	public static String GetValue(String SQL) {
		String strValue = "";
		pgSelect sqlExec = new pgSelect();
		
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			strValue = (String) sqlExec.getResult()[0][0];
		} else {
			strValue = "";
		}
		
		return strValue;
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
}
