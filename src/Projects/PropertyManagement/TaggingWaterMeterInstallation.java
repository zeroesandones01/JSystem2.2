package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;

import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelMeterInstallation;

public class TaggingWaterMeterInstallation extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = 8885030739403324728L;

	Dimension size = new Dimension(800, 600);
	static String title = "Water Meter Tagging";

	private JPanel pnlNorth;
	private JPanel pnlNorthCenter;
	private JPanel pnlNCLabel;
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;

	private JPanel pnlNCComponent;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private _JLookup lookupProject;
	private _JXTextField txtProject;
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;
	private JButton btnGenerate;
	private JButton btnTag;

	private JPanel pnlCenter;
	private _JTableMain tblMeterInstallation;
	private JScrollPane scrollMeterInstallation;
	private JList rowHeaderMeterInstallation;
	private modelMeterInstallation modelMeterInstall;
	private _JDateChooser dteMeterInstall;
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

	public TaggingWaterMeterInstallation() {
		super(title, true, true, false, false);
		initGUI();
	}

	public TaggingWaterMeterInstallation(String title) {
		super(title);
		initGUI();
	}

	public TaggingWaterMeterInstallation(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, false, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(size);
		this.setPreferredSize(size);
		{
			JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 130));
				{
					pnlNorthCenter = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					pnlNorthCenter.setBorder(JTBorderFactory.createTitleBorder("Client Name"));
					{
						pnlNCLabel = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNorthCenter.add(pnlNCLabel, BorderLayout.WEST);
						{
							lblCompany = new JLabel("Company");
							pnlNCLabel.add(lblCompany);
						}
						{
							lblProject = new JLabel("Project");
							pnlNCLabel.add(lblProject);
						}
						{
							lblPhase = new JLabel("Phase");
							pnlNCLabel.add(lblPhase);
						}
					}
					{
						pnlNCComponent = new JPanel(new GridLayout(3, 1, 3, 3));
						pnlNorthCenter.add(pnlNCComponent, BorderLayout.CENTER);
						{
							JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlCompany);
							{
								lookupCompany = new _JLookup("Company");
								pnlCompany.add(lookupCompany, BorderLayout.WEST);
								lookupCompany.setPreferredSize(new Dimension(100, 0));
								lookupCompany.setFilterName(true);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.addLookupListener(new LookupListener() {

									@Override
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();

										if(data != null) {
											String co_id = (String) data[0];
											String company_name = (String) data[1];
											lookupCompany.setValue(co_id);
											txtCompany.setText(company_name);
										}
									}
								});
							}
							{
								txtCompany = new _JXTextField();
								pnlCompany.add(txtCompany, BorderLayout.CENTER);

							}
						}
						{
							JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlProject);
							{
								lookupProject = new _JLookup("Project");
								pnlProject.add(lookupProject, BorderLayout.WEST);
								lookupProject.setPreferredSize(new Dimension(100, 0));
								lookupProject.setFilterName(true);
								lookupProject.addFocusListener(new FocusListener() {

									@Override
									public void focusLost(FocusEvent e) {
										// TODO Auto-generated method stub

									}

									@Override
									public void focusGained(FocusEvent e) {
										lookupProject.setLookupSQL(SQL_PROJECT(lookupCompany.getValue()));
									}
								});

								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											String proj_id = (String) data[0];
											String proj_name = (String) data[1];

											lookupProject.setValue(proj_id);
											txtProject.setText(proj_name);
										}
									}
								});
							}
							{
								txtProject = new _JXTextField();
								pnlProject.add(txtProject, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlPhase = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlPhase);
							{
								lookupPhase = new _JLookup("Phase");
								pnlPhase.add(lookupPhase, BorderLayout.WEST);
								lookupPhase.setPreferredSize(new Dimension(100, 0));
								lookupPhase.setFilterName(true);
								lookupPhase.addFocusListener(new FocusListener() {

									@Override
									public void focusLost(FocusEvent e) {
										// TODO Auto-generated method stub
									}

									@Override
									public void focusGained(FocusEvent e) {
										lookupPhase.setLookupSQL(SQL_PHASE(lookupProject.getValue()));
									}
								});
							}
							lookupPhase.addLookupListener(new LookupListener() {
								@Override
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup) event.getSource()).getDataSet();
									if(data != null){
										String phase = (String) data[0];
										String phase_desc = (String) data[1];

										lookupPhase.setValue(phase);
										txtPhase.setText(phase_desc);
									}
								}
							});
							{
								txtPhase = new _JXTextField();
								pnlPhase.add(txtPhase, BorderLayout.CENTER);
							}
						}
					}
				}
				{
					btnGenerate = new JButton("Generate");
					pnlNorth.add(btnGenerate, BorderLayout.EAST);
					btnGenerate.setPreferredSize(new Dimension(200, 0));
					btnGenerate.setActionCommand("Generate");
					btnGenerate.addActionListener(this);
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					dteMeterInstall = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCenter.add(dteMeterInstall);
					dteMeterInstall.setDate(null);
					((JTextFieldDateEditor)dteMeterInstall.getDateEditor()).setEditable(false);
					dteMeterInstall.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					((JTextFieldDateEditor)dteMeterInstall.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
						public void insertUpdate(DocumentEvent evt) {
							SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

							if (modelMeterInstall.getValueAt(tblMeterInstallation.getSelectedRow(),0).equals(true)) {
								modelMeterInstall.setValueAt(""+sdf.format(dteMeterInstall.getDate()),tblMeterInstallation.getSelectedRow(),3);
							}
						}
						public void changedUpdate(DocumentEvent e) {}
						public void removeUpdate(DocumentEvent e) {}
					});
				}
				{
					scrollMeterInstallation = new JScrollPane();
					pnlCenter.add(scrollMeterInstallation, BorderLayout.CENTER);
					{

						modelMeterInstall = new modelMeterInstallation();

						tblMeterInstallation = new _JTableMain(modelMeterInstall);
						scrollMeterInstallation.setViewportView(tblMeterInstallation);
						scrollMeterInstallation.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						tblMeterInstallation.setSortable(false);
						tblMeterInstallation.hideColumns("Proj. ID", "PBL ID");
						tblMeterInstallation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblMeterInstallation.addMouseListener(this);
						tblMeterInstallation.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

							@Override
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()){

								}
							}
						});
					}
					{
						rowHeaderMeterInstallation = tblMeterInstallation.getRowHeader();
						rowHeaderMeterInstallation.setModel(new DefaultListModel());
						scrollMeterInstallation.setRowHeaderView(rowHeaderMeterInstallation);
						scrollMeterInstallation.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}


			}
			{
				JPanel pnlSouth = new JPanel(new GridLayout(1, 5, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 25));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnTag = new JButton("Tag");
					pnlSouth.add(btnTag);
					btnTag.setActionCommand("Tag");
					btnTag.addActionListener(this);
				}
			}
		}
	}//XXX END OF INIT GUI

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(tblMeterInstallation)) {
			int row = tblMeterInstallation.convertRowIndexToModel(tblMeterInstallation.getSelectedRow());
			Boolean isSelected = (Boolean) modelMeterInstall.getValueAt(row, 0);

			for(int x = 0; x < modelMeterInstall.getRowCount(); x++){
				Boolean isSelected2 = (Boolean) modelMeterInstall.getValueAt(x, 0);
				if (isSelected2) {
					if (modelMeterInstall.getValueAt(x, 3) == null) {
						modelMeterInstall.setValueAt(sdf.format(Calendar.getInstance().getTime()), x, 3);
					}
				}else {
					modelMeterInstall.setValueAt(null, x, 3);
				}
			}

			if(isSelected) {
				if (e.getClickCount()==2) {
					if (tblMeterInstallation.getSelectedColumn()== 3) {
						if (e.getClickCount() ==2) {
							dteMeterInstall.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
							dteMeterInstall.getCalendarButton().doClick();
						}
					}
				}
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e){//XXX Action
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Generate")) {
			GenerateWaterMeterIntallation(lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue(), modelMeterInstall, rowHeaderMeterInstallation);
		}

		if(actionCommand.equals("Tag")) {
			if(checkIfTagged()) {
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					saveMeterInstallation();
					modelMeterInstall.clear();
					scrollMeterInstallation.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					lookupCompany.setValue(null);
					txtCompany.setText("");
					lookupProject.setValue(null);
					txtProject.setText("");
					lookupPhase.setValue(null);
					txtPhase.setText("");
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Units successfully tagged", "Save", JOptionPane.INFORMATION_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select units to tag", "Tag", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private Boolean checkIfTagged() {
		Boolean tagged = false;
		for(int x=0; x<modelMeterInstall.getRowCount(); x++) {
			Boolean isSelected = (Boolean) modelMeterInstall.getValueAt(x, 0);
			if(isSelected) {
				tagged = true;
				break;
			}
		}

		return tagged;
	}

	private void GenerateWaterMeterIntallation(String co_id, String proj_id, String phase, DefaultTableModel model, JList rowHeader) {
		if(model != null && rowHeader != null){
			FncTables.clearTable(model);
			DefaultListModel listModel = new DefaultListModel();
			rowHeader.setModel(listModel);

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_water_meter_not_installed(NULLIF('"+co_id+"', 'null'),  NULLIF('"+proj_id+"','null'), NULLIF('"+phase+"','null'));";
			db.select(SQL);
			FncSystem.out("Display water meter installtion", SQL);

			if(db.isNotNull()){
				for(int x= 0; x<db.getRowCount(); x++){
					model.addRow(db.getResult()[x]);
					listModel.addElement(model.getRowCount());
				}
				scrollMeterInstallation.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblMeterInstallation.getRowCount())));
				tblMeterInstallation.packAll();
			}
		}
	}

	private void saveMeterInstallation() {
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listMeterInstallDate = new ArrayList<String>();
		
		for(int x=0; x<modelMeterInstall.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelMeterInstall.getValueAt(x, 0);

			if(isSelected){
				String meter_install_date = (String) modelMeterInstall.getValueAt(x, 3);
				String proj_id = (String) modelMeterInstall.getValueAt(x, 4);
				String pbl_id = (String) modelMeterInstall.getValueAt(x, 5);

				listProjID.add(String.format("'%S'", proj_id));
				listPBL.add(String.format("'%s'", pbl_id));
				listMeterInstallDate.add(String.format("'%s'", meter_install_date));
			}
		}
		
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
		String meter_install_date = listMeterInstallDate.toString().replaceAll("\\[|\\]", "");

		pgSelect db = new pgSelect();

		String SQL = "SELECT sp_tag_meter_installation(ARRAY["+proj_id+"]::VARCHAR[], ARRAY["+pbl_id+"]::VARCHAR[],ARRAY["+meter_install_date+"]::TIMESTAMP WITHOUT TIME ZONE[], '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);

		FncSystem.out("Display SQL for tagging Meter Installation", SQL);	
	}

}
