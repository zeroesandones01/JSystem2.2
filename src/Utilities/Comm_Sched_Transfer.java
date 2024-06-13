package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelComm_Sched_Transfer;
import tablemodel.model_Comm_ViewClientRequest;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class Comm_Sched_Transfer extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Commission Schedule Transfer";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlOldSchedule;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlUnitDetails;
	private JPanel pnlUnitDetails_a;
	private JPanel pnlUnitDetails_b;
	private JPanel pnlUnitDetails_b1;
	private JPanel pnlUnitDetails_b2;
	private JPanel pnlClient_status_a;
	private JPanel pnlClient_status_b;
	private JPanel pnlUnitPaymentInfo;
	private JPanel pnlNewSchedule;

	private modelComm_Sched_Transfer modelNew_CommSched;
	private modelComm_Sched_Transfer modelNew_CommSched_total;
	private modelComm_Sched_Transfer modelOld_CommSched;
	private modelComm_Sched_Transfer modelOld_CommSched_total;

	private _JLookup lookupCompany;
	private _JLookup lookupClient;

	private JButton btnCancel;
	private JButton btnSave;
	private JButton btnRefresh;
	private JButton btnAutoTransfer;

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel lblCompany;
	private JLabel lblClient;
	private JLabel lblCommType;
	private JLabel lblStatus;
	private JLabel lblUnit;
	private JLabel lblNSP;
	private JLabel lblCommRate;

	private _JTagLabel tagCompany;
	private _JTagLabel tagClientName;
	private _JTagLabel tagStatus;
	private _JTagLabel tagUnit;	

	private _JScrollPaneMain scrollNew_CommSched;
	private _JScrollPaneMain scrollOld_CommSched;
	private _JScrollPaneTotal scrollNew_CommSchedtotal;
	private _JScrollPaneTotal scrollOld_CommSchedtotal;
	private _JTableMain tblNew_CommSched;
	private _JTableMain tblOld_CommSched;
	private JList rowHeaderNew_CommSched;
	private JList rowHeaderOld_CommSched;
	private _JTableTotal tblNew_CommSched_total;
	private _JTableTotal tblOld_CommSched_total;

	private JSplitPane splitPanel;
	private _JLookup lookupUnit;

	private JXTextField txtStatus;
	private JXTextField txtSalesType;
	private JXTextField txtClient;

	private _JXFormattedTextField txtNSP;
	private _JXFormattedTextField txtCommRate;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	static String co_id = "";		
	String company 	= "";
	String pbl_id 	= "";
	String seq_no 	= "";
	String entity_id= "";
	String status_id= "";
	Double nsp		= 0.00;
	String status	= "";
	String company_logo;	
	Integer comm_freq = null;
	String request_no= "";
	String entity_name = "";
	private JLabel lblRequest;
	private _JTagLabel tagRequest;
	private JLabel lblCommAmt;
	private _JXFormattedTextField txtCommAmt;
	private JButton btnViewRequest;
	private JPanel pnlClientRequest;
	private _JScrollPaneMain scrollViewClientReqesut;
	private _JTableMain tblClientRequest;
	private model_Comm_ViewClientRequest modelView_clientRequest;
	private JList rowHeaderClient_request;
	private JPopupMenu menu;
	private JMenuItem mniView;	

	public Comm_Sched_Transfer() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Comm_Sched_Transfer(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Comm_Sched_Transfer(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

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
		this.setPreferredSize(new java.awt.Dimension(962, 624));
		this.setBounds(0, 0, 962, 624);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(938, 560));

		{ //start of Company

			pnlComp = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlComp, BorderLayout.NORTH);			
			pnlComp.setPreferredSize(new java.awt.Dimension(905, 165));	
			//pnlComp.setBorder(lineBorder);

			{
				//company
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				pnlComp_a.setPreferredSize(new java.awt.Dimension(926, 38));	
				pnlComp_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));		
				pnlComp_a.setBorder(lineBorder);

				{
					pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
					pnlComp_a1.setPreferredSize(new java.awt.Dimension(207, 30));
					pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

					{
						lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
						pnlComp_a1.add(lblCompany);
						lblCompany.setBounds(8, 11, 62, 12);
						lblCompany.setPreferredSize(new java.awt.Dimension(115, 25));
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
									String name = (String) data[1];						
									tagCompany.setTag(name);									

									lblClient.setEnabled(true);	
									lookupClient.setEnabled(true);	
									tagClientName.setEnabled(true);	

								}
							}
						});
					}	
				}
				{
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
			}
			{
				pnlUnitDetails = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlUnitDetails, BorderLayout.CENTER);	
				pnlUnitDetails.setPreferredSize(new java.awt.Dimension(921, 35));					
				pnlUnitDetails.setBorder(lineBorder);

				{
					{
						pnlUnitDetails_a = new JPanel(new GridLayout(4, 1, 5, 5));
						pnlUnitDetails.add(pnlUnitDetails_a, BorderLayout.WEST);	
						pnlUnitDetails_a.setPreferredSize(new java.awt.Dimension(96, 35));	
						pnlUnitDetails_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							lblUnit = new JLabel("Unit ID ", JLabel.TRAILING);
							pnlUnitDetails_a.add(lblUnit);
							lblUnit.setEnabled(true);	
							lblUnit.setPreferredSize(new java.awt.Dimension(100, 40));
							lblUnit.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}	
						{
							lblClient = new JLabel("Client ", JLabel.TRAILING);
							pnlUnitDetails_a.add(lblClient);
							lblClient.setEnabled(false);	
							lblClient.setPreferredSize(new java.awt.Dimension(100, 40));
							lblClient.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}							
						{
							lblStatus = new JLabel("Status ", JLabel.TRAILING);
							pnlUnitDetails_a.add(lblStatus);
							lblStatus.setEnabled(false);	
							lblStatus.setPreferredSize(new java.awt.Dimension(100, 40));
							lblStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}	
						{
							lblRequest = new JLabel("Client Request ", JLabel.TRAILING);
							pnlUnitDetails_a.add(lblRequest);
							lblRequest.setEnabled(false);	
							lblRequest.setPreferredSize(new java.awt.Dimension(100, 40));
							lblRequest.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}	
					}

					{
						pnlUnitDetails_b = new JPanel(new BorderLayout(5, 5));
						pnlUnitDetails.add(pnlUnitDetails_b, BorderLayout.CENTER);	
						pnlUnitDetails_b.setPreferredSize(new java.awt.Dimension(802, 35));	
						pnlUnitDetails_b.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

						{
							pnlUnitDetails_b1 = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlUnitDetails_b.add(pnlUnitDetails_b1, BorderLayout.WEST);	
							pnlUnitDetails_b1.setPreferredSize(new java.awt.Dimension(102, 27));
							pnlUnitDetails_b1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							{
								lookupUnit = new _JLookup(null, "Unit ID",0, 0);
								pnlUnitDetails_b1.add(lookupUnit);
								lookupUnit.setBounds(20, 27, 20, 25);
								lookupUnit.setEnabled(true);	
								lookupUnit.setLookupSQL(getEntityList());
								lookupUnit.setPreferredSize(new java.awt.Dimension(101, 27));
								lookupUnit.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){	

											pbl_id = data[0].toString();
											seq_no = data[1].toString();
											String desc = (String) data[2];	
											String proj_alias = (String) data[9];
											lookupUnit.setText(pbl_id);
											tagUnit.setTag(desc + " | " + "Seq. No. " + seq_no + " | " + proj_alias);

											entity_id = data[3].toString();
											entity_name = data[4].toString();
											txtClient.setText(entity_id);
											tagClientName.setTag(entity_name);

											status_id = data[5].toString();
											String status_desc = (String) data[6];
											txtStatus.setText(status_id);
											tagStatus.setTag(status_desc);

											nsp		= Double.parseDouble(data[7].toString());
											txtNSP.setValue(nsp);
											Double comm_rate = Double.parseDouble(data[12].toString());

											txtCommRate.setValue(comm_rate);	
											txtSalesType.setText((String) data[8]);
											txtCommAmt.setValue(nsp*comm_rate/100);

											enableUnitDetails(true);											

											refreshTables();

											comm_freq = getCommFrequency();
											System.out.printf("comm_freq: " + comm_freq);


											displayClient_Old_Commission(modelOld_CommSched, rowHeaderOld_CommSched, modelOld_CommSched_total, comm_freq - 1);		


											if (sql_getPrevComm()>0.00)
											{
												displayClient_Transfered_Commission(modelNew_CommSched, rowHeaderNew_CommSched, modelNew_CommSched_total,comm_freq );
												JOptionPane.showMessageDialog(getContentPane(),"<html><b><i>Commission Schedule <html></b></i>" +
														"<html>has been already transfered.<html>","", JOptionPane.INFORMATION_MESSAGE);	
												enableButtons(false, false, false, true);												
											}
											else 
											{
												displayClient_New_Commission(modelNew_CommSched, rowHeaderNew_CommSched, modelNew_CommSched_total,comm_freq );
												enableButtons(true, false, true, true);												
											}
										}

									}
								});
							}	
							{
								txtClient = new JXTextField("");
								pnlUnitDetails_b1.add(txtClient);
								txtClient.setEnabled(false);	
								txtClient.setEditable(false);
								txtClient.setBounds(120, 25, 300, 22);	
								txtClient.setHorizontalAlignment(JTextField.CENTER);
								txtClient.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}
							{
								txtStatus = new JXTextField("");
								pnlUnitDetails_b1.add(txtStatus);
								txtStatus.setEnabled(false);	
								txtStatus.setEditable(false);
								txtStatus.setBounds(120, 25, 300, 22);	
								txtStatus.setHorizontalAlignment(JTextField.CENTER);
								txtStatus.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}	
							{
								btnViewRequest = new JButton("View Request");
								pnlUnitDetails_b1.add(btnViewRequest);
								btnViewRequest.setActionCommand("view");
								btnViewRequest.addActionListener(this);
								btnViewRequest.setEnabled(false);
							}	
						}
					}
					{
						pnlUnitDetails_b2 = new JPanel(new GridLayout(4, 1, 5, 5));
						pnlUnitDetails_b.add(pnlUnitDetails_b2, BorderLayout.CENTER);	
						pnlUnitDetails_b2.setPreferredSize(new java.awt.Dimension(357, 25));	
						pnlUnitDetails_b2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							tagUnit = new _JTagLabel("[ ]");
							pnlUnitDetails_b2.add(tagUnit);
							tagUnit.setBounds(209, 27, 700, 22);
							tagUnit.setEnabled(true);	
							tagUnit.setPreferredSize(new java.awt.Dimension(27, 33));
						}
						{

							tagClientName = new _JTagLabel("[ ]");
							pnlUnitDetails_b2.add(tagClientName);
							tagClientName.setBounds(209, 27, 700, 22);
							tagClientName.setEnabled(false);	
							tagClientName.setPreferredSize(new java.awt.Dimension(27, 33));
						}
						{
							tagStatus = new _JTagLabel("[ ]");
							pnlUnitDetails_b2.add(tagStatus);
							tagStatus.setBounds(209, 27, 700, 22);
							tagStatus.setEnabled(false);	
							tagStatus.setPreferredSize(new java.awt.Dimension(27, 33));
						}
						{
							tagRequest = new _JTagLabel("[ ]");
							pnlUnitDetails_b2.add(tagRequest);
							tagRequest.setBounds(209, 27, 700, 22);
							tagRequest.setEnabled(false);	
							tagRequest.setVisible(false);	
							tagRequest.setPreferredSize(new java.awt.Dimension(27, 33));
						}
					}		
					{
						pnlUnitPaymentInfo = new JPanel(new BorderLayout(5, 5));
						pnlUnitDetails_b.add(pnlUnitPaymentInfo, BorderLayout.EAST);	
						pnlUnitPaymentInfo.setPreferredSize(new java.awt.Dimension(280, 93));	
						pnlUnitPaymentInfo.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							pnlClient_status_a = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlUnitPaymentInfo.add(pnlClient_status_a, BorderLayout.WEST);	
							pnlClient_status_a.setPreferredSize(new java.awt.Dimension(75, 93));	
							pnlClient_status_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

							{
								lblNSP = new JLabel("NSP ", JLabel.TRAILING);
								pnlClient_status_a.add(lblNSP);
								lblNSP.setEnabled(false);	
								lblNSP.setPreferredSize(new java.awt.Dimension(87, 93));
								lblNSP.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}
							{
								lblCommType = new JLabel("Comm Type ", JLabel.TRAILING);
								pnlClient_status_a.add(lblCommType);
								lblCommType.setEnabled(false);	
								lblCommType.setPreferredSize(new java.awt.Dimension(87, 93));
								lblCommType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}
							{
								lblCommRate = new JLabel("Comm. Rate ", JLabel.TRAILING);
								pnlClient_status_a.add(lblCommRate);
								lblCommRate.setEnabled(false);	
								lblCommRate.setPreferredSize(new java.awt.Dimension(87, 93));
								lblCommRate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}
							{
								lblCommAmt = new JLabel("Comm. Amt. ", JLabel.TRAILING);
								pnlClient_status_a.add(lblCommAmt);
								lblCommAmt.setEnabled(false);	
								lblCommAmt.setPreferredSize(new java.awt.Dimension(87, 93));
								lblCommAmt.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}
						}
						{
							pnlClient_status_b = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlUnitPaymentInfo.add(pnlClient_status_b, BorderLayout.CENTER);	
							pnlClient_status_b.setPreferredSize(new java.awt.Dimension(342, 93));	
							pnlClient_status_b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

							{
								txtNSP = new _JXFormattedTextField(JXFormattedTextField.CENTER);
								pnlClient_status_b.add(txtNSP);
								txtNSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtNSP.setText("0.00");
								txtNSP.setEnabled(false);
								txtNSP.setEditable(false);
								txtNSP.setBounds(120, 0, 72, 22);		
							}
							{
								txtSalesType = new JXTextField("");
								pnlClient_status_b.add(txtSalesType);
								txtSalesType.setEnabled(false);	
								txtSalesType.setEditable(false);
								txtSalesType.setBounds(120, 25, 300, 22);	
								txtSalesType.setHorizontalAlignment(JTextField.CENTER);
								txtSalesType.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							}
							{
								txtCommRate = new _JXFormattedTextField(JXFormattedTextField.CENTER);
								pnlClient_status_b.add(txtCommRate);
								txtCommRate.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtCommRate.setText("0.00");
								txtCommRate.setEnabled(false);
								txtCommRate.setEditable(false);
								txtCommRate.setBounds(120, 0, 72, 22);	
							}
							{
								txtCommAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
								pnlClient_status_b.add(txtCommAmt);
								txtCommAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtCommAmt.setText("0.00");
								txtCommAmt.setEnabled(false);
								txtCommAmt.setEditable(false);
								txtCommAmt.setBounds(120, 0, 72, 22);	
							}
						}
					}		
				}
			}										
		} //end of Company

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));	

			splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			pnlTable.add(splitPanel);
			splitPanel.setOneTouchExpandable(true);
			splitPanel.setResizeWeight(.7d);

			//start of OLD Commission Schedule
			{		
				pnlOldSchedule = new JPanel();
				splitPanel.add(pnlOldSchedule, JSplitPane.LEFT);
				pnlOldSchedule.setLayout(new BorderLayout(5, 5));
				pnlOldSchedule.setPreferredSize(new java.awt.Dimension(903, 176));
				pnlOldSchedule.setBorder(lineBorder);
				pnlOldSchedule.setBorder(JTBorderFactory.createTitleBorder("Old Commission / Promo / Incentive Schedule"));

				{			
					{
						scrollOld_CommSched = new _JScrollPaneMain();
						pnlOldSchedule.add(scrollOld_CommSched, BorderLayout.CENTER);

						{
							modelOld_CommSched = new modelComm_Sched_Transfer();

							tblOld_CommSched = new _JTableMain(modelOld_CommSched);
							scrollOld_CommSched.setViewportView(tblOld_CommSched);
							tblOld_CommSched.addMouseListener(this);								
							tblOld_CommSched.setSortable(false);
							tblOld_CommSched.getColumnModel().getColumn(0).setPreferredWidth(300);
							tblOld_CommSched.getColumnModel().getColumn(1).setPreferredWidth(60);
							tblOld_CommSched.getColumnModel().getColumn(2).setPreferredWidth(60);
							tblOld_CommSched.hideColumns("<html>New Schedule<html><br><html><center>Amount<center><html>");
							tblOld_CommSched.getColumnModel().getColumn(3).setPreferredWidth(150);
							tblOld_CommSched.getColumnModel().getColumn(4).setPreferredWidth(80);
							tblOld_CommSched.getColumnModel().getColumn(5).setPreferredWidth(60);
							tblOld_CommSched.getColumnModel().getColumn(6).setPreferredWidth(120);
							tblOld_CommSched.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {}							
								public void keyPressed(KeyEvent e) {}	

							}); 
							tblOld_CommSched.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblOld_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblOld_CommSched.setCellSelectionEnabled(true);}	

								}
								public void mouseReleased(MouseEvent e) {
									if(tblOld_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblOld_CommSched.setCellSelectionEnabled(true);}
								}
							});

						}
						{
							rowHeaderOld_CommSched = tblOld_CommSched.getRowHeader22();
							scrollOld_CommSched.setRowHeaderView(rowHeaderOld_CommSched);
							scrollOld_CommSched.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollOld_CommSchedtotal = new _JScrollPaneTotal(scrollOld_CommSched);
						pnlOldSchedule.add(scrollOld_CommSchedtotal, BorderLayout.SOUTH);
						{
							modelOld_CommSched_total = new modelComm_Sched_Transfer();
							modelOld_CommSched_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null });

							tblOld_CommSched_total = new _JTableTotal(modelOld_CommSched_total, tblOld_CommSched);
							tblOld_CommSched_total.setFont(dialog11Bold);
							scrollOld_CommSchedtotal.setViewportView(tblOld_CommSched_total);
							((_JTableTotal) tblOld_CommSched_total).setTotalLabel(0);
						}
					}
				} 
				//end of OLD Commission Schedule

			}
			//start of NEW Commission Schedule
			{						

				pnlNewSchedule = new JPanel();
				splitPanel.add(pnlNewSchedule, JSplitPane.RIGHT);
				pnlNewSchedule.setLayout(new BorderLayout(5, 5));
				pnlNewSchedule.setPreferredSize(new java.awt.Dimension(903, 192));
				pnlNewSchedule.setBorder(lineBorder);
				pnlNewSchedule.setBorder(JTBorderFactory.createTitleBorder("New Commission Schedule"));

				{
					scrollNew_CommSched = new _JScrollPaneMain();
					pnlNewSchedule.add(scrollNew_CommSched, BorderLayout.CENTER);

					{
						modelNew_CommSched = new modelComm_Sched_Transfer();

						tblNew_CommSched = new _JTableMain(modelNew_CommSched);
						scrollNew_CommSched.setViewportView(tblNew_CommSched);
						tblNew_CommSched.addMouseListener(this);								
						tblNew_CommSched.setSortable(false);
						tblNew_CommSched.getColumnModel().getColumn(0).setPreferredWidth(300);
						tblNew_CommSched.getColumnModel().getColumn(1).setPreferredWidth(60);
						tblNew_CommSched.getColumnModel().getColumn(2).setPreferredWidth(60);
						tblNew_CommSched.getColumnModel().getColumn(3).setPreferredWidth(150);
						tblNew_CommSched.getColumnModel().getColumn(4).setPreferredWidth(80);
						tblNew_CommSched.getColumnModel().getColumn(5).setPreferredWidth(60);
						tblNew_CommSched.getColumnModel().getColumn(6).setPreferredWidth(120);
						tblNew_CommSched.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								tblNew_CommSched.packAll();
								computeRlsdCommTotal();
							}							
							public void keyPressed(KeyEvent e) {
								tblNew_CommSched.packAll();
								computeRlsdCommTotal();
							}							
						}); 
						tblNew_CommSched.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblNew_CommSched.rowAtPoint(e.getPoint()) == -1){}
								else{tblNew_CommSched.setCellSelectionEnabled(true);}
								tblNew_CommSched.packAll();
								computeRlsdCommTotal();
								//totalAllComm(modelNew_CommSched, modelNew_CommSched_total);	
							}
							public void mouseReleased(MouseEvent e) {
								if(tblNew_CommSched.rowAtPoint(e.getPoint()) == -1){}
								else{tblNew_CommSched.setCellSelectionEnabled(true);}
								tblNew_CommSched.packAll();
								computeRlsdCommTotal();
							}
						});

					}
					{
						rowHeaderNew_CommSched = tblNew_CommSched.getRowHeader22();
						scrollNew_CommSched.setRowHeaderView(rowHeaderNew_CommSched);
						scrollNew_CommSched.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					scrollNew_CommSchedtotal = new _JScrollPaneTotal(scrollNew_CommSched);
					pnlNewSchedule.add(scrollNew_CommSchedtotal, BorderLayout.SOUTH);
					{
						modelNew_CommSched_total = new modelComm_Sched_Transfer();
						modelNew_CommSched_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), new BigDecimal(0.00), null, null, null, null });

						tblNew_CommSched_total = new _JTableTotal(modelNew_CommSched_total, tblNew_CommSched);
						tblNew_CommSched_total.setFont(dialog11Bold);
						scrollNew_CommSchedtotal.setViewportView(tblNew_CommSched_total);
						((_JTableTotal) tblNew_CommSched_total).setTotalLabel(0);
					}
				}
			} 
			//end of Commission Schedule (by client)			
		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(1014, 33));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));

			{
				{
					btnAutoTransfer = new JButton("Auto-Transfer");
					pnlSouthCenterb.add(btnAutoTransfer);
					btnAutoTransfer.setActionCommand("AutoTransfer");
					btnAutoTransfer.addActionListener(this);
					btnAutoTransfer.setEnabled(false);
				}
				{
					btnSave = new JButton("Save");
					pnlSouthCenterb.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
				}
				{
					btnRefresh = new JButton("Refresh");
					pnlSouthCenterb.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");
					btnRefresh.addActionListener(this);
					btnRefresh.setEnabled(false);
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

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();

		{
			pnlClientRequest = new JPanel();
			pnlClientRequest.setLayout(new BorderLayout(5, 5));
			pnlClientRequest.setBorder(lineBorder);		
			pnlClientRequest.setPreferredSize(new java.awt.Dimension(600, 300));		

			{					
				{
					scrollViewClientReqesut = new _JScrollPaneMain();
					pnlClientRequest.add(scrollViewClientReqesut, null);
					{
						modelView_clientRequest = new model_Comm_ViewClientRequest();

						tblClientRequest = new _JTableMain(modelView_clientRequest);
						scrollViewClientReqesut.setViewportView(tblClientRequest);
						tblClientRequest.addMouseListener(this);	
						tblClientRequest.getColumnModel().getColumn(0).setPreferredWidth(50);
						tblClientRequest.getColumnModel().getColumn(1).setPreferredWidth(240);
						tblClientRequest.getColumnModel().getColumn(2).setPreferredWidth(80);
						tblClientRequest.getColumnModel().getColumn(3).setPreferredWidth(80);
						tblClientRequest.addMouseListener(new PopupTriggerListener_panel());
						tblClientRequest.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {tblClientRequest.packAll();}							
							public void keyPressed(KeyEvent e) {tblClientRequest.packAll();}	

						}); 
						tblClientRequest.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if(tblClientRequest.rowAtPoint(e.getPoint()) == -1){}
								else{tblClientRequest.setCellSelectionEnabled(true);}						

							}
							public void mouseReleased(MouseEvent e) {
								if(tblClientRequest.rowAtPoint(e.getPoint()) == -1){}
								else{tblClientRequest.setCellSelectionEnabled(true);}
							}
						});
					}
					{
						rowHeaderClient_request = tblClientRequest.getRowHeader22();
						scrollViewClientReqesut.setRowHeaderView(rowHeaderClient_request);
						scrollViewClientReqesut.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						scrollViewClientReqesut.setPreferredSize(new java.awt.Dimension(505, 220));
					}
				}

			}
		}
		{
			menu = new JPopupMenu("Popup");
			mniView = new JMenuItem("View Request");
			menu.add(mniView);
			mniView.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					//Integer x = tblClientRequest.getSelectedRow();
					//request_no = modelView_clientRequest.getValueAt(x,4).toString();
					if (!request_no.equals(""))
					{
						System.out.printf("request_no : " + request_no + "\n");
						Map<String, Object> mapRequest = new HashMap<String, Object>();
						mapRequest.put("request_no", request_no);
						mapRequest.put("prepared_by", UserInfo.FullName2);

						FncReport.generateReport("/Reports/rptClientRequest.jasper", String.format("Client Request Approval (%s)", entity_name), mapRequest);
					}
					
					Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlClientRequest);
					optionPaneWindow.dispose();
				}
			});
		}



	}


	//display tables
	private void displayClient_Old_Commission(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, Integer freq) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select\r\n" + 
			"\r\n" + 
			"trim(g.entity_name) as agent_name,\r\n" + 	
			"d.rate,\r\n" + 
			"f.position_abbv,\r\n" + 
			"( case when trim(a.comm_type) = '1' then '1st CA' else\r\n" + 
			"	case when trim(a.comm_type) = '2' then '2nd CA' else\r\n" + 
			"	case when trim(a.comm_type) = '3' then '3rd CA' else\r\n" + 
			"	case when trim(a.comm_type) = 'FC' then 'Full Comm' else " +
			"	case when trim(a.tran_type) = 'BB' then 'Promo' else '' end end end end end ) as comm_type,\r\n" + 
			"a.comm_amt as sked_amt,\r\n" + 
			"(case when h.status_id = 'R' then a.comm_amt else 0.00 end) as rlsd_amt, \n " +
			"0.00 as newsked_amt,\r\n" + 
			"to_char(h.date_released,'MM-dd-yyyy') as rlsd_date, \n " +
			"(case when a.cdf_no is null then '' else a.cdf_no end) as cdf_no , \n" +			
			"( case when h.cdf_no is not null and h.date_released is null then 'Processed' else\r\n" + 
			"	case when h.cdf_no is not null and h.date_released is not null then 'Released' else \r\n" + 
			"	case when h.cdf_no is null then 'Not Yet Qualified' end end end ) as status, \n" +
			"(case when a.remarks is null then '' else a.remarks end) as remarks," +
			"a.rec_id \n" +
			"\r\n" + 			

			"from (select * from cm_schedule_dl where status = 'I') a\r\n" + 
			"left join (select * from cm_schedule_hd where status_id = 'I') d on a.pbl_id = d.pbl_id \n" +
			"	and a.seq_no=d.seq_no and a.agent_code=d.agent_code and a.frequency::int = d.frequency   \r\n" + 
			"left join rf_entity e on d.agent_code = e.entity_id\r\n" + 
			"left join rf_entity g on a.agent_code = g.entity_id\r\n" + 
			"left join mf_sales_position f on d.orig_position  = f.position_code\r\n" + 
			"left join cm_cdf_hd h on a.cdf_no = h.cdf_no \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"where a.pbl_id = '"+pbl_id+"' \r\n" + 
			"and a.seq_no = "+seq_no+"   " +
			"and a.frequency::int = "+freq+"  \n" +
			"\r\n" + 
			"order by a.rec_id, d.rate, e.entity_name, a.comm_type\r\n" + 
			"" ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalAllComm(modelMain, modelTotal);

			enableButtons(true, false, false, true);
		}		


		else {			

			JOptionPane.showMessageDialog(getContentPane(),"Selected unit does not \n" +
					"have old commission schedule.","Warning",JOptionPane.WARNING_MESSAGE);
			enableButtons(false, false, false, true);

			modelOld_CommSched_total = new modelComm_Sched_Transfer();
			modelOld_CommSched_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null, null });

			tblOld_CommSched_total = new _JTableTotal(modelOld_CommSched_total, tblOld_CommSched);
			tblOld_CommSched_total.setFont(dialog11Bold);
			scrollOld_CommSchedtotal.setViewportView(tblOld_CommSched_total);
			((_JTableTotal) tblOld_CommSched_total).setTotalLabel(0);}

		tblOld_CommSched.packAll();	


		adjustRowHeight_account(tblOld_CommSched);
	}	

	private void displayClient_New_Commission(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, Integer freq) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select\r\n" + 
			"\r\n" + 
			"trim(g.entity_name) as agent_name,\r\n" + 	
			"d.rate,\r\n" + 
			"f.position_abbv,\r\n" + 
			"( case when trim(a.comm_type) = '1' then '1st CA' else\r\n" + 
			"	case when trim(a.comm_type) = '2' then '2nd CA' else\r\n" + 
			"	case when trim(a.comm_type) = '3' then '3rd CA' else\r\n" + 
			"	case when trim(a.comm_type) = 'FC' then 'Full Comm' else \n" +
			"	case when trim(a.tran_type) = 'BB' then 'Promo' else '' end end end end end ) as comm_type,\r\n" + 
			"a.comm_amt as sked_amt,\r\n" + 
			"(case when h.status_id = 'R' then a.comm_amt else 0.00 end) as rlsd_amt,\r\n" + 
			"0.00 as newsked_amt,\r\n" + 
			"to_char(h.date_released,'MM-dd-yyyy') as rlsd_date, \n " +
			"a.cdf_no, \n" +			
			"( case when h.status_id = 'I' then 'Canceled' else \n" +
			"	case when h.status_id = 'R' then 'Released' else \n" + 
			"	case when status = 'A' then 'Not Yet Qualified' else\r\n" + 
			"	case when status = 'P' then 'Processed'  end end end end ) as status,  \r\n" + 			
			"(case when a.remarks is null then '' else a.remarks end) as remarks," +
			"a.rec_id \n" +
			"\r\n" + 			

			"from (select * from cm_schedule_dl where status != 'I') a\r\n" + 
			"left join (select * from cm_schedule_hd where status_id != 'I') d on a.pbl_id = d.pbl_id " +
			"	and a.seq_no=d.seq_no and a.agent_code=d.agent_code and a.frequency::int = d.frequency  \r\n" + 
			"left join rf_entity e on d.agent_code = e.entity_id\r\n" + 
			"left join rf_entity g on a.agent_code = g.entity_id\r\n" + 
			"left join mf_sales_position f on d.orig_position  = f.position_code\r\n" + 
			"left join cm_cdf_hd h on a.cdf_no = h.cdf_no \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"where a.pbl_id = '"+pbl_id+"' \r\n" + 
			"and a.seq_no = "+seq_no+"   \n" ;
		
			if (freq>=1){sql = sql + "and a.frequency::int = "+freq+"  \n" ;}
			else {}
			
			sql = sql +			
			"\r\n" + 
			"order by a.rec_id, d.rate, e.entity_name, a.comm_type\r\n" + 
			"" ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalAllComm(modelMain, modelTotal);			

			enableButtons(true, false, true, true);
		}		


		else {			

			JOptionPane.showMessageDialog(getContentPane(),"Selected unit does not \n" +
					"have new commission schedule.","Warning",JOptionPane.WARNING_MESSAGE);
			enableButtons(false, false, true, true);

			modelNew_CommSched_total = new modelComm_Sched_Transfer();
			modelNew_CommSched_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null, null });

			tblNew_CommSched_total = new _JTableTotal(modelNew_CommSched_total, tblNew_CommSched);
			tblNew_CommSched_total.setFont(dialog11Bold);
			scrollNew_CommSchedtotal.setViewportView(tblNew_CommSched_total);
			((_JTableTotal) tblNew_CommSched_total).setTotalLabel(0);}

		tblNew_CommSched.packAll();		

		adjustRowHeight_account(tblNew_CommSched);
	}

	private void displayClient_Transfered_Commission(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, Integer freq) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select\r\n" + 
			"\r\n" + 
			"trim(g.entity_name) as agent_name,\r\n" + 	
			"d.rate,\r\n" + 
			"f.position_abbv,\r\n" + 
			"( case when trim(a.comm_type) = '1' then '1st CA' else\r\n" + 
			"	case when trim(a.comm_type) = '2' then '2nd CA' else\r\n" + 
			"	case when trim(a.comm_type) = '3' then '3rd CA' else\r\n" + 
			"	case when trim(a.comm_type) = 'FC' then 'Full Comm' else \n" +
			"	case when trim(a.tran_type) = 'BB' then 'Promo' else '' end end end end end ) as comm_type,\r\n" + 
			"a.prev_commamt as sked_amt,\r\n" + 
			"a.sked_amt as rlsd_amt,\r\n" + 
			"a.comm_amt,\r\n" + 
			"to_char(h.date_released,'MM-dd-yyyy') as rlsd_date, \n " +
			"a.old_cdfno, \n" +			
			"( case when h.status_id = 'I' then 'Canceled' else \n" +			
			"	case when status = 'A' then 'Not Yet Qualified' else\r\n" + 
			"	case when h.status_id = 'R' then 'Released' else \n" + 
			"	case when status = 'P' then 'Processed'  end end end end ) as status,  \r\n" + 			
			"(case when a.remarks is null then '' else a.remarks end) as remarks," +
			"a.rec_id \n" +
			"\r\n" + 			

			"from (select * from cm_schedule_dl where status != 'I') a\r\n" + 
			"left join (select * from cm_schedule_hd where status_id != 'I') d on a.pbl_id = d.pbl_id " +
			"	and a.seq_no=d.seq_no and a.agent_code=d.agent_code and a.frequency::int = d.frequency  \r\n" + 
			"left join rf_entity e on d.agent_code = e.entity_id\r\n" + 
			"left join rf_entity g on a.agent_code = g.entity_id\r\n" + 
			"left join mf_sales_position f on d.orig_position  = f.position_code\r\n" + 
			"left join cm_cdf_hd h on a.old_cdfno = h.cdf_no \r\n" + 
			"\r\n" + 
			"\r\n" + 
			"where a.pbl_id = '"+pbl_id+"' \r\n" + 
			"and a.seq_no = "+seq_no+"   \n" +
			"and a.frequency::int = "+freq+"  \n" +
			"\r\n" + 
			"order by a.rec_id, d.rate, e.entity_name, a.comm_type\r\n" + 
			"" ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalAllComm(modelMain, modelTotal);	
			enableButtons(true, false, false, true);
		}		


		else {			

			JOptionPane.showMessageDialog(getContentPane(),"Selected unit does not \n" +
					"have new commission schedule.","Warning",JOptionPane.WARNING_MESSAGE);
			enableButtons(false, false, false, true);

			modelNew_CommSched_total = new modelComm_Sched_Transfer();
			modelNew_CommSched_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00), new BigDecimal(0.00), null, null });

			tblNew_CommSched_total = new _JTableTotal(modelNew_CommSched_total, tblNew_CommSched);
			tblNew_CommSched_total.setFont(dialog11Bold);
			scrollNew_CommSchedtotal.setViewportView(tblNew_CommSched_total);
			((_JTableTotal) tblNew_CommSched_total).setTotalLabel(0);}

		tblNew_CommSched.packAll();		
		adjustRowHeight_account(tblNew_CommSched);
	}

	private void displayClient_Request(DefaultTableModel modelMain, JList rowHeader) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select \r\n" + 
			"\r\n" + 
			"a.byrstatus_id,\r\n" + 
			"b.status_desc,\r\n" + 
			"a.tran_date,\r\n" + 
			"a.actual_date,\r\n" + 
			"a.request_no,\r\n" + 
			"a.status_id\r\n" + 
			"\r\n" + 
			"from \r\n" + 
			"\r\n" + 
			"rf_buyer_status a\r\n" + 
			"left join mf_buyer_status b on a.byrstatus_id = b.byrstatus_id\r\n" + 
			"\r\n" + 
			"where a.pbl_id = '"+pbl_id+"' \r\n" + 
			"and a.seq_no = "+seq_no+"   \n" +			
			//"and request_no is not null \r\n" +   04-01-2016 - as requested by FAD to include all buyer status
			"\r\n" + 
			"order by tran_date desc"  ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}				
		}		


		else {			

			JOptionPane.showMessageDialog(getContentPane(),"Selected unit does not \n" +
					"have client request.","Warning",JOptionPane.WARNING_MESSAGE);
		}

		tblClientRequest.packAll();		
		adjustRowHeight_account(tblClientRequest);
	}

	private void viewClientRequest(){

		displayClient_Request(modelView_clientRequest, rowHeaderClient_request);		

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlClientRequest, "Client Request History",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);	


		if ( scanOption == JOptionPane.CLOSED_OPTION ) {

		} // CLOSED_OPTION

	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("Cancel")){cancel();}

		if (FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true){

			if(e.getActionCommand().equals("AutoTransfer"))
			{
				double total_rlsd_amt = Double.parseDouble(modelOld_CommSched_total.getValueAt(0,5).toString());

				if (total_rlsd_amt == 0.00)
				{
					JOptionPane.showMessageDialog(getContentPane(),"There is no commission for transfer.","", JOptionPane.INFORMATION_MESSAGE);	
					//modelNew_CommSched.setEditable(false); 04-01-2016 - as requested by FAD to disable editing
				}
				else 
				{
					distributeRlsdAmt();
					enableButtons(false, true, false, true);
					//modelNew_CommSched.setEditable(true); 04-01-2016 - as requested by FAD to disable editing
				}				
			}

			if(e.getActionCommand().equals("Save")) {saveCommTransfer();}

			if(e.getActionCommand().equals("view")) {viewClientRequest();}
			
			if(e.getActionCommand().equals("Refresh")) { refreshTable();}

		}		

		else if(FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false)
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to change commission schedule.","Warning",JOptionPane.WARNING_MESSAGE); }

	}

	public void mouseClicked(MouseEvent evt) {
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

	private void cancel(){

		if (btnSave.isEnabled()) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				execute_cancel();
			}
		}

		else {	execute_cancel(); 	}

		modelNew_CommSched.setEditable((false));		
	}

	private void execute_cancel(){

		enableUnitDetails(false);
		enableButtons(false, false, false, false);

		pbl_id = "";
		seq_no = null ;		
		lookupUnit.setText(pbl_id);
		tagUnit.setTag("");

		entity_id = "";
		txtClient.setText(entity_id);
		tagClientName.setTag("");

		status_id = "";
		txtStatus.setText(status_id);
		tagStatus.setTag("");

		nsp	= Double.parseDouble("0.00");
		txtNSP.setValue(nsp);

		txtCommRate.setValue(Double.parseDouble("0.00"));		

		txtSalesType.setText("");	

		refreshTables();
	}

	class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				Integer x = tblClientRequest.getSelectedRow();
				try { request_no = modelView_clientRequest.getValueAt(x,4).toString();} 
					catch (NullPointerException e) { request_no = ""; }
				
				if (request_no.equals(""))
				{
					mniView.setEnabled(false);
				}
				else
				{
					mniView.setEnabled(true);
				}
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				Integer x = tblClientRequest.getSelectedRow();
				try { request_no = modelView_clientRequest.getValueAt(x,4).toString();} 
				catch (NullPointerException e) { request_no = ""; }
				
				if (request_no.equals(""))
				{
					mniView.setEnabled(false);
				}
				else
				{
					mniView.setEnabled(true);
				}
			}
		}
	}

	private void refreshTable()	{
		
		refreshTables();

		comm_freq = getCommFrequency();
		System.out.printf("comm_freq: " + comm_freq);


		displayClient_Old_Commission(modelOld_CommSched, rowHeaderOld_CommSched, modelOld_CommSched_total, comm_freq - 1);		


		if (sql_getPrevComm()>0.00)
		{
			displayClient_Transfered_Commission(modelNew_CommSched, rowHeaderNew_CommSched, modelNew_CommSched_total,comm_freq );
			JOptionPane.showMessageDialog(getContentPane(),"<html><b><i>Commission Schedule <html></b></i>" +
					"<html>has been already transfered.<html>","", JOptionPane.INFORMATION_MESSAGE);	
			enableButtons(false, false, false, true);												
		}
		else 
		{
			displayClient_New_Commission(modelNew_CommSched, rowHeaderNew_CommSched, modelNew_CommSched_total,comm_freq );
			enableButtons(true, false, true, true);												
		}
		
		JOptionPane.showMessageDialog(getContentPane(),"Commission tables refreshed.","Warning",JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	//enable, disable

	private void enableUnitDetails(boolean x){

		lblClient.setEnabled(x);	
		txtClient.setEnabled(x);	
		tagClientName.setEnabled(x);	
		lblStatus.setEnabled(x);	
		txtStatus.setEnabled(x);	
		tagStatus.setEnabled(x);	
		lblNSP.setEnabled(x);	
		txtNSP.setEnabled(x);
		lblCommType.setEnabled(x);	
		txtSalesType.setEnabled(x);	
		lblCommRate.setEnabled(x);	
		txtNSP.setEnabled(x);
		txtCommRate.setEnabled(x);	

		lblRequest.setEnabled(x);			
		tagRequest.setEnabled(x);	

		lblCommAmt.setEnabled(x);	
		txtCommAmt.setEnabled(x);
		btnViewRequest.setEnabled(x);
	}

	public void enableButtons( Boolean a, Boolean b, Boolean c, Boolean d){
		btnAutoTransfer.setEnabled(a);
		btnSave.setEnabled(b);
		btnRefresh.setEnabled(c);
		btnCancel.setEnabled(d);			
	}

	private void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();	
		lookupCompany.setValue(co_id);
	}



	//select, lookup and get statements	
	private static String getEntityList(){

		String sql = "select \r\n" + 
		"\r\n" + 

		"a.pbl_id  as \"PBL\", " +				//0
		"a.seq_no as \"Seq. No.\"," +			//1
		"e.description as \"Unit\",\r\n" + 		//2		
		"a.account_code as \"Acct. Code\" ,\r\n" + //3
		"trim(c.entity_name) as \"Client Name\",\r\n" +  //4
		"f.status_id as \"Status ID\", \r\n" + 	//5	
		"j.status_desc as \"Status Desc\", \r\n" + 	//6	
		"k.net_sprice as \"NSP\",\r\n" + 		//7	
		"(case when l.salestype_id = '001' then 'In-House' else 'External' end) as \"Comm. Type\",  \n" +   //8
		"d.proj_alias,\r\n" + 	//9
		"i.is_rowhouse,\r\n" +  //10
		"l.salestype_id ,\r\n" + //11
		"(case when i.is_rowhouse = true and l.salestype_id = '001' then 6.00 else \r\n" +  //12
		"			case when i.is_rowhouse = true and l.salestype_id = '002' then 7.00 else\r\n" + 
		"			case when i.is_rowhouse is not true and l.salestype_id = '001' then 5.00 else\r\n" + 
		"			case when i.is_rowhouse is not true  and l.salestype_id = '002' then 6.00 else\r\n" + 
		"			0.00			\r\n" + 
		"			end end end end \r\n" + 
		"		) as comm_rate " +	


		"\r\n" + 
		"from ( select distinct on ( account_code, pbl_id, seq_no) account_code, proj_id, pbl_id, seq_no, agent_code from cm_schedule_hd where status_id = 'A' ) a\r\n" + 
		"left join rf_entity c on a.account_code = c.entity_id\r\n" + 
		"left join mf_project d on a.proj_id = d.proj_id\r\n" + 
		"left join mf_unit_info e on a.pbl_id = e.pbl_id\r\n" + 
		"left join (select * from rf_sold_unit where status_id != 'I') f on a.pbl_id = f.pbl_id and a.seq_no = f.seq_no\r\n" + 
		//"left join ( select pmt_scheme_id, unnest(type_id) as type_id from mf_payment_scheme ) g on f.pmt_scheme_id = g.pmt_scheme_id\r\n" + 		
		"left join mf_product_model i on f.model_id = i.model_id \n" +
		"left join mf_record_status j on  f.status_id = j.status_id \n" +
		"left join rf_client_price_history k on a.proj_id = k.proj_id and a.pbl_id = k.pbl_id and a.seq_no = k.seq_no  \n" +
		"left join mf_sellingagent_info l on f.sellingagent = l.agent_id \n\n";		
		return sql;

	}

	private Integer getCommFrequency() {//used

		String strSQL = 			
			"select max(coalesce(frequency::int,0)) from cm_schedule_dl where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+"  ";		

		System.out.printf("SQL - getCommFrequency: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return (Integer) db.getResult()[0][0];
		}else{
			return 1;
		}
	}

	private Double sql_getPrevComm() {

		double prev_comm = 0.00;

		String SQL = 
			"select sum(sked_amt) from cm_schedule_dl where pbl_id = '"+pbl_id+"' and seq_no = "+seq_no+" and status != 'I' \r\n" ;

		System.out.printf("SQL - sql_getPrevComm : " + SQL + "\n");
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){			
			if(db.getResult()[0][0].toString()==null||db.getResult()[0][0].equals("null")) {prev_comm = 0.00;}
			else {prev_comm = Double.parseDouble(db.getResult()[0][0].toString()); }	
		}else{
			prev_comm = 0.00;
		}

		return prev_comm;
	}



	//table operations	
	private void totalAllComm(DefaultTableModel modelMain, DefaultTableModel modelTotal) {//ok

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal prev_comm 	= new BigDecimal(0.00);	
		BigDecimal rlsd_amt		= new BigDecimal(0.00);	
		BigDecimal sked_amt		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { prev_comm 	= prev_comm.add(((BigDecimal) modelMain.getValueAt(x,4)));} 
			catch (NullPointerException e) { prev_comm 	= prev_comm.add(new BigDecimal(0.00)); }

			try { rlsd_amt 	= rlsd_amt.add(((BigDecimal) modelMain.getValueAt(x,5)));} 
			catch (NullPointerException e) { rlsd_amt 	= rlsd_amt.add(new BigDecimal(0.00)); }

			try { sked_amt 	= sked_amt.add(((BigDecimal) modelMain.getValueAt(x,6)));} 
			catch (NullPointerException e) { sked_amt 	= sked_amt.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, null, null, prev_comm , rlsd_amt,  sked_amt, null });		
	}

	public static void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}

	private void refreshTables(){

		FncTables.clearTable(modelNew_CommSched);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderNew_CommSched.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		modelNew_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 4);
		modelNew_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 5);
		modelNew_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 6);

		FncTables.clearTable(modelOld_CommSched);//Code to clear modelMain.		
		DefaultListModel listModel2 = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderOld_CommSched.setModel(listModel2);//Setting of DefaultListModel into rowHeader.
		modelOld_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 4);
		modelOld_CommSched_total.setValueAt(new BigDecimal(0.00), 0, 5);

	}

	private void computeRlsdCommTotal(){

		int rw = tblNew_CommSched.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {	

			Double amount	= 0.00;
			try { amount	= Double.parseDouble(modelNew_CommSched.getValueAt(x,5).toString()); } catch (NullPointerException e) { amount	= 0.00; }
			double gr_amt 	= amount;
			BigDecimal grossAmt_bd 	= new BigDecimal(gr_amt);
			modelNew_CommSched.setValueAt(grossAmt_bd, x, 5);

			x++;
		}		

		totalAllComm(modelNew_CommSched, modelNew_CommSched_total);	
	}



	//save
	private void saveCommTransfer(){

		if(checkCommissionVariance()==false)
		{JOptionPane.showMessageDialog(getContentPane(), 
				"<html><b>Total Released Commission </b><html>" +
				"<html><plaintext>does not tally with the </plaintext><html>" + "\n" +
				"<html><b>Total Transferred Commission </b><html>", "Variance Error", 
				JOptionPane.ERROR_MESSAGE);}
		
		else {			

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to transfer " + "\n" +
					"commission to new schedule?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();	

				Integer row_number = modelNew_CommSched.getRowCount() ;
				Integer x = 0;		

				while (x<row_number)
				{		

					String rec_id		= modelNew_CommSched.getValueAt(x,11).toString();		
					String cdf_no		= "";
					try { cdf_no = modelNew_CommSched.getValueAt(x,8).toString(); } catch (NullPointerException e) {cdf_no = ""; }			

					Double rlsd_amt 	= Double.parseDouble(modelNew_CommSched.getValueAt(x,5).toString());	
					Double new_sched_amt = Double.parseDouble(modelNew_CommSched.getValueAt(x,6).toString());	
					Double prev_comm_amt= rlsd_amt + new_sched_amt;

					String cdf_status_str	= modelNew_CommSched.getValueAt(x,9).toString();
					String cdf_status_code	= "";
					if (cdf_status_str.equals("Not Yet Qualified")){cdf_status_code = "A";} else {cdf_status_code = "P";}

					String sqlDetail = 
						"update cm_schedule_dl " +
						"set prev_commamt = "+prev_comm_amt+",  \n" +
						"sked_amt = "+rlsd_amt+",  \n" +
						"comm_amt = "+new_sched_amt+",  \n" +
						"status = '"+cdf_status_code+"', \n" ;

					if (rlsd_amt>0.00) { sqlDetail = sqlDetail + "remarks = 'Transferred Commission; ' + " +
						" '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"', "; }					
					sqlDetail = sqlDetail +

					"old_cdfno = '"+cdf_no+"' \n" +						
					"where rec_id = "+rec_id+" \n\n" ;

					System.out.printf("SQL - saveCommTransfer : " + sqlDetail);
					db.executeUpdate(sqlDetail, false);				

					x++;
				}

				db.commit();
				JOptionPane.showMessageDialog(getContentPane(),"<html><b><i>New Commission Schedule <html></b></i>" +
						"<html>successfully transfered.<html>","", JOptionPane.INFORMATION_MESSAGE);		

				enableButtons(false, true, false, true);
			}
		}
	}



	//computation
	private void distributeRlsdAmt(){

		Integer row_number_old = modelOld_CommSched.getRowCount() ;
		Integer row_old = 0;		

		while (row_old < row_number_old)

		{
			double running_rlsd_amt = Double.parseDouble(modelOld_CommSched.getValueAt(row_old,5).toString());

			if (running_rlsd_amt==0.00){}

			else 
			{				
				String cdf_date		= modelOld_CommSched.getValueAt(row_old,7).toString();		
				String cdf_no 		= modelOld_CommSched.getValueAt(row_old,8).toString();
				String cdf_status 	= modelOld_CommSched.getValueAt(row_old,9).toString();


				double rlsd_amt  = 0.00 ;
				String comm_type = "";
				System.out.printf("released_amt :"+ rlsd_amt );

				Integer row_number = modelNew_CommSched.getRowCount() ;

				String comm [] = {"1st CA", "2nd CA", "3rd CA", "4th CA", "5th CA", "Full Comm", "Promo" };
				Integer x = 0;

				while (x<7)
				{	
					Integer row = 0;			
					while (row<row_number)

					{			
						comm_type 				= modelNew_CommSched.getValueAt(row,3).toString();
						Double sched_amt  		= Double.parseDouble(modelNew_CommSched.getValueAt(row,4).toString());	
						Double current_rlsd_amt = Double.parseDouble(modelNew_CommSched.getValueAt(row,5).toString());	
						Double new_sched_amt  	= Double.parseDouble(modelNew_CommSched.getValueAt(row,6).toString());

						Double diff 			= sched_amt - current_rlsd_amt;  //if this is >0, then pwede pa idistribute sa row nato

						System.out.printf("comm : " + comm[x] + "\n");
						System.out.printf("comm_type : " + comm_type + "\n");

						if (comm[x].equals(comm_type) && diff>0 )

						{									
							Double new_rlsd_amt  = 0.00 ;			

							if (running_rlsd_amt >= diff && running_rlsd_amt > 0.00)
							{
								new_rlsd_amt = current_rlsd_amt + diff;								
								BigDecimal rlsd_amt_bd = new BigDecimal(new_rlsd_amt);
								BigDecimal new_sched_bd = new BigDecimal(0.00);												
								modelNew_CommSched.setValueAt(rlsd_amt_bd, row, 5);
								modelNew_CommSched.setValueAt(new_sched_bd, row, 6);	
								modelNew_CommSched.setValueAt(cdf_date, row, 7);
								modelNew_CommSched.setValueAt(cdf_no, row, 8);	
								modelNew_CommSched.setValueAt(cdf_status, row, 9);	
								running_rlsd_amt = running_rlsd_amt - diff;  //deduct from the running balance the amount distributed
							}

							else if (running_rlsd_amt < diff && running_rlsd_amt > 0.00)
							{
								new_rlsd_amt  = current_rlsd_amt + running_rlsd_amt;	
								new_sched_amt = sched_amt - new_rlsd_amt;	
								BigDecimal rlsd_amt_bd = new BigDecimal(new_rlsd_amt);
								BigDecimal new_sched_bd = new BigDecimal(new_sched_amt);													
								modelNew_CommSched.setValueAt(rlsd_amt_bd, row, 5);
								modelNew_CommSched.setValueAt(new_sched_bd, row, 6);
								modelNew_CommSched.setValueAt(cdf_date, row, 7);
								modelNew_CommSched.setValueAt(cdf_no, row, 8);	
								//modelNew_CommSched.setValueAt(cdf_status, row, 9);	
								running_rlsd_amt = running_rlsd_amt - new_rlsd_amt;
							}		

							else 
							{		
								BigDecimal new_sched_bd = new BigDecimal(sched_amt);
								modelNew_CommSched.setValueAt(new_sched_bd, row, 6);	
							}
						}

						row++;
					}

					x++;
				}
			}

			row_old ++; 
		}

		totalAllComm(modelNew_CommSched, modelNew_CommSched_total);	
		tblNew_CommSched.packAll();	
	}

	private Boolean checkCommissionVariance(){

		boolean x = false;		

		Double rlsd_amt 		= Double.parseDouble(modelOld_CommSched_total.getValueAt(0,5).toString());	
		Double ditributed_amt 	= Double.parseDouble(modelNew_CommSched_total.getValueAt(0,5).toString());
		
		if (rlsd_amt.equals(ditributed_amt)) {x=true;} 
		else {x=false;}		

		return x;
	}

}
