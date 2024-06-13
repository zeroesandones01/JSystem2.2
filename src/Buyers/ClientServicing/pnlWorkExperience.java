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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JApplet;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.text.DateFormatter;

import org.jdesktop.swingx.JXFormattedTextField;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncFocusTraversalPolicy;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelWorkExperience;
/**
 * @author John Lester Fatallo
 */
public class pnlWorkExperience extends JPanel implements _GUI, ActionListener {

	private static final long serialVersionUID = -2421399339436175808L;

	private JPanel pnlNorth;
	private JPanel pnlNorthCenter;

	private JPanel pnlEmployerInfo;
	private JPanel pnlEmployerInfoWest;
	private JLabel lblEmployerID;
	private JLabel lblEmployerName;
	private JLabel lblDivision;
	private JLabel lblPosition;
	private JLabel lblEmployeeRank;
	private JLabel lblEmpStat;
	private JLabel lblDateHired;
	private JLabel lblDateResigned;
	private JLabel lblEmployerStatus;
	
	private JPanel pnlEmployerInfo_Right;
	private JLabel lblPreferredDay;
	private JCheckBox chkMonday;
	private JCheckBox chkTuesday;
	private JCheckBox chkWednesday;
	private JCheckBox chkThursday;
	private JCheckBox chkFriday;
	private JCheckBox chkSaturday;
	private JCheckBox chkSunday;
	
	private JLabel lblPreferredTime;
	private JSpinner spinnerCallStart;
	private JSpinner spinnerCallEnd;
	
	private JPanel pnlEmployerInfoCenter;
	private _JLookup lookupEmployer;
	private JTextField txtEmployer;
	private JTextField txtDivision;
	private JTextField txtPosition;
	private _JLookup lookupEmployeeRank;
	private JTextField txtEmployeeRank;
	private JComboBox cmbEmpStatus;
	private _JDateChooser dateHired;
	private _JDateChooser dateResigned;
	private JComboBox cmbEmployerStatus;

	private JPanel pnlMonthlyIncome;
	private JPanel pnlMonthlyIncomeWest;
	private JLabel lblBasicSalary;
	private JLabel lblAllowances;
	private JLabel lblDeductions;
	private JLabel lblOtherMI;
	private JLabel lblTotalGrossInc;
	private JLabel lblTotalMI;
	private JLabel lblIncomeOthers;
	private JPanel pnlMonthlyIncomeCenter;
	private _JXFormattedTextField txtBasicSal;
	private _JXFormattedTextField txtAllowances;
	private _JXFormattedTextField txtTotalGrossIncome;
	private _JXFormattedTextField txtOtherMI;
	private _JXFormattedTextField txtDeductions;
	private _JXFormattedTextField txtTotalMI;
	private _JXFormattedTextField txtIncomeOthers;

	private JPanel pnlMonthlyExpenses;
	private JPanel pnlMonthlyExpensesWest;
	private JLabel lblTranportation;
	private JLabel lblFood;
	private JLabel lblOthers;
	private JLabel lblSeparator;
	private JLabel lblTotalExpense;
	private JPanel pnlMonthExpensesCenter;
	private _JXFormattedTextField txtTransportation;
	private _JXFormattedTextField txtFood;
	private _JXFormattedTextField txtOthersME;
	private JSeparator jSepTotalExpense2;
	private _JXFormattedTextField txtTotalExpense;

	private JScrollPane scrollWorkExpList;

	private _JTableMain tblWorkExpList;
	private modelWorkExperience modelWorkExp;
	private JList rowHeaderWorkExpList;

	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 
	protected static DecimalFormat df = new DecimalFormat("#,##0.00");

	private ClientInformation ci;

	public pnlWorkExperience(ClientInformation ci) {
		this.ci= ci;
		initGUI();
	}

	public pnlWorkExperience(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlWorkExperience(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlWorkExperience(LayoutManager layout, boolean isDoubleBuffered) {
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
			pnlNorth.setPreferredSize(new Dimension(786, 350));
			//**
			{

				pnlNorthCenter = new JPanel(new GridLayout(2, 1, 1, 1));
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				{
					pnlEmployerInfo = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlNorthCenter.add(pnlEmployerInfo, BorderLayout.CENTER);
					pnlEmployerInfo.setBorder(components.JTBorderFactory.createTitleBorder("Employer Info"));
					//pnlEmployerInfo.setPreferredSize(new Dimension(234, 170));
					{
						JPanel pnlEmployerInfoMain = new JPanel(new BorderLayout(5, 5));
						pnlEmployerInfo.add(pnlEmployerInfoMain);
						{
							pnlEmployerInfoWest = new JPanel(new GridLayout(9, 1, 1, 1));
							pnlEmployerInfoMain.add(pnlEmployerInfoWest, BorderLayout.WEST);
							//pnlEmployerInfoWest.setPreferredSize(new Dimension(100, 137));
							{
								lblEmployerID = new JLabel("*Employer ID");
								pnlEmployerInfoWest.add(lblEmployerID);
							}
							{
								lblEmployerName = new JLabel("Employer Name");
								pnlEmployerInfoWest.add(lblEmployerName);
							}
							{
								lblDivision = new JLabel("Division/Dept.");
								pnlEmployerInfoWest.add(lblDivision);
							}
							{
								lblPosition = new JLabel("Position");
								pnlEmployerInfoWest.add(lblPosition);
							}
							{
								lblEmployeeRank = new JLabel("Employee Rank");
								pnlEmployerInfoWest.add(lblEmployeeRank);
							}
							{
								lblEmpStat = new JLabel("Emp. Status");
								pnlEmployerInfoWest.add(lblEmpStat);
							}
							{
								lblDateHired = new JLabel("*Date Hired");
								pnlEmployerInfoWest.add(lblDateHired);
							}
							{
								lblDateResigned = new JLabel("Date Resigned");
								pnlEmployerInfoWest.add(lblDateResigned);
							}
							{
								lblEmployerStatus = new JLabel("Status");
								pnlEmployerInfoWest.add(lblEmployerStatus);
							}
						}
						{
							pnlEmployerInfoCenter = new JPanel(new GridLayout(9, 1, 1, 1));
							pnlEmployerInfoMain.add(pnlEmployerInfoCenter, BorderLayout.CENTER);
							//pnlEmployerInfoCenter.setPreferredSize(new Dimension(147, 137));
							{
								lookupEmployer = new _JLookup(null, "Employer", 0);
								pnlEmployerInfoCenter.add(lookupEmployer, BorderLayout.WEST);
								lookupEmployer.setLookupSQL(sqlEmployer());
								lookupEmployer.setFilterName(true);
								lookupEmployer.setPreferredSize(new Dimension(100, 0));
								lookupEmployer.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										
										FncSystem.out("Display SQL For Employer", lookupEmployer.getLookupSQL());
										
										if(data != null){
											String employer_name = (String) data[1];

											txtEmployer.setText(employer_name);
											txtEmployer.setCaretPosition(0);
										}
									}
								});
							}
							{
								txtEmployer = new JTextField();
								pnlEmployerInfoCenter.add(txtEmployer);
							}
							{
								txtDivision = new JTextField();
								pnlEmployerInfoCenter.add(txtDivision);
							}
							{
								txtPosition = new JTextField();
								pnlEmployerInfoCenter.add(txtPosition);
							}
							{
								JPanel pnlEmployeeRank = new JPanel(new BorderLayout(3, 3));
								pnlEmployerInfoCenter.add(pnlEmployeeRank);
								{
									lookupEmployeeRank = new _JLookup(null, "Employee Rank", 0);
									lookupEmployeeRank.setFilterName(true);
									pnlEmployeeRank.add(lookupEmployeeRank, BorderLayout.WEST);
									lookupEmployeeRank.setLookupSQL(sqlEmployeeRank());
									lookupEmployeeRank.setPreferredSize(new Dimension(30, 0));
									lookupEmployeeRank.addLookupListener(new LookupListener() {
										
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup) event.getSource()).getDataSet();
											
											if(data != null){
												String rank_code = (String) data[0];
												String rank_desc = (String) data[1];
												
												lookupEmployeeRank.setValue(rank_code);
												txtEmployeeRank.setText(rank_desc);
											}
										}
									});
								}
								{
									txtEmployeeRank = new JTextField();
									pnlEmployeeRank.add(txtEmployeeRank);
								}
							}
							{
								cmbEmpStatus = new JComboBox(getEmpStatus());
								pnlEmployerInfoCenter.add(cmbEmpStatus);
							}
							{
								JPanel pnlHired = new JPanel(new BorderLayout());
								pnlEmployerInfoCenter.add(pnlHired);
								{
									dateHired = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlHired.add(dateHired, BorderLayout.CENTER);
									dateHired.setPreferredSize(new Dimension(90, 0));
								}
							}
							{
								JPanel pnlResigned = new JPanel(new BorderLayout());
								pnlEmployerInfoCenter.add(pnlResigned);
								{
									dateResigned = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
									pnlResigned.add(dateResigned, BorderLayout.CENTER);
									dateResigned.setPreferredSize(new Dimension(90, 0));
								}
							}
							{
								cmbEmployerStatus = new JComboBox(new String[]{"Active", "Inactive"});
								pnlEmployerInfoCenter.add(cmbEmployerStatus);
								cmbEmployerStatus.setEnabled(false);
							}
							
						}
					}
					{
						pnlEmployerInfo_Right = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlEmployerInfo.add(pnlEmployerInfo_Right);
						{
							JPanel pnlEmployerInfo_Right_West = new JPanel(new GridLayout(7, 1, 5, 5));
							pnlEmployerInfo_Right.add(pnlEmployerInfo_Right_West);
							{
								lblPreferredDay = new JLabel("Preferred Day");
								pnlEmployerInfo_Right_West.add(lblPreferredDay);
							}
							{
								chkMonday = new JCheckBox("Monday");
								pnlEmployerInfo_Right_West.add(chkMonday);
							}
							{
								chkTuesday = new JCheckBox("Tuesday");
								pnlEmployerInfo_Right_West.add(chkTuesday);
							}
							{
								chkWednesday = new JCheckBox("Wednesday");
								pnlEmployerInfo_Right_West.add(chkWednesday);
							}
							{
								chkThursday = new JCheckBox("Thursday");
								pnlEmployerInfo_Right_West.add(chkThursday);
							}
							{
								lblPreferredTime = new JLabel("Preferred Time");
								pnlEmployerInfo_Right_West.add(lblPreferredTime);
							}
							{
								JPanel pnlCallStart = new JPanel(new BorderLayout(5, 5));
								pnlEmployerInfo_Right_West.add(pnlCallStart);
								{
									JLabel lblFrom = new JLabel("From");
									pnlCallStart.add(lblFrom, BorderLayout.WEST);
								}
								{
									SpinnerDateModel model = new SpinnerDateModel(new Date(),  null, null, Calendar.MINUTE);

									spinnerCallStart = new JSpinner(model);

									JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerCallStart,"hh:mm a");
									DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
									formatter.setAllowsInvalid(true); 
									formatter.setOverwriteMode(true);

									spinnerCallStart.setEditor(editor);

									pnlCallStart.add(spinnerCallStart,BorderLayout.CENTER); 
									//spinnerCallStart.setPreferredSize(new Dimension(100, 30));
								}
							}
							
							
						}
						
						{
							JPanel pnlEmployerInfo_Right_East = new JPanel(new GridLayout(7, 1, 5, 5));
							pnlEmployerInfo_Right.add(pnlEmployerInfo_Right_East);
							{
								pnlEmployerInfo_Right_East.add(Box.createHorizontalBox());
							}
							{
								chkFriday = new JCheckBox("Friday");
								pnlEmployerInfo_Right_East.add(chkFriday);
							}
							{
								chkSaturday = new JCheckBox("Saturday");
								pnlEmployerInfo_Right_East.add(chkSaturday);
							}
							{
								chkSunday = new JCheckBox("Sunday");
								pnlEmployerInfo_Right_East.add(chkSunday);
							}
							{
								pnlEmployerInfo_Right_East.add(Box.createHorizontalBox());
							}
							{
								pnlEmployerInfo_Right_East.add(Box.createHorizontalBox());
							}
							
							{
								JPanel pnlCallEnd = new JPanel(new BorderLayout(5, 5));
								pnlEmployerInfo_Right_East.add(pnlCallEnd);
								{
									JLabel lblTo = new JLabel("To");
									pnlCallEnd.add(lblTo, BorderLayout.WEST);
								}
								{
									SpinnerDateModel model = new SpinnerDateModel(new Date(),  null, null, Calendar.MINUTE);

									spinnerCallEnd = new JSpinner(model);

									JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerCallEnd,"hh:mm a");
									DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
									formatter.setAllowsInvalid(true); 
									formatter.setOverwriteMode(true);

									spinnerCallEnd.setEditor(editor);

									pnlCallEnd.add(spinnerCallEnd,BorderLayout.CENTER); 
									//spinnerCallStart.setPreferredSize(new Dimension(100, 30));
								}
							}
							
						}
					}
				}
				{
					JPanel pnlNorthCenterBot = new JPanel(new GridLayout(1, 2));
					pnlNorthCenter.add(pnlNorthCenterBot);
					{
						pnlMonthlyIncome = new JPanel(new BorderLayout(5, 5));
						pnlNorthCenterBot.add(pnlMonthlyIncome, BorderLayout.CENTER);
						pnlMonthlyIncome.setBorder(components.JTBorderFactory.createTitleBorder("Monthly Income"));
						pnlMonthlyIncome.setPreferredSize(new Dimension(234, 170));
						{
							pnlMonthlyIncomeWest = new JPanel(new GridLayout(7, 1, 1, 1));
							pnlMonthlyIncome.add(pnlMonthlyIncomeWest, BorderLayout.WEST);
							pnlMonthlyIncomeWest.setPreferredSize(new Dimension(130, 137));
							{
								lblBasicSalary = new JLabel("Basic Salary");
								pnlMonthlyIncomeWest.add(lblBasicSalary);
							}
							{
								lblAllowances = new JLabel("Allowances");
								pnlMonthlyIncomeWest.add(lblAllowances);
							}
							{
								lblOtherMI = new JLabel("Others");
								pnlMonthlyIncomeWest.add(lblOtherMI);
							}
							{
								lblIncomeOthers = new JLabel("Income From others");
								pnlMonthlyIncomeWest.add(lblIncomeOthers);
							}
							{
								lblTotalGrossInc = new JLabel("Total Gross Income");
								pnlMonthlyIncomeWest.add(lblTotalGrossInc);
							}
							{
								lblDeductions = new JLabel("Deductions");
								pnlMonthlyIncomeWest.add(lblDeductions);
							}
							{
								lblTotalMI = new JLabel("Total Monthly Income");
								pnlMonthlyIncomeWest.add(lblTotalMI);
							}
							
						}
						{
							pnlMonthlyIncomeCenter = new JPanel(new GridLayout(7, 1, 1, 1));
							pnlMonthlyIncome.add(pnlMonthlyIncomeCenter, BorderLayout.CENTER);
							pnlMonthlyIncomeCenter.setPreferredSize(new Dimension(111, 137));
							{
								txtBasicSal = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlMonthlyIncomeCenter.add(txtBasicSal);
								txtBasicSal.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtBasicSal.setText("0.00");
								/*txtBasicSal.addFocusListener(new FocusListener() {
									
									@Override
									public void focusLost(FocusEvent e) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void focusGained(FocusEvent e) {
										txtBasicSal.selectAll();
										
									}
								});*/
								txtBasicSal.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try {
											totalGI();
											TotalNI();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtAllowances = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlMonthlyIncomeCenter.add(txtAllowances);
								txtAllowances.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtAllowances.setText("0.00");
								txtAllowances.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											totalGI();
											TotalNI();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtOtherMI = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlMonthlyIncomeCenter.add(txtOtherMI);
								txtOtherMI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtOtherMI.setText("0.00");
								txtOtherMI.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											totalGI();
											TotalNI();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtIncomeOthers = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlMonthlyIncomeCenter.add(txtIncomeOthers);
								txtIncomeOthers.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtIncomeOthers.setText("0.00");
								txtIncomeOthers.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e){
										try{
											totalGI();
											TotalNI();
										}catch (NumberFormatException a) {}
									}
								});
							}
							
							{
								txtTotalGrossIncome = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlMonthlyIncomeCenter.add(txtTotalGrossIncome);
								txtTotalGrossIncome.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtTotalGrossIncome.setText("0.00");
							}
							{
								txtDeductions = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlMonthlyIncomeCenter.add(txtDeductions);
								txtDeductions.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtDeductions.setText("0.00");
								txtDeductions.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											TotalNI();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtTotalMI = new _JXFormattedTextField(JXFormattedTextField.RIGHT); 
								pnlMonthlyIncomeCenter.add(txtTotalMI);
								txtTotalMI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtTotalMI.setText("0.00");
								
								/*txtTotalMI.addFocusListener(new FocusAdapter() {
									public void focusGained(java.awt.event.FocusEvent evt) {
								        SwingUtilities.invokeLater(new Runnable() {
								            @Override
								            public void run() {
								                txtTotalMI.selectAll();
								                txtTotalMI.revalidate();
								                txtTotalMI.repaint();
								            }
								        });
								    }
								});
								txtTotalMI.addFocusListener(new FocusListener() {
									
									@Override
									public void focusLost(FocusEvent e) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void focusGained(FocusEvent e) {
										txtTotalMI.selectAll();
										
									}
								});
								txtTotalMI.addMouseListener(new MouseListener() {
									
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
										txtTotalMI.selectAll();
										
									}
								});*/
							}
							
						}
					}
					{
						pnlMonthlyExpenses = new JPanel(new BorderLayout(5, 5));
						pnlNorthCenterBot.add(pnlMonthlyExpenses, BorderLayout.EAST);
						pnlMonthlyExpenses.setBorder(components.JTBorderFactory.createTitleBorder("Monthly Expenses"));
						pnlMonthlyExpenses.setPreferredSize(new Dimension(190, 170));
						{
							pnlMonthlyExpensesWest = new JPanel(new GridLayout(7, 1, 1, 1));
							pnlMonthlyExpenses.add(pnlMonthlyExpensesWest, BorderLayout.WEST);
							pnlMonthlyExpensesWest.setPreferredSize(new Dimension(100, 137));
							{
								lblTranportation = new JLabel();
								pnlMonthlyExpensesWest.add(lblTranportation);
								lblTranportation.setText("Transportation");
							}
							{
								lblFood = new JLabel();
								pnlMonthlyExpensesWest.add(lblFood);
								lblFood.setText("Food");
							}
							{
								lblOthers = new JLabel();
								pnlMonthlyExpensesWest.add(lblOthers);
								lblOthers.setText("Others");
							}
							{
								lblSeparator = new JLabel();
								pnlMonthlyExpensesWest.add(lblSeparator);
							}
							{
								lblTotalExpense = new JLabel();
								pnlMonthlyExpensesWest.add(lblTotalExpense);
								lblTotalExpense.setText("Total Expense");
							}
						}
						{
							pnlMonthExpensesCenter = new JPanel(new GridLayout(7, 1, 1, 1));
							pnlMonthlyExpenses.add(pnlMonthExpensesCenter, BorderLayout.CENTER);
							pnlMonthExpensesCenter.setPreferredSize(new Dimension(140, 137));
							{
								txtTransportation = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlMonthExpensesCenter.add(txtTransportation);
								txtTransportation.setPreferredSize(new Dimension(45, 22));
								txtTransportation.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtTransportation.setText("0.00");
								txtTransportation.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											TotalExp();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtFood = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlMonthExpensesCenter.add(txtFood);
								txtFood.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtFood.setText("0.00");
								txtFood.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											TotalExp();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								txtOthersME = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlMonthExpensesCenter.add(txtOthersME);
								txtOthersME.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtOthersME.setText("0.00");
								txtOthersME.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e) {
										try{
											TotalExp();
										} catch(NumberFormatException a) {}
									}
								});
							}
							{
								jSepTotalExpense2 = new JSeparator();
								pnlMonthExpensesCenter.add(jSepTotalExpense2);
							}
							{
								txtTotalExpense = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlMonthExpensesCenter.add(txtTotalExpense);
								txtTotalExpense.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtTotalExpense.setText("0.00");
							}
						}
					}
				}
			}
		}
		{
			scrollWorkExpList = new JScrollPane();
			this.add(scrollWorkExpList, BorderLayout.CENTER);
			scrollWorkExpList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			{
				modelWorkExp = new modelWorkExperience();
				tblWorkExpList = new _JTableMain(modelWorkExp);
				scrollWorkExpList.setViewportView(tblWorkExpList);
				tblWorkExpList.setSortable(false);
				tblWorkExpList.hideColumns("Emp. Rec. ID","Employer ID","Basic Salary", "Allowances", "Other Monthly Income","Deductions", "Living & Utilities", "Exp. Rec. ID" ,"Transportation", "Food", "Other Expenses");
				tblWorkExpList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				modelWorkExp.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {

						((DefaultListModel)rowHeaderWorkExpList.getModel()).addElement(modelWorkExp.getRowCount());
						scrollWorkExpList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblWorkExpList.getRowCount())));

						if(modelWorkExp.getRowCount() == 0){
							rowHeaderWorkExpList.setModel(new DefaultListModel());
						}
					}
				});
				tblWorkExpList.getSelectionModel().addListSelectionListener(new ListSelectionListener() { 
					public void valueChanged(ListSelectionEvent arg0) { //TODO add employer name and address
						if (!arg0.getValueIsAdjusting()){
							try{

								if(tblWorkExpList.getSelectedRows().length ==1){
									int row = tblWorkExpList.getSelectedRow();

									String employer_id = (String) modelWorkExp.getValueAt(row, 1);
									String employer_name = (String) modelWorkExp.getValueAt(row, 2);
									String div_dept = (String) modelWorkExp.getValueAt(row, 5);
									String position = (String) modelWorkExp.getValueAt(row, 6);
									String rank_code = (String) modelWorkExp.getValueAt(row, 7);
									String rank_desc = (String) modelWorkExp.getValueAt(row, 8);
									String emp_status = (String) modelWorkExp.getValueAt(row, 9);
									Date hired = (Date) modelWorkExp.getValueAt(row, 10);
									Date resigned = (Date) modelWorkExp.getValueAt(row, 11);
									BigDecimal basic_sal = (BigDecimal) modelWorkExp.getValueAt(row, 12);
									BigDecimal allowances = (BigDecimal) modelWorkExp.getValueAt(row, 13);
									BigDecimal others_monthly_income = (BigDecimal) modelWorkExp.getValueAt(row, 14);
									BigDecimal deductions = (BigDecimal) modelWorkExp.getValueAt(row, 15);
									BigDecimal total_gross_inc = (BigDecimal) modelWorkExp.getValueAt(row, 16);
									BigDecimal total_net_inc = (BigDecimal) modelWorkExp.getValueAt(row, 17);
									BigDecimal inc_others = (BigDecimal) modelWorkExp.getValueAt(row, 18);
									BigDecimal transportation = (BigDecimal) modelWorkExp.getValueAt(row, 21);
									BigDecimal food = (BigDecimal) modelWorkExp.getValueAt(row, 20);
									BigDecimal other_exp = (BigDecimal) modelWorkExp.getValueAt(row, 22);
									BigDecimal total_exp = (BigDecimal) modelWorkExp.getValueAt(row, 23);
									String employer_info_stat = (String) modelWorkExp.getValueAt(row, 24);

									lookupEmployer.setValue(employer_id);
									txtEmployer.setText(employer_name);
									txtDivision.setText(div_dept);
									txtPosition.setText(position);
									lookupEmployeeRank.setValue(rank_code);
									txtEmployeeRank.setText(rank_desc);
									cmbEmpStatus.setSelectedItem(emp_status);
									dateHired.setDate(hired);
									dateResigned.setDate(resigned);
									txtBasicSal.setValue(basic_sal);
									txtAllowances.setValue(allowances);
									txtOtherMI.setValue(others_monthly_income);
									txtDeductions.setValue(deductions);
									txtTotalGrossIncome.setValue(total_gross_inc);
									txtTotalMI.setValue(total_net_inc);
									txtIncomeOthers.setValue(inc_others);
									txtTransportation.setValue(transportation);
									txtFood.setValue(food);
									txtOthersME.setValue(other_exp);
									
									txtTotalExpense.setValue(total_exp);
									
									if(employer_info_stat.equals("A")){
										cmbEmployerStatus.setSelectedIndex(0);
									}else{
										cmbEmployerStatus.setSelectedIndex(1);
									}

									ci.btnState(true, true, false, false, false);
									
									if(ci.canEdit() == false){
										ci.btnState(false, false, false, false, false);
									}
									
									System.out.println("Dumaan dito sa Enabling of Buttons");
								}
							} catch (ArrayIndexOutOfBoundsException e) { }
						}
					}
				});
			}
			{
				rowHeaderWorkExpList = tblWorkExpList.getRowHeader();
				rowHeaderWorkExpList.setModel(new DefaultListModel());
				scrollWorkExpList.setRowHeaderView(rowHeaderWorkExpList);
				scrollWorkExpList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		ci.setComponentsEditable(pnlNorth, false);
		setComponentsEnabled(false, false, false, false, false, false, false, false);
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupEmployer, txtDivision, txtPosition, cmbEmpStatus, dateHired,
				dateResigned, txtBasicSal, txtAllowances, txtOtherMI, txtDeductions, txtTotalMI, txtIncomeOthers, txtTransportation, txtFood, txtOthersME, txtTotalExpense));
	}//END OF INIT GUI

	private String sqlEmployer(){// CHECK SQL OF EMPLOYERS
		String sql = "select a.entity_id as \"Employer ID\", "
				+ "trim(a.entity_name) as \"Employer Name\"\n" + 
				"from rf_entity a\n" + 
				"left join rf_entity_assigned_type b on b.entity_id = a.entity_id\n" + 
				"left join mf_entity_type c on c.entity_type_id = b.entity_type_id\n"+
				"left join rf_entity_address d on d.entity_id = a.entity_id \n" + 
				"where b.entity_type_id = '31' \n"+
				"and d.pref_billing = 't' \n"+
				"and a.status_id = 'A' \n"+
				"order by a.entity_name";
		return sql;
	}
	
	private String sqlEmployeeRank(){
		String SQL = "SELECT rank_code as \"Rank Code\", rank_desc as \"Rank Desc\" FROM mf_employee_rank WHERE status_id = 'A'";
		
		return SQL;
	}

	private Object[] getEmpStatus() {
		ArrayList<Object> Empstatus = new ArrayList<Object>();

		pgSelect db = new pgSelect();
		db.select("select statdesc from mf_client_employee_status");
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				Empstatus.add(db.getResult()[x][0]);
			}
		}
		return Empstatus.toArray();
	}
	
	public void displayWorkExp(String entity_id){
		//String entity_id = "";
		modelWorkExp.clear();
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_ci_work_exp_v2('"+entity_id+"')";
		db.select(SQL);
		
		FncSystem.out("Display Work Experience List", SQL);
		
		if(db.isNotNull()){
			for(int x = 0; x<db.getRowCount(); x++){
				modelWorkExp.addRow(db.getResult()[x]);
			}
			scrollWorkExpList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblWorkExpList.getRowCount())));
			tblWorkExpList.packAll();
			
			for(int y = 0; y<modelWorkExp.getRowCount(); y++){
				String employer_id = (String) modelWorkExp.getValueAt(y, 1);
				String employer_name = (String) modelWorkExp.getValueAt(y, 2);
				String div_dept = (String) modelWorkExp.getValueAt(y, 5);
				String position = (String) modelWorkExp.getValueAt(y, 6);
				String rank_code = (String) modelWorkExp.getValueAt(y, 7);
				String rank_desc = (String) modelWorkExp.getValueAt(y, 8);
				String emp_status = (String) modelWorkExp.getValueAt(y, 9);
				Date hired = (Date) modelWorkExp.getValueAt(y, 10);
				Date resigned = (Date) modelWorkExp.getValueAt(y, 11);
				BigDecimal basic_sal = (BigDecimal) modelWorkExp.getValueAt(y, 12);
				BigDecimal allowances = (BigDecimal) modelWorkExp.getValueAt(y, 13);
				BigDecimal others_monthly_income = (BigDecimal) modelWorkExp.getValueAt(y, 14);
				BigDecimal deductions = (BigDecimal) modelWorkExp.getValueAt(y, 15);
				BigDecimal total_gross_inc = (BigDecimal) modelWorkExp.getValueAt(y, 16);
				BigDecimal total_net_inc = (BigDecimal) modelWorkExp.getValueAt(y, 17);
				BigDecimal inc_others = (BigDecimal) modelWorkExp.getValueAt(y, 18);
				BigDecimal transportation = (BigDecimal) modelWorkExp.getValueAt(y, 21);
				BigDecimal food = (BigDecimal) modelWorkExp.getValueAt(y, 20);
				BigDecimal other_exp = (BigDecimal) modelWorkExp.getValueAt(y, 22);
				BigDecimal total_exp = (BigDecimal) modelWorkExp.getValueAt(y, 23);
				String employer_info_stat = (String) modelWorkExp.getValueAt(y, 24);
				Time pref_time_start = (Time) modelWorkExp.getValueAt(y, 25);
				Time pref_time_end = (Time) modelWorkExp.getValueAt(y, 26);
				String pref_day = (String) modelWorkExp.getValueAt(y, 27);

				if(employer_info_stat.equals("A")){
					cmbEmployerStatus.setSelectedIndex(0);
					lookupEmployer.setValue(employer_id);
					txtEmployer.setText(employer_name);
					txtDivision.setText(div_dept);
					txtPosition.setText(position);
					lookupEmployeeRank.setValue(rank_code);
					txtEmployeeRank.setText(rank_desc);
					cmbEmpStatus.setSelectedItem(emp_status);
					dateHired.setDate(hired);
					dateResigned.setDate(resigned);
					txtBasicSal.setValue(basic_sal);
					txtAllowances.setValue(allowances);
					txtOtherMI.setValue(others_monthly_income);
					txtDeductions.setValue(deductions);
					txtTotalGrossIncome.setValue(total_gross_inc);
					txtTotalMI.setValue(total_net_inc);
					txtIncomeOthers.setValue(inc_others);
					txtTransportation.setValue(transportation);
					txtFood.setValue(food);
					txtOthersME.setValue(other_exp);
					
					txtTotalExpense.setValue(total_exp);
					
					if(pref_time_start != null){
						spinnerCallStart.setValue(pref_time_start);
					}
					
					if(pref_time_end != null){
						spinnerCallEnd.setValue(pref_time_end);
					}
					
					chkSunday.setSelected(false);
					chkMonday.setSelected(false);
					chkTuesday.setSelected(false);
					chkWednesday.setSelected(false);
					chkThursday.setSelected(false);
					chkFriday.setSelected(false);
					chkSaturday.setSelected(false);
					
					if(pref_day.equals("") == false) {
						chkSunday.setSelected(pref_day.contains("0"));
						chkMonday.setSelected(pref_day.contains("1"));
						chkTuesday.setSelected(pref_day.contains("2"));
						chkWednesday.setSelected(pref_day.contains("3"));
						chkThursday.setSelected(pref_day.contains("4"));
						chkFriday.setSelected(pref_day.contains("5"));
						chkSaturday.setSelected(pref_day.contains("6"));
					}
				}
			}
		}
	}

	/*public void displayWorkExp(String entity_id){ //Add data for the employer id and employer address
		modelWorkExp.clear();

		String sql = "select "+
				"distinct on (a.rec_id) \n" + 
				"a.rec_id ,a.employer_id,trim(g.entity_name) as employer_name,\n" + 
				"d.addr_no || ' ' || d.street || ' ' || d.barangay || ' ' || nullif(e.city_name, f.municipality_name) as employer_address,\n" + 
				"trim(a.department), trim(a.position) as Position, c.statdesc  as EmploymentStatus,\n" + 
				"a.datehired as Date_Hired, a.dateresigned as Date_Resigned,\n" + 
				"a.basicsalary as Basic_Salary, a.allowance as Allowance , a.othrenumeration as Other_Monthly_Income , \n" + 
				"a.deductions as Deductions,(a.basicsalary + a.allowance + a.othrenumeration) as total_gross_income, a.totincomepermth as Total_Net_Income, \n" + 
				"a.incothsource as Income_From_Other_Sources,\n" + 
				"b.rec_id,b.food as Food, b.transportation as Transportation , b.others as Others, (b.food + b.transportation + b.others) as Total \n" + 
				"from rf_client_employer a\n" + 
				"left join rf_client_expenses b on b.entity_id = a.entity_id \n" + 
				"left join mf_client_employee_status c on c.statcode = a.employmentstatus\n" + 
				"left join rf_entity_address d on d.entity_id  = a.employer_id\n" + 
				"left join mf_city e on e.city_id = d.city_id \n" + 
				"left join mf_municipality f on f.municipality_id = d.municipality_id\n" + 
				"left join rf_entity g on g.entity_id = d.entity_id\n" + 
				"where a.entity_id = '"+entity_id+"' "+
				"and d.pref_billing = 't' ";

		FncSystem.out("Display Work Experience List", sql);
		pgSelect db = new pgSelect();
		db.select(sql); 
		if (db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact number of columns and column types.
				modelWorkExp.addRow(db.getResult()[x]);
			}
			scrollWorkExpList.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblWorkExpList.getRowCount())));
			tblWorkExpList.packAll();
		}
	}*/

	private void setComponentsEnabled(Boolean emp_stat, Boolean employer ,Boolean hired, Boolean resigned, Boolean tot_gross_inc, Boolean tot_mon_inc, Boolean total_exp, Boolean preferred){
		cmbEmpStatus.setEnabled(emp_stat);
		dateHired.setEnabled(hired);
		dateResigned.setEnabled(resigned);
		txtTotalGrossIncome.setEditable(tot_gross_inc);
		txtTotalMI.setEditable(tot_mon_inc);
		//txtTotalMI.setEditable(true);
		txtTotalExpense.setEditable(total_exp);
		txtEmployer.setEditable(employer);
		chkMonday.setEnabled(preferred);
		chkTuesday.setEnabled(preferred);
		chkWednesday.setEnabled(preferred);
		chkThursday.setEnabled(preferred);
		chkFriday.setEnabled(preferred);
		chkSaturday.setEnabled(preferred);
		chkSunday.setEnabled(preferred);
		spinnerCallStart.setEnabled(preferred);
		spinnerCallEnd.setEnabled(preferred);
		//pnlEmployerInfo_Right.se
	}

	//CHECKS WHETHER EMPLOYER IS ALREADY EXISTING
	private boolean isEmployerExisting(String entity_id, Integer emp_rec_id){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_client_employer where entity_id = '"+entity_id+"' and rec_id = "+emp_rec_id+"";
		db.select(sql);
		return db.isNotNull();
	}
	
	private boolean withActiveEmployer(String entity_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM rf_client_employer WHERE entity_id = '"+entity_id+"' AND status_id = 'A'";
		db.select(SQL);
		
		return db.isNotNull();
	}

	//CHECKS WHETHER CLIENT EXPENSES IS ALREADY EXISTING
	private boolean isClientExpenseExisting(String entity_id, Integer exp_rec_id){
		pgSelect db = new pgSelect();
		String sql = "select * from rf_client_expenses where entity_id = '"+entity_id+"' and rec_id = "+exp_rec_id+"";
		db.select(sql);
		return db.isNotNull();
	}

	public void newWorkExp(){//NEW WORK EXPERIENCE
		ci.setComponentsEditable(pnlNorth, true);
		setComponentsEnabled(true, false, true, true, false, false, false, true);
		tblWorkExpList.clearSelection();
		tblWorkExpList.setEnabled(false);
		rowHeaderWorkExpList.setEnabled(false);
		clearFieldsWorkExp();
	}

	public void editWorkExp(){//EDITING FOR THE WORK EXPERIENCE
		if(tblWorkExpList.getSelectedRows().length == 1){
			ci.setComponentsEditable(pnlNorth, true);
			setComponentsEnabled(true, false, true, true, false, false, false, true);
			tblWorkExpList.setEnabled(false);
			rowHeaderWorkExpList.setEnabled(false);
		}else{
			JOptionPane.showMessageDialog(scrollWorkExpList, "Please select only one row to edit");
			tblWorkExpList.clearSelection();
		}
	}

	public void cancelWorkexp(){//CANCELATION FOR THE WORK EXPERIENCE
		ci.setComponentsEditable(pnlNorth, false);
		setComponentsEnabled(false, false, false, false, false, false, false, false);
		
		tblWorkExpList.clearSelection();
		tblWorkExpList.setEnabled(true);
		rowHeaderWorkExpList.setEnabled(true);
		clearFieldsWorkExp();
	}

	public boolean saveEmployerInfo_MonthlyIncome(String entity_id){//SAVING AND UPDATING FOR THE EMPLOYER PANEL
		
		pgUpdate db = new pgUpdate();
		String div_dept = txtDivision.getText();
		String position = txtPosition.getText();
		String rank_code = lookupEmployeeRank.getValue();
		String emp_stat = (String) cmbEmpStatus.getSelectedItem();
		Date date_hired = dateHired.getDate();
		Date date_resigned = dateResigned.getDate();
		BigDecimal basic_sal = (BigDecimal) txtBasicSal.getValued();
		BigDecimal allowances = (BigDecimal) txtAllowances.getValued();
		BigDecimal others_mi = (BigDecimal) txtOtherMI.getValued();
		BigDecimal deductions = (BigDecimal) txtDeductions.getValued();
		
		//BigDecimal tot_mont_income = BigDecimal.valueOf(Double.valueOf(txtTotalMI.getText()));
		BigDecimal tot_mont_income = (BigDecimal) txtTotalMI.getValued();
		
		BigDecimal income_other = (BigDecimal) txtIncomeOthers.getValued();
		String employer_id = lookupEmployer.getValue();
		
		System.out.printf("Display value of basic getValued2", txtBasicSal.getValued2());
		System.out.printf("Display Total Monthly Income Get valued2: %s%n", txtTotalMI.getValued2());
		System.out.printf("Display Total Monthly Income GetText: %s%n", txtTotalMI.getText());
		System.out.printf("Display value of Variable: %s%n", tot_mont_income);
		
		if(tblWorkExpList.getSelectedRows().length ==1){
			int row = tblWorkExpList.getSelectedRow();
			Integer emp_rec_id = (Integer) modelWorkExp.getValueAt(row, 0);
			
			if(isEmployerExisting(entity_id, emp_rec_id)){
				String sql = "UPDATE rf_client_employer\n" + 
						"SET employer_id= COALESCE(NULLIF('"+employer_id+"', 'null'), ''), department= '"+div_dept+"', rank = COALESCE(NULLIF('"+rank_code+"', 'null'), '') ,\"position\"= '"+position+"', \n" + 
						"employmentstatus= (select statcode from mf_client_employee_status where statdesc = '"+emp_stat+"'), \n"+
						"basicsalary= "+basic_sal+", allowance= "+allowances+", othrenumeration= "+others_mi+", \n" + 
						"deductions="+deductions+", totincomepermth= "+tot_mont_income+", incothsource= "+income_other+", \n" + 
						"datehired=NULLIF('"+date_hired+"', 'null')::TIMESTAMP, dateresigned= nullif('"+date_resigned+"', 'null')::TIMESTAMP, edited_by='"+UserInfo.EmployeeCode+"', date_edited=now()\n" + 
						"WHERE entity_id = '"+entity_id+"' and rec_id = "+emp_rec_id+"";
				db.executeUpdate(sql, true);
			} 
		}else{
			if(withActiveEmployer(entity_id)){
				String SQL1 = "UPDATE rf_client_employer SET status_id = 'I' WHERE entity_id = '"+entity_id+"' AND status_id = 'A'";
				db.executeUpdate(SQL1, true);
			}
			
			String sql = "INSERT INTO rf_client_employer(employer_id, entity_id, department, rank, \"position\", \n" + 
						"employmentstatus, basicsalary, allowance, othrenumeration, deductions, \n" + 
						"totincomepermth, incothsource, datehired, dateresigned, \n" + 
						"status_id, created_by, date_created)\n" + 
						"VALUES (COALESCE(NULLIF('"+employer_id+"', 'null'), ''),'"+entity_id+"', '"+ div_dept +"', COALESCE(NULLIF('"+rank_code+"', 'null'), '') ,'"+ position +"', "+
						"(select statcode from mf_client_employee_status where statdesc = '"+emp_stat+"'), coalesce("+ basic_sal +", 0.00) , \n" + 
						"coalesce("+ allowances +", 0.00), coalesce("+ others_mi +", 0.00), coalesce("+ deductions +", 0.00), coalesce("+ tot_mont_income +", 0.00), coalesce("+ income_other +", 0.00), \n" + 
						"nullif('"+ date_hired +"', 'null')::timestamp, nullif('"+ date_resigned +"', 'null')::TIMESTAMP, 'A', '"+ UserInfo.EmployeeCode +"', now());\n";

			db.executeUpdate(sql, true);
		}
		db.commit();
		return true;
	}

	public boolean saveMonthlyExpenses(String entity_id){//SAVING AND UPDATING OF DATA FOR THE MONTHLY EXPENSES
		pgUpdate db = new pgUpdate();

		BigDecimal transpo = (BigDecimal) txtTransportation.getValued();
		BigDecimal food = (BigDecimal) txtFood.getValued();
		BigDecimal other_me = (BigDecimal) txtOthersME.getValued();
		//UPDATING
		
		if(tblWorkExpList.getSelectedRows().length == 1){
			int row = tblWorkExpList.getSelectedRow();
			Integer exp_rec_id = (Integer) modelWorkExp.getValueAt(row, 19);
			if(isClientExpenseExisting(entity_id, exp_rec_id)){
				String sql = "UPDATE rf_client_expenses SET food= "+food+", transportation= "+transpo+", others= "+other_me+"\n" + 
							 "WHERE entity_id = '"+entity_id+"' and rec_id = "+exp_rec_id+"";
				db.executeUpdate(sql, true);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Work Experience has been Updated");
			}
		}else{//SAVING
			String sql = "INSERT INTO rf_client_expenses(entity_id, food,transportation, others)\n" + 
						 "VALUES ('"+ entity_id +"', coalesce("+ food +", 0.00), coalesce("+ transpo +", 0.00), coalesce("+ other_me +", 0.00))";
			db.executeUpdate(sql, true);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client Work Experience has been Saved");
		}
		
		db.commit();
		return true;
	}
	
	public boolean savePrefDayTime(String entity_id) {
		Boolean save = false;
		ArrayList<String> listPrefDay = new ArrayList<String>();
		
		String call_start = new SimpleDateFormat("hh:mm:ss a").format(spinnerCallStart.getValue());
		String call_end = new SimpleDateFormat("hh:mm:ss a").format(spinnerCallEnd.getValue());
		
		if(chkSunday.isSelected()){
			listPrefDay.add("0");
		}
		
		if(chkMonday.isSelected()) {
			listPrefDay.add("1");
		}
		
		if(chkTuesday.isSelected()){
			listPrefDay.add("2");
		}
		
		if(chkWednesday.isSelected()) {
			listPrefDay.add("3");
		}
		
		if(chkThursday.isSelected()) {
			listPrefDay.add("4");
		}
		
		if(chkFriday.isSelected()) {
			listPrefDay.add("5");
		}
		
		if(chkSaturday.isSelected()) {
			listPrefDay.add("6");
		}
		
		String pref_day = listPrefDay.toString().replaceAll("\\[|\\]", "'").replace(", ", "', '");
		
		pgUpdate db = new pgUpdate();
		String SQL = "UPDATE rf_client_employer set pref_time_start = NULLIF('"+call_start+"', 'null')::TIME WITHOUT TIME ZONE, \n"+
					 "pref_time_end = NULLIF('"+call_end+"', 'null')::TIME WITHOUT TIME ZONE, pref_day = ARRAY["+ pref_day +"]::VARCHAR[] \n"+
					 "WHERE entity_id = '"+entity_id+"'";
		
		db.executeUpdate(SQL, true);
		db.commit();
		return true;
	}

	public boolean saveWorkExp(String entity_id){
		saveEmployerInfo_MonthlyIncome(entity_id);
		saveMonthlyExpenses(entity_id);
		savePrefDayTime(entity_id);
		ci.setComponentsEditable(pnlNorth, false);
		setComponentsEnabled(false, false, false, false, false, false, false, false);
		tblWorkExpList.clearSelection();
		tblWorkExpList.setEnabled(true);
		rowHeaderWorkExpList.setEnabled(true);
		clearFieldsWorkExp();
		return true;
	}
	
	public boolean toSave() {
		//FOR CHECKING BEFORE SAVING 2022-02-17
		//COMMENTED FOR CSD BECAUSE THEY ONLY ENCODE INCOME
		/*if (lookupEmployer.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Employer"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (dateHired.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input Date Hired"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		return true;
	}
	
	private void totalGI(){//Computation for the Total Gross Income
		Double basic_sal = Double.valueOf(txtBasicSal.getText().trim().replace(",","")).doubleValue();
		Double allowance = Double.valueOf(txtAllowances.getText().trim().replace(",","")).doubleValue();
		Double others_mi = Double.valueOf(txtOtherMI.getText().trim().replace(",","")).doubleValue();
		Double income_others = Double.valueOf(txtIncomeOthers.getText().trim().replace(",", "")).doubleValue();

		Double tot_gross_inc = basic_sal + allowance + others_mi + income_others;
		//txtTotalGrossIncome.setValue(BigDecimal.valueOf(tot_gross_inc));
		
		txtTotalGrossIncome.setText(df.format(tot_gross_inc));
		//txtTotalGrossIncome.setText(df.format(tot_gross_inc));
	}
	
	private void TotalNI(){ //Computation for the Total Net Income

		Double basic_sal = Double.valueOf(txtBasicSal.getText().trim().replace(",","")).doubleValue();
		Double allowance = Double.valueOf(txtAllowances.getText().trim().replace(",","")).doubleValue();
		Double others_mi = Double.valueOf(txtOtherMI.getText().trim().replace(",","")).doubleValue();
		Double income_others = Double.valueOf(txtIncomeOthers.getText().trim().replace(",", "")).doubleValue();
		Double deductions = Double.valueOf(txtDeductions.getText().trim().replace(",","")).doubleValue();
		
		Double total_mon_inc = (basic_sal + allowance + others_mi + income_others) - deductions;
		
		//txtTotalMI.setValue(BigDecimal.valueOf(total_mon_inc));
		txtTotalMI.setText(df.format(total_mon_inc));
		try {
			txtTotalMI.commitEdit();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	private void TotalExp(){ //Computation for the Total Expenses
		Double transpo = Double.valueOf(txtTransportation.getText().trim().replace(",","")).doubleValue();
		Double food = Double.valueOf(txtFood.getText().trim().replace(",","")).doubleValue();
		Double others_me = Double.valueOf(txtOthersME.getText().trim().replace(",","")).doubleValue();

		Double total_exp = (transpo + food + others_me);
		
		txtTotalExpense.setValue(BigDecimal.valueOf(total_exp));
		
		//txtTotalExpense.setText(df.format(total_exp));
	}

	public void clearFieldsWorkExp(){//CLEARS THE FIELDS IN THE WORK EXPERIENCE

		//Panel Employer Info	
		lookupEmployer.setValue(null);
		txtEmployer.setText("");
		txtDivision.setText("");;
		txtPosition.setText("");
		lookupEmployeeRank.setValue(null);
		txtEmployeeRank.setText("");
		cmbEmpStatus.setSelectedItem("CONTRACTUAL");
		dateHired.setText("");
		dateResigned.setText("");
		//Panel Monthly Income
		txtBasicSal.setText("0.00");
		txtBasicSal.setValue(new BigDecimal("0.00"));
		txtAllowances.setText("0.00");
		txtAllowances.setValue(new BigDecimal("0.00"));
		txtOtherMI.setText("0.00");
		txtOtherMI.setValue(new BigDecimal("0.00"));
		txtDeductions.setText("0.00");
		txtDeductions.setValue(new BigDecimal("0.00"));
		txtTotalGrossIncome.setText("0.00");
		txtTotalGrossIncome.setValue(new BigDecimal("0.00"));
		txtTotalMI.setText("0.00");
		txtTotalMI.setValue(new BigDecimal("0.00"));
		txtIncomeOthers.setText("0.00");
		txtIncomeOthers.setValue(new BigDecimal("0.00"));
		//Panel Monthly Expenses
		txtTransportation.setText("0.00");
		txtTransportation.setValue(new BigDecimal("0.00"));
		txtFood.setText("0.00");
		txtFood.setValue(new  BigDecimal("0.00"));
		txtOthersME.setText("0.00");
		txtOthersME.setValue(new BigDecimal("0.00"));
		txtTotalExpense.setText("0.00");
		txtTotalExpense.setValue(new BigDecimal("0.00"));
		tblWorkExpList.clearSelection();
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
}
