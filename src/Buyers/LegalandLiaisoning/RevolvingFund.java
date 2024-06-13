package Buyers.LegalandLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.Format;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.table.DefaultTableModel;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


import org.postgresql.util.PGInterval;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import net.sf.jasperreports.components.table.Column;
import tablemodel.model_RevolvingFunds;
import tablemodel.model_TcostRevolvingFunds;

public class RevolvingFund extends _JInternalFrame implements _GUI,ActionListener {

	private static final long serialVersionUID = 3957562708258887535L;
	static String title = "Revolving Funds";
	Dimension frameSize = new Dimension(750, 500);

	private JPanel pnlMain; 
	private _JLookup lookupCompany,lookupEmp,lookupControlNo;
	private JDateChooser dateChoose;
	private JButton btnAdd,btnEdit,btnDel,btnPrev,btnCancel,btnSave;
	static _JTableMain tblRevolvingFund,tblTcost; 
	private JLabel lblCompany,lblEmp,lblDate,lblControlNo,lbl1,lbl2,lblPcostDesc;
	private JTextArea txtAreaPd;
	private JList rowRevolvingFunds,rowTcost;
	private static  JTabbedPane tabDesc;
	private model_RevolvingFunds model_RevolvingFunds;
	private model_TcostRevolvingFunds tCostTable;
	private JScrollPane scrollRevolve,scrollTcost;

	String rwPcostDesc = "";
	BigDecimal rwAmount;
	String rwPcostId = "";
	String jervin = "";
	String emp = "";
	String co_id = "";
	static String crtlNo = "";
	static String control = "";
	static Integer intAmount;

	public RevolvingFund(){
		super(title, true, true, true,true);
		initGUI();
	}
	public RevolvingFund(String title){
		super(title);
		initGUI();

	}
	public RevolvingFund(String title,boolean resizable,boolean closable,boolean maximizable,boolean iconiflable){
		super(title,resizable,closable,maximizable,iconiflable);
		initGUI();

	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));		
		this.setPreferredSize(frameSize);
		this.setSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5,5,5,5));
			{
				JPanel pnlMainNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainNorth,BorderLayout.NORTH);
				pnlMainNorth.setBorder(new EmptyBorder(5,5,5,5));
				{
					JPanel pnlNorth = new JPanel(new BorderLayout(5,5));
					pnlMainNorth.add(pnlNorth,BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new Dimension(0,100));
					{
						JPanel pnlNorthWest = new JPanel(new GridLayout(4,1,3,3));
						pnlNorth.add(pnlNorthWest,BorderLayout.WEST);
						{
							lblCompany = new JLabel("Company:");
							pnlNorthWest.add(lblCompany);
							lblEmp = new JLabel("Employee:");
							pnlNorthWest.add(lblEmp);
							lblDate = new JLabel("Date:");
							pnlNorthWest.add(lblDate);
							lblControlNo = new JLabel("Control No.:");
							pnlNorthWest.add(lblControlNo);
						}
					}
					{
						JPanel pnlNorthCenter = new JPanel(new GridLayout(4,1,3,3));
						pnlNorth.add(pnlNorthCenter,BorderLayout.CENTER);
						{
							JPanel pnlCompany = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlCompany);
							{
								JPanel pnlCompanyWest = new JPanel(new BorderLayout(5,5));
								pnlCompany.add(pnlCompanyWest,BorderLayout.WEST);
								pnlCompanyWest.setPreferredSize(new Dimension(100,0));
								{
									lookupCompany = new _JLookup(null,"Company");
									pnlCompanyWest.add(lookupCompany);
									lookupCompany.setEnabled(false);
									lookupCompany.setLookupSQL(displayCompany());
									lookupCompany.setReturnColumn(0);
									lookupCompany.addLookupListener(new LookupListener() {

										@Override
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();

											co_id = (String) data[0];

											if(data != null){
												lbl1.setText(String.format("[%s]", (String) data[1]));
											}
										}
									});
								}
							}
							{
								JPanel pnlCompanyCenter = new JPanel(new BorderLayout(5,5));
								pnlCompany.add(pnlCompanyCenter,BorderLayout.CENTER);
								{
									lbl1 = new JLabel("[]");
									pnlCompanyCenter.add(lbl1);
								}
							}
						}
						{
							JPanel pnlEmp = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlEmp);
							{
								JPanel pnlEmpWest = new JPanel(new BorderLayout(5,5));
								pnlEmp.add(pnlEmpWest,BorderLayout.WEST);
								pnlEmpWest.setPreferredSize(new Dimension(150,0));
								{
									lookupEmp = new _JLookup(null,"Employee");
									pnlEmpWest.add(lookupEmp);
									lookupEmp.setEnabled(false);
									lookupEmp.setLookupSQL(displayEmp());
									lookupEmp.setReturnColumn(0);
									lookupEmp.addLookupListener(new LookupListener() {

										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											emp = (String) data[0];  
											if(data != null ){
												lbl2.setText(String.format("[%s]",(String) data[1]));
											}


										}
									});
								}
							}
							{
								JPanel pnlEmpCenter = new JPanel(new BorderLayout(5,5));
								pnlEmp.add(pnlEmpCenter,BorderLayout.CENTER);
								{
									lbl2 = new JLabel("[]");
									pnlEmpCenter.add(lbl2);
								}
							}
						}
						{
							JPanel pnlDate = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlDate);
							{
								JPanel pnlDateCenter = new JPanel(new BorderLayout(5,5));
								pnlDate.add(pnlDateCenter,BorderLayout.WEST);
								pnlDateCenter.setPreferredSize(new Dimension(150,0));
								{
									dateChoose = new JDateChooser();	
									pnlDateCenter.add(dateChoose);
									dateChoose.setEnabled(false);
									JTextFieldDateEditor editDate = (JTextFieldDateEditor)dateChoose.getComponent(1);
									editDate.setHorizontalAlignment(JTextField.CENTER);
									dateChoose.setDate(FncGlobal.getDateToday());
									dateChoose.setDateFormatString("MM/dd/yyyy");
								}
							}
						}
						{
							JPanel pnlControlNo = new JPanel(new BorderLayout(5,5));
							pnlNorthCenter.add(pnlControlNo);
							{
								JPanel pnlControlNoWest = new JPanel(new BorderLayout(5,5));
								pnlControlNo.add(pnlControlNoWest,BorderLayout.WEST);
								pnlControlNoWest.setPreferredSize(new Dimension(150,0));
								{
									lookupControlNo = new _JLookup(null,"Control Numbers",0);
									pnlControlNoWest.add(lookupControlNo);
									lookupControlNo.setLookupSQL(displayControlNoPcost());
									lookupControlNo.addLookupListener(new LookupListener() {


										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											crtlNo = (String) data[0];
											//lookupCompany.setValue(ctrlCompany());
											//lbl1.setText(String.format("[%s]", lookupCompany.getValue());
											ButtonState(3);
											cntrlLookup();
											ctrlValue();
											tblRevolvingFund.setEditable(false);
											tblRevolvingFund.setEnabled(true);
											tblTcost.setEditable(false);
											tblTcost.setEnabled(true);
											



										}
									});
								}
							}
						}
					}
				}
				{
					JPanel pnlSouth = new JPanel(new BorderLayout(5,5));
					pnlMainNorth.add(pnlSouth,BorderLayout.SOUTH);
					pnlSouth.setPreferredSize(new Dimension(0,80));
					{
						JPanel pnlSouthNorth = new JPanel(new BorderLayout(5,5));
						pnlSouth.add(pnlSouthNorth,BorderLayout.NORTH);
						{
							lblPcostDesc = new JLabel("Pcost Desc:");
							pnlSouthNorth.add(lblPcostDesc);
						}
					}
					{
						JPanel pnlSouthCenter = new JPanel(new BorderLayout(5,5));
						pnlSouth.add(pnlSouthCenter,BorderLayout.CENTER);
						{
							txtAreaPd = new JTextArea();
							JScrollPane scrollTa = new JScrollPane();
							pnlSouthCenter.add(scrollTa);
							scrollTa.setViewportView(txtAreaPd);
							txtAreaPd.setEnabled(false);
						}
					}
				}
			}
			{
				JPanel pnlMainCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainCenter,BorderLayout.CENTER);
				{
					tabDesc = new JTabbedPane();
					pnlMainCenter.add(tabDesc);
					

					{
						scrollRevolve = new JScrollPane();
						tabDesc.addTab("PCost", scrollRevolve);
						scrollRevolve.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrollRevolve.setBorder(new EmptyBorder(5,5,5,5));

						{
							model_RevolvingFunds = new model_RevolvingFunds();
							tblRevolvingFund = new _JTableMain(model_RevolvingFunds);
							scrollRevolve.setViewportView(tblRevolvingFund);
							//						tblRevolvingFund.getColumnModel().getColumn(2).setPreferredWidth(225);
							tblRevolvingFund.hideColumns("Record ID","Remarks");
							tblRevolvingFund.setSortable(false);
							tblRevolvingFund.setEnabled(false);
							tblRevolvingFund.getSelectionModel().addListSelectionListener(new ListSelectionListener() {



								public void valueChanged(ListSelectionEvent arg0) {

									if(tblRevolvingFund.getSelectedRows().length==1){
										if(jervin.equals("Edit")){
											int row = tblRevolvingFund.convertRowIndexToModel(tblRevolvingFund.getSelectedRow());
											String txtArea = (String) model_RevolvingFunds.getValueAt(row, 5);

											txtAreaPd.setText(txtArea);

										} 
										else {
											int row = tblRevolvingFund.getSelectedRow();
											String txtArea = (String) model_RevolvingFunds.getValueAt(row, 5);

											txtAreaPd.setText(txtArea);
										}
									}		

								}
							});;
							tblRevolvingFund.addMouseListener(new MouseListener() {

								public void mouseReleased(MouseEvent arg0) {}
								public void mousePressed(MouseEvent arg0) {}
								public void mouseExited(MouseEvent arg0) {}
								public void mouseEntered(MouseEvent arg0) {}
								public void mouseClicked(MouseEvent e) {
									System.out.println("dumaaaanananana d2");
									if(e.getSource().equals(tblRevolvingFund)){
										int selectedRow = tblRevolvingFund.getSelectedRow();
										System.out.println("row:"+selectedRow);

										Boolean isSelected = (Boolean) model_RevolvingFunds.getValueAt(selectedRow, 0);
										if(tblRevolvingFund.getSelectedColumn()==3){
											if(e.getClickCount()==2){
												if(isSelected){
													String amountValue = JOptionPane.showInputDialog(getContentPane(), "Input Amount:");
													System.out.println("amount:"+amountValue);
													//intAmount = Integer.parseInt(amountValue);
													//BigDecimal bigAmount = BigDecimal.valueOf(amountValue);
													//tblRevolvingFund.setValueAt(amountValue ,selectedRow, 3);
													model_RevolvingFunds.setValueAt(new BigDecimal(amountValue),selectedRow,3);


												}
											}


										}
										else if(tblRevolvingFund.getSelectedColumn()==2 ) {
											if(e.getClickCount()==2){
												if(isSelected){
													Integer value = tblRevolvingFund.getSelectedRow();
													if((Boolean) tblRevolvingFund.getValueAt(value, 1).equals("218")){
														txtAreaPd.setEnabled(true);
													}
													
													
												}
												
											}

										}

									}


								}
							});

						}
					}
					{
						rowRevolvingFunds = tblRevolvingFund.getRowHeader();
						rowRevolvingFunds.setModel(new DefaultListModel());
						scrollRevolve.setRowHeaderView(rowRevolvingFunds);
						scrollRevolve.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollTcost = new JScrollPane();
						tabDesc.addTab("TCost", scrollTcost);
						scrollTcost.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
						scrollTcost.setBorder(new EmptyBorder(5,5,5,5));

					}
					{
						tCostTable = new model_TcostRevolvingFunds();
						tblTcost = new _JTableMain(tCostTable);
						scrollTcost.setViewportView(tblTcost);
						tblTcost.setSortable(false);
						tblTcost.hideColumns("Record ID");
						tblTcost.setEnabled(false);
						tblTcost.addMouseListener(new MouseListener() {


							public void mouseReleased(MouseEvent e) {}

							public void mousePressed(MouseEvent e) {}

							public void mouseExited(MouseEvent e) {}

							public void mouseEntered(MouseEvent e) {}


							public void mouseClicked(MouseEvent e) {
								if(e.getSource().equals(tblTcost)){
									int row = tblTcost.getSelectedRow();
									Boolean isSelected = (Boolean) tCostTable.getValueAt(row, 0);
									if(tblTcost.getSelectedColumn()==3){
										if(e.getClickCount()==2){
											if(isSelected){
												String amountValue = JOptionPane.showInputDialog(getContentPane(), "Input Amount:");
												System.out.println("amount:"+amountValue);
												tCostTable.setValueAt(new BigDecimal(amountValue),row,3);
											}
										}
									}
								}
							}
						});

					}
					{
						rowTcost = tblTcost.getRowHeader();
						rowTcost.setModel(new DefaultListModel());
						scrollTcost.setRowHeaderView(rowTcost );
						scrollTcost.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					tabDesc.addChangeListener(new ChangeListener() {
						
						
						public void stateChanged(ChangeEvent e) {
						if(tabDesc.getSelectedIndex()==0){
							ButtonState(1);
							lookupControlNo.setLookupSQL(displayControlNoPcost());
							rowTcost.setModel(new DefaultListModel());
							lookupCompany.setEnabled(false);
							lookupEmp.setEnabled(false);
							lookupControlNo.setEnabled(true);
							tblTcost.setEnabled(false);
							lookupCompany.setValue("");
							lookupEmp.setValue("");
							lookupControlNo.setValue("");
							dateChoose.setDate(FncGlobal.getDateToday());
							dateChoose.setEnabled(false);
							lbl1.setText("[]");
							lbl2.setText("[]");
							tCostTable.clear();
							txtAreaPd.setText("");
							txtAreaPd.setEnabled(false);
						}
						else if(tabDesc.getSelectedIndex()==1){
							ButtonState(1);
							lookupControlNo.setLookupSQL(displayControlNoTcost());
							rowRevolvingFunds.setModel(new DefaultListModel());
							lookupCompany.setEnabled(false);
							lookupEmp.setEnabled(false);
							lookupControlNo.setEnabled(true);
							tblRevolvingFund.setEnabled(false);
							lookupCompany.setValue("");
							lookupEmp.setValue("");
							lookupControlNo.setValue("");
							dateChoose.setDate(FncGlobal.getDateToday());
							dateChoose.setEnabled(false);
							lbl1.setText("[]");
							lbl2.setText("[]");
							model_RevolvingFunds.clear();
							txtAreaPd.setText("");
							txtAreaPd.setEnabled(false);
						}
						}
					});
				}



			}
			{
				JPanel pnlMainSouth = new JPanel(new GridLayout(1,8,3,3));
				pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
				pnlMainSouth.setPreferredSize(new Dimension(0,30));
				{
					btnAdd = new JButton("Add");
					pnlMainSouth.add(btnAdd);
					btnAdd.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent arg0) {
							if(tabDesc.getSelectedIndex()==0){
								jervin = "Add";
								ButtonState(2);
								displayValue(model_RevolvingFunds,rowRevolvingFunds);
								tmpInsertValue();
								lookupCompany.setEnabled(true);
								lookupEmp.setEnabled(true);
								dateChoose.setEnabled(true);
								lookupControlNo.setEnabled(false);
								lookupControlNo.setValue(controlNoValue());
								lookupCompany.setValue("");
								lookupEmp.setValue("");
								lbl1.setText("[]");
								lbl2.setText("[]");
								btnSave.setEnabled(true);
								btnCancel.setEnabled(true);
								tblRevolvingFund.setEnabled(true);
								//							displayValue(model_RevolvingFunds,rowRevolvingFunds);
								txtAreaPd.setText("");
								txtAreaPd.setEnabled(false);
								tblRevolvingFund.setEditable(true);

							}
							else if(tabDesc.getSelectedIndex()==1){
								jervin = "Add";
								ButtonState(2);
								displayValue(tCostTable,rowTcost);
								tmpInsertValue();
								lookupCompany.setEnabled(true);
								lookupEmp.setEnabled(true);
								dateChoose.setEnabled(true);
								lookupControlNo.setEnabled(false);
								lookupControlNo.setValue(controlNoValue());
								lookupCompany.setValue("");
								lookupEmp.setValue("");
								lbl1.setText("[]");
								lbl2.setText("[]");
								btnSave.setEnabled(true);
								btnCancel.setEnabled(true);
								tblTcost.setEnabled(true);
								txtAreaPd.setText("");
								txtAreaPd.setEnabled(false);
								tblTcost.setEditable(true);

							} 

						}
					});
				}
				{
					btnEdit = new JButton("Edit");
					pnlMainSouth.add(btnEdit);
					btnEdit.setEnabled(false);
					btnEdit.addActionListener(new ActionListener() {


						public void actionPerformed(ActionEvent arg0) {
							jervin = "Edit";
							if(tabDesc.getSelectedIndex()==0){
								
							int row	= tblRevolvingFund.getSelectedRow();
							Boolean selected = (Boolean) model_RevolvingFunds.getValueAt(row, 0);


							if(selected){
								if((Boolean) tblRevolvingFund.getValueAt(row, 1).equals("218"));
								txtAreaPd.setEnabled(true);
								tblRevolvingFund.setEnabled(true);
								tblRevolvingFund.setEditable(true);

								ButtonState(4);
							}else{
								tblRevolvingFund.setEnabled(true);
								ButtonState(4);
								tblRevolvingFund.setEditable(true);

							}
						}
							else if(tabDesc.getSelectedIndex()==1){
//								jervin = "Edit";
								
									
									tblTcost.setEnabled(true);
									ButtonState(4);
									tblTcost.setEditable(true);
									

								
								
								
							}
						}

					});
				}
				{
					btnSave = new JButton("Save");
					pnlMainSouth.add(btnSave);
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {


						public void actionPerformed(ActionEvent arg0) {
							if(checkCompany()==false){
								JOptionPane.showMessageDialog(getContentPane(), "Please select a company first!", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else if(checkEmployee()==false){
								JOptionPane.showMessageDialog(getContentPane(), "Please select a employee first!", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else if(checkTag()==false){
								JOptionPane.showMessageDialog(getContentPane(), "Please select a particular first!", "Error", JOptionPane.ERROR_MESSAGE);
							}
							else{
								if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to save it?", "Save",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
									if(tabDesc.getSelectedIndex()==0){
										if(jervin.equals("Edit")){
											System.out.print("Dumaan dito una");
											updateValue();
											JOptionPane.showMessageDialog(getContentPane(), "Successfully Save", "Succes", JOptionPane.INFORMATION_MESSAGE);
											displayValue(model_RevolvingFunds,rowRevolvingFunds);
											rowRevolvingFunds.setModel(new DefaultListModel());
											ButtonState(1);
											lookupCompany.setEnabled(false);
											lookupEmp.setEnabled(false);
											lookupControlNo.setEnabled(true);
											tblRevolvingFund.setEnabled(false);
											lookupCompany.setValue("");
											lookupEmp.setValue("");
											lookupControlNo.setValue("");
											dateChoose.setDate(FncGlobal.getDateToday());
											dateChoose.setEnabled(false);
											lbl1.setText("[]");
											lbl2.setText("[]");
											model_RevolvingFunds.clear();
											txtAreaPd.setText("");
											txtAreaPd.setEnabled(false);

										}
										else{
											System.out.print("Dumaan dito dalawa");
											saveValue();
											JOptionPane.showMessageDialog(getContentPane(), "Successfully Save", "Succes", JOptionPane.INFORMATION_MESSAGE);
											displayValue(model_RevolvingFunds,rowRevolvingFunds);
											rowRevolvingFunds.setModel(new DefaultListModel());
											ButtonState(1);
											lookupCompany.setEnabled(false);
											lookupEmp.setEnabled(false);
											lookupControlNo.setEnabled(true);
											tblRevolvingFund.setEnabled(false);
											lookupCompany.setValue("");
											lookupEmp.setValue("");
											lookupControlNo.setValue("");
											dateChoose.setDate(FncGlobal.getDateToday());
											dateChoose.setEnabled(false);
											lbl1.setText("[]");
											lbl2.setText("[]");
											model_RevolvingFunds.clear();
											txtAreaPd.setText("");
											txtAreaPd.setEnabled(false);

										}
									}
									else if(tabDesc.getSelectedIndex()==1){
										if(jervin.equals("Edit")){
											System.out.print("Dumaan dito una");
											updateValue();
											JOptionPane.showMessageDialog(getContentPane(), "Successfully Save", "Succes", JOptionPane.INFORMATION_MESSAGE);
											displayValue(tCostTable,rowTcost);
											rowTcost.setModel(new DefaultListModel());
											ButtonState(1);
											lookupCompany.setEnabled(false);
											lookupEmp.setEnabled(false);
											lookupControlNo.setEnabled(true);
											tblTcost.setEnabled(false);
											lookupCompany.setValue("");
											lookupEmp.setValue("");
											lookupControlNo.setValue("");
											dateChoose.setDate(FncGlobal.getDateToday());
											dateChoose.setEnabled(false);
											lbl1.setText("[]");
											lbl2.setText("[]");
											tCostTable.clear();
											txtAreaPd.setText("");
											txtAreaPd.setEnabled(false);

										}
										else{
											System.out.print("Dumaan dito dalawa");
											saveValue();
											JOptionPane.showMessageDialog(getContentPane(), "Successfully Save", "Succes", JOptionPane.INFORMATION_MESSAGE);
											displayValue(tCostTable,rowTcost);
											rowTcost.setModel(new DefaultListModel());
											ButtonState(1);
											lookupCompany.setEnabled(false);
											lookupEmp.setEnabled(false);
											lookupControlNo.setEnabled(true);
											tblTcost.setEnabled(false);
											lookupCompany.setValue("");
											lookupEmp.setValue("");
											lookupControlNo.setValue("");
											dateChoose.setDate(FncGlobal.getDateToday());
											dateChoose.setEnabled(false);
											lbl1.setText("[]");
											lbl2.setText("[]");
											tCostTable.clear();
											txtAreaPd.setText("");
											txtAreaPd.setEnabled(false);

										}

									}
								}
							}
						}

					});
				} 
				{
					btnDel = new JButton("Delete");
					jervin = "Delete";
					pnlMainSouth.add(btnDel);
					btnDel.setEnabled(false);
					btnDel.addActionListener(new ActionListener() {


						public void actionPerformed(ActionEvent arg0) {
							if(tabDesc.getSelectedIndex()==0 ){
								jervin = "Delete";
								if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete control number?", "Delete",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
									ButtonState(1);
									deleteValue();
									rowRevolvingFunds.setModel(new DefaultListModel());
									JOptionPane.showMessageDialog(getContentPane(), "Successfully Deleted", "Delete", JOptionPane.INFORMATION_MESSAGE);
									lookupCompany.setEnabled(false);
									lookupEmp.setEnabled(false);
									lookupControlNo.setEnabled(true);
									tblRevolvingFund.setEnabled(false);
									lookupCompany.setValue("");
									lookupEmp.setValue("");
									lookupControlNo.setValue("");
									dateChoose.setDate(FncGlobal.getDateToday());
									dateChoose.setEnabled(false);
									lbl1.setText("[]");
									lbl2.setText("[]");
									model_RevolvingFunds.clear();
									txtAreaPd.setText("");
									txtAreaPd.setEnabled(false);
								}
							}
							else if(tabDesc.getSelectedIndex()==1){
								jervin = "Delete";
								if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete control number?", "Delete",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
									ButtonState(1);
									deleteValue();
									rowTcost.setModel(new DefaultListModel());
									JOptionPane.showMessageDialog(getContentPane(), "Successfully Deleted", "Delete", JOptionPane.INFORMATION_MESSAGE);
									lookupCompany.setEnabled(false);
									lookupEmp.setEnabled(false);
									lookupControlNo.setEnabled(true);
									tblTcost.setEnabled(false);
									lookupCompany.setValue("");
									lookupEmp.setValue("");
									lookupControlNo.setValue("");
									dateChoose.setDate(FncGlobal.getDateToday());
									dateChoose.setEnabled(false);
									lbl1.setText("[]");
									lbl2.setText("[]");
									tCostTable.clear();
									txtAreaPd.setText("");
									txtAreaPd.setEnabled(false);
								}
							}
						}
					});					
				}
				{
					btnPrev = new JButton("Preview");
					pnlMainSouth.add(btnPrev);
					btnPrev.setEnabled(false);
					btnPrev.addActionListener(new ActionListener() {


						public void actionPerformed(ActionEvent arg0) {
							prevValue();

						}
					});
				}
				{
					btnCancel = new JButton("Cancel");
					pnlMainSouth.add(btnCancel);
					btnCancel.setEnabled(false);

					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(jervin.equals("Add")){
								cancel();
								ButtonState(1);
								rowRevolvingFunds.setModel(new DefaultListModel());
								rowTcost.setModel(new DefaultListModel());
								lookupCompany.setEnabled(false);
								lookupEmp.setEnabled(false);
								lookupControlNo.setEnabled(true);
								tblRevolvingFund.setEnabled(false);
								tblTcost.setEnabled(false);
								lookupCompany.setValue("");
								lookupEmp.setValue("");
								lookupControlNo.setValue("");
								dateChoose.setDate(FncGlobal.getDateToday());
								dateChoose.setEnabled(false);
								lbl1.setText("[]");
								lbl2.setText("[]");
								model_RevolvingFunds.clear();
								tCostTable.clear();
								txtAreaPd.setText("");
								txtAreaPd.setEnabled(false);

							}
							else {


								ButtonState(1);
								rowRevolvingFunds.setModel(new DefaultListModel());
								rowTcost.setModel(new DefaultListModel());
								lookupCompany.setEnabled(false);
								lookupEmp.setEnabled(false);
								lookupControlNo.setEnabled(true);
								tblRevolvingFund.setEnabled(false);
								tblTcost.setEnabled(false);
								lookupCompany.setValue("");
								lookupEmp.setValue("");
								lookupControlNo.setValue("");
								dateChoose.setDate(FncGlobal.getDateToday());
								dateChoose.setEnabled(false);
								lbl1.setText("[]");
								lbl2.setText("[]");
								model_RevolvingFunds.clear();
								tCostTable.clear();
								txtAreaPd.setText("");
								txtAreaPd.setEnabled(false);
							}
						}

					});
				}
			}
		}


	}





	private void displayValue(DefaultTableModel model_RevolvingFunds,JList rowRevolvingFunds) {
		if(tabDesc.getSelectedIndex()==0){
			FncTables.clearTable(model_RevolvingFunds);	
			DefaultListModel listModel = new DefaultListModel();
			rowRevolvingFunds.setModel(listModel);

			String sql = "SELECT false, pcostdtl_id as \"PCost ID.\", trim(pcostdtl_desc) as \"PCost Desc\" ,0.00\n" + 
					"				FROM mf_processing_cost_dl  a\n" + 
					"				WHERE status_id = 'A' \n"+ 
					"				GROUP BY pcostdtl_id, pcostdtl_desc\n" + 
					"				ORDER BY pcostdtl_desc ASC";

			//		String sql ="SELECT false, pcostdtl_id  as PCostID, trim(pcostdtl_desc) as PCostDesc,0.00 FROM mf_processing_cost_dl a"+
			//				" left join rf_revolving_funds b on a.pcostdtl_desc = b.pcost_desc and a.pcostdtl_id = b.pcost_id"+
			//				" where a.status_id ='A' and not EXISTs(select * from rf_revolving_funds where a.pcostdtl_id = b.pcost_id and status_id = 'I')";
			FncSystem.out("Display description", sql);
			pgSelect db = new pgSelect();
			db.select(sql);

			if(db.isNotNull()){ 
				for(int x=0; x < db.getRowCount(); x++){
					model_RevolvingFunds.addRow(db.getResult()[x]);
					listModel.addElement(model_RevolvingFunds.getRowCount());
					//rowHeaderRevolvingFunds
					//model_RevolvingFunds.setCellEditable(x, 0, true);

				}	
			}
			tblRevolvingFund.packAll();
		}
		else if(tabDesc.getSelectedIndex()==1 ){
			FncTables.clearTable(model_RevolvingFunds);	
			DefaultListModel listModel = new DefaultListModel();
			rowRevolvingFunds.setModel(listModel);

			String sql = "SELECT false, tcost_id as \"TCost ID.\", trim(tcostdtl_desc) as \"TCost Desc\" ,0.00\n" + 
					"				FROM mf_transfer_cost_dl  a\n" + 
					"				WHERE status_id = 'A' \n"+ 
					"				GROUP BY tcost_id, tcostdtl_desc\n" + 
					"				ORDER BY tcostdtl_desc ASC";


			FncSystem.out("Display description", sql);
			pgSelect db = new pgSelect();
			db.select(sql);

			if(db.isNotNull()){ 
				for(int x=0; x < db.getRowCount(); x++){
					model_RevolvingFunds.addRow(db.getResult()[x]);
					listModel.addElement(model_RevolvingFunds.getRowCount());

				}
			}
			tblTcost.packAll();
		}



	}
	public static String displayCompany() {
		String query = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
				"TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
		System.out.println("display:"+ query);
		return query;

	}
	public static String displayEmp(){

		String query = "SELECT TRIM(a.emp_code)::VARCHAR AS \"ID\",TRIM(b.entity_name) AS \"Employee\" FROM em_employee a "
				+ "Inner join rf_entity b on a.entity_id = b.entity_id  "
				+ "where b.entity_id in('0000000108','0000000127','0000000011','4044329496','3977762893','7022320846','3025278016','6294702999','7091486691') order by entity_name";
		return query;
	}
	public static String displayControlNoPcost(){
		
		String query = "select aa.control_number from (\n" + 
				"select distinct on (trim(control_no)) trim(control_no):: VARCHAR as control_number,*  from rf_revolving_funds WHERE status_id = 'A'  ORDER BY trim(control_no)\n" + 
				") aa\n" + 
				"order by aa.date_created desc";
		return query;
	}
	public static String displayControlNoTcost(){
		String query = "select aa.control_number from (\n" + 
				"SELECT DISTINCT ON (TRIM(control_no)) (TRIM(control_no)):: VARCHAR AS control_number,* FROM rf_tcost_revolving_funds WHERE status_id = 'A' ORDER BY TRIM(control_no)\n" + 
				"	) aa\n" + 
				"order by aa.date_created desc";
		return query;
	}
	public void updateValue(){
		if(tabDesc.getSelectedIndex()==0){
			pgUpdate db = new pgUpdate();
			String txtValue = txtAreaPd.getText(); 

			for(int x = 0;x<model_RevolvingFunds.getRowCount();x++){
				Boolean isSelected = (Boolean) model_RevolvingFunds.getValueAt(x, 0);
				if(isSelected){
					rwAmount = (BigDecimal) model_RevolvingFunds.getValueAt(x, 3);
					Integer rec_id= (Integer)model_RevolvingFunds.getValueAt(x, 4);
					System.out.println("rec1:"+rec_id);
					String query = "UPDATE rf_revolving_funds SET amount = "+rwAmount+",edited_by = '"+UserInfo.EmployeeCode+"',date_edited = '"+FncGlobal.getDateToday()+"',remarks = '"+txtValue+"' WHERE rec_id = '"+rec_id+"'";
					db.executeUpdate(query, true);
					System.out.println("rec:"+rec_id);
				}
			}
			db.commit();
		}
		else if(tabDesc.getSelectedIndex()==1){
			pgUpdate db = new pgUpdate();

			for(int x = 0;x<tCostTable.getRowCount();x++){
				Boolean isSelected = (Boolean) tCostTable.getValueAt(x, 0);
				if(isSelected){
					BigDecimal amount = (BigDecimal) tCostTable.getValueAt(x, 3);
					Integer rec_id= (Integer)tCostTable.getValueAt(x, 4);
					System.out.println("rec1:"+rec_id);
					String query = "UPDATE rf_tcost_revolving_funds SET amount = "+amount+",edited_by = '"+UserInfo.EmployeeCode+"',date_edited = '"+FncGlobal.getDateToday()+"' WHERE rec_id = '"+rec_id+"'";
					db.executeUpdate(query, true);
					System.out.println("rec:"+rec_id);
				}
			}
			db.commit();

		}
	}
	public void  saveValue(){
		if(tabDesc.getSelectedIndex()==0){
			Date getDate =(Date) dateChoose.getDate();
			String cntrl = controlNoValue();
			pgUpdate db = new pgUpdate();

			for(int x = 0;x<model_RevolvingFunds.getRowCount();x++){
				Boolean isSelected = (Boolean) model_RevolvingFunds.getValueAt(x, 0);
				if(isSelected){

					rwPcostDesc  = (String) model_RevolvingFunds.getValueAt(x, 2);
					rwPcostId = (String) model_RevolvingFunds.getValueAt(x, 1);
					//			rwAmount = BigDecimal.valueOf( (long) model_RevolvingFunds.getValueAt(x, 3));
					rwAmount = (BigDecimal) model_RevolvingFunds.getValueAt(x, 3);
					String txtValue = txtAreaPd.getText();



					String query = "INSERT INTO rf_revolving_funds (co_id,emp_id,pcost_desc,pcost_id,amount,control_no,status_id,created_by,date_created,remarks,rplf_no) VALUES('"+co_id+"','"+emp+"',\n"+
							"'"+rwPcostDesc+"','"+rwPcostId+"',"+rwAmount+",'"+cntrl+"','A','"+UserInfo.EmployeeCode+"','"+getDate+"','"+txtValue+"','')";
					db.executeUpdate(query, true);


				}
			}
			db.commit();
		}
		else if(tabDesc.getSelectedIndex()==1){
			Date getDate =(Date) dateChoose.getDate();
			String cntrl = controlNoValue();
			pgUpdate db = new pgUpdate();

			for(int x = 0;x<tCostTable.getRowCount();x++){
				Boolean isSelected = (Boolean) tCostTable.getValueAt(x, 0);
				if(isSelected){

					String tcostDesc  = (String) tCostTable.getValueAt(x, 2);
					String tcostId = (String) tCostTable.getValueAt(x, 1);
					BigDecimal amount = (BigDecimal) tCostTable.getValueAt(x, 3);




					String query = "INSERT INTO rf_tcost_revolving_funds (co_id,emp_id,tcost_desc,tcost_id,amount,control_no,status_id,created_by,date_created,rplf_no) VALUES('"+co_id+"','"+emp+"',\n"+
							"'"+tcostDesc+"','"+tcostId+"',"+amount+",'"+cntrl+"','A','"+UserInfo.EmployeeCode+"','"+getDate+"','')";
					db.executeUpdate(query, true);


				}
			}
			db.commit();

		}
	}
	public void deleteValue(){
		if(tabDesc.getSelectedIndex()==0){
			pgUpdate db = new pgUpdate();
			String query = "UPDATE rf_revolving_funds SET status_id = 'I',deleted_by = '"+UserInfo.EmployeeCode+"',date_deleted = now() WHERE control_no = '"+crtlNo+"'";
			db.executeUpdate(query, true);
			db.commit();
		}
		else if(tabDesc.getSelectedIndex()==1){
			pgUpdate db = new pgUpdate();
			String query = "UPDATE rf_tcost_revolving_funds SET status_id = 'I',deleted_by = '"+UserInfo.EmployeeCode+"',date_deleted = now() WHERE control_no = '"+crtlNo+"'";
			db.executeUpdate(query, true);
			db.commit();

		}
	}
	public static String controlNoValue(){
		pgSelect db = new pgSelect();
		//		String query = "SELECT to_char(COALESCE(MAX(control_no::INT), 0) + 1, 'FM0000000000')"
		//				+ "FROM rf_revolving_funds";
		String query = "  select trim(to_char(extract(month from current_date),'00')) || trim(to_char(extract(day from current_date),'00')) ||(extract(year from current_date))||'-'||\n"+
				"trim(to_char(count(control_no)+1,'0000')) from tmp_revolving_funds where date_created::DATE = Current_date";
		db.select(query);

		control = (String) db.getResult()[0][0];
		return control;
	}

	private void ButtonState(Integer intState) {

		if (intState==1) { // save,delete,cancel
			btnAdd.setEnabled(true);
			btnEdit.setEnabled(false);
			btnDel.setEnabled(false);
			btnSave.setEnabled(false);
			btnPrev.setEnabled(false);
			btnCancel.setEnabled(false);
		} else if (intState==2) {//add
			btnAdd.setEnabled(false);
			btnEdit.setEnabled(false);
			btnDel.setEnabled(false);
			btnSave.setEnabled(true);
			btnPrev.setEnabled(false);
			btnCancel.setEnabled(true);			
		}else if(intState==3){//controlookup
			btnAdd.setEnabled(true);
			btnEdit.setEnabled(true);
			btnDel.setEnabled(true);
			btnSave.setEnabled(false);
			btnPrev.setEnabled(true);
			btnCancel.setEnabled(true);	

		}else if(intState==4){//edit
			btnAdd.setEnabled(false);
			btnEdit.setEnabled(false);
			btnDel.setEnabled(true);
			btnSave.setEnabled(true);
			btnPrev.setEnabled(false);
			btnCancel.setEnabled(true);	
		}

	}
	public void cntrlLookup(){
		if(tabDesc.getSelectedIndex()==0){
		if(model_RevolvingFunds != null){
			FncTables.clearTable(model_RevolvingFunds);
			DefaultListModel listMode = new DefaultListModel();
			rowRevolvingFunds.setModel(listMode);

			pgSelect db = new pgSelect();
			String query = "SELECT FALSE,pcost_id,pcost_desc,amount,rec_id,remarks FROM rf_revolving_funds where control_no = '"+crtlNo+"'";

			db.select(query);
			if(db.isNotNull()){
				for(int x = 0;x<db.getRowCount();x++){
					model_RevolvingFunds.addRow(db.getResult()[x]);
					listMode.addElement(tblRevolvingFund.getRowCount());


				}
			}

		}
		}
		else if(tabDesc.getSelectedIndex()==1){
			if(tCostTable != null){
				FncTables.clearTable(tCostTable);
				DefaultListModel listMode = new DefaultListModel();
				rowTcost.setModel(listMode);

				pgSelect db = new pgSelect();
				String query = "SELECT FALSE,tcost_id,tcost_desc,amount,rec_id FROM rf_tcost_revolving_funds where control_no = '"+crtlNo+"'";

				db.select(query);
				if(db.isNotNull()){
					for(int x = 0;x<db.getRowCount();x++){
						tCostTable.addRow(db.getResult()[x]);
						listMode.addElement(tblTcost.getRowCount());


					}
				}

			}
			
		}
	}
	public void ctrlValue(){
		Object[] value = ctrlQuery();
		if(value != null){
			lookupCompany.setText((String)value[0]);
			lbl1.setText(String.format("[%s]",value[1]));
			lookupEmp.setText((String) value[2]);
			lbl2.setText(String.format("[%s]", value[3]));
			dateChoose.setDate((Date) value[4]);


		}
	}
	public static Object[] ctrlQuery(){
	if(tabDesc.getSelectedIndex()==0){
		pgSelect db= new pgSelect();
		String query = "select a.co_id,b.company_name,a.emp_id,d.entity_name,a.date_created/*,a.control_no*/ from rf_revolving_funds  a "
				+ "left join mf_company b on a.co_id = b.co_id "
				+ "left Join em_employee c on a.emp_id = c.emp_code  "
				+ "left join rf_entity d on c.entity_id = d.entity_id where a.control_no = '"+crtlNo+"'";
		db.select(query);
		if(db != null){
			return db.getResult()[0];

		}
		
	}
	else if(tabDesc.getSelectedIndex()==1){
		pgSelect db= new pgSelect();
		String query = "select a.co_id,b.company_name,a.emp_id,d.entity_name,a.date_created from rf_tcost_revolving_funds  a "
				+ "left join mf_company b on a.co_id = b.co_id "
				+ "left Join em_employee c on a.emp_id = c.emp_code  "
				+ "left join rf_entity d on c.entity_id = d.entity_id where a.control_no = '"+crtlNo+"'";
		db.select(query);
		if(db != null){
			return db.getResult()[0];

		}
	}
	
	return null;
	
		
	}
	public void tmpInsertValue(){
		String controlNo = controlNoValue();
		pgUpdate db = new pgUpdate();
		String query = "INSERT INTO tmp_revolving_funds(control_no,created_by,date_created,status_id) VALUES "
				+ "('"+controlNo+"','"+UserInfo.EmployeeCode+"','"+FncGlobal.getDateToday()+"','A')";
		db.executeUpdate(query, true);

		db.commit();
	}
	public void prevValue(){
		Map<String, Object>mapParameter = new HashMap<String, Object>();
		mapParameter.put("emp_code",UserInfo.EmployeeCode);
		mapParameter.put("control_no", crtlNo);
		if(tabDesc.getSelectedIndex()==0){

		FncReport.generateReport("/Reports/rptRevolvingFunds.jasper", "Revolving Funds",mapParameter);
	}
		else
		{
			FncReport.generateReport("/Reports/rptTcostRevolvingFunds.jasper", "Revolving Funds",mapParameter);
		} 
			
	}
	public void cancel(){
		pgUpdate db = new pgUpdate();
		String query = "UPDATE tmp_revolving_funds SET status_id = 'I' where control_no ='"+crtlNo+"' ";
		db.executeUpdate(query, true);

		db.commit();
	}

	private Boolean checkCompany(){

		Boolean x = false;

		String company = lookupCompany.getText();

		if((company == null) || (company.equals("null")) || (company.equals(""))){
			x = false;
		}else{
			x = true;
		}
		return x;
	}

	private Boolean checkEmployee(){

		Boolean x = false;

		String employee = lookupEmp.getText();

		if((employee == null) || (employee.equals("null")) || (employee.equals(""))){
			x = false;
		}else{
			x = true;
		}
		return x;
	}

	private Boolean checkTag(){

		Boolean x = false;


		if(tabDesc.getSelectedIndex()==0){

		for(int y = 0; y<model_RevolvingFunds.getRowCount(); y++){
			Boolean isSelected = (Boolean) model_RevolvingFunds.getValueAt(y, 0);


			if(isSelected){
				x = true;
			}
		}
		
		}
		else if(tabDesc.getSelectedIndex()==1){
			
			for(int y = 0; y<tCostTable.getRowCount(); y++){
				Boolean isSelected = (Boolean) tCostTable.getValueAt(y, 0);


				if(isSelected){
					x = true;
				}
		}
		}
		return x;
		
		
	}


}
