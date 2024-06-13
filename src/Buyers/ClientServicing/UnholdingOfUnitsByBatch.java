package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelHoldingAndReservation_HoldUnits;

/**
 * @author Alvin Gonzales (2015-05-18)
 *
 */
public class UnholdingOfUnitsByBatch extends _JInternalFrame implements ActionListener, _GUI, AncestorListener {

	private static final long serialVersionUID = -5526968677502576834L;

	static String title = "Unholding of Units by batch";
	Dimension frameSize = new Dimension(700, 500);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JButton btnGenerate;

	private _JLookup lookupCompany;
	private _JXTextField txtCompanyName;
	private _JLookup lookupProject;
	private _JXTextField txtProjectName;
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;

	private JCheckBox chkIncludeManagementHoldAccounts;

	private JScrollPane scrollHoldUnits;
	private _JTableMain tblHoldUnits;
	private modelHoldingAndReservation_HoldUnits modelHoldUnits;
	private JList rowheaderHoldUnits;
	
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnPreview;

	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	public UnholdingOfUnitsByBatch() {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new Dimension(frameSize));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			JPanel pnlMain = new JPanel(new BorderLayout(3, 3));
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(688, 100));
				{
					JPanel pnlNorthWest = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new Dimension(127, 81));
					{
						JPanel pnlLabels = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlNorthWest.add(pnlLabels, BorderLayout.WEST);
						pnlLabels.setPreferredSize(new Dimension(65, 0));
						{
							JLabel lblCompany = new JLabel("Company");
							pnlLabels.add(lblCompany);
						}
						{
							JLabel lblProject = new JLabel("Project");
							pnlLabels.add(lblProject);
						}
						{
							JLabel lblPhase = new JLabel("Phase");
							pnlLabels.add(lblPhase);
						}
					}
					{
						JPanel pnlLookups = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlNorthWest.add(pnlLookups, BorderLayout.CENTER);
						pnlLookups.setPreferredSize(new Dimension(75, 0));
						{
							lookupCompany = new _JLookup(null, "Company");
							pnlLookups.add(lookupCompany);
							lookupCompany.setReturnColumn(0);
							lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {//XXX Project
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtCompanyName.setText(data[1].toString());

										lookupProject.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));
										manager.focusNextComponent();
									}else{
										txtCompanyName.setText("");
									}
								}
							});
						}
						{
							lookupProject = new _JLookup(null, "Project", "Please select company.");
							pnlLookups.add(lookupProject);
							lookupProject.setReturnColumn(0);
							lookupProject.setBounds(90, 40, 50, 25);
							lookupProject.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {//XXX Project
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtProjectName.setText(data[1].toString());
										lookupPhase.setLookupSQL(SQL_PHASE_ALL(data[0].toString()));
										manager.focusNextComponent();
									}else{
										txtProjectName.setText("");
									}
								}
							});
						}
						{
							lookupPhase = new _JLookup(null, "Phase", "Please select project.");
							pnlLookups.add(lookupPhase);
							lookupPhase.setReturnColumn(0);
							lookupPhase.setBounds(90, 70, 50, 25);
							lookupPhase.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {//XXX Phase
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtPhase.setText(data[1].toString());
										manager.focusNextComponent();
									}else{
										JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select project first.", "Phase", JOptionPane.WARNING_MESSAGE);
									}
								}
							});
						}
					}
				}
				{
					JPanel pnlNorthCenter = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					{
						txtCompanyName = new _JXTextField();
						pnlNorthCenter.add(txtCompanyName);
					}
					{
						txtProjectName = new _JXTextField();
						pnlNorthCenter.add(txtProjectName);
					}
					{
						txtPhase = new _JXTextField();
						pnlNorthCenter.add(txtPhase);
					}
					{
						chkIncludeManagementHoldAccounts = new JCheckBox("Include Management-Hold Accounts");
						pnlNorthCenter.add(chkIncludeManagementHoldAccounts);
					}
				}
				{
					btnGenerate = new JButton("Generate");
					pnlNorth.add(btnGenerate, BorderLayout.EAST);
					btnGenerate.setPreferredSize(new Dimension(200, 81));
					btnGenerate.addActionListener(this);
				}
			}
			{
				scrollHoldUnits = new JScrollPane();
				pnlMain.add(scrollHoldUnits, BorderLayout.CENTER);
				scrollHoldUnits.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollHoldUnits.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						tblHoldUnits.clearSelection();
					}
				});
				{
					modelHoldUnits = new modelHoldingAndReservation_HoldUnits();
					modelHoldUnits.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							//Addition of rows
							if(e.getType() == 1){
								((DefaultListModel)rowheaderHoldUnits.getModel()).addElement(modelHoldUnits.getRowCount());

								if(modelHoldUnits.getRowCount() == 0){
									rowheaderHoldUnits.setModel(new DefaultListModel());
								}
							}
						}
					});

					tblHoldUnits = new _JTableMain(modelHoldUnits);
					scrollHoldUnits.setViewportView(tblHoldUnits);
					tblHoldUnits.hideColumns("Client Seq. No.", "Client ID", "Unit ID", "Sequence");

					tblHoldUnits.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if(!e.getValueIsAdjusting()){//XXX tblJVDetail

							}
						}
					});
				}
				{
					rowheaderHoldUnits = tblHoldUnits.getRowHeader();
					scrollHoldUnits.setRowHeaderView(rowheaderHoldUnits);
					scrollHoldUnits.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					scrollHoldUnits.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblHoldUnits.getRowCount())));
				}
			}
			{
				JPanel pnlSouth = new JPanel(new BorderLayout());
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(688, 32));
				{
					JPanel pnlPreview = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlPreview, BorderLayout.WEST);
					pnlPreview.setPreferredSize(new Dimension(100, 30));
					{
						btnPreview = new JButton("Preview");
						pnlPreview.add(btnPreview);
						btnPreview.addActionListener(this);
					}
				}
				{
					JPanel pnlNavigation = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setPreferredSize(new Dimension(205, 30));
					{
						btnSave = new JButton("Save");
						pnlNavigation.add(btnSave);
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			}
		}

		btnState(false, false);
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, btnGenerate));
	}

	private void btnState(Boolean sSave, Boolean sCancel) {
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private Boolean toGenerate() {
		if(lookupCompany.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select company.", "Generate", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void generateHoldUnits() {
		rowheaderHoldUnits.setModel(new DefaultListModel());
		displayHoldUnits(modelHoldUnits, lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), chkIncludeManagementHoldAccounts.isSelected());
		scrollHoldUnits.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblHoldUnits.getRowCount())));
		tblHoldUnits.packAll();

		Boolean hasClients = tblHoldUnits.getRowCount() > 0;
		btnState(hasClients, hasClients);
	}

	private Boolean toSave() {
		ArrayList<Boolean> toSave = new ArrayList<Boolean>();
		for(int x=0; x < modelHoldUnits.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelHoldUnits.getValueAt(x, 0);
			toSave.add(isSelected);
		}

		if(!toSave.contains(true)){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client to unhold.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private Boolean saveUnholdUnits() {
		return saveUnholdUnits(modelHoldUnits, lookupProject.getValue());
	}

	private void cancelUnholdingUnits() {
		lookupCompany.setValue(null);
		txtCompanyName.setText("");
		lookupProject.setValue(null);
		lookupProject.setLookupSQL(null);
		txtProjectName.setText("");
		lookupPhase.setValue(null);
		lookupPhase.setLookupSQL(null);
		txtPhase.setText("");

		rowheaderHoldUnits.setModel(new DefaultListModel());
		modelHoldUnits.clear();
		scrollHoldUnits.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer("0"));
		tblHoldUnits.packAll();
		btnState(false, false);
	}
	
	private void previewHoldUnits() {
		Map<String, Object> mapHoldUnits = new HashMap<String, Object>();

		mapHoldUnits.put("co_id", lookupCompany.getValue());
		mapHoldUnits.put("company", txtCompanyName.getText());
		mapHoldUnits.put("proj_id", lookupProject.getValue());
		mapHoldUnits.put("phase", lookupPhase.getValue());
		mapHoldUnits.put("prepared_by_alias", UserInfo.Alias);
		mapHoldUnits.put("prepared_by", UserInfo.FullName);
		mapHoldUnits.put("include_management_hold", chkIncludeManagementHoldAccounts.isSelected());

		FncReport.generateReport("/Reports/rptHoldUnits.jasper", title, txtProjectName.getText(), txtPhase.getText(), mapHoldUnits);
	}

	public static void displayHoldUnits(modelHoldingAndReservation_HoldUnits model, String co_id, String proj_id, String phase, Boolean includeManagementHold) {
		model.clear();

		String SQL = "SELECT * FROM view_hold_units('"+ co_id +"', '"+ proj_id +"', '"+ phase +"', "+ includeManagementHold +", null);";

		FncSystem.out("Client Documents", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				String client_seqno = (String) db.getResult()[x][0];
				String client_id = (String) db.getResult()[x][21];
				String client_name = (String) db.getResult()[x][2];
				String unit_id = (String) db.getResult()[x][24];
				String unit = (String) db.getResult()[x][3];
				Integer sequence = (Integer) db.getResult()[x][25];
				BigDecimal tcp = (BigDecimal) db.getResult()[x][4];
				Timestamp hold_date = (Timestamp) db.getResult()[x][9];
				Timestamp hold_until = (Timestamp) db.getResult()[x][10];
				Timestamp extend_date = (Timestamp) db.getResult()[x][11];
				Timestamp extend_until = (Timestamp) db.getResult()[x][12];
				BigDecimal payment = (BigDecimal) db.getResult()[x][13];
				String status = (String) db.getResult()[x][17];
				String remarks = (String) db.getResult()[x][18];

				model.addRow(new Object[]{ false, client_seqno, client_id, client_name, unit_id, unit, sequence, tcp, (extend_date == null ? hold_date:extend_date), (extend_until == null ? hold_until:extend_until), payment, remarks, status });
			}
		}
	}

	public static Boolean saveUnholdUnits(modelHoldingAndReservation_HoldUnits model, String proj_id) {
		ArrayList<String> listClientSeqNo = new ArrayList<String>();
		ArrayList<String> listPBL_ID = new ArrayList<String>();

		for(int x=0; x < model.getRowCount(); x++){
			Boolean selected = (Boolean) model.getValueAt(x, 0);
			String client_seqno = (String) model.getValueAt(x, 1);
			String pbl_id = (String) model.getValueAt(x, 4);

			if(selected){
				if(client_seqno != null){
					listClientSeqNo.add(String.format("'%s'", client_seqno));
				}else{
					client_seqno = (String) model.getValueAt(x, 4);
					//listClientSeqNo.add(String.format("'%s'", client_seqno));
					listClientSeqNo.add(String.format("''"));
				}
				listPBL_ID.add(String.format("'%s'", pbl_id));
				System.out.printf("Client Seq. No: %s", String.format("'%s'", client_seqno));
			}
		}

		String client_seqno = listClientSeqNo.toString().replaceAll("\\[|\\]", "");
		
		String pbl_id = listPBL_ID.toString().replaceAll("\\[|\\]", "");
		//System.out.printf("Client Seq. No: %s", client_seqno);

		String SQL = "SELECT sp_save_unholding(ARRAY["+ client_seqno +"]::VARCHAR[], '"+proj_id+"', ARRAY["+pbl_id+"]::VARCHAR[] ,'"+ UserInfo.EmployeeCode +"');";

		FncSystem.out("Unholding", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL, "Save", true);

		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("Generate")){
			if(toGenerate()){
				generateHoldUnits();
			}
		}
		
		if(action.equals("Preview")){
			if(toGenerate()){
				previewHoldUnits();
			}
		}

		if(action.equals("Save")){
			if(toSave()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					Boolean hadSaved = saveUnholdUnits();
					if(hadSaved){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client(s) has beed unhold successfuly.", "Save", JOptionPane.INFORMATION_MESSAGE);
						generateHoldUnits();
						previewHoldUnits();
					}
				}
			}
		}

		if(action.equals("Cancel")){
			cancelUnholdingUnits();
		}
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupCompany.grabFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

}
