package System;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelJsystemModule;

public class JSystemModule extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 6761057678918873462L;

	private static String title = "JSystem Modules";
	Dimension size =  new Dimension(700,600);
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorthLabel;
	private JLabel lblIncharge;
	private JLabel lblAttachment;

	private JPanel pnlNorthComponents;
	private _JLookup lookupIncharge;
	private _JXTextField txtInCharge;
	private JTextField txtFile;
	private JButton btnBrowse;

	private JPanel pnlCenter;
	private JScrollPane scrollModules;
	private _JTableMain tblModules;
	private JList rowHeaderModules;
	private modelJsystemModule modelModules;

	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;
	private File selectedFile;

	private FileOutputStream fos;
	private byte[] bytes;
	private Object file_name;
	private String filename;

	public JSystemModule() {
		super(title,true,true,false,false);
		initGUI();
	}

	public JSystemModule(String title) {
		super(title);
		initGUI();
	}

	public JSystemModule(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(size);
		setPreferredSize(size);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 50));
				{
					pnlNorthLabel = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
					pnlNorthLabel.setPreferredSize(new Dimension(100, 0));
					{
						lblIncharge = new JLabel("In-Charge");
						pnlNorthLabel.add(lblIncharge);
					}
					{
						lblAttachment = new JLabel("Attachment");
						pnlNorthLabel.add(lblAttachment);
					}
				}
				{
					pnlNorthComponents = new JPanel(new GridLayout(2, 1, 5, 5));
					pnlNorth.add(pnlNorthComponents, BorderLayout.CENTER);
					{
						JPanel pnlIncharge = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponents.add(pnlIncharge);
						{
							lookupIncharge = new _JLookup(null, "In-charge", 0);
							pnlIncharge.add(lookupIncharge, BorderLayout.WEST);
							lookupIncharge.setPreferredSize(new Dimension(100, 0));
							lookupIncharge.setEditable(false);
							lookupIncharge.setLookupSQL(sqlIncharge());
							lookupIncharge.addLookupListener(new LookupListener() {

								@Override
								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();

									if(data != null) {
										String name = (String) data [1];
										txtInCharge.setText(name);

									}
								}
							});
						}
						{
							txtInCharge = new _JXTextField();
							pnlIncharge.add(txtInCharge, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlAttachment = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponents.add(pnlAttachment);
						{
							txtFile = new _JXTextField();
							pnlAttachment.add(txtFile, BorderLayout.CENTER);
						}
						{
							btnBrowse = new JButton("Browse");
							pnlAttachment.add(btnBrowse, BorderLayout.EAST);
							btnBrowse.setPreferredSize(new Dimension(100, 0));
							btnBrowse.setActionCommand("Browse");
							btnBrowse.addActionListener(this);
							btnBrowse.setEnabled(false);
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(JTBorderFactory.createTitleBorder("List of Modules"));
				{
					scrollModules = new JScrollPane();
					pnlCenter.add(scrollModules);
					scrollModules.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					{
						modelModules = new modelJsystemModule();
						tblModules = new _JTableMain(modelModules);
						scrollModules.setViewportView(tblModules);
						//tblModules.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tblModules.hideColumns("Rec ID", "Status");
						modelModules.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								if(e.getType() == 1) {
									((DefaultListModel)rowHeaderModules.getModel()).addElement(modelModules.getRowCount());
								}

								if(modelModules.getRowCount() == 0){
									rowHeaderModules.setModel(new DefaultListModel());
								}
							}
						});
						tblModules.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

							@Override
							public void valueChanged(ListSelectionEvent e) {
								if(!e.getValueIsAdjusting()) {
									if(tblModules.getSelectedRows().length == 1){
										//int selected_row = tblModules.getSelectedRow();
										if(btnSave.isEnabled() == false) {
											btnState(true, true, false, false);
											btnBrowse.setEnabled(true);
										}
									}
								}
							}
						});
					}
					{
						rowHeaderModules = tblModules.getRowHeader();
						rowHeaderModules.setModel(new DefaultListModel());
						scrollModules.setRowHeaderView(rowHeaderModules);
						scrollModules.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlSouth = new JPanel(new GridLayout(1, 4, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 50));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouth.add(btnEdit);
					btnEdit.setActionCommand("Edit");
					btnEdit.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
				}
			}
		}
		btnState(false, true, false, false);
		displayModules();
	}

	private String sqlIncharge() {
		String SQL = "select a.emp_code as \"Emp Code\", get_employee_name(a.emp_code) as \"Name\"\n" + 
				"from em_employee a\n" + 
				"where a.emp_status NOT IN ('I', 'E')\n" + 
				"and a.dept_code = '98';";
		return SQL;
	}

	private void displayModules() {
		modelModules.clear();
		
		pgSelect db = new pgSelect();
		String SQL = "select rec_id, false ,module_name, get_employee_name(in_charge) as in_charge, user_manual is not null, status_id\n" + 
					 "from mf_jsystem_modules \n" + 
					 "where status_id = 'A'\n" + 
					 "order by get_employee_name(in_charge), rec_id";
		db.select(SQL);
		FncSystem.out("Display modules", SQL);

		if(db.isNotNull()) {
			for(int x= 0; x<db.getRowCount(); x++) {
				modelModules.addRow(db.getResult()[x]);
			}
			scrollModules.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblModules.getRowCount())));
			tblModules.packAll();
		}
	}

	private void saveDocument(byte[] document, Integer rec_id){
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			connection.setAutoCommit(true);

			PreparedStatement ps = connection.prepareStatement("UPDATE mf_jsystem_modules SET user_manual = ? WHERE rec_id = "+rec_id+" and status_id = 'A'");
			ps.setBytes(1, document);
			ps.executeUpdate();
			ps.close();
			System.out.println("Dumaan dito sa saving ng Document");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private void openFile(Integer rec_id) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("PDF document", "pdf"));				
		if (fileChooser.showOpenDialog(pnlMain) == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			txtFile.setText(selectedFile.getAbsolutePath().replace("'", "''"));
			System.out.println(selectedFile.getName().replace("'", "''"));
			System.out.println(selectedFile.getAbsolutePath().replace("'", "''"));

			filename = selectedFile.getName().replace("'", "''");

			FileInputStream fileIS;
			bytes = null;

			try {
				fileIS = new FileInputStream(new File(selectedFile.getAbsolutePath()));
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				for (int readNum; (readNum = fileIS.read(buf)) != -1;) {
					bos.write(buf, 0, readNum);      
				}
				bytes = bos.toByteArray();

			} catch (FileNotFoundException a) {
				a.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			btnState(false, false, true, true);
			if(txtFile.getText().equals("") == false) {
				saveDocument(bytes, rec_id);
			}
		}else {
			btnBrowse.setEnabled(false);
		}
	}

	private void updateIncharge(String in_charge, Integer rec_id) {
		pgUpdate db = new pgUpdate();
		String SQL = "UPDATE mf_jsystem_modules set in_charge = '"+in_charge+"' where rec_id = "+rec_id+" ";
		db.executeUpdate(SQL, true);
		db.commit();
	}

	private void preview(String module_name, Integer rec_id) {
		Connection connection = null;

		if (Desktop.isDesktopSupported()) {
			try {
				connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("select user_manual from mf_jsystem_modules where rec_id = "+rec_id+" and status_id = 'A'");

				File pdfTemp = File.createTempFile(module_name.toString(), ".pdf");
				pdfTemp.deleteOnExit();

				fos = new FileOutputStream(pdfTemp);
				rs.next();
				byte[] fileBytes = rs.getBytes(1);
				fos.write(fileBytes);
				rs.close();
				fos.close();

				Desktop.getDesktop().open(pdfTemp);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {

			}
		}
	}

	private void btnState(Boolean preview, Boolean edit, Boolean save, Boolean cancel) {
		btnPreview.setEnabled(preview);
		btnEdit.setEnabled(edit);
		btnSave.setEnabled(save);
		btnCancel.setEnabled(cancel);
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Preview")) {
			if(tblModules.getSelectedRows().length == 1) {
				int selected_row = tblModules.convertRowIndexToModel(tblModules.getSelectedRow());
				Integer rec_id = (Integer) modelModules.getValueAt(selected_row, 0);
				String module_name = (String) modelModules.getValueAt(selected_row, 2);
				Boolean with_manual = (Boolean) modelModules.getValueAt(selected_row, 4);
				
				if(with_manual) {
					preview(module_name, rec_id);
				}else {
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "This module has no uploaded manual yet", "Preview", JOptionPane.WARNING_MESSAGE);
				}
			}
		}

		if(actionCommand.equals("Edit")) {
			lookupIncharge.setEditable(true);
			btnBrowse.setEnabled(false);
			//tblModules.setEnabled(false);
			rowHeaderModules.setEnabled(false);
			btnState(false, false, true, true);
		}

		if(actionCommand.equals("Browse")) {
			if(tblModules.getSelectedRows().length == 1) {
				int selected_row = tblModules.convertRowIndexToModel(tblModules.getSelectedRow());
				Integer rec_id = (Integer) modelModules.getValueAt(selected_row, 0);
				openFile(rec_id);
			}else {
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select one row to edit", actionCommand, JOptionPane.WARNING_MESSAGE);
			}
		}

		if(actionCommand.equals("Save")) {
			if(lookupIncharge.getValue() != null) {
//				int selected_row = tblModules.convertRowIndexToModel(tblModules.getSelectedRow());
//				Integer rec_id = (Integer) modelModules.getValueAt(selected_row, 0);
				
				for(int x= 0; x<modelModules.getRowCount(); x++) {
					Boolean isSelected = (Boolean) modelModules.getValueAt(x, 1);
					Integer rec_id = (Integer) modelModules.getValueAt(x, 0);
					if(isSelected) {
						updateIncharge(lookupIncharge.getValue(), rec_id);
					}
				}
				
				lookupIncharge.setValue(null);
				txtInCharge.setText("");
				lookupIncharge.setEditable(false);
				btnBrowse.setEnabled(false);
				tblModules.setEnabled(true);
				rowHeaderModules.setEnabled(true);
				tblModules.clearSelection();
				displayModules();
				btnState(false, true, false, false);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Succesfully saved", "Save", JOptionPane.INFORMATION_MESSAGE);

			}else{
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select in charge", actionCommand, JOptionPane.WARNING_MESSAGE);
			}
		}

		if(actionCommand.equals("Cancel")) {
			lookupIncharge.setValue(null);
			lookupIncharge.setEditable(false);
			txtInCharge.setText("");
			txtFile.setText("");
			btnBrowse.setEnabled(false);
			tblModules.setEnabled(true);
			rowHeaderModules.setEnabled(true);

			btnState(false, false, false, false);
		}

	}
}
