package Projects.ConstructionManagement;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import FormattedTextField._JXFormattedTextField;
import Lookup._JLookup;

public class HouseRepair extends JInternalFrame {
	
	private static final long serialVersionUID = 1066342394226501458L;
	protected JPanel pnlInformation;
	protected JPanel pnlDetail;

	protected _JLookup lookupProject;
	protected _JLookup lookupUnit;
	protected _JLookup lookupPayeeType;
	protected _JLookup lookupPayee;

	protected _JXFormattedTextField txtNetAmount;
	protected _JXFormattedTextField txtWTax;
	protected _JXFormattedTextField txtVat;
	protected _JXFormattedTextField txtRepairAmount;

	protected JTextField txtAwardedCost;
	protected JTextField txtRetAging;
	protected JTextField txtContractor;
	protected JTextField txtNtp;
	protected JTextField txtRetensionReleased;

	protected JButton btnCancel;
	protected JButton btnSave;
	protected JButton btnDelete;
	protected JButton btnEdit;
	protected JButton btnNew;

	protected JScrollPane scrollDetail;
	protected JScrollPane scrollRemarks;
	protected JScrollPane scrollBackcharges;

	protected JLabel tagProject;
	protected JLabel tagPayeeType;
	protected JLabel tagContract;
	protected JLabel tagContractor;
	protected JLabel lblNtpContract;
	protected JLabel lblContractor;
	protected JLabel lblRetensionReleased;
	protected JLabel lblAwardedCost;
	protected JLabel lblRetAging;
	protected JLabel lblUnit;
	protected JLabel lblProject;
	protected JLabel tagUnit;
	protected JLabel tagPayee;
	protected JLabel lblNetAmount;
	protected JLabel lblWTax;
	protected JLabel lblVat;
	protected JLabel lblRepairAmount;
	protected JLabel lblRepairDate;
	protected JLabel lblPayeeType;
	protected JLabel lblPayee;
	protected JLabel lblRemarks;

	protected JDateChooser dateRepair;
	protected JTextArea txtareaRemarks;

	protected JTable tblDetail;
	protected DefaultTableModel modelDetail;
	protected JTable tblBackcharge;
	protected DefaultTableModel modelBackcharge;

	protected DefaultTableCellRenderer Renderer_Right = new DefaultTableCellRenderer();
	protected DefaultTableCellRenderer Renderer_Center = new DefaultTableCellRenderer();
	protected DecimalFormat df = new DecimalFormat("#,##0.00");
	protected MouseListener mlBackcharge;
	protected Font dialog12Bold = new Font("DIALOG", Font.BOLD, 12);
	protected Font dialog11Font = new Font("DIALOG", Font.PLAIN, 11);
	protected Color colorFont = Color.BLUE;

	protected String varbContractorId;
	protected JCheckBox checkBackcharges;
	protected String varbTypeId;

	protected String co_id;
	protected _JXFormattedTextField txt;
	protected ArrayList<Object> listRow = new ArrayList<Object>();
	protected KeyAdapter keyAdapter;
	protected ListSelectionListener lsl;
	protected ListSelectionModel rowSM;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		HouseRepair inst = new HouseRepair();
		JDesktopPane jdp = new JDesktopPane();
		jdp.add(inst);
		jdp.setPreferredSize(inst.getPreferredSize());
		frame.setContentPane(jdp);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public HouseRepair(){
		super();
		initGUI();
	}
	public HouseRepair(String proj_id, String proj_name, String co_id, String pbl_id, String unit_desc){
		super();
		initGUI();
		enableUnit(proj_id, proj_name, co_id);
		displayUnitDetails(pbl_id, unit_desc);
	}

	private void initGUI(){
		try {
			this.setPreferredSize(new java.awt.Dimension(699, 545));
			this.setBounds(0, 0, 699, 545);
			this.setVisible(true);
			this.setTitle("House Repairs");
			this.setClosable(true);
			this.getContentPane().setLayout(null);
			this.setIconifiable(true);
			Renderer_Right.setHorizontalAlignment(JLabel.RIGHT);
			Renderer_Center.setHorizontalAlignment(JLabel.CENTER);

			keyAdapter = new KeyAdapter() {
				public void keyPressed(KeyEvent arg0) {
					//int r = tblDetail.getSelectedRow();
					int c = tblBackcharge.getSelectedColumn();
					String toEdit = txt.getText().replace(",", "");
					if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
						if(c==2){
							//TODO
							if(Double.parseDouble(toEdit)<=Double.parseDouble(txtRepairAmount.getText().replace(",", ""))){
								txtVat.setText("0.00");
								txtWTax.setText("0.00");
								txtNetAmount.setText(txtRepairAmount.getText());
							}else{
								JOptionPane.showMessageDialog(getContentPane(), "Amount must be equal to Repair Amount.", "Amount", JOptionPane.WARNING_MESSAGE);
								txt.setText("0.00");
							}
						}
						if(c==3){
							System.out.println("Dumaan!!!");
							if(txtareaRemarks.getText().trim().equals("")){
								txtareaRemarks.setText(toEdit);
							}
						}
					}
				}
			};
			txt = new _JXFormattedTextField();
			//txt.setInputType(inpType.DECIMAL);
			txt.addKeyListener(keyAdapter);
			{
				pnlInformation = new JPanel();
				getContentPane().add(pnlInformation);
				pnlInformation.setLayout(null);
				pnlInformation.setBounds(4, 4, 685, 127);
				pnlInformation.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				{
					lookupProject = new _JLookup();
					pnlInformation.add(lookupProject);
					lookupProject.setText("");
					lookupProject.setHorizontalAlignment(JLabel.CENTER);
					lookupProject.setFont(dialog11Font);
					lookupProject.setEnabled(true);
					lookupProject.setBounds(90, 8, 80, 25);
					//lookupProject.setLookupSQL(Backcharges_DB.getProject());
					lookupProject.setName("lookupProject");
					lookupProject.addMouseListener(new MouseAdapter(){
						public void mouseClicked(MouseEvent evt){
							Object [] objArray = lookupProject.getDataSet();
							if ((evt.getClickCount()>=2) && (((_JLookup)evt.getSource()).getDataSet().length > 0)) {
								enableUnit(objArray[0].toString(), objArray[1].toString(), objArray[3].toString());
							}
						}
					});
				}
				{
					lookupUnit = new _JLookup();
					pnlInformation.add(lookupUnit);
					lookupUnit.setText("");
					lookupUnit.setHorizontalAlignment(JLabel.CENTER);
					lookupUnit.setFont(dialog11Font);
					lookupUnit.setEnabled(false);
					lookupUnit.setBounds(90, 36, 80, 25);
					lookupUnit.setName("lookupUnit");
					lookupUnit.addMouseListener(new MouseAdapter(){
						public void mouseClicked(MouseEvent evt){
							Object [] objArray = lookupUnit.getDataSet();
							if ((evt.getClickCount()>=2) && (((_JLookup)evt.getSource()).getDataSet().length > 0)) {
								displayUnitDetails(objArray[0].toString(), objArray[2].toString());
							}
						}
					});
				}
				{
					txtNtp = new JTextField("");
					pnlInformation.add(txtNtp);
					txtNtp.setHorizontalAlignment(JLabel.CENTER);
					txtNtp.setFont(dialog11Font);
					txtNtp.setEditable(false);
					txtNtp.setBounds(90, 64, 80, 25);
					txtNtp.setName("txtNtp");
				}
				{
					txtContractor = new JTextField("");
					pnlInformation.add(txtContractor);
					txtContractor.setHorizontalAlignment(JLabel.CENTER);
					txtContractor.setFont(dialog11Font);
					txtContractor.setEditable(false);
					txtContractor.setBounds(90, 92, 80, 25);
					txtContractor.setName("txtContractor");
				}
				{
					tagProject = new JLabel("[ ]");
					pnlInformation.add(tagProject);
					tagProject.setFont(dialog11Font);
					tagProject.setForeground(colorFont);
					tagProject.setBounds(176, 13, 232, 15);
					tagProject.setName("tagProject");
				}
				{
					tagUnit = new JLabel("[ ]");
					pnlInformation.add(tagUnit);
					tagUnit.setFont(dialog11Font);
					tagUnit.setForeground(colorFont);
					tagUnit.setBounds(176, 36, 232, 25);
					tagUnit.setName("tagUnit");
				}
				{
					tagContract = new JLabel("[ ]");
					pnlInformation.add(tagContract);
					tagContract.setFont(dialog11Font);
					tagContract.setForeground(colorFont);
					tagContract.setBounds(176, 64, 210, 25);
					tagContract.setName("tagContract");
				}
				{
					tagContractor = new JLabel("[ ]");
					pnlInformation.add(tagContractor);
					tagContractor.setFont(dialog11Font);
					tagContractor.setForeground(colorFont);
					tagContractor.setBounds(176, 92, 328, 25);
					tagContractor.setName("tagContractor");
				}
				{
					txtRetensionReleased = new JTextField("");
					pnlInformation.add(txtRetensionReleased);
					txtRetensionReleased.setFont(dialog11Font);
					txtRetensionReleased.setEditable(false);
					txtRetensionReleased.setBounds(573, 8, 105, 25);
					txtRetensionReleased.setName("txtRetensionReleased");
				}
				{
					txtRetAging = new JTextField("");
					pnlInformation.add(txtRetAging);
					txtRetAging.setFont(dialog11Font);
					txtRetAging.setEditable(false);
					txtRetAging.setBounds(573, 36, 105, 25);
					txtRetAging.setName("txtRetAging");
				}
				{
					txtAwardedCost = new JTextField("");
					pnlInformation.add(txtAwardedCost);
					txtAwardedCost.setFont(dialog11Font);
					txtAwardedCost.setEditable(false);
					txtAwardedCost.setBounds(573, 64, 105, 25);
					txtAwardedCost.setName("txtAwardedCost");
				}
				{
					lblProject = new JLabel("Project");
					pnlInformation.add(lblProject);
					lblProject.setBounds(6, 8, 82, 25);
					lblProject.setName("lblProject");
				}
				{
					lblUnit = new JLabel("Unit");
					pnlInformation.add(lblUnit);
					lblUnit.setBounds(6, 36, 82, 25);
					lblUnit.setName("lblUnit");
				}
				{
					lblNtpContract = new JLabel("Ntp/Contract");
					pnlInformation.add(lblNtpContract);
					lblNtpContract.setBounds(6, 64, 82, 22);
					lblNtpContract.setName("lblNtpContractor");
				}
				{
					lblContractor = new JLabel("Contractor");
					pnlInformation.add(lblContractor);
					lblContractor.setBounds(6, 92, 84, 25);
					lblContractor.setName("lblContractor");
				}
				{
					lblRetensionReleased = new JLabel("Retension Released");
					pnlInformation.add(lblRetensionReleased);
					lblRetensionReleased.setBounds(455, 8, 118, 25);
					lblRetensionReleased.setName("lblRetensionReleased");
				}
				{
					lblRetAging = new JLabel("Ret. Aging");
					pnlInformation.add(lblRetAging);
					lblRetAging.setBounds(455, 36, 118, 25);
					lblRetAging.setName("lblRetAging");
				}
				{
					lblAwardedCost = new JLabel("Awarded Cost");
					pnlInformation.add(lblAwardedCost);
					lblAwardedCost.setBounds(455, 64, 118, 25);
					lblAwardedCost.setName("lblAwardedCost");
				}
			}
			{
				pnlDetail = new JPanel();
				getContentPane().add(pnlDetail);
				pnlDetail.setLayout(null);
				pnlDetail.setBounds(4, 134, 685, 238);
				pnlDetail.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				{
					lookupPayee = new _JLookup();
					pnlDetail.add(lookupPayee);
					lookupPayee.setText("");
					lookupPayee.setHorizontalAlignment(JLabel.CENTER);
					lookupPayee.setFont(dialog11Font);
					lookupPayee.setEnabled(false);
					lookupPayee.setBounds(90, 7, 80, 25);
					//lookupPayee.setLookupSQL(Backcharges_DB.getPayee());
					lookupPayee.setName("lookupPayee");
					lookupPayee.addMouseListener(new MouseAdapter(){
						public void mouseClicked(MouseEvent evt){
							Object [] objArray = lookupPayee.getDataSet();
							if ((evt.getClickCount()>=2) && (((_JLookup)evt.getSource()).getDataSet().length > 0)) {
								lookupPayee.setText(objArray[0].toString());
								tagPayee.setText("[ "+objArray[1].toString()+" ]");

								lookupPayeeType.setText("");
								lookupPayeeType.setEnabled(true);
								//lookupPayeeType.setLookupSQL(Backcharges_DB.getPayeeType());
							}
						}
					});
				}
				{
					lookupPayeeType = new _JLookup();
					pnlDetail.add(lookupPayeeType);
					lookupPayeeType.setText("");
					lookupPayeeType.setHorizontalAlignment(JLabel.CENTER);
					lookupPayeeType.setFont(dialog11Font);
					lookupPayeeType.setEnabled(false);
					lookupPayeeType.setBounds(90, 35, 80, 25);
					lookupPayeeType.setName("lookupPayeeType");
					lookupPayeeType.addMouseListener(new MouseAdapter(){
						public void mouseClicked(MouseEvent evt){
							Object [] objArray = lookupPayeeType.getDataSet();
							if ((evt.getClickCount()>=2) && (((_JLookup)evt.getSource()).getDataSet().length > 0)) {
								lookupPayeeType.setText(objArray[0].toString());
								tagPayeeType.setText("[ "+objArray[1].toString()+" ]");
							}
						}
					});
				}
				{
					scrollRemarks = new JScrollPane();
					pnlDetail.add(scrollRemarks);
					scrollRemarks.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					scrollRemarks.setBounds(486, 38, 192, 74);
					scrollRemarks.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
					{
						txtareaRemarks = new JTextArea();
						scrollRemarks.setViewportView(txtareaRemarks);
						txtareaRemarks.setOpaque(true);
						txtareaRemarks.setEnabled(true);
						txtareaRemarks.setEditable(false);
						txtareaRemarks.setLineWrap(true);
						txtareaRemarks.setWrapStyleWord(true);
						txtareaRemarks.setBackground(new java.awt.Color(255,255,255));
						txtareaRemarks.setName("txtareaRemarks");
					}
				}
				{
					dateRepair = new JDateChooser();
					pnlDetail.add(dateRepair);
					dateRepair.setFont(dialog11Font);
					dateRepair.setDate(Calendar.getInstance().getTime());
					dateRepair.getCalendarButton().setEnabled(false);
					((JTextField)dateRepair.getDateEditor()).setEditable(false);
					dateRepair.setBounds(90, 64, 136, 22);
				}
				{
					txtRepairAmount = new _JXFormattedTextField();
					pnlDetail.add(txtRepairAmount);
					txtRepairAmount.setEditable(false);
					txtRepairAmount.setBounds(90, 90, 136, 22);
					//txtRepairAmount.setInputType(inpType.DECIMAL);
					txtRepairAmount.setHorizontalAlignment(SwingConstants.RIGHT);
					txtRepairAmount.setFont(new java.awt.Font("SansSerif",2,12));
					txtRepairAmount.setBackground(new java.awt.Color(144,238,144));
					//txtRepairAmount.setInputLimit(30);
					txtRepairAmount.setName("txtRepairAmount");
					txtRepairAmount.addKeyListener(new KeyAdapter(){
						public void keyPressed(KeyEvent arg0) {
							char c = arg0.getKeyChar();
							if(c == KeyEvent.VK_ENTER){
								Double amount = Double.parseDouble(txtRepairAmount.getText().replace(",", ""));
								Double vat = ((amount / 1.12) * .12);
								Double wtax = ((amount / 1.12) * .02);
								Double net = (amount - wtax);

								txtVat.setText(df.format(vat));
								txtWTax.setText(df.format(wtax));
								txtNetAmount.setText(df.format(net));
								txtareaRemarks.grabFocus();
								txtRepairAmount.grabFocus();
							}
						}
					});
				}
				{
					txtVat = new _JXFormattedTextField();
					pnlDetail.add(txtVat);
					txtVat.setEditable(false);
					txtVat.setEnabled(true);
					//txtVat.setInputType(inpType.DECIMAL);
					txtVat.setHorizontalAlignment(SwingConstants.RIGHT);
					txtVat.setFont(new java.awt.Font("SansSerif",2,12));
					txtVat.setBackground(new java.awt.Color(144,238,144));
					//txtVat.setInputLimit(30);
					txtVat.setBounds(387, 38, 93, 22);
					txtVat.setName("txtVat");
				}
				{
					txtWTax = new _JXFormattedTextField();
					pnlDetail.add(txtWTax);
					txtWTax.setEditable(false);
					txtWTax.setEnabled(true);
					//txtWTax.setInputType(inpType.DECIMAL);
					txtWTax.setHorizontalAlignment(SwingConstants.RIGHT);
					txtWTax.setFont(new java.awt.Font("SansSerif",2,12));
					txtWTax.setBackground(new java.awt.Color(144,238,144));
					//txtWTax.setInputLimit(30);
					txtWTax.setBounds(387, 64, 93, 22);
					txtWTax.setName("txtWTax");
				}
				{
					scrollDetail = new JScrollPane();
					pnlDetail.add(scrollDetail);
					scrollDetail.setBounds(6, 118, 672, 112);
					scrollDetail.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
					{
						Object colNames[] = {"Date",
								"Amount",
								"VAT",
								"WTax",
								"Net Amount",
								"RPLF No.",
								"Date Released",
								"Remarks",
						"Status"};
						Object [][] data = {};
						modelDetail = new DefaultTableModel(data, colNames){
							private static final long serialVersionUID = -3137541482255987752L;
							public boolean isCellEditable(int rowIndex, int columnIndex) {
								return false;
							}
						};
						tblDetail = new JTable(modelDetail);
						scrollDetail.setViewportView(tblDetail);
						tblDetail.getTableHeader().setReorderingAllowed(false);
						tblDetail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblDetail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
						tblDetail.getColumnModel().getColumn(0).setPreferredWidth(80);//
						tblDetail.getColumnModel().getColumn(1).setPreferredWidth(100);//
						tblDetail.getColumnModel().getColumn(2).setPreferredWidth(100);//
						tblDetail.getColumnModel().getColumn(3).setPreferredWidth(100);//
						tblDetail.getColumnModel().getColumn(4).setPreferredWidth(100);//
						tblDetail.getColumnModel().getColumn(5).setPreferredWidth(80);//
						tblDetail.getColumnModel().getColumn(6).setPreferredWidth(100);//
						tblDetail.getColumnModel().getColumn(7).setPreferredWidth(100);//
						tblDetail.getColumnModel().getColumn(8).setPreferredWidth(80);//

						tblDetail.getColumnModel().getColumn(0).setCellRenderer(Renderer_Center);
						tblDetail.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(txt));
						tblDetail.getColumnModel().getColumn(1).setCellRenderer(new HighlightAmounts());
						tblDetail.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(txt));
						tblDetail.getColumnModel().getColumn(2).setCellRenderer(new HighlightAmounts());
						tblDetail.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(txt));
						tblDetail.getColumnModel().getColumn(3).setCellRenderer(new HighlightAmounts());
						tblDetail.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(txt));
						tblDetail.getColumnModel().getColumn(4).setCellRenderer(new HighlightAmounts());
						tblDetail.getColumnModel().getColumn(5).setCellRenderer(Renderer_Center);
						tblDetail.getColumnModel().getColumn(6).setCellRenderer(Renderer_Center);
						tblDetail.getColumnModel().getColumn(8).setCellRenderer(Renderer_Center);
						tblDetail.setFont(dialog11Font);
						tblDetail.setName("tblDetail");

						lsl = new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent lse) {
								if (!lse.getValueIsAdjusting()) {
									StringBuffer buf = new StringBuffer();
									int[] selection = getSelectedIndices(rowSM, rowSM.getMinSelectionIndex(), rowSM.getMaxSelectionIndex());
									if (selection.length == 0) {
										buf.append(0);
									} else {
										for (int i = 0; i < selection.length - 1; i++) {
											buf.append(selection[i]);
											buf.append(", ");
										}
										buf.append(selection[selection.length - 1]);
									}
									Integer selectedRow = Integer.parseInt(buf.toString());
									/***Insert code here***/
									//TODO
									clearRows(modelBackcharge);
									/*Backcharges_DB.displayBackcharges(tblBackcharge, lookupUnit.getText(), tblDetail.getValueAt(selectedRow, 5).toString());

									Backcharges_DB.displayPayeeDetail(tblDetail.getValueAt(selectedRow, 5).toString());
									lookupPayee.setText(Backcharges_DB.getPayeeDetail()[0][0].toString());
									tagPayee.setText("[ "+Backcharges_DB.getPayeeDetail()[0][1]+" ]");
									lookupPayeeType.setText(Backcharges_DB.getPayeeDetail()[0][2].toString());
									tagPayeeType.setText("[ "+Backcharges_DB.getPayeeDetail()[0][3]+" ]");
									((JTextField)dateRepair.getDateEditor()).setText(Backcharges_DB.getPayeeDetail()[0][4].toString());
									txtRepairAmount.setText(Backcharges_DB.getPayeeDetail()[0][5].toString());
									txtVat.setText(Backcharges_DB.getPayeeDetail()[0][6].toString());
									txtWTax.setText(Backcharges_DB.getPayeeDetail()[0][7].toString());
									txtNetAmount.setText(Backcharges_DB.getPayeeDetail()[0][8].toString());
									txtareaRemarks.setText(Backcharges_DB.getPayeeDetail()[0][9].toString());*/
								}
							}
						};
						rowSM = tblDetail.getSelectionModel();
					}
				}
				{
					txtNetAmount = new _JXFormattedTextField();
					pnlDetail.add(txtNetAmount);
					txtNetAmount.setEditable(false);
					txtNetAmount.setEnabled(true);
					//txtNetAmount.setInputType(inpType.DECIMAL);
					txtNetAmount.setHorizontalAlignment(SwingConstants.RIGHT);
					txtNetAmount.setFont(new java.awt.Font("SansSerif",2,12));
					txtNetAmount.setBackground(new java.awt.Color(144,238,144));
					//txtNetAmount.setInputLimit(30);
					txtNetAmount.setBounds(387, 90, 93, 22);
					txtNetAmount.setName("txtNetAmount");
				}
				{
					lblRemarks = new JLabel("Repair Made/Remarks");
					pnlDetail.add(lblRemarks);
					lblRemarks.setBounds(486, 23, 186, 15);
					lblRemarks.setName("lblRemarks");
				}
				{
					lblPayee = new JLabel("Payee");
					pnlDetail.add(lblPayee);
					lblPayee.setBounds(6, 7, 84, 25);
					lblPayee.setName("lblPayee");
				}
				{
					lblPayeeType = new JLabel("Payee Type");
					pnlDetail.add(lblPayeeType);
					lblPayeeType.setBounds(6, 35, 84, 25);
					lblPayeeType.setName("lblPayeeType");
				}
				{
					lblRepairDate = new JLabel("Repair Date");
					pnlDetail.add(lblRepairDate);
					lblRepairDate.setBounds(6, 64, 84, 22);
					lblRepairDate.setName("lblRepairDate");
				}
				{
					lblRepairAmount = new JLabel("Repair Amt.");
					pnlDetail.add(lblRepairAmount);
					lblRepairAmount.setBounds(6, 90, 84, 22);
					lblRepairAmount.setName("lblRepairAmount");
				}
				{
					lblVat = new JLabel("VAT");
					pnlDetail.add(lblVat);
					lblVat.setBounds(337, 41, 43, 15);
					lblVat.setName("lblVat");
				}
				{
					lblWTax = new JLabel("WTax");
					pnlDetail.add(lblWTax);
					lblWTax.setBounds(337, 65, 43, 15);
					lblWTax.setName("lblWTax");
				}
				{
					lblNetAmount = new JLabel("Net Amt");
					pnlDetail.add(lblNetAmount);
					lblNetAmount.setBounds(337, 93, 48, 15);
					lblNetAmount.setName("lblNetAmount");
				}
				{
					tagPayee = new JLabel("[ ]");
					pnlDetail.add(tagPayee);
					tagPayee.setFont(dialog11Font);
					tagPayee.setForeground(colorFont);
					tagPayee.setBounds(176, 13, 490, 15);
					tagPayee.setName("tagPayee");
				}
				{
					tagPayeeType = new JLabel("[ ]");
					pnlDetail.add(tagPayeeType);
					tagPayeeType.setFont(dialog11Font);
					tagPayeeType.setForeground(colorFont);
					tagPayeeType.setBounds(176, 39, 167, 15);
					tagPayeeType.setName("tagPayeeType");
				}
			}
			{
				btnNew = new JButton("New");
				getContentPane().add(btnNew);
				btnNew.setEnabled(false);
				btnNew.setBounds(380, 482, 100, 30);
				btnNew.setName("btnNew");
				btnNew.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						newState();
						btnState(false, false, false, true, true);
					}
				});
			}
			{
				btnEdit = new JButton("Edit");
				//getContentPane().add(btnEdit);
				btnEdit.setEnabled(false);
				btnEdit.setBounds(275, 482, 100, 30);
				btnEdit.setName("btnEdit");
			}
			{
				btnDelete = new JButton("Delete");
				//getContentPane().add(btnDelete);
				btnDelete.setEnabled(false);
				btnDelete.setBounds(170, 482, 100, 30);
				btnDelete.setName("btnDelete");
			}
			{
				btnSave = new JButton("Save");
				getContentPane().add(btnSave);
				btnSave.setEnabled(false);
				btnSave.setBounds(485, 482, 100, 30);
				btnSave.setName("btnSave");
				btnSave.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(checkBackcharges.isSelected()){
							if(toSave()){
								saveState();
							}else{
								if(!tagContractor.getText().replace("[ ", "").replace(" ]", "").equals(tblBackcharge.getValueAt(0, 0).toString())){
									JOptionPane.showMessageDialog(getContentPane(), "Backcharges must be charge to contractor.", "Save", JOptionPane.WARNING_MESSAGE);
								}
							}
						}else{
							saveState();
						}
					}
				});
			}
			{
				btnCancel = new JButton("Cancel");
				getContentPane().add(btnCancel);
				btnCancel.setEnabled(false);
				btnCancel.setBounds(590, 482, 100, 30);
				btnCancel.setName("btnCancel");
				btnCancel.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						btnState(true, false, false, false, false);
						cancelState();
						btnState(false, false, false, false, false);
					}

				});
			}
			{
				scrollBackcharges = new JScrollPane();
				getContentPane().add(scrollBackcharges);
				scrollBackcharges.setBounds(4, 396, 685, 81);
				scrollBackcharges.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
				{
					Object colNames[] = { "Changed To",
							"Type",
							"Amount",
							"Remarks",
							"entity_id",
					"entity_type_id"};
					Object [][] data = {};
					modelBackcharge = new DefaultTableModel(data, colNames){
						private static final long serialVersionUID = -644882887843608389L;
						boolean[] canEdit = new boolean [] {
								false, false, true, true, false, false
						};
						public boolean isCellEditable(int rowIndex, int columnIndex) {
							return canEdit [columnIndex];
						}
					};
					tblBackcharge = new JTable(modelBackcharge);
					scrollBackcharges.setViewportView(tblBackcharge);
					tblBackcharge.getTableHeader().setReorderingAllowed(false);
					tblBackcharge.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					tblBackcharge.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
					tblBackcharge.getColumnModel().getColumn(0).setPreferredWidth(240);//contractor_name
					tblBackcharge.getColumnModel().getColumn(1).setPreferredWidth(120);//contractor_type_id
					tblBackcharge.getColumnModel().getColumn(2).setPreferredWidth(100);//amount
					tblBackcharge.getColumnModel().getColumn(3).setPreferredWidth(200);//remarks
					tblBackcharge.getColumnModel().getColumn(4).setWidth(0);//entity_id
					tblBackcharge.getColumnModel().getColumn(4).setMinWidth(0);//entity_id
					tblBackcharge.getColumnModel().getColumn(4).setMaxWidth(0);//entity_id
					tblBackcharge.getColumnModel().getColumn(5).setWidth(0);//entity_id
					tblBackcharge.getColumnModel().getColumn(5).setMinWidth(0);//entity_id
					tblBackcharge.getColumnModel().getColumn(5).setMaxWidth(0);//entity_id

					tblBackcharge.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(txt));
					tblBackcharge.getColumnModel().getColumn(2).setCellRenderer(new HighlightAmounts());

					tblBackcharge.setFont(dialog11Font);
					tblBackcharge.setCellSelectionEnabled(true);
					tblBackcharge.addKeyListener(keyAdapter);
					tblBackcharge.setName("tblBackcharge");
					mlBackcharge = new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							if((evt.getClickCount()>=2)){
								final int c = tblBackcharge.getSelectedColumn();
								final int r = tblBackcharge.getSelectedRow();
								if(c == 0 || c == 1){
									new Runnable() {
										public void run() {
										/*	DlgListFind dlg = new DlgListFind((JTextField) tblBackcharge.getEditorComponent(), c == 0 ? Backcharges_DB.getContractorList():Backcharges_DB.getPayeeType(), "Contractors List");
											Object[] data = dlg.getDataSet();
											if(dlg.getDataSet()==null);
											else {
												if(c==0){
													varbContractorId=data[0].toString();
													tblBackcharge.setValueAt(data[1].toString(), r, c);
													tblBackcharge.setValueAt(data[0].toString(), r, 4);

													System.out.println("Ung laman: "+tblBackcharge.getValueAt(tblBackcharge.getRowCount()-1, c));
													if(tblBackcharge.getValueAt(tblBackcharge.getRowCount()-1, c)!=null){
														((DefaultTableModel)tblBackcharge.getModel()).insertRow(tblBackcharge.getRowCount(), new Object[]{null, null, null, null, null, null});
													}
												}
												else{
													varbTypeId=data[0].toString();
													tblBackcharge.setValueAt(data[1].toString(), r, c);
													tblBackcharge.setValueAt(data[0].toString(), r, 5);
												}
											}*/
										}
									}.run();
								}
								else ;
							} 
						}
					};
				}
			}
			{
				checkBackcharges = new JCheckBox("Backcharges");
				getContentPane().add(checkBackcharges);
				checkBackcharges.setFont(dialog12Bold);
				checkBackcharges.setForeground(Color.RED);
				checkBackcharges.setBackground(new Color(244,244,244));
				checkBackcharges.setBounds(4, 377, 152, 16);
				checkBackcharges.setEnabled(false);
				checkBackcharges.setName("checkBackcharges");
				checkBackcharges.addItemListener(new ItemListener(){
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange() == ItemEvent.SELECTED){
							if(!btnNew.isEnabled()){
								((DefaultTableModel)tblBackcharge.getModel()).setRowCount(1);
								tblBackcharge.setEnabled(true);
								tblBackcharge.addMouseListener(mlBackcharge);

								if(!txtareaRemarks.getText().trim().equals("")){
									tblBackcharge.setValueAt(txtareaRemarks.getText().trim(), 0, 3);
								}

								if(tblBackcharge.getValueAt(0, 2)!=null){
									txtVat.setText("0.00");
									txtWTax.setText("0.00");
									txtNetAmount.setText(txtRepairAmount.getText());
								}
							}
						}
						if(e.getStateChange() == ItemEvent.DESELECTED){
							if(!btnNew.isEnabled()){
								Double amount = Double.parseDouble(txtRepairAmount.getText().replace(",", ""));
								Double vat = ((amount / 1.12) * .12);
								Double wtax = ((amount / 1.12) * .02);
								Double net = (amount - wtax);

								txtVat.setText(df.format(vat));
								txtWTax.setText(df.format(wtax));
								txtNetAmount.setText(df.format(net));

								tblBackcharge.removeMouseListener(mlBackcharge);
								if(tblBackcharge.getValueAt(0, 0)==null){
									clearRows(modelBackcharge);
								}else{
									tblBackcharge.clearSelection();
									tblBackcharge.setEnabled(false);
								}
							}
						}
					}
				});
			}

			//Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void refreshState(){
		lookupPayee.setText("");
		lookupPayee.setEnabled(false);
		tagPayee.setText("[ ]");

		lookupPayeeType.setText("");
		lookupPayeeType.setEnabled(false);
		tagPayeeType.setText("[ ]");

		dateRepair.getCalendarButton().setEnabled(false);
		txtRepairAmount.setText("");
		txtRepairAmount.setEditable(false);
		txtVat.setText("");
		txtVat.setEditable(false);
		txtWTax.setText("");
		txtWTax.setEditable(false);
		txtNetAmount.setText("");
		txtNetAmount.setEditable(false);
		txtareaRemarks.setText("");
	}

	protected void newState(){
		// TODO newState()
		lookupPayee.setText("");
		lookupPayee.setEnabled(true);
		tagPayee.setText("[ ]");

		lookupPayeeType.setText("");
		lookupPayeeType.setEnabled(true);
		tagPayeeType.setText("[ ]");

		dateRepair.getCalendarButton().setEnabled(true);
		txtRepairAmount.setText("");
		txtRepairAmount.setEditable(true);
		txtVat.setText("");
		txtVat.setEditable(false);
		txtWTax.setText("");
		txtWTax.setEditable(false);
		txtNetAmount.setText("");
		txtNetAmount.setEditable(false);
		txtareaRemarks.setText("");
		txtareaRemarks.setEditable(true);

		checkBackcharges.setEnabled(true);
		clearRows(modelBackcharge);
	}

	protected void btnState(Boolean bNew, Boolean bEdit, Boolean bDelete, Boolean bSave, Boolean bCancel){
		btnNew.setEnabled(bNew);
		btnEdit.setEnabled(bEdit);
		btnDelete.setEnabled(bDelete);
		btnSave.setEnabled(bSave);
		btnCancel.setEnabled(bCancel);
	}

	protected void editCell(JTable table, int rowNum, int colNum, String toEdit){
		Double repairAmount = Double.parseDouble(txtRepairAmount.getText().replace(",", ""));
		String str = (String) JOptionPane.showInputDialog(null, "Enter "+table.getColumnName(colNum), "JBlitz 1.0", 1, null, null, toEdit);
		if(str != null){
			if(colNum==2) {
				try {
					Double bAmount = Double.parseDouble(str.replace(",", ""));
					if(bAmount > repairAmount) ;
					else table.setValueAt(df.format(bAmount), rowNum, colNum);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Wrong input for "+table.getColumnName(colNum), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else table.setValueAt(str, rowNum, colNum);
		}
		table.grabFocus();
	}

	protected void clearRows( DefaultTableModel model ){
		for( int i = model.getRowCount() - 1; i >= 0; i-- ) model.removeRow(i);
	}

	class HighlightAmounts extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		private _JXFormattedTextField txt = new _JXFormattedTextField();
		private Color HIGHLIGHT = Color.CYAN;
		int r, c;
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setBackground(listRow.contains(row) && column==3 || listRow.contains(row) && column==9 || listRow.contains(row) && column==10 ? !btnEdit.isEnabled() ? isSelected ? Color.GRAY:HIGHLIGHT : isSelected ? table.getSelectionBackground():table.getBackground() : isSelected ? table.getSelectionBackground():table.getBackground());
			setForeground(listRow.contains(row) && column==3 || listRow.contains(row) && column==9 || listRow.contains(row) && column==10 ? !btnEdit.isEnabled() ? isSelected ? Color.WHITE:Color.BLACK : isSelected ? table.getSelectionForeground():table.getForeground() : isSelected ? table.getSelectionForeground():table.getForeground());
			this.r = row;
			this.c = column;
			return this;
		}
		public void setValue(Object value) {
			//txt.setInputType(inpType.DECIMAL);
			try {
				txt.setText(value.toString());
			} catch (java.lang.NullPointerException e) {
				txt.setText(null);
			}
			setHorizontalAlignment(JLabel.RIGHT);
			setText(txt.getText());
		}
	}

	protected void cancelState(){
		lookupProject.setText("");
		lookupProject.setEnabled(true);
		tagProject.setText("[ ]");
		lookupUnit.setEnabled(false);
		lookupUnit.setText("");
		tagUnit.setText("[ ]");
		txtNtp.setText("");
		tagContract.setText("[ ]");
		txtContractor.setText("");
		tagContractor.setText("[ ]");
		txtRetensionReleased.setText("");
		txtRetAging.setText("");
		txtAwardedCost.setText("");

		lookupPayee.setText("");
		lookupPayee.setEnabled(false);
		tagPayee.setText("[ ]");

		lookupPayeeType.setText("");
		lookupPayeeType.setEnabled(false);
		tagPayeeType.setText("[ ]");

		dateRepair.getCalendarButton().setEnabled(false);

		txtRepairAmount.setEditable(false);
		txtRepairAmount.setText("");
		txtVat.setText("");
		txtWTax.setText("");
		txtNetAmount.setText("");
		txtareaRemarks.setText("");
		txtareaRemarks.setEditable(false);

		rowSM.removeListSelectionListener(lsl);

		clearRows(modelDetail);
		clearRows(modelBackcharge);
		checkBackcharges.setSelected(false);
		checkBackcharges.setEnabled(false);
		//TODO
	}

	protected Boolean toSave(){
		String contractor = tagContractor.getText().replace("[ ", "").replace(" ]", "");
		String bc_contractor = tblBackcharge.getValueAt(0, 0).toString();
		if(!contractor.equals(bc_contractor)){
			return false;
		}else{
			return true;
		}
	}

	protected int[] getSelectedIndices(ListSelectionModel model, int start, int stop) {
		if ((start == -1) || (stop == -1)) {
			// no selection, so return an empty array
			return new int[0];
		}

		int guesses[] = new int[stop - start + 1];
		int index = 0;
		// manually walk through these . . .
		for (int i = start; i <= stop; i++) {
			if (model.isSelectedIndex(i)) {
				guesses[index++] = i;
			}
		}
		// ok, pare down the guess array to the real thing
		int realthing[] = new int[index];
		System.arraycopy(guesses, 0, realthing, 0, index);
		return realthing;
	}

	protected void reloadState(){
		rowSM.removeListSelectionListener(lsl);
		clearRows(modelDetail);
		clearRows(modelBackcharge);

		/*txtNtp.setText(Backcharges_DB.getUnitInfo()[0].toString());
		tagContract.setText("[ "+Backcharges_DB.getUnitInfo()[1].toString()+" ]");
		txtContractor.setText(Backcharges_DB.getUnitInfo()[2].toString());
		tagContractor.setText("[ "+Backcharges_DB.getUnitInfo()[3].toString()+" ]");
		Backcharges_DB.displayRepairDetail(tblDetail, lookupUnit.getText());
		rowSM.addListSelectionListener(lsl);*/

		refreshState();
		checkBackcharges.setEnabled(false);

		btnState(true, false, false, false, false);
	}

	protected void saveState(){
		/*String drf_no = ContractorsBilling_DB.getDRFNo(co_id);
		String repair_no = Backcharges_DB.getRepairNo();

		//save to tf_co_house_repair
		Backcharges_DB.savetoHouseRepair(
				repair_no,
				txtContractor.getText(),
				//txtContractor.getText(),
				lookupProject.getText(), 
				lookupUnit.getText(), 
				lookupPayee.getText(), 
				lookupPayeeType.getText(), 
				dateRepair.getDate(), 
				Double.parseDouble(txtRepairAmount.getText().replace(",", "")), 
				Double.parseDouble(txtVat.getText().replace(",", "")), 
				Double.parseDouble(txtWTax.getText().replace(",", "")), 
				Double.parseDouble(txtNetAmount.getText().replace(",", "")), 
				txtareaRemarks.getText(), 
				co_id,
				drf_no,
				checkBackcharges.isSelected());

		//save to tf_drf_main
		Backcharges_DB.saveNtpRP("03", lookupPayee.getText(), lookupPayeeType.getText(), drf_no);

		if(checkBackcharges.isSelected()){
			//save to tf_co_house_repair_backcharges
			Backcharges_DB.savetoBackcharges(tblBackcharge, repair_no);

			//save to tf_drf_detail
			Backcharges_DB.saveBackchargeRpDetail(
					tblBackcharge, 
					co_id, 
					Double.parseDouble(txtRepairAmount.getText().replace(",", "")), 
					lookupPayee.getText(), 
					lookupPayeeType.getText(), 
					lookupProject.getText(),
					repair_no,
					drf_no,
					txtareaRemarks.getText(),
					lookupPayeeType.getText().equals("19") ? "20":"21");
		}else{
			//save to tf_drf_detail
			Backcharges_DB.saveRepairRpDetail("03", 
					Double.parseDouble(txtRepairAmount.getText().replace(",", "")), 
					lookupPayee.getText(), 
					lookupPayeeType.getText(), 
					lookupProject.getText(),
					txtContractor.getText(),
					tagContract.getText().replace("[ ", "").replace(" ]", ""),
					0.00,
					"02600202",
					drf_no,
					drf_no,
					txtareaRemarks.getText(),
					lookupPayeeType.getText().equals("19") ? "20":"21",
							txtVat.getText().replace(",", ""),
							txtWTax.getText().replace(",", ""),
							txtNetAmount.getText().replace(",", ""));
		}
		reloadState();*/
	}

	protected void enableUnit(String proj_id, String proj_name, String comp_id){
		lookupProject.setText(proj_id);
		tagProject.setText("[ "+proj_name+" ]");
		co_id = comp_id;

		lookupUnit.setText("");
		lookupUnit.setEnabled(true);
		//lookupUnit.setLookupSQL(Backcharges_DB.getUnit(proj_id));
	}

	protected void displayUnitDetails(String pbl_id, String unit_desc){
		rowSM.removeListSelectionListener(lsl);
		refreshState();
		clearRows(modelDetail);
		clearRows(modelBackcharge);

		lookupUnit.setText(pbl_id);
		tagUnit.setText("[ "+unit_desc+" ]");
		/*Backcharges_DB.displayUnitInfo(pbl_id);

		txtNtp.setText(Backcharges_DB.getUnitInfo()[0].toString());
		tagContract.setText("[ "+Backcharges_DB.getUnitInfo()[1].toString()+" ]");
		txtContractor.setText(Backcharges_DB.getUnitInfo()[2].toString());
		tagContractor.setText("[ "+Backcharges_DB.getUnitInfo()[3].toString()+" ]");
		Backcharges_DB.displayRepairDetail(tblDetail, pbl_id);
		rowSM.addListSelectionListener(lsl);*/

		btnState(true, true, false, false, false);
	}
}
