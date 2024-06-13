package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Renderer.DateRenderer;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_checks_pdc_warehousing;

public class PDCWarehousing_BRSTN extends JXPanel implements _GUI {

	private static final long serialVersionUID = 3824810583510931048L;
	
	private static Font font = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private static Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private static Font font10Bold = FncLookAndFeel.systemFont_Bold.deriveFont(10f);

	private static JScrollPane scrTab;
	private static model_checks_pdc_warehousing modelWith;	
	public static _JTableMain tblWith;
	public static JList rowWith;
	
	private static JXPanel panTabList;
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static checkStatusMonitoring_pdcWarehousing pdcw; 
	
	private static JButton btnSave; 
	
	public PDCWarehousing_BRSTN(checkStatusMonitoring_pdcWarehousing main_pdcw) {
		this.pdcw = main_pdcw; 
		initGUI(); 
	}

	public PDCWarehousing_BRSTN(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public PDCWarehousing_BRSTN(LayoutManager layout) {
		super(layout);

	}

	public PDCWarehousing_BRSTN(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));

		JXPanel panLine = new JXPanel(new BorderLayout(5, 5)); 
		this.add(panLine, BorderLayout.CENTER);
		{
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5)); 
				panLine.add(panCenter, BorderLayout.CENTER); 
				{
					CreateTable();
					panCenter.add(panTabList, BorderLayout.CENTER);
				}
			}
			{
				JXPanel panEnd = new JXPanel(new BorderLayout(5, 5)); 
				panLine.add(panEnd, BorderLayout.PAGE_END);
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					btnSave = new JButton("Save"); 
					panEnd.add(btnSave, BorderLayout.LINE_END);
					btnSave.setFont(font11);
					btnSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (JOptionPane.showConfirmDialog(null, "Save BRSTN values?", 
									"Saving", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
								Execute();
								JOptionPane.showMessageDialog(null, "BRSTN saved!");
							}
 
							LoadList(); 
						}
					});
					btnSave.setPreferredSize(new Dimension(120, 0));
				}
			}
		}
	}

	public void CreateTable() {
		panTabList = new JXPanel(new GridLayout(1, 1, 0, 0));
		panTabList.setOpaque(isOpaque());
		{
			scrTab = new JScrollPane();
			panTabList.add(scrTab, BorderLayout.CENTER);
			scrTab.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelWith = new model_checks_pdc_warehousing();
				modelWith.setEditable(false);
				
				tblWith = new _JTableMain(modelWith);
				tblWith.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {

						}
					}
				});

				rowWith = tblWith.getRowHeader();
				scrTab.setViewportView(tblWith);
				
				tblWith.getColumnModel().getColumn(1).setPreferredWidth(215);
				tblWith.getColumnModel().getColumn(2).setPreferredWidth(120);
				tblWith.getColumnModel().getColumn(3).setPreferredWidth(95);
				tblWith.getColumnModel().getColumn(4).setPreferredWidth(90);
				tblWith.getColumnModel().getColumn(5).setPreferredWidth(95);
				tblWith.getColumnModel().getColumn(9).setPreferredWidth(95);
				tblWith.getColumnModel().getColumn(11).setPreferredWidth(100);
				tblWith.getColumnModel().getColumn(9).setCellRenderer(new DateRenderer(sdf));
				
				rowWith = tblWith.getRowHeader();
				rowWith.setModel(new DefaultListModel());
				scrTab.setRowHeaderView(rowWith);
				scrTab.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				
				tblWith.hideColumns("Tag", "Bank", "Branch", "Date", "Account No", "Amount", "ref_no", "pay_rec_id");
			}
		}
	}
	
    public static void LoadList() {
		FncGlobal.startProgress("Refreshing...");

		FncTables.clearTable(modelWith); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowWith.setModel(listModel); 

		String strSQL = "select false as tag, * \n" +
				"from view_check_for_pdc_warehousing('"+checkStatusMonitoring_pdcWarehousing.txtCoID.getValue()+"', \n" +
				"'"+checkStatusMonitoring_pdcWarehousing.txtProID.getValue()+"', '', '04', '')";

		pgSelect db = new pgSelect();
		db.select(strSQL);

		if (db.isNotNull()) {
			for(int x=0; x < db.getRowCount(); x++){
				modelWith.addRow(db.getResult()[x]);
				listModel.addElement(modelWith.getRowCount());
			}			
		}

		FncGlobal.stopProgress();
    }
    
    private static void Execute() {
    	FncGlobal.startProgress("Saving.. Please wait..");
    	
    	pgUpdate dbExec = new pgUpdate(); 
    	
    	for (int x=0; x < modelWith.getRowCount(); x++) {
    		dbExec.executeUpdate("update rf_payments \n" + 
    				"set brstn = '"+modelWith.getValueAt(x, 11)+"' \n" + 
    				"where pay_rec_id::int = '"+modelWith.getValueAt(x, 12)+"'::int", false);
    	}
    	
    	dbExec.commit();
    	
    	FncGlobal.stopProgress();
    }
}
