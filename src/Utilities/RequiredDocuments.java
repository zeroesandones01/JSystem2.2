package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXTextArea;

import Database.pgSelect;
import tablemodel.modelDocumentsMaintenance_AddendumDocs;
import tablemodel.modelDocumentsMaintenance_DocsMonitoring;
import tablemodel.modelROP_Allocation;
import tablemodel.modelDocumentsMaintenance_BuyerTypes;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup._JLookupTable;
import components._ButtonGroup;
import components._JCheckBox;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;

/**
 * 
 * @author Alvin Gonzales (2016-06-30)
 *
 */
public class RequiredDocuments extends _JInternalFrame implements ActionListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String title = "Documents Maintenance";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Dimension frameSize = new Dimension(800, 600);

	private JPanel pnlMain;
	private JTabbedPane tabCenter;
	//private JPanel pnlDocsMonitoring;
	//private JPanel pnlCenter;
	//private JPanel pnlSouth;
	//private JPanel pnlTextfields;
	private JPanel pnlDocumentDetails;

	private JScrollPane scrollAllDocuments;
	private _JTableMain tblAllDocuments;
	private modelDocumentsMaintenance_DocsMonitoring modelAllDocuments;
	private JList rowHeaderAllDocuments;

	private JScrollPane scrollBuyerTypes;
	private _JTableMain tblBuyerTypes;
	private JPanel pnlLabel;
	private modelDocumentsMaintenance_BuyerTypes modelBuyerTypes;
	private JList rowHeaderBuyerTypes;

	private _JXTextField txtID;
	private _JXTextField txtName;
	private _JXTextField txtAlias;
	private _JXTextField txtClientStatus;
	private _JXTextField txtBusinessClass;
	private _JXTextField txtReportName;
	private _JXTextField txtBusinessNature;
	private _JXTextField txtCivilStatus;
	private _JXTextField txtDocsSub;

	//private _JCheckBox chkMarried;
	private _JCheckBox chkMandatory;

	private JPanel pnlNavigation;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnCancel;
	private JButton btnSave;

	private _ButtonGroup grpButton = new _ButtonGroup();

	private JScrollPane scrollAddendumDocuments;
	private _JTableMain tblAddendumDocuments;
	private modelDocumentsMaintenance_AddendumDocs modelAddendumDocuments;
	private JList rowHeaderAddendumDocuments;

	private JXTextArea txtRemarks;
	
	private _ButtonGroup grpAddendumButton = new _ButtonGroup();
	private JButton btnAddendumNew;
	private JButton btnAddendumEdit;
	private JButton btnAddendumDelete;
	private JButton btnAddendumCancel;
	private JButton btnAddendumSave;

	public RequiredDocuments() {
		super(title, true, true, true, true);
		initGUI();
	}

	public RequiredDocuments(String title) {
		super(title);
		initGUI();
	}

	public RequiredDocuments(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		/*this.setMinimumSize(frameSize);*/
		this.setLayout(new BorderLayout());
		//this.addAncestorListener(this);

		pnlMain = new JPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(EMPTY_BORDER);
		{
			tabCenter = new JTabbedPane();
			pnlMain.add(tabCenter, BorderLayout.CENTER);
			{
				JPanel pnlDocsMonitoring = new JPanel(new BorderLayout(5, 5));
				tabCenter.add("Docs Monitoring", pnlDocsMonitoring);
				pnlDocsMonitoring.setBorder(EMPTY_BORDER);
				{
					JPanel pnlCenter = new JPanel(new BorderLayout());
					pnlDocsMonitoring.add(pnlCenter, BorderLayout.CENTER);
					//pnlNorth.setPreferredSize(new Dimension(788, 250));
					{
						JLabel lblTitle = new JLabel("All Documents");
						pnlCenter.add(lblTitle, BorderLayout.NORTH);
						lblTitle.setFont(new Font(FncLookAndFeel.font_name, Font.BOLD, 12));
					}
					{
						scrollAllDocuments = new JScrollPane();
						pnlCenter.add(scrollAllDocuments, BorderLayout.CENTER);
						scrollAllDocuments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollAllDocuments.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								if(tblAllDocuments.isEnabled()){
									tblAllDocuments.clearSelection();
								}
							}
						});
						{
							modelAllDocuments = new modelDocumentsMaintenance_DocsMonitoring();
							modelAllDocuments.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									//Addition of rows
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderAllDocuments.getModel()).addElement(modelAllDocuments.getRowCount());

										if(modelAllDocuments.getRowCount() == 0){
											rowHeaderAllDocuments.setModel(new DefaultListModel());
										}
									}
								}
							});

							tblAllDocuments = new _JTableMain(modelAllDocuments);
							scrollAllDocuments.setViewportView(tblAllDocuments);
							tblAllDocuments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tblAllDocuments.setSortable(false);
							tblAllDocuments.hideColumns("ID", "Alias", "Client Status", "Business Class", "Report", "Business Nature", "Civil Status", "Doc Sub");

							tblAllDocuments.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent arg0) {
									if(!arg0.getValueIsAdjusting()){//XXX
										try {
											if(grpButton.getActionCommand() != null && grpButton.getActionCommand().equals("New")){
												return;
											}

											Integer row = tblAllDocuments.convertRowIndexToModel(tblAllDocuments.getSelectedRow());

											//Integer doc_id = (Integer) modelAllDocuments.getValueAt(row, 0);
											
											String doc_id = (String) modelAllDocuments.getValueAt(row, 0);
											String doc_desc = (String) modelAllDocuments.getValueAt(row, 1);
											String doc_alias = (String) modelAllDocuments.getValueAt(row, 2);
											//Boolean for_married = (Boolean) modelAllDocuments.getValueAt(row, 7);
											Boolean for_ocw = (Boolean) modelAllDocuments.getValueAt(row, 7);
											String client_status = (String) modelAllDocuments.getValueAt(row, 8);
											String business_class = (String) modelAllDocuments.getValueAt(row, 9);
											String report = (String) modelAllDocuments.getValueAt(row, 10);
											String business_nature = (String) modelAllDocuments.getValueAt(row, 11);
											String civil_status = (String) modelAllDocuments.getValueAt(row, 12);
											String doc_sub_id = (String) modelAllDocuments.getValueAt(row, 13);
											
											displayDocumentDetails(doc_id, doc_desc, doc_alias, for_ocw, client_status, business_class, report, business_nature, civil_status, doc_sub_id);
											btnState(true, true, true, false, false);
										} catch (IndexOutOfBoundsException e) { }
									}
								}
							});

							tblAllDocuments.addMouseListener(new MouseListener() {
								public void mouseReleased(MouseEvent arg0) {
									if(arg0.isPopupTrigger()){
										initializeMenu().show((_JTableMain)arg0.getSource(), arg0.getX(), arg0.getY());
									}
								}
								public void mousePressed(MouseEvent arg0) {
									if(arg0.isPopupTrigger()){
										if(arg0.getSource() instanceof _JTableMain){
											initializeMenu().show((_JTableMain)arg0.getSource(), arg0.getX(), arg0.getY());
										}
									}
								}
								public void mouseExited(MouseEvent arg0) { }
								public void mouseEntered(MouseEvent arg0) { }
								public void mouseClicked(MouseEvent arg0) { }
							});
						}
						{
							rowHeaderAllDocuments = tblAllDocuments.getRowHeader();
							rowHeaderAllDocuments.setModel(new DefaultListModel());
							scrollAllDocuments.setRowHeaderView(rowHeaderAllDocuments);
							scrollAllDocuments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							scrollAllDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAllDocuments.getRowCount())));
						}
					}
				}
				{
					JPanel pnlSouth = new JPanel(new BorderLayout(0, 3));
					pnlDocsMonitoring.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setPreferredSize(new Dimension(788, 300));
					{
						pnlDocumentDetails = new JPanel(new BorderLayout(5, 5));
						pnlSouth.add(pnlDocumentDetails, BorderLayout.CENTER);
						pnlDocumentDetails.setBorder(components.JTBorderFactory.createTitleBorder("Document Details"));

						GridLayout grid = new GridLayout(9, 1, 3, 3);
						{
							pnlLabel = new JPanel(grid);
							pnlDocumentDetails.add(pnlLabel, BorderLayout.WEST);
							pnlLabel.setPreferredSize(new Dimension(120, 234));
							{
								JLabel lblID = new JLabel("Doc. ID");
								pnlLabel.add(lblID);
							}
							{
								JLabel lblName = new JLabel("Doc. Name");
								pnlLabel.add(lblName);
							}
							{
								JLabel lblReportName = new JLabel("Report Name");
								pnlLabel.add(lblReportName);
							}
							{
								JLabel lblClientStatus = new JLabel("Client Status");
								pnlLabel.add(lblClientStatus);
							}
							{
								JLabel lblClientStatus = new JLabel("Business Class");
								pnlLabel.add(lblClientStatus);
							}
							{
								JLabel lblBusinessNature = new JLabel("Business Nature");
								pnlLabel.add(lblBusinessNature);
							}
							{
								JLabel lblCivilStatus = new JLabel("Civil Status");
								pnlLabel.add(lblCivilStatus);
							}
							{
								JLabel lblDocSub = new JLabel("Doc Sub");
								pnlLabel.add(lblDocSub);
							}
						}
						{
							JPanel pnlTextfields = new JPanel(grid);
							pnlDocumentDetails.add(pnlTextfields, BorderLayout.CENTER);
							{
								JPanel pnlID = new JPanel(new BorderLayout(3, 3));
								pnlTextfields.add(pnlID);
								{
									txtID = new _JXTextField("");
									pnlID.add(txtID, BorderLayout.WEST);
									txtID.setHorizontalAlignment(SwingConstants.CENTER);
									txtID.setPreferredSize(new Dimension(82, 36));
								}
								{
									JLabel lblAlias = new JLabel("Doc. Alias ", JLabel.TRAILING);
									pnlID.add(lblAlias, BorderLayout.CENTER);
								}
								{
									txtAlias = new _JXTextField("");
									pnlID.add(txtAlias, BorderLayout.EAST);
									txtAlias.setHorizontalAlignment(SwingConstants.CENTER);
									txtAlias.setPreferredSize(new Dimension(150, 36));
								}
							}
							{
								JPanel pnlName = new JPanel(new BorderLayout(3, 3));
								pnlTextfields.add(pnlName);
								{
									txtName = new _JXTextField("");
									pnlName.add(txtName, BorderLayout.CENTER);
								}
							}
							{
								JPanel pnlReportName = new JPanel(new BorderLayout(3, 3));
								pnlTextfields.add(pnlReportName);
								{
									txtReportName = new _JXTextField("");
									pnlReportName.add(txtReportName, BorderLayout.CENTER);
								}
							}
							{
								JPanel pnlClientStatus = new JPanel(new BorderLayout(3, 3));
								pnlTextfields.add(pnlClientStatus);
								{
									txtClientStatus = new _JXTextField("");
									pnlClientStatus.add(txtClientStatus, BorderLayout.CENTER);
								}
								{
									JButton btnClientStatus = new JButton(getSearchIcon());
									pnlClientStatus.add(btnClientStatus, BorderLayout.EAST);
									btnClientStatus.setActionCommand("ClientStatus");
									btnClientStatus.addActionListener(this);
								}
							}
							{
								JPanel pnlBusinessClass = new JPanel(new BorderLayout(3, 3));
								pnlTextfields.add(pnlBusinessClass);
								{
									txtBusinessClass = new _JXTextField("");
									pnlBusinessClass.add(txtBusinessClass, BorderLayout.CENTER);
								}
								{
									JButton btnBusinessClass = new JButton(getSearchIcon());
									pnlBusinessClass.add(btnBusinessClass, BorderLayout.EAST);
									btnBusinessClass.setActionCommand("BusinessClass");
									btnBusinessClass.addActionListener(this);
								}
							}
							{
								JPanel pnlBusinessNature = new JPanel(new BorderLayout(3, 3));
								pnlTextfields.add(pnlBusinessNature);
								{
									txtBusinessNature = new _JXTextField("");
									pnlBusinessNature.add(txtBusinessNature, BorderLayout.CENTER);
								}
								{
									JButton btnBusinessNature = new JButton(getSearchIcon());
									pnlBusinessNature.add(btnBusinessNature, BorderLayout.EAST);
									btnBusinessNature.setActionCommand("BusinessNature");
									btnBusinessNature.addActionListener(this);
								}
							}
							{
								JPanel pnlCivilStatus = new JPanel(new BorderLayout(3, 3));
								pnlTextfields.add(pnlCivilStatus);
								{
									txtCivilStatus = new _JXTextField("");
									pnlCivilStatus.add(txtCivilStatus, BorderLayout.CENTER);
								}
								{
									JButton btnCivilStatus = new JButton(getSearchIcon());
									pnlCivilStatus.add(btnCivilStatus, BorderLayout.EAST);
									btnCivilStatus.setActionCommand("CivilStatus");
									btnCivilStatus.addActionListener(this);
								}
							}
							{
								JPanel pnlDocSub = new JPanel(new BorderLayout(3, 3));
								pnlTextfields.add(pnlDocSub);
								{
									txtDocsSub = new _JXTextField("");
									pnlDocSub.add(txtDocsSub, BorderLayout.CENTER);
								}
								{
									JButton btnDocSub = new JButton(getSearchIcon());
									pnlDocSub.add(btnDocSub, BorderLayout.EAST);
									btnDocSub.setActionCommand("DocsSub");
									btnDocSub.addActionListener(this);
								}
							}
							{
								JPanel pnlReportName = new JPanel(new BorderLayout(3, 3));
								pnlTextfields.add(pnlReportName);
								{
									chkMandatory = new _JCheckBox("Mandatory");
									pnlReportName.add(chkMandatory, BorderLayout.CENTER);
								}
							}
							/*{
								JPanel pnlMarried = new JPanel(new BorderLayout(3, 3));
								pnlTextfields.add(pnlMarried);
								{
									//chkMarried = new _JCheckBox("Married (for married clients)");
									//pnlMarried.add(chkMarried, BorderLayout.CENTER);
								}
							}*/
						}
						{
							scrollBuyerTypes = new JScrollPane();
							pnlDocumentDetails.add(scrollBuyerTypes, BorderLayout.EAST);
							scrollBuyerTypes.setBorder(components.JTBorderFactory.createTitleBorder("Buyer Types"));
							scrollBuyerTypes.setPreferredSize(new Dimension(300, 232));
							scrollBuyerTypes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollBuyerTypes.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent e) {
									tblBuyerTypes.clearSelection();
								}
							});
							{
								modelBuyerTypes = new modelDocumentsMaintenance_BuyerTypes();
								modelBuyerTypes.addTableModelListener(new TableModelListener() {
									public void tableChanged(TableModelEvent e) {
										//Addition of rows
										if(e.getType() == TableModelEvent.INSERT){
											((DefaultListModel)rowHeaderBuyerTypes.getModel()).addElement(modelBuyerTypes.getRowCount());

											if(modelBuyerTypes.getRowCount() == 0){
												rowHeaderBuyerTypes.setModel(new DefaultListModel());
											}
										}
									}
								});

								tblBuyerTypes = new _JTableMain(modelBuyerTypes);
								scrollBuyerTypes.setViewportView(tblBuyerTypes);
								tblBuyerTypes.hideColumns("ID", "Alias", "Group ID");
							}
							{
								rowHeaderBuyerTypes = tblBuyerTypes.getRowHeader();
								rowHeaderBuyerTypes.setModel(new DefaultListModel());
								scrollBuyerTypes.setRowHeaderView(rowHeaderBuyerTypes);
								scrollBuyerTypes.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								scrollBuyerTypes.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBuyerTypes.getRowCount())));
							}
						}
					}
					{
						pnlNavigation = new JPanel(new BorderLayout());
						pnlSouth.add(pnlNavigation, BorderLayout.SOUTH);
						pnlNavigation.setPreferredSize(new Dimension(788, 30));
						{
							JPanel pnlNav = new JPanel(new GridLayout(1, 5, 3, 0));
							pnlNavigation.add(pnlNav, BorderLayout.EAST);
							pnlNav.setPreferredSize(new Dimension(520, 30));
							{
								btnNew = new JButton("New");
								pnlNav.add(btnNew);
								btnNew.setActionCommand("New");
								btnNew.addActionListener(this);
								grpButton.add(btnNew);
							}
							{
								btnEdit = new JButton("Edit");
								pnlNav.add(btnEdit);
								btnEdit.setActionCommand("Edit");
								btnEdit.addActionListener(this);
								grpButton.add(btnEdit);
							}
							{
								btnDelete = new JButton("Delete");
								pnlNav.add(btnDelete);
								btnDelete.addActionListener(this);
							}
							{
								btnSave = new JButton("Save");
								pnlNav.add(btnSave);
								btnSave.addActionListener(this);
							}
							{
								btnCancel = new JButton("Cancel");
								pnlNav.add(btnCancel);
								btnCancel.addActionListener(this);
							}
						}
					}
				}
			}
			{
				JPanel pnlAddendumMonitoring = new JPanel(new BorderLayout(5, 5));
				tabCenter.add("Addendum Docs. Report", pnlAddendumMonitoring);
				pnlAddendumMonitoring.setBorder(EMPTY_BORDER);
				{
					JPanel pnlCenter = new JPanel(new BorderLayout());
					pnlAddendumMonitoring.add(pnlCenter, BorderLayout.CENTER);
					//pnlNorth.setPreferredSize(new Dimension(788, 250));
					{
						JLabel lblTitle = new JLabel("Addendum Documents");
						pnlCenter.add(lblTitle, BorderLayout.NORTH);
						lblTitle.setFont(new Font(FncLookAndFeel.font_name, Font.BOLD, 12));
					}
					{
						scrollAddendumDocuments = new JScrollPane();
						pnlCenter.add(scrollAddendumDocuments, BorderLayout.CENTER);
						scrollAddendumDocuments.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollAddendumDocuments.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								if(tblAddendumDocuments.isEnabled()){
									tblAddendumDocuments.clearSelection();
								}
							}
						});
						{
							modelAddendumDocuments = new modelDocumentsMaintenance_AddendumDocs();
							modelAddendumDocuments.addTableModelListener(new TableModelListener() {
								public void tableChanged(TableModelEvent e) {
									//Addition of rows
									if(e.getType() == TableModelEvent.INSERT){
										((DefaultListModel)rowHeaderAddendumDocuments.getModel()).addElement(modelAddendumDocuments.getRowCount());

										if(modelAddendumDocuments.getRowCount() == 0){
											rowHeaderAddendumDocuments.setModel(new DefaultListModel());
										}
									}
								}
							});

							tblAddendumDocuments = new _JTableMain(modelAddendumDocuments);
							scrollAddendumDocuments.setViewportView(tblAddendumDocuments);
							tblAddendumDocuments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							tblAddendumDocuments.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent arg0) {
									if(!arg0.getValueIsAdjusting()){//XXX
										/*try {
											if(grpButton.getActionCommand() != null && grpButton.getActionCommand().equals("New")){
												return;
											}

											Integer row = tblAddendumDocuments.convertRowIndexToModel(tblAddendumDocuments.getSelectedRow());

											Integer doc_id = (Integer) modelAddendumDocuments.getValueAt(row, 0);
											String doc_desc = (String) modelAddendumDocuments.getValueAt(row, 1);
											String doc_alias = (String) modelAddendumDocuments.getValueAt(row, 2);
											//Boolean for_married = (Boolean) modelAddendumDocuments.getValueAt(row, 7);
											Boolean for_ocw = (Boolean) modelAddendumDocuments.getValueAt(row, 7);
											String client_status = (String) modelAddendumDocuments.getValueAt(row, 8);
											String business_class = (String) modelAddendumDocuments.getValueAt(row, 9);
											String report = (String) modelAddendumDocuments.getValueAt(row, 10);

											displayDocumentDetails(doc_id, doc_desc, doc_alias, for_ocw, client_status, business_class, report);
											btnState(true, true, true, false, false);
										} catch (IndexOutOfBoundsException e) { }*/
									}
								}
							});

							tblAddendumDocuments.addMouseListener(new MouseListener() {
								public void mouseReleased(MouseEvent arg0) {
									if(arg0.isPopupTrigger()){
										initializeMenu().show((_JTableMain)arg0.getSource(), arg0.getX(), arg0.getY());
									}
								}
								public void mousePressed(MouseEvent arg0) {
									if(arg0.isPopupTrigger()){
										if(arg0.getSource() instanceof _JTableMain){
											initializeMenu().show((_JTableMain)arg0.getSource(), arg0.getX(), arg0.getY());
										}
									}
								}
								public void mouseExited(MouseEvent arg0) { }
								public void mouseEntered(MouseEvent arg0) { }
								public void mouseClicked(MouseEvent arg0) { }
							});
						}
						{
							rowHeaderAddendumDocuments = tblAddendumDocuments.getRowHeader();
							rowHeaderAddendumDocuments.setModel(new DefaultListModel());
							scrollAddendumDocuments.setRowHeaderView(rowHeaderAddendumDocuments);
							scrollAddendumDocuments.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
							scrollAddendumDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAddendumDocuments.getRowCount())));
						}
					}
				}
				{
					Object[] options = new Object[]{"N/A", "TR", "OR", "CO3"};

					JPanel pnlDetails = new JPanel(new BorderLayout(3, 3));
					pnlAddendumMonitoring.add(pnlDetails, BorderLayout.SOUTH);
					pnlDetails.setBorder(components.JTBorderFactory.createTitleBorder("Document Details"));
					pnlDetails.setPreferredSize(new Dimension(0, 300));
					{
						JPanel pnlDetailsNorth = new JPanel(new BorderLayout(3, 3));
						pnlDetails.add(pnlDetailsNorth, BorderLayout.NORTH);
						{
							JPanel pnlDocumentName = new JPanel(new BorderLayout(3, 3));
							pnlDetailsNorth.add(pnlDocumentName, BorderLayout.NORTH);
							{
								JLabel lblDoc = new JLabel("Document ");
								pnlDocumentName.add(lblDoc, BorderLayout.WEST);
							}
							{
								
							}
						}
						{
							JPanel pnlSubmissionDatePerBuyerType = new JPanel(new GridLayout(1, 4, 3, 3));
							pnlDetailsNorth.add(pnlSubmissionDatePerBuyerType, BorderLayout.CENTER);
							{
								JPanel pnlRegEmployed = new JPanel(new GridLayout(2, 2, 3, 3));
								pnlSubmissionDatePerBuyerType.add(pnlRegEmployed);
								pnlRegEmployed.setBorder(components.JTBorderFactory.createTitleBorder("Regularly Employed"));
								{
									JLabel lblRegular = new JLabel("Regular");
									pnlRegEmployed.add(lblRegular);
								}
								{
									JComboBox cmbRegular = new JComboBox(new DefaultComboBoxModel(options));
									pnlRegEmployed.add(cmbRegular);
								}
								{
									JLabel lblSpecial = new JLabel("Special");
									pnlRegEmployed.add(lblSpecial);
								}
								{
									JComboBox cmbSpecial = new JComboBox(new DefaultComboBoxModel(options));
									pnlRegEmployed.add(cmbSpecial);
								}
							}
							{
								JPanel pnlSelfEmployed = new JPanel(new GridLayout(2, 2, 3, 3));
								pnlSubmissionDatePerBuyerType.add(pnlSelfEmployed);
								pnlSelfEmployed.setBorder(components.JTBorderFactory.createTitleBorder("Self Employed"));
								{
									JLabel lblRegular = new JLabel("Regular");
									pnlSelfEmployed.add(lblRegular);
								}
								{
									JComboBox cmbRegular = new JComboBox(new DefaultComboBoxModel(options));
									pnlSelfEmployed.add(cmbRegular);
								}
								{
									JLabel lblSpecial = new JLabel("Special");
									pnlSelfEmployed.add(lblSpecial);
								}
								{
									JComboBox cmbSpecial = new JComboBox(new DefaultComboBoxModel(options));
									pnlSelfEmployed.add(cmbSpecial);
								}
							}
							{
								JPanel pnlOFW = new JPanel(new GridLayout(2, 2, 3, 3));
								pnlSubmissionDatePerBuyerType.add(pnlOFW);
								pnlOFW.setBorder(components.JTBorderFactory.createTitleBorder("OFW"));
								{
									JLabel lblRegular = new JLabel("Regular");
									pnlOFW.add(lblRegular);
								}
								{
									JComboBox cmbRegular = new JComboBox(new DefaultComboBoxModel(options));
									pnlOFW.add(cmbRegular);
								}
								{
									JLabel lblSpecial = new JLabel("Special");
									pnlOFW.add(lblSpecial);
								}
								{
									JComboBox cmbSpecial = new JComboBox(new DefaultComboBoxModel(options));
									pnlOFW.add(cmbSpecial);
								}
							}
							{
								JPanel pnlCopies = new JPanel(new GridLayout(2, 2, 3, 3));
								pnlSubmissionDatePerBuyerType.add(pnlCopies);
								pnlCopies.setBorder(components.JTBorderFactory.createTitleBorder("No. of copies"));
								{
									JLabel lblOriginal = new JLabel("Original");
									pnlCopies.add(lblOriginal);
								}
								{
									_JXFormattedTextField txtOriginal = new _JXFormattedTextField(_JXFormattedTextField.CENTER);
									pnlCopies.add(txtOriginal);
								}
								{
									JLabel lblPhotocopy = new JLabel("Photocopy");
									pnlCopies.add(lblPhotocopy);
								}
								{
									_JXFormattedTextField txtPhotocopy = new _JXFormattedTextField(_JXFormattedTextField.CENTER);
									pnlCopies.add(txtPhotocopy);
								}
							}
						}
					}
					{
						JPanel pnlCenter = new JPanel(new BorderLayout());
						pnlDetails.add(pnlCenter, BorderLayout.CENTER);
						pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Remarks"));
						{
							JScrollPane scrollNotes = new JScrollPane();
							pnlCenter.add(scrollNotes, BorderLayout.CENTER);
							{
								txtRemarks = new JXTextArea("Remarks");
								scrollNotes.setViewportView(txtRemarks);
								txtRemarks.setLineWrap(true);
								txtRemarks.setWrapStyleWord(true);
								txtRemarks.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
							}
						}
					}
					{
						JPanel pnlSouth = new JPanel(new BorderLayout());
						pnlDetails.add(pnlSouth, BorderLayout.SOUTH);
						pnlSouth.setPreferredSize(new Dimension(0, 30));
						{
							JPanel pnlNav = new JPanel(new GridLayout(1, 5, 3, 0));
							pnlSouth.add(pnlNav, BorderLayout.EAST);
							pnlNav.setPreferredSize(new Dimension(520, 30));
							{
								btnAddendumNew = new JButton("New");
								pnlNav.add(btnAddendumNew);
								btnAddendumNew.setActionCommand("Addendum New");
								btnAddendumNew.addActionListener(this);
								grpAddendumButton.add(btnAddendumNew);
							}
							{
								btnAddendumEdit = new JButton("Edit");
								pnlNav.add(btnAddendumEdit);
								btnAddendumEdit.setActionCommand("Addendum Edit");
								btnAddendumEdit.addActionListener(this);
								grpAddendumButton.add(btnAddendumEdit);
							}
							{
								btnAddendumDelete = new JButton("Delete");
								pnlNav.add(btnAddendumDelete);
								btnAddendumDelete.setActionCommand("Addendum Delete");
								btnAddendumDelete.addActionListener(this);
							}
							{
								btnAddendumSave = new JButton("Save");
								pnlNav.add(btnAddendumSave);
								btnAddendumSave.setActionCommand("Addendum Save");
								btnAddendumSave.addActionListener(this);
							}
							{
								btnAddendumCancel = new JButton("Cancel");
								pnlNav.add(btnAddendumCancel);
								btnAddendumCancel.setActionCommand("Addendum Cancel");
								btnAddendumCancel.addActionListener(this);
							}
						}
					}
				}
			}
		}

		loadDocuments();
		btnState(true, false, false, false, false);
		this.setComponentsEditable(pnlDocumentDetails, true);
		this.setComponentsEnabled(pnlDocumentDetails, false);
		txtID.setEditable(false);
		txtClientStatus.setEditable(false);

		loadAddendumDocuments();
	}

	private void btnState(Boolean sNew, Boolean sEdit, Boolean sDelete, Boolean sSave, Boolean sCancel) {
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnDelete.setEnabled(sDelete);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
	}

	private void loadDocuments() {
		rowHeaderAllDocuments.setModel(new DefaultListModel());
		displayAllDocuments(modelAllDocuments);
		scrollAllDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAllDocuments.getRowCount())));
		tblAllDocuments.packAll();
	}

	private void loadDocsBuyerTypes(String doc_id) {
		rowHeaderBuyerTypes.setModel(new DefaultListModel());
		displayBuyerTypes(modelBuyerTypes, doc_id);
		scrollBuyerTypes.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBuyerTypes.getRowCount())));
		tblBuyerTypes.packAll();
	}

	private void displayDocumentDetails(String doc_id, String doc_desc, String doc_alias, Boolean for_ocw, String client_status, String business_class, String report, String business_nature, String civil_status, String doc_sub_id) {//XXX
		
		//txtID.setText(Integer.toString(doc_id));
		txtID.setText(doc_id);
		txtName.setText(doc_desc);
		txtAlias.setText(doc_alias);
		//chkMarried.setSelected(for_married == null ? false:for_married);
		chkMandatory.setSelected(for_ocw == null ? false:for_ocw);
		txtClientStatus.setText(client_status == null ? "":String.format("[%s]", client_status));
		txtBusinessClass.setText(business_class == null ? "":String.format("[%s]", business_class));
		txtBusinessNature.setText(business_nature == null ? "":String.format("[%s]", business_nature));
		txtCivilStatus.setText(civil_status == null ? "":String.format("[%s]", civil_status));
		txtDocsSub.setText(doc_sub_id == null ? "":String.format("[%s]", doc_sub_id));
		txtReportName.setText(report);
		
		loadDocsBuyerTypes(doc_id);
	}

	private Icon getSearchIcon() {
		Image img = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/search1.png")).getImage();
		img = img.getScaledInstance(19, 19, Image.SCALE_SMOOTH);

		return new ImageIcon(img);
	}

	private void lookupClientStatus() {
		String selectedStatusID = txtClientStatus.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Client Status", getClientStatus(selectedStatusID), true);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[][] data = dlg.getResult();
		try {
			if(data.length > 0){
				ArrayList<String> listTypeID = new ArrayList<String>();
				for (int x = 0; x < data.length; x++) {
					String type_id = (String) data[x][0];
					listTypeID.add(type_id);
				}
				txtClientStatus.setText(listTypeID.toString());
			}else{
				txtClientStatus.setText("");
			}
		} catch (NullPointerException e) { }
	}

	private void lookupBusinessClass() {
		String selectedClassID = txtBusinessClass.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");

		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Business Class", getBusinessClass(selectedClassID), true);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);

		Object[][] data = dlg.getResult();
		try {
			if(data.length > 0){
				ArrayList<String> listTypeID = new ArrayList<String>();
				for (int x = 0; x < data.length; x++) {
					String type_id = (String) data[x][0];
					listTypeID.add(type_id);
				}
				txtBusinessClass.setText(listTypeID.toString());
			}else{
				txtBusinessClass.setText("");
			}
		} catch (NullPointerException e) { }
	}
	
	private void lookupBusinessNature(){
		String selectedBusinessNature = txtBusinessNature.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");
		
		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Business Class", getBusinessNature(selectedBusinessNature), true);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);
		
		Object[][] data = dlg.getResult();
		try {
			if(data.length > 0){
				ArrayList<String> listTypeID = new ArrayList<String>();
				for (int x = 0; x < data.length; x++) {
					String type_id = (String) data[x][0];
					listTypeID.add(type_id);
				}
				txtBusinessNature.setText(listTypeID.toString());
			}else{
				txtBusinessNature.setText("");
			}
		} catch (NullPointerException e) { }
	}
	
	private void lookupCivilStatus(){
		String selectedCivilStatus = txtCivilStatus.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");
		
		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Civil Status", getCivilStatus(selectedCivilStatus), true);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);
		
		Object[][] data = dlg.getResult();
		try {
			if(data.length > 0){
				ArrayList<String> listTypeID = new ArrayList<String>();
				for (int x = 0; x < data.length; x++) {
					String type_id = (String) data[x][0];
					listTypeID.add(type_id);
				}
				txtCivilStatus.setText(listTypeID.toString());
			}else{
				txtCivilStatus.setText("");
			}
		} catch (NullPointerException e) { }
	}
	
	private void lookupDocSub(){
		String selectedDocSub = txtDocsSub.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");
		
		_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, "Civil Status", getDocSub(selectedDocSub), true);
		dlg.setLocationRelativeTo(FncGlobal.homeMDI);
		dlg.setVisible(true);
		
		Object[][] data = dlg.getResult();
		try {
			if(data.length > 0){
				ArrayList<String> listTypeID = new ArrayList<String>();
				for (int x = 0; x < data.length; x++) {
					String type_id = (String) data[x][0];
					listTypeID.add(type_id);
				}
				txtDocsSub.setText(listTypeID.toString());
			}else{
				txtDocsSub.setText("");
			}
		} catch (NullPointerException e) { }
	}

	private void newDocument() {
		clearDocumentDetails();

		String doc_id = getNewDocID();

		//txtID.setText(Integer.toString(doc_id));
		txtID.setText(doc_id);
		
		loadDocsBuyerTypes(doc_id);
		
		this.setComponentsEnabled(pnlDocumentDetails, true);
		btnState(false, false, false, true, true);
	}

	private void editDocument() {

		tblAllDocuments.setEnabled(false);
		this.setComponentsEnabled(pnlDocumentDetails, true);
		btnState(false, false, false, true, true);
	}

	private void saveDocument() {
		if (JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are all entries correct?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			ArrayList<String> listTypeID = new ArrayList<String>();
			ArrayList<String> listGroupID = new ArrayList<String>();

			for(int x=0; x < modelBuyerTypes.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelBuyerTypes.getValueAt(x, 0);
				String type_id = (String) modelBuyerTypes.getValueAt(x, 1);
				String group_id = (String) modelBuyerTypes.getValueAt(x, 4);

				if(isSelected){
					listTypeID.add(type_id);
					listGroupID.add(group_id);
				}
			}

			String doc_id = txtID.getText();
			String doc_desc = txtName.getText().replace("'", "''");
			String doc_alias = txtAlias.getText().replace("'", "''");
			String client_class = listGroupID.toString().replaceAll("\\[|\\]", "'").replace(", ", "', '");
			String buyertype = listTypeID.toString().replaceAll("\\[|\\]", "'").replace(", ", "', '");
			String client_status = txtClientStatus.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");
			String business_class = txtBusinessClass.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");
			String business_nature = txtBusinessNature.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");
			String civil_status = txtCivilStatus.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");
			String doc_sub_id = txtDocsSub.getText().replaceAll("\\[|\\]", "'").replace(", ", "', '");
			
			//Boolean married = chkMarried.isSelected();
			Boolean ocw = chkMandatory.isSelected();
			String url = txtReportName.getText().replace("'", "''");

			if(commitDocument(doc_id, doc_desc, doc_alias, client_class, buyertype, client_status, business_class, business_nature, civil_status, doc_sub_id ,ocw, url)){
				loadDocuments();
				btnState(true, tblAllDocuments.getSelectedRowCount() > 0, false, false, false);
				this.setComponentsEnabled(pnlDocumentDetails, false);
				tblAllDocuments.setEnabled(true);
				resetButtonGroup();
			};
		}
	}

	private void cancelDocument() {
		tblAllDocuments.setEnabled(true);
		this.setComponentsEnabled(pnlDocumentDetails, false);
		btnState(true, true, true, false, false);
		resetButtonGroup();

		if(tblAllDocuments.getSelectedRowCount() > 0){
			//Integer doc_id = (Integer) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 0);
			
			String doc_id = (String) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 0);
			String doc_desc = (String) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 1);
			String doc_alias = (String) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 2);
			//Boolean for_married = (Boolean) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 7);
			Boolean for_ocw = (Boolean) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 7);
			String client_status = (String) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 8);
			String business_class = (String) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 9);
			String report = (String) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 10);
			String business_nature = (String) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 11);
			String civil_status = (String) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 12);
			String doc_sub_id = (String) modelAllDocuments.getValueAt(tblAllDocuments.getSelectedRow(), 13);
			
			displayDocumentDetails(doc_id, doc_desc, doc_alias, for_ocw, client_status, business_class, report, business_nature, civil_status, doc_sub_id);
			btnState(true, true, true, false, false);
		}
	}

	private void clearDocumentDetails() {
		txtID.setText("");
		txtName.setText("");
		txtAlias.setText("");
		txtReportName.setText("");
		txtClientStatus.setText("");
		txtBusinessClass.setText("");
		//chkMarried.setSelected(false);
		chkMandatory.setSelected(false);
	}

	private void resetButtonGroup() {
		grpButton.clearSelection();
		grpButton = new _ButtonGroup();
		grpButton.add(btnNew);
		grpButton.add(btnEdit);
	}

	private void loadAddendumDocuments() {
		rowHeaderAddendumDocuments.setModel(new DefaultListModel());
		displayAddendumDocuments(modelAddendumDocuments);
		scrollAddendumDocuments.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblAddendumDocuments.getRowCount())));
		tblAddendumDocuments.packAll();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {//XXX action
		String action = evt.getActionCommand();

		if(action.equals("ClientStatus")){
			lookupClientStatus();
		}
		if(action.equals("BusinessClass")){
			lookupBusinessClass();
		}
		
		if(action.equals("BusinessNature")){
			lookupBusinessNature();
		}
		
		if(action.equals("CivilStatus")){
			lookupCivilStatus();
		}
		
		if(action.equals("DocsSub")){
			lookupDocSub();
		}

		if(action.equals("New")) {
			grpButton.setSelectedButton(evt);
			newDocument();
		}
		if(action.equals("Edit")) {
			grpButton.setSelectedButton(evt);
			editDocument();
		}
		if(action.equals("Delete")) {

		}
		if(action.equals("Save")) {
			saveDocument();
		}
		if(action.equals("Cancel")) {
			cancelDocument();
		}
	}

	private JPopupMenu initializeMenu() {
		JPopupMenu menu = new JPopupMenu();

		JMenuItem menuRefresh = new JMenuItem("Refresh");
		menu.add(menuRefresh);
		menuRefresh.setFont(menuRefresh.getFont().deriveFont(10f));
		menuRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadDocuments();
			}
		});

		return menu;
	}

	/**
	 * 
	 * Database related methods
	 * 
	 *
	 */
	private String getNewDocID() {
		String SQL = "SELECT (MAX(doc_id::INT) + 1)::VARCHAR FROM mf_documents;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return null;
		}
	}

	private void displayAllDocuments(modelDocumentsMaintenance_DocsMonitoring model) {
		model.clear();

		String SQL = "SELECT TRIM(doc_id), TRIM(doc_desc), TRIM(doc_alias),\n" + 
				"  client_class @> ARRAY['02']::VARCHAR[] as ihf,\n" + 
				"  client_class @> ARRAY['03']::VARCHAR[] as cash,\n" + 
				"  client_class @> ARRAY['04']::VARCHAR[] as hdmf,\n" + 
				"  null, mandatory, array_to_string(client_status, ', ') as client_status, \n"+
				"  array_to_string(business_class, ', ') as business_class, \n"+
				"TRIM(url) as report, \n" + 
				"array_to_string(business_nature, ', ') as business_nature, \n" + 
				"array_to_string(civil_status, ', ') as civil_status, \n" + 
				"array_to_string(doc_sub_id, ', ') as doc_sub_id \n"+
				"FROM mf_documents\n" + 
				"WHERE modules = 'DM'\n" + 
				"AND status_id = 'A'\n" + 
				"ORDER BY doc_desc;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		
		FncSystem.out("Display All Documents", SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	private void displayBuyerTypes(modelDocumentsMaintenance_BuyerTypes model, String doc_id) {
		model.clear();

		String SQL = "SELECT (COALESCE(buyertype, '00') = type_id) as sel, TRIM(type_id)::VARCHAR as id, TRIM(type_desc) as description, TRIM(type_alias) as alias, a.type_group_id\n" + 
				"FROM mf_buyer_type a\n" + 
				"LEFT JOIN (SELECT unnest(buyertype) as buyertype FROM mf_documents WHERE doc_id = '"+ doc_id +"') b ON b.buyertype = a.type_id\n" + 
				"ORDER BY type_id::INT;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

	private String getClientStatus(String selectedStatusID) {
		String SQL = "SELECT ARRAY["+ selectedStatusID +"]::VARCHAR[] @> ARRAY[TRIM(byrstatus_id)]::VARCHAR[] as \"Select\", TRIM(byrstatus_id)::VARCHAR as \"ID\", TRIM(status_desc) as \"Description\", TRIM(status_alias) as \"Alias\"\n" + 
				"FROM mf_buyer_status\n" + 
				"WHERE status_id = 'A'\n" +
				"AND TRIM(byrstatus_id) IN ('01', '17')" + 
				"ORDER BY byrstatus_id::INT;";
		FncSystem.out("Client Status", SQL);
		return SQL;
	}

	private String getBusinessClass(String selectedClassID) {
		String SQL = "SELECT ARRAY["+ selectedClassID +"]::VARCHAR[] @> ARRAY[class_id]::VARCHAR[] as \"Select\", class_id as \"ID\", TRIM(class_name) as \"Description\", TRIM(class_type) as \"Group\"\n" + 
				"FROM mf_business_class\n" + 
				"WHERE status_id = 'A';";
		FncSystem.out("Business Class", SQL);
		return SQL;
	}
	
	private String getBusinessNature(String selectedBusinessNature){
		String SQL = "SELECT ARRAY["+selectedBusinessNature+"]::VARCHAR[] @> ARRAY[nob_code]::VARCHAR[] as \"Select\", nob_code as \"ID\", TRIM(nob_desc) as \"Description\"\n" + 
					 "FROM mf_nature_business\n" + 
					 "WHERE status_id = 'A'\n" + 
					 "order by nob_desc;\n";
		FncSystem.out("Business nature", SQL);
		return SQL;
	}
	
	private String getCivilStatus(String selectedCivilStatus){
		String SQL = "SELECT ARRAY["+selectedCivilStatus+"]::VARCHAR[] @> ARRAY[civil_status_code]::VARCHAR[] as \"Select\", civil_status_code as \"ID\", TRIM(civil_status_desc) as \"Description\"\n" + 
					 "FROM mf_civil_status\n" + 
					 "WHERE status_id = 'A';";
		FncSystem.out("Civil Status", SQL);
		return SQL;
	}
	
	private String getDocSub(String selectedDocSub){
		String SQL = "SELECT ARRAY["+selectedDocSub+"]::VARCHAR[] @> ARRAY[doc_id]::VARCHAR[] as \"Select\", \n" + 
					 "TRIM(doc_id) as \"ID\", TRIM(doc_desc) AS \"Description\", TRIM(doc_alias) as \"Alias\"\n" + 
					 "FROM mf_documents\n" + 
					 "WHERE modules = 'DM'\n" + 
					 "AND status_id = 'A'\n" + 
					 "ORDER BY doc_desc;";
		FncSystem.out("Docs Sub", SQL);
		return SQL;
	}

	private Boolean commitDocument(String doc_id, String doc_desc, String doc_alias, String client_class, String buyertype, String client_status, String business_class, String business_nature, 
			String civil_status,String doc_sub_id, Boolean ocw, String url) {

		String SQL = "SELECT sp_save_required_document_v2('"+ doc_id +"', '"+ doc_desc +"', '"+ doc_alias +"', ARRAY["+ client_class +"]::VARCHAR[],\n" + 
				"     ARRAY["+ buyertype +"]::VARCHAR[], ARRAY["+ client_status +"]::VARCHAR[], ARRAY["+ business_class +"]::VARCHAR[], ARRAY["+business_nature+"]::VARCHAR[], \n"+
				"	  ARRAY["+civil_status+"]::VARCHAR[], ARRAY["+doc_sub_id+"]::VARCHAR[], FALSE, "+ ocw +", '"+ url +"', \n" + 
				"     '"+ UserInfo.EmployeeCode +"', '"+ UserInfo.EmployeeCode +"');";

		try {
			pgSelect db = new pgSelect();
			db.select(SQL, "Save", true);
			
			FncSystem.out("Display Saving of Required Document", SQL);

			return true;
		} catch (Exception e) {
			return false;
		}
	}



	private void displayAddendumDocuments(modelDocumentsMaintenance_AddendumDocs model) {
		model.clear();

		String SQL = "SELECT cts_doc_id, cts_doc_name, regularly_employed_ru, regularly_employed_su, \n" + 
				"       self_employed_ru, self_employed_su, ofw_ru, ofw_su, no_of_orig_copies, \n" + 
				"       no_of_photocopy, remarks, created_by, date_created, edited_by, date_edited\n" + 
				"FROM dm_addendum_to_cts_docs\n" + 
				"WHERE status_id = 'A'\n" + 
				"ORDER BY cts_doc_id::INT;";

		pgSelect db = new pgSelect();
		db.select(SQL);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				model.addRow(db.getResult()[x]);
			}
		}
	}

}
