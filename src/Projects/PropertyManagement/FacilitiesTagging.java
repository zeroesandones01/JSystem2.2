package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import DateChooser.DateEvent;
import DateChooser.DateListener;
import DateChooser._JDateChooser;
import Functions.FncSystem;
import Functions.FncTables;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelFacilitiesTagging;

public class FacilitiesTagging extends _JInternalFrame implements _GUI, ActionListener {
	
	private static final long serialVersionUID = -8574395597387250373L;
	
	static String title = "Facilities Tagging";
	Dimension frameSize = new Dimension(500, 500);
	
	private JPanel pnlNorth;
	private JPanel pnlCenter;
	private JPanel pnlSouth;

	private JLabel lblStatusDate;

	private _JDateChooser dteReading;
	
	private _JTableMain tblFacilitiesTagging;
	private modelFacilitiesTagging modelFacilitiesTag;
	private JScrollPane scrollFacilitiesTagging;
	private JList rowHeaderFacilitiesTagging;

	private JButton btnSave;
	private JButton btnCancel;
	
	public FacilitiesTagging() {
		super(title, true, true, false, true);
		initGUI();
	}

	public FacilitiesTagging(String title) {
		super(title);
		initGUI();
	}

	public FacilitiesTagging(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);

		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(panMain, BorderLayout.CENTER);
			panMain.setPreferredSize(frameSize);
			panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				panMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 50));
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder(""));
				{
					JPanel pnlNorthLabel = new JPanel(new GridLayout(1, 1, 3, 3));
					pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
					
					{
						lblStatusDate = new JLabel("Input Reading Date");
						pnlNorthLabel.add(lblStatusDate);
					}
				}
				{
					JPanel pnlNorthComponent = new JPanel(new GridLayout(1, 1, 3, 3));
					pnlNorth.add(pnlNorthComponent, BorderLayout.CENTER);
					{
						JPanel pnlDateCondition = new JPanel(new BorderLayout(3, 3));
						pnlNorthComponent.add(pnlDateCondition);
						{
							JPanel pnlDateConditionWest = new JPanel(new BorderLayout(3, 3));
							pnlDateCondition.add(pnlDateConditionWest, BorderLayout.WEST);
							pnlDateConditionWest.setPreferredSize(new Dimension(210, 0));
							{
								dteReading = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlDateConditionWest.add(dteReading, BorderLayout.CENTER);
								dteReading.setPreferredSize(new Dimension(140, 0));
								dteReading.addDateListener(new DateListener() {
									
									@Override
									public void datePerformed(DateEvent event) {
										Date reading_date = dteReading.getDate();
										displayFacilities(reading_date);
										
									}
								});
							}
						}
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				panMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Status List"));
				{
					scrollFacilitiesTagging = new JScrollPane();
					pnlCenter.add(scrollFacilitiesTagging, BorderLayout.CENTER);
					{
				
						modelFacilitiesTag = new modelFacilitiesTagging();
				
						tblFacilitiesTagging = new _JTableMain(modelFacilitiesTag);
						scrollFacilitiesTagging.setViewportView(tblFacilitiesTagging);
						scrollFacilitiesTagging.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						tblFacilitiesTagging.setSortable(false);
						tblFacilitiesTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No", "Rec. ID");
						
						modelFacilitiesTag.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								
								if(e.getType() == TableModelEvent.INSERT){
									((DefaultListModel)rowHeaderFacilitiesTagging.getModel()).addElement(modelFacilitiesTag.getRowCount());
								}

								if(modelFacilitiesTag.getRowCount() == 0){
									rowHeaderFacilitiesTagging.setModel(new DefaultListModel());
								}
							}
						});
					}
					{
						rowHeaderFacilitiesTagging = tblFacilitiesTagging.getRowHeader();
						rowHeaderFacilitiesTagging.setModel(new DefaultListModel());
						scrollFacilitiesTagging.setRowHeaderView(rowHeaderFacilitiesTagging);
						scrollFacilitiesTagging.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(3, 3));
				panMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 40));
				{
					JPanel pnlSouthLower = new JPanel(new GridLayout(1, 3));
					pnlSouth.add(pnlSouthLower);
					{
						pnlSouthLower.add(Box.createHorizontalBox());
					}
					{
						btnSave = new JButton("Save");
						pnlSouthLower.add(btnSave);
						btnSave.setActionCommand("Save");
						btnSave.addActionListener(this);
					}
					{
						btnCancel = new JButton("Cancel");
						pnlSouthLower.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.addActionListener(this);
					}
				}
			}
		}

	}//XXX END OF INIT GUI

	private void displayFacilities(Date reading_date) {
		modelFacilitiesTag.clear();
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT * FROM display_facilities_tagging('"+reading_date+"'::TIMESTAMP)";
		db.select(SQL);
		
		FncSystem.out("SQL for display Facilities", SQL);
		if(db.isNotNull()) {
			for(int x= 0; x<db.getRowCount(); x++){
				modelFacilitiesTag.addRow(db.getResult()[x]);
			}
			scrollFacilitiesTagging.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblFacilitiesTagging.getRowCount())));
			tblFacilitiesTagging.packAll();
		}
	}
	
}
