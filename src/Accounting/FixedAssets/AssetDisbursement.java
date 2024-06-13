package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXFormattedTextField;

import Buyers.ClientServicing.TagClientMailsForExport;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JTagLabel;
import interfaces._GUI;
import net.sf.jasperreports.web.actions.Action;
import net.sf.jasperreports.web.actions.ActionException;
import tablemodel.modelPrintAssetSticker;
import tablemodel.modelassetdisbursement;

public class AssetDisbursement extends _JInternalFrame implements _GUI, ActionListener, Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String title="Asset Disbursement Module";
	public static Dimension framesize= new Dimension(700,500);

	private JPanel pnlmain;

	private JPanel pnlsouth;

	private static _JLookup lookupcompany;

	private static _JTagLabel tagCompany;

	private static _JLookup lookupreqno;

	private static _JLookup lookuppvno;

	private static _JDateChooser req_date;

	private static JXFormattedTextField txtamount;

	private static JTextField txtparticular;

	private static JTextField txtpayee;

	private static JTextField txtstatus;

	private static JButton btnaddnew;

	private static JButton btnsave;

	private static JButton btncancel;

	private static JButton btnedit;

	private static _JLookup lookupassetno;

	private static JButton btnpreview;

	static String co_id;

	private String company;

	private static _JLookup lookuppayee;

	private JScrollPane scrollassetdisbursement;

	private modelassetdisbursement modelasset_disbursement;

	private _JTableMain tblasset_disbursement;

	private JList rowheaderasset_disbursement;

	static String rplf_no = "";

	private static String request_id;

	private static String rec_id;

	private static String asset_no;
	
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 8);

	public AssetDisbursement() {
		// TODO Auto-generated constructor stub
		super(title, false, true, true, true);
		initGUI();
	}

	public AssetDisbursement(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public AssetDisbursement(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean requiresRefill() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void run() throws ActionException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(framesize );
		this.setPreferredSize(framesize );
		this.setLayout(new BorderLayout());
		{
			pnlmain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlmain);
			pnlmain.setBorder(_EMPTY_BORDER);
			{
				JPanel pnlnorth = new JPanel(new BorderLayout(5, 5));
				pnlmain.add(pnlnorth, BorderLayout.NORTH);
				pnlnorth.setPreferredSize(new Dimension(0, 60));
				pnlnorth.setBorder(JTBorderFactory.createTitleBorder("Select Company"));
				//pnlnorth.setBorder(LINE_BORDER);
				{
					JLabel lblcompany = new JLabel("Company");
					pnlnorth.add(lblcompany, BorderLayout.WEST);
					lblcompany.setPreferredSize(new Dimension(60, 0));
				}
				{
					lookupcompany = new _JLookup();
					pnlnorth.add(lookupcompany, BorderLayout.CENTER);
					lookupcompany.setLookupSQL(SQL_COMPANY());
					lookupcompany.setReturnColumn(0);
					lookupcompany.addLookupListener(new LookupListener() {

						@Override
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								co_id 		= (String) data[0];	
								company		= (String) data[1];					
								tagCompany.setTag(company);
								
							}
						}
					});
				{
					JPanel pnlnorth_east = new JPanel(new BorderLayout(5, 5));
					pnlnorth.add(pnlnorth_east, BorderLayout.EAST);
					pnlnorth_east.setPreferredSize(new Dimension(570, 0));
					{
						tagCompany = new _JTagLabel("[ ]");
						pnlnorth_east.add(tagCompany, BorderLayout.WEST);
						tagCompany.setPreferredSize(new Dimension(350, 0));
						//tagCompany.setBorder(_LINE_BORDER);
					}
					{
						JLabel lblstatus = new JLabel("Status", JLabel.TRAILING);
						pnlnorth_east.add(lblstatus, BorderLayout.CENTER);
					}
					{
						txtstatus = new JTextField();
						pnlnorth_east.add(txtstatus, BorderLayout.EAST);
						txtstatus.setPreferredSize(new Dimension(150, 0));
						txtstatus.setEditable(false);
					}
				}
			}
			{
				JPanel pnlcenter = new JPanel(new BorderLayout(5, 5));
				pnlmain.add(pnlcenter, BorderLayout.CENTER);
				{
					JPanel pnlcenter1 = new JPanel(new BorderLayout(5, 5));
					pnlcenter.add(pnlcenter1, BorderLayout.NORTH);
					pnlcenter1.setPreferredSize(new Dimension(0, 200));
					pnlcenter1.setBorder(JTBorderFactory.createTitleBorder("Details"));
					{
						JPanel pnlcenter1_west = new JPanel(new BorderLayout(5, 5));
						pnlcenter1.add(pnlcenter1_west, BorderLayout.WEST);
						pnlcenter1_west.setPreferredSize(new Dimension(300, 0));
						//pnlcenter1_west.setBorder(_LINE_BORDER);
						{
							//JPanel pnlleft = new JPanel(new GridLayout(4, 2, 5, 5));
							JPanel pnlleft = new JPanel(new GridLayout(4, 1, 5, 5)); 
							pnlcenter1_west.add(pnlleft);
							{
								JPanel pnlreqno = new JPanel(new BorderLayout(5, 5));
								pnlleft.add(pnlreqno);
								{
									JLabel lblreqno = new JLabel("Request No.");
									pnlreqno.add(lblreqno, BorderLayout.WEST);
									lblreqno.setPreferredSize(new Dimension(100, 0));
								}
								{
									lookupreqno = new _JLookup();
									pnlreqno.add(lookupreqno, BorderLayout.CENTER);
									
								}
							}
							{
								JPanel pnlreqdate = new JPanel(new BorderLayout(5, 5));
								pnlleft.add(pnlreqdate);
								{
									JLabel lbldatereq = new JLabel("Date Request.");
									pnlreqdate.add(lbldatereq, BorderLayout.WEST);
									lbldatereq.setPreferredSize(new Dimension(100, 0));
								}
								{
									req_date= new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
									pnlreqdate.add(req_date, BorderLayout.CENTER);
									req_date.setDate(null);
									req_date.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									req_date.setEditable(false);
									req_date.setEnabled(false);
								}
							}
							{
								JPanel pnlpvno = new JPanel(new BorderLayout(5, 5));
								pnlleft.add(pnlpvno);
								{
									JLabel lblpv_no = new JLabel("PV No.");
									pnlpvno.add(lblpv_no, BorderLayout.WEST);
									lblpv_no.setPreferredSize(new Dimension(100, 0));
								}
								{
									lookuppvno = new _JLookup();
									pnlpvno.add(lookuppvno, BorderLayout.CENTER);
									lookuppvno.setEditable(false);
								}
							}
						}
					}
					{
						JPanel pnlcenter1_center = new JPanel(new BorderLayout(5, 5));
						pnlcenter1.add(pnlcenter1_center, BorderLayout.CENTER);
						//pnlcenter1_center.setBorder(_LINE_BORDER);
						{
							JPanel pnlright = new JPanel(new GridLayout(4, 2, 5, 5)); 
							pnlcenter1_center.add(pnlright);
							
							{
								JLabel lblamount = new JLabel("Amount", JLabel.TRAILING);
								pnlright.add(lblamount);
							}
							{
								txtamount = new JXFormattedTextField();
								pnlright.add(txtamount);
								txtamount.setEditable(false);
								txtamount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							}
							{
								JLabel lblpayee = new JLabel("Payee", JLabel.TRAILING);
								pnlright.add(lblpayee);
							}
							{
								lookuppayee = new _JLookup();
								pnlright.add(lookuppayee);
								lookuppayee.setReturnColumn(3);
								//lookuppayee.setEditable(false);
								lookuppayee.setLookupSQL(getEntityList());
								lookuppayee.setEditable(false);
								lookuppayee.addLookupListener(new LookupListener() {
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											String entity_name = (String) data[4];	
											
											txtpayee.setText(entity_name);
											
										}
									}
								});
							}
							{
								JLabel lbl = new JLabel("");
								pnlright.add(lbl);
							}
							{
								txtpayee = new JTextField();
								pnlright.add(txtpayee);
								txtpayee.setFont(dialog11Bold);
								txtpayee.setEditable(false);
							}
							{
								JLabel lblassetno = new JLabel("Asset No.", JLabel.TRAILING);
								pnlright.add(lblassetno);
							}
							{
								//txtassetno = new JTextField();
								lookupassetno = new _JLookup();
								pnlright.add(lookupassetno);
								lookupassetno.setReturnColumn(0);
								lookupassetno.setLookupSQL(sql_asset());
								lookupassetno.setEditable(false);
								lookupassetno.addLookupListener(new LookupListener() {
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											
											
										}
									}
								});
								
							}
							/*{
								JLabel lbl = new JLabel("");
								pnlright.add(lbl);
							}*/
						}
					}
					{
						JPanel pnlparticular = new JPanel(new BorderLayout(5,5));
						pnlcenter1.add(pnlparticular, BorderLayout.SOUTH);
						pnlparticular.setPreferredSize(new Dimension(0, 40));
						{
							JLabel lblparticular = new JLabel("Particular");
							pnlparticular.add(lblparticular, BorderLayout.WEST);
							lblparticular.setPreferredSize(new Dimension(100, 0));
						}
						{
							txtparticular = new JTextField();
							pnlparticular.add(txtparticular, BorderLayout.CENTER);
							txtparticular.setEditable(false);
						}
					}
				}
				{
					JPanel pnlcenter2 = new JPanel(new BorderLayout(5, 5));
					pnlcenter.add(pnlcenter2, BorderLayout.CENTER);
					pnlcenter2.setBorder(JTBorderFactory.createTitleBorder("Disbursement Listing"));
					{
						scrollassetdisbursement = new JScrollPane();
						pnlcenter2.add(scrollassetdisbursement);
						scrollassetdisbursement.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
						{
							modelasset_disbursement = new modelassetdisbursement();
							tblasset_disbursement = new _JTableMain(modelasset_disbursement);
							scrollassetdisbursement.setViewportView(tblasset_disbursement);
							modelasset_disbursement .setEditable(false);
							tblasset_disbursement.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								@Override
								public void valueChanged(ListSelectionEvent e) {
									if(!e.getValueIsAdjusting()){
										try {
											int row = tblasset_disbursement.getSelectedRow();
												request_id=    (String) modelasset_disbursement.getValueAt(row, 0);
												asset_no=    (String) modelasset_disbursement.getValueAt(row, 1);
											
											displayrequest_detail();
											enable_buttons(true, false, true, true);
											System.out.println("");
											System.out.println("asset_no: "+asset_no);
											
										}catch( ArrayIndexOutOfBoundsException ex ) {
											
										}
									}
								}
							});
							tblasset_disbursement.getTableHeader().setEnabled(false);
							tblasset_disbursement.getColumnModel().getColumn(0).setPreferredWidth(100);
							tblasset_disbursement.getColumnModel().getColumn(0).setPreferredWidth(100);
							tblasset_disbursement.getColumnModel().getColumn(0).setPreferredWidth(150);
							tblasset_disbursement.getColumnModel().getColumn(0).setPreferredWidth(200);
							tblasset_disbursement.getColumnModel().getColumn(0).setPreferredWidth(100);
						}
						{
							rowheaderasset_disbursement = tblasset_disbursement.getRowHeader();
							scrollassetdisbursement.setRowHeaderView(rowheaderasset_disbursement);
							scrollassetdisbursement.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
			}
			{
				JPanel pnlsouth = new JPanel(new GridLayout(1, 4, 5, 5));
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 50));
				{
					btnaddnew = new JButton("Addnew");
					btnaddnew.setActionCommand("Addnew");
					pnlsouth.add(btnaddnew);
					btnaddnew.setEnabled(false);
					btnaddnew.addActionListener(this);
				}
				{
					btnsave = new JButton("Save"); 
					btnsave.setActionCommand("Save");
					pnlsouth.add(btnsave);
					btnsave.setEnabled(false);
					btnsave.addActionListener(this);
				}
				/*{
					btnedit = new JButton("Edit");
					pnlsouth.add(btnedit);
				}*/
				{
					btncancel = new JButton("Cancel"); 
					btncancel.setActionCommand("Cancel");
					pnlsouth.add(btncancel);
					btncancel.setEnabled(false);
					btncancel.addActionListener(this);
				}
				{
					btnpreview = new JButton("Preview"); 
					btnpreview.setActionCommand("Preview");
					pnlsouth.add(btnpreview);
					btnpreview.setEnabled(false);
					btnpreview.addActionListener(this);
				}
			}
		}
	}
	displayAllrequests();
	enable_buttons(true, false, true, false);	
	
}	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Addnew")){
			lookupcompany.setValue("");
			tagCompany.setTag("");
			txtstatus.setText("");
			lookupreqno.setValue("");
			req_date.setDate(null);
			lookuppvno.setValue("");
			txtamount.setText("");
			lookuppayee.setValue("");
			txtpayee.setText("");
			lookupassetno.setValue("");
			txtparticular.setText("");
			req_date.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			
			txtamount.setEditable(true);
			lookuppayee.setEditable(true);
			lookupassetno.setEditable(true);
			txtparticular.setEditable(true);
			req_date.setEditable(true);
			req_date.setEnabled(true);
			enable_buttons(false, true, true, false);
			
			
			
		}
		
		if(e.getActionCommand().equals("Save")){	
			
			pgUpdate db = new pgUpdate();	
			rplf_no = sql_getNextRPLFno();
			
			insertassetdisbursement(db);
			insertRPLF_header(db);
			insertRPLF_detail(db);
			inserttbl_history(db);
			
			db.commit();
			JOptionPane.showMessageDialog(getContentPane(),"New Asset Disbursement saved.","Information",JOptionPane.INFORMATION_MESSAGE);
			enable_buttons(true, false, true, false);
			displayAllrequests();
			
		}
		
		if(e.getActionCommand().equals("Cancel")){	
			
			lookupcompany.setValue("");
			tagCompany.setTag("");
			txtstatus.setText("");
			lookupreqno.setValue("");
			req_date.setDate(null);
			lookuppvno.setValue("");
			txtamount.setText("0.00");
			lookuppayee.setValue("");
			txtpayee.setText("");
			lookupassetno.setValue("");
			txtparticular.setText("");
			req_date.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			
			txtamount.setEditable(false);
			lookuppayee.setEditable(false);
			lookupassetno.setEditable(false);
			txtparticular.setEditable(false);
			req_date.setEditable(false);
			req_date.setEnabled(false);
			enable_buttons(true, false, true, false);
			displayAllrequests();
			
		}
		
		if(e.getActionCommand().equals("Preview")){	
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("co_id",lookupcompany.getValue());
			mapParameters.put("request_id",request_id);
			mapParameters.put("asset_no",asset_no);
			mapParameters.put("co_name",tagCompany.getText());
			
			System.out.println("");
			System.out.println("co_id: "+lookupcompany.getValue());
			System.out.println("request_id: "+request_id);
			System.out.println("asset_no: "+asset_no);
			System.out.println("co_name: "+tagCompany.getText());
			FncReport.generateReport("/Reports/rptAssetDisbursement.jasper", "Asset Disbursement", mapParameters);
		}
	}
	
	private static void enable_buttons(Boolean add, Boolean save, Boolean cancel, Boolean preview ) {
		btnaddnew.setEnabled(add);
		btnsave.setEnabled(save);
		btncancel.setEnabled(cancel);
		btnpreview.setEnabled(preview);
	}
	
	private void displayAllrequests() {
		modelasset_disbursement.clear();
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderasset_disbursement.setModel(listModel);//Setting of listModel into rowHeader.

		String strSQL = 
				"select \n" + 
				"a.request_id,\n" + 
				//"a.rec_id::varchar,\n" + 
				"a.asset_no,\n" + 
				"a.rplf_no,\n" + 
				"b.asset_name,\n" + 
				"a.date_created::Date,\n" + 
				"a.rec_id\n" + 
				"from rf_asset_disbursement a\n" + 
				"left join tbl_asset b on a.asset_no::int = b.asset_no\n" + 
				"left join em_employee c on a.entity_id = c.entity_id\n" + 
				"left join mf_department d on c.dept_code = d.dept_code\n" + 
				"left join rf_entity e on a.entity_id = e.entity_id\n" + 
				"where a.status_id = 'A' ";
				
				FncSystem.out("Display All request", strSQL);

			pgSelect db = new pgSelect();
			db.select(strSQL);
		
			if(db.isNotNull()){
				for(int x=0; x < db.getRowCount(); x++){
		
					//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
					modelasset_disbursement.addRow(db.getResult()[x]);
		
					//For every row added in model, the table header will also add the row number.
					listModel.addElement(modelasset_disbursement.getRowCount());
				}
			}
			
			
			tblasset_disbursement.packAll();
	}
	
	private static void displayrequest_detail() {
		
		String sql = "select \n" + 
				//"a.request_id,\n"+
				"a.rplf_no,\n" + 
				"a.date_created::Date,\n" + 
				"'',\n" + 
				"to_char(a.amount, 'FM999,999,999.00'),\n" + 
				"a.entity_id,\n" + 
				"e.entity_name,\n" + 
				"a.asset_no,\n" + 
				"a.particular,\n" + 
				"a.co_id, \n" + 
				"f.company_name \n" + 
				"from rf_asset_disbursement a\n" + 
				"left join tbl_asset b on a.asset_no::int = b.asset_no\n" + 
				"left join em_employee c on a.entity_id = c.entity_id\n" + 
				"left join mf_department d on c.dept_code = d.dept_code\n" + 
				"left join rf_entity e on a.entity_id = e.entity_id \n" + 
				"left join mf_company f on a.co_id = f.co_id \n" + 
				"where a.status_id = 'A'\n" + 
				"and a.request_id = '"+request_id+"'\n" + 
				"and a.asset_no = '"+asset_no+"'";
				//"and a.co_id = '"+lookupcompany.getValue()+"'";
		
		FncSystem.out("display request Detail", sql);
		pgSelect db = new pgSelect();
		db.select(sql);

		if(db.isNotNull()){
			lookupreqno.setText((String) db.getResult()[0][0]);
			req_date.setDate( (Date) db.getResult()[0][1]);
			lookuppvno.setText( (String) db.getResult()[0][2]);
			txtamount.setText((String) db.getResult()[0][3]);
			lookuppayee.setText((String) db.getResult()[0][4]);
			txtpayee.setText((String) db.getResult()[0][5]);
			lookupassetno.setText((String) db.getResult()[0][6]);
			txtparticular.setText((String) db.getResult()[0][7]);
			lookupcompany.setText((String) db.getResult()[0][8]);
			tagCompany.setText((String) db.getResult()[0][9]);
		}
	}
	
	private String sql_getNextRPLFno() {//EDITED BY JED 2021-05-17 : CHANGE THE GENERATION OF RPLF INSIDE THE FUNCTION

		String rplf = "";
//		String SQL = 
//				"select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+co_id+"' " ;
		String SQL = 
				"select * from fn_get_rplf_no('"+lookupcompany.getValue()+"')" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {rplf = "000000001";}
			else {rplf = (String) db.getResult()[0][0]; }
		}else{
			rplf = "000000001";
		}

		return rplf;
	}
	
	private void inserttbl_history (pgUpdate db) {
		
		Object[] asset = getassetDtls(lookupassetno.getValue());
		String asset_code = (String) asset[0];
		String current_cust = (String) asset[1];
		String reason = (String) asset[4];
		String remarks = (String) asset[5];
		String move_no = (String) asset[7];
		
		
		String sql="INSERT INTO tbl_asset_history( \n" +
				"asset_code, " +
				"prev_cust, " +
				"current_cust, " +
				"trans_date, " +
				"reason, " +
				"remarks, \n" +
				"status, " +
				"move_no, " +
				"asset_no, " +
				"trans_by,\n"+
				"dept_code) \n" +
				"values(" +
				" '"+asset_code+"',\n"+
				" '"+current_cust+"',\n"+
				" '"+current_cust+"',\n"+
				" now()::date,\n"+
				" '"+reason+"',\n"+
				" '"+remarks+"',\n"+
				" 'A',\n"+
				" '"+move_no+"',\n"+
				" '"+lookupassetno.getValue()+"',\n"+
				" '"+UserInfo.EmployeeCode+"',\n"+
				" ''\n"+
				") \n";
		
		System.out.println("");
		System.out.println("inserttbl_history: "+sql);
		db.executeUpdate(sql, false);
		
	}
	
	private static Object [] getassetDtls(String asset_no) {

		//deduction_amt = (Double.valueOf(txtDAmount.getText().trim().replace(",","")).doubleValue());

		String strSQL = 
				"select \n" + 
				"asset_code,\n" + 
				"current_cust::varchar,\n" + 
				"current_cust::varchar,\n" + 
				"now()::Date,\n" + 
				"'Asset Disbursement' as reason,\n" + 
				"'Rplf No. '|| '"+rplf_no+"' as remakrs,\n" + 
				"'A',\n" + 
				"( select trim(to_char(max(move_no) + 1, '0000000000')) from tbl_asset_history ) as move_no,\n" + 
				"asset_no,\n" + 
				"'"+UserInfo.EmployeeCode+"' as trans_by,\n" + 
				"null,\n" + 
				"null,\n" + 
				"null,\n" + 
				"null,\n" + 
				"null\n" + 
				"from tbl_asset \n" + 
				"where status = 'A'\n" + 
				"and asset_no = '"+asset_no+"'";


		pgSelect db = new pgSelect();
		db.select(strSQL);		

		FncSystem.out("Display SQL for Contract Details", strSQL);

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	
	private void insertRPLF_header(pgUpdate db){
		
		//int rw = tblProgBillMain.getModel().getRowCount()-1;
		//String billingremarks = "";
		//billingremarks = modelProgBillMain.getValueAt(rw,15).toString();

		Date date_liq_ext	= null;
		String rplf_type_id = "";
		String entity_id1	= "";
		String ent_type_id 	= "";
		String sd_no		= "";
		String doc_id		= "";
		Integer proc_id		= null;	
		String branch_id	= "";	
		String justif		= "";	
		String status_id	= "";	
		String created_by	= "";	
		String edited_by	= "";	
		String pay_type_id	=""; //(txtamount.getText() > 2000.00 ? "B":"A");	
		Date edited_date	= null;					

		date_liq_ext= null;
		rplf_type_id= "01";//check payment
		entity_id1	= lookuppayee.getValue().trim();
		ent_type_id = FncGlobal.GetString("select entity_type_id from rf_entity_assigned_type where entity_id = '"+entity_id1+"' and status_id = 'A'");
		sd_no		= null;
		doc_id		= "09";//Request for payment
		proc_id		= 1;	
		branch_id	= null;	
		justif		= null;	
		status_id	= "A";	
		created_by	= UserInfo.EmployeeCode;	
		edited_by	= "";	
		edited_date	= null;		
		

		String sqlDetail = 
				"INSERT into rf_request_header values ( \n" +
						"'"+lookupcompany.getValue()+"',  \n" +  		//1
						"'"+lookupcompany.getValue()+"',  \n" +		//2
						"'"+rplf_no+"',  \n" +		//3
						"now(),  \n" +				//4
						"'"+req_date.getDate()+"',  \n" + //5
						""+date_liq_ext+",  \n" + 	//6
						"'"+rplf_type_id+"' , \n" +	//7
						"'"+entity_id1+"',  \n" +	//8
						"'"+entity_id1+"',  \n" +	//9
						"'"+ent_type_id+"',  \n" +	//10
						""+sd_no+",  \n" +			//11
						"'"+doc_id+"' , \n" +		//12
						""+proc_id+",  \n" +		//13
						""+branch_id+" , \n" +		//14
						""+justif+",  \n" +			//15
						" '', \n"+	//16
						"'"+status_id+"' , \n" +	//17
						"'"+created_by+"',  \n" +	//18
						"'"+req_date.getDate()+"' , \n" +	//19
						"'"+edited_by+"' , \n" +	//20
						""+edited_date+", \n" +		//21	
						"'B', \n" +				    //22
						"null " +				    //23
						")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void insertRPLF_detail(pgUpdate db){

		

		Integer line_no		= null;
		String ref_doc_id 	= "";
		Date ref_doc_date	= null;
		Boolean	with_budget	= false;
		String part_desc	= "";
		String acct_id		= "";
		String amount 		= "";
		String entity_id	= "";
		String entity_type_id	= "";
		String proj_id		= "";
		String div_id		= "";
		String dept_id		= "";
		String sec_id		= "";
		String inter_bus_id	= "";
		String inter_co_id	= "";
		String sd_no		= "";
		String asset_no		= "";
		String old_acct_id	= "";	
		String subProj		= "";

		line_no		= 1;
		ref_doc_id 	= "75";  //75 - Contractor's Billing Statement
		ref_doc_date= FncGlobal.dateFormat(FncGlobal.getDateSQL());
		with_budget	= false;
		part_desc	= txtparticular.getText() + "For Asset " +""+lookupassetno.getValue()+""; 
		acct_id		= "08-01-16-003";//Repairs and Maintenance - Furniture and Equipment		
		amount 		= txtamount.getText().replace(",", "");		
		entity_id	= lookuppayee.getValue().trim();		
		entity_type_id	= FncGlobal.GetString("select entity_type_id from rf_entity_assigned_type where entity_id = '"+entity_id+"' and status_id = 'A'");	
		//proj_id		= txtProject.getText().trim();			
		div_id		= "";
		dept_id		= "";
		sec_id		= "";
		inter_bus_id= "";
		inter_co_id	= "";	
		sd_no		= null;
		asset_no	= lookupassetno.getValue();
		old_acct_id	= null;	
		//subProj		= getSubProject(proj_id);
		//System.out.println("remarks: "+remarks);
		
		

		String sqlDetail = 
				"INSERT into rf_request_detail values ( \n" +
						"'"+lookupcompany.getValue()+"',  \n" +  		//1 co_id
						"'"+lookupcompany.getValue()+"',  \n" +		//2 busunit_id
						"'"+rplf_no+"',  \n" +		//3 rplf_no
						""+line_no+",  \n" +		//4 line_no
						"'"+ref_doc_id+"',  \n" + 	//5 ref_doc_id
						"'',  \n" +	//6	ref_doc_no
						"'"+ref_doc_date+"',  \n" + //7 ref_doc_date
						""+with_budget+" , \n" +	//8 with_budget
						"'"+part_desc+"',  \n" +	//9 part_desc
						"'"+acct_id+"',  \n" +		//10 acct_id			
						"'',  \n" +		//11 remarks			
						""+amount+",  \n" +			//12 amount			
						"'"+entity_id+"',  \n" +	//13 entity_id			
						"'"+entity_type_id+"' , \n" +	//14 entity_type_id
						"'"+proj_id+"',  \n" +		//15 project_id			
						"'"+subProj+"', \n" +		//16 sub_projectid			
						"'"+div_id+"',  \n" +		//17 div_id
						"'"+dept_id+"', \n" +		//18 dept_id
						"'"+sec_id+"',  \n" +		//19 sect_id
						"'"+inter_bus_id+"' , \n" +	//20 inter_busunit_id
						"'"+inter_co_id+"' , \n" +	//21 inter_co_id
						//"get_proj_isvatable('"+proj_id+"'), \n" +	//22
						//"get_ent_isvatable('"+entity_id+"') , \n" +	//23
						"false, \n" +				//24 is_vatproject	
						"false, \n" +				//25 is_vatentity
						"false, \n" +				//25 is_taxpaidbyco
						"false, \n" +				//25 is_gross
						"get_wtaxid_given_entitytype('"+entity_type_id+"') , \n" +	//26 wtax_id
						"0, \n" +		//27wtax_rate
						"0.00, \n" +			//28	wtx_amt
						"'', \n" +				//29	vat_acct_id
						"(case when get_ent_isvatable('"+entity_id+"') = true then 12 else 0 end), \n" +//30 vat_rate		
						"0.00, \n" +			//31	vat_amt	
						""+amount+", \n" +			//32	exp_amt	
						""+amount+", \n" +			//33	net_amt	
						""+sd_no+", \n" +			//34	sd_no
						"null, \n" +				//35	
						""+asset_no+", \n" +		//36	asset_no
						""+old_acct_id+", \n" +		//37	old_acct_id
						"'A',  \n" +				//38	status
						"'"+UserInfo.EmployeeCode+"',  \n" +	//39 created_by
						"now() , \n" +	//40	date_created
						"'' , \n" +					//41
						"null, \n" +				//42
						"0.00, \n" +			//43   ret_amt
						"0.00, \n" +			//44   dpr_amt
						"0.00, \n" +			//45   bc_amt
						"0.00 \n" +			//46   od_amt

			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}
	
	private void insertassetdisbursement(pgUpdate db) {
		//pgUpdate db = new pgUpdate();	
		String req_no = getrequest_id();
		
		String sql = "insert into rf_asset_disbursement values\n" + 
				"(\n" + 
				"	'"+lookupcompany.getValue()+"',\n" +  //rplf_no
				"	'"+rplf_no+"',\n" +   //rplf_no
				"	'',\n" +   //dept_id
				"	'"+txtparticular.getText()+"',\n" +   //particular
				"	"+txtamount.getText().replace(",", "")+",\n" +   //amount 
				"	'"+lookuppayee.getValue()+"',\n" +   //entity_id
				"	'',\n" +   //item_id
				"	'"+lookupassetno.getValue().trim()+"',\n" +   //asset_no
				"	'A',\n" +   //status_id
				"	'"+UserInfo.EmployeeCode+"',\n" +   //created_by
				"	now(),\n" +   //date_created
				"	'',\n" +   //edited_by
				"	null, \n" +   //date_edited
				"	'"+req_no+"' \n" +
				")";
		
		System.out.printf("SQL #1: %s", sql);
		db.executeUpdate(sql, false);
		//db.commit();
	}
	
	public static String getrequest_id(){
		pgSelect db = new pgSelect();
		String strSQL = "select to_char(coalesce(max(request_id::int),0)+1, 'FM00000000') from rf_asset_disbursement";
		db.select(strSQL);
		return db.Result[0][0].toString();
	}
	

	private static String sql_asset () {
		
		String sql = "select to_char(a.asset_no, '00000000')::varchar, a.asset_name, a.current_cust, c.entity_name \n" + 
				"from tbl_asset a\n" + 
				"left join em_employee b on a.current_cust = b.emp_code::int\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where a.asset_no is not null and a.status = 'A' and a.date_scanned is not null\n" + 
				"order by asset_no ";
		return sql;
	}
	
	private String getEntity_type(String entity_id) {

		return

		"select a.entity_type_id ,  " + "trim(b.entity_type_desc), " + "c.wtax_id, " + "c.wtax_rate \n"
				+ "from (select * from rf_entity_assigned_type where trim(entity_id) =  '" + entity_id + "' ) a \r\n"
				+ "left join mf_entity_type b on a.entity_type_id = b.entity_type_id\r\n"
				+ "left join rf_withholding_tax c on b.wtax_id = c.wtax_id   ";

	}
	
	public static String getEntityList(){
		
		String sql =
				"select\n" + 
				"entity_kind as \"Kind\", \n" + 
				"vat_registered as \"VAT\", \n" + 
				"trim(\n" + 
				"	case\n" + 
				"			when a.tin_no = '' or a.tin_no is null then ''\n" + 
				"			else\n" + 
				"					(\n" + 
				"						concat (		\n" + 
				"									substr(trim(replace(a.tin_no,'-','')), 1, 3), '-',\n" + 
				"									substr(trim(replace(a.tin_no,'-','')), 4, 3), '-',\n" + 
				"									substr(trim(replace(a.tin_no,'-','')), 7, 3), '-',\n" + 
				"									substr(trim(replace(a.tin_no,'-','')), 10, 3),\n" + 
				"									(case \n" + 
				"										when substr(trim(replace(a.tin_no,'-','')), 10, 3) = '' or substr(trim(replace(a.tin_no,'-','')), 10, 3) is null  then '000' else '' end)\n" + 
				"								)\n" + 
				"					) end\n" + 
				")as \"TIN\",\n" + 
				"trim(entity_id) as \"Entity ID\",\n" + 
				"trim(entity_name) as \"Name\"\n" + 
				"from (\n" + 
				"		(\n" + 
				"			select \n" + 
				"			a.entity_id, \n" + 
				"			a.entity_name, \n" + 
				"			a.entity_kind, \n" + 
				"			a.vat_registered,\n" + 
				"			replace(b.tin_no,'-','') as tin_no\n" + 
				"			from rf_entity a\n" + 
				"			left join rf_entity_id_no b on a.entity_id = b.entity_id\n" + 
				"			where a.status_id = 'A' --limit 3000\n" + 
				"		) \n" + 
				"		union\n" + 
				"		(\n" + 
				"			select \n" + 
				"			a.entity_id, \n" + 
				"			a.entity_name, \n" + 
				"			a.entity_kind, \n" + 
				"			a.vat_registered,\n" + 
				"			replace(b.tin_no,'-','') as tin_no\n" + 
				"			from rf_entity a\n" + 
				"			left join rf_entity_id_no b on a.entity_id = b.entity_id\n" + 
				"			where a.entity_id in (select entity_id  from em_employee)\n" + 
				"		) \n" + 
				"-- 		union\n" + 
				"-- 		(\n" + 
				"-- 			select \n" + 
				"-- 			entity_id, \n" + 
				"-- 			entity_name, \n" + 
				"-- 			entity_kind, \n" + 
				"-- 			vat_registered \n" + 
				"-- 			from rf_entity\n" + 
				"-- 		)\n" + 
				"	)a\n" + 
				"--left join rf_entity_id_no b on a.entity_id = b.entity_id\n" + 
				"order by a.entity_name";
		
		return sql; 

	}
}
