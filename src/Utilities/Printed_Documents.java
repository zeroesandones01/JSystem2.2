package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Buyers.ClientServicing._CARD;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelPrintedDocs;

public class Printed_Documents extends _JInternalFrame implements _GUI, ActionListener {
	
	private static final long serialVersionUID = -6134737747730829955L;
	
	static String title = "Printed Documents";
	static Dimension SIZE = new Dimension(600, 500);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSeqNo;
	
	private JPanel pnlNorthCenter;
	private JPanel pnlClient;
	private _JLookup lookupClient;
	private _JXTextField txtClient;
	private JPanel pnlProject;
	private _JXTextField txtProjID;
	private _JXTextField txtProject;
	
	private JPanel pnlUnit;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDesc;
	
	private JPanel pnlSeqNo;
	private _JXTextField txtSeqNo;
	
	private JPanel pnlCenter;
	private JScrollPane scrollPrintedDocs;
	private _JTableMain tblPrintedDocs;
	private JList rowHeaderPrintedDocs;
	private modelPrintedDocs modelPrintedDocs;
	
	private JPanel pnlSouth;
	private JButton btnUntagDoc;
	
	public Printed_Documents(){
		super(title, true, true, false, true);
		initGUI();
	}
	
	public Printed_Documents(String title){
		super(title, true, true, true, true);
		initGUI();
	}
	
	public Printed_Documents(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		
		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Client Information"));
			{
				pnlNorthWest = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				{
					lblClient = new JLabel("Client");
					pnlNorthWest.add(lblClient);
				}
				{
					lblProject = new JLabel("Project");
					pnlNorthWest.add(lblProject);
				}
				{
					lblUnit = new JLabel("Unit");
					pnlNorthWest.add(lblUnit);
				}
				{
					lblSeqNo = new JLabel("Seq. No");
					pnlNorthWest.add(lblSeqNo);
				}
			}
			{
				pnlNorthCenter = new JPanel(new GridLayout(4, 1, 5, 5));
				pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
				{
					pnlClient = new JPanel(new BorderLayout(5, 5));
					pnlNorthCenter.add(pnlClient);
					{
						lookupClient = new _JLookup(null, "Client", 0);
						lookupClient.setLookupSQL(sqlClients());
						lookupClient.setFilterName(true);
						pnlClient.add(lookupClient, BorderLayout.WEST);
						lookupClient.setPreferredSize(new Dimension(100, 0));
						lookupClient.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();
								
								FncSystem.out("Display SQL For lookupClient", lookupClient.getLookupSQL());
								if(data != null){
									String entity_id = (String) data[0];
									String entity_name = (String) data[1];
									String proj_id = (String) data[6];
									String proj_name = (String) data[7];
									String pbl_id = (String) data[3];
									String unit_desc = (String) data[2];
									Integer seq_no = (Integer) data[4];
									
									txtClient.setText(entity_name);
									txtProjID.setText(proj_id);
									txtProject.setText(proj_name);
									txtUnitID.setText(pbl_id);
									txtUnitDesc.setText(unit_desc);
									txtSeqNo.setText(seq_no.toString());
									
									displayPrintedDocs(entity_id, proj_id, pbl_id, seq_no);
								}
							}
						});
					}
					{
						txtClient = new _JXTextField();
						pnlClient.add(txtClient, BorderLayout.CENTER);
					}
				}
				{
					pnlProject = new JPanel(new BorderLayout(5, 5));
					pnlNorthCenter.add(pnlProject);
					{
						txtProjID = new _JXTextField();
						pnlProject.add(txtProjID, BorderLayout.WEST);
						txtProjID.setPreferredSize(new Dimension(100, 0));
						txtProjID.setHorizontalAlignment(JXTextField.CENTER);
					}
					{
						txtProject = new _JXTextField();
						pnlProject.add(txtProject, BorderLayout.CENTER);
					}
				}
				{
					pnlUnit = new JPanel(new BorderLayout(5, 5));
					pnlNorthCenter.add(pnlUnit);
					{
						txtUnitID = new _JXTextField();
						pnlUnit.add(txtUnitID, BorderLayout.WEST);
						txtUnitID.setPreferredSize(new Dimension(100, 0));
						txtUnitID.setHorizontalAlignment(JXTextField.CENTER);
					}
					{
						txtUnitDesc = new _JXTextField();
						pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
						//txtUnitDesc.setPreferredSize(new Dimension(100, 0));
					}
				}
				{
					pnlSeqNo = new JPanel(new BorderLayout(5, 5));
					pnlNorthCenter.add(pnlSeqNo);
					{
						txtSeqNo = new _JXTextField();
						pnlSeqNo.add(txtSeqNo, BorderLayout.WEST);
						txtSeqNo.setPreferredSize(new Dimension(50, 0));
						txtSeqNo.setHorizontalAlignment(JXTextField.CENTER);
					}
				}
			}
		}
		{
			pnlCenter = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Printed Docs"));
			{
				scrollPrintedDocs = new JScrollPane();
				pnlCenter.add(scrollPrintedDocs, BorderLayout.CENTER);
				scrollPrintedDocs.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				{
					modelPrintedDocs = new modelPrintedDocs();
					tblPrintedDocs = new _JTableMain(modelPrintedDocs);
					scrollPrintedDocs.setViewportView(tblPrintedDocs);
					tblPrintedDocs.hideColumns("Rec. ID");
					
					modelPrintedDocs.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							
							if(e.getType() == TableModelEvent.INSERT){
								((DefaultListModel)rowHeaderPrintedDocs.getModel()).addElement(modelPrintedDocs.getRowCount());
							}

							if(modelPrintedDocs.getRowCount() == 0){
								rowHeaderPrintedDocs.setModel(new DefaultListModel());
							}
						}
					});
					/*tblPrintedDocs.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						
						@Override
						public void valueChanged(ListSelectionEvent e) {
							if(!e.getValueIsAdjusting()){
								
							}
						}
					});*/
				}
				{
					rowHeaderPrintedDocs = tblPrintedDocs.getRowHeader();
					rowHeaderPrintedDocs.setModel(new DefaultListModel());
					scrollPrintedDocs.setRowHeaderView(rowHeaderPrintedDocs);
					scrollPrintedDocs.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}
		{
			pnlSouth = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			{
				pnlSouth.add(Box.createHorizontalBox());
				/*pnlSouth.add(Box.createHorizontalBox());
				pnlSouth.add(Box.createHorizontalBox());*/
			}
			{
				btnUntagDoc = new JButton("Untag Document(s)");
				pnlSouth.add(btnUntagDoc);
				btnUntagDoc.setActionCommand("Untag Document");
				btnUntagDoc.addActionListener(this);
			}
		}
	}//XXX END OF INIT GUI
	
	//DISPLAY OF PRINTED DOCUMENTS
	private void displayPrintedDocs(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		modelPrintedDocs.clear();
		
		String SQL = "SELECT false, a.doc_id, b.doc_desc, a.date_printed ,initcap(get_employee_name(a.printed_by)), a.rec_id\n" + 
					 "from rf_printed_documents a\n" + 
					 "LEFT JOIN mf_system_doc b on b.doc_id = a.doc_id\n" + 
					 "WHERE a.entity_id = '"+entity_id+"' and a.proj_id = '"+proj_id+"' and a.pbl_id = '"+pbl_id+"' and a.seq_no = "+seq_no+" and a.status_id = 'A'\n" + 
					 "ORDER BY a.date_printed;";
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		FncSystem.out("Display Printed Docs", SQL);
		
		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				modelPrintedDocs.addRow(db.getResult()[x]);
			}
			scrollPrintedDocs.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPrintedDocs.getRowCount())));
			tblPrintedDocs.packAll();
		}
	}
	
	//UNTAGGING OF DOCUMENTS
	public void unTagDocuments(String entity_id, String proj_id, String pbl_id, Integer seq_no){
		
		pgUpdate db = new pgUpdate();
		
		for(int x = 0; x < modelPrintedDocs.getRowCount(); x++){
			Boolean selected = (Boolean) modelPrintedDocs.getValueAt(x, 0);
			String doc_id = (String) modelPrintedDocs.getValueAt(x, 1);
			Integer rec_id = (Integer) modelPrintedDocs.getValueAt(x, 5);
			
			System.out.printf("Display Value of Doc ID: %s%n", doc_id);
			if(selected){
//				String SQL = "DELETE FROM rf_printed_documents WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = "+seq_no+" AND doc_id = '"+doc_id+"' AND rec_id = "+rec_id+""; //MODIFIED BY MONIQUE DTD 07-11-2024
				String SQL = "UPDATE rf_printed_documents SET status_id = 'I' WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = "+seq_no+" AND doc_id = '"+doc_id+"' AND rec_id = "+rec_id+"";
				db.executeUpdate(SQL, true);
			}
		}
		db.commit();
	}
	
	public static String sqlClients() {
//		return "SELECT * FROM jsearch \n"; 
		//MODIFIED BY MONIQUE DTD 10-18-2024; TO REFLECT ITSREAL CLIENTS ON RETRIEVAL
		return "SELECT *, '' FROM jsearch \n"+
		"union all (select entity_id, upper(entity_name), '', '' , null, '', '015', 'TERRAVERDE RESIDENCES', '' from rf_entity where entity_id IN ('4270585379', '8785068676', '3252083086', '6731932436', '3746524839', '7704625972')) \n"+ //COMMENTED BY MONIQUE DTD 2023-07-14 --uncomment by lester 2023-07-31 for ahppy well payments
		"union all\n" + 
		"SELECT *, 'itsreal' as server FROM jsearch_itsreal ";
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Untag Document")){
			if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Untag Document(s)?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				unTagDocuments(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), Integer.valueOf(txtSeqNo.getText()));
				
				displayPrintedDocs(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), Integer.valueOf(txtSeqNo.getText()));
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Document(s) Untagged", actionCommand, JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
}