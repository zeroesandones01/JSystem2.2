package Projects.ConstructionManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
	
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import Buyers.ClientServicing.TD_ROP_RefundAllocation;
import Buyers.LoansManagement._PagibigStatusMonitoring;
import Database.pgSelect;
import Database.pgUpdate;
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
import interfaces._GUI;
import tablemodel.modelQR_Codes;

public class GenerateQRCode extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = -1834829101657201605L;
	static String title = "QR Code Generator";
	Dimension frameSize = new Dimension(550, 500);

	static Border lineRed = BorderFactory.createLineBorder(Color.RED);

	private JPanel pnlNorth;
	private JPanel pnlCenter;
	private JPanel pnlSouth;

	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblNotice;

	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtPhaseID;
	private _JLookup txtNoticeID;
	private _JLookup txtBatchID;
	

	private JCheckBox chkBatch;

	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;
	private JTextField txtClient;
	private JTextField txtNotice;

	private _JTableMain tblQR_Codes;
	private modelQR_Codes model_QR;
	private JScrollPane scrollQR_Codes;
	private JList rowHeaderQR_Codes;

	private JButton btnTag;
	private JButton btnPreview;
	private JButton btnCancel;

	private String strUnit;
	
	private JCheckBox chkDisplayNotCompleted;

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public GenerateQRCode() {
		super(title, true, true, false, true);
		initGUI();
	}

	public GenerateQRCode(String title) {
		super(title);
		initGUI();
	}

	public GenerateQRCode(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);

		//hdmfNot = new _PagibigNotices();
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(panMain, BorderLayout.CENTER);
			panMain.setPreferredSize(frameSize);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				panMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 130));
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Notice Details"));
				{
					JPanel pnlNorthLabel = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
					{
						lblCompany = new JLabel("Company");
						pnlNorthLabel.add(lblCompany);
					}
					{
						lblProject = new JLabel("Project");
						pnlNorthLabel.add(lblProject);
					}
					{
						lblPhase = new JLabel("Phase");
						pnlNorthLabel.add(lblPhase);
					}
					{
						chkDisplayNotCompleted = new JCheckBox();
						pnlNorthLabel.add(chkDisplayNotCompleted);
						chkDisplayNotCompleted.addChangeListener(new ChangeListener() {
							
							@Override
							public void stateChanged(ChangeEvent e) {
								
								displayUnits(txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), chkDisplayNotCompleted.isSelected());
							
							}
						});
					}
				}
				{
					JPanel pnlNorthComponent = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlNorth.add(pnlNorthComponent, BorderLayout.CENTER);
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlCompany);
						{
							txtCoID = new _JLookup("");
							pnlCompany.add(txtCoID, BorderLayout.WEST);
							txtCoID.setHorizontalAlignment(JTextField.CENTER);
							txtCoID.setLookupSQL(_PagibigStatusMonitoring.sqlCompany());
							txtCoID.setReturnColumn(0);
							txtCoID.setValue("02");
							txtCoID.setPreferredSize(new Dimension(50, 0));
							txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtCompany.setText(data[1].toString());
										txtProID.setLookupSQL(_PagibigStatusMonitoring.sqlProject(txtCoID.getValue()));

										//btnPreview.setEnabled(true);
									} else {
										//btnPreview.setEnabled(false);									
									}
								}
							});
						}
						{
							txtCompany = new JTextField("");
							pnlCompany.add(txtCompany, BorderLayout.CENTER);
							txtCompany.setHorizontalAlignment(JTextField.CENTER);
							txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
						}
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlProject);
						{
							txtProID = new _JLookup("");
							pnlProject.add(txtProID, BorderLayout.WEST);
							txtProID.setHorizontalAlignment(JTextField.CENTER);
							txtProID.setLookupSQL(_PagibigStatusMonitoring.sqlProject(""));
							txtProID.setReturnColumn(0);
							txtProID.setValue("015");
							txtProID.setPreferredSize(new Dimension(50, 0));
							txtProID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										String proj_id = (String) data[0];
										txtProject.setText(data[1].toString());

										txtPhaseID.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(proj_id));
									}
								}
							});
						}
						{
							txtProject = new JTextField("");
							pnlProject.add(txtProject, BorderLayout.CENTER);
							txtProject.setHorizontalAlignment(JTextField.CENTER);
							txtProject.setText("TERRAVERDE RESIDENCES");
						}
					}
					{
						JPanel pnlPhase = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlPhase);
						{
							txtPhaseID = new _JLookup("");
							pnlPhase.add(txtPhaseID, BorderLayout.WEST);
							txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
							txtPhaseID.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(txtProID.getValue()));
							txtPhaseID.setReturnColumn(1);
							txtPhaseID.setPreferredSize(new Dimension(50, 0));
							txtPhaseID.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										txtPhase.setText(data[2].toString());
										displayUnits(txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(),chkDisplayNotCompleted.isSelected());
									}

								}
							});
						}
						{
							txtPhase = new JTextField("");
							pnlPhase.add(txtPhase, BorderLayout.CENTER);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
						}
					}
					
					{
						JLabel lblDisplayNotComplete = new JLabel("Display only not Completed Units");
						pnlNorthComponent.add(lblDisplayNotComplete);
					}
					
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				panMain.add(pnlCenter, BorderLayout.CENTER);
				{
					scrollQR_Codes = new JScrollPane();
					pnlCenter.add(scrollQR_Codes, BorderLayout.CENTER);
					{

						model_QR = new modelQR_Codes();

						tblQR_Codes = new _JTableMain(model_QR);
						scrollQR_Codes.setViewportView(tblQR_Codes);
						scrollQR_Codes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						tblQR_Codes.setSortable(false);
						tblQR_Codes.hideColumns("Unit ID");
					}
					{
						rowHeaderQR_Codes = tblQR_Codes.getRowHeader();
						rowHeaderQR_Codes.setModel(new DefaultListModel());
						scrollQR_Codes.setRowHeaderView(rowHeaderQR_Codes);
						scrollQR_Codes.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1, 5, 3, 3));
				panMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 40));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(true);
				}
				
			}
		}
		displayUnits(txtCoID.getValue(),txtProID.getValue(), txtPhaseID.getValue(), chkDisplayNotCompleted.isSelected());
		
	}//XXX END OF INIT GUI

	
	private void cancelPagibigNotices(){
		model_QR.clear();
		rowHeaderQR_Codes.setModel(new DefaultListModel());
		scrollQR_Codes.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
	}
	
	private void displayUnits(String co_id, String proj_id, String phase, Boolean not_complete_only) {
		if(model_QR != null && rowHeaderQR_Codes != null) {
			model_QR.clear();
			
			DefaultListModel listModel = new DefaultListModel();
			rowHeaderQR_Codes.setModel(listModel);
			
			String SQL = "SELECT false, a.description, a.unit_id, a.phase, a.block, a.lot, b.proj_name \n" + 
						 "FROM mf_unit_info a\n" + 
						 "LEFT JOIN mf_project b on b.proj_id = a.proj_id \n" +
						 "LEFT JOIN mf_company c on c.co_id = b.co_id \n"+
						 "WHERE (CASE WHEN NULLIF('"+co_id+"', 'null') IS NOT NULL THEN b.co_id = '"+co_id+"' else true end)\n" + 
						 "AND (CASE WHEN NULLIF('"+proj_id+"', 'null') IS NOT NULL THEN a.proj_id = '"+proj_id+"' else true end)\n" + 
						 "AND (CASE WHEN NULLIF('"+phase+"', 'null') IS NOT NULL THEN a.phase = '"+phase+"' else true end) \n"+
						 "AND (CASE WHEN "+not_complete_only+" THEN NOT EXISTS (SELECT * FROM co_ntp_accomplishment WHERE pbl_id = a.pbl_id AND percent_accomplish = 100 AND status_id = 'A') ELSE TRUE END) \n"+
						 "AND NOT EXISTS (SELECT * FROM rf_qr_units_printed where proj_id = a.proj_id and pbl_id = a.pbl_id and status_id = 'A') \n"+
						 //"AND EXISTS (SELECT * FROM co_ntp_accomplishment where pbl_id = a.pbl_id and as_of_date::dATE = '2021-06-29' AND status_id = 'A') \n"+
						 "ORDER BY getinteger(a.phase), getinteger(a.block), getinteger(a.lot)";
			pgSelect db = new pgSelect();
			db.select(SQL);
			
			FncSystem.out("SQL Units", SQL);
			
			if(db.isNotNull()) {
				for(int x= 0; x<db.getRowCount(); x++) {
					model_QR.addRow(db.getResult()[x]);
					listModel.addElement(model_QR.getRowCount());
				}
			}
			tblQR_Codes.packAll();
		}
	}
	
	private void previewQR() {
		
		
		ArrayList<TD_QR_Generator> listQR_Generator= new ArrayList<TD_QR_Generator>();
		Integer column = 1;
		ArrayList<String> listUnitID = new ArrayList<String>();
		
		for(int x = 0; x<model_QR.getRowCount(); x++) {
			Boolean isSelected = (Boolean) model_QR.getValueAt(x, 0);
			if(isSelected) {
				
				String description = (String) model_QR.getValueAt(x, 1);
				String unit_id = (String) model_QR.getValueAt(x, 2);
				String phase = (String) model_QR.getValueAt(x, 3);
				String block = (String) model_QR.getValueAt(x, 4);
				String lot = (String) model_QR.getValueAt(x, 5);
				String proj_name = (String) model_QR.getValueAt(x, 6);
				
				listQR_Generator.add(new TD_QR_Generator(unit_id, description, phase, block, lot, proj_name ,column));
				listUnitID.add(String.format("'%s'", unit_id));
				
				if(column == 1) {
					column = 2;
				}else {
					column = 1;
				};
			}
		}
		
		String unit_id = listUnitID.toString().replaceAll("\\[|\\]", "");
		
		pgSelect db = new pgSelect();

		String SQL = "SELECT sp_record_qr_unit_printed('"+txtProID.getValue()+"',ARRAY["+unit_id+"]::VARCHAR[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("listQR_Generator", listQR_Generator);
		FncReport.generateReport("/Reports/rptQR_Codes.jasper", "QR Codes", mapParameters);
	}

	public void actionPerformed(ActionEvent act) {
		String actionCommand = act.getActionCommand();

		if(actionCommand.equals("Preview")){
			if(txtProID.getValue() != null) {
				previewQR();
			}else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select project", "Preview", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		
	}
	
}
