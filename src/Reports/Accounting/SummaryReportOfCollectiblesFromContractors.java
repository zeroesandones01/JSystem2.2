package Reports.Accounting;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

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
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_SummaryReportOfCollectiblesFromContractors;

public class SummaryReportOfCollectiblesFromContractors extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = 6949584381598703366L;
	static String title = "SUMMARY REPORT OF COLLECTIBLES FROM CONTRACTORS";
	static Dimension frameSize = new Dimension(800, 600);

	private JPanel pnlMain;
	private JCheckBox chkRemoveZero;
	private static JButton btnPreview;
	private static JButton btnCancel;

	private JScrollPane scrolldetails;
	private static model_SummaryReportOfCollectiblesFromContractors modelSummaryReportOfCollectiblesFromContractors;
	private _JTableMain tblSummaryReportOfCollectiblesFromContractors;
	private static JList rowheaderdetails;

	private _JLookup lookupCompany;
	private static _JLookup lookupContractor;

	private static JTextField txtContractor;
	private JTextField txtCompany;

	private static JLabel lblContractor;
	private JLabel extra;

	private String co_id = "";
	private String company;
	private String company_logo;
	protected String contractor_id;
	protected String contractor_name;

	private static _JXFormattedTextField txtRepair;
	private static _JXFormattedTextField txtUtilities;

	public SummaryReportOfCollectiblesFromContractors() {
		super(title, false, true, true, true);
		initGUI();
	}

	public SummaryReportOfCollectiblesFromContractors(String title) {
		super(title);
		initGUI();
	}

	public SummaryReportOfCollectiblesFromContractors(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override

	public void initGUI() {

		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		this.setMinimumSize(frameSize);
		this.setResizable(true);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				{
					JPanel panCenter = new JPanel(new GridBagLayout()); 
					pnlMain.add(panCenter, BorderLayout.CENTER); 

					GridBagConstraints c = new GridBagConstraints();
					c.fill = c.BOTH;
					c.insets = new Insets(5, 3, 5, 3);

					{
						c.gridx = 0; 
						c.gridy = 0; 
						c.weightx = 1; 
						c.weighty = 0.25;	
						c.ipady = 5; 

						JPanel panel = new JPanel(new BorderLayout());
						panel.setBorder(JTBorderFactory.createTitleBorder("Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						panel.add(getCompany(), BorderLayout.CENTER);

						panCenter.add(panel, c);
					}
					{
						c.gridx = 0; 
						c.gridy = 1; 
						c.weightx = 1; 
						c.weighty = 0.25;	
						c.ipady = 5; 

						JPanel panel = new JPanel(new BorderLayout());
						panel.setBorder(JTBorderFactory.createTitleBorder("Receivables", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						panel.add(getReceivables(), BorderLayout.CENTER);

						panCenter.add(panel, c);
					}
					{
						c.gridx = 0; 
						c.gridy = 2; 
						c.weightx = 1; 
						c.weighty = 2;	
						c.ipady = 100;

						JPanel panel = new JPanel(new BorderLayout());
						panel.add(getDetails(), BorderLayout.CENTER);
						panCenter.add(panel, c);
					}
				}
				{
					JPanel panEnd = new JPanel(new GridLayout(1, 5, 5, 5)); 
					pnlMain.add(panEnd, BorderLayout.PAGE_END); 
					panEnd.setPreferredSize(new Dimension(0, 30));
					panEnd.setBorder(new EmptyBorder(0, 5, 0, 5));
					{

						{
							panEnd.add(new JLabel(""));
							panEnd.add(new JLabel("")); 
							panEnd.add(new JLabel(""));
							panEnd.add(new JLabel("")); 
						}
						{
							btnPreview = new JButton("Preview");
							panEnd.add(btnPreview);
							btnPreview.setFont(FncGlobal.sizeControls);
							btnPreview.setEnabled(false);
							btnPreview.setMnemonic(KeyEvent.VK_P);
							btnPreview.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									preview();
								}
							});
						}
					}
				}
			}
		}
	}

	public static void cleartable_rowheader() {
		FncTables.clearTable(modelSummaryReportOfCollectiblesFromContractors); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowheaderdetails.setModel(listModel); 
	}

	public static void display_contracts(DefaultTableModel model, JList rowHeader, String co_id, String contractor){
		FncTables.clearTable(model); 
		DefaultListModel listModel = new DefaultListModel(); 
		rowHeader.setModel(listModel); 

		String strSQL = "select a.ntp_no, a.contract_no, b.pctg_accomp, \n" + 
				"coalesce(b.repair, 0.00) as repair, coalesce(b.utilities, 0.00) as utilities \n" + 
				"from co_ntp_header a \n" + 
				"left join rf_receivables_from_contractors b on a.ntp_no = b.ntp_no \n" +
				"where (a.co_id = '"+co_id+"' or '"+co_id+"' = '') and a.entity_id = '"+contractor+"' and a.status_id = 'A'; ";
		pgSelect db = new pgSelect();
		db.select(strSQL);

		System.out.println();
		System.out.println("select repair from rf_receivables_from_contractors where entity_id = '"+contractor+"' and repair != 0 limit 1");
		
		txtRepair.setValue(FncGlobal.GetDecimal("select repair from rf_receivables_from_contractors where entity_id = '"+contractor+"' and repair != 0 limit 1"));
		txtRepair.setText(FncGlobal.GetString("select repair::varchar from rf_receivables_from_contractors where entity_id = '"+contractor+"' and repair != 0 limit 1"));
		txtUtilities.setValue(FncGlobal.GetDecimal("select utilities from rf_receivables_from_contractors where entity_id = '"+contractor+"' and utilities != 0 limit 1"));
		txtUtilities.setText(FncGlobal.GetString("select utilities::varchar from rf_receivables_from_contractors where entity_id = '"+contractor+"' and utilities != 0 limit 1"));
		
		if (db.isNotNull()) {
			for (int x=0; x < db.getRowCount(); x++) {
				model.addRow(db.getResult()[x]);
				listModel.addElement(model.getRowCount());
			}
		}
	}

	private JPanel getCompany() {
		GridBagConstraints c = new GridBagConstraints(); 
		JPanel panCompany = new JPanel(new GridBagLayout()); 
		c.fill = c.BOTH; 
		c.ipady = 10;
		c.insets = new Insets(5, 3, 5, 3); 

		{
			c.gridx = 0; 
			c.gridy = 0; 

			c.weightx = .05;
			c.weighty = 1; 

			c.ipadx = 5;

			JLabel lblCompany = new JLabel("Company");
			lblCompany.setFont(FncGlobal.sizeLabel);
			panCompany.add(lblCompany, c); 
		}
		{
			c.gridx = 1; 
			c.gridy = 0; 

			c.weightx = .5;
			c.weighty = 1;

			c.ipadx = 5; 

			lookupCompany = new _JLookup(null, "Company");
			lookupCompany.setFont(FncGlobal.sizeTextValue);
			lookupCompany.setReturnColumn(0);
			lookupCompany.setLookupSQL(SQL_COMPANY());
			lookupCompany.addLookupListener(new LookupListener() {
				public void lookupPerformed(LookupEvent event) {
					Object[] data = ((_JLookup)event.getSource()).getDataSet();

					if(data != null){

						co_id = (String) data[0];
						company = (String) data[1];
						company_logo = (String) data[3];

						String name = (String) data[1];		

						lookupCompany.setValue((String) data[0]);
						txtCompany.setText(name);

						lookupContractor.setLookupSQL("select b.entity_id, b.entity_name \n" + 
								"from (select distinct entity_id from co_ntp_header where co_id = '"+co_id+"' or '"+co_id+"' = '') a \n" + 
								"inner join rf_entity b on a.entity_id = b.entity_id \n" + 
								"order by b.entity_name");
					}
				}
			});
			lookupCompany.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
						lookupCompany.setValue("");
						txtCompany.setText("");

						lookupContractor.setValue("");
						txtContractor.setText("");

						txtRepair.setText("0.00");
						txtUtilities.setText("0.00");

						txtRepair.setEditable(false);
						txtUtilities.setEditable(false);
					}
				}

				public void keyReleased(KeyEvent e) {

				}

				public void keyPressed(KeyEvent e) {

				}
			});

			panCompany.add(lookupCompany, c); 
		}

		{
			c.gridx = 2; 
			c.gridy = 0; 
			c.weightx = 2;
			c.weighty = 1;
			c.ipadx = 50; 

			txtCompany = new JTextField();
			txtCompany.setFont(FncGlobal.sizeTextValue);
			txtCompany.setEditable(false);

			panCompany.add(txtCompany, c); 
		}
		{
			c.gridx = 0; 
			c.gridy = 1; 
			c.weightx = .05;
			c.weighty = 1; 
			c.ipadx = 5;

			lblContractor = new JLabel("Contractor");
			lblContractor.setFont(FncGlobal.sizeLabel);
			panCompany.add(lblContractor,c);
		}
		{
			c.gridx = 1; 
			c.gridy = 1; 
			c.weightx = 0.5;
			c.weighty = 1;
			c.ipadx = 5; 

			lookupContractor = new _JLookup(null, "Contractor");
			panCompany.add(lookupContractor,c);
			lookupContractor.setFont(FncGlobal.sizeTextValue);
			lookupContractor.setReturnColumn(1);
			lookupContractor.setLookupSQL("select b.entity_id, b.entity_name \n" + 
					"from (select distinct entity_id from co_ntp_header) a \n" + 
					"inner join rf_entity b on a.entity_id = b.entity_id \n" + 
					"order by b.entity_name");
			lookupContractor.addLookupListener(new LookupListener() {
				public void lookupPerformed(LookupEvent event) {
					Object[] data = ((_JLookup)event.getSource()).getDataSet();
					if(data != null){	
						contractor_id 	= (String) data[0];		
						contractor_name 	= (String) data[1];
						lookupContractor.setValue(String.valueOf(contractor_id));
						txtContractor.setText(contractor_name);
						modelSummaryReportOfCollectiblesFromContractors.setEditable(true);
						display_contracts(modelSummaryReportOfCollectiblesFromContractors, rowheaderdetails, co_id, contractor_id);

						txtRepair.setEditable(true);
						txtUtilities.setEditable(true);

						modelSummaryReportOfCollectiblesFromContractors.setEditable(true);
						btnPreview.setEnabled(true);
					}
				}
			});
			lookupContractor.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
						lookupContractor.setValue("");
						txtContractor.setText("");

						txtRepair.setText("0.00");
						txtUtilities.setText("0.00");

						txtRepair.setEditable(false);
						txtUtilities.setEditable(false);

						modelSummaryReportOfCollectiblesFromContractors.setEditable(false);
						btnPreview.setEnabled(false);
					}
				}

				public void keyReleased(KeyEvent e) {

				}

				public void keyPressed(KeyEvent e) {

				}
			});
		}
		{
			c.gridx = 2; 
			c.gridy = 1; 

			c.weightx = 2;
			c.weighty = 1;

			c.ipadx = 50; 
			txtContractor = new JTextField();
			panCompany.add(txtContractor, c);
			txtContractor.setFont(FncGlobal.sizeTextValue);
			txtContractor.setEditable(false);
		}

		return panCompany; 
	}

	private JPanel getDetails() {
		GridBagConstraints c = new GridBagConstraints();
		JPanel pandetails = new JPanel(new GridBagLayout());
		c.fill = c.BOTH; 
		c.ipady = 10;
		c.insets = new Insets(0, 5, 0, 5);
		{
			c.gridx = 0; 
			c.gridy = 0; 

			c.weightx = 1;
			c.weighty = 1; 

			c.ipadx = 10;

			scrolldetails = new JScrollPane();
			pandetails.add(scrolldetails, c);
			scrolldetails.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			{
				modelSummaryReportOfCollectiblesFromContractors = new model_SummaryReportOfCollectiblesFromContractors();
				tblSummaryReportOfCollectiblesFromContractors = new _JTableMain(modelSummaryReportOfCollectiblesFromContractors);
				scrolldetails.setViewportView(tblSummaryReportOfCollectiblesFromContractors);
				tblSummaryReportOfCollectiblesFromContractors.getColumnModel().getColumn(0).setPreferredWidth(100);
				tblSummaryReportOfCollectiblesFromContractors.getColumnModel().getColumn(1).setPreferredWidth(300);
				tblSummaryReportOfCollectiblesFromContractors.getColumnModel().getColumn(2).setPreferredWidth(300);
			}
			{
				rowheaderdetails = tblSummaryReportOfCollectiblesFromContractors.getRowHeader();
				scrolldetails.setRowHeaderView(rowheaderdetails);
				scrolldetails.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
		return pandetails;
	}

	private JPanel getReceivables() {
		JPanel panInput = new JPanel(new BorderLayout(10, 10)); 
		{
			{
				JPanel panReceivables = new JPanel(new GridLayout(1, 4, 5, 5)); 
				panInput.add(panReceivables, BorderLayout.CENTER); 
				{
					{
						JLabel label = new JLabel("Repair Works"); 
						panReceivables.add(label); 
						label.setFont(FncGlobal.sizeLabel);
						label.setHorizontalAlignment(JLabel.LEADING);
					}
					{
						txtRepair = new _JXFormattedTextField("0.00");
						panReceivables.add(txtRepair); 
						txtRepair.setHorizontalAlignment(JTextField.TRAILING);
						txtRepair.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtRepair.setEditable(false);
						txtRepair.setFont(FncGlobal.sizeTextValue); 
					}
					{
						JLabel label = new JLabel("Utilities"); 
						panReceivables.add(label); 
						label.setFont(FncGlobal.sizeLabel);
						label.setHorizontalAlignment(JLabel.CENTER);
					}
					{
						txtUtilities = new _JXFormattedTextField("0.00");
						panReceivables.add(txtUtilities); 
						txtUtilities.setHorizontalAlignment(JTextField.TRAILING);
						txtUtilities.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtUtilities.setEditable(false);
						txtUtilities.setFont(FncGlobal.sizeTextValue); 
					}
				}
			}
			{
				JButton btnSave = new JButton("Save"); 
				panInput.add(btnSave, BorderLayout.LINE_END); 
				btnSave.setPreferredSize(new Dimension(100, 0));
				btnSave.setFont(FncGlobal.sizeControls);
				btnSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						save();
					}
				});
			}
		}

		return panInput; 
	}
	
	private void save() {
		pgUpdate dbExec = new pgUpdate(); 
		dbExec.executeUpdate("delete from rf_receivables_from_contractors where entity_id = '"+lookupContractor.getValue()+"'", true);
		dbExec.commit();
		
		dbExec = new pgUpdate();
		for (int x=0; x<tblSummaryReportOfCollectiblesFromContractors.getRowCount(); x++) {
			try {
				dbExec.executeUpdate("insert into rf_receivables_from_contractors values (\n" +
						"'"+lookupContractor.getValue()+"', \n" +
						"'"+tblSummaryReportOfCollectiblesFromContractors.getValueAt(x, 0).toString()+"', \n" +
						"'"+tblSummaryReportOfCollectiblesFromContractors.getValueAt(x, 2).toString()+"'::numeric, \n" +
						"'"+txtRepair.getValue()+"'::numeric, \n" +
						"'"+txtUtilities.getValue()+"'::numeric \n" +
						"); ", true);
			} catch (NullPointerException ex) {
				
			}			
		}
		dbExec.executeUpdate("update rf_receivables_from_contractors set repair = '"+txtRepair.getValue()+"', utilities = '"+txtUtilities.getValue()+"' where entity_id = '"+lookupContractor.getValue()+"'", true);
		dbExec.commit();
		
		display_contracts(modelSummaryReportOfCollectiblesFromContractors, rowheaderdetails, co_id, contractor_id);
	}
	
	private void preview(){
		SimpleDateFormat sdf = new SimpleDateFormat("MMMMM.dd.yyyyy"); 
		String criteria = "";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("co_name", txtCompany.getText());
		mapParameters.put("contractor_id", lookupContractor.getValue());
		mapParameters.put("emp_name", UserInfo.FullName);
		mapParameters.put("as_of", sdf.format(FncGlobal.getDateToday()));
		mapParameters.put("backcharge_repair_works", new BigDecimal(txtRepair.getValue().toString()));
		mapParameters.put("backcharge_utilities", new BigDecimal(txtUtilities.getValue().toString()));	

		FncReport.generateReport("/Reports/rpt_collectibles_from_contractors_summary.jasper", reportTitle, "", mapParameters);
	}
}
