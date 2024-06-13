package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.output.ThresholdingOutputStream;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Functions.FncTables;
import Renderer.DateRenderer;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_rem_conversion;

public class tab_remConversion extends JXPanel implements _GUI {

	private static final long serialVersionUID = 1588509297357617871L;
	private hdmfInfo_maintab main; 
	
	private JScrollPane scrollREM;
	public static JList rowREM;
	private _JTableMain tblREM;
	private static model_rem_conversion modelREM;
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	public tab_remConversion(hdmfInfo_maintab hdmf_tab) {
		this.main = hdmf_tab; 
		initGUI(); 
	}

	public tab_remConversion(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public tab_remConversion(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public tab_remConversion(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		JXPanel panRemConversion = new JXPanel(new BorderLayout(5, 5));
		this.add(panRemConversion, BorderLayout.CENTER);
		panRemConversion.setOpaque(true);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5)); 
			panRemConversion.add(panMain, BorderLayout.CENTER); 
			{
				{
					JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
					panMain.add(panCenter, BorderLayout.LINE_START); 
					panCenter.setPreferredSize(new Dimension(784, 0));
					{
						scrollREM = new JScrollPane();
						panCenter.add(scrollREM, BorderLayout.CENTER);
						scrollREM.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
						scrollREM.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						{
							{
								modelREM = new model_rem_conversion();
								tblREM = new _JTableMain(modelREM);
								
								rowREM = tblREM.getRowHeader();
								scrollREM.setViewportView(tblREM);
								
								tblREM.getColumnModel().getColumn(0).setPreferredWidth(250);
								tblREM.getColumnModel().getColumn(1).setPreferredWidth(100);
								tblREM.getColumnModel().getColumn(2).setPreferredWidth(250);
								tblREM.getColumnModel().getColumn(3).setPreferredWidth(125);
								
								tblREM.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
								
								tblREM.hideColumns("Status Sequence", "entity_id", "proj_id", "pbl_id", "seq_no");
							}
							{
								rowREM = tblREM.getRowHeader();
								rowREM.setModel(new DefaultListModel());
								scrollREM.setRowHeaderView(rowREM);
								scrollREM.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,FncTables.getRowHeader_Header());
							}	
						}
					}
				}
			}
		}
	}
	
	public static void DisplayHDMFREM(String strGetEnt, String strGetProj, String strGetUnit, String strGetSeq) {
		FncTables.clearTable(modelREM);
		DefaultListModel listModel = new DefaultListModel();
		rowREM.setModel(listModel); 
		
		pgSelect db = new pgSelect();
		db.select("select * \n" +
				"from view_card_rem_conversion('"+strGetEnt+"', '"+strGetProj+"', '"+strGetUnit+"', '"+strGetSeq+"'::int)");
		
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelREM.addRow(db.getResult()[x]);
				listModel.addElement(modelREM.getRowCount());
			}
		}
	}
}
