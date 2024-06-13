package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdesktop.swingx.JXPanel;

import com.toedter.calendar.JTextFieldDateEditor;

import tablemodel.modelPRC;
import tablemodel.model_RelOR;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import Lookup._JLookup;

public class OfficialReceiptReleasing extends _JInternalFrame implements
		ActionListener {

	private static final long serialVersionUID = -6504087838771992878L;
	static String title = "Releasing Of Official Receipt";
	Dimension frameSize = new Dimension(800, 500);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);

	private JButton btnMark;
	private JButton btnRefresh;
	
	private JTextField txtName;
	private JTextField txtOR;
	
	private JXPanel pnlTab;
	private JXPanel pnlDate;
	
	private JLabel lblDate;
	
	public static _JTableMain tblOR;
	private JScrollPane scrTab;
	public static JList rowOR;
	
	private model_RelOR modelOR;
	
	private static _JDateChooser dteRefDate;
	
	public OfficialReceiptReleasing() {
		super(title, true, true, false, false);
		InitGUI();
	}

	public OfficialReceiptReleasing(String title) {
		super(title);
		InitGUI();
	}

	public OfficialReceiptReleasing(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		InitGUI();
	}

	public void InitGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(frameSize);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panCenter, BorderLayout.CENTER);
			{
				JXPanel panList = new JXPanel(new BorderLayout(5, 5));
				panCenter.add(panList, BorderLayout.CENTER);
				panList.setBorder(JTBorderFactory.createTitleBorder("Official Receipt List", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					CreatePages();
					panList.add(pnlTab);
					DisplayOR(modelOR, rowOR, false);
				}
			}
			JXPanel panEnd = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panEnd, BorderLayout.PAGE_END);
			panEnd.setPreferredSize(new Dimension(0, 30));
			{
				/*
				JXPanel panParam = new JXPanel(new GridLayout(1, 2, 5, 5));
				panEnd.add(panParam, BorderLayout.LINE_START);
				panParam.setPreferredSize(new Dimension(500, 0));
				{
					JXPanel panOR = new JXPanel(new GridLayout(1, 1, 5, 5));
					panParam.add(panOR, BorderLayout.CENTER);
					panOR.setBorder(JTBorderFactory.createTitleBorder("Search Official Receipt", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						txtOR = new JTextField("");
						panOR.add(txtOR, BorderLayout.CENTER);
						txtOR.setHorizontalAlignment(JTextField.CENTER);
						txtOR.setEnabled(false);
					}
					JXPanel panName = new JXPanel(new GridLayout(1, 1, 5, 5));
					panParam.add(panName, BorderLayout.CENTER);
					panName.setBorder(JTBorderFactory.createTitleBorder("Search Client's Name", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						txtName = new JTextField("");
						panName.add(txtName, BorderLayout.CENTER);
						txtName.setHorizontalAlignment(JTextField.CENTER);
						txtName.setEnabled(false);
					}
				}
				*/
				JXPanel panButton = new JXPanel(new GridLayout(1, 2, 5, 5));
				panEnd.add(panButton, BorderLayout.CENTER);
				{
					btnMark = new JButton("Mark For Release");
					panButton.add(btnMark, BorderLayout.CENTER);
					btnMark.setActionCommand("Mark");
					btnMark.addActionListener(this);
				}
				{
					btnRefresh = new JButton("Refresh");
					panButton.add(btnRefresh, BorderLayout.CENTER);
					btnRefresh.setActionCommand("Refresh");	
					btnRefresh.addActionListener(this);
				}
			}
			
		}
		{
			pnlDate = new JXPanel(new GridLayout(2, 1, 5, 5));
			pnlDate.setLayout(null);
			pnlDate.setPreferredSize(new java.awt.Dimension(265, 80));
			{
				lblDate = new JLabel();
				pnlDate.add(lblDate);
				lblDate.setBounds(5, 15, 160, 20);
				lblDate.setText("Date Released:");
			}
			{
				dteRefDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
				pnlDate.add(dteRefDate);
				dteRefDate.setBounds(130, 15, 125, 21);
				dteRefDate.setDate(null);
				dteRefDate.setEnabled(true);
				dteRefDate.setDateFormatString("yyyy-MM-dd");
				((JTextFieldDateEditor) dteRefDate.getDateEditor()).setEditable(false);
				dteRefDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
				dteRefDate.addPropertyChangeListener( new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
					}
				});	
			}
			{
				JButton btnOK = new JButton();
				pnlDate.add(btnOK);
				btnOK.setBounds(95, 58, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
						optionPaneWindow.dispose();
					}
				});
			}
		}
	}
	
	private void CreatePages() {
		pnlTab = new JXPanel(new GridLayout(1, 1, 0, 0));
		pnlTab.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			pnlTab.add(scrTab, BorderLayout.CENTER);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelOR = new model_RelOR();
				modelOR.setEditable(false);
				tblOR = new _JTableMain(modelOR);
				
				rowOR = tblOR.getRowHeader();
				scrTab.setViewportView(tblOR);
				
				tblOR.getColumnModel().getColumn(0).setPreferredWidth(88);
				tblOR.getColumnModel().getColumn(1).setPreferredWidth(50);
				tblOR.getColumnModel().getColumn(2).setPreferredWidth(250);
				tblOR.getColumnModel().getColumn(3).setPreferredWidth(140);
				tblOR.getColumnModel().getColumn(4).setPreferredWidth(88);
				tblOR.getColumnModel().getColumn(5).setPreferredWidth(88);
				tblOR.getColumnModel().getColumn(6).setPreferredWidth(88);
				tblOR.getColumnModel().getColumn(7).setPreferredWidth(88);
				tblOR.getColumnModel().getColumn(8).setPreferredWidth(140);
				
				rowOR = tblOR.getRowHeader();
				rowOR.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowOR);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
			}
			
			tblOR.hideColumns("Mark");
		}	
	}
	
	private void DisplayOR(DefaultTableModel modelMain, JList rowHeader, Boolean blnRel) {
		String SQL = "";
		
		if (!blnRel) {
			SQL = "SELECT DISTINCT A.or_no, (case when coalesce(A.DateReleased::char(10), '') = '' then false else true end) as Mark, A.Name, \n" +
				 "A.description, A.PaymentType, A.particulars, SUM(A.amount) as Amount, A.or_date, A.doc_alias, \n" +
				 "A.pay_rec_id, A.entity_id, A.proj_id, A.pbl_id, A.seq_no, A.DateReleased FROM \n" +
				 "( \n" +
				 "SELECT (CASE WHEN a.or_doc_id IS NOT NULL THEN a.or_no ELSE NULL END) as or_no, \n" +
				 "(CASE WHEN a.pr_doc_id IS NOT NULL THEN COALESCE(a.ar_no, a.or_no) ELSE NULL END) as ar_no, \n" + 
				 "get_client_name(a.entity_id) as Name, \n" +
				 "CASE WHEN a.pymnt_type = 'A' THEN 'Cash' ELSE 'Check' END as PaymentType, c.particulars, a.amount, a.remarks, \n" + 
				 "a.trans_date, a.actual_date, d.doc_alias, a.pay_rec_id, a.entity_id, a.proj_id, \n" +
				 "a.pbl_id, b.description, a.seq_no, a.client_seqno, a.or_date::date, \n" +
				 "(case when strpos(a.remarks, 'Released to client on ') > 0 \n" +
				 "then left(right(a.remarks, length(a.remarks) - strpos(a.remarks, 'Released to client on ') - 21), 10)::date \n" +
				 "else null::date end) as DateReleased \n" +
				 "FROM rf_payments a \n" +
				 "LEFT JOIN mf_unit_info b ON b.proj_id = a.proj_id AND b.pbl_id = a.pbl_id \n" +
				 "LEFT JOIN mf_pay_particular c ON c.pay_part_id = a.pay_part_id \n" +
				 "LEFT JOIN mf_system_doc d ON d.doc_id = COALESCE(a.or_doc_id, a.pr_doc_id) \n" +
				 "WHERE a.client_seqno is not null and (coalesce(a.remarks, '') ~* 'Late LTS/BOI' or coalesce(a.remarks, '') ~* 'Late OR Issuance for Good Check') \n" +
				 ") A \n" +
				 "GROUP BY A.remarks, A.or_no, A.Name, A.PaymentType, A.particulars, A.or_date, A.doc_alias, \n" + 
				 "A.pay_rec_id, A.entity_id, A.proj_id, A.pbl_id, A.description, A.seq_no, A.DateReleased \n" +
				 "ORDER BY A.or_no";	
		} else {
			SQL = "SELECT DISTINCT A.or_no, (case when coalesce(A.DateReleased::char(10), '') = '' then false else true end) as Mark, A.Name, \n" +
				  "A.description, A.PaymentType, A.particulars, SUM(A.amount) as Amount, A.or_date, A.doc_alias, \n" +
				  "A.pay_rec_id, A.entity_id, A.proj_id, A.pbl_id, A.seq_no, A.DateReleased FROM \n" +
				  "( \n" +
				  "SELECT (CASE WHEN a.or_doc_id IS NOT NULL THEN a.or_no ELSE NULL END) as or_no, \n" +
				  "(CASE WHEN a.pr_doc_id IS NOT NULL THEN COALESCE(a.ar_no, a.or_no) ELSE NULL END) as ar_no, \n" + 
				  "get_client_name(a.entity_id) as Name, \n" +
				  "CASE WHEN a.pymnt_type = 'A' THEN 'Cash' ELSE 'Check' END as PaymentType, c.particulars, a.amount, a.remarks, \n" + 
				  "a.trans_date, a.actual_date, d.doc_alias, a.pay_rec_id, a.entity_id, a.proj_id, \n" +
				  "a.pbl_id, b.description, a.seq_no, a.client_seqno, a.or_date::date, \n" +
				  "(case when strpos(a.remarks, 'OR Released to client on ') > 0 \n" +
				  "then left(right(a.remarks, length(a.remarks) - strpos(a.remarks, 'OR Released to client on ') - 24), 10)::date \n" +
				  "else null::date end) as DateReleased \n" +
				  "FROM rf_payments a \n" +
				  "LEFT JOIN mf_unit_info b ON b.proj_id = a.proj_id AND b.pbl_id = a.pbl_id \n" +
				  "LEFT JOIN mf_pay_particular c ON c.pay_part_id = a.pay_part_id \n" +
				  "LEFT JOIN mf_system_doc d ON d.doc_id = COALESCE(a.or_doc_id, a.pr_doc_id) \n" +
				  "WHERE a.client_seqno is not null and (coalesce(a.remarks, '') ~* 'Late LTS/BOI' or coalesce(a.remarks, '') ~* 'Late OR Issuance for Good Check') \n" +
				  ") A \n" +
				  "WHERE COALESCE(A.DateReleased::char(10), '') = '' \n" +
				  "GROUP BY A.remarks, A.or_no, A.Name, A.PaymentType, A.particulars, A.or_date, A.doc_alias, \n" + 
				  "A.pay_rec_id, A.entity_id, A.proj_id, A.pbl_id, A.description, A.seq_no, A.DateReleased \n" +
				  "ORDER BY A.or_no";	
		}
		
		System.out.println("");
		System.out.println(SQL);
		
		try{
			FncTables.clearTable(modelMain);
		} catch(IndexOutOfBoundsException e){
			System.out.println("Mark 1");
		}
		
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);
		
		pgSelect dbOR = new pgSelect();
		dbOR.select(SQL);
		
		if (dbOR.isNotNull()){
			for (int x = 0; x < dbOR.getRowCount(); x++) {
				modelMain.addRow(dbOR.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {
			JOptionPane.showMessageDialog(null, "No records found.");
		}
	}
	
	public void actionPerformed(ActionEvent act) {
		if (act.getActionCommand().equals("Mark")) {
			MarkMode(true);
		} else if (act.getActionCommand().equals("Refresh")) {
			FncGlobal.startProgress("Retrieving OR list");
			MarkMode(false);
			FncGlobal.stopProgress();
			JOptionPane.showMessageDialog(getContentPane(), "OR list refreshed.");
		} else if (act.getActionCommand().equals("Save")) {
			FncGlobal.startProgress("Saving");
			Boolean blnWithKek = false;
			
			for(int w = 0; w < tblOR.getRowCount(); w++){
				if((Boolean)tblOR.getValueAt(w,1).equals(true)){
					blnWithKek = true;
				}
			}
			
			if (blnWithKek) {
				if (Save()) {
					MarkMode(false);
					JOptionPane.showMessageDialog(getContentPane(), "Official Receipts marked as released.");
				} else {
					MarkMode(false);
					JOptionPane.showMessageDialog(getContentPane(), "Saving was not successful.");
				}
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "No receipt was selected.");
				if (JOptionPane.showConfirmDialog(getContentPane(), "No receipt was selected. Cancel tagging?" , "Not Saved", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					MarkMode(false);
				}
			}
			FncGlobal.stopProgress();
		}
	}
	
	private Boolean Save() {
		String strDate = "";
		SimpleDateFormat sdfTo = new SimpleDateFormat("MM-dd-yyyy");
		
		int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Released Date", JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
		
		if (scanOption==JOptionPane.CLOSED_OPTION) {
			try {
				strDate = (String)sdfTo.format(dteRefDate.getDate());
			} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				
			}				
		}
		
		Boolean blnSaved = false;
		
		for (int x = 0; x < modelOR.getRowCount(); x++) {
			if ((Boolean) modelOR.getValueAt(x, 1).equals(true)) {
				System.out.println("");
				System.out.println("Name: " + modelOR.getValueAt(x, 2));
				System.out.println("OR: " + modelOR.getValueAt(x, 0));
				System.out.println("pay_rec_id: " + modelOR.getValueAt(x, 9));
				System.out.println("Date: " + strDate);
				
				pgUpdate db_Rev = new pgUpdate();
				String SQL_Rev = "update rf_payments \n" +
								 "set remarks = trim(remarks) || (case when right(trim(remarks), 1) = ';' then ' ' else '; ' end) || 'OR Released to client on ' || '" + strDate + "' || '; ' \n" +
								 "where entity_id = '"+modelOR.getValueAt(x, 10)+"' and or_no = '"+modelOR.getValueAt(x, 0)+"' \n" +
								 "and pay_rec_id::INTEGER = '"+modelOR.getValueAt(x, 9)+"'::INTEGER \n" + 
								 "and (remarks ~* 'Late LTS/BOI' or remarks ~* 'Late OR Issuance for Good Check')";
				
				System.out.println("");
				System.out.println(SQL_Rev);
				db_Rev.executeUpdate(SQL_Rev, false);
				db_Rev.commit();
					
				blnSaved = true;
			}
		}
		
		MarkMode(false);
		
		return blnSaved;
	}
	
	private void MarkMode(Boolean blnDo) {
		if (blnDo) {
			btnMark.setText("Save");
			btnMark.setActionCommand("Save");
			DisplayOR(modelOR, rowOR, true);
			tblOR.unhideColumns("Mark");
		} else {
			btnMark.setText("Mark For Release");
			btnMark.setActionCommand("Mark");
			DisplayOR(modelOR, rowOR, false);
			tblOR.hideColumns("Mark");
		}
	}
}
