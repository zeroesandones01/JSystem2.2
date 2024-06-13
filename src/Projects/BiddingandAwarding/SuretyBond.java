package Projects.BiddingandAwarding;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXFormattedTextField;

import tablemodel.modelNTPSuretyBond;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;

public class SuretyBond extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8600135017373881213L;
	
	private JPanel pnlMenu;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnCancel;
	
	private JPanel pnlNorth;
	private JPanel pnlNorthNorth;
	
	private JLabel lblBondNo;
	private JTextField txtBondNo;
	
	private JLabel lblAmount;
	private _JXFormattedTextField txtAmount;
	
	private JLabel lblFrom;
	private _JDateChooser dateFrom;
	
	private JLabel lblTo;
	private _JDateChooser dateTo;

	private JPanel pnlNorthWest;
	private JLabel lblInsuranceCo;
	private _JLookup lookupInsuranceCo;
	private JLabel lblDepartmentConcerned;
	private _JLookup lookupDepartmentConcerned;
	private JLabel lblPresentLocation;
	private _JLookup lookupPresentLocation;

	private JPanel pnlNorthCenter;
	private JTextField txtInsuranceCo;
	private JTextField txtDepartmentConcerned;
	private JTextField txtPresentLocation;
	
	private JPanel pnlCenter;
	
	private JScrollPane scrollCenter;
	private _JTableMain tblSuretyBondMain;
	private modelNTPSuretyBond modelSuretyBondMain;
	private JList rowHeaderSuretyBondMain;
	
	private String contract_no = null;
	//private String server = null;
	//private String dbase = null;
	
	private ButtonGroup grpButton = new ButtonGroup();
	private KeyboardFocusManager KEYBOARD_MANAGER = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	public SuretyBond() {
		initThis();
	}

	public SuretyBond(LayoutManager layout) {
		super(layout);
		initThis();
	}

	public SuretyBond(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initThis();
	}

	public SuretyBond(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initThis();
	}
	
	private void initThis() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(800, 250));
		{
			pnlCenter = new JPanel();
			
			JScrollPane scr = new JScrollPane(pnlCenter); 
			this.add(scr, BorderLayout.CENTER);
			
			pnlCenter.setLayout(new BorderLayout(5, 5));
			{
				pnlNorth = new JPanel();
				pnlCenter.add(pnlNorth, BorderLayout.NORTH);
//				pnlCenter.add(pnlNorth);
				pnlNorth.setLayout(new BorderLayout(3, 3));
				pnlNorth.setPreferredSize(new Dimension(416, 122));
				{
					pnlNorthNorth = new JPanel();
					pnlNorth.add(pnlNorthNorth, BorderLayout.NORTH);
					pnlNorthNorth.setLayout(null);
					pnlNorthNorth.setPreferredSize(new Dimension(416, 47));
					{
						lblBondNo = new JLabel("Bond No.");
						pnlNorthNorth.add(lblBondNo);
						lblBondNo.setBounds(0, 0, 60, 22);
					}
					{
						txtBondNo = new JTextField();
						pnlNorthNorth.add(txtBondNo);
						txtBondNo.setBounds(60, 0, 250, 22);
					}
					{
						lblAmount = new JLabel("Amount");
						pnlNorthNorth.add(lblAmount);
						lblAmount.setBounds(0, 25, 56, 22);
					}
					{
						txtAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
						pnlNorthNorth.add(txtAmount);
						txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtAmount.setBounds(60, 25, 150, 22);
						txtAmount.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								KEYBOARD_MANAGER.focusNextComponent();
							}
						});
					}
					{
						lblFrom = new JLabel("From");
						pnlNorthNorth.add(lblFrom);
						lblFrom.setBounds(350, 0, 60, 22);
					}
					{
						dateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnlNorthNorth.add(dateFrom);
						dateFrom.setDate(Calendar.getInstance().getTime());
						dateFrom.setBounds(410, 0, 116, 22);
					}
					{
						lblTo = new JLabel("To");
						pnlNorthNorth.add(lblTo);
						lblTo.setBounds(350, 25, 60, 22);
					}
					{
						dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnlNorthNorth.add(dateTo);
						dateTo.setDate(Calendar.getInstance().getTime());
						dateTo.setBounds(410, 25, 116, 22);
					}
				}
				{
					pnlNorthWest = new JPanel();
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setLayout(null);
					pnlNorthWest.setPreferredSize(new Dimension(210, 100));
					{
						lblInsuranceCo = new JLabel("Insurance Co.");
						pnlNorthWest.add(lblInsuranceCo);
						lblInsuranceCo.setBounds(0, 0, 110, 22);
					}
					{
						lookupInsuranceCo = new _JLookup(null, "Insurance Company");
						pnlNorthWest.add(lookupInsuranceCo);
						lookupInsuranceCo.setReturnColumn(0);
						lookupInsuranceCo.setLookupSQL(_SuretyBond.InsuranceCompany2());
						lookupInsuranceCo.setBounds(110, 0, 100, 22);
						lookupInsuranceCo.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtInsuranceCo.setText((String) data[1]);

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblDepartmentConcerned = new JLabel("Dept. Concerned");
						pnlNorthWest.add(lblDepartmentConcerned);
						lblDepartmentConcerned.setBounds(0, 25, 110, 22);
					}
					{
						lookupDepartmentConcerned = new _JLookup(null, "Department Concerned");
						pnlNorthWest.add(lookupDepartmentConcerned);
						lookupDepartmentConcerned.setReturnColumn(0);
						lookupDepartmentConcerned.setLookupSQL(_SuretyBond.DepartmentConcerned());
						lookupDepartmentConcerned.setBounds(110, 25, 100, 22);
						lookupDepartmentConcerned.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtDepartmentConcerned.setText((String) data[1]);

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblPresentLocation = new JLabel("Present Location");
						pnlNorthWest.add(lblPresentLocation);
						lblPresentLocation.setBounds(0, 50, 110, 22);
					}
					{
						lookupPresentLocation = new _JLookup(null, "Present Location");
						pnlNorthWest.add(lookupPresentLocation);
						lookupPresentLocation.setReturnColumn(0);
						lookupPresentLocation.setLookupSQL(_SuretyBond.PresentLocation());
						lookupPresentLocation.setBounds(110, 50, 100, 22);
						lookupPresentLocation.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtPresentLocation.setText((String) data[1]);

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
				}
				{
					pnlNorthCenter = new JPanel();
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					pnlNorthCenter.setLayout(new GridLayout(3, 1, 3, 3));
					{
						txtInsuranceCo = new JTextField();
						pnlNorthCenter.add(txtInsuranceCo);
					}
					{
						txtDepartmentConcerned = new JTextField();
						pnlNorthCenter.add(txtDepartmentConcerned);
					}
					{
						txtPresentLocation = new JTextField();
						pnlNorthCenter.add(txtPresentLocation);
					}
				}
			}
			{
				scrollCenter = new JScrollPane();
				pnlCenter.add(scrollCenter, BorderLayout.CENTER);
//				pnlCenter.add(scrollCenter);
				{
					modelSuretyBondMain = new modelNTPSuretyBond();
					
					tblSuretyBondMain = new _JTableMain(modelSuretyBondMain);
					scrollCenter.setViewportView(tblSuretyBondMain);
					tblSuretyBondMain.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblSuretyBondMain.hideColumns("Rec ID","Insurance Co. ID", "Dept. Concerned ID", "Present Location ID");
					
					tblSuretyBondMain.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent arg0) {
							try {
								if(!arg0.getValueIsAdjusting()){
									int row = tblSuretyBondMain.convertRowIndexToModel(tblSuretyBondMain.getSelectedRow());
									
									String bond_no = (String) modelSuretyBondMain.getValueAt(row, 0);
									String insurance_co_id = (String) modelSuretyBondMain.getValueAt(row, 1);
									String insurance_co_name = (String) modelSuretyBondMain.getValueAt(row, 2);
									Date date_from = (Date) modelSuretyBondMain.getValueAt(row, 3);
									Date date_to = (Date) modelSuretyBondMain.getValueAt(row, 4);
									BigDecimal amount = (BigDecimal) modelSuretyBondMain.getValueAt(row, 5);
									String department_id = (String) modelSuretyBondMain.getValueAt(row, 6);
									String department_name = (String) modelSuretyBondMain.getValueAt(row, 7);
									String location_id = (String) modelSuretyBondMain.getValueAt(row, 8);
									String location_name = (String) modelSuretyBondMain.getValueAt(row, 9);
									
									btnState(true, true, true, false, false);

									txtBondNo.setText(bond_no);
									lookupInsuranceCo.setValue(insurance_co_id);
									txtInsuranceCo.setText(insurance_co_name);
									dateFrom.setDate(date_from);
									dateTo.setDate(date_to);
									txtAmount.setValue(amount);
									lookupDepartmentConcerned.setValue(department_id);
									txtDepartmentConcerned.setText(department_name);
									lookupPresentLocation.setValue(location_id);
									txtPresentLocation.setText(location_name);
								}
							} catch (ArrayIndexOutOfBoundsException e) { }
						}
					});
					tblSuretyBondMain.setSortable(false);
				}
				{
					rowHeaderSuretyBondMain = tblSuretyBondMain.getRowHeader();
					rowHeaderSuretyBondMain.setModel(new DefaultListModel());
					scrollCenter.setRowHeaderView(rowHeaderSuretyBondMain);
					scrollCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}
		{
			pnlMenu = new JPanel();
			this.add(pnlMenu, BorderLayout.WEST);
			pnlMenu.setLayout(new GridLayout(5, 1, 5, 5));
			pnlMenu.setBorder(components.JTBorderFactory.createTitleBorder("Menu"));
			pnlMenu.setPreferredSize(new Dimension(74, 240));
			{
				btnAdd = new JButton("Add");
				pnlMenu.add(btnAdd);
				btnAdd.setActionCommand(btnAdd.getText());
				btnAdd.addActionListener(this);
				grpButton.add(btnAdd);
			}
			{
				btnEdit = new JButton("Edit");
				pnlMenu.add(btnEdit);
				btnEdit.setActionCommand(btnEdit.getText());
				btnEdit.addActionListener(this);
				grpButton.add(btnEdit);
			}
			{
				btnDelete = new JButton("Delete");
				pnlMenu.add(btnDelete);
				btnDelete.setActionCommand(btnDelete.getText());
				btnDelete.addActionListener(this);
			}
			{
				btnSave = new JButton("Save");
				pnlMenu.add(btnSave);
				btnSave.setActionCommand(btnSave.getText());
				btnSave.addActionListener(this);
			}
			{
				btnCancel = new JButton("Cancel");
				pnlMenu.add(btnCancel);
				btnCancel.setActionCommand(btnCancel.getText());
				btnCancel.addActionListener(this);
			}
		}
		
		setComponentsEnabled(false) ;
		btnState(false, false, false, false, false);
		
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(txtBondNo, txtAmount, lookupInsuranceCo, lookupDepartmentConcerned, lookupPresentLocation, (JTextField) dateFrom.getDateEditor(), (JTextField) dateTo.getDateEditor()));
	}
	
	public void btnState(Boolean sAdd, Boolean sEdit, Boolean sDelete, Boolean sSave, Boolean sCancel) {
		btnAdd.setEnabled(sAdd);
		btnEdit.setEnabled(sEdit);
		btnDelete.setEnabled(sDelete);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}
	
	public void refreshSuretyBond(boolean isNewSuretyBond) {
		txtBondNo.setText("");
		txtAmount.setValue(null);
		dateFrom.setDate(Calendar.getInstance().getTime());
		dateTo.setDate(Calendar.getInstance().getTime());
		lookupInsuranceCo.setValue(null);
		txtInsuranceCo.setText("");
		lookupDepartmentConcerned.setValue(null);
		txtDepartmentConcerned.setText("");
		lookupPresentLocation.setValue(null);
		txtPresentLocation.setText("");
		
		if(isNewSuretyBond){
			((DefaultListModel)rowHeaderSuretyBondMain.getModel()).removeAllElements();
			modelSuretyBondMain.clear();
			scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSuretyBondMain.getRowCount())));
			tblSuretyBondMain.packAll();
		}
	}
	
	public void setComponentsEnabled(boolean enable) {
		panelLooper(pnlNorth, enable);
	}

	public void panelLooper(Container panel, boolean enable) {
		for(Component comp : panel.getComponents()){
			if(comp instanceof JPanel){
				panelLooper((JPanel) comp, enable);
			}else if(comp instanceof JScrollPane){
				if(comp.getName().equals("scrollPhase")){
					panelLooper((JScrollPane) comp, enable);
				}
			}else{
				if(comp.getName() != null){
					comp.setEnabled(!enable);
				}else{
					if(panel instanceof JScrollPane){
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					}else{
						comp.setEnabled(enable);
					}
				}
			}
		}
	}
	public void newSuretyBond() {
		refreshSuretyBond(false);
		setComponentsEnabled(true);
		btnState(true, false, false, false, false);
	}
	
	public void cancelSuretyBond() {
		refreshSuretyBond(false);
		setComponentsEnabled(false) ;
		btnState(false, false, false, false, false);
	}
	
	public void disableSuretyBond() {
		refreshSuretyBond(true);
		setComponentsEnabled(false) ;
		btnState(false, false, false, false, false);
	}
	public void editSuretyBond() {
		refreshSuretyBond(true);
		setComponentsEnabled(false) ;
		btnState(true, false, false, false, false);
	}
	
	public void displaySuretyBonds(String contract_no) {
		this.contract_no = contract_no;
		refreshSuretyBond(true);
		
		_SuretyBond.displaySuretyBondDetails(modelSuretyBondMain, rowHeaderSuretyBondMain, contract_no);
		scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSuretyBondMain.getRowCount())));
		tblSuretyBondMain.packAll();
		
		//setComponentsEnabled(true);
		btnState(true, false, false, false, false);
	}
	
	public boolean validatingSuretyBond(){
		if(txtBondNo.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Bond No.", "Add", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtAmount.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Amount", "Add", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(dateFrom.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Date From", "Add", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(dateTo.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Date To", "Add", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(lookupInsuranceCo.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Insurance Co.", "Add", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Add")){
			refreshSuretyBond(false);
			setComponentsEnabled(true);
			tblSuretyBondMain.clearSelection();
			tblSuretyBondMain.setEnabled(false);
			btnState(false, false, false, true, true);
			
			grpButton.setSelected(((JButton)arg0.getSource()).getModel(), true);
		}
		
		if(actionCommand.equals("Edit")){
			setComponentsEnabled(true);
			tblSuretyBondMain.setEnabled(false);
			btnState(false, false, false, true, true);
			
			grpButton.setSelected(((JButton)arg0.getSource()).getModel(), true);
		}
		
		if(actionCommand.equals("Delete")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				String bond_no = txtBondNo.getText().trim();
				
				_SuretyBond.deleteSuretyBond(contract_no, bond_no);
				
				refreshSuretyBond(false);
				setComponentsEnabled(false);
				tblSuretyBondMain.setEnabled(true);
				btnState(true, false, false, false, false);
				displaySuretyBonds(contract_no);
			}
		}
		
		if(actionCommand.equals("Save")){
			if(validatingSuretyBond()){

				if(tblSuretyBondMain.getSelectedRows().length==1) {
					int x = tblSuretyBondMain.getSelectedRow();
					Integer rec_id = (Integer) modelSuretyBondMain.getValueAt(x, 10);
					boolean isExisting = grpButton.getSelection().getActionCommand().equals("Edit");
					String bond_no = txtBondNo.getText();
					String insurance_id = lookupInsuranceCo.getValue();
					Timestamp date_from = dateFrom.getTimestamp();
					Timestamp date_to = dateTo.getTimestamp();
					BigDecimal amount = (BigDecimal) txtAmount.getValued();
					String department_id = lookupDepartmentConcerned.getValue();
					String location_id = lookupPresentLocation.getValue();
					
					if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						_SuretyBond.saveSuretyBonds(isExisting, contract_no, bond_no, insurance_id, date_from, date_to, amount, department_id, location_id,rec_id);
						
						refreshSuretyBond(false);
						setComponentsEnabled(false);
						tblSuretyBondMain.setEnabled(true);
						btnState(true, false, false, false, false);
						displaySuretyBonds(contract_no);
					}
				}
				else
				{

					int x = tblSuretyBondMain.getSelectedRow();
					Integer rec_id = 0;
					boolean isExisting = grpButton.getSelection().getActionCommand().equals("Edit");
					String bond_no = txtBondNo.getText();
					String insurance_id = lookupInsuranceCo.getValue();
					Timestamp date_from = dateFrom.getTimestamp();
					Timestamp date_to = dateTo.getTimestamp();
					BigDecimal amount = (BigDecimal) txtAmount.getValued();
					String department_id = lookupDepartmentConcerned.getValue();
					String location_id = lookupPresentLocation.getValue();
					
					if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						_SuretyBond.saveSuretyBonds(isExisting, contract_no, bond_no, insurance_id, date_from, date_to, amount, department_id, location_id,rec_id);
						
						refreshSuretyBond(false);
						setComponentsEnabled(false);
						tblSuretyBondMain.setEnabled(true);
						btnState(true, false, false, false, false);
						displaySuretyBonds(contract_no);
					}
				
				}
				
				
			}
		}
		
		if(actionCommand.equals("Cancel")){
			refreshSuretyBond(false);
			setComponentsEnabled(false);
			tblSuretyBondMain.setEnabled(true);
			btnState(true, false, false, false, false);
			displaySuretyBonds(contract_no);
		}
	}
}
