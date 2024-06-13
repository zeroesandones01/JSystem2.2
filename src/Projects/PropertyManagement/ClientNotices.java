package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._RealTimeDebit;
import Buyers.LoansManagement._PagibigStatusMonitoring;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelPagibigNotices;

public class ClientNotices extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = -1834829101657201605L;
	static String title = "Client Notices";
	Dimension frameSize = new Dimension(750, 500);

	static Border lineRed = BorderFactory.createLineBorder(Color.RED);

	private JPanel pnlNorth;
	private JPanel pnlCenter;
	private JPanel pnlSouth;

	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblNotice;

	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtPhaseID;
	private JComboBox cmbBuyerType;
	private JComboBox cmbHouseType;
	private _JLookup txtNoticeID;
	private _JLookup txtBatchID;
	private _JLookup txtClientID;

	private JCheckBox chkBatch;

	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;
	private JTextField txtClient;
	private JTextField txtNotice;

	private _JTableMain tblPagibigNotices;
	private modelPagibigNotices modelPagibigNotices;
	private JScrollPane scrollPagibigNotices;
	private JList rowHeaderPagibigNotices;

	private JButton btnTag;
	private JButton btnPreview;
	private JButton btnCancel;

	private String strUnit;

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public ClientNotices() {
		super(title, true, true, false, true);
		initGUI();
	}

	public ClientNotices(String title) {
		super(title);
		initGUI();
	}

	public ClientNotices(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);

		//hdmfNot = new _PagibigNotices();
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(panMain, BorderLayout.CENTER);
			panMain.setPreferredSize(frameSize);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				panMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 180));
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Notice Details"));
				{
					JPanel pnlNorthLabel = new JPanel(new GridLayout(7, 1, 3, 3));
					pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
					{
						lblCompany = new JLabel("Company");
						pnlNorthLabel.add(lblCompany);
					}
					{
						lblProject = new JLabel("Project");
						pnlNorthLabel.add(lblProject);
					}
					{
						lblPhase = new JLabel("Phase");
						pnlNorthLabel.add(lblPhase);
					}
					{
						JLabel lblBuyerType = new JLabel("Buyer Type");
						pnlNorthLabel.add(lblBuyerType);
					}
					{
						JLabel lblHouse = new JLabel("House & Lot/Lot");
						pnlNorthLabel.add(lblHouse);
					}
					{
						lblNotice = new JLabel("Notice Type");
						pnlNorthLabel.add(lblNotice);
					}
					{
						chkBatch = new JCheckBox("Batch No");
						pnlNorthLabel.add(chkBatch, BorderLayout.WEST);
						chkBatch.setHorizontalAlignment(JTextField.LEFT);
						chkBatch.setPreferredSize(new Dimension(160, 0));
						chkBatch.setSelected(false);
						chkBatch.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evChk) {
								Boolean blnDo = evChk.getStateChange() == ItemEvent.SELECTED;
								txtBatchID.setValue(null);
								txtBatchID.setEnabled(blnDo);

								modelPagibigNotices.clear();
								scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
								rowHeaderPagibigNotices.setModel(new DefaultListModel());
								//chkClient.setSelected(!blnDo);
								/*txtClientID.setEnabled(!blnDo);
								txtClientID.setValue("");
								txtClient.setText("");
								btnPreview.setText("Preview");*/
							}
						});
					}
				}
				{
					JPanel pnlNorthComponent = new JPanel(new GridLayout(7, 1, 3, 3));
					pnlNorth.add(pnlNorthComponent, BorderLayout.CENTER);
					{
						JPanel pnlCompany = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlCompany);
						{
							txtCoID = new _JLookup("");
							pnlCompany.add(txtCoID, BorderLayout.WEST);
							txtCoID.setHorizontalAlignment(JTextField.CENTER);
							txtCoID.setLookupSQL(_PagibigStatusMonitoring.sqlCompany());
							txtCoID.setReturnColumn(0);
							txtCoID.setValue("02");
							txtCoID.setPreferredSize(new Dimension(50, 0));
							txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtCompany.setText(data[1].toString());
										txtProID.setLookupSQL(sqlProject(txtCoID.getValue()));
										txtProID.setValue(null);
										txtProject.setText("");
										//btnPreview.setEnabled(true);
									} else {
										//btnPreview.setEnabled(false);									
									}
								}
							});
						}
						{
							txtCompany = new JTextField("");
							pnlCompany.add(txtCompany, BorderLayout.CENTER);
							txtCompany.setHorizontalAlignment(JTextField.CENTER);
							txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
						}
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlProject);
						{
							txtProID = new _JLookup("");
							pnlProject.add(txtProID, BorderLayout.WEST);
							txtProID.setHorizontalAlignment(JTextField.CENTER);
							txtProID.setLookupSQL(_PagibigStatusMonitoring.sqlProject(""));
							txtProID.setReturnColumn(0);
							txtProID.setValue("015");
							txtProID.setPreferredSize(new Dimension(50, 0));
							txtProID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										String proj_id = (String) data[0];
										txtProject.setText(data[1].toString());

										txtPhaseID.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(proj_id));
									}
								}
							});
						}
						{
							txtProject = new JTextField("");
							pnlProject.add(txtProject, BorderLayout.CENTER);
							txtProject.setHorizontalAlignment(JTextField.CENTER);
							txtProject.setText("TERRAVERDE RESIDENCES");
						}
					}
					{
						JPanel pnlPhase = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlPhase);
						{
							txtPhaseID = new _JLookup("");
							pnlPhase.add(txtPhaseID, BorderLayout.WEST);
							txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
							txtPhaseID.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(txtProID.getValue()));
							txtPhaseID.setReturnColumn(1);
							txtPhaseID.setPreferredSize(new Dimension(50, 0));
							txtPhaseID.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										txtPhase.setText(data[2].toString());

									}

								}
							});
						}
						{
							txtPhase = new JTextField("");
							pnlPhase.add(txtPhase, BorderLayout.CENTER);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
						}
					}
					{
						JPanel pnlBuyertype = new JPanel(new BorderLayout(5, 5));
						pnlNorthComponent.add(pnlBuyertype);
						{
							cmbBuyerType = new JComboBox(new DefaultComboBoxModel(new String[] { "All", "IHF", "HDMF"}));
							pnlBuyertype.add(cmbBuyerType, BorderLayout.WEST);
							cmbBuyerType.setPreferredSize(new Dimension(150, 0));
						}
					}
					{
						JPanel pnlHouseType = new JPanel(new BorderLayout(5, 5));
						pnlNorthComponent.add(pnlHouseType);
						{
							cmbHouseType = new JComboBox(new DefaultComboBoxModel(new String[] { "All", "House & Lot", "Lot Only"}));
							pnlHouseType.add(cmbHouseType, BorderLayout.WEST);
							cmbHouseType.setPreferredSize(new Dimension(150, 0));
						}
					}
					{
						JPanel pnlNotice = new JPanel(new BorderLayout(5, 5));
						pnlNorthComponent.add(pnlNotice);
						{
							txtNoticeID = new _JLookup();
							pnlNotice.add(txtNoticeID, BorderLayout.WEST);
							txtNoticeID.setHorizontalAlignment(JTextField.CENTER);
							txtNoticeID.setLookupSQL(_ClientNotices.NoticeSQL());
							txtNoticeID.setReturnColumn(0);
							txtNoticeID.setPreferredSize(new Dimension(50, 0));
							txtNoticeID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();

									if (data!=null) {
										String notice_id = (String) data[0];
										chkBatch.setSelected(false);
										txtBatchID.setValue(null);
										
										if(notice_id.equals("143") == false) {
											cmbBuyerType.setSelectedIndex(0);
											cmbHouseType.setSelectedIndex(0);
										}

										txtNotice.setText(data[1].toString());
										txtBatchID.setLookupSQL(_ClientNotices.Batch(txtNoticeID.getValue()));
										
										displayQualifiedAccts();
									}
								}
							});
						}
						{
							txtNotice = new JTextField("");
							pnlNotice.add(txtNotice, BorderLayout.CENTER);
							txtNotice.setHorizontalAlignment(JTextField.CENTER);
						}
					}
					{
						JPanel pnlBatch = new JPanel(new BorderLayout(5, 5));
						pnlNorthComponent.add(pnlBatch);
						{
							txtBatchID = new _JLookup();
							pnlBatch.add(txtBatchID, BorderLayout.WEST);
							txtBatchID.setPreferredSize(new Dimension(150, 0));
							txtBatchID.setHorizontalAlignment(JTextField.CENTER);
							txtBatchID.setReturnColumn(0);
							txtBatchID.setLookupSQL(_ClientNotices.Batch(""));
							txtBatchID.setEnabled(false);
							txtBatchID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									
									if(txtNoticeID.getValue().equals("63")){
										_ClientNotices.displayNoticeTurnover_HDMF_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue());
										scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
										tblPagibigNotices.packAll();
									}
			
									if(txtNoticeID.getValue().equals("134")){
										_ClientNotices.displayNoticeTurnover_Cash_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
										scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
										tblPagibigNotices.packAll();
									}
									
									if(txtNoticeID.getValue().equals("69")){
										_ClientNotices.displayNoticeTurnover_LotOnly_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
										scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
										tblPagibigNotices.packAll();
									}
									
									if(txtNoticeID.getValue().equals("101")){
										_ClientNotices.displayNoticeAssumedTurnover(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
										scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
										tblPagibigNotices.packAll();
									}
									
									if(txtNoticeID.getValue().equals("143")){
										_ClientNotices.displayPMD_FD_Dues(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), (String) cmbBuyerType.getSelectedItem(), (String) cmbHouseType.getSelectedItem() ,txtBatchID.getValue());
										setBuyerType(txtBatchID.getValue());
										scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
										tblPagibigNotices.packAll();
									}

									btnState(false, true, true);

									if (txtNoticeID.getValue().equals("127") && !txtBatchID.getValue().equals("")) {
										btnState(false, true, true);
									} else if (txtNoticeID.getValue().equals("127") && txtBatchID.getValue().equals("")) {
										btnState(false, true, true);
									} else if (txtNoticeID.getValue().equals("128") && !txtBatchID.getValue().equals("")) {
										btnState(false, true, true);
									} else if (txtNoticeID.getValue().equals("128") && txtBatchID.getValue().equals("")) {
										btnState(true, true, true);
									}


								}
							});
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				panMain.add(pnlCenter, BorderLayout.CENTER);
				{
					scrollPagibigNotices = new JScrollPane();
					pnlCenter.add(scrollPagibigNotices, BorderLayout.CENTER);
					{

						modelPagibigNotices = new modelPagibigNotices();

						tblPagibigNotices = new _JTableMain(modelPagibigNotices);
						scrollPagibigNotices.setViewportView(tblPagibigNotices);
						scrollPagibigNotices.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						tblPagibigNotices.setSortable(false);
						tblPagibigNotices.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq No");
					}
					{
						rowHeaderPagibigNotices = tblPagibigNotices.getRowHeader();
						rowHeaderPagibigNotices.setModel(new DefaultListModel());
						scrollPagibigNotices.setRowHeaderView(rowHeaderPagibigNotices);
						scrollPagibigNotices.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1, 5, 3, 3));
				panMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 40));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					//pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnTag = new JButton("Tag");
					pnlSouth.add(btnTag);
					btnTag.setActionCommand(btnTag.getText());
					btnTag.addActionListener(this);
					btnTag.setEnabled(false);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
				}
			}
		}
		btnState(false, false, false);
	}//XXX END OF INIT GUI

	private void btnState(Boolean sTag, Boolean sPreview, Boolean sCancel){
		btnTag.setEnabled(sTag);
		btnPreview.setEnabled(sPreview);
		btnCancel.setEnabled(sCancel);
	}

	private void previewTransmittal(String batch_no){

		if(txtNoticeID.getValue().equals("105") || txtNoticeID.getValue().equals("111") || txtNoticeID.getValue().equals("122") || txtNoticeID.getValue().equals("123") 
		   || txtNoticeID.getValue().equals("63") || txtNoticeID.getValue().equals("118") || txtNoticeID.getValue().equals("134") || txtNoticeID.getValue().equals("69")
		   || txtNoticeID.getValue().equals("101")){

			/*Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("company_name", txtCompany.getText());
			mapParameters.put("prepared_by", UserInfo.FullName2);
			mapParameters.put("batch_no", batch_no);*/

			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
			ArrayList<String> listSeq = new ArrayList<String>();
			//ArrayList<Integer> listSeq = new ArrayList<Integer>();

			for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

				if(isSelected){
					String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
					String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
					String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
					Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

					listEntityID.add(String.format("%s", entity_id));
					listProjID.add(String.format("%s", proj_id));
					listPBL.add(String.format("%s", pbl_id));
					listSeq.add(String.format("%s", seq_no));
					//listSeq.add(seq_no);
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

			Map<String, Object> mapParameters = new HashMap<String, Object>();

			//mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
			mapParameters.put("company_name", txtCompany.getText());
			mapParameters.put("prepared_by", UserInfo.FullName2);
			//mapParameters.put(company_alias, UserInfo.Alias);
			mapParameters.put("entity_id", entity_id);
			mapParameters.put("proj_id", proj_id);
			mapParameters.put("pbl_id", pbl_id);
			mapParameters.put("seq_no", seq_no);
			mapParameters.put("batch_no", batch_no);


			FncReport.generateReport("/Reports/rptNTCList_IncompleteDocs_First_Notice.jasper", "List of Clients (Transmittal)", mapParameters);
		}

		if(txtNoticeID.getValue().equals("111")){

		}
	}

	private void previewTransmittal2(String batch_no){

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("batch_no", batch_no);
		FncReport.generateReport("/Reports/subrptTransmittalForPostOffice.jasper", "Notices Transmittal", mapParameters);

	}
	
	private void previewTransmittal_PMD_FD(String batch_no) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("batch_no", batch_no);
		FncReport.generateReport("/Reports/rptPMDFD_Transmittal2.jasper", "Notices Transmittal", mapParameters);

	}

	private void cancelPagibigNotices(){
		modelPagibigNotices.clear();
		rowHeaderPagibigNotices.setModel(new DefaultListModel());
		scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		btnState(false, false, false);
	}

	public void actionPerformed(ActionEvent act) {
		String actionCommand = act.getActionCommand();

		if(actionCommand.equals("Tag")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

				if(txtNoticeID.getValue().equals("63")){
					String batch_no = _ClientNotices.saveNoticeOfTurnover_HDMF(modelPagibigNotices);

					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNoticeTurnover_HDMF();

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}
				
				if(txtNoticeID.getValue().equals("134")){
					String batch_no = _ClientNotices.saveNoticeTurnover_Cash(modelPagibigNotices);
					
					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNoticeTurnover_Cash();
					
					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}
				
				if(txtNoticeID.getValue().equals("69")){
					String batch_no = _ClientNotices.saveNoticeTurnover_LotOnly(modelPagibigNotices);
					
					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNoticeTurnover_LotOnly();
					
					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}
				
				if(txtNoticeID.getValue().equals("101")){
					String batch_no = _ClientNotices.saveNoticeAssumed_Turnover(modelPagibigNotices);
					
					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNoticeAssumedTurnover();
					
					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}
				
				if(txtNoticeID.getValue().equals("143")){
					String batch_no = _ClientNotices.saveFinalDemand_DuePmt(modelPagibigNotices);
					
					previewTransmittal(batch_no);
					previewTransmittal_PMD_FD(batch_no);
					generateFinalDemandDues_Pmt();
					
					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				btnState(false, true, false);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Save", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if(actionCommand.equals("Preview")){
			
			if(txtNoticeID.getValue().equals("63")){
				generateNoticeTurnover_HDMF();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				} else {
					previewTransmittal(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("134")){
				generateNoticeTurnover_Cash();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				} else {
					previewTransmittal(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("69")){
				generateNoticeTurnover_LotOnly();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				} else {
					previewTransmittal(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("62")){
				generateNoticeTurnover_ihf();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				} else {
					previewTransmittal(txtBatchID.getValue());
				}
			}
			
			if(txtNoticeID.getValue().equals("101")){
				generateNoticeAssumedTurnover();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				} else {
					previewTransmittal(txtBatchID.getValue());
				}
			}
			
			if(txtNoticeID.getValue().equals("143")){
				generateFinalDemandDues_Pmt();
				if(txtBatchID.getValue() != null){
					previewTransmittal_PMD_FD(txtBatchID.getValue());
				}
			}

		}

		if(actionCommand.equals("Cancel")){
			cancelPagibigNotices();
		}


		/*if (actionCommand.equals("gENERATE")) {
			if (chkClient.isSelected()) {
				Generate();	
			} else {
				Preview();
			}
		}*/
	}

	private void displayQualifiedAccts() {
		FncGlobal.startProgress("Generating Qualified Accounts");

		new Thread(new Runnable() {
			public void run() {
				
				if(txtNoticeID.getValue().equals("63")){
					_ClientNotices.displayNoticeTurnover_HDMF_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("69")){
					_ClientNotices.displayNoticeTurnover_LotOnly_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("134")){
					_ClientNotices.displayNoticeTurnover_Cash_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				//added by jari cruz asof march 10 2023
				if(txtNoticeID.getValue().equals("62")){
					_ClientNotices.displayNoticeTurnover_Ihf_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}
				
				if(txtNoticeID.getValue().equals("101")){
					_ClientNotices.displayNoticeAssumedTurnover(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}
				
				if(txtNoticeID.getValue().equals("143")){
					_ClientNotices.displayPMD_FD_Dues(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), (String) cmbBuyerType.getSelectedItem(), (String) cmbHouseType.getSelectedItem() ,txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if (!chkBatch.isSelected()) {
					btnState(true, true, true);
				} else {
					btnState(false, true, false);
				}

				FncGlobal.stopProgress();
			}
		}).start();
	}

	private void Preview() {
		String strSQL = "";

		pgUpdate db_delete = new pgUpdate();
		db_delete.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
		db_delete.commit();

		System.out.println("");
		System.out.println("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'");		

		for (int x = 0; x < modelPagibigNotices.getRowCount(); x++) {
			if ((Boolean) modelPagibigNotices.getValueAt(x, 0)) {
				pgUpdate dbInsert = new pgUpdate();
				strSQL = "insert into tmp_hdmf (client_name, emp_code) values ('"+modelPagibigNotices.getValueAt(x, 1)+"', '"+UserInfo.EmployeeCode+"')";

				System.out.println("");
				System.out.println(strSQL);

				dbInsert.executeUpdate(strSQL, false);
				dbInsert.commit();
			}
		}

		try {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("batch_no", txtBatchID.getValue());
			mapParameters.put("company", txtCompany.getText());
			mapParameters.put("company_address", "");
			mapParameters.put("notice_type_id", txtNoticeID.getValue());
			mapParameters.put("prepared_by", _RealTimeDebit.GetName(UserInfo.EmployeeCode));

			if (txtNoticeID.getValue().equals("127")) {
				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingFirstNotice.jasper", "Qualified for First Filing First Notice", "", mapParameters);
			} else if (txtNoticeID.getValue().equals("128")) {
				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingFinalNotice.jasper", "Qualified for First Filing Final Notice", "", mapParameters);					
			}

			if(txtNoticeID.getValue().equals("104") || txtNoticeID.getValue().equals("105")){

			}

		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(null, "Transmittals preview will only be available after tagging.");
		}
	}
	
	private void generateNoticeTurnover_HDMF(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		String logo = FncGlobal.GetString("SELECT company_logo from mf_company where co_id = '"+txtCoID.getValue()+"'");

		//mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq-new.png"));
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ logo));

		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeOfTurnover_HDMF.jasper", "Notice of Turn-over (HDMF Financing)", mapParameters);
	}

	private void generateNoticeTurnover_Cash(){

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		//mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenqlogo.png"));
		String logo = FncGlobal.GetString("SELECT company_logo from mf_company where co_id = '"+txtCoID.getValue()+"'");
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ logo));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());
		
		FncReport.generateReport("/Reports/rptNoticeOfTurnOver_Cash.jasper", "Notice of Turn-over (Cash)", mapParameters);
	}

	private void generateNoticeTurnover_LotOnly(){

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		ArrayList<String> listEntityID_for_late = new ArrayList<String>();
		ArrayList<String> listProjID_for_late = new ArrayList<String>();
		ArrayList<String> listPBL_for_late = new ArrayList<String>();
		ArrayList<String> listSeq_for_late = new ArrayList<String>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				if (fncCheckIfForLate(entity_id,proj_id,pbl_id)) {
					listEntityID_for_late.add(String.format("%s", entity_id));
					listProjID_for_late.add(String.format("%s", proj_id));
					listPBL_for_late.add(String.format("%s", pbl_id));
					listSeq_for_late.add(String.format("%s", seq_no));
					//listSeq.add(seq_no);
				} else {
					listEntityID.add(String.format("%s", entity_id));
					listProjID.add(String.format("%s", proj_id));
					listPBL.add(String.format("%s", pbl_id));
					listSeq.add(String.format("%s", seq_no));
					//listSeq.add(seq_no);
				}
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		String entity_id_for_late = listEntityID_for_late.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id_for_late = listProjID_for_late.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id_for_late = listPBL_for_late.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no_for_late = listSeq_for_late.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		System.out.printf("Display value of entity_id_for_late: (%s)%n", entity_id_for_late);
		System.out.printf("Display value of proj_id_for_late: (%s)%n", proj_id_for_late);
		System.out.printf("Display value of pbl_id_for_late id: (%s)%n", pbl_id_for_late);
		System.out.printf("Display value of seq_no_for_late: (%s)%n", seq_no_for_late);

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		String logo = FncGlobal.GetString("SELECT company_logo from mf_company where co_id = '"+txtCoID.getValue()+"'");
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ logo));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);

		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		Map<String, Object> mapParameters_for_late = new HashMap<String, Object>();
		String logo_for_late = FncGlobal.GetString("SELECT company_logo from mf_company where co_id = '"+txtCoID.getValue()+"'");
		mapParameters_for_late.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ logo_for_late));
		mapParameters_for_late.put("company", txtCompany.getText());
		mapParameters_for_late.put("prepared_by", UserInfo.Alias);

		mapParameters_for_late.put("entity_id", entity_id_for_late);
		mapParameters_for_late.put("proj_id", proj_id_for_late);
		mapParameters_for_late.put("pbl_id", pbl_id_for_late);
		mapParameters_for_late.put("seq_no", seq_no_for_late);
		mapParameters_for_late.put("batch_no", txtBatchID.getValue());
		mapParameters_for_late.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());
		
		System.out.println("listEntityID: "+listEntityID.isEmpty());
		if (!listEntityID.isEmpty()) {
			System.out.println("listEntityID condition");
			FncReport.generateReport("/Reports/rptNoticeOfTurnover_LotOnly.jasper", "Notice of Turn-over (Cash)", mapParameters);
		}
		if (!listEntityID_for_late.isEmpty()) {
			System.out.println("listEntityID_for_late condition");
			FncReport.generateReport("/Reports/rptNoticeOfTurnover_LotOnly_for_late.jasper", "Notice of Turn-over (Cash)", mapParameters_for_late);
		}
	}

	private void generateNoticeTurnover_ihf(){

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		ArrayList<String> listEntityID_for_late = new ArrayList<String>();
		ArrayList<String> listProjID_for_late = new ArrayList<String>();
		ArrayList<String> listPBL_for_late = new ArrayList<String>();
		ArrayList<String> listSeq_for_late = new ArrayList<String>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				if (fncCheckIfForLate(entity_id,proj_id,pbl_id)) {
					listEntityID_for_late.add(String.format("%s", entity_id));
					listProjID_for_late.add(String.format("%s", proj_id));
					listPBL_for_late.add(String.format("%s", pbl_id));
					listSeq_for_late.add(String.format("%s", seq_no));
					//listSeq.add(seq_no);
				} else {
					listEntityID.add(String.format("%s", entity_id));
					listProjID.add(String.format("%s", proj_id));
					listPBL.add(String.format("%s", pbl_id));
					listSeq.add(String.format("%s", seq_no));
					//listSeq.add(seq_no);
				}
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		String entity_id_for_late = listEntityID_for_late.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id_for_late = listProjID_for_late.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id_for_late = listPBL_for_late.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no_for_late = listSeq_for_late.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		System.out.printf("Display value of entity_id_for_late: (%s)%n", entity_id_for_late);
		System.out.printf("Display value of proj_id_for_late: (%s)%n", proj_id_for_late);
		System.out.printf("Display value of pbl_id_for_late id: (%s)%n", pbl_id_for_late);
		System.out.printf("Display value of seq_no_for_late: (%s)%n", seq_no_for_late);

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		String logo = FncGlobal.GetString("SELECT company_logo from mf_company where co_id = '"+txtCoID.getValue()+"'");
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ logo));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);

		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		Map<String, Object> mapParameters_for_late = new HashMap<String, Object>();
		String logo_for_late = FncGlobal.GetString("SELECT company_logo from mf_company where co_id = '"+txtCoID.getValue()+"'");
		mapParameters_for_late.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ logo_for_late));
		mapParameters_for_late.put("company", txtCompany.getText());
		mapParameters_for_late.put("prepared_by", UserInfo.Alias);

		mapParameters_for_late.put("entity_id", entity_id_for_late);
		mapParameters_for_late.put("proj_id", proj_id_for_late);
		mapParameters_for_late.put("pbl_id", pbl_id_for_late);
		mapParameters_for_late.put("seq_no", seq_no_for_late);
		mapParameters_for_late.put("batch_no", txtBatchID.getValue());
		mapParameters_for_late.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());
		
		if (!listEntityID.isEmpty()) {
			System.out.println("listEntityID condition");
			FncReport.generateReport("/Reports/rptNoticeOfTurnover_ihf_for_late.jasper", "Notice of Turn-over (Cash)", mapParameters);
		}
		if (!listEntityID_for_late.isEmpty()) {
			System.out.println("listEntityID_for_late condition");
			FncReport.generateReport("/Reports/rptNoticeOfTurnover_ihf_for_late.jasper", "Notice of Turn-over (Cash)", mapParameters_for_late);
		}
	}
	
	private void generateNoticeAssumedTurnover(){

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenqlogo.png"));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeofAssumedTurnover.jasper", "Notice of Turn-over (Cash)", mapParameters);
	}
	
	private void generateFinalDemandDues_Pmt(){

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		if(txtCoID.getValue().equals("02")) {
			mapParameters.put("letterhead_logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenqlogo.png"));
		}
		
		mapParameters.put("signature", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "atty_lyka_sig.jpg"));

		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());
		
		if(cmbBuyerType.getSelectedItem().equals("IHF")) {
			FncReport.generateReport("/Reports/rptPMD_DemandLetterPmts_IHF.jasper", "Final Demand to Pay - IHF", mapParameters);
		}
		if(cmbBuyerType.getSelectedItem().equals("HDMF")){
			FncReport.generateReport("/Reports/rptPMD_DemandLettersPmts_Pagibig.jasper", "Final Demand to Pay - HDMF", mapParameters);
		}
	}

	private void Generate() {
		Boolean blnClient = false;
		Boolean blnNotice = false;

		if (!(Boolean) txtClient.getText().equals("")) {
			blnClient = true;
		}

		if (!(Boolean) txtNotice.getText().equals("")) {
			blnNotice = true;
		}

		if (blnClient && blnNotice) {
			//GenerateNotice();	
		} else {
			if (!blnClient) {
				JOptionPane.showMessageDialog(getContentPane(), "No client was selected.");
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "No notice type was selected.");
			}
		}
	}

	/*private void GenerateNotice() {
		Boolean blnProceed = false;
		String strNotice = txtNoticeID.getValue().toString();
		String strBatch = FncGlobal.GetString("select trim(to_char((max(batch_no::int) + 1), '0000000000')) from rf_client_notices");

		String entity_id = txtClientID.getValue();
		String proj_id = txtProID.getValue();
		String pbl_id = strUnit;
		String strSeq = _RealTimeDebit.GetValue("select trim(seq_no::char(5)) from rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and status_id = 'A'");
		Integer intRec = FncGlobal.GetInteger("select (max(rec_id::int) + 1) from rf_client_notices");

		if (hdmfNot.NoticeExists(entity_id, proj_id, strUnit, strSeq, strNotice)) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "A notice of the same type that is yet to be sent exists\nwithin the client's notice records. Do you wish to proceed?", 
					"Confirm", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				blnProceed = true;
			} else {
				blnProceed = false;
			}
		} else {
			blnProceed = true;
		}

		if (blnProceed) {
			if (strNotice.equals("127")) {
				blnProceed = hdmfNot.QualifiedForFirstFilingFirstNotice(entity_id, proj_id, pbl_id, strSeq, intRec);
			} else if (strNotice.equals("128")) {
				blnProceed = hdmfNot.QualifiedForFirstFilingFinalNotice(entity_id, proj_id, pbl_id, strSeq, intRec);
			}
		}

		if (blnProceed) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Generate " + txtNotice.getText() + " for " + txtClient.getText() + "?", 
					"Confirm", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				blnProceed = true;
			} else {
				blnProceed = false;
			}
		}

		if (blnProceed) {
			pgUpdate db = new pgUpdate();
			String sql = "insert into rf_client_notices (rec_id, entity_id, projcode, pbl_id, seq_no, part_id, notice_id, stage_id, dateprep, preparedby, batch_no, status_id) \n" +
					"values ('"+intRec+"'::int, '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+"::int, '013', \n" +
					"'"+strNotice+"', 1, Now(), '"+UserInfo.EmployeeCode+"', '"+strBatch+"', 'A');";

			System.out.println(sql);
			db.executeUpdate(sql, false);
			db.commit();
			txtBatchID.setValue(strBatch);
			JOptionPane.showMessageDialog(getContentPane(), txtNotice.getText() + " created.");
			Preview();
		} else {
			strBatch = "";
			JOptionPane.showMessageDialog(getContentPane(), "Notice generation cancelled.");
		}
	}*/

	private void GenerateLoanReturnedNotice() {
		pgUpdate dbExec = new pgUpdate();

		dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_hdmf", true);
		dbExec.commit();

		for (int x = 0; x < modelPagibigNotices.getRowCount(); x++) {
			if ((Boolean) modelPagibigNotices.getValueAt(x, 0)) {
				dbExec = new pgUpdate();
				dbExec.executeUpdate("insert into tmp_hdmf values ('"+modelPagibigNotices.getValueAt(x, 1)+"', '"+UserInfo.EmployeeCode+"')", true);
				dbExec.commit();
			}
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", txtBatchID.getValue().toString());
		mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("company_address", "");
		mapParameters.put("notice_type_id", "130");
		mapParameters.put("prepared_by", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("prepared_by_code", UserInfo.EmployeeCode);
		FncReport.generateReport("/Reports/rpt_hdmf_ReturnedHDMF_FirstNotice.jasper", "Returned from HDMF First Notice", "", mapParameters);

		String strLoanRetBatch = FncGlobal.GetString("select trans_no\n" + 
				"from rf_buyer_status x\n" + 
				"where x.byrstatus_id = '43' and \n" + 
				"exists(select * from rf_client_notices y where y.batch_no = '0000000413' and y.entity_id = x.entity_id \n" + 
				"and y.projcode = x.proj_id and y.pbl_id = x.pbl_id and y.seq_no = x.seq_no and y.status_id = 'A')\n" + 
				"limit 1"); 

		mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("02_BatchNo", txtBatchID.getValue().toString());
		mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("04_UserCode", FncGlobal.GetString("select login_id from rf_system_user where emp_code = '"+UserInfo.EmployeeCode+"'"));
		mapParameters.put("05_NoticeType", "First Notice - Returned from HDMF");
		FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticeTransmittal.jasper", "Returned from HDMF Notice Transmittal", "", mapParameters);

		pgSelect dbSel = new pgSelect();
		String strSQL = "select distinct region::varchar(155) from view_notice_transmittal('"+txtBatchID.getValue().toString()+"')";
		dbSel.select(strSQL);

		if (dbSel.getRowCount() > 1) {
			for (int x = 0; x < dbSel.getRowCount(); x++) {
				System.out.println("");
				System.out.println("dbExec.getResult()[x]: " + dbSel.getResult()[x][0]);

				mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
				mapParameters.put("02_BatchNo", txtBatchID.getValue().toString());
				mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
				mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
				mapParameters.put("05_NoticeType", "First Notice - Returned from HDMF");
				mapParameters.put("06_region", dbSel.getResult()[x][0]);
				FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticePhilPostTransmittal.jasper", "Returned from HDMF Notice PhilPost Transmittal - " + dbSel.getResult()[x][0], "", mapParameters);		
			}
		} else {
			mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
			mapParameters.put("02_BatchNo", txtBatchID.getValue().toString());
			mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
			mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
			mapParameters.put("05_NoticeType", "First Notice - Returned from HDMF");
			mapParameters.put("06_region", dbSel.getResult()[0][0]);
			FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticePhilPostTransmittal.jasper", "Returned from HDMF Notice PhilPost Transmittal - " + dbSel.getResult()[0][0], "", mapParameters);
		}
	}
	
	private boolean fncCheckIfForLate(String entity_id, String proj_id, String pbl_id) {
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS\n"
				+ "(\n"
				+ "	select re.entity_id as jsystem_id,tqftni.* from tmp_qualified_for_to_notice_itsreal tqftni\n"
				+ "	left join rf_entity re on TRIM(re.entity_name) = TRIM(tqftni.entity_name) and re.status_id = 'A'\n"
				+ "	where re.entity_id = '"+entity_id+"'\n"
				+ "	and tqftni.proj_id = '"+proj_id+"'\n"
				+ "	and tqftni.pbl_id = '"+pbl_id+"'\n"
				+ ");";
		db.select(SQL);
		return (boolean) db.getResult()[0][0];
	}
	
	private String sqlProject(String co_id){
		return "SELECT \n" + 
			   "proj_id as \"ID\", \n" + 
			   "proj_name as \"Project\", \n" + 
			   "proj_alias as \"Alias\"\n" + 
			   "FROM mf_project\n" + 
			   "WHERE co_id = '"+co_id+"';";
	}
	
	private void setBuyerType(String batch_no) {
		String buyer_type = "";
		
		pgSelect db = new pgSelect();
		String SQL = "select b.buyer_type\n"
				+ "from rf_client_notices a\n"
				+ "LEFT JOIN tmp_dues_for_collection b on b.entity_id = a.entity_id and b.proj_id = a.projcode and b.pbl_id = a.pbl_id and b.seq_no = a.seq_no \n"
				+ "where a.batch_no = '"+batch_no+"'\n"
				+ "group by b.buyer_type;";
		db.select(SQL);
		
		if(db.isNotNull()) {
			for(int x= 0; x<= db.getRowCount(); x++) {
				buyer_type = (String) db.getResult()[0][0];
			}
		}
		
		if(buyer_type.equals("PAGIBIG")) {
			cmbBuyerType.setSelectedIndex(2);
		}else {
			cmbBuyerType.setSelectedIndex(1);
		}
	}
}
