package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_unit_turnover;

public class HOADuesStartDate extends _JInternalFrame
		implements _GUI {
	
	private static final long serialVersionUID = 1696231374073396297L;
	
	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;
	
	JLabel lblNorth;
	JLabel lblCenter;
	
	JXTextField txtPhase;
	JXTextField txtBlock;
	JXTextField txtCompany;
	JXTextField txtProject;
	
	_JLookup lookupProject;
	_JLookup lookupPhase;
	_JLookup lookupCompany;
	_JLookup lookupBlock;
	
	JButton btnPreview;
	JButton btnSave;
	JButton btnCancel;
	
	model_unit_turnover modelUnitTO;
	JScrollPane scrollUnitTO;
	_JTableMain tblUnitTO;
	JList rowheaderUnitTO;
	_JDateChooser dateSched;
	JSpinner time;
	
	String co_id = "";
	String proj_id = "";
	String venue_id = "";
	String venue = "";
	
	static String title = "HOA Dues Start Date";
	Dimension frameSize = new Dimension(550, 400);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public HOADuesStartDate() {
		super(title, false, true, false, true);
		initGUI();
	}

	public HOADuesStartDate(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public HOADuesStartDate(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(null);
				pnlNorth.setBorder(lineBorder);
				pnlNorth.setPreferredSize(new Dimension(750, 130));// XXX
				{
					lblNorth = new JLabel("Company");
					pnlNorth.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 10, 120, 22);
				}
				{
					lookupCompany = new _JLookup(null, "Company");
					pnlNorth.add(lookupCompany);
					lookupCompany.setReturnColumn(0);
					lookupCompany.setLookupSQL(SQL_COMPANY());
					lookupCompany.setBounds(90, 10, 120, 22);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								txtCompany.setText(String.format("%s", (String) data[1]));
								lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));

								//lookupProject.setValue(null);
								//txtProject.setText("");
								//listPhase.setModel(new DefaultComboBoxModel(new ArrayList<_JCheckListItem>().toArray()));

								//generateContractNo();
								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					txtCompany = new JXTextField("");
					pnlNorth.add(txtCompany);
					txtCompany.setHorizontalAlignment(JLabel.LEFT);
					txtCompany.setBounds(215, 10, 280, 22);
					txtCompany.setEditable(false);
				}
					{
						lblNorth = new JLabel("Project");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 35, 120, 22);
					}
					{
						lookupProject = new _JLookup(null, "Project", "Please select company.");
						pnlNorth.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setBounds(90, 35, 120, 22);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									//String co_id = lookupCompany.getValue();

									//String project_id = (String) data[0];
									//String project_name = (String) data[1];
									//String project_alias = (String) data[2];

									txtProject.setText(String.format("%s", (String) data[1]));
									lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));
									//displayUnit(lookupCompany.getValue(), (String) data[0] );
									
									btnPreview.setEnabled(true);

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						txtProject = new JXTextField("");
						pnlNorth.add(txtProject);
						txtProject.setHorizontalAlignment(JLabel.LEFT);
						txtProject.setBounds(215, 35, 280, 22);
						txtProject.setEditable(false);
					}
					{
						lblNorth = new JLabel("Phase");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 60, 120, 22);
					}
					{
						lookupPhase = new _JLookup(null, "Phase", "Please select project.");
						pnlNorth.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setBounds(90, 60, 120, 22);
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									txtPhase.setText(String.format("Phase %s", (String) data[0]));
									lookupBlock.setLookupSQL(SQL_BLOCK((String) data[0]));
									//displayUnit(lookupProject.getValue(), (String) data[0] );

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						txtPhase = new JXTextField("");
						pnlNorth.add(txtPhase);
						txtPhase.setHorizontalAlignment(JLabel.LEFT);
						txtPhase.setBounds(215, 60, 280, 22);
						txtPhase.setEditable(false);
					}
					{
						lblNorth = new JLabel("Block");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 85, 120, 22);
					}
					{
						lookupBlock = new _JLookup(null, "Block", "Please select phase.");
						pnlNorth.add(lookupBlock);
						lookupBlock.setReturnColumn(0);
						lookupBlock.setBounds(90, 85, 120, 22);
						lookupBlock.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									txtBlock.setText(String.format("Block %s", (String) data[0]));
									//displayUnit(lookupPhase.getValue(), (String) data[0] );
									
								}
							}
						});
					}
					{
						txtBlock = new JXTextField("");
						pnlNorth.add(txtBlock);
						txtBlock.setHorizontalAlignment(JLabel.LEFT);
						txtBlock.setBounds(215, 85, 280, 22);
						txtBlock.setEditable(false);
					}
					{
						pnlCenter = new JPanel();
						pnlMain.add(pnlCenter, BorderLayout.CENTER);
						pnlCenter.setLayout(new BorderLayout(5, 5));
						pnlCenter.setBorder(lineBorder);
						{
							scrollUnitTO = new JScrollPane();
							pnlCenter.add(scrollUnitTO, BorderLayout.CENTER);
							scrollUnitTO.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollUnitTO.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							
							modelUnitTO = new model_unit_turnover();
							modelUnitTO.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowheaderUnitTO.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowheaderUnitTO.getModel()).addElement(modelUnitTO.getRowCount());
									}
								}
							});
							

							tblUnitTO = new _JTableMain(modelUnitTO);
							scrollUnitTO.setViewportView(tblUnitTO);
							tblUnitTO.hideColumns("Addr_no");
							tblUnitTO.hideColumns("Street");
							tblUnitTO.hideColumns("City");
							tblUnitTO.hideColumns("Province");
							tblUnitTO.hideColumns("Entity ID");
							tblUnitTO.hideColumns("Company Alias");
							tblUnitTO.hideColumns("Batch no");
							tblUnitTO.hideColumns("Notice Alias");
							tblUnitTO.hideColumns("Municipality");
							tblUnitTO.hideColumns("Zip Code");
							tblUnitTO.hideColumns("PBL ID");
							tblUnitTO.hideColumns("Seq No");
							tblUnitTO.hideColumns("Color Scheme");
							tblUnitTO.hideColumns("With NTC");
							tblUnitTO.hideColumns("House Const");
							tblUnitTO.hideColumns("Move-in Date");
							tblUnitTO.hideColumns("Turn-over Date");
							tblUnitTO.hideColumns("Final Orientation");
							tblUnitTO.hideColumns("NTC Batch No");
							
							rowheaderUnitTO = tblUnitTO.getRowHeader();
							rowheaderUnitTO.setModel(new DefaultListModel());
							scrollUnitTO.setRowHeaderView(rowheaderUnitTO);
							scrollUnitTO.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							
						}
					}
					{
						pnlSouth = new JPanel();
						pnlMain.add(pnlSouth, BorderLayout.SOUTH);
						pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
						//pnlSouth.setBorder(lineBorder);
						pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX
						{
							pnlSouth.add(Box.createHorizontalBox());
							pnlSouth.add(Box.createHorizontalBox());
							pnlSouth.add(Box.createHorizontalBox());
						}
						{
							btnPreview = new JButton("Preview");
							pnlSouth.add(btnPreview);
							btnPreview.setActionCommand("Preview");
							btnPreview.setMnemonic(KeyEvent.VK_P);
							btnPreview.addActionListener(this);
						}
						{
							btnSave = new JButton("Save");
							pnlSouth.add(btnSave);
							btnSave.setActionCommand("Save");
							btnSave.setMnemonic(KeyEvent.VK_P);
							btnSave.addActionListener(this);
						}
						{
							btnCancel = new JButton("Cancel");
							pnlSouth.add(btnCancel);
							btnCancel.setActionCommand("Cancel");
							btnCancel.setMnemonic(KeyEvent.VK_C);
							btnCancel.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									int response = JOptionPane.showConfirmDialog(HOADuesStartDate.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
											"Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
									if (response == JOptionPane.YES_OPTION) {
										lookupProject.setText("...");
										refreshTO();
										
										btnPreview.setEnabled(false);
									}
								}
							});
						}
					}
			}
		}
		btnPreview.setEnabled(false);
	}
	
	public void displayUnit(String phase, String block) {

		FncTables.clearTable(modelUnitTO);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		//rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
				"select distinct on (client_name, a.pbl_id) false, trim(a.description), c.model_desc, get_client_name(b.entity_id) as client_name, \n" +
				"d.addr_no, d.street, (case when e.city_name is null then h.municipality_name else e.city_name end) as city_name, (case when f.province_name is null then 'Metro Manila' else f.province_name end) as province_name, b.entity_id, 'CENQ' as company_alias," +
				"null, i.notice_alias, h.municipality_name, d.zip_code, b.pbl_id, b.seq_no, j.hse_color as color_scheme,\n" +
				"(case when a.ntc is not null then true else false end) as with_ntc, k.ntc_batchno, null, \n" +
				"null, null, null, a.ntc, a.house_constructed \n" +

				"from mf_unit_info a \n" +
				
				"left join rf_sold_unit b on a.pbl_id = b.pbl_id and a.proj_id = b.projcode \n" +
				"left join mf_product_model c on a.model_id = c.model_id \n" +
				"left join rf_entity_address d on  b.entity_id = d.entity_id and d.pref_billing and d.status_id ='A'\n" +
				"left join mf_city e on d.city_id = e.city_id \n" +
				"left join mf_province f on d.province_id = f.province_id \n" +
				"left join rf_client_notices g on b.entity_id = g.entity_id and g.pbl_id = a.pbl_id and g.projcode = a.proj_id \n" +
				"left join mf_municipality h on d.municipality_id = h.municipality_id \n" +
				"left join mf_notice_type i on g.notice_id = i.notice_id \n" +
				"left join (select * from rf_marketing_scheme_pricelist where status_id = 'A') j on a.proj_id = j.proj_id and a.phase = j.phase and a.block = j.block and a.lot = j.lot \n" +
				"left join rf_buyer_status k on b.entity_id = k.entity_id and b.projcode = k.proj_id and b.pbl_id = k.pbl_id and b.seq_no = k.seq_no \n" +
				"left join (select * from co_ntp_detail where work_percent = 100 and status_id = 'A' and ntp_no in (select ntp_no from co_ntp_header where co_id = '02' and status_id = 'A')) l on a.pbl_id = l.pbl_id \n" +

				"where (CASE WHEN ('"+lookupBlock.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupBlock.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE a.block ~* '"+lookupBlock.getValue()+"' END) \n" +
				"and (CASE WHEN ('"+lookupPhase.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupPhase.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE a.phase ~* '"+lookupPhase.getValue()+"' END) \n" +
				"and (CASE WHEN ('"+lookupProject.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupProject.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE a.proj_id ~* '"+lookupProject.getValue()+"' END) \n" +
				//"and (CASE WHEN ('"+lookupBatch.getValue()+"' = 'All' OR NULLIF(UPPER('"+lookupBatch.getValue()+"'), 'NULL') IS NULL) THEN TRUE ELSE g.batch_no ~* '"+lookupBatch.getValue()+"' END) \n" +
				"and get_client_name(b.entity_id) not in ('')" +
				//"and a.unit_id not in ('')" +
				//"and g.batch_no not in ('')" +
				//"and d.street not in ('')" +
				"and b.status_id='A'\n" +
				"and a.ntc is not null\n" +
				"and a.pbl_id not in (select pbl_id from rf_qualified4orientation where status_id = 'A')\n" +
				//"group by a.house_constructed, k.ntc_batchno, b.pbl_id, a.description, model_desc, client_name, addr_no, street, city_name, province_name, b.entity_id, company_alias, g.batch_no, municipality_name, notice_alias, a.pbl_id, b.seq_no, j.hse_color, a.ntc, d.zip_code\n" +
				"order by client_name, a.pbl_id \n";
		

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelUnitTO.addRow(db.getResult()[x]);
				listModel.addElement(modelUnitTO.getRowCount());				
			}	
		}		

		tblUnitTO.packAll();		
	}
	private void refreshTO() {
		
		if (lookupProject.getText().equals("...")) {
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupPhase.setValue("");
			lookupBlock.setValue("");
			txtCompany.setText("");
			txtProject.setText("");
			txtPhase.setText("");
			txtBlock.setText("");
			//dateSched.setText("");
			//time.setValue("");
			modelUnitTO.setRowCount(0);
			
		}
	} // refreshTO()
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Preview")){
			//preview();
		}
		if(actionCommand.equals("Save")){
			//save();
		}
	}
	private void preview(){	
		ArrayList<String> iftrue = new ArrayList<String>();
		String SQL ="";
		
		ArrayList<String> listBatches = new ArrayList<String>();
		ArrayList<String> listentity = new ArrayList<String>();
		for (int i = 0; i < modelUnitTO.getRowCount(); i++) {
			Boolean SelectedItem = (Boolean) modelUnitTO.getValueAt(i, 0);
			String entity = (String) modelUnitTO.getValueAt(i, 8);
			
			if (SelectedItem) {
				listentity.add(entity);
				iftrue.add(modelUnitTO.getValueAt(i, 1).toString());
				SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +
						
						"SELECT \n" +
						"get_client_name('"+modelUnitTO.getValueAt(i, 8)+"', false) AS client_name,\n" + 
						"NULLIF('"+modelUnitTO.getValueAt(i, 4)+"','null') AS addr_no, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 5)+"','null') AS street, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 6)+"','null') AS city_name, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 7)+"','null') AS province_name, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 12)+"','null') AS municipality_name, \n" +
						"NULLIF('"+modelUnitTO.getValueAt(i, 13)+"','null') AS zip_code, \n" +
						"'"+modelUnitTO.getValueAt(i, 8)+"' AS entity_id, \n" +
						"'"+modelUnitTO.getValueAt(i, 9)+"' AS co_alias, \n" +
						"(select proj_alias from mf_project where proj_id = '"+lookupProject.getValue()+"') AS ref3, \n" +
						"'"+modelUnitTO.getValueAt(i, 11)+"' AS title, \n" +
						//"'' AS title, \n" +
						"get_client_address_billing('"+modelUnitTO.getValueAt(i, 8)+"') AS address, \n" +
						"get_client_name('"+modelUnitTO.getValueAt(i, 8)+"') AS addressee, \n" +
						"NULLIF(_get_client_mobileno('"+modelUnitTO.getValueAt(i, 8)+"'),'null') AS mobile_no, \n" +
						"NULLIF(_get_client_email('"+modelUnitTO.getValueAt(i, 8)+"'),'null') AS email_add, \n" +
						"'"+modelUnitTO.getValueAt(i, 1)+"' AS unit \n";
			}
		}

		if (iftrue.isEmpty()) {
			JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
			return;
		}
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("proj_name", txtProject.getText());
		mapParameters.put("venue", venue);
		mapParameters.put("date", dateSched.getDate());
		mapParameters.put("time", time.getValue());
		mapParameters.put("emp_alias", UserInfo.Alias);
		mapParameters.put("emp_fname", UserInfo.FirstName);
		mapParameters.put("emp_lname", UserInfo.LastName);
		mapParameters.put("emp_name", UserInfo.FullName);
		mapParameters.put("entity_id", listentity);
		mapParameters.put("batch_no", listBatches.toString().replace("[", "").replace("]", ""));
		
		FncReport.generateReport("/Reports/rptNotcTO.jasper", "Notice for TurnOver Orientation", mapParameters, SQL);
		FncReport.generateReport("/Reports/rptTransmittalForNTConstruct.jasper", "Transmittal For TurnOver Orientation - Metro Manila", mapParameters, SQL);
		FncReport.generateReport("/Reports/rptTransmittalForNTConstructP.jasper", "Transmittal For TurnOver Orientation - Provincial", mapParameters, SQL);
		//FncReport.generateReport("/Reports/rptMBTCTO.jasper", "MBTC Enrollee List", mapParameters);
	}
	private void save(){	

		for(int x =0; x < modelUnitTO.getRowCount(); x++){
			String entity = (String) modelUnitTO.getValueAt(x, 8);
			Boolean isSelected = (Boolean) modelUnitTO.getValueAt(x, 0);
			String projcode = (String) lookupProject.getValue(); 
			String pbl_id = (String) modelUnitTO.getValueAt(x, 14); //
			Integer seq_no = (Integer) modelUnitTO.getValueAt(x, 15); //
 			//Date date_qualified = null; 
 			//Date attended = null;
			Date date_attended = (Date) dateSched.getDate();
			Date time_attended = (Date) time.getValue();
			//String tagfordel = null; 
			String assigned_by = (String) UserInfo.EmployeeCode;
			String color_scheme_id = (String) modelUnitTO.getValueAt(x, 16); //
			Boolean with_ntc = (Boolean) modelUnitTO.getValueAt(x, 17); //
			String ntc_batchno = (String) modelUnitTO.getValueAt(x, 18); //
			//String remarks = null; //
			Date ntc_date = (Date) modelUnitTO.getValueAt(x, 23); //
			//Date hcons_date = (Date) modelUnitTO.getValueAt(x, 24); //
			//Date movein_date = null; //
			//String tagfortover = null; //
			//Date tover_date = null; //
			//Date final_orientation = null; //
			Date date_orient_assigned = (Date) dateSched.getDate();
			String created_by = UserInfo.EmployeeCode;
			
			if(isSelected){
				
				String strSQL = "SELECT sp_save_to_orientation('"+entity+"', '"+projcode+"', '"+pbl_id+"', "+seq_no+",,"
								+ "'"+date_attended+"'::timestamp, '"+time_attended+"'::timestamp, '"+venue+"',"
								+ "'"+assigned_by+"', '"+color_scheme_id+"', '"+with_ntc+"', '"+ntc_batchno+"',"
								+ "'"+ntc_date+"'::timestamp,"
								+ "'"+date_orient_assigned+"'::timestamp, '"+created_by+"');"; 
				
				pgSelect db = new pgSelect();
				db.select(strSQL);
			}
			
		}
		JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
		
			modelUnitTO.setRowCount(0);
			//displayUnit(lookupCompany.getValue(), lookupProject.getValue());
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupPhase.setValue("");
			lookupBlock.setValue("");
			txtCompany.setText("");
			txtProject.setText("");
			txtPhase.setText("");
			txtBlock.setText("");
	}
}
