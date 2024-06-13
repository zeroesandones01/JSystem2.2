package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.toedter.calendar.JTextFieldDateEditor;

import Database.pgSelect;
import DateChooser._JDateChooser;
import Dialogs.dlgDate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;
import components._JButton;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.model_Cancellation_Status;

public class pnlTagAccountsStatus extends JPanel implements _GUI{

	public _JLookup lookupProjID;
	private _JXTextField txtProjName;
	private Cancellation cancel;
	private JPanel pnlCenter;
	private _JDateChooser dteStatus;
	private JScrollPane scrollCancelTag;
	private model_Cancellation_Status modelCancelTag;
	private _JTableMain tblCancelTag;
	private JList rowHeaderCancelTag;
	private JPanel pnlSouth;
	private JLabel lblEditable_Tag;
	private _JButton btnPost;
	private _JButton btnClear;
	private _JButton btnPreview;

	private String proj_id;
	private String proj_name;

	private _FCancellationProcessing functions= new _FCancellationProcessing();
	private Date dateFrom;
	private Date dateTo;
	private Boolean printall;
	public pnlTagAccountsStatus(Cancellation cancel){
		this.cancel = cancel;
		initGUI();
		btnState(false, false, false);
	}

	public pnlTagAccountsStatus() {

	}

	public pnlTagAccountsStatus(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public pnlTagAccountsStatus(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public pnlTagAccountsStatus(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}


	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		{
			JPanel pnlNorth = new JPanel(new BorderLayout(3, 3));
			this.add(pnlNorth,BorderLayout.NORTH);
			{
				JPanel pnlProject = new JPanel(new BorderLayout(3,3));
				pnlNorth.add(pnlProject,BorderLayout.CENTER);
				pnlProject.setPreferredSize(new Dimension(0, 60));
				pnlProject.setBorder(components.JTBorderFactory.createTitleBorder("Project"));
				{
					{

						lookupProjID = new _JLookup("ID", "Project", 0) ; /// XXX lookupProjID 
						pnlProject.add(lookupProjID,BorderLayout.WEST);
						lookupProjID.setReturnColumn(0);
						lookupProjID.setPreferredSize(new Dimension(100, 20));
						lookupProjID.addLookupListener(new LookupListener() {

							@Override
							public void lookupPerformed(LookupEvent e) {

								Object[] data = ((_JLookup)e.getSource()).getDataSet();

								if(data != null){

									proj_id = data[0].toString();
									proj_name = data[1].toString();
									txtProjName.setText(proj_name);


									new Thread(new Generate()).start();
									btnState(true, true, true);

								}

							}
						});

					}
					{
						txtProjName = new _JXTextField();
						pnlProject.add(txtProjName,BorderLayout.CENTER);
						txtProjName.setEditable(false);
						txtProjName.setPrompt("Project Name");
						txtProjName.setPreferredSize(new Dimension(100, 20));
					} 
				}

			}
			{

				pnlCenter = new JPanel(new BorderLayout(5,5));
				this.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setPreferredSize(new Dimension(0, 370));
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("Status of Cancellation Accounts"));
				{
					dteStatus = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCenter.add(dteStatus);
					dteStatus.setDate(null);
					((JTextFieldDateEditor)dteStatus.getDateEditor()).setEditable(false);
					dteStatus.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					((JTextFieldDateEditor)dteStatus.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
						public void insertUpdate(DocumentEvent evt) {
							SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

							if (modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(),0).equals(true)) {
								modelCancelTag.setValueAt(""+sdf.format(dteStatus.getDate()),tblCancelTag.getSelectedRow(),6);
							}
						}
						public void changedUpdate(DocumentEvent e) {}
						public void removeUpdate(DocumentEvent e) {}
					});
				}
				{

					scrollCancelTag = new JScrollPane();
					pnlCenter.add(scrollCancelTag, BorderLayout.CENTER);
					scrollCancelTag.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblCancelTag.clearSelection();
						}
					});
					{

						modelCancelTag = new model_Cancellation_Status();
						modelCancelTag.addTableModelListener(new TableModelListener() {
							public void tableChanged(TableModelEvent e) {
								//Addition of rows
								if(e.getType() == 1){
									((DefaultListModel)rowHeaderCancelTag.getModel()).addElement(modelCancelTag.getRowCount());

									if(modelCancelTag.getRowCount() == 0){
										rowHeaderCancelTag.setModel(new DefaultListModel());
									}
								}
							}
						});

						tblCancelTag = new _JTableMain(modelCancelTag);
						scrollCancelTag.setViewportView(tblCancelTag);
						modelCancelTag.setEditable(true);
						tblCancelTag.setHorizontalScrollEnabled(true);
						//tblCancelTag.addMouseMotionListener(this);
						//tblCancelTag.addMouseListener(this);
						//tblCancelTag.addKeyListener(this);
						tblCancelTag.hideColumns("Status ID","CSV ID","Entity ID","Project ID","PBL ID","Seq ID","Unit ID","PartID","Cancel ID","CSV Amount");

						tblCancelTag.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e){




								int selectedIndex =  tblCancelTag.convertRowIndexToModel(tblCancelTag.getSelectedRow());


								if ((Boolean) modelCancelTag.getValueAt(selectedIndex, 0)) { 

									if (tblCancelTag.getSelectedColumn()== 5) {
										String SQL = "select cancel_status_id as \"ID\",\n" + 
												"trim(status_desc) as \"Description\" \n" + 
												"from rf_cancel_status \n" + 
												"where status_id = 'A'\n" + 
												"and status_group = 'CNCL'\n" + 
												"and cancel_status_id not in\n" + 
												"\n" + 
												"(select cancel_status_id from rf_cancelled_accts_status \n" + 
												"where cancel_id = '"+ modelCancelTag.getValueAt(selectedIndex,14)+"' and status_id = 'A')\n" + 
												"";

										_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, SQL, false);

										dlg.setLocationRelativeTo(FncGlobal.homeMDI);
										dlg.setVisible(true); 

										Object[] data = dlg.getReturnDataSet();

										if(data != null){

											modelCancelTag.setValueAt(data[1], selectedIndex,5);
											modelCancelTag.setValueAt(data[0], selectedIndex,8);
											tblCancelTag.packAll();
											tblCancelTag.packColumn(7, 400, 400);

										}
									}

									if (tblCancelTag.getSelectedColumn()== 6) {
										if (e.getClickCount() ==2) {
											dteStatus.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
											dteStatus.getCalendarButton().doClick();
										}

									} // column 6
									/** Repaint for Highlight **/
									if (tblCancelTag.getSelectedColumn() == 0) {
										tblCancelTag.repaint();
										tblCancelTag.packAll();
										tblCancelTag.packColumn(7, 400, 400); 
									}

									/** Listener for boolean canEditStatus **/
									if (tblCancelTag.getSelectedColumn() == 7) {

										System.out.println("Enable :" + modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(),0).equals(true));
										modelCancelTag.setEditable(( modelCancelTag.getValueAt(tblCancelTag.getSelectedRow(),0).equals(true)) );

									} // col 8
								}else{

									JOptionPane.showMessageDialog(pnlTagAccountsStatus.this, "Please tag before you enter the details", "Incomplete", JOptionPane.OK_OPTION);
									return;
								}

							}

						});
						/** Repaint for Highlight **/
						tblCancelTag.getTableHeader().addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent evt) {
								tblCancelTag.repaint();
							}
						});
					}
					{
						rowHeaderCancelTag = tblCancelTag.getRowHeader();
						rowHeaderCancelTag.setModel(new DefaultListModel());
						scrollCancelTag.setRowHeaderView(rowHeaderCancelTag);
						scrollCancelTag.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						scrollCancelTag.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelTag.getRowCount())));

					}
				}

			}
			{
				pnlSouth = new JPanel(new BorderLayout(5,5));
				this.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 45));
				{
					{
						JPanel pnlPreview = new JPanel(new BorderLayout(3,3));
						pnlSouth.add(pnlPreview,BorderLayout.WEST);
						pnlPreview.setPreferredSize(new Dimension(110,45));
						{
							btnPreview = new _JButton("Preview");
							pnlPreview.add(btnPreview);
							btnPreview.addActionListener(new ActionListener() {



								@Override
								public void actionPerformed(ActionEvent arg0) {

									dlgDate date_cancel = new dlgDate(FncGlobal.homeMDI, "Print Report");
									date_cancel.setLocationRelativeTo(null);
									date_cancel.setVisible(true);

									dateFrom = date_cancel.getDateFrom();
									dateTo = date_cancel.getDateTo();

									printall = date_cancel.getPrintAll();


									new Thread(new ForPreview()).start();


								}
							});
						}
					}
					{

						JPanel pnlButton = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlSouth.add(pnlButton,BorderLayout.EAST);
						pnlButton.setPreferredSize(new Dimension(300, 45));
						{
							btnPost = new _JButton("Post");
							pnlButton.add(btnPost);
							btnPost.addActionListener(new ActionListener() {

								private int tagged;

								@Override
								public void actionPerformed(ActionEvent e) {

									int selectedIndex =  tblCancelTag.convertRowIndexToModel(tblCancelTag.getSelectedRow());

									if (modelCancelTag.getRowCount() == 0) {

										JOptionPane.showMessageDialog(pnlTagAccountsStatus.this, "Please generate qualified Account(s) first  ", "Incomplete", JOptionPane.OK_OPTION);
										return;
									}

									for (int x = 0; x < modelCancelTag.getRowCount(); x++) {

										Boolean SelectedItem = (Boolean) modelCancelTag.getValueAt(x, 0);
										if (SelectedItem) {
											tagged++;
										}
									}

									if (tagged ==0 ) {
										JOptionPane.showMessageDialog(pnlTagAccountsStatus.this, "Please Select Account(s) to be Posted  "  ,"Incomplete",JOptionPane.OK_OPTION);
										return;
									}

									if (tagged >= 1) {

										tblCancelTag.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);// Stops Cell Edit
										System.out.println("tagged >= 1");
										int isEmpty = 0;
										for (int x = 0; x < modelCancelTag.getRowCount(); x++) {
											Boolean SelectedItem = (Boolean) modelCancelTag.getValueAt(x, 0);
											if (SelectedItem) {
												if (modelCancelTag.getValueAt(x,6) == null || modelCancelTag.getValueAt(x,7) == null || modelCancelTag.getValueAt(x,8) == null || modelCancelTag.getValueAt(x,8).equals("")) {
													isEmpty++;
												}
											}
										}

										System.out.println("empty" +isEmpty);
										if (isEmpty >= 1) {

											System.out.println("empty ang reason" +isEmpty);
											JOptionPane.showMessageDialog(pnlTagAccountsStatus.this,"Please Select New Status,  Status Date and Remarks for the Selected Account(s)","Incomplete Details",JOptionPane.OK_OPTION);
											return;

										}

										int response = JOptionPane.showConfirmDialog(pnlTagAccountsStatus.this,"Are you sure you want to post new status(s)  ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
										if (response == JOptionPane.YES_OPTION) {

											new Thread(new ForPosting()).start();

											JOptionPane.showMessageDialog( pnlTagAccountsStatus.this, "Selected accounts status updated  ", "Successful", JOptionPane.INFORMATION_MESSAGE );

											Clear();
											btnState(false, false, false);

										}
									}


								}
							});
						}
						{
							btnClear = new _JButton("Clear");
							pnlButton.add(btnClear);
							btnClear.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									int response = JOptionPane.showConfirmDialog( pnlTagAccountsStatus.this,"Are you sure you want to Clear all fields? ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
									if (response == JOptionPane.YES_OPTION) {
										Clear();
										btnState(false, false, false);
									}


								}
							});
						}
					}
				}
			}
		}

	}
	public class ForPreview implements Runnable{

		@Override
		public void run() {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", cancel.company);
			mapParameters.put("co_id", cancel.co_id);
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ cancel.company_logo));
			mapParameters.put("dateFrom", dateFrom);
			mapParameters.put("dateTo", dateTo);
			mapParameters.put("printall", printall);
			mapParameters.put("proj_id", lookupProjID.getValue());


			FncReport.generateReport("/Reports/rptCancellationStatus.jasper", "Status of Canceled Accounts", cancel.company, mapParameters);	
		}
	}
	public class Generate implements Runnable{

		@Override
		public void run() {
			FncGlobal.startProgress("Please wait. . . ");

			rowHeaderCancelTag.setModel(new DefaultListModel());
			functions.generateCancellationStatus(modelCancelTag, rowHeaderCancelTag, proj_id);
			scrollCancelTag.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelTag.getRowCount())));

			tblCancelTag.packAll();
			FncGlobal.stopProgress();
		}
	}
	public class ForPosting implements Runnable{
		@Override
		public void run() {
			FncGlobal.startProgress("Posting  . . .Please wait ");

			PostingStatusTag(); 
			FncGlobal.stopProgress();
		}
	}

	private pgSelect db =new pgSelect();

	private void PostingStatusTag(){
		for (int i = 0 ;  i < modelCancelTag.getRowCount(); i++) {

			Boolean ifSelected =  (Boolean) modelCancelTag.getValueAt(i, 0);

			if (ifSelected) {

				String user_id 			= UserInfo.EmployeeCode.trim();
				String transdate		=  (String) modelCancelTag.getValueAt(i, 6);
				String remarks 			= modelCancelTag.getValueAt(i, 7).toString().replace("'", "''");
				String cancel_status_id =  modelCancelTag.getValueAt(i, 8).toString();

				/*String _entity_id = modelCancelTag.getValueAt(i, 11).toString();
				String proj_id_ = modelCancelTag.getValueAt(i, 12).toString();
				String _pbl_id = modelCancelTag.getValueAt(i, 13).toString();
				Integer _seq_no = (Integer) modelCancelTag.getValueAt(i, 14); 
				 */

				String SQL = "";

				Integer cancel_id = (Integer) modelCancelTag.getValueAt(i, 14); 

				SQL = "SELECT sp_cancellation_status_tag_new(\n" + 
						"    "+cancel_id+" ,\n" + 
						"    '"+cancel_status_id+"' ,\n" + 
						"    '"+transdate+"'::timestamp ,\n" + 
						"    '"+remarks+"' ,\n" + 
						"    '"+user_id+"' ,\n" + 
						"    "+ifSelected+" )";		

				System.out.printf("SQL: %s%n", SQL);
				db.select(SQL);

			} 
		}	
	}


	private void btnState(Boolean sPreview, Boolean sPost, Boolean Clear){

		btnPreview.setEnabled(sPreview);
		btnPost.setEnabled(sPost);
		btnClear.setEnabled(Clear);
	}

	private void Clear(){
		lookupProjID.setValue("");
		txtProjName.setText("");

		modelCancelTag.clear();

		rowHeaderCancelTag.setModel(new DefaultListModel());
		scrollCancelTag.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblCancelTag.getRowCount())));

	}





}
