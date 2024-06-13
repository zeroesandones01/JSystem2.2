package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Functions.FncTables;
import components._JScrollPaneMain;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_proforma_payments;

public class proForma_payments extends JXPanel implements _GUI {

	private static final long serialVersionUID = 1274261686139403216L;
	private static ProForma_Entries pf_main; 
	static Dimension size = new Dimension(1100, 600);
	
	static _JScrollPaneMain scrollPF_entries;
	static model_proforma_payments modelPF_main;
	static _JTableMain tblPF_main;
	static JList rowHeader_PFmain;
	
	public proForma_payments(ProForma_Entries proForma_Entries) {
		this.pf_main = proForma_Entries; 
		initGUI(); 
	}

	public proForma_payments(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public proForma_payments(LayoutManager layout) {
		super(layout);

	}

	public proForma_payments(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	public void initGUI() {
		this.setSize(size);
		this.setLayout(new BorderLayout(5, 5));
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5)); 
		this.add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			scrollPF_entries = new _JScrollPaneMain();
			panMain.add(scrollPF_entries, BorderLayout.CENTER);
			{
				modelPF_main = new model_proforma_payments();
				tblPF_main = new _JTableMain(modelPF_main);
				scrollPF_entries.setViewportView(tblPF_main);
				tblPF_main.setSortable(false);
				
				tblPF_main.getColumnModel().getColumn(0).setPreferredWidth(55);
				tblPF_main.getColumnModel().getColumn(1).setPreferredWidth(173);
				tblPF_main.getColumnModel().getColumn(2).setPreferredWidth(75);

				tblPF_main.getColumnModel().getColumn(3).setPreferredWidth(90);
				tblPF_main.getColumnModel().getColumn(4).setPreferredWidth(90);
				tblPF_main.getColumnModel().getColumn(5).setPreferredWidth(90);
				tblPF_main.getColumnModel().getColumn(6).setPreferredWidth(90);
				tblPF_main.getColumnModel().getColumn(7).setPreferredWidth(90);
				tblPF_main.getColumnModel().getColumn(8).setPreferredWidth(90);
				tblPF_main.getColumnModel().getColumn(9).setPreferredWidth(90);
				tblPF_main.getColumnModel().getColumn(10).setPreferredWidth(90);
				tblPF_main.getColumnModel().getColumn(11).setPreferredWidth(90);
				
				tblPF_main.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if(!e.getValueIsAdjusting()){
							try {
								Integer row = tblPF_main.convertRowIndexToModel(tblPF_main.getSelectedRow());
								String strPF = (String) modelPF_main.getValueAt(row, 0).toString();
								
								ProForma_Entries.display_entries(ProForma_Entries.modelProForma, ProForma_Entries.rowProForma, strPF);
							} catch (ArrayIndexOutOfBoundsException e1) {
								
							} catch (IndexOutOfBoundsException e2) {
								
							}
						}
					}
				});
			}
			{
				rowHeader_PFmain = tblPF_main.getRowHeader();
				scrollPF_entries.setRowHeaderView(rowHeader_PFmain);
				scrollPF_entries.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}
	
	static void display_proforma_categories() {
		FncTables.clearTable(modelPF_main); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader_PFmain.setModel(listModel); 

		String sql = "select a.pf_no, a.description, b.particulars, c.doc_alias, \n" + 
				"(case when a.cash_check = 'A' then 'CASH' ELSE 'CHECK' END) as cash_check, \n" + 
				"a.dated_pdc, (case when a.tr_or = '17' then 'TR' ELSE 'OR' END) as tr_or, \n" + 
				"a.lts, a.boi, a.quarter_pctg, a.realized, a.implemented\n" + 
				"from mf_proforma_entries_header a\n" + 
				"inner join mf_pay_particular b on a.particular = b.pay_part_id\n" + 
				"inner join mf_system_doc c on a.or_ar = c.doc_id\n" + 
				"order by a.pf_no";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelPF_main.addRow(db.getResult()[x]);
				listModel.addElement(modelPF_main.getRowCount());
			}			
		}
	}
}
