package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXApplet;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import net.sf.jasperreports.web.servlets.JasperPrintAccessor;
import tablemodel.model_lrmdReports;
import tablemodel.model_water_service_disconnection;

public class WaterServiceConnection extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -8301589713382991337L;
	static String title = "Water Service Connection";
	Dimension frameSize = new Dimension(563, 605);
	private static Border line = BorderFactory.createLineBorder(Color.RED);
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	
	private static _JLookup txtCoID;
	private static _JLookup txtProID;
	private static _JLookup txtPhaseID;
	
	private static JTextField txtCo;
	private static JTextField txtPro;
	private static JTextField txtPhase;
	private static JTextField txtSearch;
	
	private static JComboBox cboMode; 
	
	private static JButton btnPreview;
	private static JButton btnSave; 
	
	private JScrollPane scrWater;
	private model_water_service_disconnection modelWater;	
	public static _JTableMain tblWater;
	public static JList rowWater;
	
	private static JXPanel panMode; 
	
	public WaterServiceConnection() {
		super(title, false, true, false, false);
		initGUI(); 
	}

	public WaterServiceConnection(String title) {
		super(title);
		initGUI();
	}

	public WaterServiceConnection(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(frameSize);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panPage, BorderLayout.PAGE_START);
				panPage.setPreferredSize(new Dimension(0, 180));
				{
					{
						JXPanel panDetails = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panDetails, BorderLayout.CENTER); 
						panDetails.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JXPanel panDiv = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panDetails.add(panDiv, BorderLayout.LINE_START); 
								panDiv.setPreferredSize(new Dimension(180, 0));
								{
									{
										JXPanel panLabel = new JXPanel(new GridLayout(3, 1, 5, 5)); 
										panDiv.add(panLabel); 
										{
											String[] strLabel = new String[] {
												"Company", 
												"Project", 
												"Phase"
											}; 
											
											JLabel[] lblLabel = new JLabel[strLabel.length];
											for (int x = 0; x < strLabel.length; x++) {
												System.out.println("Label: "+strLabel[x].toString());
												
												lblLabel[x] = new JLabel(strLabel[x].toString()); 
												panLabel.add(lblLabel[x]); 
												lblLabel[x].setHorizontalAlignment(JLabel.LEFT);
												lblLabel[x].setFont(font11);
											}
										}
									}
									{
										JXPanel panLookup = new JXPanel(new GridLayout(3, 1, 5, 5)); 
										panDiv.add(panLookup); 
										{
											{
												txtCoID = new _JLookup(""); 
												panLookup.add(txtCoID); 
												txtCoID.setReturnColumn(0);
												txtCoID.setHorizontalAlignment(_JLookup.CENTER);
												txtCoID.setLookupSQL(lookupMethods.getCompany());
												txtCoID.setFont(font11);
												txtCoID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
														
														if (data!=null) {
															txtCo.setText(data[1].toString());
															txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));
															LoadClients(modelWater, rowWater); 
														}
													}
												});
											}
											{
												txtProID = new _JLookup(""); 
												panLookup.add(txtProID); 
												txtProID.setReturnColumn(0);
												txtProID.setHorizontalAlignment(_JLookup.CENTER);
												txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));
												txtProID.setFont(font11);
												txtProID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
														
														if (data!=null) {
															txtPro.setText(data[1].toString());
															txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
															LoadClients(modelWater, rowWater); 
														}
													}
												});
											}
											{
												txtPhaseID = new _JLookup(""); 
												panLookup.add(txtPhaseID); 
												txtPhaseID.setReturnColumn(0);
												txtPhaseID.setHorizontalAlignment(_JLookup.CENTER);
												txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
												txtPhaseID.setFont(font11);
												txtPhaseID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
														
														if (data!=null) {
															txtPhase.setText(data[1].toString());
															LoadClients(modelWater, rowWater); 
														}
													}
												});
											}
										}
									}
								}
							}
							{
								JXPanel panBox = new JXPanel(new GridLayout(3, 1, 5, 5)); 
								panDetails.add(panBox, BorderLayout.CENTER); 
								panBox.setPreferredSize(new Dimension(120, 0));
								{
									{
										txtCo = new JTextField(""); 
										panBox.add(txtCo); 
										txtCo.setEditable(false);
										txtCo.setHorizontalAlignment(JTextField.CENTER);
										txtCo.setFont(font11);
									}
									{
										txtPro = new JTextField(""); 
										panBox.add(txtPro); 
										txtPro.setEditable(false);
										txtPro.setHorizontalAlignment(JTextField.CENTER);
										txtPro.setFont(font11);
									}
									{
										txtPhase = new JTextField(""); 
										panBox.add(txtPhase); 
										txtPhase.setEditable(false);
										txtPhase.setHorizontalAlignment(JTextField.CENTER);
										txtPhase.setFont(font11);
									}
								}
							}
						}
					}
					{
						JXPanel panOther = new JXPanel(new GridLayout(1, 2, 5, 5)); 
						panPage.add(panOther, BorderLayout.PAGE_END); 
						panOther.setPreferredSize(new Dimension(0, 60));
						{
							{
								JXPanel panMode = new JXPanel(new BorderLayout(5, 5)); 
								panOther.add(panMode); 
								panMode.setBorder(JTBorderFactory.createTitleBorder("Mode", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									cboMode = new JComboBox(new String[] {
										"Connected", 
										"Disconnected",
										"Reconnected", 
									}); 
									panMode.add(cboMode, BorderLayout.CENTER); 
									cboMode.setFont(font11);
									cboMode.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent e) {
											LoadClients(modelWater, rowWater); 
										}
									});
								}
							}
							{
								JXPanel panSearch = new JXPanel(new BorderLayout(5, 5)); 
								panOther.add(panSearch); 
								panSearch.setBorder(JTBorderFactory.createTitleBorder("Search", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									txtSearch = new JTextField(""); 
									panSearch.add(txtSearch, BorderLayout.CENTER); 
									txtSearch.setHorizontalAlignment(JTextField.LEFT);
									txtSearch.setFont(font11);
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
					scrWater = new JScrollPane();
					panCenter.add(scrWater, BorderLayout.CENTER);
					scrWater.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					{
						modelWater = new model_water_service_disconnection();
						modelWater.setEditable(false);
						
						tblWater = new _JTableMain(modelWater);
						tblWater.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent event) {
								if (!event.getValueIsAdjusting()) {
									System.out.println("");
									System.out.println("Selected row " + tblWater.getSelectedRow());
								}
							}
						});
						
						rowWater = tblWater.getRowHeader();
						scrWater.setViewportView(tblWater);
						
						tblWater.getColumnModel().getColumn(0).setPreferredWidth(40);
						tblWater.getColumnModel().getColumn(1).setPreferredWidth(253);
						tblWater.getColumnModel().getColumn(2).setPreferredWidth(125);
						tblWater.getColumnModel().getColumn(3).setPreferredWidth(75);
						
						tblWater.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");

						rowWater = tblWater.getRowHeader();
						rowWater.setModel(new DefaultListModel());
						scrWater.setRowHeaderView(rowWater);
						scrWater.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JXPanel panEnd = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnPreview = new JButton("Preview"); 
						panEnd.add(btnPreview, BorderLayout.LINE_START); 
						btnPreview.setPreferredSize(new Dimension(150, 0));
						btnPreview.setFont(font11);
						btnPreview.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								String strDate = "";
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
								strDate = df.format(FncGlobal.getDateToday());
								
								String strMode = (cboMode.getSelectedIndex()==0)?"C":((cboMode.getSelectedIndex()==1)?"D":"R");
								
								Map<String, Object> mapParameters = new HashMap<String, Object>(); 
								mapParameters.put("01_co_id", txtCoID.getValue()); 
								mapParameters.put("02_co_name", txtCo.getText()); 
								mapParameters.put("03_proj_id", txtProID.getValue()); 
								mapParameters.put("04_proj_name", txtPro.getText()); 
								mapParameters.put("05_dateParam", strDate); 
								mapParameters.put("06_mode", strMode);
								mapParameters.put("07_phase", txtPhaseID.getValue()); 
								FncReport.generateReport("/Reports/rpt_pmd_disconnected_water_service.jasper", "Water Service Connection", "", mapParameters);
							}
						});
					}
					{
						btnSave = new JButton(""); 
						panEnd.add(btnSave, BorderLayout.LINE_END); 
						btnSave.setPreferredSize(new Dimension(150, 0));
						btnSave.addActionListener(this);
						btnSave.setFont(font11);
					}	
				}
			}
		}
		
		SetDefault(); 
		RefreshList(); 
		LoadClients(modelWater, rowWater); 
	}
	
	private static void SetDefault() {
		txtCoID.setValue("02");
		txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");
		txtProID.setValue("015");
		txtPro.setText("TERRAVERDE RESIDENCES");
		txtPhaseID.setValue("");
		
		txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));
		txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
	}
	
	private void LoadClients(DefaultTableModel modelMain, JList rowHeader) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		String strMode = (cboMode.getSelectedIndex()==0)?"C":((cboMode.getSelectedIndex()==1)?"D":"R"); 
		
		if (strMode.equals("C")) {
			btnSave.setEnabled(true);
			btnSave.setText("Tag as Disconnected");
			btnSave.setActionCommand("Disconnect");
		} else if (strMode.equals("D")) {
			btnSave.setEnabled(true);
			btnSave.setText("Tag as Reconnected");
			btnSave.setActionCommand("Reconnect");
		} else {
			btnSave.setEnabled(false);
			btnSave.setActionCommand("");			
		}
		
		String SQL = "select * from view_pmd_water_service_connection('"+txtCoID.getValue()+"', '"+txtProID.getValue()+"', '"+txtPhaseID.getValue()+"', '"+strMode+"')"; 

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};
	}
	
	private void RefreshList() {
		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate("insert into rf_water_service_connection\n" + 
				"select a.entity_id, a.projcode, a.pbl_id, a.seq_no, \n" + 
				"'C'::varchar(1) as connection_status, \n" + 
				"null::timestamp as curr_disconnection_date, ''::varchar(6) disconnected_by, \n" + 
				"null::timestamp as curr_reconnection_date, ''::varchar(6) reconnected_by, \n" + 
				"now() as date_created, '"+UserInfo.EmployeeCode+"'::varchar(6) created_by, \n" + 
				"null::timestamp as date_edited, ''::varchar(6) edited_by, 'A'::varchar(1) as status_id\n" + 
				"from (select * from rf_sold_unit x where exists(select * from rf_water_reading_v2 y where y.entity_id = x.entity_id and y.proj_id = x.projcode and y.pbl_id = x.pbl_id and y.seq_no::int = x.seq_no::int and y.status_id = 'A')) a\n" + 
				"inner join mf_unit_info b on a.projcode = b.proj_id and a.pbl_id = b.pbl_id\n" + 
				"inner join rf_entity c on a.entity_id = c.entity_id\n" + 
				"inner join mf_product_model d on a.model_id = d.model_id\n" + 
				"where not exists(select * from rf_water_service_connection x where x.entity_id = a.entity_id and x.proj_id = a.projcode and x.pbl_id = a.pbl_id and x.seq_no::int = a.seq_no::int and x.status_id = 'A')", true);
		dbExec.commit();
	}
	
	public void actionPerformed(ActionEvent e) { 
		if (JOptionPane.showConfirmDialog(null, "Tag selected clients as "+e.getActionCommand()+"ed?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
			if (e.getActionCommand().equals("Disconnect")) {
				executeDisconnection(); 
			} else if (e.getActionCommand().equals("Reconnect")) {
				executeReconnection(); 
			}	
		}
	}
	
	private void executeDisconnection() {
		pgUpdate dbExec = new pgUpdate(); 
		
		for (int x=0; x<modelWater.getRowCount();x++) {
			if ((Boolean) modelWater.getValueAt(x, 0)) {
				dbExec = new pgUpdate();
				dbExec.executeUpdate("update rf_water_service_connection \n" + 
						"set connection_status = 'D', curr_disconnection_date = now(), disconnected_by = '"+UserInfo.EmployeeCode+"' \n" + 
						"where entity_id = '"+modelWater.getValueAt(x, 4).toString()+"' and proj_id = '"+modelWater.getValueAt(x, 5).toString()+"' \n" +
						"and pbl_id = '"+modelWater.getValueAt(x, 6).toString()+"' and seq_no = '"+modelWater.getValueAt(x, 7).toString()+"'::int and status_id = 'A'", false);
				dbExec.commit();
			}
		}
		
		cboMode.setSelectedIndex(1);
		RefreshList(); 
		JOptionPane.showMessageDialog(this, "Clients are tagged as disconnected!");
	}
	
	private void executeReconnection() {
		pgUpdate dbExec = new pgUpdate(); 
		
		for (int x=0; x<modelWater.getRowCount();x++) {
			if ((Boolean) modelWater.getValueAt(x, 0)) {
				dbExec = new pgUpdate();
				dbExec.executeUpdate("update rf_water_service_connection \n" + 
						"set connection_status = 'R', curr_disconnection_date = now(), disconnected_by = '"+UserInfo.EmployeeCode+"' \n" + 
						"where entity_id = '"+modelWater.getValueAt(x, 4).toString()+"' and proj_id = '"+modelWater.getValueAt(x, 5).toString()+"' \n" +
						"and pbl_id = '"+modelWater.getValueAt(x, 6).toString()+"' and seq_no = '"+modelWater.getValueAt(x, 7).toString()+"'::int and status_id = 'A'", false);
				dbExec.commit();
			}
		}

		cboMode.setSelectedIndex(2);
		RefreshList();
		JOptionPane.showMessageDialog(this, "Clients are tagged as reconnected!");
	}
}

