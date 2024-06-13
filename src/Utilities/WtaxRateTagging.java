package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.toedter.calendar.JTextFieldDateEditor;

import tablemodel.model_wtax_waiver_tagging;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;

public class WtaxRateTagging extends _JInternalFrame implements ActionListener, _GUI, MouseListener {

	private static final long serialVersionUID = -6517018477299344044L;

	static String title = "WTax Rate Waiver Tagging";
	Dimension frameSize = new Dimension(800, 550);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;

	private JScrollPane scrollCenter;
	private _JTableMain tblWaiver;
	private model_wtax_waiver_tagging modelWaiver;
	private JList rowheaderAccounts;

	private JButton btnSave;
	private JPanel pnlComp_a3;
	private JCheckBox chkSubmitted;
	private JPanel pnlDept_x;

	String div_id = "";
	Boolean submitted = false;

	private JButton btnRemove;
	private JPanel pnlDate;
	private JLabel lblDate;
	private _JDateChooser dteRefDate;
	private JButton btnOK;

	private _JLookup txtCoID;
	private JTextField txtCo;

	public WtaxRateTagging() {
		super(title, true, true, true, true);
		initGUI();
	}

	public WtaxRateTagging(String title) {
		super(title);
		initGUI();
	}

	public WtaxRateTagging(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(930, 550));
		this.setMinimumSize(frameSize);
		this.setLayout(new BorderLayout(5, 5));
		this.setBounds(0, 0, 930, 550);
		{
			JPanel panMain = new JPanel();
			this.add(panMain, BorderLayout.CENTER);
			panMain.setLayout(new BorderLayout(3, 3));
			panMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 3));
			{
				{
					JPanel panPage = new JPanel(new GridLayout(1, 2, 5, 5));
					panMain.add(panPage, BorderLayout.PAGE_START);
					panPage.setPreferredSize(new Dimension(0, 60));
					panPage.setBorder(JTBorderFactory.createTitleBorder("Filters",
							FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						{
							JPanel panCompany = new JPanel(new BorderLayout(5, 5));
							panPage.add(panCompany);
							{
								{
									JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5));
									panCompany.add(panLabel, BorderLayout.LINE_START);
									panLabel.setPreferredSize(new Dimension(150, 0));
									{
										{
											JLabel label = new JLabel("Company");
											panLabel.add(label, BorderLayout.LINE_START);
											label.setHorizontalAlignment(JLabel.LEADING);
										}
										{
											txtCoID = new _JLookup("");
											panLabel.add(txtCoID);
											txtCoID.setReturnColumn(0);
											txtCoID.setLookupSQL(lookupMethods.getCompany());
											txtCoID.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent event) {
													Object[] data = ((_JLookup) event.getSource()).getDataSet();

													if (data != null) {
														txtCoID.setValue(data[0].toString());
														txtCo.setText(data[1].toString());
													}

													displayTaggedWaiver();
												}
											});
											txtCoID.setValue("02");
										}
									}
								}
								{
									txtCo = new JTextField("");
									panCompany.add(txtCo, BorderLayout.CENTER);
									txtCo.setHorizontalAlignment(JTextField.CENTER);
									txtCo.setEditable(false);
									txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");
								}
							}
						}
						{
							chkSubmitted = new JCheckBox("Show Tagged Waivers");
							panPage.add(chkSubmitted);
							chkSubmitted.setEnabled(true);
							chkSubmitted.setHorizontalAlignment(JCheckBox.TRAILING);
							chkSubmitted.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent arg0) {
									displayTaggedWaiver();

									if (chkSubmitted.isSelected() == true) {
										submitted = true;
										enableButton(false, true);
										displayTaggedWaiver();
									} else {
										submitted = false;
										enableButton(true, false);
										displayTaggedWaiver();
									}
								}
							});
						}
					}
				}
				{
					scrollCenter = new JScrollPane();
					panMain.add(scrollCenter, BorderLayout.CENTER);
					scrollCenter.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollCenter.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblWaiver.clearSelection();
						}
					});
					{
						{
							modelWaiver = new model_wtax_waiver_tagging();
							modelWaiver.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									if (e.getType() == TableModelEvent.DELETE) {
										rowheaderAccounts.setModel(new DefaultListModel());
										scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables
												.getRowHeader_Footer(Integer.toString(tblWaiver.getRowCount())));
									}
									if (e.getType() == TableModelEvent.INSERT) {
										((DefaultListModel) rowheaderAccounts.getModel())
										.addElement(modelWaiver.getRowCount());
									}
								}
							});

							tblWaiver = new _JTableMain(modelWaiver);
							scrollCenter.setViewportView(tblWaiver);
							tblWaiver.setSortable(false);
							tblWaiver.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									if (!e.getValueIsAdjusting()) {// XXX tblJVDetail

									}
								}
							});
							tblWaiver.addMouseListener(this);
						}
						{
							rowheaderAccounts = tblWaiver.getRowHeader();
							rowheaderAccounts.setModel(new DefaultListModel());
							scrollCenter.setRowHeaderView(rowheaderAccounts);
							scrollCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());
						}
					}
				}
			}
			{
				JPanel panEnd = new JPanel(new GridLayout(1, 5, 5, 5));
				panMain.add(panEnd, BorderLayout.SOUTH);
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						JButton btnRefresh = new JButton("Refresh");
						panEnd.add(btnRefresh);
						btnRefresh.addActionListener(this);
						btnRefresh.setFont(FncGlobal.font12);
					}
					{
						panEnd.add(new JLabel(""));
						panEnd.add(new JLabel(""));
					}
					{
						btnSave = new JButton("Submit Tax Waiver");
						panEnd.add(btnSave);
						btnSave.setActionCommand("In");
						btnSave.addActionListener(this);
						btnSave.setFont(FncGlobal.font12);
					}
					{
						btnRemove = new JButton("Remove Tax Waiver");
						panEnd.add(btnRemove);
						btnRemove.setActionCommand("Out");
						btnRemove.addActionListener(this);
						btnSave.setFont(FncGlobal.font12);
					}
				}
			}
		}

		{
			pnlDate = new JPanel();
			pnlDate.setLayout(null);
			pnlDate.setPreferredSize(new java.awt.Dimension(265, 80));
			{
				{
					lblDate = new JLabel();
					pnlDate.add(lblDate);
					lblDate.setBounds(5, 15, 160, 20);
					lblDate.setText("Date Submitted :");
				}
				{
					dteRefDate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
					pnlDate.add(dteRefDate);
					dteRefDate.setBounds(130, 15, 125, 21);
					dteRefDate.setDate(null);
					dteRefDate.setEnabled(true);
					dteRefDate.setDateFormatString("yyyy-MM-dd");
					((JTextFieldDateEditor) dteRefDate.getDateEditor()).setEditable(false);
					dteRefDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
					dteRefDate.addPropertyChangeListener(new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent e) {
						}
					});
				}
				{
					btnOK = new JButton();
					pnlDate.add(btnOK);
					btnOK.setBounds(95, 58, 69, 22);
					btnOK.setText("OK");
					btnOK.setFocusable(false);
					btnOK.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Window optionPaneWindow = SwingUtilities.getWindowAncestor(pnlDate);
							optionPaneWindow.dispose();
						}
					});
				}
			}

		}

		displayTaggedWaiver();
		enableButton(true, submitted);
	}

	private void displayTaggedWaiver() {
		modelWaiver.clear();
		String SQL = "select * from view_tax_waiver('" + txtCoID.getValue() + "', " + chkSubmitted.isSelected() + ")";
		FncSystem.out("Sales Contract Monitoring", SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelWaiver.addRow(db.getResult()[x]);
			}
		}

		scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
				FncTables.getRowHeader_Footer(Integer.toString(tblWaiver.getRowCount())));
		tblWaiver.packAll();
		tblWaiver.getColumnModel().getColumn(2).setPreferredWidth(250);
	}

	@Override
	public void actionPerformed(ActionEvent e) {// XXX actionCommand
		String action = e.getActionCommand();

		if (action.equals("Refresh")) {
			// displayTaggedWaiver(null,lookupDepartment.getText(),chkSubmitted.isSelected());
		}
		if (action.equals("In")) {
			save();
			enableButton(true, false);
		}
		if (action.equals("Out")) {

			remove();
			enableButton(false, true);
		}
	}

	private void save() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to submit selected agent(s)?",
				"Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String entity_id = "";
			String date_submitted = "";
			String entity_type_id = "";

			pgUpdate db = new pgUpdate();
			Integer row = modelWaiver.getRowCount();
			Integer x = 0;

			while (x < row) {
				Boolean isTrue = false;
				if (modelWaiver.getValueAt(x, 0) instanceof String) {
					isTrue = new Boolean((String) modelWaiver.getValueAt(x, 0));
				}
				if (modelWaiver.getValueAt(x, 0) instanceof Boolean) {
					isTrue = (Boolean) modelWaiver.getValueAt(x, 0);
				}

				if (isTrue) {

					try {
						entity_type_id = modelWaiver.getValueAt(x, 4).toString().trim();
					} catch (NullPointerException e) {
						entity_id = "";
					}
					try {
						entity_id = modelWaiver.getValueAt(x, 1).toString().trim();
					} catch (NullPointerException e) {
						entity_id = "";
					}
					try {
						date_submitted = modelWaiver.getValueAt(x, 6).toString().trim();
					} catch (NullPointerException e) {
						date_submitted = "";
					}

					submitWaiver(entity_id, date_submitted, entity_type_id, txtCoID.getValue());
				}

				x++;
			}

			db.commit();
			JOptionPane.showMessageDialog(getContentPane(), "Waiver(s) submitted.", "Information", JOptionPane.INFORMATION_MESSAGE);
			displayTaggedWaiver();
		}

	}

	private void remove() {

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to remove waiver(s)?", "Save",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String entity_type_id = "";
			String agent_name = "";
			pgUpdate db = new pgUpdate();

			for (int x = 0; x < modelWaiver.getRowCount(); x++) {
				Boolean isTrue = false;
				if (modelWaiver.getValueAt(x, 0) instanceof String) {
					isTrue = new Boolean((String) modelWaiver.getValueAt(x, 0));
				}
				if (modelWaiver.getValueAt(x, 0) instanceof Boolean) {
					isTrue = (Boolean) modelWaiver.getValueAt(x, 0);
				}

				if (isTrue) {

					try {
						entity_type_id = modelWaiver.getValueAt(x, 4).toString().trim();
					} catch (NullPointerException e) {
						entity_type_id = "";
					}
					try {
						agent_name = modelWaiver.getValueAt(x, 1).toString().trim();
					} catch (NullPointerException e) {
						agent_name = "";
					}

					//removeWaiver(db, agent_name, entity_type_id);
					removeWaiver(agent_name, entity_type_id, txtCoID.getValue());

				}
			}

			db.commit();
			JOptionPane.showMessageDialog(getContentPane(), "Waiver(s) removed.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			displayTaggedWaiver();
		}

	}

	private void enableButton(Boolean a, Boolean b) {
		btnSave.setEnabled(a);
		btnRemove.setEnabled(b);
	}

	private void clickTableColumn() {

		int column = tblWaiver.getSelectedColumn();

		if (column == 5) {

			int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlDate, "Date of Waiver Submission",
					JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);

			if (scanOption == JOptionPane.CLOSED_OPTION) {
				try {
					modelWaiver.setValueAt(dteRefDate.getDate(), tblWaiver.getSelectedRow(), 5);
				} catch (java.lang.ArrayIndexOutOfBoundsException e) {
				}
			}
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		clickTableColumn();
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

	private void submitWaiver(pgUpdate db, String agent, String subm_date, String ent_type_id) {

		String sqlDetail = "insert into rf_entity_tax_waiver values (" + "'" + agent + "', \n" + "true, \n" + "'"
				+ subm_date + "', \n" + "'A', \n" + "'" + UserInfo.EmployeeCode + "', \n" + "now(), \n" + "'', \n"
				+ "now() \n" + ") ";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

		String new_ent_type_id = "";

		/*
		 * Modified by Mann2x; Date Modified: December 5, 2018; DCRF# 861; if
		 * (ent_type_id.equals("34")) { new_ent_type_id = "23"; } else if
		 * (ent_type_id.equals("35")) { new_ent_type_id = "24"; }
		 */

		if (ent_type_id.equals("41")) {
			new_ent_type_id = "39";
		} else if (ent_type_id.equals("40")) {
			new_ent_type_id = "38";
		}

		// Modified by Mann2x; Date Modified: December 5, 2018; DCRF# 861;
		// "where entity_type_id in ('03', '04', '12', '34', '35', '23', '24') \n" +
		String sqlDetail2 = "update rf_entity_assigned_type \n" + 
				"set entity_type_id = '"+new_ent_type_id+"' \n" +
				"where entity_type_id in ('03', '04', '12', '34', '35', '23', '24', '39', '38', '41', '40') \n" +
				"and entity_id = '"+agent+"' and status_id = 'A' and co_id = '"+txtCoID.getValue()+"'";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);

		pgUpdate dbExec = new pgUpdate(); 
		if (FncGlobal.GetBoolean("select exists(select * from mf_sellingagent_info where agent_id = '"+agent+"' and array_position(co_id, '02') is not null); ")) {
			dbExec.executeUpdate("update mf_sellingagent_info \n" + 
					"set co_id[array_position(co_id, '"+txtCoID.getValue()+"')] = '"+txtCoID.getValue()+"', entity_type_id[array_position(co_id, '"+txtCoID.getValue()+"')] = '"+new_ent_type_id+"' \n" + 
					"where agent_id = '"+agent+"'; ", true);
		} else {
			dbExec.executeUpdate("update mf_sellingagent_info \n" + 
					"set co_id[array_length(co_id)] = '"+txtCoID.getValue()+"', entity_type_id[array_length(co_id)] = '"+new_ent_type_id+"' \n" + 
					"where agent_id = '"+agent+"'; ", true);	
		}
		dbExec.commit();
	}
	
	private void submitWaiver(String agent, String dteDate, String type, String co_id) {
		String SQL1 = "insert into rf_entity_tax_waiver values ('"+agent+"', true, '"+dteDate+"', 'A', '"+UserInfo.EmployeeCode+"', now(), '', now(), '"+co_id+"'); ";

		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate(SQL1, false);

		String new_ent_type_id = "";

		if (type.equals("41")) {
			new_ent_type_id = "39";
		} else if (type.equals("40")) {
			new_ent_type_id = "38";
		}

		String SQL2 = "update rf_entity_assigned_type \n" + 
				"set entity_type[array_position(co_id, '"+co_id+"')] = '"+new_ent_type_id+"', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
				"where entity_type_id in ('03', '04', '12', '34', '35', '23', '24', '39', '38', '41', '40') \n" +
				"and entity_id = '"+agent+"' and status_id = 'A'";
		dbExec.executeUpdate(SQL2, false);
		dbExec.commit();
	}
	
	private void removeWaiver(pgUpdate db, String agent, String ent_type_id) {
		String sqlDetail = "update rf_entity_tax_waiver " + "set status_id = 'I', " + "edited_by = '"
				+ UserInfo.EmployeeCode + "', \n" + "date_edited = now() \n " + "where entity_id = '" + agent + "' \n";

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);

		String new_ent_type_id = "";

		/*
		 * Modified by Mann2x; Date Modified: December 5, 2018; DCRF# 861; if
		 * (ent_type_id.equals("23")) { new_ent_type_id = "34"; } else if
		 * (ent_type_id.equals("24")) { new_ent_type_id = "35"; }
		 */

		if (ent_type_id.equals("39")) {
			new_ent_type_id = "41";
		} else if (ent_type_id.equals("38")) {
			new_ent_type_id = "40";
		}

		String sqlDetail2 = "update rf_entity_assigned_type set entity_type_id = '" + new_ent_type_id + "' \n" +
				// Modified by Mann2x; Date Modified: December 5, 2018; DCRF# 861;
				// "where entity_type_id in ('03', '04', '12', '34', '35', '23', '24') \n" +
				"where entity_type_id in ('03', '04', '12', '34', '35', '23', '24', '39', '38', '41', '40') \n"
				+ "and entity_id = '" + agent + "' \n" + "and status_id = 'A' ";

		System.out.printf("SQL #2: %s", sqlDetail2);
		db.executeUpdate(sqlDetail2, false);
	}
	
	private void removeWaiver(String agent, String type, String co_id) {
		String SQL1 = "update rf_entity_tax_waiver \n" +
				"set status_id = 'I', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
				"where entity_id = '"+agent+"' and co_id = '"+co_id+"'";

		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate(SQL1, false);

		String new_ent_type_id = "";

		if (type.equals("39")) {
			new_ent_type_id = "41";
		} else if (type.equals("38")) {
			new_ent_type_id = "40";
		}

		String SQL2 = "update rf_entity_assigned_type \n" + 
				"set entity_type[array_position(co_id, '"+co_id+"')] = '"+new_ent_type_id+"', edited_by = '"+UserInfo.EmployeeCode+"', date_edited = now() \n" +
				"where entity_type_id in ('03', '04', '12', '34', '35', '23', '24', '39', '38', '41', '40') \n" +
				"and entity_id = '"+agent+"' and status_id = 'A'";
		dbExec.executeUpdate(SQL2, false);
		dbExec.commit();
	}
}