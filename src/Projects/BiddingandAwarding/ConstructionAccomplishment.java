package Projects.BiddingandAwarding;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import tablemodel.model_construction_accomplishment;
import DateChooser._JDateChooser;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTableMain;

/**
 * 
 * @author Jessa Herrera
 *
 */
public class ConstructionAccomplishment extends _JInternalFrame implements _GUI, ActionListener {
	
	static String title = "Construction Accomplishment";
	Dimension frameSize = new Dimension(700, 500);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	
	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;
	
	JLabel lblNorth;
	JLabel lblProject;
	JLabel lblNTPType;
	JLabel lblContractor;
	
	_JLookup lookupProject;
	
	model_construction_accomplishment modelCA;
	JScrollPane scrollCA;
	_JTableMain tblCA;
	JList rowheaderCA;
	_JDateChooser dateAsOf;
	
	private _JDateChooser dateFinish;

	public ConstructionAccomplishment() {
		super(title, true, true, false, true);
		initGUI();
	}

	public ConstructionAccomplishment(String title) {
		super(title, true, true, false, true);
		initGUI();
	}

	public ConstructionAccomplishment(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{

				pnlMain = new JPanel();
				this.getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setLayout(new BorderLayout(5, 5));
				pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					pnlNorth = new JPanel();
					pnlMain.add(pnlNorth, BorderLayout.NORTH);
					pnlNorth.setLayout(null);
					pnlNorth.setBorder(lineBorder);
					pnlNorth.setPreferredSize(new Dimension(788, 125));// XXX
					{
						lblNorth = new JLabel("As of date");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 10, 120, 22);
					}
					{
						dateAsOf = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
						pnlNorth.add(dateAsOf);
						dateAsOf.setBounds(90, 10, 148, 22);
					}
					{
						lblNorth = new JLabel("Project");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 35, 120, 22);
					}
					{
						lookupProject = new _JLookup(null, "Project");
						pnlNorth.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setLookupSQL(SQL_PROJECT("02"));
						lookupProject.setName("lookupProject");
						lookupProject.setBounds(90, 35, 120, 22);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									//System.out.printf("testing");
									lblProject.setText(String.format("[ %s ]", (String) data[1]));
								}
							}
						});
					}
					{
						lblProject = new JLabel("[ ]");
						pnlNorth.add(lblProject);
						lblProject.setHorizontalAlignment(JLabel.LEFT);
						lblProject.setBounds(215, 35, 250, 22);
					}
					{
						lblNorth = new JLabel("NTP Type");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 60, 120, 22);
					}
					{
						_JLookup lookupNTPType = new _JLookup(null, "NTP Type");
						pnlNorth.add(lookupNTPType);
						lookupNTPType.setReturnColumn(0);
						lookupNTPType.setLookupSQL(SQL_NTP_TYPE());
						lookupNTPType.setName("lookupNTPType");
						lookupNTPType.setBounds(90, 60, 120, 22);
						lookupNTPType.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									lblNTPType.setText(String.format("[ %s ]", (String) data[1]));
								}
							}
						});
					}
					{
						lblNTPType = new JLabel("[ ]");
						pnlNorth.add(lblNTPType);
						lblNTPType.setHorizontalAlignment(JLabel.LEFT);
						lblNTPType.setBounds(215, 60, 250, 22);
					}
					{
						lblNorth = new JLabel("Contractor");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 85, 120, 22);
					}
					{
						_JLookup lookupContractor = new _JLookup(null, "Contractor");
						pnlNorth.add(lookupContractor);
						lookupContractor.setReturnColumn(0);
						lookupContractor.setLookupSQL(SQL_CONTRACTOR());
						lookupContractor.setName("lookupContractor");
						lookupContractor.setBounds(90, 85, 120, 22);
						lookupContractor.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){
									lblContractor.setText(String.format("[ %s ]", (String) data[1]));
								}
							}
						});
					}
					{
						lblContractor = new JLabel("[ ]");
						pnlNorth.add(lblContractor);
						lblContractor.setHorizontalAlignment(JLabel.LEFT);
						lblContractor.setBounds(215, 85, 300, 22);
					}
					{
					pnlWest = new JPanel();
					//pnlMain.add(pnlWest, BorderLayout.WEST);
					pnlWest.setLayout(new BorderLayout(5, 5));
					pnlWest.setBorder(lineBorder);
					pnlWest.setPreferredSize(new Dimension(200, 400));// XXX	
					{
						JLabel lblWest = new JLabel("WEST");
						pnlWest.add(lblWest, BorderLayout.CENTER);
						lblWest.setHorizontalAlignment(JLabel.CENTER);
					}
				}
				{
					pnlEast = new JPanel();
					//pnlMain.add(pnlEast, BorderLayout.EAST);
					pnlEast.setLayout(new BorderLayout(5, 5));
					pnlEast.setBorder(lineBorder);
					pnlEast.setPreferredSize(new Dimension(200, 400));// XXX
					{
						JLabel lblEast = new JLabel("EAST");
						pnlEast.add(lblEast, BorderLayout.CENTER);
						lblEast.setHorizontalAlignment(JLabel.CENTER);
					}
				}
				{
					pnlCenter = new JPanel();
					pnlMain.add(pnlCenter, BorderLayout.CENTER);
					pnlCenter.setLayout(new BorderLayout(5, 5));
					pnlCenter.setBorder(lineBorder);
					{
						JScrollPane scrollCA = new JScrollPane();
						pnlCenter.add(scrollCA, BorderLayout.CENTER);
						scrollCA.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollCA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
						
						modelCA = new model_construction_accomplishment();
						modelCA.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								if(e.getType() == TableModelEvent.DELETE){
									rowheaderCA.setModel(new DefaultListModel());
								}
								if(e.getType() == TableModelEvent.INSERT){
									((DefaultListModel)rowheaderCA.getModel()).addElement(modelCA.getRowCount());
								}
							}
						});

						tblCA = new _JTableMain(modelCA);
						scrollCA.setViewportView(tblCA);
						
						rowheaderCA = tblCA.getRowHeader();
						rowheaderCA.setModel(new DefaultListModel());
						scrollCA.setRowHeaderView(rowheaderCA);
						scrollCA.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
						
					}
				}			
				{
					pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
					//pnlSouth.setBorder(lineBorder);
					pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX	
					{
						JButton btnUpload = new JButton("Upload");
						pnlSouth.add(btnUpload);
						btnUpload.setActionCommand("Upload");
						btnUpload.setMnemonic(KeyEvent.VK_U);
						btnUpload.addActionListener(this);
					}
					{
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
					}
					{
						JButton btnEdit = new JButton("Edit");
						pnlSouth.add(btnEdit);
						btnEdit.setActionCommand("Edit");
						btnEdit.setMnemonic(KeyEvent.VK_E);
						btnEdit.addActionListener(this);
						//grpNewEdit.add(btnEdit);
					}
					{
						JButton btnSave = new JButton("Save");
						pnlSouth.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.setMnemonic(KeyEvent.VK_S);
						btnSave.addActionListener(this);
					}
					{
						JButton btnCancel = new JButton("Cancel");
						pnlSouth.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.setMnemonic(KeyEvent.VK_C);
						btnCancel.addActionListener(this);
					}
				}
			}
		}

	}
	
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Upload")){
			//KEYBOARD_MANAGER.focusNextComponent();
		}
	}
	
	
	private String SQL_NTP_TYPE() {
		return "SELECT type_id as \"ID\", TRIM(type_desc) as \"Description\", type_alias as \"Alias\" FROM mf_ntp_type;";
	}
	private String SQL_CONTRACTOR() {
		return "SELECT entity_id as \"ID\", TRIM(entity_name) as \"Name\", vat_registered as \"VAT\", entity_kind as \"Kind\" FROM rf_entity WHERE entity_kind = 'C' AND NULLIF(TRIM(remarks), '') IS NULL AND status_id = 'A' ORDER BY entity_name;";
	}
}