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

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import tablemodel.modelCompanyList;
import Functions.FncTables;
import components._JTableMain;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class AddCompany extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = -1601139037500836768L;

	Dimension frameSize = new Dimension(500, 500);
	static String title = "Add Company";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JLabel lblCompanyID;
	private JLabel lblCompanyName;
	private JLabel lblCompanyAddress;

	private JPanel pnlNorthEast;
	private JPanel pnlCompany;
	private _JXTextField txtCompanyID;
	private JLabel lblCompanyAlias;
	private _JXTextField txtCompanyAlias;

	private _JXTextField txtCompanyName;
	private _JXTextField txtCompanyAddress;

	private JPanel pnlCenter;
	private modelCompanyList modelCompany;
	private _JTableMain tblAddCompany;
	private JScrollPane scrollAddCompany;
	private JList rowHeaderAddCompany;

	private JPanel pnlSouth;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	
	private String to_do = "add_new";

	/*public AddCompany() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AddCompany(String title) {
		super(title, false, true, false, true);
		initGUI();
	}*/
	
	public AddCompany() {
		initGUI();
	}

	public AddCompany(LayoutManager layout) {
		super(layout);
	}

	public AddCompany(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public AddCompany(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		//this.setTitle(title);
		this.setSize(frameSize);
		this.setLayout(new BorderLayout(5, 5));
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(EMPTY_BORDER);
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Company Details"));
				{
					pnlNorthWest = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					{
						lblCompanyID = new JLabel("Company ID");
						pnlNorthWest.add(lblCompanyID);
					}
					{
						lblCompanyName = new JLabel("*Company Name");
						pnlNorthWest.add(lblCompanyName);
					}
					{
						lblCompanyAddress = new JLabel("*Company Address");
						pnlNorthWest.add(lblCompanyAddress);
					}
				}
				{
					pnlNorthEast = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
					{
						pnlCompany = new JPanel(new BorderLayout(5, 5));
						pnlNorthEast.add(pnlCompany);
						{
							txtCompanyID = new _JXTextField("ID");
							pnlCompany.add(txtCompanyID, BorderLayout.WEST);
							txtCompanyID.setPreferredSize(new Dimension(100, 0));
							txtCompanyID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							lblCompanyAlias = new JLabel("*Company Alias", JLabel.TRAILING);
							pnlCompany.add(lblCompanyAlias, BorderLayout.CENTER);
						}
						{
							txtCompanyAlias = new _JXTextField("Alias");
							pnlCompany.add(txtCompanyAlias, BorderLayout.EAST);
							txtCompanyAlias.setPreferredSize(new Dimension(100, 0));
						}
					}
					{
						txtCompanyName = new _JXTextField("Company Name");
						pnlNorthEast.add(txtCompanyName);
						//txtCompanyName.setHorizontalAlignment(JXTextField.WEST);
					}
					{
						txtCompanyAddress = new _JXTextField("Company Address");
						pnlNorthEast.add(txtCompanyAddress);
						//txtCompanyAddress.setHorizontalAlignment(JXTextField.WEST);
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Company List"));
				{
					scrollAddCompany = new JScrollPane();
					pnlCenter.add(scrollAddCompany, BorderLayout.CENTER);
					scrollAddCompany.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelCompany = new modelCompanyList();
						tblAddCompany = new _JTableMain(modelCompany);
						scrollAddCompany.setViewportView(tblAddCompany);
						tblAddCompany.hideColumns("Address");

						modelCompany.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {

								((DefaultListModel)rowHeaderAddCompany.getModel()).addElement(modelCompany.getRowCount());

								if(modelCompany.getRowCount() == 0){
									rowHeaderAddCompany.setModel(new DefaultListModel());
								}
							}
						});

						tblAddCompany.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent arg0) {
								if(!arg0.getValueIsAdjusting()){
									try {
										if(tblAddCompany.getSelectedRows().length ==1){
											int row = tblAddCompany.getSelectedRow();
											String co_id = (String) modelCompany.getValueAt(row, 0);
											String company_name = (String) modelCompany.getValueAt(row, 1);
											String company_alias = (String) modelCompany.getValueAt(row, 2);
											String company_address = (String) modelCompany.getValueAt(row, 3);

											txtCompanyID.setText(co_id);
											txtCompanyAlias.setText(company_alias);
											txtCompanyName.setText(company_name);
											txtCompanyAddress.setText(company_address);
											btnState(true, true, false, false);
										}else{
											btnState(true, false, false, false);
										}
									} catch (ArrayIndexOutOfBoundsException e) { }
								}
							}
						});
					}
					{
						rowHeaderAddCompany = tblAddCompany.getRowHeader();
						rowHeaderAddCompany.setModel(new DefaultListModel());
						scrollAddCompany.setRowHeaderView(rowHeaderAddCompany);
						scrollAddCompany.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
			}
		}
		displayCompanyList();
		//this.setComponentsEditable(pnlNorthEast, false);
		btnState(true, false, false, false);
	}//END OF INIT GUI

	private void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void displayCompanyList(){
		try{
			modelCompany.clear();
		} catch (ArrayIndexOutOfBoundsException e) {}

		pgSelect db = new pgSelect();
		String sql = "SELECT TRIM(co_id) as \"Company ID\", " +
				"TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias) as \"Company Alias\", " +
				"TRIM(company_address) as \"Address\" " +
				"from mf_company " +
				"order by co_id ";
		db.select(sql);
		if(db.isNotNull()){
			for (int x=0; x<db.getRowCount(); x++){
				modelCompany.addRow(db.getResult()[x]);
			}
			scrollAddCompany.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAddCompany.getRowCount())));
			tblAddCompany.packAll();
		}
	}

	private boolean isCompanyExisting(String co_id){
		pgSelect db = new pgSelect();
		String sql = "select * from mf_company where co_id = '"+co_id+"'";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean isCompanyNameExisting(String company_name){
		pgSelect db = new pgSelect();
		String sql = "select * from mf_company where company_name = '"+company_name+"'";
		db.select(sql);
		return db.isNotNull();
	}

	private boolean toSave(){
		if(txtCompanyAlias.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Company Alias"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtCompanyName.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Company Name"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtCompanyAddress.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Employee Code"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void newCompany(){
		cancelCompany();
		pgSelect db = new pgSelect();
		String sql = "select trim(to_char(max(co_id::integer) + 1, '00')) from mf_company\n";
		db.select(sql);
		txtCompanyID.setText((String) db.getResult()[0][0]);
		//this.setComponentsEditable(pnlNorthEast, true);
		txtCompanyAlias.setEditable(true);
		txtCompanyName.setEditable(true);
		txtCompanyAddress.setEditable(true);
		btnState(false, false, true, true);
		tblAddCompany.setEnabled(false);
		rowHeaderAddCompany.setEnabled(false);
	}

	private void editCompany(){
		if(tblAddCompany.getSelectedRows().length == 1){
			//this.setComponentsEditable(pnlNorthEast, true);
			btnState(false, false, true, true);
			txtCompanyID.setEditable(false);
			tblAddCompany.setEnabled(false);
			rowHeaderAddCompany.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollAddCompany, "Please select one row to edit");
			tblAddCompany.clearSelection();
		}
	}

	private void addCompany(String co_id, String company_alias, String company_name, String company_address){
		pgUpdate db = new pgUpdate();
		String sql = "INSERT INTO mf_company(co_id, company_alias, company_name, company_address, vat_rate, company_logo)\n" + 
				"VALUES ('"+co_id+"', '"+company_alias+"', '"+company_name+"', '"+company_address+"', null, null);";
		db.executeUpdate(sql, true);
		db.commit();
	}

	private void updateCompany(String company_alias, String company_name, String company_address, String co_id){
		pgUpdate db = new pgUpdate();
		String sql = "UPDATE mf_company SET company_alias='"+company_alias+"', company_name='"+company_name+"', company_address='"+company_address+"' \n" + 
				"WHERE co_id = '"+co_id+"';";
		db.executeUpdate(sql, true);
		db.commit();
	}

	private void cancelCompany(){
		txtCompanyID.setText("");
		txtCompanyAlias.setText("");
		txtCompanyName.setText("");
		txtCompanyAddress.setText("");
		//this.setComponentsEditable(pnlNorthEast, false);
		btnState(true, true, false, false);
		tblAddCompany.clearSelection();
		tblAddCompany.setEnabled(true);
		rowHeaderAddCompany.setEnabled(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if(actionCommand.equals("New")){
			newCompany();
			to_do = "add_new";
		}
		if(actionCommand.equals("Edit")){
			editCompany();
			to_do = "edit";
		}
		if(actionCommand.equals("Save")){
			
			String remark = "";
			if (to_do.equals("add_new")) {remark = "Are you sure you want to add a new company?";} 
			else {remark = "Are you sure you want to update existing company?";}
			
			if(toSave()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), remark, actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

					if(isCompanyExisting(txtCompanyID.getText())){
						updateCompany(txtCompanyAlias.getText(), txtCompanyName.getText().replace("'", "").toUpperCase(), txtCompanyAddress.getText().replace("'", "").toUpperCase(), txtCompanyID.getText());
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Company Information has been updated...");
					}else{
						if(isCompanyNameExisting(txtCompanyName.getText().replace("'", "").toUpperCase())){
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Company is already existing, please input another name");
							txtCompanyName.setText("");
						}else{
							addCompany(txtCompanyID.getText(), txtCompanyAlias.getText(), txtCompanyName.getText().replace("'", "").toUpperCase(), txtCompanyAddress.getText().replace("'", "").toUpperCase());
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Company Info has been saved...");
						}
						displayCompanyList();
						cancelCompany();
					}
				}
			}
		}
		if(actionCommand.equals("Cancel")){
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Cancel Editing?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				cancelCompany();
			}
		}
	}
}
