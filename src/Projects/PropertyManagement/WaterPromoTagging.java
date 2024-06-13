package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelStatusListing;
import tablemodel.modelWaterPromoTagging;

public class WaterPromoTagging extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 6751674849092107368L;
	static String title = "Water Promo Tagging";
	Dimension frameSize = new Dimension(500, 350);

	private JPanel pnlNorth;
	private JPanel pnlCenter;
	private JPanel pnlSouth;

	private JPanel pnlTagPromo;

	private _JLookup lookupClient;
	private _JXTextField txtClient;
	private _JXTextField txtProjID;
	private _JXTextField txtProjName;
	private _JXTextField txtPBL;
	private _JXTextField txtUnitDesc;
	private _JXTextField txtSeqNo;
	private _JXFormattedTextField txtDueAmt;

	private JComboBox cmbPmtType;
	private _JDateChooser datePromoStart;

	private modelWaterPromoTagging modelWaterPromo;
	private JScrollPane scrollWaterPromo;
	private _JTableMain tblScrollWaterPromo;
	private JList rowHeaderWaterPromo;

	private JButton btnTagNew;
	private JButton btnTagSave;
	private JButton btnTagPreview;
	private JButton btnTagCancel;

	private JTabbedPane tabCenter;

	private JPanel pnlReport;

	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblStatus;
	private JCheckBox chkActualDate;
	private JLabel lblStatusDate;

	private _JLookup lookupCompany;
	private _JLookup lookupProject;
	private _JLookup lookupPhase;
	private _JLookup lookupStatus;

	private _JXTextField txtCompany;
	private _JXTextField txtProject;
	private _JXTextField txtPhase;

	private _JDateChooser datePeriodFrom;
	private _JDateChooser datePeriodTo;
	private JComboBox cmbPmtFilter;

	private JButton btnPreview;
	private JButton btnCancel;

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

	public WaterPromoTagging() {
		super(title, true, true, false, true);
		initGUI();
	}

	public WaterPromoTagging(String title) {
		super(title);
		initGUI();
	}

	public WaterPromoTagging(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(panMain, BorderLayout.CENTER);
			panMain.setPreferredSize(frameSize);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				tabCenter = new JTabbedPane();
				panMain.add(tabCenter, BorderLayout.CENTER);
				{
					pnlTagPromo = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Tag Water Promo", null, pnlTagPromo);
					{
						JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
						pnlTagPromo.add(pnlNorth, BorderLayout.NORTH);
						pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Client Information"));
						{
							JPanel pnlLabel = new JPanel(new GridLayout(6, 1, 3, 3));
							pnlNorth.add(pnlLabel, BorderLayout.WEST);
							{
								JLabel lblClient = new JLabel("Client");
								pnlLabel.add(lblClient);
							}
							{
								JLabel lblProj = new JLabel("Proj.");
								pnlLabel.add(lblProj);
							}
							{
								JLabel lblUnit = new JLabel("Unit");
								pnlLabel.add(lblUnit);
							}
							{
								JLabel lblSeqNo = new JLabel("Seq.");
								pnlLabel.add(lblSeqNo);
							}
							{
								JLabel lblPymentType = new JLabel("Payment Type");
								pnlLabel.add(lblPymentType);
							}
							{
								JLabel lblDateStart = new JLabel("Date Start");
								pnlLabel.add(lblDateStart);
							}
						}
						{
							JPanel pnlComponents = new JPanel(new GridLayout(6, 1, 3, 3));
							pnlNorth.add(pnlComponents, BorderLayout.CENTER);
							{
								JPanel pnlClient = new JPanel(new BorderLayout(3, 3));
								pnlComponents.add(pnlClient);
								{
									lookupClient = new _JLookup(null, "Client", 0);
									pnlClient.add(lookupClient, BorderLayout.WEST);
									lookupClient.setPreferredSize(new Dimension(100, 0));
									lookupClient.setLookupSQL(sqlClients());
									lookupClient.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup)event.getSource()).getDataSet();

											if(data != null) {
												String entity_name = (String) data[1];
												String proj_id = (String) data[2];
												String proj_name = (String) data[3];
												String pbl_id = (String) data[4];
												String unit_desc = (String) data[5];
												Integer seq_no = (Integer) data[6];
												BigDecimal due_amt = (BigDecimal) data[7];

												txtClient.setText(entity_name);
												txtProjID.setText(proj_id);
												txtProjName.setText(proj_name);
												txtPBL.setText(pbl_id);
												txtUnitDesc.setText(unit_desc);
												txtSeqNo.setText(String.valueOf(seq_no));
												txtDueAmt.setValue(due_amt);

											}
										}
									});
								}
								{
									txtClient = new _JXTextField();
									pnlClient.add(txtClient, BorderLayout.CENTER);
								}
							}
							{
								JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
								pnlComponents.add(pnlProject);
								{
									txtProjID = new _JXTextField();
									pnlProject.add(txtProjID, BorderLayout.WEST);
									txtProjID.setPreferredSize(new Dimension(50, 0));
								}
								{
									txtProjName = new _JXTextField();
									pnlProject.add(txtProjName, BorderLayout.CENTER);
								}
							}
							{
								JPanel pnlUnit = new JPanel(new BorderLayout(3, 3));
								pnlComponents.add(pnlUnit);
								{
									txtPBL = new _JXTextField();
									pnlUnit.add(txtPBL, BorderLayout.WEST);
									txtPBL.setPreferredSize(new Dimension(50, 0));
								}
								{
									txtUnitDesc = new _JXTextField();
									pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
								}
							}
							{
								JPanel pnlSeq = new JPanel(new BorderLayout(3, 3));
								pnlComponents.add(pnlSeq);
								{
									txtSeqNo = new _JXTextField();
									pnlSeq.add(txtSeqNo, BorderLayout.WEST);
									txtSeqNo.setPreferredSize(new Dimension(50, 0));
								}
								{
									JPanel pnlDueAmt = new JPanel(new BorderLayout(3, 3));
									pnlSeq.add(pnlDueAmt);
									{
										JLabel lblDueAmt = new JLabel("Due Amt");
										pnlDueAmt.add(lblDueAmt, BorderLayout.WEST);
										lblDueAmt.setPreferredSize(new Dimension(100, 0));
									}
									{
										txtDueAmt = new _JXFormattedTextField(JXTextField.RIGHT);
										pnlDueAmt.add(txtDueAmt, BorderLayout.CENTER);
										txtDueAmt.setPreferredSize(new Dimension(100, 0));
										txtDueAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtDueAmt.setEditable(false);
									}
								}
							}
							{
								JPanel pnlPmtType = new JPanel(new BorderLayout(3, 3));
								pnlComponents.add(pnlPmtType);
								{
									cmbPmtType = new JComboBox(new String[] {"Full", "Installment"});
									pnlPmtType.add(cmbPmtType, BorderLayout.WEST);
									cmbPmtType.setPreferredSize(new Dimension(150, 0));
								}
							}
							{
								JPanel pnlDatePromo = new JPanel(new BorderLayout(3, 3));
								pnlComponents.add(pnlDatePromo);
								{
									datePromoStart = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlDatePromo.add(datePromoStart, BorderLayout.WEST);
									datePromoStart.setPreferredSize(new Dimension(150, 0));
								}
							}
						}
					}
					{
						JPanel pnlCenter = new JPanel(new BorderLayout(3, 3));
						pnlTagPromo.add(pnlCenter, BorderLayout.CENTER);
						{
							scrollWaterPromo = new JScrollPane();
							pnlCenter.add(scrollWaterPromo, BorderLayout.CENTER);
							{

								modelWaterPromo = new modelWaterPromoTagging();

								tblScrollWaterPromo = new _JTableMain(modelWaterPromo);
								scrollWaterPromo.setViewportView(tblScrollWaterPromo);
								scrollWaterPromo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								tblScrollWaterPromo.setSortable(false);

								modelWaterPromo.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {

										((DefaultListModel)rowHeaderWaterPromo.getModel()).addElement(modelWaterPromo.getRowCount());

										if(modelWaterPromo.getRowCount() == 0){
											rowHeaderWaterPromo.setModel(new DefaultListModel());
										}

									}
								});
							}
							{
								rowHeaderWaterPromo = tblScrollWaterPromo.getRowHeader();
								rowHeaderWaterPromo.setModel(new DefaultListModel());
								scrollWaterPromo.setRowHeaderView(rowHeaderWaterPromo);
								scrollWaterPromo.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
					}
					{
						JPanel pnlSouthTag = new JPanel(new GridLayout(1, 4, 3, 3));
						pnlTagPromo.add(pnlSouthTag, BorderLayout.SOUTH);
						{
							btnTagNew = new JButton("New");
							pnlSouthTag.add(btnTagNew);
							btnTagNew.setActionCommand("Tag New");
							btnTagNew.addActionListener(this);

						}
						{
							btnTagSave = new JButton("Save");
							pnlSouthTag.add(btnTagSave);
							btnTagSave.setActionCommand("Tag Save");
							btnTagSave.addActionListener(this);
						}
						{
							btnTagPreview = new JButton("Preview");
							pnlSouthTag.add(btnTagPreview);
							btnTagPreview.setActionCommand("Tag Preview");
							btnTagPreview.addActionListener(this);
						}
						{
							btnTagCancel = new JButton("Cancel");
							pnlSouthTag.add(btnTagCancel);
							btnTagCancel.setActionCommand("Tag Cancel");
							btnTagCancel.addActionListener(this);
						}
					}
				}
				{
					pnlReport = new JPanel(new BorderLayout(3, 3));
					tabCenter.addTab("Generate Tagged Promo", null, pnlReport);
					{
						JPanel pnlReportCenter = new JPanel(new BorderLayout(3, 3));
						pnlReport.add(pnlReportCenter, BorderLayout.CENTER);
						{
							JPanel pnlRCLabel = new JPanel(new GridLayout(6, 1, 3, 3));
							pnlReportCenter.add(pnlRCLabel, BorderLayout.WEST);
							{
								JLabel lblCompany = new JLabel("Company");
								pnlRCLabel.add(lblCompany);
							}
							{
								JLabel lblProject = new JLabel("Project");
								pnlRCLabel.add(lblProject);
							}
							{
								JLabel lblPhase = new JLabel("Phase");
								pnlRCLabel.add(lblPhase);
							}
							{
								JLabel lblDateFrom = new JLabel("Period from");
								pnlRCLabel.add(lblDateFrom);
							}
							{
								JLabel lblDateTo = new JLabel("Period To");
								pnlRCLabel.add(lblDateTo);
							}
							{
								JLabel lblPmtFilter = new JLabel("Pmt Type");
								pnlRCLabel.add(lblPmtFilter);
							}
						}
						{
							JPanel pnlRCComponents = new JPanel(new GridLayout(6, 1, 3, 3));
							pnlReportCenter.add(pnlRCComponents, BorderLayout.CENTER);
							{
								JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
								pnlRCComponents.add(pnlCompany);
								{
									lookupCompany = new _JLookup(null, "Company");
									pnlCompany.add(lookupCompany, BorderLayout.WEST);
									lookupCompany.setReturnColumn(0);
									lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
									lookupCompany.setPreferredSize(new Dimension(50, 0));
									lookupCompany.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {//XXX Project
											Object[] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){
												txtCompany.setText(data[1].toString());
												txtCompany.setToolTipText(data[2].toString());

												lookupProject.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));

											}else{
												txtCompany.setText("");
												txtCompany.setToolTipText("");
											}
										}
									});
								}
								{
									txtCompany = new _JXTextField();
									pnlCompany.add(txtCompany, BorderLayout.CENTER);
									txtCompany.setEditable(false);
								}
							}
							{
								JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
								pnlRCComponents.add(pnlProject);

								{
									lookupProject = new _JLookup(null, "Project", "Please select company.");
									pnlProject.add(lookupProject, BorderLayout.WEST);
									lookupProject.setReturnColumn(0);
									lookupProject.setPreferredSize(new Dimension(50, 0));
									lookupProject.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {//XXX Project
											Object[] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){
												txtProject.setText(data[1].toString());
												lookupPhase.setLookupSQL(SQL_PHASE_ALL(data[0].toString()));
											}else{
												txtProject.setText("");
											}
										}
									});
								}
								{
									txtProject = new _JXTextField();
									pnlProject.add(txtProject, BorderLayout.CENTER);
									txtProject.setEditable(false);
								}
							}
							{
								JPanel pnlPhase = new JPanel(new BorderLayout(3, 3));
								pnlRCComponents.add(pnlPhase);
								{
									lookupPhase = new _JLookup(null, "Phase", "Please select project.");
									pnlPhase.add(lookupPhase, BorderLayout.WEST);
									lookupPhase.setReturnColumn(0);
									lookupPhase.setPreferredSize(new Dimension(50, 0));
									lookupPhase.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {//XXX Phase
											Object[] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){
												txtPhase.setText(data[1].toString());
											}else{
												JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select project first.", "Phase", JOptionPane.WARNING_MESSAGE);
											}
										}
									});
								}
								{
									txtPhase = new _JXTextField();
									pnlPhase.add(txtPhase, BorderLayout.CENTER);
									txtPhase.setEditable(false);
								}
							}
							{
								JPanel pnlDateFrom = new JPanel(new BorderLayout(3, 3));
								pnlRCComponents.add(pnlDateFrom);
								{
									datePeriodFrom = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
									pnlDateFrom.add(datePeriodFrom, BorderLayout.WEST);
									datePeriodFrom.setDate(Calendar.getInstance().getTime());
									datePeriodFrom.setPreferredSize(new Dimension(150, 0));
								}
							}
							{
								JPanel pnlDateTo = new JPanel(new BorderLayout(3, 3));
								pnlRCComponents.add(pnlDateTo);
								{
									datePeriodTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
									pnlDateTo.add(datePeriodTo, BorderLayout.WEST);
									datePeriodTo.setDate(Calendar.getInstance().getTime());
									datePeriodTo.setPreferredSize(new Dimension(150, 0));
								}
							}
							{
								JPanel pnlFilterBy = new JPanel(new BorderLayout(3, 3));
								pnlRCComponents.add(pnlFilterBy);
								{
									cmbPmtFilter = new JComboBox(new DefaultComboBoxModel(new String[] { "All", "Full", "Installment" }));
									pnlFilterBy.add(cmbPmtFilter, BorderLayout.WEST);
									cmbPmtFilter.setPreferredSize(new Dimension(150, 0));
								}
							}
							
						}
					}
					{
						JPanel pnlReportSouth = new JPanel(new GridLayout(1, 3, 3, 3));
						pnlReport.add(pnlReportSouth, BorderLayout.SOUTH);
						{
							pnlReportSouth.add(Box.createHorizontalBox());
						}
						{
							btnPreview = new JButton("Preview");
							pnlReportSouth.add(btnPreview);
							btnPreview.setActionCommand("Preview Report Tagged Promo");
							btnPreview.addActionListener(this);
							btnPreview.setPreferredSize(new Dimension(0, 50));
						}
						{
							pnlReportSouth.add(Box.createHorizontalBox());
						}
					}
				}
			}
			tagCancel();
			displayTagClients();
		}
	}
	//XXX END OF INIT GUI

	private void btnState(Boolean tagNew, Boolean tagSave, Boolean tagPreview, Boolean tagCancel) {
		btnTagNew.setEnabled(tagNew);
		btnTagSave.setEnabled(tagSave);
		btnTagPreview.setEnabled(tagPreview);
		btnTagCancel.setEnabled(tagCancel);
	}

	private void setDate() {
		pgSelect db = new pgSelect();
		String SQL = "SELECT CURRENT_DATE";
		db.select(SQL);

		Date start_date = (Date) db.getResult()[0][0];
		datePromoStart.setDate(start_date);
	}

	private void tagNew() {
		lookupClient.setEnabled(true);
		cmbPmtType.setEnabled(true);
		datePromoStart.setEnabled(true);
		datePromoStart.getCalendarButton().setEnabled(true);
		btnState(false, true, false, true);
	}

	private void tagCancel() {
		lookupClient.setEnabled(false);
		cmbPmtType.setEnabled(false);
		datePromoStart.setEnabled(false);
		datePromoStart.getCalendarButton().setEnabled(false);
		lookupClient.setValue(null);
		txtClient.setText("");
		txtProjID.setText("");
		txtProjName.setText("");
		txtPBL.setText("");
		txtUnitDesc.setText("");
		txtSeqNo.setText("");
		cmbPmtType.setSelectedIndex(0);
		txtDueAmt.setValue(new BigDecimal("0.00"));
		setDate();
		btnState(true, false, true, false);
	}

	private String sqlClients() {
		String SQL = "select entity_id as \"Client ID\", get_client_name(a.entity_id) as \"Name\", \n"
				+ "a.proj_id as \"Proj ID\", b.proj_name as \"Project\",\n"
				+ "a.pbl_id as \"PBL\", get_merge_unit_desc_v3(a.entity_id, a.proj_id, a.pbl_id,a.seq_no), a.seq_no, abs(a.water_balance) as \"Due Amt\"\n"
				+ "from tmp_dues_for_collection a\n"
				+ "LEFT JOIN mf_project b on b.proj_id = a.proj_id\n"
				+ "where a.water_balance < 0\n"
				+ "and not exists (SELECT *\n"
				+ "			    FROM rf_water_promo\n"
				+ "			    WHERE entity_id = a.entity_id \n"
				+ "			    and proj_id = a.proj_id\n"
				+ "			    and pbl_id = a.pbl_id\n"
				+ "			    and seq_no = a.seq_no \n"
				+ "			    and status_id = 'A')\n"
				+ "";

		return SQL;
	}

	private void displayTagClients() {
		modelWaterPromo.clear();
		DefaultListModel listModel = new DefaultListModel();

		pgSelect db = new pgSelect();
		String SQL ="SELECT * FROM view_tagged_water_promo_clients()";
		db.select(SQL);
		FncSystem.out("Display SQL for Tagged Promo", SQL);

		if(db.isNotNull()) {
			for(int x= 0; x<db.getRowCount(); x++) {
				modelWaterPromo.addRow(db.getResult()[x]);
				listModel.addElement(modelWaterPromo.getRowCount());
			}
		}

		scrollWaterPromo.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(modelWaterPromo.getRowCount())));
		tblScrollWaterPromo.packAll();
	}

	private Boolean tagWaterPromo() {

		String entity_id = lookupClient.getValue();
		String proj_id = txtProjID.getText();
		String pbl_id = txtPBL.getText();
		String seq_no = txtSeqNo.getText();
		Integer pmt_type = cmbPmtType.getSelectedIndex();
		Date promo_start = datePromoStart.getDate();

		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_water_promo('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", "+pmt_type+", '"+promo_start+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL, "Save", true);
		FncSystem.out("Display tagging of water promo", SQL);

		return (Boolean) db.getResult()[0][0];
	}

	private void previewSOA(String entity_id, String proj_id, String pbl_id, String seq_no) {

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);

		FncReport.generateReport("/Reports/rptWaterPromoSched.jasper", "Water Promo SOA", mapParameters);

	}
	
	private void previewTaggedPromoReport() {

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("phase",lookupPhase.getValue());
		mapParameters.put("period_from", datePeriodFrom.getDate());
		mapParameters.put("period_to", datePeriodTo.getDate());
		mapParameters.put("pmt_type", cmbPmtFilter.getSelectedItem());
		
		FncReport.generateReport("/Reports/rptPromoWaterCollections.jasper", "Tagged Water Promo Sched", mapParameters);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Tag New")) {
			tagNew();
		}

		if(actionCommand.equals("Tag Save")) {
			if(tagWaterPromo()) {
				if(cmbPmtType.getSelectedIndex() == 1) {
					previewSOA(lookupClient.getValue(), txtProjID.getText(), txtPBL.getText(), txtSeqNo.getText());
				}
				tagCancel();
				displayTagClients();

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Water Promo Tagged", "Save", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if(actionCommand.equals("Tag Preview")) {
			if(tblScrollWaterPromo.getSelectedRowCount() == 1) {
				int selected_row = tblScrollWaterPromo.convertRowIndexToModel(tblScrollWaterPromo.getSelectedRow());
				String entity_id = (String) modelWaterPromo.getValueAt(selected_row, 5);
				String proj_id = (String) modelWaterPromo.getValueAt(selected_row, 6);
				String pbl_id = (String) modelWaterPromo.getValueAt(selected_row, 7);
				Integer seq_no = (Integer) modelWaterPromo.getValueAt(selected_row, 8);

				previewSOA(entity_id, proj_id, pbl_id, String.valueOf(seq_no));
			}else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select 1 to preview", "Preview", JOptionPane.WARNING_MESSAGE);
			}
		}

		if(actionCommand.equals("Tag Cancel")) {
			tagCancel();
		}
		
		if(actionCommand.equals("Preview Report Tagged Promo")) {
			previewTaggedPromoReport();
		}
	}
}
