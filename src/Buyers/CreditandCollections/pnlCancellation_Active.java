package Buyers.CreditandCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;

import Database.pgSelect;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JTabbedPane;

public class pnlCancellation_Active extends JPanel implements _GUI, ActionListener, LookupListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private _CancellationProcessing_New cP;
	private JXPanel pnlActiveNorth;
	private JLabel lblBuyerType;
	public DefaultComboBoxModel cmbBuyerTypeModel;
	public JComboBox cmbBuyerType;
	private JPanel pnlBatch;
	private JLabel lblBatchNo;
	public _JLookup lookupBatchNo;
	public _JTabbedPane tabpane_per_account;
	public static pnlPerBatch_Cancel pnlPerBatch;

	private _FCancellationProcessing functions= new _FCancellationProcessing();
	private String title;
	private String lblListof;
	public static pnlPerAccount pnlPerAccount;
	private String batch_no = "" ;
	
	public pnlCancellation_Active(_CancellationProcessing_New cP ) {
		this.cP = cP;
		initGUI();
	}

	public pnlCancellation_Active(LayoutManager layout) {
		super(layout);
	}

	public pnlCancellation_Active(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public pnlCancellation_Active(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	@Override
	public void initGUI() {

		this.setLayout(new BorderLayout(5,5));
		{
			pnlActiveNorth = new JXPanel(new BorderLayout(5,5));
			this.add(pnlActiveNorth,BorderLayout.NORTH);
			pnlActiveNorth.setPreferredSize(new Dimension(0, 30));
			{
				JPanel pnlNorth = new  JPanel(new BorderLayout(5, 5));
				pnlActiveNorth.add(pnlNorth,BorderLayout.NORTH);
			}
			{
				{

					JPanel  pnlCancelActive_North_East = new  JPanel(new  BorderLayout(5, 5));
					pnlActiveNorth.add(pnlCancelActive_North_East,BorderLayout.WEST);
					pnlCancelActive_North_East.setPreferredSize(new  Dimension(300, 60));
					{
						lblBuyerType = new JLabel(" Buyer Type :");
						pnlCancelActive_North_East.add(lblBuyerType,BorderLayout.WEST);
						lblBuyerType.setPreferredSize(new  Dimension(80, 60));
					}
					{
						cmbBuyerTypeModel = new DefaultComboBoxModel(new String[] {"All", "IHF Accounts", "Pag-Ibig Accounts" });
						cmbBuyerType = new JComboBox();
						pnlCancelActive_North_East.add(cmbBuyerType,BorderLayout.CENTER);
						cmbBuyerType.setModel(cmbBuyerTypeModel);
						cmbBuyerType.setPreferredSize(new Dimension(220, 25));
						cmbBuyerType.addActionListener(this);
					}
				}
			}
			{
				pnlBatch = new JPanel(new BorderLayout(5,5));
				pnlActiveNorth.add(pnlBatch,BorderLayout.EAST);
				pnlBatch.setPreferredSize(new Dimension(250, 20));
				{
					lblBatchNo = new  JLabel("Batch No :");
					pnlBatch.add(lblBatchNo,BorderLayout.WEST);
					//lblBatchNo.setHorizontalTextPosition(SwingConstants.EAST);
				}
				{
					lookupBatchNo = new _JLookup("Search Batch No", "Batch No", 0) ; 
					pnlBatch.add(lookupBatchNo,BorderLayout.CENTER);
					lookupBatchNo.addLookupListener(this);
					lookupBatchNo.setReturnColumn(0);
					lookupBatchNo.setLookupSQL(functions.listBatch());
					lookupBatchNo.setPreferredSize(new Dimension(60, 20));
				}
			}
		}
		{
			tabpane_per_account = new _JTabbedPane();
			this.add(tabpane_per_account,BorderLayout.CENTER);
			{
				pnlPerBatch = new pnlPerBatch_Cancel(this,"List of All Accounts");
				tabpane_per_account.addTab("Per Batch Accounts", pnlPerBatch);

			}
			{
				pnlPerAccount = new pnlPerAccount(this);
				tabpane_per_account.addTab("Per Account Accounts", pnlPerAccount);
			}

		}
		cP.setComponentsEditable(this, false);
		cP.setComponentsEnabled(this, false);
		setClientInformationEnabled(false);
	}//GUI

	@Override
	public void lookupPerformed(LookupEvent e) {
		
		if (e.getSource().equals(lookupBatchNo)) {
			
			
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){
				batch_no = data[0].toString();

				setBatch_no(batch_no);
				pnlPerBatch.btnState(true, true, false, true, false);
				new Thread(new Generate_Edit()).start();
				cmbBuyerType.setEnabled(true);
				lblBuyerType.setEnabled(true);
				pnlPerBatch.lblForTestingDate.setEnabled(true);
				pnlPerBatch.dteDueDate.setEnabled(true);
				pnlPerBatch.btnPreviewActive.setEnabled(true);
				pnlPerBatch.dteDueDate.setDate(dateFormat("2016-04-14"));
				
				tabpane_per_account.setSelectedIndex(0);
			
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(cmbBuyerType)) {
			
			if (cmbBuyerType.getSelectedIndex() == 0 ) {
				title ="List of All Accounts";
			}
			if(cmbBuyerType.getSelectedIndex() == 1 ){
				title ="List of IHF Accounts";
			}
			if(cmbBuyerType.getSelectedIndex() == 2 ){
				title ="List of Pag-ibig Accounts";
			}
		}
		pnlPerBatch.pnlCenterBatch.setBorder(components.JTBorderFactory.createTitleBorder(title));
		
	}
	
	public void setClientInformationEnabled(boolean enable) {
		for(Component panel : this.getComponents()){
			if(panel instanceof JPanel){
				for(Component comp : ((JPanel) panel).getComponents()){
					//if(comp instanceof JLabel == false){
					comp.setEnabled(enable);
					//}
				}
			}
		}
	}

	/**
	 * Added 2015-04-22 by Christian Paquibot
	 *
	 */
	
	public void setComponentsEditable(JPanel panel, boolean editable) {
		panelLooperEditable(panel, editable);
	}

	public void panelLooperEditable(Container panel, boolean editable) {
		
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				panelLooperEditable((JPanel) comp, editable);
			} else if (comp instanceof JScrollPane) {
				panelLooperEditable((JScrollPane) comp, editable);
			} else {
				if(comp instanceof JTextField){
					((JTextField)comp).setEditable(editable);
				}
			}
		}
	}
	/**
	 * Added 2015-04-22 by Christian Paquibot
	 *
	 */
	
	public void setComponentsEnabled(JPanel panel, boolean enable) {
		panelLooper(panel, enable);
	}

	public void panelLooper(Container panel, boolean enable) {
		for (Component comp : panel.getComponents()) { 
			if (comp instanceof JXTable) {
				return;
			}

			if (comp instanceof JPanel) {
				panelLooper((JPanel) comp, enable);
			} else if (comp instanceof JScrollPane) {
				panelLooper((JScrollPane) comp, enable);
			} else if (comp instanceof JTabbedPane) {
				//panelLooper((JTabbedPane) comp, enable);
			} else {
				if (comp.getName() != null) {
					comp.setEnabled(enable);
				} else {
					if (panel instanceof JScrollPane) {
						((JScrollPane) panel).getViewport().getView().setEnabled(enable);
					} else {
						comp.setEnabled(enable);
					}
				}
			}
		}
	}

	public void _setEnableComponents(Boolean ena){

		cP.setComponentsEditable(this, ena);
		cP.setComponentsEnabled(this, ena);
		//setClientInformationEnabled(ena);
		cmbBuyerType.setEnabled(ena);
	}

	public void lookupProjectID(){
		pnlPerBatch.lookupProjID.setLookupSQL(functions.lookupProjects(_CancellationProcessing_New.co_id));
	}

	/*public  void _setEnableComponents_(Boolean ena){
		 
		cP.setComponentsEnabled(pnlPerBatch.pnlMainBatch, ena);
		cP.setComponentsEditable(pnlPerBatch.pnlMainBatch, ena);
		
		pnlPerBatch.btnGenerateActive.setEnabled(false);
		pnlPerBatch.btnPreviewActive.setEnabled(false); 
		pnlPerBatch.tblPerBatch.setEnabled(false);
		pnlPerBatch.btnState(true, true, false, false, false);
	}*/
	
	public void btnState_PerBatch(){
		pnlPerBatch.btnState(true, true, false, false, false);
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	
	
	public class Generate_Edit implements Runnable{
		private String batch_no;
		private String _reason_id;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void run() {

			
			batch_no = lookupBatchNo.getValue().toString();

			if (tabpane_per_account.getSelectedIndex()== 0 ) {


				if (cmbBuyerType.getSelectedIndex() == 0) {
					lblListof = "Recommended for Cancellation for All Accounts";

				}
				if (cmbBuyerType.getSelectedIndex() == 1) {
					lblListof = "Recommended for Cancellation for IHF Accounts";

				}	
				if (cmbBuyerType.getSelectedIndex() == 2) {
					lblListof = "Recommended for Cancellation for Pag-ibig Accounts";
 
				}

				pnlPerBatch.rowHeadePerBatch.setModel(new DefaultListModel());
				functions.displayEditPerBatchRecommended(pnlPerBatch.modelPerBatchModel, pnlPerBatch.rowHeadePerBatch,batch_no);
				pnlPerBatch.scrollPerBatch.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(pnlPerBatch.tblPerBatch.getRowCount())));


				if (pnlPerBatch.cmbProcessFor.getSelectedIndex() == 0 ) {
					pnlPerBatch.tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase",	"<html><font color = \"red\">*</font> &nbsp;<i>CSV</i>","Project Name");	
					pnlPerBatch.tblPerBatch.showColumns("<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>");
					pnlPerBatch.tblPerBatch.packAll();
					//tblPerBatch.packColumn(21, 400, 400);
					//tblPerBatch.packColumn(22, 400, 400);
				}else{
					pnlPerBatch.tblPerBatch.hideColumns("Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Phase","<html><font color = \"red\">*</font> &nbsp;<i>Remarks</i>","<html><font color = \"red\">*</font> &nbsp;<i>Reason</i>","Project Name");
					pnlPerBatch.tblPerBatch.showColumns("<html><font color = \"red\">*</font> &nbsp;<i>CSV</i>");
					pnlPerBatch.tblPerBatch.packAll();
				}
				 pgSelect db = new pgSelect();
				db.select("SELECT proj_id, get_project_name(proj_id),approved_by,get_client_name_emp_id(approved_by),reason FROM rf_cancellation where batch_no = ('"+lookupBatchNo.getValue().toString()+"') and status_id = 'A'");

				if (db.isNotNull()) {
					pnlPerBatch.lookupProjID.setValue(db.Result[0][0].toString());
					pnlPerBatch.txtProjectName.setText(db.Result[0][1].toString());
					pnlPerBatch.lookupApproveby_Active.setValue(db.Result[0][2].toString());
					pnlPerBatch.txtApprovedBy_Active.setText(db.Result[0][3].toString());
					_reason_id = db.Result[0][4].toString();
				}
/*
				db.select("SELECT remdesc FROM mf_remarks WHERE remark_id  = '"+reason+"' AND status_id = 'A'");

				if (db.isNotNull()) {

					pnlPerBatch.modelPerBatchModel.setValueAt( db.Result[0][0].toString(), pnlPerBatch.tblPerBatch.getSelectedRow(),22);
				}
*/
				pnlPerBatch.modelPerBatchModel.setEditable(true);
			}
		}
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

}
