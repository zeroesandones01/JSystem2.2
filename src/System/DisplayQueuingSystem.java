package System;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import Functions.UserInfo;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelQueuingSystem;

public class DisplayQueuingSystem extends _JInternalFrame implements _GUI, ActionListener, InternalFrameListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlMain;
	private JPanel pnlWest;
	private JScrollPane scrollList;
	private modelQueuingSystem modelQueue;
	private _JTableMain tblQueue;
	private JPanel pnlCenter;
	private JPanel pnlDisplay;
	static JTextField txtDisplayArea;
	private JPanel pnlCenterButtons;
	private JButton btnNext;
	private JPanel pnlTable;
	private JButton btnInject;
	private JPanel pnlInject;
	private JPanel pnlInjectCenter;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JRadioButton rad1;
	private JRadioButton rad2;
	private JRadioButton rad3;
	private JRadioButton rad4;
	private JPanel pnlLMD;
	private JPanel pnlLMDCenter;
	private JButton btnFF;
	private JButton btnPF;
	private JButton btnREM;
	private JButton btnIHF;
	private String chkDept;
	
	public DisplayQueuingSystem() {
		// TODO Auto-generated constructor stub
		super("Display Queuing System", false, true, true, true);
		initGUI();
		setDefaultCloseOperation(_JInternalFrame.DO_NOTHING_ON_CLOSE);
	}

	@Override
	public void initGUI() {
		chkDept = getDepartment();
		
		this.setLayout(new BorderLayout(5,5));
		this.setSize(new Dimension(750, 300));
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			/*{
				pnlWest= new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlWest, BorderLayout.WEST);
				pnlWest.setPreferredSize(new Dimension(180,0));
				{
					pnlTable = new JPanel(new BorderLayout(5,5));
					pnlWest.add(pnlTable, BorderLayout.CENTER);
					pnlTable.setBorder(components.JTBorderFactory.createTitleBorder("List"));
					{
						scrollList = new JScrollPane();
						pnlTable.add(scrollList);
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
				}
				{
					pnlCenterButtons = new JPanel(new BorderLayout(5,5));
					pnlWest.add(pnlCenterButtons, BorderLayout.SOUTH);
					pnlCenterButtons.setPreferredSize(new Dimension(0,30));
					{
						btnNext = new JButton("Next");
						pnlCenterButtons.add(btnNext);
						btnNext.addActionListener(this);
						btnNext.setActionCommand("Next");
					}
				}
			}*/
			{//**PANEL FOR INJECT
				pnlInject = new JPanel(new BorderLayout(5,5));
				pnlInject.setPreferredSize(new Dimension(200,300));
				{
					if(chkDept.equals("52")) {
						System.out.println("Dumaan dito 52");
						{
							pnlInjectCenter = new JPanel(new GridLayout(4,1,5,5));
							pnlInject.add(pnlInjectCenter, BorderLayout.CENTER);
							{
								btn1 = new JButton("");
								pnlInjectCenter.add(btn1);
								btn1.addActionListener(this);
								btn1.setFont(FncGlobal.sizeControls);
							}
							{
								btn2 = new JButton("");
								pnlInjectCenter.add(btn2);
								btn2.addActionListener(this);
								btn2.setFont(FncGlobal.sizeControls);
							}
							{
								btn3 = new JButton("");
								pnlInjectCenter.add(btn3);
								btn3.addActionListener(this);
								btn3.setFont(FncGlobal.sizeControls);
							}
							{
								btn4 = new JButton("");
								pnlInjectCenter.add(btn4);
								btn4.addActionListener(this);
								btn4.setFont(FncGlobal.sizeControls);
							}
						}
					}else {
						System.out.println("Dumaan dito others");
						{
							pnlInjectCenter = new JPanel(new GridLayout(3,1,5,5));
							pnlInject.add(pnlInjectCenter, BorderLayout.CENTER);
							{
								btn1 = new JButton("");
								pnlInjectCenter.add(btn1);
								btn1.addActionListener(this);
								btn1.setFont(FncGlobal.sizeControls);
							}
							{
								btn2 = new JButton("");
								pnlInjectCenter.add(btn2);
								btn2.addActionListener(this);
								btn2.setFont(FncGlobal.sizeControls);
							}
							{
								btn3 = new JButton("");
								pnlInjectCenter.add(btn3);
								btn3.addActionListener(this);
								btn3.setFont(FncGlobal.sizeControls);
							}
						}
					}
				}
//				{
//					pnlInjectCenter = new JPanel(new GridLayout(3,1,5,5));
//					pnlInject.add(pnlInjectCenter, BorderLayout.CENTER);
//					{
//						btn1 = new JButton("");
//						pnlInjectCenter.add(btn1);
//						btn1.addActionListener(this);
//						btn1.setFont(FncGlobal.sizeControls);
//					}
//					{
//						btn2 = new JButton("");
//						pnlInjectCenter.add(btn2);
//						btn2.addActionListener(this);
//						btn2.setFont(FncGlobal.sizeControls);
//					}
//					{
//						btn3 = new JButton("");
//						pnlInjectCenter.add(btn3);
//						btn3.addActionListener(this);
//						btn3.setFont(FncGlobal.sizeControls);
//					}
//				}
			}
			{//**PANEL FOR LMD
				pnlLMD = new JPanel(new BorderLayout(5,5));
				pnlLMD.setPreferredSize(new Dimension(200,300));
				{
					pnlLMDCenter = new JPanel(new GridLayout(4,1,5,5));
					pnlLMD.add(pnlLMDCenter, BorderLayout.CENTER);
					{
						btnFF = new JButton("FIRST FILLING");
						pnlLMDCenter.add(btnFF);
						btnFF.setFont(FncGlobal.sizeControls);
						btnFF.addActionListener(this);
						btnFF.setActionCommand("FIRST FILLING");
					}
					{
						btnPF = new JButton("POST FILLING");
						pnlLMDCenter.add(btnPF);
						btnPF.setFont(FncGlobal.sizeControls);
						btnPF.addActionListener(this);
						btnPF.setActionCommand("POST FILLING");
					}
					{
						btnREM = new JButton("PAGIBIG REM");
						pnlLMDCenter.add(btnREM);
						btnREM.setFont(FncGlobal.sizeControls);
						btnREM.addActionListener(this);
						btnREM.setActionCommand("REM");
					}
					{
						btnIHF = new JButton("IN-HOUSE");
						pnlLMDCenter.add(btnIHF);
						btnIHF.setFont(FncGlobal.sizeControls);
						btnIHF.addActionListener(this);
						btnIHF.setActionCommand("IHF");
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					if(chkDept.equals("52")) {
						{
							JPanel pnlTest = new JPanel(new GridLayout(1,4,3,3));
							pnlCenter.add(pnlTest, BorderLayout.NORTH);
							pnlTest.setPreferredSize(new Dimension(0,20));
							ButtonGroup group = new ButtonGroup();
							{
								rad1 = new JRadioButton("FF");
								pnlTest.add(rad1);
								group.add(rad1);
								//rad1.setSelected(true);
							}
							{
								rad2 = new JRadioButton("PF");
								pnlTest.add(rad2);
								group.add(rad2);
							}
							{
								rad3 = new JRadioButton("REM");
								pnlTest.add(rad3);
								group.add(rad3);
							}
							{
								rad4 = new JRadioButton("IHF");
								pnlTest.add(rad4);
								group.add(rad4);
							}
							
							initialize_comp();
						}
					}
				}
				{
					Border linecolor = BorderFactory.createLineBorder(Color.GRAY);
					TitledBorder title = BorderFactory.createTitledBorder(linecolor, "NOW SERVING!");

					pnlDisplay = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlDisplay, BorderLayout.CENTER);
					pnlDisplay.setBorder(title);
					title.setTitleJustification(TitledBorder.CENTER);
					pnlDisplay.setPreferredSize(new Dimension(0,150));
					{
						Font font = new Font("SansSerif", Font.PLAIN, 48);

						txtDisplayArea = new JTextField("");
						pnlDisplay.add(txtDisplayArea);
						txtDisplayArea.setFont(font);
						txtDisplayArea.setOpaque(false);
						txtDisplayArea.setEditable(false);
						txtDisplayArea.setHorizontalAlignment(SwingConstants.CENTER);
					}
				}
				{
					pnlCenterButtons = new JPanel(new GridLayout(1,2,5,5));
					pnlCenter.add(pnlCenterButtons, BorderLayout.SOUTH);
					pnlCenterButtons.setPreferredSize(new Dimension(0,30));
					{
						btnNext = new JButton("Next");
						pnlCenterButtons.add(btnNext);
						btnNext.addActionListener(this);
						btnNext.setActionCommand("Next");
						btnNext.setFont(FncGlobal.sizeControls);
					}
					{
						btnInject = new JButton("Inject");
						pnlCenterButtons.add(btnInject);
						btnInject.addActionListener(this);
						btnInject.setActionCommand("Inject");
						btnInject.setFont(FncGlobal.sizeControls);
					}
				}
			}
		}
	}
	
	private void initialize_comp() {
		String emp_code = UserInfo.EmployeeCode;
		getActiveOrderNo(emp_code);
		
		if(getActiveOrderNo(emp_code) == 1) {
			rad1.setSelected(true);
		}
		if(getActiveOrderNo(emp_code) == 2) {
			rad2.setSelected(true);
		}
		if(getActiveOrderNo(emp_code) == 3) {
			rad3.setSelected(true);
		}
		if(getActiveOrderNo(emp_code) == 4) {
			rad4.setSelected(true);
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Next"))			{ nextQueue();}
		
		if(e.getActionCommand().equals("Inject"))		{ injectQueueNo();}
		
		if(e.getActionCommand().equals("COLLECTIONS"))	{ fwdtoCCD(); }
		
		if(e.getActionCommand().equals("PAYMENTS"))		{ fwdtoCSD(); }
		
		if(e.getActionCommand().equals("DOCUMENTS"))	{ fwdtoLMD(); }
		
		if(e.getActionCommand().equals("PROPERTY"))		{ fwdtoPMD(); }
		
		if(e.getActionCommand().equals("FIRST FILLING")){ fwdtoFF(); }
		
		if(e.getActionCommand().equals("POST FILLING"))	{ fwdtoPF(); }
		
		if(e.getActionCommand().equals("REM"))			{ fwdtoREM(); }
		
		if(e.getActionCommand().equals("IHF"))			{ fwdtoIHF(); }
		
		
	}

	private void fwdtoIHF() {
		System.out.println("Forward to IHF");
		if(JOptionPane.showConfirmDialog(getContentPane(), "Forward to IHF?", "Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {

			String proc_no = txtDisplayArea.getText();

			String sql = "UPDATE rf_queuing_system SET\n" +
						"fwd_to_dept = '52', status_id = 'A', processed_by = null, order_no = 4\n" +
						"where proc_no = (select right('"+proc_no+"',3)) and status_id = 'P' and dept_alias = (select left('"+proc_no+"',3))";

			pgUpdate db = new pgUpdate();
			db.executeUpdate(sql, false);
			db.commit();
			
			insertAuditTrail("INJECT", proc_no, "IHF");
			
			JOptionPane.showMessageDialog(getContentPane(), "Successfully forwarded!", "Message", JOptionPane.INFORMATION_MESSAGE);

			Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlLMD);
			optionPaneWindow.dispose();

			txtDisplayArea.setText("");
		}
	}

	private void fwdtoREM() {
		System.out.println("Forward to REM");
		if(JOptionPane.showConfirmDialog(getContentPane(), "Forward to PAGIBIG REM?", "Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {

			String proc_no = txtDisplayArea.getText();

			String sql = "UPDATE rf_queuing_system SET\n" +
						"fwd_to_dept = '52', status_id = 'A', processed_by = null, order_no = 3\n" +
						"where proc_no = (select right('"+proc_no+"',3)) and status_id = 'P' and dept_alias = (select left('"+proc_no+"',3))";

			pgUpdate db = new pgUpdate();
			db.executeUpdate(sql, false);
			db.commit();
			
			insertAuditTrail("INJECT", proc_no, "REM");

			JOptionPane.showMessageDialog(getContentPane(), "Successfully forwarded!", "Message", JOptionPane.INFORMATION_MESSAGE);

			Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlLMD);
			optionPaneWindow.dispose();

			txtDisplayArea.setText("");
		}
	}

	private void fwdtoPF() {
		System.out.println("Forward to PF");
		if(JOptionPane.showConfirmDialog(getContentPane(), "Forward to Post Filling?", "Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {

			String proc_no = txtDisplayArea.getText();

			String sql = "UPDATE rf_queuing_system SET\n" +
						"fwd_to_dept = '52', status_id = 'A', processed_by = null, order_no = 2\n" +
						"where proc_no = (select right('"+proc_no+"',3)) and status_id = 'P' and dept_alias = (select left('"+proc_no+"',3))";

			pgUpdate db = new pgUpdate();
			db.executeUpdate(sql, false);
			db.commit();
			
			insertAuditTrail("INJECT", proc_no, "PF");

			JOptionPane.showMessageDialog(getContentPane(), "Successfully forwarded!", "Message", JOptionPane.INFORMATION_MESSAGE);

			Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlLMD);
			optionPaneWindow.dispose();

			txtDisplayArea.setText("");
		}
	}

	private void fwdtoFF() {
		System.out.println("Forward to FF");
		if(JOptionPane.showConfirmDialog(getContentPane(), "Forward to First Filling?", "Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {

			String proc_no = txtDisplayArea.getText();

			String sql = "UPDATE rf_queuing_system SET\n" +
						"fwd_to_dept = '52', status_id = 'A', processed_by = null, order_no = 1\n" +
						"where proc_no = (select right('"+proc_no+"',3)) and status_id = 'P' and dept_alias = (select left('"+proc_no+"',3))";

			pgUpdate db = new pgUpdate();
			db.executeUpdate(sql, false);
			db.commit();

			insertAuditTrail("INJECT", proc_no, "FF");
			
			JOptionPane.showMessageDialog(getContentPane(), "Successfully forwarded!", "Message", JOptionPane.INFORMATION_MESSAGE);

			Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlLMD);
			optionPaneWindow.dispose();

			txtDisplayArea.setText("");
		}
	}

	private void fwdtoPMD() {
		System.out.println("Forward to PMD");
		if(JOptionPane.showConfirmDialog(getContentPane(), "Forward to PMD?", "Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {
			
			String proc_no = txtDisplayArea.getText();
			 
			String sql = "UPDATE rf_queuing_system SET\n" +
					 	"fwd_to_dept = '08', status_id = 'A', processed_by = null, order_no = 1\n" +
					 	"where proc_no = (select right('"+proc_no+"',3)) and status_id = 'P' and dept_alias = (select left('"+proc_no+"',3))";
			 
			pgUpdate db = new pgUpdate();
			db.executeUpdate(sql, false);
			db.commit();
			
			insertAuditTrail("INJECT", proc_no, "PROPERTY");
			
			JOptionPane.showMessageDialog(getContentPane(), "Successfully forwarded!", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlInject);
			optionPaneWindow.dispose();
			
			txtDisplayArea.setText("");
		}
	}

//	private void fwdtoLMD() {
//		System.out.println("Forward to LMD");
//		if(JOptionPane.showConfirmDialog(getContentPane(), "Forward to LMD?", "Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {
//			
//			String proc_no = txtDisplayArea.getText();
//			 
//			String sql = "UPDATE rf_queuing_system SET\n" +
//					 	"fwd_to_dept = '52', status_id = 'A', processed_by = null\n" +
//					 	"where proc_no = (select right('"+proc_no+"',3)) and status_id = 'P' and dept_alias = (select left('"+proc_no+"',3))";
//			 
//			pgUpdate db = new pgUpdate();
//			db.executeUpdate(sql, false);
//			db.commit();
//			
//			JOptionPane.showMessageDialog(getContentPane(), "Successfully forwarded!", "Message", JOptionPane.INFORMATION_MESSAGE);
//			
//			Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlInject);
//			optionPaneWindow.dispose();
//			
//			txtDisplayArea.setText("");
//		}
//	}
	
	private void fwdtoLMD() {
		
		Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlInject);
		optionPaneWindow.dispose();
		
		JOptionPane.showOptionDialog(getContentPane(), pnlLMD, "Please select a process",
				JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
		
	}

	private void fwdtoCSD() {
		System.out.println("Forward to CSD");
		if(JOptionPane.showConfirmDialog(getContentPane(), "Forward to CSD?", "Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {
		
			String proc_no = txtDisplayArea.getText();
			 
			String sql = "UPDATE rf_queuing_system SET\n" +
					 	"fwd_to_dept = '02', status_id = 'A', processed_by = null, order_no = 1\n" +
					 	"where proc_no = (select right('"+proc_no+"',3)) and status_id = 'P' and dept_alias = (select left('"+proc_no+"',3))";
			 
			pgUpdate db = new pgUpdate();
			db.executeUpdate(sql, false);
			db.commit();
			
			insertAuditTrail("INJECT", proc_no, "PAYMENT");
			
			JOptionPane.showMessageDialog(getContentPane(), "Successfully forwarded!", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlInject);
			optionPaneWindow.dispose();
			
			txtDisplayArea.setText("");
		}	
	}

	private void fwdtoCCD() {
		System.out.println("Forward to CCD");
		if(JOptionPane.showConfirmDialog(getContentPane(), "Forward to CCD?", "Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {
			
			String proc_no = txtDisplayArea.getText();
			 
			String sql = "UPDATE rf_queuing_system SET\n" +
			"fwd_to_dept = '54', status_id = 'A', processed_by = null, order_no = 1\n" +
			"where proc_no = (select right('"+proc_no+"',3)) and status_id = 'P' and dept_alias = (select left('"+proc_no+"',3))";
			 
			pgUpdate db = new pgUpdate();
			db.executeUpdate(sql, false);
			db.commit();
			
			insertAuditTrail("INJECT", proc_no, "COLLECTION");
			
			JOptionPane.showMessageDialog(getContentPane(), "Successfully forwarded!", "Message", JOptionPane.INFORMATION_MESSAGE);
			
			Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlInject);
			optionPaneWindow.dispose();
			
			txtDisplayArea.setText("");
		}
	}

	private void injectQueueNo() {
		
		if(txtDisplayArea.getText().equals("")) {
			System.out.println("NO QUEUE NUMBER TO INJECT!");
		}else {
			
			String dept_code = getDepartment();
			
			if(dept_code.equals("02")) {//CSD
				btn1.setText("COLLECTIONS");
				btn2.setText("PROPERTY");
				btn3.setText("DOCUMENTS");
				btn1.setActionCommand("COLLECTIONS");
				btn2.setActionCommand("PROPERTY");
				btn3.setActionCommand("DOCUMENTS");
				
				JOptionPane.showOptionDialog(getContentPane(), pnlInject, "Inject",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
			}
			
			if(dept_code.equals("54")) {//CCD
				btn1.setText("PAYMENTS");
				btn2.setText("PROPERTY");
				btn3.setText("DOCUMENTS");
				btn1.setActionCommand("PAYMENTS");
				btn2.setActionCommand("PROPERTY");
				btn3.setActionCommand("DOCUMENTS");
				
				JOptionPane.showOptionDialog(getContentPane(), pnlInject, "Inject",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
			}
			
			if(dept_code.equals("08")) {//PMD
				btn1.setText("PAYMENTS");
				btn2.setText("COLLECTIONS");
				btn3.setText("DOCUMENTS");
				btn1.setActionCommand("PAYMENTS");
				btn2.setActionCommand("COLLECTIONS");
				btn3.setActionCommand("DOCUMENTS");
				
				JOptionPane.showOptionDialog(getContentPane(), pnlInject, "Inject",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
			}
			
			if(dept_code.equals("52")) {//LMRD
				btn1.setText("PAYMENTS");
				btn2.setText("COLLECTIONS");
				btn3.setText("PROPERTY");
				btn4.setText("DOCUMENTS");
				btn1.setActionCommand("PAYMENTS");
				btn2.setActionCommand("COLLECTIONS");
				btn3.setActionCommand("PROPERTY");
				btn4.setActionCommand("DOCUMENTS");
				
				JOptionPane.showOptionDialog(getContentPane(), pnlInject, "Inject",
						JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
			}
		}	
	}

	private void nextQueue() {
		String dept_code = getDepartment();
		//playSound(); 
		
		if(dept_code.equals("02")) {//******CSD
			String on_process = txtDisplayArea.getText();
			Integer order_no = 1;
			
			if(on_process.equals("null") || on_process.equals("")) {
				//JOptionPane.showMessageDialog(getContentPane(), "No active queue!", "Information", JOptionPane.INFORMATION_MESSAGE);
				//System.out.println("Walang laman");
				//System.out.printf("\non_process: %s\n", on_process);
				String active_qno = getActiveQueueNo(dept_code, order_no);
				if(active_qno.equals("") || active_qno.equals("null") || active_qno == null) {
					JOptionPane.showMessageDialog(getContentPane(), "There is no client to be processed.", "Message", JOptionPane.INFORMATION_MESSAGE);
				}else {
					txtDisplayArea.setText(active_qno);
					updateOngoingQueueNo(active_qno, dept_code);
					insertAuditTrail("NEXT", active_qno, "PAYMENT");
					//displayQueueNumbers(modelQueue);
				}
			}else {
				//System.out.println("May laman");
				//System.out.printf("\non_process: %s\n", on_process);
				updatePreviousQueueNo(on_process, dept_code);
				String active_qno = getActiveQueueNo(dept_code, order_no);
				txtDisplayArea.setText(active_qno);
				updateOngoingQueueNo(active_qno, dept_code);
				insertAuditTrail("NEXT", active_qno, "PAYMENT");
				//displayQueueNumbers(modelQueue);
			}
		}	
		
		if(dept_code.equals("54") || dept_code.equals("103")) {//*******CCD
			String on_process = txtDisplayArea.getText();
			Integer order_no = 1;
			
			if(on_process.equals("null") || on_process.equals("")) {
				//JOptionPane.showMessageDialog(getContentPane(), "No active queue!", "Information", JOptionPane.INFORMATION_MESSAGE);
				//System.out.println("Walang laman");
				//System.out.printf("\non_process: %s\n", on_process);
				String active_qno = getActiveQueueNo(dept_code, order_no);
				if(active_qno.equals("") || active_qno.equals("null") || active_qno == null) {
					JOptionPane.showMessageDialog(getContentPane(), "There is no client to be processed.", "Message", JOptionPane.INFORMATION_MESSAGE);
				}else {
					txtDisplayArea.setText(active_qno);
					updateOngoingQueueNo(active_qno, dept_code);
					insertAuditTrail("NEXT", active_qno, "COLLECTION");
					//displayQueueNumbers(modelQueue);
				}	
			}else {
				//System.out.println("May laman");
				//System.out.printf("\non_process: %s\n", on_process);
				updatePreviousQueueNo(on_process, dept_code);
				String active_qno = getActiveQueueNo(dept_code, order_no);
				txtDisplayArea.setText(active_qno);
				updateOngoingQueueNo(active_qno, dept_code);
				insertAuditTrail("NEXT", active_qno, "COLLECTION");
				//displayQueueNumbers(modelQueue);
			}
		}
		
		if(dept_code.equals("08")) {//******PMD
			String on_process = txtDisplayArea.getText();
			Integer order_no = 1;
			
			if(on_process.equals("null") || on_process.equals("")) {
				//JOptionPane.showMessageDialog(getContentPane(), "No active queue!", "Information", JOptionPane.INFORMATION_MESSAGE);
				//System.out.println("Walang laman");
				//System.out.printf("\non_process: %s\n", on_process);
				String active_qno = getActiveQueueNo(dept_code, order_no);
				if(active_qno.equals("") || active_qno.equals("null") || active_qno == null) {
					JOptionPane.showMessageDialog(getContentPane(), "There is no client to be processed.", "Message", JOptionPane.INFORMATION_MESSAGE);
				}else {
					txtDisplayArea.setText(active_qno);
					updateOngoingQueueNo(active_qno, dept_code);
					insertAuditTrail("NEXT", active_qno, "PROPERTY");
					//displayQueueNumbers(modelQueue);
				}
			}else {
				//System.out.println("May laman");
				//System.out.printf("\non_process: %s\n", on_process);
				updatePreviousQueueNo(on_process, dept_code);
				String active_qno = getActiveQueueNo(dept_code, order_no);
				txtDisplayArea.setText(active_qno);
				updateOngoingQueueNo(active_qno, dept_code);
				insertAuditTrail("NEXT", active_qno, "PROPERTY");
				//displayQueueNumbers(modelQueue);
			}
		}
		
		if(dept_code.equals("52")) {//******LMD
			String on_process = txtDisplayArea.getText();
			Integer order_no = null;
			String process = "";
			
			if(rad1.isSelected()) {
				order_no = 1;
				process = "FF";
			}
			
			if(rad2.isSelected()) {
				order_no = 2;
				process = "PF";
			}
			
			if(rad3.isSelected()) {
				order_no = 3;
				process = "REM";
			}
			
			if(rad4.isSelected()) {
				order_no = 4;
				process = "IHF";
			}
			
			if(on_process.equals("null") || on_process.equals("")) {
				//JOptionPane.showMessageDialog(getContentPane(), "No active queue!", "Information", JOptionPane.INFORMATION_MESSAGE);
				//System.out.println("Walang laman");
				//System.out.printf("\non_process: %s\n", on_process);
				
				/*TO SAVE THE PREVIOUS PROCESS PER USER*/
				if(ifUserIsActive(dept_code, order_no)) {
					/*NO ACTION*/
				}else {
					updateUser();
					insertUser(dept_code, order_no);
				}
				
				String active_qno = getActiveQueueNo(dept_code, order_no);
				if(active_qno.equals("") || active_qno.equals("null") || active_qno == null) {
					JOptionPane.showMessageDialog(getContentPane(), "There is no client to be processed.", "Message", JOptionPane.INFORMATION_MESSAGE);
				}else {
					txtDisplayArea.setText(active_qno);
					updateOngoingQueueNo(active_qno, dept_code);
					insertAuditTrail("NEXT", active_qno, process);
					//displayQueueNumbers(modelQueue);
				}
				//displayQueueNumbers(modelQueue);
			}else {
				//System.out.println("May laman");
				//System.out.printf("\non_process: %s\n", on_process);
				updatePreviousQueueNo(on_process, dept_code);
				String active_qno = getActiveQueueNo(dept_code, order_no);
				txtDisplayArea.setText(active_qno);
				updateOngoingQueueNo(active_qno, dept_code);
				insertAuditTrail("NEXT", active_qno, process);
				//displayQueueNumbers(modelQueue);
			}
		}
	}
	
	private void updateUser() {
		
		String sql = "UPDATE public.rf_queuing_user\n" + 
				"	SET status_id = 'I'\n" + 
				"	WHERE emp_code = '"+UserInfo.EmployeeCode+"' and status_id = 'A'";
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();

	}
	
	private void insertUser(String dept_code, Integer order_no) {
		
		String sql = "INSERT INTO public.rf_queuing_user(\n" + 
				"	dept_code, emp_code, order_no, status_id, date_created)\n" + 
				"	VALUES ('"+dept_code+"', '"+UserInfo.EmployeeCode+"', "+order_no+", 'A', now())";
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();

	}
	
	private Boolean ifUserIsActive(String dept_code, Integer order_no) {
		
		Boolean x = false;
		
		String sql = "select emp_code, order_no from rf_queuing_user where dept_code = '"+dept_code+"' and order_no = "+order_no+" and status_id = 'A' order by date_created DESC LIMIT 1";
		
		FncSystem.out("SQL for checking user privilege", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			x = true;
		}else {
			x = false;
		}
		
		return x;
	}
	
	private Integer getActiveOrderNo(String emp_code) {
		
		Integer y = 1;
		
		String sql = "select order_no from rf_queuing_user where emp_code = '"+emp_code+"' and status_id = 'A' order by date_created DESC LIMIT 1";
		
		FncSystem.out("SQL for getting user privilege", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			y = (Integer) db.getResult()[0][0];
		}else {
			y = 1;
		}
		
		return y;
	}
	
	private static String getActiveQueueNo(String dept_code, Integer order_no) {
		
		String proc_no = "";
		
		//String sql = "select CONCAT(dept_alias, proc_no) as \"proc_no\" from rf_queuing_system where dept_code = '"+dept_code+"' and status_id = 'A' order by date_created ASC limit 1";
//		String sql =
//				"select * from(\n" + 
//				"		(select \n" + 
//				"				CONCAT(dept_alias, proc_no) as \"proc_no\" \n" + 
//				"				from rf_queuing_system \n" + 
//				"				where fwd_to_dept = '"+dept_code+"' and status_id = 'A' and date_created::date = now()::date) \n" + 
//				"\n" + 
//				"				union all\n" + 
//				"\n" + 
//				"		(select \n" + 
//				"				CONCAT(dept_alias, proc_no) as \"proc_no\" \n" + 
//				"				from rf_queuing_system \n" + 
//				"				where dept_code = '"+dept_code+"' and status_id = 'A' and fwd_to_dept is null and date_created::date = now()::date\n" + 
//				"				order by date_created ASC) \n" + 
//				"		) a\n" + 
//				"limit 1";
		
		String sql =
				"select * from( \n" + 
				"				\n" + 
				"				(\n" + 
				"					select\n" + 
				"					CONCAT(dept_alias, proc_no) as \"proc_no\" \n" + 
				"					from rf_queuing_system\n" + 
				"					where fwd_to_dept = '"+dept_code+"' and status_id = 'A' and date_created::date = now()::date and order_no = "+order_no+"\n" + 
				"				)\n" + 
				"				\n" + 
				"				union all\n" + 
				"	\n" + 
				"				(\n" + 
				"					select\n" + 
				"					CONCAT(dept_alias, proc_no) as \"proc_no\" \n" + 
				"					from rf_queuing_system\n" + 
				"					where \"isPriority\" = true and dept_code = '"+dept_code+"' and status_id = 'A' and fwd_to_dept is null and date_created::date = now()::date and order_no = "+order_no+"\n" + 
				"				)\n" + 
				"\n" + 
				"				union all \n" + 
				"\n" + 
				"				(\n" + 
				"					select \n" + 
				"					CONCAT(dept_alias, proc_no) as \"proc_no\"\n" + 
				"					from rf_queuing_system\n" + 
				"					where \"isPriority\" = false and dept_code = '"+dept_code+"' and status_id = 'A' and fwd_to_dept is null and date_created::date = now()::date and order_no = "+order_no+"\n" + 
				"					order by date_created ASC\n" + 
				"				)\n" + 
				"			) a\n" + 
				"limit 1";
		
		FncSystem.out("SQL for getting next queue", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			proc_no = db.getResult()[0][0].toString();
		}
		
		return proc_no;
		
	}
	
	private void updateOngoingQueueNo(String proc_no, String dept_code) {
		
		String sql = "UPDATE rf_queuing_system SET\n"+
					"status_id = 'P', processed_by = '"+UserInfo.EmployeeCode+"'\n"+
					"where proc_no = (select right('"+proc_no+"',3)) and dept_alias = (select left('"+proc_no+"',3)) and status_id = 'A'";
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, true);
		db.commit();
	}
	
	private void updatePreviousQueueNo(String proc_no, String dept_code) {
		
		String sql = "UPDATE rf_queuing_system SET\n"+
					"status_id = 'I',\n"+
					"date_finished = now()\n"+
					"where proc_no = (select right('"+proc_no+"', 3)) and dept_alias = (select left('"+proc_no+"',3)) and status_id = 'P'";
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, true);
		db.commit();
	}
	
	private String getDepartment() {
		
		String dept_code = "";
		
		String sql = "select\n" + 
				"dept_code\n" + 
				"from em_employee\n" + 
				"where emp_code = '"+UserInfo.EmployeeCode+"'";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			dept_code = db.getResult()[0][0].toString();
		}else {
			dept_code = "";
		}
		
		return dept_code;
	}
	
	private void insertAuditTrail(String activity, String queue_no, String process) {
		
		String emp_code = UserInfo.EmployeeCode;
		
		String sql = "INSERT INTO public.rf_queuing_trail(\n" + 
				"	activity, processed_by, date_upd, status_id, remarks)\n" + 
				"	VALUES ('"+activity+"', '"+emp_code+"', now(), 'A', format('QNO:%s PROCESS:%s', '"+queue_no+"', '"+process+"'))";
		
		pgUpdate db = new pgUpdate();
		db.executeUpdate(sql, false);
		db.commit();
		
	}
	
    public void internalFrameClosing(InternalFrameEvent e) {
    	try{
    		String ifActive = txtDisplayArea.getText();
    		if(ifActive.equals("")) {
    			String dept_code = getDepartment();
    			String sql = "UPDATE rf_queuing_system SET status_id = 'I', date_finished = now() where dept_code = '"+dept_code+"' and status_id = 'P'";
            	
            	FncSystem.out("After closing", sql);
            	pgUpdate db = new pgUpdate();
            	db.executeUpdate(sql, false);
            	db.commit();
            	dispose();
    		}else {
    			if(JOptionPane.showConfirmDialog(getContentPane(), "You're currently processing a client! Are you sure you want to exit?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
    				
    				String dept_code = getDepartment();
        			String sql = "UPDATE rf_queuing_system SET status_id = 'I', date_finished = now() where dept_code = '"+dept_code+"' and status_id = 'P'";
        			String sql1 = "UPDATE rf_queuing_system SET status_id = 'I', date_finished = now() where fwd_to_dept = '"+dept_code+"' and status_id = 'P'";
                	
                	FncSystem.out("After closing", sql);
                	pgUpdate db = new pgUpdate();
                	db.executeUpdate(sql, false);
                	db.commit();
                	
                	pgUpdate dbExec = new pgUpdate();
                	dbExec.executeUpdate(sql1, false);
                	dbExec.commit();
                	dispose();
    			}
    		}
    	}catch (Exception x) {
    		x.printStackTrace();
		}	
    }
}
