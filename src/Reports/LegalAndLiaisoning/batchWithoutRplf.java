package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Database.pgSelect;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JTagLabel;
import interfaces._GUI;
import tablemodel.modelbatchwithoutrplf;

public class batchWithoutRplf extends _JInternalFrame implements ActionListener, _GUI {
	
	private static final long serialVersionUID = 1L;
	static Dimension frameSize = new Dimension(700,600);
	static String title = "Batch Without RPLF";
	private JTabbedPane tabpane;
	private JButton btngenerate;
	private JButton btncancel;
	private _JLookup lookupcompany;
	private _JLookup lookupproj;
	private _JTagLabel tagcomp;
	private _JTagLabel tagproj;
	private _JLookup lookupcode;
	private _JTagLabel tagcode;
	private JComboBox cmbpcosttcost;
	private _JTagLabel tagpcosttcost;
	private modelbatchwithoutrplf modelbatch_norplf;
	private _JTableMain tblbatch_norplf;
	private JList rowheader_batch_norplf;
	private JScrollPane scrollPCOST_TCOST;

	public batchWithoutRplf() {
		super(title, true, true, true, true);
		initGUI();
	}

	public batchWithoutRplf(String title) {
		super(title);
	}

	public batchWithoutRplf(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			JPanel pnlmain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlmain);
			pnlmain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlnorth = new JPanel(new BorderLayout(3, 3));
				pnlmain.add(pnlnorth, BorderLayout.NORTH);
				pnlnorth.setPreferredSize(new Dimension(0, 120));
				{
					JPanel pnllabel = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlnorth.add(pnllabel, BorderLayout.WEST);
					pnllabel.setPreferredSize(new Dimension(80, 0));
					{
						JLabel lblcompany = new JLabel("Company", JLabel.LEADING);
						pnllabel.add(lblcompany);
					}
					{
						JLabel lblproj = new JLabel("Project", JLabel.LEADING);
						pnllabel.add(lblproj);
					}
					{
						JLabel lblcode = new JLabel("Pcost/Tcost", JLabel.LEADING);
						pnllabel.add(lblcode);
					}
					{
						JLabel lblpcosttcost = new JLabel("Code", JLabel.LEADING);
						pnllabel.add(lblpcosttcost);
					}
				}
				{
					JPanel pnllookup = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlnorth.add(pnllookup, BorderLayout.CENTER);
					{
						lookupcompany = new _JLookup();
						pnllookup.add(lookupcompany);
						lookupcompany.setLookupSQL(SQL_COMPANY());
						lookupcompany.setReturnColumn(0);
						lookupcompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();
								if(data != null) {
									
									tagcomp.setTag(data[1].toString());
									lookupproj.setLookupSQL(sql_project(data[0].toString()));
									lookupproj.setEditable(true);
								}
							}
						});
					}
					{
						lookupproj = new _JLookup();
						pnllookup.add(lookupproj);
						lookupproj.setReturnColumn(0);
						lookupproj.setEditable(false);
						lookupproj.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();
								if(data != null) {
									
									String projname = data[1].toString();
									tagproj.setTag(projname);
								}
							}
						});
					}
					{
						String status2[] = { "","Pcost","Tcost"};
						cmbpcosttcost = new JComboBox(status2);
						pnllookup.add(cmbpcosttcost);
						cmbpcosttcost.setSelectedIndex(0);
						cmbpcosttcost.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
								
								if(cmbpcosttcost.getSelectedItem() == "Pcost") {
									lookupcode.setLookupSQL(pcost_batch(lookupcompany.getValue(), lookupproj.getValue()));
								}else if (cmbpcosttcost.getSelectedItem() == "Tcost") {
									lookupcode.setLookupSQL(tcost_batch(lookupcompany.getValue(), lookupproj.getValue()));
								}else {
								}
								lookupcode.setEditable(true);
							}
						});
					}
					{
						lookupcode = new _JLookup();
						pnllookup.add(lookupcode);
						lookupcode.setReturnColumn(0);
						lookupcode.setEditable(false);
						lookupcode.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();
								if(data != null) {
									
									tagcode.setTag(data[1].toString());
									button_state(true, true);
								}
							}
						});
					}
				}
				{
					JPanel pnltxtfields = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlnorth.add(pnltxtfields, BorderLayout.EAST);
					pnltxtfields.setPreferredSize(new Dimension(500, 0));
					{
						tagcomp = new _JTagLabel("[ ]");
						pnltxtfields.add(tagcomp);
					}
					{
						tagproj = new _JTagLabel("[ ]");
						pnltxtfields.add(tagproj);
					}
					
					{
						tagpcosttcost = new _JTagLabel("");
						pnltxtfields.add(tagpcosttcost);
					}
					{
						tagcode = new _JTagLabel("[ ]");
						pnltxtfields.add(tagcode);
					}
				}
			}
			{
				JPanel pnlcenter = new JPanel(new BorderLayout(3, 3));
				pnlmain.add(pnlcenter, BorderLayout.CENTER);
				pnlcenter.setBorder(JTBorderFactory.createTitleBorder("Listing"));
				{
					scrollPCOST_TCOST = new JScrollPane();
					pnlcenter.add(scrollPCOST_TCOST);
					scrollPCOST_TCOST.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelbatch_norplf = new modelbatchwithoutrplf();
						tblbatch_norplf = new _JTableMain(modelbatch_norplf);
						scrollPCOST_TCOST.setViewportView(tblbatch_norplf);
						tblbatch_norplf.getColumnModel().getColumn(0).setPreferredWidth(200);
						tblbatch_norplf.getColumnModel().getColumn(1).setPreferredWidth(150);
						tblbatch_norplf.getColumnModel().getColumn(1).setPreferredWidth(150);
					}
					{
						rowheader_batch_norplf = tblbatch_norplf.getRowHeader();
						scrollPCOST_TCOST.setRowHeaderView(rowheader_batch_norplf);
						scrollPCOST_TCOST.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JPanel pnlsouth = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 50));
				{
					btngenerate = new JButton("Generate");
					pnlsouth.add(btngenerate);
					btngenerate.setActionCommand("generate");
					btngenerate.addActionListener(this);
				}
				{
					btncancel = new JButton("Cancel");
					pnlsouth.add(btncancel);
					btncancel.setActionCommand("cancel");
					btncancel.addActionListener(this);
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("generate")) {
//			if(cmbpcosttcost.getSelectedIndex() == 1) {
//				display_pcost(modelbatch_norplf, rowheader_batch_norplf, lookupcompany.getValue(), lookupproj.getValue(), lookupcode.getValue());
//				button_state(false, true);
//			}
//			
//			if(cmbpcosttcost.getSelectedIndex() == 2) {
//				display_tcost(modelbatch_norplf, rowheader_batch_norplf, lookupcompany.getValue(), lookupproj.getValue(), lookupcode.getValue());
//				button_state(false, true);
//			}
			display_pcost_tcost( modelbatch_norplf, rowheader_batch_norplf, lookupcompany.getValue(), lookupproj.getValue(), lookupcode.getValue(), cmbpcosttcost.getSelectedItem().toString() );
		}
		
		if(e.getActionCommand().equals("cancel")) {
			lookupcompany.setValue(null);
			tagcomp.setTag("");
			lookupproj.setValue(null);
			tagproj.setTag("");
			lookupproj.setEditable(false);
			cmbpcosttcost.setSelectedIndex(0);
			lookupcode.setValue(null);
			tagcode.setTag("");
			lookupcode.setEditable(false);
			FncTables.clearTable(modelbatch_norplf);
			rowheader_batch_norplf = tblbatch_norplf.getRowHeader();
			scrollPCOST_TCOST.setRowHeaderView(rowheader_batch_norplf);
		}
	}
	
	public static String SQL_COMPANY() {// XXX Company
		String SQL = "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", "
				+ "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
		return SQL;
	}
	
	public static String sql_project(String co_id) {
		String SQL = "select proj_id as \"ID\", trim(proj_name) as \"Project Name\", trim(proj_alias) as \"Alias\"\n"
				+ "from mf_project where trim(co_id) = '" + co_id + "' and status_id='A' order by proj_id\n";
		return SQL;
	}
	
	private String pcost_batch(String co_id, String proj) {
		String sql = "select pcostdtl_id, pcostdtl_desc, pcostdtl_amt, co_id, proj_id \n"
				+ "from mf_processing_cost_dl \n"
				+ "where status_id = 'A' and co_id = '"+co_id+"' and proj_id = '"+proj+"'";
		System.out.println("pcost_batch: "+sql);
		return sql;
	}
	
	private String tcost_batch(String co_id, String proj) {
		String sql = "select tcostdtl_id,tcostdtl_desc, tcostdtl_amt, co_id, proj_id \n"
				+ "from mf_transfer_cost_dl \n"
				+ "where status_id = 'A' and co_id = '"+co_id+"' and proj_id = '"+proj+"'  order by tcostdtl_id";
		System.out.println("tcost_batch: "+sql);
		return sql;
	}

	public void button_state (Boolean gen, Boolean cancel) {
		btngenerate.setEnabled(gen);
		btncancel.setEnabled(cancel);
		
	}
	
	public void display_pcost_tcost(DefaultTableModel modelMain, JList rowHeader, String co_id, String proj_id, String code, String desc) {
		FncTables.clearTable(modelMain);//Code to clear modelMain.	
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		
		String Strsql = "select * from view_pcost_tcost_without_rplf( '"+code+"', '"+co_id+"', '"+proj_id+"','"+desc+"' ); ";
		
		System.out.println("display_pcost_tcost: "+Strsql);
		pgSelect db = new pgSelect();
		db.select(Strsql);
		if(db.isNotNull()) {
			for (int x = 0; x < db.getRowCount(); x++) {
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
		}
	}
}
