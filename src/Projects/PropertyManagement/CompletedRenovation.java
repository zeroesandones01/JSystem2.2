package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_RenovationTable;

public class CompletedRenovation extends _JInternalFrame implements _GUI,ActionListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5550946790471729508L;

	Dimension size = new Dimension(1000, 600);
	static String title = "Completed Renovation";

	private JPanel pnlNorth;
	private JPanel pnlNorthCenter;
	private JPanel pnlNCLabel;
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;

	private JPanel pnlNCComponent;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private _JLookup lookupProject;
	private _JXTextField txtProject;
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;



	private JButton btnGenerate;
	private JButton btnSave;
	private JButton btnCancel;
	private JPanel pnlCenter;


	private _JTableMain tblRenovate;
	private JScrollPane scrollRenovate;
	private JList rowHeaderRenovate;
	private model_RenovationTable model_RenovationTable;





	public CompletedRenovation() {
		super(title, true, true, false, false);
		initGUI();
	}

	public CompletedRenovation(String title) {
		super(title);
		initGUI();
	}

	public CompletedRenovation(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, false, true, true, true);
		initGUI();
	}

	public void initGUI(){
		this.setTitle(title);
		this.setSize(size);
		this.setPreferredSize(size);

		{
			JPanel pnlMain = new JPanel (new BorderLayout(5,5));
			getContentPane().add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5,5,5,5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 130));
				{
					pnlNorthCenter = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					pnlNorthCenter.setBorder(JTBorderFactory.createTitleBorder("Client Name"));
					{
						pnlNCLabel = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNorthCenter.add(pnlNCLabel, BorderLayout.WEST);
						{
							lblCompany = new JLabel("Company:");
							pnlNCLabel.add(lblCompany);
						}
						{
							lblProject = new JLabel("Project:");
							pnlNCLabel.add(lblProject);
						}
						{
							lblPhase = new JLabel("Phase:");
							pnlNCLabel.add(lblPhase);
						}
						{
							pnlNCComponent = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlNorthCenter.add(pnlNCComponent, BorderLayout.CENTER);
						}
					}
					{
						pnlNCComponent = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNorthCenter.add(pnlNCComponent, BorderLayout.CENTER);
						{
							JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlCompany);
							{
								lookupCompany = new _JLookup(null, "Company", 0);
								pnlCompany.add(lookupCompany, BorderLayout.WEST);
								lookupCompany.setPreferredSize(new Dimension(100, 0));
								lookupCompany.setLookupSQL(sqlCompany());
								//lookupCompany.setValue("02");
								lookupCompany.setFilterName(true);
								lookupCompany.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										// TODO Auto-generated method stub
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null) {
											String co_id = (String) data[0];
											String company_name = (String) data[1];
											lookupProject.setLookupSQL(sqlProject(co_id));
											txtCompany.setText(company_name);
											//lookupProject.setLookupSQL(sqlProject(co_id));
										}

									}
								});

								{
									txtCompany = new _JXTextField();
									pnlCompany.add(txtCompany, BorderLayout.CENTER);
									//txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");

								}
							}
						}
						{
							JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlProject);
							{
								lookupProject = new _JLookup(null, "Project", 0, "Please select company");
								pnlProject.add(lookupProject, BorderLayout.WEST);
								lookupProject.setPreferredSize(new Dimension(100, 0));
								lookupProject.setFilterName(true);
								//lookupProject.setValue("015");
								lookupProject.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										// TODO Auto-generated method stub
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											String proj_id = (String) data[0];
											String proj_name = (String) data[1];

											lookupProject.setValue(proj_id);
											txtProject.setText(proj_name);
											lookupPhase.setLookupSQL(sqlPhase(proj_id));
										}
									}
								});

								{
									txtProject = new _JXTextField();
									pnlProject.add(txtProject, BorderLayout.CENTER);
									//txtProject.setText("TERRAVERDE RESIDENCES");
								}
							}
						}
						{

							JPanel pnlPhase = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlPhase);
							{
								lookupPhase = new _JLookup(null, "Phase", 0, "Please select project");
								pnlPhase.add(lookupPhase, BorderLayout.WEST);
								lookupPhase.setPreferredSize(new Dimension(100, 0));
								lookupPhase.setFilterName(true);
								lookupPhase.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										// TODO Auto-generated method stub
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											String description = (String) data[1];
											txtPhase.setText(description);
										}
									}
								});
								{
									txtPhase = new _JXTextField();
									pnlPhase.add(txtPhase, BorderLayout.CENTER);

								}

							}
						}

					}
				}
				{
					JPanel pnlNorthWest = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.EAST);
					pnlNorthWest.setPreferredSize(new Dimension(200, 0));
					{
						btnGenerate = new JButton("Generate");
						pnlNorthWest.add(btnGenerate);
						//btnGenerate.setActionCommand(btnGenerate.getText());
						btnGenerate.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								generate(lookupCompany.getValue(),lookupProject.getValue(),lookupPhase.getValue(),model_RenovationTable, rowHeaderRenovate);


								new Thread(new Runnable() {
									public void run() {
										FncGlobal.startProgress("Generating Accounts");
										tblRenovate.packAll();
										scrollRenovate.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRenovate.getRowCount())));
										FncGlobal.stopProgress();

									}
								}).start();

							}
						});
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					scrollRenovate = new JScrollPane();
					pnlCenter.add(scrollRenovate, BorderLayout.CENTER);
					scrollRenovate.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollRenovate.setBorder(new EmptyBorder(5,5,5,5));
					{
						model_RenovationTable = new model_RenovationTable();
						tblRenovate = new _JTableMain(model_RenovationTable);
						scrollRenovate.setViewportView(tblRenovate);
						tblRenovate.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblRenovate.hideColumns("ENTITY ID","PROJECT ID","PBL ID","SEQ. NO.");

					}
				}
				{
					rowHeaderRenovate = tblRenovate.getRowHeader();
					rowHeaderRenovate.setModel(new DefaultListModel());
					scrollRenovate.setRowHeaderView(rowHeaderRenovate);
					scrollRenovate.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				JPanel pnlCenterBottom = new JPanel(new GridLayout(1, 7, 3, 3));
				pnlCenter.add(pnlCenterBottom, BorderLayout.SOUTH);
				pnlCenterBottom.setPreferredSize(new Dimension(0, 40));
				{
					pnlCenterBottom.add(Box.createHorizontalBox());
					pnlCenterBottom.add(Box.createHorizontalBox());
					pnlCenterBottom.add(Box.createHorizontalBox());
					pnlCenterBottom.add(Box.createHorizontalBox());
					{
						btnSave = new JButton("Save");
						pnlCenterBottom.add(btnSave);
						btnSave.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to save?","Save",
										JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){

									removeValue();
									FncTables.clearTable(model_RenovationTable);


								}


							}
						});
					}
					{
						btnCancel = new JButton("Cancel");
						pnlCenterBottom.add(btnCancel);
						btnCancel.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to cancel?", "Cancel", 
										JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
									cancelValue();
								}
							}
						});					}
				}
			}
		}

	}
	public String sqlCompany() {//XXX Company
		/* edited on No. 26, 2014 by Del Gonzales as needed in PV preview */
		String SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
		return SQL;
	}

	public String sqlProject(String co_id) {
		return "select a.proj_id as \"ID\", a.proj_name as \"Proj. Name\", \n" + 
				"a.proj_alias as \"Proj. Alias\"\n" + 
				"from mf_project a\n" + 
				"where a.status_id = 'A'\n" + 
				"and a.co_id = '"+co_id+"';\n";
	}

	public String sqlPhase(String proj_id) {
		return "SELECT sp.phase as \"Phase\", FORMAT('%s %s', 'Phase', sp.phase) as \"Description\", \n" + 
				"FORMAT('%s%s', p.proj_alias, sp.phase)\n" + 
				"FROM mf_sub_project sp\n" + 
				"left join mf_project p on p.proj_id = sp.proj_id\n" + 
				"where sp.proj_id = '"+proj_id+"' AND sp.status_id = 'A'\n" +//ADDED STATUS ID BY LESTER DCRF 2718 
				"order by sp.phase;";
	}
	private void generate(String co_id, String proj_id, String phase,DefaultTableModel model_RenovationTable, JList rowHeaderRenovate) {
		//System.out.print("Dumaan dito");
		if(model_RenovationTable != null && rowHeaderRenovate != null){

			FncTables.clearTable(model_RenovationTable);
			DefaultListModel listModel = new DefaultListModel();
			rowHeaderRenovate.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM leffjoin_function(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'))";
			db.select(SQL);

			FncSystem.out("Display value of SQL", SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model_RenovationTable.addRow(db.getResult()[x]);
					listModel.addElement(tblRenovate.getRowCount());
				}
			}
		}
	}
	private void removeValue(){
		//		if(tblRenovate.getSelectedRows().length==1);{
		//			int rows = tblRenovate.getSelectedRow();
		for(int x= 0; x<model_RenovationTable.getRowCount(); x++){
			Boolean isSelected =(Boolean) model_RenovationTable.getValueAt(x, 0);
			if(isSelected){

				String entityId = (String) model_RenovationTable.getValueAt(x, 4);
				String projectId = (String) model_RenovationTable.getValueAt(x, 5);
				String pblId = (String) model_RenovationTable.getValueAt(x, 6);
				Integer seqNo = (Integer) model_RenovationTable.getValueAt(x, 7);


				pgUpdate db = new pgUpdate();
				String query = "UPDATE rf_qualified4orientation SET ongoing_renovation = null WHERE entity_id = '"+entityId+"' AND projcode = '"+projectId+"' "
						+ "AND pbl_id = '"+pblId+"' AND seq_no = '"+seqNo+"' ";
				db.executeUpdate(query, true);
				db.commit();
				System.out.println("Dumaan dito");
			}
		}
	}
	private void cancelValue(){
		FncTables.clearTable(model_RenovationTable);
		rowHeaderRenovate.setModel(new DefaultListModel());
		scrollRenovate.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(null));
		lookupCompany.setValue(null);
		lookupProject.setValue(null);
		lookupPhase.setValue(null);
		txtCompany.setText(null);
		txtProject.setText(null);
		txtPhase.setText(null);
	}

}
