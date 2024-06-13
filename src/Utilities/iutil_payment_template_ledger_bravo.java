package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncTables;
import Renderer.DateRenderer;
import components._JTableMain;
import tablemodel.model_itsreal_ledger;

public class iutil_payment_template_ledger_bravo extends JPanel {

	private static final long serialVersionUID = -7381817636244829182L;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	private static String intTemplateID;

	private JScrollPane scrLedger;
	private static model_itsreal_ledger modelLedger;
	public static _JTableMain tblLedger;
	public static JList rowLedger;

	public static void setTemplateID(String template) {
		intTemplateID = template;
	}

	public iutil_payment_template_ledger_bravo() {
		initGUI();
	}

	public iutil_payment_template_ledger_bravo(LayoutManager arg0) {
		super(arg0);
		initGUI();
	}

	public iutil_payment_template_ledger_bravo(boolean arg0) {
		super(arg0);
		initGUI();
	}

	public iutil_payment_template_ledger_bravo(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		JPanel panLedger = new JPanel(new BorderLayout(5, 5));
		this.add(panLedger, BorderLayout.CENTER);
		panLedger.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				scrLedger = new JScrollPane();
				panLedger.add(scrLedger, BorderLayout.CENTER);
				scrLedger.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				{
					modelLedger = new model_itsreal_ledger();
					modelLedger.setEditable(false);

					tblLedger = new _JTableMain(modelLedger);
					rowLedger = tblLedger.getRowHeader();
					scrLedger.setViewportView(tblLedger);

					tblLedger.getColumnModel().getColumn(0).setPreferredWidth(85);
					tblLedger.getColumnModel().getColumn(1).setPreferredWidth(85);
					tblLedger.getColumnModel().getColumn(2).setPreferredWidth(85);

					tblLedger.getColumnModel().getColumn(3).setPreferredWidth(75);
					tblLedger.getColumnModel().getColumn(4).setPreferredWidth(65);
					tblLedger.getColumnModel().getColumn(5).setPreferredWidth(30);
					tblLedger.getColumnModel().getColumn(6).setPreferredWidth(30);
					tblLedger.getColumnModel().getColumn(7).setPreferredWidth(30);

					tblLedger.getColumnModel().getColumn(8).setPreferredWidth(65); /* MRI */
					tblLedger.getColumnModel().getColumn(9).setPreferredWidth(65);
					tblLedger.getColumnModel().getColumn(10).setPreferredWidth(65);
					tblLedger.getColumnModel().getColumn(11).setPreferredWidth(65);
					tblLedger.getColumnModel().getColumn(12).setPreferredWidth(65);

					tblLedger.getColumnModel().getColumn(13).setPreferredWidth(30); /* INTEREST */
					tblLedger.getColumnModel().getColumn(14).setPreferredWidth(75); /* PRINCIPAL */
					tblLedger.getColumnModel().getColumn(15).setPreferredWidth(75);
					tblLedger.getColumnModel().getColumn(16).setPreferredWidth(75);

					tblLedger.getColumnModel().getColumn(17).setPreferredWidth(85);

					tblLedger.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));
					tblLedger.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer(sdf));
					tblLedger.getColumnModel().getColumn(2).setCellRenderer(new DateRenderer(sdf));

					rowLedger = tblLedger.getRowHeader();
					rowLedger.setModel(new DefaultListModel());
					scrLedger.setRowHeaderView(rowLedger);
					scrLedger.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
		}
	}

	@SuppressWarnings("static-access")
	public static void loadLedger(DefaultTableModel modelMain, JList rowHeader) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel);

		String SQL = "select * \n" + "from fn_template_display_client_ledger(" + intTemplateID + "); ";

		System.out.println("");
		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		} else {

		}
		;
	}

	public static void reloadLedger() {
		loadLedger(modelLedger, rowLedger);
	}
}
