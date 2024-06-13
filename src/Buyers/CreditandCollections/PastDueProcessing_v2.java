package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.SpringUtilities;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JButton;
import components._JInternalFrame;
import components._JRadioButton;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import interfaces._GUI;
import tablemodel._model_PastDue;


@SuppressWarnings("rawtypes")
public class PastDueProcessing_v2 extends _JInternalFrame implements _GUI,LookupListener,MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static String title = "Past Due Processing";
	public static Dimension frameSize = new Dimension(900, 600);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);


	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private _JRadioButton rbtnPastDue;
	private _JRadioButton rbtnBuyBack;
	private _JRadioButton rbtnPCancel;

	private ButtonGroup btngrpProcess = new ButtonGroup();
	private JLabel lblProject;
	private JLabel lblBuyerType;
	private JLabel lblMonthPD;
	private JLabel lblAccountStatus;
	private JLabel lblSalesDiv;
	private _JLookup lookupProject;
	private JTextField txtProject;

	private DefaultComboBoxModel cmbBuyerTypeModel;
	private JComboBox cmbBuyerType;
	private DefaultComboBoxModel cmbMonthPDModel;
	private JComboBox cmbMonthPD;
	private DefaultComboBoxModel cmbAccountStatusModel;
	private JComboBox cmbAccountStatus;
	private DefaultComboBoxModel cmbSalesDivModel;
	private JComboBox cmbSalesDiv;

	private _JButton btnGenerate;
	private _JButton btnPreview;
	private JPanel pnlCenter;
	private _JScrollPaneMain scrollPastDue;
	private _model_PastDue modelPastDue;
	private _JTableMain tblPastDue;
	private JList rowHeaderPastDue;
	private _JScrollPaneTotal scrollNSPTotal;
	private _model_PastDue modelNSPTotal;
	private _JTableTotal tblNSPTotal;

	private String co_id;
	private String company;
	private String company_logo;

	private JPanel pnlCenter_Table;
	private JPanel pnlProject;
	private JPanel pnlButton;
	private _JDateChooser dteDueDate;
	private JLabel lblDueDate;

	private _FPastDueProcessing method = new _FPastDueProcessing();

	private String proj_id;
	private String proj_name;
	private String labelType;
	private DefaultComboBoxModel cmbCancellationModel;
	private JComboBox cmbCancellation;
	private JLabel lblCancellation;
	private DefaultComboBoxModel cmbReasonModel;
	private JComboBox cmbReason;
	private DefaultComboBoxModel cmbRemarksModel;
	private JComboBox cmbRemarks;
	private String ph;
	private String blk;
	private String lot;
	private String dueDate;

	public PastDueProcessing_v2() {
		super(title, true, true, true, true);
		initGUI();
		FormLoad();
	}

	public PastDueProcessing_v2(String title) {
		super(title);
		initGUI();
		FormLoad();
	}

	public PastDueProcessing_v2(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		FormLoad();
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public void initGUI() {
		try {
			this.setTitle(title);
			this.setSize(frameSize);
			this.setPreferredSize(frameSize);
			getContentPane().setLayout(new BorderLayout());
			{
				pnlMain = new JPanel();
				this.add(pnlMain,BorderLayout.CENTER);
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlMain.setLayout(new BorderLayout(3,3));
				{
					pnlNorth = new JPanel();
					pnlMain.add(pnlNorth, BorderLayout.NORTH);
					pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Process"));
					pnlNorth.setPreferredSize(new Dimension(pnlMain.getWidth(), 270));
					pnlNorth.setLayout(new BorderLayout(3,3)); 
					{
						JPanel pnlCompany = new JPanel();
						pnlNorth.add(pnlCompany, BorderLayout.NORTH);
						//	pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Process"));
						pnlCompany.setPreferredSize(new Dimension(pnlMain.getWidth(), 25));
						pnlCompany.setLayout(new BorderLayout(3,3)); 
						{

							{
								lblCompany = new JLabel("Company");
								pnlCompany.add(lblCompany,BorderLayout.WEST);
								lblCompany.setPreferredSize(new Dimension(100, 25));
							}
							{
								JPanel pnlLookup = new JPanel(new BorderLayout(3,3));
								pnlCompany.add(pnlLookup,BorderLayout.CENTER);
								pnlLookup.setPreferredSize(new Dimension(74, 25));
								{

									lookupCompany = new _JLookup("", "Company", 0) ; /// XXX lookupCompany 
									pnlLookup.add(lookupCompany,BorderLayout.WEST);
									lookupCompany.setLookupSQL(method.getCompany());
									lookupCompany.setPreferredSize(new Dimension(100, 25));
									lookupCompany.addLookupListener(this);
								}
								{
									txtCompany = new JTextField();
									pnlLookup.add(txtCompany,BorderLayout.CENTER);
									txtCompany.setEditable(false);
									txtCompany.setPreferredSize(new Dimension(100, 25));
								} 
							}

							SpringUtilities.makeCompactGrid(pnlCompany, 1, 2, 2, 2, 2, 2, false);

						}
					}
					{
						JPanel pnlN = new JPanel();
						pnlNorth.add(pnlN, BorderLayout.CENTER);
						pnlN.setPreferredSize(new Dimension(pnlMain.getWidth(), 25));
						pnlN.setLayout(new BorderLayout(3,3));
						{
							JPanel pnlRadionBtn = new JPanel(new GridLayout(0, 3, 2, 2));
							pnlN.add(pnlRadionBtn, BorderLayout.NORTH);
							pnlRadionBtn.setPreferredSize(new Dimension(pnlMain.getWidth(), 25));
							pnlRadionBtn.setBorder(FncGlobal.lineBorder);
							{

								{
									rbtnPastDue = new _JRadioButton("Past Due");
									pnlRadionBtn.add(rbtnPastDue);
									btngrpProcess.add(rbtnPastDue);
									rbtnPastDue.setActionCommand("PastDue");
									rbtnPastDue.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											//pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("List of Past Due "));
											//Change(rbtnPastDue);
											//computeTotal();
											setChange();
											computeTotal();
										}
									});
								} 
								{
									rbtnBuyBack = new _JRadioButton("Buyback");
									pnlRadionBtn.add(rbtnBuyBack);
									btngrpProcess.add(rbtnBuyBack);
									rbtnBuyBack.setActionCommand("BB");
									rbtnBuyBack.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											//	pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("List of Buyback from Bank "));
											//	Change(rbtnBuyBack);
											//	computeTotal();
											setChange();
											computeTotal();
										}
									});
								}
								{
									rbtnPCancel= new _JRadioButton("Cancellable Accounts");
									pnlRadionBtn.add(rbtnPCancel);
									btngrpProcess.add(rbtnPCancel);
									rbtnPCancel.setActionCommand("Cancel");
									rbtnPCancel.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											//	pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("List of Projected for Cancellation"));
											//	Change(rbtnPCancel);
											//	computeTotal();
											setChange();
											computeTotal();
										}
									});
								}

							}
						}
						/*{
						JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 2, 2));
						pnlN.add(pnlCenter, BorderLayout.CENTER);
						pnlCenter.setPreferredSize(new Dimension(pnlMain.getWidth(), 0));
						pnlCenter.setBorder(FncGlobal.lineBorder);
						{
							JPanel pnlWest = new JPanel(new GridLayout(0, 3, 2, 2));
							pnlCenter.add(pnlWest);
						//	pnlWest.setPreferredSize(new Dimension(400, 0));
							pnlWest.setBorder(FncGlobal.lineBorder);
						}
						{
							JPanel pnlEast = new JPanel(new GridLayout(0, 3, 2, 2));
							pnlCenter.add(pnlEast);
							pnlEast.setBorder(FncGlobal.lineBorder);
						}
					}*/

						{
							pnlCenter = new JPanel(new BorderLayout(3, 3));
							pnlN.add(pnlCenter, BorderLayout.CENTER);
							{
								pnlProject = new JPanel(new BorderLayout(3,3));
								pnlCenter.add(pnlProject, BorderLayout.NORTH);
								pnlProject.setPreferredSize(new Dimension(pnlMain.getWidth(), 25));
								{
									{
										lblProject = new JLabel("Project");
										pnlProject.add(lblProject,BorderLayout.WEST);
										lblProject.setPreferredSize(new Dimension(152, 25));
									}
									{
										JPanel pnlLookup = new JPanel(new BorderLayout(3,3));
										pnlProject.add(pnlLookup,BorderLayout.CENTER);
										pnlLookup.setPreferredSize(new Dimension(100, 25));
										{

											lookupProject = new _JLookup("", "Company", 0) ; /// XXX lookupCompany 
											pnlLookup.add(lookupProject,BorderLayout.WEST);
											lookupProject.setLookupSQL(method.lookupProjects());
											lookupProject.setPreferredSize(new Dimension(100, 25));
											lookupProject.addLookupListener(this);
										}
										{
											txtProject = new JTextField();
											pnlLookup.add(txtProject,BorderLayout.CENTER);
											txtProject.setEditable(false);
											txtProject.setPreferredSize(new Dimension(100, 25));
										} 
									}
								}
							}
							
							JPanel pnlWest = new JPanel(new BorderLayout(5, 5));
							pnlCenter.add(pnlWest, BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(400, 0));
							{
								JPanel pnlLabel = new JPanel(new GridLayout(6, 1, 2, 2));
								pnlWest.add(pnlLabel, BorderLayout.WEST);
								pnlLabel.setPreferredSize(new Dimension(150, 0));
								{

									lblBuyerType = new JLabel("Buyer Type");
									pnlLabel.add(lblBuyerType);

									lblMonthPD = new JLabel("Month Past Due");
									pnlLabel.add(lblMonthPD);

									lblAccountStatus = new JLabel("Account Status ");
									pnlLabel.add(lblAccountStatus);

									lblSalesDiv = new JLabel("Sales Division");
									pnlLabel.add(lblSalesDiv);

									lblCancellation = new JLabel("Reason for Cancellation");
									pnlLabel.add(lblCancellation);

									lblDueDate = new JLabel("Date Cut Off");
									pnlLabel.add(lblDueDate);
								}
								{
									
									JPanel pnlAction = new JPanel(new GridLayout(6, 1, 2, 2));
									pnlWest.add(pnlAction, BorderLayout.CENTER);
									pnlAction.setPreferredSize(new Dimension(400, 0));
									{
										cmbBuyerTypeModel = new DefaultComboBoxModel(
												new String[] {"All Accounts"," In-House Accounts", " Pag-ibig Accounts", "Cash Accounts", "Bank Finance Accounts" });
										cmbBuyerType = new JComboBox();
										pnlAction.add(cmbBuyerType);
										cmbBuyerType.setModel(cmbBuyerTypeModel);
										//cmbBuyerType.setSelectedItem(null);
										cmbBuyerType.addActionListener(this);
									}
									{

										cmbMonthPDModel = new DefaultComboBoxModel(
												new String[] { " All", " 1", " 2", " > 2", " TR" });
										cmbMonthPD = new JComboBox();
										pnlAction.add(cmbMonthPD);
										cmbMonthPD.setModel(cmbMonthPDModel);
										cmbMonthPD.setSelectedItem(null);
										cmbMonthPD.addActionListener(this);
									}
									{
										cmbAccountStatusModel = new DefaultComboBoxModel(
												new String[] {" All Status", " Buyback", " Forwarded to Bank", " Sold to Bank", " REM" });
										cmbAccountStatus = new JComboBox();
										pnlAction.add(cmbAccountStatus);
										cmbAccountStatus.setModel(cmbAccountStatusModel);
										//cmbAccountStatus.setSelectedItem(null);
										cmbAccountStatus.addActionListener(this);
									}
									{
										cmbSalesDivModel = new DefaultComboBoxModel(
												new String[] {"All Division","Division 1","Division 2","Division 3","Division 4"});
										cmbSalesDiv = new JComboBox();
										pnlAction.add(cmbSalesDiv);
										cmbSalesDiv.setModel(cmbSalesDivModel);
										cmbSalesDiv.setBounds(122,cmbAccountStatus.getY()+25, 230, 22);
										//cmbSalesDiv.setSelectedItem(null);
										cmbSalesDiv.addActionListener(this);
									}

									{
										cmbCancellationModel = new DefaultComboBoxModel(
												new String[] {"All","TR Due To Docs","TR Due To Payments","OR Due To Payments"});
										cmbCancellation = new JComboBox();
										pnlAction.add(cmbCancellation);
										cmbCancellation.setModel(cmbCancellationModel);
										cmbCancellation.setBounds(122,cmbSalesDiv.getY()+25, 230, 22);
										cmbCancellation.setSelectedItem(null);
										cmbCancellation.addActionListener(this);
									}
									{

										dteDueDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
										pnlAction.add(dteDueDate);
										dteDueDate.setDate(dateFormat(getDateSQL()));
									}
								}
							}
							{
								pnlButton = new JPanel(new GridLayout(0, 2, 2, 2));
								pnlCenter.add(pnlButton, BorderLayout.CENTER);
								pnlButton.setPreferredSize(new Dimension(400, 0));
								pnlButton.setBorder(FncGlobal.lineBorder);

								{
									{
										btnGenerate = new _JButton("Generate");
										pnlButton.add(btnGenerate);
										btnGenerate.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(ActionEvent e) {

												new Thread(new PdAndBBCancelThread()).start();
											}
										});
									}
									{
										btnPreview = new _JButton("Preview");
										pnlButton.add(btnPreview);
										btnPreview.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(ActionEvent e) {
												if (rbtnPastDue.isSelected()) {
													Preview();
												}
												if (rbtnBuyBack.isSelected()) {
													Preview();
												}

												if (rbtnPCancel.isSelected()) {
													Preview();
												}
											}
										});
									}
								}
							}
						}
					}

				}//pnlNorth
				{
					pnlCenter_Table = new JPanel();
					pnlMain.add(pnlCenter_Table, BorderLayout.CENTER);
					pnlCenter_Table.setBorder(components.JTBorderFactory.createTitleBorder(""));// XXX
					pnlCenter_Table.setLayout(new BorderLayout(5,5));
					{

						cmbReasonModel = new DefaultComboBoxModel(new String[] {"","UNCOMPLIED COMMITMENT", "BACK OUT", "FULL SETTLED", "TRANSFER OF RIGHTS", "NON PAYMENT"});
						cmbReason = new JComboBox();
						cmbReason.setModel(cmbReasonModel);
						cmbReason.addActionListener(this);
					}
					{

						cmbRemarksModel = new DefaultComboBoxModel(new String[] {"","UNCOMPLIED COMMITMENT", "BACK OUT", "FULL SETTLED", "TRANSFER OF RIGHTS", "NON PAYMENT"});
						cmbRemarks = new JComboBox();
						cmbRemarks.setModel(cmbRemarksModel);
						cmbRemarks.addActionListener(this);
					}
					{
						

						scrollPastDue = new _JScrollPaneMain();
						pnlCenter_Table.add(scrollPastDue, BorderLayout.CENTER);
						scrollPastDue.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblPastDue.clearSelection();
							}
						});
						{

							modelPastDue = new _model_PastDue();
							modelPastDue.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowHeaderPastDue.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderPastDue.getModel()).addElement(modelPastDue.getRowCount());
									}
								}
							});


							tblPastDue = new _JTableMain(modelPastDue);
							scrollPastDue.setViewportView(tblPastDue);
							tblPastDue.setHorizontalScrollEnabled(true);
							modelPastDue.setEditable(true);
							tblPastDue.hideColumns("Tag","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name", "TR Date", "OR Date", "OB - Company","OB - Bank","Due Date (Bank)", "TCT No.","With NTC","CTS Notarized","Email Add.","Reason For Buyback","Remarks","Months PD");
							tblPastDue.addMouseListener(this);

						}
						{
							rowHeaderPastDue = tblPastDue.getRowHeader();
							rowHeaderPastDue.setModel(new DefaultListModel());
							scrollPastDue.setRowHeaderView(rowHeaderPastDue);
							scrollPastDue.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{

							scrollNSPTotal = new _JScrollPaneTotal(scrollPastDue);
							pnlCenter_Table.add(scrollNSPTotal, BorderLayout.SOUTH);
							{
								modelNSPTotal = new _model_PastDue();
								modelNSPTotal.addRow(new Object[] {
										null,//"Tag", // 0
										null,//"Unit", // 1
										null,//"Client Name", // 2
										null,//"Buyer Type", // 3
										null,//"Sales Division", // 4
										null,//"Contact No.", // 5
										null,//"House Model", // 6
										null,//"Acct. Status",// 7
										null,//"House Status", // 8  
										null,//"TR Date", // 9
										null,//"OR Date", // 10
										null,//"Default Date", // 11
										null,//"Last Date Paid", // 12 
										null,//"Days PD", // 13
										null,//"Days PD", // 13
										null,//"Payments Stage", // 14
										null,//"Last Phone Follow up", // 15
										null,//"Email Add.", // 16
										null,//"PN No.", // 17 -- ADD FUNCTION RIGHT CLICK TO SEE THE PN HISTORY AS PER KIM
										null,//"Notice of Default", // 18
										null,//"Notice of Final Demand", // 19
										null,//"Notice of Cancel", // 20
										"Total",//"Maceda", // 21 -- BOOLEAN- CheckBox
										0.00,//"NSP", // 22
										null,//"Entity ID", // 23
										null,//"Project ID", // 24
										null,//"PBL ID", // 25
										null,//"Seq ID", // 26
										null,//"Unit ID", //27
										null,//"Part ID", // 28
										null,//"LastSchedDate", //29
										null,//"Phase", // 30
										null,//"Project Name", // 31
										0.00,//"OB - Company", // 32
										0.00,//"OB - Bank", // 33
										null,//"Due Date (Bank)", // 34
										null,//"TCT No.", // 35
										null,//"With NTC", //36
										null,//"CTS Notarized" //37
										null,//"With NTC", //36
										null//"CTS Notarized" //37

								});

								tblNSPTotal = new _JTableTotal(modelNSPTotal, tblPastDue);
								scrollNSPTotal.setViewportView(tblNSPTotal);
								tblNSPTotal.setTotalLabel(22);
								//tblNSPTotal.hideColumns("Tag","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name", "TR Date", "OR Date");
								tblNSPTotal.hideColumns("Tag","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name", "TR Date", "OR Date", "OB - Company","OB - Bank","Due Date (Bank)", "TCT No.","With NTC","CTS Notarized","Email Add.","Reason For Buyback","Remarks","Months PD");

							}
						}
					}
				}
			}


		} catch (Exception e) {
		}

	}
	private void FormLoad(){
		getDefaultCompany();
		setEnabled();
	}
	private void getDefaultCompany(){

		co_id = "02";
		company = "CENQHOMES DEVELOPMENT CORPORATION" ;
		company_logo = "cenq_logo.jpg";

		txtCompany.setText(company);
		lookupCompany.setValue(co_id);
		rbtnPastDue.setEnabled(true);
		rbtnBuyBack.setEnabled(true);
		rbtnPCancel.setEnabled(true);
	}

	private void setEnabled(){
		this.setComponentsEnabled(pnlCenter, false);
	}
	
	private boolean toGenerate(){
		if(cmbAccountStatus.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select account status", "Generate", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(cmbSalesDiv.getSelectedItem() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select sales division", "Generate", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void setChange(){

		if (rbtnPastDue.isSelected()) {
			this.setComponentsEnabled(pnlCenter, true);

			lblSalesDiv.setEnabled(false);
			cmbSalesDiv.setEnabled(false);

			lblCancellation.setEnabled(false);
			cmbCancellation.setEnabled(false);
			tblPastDue.hideColumns("Tag","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name", "TR Date", "OR Date", "OB - Company","OB - Bank","Due Date (Bank)", "TCT No.","With NTC","CTS Notarized","Email Add.","Reason For Buyback","Remarks","Months PD");
			tblNSPTotal.hideColumns("Tag","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name", "TR Date", "OR Date", "OB - Company","OB - Bank","Due Date (Bank)", "TCT No.","With NTC","CTS Notarized","Email Add.","Reason For Buyback","Remarks","Months PD");
			tblNSPTotal.showColumns("Notice of Default","Notice of Final Demand","Notice of Cancel","Days PD","NSP");
			tblPastDue.showColumns("Notice of Default","Notice of Final Demand","Notice of Cancel","Days PD","NSP");

		}

		if (rbtnBuyBack.isSelected()) {

			this.setComponentsEnabled(pnlCenter, false);
			this.setComponentsEnabled(pnlProject, true);
			this.setComponentsEnabled(pnlButton, true);

			lblDueDate.setEnabled(true);
			dteDueDate.setEnabled(true);

			tblPastDue.hideColumns("Tag","Entity ID", "Project ID","PBL ID", "Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name", "TR Date", "OR Date","Buyer Type","With NTC","CTS Notarized","Notice of Default","Notice of Final Demand","Notice of Cancel","Days PD","NSP");
			tblNSPTotal.hideColumns("Tag","Entity ID", "Project ID","PBL ID","Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name", "TR Date", "OR Date","Buyer Type","With NTC","CTS Notarized","Notice of Default","Notice of Final Demand","Notice of Cancel","Days PD","NSP");
			tblPastDue.showColumns("Tag","OB - Company", "OB - Bank","Due Date (Bank)", "TCT No.","Reason For Buyback","Remarks","Months PD");
			tblNSPTotal.showColumns("Tag","OB - Company", "OB - Bank","Due Date (Bank)", "TCT No.","Reason For Buyback","Remarks","Months PD");

		}
		if (rbtnPCancel.isSelected()) {
			this.setComponentsEnabled(pnlCenter, true);
			lblMonthPD.setEnabled(false);
			cmbMonthPD.setEnabled(false);
			lblCancellation.setEnabled(true);
			cmbCancellation.setEnabled(true);
			tblPastDue.hideColumns("Tag","Entity ID", "Project ID", "PBL ID", "Seq ID", "Unit ID", "Part ID", "LastSchedDate", "Phase", "Project Name","OB - Company","OB - Bank","Due Date (Bank)", "TCT No.","Reason For Buyback","Remarks","Months PD","Days PD");
			tblNSPTotal.hideColumns("Tag", "Entity ID", "Project ID", "PBL ID", "Seq ID", "Unit ID", "Part ID", "LastSchedDate", "Phase", "Project Name","OB - Company","OB - Bank","Due Date (Bank)", "TCT No.","Reason For Buyback","Remarks","Months PD","Days PD");
			tblNSPTotal.showColumns("TR Date", "OR Date","Buyer Type","With NTC","CTS Notarized","Notice of Default","Notice of Final Demand","Notice of Cancel","Months PD","NSP");
			tblPastDue.showColumns("TR Date", "OR Date","Buyer Type","With NTC","CTS Notarized","Notice of Default","Notice of Final Demand","Notice of Cancel","Months PD","NSP");

		}
	}

	@Override
	public void lookupPerformed(LookupEvent e) {

		if( e.getSource().equals(lookupCompany)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				co_id = data[0].toString();
				company = data[1].toString();
				company_logo = data[3] == null ? "" : data[3].toString() ;
				txtCompany.setText(company);

				rbtnPastDue.setEnabled(true);
				rbtnBuyBack.setEnabled(true);
				rbtnPCancel.setEnabled(true);

			}
		}

		if( e.getSource().equals(lookupProject)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if (data != null) {
				proj_id = data[0].toString();
				proj_name = data[1].toString();

				txtProject.setText(proj_name);
			}
		}
	}

	public class PdAndBBCancelThread implements Runnable{ 

		private String hdmf_group_id;
		private String buyer_status = "All";;
		private int trStage = 0;

		private String months_pd;

		@SuppressWarnings({ "unchecked" })
		@Override
		public void run() {
			
			if(toGenerate()){

			FncGlobal.startProgress("Please Wait. . .");

			proj_name = lookupProject.getText().toString();
			if (cmbBuyerType.getSelectedIndex() == 0) {
				hdmf_group_id = "All";
			}

			if (cmbBuyerType.getSelectedIndex() == 1) {
				hdmf_group_id = "02";
				//table_name = "ihf_due_accounts";
			}

			if (cmbBuyerType.getSelectedIndex() == 2) {
				hdmf_group_id = "04";
				//table_name ="pagibig_due_accounts";
			}

			if (cmbAccountStatus.getSelectedIndex() == 0 ) {
				buyer_status = "All";
			}
			if (cmbAccountStatus.getSelectedIndex() == 1 ) {
				buyer_status = "26";
			}
			if (cmbAccountStatus.getSelectedIndex() == 2 ) {
				buyer_status = "28";
			}
			if (cmbAccountStatus.getSelectedIndex() == 3 ) {
				buyer_status = "25";
			}
			if (cmbAccountStatus.getSelectedIndex() == 4 ) {
				buyer_status = "33";
			}

			if (rbtnPastDue.isSelected()) {

				if (hdmf_group_id.equals("02")) {
					labelType = "IHF "+"Past Due Accounts"; 
				}
				if (hdmf_group_id.equals("04")) {
					labelType = "Pag-Ibig "+"Past Due Accounts";
				}

				trStage  = 0;

				if (cmbMonthPD.getSelectedIndex() == 0 ) {
					months_pd = "All";
				}
				if (cmbMonthPD.getSelectedIndex() == 1 ) {
					months_pd = "1";
				}
				if (cmbMonthPD.getSelectedIndex() == 2 ) {
					months_pd = "2";
				}
				if (cmbMonthPD.getSelectedIndex() == 3 ) {
					months_pd = "3";
				}

				if (cmbMonthPD.getSelectedIndex() == 4 ) {
					months_pd = "";

					if (hdmf_group_id.equals("02")) {
						labelType = "IHF "+"Past Due Accounts (TR Stage)";

					}if (hdmf_group_id.equals("04")) {
						labelType = "Pag-Ibig "+"Past Due Accounts (TR Stage)";
					}

					trStage  = 1;
				}

				rowHeaderPastDue.setModel(new DefaultListModel());
				method.displayPastDue_New(modelPastDue, rowHeaderPastDue, proj_id,  months_pd,trStage, buyer_status,hdmf_group_id,dteDueDate.getDate());
				scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));
			}

			if (rbtnBuyBack.isSelected()) {
				rowHeaderPastDue.setModel(new DefaultListModel());
				labelType = "IHF and Pag-Ibig Accounts for BuyBack";
				method.displayBuyBank_new(modelPastDue, rowHeaderPastDue, proj_id,dteDueDate.getDate());
				scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));
			}

			if (rbtnPCancel.isSelected()) {

				rowHeaderPastDue.setModel(new DefaultListModel());
				method._displayCancellation_new(modelPastDue, rowHeaderPastDue, proj_id, cmbSalesDiv.getSelectedIndex(),cmbAccountStatus.getSelectedIndex(),cmbBuyerType.getSelectedIndex(),dteDueDate.getDate(),UserInfo.EmployeeCode,cmbCancellation.getSelectedIndex());
				scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));

			}

			System.out.println("BUYER TYPE: " + hdmf_group_id);
			tblPastDue.packAll();
			computeTotal();
			updateCommit();

			FncGlobal.stopProgress();
			}
		}
	}
	private void computeTotal() {

		BigDecimal totalNSP = new BigDecimal("0.00");
		BigDecimal totalOBC = new BigDecimal("0.00");
		BigDecimal totalOBB = new BigDecimal("0.00");

		for (int x = 0; x < modelPastDue.getRowCount(); x++) {
			System.out.println("BB" + modelPastDue.getColumnName(33));
			BigDecimal totalamountNSP = (BigDecimal) modelPastDue.getValueAt(x, 23);
			BigDecimal totalamountOBC = (BigDecimal) modelPastDue.getValueAt(x, 33);
			BigDecimal totalamountOBB = (BigDecimal) modelPastDue.getValueAt(x, 34);

			try {

				totalNSP = totalNSP.add(totalamountNSP);
				totalOBC = totalOBC.add(totalamountOBC);
				totalOBB = totalOBB.add(totalamountOBB);

			} catch (Exception e1) { }
		}

		modelNSPTotal.setValueAt(totalNSP, 0, 23);
		modelNSPTotal.setValueAt(totalOBC, 0, 33);
		modelNSPTotal.setValueAt(totalOBB, 0, 34);
	}

	@SuppressWarnings({ "unchecked" })
	private void updateCommit() {
		scrollNSPTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));
		tblPastDue.packAll();

		for (int row = 0; row < tblPastDue.getRowCount(); row++) {
			((DefaultListModel) rowHeaderPastDue.getModel()).setElementAt(row + 1, row);
		}
	}

	public Date dateFormat(String dates){

		DateFormat formatter ; 
		Date date = null ;

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = (Date)formatter.parse(dates);
		} catch (ParseException e) {
		} 

		return date;
	}

	private  String getDateSQL(){
		pgSelect db = new pgSelect();
		db.select("SELECT CURRENT_DATE");
		return db.Result[0][0].toString();

	}

	private void Preview(){

		if (rbtnPastDue.isSelected()) {

			List<TD_Past_Due> list = new ArrayList<TD_Past_Due>();

			for (int i = 0; i < modelPastDue.getRowCount(); i++) {

				String proj_id = (String) modelPastDue.getValueAt(i, 25);
				String pbl_id = (String) modelPastDue.getValueAt(i, 26);

				String unit_pbl =  (String) modelPastDue.getValueAt(i, 1);

				String client_name = (String) modelPastDue.getValueAt(i, 2);
				String buyer_type = (String) modelPastDue.getValueAt(i, 3);
				String account_status = (String) modelPastDue.getValueAt(i, 7);
				String house_status = (String) modelPastDue.getValueAt(i, 8);
				String house_model = (String) modelPastDue.getValueAt(i, 6);
				String sale_div =  (String) modelPastDue.getValueAt(i, 4);
				String contact_no =  (String) modelPastDue.getValueAt(i, 5);
				Timestamp last_paid_date = (Timestamp) modelPastDue.getValueAt(i, 12);
				Timestamp default_date =  (Timestamp) modelPastDue.getValueAt(i, 11);
				Integer months_pd =  (Integer) modelPastDue.getValueAt(i, 13);
				String payment_stage =  (String) modelPastDue.getValueAt(i, 15);
				BigDecimal nsp =  (BigDecimal) modelPastDue.getValueAt(i, 23);
				String phase =  (String) modelPastDue.getValueAt(i, 31);

				pgSelect dbs = new pgSelect();
				
				dbs.select("select phase,block,lot from mf_unit_info where proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"'");	

				//for (int x = 0; x < dbs.getRowCount(); x++) {
					 ph = (String) dbs.Result[0][0];
					 blk = (String) dbs.Result[0][1];
					 lot = (String) dbs.Result[0][2];

				//}
				
				list.add(new TD_Past_Due(ph, blk, lot, unit_pbl, client_name, buyer_type, account_status, house_status, house_model, sale_div, contact_no, last_paid_date, default_date, months_pd, payment_stage, nsp, phase));

			}

			/*Collections.sort(list, new Comparator<TD_Past_Due>() {
				@Override
				public int compare(TD_Past_Due o1, TD_Past_Due o2) {
					return  o1.getPhase().compareTo(o2.getPhase());
				}
			});
*/
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("lblListof", labelType);
			mapParameters.put("lblBuyerStatus", cmbAccountStatus.getSelectedItem() == null ? ""  : cmbAccountStatus.getSelectedItem().toString() );
			mapParameters.put("list",list);
			mapParameters.put("proj_name",txtProject.getText());
			mapParameters.put("proj_id",lookupProject.getValue());

			FncReport.generateReport("/Reports/rptPast_Due.jasper", "List of Past Due Accounts", mapParameters);
		}
		
		
		if (rbtnBuyBack.isSelected()) {

			List<TD_Past_Due_BuyBack> list_buyback = new ArrayList<TD_Past_Due_BuyBack>();
			pgSelect dbs = new pgSelect();
			for (int j = 0; j < modelPastDue.getRowCount(); j++) {
				Boolean isSelected = (Boolean) modelPastDue.getValueAt(j, 0);

				String entityID = (String) modelPastDue.getValueAt(j, 24);
				String proj_id = (String) modelPastDue.getValueAt(j, 25);
				String pbl_id = (String) modelPastDue.getValueAt(j, 26);
				Integer seqNo = (Integer) modelPastDue.getValueAt(j, 27);

				String client_name = (String) modelPastDue.getValueAt(j, 2);

				BigDecimal obCompany = (BigDecimal) modelPastDue.getValueAt(j, 33);
				BigDecimal obBank = (BigDecimal) modelPastDue.getValueAt(j, 34);
				String tctNo = (String) modelPastDue.getValueAt(j,36);
				Integer monthsPD = (Integer) modelPastDue.getValueAt(j,14);
				String reason = (String) modelPastDue.getValueAt(j,39);
				String remarks = (String) modelPastDue.getValueAt(j,40);

				if (isSelected) {

					dbs.select("select phase,block,lot from mf_unit_info where proj_id = '"+proj_id+"' and pbl_id = '"+pbl_id+"'");	
					for (int x = 0; x < dbs.getRowCount(); x++) {
						 ph = (String) dbs.Result[j][0];
						 blk = (String) dbs.Result[j][1];
						 lot = (String) dbs.Result[j][2];
					}
					
					dbs.select("select (case to_char(scheddate ,'dd') when '07' then '7th' \n" + 
							"               when '14' then '14th' \n" + 
							"               when '21' then '21th'\n" + 
							"               when '28' then '28th' end) as due_date \n" + 
							" from rf_client_schedule where part_id = '013' \n" + 
							" and entity_id = '"+entityID+"'\n" + 
							" and proj_id = '"+proj_id+"'\n" + 
							" and pbl_id = '"+pbl_id+"'\n" + 
							" and seq_no = "+seqNo+"\n" + 
							" and status_id = 'A'\n" + 
							" limit 1 \n" + 
							"");	
					
					for (int i = 0; i < dbs.getRowCount(); i++) {
						 dueDate = (String) dbs.Result[i][0];
					}
					
					list_buyback.add(new TD_Past_Due_BuyBack(ph, blk, lot, client_name, obCompany, obBank, dueDate, monthsPD, reason, tctNo, remarks));

				}
			}

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("list",list_buyback);
			mapParameters.put("proj_name",txtProject.getText());
			mapParameters.put("proj_id",lookupProject.getValue());
			mapParameters.put("requestedby",UserInfo.FullName);

			FncReport.generateReport("/Reports/rptPreviewBuyBack.jasper", "BUYBACK FORM", mapParameters);

		}
		
		if (rbtnPCancel.isSelected()) {

			
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
			ArrayList<String> listSeq = new ArrayList<String>();
			
			int sales_div = cmbSalesDiv.getSelectedIndex();
			int buyer_type = cmbBuyerType.getSelectedIndex();
			int buyer_status = cmbCancellation.getSelectedIndex();

			ArrayList<String> status;
			String v_status = "";
			if (buyer_status == 3){
				
				v_status = "OFFICIAL RESERVATIONS [OR]";
			}
			
			if (buyer_status == 2 ||  buyer_status == 1){
				
				v_status = "TEMPORARY RESERVATIONS [TR]";
			}
			
			if (buyer_status == 0){
				v_status = "TEMPORARY RESERVATIONS [TR],OFFICIAL RESERVATIONS [OR] ";
			}
			
			ArrayList<String> saleslist;

			ArrayList<String> buyerlist;

			String v_sales_div = "";

			if (sales_div ==0) {
				v_sales_div = "BUSINESS DEVELOPMENT TEAM 1,BUSINESS DEVELOPMENT TEAM 2,BUSINESS DEVELOPMENT TEAM 3,BUSINESS DEVELOPMENT TEAM 4,BUSINESS DEVELOPMENT DIVISION";
			}
			if (sales_div == 1){

				v_sales_div = "BUSINESS DEVELOPMENT TEAM 1";
			}
			if (sales_div == 2){

				v_sales_div = "BUSINESS DEVELOPMENT TEAM 2";
			}
			if (sales_div == 3){

				v_sales_div = "BUSINESS DEVELOPMENT TEAM 3";
			}
			if (sales_div == 4){

				v_sales_div = "BUSINESS DEVELOPMENT TEAM 4";
			}

			String  v_buyertype = "";

			if (buyer_type == 0) {
				v_buyertype = "IHF,PAGIBIG,CASH,BANK FINANCE";
			}
			if (buyer_type == 1) {
				v_buyertype = "IHF";
			}

			if (buyer_type == 2) {
				v_buyertype = "PAGIBIG";
			}
			
			if (buyer_type == 3) {
				v_buyertype = "CASH";
			}
			
			if(buyer_type == 4){
				v_buyertype = "BANK FINANCE";
			}

			for(int x= 0; x<modelPastDue.getRowCount(); x++){
				
				
				
				String entity_id = (String) modelPastDue.getValueAt(x, 24);
				String proj_id = (String) modelPastDue.getValueAt(x, 25);
				String pbl_id = (String) modelPastDue.getValueAt(x, 26);
				Integer seq_no = (Integer) modelPastDue.getValueAt(x, 27);
				
				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);

			
		}
		
		
		
		
			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			
			
			
			

			System.out.printf("Display value of entity_id: (%s)%n", entity_id);
			System.out.printf("Display value of proj_id: (%s)%n", proj_id);
			System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
			System.out.printf("Display value of seq_no: (%s)%n", seq_no);
			
			listEntityID = new ArrayList<String>(Arrays.asList(entity_id.split("\\s*,\\s*")));
			listProjID = new ArrayList<String>(Arrays.asList(proj_id.split("\\s*,\\s*")));
			listPBL = new ArrayList<String>(Arrays.asList(pbl_id.split("\\s*,\\s*")));
			listSeq = new ArrayList<String>(Arrays.asList(seq_no.split("\\s*,\\s*")));
			saleslist = new ArrayList<String>(Arrays.asList(v_sales_div.split("\\s*,\\s*")));
			buyerlist = new ArrayList<String>(Arrays.asList(v_buyertype.split("\\s*,\\s*")));
			status = new ArrayList<String>(Arrays.asList(v_status.split("\\s*,\\s*")));
			
			System.out.println("***** " + saleslist);
			

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			System.out.printf("Display Company: %s%n", company);
			System.out.printf("Display CO ID: %s%n", co_id);
			System.out.printf("Display sales: %s%n", Arrays.toString(saleslist.toArray()));
			System.out.printf("Display buyerlits: %s%n", Arrays.toString(buyerlist.toArray()));
			System.out.printf("Display Status: %s%n", Arrays.toString(status.toArray()));
			
			System.out.printf("Display Status: ***%s%n",saleslist);
			
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("type", cmbCancellation.getSelectedIndex());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("listEntityID", listEntityID);
			mapParameters.put("listProjID", listProjID);
			mapParameters.put("listPBL", listPBL);
			mapParameters.put("listSeq", listSeq);
			
			mapParameters.put("sales", saleslist);
			mapParameters.put("buyer", buyerlist);
			mapParameters.put("status", status);

			
			
			
			
			
						
			
			FncReport.generateReport("/Reports/rptListofCancellableAccounts.jasper", "List of Cancellable Accounts", mapParameters);

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(tblPastDue)) {
			
		}if (tblPastDue.getSelectedColumn()== 19) {
			System.out.println("REASON TYPE" + tblPastDue.getColumnName(19));
			TableColumn monthcolumn = tblPastDue.getColumnModel().getColumn(19);
			monthcolumn.setCellEditor(new DefaultCellEditor(cmbReason));
		}
		if (tblPastDue.getSelectedColumn()== 20) {
			System.out.println("REMARKS TYPE" + tblPastDue.getColumnModel().getColumn(20));
			TableColumn monthcolumn = tblPastDue.getColumnModel().getColumn(20);
			monthcolumn.setCellEditor(new DefaultCellEditor(cmbRemarks));
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	
	private void generateNTC_First_Notice(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<Integer> listSeq = new ArrayList<Integer>();
		ArrayList<String> saleslist = new ArrayList<String>();

		ArrayList<String> buyerlist = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();
		String v_sales_div = "";

		int sales_div = cmbSalesDiv.getSelectedIndex();
		int buyer_type = cmbBuyerType.getSelectedIndex();
		int buyer_status = cmbCancellation.getSelectedIndex();

		ArrayList<String> status;
		String v_status = "";
		String  v_buyertype = "";
		if (buyer_status == 3){
			
			v_status = "OFFICIAL RESERVATIONS [OR]";
		}
		
		if (buyer_status == 2 ||  buyer_status == 1){
			
			v_status = "TEMPORARY RESERVATIONS [TR]";
		}
		
		if (buyer_status == 0){
			v_status = "TEMPORARY RESERVATIONS [TR],OFFICIAL RESERVATIONS [OR] ";
		}
		
		

	

		if (sales_div ==0) {
			v_sales_div = "BUSINESS DEVELOPMENT TEAM 1,BUSINESS DEVELOPMENT TEAM 2,BUSINESS DEVELOPMENT TEAM 3,BUSINESS DEVELOPMENT TEAM 4";
		}
		if (sales_div == 1){

			v_sales_div = "BUSINESS DEVELOPMENT TEAM 1";
		}
		if (sales_div == 2){

			v_sales_div = "BUSINESS DEVELOPMENT TEAM 2";
		}
		if (sales_div == 3){

			v_sales_div = "BUSINESS DEVELOPMENT TEAM 3";
		}
		if (sales_div == 4){

			v_sales_div = "BUSINESS DEVELOPMENT TEAM 4";
		}

	

		if (buyer_type == 0) {
			v_buyertype = "IHF,PAGIBIG";
		}
		if (buyer_type == 1) {
			v_buyertype = "IHF";
		}

		if (buyer_type == 2) {
			v_buyertype = "PAGIBIG";
		}
		
		for(int x= 0; x<modelPastDue.getRowCount(); x++){
			
			
			
				String entity_id = (String) modelPastDue.getValueAt(x, 24);
				String proj_id = (String) modelPastDue.getValueAt(x, 25);
				String pbl_id = (String) modelPastDue.getValueAt(x, 26);
				Integer seq_no = (Integer) modelPastDue.getValueAt(x, 27);
				
				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(seq_no);
				//listSeq.add(seq_no);

				saleslist.add(String.format("%s", v_sales_div));
				buyerlist.add(String.format("%s", v_buyertype));
		}
		
		
		
		
		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		Integer seq_no = Integer.valueOf(listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", ""));
		
		String sales_list = saleslist.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String buyer_list = buyerlist.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		
		
		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);
		System.out.printf("Display value of sales_list: (%s)%n", sales_list);
		System.out.printf("Display value of buyer_list: (%s)%n", buyer_list);
		
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("sales_list", sales_list);
		mapParameters.put("buyer_list", buyer_list);
		
		
		FncReport.generateReport("/Reports/rptNoticeToComply_IncompleteDocs_FirstNotice.jasper", "Notice to Comply First Notice", mapParameters);
	}
}
