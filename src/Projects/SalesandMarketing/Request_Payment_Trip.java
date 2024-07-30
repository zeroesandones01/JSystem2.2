package Projects.SalesandMarketing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._FPromissoryNoteCommintment;
import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_RequestPaymenTrip;

public class Request_Payment_Trip extends _JInternalFrame implements _GUI,ActionListener,LookupListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Request for Payment (Tripping)";
	public static Dimension frameSize = new Dimension(900, 500);
	private ButtonGroup grpButton = new ButtonGroup();
	private JXPanel pnlMain;
	private JXPanel pnlNorth;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private JLabel lblRequestType;
	private JLabel lblPayee;
	private JLabel lblPayeeType;
	private _JLookup lookupRequestType;
	private _JLookup lookupPayee;
	private _JLookup lookupPayeeType;
	private JXLabel lblRPLFNo;
	private JXLabel lblRPLFValue;
	private JXPanel pnlCenter;
	private _JScrollPaneMain scrollListTicket;
	private model_RequestPaymenTrip modelListTicket;
	private _JTableMain tblListTicket;
	private JList rowListTicket;
	private _JScrollPaneTotal scrollTicketCostTotal;
	private model_RequestPaymenTrip modelTicketCostTotal;
	private _JTableTotal tblTicketCostTotal;

	private _FPromissoryNoteCommintment methods = new _FPromissoryNoteCommintment();
	private String co_id;
	private String company;
	private String company_logo;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	private String rplf_type_id;
	private String rplf_type_desc;
	private String entity_id;
	private String entity_name;
	private String entity_type_id;
	private String entity_type_desc;
	private JButton btnPreview;
	private Boolean previewlookup = true;

	public Request_Payment_Trip() {
		super(title, true, true, false, true);
		initGUI();
		formLoad();
	}

	public Request_Payment_Trip(String title) {
		super(title);
		initGUI();
		formLoad();
	}

	public Request_Payment_Trip(String title, boolean resizable, boolean closable, boolean maximizable,	boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		formLoad();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new Dimension(frameSize));
		this.getContentPane().setLayout(new BorderLayout());
		this.setResizable(false);
		{
			pnlMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JXPanel();
				pnlMain.add(pnlNorth,BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(3, 3));
				pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 180));
				{

					JXPanel North = new JXPanel(new BorderLayout());
					pnlNorth.add(North,BorderLayout.NORTH);
					North.setPreferredSize(new Dimension(pnlMain.getWidth(), 55));
					North.setBorder(components.JTBorderFactory.createTitleBorder("Company"));
					{
						{
							JPanel pnlCompany = new JPanel(new BorderLayout(3,3));
							North.add(pnlCompany,BorderLayout.CENTER);
							pnlCompany.setPreferredSize(new Dimension(74, 20));
							{
								{

									lookupCompany = new _JLookup("ID", "Company", 0) ; /// XXX lookupCompany 
									pnlCompany.add(lookupCompany,BorderLayout.WEST);
									lookupCompany.addLookupListener(this);
									lookupCompany.setReturnColumn(0);
									lookupCompany.setLookupSQL(methods.getCompany());
									lookupCompany.setPreferredSize(new Dimension(100, 20));
								}
								{
									txtCompany = new _JXTextField();
									pnlCompany.add(txtCompany,BorderLayout.CENTER);
									txtCompany.setEditable(false);
									txtCompany.setPrompt("Company Name");
									txtCompany.setPreferredSize(new Dimension(100, 20));
								} 
							}
						}
					}

					JXPanel Center = new JXPanel(new BorderLayout());
					pnlNorth.add(Center,BorderLayout.CENTER);
					Center.setPreferredSize(new Dimension(pnlMain.getWidth(), 120));
					Center.setBorder(components.JTBorderFactory.createTitleBorder(""));
					{
						JXPanel Center_West = new JXPanel(new BorderLayout());
						Center.add(Center_West,BorderLayout.WEST);
						Center_West.setPreferredSize(new Dimension(450, 100));
						{

							JPanel pnlLabel = new JPanel(new GridLayout(3, 1, 3, 3));
							Center_West.add(pnlLabel,BorderLayout.WEST);
							{
								JLabel lblRequestType = new JLabel(" Request Type ");
								pnlLabel.add(lblRequestType);

								JLabel lblPayee = new JLabel(" Payee ");
								pnlLabel.add(lblPayee);

								JLabel lblPayeeType = new JLabel(" Payee Type ");
								pnlLabel.add(lblPayeeType);


							}
							JPanel pnlAction = new JPanel(new GridLayout(3, 1, 3, 3));
							Center_West.add(pnlAction,BorderLayout.CENTER);
							{

								JXPanel pnlRequestType = new JXPanel(new BorderLayout(3, 3));
								pnlAction.add(pnlRequestType);
								{
									lookupRequestType = new _JLookup("", "Request Type", 0) ; /// XXX lookupRequestType 
									pnlRequestType.add(lookupRequestType,BorderLayout.WEST);
									lookupRequestType.setPreferredSize(new Dimension(100, 25));
									lookupRequestType.setLookupSQL(getRequestType());
									lookupRequestType.setReturnColumn(0);
									lookupRequestType.addLookupListener(this);


								}
								{
									lblRequestType = new JXLabel("[ ]");
									pnlRequestType.add(lblRequestType,BorderLayout.CENTER);
								}

								JXPanel pnlPayee = new JXPanel(new BorderLayout(3, 3));
								pnlAction.add(pnlPayee);
								{
									lookupPayee = new _JLookup("", "Payee", 0) ; /// XXX lookupPayee 
									pnlPayee.add(lookupPayee,BorderLayout.WEST);
									lookupPayee.setPreferredSize(new Dimension(100, 25));
									lookupPayee.setLookupSQL(getPayee());
									lookupPayee.setReturnColumn(0);
									lookupPayee.addLookupListener(this);

								}
								{
									lblPayee = new JXLabel("[ ]");
									pnlPayee.add(lblPayee,BorderLayout.CENTER);
								}

								JXPanel pnlPayeeType = new JXPanel(new BorderLayout(3, 3));
								pnlAction.add(pnlPayeeType);
								{
									lookupPayeeType = new _JLookup("", "Payee Type", 0) ; /// XXX lookupPayeeType 
									pnlPayeeType.add(lookupPayeeType,BorderLayout.WEST);
									lookupPayeeType.setPreferredSize(new Dimension(100, 25));
									lookupPayeeType.setReturnColumn(0);
									lookupPayeeType.addLookupListener(this);

								}
								{
									lblPayeeType = new JXLabel("[ ]");
									pnlPayeeType.add(lblPayeeType,BorderLayout.CENTER);
								}
							}
						}

						JXPanel Center_East = new JXPanel(new BorderLayout());
						Center.add(Center_East,BorderLayout.EAST);
						Center_East.setPreferredSize(new Dimension(300, 100));
						{
							JXPanel pnlRPLFNo = new JXPanel(new BorderLayout(3, 3));
							Center_East.add(pnlRPLFNo,BorderLayout.NORTH);
							{
								lblRPLFNo = new JXLabel("RPLF No. :");
								pnlRPLFNo.add(lblRPLFNo,BorderLayout.WEST);
							}
							{
								lblRPLFValue = new JXLabel("[ ]");
								pnlRPLFNo.add(lblRPLFValue,BorderLayout.CENTER);
							}
						}
					}
				}
			}
			{
				pnlCenter = new JXPanel();
				pnlMain.add(pnlCenter,BorderLayout.CENTER);
				pnlCenter.setLayout(new BorderLayout(3, 3));
				{
					JXPanel pnlListTicket = new JXPanel(new BorderLayout(5, 5));
					pnlCenter.add(pnlListTicket);
					{
						scrollListTicket = new _JScrollPaneMain();
						pnlListTicket.add(scrollListTicket, BorderLayout.CENTER);
						scrollListTicket.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblListTicket.clearSelection();
							}
						});
						{
							modelListTicket = new model_RequestPaymenTrip();
							modelListTicket.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowListTicket.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowListTicket.getModel()).addElement(modelListTicket.getRowCount());
									}
								}
							});


							tblListTicket = new _JTableMain(modelListTicket);
							scrollListTicket.setViewportView(tblListTicket);
							tblListTicket.setHorizontalScrollEnabled(true);
							modelListTicket.setEditable(true);
							tblListTicket.hideColumns("Purpose", // 7
									"Entity ID", // 8
									"Driver ID", // 9
									"Div ID", // 10
									"Dept ID", // 11
									"Charge Acct. ID", // 12
									"Charge Proj. ID", // 13
									"Co ID", // 15
									"BusUnit ID");
							tblListTicket.packAll();

						}
						{
							rowListTicket = tblListTicket.getRowHeader();
							rowListTicket.setModel(new DefaultListModel());
							scrollListTicket.setRowHeaderView(rowListTicket);
							scrollListTicket.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{

							scrollTicketCostTotal = new _JScrollPaneTotal(scrollListTicket);
							pnlListTicket.add(scrollTicketCostTotal, BorderLayout.SOUTH);
							{
								modelTicketCostTotal = new model_RequestPaymenTrip();
								modelTicketCostTotal.addRow(new Object[] {
										null,//"Tag", // 0
										null,//"Ticket No.", // 1
										null,//"Date", // 2
										"Total",//"Request By"
										0.00,//"Cost", // 22
										null,//"Driver Name", // 32
										null,//"ATM No.", // 33
										null,//"Purpose", // 34
										null,//"Entity ID.", // 35
										null,//"Driver ID", //36
										null,//"Div ID" //37
										null,//"Dept ID, //36
										null,//"Charge Acct. ID", //36
										null,//"Charge Proj. ID", //36
										null,//"Co ID", //36
										null//"BusUnit ID" //37

								});

								tblTicketCostTotal = new _JTableTotal(modelTicketCostTotal, tblListTicket);
								scrollTicketCostTotal.setViewportView(tblTicketCostTotal);
								tblTicketCostTotal.setTotalLabel(3);
								tblTicketCostTotal.hideColumns("Purpose", // 7
										"Entity ID", // 8
										"Driver ID", // 9
										"Div ID", // 10
										"Dept ID", // 11
										"Charge Acct. ID", // 12
										"Charge Proj. ID", // 13
										"Co ID", // 15
										"BusUnit ID");
								tblTicketCostTotal.packAll();

							}
						}
					}
				}
			}
			{
				JPanel pnlButton = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlButton, BorderLayout.SOUTH);
				pnlButton.setPreferredSize(new Dimension(0, 40));
				{

					{
						JPanel pnlButtonAction = new JPanel(new BorderLayout(3, 3));
						pnlButton.add(pnlButtonAction, BorderLayout.WEST);
						pnlButtonAction.setPreferredSize(new Dimension(200, 25));

						{


							btnPreview = new JButton("Preview");
							pnlButtonAction.add(btnPreview,BorderLayout.WEST);
							btnPreview.addActionListener(this);
							btnPreview.setActionCommand("btnPreview");
							btnPreview.setPreferredSize(new Dimension(150 , 25));

						}
					}
					JPanel pnlButtonAction = new JPanel(new GridLayout(1, 4, 3, 3));
					pnlButton.add(pnlButtonAction, BorderLayout.EAST);
					pnlButtonAction.setPreferredSize(new Dimension(500, 25));
					{

						btnNew = new JButton("New");
						pnlButtonAction.add(btnNew);
						btnNew.addActionListener(this);
						btnNew.setActionCommand("New");
						grpButton.add(btnNew);

						btnEdit = new JButton("Edit");
						pnlButtonAction.add(btnEdit);
						btnEdit.addActionListener(this);
						btnEdit.setActionCommand("Edit");
						grpButton.add(btnEdit);

						btnSave = new JButton("Save");
						pnlButtonAction.add(btnSave);
						btnSave.addActionListener(this);

						btnCancel = new JButton("Cancel");
						pnlButtonAction.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			}
		}

	}



	private void updateCommit(_JScrollPaneTotal scroll,_JTableMain table,JList rowheader) {
		scroll.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(table.getRowCount())));
		table.packAll();

		for (int row = 0; row < table.getRowCount(); row++) {
			((DefaultListModel) rowheader.getModel()).setElementAt(row + 1, row);
		}
	}

	private void computeTotal(DefaultTableModel model,DefaultTableModel modeltotal) {
		BigDecimal  totalgrp = new BigDecimal("0.00");

		for (int x = 0; x < model.getRowCount(); x++) {
			BigDecimal totalamount = (BigDecimal) model.getValueAt(x, 4);

			try {
				totalgrp = totalgrp.add(totalamount);
			} catch (Exception e1) { }
		}


		modeltotal.setValueAt(totalgrp, 0, 4);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(btnNew)) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			NewProcess();
		}

		if (e.getSource().equals(btnEdit)) {
			JButton button = (JButton) e.getSource();
			grpButton.setSelected(button.getModel(), true);
			previewlookup = false;
		}

		if (e.getSource().equals(btnSave)) {
			toSave();

		}

		if (e.getSource().equals(btnCancel)) {


			int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to Clear all fields? ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				Clear();
				formLoad();
			}
		}

		if (e.getSource().equals(btnPreview)) {
			PreviewProcess();
		}



	}

	@Override
	public void lookupPerformed(LookupEvent e) {

		if (e.getSource().equals(lookupCompany)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				co_id = data[0].toString();
				company = data[1].toString();
				company_logo = data[3].toString();

				txtCompany.setText(company);
				lookupCompany.setValue(co_id);
				loadListTicket(modelListTicket, rowListTicket, co_id);

			}
		}

		if (e.getSource().equals(lookupRequestType)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				rplf_type_id = data[0].toString();
				rplf_type_desc = data[1].toString();


				lookupRequestType.setValue(rplf_type_id);
				lblRequestType.setText(formatBracketText(rplf_type_desc));

			}
		}
		if (e.getSource().equals(lookupPayee)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				entity_id = data[0].toString();
				entity_name = data[1].toString();


				lookupPayee.setValue(entity_id);
				lblPayee.setText(formatBracketText(entity_name));

				lookupPayeeType.setLookupSQL(getPayeeType(entity_id));

			}
		}

		if (e.getSource().equals(lookupPayeeType)) {

			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				entity_type_id = data[0].toString();
				entity_type_desc = data[1].toString();


				lookupPayeeType.setValue(entity_type_id);
				lblPayeeType.setText(formatBracketText(entity_type_desc));


			}
		}
	}


	private void getDefaultCompany(){

		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION" ;
		company_logo = "cenq_logo.jpg";

		txtCompany.setText(company);
		lookupCompany.setValue(co_id);

	}

	private void loadListTicket(model_RequestPaymenTrip model, JList rowHeader, String co_id){
		pgSelect db = new pgSelect();
		model.clear();

		db.select("select * from sp_generate_request_paymen_tripping('"+co_id+"')");

		if(db.isNotNull()){

			ArrayList<Object[]> listData = new ArrayList<Object[]>();

			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}

			updateCommit(scrollTicketCostTotal,tblListTicket,rowListTicket);
			computeTotal(modelListTicket,modelTicketCostTotal);
			tblListTicket.packAll();
		}
	}

	//
	private void formLoad(){
		this.setComponentsEnabled(pnlMain, false);
		getDefaultCompany();
		btnState(true, true, false, false,true);
	}

	private void NewProcess(){
		previewlookup = false;
		this.setComponentsEnabled(pnlMain, true);
		loadListTicket(modelListTicket, rowListTicket, co_id);

		btnState(false, false, true, true,true);
	}


	public void btnState(Boolean sNew,Boolean sEdit, Boolean sSave, Boolean sCancel,Boolean sPreview){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
		btnPreview.setEnabled(sPreview);

	}

	private String getRequestType(){
		String SQL = "SELECT rplf_type_id as \"Code\", rplf_type_desc as \"Type\" fROM mf_rplf_type where rplf_type_id in ('01','02','07','11', '06') ";

		return SQL;
	}

	private String getPayee(){
		String SQL = "\n" + 
				"select \n" + 
				"a.entity_id AS \"ID\",\n" + 
				"get_client_name(a.entity_id) as \"Name\"\n" + 
				"from rf_entity a\n" + 
				"left join em_employee b on a.entity_id = b.entity_id\n" + 
				"where status_id = 'A'\n" + 
				"order by \"Name\"";

		return SQL;
	}

	private String getPayeeType(String entity_id){
		String SQL = "select a.entity_type_id as \"ID\", entity_type_desc as \"Type\"\n" + 
				"from rf_entity_assigned_type a\n" + 
				"left join mf_entity_type b ON a.entity_type_id = b.entity_type_id\n" + 
				"where a.entity_id = '"+entity_id+"'\n" + 
				"";

		return SQL;
	}

	private void toSave(){

		if (lookupRequestType.getValue().equals("") || lookupRequestType.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please enter request type.", "Save", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (lookupPayee.getValue().equals("") || lookupPayee.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please enter payee.", "Save", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (lookupPayeeType.getValue().equals("") || lookupPayeeType.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please enter project to be charged.", "Save", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (hasSelected()) {

			if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

				pgSelect db = new pgSelect();

				String rplf_no = getRPLFno(lookupCompany.getValue());
				for (int i = 0; i < modelListTicket.getRowCount(); i++) {
					Boolean isSelected = (Boolean) modelListTicket.getValueAt(i, 0);

					String rplf_type_id = (String) lookupRequestType.getValue();
					String payee = (String) lookupPayee.getValue();
					String payee_type_id = (String) lookupPayeeType.getValue();
					String user_id = (String) UserInfo.EmployeeCode;

					if (isSelected) {
						String ticketNo = (String) modelListTicket.getValueAt(i, 1);
						String coID = (String) modelListTicket.getValueAt(i, 14);
						String busUnitID = (String) modelListTicket.getValueAt(i, 15);
						String purpose = (String) modelListTicket.getValueAt(i, 7);
						String acct_id = (String) modelListTicket.getValueAt(i, 12);
						String driver_code = (String) modelListTicket.getValueAt(i, 9);

						db.select("SELECT sp_save_requestforpayment_tripping(\n" + 
								"'"+coID+"'	        ,---p_co_id character varying,\n" + 
								"'"+busUnitID+"'	,---p_busunit_id character varying,\n" + 
								"'"+rplf_type_id+"' ,---p_rplf_type_id character varying,\n" + 
								"'"+payee+"'        ,---p_payee character varying,\n" + 
								"'"+payee_type_id+"',---p_payee_type_id character varying,\n" + 
								"'"+purpose+"'      ,---p_purpose character varying,\n" + 
								"'"+user_id+"'      ,---p_user_id character varying,\n" + 
								"'"+acct_id+"'      ,---p_acct_id character varying,\n" + 
								"'"+driver_code+"'  ,---p_driver_code character varying,\n" + 
								"'"+ticketNo+"'     ,---p_ticket_no character varying,\n" +
								"'"+rplf_no+"'     ,---p_ticket_no character varying,\n" +
								""+isSelected+"      ---p_selected boolean\n" + 
								");");

						//db.select("SELECT rplf_no from rf_request_detail where ref_doc_no = '"+ticketNo+"' limit 1");


						lblRPLFValue.setText(formatBracketText((String) rplf_no));
					}
				}

				JOptionPane.showMessageDialog(this.getTopLevelAncestor(),"Finished Saving!","Save",JOptionPane.INFORMATION_MESSAGE);
				//Clear();
				//formLoad();
			}

		}else{
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please tag trip tickets first.", "Save", JOptionPane.WARNING_MESSAGE );
			return;
		}
	}


	private Boolean hasSelected() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < modelListTicket.getRowCount(); x++){
			listSelected.add((Boolean) modelListTicket.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}


	private void Clear(){
		previewlookup = true;
		this.setComponentsClear(pnlMain, "");
		this.setComponentsClear(pnlMain, "");
		lblRequestType.setText(formatBracketText(""));
		lblPayee.setText(formatBracketText(""));
		lblPayeeType.setText(formatBracketText(""));

		modelListTicket.clear();
		updateCommit(scrollTicketCostTotal,tblListTicket,rowListTicket);
		computeTotal(modelListTicket, modelTicketCostTotal);
	}

	private void PreviewProcess(){
		String SQL = "";

		if (previewlookup) {
			
			String strSQL = 	"SELECT DISTINCT ON (rplf_no) rplf_no AS \"RPLF No.\",\n" + 
					"get_client_name(requested_by) AS \"Requested by\",\n" + 
					"tripping_date AS \"Tripping Date\"\n" + 
					"FROM rf_tripticket_header \n" + 
					"WHERE rplf_no IS NOT NULL \n" + 
					"ORDER BY rplf_no ";


			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null , "Tripping", strSQL, false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);
			Object[] data = dlg.getReturnDataSet();

			if(data != null){

				SQL = "select tripping_date,\n" + 
						"get_client_name(driver_code) as driver_name,\n" + 
						"total_cost\n" + 
						"from rf_tripticket_header \n" + 
						"where rplf_no = '"+data[0].toString()+"'\n" + 
						"and status_id = 'A'\n" + 
						"order by driver_name";
			}else{
				return;
			}
		}else{
			
			if (hasSelected()) {
				if (grpButton.getSelection().getActionCommand().equals("New") || grpButton.getSelection().getActionCommand().equals("Edit")) {

					for (int i = 0; i < modelListTicket.getRowCount(); i++) {
						Boolean isSelected = (Boolean) modelListTicket.getValueAt(i, 0);


						if (isSelected) {

							String driver_name = (String) modelListTicket.getValueAt(i, 5);
							Timestamp tripping_date = (Timestamp) modelListTicket.getValueAt(i, 2);
							BigDecimal total_cost = (BigDecimal) modelListTicket.getValueAt(i, 4);

							SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +

									"SELECT '"+tripping_date+"' AS tripping_date,\n" + 
									"'"+driver_name+"' AS driver_name,\n"+
									"'"+total_cost+"' AS total_cost,\n"+
									"'' AS rplf_no \n";
						}
					}

					SQL = SQL + "\n order by driver_name,tripping_date";
				}
			}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please tag trip tickets first.", "Preview", JOptionPane.WARNING_MESSAGE );
				return;
			}
		}
			
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("co_id", co_id);
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));

		FncReport.generateReport("/Reports/rptListTrippingDriver.jasper", "List of Driver", mapParameters, SQL);

	}

	public String formatBracketText(String text){

		String format;
		if (text.equals("") || text == null) {
			format = String.format("[ %s ]", "");
		}else{

			format = String.format("[ %s ]", text);
		}

		return format;
	}

	private String getRPLFno(String co_id){
		pgSelect db = new pgSelect();

		//db.select("SELECT TRIM(to_char(max(COALESCE(rplf_no::int,0))+1,'000000000')) FROM rf_request_header WHERE co_id = '"+co_id+"'");
		
		db.select("SELECT fn_get_rplf_no('"+co_id+"')");
		
		return (String) db.Result[0][0];
	}
	
	
}

