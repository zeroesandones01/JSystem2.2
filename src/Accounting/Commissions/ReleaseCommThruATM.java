package Accounting.Commissions;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import tablemodel.modelCommRelThruATM;
import Accounting.Disbursements.RequestForPayment;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncAcounting;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class ReleaseCommThruATM extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Release Commission Thru ATM";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlCommDetails;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlComp;
	private JPanel pnlComp_a;
	private JPanel pnlComp_a1;
	private JPanel pnlComp_a2;
	private JPanel pnlCV_no;
	private JPanel pnlCV_no_a;
	private JLabel lblCDF_no;
	private JPanel pnlCV_no_b;
	private JPanel pnlCV_no_b1;
	private JPanel pnlCV_no_b2;
	private JPanel pnlCV_no_b3;
	private JPanel pnlSouthCenter_a;
	private JPanel pnlSouthCenter_a1;
	private JPanel pnlSouthCenter_a2;
	private JPanel pnlSouthCenter_a1_a;
	private JPanel pnlSouthCenter_a1_b;
	private JPanel pnlAddLocation;

	private JLabel lblCompany;
	private JLabel lblPayDate;	
	private JLabel lblFile;
	private JLabel lblFileLocation;
	
	private modelCommRelThruATM modelComm_CommSched;
	private modelCommRelThruATM modelComm_CommSched_total;

	private _JLookup lookupCompany;
	private _JLookup lookupCDF;

	private JButton btnPrint;
	private JButton btnSave;
	private JButton btnAdd;
	private JButton btnOK;
	
	private JXTextField txtFileLocation;	
	private JXTextField txtFile;	
	
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private _JTagLabel tagCompany;

	private _JScrollPaneMain scrollComm_CommSched;
	private _JScrollPaneTotal scrollComm_CommSchedtotal;
	private _JTableMain tblComm_CommSched;
	private JList rowHeaderComm_CommSched;
	private _JTableTotal tblComm_CommSched_total;
	private _JDateChooser datePay;	

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);

	String co_id 	= "";		
	String company 	= "";
	String company_logo;	

	public ReleaseCommThruATM() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ReleaseCommThruATM(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public ReleaseCommThruATM(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(709, 421));
		this.setBounds(0, 0, 709, 421);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{ //start of Company

			pnlComp = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlComp, BorderLayout.NORTH);			
			pnlComp.setPreferredSize(new java.awt.Dimension(1047, 72));	
			pnlComp.setBorder(lineBorder);

			{
				//company
				pnlComp_a = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlComp_a, BorderLayout.NORTH);	
				pnlComp_a.setPreferredSize(new java.awt.Dimension(1045, 36));	
				pnlComp_a.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));			

				{
					pnlComp_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlComp_a.add(pnlComp_a1, BorderLayout.WEST);	
					pnlComp_a1.setPreferredSize(new java.awt.Dimension(207, 30));
					pnlComp_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

					{
						lblCompany = new JLabel("        COMPANY", JLabel.TRAILING);
						pnlComp_a1.add(lblCompany);
						lblCompany.setBounds(8, 11, 62, 12);
						lblCompany.setPreferredSize(new java.awt.Dimension(115, 25));
						lblCompany.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}
					{
						lookupCompany = new _JLookup(null, "Company",0,2);
						pnlComp_a1.add(lookupCompany);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setReturnColumn(0);
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

									co_id 		= (String) data[0];										
									String name = (String) data[1];	
									company		= name;
									tagCompany.setTag(name);

									lblCDF_no.setEnabled(true);	
									lookupCDF.setEnabled(true);	
									lookupCDF.setEditable(true);										

									lblPayDate.setEnabled(true);
									datePay.setEnabled(true);

									lookupCDF.setLookupSQL(getDV_no(co_id));
									lookupCDF.setValue("");
									btnSave.setEnabled(false);
									btnPrint.setEnabled(false);
									refresh_Table();
								}
							}
						});
					}	
				}
				{
					pnlComp_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlComp_a.add(pnlComp_a2, BorderLayout.CENTER);	
					pnlComp_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
					pnlComp_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

					{
						tagCompany = new _JTagLabel("[ ]");
						pnlComp_a2.add(tagCompany);
						tagCompany.setBounds(209, 27, 700, 22);
						tagCompany.setEnabled(true);	
						tagCompany.setPreferredSize(new java.awt.Dimension(27, 33));
					}	
				}
			}
			{
				pnlCV_no = new JPanel(new BorderLayout(5, 5));
				pnlComp.add(pnlCV_no, BorderLayout.SOUTH);	
				pnlCV_no.setPreferredSize(new java.awt.Dimension(905, 33));					

				{
					pnlCV_no_a = new JPanel(new GridLayout(1, 1, 5, 10));
					pnlCV_no.add(pnlCV_no_a, BorderLayout.WEST);	
					pnlCV_no_a.setPreferredSize(new java.awt.Dimension(95, 40));	
					pnlCV_no_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblCDF_no = new JLabel("CV No.", JLabel.TRAILING);
						pnlCV_no_a.add(lblCDF_no);
						lblCDF_no.setEnabled(false);	
						lblCDF_no.setPreferredSize(new java.awt.Dimension(100, 40));
						lblCDF_no.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}						

					pnlCV_no_b = new JPanel(new BorderLayout(5, 5));
					pnlCV_no.add(pnlCV_no_b, BorderLayout.CENTER);	
					pnlCV_no_b.setPreferredSize(new java.awt.Dimension(814, 40));	
					pnlCV_no_b.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

					{
						pnlCV_no_b1 = new JPanel(new GridLayout(1, 1, 5, 10));
						pnlCV_no_b.add(pnlCV_no_b1, BorderLayout.WEST);	
						pnlCV_no_b1.setPreferredSize(new java.awt.Dimension(147, 24));					

						{
							lookupCDF = new _JLookup(null, "CV No.", 2, 2);
							pnlCV_no_b1.add(lookupCDF);
							lookupCDF.setBounds(20, 27, 20, 25);
							lookupCDF.setReturnColumn(0);
							lookupCDF.setEnabled(false);	
							//lookupCDF.setEditable(false);	
							lookupCDF.setPreferredSize(new java.awt.Dimension(135, 24));
							lookupCDF.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){	

										lookupCDF.setLookupSQL(getDV_no(co_id));
										Date tran_date = (Date) data[1];						
										datePay.setDate(tran_date);	

										displayCV_details(modelComm_CommSched, rowHeaderComm_CommSched, modelComm_CommSched_total, (String) data[0] );	

										btnSave.setEnabled(true);
										btnPrint.setEnabled(true);

										lblFileLocation.setEnabled(true);	
										txtFileLocation.setEnabled(true);	
										txtFileLocation.setEditable(true);
										btnAdd.setEnabled(true);										
									}
								}
							});
						}	
					}
					{
						pnlCV_no_b2 = new JPanel(new GridLayout(1, 2, 5, 0));
						pnlCV_no_b.add(pnlCV_no_b2, BorderLayout.CENTER);	
						pnlCV_no_b2.setPreferredSize(new java.awt.Dimension(357, 25));	
					}
					{
						pnlCV_no_b3 = new JPanel(new GridLayout(1, 2,5,0));
						pnlCV_no_b.add(pnlCV_no_b3, BorderLayout.EAST);	
						pnlCV_no_b3.setPreferredSize(new java.awt.Dimension(310, 20));	
						pnlCV_no_b3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));			

						{
							lblPayDate = new JLabel("Pay Date  ", JLabel.TRAILING);
							pnlCV_no_b3.add(lblPayDate);
							lblPayDate.setBounds(8, 11, 62, 12);
							lblPayDate.setEnabled(false);
							lblPayDate.setPreferredSize(new java.awt.Dimension(96, 26));
							lblPayDate.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							lblPayDate.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
						}
						{
							datePay = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
							pnlCV_no_b3.add(datePay);
							datePay.setBounds(485, 7, 125, 21);
							datePay.setDate(Calendar.getInstance().getTime());
							datePay.setEnabled(false);
							datePay.setDateFormatString("yyyy-MM-dd");
							((JTextFieldDateEditor)datePay.getDateEditor()).setEditable(false);
							datePay.addPropertyChangeListener( new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent e) {

								}
							});	
						}	
					}
				}
			}								
		} //end of Company

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlSubTable = new JPanel();
			pnlTable.add(pnlSubTable, BorderLayout.CENTER);
			pnlSubTable.setLayout(new BorderLayout(5, 5));
			pnlSubTable.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlSubTable.setBorder(lineBorder);		

			{						

				pnlCommDetails = new JPanel();
				pnlSubTable.add(pnlCommDetails, BorderLayout.CENTER);
				pnlCommDetails.setLayout(new BorderLayout(0,0));
				pnlCommDetails.setBorder(lineBorder);		
				pnlCommDetails.setPreferredSize(new java.awt.Dimension(923, 177));
				pnlCommDetails.setBorder(JTBorderFactory.createTitleBorder("Commission"));

				//start of Commission Schedule (by client)
				{			

					{
						scrollComm_CommSched = new _JScrollPaneMain();
						pnlCommDetails.add(scrollComm_CommSched, BorderLayout.CENTER);
						{
							modelComm_CommSched = new modelCommRelThruATM();

							tblComm_CommSched = new _JTableMain(modelComm_CommSched);
							scrollComm_CommSched.setViewportView(tblComm_CommSched);
							tblComm_CommSched.addMouseListener(this);								
							tblComm_CommSched.setSortable(false);
							tblComm_CommSched.getColumnModel().getColumn(0).setPreferredWidth(120);
							tblComm_CommSched.getColumnModel().getColumn(1).setPreferredWidth(120);
							tblComm_CommSched.getColumnModel().getColumn(2).setPreferredWidth(400);
							tblComm_CommSched.getColumnModel().getColumn(3).setPreferredWidth(120);
							tblComm_CommSched.getColumnModel().getColumn(4).setPreferredWidth(120);
							tblComm_CommSched.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {}							
								public void keyPressed(KeyEvent e) {}	

							}); 
							tblComm_CommSched.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblComm_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblComm_CommSched.setCellSelectionEnabled(true);}

								}
								public void mouseReleased(MouseEvent e) {
									if(tblComm_CommSched.rowAtPoint(e.getPoint()) == -1){}
									else{tblComm_CommSched.setCellSelectionEnabled(true);}
								}
							});

						}
						{
							rowHeaderComm_CommSched = tblComm_CommSched.getRowHeader22();
							scrollComm_CommSched.setRowHeaderView(rowHeaderComm_CommSched);
							scrollComm_CommSched.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollComm_CommSchedtotal = new _JScrollPaneTotal(scrollComm_CommSched);
						pnlCommDetails.add(scrollComm_CommSchedtotal, BorderLayout.SOUTH);
						{
							modelComm_CommSched_total = new modelCommRelThruATM();
							modelComm_CommSched_total.addRow(new Object[] { "Total" , null, null, null, new BigDecimal(0.00) });

							tblComm_CommSched_total = new _JTableTotal(modelComm_CommSched_total, tblComm_CommSched);
							tblComm_CommSched_total.setFont(dialog11Bold);
							scrollComm_CommSchedtotal.setViewportView(tblComm_CommSched_total);
							((_JTableTotal) tblComm_CommSched_total).setTotalLabel(0);
						}
					}
				} 
				//end of Commission Schedule (by client)
			}

		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(907, 77));

			{
				pnlSouthCenter_a = new JPanel(new BorderLayout(5, 5));
				pnlSouth.add(pnlSouthCenter_a, BorderLayout.NORTH);
				pnlSouthCenter_a.setPreferredSize(new java.awt.Dimension(1500, 36));
				pnlSouthCenter_a.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));		

				{					
					{
						pnlSouthCenter_a1 = new JPanel(new BorderLayout(5, 5));
						pnlSouthCenter_a.add(pnlSouthCenter_a1, BorderLayout.WEST);	
						pnlSouthCenter_a1.setPreferredSize(new java.awt.Dimension(533, 26));	
						pnlSouthCenter_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));	

						{
							pnlSouthCenter_a1_a = new JPanel(new BorderLayout(5, 5));
							pnlSouthCenter_a1.add(pnlSouthCenter_a1_a, BorderLayout.WEST);	
							pnlSouthCenter_a1_a.setPreferredSize(new java.awt.Dimension(89, 36));	
							pnlSouthCenter_a1_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));	

							lblFileLocation = new JLabel("File Directory :", JLabel.TRAILING);
							pnlSouthCenter_a1_a.add(lblFileLocation);
							lblFileLocation.setEnabled(false);	
							lblFileLocation.setPreferredSize(new java.awt.Dimension(183, 26));
						}
						{

							pnlSouthCenter_a1_b = new JPanel(new GridLayout(1, 2,5,0));
							pnlSouthCenter_a1.add(pnlSouthCenter_a1_b, BorderLayout.CENTER);	
							pnlSouthCenter_a1_b.setPreferredSize(new java.awt.Dimension(491, 36));	
							pnlSouthCenter_a1_b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));	

							txtFileLocation = new JXTextField("");
							pnlSouthCenter_a1_b.add(txtFileLocation);
							txtFileLocation.setEnabled(false);	
							txtFileLocation.setEditable(false);
							txtFileLocation.setText(sql_getFileDirectory());
							txtFileLocation.setBounds(120, 25, 300, 22);	
							txtFileLocation.setHorizontalAlignment(JTextField.CENTER);
							txtFileLocation.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
							txtFileLocation.setPreferredSize(new java.awt.Dimension(547, 26));
						}
					}	
					{
						pnlSouthCenter_a2 = new JPanel(new GridLayout(1, 2,5,0));
						pnlSouthCenter_a.add(pnlSouthCenter_a2, BorderLayout.CENTER);	
						pnlSouthCenter_a2.setPreferredSize(new java.awt.Dimension(491, 36));	
						pnlSouthCenter_a2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));	

						btnAdd = new JButton("Add/Edit File Direction");
						pnlSouthCenter_a2.add(btnAdd);
						btnAdd.setActionCommand("Add");
						btnAdd.addActionListener(this);
						btnAdd.setEnabled(false);
					}	
				}
			}
			{
				pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth.add(pnlSouthCenterb, BorderLayout.CENTER);
				pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
				pnlSouthCenterb.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));	
				{
					{
						btnSave = new JButton("Save to File For Uploading");
						pnlSouthCenterb.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.addActionListener(this);
						btnSave.setEnabled(false);
					}
					{
						btnPrint = new JButton("Print Prooflist");
						pnlSouthCenterb.add(btnPrint);
						btnPrint.setActionCommand("Print");
						btnPrint.addActionListener(this);
						btnPrint.setEnabled(false);
					}				
				}
			}
		}
		{
			pnlAddLocation = new JPanel();
			pnlAddLocation.setLayout(null);
			pnlAddLocation.setPreferredSize(new java.awt.Dimension(400, 80));

			{
				lblFile = new JLabel();
				pnlAddLocation.add(lblFile);
				lblFile.setBounds(5, 15, 85, 20);
				lblFile.setText("File Directory:");
			}
			{
				txtFile = new JXTextField("");
				pnlAddLocation.add(txtFile);
				txtFile.setEnabled(true);	
				txtFile.setEditable(true);
				txtFile.setText(sql_getFileDirectory());
				txtFile.setBounds(88, 15, 310, 20);	
				txtFile.setHorizontalAlignment(JTextField.CENTER);
				txtFile.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
			}
			{
				btnOK = new JButton();
				pnlAddLocation.add(btnOK);
				btnOK.setBounds(160, 58, 69, 22);
				btnOK.setText("OK");
				btnOK.setFocusable(false);
				btnOK.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						saveFileLoc();
						Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlAddLocation);
						optionPaneWindow.dispose();
						txtFileLocation.setText(sql_getFileDirectory());
					}
				});
			}
		}

		//added 01/26/2016 - purpose - set CENQHOMES as default company
		initialize_comp();
	}

	//display
	public void displayCV_details(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal, String cv_no) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"select\r\n" + 
			"\r\n" + 
			"a.cv_no,\r\n" + 
			"a.pv_no,\r\n" + 
			"upper(trim(b.entity_name)),\r\n" + 
			"(case when c.atm_no is null then bb.atm_no else c.atm_no end) as atm_no, \r\n" + 
			"(coalesce(d.net,0) + coalesce(d.bonus,0)) as net_amt\r\n" + 
			"\r\n" + 
			"from rf_pv_header a \r\n" + 
			"left join (select distinct on (rplf_no) rplf_no, agent_code, sum(comm_amt)as net, sum(bonus_amt) as bonus  from cm_cdf_hd group by rplf_no, agent_code ) d on a.pv_no = d.rplf_no\r\n" + 
			"left join rf_entity b on d.agent_code = b.entity_id\r\n " +
			"left join rf_entity_id_no bb on b.entity_id = bb.entity_id \n" + 
			"left join mf_sellingagent_info c on d.agent_code = c.agent_id\r\n" + 
			"\r\n" + 
			"where a.cv_no = '"+cv_no+"'  \r\n" + 
			"and a.co_id = '"+co_id+"'\r\n";

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	

			totalCV(modelMain, modelTotal);			
		}		

		else {

			modelComm_CommSched_total = new modelCommRelThruATM();
			modelComm_CommSched_total.addRow(new Object[] { "Total" , null, null, null, new BigDecimal(0.00) });

			tblComm_CommSched_total = new _JTableTotal(modelComm_CommSched_total, tblComm_CommSched);
			tblComm_CommSched_total.setFont(dialog11Bold);
			scrollComm_CommSchedtotal.setViewportView(tblComm_CommSched_total);
			((_JTableTotal) tblComm_CommSched_total).setTotalLabel(0);
		}

		tblComm_CommSched.packAll();		
		adjustRowHeight();

	}	

	//display tables


	//reset, enable, disable
	public void refresh_Table(){

		//reset table 1
		FncTables.clearTable(modelComm_CommSched);
		FncTables.clearTable(modelComm_CommSched_total);			
		rowHeaderComm_CommSched = tblComm_CommSched.getRowHeader22();
		scrollComm_CommSched.setRowHeaderView(rowHeaderComm_CommSched);	
		modelComm_CommSched_total.addRow(new Object[] {  "Total", new BigDecimal(0.00) });

	}

	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {//

		if (FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true)
		{
			if(e.getActionCommand().equals("Print")){ preview_aubProoflist(); preview_ATM_paymentProoflist(); }

			if(e.getActionCommand().equals("Save")){ try {
				saveATM(); } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			}			
		}		
		else {JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to process commission.","Warning",JOptionPane.WARNING_MESSAGE); }

		if(e.getActionCommand().equals("Add")){ 

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlAddLocation, "Enter",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);		

			if ( scanOption == JOptionPane.CLOSED_OPTION ) {

			} // CLOSED_OPTION

		}


	}

	public void mouseClicked(MouseEvent evt) {	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void preview_aubProoflist(){

		String cv_no 	= lookupCDF.getText();
		String criteria = "AUB Prooflist";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("cv_no", lookupCDF.getText());
		mapParameters.put("prepared_by", UserInfo.EmployeeCode);	
		mapParameters.put("cv_no", cv_no);	
		mapParameters.put("co_id", co_id);	

		FncReport.generateReport("/Reports/rptAUB_prooflist.jasper", reportTitle, "", mapParameters);

	}

	public void preview_ATM_paymentProoflist(){

		String cv_no 	= lookupCDF.getText();
		String criteria = "ATM Payment Prooflist";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("company", company);
		mapParameters.put("cv_no", lookupCDF.getText());
		mapParameters.put("prepared_by", UserInfo.EmployeeCode);	
		mapParameters.put("cv_no", cv_no);	
		mapParameters.put("co_id", co_id);	

		FncReport.generateReport("/Reports/rptATM_payment_prooflist.jasper", reportTitle, "", mapParameters);

	}

	public void initialize_comp(){		

		co_id 		= "02";	
		company		= "CENQHOMES DEVELOPMENT CORPORATION";			
		tagCompany.setTag(company);
		company_logo = RequestForPayment.sql_getCompanyLogo();

		lblCDF_no.setEnabled(true);	
		lookupCDF.setEnabled(true);	
		lookupCDF.setEditable(true);										

		lblPayDate.setEnabled(true);
		datePay.setEnabled(true);

		lookupCDF.setLookupSQL(getDV_no(co_id));
		lookupCDF.setValue("");
		btnSave.setEnabled(false);
		btnPrint.setEnabled(false);
		refresh_Table();

		lookupCompany.setValue(co_id);
	}


	//select, lookup and get statements	
	private static String getDV_no(String co_id){//used
		return 
		"\r\n" + 
		"select distinct on (cv_no)\r\n" + 
		"\r\n" + 
		"a.cv_no,\r\n" + 
		"a.cv_date,\r\n" + 
		"a.co_id\r\n" + 
		"\r\n" + 
		"from rf_cv_header a\r\n" + 
		"\r\n" + 
		"where cv_no in (select distinct on (cv_no) cv_no from \r\n" + 
		"   rf_pv_header where rplf_no in (select distinct on (rplf_no) rplf_no from cm_cdf_hd))\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"and status_id = 'P'\r\n" + 
		"and co_id = '"+co_id+"' " +
		"\r\n" + 
		"order by a.cv_no desc"  ;

	}

	public static String sql_getFileDirectory() {

		String a = "";

		String SQL = 
			"select trim(file_location) from cm_user_atm_file_location " +
			"where emp_code = '"+UserInfo.EmployeeCode+"'\r\n" + 
			"and status_id = 'A' " ;

		System.out.printf("SQL #1: sql_getAvailer", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {a = "";}
			else {a = (String) db.getResult()[0][0]; }

		}else{
			a = "";
		}

		return a;
	}


	//table operations	
	private void adjustRowHeight(){		

		int rw = tblComm_CommSched.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tblComm_CommSched.setRowHeight(x, 22);			
			x++;
		}
	}

	public void totalCV(DefaultTableModel modelMain, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelTotal);//Code to clear modelMain.		

		BigDecimal amt_bd 		= new BigDecimal(0.00);	

		for(int x=0; x < modelMain.getRowCount(); x++){		

			try { amt_bd 	= amt_bd.add(((BigDecimal) modelMain.getValueAt(x,4)));} 
			catch (NullPointerException e) { amt_bd 	= amt_bd.add(new BigDecimal(0.00)); }

		}		

		modelTotal.addRow(new Object[] { "Total", null, null, null, amt_bd});
	}


	//processes
	public void saveATM() throws IOException{

		String file_dir = sql_getFileDirectory();

		if(file_dir.equals(""))
		{JOptionPane.showMessageDialog(getContentPane(), "<html>Please add <html>" +
				"<html><b><i> a File Directory.<html></b></i>", "File Directory Not Found", 
				JOptionPane.WARNING_MESSAGE);}
		else {		

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");	
			NumberFormat nf  = new DecimalFormat("00000000000.000000000000000000000000"); 	
			NumberFormat nf3 = new DecimalFormat("00000000000.000000000000000000000000000000"); 	
			NumberFormat nf2 = new DecimalFormat("000000"); 

			String cv_no = lookupCDF.getText();

			File myFile = new File(file_dir,"CVNo"+cv_no+".txt");
			myFile.createNewFile();
			BufferedWriter output = new BufferedWriter(new FileWriter(myFile));			

			output.write("BF"+company+"       "+dateFormat.format(Calendar.getInstance().getTime()));

			int rw = tblComm_CommSched.getModel().getRowCount();	
			int x = 0;

			while (x<rw)
			{
				String atmno	=  tblComm_CommSched.getValueAt(x,3).toString().trim();	
				Double rate		=  Double.parseDouble(tblComm_CommSched.getValueAt(x,4).toString());	

				output.append(System.lineSeparator());
				output.write("01"+atmno+nf.format(rate));

				x++;
			}		

			int tot_atm = x + 1 ;
			double tot_amt = Double.parseDouble(tblComm_CommSched_total.getValueAt(0,4).toString());	

			output.append(System.lineSeparator());
			output.write("EF"+nf2.format(tot_atm)+nf3.format(tot_amt) );


			output.close();

			JOptionPane.showMessageDialog(getContentPane(),"A notepad file has been saved in your computer.","Information",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void saveFileLoc(){	

		pgUpdate db = new pgUpdate();				

		//set old file location to inactive status
		String sqlDetail1 = 
			"update cm_user_atm_file_location \n" +
			"set status_id = 'I' \n" +
			"where emp_code = '"+UserInfo.EmployeeCode+"' \n\n " ;

		System.out.printf("SQL #1: %s", sqlDetail1);
		db.executeUpdate(sqlDetail1, false);	

		String sqlDetail2 = 
			"INSERT into cm_user_atm_file_location values ( \n" +
			"'"+UserInfo.EmployeeCode+"',  \n" +  		//1
			"'"+txtFile.getText().trim()+"',  \n" +		//2
			"'A' , \n" +				//3
			"'"+UserInfo.EmployeeCode+"',  \n" +	//4
			"'"+Calendar.getInstance().getTime()+"',  \n" +	//5
			"'' , \n" +					//6
			"null  \n" +				//7	
			")  \n\n " ;

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);	
		db.commit();	

		JOptionPane.showMessageDialog(getContentPane(),"File location saved.","Information",JOptionPane.INFORMATION_MESSAGE);
	}

	//panels
	public void panelAddLocation(){



	}
}
