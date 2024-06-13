package Reports.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.design.JRDesignSortField;
import net.sf.jasperreports.engine.type.SortFieldTypeEnum;
import net.sf.jasperreports.engine.type.SortOrderEnum;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;

public class TemporaryReservedUnits extends _JInternalFrame implements _GUI, ActionListener, AncestorListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5159650933602942626L;

	static String title = "Temporary Reserved Units";
	Dimension frameSize = new Dimension(510, 270);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JXPanel pnlMain;

	private JXPanel pnlCenter;

	private JXLabel lblCompany;
	private _JLookup lookupCompany;
	private JTextField txtCompany;

	private _JLookup lookupProject;
	private JTextField txtProject;

	private _JLookup lookupPhase;
	private JXTextField txtPhase;

	private _JDateChooser dateFrom;
	private _JDateChooser dateTo;

	private JComboBox<String> cmbSorting;

	private JCheckBox chkIncludeManagementHoldAccounts;

	private JXPanel pnlSouth;
	private JButton btnPreview;

	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	public TemporaryReservedUnits() {
		super(title, false, true, false, true);
		initGUI();
	}

	public TemporaryReservedUnits(String title) {
		super(title);
		initGUI();
	}

	public TemporaryReservedUnits(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.addAncestorListener(this);
		{
			pnlMain = new JXPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlCenter = new JXPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setLayout(null);
				pnlCenter.setBorder(FncGlobal.lineBorder);
				{
					lblCompany = new JXLabel("Company");
					pnlCenter.add(lblCompany);
					//lblProject.setBorder(lineBorder);
					lblCompany.setBounds(10, 10, 75, 25);
				}
				{
					lookupCompany = new _JLookup(null, "Company");
					pnlCenter.add(lookupCompany);
					lookupCompany.setReturnColumn(0);
					lookupCompany.setLookupSQL(_JInternalFrame.SQL_COMPANY());
					lookupCompany.setBounds(90, 10, 50, 25);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {//XXX Project
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								txtCompany.setText(data[1].toString());
								txtCompany.setToolTipText(data[2].toString());

								lookupProject.setLookupSQL(SQL_PROJECT_ALL(data[0].toString()));

								manager.focusNextComponent();
							}else{
								txtCompany.setText("");
								txtCompany.setToolTipText("");
							}
						}
					});
				}
				{
					txtCompany = new JTextField();
					pnlCenter.add(txtCompany);
					txtCompany.setEditable(false);
					txtCompany.setBounds(144, 10, 341, 25);
				}
				{
					JXLabel lblProject = new JXLabel("Project");
					pnlCenter.add(lblProject);
					lblProject.setBounds(10, 40, 75, 25);
				}
				{
					lookupProject = new _JLookup(null, "Project", "Please select company.");
					pnlCenter.add(lookupProject);
					lookupProject.setReturnColumn(0);
					lookupProject.setBounds(90, 40, 50, 25);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {//XXX Project
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								txtProject.setText(data[1].toString());
								lookupPhase.setLookupSQL(SQL_PHASE_ALL(data[0].toString()));
								manager.focusNextComponent();
							}else{
								txtProject.setText("");
							}
						}
					});
				}
				{
					txtProject = new JTextField();
					pnlCenter.add(txtProject);
					txtProject.setEditable(false);
					txtProject.setBounds(144, 40, 251, 25);
				}
				{
					JXLabel lblPhase = new JXLabel("Phase");
					pnlCenter.add(lblPhase);
					lblPhase.setBounds(10, 70, 75, 25);
				}
				{
					lookupPhase = new _JLookup(null, "Phase", "Please select project.");
					pnlCenter.add(lookupPhase);
					lookupPhase.setReturnColumn(0);
					lookupPhase.setBounds(90, 70, 50, 25);
					lookupPhase.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {//XXX Phase
							Object[] data = ((_JLookup)event.getSource()).getDataSet();
							if(data != null){
								txtPhase.setText(data[1].toString());
								manager.focusNextComponent();
							}else{
								JOptionPane.showMessageDialog(FncGlobal.homeMDI, "Please select project first.", "Phase", JOptionPane.WARNING_MESSAGE);
							}
						}
					});
				}
				{
					txtPhase = new JXTextField("");
					pnlCenter.add(txtPhase);
					txtPhase.setEditable(false);
					txtPhase.setBounds(144, 70, 120, 25);
				}
				{
					JXLabel lblDateFrom = new JXLabel("Date From");
					pnlCenter.add(lblDateFrom);
					lblDateFrom.setBounds(10, 100, 75, 25);
				}
				{
					dateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
					pnlCenter.add(dateFrom);
					dateFrom.setDate(Calendar.getInstance().getTime());
					dateFrom.setBounds(90, 100, 120, 25);
				}
				{
					JXLabel lblDateTo = new JXLabel("Date To");
					pnlCenter.add(lblDateTo);
					lblDateTo.setBounds(10, 130, 75, 25);
				}
				{
					dateTo = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
					pnlCenter.add(dateTo);
					dateTo.setDate(Calendar.getInstance().getTime());
					dateTo.setBounds(90, 130, 120, 25);
				}
				{
					JXLabel lblSortBy = new JXLabel("Sort By");
					pnlCenter.add(lblSortBy);
					lblSortBy.setBounds(10, 160, 75, 25);
				}
				{
					cmbSorting = new JComboBox(new DefaultComboBoxModel(new String[] { "Client Name", "Phase-Block-Lot" }));
					pnlCenter.add(cmbSorting, BorderLayout.CENTER);
					cmbSorting.setBounds(90, 160, 200, 25);
				}
				{
					chkIncludeManagementHoldAccounts = new JCheckBox("Include Management-Hold Accounts");
					//pnlCenter.add(chkIncludeManagementHoldAccounts);
					chkIncludeManagementHoldAccounts.setBounds(90, 99, 341, 25);
				}
			}
			{
				pnlSouth = new JXPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				//pnlSouth.setBorder(lineBorder);
				//pnlSouth.setCursor(handCursor);
				pnlSouth.setLayout(new OverlayLayout(pnlSouth));
				pnlSouth.setPreferredSize(new Dimension(388, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject, lookupPhase, chkIncludeManagementHoldAccounts, btnPreview));
	}

	private List<JRSortField> sortBy(String sort_by) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("Client Name", new String[]{"c_client_name"});
		map.put("Phase-Block-Lot", new String[]{"c_phase_no", "c_phase_text", "c_block_no", "c_block_text", "c_lot_no", "c_lot_text"});

		List<JRSortField> sortList = new ArrayList<JRSortField>();
		for(String fields : map.get(sort_by)){
			JRDesignSortField sortField = new JRDesignSortField();
			sortField.setName(fields);
			sortField.setOrder(SortOrderEnum.ASCENDING);
			sortField.setType(SortFieldTypeEnum.FIELD);

			sortList.add(sortField);
		}

		return sortList;
	}

	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupCompany.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("co_id", lookupCompany.getValue());
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("company_alias", txtCompany.getToolTipText());
		mapParameters.put("proj_id", lookupProject.getValue());
		mapParameters.put("phase", lookupPhase.getValue());
		mapParameters.put("date_from", dateFrom.getDate());
		mapParameters.put("date_to", dateTo.getDate());
		mapParameters.put("prepared_by", UserInfo.Alias);
		mapParameters.put(JRParameter.SORT_FIELDS, sortBy((String) cmbSorting.getSelectedItem()));

		FncReport.generateReport("/Reports/rptTemporaryReservedUnits.jasper", title, txtProject.getText(), txtPhase.getText(), mapParameters);
	}

}