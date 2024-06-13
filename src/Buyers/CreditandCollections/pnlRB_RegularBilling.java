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

import tablemodel.modelRB_RegularBilling;
import DateChooser.DateEvent;
import DateChooser.DateListener;
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

public class pnlRB_RegularBilling extends JPanel implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 149866368170342223L;

	private JPanel pnlNorth;

	private JPanel pnlGenerate;

	private JLabel lblGenerateBy;
	private JComboBox cmbGenerateBy;

	private JPanel pnlBatchNo;
	private JLabel lblBatchNo;
	private _JLookup lookupBatchNo;

	private JPanel pnlClient;
	private JLabel lblClient;
	private _JLookup lookupClient;
	private _JXTextField txtClient;

	private JPanel pnlProject;
	private JLabel lblProject;
	private _JLookup lookupProject;
	private _JXTextField txtProject;

	private JPanel pnlPhase;
	private JLabel lblPhase;
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;

	private JPanel pnlDueDate;
	private JLabel lblDueDate;
	private _JDateChooser dateDueDate;
	private JLabel lblSequence;
	private _JXTextField txtSequence;

	private JScrollPane scrollRegularBilling;
	private _JTableMain tblRegularBilling;
	private modelRB_RegularBilling modelRegularBilling;
	private JList rowHeaderRegularBilling;

	private JPanel pnlSouth;
	private JButton btnGenerate;
	private JButton btnPreview;
	private JPanel pnlNavigation;
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnCancel;

	String SQL_CLIENT;
	String SQL_PROJECT;

	public pnlRB_RegularBilling(String SQL_CLIENT, String SQL_PROJECT) {
		this.SQL_CLIENT = SQL_CLIENT;
		this.SQL_PROJECT = SQL_PROJECT;

		initGUI();
	}

	public pnlRB_RegularBilling(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlRB_RegularBilling(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlRB_RegularBilling(LayoutManager layout, boolean isDoubleBuffered) {
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
			pnlNorth.setPreferredSize(new Dimension(783, 173));
			{
				pnlGenerate = new JPanel(new GridLayout(5, 1, 3, 3));
				pnlNorth.add(pnlGenerate, BorderLayout.CENTER);
				pnlGenerate.setBorder(components.JTBorderFactory.createTitleBorder("Generate"));
				{
					pnlBatchNo = new JPanel(new BorderLayout());
					pnlGenerate.add(pnlBatchNo);
					{
						lblGenerateBy = new JLabel("Generate By");
						pnlBatchNo.add(lblGenerateBy, BorderLayout.WEST);
						lblGenerateBy.setPreferredSize(new Dimension(90, 29));
					}
					{
						JPanel pnl1 = new JPanel(new BorderLayout());
						pnlBatchNo.add(pnl1, BorderLayout.CENTER);
						{
							cmbGenerateBy = new JComboBox(new String[]{"Client", "Project"});
							pnl1.add(cmbGenerateBy, BorderLayout.WEST);
							cmbGenerateBy.setActionCommand("GenerateBy");
							cmbGenerateBy.setSelectedIndex(1);
							cmbGenerateBy.setPreferredSize(new Dimension(120, 29));
							cmbGenerateBy.addActionListener(this);
						}
						{
							JPanel pnl2 = new JPanel(new BorderLayout());
							pnl1.add(pnl2, BorderLayout.EAST);
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
								lookupBatchNo.setLookupSQL(_RegularBillingAndNotices.getPreviewBatchNo("06"));
								lookupBatchNo.setPreferredSize(new Dimension(120, 29));
								lookupBatchNo.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();
										if(data != null){
											Object[] details = _RegularBillingAndNotices.getBatchDetails((String) data[0]);
											lookupProject.setValue((String) details[0]);
											txtProject.setText((String) details[1]);
											dateDueDate.setDate((Date) details[2]);

											rowHeaderRegularBilling.setModel(new DefaultListModel());
											_RegularBillingAndNotices.previewRegularBilling(modelRegularBilling, (String) data[0]);
											scrollRegularBilling.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRegularBilling.getRowCount())));
											tblRegularBilling.packAll();

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
							lookupClient.setLookupSQL(SQL_CLIENT);
							lookupClient.setFilterName(true);
							lookupClient.setPreferredSize(new Dimension(120, 24));
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
								txtPhase.setPreferredSize(new Dimension(150, 25));
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
					pnlDueDate = new JPanel(new BorderLayout());
					pnlGenerate.add(pnlDueDate);
					{
						lblDueDate = new JLabel("Due Date");
						pnlDueDate.add(lblDueDate, BorderLayout.WEST);
						lblDueDate.setPreferredSize(new Dimension(90, 29));
					}
					{
						JPanel pnl1 = new JPanel(new BorderLayout());
						pnlDueDate.add(pnl1, BorderLayout.CENTER);
						{
							dateDueDate = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
							pnl1.add(dateDueDate, BorderLayout.WEST);
							dateDueDate.setPreferredSize(new Dimension(120, 29));
							dateDueDate.addDateListener(new DateListener() {
								public void datePerformed(DateEvent event) {
									ArrayList<Integer> listDueDate = new ArrayList<Integer>();
									listDueDate.add(7);listDueDate.add(14);listDueDate.add(21);listDueDate.add(28);

									_JDateChooser dateChooser = (_JDateChooser) event.getSource();
									Date dueDate = dateChooser.getDate();

									Calendar calendar = Calendar.getInstance();
									calendar.setTime(dueDate);

									Integer day_of_month = calendar.get(Calendar.DAY_OF_MONTH);

									if(!listDueDate.contains(day_of_month)){
										JOptionPane.showMessageDialog(getTopLevelAncestor(), "Please select date at 7, 14, 21 and 28.", "Due Date", JOptionPane.WARNING_MESSAGE);
										dateChooser.setDate(null);
									}else{
										Boolean hasPreviousBillingDueDate = _RegularBillingAndNotices.hasPreviousBillingDueDate("06", lookupProject.getValue(), lookupPhase.getValue(), dueDate);

										if(!hasPreviousBillingDueDate){//XXX
											JOptionPane.showMessageDialog(getTopLevelAncestor(), "There is an unposted billing on previous due date.", "Due Date", JOptionPane.WARNING_MESSAGE);
											dateChooser.setDate(null);

											modelRegularBilling.clear();
											rowHeaderRegularBilling.setModel(new DefaultListModel());
											scrollRegularBilling.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRegularBilling.getRowCount())));

											btnState(true, false, false, false, true);
										}
									}
								}
							});
						}
					}
				}
			}
		}
		{
			scrollRegularBilling = new JScrollPane();
			this.add(scrollRegularBilling, BorderLayout.CENTER);
			scrollRegularBilling.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollRegularBilling.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					tblRegularBilling.clearSelection();
				}
			});
			{
				modelRegularBilling = new modelRB_RegularBilling();
				modelRegularBilling.setEditable(false);
				modelRegularBilling.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						if(e.getType() == 1){
							((DefaultListModel)rowHeaderRegularBilling.getModel()).addElement(modelRegularBilling.getRowCount());
							if(modelRegularBilling.getRowCount() == 0){
								rowHeaderRegularBilling.setModel(new DefaultListModel());
							}
						}
					}
				});

				tblRegularBilling = new _JTableMain(modelRegularBilling);
				scrollRegularBilling.setViewportView(tblRegularBilling);
				tblRegularBilling.hideColumns("Client ID", "PBL ID", "Seq.", "Part. ID", "Last Bill", "Next Bill");
			}
			{
				rowHeaderRegularBilling = tblRegularBilling.getRowHeader();
				rowHeaderRegularBilling.setModel(new DefaultListModel());
				scrollRegularBilling.setRowHeaderView(rowHeaderRegularBilling);
				scrollRegularBilling.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				scrollRegularBilling.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRegularBilling.getRowCount())));
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

		rowHeaderRegularBilling.setModel(new DefaultListModel());
		modelRegularBilling.clear();
		scrollRegularBilling.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer("0"));
	}

	private void newRegularBilling() {
		refreshRegularBilling(true);
		lookupBatchNo.setEditable(true);
		lookupBatchNo.setEditable(false);
		lookupBatchNo.setValue(_RegularBillingAndNotices.getNewBathcNo());

		clearFields();

		rowHeaderRegularBilling.setModel(new DefaultListModel());
		modelRegularBilling.clear();
		scrollRegularBilling.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer("0"));
		tblRegularBilling.packAll();

		modelRegularBilling.setEditable(true);
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
		lblBatchNo.setEnabled(true);
		lookupBatchNo.setLookupSQL(_RegularBillingAndNotices.getPreviewBatchNo("06"));
		lookupBatchNo.setEnabled(true);
		lookupBatchNo.setEditable(true);
		lookupBatchNo.setValue(null);

		clearFields();

		rowHeaderRegularBilling.setModel(new DefaultListModel());
		modelRegularBilling.clear();
		scrollRegularBilling.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer("0"));
		tblRegularBilling.packAll();

		modelRegularBilling.setEditable(false);
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
			if(lookupPhase.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Phase.", "Generate", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			if(dateDueDate.getDate() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Due Date.", "Generate", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		return true;
	}

	private void displayRegularBilling() {
		if(toGenerate()){
			rowHeaderRegularBilling.setModel(new DefaultListModel());
			_RegularBillingAndNotices.displayRegularBillingByClient(modelRegularBilling, lookupClient.getValue(), lookupProject.getValue(), lookupPhase.getValue(), txtSequence.getText(), dateDueDate.getDate());
			scrollRegularBilling.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblRegularBilling.getRowCount())));
			tblRegularBilling.packAll();

			btnState(true, false, false, (tblRegularBilling.getRowCount() > 0), true);
		}
	}

	public void generateNewBatchNo() {
		lookupBatchNo.setValue(_RegularBillingAndNotices.getNewBathcNo());
	}

	private Boolean hasSelected() {
		ArrayList<Boolean> listSelected = new ArrayList<Boolean>();
		for(int x=0; x < modelRegularBilling.getRowCount(); x++){
			listSelected.add((Boolean) modelRegularBilling.getValueAt(x, 0));
		}
		return listSelected.contains(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("GenerateBy")){
			clearFields();

			String selectedItem = (String) ((JComboBox)arg0.getSource()).getSelectedItem();
			if(selectedItem.equals("Client")){
				generatebyClient();
			}else{
				generatebyProject();
			}
		}

		if(action.equals("Generate")){
			displayRegularBilling();
		}

		if(action.equals("Preview")){
			String batch_no = lookupBatchNo.getValue();

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
			mapParameters.put("batch_no", batch_no);
			mapParameters.put("prepared_by", UserInfo.Alias);

			FncReport.generateReport("/Reports/rptBillingStatement.jasper", String.format("Batch No.: %s", batch_no), mapParameters);
		}

		if(action.equals("New")){
			newRegularBilling();
		}

		if(action.equals("Save")){
			if(hasSelected()){
				if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					_RegularBillingAndNotices.saveRegularBilling(modelRegularBilling, lookupProject.getValue(), lookupBatchNo.getValue());
					JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Client(s) has been saved.", action, JOptionPane.INFORMATION_MESSAGE);
					cancelRegularBilling();
					displayRegularBilling();
					
				}
			}else{
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Please select client(s) to save.", action, JOptionPane.WARNING_MESSAGE);
			}
		}

		if(action.equals("Cancel")){
			cancelRegularBilling();
		}
	}

}
