package Projects.SalesandMarketing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import tablemodel.model_Sales_Contract;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;
import components._JTableMain;
import components._JTagLabel;

public class SalesContractMonitoring extends _JInternalFrame implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6517018477299344044L;

	static String title = "Sales Contract Monitoring";
	Dimension frameSize = new Dimension(800, 550);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSouth;

	private JScrollPane scrollCenter;
	private _JTableMain tblAccounts;
	private model_Sales_Contract modelAccounts;
	private JList rowheaderAccounts;

	private JButton btnSave;
	private JPanel pnlDept;
	private JPanel pnlDept_a;
	private JLabel lblDepartment;
	private JPanel pnlDept_b;
	private _JLookup lookupDepartment;
	private JPanel pnlDept_c;
	private _JTagLabel tagDepartment;
	private JPanel pnlComp_a3;
	private JCheckBox chkAcctsWithCA;
	private JPanel pnlDept_x;
	
	String div_id = "";

	private JButton btnRemove;

	public SalesContractMonitoring() {
		super(title, true, true, true, true);
		initGUI();
	}

	public SalesContractMonitoring(String title) {
		super(title);
		initGUI();
	}

	public SalesContractMonitoring(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(930, 550));
		this.setMinimumSize(frameSize);
		this.setLayout(new BorderLayout());
		this.setBounds(0, 0, 930, 550);
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(3, 3));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 3));

			{

				pnlDept_x = new JPanel();
				pnlMain.add(pnlDept_x, BorderLayout.NORTH);
				pnlDept_x.setLayout(new BorderLayout(0,0));
				pnlDept_x.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));		
				pnlDept_x.setPreferredSize(new java.awt.Dimension(920, 37));

				{

					pnlDept = new JPanel();
					pnlDept_x.add(pnlDept, BorderLayout.WEST);
					pnlDept.setLayout(new BorderLayout(0,0));
					pnlDept.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));		
					pnlDept.setPreferredSize(new java.awt.Dimension(545, 37));

					{			
						pnlDept_a = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlDept.add(pnlDept_a, BorderLayout.WEST);	
						pnlDept_a.setPreferredSize(new java.awt.Dimension(87, 37));	
						pnlDept_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							lblDepartment = new JLabel("Sales Div. :", JLabel.TRAILING);
							pnlDept_a.add(lblDepartment);
							lblDepartment.setEnabled(true);	
							lblDepartment.setPreferredSize(new java.awt.Dimension(92, 25));
							lblDepartment.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
						}		

						pnlDept_b = new JPanel(new BorderLayout(0, 5));
						pnlDept.add(pnlDept_b, BorderLayout.CENTER);	
						pnlDept_b.setPreferredSize(new java.awt.Dimension(639, 41));	
						pnlDept_b.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 0));

						{
							lookupDepartment = new _JLookup(null, "Company",0,2);
							pnlDept_b.add(lookupDepartment);
							lookupDepartment.setEnabled(true);
							lookupDepartment.setReturnColumn(0);
							lookupDepartment.setLookupSQL(getDivisionMain());
							lookupDepartment.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){										
										//refresh_tablesMain();

										div_id = (String) data[0];	
										String div = (String) data[1];	
										tagDepartment.setTag(div);
										displayAccounts(null,div_id,chkAcctsWithCA.isSelected());
										/*
													
									
									enableMainButtons(true, false, false, true);	
									refreshOthers();
									displayAgentList(modelAgentList, rowHeaderAgentList, modelAgentList_total, div_id );*/	
									}
								}

							});		
						}

						pnlDept_c = new JPanel(new BorderLayout(5, 5));
						pnlDept.add(pnlDept_c, BorderLayout.EAST);	
						pnlDept_c.setPreferredSize(new java.awt.Dimension(389, 32));	
						pnlDept_c.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 5));

						{
							tagDepartment = new _JTagLabel("[ ]");
							pnlDept_c.add(tagDepartment);
							tagDepartment.setBounds(209, 27, 700, 22);
							tagDepartment.setEnabled(true);	
							tagDepartment.setPreferredSize(new java.awt.Dimension(206, 24));
						}	
					}
				}
				{
					pnlComp_a3 = new JPanel(new GridLayout(1, 1, 5, 5));
					pnlDept_x.add(pnlComp_a3, BorderLayout.CENTER);	
					pnlComp_a3.setPreferredSize(new java.awt.Dimension(243, 29));	
					pnlComp_a3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5));

					{
						chkAcctsWithCA = new JCheckBox("Show Submitted Contracts");
						pnlComp_a3.add(chkAcctsWithCA);
						chkAcctsWithCA.setEnabled(true);	
						chkAcctsWithCA.setPreferredSize(new java.awt.Dimension(383, 26));
						chkAcctsWithCA.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent arg0) {
								displayAccounts(null, lookupDepartment.getText(),chkAcctsWithCA.isSelected());
								if (chkAcctsWithCA.isSelected()==true)
								{
									enableButton(false, true);
								}
								else
								{
									enableButton(true, false);
								}
							}
						});
					}
				}
			}


			{
				pnlNorth = new JPanel();
				//pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
				pnlNorth.setPreferredSize(new Dimension(790, 119));
			}
			{
				scrollCenter = new JScrollPane();
				pnlMain.add(scrollCenter, BorderLayout.CENTER);
				scrollCenter.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollCenter.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						tblAccounts.clearSelection();
					}
				});
				{
					modelAccounts = new model_Sales_Contract();
					modelAccounts.addTableModelListener(new TableModelListener() {
						public void tableChanged(TableModelEvent e) {
							if(e.getType() == TableModelEvent.DELETE){
								rowheaderAccounts.setModel(new DefaultListModel());
								scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAccounts.getRowCount())));
							}
							if(e.getType() == TableModelEvent.INSERT){
								((DefaultListModel)rowheaderAccounts.getModel()).addElement(modelAccounts.getRowCount());
							}
						}
					});

					tblAccounts = new _JTableMain(modelAccounts);
					scrollCenter.setViewportView(tblAccounts);
					tblAccounts.setSortable(false);
					//tblAccounts.hideColumns("Client ID", "Proj. ID", "PBL ID", "BuyerType");

					tblAccounts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if(!e.getValueIsAdjusting()){//XXX tblJVDetail

							}
						}
					});
				}
				{
					rowheaderAccounts = tblAccounts.getRowHeader();
					rowheaderAccounts.setModel(new DefaultListModel());
					scrollCenter.setRowHeaderView(rowheaderAccounts);
					scrollCenter.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout());
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(790, 30));
				{
					JPanel pnlNavigation = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.WEST);
					pnlNavigation.setPreferredSize(new Dimension(100, 30));
					{
						JButton btnRefresh = new JButton("Refresh");
						pnlNavigation.add(btnRefresh);
						btnRefresh.addActionListener(this);
					}
				}
				{
					JPanel pnlNavigation = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setPreferredSize(new Dimension(205, 30));
					{
						btnSave = new JButton("In");
						pnlNavigation.add(btnSave);
						btnSave.addActionListener(this);
					}
					{
						btnRemove = new JButton("Out");
						pnlNavigation.add(btnRemove);
						btnRemove.addActionListener(this);
					}
				}
			}
		}

		displayAccounts(null, "",chkAcctsWithCA.isSelected());
		enableButton(true, false);
	}

	private void displayAccounts(String co_id, String div, Boolean submit) {

		modelAccounts.clear();
		String SQL = 
		"select " +
		"false," +
		"a.agent_name," +
		"a.date_submitted," +
		"a.div," +
		"a.dept," +
		"a.sales_type, " +
		"a.posn," +
		"a.accredit_from," +
		"a.accredit_to," +
		"a.prc_no," +
		"a.hlurb_no " +
		"from (" +
		"select \r\n" + 
		"\r\n" + 
		"(case when g.submitted is true then true else false end) as submitted," +
		"upper(trim(b.entity_name)) as agent_name,\r\n" + 
		"g.date_submitted, \r\n" +
		"trim(c.division_alias) as div,\r\n" + 
		"trim(d.dept_alias) as dept,\r\n" + 
		"(case when a.salestype_id = '001' then 'In-House' else\r\n" + 
		"(case when a.salestype_id = '002' then 'External' end ) end) as sales_type,\r\n" + 
		"trim(e.position_abbv) as posn,\r\n" + 
		"a.accredit_from,\r\n" + 
		"a.accredit_to,\r\n" + 
		"trim(f.prc_id) as prc_no,\r\n" + 
		"trim(f.hlurb_regist_no) as hlurb_no" +				 
		"\r\n" + 
		"\r\n" + 
		"from (select * from mf_sellingagent_info where status_id = 'A'" +
		"	and (case when '"+div+"' = '' then agent_id is not null else sales_div_id = '"+div+"' end )) a\r\n" + 
		"left join rf_entity b on a.agent_id = b.entity_id\r\n" + 
		"left join mf_division c on a.sales_div_id = c.division_code\r\n" + 
		"left join mf_department d on a.dept_id = d.dept_code\r\n" + 
		"left join mf_sales_position e on a.salespos_id = e.position_code\r\n" + 
		"left join rf_entity_id_no f on a.agent_id = f.entity_id\r\n" +
		"left join (select * from mf_agent_contract where status_id = 'A' ) g on a.agent_id = g.agent_id " + 
		"\r\n" + 
		"where b.entity_name is not null) a \n" +
		"where (case when "+submit+" = true then a.submitted is true else a.submitted is false end) " +
		"\r\n" + 
		"order by a.agent_name ;";

		FncSystem.out("Sales Contract Monitoring", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x<db.getRowCount(); x++){
				modelAccounts.addRow(db.getResult()[x]);
			}
		}

		scrollCenter.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAccounts.getRowCount())));
		tblAccounts.packAll();
	}

	@Override
	public void actionPerformed(ActionEvent e) {//XXX actionCommand
		String action = e.getActionCommand();

		if(action.equals("Refresh")){
			displayAccounts(null,lookupDepartment.getText(),chkAcctsWithCA.isSelected());
		}
		if(action.equals("In"))
		{ 
			save();
			enableButton(true, false);
		}
		if(action.equals("Out"))
		{ 
			remove();
			enableButton(false, true);
		}
	}

	private void save(){
		
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to submit selected agent(s)?", "Save", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String agent_name = "";
			
			pgUpdate db = new pgUpdate();	
			Integer row = modelAccounts.getRowCount();
			Integer x = 0;

			while (x<row){
				Boolean isTrue = false;
				if(modelAccounts.getValueAt(x,0) instanceof String){
					isTrue = new Boolean((String)modelAccounts.getValueAt(x,0));
				}
				if(modelAccounts.getValueAt(x,0) instanceof Boolean){
					isTrue = (Boolean)modelAccounts.getValueAt(x,0);
				}				

				if(isTrue){						
					
					try { agent_name = modelAccounts.getValueAt(x,1).toString().trim(); } catch (NullPointerException e) { agent_name = ""; }
					
					submitContract(db, agent_name);
				}	
				
				x++;
			}

			db.commit();
			JOptionPane.showMessageDialog(getContentPane(),"Contract(s) submitted.","Information",JOptionPane.INFORMATION_MESSAGE);
			displayAccounts(null,lookupDepartment.getText(),chkAcctsWithCA.isSelected());
		}
		
		
	}
	
	private void remove(){
		
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to remove selected agent(s)?", "Save", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			String agent_name = "";
			pgUpdate db = new pgUpdate();	

			for(int x=0; x < modelAccounts.getRowCount(); x++){
				Boolean isTrue = false;
				if(modelAccounts.getValueAt(x,0) instanceof String){
					isTrue = new Boolean((String)modelAccounts.getValueAt(x,0));
				}
				if(modelAccounts.getValueAt(x,0) instanceof Boolean){
					isTrue = (Boolean)modelAccounts.getValueAt(x,0);
				}
				
				if(isTrue){						
					
					try { agent_name = modelAccounts.getValueAt(x,1).toString().trim(); } catch (NullPointerException e) { agent_name = ""; }
					
					removeContract(db, agent_name);

									
				}	
			}

			db.commit();	
			JOptionPane.showMessageDialog(getContentPane(),"Contract(s) removed.","Information",JOptionPane.INFORMATION_MESSAGE);
			displayAccounts(null,lookupDepartment.getText(),chkAcctsWithCA.isSelected());
		}
		
		
	}
	
	private void submitContract(pgUpdate db, String agent){
		String sqlDetail = 
			"insert into mf_agent_contract values (" +
			"(select entity_id from rf_entity where upper(trim(entity_name)) = '"+agent.replace("'","''")+"' limit 1)," +
			//"'"+sql_getEntityID(agent)+"' " +
			"true," +
			"now()," +
			"'A'" +
			") " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}
	
	private void removeContract(pgUpdate db, String agent){
		String sqlDetail = 
			"update mf_agent_contract set submitted = false, status_id = 'I' where agent_id = " +
			"(select entity_id from rf_entity where upper(trim(entity_name)) = '"+agent.replace("'","''")+"' limit 1)" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		
	}
	
	private String getDivisionMain(){

		String sql = 
			"select " +
			"trim(b.division_code) as \"Div Code\", " +
			"trim(b.division_name) as \"Div Name\", " +
			"trim(b.division_alias) as \"Div Alias\" " +
			"from mf_division b " +
			"where b.division_code in ('06','07','08','09','29') " ;	//'04',	

		return sql;
	}	
	
	private void enableButton(Boolean a, Boolean b){
		btnSave.setEnabled(a);
		btnRemove.setEnabled(b);
	}
	
	public static String sql_getEntityID(String agent) {

		String ent_id = "";

		String SQL = 
			"select entity_id from rf_entity where upper(trim(entity_name)) = '"+agent.replace("'","''")+"'" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {ent_id = "";}
			else {ent_id = (String) db.getResult()[0][0]; }

		}else{
			ent_id = "";
		}

		return ent_id;
	}
	
	
}