package Dialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXTextArea;

import Database.pgSelect;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Renderer.ButtonCell;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelDM_AllDocuments;
import tablemodel.modelDM_RRDocuments;

public class dlgDM_RRDocuments extends JDialog implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7777996443874310680L;

	private Dimension size = new Dimension(500, 500);


	private JScrollPane scrollDocuments;

	private _JTableMain tblDocuments;
	private modelDM_RRDocuments modelDocuments;
	private modelDM_AllDocuments modelAllDocs;
	private JList rowHeaderDocuments;

	private _JXTextField txtReceivedBy;
	private _JXTextField txtReceivedFrom;
	//private _JDateChooser dateReceived;

	private JButton btnSave;
	private JButton btnCancel;

	private JScrollPane scrollRemarks;
	private JXTextArea txtRemarks;

	String entity_id;
	String proj_id;
	String pbl_id;
	Integer seq_no;
	String coapplicant_id;
	String unit_id;
	Boolean isRelease;
	Integer selected_tab;

	public dlgDM_RRDocuments() {
		initGUI();
	}

	public dlgDM_RRDocuments(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_RRDocuments(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_RRDocuments(Window owner) {
		super(owner);
		initGUI();
	}

	public dlgDM_RRDocuments(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_RRDocuments(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_RRDocuments(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlgDM_RRDocuments(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlgDM_RRDocuments(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlgDM_RRDocuments(Window owner, String title, String entity_id, String proj_id, String pbl_id, Integer seq_no, String coapplicant_id ,String unit_id, Boolean isRelease, Integer selected_index) {
		super(owner, title);

		this.entity_id = entity_id;
		this.proj_id = proj_id;
		this.pbl_id = pbl_id;
		this.seq_no = seq_no;
		this.coapplicant_id = coapplicant_id;
		this.unit_id = unit_id;
		this.isRelease = isRelease;
		this.selected_tab = selected_index;
		
		initGUI();
		
		System.out.printf("Display coapplicant ID: (%s)", coapplicant_id);
		
		if(isRelease){
			if(coapplicant_id == null){
				if(selected_tab == 0){
					displayDocumentsToRelease(entity_id, proj_id, pbl_id, seq_no);
				}
			}else{
				if(selected_tab == 1){
					displayCoAppDocsToRelease(entity_id, proj_id, pbl_id, seq_no, coapplicant_id);
				}
			}
		}else{
			if(coapplicant_id == null){
				if(selected_tab == 0){
					displayDocumentsToReturn(entity_id, proj_id, pbl_id, seq_no);
				}
			}else{
				if(selected_tab == 1){
					displayCoAppDocsToReturn(entity_id, proj_id, pbl_id, seq_no, coapplicant_id);
				}
			}
		}
	}

	public dlgDM_RRDocuments(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public dlgDM_RRDocuments(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlgDM_RRDocuments(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlgDM_RRDocuments(Frame arg0, String arg1, boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public dlgDM_RRDocuments(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlgDM_RRDocuments(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getRootPane().registerKeyboardAction(this, "Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			JPanel pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					scrollDocuments = new JScrollPane();
					pnlCenter.add(scrollDocuments, BorderLayout.CENTER);
					scrollDocuments.setBorder(components.JTBorderFactory.createTitleBorder("Documents"));
					scrollDocuments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollDocuments.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblDocuments.clearSelection();
						}
					});
					{
						modelDocuments = new modelDM_RRDocuments();
						modelDocuments.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								//Addition of rows
								if(e.getType() == 1){
									((DefaultListModel)rowHeaderDocuments.getModel()).addElement(modelDocuments.getRowCount());

									if(modelDocuments.getRowCount() == 0){
										rowHeaderDocuments.setModel(new DefaultListModel());
									}
								}
							}
						});
						
						/*modelAllDocs = new modelDM_AllDocuments();
						modelAllDocs.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								//Addition of rows
								if(e.getType() == 1){
									((DefaultListModel)rowHeaderDocuments.getModel()).addElement(modelAllDocs.getRowCount());

									if(modelAllDocs.getRowCount() == 0){
										rowHeaderDocuments.setModel(new DefaultListModel());
									}
								}
							}
						});*/

						tblDocuments = new _JTableMain(modelDocuments);
						scrollDocuments.setViewportView(tblDocuments);
						tblDocuments.hideColumns("ID", "Doc Alias", "Required", "Mandatory");
						
						tblDocuments.setDefaultRenderer(JButton.class, new ButtonCell(null, 8));
						tblDocuments.setDefaultEditor(JButton.class, new ButtonCell(null, 8));
					}
					{
						rowHeaderDocuments = tblDocuments.getRowHeader();
						rowHeaderDocuments.setModel(new DefaultListModel());
						scrollDocuments.setRowHeaderView(rowHeaderDocuments);
						scrollDocuments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						scrollDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocuments.getRowCount())));
					}
				}
				{
					JPanel pnlSouth = new JPanel(new BorderLayout(5, 5));
					pnlCenter.add(pnlSouth, BorderLayout.SOUTH);
					{
						JPanel pnlNorth = new JPanel(new GridLayout(2, 1, 10, 5));
						pnlSouth.add(pnlNorth, BorderLayout.NORTH);
						pnlNorth.setPreferredSize(new Dimension(0, 50));
						{
							JPanel pnlReceived = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnlReceived);
							{
								JLabel lblReceivedBy = new JLabel("Received by");
								pnlReceived.add(lblReceivedBy, BorderLayout.WEST);
							}
							{
								txtReceivedBy = new _JXTextField("Received by");
								pnlReceived.add(txtReceivedBy, BorderLayout.CENTER);
								txtReceivedBy.setEditable(true);
							}
							
						}
						{
							JPanel pnlReceivedFrom = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnlReceivedFrom);
							{
								JLabel lblReceivedFrom = new JLabel("Received From");
								pnlReceivedFrom.add(lblReceivedFrom, BorderLayout.WEST);
							}
							{
								txtReceivedFrom = new _JXTextField();
								pnlReceivedFrom.add(txtReceivedFrom);
								txtReceivedFrom.setEditable(true);
							}
						}
						/*{
							JPanel pnlDateReceived = new JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnlDateReceived);
							{
								JLabel lblReceivedBy = new JLabel("Date Received");
								pnlDateReceived.add(lblReceivedBy, BorderLayout.WEST);
							}
							{
								dateReceived = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlDateReceived.add(dateReceived, BorderLayout.CENTER);
							}
						}*/
					}
					{
						scrollRemarks = new JScrollPane();
						pnlSouth.add(scrollRemarks, BorderLayout.CENTER);
						scrollRemarks.setBorder(components.JTBorderFactory.createTitleBorder("Remarks"));
						scrollRemarks.setPreferredSize(new Dimension(0, 100));
						{
							txtRemarks = new JXTextArea("Please enter remarks here...");
							scrollRemarks.setViewportView(txtRemarks);
							txtRemarks.setLineWrap(true);
							txtRemarks.setWrapStyleWord(true);
						}
					}
				}
			}
			{
				JPanel pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					JPanel pnlNavigation = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setAlignmentX(0.5f);
					pnlNavigation.setAlignmentY(0.5f);
					pnlNavigation.setMaximumSize(new Dimension(205, 30));
					{
						btnSave = new JButton("Save");
						pnlNavigation.add(btnSave);
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlNavigation.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			}
		}
	}

	private void displayDocumentsToRelease(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		modelDocuments.clear();

		String SQL = "SELECT false as select, a.no_copies, a.doc_id, b.doc_desc\n" + 
				"FROM rf_buyer_documents a\n" + 
				//"LEFT JOIN mf_doc_details b ON b.doc_id = a.doc_id\n" + 
				"LEFT JOIN mf_documents b ON b.doc_id = a.doc_id\n" +
				//"LEFT JOIN dm_docs_inout c ON c.entity_id = a.entity_id and c.proj_id = a.projcode and c.pbl_id = a.pbl_id and c.seq_no = a.seq_no and c.doc_id = a.doc_id and c.inout_code = false\n" + 
				"WHERE a.entity_id = '"+ entity_id +"'\n" + 
				"AND a.projcode = '"+ proj_id +"'\n" + 
				"AND a.pbl_id = '"+ pbl_id +"'\n" + 
				"AND a.seq_no = "+ seq_no +"\n" +
				"AND a.status_id = 'A' \n"+
				"AND (CASE WHEN (a.docs_in IS NULL AND a.docs_OUT IS NULL) THEN TRUE ELSE (timestamp_gt(a.docs_in, a.docs_out) OR a.docs_out IS NULL) END)\n" +
				"ORDER BY TRIM(b.doc_desc)/*, c.received_date*/;";

		FncSystem.out("Documents for Release", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				modelDocuments.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
			scrollDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocuments.getRowCount())));
			tblDocuments.packAll();
		}
	}
	
	private void displayCoAppDocsToRelease(String entity_id, String proj_id, String pbl_id, Integer seq_no, String coapplicant_id){
		modelDocuments.clear();
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT false as select, a.no_copies, a.doc_id, b.doc_desc\n" + 
					 "FROM dm_coapplicant_documents a\n" + 
					 //"LEFT JOIN mf_doc_details b ON b.doc_id = a.doc_id\n" + 
					 "LEFT JOIN mf_documents b ON b.doc_id = a.doc_id\n" +
					 //"LEFT JOIN dm_docs_inout c ON c.entity_id = a.coapplicant_id and c.proj_id = a.projcode and c.pbl_id = a.pbl_id and c.seq_no = a.seq_no and c.doc_id = a.doc_id\n" + 
					 "WHERE a.entity_id = '"+entity_id+"'\n" + 
					 "AND a.projcode = '"+proj_id+"'\n" + 
					 "AND a.pbl_id = '"+pbl_id+"'\n" + 
					 "AND a.seq_no = "+seq_no+" \n" + 
					 "AND a.coapplicant_id = '"+coapplicant_id+"'\n" + 
					 "AND (CASE WHEN (a.docs_in IS NULL AND a.docs_OUT IS NULL) THEN TRUE ELSE (timestamp_gt(a.docs_in, a.docs_out) OR a.docs_out IS NULL) END)\n" + 
					 "ORDER BY TRIM(b.doc_desc)/*, c.received_date*/;";
		db.select(SQL);
		
		FncSystem.out("Coapplicant Documents for Release", SQL);
		
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				modelDocuments.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
			scrollDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocuments.getRowCount())));
			tblDocuments.packAll();
		}
	}

	private void displayDocumentsToReturn(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		modelDocuments.clear();

		/*String SQL = "SELECT false as select, a.no_copies, a.doc_id, b.doc_desc\n" + 
				"FROM rf_buyer_documents a\n" + 
				//"LEFT JOIN mf_doc_details b ON b.doc_id = a.doc_id\n" + //Change this with mf documents
				"LEFT JOIN mf_documents b ON b.doc_id = a.doc_id\n" +
				"LEFT JOIN dm_docs_inout c ON c.entity_id = a.entity_id and c.proj_id = a.projcode and c.pbl_id = a.pbl_id and c.seq_no = a.seq_no and c.doc_id = a.doc_id and c.inout_code\n" + 
				"WHERE a.entity_id = '"+ entity_id +"'\n" + 
				"AND a.projcode = '"+ proj_id +"'\n" + 
				"AND a.pbl_id = '"+ pbl_id +"'\n" + 
				"AND a.seq_no = "+ seq_no +"\n" + 
				"AND (CASE WHEN (a.docs_in IS NOT NULL AND a.docs_OUT IS NOT NULL)\n" + 
				"  THEN (timestamp_gt(a.docs_out, a.docs_in) OR a.docs_in IS NULL)\n" + 
				"  ELSE timestamp_gt(a.docs_out, COALESCE(a.docs_in, timestamp_mi_interval(a.docs_out, interval '1 DAY'))) END)\n" + 
				"ORDER BY TRIM(b.doc_desc), c.received_date;";*/
		
		String SQL = "SELECT * FROM view_dm_docs_to_return('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";
		
		FncSystem.out("Documents for Return", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				modelDocuments.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
			scrollDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocuments.getRowCount())));
			tblDocuments.packAll();
		}
	}
	
	//LESTER ADDED FOR DISPLAY OF DOCS TO RETURN FOR COAPPLICANT
	private void displayCoAppDocsToReturn(String entity_id, String proj_id, String pbl_id, Integer seq_no, String coapplicant_id){
		modelDocuments.clear();
		
		pgSelect db = new pgSelect();
		/*String SQL = "SELECT FALSE, a.no_copies, a.doc_id, b.doc_desc\n" + 
					 "FROM dm_coapplicant_documents a\n" + 
					 "LEFT JOIN mf_documents b on b.doc_id = a.doc_id\n" + 
					 "LEFT JOIN dm_docs_inout c on c.entity_id = a.coapplicant_id and c.proj_id = a.projcode and c.pbl_id = a.pbl_id and c.seq_no = a.seq_no and c.doc_id = a.doc_id\n" + 
					 "where a.entity_id = '"+entity_id+"'\n" + 
					 "and a.projcode = '"+proj_id+"'\n" + 
					 "AND a.pbl_id = '"+pbl_id+"'\n" + 
					 "and a.seq_no = "+seq_no+"\n" + 
					 "and a.coapplicant_id = '"+coapplicant_id+"'\n" + 
					 "AND (CASE WHEN (a.docs_in IS NOT NULL AND a.docs_OUT IS NOT NULL)\n" + 
					 "  THEN (timestamp_gt(a.docs_out, a.docs_in) OR a.docs_in IS NULL)\n" + 
					 "  ELSE timestamp_gt(a.docs_out, COALESCE(a.docs_in, timestamp_mi_interval(a.docs_out, interval '1 DAY'))) END)\n" + 
					 "ORDER BY TRIM(b.doc_desc), c.received_date;";*/
		String SQL = "SELECT * FROM view_dm_coapp_docs_to_return('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+coapplicant_id+"')";
		
		db.select(SQL);
		
		FncSystem.out("Coapplicant Documents for Return", SQL);
		
		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x < db.getRowCount(); x++){
				modelDocuments.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
			scrollDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblDocuments.getRowCount())));
			tblDocuments.packAll();
		}
	}

	private Boolean hasSelected() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < modelDocuments.getRowCount(); x++){
			listSelected.add((Boolean) modelDocuments.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

	public Boolean hasDocuments() {
		return modelDocuments.getRowCount() > 0;
	}

	private void saveDocuments() {
		ArrayList<Integer> listCopies = new ArrayList<Integer>();
		ArrayList<String> listDocuments = new ArrayList<String>();

		for(int x=0; x < modelDocuments.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelDocuments.getValueAt(x, 0);
			Integer copies = (Integer) modelDocuments.getValueAt(x, 1);
			String doc_id = (String) modelDocuments.getValueAt(x, 2);
			if(isSelected){
				listCopies.add(copies);
				listDocuments.add(String.format("'%s'", doc_id));
			}
		}

		String documents = listDocuments.toString().replaceAll("\\[|\\]", "");
		String copies = listCopies.toString().replaceAll("\\[|\\]", "");
		String findings = txtRemarks.getText().trim().replace("'", "''");
		String received_by = txtReceivedBy.getText().replace("'", "''");
		String received_from = txtReceivedFrom.getText().replace("'", "''");
		//Date date_received = dateReceived.getDate();

		pgSelect db = new pgSelect();
		if(isRelease){
			String SQL = "SELECT sp_release_documents('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ unit_id +"',\n" + 
					"	ARRAY["+ documents +"]::VARCHAR[], ARRAY["+ copies +"]::INT[], null, null, '"+ received_by +"', '"+received_from+"' ,NULL,\n" + 
					"	'"+ findings +"', '"+ UserInfo.EmployeeCode +"')";
			
			db.select(SQL);
			FncSystem.out("Display SQL for release", SQL);
		}else{
			String SQL = "SELECT sp_return_documents('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ unit_id +"', ARRAY["+ documents +"]::VARCHAR[], ARRAY["+ copies +"]::INT[], '"+ findings +"', '"+ received_by +"', '"+received_from+"' ,'"+ UserInfo.EmployeeCode +"');";
			db.select(SQL);
			FncSystem.out("Display SQL for return documents", SQL);
		}

		/*String SQL = "SELECT sp_release_documents('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ unit_id +"', ARRAY["+ documents +"]::VARCHAR[], ARRAY["+ copies +"]::INT[], '"+ findings +"', '"+ received_by +"', '"+ UserInfo.EmployeeCode +"');";
		System.out.printf("Save: %s%n", SQL);*/
	}
	
	private void saveDocsDetails(modelDM_RRDocuments model){
		//pgUpdate db = new pgUpdate();
		
		pgSelect db = new pgSelect();

		for(int x=0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String doc_id = (String) model.getValueAt(x, 2);
			String doc_desc = (String) model.getValueAt(x, 3);
			//Boolean isRequired = (Boolean) model.getValueAt(x, 5);
			String details = (String) model.getValueAt(x, 8);

			if(isSelected) {
				//System.out.printf("%-50s: %s (%s) %s%n", doc_desc, details.split("\\:|\\;")[1], details.split("\\;").length, isRequired);

				if(/*isRequired && */details != null){ //commented by Alvin Gonzales (2015-09-03)
					System.out.println();
					//System.out.printf("%-50s%n", doc_desc);
					System.out.printf("%-50s: %s (%s)%n", doc_desc, details.split("\\:|\\;")[1], details.split("\\;").length);

					int doc_sequence = 1;
					for(String detail_part : details.split("\\;")){
						String key = detail_part.split("\\:")[0].trim();
						String value = detail_part.split("\\:")[1].trim();
						//System.out.printf("%-50s: %s: %s%n", doc_desc, key, value);
						
						String SQL = "SELECT sp_save_doc_details('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+doc_id+"', "+(doc_sequence++)+", '"+key+"', '"+value+"', '"+UserInfo.EmployeeCode+"')";
						db.select(SQL);
						/*String SQL = "INSERT INTO rf_buyer_documents_details(\n" + 
								"            entity_id, projcode, pbl_id, seq_no, doc_id, doc_sequence, detail_key, \n" + 
								"            detail_value, created_by, date_created, status_id, unit_id)\n" + 
								"    VALUES ('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ doc_id +"', "+ (doc_sequence++) +", '"+ key +"', \n" + 
								"            '"+ value +"', '"+ UserInfo.EmployeeCode +"', now(), 'A', '"+ unit_id +"');";
						db.executeUpdate(SQL, true);*/
					}
					//System.out.println();
					//System.out.printf("%-50s: %s (%s)%n", doc_desc, details.split("\\:|\\;")[1], details.split("\\;").length);
				}
			}
		}
		//db.commit();
	}
	
	private void saveCoAppDocuments(){
		ArrayList<Integer> listCopies = new ArrayList<Integer>();
		ArrayList<String> listDocuments = new ArrayList<String>();

		for(int x=0; x < modelDocuments.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelDocuments.getValueAt(x, 0);
			Integer copies = (Integer) modelDocuments.getValueAt(x, 1);
			String doc_id = (String) modelDocuments.getValueAt(x, 2);
			if(isSelected){
				listCopies.add(copies);
				listDocuments.add(String.format("'%s'", doc_id));
			}
		}

		String documents = listDocuments.toString().replaceAll("\\[|\\]", "");
		String copies = listCopies.toString().replaceAll("\\[|\\]", "");
		String findings = txtRemarks.getText().trim().replace("'", "''");
		String received_by = txtReceivedBy.getText();
		String received_from = txtReceivedFrom.getText();
		
		pgSelect db = new pgSelect();
		String SQL = "";
		
		if(isRelease){
			SQL = "SELECT sp_release_coapp_documents('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+coapplicant_id+"', '"+unit_id+"', \n"+
				  "ARRAY["+documents+"]::VARCHAR[], ARRAY["+copies+"]::INT[], NULL, NULL, '"+received_by+"', '"+received_from+"' ,NULL, '"+findings+"', '"+UserInfo.EmployeeCode+"')";
		}else{
			SQL = "SELECT sp_return_coapp_documents('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+coapplicant_id+"' ,'"+ unit_id +"', ARRAY["+ documents +"]::VARCHAR[], ARRAY["+ copies +"]::INT[], '"+ findings +"', '"+ received_by +"', '"+received_from+"' ,'"+ UserInfo.EmployeeCode +"');";
		}
		
		db.select(SQL);
		FncSystem.out("Display Coapplicant Documents", SQL);
		
	}
	
	private void saveCoappDocsDetails(modelDM_RRDocuments model){
		//pgUpdate db = new pgUpdate();
		System.out.println("Dumaan dito sa Coapp Doc Details");
		pgSelect db = new pgSelect();

		for(int x=0; x < model.getRowCount(); x++){
			Boolean isSelected = (Boolean) model.getValueAt(x, 0);
			String doc_id = (String) model.getValueAt(x, 2);
			String doc_desc = (String) model.getValueAt(x, 3);
			//Boolean isRequired = (Boolean) model.getValueAt(x, 5);
			String details = (String) model.getValueAt(x, 8);

			if(isSelected) {
				//System.out.printf("%-50s: %s (%s) %s%n", doc_desc, details.split("\\:|\\;")[1], details.split("\\;").length, isRequired);

				if(/*isRequired && */details != null){ //commented by Alvin Gonzales (2015-09-03)
					System.out.println();
					//System.out.printf("%-50s%n", doc_desc);
					System.out.printf("%-50s: %s (%s)%n", doc_desc, details.split("\\:|\\;")[1], details.split("\\;").length);

					int doc_sequence = 1;
					for(String detail_part : details.split("\\;")){
						String key = detail_part.split("\\:")[0].trim();
						String value = detail_part.split("\\:")[1].trim();
						//System.out.printf("%-50s: %s: %s%n", doc_desc, key, value);
						
						String SQL = "SELECT sp_save_coapp_doc_details('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+coapplicant_id+"' ,'"+doc_id+"', "+(doc_sequence++)+", '"+key+"', '"+value+"', '"+UserInfo.EmployeeCode+"')";
						db.select(SQL);
						FncSystem.out("Display Docs Out", SQL);
						
						/*String SQL = "INSERT INTO rf_buyer_documents_details(\n" + 
								"            entity_id, projcode, pbl_id, seq_no, doc_id, doc_sequence, detail_key, \n" + 
								"            detail_value, created_by, date_created, status_id, unit_id)\n" + 
								"    VALUES ('"+ entity_id +"', '"+ proj_id +"', '"+ pbl_id +"', "+ seq_no +", '"+ doc_id +"', "+ (doc_sequence++) +", '"+ key +"', \n" + 
								"            '"+ value +"', '"+ UserInfo.EmployeeCode +"', now(), 'A', '"+ unit_id +"');";
						db.executeUpdate(SQL, true);*/
					}
					//System.out.println();
					//System.out.printf("%-50s: %s (%s)%n", doc_desc, details.split("\\:|\\;")[1], details.split("\\;").length);
				}
			}
		}
		//db.commit();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if(action.equals("Save")){
			if(hasSelected()){
				if (JOptionPane.showConfirmDialog(this, "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					if(coapplicant_id == null){
						if(selected_tab == 0){
							saveDocuments();
							if(isRelease == false){
								saveDocsDetails(modelDocuments);
							}
						}
					}else{
						if(selected_tab == 1){
							saveCoAppDocuments();
							if(isRelease == false){
								saveCoappDocsDetails(modelDocuments);
							}
						}
					}
					dispose();
				}
			}else{

			}
		}
		if(action.equals("Cancel")){
			dispose();
		}
		if(action.equals("Escape")){
			dispose();
		}
	}

}
