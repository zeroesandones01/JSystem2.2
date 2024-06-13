package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import tablemodel.modelReceiptPrinting_GoodCheck;
import Functions.FncAcounting;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class ReceiptPrinting_GoodCheck extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6941612072290729931L;

	static String title = "Receipt Printing (OR Issued for Good Check)";
	Dimension frameSize = new Dimension(800, 500);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JScrollPane scrollReceipts;
	private _JTableMain tblReceipts;
	private modelReceiptPrinting_GoodCheck modelReceipts;
	private JList rowheaderCheckHistory;

	public ReceiptPrinting_GoodCheck() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ReceiptPrinting_GoodCheck(String title) {
		super(title);
		initGUI();
	}

	public ReceiptPrinting_GoodCheck(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new Dimension(frameSize));
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JPanel(new BorderLayout(3, 3));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				scrollReceipts = new JScrollPane();
				pnlMain.add(scrollReceipts, BorderLayout.CENTER);
				scrollReceipts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollReceipts.setBorder(JTBorderFactory.createTitleBorder("Check History", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					modelReceipts = new modelReceiptPrinting_GoodCheck();
					modelReceipts.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							if(e.getType() == TableModelEvent.DELETE){
								rowheaderCheckHistory.setModel(new DefaultListModel());
							}
							if(e.getType() == TableModelEvent.INSERT){
								((DefaultListModel)rowheaderCheckHistory.getModel()).addElement(modelReceipts.getRowCount());
								//scrollCheckHistory.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(modelReceipts.getRowCount())));
							}
						}
					});

					tblReceipts = new _JTableMain(modelReceipts);
					scrollReceipts.setViewportView(tblReceipts);
					tblReceipts.hideColumns("Client ID", "PBL ID", "Actual Date");
				}
				{
					rowheaderCheckHistory = tblReceipts.getRowHeader();
					rowheaderCheckHistory.setModel(new DefaultListModel());
					scrollReceipts.setRowHeaderView(rowheaderCheckHistory);
					scrollReceipts.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				//pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					JButton btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
				{
					JButton btnRefresh = new JButton("Refresh");
					pnlSouth.add(btnRefresh);
					btnRefresh.setMnemonic(KeyEvent.VK_R);
					btnRefresh.addActionListener(this);
				}
			}
		}
		displayReceipts(modelReceipts, rowheaderCheckHistory);
	}

	private void displayReceipts(modelReceiptPrinting_GoodCheck model, JList rowHeader) {
		FncTables.clearTable(model);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		
		String SQL = "select * from view_lateor_receipt_printing_check";
		System.out.println("");
		System.out.println("SQL: " + SQL);
		
		pgSelect db = new pgSelect();
		db.select(SQL);

		for(int x=0; x < db.getRowCount(); x++){
			model.addRow(db.getResult()[x]);
		}
		
		scrollReceipts.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblReceipts.getRowCount())));
		tblReceipts.packAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {//XXX actionCommand
		String action = e.getActionCommand();

		if(action.equals("Preview")&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "7")==true){			
									
			if (tblReceipts.getSelectedRow()==-1)
			{
				JOptionPane.showMessageDialog(getContentPane(),"Please select row to preview.","Information",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				int row = tblReceipts.convertRowIndexToModel(tblReceipts.getSelectedRow());				
				String or_no = (String) modelReceipts.getValueAt(row, 3);
				Date or_date = (Date) modelReceipts.getValueAt(row, 4);
				
				if(or_no != null){

					Map<String, Object> mapParameters = new HashMap<String, Object>();
					mapParameters.put("or_date", or_date);
					mapParameters.put("or_no", or_no);
					mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
					FncReport.generateReport("/Reports/rptORReceipt_merged.jasper", "Official Receipt", String.format("OR No.: %s", or_no), mapParameters);
				}
			}
		}
		else if(e.getActionCommand().equals("Preview")&& FncAcounting.EmpCanPreview(UserInfo.EmployeeCode, "7")==false)
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to print receipt.","Warning",JOptionPane.WARNING_MESSAGE); }
	
		
		if(action.equals("Refresh")){
			displayReceipts(modelReceipts, rowheaderCheckHistory);
			JOptionPane.showMessageDialog(getContentPane(),"Table refreshed.","Information",JOptionPane.INFORMATION_MESSAGE); 
		}
	}

}
