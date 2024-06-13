package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Buyers.LoansManagement.TD_NOA_Tagging;
import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_OffRemAccounts;

public class PostPCopyTCTRPTReceipt  extends _JInternalFrame implements _GUI, ActionListener {

	static Dimension SIZE = new Dimension(550, 630);
	private JTextField txtProj;
	private JTextField txtPhase;
	private JDateChooser dateSent;
	private _JLookup lookupProj;
	private _JLookup lookupPhase;
	private JScrollPane scrollClients;
	private model_OffRemAccounts tbl_model_OffRem;
	private _JTableMain tblOffRemAccnts;
	private JList rowReadHeader;
	private JButton btnPost;
	private JButton btnCancel;
	private JButton btnGenerate;
	private Date actual_date;

	public PostPCopyTCTRPTReceipt() {
		super("Post Photocopy of TCT/RPT Receipt", false, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);

		JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
		this.add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			JPanel pnlGenerate = new JPanel(new BorderLayout(5, 5)); 
			pnlMain.add(pnlGenerate, BorderLayout.NORTH);
			pnlGenerate.setBorder(components.JTBorderFactory.createTitleBorder("Generate Clients"));
			pnlGenerate.setPreferredSize(new Dimension(0, 200));
			{
				JPanel pnlNorth = new JPanel(new GridLayout(3, 2, 5, 	 5));
				pnlNorth.setPreferredSize(new Dimension(200, 0));
				pnlGenerate.add(pnlNorth, BorderLayout.CENTER);

				{
					JLabel lblProj = new JLabel("Project:");
					pnlNorth.add(lblProj); 
					lblProj.setPreferredSize(new Dimension(0, 50));

					lookupProj = new _JLookup(null, "Project");
					pnlNorth.add(lookupProj);
					lookupProj.setLookupSQL(_JInternalFrame.SQL_PROJECT());
					lookupProj.setReturnColumn(0);
					lookupProj.addLookupListener(new LookupListener() {

						@Override
						public void lookupPerformed(LookupEvent event) {

							Object [] data = ((_JLookup)event.getSource()).getDataSet();
							if (data != null) {
								txtProj.setText(data[1].toString());
								lookupPhase.setLookupSQL(SQL_PHASE_ALL(data[0].toString()));
								btnGenerate.setEnabled(true);
								
							}
							else {
								txtProj.setText("");
							}

						}
					});

					JLabel lblPhase = new JLabel("Phase:");
					pnlNorth.add(lblPhase); 
					lblPhase.setPreferredSize(new Dimension(0, 50));

					lookupPhase = new _JLookup(null, "Phase", "Please select project.");
					pnlNorth.add(lookupPhase);
					lookupPhase.setReturnColumn(0);
					lookupPhase.addLookupListener(new LookupListener() {

						@Override
						public void lookupPerformed(LookupEvent event) {
							Object [] data  = ((_JLookup)event.getSource()).getDataSet();
							if (data != null) {
								txtPhase.setText(data[1].toString());
							}
							else {
								txtPhase.setText("");
							}
						}
					});

					JLabel lblDate = new JLabel("Date: "); 
					pnlNorth.add(lblDate);
					lblDate.setPreferredSize(new Dimension(0, 50));

					dateSent = new JDateChooser();
					pnlNorth.add(dateSent);
					dateSent.setDate(FncGlobal.getDateToday());	
					dateSent.setDateFormatString("MM/dd/yy");

				}

				{
					JPanel pnlComp = new JPanel(new GridLayout(3, 1, 5, 5)); 
					pnlGenerate.add(pnlComp, BorderLayout.LINE_END);
					pnlComp.setPreferredSize(new Dimension(200, 0));

					{
						txtProj = new JTextField();
						pnlComp.add(txtProj); 
						txtProj.setPreferredSize(new Dimension(100, 50));
						txtProj.setHorizontalAlignment(JTextField.CENTER);

						txtPhase = new JTextField(); 
						pnlComp.add(txtPhase);
						txtPhase.setPreferredSize(new Dimension(100, 50));
						txtPhase.setHorizontalAlignment(JTextField.CENTER);
						
						{
							
						}

					}
				}

				{
					JPanel pnlButton = new JPanel(new BorderLayout(5, 5)); 
					pnlGenerate.add(pnlButton, BorderLayout.SOUTH);
					pnlButton.setPreferredSize(new Dimension(0, 30));

					{
						btnGenerate = new JButton("GENERATE"); 
						pnlButton.add(btnGenerate); 
						btnGenerate.setPreferredSize(new Dimension(50, 50));
						btnGenerate.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								
								displayData(tbl_model_OffRem, rowReadHeader);
								
								System.out.println("proj_id:" + lookupProj.getValue());
								System.out.println("phase:" + lookupPhase.getValue());
								
							}
						});
					}
				}

			}

			JPanel pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER); 
			pnlTable.setBorder(new EmptyBorder(5, 5, 5, 5));

			{
				scrollClients = new JScrollPane(); 
				pnlTable.add(scrollClients, BorderLayout.CENTER);

				{
					tbl_model_OffRem = new model_OffRemAccounts();
					tblOffRemAccnts = new _JTableMain(tbl_model_OffRem);
					rowReadHeader = tblOffRemAccnts.getRowHeader();
					scrollClients.setViewportView(tblOffRemAccnts);
					tblOffRemAccnts.setSortable(true);
					
					tblOffRemAccnts.hideColumns("ID", "Project ID", "PBL ID", "Seq_No");
					tblOffRemAccnts.getColumnModel().getColumn(1).setPreferredWidth(165);
					tblOffRemAccnts.getColumnModel().getColumn(2).setPreferredWidth(50);
					tblOffRemAccnts.getColumnModel().getColumn(3).setPreferredWidth(120);
					tblOffRemAccnts.getColumnModel().getColumn(4).setPreferredWidth(110);
					
					tblOffRemAccnts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblOffRemAccnts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							if (tblOffRemAccnts.getSelectedRows().length == 1){
						}	

						}	
					});




				}

			}
			
			JPanel pnlSouth = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH); 
			pnlSouth.setPreferredSize(new Dimension(0, 30));
			
			{
				btnPost = new JButton("POST"); 
				pnlSouth.add(btnPost); 
				btnPost.addActionListener(this);
			
				btnCancel = new JButton("CANCEL"); 
				pnlSouth.add(btnCancel);
				btnCancel.addActionListener(this);
							
			}

		}
		
	
		btnGenerate.setEnabled(false);
	}

	private void displayData (DefaultTableModel model_OffRemAccounts, JList rowReadHeader) {
		FncTables.clearTable(tbl_model_OffRem);
		DefaultListModel list = new DefaultListModel (); 
		rowReadHeader.setModel(list);
		String SQL = "Select * from view_offrem_wo_tctrpt_receipt('"+lookupProj.getValue()+"','"+lookupPhase.getValue()+"')";
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x = 0;x<db.getRowCount();x++){
				tbl_model_OffRem.addRow(db.getResult()[x]);
				list.addElement(tbl_model_OffRem.getRowCount());
			}

		}
	}
	
	private void displayTagAccounts(String strBatch) {
		FncTables.clearTable(tbl_model_OffRem);
		DefaultListModel list = new DefaultListModel (); 
		rowReadHeader.setModel(list);
		String SQL = "Select a.entity_id, d.unit_id, get_client_name(a.entity_id) as Client, a.proj_id, b.proj_alias, c.description, a.pbl_id, a.seq_no, e.type_desc\n" + 
				"from rf_buyer_status a\n" + 
				"LEFT JOIN mf_project b on b.proj_id = a.proj_id\n" + 
				"LEFT JOIN mf_unit_info c on c.proj_id = a.proj_id and c.pbl_id = a.pbl_id \n" + 
				"LEFT JOIN rf_sold_unit d on d.entity_id = a.entity_id and d.projcode = a.proj_id and d.pbl_id = a.pbl_id and d.seq_no = a.seq_no \n" + 
				"LEFT JOIN mf_buyer_type e on e.type_id = d.buyertype\n" + 
				"where a.byrstatus_id = '140' and a.status_id = 'A' and a.trans_no = '"+strBatch+"';";
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x = 0;x<db.getRowCount();x++){
				tbl_model_OffRem.addRow(db.getResult()[x]);
				list.addElement(tbl_model_OffRem.getRowCount());
			}

		}
		
		btnGenerate.setEnabled(false);
		btnPost.setEnabled(false);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {//XXX actionCommand
		String action = e.getActionCommand();

		if(action.equals("CANCEL")){
			lookupProj.setValue("");
			txtProj.setText("");
			lookupPhase.setValue("");
			txtPhase.setText("");
			dateSent.setDate(FncGlobal.getDateToday());
			tbl_model_OffRem.clear();
		}
		if(action.equals("POST")){
			ArrayList<Boolean> hasSelected = new ArrayList<Boolean>();
			for (int x = 0; x < tbl_model_OffRem.getRowCount(); x++){
				Boolean selected = (Boolean) tbl_model_OffRem.getValueAt(x, 0);
				hasSelected.add(selected);
			}

			if(hasSelected.contains(true)){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					ArrayList<String> listEntityID = new ArrayList<String>();
					ArrayList<String> listProjID = new ArrayList<String>();
					ArrayList<String> listPblID = new ArrayList<String>();
					ArrayList<Integer> listSeqNo = new ArrayList<Integer>();
					


					for(int x=0; x < tbl_model_OffRem.getRowCount(); x++){
						Boolean selected = (Boolean) tbl_model_OffRem.getValueAt(x, 0);
						String entity_id = (String) tbl_model_OffRem.getValueAt(x, 1);
						//String entity_name = (String) tbl_model_OffRem.getValueAt(x, 2);
						String pbl_id = (String) tbl_model_OffRem.getValueAt(x, 6);
						Integer seq_no = (Integer) tbl_model_OffRem.getValueAt(x, 7);
						String proj_id = (String) tbl_model_OffRem.getValueAt(x, 3);
						
						hasSelected.add(selected);
						if(selected){
							listEntityID.add(String.format("'%s'", entity_id));
							listProjID.add(String.format("'%s'", proj_id));
							listPblID.add(String.format("'%s'", pbl_id));
							listSeqNo.add(seq_no);

						}
					}
						
					actual_date = dateSent.getDate();
					String strBatch = FncGlobal.GetString("select LPAD((coalesce(MAX(trans_no::int), 0) + 1)::TEXT, 6, '0'::TEXT) as batch_no from rf_buyer_status where byrstatus_id = '140'"); 
					
					//PUT DATE TAGGED HERE
					String SQL = "SELECT sp_tag_tct_rpt_receipt(\n" +
							"  ARRAY"+ listEntityID +"::VARCHAR[],\n" +
							"  ARRAY"+ listProjID +"::VARCHAR[],\n" +
							"  ARRAY"+ listPblID +"::VARCHAR[],\n" +
							"  ARRAY"+ listSeqNo +"::INTEGER[],\n" +
							"NULLIF('"+actual_date+"', 'null')::TIMESTAMP, \n"+
							"  '"+ UserInfo.Branch +"', '"+ UserInfo.EmployeeCode +"', '"+strBatch+"');";
					System.out.printf("%s%n", listEntityID.toString());	
					System.out.printf("%s%n", listProjID.toString());
					System.out.printf("%s%n", listPblID.toString());
					System.out.printf("%s%n%n", listSeqNo.toString());
					System.out.printf("%s%n%n", actual_date.toString());
					System.out.printf("%s%n%n", strBatch.toString());
					System.out.printf("%s%n", SQL);

					pgSelect db = new pgSelect();
					db.select(SQL, "Save", true);

					if(db.isNotNull() && (Boolean)db.getResult()[0][0]){
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Client(s) has been tagged.", action, JOptionPane.INFORMATION_MESSAGE);
						
						displayTagAccounts(strBatch);
					}
				}
			}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select client to tagged.", action, JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
