package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Buyers.ClientServicing._RefundofPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
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

	private JXLabel lblStatusType;
	private JTextField txtCompany;
	
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

	private _JXTextField txtStatID;

	private JXPanel pnlCenter;


	private JPanel pnlNCLabel;

	private JXLabel lblStatusAlias;

	private JXLabel lblStatusName;

	private JPanel pnlNCenter;

	private _JXTextField txtStatusName;
	private _JXTextField txtStatusAlias;

	
	
	private _ButtonGroup grpNewEdit = new _ButtonGroup();
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;

	private JPanel pnlNELookup;

	private _JLookup lookupID;
	
	String to_do 	= "";

	

	






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
						
						pnlNELookup = new JPanel(new GridLayout(1, 1, 3, 3));
						pnlNorthEast.add(pnlNELookup, BorderLayout.WEST);
						{
							lookupID = new _JLookup(null, "Status ID");
							pnlNELookup.add(lookupID, BorderLayout.WEST);
							//lookupID.setPreferredSize(new Dimension(200, 20));
							lookupID.setReturnColumn(1);
							lookupID.setFilterName(true);
							lookupID.addActionListener(this);
							lookupID.setEnabled(false);
							lookupID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									
										if (data != null) {
										
										rec_id = (Integer) data[13];
										
										txtDescription.setText(String.format("%s", data[2]));
										txtAlias.setText(String.format("%s", data[3]));
										txtAmt.setValue(data[4]);
										cmbClass.setSelectedItem(String.format("%s", data[6]));
										lookupAcctID.setValue(String.format("%s", data[5]));
										lookupRefundAcct.setValue(String.format("%s", data[14]));
										lookupPayee.setValue(String.format("%s", data[7]));
										lookupPayeeType.setValue(String.format("%s", data[8]));
										cmbRefundable.setSelectedItem(String.format("%s", data[9]));
										if (data[11] != null){
											txtRemarks.setText(String.format("%s", data[10]));
										} else {
											txtRemarks.setText(" ");
										}
										cmbStatus.setSelectedItem(String.format("%s", data[0]));
										lblPayee.setText(String.format("[ %s ]", data[11]));
										lblPayeeType.setText(String.format("[ %s ]", data[12]));
										
										btnEdit.setEnabled(true);
								
										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
					}
					{
						pnlNECenter = new JPanel(new GridLayout(1, 1, 3, 3));
						pnlNorthEast.add(pnlNECenter, BorderLayout.CENTER);
						{
							txtStatID = new _JXTextField("Status ID");
							pnlNECenter.add(txtStatID);
						}
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
							lblStatusName = new JXLabel("Status Name");
							pnlNCLabel.add(lblStatusName);
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
							txtStatusName = new _JXTextField("Status Name");
							pnlNCenter.add(txtStatusName);
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
		cmbStatusType.setEnabled(true);
		txtStatusName.setEditable(true);
		txtStatusAlias.setEditable(true);
		btnState(false, false, true, true);
	}
	
	private void edit(){
		
		cmbStatusType.setEnabled(true);
		txtStatusName.setEditable(true);
		txtStatusAlias.setEditable(true);
		btnState(false, false, true, true);

	private void save() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
			JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();	

			if (to_do.equals("Add")) 
			{
				saving();
				JOptionPane.showMessageDialog(getContentPane(),"New TCT Status/Location was added.","Information",JOptionPane.INFORMATION_MESSAGE);
				cancel();

			} 

			else if (to_do.equals("edit"))
			{				
				//updatePCost(db); 
				updatePCost();
				//db.commit();				
				JOptionPane.showMessageDialog(getContentPane(),"PCost/TCost ID was successfully updated.","Information",JOptionPane.INFORMATION_MESSAGE);
				cancel();
				}			
			}

			else {}	



		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("Welcome");
		if(actionCommand.equals("Add")){
			grpNewEdit.setSelectedButton(e);
			add();
		}
		
		if(actionCommand.equals("Edit")){
			grpNewEdit.setSelectedButton(e);
			edit();
		}
	}
	
	private void saving(){
		
		String status_type = (String) cmbStatusType.getSelectedItem();
		String id = (String) lookupID.getValue();
		String description = (String) txtStatusName.getText();
		String alias = (String) txtStatusAlias.getText();
		
	}
}

				
				
				
					
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				



