package Reports.CreditAndCollections;

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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;
import Buyers.LoansManagement._PagibigStatusMonitoring;
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
import tablemodel.modelSpecialNotices;

public class SpecialNotices extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = -1834829101657201605L;
	static String title = "Special Notices";
	Dimension frameSize = new Dimension(750,500);

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
	private _JLookup txtNoticeID;
	private _JLookup txtBatchID;


	private JCheckBox chkBatch;

	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;

	private JTextField txtNotice;

	private _JTableMain tblSpecialNotices;
	private modelSpecialNotices modelSpecialNotices;
	private JScrollPane scrollSpecialNotices;
	private JList rowHeaderSpecialNotices;

	private JButton btnTag;
	private JButton btnPreview;
	private JButton btnCancel;



	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public SpecialNotices() {
		super(title, true, true, false, true);
		initGUI();
	}

	public SpecialNotices(String title) {
		super(title);
		initGUI();
	}

	public SpecialNotices(String title, boolean resizable, boolean closable,
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
				pnlNorth.setPreferredSize(new Dimension(0, 150));
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Notice Details"));
				{
					JPanel pnlNorthLabel = new JPanel(new GridLayout(5, 1, 3, 3));
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
						lblNotice = new JLabel("Notice Type");
						pnlNorthLabel.add(lblNotice);
					}
					{
						chkBatch = new JCheckBox("Batch No");
						pnlNorthLabel.add(chkBatch, BorderLayout.WEST);
						chkBatch.setHorizontalAlignment(JTextField.LEFT);
						chkBatch.setPreferredSize(new Dimension(180, 0));
						chkBatch.setSelected(false);
						chkBatch.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evChk) {
								Boolean blnDo = evChk.getStateChange() == ItemEvent.SELECTED;
								txtBatchID.setValue(null);
								txtBatchID.setEnabled(blnDo);

								modelSpecialNotices.clear();
								scrollSpecialNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
								rowHeaderSpecialNotices.setModel(new DefaultListModel());

							}
						});
					}
				}
				{
					JPanel pnlNorthComponent = new JPanel(new GridLayout(5, 1, 3, 3));
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
							txtCoID.setPreferredSize(new Dimension(50, 0));
							txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtCompany.setText(data[1].toString());
										txtProID.setLookupSQL(sqlProject(txtCoID.getValue()));
										txtProID.setValue(null);
										txtProject.setText("");

									} else {
								
									}
								}
							});
						}
						{
							txtCompany = new JTextField("");
							pnlCompany.add(txtCompany, BorderLayout.CENTER);
							txtCompany.setHorizontalAlignment(JTextField.CENTER);
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
						JPanel pnlNotice = new JPanel(new BorderLayout(5, 5));
						pnlNorthComponent.add(pnlNotice);
						{
							txtNoticeID = new _JLookup();
							pnlNotice.add(txtNoticeID, BorderLayout.WEST);
							txtNoticeID.setHorizontalAlignment(JTextField.CENTER);
							txtNoticeID.setLookupSQL(_SpecialNotices.NoticeSQL());
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
										}

										txtNotice.setText(data[1].toString());
										txtBatchID.setLookupSQL(_SpecialNotices.Batch(txtNoticeID.getValue()));
										
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
							txtBatchID.setLookupSQL(_SpecialNotices.Batch(""));
							txtBatchID.setEnabled(false);
							txtBatchID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									
									if(txtNoticeID.getValue().equals("145")){
										_SpecialNotices.displayNTC_for_TCT_Ecar_Processing(modelSpecialNotices, rowHeaderSpecialNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue());
										scrollSpecialNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSpecialNotices.getRowCount())));
										tblSpecialNotices.packAll();
									}
									
									if(txtNoticeID.getValue().equals("146")){
										_SpecialNotices.displayNTC_for_TCT_Under_Buyers_Name(modelSpecialNotices, rowHeaderSpecialNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue());
										scrollSpecialNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSpecialNotices.getRowCount())));
										tblSpecialNotices.packAll();
									}

									btnState(false, true, true);

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
					scrollSpecialNotices = new JScrollPane();
					pnlCenter.add(scrollSpecialNotices, BorderLayout.CENTER);
					{

						modelSpecialNotices = new modelSpecialNotices();

						tblSpecialNotices = new _JTableMain(modelSpecialNotices);
						scrollSpecialNotices.setViewportView(tblSpecialNotices);
						scrollSpecialNotices.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						tblSpecialNotices.setSortable(false);
						tblSpecialNotices.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq No");
					}
					{
						rowHeaderSpecialNotices = tblSpecialNotices.getRowHeader();
						rowHeaderSpecialNotices.setModel(new DefaultListModel());
						scrollSpecialNotices.setRowHeaderView(rowHeaderSpecialNotices);
						scrollSpecialNotices.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
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

		if(txtNoticeID.getValue().equals("145") || txtNoticeID.getValue().equals("146") ){


			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
			ArrayList<String> listSeq = new ArrayList<String>();
			
			for(int x= 0; x<modelSpecialNotices.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelSpecialNotices.getValueAt(x, 0);

				if(isSelected){
					String entity_id = (String) modelSpecialNotices.getValueAt(x, 7);
					String proj_id = (String) modelSpecialNotices.getValueAt(x, 8);
					String pbl_id = (String) modelSpecialNotices.getValueAt(x, 9);
					Integer seq_no = (Integer) modelSpecialNotices.getValueAt(x, 10);

					listEntityID.add(String.format("%s", entity_id));
					listProjID.add(String.format("%s", proj_id));
					listPBL.add(String.format("%s", pbl_id));
					listSeq.add(String.format("%s", seq_no));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

			Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("company_name", txtCompany.getText());
			mapParameters.put("prepared_by", UserInfo.FullName2);
			mapParameters.put("entity_id", entity_id);
			mapParameters.put("proj_id", proj_id);
			mapParameters.put("pbl_id", pbl_id);
			mapParameters.put("seq_no", seq_no);
			mapParameters.put("batch_no", batch_no);


			FncReport.generateReport("/Reports/rptNTCList_IncompleteDocs_First_Notice.jasper", "List of Clients (Transmittal)", mapParameters);
		}
	}

	private void previewTransmittal2(String batch_no){

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("batch_no", batch_no);
		FncReport.generateReport("/Reports/subrptTransmittalForPostOffice.jasper", "Notices Transmittal", mapParameters);

	}


	private void cancelPagibigNotices(){
		modelSpecialNotices.clear();
		rowHeaderSpecialNotices.setModel(new DefaultListModel());
		scrollSpecialNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		btnState(false, false, false);
	}

	public void actionPerformed(ActionEvent act) {
		String actionCommand = act.getActionCommand();

		if(actionCommand.equals("Tag")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

				if(txtNoticeID.getValue().equals("145")){
					String batch_no = _SpecialNotices.saveNTC_for_TCT_Ecar_Processing(modelSpecialNotices);

					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNTC_for_TCT_Ecar_Processing();

					modelSpecialNotices.clear();
					scrollSpecialNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderSpecialNotices.setModel(new DefaultListModel());
				}
	
				if(txtNoticeID.getValue().equals("146")){
					String batch_no = _SpecialNotices.saveNTC_for_TCT_Under_Buyers_Name(modelSpecialNotices);

					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNTC_for_TCT_Under_Buyers_Name();

					modelSpecialNotices.clear();
					scrollSpecialNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderSpecialNotices.setModel(new DefaultListModel());
				}
	
				btnState(false, true, false);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Save", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if(actionCommand.equals("Preview")){
			
			if(txtNoticeID.getValue().equals("145")){
				generateNTC_for_TCT_Ecar_Processing();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				} else {
					previewTransmittal(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("146")){
				generateNTC_for_TCT_Under_Buyers_Name();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				} else {
					previewTransmittal(txtBatchID.getValue());
				}
			}


		}

		if(actionCommand.equals("Cancel")){
			cancelPagibigNotices();
		}
	}

	private void displayQualifiedAccts() {
		FncGlobal.startProgress("Generating Qualified Accounts");

		new Thread(new Runnable() {
			public void run() {
				
				System.out.println("Dumaan dito sa DIsplay");
				
				if(txtNoticeID.getValue().equals("145")){
					System.out.println("Generate Ecar");
					_SpecialNotices.displayNTC_for_TCT_Ecar_Processing(modelSpecialNotices, rowHeaderSpecialNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue());
					scrollSpecialNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSpecialNotices.getRowCount())));
					tblSpecialNotices.packAll();
				}
				
				System.out.println("End of generate ecar");
				
				if(txtNoticeID.getValue().equals("146")){
					_SpecialNotices.displayNTC_for_TCT_Under_Buyers_Name(modelSpecialNotices, rowHeaderSpecialNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue());
					scrollSpecialNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblSpecialNotices.getRowCount())));
					tblSpecialNotices.packAll();
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

	
	
	private void generateNTC_for_TCT_Ecar_Processing(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();

		for(int x= 0; x<modelSpecialNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelSpecialNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelSpecialNotices.getValueAt(x, 7);
				String proj_id = (String) modelSpecialNotices.getValueAt(x, 8);
				String pbl_id = (String) modelSpecialNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelSpecialNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
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

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ logo));

		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeForTCT_ECar_Processing.jasper", "Notice for TCT - E-Car Processing", mapParameters);
	}

	private void generateNTC_for_TCT_Under_Buyers_Name(){

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();

		for(int x= 0; x<modelSpecialNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelSpecialNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelSpecialNotices.getValueAt(x, 7);
				String proj_id = (String) modelSpecialNotices.getValueAt(x, 8);
				String pbl_id = (String) modelSpecialNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelSpecialNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
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
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ logo));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());
		
		FncReport.generateReport("/Reports/rptNoticeOfTurnOver_Cash.jasper", "Notice of Turn-over (Cash)", mapParameters);
	}

	
	private String sqlProject(String co_id){
		return "SELECT \n" + 
			   "proj_id as \"ID\", \n" + 
			   "proj_name as \"Project\", \n" + 
			   "proj_alias as \"Alias\"\n" + 
			   "FROM mf_project\n" + 
			   "WHERE co_id = '"+co_id+"';";
	}
}

