package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Database.pgSelect;
import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_WaterReadingFacility;

public class FacilityTagWaterReading extends _JInternalFrame implements _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -543641562163037707L;
	static Dimension SIZE = new Dimension(550, 600);

	private static model_WaterReadingFacility tbl_model_WaterReading; 
	private JList rowReadHeader;
	private JScrollPane scrollWaterReading;
	private _JTableMain tblWaterReading;

	private JLabel lblEntity_id;
	private JLabel lblProj_id;
	private JLabel lblPBL_id;
	private JLabel lblDesc;
	private JLabel lblWaterReading;
	private JLabel lblPrevReading;
	private JLabel lblCur_reading;
	private JLabel lblConsumption;
	private JLabel lblWaterCost;
	

	private JTextField txtEntity_name;
	private JTextField txtProj_name;
	private JTextField txtDesc;

	private _JLookup lookupEntity_id;
	private _JLookup lookupProj_id;
	private _JLookup lookupPBL_id;

	private JDateChooser dateWaterReading;
	
	private _JXFormattedTextField ftxtPrev_reading;
	private static _JXFormattedTextField ftxtCur_reading; 
	private _JXFormattedTextField ftxtConsumption;
	private static _JXFormattedTextField ftxtWtr_cost;

	private JButton btnSave;
//	private JButton btnViewAll;
	private JButton btnCancel;
	private JButton btnPrev; 

	Border lineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
	
	// variables
	private String ent_id;
	private Double water_read;
	private Double consumption; 
	private BigDecimal pr_value;
	private Object wtr_cost;
	private Object prev_rdng;
	private String proj_id;
	private String pbl_id;
	private Date read_date;
	
	public FacilityTagWaterReading() {
		super("Water Reading Tagging", false, true, true, true);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);


		JPanel pnlMain = new JPanel(new BorderLayout(5,5));
		this.add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		{
			{
				JPanel pnlPageStart = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlPageStart, BorderLayout.PAGE_START);
				pnlPageStart.setPreferredSize(new Dimension(0, 123));

				{
					JPanel pnlComp_1 = new JPanel(new BorderLayout(10, 5));
					pnlPageStart.add(pnlComp_1, BorderLayout.PAGE_START);
					pnlComp_1.setPreferredSize(new Dimension(0, 40));
					pnlComp_1.setBorder(new EmptyBorder(5, 5, 5, 5));

					{	
						lblEntity_id = new JLabel("Client:");
						pnlComp_1.add(lblEntity_id, BorderLayout.LINE_START); 
						lblEntity_id.setPreferredSize(new Dimension(65, 0));

						lookupEntity_id = new _JLookup(null, "Company", 0);
						pnlComp_1.add(lookupEntity_id, BorderLayout.CENTER);
						lookupEntity_id.setEnabled(true);
						lookupEntity_id.setReturnColumn(0);
						lookupEntity_id.setLookupSQL(getClientInfo());
						lookupEntity_id.addLookupListener(new LookupListener() {


							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();

								if(data!=null) {
									lookupEntity_id.setValue(data[0].toString());
									txtEntity_name.setText(data[1].toString());
								}
																	
								// set value of previous reading
								getValuePrevReading();
								
								// set default data to selected components 
								initialize_comp (); 
								
								// set table as not editable
								tblWaterReading.setEnabled(false);
								
								
							}
						
						});


						txtEntity_name = new JTextField();
						pnlComp_1.add(txtEntity_name, BorderLayout.LINE_END);	
						txtEntity_name.setPreferredSize(new Dimension(300, 0));
						txtEntity_name.setEditable(false);
						txtEntity_name.setHorizontalAlignment(JTextField.LEFT);
						txtEntity_name.setFont(new Font("Segoe UI", Font.PLAIN, 12));

					}

					JPanel pnlComp_2 = new JPanel(new BorderLayout(10, 5));
					pnlPageStart.add(pnlComp_2, BorderLayout.CENTER);
					pnlComp_2.setBorder(new EmptyBorder(0, 5, 0, 5));

					{
						lblProj_id = new JLabel("Project:");
						pnlComp_2.add(lblProj_id, BorderLayout.LINE_START);
						lblProj_id.setPreferredSize(new Dimension(65, 0));

						lookupProj_id = new _JLookup();
						pnlComp_2.add(lookupProj_id, BorderLayout.CENTER);  
						lookupProj_id.setReturnColumn(0);
						lookupProj_id.setLookupSQL(lookupMethods.getProject("02"));
						lookupProj_id.addLookupListener(new LookupListener() {


							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();

								if(data != null) {
									lookupProj_id.setValue(data[0].toString());
									txtProj_name.setText(data[1].toString());
								}
							}
						});
						
						txtProj_name = new JTextField();
						pnlComp_2.add(txtProj_name, BorderLayout.LINE_END);	
						txtProj_name.setPreferredSize(new Dimension(300, 0));
						txtProj_name.setHorizontalAlignment(JTextField.CENTER);
						txtProj_name.setEditable(false);
					}

					JPanel pnlComp_3 = new JPanel(new BorderLayout(5, 5));
					pnlPageStart.add(pnlComp_3, BorderLayout.PAGE_END);
					pnlComp_3.setPreferredSize(new Dimension(0, 40));
					pnlComp_3.setBorder(new EmptyBorder(5, 5, 5, 5));

					{
						JPanel pnlPBL = new JPanel(new BorderLayout(5, 5));
						pnlComp_3.add(pnlPBL, BorderLayout.LINE_START);
						pnlPBL.setPreferredSize(new Dimension(175, 0));

						lblPBL_id = new JLabel("PBL ID:");
						pnlPBL.add(lblPBL_id, BorderLayout.LINE_START);
						lblPBL_id.setPreferredSize(new Dimension(70, 0));

						lookupPBL_id = new _JLookup();
						pnlPBL.add(lookupPBL_id, BorderLayout.CENTER);
						lookupPBL_id.setReturnColumn(0);
						lookupPBL_id.setLookupSQL(getPBL_ID());
						lookupPBL_id.addLookupListener(new LookupListener() {
							
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();
								
								if (data!= null) {
									lookupPBL_id.setValue(data[0].toString());
									txtDesc.setText(data[1].toString());
								}
						
								
							}
						});

						JPanel pnlPBL_extra = new JPanel(); 
						pnlPBL.add(pnlPBL_extra, BorderLayout.LINE_END);
						pnlPBL_extra.setPreferredSize(new Dimension(20, 0));

						JPanel pnlDesc = new JPanel(new BorderLayout(5, 5));
						pnlComp_3.add(pnlDesc, BorderLayout.CENTER); 

						lblDesc = new JLabel("Description:");
						pnlDesc.add(lblDesc, BorderLayout.LINE_START); 
						lblDesc.setPreferredSize(new Dimension(80, 0));

						txtDesc = new JTextField();
						pnlDesc.add(txtDesc, BorderLayout.CENTER);
						txtDesc.setHorizontalAlignment(JTextField.CENTER);
						txtDesc.setEditable(false);
					}
				}
			}

			JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter, BorderLayout.CENTER);
//			pnlCenter.setBorder(lineBorder);

			{
				JPanel pnlComp_4 = new JPanel(new BorderLayout(5, 5));
				pnlCenter.add(pnlComp_4, BorderLayout.PAGE_START);
				pnlComp_4.setPreferredSize(new Dimension(0, 90));
				pnlComp_4.setBorder(new EmptyBorder(5, 5, 5, 5));
				{
					JPanel pnlDate = new JPanel(new BorderLayout(5, 5));
					pnlComp_4.add(pnlDate, BorderLayout.PAGE_START); 
					pnlDate.setPreferredSize(new Dimension(0, 30));
					
					lblWaterReading = new JLabel("Reading Date:");
					pnlDate.add(lblWaterReading, BorderLayout.LINE_START);
					lblWaterReading.setPreferredSize(new Dimension(100, 0));

					dateWaterReading = new JDateChooser();
					pnlDate.add(dateWaterReading, BorderLayout.CENTER);
					dateWaterReading.setDate(FncGlobal.getDateToday());
					dateWaterReading.setDateFormatString("MM/dd/yyyy");
					dateWaterReading.setEnabled(false);
				}
					JPanel pnlPrev = new JPanel(new BorderLayout(5,	5));
					pnlComp_4.add(pnlPrev, BorderLayout.CENTER); 
					pnlPrev.setBorder(new EmptyBorder(10, 0, 0, 5));
				{
					lblPrevReading = new JLabel("Previous Reading:");
					pnlPrev.add(lblPrevReading, BorderLayout.LINE_START);
					lblPrevReading.setPreferredSize(new Dimension(120, 0));

					ftxtPrev_reading = new _JXFormattedTextField("0");
					pnlPrev.add(ftxtPrev_reading, BorderLayout.CENTER);
					ftxtPrev_reading.setHorizontalAlignment(JTextField.CENTER);
					ftxtPrev_reading.setFont(new Font("Segoe UI", Font.BOLD, 12));
					ftxtPrev_reading.setEditable(false);
				}	
					JPanel pnlConsumption = new JPanel(new BorderLayout(5, 5));
					pnlPrev.add(pnlConsumption, BorderLayout.LINE_END);
					pnlConsumption.setPreferredSize(new Dimension(260, 0));
					{
						lblConsumption = new JLabel("       Consumption:");
						pnlConsumption.add(lblConsumption, BorderLayout.LINE_START);
						lblConsumption.setPreferredSize(new Dimension(120, 0));
						
						ftxtConsumption = new _JXFormattedTextField("0");
						pnlConsumption.add(ftxtConsumption, BorderLayout.CENTER);
						ftxtConsumption.setHorizontalAlignment(JTextField.CENTER);
						ftxtConsumption.setFont(new Font("Segoe UI", Font.BOLD, 12));
						ftxtConsumption.setEditable(false);
						
					}
			
			}
			
			{
				JPanel pnlCenter_Comp = new JPanel(new GridLayout(1, 3, 10, 5));
				pnlCenter.add(pnlCenter_Comp, BorderLayout.CENTER);
				pnlCenter_Comp.setBorder(new EmptyBorder(5, 5, 5, 5));

				{
					JPanel pnlComp_5 = new JPanel(new BorderLayout(5, 5));
					pnlCenter_Comp.add(pnlComp_5); 

					lblCur_reading = new JLabel("Current Reading:");
					pnlComp_5.add(lblCur_reading, BorderLayout.LINE_START);
					lblCur_reading.setPreferredSize(new Dimension(120, 0));

					ftxtCur_reading = new _JXFormattedTextField("0");
					pnlComp_5.add(ftxtCur_reading, BorderLayout.CENTER);
					ftxtCur_reading.setHorizontalAlignment(JTextField.CENTER);
					ftxtCur_reading.setEnabled(false);
//					ftxtCur_reading.setFormatterFactory(_JXFormattedTextField.INTEGER);
					ftxtCur_reading.setFont(new Font("Segoe UI", Font.BOLD, 12));
					ftxtCur_reading.addKeyListener(new KeyListener() {


						public void keyTyped(KeyEvent arg0) {


						}


						public void keyReleased(KeyEvent arg0) {
							
//							try {
//								System.out.println("Dumaan dito");
//								water_read = Integer.parseInt(ftxtCur_reading.getText()); 
//								
//								consumption = (water_read - pr_value.intValue());
//								ftxtConsumption.setValue(consumption);
//								ftxtWtr_cost.setValue(consumption * 8.00);
//								
//							}
//								catch (NumberFormatException e) 
//							
//								{ 
//									ftxtCur_reading.setValue("0");
//									water_read = Integer.parseInt(ftxtCur_reading.getText().trim()); 
//									ftxtWtr_cost.setValue(water_cost);	
//								}	
//
//								btnState(true, true, false, false);
//
						}


						public void keyPressed(KeyEvent arg0) {
							if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
							water_read = Double.parseDouble(ftxtCur_reading.getText());
							if(water_read >= pr_value.intValue()) {
								consumption = (water_read - pr_value.intValue());
								ftxtConsumption.setValue(consumption);
								ftxtWtr_cost.setValue(consumption * 8.00);
								btnState(true, true, false);
							}				
							else {
								JOptionPane.showMessageDialog(getContentPane(), "Invalid input!");
								ftxtWtr_cost.setValue(null);
								ftxtConsumption.setText(" ");
								btnState(false, true, false);
							}
					        }
							if (arg0.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
								ftxtWtr_cost.setValue(null);
								ftxtConsumption.setText(" ");
							}
						
						}
					});

				}
				{
					JPanel pnlComp_6 = new JPanel(new BorderLayout(5, 5));
					pnlCenter_Comp.add(pnlComp_6);

					lblWaterCost = new JLabel("  Water Cost:", JLabel.TRAILING);
					pnlComp_6.add(lblWaterCost, BorderLayout.LINE_START);
					lblWaterCost.setPreferredSize(new Dimension(100, 0));

					ftxtWtr_cost = new _JXFormattedTextField("0.00");
					pnlComp_6.add(ftxtWtr_cost, BorderLayout.CENTER);
					ftxtWtr_cost.setHorizontalAlignment(JTextField.CENTER);
					ftxtWtr_cost.setFormatterFactory(_JXFormattedTextField.DECIMAL);
					ftxtWtr_cost.setFont(new Font("Segoe UI", Font.BOLD, 12));
					ftxtWtr_cost.setEditable(false);


				}

			}
			{
				JPanel pnlTable = new JPanel(new BorderLayout(5, 5));
				pnlCenter.add(pnlTable, BorderLayout.PAGE_END);
				pnlTable.setPreferredSize(new Dimension(0, 240));
				pnlTable.setBorder(new EmptyBorder(5, 5, 5, 5));
				{
					scrollWaterReading = new JScrollPane(); 
					pnlTable.add(scrollWaterReading, BorderLayout.CENTER);

					{
						tbl_model_WaterReading = new model_WaterReadingFacility();
						tblWaterReading = new _JTableMain(tbl_model_WaterReading);
						rowReadHeader = tblWaterReading.getRowHeader();
						scrollWaterReading.setViewportView(tblWaterReading);
						tblWaterReading.setSortable(true);
						
						tblWaterReading.hideColumns("Entity_ID");
						tblWaterReading.getColumnModel().getColumn(0).setPreferredWidth(75);
						tblWaterReading.getColumnModel().getColumn(1).setPreferredWidth(165);
						tblWaterReading.getColumnModel().getColumn(2).setPreferredWidth(75);
						tblWaterReading.getColumnModel().getColumn(3).setPreferredWidth(70);
						tblWaterReading.getColumnModel().getColumn(4).setPreferredWidth(100);
						tblWaterReading.getColumnModel().getColumn(5).setPreferredWidth(120);
						tblWaterReading.getColumnModel().getColumn(6).setPreferredWidth(120);
						tblWaterReading.getColumnModel().getColumn(7).setPreferredWidth(120);
						tblWaterReading.getColumnModel().getColumn(8).setPreferredWidth(120);
						tblWaterReading.getColumnModel().getColumn(9).setPreferredWidth(120);
						tblWaterReading.getColumnModel().getColumn(10).setPreferredWidth(120);
						
						
						tblWaterReading.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblWaterReading.getSelectionModel().addListSelectionListener(new ListSelectionListener( ) {
							
							@Override
							public void valueChanged(ListSelectionEvent arg0) {
								if(tblWaterReading.getSelectedRows().length ==1) {
									
									tableClick();
									btnState(false, false, true);
								}
								
							}
						});
			

					}
					{
						rowReadHeader = tblWaterReading.getRowHeader();
						rowReadHeader.setModel(new DefaultListModel());
						scrollWaterReading.setRowHeaderView(rowReadHeader);
						scrollWaterReading.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}

			}

			JPanel pnlPageEnd = new JPanel(new GridLayout(1, 3, 5, 5));
			pnlMain.add(pnlPageEnd, BorderLayout.PAGE_END);
			pnlPageEnd.setPreferredSize(new Dimension(0, 30));

			{
				btnSave = new JButton("Save");
				pnlPageEnd.add(btnSave); 
				btnSave.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent arg0) {
						getData();
						JOptionPane.showMessageDialog(getContentPane(), "Successfully Save!");
						displayValue(tbl_model_WaterReading, rowReadHeader);
						ftxtCur_reading.setEditable(false);
						btnState(false, false, true);
						
						//preview billing report
						preview();
						
					}
				});

				btnCancel = new JButton("Cancel");
				pnlPageEnd.add(btnCancel);
				btnCancel.addActionListener(new ActionListener() {
					
				
					public void actionPerformed(ActionEvent arg0) {
						ftxtCur_reading.setText("0");
						ftxtWtr_cost.setText("0.00");
						
						
					}
				});

				btnPrev = new JButton("Preview");
				pnlPageEnd.add(btnPrev);
				btnPrev.setEnabled(false);
				btnPrev.addActionListener(new ActionListener() {
					
				
					public void actionPerformed(ActionEvent arg0) {
						preview();
						
					}
				});

			}		
		}
		
		// display value to table 
		displayValue(tbl_model_WaterReading, rowReadHeader);
		btnState(false, false, false);
		

	}
	
//	private void computeWaterCost() {
//
//		try {
//		System.out.println("Dumaan dito");
//		water_read = Double.parseDouble(ftxtCur_reading.getText().trim()); 
//
//		water_cost = (water_read * 8.00);
//		ftxtWtr_cost.setText(water_cost.toString());
//		
//		}
//		catch (NumberFormatException e) 
//		
//		{ 
//			water_read = 0.00 ; 
//		}	
//
//		btnState(true, true, false);
//
//	}
	
	
	private void preview(){	

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("ent_id", lookupEntity_id.getValue());
		mapParameters.put("ent_name", txtEntity_name.getText());
		mapParameters.put("proj_id", lookupProj_id.getValue());
		mapParameters.put("proj_name", txtProj_name.getText());
		mapParameters.put("pbl_id", lookupPBL_id.getValue());
		mapParameters.put("date_wr", dateWaterReading.getDate());	
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/cenqlogo.png"));

		FncReport.generateReport("/Reports/rptWaterReadingFacility.jasper", "Transmittal Form", mapParameters);
	}
	
	private void getValuePrevReading() {
		
		ent_id = lookupEntity_id.getValue();
		Object[] data = getPreviousReading(ent_id);
		
		if (data!= null) {
			pr_value = (BigDecimal) data[0];
			ftxtPrev_reading.setValue(pr_value.intValue());
			
			
		}	
	}

	
	private void tableClick() {
		if (tblWaterReading.getSelectedRows().length == 1) {
			int row = tblWaterReading.getSelectedRow(); 
			Integer rec_id = (Integer) tbl_model_WaterReading.getValueAt(row, 0); 
					
			Object[] data = getDetails(rec_id); 
			
			String r_ent_id = (String) data [1];
			String r_client = (String) data[2];
			String r_proj_id = (String) data[3];
			String r_proj_name= (String) data[4];
			String r_pbl_id = (String) data[5]; 
			String r_desc = (String) data[6];
			Date r_rdng_date = (Date) data[7];
			BigDecimal r_prev_rdng = (BigDecimal) data[8];
			BigDecimal r_meter_rdng = (BigDecimal) data[9];
			BigDecimal r_wtr_cost = (BigDecimal) data[10];	
			
			BigDecimal cur_consumption = (BigDecimal) (r_meter_rdng.subtract(r_prev_rdng));
			
			
			lookupEntity_id.setValue(r_ent_id);
			txtEntity_name.setText(r_client);
			lookupProj_id.setValue(r_proj_id);
			txtProj_name.setText(r_proj_name);
			lookupPBL_id.setValue(r_pbl_id);
			txtDesc.setText(r_desc);
			dateWaterReading.setDate(r_rdng_date);
			ftxtPrev_reading.setValue(r_prev_rdng);
			ftxtCur_reading.setValue(r_meter_rdng);
			ftxtWtr_cost.setValue(r_wtr_cost);
			ftxtConsumption.setValue(cur_consumption); 
			
			dateWaterReading.setEnabled(true);
			ftxtCur_reading.setEditable(false);
			ftxtCur_reading.setEnabled(true);
			ftxtPrev_reading.setEnabled(true);
			ftxtPrev_reading.setEditable(false);
			ftxtConsumption.setEnabled(true);
			ftxtConsumption.setEditable(false);
			ftxtWtr_cost.setEnabled(true);
			ftxtWtr_cost.setEditable(false);
			
		}
	}
	
	private static Object[] getDetails(Integer rec_id) {
		String SQL = "Select a.rec_id, b.entity_id, b.entity_name, a.proj_id, d.proj_name, a.pbl_id, c.description, a.reading_date, a.initial_meter_reading, a.meter_reading, a.reading_amt\n" + 
				"from\n" + 
				"rf_water_reading_v2 a \n" + 
				"left join rf_entity b on a.entity_id = b.entity_id\n" + 
				"inner join mf_unit_other_descriptions c on a.pbl_id = c.pbl_id and a.proj_id = c.proj_id \n" + 
				"left join mf_project d on a.proj_id = d.proj_id  where a.rec_id ="+rec_id+"";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	
	private static Object [] getPreviousReading (String ent_id) {
		pgSelect db = new pgSelect();
				
		String SQL = "select coalesce( max(meter_reading), 0) from rf_water_reading_v2 where entity_id ='"+ent_id+"'";
	
		db.select(SQL);
		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
		
	}
	
	private void displayValue(DefaultTableModel model_WaterReadingFacility, JList rowReadHeader) {
		FncTables.clearTable(tbl_model_WaterReading);
		DefaultListModel listmod = new DefaultListModel();
		rowReadHeader.setModel(listmod);
		String SQL = "Select c_rec_id, c_entity_id, c_client_name, c_proj_id, c_proj_alias, c_pbl_id, c_description, \n" + 
				"c_reading_date, c_prev_reading, c_pres_reading, c_consumption, C_water_cost "
				+ "from view_water_tank_reading('"+ent_id+"','"+proj_id+"','"+pbl_id+"',null) order by c_reading_date ASC";
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x = 0;x<db.getRowCount();x++){
				tbl_model_WaterReading.addRow(db.getResult()[x]);
				listmod.addElement(tbl_model_WaterReading.getRowCount());
			}

		}
		
	}
	
	private void getData() {
		pgUpdate db = new pgUpdate(); 
		ent_id = lookupEntity_id.getText();
		proj_id = lookupProj_id.getText();
		pbl_id = lookupPBL_id.getText();
		read_date = dateWaterReading.getDate();
//		Double mtr_rdng = ftxtCur_reading.getValue();
		wtr_cost = ftxtWtr_cost.getValue();
		prev_rdng = ftxtPrev_reading.getValue();
//		water_read = Double.parseDouble(ftxtCur_reading.getText());

		

		
		{
			String query = "INSERT INTO rf_water_reading_v2 (rec_id, entity_id, proj_id, pbl_id, water_reader, reading_date, water_meter_no, initial_meter_reading, meter_reading, reading_amt, status_id, created_by, date_created) "+
					"VALUES ((SELECT coalesce(max(rec_id), 0)+1 from rf_water_reading_v2),"+  
					"'"+ent_id+"','"+proj_id+"', '"+pbl_id+"', '1094320905','"+read_date+"', '0000033163' , '"+prev_rdng+"',"+water_read+","+wtr_cost+", 'A', '"+UserInfo.EmployeeCode+"', now())";
			db.executeUpdate(query, true);

		}
		db.commit();
		
	}
	
	private String getClientInfo() {
		return "SELECT TRIM (entity_id)::VARCHAR as \"ID\", TRIM (entity_name) as \"Client\", TRIM (entity_alias) as \"Alias\" from rf_entity where entity_id = '4270585379'";
	}
	
	private String getPBL_ID() {
		return "SELECT TRIM (pbl_id)::VARCHAR as \"ID\", TRIM (description) as \"Description\" FROM mf_unit_other_descriptions order by pbl_id" ;
						
	}
	private void initialize_comp() {

//		String ent_id = "4270585379";
//		String co_name = "HAPPY WELL MANAGEMENT & COLLECTION SERVICES, INC.";
		String proj_id = "015";
		String proj_name = "TERRAVERDE RESIDENCES";
		String pbl_id = "6324"; 
		String desc = "Block 8 Compact Open Space"; 
//		lookupEntity_id.setValue(ent_id);
//		txtEntity_name.setText(co_name);
		dateWaterReading.setEnabled(true);
		ftxtCur_reading.setEnabled(true);
		lookupProj_id.setValue(proj_id);
		txtProj_name.setText(proj_name);
		lookupPBL_id.setValue(pbl_id);
		txtDesc.setText(desc);
		
		btnState (true, false,false);

	}

	private void btnState(Boolean bSave, Boolean bCancel, Boolean bPrev) {
		btnSave.setEnabled(bSave);
		btnCancel.setEnabled(bCancel);
		btnPrev.setEnabled(bPrev);	
	}


}
