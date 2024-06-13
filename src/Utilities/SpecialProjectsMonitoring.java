package Utilities;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Scrollable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.formula.ptg.TblPtg;
import org.jdesktop.swingx.JXTextArea;

import com.toedter.calendar.JDateChooser;

import Buyers.ClientServicing._OtherRequest;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Renderer.DateRenderer;
import components._JInternalFrame;
import interfaces._GUI;
import tablemodel.model_SpecialProjMonitoring;
import components.*;

public class SpecialProjectsMonitoring extends _JInternalFrame implements _GUI, ActionListener {
	private static final long serialVersionUID = -1586273380322233521L;
	private static String title = "Special Projects Monitoring";
	private static Dimension size = new Dimension(1000,700);

	private JPanel pnlMain,pnlMainCenter;
	private JLabel lblProjNo,lblProjName,lblIncharge,lblDiv,lblDept,lblProjObj,lblDateCreated,lblConfidential,lblUrgent
	,lblProjectBackground,lblDurationOrig,lblDurationActual,lblAhead,lblReasons;
	private _JLookup lookProjNo,lookIncharge,lookDept;
	private JTextField txtProjName,txtDurationOrig,txtDuarationActual,txtAhead,txtDiv;
	private JXTextArea txtAreaProjBackground,txtAreaReasons,txtAreaProjLearnings,txtAreaProjObj;
	private JList listProjObj,listProjLearnings;
	private JRadioButton radioConfYes,radioConfNo,radioUrgYes,radioUrgNo;
	private ButtonGroup groupConfidential;
	private ButtonGroup groupUrgent;
	private JDateChooser dateCreated, dateActualStart,dateFeedBack;
	private _JDateChooser dateOrigStart;
	private JScrollPane scrollProjObj,scrollProjBack,scrollReasons,scrollProjectLearning,scrollProjDetails;
	private model_SpecialProjMonitoring spm;
	private JList rowProjDetails;
	private _JTableMain tblSpm;
	private _JLookupTable dlgDependency;
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");


	public SpecialProjectsMonitoring () {
		super(title, true, true, true, true);
		initGUI();
	}
	public void initGUI() {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setLayout(new BorderLayout(5, 5));
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain,BorderLayout.CENTER);
			{
				JPanel pnlMainNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainNorth,BorderLayout.NORTH);
				pnlMainNorth.setBorder(JTBorderFactory.createTitleBorder("BACKGROUND INFORMATION"));
				pnlMainNorth.setPreferredSize(new Dimension(0,200));
				{
					JPanel pnlNorthNorth = new JPanel(new BorderLayout(5,5));
					pnlMainNorth.add(pnlNorthNorth,BorderLayout.NORTH);
					//					pnlNorthNorth.setPreferredSize(new Dimension(0,80));
					{
						JPanel pnlNorthWest = new JPanel(new GridLayout(5,1,3,3));
						pnlNorthNorth.add(pnlNorthWest,BorderLayout.WEST);
						{
							lblProjNo = new JLabel("Project No.:");
							pnlNorthWest.add(lblProjNo);

							lblProjName = new JLabel("Project Name:");
							pnlNorthWest.add(lblProjName);

							lblIncharge = new JLabel("Person-In-Charge:");
							pnlNorthWest.add(lblIncharge);

							lblDiv = new JLabel("Division:");
							pnlNorthWest.add(lblDiv);

							lblDept = new JLabel("Department:");
							pnlNorthWest.add(lblDept);

						}
					}
					{
						JPanel pnlNorthCenter = new JPanel(new GridLayout(5, 1, 3, 3));
						pnlNorthNorth.add(pnlNorthCenter,BorderLayout.CENTER);
						{
							JPanel pnlProjNo = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlProjNo);
							{
								JPanel pnlLookProjNo = new JPanel (new BorderLayout(5,5));
								pnlProjNo.add(pnlLookProjNo,BorderLayout.WEST);
								pnlLookProjNo.setPreferredSize(new Dimension(100,0));
								{
									lookProjNo = new _JLookup(null,"Project No",0);
									pnlLookProjNo.add(lookProjNo);
								}
							}
						}

						{
							JPanel pnlProjName = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlProjName);
							{
								JPanel pnlTxtProjName = new JPanel(new BorderLayout(5,5));
								pnlProjName.add(pnlTxtProjName,BorderLayout.WEST);
								pnlTxtProjName.setPreferredSize(new Dimension(300,0));

								txtProjName = new JTextField();
								pnlTxtProjName.add(txtProjName);
							}
						}
						{
							JPanel pnlInchargeAndProjObj = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlInchargeAndProjObj);
							{
								JPanel pnlLookInCharge = new JPanel(new BorderLayout(5,5));
								pnlInchargeAndProjObj.add(pnlLookInCharge,BorderLayout.WEST);
								pnlLookInCharge.setPreferredSize(new Dimension(230,0));
								{
									lookIncharge = new _JLookup(null,"Person-In-Charge",0);
									pnlLookInCharge.add(lookIncharge);
									lookIncharge.setLookupSQL(lookInchargeValue());
									lookIncharge.addLookupListener(new LookupListener() {


										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();
											if(data != null) {
												lookIncharge.setValue((String)data[1]);
												txtDiv.setText((String)data[0]);
												lookDept.setValue((String) data[2]);
											}
										}
									});
								}
							}

						}

						{
							JPanel pnlDiv = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlDiv);
							{
								JPanel pnlTxtDiv = new JPanel(new BorderLayout(5,5));
								pnlDiv.add(pnlTxtDiv,BorderLayout.WEST);
								pnlTxtDiv.setPreferredSize(new Dimension(100,0));

								txtDiv = new JTextField();
								pnlTxtDiv.add(txtDiv);
								txtDiv.setHorizontalAlignment(JTextField.CENTER);
							}
						}
						{
							JPanel pnlDept = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlDept);
							{
								JPanel pnlLookDept = new JPanel(new BorderLayout(5,5));
								pnlDept.add(pnlLookDept,BorderLayout.WEST);
								pnlLookDept.setPreferredSize(new Dimension(100,0));


								lookDept = new _JLookup(null,"Department",0);
								pnlLookDept.add(lookDept);
							}
						}

					}
					{
						JPanel pnlNorthEast = new JPanel(new BorderLayout(5,5));
						pnlNorthNorth.add(pnlNorthEast,BorderLayout.EAST);
						pnlNorthEast.setPreferredSize(new Dimension(300,0));
						{
							JPanel pnlEastNorth = new JPanel(new BorderLayout(5,5));
							pnlNorthEast.add(pnlEastNorth,BorderLayout.NORTH);
							{
								JPanel pnlEastWest = new JPanel(new GridLayout(3,1,3,3));
								pnlEastNorth.add(pnlEastWest,BorderLayout.WEST);
								{
									lblDateCreated = new JLabel("Date Created:");
									pnlEastWest.add(lblDateCreated);

									lblConfidential = new JLabel("Confidential:");
									pnlEastWest.add(lblConfidential);

									lblUrgent = new JLabel("Urgent:");
									pnlEastWest.add(lblUrgent);


								}
							}
							{
								JPanel pnlEastCenter = new JPanel(new BorderLayout(5,5));
								pnlEastNorth.add(pnlEastCenter,BorderLayout.CENTER);
								{
									JPanel pnlCenterWest = new JPanel(new GridLayout(3,1,3,3));
									pnlEastCenter.add(pnlCenterWest,BorderLayout.WEST);
									pnlCenterWest.setPreferredSize(new Dimension(130,0));
									{
										dateCreated = new JDateChooser();
										pnlCenterWest.add(dateCreated);
										dateCreated.setDate(FncGlobal.getDateToday());
										dateCreated.setDateFormatString("MM/dd/yyyy");
									}
									{
										JPanel pnlConfidential = new JPanel(new GridLayout(1,2,3,3));
										pnlCenterWest.add(pnlConfidential);
										{
											JPanel pnlConfNo = new JPanel(new BorderLayout(5,5));
											pnlConfidential.add(pnlConfNo);
											pnlConfNo.setPreferredSize(new Dimension(70,0));

											radioConfNo = new JRadioButton("No");
											pnlConfNo.add(radioConfNo);

										}
										{
											JPanel pnlConfYes = new JPanel(new BorderLayout(5,5));
											pnlConfidential.add(pnlConfYes);
											pnlConfYes.setPreferredSize(new Dimension(70,0));

											radioConfYes = new JRadioButton("Yes");
											pnlConfYes.add(radioConfYes);
										}
										{
											groupConfidential = new ButtonGroup();
											groupConfidential.add(radioConfNo);
											groupConfidential.add(radioConfYes);
											radioConfNo.setSelected(true);
										}

									}
									{
										JPanel pnlUrgent = new JPanel(new GridLayout(1,2,3,3));
										pnlCenterWest.add(pnlUrgent);
										{
											JPanel pnlUrgentNo = new JPanel(new BorderLayout(5,5));
											pnlUrgent.add(pnlUrgentNo);
											pnlUrgentNo.setPreferredSize(new Dimension(70,0));

											radioUrgNo = new JRadioButton("No");
											pnlUrgentNo.add(radioUrgNo);

										}
										{
											JPanel pnlUrgentYes = new JPanel(new BorderLayout(5,5));
											pnlUrgent.add(pnlUrgentYes);
											pnlUrgentYes.setPreferredSize(new Dimension(70,0));

											radioUrgYes = new JRadioButton("Yes");
											pnlUrgentYes.add(radioUrgYes);
										}
										{
											groupUrgent = new ButtonGroup();
											groupUrgent.add(radioUrgNo);
											groupUrgent.add(radioUrgYes);
											radioUrgNo.setSelected(true);
										}

									}
								}
							}
						}

					}
				}
				{
					JPanel pnlSouthNorth = new JPanel(new BorderLayout(5,5));
					pnlMainNorth.add(pnlSouthNorth,BorderLayout.SOUTH);
					//					pnlSouthNorth.setPreferredSize(new Dimension());
					{
						JPanel pnlSouthWest = new JPanel(new BorderLayout(5,5));
						pnlSouthNorth.add(pnlSouthWest,BorderLayout.WEST);
						{
							JPanel pnlWest = new JPanel(new BorderLayout(5,5));
							pnlSouthWest.add(pnlWest,BorderLayout.WEST);

							//							lblProjObj = new JLabel("Project \n Objective:");
							lblProjObj = new JLabel("<html>Project<br>Objective:</html>");

							pnlWest.add(lblProjObj);
						}
						{
							JPanel pnlSouthCenter = new JPanel(new BorderLayout(5,5));
							pnlSouthWest.add(pnlSouthCenter,BorderLayout.CENTER);
							pnlSouthCenter.setPreferredSize(new Dimension(600,70));
							{
								{
									txtAreaProjObj = new JXTextArea("Insert Text Here");
									scrollProjObj = new JScrollPane();
									pnlSouthCenter.add(scrollProjObj);
									scrollProjObj.setViewportView(txtAreaProjObj);
								}

								//							{
								//								String week[]= { "Monday","Tuesday","Wednesday", 
								//										"Thursday","Friday","Saturday","Sunday"}; 
								//								listProjObj = new JList(week);
								//								scrollProjObj = new JScrollPane();
								//								pnlSouthCenter.add(scrollProjObj);
								//								scrollProjObj.setViewportView(listProjObj);
								//							}

							}

						}
					}
					{
						JPanel pnlSouthEast = new JPanel(new BorderLayout(5,5));
						pnlSouthNorth.add(pnlSouthEast,BorderLayout.CENTER);
						//						pnlSouthEast.setPreferredSize(preferredSize);
						{
							JPanel pnlSoutWest = new JPanel(new BorderLayout(5,5));
							pnlSouthEast.add(pnlSoutWest,BorderLayout.WEST);

							//							lblProjectBackground = new JLabel("Project Background Info.:");
							lblProjectBackground = new JLabel("<html>Project<br>Background<br>Info.:</html>");

							pnlSoutWest.add(lblProjectBackground);

						}
						{
							JPanel pnlSouthCenter = new JPanel(new BorderLayout(5,5));
							pnlSouthEast.add(pnlSouthCenter,BorderLayout.CENTER);
							pnlSouthCenter.setPreferredSize(new Dimension(80,50));

							txtAreaProjBackground = new JXTextArea("Insert Text Here");
							scrollProjBack = new JScrollPane();
							pnlSouthCenter.add(scrollProjBack);
							scrollProjBack.setBorder(new EmptyBorder(5,5,5,5));
							scrollProjBack.setViewportView(txtAreaProjBackground);

						}
					}

				}
			}
			{
				pnlMainCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainCenter,BorderLayout.CENTER);
				pnlMainCenter.setBorder(JTBorderFactory.createTitleBorder("Project Details"));
				{
					dateOrigStart = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlMainCenter.add(dateOrigStart); 
					dateOrigStart.setDate(FncGlobal.getDateToday());
					//dateOrigStart.setDateFormatString("MM/dd/yyyy");
					dateOrigStart.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							int selectedRow = tblSpm.getSelectedRow();

							//							spm.setValueAt(Date.valueOf(sdf.format(dateFeedBack.getDate().getTime())), selectedRow, 1);	
							//spm.setValueAt(sdf.format(dateOrigStart.getDate().getTime()), selectedRow, 1);	
							//spm.setValueAt(Date.valueOf(sdf.format(dateOrigStart.getDate())), selectedRow, 1);
							spm.setValueAt(dateOrigStart.getDate(), selectedRow, 1);
							
							origDateEndValue();

						}
					});
				}
				{
					dateActualStart = new JDateChooser();
					pnlMainCenter.add(dateActualStart);
					dateActualStart.setDate(FncGlobal.getDateToday());
					dateActualStart.setDateFormatString("MM/dd/yyyy");
					dateActualStart.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int selectedRow = tblSpm.getSelectedRow();

							spm.setValueAt(sdf.format(dateActualStart.getDate().getTime()), selectedRow, 4);


						}
					});
				}
				{
					dateFeedBack = new JDateChooser();
					pnlMainCenter.add(dateFeedBack);
					dateFeedBack.setDate(FncGlobal.getDateToday());
					dateFeedBack.setDateFormatString("MM/dd/yyyy");
					dateFeedBack.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int selectedRow = tblSpm.getSelectedRow();

							spm.setValueAt((sdf.format(dateFeedBack.getDate().getTime())), selectedRow, 9);	

						}
					});
				}



				{
					scrollProjDetails = new JScrollPane();
					pnlMainCenter.add(scrollProjDetails,BorderLayout.CENTER);
					scrollProjDetails.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					scrollProjDetails.setBorder(new EmptyBorder(5,5,5,5));
				}
				{

				}
				{

					spm = new model_SpecialProjMonitoring();
					spm.setEditable(true);
					tblSpm = new _JTableMain(spm);
					scrollProjDetails.setViewportView(tblSpm);
					tblSpm.setSortable(false);
					tblSpm.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
					tblSpm.getColumnModel().getColumn(0).setPreferredWidth(225);
					tblSpm.getColumnModel().getColumn(8).setPreferredWidth(150);
					//					tblSpm.getColumnModel().getColumn(10).setPreferredWidth(150);
					tblSpm.getColumnModel().getColumn(11).setPreferredWidth(150);
					tblSpm.getColumnModel().getColumn(12).setPreferredWidth(150);
					tblSpm.addKeyListener(new KeyListener() {

						@Override
						public void keyTyped(KeyEvent arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void keyReleased(KeyEvent arg0) {
							if(tblSpm.getSelectedColumn()==3) {
								origDateEndValue();
							}
						}

						@Override
						public void keyPressed(KeyEvent arg0) {
							// TODO Auto-generated method stub

						}
					});
					tblSpm.addMouseListener(new MouseListener() {

						public void mouseReleased(MouseEvent e) {}
						public void mousePressed(MouseEvent e) {}
						public void mouseExited(MouseEvent e) {}
						public void mouseEntered(MouseEvent e) {

						}

						public void mouseClicked(MouseEvent e) {
							if(e.getSource().equals(tblSpm)) {
								if(tblSpm.getSelectedColumn()==1) {
									if(e.getClickCount()==2) {
										dateOrigStart.setBounds((int)pnlMainCenter.getMousePosition().getX(), (int)pnlMainCenter.getMousePosition().getY(), 0, 0);
										dateOrigStart.getCalendarButton().doClick();

									}
								}
								else if(tblSpm.getSelectedColumn()==4) {
									if(e.getClickCount()==2) {
										dateActualStart.setBounds((int)pnlMainCenter.getMousePosition().getX(), (int)pnlMainCenter.getMousePosition().getY(), 0, 0);
										dateActualStart.getCalendarButton().doClick();

									}
								}
								else if(tblSpm.getSelectedColumn()==9) {
									if(e.getClickCount()==2) {
										dateFeedBack.setBounds((int)pnlMainCenter.getMousePosition().getX(), (int)pnlMainCenter.getMousePosition().getY(), 0, 0);
										dateFeedBack.getCalendarButton().doClick();

									}
								}
								else if(tblSpm.getSelectedColumn()==7) {
									int selectedRow = tblSpm.getSelectedRow();
									if(e.getClickCount()==2) {
										dlgDependency = new _JLookupTable(FncGlobal.homeMDI, null , "Dependency", testsample(), false);
										dlgDependency.setLocationRelativeTo(FncGlobal.homeMDI);
										dlgDependency.setVisible(true);

										Object data [] = dlgDependency.getReturnDataSet();
										if(data != null) {
											spm.setValueAt(data[0], selectedRow, 7);
										}



									}
								}
								else if(tblSpm.getSelectedColumn()==8) {
									int selectedRow = tblSpm.getSelectedRow();
									if(e.getClickCount()==2) {
										_JLookupTable dlgInCharge = new _JLookupTable(FncGlobal.homeMDI, null , "Activity In-Charge", testsample(), false);
										dlgInCharge.setLocationRelativeTo(FncGlobal.homeMDI);
										dlgInCharge.setVisible(true);

										Object data [] = dlgInCharge.getReturnDataSet();
										if(data != null) {
											spm.setValueAt(data[0], selectedRow, 8);
										}



									}
								}
								else if(tblSpm.getSelectedColumn()==10) {
									int selectedRow = tblSpm.getSelectedRow();
									if(e.getClickCount()==2) {
										_JLookupTable dlgContactPerson = new _JLookupTable(FncGlobal.homeMDI, null , "Activity In-Charge", testsample(), false);
										dlgContactPerson.setLocationRelativeTo(FncGlobal.homeMDI);
										dlgContactPerson.setVisible(true);

										Object data [] = dlgContactPerson.getReturnDataSet();
										if(data != null) {
											spm.setValueAt(data[0], selectedRow, 10);
										}



									}
								}
								//								if(tblSpm.getSelectedColumn()==)
							}
						}
					});

				}
				{
					rowProjDetails = tblSpm.getRowHeader();
					rowProjDetails.setModel(new DefaultListModel());
					scrollProjDetails.setRowHeaderView(rowProjDetails);
					scrollProjDetails.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				JPanel pnlMainSouth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
				pnlMainSouth.setPreferredSize(new Dimension(0,250));
				{
					JPanel pnlSouthNorth = new JPanel(new BorderLayout(5,5));
					pnlMainSouth.add(pnlSouthNorth,BorderLayout.NORTH);
					pnlSouthNorth.setBorder(JTBorderFactory.createTitleBorder("Project Assessment:"));
					pnlSouthNorth.setPreferredSize(new Dimension(0,100));
					{
						JPanel pnlNorthWest = new JPanel(new BorderLayout(5,5));
						pnlSouthNorth.add(pnlNorthWest,BorderLayout.WEST);
						{
							JPanel pnlWestWest = new JPanel(new GridLayout(3,1,3,3));
							pnlNorthWest.add(pnlWestWest,BorderLayout.WEST);

							lblDurationOrig = new JLabel("Total Duration(Original):");
							pnlWestWest.add(lblDurationOrig);

							lblDurationActual = new JLabel("Total Duration(Actual):");
							pnlWestWest.add(lblDurationActual);

							lblAhead = new JLabel("% Ahead(Delayed):");
							pnlWestWest.add(lblAhead);
						}
						{
							JPanel pnlWestCenter = new JPanel(new GridLayout(3,1,3,3));
							pnlNorthWest.add(pnlWestCenter,BorderLayout.CENTER);
							pnlWestCenter.setPreferredSize(new Dimension(120,50));
							{
								JPanel pnlOrig = new JPanel(new BorderLayout(5,5));
								pnlWestCenter.add(pnlOrig);
								{
									JPanel pnlOrigWest = new JPanel(new BorderLayout(5,5));
									pnlOrig.add(pnlOrigWest,BorderLayout.WEST);
									pnlOrigWest.setPreferredSize(new Dimension(50,50));

									txtDurationOrig = new JTextField();
									pnlOrigWest.add(txtDurationOrig);

								}
								{
									JPanel pnlOrigCenter = new JPanel(new BorderLayout(5,5));
									pnlOrig.add(pnlOrigCenter,BorderLayout.CENTER);


									JLabel lblDaysOrig = new JLabel("days");
									pnlOrigCenter.add(lblDaysOrig);

								}
							}
							{
								JPanel pnlActual = new JPanel(new BorderLayout(5,5));
								pnlWestCenter.add(pnlActual);
								{
									JPanel pnlActualWest = new JPanel(new BorderLayout(5,5));
									pnlActual.add(pnlActualWest,BorderLayout.WEST);
									pnlActualWest.setPreferredSize(new Dimension(50,50));

									txtDuarationActual = new JTextField();
									pnlActualWest.add(txtDuarationActual);

								}
								{
									JPanel pnlActualCenter = new JPanel(new BorderLayout(5,5));
									pnlActual.add(pnlActualCenter,BorderLayout.CENTER);


									JLabel lblDaysActual = new JLabel("days");
									pnlActualCenter.add(lblDaysActual);

								}
							}
							{
								JPanel pnlAhead = new JPanel(new BorderLayout(5,5));
								pnlWestCenter.add(pnlAhead);
								{
									JPanel pnlAheadWest = new JPanel(new BorderLayout(5,5));
									pnlAhead.add(pnlAheadWest,BorderLayout.WEST);
									pnlAheadWest.setPreferredSize(new Dimension(50,50));

									txtAhead = new JTextField();
									pnlAheadWest.add(txtAhead);
								}
							}
						}

					}
					{
						JPanel pnlNorthCenter = new JPanel(new BorderLayout(5,5));
						pnlSouthNorth.add(pnlNorthCenter,BorderLayout.CENTER);
						pnlNorthCenter.setPreferredSize(new Dimension(300,0));
						{
							JPanel pnlLblReason = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlLblReason,BorderLayout.NORTH);

							lblReasons = new JLabel("Reasons for Project Advance or Delay:");
							pnlLblReason.add(lblReasons);
						}
						{
							JPanel pnlTxtArea = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlTxtArea,BorderLayout.CENTER);
							pnlTxtArea.setPreferredSize(new Dimension(0,70));

							scrollReasons = new JScrollPane();
							pnlTxtArea.add(scrollReasons);
							scrollReasons.setBorder(new EmptyBorder(5,5,5,5));
							txtAreaReasons = new JXTextArea("Insert Text Here");
							scrollReasons.setViewportView(txtAreaReasons);
						}
					}
				}
				{
					JPanel pnlSouthCenter = new JPanel(new BorderLayout(5,5));
					pnlMainSouth.add(pnlSouthCenter,BorderLayout.CENTER);
					pnlSouthCenter.setBorder(JTBorderFactory.createTitleBorder("Project Learnings:"));

					scrollProjectLearning = new JScrollPane();
					pnlSouthCenter.add(scrollProjectLearning,BorderLayout.CENTER);
					scrollProjectLearning.setBorder(new EmptyBorder(5,5,5,5));
					txtAreaProjLearnings = new JXTextArea("Insert Text Here");
					scrollProjectLearning.setViewportView(txtAreaProjLearnings);
				}

			}
		}
		deptHeadDisplay();
		displayTable(spm, rowProjDetails);

	}
	public void actionPerformed(ActionEvent arg0) {

	}
	public void displayTable (DefaultTableModel model_main,JList rowHeader) {
		FncTables.clearTable(model_main);
		DefaultListModel listmodel = new DefaultListModel();
		rowHeader.setModel(listmodel);
		for(int x = 0;x <= 30;x++){
			model_main.addRow(new Object[] {null,null,null,null,null,null,null,null,null,null,});
			listmodel.addElement(model_main.getRowCount());

		}

	}
	public void clickSmpTable() {
		int column = tblSpm.getSelectedColumn();
		int row = tblSpm.getSelectedRow();

		Integer x[] = {0,1,2,3,4,5,6,7,8,9,10,11,12};
		String sql[] = {"","","","","","","",testsample(),"","","","",""};
		if (/*column == x[column] && sql[column]!=""*/column==7) {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, null, sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);			
			dlg.setVisible(true);
		}
	}
	private String testsample() {

		String query = "SELECT table_name,date_updated FROM rf_trigger_filter";
		return query;
	}
	private void deptHeadDisplay(){
		pgSelect db = new pgSelect();
		String query = "Select * from special_project_monitoring('"+UserInfo.EmployeeCode+"')";
		db.select(query);
		if(db.isNotNull()) {
			lookIncharge.setValue((String) db.getResult()[0][1]);
			txtDiv.setText((String)db.getResult()[0][0]);
			lookDept.setValue((String) db.getResult()[0][2]);
		}
	}
	private String lookInchargeValue() {
		return "select distinct(a.division_alias) as \"Division\" ,d.entity_name as \"In-Charge\" ,b.dept_alias as \"Department\" from mf_division  a\n" + 
				"left join mf_department b on a.division_code = b.division_code\n" + 
				"left join em_employee c on /*a.div_head_code = c.emp_code and*/ b.dept_head_code = c.emp_code\n" + 
				"left join rf_entity d on c.entity_id = d.entity_id order by d.entity_name";
	}
	private void origDateEndValue() {

		int x = tblSpm.getSelectedRow(); 
		pgSelect  db = new pgSelect();

		Date origStartDate =  (Date) spm.getValueAt(x, 1) ;
		String origDuration = (String) spm.getValueAt(x, 3);
		
		
		if(origDuration != null && !origDuration.equals("")) {
			String query = "SELECT special_project_monitoring_orig_end('"+origStartDate+"','"+origDuration+"')";

			db.select(query);
			FncSystem.out("Display ENd Date Query", query);

			if(db.isNotNull()) {
				spm.setValueAt(sdf.format(db.getResult()[0][0]), x, 2);
			}
		}

	}

}
