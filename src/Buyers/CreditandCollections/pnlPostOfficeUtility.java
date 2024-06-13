package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JButton;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_Post_Office_Utility;

@SuppressWarnings({"rawtypes","unchecked"})
public class pnlPostOfficeUtility extends JXPanel implements _GUI,MouseListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JXPanel pnlNorth;
	private JXPanel pnlBatch_West;
	private JXPanel pnlBatch;
	private JLabel lblBatch;
	private JLabel lblDate;
	private JLabel lblLocation;
	private JLabel lblReceiveby;
	private JXPanel pnlBatchAction_Center;
	private JXPanel pnlBatchAction;
	private _JLookup lookupBatchno;
	private _JDateChooser dteReceived;
	private JXTextField txtLocation;
	private JXTextField txtReceiveby;
	private JXPanel pnlCenter;
	private JXPanel pnlListBatch;
	private JScrollPane scrollListBatch;
	private model_Post_Office_Utility modelListBatchModel;
	private _JTableMain tblListBatch;
	private JList rowHeaderListBatch;
	private pgSelect db = new pgSelect();
	private boolean ifCheck_Accounts = false;
	private String received_by;
	private String batch_no;
	private JXPanel pnlSouth;
	private JPanel pnlButton;
	private _JButton btnSave;
	private _JButton btnTagSent;
	private _JButton btnCancel;
	private String entityID;
	private String pbl_id;
	private JRadioButton rbtnTypeMail;
	/**
	 * 
	 */
	

	public pnlPostOfficeUtility(Transmittal trans) {
		initGUI();
	}

	public pnlPostOfficeUtility(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlPostOfficeUtility(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlPostOfficeUtility(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {

		this.setLayout(new BorderLayout());
		{

			JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JXPanel();
				pnlMain.add(pnlNorth,BorderLayout.NORTH);
				pnlNorth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlNorth.setLayout(new BorderLayout(5, 5));
				pnlNorth.setPreferredSize(new Dimension(this.getWidth(), 100));

				{
					pnlBatch_West = new JXPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlBatch_West,BorderLayout.WEST);
					pnlBatch_West.setPreferredSize(new Dimension(90, 100));

					{
						pnlBatch = new JXPanel(new GridLayout(4, 1, 3, 3));
						pnlBatch_West.add(pnlBatch,BorderLayout.WEST);
						{
							lblBatch = new JLabel("Batch No.:");
							pnlBatch.add(lblBatch);

							lblDate = new JLabel("Date :");
							pnlBatch.add(lblDate);

							lblLocation = new JLabel("Location :");
							pnlBatch.add(lblLocation);

							lblReceiveby = new JLabel("Type of Mail :");
							pnlBatch.add(lblReceiveby);
						}

					}
				}
				{
					pnlBatchAction_Center = new JXPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlBatchAction_Center,BorderLayout.CENTER);
					{

						pnlBatchAction = new JXPanel(new GridLayout(4, 1, 3, 3));
						pnlBatchAction_Center.add(pnlBatchAction,BorderLayout.WEST);
						pnlBatchAction.setPreferredSize(new Dimension(300, 100));
						{
							lookupBatchno = new _JLookup("Batch No.", "Batch No.", 0);
							pnlBatchAction.add(lookupBatchno);
							lookupBatchno.setLookupSQL("\n" + 
									"SELECT DISTINCT a.batch_no AS \"Batch No.\" FROM rf_notices_sent a\n" + 
									"left join rf_client_notices b on a.batch_no = b.batch_no\n" + 
									"WHERE a.sent_to = 'POST OFFICE' \n" + 
									"and a.status_id = 'A'\n" 
									/*"and b.received_date is null"*/);
							lookupBatchno.addLookupListener(new LookupListener() {


								@Override
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup)e.getSource()).getDataSet();

									if(data != null){
										batch_no = data[0].toString();

										new Thread(new Generate()).start();

										Check_Acccounts(batch_no);
										
										btnSave.setEnabled(true);
										btnTagSent.setEnabled(true);
									}
								}
							});
						}
						{
							dteReceived = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlBatchAction.add(dteReceived);
							dteReceived.setDate(dateFormat(getDateSQL()));
						}
						{
							txtLocation = new JXTextField("Location");
							pnlBatchAction.add(txtLocation);

						}
						
						{
							JPanel pnlMail = new JPanel(new GridLayout(1, 2, 3, 3));
							pnlBatchAction.add(pnlMail);
							{
								{
									txtReceiveby = new JXTextField("Type of Mail");
									pnlMail.add(txtReceiveby);
								}
								{
									rbtnTypeMail = new JRadioButton("Registered Mail");
									pnlMail.add(rbtnTypeMail);
									rbtnTypeMail.setActionCommand("Mail");
									rbtnTypeMail.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {
											if (rbtnTypeMail.isSelected()) {
												txtReceiveby.setEnabled(false);	
												txtReceiveby.setText("Registered Mail");
											}else{
												txtReceiveby.setEnabled(true);
												txtReceiveby.setText("");
											}
										}
									});
								}
								
							}
						}
						
					}
				}

			}//pnlNorth
			{
				pnlCenter = new JXPanel();
				pnlMain.add(pnlCenter,BorderLayout.CENTER);
				pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlCenter.setLayout(new BorderLayout(5, 5));

				{
					pnlListBatch = new JXPanel();
					pnlCenter.add(pnlListBatch,BorderLayout.CENTER);
					pnlListBatch.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlListBatch.setLayout(new BorderLayout(3,3));
					pnlListBatch.setPreferredSize(new Dimension(this.getWidth(), 300));
					pnlListBatch.setBorder(components.JTBorderFactory.createTitleBorder("List of Batch"));
					{

						scrollListBatch = new JScrollPane();
						pnlListBatch.add(scrollListBatch, BorderLayout.CENTER);
						scrollListBatch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollListBatch.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								tblListBatch.clearSelection();
							}
						});
						{

							modelListBatchModel = new model_Post_Office_Utility();
							modelListBatchModel.addTableModelListener(new TableModelListener() {
								
								public void tableChanged(TableModelEvent e) {
									if(e.getType() == TableModelEvent.DELETE){
										rowHeaderListBatch.setModel(new DefaultListModel());
									}
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderListBatch.getModel()).addElement(modelListBatchModel.getRowCount());
									}
								}
							});

							tblListBatch = new _JTableMain(modelListBatchModel);
							scrollListBatch.setViewportView(tblListBatch);
							modelListBatchModel.setEditable(false);
							tblListBatch.setHorizontalScrollEnabled(true);
							tblListBatch.addMouseListener(this);
							tblListBatch.packAll();

							tblListBatch.hideColumns("EntityID,","PBLID","SeqNo","EntityID");

							/** Repaint for Highlight **/
							tblListBatch.getTableHeader().addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									tblListBatch.repaint();
								}
							});
						}
						{
							rowHeaderListBatch = tblListBatch.getRowHeader();
							rowHeaderListBatch.setModel(new DefaultListModel());
							scrollListBatch.setRowHeaderView(rowHeaderListBatch);
							scrollListBatch.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
				}

			}
			{


				pnlSouth = new JXPanel();
				pnlMain.add(pnlSouth,BorderLayout.SOUTH);
				pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlSouth.setLayout(new BorderLayout(3,3));
				pnlSouth.setPreferredSize(new Dimension(100, 50));
				{
					pnlButton = new  JPanel(new GridLayout(1, 5, 5, 5));
					pnlSouth.add(pnlButton,BorderLayout.EAST);
					pnlButton.setPreferredSize(new Dimension(300, 50));
					{
						btnSave = new _JButton("Save");
						pnlButton.add(btnSave);
						btnSave.addActionListener(this);
					}
					{
						btnTagSent = new _JButton("Send");
						pnlButton.add(btnTagSent);
						btnTagSent.setActionCommand("Send");
						btnTagSent.addActionListener(this);
					}
					{
						btnCancel = new _JButton("Cancel");
						pnlButton.add(btnCancel);
						btnCancel.addActionListener(this);
					}
				}
			
			}
		}//pnlMain

	
		
		
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String actionCommand = e.getActionCommand();

		if (e.getSource().equals(btnSave)) {
			String location = txtLocation.getText().toString().toUpperCase();
			String receivedby = txtReceiveby.getText().toString().toUpperCase();
			pgUpdate pU = new pgUpdate();

			if (location.isEmpty()) {
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please input a location", "Save", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (receivedby.isEmpty()) {
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please input a received by", "Save", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (hasSelectedTagged()) {

				//if (ifCheck_Accounts == false){
					if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are you sure you want to save this batch no.?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						for (int i = 0; i < modelListBatchModel.getRowCount(); i++) {
							Boolean isSelected = (Boolean) modelListBatchModel.getValueAt(i, 0);

							entityID = modelListBatchModel.getValueAt(i, 4).toString();
							pbl_id = modelListBatchModel.getValueAt(i, 5).toString();
							Integer seq_no = (Integer) modelListBatchModel.getValueAt(i, 6);

							if (isSelected) {

								pU.executeUpdate("UPDATE rf_client_notices SET received_date = '"+dteReceived.getDate()+"', \n"	+
										"relationtobuyer = '"+location+"', \n"	+ 
										"receivedby = '"+receivedby+"' \n" + 
										"WHERE batch_no = '"+batch_no+"' \n" + 
										"AND entity_id = '"+entityID+"' \n" + 
										"AND pbl_id = '"+pbl_id+"' \n"	+ 
										"AND seq_no = "+seq_no+"", true);
							}
						}
						
						db.select("SELECT sp_mf_audit_trail('"+UserInfo.EmployeeCode+"','"+batch_no+"')");
					}
					
					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Successfully Saved ", "Save", JOptionPane.INFORMATION_MESSAGE);
					pU.commit();
					btnSave.setEnabled(false);
					
				/*}else{

					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), " Batch is already tagged for " + received_by, "Save", JOptionPane.INFORMATION_MESSAGE);
				}*/

			}else{
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select client first to save", "Save", JOptionPane.WARNING_MESSAGE);
			}
			
		}//btnSave
		
		if(actionCommand.equals("Send")){
			pgUpdate dbUpdate = new pgUpdate();
			dbUpdate.executeUpdate("UPDATE rf_client_notices SET datesent = '"+dteReceived.getDate()+"' WHERE batch_no = '"+lookupBatchno.getValue()+"'", true);
			dbUpdate.commit();
			
			JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Successfully Saved", "Save", JOptionPane.INFORMATION_MESSAGE);
			btnTagSent.setEnabled(false);
		}
		
		if (e.getSource().equals(btnCancel)) {
			Cancel_Process();
		}
		
	}

	private  String getDateSQL(){

		db.select("SELECT CURRENT_DATE");
		return db.Result[0][0].toString();
		
	}
	
	private void Check_Acccounts(String batch_no){

		db.select("SELECT batch_no, NULLIF(receivedby,'') FROM rf_client_notices WHERE batch_no = '"+batch_no+"' AND status_id = 'A' AND NULLIF(receivedby,'') <> ''");

		if (db.isNull()) {
			ifCheck_Accounts = false;
		}else{
			ifCheck_Accounts = true;
			received_by = db.Result[0][1].toString().toUpperCase();

		}
		System.out.println("Check :" + ifCheck_Accounts);
	}
	
	public Date dateFormat(String dates){

		DateFormat formatter ; 
		Date date = null ;

		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = (Date)formatter.parse(dates);
		} catch (ParseException e) {
		} 

		return date;
	}
	
	public class Generate implements Runnable{

		@Override
		public void run() {
			FncGlobal.startProgress("Generate...Please wait");
			
			rowHeaderListBatch.setModel(new DefaultListModel());
			
			generateListClient(modelListBatchModel,batch_no);
			scrollListBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblListBatch.getRowCount())));
			tblListBatch.packAll();

			FncGlobal.stopProgress();
		} // XXX Generate_Batch_Tagged

	}
	
	private void generateListClient(model_Post_Office_Utility model,String batchNo){
		pgSelect db = new pgSelect();
		model.clear();

		String SQL = "";

		SQL = "\n" + 
				"SELECT true, \n" + 
				"get_client_name(entity_id),\n" + 
				"get_project_alias(projcode),  \n" + 
				"get_unit_description(projcode,pbl_id),\n" + 
				"entity_id,\n" + 
				"pbl_id,\n" + 
				"seq_no\n" + 
				"FROM rf_client_notices \n" + 
				"WHERE batch_no = '"+batchNo+"'\n" + 
				"AND status_id = 'A'\n" + 
				"ORDER BY get_client_name(entity_id)";

		FncSystem.out("Client Schedule", SQL);
		db.select(SQL);

		if(db.isNotNull()){
			ArrayList<Object[]> listData = new ArrayList<Object[]>();
			for(int x=0; x<db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
				listData.add(db.getResult()[x]);
			}
		}else{

			JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Records to Show!", "Empty Resultset", JOptionPane.INFORMATION_MESSAGE);
		}
		tblListBatch.packAll();

	}
	
	private Boolean hasSelectedTagged() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < modelListBatchModel.getRowCount(); x++){
			listSelected.add((Boolean) modelListBatchModel.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}
	
private void Cancel_Process(){
		
		lookupBatchno.setValue("");
		txtLocation.setText("");
		txtReceiveby.setText("");
		rbtnTypeMail.setSelected(false);
		dteReceived.setDate(dateFormat(getDateSQL()));
		modelListBatchModel.clear();
		btnSave.setEnabled(true);
		rowHeaderListBatch.setModel(new DefaultListModel());
		scrollListBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblListBatch.getRowCount())));
		tblListBatch.packAll();

	}
}
