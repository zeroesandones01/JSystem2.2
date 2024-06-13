package Accounting.FixedAssets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import Accounting.GeneralLedger.JournalVoucher;
import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelpurchase_receiving;

public class procurement_payment_processing extends JPanel implements ActionListener, _GUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelpayment_processing;
	private JPanel pnlcenter;
	private JScrollPane scrollpymt_processing;
	private static modelpurchase_receiving modelpymt_processing;
	private static _JTableMain tblpymt_processing;
	private static JList rowheaderpymt_processing;
	private JPanel pnlnorth;
	private JPanel pnlnorth_west;
	private JPanel pnlnorth_east;
	public static _JLookup lookuppo_no;
	private static JTextField txtstatus;
	private static _JDateChooser date;
	protected static String po_no;
	private JPopupMenu menu1;
	private JMenuItem mnicreatenRPLF;
	protected Date dr_date;
	protected static String dr_id;


	public procurement_payment_processing() {
		initGUI();
	}

	public procurement_payment_processing(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public procurement_payment_processing(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public procurement_payment_processing(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 3, 3));
		{
			panelpayment_processing= new JPanel(new BorderLayout(5, 5));
			  this.add(panelpayment_processing, BorderLayout.CENTER);
			  
			  {
				    menu1 = new JPopupMenu("Popup");	
					mnicreatenRPLF = new JMenuItem("Create RPLF");
					menu1.add(mnicreatenRPLF);
					mnicreatenRPLF.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							if(JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Do you want to create RPLF?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)== JOptionPane.YES_OPTION) {
								
								//insert_rplf();
								insert_rplf_details_header();
								JOptionPane.showMessageDialog(getTopLevelAncestor(), "RPLF already created!", "", JOptionPane.INFORMATION_MESSAGE);
								display_PO(modelpymt_processing, rowheaderpymt_processing);
							}
						}
					});
			  }
			  
			  {
				  pnlnorth = new JPanel(new BorderLayout(5, 5));
				  panelpayment_processing.add(pnlnorth, BorderLayout.NORTH);
				  pnlnorth.setPreferredSize(new Dimension(0, 60));
				  {
					  pnlnorth_west = new JPanel(new BorderLayout(5, 5));
					  pnlnorth.add(pnlnorth_west, BorderLayout.WEST);
					  pnlnorth_west.setPreferredSize(new Dimension(200, 0));
					  {
						  JPanel pnllabel = new JPanel(new GridLayout(2, 1, 5, 5));
						  pnlnorth_west.add(pnllabel, BorderLayout.WEST);
						  pnllabel.setPreferredSize(new Dimension(70, 0));
						  {
							  JLabel lblpo_no = new JLabel("DR ID");
							  pnllabel.add(lblpo_no);
						  }
						  {
							  JLabel lblxtra = new JLabel("");
							  pnllabel.add(lblxtra);
						  }
					  }
					  {
						  JPanel pnlfields = new JPanel(new GridLayout(2, 1, 5, 5));
						  pnlnorth_west.add(pnlfields, BorderLayout.CENTER);
						  {
							  lookuppo_no = new _JLookup();
							  lookuppo_no.setLookupSQL(setDR_id());
							  pnlfields.add(lookuppo_no);
							  lookuppo_no.setReturnColumn(0);
							  lookuppo_no.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup)event.getSource()).getDataSet();
										
										if(data !=null) {
											dr_id= (String)data[0];
											po_no= (String)data[1];
											dr_date=(Date)data[2];
											
											date.setDate(dr_date);
											display_PO(modelpymt_processing, rowheaderpymt_processing);
											displayreceiving_details();
											procurement.enable_buttons(true, false, false, false, true, true);
										}
									}
								});
							  //lookuppo_no.addMouseListener(new PopupTriggerListener_panel2());
						  }
						  {
							  JLabel lblxtra2 = new JLabel("");
							  pnlfields.add(lblxtra2);
						  }
					  }
				  }
				  {
					  pnlnorth_east = new JPanel(new BorderLayout(5, 5));
					  pnlnorth.add(pnlnorth_east, BorderLayout.EAST);
					  pnlnorth_east.setPreferredSize(new Dimension(200, 0));
					  {
						  JPanel pnltxtflds = new JPanel(new GridLayout(2, 1, 5, 5));
						  pnlnorth_east.add(pnltxtflds, BorderLayout.EAST);
						  pnltxtflds.setPreferredSize(new Dimension(120, 0));
						  {
							  date = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							  pnltxtflds.add(date);
						  }
						  {
							  txtstatus = new JTextField();
							  pnltxtflds.add(txtstatus);
						  }
					  }
					  {
						  JPanel pnlfields = new JPanel(new GridLayout(2, 1, 5, 5));
						  pnlnorth_east.add(pnlfields, BorderLayout.CENTER);
						  {
							  JLabel lbldate = new JLabel("Date");
							  pnlfields.add(lbldate);
						  }
						  {
							  JLabel lblstatus = new JLabel("Status");
							  pnlfields.add(lblstatus);
						  }
					  }
					  
				  }
			  }
			  {
				  pnlcenter = new JPanel(new BorderLayout(5,5));	
				  panelpayment_processing.add(pnlcenter, BorderLayout.CENTER);
				  {
					  scrollpymt_processing = new JScrollPane();
					  pnlcenter.add(scrollpymt_processing);
					  scrollpymt_processing.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					  {
						  modelpymt_processing = new modelpurchase_receiving();
						  tblpymt_processing = new _JTableMain(modelpymt_processing);
						  scrollpymt_processing.setViewportView(tblpymt_processing);
						  tblpymt_processing.setHorizontalScrollEnabled(true);
						  modelpymt_processing.setEditable(false);
						  tblpymt_processing.addMouseListener(new PopupTriggerListener_panel2());
					  }
					  {
						  
						  rowheaderpymt_processing = tblpymt_processing.getRowHeader();
						  scrollpymt_processing.setRowHeaderView(rowheaderpymt_processing);
						  scrollpymt_processing.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						
					  }
				  }
			  }
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
	//right click
	class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int column = tblpymt_processing.getSelectedColumn();
				int row    = tblpymt_processing.getSelectedRow();
				
				if(column == 8  ) {
					
					menu1.show(ev.getComponent(), ev.getX(), ev.getY());
					
				}else {
					
					menu1.setEnabled(false);
				}
				
				
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu1.show(ev.getComponent(), ev.getX(), ev.getY());
			}
		}
	}
	
	public static void insert_rplf_details_header() {
		
		pgSelect db = new pgSelect();
		//String strsql="select sp_create_rplf_purchase_order2()";
		//String strsql="select sp_create_rplf_purchase_order3('"+procurement.lookupcompany.getValue()+"' , '"+po_no+"' , '"+UserInfo.EmployeeCode+"', '"+txtremarks.getText()+"', '"+lookuprecvdby_id.getValue()+"', '"+received_date.getDate()+"')";
		String strsql="select sp_create_receiving_rplf  \n"+
				"(\n"+
						"'"+procurement.lookupcompany.getValue()+"',\n"+
						"'"+po_no+"', \n"+
						"'"+UserInfo.EmployeeCode+"', \n"+
						"'"+dr_id+"' \n"+
				")";
		
		
		System.out.printf("insert_rplf_details_header= ", strsql);
		db.select(strsql);	
	}
	
	
	public static void insert_rplf() {
		
		int x  = 0;	
		int rw = tblpymt_processing.getModel().getRowCount();
		String co_id = procurement.lookupcompany.getValue();
		String requester = procurement.lookuprequester.getValue();
		//String remarks = txtremarks.getText();
		//String rcvd_by = lookuprecvdby_id.getValue();
		//rplf_no = sql_getNextRPLFno();
		//String dr_id= setreceiving_dr_id();
		
		
		pgSelect db = new pgSelect();
		
		while(x<rw) {
			
			Boolean selected = (Boolean) modelpymt_processing.getValueAt(x, 0);
			String supp_id = (String) modelpymt_processing.getValueAt(x, 1);
			String po_no = (String) modelpymt_processing.getValueAt(x, 2);
			String item_id = (String) modelpymt_processing.getValueAt(x, 3);
			String description = (String) modelpymt_processing.getValueAt(x, 4);
			String unit = (String) modelpymt_processing.getValueAt(x, 5);
			String quantity = (String) modelpymt_processing.getValueAt(x, 6);
			String dr_id = (String) modelpymt_processing.getValueAt(x, 9);
			
		if(selected) {	
		
		String strsql="select sp_create_rplf_purchase_order2  \n"+
				"(\n"+
						"'"+co_id+"',\n"+
						"'"+po_no+"', \n"+
						"'"+UserInfo.EmployeeCode+"', \n"+
						"'"+dr_id+"', \n"+
						"'"+item_id+"' \n"+
				")";
				
			
			db.select(strsql);
			System.out.printf("insert_receiving: %s",strsql);
			System.out.println("supp_id= "+ supp_id);
			System.out.println("po_no= "+ po_no);

		}
			x++;
		
		}
	}
	
	public static String setDR_id() {
		String strsql="select distinct on(dr_id) dr_id,po_no, date_received\n" + 
				"from rf_receiving_purchase";
		return strsql;
	}
	
public static void displayreceiving_details() {
		
		String strsql="select distinct on (a.po_no)\n" + 
				"a.co_id,\n" + 
				"c.company_name,\n" + 
				"a.requester,\n" + 
				"e.entity_name,\n" + 
				"g.division_alias || '-' || h.dept_alias as div_dept \n" + 
				"from rf_purchase_order a \n" + 
				"left join rf_receiving_purchase b on a.po_no=b.po_no\n" + 
				"left join mf_company c on a.co_id=c.co_id\n" + 
				"left join em_employee d on a.requester=d.emp_code\n" + 
				"left join rf_entity e on d.entity_id=e.entity_id\n" + 
				"left join rf_purchase_request_details f on a.pr_no=f.pr_no\n" + 
				"left join mf_division g on f.div_id=g.division_code\n" + 
				"left join mf_department h on f.dept_id=h.dept_code\n" + 
				"where a.po_no='"+lookuppo_no.getValue()+"' ";
		
		FncSystem.out("displayreceiving_details", strsql);
		pgSelect db = new pgSelect();
		db.select(strsql);
		if(db.isNotNull()) {
			procurement.lookupcompany.setValue((String) db.getResult()[0][0]);    //co_id
			procurement.txtcompany.setText((String) db.getResult()[0][1]);        //company_name
			procurement.lookuprequester.setValue((String) db.getResult()[0][2]);  //requester
			procurement.txtrequester_name.setText((String) db.getResult()[0][3]); //entity_name
			procurement.txtdivdept.setText((String) db.getResult()[0][4]);		  //Div/Dept
			
			
		}
	}
	
	
   public static void display_PO(DefaultTableModel model, JList rowHeader){
		
		FncTables.clearTable(model);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of listModel into rowHeader.
		
		//Individual display of assets
		String strSQL = "select (case when item_received is null then false else item_received end ) as item_received, a.supplier, a.po_no, a.item_id, a.description, a.unit,a.quantity, a.unit_price, b.drf_no, b.dr_id \n" + 
				"from rf_purchase_order  a\n" + 
				"left join rf_receiving_purchase b on a.po_no=b.po_no and a.item_id=b.item_id\n"+
				"where a.po_no='"+po_no+"' and b.dr_id='"+dr_id+"' and  item_received='t'\n" + 
				" ";
		
		FncSystem.out("display_PO", strSQL);	
		 
		pgSelect db = new pgSelect();
		db.select(strSQL);

		if(db.isNotNull()){
			
			for(int x=0; x < db.getRowCount(); x++)
			{
				//You can only use this kind of adding row in model when you're query and model has the same and exact unmber of columns and column types.
				model.addRow(db.getResult()[x]);
				
				//For every row added in model, the table header will also add the row number.
				listModel.addElement(model.getRowCount());
			}
		}
	}
   
   public static void cleartab_payment_processing() {
	   lookuppo_no.setValue("");
	   date.setDate(null);
	   txtstatus.setText("");
	   procurement.lookupcompany.setValue("");
	   procurement.lookuprequester.setValue("");
	   procurement.txtcompany.setText("");
	   procurement.txtrequester_name.setText("");
	   procurement.txtdivdept.setText("");
	   FncTables.clearTable(modelpymt_processing);
   }
   
   public static void cleartable_rowheader() {
		 
		FncTables.clearTable(modelpymt_processing);//Code to clear model.
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderpymt_processing.setModel(listModel);//Setting of listModel into rowHeader.
	}

}
