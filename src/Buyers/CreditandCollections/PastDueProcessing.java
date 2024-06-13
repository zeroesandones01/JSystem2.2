package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.SpringUtilities;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components._JButton;
import components._JInternalFrame;
import components._JRadioButton;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JXTextField;
import tablemodel.model_PastDue;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class PastDueProcessing extends _JInternalFrame implements ActionListener, LookupListener,MouseListener,MouseMotionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Past Due Processing";
	public static Dimension frameSize = new Dimension(800, 630);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorth_North;
	private JPanel pnlNorth_Center;
	private _JRadioButton rbtnPastDue;
	private JLabel lblProject;
	private _JLookup lookupProject;
	private _JXTextField txtProject; 
	private JLabel lblBuyerType;

	private DefaultComboBoxModel cmbBuyerTypeModel;
	private JComboBox cmbBuyerType;
	private DefaultComboBoxModel cmbMonthPDModel;
	private JComboBox cmbMonthPD;
	private JLabel lblMonthPD;
	private _JRadioButton rbtnBuyBack;
	private _JRadioButton rbtnPCancel;
	private JPanel pnlNorth_South;
	private _JButton btnGenerate;
	private ButtonGroup btngrpProcess = new ButtonGroup();
	private JPanel pnlCenter;
	private model_PastDue modelPastDue;
	private _JTableMain tblPastDue;
	private JList rowHeadePastDue;
	private JPanel pnlSouth;
	private _JButton btnPost;
	private _JButton btnPreview;
	private _JButton btnClear;
	private String noticeBatchNo;
	private String entityID;
	private String projID;
	private String pblID;
	private Integer seqID;
	private BigDecimal amountDue;
	private Timestamp defaultDate;
	private Timestamp lastpayDate;
	private String partAlias;
	private Timestamp lastschedDate;
	private int Selected;
	private JPanel pnlNorth_North_R;
	private JLabel lblCompany;
	private JPanel pnlCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;
	private _FPromissoryNoteCommintment method = new _FPromissoryNoteCommintment();
	private _FPastDueProcessing pastdue = new _FPastDueProcessing();
	private String co_id;
	private String company;
	private String company_logo;
	private String labelType;
	private int trStage = 0;
	private String table_name = "";
	private String months_pd;
	private String days_pd;
	private JLabel lblAccountStatus;
	private DefaultComboBoxModel cmbAccountStatusModel;
	private JComboBox cmbAccountStatus ;
	private _JScrollPaneTotal scrollNSPTotal;
	private model_PastDue modelNSPTotal;
	private _JTableTotal tblNSPTotal;
	private _JScrollPaneMain scrollPastDue;
	private String buyer_status = "All";
	private String reason;
	private String remarks;
	private JLabel lblSalesDiv;
	private DefaultComboBoxModel cmbSalesDivModel;
	private JComboBox cmbSalesDiv;
	
	
	public PastDueProcessing() {
		super(title, true, true, true, true);
		initGUI();
		formLoad();
	
	}

	public PastDueProcessing(String title) {
		super(title);
		initGUI();
		formLoad();
	}

	public PastDueProcessing(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
		formLoad();
	}


	private void initGUI(){
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		getContentPane().setLayout(new BorderLayout());
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain,BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setLayout(new BorderLayout(5,5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Process"));
				pnlNorth.setPreferredSize(new Dimension(pnlMain.getWidth(), 290));
				pnlNorth.setLayout(new BorderLayout(5,5)); 
				{
					pnlNorth_North = new JPanel(new BorderLayout(5,5));
					pnlNorth.add(pnlNorth_North, BorderLayout.NORTH);
					pnlNorth_North.setPreferredSize(new Dimension(pnlMain.getWidth(), 60));
					pnlNorth_North.setBorder(lineBorder);

					{
						JPanel pnlNorth_North_C = new JPanel();
						pnlNorth_North.add(pnlNorth_North_C, BorderLayout.NORTH);
						pnlNorth_North_C.setPreferredSize(new Dimension(pnlNorth_North.getWidth(), 30));
						pnlNorth_North_C.setLayout(new SpringLayout());
						{
							{
								lblCompany = new JLabel("Company");
								pnlNorth_North_C.add(lblCompany);
								lblCompany.setPreferredSize(new Dimension(74, 25));
							}
							{
								pnlCompany = new JPanel(new BorderLayout(5,5));
								pnlNorth_North_C.add(pnlCompany,BorderLayout.CENTER);
								pnlCompany.setPreferredSize(new Dimension(74, 25));
								{

									lookupCompany = new _JLookup("", "Company", 0) ; /// XXX lookupCompany 
									pnlCompany.add(lookupCompany,BorderLayout.WEST);
									lookupCompany.addLookupListener(this);
									lookupCompany.setLookupSQL(method.getCompany());
									lookupCompany.setPreferredSize(new Dimension(100, 25));
								}
								{
									txtCompany = new JTextField();
									pnlCompany.add(txtCompany,BorderLayout.CENTER);
									txtCompany.setEditable(false);
									txtCompany.setPreferredSize(new Dimension(100, 25));
								} 
							}

							SpringUtilities.makeCompactGrid(pnlNorth_North_C, 1, 2, 2, 2, 2, 2, false);
						}
					}
					{

						pnlNorth_North_R = new JPanel();
						pnlNorth_North.add(pnlNorth_North_R, BorderLayout.CENTER);
						pnlNorth_North_R.setPreferredSize(new Dimension(pnlMain.getWidth(), 30));
						pnlNorth_North_R.setLayout(new GridLayout(0, 3, 2, 2));
						{
							{
								rbtnPastDue = new _JRadioButton("Past Due");
								pnlNorth_North_R.add(rbtnPastDue);
								btngrpProcess.add(rbtnPastDue);
								rbtnPastDue.setActionCommand("PastDue");
								rbtnPastDue.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("List of Past Due "));
										Change(rbtnPastDue);
										computeTotal();

									}
								});
							} 
							{
								rbtnBuyBack = new _JRadioButton("Buyback from Bank");
								pnlNorth_North_R.add(rbtnBuyBack);
								btngrpProcess.add(rbtnBuyBack);
								rbtnBuyBack.setActionCommand("BB");
								rbtnBuyBack.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("List of Buyback from Bank "));
										Change(rbtnBuyBack);
										computeTotal();
									}
								});
							}
							{
								rbtnPCancel= new _JRadioButton("Projected for Cancellation");
								pnlNorth_North_R.add(rbtnPCancel);
								btngrpProcess.add(rbtnPCancel);
								rbtnPCancel.setActionCommand("Cancel");
								rbtnPCancel.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("List of Projected for Cancellation"));
										Change(rbtnPCancel);
										computeTotal();
									}
								});
							}
						}
					}

				}
				{
					pnlNorth_Center = new JPanel();
					pnlNorth.add(pnlNorth_Center, BorderLayout.CENTER);
					pnlNorth_Center.setPreferredSize(new Dimension(pnlMain.getWidth(), 180));
					pnlNorth_Center.setLayout(null);
					pnlNorth_Center.setBorder(lineBorder);
					{
						lblProject = new JLabel("Projects");
						pnlNorth_Center.add(lblProject);
						lblProject.setBounds(4, 9, 100, 22);

					}
					{
						lookupProject = new _JLookup("Project ID", "Search Projects", 0);
						pnlNorth_Center.add(lookupProject);
						lookupProject.setBounds(122, 10, 91, 22);
					//	lookupProject.setLookupSQL(_FPastDueProcessing.lookupProjects());
						lookupProject.addLookupListener(this);
					}
					{
						txtProject  = new _JXTextField();
						pnlNorth_Center.add(txtProject);
						txtProject.setPrompt("Project Name");
						txtProject.setBounds(215, 10, 338, 22);
						txtProject.setEditable(false);

					}
					{
						lblBuyerType = new JLabel("Buyer Type");
						pnlNorth_Center.add(lblBuyerType);
						lblBuyerType.setBounds(4, 35, 100, 22);

					}
					{
						cmbBuyerTypeModel = new DefaultComboBoxModel(
								new String[] {"All Accounts"," In-House Accounts", " Pag-ibig Accounts" });
						cmbBuyerType = new JComboBox();
						pnlNorth_Center.add(cmbBuyerType);
						cmbBuyerType.setModel(cmbBuyerTypeModel);
						cmbBuyerType.setBounds(122, 34, 230, 22);
						cmbBuyerType.setSelectedItem(null);
						cmbBuyerType.addActionListener(this);

					}
					{
						lblMonthPD = new JLabel("Month Past Due");
						pnlNorth_Center.add(lblMonthPD);
						lblMonthPD.setBounds(4, 60, 100, 22);

					}
					{
						cmbMonthPDModel = new DefaultComboBoxModel(
								new String[] { " All", " 1", " 2", " > 2", " TR" });
						cmbMonthPD = new JComboBox();
						pnlNorth_Center.add(cmbMonthPD);
						cmbMonthPD.setModel(cmbMonthPDModel);
						cmbMonthPD.setBounds(122,60, 230, 22);
						cmbMonthPD.setSelectedItem(null);
						cmbMonthPD.addActionListener(this);
					}
					{
						lblAccountStatus = new JLabel("Account Status ");
						pnlNorth_Center.add(lblAccountStatus);
						lblAccountStatus.setBounds(4, lblMonthPD.getY()+25, 100, 22);

					}
					{
						cmbAccountStatusModel = new DefaultComboBoxModel(
								new String[] {" All Status", " Buyback", " Forwarded to Bank", " STB", " REM" });
						cmbAccountStatus = new JComboBox();
						pnlNorth_Center.add(cmbAccountStatus);
						cmbAccountStatus.setModel(cmbAccountStatusModel);
						cmbAccountStatus.setBounds(122,cmbMonthPD.getY()+25, 230, 22);
						cmbAccountStatus.setSelectedItem(null);
						cmbAccountStatus.addActionListener(this);
					}
					{
						lblSalesDiv = new JLabel("Sales Division");
						pnlNorth_Center.add(lblSalesDiv);
						lblSalesDiv.setBounds(4, lblAccountStatus.getY()+25, 100, 22);
					}
					{

						cmbSalesDivModel = new DefaultComboBoxModel(
								new String[] {"All Division","Division 1","Division 2","Division 3","Division 4"});
						cmbSalesDiv = new JComboBox();
						pnlNorth_Center.add(cmbSalesDiv);
						cmbSalesDiv.setModel(cmbSalesDivModel);
						cmbSalesDiv.setBounds(122,cmbAccountStatus.getY()+25, 230, 22);
						cmbSalesDiv.setSelectedItem(null);
						cmbSalesDiv.addActionListener(this);

					}
				}
				{
					pnlNorth_South = new JPanel();
					pnlNorth.add(pnlNorth_South,BorderLayout.SOUTH);
					pnlNorth_South.setLayout(new BorderLayout());
					pnlNorth_South.setPreferredSize(new Dimension(pnlMain.getWidth(), 40));
					{
						btnGenerate = new _JButton("Generate");
						pnlNorth_South.add(btnGenerate,BorderLayout.CENTER);
						btnGenerate.addActionListener(this);
					}
				}
			}
			{
				pnlCenter = new JPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("List of "));// XXX
				pnlCenter.setLayout(new BorderLayout(5,5));

				{

					scrollPastDue = new _JScrollPaneMain();
					pnlCenter.add(scrollPastDue, BorderLayout.CENTER);
					scrollPastDue.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblPastDue.clearSelection();
						}
					});
					{

						modelPastDue = new model_PastDue();
						modelPastDue.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								//Addition of rows
								if(e.getType() == 1){
									((DefaultListModel)rowHeadePastDue.getModel()).addElement(modelPastDue.getRowCount());

									if(modelPastDue.getRowCount() == 0){
										rowHeadePastDue.setModel(new DefaultListModel());
									}
								}
							}
						});

						tblPastDue = new _JTableMain(modelPastDue);
						scrollPastDue.setViewportView(tblPastDue);
						tblPastDue.setHorizontalScrollEnabled(true);
						tblPastDue.addMouseMotionListener(this);
						tblPastDue.addMouseListener(this);
						
						//	tblPastDue.hideColumns("Balance");
						/*tblPastDue.addPropertyChangeListener(new PropertyChangeListener() {//XXX Work Items
							public void propertyChange(PropertyChangeEvent arg0) {
								_JTableMain table = (_JTableMain) arg0.getSource();
								String propertyName = arg0.getPropertyName();

								if (propertyName.equals("tableCellEditor")) {
									int column = table.convertColumnIndexToModel(table.getEditingColumn());
									int row = table.getEditingRow();

									int total_amount = table.convertColumnIndexToModel(table.getColumnModel().getColumnIndex("NSP"));

									if (column != -1 && column != 20 && modelPastDue.getColumnClass(column).equals(BigDecimal.class)) {
										Object oldValue = null;
										try {
											oldValue = ((NumberEditorExt) arg0.getOldValue()).getCellEditorValue();
										} catch (NullPointerException e) {
										}

										if (oldValue != null) {
											if (oldValue instanceof Double) {
												table.setValueAt(new BigDecimal((Double) oldValue), row, column);
												modelPastDue.setValueAt(new BigDecimal((Double) oldValue), row, total_amount);
											}
											if (oldValue instanceof Long) {
												table.setValueAt(new BigDecimal((Long) oldValue), row, column);
												modelPastDue.setValueAt(new BigDecimal((Long) oldValue), row, total_amount);
											}
											computeTotal();
										}
									}
								}
							}
						});*/

						/** Repaint for Highlight **/
						/*tblPastDue.getTableHeader().addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent evt) {
								tblPastDue.repaint();
							}
						});*/
					}
					{
						rowHeadePastDue = tblPastDue.getRowHeader();
						rowHeadePastDue.setModel(new DefaultListModel());
						scrollPastDue.setRowHeaderView(rowHeadePastDue);
						scrollPastDue.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{

						scrollNSPTotal = new _JScrollPaneTotal(scrollPastDue);
						pnlCenter.add(scrollNSPTotal, BorderLayout.SOUTH);
						{
							modelNSPTotal = new model_PastDue();
							modelNSPTotal.addRow(new Object[] {

									null,//	"Tag", // 0
									null,//	"Unit", // 1
									null,//	"Client Name", // 2
									null,//	"Buyer Type", //3
									null,//	"Sales Division", // 4
									null,//	"Contact No.", // 5
									null,//	"House Model", //6
									null,//	"Sold to Bank", //7
									null,//	"Buyback", //8
									null,//	"Moved in", //9
									null,//	"Stage", // 10
									null,//	"TR Date", // 11
									null,//	"OR Date", // 12
									null,//	"Default Date", //13
									null,//	"Last Date Paid", //14 
									null,//	"Month PD", // 15
									null,//	"Days PD", // 16
									null,//	"Payments Made", // 17
									null,//	"Amount Due", // 18
									null,//	"Last Phone Follow up", //19
									null,//	"Email Add.", // 20
									null,//	"PN No.", //21
									null,//	"Notice of Default", //22
									"Total",//	"Notice of Cancellation", //23
									0.00,//	"NSP", // 24
									null,//	"Entity ID", // 25
									null,//	"Project ID", // 26
									null,//	"PBL ID", // 27
									null,//	"Seq ID", // 28
									null,//	"Unit ID", //29
									null,//	"Part ID", // 30
									null,//	"LastSchedDate", //31
									null,//	"Phase", // 32
									null,//	"Project Name", // 33
									0.00,//	"OB - Company", // 34
									0.00,//	"OB - Bank", // 35
									null,//	"Due Date (Bank)", // 36
									null,//	"TCT No.", // 37
									null,//	"Reason for Buyback", // 38
									null,//	"Remarks",//39
									null,//	"With NTC", //40
									null//	"CTS Notarized" //41

							});

							tblNSPTotal = new _JTableTotal(modelNSPTotal, tblPastDue);
							scrollNSPTotal.setViewportView(tblNSPTotal);
							tblNSPTotal.setTotalLabel(23);
							tblNSPTotal.hideColumns("Stage","Days PD","Tag","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name", "TR Date", "OR Date");
						}
						//scrollCommitAmountTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
					}
				}

				{/*
					scrollPastDue = new _JScrollPaneMain();

					pnlCenter.add(scrollPastDue, BorderLayout.CENTER);
					scrollPastDue.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblPastDue.clearSelection();
						}
					});
					{

						modelPastDue = new model_PastDue();
						modelPastDue.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								//Addition of rows
								if(e.getType() == 1){
									((DefaultListModel)rowHeadePastDue.getModel()).addElement(modelPastDue.getRowCount());

									if(modelPastDue.getRowCount() == 0){
										rowHeadePastDue.setModel(new DefaultListModel()); ,
									}
								}
							}
						});

						tblPastDue = new _JTableMain(modelPastDue);
						scrollPastDue.setViewportView(tblPastDue);
						modelPastDue.setEditable(true);
						tblPastDue.setHorizontalScrollEnabled(true);

						//tblPastDue.hideColumns("Tag","Entity ID","Project ID","PBL ID",	"Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name");
						tblPastDue.packAll();
					}
					{
						rowHeadePastDue = tblPastDue.getRowHeader();
						rowHeadePastDue.setModel(new DefaultListModel());
						scrollPastDue.setRowHeaderView(rowHeadePastDue);
						scrollPastDue.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));

					}
					{

						scrollNSPTotal = new _JScrollPaneTotal(scrollPastDue);
						pnlCenter.add(scrollNSPTotal, BorderLayout.CENTER);
						{
							modelNSPTotal = new model_PastDue();
							modelNSPTotal.addRow(new Object[] {
									null, // 0
									null, // 1
									null, // 2
									null, // 3
									null, // 4
									null, //5
									null, //6
									null, //7
									null, //8
									null, // 9
									null, //10
									null, //11 
									null, // 12
									null, // 13
									null, // 14
									null, // 15
									null, //16
									null, // 17
									null, //18
									null, //19
									"Total", //20
									0.00, // 21
									null, // 22
									null, // 23
									null, // 24
									null, // 25
									null, //26
									null, // 27
									null, //28
									null, // 29
									null // 30
									});

							tblNSPTotal = new _JTableTotal(modelNSPTotal, tblPastDue);
							scrollNSPTotal.setViewportView(tblNSPTotal);
							tblNSPTotal.setTotalLabel(20);
						}
						//scrollNSPTotal.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
					}
				 */}
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(pnlMain.getWidth(), 40));
				pnlSouth.setLayout(new GridLayout(0, 3, 2, 2));
				{
					btnPost = new _JButton("Post");
					pnlSouth.add(btnPost);
					btnPost.addActionListener(this);
				}
				{
					btnPreview = new _JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.addActionListener(this);

				}
				{
					btnClear = new _JButton("Clear");
					pnlSouth.add(btnClear);
					btnClear.addActionListener(this);

				}
			}
		}

		FncTables.bindColumnTables(tblPastDue, tblNSPTotal);
		tblPastDue.hideColumns("Stage","Days PD","Tag","Entity ID","Project ID","PBL ID",	"Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name","OB - Company",	"OB - Bank","Due Date (Bank)", "TCT No.","Reason for Buyback", "TR Date", "OR Date","With NTC","CTS Notarized");
		tblNSPTotal.hideColumns("Stage","Days PD","Tag","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name","OB - Company",	"OB - Bank","Due Date (Bank)", "TCT No.","Reason for Buyback", "TR Date", "OR Date","With NTC","CTS Notarized");

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(cmbBuyerType)) {
			cmbMonthPD.setSelectedItem(null);
			cmbAccountStatus.setSelectedItem(null);

			FncTables.clearTable(modelPastDue);
			rowHeadePastDue.setModel(new DefaultListModel());
			scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));
		}
		if (e.getSource().equals(cmbMonthPD)) {
			cmbAccountStatus.setSelectedItem(null);
			FncTables.clearTable(modelPastDue);
			rowHeadePastDue.setModel(new DefaultListModel());
			scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));
		}

		if (e.getSource().equals(btnGenerate)) {
			RequireInfoMsg();
			tblPastDue.setEnabled(true);

			if (rbtnPastDue.isSelected()) {
				btnState(true, false, true, true);

			}
			if (rbtnBuyBack.isSelected()) {
				btnState(true, false, true, true);
			}
			if (rbtnPCancel.isSelected()) {
				btnState(true, true, true, true);
			}
		}

		if (e.getSource().equals(btnPost)) {

			Selected = 0;

			System.out.println("POST");
			if (modelPastDue.getRowCount() == 0) { // XXX POSTING
				JOptionPane.showMessageDialog(this, "Please generate qualified Account(s) first  ","Invalid",JOptionPane.OK_OPTION);
			}

			for (int x = 0; x < modelPastDue.getRowCount(); x++) {

				Boolean SelectedItem = (Boolean) modelPastDue.getValueAt(x, 0);

				if (SelectedItem) {
					Selected++;
				}
			}

			System.out.println("SELECTED " + Selected);
			if (Selected ==0 ) {
				JOptionPane.showMessageDialog(this, "Please Select Account(s) to be Posted  ");
				return;
				
			}else{

				if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to Recommend these Account(s) for Cancellation?  ", btnPost.getText(), 
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					System.out.println("POSTED SAVE");
					{
						new Thread(new ForPosting()).start();
					}

					JOptionPane.showMessageDialog(this,"Post successfully saved.","Post", JOptionPane.INFORMATION_MESSAGE);			
					Change(rbtnPCancel);	
				}	
			}
			/*
				Boolean SelectedItem = (Boolean) tblPastDue.getModel().getValueAt(x, 0);
				System.out.println("********************" +  SelectedItem);
				if (SelectedItem) {
					Selected++;
				}
				if (Selected==0) {
					JOptionPane.showMessageDialog(this, "Please Select Account(s) to be Posted  ");
					return;
				}else{
					System.out.println("POST IF  SELECTED"  );
					noticeBatchNo = _FPastDueProcessing.getBatchNo();

					if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to Recommend these Account(s) for Cancellation?  ", btnPost.getText(), 
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						System.out.println("POSTED SAVE");
						for (int i = 0; i < tblPastDue.getModel().getRowCount(); i++) {

							Boolean ifSelected = (Boolean) tblPastDue.getModel().getValueAt(i, 0);

							System.out.println("POST IF  SELECTED" + ifSelected +":"+ tblPastDue.getModel().getValueAt(i, 17).toString()  );
							entityID = tblPastDue.getModel().getValueAt(i, 17).toString();
							projID = tblPastDue.getModel().getValueAt(i, 18).toString();
							pblID = tblPastDue.getModel().getValueAt(i, 19).toString();
							seqID = (Integer) tblPastDue.getModel().getValueAt(i, 20);
							unitID = tblPastDue.getModel().getValueAt(i, 21).toString();
							partID = tblPastDue.getModel().getValueAt(i, 22).toString();
							amountDue = (BigDecimal) tblPastDue.getModel().getValueAt(i, 15);
							defaultDate = (Timestamp) tblPastDue.getModel().getValueAt(i, 10);
							lastpayDate = (Timestamp) tblPastDue.getModel().getValueAt(i, 11);
							lastschedDate =(Timestamp) tblPastDue.getModel().getValueAt(i, 23);
							partAlias =  tblPastDue.getModel().getValueAt(i, 9).toString();

							if (ifSelected) {

								new Thread(new ForPosting()).start();
								System.out.println("----SAVE" );
								Change(rbtnPCancel);	
							}
						}
						JOptionPane.showMessageDialog(this,"Post successfully saved.","Post", JOptionPane.INFORMATION_MESSAGE);			
					}				
				}
			 */
		}

		if (e.getSource().equals(btnClear)) {

			int response = JOptionPane.showConfirmDialog(this," Are you sure you want to Clear all fields?   ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				clear();
			}
		}

		if (e.getSource().equals(btnPreview)) {
			/*	if (rbtnPastDue.isSelected()) {
				new Thread(new ForPreview()).start();	
			}
			if (rbtnPCancel.isSelected()) {
				new Thread(new ForPreview()).start();	
			}*/
			if (rbtnBuyBack.isSelected()) {

				new Thread(new ForPreviewBuyback()).start();	

			}
			if (rbtnPastDue.isSelected()) {
				new Thread(new ForPreview()).start();
			}

			if (rbtnPCancel.isSelected()) {
				new Thread(new ForPreviewProjCancel()).start();

			}
		}
	}

	@Override
	public void lookupPerformed(LookupEvent e) {
		if(e.getSource().equals(lookupProject)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();
			if(data != null){

				cmbBuyerType.setSelectedItem(null);
				cmbMonthPD.setSelectedItem(null);
				FncTables.clearTable(modelPastDue);
				rowHeadePastDue.setModel(new DefaultListModel());
				scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));

				txtProject.setText(data[1].toString());
				txtProject.setCaretPosition(0);
				System.out.println("PROJECT : "+ data[1]);

				btnState(true, false, false, true);

			}
		}
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
	}

	private void Change(_JRadioButton rbtn){

		if(rbtn.equals(rbtnPastDue)){
			lblProject.setEnabled(true);
			lookupProject.setEnabled(true);
			txtProject.setEnabled(true);

			lblBuyerType.setEnabled(true);
			cmbBuyerType.setEnabled(true); 

			lblMonthPD.setEnabled(true);
			cmbMonthPD.setEnabled(true);

			lblAccountStatus.setEnabled(true);
			cmbAccountStatus.setEnabled(true);

			lblSalesDiv.setEnabled(false);
			cmbSalesDiv.setEnabled(false);

			//btnPost.setEnabled(false);
			btnState(false, false, false, false);
			tblPastDue.hideColumns("Stage","Days PD","Tag","Entity ID","Project ID","PBL ID",	"Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name","OB - Company",	"OB - Bank","Due Date (Bank)", "TCT No.","Reason for Buyback","Remarks", "TR Date", "OR Date","With NTC","CTS Notarized");
			tblNSPTotal.hideColumns("Stage","Days PD","Tag","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name","OB - Company",	"OB - Bank","Due Date (Bank)", "TCT No.","Reason for Buyback","Remarks", "TR Date", "OR Date","With NTC","CTS Notarized");


		}
		if (rbtn.equals(rbtnBuyBack)) {

			lblProject.setEnabled(true);
			lookupProject.setEnabled(true);
			txtProject.setEnabled(true);

			lblBuyerType.setEnabled(false);
			cmbBuyerType.setEnabled(false);

			lblMonthPD.setEnabled(false);
			cmbMonthPD.setEnabled(false);

			lblAccountStatus.setEnabled(false);
			cmbAccountStatus.setEnabled(false);

			lblSalesDiv.setEnabled(false);
			cmbSalesDiv.setEnabled(false);

			//btnPost.setEnabled(false);
			btnState(false, false, false, false);
			tblPastDue.hideColumns("Stage","Days PD","Tag","Entity ID","Project ID","PBL ID",	"Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name", "TR Date", "OR Date","Buyer Type","With NTC","CTS Notarized");
			tblNSPTotal.hideColumns("Stage","Days PD","Tag","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","Part ID","LastSchedDate","Phase","Project Name", "TR Date", "OR Date","Buyer Type","With NTC","CTS Notarized");
			tblPastDue.showColumns("OB - Company",	"OB - Bank","Due Date (Bank)", "TCT No.","Reason for Buyback","Remarks");
			tblNSPTotal.showColumns("OB - Company",	"OB - Bank","Due Date (Bank)", "TCT No.","Reason for Buyback","Remarks");
		}
		if (rbtn.equals(rbtnPCancel)) {

			lblProject.setEnabled(true);
			lookupProject.setEnabled(true);
			txtProject.setEnabled(true);

			lblBuyerType.setEnabled(true);
			cmbBuyerType.setEnabled(true);

			lblMonthPD.setEnabled(false);
			cmbMonthPD.setEnabled(false);

			lblAccountStatus.setEnabled(true);
			cmbAccountStatus.setEnabled(true);

			lblSalesDiv.setEnabled(true);
			cmbSalesDiv.setEnabled(true);

			//btnPost.setEnabled(true);
			btnState(false, false, false, false);
			tblPastDue.hideColumns("Stage","Days PD","Entity ID", "Project ID", "PBL ID", "Seq ID", "Unit ID", "Part ID", "LastSchedDate", "Phase", "Project Name","OB - Company","OB - Bank","Due Date (Bank)", "TCT No.","Reason for Buyback","Remarks");
			tblNSPTotal.hideColumns("Stage","Days PD","Tag", "Entity ID", "Project ID", "PBL ID", "Seq ID", "Unit ID", "Part ID", "LastSchedDate", "Phase", "Project Name","OB - Company","OB - Bank","Due Date (Bank)", "TCT No.","Reason for Buyback","Remarks");
			tblNSPTotal.showColumns("Tag", "TR Date", "OR Date","Buyer Type","With NTC","CTS Notarized");
			tblPastDue.showColumns("Tag", "TR Date", "OR Date","Buyer Type","With NTC","CTS Notarized");
			modelPastDue.setEditable(true);
			modelNSPTotal.setEditable(true);

		}

		lookupProject.setText("");
		txtProject.setText("");
		cmbBuyerType.setSelectedItem(null);
		cmbMonthPD.setSelectedItem(null);
		rowHeadePastDue.setModel(new DefaultListModel());
		FncTables.clearTable(modelPastDue);
	}


	public class PdAndBBCancelThread implements Runnable{ 

		private String hdmf_group_id;
		private String sales_div;

		@Override
		public void run() {
			computeTotal();
			updateCommit();
			projID = lookupProject.getText().toString();
			if (cmbBuyerType.getSelectedIndex() == 0) {
				hdmf_group_id = "All";
			}

			if (cmbBuyerType.getSelectedIndex() == 1) {
				hdmf_group_id = "02";
				table_name = "ihf_due_accounts";
			}

			if (cmbBuyerType.getSelectedIndex() == 2) {
				hdmf_group_id = "04";
				table_name ="pagibig_due_accounts";
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
					months_pd = "months_past_due >=  1";
				}
				if (cmbMonthPD.getSelectedIndex() == 1 ) {
					months_pd = "months_past_due =  1";
				}
				if (cmbMonthPD.getSelectedIndex() == 2 ) {
					months_pd = "months_past_due =  2";
				}
				if (cmbMonthPD.getSelectedIndex() == 3 ) {
					months_pd = "months_past_due >=  3";
				}

				if (cmbMonthPD.getSelectedIndex() == 4 ) {
					months_pd = "months_past_due >=  1";

					if (hdmf_group_id.equals("02")) {
						labelType = "IHF "+"Past Due Accounts (TR Stage)";

					}if (hdmf_group_id.equals("04")) {
						labelType = "Pag-Ibig "+"Past Due Accounts (TR Stage)";
					}
					
					trStage  = 1;
				}

				rowHeadePastDue.setModel(new DefaultListModel());
				_FPastDueProcessing.displayPastDue(modelPastDue, rowHeadePastDue, table_name, projID,  months_pd,trStage, buyer_status,hdmf_group_id );
				scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));
			}

			if (rbtnBuyBack.isSelected()) {
				rowHeadePastDue.setModel(new DefaultListModel());
				labelType = "IHF and Pag-Ibig Accounts for BuyBack";
				_FPastDueProcessing.displayBuyBank(modelPastDue, rowHeadePastDue, projID);
				scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));
			}
			if (rbtnPCancel.isSelected()) {
				days_pd = "days_past_due >=  60";

				if (cmbSalesDiv.getSelectedIndex() == 0 ) {
					sales_div = "All";
				}
				if (cmbSalesDiv.getSelectedIndex() == 1 ) {
					sales_div = "1";
				}
				if (cmbSalesDiv.getSelectedIndex() == 2 ) {
					sales_div = "2";
				}
				if (cmbSalesDiv.getSelectedIndex() == 3 ) {
					sales_div = "3";
				}
				if (cmbSalesDiv.getSelectedIndex() == 4 ) {
					sales_div = "4";
				}

				if (hdmf_group_id.equals("02")) {
					labelType = "IHF "+"Accounts Projected for Cancellation"; 
				}
				if (hdmf_group_id.equals("04")) {
					labelType = "Pag-Ibig "+"Accounts Projected for Cancellation";
				}
				if (hdmf_group_id.equals("All")) { 
					labelType = "IHF and Pag-Ibig "+"Accounts Projected for Cancellation";
				}
 
				rowHeadePastDue.setModel(new DefaultListModel());
				_FPastDueProcessing.displayCancellation(modelPastDue, rowHeadePastDue, projID, table_name, days_pd,sales_div,buyer_status,hdmf_group_id);
				scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));

			}

			System.out.println("BUYER TYPE: " + hdmf_group_id);
			tblPastDue.packAll();
			computeTotal();
			updateCommit();
		}
	}
	private void RequireInfoMsg(){ 

		if (rbtnPastDue.isSelected()) {
			if (cmbBuyerType.getSelectedItem()== null || txtProject.getText().equals("") || cmbMonthPD.getSelectedItem() == null || cmbAccountStatus.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this,	"Please fill up the required Informations","Incomplete Details", JOptionPane.OK_OPTION);
				return;
			}else{

				new Thread(new PdAndBBCancelThread()).start();
			}
		}
		if (rbtnBuyBack.isSelected()) { 
			if (txtProject.getText().equals("")) {
				JOptionPane.showMessageDialog(this,	"Please fill up the required Informations ","Incomplete Details", JOptionPane.OK_OPTION);
				return;
			}else{
				
				new Thread(new PdAndBBCancelThread()).start();
			}
		}
		if (rbtnPCancel.isSelected()) {
			if (cmbBuyerType.getSelectedItem()== null || txtProject.getText().equals("") || cmbSalesDiv.getSelectedItem()== null || cmbAccountStatus.getSelectedItem()== null) {
				JOptionPane.showMessageDialog(this,"Please fill up the required Informations  ","Incomplete Details", JOptionPane.OK_OPTION);
				return;
			}else{

				new Thread(new PdAndBBCancelThread()).start();
			}
		}
	}

	public class ForPosting implements Runnable{

		private String p_user_id;
		private Integer i_months_pd;
		private Integer i_days_pd;

		@Override
		public void run() { 
			//pgUpdate dbU = new pgUpdate();
			FncGlobal.startProgress("Saving  . . .Please wait ");
			System.out.println("POST IF  SELECTED"  );
			noticeBatchNo = _FPastDueProcessing.getBatchNo();
			for (int i = 0 ;  i < modelPastDue.getRowCount(); i++) {
				Boolean ifSelected =(Boolean) modelPastDue.getValueAt(i, 0);

				if (ifSelected) {
					
					entityID = modelPastDue.getValueAt(i, 25).toString();
					projID = modelPastDue.getValueAt(i, 26).toString();
					pblID = modelPastDue.getValueAt(i, 27).toString().trim();
					seqID = (Integer) modelPastDue.getValueAt(i, 28);
					i_months_pd = (Integer) modelPastDue.getValueAt(i, 15);
					i_days_pd = (Integer) modelPastDue.getValueAt(i, 16);
					amountDue = (BigDecimal) modelPastDue.getValueAt(i, 18);
					defaultDate = (Timestamp) modelPastDue.getValueAt(i, 13);
					lastpayDate = (Timestamp) modelPastDue.getValueAt(i, 14);
					lastschedDate =(Timestamp) modelPastDue.getValueAt(i, 31);
					partAlias =  modelPastDue.getValueAt(i, 10).toString();
					p_user_id = UserInfo.EmployeeCode;
					
					//System.out.println("POST IF  SELECTED" + ifSelected +":"+ modelPastDue.getValueAt(i, 17).toString() + unitID  );
					//_FPastDueProcessing.forPosting(entityID, projID, pblID, seqID, unitID, partID, amountDue,  partAlias, noticeBatchNo, defaultDate, lastpayDate, lastschedDate,dbU);
 
					pgSelect db = new pgSelect();

					db.select("SELECT sp_post_projected_cancellation(\n" + 
							"    '"+entityID+"', ---p_entity_id ,\n" + 
							"    '"+projID+"', ---p_proj_id ,\n" + 
							"    '"+pblID+"', ---p_pbl_id ,\n" + 
							"    "+seqID+", ---i_seq_no ,\n" +  
							"    '"+partAlias+"', ---p_part_stage ,\n" + 
							"    '"+noticeBatchNo+"', ---p_batchno ,\n" +
							"    '"+i_months_pd+"', ---i_months_pd ,\n" +
							"    '"+i_days_pd+"', ---i_days_pd ,\n" + 
							"    '"+amountDue+"', ---n_amountdue ,\n" + 
							"    '"+lastpayDate+"', ---p_lastpaid_date ,\n" +  
							"    '"+defaultDate+"', ---p_default_date ,\n" + 
							"    '"+lastschedDate+"', ---p_lastsched_date ,\n" + 
							"    '"+p_user_id+"', ---p_user_id ,\n" + 
							"    "+ifSelected+") ---p_selected ");
					
					
				}
			}
			FncGlobal.stopProgress();
		}
	}

	private void clear(){
 
		lookupProject.setValue("");
		txtProject.setText("");
		cmbBuyerType.setSelectedItem(null);
		cmbMonthPD.setSelectedItem(null);
		btngrpProcess.clearSelection();
		FncTables.clearTable(modelPastDue);
		rowHeadePastDue.setModel(new DefaultListModel());
		scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));

		rowHeadePastDue.setModel(new DefaultListModel());
		scrollPastDue.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));
		scrollNSPTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));
		computeTotal();

		setEnableComponents(false);
		btnState(false, false, false, false);
	}

	private void btnState(Boolean SGen, Boolean SPost, Boolean SPreview,Boolean SClear){

		btnGenerate.setEnabled(SGen);
		btnPost.setEnabled(SPost); 
		btnPreview.setEnabled(SPreview);
		btnClear.setEnabled(SClear);

	}

	private void formLoad(){

		setEnableComponents(false);
		btnState(false, false, false, false);
		
		getDefaultCompany();
	}

	private void setEnableComponents(Boolean ena){

		super.setEnabled(ena);
		lookupProject.setEnabled(ena);
		txtProject.setEnabled(ena);
		lblProject.setEnabled(ena);

		lblBuyerType.setEnabled(ena);
		cmbBuyerType.setEnabled(ena);

		lblMonthPD.setEnabled(ena);
		cmbMonthPD.setEnabled(ena);

		lblAccountStatus.setEnabled(ena);
		cmbAccountStatus.setEnabled(ena);

		lblSalesDiv.setEnabled(ena);
		cmbSalesDiv.setEnabled(ena);

		if (lookupCompany.getValue().isEmpty()) {
			rbtnPastDue.setEnabled(ena);
			rbtnBuyBack.setEnabled(ena);
			rbtnPCancel.setEnabled(ena);

		}else{
			rbtnPastDue.setEnabled(true);
			rbtnBuyBack.setEnabled(true);
			rbtnPCancel.setEnabled(true);
		}
		btngrpProcess.clearSelection();
	}
 

	public class ForPreviewBuyback implements Runnable{

		@Override
		public void run() {

			Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("lblListof", labelType);
			mapParameters.put("lblBuyerStatus", cmbAccountStatus.getSelectedItem() == null ? ""  : cmbAccountStatus.getSelectedItem().toString() );

			String SQL = "";  
			for (int rw = 0; rw < modelPastDue.getRowCount(); rw++) {

				System.out.println("*************************" + modelPastDue.getValueAt(rw, 7));

				//	String contact_no = modelPastDue.getValueAt(rw, 4) == null ?  "" : modelPastDue.getValueAt(rw, 4).toString(); 
				//	String house_model = modelPastDue.getValueAt(rw, 5) == null ?  "" : modelPastDue.getValueAt(rw, 5).toString(); 
				//	String stb_date = (modelPastDue.getValueAt(rw, 6) == null ?  "" : modelPastDue.getValueAt(rw, 6)).toString(); 
				//	String bb_date = modelPastDue.getValueAt(rw, 7) == null ?  "" : modelPastDue.getValueAt(rw, 7).toString(); 
				//	String movein_date = modelPastDue.getValueAt(rw, 8) == null ?  "" : modelPastDue.getValueAt(rw, 8).toString(); 
				//	String stage = modelPastDue.getValueAt(rw, 9) == null ?  "" : modelPastDue.getValueAt(rw, 9).toString();
				//	String default_date = modelPastDue.getValueAt(rw, 10) == null ?  "" : modelPastDue.getValueAt(rw, 10).toString(); 
				//	String last_paid_date = modelPastDue.getValueAt(rw, 11) == null ?  "" : modelPastDue.getValueAt(rw, 11).toString();
				String months_pd = modelPastDue.getValueAt(rw, 15) == null ?  "" : modelPastDue.getValueAt(rw, 15).toString();
				//	String days_pd = modelPastDue.getValueAt(rw, 13) == null ?  "" : modelPastDue.getValueAt(rw, 13).toString();
				//	String months_paid = modelPastDue.getValueAt(rw, 14) == null ?  "" : modelPastDue.getValueAt(rw, 14).toString();
				//	BigDecimal amount_due  = (BigDecimal) ((BigDecimal)modelPastDue.getValueAt(rw, 15) == null ?  "" : (BigDecimal)modelPastDue.getValueAt(rw, 15));
				//	String last_follow_up = modelPastDue.getValueAt(rw, 16) == null ?  "" : modelPastDue.getValueAt(rw, 16).toString();
				//	String email_add = modelPastDue.getValueAt(rw, 17) == null ?  "" : modelPastDue.getValueAt(rw, 17).toString();
				//	String pn_no = modelPastDue.getValueAt(rw, 18) == null ?  "" : modelPastDue.getValueAt(rw, 18).toString();
				//	String entity_id = modelPastDue.getValueAt(rw, 22) == null ?  "" : modelPastDue.getValueAt(rw, 22).toString();
				String proj_id = modelPastDue.getValueAt(rw, 26) == null ?  "" : modelPastDue.getValueAt(rw, 26).toString();
				//	String pbl_id = modelPastDue.getValueAt(rw, 24) == null ?  "" : modelPastDue.getValueAt(rw, 24).toString();
				//	String seq_no  = modelPastDue.getValueAt(rw, 25) == null ?  "" : modelPastDue.getValueAt(rw, 25).toString();
				//	String unit_id  = modelPastDue.getValueAt(rw, 26) == null ?  "" : modelPastDue.getValueAt(rw, 26).toString();
				//	String part_id  = modelPastDue.getValueAt(rw, 27) == null ?  "" : modelPastDue.getValueAt(rw, 27).toString();
				//	String last_due_date  = modelPastDue.getValueAt(rw, 28) == null ?  "" : modelPastDue.getValueAt(rw, 28).toString();
				String phase  = modelPastDue.getValueAt(rw, 32) == null ?  "" : modelPastDue.getValueAt(rw, 32).toString();
				String proj_name  = modelPastDue.getValueAt(rw, 33) == null ?  "" : modelPastDue.getValueAt(rw, 33).toString();

				String due_date_bank  =  (pastdue.dateFormat_day((Date) modelPastDue.getValueAt(rw, 35))  == null ?  null : pastdue.dateFormat_day((Date) modelPastDue.getValueAt(rw, 35)) );
				String tct_no = modelPastDue.getValueAt(rw, 37) == null ?  "" : modelPastDue.getValueAt(rw, 37).toString(); 
				String reason_buyback = modelPastDue.getValueAt(rw, 38) == null ?  "" : modelPastDue.getValueAt(rw, 38).toString();
				String remarks = modelPastDue.getValueAt(rw, 39) == null ?  "" : modelPastDue.getValueAt(rw, 39).toString();
					
				
				SQL = (!SQL.isEmpty() ? SQL + "union\n" : "") +
						
						"SELECT \n" + 
						"'"+modelPastDue.getValueAt(rw, 1)+"' AS unit_pbl,\n" + 
						"'"+ modelPastDue.getValueAt(rw, 2).toString().replace("'", "''")+ "' AS client_name,\n" + 
						"'"+months_pd+"' AS months_pd,\n" + 
						"'"+(BigDecimal)modelPastDue.getValueAt(rw, 23)+"' AS nsp,\n" + 
						"'"+proj_id+"' AS proj_id,\n" + 
						"'"+phase+"' AS phase,\n" + 
						"'"+proj_name+"' AS proj_name,\n" + 
						"'"+(BigDecimal)modelPastDue.getValueAt(rw, 33)+"' AS ob_company,\n" + 
						"'"+(BigDecimal)modelPastDue.getValueAt(rw, 34)+"' AS ob_bank,\n" + 
						" NULLIF('"+due_date_bank+ "','null') AS due_date_bank,\n" + 
						"'"+tct_no+"' AS tct_no,\n" + 
						"'"+reason_buyback+"' AS reason_buyback,\n" + 
						"'"+remarks+"' AS remarks";
						
						/*+ "select\n"  
						+ "'" 
						+ modelPastDue.getValueAt(rw, 1)+ "' as unit_pbl,\n"
						+ "'"+ modelPastDue.getValueAt(rw, 2).toString().replace("'", "''")+ "' as client_name,\n"
						+ "'"
							+ modelPastDue.getValueAt(rw, 2).toString().replace("'", "''")
						+ "' as sale_div,\n"
						+ "'"
						+ contact_no 
						+ "' as contact_no,\n"
						+ "'"
						+ house_model4
						+ "' as house_model,\n"
						+ "'"
						+ stb_date 
						+ "' as stb_date,\n"
						+ "'"
						+ bb_date
						+ "' as bb_date,\n"
						+ "'"
						+ movein_date
						+ "' as movein_date,\n"
						+ "'"
						+ stage
						+ "' as stage,\n"
						+ "'"
						+ default_date
						+ "' as default_date,\n"
						+ "'"
						+ last_paid_date
						+ "' as last_paid_date,\n"
						+ "'"
						+months_pd
						+ "' as months_pd,\n"
						+ "'"
						+ days_pd
						+ "' as days_pd,\n"
						+ "'"
						+ months_paid
						+ "' as months_paid,\n"
						+ "'"
						+ amount_due
						+ "' as amount_due,\n" 
						+ "'"
						+ last_follow_up 
						+ "' as last_follow_up,\n"
						+ "'"
						+ email_add
						+ "' as email_add,\n" 
						+ "'"
						+ pn_no
						+ "' as pn_no,\n"
						+ "'"
						+ (Timestamp) modelPastDue.getValueAt(rw, 19)
						+ "' as nod_date,\n"
						+ "'"
						+ (Timestamp)modelPastDue.getValueAt(rw, 20)
						+ "' as noc_date,\n"
						+ "'"
						+ (BigDecimal)modelPastDue.getValueAt(rw, 23)+ "' as nsp,\n"
						+ "'"
						+ entity_id
						+ "' as entity_id,\n"
						+ "'"
						+ proj_id+ "' as proj_id,\n"
						+ "'"
						+ pbl_id
						+ "' as pbl_id,\n"
						+ "'"
						+ seq_no
						+ "' as seq_no,\n"
						+ "'"
						+ unit_id
						+ "' as unit_id,\n"
						+ "'"
						+ part_id
						+ "' as part_id,\n"
						+ "'"
						+ last_due_date
						+ "' as last_due_date,\n"
						+ "'"
						+ phase	+ "' as phase,\n"
						+ "'"+ proj_name+ "' as proj_name,\n"
						+ "'"+ (BigDecimal)modelPastDue.getValueAt(rw, 33)+ "' as ob_company,\n"
						+ "'"
						+ (BigDecimal)modelPastDue.getValueAt(rw, 34)
						+ "' as ob_bank,\n"
						+ " NULLIF('"+due_date_bank+ "','null') as due_date_bank,\n"
						+ "'"
						+ tct_no
						+ "' as tct_no,\n"
						+ "'"
						+ reason_buyback 
						+ "' as reason_buyback,\n"
						+ "'"
						+ remarks
						+ "' as remarks \n"; */


				System.out.println("*************************" + SQL);

				//mapParameters.put("reason_buyback",modelPastDue.getValueAt(i, 35));
				//mapParameters.put("remarks",modelPastDue.getValueAt(i, 35));

			}
			//String SQL  = "SELECT false, * FROM sp_generate_buyback('"+projID+"')";

			FncReport.generateReport("/Reports/rptPD_Buyback.jasper", "List of BuyBack Accounts",  mapParameters, SQL);

		}
	}
	
	public class ForPreview implements Runnable{
		private String hdmf_group_id;

		@Override
		public void run() {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("lblListof", labelType);
			mapParameters.put("lblBuyerStatus", cmbAccountStatus.getSelectedItem() == null ? ""  : cmbAccountStatus.getSelectedItem().toString() );
			mapParameters.put("reason_buyback",reason);
			mapParameters.put("remarks",remarks);

			//		mapParameters.put("proj_id", projID);
			//		mapParameters.put("table_name", table_name);
			//		mapParameters.put("monthspd", months_pd);
			//		mapParameters.put("tr_stage", trStage);

			String SQL = "";
			if (rbtnPastDue.isSelected()) {

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

				if (cmbBuyerType.getSelectedIndex() == 0) {
					hdmf_group_id = "All";
				}

				if (cmbBuyerType.getSelectedIndex() == 1) {
					hdmf_group_id = "02";
					table_name = "ihf_due_accounts";
				}

				if (cmbBuyerType.getSelectedIndex() == 2) {
					hdmf_group_id = "04";
					table_name ="pagibig_due_accounts";
				}
				//	SQL = "SELECT false,* FROM sp_generate_past_due('"+table_name+"', '"+ projID +"' ,'"+months_pd+"',"+trStage+")";	
			//	SQL = "SELECT false,* FROM sp_generate_past_due('"+table_name+"', '"+ projID +"' ,'"+months_pd+"','"+buyer_status+"',"+hdmf_group_id+")";
				SQL = "SELECT false,* FROM sp_generate_past_due('"+table_name+"', '"+ projID +"' ,'"+months_pd+"','"+buyer_status+"','"+hdmf_group_id+"',"+trStage+")";
				System.out.println("SQL PRINT: " +SQL );
				FncReport.generateReport("/Reports/rptPast_Due.jasper", "List of Past Due Accounts",  mapParameters, SQL);
			}
			/*if (rbtnPCancel.isSelected()) {
				SQL = "SELECT false, * FROM sp_generate_proj_cancellation('"+table_name+"','"+projID+"','"+days_pd+"')";	

				FncReport.generateReport("/Reports/rptPast_Due.jasper", "List of Past Due Accounts",  mapParameters, SQL);
			}
			 */
			/*
			if (rbtnBuyBack.isSelected()) {
				//String reason =  modelPastDue.getValueAt(modelPastDue.getRowCount(), 35)).toString()
				//String remarks =  modelPastDue.getValueAt(tblPastDue.getRowCount(), 26).toString();

				SQL  = "SELECT false, * FROM sp_generate_buyback('"+projID+"')";

				FncReport.generateReport("/Reports/rptPD_Buyback.jasper", "List of BuyBack Accounts",  mapParameters, SQL);
			}
			 */
		}
	}

	private void computeTotal() {

		BigDecimal totalNSP = new BigDecimal("0.00");
		BigDecimal totalOBC = new BigDecimal("0.00");
		BigDecimal totalOBB = new BigDecimal("0.00");

		for (int x = 0; x < modelPastDue.getRowCount(); x++) {
			BigDecimal totalamountNSP = (BigDecimal) modelPastDue.getValueAt(x, 24);
			BigDecimal totalamountOBC = (BigDecimal) modelPastDue.getValueAt(x, 34);
			BigDecimal totalamountOBB = (BigDecimal) modelPastDue.getValueAt(x, 35);

			try {

				totalNSP = totalNSP.add(totalamountNSP);
				totalOBC = totalOBC.add(totalamountOBC);
				totalOBB = totalOBB.add(totalamountOBB);

			} catch (Exception e1) { }
		}

		modelNSPTotal.setValueAt(totalNSP, 0, 24);
		modelNSPTotal.setValueAt(totalOBC, 0, 34);
		modelNSPTotal.setValueAt(totalOBB, 0, 35);
	}

	private void updateCommit() {
		scrollNSPTotal.setRowHeaderView(FncTables.getRowHeader_Footer(Integer.toString(tblPastDue.getRowCount())));
		tblPastDue.packAll();

		for (int row = 0; row < tblPastDue.getRowCount(); row++) {
			((DefaultListModel) rowHeadePastDue.getModel()).setElementAt(row + 1, row);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource().equals(tblPastDue)) {

			if (tblPastDue.getSelectedColumn()== 25) {

				String SQL = "SELECT remark_id, remdesc FROM mf_remarks where remtype = '01'";

				_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, SQL, false);

				dlg.setLocationRelativeTo(FncGlobal.homeMDI);
				dlg.setVisible(true); 

				Object[] data = dlg.getReturnDataSet();

				if(data != null){

					modelPastDue.setValueAt(data[1], tblPastDue.getSelectedRow(), 38);
					tblPastDue.packAll();
					tblPastDue.packColumn(38, 400, 400);

					System.out.println("Status_id :" + modelPastDue.getValueAt( tblPastDue.getSelectedRow(), 25));
				}
			}
			/** Listener for boolean canEditStatus **/
			if (tblPastDue.getSelectedColumn()== 25 || tblPastDue.getSelectedColumn()== 26  ) {
				modelPastDue.setEditable(true);
			} // col 8
			
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}
	@Override
	public void mousePressed(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){}
	@Override
	public void mouseDragged(MouseEvent e){}
	@Override
	public void mouseMoved(MouseEvent e) {

		if (e.getSource().equals(tblPastDue)) {

			if (tblPastDue.columnAtPoint(e.getPoint()) == 25 ) {
				tblPastDue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}else {
				tblPastDue.setCursor(null);
			}	
		}
	}

	public class ForPreviewProjCancel implements Runnable{

		private String preparedby = "";
		private String approvedby = "";
		ArrayList<String> iftrue = new ArrayList<String>();
		@Override
		public void run() {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", company);
			mapParameters.put("co_id", co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("lblListof", labelType);
			mapParameters.put("lblBuyerStatus", cmbAccountStatus.getSelectedItem() == null ? ""  : cmbAccountStatus.getSelectedItem().toString());
			pgSelect db = new pgSelect();

			String SQL ="";
			for (int i = 0; i < modelPastDue.getRowCount(); i++) {
				Boolean SelectedItem = (Boolean) modelPastDue.getValueAt(i, 0);
				
				
				
				if (SelectedItem) {
					iftrue.add(modelPastDue.getValueAt(i, 25).toString());
					SQL = (!SQL.isEmpty() ? SQL + "UNION\n" : "") +

							"SELECT '"+modelPastDue.getValueAt(i, 1)+"' AS unit_pbl,\n" + 
							"'"+modelPastDue.getValueAt(i, 2)+"' AS client_name,\n" + 
							"'"+modelPastDue.getValueAt(i, 3)+"' AS buyertype,\n" + 
							"NULLIF('"+modelPastDue.getValueAt(i, 4)+"','null') AS sale_div,\n" + 
							"_get_sales_agent('"+modelPastDue.getValueAt(i, 25)+"','"+modelPastDue.getValueAt(i, 26)+"','"+modelPastDue.getValueAt(i, 27)+"') AS sale_agent,\n" +
							"'"+modelPastDue.getValueAt(i, 6)+"' AS house_model,\n" + 
							"NULLIF('"+modelPastDue.getValueAt(i, 10)+"','null') AS stage,\n" + 
							"'"+modelPastDue.getValueAt(i, 32)+"' AS phase,\n" + 
							"'"+modelPastDue.getValueAt(i, 11)+"' AS tr_date,\n" + 
							"NULLIF('"+modelPastDue.getValueAt(i, 12)+"','null') AS or_date,\n" + 
							"NULLIF('"+modelPastDue.getValueAt(i, 13)+"','null') AS default_date,\n" + 
							"'"+(BigDecimal)modelPastDue.getValueAt(i, 24)+"'::numeric AS nsp,\n" + 
							"'"+modelPastDue.getValueAt(i, 40)+"' AS withntc,\n" +
							"NULLIF('"+modelPastDue.getValueAt(i, 41)+"','null') AS cts_notarized,\n" + 
							"'"+modelPastDue.getValueAt(i, 26)+"' AS proj_id,\n" + 
							"NULLIF('"+modelPastDue.getValueAt(i, 33)+"','null') AS proj_name,\n"+
							"(case when NULLIF('"+modelPastDue.getValueAt(i, 12)+"','null') is null then 'TR' ELSE 'OR' END) AS class \n";

				}
			}

			if (iftrue.isEmpty()) {
				JOptionPane.showMessageDialog(getContentPane(),"Please select first for preview ","Preview", JOptionPane.OK_OPTION);
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
					if (db.Result[j][16].equals("TR")) {
						BigDecimal totalTR = (BigDecimal) db.Result[j][11];

						try {
							total_tr = total_tr.add(totalTR);

						} catch (Exception e1) { }
						count_tr++;
					}
					if (db.Result[j][16].equals("OR")) {

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
//
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
				FncReport.generateReport("/Reports/rptPD_Proj_Cancellation.jasper", "List of Recommendation Accounts",  mapParameters, SQL);
				
			}
			

		}
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
}


