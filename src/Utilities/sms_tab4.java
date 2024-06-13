package Utilities;

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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTipOfTheDay;

import com.toedter.calendar.JDateChooser;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_sms_monitoring;

public class sms_tab4 extends JXPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = -3853626408845845774L;
	private jSMS sms_main; 
	
	public static JList rowSMS;
	private _JTableMain tblSMS;
	
	private JScrollPane scrSMS;
	private JScrollPane scrMessage;
	private JScrollPane scrLog;
	
	private model_sms_monitoring modelSMS;

	private JComboBox cboWhen;

	private _JLookup txtBatch; 
	private _JLookup txtClientID;

	private JTextField txtClient;

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat sdtf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	private JTextArea txtLog;
	private JTextArea txtMessage;

	private JCheckBox chkFrom;
	private JCheckBox chkBatch;
	private JCheckBox chkClient;
	private JCheckBox chkFailed;
	private JCheckBox chkPause;
	
	private _JDateChooser dteFrom;
	private _JDateChooser dteTo;
	
	private Font fontPlainSanSer11 = new Font("SansSerif", Font.PLAIN, 11);
	
	private Timer timerReload; 
	
	public sms_tab4(jSMS sms) {
		this.sms_main = sms; 
		initGUI();
	}

	public sms_tab4(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public sms_tab4(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public sms_tab4(LayoutManager layout, boolean isDoubleBuffered) {
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
				panPage.setPreferredSize(new Dimension(0, 120));
				{
					{
						JXPanel panFilters = new JXPanel(new GridLayout(3, 1, 5, 5)); 
						panPage.add(panFilters, BorderLayout.CENTER); 
						panFilters.setBorder(JTBorderFactory.createTitleBorder("Filters", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JXPanel panWhen = new JXPanel(new BorderLayout(5, 5)); 
								panFilters.add(panWhen);
								{
									{
										chkFrom = new JCheckBox("From"); 
										panWhen.add(chkFrom, BorderLayout.LINE_START);
										chkFrom.setHorizontalAlignment(JLabel.LEADING);
										chkFrom.setFont(fontPlainSanSer11);
										chkFrom.setSelected(true);
										chkFrom.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												if (!chkFrom.isSelected()) {
													JOptionPane.showMessageDialog(null, "This checkbox should not be deselected!", "Warning", JOptionPane.WARNING_MESSAGE);	
												}
												
												dteFrom.setEnabled(true);
												dteTo.setEnabled(true);
												
												/*
												dteFrom.setEnabled(chkFrom.isSelected());
												dteTo.setEnabled(chkFrom.isSelected());
												dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
												dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
												*/
											}
										});
										chkFrom.setPreferredSize(new Dimension(125, 0));
									}
									{
										String[] strItems = {
												"Within the day", 
												"Within the week",
												"Within the month",
												"Within the year",
												"Since the beginning of time"											
										}; 
										
										cboWhen = new JComboBox(strItems); 
										//panWhen.add(cboWhen, BorderLayout.CENTER); 
										cboWhen.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												LoadMessages(); 
											}
										});
										cboWhen.setFont(fontPlainSanSer11);
										cboWhen.setEnabled(false);
									}
									{
										JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5)); 
										panWhen.add(panDate, BorderLayout.CENTER);
										{
											{
												dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
												panDate.add(dteFrom);
												dteFrom.getCalendarButton().setVisible(true);
												dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
												dteFrom.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
													public void propertyChange(PropertyChangeEvent evt) {
														LoadMessages();
													}
												});
											}
											{
												dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
												panDate .add(dteTo);
												dteTo.getCalendarButton().setVisible(true);
												dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
												dteTo.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
													public void propertyChange(PropertyChangeEvent evt) {
														LoadMessages();
													}
												});
											}	
										}
									}
								}
							}
							{
								JXPanel panBatch = new JXPanel(new BorderLayout(5, 5)); 
								panFilters.add(panBatch);
								{
									{
										chkBatch = new JCheckBox("Batch"); 
										panBatch.add(chkBatch, BorderLayout.LINE_START);
										chkBatch.setHorizontalAlignment(JLabel.LEADING);
										chkBatch.setFont(fontPlainSanSer11);
										chkBatch.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												txtBatch.setEnabled(chkBatch.isSelected());
												txtBatch.setValue("");
											}
										});
										chkBatch.setPreferredSize(new Dimension(125, 0));
									}
									{
										txtBatch = new _JLookup(); 
										panBatch.add(txtBatch, BorderLayout.CENTER);
										txtBatch.setLookupSQL("select distinct x.batch, x.date_created::Date, login_id from rf_sms_batch x inner join rf_system_user y on x.created_by = y.emp_code order by x.batch desc");
										txtBatch.setReturnColumn(0);
										txtBatch.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												LoadMessages(); 
											}
										});
										txtBatch.setFont(fontPlainSanSer11);
										txtBatch.setEnabled(false);
									}
								}
							}
							{
								JXPanel panClient = new JXPanel(new BorderLayout(5, 5)); 
								panFilters.add(panClient);
								{
									{
										chkClient = new JCheckBox("Client"); 
										panClient.add(chkClient, BorderLayout.LINE_START);
										chkClient.setHorizontalAlignment(JLabel.LEADING);
										chkClient.setFont(fontPlainSanSer11);
										chkClient.addItemListener(new ItemListener() {
											public void itemStateChanged(ItemEvent e) {
												txtClientID.setEnabled(chkClient.isSelected());
												txtClientID.setValue("");
												txtClient.setText("");
											}
										});
										chkClient.setPreferredSize(new Dimension(125, 0));
									}
									{
										JXPanel panLabel = new JXPanel(new BorderLayout(5, 5)); 
										panClient.add(panLabel, BorderLayout.CENTER); 
										{
											{
												txtClientID = new _JLookup(); 
												panLabel.add(txtClientID, BorderLayout.LINE_START); 
												txtClientID.setReturnColumn(1);
												txtClientID.setLookupSQL("select distinct x.entity_id, y.entity_name as name \n" + 
														"from rf_sms_batch x \n" + 
														"inner join rf_entity y on x.entity_id = y.entity_id \n" + 
														"order by y.entity_name");
												txtClientID.setPreferredSize(new Dimension(100, 0));
												txtClientID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
														
														if (data!=null) {
															txtClientID.setValue(data[0].toString());
															txtClient.setText(data[1].toString());
															LoadMessages(); 
														}
													}
												});
												txtClientID.setFont(fontPlainSanSer11);
												txtClientID.setEnabled(false);
											}
											{
												txtClient = new JTextField(""); 
												panLabel.add(txtClient, BorderLayout.CENTER);
												txtClient.setHorizontalAlignment(JTextField.CENTER);
												txtClient.setEditable(false);
												txtClient.setFont(fontPlainSanSer11);
											}
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
				{
					scrSMS = new JScrollPane();
					panMain.add(scrSMS, BorderLayout.CENTER);
					{
						{
							modelSMS = new model_sms_monitoring();
							tblSMS = new _JTableMain(modelSMS);
							scrSMS.setViewportView(tblSMS);
							scrSMS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							
							tblSMS.getColumnModel().getColumn(0).setPreferredWidth(40);
							tblSMS.getColumnModel().getColumn(1).setPreferredWidth(120);
							tblSMS.getColumnModel().getColumn(2).setPreferredWidth(120);
							tblSMS.getColumnModel().getColumn(3).setPreferredWidth(229);
							tblSMS.getColumnModel().getColumn(4).setPreferredWidth(300);
							tblSMS.getColumnModel().getColumn(5).setPreferredWidth(80);
							tblSMS.getColumnModel().getColumn(6).setPreferredWidth(80);
							tblSMS.getColumnModel().getColumn(7).setPreferredWidth(80);
							
							tblSMS.setSortable(false);
							tblSMS.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(stf));
							tblSMS.getColumnModel().getColumn(6).setCellRenderer(new DateRenderer(sdf));
							
							tblSMS.hideColumns("#", "From");
							
							tblSMS.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									System.out.println("");
									System.out.println("Clicked OR!");
									
									ShowMessage(); 
								}

								public void mouseReleased(MouseEvent e) {
									System.out.println("");
									System.out.println("Clicked!");	
									
									ShowMessage(); 
								}
								
								private void ShowMessage() {
									txtMessage.setText(FncGlobal.GetString("select message from rf_sms_batch where msg_id = '"+modelSMS.getValueAt(tblSMS.getSelectedRow(), 0)+"'"));
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
				JXPanel panEnd = new JXPanel(new GridLayout(1, 2, 5, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 120));
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
							panEnd.add(scrMessage); 
							scrMessage.setBorder(JTBorderFactory.createTitleBorder("Message", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						}	
					}
					{
						JXPanel panFailedFilterAndLog = new JXPanel(new BorderLayout(5, 5)); 
						panEnd.add(panFailedFilterAndLog);
						{
							{
								JXPanel panPauseFailed = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panFailedFilterAndLog.add(panPauseFailed, BorderLayout.PAGE_START);
								{
									chkPause = new JCheckBox("Pause Refresh"); 
									panPauseFailed.add(chkPause); 
									chkPause.setHorizontalAlignment(JCheckBox.LEADING);
									chkPause.setSelected(false);
								}
								{
									chkFailed = new JCheckBox("Show failed"); 
									panPauseFailed.add(chkFailed); 
									chkFailed.setHorizontalAlignment(JCheckBox.LEADING);
									chkFailed.setSelected(false);
									chkFailed.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent e) {
											LoadMessages();
										}
									});
								}	
							}
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
								panFailedFilterAndLog.add(scrLog, BorderLayout.CENTER);
								scrLog.setBorder(JTBorderFactory.createTitleBorder("SMS Log", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							}	
						}
					}
				}
			}
		}
		chkFrom.setSelected(true);
		Reload(); 
	}
	
	public void actionPerformed(ActionEvent arg0) {


	}
	
	private void Reload() {
		TimerTask tmr = new TimerTask() {

			public void run() {
				if (!chkPause.isSelected()) {
					LoadMessages();	
				}
			}
		};

		timerReload = new Timer();
		long delay = 0;
		long intevalPeriod = 5 * 1000; 
	
		timerReload.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}
	
	private void LoadMessages() {
		pgSelect db = new pgSelect();

		FncTables.clearTable(modelSMS);
		DefaultListModel listModel = new DefaultListModel();
		rowSMS.setModel(listModel); 
		
		String strFilter = "where ";
		
		/*
		if (chkFrom.isSelected()) {
			if (cboWhen.getSelectedIndex()==0) {
				strFilter = strFilter + ((strFilter.length()>6) ? "and " : "") + "y.date_created::date = now()::date "; 
			} else if (cboWhen.getSelectedIndex()==1) {
				strFilter = strFilter + ((strFilter.length()>6) ? "and " : "") + "y.date_created::date between (current_date - cast(extract(dow from current_date) as int) + 1)::date and current_date ";
			} else if (cboWhen.getSelectedIndex()==2) {
				strFilter = strFilter + ((strFilter.length()>6) ? "and " : "") + "y.date_created::date \n" +
						"between (date_part('year', current_Date) || '-' || date_part('month', current_Date) || '-01')::date and last_day(current_date) ";
			} else if (cboWhen.getSelectedIndex()==3) {	
				strFilter = strFilter + ((strFilter.length()>6) ? "and " : "") + "y.date_created::date \n" +
						"between (date_part('year', current_Date) || '-01-01')::date and (date_part('year', current_Date) || '-12-31')::date ";
			} else if (cboWhen.getSelectedIndex()==4) {
				strFilter = strFilter + ((strFilter.length()>6) ? "and " : "") + "y.date_created::date <= current_date ";
			}
		}
		*/
		if (chkFrom.isSelected()) {
			strFilter = strFilter + ((strFilter.length()>6) ? "and " : "") + "y.date_created::date >= '"+dteFrom.getDate().toString()+"'::date and y.date_created::date <= '"+dteTo.getDate().toString()+"' "; 
		}
		
		if (chkBatch.isSelected()) {
			strFilter = strFilter + ((strFilter.length()>6) ? "and " : "") + "y.batch = '"+txtBatch.getValue()+"' ";
		}
		
		if (chkClient.isSelected()) {
			strFilter = strFilter + ((strFilter.length()>6) ? "and " : "") + "y.entity_id = '"+txtClientID.getValue()+"' ";
		}
		
		if (chkFailed.isSelected()) {
			strFilter = strFilter + ((strFilter.length()>6) ? "and " : "") + "x.msg_status = 'F' ";
		}
		
		String strSQL = "select x.msg_id, (case when x.msg_status = 'P' then 'PENDING' when x.msg_status = 'F' then 'FAILED' else 'SENT' end) as status, \n" + 
				"y.batch, z.entity_name as recipient, y.cellphone, y.date_sent::time, y.date_sent::date, \n" + 
				"(select login_id from rf_system_user a where a.emp_code = y.created_by) as sent_by, 'Resend' as resend \n" + 
				"from rf_sms_pool x \n" + 
				"inner join rf_sms_batch y on x.msg_id = y.msg_id \n" + 
				"left join rf_entity z on y.entity_id = z.entity_id \n" + 
				strFilter + " \n" +
				"order by x.pool_row_id desc, x.msg_id desc"; 
		
		db.select(strSQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelSMS.addRow(db.getResult()[x]);
				listModel.addElement(modelSMS.getRowCount());
			} 
		} else {

		}
	}
}
