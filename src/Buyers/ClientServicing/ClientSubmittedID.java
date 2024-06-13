/**
 * 
 */
package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelClientSubmittedID;

/**
 * @author John Lester Fatallo
 */
public class ClientSubmittedID extends JXPanel implements _GUI,
ActionListener {

	private static final long serialVersionUID = -3839621438042672964L;

	static Dimension SIZE = new Dimension(800, 600);

	private static String title = "Client Submitted ID";

	private JPanel pnlNorth;

	private JPanel pnlNorthWest;

	private JPanel pnlNorthCenter;
	
	private JLabel lblIDType;
	private JLabel lblIDNo;
	private JLabel lblExpiration;
	private _JLookup lookupID;
	private _JXTextField txtIDDesc;
	
	private JPanel pnlIDNo;
	private _JXTextField txtIDNo;
	
	private JPanel pnlIDExpiration;
	private _JDateChooser dateExpiration;
	private JLabel lblStatus;
	private JComboBox cmbIDStatus;
	private JLabel lblDefault;
	private JCheckBox chkDefault;
	
	private JPanel pnlValidID;
	private JScrollPane scrollIssuedID;
	private _JTableMain tblIssuedID;
	private JList rowHeaderIssuedID;
	private modelClientSubmittedID modelSubmittedID;
	//private JList rowHeaderSubmittedID;

	private ClientInformation ci;
	
	public ClientSubmittedID(ClientInformation ci){
		this.ci = ci;
		initGUI();
	}
	
	public ClientSubmittedID(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public ClientSubmittedID(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public ClientSubmittedID(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(796, 386));
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("ID Details"));
			{
				pnlNorthWest = new JPanel(new GridLayout(3, 1, 3, 3));
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				{
					lblIDType = new JLabel("*ID Type");
					pnlNorthWest.add(lblIDType);
				}
				{
					lblIDNo = new JLabel("*ID No");
					pnlNorthWest.add(lblIDNo);
				}
				{
					lblExpiration = new JLabel("Expiration");
					pnlNorthWest.add(lblExpiration);
				}
				
			}
			{
				pnlNorthCenter = new JPanel(new GridLayout(3, 1, 3, 3));
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				{
					pnlValidID = new JPanel(new BorderLayout(3, 3));
					pnlNorthCenter.add(pnlValidID);
					{
						lookupID = new _JLookup(null, "Select ID", 0);
						pnlValidID.add(lookupID, BorderLayout.WEST);
						lookupID.setPreferredSize(new Dimension(70, 0));
						lookupID.setFilterName(true);
						lookupID.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();
								FncSystem.out("Display sql for lookup of id", lookupID.getLookupSQL());
								
								if(data != null){
									String id_desc = (String) data[1];
									txtIDDesc.setText(id_desc);
								}
							}
						});
					}
					{
						txtIDDesc = new _JXTextField();
						pnlValidID.add(txtIDDesc, BorderLayout.CENTER);
					}
				}
				{
					pnlIDNo = new JPanel(new BorderLayout(3, 3));
					pnlNorthCenter.add(pnlIDNo);
					{
						txtIDNo = new _JXTextField();
						pnlIDNo.add(txtIDNo, BorderLayout.WEST);
						txtIDNo.setPreferredSize(new Dimension(250, 0));
					}
					{
						lblStatus = new JLabel("Status", JLabel.TRAILING);
						pnlIDNo.add(lblStatus);
					}
					{
						cmbIDStatus = new JComboBox(new String[]{"Active", "Inactive"});
						pnlIDNo.add(cmbIDStatus, BorderLayout.EAST);
						cmbIDStatus.setEnabled(false);
						cmbIDStatus.setPreferredSize(new Dimension(150, 0));
					}
				}
				{
					pnlIDExpiration = new JPanel(new BorderLayout(3, 3));
					pnlNorthCenter.add(pnlIDExpiration);
					{
						dateExpiration = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
						pnlIDExpiration.add(dateExpiration, BorderLayout.WEST);
						dateExpiration.setPreferredSize(new Dimension(100, 0));
						dateExpiration.getCalendarButton().setEnabled(false);
					}
					{
						lblDefault = new JLabel("Default", JLabel.TRAILING);
						pnlIDExpiration.add(lblDefault);
					}
					{
						chkDefault = new JCheckBox();
						pnlIDExpiration.add(chkDefault, BorderLayout.EAST);
						chkDefault.setEnabled(false);
					}
				}
			}
		}
		{
			pnlValidID = new JPanel(new BorderLayout(5, 5));
			this.add(pnlValidID, BorderLayout.CENTER);
			pnlValidID.setBorder(components.JTBorderFactory.createTitleBorder("List of Submitted ID's"));
			{
				scrollIssuedID = new JScrollPane();
				pnlValidID.add(scrollIssuedID, BorderLayout.CENTER);
				scrollIssuedID.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				{
					modelSubmittedID = new modelClientSubmittedID();

					tblIssuedID = new _JTableMain(modelSubmittedID);
					scrollIssuedID.setViewportView(tblIssuedID);
					tblIssuedID.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblIssuedID.hideColumns("Rec. ID");
					tblIssuedID.setSortable(false);

					modelSubmittedID.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							if(e.getType() == 1) {
								((DefaultListModel)rowHeaderIssuedID.getModel()).addElement(modelSubmittedID.getRowCount());
							}
							
							if(modelSubmittedID.getRowCount() == 0){
								rowHeaderIssuedID.setModel(new DefaultListModel());
							}
						}
					});
					
					tblIssuedID.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						
						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							if(!arg0.getValueIsAdjusting()){
								if(tblIssuedID.getSelectedRows().length == 1){
									int selected_row = tblIssuedID.getSelectedRow();
									
									String id_type = (String) modelSubmittedID.getValueAt(selected_row, 1);
									String id_desc = (String) modelSubmittedID.getValueAt(selected_row, 2);
									String id_no = (String) modelSubmittedID.getValueAt(selected_row, 3);
									Date id_expiration = (Date) modelSubmittedID.getValueAt(selected_row, 4);
									String id_status = (String) modelSubmittedID.getValueAt(selected_row, 5);
									Boolean is_default = (Boolean) modelSubmittedID.getValueAt(selected_row, 6);
									
									lookupID.setValue(id_type);
									txtIDDesc.setText(id_desc);
									txtIDNo.setText(id_no);
									dateExpiration.setDate(id_expiration);
									chkDefault.setSelected(is_default);
									
									if(id_status.equals("A")){
										cmbIDStatus.setSelectedIndex(0);
									}else{
										cmbIDStatus.setSelectedIndex(1);
									}
									
									ci.btnState(true, true, false, false, false);
									if(ci.canEdit() == false){
										ci.btnState(false, false, false, false, false);
									}
								}
							}
						}
					});
				}
				{
					tblIssuedID.getColumnModel().getColumn(0).setPreferredWidth(70); //Select
					tblIssuedID.getColumnModel().getColumn(1).setPreferredWidth(70); //ID Code
					tblIssuedID.getColumnModel().getColumn(2).setPreferredWidth(350); //ID Type
					tblIssuedID.getColumnModel().getColumn(3).setPreferredWidth(100); //ID No.
					tblIssuedID.getColumnModel().getColumn(4).setPreferredWidth(100); //Expiration
				}
				{
					rowHeaderIssuedID = tblIssuedID.getRowHeader();
					rowHeaderIssuedID.setModel(new DefaultListModel());
					scrollIssuedID.setRowHeaderView(rowHeaderIssuedID);
					scrollIssuedID.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}
		ci.setComponentsEditable(pnlNorth, false);
		
	}//XXX END OF INIT GUI
	
	private String sqlIDType(String entity_id){//SQL FOR THE ID TYPE TO BE SUBMITTED
		return "SELECT id_no as \"ID Code\", TRIM(id_desc) as \"ID Desc\"\n" + 
			   "FROM mf_client_valid_id \n" + 
			   "WHERE id_no NOT IN (select id_type::INT from rf_entity_submitted_id where entity_id = '"+entity_id+"') \n"+
			   "ORDER BY id_desc";
	}
	
	public void displaySubmittedID(String entity_id){//DISPLAYS THE SUBMITTED ID
		modelSubmittedID.clear();
		//XXX PUT CLEAR HERE
		
		pgSelect db = new pgSelect();

		String SQL = "SELECT * FROM view_ci_submitted_id('"+entity_id+"')";
		db.select(SQL);
		FncSystem.out("Display Submitted ID", SQL);

		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				modelSubmittedID.addRow(db.getResult()[x]);
			}
			scrollIssuedID.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblIssuedID.getRowCount())));
			tblIssuedID.packAll();
		}
	}
	
	public void newSubmittedID(){//ENABLES THE FIELDS
		
		clearSubmittedID();
		
		lookupID.setLookupSQL(sqlIDType(ci.getEntityID()));
		
		lookupID.setEditable(true);
		txtIDNo.setEditable(true);
		chkDefault.setEnabled(true);
		
		tblIssuedID.clearSelection();
		tblIssuedID.setEnabled(false);
		rowHeaderIssuedID.setEnabled(false);
		
		cmbIDStatus.setEnabled(true);
		dateExpiration.getCalendarButton().setEnabled(true);
		
	}

	public void editSubmittedID(){
		
		if(tblIssuedID.getSelectedRows().length == 1){
			
			lookupID.setLookupSQL(sqlIDType(ci.getEntityID()));
			
			ci.btnState(false, false, false, true, true);
			lookupID.setEditable(true);
			txtIDNo.setEditable(true);
			dateExpiration.getCalendarButton().setEnabled(true);
			dateExpiration.setEditable(true);
			cmbIDStatus.setEnabled(true);
			chkDefault.setEnabled(true);
			
			tblIssuedID.setEnabled(false);
			rowHeaderIssuedID.setEnabled(false);
			
		}else{
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select item to edit", "Edit", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public Boolean saveSubmittedID(String entity_id){
		String id_code = lookupID.getValue();
		String id_no = txtIDNo.getText();
		Date expiration = dateExpiration.getDate();
		String status = cmbIDStatus.getSelectedItem() == "Active" ? "A":"I";
		Boolean is_default = chkDefault.isSelected();
		Integer rec_id = null;
		
		if(tblIssuedID.getSelectedRows().length == 1){
			int selected_row = tblIssuedID.convertRowIndexToModel(tblIssuedID.getSelectedRow());
			
			rec_id = (Integer) modelSubmittedID.getValueAt(selected_row, 0);
		}
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT sp_save_ci_submitted_id('"+entity_id+"', NULLIF('"+id_code+"', 'null'), '"+id_no+"', \n"+
					 "NULLIF('"+expiration+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, '"+status+"', "+is_default+", \n"+
					 ""+rec_id+", '"+UserInfo.EmployeeCode+"')";
		db.select(SQL, "Save", true);
		
		return (Boolean) db.getResult()[0][0];
	}
	
	public void cancelSubmittedID(){
		
		clearSubmittedID();
		cmbIDStatus.setEnabled(false);
		dateExpiration.getCalendarButton().setEnabled(false);
		chkDefault.setEnabled(false);
		
	}
	
	public void clearSubmittedID(){//CLEAR THE FIELDS IN THE CLIENT DETAILS AND IN THE TABLE
		
		ci.setComponentsEditable(pnlNorth, false);
		tblIssuedID.clearSelection();
		tblIssuedID.setEnabled(true);
		rowHeaderIssuedID.setEnabled(true);
		
		lookupID.setValue(null);
		txtIDDesc.setText("");
		txtIDNo.setText("");
		dateExpiration.setDate(null);
		chkDefault.setSelected(false);
		
	}

	public void actionPerformed(ActionEvent arg0) {//REMOVE THIS
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("New")){//NEW SUBMITTED ID
			newSubmittedID();
		}
		
		if(actionCommand.equals("Edit")){//EDITING OF THE DATA FOR THE SUBMITTED ID
			editSubmittedID();
		}
		
	}
}
