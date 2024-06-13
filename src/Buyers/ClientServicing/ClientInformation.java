package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.codec.binary.Base64;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Dialogs.dlg_ImageViewer;
import Dialogs.dlg_MiddleInitial;
import Functions.FncGlobal;
import Functions.FncImageFileChooser;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._ButtonGroup;
import components._JInternalFrame;
import components._JTabbedPane;

public class ClientInformation extends _JInternalFrame implements ActionListener, AncestorListener {

	private static final long serialVersionUID = -279616837577539446L;
	static Dimension SIZE = new Dimension(1000, 615);

	//private 
	static String title = "Client Information";
	private Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);

	private static _JTabbedPane tabCenter;
	public static pnlClientInformation pnlCI;
	public static ClientSubmittedID pnlSubmittedID;
	public static pnlAddress pnlADDRESS;
	public static pnlConnection pnlConnect;
	public static pnlDependents pnlDepend;
	public static pnlWorkExperience pnlWorkExp;
	public static pnlFinancialInfo pnlFinanceInfo;
	public static pnlReferencesOther pnlRefOther;
	private JPanel pnlPicture;
	private JPanel pnlSignature;
	//public static pnlSubmittedID 

	private JPanel pnlEast;
	public static _JLookup lookupClient;
	private JLabel lblPicture;
	private JLabel lblSignature;

	private JPanel pnlEastSouth;
	private _ButtonGroup grpNewEdit = new _ButtonGroup();
	public static JButton btnNew;
	private static JButton btnEdit;
	private static JButton btnDelete;
	private static JButton btnSave;
	private static JButton btnCancel;
	public String entity_id = null;
	private JFileChooser fileChooser;
	private String client_picture;
	protected FncImageFileChooser lblClientImageFileChooser;
	protected FncImageFileChooser lblClientSignatureFileChooser;
	
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
	Timer timerStatus = null;
	boolean blinkState = false;
	
	//protected byte[] imgByte = null;

	public ClientInformation() {
		super(title, true, true, true, true);
		initGUI();
	}
	public ClientInformation(String title, String entity_id, String entity_name) { //CHARD
		super(title, true, true, true, true);
		this.entity_id = entity_id;
		initGUI();
		displayClientInfo(entity_id, entity_name);
	}

	public ClientInformation(String title) {
		super(title, true, true, true, true);
		initGUI();
	}
	
	public ClientInformation(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	private void initGUI() {
		this.setTitle(title);
		this.setPrimaryTitle(title);
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.addAncestorListener(this);
		this.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent arg0) {
				if(timerStatus != null){
					timerStatus.stop();
				}
			}

			public void internalFrameClosed(InternalFrameEvent arg0) {
				//System.out.println("internalFrameClosed");
				executor.shutdown();
				if(timerStatus != null){
					timerStatus.stop();
				}
			}
		});

		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			JXPanel pnlNorth = new JXPanel();
			//pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(LINE_BORDER);
			pnlNorth.setPreferredSize(new Dimension(778, 100));
		}
		{
			pnlEast = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlEast, BorderLayout.EAST);
			pnlEast.setLayout(new SpringLayout());
			pnlEast.setBorder(JTBorderFactory.createTitleBorder("Client"));
			pnlEast.setPreferredSize(new Dimension(170, 571));
			{
				lookupClient = new _JLookup(null, "Client", 0);
				//lookupClient = new _Jloo
				pnlEast.add(lookupClient, BorderLayout.NORTH);
				lookupClient.setLookupSQL(sqlClients());
				lookupClient.setPreferredSize(new Dimension(170, 30));
				lookupClient.setFilterName(true);
				lookupClient.addLookupListener(new LookupListener() {
					public void lookupPerformed(LookupEvent event) {
						Object[] data = ((_JLookup) event.getSource()).getDataSet();
						
						FncSystem.out("Display SQL for Client", lookupClient.getLookupSQL());
						if(data != null){
							entity_id = (String) data[0];
							System.out.printf("Display value of entity_id: %s%n", entity_id);
							String entity_name = (String) data[1];
							
							//CLEARS THE FIELDS WHEN SELECTING NEW CLIENT
							
							pnlCI.clearCIFields();
							
							pnlADDRESS.clearAddressFields();
							pnlConnect.clearConnectionFields();
							pnlWorkExp.clearFieldsWorkExp();
							pnlDepend.clearDependentFields();
							pnlFinanceInfo.clearFields();
							pnlRefOther.clearRefOther();
							
							//ClientInformation.this.setComponentsClear(pnlCI, "");
							//pnlRefOther.cl

							//DISPLAYS THE DETAILS AFTER CLEARING FOR THE SELECTED ENTITY
							displayClientInformation(entity_id);
							displaySubmittedID(entity_id);
							displayConnectionList(entity_id);
							displayAddressList(entity_id);
							displayWorkExpList(entity_id);
							displayDependentList(entity_id);
							displayReferencesOther(entity_id);
							displayFinancialInfo(entity_id);
							
							try {
							displayClientImage(lblClientImageFileChooser, entity_id);
							displayClientSignature(lblClientSignatureFileChooser, entity_id);
							} catch (IOException e) {
								e.printStackTrace();
							} /*catch (OutOfMemoryError er){
								JOptionPane.showMessageDialog(getContentPane(), "Out of memory.", "Loading Image.", JOptionPane.WARNING_MESSAGE);
							}*/

							//ClientInformation.this.setTitle("Client Information - "+entity_name+"");
							
							setSecondaryTitle(entity_name);
							startTimerStatus();
							
							//Commented for Easier Editing of All Tabs
							/*if(UserInfo.Department.equals("52")){
								pnlState(false, false, true, true, true, true, true, true);
							}else{
								pnlState(true, true, false, false, false, false, false, false);
							}*/
							
							pnlState(true, true, true, true, true, true, true, true);
							
							Integer selectedTab = tabCenter.getSelectedIndex();
							
							if(selectedTab == 0){//Enabling of buttons for the Client Info Panel 
								btnState(true, true, false, false, false);
							}
							
							if(selectedTab == 1){//Enabling of buttons for Submitted ID
								//btnState(true, false, false, false, false); //Creation of new Entity in Holding and Reservation
								btnState(true, true, false, false, false); //Creation of New Entity In 
							}
							if(selectedTab == 2){ // Enabling of buttons for the Address Panel
								
								btnState(true, false, false, false, false);
							}
							if(selectedTab == 3){ //Enabling of buttons for the Connections Panel
								btnState(true, false, false, false, false);
							}
							if (selectedTab == 4){ //Enabling of buttons for the Dependents Panel
								btnState(true, false, false, false, false);
							}
							if (selectedTab == 5){ //Enabling of buttons for the Work Exp Panel
								btnState(true, false, false, false, false);
							}
							if (selectedTab == 6){// Enabling of buttons for the Financial Info Panel
								btnState(false, true, false, false, false);
							}
							if (selectedTab == 7){//Enabling of buttons for the References/Other Info Panel
								btnState(true, false, false, false, false);
							}
							
							if(canEdit() == false){
								btnState(false, false, false, false, false);
							}
						}
					}
				});
			}
			{
				JPanel pnlSeparator3 = new JPanel(new BorderLayout(5, 5));
				pnlEast.add(pnlSeparator3);
				pnlSeparator3.setPreferredSize(new Dimension(170, 50));
			}
			{
				pnlPicture = new JPanel(new BorderLayout(5, 5));
				pnlEast.add(pnlPicture);
				pnlPicture.setLayout(new BorderLayout(5, 5));
				pnlPicture.setBorder(LINE_BORDER);
				pnlPicture.setPreferredSize(new Dimension(170, 150));
				{
					lblClientImageFileChooser = new FncImageFileChooser(145, 150);
					pnlPicture.add(lblClientImageFileChooser);
					lblClientImageFileChooser.setClickable(false);
					lblClientImageFileChooser.setDefaultClientImage();
					lblClientImageFileChooser.addMouseListener(new MouseListener() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mouseClicked(MouseEvent e) {
							if(lblClientImageFileChooser.isClickable() == false && lookupClient.getValue() != null){
								if(e.getClickCount() >= 2){
									try{
									dlg_ImageViewer img_dlg = new dlg_ImageViewer(FncGlobal.homeMDI, "Client Image", displayClientImage(lblClientImageFileChooser, lookupClient.getValue()), new Dimension(500, 500));
									img_dlg.setLocationRelativeTo(null);
									img_dlg.setVisible(true);
									
									
									} catch (IOException a) {
										a.printStackTrace();
									} catch (OutOfMemoryError er){
										JOptionPane.showMessageDialog(getContentPane(), "Out of memory.", "Loading Image.", JOptionPane.WARNING_MESSAGE);
								
									}
								}
							}
							
							if(lblClientImageFileChooser.isClickable()){
								if(e.getClickCount() >= 2 && lblClientImageFileChooser.getIsOk()){
									
								}
							}
						}
					});
				}
			}
			{
				lblPicture = new JLabel("Picture" , JLabel.CENTER);
				pnlEast.add(lblPicture);
			}
			{
				JPanel pnlSeparator2 = new JPanel(new BorderLayout(5, 5));
				pnlEast.add(pnlSeparator2);
			}
			{
				pnlSignature = new JPanel();
				pnlEast.add(pnlSignature);
				pnlSignature.setLayout(new BorderLayout(5, 5));
				pnlSignature.setBorder(LINE_BORDER);
				pnlSignature.setPreferredSize(new Dimension(170, 30));
				{
					lblClientSignatureFileChooser = new FncImageFileChooser(145, 30);
					pnlSignature.add(lblClientSignatureFileChooser);
					lblClientSignatureFileChooser.setClickable(false);
					lblClientSignatureFileChooser.setDefaultClientSignature();
					lblClientSignatureFileChooser.addMouseListener(new MouseListener() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void mouseClicked(MouseEvent e) {
							if(lblClientSignatureFileChooser.isClickable() == false && lookupClient.getValue() != null){
								if(e.getClickCount() >= 2){
									try{
									dlg_ImageViewer img_dlg = new dlg_ImageViewer(FncGlobal.homeMDI, "Client Sginature", displayClientSignature(lblClientSignatureFileChooser, lookupClient.getValue()), new Dimension(500, 500));
									img_dlg.setLocationRelativeTo(null);
									img_dlg.setVisible(true);
									
									} catch (IOException a) {
										a.printStackTrace();
									} catch (OutOfMemoryError er){
										JOptionPane.showMessageDialog(getContentPane(), "Out of memory.", "Loading Image.", JOptionPane.WARNING_MESSAGE);
								
									}
								}
							}
							
							if(lblClientSignatureFileChooser.isClickable()){
								if(e.getClickCount() >= 2 && lblClientSignatureFileChooser.getIsOk()){
									
								}
							}
						}
					});
				}
			}
			{
				lblSignature = new JLabel("Signature", JLabel.CENTER);
				pnlEast.add(lblSignature);
			}
			{
				JPanel pnlSeparator = new JPanel();
				pnlEast.add(pnlSeparator);
			}
			{
				pnlEastSouth = new JXPanel();
				pnlEast.add(pnlEastSouth, BorderLayout.SOUTH);
				pnlEastSouth.setLayout(new GridLayout(5, 1, 0, 5));
				//pnlEastSouth.setBorder(LINE_BORDER);
				pnlEastSouth.setPreferredSize(new Dimension(200, 170));
				{
					btnNew = new JButton("New");
					pnlEastSouth.add(btnNew);
					btnNew.setActionCommand(btnNew.getText());
					btnNew.setMnemonic(KeyEvent.VK_N);
					btnNew.addActionListener(this);
					grpNewEdit.add(btnNew);
				}
				{
					btnEdit = new JButton("Edit");
					pnlEastSouth.add(btnEdit);
					btnEdit.setActionCommand(btnEdit.getText());
					btnEdit.setMnemonic(KeyEvent.VK_E);
					btnEdit.addActionListener(this);
					grpNewEdit.add(btnEdit);
				}
				{
					btnDelete = new JButton("Delete");
					pnlEastSouth.add(btnDelete);
					btnDelete.setActionCommand(btnDelete.getText());
					btnDelete.setMnemonic(KeyEvent.VK_D);
					btnDelete.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlEastSouth.add(btnSave);
					btnSave.setActionCommand(btnSave.getText());
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlEastSouth.add(btnCancel);
					btnCancel.setActionCommand(btnCancel.getText());
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
			}
			SpringUtilities.makeCompactGrid(pnlEast, 9, 1, 3, 3, 3, 3, false);
		}
		{
			tabCenter = new _JTabbedPane();
			pnlMain.add(tabCenter, BorderLayout.CENTER);
			{
				pnlCI = new pnlClientInformation(this);
				tabCenter.addTab("Client Information", pnlCI);
			}
			{
				pnlSubmittedID = new ClientSubmittedID(this);
				tabCenter.addTab("Submitted ID", pnlSubmittedID);
			}
			{
				pnlADDRESS = new pnlAddress(this);
				tabCenter.addTab("Address", pnlADDRESS);
			}
			{
				pnlConnect = new pnlConnection(this);
				tabCenter.addTab("Connections", pnlConnect);
			}
			{
				pnlDepend = new pnlDependents(this);
				tabCenter.addTab("Dependents", pnlDepend);
			}
			{
				pnlWorkExp = new pnlWorkExperience(this);
				tabCenter.addTab("Work Experience", pnlWorkExp);
			}
			{
				pnlFinanceInfo = new pnlFinancialInfo(this);
				tabCenter.addTab("Financial Info", pnlFinanceInfo);
			}
			{
				pnlRefOther = new pnlReferencesOther(this);
				tabCenter.addTab("References/Other Info", pnlRefOther);
			}
			
			tabCenter.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					int selectedTab = ((_JTabbedPane)arg0.getSource()).getSelectedIndex();

					if(selectedTab == 0){ // Enabling of buttons for the Client Info Panel
						btnState(true, true, false, false, false);
					}
					if(selectedTab == 1){//Enabling of buttons for the Submitted ID Panel
						pnlSubmittedID.clearSubmittedID();
						btnState(true, false, false, false, false);
					}
					if(selectedTab == 2){ // Enabling of buttons for the Address Panel
						pnlADDRESS.clearAddressFields();
						btnState(true, false, false, false, false);
					}
					if(selectedTab == 3){ //Enabling of buttons for the Connections Panel
						pnlConnect.clearConnectionFields();
						btnState(true, false, false, false, false);
					}
					if (selectedTab == 4){ //Enabling of buttons for the Dependents Panel
						pnlDepend.clearDependentFields();
						btnState(true, false, false, false, false);
					}
					if (selectedTab == 5){ //Enabling of buttons for the Work Exp Panel
						pnlWorkExp.clearFieldsWorkExp();
						displayWorkExpList(lookupClient.getValue());
						btnState(true, false, false, false, false);
					}
					if (selectedTab == 6){// Enabling of buttons for the Financial Info Panel
						
						btnState(false, true, false, false, false);
					}
					if (selectedTab == 7){//Enabling of buttons for the References/Other Info Panel
						pnlRefOther.clearRefOther();
						btnState(true, false, false, false, false);
					}
					
					if(canEdit() == false){
						btnState(false, false, false, false, false);
					}
				}
			});
		}
		btnState(true, false, false, false, false);
		if(canEdit() == false){
			btnState(false, false, false, false, false);
		}
		pnlState(false, false, false, false, false, false, false, false);
		
	}//XXX End of initGUI

	//SETS THE BUTTONSTATE OF THE CLIENT INFORMATION
	public static void btnState(Boolean sNew, Boolean sEdit, Boolean sDelete, Boolean sSave, Boolean sCancel) {
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnDelete.setEnabled(sDelete);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}
	//SET THE PANEL STATE WHEN EDITING OR CREATING NEW DATA
	public static void pnlState(Boolean CI, Boolean sub_id ,Boolean addr, Boolean conn, Boolean dpend, Boolean FI, Boolean RO, Boolean WE){
		tabCenter.setEnabledAt(0, CI);
		tabCenter.setEnabledAt(1, sub_id);
		tabCenter.setEnabledAt(2, addr);
		tabCenter.setEnabledAt(3, conn);
		tabCenter.setEnabledAt(4, dpend);
		tabCenter.setEnabledAt(5, WE);
		tabCenter.setEnabledAt(6, FI);
		tabCenter.setEnabledAt(7, RO);
	}

	public String getEntityID(){
		String client_id = entity_id;
		return client_id;
	}

	//SQL FPR THE CLIENTS
	private String sqlClients() {
		String sql = "SELECT\n" + 
				"trim(a.entity_id) as \"ID\",\n" + 
				"trim(a.entity_name) as \"Client Name\",\n" + 
				"trim(a.entity_kind) as \"Entity Kind\",\n" + 
				"date(a.date_of_birth) as \"Birthday\",\n" + 
				"date(a.date_created) as \"Date Created\"\n" + 
				"FROM rf_entity a\n" + 
				//"WHERE date_part('year', date_created) >= 2014\n" +
				"WHERE a.status_id = 'A' \n"+
				"AND (CASE WHEN ('"+UserInfo.Division+"' IN ('04', '06', '07', '08', '09', '29') AND '"+UserInfo.EmployeeCode+"' NOT IN ('900965', '900606', '900383', '900028')) THEN \n"+
				"     EXISTS (SELECT * FROM rf_entity_assigned_type WHERE entity_id = a.entity_id AND entity_type_id = '01' AND status_id = 'A') \n"+
				"	  ELSE TRUE END)"+
				"GROUP BY trim(a.entity_id), trim(a.entity_name), trim(a.entity_kind), date(a.date_of_birth), date(a.date_created)\n" + 
				"ORDER BY trim(a.entity_name);";
		return sql;
	}
	
	public void startTimerStatus(){
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				blinkState = !blinkState;

				if(blinkState){
					
					ClientInformation.this.setTitle(String.format("%s - %s", title, getSecondaryTitle()));
					//System.out.printf("********************************************************************** %s (%s)%n", getSecondaryTitle(), timerStatus.getActionCommand());
				}else{
					ClientInformation.this.setTitle(getPrimaryTitle());
				}
			}
		};

		if(timerStatus == null){
			System.out.println("********** DUMAAN! #1");
			timerStatus = new Timer(1000, action);
			timerStatus.setActionCommand(getSecondaryTitle());
			timerStatus.start();
		}else{
			System.out.println("********** DUMAAN! #2");
			timerStatus.stop();
			timerStatus.restart();
		}
	}

	//DIPLAYS THE DETAILS IN CLIENT INFORMATION BASED ON THE SELECTED TAB
	public static void displayClientInformation(String entity_id) {
		pnlCI.displayDetails(entity_id);
	}
	
	public static void displaySubmittedID(String entity_id){
		pnlSubmittedID.displaySubmittedID(entity_id);
	}

	public static void displayConnectionList(String entity_id) {
		pnlConnect.displayConnectionList(entity_id);
	}

	public static void displayAddressList(String entity_id) {
		//pnlADDRESS.displayAddressList(entity_id);
		pnlADDRESS.displayAddress(entity_id);
	}

	public static void displayWorkExpList(String entity_id){
		pnlWorkExp.displayWorkExp(entity_id);
	}

	public static void displayDependentList(String entity_id){
		pnlDepend.displayDependentList(entity_id);
	}

	public static void displayReferencesOther(String entity_id){
		pnlRefOther.displayRefOther(entity_id);
	}
	public static void displayFinancialInfo(String entity_id){
		pnlFinanceInfo.displayFinancialInfo(entity_id);
	}
	
	/*private void displayImages(String entity_id){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT picture FROM rf_complain_accomplishment where entity_id = '"+entity_id+"'";
		db.select(SQL);
		
		FncSystem.out("Display sql for Picture of client", SQL);
	}*/
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		Integer selectedTab = tabCenter.getSelectedIndex();

		if(actionCommand.equals("New")){
			//pnlCI.setClientInformationEnabled(true);
			grpNewEdit.setSelectedButton(e);
			
			if(selectedTab == 0){
				lookupClient.setValue(getNewEntityID());
				lblClientImageFileChooser.setClickable(true);
				lblClientSignatureFileChooser.setClickable(true);
				
				entity_id = lookupClient.getValue();
				lblClientImageFileChooser.setDefaultClientImage();
				lblClientSignatureFileChooser.setDefaultImage();
				pnlCI.newCI(lookupClient.getValue());
				
				if(timerStatus != null){
					timerStatus.stop();
				}
				ClientInformation.this.setTitle(title);
				
				//Commented on 2016-09-02
				displayClientInformation(lookupClient.getValue());
				displaySubmittedID(lookupClient.getValue());
				displayConnectionList(lookupClient.getValue());
				displayAddressList(lookupClient.getValue());
				displayWorkExpList(lookupClient.getValue());
				displayDependentList(lookupClient.getValue());
				displayReferencesOther(lookupClient.getValue());
				displayFinancialInfo(lookupClient.getValue());
				//pnlCI.clearIndividualCI();
				pnlState(true, false, false, false, false, false, false, false);
			}
			
			if(selectedTab == 1){
				pnlSubmittedID.newSubmittedID();
				pnlState(false, true, false, false, false, false, false, false);
			}
			if (selectedTab == 2){
				pnlADDRESS.newAddress();
				pnlState(false, false, true, false, false, false, false, false);
			}
			if (selectedTab == 3){
				pnlConnect.newConnection();
				pnlState(false, false, false, true, false, false, false, false);
			}
			if (selectedTab == 4){
				pnlDepend.newDependent();
				pnlState(false, false, false, false, true, false, false, false);
			}
			if (selectedTab == 5){
				pnlWorkExp.newWorkExp();
				pnlState(false, false, false, false, false, false, false, true);
			}
			if (selectedTab == 7){
				pnlRefOther.newRefother();
				pnlState(false, false, false, false, false, false, true, false);
			}
			
			lookupClient.setEditable(false);
			btnState(false, false, false, true, true);
		}
		
		if(actionCommand.equals("Edit")){//EDITING FOR THE CLIENT INFORMATION
			
			if (selectedTab == 0){
				if(pnlCI.canEdit(lookupClient.getValue())){
					pnlCI.editCI(lookupClient.getValue());
					lblClientImageFileChooser.setClickable(true);
					lblClientSignatureFileChooser.setClickable(true);
					pnlState(true, false, false, false, false, false, false, false);
					grpNewEdit.setSelectedButton(e);
					lookupClient.setEditable(false);
					btnState(false, false, false, true, true);
					//System.out.println("Dumaan dito sa edit ng Client Info");
				}else{
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Sorry you are not authorized to edit this entry", "Edit", JOptionPane.WARNING_MESSAGE);
				}
			}
			
			if(selectedTab == 1){
				pnlSubmittedID.editSubmittedID();
				pnlState(false, true, false, false, false, false, false, false);
				btnState(false, false, false, true, true);
				grpNewEdit.setSelectedButton(e);
				lookupClient.setEditable(false);
			}
			
			if(selectedTab == 2){
				pnlADDRESS.editAddress();
				pnlState(false, false, true, false, false, false, false, false);
				btnState(false, false, false, true, true);
				grpNewEdit.setSelectedButton(e);
				lookupClient.setEditable(false);
			}
			
			if(selectedTab == 3){
				pnlConnect.editConnection();
				pnlState(false, false, false, true, false, false, false, false);
				btnState(false, false, false, true, true);
				grpNewEdit.setSelectedButton(e);
				lookupClient.setEditable(false);
			}
			
			if(selectedTab == 4){
				pnlDepend.editDependet();
				pnlState(false, false, false, false, true, false, false, false);
				btnState(false, false, false, true, true);
				grpNewEdit.setSelectedButton(e);
				lookupClient.setEditable(false);
			}
			
			if(selectedTab == 5){
				pnlWorkExp.editWorkExp();
				pnlState(false, false, false, false, false, false, false, true);
				btnState(false, false, false, true, true);
				grpNewEdit.setSelectedButton(e);
				lookupClient.setEditable(false);
			}
			
			if (selectedTab == 6){
				pnlFinanceInfo.editFinancialInfo();
				pnlState(false, false, false, false, false, true, false, false);
				btnState(false, false, false, true, true);
				grpNewEdit.setSelectedButton(e);
				lookupClient.setEditable(false);
			}
			
			if(selectedTab == 7){
				pnlRefOther.edit(lookupClient.getValue());
				pnlState(false, false, false, false, false, false, true, false);
				btnState(false, false, false, true, true);
				grpNewEdit.setSelectedButton(e);
				lookupClient.setEditable(false);
			}
			
		}
		
		if(actionCommand.equals("Delete")){//DELETE FOR THE CLIENT INFORMATION
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Delete this entry?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				if(tabCenter.getSelectedIndex() == 2){
					if(pnlADDRESS.deleteAddress(lookupClient.getValue())){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Address has been Updated.", actionCommand, JOptionPane.INFORMATION_MESSAGE);
						displayAddressList(lookupClient.getValue());
					}
				}
				if(tabCenter.getSelectedIndex() == 3){
					if(pnlConnect.deleteConnection(lookupClient.getValue())){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Connection has been Updated.", actionCommand, JOptionPane.INFORMATION_MESSAGE);
						displayConnectionList(lookupClient.getValue());
					}
				}
			}
		}
		
		if(actionCommand.equals("Save")){//SAVING FOR THE CLIENT INFORMATION
			if (tabCenter.getSelectedIndex() == 0){
				//if (pnlCI.checkRequiredFields()){
					if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						if (pnlCI.saveCI_Info(lookupClient.getValue()) && pnlCI.saveEntityTypes(lookupClient.getValue())){
							//pnlCI.saveCI(lookupClient.getValue());
							
							//pnlCI.saveEntityTypes(lookupClient.getValue());
							saveImage(lblClientImageFileChooser.getImageByte(), lblClientSignatureFileChooser.getImageByte() ,lookupClient.getValue());
							lblClientImageFileChooser.setClickable(false);
							lblClientSignatureFileChooser.setClickable(false);
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Information has been Saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
							displayClientInformation(lookupClient.getValue());
							pnlCI.disableComponents();
							btnState(true, true, false, false, false);
							pnlState(true, true, true, true, true, true, true, true);
							pnlCI.cancelCI(lookupClient.getValue());
							
							lookupClient.setEditable(true);
						}
					}
				//}
			}
			
			if(tabCenter.getSelectedIndex() == 1){
				if(pnlSubmittedID.saveSubmittedID(lookupClient.getValue())){
					pnlSubmittedID.cancelSubmittedID();
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Submitted ID has been saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
					displaySubmittedID(lookupClient.getValue());
					btnState(true, false, false, false, false);
					pnlState(true, true, true, true, true, true, true, true);

					lookupClient.setEditable(true);
				}
			}
			
			if (tabCenter.getSelectedIndex() == 2){//SAVING FOR THE ADDRESS PANEL
				if(pnlADDRESS.toSave()){
					/*if (pnlADDRESS.saveAddress(lookupClient.getValue())){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Address has been Saved.", actionCommand, JOptionPane.INFORMATION_MESSAGE);
						displayAddressList(lookupClient.getValue());
						btnState(true, false, false, false, false);
						pnlState(true, true, true, true, true, true, true, true);
						
						lookupClient.setEditable(true);
					}*/
					
					if(pnlADDRESS.saveAddress2(lookupClient.getValue())){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Address has been Saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
						displayAddressList(lookupClient.getValue());
						btnState(true, false, false, false, false);
						pnlState(true, true, true, true, true, true, true, true);
						
						lookupClient.setEditable(true);
					}
				}
			}
			
			if(tabCenter.getSelectedIndex() == 3){

				if(pnlConnect.saveClientConnection(lookupClient.getValue())){
					//pnlConnect.saveConnectionSignature();
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Connection Saved", actionCommand, JOptionPane.INFORMATION_MESSAGE);
					displayConnectionList(lookupClient.getValue());
					btnState(true, false, false, false, false);
					pnlState(true, true, true, true, true, true, true, true);

					lookupClient.setEditable(true);
				}
			}

			if (tabCenter.getSelectedIndex() == 4){//SAVING FOT THE DEPENDENTS PANEL
				if(pnlDepend.tosave()){
					if(pnlDepend.saveDependent(lookupClient.getValue())){
						displayDependentList(lookupClient.getValue());
						btnState(true, false, false, false, false);
						pnlState(true, true, true, true, true, true, true, true);

						lookupClient.setEditable(true);
					}
				}
			}
			
			if (tabCenter.getSelectedIndex() == 5){//SAVING FOR THE WORK EXPERIENCE PANEL
				if (pnlWorkExp.toSave()){
					if (pnlWorkExp.saveWorkExp(lookupClient.getValue())){
						displayWorkExpList(lookupClient.getValue());
						btnState(true, false, false, false, false);
						pnlState(true, true, true, true, true, true, true, true);
						
						lookupClient.setEditable(true);
					}
				}
			}
			
			if (tabCenter.getSelectedIndex() == 6){//SAVING FOR THE FINANCIAL INFO PANEL
				if(pnlFinanceInfo.saveFinancial(lookupClient.getValue())){
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Financial Info has been Updated.", actionCommand, JOptionPane.INFORMATION_MESSAGE);
					displayFinancialInfo(lookupClient.getValue());
					btnState(false, true, false, false, false);
					pnlState(true, true, true, true, true, true, true, true);
					
					lookupClient.setEditable(true);
				}
			}
			
			if (tabCenter.getSelectedIndex() == 7){//SAVING FOR THE REFERENCES OTHER PANEL
				pnlRefOther.save(lookupClient.getValue());
				displayReferencesOther(lookupClient.getValue());
				pnlState(true, true, true, true, true, true, true, true);
				
				lookupClient.setEditable(true);
			}
			
			grpNewEdit.clearSelection();
		}
		
		if(actionCommand.equals("Cancel")){//CANCELATION FOR THE CLIENT INFO
			pnlCI.setClientInformationEnabled(false);
			
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Cancel Editing?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				lookupClient.setEditable(true);
				
				if (selectedTab == 0){
					if(entityExisting(lookupClient.getValue()) == false){
						lookupClient.setValue(null);
						btnState(true, false, false, false, false);
					}else{
						btnState(true, true, false, false, false);
					}
					
					pnlCI.cancelCI(lookupClient.getValue());
					lblClientImageFileChooser.setClickable(false);
					lblClientSignatureFileChooser.setClickable(false);
					
					try {
						displayClientImage(lblClientImageFileChooser, lookupClient.getValue());
						displayClientSignature(lblClientSignatureFileChooser, lookupClient.getValue());
						} catch (IOException a) {
							a.printStackTrace();
						} catch (OutOfMemoryError er){
							JOptionPane.showMessageDialog(getContentPane(), "Out of memory.", "Loading Image.", JOptionPane.WARNING_MESSAGE);
						}
					//displayClientImage(lblClientImageFileChooser, actionCommand);
					
				}
				
				if(selectedTab == 1){
					pnlSubmittedID.cancelSubmittedID();
					btnState(true, false, false, false, false);
				}
				if (selectedTab == 2){
					pnlADDRESS.cancelAddress();
					btnState(true, false, false, false, false);
				}
				if (selectedTab == 3){
					pnlConnect.cancelConnection();
					btnState(true, false, false, false, false);
				}
				if (selectedTab == 4){
					pnlDepend.cancelDependents();
					btnState(true, false, false, false, false);
				}
				if (selectedTab == 5){
					pnlWorkExp.cancelWorkexp();
					displayWorkExpList(lookupClient.getValue());
					btnState(true, false, false, false, false);
				}
				
				if (selectedTab == 6){
					pnlFinanceInfo.cancelFinancialInfo();
					btnState(false, true, false, false, false);
					displayFinancialInfo(lookupClient.getValue());
				}
				
				if (selectedTab == 7){
					pnlRefOther.cancelRefOther();
				}
				
				if(lookupClient.getValue() == null){
					pnlState(false, false, false, false, false, false, false, false);
				}else{
					pnlState(true, true, true, true, true, true, true, true);
				}
				
				grpNewEdit.clearSelection();
			}
		}
	}
	
	public void displayClientInfo(String _entity_id, String entity_name) { //CHARD
		
		displayClientInformation(_entity_id);
		displaySubmittedID(_entity_id);
		displayConnectionList(_entity_id);
		displayAddressList(_entity_id);
		displayWorkExpList(_entity_id);
		displayDependentList(_entity_id);
		displayReferencesOther(_entity_id);
		displayFinancialInfo(_entity_id);

		lookupClient.setValue(_entity_id);
		
		setSecondaryTitle(entity_name);
		startTimerStatus();
		
		try {
			displayClientImage(lblClientImageFileChooser, entity_id);
			displayClientSignature(lblClientSignatureFileChooser, entity_id);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError er){
				JOptionPane.showMessageDialog(getContentPane(), "Out of memory.", "Loading Image.", JOptionPane.WARNING_MESSAGE);
		}
		
		pnlState(true, true, true, true, true, true, true, true);
		
		btnState(true, true, false, false, false); 
		
		if(canEdit() == false){
			btnState(false, false, false, false, false);
		}
		
	}
	
	private String getNewEntityID(){
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT get_new_entity_id()";
		db.select(SQL);
		//FncSystem.out("Display New Entity ID", SQL);
		
		return (String) db.getResult()[0][0];
	}
	
	private Boolean entityExisting(String entity_id){
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM rf_entity WHERE entity_id = '"+entity_id+"'";
		db.select(SQL);
		FncSystem.out("Display Entity is existing", SQL);
		
		if(db.isNotNull()){
			return true;
		}else{
			return false;
		}
	}
	
	private void saveImage(byte[] img, byte[] signature ,String entity_id){
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			connection.setAutoCommit(true);

			PreparedStatement ps = connection.prepareStatement("UPDATE rf_entity_image SET entity_image = ?, entity_signature = ? WHERE entity_id = '"+entity_id+"'");
			ps.setBytes(1, img);
			ps.setBytes(2, signature);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static byte[] displayClientImage(FncImageFileChooser label, String entity_id) throws IOException{
		byte bt[]=null;

		String strSQL = "SELECT entity_image FROM rf_entity_image WHERE entity_id = '"+entity_id+"'";
		FncSystem.out("Display SQL for Client Image", strSQL);
		label.setDefaultClientImage();
		
		try{
			Functions.FncSelectRecords.selectV2(strSQL, false);
			Functions.FncSelectRecords.rs.last();
			Functions.FncSelectRecords.rs.beforeFirst();
			//int x = 0;

			while (Functions.FncSelectRecords.rs.next()) {
				bt = Functions.FncSelectRecords.rs.getBytes(1);
				
				//check if picture exist in column
				if(bt!=null) {
					label.setByteImageIcon(bt);
				}
				else{
					System.out.print("Dumaan dito sa default client IMage");
					label.setDefaultClientImage();
				}
				//x++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Functions.FncSelectRecords.disconnect();
		
		return bt;
	}
	
	public static byte[] displayClientSignature(FncImageFileChooser label, String entity_id) throws IOException{
		byte bt[]=null;

		String strSQL = "SELECT entity_signature FROM rf_entity_image WHERE entity_id = '"+entity_id+"'";
		label.setDefaultClientSignature();
		
		try{
			Functions.FncSelectRecords.selectV2(strSQL, false);
			Functions.FncSelectRecords.rs.last();
			Functions.FncSelectRecords.rs.beforeFirst();
			//int x = 0;

			while (Functions.FncSelectRecords.rs.next()) {
				bt = Functions.FncSelectRecords.rs.getBytes(1);

				System.out.printf("Display value of BT: %s%n", bt);
				if(bt!=null) {
					label.setByteImageIcon(bt);
				}
				else{
					System.out.println("Dumaan dito sa display ng default clien signature");
					label.setDefaultClientSignature();
				}
				//x++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Functions.FncSelectRecords.disconnect();
		
		return bt;
	}
	
	public static boolean canEdit(){
		/*System.out.printf("Display Division Code: %s%n", UserInfo.Division);
		System.out.printf("Display Employee Code: %s%n", UserInfo.EmployeeCode);*/
		
		if((UserInfo.Division.equals("04") || UserInfo.Division.equals("06") || UserInfo.Division.equals("07") 
				   || UserInfo.Division.equals("08") || UserInfo.Division.equals("09") || UserInfo.Division.equals("29"))){
			if(UserInfo.EmployeeCode.trim().equals("900965") || UserInfo.EmployeeCode.trim().equals("900606") || 
					UserInfo.EmployeeCode.trim().equals("900383") || UserInfo.EmployeeCode.trim().equals("900028")){
				System.out.printf("Display Employee Code: %s%n", UserInfo.EmployeeCode);
				System.out.println("Dumaan sa false");
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	
	
	@Override
	public void ancestorAdded(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
