package Buyers.CreditandCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import tablemodel.model_CancelActive_PerBatch;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JButton;
import components._JTableMain;
import components._JXTextField;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class pnlPerBatch_Cancel extends JXPanel implements _GUI, ActionListener,LookupListener,MouseListener, MouseMotionListener,KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private pnlCancellation_Active ca;
	public JXPanel pnlMainBatch;
	private JXPanel pnlNorthBatch;
	public JLabel lblProcessFor;
	public JLabel lblProjID;
	public JLabel lblForTestingDate;
	public DefaultComboBoxModel cmbProcessForModel;
	public JComboBox cmbProcessFor;
	public  _JLookup lookupProjID;
	public _JXTextField txtProjectName;
	public _JDateChooser dteDueDate;
	public _JButton btnGenerateActive;
	public _JButton btnPreviewActive;
	public JPanel pnlCenterBatch;
	private JComboBox cmbCancelType;
	private DefaultComboBoxModel cmbCancelTypeModel;
	public  _JTableMain tblPerBatch;
	public model_CancelActive_PerBatch modelPerBatchModel;
	public JList rowHeadePerBatch;
	private JPanel pnlSouthBatch;
	private JPanel pnlSouth_Center_Active;
	private JPanel pnlCenter_Approved_C_Active;
	private JLabel lblApproveBy_Active;
	public _JLookup lookupApproveby_Active;
	public JTextField txtApprovedBy_Active;
	private JPanel pnlSouth_East_Active;
	private _JButton btnNew_Active;
	private _JButton btnEdit_Active;
	private _JButton btnSave_Active;
	private _JButton btnPost_Active;
	private _JButton btnClear_Active;
	private _FCancellationProcessing functions= new _FCancellationProcessing();
	private ButtonGroup grpButton = new ButtonGroup();
	private _CancellationProcessing_New cn;
	public JPanel pnlButton;
	private String proj_id;
	private String proj_name;
	private String table_name;
	public JScrollPane scrollPerBatch;
	//private String approved_id;
	private String approved_name;
	private String lblListof;
	private String title;

	private pgSelect db = new pgSelect();
	public pnlPerBatch_Cancel(pnlCancellation_Active ca) {
		this.ca = ca; 
		initGUI(); 
	}
	public pnlPerBatch_Cancel(pnlCancellation_Active ca,String title) {
		this.ca = ca; 
		this.title = title;
		initGUI(); 
	}

	public pnlPerBatch_Cancel(pnlCancellation_Active ca , _CancellationProcessing_New cn) {
		this.ca = ca;
		this.cn = cn; 
		initGUI();
	}

	public pnlPerBatch_Cancel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public pnlPerBatch_Cancel(LayoutManager layout) {
		super(layout);
	}

	public pnlPerBatch_Cancel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		{

			pnlMainBatch = new JXPanel(new BorderLayout(5,5));
			this.add(pnlMainBatch,BorderLayout.CENTER);
			{
				pnlNorthBatch = new JXPanel(new BorderLayout(5,5)); 
				pnlMainBatch.add(pnlNorthBatch,BorderLayout.NORTH);
				pnlNorthBatch.setPreferredSize(new Dimension(0, 80));
				{
					JPanel _pnlLabel = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlNorthBatch.add(_pnlLabel,BorderLayout.WEST);
					_pnlLabel.setVisible(true);

					{
						lblProcessFor = new JLabel(" Process For : ");
						_pnlLabel.add(lblProcessFor);
					}
					{
						lblProjID = new JLabel(" Project ID : ");
						_pnlLabel.add(lblProjID);
					}
					{
						lblForTestingDate = new JLabel(" Due Date : ");
						_pnlLabel.add(lblForTestingDate);
					}
				}
				{
					JPanel pnlProcess = new JPanel(new GridLayout(3, 1, 3, 3));
					pnlNorthBatch.add(pnlProcess,BorderLayout.CENTER);
					pnlProcess.setPreferredSize(new Dimension(300, 60));
					{

						cmbProcessForModel = new DefaultComboBoxModel(new String[] { " Recommended for Cancellation", " Qualified for CSV Tagging" });
						cmbProcessFor = new JComboBox();
						pnlProcess.add(cmbProcessFor);
						cmbProcessFor.setModel(cmbProcessForModel);
						cmbProcessFor.setPreferredSize(new Dimension(250, 25));
						cmbProcessFor.addActionListener(this);
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(3,3));
						pnlProcess.add(pnlProject);
						{
							lookupProjID = new _JLookup("ID", "Search Project", 0);
							pnlProject.add(lookupProjID,BorderLayout.WEST);
							lookupProjID.setPreferredSize(new Dimension(50, 25));

							lookupProjID.addLookupListener(this);

						}
						{
							txtProjectName = new _JXTextField();
							pnlProject.add(txtProjectName,BorderLayout.CENTER);
							txtProjectName.setEditable(false);
							txtProjectName.setPrompt("Project Name");
						}
					}
					{

						dteDueDate= new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlProcess.add(dteDueDate);
						dteDueDate.setDate(null);

					}
				}
				{
					pnlButton = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlNorthBatch.add(pnlButton,BorderLayout.EAST);
					pnlButton.setPreferredSize(new Dimension(300, 60));
					{
						btnGenerateActive = new _JButton("Generate");
						pnlButton.add(btnGenerateActive);
						btnGenerateActive.addActionListener(this);
					}
					{
						btnPreviewActive = new _JButton("Preview");
						pnlButton.add(btnPreviewActive);
						btnPreviewActive.addActionListener(this);
					}
				}
			}
			{

				pnlCenterBatch = new JPanel(new BorderLayout(5, 5));
				pnlMainBatch.add(pnlCenterBatch,BorderLayout.CENTER);
				pnlCenterBatch.setBorder(components.JTBorderFactory.createTitleBorder("List of All Accounts"));
				pnlCenterBatch.setPreferredSize(new Dimension(900, 300));
				{
					cmbCancelTypeModel = new DefaultComboBoxModel(new String[] {"","Company Initiated", "Buyer Initiated"});
					cmbCancelType = new JComboBox();
					cmbCancelType.setModel(cmbCancelTypeModel);
					cmbCancelType.addActionListener(this);

				}
				{

					scrollPerBatch = new JScrollPane();
					pnlCenterBatch.add(scrollPerBatch, BorderLayout.CENTER);
					scrollPerBatch.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblPerBatch.clearSelection();
						}
					});
					{

						modelPerBatchModel = new model_CancelActive_PerBatch();
						modelPerBatchModel.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								//Addition of rows
								if(e.getType() == 1){
									((DefaultListModel)rowHeadePerBatch.getModel()).addElement(modelPerBatchModel.getRowCount());

									if(modelPerBatchModel.getRowCount() == 0){
										rowHeadePerBatch.setModel(new DefaultListModel());
									}
								}
							}
						});

						tblPerBatch = new _JTableMain(modelPerBatchModel);
						scrollPerBatch.setViewportView(tblPerBatch);
						modelPerBatchModel.setEditable(true);
						tblPerBatch.setHorizontalScrollEnabled(true);
						tblPerBatch.addMouseMotionListener(this);
						tblPerBatch.addMouseListener(this);
						tblPerBatch.addKeyListener(this);
						tblPerBatch.packAll();

						/** Repaint for Highlight **/
						tblPerBatch.getTableHeader().addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent evt) {
								tblPerBatch.repaint();
							}
						});
					}
					{
						rowHeadePerBatch = tblPerBatch.getRowHeader();
						rowHeadePerBatch.setModel(new DefaultListModel());
						scrollPerBatch.setRowHeaderView(rowHeadePerBatch);
						scrollPerBatch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

					}
				}
			}//CENTER  
			{

				pnlSouthBatch = new JPanel(new BorderLayout(4,4));
				pnlMainBatch.add(pnlSouthBatch, BorderLayout.SOUTH);
				pnlSouthBatch.setPreferredSize(new Dimension(900, 55));
				{
					JPanel pnlNorth = new  JPanel(new BorderLayout(5, 5));
					pnlSouthBatch.add(pnlNorth,BorderLayout.NORTH);
				}

				{
					pnlSouth_Center_Active = new  JPanel(new BorderLayout(5, 5));
					pnlSouthBatch.add(pnlSouth_Center_Active,BorderLayout.CENTER);
					{
						JPanel pnlCenter_Approved = new JPanel(new BorderLayout(5, 5));
						pnlSouth_Center_Active.add(pnlCenter_Approved,BorderLayout.NORTH);
						pnlCenter_Approved.setPreferredSize(new Dimension(0, 5));
						pnlCenter_Approved.setVisible(true);

					}
					{
						pnlCenter_Approved_C_Active = new JPanel(new BorderLayout(5, 5));
						pnlSouth_Center_Active.add(pnlCenter_Approved_C_Active,BorderLayout.CENTER);
						pnlCenter_Approved_C_Active.setVisible(true);
						{
							lblApproveBy_Active = new JLabel(" Approved By :");
							pnlCenter_Approved_C_Active.add(lblApproveBy_Active,BorderLayout.WEST);
						}
						{
							JPanel pnlApproved = new JPanel(new BorderLayout(3, 3));
							pnlCenter_Approved_C_Active.add(pnlApproved,BorderLayout.CENTER);
							pnlApproved.setVisible(true);
							{
								lookupApproveby_Active = new _JLookup("Emp. ID", "Search Employee", 0);
								pnlApproved.add(lookupApproveby_Active,BorderLayout.WEST);
								lookupApproveby_Active.setLookupSQL(functions.getApprovedby());
								lookupApproveby_Active.setPreferredSize(new Dimension(60, 50));
								lookupApproveby_Active.addLookupListener(this);

							}
							{
								txtApprovedBy_Active = new JTextField();
								pnlApproved.add(txtApprovedBy_Active,BorderLayout.CENTER);
								txtApprovedBy_Active.setEditable(false);
							}
						}
					}

					{
						JPanel pnlCenter_Approved_S = new JPanel(new BorderLayout(5, 5));
						pnlSouth_Center_Active.add(pnlCenter_Approved_S,BorderLayout.SOUTH);
						pnlCenter_Approved_S.setPreferredSize(new Dimension(0, 5));
					}	
				}
				{
					pnlSouth_East_Active = new  JPanel(new GridLayout(1, 5, 3, 3));
					pnlSouthBatch.add(pnlSouth_East_Active,BorderLayout.EAST);
					pnlSouth_East_Active.setPreferredSize(new Dimension(400, 0));

					{
						btnNew_Active = new _JButton("New");
						pnlSouth_East_Active.add(btnNew_Active);
						btnNew_Active.setActionCommand("New");
						btnNew_Active.addActionListener(this);
						grpButton.add(btnNew_Active);
					}
					{
						btnEdit_Active = new _JButton("Edit");
						pnlSouth_East_Active.add(btnEdit_Active);
						btnEdit_Active.setActionCommand("Edit");
						btnEdit_Active.addActionListener(this);
						grpButton.add(btnEdit_Active);
					}
					{
						btnSave_Active = new _JButton("Save");
						pnlSouth_East_Active.add(btnSave_Active);
						btnSave_Active.addActionListener(this);
					}
					{
						btnPost_Active = new _JButton("Post");
						pnlSouth_East_Active.add(btnPost_Active);
						btnPost_Active.setActionCommand("Post");
						btnPost_Active.addActionListener(this);
					}

					{
						btnClear_Active = new _JButton("Clear");
						pnlSouth_East_Active.add(btnClear_Active);
						btnClear_Active.setActionCommand("Clear_Active");
						btnClear_Active.addActionListener(this);
						grpButton.add(btnClear_Active);

					}
				}

				JPanel _pnlSouth = new  JPanel(new BorderLayout(5, 5));
				pnlSouthBatch.add(_pnlSouth,BorderLayout.SOUTH);

			}

		}
		ca.setComponentsEditable(this, false);
		ca.setComponentsEnabled(this, false);
		setEnabled(false);
	}

	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void lookupPerformed(LookupEvent e) {

		if( e.getSource().equals(lookupProjID)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){
				proj_id = data[0].toString();
				proj_name = data[1].toString();

				txtProjectName.setText(proj_name);
				btnGenerateActive.setEnabled(true);
				btnPreviewActive.setEnabled(true);

			}
		}

		if( e.getSource().equals(lookupApproveby_Active)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){
				//approved_id = data[0].toString();
				approved_name = data[1].toString();

				txtApprovedBy_Active.setText(approved_name);

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnGenerateActive)) {

			if (ca.cmbBuyerType.getSelectedIndex() == 0 ) {
				table_name ="All";
			}
			if(ca.cmbBuyerType.getSelectedIndex() == 1 ){
				table_name ="ihf_due_accounts";
			}
			if(ca.cmbBuyerType.getSelectedIndex() == 2 ){
				table_name ="pagibig_due_accounts";
			}

			if (txtProjectName.getText().isEmpty() || txtProjectName.getText().equals("Project Name")){
				JOptionPane.showMessageDialog( this, "Please Choose a Project Name first  ", "Incomplete", JOptionPane.OK_OPTION );
				return;
			}
			new Thread(new Generate()).start();

		}

		if (e.getSource().equals(btnNew_Active)) {
			NewProcess();
		}

		if (e.getSource().equals(btnClear_Active)) {
			ClearProcess();
		}

		if (e.getSource().equals(btnPreviewActive)) {
			new Thread(new ForPreviewProjCancel()).start(); 
		}

	}//actionPerformed

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void setEnabled(boolean enable) {
		for(Component panel : this.getComponents()){
			if(panel instanceof JPanel){
				for(Component comp : ((JPanel) panel).getComponents()){
					//if(comp instanceof JLabel == false){
					comp.setEnabled(enable);
					//}
				}
			}
		}
	}

	public  void _setEnableComponents(Boolean ena){
		tblPerBatch.setEnabled(true);
		lookupApproveby_Active.setEnabled(true);

	}

	public void btnState(Boolean sNew,Boolean sEdit ,Boolean sSave,Boolean sPost,Boolean sClear ){
		btnNew_Active.setEnabled(sNew);
		btnEdit_Active.setEnabled(sEdit);
		btnSave_Active.setEnabled(sSave);
		btnPost_Active.setEnabled(sPost);
		btnClear_Active.setEnabled(sClear);

	}

	public class Generate implements Runnable{
		private int recomend_or_csv;

		public void run() {

			FncGlobal.startProgress("Generating . . . Report");
			
			if (cmbProcessFor.getSelectedIndex() == 0 ) {
				recomend_or_csv = 0;
			}else{
				recomend_or_csv = 1;
			}

			if (ca.cmbBuyerType.getSelectedIndex() == 0) {
				lblListof = "Recommended for Cancellation for All Accounts";

			}
			if (ca.cmbBuyerType.getSelectedIndex() == 1) {
				lblListof = "Recommended for Cancellation for IHF Accounts";

			}	
			if (ca.cmbBuyerType.getSelectedIndex() == 2) {
				lblListof = "Recommended for Cancellation for Pag-ibig Accounts";

			}

			rowHeadePerBatch.setModel(new DefaultListModel());
			functions.displayPerBatchRecommended(modelPerBatchModel, rowHeadePerBatch, table_name, proj_id,recomend_or_csv);
			scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

			if (cmbProcessFor.getSelectedIndex() == 0 ) {
				tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase",	"<html><font color = \"red\">*</font> &nbsp;<i>CSV</i>","Project Name");	
				tblPerBatch.showColumns("<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>");
				tblPerBatch.packAll();
				tblPerBatch.packColumn(21, 400, 400);
				tblPerBatch.packColumn(22, 400, 400);
			}else{
				tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase","<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>","Project Name");
				tblPerBatch.showColumns("<html><font color = \"red\">*</font> &nbsp;<i>CSV</i>");
				tblPerBatch.packAll();
			}

			/*
			if (tabpane.getSelectedIndex()== 1 || tblCancelTag.isShowing()) {

				rowHeaderCancelTag.setModel(new DefaultListModel());

				_FCancellationProcessing.displayCancellationTag(modelCancelTag, rowHeaderCancelTag, _proj_id,cmbProcessFor_Tag.getSelectedIndex());
				scrollCancelTag.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelTag.getRowCount())));

				tblCancelTag.packAll();
				tblCancelTag.packColumn(8, 400, 400);

			}*/
			FncGlobal.stopProgress();
		}
	}

	public void NewProcess(){
		ca.lookupBatchNo.setValue(functions.getBatchNo());
		ca.lookupBatchNo.setEditable(false);
		ca.setComponentsEnabled(pnlMainBatch, true);
		ca.setComponentsEditable(pnlMainBatch, true);
		btnState(false, false, true, false, true);

	}

	public void ClearProcess(){
		
		ca.cmbBuyerType.setSelectedIndex(0);
		ca.lookupBatchNo.setValue("");
		ca.lookupBatchNo.setEditable(true);

		lookupApproveby_Active.setValue("");
		txtApprovedBy_Active.setText("");

		lookupProjID.setValue("");
		txtProjectName.setText("");

		modelPerBatchModel.clear();
		rowHeadePerBatch.setModel(new DefaultListModel());
		scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));

		ca.setComponentsEnabled(pnlMainBatch, false);
		ca.setComponentsEditable(pnlMainBatch, false);

		btnState(true, true, false, false, false);

	}

	public class ForPreviewProjCancel implements Runnable{

		private String preparedby = "";
		private String approvedby = "";
		ArrayList<String> iftrue = new ArrayList<String>();
		private String entity_id;
		private String _proj_id;
		private String pbl_id;
		private String seq_no;
		@Override
		public void run() {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", _CancellationProcessing_New.company);
			mapParameters.put("co_id",  _CancellationProcessing_New.co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+  _CancellationProcessing_New.company_logo));
			mapParameters.put("lblListof", lblListof);
			

			String SQL ="";
			for (int i = 0; i < modelPerBatchModel.getRowCount(); i++) {
				Boolean SelectedItem = (Boolean) modelPerBatchModel.getValueAt(i, 0);

				if (SelectedItem) {
					entity_id = modelPerBatchModel.getValueAt(i, 21).toString();
					_proj_id = modelPerBatchModel.getValueAt(i, 22).toString();
					pbl_id = modelPerBatchModel.getValueAt(i, 23).toString();
					seq_no = modelPerBatchModel.getValueAt(i, 24).toString();
					iftrue.add(modelPerBatchModel.getValueAt(i, 25).toString());


					SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +

							"SELECT '"+modelPerBatchModel.getValueAt(i, 1)+"' AS unit_pbl,\n" + 
							"'"+modelPerBatchModel.getValueAt(i, 2)+"' AS client_name,\n" + 
							"'"+modelPerBatchModel.getValueAt(i, 3)+"' AS buyertype,\n" + 
							"'"+modelPerBatchModel.getValueAt(i, 5)+"' AS sale_div,\n" + 
							"_get_sales_agent('"+entity_id+"','"+_proj_id+"','"+pbl_id+"') AS sale_agent,\n" +
							"'"+modelPerBatchModel.getValueAt(i, 7)+"' AS house_model,\n" + 
							"get_payment_stage('"+entity_id+"','"+_proj_id+"','"+pbl_id+"','"+seq_no+"') AS stage,\n" + 
							"'"+modelPerBatchModel.getValueAt(i, 27)+"' AS phase,\n" + 
							"_get_tr_date('"+entity_id+"','"+_proj_id+"','"+pbl_id+"','"+seq_no+"') AS tr_date,\n" + 
							"_get_or_date('"+entity_id+"','"+_proj_id+"','"+pbl_id+"','"+seq_no+"') AS or_date,\n" + 
							"NULLIF('"+modelPerBatchModel.getValueAt(i, 14)+"','null') AS default_date,\n" + 
							"get_nsp('"+entity_id+"','"+pbl_id+"','"+seq_no+"','"+_proj_id+"')::numeric AS nsp,\n" + 
							"'"+modelPerBatchModel.getValueAt(i, 19)+"' AS withntc,\n" +
							"'"+modelPerBatchModel.getValueAt(i, 22)+"' AS proj_id,\n" + 
							"NULLIF('"+modelPerBatchModel.getValueAt(i, 28)+"','null') AS proj_name,\n"+
							"(case when _get_or_date('"+entity_id+"','"+_proj_id+"','"+pbl_id+"','"+seq_no+"') is null then 'TR' ELSE 'OR' END) AS class \n";

				}
			}

			if (iftrue.isEmpty()) {
				JOptionPane.showMessageDialog(ca,"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
				return;
			}

			db.select(SQL);

			if (db.isNotNull()) {
				int count_tr = 0;
				int count_or = 0;
				int total_or_tr = 0;
				BigDecimal total_tr = new BigDecimal("0.00");
				BigDecimal total_or = new BigDecimal("0.00");
				BigDecimal subtotal_or_tr = new BigDecimal("0.00");

				for (int j = 0; j < db.getRowCount(); j++) {
					if (db.Result[j][15].equals("TR")) {
						BigDecimal totalTR = (BigDecimal) db.Result[j][11];

						try {
							total_tr = total_tr.add(totalTR);

						} catch (Exception e1) { }
						count_tr++;
					}
					if (db.Result[j][15].equals("OR")) {

						BigDecimal totalOR = (BigDecimal) db.Result[j][11]; 

						try {
							total_or = total_or.add(totalOR);

						} catch (Exception e1) { } 
						count_or++;
					}	
				}


				total_or_tr = count_or + count_tr;
				subtotal_or_tr = subtotal_or_tr.add(total_tr) ;
				subtotal_or_tr = subtotal_or_tr.add(total_or) ;

				mapParameters.put("total_sum_tr",total_tr);
				mapParameters.put("count_tr",count_tr);
				mapParameters.put("total_sum_or",total_or);
				mapParameters.put("count_or",count_or);
				mapParameters.put("subtotal_or_tr",subtotal_or_tr);
				mapParameters.put("total_or_tr",total_or_tr);
				
				//  Modified by : Jervin Vilog /change of level no. due to request of sir garret 
//				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
//						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
//						+ "and (select level_no from mf_rank_level where level_code = emp_rank) > 13");
				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
						+ "and (select level_no from mf_rank_level where level_code = emp_rank) < 5");

				if (db.isNotNull()) {
					preparedby = db.Result[0][1].toString();
				}
				
				//  Modified by : Jervin Vilog /change of level no. due to request of sir garret 
//				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
//						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
//						+ "and (select  level_no from mf_rank_level where level_code = emp_rank) <= 13");
				db.select("select (select  level_no from mf_rank_level where level_code = emp_rank), _get_client_name(entity_id) "
						+ "from em_employee where emp_code = '"+UserInfo.EmployeeCode+"' "
						+ "and (select  level_no from mf_rank_level where level_code = emp_rank) >= 5");

				if (db.isNotNull()) { 
					approvedby = db.Result[0][1].toString();
				}

				mapParameters.put("approvedby",approvedby);
				mapParameters.put("preparedby",preparedby);

				System.out.println("SQL: " + SQL) ;
				FncReport.generateReport("/Reports/rptCancellation_Processing.jasper", "List of Recommendation Accounts",  mapParameters, SQL);

			}
		}
	}
	
	public class Posting_Cancellation_Active implements Runnable{ // XXX Posting_Cancellation_Active

		private Boolean SelectedItem;
		private String _textreason;
		private int tagged;

		@Override
		public void run() {

			FncGlobal.startProgress("Saving  . . .Please wait ");
 
			int isEmpty = 0;
			if (ca.tabpane_per_account.getSelectedIndex() == 0) {
				tagged = 0;

				if (cmbProcessFor.getSelectedIndex() == 0) {

					for (int x = 0; x < tblPerBatch.getModel().getRowCount(); x++) {

						SelectedItem = (Boolean) modelPerBatchModel.getValueAt(x, 0);
						_textreason =  modelPerBatchModel.getValueAt(x,29).toString().trim();
						
						if (SelectedItem) {
							tagged++;
						}
					}

					if (tagged ==0 ) {
						JOptionPane.showMessageDialog(cn, "Please select Account(s) to be Cancelled and fill in the ''Reason'' and ''Remarks'' Column"  ,"Incomplete Details",JOptionPane.OK_OPTION);
						FncGlobal.stopProgress();
						return;
					}

					if (tagged >= 1) {
						tblPerBatch.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);// Stops Cell Edit
						System.out.println("tagged >= 1");

						for (int x = 0; x < tblPerBatch.getModel().getRowCount(); x++) {
							SelectedItem = (Boolean) modelPerBatchModel.getValueAt(x, 0);
							_textreason =  modelPerBatchModel.getValueAt(x,29).toString().trim();
							if (SelectedItem) {
								if (_textreason.equals("")) {
									System.out.println("empty ang reason");

									isEmpty++;
								}
							}
						}

						System.out.println("empty" +isEmpty);
												
						if (isEmpty >= 1) {

							System.out.println("empty ang reason" +isEmpty);
							JOptionPane.showMessageDialog(cn,"Please Indicate the Reason for Cancellation on the ''Reason Column''  \n You can also put Remarks on ''Remarks Column''","Incomplete Details",JOptionPane.OK_OPTION);
							FncGlobal.stopProgress(); 
							return;

						}

						if (lookupApproveby_Active.getText().isEmpty()) {
							JOptionPane.showMessageDialog(cn, "Please Select an Approver for this Cancellation Process  ",  "Incomplete Details",  JOptionPane.OK_OPTION);
							FncGlobal.stopProgress();
							return;
						} // Approver is Empty 

						int response = JOptionPane.showConfirmDialog(cn,"Are you sure you want to cancel selected account(s)  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response == JOptionPane.YES_OPTION) {

							//Save_Cancellation();
							//perBatchRecommend();
							//new Thread(new ForNotices()).start();
							//_setEnabled(false);
							//_Clear();
							//lblBatchNo.setEnabled(true);
							//lookupBatchNo.setEnabled(true);
							btnState(true, true, false, false, false);

						}
					}

				}//recommended 

				/*	if (cmbProcessFor.getSelectedIndex() == 1) {

					if (tblPerBatch.getRowCount() == 0) {
						JOptionPane.showMessageDialog(getContentPane(), "Please Generate Recommended Accounts for Cancellation  ", "Incomplete", JOptionPane.OK_OPTION);
						FncGlobal.stopProgress();
						return;

					}

					for (int x = 0; x < modelPerBatchModel.getRowCount(); x++) {

						SelectedItem = (Boolean) modelPerBatchModel.getValueAt(x, 0);
						if (SelectedItem) {
							tagged++;
						}
					}

					if (tagged ==0 ) {
						JOptionPane.showMessageDialog(getContentPane(), "Please select Account(s) to be Posted for CSV \n and input the Amount of CSV on the CSV Column  "  ,"Incomplete Details",JOptionPane.OK_OPTION);
						FncGlobal.stopProgress();
						return;
					}

					if (tagged >= 1) {
						if (SelectedItem) {

							for (int x = 0; x < tblPerBatch.getModel().getRowCount(); x++) {
								SelectedItem = (Boolean) modelPerBatchModel.getValueAt(x, 0);
								BigDecimal csv_amount =  (BigDecimal) (modelPerBatchModel.getValueAt(x,31) == null ? null : modelPerBatchModel.getValueAt(x,31)) ;
								if (SelectedItem) {
									if (csv_amount == null) {
										System.out.println("empty ang reason");

										isEmpty++;
									}
								}
							}
						}

						if (isEmpty >= 1) {

							JOptionPane.showMessageDialog(getContentPane(),
									"Please Input the Amount of CSV on the CSV Column  ",
									"Incomplete Details", JOptionPane.OK_OPTION);
							FncGlobal.stopProgress();
							return;
						}

						if (lookupApproveby_Active.getText().isEmpty()) {
							JOptionPane.showMessageDialog(getContentPane(), "Please Select an Approver for this Cancellation Process  ",  "Incomplete Details",  JOptionPane.OK_OPTION);
							FncGlobal.stopProgress();
							return;
						} // Approver is Empty

						if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to qualified selected account(s)  " ,"Confirm", 
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

							perBatchCSV();
							formload();
							clear();

							JOptionPane.showMessageDialog(getContentPane(),
									"\nSelected Account(s) Posted for CSV with the following RPLF No.  \n" + forcsv,
									"Succesful",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}*/
			}
			FncGlobal.stopProgress(); 
		}
	}
	/*
	
	public class Generate_Edit implements Runnable{

		private String _reason_id;

		@Override
		public void run() {

			

			if (ca.tabpane_per_account.getSelectedIndex()== 0 || tblPerBatch.isShowing()) {


				if (ca.cmbBuyerType.getSelectedIndex() == 0) {
					lblListof = "Recommended for Cancellation for All Accounts";

				}
				if (ca.cmbBuyerType.getSelectedIndex() == 1) {
					lblListof = "Recommended for Cancellation for IHF Accounts";

				}	
				if (ca.cmbBuyerType.getSelectedIndex() == 2) {
					lblListof = "Recommended for Cancellation for Pag-ibig Accounts";

				}

				rowHeadePerBatch.setModel(new DefaultListModel());
				functions.displayEditPerBatchRecommended(modelPerBatchModel, rowHeadePerBatch,ca.getBatch_no());
				scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPerBatch.getRowCount())));


				if (cmbProcessFor.getSelectedIndex() == 0 ) {
					tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase",	"<html><font color = \"red\">*</font> &nbsp;<i>CSV</i>","Project Name");	
					tblPerBatch.showColumns("<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>");
					tblPerBatch.packAll();
					//tblPerBatch.packColumn(21, 400, 400);
					//tblPerBatch.packColumn(22, 400, 400);
				}else{
					tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase","<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>","Project Name");
					tblPerBatch.showColumns("<html><font color = \"red\">*</font> &nbsp;<i>CSV</i>");
					tblPerBatch.packAll();
				}

				db.select("SELECT proj_id, get_project_name(proj_id),approved_by,get_client_name_emp_id(approved_by),reason FROM rf_cancellation where batch_no = ('"+ca.getBatch_no()+"')");

				if (db.isNotNull()) {
					lookupProjID.setValue(db.Result[0][0].toString());
					txtProjectName.setText(db.Result[0][1].toString());
					lookupApproveby_Active.setValue(db.Result[0][2].toString());
					txtApprovedBy_Active.setText(db.Result[0][3].toString());
					_reason_id = db.Result[0][4].toString();
				}

				db.select("SELECT remdesc FROM mf_remarks WHERE remark_id  = '"+reason+"' AND status_id = 'A'");

				if (db.isNotNull()) {

					modelPerBatchModel.setValueAt( db.Result[0][0].toString(), tblPerBatch.getSelectedRow(),22);
				}

				modelPerBatchModel.setEditable(true);
			}
		}
	}*/
	
	
}
