package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import tablemodel.modelNewSchedule;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.SpringUtilities;
import components._JTableMain;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class pnlOtherReq_PreviewSchedSearch extends JDialog implements _GUI, ActionListener {


	private static final long serialVersionUID = -820078677048122220L;

	private Dimension size = new Dimension(1000, 600);
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlNorth;

	private JLabel lblRequestNo;
	private _JXTextField txtRequestNo;

	private JLabel lblRequestDate;
	private _JDateChooser dteRequest;

	private JLabel lblReqStatus;
	private _JXTextField txtReqStatus;
	private JLabel lblRequestingClient;
	private _JXTextField txtReqCLient;

	private JLabel lblEntityID;
	private _JXTextField txtEntityID;

	private JLabel lblProject;
	private _JXTextField txtProject;

	private JLabel lblSellingPrice;
	private _JXFormattedTextField txtSellingPrice;

	private JLabel lblDPAmt;
	private _JXFormattedTextField txtDPAmt;

	private JLabel lblCLient;
	private _JXTextField txtEntityName;

	private JLabel lblHousemodel;
	private _JXTextField txtHouseModel;

	private JLabel lblVatAmt;
	private _JXFormattedTextField txtVATAmt;

	private JLabel lblMAAmt;
	private _JXFormattedTextField txtMAAmt;

	private JLabel lblUnitID;
	private _JXTextField txtUnitID;

	private JLabel lblClientClass;
	private _JXTextField txtClientClass;

	private JLabel lblDiscount;
	private _JXFormattedTextField txtDiscount;

	private JLabel lblDPTerms;
	private _JXTextField txtDPTerms;

	private JLabel lblUnitDesc;
	private _JXTextField txtUnitDesc;

	private JLabel lblReqType;
	private _JXTextField txtReqType;

	private JLabel lblNSP;
	private _JXFormattedTextField txtNSP;

	private JLabel lblMATerms;
	private _JXTextField txtMATerms;

	private JPanel pnlCenter;
	private modelNewSchedule modelPrevSched;
	private _JTableMain tblPrevSched;
	private JScrollPane scrollPrevSched;
	private JList rowHeaderPrevSched;

	private JPanel pnlSouth;
	private JButton btnPrintPreview;
	private JButton btnCancel;

	public pnlOtherReq_PreviewSchedSearch() {
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Frame owner) {
		super(owner);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Dialog owner) {
		super(owner);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Window owner) {
		super(owner);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Frame owner, String title, Object [] data3, String req_no, String req_client, String req_stat) {
		super(owner, title);
		initGUI();
		setName(data3);
		setDetails(req_no, data3);
		displaySchedule(req_no);
		setDPMAAmt(req_no);
	}

	public pnlOtherReq_PreviewSchedSearch(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Window owner,
			ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Frame arg0, String arg1, boolean arg2) {
		super(arg0, arg1, arg2);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Dialog owner, String title,
			boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Window owner, String title,
			ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Frame arg0, String arg1,
			boolean arg2, GraphicsConfiguration arg3) {
		super(arg0, arg1, arg2, arg3);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Dialog owner, String title,
			boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public pnlOtherReq_PreviewSchedSearch(Window owner, String title,
			ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setResizable(false);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getRootPane().registerKeyboardAction(this, "Escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new SpringLayout());
			//pnlNorth.setBorder(EMPTY_BORDER);
			pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Request Details"));
			{
				lblRequestNo = new JLabel("Request No.");
				pnlNorth.add(lblRequestNo);
			}
			{
				txtRequestNo = new _JXTextField();
				pnlNorth.add(txtRequestNo);
			}
			{
				lblRequestingClient = new JLabel("Requesting Client");
				pnlNorth.add(lblRequestingClient);
			}
			{
				txtReqCLient = new _JXTextField();
				pnlNorth.add(txtReqCLient);
			}
			{
				lblReqStatus = new JLabel("Req. Status");
				pnlNorth.add(lblReqStatus);
			}
			{
				txtReqStatus = new _JXTextField();
				pnlNorth.add(txtReqStatus);
			}
			{
				lblRequestDate = new JLabel("Req. Date");
				pnlNorth.add(lblRequestDate);
			}
			{
				dteRequest = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
				pnlNorth.add(dteRequest);
				//dteRequest.setPreferredSize(new Dimension(100, 0));
				dteRequest.getCalendarButton().setVisible(false);
				dteRequest.setEditable(false);
			}
			{
				lblEntityID = new JLabel("Entity ID");
				pnlNorth.add(lblEntityID);
			}
			{
				txtEntityID = new _JXTextField();
				pnlNorth.add(txtEntityID, BorderLayout.WEST);
			}
			{
				lblProject = new JLabel("Project");
				pnlNorth.add(lblProject);
			}
			{
				txtProject = new _JXTextField();
				pnlNorth.add(txtProject);
			}
			{
				lblSellingPrice = new JLabel("Selling Price");
				pnlNorth.add(lblSellingPrice);
			}
			{
				txtSellingPrice = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
				pnlNorth.add(txtSellingPrice);
				txtSellingPrice.setFormatterFactory(_JXFormattedTextField.DECIMAL);
				txtSellingPrice.setEditable(false);
			}
			{
				lblDPAmt = new JLabel("DP Amt");
				pnlNorth.add(lblDPAmt);
			}
			{
				txtDPAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
				pnlNorth.add(txtDPAmt);
				txtDPAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
				txtDPAmt.setEditable(false);
			}
			{
				lblCLient = new JLabel("Client Name");
				pnlNorth.add(lblCLient);
			}
			{
				txtEntityName = new _JXTextField();
				pnlNorth.add(txtEntityName);
			}
			{
				lblHousemodel = new JLabel("House Model");
				pnlNorth.add(lblHousemodel);
			}
			{
				txtHouseModel = new _JXTextField();
				pnlNorth.add(txtHouseModel);
			}
			{
				lblVatAmt = new JLabel("VAT");
				pnlNorth.add(lblVatAmt);
			}
			{
				txtVATAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
				pnlNorth.add(txtVATAmt);
				txtVATAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
				txtVATAmt.setEditable(false);
			}
			{
				lblMAAmt = new JLabel("MA Amt.");
				pnlNorth.add(lblMAAmt);
			}
			{
				txtMAAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
				pnlNorth.add(txtMAAmt);
				txtMAAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
				txtMAAmt.setEditable(false);
			}
			{
				lblUnitID = new JLabel("Unit ID");
				pnlNorth.add(lblUnitID);
			}
			{
				txtUnitID = new _JXTextField();
				pnlNorth.add(txtUnitID);
			}
			{
				lblClientClass = new JLabel("Client Class");
				pnlNorth.add(lblClientClass);
			}
			{
				txtClientClass = new _JXTextField();
				pnlNorth.add(txtClientClass);
			}
			{
				lblDiscount = new JLabel("Discount");
				pnlNorth.add(lblDiscount);
			}
			{
				txtDiscount = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
				pnlNorth.add(txtDiscount);
				txtDiscount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
				txtDiscount.setEditable(false);
			}
			{
				lblDPTerms = new JLabel("DP Terms");
				pnlNorth.add(lblDPTerms);
			}
			{
				txtDPTerms = new _JXTextField();
				pnlNorth.add(txtDPTerms);
				txtDPTerms.setHorizontalAlignment(JXTextField.CENTER);
			}
			{
				lblUnitDesc = new JLabel("PBL");
				pnlNorth.add(lblUnitDesc);
			}
			{
				txtUnitDesc = new _JXTextField();
				pnlNorth.add(txtUnitDesc);
			}
			{
				lblReqType = new JLabel("Req. Type");
				pnlNorth.add(lblReqType);
			}
			{
				txtReqType = new _JXTextField();
				pnlNorth.add(txtReqType);
			}
			{
				lblNSP = new JLabel("NSP");
				pnlNorth.add(lblNSP);
			}
			{
				txtNSP = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
				pnlNorth.add(txtNSP);
				txtNSP.setFormatterFactory(_JXFormattedTextField.DECIMAL);
				txtNSP.setEditable(false);
			}
			{
				lblMATerms = new JLabel("MA Terms");
				pnlNorth.add(lblMATerms);
			}
			{
				txtMATerms = new _JXTextField();
				pnlNorth.add(txtMATerms);
				txtMATerms.setHorizontalAlignment(JXTextField.CENTER);
			}

			//SpringUtilities.makeCompactGrid(pnlNorth, 4, 8, 1, 1, 1, 1, false);
			SpringUtilities.makeCompactGrid(pnlNorth, 5, 8, 5, 5, 5, 5, false);
		}
		{
			pnlCenter = new JPanel(new BorderLayout(5, 5));
			this.add(pnlCenter, BorderLayout.CENTER);
			{
				scrollPrevSched = new JScrollPane();
				pnlCenter.add(scrollPrevSched, BorderLayout.CENTER);
				scrollPrevSched.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollPrevSched.setBorder(EMPTY_BORDER);
				{
					modelPrevSched = new modelNewSchedule();
					tblPrevSched = new _JTableMain(modelPrevSched);
					scrollPrevSched.setViewportView(tblPrevSched);
					tblPrevSched.hideColumns("Part ID","Int. Rate");
					
					modelPrevSched.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {

							((DefaultListModel)rowHeaderPrevSched.getModel()).addElement(modelPrevSched.getRowCount());

							if(modelPrevSched.getRowCount() == 0){
								rowHeaderPrevSched.setModel(new DefaultListModel());
							}
						}
					});
				}
				{
					rowHeaderPrevSched = tblPrevSched.getRowHeader();
					rowHeaderPrevSched.setModel(new DefaultListModel());
					scrollPrevSched.setRowHeaderView(rowHeaderPrevSched);
					scrollPrevSched.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}
		{
			pnlSouth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new GridLayout(1, 2, 5, 5));
			pnlSouth.setBorder(EMPTY_BORDER);
			{
				btnPrintPreview = new JButton("Print Preview");
				pnlSouth.add(btnPrintPreview);
				btnPrintPreview.setActionCommand(btnPrintPreview.getText());
				btnPrintPreview.setMnemonic(KeyEvent.VK_R);
				btnPrintPreview.addActionListener(this);
			}
			{
				btnCancel = new JButton("Cancel");
				pnlSouth.add(btnCancel);
				btnCancel.setActionCommand(btnCancel.getText());
				btnCancel.setMnemonic(KeyEvent.VK_C);
				btnCancel.addActionListener(this);
			}
		}
	}

	public void setName(Object []data3){//SETS THE TITLE OF THIS PANEL BASED ON THE OLD ENTITY ID
		String old_entity_name = (String) data3[31];
		//lblClientName.setText(old_entity_name);
		this.setTitle("Preview Schedule - ("+old_entity_name+")");
	}

	private void setDetails(String req_no, Object []data3){//SET THE DETAILS FOR THIS PANEL

		pgSelect db = new pgSelect();
		
		String sql = "select trim(a.request_no), trim(c.req_status_desc), \n"+
					 "trim(a.request_by), a.request_date,\n" + 
					 "trim(a.new_entity_id), trim(get_client_name(a.new_entity_id)),\n" + 
					 "trim(a.new_unit_id), trim(get_unit_description(a.new_unit_id)),\n" + 
					 "get_project_name(a.new_proj_id), get_model_desc(a.new_model),\n" + 
					 "trim(b.type_desc), a.new_sprice, a.new_disc, a.new_sprice - a.new_disc, \n" + 
					 "a.new_dp_amount, a.new_availed, a.new_dp_term, a.new_ma_term \n" + 
					 "from req_rt_request_header a\n" + 
					 "left join mf_buyer_type b on b.type_id = a.new_entity_class \n"+
					 "left join mf_request_status c on c.req_status_id = a.request_status \n" + 
					 "where a.request_no = '"+req_no+"';";
		db.select(sql);
		
		FncSystem.out("Display Schedule Searched", sql);
		txtRequestNo.setText(req_no);
		txtReqStatus.setText((String) db.getResult()[0][1]);
		txtReqCLient.setText((String) db.getResult()[0][2]);
		dteRequest.setDate((Date) db.getResult()[0][3]);
		txtEntityID.setText((String) db.getResult()[0][4]);
		txtEntityName.setText((String) db.getResult()[0][5]);
		txtUnitID.setText((String) db.getResult()[0][6]);
		txtUnitDesc.setText((String) db.getResult()[0][7]);
		txtProject.setText((String) db.getResult()[0][8]);
		txtHouseModel.setText((String) db.getResult()[0][9]);
		txtClientClass.setText((String) db.getResult()[0][10]);
		txtSellingPrice.setValue(db.getResult()[0][11]);
		txtDiscount.setValue(db.getResult()[0][12]);
		txtNSP.setValue(db.getResult()[0][13]);
		txtDPTerms.setText(db.getResult()[0][16].toString());
		txtMATerms.setText(db.getResult()[0][17].toString());
		
		BigDecimal vat_rate = (BigDecimal) data3[14];
		BigDecimal vat_amt = (BigDecimal) data3[13];
		txtVATAmt.setValue(vat_amt);
	
		//_OtherRequest2.setNewVa
		
		//txtReqType.setText((String) db.getResult()[0][5]);
	}

	public void displaySchedule(String req_no){//DISPLAYS THE NEW SCHEDULE BASED ON THE REQUEST NO
		pgSelect db = new pgSelect();
		String sql = "select a.part_id, b.part_desc, a.scheddate, a.amount, \n" + 
					 "coalesce(a.proc_fee, 0.00), coalesce(a.mri, 0.00),coalesce(a.fire, 0.00), \n"+
					 "coalesce(a.interest, 0.00), a.principal,\n" + 
					 "coalesce(a.vat, 0.00), a.balance, coalesce(a.interest_rate, 0.00)\n" + 
					 "from rf_req_client_schedule a\n" + 
					 "left join mf_client_ledger_part b on b.part_id = a.part_id\n" + 
					 "where a.request_no = '"+req_no+"'\n" + 
					 "order by a.part_id, a.scheddate";
		db.select(sql);

		FncSystem.out("Display Schedule", sql);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelPrevSched.addRow(db.getResult()[x]);
			}
			scrollPrevSched.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPrevSched.getRowCount())));
			tblPrevSched.packAll();
		}
	}
	
	public void setDPMAAmt(String req_no){
		pgSelect db = new pgSelect();
		String sqlDP = "select coalesce(amount, 0.00) from rf_req_client_schedule where request_no = '"+req_no+"' and part_id = '013' LIMIT 1\n";
		db.select(sqlDP);
		FncSystem.out("Display DP Amt", sqlDP);
		txtDPAmt.setValue(db.getResult()[0][0]);
		String sqlMA = "select coalesce(amount, 0.00) from rf_req_client_schedule where request_no = '"+req_no+"' and part_id = '014' LIMIT 1\n";
		db.select(sqlMA);
		FncSystem.out("Display MA Amt", sqlMA);
		txtMAAmt.setValue(db.getResult()[0][0]);
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		if(actionCommand.equals("Print Preview")){//PREVIEW OF THE REQUEST 

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("entity_id", txtEntityID.getText()); //Entity ID
			mapParameters.put("entity_name", txtEntityName.getText()); //Entity name
			mapParameters.put("unit_id", txtUnitID.getText()); //Unit ID
			mapParameters.put("unit_desc", txtUnitDesc.getText()); //Unit Desc
			mapParameters.put("project", txtProject.getText()); //Project
			mapParameters.put("house_model", txtHouseModel.getText()); //House Model
			mapParameters.put("client_class", txtClientClass.getText()); //Client Class
			mapParameters.put("req_type", txtReqType.getText()); //Req. Type
			mapParameters.put("selling_price",  txtSellingPrice.getValued()); //Selling Price
			mapParameters.put("vat", (BigDecimal) txtVATAmt.getValued()); //VAT
			mapParameters.put("discount", (BigDecimal) txtDiscount.getValued()); //Discount
			mapParameters.put("nsp", (BigDecimal) txtNSP.getValued()); //NSP
			mapParameters.put("dp_amt", (BigDecimal) txtDPAmt.getValued()); // DP Amt;
			mapParameters.put("ma_amt", (BigDecimal) txtMAAmt.getValued()); //MA Amt
			mapParameters.put("dp_terms", txtDPTerms.getText()); //DP Terms
			mapParameters.put("ma_terms", txtMATerms.getText()); //MA Terms
			mapParameters.put("connection", FncGlobal.connection);
			mapParameters.put("request_no", txtRequestNo.getText());

			this.dispose();

			FncReport.generateReport("/Reports/rptPreviewSchedule.jasper", "Preview Schedule", mapParameters);
			
			System.out.println("***************Dumaan dito");
		}
		if(actionCommand.equals("Cancel")){
			if(JOptionPane.showConfirmDialog(pnlOtherReq_PreviewSchedSearch.this, "Are you sure you want to cancel?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				this.dispose();
			}
		}

	}
}
