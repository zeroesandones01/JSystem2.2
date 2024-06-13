package Buyers.LegalandLiaisoning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.jdesktop.swingx.JXTextField;
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
import tablemodel.model_ForEditRealProperty;



public class ForEditRealPropertyHouse extends _JInternalFrame implements _GUI, MouseListener, KeyListener {

	private static final long serialVersionUID = 6500003584501090022L;

	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;

	JLabel lblCompany;
	JLabel lblProject;
	JLabel lblBlock;
	JLabel lblBatch2;
	JLabel lblOrientationDate;
	JLabel lblClientName;
	JLabel lblFilterToLotOnly;
	JLabel lblPhase;

	JXTextField txtOrientationDate;
	JXTextField txtClientName;
	JXTextField txtCompany;
	JXTextField txtProject;
	JXTextField txtOccupancyStatus;
	JXTextField txtSearch;
	JXTextField txtPhase;
	JXTextField txtBatchNo;
	JXTextField txtParticulars;

	static _JLookup lookupCompany;
	static _JLookup lookupProject;
	static _JLookup lookupParticulars;
	static _JLookup lookupBatchNo;
	static _JLookup lookupClient;
	static _JLookup lookupPhase;


	JComboBox cmbOccupancyStatus;

	JButton btnRetrieve;
	JButton btnEdit;
	JButton btnSave;
	JButton btnPreview;
	JButton btnCancel;

	JCheckBox chkboxFilter;
	JCheckBox chkwithBPermit;
	JCheckBox chkwithCEINo;
	JCheckBox chkwithOccuNo;
	JCheckBox chkwithoutBPermit;
	JCheckBox chkwithoutCEINo;
	JCheckBox chkwithoutOccuNo;

	model_ForEditRealProperty modelEditRPT;
	JScrollPane scrollOccupancy;
	_JTableMain tblEditRPT;
	JList rowheaderOccupancy;
	
	private static String company_logo;
	private static String projcode;
	private static String co_id;
	private static String phase;
	private static String batch;

	static String title = "For Edit Real Property Tax House";
	Dimension frameSize = new Dimension(1000, 600);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);


	public ForEditRealPropertyHouse() {
		super(title, true, true, false, true);
		initGUI();
	}

	public ForEditRealPropertyHouse(String title) {
		super(title, true, true, false, true);
		initGUI();
	}

	public ForEditRealPropertyHouse(String title,
			boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new GridLayout(1, 1, 5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Details"));

				{
					JPanel pnlNorthWest = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthWest);
					{
						JPanel pnlNorthWestLabel = new JPanel(new GridLayout(2, 1, 5, 5));
						pnlNorthWest.add(pnlNorthWestLabel, BorderLayout.WEST);
						{
							lblCompany = new JLabel("Company");
							pnlNorthWestLabel.add(lblCompany);

						}
						{
							lblProject = new JLabel("Project");
							pnlNorthWestLabel.add(lblProject);

						}
						/*{
							JLabel lblOccupancyStatus = new JLabel("Particulars");
							pnlNorthWestLabel.add(lblOccupancyStatus);
						}*/
					}
					{
						JPanel pnlNorthWestComponent = new JPanel(new GridLayout(2, 1, 5, 5));
						pnlNorthWest.add(pnlNorthWestComponent);
						{
							JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
							pnlNorthWestComponent.add(pnlCompany);
							{
								lookupCompany = new _JLookup(null, "Company");
								pnlCompany.add(lookupCompany, BorderLayout.WEST);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.setPreferredSize(new Dimension(100, 0));
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											txtCompany.setText(String.format("%s", (String) data[1]));
											lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));
											co_id = (String) data[0];
											company_logo = (String) data[3];
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								txtCompany = new JXTextField(" ");
								pnlCompany.add(txtCompany, BorderLayout.CENTER);
								txtCompany.setHorizontalAlignment(JLabel.LEFT);
								txtCompany.setBounds(165, 10, 300, 22);
								txtCompany.setEditable(false);
							}
						}
						{
							JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
							pnlNorthWestComponent.add(pnlProject);
							{
								lookupProject = new _JLookup(null, "Project", "Please select company.");
								pnlProject.add(lookupProject, BorderLayout.WEST);
								lookupProject.setReturnColumn(0);
								lookupProject.setPreferredSize(new Dimension(100, 0));
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										final Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											System.out.printf("Display lookupBatchNo: %s%n", lookupBatchNo.getValue());
											System.out.printf("Display lookupPhase: %s%n", lookupPhase.getValue());												
											txtProject.setText(String.format("%s", (String) data[1]));
											projcode = (String) data[0];
											lookupPhase.setLookupSQL(SQL_PHASE(projcode));
											lookupBatchNo.setLookupSQL(SQL_Batch());
											btnSave.setEnabled(false);
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								txtProject = new JXTextField(" ");
								pnlProject.add(txtProject, BorderLayout.CENTER);
								txtProject.setHorizontalAlignment(JLabel.LEFT);
								txtProject.setBounds(165, 35, 300, 22);
								txtProject.setEditable(false);
							}
						}

						/*	{
							JPanel pnlParticulars = new JPanel(new BorderLayout(5, 5));
							pnlNorthWestComponent.add(pnlParticulars);
							{
								lookupParticulars = new _JLookup(null, "Particulars");
								pnlParticulars.add(lookupParticulars, BorderLayout.WEST);
								lookupParticulars.setReturnColumn(0);
								lookupParticulars.setPreferredSize(new Dimension(100, 0));
								lookupParticulars.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										final Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											txtParticulars.setText(String.format("%s", (String) data[1]));
											lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));
											btnSave.setEnabled(false);
											btnPreview.setEnabled(true);
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								txtParticulars = new JXTextField(" ");
								pnlParticulars.add(txtParticulars, BorderLayout.CENTER);
								txtParticulars.setHorizontalAlignment(JLabel.LEFT);
								txtParticulars.setBounds(165, 35, 300, 22);
								txtParticulars.setEditable(false);
							}
						}*/

					}
				}
				{
					JPanel pnlNorthEast = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthEast);
					{
						JPanel pnlNorthEastLabel = new JPanel(new GridLayout(2, 1, 5, 5));
						pnlNorthEast.add(pnlNorthEastLabel, BorderLayout.WEST);
						{
							lblPhase = new JLabel("Phase");
							pnlNorthEastLabel.add(lblPhase);
							lblPhase.setHorizontalAlignment(JLabel.LEFT);
						}
						{
							lblBlock = new JLabel("Batch No");
							pnlNorthEastLabel.add(lblBlock);
							lblBlock.setHorizontalAlignment(JLabel.LEFT);
						}
					}
					{
						JPanel pnlNorthEastComponent = new JPanel(new GridLayout(2, 1, 5, 5));
						pnlNorthEast.add(pnlNorthEastComponent, BorderLayout.CENTER);
						{
							JPanel pnlPhase = new JPanel(new BorderLayout(5, 5));
							pnlNorthEastComponent.add(pnlPhase);
							{
								lookupPhase = new _JLookup(null, "Phase", "Please select project.");
								pnlPhase.add(lookupPhase, BorderLayout.WEST);
								lookupPhase.setReturnColumn(0);
								lookupPhase.setPreferredSize(new Dimension(100, 0));
								lookupPhase.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										final Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											txtPhase.setText(String.format("%s", (String) data[1]));
											phase = (String) data[0];
											lookupBatchNo.setLookupSQL(SQL_Batch());
											btnSave.setEnabled(false);
											KEYBOARD_MANAGER.focusNextComponent();
											btnState(false, false, true, false);
										}
									}
								});
							}
							{
								txtPhase = new JXTextField(" ");
								pnlPhase.add(txtPhase, BorderLayout.CENTER);
								txtPhase.setHorizontalAlignment(JLabel.LEFT);
								txtPhase.setEditable(false);
							}
							
						}
						{
							JPanel pnlBlock = new JPanel(new BorderLayout(5, 5));
							pnlNorthEastComponent.add(pnlBlock);
							{
								lookupBatchNo = new _JLookup(null, "Batch No");
								pnlBlock.add(lookupBatchNo, BorderLayout.WEST);
								lookupBatchNo.setReturnColumn(0);
								lookupBatchNo.setLookupSQL(SQL_Batch());
								lookupBatchNo.setPreferredSize(new Dimension(100, 0));
								lookupBatchNo.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										final Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											txtBatchNo.setText(String.format("%s", (String) data[0]));
											batch = (String) data[0];
											btnSave.setEnabled(false);
											displayAccounts();
										}
									}
								});
							}
							{
								txtBatchNo = new JXTextField(" ");
								pnlBlock.add(txtBatchNo, BorderLayout.CENTER);
								txtBatchNo.setHorizontalAlignment(JLabel.LEFT);
								txtBatchNo.setEditable(false);
							}
						}
					}
				}

				{
					pnlWest = new JPanel();
					pnlWest.setLayout(new BorderLayout(5, 5));
					pnlWest.setBorder(lineBorder);
					pnlWest.setPreferredSize(new Dimension(200, 400));// XXX	
					{
						JLabel lblWest = new JLabel("WEST");
						pnlWest.add(lblWest, BorderLayout.CENTER);
						lblWest.setHorizontalAlignment(JLabel.CENTER);
					}
				}
				{
					pnlEast = new JPanel();
					pnlEast.setLayout(new BorderLayout(5, 5));
					pnlEast.setBorder(lineBorder);
					pnlEast.setPreferredSize(new Dimension(200, 400));// XXX
					{
						JLabel lblEast = new JLabel("EAST");
						pnlEast.add(lblEast, BorderLayout.CENTER);
						lblEast.setHorizontalAlignment(JLabel.CENTER);
					}
				}
				{
					pnlCenter = new JPanel();
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					pnlCenter.setLayout(new BorderLayout(5, 5));
					pnlCenter.setBorder(lineBorder);
					{
						scrollOccupancy = new JScrollPane();
						pnlCenter.add(scrollOccupancy, BorderLayout.CENTER);
						scrollOccupancy.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollOccupancy.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

						modelEditRPT = new model_ForEditRealProperty();
						modelEditRPT.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								if(e.getType() == TableModelEvent.DELETE){
									rowheaderOccupancy.setModel(new DefaultListModel());
								}
								if(e.getType() == TableModelEvent.INSERT){
									((DefaultListModel)rowheaderOccupancy.getModel()).addElement(modelEditRPT.getRowCount());
								}
							}
						});

						tblEditRPT = new _JTableMain(modelEditRPT);
						tblEditRPT.addMouseListener(this);
						tblEditRPT.addKeyListener(this);
						tblEditRPT.hideColumns("Batch No");
						tblEditRPT.hideColumns("RPLF No");
						tblEditRPT.hideColumns("Client ID");
						tblEditRPT.setSortable(false);
						scrollOccupancy.setViewportView(tblEditRPT);

						rowheaderOccupancy = tblEditRPT.getRowHeader();
						rowheaderOccupancy.setModel(new DefaultListModel());
						scrollOccupancy.setRowHeaderView(rowheaderOccupancy);
						scrollOccupancy.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());

					}
				}
				{
					pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
					pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX
					{
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
					}
					{
						btnEdit = new JButton("Edit");
						pnlSouth.add(btnEdit);
						btnEdit.setActionCommand("Edit");
						btnEdit.setMnemonic(KeyEvent.VK_E);
						btnEdit.addActionListener(this);

					}
					{
						btnSave = new JButton("Save");
						pnlSouth.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.setMnemonic(KeyEvent.VK_S);
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlSouth.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.setMnemonic(KeyEvent.VK_C);
						btnCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								lookupProject.setText("");
								refreshTO();								
							}
						});
					}
				}
			}


		}

		btnState(false, false, false, false);
		company_logo = sql_getCompanyLogo();
	}

	private void btnState(Boolean sEdit, Boolean sSave, Boolean sPreview, Boolean sCancel){
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void displayAccounts() {

		FncTables.clearTable(modelEditRPT);	
		DefaultListModel listModel = new DefaultListModel();

		String sql = "SELECT false AS \"TAG\" , * FROM view_for_edit_real_property_batch('"+co_id+"', '"+projcode+"', '"+phase+"', '"+batch+"') order by c_client"; 

		pgSelect db = new pgSelect();
		db.select(sql);

		FncSystem.out("Display For Edit Real Property Tax Client!", sql);

		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelEditRPT.addRow(db.getResult()[x]);
				listModel.addElement(modelEditRPT.getRowCount());
			}	
		}		
		tblEditRPT.packAll();
		btnSave.setEnabled(true);
	}


	private void refreshTO() {

		if (lookupProject.getText().equals("")) {
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupBatchNo.setValue("");
			lookupPhase.setValue("");
			txtCompany.setText(" ");
			txtProject.setText(" ");
			modelEditRPT.setRowCount(0);
			btnSave.setEnabled(false);


		}
	} 
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		ArrayList<String> iftrue = new ArrayList<String>();
		String SQL ="";

		if(actionCommand.equals("Edit")){

			btnEdit.setEnabled(false);
			btnSave.setEnabled(true);
		}

		if(actionCommand.equals("Save")){

			ArrayList<String> ifSelected = new ArrayList<String>();
			for (int i = 0; i < modelEditRPT.getRowCount(); i++) {
				Boolean SelectedItem = (Boolean) modelEditRPT.getValueAt(i, 0);

				if(SelectedItem){
					ifSelected.add(modelEditRPT.getValueAt(i, 1).toString());

					if(JOptionPane.showConfirmDialog(getContentPane(),"Are you sure that the amount is correct?", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

						for(int x =0; x < modelEditRPT.getRowCount(); x++){
							
							Boolean isSelected = (Boolean) modelEditRPT.getValueAt(x, 0);
							String batch_no = (String) modelEditRPT.getValueAt(x, 1);
							String rplf_no = (String) modelEditRPT.getValueAt(x, 2);
							String entity_id = (String) modelEditRPT.getValueAt(x, 3);
							BigDecimal rpt_lot = new BigDecimal(String.format("%s", modelEditRPT.getValueAt(x, 8)));
							String rpt_lot_or = (String) modelEditRPT.getValueAt(x, 10);
							BigDecimal rpt_house = new BigDecimal(String.format("%s", modelEditRPT.getValueAt(x, 12)));
							String rpt_house_or = (String) modelEditRPT.getValueAt(x, 14);
							Integer proc_cost_id_lot = (Integer) modelEditRPT.getValueAt(x, 16);
							Integer proc_cost_id_house = (Integer) modelEditRPT.getValueAt(x, 17);

							if(isSelected){

								System.out.printf("RPT LOT: %s\n", rpt_lot);
								System.out.printf("RPT LOT OR: %s\n", rpt_lot_or);
								System.out.printf("RPT HOUSE: %s\n", rpt_house);
								System.out.printf("RPT HOUSE OR: %s\n", rpt_house_or);
								System.out.printf("ENTITY ID: %s\n", entity_id);
								System.out.printf("BATCH NO.: %s\n", batch_no);
								System.out.printf("RPLF NO.: %s\n", rplf_no);
								System.out.printf("proc_cost_id.: %s\n", proc_cost_id_lot);
								System.out.printf("proc_cost_id.: %s\n", proc_cost_id_house);
								

								SQL = "SELECT * FROM sp_save_edit_rpt("+rpt_lot+", '"+rpt_lot_or+"', "+rpt_house+", '"+rpt_house_or+"', '"+entity_id+"', '"+batch_no+"' ,'"+rplf_no+"','"+UserInfo.EmployeeCode+"', "+proc_cost_id_lot+", "+proc_cost_id_house+", '"+co_id+"');";

								FncSystem.out("Display SQL", SQL);

								pgSelect db = new pgSelect();
								db.select(SQL);

							}
						}

						JOptionPane.showMessageDialog(getContentPane(), "Saved.", "Save", JOptionPane.INFORMATION_MESSAGE);

						btnSave.setEnabled(false);
						modelEditRPT.setRowCount(0);
						lookupCompany.setValue(null);
						lookupProject.setValue(null);
						lookupBatchNo.setValue(null);
						lookupPhase.setValue(null);
						txtCompany.setText("");
						txtProject.setText("");
						txtPhase.setText("");
						txtBatchNo.setText("");
						modelEditRPT.setRowCount(0);
					}
				}
			}if(ifSelected.isEmpty()){
				JOptionPane.showMessageDialog(getContentPane(),"Please select a row first! ","Save", JOptionPane.OK_OPTION);
			}

		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblEditRPT)) {

			for(int x = 0; x < modelEditRPT.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelEditRPT.getValueAt(x, 0);
				if (isSelected) {
					btnSave.setEnabled(true);
				}
			}
			if ((e.getClickCount() >= 2)) {
				clickTableColumn();
			}
		}
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
	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F2) {
			clickTableColumn();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) { }

	private void clickTableColumn() {//used
		int column = tblEditRPT.getSelectedColumn();
		int row = tblEditRPT.getSelectedRow();

		if (column == 9) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Color Scheme", getColorScheme(), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();
			if (data != null) {
				modelEditRPT.setValueAt(data[0], row, column);
			}
		}
	}

	private String getColorScheme(){//used

		String sql = "select hse_color as \"Color Scheme\" from rf_marketing_scheme_pricelist group by hse_color order by hse_color";		

		FncSystem.out("Color", sql);

		return sql;

	}

	private static String sql_getCompanyLogo() {

		String a = "";

		String SQL = 
				"select company_logo from mf_company \n" + 
						"where co_id = '"+co_id+"' " ;

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
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

	public static String SQL_Partuculars() {
		String SQL = "SELECT pcostdtl_id as \"Particular Code\", pcostdtl_desc as \"Particular Description\" \n" + 
				"       FROM mf_processing_cost_dl\n" + 
				"       WHERE status_id = 'A'\n" + 
				"       AND   co_id     = '"+ co_id +"'\n" + 
				"       AND proj_id     = '"+ projcode +"'\n" + 
				"       AND pcostdtl_id = '215';";

		FncSystem.out("Particulars code here!", SQL);

		return SQL;
	}

	public static String SQL_Batch() {
		String SQL = "select c_batch AS \"Batch No.\" from view_for_edit_real_property('"+co_id+"','"+projcode+"','"+phase+"') \n" + 
				"                    group by c_batch \n" + 
				"                    order by c_batch desc;";

		FncSystem.out("RPLF Checker!", SQL);

		return SQL;
	}
}