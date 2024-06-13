package Dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.lowagie.text.Jpeg;

import Database.pgSelect;
import Functions.FncTables;
import Utilities.EditRevolvingFunds;
import components._JDialog;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_dlg_edit_revolving_funds;

public class dlg_Edit_Revolving_Funds extends _JDialog implements _GUI {

	private static final long serialVersionUID = -5077040598101612043L;	
	private static Dimension size = new Dimension(280,300);
	private static String Title = "Rplf No.";
	private static JPanel pnlMain;
	private static JTextField txtSearch;
	private static JButton btnSearch;
	private static model_dlg_edit_revolving_funds modelRevolving;
	private static _JTableMain tblRplfNo;
	private static ArrayList<String> arrayRplfNo;


	public dlg_Edit_Revolving_Funds(Frame owner){
		super(owner);
		initGUI();
	}
	public void initGUI() {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setLayout(new BorderLayout(5,5));


		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain,BorderLayout.CENTER);
			pnlMain.setBorder(new EmptyBorder(5,5,5,5));

			{
				JPanel pnlMainNorth = new JPanel(new BorderLayout(5,5));
				pnlMain.add(pnlMainNorth,BorderLayout.NORTH);
				{
					JPanel pnlNorthStart = new JPanel(new BorderLayout(5,5));
					pnlMainNorth.add(pnlNorthStart,BorderLayout.LINE_START);
					{
						JLabel lblRplf = new JLabel("Rplf No.");
						pnlNorthStart.add(lblRplf);
					}
				}
				{
					JPanel pnlNorthCenter = new JPanel(new BorderLayout(5,5));
					pnlMainNorth.add(pnlNorthCenter,BorderLayout.CENTER);
					{
						txtSearch = new JTextField();
						pnlNorthCenter.add(txtSearch);
						txtSearch.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent e) {
								checkRplfNo();
							}
						});
					}
				}


			}
			{
				JScrollPane scrollRplf = new JScrollPane();
				pnlMain.add(scrollRplf,BorderLayout.CENTER);

				{
					modelRevolving = new model_dlg_edit_revolving_funds();
					tblRplfNo = new _JTableMain(modelRevolving);
					scrollRplf.setViewportView(tblRplfNo);

					tblRplfNo.getColumnModel().getColumn(1).setPreferredWidth(200);

				}
			}
			{
				JPanel pnlMainSouth = new JPanel(new GridLayout(0, 2, 3, 3));
				pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
				pnlMainSouth.setPreferredSize(new Dimension(0,30));
				{
					JButton btnConfirm = new JButton("Confirm");
					pnlMainSouth.add(btnConfirm);
					btnConfirm.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							saveRplf();					
							}
					});
				}
				{
					JButton btnCancel = new JButton("Cancel");
					pnlMainSouth.add(btnCancel);
					btnCancel.addActionListener(new ActionListener() {
						
						
						public void actionPerformed(ActionEvent e) {
							EditRevolvingFunds.disposable();
						}
					});
				}
			}
		}
		displayTable(modelRevolving,null);

	}
	public void displayTable(DefaultTableModel modelRevolving,JList rowheader) {
		FncTables.clearTable(modelRevolving);
		pgSelect db = new pgSelect();
		db.select("select false,rplf_no from rf_request_header  where rplf_type_id  = '06' and status_id = 'A' order by rplf_no");
		if(db.isNotNull()) {
			for(int x = 0;x<db.getRowCount();x++) {
				modelRevolving.addRow(db.getResult()[x]);

			}
		}
	}
	public void checkRplfNo() {
		int rw = tblRplfNo.getModel().getRowCount();	
		int x = 0;

		while (x < rw) {			

			String name = "";

			try { name	= tblRplfNo.getValueAt(x,1).toString();}
			catch (NullPointerException e) { name	= ""; }

			String acct_name	= txtSearch.getText();	
			Boolean	start		= name.contains(acct_name);


			if (start==true) {
				tblRplfNo.setRowSelectionInterval(x, x); 
				tblRplfNo.changeSelection(x, 1, false, false);				
				break;			
			}
			else {
				tblRplfNo.setRowSelectionInterval(0, 0); 
				tblRplfNo.changeSelection(0, 1, false, false);					
			}

			x++;

		}	
	}
	public void saveRplf() {
		arrayRplfNo = new ArrayList<String>();
		for(int x=0;x<modelRevolving.getRowCount();x++) {
			Boolean isSelected = (Boolean) modelRevolving.getValueAt(x, 0);
			if(isSelected) {
				String rplfNo = (String) modelRevolving.getValueAt(x, 1);
				arrayRplfNo.add(String.format("%s", rplfNo));
				
				
			}
		}
		String rplf2 = arrayRplfNo.toString().replaceAll("\\[", "").replaceAll("\\]","");
		EditRevolvingFunds.txtRplfNo.setText(rplf2);
		EditRevolvingFunds.disposable();
	}

}
