package Buyers.CreditandCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import tablemodel.modelRB_RegularNotices;
import DateChooser._JDateChooser;
import Functions.FncComponent;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;

public class pnlRB_RegularNotices extends JPanel implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 149866368170342223L;


	private JPanel pnlGenerate;

	private JPanel pnlNoticeType;
	private JLabel lblSelectType;
	private JComboBox cmbNoticeType;

	private JPanel pnlBatchNo;
	private JLabel lblBatchNo;
	private _JLookup lookupBatchNo;

	private JPanel pnlProject;
	private JLabel lblProject;
	private _JLookup lookupProject;
	private _JXTextField txtProject;

	private JPanel pnlPhase;
	private JLabel lblPhase;
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;

	private JPanel pnlVenue;
	private JLabel lblVenue;
	private _JLookup lookupVenue;
	private _JXTextField txtVenue;

	private JPanel pnlButtonProcess;
	private JLabel lblDueDate;
	private _JDateChooser dateDueDate;

	private JLabel lblTime;
	private JSpinner spinTime;

	private JButton btnGenerate;



	private JButton btnPreview;

	private JScrollPane scrollNotices;
	private _JTableMain tblNotices;
	private modelRB_RegularNotices modelNotices;
	private JList rowHeaderNotices;

	private JPanel pnlSouth;
	private JPanel pnlNavigation;
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnCancel;
	
	private Map<String, String> mapNotices = new HashMap<String, String>();

	String SQL_PROJECT;


	public pnlRB_RegularNotices(String SQL_PROJECT) {
		this.SQL_PROJECT = SQL_PROJECT;
		initGUI();
	}

	public pnlRB_RegularNotices(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlRB_RegularNotices(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlRB_RegularNotices(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlGenerate = new JPanel(new GridLayout(6, 1, 3, 3));
			this.add(pnlGenerate, BorderLayout.NORTH);
			pnlGenerate.setBorder(components.JTBorderFactory.createTitleBorder("Generate"));
			pnlGenerate.setPreferredSize(new Dimension(783, 200));
			{
				pnlNoticeType = new JPanel(new BorderLayout());
				pnlGenerate.add(pnlNoticeType);
				pnlNoticeType.setPreferredSize(new Dimension(0, 55));
				{
					lblSelectType = new JLabel("Select ");
					pnlNoticeType.add(lblSelectType, BorderLayout.WEST);
					lblSelectType.setPreferredSize(new Dimension(70, 29));
				}
				{
					JPanel pnlCombo = new JPanel(new BorderLayout());
					pnlNoticeType.add(pnlCombo, BorderLayout.CENTER);
					{
						cmbNoticeType = new JComboBox(new DefaultComboBoxModel(_RegularBillingAndNotices.getRegularNoticeType(mapNotices)));
						pnlCombo.add(cmbNoticeType, BorderLayout.WEST);
						cmbNoticeType.setActionCommand("notice_type");
						cmbNoticeType.setPreferredSize(new java.awt.Dimension(400, 22));
						cmbNoticeType.addActionListener(this);
					}
				}
			}
			{
				pnlBatchNo = new JPanel(new BorderLayout());
				pnlGenerate.add(pnlBatchNo);
				{
					lblBatchNo = new JLabel("Batch No");
					pnlBatchNo.add(lblBatchNo, BorderLayout.WEST);
					lblBatchNo.setPreferredSize(new Dimension(70, 29));
				}
				{
					JPanel pnl1 = new JPanel(new BorderLayout());
					pnlBatchNo.add(pnl1, BorderLayout.CENTER);
					{
						String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();
						
						lookupBatchNo = new _JLookup(null, "Batch No.");
						pnl1.add(lookupBatchNo, BorderLayout.WEST);
						lookupBatchNo.setReturnColumn(0);
						lookupBatchNo.setLookupSQL(_RegularBillingAndNotices.getPreviewBatchNo(notice_id));
						lookupBatchNo.setPreferredSize(new Dimension(120, 29));
						lookupBatchNo.addLookupListener(new LookupListener() {//XXX Batch No.
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									Object[] details = _RegularBillingAndNotices.getBatchDetails((String) data[0]);
									lookupProject.setValue((String) details[0]);
									txtProject.setText((String) details[1]);
									dateDueDate.setDate((Date) details[2]);

									displayRegularNotices(true);

									btnState(false, true, true, false, false);
								}
							}
						});
					}
				}
			}
			{
				pnlProject = new JPanel(new BorderLayout());
				pnlGenerate.add(pnlProject);
				{
					lblProject = new JLabel("Project");
					pnlProject.add(lblProject, BorderLayout.WEST);
					lblProject.setPreferredSize(new Dimension(70, 29));
				}
				{
					JPanel pnl1 = new JPanel(new BorderLayout(3, 3));
					pnlProject.add(pnl1, BorderLayout.CENTER);
					{
						lookupProject = new _JLookup(null, "Project");
						pnl1.add(lookupProject, BorderLayout.WEST);
						lookupProject.setReturnColumn(0);
						lookupProject.setLookupSQL(SQL_PROJECT());
						lookupProject.setPreferredSize(new Dimension(50, 24));
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtProject.setText((String) data[1]);
									lookupPhase.setLookupSQL(_JInternalFrame.SQL_PHASE((String) data[0]));
								}
							}
						});
					}
					{
						txtProject = new _JXTextField("Project Name");
						pnl1.add(txtProject, BorderLayout.CENTER);
					}
				}
			}
			{
				pnlPhase = new JPanel(new BorderLayout());
				pnlGenerate.add(pnlPhase);
				{
					lblPhase = new JLabel("Phase");
					pnlPhase.add(lblPhase, BorderLayout.WEST);
					lblPhase.setPreferredSize(new Dimension(70, 29));
				}
				{
					JPanel pnlPhaseCenter = new JPanel(new BorderLayout());
					pnlPhase.add(pnlPhaseCenter, BorderLayout.CENTER);
					{
						JPanel pnlPhaseCenterWest = new JPanel(new BorderLayout(3, 3));
						pnlPhaseCenter.add(pnlPhaseCenterWest, BorderLayout.WEST);
						pnlPhaseCenterWest.setPreferredSize(new Dimension(200, 0));
						{
							lookupPhase = new _JLookup(null, "Phase", "Please select project.");
							pnlPhaseCenterWest.add(lookupPhase, BorderLayout.WEST);
							lookupPhase.setReturnColumn(0);
							lookupPhase.setPreferredSize(new Dimension(50, 24));
							lookupPhase.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtPhase.setText((String) data[1]);
									}
								}
							});
						}
						{
							txtPhase = new _JXTextField("Phase Name");
							pnlPhaseCenterWest.add(txtPhase, BorderLayout.CENTER);
						}
					}
				}
			}
			{
				pnlVenue = new JPanel(new BorderLayout());
				pnlGenerate.add(pnlVenue);
				{
					lblVenue = new JLabel("Venue");
					pnlVenue.add(lblVenue, BorderLayout.WEST);
					lblVenue.setPreferredSize(new Dimension(70, 29));
				}
				{
					JPanel pnl1 = new JPanel(new BorderLayout(3, 3));
					pnlVenue.add(pnl1, BorderLayout.CENTER);
					{
						lookupVenue = new _JLookup(null, "Venue");
						pnl1.add(lookupVenue, BorderLayout.WEST);
						lookupVenue.setReturnColumn(0);
						lookupVenue.setLookupSQL(_RegularBillingAndNotices.SQL_VENUE());
						lookupVenue.setPreferredSize(new Dimension(50, 24));
						lookupVenue.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									txtVenue.setText((String) data[1]);
								}
							}
						});
					}
					{
						txtVenue = new _JXTextField("Venue Name");
						pnl1.add(txtVenue, BorderLayout.CENTER);
					}
				}
			}
			{
				pnlButtonProcess = new JPanel(new BorderLayout());
				pnlGenerate.add(pnlButtonProcess);
				{
					lblDueDate = new JLabel("Date");
					pnlButtonProcess.add(lblDueDate, BorderLayout.WEST);
					lblDueDate.setPreferredSize(new Dimension(70, 29));
				}
				{
					JPanel pnl1 = new JPanel(new BorderLayout(3, 3));
					pnlButtonProcess.add(pnl1, BorderLayout.CENTER);
					{
						dateDueDate = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnl1.add(dateDueDate, BorderLayout.WEST);
						dateDueDate.setPreferredSize(new Dimension(120, 29));
					}
					{
						JPanel pnl2 = new JPanel(new BorderLayout(3, 3));
						pnl1.add(pnl2, BorderLayout.CENTER);
						{
							JPanel pnl3 = new JPanel(new BorderLayout(3, 3));
							pnl2.add(pnl3, BorderLayout.WEST);
							pnl3.setPreferredSize(new Dimension(200, 29));
							{
								lblTime = new JLabel("Time  ", JLabel.TRAILING);
								pnl3.add(lblTime, BorderLayout.WEST);
								lblTime.setPreferredSize(new Dimension(100, 29));
							}
							{
								spinTime = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
								pnl3.add(spinTime, BorderLayout.CENTER);
								spinTime.setEditor(new JSpinner.DateEditor(spinTime, "hh:mm a"));
								spinTime.setEnabled(false);

								JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)spinTime.getEditor();
								spinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
							}
						}
					}
				}
			}
		}
		{
			scrollNotices = new JScrollPane();
			this.add(scrollNotices, BorderLayout.CENTER);
			scrollNotices.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollNotices.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					tblNotices.clearSelection();
				}
			});
			{
				modelNotices = new modelRB_RegularNotices();
				modelNotices.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						if(e.getType() == 1){
							((DefaultListModel)rowHeaderNotices.getModel()).addElement(modelNotices.getRowCount());
							if(modelNotices.getRowCount() == 0){
								rowHeaderNotices.setModel(new DefaultListModel());
							}
						}
					}
				});

				tblNotices = new _JTableMain(modelNotices);
				scrollNotices.setViewportView(tblNotices);
				tblNotices.hideColumns("Client ID", "PBL ID", "Seq.", "Part. ID", "Last Bill", "Next Bill");
			}
			{
				rowHeaderNotices = tblNotices.getRowHeader();
				rowHeaderNotices.setModel(new DefaultListModel());
				scrollNotices.setRowHeaderView(rowHeaderNotices);
				scrollNotices.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNotices.getRowCount())));
			}
		}
		{
			pnlSouth = new JPanel(new BorderLayout());
			this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setPreferredSize(new Dimension(783, 30));
			{
				JPanel pnlNavigation = new JPanel(new GridLayout(1, 2, 5, 5));
				pnlSouth.add(pnlNavigation, BorderLayout.WEST);
				pnlNavigation.setPreferredSize(new Dimension(205, 28));
				{
					btnGenerate = new JButton("Generate");
					pnlNavigation.add(btnGenerate);
					btnGenerate.addActionListener(this);
				}
				{
					btnPreview = new JButton("Preview");
					pnlNavigation.add(btnPreview);
					btnPreview.addActionListener(this);
				}
			}
			{
				pnlNavigation = new JPanel(new GridLayout(1, 3, 5, 5));
				pnlSouth.add(pnlNavigation, BorderLayout.EAST);
				pnlNavigation.setPreferredSize(new Dimension(310, 28));
				{
					btnNew = new JButton("New");
					pnlNavigation.add(btnNew);
					btnNew.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlNavigation.add(btnSave);
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlNavigation.add(btnCancel);
					btnCancel.addActionListener(this);
				}
			}
		}
		cancelRegularBilling();
	}
	
	private String SQL_PROJECT() {//XXX Project
		String SQL = "SELECT TRIM(proj_id)::VARCHAR as \"ID\", TRIM(proj_name) as \"Project Name\", TRIM(proj_alias)::VARCHAR as \"Alias\" FROM mf_project WHERE co_id IN ('02', '01', '04', '05') and status_id = 'A' ORDER BY proj_id;";
		return SQL;
	}

	private void btnState(Boolean sGenerate, Boolean sPreview, Boolean sNew, Boolean sSave, Boolean sCancel) {
		btnGenerate.setEnabled(sGenerate);
		btnPreview.setEnabled(sPreview);
		btnNew.setEnabled(sNew);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void newRegularBilling() {
		refreshRegularBilling(true);
		cmbNoticeType.setEnabled(false);
		lookupBatchNo.setEnabled(false);
		//lookupBatchNo.setValue(_RegularBillingAndNotices.getNewBathcNo());

		lookupProject.setValue(null);
		txtProject.setText("");
		lookupPhase.setValue(null);
		txtPhase.setText("");
		lookupVenue.setValue(null);
		txtVenue.setText("");
		dateDueDate.setDate(null);

		String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();
		if(!notice_id.equals("47")){
			lblVenue.setEnabled(false);
			lookupVenue.setEnabled(false);
			txtVenue.setEnabled(false);
			lblDueDate.setEnabled(false);
			dateDueDate.setEnabled(false);
			lblTime.setEnabled(false);
			spinTime.setEnabled(false);
		}
		rowHeaderNotices.setModel(new DefaultListModel());
		modelNotices.clear();
		scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer("0"));
		tblNotices.showColumns("Select");

		btnState(true, false, false, true, true);
	}

	private void cancelRegularBilling() {
		refreshRegularBilling(false);
		lblSelectType.setEnabled(true);
		cmbNoticeType.setEnabled(true);
		lblBatchNo.setEnabled(true);
		lookupBatchNo.setEnabled(true);
		lookupBatchNo.setValue(null);

		lookupProject.setValue(null);
		txtProject.setText("");
		lookupPhase.setValue(null);
		txtPhase.setText("");
		lookupVenue.setValue(null);
		txtVenue.setText("");
		dateDueDate.setDate(null);

		rowHeaderNotices.setModel(new DefaultListModel());
		modelNotices.clear();
		scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer("0"));
		tblNotices.showColumns("Select");
		tblNotices.packAll();

		btnState(false, false, true, false, false);
	}

	private void refreshRegularBilling(Boolean enable) {
		FncComponent.setComponentsEnabled(pnlGenerate, enable);
	}

	private void displayRegularNotices(Boolean retrieve) {//XXX displayRegularNotices
		
		if(retrieve){
			String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();

			rowHeaderNotices.setModel(new DefaultListModel());
			_RegularBillingAndNotices.displayRegularNotices(modelNotices, notice_id, lookupProject.getValue(), lookupPhase.getValue(), retrieve, lookupBatchNo.getValue());
			scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNotices.getRowCount())));
			tblNotices.hideColumns("Select");
			tblNotices.packAll();
		}else{
			if(toGenerate()){
				String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();

				rowHeaderNotices.setModel(new DefaultListModel());
				_RegularBillingAndNotices.displayRegularNotices(modelNotices, notice_id, lookupProject.getValue(), lookupPhase.getValue(), retrieve, lookupBatchNo.getValue());
				scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNotices.getRowCount())));
				tblNotices.showColumns("Select");
				tblNotices.packAll();
			}
		}
	}

	private Boolean hasSelected() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < modelNotices.getRowCount(); x++){
			listSelected.add((Boolean) modelNotices.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

	public void generateNewBatchNo() {
		lookupBatchNo.setValue(_RegularBillingAndNotices.getNewBathcNo());
	}

	private Boolean toGenerate() {
		String selectedItem = (String) cmbNoticeType.getSelectedItem();
		selectedItem = selectedItem.split("-")[0].trim();
		System.out.printf("Selected: %s%n", selectedItem);
		
		if(lookupProject.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project.", "Generate", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		//---//--- 2017-06-27 Request by Ma'am Jhing Want to remove filter phase because she want to per project to generate. 
		/*if(lookupPhase.getValue() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Phase.", "Generate", JOptionPane.WARNING_MESSAGE);
			return false;
		}*/
		
		if(selectedItem.equals("47")){
			if(lookupVenue.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Venue.", "Generate", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(dateDueDate.getDate() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Date.", "Generate", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(spinTime.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input Time.", "Generate", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {//XXX action
		String action = arg0.getActionCommand();

		if(action.equals("notice_type")){
			String notice_type_id = ((String) ((JComboBox)arg0.getSource()).getSelectedItem()).split("-")[0].trim();
			//System.out.printf("Notice Type ID: %s%n", notice_type_id);
			if(lookupBatchNo.isEditable()){
				cancelRegularBilling();

				lookupBatchNo.setLookupSQL(_RegularBillingAndNotices.getPreviewBatchNo(notice_type_id));
				/*lookupBatchNo.setValue(null);

				lookupProject.setValue(null);
				txtProject.setText("");
				dateDueDate.setDate(null);*/
			}
		}

		if(action.equals("Generate")){
			displayRegularNotices(false);
		}

		if(action.equals("Preview")){
			
			
			
			String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();
			String report_name = mapNotices.get(notice_id);
			String batch_no = lookupBatchNo.getValue();

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("notice_type_id", notice_id);
			mapParameters.put("batch_no", batch_no);
			mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
			mapParameters.put("prepared_by", UserInfo.Alias);
			Transmittal();
			FncReport.generateReport(String.format("/Reports/%s.jasper", report_name), String.format("Batch No.: %s", batch_no), mapParameters);
		
		}

		if(action.equals("New")){
			newRegularBilling();
		}

		if(action.equals("Save")){
			if(hasSelected()){
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();

					String batch_no =  _RegularBillingAndNotices.saveRegularNotices(modelNotices, lookupProject.getValue(), notice_id, lookupBatchNo.getValue());

					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), String.format("Client(s) has been saved.%nBatch No: %s", batch_no), action, JOptionPane.INFORMATION_MESSAGE);
					cancelRegularBilling();
					displayRegularNotices(false);
					//lookupBatchNo.setValue(_RegularBillingAndNotices.getNewBathcNo());
				}
			}else{
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select client(s) to save.", action, JOptionPane.WARNING_MESSAGE);
			}
		}

		if(action.equals("Cancel")){
			cancelRegularBilling();
		}
	}
   private void Transmittal(){
		
		String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();
		String batch_no = lookupBatchNo.getValue();
		/**go
		 * Added Transmittal Report - Christian C. Paquibot (2016-01-27)
		 * 
		 */
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("notice_type_id", notice_id);
		mapParameters.put("batch_no", batch_no);
		mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("prepared_by", UserInfo.Alias);
		
		
		FncReport.generateReport(String.format("/Reports/%s.jasper", "subrptTransmittalForPostOffice"), "Transmittal For Post Office", mapParameters);			
		
	}
}
