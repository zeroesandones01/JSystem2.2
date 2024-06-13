package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.poi.ss.formula.ptg.TblPtg;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncTables;
import Functions.UserInfo;
import System.tablemodelTransferJournalEntries;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import tablemodel.modelInactivateasset;

public class InactivateAssets extends _JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String title = "Inactivate Assets";
	public static Dimension framesize = new Dimension(700, 600);

	private JPanel panelmain;

	private JButton btnsave;

	private JButton btncancel;

	private static modelInactivateasset modelInactivestatus;

	private static _JTableMain tblinactivestatus;

	private static JList rowheaderinactivestatus;
	
	public InactivateAssets(){
		super(title, false, true, true, true);
		initGUI();
	}

	public InactivateAssets (String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public InactivateAssets (String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	
	public void initGUI() {
		this.setTitle(title);
		this.setSize(framesize);
		this.setPreferredSize(framesize);
		this.setLayout(new BorderLayout());
		
		{
			panelmain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(panelmain);
			panelmain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlcenter = new JPanel(new BorderLayout(5, 5));
				panelmain.add(pnlcenter, BorderLayout.CENTER);
				pnlcenter.setBorder(JTBorderFactory.createTitleBorder(" List Of Active Assets"));
				
				{
					JScrollPane mainscroll = new JScrollPane();
					pnlcenter.add(mainscroll, BorderLayout.CENTER);
					mainscroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					{
						modelInactivestatus = new modelInactivateasset();
						tblinactivestatus = new _JTableMain(modelInactivestatus);
						mainscroll.setViewportView(tblinactivestatus);
						tblinactivestatus.getTableHeader().setEnabled(false);
						tblinactivestatus.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								btnsave.setEnabled(true);
								btncancel.setEnabled(true);
								
							}
						});
						
						tblinactivestatus.getColumnModel().getColumn(0).setPreferredWidth(50);
						tblinactivestatus.getColumnModel().getColumn(1).setPreferredWidth(80);
						tblinactivestatus.getColumnModel().getColumn(2).setPreferredWidth(130);
						tblinactivestatus.getColumnModel().getColumn(3).setPreferredWidth(250);
						tblinactivestatus.getColumnModel().getColumn(4).setPreferredWidth(250);
						
					}
					{
						rowheaderinactivestatus = tblinactivestatus.getRowHeader();
						mainscroll.setRowHeaderView(rowheaderinactivestatus);
						mainscroll.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				JPanel pnlsouth = new JPanel(new GridLayout(1, 4, 5, 5));
				panelmain.add(pnlsouth, BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 30));
				{
					JLabel label = new JLabel("");
					pnlsouth.add(label);
				}
				{
					btnsave = new JButton("Save");
					pnlsouth.add(btnsave);
					btnsave.setEnabled(false);
					btnsave.setActionCommand("save");
					//btnsave.addActionListener(this);
					btnsave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if( JOptionPane.showConfirmDialog(getTopLevelAncestor(), "Inactivate selected asset?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
								
								System.out.println("Dumaan dito sa save");
								inactivate_asset_status();
								JOptionPane.showMessageDialog(getTopLevelAncestor(), "Update done!", "Save", JOptionPane.PLAIN_MESSAGE);
								displayactive();
								btnsave.setEnabled(false);
								btncancel.setEnabled(true);
							}
						}
					});
				}
				{
					btncancel = new JButton("Cancel");
					pnlsouth.add(btncancel);
					btncancel.setEnabled(false);
					btncancel.setActionCommand("cancel");
					//btncancel.addActionListener(this);
					btncancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							System.out.println("Dumaan dito cancel");
							modelInactivestatus.clear();
							displayactive();
							btnsave.setEnabled(false);
						}
					});
				}
				{
					JLabel label = new JLabel("");
					pnlsouth.add(label);
				}
			}
			displayactive();
			btncancel.setEnabled(true);
		}
	}
	
	public static void displayactive() {
		modelInactivestatus.clear();
		DefaultListModel listModel = new DefaultListModel();//Creating listModel for rowHeader.
		rowheaderinactivestatus.setModel(listModel);//Setting of listModel into rowHeader.
		
		String Strsql = "select \n" + 
				"false,\n" + 
				"a.asset_no,\n" + 
				"a.asset_code,\n" + 
				"a.asset_name,\n" + 
				"c.entity_name\n" + 
				"\n" + 
				"from tbl_asset a\n" + 
				"left join em_employee b on a.current_cust = b.emp_code::int\n" + 
				"left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"where status = 'A'\n" + 
				//"and date_scanned is null \n //Comment by Erick 2023-08-24 to display all assets scanned and not scanned.
				"order by a.asset_no";
		
		System.out.println("***");
		System.out.println("displayactive: "+ Strsql);
		pgSelect db = new pgSelect();
		db.select(Strsql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelInactivestatus.addRow(db.getResult()[x]);
				listModel.addElement(modelInactivestatus.getRowCount());
			}
		}
	}
	
	public static void inactivate_asset_status() {
		pgUpdate db = new pgUpdate();
		
		for( int x = 0; x < tblinactivestatus.getRowCount(); x++ ) {
			if(new Boolean( tblinactivestatus.getValueAt(x, 0).toString())) {
				
				String sql1 = "update tbl_asset set status = 'I', date_edited = current_date, edited_by = '" +UserInfo.EmployeeCode + "' where asset_no = '"+tblinactivestatus.getValueAt(x, 1)+"' and status = 'A'" ;
				System.out.println("***");
				System.out.println("update tbl_asset stattus: "+sql1);
				db.executeUpdate(sql1, false);
				
				String sql2 = "INSERT INTO tbl_asset_history( \n" +
						"asset_code, \n" +
						"prev_cust, \n" +
						"current_cust, \n" +
						"trans_date, \n" +
						"reason, \n" +
						"remarks, \n" +
						"status, \n" +
						"move_no, \n" +
						"asset_no, \n" +
						"trans_by) \n" +
						"VALUES (\n" +
						"'"+tblinactivestatus.getValueAt(x, 2)+"', \n" +//asset_code
						"(select current_cust from tbl_asset where asset_no ="+tblinactivestatus.getValueAt(x, 1)+"), \n" +//prev_cust
						"(select current_cust from tbl_asset where asset_no ="+tblinactivestatus.getValueAt(x, 1)+"), \n" +//current_cust
						"current_date, \n" +//trans_date
						"'INACTIVATE ASSET', \n" +//reason
						"'Inactivated by "+UserInfo.FullName+"', \n" +//remarks
						"'I', \n" +//status
						"(select coalesce(max(move_no),0) + 1 from tbl_asset_history), \n" +//move_no
						""+tblinactivestatus.getValueAt(x, 1)+", \n" +//asset_no
						"'" +UserInfo.EmployeeCode + "') \n";//trans_by
				
				System.out.println("***");
				System.out.println("INSERT INTO tbl_asset_history: "+sql2);
				db.executeUpdate(sql2, false);
			}
		}
		db.commit();
	}
}
