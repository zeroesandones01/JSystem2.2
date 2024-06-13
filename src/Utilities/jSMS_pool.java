package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameEvent;

import org.jdesktop.swingx.JXPanel;
import org.springframework.core.env.JOptCommandLinePropertySource;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Functions.sms_builder;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_sms_pool;

public class jSMS_pool extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 1324113651457724763L;
	static String title = "SMS Pool";
	Dimension frameSize = new Dimension(947, 600);
	
	public static JList rowSMS;
	private _JTableMain tblSMS;
	private JScrollPane scrSMS;
	private model_sms_pool modelSMS;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat sdtf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	private JScrollPane scrMessage;
	private JScrollPane scrLog;
	private JScrollPane scrResponse;
	
	private JTextArea txtMessage;
	private JTextArea txtLog;
	private JTextArea txtResponse;
	private JTextArea txtAndroid;
	
	private String[] toppings = new String[0];
	private Integer intPart;
	
	private TimerTask tmr;
	
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JTextField txtDevice;
	private JTextField txtURL;
	private JTextField txtDeviceResponse;
	private JTextField txtUrlResponse;
	
	private Timer timerDeviceCheck; 
	private Timer timerUSBCheck;
	private Timer timerReload; 
	private Timer timerExecute;
	private Timer timerAndroid;
	
	@SuppressWarnings("unused")
	private Boolean timerReloadActive = false; 
	@SuppressWarnings("unused")
	private Boolean timerExecuteActive = false; 
	
	private JToggleButton togSMS;
	private Font fontPlainSanSer11 = new Font("SansSerif", Font.PLAIN, 11);
	
	private JXPanel panPage; 
	private JTextField txtModel;
	private JTextField txtBattery;
	private JTextField txtNetwork;
	private JTextField txtConnection;
	
	private JXPanel panParameters1;
	private JXPanel panParameters2;
	
	private JLabel lblMode; 
	
	@SuppressWarnings("deprecation")
	private static Integer intHour = new Date().getHours(); 
	@SuppressWarnings("deprecation")
	private static Integer intMinute = new Date().getMinutes(); 
	@SuppressWarnings("unused")
	private static Integer[] intContractor = null;
	@SuppressWarnings("unused")
	private static Integer[][] intUnits = null; 
	
	public jSMS_pool() {
		super(title, false, true, false, false);
		initGUI();
	}

	public jSMS_pool(String title) {
		super(title);
		initGUI();
	}

	public jSMS_pool(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setSize(frameSize);
		this.setTitle(title);

		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				panPage = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 90));
				{
					CreateUSBMode_parameters(); 
					CreateGateMode_parameters();
					{
						panPage.add(panParameters1, BorderLayout.CENTER); 
					}
					{
						JXPanel panMode = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panMode, BorderLayout.LINE_END);
						panMode.setBorder(new EmptyBorder(0, 0, 0, 0));
						panMode.setPreferredSize(new Dimension(200, 0));
						{
							lblMode = new JLabel("Gate Mode"); 
							panMode.add(lblMode, BorderLayout.CENTER);
							lblMode.setBorder(BorderFactory.createRaisedBevelBorder());
							lblMode.setHorizontalAlignment(JLabel.CENTER);
							lblMode.setForeground(Color.BLACK);
							lblMode.addMouseListener(new MouseListener() {
								public void mouseReleased(MouseEvent e) {
									
								}

								public void mousePressed(MouseEvent e) {
									
								}
								
								public void mouseExited(MouseEvent e) {
									
								}
								
								public void mouseEntered(MouseEvent e) {
									
								}
								
								public void mouseClicked(MouseEvent e) {
									
									if (lblMode.getBorder()==BorderFactory.createLoweredSoftBevelBorder()) {
										lblMode.setBorder(BorderFactory.createRaisedBevelBorder());
										lblMode.setText("Gate Mode");
										panPage.remove(panParameters2);
										panPage.add(panParameters1, BorderLayout.CENTER); 
										
										System.out.println("Mode: "+lblMode.getText());
									} else {
										lblMode.setBorder(BorderFactory.createLoweredSoftBevelBorder());
										lblMode.setText("USB Mode");
										panPage.remove(panParameters1);
										panPage.add(panParameters2, BorderLayout.CENTER);
										//Comment by Erick 2023-03-17
										/*try {
											GetMobileProperties();
										} catch (InterruptedException | IOException e1) {
											e1.printStackTrace();
										}*/ 
										
										System.out.println("Mode: "+lblMode.getText());
									}
									
									timerExecute.cancel();
									timerExecute.purge(); 
									
									if (lblMode.getText().equals("USB Mode")) {
										ExecuteUSB();
									} else {
										Execute();
									}
									
									panPage.repaint();
								}
							});
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					scrSMS = new JScrollPane();
					panMain.add(scrSMS, BorderLayout.CENTER);
					{
						{
							modelSMS = new model_sms_pool();
							tblSMS = new _JTableMain(modelSMS);
							scrSMS.setViewportView(tblSMS);
							scrSMS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							
							tblSMS.getColumnModel().getColumn(0).setPreferredWidth(50);
							tblSMS.getColumnModel().getColumn(1).setPreferredWidth(50);
							tblSMS.getColumnModel().getColumn(2).setPreferredWidth(81);
							tblSMS.getColumnModel().getColumn(3).setPreferredWidth(260);
							tblSMS.getColumnModel().getColumn(4).setPreferredWidth(200);
							tblSMS.getColumnModel().getColumn(5).setPreferredWidth(71);
							tblSMS.getColumnModel().getColumn(6).setPreferredWidth(80);
							tblSMS.getColumnModel().getColumn(7).setPreferredWidth(80);
							
							tblSMS.setSortable(false);
							tblSMS.getColumnModel().getColumn(6).setCellRenderer(new DateRenderer(stf));
							
							tblSMS.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									ShowMessage(); 
								}

								public void mouseReleased(MouseEvent e) {
									ShowMessage(); 
								}
								
								private void ShowMessage() {
									txtMessage.setText(FncGlobal.GetString("select message from rf_sms_batch where msg_id = '"+modelSMS.getValueAt(tblSMS.getSelectedRow(), 1)+"'"));
								}
							});
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
			{
				JXPanel panEnd = new JXPanel(new GridLayout(1, 3, 5, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 250));
				{
					{
						JXPanel panMsgRes = new JXPanel(new GridLayout(2, 1, 5, 5)); 
						panEnd.add(panMsgRes); 
						panMsgRes.setPreferredSize(new Dimension(300, 0));
						{
							{
								{
									txtMessage = new JTextArea(""); 
									txtMessage.setEditable(false);
									txtMessage.setLineWrap(true);
									txtMessage.setWrapStyleWord(true);
									txtMessage.setFont(new Font("Tahoma", Font.PLAIN, 12));
								}
								{
									scrMessage = new JScrollPane(txtMessage);
									panMsgRes.add(scrMessage, BorderLayout.LINE_START); 
									scrMessage.setBorder(JTBorderFactory.createTitleBorder("Message", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
									scrMessage.setPreferredSize(new Dimension(300, 0));
								}
							}
							{
								{
									txtResponse = new JTextArea(""); 
									txtResponse.setEditable(false);
									txtResponse.setBackground(Color.BLACK);
									txtResponse.setForeground(Color.WHITE);
									txtResponse.setLineWrap(true);
									txtResponse.setFont(new Font("Tahoma", Font.PLAIN, 11));
								}
								{
									scrResponse = new JScrollPane(txtResponse);
									panMsgRes.add(scrResponse, BorderLayout.LINE_START); 
									scrResponse.setBorder(JTBorderFactory.createTitleBorder("Gateway Response", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
									scrResponse.setPreferredSize(new Dimension(300, 0));
								}
							}
						}
					}
					{
						JXPanel panAndroidScript = new JXPanel(new BorderLayout(5, 5)); 
						panEnd.add(panAndroidScript);
						panAndroidScript.setBorder(JTBorderFactory.createTitleBorder("CMD Notification", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								txtAndroid = new JTextArea(""); 
								txtAndroid.setEditable(false);
								txtAndroid.setLineWrap(true);
								txtAndroid.setWrapStyleWord(true);
								txtAndroid.setFont(new Font("Tahoma", Font.PLAIN, 10));
							}
							{
								JScrollPane scrAndroid = new JScrollPane(txtAndroid);
								panAndroidScript.add(scrAndroid, BorderLayout.CENTER); 
								
								scrAndroid.setPreferredSize(new Dimension(300, 0));
							}
						}
					}
					{
						JXPanel panLogNPause = new JXPanel(new BorderLayout(5, 5)); 
						panEnd.add(panLogNPause);
						{
							{
								{
									txtLog = new JTextArea(""); 
									txtLog.setEditable(false);
									txtLog.setBackground(Color.BLACK);
									txtLog.setForeground(Color.WHITE);
									txtLog.setLineWrap(true);
									txtLog.setFont(new Font("Tahoma", Font.PLAIN, 10));
								}
								{
									scrLog = new JScrollPane(txtLog);
									panLogNPause.add(scrLog, BorderLayout.CENTER);
									scrLog.setBorder(JTBorderFactory.createTitleBorder("SMS Log", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								}
							}
							{
								togSMS = new JToggleButton("SLEEP"); 
								panLogNPause.add(togSMS, BorderLayout.PAGE_END); 
								togSMS.setSelected(false);
								togSMS.setPreferredSize(new Dimension(0, 30));
								togSMS.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										timerExecute.cancel();
										timerExecute.purge(); 
										
										if (togSMS.isSelected()) {
											togSMS.setText("ACTIVATE");
											addLog("Sleep mode activated"); 
										} else {
											togSMS.setText("SLEEP");
											addLog("Sleep mode deactivated");
										}
										
										if (lblMode.getText().equals("USB Mode")) {
											ExecuteUSB();
										} else {
											Execute();
										}
									}
								});
								togSMS.setFont(new Font(togSMS.getFont().getName(), togSMS.getFont().getStyle(), togSMS.getFont().getSize()-1));
								togSMS.setFocusable(false);
							}
						}
					}
				}
			}
		}

		togSMS.setSelected(false);
		
		Reload();
		
		if (lblMode.getText().equals("USB Mode")) {
			ExecuteUSB();
		} else {
			Execute();
		}
		
		//DeviceCheck(); Comment by Erick 2023-03-17 No need for device check.
		//USBCheck(); Comment by Erick 2023-03-17 
		InitiateAndroid(); 
	}

	private void LoadMessages() {//Display Pending/failed messages
		pgSelect db = new pgSelect();
		
		addLog("Refreshing message pool..."); 
		
		FncTables.clearTable(modelSMS);
		DefaultListModel listModel = new DefaultListModel();
		rowSMS.setModel(listModel); 
		
		String strSQL = "select x.pool_row_id, x.msg_id, (select login_id from rf_system_user a where a.emp_code = y.sent_by) as recipient, \n" + 
				"z.entity_name, y.cellphone, (case when x.msg_status = 'P' then 'PENDING' when x.msg_status = 'F' then 'FAILED' else 'SENT' end) as status, y.date_sent::time, y.date_sent::date \n" + 
				"from rf_sms_pool x \n" + 
				"inner join rf_sms_batch y on x.msg_id = y.msg_id and x.msg_status =y.msg_status\n" + 
				"left join rf_entity z on y.entity_id = z.entity_id \n" + 
				"where y.msg_row_id != '72230' \n"+
				//"and x.msg_status in ('P','F')  \n"+//Edited by Erick 2023/01/27
				"and x.msg_status in ('P')  \n"+//Edited by Erick 2023/01/27
				"and x.date_queued::date > '2022/12/31'\n"+ //Added by Erick 2023/01/27
				"order by x.msg_id  \n" +
				"limit 3000"; 
		
		db.select(strSQL);
		
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelSMS.addRow(db.getResult()[x]);
				listModel.addElement(modelSMS.getRowCount());
			}
			
			addLog("Message pool refreshed..."); 
		} else {
			addLog("No message was retrieved...");
		}
	}
	
	private void StackMessage(String strMsg) {
		String strPart = null; 
		Integer intText;
		Integer intModulo;
		Integer intDividend;
		Integer intDivisor;
		Integer intQuotient;
		
		strMsg = strMsg.replace("\n", "%0A");
		intText = strMsg.length();
		
		addLog("Distributing message into array...");
		
		if (intText>231) {
			if (intText%Integer.parseInt("231")>0) {
				intModulo = 1; 
			} else {
				intModulo = 0; 
			}
			
			intDividend = strMsg.length(); 
			intDivisor = Integer.parseInt("231"); 
			intQuotient = intDividend / intDivisor; 
			intPart = intQuotient + intModulo; 
		} else {
			strPart = ""; 
			intPart = 1;
		}

		addLog("Message distributed into " + intPart + " part(s)...");
		
		toppings = new String[intPart];
		strPart = strMsg; 
		
		Integer intArr = 0; 
		for (int i = 0; i < strPart.length(); i += 231) {
			toppings[intArr]=strPart.substring(i, Math.min(i + 231, strPart.length()));
			intArr++;
		}
	}
	
	private void addLog(String strLog) {
		if (txtLog.getText().length()>=5000) {
			txtLog.setText("");
		} 
		
		txtLog.setText(txtLog.getText() + "("+sdtf.format(System.currentTimeMillis()).toString()+"): " + strLog + "\n");
		txtLog.setCaretPosition(txtLog.getDocument().getLength());
	}
	
	private void Reload() {
		TimerTask tmr = new TimerTask() {

			public void run() {
				LoadMessages();
				timerReloadActive = true; 
			}
		};

		timerReload = new Timer();
		long delay = 0;
		long intevalPeriod = 120 * 1000; 
	
		timerReload.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}
	
	private void DeviceCheck() {
		TimerTask devtmr = new TimerTask() {

			public void run() {
				addLog("Checking gateway connection...");

				String strDevice = txtDevice.getText(); 
				try {
					if (lblMode.getText().equals("USB Mode")) {
						GetMobileProperties(); 
					} else {
						txtResponse.setText(sms_builder.FetchSingleDevice(strDevice).toString());
					}
					
					addLog("Gateway connection available...");
				} catch (Exception e) {
					addLog("Gateway connection not available...");
				}
			}
		};

		timerDeviceCheck = new Timer();
		long delay = 0;
		long intevalPeriod = 90 * 1000; 
	
		timerDeviceCheck.scheduleAtFixedRate(devtmr, delay, intevalPeriod);
	}
	
	private void USBCheck() {
		TimerTask devtmr = new TimerTask() {

			public void run() {
				try {
					GetMobileProperties();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		};

		timerUSBCheck = new Timer();
		long delay = 0;
		long intevalPeriod = 1000; 
	
		timerUSBCheck.scheduleAtFixedRate(devtmr, delay, intevalPeriod);
	}
	
	public void windowClosing(InternalFrameEvent e) {
		JOptionPane.showMessageDialog(null, "This frame will close.");
		timerExecute.cancel();
		timerExecute.purge(); 
		
		timerReload.cancel();
		timerReload.purge(); 
		
		timerDeviceCheck.cancel();
		timerDeviceCheck.purge(); 
		
	    System.exit(0);
	}
	
	private void CreateGateMode_parameters() {
		panParameters1 = new JXPanel(new GridLayout(2, 2, 5, 5)); 
		panParameters1.setBorder(JTBorderFactory.createTitleBorder("Parameters", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
		{
			{
				JXPanel panEmail = new JXPanel(new BorderLayout(5, 5)); 
				panParameters1.add(panEmail); 
				{
					{
						JLabel lblEmail = new JLabel("Email"); 
						panEmail.add(lblEmail, BorderLayout.LINE_START); 
						lblEmail.setHorizontalAlignment(JLabel.LEADING);
						lblEmail.setPreferredSize(new Dimension(100, 0));
						lblEmail.setFont(fontPlainSanSer11);
					}
					{
						txtEmail = new JTextField("sptacct98@gmail.com"); 
						panEmail.add(txtEmail, BorderLayout.CENTER); 
						txtEmail.setHorizontalAlignment(JTextField.CENTER);
						txtEmail.setEditable(false);
						txtEmail.setFont(fontPlainSanSer11);
					}
				}
			}
			{
				JXPanel panDevice = new JXPanel(new BorderLayout(5, 5)); 
				panParameters1.add(panDevice); 
				{
					{
						JLabel lblDevice = new JLabel("Device"); 
						panDevice.add(lblDevice, BorderLayout.LINE_START); 
						lblDevice.setHorizontalAlignment(JLabel.CENTER);
						lblDevice.setPreferredSize(new Dimension(75, 0));
						lblDevice.setFont(fontPlainSanSer11);
					}
					{
						txtDevice = new JTextField("97145"); 
						panDevice.add(txtDevice, BorderLayout.CENTER); 
						txtDevice.setHorizontalAlignment(JTextField.CENTER);
						txtDevice.setFont(fontPlainSanSer11);
					}
					{
						txtDeviceResponse = new JTextField("---"); 
						panDevice.add(txtDeviceResponse, BorderLayout.LINE_END); 
						txtDeviceResponse.setHorizontalAlignment(JTextField.CENTER);
						txtDeviceResponse.setEditable(false);
						txtDeviceResponse.setPreferredSize(new Dimension(30, 0));
						txtDeviceResponse.setFont(fontPlainSanSer11);
					}
				}
			}
			{
				JXPanel panPassword = new JXPanel(new BorderLayout(5, 5)); 
				panParameters1.add(panPassword); 
				{
					{
						JLabel lblPassword = new JLabel("Password"); 
						panPassword.add(lblPassword, BorderLayout.LINE_START); 
						lblPassword.setHorizontalAlignment(JLabel.LEADING);
						lblPassword.setPreferredSize(new Dimension(100, 0));
						lblPassword.setFont(fontPlainSanSer11);
					}
					{
						txtPassword = new JPasswordField(50); 
						panPassword.add(txtPassword, BorderLayout.CENTER); 
						txtPassword.setHorizontalAlignment(JTextField.CENTER);
						txtPassword.setEditable(false);
						txtPassword.setFont(fontPlainSanSer11);
						txtPassword.setText("hokage98");
					}
				}
			}
			{
				JXPanel panURL = new JXPanel(new BorderLayout(5, 5)); 
				panParameters1.add(panURL); 
				{
					{
						JLabel lblURL = new JLabel("URL"); 
						panURL.add(lblURL, BorderLayout.LINE_START); 
						lblURL.setHorizontalAlignment(JLabel.CENTER);
						lblURL.setPreferredSize(new Dimension(75, 0));
						lblURL.setFont(fontPlainSanSer11);
					}
					{
						txtURL = new JTextField("http://smsgateway.me/"); 
						panURL.add(txtURL, BorderLayout.CENTER); 
						txtURL.setHorizontalAlignment(JTextField.CENTER);
						txtURL.setFont(fontPlainSanSer11);
					}
					{
						txtUrlResponse = new JTextField("---"); 
						panURL.add(txtUrlResponse, BorderLayout.LINE_END); 
						txtUrlResponse.setHorizontalAlignment(JTextField.CENTER);
						txtUrlResponse.setEditable(false);
						txtUrlResponse.setPreferredSize(new Dimension(30, 0));
						txtUrlResponse.setFont(fontPlainSanSer11);
					}
				}
			}
		}
	}
	
	private void CreateUSBMode_parameters() {
		panParameters2 = new JXPanel(new GridLayout(2, 2, 5, 5)); 
		panParameters2.setBorder(JTBorderFactory.createTitleBorder("Parameters", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
		{
			{
				JXPanel panModel = new JXPanel(new BorderLayout(5, 5)); 
				panParameters2.add(panModel); 
				{
					{
						JLabel lblModel = new JLabel("Model"); 
						panModel.add(lblModel, BorderLayout.LINE_START); 
						lblModel.setHorizontalAlignment(JLabel.LEADING);
						lblModel.setPreferredSize(new Dimension(100, 0));
						lblModel.setFont(fontPlainSanSer11);
					}
					{
						txtModel = new JTextField(""); 
						panModel.add(txtModel, BorderLayout.CENTER); 
						txtModel.setHorizontalAlignment(JTextField.CENTER);
						txtModel.setEditable(false);
						txtModel.setFont(fontPlainSanSer11);
						txtModel.setBackground(Color.CYAN);
					}
				}
			}
			{
				JXPanel panBattery = new JXPanel(new BorderLayout(5, 5)); 
				panParameters2.add(panBattery); 
				{
					{
						JLabel lblBattery = new JLabel("Battery"); 
						panBattery.add(lblBattery, BorderLayout.LINE_START); 
						lblBattery.setHorizontalAlignment(JLabel.CENTER);
						lblBattery.setPreferredSize(new Dimension(75, 0));
						lblBattery.setFont(fontPlainSanSer11);
					}
					{
						txtBattery = new JTextField(""); 
						panBattery.add(txtBattery, BorderLayout.CENTER); 
						txtBattery.setHorizontalAlignment(JTextField.CENTER);
						txtBattery.setFont(fontPlainSanSer11);
						txtBattery.setBackground(Color.CYAN);
					}
				}
			}
			{
				JXPanel panNetwork = new JXPanel(new BorderLayout(5, 5)); 
				panParameters2.add(panNetwork); 
				{
					{
						JLabel lblNetwork = new JLabel("Network"); 
						panNetwork.add(lblNetwork, BorderLayout.LINE_START); 
						lblNetwork.setHorizontalAlignment(JLabel.LEADING);
						lblNetwork.setPreferredSize(new Dimension(100, 0));
						lblNetwork.setFont(fontPlainSanSer11);
					}
					{
						txtNetwork = new JTextField(50); 
						panNetwork.add(txtNetwork, BorderLayout.CENTER); 
						txtNetwork.setHorizontalAlignment(JTextField.CENTER);
						txtNetwork.setEditable(false);
						txtNetwork.setFont(fontPlainSanSer11);
						txtNetwork.setBackground(Color.CYAN);
					}
				}
			}
			{
				JXPanel panConnection = new JXPanel(new BorderLayout(5, 5)); 
				panParameters2.add(panConnection); 
				{
					{
						JLabel lblURL = new JLabel("Status"); 
						panConnection.add(lblURL, BorderLayout.LINE_START); 
						lblURL.setHorizontalAlignment(JLabel.CENTER);
						lblURL.setPreferredSize(new Dimension(75, 0));
						lblURL.setFont(fontPlainSanSer11);
					}
					{
						txtConnection = new JTextField(""); 
						panConnection.add(txtConnection, BorderLayout.CENTER); 
						txtConnection.setHorizontalAlignment(JTextField.CENTER);
						txtConnection.setFont(fontPlainSanSer11);
						txtConnection.setBackground(Color.CYAN);
					}
				}
			}
		}
	}
	
	private void GetMobileProperties() throws InterruptedException, IOException {
		String strDevice = sms_builder.ExecuteBash("adb devices"); 
		String strModel1 = "";
		String strModel2 = ""; 
		String strBattery = ""; 
		String strNetwork = "";
		 
		strDevice = strDevice.replace("List of devices attached", "");
		strDevice = strDevice.replace("device", ""); 
		strDevice = strDevice.trim(); 
		
		strModel1 = sms_builder.ExecuteBash("adb -s "+strDevice+" shell getprop ro.product.manufacturer").trim().toUpperCase();
		strModel2 = sms_builder.ExecuteBash("adb -s "+strDevice+" shell getprop ro.product.model").trim().toUpperCase();
		strBattery = sms_builder.ExecuteBash("adb shell dumpsys battery | grep \"level:\" | awk '{ print $2 }'");
		strNetwork = sms_builder.ExecuteBash("adb -s "+strDevice+" shell getprop gsm.STK_SETUP_MENU");
		
		txtModel.setText(strModel1+" "+strModel2);
		txtBattery.setText(strBattery);
		txtNetwork.setText(strNetwork);
		txtConnection.setText((!strDevice.equals(""))?"Device ID: "+strDevice+" [Connected]":"Not Connected"); 
	}
	
	@SuppressWarnings("unused")
	private Boolean SendMessageUSB(Integer intMsgID) throws org.postgresql.util.PSQLException {
		String strNumber = "";
		String strMessage = "";
		String strResponse = ""; 
		Boolean blnSent = true;
		
		String[] arr_mobile = FncGlobal.GetString("select cellphone from rf_sms_batch  where msg_id = '"+intMsgID+"'").split(",");  
		
		addLog("Sending Msg " + intMsgID + "...");
		
		for (int x = 0; x < toppings.length; x++) {
			strMessage = toppings[x];
			
			for (int y=0; y<arr_mobile.length; y++) {

				strNumber = arr_mobile[y];
				
				try {
					sms_builder.SendThruUSB(strNumber, strMessage); 
					strResponse="sent"; 
					blnSent = true; 
				} catch (InterruptedException | IOException e1) {
					strResponse = "failed";
					blnSent = false; 
				}
				
				sms_builder.createMessageLogUSB(intMsgID.toString(), "", toppings[x], arr_mobile[y], "SENT");
			}
		} 
		
		return blnSent; 
	}
	
	private boolean FetchNextMessage() {
		Boolean blnFetch = false; 
		Boolean blnInGrid = false;
		Boolean blnSent = false;
		String strMsgID = FncGlobal.GetString("select msg_id::varchar \n" +
				"from rf_sms_pool \n" +
				"where msg_status in ('P') \n" +
				"order by msg_id DESC --pool_row_id");
		
		for (int x=0; x<modelSMS.getRowCount(); x++) {
			if (modelSMS.getValueAt(x, 1).toString().equals(strMsgID)) {
				blnInGrid = true;
				System.out.println("Status = "+modelSMS.getValueAt(x, 5).toString());
				System.out.println("FetchNextMessage loop: "+sms_builder.msg_status);
				
			}
		}
	
		if (strMsgID!="" && blnInGrid) {
			blnFetch = true; 
			
			addLog("Msg "+strMsgID+" is now pending...");
			addLog("Retrieving message...");
			StackMessage(FncGlobal.GetString("select message from rf_sms_batch where msg_id::int = '"+strMsgID+"'::int"));
			
			try {
				blnSent = SendMessage(Integer.parseInt(strMsgID));
			} catch (Exception e1) {
				e1.printStackTrace();
			}  
			
			if (blnSent) {
				for (int x=0; x<modelSMS.getRowCount(); x++) {
					if (modelSMS.getValueAt(x, 1).toString().equals(strMsgID)) {
						modelSMS.setValueAt("SENT", x, 5);
						modelSMS.setValueAt(new Timestamp(System.currentTimeMillis()), x, 6);
						modelSMS.setValueAt(FncGlobal.getDateToday(), x, 6);
						
						pgUpdate dbExec1 = new pgUpdate(); 
						dbExec1.executeUpdate("update rf_sms_batch set sent = true, date_sent = now(), msg_status = 'S' where msg_id = '"+strMsgID+"'", false);
						dbExec1.commit();
						
						pgUpdate dbExec2 = new pgUpdate(); 
						dbExec2.executeUpdate("update rf_sms_pool set msg_status = 'S' where msg_id = '"+strMsgID+"'", false);
						dbExec2.commit();
					}
				}
			} else {

			}
		} else {
			blnFetch = false; 
			addLog("There is no pending message");
		}
		
		return blnFetch; 
	}
	
	private boolean FetchNextMessageUSB() {
		Boolean blnFetch = false; 
		Boolean blnInGrid = false;
		Boolean blnSent = false;
		String strMsgID = FncGlobal.GetString("select msg_id::varchar from rf_sms_pool where msg_status in ('P') order by pool_row_id");
		
		for (int x=0; x<modelSMS.getRowCount(); x++) {
			if (modelSMS.getValueAt(x, 1).toString().equals(strMsgID)) {
				blnInGrid = true;
			}
		}
	
		if (strMsgID!="" && blnInGrid) {
			blnFetch = true; 
			
			addLog("Msg "+strMsgID+" is now pending...");
			addLog("Retrieving message...");
			StackMessage(FncGlobal.GetString("select message from rf_sms_batch where msg_id::int = '"+strMsgID+"'::int"));
			
			try {
				
				if (txtConnection.getText().equals("Not Connected")) {
					blnSent = false; 
				} else {
					blnSent = SendMessageUSB(Integer.parseInt(strMsgID));
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}  
			
			if (blnSent) {
				for (int x=0; x<modelSMS.getRowCount(); x++) {
					if (modelSMS.getValueAt(x, 1).toString().equals(strMsgID)) {
						modelSMS.setValueAt("SENT", x, 5);
						modelSMS.setValueAt(new Timestamp(System.currentTimeMillis()), x, 6);
						modelSMS.setValueAt(FncGlobal.getDateToday(), x, 6);
						
						pgUpdate dbExec1 = new pgUpdate(); 
						dbExec1.executeUpdate("update rf_sms_batch set sent = true, date_sent = now(), msg_status = 'S' where msg_id = '"+strMsgID+"'", false);
						dbExec1.commit();
						
						pgUpdate dbExec2 = new pgUpdate(); 
						dbExec2.executeUpdate("update rf_sms_pool set msg_status = 'S' where msg_id = '"+strMsgID+"'", false);
						dbExec2.commit();
					}
				}
			} else {

			}
		} else {
			blnFetch = false; 
			addLog("There is no pending message");
		}
		
		return blnFetch; 
	}
	
	private void ExecuteUSB() {
		tmr = new TimerTask() {
			
			public void run() {

				if (!togSMS.isSelected()) {
					FetchNextMessageUSB();
				} else {
					addLog("Currently on sleep mode");
				}
				 
				timerExecuteActive = true;
			}
		};

		timerExecute = new Timer();
		long delay = 0;
		long intevalPeriod = 20 * 1000; 
	
		timerExecute.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}
	
	private void Execute() {
		tmr = new TimerTask() {

			public void run() {
				if (!togSMS.isSelected()) {
					
					FetchNextMessage();
				} else {
					addLog("Currently on sleep mode");
				}
				 
				timerExecuteActive = true;
			}
		};

		timerExecute = new Timer();
		long delay = 0;
		long intevalPeriod = 10 * 1000; 
	
		timerExecute.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}
	
	private void InitiateAndroid() {
		tmr = new TimerTask() {

			@SuppressWarnings("deprecation")
			public void run() {
				Date dteDate = new Date();
				
				String strSQL = "select distinct a.created_by \n" + 
					"from co_ntp_accomplishment_v2 a\n" + 
					"where date_part('hour', a.date_created)::int = date_part('hour', now())::int \n" + 
					"and date_part('day', a.date_created)::int = date_part('day', now())::int \n" +
					"and date_part('month', a.date_created)::int = date_part('month', now())::int \n" + 
					"and date_part('year', a.date_created)::int = date_part('year', now())::int \n" + 
					"and a.status_id = 'A'"; 
				
				pgSelect dbExec = new pgSelect(); 
				dbExec.select(strSQL);

				//System.out.println("Seconds: "+dteDate.getSeconds());
				
				if (dbExec!=null) {
					
					if (intMinute!=dteDate.getMinutes()) {
						txtAndroid.setText("");
						
						for (int x=0; x<dbExec.getRowCount(); x++) {
							intContractor = null;
							intUnits = null;
							
							System.out.println("Loop: "+x);
							
							txtAndroid.setText(txtAndroid.getText().concat(generateNotification(dbExec.Result[x][0].toString(), intHour.toString())));	
						}
					}
					
					if (intHour!=dteDate.getHours()) {
						//System.out.println("Hour changed!");
						
						processCMDMessage(txtAndroid.getText());
						
						intHour = dteDate.getHours(); 
						intMinute = dteDate.getMinutes();
					}
				}
			}
		};

		timerAndroid = new Timer();
		long delay = 0;
		long intevalPeriod = 1000; 
	
		timerAndroid.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}
	
	@SuppressWarnings("unused")
	private static String generateNotification(String strInspector, String strHour) {
		Date dteDate = new Date();
		String strMsg = "";
		String strMarker = ""; 
		
		String strSQL = "select (row_number() over(order by a.date_created::date, a.percent_accomplish, d.entity_name)::int / 13)+1 as row_num, \n" + 
				"concat(d.entity_name, ' successfully marked the following unit(s) [', a.date_created::date, ' @ ', TO_CHAR(a.date_created::time, 'hh:mmAM'), ']: ') as message1, \n" + 
				"concat(replace(replace(replace(replace(concat('PH ', b.phase, ' BLK ', b.block, ' LT ', b.lot, ' [', a.percent_accomplish, '%', ']')::text, '\"', ''), ',', ', '), '{', ''), '}', '')) as message2, \n" + 
				"unnest(e.mobile) as contacts \n" + 
				"from co_ntp_accomplishment_v2 a \n" + 
				"inner join mf_unit_info b on a.pbl_id = b.pbl_id \n" + 
				"inner join em_employee c on a.created_by = c.emp_code \n" + 
				"inner join rf_entity d on c.entity_id = d.entity_id \n" + 
				"left join rf_contacts e on c.entity_id = e.entity_id \n" +
				"where a.created_by = '"+strInspector+"' \n" + 
				"and a.date_created::date = now()::date \n" + 
				"and date_part('hour', a.date_created)::int = '"+strHour+"'::int \n" + 
				"and a.status_id = 'A' \n" + 
				"order by a.date_created::date, a.percent_accomplish, d.entity_name"; 
		
		System.out.println("");
		System.out.println("strSQL: "+strSQL);
		
		pgSelect dbExec = new pgSelect(); 
		dbExec.select(strSQL);
		
		if (dbExec!=null) {
			strMsg = dbExec.getResult()[0][1].toString(); 
			strMarker = dbExec.getResult()[0][0].toString(); 
			
			Integer intCounter = 0; 
			
			for (int x=0; x<dbExec.getRowCount(); x++) {
				if (intCounter==5) {
					strMsg = strMsg+"<@ante>\n\n"+dbExec.getResult()[x][1].toString();
					intCounter = 0; 
				}
				
				strMsg = strMsg+dbExec.getResult()[x][2].toString()+", ";  
				strMarker = dbExec.getResult()[x][0].toString();
				intCounter++;
			}
			
			return strMsg+"<@ante>\n\n"; 
		} else {
			return strMsg;
		}
		
	}
	
	private void processCMDMessage(String strMsg) {
		pgUpdate dbExec = new pgUpdate();
		
		String strExec = "";
		String strMobileNo = "";
		String strMessage = ""; 
		
		Integer intRowID = 0; 
		Integer intMsgID = 0; 
		Integer intQueueID = 0; 
		
		pgSelect dbList = new pgSelect();
		dbList.select("select unnest(c.mobile) as mobile, a.col \n" +
				"from (select unnest(string_to_array(replace('"+strMsg+"', E'<@ante>\n', '<@ante>'), E'<@ante>\n')) as col) a \n" +
				"inner join rf_entity b on b.entity_name = split_part(col, ' successfully marked the following unit(s) ', 1) \n" + 
				"left join rf_contacts c on b.entity_id = c.entity_id \n" +
				"where coalesce(a.col, '') != ''");

		if (dbList!=null) {
			for (int x=0; x<dbList.getRowCount(); x++) {
				System.out.println(dbList.getResult()[x][0].toString());
				System.out.println(dbList.getResult()[x][1].toString());
				
				intRowID = FncGlobal.GetInteger("select (coalesce(max(msg_row_id::int), 0) + 1) from rf_sms_batch");
				intMsgID = FncGlobal.GetInteger("select (coalesce(max(msg_id::int), 0) + 1) from rf_sms_batch");
				intQueueID = FncGlobal.GetInteger("select (coalesce(max(pool_row_id::int), 0) + 1)::int from rf_sms_pool");
				
				strMobileNo = dbList.getResult()[x][0].toString(); 
				strMessage = dbList.getResult()[x][1].toString();
				
				strExec = "insert into rf_sms_batch (msg_row_id, msg_id, batch, entity_id, proj_id, pbl_id, seq_no, \n" + 
						"cellphone, sent, message, created_by, date_created, sent_by, date_sent, \n" +
						"msg_status, due_date, type_id, list_type_index) \n" + 
						"values ("+intRowID+"::bigint, "+intMsgID+"::bigint, '', '', '', '', null::int, \n" +
						"'"+strMobileNo+"', false, '"+strMessage+"', '"+UserInfo.EmployeeCode+"', now(), \n" +
						"'"+UserInfo.EmployeeCode+"', now(), 'P', null, null, null)";
				
				System.out.println(""); 
				System.out.println(strExec);
				
				dbExec = new pgUpdate(); 
				dbExec.executeUpdate(strExec, false);
				dbExec.commit();
				
				strExec = "insert into rf_sms_pool values ('"+intQueueID+"'::bigint, '"+intMsgID.toString()+"'::bigint, 'P', now())";
				System.out.println(strExec);
				
				System.out.println(""); 
				System.out.println(strExec);
				
				dbExec = new pgUpdate(); 
				dbExec.executeUpdate(strExec, false);
				dbExec.commit();
			}
		}
	}

	private Boolean SendMessage(Integer intMsgID) throws org.postgresql.util.PSQLException {
		String strMsgID = ""; 
		Boolean blnSent = true; 
		String strResponse = "";
		String[] arr_mobile = FncGlobal.GetString("select cellphone from rf_sms_batch  where msg_id = '"+intMsgID+"'").split(",");  
		
		addLog("Sending Msg " + intMsgID + "...");
		
		for (int x = 0; x < toppings.length; x++) {
			sms_builder.strMessage = toppings[x];
			
			for (int y=0; y<arr_mobile.length; y++) {
				sms_builder.strNumber = arr_mobile[y]; 
				
				try {
					//strMsgID = sms_builder.SendMessageToNumber_withResponse();
					strMsgID = sms_builder.SendMessageToNumber_v2();//v2 is method sending
					//strResponse="sent"; // Comment by Erick 2023-03-16
					strResponse = strMsgID;
					blnSent = true; 
					System.out.println("Test : "+strMsgID);
					
				} catch (Exception e1) {
					strResponse = "failed";
					System.out.println("The response is "+strResponse);
					addLog("Sending Msg " + intMsgID + " is failed...");
					JOptionPane.showMessageDialog(getContentPane(), "Sending message failed!", "Error", JOptionPane.OK_OPTION);
					blnSent = false; 
					break;// Added by Erick 2023-03-16
				}
				
				Integer intRetry = 0; 
				while (!strResponse.equals("sent")) {
					
					try {
						strResponse = sms_builder.GetMessageStatusFromResponse(sms_builder.FetchSingleMessage(strMsgID).toString());  
					} catch (Exception e1) {
						strResponse = "failed"; 
					}
					
					if (!strResponse.equals("sent") && intRetry==10) {
						strResponse = "failed";
					}
					
					if (strResponse.equals("failed") || strResponse.equals("")) {
						pgUpdate dbExec1 = new pgUpdate(); 
						dbExec1.executeUpdate("update rf_sms_batch set sent = false, date_sent = null, msg_status = 'F' where msg_id = "+intMsgID+"", false);
						dbExec1.commit();
						
						pgUpdate dbExec2 = new pgUpdate(); 
						dbExec2.executeUpdate("update rf_sms_pool set msg_status = 'F' where msg_id = "+intMsgID+"", false);
						dbExec2.commit();
						
						blnSent = false; 
						break;
					}
					
					intRetry++;
				}
				
				sms_builder.createMessageLog(intMsgID.toString(), strMsgID, toppings[x], arr_mobile[y], strResponse);
			}
		} 
		
		return blnSent; 
	}
}