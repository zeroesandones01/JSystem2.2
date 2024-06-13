package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelsupplier_procurement;

public class Supplier extends _JInternalFrame implements _GUI, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String title="Supplier Module";
	public static Dimension framesize= new Dimension(800,700);

	private JPanel pnlmain;
	private JScrollPane jscrollSupplier;
	private _JTableMain tblsupplier;
	private static JList rowheadersupplier;

	private JButton btnadd;
	private JButton btnedit;
	private JButton btndelete;
	private JButton btnsave;
	private JButton btncancel;
	private JButton btnAddAcct;
	private JButton btnRemoveAcct;

	private static modelsupplier_procurement modelsupplier;
	private _JLookup lookupsupp_id;
	private _JLookup lookupattachment;
	private JCheckBox chkboxvat_registered;
	public static ButtonGroup grpadd_edit = new ButtonGroup();
	protected String supplier_id = "";

	private JTextField txtsupp_name;
	private JTextField txtauthorized_person;
	private JTextField txtposition;
	private JTextField txttin;
	private JTextField txtvat_registered;
	private JTextField txtbusiness_tel_no;
	private JTextField txtcp_no;
	private JTextField txtfax_no;
	private JTextField txtemail_add;
	private JTextField txtfb_acct;
	private JTextField txtbusiness_nature;
	private JTextField txtbusiness_class;
	private static JTextField txtname;
	private static JTextField txttel_no;
	private static JTextField txtemail;
	private static JTextField txtfax;
	private static JTextField txtfb;
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	public Supplier(){
		super(title, false, true, true, true);
		initGUI();
		
	}
	public Supplier (String title) {
		super(title, true, true, true, true);
		initGUI();
	}
	public Supplier (String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
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
				JPanel pnlNorth= new JPanel(new BorderLayout(5,5));
				pnlmain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 370));
				{
					JPanel pnlmain_info = new JPanel(new BorderLayout(5,5));
					pnlNorth.add(pnlmain_info, BorderLayout.NORTH);
					pnlmain_info.setPreferredSize(new Dimension(0, 250));
					{
						JPanel pnlmain_info_west = new JPanel(new BorderLayout(5, 5));
						pnlmain_info.add(pnlmain_info_west, BorderLayout.WEST);
						pnlmain_info_west.setPreferredSize(new Dimension(550, 0));
						pnlmain_info_west.setBorder(JTBorderFactory.createTitleBorder("Main Info"));
						{
							JPanel pnllabels_fields = new JPanel(new BorderLayout(5, 5));
							pnlmain_info_west.add(pnllabels_fields);
							pnllabels_fields.setPreferredSize(new Dimension(450, 0));
							{
								JPanel pnlwest = new JPanel(new GridLayout(8, 1, 3, 3));
								pnllabels_fields.add(pnlwest, BorderLayout.WEST);
								pnlwest.setPreferredSize(new Dimension(120,0));
								{
									JLabel lblsupp_id = new JLabel("Supplier ID");
									pnlwest.add(lblsupp_id);
								}
								{
									JLabel lblsupp_name = new JLabel("Supplier Name");
									pnlwest.add(lblsupp_name);
								}
								{
									JLabel lblauthorized_person= new JLabel("Authorized Person");
									pnlwest.add(lblauthorized_person);
								}
								{
									JLabel lblposition= new JLabel("Position");
									pnlwest.add(lblposition);
								}
								{
									JLabel lbltin= new JLabel("Tin No");
									pnlwest.add(lbltin);
								}
								{
									JLabel lblvat_registered= new JLabel("Vat Registered");
									pnlwest.add(lblvat_registered);
								}
								{
									JLabel lblbusiness_nature= new JLabel("Business Nature");
									pnlwest.add(lblbusiness_nature);
								}
								{
									JLabel lblbusiness_class= new JLabel("Business Class");
									pnlwest.add(lblbusiness_class);
								}
							}
							{
								JPanel pnleast = new JPanel(new GridLayout(8, 1, 3, 3));
								pnllabels_fields.add(pnleast, BorderLayout.CENTER);
								{
									JPanel pnlsupp_id = new JPanel(new BorderLayout(5,5));
									pnleast.add(pnlsupp_id);
									{
										lookupsupp_id= new _JLookup(null, null, 0, 0);
										pnlsupp_id.add(lookupsupp_id, BorderLayout.WEST);
										lookupsupp_id.setPreferredSize(new Dimension(100,0));
										lookupsupp_id.setLookupSQL(getsupplier());
										lookupsupp_id.addLookupListener(new LookupListener() {
											


											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												pgSelect db = new pgSelect();
												if(data !=null){
													supplier_id = (String)data[1];
													
													lookupsupp_id.setValue(supplier_id);
													displaysupplierdetails(supplier_id);
													display_supplier_product(modelsupplier, rowheadersupplier, supplier_id, db);
													tblsupplier.packAll();
													displaycontactperson(supplier_id);
													enablebuttons(true, true, false, false, true);
												}
											}
										});
									}
								}
								{
									txtsupp_name= new JTextField();
									txtsupp_name.setEditable(false);
									pnleast.add(txtsupp_name);
								}
								{
									txtauthorized_person= new JTextField();
									txtauthorized_person.setEditable(false);
									pnleast.add(txtauthorized_person);
								}
								{
									txtposition= new JTextField();
									txtposition.setEditable(false);
									pnleast.add(txtposition);
								}
								{
									txttin=new JTextField();
									txttin.setEditable(false);
									pnleast.add(txttin);
								}
								{
									JPanel pnlvatregistered_attachments= new JPanel(new BorderLayout(3,3));
									pnleast.add(pnlvatregistered_attachments);
									{
										chkboxvat_registered= new JCheckBox();
										chkboxvat_registered.setEnabled(false);
										pnlvatregistered_attachments.add(chkboxvat_registered, BorderLayout.WEST);
										chkboxvat_registered.setPreferredSize(new Dimension(40, 0));
									}
									/*{
										JLabel lblattachment = new JLabel("Attachment", JLabel.TRAILING);
										pnlvatregistered_attachments.add(lblattachment);
									}
									{
										lookupattachment = new _JLookup();
										pnlvatregistered_attachments.add(lookupattachment, BorderLayout.EAST);
										lookupattachment.setPreferredSize(new Dimension(80, 0));
									}*/
								}
								{
									txtbusiness_nature= new JTextField();
									txtbusiness_nature.setEditable(false);
									pnleast.add(txtbusiness_nature);
								}
								{
									txtbusiness_class=new JTextField();
									txtbusiness_class.setEditable(false);
									pnleast.add(txtbusiness_class);
								}

							}
							
						}
						
					}
					{
						JPanel pnlmain_info_east = new JPanel(new BorderLayout(5, 5));
						pnlmain_info.add(pnlmain_info_east, BorderLayout.CENTER);
						pnlmain_info_east.setBorder(JTBorderFactory.createTitleBorder("Contact Person"));

						{
							JPanel pnlcontactperson_details= new JPanel(new GridLayout(10, 1, 3, 3));
							pnlmain_info_east.add(pnlcontactperson_details, BorderLayout.CENTER);
							pnlcontactperson_details.setPreferredSize(new Dimension(0, 200));
							{
								JLabel lblname= new JLabel("Name");
								pnlcontactperson_details.add(lblname);
								lblname.setFont(new Font("Tahoma", Font.PLAIN, 9));
							}
							{
								 txtname = new JTextField();
								txtname.setEditable(false);
								pnlcontactperson_details.add(txtname);
							}
							{
								JLabel lbltel_no = new JLabel("Tel. No.");
								pnlcontactperson_details.add(lbltel_no);
								lbltel_no.setFont(new Font("Tahoma", Font.PLAIN, 9));
							}
							{
								 txttel_no=new JTextField();
								txttel_no.setEditable(false);
								pnlcontactperson_details.add(txttel_no);
							}
							{
								JLabel lblemail_add=new JLabel("Email");
								pnlcontactperson_details.add(lblemail_add);
								lblemail_add.setFont(new Font("Tahoma", Font.PLAIN, 9));
							}
							{
								 txtemail = new JTextField();
								txtemail.setEditable(false);
								pnlcontactperson_details.add(txtemail);
							}
							{
								JLabel lblfax = new JLabel("Fax No.");
								pnlcontactperson_details.add(lblfax);
								lblfax.setFont(new Font("Tahoma", Font.PLAIN, 9));
							}
							{
								 txtfax = new JTextField();
								txtfax.setEditable(false);
								pnlcontactperson_details.add(txtfax);
							}
							{
								JLabel lblfb =new JLabel("FB Acct.");
								pnlcontactperson_details.add(lblfb);
								lblfb.setFont(new Font("Tahoma", Font.PLAIN, 9));
							}
							{
								 txtfb = new JTextField();
								txtfb.setEditable(false);
								pnlcontactperson_details.add(txtfb);
							}
						}
						
					}
				}
				{
					JPanel pnlsupplier_contact_details = new JPanel(new BorderLayout(5,5));
					pnlNorth.add(pnlsupplier_contact_details, BorderLayout.CENTER);
					pnlsupplier_contact_details.setBorder(JTBorderFactory.createTitleBorder("Supplier Contact Details"));
					{
						 JPanel pnlsupplier_contact_details_west = new JPanel(new BorderLayout(5,5));
						 pnlsupplier_contact_details.add(pnlsupplier_contact_details_west, BorderLayout.WEST);
						 pnlsupplier_contact_details_west.setPreferredSize(new Dimension(400, 0));
						 {
								JPanel pnlsupp_contact_details_labels = new JPanel(new GridLayout(3, 1, 3, 3));
								pnlsupplier_contact_details_west.add(pnlsupp_contact_details_labels, BorderLayout.WEST);
								pnlsupp_contact_details_labels.setPreferredSize(new Dimension(120, 0));
								{
									JLabel lblbusiness_tel_no=new JLabel("Business Tel. No.");
									pnlsupp_contact_details_labels.add(lblbusiness_tel_no);
								}
								{
									JLabel lblcp_no = new JLabel("Cellphone No.");
									pnlsupp_contact_details_labels.add(lblcp_no);
								}
								{
									JLabel lblfax_no= new JLabel("Fax No.");
									pnlsupp_contact_details_labels.add(lblfax_no);
								}
								
							}
							{
								JPanel pnlsupp_contact_details_txtfields = new JPanel(new GridLayout(3, 1, 3, 3));
								pnlsupplier_contact_details_west.add(pnlsupp_contact_details_txtfields, BorderLayout.CENTER);
								{
									txtbusiness_tel_no=new JTextField();
									txtbusiness_tel_no.setEditable(false);
									pnlsupp_contact_details_txtfields.add(txtbusiness_tel_no);
								}
								{
									txtcp_no= new JTextField();
									txtcp_no.setEditable(false);
									pnlsupp_contact_details_txtfields.add(txtcp_no);
								}
								{
									txtfax_no= new JTextField();
									txtfax_no.setEditable(false);
									pnlsupp_contact_details_txtfields.add(txtfax_no);
								}
								
							}
					}
					{
						JPanel pnlsupplier_contact_details_center = new JPanel(new BorderLayout(5,5));
						pnlsupplier_contact_details.add(pnlsupplier_contact_details_center, BorderLayout.CENTER);
						{
							JPanel pnlsupp_contact_details_labels_center = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlsupplier_contact_details_center.add(pnlsupp_contact_details_labels_center, BorderLayout.WEST);
							pnlsupp_contact_details_labels_center.setPreferredSize(new Dimension(100, 0));
							{
								JLabel lblemail_add = new JLabel("Email Address", JLabel.TRAILING);
								pnlsupp_contact_details_labels_center.add(lblemail_add);
							}
							{
								JLabel lblfb_account = new JLabel("Facebook Acct.", JLabel.TRAILING);
								pnlsupp_contact_details_labels_center.add(lblfb_account);
							}
						}
						{
							JPanel pnlsupp_contact_details_txtfields_center = new JPanel(new GridLayout(3, 1, 3, 3));
							pnlsupplier_contact_details_center.add(pnlsupp_contact_details_txtfields_center, BorderLayout.CENTER);
							{
								txtemail_add= new JTextField();
								txtemail_add.setEditable(false);
								pnlsupp_contact_details_txtfields_center.add(txtemail_add);
							}
							{
								txtfb_acct = new JTextField();
								txtfb_acct.setEditable(false);
								pnlsupp_contact_details_txtfields_center.add(txtfb_acct);
							}
						}
					}
					
				}
			}
			{
				JPanel pnlCenter = new JPanel(new BorderLayout(5,5));
				pnlmain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Supplier Product Details"));
				{
					jscrollSupplier = new JScrollPane();
					pnlCenter.add(jscrollSupplier, BorderLayout.CENTER);
					jscrollSupplier.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelsupplier = new modelsupplier_procurement();
						tblsupplier = new _JTableMain(modelsupplier);
						jscrollSupplier.setViewportView(tblsupplier);
						modelsupplier.setEditable(false);
						tblsupplier.setEditable(false);
						//tblsupplier.packAll();
						
						tblsupplier.getColumnModel().getColumn(0).setPreferredWidth(150);//product id
						tblsupplier.getColumnModel().getColumn(1).setPreferredWidth(250);//product name
						tblsupplier.getColumnModel().getColumn(2).setPreferredWidth(250);//item id
						tblsupplier.getColumnModel().getColumn(3).setPreferredWidth(100);//item name
						tblsupplier.getColumnModel().getColumn(4).setPreferredWidth(100);//category id
						tblsupplier.getColumnModel().getColumn(5).setPreferredWidth(250);//category_name
						tblsupplier.getColumnModel().getColumn(6).setPreferredWidth(250);//model
						tblsupplier.getColumnModel().getColumn(7).setPreferredWidth(250);//brand
						tblsupplier.getColumnModel().getColumn(8).setPreferredWidth(100);//unit
						tblsupplier.getColumnModel().getColumn(9).setPreferredWidth(150);//unit
						tblsupplier.getColumnModel().getColumn(10).setPreferredWidth(200);//item unit
						
						
						
						tblsupplier.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {
									int column 	= tblsupplier.getSelectedColumn();
									if(column == 2) {displayitem_category();}
									if(column == 10){display_itemunit();}
									tblsupplier.packAll();
								}
								
							}
							public void mouseReleased(MouseEvent e) {
								if ((e.getClickCount() >= 2)) {
									int column 	= tblsupplier.getSelectedColumn();
									if(column == 2) {displayitem_category();}
									if(column == 10){display_itemunit();}
									tblsupplier.packAll();
								}
							}
						});
					}
					{
						rowheadersupplier= tblsupplier.getRowHeader();
						jscrollSupplier.setRowHeaderView(rowheadersupplier);
						jscrollSupplier.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						
					}
					{
						jscrollSupplier.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,displayTableNavigation());
					}
				}
			}
			{
				JPanel pnlSouth= new JPanel(new GridLayout(1, 5, 5, 5));
				pnlmain.add(pnlSouth,BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 35));
				{
					btnadd = new JButton("ADD");
					pnlSouth.add(btnadd);
					btnadd.setActionCommand("ADD");
					btnadd.setEnabled(false);
					grpadd_edit.add(btnadd);
					btnadd.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							FncTables.clearTable(modelsupplier);
							cleartable_rowheader();
							modelsupplier.addRow(new Object[] {setproduct_id(),null,null,null,null});
							//modelsupplier.addRow(new Object[] {getlast_produtctid_ontable(),null,null,null,null});
							((DefaultListModel) rowheadersupplier.getModel()).addElement(modelsupplier.getRowCount());
							modelsupplier.setEditable(true);
							tblsupplier.setEditable(true);
							btnAddAcct.setEnabled(true);
							btnRemoveAcct.setEnabled(true);
							enablebuttons(false, false, false, true, true);
							grpadd_edit.setSelected(btnadd.getModel(), true);
						}
					});
					
				}
				{
					btnedit = new JButton("EDIT");
					btnedit.setActionCommand("EDIT");
					pnlSouth.add(btnedit);
					btnedit.setEnabled(false);
					grpadd_edit.add(btnedit);
					btnedit.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							enablebuttons(false, false, false, true, true);
							int toedit = JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to edit contact person?", "Edit", JOptionPane.YES_NO_OPTION);
							if(toedit == JOptionPane.YES_OPTION) {
								txtname.setEditable(true);
								txttel_no.setEditable(true);
								txtemail.setEditable(true);
								txtfax.setEditable(true);
								txtfb.setEditable(true);
								grpadd_edit.setSelected(btnedit.getModel(), true);
								
							}else {
								enablebuttons(true, true, false, false, true);
							}							
						}
					});
				}
				{
					btndelete = new JButton("DELETE");
					pnlSouth.add(btndelete);
					btndelete.setActionCommand("DELETE");
					btndelete.setEnabled(false);
					btndelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
						}
					});
				}
				{
					btnsave = new JButton("SAVE");
					pnlSouth.add(btnsave);
					btnsave.setActionCommand("SAVE");
					btnsave.setEnabled(false);
					btnsave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(grpadd_edit.isSelected(btnadd.getModel())) {
								
								int tosave= JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to save new product?", "Save", JOptionPane.YES_NO_OPTION);
								if(tosave == JOptionPane.YES_OPTION) {
								pgSelect db = new pgSelect();
								insertproducts();
								enablebuttons(true, false, false, false, true);
								btnAddAcct.setEnabled(false);
								btnRemoveAcct.setEnabled(false);
								JOptionPane.showMessageDialog(getTopLevelAncestor(), "New products already save!");
								display_supplier_product(modelsupplier, rowheadersupplier, supplier_id, db);
								}
							}else  {
								updatesupplier_contact_person_details(lookupsupp_id.getValue());
								insertcontactperson();
								JOptionPane.showMessageDialog(getTopLevelAncestor(), "Saved!");
								displaycontactperson(lookupsupp_id.getValue());
								enablebuttons(false, false, false, false, true);
								System.out.println("");
								/*int tosave= JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to save new product?", "Save", JOptionPane.YES_NO_OPTION);
								if(tosave == JOptionPane.YES_OPTION) {
								pgSelect db = new pgSelect();	
								insertproducts();
								enablebuttons(true, false, false, false, true);
								btnAddAcct.setEnabled(false);
								btnRemoveAcct.setEnabled(false);
								JOptionPane.showMessageDialog(getTopLevelAncestor(), "New products already save!");
								display_supplier_product(modelsupplier, rowheadersupplier, supplier_id, db);
								}*/
								
							}
						}
					});
				}	
					
				{
					btncancel = new JButton("CANCEL");
					pnlSouth.add(btncancel);
					btncancel.setActionCommand("CANCEL");
					btncancel.setEnabled(false);
					btncancel.addActionListener(this);
				}
			}
		}

	}
	
	public  void actionPerformed(ActionEvent e) {
		
		/*if( e.getActionCommand().equals("ADD") ) {
			
			FncTables.clearTable(modelsupplier);
			cleartable_rowheader();
			modelsupplier.addRow(new Object[] {setproduct_id(),null,null,null,null});
			((DefaultListModel) rowheadersupplier.getModel()).addElement(modelsupplier.getRowCount());
			modelsupplier.setEditable(true);
			tblsupplier.setEditable(true);
			btnAddAcct.setEnabled(true);
			btnRemoveAcct.setEnabled(true);
			enablebuttons(false, false, false, true, true);
		}*/
		
		
		if(e.getActionCommand().equals("Add Acct")) {
			
			modelsupplier.addRow(new Object[] {getlast_produtctid_ontable(),null,null,null,null});
			((DefaultListModel) rowheadersupplier.getModel()).addElement(modelsupplier.getRowCount());
			System.out.println("Last product id= "+getlast_produtctid_ontable());
		}
		
		
		/*if(e.getActionCommand().equals("SAVE")) {
			
			if(grpadd_edit.isSelected(btnadd.getModel())) {
				
				int tosave= JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to save new product?", "Save", JOptionPane.YES_NO_OPTION);
				if(tosave == JOptionPane.YES_OPTION) {
					
				insertproducts();
				enablebuttons(false, false, false, false, true);
				btnAddAcct.setEnabled(false);
				btnRemoveAcct.setEnabled(false);
				JOptionPane.showMessageDialog(getTopLevelAncestor(), "New products already save!");
				}
			}else {
				//updatesupplier_contact_person_details(lookupsupp_id.getValue());
				//insertcontactperson();
				JOptionPane.showMessageDialog(getTopLevelAncestor(), "Saved!");
				displaycontactperson(lookupsupp_id.getValue());
				enablebuttons(false, false, false, false, true);
				System.out.println("");
			}
		}*/
		
		
		if(e.getActionCommand().equals("CANCEL" ) ) {
			if(lookupsupp_id.getValue() != "" && modelsupplier.getRowCount() > 0) {
				//if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to cancel adding new product?")== JOptionPane.YES_OPTION) {
					clearFields();
					btnAddAcct.setEnabled(false);
					btnRemoveAcct.setEnabled(false);
					cleartable_rowheader();
					
					txtname.setText("");
					txttel_no.setText("");
					txtemail.setText("");
					txtfax.setText("");
					txtfb.setText("");
					
					enablebuttons(false, false, false, false, false);
				//}
				
			}else if(lookupsupp_id.getValue() != "") {
				
				lookupsupp_id.setValue("");
				txtsupp_name.setText("");
				txtauthorized_person.setText("");
				txtposition.setText("");
				chkboxvat_registered.setSelected(false);
				txtbusiness_nature.setText("");
				txtbusiness_class.setText("");
				txtfb_acct.setText("");
				txtbusiness_tel_no.setText("");
				txtcp_no.setText("");
				txtfax_no.setText("");
				txtemail_add.setText("");
				txttin.setText("");
				
				txtname.setText("");
				txttel_no.setText("");
				txtemail.setText("");
				txtfax.setText("");
				txtfb.setText("");
				
				enablebuttons(false, false, false, false, false);
			}
		}
		/*if(e.getActionCommand().equals("EDIT" )) {
			
			enablebuttons(false, false, false, true, true);
			int toedit = JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to edit contact person?", "Edit", JOptionPane.YES_NO_OPTION);
			if(toedit == JOptionPane.YES_OPTION) {
				txtname.setEditable(true);
				txttel_no.setEditable(true);
				txtemail.setEditable(true);
				txtfax.setEditable(true);
				txtfb.setEditable(true);
				
			}else {
				enablebuttons(true, true, false, false, true);
			}
		}*/
	}
	
	public void enablebuttons(Boolean add, Boolean edit, Boolean delete, Boolean save, Boolean cancel) {
		btnadd.setEnabled(add);
		btnedit.setEnabled(edit);
		btndelete.setEnabled(edit);
		btnsave.setEnabled(save);
		btncancel.setEnabled(cancel);
	}
	
	private void displayitem_category() {
		int column = tblsupplier.getSelectedColumn();
		int row = tblsupplier.getSelectedRow();
		if (column == 2 && modelsupplier.isEditable() ) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Select Item", getitem(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);
			
			
			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelsupplier.setValueAt(data[1], row, column);
				modelsupplier.setValueAt(data[0], row, 3);
				modelsupplier.setValueAt(data[2], row, 4);
				modelsupplier.setValueAt(data[3], row, 5);
				
				System.out.println("Dumaan sa displayitem_category");
			}			
			tblsupplier.packAll();
			
		}
	}
	
	private void display_terms() {
		
		int column = tblsupplier.getSelectedColumn();
		int row = tblsupplier.getSelectedRow();
		if (column == 10 && modelsupplier.isEditable() ) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Select Terms", get_terms(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);
			
			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelsupplier.setValueAt(data[0], row, column);
				
				System.out.println("Dumaan sa display_terms");
			}			
			//tblsupplier.packAll();
		}
	}
		
	private void display_itemunit() {
		
		int column = tblsupplier.getSelectedColumn();
		int row = tblsupplier.getSelectedRow();
		if (column == 10 && modelsupplier.isEditable() ) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Select Item Unit", get_itemunit(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);
			
			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelsupplier.setValueAt(data[0], row, column);
				
				System.out.println("Dumaan sa display_itemunit");
			}			
			tblsupplier.packAll();
		}
	}

	private String getitem() {
		String strsql="select a.item_name, \n" + 
				"a.item_id,\n" + 
				"b.category_id,\n" + 
				"b.category_name \n" + 
				"from tbl_item a\n" + 
				"left join tbl_category b on a.category_id=b.category_id";
		
		return strsql;
		
	}
	
	private String get_terms() {
		String strsql="select 'COD','01'   union all\n" + 
				"select 'INSTALLMENT', '02'  union all\n" + 
				"select 'CHEQUE', '03' union all \n" + 
				"select '7 DAYS', '04'";
		return strsql;
		
	}
	
	private String get_itemunit() {
		String strsql="select 'PER PIECE'   union all\n" + 
				"select 'PER BOX'  union all\n" + 
				"select 'PER SET' union all\n" + 
				"select 'PER REAM' UNION ALL \n"+ 
				 "select 'PER ROLL' ";
		return strsql;
		
	}
	
	public static void display_supplier_product(DefaultTableModel modelsupplier, JList rowheader, String supplier_id, pgSelect db) {
		
		FncTables.clearTable(modelsupplier);
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader
		rowheader.setModel(listModel);
		
		String strsql = "\n" + 
				"select product_id,\n" + 
				"product_name,\n" + 
				"a.item_id,\n" + 
				"c.item_name,\n" + 
				"a.category_id,\n" + 
				"b.category_name,\n" + 
				"model,\n" + 
				"brand,\n" + 
				"unit_cost,\n" + 
				"unit ,\n" + 
				"item_unit \n"+
				"from rf_supplier_products  a\n" + 
				"left join tbl_category b on a.category_id=b.category_id::varchar\n" + 
				"left join tbl_item c on a.item_id=c.item_id::varchar\n" + 
				"where supplier_id ='"+supplier_id+"'\n" + 
				"and  status_id='A' order by product_id";
		System.out.println("");
		System.out.println("Display_supplier_product: \n"+strsql );
		db.select(strsql);
		
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				modelsupplier.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(modelsupplier.getRowCount());
			}
		}
		
	}
	private void insertcontactperson() {
		pgUpdate db = new pgUpdate();
		
		String strsql="insert into rf_supplier_contact_person_details\n" + 
				"(\n" + 
				"	supplier_id,\n" + 
				"	contact_person,\n" + 
				"	tel_no,\n" + 
				"	email,\n" + 
				"	fax_no,\n" + 
				"	fb_acct,\n" + 
				"	status_id,\n"+
				"	added_by,\n" + 
				"	date_added\n" + 
				")\n" + 
				"values\n" + 
				"(\n" + 
				"	'"+lookupsupp_id.getValue()+"',\n" + 
				"	'"+txtname.getText()+"',\n" + 
				"	'"+txttel_no.getText()+"',\n" + 
				"	'"+txtemail.getText()+"',\n" + 
				"	'"+txtfax.getText()+"',\n" + 
				"	'"+txtfb.getText()+"',\n" + 
				"	'A',\n"+
				"	now(),\n" + 
				"	'"+FncGlobal.getDateToday()+"'\n" + 
				")";
		
		System.out.println("insertcontactperson: "+ strsql );
		db.executeUpdate(strsql, false);
		db.commit();
	}
	
	private void updatesupplier_contact_person_details(String supplier) {//used
		
		pgUpdate db = new pgUpdate();
		String sqlDetail = "update rf_supplier_contact_person_details set status_id = 'I' " +
				"where trim(supplier_id) = '"+supplier+"' " +
				"and status_id = 'A' " ;
		System.out.println("updatesupplier_contact_person_details: "+sqlDetail);
		db.executeUpdate(sqlDetail, false);	
		db.commit();
	}
	
	private void insertproducts() {
		
		int x  = 0;	
		int y  = 1;
		int rw = tblsupplier.getModel().getRowCount();
		String supp_id = lookupsupp_id.getValue();
		
		pgUpdate db = new pgUpdate();
		
		while(x < rw ) {
			
			String product_id     = modelsupplier.getValueAt(x, 0).toString();
			String product_name = modelsupplier.getValueAt(x, 1).toString();
			String item_id = modelsupplier.getValueAt(x, 2).toString();
			String category_id = modelsupplier.getValueAt(x, 4).toString();
			
			String model = modelsupplier.getValueAt(x, 6).toString();
			String brand     = modelsupplier.getValueAt(x, 7).toString();
			String unit_cost         = modelsupplier.getValueAt(x, 8).toString();
			String unit    =  modelsupplier.getValueAt(x, 9).toString();
			String item_unit = modelsupplier.getValueAt(x, 10).toString();
			//String terms = modelsupplier.getValueAt(x, 11).toString();
			
			if(product_id == "") {}
			else {
				String strsql="insert into rf_supplier_products \n" + 
						"(\n" + 
						"rec_id,\n" + 
						"supplier_id,\n" + 
						"product_id,\n" + 
						"product_name,\n"+
						"item_id, \n"+
						"category_id,\n"+
						"model,\n" + 
						"brand,\n" + 
						"unit_cost,\n" + 
						"unit,\n" + 
						"item_unit,\n"+
						"status_id,\n" + 
						//"terms, \n"+
						"added_by,\n" + 
						"date_added\n" + 
						")\n" + 
						"values\n" + 
						"(\n" + 
						"'"+product_id+"',\n" + //rec_id
						"'"+supp_id+"',\n" +  //supplier_id
						"'"+product_id+"',\n" +  //product_id
						"'"+product_name+"',\n"+
						"'"+item_id+"',\n"+
						"'"+category_id+"',\n"+
						"'"+model+"',\n" +  //model
						"'"+brand+"',\n" +  //brand
						"'"+unit_cost+"',\n" + //unit_cost
						"'"+unit+"',\n" +  //unit
						"'"+item_unit+"',\n"+ //item_unit
						"'A',\n" + //status_id
						//"'"+terms+"', \n"+ //terms
						"'"+UserInfo.EmployeeCode+"',\n" +  //added_by
						"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"'\n" + //date_added
						")";
				
				System.out.printf("SQL #1: %s", strsql);
				db.executeUpdate(strsql, false);
				x++;
			}
		}
		db.commit();
	}
	
	public static String setproduct_id() {
		
		pgSelect db = new pgSelect();
		String strSQL = "select  COALESCE(MAX(product_id::int),0) + 1 from rf_supplier_products";
		db.select(strSQL);
		return db.Result[0][0].toString();
		
	}
	
	private Integer getlast_produtctid_ontable(){//used

		Integer x = 0;		
		int rc = tblsupplier.getModel().getRowCount();
		int w = rc - 1;

		while (x<rc) {	

			int seqno = Integer.parseInt(tblsupplier.getValueAt(w,0).toString());
			System.out.println("seqno = "+seqno);
			System.out.println("rowcount = "+rc);
			System.out.println("rc-1 = "+w);

			if (seqno > 0){
				x = seqno+1; break;
			}
			else
			{
				x = 1;
			}
			rc--;
		}
		return x;

	}
	
	public String getsupplier() {
		String strsql="select a.entity_name, \n" + 
				//"a.entity_kind, \n" + 
				"a.entity_id\n" + 
				//"c.entity_type_desc\n" + 
				"from rf_entity  a\n" + 
				"left join rf_entity_assigned_type b on a.entity_id=b.entity_id\n" + 
				"left join mf_entity_type c on b.entity_type_id = c.entity_type_id\n" + 
				"where a.status_id='A'\n" + 
				"and b.status_id='A' \n"+
				"and b.entity_type_id in ('09', --SUPPLIER OF GOODS -  CORP\n" + 
				"			'10',    --SUPPLIER OF GOODS - IND\n" + 
				" 			'11'   	--SUPPLIER-NON WITHHOLDING TAX\n"+
				"			)";
		return strsql;
	}
	
	private JPanel displayTableNavigation() {
		
		
		  btnAddAcct = new JButton(new
		  ImageIcon(this.getClass().getClassLoader().getResource(
		  "Images/Science-Plus2-Math-icon.png")));
		  btnAddAcct.setActionCommand("Add Acct");
		  btnAddAcct.setToolTipText("For multiple entry");
		  btnAddAcct.setEnabled(false); 
		  btnAddAcct.addActionListener(this);
		  
		  btnRemoveAcct = new JButton(new
		  ImageIcon(this.getClass().getClassLoader().getResource(
		  "Images/Science-Minus2-Math-icon.png")));
		  btnRemoveAcct.setActionCommand("Minus Acct");
		  btnRemoveAcct.setToolTipText("For multiple entry");
		  btnRemoveAcct.setEnabled(false); 
		  btnRemoveAcct.addActionListener(this);
		  
		  
		  JPanel pnl = new JPanel(new GridLayout(1, 2)); pnl.add(btnAddAcct);
		  pnl.add(btnRemoveAcct);
		 

		return pnl;
	}
	
	public static void cleartable_rowheader() {
		 
		FncTables.clearTable(modelsupplier);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheadersupplier.setModel(listModel);//Setting of listModel into rowHeader.
	}
	
	public void displaysupplierdetails(String entity_id) {
		pgSelect db = new pgSelect();
		
		String strsql = "select * from view_supplier_details('"+entity_id+"')";
		db.select(strsql);
		
		FncSystem.out("Display Client Information", strsql);
		
		if(db.isNotNull()) {
			txtsupp_name.setText((String) db.getResult()[0][0]);
			txtauthorized_person.setText((String) db.getResult()[0][1]);
			txtposition.setText((String) db.getResult()[0][2]);
			if((Boolean) db.getResult()[0][3].equals("t")) {
				chkboxvat_registered.setSelected(true);
				//txtvat_registered.setText("YES");
			}else {
				chkboxvat_registered.setSelected(false);
				//txtvat_registered.setText("NO");
			}
			txtbusiness_nature.setText((String) db.getResult()[0][4]);
			txtbusiness_class.setText((String) db.getResult()[0][5]);
			txtfb_acct.setText((String) db.getResult()[0][6]);
			txtbusiness_tel_no.setText((String) db.getResult()[0][7]);
			txtcp_no.setText((String) db.getResult()[0][8]);
			txtfax_no.setText((String) db.getResult()[0][9]);
			txtemail_add.setText((String) db.getResult()[0][10]);
			txttin.setText((String) db.getResult()[0][11]);
			
		}
	}
	
	public void displaycontactperson( String supplier_id) {
		pgSelect db = new pgSelect();
		String strsql = "select contact_person, tel_no, email, fax_no, fb_acct  from rf_supplier_contact_person_details where supplier_id= '"+supplier_id+"' and status_id='A'";
		db.select(strsql);
		if(db.isNotNull()) {
			txtname.setText((String)db.getResult()[0][0]);
			txttel_no.setText((String)db.getResult()[0][1]);
			txtemail.setText((String)db.getResult()[0][2]);
			txtfax.setText((String)db.getResult()[0][3]);
			txtfb.setText((String)db.getResult()[0][4]);
		}
	}
	
	public void clearFields(){
		//Enable buttons
		enablebuttons(false, true, true, false, false);
		
		//Clear table
		FncTables.clearTable(modelsupplier);
		
		//clear all fields
		lookupsupp_id.setValue("");
		txtsupp_name.setText("");
		txtauthorized_person.setText("");
		txtposition.setText("");
		chkboxvat_registered.setSelected(false);
		txtbusiness_nature.setText("");
		txtbusiness_class.setText("");
		txtfb_acct.setText("");
		txtbusiness_tel_no.setText("");
		txtcp_no.setText("");
		txtfax_no.setText("");
		txtemail_add.setText("");
		txttin.setText("");
		
	}
	

}
