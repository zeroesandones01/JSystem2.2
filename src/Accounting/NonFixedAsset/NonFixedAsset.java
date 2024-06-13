package Accounting.NonFixedAsset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXFormattedTextField;

import com.toedter.calendar.JDateChooser;

import Accounting.FixedAssets.AssetWarrantyAttachments;
import Accounting.FixedAssets._AssetMonitoring;
import Accounting.FixedAssets.panelAssetInformation;
import Database.pgSelect;
import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JButton;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import net.sf.jasperreports.engine.xml.JRPenFactory.Left;
import tablemodel.modelnonfixedasset;

public class NonFixedAsset extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	
	
	
	
	
	
	private static final long serialVersionUID = 1L;
	static String title = "CAPEX(Charge To Direct Expense)";
	static Dimension framesize = new Dimension(800, 600);
	private JPanel pnlmain;
	private static _JButton btnadd;
	private static _JButton btnedit;
	static _JButton btnsave;
	private static _JButton btncancel;
	private static JTextField txtbrand;
	private static JTextField txtdescription;
	private static JTextField txtsupplier;
	private static JTextField txtcustodian;
	private static JTextField txtcategory;
	private static JTextField txtitem;
	private static JTextField txtstatus;
	private static JTextField txtserialno;
	private static JTextField txtmodelno;
	private static JTextField txtlocation;
	private static JTextField txtcompany;
	private static JTextArea txtremarks;
	private static _JLookup lookupcompany;
	private static _JLookup lookupcustodian;
	private static _JLookup lookupcategory;
	private static _JLookup lookupitemid;
	private static _JLookup lookupsupplierid;
	private static _JLookup lookupID;
	private static _JLookup lookuplocation;
	private static JXFormattedTextField txtamount;
	private JScrollPane scrollnorth;
	private JScrollPane scremark;
	private static JDateChooser dateacquired;
	private static modelnonfixedasset modelnfasset;
	private _JTableMain tblnfasset;
	private static JList rowheadernfasset;
	protected String co_id;
	protected String co_name;
	protected String co_logo;
	protected String emp_code;
	protected String emp_name;
	private JButton btnattachment;
	protected JDialog dialog;
	private static _JButton btnpreview;
	protected static String dept_code;
	protected static String rec_id;
	protected static String objct_id;
	private static String id = "";
	protected static String object_no;
	String to_do 	= "";
	protected static ButtonGroup grpNE = new ButtonGroup();

	public NonFixedAsset() {
		super(title, false,true,true,true);
		initGUI();
	}

	public NonFixedAsset(String title) {
		super(title);
	}

	public NonFixedAsset(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(framesize);
		this.setPreferredSize(framesize);
		this.setLayout(new BorderLayout());
		{
			pnlmain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlmain);
			pnlmain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlmain.setBackground(Color.LIGHT_GRAY);
			pnlmain.setOpaque(true);

			{
				JPanel pnlnorth = new JPanel(new BorderLayout(5, 5));
				pnlmain.add(pnlnorth, BorderLayout.NORTH);
				pnlnorth.setPreferredSize(new Dimension(0, 280));
				pnlnorth.setBorder(components.JTBorderFactory.createTitleBorder("Company",FncLookAndFeel.systemFont_Bold.deriveFont(12f)));
				
				{
					JPanel pnlcompany_status = new JPanel(new BorderLayout(5, 5));
					pnlnorth.add(pnlcompany_status, BorderLayout.NORTH);
					pnlcompany_status.setPreferredSize(new Dimension(0, 35));
					pnlcompany_status.setBorder(_EMPTY_BORDER);

					{
						JLabel lblcompany = new JLabel("Company");
						pnlcompany_status.add(lblcompany, BorderLayout.WEST);
						lblcompany.setPreferredSize(new Dimension(70, 0));
					}
					{
						lookupcompany = new _JLookup();
						pnlcompany_status.add(lookupcompany, BorderLayout.CENTER);
						lookupcompany.setEditable(false);
						lookupcompany.setLookupSQL(_AssetMonitoring.getCompany());
						lookupcompany.addLookupListener(new LookupListener() {

							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if(data != null){
									co_id = (String) data [0];
									co_name = (String) data [1];
									co_logo = (String) data [3];
									lookupcompany.setValue(co_id);
									txtcompany.setText(co_name);
								}
							}
						});
					}
					{
						JPanel pnlstatus = new JPanel(new BorderLayout(5, 5));
						pnlcompany_status.add(pnlstatus, BorderLayout.EAST);
						pnlstatus.setPreferredSize(new Dimension(600, 0));
						{
							txtcompany = new JTextField();
							pnlstatus.add(txtcompany, BorderLayout.WEST);
							txtcompany.setPreferredSize(new Dimension(310, 0));
							txtcompany.setEditable(false);
							txtcompany.setHorizontalAlignment(JTextField.CENTER);
						}
						{
							JLabel lblstatus = new JLabel("Status", JLabel.TRAILING);
							pnlstatus.add(lblstatus, BorderLayout.CENTER);
						}
						{
							txtstatus = new JTextField();
							pnlstatus.add(txtstatus, BorderLayout.EAST);
							txtstatus.setPreferredSize(new Dimension(100, 0));
							txtstatus.setEditable(false);
							txtstatus.setHorizontalAlignment(JTextField.CENTER);
						}
					}
				}
				{
					scrollnorth = new JScrollPane();
					pnlnorth.add(scrollnorth, BorderLayout.CENTER);
					scrollnorth.setBorder(components.JTBorderFactory.createTitleBorder("Item List",FncLookAndFeel.systemFont_Bold.deriveFont(12f)));
					scrollnorth.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollnorth.setBackground(Color.LIGHT_GRAY);
					scrollnorth.setOpaque(true);
					{
						modelnfasset = new modelnonfixedasset();
						tblnfasset = new _JTableMain(modelnfasset);
						scrollnorth.setViewportView(tblnfasset);
						modelnfasset.setEditable(false);
						tblnfasset.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()) {
									try {
										int row = tblnfasset.getSelectedRow();
										objct_id = (String) modelnfasset.getValueAt(row, 1);
										rec_id =  modelnfasset.getValueAt(row, 7).toString();
										setobjectdetails(objct_id);
										enable_buttons(true, true, false, true, true);
										
									}catch (ArrayIndexOutOfBoundsException ex) {
									}
								}
							}
						});
						tblnfasset.getTableHeader().setEnabled(false);
						tblnfasset.getColumnModel().getColumn(0).setPreferredWidth(50);
						tblnfasset.getColumnModel().getColumn(1).setPreferredWidth(100);
						tblnfasset.getColumnModel().getColumn(2).setPreferredWidth(250);
						tblnfasset.getColumnModel().getColumn(3).setPreferredWidth(100);
						tblnfasset.getColumnModel().getColumn(4).setPreferredWidth(100);
						tblnfasset.getColumnModel().getColumn(5).setPreferredWidth(200);
						tblnfasset.getColumnModel().getColumn(6).setPreferredWidth(50);
						tblnfasset.hideColumns("Custodian ID");
					}
					{
						rowheadernfasset = tblnfasset.getRowHeader();
						scrollnorth.setRowHeaderView(rowheadernfasset);
						scrollnorth.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JPanel pnlcenter = new JPanel(new BorderLayout(5, 5));
				pnlmain.add(pnlcenter, BorderLayout.CENTER);
				pnlcenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlcenter.setBorder(components.JTBorderFactory.createTitleBorder("Item Details"));
				{//Panel Center left Panel
					JPanel pnlcenter_a = new JPanel(new BorderLayout(5, 5));
					pnlcenter.add(pnlcenter_a, BorderLayout.CENTER);
					pnlcenter.setPreferredSize(new Dimension(500, 0));
					{
						JPanel pnlcenter_a1 = new JPanel(new BorderLayout(5, 5));
						pnlcenter_a.add(pnlcenter_a1, BorderLayout.CENTER);
						{
							JPanel pnlcenter_a1_center = new JPanel(new GridLayout(7, 1, 5, 5));
							pnlcenter_a1.add(pnlcenter_a1_center, BorderLayout.WEST);
							pnlcenter_a1_center.setPreferredSize(new Dimension(80, 0));
							pnlcenter_a1_center.setBorder(_EMPTY_BORDER);

							{
								JLabel lbl1 = new JLabel("ID", JLabel.LEADING);
								pnlcenter_a1_center.add(lbl1);
							}
							{
								JLabel lbl2 = new JLabel("Custodian", JLabel.LEADING);
								pnlcenter_a1_center.add(lbl2);
							}
							{
								JLabel lbl3 = new JLabel("Category", JLabel.LEADING);
								pnlcenter_a1_center.add(lbl3);
							}
							{
								JLabel lbl4 = new JLabel("Item", JLabel.LEADING);
								pnlcenter_a1_center.add(lbl4);
							}
							{
								JLabel lbl5 = new JLabel("Brand", JLabel.LEADING);
								pnlcenter_a1_center.add(lbl5);
							}
							{
								JLabel lbl6 = new JLabel("Description", JLabel.LEADING);
								pnlcenter_a1_center.add(lbl6);
							}
							{
								JLabel lbl7 = new JLabel("Supplier", JLabel.LEADING);
								pnlcenter_a1_center.add(lbl7);
							}
						}
						{
							JPanel pnlcenter_a1_east = new JPanel(new GridLayout(7, 1, 5, 5));
							pnlcenter_a1.add(pnlcenter_a1_east, BorderLayout.CENTER);
							{
								JPanel pnlid = new JPanel(new BorderLayout(5, 5));
								pnlcenter_a1_east.add(pnlid);
								{
									lookupID = new _JLookup();
									pnlid.add(lookupID, BorderLayout.WEST);
									lookupID.setPreferredSize(new Dimension(80, 0));
								}
								{
									JPanel pnlidcenter = new JPanel(new BorderLayout(5, 5));
									pnlid.add(pnlidcenter, BorderLayout.CENTER);
									{
										JLabel lbldateacquired = new JLabel("Date Acquired", JLabel.TRAILING);
										pnlidcenter.add(lbldateacquired, BorderLayout.CENTER);
									}
									{
										dateacquired = new JDateChooser("yyyy/MM/dd","####/##/##",'_');
										pnlidcenter.add(dateacquired, BorderLayout.EAST);
										dateacquired.setEnabled(false);
										dateacquired.setPreferredSize(new Dimension(110, 0));
									}
								}
							}
							{
								JPanel pnlcustodianid = new JPanel(new BorderLayout(5, 5));
								pnlcenter_a1_east.add(pnlcustodianid);

								{
									lookupcustodian = new _JLookup();
									pnlcustodianid.add(lookupcustodian, BorderLayout.WEST);
									lookupcustodian.setEditable(false);
									lookupcustodian.setPreferredSize(new Dimension(80, 0));
									//lookupcustodian.setLookupSQL(_AssetMonitoring.getCustodian());
									lookupcustodian.setLookupSQL(getCustodian());
									lookupcustodian.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] db = ((_JLookup) event.getSource()).getDataSet();
											
											 emp_code= (String) db[0];
											 emp_name= (String) db[1];
											 co_id = (String) db[2];
											 co_name = (String) db[3];
											 dept_code = (String)db[6];
											 
											 lookupcompany.setValue(co_id);
											 txtcompany.setText(co_name);
											 
											 lookupcustodian.setValue(emp_code);
											 txtcustodian.setText(emp_name);
										}
									});
								}
								{
									txtcustodian = new JTextField();
									pnlcustodianid.add(txtcustodian, BorderLayout.CENTER);
									txtcustodian.setEditable(false);
								}
							}
							{
								JPanel pnlcategoryID = new JPanel(new BorderLayout(5, 5));
								pnlcenter_a1_east.add(pnlcategoryID);
								{
									lookupcategory = new _JLookup();
									pnlcategoryID.add(lookupcategory, BorderLayout.WEST);
									lookupcategory.setEditable(false);
									lookupcategory.setReturnColumn(0);
									lookupcategory.setLookupSQL(_AssetMonitoring.getCategory());
									lookupcategory.setPreferredSize(new Dimension(80, 0));
									lookupcategory.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if(data != null){
												Integer category_id= (Integer) data[0];
												String  category_name= (String) data[1];
												txtcategory.setText(category_name);
												
												lookupitemid.setEditable(true);
												lookupitemid.setLookupSQL(_AssetMonitoring.getItem(lookupcategory.getValue()));
												
											}
										}
									});
								}
								{
									txtcategory = new JTextField();
									pnlcategoryID.add(txtcategory, BorderLayout.CENTER);
									txtcategory.setEditable(false);
								}
							}
							{
								JPanel pnlitemID = new JPanel(new BorderLayout(5, 5));
								pnlcenter_a1_east.add(pnlitemID);
								{
									lookupitemid = new _JLookup();
									pnlitemID.add(lookupitemid, BorderLayout.WEST);
									lookupitemid.setEditable(false);
									lookupitemid.setReturnColumn(0);
									lookupitemid.setPreferredSize(new Dimension(80, 0));
									lookupitemid.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if(data != null){
												txtitem.setText(data[1].toString());
											}
										}
									});
								}
								{
									txtitem = new JTextField();
									pnlitemID.add(txtitem, BorderLayout.CENTER);
									txtitem.setEditable(false);
								}
							}
							{
								txtbrand = new JTextField();
								pnlcenter_a1_east.add(txtbrand);
								txtbrand.setEditable(false);
							}
							{
								txtdescription = new JTextField();
								pnlcenter_a1_east.add(txtdescription);
								txtdescription.setEditable(false);
							}
							{
								JPanel pnlsupplier = new JPanel(new BorderLayout(5, 5));
								pnlcenter_a1_east.add(pnlsupplier);
								{
									lookupsupplierid = new _JLookup();
									pnlsupplier.add(lookupsupplierid, BorderLayout.WEST);
									lookupsupplierid.setEditable(false);
									lookupsupplierid.setPreferredSize(new Dimension(80, 0));
									lookupsupplierid.setLookupSQL(getsupplier());
									lookupsupplierid.setReturnColumn(0);
									lookupsupplierid.setFont(FncLookAndFeel.systemFont_Plain.deriveFont(11f));
									lookupsupplierid.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if(data != null){
												txtsupplier.setText(data[1].toString());
											}
										}
									});
								}
								{
									txtsupplier = new JTextField();
									pnlsupplier.add(txtsupplier, BorderLayout.CENTER);
									txtsupplier.setEditable(false);
								}
							}
						}
					}
				}
				{//Panel Center rigth Panel
					JPanel pnlcenter_b = new JPanel(new BorderLayout(5, 5));
					pnlcenter.add(pnlcenter_b, BorderLayout.EAST);
					pnlcenter_b.setPreferredSize(new Dimension(300, 0));
					{
						JPanel pnlcenter_b_south = new JPanel(new BorderLayout(5, 5));
						pnlcenter_b.add(pnlcenter_b_south, BorderLayout.NORTH);
						pnlcenter_b_south.setPreferredSize(new Dimension(0, 115));
						pnlcenter_b_south.setBorder(_EMPTY_BORDER);
						{
							JPanel pnlcenter_b_south_label = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlcenter_b_south.add(pnlcenter_b_south_label,BorderLayout.WEST);
							pnlcenter_b_south_label.setPreferredSize(new Dimension(65, 0));
							{
								JLabel lblamount = new JLabel("Amount");
								pnlcenter_b_south_label.add(lblamount);
							}
							{
								JLabel lblserial = new JLabel("Serial No.");
								pnlcenter_b_south_label.add(lblserial);
							}
							{
								JLabel lblmodel = new JLabel("Model No.");
								pnlcenter_b_south_label.add(lblmodel);
							}
							{
								JLabel lbllocation = new JLabel("Location");
								pnlcenter_b_south_label.add(lbllocation);
							}
						}
						{
							JPanel pnlcenter_b_south_fields = new JPanel(new GridLayout(4, 1, 5, 5));
							pnlcenter_b_south.add(pnlcenter_b_south_fields, BorderLayout.CENTER);

							{
								txtamount = new _JXFormattedTextField(JXFormattedTextField.LEFT);
								pnlcenter_b_south_fields.add(txtamount);
								txtamount.setEditable(false);
								txtamount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtamount.setHorizontalAlignment(JXFormattedTextField.CENTER);
							}
							{
								txtserialno = new JTextField();
								pnlcenter_b_south_fields.add(txtserialno);
								txtserialno.setEditable(false);
							}
							{
								txtmodelno = new JTextField();
								pnlcenter_b_south_fields.add(txtmodelno);
								txtmodelno.setEditable(false);
							}
							{
								JPanel pnllocation = new JPanel(new BorderLayout(5, 5));
								pnlcenter_b_south_fields.add(pnllocation);
								{
									lookuplocation = new _JLookup();
									pnllocation.add(lookuplocation, BorderLayout.WEST);
									lookuplocation.setEditable(false);
									lookuplocation.setPreferredSize(new Dimension(50, 0));
									lookuplocation.setLookupSQL(panelAssetInformation.getassetlocation());
									lookuplocation.setReturnColumn(0);
									lookuplocation.addLookupListener(new LookupListener() {
										public void lookupPerformed(LookupEvent event) {
											Object [] data = ((_JLookup)event.getSource()).getDataSet();
											if(data !=null){
												txtlocation.setText(data[1].toString());
											}
										}
									});
								}
								{
									txtlocation = new JTextField();
									pnllocation.add(txtlocation, BorderLayout.CENTER);
									txtlocation.setEditable(false);
								}
							}
						}
					}
					{
						JPanel pnlcenter_b_center = new JPanel(new BorderLayout(5, 5));
						pnlcenter_b.add(pnlcenter_b_center, BorderLayout.CENTER);
						pnlcenter_b_center.setBorder(JTBorderFactory.createTitleBorder("Remarks"));
						{
							scremark = new JScrollPane();
							pnlcenter_b_center.add(scremark);
							{
								txtremarks = new JTextArea();
								scremark.add(txtremarks/*, BorderLayout.CENTER*/);
								txtremarks.setEditable(false);
								scremark.setViewportView(txtremarks);
							}
						}
					}
				}
			}
			{
				JPanel pnlsouth = new JPanel(new GridLayout(1, 6, 5, 5)); 
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 35));
				{
					btnadd = new _JButton("AddNew");
					btnadd.setActionCommand("add");
					grpNE.add(btnadd);
					btnadd.setEnabled(false);
					pnlsouth.add(btnadd);
					btnadd.addActionListener(this);
					//btnadd.setBackground(Color.YELLOW);
				}
				{
					btnedit = new _JButton("Edit");
					btnedit.setActionCommand("edit");
					btnedit.setEnabled(false);
					pnlsouth.add(btnedit);
					btnedit.addActionListener(this);
					
				}
				{
					btnsave = new _JButton("Save");
					btnsave.setActionCommand("save");
					btnsave.setEnabled(false);
					pnlsouth.add(btnsave);
					btnsave.addActionListener(this);
				}
				{
					btncancel = new _JButton("Cancel");
					btncancel.setActionCommand("cancel");
					grpNE.add(btncancel);
					pnlsouth.add(btncancel);
					btncancel.setEnabled(false);
					btncancel.addActionListener(this);
				}
				{
					btnpreview = new _JButton("Preview");
					pnlsouth.add(btnpreview);
					btnpreview.setEnabled(false);
					btnpreview.setActionCommand("preview");
					btnpreview.addActionListener(this);
				}
				{
					btnattachment = new JButton("Attachments");
					pnlsouth.add(btnattachment);
					btnattachment.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							dialog = new JDialog(FncGlobal.homeMDI, "Warranty Attachments", true); 
							dialog.setLayout(new BorderLayout(0, 0));
							dialog.setSize(350, 300);
							dialog.setResizable(false);
							

							nonfixedAssetWarrantyAttachments asswar = new nonfixedAssetWarrantyAttachments(lookupID.getValue());
							dialog.add(asswar, BorderLayout.CENTER);

							final Toolkit toolkit = Toolkit.getDefaultToolkit();
							final Dimension screenSize = toolkit.getScreenSize();

							final int x = (screenSize.width - dialog.getWidth()) / 2;
							final int y = (screenSize.height - dialog.getHeight()) / 2;

							dialog.setLocation(x, y);
							dialog.setVisible(true);

						}
					});
					
				}
				
			}
		}
		displayall();
		enable_buttons(true, false, false, true, true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("add")) {
			lookupID.setEditable(false);
			
			ctrl_fields(true);
			clear_fields();
			dateacquired.setDate(FncGlobal.getDateToday());
			tblnfasset.setEditable(false);
			tblnfasset.setEnabled(false);
			enable_buttons(false, false, true, true, false);
			
			object_no = getobjectid();
			System.out.println("object_no: "+getobjectid());
			lookupID.setValue(object_no);
			to_do = "NEW";
			
		}
		if(e.getActionCommand().equals("save")) {
			double amount = 0.00;
			amount = Double.parseDouble(txtamount.getText().trim().replace(",", ""));
			
			System.out.println("Amount"+amount);
			System.out.println("to_do: "+to_do);
			System.out.println("object_no: "+object_no);
			
			
			if(chkdetails()) {//Check first if there are missing details
				if (to_do.equals("NEW")) {
					
					
						if (checksttachment(lookupID.getValue())) {
							
						int save = JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Would you like to proceed?", "Save", JOptionPane.YES_NO_OPTION);
						if(save == JOptionPane.YES_OPTION) {
							
							insertobject(object_no);
							JOptionPane.showMessageDialog(getTopLevelAncestor(), "Done!");
							lookupID.setValue(object_no);
							
							displayall();
							tblnfasset.setEditable(true);
							tblnfasset.setEnabled(true);
							ctrl_fields(false);
							enable_buttons(true, true, false, true, true);
							
						}
						
					}else {
						
						JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please Upload Attachment.", "Save", JOptionPane.WARNING_MESSAGE);
					}
				}else if (to_do.equals("EDIT")) {
					int edit = JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Would you like to save?", "Edit", JOptionPane.YES_NO_OPTION);
					if(edit == JOptionPane.YES_OPTION) {
						//object_no= lookupID.getValue();
						updatenonfixedasset(lookupID.getValue());
						insertobject(lookupID.getValue());
						JOptionPane.showMessageDialog(getTopLevelAncestor(), "Done!");
						

						displayall();
						tblnfasset.setEditable(true);
						tblnfasset.setEnabled(true);
						ctrl_fields(false);
						enable_buttons(true, true, false, true, true);
					}
					
				}else {}
				
			}else {
				
				if(lookupcustodian.getValue().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select Custodian", "Incomplete detail!", JOptionPane.INFORMATION_MESSAGE);
				}
				if(lookupcategory.getValue().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select Category", "Incomplete detail!", JOptionPane.INFORMATION_MESSAGE);
				}
				if(lookupitemid.getValue().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select Item ID", "Incomplete detail!", JOptionPane.INFORMATION_MESSAGE);
				}
				if(txtbrand.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select Brand", "Incomplete detail!", JOptionPane.INFORMATION_MESSAGE);
				}
				if(txtdescription.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select Description", "Incomplete detail!", JOptionPane.INFORMATION_MESSAGE);
				}
				if(lookupsupplierid.getValue().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select Supplier", "Incomplete detail!", JOptionPane.INFORMATION_MESSAGE);
				}
				if(txtamount.getText().equals("0.00")) {
					JOptionPane.showMessageDialog(getContentPane(), "The amount cannot be 0.00", "", JOptionPane.INFORMATION_MESSAGE);
				}
				if( amount  > 4999.00 ) {// as Instructed by Sir Gareth Vacal
					JOptionPane.showMessageDialog(getContentPane(), "The amount cannot exceed 4,999.00", "", JOptionPane.INFORMATION_MESSAGE);
				}
				/*if( amount  > 1999.00 ) {
					JOptionPane.showMessageDialog(getContentPane(), "The amount cannot exceed 1,999.00", "", JOptionPane.INFORMATION_MESSAGE);
				}*/
				if(txtserialno.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select Serial No.", "Incomplete detail!", JOptionPane.INFORMATION_MESSAGE);
				}
				if(txtmodelno.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select Model No.", "Incomplete detail!", JOptionPane.INFORMATION_MESSAGE);
				}
				if(lookuplocation.getValue().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please select Location", "Incomplete detail!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if(e.getActionCommand().equals("edit")) {
			
			ctrl_fields(true);
			tblnfasset.setEditable(false);
			tblnfasset.setEnabled(false);
			enable_buttons(false, false, true, true, false);
			to_do = "EDIT";
			System.out.println (""+lookupID.getValue());
		}
		if(e.getActionCommand().equals("cancel")) {
			
			if(grpNE.isSelected(btnadd.getModel())) {
				System.out.println("Reset");
				if(btnsave.isEnabled() && grpNE.isSelected(btncancel.getModel())) {
					inactiveattachment(lookupID.getValue());
				} 
			}
			
			lookupID.setEditable(true);
			tblnfasset.setEditable(true);
			tblnfasset.setEnabled(true);
			ctrl_fields(false);
			clear_fields();
			enable_buttons(true, false, false, true, true);
			displayall();
			
		}
		if(e.getActionCommand().equals("preview")) {
			String criteria = "";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			//mapParameters.put("co_id", lookupcompany.getValue());
			FncReport.generateReport("/Reports/rptnonfixedassets.jasper", reportTitle, "", mapParameters);
		}
	}
	
	public static void enable_buttons( Boolean add, Boolean edit, Boolean save, Boolean cancel, Boolean preview) {
		
		btnadd.setEnabled(add);
		btnedit.setEnabled(edit);
		btnsave.setEnabled(save);
		btncancel.setEnabled(cancel);
		btnpreview.setEnabled(preview);
	}
	
	public void clear_fields() {
		
		lookupcompany.setValue("");
		txtcompany.setText("");
		txtstatus.setText("");
		lookupID.setValue("");
		dateacquired.setDate(null);
		lookupcustodian.setValue("");
		txtcustodian.setText("");
		lookupcategory.setValue("");
		txtcategory.setText("");
		lookupitemid.setValue("");
		txtitem.setText("");
		txtbrand.setText("");
		txtdescription.setText("");
		lookupsupplierid.setValue("");
		txtsupplier.setText("");
		txtamount.setValue(0.00);
		txtserialno.setText("");
		txtmodelno.setText("");
		lookuplocation.setValue("");
		txtlocation.setText("");
		txtremarks.setText("");
	}
	
	public static void ctrl_fields(Boolean set) {
		dateacquired.setEnabled(set);
		lookupcustodian.setEditable(set);
		lookupcategory.setEditable(set);
		lookupitemid.setEditable(set);
		lookupsupplierid.setEditable(set);
		lookuplocation.setEditable(set);
		txtbrand.setEditable(set);
		txtdescription.setEditable(set);
		txtamount.setEditable(set);
		txtserialno.setEditable(set);
		txtmodelno.setEditable(set);
		txtremarks.setEditable(set);
		
	}
	
	public static String getobjectid(){
		
		pgSelect db = new pgSelect();
		//String strSQL = "select to_char( COALESCE(MAX(object_id)::int,0) + 1 , 'FM00000000') FROM tbl_nonfixedasset";
		String strSQL = " select 'NF'||to_char((max(right(object_id, 8))::int + 1), 'FM00000000') FROM tbl_nonfixedasset ";
		db.select(strSQL);
		if(db != null) {
			id = db.Result[0][0].toString();
		}
			
		return id;
	}
	
	public static String getsupplier() {
		String sql="select x.entity_id, x.entity_name, y.entity_type_id, z.entity_type_desc\n" + 
				"from rf_entity x \n" + 
				"left join rf_entity_assigned_type y on x.entity_id = y.entity_id\n" + 
				"left join mf_entity_type z on y.entity_type_id = z.entity_type_id\n" + 
				"where y.entity_type_id in ('09','10','11','32','33', '42', '43') and x.entity_name != ''\n" + 
				"order by x.entity_name";
		
		System.out.println("getsupplier"+sql);
		return sql;
		
	}
	
	public static String getCustodian(){
		
		String strsql="select a.emp_code,  b.entity_name,e.co_id,e.company_name,d.division_code,d.division_name, c.dept_code \n" + 
				"from  em_employee a\n" + 
				"left join rf_entity b ON a.entity_id=b.entity_id\n" + 
				"left join mf_department as c on a.dept_code=c.dept_code\n"+
				"left join mf_division as d on c.division_code=d.division_code \n"+
				"left join mf_company e on a.co_id=e.co_id\n"+
				"where not emp_status='I'\n";
				//"where e.co_id::int="+co_id+"\n";
				//"AND not emp_status='I' \n";//lookup active emp only
		
		return strsql;
	}
	
	private void insertobject(String object_id) {
		String asset_name = txtdescription.getText().replace("'", "''")+" "+txtbrand.getText().replace("'", "''")+" "+txtitem.getText().replace(",", "''");
		
		pgUpdate db = new pgUpdate();
		String strsql = "insert into tbl_nonfixedasset values (\n" + 
				
				"    '"+lookupitemid.getValue()+"',\n" +   		//item_id
				"    '"+asset_name+"',\n" +         			//object_name
				"    "+txtamount.getValue()+",\n" +        		//asset_cost
				"    '"+object_id+"',\n" +         				//object_id
				"    '"+txtserialno.getText()+"',\n" +          //item_serial
				"    '"+txtmodelno.getText()+"',\n" +         	//item_model
				"    '"+dateacquired.getDate()+"',\n" +        	//date_acquired
				"    null,\n" +        							//from_dep
				"  	 null,\n" +        							//to_dep
				"    '"+txtremarks.getText()+"',\n" +          	//remarks
				"    'Active',\n" +     						//status
				"    '"+lookupsupplierid.getValue()+"',\n" +    //supp_id
				"    '"+lookupcustodian.getValue()+"',\n" +     //current_cust
				"    '',\n" +     								//reference_no
				"    "+txtamount.getValue()+",\n" +         	//original_cost
				"    '"+lookupcompany.getValue()+"',\n" +       //co_id
				"    true,\n" +         						//item_found
				"    '"+txtitem.getText()+"',\n" +          	//item_name
				"    '"+txtbrand.getText()+"',\n" +         	//brand
				"    '"+txtdescription.getText()+"',\n" +       //description   
				"    '"+dept_code+"',\n" +         				//dept_code
				"    '"+lookuplocation.getValue()+"',\n" +      //loc_id
				"    '',\n" +          							//replaced_assetno
				"    '"+UserInfo.EmployeeCode+"',\n" +     		//added_by
				"    now(),\n" +        						//date_added
				"    '',\n" +     								//edited_by
				"    null,\n" +        							//date_edited
				"    null,\n" +        							//date_scanned
				"    '',\n" +     								//scanned_by
				"    null,\n" +        							//date_retired
				"    '',\n" +     								//retired_by
				"    null,\n" +         						//date_disposed
				"    ''\n" +      								//dispose_by
				")";
		System.out.println("insertobject: "+strsql);
		db.executeUpdate(strsql, false);
		db.commit();
	}
	
	private static void displayall() {
		FncTables.clearTable(modelnfasset);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowheadernfasset.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		
		String sql="select false,\n" + 
				"object_id,\n" + 
				"object_name,\n" + 
				"date_acquired,\n" + 
				"current_cust,\n" + 
				"c.entity_name,\n" + 
				"a.status_id, a.rec_id\n, coalesce(a.asset_no::text, '')" + 
				"from tbl_nonfixedasset a\n" + 
				"left join em_employee b on a.current_cust = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id  \n"+
				"where a.status_id = 'Active' order by object_id asc";
		
		System.out.println("Display All: "+sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db != null) {
			for(int x=0; x < db.getRowCount(); x++){
				modelnfasset.addRow(db.getResult()[x]);
				listModel.addElement(modelnfasset.getRowCount());
			}
		}
	}
	
	private static Object [] objectdetails(String object_id) {
		// asdasd
		String sql="select \n" + 
				"coalesce(a.co_id, ''),\n" + 
				"coalesce(d.company_name, ''),\n" + 
				"a.status_id,\n" + 
				"a.object_id,\n" + 
				"a.date_acquired,\n" + 
				"a.current_cust,\n" + 
				"c.entity_name,\n" + 
				"h.category_id,\n" + 
				"h.category_name,\n" + 
				"g.item_id,\n" + 
				"g.item_name,\n" + 
				"coalesce(a.brand, ''),\n" + 
				"coalesce(a.description, ''),\n" + 
				"a.supp_id,\n" + 
				"coalesce(f.entity_name, sup.supp_name),\n" + 
				"a.asset_cost,\n" + 
				"a.item_serial,\n" + 
				"item_model,\n" + 
				"a.loc_id,\n" + 
				"e.loc_name,\n" + 
				"(concat('OLD ASSET NO: ', a.asset_no) ||E'\n' ||a.remarks ) as remarks\n" + 
//				(concat('OLD ASSET NO', 'asset NO') ||E'\n'||'remarks' )
				"from tbl_nonfixedasset a\n" + 
				"left join em_employee b on a.current_cust = b.emp_code\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"left join mf_company d on a.co_id = d.co_id\n" + 
				"left join tbl_asset_location e on a.loc_id = e.loc_id\n" + 
				"left join rf_entity f on a.supp_id = f.entity_id\n" + 
				"left join tbl_item g on a.item_id::int = g.item_id\n" + 
				"left join tbl_category h on g.category_id = h.category_id\n" + 
				"left join tbl_supplier sup on sup.supp_id = a.supp_id::bigint\n" + 
				"where a.object_id = '"+object_id+"' and a.rec_id = '"+rec_id+"'";
		
		System.out.println("objectdetails: "+sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()) {
			return db.getResult()[0];
		}else {
			return null;
		}
	}
	
	private static void setobjectdetails(String object_id) {
		Object[] objdtls = objectdetails(object_id);
		
		lookupcompany.setValue(objdtls[0].toString());
		txtcompany.setText(objdtls[1].toString());
		txtstatus.setText(objdtls[2].toString());
		lookupID.setValue(object_id);
		dateacquired.setDate((Date)objdtls[4]);
		lookupcustodian.setValue(objdtls[5].toString());
		txtcustodian.setText(objdtls[6].toString());
		lookupcategory.setValue(objdtls[7].toString());
		txtcategory.setText(objdtls[8].toString());
		lookupitemid.setValue(objdtls[9].toString());
		txtitem.setText(objdtls[10].toString());
		txtbrand.setText(objdtls[11].toString());
		txtdescription.setText(objdtls[12].toString());
		lookupsupplierid.setValue(objdtls[13].toString());
		txtsupplier.setText(objdtls[14].toString());
		txtamount.setValue(objdtls[15]);
		txtserialno.setText(objdtls[16].toString());
		txtmodelno.setText(objdtls[17].toString());
		lookuplocation.setValue(objdtls[18].toString());
		txtlocation.setText(objdtls[19].toString());
		txtremarks.setText(objdtls[20].toString());
	}
	
	private boolean chkdetails() {
		double amount = 0.00;
		try {
			amount = Double.parseDouble(txtamount.getText().replace(",", ""));
		}catch(java.lang.NumberFormatException e){}
		
		if(lookupcompany.getValue().equals("")
				|| lookupcustodian.getValue().equals("")
				|| lookupcategory.getValue().equals("")
				|| lookupitemid.getValue().equals("")
				|| lookupsupplierid.getValue().equals("")
				|| lookuplocation.getValue().equals("")
				|| txtbrand.getText().equals("")
				|| txtdescription.getText().equals("")
				|| txtserialno.getText().equals("")
				|| txtmodelno.getText().equals("")
				|| amount == 0.00
				//|| amount > 1999.00
				|| amount > 4999.00 // As Requested by Sir Gareth Vacal
				) {
			return false;
		}else {
			return true;
		}
	}
	
	private void updatenonfixedasset(String object_id) {
		pgUpdate db = new pgUpdate();
		String sql = "update tbl_nonfixedasset set\n" + 
				"status_id = 'Inactive',\n" + 
				"edited_by ='"+UserInfo.EmployeeCode+"',\n" + 
				"date_edited= now()\n" + 
				"where object_id = '"+object_id+"'\n" + 
				"and status_id = 'Active'";
		System.out.println("updatenonfixedasset: "+sql);
		db.executeUpdate(sql, false);
		db.commit();
	}
	
	private static void inactiveattachment ( String assetno) {
		pgUpdate db = new pgUpdate();
		String sql = "update tbl_asset_warranty_attachments set status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() where asset_no = '"+assetno+"' and status_id = 'A'";
		
		System.out.println("inactiveattachment: "+sql);
		db.executeUpdate(sql, false);
		db.commit();
	}
	
	private boolean checksttachment(String assetno) {
		pgSelect db = new pgSelect();
		String sql = "select asset_no from tbl_asset_warranty_attachments where asset_no = '"+assetno+"' and status_id = 'A' ";
		System.out.println("checksttachment"+sql);
		db.select(sql);
		if(db.isNotNull()) {
			return true;
		}else {
			return false;
		}
			
	}
}
