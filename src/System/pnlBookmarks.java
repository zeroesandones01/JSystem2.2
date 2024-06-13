package System;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncTables;
import Functions.UserInfo;
import components._JTableMain;

/**
 * @author Alvin Gonzales
 */
public class pnlBookmarks extends JPanel implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6709358663635977657L;

	private JScrollPane scrollBookmarks;
	private _JTableMain tblBookmarks;
	private modelBookmarks modelBookmarks;
	private JList rowheaderBookmarks;

	public pnlBookmarks() {
		initGUI();
	}

	public pnlBookmarks(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlBookmarks(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlBookmarks(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(new Dimension(0, 0));
		this.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		{
			scrollBookmarks = new JScrollPane();
			this.add(scrollBookmarks, BorderLayout.CENTER);
			scrollBookmarks.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollBookmarks.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					tblBookmarks.clearSelection();
				}
			});
			{
				modelBookmarks = new modelBookmarks();
				modelBookmarks.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						if(e.getType() == TableModelEvent.DELETE){
							rowheaderBookmarks.setModel(new DefaultListModel());
						}
						if(e.getType() == TableModelEvent.INSERT){
							((DefaultListModel)rowheaderBookmarks.getModel()).addElement(modelBookmarks.getRowCount());
						}
					}
				});

				tblBookmarks = new _JTableMain(modelBookmarks);
				scrollBookmarks.setViewportView(tblBookmarks);
				tblBookmarks.hideColumns("Rec ID");

				tblBookmarks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if(!e.getValueIsAdjusting()){//XXX Payments
							/*try {
								Integer row = tblPayments.convertRowIndexToModel(tblPayments.getSelectedRow());

								Integer pay_rec_id = (Integer) modelPayments.getValueAt(row, 20);


							} catch (ArrayIndexOutOfBoundsException e1) {
							} catch (IndexOutOfBoundsException e2) { }*/
						}
					}
				});

				/*tblPayments.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						_JTableMain table = (_JTableMain) e.getSource();
						int[] rows = table.getSelectedRows();

						if(e.isPopupTrigger()){
							if(rows.length == 1){
								try {
									initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
								} catch (NullPointerException e1) { }
							}
						}
					}
					public void mousePressed(MouseEvent e) {
						_JTableMain table = (_JTableMain) e.getSource();
						int[] rows = table.getSelectedRows();

						if(e.isPopupTrigger()){
							if(rows.length == 1){
								try {
									initializeMenu(e).show((_JTableMain)e.getSource(), e.getX(), e.getY());
								} catch (NullPointerException e1) { }
							}
						}
					}

					public JPopupMenu initializeMenu(MouseEvent e) {
						final ArrayList<Boolean> hasSettled = new ArrayList<Boolean>();
						final _JTableMain table = (_JTableMain) e.getSource();

						final String pbl_id = txtPblID.getText();
						final Integer seq_no  = getSequence();

						int row = table.convertRowIndexToModel(table.getSelectedRow());
						System.out.printf("Selected: (%s, %s)%n", row, 14);

						String receipt_no = (String) modelPayments.getValueAt(row, 14);
						String receipt_type = "OR";
						if(receipt_no == null){
							receipt_no = (String) modelPayments.getValueAt(row, 16);
							receipt_type = "AR";
						}

						final String final_receipt_no = receipt_no;
						final String final_receipt_type = receipt_type;

						JMenuItem menuViewRequests = new JMenuItem("Preview Receipt Details/Entries");
						menuViewRequests.setFont(menuViewRequests.getFont().deriveFont(10f));
						menuViewRequests.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(FncGlobal.homeMDI.isNotExisting("CashReceiptBook")){
									CashReceiptBook crb = new CashReceiptBook(final_receipt_type, final_receipt_no, pbl_id, seq_no);
									Home_JSystem.addWindow(crb, null);
								}
							}
						});

						JPopupMenu menu = new JPopupMenu();
						menu.add(menuViewRequests);
						if(hasSettled.contains(true)){
							return null;
						}else{
							return menu;
						}
					}
				});*/
			}
			{
				rowheaderBookmarks = tblBookmarks.getRowHeader();
				rowheaderBookmarks.setModel(new DefaultListModel());
				scrollBookmarks.setRowHeaderView(rowheaderBookmarks);
				scrollBookmarks.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		{
			JPanel pnlSouth = new JPanel(new BorderLayout());
			this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setPreferredSize(new Dimension(0, 30));
			{
				JButton btnDelete = new JButton("Delete");
				pnlSouth.add(btnDelete, BorderLayout.EAST);
				btnDelete.setPreferredSize(new Dimension(100, 0));
				btnDelete.addActionListener(this);
			}
		}

		displayBookmarks(modelBookmarks);
	}

	private void displayBookmarks(modelBookmarks model) {
		model.clear();

		pgSelect db = new pgSelect();
		db.select("SELECT false, TRIM(module_name), TRIM(class_name), date_bookmarked FROM rf_bookmarks WHERE emp_code = '"+ UserInfo.EmployeeCode +"' ORDER BY module_name;");
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}

		scrollBookmarks.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBookmarks.getRowCount())));
		tblBookmarks.packAll();
	}

	private Boolean toSave() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < tblBookmarks.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelBookmarks.getValueAt(x, 0);
			listSelected.add(isSelected);
		}

		if(!listSelected.contains(true)){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select bookmark item to delete.", "Delete", JOptionPane.WARNING_MESSAGE);
		}

		return listSelected.contains(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();
		if(action.equals("Delete")){
			if(toSave()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Delete", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					pgUpdate db = new pgUpdate();
					for(int x=0; x < tblBookmarks.getRowCount(); x++){
						Boolean isSelected = (Boolean) modelBookmarks.getValueAt(x, 0);
						String module = (String) modelBookmarks.getValueAt(x, 1);
						String class_name = (String) modelBookmarks.getValueAt(x, 2);

						if(isSelected){
							//System.out.printf("Module: %s (%s)%n", module, class_name);
							db.executeUpdate("DELETE FROM rf_bookmarks WHERE TRIM(emp_code) = '"+ UserInfo.EmployeeCode +"' AND TRIM(module_name) = '"+ module +"' AND TRIM(class_name) = '"+ class_name +"';", false);
						}
					}
					db.commit();
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Bookmark item has been deleted.", "Delete", JOptionPane.INFORMATION_MESSAGE);
					displayBookmarks(modelBookmarks);
					FncGlobal.homeMDI.reloadBookmark();
				}
			}
		}
	}

	class modelBookmarks extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		Class[] types = new Class[] { Boolean.class, Object.class, Object.class, Timestamp.class };
		Boolean[] editable = new Boolean[]{ true, false, false, false };

		public modelBookmarks() {
			setColumnIdentifiers(new String[] { "Select", "Module", "Class", "Date Bookmarked" });
		}

		public Class getColumnClass(int columnIndex) {
			return types[columnIndex];
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return editable[columnIndex];
		}

		public void clear() {
			FncTables.clearTable(this);
		}

	}

}
