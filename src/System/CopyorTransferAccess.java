package System;

//IMPORTING OF OBJECTS
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;

public class CopyorTransferAccess extends _JInternalFrame  {

	private static final long serialVersionUID = 824891899673760998L;
	static String title = "Copy or Transfer Access";
	Dimension frameSize = new Dimension(400,120);

	//DECLARATION OF OBJECTS
	private static JButton btnCopy;
	private static JButton btnTransfer;
	private static _JLookup lookupEmpFrom;
	private static _JLookup lookupEmpTo;
	private static  JLabel lblEmpFrom,lblEmpTo ;

	public CopyorTransferAccess() {
		super(title, false, true, false, true);
		initGUI(); 
	}

	public CopyorTransferAccess(String title) {
		super(title);
		initGUI(); 
	}

	public CopyorTransferAccess(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	//START OF INIT GUI
	public void initGUI() {

		this.setTitle(title);
		this.setSize(frameSize);

		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JPanel pnlCenter = new JPanel(new GridLayout(2, 1, 3, 3));
			panMain.add(pnlCenter,BorderLayout.CENTER);
			{
				JPanel pnlFrom = new JPanel(new BorderLayout(5,5));
				pnlCenter.add(pnlFrom);
				{
					JLabel lblFrom = new JLabel("Access From:");
					pnlFrom.add(lblFrom,BorderLayout.WEST);
				}
				{
					JPanel pnlFrom2 = new JPanel(new BorderLayout(5,5));
					pnlFrom.add(pnlFrom2,BorderLayout.CENTER);
					{
						lookupEmpFrom = new _JLookup(null,"Employee From");
						pnlFrom2.add(lookupEmpFrom,BorderLayout.WEST);
						lookupEmpFrom.setPreferredSize(new Dimension(60,0));
						lookupEmpFrom.setEnabled(true);
						lookupEmpFrom.setLookupSQL(employeeList());
						lookupEmpFrom.setFilterIndex(1);
						lookupEmpFrom.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if(data != null){
									lookupEmpFrom.setValue((String) data[0]);
									lblEmpFrom.setText(String.format("[%s]", (String) data[1]));							

								}
							}
						});
					}
					{
						lblEmpFrom = new JLabel("[]");
						pnlFrom2.add(lblEmpFrom,BorderLayout.CENTER);
					}
				}
			}
			{

				JPanel pnlTo = new JPanel(new BorderLayout(5,5));
				pnlCenter.add(pnlTo);
				{
					JLabel lblTo = new JLabel("Access To:    ");
					pnlTo.add(lblTo,BorderLayout.WEST);
				}
				{
					JPanel pnlTo2 = new JPanel(new BorderLayout(5,5));
					pnlTo.add(pnlTo2,BorderLayout.CENTER);
					{

						lookupEmpTo = new _JLookup(null,"Employee To");
						lookupEmpTo.setPreferredSize(new Dimension(60,0));
						pnlTo2.add(lookupEmpTo,BorderLayout.WEST);
						lookupEmpTo.setEnabled(true);
						lookupEmpTo.setLookupSQL(employeeList());
						lookupEmpTo.setFilterIndex(1);
						lookupEmpTo.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();


								if(data != null){
									lookupEmpTo.setValue((String) data[0]);
									lblEmpTo.setText(String.format("[%s]", (String) data[1]));							

								}
							}

						});
					}
					{
						lblEmpTo = new JLabel("[]");
						pnlTo2.add(lblEmpTo,BorderLayout.CENTER);
					}
				}
			}
		}
		{
			JPanel pnlSouth = new JPanel(new GridLayout(1,2,3,3));
			panMain.add(pnlSouth,BorderLayout.SOUTH);
			{
				btnCopy = new JButton("Copy Access");
				pnlSouth.add(btnCopy);
				btnCopy.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						saveAccess();
					}
				});
			}
			{
				btnTransfer = new JButton("Transfer Access");
				pnlSouth.add(btnTransfer);
				btnTransfer.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {		
						transferAccess();
					}
				});
			}
		}
	}
	//END OF INIT GUI

	private static void saveAccess() {
		if(JOptionPane.showConfirmDialog(null, "Are you sure you want to copy access?", "Copy",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
			pgSelect db = new pgSelect();

			String query = "SELECT sp_save_access('copy','"+lookupEmpFrom.getValue()+"','"+lookupEmpTo.getValue()+"','"+UserInfo.EmployeeCode+"')";
			db.select(query);
			FncSystem.out("Display SQL for filter dept", query);

			JOptionPane.showMessageDialog(null,"Successfully Copied Access","Success",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//METHOD TO CALL TO TRANSFER ACCESS
	private static void transferAccess() {
		if(JOptionPane.showConfirmDialog(null, "Are you sure you want to transfer access?", "Transfer",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
			pgSelect db = new pgSelect();

			String query = "SELECT sp_save_access('transfer','"+lookupEmpFrom.getValue()+"','"+lookupEmpTo.getValue()+"','"+UserInfo.EmployeeCode+"')";
			db.select(query);
			FncSystem.out("Display SQL for filter dept", query);

			JOptionPane.showMessageDialog(null,"Succesfully Transferred Access","Success",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	//METHOD TO DISPLAY EMPLOYEE LIST
	public static String employeeList() {
		return "select a.emp_code,b.entity_name from em_employee a\n" + 
				"left join rf_entity b on a.entity_id = b.entity_id and b.status_id = 'A'\n" + 
				"where a.emp_status not in ('E','I')  \n" + 
				"order by b.entity_name";

	}
}
