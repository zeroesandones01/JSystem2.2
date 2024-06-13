package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import DateChooser._JDateChooser;
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
import tablemodel.modelDocsEvaluation;

/**
 * @author John Lester Fatallo
 */
public class PreDocsEvaluation extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = -651155433576794347L;
	
	Dimension size = new Dimension(800, 600);
	static String title = "Pre-Docs Evaluation";

	private JPanel pnlNorth;
	private JPanel pnlNorthCenter;
	private JPanel pnlNCLabel;
	private JLabel lblProject;
	private JLabel lblClientName;
	private JLabel lblSalesGroup;

	private JPanel pnlNCComponent;
	private _JLookup lookupProject;
	private _JXTextField txtProject;
	private _JLookup lookupClient;
	private _JXTextField txtFirstName;
	private _JXTextField txtLastName;
	private _JXTextField txtMiddleName;
	private _JLookup lookupSalesGroup;
	private _JXTextField txtSalesGroup;
	private _JDateChooser dteEvaluation;
	
	
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnInactive;
	private JButton btnCancel;
	
	private JPanel pnlCenter;
	
	private _JTableMain tblDocsEval;
	private JScrollPane scrollDocsEval;
	private JList rowHeaderDocsEval;
	private modelDocsEvaluation modelDocsEval;
	

	public PreDocsEvaluation() {
		super(title, true, true, false, false);
		initGUI();
	}

	public PreDocsEvaluation(String title) {
		super(title);
		initGUI();
	}

	public PreDocsEvaluation(String title, boolean resizable, boolean closable, boolean maximizable,
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
						pnlNCLabel = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlNorthCenter.add(pnlNCLabel, BorderLayout.WEST);
						{
							lblProject = new JLabel("Project");
							pnlNCLabel.add(lblProject);
						}
						{
							lblClientName = new JLabel("Client Name");
							pnlNCLabel.add(lblClientName);
						}
						{
							lblSalesGroup = new JLabel("Sales Group");
							pnlNCLabel.add(lblSalesGroup);
						}
						{
							JLabel lblDateEvaluated = new JLabel("Date Evaluated");
							pnlNCLabel.add(lblDateEvaluated);
						}
					}
					{
						pnlNCComponent = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlNorthCenter.add(pnlNCComponent, BorderLayout.CENTER);
						{
							JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlProject);
							{
								lookupProject = new _JLookup("Project");
								pnlProject.add(lookupProject, BorderLayout.WEST);
								lookupProject.setPreferredSize(new Dimension(100, 0));
								lookupProject.setValue("015");
								lookupProject.setEditable(false);
								lookupProject.setFilterName(true);
								lookupProject.setLookupSQL(_PreDocsEvaluation.sqlProject());
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											String proj_id = (String) data[0];
											String proj_name = (String) data[1];
											
											lookupProject.setValue(proj_id);
											txtProject.setText(proj_name);
											//lookupPhase.setLookupSQL(_BankFinancingMonitoring.sqlPhase(proj_id));
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
							JPanel pnlClient = new JPanel(new BorderLayout(3, 3));
							pnlNCComponent.add(pnlClient);
							{
								lookupClient = new _JLookup(null, "Client", 0);
								pnlClient.add(lookupClient, BorderLayout.WEST);
								lookupClient.setPreferredSize(new Dimension(100, 0));
								lookupClient.setEditable(false);
								lookupClient.setFilterName(true);
								lookupClient.setLookupSQL(_PreDocsEvaluation.sqlClients());
								lookupClient.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											FncSystem.out("Display SQL for Client", lookupClient.getLookupSQL());
											String entity_id = (String) data[0];
											String last_name = (String) data[4];
											String first_name = (String) data[5];
											String middle_name = (String) data[6];
											txtLastName.setText(last_name);
											txtFirstName.setText(first_name);
											txtMiddleName.setText(middle_name);
											
											txtLastName.setEditable(false);
											txtFirstName.setEditable(false);
											txtMiddleName.setEditable(false);
											
											if(isEmployee(entity_id)) {
												lookupSalesGroup.setValue("09");
												txtSalesGroup.setText("PROJECT DEVELOPMENT DEPARTMENT");
												lookupSalesGroup.setEditable(false);
											}else {
												lookupSalesGroup.setValue(null);
												txtSalesGroup.setText("");
												lookupSalesGroup.setEditable(true);
											}
										}
									}
								});
							}
							{
								JPanel pnlClientName = new JPanel(new GridLayout(1, 3, 3, 3));
								pnlClient.add(pnlClientName, BorderLayout.CENTER);
								{
									txtLastName = new _JXTextField("Last Name");
									pnlClientName.add(txtLastName);
								}
								{
									txtFirstName = new _JXTextField("First Name");
									pnlClientName.add(txtFirstName);
								}
								{
									txtMiddleName = new _JXTextField("Middle Name");
									pnlClientName.add(txtMiddleName);
								}
							}
						}
						{
							JPanel pnlSalesGroup = new JPanel(new BorderLayout(3, 3));
							pnlNCComponent.add(pnlSalesGroup);
							{
								lookupSalesGroup = new _JLookup(null, "Sales Group", 0);
								pnlSalesGroup.add(lookupSalesGroup, BorderLayout.WEST);
								lookupSalesGroup.setPreferredSize(new Dimension(100, 0));
								lookupSalesGroup.setEditable(false);
								lookupSalesGroup.setFilterName(true);
								lookupSalesGroup.setLookupSQL(_PreDocsEvaluation.sqlSalesGroup());
								lookupSalesGroup.addLookupListener(new LookupListener() {
									
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											//String sales_grp_id = (String) data[0];
											String sales_group = (String) data[1];
											
											txtSalesGroup.setText(sales_group);
											//lookupPhase.setLookupSQL(_BankFinancingMonitoring.sqlPhase(proj_id));
										}
									}
								});
							}
							{
								txtSalesGroup = new _JXTextField();
								pnlSalesGroup.add(txtSalesGroup, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnldteEval = new JPanel(new BorderLayout(3, 3));
							pnlNCComponent.add(pnldteEval);
							{
								dteEvaluation = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnldteEval.add(dteEvaluation, BorderLayout.WEST);
								dteEvaluation.setPreferredSize(new Dimension(150, 0));
								dteEvaluation.setEditable(false);
								dteEvaluation.getCalendarButton().setEnabled(false);
								dteEvaluation.setDate(Calendar.getInstance().getTime());
							}
						}
					}
				}
				
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				/*{

					dteEffectivity = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCenter.add(dteEffectivity);
					dteEffectivity.setDate(null);
					((JTextFieldDateEditor)dteEffectivity.getDateEditor()).setEditable(false);
					dteEffectivity.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					((JTextFieldDateEditor)dteEffectivity.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
						public void insertUpdate(DocumentEvent evt) {
							SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
							if (modelLOG_Released.getValueAt(tblBF_StatusTagging.getSelectedRow(),0).equals(true)) {
								modelLOG_Released.setValueAt(""+sdf.format(dteEffectivity.getDate()),tblBF_StatusTagging.getSelectedRow(),7);
							}
						}
						public void changedUpdate(DocumentEvent e) {}
						public void removeUpdate(DocumentEvent e) {}
					});
				}*/
				{
					scrollDocsEval = new JScrollPane();
					pnlCenter.add(scrollDocsEval, BorderLayout.CENTER);
					{

						modelDocsEval = new modelDocsEvaluation();
						
						tblDocsEval = new _JTableMain(modelDocsEval);
						scrollDocsEval.setViewportView(tblDocsEval);
						scrollDocsEval.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						tblDocsEval.setSortable(false);
						tblDocsEval.hideColumns("Rec. ID", "Proj. ID", "Entity ID");
						tblDocsEval.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblDocsEval.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							
							@Override
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){
									btnState(true, true, false, false);
								}
							}
						});
						
					}
					{
						rowHeaderDocsEval = tblDocsEval.getRowHeader();
						rowHeaderDocsEval.setModel(new DefaultListModel());
						scrollDocsEval.setRowHeaderView(rowHeaderDocsEval);
						scrollDocsEval.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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
						btnNew = new JButton("New");
						pnlCenterBottom.add(btnNew);
						btnNew.setActionCommand(btnNew.getText());
						btnNew.addActionListener(this);
					}
					{
						btnInactive = new JButton("Inactive");
						pnlCenterBottom.add(btnInactive);
						btnInactive.setActionCommand(btnInactive.getText());
						btnInactive.addActionListener(this);
					}
					{
						btnSave = new JButton("Save");
						pnlCenterBottom.add(btnSave);
						btnSave.setActionCommand(btnSave.getText());
						btnSave.addActionListener(this);
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
		_PreDocsEvaluation.displayDocsEval(modelDocsEval, rowHeaderDocsEval);
		tblDocsEval.packAll();
		btnState(true, false, false, false);
	}//XXX END OF INIT GUI
	
	/*private void btnState(Boolean sGenerate, Boolean sPost, Boolean sPreview, Boolean sExport){
		btnPost.setEnabled(sPost);
		btnInactive.setEnabled(sPreview);
		btnCancel.setEnabled(sExport);
	}*/
	
	private void btnState(Boolean sNew, Boolean sInactive, Boolean sSave, Boolean sCancel){
		
		btnNew.setEnabled(sNew);
		btnInactive.setEnabled(sInactive);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
		
	}
	
	private boolean isEmployee(String entity_id) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM em_employee WHERE entity_id = '"+entity_id+"'";
		db.select(SQL);
		
		if(db.isNotNull()) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean toSave(){
		
		
		
		return true;
	}
	
	private void newDocsEvaluation(){
		
		lookupProject.setEditable(true);
		lookupClient.setEditable(true);
		lookupClient.setValue(_PreDocsEvaluation.getNewEntityID());
		txtLastName.setEditable(true);
		txtMiddleName.setEditable(true);
		txtFirstName.setEditable(true);
		lookupSalesGroup.setEditable(true);
		dteEvaluation.getCalendarButton().setEnabled(true);
		dteEvaluation.setEditable(true);
		//lookupProject.setValue(null);
		//txtProject.setText("");
		txtLastName.setText("");
		txtMiddleName.setText("");
		txtFirstName.setText("");
		lookupSalesGroup.setValue(null);
		txtSalesGroup.setText("");
		
		tblDocsEval.clearSelection();
		tblDocsEval.setEnabled(false);
		btnState(false, false, true, true);
		
	}
	
	private void cancelDocsEvaluation(){
		
		lookupProject.setEditable(false);
		lookupClient.setEditable(false);
		txtLastName.setEditable(false);
		txtMiddleName.setEditable(false);
		txtFirstName.setEditable(false);
		dteEvaluation.getCalendarButton().setEnabled(false);
		dteEvaluation.setEditable(false);
		lookupSalesGroup.setEditable(false);
		
		lookupProject.setValue(null);
		lookupClient.setValue(null);
		txtProject.setText("");
		txtLastName.setText("");
		txtFirstName.setText("");
		txtMiddleName.setText("");
		lookupSalesGroup.setValue(null);
		txtSalesGroup.setText("");
		
		tblDocsEval.setEnabled(true);
		dteEvaluation.setDate(null);
		btnState(true, false, false, false);
		lookupProject.setValue("015");
		txtProject.setText("TERRAVERDE RESIDENCES");
		dteEvaluation.setDate(Calendar.getInstance().getTime());
		
	}
	
	private int getSelected_ClientsInactive(){
		int count = 0;
		
		try{
		for(int x= 0; x<tblDocsEval.getRowCount(); x++){
			if(tblDocsEval.getValueAt(x, 0).toString().equals("true")){
				count ++;
			}
		}
		}catch (ArrayIndexOutOfBoundsException e){}
		return count;
	}

	public void actionPerformed(ActionEvent e){//XXX Action
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("New")){
			newDocsEvaluation();
		}
		
		if(actionCommand.equals("Inactive")){
			if(getSelected_ClientsInactive() != 0){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to inactive seletec accounts?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					_PreDocsEvaluation.inactiveAccounts(modelDocsEval);
					cancelDocsEvaluation();
					_PreDocsEvaluation.displayDocsEval(modelDocsEval, rowHeaderDocsEval);
					tblDocsEval.packAll();
					btnState(true, false, false, false);
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Inactive complete", "Save", JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(PreDocsEvaluation.this.getTopLevelAncestor(), "Please select accounts to inactive", actionCommand, JOptionPane.WARNING_MESSAGE);
			}
		}

		if(actionCommand.equals("Save")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					if(_PreDocsEvaluation.saveDocsEval(lookupProject.getValue(), lookupClient.getValue() ,txtLastName.getText(), txtFirstName.getText(), txtMiddleName.getText(), lookupSalesGroup.getValue(), (Date) dteEvaluation.getDate())){
						cancelDocsEvaluation();
						_PreDocsEvaluation.displayDocsEval(modelDocsEval, rowHeaderDocsEval);
						scrollDocsEval.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocsEval.getRowCount())));
						tblDocsEval.packAll();
						btnState(true, false,false, false);
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Save", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
		
		if(actionCommand.equals("Cancel")){
			cancelDocsEvaluation();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
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
