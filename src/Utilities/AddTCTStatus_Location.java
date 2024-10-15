package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._ButtonGroup;
import components._JInternalFrame;
import components._JXTextField;

public class AddTCTStatus_Location extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 5159650933602942626L;

	static String title = "Add TCT Status/Location";
	Dimension frameSize = new Dimension(400, 180);
	Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JXPanel pnlMain;
	private JXPanel pnlSouth;
	private JXPanel pnlNorth;
	private JXPanel pnlNorthWest;
	private JComboBox cmbStatusType;
	private JPanel pnlNWLabel;

	private JXPanel pnlNorthCenter;
	private JXPanel pnlNWCenter;

	private JPanel pnlNorthEast;
	private JPanel pnlNELabel;
	private JPanel pnlNECenter;
	private JXPanel pnlCenter;
	private JPanel pnlNCLabel;
	private JXLabel lblStatusAlias;
	private JPanel pnlNCenter;
	private _JXTextField txtStatusAlias;

	private _ButtonGroup grpNewEdit = new _ButtonGroup();
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	
	private String toDo = "";
	
	private _JLookup lookupCode;
	private JXLabel lblStatusDesc;
	private _JXTextField txtStatusDesc;
	private String status_code;


	
	public AddTCTStatus_Location() {
		super(title, false, true, true, true);
		initGUI();
	}

	public AddTCTStatus_Location(String title) {
		super(title);
		initGUI();
	}

	public AddTCTStatus_Location(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		
		
		pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JXPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("TCT Status/Location"));
			pnlNorth.setLayout(new GridLayout(1, 2, 3, 3));
			{
				pnlNorthWest = new JXPanel(new BorderLayout(5, 5));
				pnlNorth.add(pnlNorthWest);
				{
					pnlNWLabel = new JPanel(new GridLayout(1, 1, 3, 3));
					pnlNorthWest.add(pnlNWLabel, BorderLayout.WEST);
					{
						JXLabel lblStatusType = new JXLabel("Status Type");
						pnlNWLabel.add(lblStatusType);
					}
				}
				{
					pnlNWCenter = new JXPanel(new GridLayout(1, 1, 3, 3));
					pnlNorthWest.add(pnlNWCenter, BorderLayout.CENTER);
					{
						JXPanel pnlStatusType = new JXPanel(new BorderLayout(3, 3));
						pnlNWCenter.add(pnlStatusType);
						{
							cmbStatusType = new JComboBox(new String [] {"Status", "Location"});
							pnlStatusType.add(cmbStatusType, BorderLayout.CENTER);
							cmbStatusType.setEnabled(false);
							
						}
					}
				}
				{
					pnlNorthEast = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthEast);
					{
						pnlNELabel = new JPanel(new GridLayout(1, 1, 3, 3));
						pnlNorthEast.add(pnlNELabel, BorderLayout.WEST);
						{
							JLabel lblStatusId = new JLabel("Status Code");
							pnlNELabel.add(lblStatusId);
						}
					}
				}
				{
						pnlNECenter = new JXPanel(new GridLayout(1, 1, 3, 3));
						pnlNorthEast.add(pnlNECenter, BorderLayout.CENTER);
						{
							lookupCode = new _JLookup(null, "Status Code");
							pnlNECenter.add(lookupCode);
							lookupCode.setReturnColumn(0);
							lookupCode.setFilterName(true);
							lookupCode.setEnabled(true);
							lookupCode.setLookupSQL(getStatusCode()); 
							lookupCode.addLookupListener(new LookupListener() {
								
								@Override
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet(); 
									
									if(data != null) {
									
									status_code = (String) data[0];
									
									}
									
									displayStatusData(status_code);
									btnEdit.setEnabled(true);
									btnAdd.setEnabled(false);
									txtStatusDesc.setEditable(false);
									txtStatusAlias.setEditable(false);	
						
								}
							});
				
						}
					}
			}
			{
				 pnlCenter = new JXPanel(new BorderLayout(5, 5));
				 pnlMain.add(pnlCenter, BorderLayout.CENTER);
				 pnlCenter.setLayout(new GridLayout(2, 2, 3, 3));
				{
					pnlNorthCenter = new JXPanel(new BorderLayout(5,5));
					pnlMain.add(pnlNorthCenter);
					{
						pnlNCLabel = new JPanel(new GridLayout(2, 2, 3, 3));
						pnlNorthCenter.add(pnlNCLabel, BorderLayout.WEST);
						{
							lblStatusDesc = new JXLabel("Status Description");
							pnlNCLabel.add(lblStatusDesc);
						}
						{
						    lblStatusAlias = new JXLabel("Status Alias");
							pnlNCLabel.add(lblStatusAlias);
						}
					}
				}
				{
					pnlNCenter = new JPanel(new GridLayout(2, 2, 3, 3));
					pnlNorthCenter.add(pnlNCenter, BorderLayout.CENTER);
					{
							txtStatusDesc = new _JXTextField("Status Description");
							pnlNCenter.add(txtStatusDesc);
					}
					{
							txtStatusAlias = new _JXTextField("Status Alias");
							pnlNCenter.add(txtStatusAlias);
					}
				}
				

			}
			
			{
				pnlSouth = new JXPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 7));
				pnlSouth.setPreferredSize(new Dimension(300, 30));
				{
					btnAdd = new JButton("Add");
					pnlSouth.add(btnAdd);
					btnAdd.setActionCommand(btnAdd.getText());
					btnAdd.setMnemonic(KeyEvent.VK_A);
					btnAdd.addActionListener(this);
					grpNewEdit.add(btnAdd);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouth.add(btnEdit);
					btnEdit.setActionCommand(btnEdit.getText());
					btnEdit.setMnemonic(KeyEvent.VK_E);
					btnEdit.addActionListener(this);
					grpNewEdit.add(btnEdit);

					
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
				
				this.btnState(true, false, false, false);
			}
		}
	} // END OF INIT GUI
	
	public void btnState(Boolean sAdd, Boolean sEdit ,Boolean sSave, Boolean sCancel){
		btnAdd.setEnabled(sAdd);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}
	
	public void add(){
		
		System.out.println("Welcome");
		lookupCode.setEnabled(true);
		cmbStatusType.setEnabled(true);
		txtStatusDesc.setEditable(true);
		txtStatusAlias.setEditable(true);
		btnState(false, false, true, true);
	}
	
	private void edit(){
		
		
		cmbStatusType.setEnabled(true);
		txtStatusDesc.setEditable(true);
		txtStatusAlias.setEditable(true);
	    btnState(false, false, true, true);
	    
	}
	
	private void saving(){
		
		pgUpdate db = new pgUpdate();
		
		//String code = (String) lookupCode.getValue();
		String description = (String) txtStatusDesc.getText();
		String alias = (String) txtStatusAlias.getText();
		String status_type = "";
		if(cmbStatusType.getSelectedIndex()==0) {
			status_type = "S";
		}
		else 
		{ 
			status_type = "L";
		}
		{
			String query = "INSERT INTO public.mf_tct_taxdec_status (status_code, status_desc, status_alias, status_id, created_by, date_created,status_type)"+
			"VALUES ((Select max(getinteger(status_code))+1 from mf_tct_taxdec_status where status_id = 'A'),"+
			 "'"+description+"','"+alias+"','A','"+UserInfo.EmployeeCode+"',now(),'"+status_type+"')";
			
			db.executeUpdate(query, true);
		}
		db.commit();
		
	}
			
//		{
////			String query = "INSERT INTO mf_holidays (rec_id,holidays,holiday_desc,holiday_type,halfday,created_by,holiday_otcode,date_created) "+
////					"VALUES ((SELECT COALESCE(max(rec_id), 0)+1 from mf_holidays),"+  
////					"'"+dateVal+"','"+textVal+"', '"+comboVal+"','"+checkVal+"','"+UserInfo.EmployeeCode+"','00','"+FncGlobal.getDateToday()+"')";
////			db.executeUpdate(query, true);
//			
//			String SQL = "SELECT sp_save_holiday( '"+dateVal+"','"+textVal+"', '"+comboVal+"', "+checkVal+", '"+UserInfo.EmployeeCode+"')";
//			db.select(SQL, "Save", true);
//
//		}
//	}
	
	
	
	private void update(){

		pgUpdate db = new pgUpdate();

		String code = (String) lookupCode.getValue();
		String description = (String) txtStatusDesc.getText();
		String alias = (String) txtStatusAlias.getText();
		String status_type = "";
		if(cmbStatusType.getSelectedIndex()==0) {
			status_type = "S";
		}
		else 
		{ 
			status_type = "L";
		}
		{
			String query = "UPDATE public.mf_tct_taxdec_status \n"+
					"SET status_desc= '"+description+"', status_alias= '"+alias+"', edited_by= '"+UserInfo.EmployeeCode+"', date_edited= now(), status_type= '"+status_type+"' \n"+
					"WHERE status_code= '"+code+"';";

			db.executeUpdate(query, true);
			System.out.println("UPDATE SQL: "+query);
		}
		JOptionPane.showMessageDialog(getContentPane(),"New TCT Status/Location was updated.","Information",JOptionPane.INFORMATION_MESSAGE);
		db.commit();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		System.out.println("ACTION COMMAND");

	
		if(actionCommand.equals("Add")){
			grpNewEdit.setSelectedButton(e);
			add();
		}
		
		if(actionCommand.equals("Edit")){
			grpNewEdit.setSelectedButton(e);
			edit();
			toDo = "edit";
		}
		
		System.out.println("Eto Value ng ToDo: "+toDo);
		
		if(actionCommand.equals("Save")) {
			
			
			if(toDo.equals("edit")){
				
				update();
				btnSave.setEnabled(false);
				btnCancel.setEnabled(false);
				btnAdd.setEnabled(true);
				cmbStatusType.setSelectedItem("");
				txtStatusDesc.setText("");
				txtStatusAlias.setText("");		
			}
			
			else {
				
				saving();
				JOptionPane.showMessageDialog(getContentPane(),"New TCT Status/Location was added.","Information",JOptionPane.INFORMATION_MESSAGE);
				btnSave.setEnabled(false);
				btnCancel.setEnabled(false);
				btnAdd.setEnabled(true);
				cmbStatusType.setSelectedItem("");
				txtStatusDesc.setText("");
				txtStatusAlias.setText("");		
				cmbStatusType.setEditable(false);
				txtStatusDesc.setEditable(false);
				txtStatusAlias.setEditable(false);	
			}	
	}
		
		if(actionCommand.equals("Cancel")){
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to cancel?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
//				clearROP();
//				loadComponents();
				cmbStatusType.setSelectedItem(null);
				txtStatusDesc.setText("");
				txtStatusAlias.setText("");
				
				btnSave.setEnabled(false);
				btnAdd.setEnabled(true);
				btnCancel.setEnabled(false);
				
			}
		}
	
	}
	
	public static String getStatusCode() { //XXX TODO
		String SQL = "SELECT distinct ON(getinteger(status_code)) TRIM(status_code) as \"ID\"\n"
				+ ", TRIM(status_desc) as \"Description\", \n"
				+ "TRIM(status_alias)::VARCHAR as \"Alias\"\n"
				+ "FROM mf_tct_taxdec_status \n"
				+ "WHERE status_id = 'A' \n"
				+ "order by getinteger(status_code) ";
		
		return SQL; 
		
	}
	
	public void displayStatusData(String status_code){
		pgSelect db = new pgSelect();
		
		String status_type = "";
	
		String SQL = "SELECT status_desc, status_alias, status_type FROM mf_tct_taxdec_status where status_code = ('"+status_code+"')";
		db.select(SQL);
		
		FncSystem.out("Display Status Code", SQL);
		
		if(db.isNotNull()){
		txtStatusDesc.setText((String) db.getResult()[0][0]);
		txtStatusAlias.setText((String) db.getResult()[0][1]);
		status_type = (String) db.getResult()[0][2]; 
		}
		
		if(status_type.equals("S")) {
			cmbStatusType.setSelectedIndex(0);
		} else {
			cmbStatusType.setSelectedIndex(1);
		}
		
	}

}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				



