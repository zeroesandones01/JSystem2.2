package Buyers.CreditandCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.model_NoticesTagBatch;
import tablemodel.model_NoticesTaggedBatch;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import components._JButton;
import components._JTableMain;
import components._JXTextField;

@SuppressWarnings("rawtypes")
public class pnlNoticesTagForCourier extends JXPanel implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JXPanel pnlNorth;
	private JXPanel pnlDateBatch;
	private JXPanel pnlLabelDate;
	private JLabel lblFrom;
	private JLabel lblTo;
	private JXPanel pnlTo;
	private _JDateChooser dteFrom;
	private _JDateChooser dteTo;
	private JXPanel pnlProcess;
	private _JButton btnGenerate;
	private JXPanel pnlTaggedBatch;
	private JXPanel pnlCenter;
	private JXPanel pnlListBatch;
	private JXPanel pnlSearch;
	private JLabel lblSearch;
	private _JXTextField txtSearch;
	private JXPanel pnlTableBatch;
	private JScrollPane scrollBatch;
	private model_NoticesTagBatch modelBatchTaggingModel;
	private _JTableMain tblBatchTagging;

	private JList rowHeadeBatchTagging;
	private JXPanel pnlTagBatch;
	private JScrollPane scrollBatchTagged;
	private model_NoticesTaggedBatch modelBatchTaggedModel;
	private _JTableMain tblBatchTagged;
	private JList rowHeaderBatchTagged;
	private JXPanel pnlSouth;
	private JPanel pnlButton;
	private _JButton btnSendPostOffice;
	private _JButton btnSendLogBook;
	private _JButton btnSave;
	private _JButton btnRemove;
	private _JButton btnCancel;

	public pnlNoticesTagForCourier(Transmittal trans) {
		initGUI();
		FormLoad();
	}

	public pnlNoticesTagForCourier(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlNoticesTagForCourier(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlNoticesTagForCourier(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		{

			pnlNorth = new JXPanel();
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlNorth.setLayout(new GridLayout(1, 2, 3, 3));
			pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 100));
			{
				pnlDateBatch = new JXPanel();
				pnlNorth.add(pnlDateBatch);
				pnlDateBatch.setBorder(components.JTBorderFactory.createTitleBorder("Date of Batch"));
				pnlDateBatch.setLayout(new BorderLayout(3, 3));
				{
					pnlLabelDate = new JXPanel(new GridLayout(2, 1, 3, 3));
					pnlDateBatch.add(pnlLabelDate, BorderLayout.WEST);
					{
						lblFrom = new JLabel("From :");
						pnlLabelDate.add(lblFrom);

					}
					{
						lblTo = new JLabel("To :");
						pnlLabelDate.add(lblTo);
					}

				}

				{
					pnlTo = new JXPanel(new GridLayout(2, 1, 3, 3));
					pnlDateBatch.add(pnlTo, BorderLayout.CENTER);
					{
						dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlTo.add(dteFrom);
						dteFrom.setDate(dateFormat(getDateSQL()));
					}

					{
						dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlTo.add(dteTo);
						dteTo.setDate(dateFormat(getDateSQL()));
					}
				}
			}
			{
				pnlProcess = new JXPanel();
				pnlNorth.add(pnlProcess);
				pnlProcess.setLayout(new BorderLayout());
				{
					btnGenerate = new _JButton("Generate");
					pnlProcess.add(btnGenerate, BorderLayout.CENTER);
					btnGenerate.addActionListener(this);
				}
			}

			//

		}
		{

			pnlCenter = new JXPanel();
			this.add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlCenter.setLayout(new BorderLayout(3, 3));
			pnlCenter.setPreferredSize(new Dimension(this.getWidth(), 400));
			// pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("List
			// of Batch"));

			{
				pnlListBatch = new JXPanel();
				pnlCenter.add(pnlListBatch, BorderLayout.NORTH);
				pnlListBatch.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlListBatch.setLayout(new BorderLayout(3, 3));
				pnlListBatch.setPreferredSize(new Dimension(this.getWidth(), 200));
				pnlListBatch.setBorder(components.JTBorderFactory.createTitleBorder("List of Batch"));
				{

					{
						pnlSearch = new JXPanel(new BorderLayout(3, 3));
						pnlListBatch.add(pnlSearch, BorderLayout.NORTH);
						{
							lblSearch = new JLabel("Search : ");
							pnlSearch.add(lblSearch, BorderLayout.WEST);
						}
						{

						}
						{
							txtSearch = new _JXTextField("Enter Batch No. to Search");
							pnlSearch.add(txtSearch, BorderLayout.CENTER);
							txtSearch.setEditable(true);
							txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
							txtSearch.addKeyListener(new KeyListener() {

								@Override
								public void keyTyped(KeyEvent e) {
									// TODO Auto-generated method stub

								}

								@Override
								public void keyReleased(KeyEvent e) {
									SearchBatch();
								}

								@Override
								public void keyPressed(KeyEvent e) {
									// TODO Auto-generated method stub

								}
							});
							/*
							 * txtSearch.addKeyListener(new KeyAdapter() {
							 * public void keyPressed(KeyEvent pwede) { //
							 * Return value to internal frame when user presses
							 * ENTER on table if(pwede.getKeyCode() ==
							 * KeyEvent.VK_DOWN || pwede.getKeyCode() ==
							 * KeyEvent.VK_TAB){ tblBatchTagging.requestFocus();
							 * } } public void keyReleased(KeyEvent pwede){
							 * //searchText(((JTextField)
							 * evt.getSource()).getText().trim().toUpperCase(),
							 * tblDefault.getRowCount());
							 * 
							 * //TableRowSorter<TableModel> sorter = new
							 * TableRowSorter<TableModel>(tblDefault.getModel())
							 * ; // tblDefault.setRowSorter(sorter);
							 * //sorter.setRowFilter(
							 * RowFilter.regexFilter("(?i)" + ((JTextField)
							 * evt.getSource()).getText().trim(),
							 * cboSearchColumn.getSelectedIndex() +
							 * (getBooleanZero() ? 1:0)) );
							 * //sorter.setSortable(0, !getBooleanZero()); } });
							 * txtSearch.getDocument().addDocumentListener(new
							 * DocumentListener() {
							 * 
							 * @Override public void removeUpdate(DocumentEvent
							 * e) { searchText();
							 * 
							 * }
							 * 
							 * @Override public void insertUpdate(DocumentEvent
							 * e) { searchText();
							 * 
							 * }
							 * 
							 * @Override public void changedUpdate(DocumentEvent
							 * e) { searchText();
							 * 
							 * } });
							 */

						}
					}
					{
						pnlTableBatch = new JXPanel(new BorderLayout(3, 3));
						pnlListBatch.add(pnlTableBatch, BorderLayout.CENTER);
						{

							scrollBatch = new JScrollPane();
							pnlTableBatch.add(scrollBatch, BorderLayout.CENTER);
							scrollBatch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollBatch.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									tblBatchTagging.clearSelection();
								}
							});

							{

								modelBatchTaggingModel = new model_NoticesTagBatch();
								modelBatchTaggingModel.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										if (e.getType() == TableModelEvent.DELETE) {
											rowHeadeBatchTagging.setModel(new DefaultListModel());
										}
										if (e.getType() == TableModelEvent.INSERT) {
											((DefaultListModel) rowHeadeBatchTagging.getModel())
													.addElement(modelBatchTaggingModel.getRowCount());
										}
									}
								});

								tblBatchTagging = new _JTableMain(modelBatchTaggingModel);
								scrollBatch.setViewportView(tblBatchTagging);
								modelBatchTaggingModel.setEditable(true);
								tblBatchTagging.setHorizontalScrollEnabled(true);
								tblBatchTagging.addMouseListener(this);
								tblBatchTagging.packAll();
								tblBatchTagging.hideColumns("Date Prepared", "Status");

								/** Repaint for Highlight **/
								tblBatchTagging.getTableHeader().addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent evt) {
										tblBatchTagging.repaint();
									}
								});
							}
							{

								rowHeadeBatchTagging = tblBatchTagging.getRowHeader();
								rowHeadeBatchTagging.setModel(new DefaultListModel());
								scrollBatch.setRowHeaderView(rowHeadeBatchTagging);
								scrollBatch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
										FncTables.getRowHeader_Header());
							}

						}
					}

				}

			}
			{
				pnlTaggedBatch = new JXPanel();
				pnlCenter.add(pnlTaggedBatch, BorderLayout.CENTER);
				pnlTaggedBatch.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlTaggedBatch.setLayout(new BorderLayout(3, 3));
				pnlTaggedBatch.setPreferredSize(new Dimension(this.getWidth(), 200));
				pnlTaggedBatch.setBorder(components.JTBorderFactory.createTitleBorder("Tagged Notices"));

				{

					pnlTagBatch = new JXPanel(new BorderLayout(3, 3));
					pnlTaggedBatch.add(pnlTagBatch, BorderLayout.CENTER);
					{

						scrollBatchTagged = new JScrollPane();
						pnlTagBatch.add(scrollBatchTagged, BorderLayout.CENTER);
						scrollBatchTagged.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollBatchTagged.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblBatchTagged.clearSelection();
							}
						});

						{

							modelBatchTaggedModel = new model_NoticesTaggedBatch();
							modelBatchTaggedModel.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									// Addition of rows
									if (e.getType() == 1) {
										((DefaultListModel) rowHeaderBatchTagged.getModel())
												.addElement(modelBatchTaggedModel.getRowCount());

										if (modelBatchTaggedModel.getRowCount() == 0) {
											rowHeaderBatchTagged.setModel(new DefaultListModel());
										}
									}
								}
							});

							tblBatchTagged = new _JTableMain(modelBatchTaggedModel);
							scrollBatchTagged.setViewportView(tblBatchTagged);
							modelBatchTaggedModel.setEditable(true);
							tblBatchTagged.setHorizontalScrollEnabled(true);
							tblBatchTagged.addMouseListener(this);
							tblBatchTagged.packAll();
							// tblBatchTagged.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

							/** Repaint for Highlight **/
							tblBatchTagged.getTableHeader().addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									tblBatchTagged.repaint();
								}
							});
						}
						{
							rowHeaderBatchTagged = tblBatchTagged.getRowHeader();
							rowHeaderBatchTagged.setModel(new DefaultListModel());
							scrollBatchTagged.setRowHeaderView(rowHeaderBatchTagged);
							scrollBatchTagged.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER,
									FncTables.getRowHeader_Header());

						}
					}
				}
			}

		}
		{

			pnlSouth = new JXPanel();
			this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlSouth.setLayout(new BorderLayout(3, 3));
			pnlSouth.setPreferredSize(new Dimension(this.getWidth(), 50));
			// pnlSouth.setBorder(components.JTBorderFactory.createTitleBorder("Tagged
			// Notices"));

			{

				JPanel west = new JPanel(new GridLayout(1, 1, 3, 3));
				pnlSouth.add(west, BorderLayout.WEST);
				west.setPreferredSize(new Dimension(100, 50));
				{
					btnSendPostOffice = new _JButton(
							"<html><b><p align=\"center\">Send to \nPost Office</p></b></html>");
					west.add(btnSendPostOffice);
					btnSendPostOffice.addActionListener(this);
				}

				JPanel east = new JPanel(new GridLayout(1, 3, 3, 3));
				pnlSouth.add(east, BorderLayout.EAST);
				east.setPreferredSize(new Dimension(300, 50));
				{
					btnSave = new _JButton("Save");
					east.add(btnSave);
					btnSave.addActionListener(this);
				}
				{

					btnRemove = new _JButton("Remove");
					east.add(btnRemove);
					btnRemove.addActionListener(this);

				}
				{
					btnCancel = new _JButton("Cancel");
					east.add(btnCancel);
					btnCancel.addActionListener(this);
				}

			}
			{
				/*
				 * 
				 * pnlButton = new JPanel(new GridLayout(1, 5, 5, 5));
				 * pnlSouth.add(pnlButton,BorderLayout.CENTER); { {
				 * btnSendPostOffice = new _JButton(
				 * "<html><b><p align=\"center\">For Sending</p></b></html>");
				 * pnlButton.add(btnSendPostOffice);
				 * btnSendPostOffice.addActionListener(this); } { btnSendLogBook
				 * = new _JButton(
				 * "<html><b><p align=\"center\">Send to \nLogBook</p></b></html>"
				 * ); pnlButton.add(btnSendLogBook);
				 * btnSendLogBook.addActionListener(this); } { btnSave = new
				 * _JButton("Save"); pnlButton.add(btnSave);
				 * btnSave.addActionListener(this); } {
				 * 
				 * btnRemove = new _JButton("Remove"); pnlButton.add(btnRemove);
				 * btnRemove.addActionListener(this);
				 * 
				 * } { btnCancel = new _JButton("Cancel");
				 * pnlButton.add(btnCancel); btnCancel.addActionListener(this);
				 * } }
				 */}
		}
	}

	private String getDateSQL() {
		pgSelect db = new pgSelect();
		db.select("SELECT CURRENT_DATE");
		return db.Result[0][0].toString();
	}

	public static Date dateFormat(String dates) {

		DateFormat formatter;
		Date date = null;

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = (Date) formatter.parse(dates);
		} catch (ParseException e) {
		}

		return date;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// XXX actionPerformed
		String action = e.getActionCommand();
		if (e.getSource().equals(btnGenerate)) {
			new Thread(new Generate_Batch_Tagging()).start();
			// new Thread(new Generate_Batch_Tagged()).start();
			btnState(false, true, false, true);
			modelBatchTaggedModel.setEditable(false);
		}

		if (e.getSource().equals(btnSave)) {
			if (hasSelected()) {
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(),
						"Are you sure you want to tagged this batch no ?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					_NoticesTagForCourier.saveBatchTagging(modelBatchTaggingModel);

					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Batches has been tagged.",
							"Save", JOptionPane.INFORMATION_MESSAGE);

					rowHeaderBatchTagged.setModel(new DefaultListModel());
					GenerateTaggedBatch(modelBatchTaggedModel, UserInfo.EmployeeCode);
					scrollBatchTagged.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
							FncTables.getRowHeader_Footer(Integer.toString(tblBatchTagged.getRowCount())));

					rowHeadeBatchTagging.setModel(new DefaultListModel());
					modelBatchTaggingModel.clear();
					scrollBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
							FncTables.getRowHeader_Footer(Integer.toString(tblBatchTagging.getRowCount())));
					modelBatchTaggingModel.setEditable(false);
					modelBatchTaggedModel.setEditable(true);
					btnGenerate.setEnabled(false);
					btnState(true, false, true, true);
				}

			} else {
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select batch no to tagging.",
						action, JOptionPane.WARNING_MESSAGE);
			}

		}

		if (e.getSource().equals(btnRemove)) {
			removeSelectedTable();
		}

		if (e.getSource().equals(btnCancel)) {
			Cancel_Process();
		}

		if (e.getSource().equals(btnSendLogBook)) {

			if (hasSelectedTagged()) {
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(),
						"Do You Want To Send This Tagged Batches To Logbook ?", "Send To LogBook",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					_NoticesTagForCourier.SendLogBook(modelBatchTaggedModel);

					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Send to logbook has been sent.",
							"Send To LogBook", JOptionPane.INFORMATION_MESSAGE);

					rowHeaderBatchTagged.setModel(new DefaultListModel());
					GenerateTaggedBatch(modelBatchTaggedModel, UserInfo.EmployeeCode);
					scrollBatchTagged.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
							FncTables.getRowHeader_Footer(Integer.toString(tblBatchTagged.getRowCount())));

					btnState(true, false, false, true);

				}
			} else {
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select batch no.", action,
						JOptionPane.WARNING_MESSAGE);
			}

		}
		if (e.getSource().equals(btnSendPostOffice)) {

			if (hasSelectedTagged()) {
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(),
						"Do You Want To Send This Tagged Batches To Post Office ?", "Send to Post Office",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

					_NoticesTagForCourier.SendPostOffice(modelBatchTaggedModel);

					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Batch(s) has been saved.",
							"Send to Post Office", JOptionPane.INFORMATION_MESSAGE);

					rowHeaderBatchTagged.setModel(new DefaultListModel());
					GenerateTaggedBatch(modelBatchTaggedModel, UserInfo.EmployeeCode);
					scrollBatchTagged.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
							FncTables.getRowHeader_Footer(Integer.toString(tblBatchTagged.getRowCount())));

					btnState(true, false, false, true);

				}
			} else {
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select batch no.", action,
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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

	private void Cancel_Process() {
		FormLoad();
		modelBatchTaggingModel.clear();
		modelBatchTaggingModel.setEditable(true);
		modelBatchTaggedModel.setEditable(true);
	}

	private void FormLoad() {
		new Thread(new Generate_Batch_Tagged()).start();
		System.out.println("****" + tblBatchTagged.getRowCount());

	}

	private void GenerateTaggedBatch(model_NoticesTaggedBatch model, String Emp_code) {
		pgSelect db = new pgSelect();
		model.clear();

		String SQL = "";

		SQL = "select false, * from sp_generate_batch_tagged('" + Emp_code + "')";

		FncSystem.out("Client Schedule", SQL);
		db.select(SQL);

		if (db.isNotNull()) {
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for (int x = 0; x < db.getRowCount(); x++) {
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		} else {
			// JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to
			// Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}

		tblBatchTagged.packAll();
	}

	public class Generate_Batch_Tagged implements Runnable {

		@Override
		public void run() {
			FncGlobal.startProgress("Generate...Please wait");
			rowHeaderBatchTagged.setModel(new DefaultListModel());
			GenerateTaggedBatch(modelBatchTaggedModel, UserInfo.EmployeeCode);
			scrollBatchTagged.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
					FncTables.getRowHeader_Footer(Integer.toString(tblBatchTagged.getRowCount())));

			tblBatchTagged.packAll();

			if (tblBatchTagged.getRowCount() == 0) {
				btnState(false, false, false, false);
			} else {

				btnState(true, false, true, true);
			}
			FncGlobal.stopProgress();
		} // XXX Generate_Batch_Tagged
	}

	private Boolean hasSelected() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for (int x = 0; x < modelBatchTaggingModel.getRowCount(); x++) {
			listSelected.add((Boolean) modelBatchTaggingModel.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

	public class Generate_Batch_Tagging implements Runnable {

		@Override
		public void run() {
			FncGlobal.startProgress("Generate...Please wait");
			rowHeadeBatchTagging.setModel(new DefaultListModel());
			getBatchForTagging(modelBatchTaggingModel, dteFrom.getDate(), dteTo.getDate());
			scrollBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
					FncTables.getRowHeader_Footer(Integer.toString(tblBatchTagging.getRowCount())));
			tblBatchTagging.packAll();

			FncGlobal.stopProgress();
		} // XXX Generate_Batch_Tagged

	}

	private void getBatchForTagging(model_NoticesTagBatch model, Date dteFrom, Date dteTo) {
		pgSelect db = new pgSelect();
		model.clear();

		String SQL = "";

		SQL = "SELECT false,* FROM sp_generate_batch_tagging('" + dteFrom + "', '" + dteTo + "')";

		FncSystem.out("Client Schedule", SQL);
		db.select(SQL);

		if (db.isNotNull()) {
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for (int x = 0; x < db.getRowCount(); x++) {
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		} else {

			// JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to
			// Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
		tblBatchTagging.packAll();

	}

	private void btnState(Boolean sPostOffice, Boolean sSave, Boolean sRemove, Boolean sCancel) {
		btnSendPostOffice.setEnabled(sPostOffice);
		btnSave.setEnabled(sSave);
		btnRemove.setEnabled(sRemove);
		btnCancel.setEnabled(sCancel);

	}

	private void searchText() {
		// Searches the table with the search string provided and goto or scroll
		// to the corresponding table cell
		int rows = tblBatchTagging.getRowCount();
		int col = 1;
		java.awt.Rectangle rect;
		Boolean bl = false;

		for (int i = 0; i < rows; i++) {
			String value = tblBatchTagging.getValueAt(i, col).toString();
			if (value.toUpperCase().startsWith(txtSearch.getText().toUpperCase()) && bl == false) {
				tblBatchTagging.setRowSelectionInterval(i, i);

				rect = tblBatchTagging.getCellRect(i, col, true);
				tblBatchTagging.scrollRectToVisible(rect);

				bl = true;
			}
		} // for
	} // searchText

	public void removeSelectedTable() {

		ArrayList<String> listBatches = new ArrayList<String>();
		ArrayList<Timestamp> listTaggedDate = new ArrayList<Timestamp>();

		if (!hasSelectedTagged()) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select batch no to delete", "Delete",
					JOptionPane.WARNING_MESSAGE);
		} else {
			if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(),
					"Do You Want To Delete The Batch No.?", "Delete", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				int totalrows = modelBatchTaggedModel.getRowCount();
				int rowdeleted = 0;

				for (int row = 0; row <= totalrows - 1; row++) {
					Boolean isSelected = (Boolean) tblBatchTagged.getModel().getValueAt(row - rowdeleted, 0);
					String batch_no = (String) tblBatchTagged.getModel().getValueAt(row - rowdeleted, 1);
					Timestamp tagged_date = (Timestamp) tblBatchTagged.getModel().getValueAt(row - rowdeleted, 4);
					if (isSelected) {
						System.out.println("Pasuk xa d2 Deleted Entry" + batch_no);
						rowHeaderBatchTagged.setModel(new DefaultListModel());
						modelBatchTaggedModel.removeRow(row - rowdeleted);
						rowdeleted++;
						listBatches.add(batch_no);
						listTaggedDate.add(tagged_date);

					}

					for (int x = 1; x <= modelBatchTaggedModel.getRowCount(); x++) {
						((DefaultListModel) rowHeaderBatchTagged.getModel()).addElement(x);
					}

					tblBatchTagged.packAll();
					scrollBatchTagged.setCorner(JScrollPane.LOWER_LEFT_CORNER,
							FncTables.getRowHeader_Footer(Integer.toString(tblBatchTagged.getRowCount())));

				}
				for (int i = 0; i < listBatches.size(); i++) {
					System.out.println("Del:" + listBatches.get(i));
					pgUpdate pU = new pgUpdate();

					pU.executeUpdate("update rf_notices_sent set status = 'removed', removed_by = '"
							+ UserInfo.EmployeeCode + "', removed_date = now()\n" + "where batch_no = '"
							+ listBatches.get(i) + "' \n" + "and tagged_date = '" + listTaggedDate.get(i) + "' \n"
							+ "and status not in ('sent', 'removed')", true);

					pU.commit();
				}
			}
		}
	}// removeSelectedTable

	private Boolean hasSelectedTagged() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for (int x = 0; x < modelBatchTaggedModel.getRowCount(); x++) {
			listSelected.add((Boolean) modelBatchTaggedModel.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

	private void SearchBatch() {

		String search = txtSearch.getText().toUpperCase().replace("'", "");
		try {
			modelBatchTaggingModel.clear();
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		String sql = "SELECT false,* FROM sp_generate_batch_tagging('" + dteFrom.getDate() + "', '" + dteTo.getDate()
				+ "') \n";

		if (txtSearch.getText().isEmpty() == false) {
			sql = sql + "where c_batch like '%" + search + "'  order by c_batch \n";
		} else {
			sql = sql + " order by c_batch";
		}

		pgSelect db = new pgSelect();
		db.select(sql);
		FncSystem.out("Display Employees", sql);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelBatchTaggingModel.addRow(db.getResult()[x]);
			}
			scrollBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER,
					FncTables.getRowHeader_Footer(Integer.toString(tblBatchTagging.getRowCount())));
			tblBatchTagging.packAll();
		}

	}

}
