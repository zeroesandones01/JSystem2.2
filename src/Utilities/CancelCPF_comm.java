package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXApplet;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelCancelCPF;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncAcounting;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class CancelCPF_comm extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "CPF Cancellation";
	static Dimension size = new Dimension(600, 600);

	private modelCancelCPF modelComm_CommSched;
	private modelCancelCPF modelComm_CommSched_total;

	private static _JLookup lookupCompany;
	private static _JLookup lookupCPFno;

	private static JTextField txtCompany;
	private static JTextField txtCPFStatus;
	private static JTextField txtRPLFno;
	private static JTextField txtRPLFStatus;
	private static JTextField txtPVno;
	private static JTextField txtPVStatus;
	private static JTextField txtCVno;
	private static JTextField txtCVStatus;
	private static JTextArea txtReasonCanc;

	private static JFormattedTextField txtAmount; 

	private static _JDateChooser dteDate; 

	private static JButton btnCancel;
	private static JButton btnInactivate;

	private _JScrollPaneMain scrollComm_CommSched;
	private _JScrollPaneTotal scrollComm_CommSchedtotal;
	private _JTableMain tblComm_CommSched;
	private JList rowHeaderComm_CommSched;
	private _JTableTotal tblComm_CommSched_total;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private Font font10 = FncLookAndFeel.systemFont_Plain.deriveFont(10f);

	public CancelCPF_comm() {
		super(title, true, true, true, true);
		initGUI();
	}

	public CancelCPF_comm(String title) {
		super(title);

	}

	public CancelCPF_comm(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(size);
		this.setPreferredSize(size);
		this.setMinimumSize(size);

		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(new Dimension(1134, 645));
		panMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panDetails = new JXPanel(new GridLayout(4, 1, 5, 5)); 
				panMain.add(panDetails, BorderLayout.PAGE_START); 
				panDetails.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				panDetails.setPreferredSize(new Dimension(0, 150));
				{
					{
						JXPanel panComp = new JXPanel(new BorderLayout(5, 5)); 
						panDetails.add(panComp); 
						{
							{
								JLabel lblComp = new JLabel("Company"); 
								panComp.add(lblComp, BorderLayout.LINE_START);
								lblComp.setPreferredSize(new Dimension(75, 0));
								lblComp.setFont(font11);
							}
							{
								JXPanel panBox = new JXPanel(new BorderLayout(5, 5)); 
								panComp.add(panBox, BorderLayout.CENTER); 
								{
									{
										lookupCompany = new _JLookup(""); 
										panBox.add(lookupCompany, BorderLayout.LINE_START); 
										lookupCompany.setReturnColumn(0);
										lookupCompany.setLookupSQL(lookupMethods.getCompany());
										lookupCompany.setHorizontalAlignment(_JLookup.CENTER);
										lookupCompany.setPreferredSize(new Dimension(97, 0));
										lookupCompany.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet(); 

												if (data!=null) {
													txtCompany.setText(data[1].toString());
												}
											}
										});
										lookupCompany.setFont(font11);
									}
									{
										txtCompany = new JTextField(""); 
										panBox.add(txtCompany, BorderLayout.CENTER); 
										txtCompany.setHorizontalAlignment(JTextField.CENTER);
										txtCompany.setEditable(false);
										txtCompany.setFont(font11);
									}
								}
							}
						}
					}
					{
						JXPanel panDiv1 = new JXPanel(new GridLayout(1, 2, 5, 5)); 
						panDetails.add(panDiv1); 
						{
							{
								JXPanel panCPF = new JXPanel(new BorderLayout(5, 5)); 
								panDiv1.add(panCPF); 
								{
									{
										JLabel lblCPF = new JLabel("CPF"); 
										panCPF.add(lblCPF, BorderLayout.LINE_START);
										lblCPF.setHorizontalAlignment(JLabel.LEADING);
										lblCPF.setPreferredSize(new Dimension(75, 0));
										lblCPF.setFont(font11);
									}
									{
										JXPanel panBox = new JXPanel(new GridLayout(1, 2, 5, 5)); 
										panCPF.add(panBox, BorderLayout.CENTER); 
										{
											{
												lookupCPFno = new _JLookup(""); 
												panBox.add(lookupCPFno); 
												lookupCPFno.setReturnColumn(0);

												lookupCPFno.setHorizontalAlignment(_JLookup.CENTER);
												lookupCPFno.addFocusListener(new FocusListener() {

													@Override
													public void focusLost(FocusEvent e) {
														// TODO Auto-generated method stub

													}

													@Override
													public void focusGained(FocusEvent e) {
														lookupCPFno.setLookupSQL(lookupMethods.getCPF(lookupCompany.getValue()));

													}
												});
												lookupCPFno.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup)event.getSource()).getDataSet();
														if(data != null){	
															refresh();
															FncSystem.out("Display SQL for lookupCPF", lookupCPFno.getLookupSQL());
															lookupCPFno.setValue((String) data[0]);
															detailCPF((String) data[0]); 

															displayAgentPromo(modelComm_CommSched, rowHeaderComm_CommSched, modelComm_CommSched_total);
															SetAction(cancellationAccessMatrix(UserInfo.EmployeeCode));
														}
													}
												});
												lookupCPFno.setFont(font11);
											}
											{
												txtCPFStatus = new JTextField(""); 
												panBox.add(txtCPFStatus); 
												txtCPFStatus.setHorizontalAlignment(JTextField.CENTER);
												txtCPFStatus.setEditable(false);
												txtCPFStatus.setFont(font11);
											}
										}
									}
								}
							}
							{
								JXPanel panRPLF = new JXPanel(new BorderLayout(5, 5)); 
								panDiv1.add(panRPLF); 
								{
									{
										JLabel lblRPLF = new JLabel("RPLF"); 
										panRPLF.add(lblRPLF, BorderLayout.LINE_START);
										lblRPLF.setHorizontalAlignment(JLabel.CENTER);
										lblRPLF.setPreferredSize(new Dimension(100, 0));
										lblRPLF.setFont(font11);
									}
									{
										JXPanel panBox = new JXPanel(new GridLayout(1, 2, 5, 5)); 
										panRPLF.add(panBox, BorderLayout.CENTER); 
										{
											{
												txtRPLFno = new JTextField(""); 
												panBox.add(txtRPLFno);
												txtRPLFno.setHorizontalAlignment(JLabel.CENTER);
												txtRPLFno.setFont(font11);
											}
											{
												txtRPLFStatus = new JTextField(""); 
												panBox.add(txtRPLFStatus); 
												txtRPLFStatus.setHorizontalAlignment(JTextField.CENTER);
												txtRPLFStatus.setEditable(false);
												txtRPLFStatus.setFont(font11);
											}
										}
									}
								}
							}
						}
					}
					{
						JXPanel panDiv2 = new JXPanel(new GridLayout(1, 2, 5, 5)); 
						panDetails.add(panDiv2); 
						{
							{
								JXPanel panPV = new JXPanel(new BorderLayout(5, 5)); 
								panDiv2.add(panPV); 
								{
									{
										JLabel lblPV = new JLabel("PV"); 
										panPV.add(lblPV, BorderLayout.LINE_START);
										lblPV.setHorizontalAlignment(JLabel.LEADING);
										lblPV.setPreferredSize(new Dimension(75, 0));
										lblPV.setFont(font11);
									}
									{
										JXPanel panBox = new JXPanel(new GridLayout(1, 2, 5, 5)); 
										panPV.add(panBox, BorderLayout.CENTER); 
										{
											{
												txtPVno = new JTextField(""); 
												panBox.add(txtPVno);
												txtPVno.setHorizontalAlignment(JLabel.CENTER);
												txtPVno.setFont(font11);
											}
											{
												txtPVStatus = new JTextField(""); 
												panBox.add(txtPVStatus); 
												txtPVStatus.setHorizontalAlignment(JTextField.CENTER);
												txtPVStatus.setEditable(false);
												txtPVStatus.setFont(font11);
											}
										}
									}
								}
							}
							{
								JXPanel panCV = new JXPanel(new BorderLayout(5, 5)); 
								panDiv2.add(panCV); 
								{
									{
										JLabel lblCV = new JLabel("CV"); 
										panCV.add(lblCV, BorderLayout.LINE_START);
										lblCV.setHorizontalAlignment(JLabel.CENTER);
										lblCV.setPreferredSize(new Dimension(100, 0));
										lblCV.setFont(font11);
									}
									{
										JXPanel panBox = new JXPanel(new GridLayout(1, 2, 5, 5)); 
										panCV.add(panBox, BorderLayout.CENTER); 
										{
											{
												txtCVno = new JTextField(""); 
												panBox.add(txtCVno);
												txtCVno.setHorizontalAlignment(JLabel.CENTER);
												txtCVno.setFont(font11);
											}
											{
												txtCVStatus = new JTextField(""); 
												panBox.add(txtCVStatus); 
												txtCVStatus.setHorizontalAlignment(JTextField.CENTER);
												txtCVStatus.setEditable(false);
												txtCVStatus.setFont(font11);
											}
										}
									}
								}
							}
						}
					}
					{
						JXPanel panDiv3 = new JXPanel(new GridLayout(1, 2, 5, 5)); 
						panDetails.add(panDiv3); 
						{
							{
								JXPanel panAmount = new JXPanel(new BorderLayout(5, 5)); 
								panDiv3.add(panAmount); 
								{
									{
										JLabel lblAmount = new JLabel("Amount"); 
										panAmount.add(lblAmount, BorderLayout.LINE_START);
										lblAmount.setHorizontalAlignment(JLabel.LEADING);
										lblAmount.setPreferredSize(new Dimension(75, 0));
										lblAmount.setFont(font11);
									}
									{
										txtAmount = new _JXFormattedTextField("0.00");
										panAmount.add(txtAmount, BorderLayout.CENTER);
										txtAmount.setHorizontalAlignment(JTextField.TRAILING);
										txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtAmount.setEditable(false);
										txtAmount.setFont(font11);
									}
								}
							}
							{
								JXPanel panDate = new JXPanel(new BorderLayout(5, 5)); 
								panDiv3.add(panDate); 
								{
									{
										JLabel lblDate = new JLabel("Date Cancelled"); 
										panDate.add(lblDate, BorderLayout.LINE_START);
										lblDate.setHorizontalAlignment(JLabel.CENTER); 
										lblDate.setPreferredSize(new Dimension(100, 0));
										lblDate.setFont(font10);
									}
									{
										dteDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										panDate.add(dteDate);
										dteDate.getCalendarButton().setVisible(false);
										dteDate.setEditable(false);
										dteDate.setFont(font11);
									}
								}
							}
						}
					}
				}
			}
		}
		{
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
			panMain.add(panCenter, BorderLayout.CENTER);
			panCenter.setBorder(JTBorderFactory.createTitleBorder("Promo/Incentive Manually Entered under CPF", FncLookAndFeel.systemFont_Bold.deriveFont(10f))); 
			{
				{
					JXPanel panTable = new JXPanel(new BorderLayout(5, 5)); 
					panCenter.add(panTable, BorderLayout.CENTER); 
					{
						{
							scrollComm_CommSched = new _JScrollPaneMain();
							panTable.add(scrollComm_CommSched, BorderLayout.CENTER);
							{
								modelComm_CommSched = new modelCancelCPF();

								tblComm_CommSched = new _JTableMain(modelComm_CommSched);
								scrollComm_CommSched.setViewportView(tblComm_CommSched);							
								tblComm_CommSched.setSortable(false);
								tblComm_CommSched.getColumnModel().getColumn(0).setPreferredWidth(40);
								tblComm_CommSched.getColumnModel().getColumn(1).setPreferredWidth(300);
								tblComm_CommSched.getColumnModel().getColumn(2).setPreferredWidth(80);
								tblComm_CommSched.getColumnModel().getColumn(3).setPreferredWidth(300);
								tblComm_CommSched.getColumnModel().getColumn(4).setPreferredWidth(80);
								tblComm_CommSched.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent evt) {

									}

									public void keyPressed(KeyEvent e) {

									}
								});
								tblComm_CommSched.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if(tblComm_CommSched.rowAtPoint(e.getPoint()) == -1) {

										} else{
											tblComm_CommSched.setCellSelectionEnabled(true);
										}	
									}
									public void mouseReleased(MouseEvent e) {
										if(tblComm_CommSched.rowAtPoint(e.getPoint()) == -1) {

										} else {
											tblComm_CommSched.setCellSelectionEnabled(true);
										}
									}
								});
							}
							{
								rowHeaderComm_CommSched = tblComm_CommSched.getRowHeader22();
								scrollComm_CommSched.setRowHeaderView(rowHeaderComm_CommSched);
								scrollComm_CommSched.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							scrollComm_CommSchedtotal = new _JScrollPaneTotal(scrollComm_CommSched);
							panTable.add(scrollComm_CommSchedtotal, BorderLayout.SOUTH);
							{
								modelComm_CommSched_total = new modelCancelCPF();
								modelComm_CommSched_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00)});

								tblComm_CommSched_total = new _JTableTotal(modelComm_CommSched_total, tblComm_CommSched);
								tblComm_CommSched_total.setFont(dialog11Bold);
								scrollComm_CommSchedtotal.setViewportView(tblComm_CommSched_total);
								((_JTableTotal) tblComm_CommSched_total).setTotalLabel(0);
							}
						}
					}
				}
				{
					JXPanel panReason = new JXPanel(new BorderLayout(5, 10)); 
					panCenter.add(panReason, BorderLayout.PAGE_END); 
					panReason.setPreferredSize(new Dimension(0, 50));
					{
						{
							JLabel lblReason = new JLabel("Reason for cancellation"); 
							panReason.add(lblReason, BorderLayout.LINE_START); 
							lblReason.setHorizontalAlignment(JLabel.LEADING);
							lblReason.setVerticalAlignment(JLabel.CENTER);
							lblReason.setFont(font11);
						}

						{
							txtReasonCanc = new JTextArea(""); 
							panReason.add(txtReasonCanc, BorderLayout.CENTER); 
							txtReasonCanc.setFont(new Font("Tahoma", Font.PLAIN, 12));
							txtReasonCanc.setLineWrap(true);
							txtReasonCanc.setWrapStyleWord(true);
							txtReasonCanc.setEditable(false);

							JScrollPane scrMessage = new JScrollPane(txtReasonCanc);
							panReason.add(scrMessage, BorderLayout.CENTER); 
						}
					}
				}
			}
		}
		{
			btnInactivate = new JButton("");
			panMain.add(btnInactivate, BorderLayout.PAGE_END);
			btnInactivate.setActionCommand("Deactivate");
			btnInactivate.addActionListener(this);
			btnInactivate.setEnabled(false);
			btnInactivate.setPreferredSize(new Dimension(0, 30));
		}

		initialize_comp();
	}

	public void displayAgentPromo(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String sql = "select true, upper(trim(c.entity_name)) as agent, upper(trim(cc.entity_name)) as client_name, \n" + 
				"trim(d.description) as unit, a.tran_type, b.cdf_date, trim(e.promo_desc) as promo, a.applied_amt \n" + 
				"from cm_cdf_dl a \n" + 
				"left join cm_cdf_hd b on a.cdf_no = b.cdf_no \n" + 
				"left join rf_entity c on b.agent_code = c.entity_id  \n" + 
				"left join rf_entity cc on a.account_code = cc.entity_id \n" + 
				"left join mf_unit_info d on a.projcode = d.proj_id and a.pbl_id = d.pbl_id \n" + 
				"left join cm_promo_bonus e on a.promo_code = e.promo_code \n" + 
				"where a.cdf_no = '"+lookupCPFno.getValue()+"'";  

		System.out.println(sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalAllComm(modelMain, modelTotal);			
		} else {
			modelComm_CommSched_total = new modelCancelCPF();
			modelComm_CommSched_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00)});

			tblComm_CommSched_total = new _JTableTotal(modelComm_CommSched_total, tblComm_CommSched);
			tblComm_CommSched_total.setFont(dialog11Bold);
			scrollComm_CommSchedtotal.setViewportView(tblComm_CommSched_total);
			((_JTableTotal) tblComm_CommSched_total).setTotalLabel(0);
		}

		tblComm_CommSched.packAll();		
		tblComm_CommSched.getColumnModel().getColumn(0).setPreferredWidth(40);
		tblComm_CommSched.getColumnModel().getColumn(1).setPreferredWidth(250);
		tblComm_CommSched.getColumnModel().getColumn(2).setPreferredWidth(250);
		tblComm_CommSched.getColumnModel().getColumn(3).setPreferredWidth(120);
		tblComm_CommSched.getColumnModel().getColumn(4).setPreferredWidth(80);
		tblComm_CommSched.getColumnModel().getColumn(5).setPreferredWidth(80);
		tblComm_CommSched.getColumnModel().getColumn(6).setPreferredWidth(150);
		tblComm_CommSched.getColumnModel().getColumn(7).setPreferredWidth(80);

		adjustRowHeight_account(tblComm_CommSched);
	}	

	private void refresh() {
		lookupCPFno.setValue("");
		txtCPFStatus.setText("");

		txtRPLFno.setText("");
		txtRPLFStatus.setText("");

		txtPVno.setText("");
		txtPVStatus.setText("");

		txtCVno.setText("");
		txtCVStatus.setText("");

		txtAmount.setValue(new BigDecimal("0.00"));
		dteDate.setDate(null);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel")) {
			cancel();
		}

		if (e.getActionCommand().equals("Activate")) {
			save("A");
			detailCPF(lookupCPFno.getValue());
		}

		if (e.getActionCommand().equals("Deactivate")) {
			save("I");
			detailCPF(lookupCPFno.getValue());
		}
	}

	private void cancel(){
		if (checkTaggedAccountBeforeCancel()==true) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				execute_cancel();
			}
		} else {
			execute_cancel();
		}
	}

	private void execute_cancel(){
		txtRPLFno.setEnabled(false);	
		txtReasonCanc.setEnabled(false);	

		lookupCPFno.setValue("");
		txtRPLFno.setText("");
		txtPVno.setText("");
		txtCVno.setText("");
		txtReasonCanc.setText("");

		btnInactivate.setEnabled(false);
		btnCancel.setEnabled(false);

		FncTables.clearTable(modelComm_CommSched); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeaderComm_CommSched.setModel(listModel); 

		tblComm_CommSched.getColumnModel().getColumn(0).setPreferredWidth(40);
		tblComm_CommSched.getColumnModel().getColumn(1).setPreferredWidth(300);
		tblComm_CommSched.getColumnModel().getColumn(2).setPreferredWidth(80);
		tblComm_CommSched.getColumnModel().getColumn(3).setPreferredWidth(300);
		tblComm_CommSched.getColumnModel().getColumn(4).setPreferredWidth(80);
	}

	private void save(String status){

		if (txtReasonCanc.getText().equals("")) {
			JOptionPane.showMessageDialog(getContentPane(), "Please enter the reason for cancellation.", "Incomplete Detail", 
					JOptionPane.WARNING_MESSAGE);}
		else {	

			String remark = "";

			if (status.equals("A")) {
				remark = "Are you sure you want to activate this CDF?";
			} else {
				remark = "Are you sure you want to cancel this CDF?";
			}

			if (JOptionPane.showConfirmDialog(getContentPane(), remark, "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgUpdate db = new pgUpdate();				
				cancelCPF(db, status); 
				updateRPLF_header(db, status);
				updatePV_header(db, status);
				db.commit();

				if (status.equals("A")) {
					JOptionPane.showMessageDialog(getContentPane(),"Commission Payout Form (CPF) and payment request activated.","Information",JOptionPane.INFORMATION_MESSAGE);
					btnInactivate.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(getContentPane(),"Commission Payout Form (CPF) and payment request canceled.","Information",JOptionPane.INFORMATION_MESSAGE);
					btnInactivate.setEnabled(false);
				}			
			}
		}
	}

	public void initialize_comp(){		
		lookupCompany.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		lookupCPFno.setLookupSQL(lookupMethods.getCPF(lookupCompany.getValue()));
	}

	private void totalAllComm(DefaultTableModel modelMain, DefaultTableModel modelTotal) {
		FncTables.clearTable(modelTotal);
		BigDecimal net_comm = new BigDecimal(0.00);	

		try {
			for(int x=0; x < modelMain.getRowCount(); x++){		
				try {
					net_comm = net_comm.add(((BigDecimal) modelMain.getValueAt(x, 7)));
				} catch (NullPointerException e) {
					net_comm = net_comm.add(new BigDecimal(0.00));
				}
			}		

			modelTotal.addRow(new Object[] { "Total", null, null, null, net_comm});
		} catch (Exception ex) {
			System.out.print("Something went wrong!");
		}
	}

	private void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}

	private boolean checkTaggedAccountBeforeCancel() {

		boolean containsTaggedAcct = false;		

		for(int x=0; x < modelComm_CommSched.getRowCount(); x++){

			Boolean isTrue = false;
			if(modelComm_CommSched.getValueAt(x,0) instanceof String){
				isTrue = new Boolean((String)modelComm_CommSched.getValueAt(x,0));
			}
			if(modelComm_CommSched.getValueAt(x,0) instanceof Boolean){
				isTrue = (Boolean)modelComm_CommSched.getValueAt(x,0);
			}

			if(isTrue){					
				containsTaggedAcct=true;
				break;					
			}		
			else {}
		}
		return containsTaggedAcct;

	}

	private void cancelCPF(pgUpdate db, String status){

		String sqlDetail = 
				"update cm_cdf_hd set " +
						"status_id = '"+status+"'," +
						"remarks = '"+txtReasonCanc.getText().trim()+"', " +
						"canceled_by = '"+UserInfo.EmployeeCode+"', " +
						"canceled_date = '"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"' " +
						"where trim(cdf_no) = '"+lookupCPFno.getValue()+"' and co_id = '"+lookupCompany.getValue()+"'   " ;

		System.out.println(sqlDetail);
		db.executeUpdate(sqlDetail, false);				

		String sqlDetail2 = "update cm_schedule_dl \n" +
				"set status = 'A', cdf_no = null \n" +
				"where trim(cdf_no) = '"+lookupCPFno.getValue()+"' \n" +
				"and co_id = '"+lookupCompany.getValue()+"'   " ;

		System.out.println(sqlDetail2);
		db.executeUpdate(sqlDetail2, false);	

	}

	private void updateRPLF_header(pgUpdate db, String status) {
		String sqlDetail = ""; 

		if (status.equals("I")) {
			if (!FncGlobal.GetBoolean("select exists(select * from rf_pv_header where trim(pv_no) = '"+txtRPLFno.getText()+"' and co_id = '"+lookupCompany.getValue()+"' and status_id = 'P')")) {
				sqlDetail = "update rf_request_header \n" +
						"set status_id = '"+status+"' \n" +
						"where trim(rplf_no) = '"+txtRPLFno.getText()+"' and co_id = '"+lookupCompany.getValue()+"'" ;		
			}	
		} else {
			sqlDetail = "update rf_request_header \n" +
					"set status_id = '"+status+"' \n" +
					"where trim(rplf_no) = '"+txtRPLFno.getText()+"' \n"+
					"and co_id = '"+lookupCompany.getValue()+"'" ;			
		}

		System.out.println(sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}

	private void updatePV_header(pgUpdate db, String status){
		String sqlDetail = ""; 

		if (status.equals("I")) {
			sqlDetail = "update rf_pv_header \n" +
					"set status_id = '"+status+"', proc_id = 0 \n" +
					"where trim(pv_no) = '"+txtRPLFno.getText()+"' \n" +
					"and co_id = '"+lookupCompany.getValue()+"' and status_id != 'P'" ;	
		} else {
			sqlDetail = "update rf_pv_header \n" +
					"set status_id = '"+status+"', proc_id = 0 \n" +
					"where trim(pv_no) = '"+txtRPLFno.getText()+"' \n" +
					"and co_id = '"+lookupCompany.getValue()+"'" ;			
		}

		System.out.println(sqlDetail);
		db.executeUpdate(sqlDetail, false);				

	}

	private static String getCPFStatus(String strCOID, String strCDF) {
		return FncGlobal.GetString("select (case when status_id = 'A' then 'ACTIVE' when status_id = 'R' then 'RELEASED' WHEN status_id = 'P' THEN 'POSTED' else 'INACTIVE' end) \n" +
				"from cm_cdf_hd a \n" +
				"where a.cdf_no = '"+strCDF+"' and co_id = '"+strCOID+"'"); 
	}

	private static Date getCanceledDate(String strCOID, String strCDF) {
		return FncGlobal.GetDate("select a.canceled_date \n" +
				"from cm_cdf_hd a \n" +
				"where a.cdf_no = '"+strCDF+"' and co_id = '"+strCOID+"'"); 
	}

	public String getCanceledBy(String strCOID, String strCDF) {
		return FncGlobal.GetString("select trim(upper(concat(bb.last_name, ', ', bb.first_name))) \n" +
				"from cm_cdf_hd a \n" +
				"left join em_employee b on a.canceled_by = b.emp_code \n" +
				"left join rf_entity bb on b.entity_id = bb.entity_id \n" + 
				"where a.cdf_no = '"+strCDF+"' and a.co_id = '"+strCOID+"'"); 
	}

	private static Boolean cancellationAccessMatrix(String strUser) {
		Boolean blnProceed = false; 

		if (UserInfo.EmployeeCode.equals("900054") || UserInfo.Department.equals("98")) {
			blnProceed = true; 
		} else if (UserInfo.EmployeeCode.equals("900365") || UserInfo.EmployeeCode.equals("900965")) {
			if (txtCVStatus.getText().equals("POSTED")) {
				blnProceed = false;
			} else {
				blnProceed = true;
			}	 
		} else {
			blnProceed = false;
		}

		return blnProceed; 
	}

	private static void SetAction(Boolean blnDo) {
		btnInactivate.setEnabled(blnDo);

		if (blnDo) {	
			btnInactivate.setText((txtCPFStatus.getText().equals("INACTIVE"))?"Activate":"Deactivate");
			btnInactivate.setActionCommand((txtCPFStatus.getText().equals("INACTIVE"))?"Activate":"Deactivate");
			txtReasonCanc.setEditable((txtCPFStatus.getText().equals("INACTIVE"))?false:true);
		} else {
			btnInactivate.setText("Insufficient Access");
		}
	}

	private static void detailCPF(String strCPF) {
		pgSelect dbExec = new pgSelect(); 
		dbExec.select("select *\n" + 
				"from view_comm_cdf_status\n" + 
				"where cdf_no = '"+strCPF+"' and co_id = '"+lookupCompany.getValue()+"'"); 

		if (dbExec.getRowCount()>0) {
			txtCPFStatus.setText(getCPFStatus(lookupCompany.getValue(), lookupCPFno.getValue()));

			txtRPLFno.setText(dbExec.Result[0][1].toString());
			txtRPLFStatus.setText(dbExec.Result[0][6].toString());

			txtPVno.setText(dbExec.Result[0][2].toString());
			txtPVStatus.setText(dbExec.Result[0][7].toString());

			txtCVno.setText(dbExec.Result[0][3].toString());
			txtCVStatus.setText(dbExec.Result[0][8].toString());

			txtAmount.setText(nf.format(Double.parseDouble(dbExec.Result[0][5].toString())));
			dteDate.setDate(getCanceledDate(lookupCompany.getValue(), lookupCPFno.getValue()));
		}
	}
}
