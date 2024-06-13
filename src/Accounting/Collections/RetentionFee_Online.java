package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import Renderer.DateRenderer;
import System.pnlPassword;
import components.JTBorderFactory;
import components._JFormattedTextField;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelByrPmtPosting_CRB;
import tablemodel.modelConnectionList;
import tablemodel.modelRetFeeOnline;
import tablemodel.model_retention_details;

public class RetentionFee_Online extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 8424384353558388288L;
	private static String title = "Retention Fee (Online)";
	Border line = BorderFactory.createLineBorder(Color.GRAY);
	Dimension frameSize = new Dimension(700, 500);

	private JPanel pnlNorth;
	private JPanel pnlNorthLabel;
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblActualDate;

	private JPanel pnlNorthComponents;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private _JLookup lookupProject;
	private _JXTextField txtProject;
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;
	private _JDateChooser dteActual;

	private JPanel pnlCenter;
	private modelRetFeeOnline modelREtFeeOL;
	private _JTableMain tblScrollRetFeeOL;
	private JScrollPane scrollRetGFeeOL;
	private JList rowHeaderScrollRetFeeOL;
	private JButton btnAddRow;
	private JButton btnRemoveRow;

	private JPanel pnlSouth;
	private JButton btnNew;
	private JButton btnSearch;
	private JButton btnSave;
	private JButton btnCancel;
	private _JLookup lookupparticular;
	private _JXTextField txtParticular;

	public RetentionFee_Online() {
		super(title, true, true, false, true);
		initGUI();
	}

	public RetentionFee_Online(String title) {
		super(title, true, true, false, true);
		initGUI();
	}

	public RetentionFee_Online(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);

		JXPanel panMain = new JXPanel(new BorderLayout(3, 3));
		getContentPane().add(panMain, BorderLayout.CENTER);
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			panMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setPreferredSize(new Dimension(0, 180));
			pnlNorth.setBorder(JTBorderFactory.createTitleBorder(""));
			{
				pnlNorthLabel = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
				{
					lblCompany = new JLabel("Company");
					pnlNorthLabel.add(lblCompany);
				}
				{
					lblProject = new JLabel("Project");
					pnlNorthLabel.add(lblProject);
				}
				{
					lblPhase = new JLabel("Phase");
					pnlNorthLabel.add(lblPhase);
				}
				{
					JLabel lblparticular = new JLabel("Particular");
					pnlNorthLabel.add(lblparticular);
				}
				{
					lblActualDate = new JLabel("Actual Date");
					pnlNorthLabel.add(lblActualDate);
				}
			}
			{
				pnlNorthComponents = new JPanel(new GridLayout(5, 1, 5, 5));
				pnlNorth.add(pnlNorthComponents);
				{
					JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
					pnlNorthComponents.add(pnlCompany);
					{
						lookupCompany = new _JLookup(null, "Company", 0);
						pnlCompany.add(lookupCompany, BorderLayout.WEST);
						lookupCompany.setPreferredSize(new Dimension(100, 0));
						lookupCompany.setLookupSQL(sqlCompany());
						lookupCompany.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();

								if (data != null) {
									String company_name = (String) data[1];
									txtCompany.setText(company_name);
								}
							}
						});
					}
					{
						txtCompany = new _JXTextField();
						pnlCompany.add(txtCompany, BorderLayout.CENTER);
					}
				}
				{
					JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
					pnlNorthComponents.add(pnlProject);
					{
						lookupProject = new _JLookup(null, "Project", 0);
						pnlProject.add(lookupProject, BorderLayout.WEST);
						lookupProject.setPreferredSize(new Dimension(100, 0));
						lookupProject.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();

								if (data != null) {
									String proj_name = (String) data[1];
									txtProject.setText(proj_name);
								}
							}
						});
						lookupProject.addFocusListener(new FocusListener() {

							@Override
							public void focusLost(FocusEvent e) {
								// TODO Auto-generated method stub

							}

							@Override
							public void focusGained(FocusEvent e) {
								lookupProject.setLookupSQL(sqlProject(lookupCompany.getValue()));

							}
						});
					}
					{
						txtProject = new _JXTextField();
						pnlProject.add(txtProject, BorderLayout.CENTER);
					}
				}
				{
					JPanel pnlPhase = new JPanel(new BorderLayout(3, 3));
					pnlNorthComponents.add(pnlPhase);
					{
						lookupPhase = new _JLookup(null, "Phase", 1);
						pnlPhase.add(lookupPhase, BorderLayout.WEST);
						lookupPhase.setPreferredSize(new Dimension(100, 0));
						lookupPhase.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();

								if (data != null) {
									String phase = (String) data[2];
									txtPhase.setText(phase);
								}
							}
						});
						lookupPhase.addFocusListener(new FocusListener() {

							@Override
							public void focusLost(FocusEvent e) {

							}

							@Override
							public void focusGained(FocusEvent e) {
								lookupPhase.setLookupSQL(sqlPhase(lookupProject.getValue()));
							}
						});
					}
					{
						txtPhase = new _JXTextField();
						pnlPhase.add(txtPhase, BorderLayout.CENTER);
					}
				}
				{
					JPanel pnlparticular = new JPanel(new BorderLayout(5, 5));
					pnlNorthComponents.add(pnlparticular);
					{
						lookupparticular = new _JLookup(null, "Particular", 0);
						pnlparticular.add(lookupparticular, BorderLayout.WEST);
						lookupparticular.setPreferredSize(new Dimension(100, 0));
						lookupparticular.addLookupListener(new LookupListener() {
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();

								if (data != null) {
									String particular = (String) data[2];
									txtParticular.setText(particular);
								}
							}
						});
						lookupparticular.addFocusListener(new FocusListener() {
							public void focusLost(FocusEvent e) {

							}

							@Override
							public void focusGained(FocusEvent e) {
								// TODO Auto-generated method stub
								lookupparticular.setLookupSQL(sqlParticular());
							}
						});
					}
					{
						txtParticular = new _JXTextField();
						pnlparticular.add(txtParticular, BorderLayout.CENTER);
					}
				}
				{
					JPanel pnlDate = new JPanel(new BorderLayout(3, 3));
					pnlNorthComponents.add(pnlDate);
					pnlDate.setPreferredSize(new Dimension(150, 0));
					{
						dteActual = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
						pnlDate.add(dteActual, BorderLayout.WEST);
						dteActual.setPreferredSize(new Dimension(150, 0));
					}
				}
			}
		}
		{
			pnlCenter = new JPanel(new BorderLayout(3, 3));
			panMain.add(pnlCenter, BorderLayout.CENTER);
			{
				scrollRetGFeeOL = new JScrollPane();
				pnlCenter.add(scrollRetGFeeOL, BorderLayout.CENTER);
				scrollRetGFeeOL.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				{
					modelREtFeeOL = new modelRetFeeOnline();
					tblScrollRetFeeOL = new _JTableMain(modelREtFeeOL);
					scrollRetGFeeOL.setViewportView(tblScrollRetFeeOL);
					tblScrollRetFeeOL.setSortable(false);
					tblScrollRetFeeOL.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq No.");
					modelREtFeeOL.addTableModelListener(new TableModelListener() {

						@Override
						public void tableChanged(TableModelEvent e) {
							if (modelREtFeeOL.getRowCount() > 0) {
								int selected_column = tblScrollRetFeeOL.getSelectedColumn();

								if (selected_column == 2) {
									tblScrollRetFeeOL.packAll();
								}
							}
						}
					});
					tblScrollRetFeeOL.addMouseListener(new MouseListener() {

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseClicked(MouseEvent e) {

							if ((e.getClickCount() >= 2)) {
								lookupClients();
							}
						}
					});
				}
				{
					rowHeaderScrollRetFeeOL = tblScrollRetFeeOL.getRowHeader();
					rowHeaderScrollRetFeeOL.setModel(new DefaultListModel());
					scrollRetGFeeOL.setRowHeaderView(rowHeaderScrollRetFeeOL);
					scrollRetGFeeOL.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					scrollRetGFeeOL.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, displayTableNavigation());
				}
			}
		}
		{
			pnlSouth = new JPanel(new GridLayout(1, 4, 5, 5));
			panMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setPreferredSize(new Dimension(0, 50));
			{
				btnNew = new JButton("New");
				pnlSouth.add(btnNew);
				btnNew.setActionCommand("New");
				btnNew.addActionListener(this);
			}
			{
				btnSearch = new JButton("Search");
				pnlSouth.add(btnSearch);
				btnSearch.setActionCommand("Search");
				btnSearch.addActionListener(this);
			}
			{
				btnSave = new JButton("Save");
				pnlSouth.add(btnSave);
				btnSave.setActionCommand("Save");
				btnSave.addActionListener(this);
			}
			{
				btnCancel = new JButton("Cancel");
				pnlSouth.add(btnCancel);
				btnCancel.setActionCommand("Cancel");
				btnCancel.addActionListener(this);
			}
		}
		initializeComponents();
	}

	private void initializeComponents() {
		lookupCompany.setValue(null);
		txtCompany.setText("");
		lookupCompany.setEditable(false);
		lookupProject.setValue(null);
		txtProject.setText("");
		lookupProject.setEditable(false);
		lookupPhase.setValue(null);
		txtPhase.setText("");
		lookupPhase.setEditable(false);
		dteActual.setDate(null);
		dteActual.setEditable(false);
		dteActual.getCalendarButton().setEnabled(false);
		modelREtFeeOL.clear();
		rowHeaderScrollRetFeeOL.setModel(new DefaultListModel());
		lookupparticular.setEditable(false);
		lookupparticular.setValue(null);
		txtParticular.setText("");

		btnEnabled(true, true, false, false, false, false);
	}

	private void newRetFeeOL() {
		lookupCompany.setEditable(true);
		lookupCompany.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		lookupProject.setEditable(true);
		lookupPhase.setEditable(true);
		dteActual.setEditable(true);
		dteActual.getCalendarButton().setEnabled(true);
		modelREtFeeOL.clear();
		rowHeaderScrollRetFeeOL.setModel(new DefaultListModel());
		lookupparticular.setEditable(true);
		btnEnabled(false, false, true, true, true, true);
	}

	private void cancelRetFeeOL() {
		initializeComponents();
	}

	private void btnEnabled(Boolean New, Boolean Search, Boolean Save, Boolean Cancel, Boolean AddRow,
			Boolean RemoveRow) {
		btnNew.setEnabled(New);
		btnSearch.setEnabled(Search);
		btnSave.setEnabled(Save);
		btnCancel.setEnabled(Cancel);
		btnAddRow.setEnabled(AddRow);
		btnRemoveRow.setEnabled(RemoveRow);
	}

	private JPanel displayTableNavigation() {
		btnAddRow = new JButton(
				new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Plus2-Math-icon.png")));
		btnAddRow.setActionCommand("Add Row");
		btnAddRow.setToolTipText("Add Row");
		btnAddRow.setEnabled(false);
		btnAddRow.addActionListener(this);

		btnRemoveRow = new JButton(
				new ImageIcon(this.getClass().getClassLoader().getResource("Images/Science-Minus2-Math-icon.png")));
		btnRemoveRow.setActionCommand("Remove Row");
		btnRemoveRow.setToolTipText("Remove Row");
		btnRemoveRow.setEnabled(false);
		btnRemoveRow.addActionListener(this);

		JPanel pnl = new JPanel(new GridLayout(1, 2));
		pnl.add(btnAddRow);
		pnl.add(btnRemoveRow);

		return pnl;
	}

	private void lookupClients() {
		int selected_column = tblScrollRetFeeOL.getSelectedColumn();
		int selectec_row = tblScrollRetFeeOL.getSelectedRow();

		if (selected_column == 0) {
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Client",
					sqlClients(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), false, ""),
					false);
			// _JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Client",
			// sqlClients(lookupCompany.getValue(), lookupProject.getValue(),
			// lookupPhase.getValue()), false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);

			Object[] data = dlg.getReturnDataSet();

			if (data != null) {
				String entity_name = (String) data[0];
				String unit = (String) data[1];
				BigDecimal amount = (BigDecimal) data[2];
				BigDecimal payable_epeb = (BigDecimal) data[3];
				BigDecimal balance_epeb = (BigDecimal) data[4];
				BigDecimal payable_ret1 = (BigDecimal) data[5];
				BigDecimal balance_ret1 = (BigDecimal) data[6];
				BigDecimal payable_ret2 = (BigDecimal) data[7];
				BigDecimal balance_ret2 = (BigDecimal) data[8];
				BigDecimal total_ret = (BigDecimal) data[9];
				String entity_id = (String) data[10];
				String proj_id = (String) data[11];
				String pbl_id = (String) data[12];
				Integer seq_no = (Integer) data[13];

				modelREtFeeOL.setValueAt(entity_name, selectec_row, 0);
				modelREtFeeOL.setValueAt(unit, selectec_row, 1);
				modelREtFeeOL.setValueAt(amount, selectec_row, 2);
				modelREtFeeOL.setValueAt(payable_epeb, selectec_row, 3);
				modelREtFeeOL.setValueAt(balance_epeb, selectec_row, 4);
				modelREtFeeOL.setValueAt(payable_ret1, selectec_row, 5);
				modelREtFeeOL.setValueAt(balance_ret1, selectec_row, 6);
				modelREtFeeOL.setValueAt(payable_ret2, selectec_row, 7);
				modelREtFeeOL.setValueAt(balance_ret2, selectec_row, 8);
				modelREtFeeOL.setValueAt(total_ret, selectec_row, 9);
				modelREtFeeOL.setValueAt(entity_id, selectec_row, 10);
				modelREtFeeOL.setValueAt(proj_id, selectec_row, 11);
				modelREtFeeOL.setValueAt(pbl_id, selectec_row, 12);
				modelREtFeeOL.setValueAt(seq_no, selectec_row, 13);
				tblScrollRetFeeOL.packAll();
			}
		}
	}

	private void displaySearch() {
		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Client", sqlSearch(), false);
		// _JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Client",
		// sqlClients(lookupCompany.getValue(), lookupProject.getValue(),
		// lookupPhase.getValue()), false);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[] data = dlg.getReturnDataSet();

		String or_no = "";
		if (data != null) {
			or_no = (String) data[0];
			displayORClients(modelREtFeeOL, rowHeaderScrollRetFeeOL, or_no);
		}
	}

	private void displayORClients(modelRetFeeOnline model, JList rowHeader, String or_no) {
		if (model != null && rowHeader != null) {
			model.clear();
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_ret_fee_online_clients('" + lookupCompany.getValue() + "','"
					+ lookupProject.getValue() + "','" + lookupPhase.getValue() + "',true,'" + or_no + "')";
			db.select(SQL);

			if (db.isNotNull()) {
				for (int x = 0; x < db.getRowCount(); x++) {
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
			}
		}
		tblScrollRetFeeOL.packAll();
	}

	private void addRow() {
		modelREtFeeOL.addRow(
				new Object[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, });
		((DefaultListModel) rowHeaderScrollRetFeeOL.getModel()).addElement(modelREtFeeOL.getRowCount());
	}

	private void removeRow() {
		modelREtFeeOL.removeRow(tblScrollRetFeeOL.getSelectedRow());
		rowHeaderScrollRetFeeOL.setModel(new DefaultListModel());
		for (int x = 0; x < modelREtFeeOL.getRowCount(); x++) {

			((DefaultListModel) rowHeaderScrollRetFeeOL.getModel()).addElement(x + 1);
		}
	}

	private String sqlCompany() {
		return "SELECT co_id as \"ID\", \n" + "company_name as \"Company Name\", \n" + "company_alias as \"Alias\" \n"
				+ "FROM mf_company;";
	}

	private String sqlProject(String co_id) {
		return "SELECT \n" + "proj_id as \"ID\", \n" + "proj_name as \"Project\", \n" + "proj_alias as \"Alias\"\n"
				+ "FROM mf_project\n" + "WHERE co_id = '" + co_id + "';";
	}

	private String sqlPhase(String proj_id) {

		return "SELECT sub_proj_id as \"ID\", \n" + "phase as \"Phase\",\n" + "sub_proj_name as \"Phase Name\",\n"
				+ "sub_proj_alias as \"Alias\"\n" + "FROM mf_sub_project\n" + "WHERE proj_id = '" + proj_id + "' AND status_id = 'A'";//ADDED STATUS ID BY LESTER DCRF 2718
	}

	private String sqlParticular() {
		return "select pay_part_id, particulars, partdesc\n" + "	from mf_pay_particular \n"
				+ "	where particulars ~* 'LNREL' or pay_part_id in ('218', '246', '247')\n" + "	order by particulars";
	}

	private String sqlClients(String co_id, String proj_id, String phase, Boolean search, String or_no) {
		String SQL = "SELECT * FROM view_ret_fee_online_clients('" + co_id + "', '" + proj_id + "', '" + phase + "', "
				+ search + ", '" + or_no + "')";

		return SQL;
	}

	private String sqlClients(String co_id, String proj_id, String phase) {
		String SQL = "SELECT get_client_name(a.entity_id) as \"Name\", \n"
				+ "							  get_merge_unit_desc_v3(a.entity_id, a.projcode, a.pbl_id, a.seq_no) as \"Unit\",\n"
				+ "							  a.entity_id as \"Entity ID\", a.projcode as \"Proj. ID\", a.pbl_id as \"PBL ID\", a.seq_no as \"Seq. No\"\n"
				+ "							  FROM rf_sold_unit a\n"
				+ "							  LEFT JOIN mf_project b on b.proj_id = a.projcode\n"
				+ "							  LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id \n"
				+ "							  where a.currentstatus != '02'\n"
				+ "							  AND a.status_id = 'A'\n"
				+ "							  AND CASE WHEN NULLIF('" + co_id
				+ "', 'null') is null THEN TRUE ELSE b.co_id = '" + co_id + "' end\n"
				+ "							  AND CASE WHEN NULLIF('" + proj_id
				+ "', 'null') IS NULL THEN TRUE ELSE a.projcode = '" + proj_id + "' end\n"
				+ "							  and case when nullif('" + phase
				+ "', 'null') IS NULL THEN TRUE ELSE c.phase = '" + phase + "' end\n"
				+ "							  and not exists (SELECT *\n"
				+ "											  from rf_payments \n"
				+ "											  where entity_id = a.entity_id \n"
				+ "											  and proj_id = a.projcode \n"
				+ "											  and pbl_id = a.pbl_id \n"
				+ "											  and seq_no = a.seq_no \n"
				+ "											  and pay_part_id = '251'\n"
				+ "											  AND status_id = 'A')\n";

		return SQL;
	}

	private String sqlSearch() {
		String SQL = "SELECT or_no as \"OR No.\", trans_date::DATE as \"Trans Date\"\n" + "from rf_payments \n"
				+ "WHERE pay_part_id IN ('217','218','087','251','252','253','254','256','257','258','272','273','246','247','274') \n"
				+ "AND remarks ~*'Ret Fee Online'\n" + "AND status_id = 'A'\n" + "GROUP by or_no, trans_date::dATE\n"
				+ "ORDER BY trans_date::dATE desc;";
		return SQL;
	}

	//MODIFIED BY MONIQUE TO REPLACE OR TO SI(SALES INVOICE) DTD 2022-07-19 WITH DCRF#2121
	/*private String get_OR_NUM() {
		String or_no = "";
		String SQL = "SELECT get_receipt_no_to_issue('" + lookupCompany.getValue() + "', '01', '" + UserInfo.branch_id
				+ "', '" + UserInfo.EmployeeCode + "')";
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			or_no = (String) db.getResult()[0][0];
		} else {
			or_no = null;
		}
		return or_no;
	}*/
	
	
	private String get_SI_NUM() {
		String si_no = "";
		String SQL = "SELECT get_receipt_no_to_issue('" + lookupCompany.getValue() + "', '307', '" + UserInfo.branch_id
				+ "', '" + UserInfo.EmployeeCode + "')";
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			si_no = (String) db.getResult()[0][0];
		} else {
			si_no = null;
		}
		return si_no;
	}
	
	private String getARNo() {
		String ar_no = "";
		String SQL = "SELECT get_receipt_no_to_issue('" + lookupCompany.getValue() + "', '03', '" + UserInfo.branch_id
				+ "', '" + UserInfo.EmployeeCode + "')";
		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			ar_no = (String) db.getResult()[0][0];
		} else {
			ar_no = null;
		}
		return ar_no;
	}

	private String saveRetFeeOnline() {
		// Edited by Erick 2022-05-20 -- To generate client sequence no for every client
		// with the save or_no.

		/*
		 * ArrayList<String> listEntityID = new ArrayList<String>(); ArrayList<String>
		 * listProjID = new ArrayList<String>(); ArrayList<String> listPBL = new
		 * ArrayList<String>(); ArrayList<Integer> listSeq = new ArrayList<Integer>();
		 * ArrayList<BigDecimal> listAmount = new ArrayList<BigDecimal>();
		 */

		String entity_id = "";
		String proj_id = "";
		String pbl_id = "";
		Integer seq_no = null;
		BigDecimal amount = new BigDecimal("0.00");
		//String or_no = ""; Commented by Monique to replace or to si (sales invoice) dtd 2022-07-19 with DCRF #2121
		String si_no = "";
		String client_seqno = "";
		//or_no = get_OR_NUM();
		si_no = get_SI_NUM();
		String ar_no = getARNo();
		

		for (int x = 0; x < modelREtFeeOL.getRowCount(); x++) {
			String name = (String) modelREtFeeOL.getValueAt(x, 0);
			System.out.println("x = " + x);

			if (name != null) {
				amount = new BigDecimal("0.00");
				System.out.printf("valuer of amount: %s", modelREtFeeOL.getValueAt(x, 2));

				try {
					amount = (BigDecimal) modelREtFeeOL.getValueAt(x, 2);
				} catch (ClassCastException e) {
					// amount = new BigDecimal((Long) model.getValueAt(x, 2));

					if (modelREtFeeOL.getValueAt(x, 2) instanceof Double) {
						amount = BigDecimal.valueOf((Double) modelREtFeeOL.getValueAt(x, 2));
					}

					if (modelREtFeeOL.getValueAt(x, 2) instanceof Long) {
						amount = BigDecimal.valueOf((Long) modelREtFeeOL.getValueAt(x, 2));
					}
				}

				entity_id = (String) modelREtFeeOL.getValueAt(x, 10);
				proj_id = (String) modelREtFeeOL.getValueAt(x, 11);
				pbl_id = (String) modelREtFeeOL.getValueAt(x, 12);
				seq_no = (Integer) modelREtFeeOL.getValueAt(x, 13);

				// Commented by Erick 2022-05-20
				/*
				 * listProjID.add(String.format("'%S'", proj_id));
				 * listPBL.add(String.format("'%s'", pbl_id)); listSeq.add(seq_no);
				 * listAmount.add(amount); listEntityID.add(String.format("'%s'", entity_id));
				 */
			}
			// or_no = get_OR_NUM();
			pgSelect db = new pgSelect();
			// String SQL = "SELECT * from
			// sp_post_ret_fee_online('"+lookupCompany.getValue()+"',ARRAY["+entity_id+"]::VARCHAR[],ARRAY["+proj_id+"]::VARCHAR[],ARRAY["+pbl_id+"]::VARCHAR[],
			// ARRAY["+seq_no+"]::INT[], ARRAY["+amount+"]::NUMERIC[],
			// '"+dteActual.getDate()+"'::TIMESTAMP WITHOUT TIME
			// ZONE,'"+UserInfo.Branch+"','"+UserInfo.EmployeeCode+"')";
			String SQL = "SELECT * from sp_post_ret_fee_online_v3('" + lookupCompany.getValue() + "','" + entity_id
					+ "','" + proj_id + "','" + pbl_id + "', " + seq_no + ", " + amount + ", '" + dteActual.getDate()
					+ "'::TIMESTAMP WITHOUT TIME ZONE,'" + UserInfo.Branch + "','" + UserInfo.EmployeeCode + "', '"
					+ ar_no + "', '" + lookupparticular.getValue() + "')";

			db.select(SQL, "Save", true);

			FncSystem.out("Display SQL for saveRetFeeOnline", SQL);

			if (db.isNotNull()) {
				ar_no = (String) db.getResult()[0][0];
				client_seqno = (String) db.getResult()[0][1];
			}
		}

		// Commented by Erick 2022-05-20
		/*
		 * String entity_id = listEntityID.toString().replaceAll("\\[|\\]", ""); String
		 * proj_id = listProjID.toString().replaceAll("\\[|\\]", ""); String pbl_id =
		 * listPBL.toString().replaceAll("\\[|\\]", ""); String seq_no =
		 * listSeq.toString().replaceAll("\\[|\\]", ""); String amount =
		 * listAmount.toString().replaceAll("\\[|\\]", "");
		 */

		/*
		 * String or_no = get_OR_NUM(); String client_seqno = ""; pgSelect db = new
		 * pgSelect(); //String SQL =
		 * "SELECT * from sp_post_ret_fee_online('"+lookupCompany.getValue()+"',ARRAY["+
		 * entity_id+"]::VARCHAR[],ARRAY["+proj_id+"]::VARCHAR[],ARRAY["+
		 * pbl_id+"]::VARCHAR[], ARRAY["+seq_no+"]::INT[], ARRAY["
		 * +amount+"]::NUMERIC[], '"+dteActual.getDate()
		 * +"'::TIMESTAMP WITHOUT TIME ZONE,'"+UserInfo.Branch+"','"+UserInfo.
		 * EmployeeCode+"')"; String SQL =
		 * "SELECT * from sp_post_ret_fee_online('"+lookupCompany.getValue()+"','"+
		 * entity_id+"','"+proj_id+"','"+pbl_id+"', "+seq_no+", "+amount+", '"+dteActual
		 * .getDate()+"'::TIMESTAMP WITHOUT TIME ZONE,'"+UserInfo.Branch+"','"+UserInfo.
		 * EmployeeCode+"', '"+or_no+"')";
		 * 
		 * db.select(SQL, "Save", true);
		 * 
		 * FncSystem.out("Display SQL for saveRetFeeOnline", SQL);
		 * 
		 * if(db.isNotNull()){ or_no = (String) db.getResult()[0][0]; client_seqno =
		 * (String) db.getResult()[0][1]; }
		 */
		/* && client_seqno != null */
		System.out.println("");
		System.out.println("client_seqno = " + client_seqno);
		//System.out.println("or_no = " + or_no);
		System.out.println("si_no = " + si_no);

		//if (or_no != null && client_seqno != null) {
		
		if (si_no != null && client_seqno != null) {
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("client_seqno", client_seqno);
			//mapParameters.put("or_no", or_no);
			mapParameters.put("si_no", si_no);
			// mapParameters.put("credit_ar_no", "");
			mapParameters.put("credit_ar_no", null);
			mapParameters.put("recpt_type", "OR No.");
			mapParameters.put("credited_amount", null);
			mapParameters.put("prepared_by", UserInfo.Alias.toUpperCase());
			/*String co_id = FncGlobal.GetString("select co_id from rf_pay_header a \n" + 
					"where client_seqno = '"+client_seqno+"'");*/
			System.out.println("mapParameters: " + mapParameters);
			
			FncReport.generateReport("/Reports/rpt_RetFeeOL_CDC.jasper", "Sales Invoice",
					String.format("SI No.: %s", si_no), mapParameters);
			
			/*if(co_id.equals("01")) {
				FncReport.generateReport("/Reports/rptSalesInvoice_VDC.jasper", "Sales Invoice", String.format("SI No.: %s", si_no), mapParameters);
			}else if(co_id.equals("02")) {
				FncReport.generateReport("/Reports/rptSalesInvoice_CDC.jasper", "Sales Invoice", String.format("SI No.: %s", si_no), mapParameters);
			}*/
		}

		return /*or_no*/ si_no;
	}

	private Boolean toSave() {
		if (dteActual.getDate() == null || txtParticular.getText().equals("")) {
			if (txtParticular.getText().equals("")) {
				JOptionPane.showMessageDialog(RetentionFee_Online.this, String.format("Please select Particular."),
						"Error", JOptionPane.INFORMATION_MESSAGE);
			}
			if (dteActual.getDate() == null) {
				JOptionPane.showMessageDialog(RetentionFee_Online.this, "Please input actual date", "Save",
						JOptionPane.WARNING_MESSAGE);
			}
			return false;
		}

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {// XXX Action
		String action = e.getActionCommand();

		if (action.equals("New")) {
			newRetFeeOL();
		}
		if (action.equals("Search")) {
			displaySearch();
		}

		if (action.equals("Save")) {
			System.out.println("Save");
			if (toSave()) {
				if (JOptionPane.showConfirmDialog(getContentPane(), "Are the entries correct?", "Save",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					//MODIFIED BY MONIQUE TO REPLACE OR TO SI(SALES INVOICE) DTD 2022-07-19 WITH DCRF#2121
					/*String or_no = saveRetFeeOnline();
					if (or_no != null) {
						JOptionPane.showMessageDialog(RetentionFee_Online.this,
								String.format("Retention saved with OR NO: %s", or_no), "Save",
								JOptionPane.INFORMATION_MESSAGE);
						cancelRetFeeOL();*/
						
						String si_no = saveRetFeeOnline();
						if (si_no != null) {
							JOptionPane.showMessageDialog(RetentionFee_Online.this,
									String.format("Retention saved with SI NO: %s", si_no), "Save",
									JOptionPane.INFORMATION_MESSAGE);
							cancelRetFeeOL();
					}
				}
			}
		}

		if (action.equals("Cancel")) {
			cancelRetFeeOL();
		}
		if (action.equals("Add Row")) {
			addRow();
		}
		if (action.equals("Remove Row")) {
			if (tblScrollRetFeeOL.getSelectedRows().length == 1) {
				removeRow();
			} else {
				JOptionPane.showMessageDialog(RetentionFee_Online.this, "Please select row to remove", "Remove",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
