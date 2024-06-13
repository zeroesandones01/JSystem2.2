package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Functions.sms_builder;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_sms_batch_sending;

public class sms_tab2 extends JXPanel implements _GUI {

	private static final long serialVersionUID = -4835022540286216974L;
	private jSMS sms_main; 

	private static JSplitPane splitSMS; 
	
	private _JLookup txtBat; 
	
	private JButton btnSend; 
	private JButton btnApprove;
	private JButton btnDisapprove;
	private JButton btnDelete;
	
	public static JList rowSMS;
	private _JTableMain tblSMS;
	private JScrollPane scrSMS;
	private model_sms_batch_sending modelSMS;
	
	private JTextArea txtMessage; 
	private JLabel lblRemChar; 

	static Border lineBorderGray = BorderFactory.createLineBorder(Color.GRAY);
	
	private String[] toppings = new String[0];
	private Integer intPart;
	private JScrollPane scrMessage;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	private Boolean blnWithAccess = FncGlobal.GetBoolean("select exists(select * from mf_sms_access where emp_code = '"+UserInfo.EmployeeCode+"' and access_approval = true)"); 

	private Font fontPlainSanSerNine = new Font("SansSerif", Font.PLAIN, 9);
	private Font fontPlainSanSer11 = new Font("SansSerif", Font.PLAIN, 11);
	
	public sms_tab2(jSMS sms) {
		this.sms_main = sms; 
		initGUI();
	}

	public sms_tab2(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public sms_tab2(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public sms_tab2(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		this.add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 60));
				{
					{
						JXPanel panBat = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panBat, BorderLayout.LINE_START); 
						panBat.setPreferredSize(new Dimension(200, 0));
						panBat.setBorder(JTBorderFactory.createTitleBorder("Batch", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							txtBat = new _JLookup(); 
							panBat.add(txtBat, BorderLayout.CENTER);
							txtBat.setLookupSQL("select distinct x.batch, x.date_created::Date as date_created, z.login_id as created_by \n" + 
									"from rf_sms_batch x \n" + 
									"inner join em_employee y on x.created_by = y.emp_code \n" + 
									"inner join rf_system_user z on z.emp_code = y.emp_code \n" + 
									"where x.sent = false \n" + 
									"and coalesce(x.batch, '') != '' \n" + 
									"and ((select a.dept_code from em_employee a where a.emp_code = '"+UserInfo.EmployeeCode+"') = y.dept_code or \n" + 
									"(select a.dept_code from em_employee a where a.emp_code = '"+UserInfo.EmployeeCode+"') = '98') or \n" + 
									"/*special ma'am nelia access; so that could do approvals for both pct and cc*/('"+UserInfo.EmployeeCode+"' = '987120' and y.dept_code in ('54', '103')) \n" + 
									"order by x.batch desc");
							txtBat.setReturnColumn(0);
							txtBat.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
									
									if (data!=null) {
										GenerateList(); 
										txtMessage.setText(FncGlobal.GetString("select coalesce(message, '["+txtBat.getValue()+"] This is a test message.') from rf_sms_batch where batch = '"+txtBat.getValue()+"'"));
										btnSend.setEnabled(true);
									}
									
									String strStatus = FncGlobal.GetString("select msg_status from rf_sms_batch where batch = '"+txtBat.getValue().toString()+"' limit 1");
									
									System.out.println("");
									System.out.println("strStatus: " + strStatus);
									System.out.println("blnWithAccess: " + blnWithAccess); 
									
									if (strStatus.equals("O")) {
										ButtonState(true, false, false, true);
										txtMessage.setEditable(true);
									} else if (strStatus.equals("A")) {
										if (blnWithAccess) {
											ButtonState(false, true, true, true);
										} else {
											ButtonState(false, false, false, false);
										}
										txtMessage.setEditable(false);
									} else {
										ButtonState(false, false, false, false);
										txtMessage.setEditable(false);
									}
										
								}
							});
						}
					}
					{
						JXPanel panButtons = new JXPanel(new GridLayout(1, 3, 5, 5)); 
						panPage.add(panButtons, BorderLayout.CENTER); 
						{
							{
								btnSend = new JButton("Save"); 
								panButtons.add(btnSend); 
								btnSend.setEnabled(false);
								btnSend.setFont(fontPlainSanSer11);
								btnSend.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										Boolean blnSent = false;
										Boolean blnBlank = false;
										Boolean blnProceed = false;
										
										if (FncGlobal.GetInteger("select count(*)::int from rf_sms_batch x \n" +
												"where x.batch = '"+txtBat.getValue().toString()+"' and \n" +
												"exists(select * from rf_sms_pool y where y.msg_id = x.msg_id)")>0) {
											blnSent = true;
											JOptionPane.showMessageDialog(null, "This batch has already been queued.");
										} else {
											blnSent = false;
										}
										
										if (txtMessage.getText().toString().length()<1) {
											blnBlank = true;
											JOptionPane.showMessageDialog(null, "The message cannot be blank.");									
										} else {
											blnBlank = false;
										}
										
										if (!blnSent && !blnBlank) {
											blnProceed = true; 
										} else {
											blnProceed = false;
										}
										
										if (blnProceed) {
											if (JOptionPane.showConfirmDialog(null, "Save for approval?", "Proceed", JOptionPane.YES_NO_OPTION, 
													JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
												SaveMessageForApproval();
											}	
										}
									}
								});
							}
						}
						{
							btnApprove = new JButton("Approve"); 
							panButtons.add(btnApprove); 
							btnApprove.setEnabled(false);
							btnApprove.setFont(fontPlainSanSer11);
							btnApprove.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									QueueMessages();
									ButtonState(false, false, false, false);
								}
							});
						}
						{
							btnDisapprove = new JButton("Disapprove"); 
							panButtons.add(btnDisapprove); 
							btnDisapprove.setEnabled(false);
							btnDisapprove.setFont(fontPlainSanSer11);
							btnDisapprove.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									DisapproveMessage();
									ButtonState(true, false, false, false);
								}
							});
						}
						{
							btnDelete = new JButton("Delete"); 
							panButtons.add(btnDelete); 
							btnDelete.setEnabled(false);
							btnDelete.setFont(fontPlainSanSer11);
							btnDelete.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									if (JOptionPane.showConfirmDialog(null, "Delete this batch?", "SMS Batch", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
										DeleteMessage();
										ButtonState(false, false, false, false); 
									}
								}
							});
						}
					}
				}
			}
			{
				splitSMS = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				panMain.add(splitSMS, BorderLayout.CENTER); 
				splitSMS.setOneTouchExpandable(true);
				splitSMS.setResizeWeight(.7d);
				{
					{
						JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
						splitSMS.add(panCenter); 
						{
							scrSMS = new JScrollPane();
							panCenter.add(scrSMS, BorderLayout.CENTER);
							{
								modelSMS = new model_sms_batch_sending();
								tblSMS = new _JTableMain(modelSMS);
								scrSMS.setViewportView(tblSMS);
								scrSMS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								
								tblSMS.getColumnModel().getColumn(1).setPreferredWidth(203);
								tblSMS.getColumnModel().getColumn(2).setPreferredWidth(50);
								tblSMS.getColumnModel().getColumn(3).setPreferredWidth(125);
								tblSMS.getColumnModel().getColumn(4).setPreferredWidth(300);
								tblSMS.getColumnModel().getColumn(5).setPreferredWidth(125);
								tblSMS.getColumnModel().getColumn(6).setPreferredWidth(100);
								tblSMS.getColumnModel().getColumn(7).setPreferredWidth(75);
								
								tblSMS.setSortable(false);
								tblSMS.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no", "Sent", "batch", "msg_id", "due_date", "msg_id");
							}
							{
								rowSMS = tblSMS.getRowHeader();
								rowSMS.setModel(new DefaultListModel());
								scrSMS.setRowHeaderView(rowSMS);
								scrSMS.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
					{
						JXPanel panEnd = new JXPanel(new BorderLayout(5, 5)); 
						splitSMS.add(panEnd); 
						panEnd.setPreferredSize(new Dimension(0, 100));
						panEnd.setBorder(JTBorderFactory.createTitleBorder("Message", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								txtMessage = new JTextArea(""); 
								txtMessage.setEditable(false);
								txtMessage.setLineWrap(true);
								txtMessage.setWrapStyleWord(true);
								txtMessage.setFont(new Font("Tahoma", Font.PLAIN, 12));
								txtMessage.getDocument().addDocumentListener(new DocumentListener() {
									public void removeUpdate(DocumentEvent e) {
										ReplaceInvalidChar(); 
										RemainingChar();
									}
									
									public void insertUpdate(DocumentEvent e) {
										ReplaceInvalidChar(); 
										RemainingChar();
									}
									
									public void changedUpdate(DocumentEvent e) {
										ReplaceInvalidChar(); 
										RemainingChar();
									}
									
									private void ReplaceInvalidChar() {

									}
									
									private void RemainingChar() {
										Runnable doAssist = new Runnable() {
											public void run() {
												String strPart; 
												Integer intText;
												Integer intModulo;
												Integer intDividend;
												Integer intDivisor;
												Integer intQuotient;

												intText = txtMessage.getText().length();
												
												if (intText>231) {
													if (intText%Integer.parseInt("231")>0) {
														intModulo = 1; 
													} else {
														intModulo = 0; 
													}
													
													intDividend = txtMessage.getText().length(); 
													intDivisor = Integer.parseInt("231"); 
													intQuotient = intDividend/ intDivisor; 
													intPart = intQuotient + intModulo; 

													strPart = "(" + (intQuotient + intModulo) + " Parts)"; 
												} else {
													strPart = ""; 
													intPart = 1;
												}
												
												lblRemChar.setText("Remaining characters (" + intText.toString() + "/231)" + strPart);
												toppings = new String[intPart];
												strPart = txtMessage.getText();
												
												Integer intArr = 0; 
												for (int i = 0; i < strPart.length(); i += 231) {
													toppings[intArr]=strPart.substring(i, Math.min(i + 231, strPart.length()));
													intArr++;
												}
											}
										};
										SwingUtilities.invokeLater(doAssist);
									}
								});
							}
							{
								scrMessage = new JScrollPane(txtMessage);
								panEnd.add(scrMessage, BorderLayout.CENTER); 
							}
							{
								lblRemChar = new JLabel("Remaining characters (0/231)"); 
								panEnd.add(lblRemChar, BorderLayout.PAGE_END); 
								lblRemChar.setHorizontalAlignment(JLabel.TRAILING);
								lblRemChar.setFont(fontPlainSanSerNine);
							}
						}
					}
				}	
			}
		}
		ButtonState(false, false, false, false); 
	}
	
	private void ButtonState(Boolean blnSave, Boolean blnApprove, Boolean blnDisapprove, Boolean blnDelete) {
		btnSend.setEnabled(blnSave);
		txtMessage.setEditable(blnSave);
		btnApprove.setEnabled(blnApprove);
		btnDisapprove.setEnabled(blnDisapprove);
		btnDelete.setEnabled(blnDelete);
	}
	
	private boolean GenerateList() {
		boolean blnSuccess = false; 
		
		FncTables.clearTable(modelSMS);
		DefaultListModel listModel = new DefaultListModel();
		rowSMS.setModel(listModel); 
		
		String strCoID = "";
		String strProID = "";
		String strPhaseID = "";
		String strBatch = txtBat.getValue()==null || txtBat.getValue().toString()=="null" || txtBat.getValue().isEmpty() ? "" : txtBat.getValue().toString();

		String strDateFrom = FncGlobal.GetString("select min(due_date)::date::varchar from rf_sms_batch where batch = '"+txtBat.getValue().toString()+"'"); 
		String strDateTo = FncGlobal.GetString("select max(due_date)::date::varchar from rf_sms_batch where batch = '"+txtBat.getValue().toString()+"'"); 
		String strType = FncGlobal.GetString("select distinct z.type_group_id \n" + 
				"from rf_sms_batch x \n" +
				"inner join rf_sold_unit y on x.entity_id = y.entity_id and x.proj_id = y.projcode and x.pbl_id = y.pbl_id and x.seq_no::int = y.seq_no::int and y.status_id = 'A'\n" + 
				"inner join mf_buyer_type z on y.buyertype = z.type_id\n" + 
				"where x.batch = '"+txtBat.getValue().toString()+"'"); 
		String strListIdx = FncGlobal.GetString("select distinct list_type_index::varchar from rf_sms_batch where batch = '"+txtBat.getValue().toString()+"'"); 
		
		pgSelect db = new pgSelect();
		db.select("select distinct c_tag, c_name, c_project, c_unit, c_cellphone, c_sent, '"+strBatch+"', c_entity_id, c_proj_id, c_pbl_id, c_seq_no, c_date, c_msg_id \n" + 
				"from view_sms_list('"+strCoID+"', '"+strProID+"', " +
				"'"+strPhaseID+"', '"+strType+"', " +
				"'"+strDateFrom+"', '"+strDateTo+"', '"+strListIdx+"', " +
				"'"+strBatch+"')");
		
		System.out.println("select distinct c_tag, c_name, c_project, c_unit, c_cellphone, c_sent, '"+strBatch+"', c_entity_id, c_proj_id, c_pbl_id, c_seq_no, c_date, c_msg_id  \n" + 
				"from view_sms_list('"+strCoID+"', '"+strProID+"', " +
				"'"+strPhaseID+"', '"+strType+"', " +
				"'"+strDateFrom+"', '"+strDateTo+"', '"+strListIdx+"', " +
				"'"+strBatch+"')");

		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelSMS.addRow(db.getResult()[x]);
				listModel.addElement(modelSMS.getRowCount());
			}
			blnSuccess = true; 
		}
		
		return blnSuccess; 
	}
	
	private void QueueMessages() {
		String strSQL = ""; 
		Integer intQueueID = 0; 
		pgUpdate dbExec = new pgUpdate(); 
		
		for (int x = 0; x < modelSMS.getRowCount(); x++) {
			if ((Boolean) modelSMS.getValueAt(x, 0)) {
				String strEmployee = "";
				String strUnitID = ""; 
				
				intQueueID = FncGlobal.GetInteger("select (coalesce(max(pool_row_id::int), 0) + 1)::int from rf_sms_pool");
				
				strSQL = "insert into rf_sms_pool values ('"+intQueueID+"'::bigint, '"+modelSMS.getValueAt(x, 12)+"'::bigint, 'P', now())";
				dbExec = new pgUpdate();
				dbExec.executeUpdate(strSQL, false);
				dbExec.commit();
				
				strSQL = "update rf_sms_batch \n" + 
						"set message = '"+txtMessage.getText()+"', sent_by = '"+UserInfo.EmployeeCode+"', msg_status = 'P' \n" + 
						"where msg_id::int = '"+modelSMS.getValueAt(x, 12)+"'::int"; 
				dbExec = new pgUpdate();
				dbExec.executeUpdate(strSQL, false);
				dbExec.commit();
				
				strEmployee = FncGlobal.GetString("select created_by \n" +
						"from rf_sms_batch \n" +
						"where msg_id::int = '"+modelSMS.getValueAt(x, 12)+"'::int"); 
				
				strUnitID = FncGlobal.GetString("select unit_id \n" +
						"from mf_unit_info \n" +
						"where proj_id = '"+modelSMS.getValueAt(x, 8).toString()+"' \n" +
						"and pbl_id = '"+modelSMS.getValueAt(x, 9).toString()+"'"); 
				
				sms_builder.InsertIntoPhoneFollowup(modelSMS.getValueAt(x, 7).toString(), 
						modelSMS.getValueAt(x, 9).toString(), 
						modelSMS.getValueAt(x, 10).toString(), 
						strUnitID, 
						modelSMS.getValueAt(x, 8).toString(), 
						FncGlobal.GetString("select z.entity_name \n" + 
								"from rf_system_user x \n" + 
								"inner join em_employee y on x.emp_code = y.emp_code \n" + 
								"inner join rf_entity z on y.entity_id = z.entity_id \n" + 
								"where x.emp_code = '"+strEmployee+"'"), 
						txtMessage.getText(), 
						modelSMS.getValueAt(x, 4).toString()); 

				btnSend.setEnabled(false);
			}
		}
		
		ButtonState(false, false, false, false);
		JOptionPane.showMessageDialog(null, "Message is queued for sending.");
	}
	
	private void SaveMessageForApproval() {
		String strSQL = ""; 
		String strMessage = ""; 
		pgUpdate dbExec = new pgUpdate(); 
		
		if (!txtMessage.getText().contains("(This is a system generated text message. Please do not reply.)")) {
			strMessage = txtMessage.getText() + " (This is a system generated text message. Please do not reply.)"; 
		} else {
			strMessage = txtMessage.getText(); 
		}
		
		strMessage = strMessage.replace("'", "`"); 
		txtMessage.setText(strMessage);
		
		for (int x = 0; x < modelSMS.getRowCount(); x++) {
			if ((Boolean) modelSMS.getValueAt(x, 0)) {
				strSQL = "update rf_sms_batch \n" + 
						"set message = '"+strMessage+"', msg_status = 'A' \n" + 
						"where msg_id::int = '"+modelSMS.getValueAt(x, 12)+"'::int"; 

				dbExec = new pgUpdate();
				dbExec.executeUpdate(strSQL, false);
				dbExec.commit();

				btnSend.setEnabled(false);
			}
		}
		
		if (blnWithAccess) {
			ButtonState(false, true, true, true);
		}
		
		JOptionPane.showMessageDialog(null, "Message is saved for approval.");
	}
	
	private void DisapproveMessage() {
		String strSQL = ""; 
		pgUpdate dbExec = new pgUpdate(); 
		
		for (int x = 0; x < modelSMS.getRowCount(); x++) {
			if ((Boolean) modelSMS.getValueAt(x, 0)) {
				strSQL = "update rf_sms_batch \n" + 
						"set msg_status = 'O' \n" + 
						"where msg_id::int = '"+modelSMS.getValueAt(x, 12)+"'::int"; 
				dbExec = new pgUpdate();
				dbExec.executeUpdate(strSQL, false);
				dbExec.commit();
				
				btnSend.setEnabled(false);
			}
		}
		
		ButtonState(true, false, false, false);
		JOptionPane.showMessageDialog(null, "Message disapproved!");
	}
	
	private void DeleteMessage() {
		String strSQL = ""; 
		pgUpdate dbExec = new pgUpdate(); 
		
		for (int x = 0; x < modelSMS.getRowCount(); x++) {
			if ((Boolean) modelSMS.getValueAt(x, 0)) {
				strSQL = "delete from rf_sms_batch where msg_id::int = '"+modelSMS.getValueAt(x, 12)+"'::int"; 
				dbExec = new pgUpdate();
				dbExec.executeUpdate(strSQL, false);
				dbExec.commit();
				
				btnSend.setEnabled(false);
			}
		}
		
		ButtonState(true, false, false, false);
		JOptionPane.showMessageDialog(null, "Message batch deleted!");
	}
}
