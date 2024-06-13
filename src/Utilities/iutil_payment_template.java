package Utilities;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import com.toedter.calendar.JDateChooser;

import Database.pgSelect;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import tablemodel.model_itsreal_ledger;
import tablemodel.model_itsreal_schedule;

public class iutil_payment_template extends _JInternalFrame {

	private static final long serialVersionUID = -5644963472718392882L;
	static String title = "Payment Template";
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private JScrollPane scrRegular;
	private static model_itsreal_schedule modelRegular;	
	public static _JTableMain tblRegular;
	public static JList rowRegular;

	private JScrollPane scrMoratorium;
	private model_itsreal_schedule modelMoratorium;	
	public static _JTableMain tblMoratorium;
	public static JList rowMoratorium;

	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

	private static iutil_payment_template_parameters iutil_params; 
	private static iutil_payment_template_addpayment iutil_addpay;
	private static iutil_payment_template_dues iutil_dues;
	private static iutil_payment_template_bravo iutil_bravo;

	public static CardLayout card; 

	private static JPanel panOmega;
	private static JPanel panAlpha;

	public static iutil_payment_template_ledger ledger; 
	
	public iutil_payment_template() {
		super(title, true, true, true, false);
		initGUI(); 
	}

	public iutil_payment_template(String title) {
		super(title);
		initGUI(); 
	}

	public iutil_payment_template(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	private JPanel panRegularSched() {
		JPanel panRegular = new JPanel(new BorderLayout(5, 5)); 
		panRegular.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			scrRegular = new JScrollPane();
			panRegular.add(scrRegular, BorderLayout.CENTER);
			scrRegular.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelRegular = new model_itsreal_schedule(); 
				modelRegular.setEditable(false);

				tblRegular = new _JTableMain(modelRegular);
				tblRegular.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblRegular.getSelectedRow());
						}
					}
				});
				tblRegular.setFont(FncGlobal.font11);

				rowRegular = tblRegular.getRowHeader();
				scrRegular.setViewportView(tblRegular);

				tblRegular.getColumnModel().getColumn(0).setPreferredWidth(85);
				tblRegular.getColumnModel().getColumn(1).setPreferredWidth(80);
				tblRegular.getColumnModel().getColumn(2).setPreferredWidth(75);
				tblRegular.getColumnModel().getColumn(3).setPreferredWidth(65);
				tblRegular.getColumnModel().getColumn(4).setPreferredWidth(65);
				tblRegular.getColumnModel().getColumn(5).setPreferredWidth(65);
				tblRegular.getColumnModel().getColumn(6).setPreferredWidth(80);
				tblRegular.getColumnModel().getColumn(7).setPreferredWidth(80);
				tblRegular.getColumnModel().getColumn(8).setPreferredWidth(90);

				tblRegular.getColumnModel().getColumn(0).setCellRenderer(new DateRenderer(sdf));

				rowRegular = tblRegular.getRowHeader();
				rowRegular.setModel(new DefaultListModel());
				scrRegular.setRowHeaderView(rowRegular);
				scrRegular.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}

		return panRegular; 
	}

	private JPanel panMoratoriumSched() {
		JPanel panMoratorium = new JPanel(new BorderLayout(5, 5)); 
		panMoratorium.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			scrMoratorium = new JScrollPane();
			panMoratorium.add(scrMoratorium, BorderLayout.CENTER);
			scrMoratorium.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			{
				modelMoratorium = new model_itsreal_schedule(); 
				modelMoratorium.setEditable(false);

				tblMoratorium = new _JTableMain(modelMoratorium);
				tblMoratorium.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						if (!event.getValueIsAdjusting()) {
							System.out.println("");
							System.out.println("Selected row " + tblMoratorium.getSelectedRow());
						}
					}
				});
				tblMoratorium.setFont(FncGlobal.font7);

				rowMoratorium = tblMoratorium.getRowHeader();
				scrMoratorium.setViewportView(tblMoratorium);

				tblMoratorium.getColumnModel().getColumn(0).setPreferredWidth(100);
				tblMoratorium.getColumnModel().getColumn(1).setPreferredWidth(100);
				tblMoratorium.getColumnModel().getColumn(2).setPreferredWidth(100);
				tblMoratorium.getColumnModel().getColumn(3).setPreferredWidth(100);
				tblMoratorium.getColumnModel().getColumn(4).setPreferredWidth(100);
				tblMoratorium.getColumnModel().getColumn(5).setPreferredWidth(100);
				tblMoratorium.getColumnModel().getColumn(6).setPreferredWidth(100);
				tblMoratorium.getColumnModel().getColumn(7).setPreferredWidth(100);
				tblMoratorium.getColumnModel().getColumn(8).setPreferredWidth(100);

				rowMoratorium = tblMoratorium.getRowHeader();
				rowMoratorium.setModel(new DefaultListModel());
				scrMoratorium.setRowHeaderView(rowMoratorium);
				scrMoratorium.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}

		return panMoratorium; 
	}

	public void initGUI() {
		this.setSize(new Dimension(screenSize.width-80, screenSize.height-150));
		this.setMinimumSize(new Dimension(screenSize.width-80, screenSize.height-150));
		this.setLayout(new BorderLayout(5, 5)); 

		card = new CardLayout(); 

		panOmega = new JPanel(card);
		this.add(panOmega, BorderLayout.CENTER);
		{
			createAlpha();

			panOmega.add("alpha", panAlpha);
			panOmega.add("bravo", new iutil_payment_template_bravo(new BorderLayout(5, 5)));
		} 

		iutil_payment_template_parameters.focus();
	}

	@SuppressWarnings("static-access")
	private static void loadRegular(DefaultTableModel modelMain, JList rowHeader) {
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 

		String SQL = "select * \n" + 
				"from view_template_client_schedule \n" + 
				"where template_id::int = "+iutil_params.getTemplateNo()+"::int; ";

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
			JOptionPane.showMessageDialog(null, "No records were returned.");
		};

	}

	public static void reloadRegular() {
		loadRegular(modelRegular, rowRegular); 
	}

	private void createAlpha() {
		panAlpha = new JXPanel(new BorderLayout(5, 5));
		panAlpha.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				iutil_params = new iutil_payment_template_parameters(new BorderLayout(5, 5));
				panAlpha.add(iutil_params, BorderLayout.LINE_START); 
				iutil_params.setPreferredSize(new Dimension(250, 0));
				iutil_params.setBorder(JTBorderFactory.createTitleBorder("Filter", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			}
			{
				JPanel panCenter = new JPanel(new GridLayout(2, 1, 5, 5));  
				panAlpha.add(panCenter, BorderLayout.CENTER); 
				{
					{
						JTabbedPane tabSchedule = new JTabbedPane(); 
						panCenter.add(tabSchedule); 
						{
							tabSchedule.add("     Regular     ", panRegularSched()); 
							tabSchedule.add("   Moratorium    ", panMoratoriumSched());
						}
					}
					{
						JTabbedPane tabLedger = new JTabbedPane(); 
						panCenter.add(tabLedger); 
						{
							ledger = new iutil_payment_template_ledger(new BorderLayout(5, 5)); 
							
							tabLedger.add("      Dues       ", new iutil_payment_template_dues(new BorderLayout(5, 5)));
							tabLedger.add("     Ledger      ", new iutil_payment_template_ledger(new BorderLayout(5, 5)));
						}
					}
				}
			}
			{
				JPanel panEnd = new JPanel(new BorderLayout(5, 5));  
				panAlpha.add(panEnd, BorderLayout.LINE_END); 
				panEnd.setPreferredSize(new Dimension(250, 0));
				{
					iutil_addpay = new iutil_payment_template_addpayment(new BorderLayout(5, 5)); 
					panEnd.add(iutil_addpay, BorderLayout.CENTER); 
				}
			}
		}
	}

	public static void switcher(String key) {
		card.show(panOmega, key);
	}
}
