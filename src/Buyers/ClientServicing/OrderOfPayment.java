package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelOrderOfPayments_CreditPayments;
import tablemodel.modelOrderOfPayments_NewPayments;
import tablemodel.modelOrderOfPayments_PaymentDetails;
import Database.pgSelect;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncBigDecimal;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;

import com.toedter.calendar.JTextFieldDateEditor;

import components._ButtonGroup;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;

public class OrderOfPayment extends _JInternalFrame implements _GUI, ActionListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4254735478522431784L;

	static String title = "Order of Payments";
	Dimension frameSize = new Dimension(800, 610);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlPaymentDetailsWest;
	private JPanel pnlNewPayments;	
	private JPanel pnlCenter;
	private JPanel pnlPaymentDetails;
	private JPanel pnlIDs;
	private JPanel pnlLabels;
	private JPanel pnlNorthCenter;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthNorth;
	private JPanel pnlCenterSouth;
	private JPanel pnlPaymentList;
	private JPanel pnlViewPastOP;
	private JPanel pnlView_North;
	private JPanel pnlView_Center;
	private JPanel pnlView_North_a;
	private JPanel pnlView_North_b;
	private JPanel pnlView_North_c;

	private JLabel lblSelectType;
	private JLabel lblReservationType;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSequence;
	private JLabel lblModel;
	private JLabel lblSellingPrice;
	private JLabel lblParticular;
	private JLabel lblCheckType;
	private JLabel lblCheckDate;
	private JLabel lblCheckNo;
	private JLabel lblNoOfCheck;
	private JLabel lblAccountNo;
	private JLabel lblBRSTN;
	private JLabel lblBank;
	private JLabel lblBranch;
	private JLabel lblViewDate;


	private JComboBox cmbSelectType;
	private JComboBox cmbCheckType;

	private _JLookup lookupClient;
	private _JLookup lookupParticular;
	private _JLookup lookupBank;	
	private _JLookup lookupBranch;

	private _JXTextField txtClientName;	
	private _JXTextField txtProjectID;
	private _JXTextField txtProjectName;	
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDescription;
	private _JXTextField txtOtherLot;
	private _JXTextField txtSequence;	
	private _JXTextField txtModelID;
	private _JXTextField txtModelName;
	private _JXTextField txtParticularName;
	private _JXTextField txtParticularAlias;
	private _JXTextField txtCheckNo;
	private _JXTextField txtAccountNo;	
	private _JXTextField txtBRSTN;
	private _JXTextField txtBankName;
	private _JXTextField txtBranchName;

	private _JXFormattedTextField txtSellingPrice;
	private _JXFormattedTextField txtCashAmount;
	private _JXFormattedTextField txtCheckAmount;
	private _JXFormattedTextField txtNoOfCheck;

	private JCheckBox chkCashAmount;
	private JCheckBox chkCheckAmount;

	private _JDateChooser dateCheckDate;
	private _JDateChooser current_date;

	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	private _ButtonGroup grpNewEdit = new _ButtonGroup();
	private JButton btnPD_Post;
	private JCheckBox chkCreditItsReal;
	private JButton btnPD_Edit;
	private JButton btnPD_Delete;
	private JButton btnPD_Cancel;	
	private JButton btnAROR;
	private JButton btnRefresh;
	private JButton btnViewPastOP;

	private _JDateChooser dteViewOP;

	private JTabbedPane tabPayments;	
	private _JScrollPaneMain scrollPaymentList;		
	private JList rowHeaderPaymentList;
	private JList rowHeaderNewPayments;	
	private JList rowHeaderNewPayments_past;

	private _JScrollPaneTotal scrollPaymentListTotal;		
	private _JTableTotal tblPaymentListTotal;

	private modelOrderOfPayments_PaymentDetails modelPaymentListTotal;
	private modelOrderOfPayments_PaymentDetails modelPaymentList;	
	private modelOrderOfPayments_NewPayments modelNewPayments;
	private modelOrderOfPayments_CreditPayments modelCreditPayments;
	private modelOrderOfPayments_NewPayments modelNewPayments_past;

	private JScrollPane scrollNewPayments;
	private JScrollPane scrollCreditPayments;	
	private JScrollPane scrollNewPayments_past;

	private _JTableMain tblNewPayments;
	private _JTableMain tblCreditPayments;
	private _JTableMain tblPaymentList;		
	private _JTableMain tblNewPayments_past;

	private String receipt_id = "03";
	//private String receipt_type = "ACKNOWLEDGEMENT RECEIPT";
	private String receipt_alias = "AR";
	private String receipt_type = "";
	BigDecimal excess = null;
	String pbl_id = "";
	Integer unit_seq_no = null;	
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private Boolean blnViewMode = false;

	private String strSelectedLot = "";
	private JLabel lblLotRemarks; 
	private String strParticular; 

	public OrderOfPayment() {
		super(title, true, true, true, true);
		initGUI();
	}

	/*public OrderOfPayment(String entity_id, String entity_name, String proj_id, String proj_name, String pbl_id, String unit_description, Integer seq_no,
			String model_id, String model_name, BigDecimal selling_price, Object[][] data) {
		super(title, true, true, true, true);
		initGUI();
		System.out.println("***** DUMAAN *****");
		grpNewEdit.setSelectedButton(btnNew);

		displayFromCARD(entity_id, entity_name, proj_id, proj_name, pbl_id, unit_description, seq_no, model_id, model_name, selling_price, data);
	}*/

	public OrderOfPayment(String title) {
		super(title);
		initGUI();
	}

	public OrderOfPayment(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
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
		//this.addAncestorListener(this);

		/*this.addAncestorListener(new AncestorListener() {
			public void ancestorRemoved(AncestorEvent arg0) { }
			public void ancestorMoved(AncestorEvent arg0) { }
			public void ancestorAdded(AncestorEvent arg0) {
				System.out.println("***********************************DUMAAN!");
			}
		});*/

		this.getRootPane().registerKeyboardAction(this, "Current Date", KeyStroke.getKeyStroke(KeyEvent.VK_J, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		//disabled due to unnecessary icon on toolbar
		/*Image iconInternalFrame = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/buyers/orderofpayments.png")).getImage();
		iconInternalFrame = iconInternalFrame.getScaledInstance(19, 19, Image.SCALE_DEFAULT);
		this.setFrameIcon(new ImageIcon(iconInternalFrame));*/
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(3, 3));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 3));

			{
				pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Transaction Details"));
				pnlNorth.setPreferredSize(new Dimension(0, 155));
				{
					pnlNorthNorth = new JPanel();
					//pnlNorth.add(pnlNorthNorth, BorderLayout.NORTH);
					pnlNorthNorth.setPreferredSize(new Dimension(773, 25));
				}
				{
					pnlNorthWest = new JPanel(new BorderLayout(3, 3));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					pnlNorthWest.setPreferredSize(new Dimension(240, 0));
					{
						pnlLabels = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlNorthWest.add(pnlLabels, BorderLayout.WEST);
						//pnlLabels.setBorder(lineBorder);
						pnlLabels.setPreferredSize(new Dimension(94, 0));
						{
							lblSelectType = new JLabel("Select Type");
							pnlLabels.add(lblSelectType);
						}
						{
							lblClient = new JLabel("Client Seq. #");
							pnlLabels.add(lblClient);
						}
						{
							lblProject = new JLabel("Project");
							pnlLabels.add(lblProject);
						}
						{
							lblUnit = new JLabel("Unit");
							pnlLabels.add(lblUnit);
						}
						{
							lblModel = new JLabel("Model");
							pnlLabels.add(lblModel);
						}
					}
					{
						pnlIDs = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlNorthWest.add(pnlIDs, BorderLayout.CENTER);
						{
							cmbSelectType = new JComboBox(new String[] { "RESERVATION (TR)", "NEW PAYMENT" });
							pnlIDs.add(cmbSelectType);
							cmbSelectType.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent e) {
									setParticular();
									btnState(false, false, false, false, false);
								}
							});
							cmbSelectType.addPopupMenuListener(new PopupMenuListener() {
								public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) { }

								@Override
								public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
									/*int selectedType = cmbSelectType.getSelectedIndex();
									String selectedItem = (String) cmbSelectType.getSelectedItem();

									if(selectedType == 1){
										JOptionPane.showMessageDialog(OrderOfPayment.this.getTopLevelAncestor(), String.format("Sorry for the inconvenience. %s transaction still under development.", selectedItem), "Type", JOptionPane.WARNING_MESSAGE);
									}*/
								}
								public void popupMenuCanceled(PopupMenuEvent arg0) { }
							});
						}
						{
							lookupClient = new _JLookup(null, "Client");
							pnlIDs.add(lookupClient);
							lookupClient.setFilterName(true);
							lookupClient.setReturnColumn(0);
							lookupClient.setPrompt("Client ID");
							lookupClient.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										int selectedType = cmbSelectType.getSelectedIndex();

										if(selectedType == 0){
											displayReservationClient(data);
										}else{
											/*
											String entity_id = (String) data[0];
											String proj_id = (String) data[6];
											pbl_id = (String) data[3];
											unit_seq_no = (Integer) data[4];
											receipt_type = "";
											 */

											String entity_id = (String) data[0];
											String proj_id = (String) data[6]==""? "":(String) data[6];
											pbl_id = (String) data[3]==""? "":(String) data[3];
											String server_id = (String) data[8];

											try {
												unit_seq_no = (Integer) data[4];
											} catch (NullPointerException e) {
												unit_seq_no = 0;
											}
											
											
											// ADDED BY MONIQUE DTD 2023-07-25; CORRELATE TO DCRF#2693
											FncSystem.out("Server_id: ", server_id.toString());	
											
											if(server_id.toString().equals("itsreal")) {
												chkCreditItsReal.setEnabled(true);
											}else {
												chkCreditItsReal.setEnabled(false);
											}
											
											receipt_type = "";

											displayNewPaymentClient(entity_id, proj_id, pbl_id, unit_seq_no,server_id);
										}
										//Added by Erick 2021-11-09
										lookupParticular.setLookupSQL(_OrderOfPayment.getParticulars(lookupClient.getValue(), txtProjectID.getText(), txtUnitID.getText(), txtSequence.getText(), 
												cmbSelectType.getSelectedItem() == "RESERVATION (TR)", receipt_type==null? "":receipt_type));
									}
								}
							});
						}
						{
							txtProjectID = new _JXTextField("Project ID");
							pnlIDs.add(txtProjectID);
							txtProjectID.setEditable(false);
							txtProjectID.setHorizontalAlignment(_JXTextField.CENTER);
						}
						{
							txtUnitID = new _JXTextField("Unit ID");
							pnlIDs.add(txtUnitID);
							txtUnitID.setEditable(false);
							txtUnitID.setHorizontalAlignment(_JXTextField.CENTER);
						}
						{
							txtModelID = new _JXTextField("Model ID");
							pnlIDs.add(txtModelID);
							txtModelID.setEditable(false);
							txtModelID.setHorizontalAlignment(_JXTextField.CENTER);
						}
					}
				}
				{
					pnlNorthCenter = new JPanel(new GridLayout(5, 1, 3, 3));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					{
						JPanel pnlResType = new JPanel(new BorderLayout(5, 5));
						pnlNorthCenter.add(pnlResType);
						{
							lblReservationType = new JLabel();
							pnlResType.add(lblReservationType, BorderLayout.WEST);
						}
						{
							chkCreditItsReal = new JCheckBox("Credit from ItsReal?");
							pnlResType.add(chkCreditItsReal, BorderLayout.CENTER);
							chkCreditItsReal.setEnabled(false); 
						}
					}
					{
						txtClientName = new _JXTextField("Client Name");
						pnlNorthCenter.add(txtClientName);
						txtClientName.setEditable(false);
					}
					{
						txtProjectName = new _JXTextField("Project Name");
						pnlNorthCenter.add(txtProjectName);
						txtProjectName.setEditable(false);
					}
					{
						JPanel pnlUnit = new JPanel(new BorderLayout());
						pnlNorthCenter.add(pnlUnit);
						{
							JXPanel panDescOth = new JXPanel(new BorderLayout(5, 5)); 
							pnlUnit.add(panDescOth, BorderLayout.CENTER);
							{
								{
									txtUnitDescription = new _JXTextField("Unit Description");
									panDescOth.add(txtUnitDescription, BorderLayout.CENTER);
								}
								{
									txtOtherLot = new _JXTextField("Other Lot"); 
									panDescOth.add(txtOtherLot, BorderLayout.LINE_END);
									txtOtherLot.setPreferredSize(new Dimension(50, 0));
								}	
							}
						}
						{
							JPanel pnlSequence = new JPanel(new BorderLayout(5, 5));
							pnlUnit.add(pnlSequence, BorderLayout.EAST);
							pnlSequence.setPreferredSize(new Dimension(200, 0));
							{
								lblSequence = new JLabel("Seq. No.", JLabel.TRAILING);
								pnlSequence.add(lblSequence, BorderLayout.WEST);
								lblSequence.setPreferredSize(new Dimension(90, 0));
							}
							{
								txtSequence = new _JXTextField();
								pnlSequence.add(txtSequence, BorderLayout.CENTER);
								txtSequence.setHorizontalAlignment(JXTextField.CENTER);
							}
						}
					}
					{
						JPanel pnlModel = new JPanel(new BorderLayout());
						pnlNorthCenter.add(pnlModel);
						{
							txtModelName = new _JXTextField("Model Name");
							pnlModel.add(txtModelName, BorderLayout.CENTER);
							txtModelName.setEditable(false);
						}
						{
							JPanel pnlSellingPrice = new JPanel(new BorderLayout(5, 5));
							pnlModel.add(pnlSellingPrice, BorderLayout.EAST);
							pnlSellingPrice.setPreferredSize(new Dimension(200, 0));
							{
								lblSellingPrice = new JLabel("Selling Price", JLabel.TRAILING);
								pnlSellingPrice.add(lblSellingPrice, BorderLayout.WEST);
								lblSellingPrice.setPreferredSize(new Dimension(90, 0));
							}
							{
								txtSellingPrice = new _JXFormattedTextField(_JXFormattedTextField.RIGHT);
								pnlSellingPrice.add(txtSellingPrice, BorderLayout.CENTER);
								txtSellingPrice.setPrompt("Selling Price");
								txtSellingPrice.setEditable(false);
								txtSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(0, 0));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Payment Details"));
				{
					JPanel pnlWestNavigation = new JPanel(new BorderLayout());
					pnlCenter.add(pnlWestNavigation, BorderLayout.NORTH);
					{
						JPanel pnlBorder = new JPanel(new BorderLayout());
						pnlWestNavigation.add(pnlBorder, BorderLayout.WEST);
						pnlBorder.setBorder(BorderFactory.createLineBorder(Color.GRAY));
						{
							JPanel pnlWest = new JPanel(new GridLayout(5, 1, 3, 3));
							pnlBorder.add(pnlWest, BorderLayout.CENTER);
							pnlWest.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
							pnlWest.setPreferredSize(new Dimension(80, 0));
							{
								btnPD_Post = new JButton("Post");
								pnlWest.add(btnPD_Post);
								btnPD_Post.setActionCommand("PD_Post");
								btnPD_Post.setMnemonic(KeyEvent.VK_P);
								btnPD_Post.addActionListener(this);
							}
							{
								pnlWest.add(Box.createHorizontalGlue());
							}
							{
								btnPD_Edit = new JButton("Edit");
								pnlWest.add(btnPD_Edit);
								btnPD_Edit.setActionCommand("PD_Edit");
								//btnPD_Edit.setMnemonic(KeyEvent.VK_E);
								btnPD_Edit.addActionListener(this);
							}
							{
								btnPD_Delete = new JButton("Delete");
								pnlWest.add(btnPD_Delete);
								btnPD_Delete.setActionCommand("PD_Delete");
								//btnPD_Delete.setMnemonic(KeyEvent.VK_D);
								btnPD_Delete.addActionListener(this);
							}
							{
								btnPD_Cancel = new JButton("Cancel");
								pnlWest.add(btnPD_Cancel);
								btnPD_Cancel.setActionCommand("PD_Cancel");
								//btnPD_Cancel.setMnemonic(KeyEvent.VK_C);
								btnPD_Cancel.addActionListener(this);
							}
						}

					}
					{
						pnlPaymentDetails = new JPanel(new BorderLayout(5, 3));
						pnlWestNavigation.add(pnlPaymentDetails, BorderLayout.CENTER);
						pnlPaymentDetails.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
						pnlPaymentDetails.setPreferredSize(new Dimension(0, 200));
						{
							pnlPaymentDetailsWest = new JPanel(new GridLayout(8, 1, 3, 3));
							pnlPaymentDetails.add(pnlPaymentDetailsWest, BorderLayout.WEST);
							//pnlPaymentDetailsWest.setBorder(lineBorder);
							pnlPaymentDetailsWest.setPreferredSize(new Dimension(80, 0));
							{
								lblParticular = new JLabel("Particular", JLabel.TRAILING);
								pnlPaymentDetailsWest.add(lblParticular);
							}
							{
								chkCashAmount = new JCheckBox("Cash");//"Cash / Amount"
								pnlPaymentDetailsWest.add(chkCashAmount);
								chkCashAmount.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent arg0) {
										Boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;
										txtCashAmount.setEnabled(selected);
										txtCashAmount.requestFocus();
									}
								});
							}
							{
								chkCheckAmount = new JCheckBox("Check");//"Check / Amount"
								pnlPaymentDetailsWest.add(chkCheckAmount);
								chkCheckAmount.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent arg0) {
										Boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;
										txtCheckAmount.setEnabled(selected);
										txtCheckAmount.requestFocus();
										enableCheckPayment(selected);
									}
								});
							}
							{
								lblCheckType = new JLabel("Check Type", JLabel.TRAILING);
								pnlPaymentDetailsWest.add(lblCheckType);
							}
							{
								lblCheckNo = new JLabel("Check No.", JLabel.TRAILING);
								pnlPaymentDetailsWest.add(lblCheckNo);
							}
							{
								lblAccountNo = new JLabel("Account No.", JLabel.TRAILING);
								pnlPaymentDetailsWest.add(lblAccountNo);
							}
							{
								lblBank = new JLabel("Bank", JLabel.TRAILING);
								pnlPaymentDetailsWest.add(lblBank);
							}
							{
								lblBranch = new JLabel("Branch", JLabel.TRAILING);
								pnlPaymentDetailsWest.add(lblBranch);
							}
						}
						{
							JPanel jPanel2 = new JPanel(new GridLayout(8, 1, 3, 3));
							pnlPaymentDetails.add(jPanel2, BorderLayout.CENTER);
							{
								JPanel pnlParticular = new JPanel(new BorderLayout(3, 3));
								jPanel2.add(pnlParticular);
								{
									lookupParticular = new _JLookup(null, "Particular", 0);
									pnlParticular.add(lookupParticular, BorderLayout.WEST);
									lookupParticular.setPrompt("Part. ID");
									lookupParticular.setFilterName(true);									
									lookupParticular.setPreferredSize(new Dimension(80, 0));
									lookupParticular.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup)event.getSource()).getDataSet();

											if (data != null) {

												String selected_receipt_type = (String) data[6];
												//if (receipt_type.equals("")||receipt_type.equals(selected_receipt_type)) {
													String desciption = (String) data[1];
													String alias = (String) data[2];

													if (hasLTS_BOI(txtUnitID.getText())==false||receipt_id.equals(null)) {
														receipt_id = "03";
														receipt_type = "AR";
													} else {
														receipt_id = (String) data[5];
														receipt_type = (String) data[6];
													}													

													receipt_alias = (String) data[7];
													txtParticularName.setText(desciption);
													txtParticularAlias.setText(alias);

													strParticular = desciption; 
//												} else {
//													JOptionPane.showMessageDialog(getContentPane(), 
//															"The selected payment requires a different receipt type." + "\n" +
//																	"Please select other payment particular or you can create a new OP. ", 
//																	"Receipt Type Incompatible", 
//																	JOptionPane.WARNING_MESSAGE);
//												}
												
												
												FncSystem.out("Display SQL for lookupParticular", lookupParticular.getLookupSQL());

												lookupParticular.setLookupSQL(_OrderOfPayment.getParticulars(lookupClient.getValue(), txtProjectID.getText(), txtUnitID.getText(), txtSequence.getText(), 
														cmbSelectType.getSelectedItem() == "RESERVATION (TR)", receipt_type==null? "":receipt_type));
												
												System.out.println("lookupClient: "+lookupClient.getValue());
												System.out.println("txtProjectID: "+txtProjectID.getText());
												System.out.println("txtUnitID: "+txtUnitID.getText());
												System.out.println("txtSequence: "+txtSequence.getText());
												System.out.println("cmbSelectType: "+cmbSelectType.getSelectedItem() == "RESERVATION (TR)");
												System.out.println("receipt_type: "+ receipt_type==null? "":receipt_type);
												
												/*String desciption = (String) data[1];
												String alias = (String) data[2];
												receipt_id = (String) data[5];
												receipt_type = (String) data[6];
												receipt_alias = (String) data[7];
												txtParticularName.setText(desciption);
												txtParticularAlias.setText(alias);*/

												if (!txtOtherLot.getText().equals("")) {
													if (lotSpecific(lookupParticular.getValue())) {
														strSelectedLot = OtherLotSelection();
													} else {
														strSelectedLot = ""; 
													}	
												}

												ActivateLotLabel(); 

												System.out.println("");
												System.out.println("strSelectedLot: "+strSelectedLot);
											}
										}
									});
								}
								{
									txtParticularName = new _JXTextField("Particular Name");
									pnlParticular.add(txtParticularName, BorderLayout.CENTER);
								}
								{
									txtParticularAlias = new _JXTextField("Particular Alias");
									//pnlParticular.add(txtParticularAlias, BorderLayout.EAST);
									txtParticularAlias.setPreferredSize(new Dimension(120, 0));

									btnAROR = new JButton("AR/OR No.");
									pnlParticular.add(btnAROR, BorderLayout.EAST);
									btnAROR.setPreferredSize(new Dimension(120, 0));
									btnAROR.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											//String entity_id = lookupClient.getValue();

											if(lookupParticular.getValue() == null){
												JOptionPane.showMessageDialog(OrderOfPayment.this.getTopLevelAncestor(), "Please select particular first.", "AR/OR No.", JOptionPane.INFORMATION_MESSAGE);
												return;
											}

											if(lookupClient.getValue() != null){
												//displayCredit(e.getActionCommand(), null, "AR/OR No.");
												displayCredit(e.getActionCommand(), lookupClient.getValue(), "AR/OR No.");
											}else{
												displayCredit(e.getActionCommand(), lookupClient.getValue(), "AR/OR No.");
											}

										}
									});
								}
							}
							{
								JPanel pnlCashAmount = new JPanel(new BorderLayout(3, 3));
								jPanel2.add(pnlCashAmount);
								{
									{
										txtCashAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
										pnlCashAmount.add(txtCashAmount, BorderLayout.WEST);
										txtCashAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
										txtCashAmount.setPreferredSize(new Dimension(200, 0));
									}
									{
										lblLotRemarks = new JLabel(""); 
										pnlCashAmount.add(lblLotRemarks, BorderLayout.CENTER);
										lblLotRemarks.setHorizontalAlignment(JLabel.TRAILING);
										lblLotRemarks.setEnabled(true);
									}
								}
							}
							{
								JPanel pnlCheckAmount = new JPanel(new BorderLayout(3, 3));
								jPanel2.add(pnlCheckAmount);
								{
									txtCheckAmount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									pnlCheckAmount.add(txtCheckAmount, BorderLayout.WEST);
									txtCheckAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtCheckAmount.setPreferredSize(new Dimension(200, 0));
								}
							}
							{
								JPanel pnlCheckType = new JPanel(new BorderLayout(3, 3));
								jPanel2.add(pnlCheckType);
								{
									cmbCheckType = new JComboBox(getCheckTypes());
									pnlCheckType.add(cmbCheckType, BorderLayout.WEST);
									cmbCheckType.setPreferredSize(new Dimension(200, 0));
								}
								{
									JPanel pnlCheckDate = new JPanel(new BorderLayout(3, 3));
									pnlCheckType.add(pnlCheckDate, BorderLayout.CENTER);
									pnlCheckDate.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 150));
									{
										JPanel pnlCheckDateWest = new JPanel(new BorderLayout(3, 3));
										pnlCheckDate.add(pnlCheckDateWest, BorderLayout.WEST);
										pnlCheckDateWest.setPreferredSize(new Dimension(200, 0));
										{
											lblCheckDate = new JLabel("Check Date");
											pnlCheckDateWest.add(lblCheckDate, BorderLayout.WEST);
											lblCheckDate.setPreferredSize(new Dimension(80, 0));
										}
										{
											dateCheckDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
											pnlCheckDateWest.add(dateCheckDate, BorderLayout.CENTER);
										}
									}
								}
							}
							{
								JPanel pnlCheckNo = new JPanel(new BorderLayout(3, 3));
								jPanel2.add(pnlCheckNo);
								{
									txtCheckNo = new _JXTextField("Check No.");
									pnlCheckNo.add(txtCheckNo, BorderLayout.WEST);
									txtCheckNo.setPreferredSize(new Dimension(200, 0));
								}
								{
									JPanel pnlNoOfCheck = new JPanel(new BorderLayout(3, 3));
									pnlCheckNo.add(pnlNoOfCheck, BorderLayout.CENTER);
									pnlNoOfCheck.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 150));
									{
										JPanel pnlNoOfCheckWest = new JPanel(new BorderLayout(3, 3));
										pnlNoOfCheck.add(pnlNoOfCheckWest, BorderLayout.WEST);
										pnlNoOfCheckWest.setPreferredSize(new Dimension(173, 0));
										{
											lblNoOfCheck = new JLabel("No. of Check");
											pnlNoOfCheckWest.add(lblNoOfCheck, BorderLayout.WEST);
											lblNoOfCheck.setPreferredSize(new Dimension(80, 0));
										}
										{
											txtNoOfCheck = new _JXFormattedTextField(JXFormattedTextField.CENTER);
											pnlNoOfCheckWest.add(txtNoOfCheck, BorderLayout.CENTER);
											txtNoOfCheck.setPrompt("#");
											txtNoOfCheck.setFormatterFactory(_JXFormattedTextField.INTEGER);
											txtNoOfCheck.addKeyListener(new KeyAdapter() {
												public void keyReleased(KeyEvent e) {	

													if (Integer.parseInt(txtNoOfCheck.getText())>12)
													{
														JOptionPane.showMessageDialog(getContentPane(), "No. of checks cannot exceed 12", "Warning", 
																JOptionPane.WARNING_MESSAGE);
													}

												}				 
											});		
										}
									}
								}
							}
							{
								JPanel pnlAccountNo = new JPanel(new BorderLayout(3, 3));
								jPanel2.add(pnlAccountNo);
								{
									txtAccountNo = new _JXTextField("Account No.");
									pnlAccountNo.add(txtAccountNo, BorderLayout.WEST);
									txtAccountNo.setPreferredSize(new Dimension(200, 0));
								}
								{
									JPanel pnlBRSTN = new JPanel(new BorderLayout(3, 3));
									pnlAccountNo.add(pnlBRSTN, BorderLayout.CENTER);
									pnlBRSTN.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 150));
									{
										JPanel pnlNoOfCheckWest = new JPanel(new BorderLayout(3, 3));
										pnlBRSTN.add(pnlNoOfCheckWest, BorderLayout.WEST);
										pnlNoOfCheckWest.setPreferredSize(new Dimension(173, 0));
										{
											lblBRSTN = new JLabel("BRSTN");
											pnlNoOfCheckWest.add(lblBRSTN, BorderLayout.WEST);
											lblBRSTN.setPreferredSize(new Dimension(80, 0));
										}
										{
											txtBRSTN = new _JXTextField("BRSTN");
											pnlNoOfCheckWest.add(txtBRSTN, BorderLayout.CENTER);
										}
									}
								}
							}
							{
								JPanel pnlBank = new JPanel(new BorderLayout(3, 3));
								jPanel2.add(pnlBank);
								{
									lookupBank = new _JLookup(null, "Bank", 0);
									pnlBank.add(lookupBank, BorderLayout.WEST);
									lookupBank.setPrompt("Bank ID");
									lookupBank.setLookupSQL(_OrderOfPayment.getBank());
									lookupBank.setPreferredSize(new Dimension(80, 0));
									lookupBank.setFilterName(true);
									lookupBank.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){
												String bank_id = (String) data[0];
												String bank_name = (String) data[1];

												txtBankName.setText(bank_name);
												lookupBranch.setLookupSQL(_OrderOfPayment.getBankBranch(bank_id));
											}
										}
									});
								}
								{
									txtBankName = new _JXTextField("Bank Name");
									pnlBank.add(txtBankName, BorderLayout.CENTER);
								}
							}
							{
								JPanel pnlBranch = new JPanel(new BorderLayout(3, 3));
								jPanel2.add(pnlBranch);
								{
									lookupBranch = new _JLookup(null, "Branch", 0);
									pnlBranch.add(lookupBranch, BorderLayout.WEST);
									lookupBranch.setPrompt("Branch ID");
									lookupBranch.setPreferredSize(new Dimension(80, 0));
									lookupBranch.setFilterName(true);
									lookupBranch.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup)event.getSource()).getDataSet();
											if(data != null){
												String branch_name = (String) data[1];

												txtBranchName.setText(branch_name);
											}
										}
									});
								}
								{
									txtBranchName = new _JXTextField("Branch Name");
									pnlBranch.add(txtBranchName, BorderLayout.CENTER);
								}
							}
						}
						{
							JPanel jPanel3 = new JPanel(new BorderLayout());
							//pnlPaymentDetails.add(jPanel3, BorderLayout.EAST);
							jPanel3.setBorder(components.JTBorderFactory.createTitleBorder("AR/OR No."));
							jPanel3.setPreferredSize(new Dimension(170, 0)); 
							{
								scrollCreditPayments = new JScrollPane();
								jPanel3.add(scrollCreditPayments, BorderLayout.CENTER);
								scrollCreditPayments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								scrollCreditPayments.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent e) {
										if(tblCreditPayments.isEnabled()){
											tblCreditPayments.clearSelection();
										}
									}
								});
								{
									modelCreditPayments = new modelOrderOfPayments_CreditPayments();
									/*modelCreditPayments.addTableModelListener(new TableModelListener() {
										public void tableChanged(TableModelEvent e) {
											//Addition of rows
											if(e.getType() == TableModelEvent.INSERT){
												((DefaultListModel)rowHeaderCreditPayments.getModel()).addElement(modelCreditPayments.getRowCount());
											}
											if(e.getType() == TableModelEvent.DELETE){
												if(modelCreditPayments.getRowCount() == 0){
													rowHeaderCreditPayments.setModel(new DefaultListModel());
												}
											}
										}
									});*/

									tblCreditPayments = new _JTableMain(modelCreditPayments);
									scrollCreditPayments.setViewportView(tblCreditPayments);
									tblCreditPayments.hideColumns("Trans. Date");
								}
								{
									/*rowHeaderCreditPayments = tblCreditPayments.getRowHeader();
									rowHeaderCreditPayments.setModel(new DefaultListModel());
									scrollCreditPayments.setRowHeaderView(rowHeaderCreditPayments);
									scrollCreditPayments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									scrollCreditPayments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCreditPayments.getRowCount())));*/
								}

							}
						}
					}
				}
				{
					tabPayments = new JTabbedPane();
					pnlCenter.add(tabPayments, BorderLayout.CENTER);
					tabPayments.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent arg0) {
							int selectedTab = ((JTabbedPane)arg0.getSource()).getSelectedIndex();
							if(selectedTab == 0){
								try {
									if(cmbSelectType.isEnabled()==true){
										btnState(lookupClient.getValue() != null && lookupClient.isEditable(), false, false, true, false);										
									}else{
										btnState(lookupClient.getValue() != null && lookupClient.isEditable(), false, true, true, false);

									}
								} catch (NullPointerException e) { }								
							}
							if(selectedTab == 1){
								/*if(cmbSelectType.isEnabled()){
									displayNewPayments();
								}*/
							}
							//cmbSelectType.setEnabled(selectedTab == 0);
						}
					});
					{
						pnlPaymentList = new JPanel(new BorderLayout());
						tabPayments.addTab("  Payment List  ", null, pnlPaymentList, null);
						{
							scrollPaymentList = new _JScrollPaneMain();
							pnlPaymentList.add(scrollPaymentList, BorderLayout.CENTER);
							scrollPaymentList.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									_JScrollPaneMain sp = (_JScrollPaneMain) e.getSource();

									if(((_JTableMain)sp.getViewport().getView()).isEnabled()){
										btnPD_State((grpNewEdit.getSelectedButton() != null), false, false, false);
										//btnRefresh.setEnabled(true);
									}
								}
							});
							{
								modelPaymentList = new modelOrderOfPayments_PaymentDetails();

								tblPaymentList = new _JTableMain(modelPaymentList);
								scrollPaymentList.setViewportView(tblPaymentList);								

								//Process after row add in tables
								modelPaymentList.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if(e.getType() == TableModelEvent.INSERT){
											//_OrderOfPayment.totalPayments(modelPaymentList, modelPaymentListTotal);

											((DefaultListModel)rowHeaderPaymentList.getModel()).addElement(modelPaymentList.getRowCount());
											scrollPaymentListTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelPaymentList.getRowCount())));

											if(modelPaymentList.getRowCount() == 0){
												rowHeaderPaymentList.setModel(new DefaultListModel());
											}else{
												tblPaymentList.packAll();
												tblPaymentList.setEnabled(true);
												//clearPaymentDetails(); 
												//setComponentsEnabled(pnlPaymentDetails, false);
												btnPD_State(!cmbSelectType.isEnabled(), !cmbSelectType.isEnabled(), !cmbSelectType.isEnabled(), false);
												btnState(false, false, true, true, false);
											}
										}

										if(e.getType() == TableModelEvent.DELETE){
											try {
												//_OrderOfPayment.totalPayments(modelPaymentList, modelPaymentListTotal);
												scrollPaymentListTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelPaymentList.getRowCount())));

												rowHeaderPaymentList.setModel(new DefaultListModel());
												for(int x=0; x < modelPaymentList.getRowCount(); x++){
													((DefaultListModel)rowHeaderPaymentList.getModel()).addElement(x + 1);
												}
											} catch (ArrayIndexOutOfBoundsException e1) { }
										}

										_OrderOfPayment.totalPayments(modelPaymentList, modelPaymentListTotal);
									}
								});
								tblPaymentList.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if(tblPaymentList.rowAtPoint(e.getPoint()) == -1){}
										else{tblPaymentList.setCellSelectionEnabled(true);}

										/*	Modified by Mann2x; Modified Date: November 28, 2016; Modified so that buttons will be disabled during View Mode	*/
										/*	btnPD_State(true, true, true, false);	*/
										if (blnViewMode) {
											btnPD_State(false, false, false, false);
											btnState(false, false, false, false, true);
										} else {
											btnPD_State(true, true, true, false);
										}
									}

									public void mouseReleased(MouseEvent e) {
										if(tblPaymentList.rowAtPoint(e.getPoint()) == -1){}
										else{tblPaymentList.setCellSelectionEnabled(true);}

										/*	Modified by Mann2x; Modified Date: November 28, 2016; Modified so that buttons will be disabled during View Mode	*/
										/*	btnPD_State(true, true, true, false);	*/
										if (blnViewMode) {
											btnPD_State(false, false, false, false);
											btnState(false, false, false, false, true);
										} else {
											btnPD_State(true, true, true, false);
										}										
									}
								});
								tblPaymentList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent arg0) {
										if(!arg0.getValueIsAdjusting()){
											try {
												int row = tblPaymentList.getModelRow(tblPaymentList.getSelectedRow());

												String part_id = (String) modelPaymentList.getValueAt(row, 0);
												String particular = (String) modelPaymentList.getValueAt(row, 1);
												BigDecimal amount = (BigDecimal) modelPaymentList.getValueAt(row, 2);
												String type = (String) modelPaymentList.getValueAt(row, 3);
												String check_no = (String) modelPaymentList.getValueAt(row, 4);
												String check_type_id = (String) modelPaymentList.getValueAt(row, 5);
												String check_type = (String) modelPaymentList.getValueAt(row, 6);
												Date check_date = (Date) modelPaymentList.getValueAt(row, 7);
												String account_no = (String) modelPaymentList.getValueAt(row, 8);
												String bank_id = (String) modelPaymentList.getValueAt(row, 9);
												String bank_name = (String) modelPaymentList.getValueAt(row, 10);
												String branch_id = (String) modelPaymentList.getValueAt(row, 11);
												String branch_name = (String) modelPaymentList.getValueAt(row, 12);
												String brstn = (String) modelPaymentList.getValueAt(row, 17);
												/*String receipt_no = (String) modelPaymentList.getValueAt(row, 13);
												String receipt_id = (String) modelPaymentList.getValueAt(row, 14);
												String receipt_type = (String) modelPaymentList.getValueAt(row, 15);
												Boolean credit = (Boolean) modelPaymentList.getValueAt(row, 17);*/

												clearPaymentDetails();
												if(type.equals("CASH")){
													lookupParticular.setValue(part_id);
													txtParticularName.setText(particular);
													txtCashAmount.setValue(amount);
												}

												if(type.equals("CHECK")){
													lookupParticular.setValue(part_id);
													txtParticularName.setText(particular);
													cmbCheckType.setSelectedItem(String.format("%s (%s)", check_type.trim(), check_type_id));
													txtCheckAmount.setValue(amount);
													txtCheckNo.setText(check_no);
													txtAccountNo.setText(account_no);
													dateCheckDate.setDate(check_date);
													txtNoOfCheck.setValue(Integer.parseInt("1"));
													lookupBank.setValue(bank_id);
													txtBankName.setText(bank_name);
													lookupBranch.setValue(branch_id);
													txtBranchName.setText(branch_name);
													txtBRSTN.setText(brstn);
												}
												tblPaymentList.requestFocus();

												//System.out.printf("Group: %s%n", grpNewEdit.getSelection().getActionCommand());
												/*if(grpNewEdit.getSelectedButton() != null){
													btnPD_State(true, !credit, true, false);
												}*/				

											} catch (IndexOutOfBoundsException e) { }
										}
									}
								});
							}
							{
								rowHeaderPaymentList = tblPaymentList.getRowHeader();
								rowHeaderPaymentList.setModel(new DefaultListModel());
								scrollPaymentList.setRowHeaderView(rowHeaderPaymentList);
								scrollPaymentList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							scrollPaymentListTotal = new _JScrollPaneTotal(scrollPaymentList);
							pnlPaymentList.add(scrollPaymentListTotal, BorderLayout.SOUTH);
							{
								modelPaymentListTotal = new modelOrderOfPayments_PaymentDetails();
								modelPaymentListTotal.addRow(new Object[] { null, "Total", 0.00, null, null, null });

								tblPaymentListTotal = new _JTableTotal(modelPaymentListTotal, tblPaymentList);
								scrollPaymentListTotal.setViewportView(tblPaymentListTotal);

								tblPaymentListTotal.setTotalLabel(1);
							}
						}
					}
					{
						pnlNewPayments = new JPanel(new BorderLayout());
						tabPayments.addTab("  New Payments  ", null, pnlNewPayments, null);
						{
							scrollNewPayments = new JScrollPane();
							pnlNewPayments.add(scrollNewPayments, BorderLayout.CENTER);
							scrollNewPayments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollNewPayments.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									if(tblNewPayments.isEnabled()){
										tblNewPayments.clearSelection();
									}
								}
							});
							{
								modelNewPayments = new modelOrderOfPayments_NewPayments();
								modelNewPayments.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										//Addition of rows
										if(e.getType() == 1){
											((DefaultListModel)rowHeaderNewPayments.getModel()).addElement(modelNewPayments.getRowCount());

											if(modelNewPayments.getRowCount() == 0){
												rowHeaderNewPayments.setModel(new DefaultListModel());
											}
										}
									}
								});

								tblNewPayments = new _JTableMain(modelNewPayments);
								scrollNewPayments.setViewportView(tblNewPayments);
								tblNewPayments.hideColumns("Client ID", "Project ID", "Unit ID");
								tblNewPayments.setSortable(false);
								tblNewPayments.addMouseListener(new MouseAdapter() {
									public void mousePressed(MouseEvent e) {
										if(tblNewPayments.rowAtPoint(e.getPoint()) == -1){}
										else{tblNewPayments.setCellSelectionEnabled(true);}
										clickPayment();
									}
									public void mouseReleased(MouseEvent e) {
										if(tblNewPayments.rowAtPoint(e.getPoint()) == -1){}
										else{tblNewPayments.setCellSelectionEnabled(true);}
										clickPayment();
									}
								});

							}
							{
								rowHeaderNewPayments = tblNewPayments.getRowHeader();
								rowHeaderNewPayments.setModel(new DefaultListModel());
								scrollNewPayments.setRowHeaderView(rowHeaderNewPayments);
								scrollNewPayments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								scrollNewPayments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNewPayments.getRowCount())));
							}

						}
					}
				}
			}
			{
				pnlCenterSouth = new JPanel(new GridLayout(1, 10, 3, 3));
				pnlCenter.add(pnlCenterSouth, BorderLayout.SOUTH);
				pnlCenterSouth.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
				pnlCenterSouth.setPreferredSize(new Dimension(0, 35));

				{
					btnViewPastOP = new JButton("<html><center>View<center><html>" +
							"<html><center>Past OP<center><html>");
					pnlCenterSouth.add(btnViewPastOP);
					btnViewPastOP.setActionCommand("ViewPastOP");
					btnViewPastOP.setMnemonic(KeyEvent.VK_V);
					btnViewPastOP.addActionListener(this);
					btnViewPastOP.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,11));
				}
				{
					pnlCenterSouth.add(Box.createHorizontalBox());
					pnlCenterSouth.add(Box.createHorizontalBox());
					pnlCenterSouth.add(Box.createHorizontalBox());
				}
				{
					btnNew = new JButton("New");
					pnlCenterSouth.add(btnNew);
					btnNew.setActionCommand("New");
					btnNew.setMnemonic(KeyEvent.VK_N);
					btnNew.addActionListener(this);
					grpNewEdit.add(btnNew);
				}
				{
					btnEdit = new JButton("Edit");
					pnlCenterSouth.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.setMnemonic(KeyEvent.VK_E);
					btnEdit.addActionListener(this);
					grpNewEdit.add(btnEdit);
				}
				{
					btnSave = new JButton("Save");
					pnlCenterSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
				{
					btnRefresh = new JButton("Refresh");
					pnlCenterSouth.add(btnRefresh);
					btnRefresh.setActionCommand("Refresh");
					btnRefresh.setMnemonic(KeyEvent.VK_R);
					btnRefresh.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlCenterSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}				
			}
		}
		{
			pnlViewPastOP = new JPanel();
			pnlViewPastOP.setLayout(new BorderLayout(5, 5));	
			pnlViewPastOP.setPreferredSize(new java.awt.Dimension(1000, 450));	
			{
				pnlView_North = new JPanel(new BorderLayout(5, 5));
				pnlViewPastOP.add(pnlView_North, BorderLayout.NORTH);
				pnlView_North.setPreferredSize(new Dimension(0, 60));
				{
					JXPanel pnlDateParam = new JXPanel(new BorderLayout(5, 5));
					pnlView_North.add(pnlDateParam, BorderLayout.LINE_START);
					pnlDateParam.setPreferredSize(new Dimension(500, 0));
					pnlDateParam.setBorder(components.JTBorderFactory.createTitleBorder("OP Date"));
					{
						pnlView_North_a = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlDateParam.add(pnlView_North_a, BorderLayout.LINE_START);	
						//pnlView_North_a.setPreferredSize(new java.awt.Dimension(150, 40));	
						//pnlView_North_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
						pnlView_North_a.setPreferredSize(new Dimension(150, 0));
						{
							lblViewDate = new JLabel("OP Date", JLabel.LEADING);
							pnlView_North_a.add(lblViewDate);
							//lblViewDate.setEnabled(true);
						}	
					}	
					{
						pnlView_North_b = new JPanel(new BorderLayout(5, 5));
						pnlDateParam.add(pnlView_North_b, BorderLayout.CENTER);	
						//pnlView_North_b.setPreferredSize(new java.awt.Dimension(90, 40));	
						//pnlView_North_b.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
						{
							dteViewOP = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlView_North_b.add(dteViewOP);
							dteViewOP.setEnabled(true);
							dteViewOP.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor)dteViewOP.getDateEditor()).setEditable(false);
							dteViewOP.setDate(null);
							dteViewOP.addDateListener(new DateListener() {
								public void datePerformed(DateEvent event) {
									displayPastPayments(dteViewOP.getDate());
								}
							});	
						}
					}
				}
				{
					pnlView_North_c = new JPanel(new BorderLayout(5, 5));
					pnlView_North.add(pnlView_North_c, BorderLayout.CENTER);	
					pnlView_North_c.setPreferredSize(new java.awt.Dimension(300, 40));	
					{
						JButton btnLoad = new JButton("Load");
						pnlView_North_c.add(btnLoad, BorderLayout.CENTER);
						btnLoad.setPreferredSize(new Dimension(200, 40));
						btnLoad.setActionCommand("Load");
						btnLoad.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								System.out.println("");
								System.out.println("Load Action");

								blnViewMode = true;
								_OrderOfPayment.displayPastPayments(modelNewPayments, dteViewOP.getDate());

								btnPD_State(false, false, false, false);
								btnState(false, false, false, false, true);

								Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlViewPastOP);
								optionPaneWindow.dispose();
							}
						});
					}
				}
			}
			{
				pnlView_Center = new JPanel(new BorderLayout(3, 3));
				pnlViewPastOP.add(pnlView_Center, BorderLayout.CENTER);
				pnlView_Center.setBorder(components.JTBorderFactory.createTitleBorder("OP Transaction Details"));
				pnlView_Center.setPreferredSize(new Dimension(0, 30));

				{
					scrollNewPayments_past = new JScrollPane();
					pnlView_Center.add(scrollNewPayments_past, BorderLayout.CENTER);
					scrollNewPayments_past.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollNewPayments_past.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							if(tblNewPayments_past.isEnabled()){
								tblNewPayments_past.clearSelection();
							}
						}
					});
					{
						modelNewPayments_past = new modelOrderOfPayments_NewPayments();
						modelNewPayments_past.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								//Addition of rows
								if(e.getType() == 1){
									((DefaultListModel)rowHeaderNewPayments_past.getModel()).addElement(modelNewPayments_past.getRowCount());

									if(modelNewPayments_past.getRowCount() == 0){
										rowHeaderNewPayments_past.setModel(new DefaultListModel());
									}
								}
							}
						});

						tblNewPayments_past = new _JTableMain(modelNewPayments_past);
						scrollNewPayments_past.setViewportView(tblNewPayments_past);
						tblNewPayments_past.hideColumns("Client ID", "Project ID", "Unit ID");
						tblNewPayments_past.setSortable(false);					


					}
					{
						rowHeaderNewPayments_past = tblNewPayments_past.getRowHeader();
						rowHeaderNewPayments_past.setModel(new DefaultListModel());
						scrollNewPayments_past.setRowHeaderView(rowHeaderNewPayments_past);
						scrollNewPayments_past.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						scrollNewPayments_past.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNewPayments_past.getRowCount())));
					}

				}
			}

		}

		initializeComponents();
		setComponentsEnabled(pnlPaymentDetails, false);

		FncTables.bindColumnTables(tblPaymentList, tblPaymentListTotal);
		tblPaymentList.hideColumns("Part ID", "Check Type ID", "Bank ID", "Branch ID", "Receipt ID");

		//displayNewPayments();
		new Thread(this, "Order of Payments - New Payments").start();

		btnPD_State(false, false, false, false);
		btnState(false, false, false, false, true);

	} 

	private void initializeComponents() {
		lookupClient.setLookupSQL(_OrderOfPayment.getReservation());
		chkCreditItsReal.setEnabled(false);
		lookupParticular.setValue("106");
		txtParticularName.setText("RESERVATION (TR)");
		txtParticularAlias.setText("RESER");
	}


	//Enable. Disable
	private void btnPD_State(Boolean sPost, Boolean sEdit, Boolean sDelete, Boolean sCancel) {
		btnPD_Post.setEnabled(sPost);
		btnPD_Edit.setEnabled(sEdit);
		btnPD_Delete.setEnabled(sDelete);
		btnPD_Cancel.setEnabled(sCancel);
	}

	private void btnState(Boolean sNew, Boolean sEdit, Boolean sSave, Boolean sCancel, Boolean sRefresh) {
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
		btnRefresh.setEnabled(sRefresh);
	}

	private Object[] getCheckTypes() {
		pgSelect db = new pgSelect();
		db.select("select format('%s (%s)', check_desc, check_id) from mf_check_type order by rec_id;");

		if(db.isNotNull()){
			ArrayList<Object> listCheckType = new ArrayList<Object>();
			for(int x=0; x < db.getRowCount(); x++){
				listCheckType.add(db.getResult()[x][0]);
			}
			return listCheckType.toArray();
		}else{
			return null;
		}
	}

	private void clearTransactionDetails() {
		//cmbSelectType.setSelectedIndex(0);
		lookupClient.setValue(null);
		txtClientName.setText("");
		txtProjectID.setText("");
		txtProjectName.setText("");
		txtUnitID.setText("");
		txtUnitDescription.setText("");
		txtModelID.setText("");
		txtModelName.setText("");
		txtSellingPrice.setValue(null);
		txtSequence.setText(null);
		lblReservationType.setText("");
	}

	private void clearPaymentDetails() {
		lookupParticular.setValue(null);
		txtParticularName.setText("");
		txtParticularAlias.setText("");
		chkCashAmount.setSelected(false);
		txtCashAmount.setValue(null);
		chkCheckAmount.setSelected(false);
		txtCheckAmount.setValue(null);
		cmbCheckType.setSelectedIndex(0);
		dateCheckDate.setDate(Calendar.getInstance().getTime());
		txtCheckNo.setText("");
		txtNoOfCheck.setValue(null);
		txtBRSTN.setText("");
		txtAccountNo.setText("");
		lookupBank.setValue(null);
		txtBankName.setText("");
		lookupBranch.setValue(null);
		txtBranchName.setText("");
	}

	private void enableCheckPayment(Boolean enabled) {
		lblCheckType.setEnabled(enabled);
		cmbCheckType.setEnabled(enabled);
		lblCheckDate.setEnabled(enabled);
		dateCheckDate.setEnabled(enabled);
		lblCheckNo.setEnabled(enabled);
		txtCheckNo.setEnabled(enabled);
		lblNoOfCheck.setEnabled(enabled);
		txtNoOfCheck.setEnabled(enabled);
		lblAccountNo.setEnabled(enabled);
		txtAccountNo.setEnabled(enabled);
		lblBRSTN.setEnabled(enabled);
		txtBRSTN.setEnabled(enabled);
		lblBank.setEnabled(enabled);
		lookupBank.setEnabled(enabled);
		txtBankName.setEnabled(enabled);
		lblBranch.setEnabled(enabled);
		lookupBranch.setEnabled(enabled);
		txtBranchName.setEnabled(enabled);

		txtCheckNo.setEditable(enabled);
		txtAccountNo.setEditable(enabled);
		txtBRSTN.setEditable(enabled);

		if(dateCheckDate.getDate() == null){
			dateCheckDate.setDate(Calendar.getInstance().getTime());
		}
	}

	private void setParticular() {
		clearTransactionDetails();
		int selectedType = cmbSelectType.getSelectedIndex();
		if(selectedType == 0){
			lblClient.setText("Client Seq. #");
			lookupClient.setLookupSQL(_OrderOfPayment.getReservation());
			lookupClient.setPrompt("Client Seq. No.");
			lookupParticular.setValue("106");
			txtParticularName.setText("RESERVATION (TR)");
			txtParticularAlias.setText("RESER");
		}
		if(selectedType == 1){
			lblClient.setText("Client");
			lookupClient.setLookupSQL(_CARD.sqlClients2());//_CARD.sqlClients()
			lookupClient.setPrompt("Client ID");
			lookupParticular.setValue(null);
			txtParticularName.setText("");
			txtParticularAlias.setText("");
		}
		modelPaymentList.clear();
	}

	//Display
	private void displayNewPayments() {// Added by Alvin Gonzales (2015-05-30)
		try {
			rowHeaderNewPayments.setModel(new DefaultListModel());
			_OrderOfPayment.displayNewPayments(modelNewPayments);
			scrollNewPayments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNewPayments.getRowCount())));
			tblNewPayments.packAll();
		} catch (ArrayIndexOutOfBoundsException e) { }
	}

	private void displayReturnedPaymentTransactionDetails(String client_seqno) {// Added by Alvin Gonzales (2015-06-02)
		Object[] data = _OrderOfPayment.getTransactionDetails(client_seqno);

		if(data != null){
			cmbSelectType.setSelectedIndex(1);
			lblReservationType.setText(String.format("[ %s ]", (String) data[0]));
			lookupClient.setValue((String) data[1]);
			txtClientName.setText((String) data[2]);
			txtProjectID.setText((String) data[3]);
			txtProjectName.setText((String) data[4]);
			txtUnitID.setText((String) data[5]);
			txtUnitDescription.setText((String) data[6]);

			try {
				txtSequence.setText(Integer.toString((Integer) data[7]));
			} catch (NullPointerException ex) {
				System.out.println("");
				System.out.println("Zero unit sequence");
				txtSequence.setText("");

			}

			txtModelID.setText((String) data[8]);
			txtModelName.setText((String) data[9]);
			txtSellingPrice.setValue((BigDecimal) data[10]);
		}

		getTransactionPaymentList(client_seqno, modelPaymentList, rowHeaderPaymentList);
	}

	private void displayCredit(String actionCommand, String client_seqno, String title) {
		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, new Dimension(600, 420), null, title, _OrderOfPayment.sqlCreditPayments(client_seqno), true);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[][] data = dlg.getResult();

		if (data != null) {
			String part_id = lookupParticular.getValue();
			String part_name = txtParticularName.getText();
			String payment_type = "CASH";

			if(client_seqno != null){
				part_id = "106";
				part_name = "RESERVATION";
			}

			String preview = String.format("%s%n%n", "Are all entries correct?");
			preview = preview + String.format("    %-6s   %-50s %12s     %n", "OR #", "Client Name", "Amount");

			for(int x=0; x < data.length; x++){
				String client_name= (String) data[x][0];
				String receipt_no = (String) data[x][2];
				BigDecimal amount = (BigDecimal) data[x][3];				
				preview = preview + String.format("  • %-6s - %-50s %12s     %n", receipt_no, client_name, FncBigDecimal.format(amount));
			}

			preview = preview + String.format("%n", "");

			UIManager.put("OptionPane.messageFont", new Font(Font.MONOSPACED, Font.PLAIN, 12)); //change the font of optionpane to display the text properly/
			int option = JOptionPane.showOptionDialog(getContentPane(), preview, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"OK", "Cancel"}, 1);
			UIManager.put("OptionPane.messageFont", FncLookAndFeel.systemFont_Plain); // change the font of optionpane back to default.

			if(option == 1){
				return;
			}

			for(int x=0; x < data.length; x++){
				System.out.println("");
				System.out.println("I went here!");

				String receipt_no = (String) data[x][2];
				BigDecimal amount = (BigDecimal) data[x][3];
				Date tran_date = (Date) data[x][5];
				String pay_rec_id = (String) data[x][6];
				System.out.printf("Trans Date : " + tran_date);

				String bankID=null, bankBranchID=null, acctNo=null, checkNo=null, brstn=null, 
						checkType=null, checkTypeName=null, bankName=null, bankBranchName=null;
				Date check_date=null;
				Object[] pmt_dtl = _OrderOfPayment.getPmtDtls(receipt_no, tran_date, pay_rec_id);

				try {
					payment_type = pmt_dtl[0].toString();
				} catch (NullPointerException e) {
					payment_type = "A";
				}

				try {
					bankID = pmt_dtl[1].toString();
				} catch (NullPointerException e) {
					bankID = null;
				}

				try {
					bankBranchID = pmt_dtl[2].toString();
				} catch (NullPointerException e) {
					bankBranchID = null;
				}

				try {
					acctNo = pmt_dtl[3].toString();
				} catch (NullPointerException e) {
					acctNo = null;
				}

				try {
					checkNo = pmt_dtl[4].toString();
				} catch (NullPointerException e) {
					checkNo = null;
				}

				try {
					check_date = (Date) pmt_dtl[5];
				} catch (NullPointerException e) {
					check_date = null;
				}

				try {
					brstn = pmt_dtl[6].toString();
				} catch (NullPointerException e) {
					brstn = null;
				}

				try {
					checkType = pmt_dtl[7].toString();
				} catch (NullPointerException e) {
					checkType = null;
				}

				try {
					checkTypeName = pmt_dtl[8].toString();
				} catch (NullPointerException e) {
					checkTypeName = null;
				}

				try {
					bankName = pmt_dtl[9].toString();
				} catch (NullPointerException e) {
					bankName = null;
				}

				try {
					bankBranchName = pmt_dtl[10].toString();
				} catch (NullPointerException e) {
					bankBranchName = null;
				}

				if(chkCashAmount.isSelected() && (amount == null || amount.compareTo(FncBigDecimal.zeroValue()) <= 0)){
					JOptionPane.showMessageDialog(getContentPane(), "Please input valid amount.", "Post", JOptionPane.WARNING_MESSAGE);
					return;
				}

				ArrayList<String> listTaggedReceipt = new ArrayList<String>();

				for (int y=0; y < modelPaymentList.getRowCount(); y++) {
					String tagged_receipt_no = (String) modelPaymentList.getValueAt(y, 13);
					listTaggedReceipt.add(tagged_receipt_no);
				}

				ArrayList<String> listReceiptType = new ArrayList<String>();

				for (int z=0; z < tblPaymentList.getRowCount(); z++) {
					String receipt_type = (String) modelPaymentList.getValueAt(z, 14);
					listReceiptType.add(receipt_type);
				}


				if(!listReceiptType.contains(receipt_id) && listReceiptType.size() > 0){
					JOptionPane.showMessageDialog(OrderOfPayment.this.getTopLevelAncestor(), "Different receipt cannot be issued simultaneously.", "Post", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if(tblPaymentList.isEnabled()) {
					modelPaymentList.addRow(new Object[] {
							part_id, 
							part_name, 
							amount, 
							payment_type, 
							checkNo, 
							checkType, 
							checkTypeName, 
							check_date, 
							acctNo, 
							bankID, 
							bankName, 
							bankBranchID, 
							bankBranchName, 
							receipt_no, 
							receipt_id, 
							receipt_type, 
							tran_date, 
							brstn, 
							true, 
							pay_rec_id
					});
				}
			}

			tblPaymentList.packAll();
			btnState(true, false, true, true, false);
		} else {

		}
	}

	public void displayFromCARD(String entity_id, String entity_name, String proj_id, String proj_name, String pbl_id, String unit_description, Integer seq_no,
			String model_id, String model_name, BigDecimal selling_price, Object[][] data) {

		cmbSelectType.setSelectedIndex(1);
		lookupClient.setValue(entity_id);
		txtClientName.setText(entity_name);
		txtProjectID.setText(proj_id);
		txtProjectName.setText(proj_name);
		txtUnitID.setText(pbl_id);
		txtUnitDescription.setText(unit_description);
		txtSequence.setText(Integer.toString(seq_no));
		txtModelID.setText(model_id);
		txtModelName.setText(model_name);
		txtSellingPrice.setValue(selling_price);

		for(int x=0; x < data.length; x++){
			String part_id = (String) data[x][0];
			BigDecimal amount = (BigDecimal) data[x][2];
			Boolean toAdd = true;

			if(part_id.equals("106")){
				toAdd = validateReservation(amount);
			}

			if(toAdd){
				modelPaymentList.addRow(data[x]);
			}
		}

		tblPaymentList.packAll();
		tblPaymentList.setEnabled(true);
		clearPaymentDetails();
		tabPayments.setSelectedIndex(0);
		tabPayments.setEnabledAt(1, false);
		//setComponentsEnabled(pnlPaymentDetails, false);
		btnPD_State(true, !cmbSelectType.isEnabled(), !cmbSelectType.isEnabled(), false);

		grpNewEdit.setSelectedButton(btnNew);
		newTransaction();
		btnState(false, false, true, true, true);
	}

	private void displayReservationClient(Object[] data) {
		modelPaymentList.clear();
		modelPaymentListTotal.setValueAt(0.00, 0, 2);

		String client_seq_no = (String) data[0];
		String client_name = (String) data[2];
		String proj_id = (String) data[3];
		String proj_name = (String) data[4];
		String unit_id = (String) data[5];
		String unit_description = "";

		if(_HoldingAndReservation.withOtherUnit(unit_id)){
			unit_description = _HoldingAndReservation.getMergeUnitDesc(unit_id);
		}else{
			unit_description = (String) data[6];
		}
		//String unit_description = (String) data[6];

		unit_seq_no = null;
		pbl_id = (String) data[6];
		if(data[7] != null){
			//unit_seq_no = Integer.toString((Integer) data[7]);
			unit_seq_no = Integer.parseInt(data[7].toString());
			txtSequence.setText(unit_seq_no.toString());
		} 
		else {
			txtSequence.setText(null);
		}
		String model_id = (String) data[8];
		String model_name = "";
		if(_HoldingAndReservation.withOtherUnit(unit_id)){
			model_name = _HoldingAndReservation.getMergeModelDesc(unit_id);
		}else{
			model_name = (String) data[9];
		}

		BigDecimal selling_price = (BigDecimal) data[10];

		txtClientName.setText(client_name);
		txtProjectID.setText(proj_id);
		txtProjectName.setText(proj_name);
		txtUnitID.setText(unit_id);
		txtUnitDescription.setText(unit_description);

		txtModelID.setText(model_id);
		txtModelName.setText(model_name);
		txtSellingPrice.setValue(selling_price);

		if((Boolean) data[11]){
			lblReservationType.setText("[ TEMPORARY RESERVATION ]");

			/*	Removed by Mann2x; Date Removed: May 23, 2017; As requested by Sir Del;
			if(hasHoldingFee(unit_id, client_name)==true)
			{
				displayCredit("Holding", client_seq_no, "Hold Payments");
			}
			 */

			//_OrderOfPayment.displayHoldingAsReservation(modelPaymentList, lookupClient.getValue());
		}else{
			lblReservationType.setText("[ OFFICIAL RESERVATION ]");
		}
		tblPaymentList.packAll();

		btnState(true, false, true, true, false);
	}

	private void displayNewPaymentClient(String entity_id, String proj_id, String pbl_id, Integer seq_no, String server_id) {
		Object[] data = null;
		if (server_id.equals("")) {
			data = _OrderOfPayment.getNewPaymentsData(entity_id, proj_id, pbl_id, seq_no);
		}else {
			data = _OrderOfPayment.getNewPaymentsData_itsreal(entity_id, proj_id, pbl_id, seq_no);
		}

		

		modelPaymentList.clear();
		modelPaymentListTotal.setValueAt(0.00, 0, 2);

		//try { status_id = pricelist_dtl[0].toString();} catch (NullPointerException e) { status_id = "" ; }
		String clientname = "", projID = "", projName = "", unitID = "", 
				unitDesc = "", modelID = "", modelName = "", otherLot = "";
		Integer sequence = null;
		BigDecimal sellingPrice_bd = new BigDecimal(0.00);		

		try { clientname = data[1].toString();} catch (NullPointerException e) { clientname = _OrderOfPayment.sql_getEntityName(entity_id) ; }

		System.out.printf("clientname : " + clientname);

		try {
			projID = proj_id;
		} catch (NullPointerException e) {
			projID = "";
		} //Adjusted by Del Gonzales 2017-03-24

		//try { projID = (String) data[2];} catch (NullPointerException e) { projID = "" ; }

		try {
			projName = (String) data[3];
		} catch (NullPointerException e) {
			projName = "" ;
		}

		try {
			unitID = (String) data[4];
		} catch (NullPointerException e) {
			unitID = ""; 
		}

		try { unitDesc = (String) data[5];} catch (NullPointerException e) { unitDesc = "" ; }
		try { modelID = (String) data[7];} catch (NullPointerException e) { modelID = "" ; }
		try { modelName = (String) data[8];} catch (NullPointerException e) { modelName = "" ; }
		try { sequence = (Integer) data[6];} catch (NullPointerException e) { sequence = 1 ; }

		try {
			sellingPrice_bd = (BigDecimal) data[9];
		} catch (NullPointerException e) {

		}

		try {
			otherLot = (String) data[11];
		} catch (NullPointerException ex) {
			otherLot = ""; 
		}

		txtClientName.setText(clientname);
		txtProjectID.setText(projID);
		txtProjectName.setText(projName);
		txtUnitID.setText(unitID);
		txtUnitDescription.setText(unitDesc);
		txtSequence.setText(sequence.toString());
		txtModelID.setText(modelID);
		txtModelName.setText(modelName);
		txtSellingPrice.setValue(sellingPrice_bd);
		txtOtherLot.setText(otherLot);

		_OrderOfPayment.displayCreditPayments(modelCreditPayments, entity_id);
		tblCreditPayments.packAll();

		btnState(true, false, false, true, false);
	}

	private void displayPastPayments(Date tran_date) {// Added by Alvin Gonzales (2015-05-30)
		try {
			rowHeaderNewPayments_past.setModel(new DefaultListModel());
			_OrderOfPayment.displayPastPayments(modelNewPayments_past, tran_date);
			scrollNewPayments_past.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNewPayments_past.getRowCount())));
			tblNewPayments_past.packAll();
		} catch (ArrayIndexOutOfBoundsException e) { }
	}


	//Action Performed
	@Override
	public void actionPerformed(ActionEvent e) { 
		String action = e.getActionCommand();
		String selectedTransaction = (String) cmbSelectType.getSelectedItem();

		if(action.equals("Current Date")){
			current_date = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
			int option = JOptionPane.showOptionDialog(getContentPane(), current_date, "Current Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
			if(option == JOptionPane.OK_OPTION){}
			System.out.printf("Current Date: %s%n", current_date.getDate());
		}

		if(action.equals("PD_Post")){postTransaction();}

		if(action.equals("PD_Edit")){editParticular();}

		if(action.equals("PD_Delete")){deleteParticular();}

		if(action.equals("PD_Cancel")){cancelParticular();}

		if(action.equals("New")){grpNewEdit.setSelectedButton(e);newTransaction();}

		if(action.equals("Edit")){grpNewEdit.setSelectedButton(e);editTransaction();}
		
//		MODIFIED BY MONIQUE DTD 2023-07-25; REFER TO DCRF#2693
//		if(action.equals("Save")){
//			saveOP(selectedTransaction, action);
//			
//		}
		
		if(action.equals("Save")){
			
			if(chkCreditItsReal.isSelected()) {
				if(JOptionPane.showConfirmDialog(getContentPane(), "Is Credit from ItsReal?", "Confirm", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					saveOP(selectedTransaction, action);
				}
			} else {
				saveOP(selectedTransaction, action);
			}
		}

		if(action.equals("Cancel")){grpNewEdit.clearSelection(); cancelTransaction();}

		if(action.equals("Refresh")){grpNewEdit.clearSelection(); refreshFields(); blnViewMode = false;}

		if(action.equals("ViewPastOP")){viewPastOP();}
	}

	private void saveOP(String selectedTransaction, String action) {
		if(tblPaymentList.getRowCount() < 1){
			JOptionPane.showMessageDialog(getContentPane(), "Please add cash or check payment to save.", "Save", JOptionPane.WARNING_MESSAGE);
			return;
		}
		

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Save", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			if (selectedTransaction.equals("RESERVATION (TR)")) {
				Boolean isTR = lblReservationType.getText().equals("[ TEMPORARY RESERVATION ]");

				if(_OrderOfPayment.saveReservation(modelPaymentList, lookupClient.getValue(), 
						(BigDecimal) modelPaymentListTotal.getValueAt(0, 2), isTR, chkCreditItsReal.isSelected())) {
					JOptionPane.showMessageDialog(getContentPane(), "Reservation payment has been saved.", "Save", JOptionPane.PLAIN_MESSAGE);
					clearTransactionDetails();
					setComponentsEnabled(pnlCenter, false);
					modelPaymentList.clear();
					btnState(false, false, false, false, false);
					tabPayments.setEnabledAt(1, true);
					tabPayments.setSelectedIndex(1);
					displayNewPayments();
				}
			} else { /**		NEW PAYMENT		**/

				String entity_id = lookupClient.getValue();
				String proj_id = txtProjectID.getText();
				String unit_id = txtUnitID.getText();
				String seq_no = txtSequence.getText();
				BigDecimal totalAmount = (BigDecimal) modelPaymentListTotal.getValueAt(0, 2);

				/**	Modification Mark: Begin	**/
				/**	Modified by Mann2x; Date Modified June 19, 2017; Partial payments must not be accepted without promissory notes;	*/
				/**	Mann2x's Additional Filters	**/
				Boolean blnProceed = false;
				Boolean blnPartial = false;
				String strPblID = FncGlobal.GetString("select pbl_id from mf_unit_info where unit_id = '"+unit_id+"'");

				/**	Modified by Mann2x; Date Modified May 21, 2020; Included the moratorium period;	*/
				/**	Modification Mark: Begin	**/

				Boolean blnPastDue = false; 
				BigDecimal bdToUpdate = null; 
				
				Boolean bln_afterECQ = false; 
				
				try {
					bln_afterECQ = _CARD.afterECQ(entity_id, proj_id, strPblID, Integer.parseInt(seq_no)); 
				} catch (NullPointerException e) {
					bln_afterECQ = false;  
				}
				
				if (bln_afterECQ) {
					blnPastDue = FncGlobal.GetBoolean("select (case when get_card_pmt_status = 'CURRENT' THEN false else true end) \n" +
							"from get_card_pmt_status('"+entity_id+"', '"+proj_id+"', '"+strPblID+"', '"+seq_no+"'::int)");
					
					bdToUpdate = FncGlobal.GetDecimal("SELECT coalesce(sum(c_amount), 0)::numeric(19, 2) \n" +
							"from view_card_dues('"+entity_id+"', '"+proj_id+"', '"+strPblID+"', '"+seq_no+"'::int, now()::TIMESTAMP, FALSE) \n" +
							"where c_scheddate::date <= now()::date and c_part_desc = 'DP'");
				} else {
					blnPastDue = FncGlobal.GetBoolean("select (case when get_card_pmt_status_with_moratorium = 'CURRENT' THEN false else true end) \n" +
							"from get_card_pmt_status_with_moratorium('"+entity_id+"', '"+proj_id+"', '"+strPblID+"', '"+seq_no+"'::int)");
					
					/*
					bdToUpdate = FncGlobal.GetDecimal("SELECT coalesce(sum(c_amount), 0)::numeric(19, 2) \n" +
							"from view_card_dues_regular('"+entity_id+"', '"+proj_id+"', '"+strPblID+"', '"+seq_no+"'::int, now()::TIMESTAMP, FALSE) \n" +
							"where c_scheddate::date <= now()::date and c_part_desc = 'DP'");
					*/
					
					bdToUpdate = FncGlobal.GetDecimal("\n" + 
							"select sum(a.c_amount)\n" + 
							"from\n" + 
							"(\n" + 
							"	select coalesce(sum(c_amount), 0)::numeric(19, 2) as c_amount\n" + 
							"	from view_card_dues_regular('"+entity_id+"', '"+proj_id+"', '"+strPblID+"', '"+seq_no+"'::int, now()::TIMESTAMP, FALSE) \n" +
							"	where c_scheddate::date < now()::date \n" +
							"	union\n" + 
							"	select coalesce(sum(c_amount), 0)::numeric(19, 2) as c_amount\n" + 
							"	from view_card_dues_moratorium('"+entity_id+"', '"+proj_id+"', '"+strPblID+"', '"+seq_no+"'::int, now()::TIMESTAMP, FALSE)\n" +
							"	where c_scheddate::date < now()::date \n" +
							") a"); 
				}

				/**	Modification Mark: End		**/

				BigDecimal bln_rounded1 = FncGlobal.GetDecimal("select '"+totalAmount.toString()+"'::numeric(19, 2)");
				BigDecimal bln_rounded2 = FncGlobal.GetDecimal("select '"+bdToUpdate.toString()+"'::numeric(19, 2)");

				blnPartial = (bln_rounded1.compareTo(bln_rounded2)==-1) ? true : false;

				Integer intCountPN = FncGlobal.GetInteger("select count(*)::int \n" +
						"from rf_pn_header x \n" +
						"inner join rf_pn_detail y on x.pn_no = y.pn_no \n" +
						"where x.entity_id = '"+entity_id+"' and x.proj_id = '"+proj_id+"' and x.pbl_id = '"+strPblID+"' and x.seq_no::int = '"+seq_no+"'::int \n" +
						"and y.commit_date::Date >= now()::date and x.payment_amount::numeric(19, 2) >= '"+totalAmount+"'::numeric(19, 2)");

				System.out.println("With PN");
				System.out.println("select count(*)::int \n" +
						"from rf_pn_header x \n" +
						"inner join rf_pn_detail y on x.pn_no = y.pn_no \n" +
						"where x.entity_id = '"+entity_id+"' and x.proj_id = '"+proj_id+"' and x.pbl_id = '"+strPblID+"' and x.seq_no::int = '"+seq_no+"'::int \n" +
						"and y.commit_date::Date >= now()::date and x.payment_amount::numeric(19, 2) >= '"+totalAmount+"'::numeric(19, 2)");

				if (!blnPastDue) {
					blnProceed = true;
				} else {
					if (blnPartial) {
						if (intCountPN>0) {
							blnProceed = true;
						} else {
							blnProceed = false;
						}
					} else {
						blnProceed = true;
					}

				}
				
				System.out.println("");
				System.out.println("totalAmount: " + bln_rounded1);
				System.out.println("bdToUpdate: " + bln_rounded2);
				System.out.println("totalAmount.compareTo(bdToUpdate): " + bln_rounded1.compareTo(bln_rounded2)); 
				System.out.println("blnProceed: " + blnProceed);
				System.out.println("blnPartial: " + blnPartial);
				System.out.println("blnPastDue: " + blnPastDue);
				System.out.println("bdToUpdate: " + bdToUpdate);
				System.out.println("intCountPN: " + intCountPN);
				
				if (strParticular==null) {
					strParticular = (String) modelPaymentList.getValueAt(tblPaymentList.getSelectedRow(), 1); 
				}
				
				System.out.println("strParticular: " + strParticular);
				
				/**	Mann2x's Additional Filters	**/	

				String hasSaved = "";
				Boolean hasSaved_boolean = false;
				String message = "";
				String clientSeqNo = "";
				Boolean blnApply = FncGlobal.GetBoolean("select coalesce(apply_ledger, false) from mf_pay_particular where partdesc = '"+strParticular+"'"); 
				
				if (FncGlobal.getDateSQL().equals("2021-03-23") && UserInfo.Branch.equals("10")) {
					blnProceed = true; 
				}
				
				if(UserInfo.EmployeeCode.equals("900876")) {
					blnProceed = true; 
				}
				
				if (FncGlobal.getDateSQL().equals("2021-04-13")) {
					blnProceed = true; 
				}
				
				if (!blnProceed && blnApply) {
					JOptionPane.showMessageDialog(getContentPane(), "This client's payment status is past due.\nPartial payments will not be accepted without\na PROMISSORY NOTE. \nAmount to update is "+bdToUpdate+" (Moratorium and Regular).", 
							"Partial Payment", JOptionPane.INFORMATION_MESSAGE);
				}

				System.out.println("blnProceed: "+blnProceed);
				System.out.println("Apply Ledger: "+blnApply);
				

				if (blnProceed || (!blnProceed && !blnApply)) {
					if(grpNewEdit.getActionCommand().equals("New")){

						message = "New payment has been saved.";

						Object[] op_dtl = _OrderOfPayment.saveNewPayment(modelPaymentList, entity_id, proj_id, unit_id, seq_no, totalAmount, chkCreditItsReal.isSelected()); 
						//Object[] op_dtl =  _OrderOfPayment.saveNewPayment(modelPaymentList, entity_id, proj_id, unit_id, seq_no, totalAmount);; 
						hasSaved = op_dtl[0].toString();						
						clientSeqNo = op_dtl[1].toString();					

						System.out.printf("hasSaved : " + hasSaved);
						System.out.printf("clientSeqNo : " + clientSeqNo);				

					} else {

						clientSeqNo = lblReservationType.getText().replaceAll("\\[|\\]", "").trim();
						message = "Payment has been updated.";
						hasSaved_boolean = _OrderOfPayment.updateNewPayment(modelPaymentList, clientSeqNo, totalAmount);

					}

					if(hasSaved.equals("TRUE") || hasSaved_boolean==true){
						JOptionPane.showMessageDialog(getContentPane(), message, action, JOptionPane.PLAIN_MESSAGE);

						//clearTransactionDetails();
						setComponentsEnabled(pnlCenter, false);
						modelPaymentList.clear();
						cmbSelectType.setEnabled(true);
						lookupClient.setEditable(true);
						//tblPaymentList.setEnabled(true);
						tblNewPayments.setEnabled(true);
						grpNewEdit.clearSelection();
						tabPayments.setEnabledAt(1, true);
						btnState(false, false, false, false, true);
						tabPayments.setEnabledAt(1, true);

						JOptionPane.showMessageDialog(getContentPane(), "Client Sequence No. " + clientSeqNo, "Client Sequence No.", 
								JOptionPane.INFORMATION_MESSAGE);

						tabPayments.setSelectedIndex(1);
						displayNewPayments();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Saving did not proceed.");
				}
				/**	Modification Mark: End	**/

				/*

				if(grpNewEdit.getActionCommand().equals("New")){

				message = "New payment has been saved.";

				Object[] op_dtl = _OrderOfPayment.saveNewPayment(modelPaymentList, entity_id, proj_id, unit_id, seq_no, totalAmount);; 
				//Object[] op_dtl =  _OrderOfPayment.saveNewPayment(modelPaymentList, entity_id, proj_id, unit_id, seq_no, totalAmount);; 
				hasSaved = op_dtl[0].toString();						
				clientSeqNo = op_dtl[1].toString();					

				System.out.printf("hasSaved : " + hasSaved);
				System.out.printf("clientSeqNo : " + clientSeqNo);				

			} else {

				clientSeqNo = lblReservationType.getText().replaceAll("\\[|\\]", "").trim();
				message = "Payment has been updated.";
				hasSaved_boolean = _OrderOfPayment.updateNewPayment(modelPaymentList, clientSeqNo, totalAmount);

			}

			if(hasSaved.equals("TRUE") || hasSaved_boolean==true){
				JOptionPane.showMessageDialog(getContentPane(), message, action, JOptionPane.PLAIN_MESSAGE);

				//clearTransactionDetails();
				setComponentsEnabled(pnlCenter, false);
				modelPaymentList.clear();
				cmbSelectType.setEnabled(true);
				lookupClient.setEditable(true);
				//tblPaymentList.setEnabled(true);
				tblNewPayments.setEnabled(true);
				grpNewEdit.clearSelection();
				tabPayments.setEnabledAt(1, true);
				btnState(false, false, false, false, true);
				tabPayments.setEnabledAt(1, true);

				JOptionPane.showMessageDialog(getContentPane(), "Client Sequence No. " + clientSeqNo, "Client Sequence No.", 
						JOptionPane.INFORMATION_MESSAGE);

				tabPayments.setSelectedIndex(1);
				displayNewPayments();

				 */
			}
			chkCreditItsReal.setSelected(false);
		}
	}

	private void setPaymentDetailsEnable(Boolean enable) {// Added by Alvin Gonzales (2015-06-02)
		lblParticular.setEnabled(enable);
		lookupParticular.setEnabled(enable);
		txtParticularName.setEnabled(enable);
		txtParticularAlias.setEnabled(enable);

		chkCashAmount.setEnabled(enable);
		chkCashAmount.setSelected(enable);
		txtCashAmount.setEnabled(enable);

		chkCheckAmount.setEnabled(enable);
		tblCreditPayments.setEnabled(enable);
		btnAROR.setEnabled(enable);
	}

	private void postTransaction() {// Added by Alvin Gonzales (2015-06-02)
		if(toPost()){
			if (lookupParticular.getValue() == null) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select particular.", "Post", JOptionPane.WARNING_MESSAGE);
				return;
			}			

			if (chkCashAmount.isSelected()==true) {
				postCashTransaction();
			}

			if (chkCheckAmount.isSelected()==true) {
				postCheckTransaction();
			}

			/*if(chkCashAmount.isSelected()){}

			if(chkCheckAmount.isSelected()){}*/

			clearPaymentDetails();
			btnPD_State(true, false, false, false);

			tblPaymentList.setEnabled(true);
			tblPaymentList.packAll();

		}
	}

	private void postCashTransaction() {

		String part_id = lookupParticular.getValue();
		String part_name = txtParticularName.getText();
		BigDecimal amount = (BigDecimal) txtCashAmount.getValued();
		String payment_type = "CASH";
		Double op_amount	= 0.00;						
		try { op_amount	= Double.parseDouble(modelPaymentListTotal.getValueAt(0,2).toString()); } 
		catch (NullPointerException e) { op_amount	= 0.00; }
		BigDecimal op_amount_bd 	= new BigDecimal(op_amount);

		if(chkCashAmount.isSelected() && (amount == null || amount.compareTo(FncBigDecimal.zeroValue()) <= 0)){
			JOptionPane.showMessageDialog(getContentPane(), "Please input valid amount.", "Post", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if(tblPaymentList.isEnabled())
		{
			if (part_id.equals("106")&&validateReservation(op_amount_bd.add(amount))==false)
			{
				if (amount.subtract(excess).toString().equals("0.00")) {

				} else {
					modelPaymentList.addRow(new Object[]{part_id, part_name, amount.subtract(excess), payment_type, 
							null, null, null, null, null, null, null, null, null, null, receipt_id, receipt_alias, null, null, false});
				}	
			} else if (part_id.equals("106") && validateReservation(op_amount_bd.add(amount))==true) {						
				modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, null, null, null, null, null, null, null, null, null, null, receipt_id, receipt_alias, null, null, false});
			} else if (part_id.equals("033")) {
				Integer intOption = null;

				Double dp_paid 		= sql_getDP_paid();
				Double dp_required  = sql_getDP_required();
				Double dp_amount	= Double.parseDouble(txtCashAmount.getText().replace(",",""));
				Double excess_amt   = dp_required - dp_paid - dp_amount  - op_amount;

				/*	Modified by Mann2x; Date Modified: May 26, 2020; Included moratorium options;	*/
				String due_type = ""; 
				String strPBLID = FncGlobal.GetString("select pbl_id from mf_unit_info where proj_id = '"+txtProjectID.getText()+"' and unit_id = '"+txtUnitID.getText()+"'"); 

				if (_OrderOfPayment.withRemainingMoratorium(lookupClient.getValue(), txtProjectID.getText(), strPBLID, txtSequence.getText())) {
					if (pastDuePriorECQ(lookupClient.getValue(), txtProjectID.getText(), strPBLID, txtSequence.getText())) {
						JOptionPane.showMessageDialog(this, "This account is not current. Due type will automatically be set as `Regular`.");
						intOption = JOptionPane.YES_OPTION;
					} else if (!_OrderOfPayment.withRemainingRegular(lookupClient.getValue(), txtProjectID.getText(), strPBLID, txtSequence.getText())) {
						JOptionPane.showMessageDialog(this, "This account no longer have any regular dues. Due type will automatically be set as `Moratorium`.");
						intOption = JOptionPane.CANCEL_OPTION;					          
					} else {
						intOption = JOptionPane.showOptionDialog(null, "This payment is intended for which due?", "Payment Mode", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
								new Object[] {" Regular  ", "Moratorium"}, JOptionPane.YES_OPTION);
					}
				} else {
					intOption = JOptionPane.YES_OPTION;
				}
				
				due_type = (String) ((intOption==JOptionPane.YES_OPTION)?"R":"M"); 
				modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, null, null, null, null, null, null, null, null, null, null, receipt_id, receipt_alias, null,  null, false, null, "", due_type});
				
			} else if (part_id.equals("197") || part_id.equals("268")) {
				modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, null, null, null, null, null, null, null, null, null, null, receipt_id, receipt_alias, null,  null, false, null, strSelectedLot});
				strSelectedLot = ""; 
			} else {						
				modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, null, null, null, null, null, null, null, null, null, null, receipt_id, receipt_alias,null,  null, false});
			}	
		}
		else {					
			int row = tblPaymentList.getSelectedRow();
			modelPaymentList.setValueAt(part_id, row, 0);
			modelPaymentList.setValueAt(part_name, row, 1);
			modelPaymentList.setValueAt(amount, row, 2);
			modelPaymentList.setValueAt(payment_type, row, 3);
			modelPaymentList.setValueAt(receipt_id, row, 14);
			modelPaymentList.setValueAt(receipt_alias, row, 15);						

			//clearPaymentDetails();
			//btnPD_State(true, false, false, false);
		}			
	}

	private void postCheckTransaction() {
		String part_id = lookupParticular.getValue();
		String part_name = txtParticularName.getText();
		BigDecimal amount = (BigDecimal) txtCheckAmount.getValued();
		String payment_type = "CHECK";
		String check_type_id = ((String)cmbCheckType.getSelectedItem()).split("[\\(\\)]")[1];
		String check_type_name = ((String)cmbCheckType.getSelectedItem()).split("[\\(\\)]")[0];
		Date check_date = dateCheckDate.getDate();
		System.out.printf("Display Check No: (%s)%n", txtCheckNo.getText().replaceAll("[^\\d]", ""));
		String check_no = txtCheckNo.getText().replaceAll("[^\\d]", "");
		int no_of_check = (Integer) txtNoOfCheck.getIntegerValue();
		String account_no = txtAccountNo.getText();
		String brstn = txtBRSTN.getText();
		String bank_id = lookupBank.getValue();
		String bank_name = txtBankName.getText();
		String branch_id = lookupBranch.getValue();
		String branch_name = txtBranchName.getText();
		String receiptID = "";
		String receiptTypeID = "";

		System.out.printf("Display check no: (%s)%n", check_no);
		Long new_check_no = Long.valueOf(check_no);
		System.out.printf("Display Value of Check No: %s%n", new_check_no);

		Calendar cal = Calendar.getInstance();
		cal.setTime(check_date);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DAY_OF_MONTH);

		Double op_amount	= 0.00;						
		try { op_amount	= Double.parseDouble(modelPaymentListTotal.getValueAt(0,2).toString()); } catch (NullPointerException e) { op_amount	= 0.00; }
		BigDecimal op_amount_bd 	= new BigDecimal(op_amount);
		Double tot_check_amt = Double.parseDouble(txtCheckAmount.getText().replace(",","")) * no_of_check;
		BigDecimal tot_check_amt_bd = new BigDecimal(tot_check_amt);

		System.out.println("");
		System.out.println("SQL Date: " + FncGlobal.getDateSQL());

		System.out.println("");
		System.out.println("strSelectedLot: "+strSelectedLot); 


		if(tblPaymentList.isEnabled())
		{
			if (part_id.equals("106"))
			{
				if (validateReservation(op_amount_bd.add(tot_check_amt_bd))==false){}
				else if (validateReservation(op_amount_bd.add(tot_check_amt_bd))==true)
				{						
					for(int x=0; x < no_of_check; x++){
						cal.set(year, month++, date);						

						if (cal.getTime().compareTo(FncGlobal.dateFormat(FncGlobal.getDateSQL()))<=0) {
							receiptID = receipt_id;
							receiptTypeID = receipt_alias;
						} else {
							receiptID = "03";
							receiptTypeID = "AR";
						}

						modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, Long.toString(new_check_no++), check_type_id,
								check_type_name, cal.getTime(), account_no,	bank_id, bank_name, branch_id, branch_name, null,
								receiptID, receiptTypeID, null, null, brstn});
					}
				}	
			} else if (part_id.equals("033")) {
				Double dp_paid 		= sql_getDP_paid();
				Double dp_required  = sql_getDP_required();
				Double dp_amount	= tot_check_amt;

				for(int x=0; x < no_of_check; x++){
					cal.set(year, month++, date);

					/*	Added by Mann2x; Date Added: July 5, 2017; DCRF# 159;	*/
					String strPBL = FncGlobal.GetString("select x.pbl_id from mf_unit_info x where x.unit_id = '"+txtUnitID.getText().toString()+"'");
					Boolean blnOR = FncGlobal.GetBoolean("select (case when (\n" +
							"select count(*) \n" +
							"from rf_buyer_status x \n" +
							"where x.entity_id = '"+lookupClient.getValue().toString()+"' and x.proj_id = '"+txtProjectID.getText().toString()+"' \n" +
							"and x.pbl_id = '"+strPBL+"' and x.seq_no::int = '"+txtSequence.getText()+"'::int \n" +
							"and byrstatus_id = '01' and x.status_id = 'A'\n" +
							") > 0 then true else false end)");
					String strSubProject = FncGlobal.GetString("SELECT sub_proj_id \n" +
							"from mf_unit_info \n" +
							"where proj_id = '"+txtProjectID.getText().toString()+"' and pbl_id = '"+strPBL+"'");
					Boolean blnBOI = FncGlobal.GetBoolean("SELECT (case when boi is null then false else true end) \n" +
							"FROM mf_sub_project \n" +
							"WHERE sub_proj_id = '"+strSubProject+"' and status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718
					Boolean blnLTS = FncGlobal.GetBoolean("SELECT (case when lts_date is null then false else true end) \n" +
							"FROM mf_sub_project \n" +
							"WHERE sub_proj_id = '"+strSubProject+"' AND status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718

					/*	Modified By Mann2x; Modified Date: October 27, 2016; A check can be considered as dated the day before it's check date;
					if (cal.getTime().compareTo(FncGlobal.dateFormat(FncGlobal.getDateSQL()))<=0) {
						receiptID = "03";
						receiptTypeID = "AR";
					}
					 */

					/*	Modified by Mann2x; Date Modified: July 5, 2017; DCRF# 159;
					if (_OrderOfPayment.CheckIfDated(cal.getTime().toString(), FncGlobal.getDateSQL())) {
					 */
					if (_OrderOfPayment.CheckIfDated(cal.getTime().toString(), FncGlobal.getDateSQL()) && blnOR && blnBOI && blnLTS) {
						receiptID = receipt_id;
						receiptTypeID = receipt_alias;							
					} else {
						receiptID = "03";
						receiptTypeID = "AR";						
					}

					System.out.println("");
					System.out.println("receiptID: " + receiptID);
					System.out.println("receiptTypeID: " + receiptTypeID);

					/* As can be seen in this code, the "receiptID" value is hardcoded.
					modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, Long.toString(new_check_no++),
							check_type_id, check_type_name, cal.getTime(), account_no, bank_id, 
							bank_name, branch_id, branch_name, null, "03", 
							receiptTypeID, null, brstn, false});
					 */

					/*	Modified by Mann2x; Date Modified: May 26, 2020; Included moratorium options;	*/
					String due_type = ""; 
					String strPBLID = FncGlobal.GetString("select pbl_id from mf_unit_info where proj_id = '"+txtProjectID.getText()+"' and unit_id = '"+txtUnitID.getText()+"'"); 

					if (_OrderOfPayment.withRemainingMoratorium(lookupClient.getValue(), txtProjectID.getText(), strPBLID, txtSequence.getText())) {
						Integer intOption; 

						if (_OrderOfPayment.withRemainingMoratorium(lookupClient.getValue(), txtProjectID.getText(), strPBLID, txtSequence.getText())) {
							if (pastDuePriorECQ(lookupClient.getValue(), txtProjectID.getText(), strPBLID, txtSequence.getText())) {
								JOptionPane.showMessageDialog(this, "This account is not current. Due type will automatically be set as `Regular`.");
								intOption = JOptionPane.YES_OPTION;
							} else if (!_OrderOfPayment.withRemainingRegular(lookupClient.getValue(), txtProjectID.getText(), strPBLID, txtSequence.getText())) {
								JOptionPane.showMessageDialog(this, "This account no longer have any regular dues. Due type will automatically be set as `Moratorium`.");
								intOption = JOptionPane.CANCEL_OPTION;                                                    
							} else {
								intOption = JOptionPane.showOptionDialog(null, "This payment is intended for which due?", "Payment Mode", 
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
										new Object[] {" Regular  ", "  Mixed   ", "Moratorium"}, JOptionPane.YES_OPTION);
							}
						} else {
							intOption = JOptionPane.YES_OPTION;
						}

						due_type = (String) ((intOption==JOptionPane.YES_OPTION)?"R":(intOption==JOptionPane.NO_OPTION)?"W":(intOption==JOptionPane.CANCEL_OPTION)?"M":true); 
					}

					/*
					modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, Long.toString(new_check_no++),
							check_type_id, check_type_name, cal.getTime(), account_no, bank_id, 
							bank_name, branch_id, branch_name, null, receiptID, 
							receiptTypeID, null, brstn, false});
					 */

					modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, Long.toString(new_check_no++),
							check_type_id, check_type_name, cal.getTime(), account_no, bank_id, 
							bank_name, branch_id, branch_name, null, receiptID, 
							receiptTypeID, null, brstn, false, null, null, due_type});
				}
			} else if (part_id.equals("197") || part_id.equals("268")) {
				for(int x=0; x < no_of_check; x++){
					cal.set(year, month++, date);

					receiptID = "03";
					receiptTypeID = "AR";

					modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, Long.toString(new_check_no++),
							check_type_id, check_type_name, cal.getTime(), account_no, bank_id, 
							bank_name, branch_id, branch_name, null, receiptID, 
							receiptTypeID, null, brstn, false, null, strSelectedLot});
				}
				strSelectedLot = ""; 
			} else {
				for(int x=0; x < no_of_check; x++){
					cal.set(year, month++, date);

					/*	Added by Mann2x; Date Added: July 5, 2017; DCRF# 159;	*/
					String strPBL = "";
					try {
						strPBL = FncGlobal.GetString("select x.pbl_id from mf_unit_info x where x.unit_id = '"+txtUnitID.getText().toString()+"'");
					} catch (NullPointerException ex) {
						strPBL = "";
					}

					Boolean blnOR = false;
					try {
						blnOR = FncGlobal.GetBoolean("select (case when (\n" +
								"select count(*) \n" +
								"from rf_buyer_status x \n" +
								"where x.entity_id = '"+lookupClient.getValue().toString()+"' and x.proj_id = '"+txtProjectID.getText().toString()+"' \n" +
								"and x.pbl_id = '"+strPBL+"' and x.seq_no::int = '"+txtSequence.getText()+"'::int \n" +
								"and byrstatus_id = '01' and x.status_id = 'A'\n" +
								") > 0 then true else false end)");
					} catch (NullPointerException ex) {
						blnOR = false;
					}

					String strSubProject = "";
					try {
						strSubProject = FncGlobal.GetString("SELECT sub_proj_id \n" +
								"from mf_unit_info \n" +
								"where proj_id = '"+txtProjectID.getText().toString()+"' and pbl_id = '"+strPBL+"'");
					} catch (NullPointerException ex) {
						strSubProject = "";
					}

					Boolean blnBOI = false;
					try {
						blnBOI = FncGlobal.GetBoolean("SELECT (case when boi is null then false else true end) \n" +
								"FROM mf_sub_project \n" +
								"WHERE sub_proj_id = '"+strSubProject+"' AND status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718
					} catch (NullPointerException ex) {
						blnBOI = false;
					}

					Boolean blnLTS = false;
					try {
						blnLTS = FncGlobal.GetBoolean("SELECT (case when lts_date is null then false else true end) \n" +
								"FROM mf_sub_project \n" +
								"WHERE sub_proj_id = '"+strSubProject+"' AND status_id = 'A'");//ADDED STATUS ID BY LESTER DCRF 2718
					} catch (NullPointerException ex) {
						blnLTS = false;
					}

					/*	Modified By Mann2x; Modified Date: October 27, 2016; A check can be considered as dated the day before it's check date;
					if (cal.getTime().compareTo(FncGlobal.dateFormat(FncGlobal.getDateSQL()))<=0) {
						receiptID = receipt_id; 
						receiptTypeID = receipt_alias;
					} else {
						receiptID = "03";
						receiptTypeID = "AR";
					}
					 */

					/*	Modified by Mann2x; Date Modified: July 5, 2017; DCRF# 159;	
					if (_OrderOfPayment.CheckIfDated(cal.getTime().toString(), FncGlobal.getDateSQL())) {
					 */
					if (_OrderOfPayment.CheckIfDated(cal.getTime().toString(), FncGlobal.getDateSQL()) && blnOR && blnBOI && blnLTS) {
						receiptID = receipt_id;
						receiptTypeID = receipt_alias;							
					} else {
						receiptID = "03";
						receiptTypeID = "AR";						
					}

					modelPaymentList.addRow(new Object[]{part_id, part_name, amount, payment_type, Long.toString(new_check_no++), check_type_id, check_type_name, cal.getTime(), account_no,
							bank_id, bank_name, branch_id, branch_name, null, receiptID, receiptTypeID, null, brstn, false});
				}
			}	
		}

		else if(!tblPaymentList.isEnabled())
		{
			int row = tblPaymentList.getSelectedRow();
			modelPaymentList.setValueAt(part_id, row, 0);
			modelPaymentList.setValueAt(part_name, row, 1);
			modelPaymentList.setValueAt(amount, row, 2);
			modelPaymentList.setValueAt(payment_type, row, 3);
			modelPaymentList.setValueAt(check_no, row, 4);
			modelPaymentList.setValueAt(check_type_id, row, 5);
			modelPaymentList.setValueAt(check_type_name, row, 6);
			modelPaymentList.setValueAt(check_date, row, 7);
			modelPaymentList.setValueAt(account_no, row, 8);
			modelPaymentList.setValueAt(bank_id, row, 9);
			modelPaymentList.setValueAt(bank_name, row, 10);
			modelPaymentList.setValueAt(branch_id, row, 11);
			modelPaymentList.setValueAt(branch_name, row, 12);
			modelPaymentList.setValueAt(null, row, 13);

			/*	Filter Added by Mann2x; Date Added: January 23, 2017; PDC receipt type turns to ORV when edited because filter is not available at this part;	*/
			/*
				modelPaymentList.setValueAt(receipt_id, row, 14);
				modelPaymentList.setValueAt(receipt_alias, row, 15);
			 */
			if (_OrderOfPayment.CheckIfDated(cal.getTime().toString(), FncGlobal.getDateSQL())) {
				modelPaymentList.setValueAt(receipt_id, row, 14);
				modelPaymentList.setValueAt(receipt_alias, row, 15);
			} else {
				modelPaymentList.setValueAt("03", row, 14);
				modelPaymentList.setValueAt("AR", row, 15);
			}

			modelPaymentList.setValueAt(null, row, 16);
			modelPaymentList.setValueAt(brstn, row, 17);
			modelPaymentList.setValueAt(false, row, 18);
		}
	}

	private void newTransaction() {// Added by Alvin Gonzales (2015-06-02)

		setPaymentDetailsEnable(true);

		lookupParticular.setLookupSQL(_OrderOfPayment.getParticulars(lookupClient.getValue(), txtProjectID.getText(), txtUnitID.getText(), txtSequence.getText(), 
				cmbSelectType.getSelectedItem() == "RESERVATION (TR)", receipt_type==null? "":receipt_type ));

		tblNewPayments.clearSelection();
		tabPayments.setSelectedIndex(0);
		tabPayments.setEnabledAt(1, false);

		Boolean isTR = lblReservationType.getText().equals("[ TEMPORARY RESERVATION ]");
		btnPD_State(true, tblPaymentList.getSelectedRows().length > 0, tblPaymentList.getSelectedRows().length > 0, false);
		btnState(false, false, isTR, true, false);
		
		lookupClient.setEnabled(false); //ADDED BY MONIQUE DTD 2023-07-25
	}

	private void editParticular() {// Added by Alvin Gonzales (2015-06-02)
		int row = tblPaymentList.getModelRow(tblPaymentList.getSelectedRow());
		String type = (String) modelPaymentList.getValueAt(row, 3);

		chkCashAmount.setSelected(type.equals("CASH"));
		chkCheckAmount.setSelected(type.equals("CHECK"));

		tblPaymentList.setEnabled(false);
		btnPD_State(true, false, false, true);		
	}

	private void deleteParticular() {// Added by Alvin Gonzales (2015-06-02)
		int[] rows = tblPaymentList.getSelectedRows();

		for(int x = rows.length - 1; x >= 0; x--){
			modelPaymentList.removeRow(rows[x]);
		}
		clearPaymentDetails();

		int w = tblPaymentList.getRowCount();
		if (w>0)
		{
			btnPD_State(true, true, true, false);
		}
		else 
		{
			btnPD_State(true, false, false, false);
		}
	}

	private void cancelParticular() {// Added by Alvin Gonzales (2015-06-02)		
		tblPaymentList.setEnabled(true);
		btnPD_State(true, true, true, false);
		chkCreditItsReal.setEnabled(false); //ADDED BY MONIQUE DTD 2023-07-25
	}

	private void editTransaction() {// Added by Alvin Gonzales (2015-06-02)
		cmbSelectType.setEnabled(false);
		lookupClient.setEditable(false);

		lookupParticular.setLookupSQL(_OrderOfPayment.getParticulars(lookupClient.getValue(), txtProjectID.getText(), 
				txtUnitID.getText(), txtSequence.getText(), cmbSelectType.getSelectedItem() == "RESERVATION (TR)", receipt_type));

		setPaymentDetailsEnable(true);
		tblNewPayments.setEnabled(false);

		_OrderOfPayment.displayReturnedPaymentList(modelPaymentList, lblReservationType.getText().replaceAll("\\[|\\]", "").trim());
		tblPaymentList.packAll();
		tabPayments.setSelectedIndex(0);

		btnPD_State(true, false, false, false);
		btnState(false, false, true, true, true);		
	}

	private void cancelTransaction() {// Added by Alvin Gonzales (2015-06-02)

		if(!btnSave.isEnabled())  {
			executeCancel();
		}
		else 
		{
			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				executeCancel();
			}
			else {}
		}

	}

	private void executeCancel(){
		setComponentsEnabled(pnlPaymentDetails, false);
		clearPaymentDetails();
		clearTransactionDetails();

		modelPaymentList.clear();
		modelPaymentListTotal.addRow(new Object[] { null, "Total", 0.00, null, null, null });

		modelCreditPayments.clear();

		tblNewPayments.setEnabled(true);

		cmbSelectType.setEnabled(true);
		lookupClient.setEditable(true);
		tabPayments.setEnabledAt(1, true);
		btnPD_State(false, false, false, false);
		btnState(false, false, false, false, true);
		excess = null;
		pbl_id = "";
		unit_seq_no = null;
		receipt_type = "";
		//ADDED BY MONIQUE DTD 2023-07-25
		chkCreditItsReal.setEnabled(false);
		lookupClient.setEnabled(true);

	}

	private void clickPayment(){
		int row = tblNewPayments.getSelectedRow();												
		String client_seqno 	= (String) modelNewPayments.getValueAt(row, 0);
		String status 			= (String) modelNewPayments.getValueAt(row, 7);
		String transacted_by 	= (String) modelNewPayments.getValueAt(row, 8);

		displayReturnedPaymentTransactionDetails(client_seqno);

		if (status.equals("RETURNED")&&transacted_by.equals(UserInfo.FullName)||
				status.equals("ON PROCESS")&&transacted_by.equals(UserInfo.FullName)) {

			/*	Modified by Mann2x; Modified Date: November 28, 2016; Modified so that buttons will be disabled during View Mode	*/
			/*	btnState(false, true, false, false, false);	*/

			if (blnViewMode) {
				btnPD_State(false, false, false, false);
				btnState(false, false, false, false, true);
			} else {
				btnState(false, true, false, false, false);
			}
		}
		else {

			/*	Modified by Mann2x; Modified Date: November 28, 2016; Modified so that buttons will be disabled during View Mode	*/
			/*	btnState(false, false, false, false, false);	*/

			if (blnViewMode) {
				btnPD_State(false, false, false, false);
				btnState(false, false, false, false, true);
			} else {
				btnState(false, true, false, false, false);
			}
		}

		btnRefresh.setEnabled(true);
	}

	private void refreshFields(){

		tabPayments.setSelectedIndex(1);
		displayNewPayments();

		JOptionPane.showMessageDialog(getContentPane(), "New payments refreshed.", "Refresh", 
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void viewPastOP(){		

		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlViewPastOP, "Past Order of Payments",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);	

		if (scanOption == JOptionPane.CLOSED_OPTION ) {

		}

	}



	//Validate, check
	private Boolean validateReservation(BigDecimal amount) {

		if(cmbSelectType.getSelectedItem() == "RESERVATION (TR)"){
			String client_seqno = lookupClient.getValue();
			String proj_id = txtProjectID.getText();
			String pbl_id = txtUnitID.getText();

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM sp_validate_reservation_payment('"+ client_seqno +"', "+ amount +", NULL, '"+proj_id+"', '"+pbl_id+"', NULL);";
			db.select(SQL, "Post", true);
			FncSystem.out("Display SQL For Validation of Reservation Pmt", SQL);

			db.select("SELECT * FROM sp_validate_reservation_payment('"+ client_seqno +"', "+ amount +", NULL, '"+ proj_id +"', '"+ pbl_id +"', NULL)");

			excess = (BigDecimal) db.getResult()[0][3];
			if(excess.compareTo(FncBigDecimal.zeroValue()) > 0){
				return false;
			}
		}

		if(cmbSelectType.getSelectedItem() == "NEW PAYMENT"){
			String entity_id = lookupClient.getValue();
			String proj_id = txtProjectID.getText();
			String pbl_id = txtUnitID.getText();
			String seq_no = (txtSequence.getText().equals("") ? "null":txtSequence.getText());
			String SQL = "SELECT * FROM sp_validate_reservation_payment(NULL, "+ amount +", '"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +");";

			pgSelect db = new pgSelect();
			db.select(SQL, "Post", true);

			excess = (BigDecimal) db.getResult()[0][3];
			if(excess.compareTo(FncBigDecimal.zeroValue()) > 0){
				return false;
			}
		}

		return true;
	}

	@Override
	public void run() {
		displayNewPayments();

	}

	private Boolean toPost() {
		if(chkCashAmount.isSelected()){
			if(txtCashAmount.getValued() == null || ((BigDecimal)txtCashAmount.getValued()).compareTo(FncBigDecimal.zeroValue()) <= 0){
				JOptionPane.showMessageDialog(getContentPane(), "Please input valid cash amount.", "Post", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		if(chkCheckAmount.isSelected()){
			if(txtCheckAmount.getValued() == null || ((BigDecimal)txtCheckAmount.getValued()).compareTo(FncBigDecimal.zeroValue()) <= 0){
				JOptionPane.showMessageDialog(getContentPane(), "Please input valid check amount.", "Post", JOptionPane.WARNING_MESSAGE);
				return false;
			}

			if(txtCheckNo.getText().trim().equals("")){
				JOptionPane.showMessageDialog(getContentPane(), "Please input check no..", "Post", JOptionPane.WARNING_MESSAGE);
				return false;
			}

			if(txtAccountNo.getText().trim().equals("")){
				JOptionPane.showMessageDialog(getContentPane(), "Please input account no..", "Post", JOptionPane.WARNING_MESSAGE);
				return false;
			}

			if(txtNoOfCheck.getIntegerValue() == null){
				JOptionPane.showMessageDialog(getContentPane(), "Please input no. of check.", "Post", JOptionPane.WARNING_MESSAGE);
				return false;
			}

			if(lookupBank.getValue() == null){
				JOptionPane.showMessageDialog(getContentPane(), "Please select bank.", "Post", JOptionPane.WARNING_MESSAGE);
				return false;
			}

			if(lookupBranch.getValue() == null){
				JOptionPane.showMessageDialog(getContentPane(), "Please select branch.", "Post", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		/*ArrayList<String> listReceipt = new ArrayList<String>();
		for(int x=0; x < tblPaymentList.getRowCount(); x++){
			String receipt_id = (String) modelPaymentList.getValueAt(x, 14);
			listReceipt.add(receipt_id);
		}

		if(!listReceipt.contains(receipt_id) && listReceipt.size() > 0){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Different receipt cannot be issued simutaneously.", "Post", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/

		return true;
	}


	//SQL
	private Double sql_getDP_paid() {

		double a = 0.00;

		String SQL = 
				"select coalesce(get_unit_dp_amount_paid ('"+pbl_id+"', "+unit_seq_no+"),0) " ;

		System.out.printf("SQL #1: sql_getDP_paid", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if(db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {a = 0.00;}
			else {a = Double.parseDouble(db.getResult()[0][0].toString()); }

		}else{
			a = 0.00;
		}

		return a;
	}

	private Double sql_getDP_required() {

		double a = 0.00;

		String SQL = 
				"select coalesce(get_unit_dp_amount_total_required ('"+pbl_id+"', "+unit_seq_no+"),0) " ;

		System.out.printf("SQL #1: sql_getDP_required", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if(db.getResult()[0][0].toString()==null||db.getResult()[0][0].toString().equals("null")) {a = 0.00;}
			else {a = Double.parseDouble(db.getResult()[0][0].toString()); }

		}else{
			a = 0.00;
		}

		return a;
	}

	private Boolean hasHoldingFee(String unit_id, String entity_name) {

		Boolean a = false;

		String SQL = 
				"select receipt_no from rf_tra_header " +
						"where unit_id = '"+unit_id+"' " + 
						"and entity_id in (select entity_id from rf_entity where upper(entity_name) = '"+entity_name+"' ) " +
						"and credit_date is null \r\n" ;

		System.out.printf("SQL #1: hasHoldingFee" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			a = true;
		}else{
			a = false;
		}

		return a;
	}

	private Boolean hasLTS_BOI(String unit_id) {

		Boolean a = false;

		String SQL = 
				"select sub_proj_id from mf_sub_project " +
						"where lts_date is not null " +
						"and boi is not null " +
						"and sub_proj_id in (select sub_proj_id from mf_unit_info where unit_id = '"+unit_id+"') AND status_id = 'A' " ;//ADDED STATUS ID BY LESTER DCRF 2718

		System.out.printf("SQL #1: hasHoldingFee" + SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			a = true;
		}else{
			a = false;
		}

		return a;
	}

	public static void getTransactionPaymentList(String client_seqno, DefaultTableModel modelMain, JList rowHeader) {
		try {
			FncTables.clearTable(modelMain);
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Out of bounds exception occured.");
		}

		String SQL = "SELECT a.part_type, b.particulars, a.amount, (CASE WHEN COALESCE(a.check_no, '') <> '' THEN 'CHECK' ELSE 'CASH' END) as Type, a.check_no, \n" + 
				"c.check_id, c.check_desc, a.check_date::timestamp, a.acct_no, a.bank, d.bank_name, a.branch, e.bank_branch_location, a.receipt_no, \n" +
				"f.doc_id as receipt_ID, f.doc_alias, g.trans_date::timestamp, a.brstn, false, null, a.pay_for_lot, a.due_type \n" +
				"FROM rf_pay_detail a \n" +
				"INNER JOIN mf_pay_particular b ON a.part_type = b.pay_part_id \n" +
				"LEFT JOIN mf_check_type c ON a.check_type = c.check_id \n" +
				"LEFT JOIN mf_bank d ON a.bank = d.bank_id \n" +
				"LEFT JOIN mf_bank_branch e ON a.bank = e.bank_id AND a.branch = e.bank_branch_id \n" +
				"LEFT JOIN mf_system_doc f ON a.receipt_type = f.doc_id \n" +
				"LEFT JOIN rf_pay_header g ON a.client_seqno = g.client_seqno \n" +
				"WHERE a.client_seqno = '"+client_seqno+"'";

		pgSelect db = new pgSelect();
		db.select(SQL);

		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
	}

	private String OtherLotSelection() {
		String strOtherLot = ""; 
		String strSQL = "select unnest(string_to_array(z.lot|| '-' ||y.lot, '-')) as lots \n" +
				"FROM hs_sold_other_lots x \n" +
				"LEFT JOIN mf_unit_info y on y.proj_id = x.proj_id and y.pbl_id = x.oth_pbl_id \n" +
				"LEFT JOIN mf_unit_info z on z.proj_id = x.proj_id and z.pbl_id = x.pbl_id \n" +
				"where x.entity_id = '"+lookupClient.getValue()+"' and x.proj_id = '"+txtProjectID.getText()+"' \n" +
				"and z.unit_id = '"+txtUnitID.getText()+"' and x.seq_no::int = "+txtSequence.getText()+"::int and x.status_id = 'A'";

		pgSelect dbExec = new pgSelect(); 
		final JComboBox combo = new JComboBox();

		dbExec.select(strSQL);

		System.out.println("");
		System.out.println("strSQL: "+strSQL);

		if (dbExec.isNotNull()) {
			for (int x=0; x<dbExec.getRowCount();x++) {
				combo.addItem(dbExec.getResult()[x][0].toString()); 
			}
		}

		String[] options = {
				"OK", 
				"CANCEL"
		};

		String title = "Select Lot";

		int selection = JOptionPane.showOptionDialog(null, combo, title,
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		System.out.println("selection: " + selection);

		if (selection==0) {
			System.out.println("selected lot: " + combo.getSelectedItem().toString());

			strOtherLot = FncGlobal.GetString("select a.pbl_id \n" +
					"from \n" +
					"( \n" +
					"select unnest(string_to_array(z.lot|| '-' ||y.lot, '-')) as lot, \n" + 
					"unnest(string_to_array(z.pbl_id|| '-' ||y.pbl_id, '-')) as pbl_id \n" +
					"from hs_sold_other_lots x \n" +
					"left join mf_unit_info y on y.proj_id = x.proj_id and y.pbl_id = x.oth_pbl_id \n" + 
					"left join mf_unit_info z on z.proj_id = x.proj_id and z.pbl_id = x.pbl_id \n" +
					"where x.entity_id = '"+lookupClient.getValue()+"' and x.proj_id = '"+txtProjectID.getText()+"' \n" +
					"and z.unit_id = '"+txtUnitID.getText()+"' and x.seq_no::int = "+txtSequence.getText()+"::int and x.status_id = 'A' \n" +
					") a \n" +
					"where a.lot = '"+combo.getSelectedItem().toString()+"'");
		} else {
			strOtherLot = "";
		}

		return strOtherLot; 
	}

	private void ActivateLotLabel() {
		if (strSelectedLot.equals("")) {
			lblLotRemarks.setText("");
		} else {
			lblLotRemarks.setText("Payment for Lot: "+strSelectedLot);
		}
	}

	private Boolean pastDuePriorECQ(String strEntityID, String strProjID, String strPBLID, String strSeqNo) {
		return FncGlobal .GetBoolean("select exists(SELECT * FROM view_card_dues_regular('"+strEntityID+"', '"+strProjID+"', '"+strPBLID+"', '"+strSeqNo+"'::int, current_date::TIMESTAMP, FALSE) where c_scheddate::date < '2020-03-17'::date)"); 
	}
	
	private Boolean lotSpecific(String strPart) {
		
		return FncGlobal.GetBoolean("select coalesce(lot_specific, false) from mf_pay_particular where pay_part_id = '"+strPart+"'"); 
	}
}