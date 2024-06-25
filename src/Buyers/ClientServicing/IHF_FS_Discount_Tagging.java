package Buyers.ClientServicing;

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
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

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
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelIHFPromo;
import tablemodel.modelWaterPromoTagging;

public class IHF_FS_Discount_Tagging extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 8385103898513100873L;
	static String title = "IHF Discount Promo Tagging";
	Dimension frameSize = new Dimension(1000, 500);

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

	private modelIHFPromo modelIHFPromo;
	private JScrollPane scrollIHFPromo;
	private _JTableMain tblScrollIHFPromo;
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

	public IHF_FS_Discount_Tagging() {
		super(title, true, true, false, true);
		initGUI();
	}

	public IHF_FS_Discount_Tagging(String title) {
		super(title);
		initGUI();
	}

	public IHF_FS_Discount_Tagging(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
					tabCenter.addTab("Tag IHF FS Promo", null, pnlTagPromo);
					{
						JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
						pnlTagPromo.add(pnlNorth, BorderLayout.NORTH);
						pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Client Information"));
						{
							JPanel pnlLabel = new JPanel(new GridLayout(5, 1, 3, 3));
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
								JLabel lblPymentType = new JLabel("Discount Percentage");
								pnlLabel.add(lblPymentType);
							}
//							{
//								JLabel lblDateStart = new JLabel("Date Start");
//								pnlLabel.add(lblDateStart);
//							}
						}
						{
							JPanel pnlComponents = new JPanel(new GridLayout(5, 1, 3, 3));
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

												txtClient.setText(entity_name);
												txtProjID.setText(proj_id);
												txtProjName.setText(proj_name);
												txtPBL.setText(pbl_id);
												txtUnitDesc.setText(unit_desc);
												txtSeqNo.setText(String.valueOf(seq_no));

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
//								{
//									JPanel pnlDueAmt = new JPanel(new BorderLayout(3, 3));
//									pnlSeq.add(pnlDueAmt);
//									{
//										JLabel lblDueAmt = new JLabel("Due Amt");
//										pnlDueAmt.add(lblDueAmt, BorderLayout.WEST);
//										lblDueAmt.setPreferredSize(new Dimension(100, 0));
//									}
//									{
//										txtDueAmt = new _JXFormattedTextField(JXTextField.RIGHT);
//										pnlDueAmt.add(txtDueAmt, BorderLayout.CENTER);
//										txtDueAmt.setPreferredSize(new Dimension(100, 0));
//										txtDueAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
//										txtDueAmt.setEditable(false);
//									}
//								}
							}
							{
								JPanel pnlPmtType = new JPanel(new BorderLayout(3, 3));
								pnlComponents.add(pnlPmtType);
								{
									cmbPmtType = new JComboBox(new String[] {"10%", "15%", "20%"});
									pnlPmtType.add(cmbPmtType, BorderLayout.WEST);
									cmbPmtType.setPreferredSize(new Dimension(150, 0));
								}
							}
//							{
//								JPanel pnlDatePromo = new JPanel(new BorderLayout(3, 3));
//								pnlComponents.add(pnlDatePromo);
//								{
//									datePromoStart = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
//									pnlDatePromo.add(datePromoStart, BorderLayout.WEST);
//									datePromoStart.setPreferredSize(new Dimension(150, 0));
//								}
//							}
						}
					}
					{
						JPanel pnlCenter = new JPanel(new BorderLayout(3, 3));
						pnlTagPromo.add(pnlCenter, BorderLayout.CENTER);
						{
							scrollIHFPromo = new JScrollPane();
							pnlCenter.add(scrollIHFPromo, BorderLayout.CENTER);
							{

								modelIHFPromo = new modelIHFPromo();

								tblScrollIHFPromo = new _JTableMain(modelIHFPromo);
								scrollIHFPromo.setViewportView(tblScrollIHFPromo);
								scrollIHFPromo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								tblScrollIHFPromo.setSortable(false);

								modelIHFPromo.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {

										((DefaultListModel)rowHeaderWaterPromo.getModel()).addElement(modelIHFPromo.getRowCount());

										if(modelIHFPromo.getRowCount() == 0){
											rowHeaderWaterPromo.setModel(new DefaultListModel());
										}

									}
								});
							}
							{
								rowHeaderWaterPromo = tblScrollIHFPromo.getRowHeader();
								rowHeaderWaterPromo.setModel(new DefaultListModel());
								scrollIHFPromo.setRowHeaderView(rowHeaderWaterPromo);
								scrollIHFPromo.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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
//				{
//					pnlReport = new JPanel(new BorderLayout(3, 3));
//					tabCenter.addTab("Generate Tagged Promo", null, pnlReport);
//					{
//						JPanel pnlReportCenter = new JPanel(new BorderLayout(3, 3));
//						pnlReport.add(pnlReportCenter, BorderLayout.CENTER);
//						{
//							JPanel pnlRCLabel = new JPanel(new GridLayout(6, 1, 3, 3));
//							pnlReportCenter.add(pnlRCLabel, BorderLayout.WEST);
//							{
//								JLabel lblCompany = new JLabel("Company");
//								pnlRCLabel.add(lblCompany);
//							}
//							{
//								JLabel lblProject = new JLabel("Project");
//								pnlRCLabel.add(lblProject);
//							}
//							{
//								JLabel lblPhase = new JLabel("Phase");
//								pnlRCLabel.add(lblPhase);
//							}
//							{
//								JLabel lblDateFrom = new JLabel("Period from");
//								pnlRCLabel.add(lblDateFrom);
//							}
//							{
//								JLabel lblDateTo = new JLabel("Period To");
//								pnlRCLabel.add(lblDateTo);
//							}
//							{
//								JLabel lblPmtFilter = new JLabel("Pmt Type");
//								pnlRCLabel.add(lblPmtFilter);
//							}
//						}
//						{
//							JPanel pnlRCComponents = new JPanel(new GridLayout(6, 1, 3, 3));
//							pnlReportCenter.add(pnlRCComponents, BorderLayout.CENTER);
//							{
//								JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
//								pnlRCComponents.add(pnlCompany);
//								{
//									lookupCompany = new _JLookup(null, "Company");
//									pnlCompany.add(lookupCompany, BorderLayout.WEST);
//									lookupCompany.setReturnColumn(0);
//									lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
//									lookupCompany.setPreferredSize(new Dimension(50, 0));
//									lookupCompany.addLookupListener(new LookupListener() {
//										public void lookupPerformed(LookupEvent event) {//XXX Project
//											Object[] data = ((_JLookup)event.getSource()).getDataSet();
//											if(data != null){
//												txtCompany.setText(data[1].toString());
//												txtCompany.setToolTipText(data[2].toString());
//
//												lookupProject.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));
//
//											}else{
//												txtCompany.setText("");
//												txtCompany.setToolTipText("");
//											}
//										}
//									});
//								}
//								{
//									txtCompany = new _JXTextField();
//									pnlCompany.add(txtCompany, BorderLayout.CENTER);
//									txtCompany.setEditable(false);
//								}
//							}
//							{
//								JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
//								pnlRCComponents.add(pnlProject);
//
//								{
//									lookupProject = new _JLookup(null, "Project", "Please select company.");
//									pnlProject.add(lookupProject, BorderLayout.WEST);
//									lookupProject.setReturnColumn(0);
//									lookupProject.setPreferredSize(new Dimension(50, 0));
//									lookupProject.addLookupListener(new LookupListener() {
//										public void lookupPerformed(LookupEvent event) {//XXX Project
//											Object[] data = ((_JLookup)event.getSource()).getDataSet();
//											if(data != null){
//												txtProject.setText(data[1].toString());
//												lookupPhase.setLookupSQL(SQL_PHASE_ALL(data[0].toString()));
//											}else{
//												txtProject.setText("");
//											}
//										}
//									});
//								}
//								{
//									txtProject = new _JXTextField();
//									pnlProject.add(txtProject, BorderLayout.CENTER);
//									txtProject.setEditable(false);
//								}
//							}
//							{
//								JPanel pnlPhase = new JPanel(new BorderLayout(3, 3));
//								pnlRCComponents.add(pnlPhase);
//								{
//									lookupPhase = new _JLookup(null, "Phase", "Please select project.");
//									pnlPhase.add(lookupPhase, BorderLayout.WEST);
//									lookupPhase.setReturnColumn(0);
//									lookupPhase.setPreferredSize(new Dimension(50, 0));
//									lookupPhase.addLookupListener(new LookupListener() {
//										public void lookupPerformed(LookupEvent event) {//XXX Phase
//											Object[] data = ((_JLookup)event.getSource()).getDataSet();
//											if(data != null){
//												txtPhase.setText(data[1].toString());
//											}else{
//												JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select project first.", "Phase", JOptionPane.WARNING_MESSAGE);
//											}
//										}
//									});
//								}
//								{
//									txtPhase = new _JXTextField();
//									pnlPhase.add(txtPhase, BorderLayout.CENTER);
//									txtPhase.setEditable(false);
//								}
//							}
//							{
//								JPanel pnlDateFrom = new JPanel(new BorderLayout(3, 3));
//								pnlRCComponents.add(pnlDateFrom);
//								{
//									datePeriodFrom = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
//									pnlDateFrom.add(datePeriodFrom, BorderLayout.WEST);
//									datePeriodFrom.setDate(Calendar.getInstance().getTime());
//									datePeriodFrom.setPreferredSize(new Dimension(150, 0));
//								}
//							}
//							{
//								JPanel pnlDateTo = new JPanel(new BorderLayout(3, 3));
//								pnlRCComponents.add(pnlDateTo);
//								{
//									datePeriodTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
//									pnlDateTo.add(datePeriodTo, BorderLayout.WEST);
//									datePeriodTo.setDate(Calendar.getInstance().getTime());
//									datePeriodTo.setPreferredSize(new Dimension(150, 0));
//								}
//							}
//							{
//								JPanel pnlFilterBy = new JPanel(new BorderLayout(3, 3));
//								pnlRCComponents.add(pnlFilterBy);
//								{
//									cmbPmtFilter = new JComboBox(new DefaultComboBoxModel(new String[] { "All", "Full", "Installment" }));
//									pnlFilterBy.add(cmbPmtFilter, BorderLayout.WEST);
//									cmbPmtFilter.setPreferredSize(new Dimension(150, 0));
//								}
//							}
//							
//						}
//					}
//					{
//						JPanel pnlReportSouth = new JPanel(new GridLayout(1, 3, 3, 3));
//						pnlReport.add(pnlReportSouth, BorderLayout.SOUTH);
//						{
//							pnlReportSouth.add(Box.createHorizontalBox());
//						}
//						{
//							btnPreview = new JButton("Preview");
//							pnlReportSouth.add(btnPreview);
//							btnPreview.setActionCommand("Preview Report Tagged Promo");
//							btnPreview.addActionListener(this);
//							btnPreview.setPreferredSize(new Dimension(0, 50));
//						}
//						{
//							pnlReportSouth.add(Box.createHorizontalBox());
//						}
//					}
//				}
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
//		datePromoStart.setEnabled(true);
//		datePromoStart.getCalendarButton().setEnabled(true);
		btnState(false, true, false, true);
	}

	private void tagCancel() {
		lookupClient.setEnabled(false);
		cmbPmtType.setEnabled(false);
		lookupClient.setValue(null);
		txtClient.setText("");
		txtProjID.setText("");
		txtProjName.setText("");
		txtPBL.setText("");
		txtUnitDesc.setText("");
		txtSeqNo.setText("");
		cmbPmtType.setSelectedIndex(0);
//		txtDueAmt.setValue(new BigDecimal("0.00"));
		btnState(true, false, true, false);
	}

	private String sqlClients() {
		
		String SQL = "select a.entity_id as \"Client ID\", get_client_name(a.entity_id) as \"Name\",\n"
				+ "a.projcode as \"Proj ID\", b.proj_name as \"Project\", \n"
				+ "a.pbl_id as \"PBL\", get_merge_unit_desc_v3(a.entity_id, a.projcode, a.pbl_id, a.seq_no) as \"Unit\", \n"
				+ "a.seq_no as \"Seq\"\n"
				+ "FROM rf_sold_unit a\n"
				+ "LEFT JOIN mf_project b on b.proj_id = a.projcode\n"
				+ "WHERE a.currentstatus != '02'\n"
				+ "AND a.status_id = 'A'\n"
				+ "AND a.projcode IN ('004', '007', '001', '019')\n" //PROJECTS APPLICABLE BASED ON DCRF
				+ "AND CASE WHEN CURRENT_DATE <= '2024-12-31' THEN TRUE ELSE FALSE END\n" //PROMO PERIOD
//				+ "AND EXISTS (SELECT *\n"
//				+ "		    FROM rf_card_pmt_status\n"
//				+ "		    where entity_id = a.entity_id\n"
//				+ "		    and proj_id = a.projcode\n"
//				+ "		    and pbl_id = a.pbl_id \n"
//				+ "		    and seq_no = a.seq_no \n"
//				+ "		    and pmt_status ~*'CURRENT')\n"
				+ "and get_group_id(a.buyertype) = '02' "
				+ "order by get_client_name(a.entity_id)";
		

		return SQL;
	}

	private void displayTagClients() {
		modelIHFPromo.clear();
		DefaultListModel listModel = new DefaultListModel();

		pgSelect db = new pgSelect();
		String SQL ="SELECT * FROM view_tagged_ihf_fs_clients()";
		db.select(SQL);
		FncSystem.out("Display SQL for Tagged Promo", SQL);

		if(db.isNotNull()) {
			for(int x= 0; x<db.getRowCount(); x++) {
				modelIHFPromo.addRow(db.getResult()[x]);
				listModel.addElement(modelIHFPromo.getRowCount());
			}
		}

		scrollIHFPromo.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(modelIHFPromo.getRowCount())));
		tblScrollIHFPromo.packAll();
	}

	private Boolean tagIHFPromo() {

		String entity_id = lookupClient.getValue();
		String proj_id = txtProjID.getText();
		String pbl_id = txtPBL.getText();
		String seq_no = txtSeqNo.getText();
		Integer perc_disc = cmbPmtType.getSelectedIndex();

		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_tag_ihf_fs_promo('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", "+perc_disc+", '"+UserInfo.EmployeeCode+"')";
		db.select(SQL, "Save", true);
		FncSystem.out("Display tagging of water promo", SQL);

		return (Boolean) db.getResult()[0][0];
	}

	private void previewFSCM(String entity_id, String proj_id, String pbl_id, String seq_no) {

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);

		FncReport.generateReport("/Reports/rptIHF_FS_Promo_CreditMemo.jasper", "IHF FS Promo", mapParameters);

	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Tag New")) {
			tagNew();
		}

		if(actionCommand.equals("Tag Save")) {
			if(tagIHFPromo()) {
				//if(cmbPmtType.getSelectedIndex() == 1) {
					previewFSCM(lookupClient.getValue(), txtProjID.getText(), txtPBL.getText(), txtSeqNo.getText());
				//}
				tagCancel();
				displayTagClients();

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "IHF Promo Tagged", "Save", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if(actionCommand.equals("Tag Preview")) {
			if(tblScrollIHFPromo.getSelectedRowCount() == 1) {
				int selected_row = tblScrollIHFPromo.convertRowIndexToModel(tblScrollIHFPromo.getSelectedRow());
				String entity_id = (String) modelIHFPromo.getValueAt(selected_row, 6);
				String proj_id = (String) modelIHFPromo.getValueAt(selected_row, 7);
				String pbl_id = (String) modelIHFPromo.getValueAt(selected_row, 8);
				Integer seq_no = (Integer) modelIHFPromo.getValueAt(selected_row, 9);

				previewFSCM(entity_id, proj_id, pbl_id, String.valueOf(seq_no));
			}else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select 1 to preview", "Preview", JOptionPane.WARNING_MESSAGE);
			}
		}

		if(actionCommand.equals("Tag Cancel")) {
			tagCancel();
		}
		
		
	}

}
