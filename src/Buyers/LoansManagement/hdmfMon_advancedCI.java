package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;

import com.linuxense.javadbf.DBFException;
import com.toedter.calendar.JYearChooser;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncExport;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JFormattedTextField;
import components._JTabbedPane;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_hdmf_ci;

public class hdmfMon_advancedCI extends JXPanel implements _GUI {

	private static final long serialVersionUID = -4961600783993820019L;
	private static PagibigStatusMonitoring_v2 hdmfMainMod;
	static Dimension size = new Dimension(738, 351);
	
	private JToggleButton togCI;
	
	private JTextField txtPFRNo;
	private JTextField txtEmployer;
	private JTextField txtBlock;
	private JTextField txtLot;
	private JTextField txtMaiden;
	private JTextField txtRemarks;
	
	private JComboBox cboCivilStatus; 
	private JTextArea txtEmployerAddress;
	private _JDateChooser dtePFR;  
	
	public static JList rowCI;
	private _JTableMain tblCI;
	private JScrollPane scrollCI;
	private model_hdmf_ci modelCI;
	
	private _JXFormattedTextField txtLoan; 
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	private Font fontPlainSanSerEleven = new Font("SansSerif", Font.PLAIN, 11);
	
	public hdmfMon_advancedCI(PagibigStatusMonitoring_v2 psm) {
		this.hdmfMainMod = psm;
		initGUI();
	}

	public hdmfMon_advancedCI(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public hdmfMon_advancedCI(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public hdmfMon_advancedCI(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5)); 
			this.add(panMain, BorderLayout.CENTER); 
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				{
					JXPanel panPage = new JXPanel(new BorderLayout(5, 5)); 
					panMain.add(panPage, BorderLayout.CENTER); 
					{
						{
							scrollCI = new JScrollPane();
							panPage.add(scrollCI, BorderLayout.CENTER);
							scrollCI.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
							{
								modelCI = new model_hdmf_ci();
								modelCI.setEditable(false);
								
								tblCI = new _JTableMain(modelCI);
								tblCI.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									public void valueChanged(ListSelectionEvent event) {
										if (!event.getValueIsAdjusting()) {

										}
									}
								});
								
								rowCI = tblCI.getRowHeader();
								scrollCI.setViewportView(tblCI);
								
								tblCI.getColumnModel().getColumn(0).setPreferredWidth(35);
								tblCI.getColumnModel().getColumn(1).setPreferredWidth(300);
								tblCI.getColumnModel().getColumn(2).setPreferredWidth(125);
								tblCI.getColumnModel().getColumn(3).setPreferredWidth(125);
								tblCI.getColumnModel().getColumn(4).setPreferredWidth(300);
								tblCI.getColumnModel().getColumn(5).setPreferredWidth(300);
								tblCI.getColumnModel().getColumn(6).setPreferredWidth(50);
								tblCI.getColumnModel().getColumn(7).setPreferredWidth(50);
								tblCI.getColumnModel().getColumn(8).setPreferredWidth(125);
								tblCI.getColumnModel().getColumn(9).setPreferredWidth(300);
								tblCI.getColumnModel().getColumn(10).setPreferredWidth(125);
								tblCI.getColumnModel().getColumn(11).setPreferredWidth(125);
								tblCI.getColumnModel().getColumn(12).setPreferredWidth(100);
								tblCI.getColumnModel().getColumn(13).setPreferredWidth(100);
								tblCI.getColumnModel().getColumn(14).setPreferredWidth(100);
								tblCI.getColumnModel().getColumn(15).setPreferredWidth(100);
								
								tblCI.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer(sdf));
								tblCI.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no");
								tblCI.getTableHeader().setEnabled(false);
								tblCI.setSortable(false);
								
								tblCI.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
									@SuppressWarnings("deprecation")
									public void valueChanged(ListSelectionEvent e) {
										if(!e.getValueIsAdjusting()){
											try {
												Integer row = tblCI.convertRowIndexToModel(tblCI.getSelectedRow());
												
												txtPFRNo.setText((String) modelCI.getValueAt(row, 2));

												try {
													//dtePFR.setDate(new SimpleDateFormat("dd-MM-yyyy").parse((String) modelCI.getValueAt(row, 3)));
													dtePFR.setDate((Date) modelCI.getValueAt(row, 3));
												} catch (Exception ex) {
													System.out.println("");
													System.out.println("ex: " + ex);
													dtePFR.setDate(null); 
												}
												
												txtEmployer.setText((String) modelCI.getValueAt(row, 4));
												txtEmployerAddress.setText((String) modelCI.getValueAt(row, 5));
												txtBlock.setText((String) modelCI.getValueAt(row, 6));
												txtLot.setText((String) modelCI.getValueAt(row, 7));
												cboCivilStatus.setSelectedItem((String) modelCI.getValueAt(row, 10).toString().trim());
												txtMaiden.setText((String) modelCI.getValueAt(row, 11));
												txtRemarks.setText((String) modelCI.getValueAt(row, 9));
												txtLoan.setValue((BigDecimal) modelCI.getValueAt(row, 8));
											} catch (ArrayIndexOutOfBoundsException e1) {
												
											} catch (IndexOutOfBoundsException e2) {
												
											}
										}
									}
								});
								
								rowCI = tblCI.getRowHeader();
								rowCI.setModel(new DefaultListModel());
								scrollCI.setRowHeaderView(rowCI);
								scrollCI.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							}
						}
						{
							JXPanel panToggle = new JXPanel(new BorderLayout(5, 5)); 
							panPage.add(panToggle, BorderLayout.LINE_END); 
							panToggle.setPreferredSize(new Dimension(75, 0));
							{
								{
									togCI = new JToggleButton("Adv. CI"); 
									panToggle.add(togCI, BorderLayout.CENTER); 
									togCI.setSelected(false);
									togCI.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											if (togCI.isSelected()) {
												togCI.setText("MSVS");
												DisplayMSVSCI();
											} else {
												togCI.setText("Adv. CI");
												DisplayAdvancedCI(); 
											}
										}
									});
									togCI.setFont(new Font(togCI.getFont().getName(), togCI.getFont().getStyle(), togCI.getFont().getSize()-1));
									togCI.setFocusable(false);
								}
							}
						}

					}
				}
				{
					JXPanel panEnd = new JXPanel(new GridLayout(3, 1, 5, 5)); 
					panMain.add(panEnd, BorderLayout.PAGE_END);
					panEnd.setPreferredSize(new Dimension(0, 215));
					panEnd.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						{
							JXPanel panGroup1 = new JXPanel(new GridLayout(2, 1, 5, 5)); 
							panEnd.add(panGroup1); 
							{
								{
									JXPanel panLine1 = new JXPanel(new BorderLayout(5, 5)); 
									panGroup1.add(panLine1);
									{
										{
											JXPanel panDiv1 = new JXPanel(new BorderLayout(5, 5)); 
											panLine1.add(panDiv1, BorderLayout.LINE_START);
											panDiv1.setPreferredSize(new Dimension(125, 0));
											{
												JLabel lblPFR = new JLabel("PFR No./Date"); 
												panDiv1.add(lblPFR, BorderLayout.CENTER); 
											}
										}
										{
											JXPanel panDiv2 = new JXPanel(new GridLayout(1, 2, 5, 5)); 
											panLine1.add(panDiv2, BorderLayout.CENTER);
											{
												{
													txtPFRNo = new JTextField(""); 
													panDiv2.add(txtPFRNo); 
													txtPFRNo.setHorizontalAlignment(JTextField.CENTER);
													txtPFRNo.addKeyListener(new KeyListener() {
														public void keyTyped(KeyEvent e) {
															
														}
														
														public void keyReleased(KeyEvent e) {
																
														}
														
														public void keyPressed(KeyEvent e) {
															dtePFR.setDate(new Date());
															if (e.getKeyCode()==10) {
																if (JOptionPane.showConfirmDialog(null, "Value will be updated. Proceed?", 
																		"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
																	UpdateCI(); 
																	modelCI.setValueAt(txtPFRNo.getText(), tblCI.getSelectedRow(), 2);
																	modelCI.setValueAt(dtePFR.getDate(), tblCI.getSelectedRow(), 3);
																} else {
																	txtPFRNo.setText((String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 2)); 
																}
															}
														}
													});
												}
												{
													dtePFR = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
													panDiv2.add(dtePFR);
													dtePFR.getCalendarButton().setVisible(true);
													dtePFR.setDate(null);
													dtePFR.setEditable(false);
													dtePFR.addDateListener(new DateListener() {
														public void datePerformed(DateEvent event) {

														}
													});
													dtePFR.addKeyListener(new KeyListener() {
														public void keyTyped(KeyEvent e) {
															
														}
														
														public void keyReleased(KeyEvent e) {
															
														}
														
														public void keyPressed(KeyEvent e) {
															if (e.getKeyCode()==10) {
																if (JOptionPane.showConfirmDialog(null, "Value will be updated. Proceed?", 
																		"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
																	UpdateCI(); 
																	modelCI.setValueAt(dtePFR.getDate(), tblCI.getSelectedRow(), 3);
																} else {
																	dtePFR.setDate((Date) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 3));  
																}
															}
														}
													});
												}
											}	
										}
										{
											JXPanel panDiv3 = new JXPanel(new GridLayout(1, 4, 5, 5)); 
											panLine1.add(panDiv3, BorderLayout.LINE_END);
											panDiv3.setPreferredSize(new Dimension(300, 0));
											{
												{
													JLabel lblBlock = new JLabel("Block"); 
													panDiv3.add(lblBlock); 
													lblBlock.setHorizontalAlignment(JLabel.CENTER);
												}
												{
													txtBlock = new JTextField(""); 
													panDiv3.add(txtBlock); 
													txtBlock.setHorizontalAlignment(JTextField.CENTER);
													txtBlock.addKeyListener(new KeyListener() {
														public void keyTyped(KeyEvent e) {
															
														}
														
														public void keyReleased(KeyEvent e) {
																
														}
														
														public void keyPressed(KeyEvent e) {
															if (e.getKeyCode()==10) {
																if (JOptionPane.showConfirmDialog(null, "Value will be updated. Proceed?", 
																		"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
																	UpdateCI(); 
																	modelCI.setValueAt(txtBlock.getText(), tblCI.getSelectedRow(), 6);
																} else {
																	txtBlock.setText((String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 6)); 
																}
															}
														}
													});
												}
												{
													JLabel lblLot = new JLabel("Lot"); 
													panDiv3.add(lblLot);
													lblLot.setHorizontalAlignment(JLabel.CENTER);
												}
												{
													txtLot = new JTextField(""); 
													panDiv3.add(txtLot); 
													txtLot.setHorizontalAlignment(JTextField.CENTER);
													txtLot.addKeyListener(new KeyListener() {
														public void keyTyped(KeyEvent e) {
															
														}
														
														public void keyReleased(KeyEvent e) {
																
														}
														
														public void keyPressed(KeyEvent e) {
															if (e.getKeyCode()==10) {
																if (JOptionPane.showConfirmDialog(null, "Value will be updated. Proceed?", 
																		"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
																	UpdateCI(); 
																	modelCI.setValueAt(txtLot.getText(), tblCI.getSelectedRow(), 7);
																} else {
																	txtLot.setText((String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 7)); 
																}
															}
														}
													});
												}
											}	
										}
									}
								}
								{
									JXPanel panLine2 = new JXPanel(new BorderLayout(5, 5)); 
									panGroup1.add(panLine2);
									{
										{
											JLabel lblEmployer = new JLabel("Employer Name"); 
											panLine2.add(lblEmployer, BorderLayout.LINE_START); 
											lblEmployer.setPreferredSize(new Dimension(125, 0));
										}
										{
											txtEmployer = new JTextField(""); 
											panLine2.add(txtEmployer, BorderLayout.CENTER); 
											txtEmployer.setHorizontalAlignment(JTextField.CENTER);
											txtEmployer.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													
												}
												
												public void keyReleased(KeyEvent e) {
														
												}
												
												public void keyPressed(KeyEvent e) {
													if (e.getKeyCode()==10) {
														if (JOptionPane.showConfirmDialog(null, "Value will be updated. Proceed?", 
																"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
															UpdateCI(); 
															modelCI.setValueAt(txtEmployer.getText(), tblCI.getSelectedRow(), 4);
														} else {
															txtEmployer.setText((String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 4)); 
														}
													}
												}
											});
										}
										{
											JXPanel panLoan = new JXPanel(new BorderLayout(5, 5)); 
											panLine2.add(panLoan, BorderLayout.LINE_END); 
											panLoan.setPreferredSize(new Dimension(300, 0));
											{
												{
													JLabel lblLoan = new JLabel("Loan Amt."); 
													panLoan.add(lblLoan, BorderLayout.LINE_START); 
													lblLoan.setHorizontalAlignment(JLabel.CENTER);
													lblLoan.setPreferredSize(new Dimension(71, 0));
												}
												{
													txtLoan = new _JXFormattedTextField("0.00");
													panLoan.add(txtLoan, BorderLayout.CENTER);
													txtLoan.setHorizontalAlignment(JTextField.TRAILING);
													txtLoan.setFormatterFactory(_JXFormattedTextField.DECIMAL);
													txtLoan.setEnabled(true);
													txtLoan.setEditable(true);
													txtLoan.setFont(fontPlainSanSerEleven); 
													txtLoan.addKeyListener(new KeyListener() {
														public void keyTyped(KeyEvent e) {
															
														}
														
														public void keyReleased(KeyEvent e) {
																
														}
														
														public void keyPressed(KeyEvent e) {
															if (e.getKeyCode()==10) {
																if (JOptionPane.showConfirmDialog(null, "Value will be updated. Proceed?", 
																		"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
																	UpdateCI(); 
																	modelCI.setValueAt(txtLoan.getValue(), tblCI.getSelectedRow(), 8);
																} else {
																	txtLoan.setValue((BigDecimal) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 8)); 
																}
															}
														}
													});
												}
											}
										}
									}
								}
							}
						}
						{
							JXPanel panGroup2 = new JXPanel(new BorderLayout(5, 5)); 
							panEnd.add(panGroup2); 
							{
								{
									JXPanel panLine1 = new JXPanel(new GridLayout(2, 1, 5, 5)); 
									panGroup2.add(panLine1, BorderLayout.LINE_START);
									panLine1.setPreferredSize(new Dimension(125, 0));
									{
										{
											JLabel lblEmployer = new JLabel("Employer Address"); 
											panLine1.add(lblEmployer);
										}
										{
											panLine1.add(new JLabel("")); 
										}
									}
								}
								{
									txtEmployerAddress = new JTextArea(""); 
									panGroup2.add(txtEmployerAddress, BorderLayout.CENTER); 
									txtEmployerAddress.setWrapStyleWord(true);
									txtEmployerAddress.setLineWrap(true);
									txtEmployerAddress.setBorder(BorderFactory.createLineBorder(Color.GRAY));
									txtEmployerAddress.addKeyListener(new KeyListener() {
										public void keyTyped(KeyEvent e) {
											
										}
										
										public void keyReleased(KeyEvent e) {
												
										}
										
										public void keyPressed(KeyEvent e) {
											if (e.getKeyCode()==10) {
												if (JOptionPane.showConfirmDialog(null, "Value will be updated. Proceed?", 
														"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
													UpdateCI(); 
													modelCI.setValueAt(txtEmployerAddress.getText(), tblCI.getSelectedRow(), 5);
												} else {
													txtEmployerAddress.setText((String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 5)); 
												}
											}
										}
									});
								}
							}
						}
						{
							JXPanel panGroup3 = new JXPanel(new GridLayout(2, 1, 5, 5)); 
							panEnd.add(panGroup3); 
							{
								{
									JXPanel panLine1 = new JXPanel(new GridLayout(1, 2, 5, 5)); 
									panGroup3.add(panLine1); 
									{
										{
											JXPanel panDiv1 = new JXPanel(new BorderLayout(5, 5));  
											panLine1.add(panDiv1); 
											{
												{
													JLabel lblCivil = new JLabel("Civil Status"); 
													panDiv1.add(lblCivil, BorderLayout.LINE_START); 
													lblCivil.setPreferredSize(new Dimension(125, 0));
												}
												{
													pgSelect dbExec = new pgSelect(); 
													dbExec.select("select '' as civil_status_desc union select civil_status_desc from mf_civil_status order by civil_status_desc");
													
													cboCivilStatus = new JComboBox(); 
													panDiv1.add(cboCivilStatus, BorderLayout.CENTER); 
													
													for (int x = 0; x < dbExec.getRowCount(); x++) {
														cboCivilStatus.addItem(dbExec.getResult()[x][0].toString());
													}

													cboCivilStatus.addKeyListener(new KeyListener() {
														public void keyTyped(KeyEvent e) {
															
														}
														
														public void keyReleased(KeyEvent e) {
																
														}
														
														public void keyPressed(KeyEvent e) {
															if (e.getKeyCode()==10) {
																if (JOptionPane.showConfirmDialog(null, "Value will be updated. Proceed?", 
																		"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
																	UpdateCI(); 
																	modelCI.setValueAt(cboCivilStatus.getSelectedItem(), tblCI.getSelectedRow(), 10);
																} else {
																	cboCivilStatus.setSelectedItem((String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 10)); 
																}
															}
														}
													});
												}
											}
										}
										{
											JXPanel panDiv2 = new JXPanel(new BorderLayout(5, 5)); 
											panLine1.add(panDiv2, BorderLayout.CENTER); 
											{
												{
													JLabel lblMaiden = new JLabel("Maiden Name"); 
													panDiv2.add(lblMaiden, BorderLayout.LINE_START); 
													lblMaiden.setPreferredSize(new Dimension(100, 0));
													lblMaiden.setHorizontalAlignment(JLabel.CENTER);
												}
												{
													txtMaiden = new JTextField(""); 
													panDiv2.add(txtMaiden, BorderLayout.CENTER); 
													txtMaiden.setHorizontalAlignment(JTextField.CENTER);
													txtMaiden.addKeyListener(new KeyListener() {
														public void keyTyped(KeyEvent e) {
															
														}
														
														public void keyReleased(KeyEvent e) {
																
														}
														
														public void keyPressed(KeyEvent e) {
															if (e.getKeyCode()==10) {
																if (JOptionPane.showConfirmDialog(null, "Value will be updated. Proceed?", 
																		"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
																	UpdateCI(); 
																	modelCI.setValueAt(txtMaiden.getText(), tblCI.getSelectedRow(), 11);
																} else {
																	txtMaiden.setText((String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 11)); 
																}
															}
														}
													});
												}
											}
										}
									}
								}
								{
									JXPanel panLine2 = new JXPanel(new BorderLayout(5, 5)); 
									panGroup3.add(panLine2); 
									{
										{
											JLabel lblEmployer = new JLabel("Remarks"); 
											panLine2.add(lblEmployer, BorderLayout.LINE_START); 
											lblEmployer.setPreferredSize(new Dimension(125, 0));
										}
										{
											txtRemarks = new JTextField(""); 
											panLine2.add(txtRemarks, BorderLayout.CENTER); 
											txtRemarks.setHorizontalAlignment(JTextField.LEFT);
											txtRemarks.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													
												}
												
												public void keyReleased(KeyEvent e) {
														
												}
												
												public void keyPressed(KeyEvent e) {
													if (e.getKeyCode()==10) {
														if (JOptionPane.showConfirmDialog(null, "Value will be updated. Proceed?", 
																"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
															UpdateCI(); 
															modelCI.setValueAt(txtRemarks.getText(), tblCI.getSelectedRow(), 9);
														} else {
															txtRemarks.setText((String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 9)); 
														}
													}
												}
											});
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void DisplayAdvancedCI() {						
		String SQL = "select * from view_hdmf_ci_advanced('', '', '');";
		
		FncTables.clearTable(modelCI);
		DefaultListModel listModel = new DefaultListModel();
		rowCI.setModel(listModel); 
		
		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelCI.addRow(db.getResult()[x]);
				listModel.addElement(modelCI.getRowCount());
			}
		}
	}
	
	public void DisplayMSVSCI() {						
		String SQL = "select * from view_hdmf_ci_msvs('"+hdmfMainMod.txtCoID.getValue()+"', '"+hdmfMainMod.txtProID.getValue()+"', '"+hdmfMainMod.txtPhaseID.getValue()+"');";
		
		FncTables.clearTable(modelCI);
		DefaultListModel listModel = new DefaultListModel();
		rowCI.setModel(listModel); 
		
		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelCI.addRow(db.getResult()[x]);
				listModel.addElement(modelCI.getRowCount());
			}
		}
	}
	
	public void Generate() {
		if (!togCI.isSelected()) {
			DisplayAdvancedCI(); 
		} else {
			DisplayMSVSCI(); 
		}
	}
	
	private void UpdateCI() {
		pgUpdate dbExec = new pgUpdate(); 
		
		try {
			String strID = (String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 12);
			String strProjID = (String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 13); 
			String strUnitID = (String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 14); 
			String strSeqNo = (String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 15); 
			String strLoan = (String) modelCI.getValueAt(tblCI.convertRowIndexToModel(tblCI.getSelectedRow()), 8).toString();
			
			if (FncGlobal.GetBoolean("select exists(select * from rf_hdmf_advanced_ci where entity_id = '"+modelCI.getValueAt(tblCI.getSelectedRow(), 12)+"')")) {
				dbExec.executeUpdate("update rf_hdmf_advanced_ci \n" + 
						"set loan_amount = '"+txtLoan.getValue()+"'::numeric(19, 2), \n" +
						"employer  = '"+txtEmployer.getText()+"', employer_address  = '"+txtEmployerAddress.getText()+"', \n" +
						"block  = '"+txtBlock.getText()+"', lot  = '"+txtLot.getText()+"', remarks  = '"+txtRemarks.getText()+"', \n" +
						"civil_status  = '"+cboCivilStatus.getSelectedItem().toString()+"', maiden_name  = '"+txtMaiden.getText()+"'\n" + 
						"where entity_id = '"+strID+"'", true);
			} else {
				dbExec.executeUpdate("insert into rf_hdmf_advanced_ci (entity_id, proj_id, pbl_id, seq_no, \n" + 
						"prfno, pfrdate, employer, employer_address, block, lot, \n" + 
						"loan_amount, remarks, civil_status, maiden_name) \n" + 
						"values ('"+strID+"', '"+strProjID+"', '"+strUnitID+"', '"+strSeqNo+"'::int, \n" +
						"'"+txtPFRNo.getText()+"', null::date, '"+txtEmployer.getText()+"', '"+txtEmployerAddress.getText()+"', \n" +
						"'"+txtBlock.getText()+"', '"+txtLot.getText()+"', '"+strLoan+"'::numeric(19, 2), '"+txtRemarks.getText()+"', '"+cboCivilStatus.getSelectedItem()+"', '"+txtMaiden.getText()+"')", true);
			}
			dbExec.commit();	
		} catch (IndexOutOfBoundsException ex) {
			JOptionPane.showMessageDialog(null, "No row was selected.");
		}
	}

	public void Preview() {
		Boolean blnWith = false;
		
		for (int x = 0; x < modelCI.getRowCount(); x++) {
			if ((Boolean) modelCI.getValueAt(x, 0)) {
				blnWith = true; 
			}
		}
		
		if (blnWith) {
			if ((JOptionPane.showConfirmDialog(null, "Create Dev Entry?", "Advanced CI", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE))==JOptionPane.YES_OPTION) {
				CreateDevEntry(); 
			}
			
			pgUpdate dbExec = new pgUpdate(); 
			dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", true);
			dbExec.commit();
			
			dbExec = new pgUpdate();
			for (int x = 0; x < modelCI.getRowCount(); x++) {
				if ((Boolean) modelCI.getValueAt(x, 0)) {
					dbExec = new pgUpdate(); 
					dbExec.executeUpdate("insert into tmp_hdmf (client_name, emp_code) \n" +
							"values ('"+modelCI.getValueAt(x, 1)+"', '"+UserInfo.EmployeeCode+"')", true);
					dbExec.commit();
				}
			}
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", hdmfMainMod.txtCo.getText());
			mapParameters.put("02_AsOfDate", sdf.format(new Date()));
			mapParameters.put("03_User", UserInfo.FullName);
			mapParameters.put("04_UserID", UserInfo.EmployeeCode);
			if (togCI.isSelected()) {
				FncReport.generateReport("/Reports/rpt_hdmf_msvs_ci.jasper", "", "MSVS", mapParameters);
			} else {
				FncReport.generateReport("/Reports/rpt_hdmf_advanced_ci.jasper", "Advanced CI", "", mapParameters);
			}	
		} else {
			JOptionPane.showMessageDialog(null, "No row was selected!");
		}
	}
	
	private void CreateDevEntry() {
		String strDir = FncGlobal.OpenDir("Folder");
		
		try {
			createADDRESSB(strDir);
			createADDRESSA(strDir);
			createADDRESSE(strDir);
			createAPMASTER(strDir);	
			createCHAR_REF(strDir);			
			createDOA_DATA(strDir);
			createINSURE(strDir);
		} catch (DBFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createADDRESSB(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "TELNO", "HOUSE_NO", "STREET", "SUBD", "BRGY_VILL", 
				"TOWN_CITY", "PROVINCE", "REGION", "ZIPCODE"};
		
		Integer char_columns [] = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
		Integer numeric_columns [] = {1};
		
		FncExport.createADDRESSB_DBF_FromModelWithQuery_AdvancedCI(col_names, char_columns, numeric_columns, modelCI, "ADDRESSB.DBF", strDir);
	}
	
	private void createADDRESSA(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "UNIT_NO", "BLDG_NAME", "STREET", "VILLAGE", "TOWN_CITY", "STATE", "COUNTRY", "ZIPCODE"};
		
		Integer char_columns [] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer numeric_columns [] = {};
		
		FncExport.createADDRESSA_DBF_FromModelWithQuery_AdvancedCI(col_names, char_columns, numeric_columns, modelCI, "ADDRESSA.DBF", strDir);
	}
	
	private void createADDRESSE(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "ADDRCODE", "EYERID", "BUS_NAME", "TELNO", "RM_FLR", "BLDG_NAME", "STREET", "BRGY_VILL", "TOWN_CITY", "PROVINCE", "REGION", "ZIPCODE"};
		
		Integer char_columns [] = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		Integer numeric_columns [] = {1};
		
		FncExport.createADDRESSE_DBF_FromModelWithQuery_AdvancedCI(col_names, char_columns, numeric_columns, modelCI, "ADDRESSE.DBF", strDir);
	}
	
	private void createAPMASTER(String strDir) throws DBFException, IOException{
		String col_names [] = {"DEVCODE", "PROJCODE", "APPLICNO", "APPLICDATE", "PE", "GUIDELINE", "SHLF" , "PROG_CD", "LNAME", "FNAME", 
				"MID", "EXTNAME", "PURP_CD", "PAYMODE", "AMTAPPLIED", "TERM", "REGHOLDER", "ISMORT", "LANDAREA", 
				"EXIST_STRY", "PROP_STRY", "EXIST_FLR", "PROP_FLR", "TCTNO", "TAXDECLOT", "TAXDECBLDG", "LOT_UNIT", 
				"BLK_BLDG", 
				"SURVEY_NO", 
				"HOUSEAGE", "HDMFID", "BDAY", "GENDER", "STATUS", "OTHER", "EMAILADDR", "OWNERSHIP", "RENT", 
				"YRSTAY", "SSS_GSISID", "TIN", "CELLPHONE", "BEEPERNO","NATURE_BUS", "POSITION", "YREMP_BUS", "FILIPINO", 
				"DEPENDENTS"};

		Integer char_columns [] = {2, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16, 23, 24, 25, 26, 27, 28, 30, 32, 33, 34, 35, 36, 39, 40, 41, 42, 43, 44};
		Integer numeric_columns [] = {0, 1, 14, 15, 18, 19, 20, 21, 22, 29, 37, 38, 45, 47};
		Integer bool_columns [] = {4, 17, 46};
		Integer date_columns [] = {3, 31};
		
		String strCircular = ""; 
		String[] circular = {"310/349", "312", "379"};

		final JComboBox<String> combo = new JComboBox<>(circular);
		
		String[] options = {"OK", "CANCEL"};
		
		String title = "Select Circular";
		int selection = JOptionPane.showOptionDialog(null, combo, title,
		      JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		
		if (selection > 0) {
			System.out.println("selection is: " + options[selection]);
		}
		
		strCircular = combo.getSelectedItem().toString();
		if (strCircular=="") {
			System.out.println("Circular: " + strCircular);
		}
		
		FncExport.createDBF_FromModelWithQuery_AdvancedCI(col_names, char_columns, bool_columns, date_columns, numeric_columns, modelCI, "APMASTER.DBF", strDir, strCircular); 
	}
	
	private void createCHAR_REF(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "BCD", "SEQ", "NAME", "ADDRESS", "TELNO"};
		
		Integer char_columns [] = {0, 3, 4, 5};
		Integer numeric_columns [] = {1, 2};
		
		FncExport.createCHAR_REF_DBF_FromModelWithQuery_AdvancedCI(col_names, char_columns, numeric_columns, modelCI, "CHAR_REF.DBF", strDir);
	}
	
	private void createDOA_DATA(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "SEL_DATE", "SEL_DOCNO", "SEL_PGNO", "SEL_BOOKNO", "SEL_SERIES", "NOTARY_PUB", "ADA_DATE"};
		
		Integer char_columns [] = {0, 2, 3, 4, 5, 6};
		Integer date_columns [] = {1, 7};
		
		FncExport.createDOADATA_DBF_FromModelWithQuery_AdvancedCI(col_names, char_columns, date_columns, modelCI, "DOADATA.DBF", strDir);
	}
	
	private void createINSURE(String strDir) throws DBFException, IOException {
		String col_names [] = {"APPLICNO", "GBCODE", "GBTYPE", "REFERNO", "REFDATE"};
		
		Integer char_columns [] = {0, 2, 3};
		Integer numeric_columns [] = {1};
		Integer date_columns [] = {4};
		
		FncExport.createINSURE_DBF_FromModelWithQuery_AdvancedCI(col_names, char_columns, date_columns, numeric_columns, modelCI, "INSURE.DBF", strDir);
	}
}
