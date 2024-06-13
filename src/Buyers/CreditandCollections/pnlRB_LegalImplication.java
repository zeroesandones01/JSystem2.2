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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Buyers.CreditandCollections.PastDueProcessing_v2.PdAndBBCancelThread;
import Database.pgSelect;
import tablemodel.modelRB_LegalImplication;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.FncComponent;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;

public class pnlRB_LegalImplication extends JPanel implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 149866368170342223L;

	private JPanel pnlNorth;

	private JPanel pnlNoticeType;
	private JLabel lblSelectType;
	private JComboBox cmbNoticeType;

	private JPanel pnlGenerate;

	private JLabel lblGenerateBy;
	private JComboBox cmbGenerateBy;

	private JPanel pnlClient;
	private JLabel lblClient;
	private _JLookup lookupClient;
	private _JXTextField txtClient;

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

	private JPanel pnlButtonProcess;
	private JLabel lblDueDate;
	private _JDateChooser dateDueDate;
	private JLabel lblSequence;
	private _JXTextField txtSequence;

	private JButton btnPreview;

	private JScrollPane scrollNotices;
	private _JTableMain tblNotices;
	private modelRB_LegalImplication modelNotices;
	private JList rowHeaderNotices;

	private JPanel pnlSouth;
	private JButton btnGenerate;
	private JPanel pnlNavigation;
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnCancel;

	private Map<String, String> mapNotices = new HashMap<String, String>();
	private _RegularBillingAndNotices method = new _RegularBillingAndNotices();
	String SQL_CLIENT;
	String SQL_PROJECT;

	private _JDateChooser dateFortesting;

	public pnlRB_LegalImplication(String SQL_CLIENT, String SQL_PROJECT) {
		this.SQL_CLIENT = SQL_CLIENT;
		this.SQL_PROJECT = SQL_PROJECT;
		initGUI();
	}

	public pnlRB_LegalImplication(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlRB_LegalImplication(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlRB_LegalImplication(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JPanel(new BorderLayout(3, 3));
			this.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setPreferredSize(new Dimension(783, 198));
			{
				JPanel pnlSelect = new JPanel(new BorderLayout(3, 3));
				pnlNorth.add(pnlSelect, BorderLayout.NORTH);
				pnlSelect.setPreferredSize(new Dimension(0, 25));
				{
					lblSelectType = new JLabel("Select ");
					pnlSelect.add(lblSelectType, BorderLayout.WEST);
					lblSelectType.setPreferredSize(new Dimension(70, 25));
				}
				{
					JPanel pnlCombo = new JPanel(new BorderLayout());
					pnlSelect.add(pnlCombo, BorderLayout.CENTER);
					{
						cmbNoticeType = new JComboBox(new DefaultComboBoxModel(_RegularBillingAndNotices.getNoticeType(mapNotices)));
						pnlCombo.add(cmbNoticeType, BorderLayout.WEST);
						cmbNoticeType.setActionCommand("notice_type");
						cmbNoticeType.setPreferredSize(new java.awt.Dimension(250, 25));
						cmbNoticeType.addActionListener(this);
					}
				}
			}
			{
				pnlGenerate = new JPanel(new GridLayout(5, 1, 3, 3));
				pnlNorth.add(pnlGenerate, BorderLayout.CENTER);
				pnlGenerate.setBorder(components.JTBorderFactory.createTitleBorder("Generate"));
				{
					pnlNoticeType = new JPanel(new BorderLayout());
					pnlGenerate.add(pnlNoticeType);
					pnlNoticeType.setPreferredSize(new Dimension(0, 55));
					{
						lblGenerateBy = new JLabel("Generate By");
						pnlNoticeType.add(lblGenerateBy, BorderLayout.WEST);
						lblGenerateBy.setPreferredSize(new Dimension(90, 29));
					}
					{
						JPanel pnlCombo = new JPanel(new BorderLayout());
						pnlNoticeType.add(pnlCombo, BorderLayout.CENTER);
						{
							cmbGenerateBy = new JComboBox(new String[]{"Client", "Project"});
							pnlCombo.add(cmbGenerateBy, BorderLayout.WEST);
							cmbGenerateBy.setActionCommand("GenerateBy");
							cmbGenerateBy.setSelectedIndex(1);
							cmbGenerateBy.setPreferredSize(new Dimension(120, 29));
							cmbGenerateBy.addActionListener(this);
						}
						{
							JPanel pnl2 = new JPanel(new BorderLayout());
							pnlCombo.add(pnl2, BorderLayout.EAST);
							pnl2.setPreferredSize(new Dimension(190, 29));
							{
								lblBatchNo = new JLabel("Batch No");
								pnl2.add(lblBatchNo, BorderLayout.WEST);
								lblBatchNo.setPreferredSize(new Dimension(70, 29));
							}
							{
								lookupBatchNo = new _JLookup(null, "Batch No.");
								pnl2.add(lookupBatchNo, BorderLayout.CENTER);
								lookupBatchNo.setReturnColumn(0);
								lookupBatchNo.setLookupSQL(_RegularBillingAndNotices.getPreviewBatchNo("02"));
								lookupBatchNo.setPreferredSize(new Dimension(120, 29));
								lookupBatchNo.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											Object[] details = _RegularBillingAndNotices.getBatchDetails((String) data[0]);
											lookupProject.setValue((String) details[0]);
											txtProject.setText((String) details[1]);
											dateDueDate.setDate((Date) details[2]);

											rowHeaderNotices.setModel(new DefaultListModel());
											_RegularBillingAndNotices.viewLegalImplicationNotices(modelNotices, (String) data[0]);
											scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNotices.getRowCount())));
											tblNotices.packAll();

											btnState(false, true, true, false, false);
										}
									}
								});
							}
						}
					}
				}
				{
					pnlClient = new JPanel(new BorderLayout());
					pnlGenerate.add(pnlClient);
					{
						lblClient = new JLabel("Client");
						pnlClient.add(lblClient, BorderLayout.WEST);
						lblClient.setPreferredSize(new Dimension(90, 29));
					}
					{
						JPanel pnl1 = new JPanel(new BorderLayout(3, 3));
						pnlClient.add(pnl1, BorderLayout.CENTER);
						{
							lookupClient = new _JLookup(null, "Client");
							pnl1.add(lookupClient, BorderLayout.WEST);
							lookupClient.setPrompt("Client ID");
							lookupClient.setReturnColumn(0);
							lookupClient.setPreferredSize(new Dimension(120, 24));
							lookupClient.setLookupSQL(method.getSQLPerClient( ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim()));
							lookupClient.addLookupListener(new LookupListener() {//XXX
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										txtClient.setText((String) data[1]);

										lookupProject.setValue((String) data[6]);
										txtProject.setText((String) data[7]);
										lookupPhase.setValue((String) data[3]);
										txtPhase.setText((String) data[2]);
										txtSequence.setText(Integer.toString((Integer) data[4]));
										
										pgSelect db = new pgSelect();
										
										db.select("SELECT scheddate::date FROM view_card_schedule('"+data[0]+"', '"+data[6]+"', '"+data[3]+"', "+data[4]+") where settled = false order by scheddate ASC  limit 1;");
										
										dateDueDate.setDate((Date)db.Result[0][0]);
										
									}
								}
							});
						}
						{
							txtClient = new _JXTextField("Client Name");
							pnl1.add(txtClient, BorderLayout.CENTER);
						}
					}
				}
				{
					pnlProject = new JPanel(new BorderLayout());
					pnlGenerate.add(pnlProject);
					{
						lblProject = new JLabel("Project");
						pnlProject.add(lblProject, BorderLayout.WEST);
						lblProject.setPreferredSize(new Dimension(90, 29));
					}
					{
						JPanel pnl1 = new JPanel(new BorderLayout(3, 3));
						pnlProject.add(pnl1, BorderLayout.CENTER);
						{
							lookupProject = new _JLookup(null, "Project");
							pnl1.add(lookupProject, BorderLayout.WEST);
							lookupProject.setPrompt("ID");
							lookupProject.setReturnColumn(0);
							lookupProject.setLookupSQL(SQL_PROJECT);
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
						lblPhase.setPreferredSize(new Dimension(90, 29));
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
								lookupPhase.setPrompt("ID");
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
						{
							JPanel pnl2 = new JPanel(new BorderLayout());
							pnlPhaseCenter.add(pnl2, BorderLayout.CENTER);
							{
								JPanel pnl3 = new JPanel(new BorderLayout());
								pnl2.add(pnl3, BorderLayout.EAST);
								pnl3.setPreferredSize(new Dimension(190, 29));
								{
									lblSequence = new JLabel("Seq. No.");
									pnl3.add(lblSequence, BorderLayout.WEST);
									lblSequence.setVisible(false);
									lblSequence.setPreferredSize(new Dimension(70, 29));
								}
								{
									txtSequence = new _JXTextField("Sequence");
									pnl3.add(txtSequence, BorderLayout.CENTER);
									txtSequence.setVisible(false);
									txtSequence.setHorizontalAlignment(JTextField.CENTER);
								}
							}
						}
					}
				}
				{
					pnlButtonProcess = new JPanel(new BorderLayout());
					pnlGenerate.add(pnlButtonProcess);
					{
						lblDueDate = new JLabel("Due Date");
						pnlButtonProcess.add(lblDueDate, BorderLayout.WEST);
						lblDueDate.setPreferredSize(new Dimension(90, 29));
					}
					
					{
						JPanel pnl2 = new JPanel(new BorderLayout());
						pnlButtonProcess.add(pnl2, BorderLayout.CENTER);
						pnl2.setPreferredSize(new Dimension(90, 29));
						{

							JPanel pnl1 = new JPanel(new GridLayout(1, 2, 3, 3));
							pnl2.add(pnl1, BorderLayout.WEST);
							{
								dateDueDate = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnl1.add(dateDueDate, BorderLayout.WEST);
								dateDueDate.setPreferredSize(new Dimension(120, 25));
								
								dateDueDate.addDateListener(new DateListener() {

									@Override
									public void datePerformed(DateEvent event) {
										String day = "";	
										day = dateFormat_day(dateDueDate.getDate());

										System.out.println("DATE " +day );
										if (day == null) {

										}else{
											if (!(day.equals("07") || day.equals("14") || day.equals("21") || day.equals("28"))) {
												dateDueDate.setDate(null);
												JOptionPane.showMessageDialog(null,"Please select [07], [14], [21], [28] for due day","Date", JOptionPane.INFORMATION_MESSAGE);
												return;
											}
										}
									 }
								});
							}
							{
								dateFortesting = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnl1.add(dateFortesting, BorderLayout.WEST);
								dateFortesting.setPreferredSize(new Dimension(120, 25));
								
								dateFortesting.addDateListener(new DateListener() {

									@Override
									public void datePerformed(DateEvent event) {
										String day = "";	
										day = dateFormat_day(dateFortesting.getDate());

									/*	System.out.println("DATE " +day );
										if (day == null) {

										}else{
											if (!(day.equals("07") || day.equals("14") || day.equals("21") || day.equals("28"))) {
												dateFortesting.setDate(null);
												JOptionPane.showMessageDialog(null,"Please select [07], [14], [21], [28] for due day","Date", JOptionPane.INFORMATION_MESSAGE);
												return;
											}
										}*/
									 }
								});
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
				modelNotices = new modelRB_LegalImplication();
				modelNotices.setEditable(false);
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

	private void btnState(Boolean sGenerate, Boolean sPreview, Boolean sNew, Boolean sSave, Boolean sCancel) {
		btnGenerate.setEnabled(sGenerate);
		btnPreview.setEnabled(sPreview);
		btnNew.setEnabled(sNew);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void clearFields() {
		lookupClient.setValue(null);
		txtClient.setText("");
		lookupProject.setValue(null);
		txtProject.setText("");
		lookupPhase.setValue(null);
		txtPhase.setText("");
		dateDueDate.setDate(null);
		txtSequence.setText("");
		dateDueDate.setDate(null);

		rowHeaderNotices.setModel(new DefaultListModel());
		modelNotices.clear();
		scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer("0"));
	}

	private void newRegularBilling() {
		refreshRegularBilling(true);
		cmbNoticeType.setEnabled(false);
		lookupBatchNo.setValue(null);
		lookupBatchNo.setEditable(false);
		//lookupBatchNo.setValue(_RegularBillingAndNotices.getNewBathcNo());

		clearFields();

		modelNotices.clear();
		modelNotices.setEditable(true);
		btnState(true, false, false, false, true);

		String selectedItem = (String) cmbGenerateBy.getSelectedItem();
		if(selectedItem.equals("Client")){
			generatebyClient();
		}else{
			generatebyProject();
		}
	}

	private void cancelRegularBilling() {
		refreshRegularBilling(false);
		lblSelectType.setEnabled(true);
		cmbNoticeType.setEnabled(true);
		lblBatchNo.setEnabled(true);
		lookupBatchNo.setEnabled(true);
		lookupBatchNo.setEditable(true);
		lookupBatchNo.setValue(null);

		String notice_type_id = ((String) cmbNoticeType.getSelectedItem()).split("-")[0].trim();
		
		if(lookupBatchNo.isEditable()){
			lookupBatchNo.setLookupSQL(_RegularBillingAndNotices.getPreviewBatchNo(notice_type_id));
		}

		clearFields();

		rowHeaderNotices.setModel(new DefaultListModel());
		modelNotices.clear();
		scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer("0"));
		tblNotices.packAll();

		modelNotices.setEditable(false);
		btnState(false, false, true, false, false);
	}

	private void generatebyClient() {
		lblClient.setEnabled(true);
		lookupClient.setEnabled(true);
		txtClient.setEnabled(true);

		lookupProject.setEditable(true);
		lookupProject.setEditable(false);

		lookupPhase.setEditable(true);
		lookupPhase.setEditable(false);

		lblPhase.setText("Unit");
		txtPhase.setPrompt("Unit Description");
		lblSequence.setVisible(true);
		txtSequence.setVisible(true);
	}

	private void generatebyProject() {
		lblClient.setEnabled(false);
		lookupClient.setEnabled(false);
		txtClient.setEnabled(false);

		lookupProject.setEditable(true);
		lookupProject.setEditable(true);

		lookupPhase.setEditable(true);
		lookupPhase.setEditable(true);

		lblPhase.setText("Phase");
		txtPhase.setPrompt("Phase Name");
		lblSequence.setVisible(false);
		txtSequence.setVisible(false);
	}

	private void refreshRegularBilling(Boolean enable) {
		FncComponent.setComponentsEnabled(pnlGenerate, enable);
	}

	public class displayRegularBilling_Thread implements Runnable{

		@Override
		public void run() {
			
			displayRegularBilling();
			
		} 
		
		
	}
	
	
	private void displayRegularBilling() {
		
		if(toGenerate()){
			
			FncGlobal.startProgress("Please Wait. . .");
			
			String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();

			pgSelect db = new pgSelect();
			
			if (notice_id.equals("03")) {
				db.select("select datesent from rf_client_notices where notice_id = '02' and status_id = 'A'");
				
				if (db.isNotNull()) {
					if (db.Result[0][0] == null || db.Result[0][0].equals("")) {
						JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please send the notice before generating the new notice.", "Notices", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}	
			}
			
			if (notice_id.equals("83")) {
				db.select("select datesent from rf_client_notices where notice_id = '03' and status_id = 'A'");
				
				if (db.isNotNull()) {
					if (db.Result[0][0] == null || db.Result[0][0].equals("")) {
						JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please send the notice before generating the new notice.", "Notices", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}	
			}
			
			if (notice_id.equals("85")) {
				db.select("select datesent from rf_client_notices where notice_id = '83' and status_id = 'A'");
				
				if (db.isNotNull()) {
					if (db.Result[0][0] == null || db.Result[0][0].equals("")) {
						JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please send the notice before generating the new notice.", "Notices", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}	
			}
			

			if (notice_id.equals("127")) {
				db.select("select datesent from rf_client_notices where notice_id = '85' and status_id = 'A'");
				
				if (db.isNotNull()) {
					if (db.Result[0][0] == null || db.Result[0][0].equals("")) {
						JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please send the notice before generating the new notice.", "Notices", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}	
			}
			
			
			if (notice_id.equals("85") || notice_id.equals("127") || notice_id.equals("83") ) {
				
				if (cmbGenerateBy.getSelectedIndex() == 0) {
					
					String entityID = lookupClient.getValue();
					String projID = lookupProject.getValue();
					String pblID = lookupPhase.getValue();
					String seqNo = txtSequence.getText();
					
					db.select("SELECT * FROM rf_client_notices WHERE entity_id = '"+entityID+"' AND projcode = '"+projID+"' AND pbl_id = '"+pblID+"' AND seq_no = "+seqNo+" AND notice_id = '"+notice_id+"' AND status_id = 'A' limit 1");
				}
				
				if (db.isNotNull()) {
										
					if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "This account has existing notice. \n Generate new notice anyway?", "Generate", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						
						
						rowHeaderNotices.setModel(new DefaultListModel());
						_RegularBillingAndNotices.displayLegalImplicationNotices(modelNotices, notice_id, lookupProject.getValue(), lookupPhase.getValue(), dateDueDate.getDate(),
								lookupClient.getValue(), lookupPhase.getValue(), txtSequence.getText(),dateFortesting.getDate());
						scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNotices.getRowCount())));
						tblNotices.packAll();

						btnState(true, false, false, (tblNotices.getRowCount() > 0), true);
					}
					
				}else{
					rowHeaderNotices.setModel(new DefaultListModel());
					_RegularBillingAndNotices.displayLegalImplicationNotices(modelNotices, notice_id, lookupProject.getValue(), lookupPhase.getValue(), dateDueDate.getDate(),
							lookupClient.getValue(), lookupPhase.getValue(), txtSequence.getText(),dateFortesting.getDate());
					scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNotices.getRowCount())));
					tblNotices.packAll();

					btnState(true, false, false, (tblNotices.getRowCount() > 0), true);
				}
				
			}else{
				
				rowHeaderNotices.setModel(new DefaultListModel());
				_RegularBillingAndNotices.displayLegalImplicationNotices(modelNotices, notice_id, lookupProject.getValue(), lookupPhase.getValue(), dateDueDate.getDate(),
						lookupClient.getValue(), lookupPhase.getValue(), txtSequence.getText(),dateFortesting.getDate());
				scrollNotices.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblNotices.getRowCount())));
				tblNotices.packAll();

				btnState(true, false, false, (tblNotices.getRowCount() > 0), true);
			}
			FncGlobal.stopProgress();
		}
		
	}

	private Boolean toGenerate() {
		String selectedItem = (String) cmbGenerateBy.getSelectedItem();
		if(selectedItem.equals("Client")){
			if(lookupClient.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Client.", "Generate", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(dateDueDate.getDate() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Due Date.", "Generate", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}else{
			if(lookupProject.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project.", "Generate", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			//--- 2017-06-27 Request by Ma'am Jhing Want to remove filter phase because she want to per project to generate. 
			/*if(lookupPhase.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Phase.", "Generate", JOptionPane.WARNING_MESSAGE);
				return false;
			}*/ 
			
			if(dateDueDate.getDate() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Due Date.", "Generate", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		return true;
	}

	public void generateNewBatchNo() {
		lookupBatchNo.setValue(_RegularBillingAndNotices.getNewBathcNo());
	}

	@Override
	public void actionPerformed(ActionEvent e) {//XXX action
		String action = e.getActionCommand();

		if(action.equals("notice_type")){
			String notice_type_id = ((String) ((JComboBox)e.getSource()).getSelectedItem()).split("-")[0].trim();
			//System.out.printf("Notice Type ID: %s%n", notice_type_id);
			if(lookupBatchNo.isEditable()){
				cancelRegularBilling();

				lookupBatchNo.setLookupSQL(_RegularBillingAndNotices.getPreviewBatchNo(notice_type_id));
				/*lookupBatchNo.setValue(null);
				lookupProject.setValue(null);
				txtProject.setText("");
				dateDueDate.setDate(null);*/
				
			}
			lookupClient.setLookupSQL(method.getSQLPerClient( ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim()));
		}

		if(action.equals("GenerateBy")){
			clearFields();

			String selectedItem = (String) ((JComboBox)e.getSource()).getSelectedItem();
			
			if(selectedItem.equals("Client")){
				generatebyClient();
			}else{
				generatebyProject();
			}
			System.out.println("Generate By");
		}

		if(action.equals("Generate")){
			System.out.println("Generate Thread");
			new Thread(new displayRegularBilling_Thread()).start();
			
		}

		if(action.equals("Preview")){
			if(lookupBatchNo.getValue() != null){
				String notice_type_id = ((String) cmbNoticeType.getSelectedItem()).split("-")[0].trim();
				
				Transmittal(lookupBatchNo.getValue());
				if(notice_type_id.equals("03")) {
					PreviewNFD(lookupBatchNo.getValue());
				}else {
					Preview(lookupBatchNo.getValue());
				}
			}
		}

		if(action.equals("New")){
			newRegularBilling();
		}

		if (e.getSource().equals(btnSave)) {

			if(hasSelected()){
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();

					String batch_no = _RegularBillingAndNotices.saveLegalImplicationNotices(modelNotices, lookupProject.getValue(), notice_id, lookupBatchNo.getValue());

					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), String.format("Client(s) has been saved. Batch No: %s", batch_no), action, JOptionPane.INFORMATION_MESSAGE);
					Transmittal(batch_no);
					Preview(batch_no);
					
					//cancelRegularBilling();
					//displayRegularBilling();
					btnState(false, true, false, false, true);
					
					
				}
			}else{
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select client(s) to save.", action, JOptionPane.WARNING_MESSAGE);
			}
		
		}

		if(action.equals("Cancel")){
			cancelRegularBilling();
		}
	}

	
	/**ADDED BY CHRISTIAN FOR DUE DATE*/
	public String dateFormat_day(Date date){
		String strdate = null;
		DateFormat df = new SimpleDateFormat("dd");
		if (!(date == null)) {
			strdate = df.format(date); 
		}

		return strdate;
	}
	
	/**
	 * Added  Christian C. Paquibot (2016-05-24)
	 * Change method preview
	 * 
	 */

	private void Transmittal(String batch_no){
		
		String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();
		//String batch_no = lookupBatchNo.getValue();
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
		//FncReport.generateReport("/Reports/rptTransmittalForPostOffice.jasper", "Transmittal For Post Office", mapParameters);			
		
	}
	
	public void Preview(String batch_no){
		System.out.println("Preview Notice");
		String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();
		String report_name = mapNotices.get(notice_id);
		String alias = UserInfo.Alias;
		//String batch_no = lookupBatchNo.getValue();
		String comp = FncGlobal.GetString("select  b.company_name from mf_project a\n" + 
				"left join mf_company b on a.co_id = b.co_id where a.proj_id = '"+lookupProject.getValue()+"'");

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
		mapParameters.put("notice_type_id", notice_id);
		mapParameters.put("batch_no", batch_no);
		mapParameters.put("p_alias", alias);//**ADDED BY JED VICEDO 2019-02-20 DCRF NO. 1281 : ADDITIONAL NOTICE HLURB**//
		//mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION"); Commentted by Erick 2022-10-25
		mapParameters.put("company", comp);
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put("from_card", false);

		FncReport.generateReport(String.format("/Reports/%s.jasper", report_name), String.format("Batch No.: %s", batch_no), mapParameters);
	}
	
	public void PreviewNFD(String batch_no){
		System.out.println("Preview Notice");
		String notice_id = ((String)cmbNoticeType.getSelectedItem()).split("-")[0].trim();
		String report_name = mapNotices.get(notice_id);
		String alias = UserInfo.Alias;
		String comp = "";
		//String batch_no = lookupBatchNo.getValue();
		if(lookupProject.getValue().equals("015")) {// Added by Erick 2021-06-24
			comp = "CENQHOMES DEVELOPMENT CORPORATION";
		}else {
			comp = "VERDANTPOINT DEVELOPMENT CORPORATION";
			System.out.println("Dumaan dito verdant");
		}
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
		mapParameters.put("notice_type_id", notice_id);
		mapParameters.put("batch_no", batch_no);
		mapParameters.put("p_alias", alias);//**ADDED BY JED VICEDO 2019-02-20 DCRF NO. 1281 : ADDITIONAL NOTICE HLURB**//
		//mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("company", comp);
		mapParameters.put("prepared_by", UserInfo.Alias);

		FncReport.generateReport("/Reports/rptNoticeOfFinalDemand_v3.jasper", String.format("Batch No.: %s", batch_no), mapParameters);
	}
	
	private Boolean hasSelected() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < modelNotices.getRowCount(); x++){
			listSelected.add((Boolean) modelNotices.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}
}
