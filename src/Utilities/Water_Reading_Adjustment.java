package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_water_reading_adjmt;

public class Water_Reading_Adjustment extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = -487159299301892349L;
	static String title = "WATER READING ADJUSTMENT";
	Dimension frameSize = new Dimension(650, 600);

	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	@SuppressWarnings("unused")
	private JPanel pnlMain;

	private JPanel pnlNorth;
	private JPanel pnlNorthWest;
	private JPanel pnlNorthWestLabels;
	private JPanel pnlNorthCtr;
	private JPanel pnlNorthEast;

	private JPanel pnlSouth;
	private JPanel pnlSouthTop;
	private JPanel pnlSouthWestLabels;
	private JPanel pnlSouthEast;
	private JPanel pnlSouthBottom;
	private JPanel pnlSouthButtons;
	private JPanel pnlDate;
	private JPanel pnlDate1;
	private JPanel pnlInfo;
	private JPanel pnlInfo1;

	private _JLookup lookupClient;
	private _JDateChooser dateSched;

	private JLabel lblClient;
	private JLabel lblClientName;
	private JLabel lblProj;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblUnitDesc;

	private JLabel lblRDate; 
	private JLabel lblPReading;
	private JLabel lblCReading;
	private JLabel lblAmtDue;
	private JLabel lblRemarks;

	private JTextField txtProj;
	private JTextField txtUnit;
	private JTextField txtN1;
	private JTextField txtN2;
	private JTextField txtPReading;
	private JTextField txtCReading;
	private JTextField txtAmtDue;
	private JTextField txtRemarks;

	public static JList rowWRA;
	private _JTableMain tblWRA;
	private JScrollPane scrWRA;
	private model_water_reading_adjmt modelWRA;

	private JButton btnRefresh;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnPreview;

	private Integer row;
	private Integer seq_no;

	public Water_Reading_Adjustment() {
		super(title, false, true, false, false);
		initGUI(); 
	}

	public Water_Reading_Adjustment(String title) {
		super(title);
		initGUI(); 
	}

	public Water_Reading_Adjustment(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) 
	{
		super();
		initGUI(); 
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);

		JPanel pnlMain = new JPanel(new BorderLayout(5,5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		pnlMain.setBackground(Color.darkGray);
		{
			//Upper Panel
			pnlNorth = new JPanel (new BorderLayout(3,3));
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			//pnlNorth.setBorder(new TitledBorder(null, "CLIENT INFORMATION", 0, 0, null, Color.darkGray));
			pnlNorth.setBorder(JTBorderFactory.createTitleBorder("CLIENT INFORMATION", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			pnlNorth.setPreferredSize(new Dimension(650,100));
			{
				pnlNorthWest = new JPanel (new BorderLayout());
				pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
				pnlNorthWest.setPreferredSize(new Dimension(180,100));
				pnlNorthWest.setBorder(BorderFactory.createEmptyBorder());
				{
					pnlNorthWestLabels = new JPanel (new GridLayout(3,1,1,1));
					pnlNorthWest.add(pnlNorthWestLabels, BorderLayout.WEST);
					pnlNorthWestLabels.setPreferredSize(new Dimension(60,100));
					pnlNorthWestLabels.setBorder(BorderFactory.createEmptyBorder());
					{
						lblClient = new JLabel("Client");
						pnlNorthWestLabels.add(lblClient);
						lblClient.setHorizontalAlignment(JLabel.LEFT);

						lblProj = new JLabel("Project");
						pnlNorthWestLabels.add(lblProj);
						lblProj.setHorizontalAlignment(JLabel.LEFT);

						lblUnit = new JLabel("Unit");
						pnlNorthWestLabels.add(lblUnit);
						lblUnit.setHorizontalAlignment(JLabel.LEFT);
					};

					pnlNorthCtr = new JPanel (new GridLayout(3,1,1,1));
					pnlNorthWest.add(pnlNorthCtr, BorderLayout.EAST);
					pnlNorthCtr.setPreferredSize(new Dimension(120,90));
					pnlNorthCtr.setBorder(BorderFactory.createEmptyBorder());
					{
						lookupClient = new _JLookup(null, "Client");
						pnlNorthCtr.add(lookupClient,BorderLayout.EAST);
						lookupClient.setReturnColumn(1);
						lookupClient.setLookupSQL(SQL_CLIENT());
						lookupClient.addLookupListener(new LookupListener() 
						{
							public void lookupPerformed(LookupEvent event) 
							{
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									String entity_id = (String) data[0];
									String proj_id = (String) data[6];
									String pbl_id = (String) data[3];
									seq_no = (Integer) data[4];
									
									
									lookupClient.setValue(String.valueOf(data[0]));
									lblClientName.setText(String.format("%s", (String) data[1]));
									lblProject.setText(String.format("%s", (String) data[7]));
									lblUnitDesc.setText(String.format("%s",FncGlobal.GetString("select description from mf_unit_info where pbl_id = '"+(String) data[3]+"' ")));
									txtProj.setText(String.format("%s", (String) data[6]));
									txtUnit.setText(String.format("%s", (String) data[3]));

									KEYBOARD_MANAGER.focusNextComponent();

									GenerateList(entity_id, proj_id, pbl_id, seq_no);
									btnSave.setEnabled(false);
								}
							}
						});
					}

					{
						txtProj = new JTextField();
						pnlNorthCtr.add(txtProj, BorderLayout.EAST);
						txtProj.setBackground(new Color(182,231,238));
						txtProj.setHorizontalAlignment(SwingConstants.CENTER);
						txtProj.setEnabled(false);
						txtProj.setEditable(false);
					}
					{
						txtUnit = new JTextField();
						pnlNorthCtr.add(txtUnit, BorderLayout.EAST);
						txtUnit.setBackground(new Color(182,231,238));
						txtUnit.setHorizontalAlignment(SwingConstants.CENTER);
						txtUnit.setEnabled(false);
						txtUnit.setEditable(false);
					}

				};

				pnlNorthEast = new JPanel (new GridLayout(3,1,1,1));
				pnlNorth.add(pnlNorthEast, BorderLayout.CENTER);
				pnlNorthEast.setPreferredSize(new Dimension(370,100));
				pnlNorthEast.setBackground(new Color(201,206,204));
				{

					lblClientName = new JLabel("[ ]");
					pnlNorthEast.add(lblClientName);
					lblClientName.setHorizontalAlignment(JLabel.LEFT);
					lblClientName.setForeground(Color.black);
					lblClientName.setBorder(BorderFactory.createLineBorder(Color.white));

					lblProject = new JLabel("[ ]");
					pnlNorthEast.add(lblProject);
					lblProject.setHorizontalAlignment(JLabel.LEFT);
					lblProject.setForeground(Color.black);
					lblProject.setBorder(BorderFactory.createLineBorder(Color.white));

					lblUnitDesc = new JLabel("[ ]");
					pnlNorthEast.add(lblUnitDesc);
					lblUnitDesc.setHorizontalAlignment(JLabel.LEFT);
					lblUnitDesc.setForeground(Color.black);
					lblUnitDesc.setBorder(BorderFactory.createLineBorder(Color.white));
				}
			}

			//Lower Panel
			pnlSouth = new JPanel (new BorderLayout(3,3));
			pnlMain.add(pnlSouth, BorderLayout.CENTER);
			//pnlSouth.setBorder(new TitledBorder(null, "METER READING INFORMATION", 0, 0, null, Color.darkGray));
			pnlSouth.setBorder(JTBorderFactory.createTitleBorder("METER READING INFORMATION", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			pnlSouth.setPreferredSize(new Dimension(650,425));
			{
				pnlSouthTop = new JPanel (new BorderLayout(1,1));
				pnlSouth.add(pnlSouthTop, BorderLayout.NORTH);
				pnlSouthTop.setPreferredSize(new Dimension(650,100));
				pnlSouthTop.setBorder(BorderFactory.createEmptyBorder());
				{	//for labels
					pnlSouthWestLabels = new JPanel (new GridLayout(5,2,1,1));
					pnlSouthTop.add(pnlSouthWestLabels, BorderLayout.WEST);
					pnlSouthWestLabels.setBorder(BorderFactory.createEmptyBorder());
					{
						lblRDate = new JLabel("Reading Date");
						pnlSouthWestLabels.add(lblRDate);
						lblRDate.setHorizontalAlignment(JLabel.LEFT);

						
						lblPReading = new JLabel("Previous Reading");
						pnlSouthWestLabels.add(lblPReading);
						lblPReading.setHorizontalAlignment(JLabel.LEFT);

						
						lblCReading = new JLabel("Current Reading");
						pnlSouthWestLabels.add(lblCReading);
						lblCReading.setHorizontalAlignment(JLabel.LEFT);


						lblAmtDue = new JLabel("Amount Due");
						pnlSouthWestLabels.add(lblAmtDue);
						lblAmtDue.setHorizontalAlignment(JLabel.LEFT);


						lblRemarks = new JLabel("Remarks");
						pnlSouthWestLabels.add(lblRemarks);
						lblRemarks.setHorizontalAlignment(JLabel.LEFT);
					}

					pnlSouthEast = new JPanel (new BorderLayout());
					pnlSouthTop.add(pnlSouthEast, BorderLayout.CENTER);
					pnlSouthEast.setPreferredSize(new Dimension (400,100));
					{
						//for date picker
						pnlDate = new JPanel (new BorderLayout());
						pnlSouthEast.add(pnlDate, BorderLayout.NORTH);
						pnlDate.setPreferredSize(new Dimension (200,20));
						{
							pnlDate1 = new JPanel (new GridLayout(1,3,1,1));
							pnlDate.add(pnlDate1, BorderLayout.CENTER);
							pnlDate1.setPreferredSize(new Dimension (120,20));
							{
								dateSched = new _JDateChooser("MM/dd/yyyy", "##/##/#####", '_');
								pnlDate1.add(dateSched);
								dateSched.setDate(null);
								dateSched.setDateFormatString("yyyy-MM-dd");
								dateSched.setDate(Calendar.getInstance().getTime());
								((JTextFieldDateEditor)dateSched.getDateEditor()).setEditable(false);
							}
							//empty
							txtN1 = new JTextField();
							pnlDate1.add(txtN1, BorderLayout.CENTER);
							txtN1.setBorder(_EMPTY_BORDER);
							txtN1.setEditable(false);
							txtN1.setEnabled(false);

							txtN2 = new JTextField();
							pnlDate1.add(txtN2, BorderLayout.CENTER);
							txtN2.setBorder(_EMPTY_BORDER);
							txtN2.setEditable(false);
							txtN2.setEnabled(false);
						}
						pnlInfo = new JPanel (new BorderLayout());
						pnlSouthEast.add(pnlInfo, BorderLayout.CENTER);
						pnlInfo.setPreferredSize(new Dimension (400,80));
						pnlInfo.setBackground(Color.darkGray);
						{
							pnlInfo1 = new JPanel (new GridLayout(4,1,0,1));
							pnlInfo.add(pnlInfo1, BorderLayout.CENTER);
							pnlInfo1.setPreferredSize(new Dimension(400,100));
							pnlInfo1.setBackground(new Color(201,206,204));
							{
								txtPReading = new JTextField();
								pnlInfo1.add(txtPReading, BorderLayout.CENTER);
								txtPReading.setBackground(new Color(201,206,204));
								txtPReading.setForeground(Color.black);
								txtPReading.setBorder(BorderFactory.createLineBorder(Color.white));
								txtPReading.setEnabled(true);
								txtPReading.setEditable(false);
								txtPReading.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e){
										computeAmountDues();
									}
								});

								txtCReading = new JTextField();
								pnlInfo1.add(txtCReading, BorderLayout.CENTER);
								txtCReading.setBackground(new Color(201,206,204));
								txtCReading.setForeground(Color.black);
								txtCReading.setBorder(BorderFactory.createLineBorder(Color.white));
								txtCReading.setEnabled(true);
								txtCReading.setEditable(false);
								txtCReading.addKeyListener(new KeyAdapter() {
									public void keyReleased(KeyEvent e){
										computeAmountDues();
									}
								});

								txtAmtDue = new JTextField();
								pnlInfo1.add(txtAmtDue, BorderLayout.CENTER);
								txtAmtDue.setBackground(new Color(201,206,204));
								txtAmtDue.setForeground(Color.blue);
								txtAmtDue.setBorder(BorderFactory.createLineBorder(Color.white));
								txtAmtDue.setEnabled(true);
								txtAmtDue.setEditable(true);

								txtRemarks = new JTextField();
								pnlInfo1.add(txtRemarks, BorderLayout.CENTER);
								txtRemarks.setBackground(new Color(201,206,204));
								txtRemarks.setForeground(Color.black);
								txtRemarks.setBorder(BorderFactory.createLineBorder(Color.white));
								txtRemarks.setEnabled(true);
								txtRemarks.setEditable(true);
							}
						}
					}
					//for table -- sample update 4/10/2019
					pnlSouthBottom = new JPanel (new BorderLayout(2,2));
					pnlSouth.add(pnlSouthBottom, BorderLayout.CENTER);
					pnlSouthBottom.setPreferredSize(new Dimension(650,325));
					pnlSouthBottom.setBorder(BorderFactory.createLineBorder(Color.darkGray));
					{
						scrWRA = new JScrollPane();
						pnlSouthBottom.add(scrWRA, BorderLayout.CENTER);
						{
							modelWRA = new model_water_reading_adjmt();
							tblWRA = new _JTableMain(modelWRA);
							scrWRA.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrWRA.setViewportView(tblWRA);
							tblWRA.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
							{
								public void valueChanged(ListSelectionEvent e)
								{
									if(!e.getValueIsAdjusting())
									{
										try 
										{
											row = tblWRA.convertRowIndexToModel(tblWRA.getSelectedRow());
											Date reading_date = (Date) modelWRA.getValueAt(row, 0);
											String prev_reading = (String) modelWRA.getValueAt(row, 1).toString();
											String curr_reading = (String) modelWRA.getValueAt(row, 2).toString();
											String total_amt = (String) modelWRA.getValueAt(row, 4).toString();
											String remarks = (String) modelWRA.getValueAt(row, 5).toString();
											String water_meter_no = (String) modelWRA.getValueAt(row, 6).toString();

											dateSched.setDate(reading_date);
											txtPReading.setText(prev_reading);
											txtCReading.setText(curr_reading);
											txtAmtDue.setText(total_amt);
											txtRemarks.setText(remarks);
										} catch (ArrayIndexOutOfBoundsException e1) {

										} catch (IndexOutOfBoundsException e2) {}
									}
								}
							});
							tblWRA.hideColumns("Water Meter No.");
							tblWRA.setSortable(false);
							tblWRA.packAll();

						}
						{
							rowWRA = tblWRA.getRowHeader();
							rowWRA.setModel(new DefaultListModel());
							scrWRA.setRowHeaderView(rowWRA);
							scrWRA.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}	
					}

					pnlSouthButtons = new JPanel (new GridLayout(1,4));
					pnlSouthBottom.add(pnlSouthButtons, BorderLayout.SOUTH);
					pnlSouthButtons.setPreferredSize(new Dimension(650,30));
					{
						Color light_cyan = new Color(128,255,255);

						btnEdit = new JButton ("Edit");
						pnlSouthButtons.add(btnEdit);
						btnEdit.setBorder(new EtchedBorder(light_cyan, null));
						btnEdit.setForeground(Color.white);
						btnEdit.setHorizontalAlignment(JButton.CENTER);
						btnEdit.setMnemonic(KeyEvent.VK_E);
						btnEdit.setActionCommand("Edit");
						btnEdit.addActionListener(this);
						btnEdit.setEnabled(true);

						btnSave = new JButton ("Save");
						pnlSouthButtons.add(btnSave);
						btnSave.setBorder(new EtchedBorder(light_cyan, null));
						btnSave.setBackground(Color.darkGray);
						btnSave.setForeground(Color.white);
						btnSave.setHorizontalAlignment(JButton.CENTER);
						btnSave.setMnemonic(KeyEvent.VK_S);
						btnSave.setActionCommand("Save");
						btnSave.addActionListener(this);
						btnSave.setEnabled(true);
						
						btnPreview = new JButton ("Preview");
						pnlSouthButtons.add(btnPreview);
						btnPreview.setBorder(new EtchedBorder(light_cyan, null));
						btnPreview.setBackground(Color.darkGray);
						btnPreview.setForeground(Color.white);
						btnPreview.setHorizontalAlignment(JButton.CENTER);
						btnPreview.setMnemonic(KeyEvent.VK_P);
						btnPreview.setActionCommand("Preview");
						btnPreview.addActionListener(this);
						btnPreview.setEnabled(true);

						btnRefresh= new JButton ("Refresh");
						pnlSouthButtons.add(btnRefresh);
						btnRefresh.setBorder(new EtchedBorder(light_cyan, null));
						btnRefresh.setBackground(Color.darkGray);
						btnRefresh.setForeground(Color.white);
						btnRefresh.setHorizontalAlignment(JButton.CENTER);
						btnRefresh.setMnemonic(KeyEvent.VK_R);
						btnRefresh.setActionCommand("Refresh");
						btnRefresh.addActionListener(this);
						btnRefresh.setEnabled(true);

					}
				}
			};
		}
	}

	public void actionPerformed(ActionEvent arg0){
		String actionCommand = arg0.getActionCommand();

		if(actionCommand.equals("Save")){

			int response = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(),"Are all your entries correct?.", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {

				/*String updateWRA = "update  rf_water_reading_v2 set reading_date = '"+dateSched.getDateString()+"' , created_by = '"+UserInfo.EmployeeCode +"', remarks = '"+txtRemarks.getText()+"', date_edited = now(),  \n" + 
						"initial_meter_reading = '"+txtPReading.getText()+"', meter_reading = '"+txtCReading.getText()+"',  \n" +
						"reading_amt = ((case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) = 0 then (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0))*0 else \n" + 
						"case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) <= 10 then 240.00 else \n" +  
						"case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) >= 11 and (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) <= 20 then (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0))*27 else  \n" + 
						"case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) >= 21 and (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) <= 30 then (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0))*30 else  \n" + 
						"case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) >= 31 and (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) <= 40 then (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0))*33 else  \n" + 
						"case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) >= 41 then (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0))*38 else 0.00 end end end end end end))  \n" + 
						"where pbl_id = '"+txtUnit.getText()+"' and water_meter_no = '"+modelWRA.getValueAt(row, 6).toString()+"' ";
				
				
				pgUpdate db = new pgUpdate();
				db.executeUpdate(updateWRA,true);
				db.commit();*/
				
				if(saveWaterReadingAdjustment()) {
					
					GenerateList(lookupClient.getValue(), txtProj.getText(), txtUnit.getText(), seq_no);
					
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Water Meter Adjustment saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}else
			refresh();
			tblWRA.requestFocus();
			tblWRA.changeSelection(row, 0, false, false);

		}

		if(actionCommand.equals("Edit")){ edit(); }
		if(actionCommand.equals("Preview")){ preview(); }
		if(actionCommand.equals("Refresh")){ refresh(); }


	}

	private void edit() {
		enableButtons(true,false,true,true);

		dateSched.setEditable(true);

		txtPReading.setEditable(true);
		txtCReading.setEditable(true);
		txtRemarks.setEditable(true);

	}
	
	private Boolean saveWaterReadingAdjustment() {
		
		String entity_id = lookupClient.getValue();
		String proj_id = txtProj.getText();
		String pbl_id = txtUnit.getText();
		Date reading_date = dateSched.getDate();
		String previous_reading = txtPReading.getText();
		String current_reading = txtCReading.getText();
		String amt_due = txtAmtDue.getText();
		String remarks = txtRemarks.getText();
		String water_meter_no = (String) modelWRA.getValueAt(row, 6);
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_save_water_reading_adjustment('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+", '"+reading_date+"'::TIMESTAMP ,COALESCE(NULLIF('"+previous_reading+"', '')::NUMERIC, 0), COALESCE(NULLIF('"+current_reading+"', '')::NUMERIC, 0), '"+amt_due+"' ,'"+remarks+"','"+water_meter_no+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL, "Save", true);
		
		if(db.isNotNull()) {
			return (Boolean) db.getResult()[0][0];
		}else {
			return false;
		}
		
	}

	private void enableButtons (Boolean a, Boolean b, Boolean c, Boolean d){
		btnRefresh.setEnabled(a);		
		btnEdit.setEnabled(b);
		btnSave.setEnabled(c);

	}
	private void preview() {
	//

	}
	
	private void refresh(){

		FncTables.clearTable(modelWRA);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowWRA.setModel(listModel);//Setting of DefaultListModel into rowHeader.
		enableButtons(true, true, false, true);

		lookupClient.setValue("");	

		dateSched.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));

		lblClientName.setText("");
		lblUnitDesc.setText("");
		lblProject.setText("");

		txtProj.setText("");
		txtUnit.setText(""); 
		txtPReading.setText("");
		txtCReading.setText("");
		txtAmtDue.setText("");
		txtRemarks.setText("");
		seq_no = null;

	}
	
	private void computeAmountDues(){
		String amount_due = "0.00";
		String previous_reading = txtPReading.getText();
		String current_reading = txtCReading.getText();
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT compute_water_amount_due(COALESCE(NULLIF('"+previous_reading+"', '')::NUMERIC, 0), COALESCE(NULLIF('"+current_reading+"', '')::NUMERIC, 0))::VARCHAR";
		db.select(SQL);
		
		FncSystem.out("Query", SQL);
		if(db.isNotNull()){
			amount_due =  (String) db.getResult()[0][0];
		}
		txtAmtDue.setText(amount_due);
	}

	private boolean GenerateList(String entity_id, String proj_id, String pbl_id, Integer seq_no) {
		boolean blnSuccess = false; 

		FncTables.clearTable(modelWRA);
		DefaultListModel listModel = new DefaultListModel();
		rowWRA.setModel(listModel); 

		System.out.println("pbl_id = '"+txtUnit.getText()+"'");

		pgSelect db = new pgSelect();
		/*String SQL = "select reading_date, coalesce(initial_meter_reading,0) as prev_reading,  (meter_reading) as current_reading, \n"+
				"((case when (meter_reading) - (initial_meter_reading) = 0 then '0.00' else \n"+
				" case when (meter_reading) - (initial_meter_reading) <= 10 then '(fixed : 240.00)' else \n"+
				" case when (meter_reading) - (initial_meter_reading) >= 11 and (meter_reading) - (initial_meter_reading) <= 20 then 'x 27.00/cu.m.' else \n"+	
				" case when (meter_reading) - (initial_meter_reading) >= 21 and (meter_reading) - (initial_meter_reading) <= 30 then 'x 30.00/cu.m.' else \n"+
				" case when (meter_reading) - (initial_meter_reading) >= 31 and (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) <= 40 then 'x 33.00/cu.m.' else \n"+
				" case when (meter_reading) - (initial_meter_reading) >= 41 then 'x 38.00/cu.m.' else '' end end end end end end)) as current_charge, \n"+
				"((case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) = 0 then 0.00 else   \n"+
				" case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) <= 10 then 240.00 else \n"+
				" case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) >= 11 and (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) <= 20 then (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0))*27 else \n"+
				" case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) >= 21 and (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) <= 30 then (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0))*30 else \n"+ 
				" case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) >= 31 and (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) <= 40 then (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0))*33 else \n"+ 
				" case when (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0)) >= 41 then (coalesce(meter_reading,0) - coalesce(initial_meter_reading,0))*38 else 0.00 end end end end end end)) as amount, \n"+
				"coalesce(remarks,'')  as remarks, \n"+
				"coalesce(water_meter_no,'')  as water_meter_no \n"+
				"from rf_water_reading_v2 \n " +
				"where pbl_id = '"+txtUnit.getText()+"' and status_id = 'A' \n"+
				"order by  reading_date::date desc";*/
		
		String SQL = "SELECT * FROM view_water_reading_adjustment('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+")";

		db.select(SQL);

		FncSystem.out("SQL water reading",SQL);

		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelWRA.addRow(db.getResult()[x]);
				listModel.addElement(modelWRA.getRowCount());
			}
			blnSuccess = true; 
		}

		return blnSuccess; 

	};


	//end	
}



