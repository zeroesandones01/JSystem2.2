package Admin;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import tablemodel.modelAddEditEntityType_EntityTypes;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components._JInternalFrame;
import components._JTableMain;

public class AddEditEntityType extends _JInternalFrame implements ActionListener, _GUI, AncestorListener {

	private static final long serialVersionUID = -4588368374417511645L;

	static String title = "Add/Edit Entity Type";
	Dimension frameSize = new Dimension(600, 600);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;
	private JPanel pnlNorthCenter;
	private JPanel pnlLabels;
	private JPanel pnlID;
	private JPanel pnlTINNo;

	private JLabel lblID;
	private _JLookup lookupID;

	private JLabel lblName;
	private JTextField txtName;

	private JLabel lblTINNo;
	private JTextField txtTINNo;
	private JCheckBox chkVATRegistered;

	private JScrollPane scrollTypes;
	private _JTableMain tblTypes;
	private modelAddEditEntityType_EntityTypes modelTypes;
	private JList rowHeaderTypes;
	
	private JScrollPane scrollCSTypes;
	private _JTableMain tblCSTypes;
	private modelAddEditEntityType_EntityTypes modelCSTypes;
	private JList rowHeaderCSTypes;

	private JPanel pnlSouth;

	private JPanel pnlNavigation;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnCancel;

	private Boolean blnBroker; 
	
	private _JLookup txtCoID;
	private JTextField txtCo;

	public AddEditEntityType() {
		super(title, true, true, true, true);
		initGUI();
	}

	public AddEditEntityType(String entity_id) {
		super(title, true, true, true, true);
		initGUI();

		displayEntity(entity_id);
	}

	public AddEditEntityType(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(3, 3));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Entity Details"));
				pnlNorth.setPreferredSize(new Dimension(0, 120));
				{
					{
						JPanel panFilter = new JPanel(new GridLayout(3, 1, 5, 5)); 
						pnlNorth.add(panFilter, BorderLayout.CENTER); 
						{
							{
								JPanel panLine1 = new JPanel(new BorderLayout(5, 5)); 
								panFilter.add(panLine1); 
								{
									{
										JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5)); 
										panLine1.add(panLabel, BorderLayout.LINE_START);
										panLabel.setPreferredSize(new Dimension(200, 0));
										{
											{
												JLabel label = new JLabel("Name"); 
												panLabel.add(label);
												label.setHorizontalAlignment(JLabel.LEADING);
											}
											{
												lookupID = new _JLookup(null, "Name");
												panLabel.add(lookupID, BorderLayout.WEST);
												lookupID.setReturnColumn(0);
												lookupID.setFilterName(true);
												lookupID.setLookupSQL(_AddEditEntityType.getEntitySQL());
												lookupID.setPreferredSize(new Dimension(100, 27));
												lookupID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet();
														
														if (data != null) {
															txtName.setText((String) data[1]);
															txtTINNo.setText((String) data[2]);
															chkVATRegistered.setSelected((Boolean) data[3]);

															displayEntityTypes((String) data[0]);
															displayCSEntityTypes(); 
															txtCoID.setEnabled(true);
														}
													}
												});
											}
										}
									}
									{
										txtName = new JTextField("");
										panLine1.add(txtName, BorderLayout.CENTER);
										txtName.setHorizontalAlignment(JTextField.CENTER);
										txtName.setEditable(false);
									}
								}
							}
							{
								JPanel panLine2 = new JPanel(new BorderLayout(5, 5)); 
								panFilter.add(panLine2); 
								{
									{
										JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5)); 
										panLine2.add(panLabel, BorderLayout.LINE_START);
										panLabel.setPreferredSize(new Dimension(200, 0));
										{
											{
												JLabel label = new JLabel("TIN"); 
												panLabel.add(label);
												label.setHorizontalAlignment(JLabel.LEADING);
											}
											{
												txtTINNo = new JTextField("");
												panLabel.add(txtTINNo, BorderLayout.CENTER);
												txtTINNo.setHorizontalAlignment(JTextField.CENTER);
												txtTINNo.setEditable(false);
											}
										}
									}
									{
										chkVATRegistered = new JCheckBox("VAT Registered");
										panLine2.add(chkVATRegistered, BorderLayout.CENTER);
										chkVATRegistered.setHorizontalAlignment(JCheckBox.LEADING);
										chkVATRegistered.setEnabled(false);
									}
								}
							}
							{
								JPanel panLine3 = new JPanel(new BorderLayout(5, 5)); 
								panFilter.add(panLine3); 
								{
									{
										JPanel panLabel = new JPanel(new GridLayout(1, 2, 5, 5)); 
										panLine3.add(panLabel, BorderLayout.LINE_START);
										panLabel.setPreferredSize(new Dimension(200, 0));
										{
											{
												JLabel label = new JLabel("Company"); 
												panLabel.add(label);
												label.setHorizontalAlignment(JLabel.LEADING);
											}
											{
												txtCoID = new _JLookup(null, "Company");
												panLabel.add(txtCoID, BorderLayout.WEST);
												txtCoID.setReturnColumn(0);
												txtCoID.setFilterName(true);
												txtCoID.setLookupSQL(lookupMethods.getCompany());
												txtCoID.addLookupListener(new LookupListener() {
													public void lookupPerformed(LookupEvent event) {
														Object[] data = ((_JLookup) event.getSource()).getDataSet();
														
														if (data != null) {
															txtCoID.setValue(data[0].toString());
															txtCo.setText(data[1].toString());
															
															displayCSEntityTypes(); 
														}
													}
												});
												txtCoID.setValue("");
												txtCoID.addKeyListener(new KeyListener() {

													public void keyTyped(KeyEvent e) {
														
													}

													public void keyReleased(KeyEvent e) {
														
													}

													public void keyPressed(KeyEvent e) {
														if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
															txtCoID.setValue("");
															txtCo.setText("");
														}
													}
												});
												txtCoID.setEnabled(false);
											}
										}
									}
									{
										txtCo = new JTextField("");
										panLine3.add(txtCo, BorderLayout.CENTER);
										txtCo.setHorizontalAlignment(JTextField.CENTER);
										txtCo.setEditable(false);
									}
								}
							}
						}
					}
				}
			}
			{
				JTabbedPane tab = new JTabbedPane(); 
				pnlMain.add(tab, BorderLayout.CENTER);
				{
					tab.add("Entity Types", panGrid1());
					tab.add("Company Specific Types", panGrid2()); 
				}
			}
			
			{
				pnlSouth = new JPanel(new BorderLayout());
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(388, 30));
				{
					pnlNavigation = new JPanel(new GridLayout(1, 3, 5, 5));
					pnlSouth.add(pnlNavigation, BorderLayout.EAST);
					pnlNavigation.setPreferredSize(new Dimension(310, 30));
					{
						btnEdit = new JButton("Edit");
						pnlNavigation.add(btnEdit);
						btnEdit.addActionListener(this);
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
		}

		btnState(false, false, false);
	} 

	private void btnState(Boolean sEdit, Boolean sSave, Boolean sCancel) {
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void displayEntity(String entity_id) {
		Object[] data = _AddEditEntityType.getEntityDetails(entity_id);

		lookupID.setValue(entity_id);
		txtName.setText((String) data[0]);
		txtTINNo.setText((String) data[1]);
		chkVATRegistered.setSelected((Boolean) data[2]);

		displayEntityTypes(entity_id);
	}

	public void ancestorAdded(AncestorEvent arg0) {

	}

	public void ancestorMoved(AncestorEvent arg0) {

	}

	public void ancestorRemoved(AncestorEvent arg0) {

	}

	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if(action.equals("Edit")){
			lookupID.setEditable(false);
			modelTypes.setEditable(true);
			modelCSTypes.setEditable(true);
			btnState(false, true, true);
		}

		if(action.equals("Save")){
			if (JOptionPane.showConfirmDialog((JFrame) this.getTopLevelAncestor(), "Are all entries correct?", action, 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
				
				_AddEditEntityType.saveEntityTypes(modelTypes, lookupID.getValue());
				_AddEditEntityType.saveCSEntityTypes(modelCSTypes, lookupID.getValue(), txtCoID.getValue()); 
				JOptionPane.showMessageDialog((JFrame) this.getTopLevelAncestor(), "Entity tpyes has been updated.", action, JOptionPane.INFORMATION_MESSAGE);
				btnCancel.doClick();
			}
		}

		if(action.equals("Cancel")){
			lookupID.setEditable(true);
			modelTypes.setEditable(false);

			displayEntityTypes(lookupID.getValue());
			displayCSEntityTypes(); 
		}
	}
	
	private JPanel panGrid1() {
		JPanel panGrid = new JPanel(new BorderLayout(5, 5)); 
		panGrid.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			scrollTypes = new JScrollPane();
			panGrid.add(scrollTypes, BorderLayout.CENTER);
			scrollTypes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollTypes.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					tblTypes.clearSelection();
				}
			});
			{
				modelTypes = new modelAddEditEntityType_EntityTypes();
				modelTypes.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						if(e.getType() == 1){
							((DefaultListModel)rowHeaderTypes.getModel()).addElement(modelTypes.getRowCount());
							if(modelTypes.getRowCount() == 0){
								rowHeaderTypes.setModel(new DefaultListModel());
							}
						}
					}
				});

				tblTypes = new _JTableMain(modelTypes);
				scrollTypes.setViewportView(tblTypes);
			}
			{
				rowHeaderTypes = tblTypes.getRowHeader();
				rowHeaderTypes.setModel(new DefaultListModel());
				scrollTypes.setRowHeaderView(rowHeaderTypes);
				scrollTypes.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				scrollTypes.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTypes.getRowCount())));
			}
		}
		
		return panGrid; 
	}

	private JPanel panGrid2() {
		JPanel panGrid = new JPanel(new BorderLayout(5, 5)); 
		panGrid.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			scrollCSTypes = new JScrollPane();
			panGrid.add(scrollCSTypes, BorderLayout.CENTER);
			scrollCSTypes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollCSTypes.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					tblCSTypes.clearSelection();
				}
			});
			{
				modelCSTypes = new modelAddEditEntityType_EntityTypes();
				modelCSTypes.addTableModelListener(new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						if(e.getType() == 1){
							((DefaultListModel)rowHeaderCSTypes.getModel()).addElement(modelTypes.getRowCount());
							if(modelCSTypes.getRowCount() == 0){
								rowHeaderCSTypes.setModel(new DefaultListModel());
							}
						}
					}
				});

				tblCSTypes = new _JTableMain(modelCSTypes);
				scrollCSTypes.setViewportView(tblCSTypes);
			}
			{
				rowHeaderCSTypes = tblCSTypes.getRowHeader();
				rowHeaderCSTypes.setModel(new DefaultListModel());
				scrollCSTypes.setRowHeaderView(rowHeaderCSTypes);
				scrollCSTypes.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
				scrollCSTypes.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCSTypes.getRowCount())));
			}
		}
		
		return panGrid; 
	}
	
	private void displayEntityTypes(String entity_id) {
		rowHeaderTypes.setModel(new DefaultListModel());
		_AddEditEntityType.displayEntityTypes(modelTypes, entity_id);
		scrollTypes.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblTypes.getRowCount())));
		tblTypes.packAll();

		if(tblTypes.getRowCount() > 0){
			btnState(true, false, false);
		}
	}
	
	private void displayCSEntityTypes() {
		if (txtCoID.getValue().equals("")) {
			modelCSTypes.clear();
		} else {
			rowHeaderCSTypes.setModel(new DefaultListModel());
			_AddEditEntityType.displayCSEntityTypes(modelCSTypes, lookupID.getValue(), txtCoID.getValue());
			scrollCSTypes.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCSTypes.getRowCount())));
			tblCSTypes.packAll();

			if(tblCSTypes.getRowCount() > 0){
				btnState(true, false, false);
			}	
		}
	}
}
