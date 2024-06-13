package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Database.pgSelect;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class PCost_Update extends _JInternalFrame implements _GUI {

	private static final long serialVersionUID = -6517018477299344044L;

	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlNorth1;
	JPanel pnlSouth;

	_JLookup lookupCheckNo;
	_JLookup lookupBatchNo;
	_JLookup lookupCompany1;
	_JLookup lookupProject1;
	_JLookup lookupBatch;
	_JLookup lookupBatch1;
	_JLookup lookupCompany;


	JButton btnUpdate;
	JButton btnCancel;

	JTextField txtCompany;
	JTextField txtProject;
	JTextField txtCompany1;
	JTextField txtProject1;

	JTabbedPane tabNorth;

	JComboBox cmbDeleteBy;
	JComboBox cmbDeleteBy1;

	static String title = "PCost Update RPLF";
//	Dimension frameSize = new Dimension(300, 150);// 510, 250
	Dimension frameSize = new Dimension(360, 180);// 510, 250

	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public PCost_Update() {
		super(title, false, true, false, true);
		initGUI();
	}

	public PCost_Update(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public PCost_Update(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}
	
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(10, 10));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JPanel pnlWest1 = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlWest1, BorderLayout.CENTER);
				//pnlWest1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					JPanel pnlLabel = new JPanel(new GridLayout(3, 0, 5, 5));
					pnlWest1.add(pnlLabel, BorderLayout.WEST);
						{
							JLabel lblCo_id = new JLabel("Company:");
							pnlLabel.add(lblCo_id);
							lblCo_id.setHorizontalAlignment(JLabel.RIGHT);
						}
						{
							JLabel lblCheckNo = new JLabel("Check No:");
							pnlLabel.add(lblCheckNo);
							lblCheckNo.setHorizontalAlignment(JLabel.RIGHT);
						}
						{
							JLabel lblBatchNo = new JLabel("Batch No:");
							pnlLabel.add(lblBatchNo);
							lblBatchNo.setHorizontalAlignment(JLabel.RIGHT);
						}
				}
				{
					JPanel pnlText = new JPanel(new GridLayout(3, 0, 5, 5));
					pnlWest1.add(pnlText, BorderLayout.CENTER);
					{
						lookupCompany = new _JLookup(null, "Company");
						pnlText.add(lookupCompany, BorderLayout.WEST);
						lookupCompany.setLookupSQL(company_id());
						lookupCompany.setReturnColumn(0);
						//lookupCheckNo.setPreferredSize(new Dimension(50, 20));
						lookupCompany.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									String co_id = (String) data[0];
									lookupCheckNo.setLookupSQL(SQL_CHECK_NO(co_id));
									
								}
							}
						});
					}
					{
						lookupCheckNo = new _JLookup(null, "Company");
						pnlText.add(lookupCheckNo, BorderLayout.WEST);
//						lookupCheckNo.setLookupSQL(SQL_CHECK_NO());
						lookupCheckNo.setReturnColumn(0);
						//lookupCheckNo.setPreferredSize(new Dimension(50, 20));
						lookupCheckNo.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									
								}
							}
						});
					}
					{
						lookupBatchNo = new _JLookup(null, "Project", "Please select Company.");
						pnlText.add(lookupBatchNo, BorderLayout.WEST);
						lookupBatchNo.setLookupSQL(SQL_BATCH());
						lookupBatchNo.setReturnColumn(0);
						//lookupBatchNo.setPreferredSize(new Dimension(50, 20));
						lookupBatchNo.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									btnUpdate.setEnabled(true);
								}
							}
						});
					}
				}
			}

			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 4, 3, 3));
				pnlSouth.setPreferredSize(new Dimension(500, 50));// XXX
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					/*pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());*/
				}
				{
					btnUpdate = new JButton("Update");
					pnlSouth.add(btnUpdate);
					btnUpdate.setActionCommand("Update");
					btnUpdate.setMnemonic(KeyEvent.VK_P);
					btnUpdate.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
			}
		}
		btnUpdate.setEnabled(false);
	}
	
	private Boolean toSave(){
		
		if(lookupCheckNo.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Select Check No", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(lookupBatchNo.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Select Batch No", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Update")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					String check_no = lookupCheckNo.getValue();
					String batch_no = lookupBatchNo.getValue();
					
					String SQL = "SELECT sp_update_pcost_rplf_v2('"+check_no+"', '"+batch_no+"','"+lookupCompany.getValue()+"')";
					pgSelect db = new pgSelect();
					db.select(SQL);
					
					lookupCheckNo.setValue(null);
					lookupBatchNo.setValue(null);
					btnUpdate.setEnabled(false);
					
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "PCost Updated");
					
				}
			}
		}
		
		if(actionCommand.equals("Cancel")){
			lookupCompany.setValue(null);
			lookupCheckNo.setValue(null);
			lookupBatchNo.setValue(null);
			btnUpdate.setEnabled(false);
		}
		
	}
	
	private static String SQL_BATCH() {
		return "SELECT batch_no as \"Batch No.\"\n" + 
				"FROM rf_processing_cost \n" +
				//"LEFT JOIN rf_request_header b ON a.rplf_no = b.rplf_no AND a.rplf_no not in (select rplf_no from rf_pv_header where status_id != 'I') \n" +
				"WHERE status_id = 'A'\n" +
				"AND rplf_no = ''\n" +
				"Group by batch_no \n" +
				"ORDER BY batch_no DESC;";
	}
	
	private String SQL_CHECK_NO(String co_id){
		return "SELECT check_no as \"Check No.\" FROM rf_check WHERE status_id = 'A' and co_id = '"+co_id+"'\n";
	}
	private String company_id(){
		return "SELECT  co_id as \"Co. ID\" ,company_name \"Company Name\" from mf_company where co_id in('01','02') order by co_id\n";
	}
	
}