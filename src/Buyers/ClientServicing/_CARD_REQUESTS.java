package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import components._JScrollPaneMain;
import components._JTabbedPane;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_tagging_of_csv;

public class _CARD_REQUESTS extends JXPanel implements _GUI {
	
	private static final long serialVersionUID = 1770006858199175887L;
	private CARD main_card;
	
	static Dimension size = new Dimension(738, 351);
	
	static Border line = BorderFactory.createLineBorder(Color.GRAY);
	
	private _JTabbedPane tabRequest;
	
	private JXPanel pnlOtherRequest;
	
	public _CARD_REQUESTS(CARD card) {
		this.main_card = card;
		initGUI(); 
	}

	public _CARD_REQUESTS(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public _CARD_REQUESTS(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public _CARD_REQUESTS(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static model_tagging_of_csv model_tagging_of_csv;
	private static JList rowOtherRequest;
	private static _JTableMain tableCSV;

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			this.add(panMain, BorderLayout.CENTER);
			panMain.setBorder(new EmptyBorder(0, 5, 5, 5));
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					tabRequest = new _JTabbedPane();
					panCenter.add(tabRequest, BorderLayout.CENTER);
					tabRequest.setBorder(new EmptyBorder(5, 5, 5, 5));
					if(UserInfo.EmployeeCode.equals("000645")){
						tabRequest.setFont(new Font("Tahoma", Font.BOLD, 7).deriveFont(1));
					}
					{
						fncCreateRequestTab();
						
						tabRequest.add("Other Requests", pnlOtherRequest);
						
					}
				}
			}
		}
	}
	

	private void fncCreateRequestTab() {
		pnlOtherRequest = new JXPanel(new BorderLayout(5, 5));
		pnlOtherRequest.setOpaque(isOpaque());
		{
			_JScrollPaneMain scroll = new _JScrollPaneMain();
			pnlOtherRequest.add(scroll, BorderLayout.CENTER);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setBorder(line);
			{
				model_tagging_of_csv = new model_tagging_of_csv("CARD");
				tableCSV = new _JTableMain(model_tagging_of_csv);
				scroll.setViewportView(tableCSV);
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				tableCSV.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableCSV.setSortable(false);
				{
					rowOtherRequest = tableCSV.getRowHeader();
					rowOtherRequest.setModel(new DefaultListModel());
					scroll.setRowHeaderView(rowOtherRequest);
					scroll.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}
	}
	
	public static void displayTaggingOfCsv(String entity_id, String proj_id, String pbl_id, Object seq_no) {
		System.out.println("Tinatawag nga ba??");
		FncTables.clearTable(model_tagging_of_csv);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowOtherRequest.setModel(listModel);
		String sql = 
				"select * from\n"
				+ "(select\n"
				+ "request_no as \"Requested No.\",\n"
				+ "date_appv::date as \"Trans Date\",\n"
				+ "request_date::date as \"Actual Date\",\n"
				+ "b.request_desc as \"Request Type\",\n"
				+ "remarks as \"Remarks\",\n"
				+ "get_employee_name(TRIM(request_by)) as \"Requested By\"\n"
				+ "from rf_csd_request_header a\n"
				+ "left join mf_csd_request_type b on a.request_type = b.request_code\n"
				+ "where status_id = 'A'\n"
				+ "and entity_id = '"+entity_id+"'\n"
				+ "and proj_id = '"+proj_id+"'\n"
				+ "and pbl_id = '"+pbl_id+"'\n"
				+ "and seq_no = "+seq_no+"\n"
				//+ "order by request_date desc\n"
				+ "union all \n"
				+ "select \n"
				+ "request_no as \"Requested No.\",\n"
				+ "date_appv::date as \"Trans Date\",\n"
				+ "request_date::date as \"Actual Date\",\n"
				+ "b.request_desc as \"Request Type\",\n"
				+ "remarks as \"Remarks\",\n"
				+ "get_employee_name(TRIM(request_by)) as \"Requested By\"\n"
				+ "from rf_csd_request_header_test a\n"
				+ "left join mf_csd_request_type b on a.request_type = b.request_code\n"
				+ "where status_id = 'A'\n"
				+ "and entity_id = '"+entity_id+"'\n"
				+ "and proj_id = '"+proj_id+"'\n"
				+ "and pbl_id = '"+pbl_id+"'\n"
				+ "and seq_no = "+seq_no+") a\n"
				+ "order by \"Requested No.\" desc\n";		
		pgSelect db = new pgSelect();
		db.select(sql);
		
		FncSystem.out("Display  ", sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				model_tagging_of_csv.addRow(db.getResult()[x]);
				listModel.addElement(model_tagging_of_csv.getRowCount());
			}	
		}		
		tableCSV.packAll();
	}
}
