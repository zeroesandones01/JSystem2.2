package System;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import Functions.FncTables;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelQueuingSystem;

public class ListOfQueueNumbers extends _JInternalFrame implements _GUI, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JPanel pnlPaymentTable;
	private modelQueuingSystem modelQueue;
	private JPanel pnlCollectionTable;
	private JPanel pnlPropertyTable;
	private JPanel pnlFirstFilling;
	private JScrollPane scrollPayment;
	private modelQueuingSystem modelPayment;
	private _JTableMain tblPayment;
	private JScrollPane scrollCollection;
	private modelQueuingSystem modelCollection;
	private _JTableMain tblCollection;
	private JScrollPane scrollProperty;
	private modelQueuingSystem modelProperty;
	private _JTableMain tblProperty;
	private JScrollPane scrollFirstFilling;
	private modelQueuingSystem modelFirstFilling;
	private _JTableMain tblFirstFilling;
	private JPanel pnlSouth;
	private JPanel pnlDisplay;
	private JTextField txtDisplayArea;
	private Timer timerReload;
	private Color color_correct = new Color(0, 102, 204);
	private JPanel pnlDisplay1;
	private JTextArea txtDisplayArea1;
	private JPanel pnlDisplay2;
	private JTextArea txtDisplayArea2;
	private JPanel pnlDisplay3;
	private JTextArea txtDisplayArea3;
	private JTextArea txtDisplayArea4;
	private JPanel pnlDisplay4;
	private DisplayQueuingSystem displayQueue;
	private Color green = new Color(0,128,0);
	private JPanel pnlPostFilling;
	private JScrollPane scrollPostFilling;
	private modelQueuingSystem modelPostFilling;
	private _JTableMain tblPostFilling;
	private JPanel pnlREM;
	private JScrollPane scrollREM;
	private modelQueuingSystem modelREM;
	private _JTableMain tblREM;
	private JPanel pnlIHF;
	private JScrollPane scrollIHF;
	private modelQueuingSystem modelIHF;
	private _JTableMain tblIHF;
	private JPanel pnlDisplay5;
	private JTextArea txtDisplayArea5;
	private JPanel pnlDisplay6;
	private JTextArea txtDisplayArea6;
	private JPanel pnlDisplay7;
	private JTextArea txtDisplayArea7;
	private JPanel pnlNorth;
	
	public ListOfQueueNumbers() {
		// TODO Auto-generated constructor stub
		super("List of Queue Numbers", false, true, true, false);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(new Dimension(800,300));
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			{
				pnlNorth = new JPanel(new GridLayout(1,7,5,5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0,30));
				
				Font fontLabels = new Font("SanSerif Bold", Font.BOLD, 20);
				
				{
					JLabel lblPayments = new JLabel("PAYMENTS");
					pnlNorth.add(lblPayments);
					lblPayments.setFont(fontLabels);
					lblPayments.setHorizontalAlignment(JLabel.CENTER);
					lblPayments.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
					lblPayments.setForeground(Color.RED);
				}
				{
					JLabel lblCollections = new JLabel("COLLECTIONS");
					pnlNorth.add(lblCollections);
					lblCollections.setFont(fontLabels);
					lblCollections.setHorizontalAlignment(JLabel.CENTER);
					lblCollections.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
					lblCollections.setForeground(Color.RED);
				}
				{
					JLabel lblProperty = new JLabel("PROPERTY");
					pnlNorth.add(lblProperty);
					lblProperty.setFont(fontLabels);
					lblProperty.setHorizontalAlignment(JLabel.CENTER);
					lblProperty.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
					lblProperty.setForeground(Color.RED);
				}
				{
					JLabel lblFF = new JLabel("FIRST FILLING");
					pnlNorth.add(lblFF);
					lblFF.setFont(fontLabels);
					lblFF.setHorizontalAlignment(JLabel.CENTER);
					lblFF.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
					lblFF.setForeground(Color.RED);
					
				}
				{
					JLabel lblPF = new JLabel("POST FILLING");
					pnlNorth.add(lblPF);
					lblPF.setFont(fontLabels);
					lblPF.setHorizontalAlignment(JLabel.CENTER);
					lblPF.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
					lblPF.setForeground(Color.RED);
				}
				{
					JLabel lblREM = new JLabel("PAGIBIG REM");
					pnlNorth.add(lblREM);
					lblREM.setFont(fontLabels);
					lblREM.setHorizontalAlignment(JLabel.CENTER);
					lblREM.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
					lblREM.setForeground(Color.RED);
				}
				{
					JLabel lblIHF = new JLabel("IHF");
					pnlNorth.add(lblIHF);
					lblIHF.setFont(fontLabels);
					lblIHF.setHorizontalAlignment(JLabel.CENTER);
					lblIHF.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
					lblIHF.setForeground(Color.RED);
				}
			}
			{
				pnlCenter= new JPanel(new GridLayout(1,7,5,5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlPaymentTable = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlPaymentTable, BorderLayout.CENTER);
					//pnlPaymentTable.setBorder(components.JTBorderFactory.createTitleBorder("Payments"));
					pnlPaymentTable.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
					{
						scrollPayment = new JScrollPane();
						pnlPaymentTable.add(scrollPayment);
						scrollPayment.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					}
					{
						modelPayment = new modelQueuingSystem();
						tblPayment = new _JTableMain(modelPayment);

						scrollPayment.setViewportView(tblPayment);
						tblPayment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblPayment.getColumn(0).setPreferredWidth(154);
						tblPayment.setRowHeight(35);
						tblPayment.setEnabled(false);
						tblPayment.setFont(new Font("SanSerif", Font.PLAIN, 24));
					}
				}
				{
					//Border raisedbevel = BorderFactory.createRaisedBevelBorder();
					//Border loweredbevel = BorderFactory.createLoweredBevelBorder();
					//Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
					
					pnlCollectionTable = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlCollectionTable, BorderLayout.CENTER);
					//pnlCollectionTable.setBorder(components.JTBorderFactory.createTitleBorder("Collection"));
					pnlCollectionTable.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
					{
						scrollCollection = new JScrollPane();
						pnlCollectionTable.add(scrollCollection);
						scrollCollection.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					}
					{
						modelCollection = new modelQueuingSystem();
						tblCollection = new _JTableMain(modelCollection);

						scrollCollection.setViewportView(tblCollection);
						tblCollection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblCollection.getColumn(0).setPreferredWidth(154);
						tblCollection.setRowHeight(35);
						tblCollection.setEnabled(false);
						tblCollection.setFont(new Font("SanSerif", Font.PLAIN, 24));
					}
				}
				{
					pnlPropertyTable = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlPropertyTable, BorderLayout.CENTER);
					//pnlPropertyTable.setBorder(components.JTBorderFactory.createTitleBorder("Property"));
					pnlPropertyTable.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
					{
						scrollProperty = new JScrollPane();
						pnlPropertyTable.add(scrollProperty);
						scrollProperty.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					}
					{
						modelProperty = new modelQueuingSystem();
						tblProperty = new _JTableMain(modelProperty);

						scrollProperty.setViewportView(tblProperty);
						tblProperty.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblProperty.getColumn(0).setPreferredWidth(154);
						tblProperty.setRowHeight(35);
						tblProperty.setEnabled(false);
						tblProperty.setFont(new Font("SanSerif", Font.PLAIN, 24));
					}
				}
				{//First filling
					pnlFirstFilling = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlFirstFilling, BorderLayout.CENTER);
					//pnlFirstFilling.setBorder(components.JTBorderFactory.createTitleBorder("First Filling"));
					pnlFirstFilling.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
					{
						scrollFirstFilling = new JScrollPane();
						pnlFirstFilling.add(scrollFirstFilling);
						scrollFirstFilling.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					}
					{
						modelFirstFilling = new modelQueuingSystem();
						tblFirstFilling = new _JTableMain(modelFirstFilling);

						scrollFirstFilling.setViewportView(tblFirstFilling);
						tblFirstFilling.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblFirstFilling.getColumn(0).setPreferredWidth(154);
						tblFirstFilling.setRowHeight(35);
						tblFirstFilling.setEnabled(false);
						tblFirstFilling.setFont(new Font("SanSerif", Font.PLAIN, 24));
					}
				}
				{//Post Filling
					pnlPostFilling = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlPostFilling, BorderLayout.CENTER);
					//pnlPostFilling.setBorder(components.JTBorderFactory.createTitleBorder("Post Filling"));
					pnlPostFilling.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
					{
						scrollPostFilling = new JScrollPane();
						pnlPostFilling.add(scrollPostFilling);
						scrollPostFilling.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					}
					{
						modelPostFilling = new modelQueuingSystem();
						tblPostFilling = new _JTableMain(modelPostFilling);

						scrollPostFilling.setViewportView(tblPostFilling);
						tblPostFilling.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblPostFilling.getColumn(0).setPreferredWidth(154);
						tblPostFilling.setRowHeight(35);
						tblPostFilling.setEnabled(false);
						tblPostFilling.setFont(new Font("SanSerif", Font.PLAIN, 24));
					}
				}
				{//REM
					pnlREM = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlREM, BorderLayout.CENTER);
					//pnlREM.setBorder(components.JTBorderFactory.createTitleBorder("PAGIBIG REM"));
					pnlREM.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
					{
						scrollREM = new JScrollPane();
						pnlREM.add(scrollREM);
						scrollREM.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					}
					{
						modelREM = new modelQueuingSystem();
						tblREM = new _JTableMain(modelREM);

						scrollREM.setViewportView(tblREM);
						tblREM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblREM.getColumn(0).setPreferredWidth(154);
						tblREM.setRowHeight(35);
						tblREM.setEnabled(false);
						tblREM.setFont(new Font("SanSerif", Font.PLAIN, 24));
					}
				}
				{//IHF
					pnlIHF = new JPanel(new BorderLayout(5,5));
					pnlCenter.add(pnlIHF, BorderLayout.CENTER);
					//pnlIHF.setBorder(components.JTBorderFactory.createTitleBorder("In-House Financing"));
					pnlIHF.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
					{
						scrollIHF = new JScrollPane();
						pnlIHF.add(scrollIHF);
						scrollIHF.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					}
					{
						modelIHF = new modelQueuingSystem();
						tblIHF = new _JTableMain(modelIHF);

						scrollIHF.setViewportView(tblIHF);
						tblIHF.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblIHF.getColumn(0).setPreferredWidth(154);
						tblIHF.setRowHeight(35);
						tblIHF.setEnabled(false);
						tblIHF.setFont(new Font("SanSerif", Font.PLAIN, 24));
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1,7,5,5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0,170));		
				
				Font fontTitle = new Font("SansSerif Bold", Font.BOLD, 12);
				String strTitle = "N O W  S E R V I N G!";
				Border linecolor = BorderFactory.createLineBorder(Color.BLACK);
				//TitledBorder title = BorderFactory.createTitledBorder(linecolor, "N O W  S E R V I N G!!!!!!!");
				TitledBorder title = BorderFactory.createTitledBorder(linecolor, strTitle, 0, 0, fontTitle, Color.BLACK);
				//title.setTitleColor(green);
				
				Font font = new Font("SansSerif", Font.PLAIN, 40);
				
				{
					pnlDisplay1 = new JPanel(new BorderLayout(5,5));
					pnlSouth.add(pnlDisplay1, BorderLayout.CENTER);
					pnlDisplay1.setBorder(title);
					title.setTitleJustification(TitledBorder.CENTER);
					pnlDisplay1.setPreferredSize(new Dimension(0,150));
					{
						txtDisplayArea1 = new JTextArea("");
						pnlDisplay1.add(txtDisplayArea1);
						txtDisplayArea1.setFont(font);
						//txtDisplayArea1.setForeground(color_correct);
						txtDisplayArea1.setForeground(green);
						txtDisplayArea1.setOpaque(false);
						txtDisplayArea1.setEditable(false);
						//txtDisplayArea1.setHorizontalAlignment(SwingConstants.CENTER);
						txtDisplayArea1.setWrapStyleWord(true);
						txtDisplayArea1.setBorder(BorderFactory.createLineBorder(Color.gray));
//						txtDisplayArea1.getDocument().addDocumentListener(new DocumentListener() {
//							
//							@Override
//							public void removeUpdate(DocumentEvent e) {
//								// TODO Auto-generated method stub
//								System.out.print("Remove");
//								//playSound();
//							}
//							
//							@Override
//							public void insertUpdate(DocumentEvent e) {
//								// TODO Auto-generated method stub
//								System.out.print("Insert");
//								//playSound();
//								
//							}
//							
//							@Override
//							public void changedUpdate(DocumentEvent e) {
//								// TODO Auto-generated method stub
//								System.out.print("change");
//								//playSound();
//							}
//						});
					}
				}
				{
					pnlDisplay2 = new JPanel(new BorderLayout(5,5));
					pnlSouth.add(pnlDisplay2, BorderLayout.CENTER);
					pnlDisplay2.setBorder(title);
					title.setTitleJustification(TitledBorder.CENTER);
					pnlDisplay2.setPreferredSize(new Dimension(0,150));
					{
						txtDisplayArea2 = new JTextArea("");
						pnlDisplay2.add(txtDisplayArea2);
						txtDisplayArea2.setFont(font);
						//txtDisplayArea2.setForeground(color_correct);
						txtDisplayArea2.setForeground(green);
						txtDisplayArea2.setOpaque(false);
						txtDisplayArea2.setEditable(false);
						//txtDisplayArea2.setHorizontalAlignment(SwingConstants.CENTER);
						txtDisplayArea2.setWrapStyleWord(true);
						txtDisplayArea2.setBorder(BorderFactory.createLineBorder(Color.gray));
					}
				}
				{
					pnlDisplay3 = new JPanel(new BorderLayout(5,5));
					pnlSouth.add(pnlDisplay3, BorderLayout.CENTER);
					pnlDisplay3.setBorder(title);
					title.setTitleJustification(TitledBorder.CENTER);
					pnlDisplay3.setPreferredSize(new Dimension(0,150));
					{
						txtDisplayArea3 = new JTextArea("");
						pnlDisplay3.add(txtDisplayArea3);
						txtDisplayArea3.setFont(font);
						//txtDisplayArea3.setForeground(color_correct);
						txtDisplayArea3.setForeground(green);
						txtDisplayArea3.setOpaque(false);
						txtDisplayArea3.setEditable(false);
						//txtDisplayArea3.setHorizontalAlignment(SwingConstants.CENTER);
						txtDisplayArea3.setWrapStyleWord(true);
						txtDisplayArea3.setBorder(BorderFactory.createLineBorder(Color.gray));
					}
				}
				{
					pnlDisplay4 = new JPanel(new BorderLayout(5,5));
					pnlSouth.add(pnlDisplay4, BorderLayout.CENTER);
					pnlDisplay4.setBorder(title);
					title.setTitleJustification(TitledBorder.CENTER);
					pnlDisplay4.setPreferredSize(new Dimension(0,150));
					{
						txtDisplayArea4 = new JTextArea("");
						pnlDisplay4.add(txtDisplayArea4);
						txtDisplayArea4.setFont(font);
						//txtDisplayArea4.setForeground(color_correct);
						txtDisplayArea4.setForeground(green);
						txtDisplayArea4.setOpaque(false);
						txtDisplayArea4.setEditable(false);
						//txtDisplayArea4.setHorizontalAlignment(SwingConstants.CENTER);
						txtDisplayArea4.setWrapStyleWord(true);
						txtDisplayArea4.setBorder(BorderFactory.createLineBorder(Color.gray));
					}
				}
				{
					pnlDisplay5 = new JPanel(new BorderLayout(5,5));
					pnlSouth.add(pnlDisplay5, BorderLayout.CENTER);
					pnlDisplay5.setBorder(title);
					title.setTitleJustification(TitledBorder.CENTER);
					pnlDisplay5.setPreferredSize(new Dimension(0,150));
					{
						txtDisplayArea5 = new JTextArea("");
						pnlDisplay5.add(txtDisplayArea5);
						txtDisplayArea5.setFont(font);
						//txtDisplayArea5.setForeground(color_correct);
						txtDisplayArea5.setForeground(green);
						txtDisplayArea5.setOpaque(false);
						txtDisplayArea5.setEditable(false);
						//txtDisplayArea5.setHorizontalAlignment(SwingConstants.CENTER);
						txtDisplayArea5.setWrapStyleWord(true);
						txtDisplayArea5.setBorder(BorderFactory.createLineBorder(Color.gray));
					}
				}
				{
					pnlDisplay6 = new JPanel(new BorderLayout(5,5));
					pnlSouth.add(pnlDisplay6, BorderLayout.CENTER);
					pnlDisplay6.setBorder(title);
					title.setTitleJustification(TitledBorder.CENTER);
					pnlDisplay6.setPreferredSize(new Dimension(0,150));
					{
						txtDisplayArea6 = new JTextArea("");
						pnlDisplay6.add(txtDisplayArea6);
						txtDisplayArea6.setFont(font);
						txtDisplayArea6.setForeground(color_correct);
						//txtDisplayArea6.setForeground(green);
						txtDisplayArea6.setOpaque(false);
						txtDisplayArea6.setEditable(false);
						//txtDisplayArea6.setHorizontalAlignment(SwingConstants.CENTER);
						txtDisplayArea6.setWrapStyleWord(true);
						txtDisplayArea6.setBorder(BorderFactory.createLineBorder(Color.gray));
					}
				}
				{
					pnlDisplay7 = new JPanel(new BorderLayout(5,5));
					pnlSouth.add(pnlDisplay7, BorderLayout.CENTER);
					pnlDisplay7.setBorder(title);
					title.setTitleJustification(TitledBorder.CENTER);
					pnlDisplay7.setPreferredSize(new Dimension(0,150));
					{
						txtDisplayArea7 = new JTextArea("");
						pnlDisplay7.add(txtDisplayArea7);
						txtDisplayArea7.setFont(font);
						//txtDisplayArea7.setForeground(color_correct);
						txtDisplayArea7.setForeground(green);
						txtDisplayArea7.setOpaque(false);
						txtDisplayArea7.setEditable(false);
						//txtDisplayArea4.setHorizontalAlignment(SwingConstants.CENTER);
						txtDisplayArea7.setWrapStyleWord(true);
						txtDisplayArea7.setBorder(BorderFactory.createLineBorder(Color.gray));
					}
				}
			}
		}
		
		initialize_comp();
		Reload();
	}
	
	private void initialize_comp() {
		
		displayQueueNumbers(modelPayment, "02", 1);
		displayQueueNumbers(modelCollection, "54", 1);
		displayQueueNumbers(modelProperty, "08", 1);
		displayQueueNumbers(modelFirstFilling, "52", 1);
		displayQueueNumbers(modelPostFilling, "52", 2);
		displayQueueNumbers(modelREM, "52", 3);
		displayQueueNumbers(modelIHF, "52", 4);
	}

	private void displayQueueNumbers(DefaultTableModel model, String dept_code, Integer order_no) {
		FncTables.clearTable(model);
		//DefaultListModel listModel = new DefaultListModel();
		//rowHeader.setModel(listModel);
		
		//String sql = "select CONCAT(dept_alias, proc_no) as \"proc_no\" from rf_queuing_system where dept_code = '"+dept_code+"' and status_id = 'A' order by date_created ASC";
		String sql =
				"select \n" + 
				"CONCAT(dept_alias, proc_no) as \"proc_no\"\n" + 
				"from rf_queuing_system\n" + 
				"where fwd_to_dept = '"+dept_code+"' \n" + 
				"and status_id = 'A'\n" + 
				"and date_created::date = now()::date\n" + 
				"and order_no = "+order_no+"\n" + 
				"\n" + 
				"union all\n" + 
				"\n" +
				"(select \n" + 
				"CONCAT(dept_alias, proc_no) as \"proc_no\"\n" + 
				"from rf_queuing_system\n" + 
				"where dept_code = '"+dept_code+"'\n" +
				"and status_id = 'A' \n" + 
				"and fwd_to_dept is null\n" + 
				"and date_created::date = now()::date\n" + 
				"and order_no = "+order_no+"\n" + 
				"order by date_created ASC)";
		
		//FncSystem.out("List of queue", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				model.addRow(db.getResult()[x]);
				//listModel.addElement(model.getRowCount());
			}
		}
	}
	
	private void getOngoingProcess(String dept_code, Integer order_no, JTextArea txtfield) {
		
//		String sql = "select\n" + 
//				"concat(dept_alias, proc_no) as \"proc_no\"\n" + 
//				"from rf_queuing_system  \n" + 
//				"where dept_code = '"+dept_code+"' and status_id = 'P' and date_finished is null";
		
		String sql =
				"select\n" + 
				"string_agg(a.\"proc_no\", '\n')::varchar from (\n" + 
				"			select\n" + 
				"			concat(dept_alias, proc_no) as \"proc_no\"\n" + 
				"			from rf_queuing_system \n" + 
				"			where status_id = 'P' \n" + 
				"			and date_finished is null \n" + 
				"			and\n" + 
				"					case  \n" + 
				"						when fwd_to_dept is null then dept_code = '"+dept_code+"' \n" + 
				"						else fwd_to_dept = '"+dept_code+"' \n" + 
				"					end \n" + 
				"			and date_created::date = now()::date\n" +
				"			and order_no = "+order_no+"\n" +
				"			order by date_created ASC\n" + 
				"		) a";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		
//		try {
//			if(txtfield.getText().equals(db.getResult()[0][0].toString()) == false) {
//				playSound();
//			}
//		}catch (NullPointerException e) {
//			// XXX: handle exception
//		}
	
		if(db.isNotNull()) {
			try {
				txtfield.setText(db.getResult()[0][0].toString());
			}catch (Exception e) {
				// TODO: handle exception
				txtfield.setText("");
			}
		}else {
			txtfield.setText("");
		}
	}
	
	private void Reload() {
		TimerTask tmr = new TimerTask() {

			public void run() {
				displayQueueNumbers(modelPayment, "02", 1);
				displayQueueNumbers(modelCollection, "54", 1);
				displayQueueNumbers(modelProperty, "08", 1);
				displayQueueNumbers(modelFirstFilling, "52", 1);
				displayQueueNumbers(modelPostFilling, "52", 2);
				displayQueueNumbers(modelREM, "52", 3);
				displayQueueNumbers(modelIHF, "52", 4);
				
				getOngoingProcess("02", 1, txtDisplayArea1);
				getOngoingProcess("54", 1, txtDisplayArea2);
				getOngoingProcess("08", 1, txtDisplayArea3);
				getOngoingProcess("52", 1, txtDisplayArea4);
				getOngoingProcess("52", 2, txtDisplayArea5);
				getOngoingProcess("52", 3, txtDisplayArea6);
				getOngoingProcess("52", 4, txtDisplayArea7);
				
			}
		};

		timerReload = new Timer();
		long delay = 0;
		long intevalPeriod = 1000; 
	
		timerReload.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}
	
	private void playSound() 
    {
      java.applet.AudioClip clip = java.applet.Applet.newAudioClip(FncLookAndFeel.class.getClassLoader().getResource("Images/chime.wav"));
	clip.play();
    }

}
