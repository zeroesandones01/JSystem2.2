package Reports.ClientServicing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Lookup.LookupEvent;
import Lookup.LookupListener;

import components._JCheckBox;
import components._JCheckListItem;
import components._JCheckListRenderer;
import components._JInternalFrame;
import components._JListFind;

public class AgingReport_OLD extends _JInternalFrame implements _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -784594086970045416L;
	
	static String title = "Aging Report";
	Dimension frameSize = new Dimension(585, 400);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	
	private JXPanel pnlMain;

	private JXPanel pnlNorth;

	private JXLabel lblServer;
	private JComboBox cmbServer;
	private JCheckBox chkBasedOnPayments;

	private _JCheckBox chkCompany;
	private _JListFind lookupCompany;
	private JTextField txtCompany;

	private _JCheckBox chkProject;
	private _JListFind lookupProject;
	private JTextField txtProjectName;

	private _JCheckBox chkModel;
	private _JListFind lookupModel;
	private JTextField txtModel;

	private JXLabel lblReport;
	private JComboBox cmbReport;

	private JLabel lblCutOff;
	private JRadioButton rbNetSellingPrice;
	private JRadioButton rbGrossSellingPrice;
	private _JDateChooser dateCutOff;
	private JTextField txtProjectAlias;
	private _JCheckBox chkAllPhase;
	private JList listPhase;
	private JScrollPane scrollPhase;

	private JXPanel pnlCenter;

	private JXPanel pnlSouth;
	private JButton btnPreview;

	public AgingReport_OLD() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AgingReport_OLD(String title) {
		super(title);
		initGUI();
	}

	public AgingReport_OLD(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new Dimension(frameSize));
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JXPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout());
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JXPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(null);
				//pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Filter"));//XXX JTBorderFactory
				pnlNorth.setPreferredSize(new Dimension(588, 315));
				{
					lblServer = new JXLabel("Server", JLabel.TRAILING);
					//pnlNorth.add(lblServer);
					lblServer.setBounds(25, 27, 60, 25);
				}
				{
					cmbServer = new JComboBox(new String[]{""});
					//pnlNorth.add(cmbServer);
					cmbServer.setActionCommand("Server");
					cmbServer.setBounds(96, 27, 174, 25);
					cmbServer.addActionListener(this);
				}
				{
					lblCutOff = new JLabel("Cut Off", JLabel.TRAILING);
					pnlNorth.add(lblCutOff);
					lblCutOff.setBounds(25, 13, 60, 25);
				}
				{
					dateCutOff = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
					pnlNorth.add(dateCutOff);
					dateCutOff.setDate(Calendar.getInstance().getTime());
					dateCutOff.setBounds(96, 13, 174, 25);
				}
				{
					chkCompany = new _JCheckBox("Company", true);
					pnlNorth.add(chkCompany);
					chkCompany.setBounds(10, 43, 81, 25);
					chkCompany.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							boolean selected = e.getStateChange() == ItemEvent.SELECTED;

							lookupCompany.setEnabled(selected);
							chkProject.setEnabled(selected);
							lookupProject.setEnabled(e.getStateChange() == ItemEvent.SELECTED && chkProject.isSelected());

							if(selected){
								displayPhase(cmbServer.getSelectedItem().toString(), lookupCompany.getValue(), lookupProject.getValue());
							}else{
								displayPhase(cmbServer.getSelectedItem().toString(), null, null);
							}

							KEYBOARD_MANAGER.focusNextComponent(chkCompany);
						}
					});
				}
				{
					lookupCompany = new _JListFind(null, "Company");
					pnlNorth.add(lookupCompany);
					lookupCompany.setReturnColumn(0);
					lookupCompany.setBounds(96, 43, 50, 25);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {//XXX Project
							Object[] data = ((_JListFind)event.getSource()).getDataSet();
							if(data != null){
								txtCompany.setText(data[1].toString());

								lookupProject.setCompany(data[0].toString());

								displayPhase(cmbServer.getSelectedItem().toString(), lookupCompany.getValue(), lookupProject.getValue());

								KEYBOARD_MANAGER.focusNextComponent();
							}else{
								txtCompany.setText("");
							}
						}
					});
				}
				{
					txtCompany = new JTextField();
					pnlNorth.add(txtCompany);
					txtCompany.setEditable(false);
					txtCompany.setBounds(150, 43, 400, 25);
				}
				{
					chkProject = new _JCheckBox("Project", false);
					pnlNorth.add(chkProject);
					chkProject.setBounds(10, 73, 81, 25);
					chkProject.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							boolean selected = e.getStateChange() == ItemEvent.SELECTED;

							lookupProject.setEnabled(selected);

							if(selected){
								displayPhase(cmbServer.getSelectedItem().toString(), lookupCompany.getValue(), lookupProject.getValue());
							}else{
								displayPhase(cmbServer.getSelectedItem().toString(), lookupCompany.getValue(), null);
							}

							KEYBOARD_MANAGER.focusNextComponent(chkProject);
						}
					});
				}
				{
					lookupProject = new _JListFind(null, "Project", "Please select company.");
					pnlNorth.add(lookupProject);
					lookupProject.setEnabled(false);
					lookupProject.setReturnColumn(0);
					lookupProject.setBounds(96, 73, 50, 25);
					lookupProject.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {//XXX Project
							Object[] data = ((_JListFind)event.getSource()).getDataSet();
							if(data != null){
								txtProjectName.setText(data[1].toString());
								txtProjectAlias.setText(data[2].toString());

								displayPhase(cmbServer.getSelectedItem().toString(), lookupCompany.getValue(), lookupProject.getValue());
								KEYBOARD_MANAGER.focusNextComponent();
							}else{
								txtProjectName.setText("");
							}
						}
					});
				}
				{
					txtProjectName = new JTextField();
					pnlNorth.add(txtProjectName);
					txtProjectName.setEditable(false);
					txtProjectName.setBounds(150, 73, 346, 25);
				}
				{
					txtProjectAlias = new JTextField();
					pnlNorth.add(txtProjectAlias);
					txtProjectAlias.setHorizontalAlignment(JTextField.CENTER);
					txtProjectAlias.setEditable(false);
					txtProjectAlias.setBounds(500, 73, 50, 25);
				}
				{
					chkModel = new _JCheckBox("Model", false);
					pnlNorth.add(chkModel);
					chkModel.setBounds(10, 103, 81, 25);
					chkModel.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							boolean selected = e.getStateChange() == ItemEvent.SELECTED;

							lookupModel.setEnabled(selected);
							KEYBOARD_MANAGER.focusNextComponent(chkModel);
						}
					});
				}
				{
					lookupModel = new _JListFind(null, "Model");
					pnlNorth.add(lookupModel);
					lookupModel.setEnabled(false);
					lookupModel.setReturnColumn(0);
					lookupModel.setBounds(96, 103, 50, 25);
					lookupModel.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {//XXX Project
							Object[] data = ((_JListFind)event.getSource()).getDataSet();
							if(data != null){

								KEYBOARD_MANAGER.focusNextComponent();
							}else{

							}
						}
					});
				}
				{
					txtModel = new JTextField();
					pnlNorth.add(txtModel);
					txtModel.setEditable(false);
					txtModel.setBounds(150, 103, 346, 25);
				}
				{
					lblReport = new JXLabel("Report", JLabel.TRAILING);
					pnlNorth.add(lblReport);
					lblReport.setBounds(25, 133, 60, 25);
				}
				{
					cmbReport = new JComboBox(new Object[]{"Aging"});
					pnlNorth.add(cmbReport);
					cmbReport.setActionCommand("Server");
					cmbReport.setBounds(96, 133, 174, 25);
					cmbReport.addActionListener(this);
				}
				{
					chkAllPhase = new _JCheckBox("All Phase", true);
					pnlNorth.add(chkAllPhase);
					chkAllPhase.setBounds(10, 163, 81, 25);
					chkAllPhase.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							boolean selected = e.getStateChange() == ItemEvent.SELECTED;

							int rowCount = listPhase.getModel().getSize();
							for(int x=0; x < rowCount; x++){
								_JCheckListItem item = (_JCheckListItem) listPhase.getModel().getElementAt(x);
								item.setSelected(selected);
								listPhase.repaint(listPhase.getCellBounds(x, x));
							}
						}
					});
				}
				{
					scrollPhase = new JScrollPane();
					pnlNorth.add(scrollPhase);
					scrollPhase.setBorder(lineBorder);
					scrollPhase.setBounds(96, 163, 305, 100);
					{
						listPhase = new JList();
						scrollPhase.setViewportView(listPhase);
						listPhase.setCellRenderer(new _JCheckListRenderer());
						listPhase.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						listPhase.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent event) {
								JList list = (JList) event.getSource();

								// Get index of item clicked
								int index = list.locationToIndex(event.getPoint());
								_JCheckListItem item = (_JCheckListItem) list.getModel().getElementAt(index);
								System.out.printf("%s - %s%n", index, item.toString());

								// Toggle selected state
								item.setSelected(!item.isSelected());

								// Repaint cell
								list.repaint(list.getCellBounds(index, index));
							}
						});
					}
				}
				{
					rbGrossSellingPrice = new JRadioButton("Gross Selling Price");
					pnlNorth.add(rbGrossSellingPrice);
					rbGrossSellingPrice.setBounds(407, 163, 150, 25);

					rbNetSellingPrice = new JRadioButton("Net Selling Price", true);
					pnlNorth.add(rbNetSellingPrice);
					rbNetSellingPrice.setBounds(407, 192, 150, 25);

					ButtonGroup grpButton = new ButtonGroup();
					grpButton.add(rbGrossSellingPrice);
					grpButton.add(rbNetSellingPrice);
				}
				{
					chkBasedOnPayments = new JCheckBox("Based on payments", true);
					pnlNorth.add(chkBasedOnPayments);
					chkBasedOnPayments.setBounds(96, 269, 174, 19);
				}
			}
			{
				pnlCenter = new JXPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
			}
			{
				pnlSouth = new JXPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 1, 5, 5));
				pnlSouth.setPreferredSize(new Dimension(588, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
				}
			}
		}
		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(cmbServer, (JTextField) dateCutOff.getDateEditor(), lookupCompany, chkProject, lookupProject, chkModel, lookupModel, cmbReport, listPhase, btnPreview));

		displayPhase(cmbServer.getSelectedItem().toString(), lookupCompany.getValue(), lookupProject.getValue());
	}

	private void displayPhase(String server, String company, String project) {
		ArrayList<_JCheckListItem> listCheckItem = new ArrayList<_JCheckListItem>();

		Map<String, Object[]> treeMap = new TreeMap<String, Object[]>(new HashMap<String, Object[]>());
		for(Entry<String, Object[]> entry: treeMap.entrySet()){
			Object[] data = entry.getValue();
			///String sub_proj_id = (String) data[0];
			//String sub_proj_name = (String) data[1];
			String sub_proj_alias = (String) data[2];
			String proj_id = (String) data[3];
			String proj_alias = (String) data[4];
			String cdf_co_id = (String) data[5];
			String sub_server = (String) data[6];

			_JCheckListItem item = new _JCheckListItem(sub_proj_alias + " (" + proj_alias + ")");
			item.setSelected(chkAllPhase.isSelected());

			if(server.equals("All")){
				if(company != null){
					if(company.equals(cdf_co_id)){
						if(project != null){
							if(project.equals(proj_id)){
								listCheckItem.add(item);
							}
						}else{
							listCheckItem.add(item);
						}
					}
				}else{
					listCheckItem.add(item);
				}
			}else{
				if(server.equals(sub_server)){
					if(company != null){
						if(company.equals(cdf_co_id)){
							if(project != null){
								if(project.equals(proj_id)){
									listCheckItem.add(item);
								}
							}else{
								listCheckItem.add(item);
							}
						}
					}else{
						listCheckItem.add(item);
					}
				}
			}
		}

		listPhase.setModel(new DefaultComboBoxModel(listCheckItem.toArray()));
	}

}
