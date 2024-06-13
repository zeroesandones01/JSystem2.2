package Projects.BiddingandAwarding;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;

import Database.pgSelect;
import Database.pgUpdate;
import FormattedTextField._JXFormattedTextField;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Home.Home_JSystem;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelContractorsSuppDetails;

public class ContractorsSupplementaryDetails extends _JInternalFrame implements _GUI, ActionListener, MouseListener, KeyListener {

	private static final long serialVersionUID = -3061284418918863916L;
	protected static final Home_JSystem Home_JSystem = null;
	//public static AbstractFormatterFactory DECIMAL2 = new DefaultFormatterFactory(getDecimalFormatter2());
	
	static String title = "Contractor Supplementary Details";
	static Dimension SIZE = new Dimension(1000, 600);
	private JPanel panel1;
	private JPanel panel2; 
	private AbstractButton option1;
	private AbstractButton option2;
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private JButton saveButton;
	private JButton editButton;
	String to_do = "save";  //to distinguish saving from updating
	private JButton addButton;
	private JButton refreshButton;
	private JScrollPane scrollCSD;
	private modelContractorsSuppDetails modelCSD;
	private static _JTableMain tblCSD;
	private JList rowHeaderCSD;
	private _JLookup lookupContractor;
	private _JXFormattedTextField txtOther;
	private JPanel pnlMain;
	private JPanel panContractor;
	private JLabel lblContractor;
	private JTextField txtContractor;
	private JPanel panNew;
	private JLabel lblNew;
	private JPanel panOther;
	private JLabel lblOther;
	private JPanel panYear;
	private JLabel lblYear;
	private _JXFormattedTextField txtYear;
	private JPanel panEquity;
	private JLabel lblEquity;
	private _JXFormattedTextField txtEquity;
	private JPanel pnlTable;
	private JPanel panel3;
	private JPanel pnlWestlabel;
	private JPanel pnlCenterComponents;
	private JButton deleteButton;
	
	
	public ContractorsSupplementaryDetails() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ContractorsSupplementaryDetails(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public ContractorsSupplementaryDetails(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	public void initGUI() {
		this.setLayout(new BorderLayout (5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(800,500));
		this.setBounds(0,0,800,500);
		{
			pnlMain = new JPanel (new BorderLayout (5,5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			{
				panel1 = new JPanel (new BorderLayout (5,5));
				pnlMain.add(panel1, BorderLayout.PAGE_START);
				panel1.setPreferredSize(new Dimension (0,200));
				panel1.setBorder(components.JTBorderFactory.createTitleBorder("Details"));
				{
					pnlWestlabel = new JPanel (new GridLayout (5,1,5,5));
					panel1.add(pnlWestlabel, BorderLayout.LINE_START);
				}
				{
					lblContractor = new JLabel ("Contractor Name:");
					pnlWestlabel.add(lblContractor);
					lblContractor.setEnabled(false);
				}
				{
					lblNew = new JLabel ("New?:");
					pnlWestlabel.add(lblNew);
					lblNew.setEnabled(false);
				}
				{
					lblOther = new JLabel ("Other:");
					pnlWestlabel.add(lblOther);
					lblOther.setEnabled(false);
				}
				{
					lblYear = new JLabel ("Latest Year of FS Report:");
					pnlWestlabel.add(lblYear);
					lblYear.setEnabled(false);
				}
				{
					lblEquity = new JLabel ("Equity Amount:");
					pnlWestlabel.add(lblEquity);
					lblEquity.setEnabled(false);
				}
				{
					pnlCenterComponents = new JPanel (new GridLayout (5,1,5,5));
					panel1.add(pnlCenterComponents, BorderLayout.CENTER);
					{
						panContractor = new JPanel (new BorderLayout(5,5));
						pnlCenterComponents.add(panContractor);
						{
							lookupContractor = new _JLookup(null, "Contractor");
							panContractor.add(lookupContractor, BorderLayout.LINE_START);
							lookupContractor.setLookupSQL(ContractorName());
							lookupContractor.setReturnColumn(0);
							lookupContractor.setPreferredSize(new Dimension(100, 0));
							lookupContractor.setEnabled(false);	
							lookupContractor.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event){
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null){
										FncSystem.out("Display sql for description", lookupContractor.getLookupSQL());

										txtContractor.setText((String) data[1]);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							txtContractor = new JTextField ("");
							panContractor.add(txtContractor, BorderLayout.CENTER);
							txtContractor.setEditable(false);
							txtContractor.setEnabled(false);
						}
					}
				}
				{
					panNew = new JPanel (new BorderLayout (5,5));
					pnlCenterComponents.add(panNew);
					{
						ButtonGroup group = new ButtonGroup ();
						{
							option1 = new JRadioButton ("Yes");
							panNew.add(option1, BorderLayout.LINE_START);
							group.add(option1);
							option1.setSelected(true);
							option1.setEnabled(false);
						}
						{
							option2 = new JRadioButton ("No");
							panNew.add(option2, BorderLayout.CENTER);
							group.add(option2);
							option2.setSelected(false);
							option2.setEnabled(false);

						}
					}
				}
				{
					panOther = new JPanel (new BorderLayout (5,5));
					pnlCenterComponents.add(panOther);
					{
						txtOther = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						panOther.add(txtOther, BorderLayout.LINE_START);
						txtOther.setFormatterFactory(_JXFormattedTextField.DECIMAL2);
						txtOther.setText("0");
						txtOther.setPreferredSize(new Dimension (100,0));
						txtOther.setEnabled(false);
					}
				}
				{
					panYear = new JPanel (new BorderLayout(5,5));
					pnlCenterComponents.add(panYear);
					{
						txtYear = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						panYear.add(txtYear, BorderLayout.LINE_START);
						txtYear.setPreferredSize(new Dimension (100,0));
						txtYear.setFormatterFactory(_JXFormattedTextField.INTEGER);
						txtYear.setEnabled(false);
					}
				}
				{
					panEquity = new JPanel (new BorderLayout(5,5));
					pnlCenterComponents.add(panEquity);
					{
						txtEquity = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						panEquity.add(txtEquity, BorderLayout.LINE_START);
						txtEquity.setPreferredSize(new Dimension(100,0));
						txtEquity.setFormatterFactory(_JXFormattedTextField.INTEGER);
						txtEquity.setText("0");
						txtEquity.setEnabled(false);
					}
				}
				{
					panel2 = new JPanel (new BorderLayout (5,5));
					pnlMain.add(panel2, BorderLayout.CENTER);
					panel2.setBorder(new EmptyBorder (5,5,5,5));
					{
						pnlTable = new JPanel (new GridLayout (1,1,5,5));
						panel2.add(pnlTable, BorderLayout.CENTER);
						{
							scrollCSD= new JScrollPane();
							pnlTable.add(scrollCSD);
							scrollCSD.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						}
						{
							modelCSD = new modelContractorsSuppDetails();
							tblCSD = new _JTableMain(modelCSD);

							scrollCSD.setViewportView(tblCSD );
							tblCSD .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tblCSD.addMouseListener(this);
							tblCSD .setSortable(false);
							tblCSD.setEnabled(true);
							tblCSD.getColumn(0).setPreferredWidth(80);
							tblCSD.getColumn(1).setPreferredWidth(170);
							tblCSD.getColumn(2).setPreferredWidth(50);
							tblCSD.getColumn(3).setPreferredWidth(100);
							tblCSD.getColumn(4).setPreferredWidth(200);
							tblCSD.getColumn(5).setPreferredWidth(180);
							tblCSD.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								
								@Override
								public void valueChanged(ListSelectionEvent e) {
									if(!e.getValueIsAdjusting()){
									displayContractorDetails();
									}
								}

								
							});
							/*tblCSD.addMouseListener(new MouseAdapter() {

								public void mousePressed(MouseEvent e) {
									if (tblCSD.isEnabled()==true)
									{
										displayContractorDetails();
									}
									else
									{
									}

								}
								public void mouseReleased(MouseEvent e) {
									if(tblCSD.rowAtPoint(e.getPoint()) == -1){
										tblCSD.clearSelection();

									}
								}
							});*/
						}
						{
							rowHeaderCSD = tblCSD.getRowHeader();
							rowHeaderCSD .setModel(new DefaultListModel());
							scrollCSD.setRowHeaderView(rowHeaderCSD);
							scrollCSD.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}
				{
					panel3 = new JPanel (new GridLayout (1,5,5,5));
					pnlMain.add(panel3, BorderLayout.PAGE_END);
					panel3.setPreferredSize(new Dimension (30,30));
					{
						{
							addButton = new JButton ("Add New");
							panel3.add(addButton);
							addButton.setPreferredSize(new Dimension (50, 0));
							addButton.setActionCommand("Add New");
							addButton.addActionListener(this);
							addButton.setEnabled(true);
						}
						{
							editButton = new JButton ("Edit");
							panel3.add(editButton);
							editButton.setPreferredSize(new Dimension (50, 0));
							editButton.setActionCommand("Edit");
							editButton.addActionListener(this);
							editButton.setEnabled(false);
						}
						{
							saveButton = new JButton ("Save");
							panel3.add(saveButton);
							saveButton.setPreferredSize(new Dimension (50, 0));
							saveButton.setActionCommand("Save");
							saveButton.addActionListener(this);
							saveButton.setEnabled(false);
						}
						{
							deleteButton = new JButton ("Delete");
							panel3.add(deleteButton);
							deleteButton.setPreferredSize(new Dimension (50, 0));
							deleteButton.setActionCommand("Delete");
							deleteButton.addActionListener(this); 
							deleteButton.setEnabled(false);
						}
						{
							refreshButton = new JButton ("Refresh");
							panel3.add(refreshButton);
							refreshButton.setPreferredSize(new Dimension (50, 0));
							refreshButton.setActionCommand("Refresh");
							refreshButton.addActionListener(this);
							refreshButton.setEnabled(false);

						}
					}
				}
			}

			displayContractor(modelCSD, rowHeaderCSD);
		}
	}
	
	//SQL
	
	private static String ContractorName() {
		String sql = "SELECT * FROM view_ntp_contractors();";
		return sql;
	}
	
	private void insertContractorDetails(pgUpdate db){

		String con_name = "";
		String con_new	= "";
		Double other	= null;	
		String yr_rpt	= "";	
		Double equity	= null;
		
						
		con_name = lookupContractor.getText();
		if(option1.isSelected()==true){con_new = "Yes";}
		else {con_new = "No";}
		other = Double.parseDouble(txtOther.getText());
		yr_rpt = txtYear.getText();
		equity = Double.parseDouble(txtEquity.getText());
		
		String sqlDetail =
			"INSERT into rf_contractor_sup_details values (" + 
			"'"+con_name+"',  \n" +  		//1
			"'"+con_new+"',  \n" +		//2
			"'"+other+"',  \n" +		//3
			"'"+yr_rpt+"',  \n" +		//4
			"'"+equity+"'," +
			"'"+UserInfo.EmployeeCode+"',"  +
			"now(),"  +
			"'A'  \n " +    //5
			")  " ;
		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

		

	}
	
	private void updateContractorDetails(pgUpdate db){
		
		String con_name = "";

		con_name = lookupContractor.getText();
		
		String sqlDetail =
			"update rf_contractor_sup_details set " + 
			"status_id = 'I'  \n" +  	
			"where con_name = '"+con_name+"'   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
	}
	
	//operation
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Add New")) { addnew(); }

		if (e.getActionCommand().equals("Save")) { save(); }
		
		if (e.getActionCommand().equals("Edit")) { edit(); }
		
		if(e.getActionCommand().equals("Refresh")){ refresh(); }
		
		if(e.getActionCommand().equals("Delete")){ delete(); }
		
	}
	
	private void save(){
		if(checkDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please complete filling up the form.", "Information", 
				JOptionPane.WARNING_MESSAGE);}
		else {

			if(checkContractor()==false)
			{JOptionPane.showMessageDialog(getContentPane(), "Please select a contractor.", "Information", 
					JOptionPane.WARNING_MESSAGE);}
			else {		

				if(checkOther()==false)
				{JOptionPane.showMessageDialog(getContentPane(), "Please complete all the details.", "Information", 
						JOptionPane.WARNING_MESSAGE);}
				else {	

					if(checkYear()==false)
					{JOptionPane.showMessageDialog(getContentPane(), "Please complete all the details.", "Information", 
							JOptionPane.WARNING_MESSAGE);}
					else {	

						if(checkEquity()==false)
						{JOptionPane.showMessageDialog(getContentPane(), "Please complete all the details.", "Information", 
								JOptionPane.WARNING_MESSAGE);}
								else {
									executeSave();}}}}}}

	private void executeSave() {
	
	if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
			JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

		if (to_do.equals("save")) {
		pgUpdate db = new pgUpdate();	//1
		insertContractorDetails(db);	//2	
		db.commit();	//3
		JOptionPane.showMessageDialog(getContentPane(),"All data saved.","Information",JOptionPane.INFORMATION_MESSAGE);
		displayContractor(modelCSD, rowHeaderCSD);	
		enable_fields(false);
		tblCSD.setEnabled(true);
		saveButton.setEnabled(false);
		addButton.setEnabled(true);
		
		
	}
		else if (to_do.equals("update")) {
		pgUpdate db = new pgUpdate();	//1
		updateContractorDetails(db);//2
		insertContractorDetails(db);	//2	
		db.commit();	//3
		JOptionPane.showMessageDialog(getContentPane(),"All data updated","Information",JOptionPane.INFORMATION_MESSAGE);
		displayContractor(modelCSD, rowHeaderCSD);	
		enable_fields(false);
		tblCSD.setEnabled(true);
		addButton.setEnabled(true);
		saveButton.setEnabled(false);
		}
	}
}
	
	private void refresh(){
		refresh_fields_fromSaving();
		addButton.setEnabled(true);
		saveButton.setEnabled(false);
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		refreshButton.setEnabled(false);
		lblContractor.setEnabled(false);
		lookupContractor.setEnabled(false);
		lblNew.setEnabled(false);
		lblOther.setEnabled(false);
		txtOther.setEnabled(false);
		option1.setEnabled(false);
		option2.setEnabled(false);
		option1.setSelected(true);
		lblYear.setEnabled(false);
		txtYear.setEnabled(false);
		lblEquity.setEnabled(false);
		txtEquity.setEnabled(false);
		txtContractor.setEditable(false);
		txtContractor.setEnabled(false);
		tblCSD.getSelectionModel().clearSelection();
	}
	
	private void addnew(){

		enable_fields(true);
		refresh_fields_fromSaving();
		saveButton.setEnabled(true);
		addButton.setEnabled(false);
		editButton.setEnabled(false);
		refreshButton.setEnabled(true);
		deleteButton.setEnabled(false);
		tblCSD.setEnabled(false);
		tblCSD.setEditable(false);
		txtContractor.setEditable(false);
		txtContractor.setEnabled(true);
		to_do = "save";
	}
	
	private void edit(){	
		
		enable_fields(true);
		addButton.setEnabled(false);
		saveButton.setEnabled(true);
		editButton.setEnabled(false);
		refreshButton.setEnabled(true);
		deleteButton.setEnabled(false);
		tblCSD.setEnabled(false);
		tblCSD.setEditable(false);
		txtContractor.setEditable(false);
		txtContractor.setEnabled(true);
		lookupContractor.setEnabled(false);
		txtContractor.setEnabled(false);
		to_do = "update";
		
	}
	
	private void delete(){
		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete the selected row?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			
			pgUpdate db = new pgUpdate();	//1
			updateContractorDetails(db);	//2	
			db.commit();	//3
			JOptionPane.showMessageDialog(getContentPane(),"Successfully deleted.","Information",JOptionPane.INFORMATION_MESSAGE);
			displayContractor(modelCSD, rowHeaderCSD);
			refresh_fields_fromSaving();
			deleteButton.setEnabled(false);
		}
		
	}
	
	//display table
	
	private static void displayContractor(DefaultTableModel modelMain, JList rowHeader) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
			"SELECT con_name, b.entity_name, con_new, other, yr_rpt, equity "
			+ "FROM rf_contractor_sup_details a "			
			+ "left join rf_entity b on a.con_name = b.entity_id " 
			+ "where a.status_id = 'A' \n "
			+ "order by created_date ASC";
		
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}	
		}
		
		tblCSD.packAll();
	}
	
	private void displayContractorDetails(){
		if (tblCSD.getSelectedRows().length>0){
			int rw = tblCSD.getSelectedRow();	
			String con_name = tblCSD.getValueAt(rw,0).toString().trim();

			Object[] ch_ord = getContractorDetail(con_name);

			lookupContractor.setText((String) ch_ord[0]);

			txtContractor.setText((String) ch_ord[1]);

			String con_new = (String) ch_ord[2];
			if (con_new.equals("Yes"))
			{option1.setSelected(true); option2.setSelected(false);} 
			else
			{option1.setSelected(false); option2.setSelected(true);}

			txtOther.setText(((BigDecimal) ch_ord[3]).toString());

			txtYear.setText((String) ch_ord[4]);

			txtEquity.setText(((BigDecimal) ch_ord[5]).toString());

			editButton.setEnabled(true);

			refreshButton.setEnabled(true);

			deleteButton.setEnabled(true);
		}
	}
	
	private Object [] getContractorDetail(String con_name) {			

		String strSQL = 
			"SELECT con_name, b.entity_name, con_new, other, yr_rpt, equity "
			+ "FROM rf_contractor_sup_details a "
			+ "left join rf_entity b on a.con_name = b.entity_id " + 
			"where con_name = '"+con_name+"'"
					+ "and a.status_id ='A' ";

		
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}
	
	//Boleean
	
	private Boolean checkDetails(){

		boolean x = false;		

		String con_name, other, yr_rpt, equity;

		con_name = lookupContractor.getText();
		other = txtOther.getText();
		yr_rpt = txtYear.getText();
		equity = txtEquity.getText();

		if (con_name.equals("")&&other.equals("")&&yr_rpt.equals("")&&equity.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}
	
	private Boolean checkContractor(){

		boolean x = false;		

		String con_name;

		con_name = lookupContractor.getText();

		if (con_name.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}
	
	private Boolean checkOther(){

		boolean x = false;		

		String other;

		other = txtOther.getText();

		if (other.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}
	
	private Boolean checkYear(){

		boolean x = false;		

		String yr_rpt;

		yr_rpt = txtYear.getText();

		if (yr_rpt.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}
	
	private Boolean checkEquity(){

		boolean x = false;		

		String equity;

		equity = txtEquity.getText();

		if (equity.equals("")) {x=false;} 
		else {x=true;}		

		return x;

	}
	
	private void enable_fields(Boolean x){

		lblContractor.setEnabled(x);
		lookupContractor.setEnabled(x);
		txtContractor.setEnabled(x);
		lblNew.setEnabled(x);
		lblOther.setEnabled(x);
		txtOther.setEnabled(x);
		lblYear.setEnabled(x);
		txtYear.setEnabled(x);
		lblEquity.setEnabled(x);
		txtEquity.setEnabled(x);
		option1.setEnabled(x);
		option2.setEnabled(x);
		tblCSD.getSelectionModel().clearSelection();
		
	}
	
	private void refresh_fields_fromSaving(){  

		lookupContractor.setText("");
		txtOther.setText("0");
		txtYear.setText("");
		txtEquity.setText("0");
		txtContractor.setText("");
		option1.setSelected(true);
		tblCSD.setEnabled(true);
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	


}
