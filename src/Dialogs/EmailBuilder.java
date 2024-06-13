package Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;

import Buyers.LoansManagement.hdmfMon_qualifyAccounts;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import components.JTBorderFactory;
import interfaces._GUI;

public class EmailBuilder extends JPanel implements _GUI {

	private static final long serialVersionUID = 4736936018166392526L;

	@SuppressWarnings("unused")
	private String strFrom;

	private static HashMap<String, String> hashRecipient = new HashMap<String, String>();
	private static HashMap<String, String> hashEmployer = new HashMap<String, String>();
	private static HashMap<String, Date> hashEmploymentDate = new HashMap<String, Date>();
	private static HashMap<String, String> hashPosition = new HashMap<String, String>();
	private static HashMap<String, Date> hashCOE = new HashMap<String, Date>();
	private static HashMap<String, String> hashRecipientID = new HashMap<String, String>();
	private static HashMap<String, String> hashAttachment = new HashMap<String, String>();

	private static JEditorPane editorMail;
	private static JTextArea txtRecipient;
	private static JTextArea txtCC;
	
	private static JTextField txtSubject;
	private static JTextField txtSenderEmail;
	private static JTextField txtAttachment;
	
	private static JPasswordField txtPassword;
	
	private JLabel lblStatus;

	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private Font font9 = FncLookAndFeel.systemFont_Plain.deriveFont(9f);

	private static JList list; 
	
	private Color colorNotConnected = new Color(255, 102, 102);
	private Color colorConnected = new Color(102, 255, 178); 
	
	private Timer timer; 
	private JProgressBar pbar; 
	
	private JButton btnSend;
	private JButton btnSendAll; 
	private JButton btnOpen; 
	
	/**********		Hashmaps	**********/

	@SuppressWarnings("static-access")
	public void mapRecipient(String strKey, String strValue) {
		this.hashRecipient.put(strKey, strValue);  
	}

	@SuppressWarnings("static-access")
	public void mapEmployer(String strKey, String strValue) {
		this.hashEmployer.put(strKey, strValue);  
	}

	@SuppressWarnings("static-access")
	public void mapEmploymentDate(String strKey, Date dteValue) {
		this.hashEmploymentDate.put(strKey, dteValue);  
	}

	@SuppressWarnings("static-access")
	public void mapCOE(String strKey, Date dteValue) {
		this.hashCOE.put(strKey, dteValue);  
	}

	@SuppressWarnings("static-access")
	public void mapRecipientID(String strKey, String strValue) {
		this.hashRecipientID.put(strKey, strValue);  
	}

	@SuppressWarnings("static-access")
	public void mapPosition(String strKey, String strValue) {
		this.hashPosition.put(strKey, strValue);  
	}	

	@SuppressWarnings("static-access")
	public void mapAttachment(String strKey, String strValue) {
		this.hashAttachment.put(strKey, strValue);  
	}	
	
	/**********		Hashmaps	**********/

	@SuppressWarnings("static-access")
	public void hashClear() {
		this.hashRecipient.clear();
		this.hashEmployer.clear();
		this.hashEmploymentDate.clear();
		this.hashCOE.clear(); 
		this.hashRecipientID.clear();
	}

	public EmailBuilder() {
		initGUI(); 
	}

	public EmailBuilder(LayoutManager layout) {
		super(layout);
		initGUI(); 
	}

	public EmailBuilder(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI(); 
	}

	public EmailBuilder(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI(); 
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(0, 0));

		JXPanel panPreview = new JXPanel(new BorderLayout(5, 5)); 
		this.add(panPreview, BorderLayout.CENTER); 
		panPreview.setPreferredSize(new Dimension(1000, 500));
		panPreview.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panLine = new JXPanel(new GridLayout(2, 1, 5, 5)); 
				panPreview.add(panLine, BorderLayout.LINE_START);
				panLine.setPreferredSize(new Dimension(300, 0));
				{
					{
						JXPanel panList = new JXPanel(new BorderLayout(5, 5)); 
						panLine.add(panList); 
						panList.setBorder(JTBorderFactory.createTitleBorder("Recipients", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{                                        
							list = new JList(); 
							panList.add(list);
							list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if (!e.getValueIsAdjusting()) {
										txtRecipient.setText(hashRecipient.get((String) list.getSelectedValue()));
										txtAttachment.setText(hashAttachment.get((String) list.getSelectedValue()));
										txtSubject.setText("EMPLOYMENT VERIFICATION - "+(String) list.getSelectedValue());
										
										if (txtSubject.getText().length()>=new Integer("35")) {
											txtSubject.setFont(font9);
										} else {
											txtSubject.setFont(font11);
										}
										txtSubject.repaint();
										txtSubject.revalidate();
										
										try {
											preview(sbMsg((String) list.getSelectedValue()).toString());
										} catch (NullPointerException ex) {
											ex.printStackTrace();
										}
									}
								}
							});
						}
					}
					{
						JXPanel panMessageLog = new JXPanel(new BorderLayout(5, 5)); 
						panLine.add(panMessageLog); 
						
						{
							{
								JTextArea txtLog = new JTextArea(""); 
								txtLog.setEditable(false);
								txtLog.setBackground(Color.BLACK);
								txtLog.setForeground(Color.WHITE);
								txtLog.setLineWrap(true);
								txtLog.setFont(new Font("Tahoma", Font.PLAIN, 10));

								JScrollPane scrLog = new JScrollPane(txtLog);
								panMessageLog.add(scrLog, BorderLayout.CENTER);
								scrLog.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							}
							{
								pbar = new JProgressBar(JProgressBar.HORIZONTAL); 
								panMessageLog.add(pbar, BorderLayout.PAGE_END); 
								pbar.setPreferredSize(new Dimension(0, 30));
								pbar.setStringPainted(true);
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panPreview.add(panCenter, BorderLayout.CENTER);	
				{
					{
						JXPanel panBody = new JXPanel(new BorderLayout(5, 5)); 
						panCenter.add(panBody, BorderLayout.CENTER); 
						{
							{
								JXPanel panCoin = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panBody.add(panCoin, BorderLayout.PAGE_START);
								panCoin.setPreferredSize(new Dimension(0, 120));
								{
									{
										JXPanel panSender = new JXPanel(new GridLayout(3, 1, 5, 5)); 
										panCoin.add(panSender, BorderLayout.PAGE_START); 
										panSender.setBorder(JTBorderFactory.createTitleBorder("Sender", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												JXPanel panSenderEmail = new JXPanel(new BorderLayout(5, 5)); 
												panSender.add(panSenderEmail); 
												{
													{
														JLabel lblRecipient = new JLabel("Sender Email"); 
														panSenderEmail.add(lblRecipient, BorderLayout.LINE_START); 
														lblRecipient.setHorizontalAlignment(JLabel.LEADING);
														lblRecipient.setPreferredSize(new Dimension(125, 0));
														lblRecipient.setFont(font11);
													}
													{
														txtSenderEmail = new JTextField("cenqhomesdevcorp_loansdept@yahoo.com"); 
														panSenderEmail.add(txtSenderEmail, BorderLayout.CENTER); 
														txtSenderEmail.setHorizontalAlignment(JTextField.CENTER);
														txtSenderEmail.setFont(font11);
													}
												}
											}
											{
												JXPanel panPassword = new JXPanel(new BorderLayout(5, 5)); 
												panSender.add(panPassword); 
												{
													{
														JLabel lblRecipient = new JLabel("Password"); 
														panPassword.add(lblRecipient, BorderLayout.LINE_START); 
														lblRecipient.setHorizontalAlignment(JLabel.LEADING);
														lblRecipient.setPreferredSize(new Dimension(125, 0));
														lblRecipient.setFont(font11);
													}
													{
														//txtPassword = new JPasswordField("plrmpcpvozfkbktz");
														txtPassword = new JPasswordField("gsztppnnocrxipar");
														panPassword.add(txtPassword, BorderLayout.CENTER); 
														txtPassword.setHorizontalAlignment(JTextField.CENTER);
														txtPassword.setFont(font11);
													}
												}
											}
											{
												JXPanel panConnection = new JXPanel(new BorderLayout(5, 5)); 
												panSender.add(panConnection); 
												{
													{
														JLabel lblStatus = new JLabel("Connection Status"); 
														panConnection.add(lblStatus, BorderLayout.LINE_START); 
														lblStatus.setHorizontalAlignment(JLabel.LEADING);
														lblStatus.setPreferredSize(new Dimension(125, 0));
														lblStatus.setFont(font11);
													}
													{
														lblStatus = new JLabel("Not Connected"); 
														panConnection.add(lblStatus, BorderLayout.CENTER); 
														lblStatus.setHorizontalAlignment(JTextField.CENTER);
														lblStatus.setFont(font11);
														lblStatus.setOpaque(true);
														lblStatus.setBorder(BorderFactory.createLineBorder(Color.GRAY));
														statusMail(false); 
													}
												}
											}
										}
									}
									{
										JXPanel panReceiver = new JXPanel(new GridLayout(2, 1, 5, 5)); 
										panCoin.add(panReceiver, BorderLayout.PAGE_START); 
										panReceiver.setBorder(JTBorderFactory.createTitleBorder("Receiver", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												JXPanel panRecipient = new JXPanel(new BorderLayout(5, 5)); 
												panReceiver.add(panRecipient); 
												{
													{
														JLabel lblRecipient = new JLabel("Recipient"); 
														panRecipient.add(lblRecipient, BorderLayout.LINE_START); 
														lblRecipient.setHorizontalAlignment(JLabel.LEADING);
														lblRecipient.setPreferredSize(new Dimension(75, 0));
														lblRecipient.setFont(font11);
													}
													{
														txtRecipient = new JTextArea(""); 
														panRecipient.add(txtRecipient, BorderLayout.CENTER); 
														txtRecipient.setFocusable(true);
														txtRecipient.setEditable(true);
														txtRecipient.setFont(font11);
													}
												}
											}
											{
												JXPanel panCC = new JXPanel(new BorderLayout(5, 5)); 
												panReceiver.add(panCC); 
												{
													{
														JLabel lblCC = new JLabel("CC"); 
														panCC.add(lblCC, BorderLayout.LINE_START); 
														lblCC.setHorizontalAlignment(JLabel.LEADING);
														lblCC.setPreferredSize(new Dimension(75, 0));
														lblCC.setFont(font11);
													}
													{
														txtCC = new JTextArea("servicing_cad@pagibigfund.gov.ph"); 
														panCC.add(txtCC, BorderLayout.CENTER); 
														txtCC.setFont(font11);
														txtCC.setEditable(true);
													}
												}
											}
										}
									}
								}
							}
							{
								JXPanel panContent = new JXPanel(new BorderLayout(5, 5)); 
								panBody.add(panContent, BorderLayout.CENTER); 
								panContent.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									editorMail = new JEditorPane(); 
									editorMail.setFont(new Font("Tahoma", Font.PLAIN, 6));
									editorMail.setContentType("text/html");
									editorMail.setEditable(false);
									editorMail.setBorder(new EmptyBorder(5, 5, 5, 5));

									JScrollPane scrMail = new JScrollPane(editorMail); 
									panContent.add(scrMail, BorderLayout.CENTER);	
								}
							}
							{
								JXPanel panOther = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panBody.add(panOther, BorderLayout.PAGE_END); 
								panOther.setPreferredSize(new Dimension(0, 60));
								{
									{
										JXPanel panSubject = new JXPanel(new BorderLayout(5, 5)); 
										panOther.add(panSubject); 
										panSubject.setBorder(JTBorderFactory.createTitleBorder("Subject", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												JLabel lblSubject = new JLabel("Subject"); 
												panSubject.add(lblSubject, BorderLayout.LINE_START); 
												lblSubject.setHorizontalAlignment(JLabel.LEADING);
												lblSubject.setPreferredSize(new Dimension(75, 0));
												lblSubject.setFont(font11);
											}
											{
												txtSubject = new JTextField("EMPLOYMENT VERIFICATION - "); 
												panSubject.add(txtSubject, BorderLayout.CENTER); 
												txtSubject.setHorizontalAlignment(JTextField.CENTER);
												txtSubject.setFocusable(false);
												txtSubject.setEditable(false);
												txtSubject.setFont(font11);
											}
										}
									}
									{
										JXPanel panAttachment = new JXPanel(new BorderLayout(5, 5)); 
										panOther.add(panAttachment, BorderLayout.CENTER); 
										panAttachment.setBorder(JTBorderFactory.createTitleBorder("Attachment", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
										{
											{
												{
													txtAttachment = new JTextField(""); 
													panAttachment.add(txtAttachment, BorderLayout.CENTER); 
													txtAttachment.setHorizontalAlignment(JTextField.CENTER);
													txtAttachment.setFocusable(false);
													txtAttachment.setEditable(false);
													txtAttachment.setFont(font11);
												}
											}
											{
												btnOpen = new JButton("Open"); 
												panAttachment.add(btnOpen, BorderLayout.LINE_END); 
												btnOpen.setFont(font11);
												btnOpen.setEnabled(true);
												btnOpen.setPreferredSize(new Dimension(50, 0));
												btnOpen.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {
														txtAttachment.setText(OpenDir()); 
														mapAttachment((String) list.getSelectedValue().toString(), txtAttachment.getText());
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
						JXPanel panButtons = new JXPanel(new GridLayout(1, 2, 5, 5)); 
						panCenter.add(panButtons, BorderLayout.PAGE_END); 
						panButtons.setPreferredSize(new Dimension(0, 30));
						{
							{
								btnSend = new JButton("Send"); 
								panButtons.add(btnSend, BorderLayout.CENTER); 
								btnSend.setFont(font11);
								btnSend.setEnabled(false);
								btnSend.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										if (JOptionPane.showConfirmDialog(FncGlobal.homeMDI, "Email will be sent. Proceed?", "Send One", 
												JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
											send(); 
										}	
									}
								});
							}
							{
								btnSendAll = new JButton("Send All"); 
								panButtons.add(btnSendAll, BorderLayout.CENTER); 
								btnSendAll.setFont(font11);
								btnSendAll.setEnabled(false);
								btnSendAll.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										if (JOptionPane.showConfirmDialog(FncGlobal.homeMDI, "All emails will be sent. Proceed?", "Send All", 
												JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
											send_all();
										}
									}
								});
							}
						}

					}
				}
			}
		}
		
		verifyCredentials(); 
	}

	public void addList(String[] strName) {
		DefaultListModel<String> listModel = new DefaultListModel<>();  

		try {
			for (String strItem : strName) {
				listModel.addElement(strItem);
			}	
		} catch (NullPointerException ex) {
			System.out.println("exception caught on initialization");
		}

		list.setModel(listModel);
		list.repaint();
		list.revalidate();
	}

	public static StringBuffer sbMsg(String strName) {
		String strLastName = FncGlobal.GetString("select last_name from rf_entity where entity_name = '"+strName+"'");
		String strPosition = hashPosition.get(strName);
		String strEmploymentDate = hashEmploymentDate.get(strName).toString();
		String strCOE = ""; 
		
		try {
			strCOE = FncGlobal.GetString("select '"+hashCOE.get(strName).toString()+"'::date::varchar"); 
		} catch (NullPointerException ex) {
			strCOE = "";
		}

		String strMrMsMrs = FncGlobal.GetString("select (case when gender = 'M' then 'Mr.' else (case when civil_status_code = 'M' then 'Mrs.' else 'Ms.' end) end) \n" + 
				"from rf_entity \n" + 
				"where entity_name = '"+strName+"'"); 

		StringBuffer sb = new StringBuffer();
		sb.append("<p>"+FncGlobal.getDateSQL()+"</p>");
		sb.append("<br>");
		sb.append("<br>");		
		sb.append("<p>Dear Sir/Ma'am, </p>");
		sb.append("<br>");
		sb.append("<p>We, CenQhomes Development Corporation is currently evaluating the housing loan application of our buyer <b>"+strName+"</b>.</p>");
		sb.append("<p>In this regard, may we verify if "+strMrMsMrs+" "+strLastName+" is still connected with your company as "+strPosition+" since "+strEmploymentDate+".</p>");
		sb.append("<p>May we also verify if you issued a <b>CERTIFICATE OF EMPLOYMENT WITH COMPENSATION</b> for his/her account dated <b>"+((strCOE.equals(""))?"[DATE NOT SPECIFIED]":strCOE)+"<b/>.</p>");
		sb.append("<p>Please see attached file for the submitted copy of <b>CERTIFICATE OF EMPLOYMENT WITH COMPENSATION AND AUTHORIZATION TO CONDUCT EMPLOYMENT VERIFICATION</b>.</p>");
		sb.append("<br>");
		sb.append("<p>Hoping for your prompt response.</p>");
		sb.append("<p>Thank you.</p>");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("<p>Note: Kindly reply on all email addresses on this thread.</p>");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("<p>JOVIE GALAN.</p>");
		sb.append("<p>Loans and Receivable Management Department</p>");
		sb.append("<p>Cenqhomes Development Corporation</p>");
		sb.append("<p>8632-2608/8534-2237 loc. 1013</p>");

		System.out.println("");
		System.out.println("sb: "+sb);
		
		return sb;
	}

	private void preview(String strContent) {
		editorMail.setText(strContent);
		editorMail.repaint();
	}
	
	private void verifyCredentials() {
		
		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent act) {
				loginTrial(); 
			}
		};
		
		timer = new Timer(60000, timerListener);
		timer.setInitialDelay(0);
		timer.start();

	}
	
	private void loginTrial() {
    	SwingWorker sw = new SwingWorker() {

			@SuppressWarnings("deprecation")
			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				Properties props = System.getProperties();
		        
		        final String from = txtSenderEmail.getText();
		        final String password = txtPassword.getText();
		        
		        props.put("mail.smtp.starttls.enable", "true");
		        props.put("mail.smtp.host", "smtp.mail.yahoo.com");
		        props.put("mail.smtp.user", from);
		        props.put("mail.smtp.password", password);
		        props.put("mail.smtp.port", "587");
		        props.put("mail.smtp.auth", "true");
		        props.put("mail.imap.ssl.enable", "true");
		        
		        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		        	protected PasswordAuthentication getPasswordAuthentication() {
		        		return new PasswordAuthentication(from, password);
		        	}
		        });
		        
		        Transport transport = null;
		        
				try {
					transport = session.getTransport("smtp");
				} catch (NoSuchProviderException e1) {
					
				}
				
		    	try {
					transport.connect("smtp.mail.yahoo.com", from, password);
					statusMail(true); 
				} catch (Exception ex) {
					statusMail(false); 
				}
				return null;
			}
    	}; 
    	sw.execute(); 
	}
	
	private void statusMail(Boolean blnRev) {		
		if (blnRev) {
			lblStatus.setText("Connected");
			lblStatus.setBackground(colorConnected);
		} else {
			lblStatus.setText("Not Connected");
			lblStatus.setBackground(colorNotConnected);
		}
		
		try {
			btnSend.setEnabled(blnRev);
			btnSendAll.setEnabled(blnRev);
			btnOpen.setEnabled(blnRev);
		} catch (NullPointerException ex) {
			
		}
		
		lblStatus.repaint();
		lblStatus.revalidate();
	}
	
	private void removeItem() {
		DefaultListModel model = (DefaultListModel) list.getModel();
		int selectedIndex = list.getSelectedIndex();
		
		if (selectedIndex != -1) {
		    model.remove(selectedIndex);
		}
	}
	
	private void send() {
		controlstate(false); 
		
		System.out.println("Index: "+list.getSelectedIndex());
		
		if (list.getSelectedIndex()==-1) {
			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select a client from the list.");
		} else {
			try {
				if (hashCOE.get((String) list.getSelectedValue())==null || 
						hashPosition.get((String) list.getSelectedValue())==null || 
						hashPosition.get((String) list.getSelectedValue()).equals("") || 
						hashEmploymentDate.get((String) list.getSelectedValue())==null
						) {
					JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Email sending failed! Check position or COE.");
				} else {
					if (sendFromMailWithAttachment((String) list.getSelectedValue())) {
						removeItem(); 
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Email sent!");
					} else {
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Email sending failed!");
					}	
				}
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
			} catch (NullPointerException ex) {
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select a client from the list.");
			}
		}
		
		if (list.getModel().getSize()==0) {
			hdmfMon_qualifyAccounts.dispose(); 
		}
		
		controlstate(true);
		
		/*
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				controlstate(false); 
				
				System.out.println("Index: "+list.getSelectedIndex());
				
				if (list.getSelectedIndex()==-1) {
					JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select a client from the list.");
				} else {
					try {
						if (hashCOE.get((String) list.getSelectedValue())==null || 
								hashPosition.get((String) list.getSelectedValue())==null || 
								hashPosition.get((String) list.getSelectedValue()).equals("") || 
								hashEmploymentDate.get((String) list.getSelectedValue())==null
								) {
							JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Email sending failed! Check position or COE.");
						} else {
							if (sendFromMailWithoutAttachment((String) list.getSelectedValue())) {
								removeItem(); 
								JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Email sent!");
							} else {
								JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Email sending failed!");
							}	
						}
					} catch (NoSuchProviderException e) {
						e.printStackTrace();
					} catch (NullPointerException ex) {
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select a client from the list.");
					}
				}
				
				if (list.getModel().getSize()==0) {
					hdmfMon_qualifyAccounts.dispose(); 
				}
				
				controlstate(true);
				return null;
			}
    	}; 
    	sw.execute(); 
    	*/
	}
	
	private void send_all() {
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				DefaultListModel model = (DefaultListModel) list.getModel();
				
				pbar.setValue(0);
				pbar.setMaximum(model.getSize());
				
				controlstate(false); 
				
				for (int x=0; x<model.getSize(); x++) {
					list.setSelectedIndex(x);
					
					System.out.println("");
					System.out.println("list.getSelectedValue().toString(): "+list.getSelectedValue().toString());
					
					if (hashCOE.get((String) list.getSelectedValue())==null || 
							hashPosition.get((String) list.getSelectedValue())==null || 
							hashPosition.get((String) list.getSelectedValue()).equals("") || 
							hashEmploymentDate.get((String) list.getSelectedValue())==null) {
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Email sending failed! Check position or COE.");
					} else {
						try {
							sendFromMailWithoutAttachment(list.getSelectedValue().toString());
						} catch (NoSuchProviderException e1) {
							e1.printStackTrace();
						} 	
					}
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					pbar.setValue(x+1);
				}
				
				hdmfMon_qualifyAccounts.dispose(); 
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Finished sending!");
				
				return null;
			}
    	}; 
    	sw.execute(); 
	}
	
	private void controlstate(Boolean blnRev) {
		txtSenderEmail.setFocusable(blnRev);
		txtPassword.setFocusable(blnRev);
		
		txtSenderEmail.setEditable(blnRev);
		txtPassword.setEditable(blnRev);
		
		list.setFocusable(blnRev);
		list.setEnabled(blnRev);

		txtRecipient.setFocusable(blnRev);
		txtRecipient.setEditable(blnRev);			

		btnSend.setEnabled(blnRev);
		btnSendAll.setEnabled(blnRev);		
	}
	
	@SuppressWarnings("deprecation")
	public static Boolean sendFromMailWithoutAttachment(String strName) throws NoSuchProviderException {
		
		final String from = txtSenderEmail.getText(); 
		final String password = txtPassword.getText();
		
		final String to = hashRecipient.get(strName);
		final String subject = "EMPLOYMENT VERIFICATION - "+strName; 
		final String html = sbMsg(strName).toString(); 

        System.out.println("");
        System.out.println("from: "+from);
        System.out.println("message: "+html);
        
        Boolean blnSent = true;
        
        Properties props = System.getProperties();
        String host = "smtp.mail.yahoo.com";

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.imap.ssl.enable", "true");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(from, password);
        	}
        });
        
        Transport transport = session.getTransport("smtp");
    	try {
			transport.connect(host, from, password);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	
        MimeMessage message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(from));
            
            if (!to.equals(txtRecipient.getText())) {
                String[] strTo = txtRecipient.getText().split(","); 
                
                for (int x=0; x<strTo.length; x++) {
                    InternetAddress toAddress = new InternetAddress();
                    toAddress = new InternetAddress(strTo[x].trim());
                    
                    System.out.println("");
                    System.out.println("toAddress: "+toAddress);
                    
                	message.addRecipient(Message.RecipientType.TO, toAddress);
                }
                
            } else {
                InternetAddress toAddress = new InternetAddress();
                toAddress = new InternetAddress(to);
                
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }
            
            String[] strCC = txtCC.getText().split(","); 
            
            for (int x=0; x<strCC.length; x++) {
            	System.out.println("CC "+x); 
            	System.out.println("item "+x+": "+strCC[x].trim());
            	message.addRecipients(Message.RecipientType.CC, strCC[x].trim());
            }
            
            message.setSubject(subject);
            message.setContent(html, "text/html");
            
            /*
            transport.sendMessage(message, message.getAllRecipients());            
            transport.close();
            */
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }

        return blnSent;
    }
	
	public static String OpenDir() {
		String strDir = "";
		
		JFileChooser chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Folder");
		
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		chooser.setAcceptAllFileFilterUsed(false);
    
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){ 
			System.out.println(chooser.getSelectedFile().toString());
			strDir = chooser.getSelectedFile().toString();
		}
		else{
			System.out.println("No Selection ");
		}
		
		return strDir;
	}
	
	@SuppressWarnings("deprecation")
	public static Boolean sendFromMailWithAttachment(String strName) throws NoSuchProviderException {
		final String from = txtSenderEmail.getText(); 
		final String password = txtPassword.getText();
		
		final String to = txtRecipient.getText(); 
		final String cc = txtCC.getText(); 
		
		final String subject = "EMPLOYMENT VERIFICATION - "+strName; 
		final String html = sbMsg(strName).toString(); 
		
		Properties props = System.getProperties();  
		props.setProperty("mail.smtp.host", "smtp.mail.yahoo.com");  
		props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.imap.ssl.enable", "true");
		
        Session session = Session.getDefaultInstance(props,  
    		new javax.mail.Authenticator() {  
    		protected PasswordAuthentication getPasswordAuthentication() {  
    			return new PasswordAuthentication(from, password);  
    		}  
        });  
        
        try {
            MimeMessage message = new MimeMessage(session);  
            message.setFrom(new InternetAddress(from));  

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(txtRecipient.getText())); 
            message.setSubject(subject);              
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(txtCC.getText()));
            
            BodyPart messageBodyPart1 = new MimeBodyPart();  
            messageBodyPart1.setText(html);  
            
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
            
            String filename = hashAttachment.get((String) list.getSelectedValue()); 
            FileDataSource source = new FileDataSource(filename);  
            messageBodyPart2.setDataHandler(new DataHandler(source));  
            messageBodyPart2.setFileName(filename);  
            
            Multipart multipart = new MimeMultipart();  
            multipart.addBodyPart(messageBodyPart1);  
            multipart.addBodyPart(messageBodyPart2);  
            
            message.setContent(multipart);
            
            Transport.send(message);
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
		}
    }
}
