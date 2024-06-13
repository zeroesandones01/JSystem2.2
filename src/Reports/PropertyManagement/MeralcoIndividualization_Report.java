package Reports.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.poifs.storage.ListManagedBlock;
import org.jdesktop.swingx.JXPanel;

import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
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
import tablemodel.modelBFST_LOGReleased;
import tablemodel.modelMeralcoIndividualEnergization;

/**
 * @author John Lester Fatallo
 */
public class MeralcoIndividualization_Report extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = -651155433576794347L;

	Dimension size = new Dimension(1000, 600);
	static String title = "Meralco Invididualization Report";

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

	private _JLookup lookupClient;
	private _JXTextField txtFirstName;
	private _JXTextField txtLastName;
	private _JXTextField txtMiddleName;
	private _JLookup lookupSalesGroup;
	private _JXTextField txtSalesGroup;
	private _JDateChooser dteEvaluation;

	private JButton btnGenerate;
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnPreview;
	private JButton btnCancel;

	private JPanel pnlCenter;

	private _JTableMain tblMeralcoIndividual;
	private JScrollPane scrollMeralcoIndividual;
	private JList rowHeaderMeralcoIndividual;
	private modelMeralcoIndividualEnergization modelMeralco;
	private _JDateChooser dteEffectivity;
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

	public MeralcoIndividualization_Report() {
		super(title, true, true, false, false);
		initGUI();
	}

	public MeralcoIndividualization_Report(String title) {
		super(title);
		initGUI();
	}

	public MeralcoIndividualization_Report(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, false, true, true, true);
		initGUI();
	}

	public void initGUI(){
		this.setTitle(title);
		this.setSize(size);
		this.setPreferredSize(size);
		{
			JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
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
							lblCompany = new JLabel("Company");
							pnlNCLabel.add(lblCompany);
						}
						{
							lblProject = new JLabel("Project");
							pnlNCLabel.add(lblProject);
						}
						{
							lblPhase = new JLabel("Phase");
							pnlNCLabel.add(lblPhase);
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
								//lookupCompany.setEditable(false);
								lookupCompany.setValue("02");
								lookupCompany.setLookupSQL(sqlCompany());
								lookupCompany.setFilterName(true);
								lookupCompany.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null) {
											String co_id = (String) data[0];
											String company_name = (String) data[1];
											lookupProject.setLookupSQL(sqlProject(co_id));
											txtCompany.setText(company_name);
											lookupProject.setLookupSQL(sqlProject(co_id));
										}
									}
								});
							}
							{
								txtCompany = new _JXTextField();
								pnlCompany.add(txtCompany, BorderLayout.CENTER);
								txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
							}
						}
						{
							JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlProject);
							{
								lookupProject = new _JLookup(null, "Project", 0, "Please select company");
								pnlProject.add(lookupProject, BorderLayout.WEST);
								lookupProject.setPreferredSize(new Dimension(100, 0));
								//lookupProject.setEditable(false);
								lookupProject.setFilterName(true);
								lookupProject.setValue("015");
								//lookupProject.setLookupSQL(_PreDocsEvaluation.sqlProject());
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
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
							}
							{
								txtProject = new _JXTextField();
								pnlProject.add(txtProject, BorderLayout.CENTER);
								txtProject.setText("TERRAVERDE RESIDENCES");
							}
						}
						{
							JPanel pnlPhase = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlPhase);
							{
								lookupPhase = new _JLookup(null, "Phase", 0, "Please select project");
								pnlPhase.add(lookupPhase, BorderLayout.WEST);
								lookupPhase.setPreferredSize(new Dimension(100, 0));
								//lookupPhase.setEditable(false);
								lookupPhase.setLookupSQL(sqlPhase(lookupProject.getValue()));
								lookupPhase.setFilterName(true);
								lookupPhase.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											String description = (String) data[1];
											txtPhase.setText(description);
										}
									}
								});
							}
							{
								txtPhase = new _JXTextField();
								pnlPhase.add(txtPhase, BorderLayout.CENTER);

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
						btnGenerate.setActionCommand(btnGenerate.getText());
						btnGenerate.addActionListener(this);
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					dteEffectivity = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCenter.add(dteEffectivity);
					dteEffectivity.setDate(null);
					((JTextFieldDateEditor)dteEffectivity.getDateEditor()).setEditable(false);
					dteEffectivity.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					((JTextFieldDateEditor)dteEffectivity.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
						public void insertUpdate(DocumentEvent evt) {
							SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

							if (modelMeralco.getValueAt(tblMeralcoIndividual.getSelectedRow(),0).equals(true)) {
								if(tblMeralcoIndividual.getSelectedColumn() == 3) {
									modelMeralco.setValueAt(""+sdf.format(dteEffectivity.getDate()),tblMeralcoIndividual.getSelectedRow(),3);
								}
								if(tblMeralcoIndividual.getSelectedColumn() == 4) {
									modelMeralco.setValueAt(""+sdf.format(dteEffectivity.getDate()),tblMeralcoIndividual.getSelectedRow(),4);
								}
								if(tblMeralcoIndividual.getSelectedColumn() == 5) {
									modelMeralco.setValueAt(""+sdf.format(dteEffectivity.getDate()),tblMeralcoIndividual.getSelectedRow(),5);
								}
								if(tblMeralcoIndividual.getSelectedColumn() == 7) {
									modelMeralco.setValueAt(""+sdf.format(dteEffectivity.getDate()),tblMeralcoIndividual.getSelectedRow(), 7);
								}
								if(tblMeralcoIndividual.getSelectedColumn() == 10) {
									modelMeralco.setValueAt(""+sdf.format(dteEffectivity.getDate()),tblMeralcoIndividual.getSelectedRow(), 10);
								}
								if(tblMeralcoIndividual.getSelectedColumn() == 11) {
									modelMeralco.setValueAt(""+sdf.format(dteEffectivity.getDate()),tblMeralcoIndividual.getSelectedRow(), 11);
								}
								if(tblMeralcoIndividual.getSelectedColumn() == 12) {
									modelMeralco.setValueAt(""+sdf.format(dteEffectivity.getDate()),tblMeralcoIndividual.getSelectedRow(), 12);
								}
								
								tblMeralcoIndividual.packAll();
							}
						}
						public void changedUpdate(DocumentEvent e) {}
						public void removeUpdate(DocumentEvent e) {}
					});
				}
				{
					scrollMeralcoIndividual = new JScrollPane();
					pnlCenter.add(scrollMeralcoIndividual, BorderLayout.CENTER);
					scrollMeralcoIndividual.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelMeralco = new modelMeralcoIndividualEnergization();
						tblMeralcoIndividual = new _JTableMain(modelMeralco);
						scrollMeralcoIndividual.setViewportView(tblMeralcoIndividual);
						tblMeralcoIndividual.setSortable(false);
						tblMeralcoIndividual.addMouseListener(this);
						tblMeralcoIndividual.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblMeralcoIndividual.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
						tblMeralcoIndividual.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(sdf));
						tblMeralcoIndividual.getColumnModel().getColumn(4).setCellRenderer(new DateRenderer(sdf));
						tblMeralcoIndividual.getColumnModel().getColumn(5).setCellRenderer(new DateRenderer(sdf));
						tblMeralcoIndividual.getColumnModel().getColumn(8).setCellRenderer(new DateRenderer(sdf));
						tblMeralcoIndividual.getColumnModel().getColumn(9).setCellRenderer(new DateRenderer(sdf));
						tblMeralcoIndividual.getColumnModel().getColumn(10).setCellRenderer(new DateRenderer(sdf));
						tblMeralcoIndividual.getColumnModel().getColumn(11).setCellRenderer(new DateRenderer(sdf));
						tblMeralcoIndividual.getColumnModel().getColumn(12).setCellRenderer(new DateRenderer(sdf));
						tblMeralcoIndividual.getColumnModel().getColumn(13).setCellRenderer(new DateRenderer(sdf));
						/*tblMeralcoIndividual.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

							@Override
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){
									btnState(true, true, false, false);
								}
							}
						});*/
					}
					{
						rowHeaderMeralcoIndividual = tblMeralcoIndividual.getRowHeader();
						rowHeaderMeralcoIndividual.setModel(new DefaultListModel());
						scrollMeralcoIndividual.setRowHeaderView(rowHeaderMeralcoIndividual);
						scrollMeralcoIndividual.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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
					}			
					{
						btnSave = new JButton("Save");
						pnlCenterBottom.add(btnSave);
						btnSave.setActionCommand(btnSave.getText());
						btnSave.addActionListener(this);
					}
					{
						btnPreview = new JButton("Preview");
						pnlCenterBottom.add(btnPreview);
						btnPreview.setActionCommand(btnPreview.getText());
						btnPreview.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlCenterBottom.add(btnCancel);
						btnCancel.setActionCommand(btnCancel.getText());
						btnCancel.addActionListener(this);
					}
				}
			}
		}
		//_PreDocsEvaluation.displayDocsEval(modelDocsEval, rowHeaderDocsEval);
		tblMeralcoIndividual.packAll();
		btnState(false, false, false);
	}//XXX END OF INIT GUI

	/*private void btnState(Boolean sGenerate, Boolean sPost, Boolean sPreview, Boolean sExport){
		btnPost.setEnabled(sPost);
		btnInactive.setEnabled(sPreview);
		btnCancel.setEnabled(sExport);
	}*/

	private void btnState(Boolean sSave, Boolean sPreview ,Boolean sCancel){

		btnPreview.setEnabled(sPreview);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);

	}

	private void generateAccounts(String co_id, String proj_id, String phase, DefaultTableModel model, JList rowHeader) {
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_meralco_individualization_report(NULLIF('"+co_id+"', 'null'), NULLIF('"+proj_id+"', 'null'), NULLIF('"+phase+"', 'null'))";
			db.select(SQL);
			
			FncSystem.out("Display value of SQL", SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
	}
	
	public static void saveMeralcoIndividualization(modelMeralcoIndividualEnergization model){
		
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<String> listCSE_Cont = new ArrayList<String>();
		ArrayList<String> listCSE_NTP = new ArrayList<String>();
		ArrayList<String> listCSE_Int = new ArrayList<String>();
		ArrayList<String> listMBase_Issued = new ArrayList<String>();
		ArrayList<String> listMER_Ener = new ArrayList<String>();
		
		pgSelect db = new pgSelect();
		
		
		for(int x= 0; x<model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			
			if(isSelected){
				
				String cse_cont = String.valueOf(model.getValueAt(x, 3)); //(String) model.getValueAt(x, 3);
				String cse_ntp = String.valueOf(model.getValueAt(x, 4));
				String cse_int = String.valueOf(model.getValueAt(x, 5));
				String mbase_issued = String.valueOf(model.getValueAt(x, 10));
				String mer_ener = String.valueOf(model.getValueAt(x, 11));
				
				String entity_id = (String) model.getValueAt(x, 17);
				String proj_id = (String) model.getValueAt(x, 18);
				String pbl_id = (String) model.getValueAt(x, 19);
				Integer seq_no = (Integer) model.getValueAt(x, 20);
				
				String SQL = "SELECT sp_tag_meralco_individualization('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', '"+seq_no+"',\n" + 
						 "NULLIF('"+cse_cont+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, NULLIF('"+cse_ntp+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, NULLIF('"+cse_int+"', 'null')::TIMESTAMP WITHOUT TIME ZONE,\n" + 
						 "NULLIF('"+mbase_issued+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, NULLIF('"+mer_ener+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, '"+UserInfo.EmployeeCode+"')";
				
				db.select(SQL);
				FncSystem.out("Display SQL for Posting Meralco Individualization", SQL);
				
			}
		}
		
	}
	
	private void preview() {
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("proj_name", txtProject.getText());
		mapParameters.put("phase", lookupPhase.getValue());
		
		FncReport.generateReport("/Reports/rptMeralcoIndividualization_Report.jasper", "Meralco Individualization Report", mapParameters);
		
	}

	public boolean toSave(){
		return true;
	}

	private void cancelDocsEvaluation(){
		btnState(false, false, false);
	}

	private int getSelected_ClientsInactive(){
		int count = 0;
		try{
			for(int x= 0; x<tblMeralcoIndividual.getRowCount(); x++){
				if(tblMeralcoIndividual.getValueAt(x, 0).toString().equals("true")){
					count ++;
				}
			}
		}catch (ArrayIndexOutOfBoundsException e){}
		return count;
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
		return "SELECT a.phase as \"Phase\", FORMAT('%s %s', 'Phase', a.phase) as \"Description\", \n" + 
				"FORMAT('%s%s', b.proj_alias, a.phase)\n" + 
				"FROM mf_sub_project a\n" + 
				"left join mf_project b on b.proj_id = a.proj_id\n" + 
				"where a.proj_id = '"+proj_id+"'\n" + 
				"order by a.phase;";
	}
	

	public void actionPerformed(ActionEvent e){//XXX Action
		
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Generate")) {
			new Thread(new Runnable() {
				public void run() {
					FncGlobal.startProgress("Generating Accounts");
					generateAccounts(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), modelMeralco, rowHeaderMeralcoIndividual);
					tblMeralcoIndividual.packAll();
					scrollMeralcoIndividual.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblMeralcoIndividual.getRowCount())));
					FncGlobal.stopProgress();
					btnState(true, true, true);
				}
			}).start();
		}

		if(actionCommand.equals("Save")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					//if(_PreDocsEvaluation.saveDocsEval(lookupProject.getValue(), lookupClient.getValue() ,txtLastName.getText(), txtFirstName.getText(), txtMiddleName.getText(), lookupSalesGroup.getValue(), (Date) dteEvaluation.getDate())){
					//cancelDocsEvaluation();
					//_PreDocsEvaluation.displayDocsEval(modelDocsEval, rowHeaderDocsEval);
					saveMeralcoIndividualization(modelMeralco);
					scrollMeralcoIndividual.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblMeralcoIndividual.getRowCount())));
					tblMeralcoIndividual.packAll();
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Save", JOptionPane.INFORMATION_MESSAGE);
					//}
				}
			}
		}
		
		if(actionCommand.equals("Preview")) {
			preview();
		}

		if(actionCommand.equals("Cancel")){
			cancelDocsEvaluation();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(tblMeralcoIndividual)){
			int selected_row = tblMeralcoIndividual.convertRowIndexToModel(tblMeralcoIndividual.getSelectedRow());

			Boolean isSelected = (Boolean) modelMeralco.getValueAt(selected_row, 0);

			if(isSelected){
				//System.out.printf("Display value of selected column: %s%n", tblBF_StatusTagging.getSelectedColumn());
				if (tblMeralcoIndividual.getSelectedColumn() == 3) {
					if (e.getClickCount() == 2) {
						System.out.println("Dumaan dito sa Double Click");
						dteEffectivity.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
						dteEffectivity.getCalendarButton().doClick();
					}
				}
				if (tblMeralcoIndividual.getSelectedColumn() == 4) {
					if (e.getClickCount() == 2) {
						System.out.println("Dumaan dito sa Double Click");
						dteEffectivity.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
						dteEffectivity.getCalendarButton().doClick();
					}
				}
				if (tblMeralcoIndividual.getSelectedColumn() == 5) {
					if (e.getClickCount() == 2) {
						System.out.println("Dumaan dito sa Double Click");
						dteEffectivity.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
						dteEffectivity.getCalendarButton().doClick();
					}
				}
				if (tblMeralcoIndividual.getSelectedColumn() == 7) {
					if (e.getClickCount() == 2) {
						System.out.println("Dumaan dito sa Double Click");
						dteEffectivity.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
						dteEffectivity.getCalendarButton().doClick();
					}
				}
				if (tblMeralcoIndividual.getSelectedColumn() == 10) {
					if (e.getClickCount() == 2) {
						System.out.println("Dumaan dito sa Double Click");
						dteEffectivity.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
						dteEffectivity.getCalendarButton().doClick();
					}
				}
				if (tblMeralcoIndividual.getSelectedColumn() == 11) {
					if (e.getClickCount() == 2) {
						System.out.println("Dumaan dito sa Double Click");
						dteEffectivity.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
						dteEffectivity.getCalendarButton().doClick();
					}
				}
				if (tblMeralcoIndividual.getSelectedColumn() == 12) {
					if (e.getClickCount() == 2) {
						System.out.println("Dumaan dito sa Double Click");
						dteEffectivity.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
						dteEffectivity.getCalendarButton().doClick();
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
