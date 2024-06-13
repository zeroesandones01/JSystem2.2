package Buyers.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import Database.pgSelect;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.SpringUtilities;
import Functions.UserInfo;
import Lookup._JLookupTable;
import components._JInternalFrame;
import components._JXTextField;

public class OtherRequest2 extends _JInternalFrame implements _GUI, ActionListener, AncestorListener{

	/**
	 * JLF
	 */
	private static final long serialVersionUID = 353194479785338335L;

	Dimension frameSize = new Dimension(1300, 600);// 510, 250
	static String title = "Other Request";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlMain;

	private JPanel pnlCenter;

	private pnlOtherRequest_OldData pnlOldData;
	private pnlOtherReq_ChangeDueDate pnlChangeDueDate;
	private pnlOtherReq_ChangeClientClass pnlChangeClientClass;
	private pnlOtherReq_CorrectionName pnlCorrectionName;
	private pnlOtherReq_ChangeSellPrice pnlChangeSellPrice;
	private pnlOtherReq_ChangePrincipal pnlChangePrincipal;
	private pnlOtherReq_TransferOfUnit pnlTransferUnit;
	private pnlOtherReq_Reapplication pnlReapplication;
	private pnlOtherReq_ReconsiderDiscPromo pnlReconDisc;
	private pnlOtherReq_ChangeSellingPerson pnlChangeAgent;
	private pnlOtherReq_ChangePmtScheme pnlChangePmtSheme;
	private pnlOtherReq_ChangeHouseModel pnlChangeHouseModel;
	private pnlOtherReq_ChangePagibigContribution pnlChangePagibigContri;
	private pnlOtherReq_ChangeCivilStatus pnlChangeCivilStatus;
	private pnlOtherReq_CreditPayment pnlCreditPayment;
	private pnlOtherReq_TransferReapp pnlTransferReapp;
	private ClientInformation clientInfo;

	private JPanel pnlNewData;
	
	private JPanel pnlSelectRequest;
	private JLabel lblSelectRequest;

	private JPanel pnlSouth;
	private JPanel pnlSouthUpper;
	private JLabel lblReqNo;
	private _JXTextField txtReqNo;
	private JLabel lblReqCli;
	private JTextField txtReqCli;
	private JLabel lblReqStatus;
	private _JXTextField txtReqStatus;
	private JLabel lblRemarks;
	private JTextField txtRemarks;
	private JPanel pnlSouthLower;
	private JButton btnNew;
	private JButton btnSearch;
	private JComboBox cmbSelectRequest;
	private JButton btnPrevSched;
	private JButton btnCreditPayment;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnClear;
	private JButton btnCancelReq;
	private JButton btnApprove;
	private JButton btnPreviewReq;
	
	private JSplitPane splitNewData;
	
	public OtherRequest2() {
		super(title, true, true, true, true);
		initGUI();
	}

	public OtherRequest2(String title) {
		super(title);
		initGUI();
	}

	public OtherRequest2(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setMinimumSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);

		{
			pnlMain = new JPanel(new BorderLayout(3, 3));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JPanel(new GridLayout(1, 2));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					pnlOldData = new pnlOtherRequest_OldData(OtherRequest2.this);
					pnlCenter.add(pnlOldData);
					pnlOldData.setBorder(components.JTBorderFactory.createTitleBorder("Old Data"));
				}
				{
					pnlNewData = new JPanel(new BorderLayout(3, 3));
					pnlCenter.add(pnlNewData);
					pnlNewData.setBorder(components.JTBorderFactory.createTitleBorder("New Data"));
					{
						
						pnlSelectRequest = new JPanel(new BorderLayout(3, 3));
						pnlNewData.add(pnlSelectRequest, BorderLayout.NORTH);
						pnlSelectRequest.setPreferredSize(new Dimension(494, 30));
						{
							lblSelectRequest = new JLabel("Select Request");
							pnlSelectRequest.add(lblSelectRequest, BorderLayout.WEST);
						}
						{
							cmbSelectRequest = new JComboBox(new DefaultComboBoxModel(_OtherRequest2.listRequest()));
							pnlSelectRequest.add(cmbSelectRequest, BorderLayout.CENTER);
							cmbSelectRequest.setEnabled(false);
							cmbSelectRequest.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {

									String selectedRequestID = ((String) cmbSelectRequest.getSelectedItem()).split("-")[0].trim();
									/*if(pnlOldData.setReqEnabled() == false){
										JOptionPane.showMessageDialog(pnlNewData, "Please Select Client First");
									}else{*/
										showRequestPanel(selectedRequestID, pnlOldData.getDataOld());
									//}
								}
							});
						}
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(2, 1, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				pnlSouth.setPreferredSize(new Dimension(798, 100));
				{
					pnlSouthUpper = new JPanel(new SpringLayout());
					pnlSouth.add(pnlSouthUpper);
					{
						lblReqNo = new JLabel("Request No");
						pnlSouthUpper.add(lblReqNo);
					}
					{
						txtReqNo = new _JXTextField();
						pnlSouthUpper.add(txtReqNo);
					}
					{
						lblReqCli = new JLabel("*Requesting client");
						pnlSouthUpper.add(lblReqCli);
					}
					{
						txtReqCli = new JTextField();
						pnlSouthUpper.add(txtReqCli);
					}
					{
						lblReqStatus = new JLabel("Request Status");
						pnlSouthUpper.add(lblReqStatus);
					}
					{
						txtReqStatus = new _JXTextField();
						pnlSouthUpper.add(txtReqStatus);
					}
					{
						lblRemarks = new JLabel("Remarks");
						pnlSouthUpper.add(lblRemarks);
					}
					{
						txtRemarks = new JTextField();
						pnlSouthUpper.add(txtRemarks);
					}
					//XXX TRY TO ADD THE REQUEST PURPOSE HERE
					SpringUtilities.makeCompactGrid(pnlSouthUpper, 2, 4, 3, 3, 3, 3, false);
				}
				{
					pnlSouthLower = new JPanel(new GridLayout(2, 5, 5, 5));
					pnlSouth.add(pnlSouthLower);
					{
						btnNew = new JButton("New");
						pnlSouthLower.add(btnNew);
						btnNew.setActionCommand(btnNew.getText());
						btnNew.setMnemonic(KeyEvent.VK_N);
						btnNew.addActionListener(this);
					}
					{
						btnSearch = new JButton("Search");
						pnlSouthLower.add(btnSearch);
						btnSearch.setActionCommand(btnSearch.getText());
						btnSearch.setMnemonic(KeyEvent.VK_E);
						btnSearch.addActionListener(this);
					}
					{
						btnEdit = new JButton("Edit");
						pnlSouthLower.add(btnEdit);
						btnEdit.setActionCommand(btnEdit.getText());
						btnEdit.setMnemonic(KeyEvent.VK_T);
						btnEdit.addActionListener(this);
					}
					{
						btnSave = new JButton("Save");
						pnlSouthLower.add(btnSave);
						btnSave.setActionCommand(btnSave.getText());
						btnSave.setMnemonic(KeyEvent.VK_S);
						btnSave.addActionListener(this);
					}
					{
						btnClear = new JButton("Clear");
						pnlSouthLower.add(btnClear);
						btnClear.setActionCommand(btnClear.getText());
						btnClear.setMnemonic(KeyEvent.VK_C);
						btnClear.addActionListener(this);
					}
					{
						btnPrevSched = new JButton("Preview Sched");
						pnlSouthLower.add(btnPrevSched);
						btnPrevSched.setActionCommand(btnPrevSched.getText());
						btnPrevSched.setMnemonic(KeyEvent.VK_V);
						btnPrevSched.addActionListener(this);
					}
					{
						btnCancelReq = new JButton("Cancel Request");
						pnlSouthLower.add(btnCancelReq);
						btnCancelReq.setActionCommand(btnCancelReq.getText());
						btnCancelReq.setMnemonic(KeyEvent.VK_L);
						btnCancelReq.addActionListener(this);
					}
					{
						btnApprove = new JButton("Approve");
						pnlSouthLower.add(btnApprove);
						btnApprove.setActionCommand(btnApprove.getText());
						btnApprove.setMnemonic(KeyEvent.VK_A);
						btnApprove.addActionListener(this);
					}
					{
						btnPreviewReq = new JButton("Preview Request");
						pnlSouthLower.add(btnPreviewReq);
						btnPreviewReq.setActionCommand(btnPreviewReq.getText());
						btnPreviewReq.setMnemonic(KeyEvent.VK_I);
						btnPreviewReq.addActionListener(this);
					}
					{
						btnCreditPayment = new JButton("Credit Payment");
						pnlSouthLower.add(btnCreditPayment);
						btnCreditPayment.setActionCommand(btnCreditPayment.getText());
						btnCreditPayment.setMnemonic(KeyEvent.VK_R);
						btnCreditPayment.addActionListener(this);
					}
				}
			}
		}
		clearOtherRequest();
	}//XXX END OF INIT GUI

	//SETS THE BUTTON STATE OF THE PANEL WHEN SELECTING , SAVING AND POSTING THE REQUEST
	public void btnState(Boolean sNew, Boolean sSearch, Boolean sEdit,Boolean sSave, Boolean sClear ,Boolean sPrevSched, Boolean sCancelReq, Boolean sApprove, Boolean sPrintReq, Boolean sCrdtPymnt){
		btnNew.setEnabled(sNew);
		btnSearch.setEnabled(sSearch);
		btnEdit.setEnabled(sEdit);
		btnPrevSched.setEnabled(sPrevSched);
		btnCreditPayment.setEnabled(sCrdtPymnt);
		btnSave.setEnabled(sSave);
		btnClear.setEnabled(sClear);
		btnCancelReq.setEnabled(sCancelReq);
		btnApprove.setEnabled(sApprove);
		btnPreviewReq.setEnabled(sPrintReq);
	}

	public Boolean toSave(){//CHECKS THE REQUIRED FIELDS 
		if(cmbSelectRequest.getSelectedItem().equals("PLEASE SELECT REQUEST")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Select Request"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtReqCli.getText().equals("")){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please Input Requesting Client"), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	public void setReqEnabled(Boolean isEnabled){//SETS THE COMBOBOX FOR THE REQUEST 
		cmbSelectRequest.setEnabled(isEnabled);
	}

	public Boolean toSaveReq(){//CHECKS THE REQUIRED FIELDS BASED FOR THE SELECTED REQUEST BEFORE SAVING
		String selectedRequestID = ((String) cmbSelectRequest.getSelectedItem()).split("-")[0].trim();
		//System.out.printf("Display selected request %s%n", selectedRequestID);

		if(selectedRequestID.equals("01") 
				|| (selectedRequestID.equals("02") && pnlChangeClientClass.checkRequiredFields())
				|| (selectedRequestID.equals("03") && pnlCorrectionName.checkRequiredFields())
				|| selectedRequestID.equals("04")
				|| (selectedRequestID.equals("05") && pnlChangePrincipal.checkRequiredFields()) 
				|| (selectedRequestID.equals("06") && pnlTransferUnit.checkRequiredFields()) 
				|| (selectedRequestID.equals("08") && pnlReconDisc.checkRequiredFields())
				|| (selectedRequestID.equals("12") && pnlTransferReapp.checkRequiredFields())
				|| (selectedRequestID.equals("13") && pnlChangeAgent.checkRequiredFields())
				|| (selectedRequestID.equals("15") && pnlChangePmtSheme.checkRequiredFields()) 
				|| (selectedRequestID.equals("16") && pnlChangeHouseModel.checkRequiredFields())
				|| selectedRequestID.equals("25")
				|| (selectedRequestID.equals("07") && pnlReapplication.checkRequiredFields())
				|| selectedRequestID.equals("18")){

			return true;
		}
		return false;
	}

	public void showRequestPanel(String req_id, Object [] data1){//DISPLAYS THE PANEL FOR THE SELECTED CLIENT REQUEST
		String old_entity_id = (String) data1[0]; //PUT CONTROL HERE BASED ON THE POLICY 
		String old_agent_id = (String) data1[3];
		String old_proj_id = (String) data1[4];
		String old_unit_id = (String) data1[5];
		String old_seq_no = (String) data1[26];
		String old_buyer_type = (String) data1[2];
		//String old_buyer_type = data

		if(_OtherRequest2.isCancelled(old_entity_id, old_unit_id)){//CONTROL FOR CANCELLED UNIT 
			if(req_id.equals("07")){//FOR REAPPLICATION
				if(_OtherRequest2.isUnitTaken(old_unit_id)== false){
					if(_OtherRequest2.isCommissionReleased(old_agent_id, old_unit_id, old_proj_id, old_seq_no)){
						JOptionPane.showMessageDialog(pnlNewData, "Request is not applicable.\nPlease coordinate with FAD for commission reversal");
					}else{
						pnlReapplication = new pnlOtherReq_Reapplication(pnlOldData);
						pnlNewData.add(pnlReapplication, BorderLayout.CENTER);
						pnlReapplication.setBorder(components.JTBorderFactory.createTitleBorder("Reapplication"));
						pnlNewData.repaint();
						pnlReapplication.newReapp(pnlOldData.getDataOld());
					}
				}else{
					cmbSelectRequest.setSelectedItem("PLEASE SELECT REQUEST");
					JOptionPane.showMessageDialog(pnlNewData, "Cannot select request... Unit is already taken by another client");
				}
			}else if(req_id.equals("12")){
				pnlTransferReapp = new pnlOtherReq_TransferReapp(pnlOldData);
				pnlNewData.add(pnlTransferReapp, BorderLayout.CENTER);
				pnlTransferReapp.setBorder(components.JTBorderFactory.createTitleBorder("Transfer-Reapply"));
				pnlNewData.repaint();
				pnlTransferReapp.newTransferReapp(pnlOldData.getDataOld());

			}else if(btnSearch.isEnabled() == false){	
				JOptionPane.showMessageDialog(pnlNewData, "Account is Cancelled.\nRequest is not applicable");
				cmbSelectRequest.setSelectedItem("PLEASE SELECT REQUEST");
			}else if(req_id.equals("06") && btnSearch.isEnabled()){
				
				pnlTransferUnit = new pnlOtherReq_TransferOfUnit(pnlOldData);
				pnlNewData.add(pnlTransferUnit, BorderLayout.CENTER);
				pnlTransferUnit.setBorder(components.JTBorderFactory.createTitleBorder("Transfer of Unit"));
				pnlNewData.repaint();
			}

			try{
				if(req_id.equals("07") == false && pnlReapplication.isShowing()){
					pnlNewData.remove(pnlReapplication);
					pnlNewData.repaint();
				}
			}catch (NullPointerException e) {}

			try{
				if(req_id.equals("12") == false){
					pnlNewData.remove(pnlTransferReapp);
					pnlNewData.repaint();
				}
			}catch (NullPointerException e) {}
		}else{

			//FOR CHANGE OF DUE DATE
			if(req_id.equals("01")){
				if(_OtherRequest.getGroupID(old_buyer_type).equals("02")){
					pnlChangeDueDate = new pnlOtherReq_ChangeDueDate(pnlOldData);
					pnlNewData.add(pnlChangeDueDate, BorderLayout.CENTER);
					pnlChangeDueDate.setBorder(components.JTBorderFactory.createTitleBorder("Change of Due Date"));
					pnlChangeDueDate.setAvailableDPDueDate();
					//pnlChangeDueDate.setAvailableMADueDate();
					pnlNewData.repaint();
				}else{
					JOptionPane.showMessageDialog(pnlNewData, "Change of Due Date is Applicable for In-House Only");
					cmbSelectRequest.setSelectedItem("PLEASE SELECT REQUEST");
				}
			}

			try{
				if(req_id.equals("01") == false && pnlChangeDueDate.isShowing()){
					pnlNewData.remove(pnlChangeDueDate);
					pnlNewData.repaint();
				}
			}catch(NullPointerException e) {}
			//FOR CHANGE OF CLIENT CLASS
			if(req_id.equals("02")){//
				pnlChangeClientClass = new pnlOtherReq_ChangeClientClass(pnlOldData);
				pnlNewData.add(pnlChangeClientClass, BorderLayout.CENTER);
				pnlChangeClientClass.setBorder(components.JTBorderFactory.createTitleBorder("Change of Client Class"));
				pnlNewData.repaint();
			}
			try{
				if(req_id.equals("02") == false && pnlChangeClientClass.isShowing()){
					pnlNewData.remove(pnlChangeClientClass);
					pnlNewData.repaint();
				}
			}catch (NullPointerException e){}
			//FOR CORRECTION OF NAME
			if(req_id.equals("03")){
				pnlCorrectionName = new pnlOtherReq_CorrectionName(pnlOldData);
				pnlNewData.add(pnlCorrectionName, BorderLayout.CENTER);
				pnlCorrectionName.setBorder(components.JTBorderFactory.createTitleBorder("Correction of Name"));
				pnlNewData.repaint();
				pnlCorrectionName.setValuesCorrectionName();
			}
			try{
				if(req_id.equals("03") == false && pnlCorrectionName.isShowing()){
					pnlNewData.remove(pnlCorrectionName);
					pnlNewData.repaint();
				}
			}catch (NullPointerException e){}
			//FOR CHANGE OF SELLING PRICE
			if(req_id.equals("04")){
				pnlChangeSellPrice = new pnlOtherReq_ChangeSellPrice(pnlOldData);
				pnlNewData.add(pnlChangeSellPrice, BorderLayout.CENTER);
				pnlChangeSellPrice.setBorder(components.JTBorderFactory.createTitleBorder("Change of Selling Price"));
				pnlNewData.repaint();
			}
			try{
				if(req_id.equals("04") == false && pnlChangeSellPrice.isShowing()){
					pnlNewData.remove(pnlChangeSellPrice);
					pnlNewData.repaint();
				}
			}catch(NullPointerException e){}
			//FOR CHANGE OF PRINCIPAL APPLICANT
			if(req_id.equals("05")){//XXX TRY HERE THE PURPOSE OF THE REQUEST IN THE CHANGE PINCIPAL APPLICANT
				pnlChangePrincipal = new pnlOtherReq_ChangePrincipal(pnlOldData);
				pnlNewData.add(pnlChangePrincipal, BorderLayout.CENTER);
				pnlChangePrincipal.setBorder(components.JTBorderFactory.createTitleBorder("Change of Principal Applicant"));
				pnlNewData.repaint();
			}
			try{
				if(req_id.equals("05") == false && pnlChangePrincipal.isShowing()){
					pnlNewData.remove(pnlChangePrincipal);
					pnlNewData.repaint();
				}
			}catch (NullPointerException e){}
			//FOR TRANSFER OF UNIT
			if(req_id.equals("06")){
				//System.out.println("Dumaan dito sa display ng panel ng transfer of unit");
				pnlTransferUnit = new pnlOtherReq_TransferOfUnit(pnlOldData);
				pnlNewData.add(pnlTransferUnit, BorderLayout.CENTER);
				pnlTransferUnit.setBorder(components.JTBorderFactory.createTitleBorder("Transfer of Unit"));
				pnlNewData.repaint();
			}
			try{
				if(req_id.equals("06") == false && pnlTransferUnit.isShowing()){
					pnlNewData.remove(pnlTransferUnit);
					pnlNewData.repaint();
				}
			}catch(NullPointerException e){}
			//FOR REAPPLICATION

			if(req_id.equals("07") && txtReqStatus.getText().equals("POSTED") == false){///XXX PUT CONTROL HERE THAT ONLY CANCELLED UNIT CAN BE REAPPLLIED
				cmbSelectRequest.setSelectedItem("PLEASE SELECT REQUEST");
				JOptionPane.showMessageDialog(pnlNewData, "Reapplication is only applicable for cancelled accounts");
			}else if(req_id.equals("07") && txtReqStatus.getText().equals("POSTED")){
				pnlReapplication = new pnlOtherReq_Reapplication(pnlOldData);
				pnlNewData.add(pnlReapplication, BorderLayout.CENTER);
				pnlReapplication.setBorder(components.JTBorderFactory.createTitleBorder("Reapplication"));
				pnlNewData.repaint();
			}
			if(req_id.equals("12") && txtReqStatus.getText().equals("POSTED") == false){
				cmbSelectRequest.setSelectedItem("PLEASE SELECT REQUEST");
				JOptionPane.showMessageDialog(pnlNewData, "Transfer-Reapply is only applicable for cancelled accounts");
			}else if(req_id.equals("12") && txtReqStatus.getText().equals("POSTED")){
				pnlTransferReapp = new pnlOtherReq_TransferReapp(pnlOldData);
				pnlNewData.add(pnlTransferReapp, BorderLayout.CENTER);
				pnlTransferReapp.setBorder(components.JTBorderFactory.createTitleBorder("Transfer-Reapply"));
				pnlNewData.repaint();
			}
			//FOR RECONSIDERATION OF DISCOUNT OR PROMO
			if(req_id.equals("08")){
				pnlReconDisc = new pnlOtherReq_ReconsiderDiscPromo(pnlOldData);
				pnlNewData.add(pnlReconDisc, BorderLayout.CENTER);
				pnlReconDisc.setBorder(components.JTBorderFactory.createTitleBorder("Reconsideration of Discount/Promo"));
				pnlNewData.repaint();
			}

			try{
				if(req_id.equals("08") == false && pnlReconDisc.isShowing()){
					pnlNewData.remove(pnlReconDisc);
					pnlNewData.repaint();
				}
			}catch(NullPointerException e){}
			//FOR CHANGE OF SELLING PERSON
			if(req_id.equals("13")){//CHECK IF VALID ONLY ON TR STAGE
				if(_OtherRequest2.isCommissionReleased(old_agent_id, old_unit_id, old_proj_id, old_seq_no)){
					JOptionPane.showMessageDialog(pnlNewData, "Request is not applicable.\nPlease coordinate with FAD for commission reversal");
				}else{
					pnlChangeAgent = new pnlOtherReq_ChangeSellingPerson(pnlOldData);
					pnlNewData.add(pnlChangeAgent, BorderLayout.CENTER);
					pnlChangeAgent.setBorder(components.JTBorderFactory.createTitleBorder("Change of Selling Person"));
					pnlNewData.repaint();
					System.out.println("Dumaan dito");
				}
			}
			try{
				if(req_id.equals("13") == false && pnlChangeAgent.isShowing()){
					pnlNewData.remove(pnlChangeAgent);
					pnlNewData.repaint();
				}
			}catch (NullPointerException e) {}
			//FOR CHANGE OF PAYMENT SCHEME
			if(req_id.equals("15")){//
				pnlChangePmtSheme = new pnlOtherReq_ChangePmtScheme(pnlOldData);
				pnlNewData.add(pnlChangePmtSheme, BorderLayout.CENTER);
				pnlChangePmtSheme.setBorder(components.JTBorderFactory.createTitleBorder("Change of Payment Scheme"));
				pnlNewData.repaint();
				System.out.println("Dumaan dito sa Change of Payment Scheme");
			}
			try{
				if(req_id.equals("15") == false && pnlChangePmtSheme.isShowing()){
					pnlNewData.remove(pnlChangePmtSheme);
					pnlNewData.repaint();
				}
			}catch(NullPointerException e){}
			//FOR CHANGE OF HOUSE MODEL
			if(req_id.equals("16")){
				pnlChangeHouseModel = new pnlOtherReq_ChangeHouseModel(pnlOldData);
				pnlNewData.add(pnlChangeHouseModel, BorderLayout.CENTER);
				pnlChangeHouseModel.setBorder(components.JTBorderFactory.createTitleBorder("Change of House Model"));
				pnlNewData.repaint();
			}
			try{
				if(req_id.equals("16") == false && pnlChangeHouseModel.isShowing()){
					pnlNewData.remove(pnlChangeHouseModel);
					pnlNewData.repaint();
				}
			}catch(NullPointerException e){}
			if(req_id.equals("18")){
				pnlChangePagibigContri = new pnlOtherReq_ChangePagibigContribution();
				pnlNewData.add(pnlChangePagibigContri, BorderLayout.CENTER);
				pnlChangePagibigContri.setBorder(components.JTBorderFactory.createTitleBorder("Change of Pagibig Contribution"));
				pnlNewData.repaint();
			}
			try{
				if(req_id.equals("18") == false && pnlChangePagibigContri.isShowing()){
					pnlNewData.remove(pnlChangePagibigContri);
					pnlNewData.repaint();
				}
			}catch (NullPointerException e) {}

			if(req_id.equals("25")){
				pnlChangeCivilStatus = new pnlOtherReq_ChangeCivilStatus(pnlOldData);
				pnlNewData.add(pnlChangeCivilStatus, BorderLayout.CENTER);
				pnlChangeCivilStatus.setBorder(components.JTBorderFactory.createTitleBorder("Change of Civil Status"));
				pnlNewData.repaint();
				pnlChangeCivilStatus.setValuesName();
				//pnlChangeCivilStatus.se

			}
			try{
				if(req_id.equals("25") == false && pnlChangeCivilStatus.isShowing()){
					pnlNewData.remove(pnlChangeCivilStatus);
					pnlNewData.repaint();
				}

			}catch (NullPointerException e){}
		}
	}

	public void newOtherRequest(){//NEW CLIENT REQUEST
		pnlOldData.newOldData();
		txtRemarks.setEditable(true);
		txtReqCli.setEditable(true);
		cmbSelectRequest.setSelectedItem("PLEASE SELECT REQUEST");
		btnState(false, false, false, false, true, false, false, false, false, false);

	}

	public void clearOtherRequest(){//CLEARS THE FIELDS FROM THE OLD AND THE PANEL IN NEW DATA
		pnlOldData.clearOldData();
		//FOR CHANGE OF DUE DATE
		try{
			if(pnlChangeDueDate.isShowing()){
				pnlNewData.remove(pnlChangeDueDate);
				pnlNewData.repaint();
				//XXX PUT CLEAR HERE AND OTHER PANELS THAT NEED IT
			}
		}catch (NullPointerException e){}
		//FOR CHANGE OF CLIENT CLASS
		try{
			if(pnlChangeClientClass.isShowing()){
				pnlNewData.remove(pnlChangeClientClass);
				pnlNewData.repaint();

			}
		}catch (NullPointerException e){}
		try{
			if(pnlCorrectionName.isShowing()){
				pnlNewData.remove(pnlCorrectionName);
				pnlNewData.repaint();
			}
		}catch (NullPointerException e){}
		try{
			if(pnlChangeSellPrice.isShowing()){
				pnlNewData.remove(pnlChangeSellPrice);
				pnlNewData.repaint();

			}
		}catch (NullPointerException e){}
		try{
			if(pnlChangePrincipal.isShowing()){
				pnlNewData.remove(pnlChangePrincipal);
				pnlNewData.repaint();
			}
		}catch (NullPointerException e){}
		try{
			if(pnlTransferUnit.isShowing()){
				pnlNewData.remove(pnlTransferUnit);
				pnlNewData.repaint();
			}
		}catch (NullPointerException e){}
		try{
			if(pnlReconDisc.isShowing()){
				pnlNewData.remove(pnlReconDisc);
				pnlNewData.repaint();
			}
		}catch (NullPointerException e){}
		try{
			if(pnlChangePmtSheme.isShowing()){
				pnlNewData.remove(pnlChangePmtSheme);
				pnlNewData.repaint();
			}
		}catch (NullPointerException e){}
		try{
			if(pnlChangeHouseModel.isShowing()){
				pnlNewData.remove(pnlChangeHouseModel);
				pnlNewData.repaint();
			}
		}catch (NullPointerException e){}
		try{
			if(pnlChangeCivilStatus.isShowing()){
				pnlNewData.remove(pnlChangeCivilStatus);
				pnlNewData.repaint();
			}
		}catch (NullPointerException e) {}
		try{
			if(pnlReapplication.isShowing()){
				pnlNewData.remove(pnlReapplication);
				pnlNewData.repaint();
			}
		}catch (NullPointerException e) {}
		try{
			if(pnlChangePagibigContri.isShowing()){
				pnlNewData.remove(pnlChangePagibigContri);
				pnlNewData.repaint();
			}
		}catch (NullPointerException e){}
		try{
			if(pnlTransferReapp.isShowing()){
				pnlNewData.remove(pnlTransferReapp);
				pnlNewData.repaint();
			}
		}catch (NullPointerException e) {}

		txtReqNo.setText("");
		txtReqStatus.setText("");
		txtReqCli.setText("");
		txtRemarks.setText("");
		txtReqCli.setEditable(true);
		txtRemarks.setEditable(true);
		cmbSelectRequest.setEnabled(false);
		cmbSelectRequest.setSelectedItem("PLEASE SELECT REQUEST");

		btnState(true, true, false, false, false, false, false, false, false, false);
	}

	public Object[] selectedReqData(){//RETURN THE DATA IN EACH PANEL BASED ON THE SELECTED REQUEST
		Object [] data2 = null;
		String selectedRequestID = ((String) cmbSelectRequest.getSelectedItem()).split("-")[0].trim();

		try{
			if(selectedRequestID.equals("01") && pnlChangeDueDate.isShowing()){
				data2 = pnlChangeDueDate.getDataChangeDueDate();
			}

			if(selectedRequestID.equals("02")){
				data2 = pnlChangeClientClass.getDataChangeClientClass();
			}
			if(selectedRequestID.equals("03")){
				data2 = pnlCorrectionName.getDataCorrectionName();
			}
			if(selectedRequestID.equals("04")){
				data2 = pnlChangeSellPrice.getDataChangeSellPrice();
			}
			if(selectedRequestID.equals("05")){
				data2 = pnlChangePrincipal.getDataChangePrincipal();
			}
			if(selectedRequestID.equals("06")){
				data2 = pnlTransferUnit.getDataTransferUnit();
			}
			if(selectedRequestID.equals("07")){
				data2 = pnlReapplication.getDataReapplication();
			}
			if(selectedRequestID.equals("08")){
				data2 = pnlReconDisc.getDataReconDiscPromo();
			}
			if(selectedRequestID.equals("12")){
				data2 = pnlTransferReapp.getDataTransferReapp();
			}
			if(selectedRequestID.equals("13")){
				data2 = pnlChangeAgent.getDataChangeSellingPerson();
			}
			if(selectedRequestID.equals("15")){
				data2 = pnlChangePmtSheme.getDataChangePmtScheme();
			}
			if(selectedRequestID.equals("16")){
				data2 = pnlChangeHouseModel.getDataChangeHouseModel();
			}
			if(selectedRequestID.equals("25")){
				data2 = pnlChangeCivilStatus.getDataChangeCivilStatus();
			}
		}catch (NullPointerException e) {}
		return data2;
	}

	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		Object [] data1 = pnlOldData.getDataOld();
		Object [] data2 = selectedReqData();

		if(actionCommand.equals("New")){//NEW CLIENT REQUEST
			newOtherRequest();
		}
		if(actionCommand.equals("Search")){//SEARCHING OF THE REQUEST
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null , "Request", _OtherRequest2.sqlSearch(), false);
			FncSystem.out("Display Sql for Search", _OtherRequest2.sqlSearch());
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);
			dlg.setVisible(true);
			Object [] data = dlg.getReturnDataSet();

			try{
				String req_no = (String) data[0];
				String entity_id = (String) data[1];
				String entity_name = (String) data[2];
				String proj_id = (String) data[3];
				String pbl_id = (String) data[4];
				String unit_id = (String) data[5];
				Integer seq_no = (Integer) data[7];
				String req_stat = (String) data[8];
				Date req_date = (Date) data[9];

				/*if(req_stat.equals("ACTIVE")){
					btnState(false, false, true, false, false, true, true, true, true);
				}else{
					btnState(false, false, false, false, false, true, false, false, true);
				}*/

				txtReqNo.setText(req_no);
				txtReqStatus.setText(req_stat);
				pnlOldData.displayOldData(entity_id, pbl_id, seq_no, proj_id);

				//pnlOldData.search(old_entity_id, old_entity_name, old_unit_id, req_date);

				pnlOldData.search(entity_id, entity_name, unit_id, req_date);
				this.setComponentsEditable(pnlOldData, false);
				cmbSelectRequest.setEnabled(false);
				txtReqCli.setEditable(false);
				txtRemarks.setEditable(false);
				String selected_req = _OtherRequest2.selectedReq(req_no);
				cmbSelectRequest.setSelectedItem(selected_req);
				String selectedRequestID = ((String) cmbSelectRequest.getSelectedItem()).split("-")[0].trim();

				if(req_stat.equals("ACTIVE")){
					if(_OtherRequest2.checkDeptHead()){//CHECKS THE USER LOGIN FOR THE APPROVING OF THE REQUESTS
						System.out.println("User logged is authorized to post");
						
						if(_OtherRequest2.forCredit(selectedRequestID)){
							System.out.println("For Credit");
							btnState(false, false, true, false, true, true, true, true, true, true);
						}else{
							System.out.println("Not for Credit");
							btnState(false, false, true, false, true, true, true, true, true, false);
						}
						
					}else{
						System.out.println("User logged is not authorized to post");
						
						if(_OtherRequest2.forCredit(selectedRequestID)){
							System.out.println("For Credit");
							btnState(false, false, true, false, true, true, true, false, true, true);
						}else{
							System.out.println("Not for Credit");
							btnState(false, false, true, false, true, true, true, false, true, false);
						}
					}
				}else{
					System.out.println("Request Posted or Cancelled");
					//btnState(false, false, false, false, true, false, false, false, true, false);
					btnState(false, false, false, false, true, true, false, false, true, false);
				}

				Object []data4 = _OtherRequest2.displayNewData(req_no);
				txtReqCli.setText((String) data4[0]);
				txtRemarks.setText((String) data4[1]);
				Date new_dp_duedate = (Date) data4[2];
				Date new_ma_duedate = (Date) data4[3];
				String new_entity_id = (String) data4[4];
				String new_entity_name = (String) data4[5];
				String new_lname = (String) data4[6];
				String new_fname = (String) data4[7];
				String new_mname = (String) data4[8];
				String new_entity_class = (String) data4[9];
				String new_entity_class_name = (String) data4[10];
				String new_agent_id = (String) data4[11];
				String new_agent_name = (String) data4[12];
				String new_unit_id = (String) data4[15];
				String new_unit_desc = (String) data4[16];
				BigDecimal new_sprice = (BigDecimal) data4[17];
				String new_model_id = (String) data4[20];
				String new_model_desc = (String) data4[21];
				String new_pmt_scheme_id = (String) data4[18];
				String new_pmt_scheme_name = (String) data4[19];
				BigDecimal new_disc_amt = (BigDecimal) data4[22];
				BigDecimal new_disc_rate = (BigDecimal) data4[29];
				BigDecimal new_dp_amt = (BigDecimal) data4[25];
				BigDecimal new_availed_amt = (BigDecimal) data4[26];
				BigDecimal new_dp_rate = (BigDecimal) data4[27];
				BigDecimal new_availed_rate = (BigDecimal) data4[28];
				Integer new_dpterm = (Integer) data4[24];
				Integer new_materm = (Integer) data4[23];
				String new_lot_area = (String) data4[30];
				BigDecimal new_fire_amt = (BigDecimal) data4[31];
				String new_civil_status = (String) data4[32];
				String purpose = (String) data4[33];
				
				System.out.printf("Display new buyer type: %s%n", new_availed_amt);

				if(selectedRequestID.equals("01")){//DISPLAY THE DATA FOR CHANGE OF DUE DATE
					pnlChangeDueDate.displayChangeDueDate(new_dp_duedate, new_ma_duedate);
				}
				if(selectedRequestID.equals("02")){//DISPLAY THE DATA FOR CHANGE OF CLIENT CLASS
					pnlChangeClientClass.displayChangeClientClass(new_entity_class, new_entity_class_name, new_pmt_scheme_id, 
							new_pmt_scheme_name, new_disc_amt, new_disc_rate, new_dp_amt, new_dp_rate, new_dpterm, new_availed_amt, 
							new_availed_rate, new_materm, new_sprice);
					this.setComponentsEditable(pnlChangeClientClass, false);
				}
				if(selectedRequestID.equals("03")){//DISPLAY THE DATA FOR THE CORRECTION OF NAME
					pnlCorrectionName.displayCorrectionName(new_fname, new_lname, new_mname);
					this.setComponentsEditable(pnlCorrectionName, false);
				}
				if(selectedRequestID.equals("04")){//DISPLAYS THE DATA FOR CHANGE OF SELLING PRICE
					pnlChangeSellPrice.displayChangeSellPrice(new_sprice, new_dp_amt, new_dp_rate, new_availed_amt, new_availed_rate);
					this.setComponentsEditable(pnlChangeSellPrice, false);
				}
				if(selectedRequestID.equals("05")){//DISPLAYS THE DATA FOR THE CHANGE PRINCIPAL
					pnlChangePrincipal.displayChangePrincipal(new_entity_id, new_entity_name, purpose);
					this.setComponentsEditable(pnlChangePrincipal, false);
				}
				if(selectedRequestID.equals("06")){//DISPLAYS THE DATA FOR THE TRANSFER OF UNIT
					pnlTransferUnit.displayTransferofUnit(new_unit_id, new_unit_desc, new_lot_area, new_model_id, new_model_desc,
							new_entity_class, new_entity_class_name, new_pmt_scheme_id, new_pmt_scheme_name, new_sprice, new_disc_amt, 
							new_disc_rate, new_dp_amt, new_dp_rate, new_dpterm, new_fire_amt, new_availed_rate, new_materm, new_availed_amt);
					this.setComponentsEditable(pnlTransferUnit, false);
				}
				if(selectedRequestID.equals("08")){//DISPLAYS THE DATA FOR RECONSIDERATION OF DISCOUNT/PROMO
					pnlReconDisc.displayReconDiscPromo(new_disc_amt, new_disc_rate, new_dp_amt, new_dp_rate, new_availed_amt, new_availed_rate, new_sprice);
					this.setComponentsEditable(pnlReconDisc, false);
				}
				if(selectedRequestID.equals("12")){
					pnlTransferReapp.displayTransferReapp(new_unit_id,new_unit_desc , new_lot_area, new_model_id, new_model_desc,new_agent_id, new_agent_name,new_entity_class, new_entity_class_name, new_pmt_scheme_id, new_pmt_scheme_name, 
							new_disc_amt, new_disc_rate, new_dp_amt, new_dp_rate, new_dpterm, new_availed_amt, new_availed_rate, new_materm, new_sprice, new_fire_amt);
					this.setComponentsEditable(pnlTransferReapp, false);
				}
				if(selectedRequestID.equals("13")){
					pnlChangeAgent.displayChangeSellingPerson(new_agent_id, new_agent_name);
					this.setComponentsEditable(pnlChangeAgent, false);
				}
				if(selectedRequestID.equals("15")){//DISPLAYS THE DATA FOR CHANGE OF PAYMENT SCHEME
					pnlChangePmtSheme.displayChangePmtScheme(new_entity_class, new_entity_class_name, new_pmt_scheme_id, new_pmt_scheme_name, 
							new_disc_amt, new_disc_rate, new_dp_amt, new_dp_rate, new_dpterm, new_availed_amt, new_availed_rate, new_materm, new_sprice);
					this.setComponentsEditable(pnlChangePmtSheme, false);
				}
				if(selectedRequestID.equals("16")){//DISPLAYS THE DATA FOR CHANGE OF HOUSE MODEL
					pnlChangeHouseModel.displayChangeHouseModel(new_model_id, new_model_desc, new_sprice, new_dp_amt, new_dp_rate, new_availed_amt, 
							new_availed_rate, new_fire_amt);
					this.setComponentsEditable(pnlChangeHouseModel, false);
				}
				if(selectedRequestID.equals("25")){
					pnlChangeCivilStatus.displayChangeCivilStatus(new_civil_status, new_fname, new_mname, new_lname);
				}
				if(selectedRequestID.equals("07")){
					pnlReapplication.displayReapplication(new_entity_class, new_entity_class_name, new_pmt_scheme_id, new_pmt_scheme_name, 
							new_disc_amt, new_disc_rate, new_dp_amt, new_dp_rate, new_dpterm, new_availed_amt, new_availed_rate, new_materm, new_sprice, new_agent_id, new_agent_name);

					this.setComponentsEditable(pnlReapplication, false);
				}
			}catch(NullPointerException e) {}
		}
		if(actionCommand.equals("Edit")){//EDITING OF CLIENT REQUEST ADDED BY JOHN LESTER FATALLO 05-06-15
			String selectedRequestID = ((String) cmbSelectRequest.getSelectedItem()).split("-")[0].trim();
			if(selectedRequestID.equals("01")){
				pnlChangeDueDate.editChangeDueDate();
			}
			if(selectedRequestID.equals("02")){
				pnlChangeClientClass.editChangeClientClass();
			}
			if(selectedRequestID.equals("03")){
				this.setComponentsEditable(pnlCorrectionName, true);
			}
			if(selectedRequestID.equals("04")){
				pnlChangeSellPrice.editChangeSellingPrice();
			}
			if(selectedRequestID.equals("05")){
				pnlChangePrincipal.editChangePrincipal();
			}
			if(selectedRequestID.equals("06")){
				pnlTransferUnit.editTransferOfUnit();
			}
			if(selectedRequestID.equals("07")){
				pnlReapplication.editReapplication();
			}
			if(selectedRequestID.equals("08")){
				pnlReconDisc.editReconDiscPromo();
			}
			if(selectedRequestID.equals("12")){
				pnlTransferReapp.editTransferReapp();
			}
			if(selectedRequestID.equals("13")){
				pnlChangeAgent.editChangeSellingPerson();
			}
			if(selectedRequestID.equals("15")){
				pnlChangePmtSheme.editChangePmtScheme();
			}
			if(selectedRequestID.equals("16")){
				pnlChangeHouseModel.editChangeHouseModel();
			}
			if(selectedRequestID.equals("25")){//PUT EDITING OF THE CIVIL STATUS EHRE
				pnlChangeCivilStatus.editChangeCivilStatus();
			}
			btnState(false, false, false, true, true, true, false, false, true, false);
		}
		if(actionCommand.equals("Preview Sched")){//PREVIEW OF
			String selectedRequestID = ((String) cmbSelectRequest.getSelectedItem()).split("-")[0].trim();

			if(txtReqStatus.getText().isEmpty() || txtReqStatus.getText().equals("ACTIVE")){
				if((toSave() && toSaveReq())){
					System.out.printf("Selected req %s%n", selectedRequestID);
					if(_OtherRequest2.affectSched(selectedRequestID) == false){	
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Request is not applicable");
					}else{
						/*pnlOtherReq_PreviewSchedule ps = new pnlOtherReq_PreviewSchedule(FncGlobal.homeMDI, "Preview Schedule", _OtherRequest2.setFinalVariables(data1, data2));
						ps.setLocationRelativeTo(null);
						ps.setVisible(true);*/
					}
				}
			}
			
			if(txtReqStatus.getText().equals("POSTED")){//FOR PREVIEW SCHEDULE OF POSTED REQUESTS CHECKS THE SCHEDULE OF THE 
				pnlOtherReq_PreviewSchedSearch pss = new pnlOtherReq_PreviewSchedSearch(FncGlobal.homeMDI, "Preview Schedule", _OtherRequest2.setFinalVariables(data1, data2), txtReqNo.getText(), txtReqCli.getText(), txtReqStatus.getText());
				pss.setLocationRelativeTo(null);
				pss.setVisible(true);
			}
		}
		if(actionCommand.equals("Credit Payment")){//CHECK KUNG KAILANG PA NG BUTTON NA CREDIT PAYMENT
			if(_OtherRequest2.isCredited(txtReqNo.getText())){//DISPLAYS THE CREDITED PAYMENTS IF THE REQUEST IS ALREADY CREDITED
				System.out.println("Dumaan dito");
				pnlOtherReq_CreditPayment cp = new pnlOtherReq_CreditPayment(FncGlobal.homeMDI, "Credit Payment");
				//cp.setCreditdetails(txtReqNo.getText());
				cp.displayCreditedPayments(txtReqNo.getText());
				cp.setLocationRelativeTo(null);
				cp.setVisible(true);
				cp.setEnabled(false);
			}else{//DISPLAYS THE PARTICULARS TO BE CREDITED
				
				pnlOtherReq_CreditPayment cp = new pnlOtherReq_CreditPayment(FncGlobal.homeMDI, "Credit Payment");
				//cp.setCreditdetails(txtReqNo.getText());
				cp.displayCreditDocuments(txtReqNo.getText());
				cp.setLocationRelativeTo(null);
				cp.setVisible(true);
				
			}
		}
		if(actionCommand.equals("Save")){//SAVING AND UPDATING OF THE REQUEST
			String req_no = "";
			if(txtReqNo.getText().equals("") || txtReqNo.getText().isEmpty()){
				req_no = _OtherRequest2.getRequestNo();
			}
			if(txtReqNo.getText().isEmpty() == false && txtReqStatus.getText().equals("ACTIVE")){
				req_no = txtReqNo.getText();
			}
			String selectedRequestID = ((String) cmbSelectRequest.getSelectedItem()).split("-")[0].trim();
			
			if(toSave() && toSaveReq()){
				if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to save request?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					
					if(_OtherRequest2.isRequestExisting(txtReqNo.getText()) == false){//CHECKS
						_OtherRequest2.saveRtRequestHeader(req_no, txtReqCli.getText(), txtRemarks.getText(), _OtherRequest2.setFinalVariables(data1,data2));
					}
					if(_OtherRequest2.isRequestExisting(txtReqNo.getText())){
						_OtherRequest2.updateRequestDetails(_OtherRequest2.setFinalVariables(data1, data2), txtReqNo.getText());
					}
					if(selectedRequestID.equals("01") || (selectedRequestID.equals("02")) 
							|| selectedRequestID.equals("04") || selectedRequestID.equals("05") 
							|| selectedRequestID.equals("06") || selectedRequestID.equals("08") 
							|| selectedRequestID.equals("15") || selectedRequestID.equals("16")
							|| selectedRequestID.equals("07") || selectedRequestID.equals("12")){
						
						//XXX UNCOMMENT WHEN DONE TESTING 
						_OtherRequest2.createNewSchedule(_OtherRequest2.setFinalVariables(data1, data2), req_no);

						if(selectedRequestID.equals("06") || selectedRequestID.equals("12")){//UPDATES THE NEW UNIT ID  CHECK IF PWEDE YUNG TRANSFER REAPP DITO
							_OtherRequest2.updateMfUnitInfo(_OtherRequest2.setFinalVariables(data1, data2));
						}
					}

					if(_OtherRequest2.forCredit(selectedRequestID) && _OtherRequest2.isTotalPaymentsZero(req_no) == false){
						System.out.printf("Display req no: %s%n", req_no);
						pnlOtherReq_CreditPayment cp = new pnlOtherReq_CreditPayment(FncGlobal.homeMDI, "Credit Payment");
						//cp.setCreditdetails(req_no);
						cp.displayCreditDocuments(req_no);
						cp.setLocationRelativeTo(null);
						cp.setVisible(true);
					}
					
					if(_OtherRequest2.isRequestExisting(txtReqNo.getText())){
						//System.out.println("Existing yung request");
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Request Updated\nRequest No: %s\nCHANGE IN PAYMENT SCHEDULE WILL TAKE EFFECT UPON POSTING/APPROVAL", req_no) , "Save", JOptionPane.INFORMATION_MESSAGE);
					}
					if(_OtherRequest2.isRequestExisting(txtReqNo.getText()) == false){
						//System.out.println("Bago yung request");
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Request Saved\nRequest No: %s\nCHANGE IN PAYMENT SCHEDULE WILL TAKE EFFECT UPON POSTING/APPROVAL", req_no) , "Save", JOptionPane.INFORMATION_MESSAGE);
					}
					clearOtherRequest();
					//_OtherRequest2.saveRequest();
				}
			}
		}
		
		if(actionCommand.equals("Clear")){//CLEARS THE FIELDS IN THE OLD AND NEW DATA
			clearOtherRequest();
		}
		
		if(actionCommand.equals("Cancel Request")){//CANCELLATION OF THE REQUEST
			String selectedRequestID = ((String) cmbSelectRequest.getSelectedItem()).split("-")[0].trim();
			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to Cancel request?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				_OtherRequest2.cancelReq(txtReqNo.getText());
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Request Successfully Cancelled") , "Post", JOptionPane.INFORMATION_MESSAGE);
				clearOtherRequest();
			}
		}
		
		if(actionCommand.equals("Approve")){//POSTING OF THE REQUEST
			String selected_req = ((String) cmbSelectRequest.getSelectedItem()).split("-")[0].trim();

			if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure to Post request?", actionCommand, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				if(_OtherRequest2.forCredit(selected_req) && _OtherRequest2.isTotalPaymentsZero(txtReqNo.getText()) == false){
					if(_OtherRequest2.isCredited(txtReqNo.getText())){
						if(_OtherRequest2.postRequest2(txtReqNo.getText())){
							System.out.println("Dumaan dito sa credit of payments");
							JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Request succesfully posted", actionCommand, JOptionPane.INFORMATION_MESSAGE);
							clearOtherRequest();
						}
					}else{
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please credit payment before posting the request", actionCommand, JOptionPane.WARNING_MESSAGE);
					}
				}else if(_OtherRequest2.postRequest2(txtReqNo.getText())){
					System.out.println("Dumaan dito sa hindi credit payment");
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Request succesfully posted", actionCommand, JOptionPane.INFORMATION_MESSAGE);
					clearOtherRequest();
				}
			}
		}
		
		if(actionCommand.equals("Preview Request")){//GENERATES A REPORT WITH THE OLD AND THE NEW DETAILS BASED ON THE REQUEST 
			//String approved_request = ((String) cmbSelectRequest.getSelectedItem()).split("-")[1].trim();
			String request_no = txtReqNo.getText();

			Map<String, Object> mapParameters = new HashMap<String, Object>();

			pgSelect db = new pgSelect();
			String sql = "select get_client_name(new_entity_id) from req_rt_request_header where request_no  = '"+request_no+"'";
			
			db.select(sql);
			String entity_name = (String) db.getResult()[0][0];
			
			mapParameters.put("request_no", request_no);
			mapParameters.put("prepared_by", UserInfo.FullName);
			mapParameters.put("connection", FncGlobal.connection);

			/*System.out.printf("Display approved request %s%n", approved_request);
			System.out.printf("Display the approved request %s%n", approved_request);*/
			FncReport.generateReport("/Reports/rptClientRequest.jasper", "Client Request Approval ("+entity_name+")", mapParameters);
		}
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub

	}

}
