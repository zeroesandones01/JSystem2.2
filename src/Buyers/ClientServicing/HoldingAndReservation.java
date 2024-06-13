package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.jdesktop.swingx.JXTextField;

import Admin.AddEditEntityType;
import Database.pgSelect;
import Functions.FncGlobal;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._ButtonGroup;
import components._JInternalFrame;
import interfaces._GUI;

public class HoldingAndReservation extends _JInternalFrame implements ActionListener, _GUI, AncestorListener, InternalFrameListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3239579436521587382L;

	static String title = "Holding and Reservations";
	Dimension frameSize = new Dimension(800, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;

	private _JLookup lookupClient;
	private JXTextField txtClient;

	private JTabbedPane tabCenter;

	private pnlCreateNewEntity pnlCreateNew;

	private pnlHoldingUnit pnlHolding;

	private pnlTemporaryReservation pnlTR;

	private pnlOfficialReservation pnlOR;

	private JPanel pnlSouth;
	private JButton btnEditEntityType;
	private JButton btnPreview;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnView;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnCancel;
	private _ButtonGroup grpNewEdit = new _ButtonGroup();

	public HoldingAndReservation() {
		super(title, true, true, true, true);
		initGUI();
	}

	public HoldingAndReservation(String title) {
		super(title);
		initGUI();
	}

	public HoldingAndReservation(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setMinimumSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		
		this.addInternalFrameListener(new InternalFrameListener() {
			
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameIconified(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				System.out.println("Dumaan dito sa CLosing");
				if(tabCenter.getSelectedIndex() == 1){
					pnlHolding.cancelHolding();
					System.out.println("Tab Selected is 1");
				}
				if(tabCenter.getSelectedIndex() == 2){
					pnlTR.cancelTR();
					System.out.println("Tab Selected is 2");
				}
				
			}
			
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		//disabled due to unnecessary icon on toolbar
		/*Image iconInternalFrame = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/buyers/holdingandreservation.png")).getImage();
		iconInternalFrame = iconInternalFrame.getScaledInstance(19, 19, Image.SCALE_DEFAULT);
		this.setFrameIcon(new ImageIcon(iconInternalFrame));*/
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Client"));
				pnlNorth.setPreferredSize(new Dimension(788, 55));// XXX
				{
					JPanel pnlNorthNorth = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthNorth, BorderLayout.CENTER);
					{
						lookupClient = new _JLookup(null, "Client", 0);
						pnlNorthNorth.add(lookupClient, BorderLayout.WEST);
						lookupClient.setFilterName(true);
						lookupClient.setPrompt("Client ID");
						lookupClient.setLookupSQL(_HoldingAndReservation.client());
						lookupClient.setPreferredSize(new Dimension(150, 0));
						lookupClient.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									txtClient.setText((String) data[1]);
								}
							}
						});
					}
					{
						txtClient = new JXTextField();
						pnlNorthNorth.add(txtClient, BorderLayout.CENTER);
						txtClient.setPrompt("Client Name");
						txtClient.setEditable(false);
					}
				}
			}
			{
				tabCenter = new JTabbedPane();
				pnlMain.add(tabCenter, BorderLayout.CENTER);
				tabCenter.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						JTabbedPane tab = (JTabbedPane) arg0.getSource();
						try {
							lookupClient.setEnabled(tab.getSelectedIndex() != 0);
							txtClient.setEnabled(tab.getSelectedIndex() != 0);
							btnState(true, false, (tab.getSelectedIndex() == 1 || tab.getSelectedIndex() == 2), false, false);
						} catch (NullPointerException e) { }
					}
				});
				//tabCenter.setBorder(lineBorder);
				{
					pnlCreateNew = new pnlCreateNewEntity(new BorderLayout());
					tabCenter.addTab("  Create New Entity  ", null, pnlCreateNew, null);
					pnlCreateNew.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				}
				{
					pnlHolding = new pnlHoldingUnit(this);
					tabCenter.addTab("  Holding Unit  ", null, pnlHolding, null);
				}
				{
					pnlTR = new pnlTemporaryReservation();
					tabCenter.addTab("  Temporary Reservation(TR)  ", null, pnlTR, null);
				}
				{
					pnlOR = new pnlOfficialReservation(this);
					//tabCenter.addTab("  Official Reservation(OR)  ", null, pnlOR, null);
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				// pnlSouth.setBorder(lineBorder);
				pnlSouth.setPreferredSize(new Dimension(500, 30));
				{
					btnEditEntityType = new JButton("Edit Entity Type");
					pnlSouth.add(btnEditEntityType, BorderLayout.WEST);
					btnEditEntityType.setActionCommand("EditEntityType");
					btnEditEntityType.setMnemonic(KeyEvent.VK_T);
					btnEditEntityType.setPreferredSize(new Dimension(150, 30));
					btnEditEntityType.addActionListener(this);
				}
				{
					JPanel pnlNavigation = new JPanel(new GridLayout(1, 4, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setPreferredSize(new Dimension(520, 0));
					{
						btnNew = new JButton("New");
						pnlNavigation.add(btnNew);
						btnNew.setActionCommand("New");
						btnNew.setMnemonic(KeyEvent.VK_N);
						btnNew.addActionListener(this);
						grpNewEdit.add(btnNew);
					}
					{
						btnEdit = new JButton("Edit");
						pnlNavigation.add(btnEdit);
						btnEdit.setActionCommand("Edit");
						btnEdit.setMnemonic(KeyEvent.VK_E);
						btnEdit.addActionListener(this);
						grpNewEdit.add(btnEdit);

						btnEdit.addPropertyChangeListener("enabled", new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent arg0) {
								btnPreview.setEnabled((Boolean) arg0.getNewValue());
								// System.out.printf("Edit: %s (%s, %s)%n",
								// arg0.getPropertyName(),
								// arg0.getOldValue(), arg0.getNewValue());
							}
						});
					}
					{
						btnView = new JButton("View");
						pnlNavigation.add(btnView);
						btnView.setActionCommand("View");
						btnView.setMnemonic(KeyEvent.VK_V);
						btnView.addActionListener(this);
					}
					{
						btnSave = new JButton("Save");
						pnlNavigation.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.setMnemonic(KeyEvent.VK_S);
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.setMnemonic(KeyEvent.VK_C);
						btnCancel.addActionListener(this);
					}
				}
				{
					btnPreview = new JButton("Preview");
					//pnlSouth.add(btnPreview);
					btnPreview.addActionListener(this);
				}
				{
					btnDelete = new JButton("Delete");
					//pnlSouth.add(btnDelete);
					btnDelete.setActionCommand("Delete");
					btnDelete.setMnemonic(KeyEvent.VK_D);
					btnDelete.addActionListener(this);
					btnDelete.setVisible(false);
				}
			}
		}
		{
			JLabel lblStatusBar = new JLabel("* - Required Fields");
			getContentPane().add(lblStatusBar, BorderLayout.SOUTH);
			lblStatusBar.setFont(lblStatusBar.getFont().deriveFont(10.0f));
			lblStatusBar.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		}

		tabCenter.setSelectedIndex(1);
		btnState(true, false, true, false, false);
		pnlCreateNew.setHoldingEnabled(false);
	}

	public void btnState(Boolean sNew, Boolean sEdit, Boolean sView, Boolean sSave, Boolean sCancel) {
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnView.setEnabled(sView);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void selectTab(Boolean new_edit) {
		int selectedTab = tabCenter.getSelectedIndex();
		for(int x=0; x < tabCenter.getTabCount(); x++){
			if(new_edit){
				tabCenter.setEnabledAt(x, x == selectedTab);
			} else {
				tabCenter.setEnabledAt(x, true);
			}
		}
	}

	public void setClientEnabled(boolean enable) {
		panelLooper(pnlNorth, enable);
	}

	public void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				if (comp.getName().equals("scrollPhase")) {
					panelLooper((JScrollPane) comp, enable);
				}
			} else {
				// System.out.printf("Panel: %-50s Component: %s%n",
				// panel.getName(), comp.getName());
				if (comp.getName() != null) {
					/*if (!comp.getName().equals("datePrepared")) {
						if (comp.getName().equals("dateCOC")) {
							comp.setEnabled(chkCOCDate.isEnabled() && chkCOCDate.isSelected());
						} else if (comp.getName().equals("dateCOCExpiry")) {
							comp.setEnabled(chkCOCExpiry.isEnabled() && chkCOCExpiry.isSelected());
						} else {
							comp.setEnabled(!enable);
						}
					}*/
				} else {
					if (panel instanceof JScrollPane) {
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					} else {
						comp.setEnabled(enable);
					}
				}
			}
		}
	}

	@Override
	public void ancestorAdded(AncestorEvent arg0) {
		lookupClient.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent arg0) { }

	@Override
	public void ancestorRemoved(AncestorEvent arg0) { }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();
		int selectedTab = tabCenter.getSelectedIndex();

		if(action.equals("EditEntityType")){
			if(lookupClient.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client.", "New", JOptionPane.WARNING_MESSAGE);
			}else{
				if(FncGlobal.homeMDI.isNotExisting("AddEditEntityType")){
					AddEditEntityType add_edit_entity_type = new AddEditEntityType(lookupClient.getValue());
					Home_JSystem.addWindow(add_edit_entity_type);
				}
			}
		}

		//XXX NEW
		if(action.equals("New")) {
			//pgSelect db = new pgSelect();
			//db.select("SELECT jobs_nullify_holding();", "Client Sequence Number", true, true);

			Boolean isNew = false;

			if(lookupClient.getValue() == null && selectedTab > 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client.", "New", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if(selectedTab == 0){
				isNew = true;
				grpNewEdit.setSelectedButton(arg0);
				pnlCreateNew.setHoldingEnabled(true);
				setClientEnabled(false);
			}
			if(selectedTab == 1){
				isNew = true;
				pnlHolding.clearFields();
				pnlHolding.setEntityID(lookupClient.getValue());
				pnlHolding.newHolding(false);
				grpNewEdit.setSelectedButton(arg0);
			}
			if(selectedTab == 2){
				if(pnlTR.validateClient(lookupClient.getValue())){
					isNew = pnlTR.newTR(lookupClient.getValue(), txtClient.getText());
					
					if(isNew){
						grpNewEdit.setSelectedButton(arg0);
					}
				}
			}
			if(selectedTab == 3){
				isNew = true;
				pnlOR.newOR(lookupClient.getValue(), txtClient.getText());
			}

			if(isNew){
				selectTab(true);
				if(selectedTab > 0){
					lookupClient.setEditable(false);
				}
				btnState(false, false, false, true, true);
			}
		}

		if(action.equals("Edit")) {
			grpNewEdit.setSelectedButton(arg0);
			//Holding Unit
			if(selectedTab == 1){
				pnlHolding.editHolding();
				lookupClient.setEditable(true);
				btnState(false, false, false, true, true);
			}

			//Temporary Reservation
			if(selectedTab == 2){
				pnlTR.edit();
				lookupClient.setEditable(true);
				btnState(false, false, false, true, true);
			}
		}

		if(action.equals("View")) {
			if(lookupClient.getValue() == null && selectedTab > 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client.", "New", JOptionPane.WARNING_MESSAGE);
				return;
			}

			//Holding Unit
			if(selectedTab == 1){
				if(pnlHolding.view(lookupClient.getValue())){
					//selectTab(false);
					//lookupClient.setEditable(true);
					//btnState(true, false, true, false, true);
				}else{

				}
			}

			//Temporary Reservation
			if(selectedTab == 2){
				if(pnlTR.view(lookupClient.getValue())){
					btnState(true, true, true, false, false);
				}else{
					btnState(true, false, true, false, false);
				}
			}
		}

		//XXX SAVE
		if(action.equals("Save")) {

			//Create New Entity
			if(selectedTab == 0){
				if(pnlCreateNew.getSelectedEntityType() == 0){ //INDIVIDUAL
					if(pnlCreateNew.save()){
						Object[] options = new Object[]{"Yes", "No"};

						int option = JOptionPane.showOptionDialog(this.getTopLevelAncestor(), "Proceed to Holding Units", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

						if(option == 0){
							pgSelect db = new pgSelect();
							db.select("select entity_id, entity_name, date_created from rf_entity where date_created is not null order by date_created desc;");
							if(db.isNotNull()){
								lookupClient.setValue((String) db.getResult()[0][0]);
								txtClient.setText((String) db.getResult()[0][1]);

								pnlCreateNew.setHoldingEnabled(false);
								setClientEnabled(true);

								tabCenter.setSelectedIndex(1);
								pnlHolding.newHolding(false);
								selectTab(true);
								btnState(false, false, false, true, true);								
							}
						}
					}
				}else{ // CORPORATION
					if(pnlCreateNew.saveNewCorporation()){

					}
				}
			}

			//Holding Unit
			if(selectedTab == 1){
				if(pnlHolding.saveHolding(lookupClient.getValue())){
					selectTab(false);
					lookupClient.setEditable(true);
					btnState(true, false, true, false, false);
				}
			}

			//Temporary Reservation (TR)
			if(selectedTab == 2){
				if(pnlTR.toSave()){
					/*if(pnlTR.checkDocuments(lookupClient.getValue())){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please submit required documents.", action, JOptionPane.WARNING_MESSAGE);
						pnlTR.displayPopup(lookupClient.getValue());
						return;
					}*/

					if(pnlTR.saveTR(lookupClient.getValue(), txtClient.getText())){
						selectTab(false);
						lookupClient.setEditable(true);
						btnState(true, true, true, false, false);
					}
				}
			}

			//Official Reservation (OR)
			if(selectedTab == 3){
				if(pnlOR.toSave()){
					if(pnlOR.saveOR(lookupClient.getValue(), txtClient.getText())){
						selectTab(false);
						lookupClient.setEditable(true);
						btnState(true, false, true, false, false);
					}
				}
			}
			grpNewEdit.clearSelection();
		}

		//XXX CANCEL
		if(action.equals("Cancel")) {
			grpNewEdit.clearSelection();
			if(selectedTab == 0){
				pnlCreateNew.setHoldingEnabled(false);
			}
			if(selectedTab == 1){
				pnlHolding.cancelHolding();
			}
			if(selectedTab == 2){
				pnlTR.cancelTR();
			}
			if(selectedTab == 3){
				pnlOR.cancelOR();
			}
			setClientEnabled(selectedTab > 0);
			selectTab(false);
			if(selectedTab > 0){
				lookupClient.setEditable(selectedTab > 0);
			}

			btnState(true, false, (selectedTab == 1 || selectedTab == 2), false, false);
		}
	}


}
