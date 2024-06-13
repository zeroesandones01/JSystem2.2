package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelCARD_CheckHistory;
import tablemodel.model_checks_floating;
import tablemodel.model_checks_pdc_warehousing;

public class checkStatusMonitoring_pdcWarehousing extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = 5747491609861152813L;
	static String title = "Check Status (PDC Warehousing)";
	private final Dimension frameSize = new Dimension(800, 600);
	
	public static _JLookup txtCoID;
	public static _JLookup txtProID;
	private static _JLookup txtPhaseID;
	
	public static JTextField txtCo;
	public static JTextField txtPro;
	public static JTextField txtPhase;
	
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private static Font font10Bold = FncLookAndFeel.systemFont_Bold.deriveFont(10f);
	
	private static JButton btnRefresh;
	private static JXPanel panMain; 

	PDCWarehousing_CheckStatus pdcw_checkstatus = new PDCWarehousing_CheckStatus(this);
	PDCWarehousing_Deposit pdcw_deposit = new PDCWarehousing_Deposit(this);
	PDCWarehousing_Export pdcw_export = new PDCWarehousing_Export(this);
	PDCWarehousing_BRSTN pdcw_brstn = new PDCWarehousing_BRSTN(this); 
	
	public checkStatusMonitoring_pdcWarehousing() {
		super(title, false, true, false, true);
		initGUI();
	}

	public checkStatusMonitoring_pdcWarehousing(String title) {
		super(title);
		initGUI();
	}

	public checkStatusMonitoring_pdcWarehousing(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		panMain = new JXPanel(new BorderLayout(5, 0)); 
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panPage, BorderLayout.PAGE_START); 
				panPage.setPreferredSize(new Dimension(0, 90));
				{
					{
						JXPanel panDetails = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panDetails, BorderLayout.CENTER);
						panDetails.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JXPanel panDiv = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panDetails.add(panDiv, BorderLayout.LINE_START); 
								panDiv.setPreferredSize(new Dimension(130, 0));
								{
									{
										JXPanel panLabel = new JXPanel(new GridLayout(2, 1, 5, 5)); 
										panDiv.add(panLabel); 
										{
											String[] strLabel = new String[] {
												"Company", 
												"Project"
											}; 
											
											JLabel[] lblLabel = new JLabel[strLabel.length];
											for (int x = 0; x < strLabel.length; x++) {
												lblLabel[x] = new JLabel(strLabel[x].toString()); 
												panLabel.add(lblLabel[x]); 
												lblLabel[x].setHorizontalAlignment(JLabel.LEFT);
												lblLabel[x].setFont(font11);
											}
										}
									}
									{
										JXPanel panProject = new JXPanel(new GridLayout(2, 1, 5, 5)); 
										panDiv.add(panProject); 
										{
											{
												txtCoID = new _JLookup(""); 
												panProject.add(txtCoID); 
												txtCoID.setReturnColumn(0);
												txtCoID.setHorizontalAlignment(_JLookup.CENTER);
												txtCoID.setLookupSQL(lookupMethods.getCompany());
												txtCoID.setFont(font11);
												txtCoID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
														
														if (data!=null) {
															txtCo.setText(data[1].toString());
															txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));
														}
													}
												});
											}
											{
												txtProID = new _JLookup(""); 
												panProject.add(txtProID, BorderLayout.CENTER); 
												txtProID.setReturnColumn(0);
												txtProID.setHorizontalAlignment(_JLookup.CENTER);
												txtProID.setLookupSQL(lookupMethods.getProject(txtCoID.getValue()));
												txtProID.setFont(font11);
												txtProID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
														
														if (data!=null) {
															txtPro.setText(data[1].toString());
															txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
														}
													}
												});
											}
										}
									}
								}	
							}
							{
								JXPanel panBox = new JXPanel(new GridLayout(2, 1, 5, 5)); 
								panDetails.add(panBox, BorderLayout.CENTER); 
								panBox.setPreferredSize(new Dimension(120, 0));
								{
									{
										txtCo = new JTextField(""); 
										panBox.add(txtCo); 
										txtCo.setEditable(false);
										txtCo.setHorizontalAlignment(JTextField.CENTER);
										txtCo.setFont(font11);
									}
									{
										JXPanel panDiv2 = new JXPanel(new BorderLayout(5, 5)); 
										panBox.add(panDiv2); 
										{
											{
												txtPro = new JTextField(""); 
												panDiv2.add(txtPro, BorderLayout.CENTER); 
												txtPro.setEditable(false);
												txtPro.setHorizontalAlignment(JTextField.CENTER);
												txtPro.setFont(font11);
											}
											{
												JXPanel panPhase = new JXPanel(new BorderLayout(5, 5)); 
												panDiv2.add(panPhase, BorderLayout.LINE_END); 
												panPhase.setPreferredSize(new Dimension(275, 0));
												{
													{
														JXPanel panPhaseLabel = new JXPanel(new GridLayout(1, 2, 5, 5)); 
														panPhase.add(panPhaseLabel, BorderLayout.LINE_START); 
														panPhaseLabel.setPreferredSize(new Dimension(130, 0));
														{
															{
																JLabel lblPhase = new JLabel("Phase"); 
																panPhaseLabel.add(lblPhase); 
																lblPhase.setHorizontalAlignment(JLabel.CENTER);
																lblPhase.setFont(font11);
															}
															{
																txtPhaseID = new _JLookup(""); 
																panPhaseLabel.add(txtPhaseID); 
																txtPhaseID.setReturnColumn(0);
																txtPhaseID.setHorizontalAlignment(_JLookup.CENTER);
																txtPhaseID.setLookupSQL(lookupMethods.getPhase(txtProID.getValue()));
																txtPhaseID.setFont(font11);
																txtPhaseID.addLookupListener(new LookupListener() {
																	public void lookupPerformed(LookupEvent event) {
																		Object[] data = ((_JLookup) event.getSource()).getDataSet(); 
																		
																		if (data!=null) {
																			txtPhase.setText(data[1].toString());
																		}
																	}
																});
															}
														}
													}
													{
														txtPhase = new JTextField(""); 
														panPhase.add(txtPhase, BorderLayout.CENTER); 
														txtPhase.setEditable(false);
														txtPhase.setHorizontalAlignment(JTextField.CENTER);
														txtPhase.setFont(font11);
													}
												}
											}
										}
									}
								}
							}
						}
					}
					{
						JXPanel panButton = new JXPanel(new BorderLayout(5, 5)); 
						panPage.add(panButton, BorderLayout.LINE_END);
						panButton.setPreferredSize(new Dimension(120, 0));
						{
							{
								btnRefresh = new JButton("Refresh"); 
								panButton.add(btnRefresh, BorderLayout.CENTER);
								btnRefresh.setFont(font11);
								btnRefresh.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										pdcw_checkstatus.LoadList(); 
										pdcw_brstn.LoadList(); 										
									}
								});
							}
							{
								final JLabel lblClick = new JLabel(">> Hide Details <<"); 
								panButton.add(lblClick, BorderLayout.PAGE_END); 
								lblClick.setFont(font10Bold);
								lblClick.setPreferredSize(new Dimension(0, 10));
								lblClick.setHorizontalAlignment(JLabel.CENTER);
								lblClick.addMouseListener(new MouseListener() {

									public void mouseReleased(MouseEvent e) {
										
									}
									
									public void mousePressed(MouseEvent e) {
										
									}
									
									public void mouseExited(MouseEvent e) {
										
									}
									
									public void mouseEntered(MouseEvent e) {
										
									}
									
									public void mouseClicked(MouseEvent e) {
										if (lblClick.getText().equals(">> Show Details <<")) {
											PDCWarehousing_CheckStatus.panLine.add(PDCWarehousing_CheckStatus.panEnd, BorderLayout.PAGE_END); 
											PDCWarehousing_CheckStatus.panEnd.setPreferredSize(new Dimension(0, 200));
											lblClick.setText(">> Hide Details <<");
										} else {
											PDCWarehousing_CheckStatus.panLine.remove(PDCWarehousing_CheckStatus.panEnd);
											
											lblClick.setText(">> Show Details <<");
										}
										
										PDCWarehousing_CheckStatus.panLine.repaint();
										PDCWarehousing_CheckStatus.panLine.revalidate();
									}
								});
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER); 
				{
					JTabbedPane tabPDC = new JTabbedPane(); 
					panCenter.add(tabPDC, BorderLayout.CENTER); 
					tabPDC.setFont(font11);
					tabPDC.setBorder(new EmptyBorder(5, 5, 5, 5));
					{
						tabPDC.add("        Check Status       ", pdcw_checkstatus);
						tabPDC.add("          Deposit          ", pdcw_deposit);
						tabPDC.add("          Export           ", pdcw_export);
						tabPDC.add("       Add/Edit BRSTN      ", pdcw_brstn);
					}
				}
			}
		}

		Defaults(); 
		pdcw_checkstatus.LoadList(); 
		pdcw_brstn.LoadList(); 
	}
	
	private void Defaults() {
		txtCoID.setText("02");
		txtCoID.setValue("02");
		txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");
		
		txtProID.setText("015");
		txtProID.setValue("015");
		txtPro.setText("TERRAVERDE RESIDENCES");
		
		txtProID.setLookupSQL(lookupMethods.getProject("02"));
		txtPhaseID.setLookupSQL(lookupMethods.getPhase("015"));
	}
	

}