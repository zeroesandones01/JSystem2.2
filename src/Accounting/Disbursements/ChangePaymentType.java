package Accounting.Disbursements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_client;
import tablemodel.model_posted_pv;

public class ChangePaymentType extends _JInternalFrame implements _GUI, MouseListener {
	
	/*
	 * EDITED BY JED 2021-08-02
	 * 
	 * 1.GUI - ADDITIONAL PANEL FOR COMPANY
	 * 2.SAVING - FUNCTION FOR SAVING WAS EDITED, COMPANY ID WAS ADDED AS PARAMETER
	 * 3.FRAME SIZE - FROM (400,400) TO (700,400)
	 * 
	 */
	
	private static final long serialVersionUID = -6517018477299344044L;
	
	static String title = "Change Payment Type of Posted PV";
	//Dimension frameSize = new Dimension(400, 400);// 510, 250
	Dimension frameSize = new Dimension(700, 400);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlSouth;
	
	JXTextField txtSearch;
	
	_JTableMain tblPV;
	model_posted_pv modelPV;

	private _JLookup lookupCompany;

	private JTextField txtCompany;

	private String co_id;

	private String co_name;

	public ChangePaymentType() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ChangePaymentType(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public ChangePaymentType(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		pnlMain = new JPanel();
		this.getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			//pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Search Posted PV"));
			pnlNorth.setPreferredSize(new Dimension(0,135));
			{
				JPanel pnlCompany = new JPanel(new BorderLayout(5,5));
				pnlNorth.add(pnlCompany, BorderLayout.NORTH);
				pnlCompany.setBorder(JTBorderFactory.createTitleBorder("Company"));
				pnlCompany.setPreferredSize(new Dimension(0,65));
				{
					JLabel lblCompany = new JLabel("Company:");
					pnlCompany.add(lblCompany, BorderLayout.WEST);
				}
				{
					JPanel panCom = new JPanel(new BorderLayout(5,5));
					pnlCompany.add(panCom, BorderLayout.CENTER);
					{
						lookupCompany = new _JLookup("", "Company");
						panCom.add(lookupCompany, BorderLayout.WEST);
						lookupCompany.setLookupSQL(SQL_COMPANY());
						lookupCompany.setReturnColumn(0);
						lookupCompany.setPreferredSize(new Dimension(70,0));
						lookupCompany.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								// TODO Auto-generated method stub
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								
								if(data!=null) {
									
									co_id = (String) data[0];	
									co_name	= (String) data[1];	
									
									txtCompany.setText(co_name);
									
									displayPostedPV(co_id);
								}
								
							}
						});
					}
					{
						txtCompany = new JTextField("");
						panCom.add(txtCompany, BorderLayout.CENTER);
						txtCompany.setEditable(false);
					}
				}
			}
			{
				JPanel pnlSearch = new JPanel(new BorderLayout(5,5));
				pnlNorth.add(pnlSearch, BorderLayout.CENTER);
				pnlSearch.setBorder(JTBorderFactory.createTitleBorder("Search Posted PV"));
				{
					txtSearch = new JXTextField();
					pnlSearch.add(txtSearch, BorderLayout.CENTER);
					//txtSearch.setPreferredSize(new java.awt.Dimension(383, 0));
					txtSearch.setHorizontalAlignment(JTextField.CENTER);	
					txtSearch.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent e) {	
							checkAllPostedPVList();
						}				 
					});	
				}			
			}
		}
		{
			pnlSouth = new JPanel(new BorderLayout(5,5));
			//pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlMain.add(pnlSouth, BorderLayout.CENTER);
			//pnlSouth.setLayout(new BorderLayout(5, 5));
			pnlSouth.setBorder(lineBorder);
			pnlSouth.setPreferredSize(new Dimension(370, 290));
			{
				JScrollPane scrollTCostClient = new JScrollPane();
				pnlSouth.add(scrollTCostClient, BorderLayout.CENTER);
				scrollTCostClient.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

				modelPV = new model_posted_pv();

				tblPV = new _JTableMain(modelPV);
				tblPV.addMouseListener(this);
				scrollTCostClient.setViewportView(tblPV);
			}
		}
		//displayPostedPV();
		initialize_comp();
	}
	
	private void initialize_comp() {//ADDED BY JED 2021-08-02 : FOR INITIALIZING COMPANY ID VALUES
		
		co_id = "02";
		co_name = "CENQHOMES DEVELOPMENT CORPORATION";
		lookupCompany.setValue(co_id);
		txtCompany.setText(co_name);
		displayPostedPV(co_id);
		
	}
	
	private void checkAllPostedPVList(){//

		int rw = tblPV.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";
			
			try { name	= tblPV.getValueAt(x,1).toString().toUpperCase();}
			 catch (NullPointerException e) { name	= ""; }
			
			String acct_name	= txtSearch.getText().trim().toUpperCase();	
			Boolean	match		= name.indexOf(acct_name)>0;
			Boolean	start		= name.startsWith(acct_name);
			Boolean	end			= name.endsWith(acct_name);

			if (match==true||start==true||end==true) {
				tblPV.setRowSelectionInterval(x, x); 
				tblPV.changeSelection(x, 1, false, false);
				break;			
			}
			else {
				
				tblPV.setRowSelectionInterval(0, 0); 
				tblPV.changeSelection(0, 1, false, false);					
			}

			x++;

		}		
	}
//	private void displayPostedPV() {
//
//		FncTables.clearTable(modelPV);//Code to clear modelMain.		
//		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
//
//		String sql = "SELECT false, a.pv_no, a.pv_date, b.pay_type_desc, get_client_name(a.entity_id1), c.status_id, a.status_id, a.cv_no\n" + 
//				"				FROM rf_pv_header a  \n" + 
//				"				LEFT JOIN mf_payment_type b on a.pay_type_id = b.pay_type_id  \n" + 
//				"				LEFT JOIN rf_cv_header c on a.cv_no = c.cv_no  \n" + 
//				"				WHERE c.status_id = 'A' or a.cv_no = '' and a.status_id = 'P'\n" + 
//				"				ORDER BY a.pv_no DESC;";
//
//		pgSelect db = new pgSelect();
//		db.select(sql);
//		
//		if(db.isNotNull()){ 
//			for(int x=0; x < db.getRowCount(); x++){
//				modelPV.addRow(db.getResult()[x]);
//				listModel.addElement(modelPV.getRowCount());
//			}	
//		}		
//		tblPV.packAll();
//	}
	private void displayPostedPV(String co_id) {//MODIFIED BY JED 2021-08-02 : PARAMETER COMPANY ID WAS ADDED

		FncTables.clearTable(modelPV);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.

		String sql = "SELECT false, a.pv_no, a.pv_date, b.pay_type_desc, get_client_name(a.entity_id1), c.status_id, a.status_id, a.cv_no\n" + 
				"				FROM rf_pv_header a  \n" + 
				"				LEFT JOIN mf_payment_type b on a.pay_type_id = b.pay_type_id  \n" + 
				"				LEFT JOIN rf_cv_header c on a.cv_no = c.cv_no and a.co_id = c.co_id \n" + 
				"				WHERE a.co_id = '"+co_id+"' and (c.status_id = 'A' or a.cv_no = '') and a.status_id = 'P'\n" +
				"				AND NOT EXISTS (SELECT * \n"+
				" 								from rf_cv_header \n"+
				"								where cv_no = a.cv_no \n"+
				"								and co_id = a.co_id) \n"+	
				"				ORDER BY a.pv_no DESC;";

		pgSelect db = new pgSelect();
		db.select(sql);
		
		if(db.isNotNull()){ 
			for(int x=0; x < db.getRowCount(); x++){
				modelPV.addRow(db.getResult()[x]);
				listModel.addElement(modelPV.getRowCount());
			}	
		}		
		tblPV.packAll();
	}
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblPV)) {
			int row = tblPV.convertRowIndexToModel(tblPV.getSelectedRow());

			for(int x = 0; x < modelPV.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelPV.getValueAt(x, 0);
				if (isSelected) {
					int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are you sure you want to change payment type? ", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						save();
					} else {
						modelPV.setValueAt(false, x, 0);
					}
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
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
	public void save(){//MODIFIED BY JED 2021-08-02 : COMPANY ID WAS ADDED AS PARAMETER IN SAVING
		for(int x = 0; x < tblPV.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPV.getValueAt(x, 0);
			String pv_no = (String) modelPV.getValueAt(x, 1);
			String edited_by = (String) UserInfo.EmployeeCode;
			
			if(isSelected){
				
				//String SQL = "SELECT sp_save_change_payment_type('"+pv_no+"', '"+edited_by+"', now()::timestamp);";
				String SQL = "SELECT sp_save_change_payment_type_v2('"+co_id+"', '"+pv_no+"', '"+edited_by+"', now()::timestamp);";
	
				pgSelect db = new pgSelect();
				FncSystem.out("SQL", SQL);
				db.select(SQL);
			}
		}
		JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saved.", "Change Payment Type.", JOptionPane.INFORMATION_MESSAGE);
		
		modelPV.setRowCount(0);
		tblPV.packAll();
		displayPostedPV(co_id);
	}

}
