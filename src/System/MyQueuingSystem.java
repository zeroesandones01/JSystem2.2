package System;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

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
import tablemodel.modelQueuingSystem;

public class MyQueuingSystem extends _JInternalFrame implements _GUI, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlDetails;
	private JLabel lblDetails;
	private JPanel pnlNorthCenter;
	private JTextField txtDetails;
	private JPanel pnlNorthButtons;
	private JButton btnGenerate;
	private JButton btnSave;
	private JPanel pnlCenter;
	private JPanel pnlCenterWest;
	private JScrollPane scrollList;
	private modelQueuingSystem modelQueue;
	private _JTableMain tblQueue;
	private JList rowHeaderQueue;
	private JPanel pnlCenterDisplay;
	private JPanel pnlDisplay;
	private JTextField txtDisplayArea;
	private JPanel pnlCenterButtons;
	private JButton btnNext;
	private JLabel lblDept;
	private JPanel pnlDept;
	private _JLookup lookupDept;
	private JPanel pnlDetailTextField;
	private JPanel pnlDeptName;
	private JLabel lblDeptName;
	private String dept_alias;
	private JButton btnPayment;
	private JButton btnCollection;
	private JButton btnProperty;
	private JButton btnDocuments;
	
	Font font = new Font("SansSerif", Font.PLAIN, 32);
	private ButtonGroup group;
	private JPanel pnlLMD;
	private JPanel pnlLMDCenter;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4; 


	public MyQueuingSystem() {
		// TODO Auto-generated constructor stub
		super("My Queuing System", false, true, true, false);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(10,10));
		//this.setSize(new Dimension(500,130));
		this.setSize(new Dimension(500,500));
		{
			pnlMain = new JPanel(new BorderLayout(10,10));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(60,60,60,60));
			
			{//**PANEL FOR LMD
				pnlLMD = new JPanel(new BorderLayout(5,5));
				pnlLMD.setPreferredSize(new Dimension(200,300));
				{
					pnlLMDCenter = new JPanel(new GridLayout(4,1,5,5));
					pnlLMD.add(pnlLMDCenter, BorderLayout.CENTER);
					{
						btn1 = new JButton("FIRST FILLING");
						pnlLMDCenter.add(btn1);
						btn1.setFont(FncGlobal.sizeControls);
						btn1.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								Integer order_no = 1;
								generateDocumentNo(order_no);
							}
						});
					}
					{
						btn2 = new JButton("POST FILLING");
						pnlLMDCenter.add(btn2);
						btn2.setFont(FncGlobal.sizeControls);
						btn2.addActionListener(this);
						btn2.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								Integer order_no = 2;
								generateDocumentNo(order_no);
							}
						});
					}
					{
						btn3 = new JButton("PAGIBIG REM");
						pnlLMDCenter.add(btn3);
						btn3.setFont(FncGlobal.sizeControls);
						btn3.addActionListener(this);
						btn3.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								Integer order_no = 3;
								generateDocumentNo(order_no);
							}
						});
					}
					{
						btn4 = new JButton("IN-HOUSE");
						pnlLMDCenter.add(btn4);
						btn4.setFont(FncGlobal.sizeControls);
						btn4.addActionListener(this);
						btn4.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								Integer order_no = 4;
								generateDocumentNo(order_no);
							}
						});
					}
				}
			}
			{
				pnlCenter = new JPanel(new GridLayout(2,2,60,60));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					btnPayment = new JButton("PAYMENTS");
					pnlCenter.add(btnPayment);
					btnPayment.setFont(font);
					btnPayment.addActionListener(this);
					btnPayment.setActionCommand("Payments");
				}
				{
					btnCollection = new JButton("COLLECTION");
					pnlCenter.add(btnCollection);
					btnCollection.setFont(font);
					btnCollection.addActionListener(this);
					btnCollection.setActionCommand("Collection");
				}
				{
					btnProperty = new JButton("PROPERTY");
					pnlCenter.add(btnProperty);
					btnProperty.setFont(font);
					btnProperty.addActionListener(this);
					btnProperty.setActionCommand("Property");
				}
				{
					btnDocuments = new JButton("DOCUMENTS");
					pnlCenter.add(btnDocuments);
					btnDocuments.setFont(font);
					btnDocuments.addActionListener(this);
					btnDocuments.setActionCommand("Documents");
				}
			}
			/*{
				pnlNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0,85));
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
				{
					pnlDetails = new JPanel(new GridLayout(2,1,5,5));
					pnlNorth.add(pnlDetails, BorderLayout.WEST);
					{
						lblDept = new JLabel("Dept.:");
						pnlDetails.add(lblDept);
						
					}
					{
						lblDetails = new JLabel("Queue No.:");
						pnlDetails.add(lblDetails);
						
					}
				}
				{
					pnlNorthCenter = new JPanel(new GridLayout(2,1,5,5));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					{
						pnlDept = new JPanel(new BorderLayout(5,5));
						pnlNorthCenter.add(pnlDept);
						{
							lookupDept = new _JLookup(null, "Department");
							pnlDept.add(lookupDept, BorderLayout.WEST);
							lookupDept.setPreferredSize(new Dimension(50,0));
							lookupDept.setLookupSQL(getDepartment());
							lookupDept.setReturnColumn(0);
							lookupDept.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data!=null) {
										String dept_name = (String) data[01];
										dept_alias = (String) data[02];
										
										lblDeptName.setText(String.format("[ %s ]", dept_name));
									}
								}
							});
						}
						{
							pnlDeptName = new JPanel(new BorderLayout(5,5));
							pnlDept.add(pnlDeptName, BorderLayout.CENTER);
							{
								lblDeptName = new JLabel("[]");
								pnlDeptName.add(lblDeptName);
							}
						}
					}
					{
						pnlDetailTextField = new JPanel(new BorderLayout(5,5));
						pnlNorthCenter.add(pnlDetailTextField);
						{
							txtDetails = new JTextField("");
							pnlDetailTextField.add(txtDetails, BorderLayout.WEST);
							txtDetails.setPreferredSize(new Dimension(280,0));
							txtDetails.setEditable(false);
							txtDetails.setHorizontalAlignment(SwingConstants.CENTER);
						}
						{
							pnlNorthButtons = new JPanel(new GridLayout(1,1,5,5));
							pnlDetailTextField.add(pnlNorthButtons, BorderLayout.CENTER);
							{
								btnGenerate = new JButton("Generate");
								pnlNorthButtons.add(btnGenerate);
								btnGenerate.addActionListener(this);
								btnGenerate.setActionCommand("Generate");
							}//						{
//								btnSave = new JButton("Save");
//								pnlNorthButtons.add(btnSave);
//								btnSave.addActionListener(this);
//								btnSave.setActionCommand("Save");
//							}

						}
					}
				}
			}*/
			/*{
				pnlCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlCenterWest = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlCenterWest, BorderLayout.WEST);
					pnlCenterWest.setBorder(components.JTBorderFactory.createTitleBorder("List"));
					pnlCenterWest.setPreferredSize(new Dimension(180,0));
					{
						scrollList = new JScrollPane();
						pnlCenterWest.add(scrollList);
						scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					}
					{
						modelQueue = new modelQueuingSystem();
						tblQueue = new _JTableMain(modelQueue);
						
						scrollList.setViewportView(tblQueue);
						tblQueue.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblQueue.getColumn(0).setPreferredWidth(140);
						tblQueue.setEnabled(false);
					}
//					{
//						rowHeaderQueue = tblQueue.getRowHeader();
//						rowHeaderQueue.setModel(new DefaultListModel());
//						scrollList.setRowHeaderView(rowHeaderQueue);
//						scrollList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
//					}
				}
				{
					pnlCenterDisplay = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlCenterDisplay, BorderLayout.CENTER);
					{
						Border linecolor = BorderFactory.createLineBorder(Color.GRAY);
						TitledBorder title = BorderFactory.createTitledBorder(linecolor, "NOW SERVING!");
						
						pnlDisplay = new JPanel(new BorderLayout(5,5));
						pnlCenterDisplay.add(pnlDisplay, BorderLayout.CENTER);
						pnlDisplay.setBorder(title);
						title.setTitleJustification(TitledBorder.CENTER);
						pnlDisplay.setPreferredSize(new Dimension(0,150));
						{
							Font font = new Font("SansSerif", Font.PLAIN, 24);
							
							txtDisplayArea = new JTextField("");
							pnlDisplay.add(txtDisplayArea);
							txtDisplayArea.setFont(font);
							txtDisplayArea.setOpaque(false);
							txtDisplayArea.setEditable(false);
							txtDisplayArea.setHorizontalAlignment(SwingConstants.CENTER);
						}
					}
					{
						pnlCenterButtons = new JPanel(new BorderLayout(5,5));
						pnlCenterDisplay.add(pnlCenterButtons, BorderLayout.SOUTH);
						pnlCenterButtons.setPreferredSize(new Dimension(0,30));
						{
							btnNext = new JButton("Next");
							pnlCenterButtons.add(btnNext);
							btnNext.addActionListener(this);
							btnNext.setActionCommand("Next");
						}
					}
				}
			}*/
		}
		
		//displayQueueNumbers(modelQueue);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//if(e.getActionCommand().equals("Generate"))	{	generateQueueNo();}
		
		//if(e.getActionCommand().equals("Next"))		{	nextQueue();}
		
		if(e.getActionCommand().equals("Payments"))		{	generatePaymentNo();}
		
		if(e.getActionCommand().equals("Collection"))	{	generateCollectionNo();}
		
		if(e.getActionCommand().equals("Property"))		{	generatePropertyNo();}
		
		//if(e.getActionCommand().equals("Documents"))	{	generateDocumentNo();}
		if(e.getActionCommand().equals("Documents"))	{	showLMDProcess();}
		
	}
	
	private void showLMDProcess() {
		
		JOptionPane.showOptionDialog(getContentPane(), pnlLMD, "Documents",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
		
	}

	private void generateDocumentNo(Integer order_no) {
		
		Object[] options = {"Regular", "Priority"};
		String dept_code = "52";
		String dept_alias = "LMD";
		String qno = getQueueNo(dept_code);
		//String proc_no = concatQueueNo(qno);
		//String strQ = dept_alias + proc_no;
		String strQ = dept_alias + qno;
		
		int x = JOptionPane.showOptionDialog(getContentPane(),
				"Please choose one.",
				"Confirmation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, 
				options, 
				options[0]);
		
		if(x == 0) {
			//insertQueueNo(dept_code, dept_alias, proc_no, false, order_no);
			//JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+dept_alias+"" + ""+proc_no+"" + ".\n"
			//		+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			insertQueueNo(dept_code, dept_alias, qno, false, order_no);
			JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+strQ+"" + ".\n"
					+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlLMD);
			optionPaneWindow.dispose();
			
			printQueue(strQ);
			insertAuditTrail(strQ);
		}
		
		else if(x == 1) {
			//insertQueueNo(dept_code, dept_alias, proc_no, true, order_no);
			//JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+dept_alias+"" + ""+proc_no+"" + ".\n"
			//		+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			insertQueueNo(dept_code, dept_alias, qno, true, order_no);
			JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+strQ+"" + ".\n"
					+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlLMD);
			optionPaneWindow.dispose();
			
			printQueue(strQ);
			insertAuditTrail(strQ);
		}
		
		else {
			System.out.println("NO ACTION");
		}	
	}

	private void generatePropertyNo() {
		
		Object[] options = {"Regular", "Priority"};
		String dept_code = "08";
		String dept_alias = "PMD";
		String qno = getQueueNo(dept_code);
		//String proc_no = concatQueueNo(qno);
		//String strQ = dept_alias + proc_no;
		String strQ = dept_alias + qno;
		Integer order_no = 1;
		
		int x = JOptionPane.showOptionDialog(getContentPane(),
				"Please choose one.",
				"Confirmation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, 
				options, 
				options[0]);
		
		if(x == 0) {
			//insertQueueNo(dept_code, dept_alias, proc_no, false, order_no);
			//JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+dept_alias+"" + ""+proc_no+"" + ".\n"
			//		+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			insertQueueNo(dept_code, dept_alias, qno, false, order_no);
			JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+strQ+"" + ".\n"
					+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			
			printQueue(strQ);
			insertAuditTrail(strQ);
		}
		
		else if(x == 1) {
			//insertQueueNo(dept_code, dept_alias, proc_no, true, order_no);
			//JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+dept_alias+"" + ""+proc_no+"" + ".\n"
			//		+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			insertQueueNo(dept_code, dept_alias, qno, true, order_no);
			JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+strQ+"" + ".\n"
					+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			printQueue(strQ);
			insertAuditTrail(strQ);
		}
		
		else {
			System.out.println("NO ACTION");
		}
	
	}

	private void generateCollectionNo() {
		
		Object[] options = {"Regular", "Priority"};
		String dept_code = "54";
		String dept_alias = "CCD";
		String qno = getQueueNo(dept_code);
		//String proc_no = concatQueueNo(qno);
		//String strQ = dept_alias + proc_no;
		String strQ = dept_alias + qno;
		Integer order_no = 1;
		
		int x = JOptionPane.showOptionDialog(getContentPane(),
				"Please choose one.",
				"Confirmation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, 
				options, 
				options[0]);
		
		if(x == 0) {
			//insertQueueNo(dept_code, dept_alias, proc_no, false, order_no);
			//JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+dept_alias+"" + ""+proc_no+"" + ".\n"
			//		+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			insertQueueNo(dept_code, dept_alias, qno, false, order_no);
			JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+strQ+"" + ".\n"
					+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			printQueue(strQ);
			insertAuditTrail(strQ);
		}
		
		else if(x == 1) {
			//insertQueueNo(dept_code, dept_alias, proc_no, false, order_no);
			//JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+dept_alias+"" + ""+proc_no+"" + ".\n"
			//		+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			insertQueueNo(dept_code, dept_alias, qno, true, order_no);
			JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+strQ+"" + ".\n"
					+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			printQueue(strQ);
			insertAuditTrail(strQ);
		}
		
		else {
			System.out.println("NO ACTION");
		}
	}

	private void generatePaymentNo() {
		
		Object[] options = {"Regular", "Priority"};
		String dept_code = "02";
		String dept_alias = "CSD";
		String qno = getQueueNo(dept_code);
		//String proc_no = concatQueueNo(qno);
		//String strQ = dept_alias + proc_no;
		String strQ = dept_alias + qno;
		Integer order_no = 1;
		
		int x = JOptionPane.showOptionDialog(getContentPane(),
				"Please choose one.",
				"Confirmation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, 
				options, 
				options[0]);
		
		if(x == 0) {
			//insertQueueNo(dept_code, dept_alias, proc_no, false, order_no);
			//JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+dept_alias+"" + ""+proc_no+"" + ".\n"
			//		+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			insertQueueNo(dept_code, dept_alias, qno, false, order_no);
			JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+strQ+"" + ".\n"
					+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			printQueue(strQ);
			insertAuditTrail(strQ);
		}
		
		else if(x == 1) {
			//insertQueueNo(dept_code, dept_alias, proc_no, false, order_no);
			//JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+dept_alias+"" + ""+proc_no+"" + ".\n"
			//		+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			insertQueueNo(dept_code, dept_alias, qno, true, order_no);
			JOptionPane.showMessageDialog(getContentPane(), "Your number is " + ""+strQ+"" + ".\n"
					+ "You may proceed to the waiting area.", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			printQueue(strQ);
			insertAuditTrail(strQ);
		}
		
		else {
			System.out.println("NO ACTION");
		}
	}

//	private void saveQueue() {
//		String proc_no = txtDetails.getText();
//		
//		String sql = "INSERT INTO rf_queuing_system(proc_no, status_id, date_created)\n"+
//					"values('"+proc_no+"', 'A', now())";
//		
//		pgUpdate db = new pgUpdate();
//		db.executeUpdate(sql, true);
//		db.commit();
//		
//		displayQueueNumbers(modelQueue);
	
//	}

	private void insertQueueNo(String dept_code, String dept_alias, String proc_no, Boolean isPrio, Integer order_no) {
		
		/*String dept_code = lookupDept.getValue();
		String queue_no = getQueueNo(dept_code);
		txtDetails.setText(queue_no);
		
		String proc_no = txtDetails.getText();
		
		String sql = "INSERT INTO rf_queuing_system(dept_code, dept_alias, proc_no, status_id, date_created)\n"+
				"values('"+dept_code+"', '"+dept_alias+"', '"+proc_no+"', 'A', now())";
	
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, true);
		db.commit();
	
		displayQueueNumbers(modelQueue);*/
		
		String sql = "INSERT INTO rf_queuing_system(dept_code, dept_alias, proc_no, status_id, date_created, \"isPriority\", order_no)\n"+
				"values('"+dept_code+"', '"+dept_alias+"', '"+proc_no+"', 'A', now(), "+isPrio+", "+order_no+")";
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, true);
		db.commit();
		
	}
	
	private static String getQueueNo(String dept_code) {
		String queue = "";
		
		//String sql = "select to_char(coalesce(max(proc_no::int),0)+1, 'FM000') from rf_queuing_system where dept_code = '"+dept_code+"' and date_created::date = now()::date";
		String sql = 
				"select to_char(coalesce(max(right(proc_no, 3)::int),0)+1, 'FM000')\n" + 
				"from rf_queuing_system \n" + 
				"where dept_code = '"+dept_code+"' \n" + 
				"and date_created::date = now()::date";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			queue = db.getResult()[0][0].toString();
		}
		
		return queue;
	}
	
//	private static String concatQueueNo(String qno) {
//		String queue = "";
//		
//		String sql = 
//				"select to_char(now()::DATE, 'yymmdd') || '-' || '"+qno+"'";
//		
//		pgSelect db = new pgSelect();
//		db.select(sql);
//		
//		if(db.isNotNull()) {
//			queue = db.getResult()[0][0].toString();
//		}
//		
//		return queue;
//	}
	
	private void printQueue(String queue_no) {
		
		String report = "Queuing System";
		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), report.toUpperCase());
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("p_logo", this.getClass().getClassLoader().getResourceAsStream("Images/cenqlogo.png"));
		mapParameters.put("p_queue_no", queue_no);
		
		FncReport.generateReport("/Reports/rptQueueNo.jasper", reportTitle, null, mapParameters);
	}
	
	private void insertAuditTrail(String queue_no) {
		
		String emp_code = UserInfo.EmployeeCode;
		
		String sql = "INSERT INTO public.rf_queuing_trail(\n" + 
				"	activity, processed_by, date_upd, status_id, remarks)\n" + 
				"	VALUES ('ADD QUEUE', '"+emp_code+"', now(), 'A', format('QNO:%s', '"+queue_no+"'))";
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();
		
	}
}
