package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class NTP_Notes extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -7187752044266149014L;
	private static	String title = "NTP Notes";
	Dimension frame = new Dimension(500,300);

	JPanel pnlMain;
	_JLookup lookupNtp;
	JLabel lblNtp;
	JTextArea txtAreaNtp;
	JButton btnEdit,btnSave,btnCancel;
	String contract_no = "";


	public NTP_Notes (){
		super(title,false, true,false, true);
		initGUI();
	}


	public void initGUI() {
		this.setPreferredSize(frame);
		this.setSize(frame);

		pnlMain = new JPanel(new BorderLayout(5,5));
		this.add(pnlMain,BorderLayout.CENTER);
		pnlMain.setBorder(new EmptyBorder(5,5,5,5));
		{
			JPanel pnlMainNorth = new JPanel(new BorderLayout(5,5));
			pnlMain.add(pnlMainNorth,BorderLayout.NORTH);
						pnlMainNorth.setPreferredSize(new Dimension(0,20));
			//			pnlMainNorth.setPreferredSize(new Dimension(0,80));
			{
				JPanel pnlNorth = new JPanel(new BorderLayout(5,5));
				pnlMainNorth.add(pnlNorth);
				{
					JPanel pnlNorthWest = new JPanel(new BorderLayout(5,5));
					pnlNorth.add(pnlNorthWest,BorderLayout.WEST);

					lookupNtp = new _JLookup(null, "Ntp_no", 0);
					pnlNorthWest.add(lookupNtp);
					lookupNtp.setPreferredSize(new Dimension(60,60));
					lookupNtp.setLookupSQL(lookNtpSql());
					lookupNtp.addLookupListener(new LookupListener() {
						
						
						public void lookupPerformed(LookupEvent event) {
							Object [] data =((_JLookup )event.getSource()).getDataSet();
							
							if(data != null){
								contract_no = (String) data[1];
								
								lblNtp.setText(String.format("[%s]", contract_no ));
								ButtonState(1);

								editLookupValue();
								
							}
							
							
						}
					});

				}
				{
					JPanel pnlNorthCenter = new JPanel(new BorderLayout(5,5));
					pnlNorth.add(pnlNorthCenter,BorderLayout.CENTER);

					lblNtp = new JLabel("[ ]");
					pnlNorthCenter.add(lblNtp);

				}
			}
		}
		{
			JPanel pnlMainCenter = new JPanel(new BorderLayout(5,5));
			pnlMain.add(pnlMainCenter,BorderLayout.CENTER);
			pnlMainCenter.setPreferredSize(new Dimension(0,80));

			{
				JScrollPane scrollNtp = new JScrollPane();
				pnlMainCenter.add(scrollNtp);

				txtAreaNtp = new JTextArea();
				scrollNtp.setViewportView(txtAreaNtp);
				txtAreaNtp.setEnabled(false);
			}
		}
		{
			JPanel pnlMainSouth = new JPanel(new GridLayout(1,3,3,3));
			pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
			pnlMainSouth.setPreferredSize(new Dimension(0,30));

			btnEdit = new JButton("Edit");
			pnlMainSouth.add(btnEdit);
			btnEdit.setEnabled(false);
			btnEdit.addActionListener(new ActionListener() {
				
			
				public void actionPerformed(ActionEvent e) {
					ButtonState(2);
					
				}
			});

			btnSave = new JButton("Save");
			pnlMainSouth.add(btnSave);
			btnSave.setEnabled(false);
			btnSave.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to save it?", "Save",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION){
					saveEdit();
					JOptionPane.showMessageDialog(getContentPane(), "Succesfully Saved", "Save", JOptionPane.INFORMATION_MESSAGE);
					ButtonState(3);
					}
				}
			});

			btnCancel = new JButton("Cancel");
			pnlMainSouth.add(btnCancel);
			btnCancel.setEnabled(false);
			btnCancel.addActionListener(new ActionListener() {
				
				
				public void actionPerformed(ActionEvent e) {
					ButtonState(3);
					
				}
			});



		}





	}
	public String lookNtpSql(){
		return "SELECT TRIM(ntp_no) AS \"NTP No.\",TRIM(contract_no) AS \"Contract Number\" FROM co_ntp_header";
		
		
	}
	private void ButtonState(Integer state){
		
		if(state==1){//lookup
			btnEdit.setEnabled(true);
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
		}
		else if(state==2){//edit
			btnEdit.setEnabled(false);
			lookupNtp.setEnabled(false);
			txtAreaNtp.setEnabled(true);
			btnSave.setEnabled(true);
			btnCancel.setEnabled(true);
		}
		else if(state==3){//cancel and save
			lookupNtp.setEnabled(true);
			lookupNtp.setValue(null);
			lblNtp.setText("[ ]");
			btnEdit.setEnabled(false);
			txtAreaNtp.setEnabled(false);
			txtAreaNtp.setText("");
			btnSave.setEnabled(false);
			btnCancel.setEnabled(false);
			
		}
		
	}
	private void saveEdit(){
		String ntp_no = lookupNtp.getText();
		String remarks = txtAreaNtp.getText();
		pgSelect db = new pgSelect();
		System.out.println("lookup:"+ntp_no);
		System.out.println("txt:"+remarks);
		
		String query = "SELECT sp_save_ntp_notes('"+ntp_no+"','"+contract_no+"','"+UserInfo.Department+"','"+remarks+"','"+UserInfo.EmployeeCode+"')";
		db.select(query);
	}
	private void editLookupValue(){
		pgSelect db = new pgSelect();
		String ntp_no = lookupNtp.getText();
		
		String query = "SELECT COALESCE(remarks,'') FROM rf_ntp_notes WHERE ntp_no = '"+ntp_no+"' and dept_code = '"+UserInfo.Department+"'";
		db.select(query);
		
		if(db.isNotNull()){
		
		txtAreaNtp.setText((String)db.getResult()[0][0]);
		} else
		{
			txtAreaNtp.setText("");
		}
	}

}
