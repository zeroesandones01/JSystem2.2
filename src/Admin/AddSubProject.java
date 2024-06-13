/**
 * 
 */
package Admin;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelAddSubProject;
import DateChooser._JDateChooser;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class AddSubProject extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = -6259830623467767396L;

	Dimension frameSize = new Dimension(500, 500);// 510, 250
	static String title = "Add Sub Project";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthEast;
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblSubProjID;
	private JLabel lblSubProjPhase;
	private JLabel lblReleaseBatch;
	private JLabel lblWithChangeModel;

	private JPanel pnlNorthWest;
	private JPanel pnlCompany;
	private JPanel pnlProject;
	
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private _JLookup lookupProject;
	private _JXTextField txtProject;

	private JPanel pnlSubProjID;
	private _JXTextField txtSubProjID;
	private JLabel lblSubProjAlias;
	private _JXTextField txtSubProjAlias;

	private JPanel pnlSubProjPhase;
	private _JXTextField txtSubProjPhase;
	private JLabel lblSubProjName;
	private _JXTextField txtSubProjName;

	private JPanel pnlReleaseBatch;
	private _JXTextField txtReleaseBatch;
	private JLabel lblPagibigCode;
	private _JXTextField txtPagibigCode;

	private JPanel pnlChangeModel;
	private JCheckBox chkChangeModel;
	private JLabel lblLTSDate;
	private _JDateChooser dteLTS;

	private JPanel pnlCenter;
	private modelAddSubProject modelSubProj;
	private JScrollPane scrollSubProject;
	private _JTableMain tblSubProject;
	private JList rowHeaderSubProject;

	private JPanel pnlSouth;
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnEdit;
	private JButton btnCancel;

	private JLabel lblBOI_reg_no;

	private JPanel pnlBOI_regist;

	private _JXTextField txtBOI_regist;

	private JLabel lblBOIDate;

	private _JDateChooser dteBOI;

	/*public AddSubProject() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AddSubProject(String title) {
		super(title, false, true, false, true);
		initGUI();
	}*/

	public AddSubProject() {
		initGUI();
	}

	public AddSubProject(LayoutManager layout) {
		super(layout);
	}

	public AddSubProject(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public AddSubProject(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		//this.setTitle(title);
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(new Dimension(0, 0));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(EMPTY_BORDER);
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new java.awt.Dimension(540, 217));
				//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Sub Project Details"));
				{
					pnlNorthWest = new JPanel(new GridLayout(7, 1, 3, 3));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					{
						lblSubProjID = new JLabel("Sub Proj.ID");
						pnlNorthWest.add(lblSubProjID);
					}
					{
						lblSubProjPhase = new JLabel("*Sub Proj. Phase");
						pnlNorthWest.add(lblSubProjPhase);
					}
					{
						lblCompany = new JLabel("*Company");
						pnlNorthWest.add(lblCompany);
					}
					{
						lblProject = new JLabel("*Project");
						pnlNorthWest.add(lblProject);
					}
					{
						lblReleaseBatch = new JLabel("Release Batch");
						pnlNorthWest.add(lblReleaseBatch);
					}
					{
						lblBOI_reg_no = new JLabel("BOI Regist. No.");
						pnlNorthWest.add(lblBOI_reg_no);
					}
					{
						lblWithChangeModel = new JLabel("With Change Model");
						pnlNorthWest.add(lblWithChangeModel);
					}
				}
				{
					pnlNorthEast = new JPanel(new GridLayout(7, 1, 3, 3));
					pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
					{
						pnlSubProjID = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlSubProjID);
						{
							txtSubProjID = new _JXTextField("ID");
							pnlSubProjID.add(txtSubProjID, BorderLayout.WEST);
							txtSubProjID.setPreferredSize(new Dimension(50, 0));
							txtSubProjID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							lblSubProjAlias = new JLabel("*Sub Proj Alias", JLabel.TRAILING);
							pnlSubProjID.add(lblSubProjAlias, BorderLayout.CENTER);
						}
						{
							txtSubProjAlias = new _JXTextField("Alias");
							pnlSubProjID.add(txtSubProjAlias, BorderLayout.EAST);
							txtSubProjAlias.setPreferredSize(new Dimension(100, 0));
						}
					}
					{
						pnlSubProjPhase = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlSubProjPhase);
						{
							txtSubProjPhase = new _JXTextField("Phase");
							pnlSubProjPhase.add(txtSubProjPhase, BorderLayout.WEST);
							txtSubProjPhase.setPreferredSize(new Dimension(50, 0));
							txtSubProjPhase.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							lblSubProjName = new JLabel("*Sub Proj. Name", JLabel.TRAILING);
							pnlSubProjPhase.add(lblSubProjName, BorderLayout.CENTER);
						}
						{
							txtSubProjName = new _JXTextField("Sub Proj Name");
							pnlSubProjPhase.add(txtSubProjName, BorderLayout.EAST);
							txtSubProjName.setPreferredSize(new Dimension(100, 0));
						}
					}
					{
						pnlCompany = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlCompany);
						{
							lookupCompany = new _JLookup(null, "Select Company", 0);
							pnlCompany.add(lookupCompany, BorderLayout.WEST);
							lookupCompany.setPreferredSize(new Dimension(50, 0));
							lookupCompany.setLookupSQL(sqlCompany());
							lookupCompany.setEnabled(false);
							lookupCompany.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String company_name = (String) data[1];
										txtCompany.setText(company_name);
										lookupProject.setLookupSQL(sqlProject((String) data[0]));
									}
								}
							});
						}
						{
							txtCompany = new _JXTextField("Company Name");
							pnlCompany.add(txtCompany);
						}
					}
					{
						pnlProject = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlProject);
						{
							lookupProject = new _JLookup(null, "Select Project", 0, "Please Select Company first");
							pnlProject.add(lookupProject, BorderLayout.WEST);
							lookupProject.setEnabled(false);
							lookupProject.setPreferredSize(new Dimension(50, 0));
							lookupProject.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										String proj_name = (String) data[1];
										txtProject.setText(proj_name);
									}
								}
							});
						}
						{
							txtProject = new _JXTextField("Project Name");
							pnlProject.add(txtProject);
						}
					}
					{
						pnlReleaseBatch = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlReleaseBatch);
						{
							txtReleaseBatch = new _JXTextField("Release Batch");
							pnlReleaseBatch.add(txtReleaseBatch, BorderLayout.WEST);
							txtReleaseBatch.setPreferredSize(new Dimension(100, 0));
						}
						{
							lblPagibigCode = new JLabel("PAG-IBIG Code", JLabel.TRAILING);
							pnlReleaseBatch.add(lblPagibigCode, BorderLayout.CENTER);
						}
						{
							txtPagibigCode = new _JXTextField("Pag-ibig Code");
							pnlReleaseBatch.add(txtPagibigCode, BorderLayout.EAST);
							txtPagibigCode.setPreferredSize(new Dimension(100, 0));
						}
					}
					{
						pnlBOI_regist = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlBOI_regist);
						{
							txtBOI_regist = new _JXTextField("Reg. No.");
							pnlBOI_regist.add(txtBOI_regist, BorderLayout.WEST);
							txtBOI_regist.setPreferredSize(new Dimension(100, 0));
						}
						{
							lblBOIDate = new JLabel("BOI Date", JLabel.TRAILING);
							pnlBOI_regist.add(lblBOIDate, BorderLayout.CENTER);
						}
						{
							dteBOI = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlBOI_regist.add(dteBOI, BorderLayout.EAST);
							dteBOI.setPreferredSize(new Dimension(100, 0));
							dteBOI.setEnabled(false);
						}
					}
					{
						pnlChangeModel = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlChangeModel);
						{
							chkChangeModel = new JCheckBox();
							pnlChangeModel.add(chkChangeModel, BorderLayout.WEST);
							chkChangeModel.setEnabled(false);
						}
						{
							lblLTSDate = new JLabel("LTS Date", JLabel.TRAILING);
							pnlChangeModel.add(lblLTSDate, BorderLayout.CENTER);
						}
						{
							dteLTS = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlChangeModel.add(dteLTS, BorderLayout.EAST);
							dteLTS.setPreferredSize(new Dimension(100, 0));
							dteLTS.setEnabled(false);
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Sub Project List"));
				{
					scrollSubProject = new JScrollPane();
					pnlCenter.add(scrollSubProject, BorderLayout.CENTER);
					scrollSubProject.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelSubProj = new modelAddSubProject();
						tblSubProject = new _JTableMain(modelSubProj);
						scrollSubProject.setViewportView(tblSubProject);
						tblSubProject.hideColumns("Co. ID", "Company Name", "Proj. ID", "Proj. Name", "Mun. ID", "Vatable");

						modelSubProj.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {

								((DefaultListModel)rowHeaderSubProject.getModel()).addElement(modelSubProj.getRowCount());

								if(modelSubProj.getRowCount() == 0){
									rowHeaderSubProject.setModel(new DefaultListModel());
								}
							}
						});

						tblSubProject.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								if(!arg0.getValueIsAdjusting()){
									try {
										if(tblSubProject.getSelectedRows().length ==1){
											int row = tblSubProject.getSelectedRow();
											String sub_proj_id = (String) modelSubProj.getValueAt(row, 0);
											String sub_proj_name = (String) modelSubProj.getValueAt(row, 1);
											String sub_proj_alias = (String) modelSubProj.getValueAt(row, 2);
											String sub_proj_phase = (String) modelSubProj.getValueAt(row, 3);
											String co_id = (String) modelSubProj.getValueAt(row, 4);
											String company_name = (String) modelSubProj.getValueAt(row, 5);
											String proj_id = (String) modelSubProj.getValueAt(row, 6);
											String proj_name = (String) modelSubProj.getValueAt(row, 7);
											String release_batch = (String) modelSubProj.getValueAt(row, 8);
											String pagibig_code = (String) modelSubProj.getValueAt(row, 9);
											Boolean with_change_model = (Boolean) modelSubProj.getValueAt(row, 10);
											Date lts_date = (Date) modelSubProj.getValueAt(row, 11);
											Date boi_date = (Date) modelSubProj.getValueAt(row, 12);
											String boi_reg_no = (String) modelSubProj.getValueAt(row, 13);

											txtSubProjID.setText(sub_proj_id);
											txtSubProjName.setText(sub_proj_name);
											txtSubProjAlias.setText(sub_proj_alias);
											txtSubProjPhase.setText(sub_proj_phase);
											lookupCompany.setValue(co_id);
											txtCompany.setText(company_name);
											lookupProject.setValue(proj_id);
											txtProject.setText(proj_name);
											txtReleaseBatch.setText(release_batch);
											txtPagibigCode.setText(pagibig_code);
											chkChangeModel.setSelected(with_change_model);
											dteLTS.setDate(lts_date);
											dteBOI.setDate(boi_date);
											txtBOI_regist.setText(boi_reg_no);

											btnState(true, true, false, false);
										}/*else{
										btnState(true, false, false, false);
									}*/
									} catch (ArrayIndexOutOfBoundsException e) { }
								}
							}
						});
					}
					{
						rowHeaderSubProject = tblSubProject.getRowHeader();
						rowHeaderSubProject.setModel(new DefaultListModel());
						scrollSubProject.setRowHeaderView(rowHeaderSubProject);
						scrollSubProject.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 4, 5, 5));
				pnlSouth.setBorder(EMPTY_BORDER);
				{
					btnNew = new JButton("New");
					pnlSouth.add(btnNew);
					btnNew.setActionCommand(btnNew.getText());
					btnNew.setMnemonic(KeyEvent.VK_N);
					btnNew.addActionListener(this);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouth.add(btnEdit);
					btnEdit.setActionCommand(btnEdit.getText());
					btnEdit.setMnemonic(KeyEvent.VK_E);
					btnEdit.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand(btnSave.getText());
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand(btnCancel.getText());
					btnCancel.addActionListener(this);
				}
			}
		}
		//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, false);
		btnState(true, false, false, false);
		displaySubProject();
	}//END OF INIT GUI

	private void btnState(Boolean sNew,Boolean sEdit ,Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void displaySubProject(){
		try{
			modelSubProj.clear();
		}catch (ArrayIndexOutOfBoundsException e) {}

		pgSelect db = new pgSelect();
		String sql = "select \n" + 
				"trim(a.sub_proj_id) as \"Sub Proj. ID\", " +
				"trim(a.sub_proj_name) as \"Sub Proj. Name\",\n" + 
				"trim(a.sub_proj_alias) as \"Sub Proj. Alias\"," +
				"trim(a.phase) as \"Phase\",\n" + 
				"trim(b.co_id) as \"Co. ID\", " +
				"trim(c.company_name) as \"Company Name\",\n" + 
				"trim(a.proj_id) as \"Proj. ID\", " +
				"trim(b.proj_name) as \"Proj. Name\",\n" + 
				"trim(a.release_batch) as \"Release Batch\", " +
				"trim(a.pagibig_code) as \"Pagibig Code\", \n"+
				"a.with_change_model as \"Change Model\"," +
				"a.lts_date as \"LTS Date\",  \n" +	
				"a.boi as \"BOI Date\",  \n" +	
				"a.boi_registration_no as \"BOI Reg. No.\"\n" +	
				"from mf_sub_project a\n" + 
				"left join mf_project b on b.proj_id = a.proj_id\n" + 
				"left join mf_company c on c.co_id = b.co_id \n";
		db.select(sql);
		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				modelSubProj.addRow(db.getResult()[x]);
			}
			scrollSubProject.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSubProject.getRowCount())));
			tblSubProject.packAll();
		}
	}


	private String sqlCompany(){
		return "select TRIM(co_id) as \"ID\", \n" + 
				"TRIM(company_name) as \"Company Name\", \n" + 
				"TRIM(company_alias) as \"Company Alias\" \n" + 
				"from mf_company \n" + 
				"order by company_name";
	}

	private String sqlProject(String co_id){
		return "select TRIM(proj_id) as \"ID\",\n" + 
				"TRIM(proj_name) as \"Project Name\",\n" + 
				"TRIM(proj_alias) as \"Alias\" \n" + 
				"from mf_project \n" + 
				"where co_id = '"+co_id+"';";
	}

	private boolean isSubProjectExisting(String sub_proj_id){
		pgSelect db = new pgSelect();
		String sql = "select * from mf_sub_project where sub_proj_id = '"+sub_proj_id+"'";
		db.select(sql);
		return db.isNotNull();
	}

	/*private boolean isSubProjectNameExisting(String sub_proj_name){  //this is not applicable as two projects may have the same name - del gonzales
		pgSelect db = new pgSelect();
		String sql = "select * from mf_sub_project where sub_proj_name = '"+sub_proj_name+"'";
		db.select(sql);
		return db.isNotNull();
	}*/

	private boolean toSave(){
		/*if(lookupCompany.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Company "), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		if(lookupProject.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Project"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtSubProjAlias.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Sub Proj. Alias"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtSubProjPhase.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Sub Proj. Phase"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtSubProjName.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Sub Proj. Name"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		/*if(txtReleaseBatch.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Release Batch"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtPagibigCode.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Pag-ibig Code"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		
		return true;
	}

	private void newSubProject(){
		cancelSubProject();
		pgSelect db = new pgSelect();
		String sql = "SELECT to_char(COALESCE(MAX(sub_proj_id::INT), 0) + 1, 'FM000') FROM mf_sub_project WHERE sub_proj_id != '';";
		db.select(sql);
		txtSubProjID.setText((String) db.getResult()[0][0]);

		//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, true);
		txtSubProjID.setEditable(false);
		btnState(false, false, true, true);
		txtCompany.setEditable(false);
		txtProject.setEditable(false);
		chkChangeModel.setEnabled(true);
		txtBOI_regist.setEnabled(true);
		dteLTS.setEnabled(true);
		dteBOI.setEnabled(true);
		
		lookupCompany.setEnabled(true);
		lookupProject.setEnabled(true);

		tblSubProject.clearSelection();
		tblSubProject.setEnabled(false);
		rowHeaderSubProject.setEnabled(false);
		
		txtSubProjAlias.setEditable(true);
		txtSubProjName.setEditable(true);
		txtSubProjPhase.setEditable(true);
		txtReleaseBatch.setEditable(true);
		txtPagibigCode.setEditable(true);
		txtBOI_regist.setEditable(true);
		
		lookupCompany.setEnabled(true);
		lookupProject.setEnabled(true);
	}

	private void editSubProject(){
		if(tblSubProject.getSelectedRows().length ==1){
			//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, true);
			txtSubProjID.setEditable(false);
			txtCompany.setEditable(false);
			txtProject.setEditable(false);
			btnState(false, false, true, true);
			chkChangeModel.setEnabled(true);
			dteLTS.setEnabled(true);
			dteBOI.setEnabled(true);
			tblSubProject.setEnabled(false);
			txtBOI_regist.setEnabled(true);
			rowHeaderSubProject.setEnabled(false);
			
			txtSubProjAlias.setEditable(true);
			txtSubProjName.setEditable(true);
			txtSubProjPhase.setEditable(true);
			txtReleaseBatch.setEditable(true);
			txtPagibigCode.setEditable(true);
			txtBOI_regist.setEditable(true);
			
			lookupCompany.setEnabled(true);
			lookupProject.setEnabled(true);
		}else{
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please Select one row to edit from the table");
			tblSubProject.clearSelection();
		}
	}
	
	private void disableSubProject(){
		
			txtSubProjID.setEditable(false);
			txtCompany.setEditable(false);
			txtProject.setEditable(false);
			btnState(false, false, true, true);
			chkChangeModel.setEnabled(false);
			dteLTS.setEnabled(false);
			dteBOI.setEnabled(false);
			tblSubProject.setEnabled(false);
			txtBOI_regist.setEnabled(false);
			rowHeaderSubProject.setEnabled(false);
			
			txtSubProjAlias.setEditable(false);
			txtSubProjName.setEditable(false);
			txtSubProjPhase.setEditable(false);
			txtReleaseBatch.setEditable(false);
			txtPagibigCode.setEditable(false);
			txtBOI_regist.setEditable(false);
			
			lookupCompany.setEnabled(false);
			lookupProject.setEnabled(false);
	}

	private void addSubProject(){
		
		String proj_id = lookupProject.getValue();
		String sub_proj_id = txtSubProjID.getText();
		String sub_proj_name = txtSubProjName.getText().toUpperCase();
		String sub_proj_alias = txtSubProjAlias.getText().toUpperCase();
		String phase = txtSubProjPhase.getText();
		String release_batch = txtReleaseBatch.getText();
		String pagibig_code = txtPagibigCode.getText();
		Boolean with_change_model = chkChangeModel.isSelected();
		Date lts_date = dteLTS.getDate();
		Date boi_date = dteBOI.getDate();
		String boi_reg_no = txtBOI_regist.getText();

		pgUpdate db = new pgUpdate();
		String sql = "INSERT INTO mf_sub_project(proj_id, sub_proj_id, sub_proj_name, sub_proj_alias, phase, release_batch, \n" + 
				"with_change_model, pagibig_code, status_id, created_by, date_created, edited_by, date_edited, lts_date, boi, boi_registration_no)\n" + 
				"VALUES ('"+proj_id+"', '"+sub_proj_id+"', '"+sub_proj_name+"', '"+sub_proj_alias+"', '"+phase+"', '"+release_batch+"', \n" + 
				""+with_change_model+", '"+pagibig_code+"', 'A', \n"+
				"'"+UserInfo.EmployeeCode+"', now(), null, null, nullif('"+lts_date+"', 'null')::TIMESTAMP," +
				" nullif('"+boi_date+"', 'null')::TIMESTAMP, '"+boi_reg_no+"' );";
		db.executeUpdate(sql, true);
		db.commit();
		displaySubProject();
		disableSubProject();
	}

	private void updateSubProject(){
		
		String proj_id = lookupProject.getValue();
		String sub_proj_id = txtSubProjID.getText();
		String sub_proj_name = txtSubProjName.getText().toUpperCase();
		String sub_proj_alias = txtSubProjAlias.getText().toUpperCase();
		String phase = txtSubProjPhase.getText();
		String release_batch = txtReleaseBatch.getText();
		String pagibig_code = txtPagibigCode.getText();
		Boolean with_change_model = chkChangeModel.isSelected();
		Date lts_date = dteLTS.getDate();
		Date boi_date = dteBOI.getDate();
		String boi_reg_no = txtBOI_regist.getText();

		pgUpdate db = new pgUpdate();
		String sql = "UPDATE mf_sub_project\n" + 
				"SET proj_id='"+proj_id+"', sub_proj_name='"+sub_proj_name+"', sub_proj_alias='"+sub_proj_alias+"', \n" + 
				"phase='"+phase+"', release_batch='"+release_batch+"', with_change_model="+with_change_model+", pagibig_code='"+pagibig_code+"', \n" + 
				"edited_by='"+UserInfo.EmployeeCode+"', date_edited=now(), lts_date= nullif('"+lts_date+"', 'null')::TIMESTAMP, \n" +
				"boi = nullif('"+boi_date+"', 'null')::TIMESTAMP, boi_registration_no = '"+boi_reg_no+"'  \n" + 
				"WHERE sub_proj_id = '"+sub_proj_id+"'";
		db.executeUpdate(sql, true);
		db.commit();
		displaySubProject();
	}

	private void cancelSubProject(){
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		txtSubProjID.setText("");
		txtSubProjAlias.setText("");
		txtSubProjPhase.setText("");
		txtSubProjName.setText("");
		txtReleaseBatch.setText("");
		chkChangeModel.setSelected(false);
		txtPagibigCode.setText("");
		dteLTS.setDate(null);
		chkChangeModel.setEnabled(false);
		dteLTS.setEnabled(false);
		//XXX EDIT THIS this.setComponentsEditable(pnlNorthEast, false);
		btnState(true, false, false, false);
		tblSubProject.clearSelection();
		tblSubProject.setEnabled(true);
		rowHeaderSubProject.setEnabled(true);
		
		txtSubProjAlias.setEditable(false);
		txtSubProjName.setEditable(false);
		txtSubProjPhase.setEditable(false);
		txtReleaseBatch.setEditable(false);
		txtPagibigCode.setEditable(false);
		
		lookupCompany.setEnabled(false);
		lookupProject.setEnabled(false);
		
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if(actionCommand.equals("New")){
			newSubProject();
		}
		if(actionCommand.equals("Edit")){
			editSubProject();
		}
		if(actionCommand.equals("Save")){
			if(toSave()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to save New Sub Project?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					if(isSubProjectExisting(txtSubProjID.getText())){
						updateSubProject();
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Sub Project Info has been updated...");
						cancelSubProject();
					}else{
						
						/*if(isSubProjectNameExisting(txtSubProjName.getText().toUpperCase())){
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Sub Project is already existing please input another name");
							txtSubProjName.setText("");
						}else{*/
							addSubProject();
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Sub Project Info has been saved...");
							cancelSubProject();
						//}
					}
				}
			}
		}
		if(actionCommand.equals("Cancel")){
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Cancel editing?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				cancelSubProject();
			}
		}
	}
}
