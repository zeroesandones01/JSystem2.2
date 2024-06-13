package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.poi.util.ArrayUtil;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Functions.sms_builder;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_sms_batch_sending;

public class sms_tab3 extends JXPanel implements _GUI {

	private static final long serialVersionUID = 6275483436110743169L;
	private jSMS sms_main;

	private JTextField txtSearch;

	private JTextArea txtMessage;
	private JTextArea txtMobileNumber;
	private JScrollPane scrLog;

	private JButton btnQ;
	private JButton btnPhoneBook;

	private JLabel lblRemChar;
	private Font fontPlainSanSerNine = new Font("SansSerif", Font.PLAIN, 9);
	static Border lineBorderGray = BorderFactory.createLineBorder(Color.GRAY);

	private String[] toppings = new String[0];
	private Integer intPart;
	private JScrollPane scrMessage;

	public static JList rowSMS;
	private _JTableMain tblSMS;
	private JScrollPane scrSMS;
	private model_sms_batch_sending modelSMS;

	private JXPanel panPhoneBook;

	private Font fontPlainSanSer11 = new Font("SansSerif", Font.PLAIN, 11);

	ArrayList<String> arrClientName = new ArrayList<String>(); // changed from arrClientID to arrClientName by Jari Cruz asof july 21 2022, reason: this variable is used to store client name
	ArrayList arrUnitID = new ArrayList();
	ArrayList arrProjID = new ArrayList();
	ArrayList arrSeqNo = new ArrayList();

	pgSelect dbRow = new pgSelect();

	public sms_tab3(jSMS sms) {
		this.sms_main = sms;
		initGUI();
	}

	public sms_tab3(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public sms_tab3(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public sms_tab3(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		this.add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.CENTER);
			panCenter.setBorder(JTBorderFactory.createTitleBorder("Message for Sending",
					FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				{
					JXPanel panLeft = new JXPanel(new BorderLayout(5, 5));
					panCenter.add(panLeft);
					panLeft.setPreferredSize(new Dimension(300, 0));
					{
						{
							JXPanel panNumber = new JXPanel(new BorderLayout(5, 5));
							panLeft.add(panNumber, BorderLayout.PAGE_START);
							panNumber.setPreferredSize(new Dimension(0, 54));
							{
								{
									JXPanel panMobileLabel = new JXPanel(new GridLayout(2, 1, 5, 5));
									panNumber.add(panMobileLabel, BorderLayout.LINE_START);
									panMobileLabel.setPreferredSize(new Dimension(100, 0));
									{
										{
											JLabel lblMobileNo1 = new JLabel("Mobile No(s)", null, JLabel.LEADING);
											panMobileLabel.add(lblMobileNo1);
											lblMobileNo1.setFont(fontPlainSanSer11);
										}
										{
											JLabel lblMobileNo2 = new JLabel("", null, JLabel.LEADING);
											panMobileLabel.add(lblMobileNo2);
										}
									}
								}
								{
									JXPanel panMobileNumber = new JXPanel(new BorderLayout(5, 5));
									panNumber.add(panMobileNumber, BorderLayout.CENTER);
									{
										{
											txtMobileNumber = new JTextArea("");
											txtMobileNumber.setLineWrap(true);
											txtMobileNumber.setWrapStyleWord(true);
											txtMobileNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
											txtMobileNumber.getDocument().addDocumentListener(new DocumentListener() {
												public void removeUpdate(DocumentEvent e) {
													CheckChar();
												}

												public void insertUpdate(DocumentEvent e) {

												}

												public void changedUpdate(DocumentEvent e) {

												}

												private void CheckChar() {
													if (txtMobileNumber.getText().length() == 0) {
														System.out.println("Clearing!");

														arrClientName.clear();
														arrUnitID.clear();
														arrProjID.clear();
														arrSeqNo.clear();
													}
												}
											});
										}
										{
											scrLog = new JScrollPane(txtMobileNumber);
											panMobileNumber.add(scrLog, BorderLayout.CENTER);
										}
									}
								}
								{
									JXPanel panPhonebook = new JXPanel(new GridLayout(2, 1, 5, 5));
									panNumber.add(panPhonebook, BorderLayout.LINE_END);
									panPhonebook.setPreferredSize(new Dimension(125, 0));
									{
										{
											btnPhoneBook = new JButton("Add Contact");
											panPhonebook.add(btnPhoneBook);
											btnPhoneBook.setFont(fontPlainSanSer11);
											btnPhoneBook.addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													int scanOption = 10;

													while (scanOption == JOptionPane.NO_OPTION || scanOption == 10) {
														GenerateList();
														scanOption = JOptionPane.showOptionDialog(null, panPhoneBook,
																"Phonebook", JOptionPane.YES_NO_CANCEL_OPTION,
																JOptionPane.PLAIN_MESSAGE, null,
																new Object[] { "Add Recipient", "     Refresh     ",
																		"      Cancel       " },
																JOptionPane.CANCEL_OPTION);

														if (scanOption == JOptionPane.YES_OPTION) {
															System.out.println("Add was pressed!");
															String strMobile = "";

															for (int x = 0; x < modelSMS.getRowCount(); x++) {
																if ((Boolean) modelSMS.getValueAt(x, 0) == true) {

																	System.out.println("");
																	System.out.println(
																			"Name: " + modelSMS.getValueAt(x, 1));
																	System.out.println(
																			"Project ID: " + modelSMS.getValueAt(x, 8));
																	System.out.println(
																			"Pbl ID: " + modelSMS.getValueAt(x, 9));
																	System.out.println(
																			"Seq No: " + modelSMS.getValueAt(x, 10));
																	System.out.println("Not Contains: " + !arrClientName
																			.contains(modelSMS.getValueAt(x, 1)));

																	if (!arrClientName
																			.contains(modelSMS.getValueAt(x, 1))) {
																		arrClientName.add(
																				modelSMS.getValueAt(x, 1).toString());
																	}
																	
																	arrProjID.add(modelSMS.getValueAt(x, 8).toString());
																	// added by jari cruz as of july 21 2022 for project id

																	arrUnitID.add(modelSMS.getValueAt(x, 9).toString());
																	// added by jari cruz as of july 21 2022 for pbl id

																	arrSeqNo.add(modelSMS.getValueAt(x, 10).toString());
																	// added by jari cruz as of july 21 2022 for seq no

																	if (txtMobileNumber.getText().length() > 0) {
																		strMobile = txtMobileNumber.getText();

																		if (strMobile.contains(
																				modelSMS.getValueAt(x, 4).toString())) {
																			JOptionPane.showMessageDialog(null, modelSMS
																					.getValueAt(x, 1)
																					+ "'s number is already in the recipient.");
																		} else {
																			txtMobileNumber.append(", " + modelSMS
																					.getValueAt(x, 4).toString());
																		}
																	} else {
																		txtMobileNumber.setText(
																				modelSMS.getValueAt(x, 4).toString());
																	}
																}
															}
														} else if (scanOption == JOptionPane.NO_OPTION) {
															System.out.println("No was pressed!");
														} else {
															System.out.println("Cancel was pressed!");
														}
													}

													System.out.println("");
													System.out.println("arrClientName Size: " + arrClientName.size());
													System.out.println("arrProjID Size: " + arrProjID.size());
													System.out.println("arrUnitID Size: " + arrUnitID.size());
													System.out.println("arrSeqNo Size: " + arrSeqNo.size());
												}
											});
										}
										{
											JLabel lblMobileNo2 = new JLabel("", null, JLabel.LEADING);
											panPhonebook.add(lblMobileNo2);
										}
									}
								}
							}
						}
						{
							JXPanel panMessage = new JXPanel(new BorderLayout(5, 5));
							panLeft.add(panMessage, BorderLayout.CENTER);
							{
								{
									{
										JLabel lblMessage = new JLabel("");
										panMessage.add(lblMessage, BorderLayout.LINE_START);
										lblMessage.setPreferredSize(new Dimension(100, 0));
									}
									{
										txtMessage = new JTextArea("");
										txtMessage.setEditable(true);
										txtMessage.setFont(new Font("Tahoma", Font.PLAIN, 12));
										txtMessage.setLineWrap(true);
										txtMessage.setWrapStyleWord(true);
										txtMessage.getDocument().addDocumentListener(new DocumentListener() {
											public void removeUpdate(DocumentEvent e) {
												RemainingChar();
											}

											public void insertUpdate(DocumentEvent e) {
												RemainingChar();
											}

											public void changedUpdate(DocumentEvent e) {
												RemainingChar();
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

														if (intText > 231) {
															if (intText % Integer.parseInt("231") > 0) {
																intModulo = 1;
															} else {
																intModulo = 0;
															}

															intDividend = txtMessage.getText().length();
															intDivisor = Integer.parseInt("231");
															intQuotient = intDividend / intDivisor;
															intPart = intQuotient + intModulo;

															strPart = "(" + (intQuotient + intModulo) + " Parts)";
														} else {
															strPart = "";
															intPart = 1;
														}

														lblRemChar.setText("Remaining characters (" + intText.toString()
																+ "/231)" + strPart);
														toppings = new String[intPart];
														strPart = txtMessage.getText();

														Integer intArr = 0;
														for (int i = 0; i < strPart.length(); i += 231) {
															toppings[intArr] = strPart.substring(i,
																	Math.min(i + 231, strPart.length()));

															try {
																if (sms_builder.containsIllegal(toppings[intArr])) {
																	JOptionPane.showMessageDialog(null,
																			"Illegal character found!\nPlease refrain from using the apostrophe.\nUse (`) instead.");
																}
															} catch (NullPointerException ex) {
																System.out.println(ex.getMessage());
															}

															intArr++;
														}
													}
												};
												SwingUtilities.invokeLater(doAssist);
											}
										});
										txtMessage.addKeyListener(new KeyListener() {
											public void keyTyped(KeyEvent e) {

											}

											public void keyReleased(KeyEvent e) {

											}

											public void keyPressed(KeyEvent e) {
												if (e.getKeyCode() == 10) {
													e.consume();

													JOptionPane.showMessageDialog(null,
															"This is not an e-mail message.\nAvoid using the nextline key.");
												}
											}
										});
									}
									{
										scrMessage = new JScrollPane(txtMessage);
										panMessage.add(scrMessage, BorderLayout.CENTER);
									}
								}
								{

								}
							}
						}
						{
							JXPanel panButton = new JXPanel(new GridLayout(1, 2, 5, 5));
							panLeft.add(panButton, BorderLayout.PAGE_END);
							panButton.setPreferredSize(new Dimension(0, 30));
							{
								{
									lblRemChar = new JLabel("Remaining characters (0/231)");
									panButton.add(lblRemChar);
									lblRemChar.setHorizontalAlignment(JLabel.TRAILING);
									lblRemChar.setFont(fontPlainSanSerNine);
								}
								{
									btnQ = new JButton("Send");
									panButton.add(btnQ);
									btnQ.setEnabled(true);
									btnQ.setFont(fontPlainSanSer11);
									btnQ.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											Boolean blnProceed = true;
											Boolean blnCheck1 = true;
											Boolean blnCheck2 = true;

											if (FncGlobal.GetBoolean(
													"select exists(select * from mf_sms_access where access_individual and emp_code = '"
															+ UserInfo.EmployeeCode + "')")) {
												blnCheck1 = true;
											} else {
												blnCheck1 = false;
												JOptionPane.showMessageDialog(null, "Insufficient access",
														"Message Sending", JOptionPane.WARNING_MESSAGE);
											}

											if (sms_builder.containsIllegal(txtMessage.getText())) {
												JOptionPane.showMessageDialog(null,
														"Illegal character found!\nPlease refrain from using the apostrophe.\nUse (`) instead.");
												blnCheck2 = false;
											} else {
												blnCheck2 = true;
											}

											if (blnCheck1 && blnCheck2) {
												if (!txtMessage.getText().contains(
														"(This is a system generated text message. Please do not reply.)")) {
													txtMessage.setText(txtMessage.getText()
															+ " (This is a system generated text message. Please do not reply.)");
												}
												txtMessage.setText(txtMessage.getText().replace("'", "`"));

												if (JOptionPane.showConfirmDialog(null, "Send this message?", "Confirm",
														JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
													Send_v2();
													JOptionPane.showMessageDialog(null,
															"Message is queued for sending.", "Message Sending",
															JOptionPane.INFORMATION_MESSAGE);
												}
											} else {
												JOptionPane.showMessageDialog(null, "Sending did not proceed.",
														"Message Sending", JOptionPane.WARNING_MESSAGE);
											}
										}
									});
								}
							}
						}
					}
				}
			}
		}
		{
			panPhoneBook = new JXPanel(new BorderLayout(3, 3));
			panPhoneBook.setPreferredSize(new Dimension(600, 400));
			{
				{
					JXPanel panSearch = new JXPanel(new BorderLayout(5, 5));
					panPhoneBook.add(panSearch, BorderLayout.PAGE_START);
					panSearch.setPreferredSize(new Dimension(0, 25));
					{
						{
							JLabel lblSearch = new JLabel("Search Name: ");
							panSearch.add(lblSearch, BorderLayout.LINE_START);
							lblSearch.setPreferredSize(new Dimension(100, 0));
						}
						{
							txtSearch = new JTextField("");
							panSearch.add(txtSearch, BorderLayout.CENTER);
							txtSearch.setHorizontalAlignment(JTextField.CENTER);
							txtSearch.setEditable(true);
							txtSearch.getDocument().addDocumentListener(new DocumentListener() {
								public void removeUpdate(DocumentEvent e) {
									focusonthis();
								}

								public void insertUpdate(DocumentEvent e) {
									focusonthis();
								}

								public void changedUpdate(DocumentEvent e) {
									focusonthis();
								}

								void focusonthis() {
									if (txtSearch.getText().equals("")) {
										tblSMS.changeSelection(0, 0, false, false);
									} else {
										for (int x = 0; x < tblSMS.getRowCount(); x++) {
											System.out.println("");
											System.out.println("txtSearch.getText(): " + txtSearch.getText());
											System.out.println("tblSMS.getValueAt(x, 1).toString(): "
													+ tblSMS.getValueAt(x, 1).toString());

											try {
												if (tblSMS.getValueAt(x, 1).toString().toLowerCase()
														.contains(txtSearch.getText())
														|| tblSMS.getValueAt(x, 1).toString()
																.substring(0, txtSearch.getText().length())
																.equals(txtSearch.getText())) {
													tblSMS.changeSelection(x, 1, false, false);
													x = tblSMS.getRowCount();
												}
											} catch (StringIndexOutOfBoundsException ex) {
												tblSMS.changeSelection(0, 0, false, false);
											}

										}
									}
								}
							});
						}
					}
				}
				{
					scrSMS = new JScrollPane();
					panPhoneBook.add(scrSMS, BorderLayout.CENTER);
					{
						modelSMS = new model_sms_batch_sending();
						tblSMS = new _JTableMain(modelSMS);
						scrSMS.setViewportView(tblSMS);
						scrSMS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

						tblSMS.getColumnModel().getColumn(1).setPreferredWidth(250);
						tblSMS.getColumnModel().getColumn(4).setPreferredWidth(256);

						tblSMS.setSortable(false);
						tblSMS.hideColumns("Sent", "batch", "entity_id");
					}
					{
						rowSMS = tblSMS.getRowHeader();
						rowSMS.setModel(new DefaultListModel());
						scrSMS.setRowHeaderView(rowSMS);
						scrSMS.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
		}
	}

	private boolean GenerateList() {
		boolean blnSuccess = false;

		FncTables.clearTable(modelSMS);
		DefaultListModel listModel = new DefaultListModel();
		rowSMS.setModel(listModel);

		pgSelect db = new pgSelect();
		db.select("select distinct false as tag, \n" + // removed distinct by jari cruz july 19 2022
				"b.entity_name as name, \n" + "d.proj_alias as proj_alias, \n" + // added proj_alias by jari cruz july
																					// 19 2022
				"e.description as unit_description, \n" + // added unit_description by jari cruz july 19 2022
				"(case when position(',' in replace(trim(array_to_string(c.mobile, ',')), '  ', ' ')) = 0 and position(' ' in replace(trim(array_to_string(c.mobile, ',')), '  ', ' ')) > 0 \n"
				+ "then replace(replace(trim(array_to_string(c.mobile, ',')), '  ', ' '), ' ', ', ') else replace(trim(array_to_string(c.mobile, ',')), '  ', ' ') end) as \"mobile no\", \n"
				+ "'' as sent, \n" + // added sent by jari cruz july 19 2022
				"'' as batch, \n" + // added batch by jari cruz july 19 2022
				"a.entity_id as entity_id, \n" + // added sent by jari cruz july 19 2022
				"a.projcode as proj_id, \n" + // added sent by jari cruz july 19 2022
				"a.pbl_id as pbl_id, \n" + // added sent by jari cruz july 19 2022
				"a.seq_no as seq_no \n" + // added sent by jari cruz july 19 2022
				"from rf_sold_unit a \n" + "inner join rf_entity b on a.entity_id = b.entity_id \n"
				+ "inner join rf_contacts c on a.entity_id = c.entity_id \n"
				+ "left join mf_project d on d.proj_id = a.projcode and d.status_id != 'I' \n"
				+ "left join mf_unit_info e on e.proj_id = a.projcode and e.pbl_id = a.pbl_id \n"
				+ "where a.status_id = 'A' and c.status_id = 'A' \n" + "order by b.entity_name");

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelSMS.addRow(db.getResult()[x]);
				listModel.addElement(modelSMS.getRowCount());
			}
			blnSuccess = true;
		}

		return blnSuccess;
	}

	private void Send() {
		String strMobile = txtMobileNumber.getText();
		String strID = "";
		String strResponse = "";
		String strFailed = "";

		String[] arr_mobile = strMobile.split(", ");

		for (int x = 0; x < toppings.length; x++) {
			System.out.println("");
			System.out.println("toppings[" + x + "]: " + toppings[x]);

			sms_builder.strMessage = toppings[x];

			for (int y = 0; y < arr_mobile.length; y++) {
				System.out.print("arr_mobile[y]: " + arr_mobile[y]);
				sms_builder.strNumber = arr_mobile[y];

				try {
					strID = sms_builder.SendMessageToNumber_withResponse();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				while (!strResponse.equals("sent")) {
					try {
						strResponse = sms_builder
								.GetMessageStatusFromResponse(sms_builder.FetchSingleMessage(strID).toString());
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					if (strResponse.equals("failed")) {
						strFailed = strFailed + arr_mobile[y].toString() + ", ";
						strFailed = strFailed.trim().substring(0, strFailed.trim().length() - 1);
						break;
					}
				}

				sms_builder.createMessageLog("0", strID, toppings[x], arr_mobile[y], strResponse);
			}

			if (strFailed.length() > 0) {
				JOptionPane.showMessageDialog(null, "Message sending failed on the following numbers: \n" + strFailed);
			} else {
				JOptionPane.showMessageDialog(null, "Message sent!");
			}
		}
	}

	private void Send_v2() {
		pgUpdate dbExec = new pgUpdate();
		String strExec = "";
		String strClientID = "";
		String strMobileNo = "";

		Integer intRowID = 0;
		Integer intMsgID = 0;
		Integer intQueueID = 0;

//		System.out.println("");
//		System.out.println("Array Size: " + arrClientName.size());

		if (arrClientName.size() > 0) {
			for (int count = 0; count < arrClientName.size(); count++) {
//				System.out.println(arr);
				
				/*Replaced by Jari Cruz asof july 21, 2022
				
				strClientID = FncGlobal.GetString(
						"select entity_id from rf_entity where entity_name = '" + arr + "' and status_id = 'A'");*/
				
				strClientID = FncGlobal.GetString(
						"select entity_id from rf_entity where entity_name = '" + arrClientName.get(count) + "' and status_id = 'A'");
				
				System.out.println("Inside Send_v2()");
				System.out.println("Client ID: " + strClientID);
				System.out.println("Client Name: " + arrClientName.get(count));
				System.out.println("Client Project ID: " + arrProjID.get(count));
				System.out.println("Client Pbl ID: " + arrUnitID.get(count));
				System.out.println("Client Seq No: " + arrSeqNo.get(count));
				System.out.println("");

				/*
				Commented by jari cruz as of july 21, 2022
				reason: this whole section is for getting client's proj_id, pbl_id, seq_no which is not needed anymore
				as it was declared inside "Add Contact" actionPerformed.
				dbRow = new pgSelect();
				dbRow.select("select projcode, pbl_id, seq_no from rf_sold_unit where entity_id = '" + strClientID
						+ "' and status_id = 'A'");
				
				arrProjID.clear();
				arrUnitID.clear();
				arrSeqNo.clear();

				if (dbRow.getRowCount() > 1) {
					for (int x = 0; x < dbRow.getRowCount(); x++) {

						System.out.println((String) dbRow.getResult()[x][0].toString());
						System.out.println((String) dbRow.getResult()[x][1].toString());
						System.out.println((String) dbRow.getResult()[x][2].toString());

						arrProjID.add((String) dbRow.getResult()[x][0].toString());
						arrUnitID.add((String) dbRow.getResult()[x][1].toString());
						arrSeqNo.add((String) dbRow.getResult()[x][2].toString());
					}
				} else {
					arrProjID.add((String) dbRow.getResult()[0][0].toString());
					arrUnitID.add((String) dbRow.getResult()[0][1].toString());
					arrSeqNo.add((String) dbRow.getResult()[0][2].toString());

					System.out.println((String) dbRow.getResult()[0][0].toString());
					System.out.println((String) dbRow.getResult()[0][1].toString());
					System.out.println((String) dbRow.getResult()[0][2].toString());
				}*/
				
				System.out.println("Array Unit ID: "+arrUnitID.size()); // to see how many clients were selected

				for (int x=0; x<arrUnitID.size(); x++) {
					intRowID = FncGlobal.GetInteger("select (coalesce(max(msg_row_id::int), 0) + 1) from rf_sms_batch");
					intMsgID = FncGlobal.GetInteger("select (coalesce(max(msg_id::int), 0) + 1) from rf_sms_batch");
					intQueueID = FncGlobal.GetInteger("select (coalesce(max(pool_row_id::int), 0) + 1)::int from rf_sms_pool");
					
					strMobileNo = FncGlobal.GetString("select (case when position(',' in replace(trim(array_to_string(x.mobile, ',')), '  ', ' ')) = 0 \n" + 
							"and position(' ' in replace(trim(array_to_string(x.mobile, ',')), '  ', ' ')) > 0 \n" + 
							"then replace(replace(trim(array_to_string(x.mobile, ',')), '  ', ' '), ' ', ', ') \n" + 
							"else replace(trim(array_to_string(x.mobile, ',')), '  ', ' ') end)::varchar \n" + 
							"from rf_contacts x \n" + 
							"where x.entity_id = '"+strClientID+"'"); 
					
					strExec = "insert into rf_sms_batch (msg_row_id, msg_id, batch, entity_id, proj_id, pbl_id, seq_no, \n" + 
							"cellphone, sent, message, created_by, date_created, sent_by, date_sent, \n" +
							"msg_status, due_date, type_id, list_type_index) \n" + 
							"values ("+intRowID+"::bigint, "+intMsgID+"::bigint, '', '"+strClientID+"', '"+arrProjID.get(x).toString()+"', '"+arrUnitID.get(x).toString()+"', '"+arrSeqNo.get(x).toString()+"'::int, \n" +
							"'"+strMobileNo+"', false, '"+txtMessage.getText()+"', '"+UserInfo.EmployeeCode+"', now(), \n" +
							"'"+UserInfo.EmployeeCode+"', now(), 'P', null, null, null)";

					System.out.println("");
					System.out.println(strExec);
					
					dbExec = new pgUpdate(); 
					dbExec.executeUpdate(strExec, false);
					dbExec.commit();
					
					strExec = "insert into rf_sms_pool values ('"+intQueueID+"'::bigint, '"+intMsgID.toString()+"'::bigint, 'P', now())";
					
					System.out.println("");
					System.out.println(strExec);
					
					dbExec = new pgUpdate(); 
					dbExec.executeUpdate(strExec, false);
					dbExec.commit();
					
					sms_builder.InsertIntoPhoneFollowup(
							strClientID, 
							arrUnitID.get(x).toString(), 
							arrSeqNo.get(x).toString(), 
							FncGlobal.GetString("select unit_id from mf_unit_info where proj_id = '"+arrProjID.get(x).toString()+"' and pbl_id = '"+arrUnitID.get(x).toString()+"'"), 
							arrProjID.get(x).toString(), 
							UserInfo.FullName, 
							txtMessage.getText(), 
							strMobileNo
					);
				}
			}
		} else {
			intRowID = FncGlobal.GetInteger("select (coalesce(max(msg_row_id::int), 0) + 1) from rf_sms_batch");
			intMsgID = FncGlobal.GetInteger("select (coalesce(max(msg_id::int), 0) + 1) from rf_sms_batch");
			intQueueID = FncGlobal.GetInteger("select (coalesce(max(pool_row_id::int), 0) + 1)::int from rf_sms_pool");
			
			strExec = "insert into rf_sms_batch (msg_row_id, msg_id, batch, entity_id, proj_id, pbl_id, seq_no, \n" + 
					"cellphone, sent, message, created_by, date_created, sent_by, date_sent, \n" +
					"msg_status, due_date, type_id, list_type_index) \n" + 
					"values ("+intRowID+"::bigint, "+intMsgID+"::bigint, '', '', '', '', null, \n" +
					"'"+txtMobileNumber.getText()+"', false, '"+txtMessage.getText()+"', '"+UserInfo.EmployeeCode+"', now(), \n" +
					"'"+UserInfo.EmployeeCode+"', now(), 'P', null, null, null)";

			System.out.println("");
			System.out.println(strExec);
			
			dbExec = new pgUpdate(); 
			dbExec.executeUpdate(strExec, false);
			dbExec.commit();
			
			strExec = "insert into rf_sms_pool values ('"+intQueueID+"'::bigint, '"+intMsgID.toString()+"'::bigint, 'P', now())";
			
			System.out.println("");
			System.out.println(strExec);
			
			dbExec = new pgUpdate(); 
			dbExec.executeUpdate(strExec, false);
			dbExec.commit();
		}

			Clear();
	}

	private void Clear() {
		arrClientName.clear();
		arrUnitID.clear();
		arrProjID.clear();
		arrSeqNo.clear();

		txtMobileNumber.setText("");
		txtMessage.setText("");
	}
}
