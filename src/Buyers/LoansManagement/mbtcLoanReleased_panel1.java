package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelMBTC;
import tablemodel.modelmbtcLoanReleased;

public class mbtcLoanReleased_panel1 extends JXPanel implements ActionListener, _GUI {

	private static final long serialVersionUID = -1201203401800092836L;
	private mbtcLoanReleased mbtcLoanReleasedMain;
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	
	private JScrollPane scrTab;
	private modelmbtcLoanReleased modelMBTC;
	public static _JTableMain tblMBTC;
	public static JList rowMBTC;
	
	private JXPanel panTab;

	private _JLookup txtBatch;

	private JButton btnGen;
	private JButton btnPost;
	private JButton btnPrevList;
	
	private _JDateChooser dteFrom;
	private _JDateChooser dteTo;
	
	public mbtcLoanReleased_panel1(mbtcLoanReleased mbtclr) {
		this.mbtcLoanReleasedMain = mbtclr;
		initGUI();
	}

	public mbtcLoanReleased_panel1(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public mbtcLoanReleased_panel1(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public mbtcLoanReleased_panel1(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}
	
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		this.add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panPage = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panPage, BorderLayout.PAGE_START);
				panPage.setPreferredSize(new Dimension(0, 65));
				{
					{
						JXPanel panBatch = new JXPanel(new BorderLayout(5, 5));
						panPage.add(panBatch, BorderLayout.CENTER);
						panBatch.setBorder(JTBorderFactory.createTitleBorder("Batch No.", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							txtBatch = new _JLookup("Select batch...");
							panBatch.add(txtBatch, BorderLayout.CENTER);
							txtBatch.setReturnColumn(0);
							txtBatch.setLookupSQL(_mbtcLoanReleased.sqlBatch());
							txtBatch.setHorizontalAlignment(JTextField.CENTER);
							txtBatch.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										String strFrom = "";
										String strTo = "";

										try {
											strFrom = dteFrom.getDate().toString();
										} catch (NullPointerException nullex) {
											strFrom = "";
										}
										
										try {
											strTo = dteTo.getDate().toString();
										} catch (NullPointerException nullex) {
											strTo = "";
										}
										
										_mbtcLoanReleased.mbtcDisplay(modelMBTC, rowMBTC, mbtcLoanReleasedMain.txtCoID.getText(), mbtcLoanReleasedMain.txtProjectID.getText(), txtBatch.getText(), strFrom, strTo);
										ButtonLock(2);
									}									
								}
							});
						}

					}
					{
						JXPanel panDate = new JXPanel(new GridLayout(1, 2, 5, 5));
						panPage.add(panDate, BorderLayout.LINE_END);
						panDate.setPreferredSize(new Dimension(500, 0));
						panDate.setBorder(JTBorderFactory.createTitleBorder("Post Approval Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							{
								JXPanel panDateDiv1 = new JXPanel(new BorderLayout(5, 5));
								panDate.add(panDateDiv1, BorderLayout.CENTER);
								{
									{
										JLabel lblDateFrom = new JLabel("Date From");
										panDateDiv1.add(lblDateFrom, BorderLayout.LINE_START);
										lblDateFrom.setHorizontalAlignment(JTextField.LEFT);
										lblDateFrom.setPreferredSize(new Dimension(80, 0));
									}
									{
										dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										panDateDiv1.add(dteFrom, BorderLayout.CENTER);
										dteFrom.getCalendarButton().setVisible(true);
									}	
								}
							}
							{
								JXPanel panDateDiv2 = new JXPanel(new BorderLayout(5, 5));
								panDate.add(panDateDiv2, BorderLayout.CENTER);
								{
									{
										JLabel lblDateTo = new JLabel("Date To");
										panDateDiv2.add(lblDateTo, BorderLayout.LINE_START);
										lblDateTo.setHorizontalAlignment(JTextField.CENTER);
										lblDateTo.setPreferredSize(new Dimension(80, 0));
									}
									{
										dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
										panDateDiv2.add(dteTo, BorderLayout.CENTER);
										dteTo.getCalendarButton().setVisible(true);
									}	
								}
							}
						}

					}
				}
			}
			{
				CreateTable();
				panMain.add(panTab, BorderLayout.CENTER);
			}
			{
				JXPanel panPageEnd = new JXPanel(new GridLayout(1, 6, 5, 5));
				panMain.add(panPageEnd, BorderLayout.PAGE_END);
				panPageEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnGen = new JButton("Generate");
						panPageEnd.add(btnGen, BorderLayout.CENTER);
						btnGen.setActionCommand("Generate");
						btnGen.addActionListener(this);
						btnGen.setEnabled(true);
					}
					{
						btnPost = new JButton("Create Batch");
						panPageEnd.add(btnPost);
						btnPost.addActionListener(this);
						btnPost.setActionCommand("Create Batch");
						btnPost.setEnabled(false);
					}
					{
						btnPrevList = new JButton("Export");
						panPageEnd.add(btnPrevList);
						btnPrevList.addActionListener(this);
						btnPrevList.setActionCommand("Export");
						btnPrevList.setEnabled(false);
					}
					
					ButtonLock(1);
				}
			}
		}
	}

	public void CreateTable(){
		panTab = new JXPanel(new GridLayout(1, 1, 0, 0));
		panTab.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			panTab.add(scrTab, BorderLayout.CENTER);
			scrTab.setBorder(lineGray);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelMBTC = new modelmbtcLoanReleased();
				modelMBTC.setEditable(false);
				tblMBTC = new _JTableMain(modelMBTC);
				
				rowMBTC = tblMBTC.getRowHeader();
				scrTab.setViewportView(tblMBTC);
				
				tblMBTC.getColumnModel().getColumn(0).setPreferredWidth(200);
				tblMBTC.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblMBTC.getColumnModel().getColumn(2).setPreferredWidth(65);
				tblMBTC.getColumnModel().getColumn(3).setPreferredWidth(120);
				tblMBTC.getColumnModel().getColumn(5).setPreferredWidth(112);
				tblMBTC.getColumnModel().getColumn(6).setPreferredWidth(112);
				
				rowMBTC = tblMBTC.getRowHeader();
				rowMBTC.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowMBTC);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblMBTC.hideColumns("Account #");
			}
		}
	}
	
	private void ButtonLock(Integer i) {
		Boolean blnAccess = false;
		
		if (UserInfo.Department.equals("54") || UserInfo.Department.equals("98")) {
			blnAccess = true;
		}
		
		if (i==1) {					/*		Initial State			*/
			btnPost.setEnabled(false);
			//btnPrevDoc.setEnabled(false);
			btnPrevList.setEnabled(false);
			//btnExp.setEnabled(false);
		} else if (i==2) {			/*		Batch is retrieved		*/
			btnPost.setEnabled(false);
			//btnPrevDoc.setEnabled(true);
			btnPrevList.setEnabled(true);
			//btnExp.setEnabled(true);			
		} else if (i==3) {			/* 		Batch is created		*/
			btnPost.setEnabled(false);
			//btnPrevDoc.setEnabled(true);
			btnPrevList.setEnabled(true);
			//btnExp.setEnabled(true);			
		} else if (i==4) {			/* 		Generate is pressed		*/
			btnPost.setEnabled(true);
			//btnPrevDoc.setEnabled(false);
			btnPrevList.setEnabled(false);
			//btnExp.setEnabled(false);			
		}
		
		if (!blnAccess) {
			//btnPrevDoc.setEnabled(false);
			//btnExp.setEnabled(false);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Set")) {

		} else if (e.getActionCommand().equals("Generate")) {
			FncGlobal.startProgress("Displaying list...");
			btnGen.setEnabled(false);
			System.out.println("txtCoID: " + mbtcLoanReleasedMain.txtCoID.getText());
			System.out.println("txtProjectID: " + mbtcLoanReleasedMain.txtProjectID.getText());
			
			if (mbtcLoanReleasedMain.txtCoID.getText().equals("") || mbtcLoanReleasedMain.txtProjectID.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "The company or project fields are not set.");
			} else {
				txtBatch.setEditable(true);
				
				try {
					if (_mbtcLoanReleased.mbtcDisplay(modelMBTC, rowMBTC, mbtcLoanReleasedMain.txtCoID.getText(), mbtcLoanReleasedMain.txtProjectID.getText(), "", dteFrom.getDate().toString(), dteTo.getDate().toString()).equals(true)) {
						ButtonLock(4);
					} else {
						ButtonLock(1);
					}
				} catch (NullPointerException nullex) {
					JOptionPane.showMessageDialog(getParent(), "The date parameter was not set.");
				}
			}
			btnGen.setEnabled(true);
			FncGlobal.stopProgress();
		} else if (e.getActionCommand().equals("Create Batch")) {
			Boolean blnMarked = false;
			txtBatch.setEditable(false);

			for (int i = 0; i < modelMBTC.getRowCount(); i++) {
				if ((Boolean) modelMBTC.getValueAt(i, 1).equals(true)) {
					blnMarked = true;
				}
			}

			if (JOptionPane.showConfirmDialog(null, "Proceed on creating a batch with these selected accounts?", "Create Batch", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
				if (!blnMarked) { 
					if (JOptionPane.showConfirmDialog(this, "Every row will be included in the batch since\nno row was selected. Proceed?", 
							"Creating Batch", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)==JOptionPane.YES_OPTION) { 
						txtBatch.setText(_mbtcLoanReleased.CreateBatch(modelMBTC, rowMBTC, 1)); 
						ButtonLock(3); 
					} 
				} else { 
					txtBatch.setText(_mbtcLoanReleased.CreateBatch(modelMBTC, rowMBTC, 0)); 
					ButtonLock(3); 
				} 
			}
		} else if (e.getActionCommand().equals("Export")) {
			_mbtcLoanReleased.CreateXLS(mbtcLoanReleasedMain.txtCoID.getText(), mbtcLoanReleasedMain.txtProjectID.getText(), txtBatch.getText()); 
		}
	}
}
