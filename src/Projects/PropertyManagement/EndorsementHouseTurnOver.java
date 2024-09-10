/**
 * 
 */
package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JXTextField;
import interfaces._GUI;
import net.sf.jasperreports.data.cache.TimestampValues;

/**
 * @author pc-114l 
 */

public class EndorsementHouseTurnOver extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 3530578596202440260L;
	static String title = "Endorsement for House Turn-Over";
	Dimension frameSize = new Dimension(800, 300);
	
	private JPanel pnlNorth;
	
	private JPanel pnlNorthWest;
	private JPanel pnlNWLabel;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblUnitDesc;
	private JLabel lblSeqNo;
	private JLabel lblPmtStatus;
	private JLabel lblLastPmtStatus;
	private JLabel lblDateIssued;
	private JLabel lblValidUntil;
	private JLabel lblPrintedBy;
	private JLabel lblReceivedBy;
	//private JLabel lblRemarks;
	
	private JPanel pnlNWComponent;
	private JPanel pnlClient;
	private _JLookup lookupClient;
	private _JXTextField txtClient;
	
	private JPanel pnlProject;
	private _JXTextField txtProjID;
	private _JXTextField txtProject;
	
	private JPanel pnlUnit;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDesc;
	private _JXTextField txtSeqNo;
	
	private _JXTextField txtPmtStatus;
	private _JDateChooser dteLastPmt;
	private _JDateChooser dteIssued;
	private _JDateChooser dteValidUntil;
	private _JXTextField txtPrintedBy;
	private _JXTextField txtReceivedBy;
	
	private JPanel pnlNorthEast;
	private JPanel pnlNELabel;
	private JLabel lblControlNo;
	private JLabel lblModelDesc;
	private JLabel lblInspectionDate;
	private JLabel lblInspectionTime;
	private JLabel lblTurnOverDate;
	private JLabel lblInspectionRemarks;
	
	private JPanel pnlNEComponent;
	private _JXTextField txtControlNo;
	private _JXTextField txtModelDesc;
	private _JDateChooser dteInspectionDate;
	private _JXTextField txtInspectionTime;
	private JSpinner spinnerCallStart;
	private JSpinner spinnerCallEnd;
	
	private _JDateChooser dteTurnOver;
	private JTextArea txtAreaInspectionRemarks;
	private _JXTextField txtInspectionRemarks;
	
	private JPanel pnlCenter;
	private JPanel pnlSouth;
	
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnSave;
	private JButton btnPreview;
	private JButton btnCancel;
	
	public EndorsementHouseTurnOver() {
		super(title, true, true, false, true);
		initGUI();
	}

	public EndorsementHouseTurnOver(String title) {
		super(title, true, true, false, true);
		initGUI();
	}

	public EndorsementHouseTurnOver(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, true, true, false, true);
		initGUI();
	}
	
	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		{
			JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setPreferredSize(frameSize);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new GridLayout(0, 2));
				pnlMain.add(pnlNorth, BorderLayout.CENTER);
				pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Client Details"));
				{
					pnlNorthWest = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthWest, BorderLayout.WEST);
					{
						pnlNWLabel = new JPanel(new GridLayout(7, 1));
						pnlNorthWest.add(pnlNWLabel, BorderLayout.WEST);
						{
							lblClient = new JLabel("Client's Name");
							pnlNWLabel.add(lblClient);
						}
						{
							lblProject = new JLabel("Project");
							pnlNWLabel.add(lblProject);
						}
						{
							lblUnitDesc = new JLabel("Phase/Block/Lot");
							pnlNWLabel.add(lblUnitDesc);
						}
						{
							lblSeqNo = new JLabel("Seq.");
							pnlNWLabel.add(lblSeqNo);
						}
						/*{
							lblPmtStatus = new JLabel("Payment Status");
							pnlNWLabel.add(lblPmtStatus);
						}
						{
							lblLastPmtStatus = new JLabel("Last Pmt made");
							pnlNWLabel.add(lblLastPmtStatus);
						}*/
						{
							lblDateIssued = new JLabel("Date of issue");
							pnlNWLabel.add(lblDateIssued);
						}
						{
							lblValidUntil = new JLabel("Valid until");
							pnlNWLabel.add(lblValidUntil);
						}
						/*{
							lblPrintedBy = new JLabel("Printed By");
							pnlNWLabel.add(lblPrintedBy);
						}*/
						{
							lblReceivedBy = new JLabel("Received by");
							pnlNWLabel.add(lblReceivedBy);
						}
					}
					{
						pnlNWComponent = new JPanel(new GridLayout(7, 1));
						pnlNorthWest.add(pnlNWComponent, BorderLayout.CENTER);
						{
							pnlClient = new JPanel(new BorderLayout(5, 5));
							pnlNWComponent.add(pnlClient);
							{
								lookupClient = new _JLookup(null, "Client", 0);
								pnlClient.add(lookupClient, BorderLayout.WEST);
								lookupClient.setLookupSQL(sqlClients());
								lookupClient.setPreferredSize(new Dimension(100, 0));
								lookupClient.setFilterName(true);
								lookupClient.addLookupListener(new LookupListener() {
									
									@Override
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										
										if(data != null){
											String entity_id = (String) data[0];
											String entity_name = (String) data[1];
											String unit_desc = (String) data[2];
											String unit_id = (String) data[3];
											String proj_id = (String) data[4];
											String proj_name = (String) data[5];
											Integer seq_no = (Integer) data[6];
											String pmt_status = (String) data[7];
											String model_desc = (String) data[8];
																				
											txtClient.setText(entity_name);
											txtUnitID.setText(unit_id);
											txtUnitDesc.setText(unit_desc);
											txtProjID.setText(proj_id);
											txtProject.setText(proj_name);
											txtSeqNo.setText(seq_no.toString());
											txtPmtStatus.setText(pmt_status);
											txtModelDesc.setText(model_desc);
											
											displayEndorsementDetails(entity_id, proj_id, unit_id, seq_no.toString());
											System.out.println("sql:" + sqlClients());
										}
									}
								});
							}
							{
								txtClient = new _JXTextField();
								pnlClient.add(txtClient, BorderLayout.CENTER);
							}
						}
						{
							pnlProject = new JPanel(new BorderLayout(5, 5));
							pnlNWComponent.add(pnlProject);
							{
								txtProjID = new _JXTextField();
								pnlProject.add(txtProjID, BorderLayout.WEST);
								txtProjID.setPreferredSize(new Dimension(50, 0));
							}
							{
								txtProject = new _JXTextField();
								pnlProject.add(txtProject, BorderLayout.CENTER);
							}
						}
						{
							pnlUnit = new JPanel(new BorderLayout(5, 5));
							pnlNWComponent.add(pnlUnit);
							{
								txtUnitID = new _JXTextField();
								pnlUnit.add(txtUnitID, BorderLayout.WEST);
								txtUnitID.setPreferredSize(new Dimension(100, 0));
							}
							{
								txtUnitDesc = new _JXTextField();
								pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
							}
						}
						{
							JPanel pnlSeqNo = new JPanel(new BorderLayout(3, 3));
							pnlNWComponent.add(pnlSeqNo);
							{
								txtSeqNo = new _JXTextField();
								pnlSeqNo.add(txtSeqNo, BorderLayout.WEST);
								txtSeqNo.setPreferredSize(new Dimension(50, 0));
							}
							{
								lblPmtStatus = new JLabel("Pmt Status", JLabel.TRAILING);
								pnlSeqNo.add(lblPmtStatus);
							}
							{
								txtPmtStatus = new _JXTextField();
								pnlSeqNo.add(txtPmtStatus, BorderLayout.EAST);
								txtPmtStatus.setPreferredSize(new Dimension(100, 0));
							}
						}
						/*{
							dteLastPmt = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlNWComponent.add(dteLastPmt);
							dteLastPmt.setPreferredSize(new Dimension(150, 0));
						}*/
						{
							dteIssued = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlNWComponent.add(dteIssued);
							dteIssued.setPreferredSize(new Dimension(150, 0));
						}
						{
							dteValidUntil = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlNWComponent.add(dteValidUntil);
							dteValidUntil.setPreferredSize(new Dimension(150, 0));
						}
						/*{
							txtPrintedBy = new _JXTextField();
							pnlNWComponent.add(txtPrintedBy);
						}*/
						{
							txtReceivedBy = new _JXTextField();
							pnlNWComponent.add(txtReceivedBy);
						}
					}
				}
				{
					pnlNorthEast = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
					{
						pnlNELabel = new JPanel(new GridLayout(8, 1));
						pnlNorthEast.add(pnlNELabel, BorderLayout.WEST);
						{
							lblControlNo = new JLabel("Control No", JLabel.TRAILING);
							pnlNELabel.add(lblControlNo);
						}
						
						{
							lblModelDesc = new JLabel("Model Description", JLabel.TRAILING);
							pnlNELabel.add(lblModelDesc);
						}
						
						{
							pnlNELabel.add(Box.createHorizontalBox());
							pnlNELabel.add(Box.createHorizontalBox());
						}
						{
							lblInspectionDate = new JLabel("Inspection Date", JLabel.TRAILING);
							pnlNELabel.add(lblInspectionDate);
						}
						{
							lblInspectionTime = new JLabel("Inspection Time", JLabel.TRAILING);
							pnlNELabel.add(lblInspectionTime);
						}
						{
							lblTurnOverDate = new JLabel("Turnover Date", JLabel.TRAILING);
							pnlNELabel.add(lblTurnOverDate);
						}
						{
							lblInspectionRemarks = new JLabel("Inspection Remarks", JLabel.TRAILING);
							pnlNELabel.add(lblInspectionRemarks);
						}
					}
					{
						pnlNEComponent = new JPanel(new GridLayout(8, 1));
						pnlNorthEast.add(pnlNEComponent, BorderLayout.CENTER);
						{
							txtControlNo = new _JXTextField();
							pnlNEComponent.add(txtControlNo);
						}
						
						{
							txtModelDesc = new _JXTextField();
							pnlNEComponent.add(txtModelDesc);
						}
						
						{
							pnlNEComponent.add(Box.createHorizontalBox());
							pnlNEComponent.add(Box.createHorizontalBox());
						}
						{
							dteInspectionDate = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlNEComponent.add(dteInspectionDate);
							dteInspectionDate.setPreferredSize(new Dimension(150, 0));
						}
						/*{
							txtInspectionTime = new _JXTextField();
							pnlNEComponent.add(txtInspectionTime);
							
						}*/
						{
							JPanel pnlInspectionTime = new JPanel(new BorderLayout(3, 3));
							pnlNEComponent.add(pnlInspectionTime);
							{
								SpinnerDateModel model = new SpinnerDateModel(new Date(),  null, null, Calendar.MINUTE);

								spinnerCallStart = new JSpinner(model);

								JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerCallStart,"hh:mm a");
								
								DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
								formatter.setAllowsInvalid(true); 
								formatter.setOverwriteMode(true);

								spinnerCallStart.setEditor(editor);

								pnlInspectionTime.add(spinnerCallStart,BorderLayout.WEST);
								spinnerCallStart.setPreferredSize(new Dimension(100,30 ));
							}
							{
								SpinnerDateModel model = new SpinnerDateModel(new Date(),  null, null, Calendar.MINUTE);

								spinnerCallEnd = new JSpinner(model);

								JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerCallEnd,"hh:mm a");
								DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
								formatter.setAllowsInvalid(true); 
								formatter.setOverwriteMode(true);

								spinnerCallEnd.setEditor(editor);

								pnlInspectionTime.add(spinnerCallEnd,BorderLayout.EAST); 
								spinnerCallEnd.setPreferredSize(new Dimension(100,30));
							}
						}
						{
							dteTurnOver = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlNEComponent.add(dteTurnOver);
							dteTurnOver.setPreferredSize(new Dimension(150, 0));
						}
						/*{
							txtAreaInspectionRemarks = new JTextArea();
							pnlNEComponent.add(txtAreaInspectionRemarks);
						}*/
						{
							txtInspectionRemarks = new _JXTextField();
							pnlNEComponent.add(txtInspectionRemarks);
						}
					}
				}
			}
			/*{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
			}*/
			{
				pnlSouth = new JPanel(new GridLayout(1, 6));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 50));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnNew = new JButton("New");
					pnlSouth.add(btnNew);
					btnNew.setActionCommand("New");
					btnNew.addActionListener(this);
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
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
				}
			}
		}
		btnState(false, false ,false, false, false);
		cancelEndorsement();
	}//XXX END OF INIT GUI
	
	private void btnState(Boolean sNew,Boolean sEdit ,Boolean sSave, Boolean sPreview, Boolean sCancel){
		btnNew.setEnabled(sNew);
		btnEdit.setEnabled(sEdit);
		btnSave.setEnabled(sSave);
		btnPreview.setEnabled(sPreview);
		btnCancel.setEnabled(sCancel);
	}
	
	private String sqlClients(){
		
		//Modified by Tim 2023-01-05
		return "SELECT a.entity_id as \"ID\", b.entity_name \"Name\", \n" + 
				"(CASE WHEN e.oth_pbl_id IS null then c.description else FORMAT('%s/%s', c.description, f.lot) end) as \"Unit\",\n" + 
				"c.unit_id as \"Unit ID\", a.projcode as \"Proj. ID\", d.proj_name as \"Proj. Name\", a.seq_no as \"Seq\",\n" + 
				"g.pmt_status as \"Pmt Status\",\n" + 
				"z.model_desc as \"Model Description\"\n" + 
				"from rf_sold_unit a\n" + 
				"left join rf_entity b on b.entity_id = a.entity_id \n" + 
				"LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id\n" + 
				"LEFT JOIN mf_project d on d.proj_id = a.projcode\n" + 
				"LEFT JOIN hs_sold_other_lots e on e.entity_id = a.entity_id and e.proj_id = a.projcode and e.pbl_id = a.pbl_id and e.seq_no = a.seq_no and e.status_id != 'X'\n" + 
				"LEFT JOIN mf_unit_info f on f.proj_id = a.projcode and f.pbl_id = e.oth_pbl_id\n" + 
				"LEFT JOIN rf_card_pmt_status g on g.entity_id = a.entity_id and g.proj_id = a.projcode and g.pbl_id = a.pbl_id and g.seq_no = a.seq_no\n" + 
				"left join mf_product_model z on c.model_id = z.model_id and coalesce(z.server_id,'') = coalesce(c.server_id,'') and coalesce(z.proj_server,'') = coalesce(c.proj_server,'')\n" + 
				"where a.currentstatus !=  '02'\n" + 
				"AND a.status_id != 'I'\n" + 
				"order by b.entity_name";
				/*"AND NOT EXISTS (SELECT *\n" + 
				"		FROM rf_endorsement_for_house_turnover\n" + 
				"		where entity_id = a.entity_id \n" + 
				"		and proj_id = a.projcode \n" + 
				"		and pbl_id = a.pbl_id \n" + 
				"		and seq_no = a.seq_no);\n";*/
	}
	
	private void displayEndorsementDetails(String entity_id, String proj_id, String unit_id, String seq_no){
		pgSelect db = new pgSelect();
		String SQL = "SELECT control_no, inspection_date, date_of_issue, \n"+
					 "split_part(inspection_time, ' - ', 1)::TIME WITHOUT TIME ZONE, \n"+
					 "split_part(inspection_time, ' - ', 2)::TIME WITHOUT TIME ZONE, \n"+
					 "valid_until, turnover_date, received_by, inspection_remarks \n"+
					 "FROM rf_endorsement_for_house_turnover \n"+
					 "WHERE entity_id = '"+entity_id+"' \n"+
					 "AND proj_id = '"+proj_id+"' \n"+
					 "AND pbl_id = getinteger('"+unit_id+"')::VARCHAR \n"+
					 "AND seq_no = "+seq_no+"";
		db.select(SQL);
		FncSystem.out("Display SQL for Endorsement", SQL);
		
		if(db.isNotNull()){
			for(int x= 0; x<db.getRowCount(); x++){
				String contro_no = (String) db.getResult()[0][0];
				Date inspection_date = (Date) db.getResult()[0][1];
				Date date_issue = (Date) db.getResult()[0][2];
				Time inspection_time_start = (Time) db.getResult()[0][3];
				Time inspection_time_end = (Time) db.getResult()[0][4];
				Date valid_until = (Date) db.getResult()[0][5];
				Date turnover_date = (Date) db.getResult()[0][6];
				String received_by = (String) db.getResult()[0][7];
				String inspection_remarks = (String) db.getResult()[0][8];
				
				txtControlNo.setText(contro_no);
				dteInspectionDate.setDate(inspection_date);
				dteIssued.setDate(date_issue);
				spinnerCallStart.setValue(inspection_time_start);
				spinnerCallEnd.setValue(inspection_time_end);
				dteValidUntil.setDate(valid_until);
				dteTurnOver.setDate(turnover_date);
				txtReceivedBy.setText(received_by);
				txtInspectionRemarks.setText(inspection_remarks);
			}
			btnState(false, true, false ,true, false);
		}else{
			txtControlNo.setText("");
			dteInspectionDate.setDate(null);
			dteIssued.setDate(null);
			spinnerCallStart.setValue(new Date());
			spinnerCallEnd.setValue(new Date());
			dteValidUntil.setDate(null);
			dteTurnOver.setDate(null);
			txtReceivedBy.setText("");
			txtInspectionRemarks.setText("");
			
			btnState(true, false, false ,false, false);
		}
	}
	
	private boolean toSave(){
		
		if(UserInfo.Division.equals("31")) {
			return true;
		}else {
			if(lookupClient.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Client first", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(dteIssued.getTimestamp() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input date issued", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(dteValidUntil.getTimestamp() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please input valid until", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		
		return true;
	}
	
	private void newEndorsement(String unit_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT FORMAT('%s-%s-%s-%s', b.proj_alias, a.phase ,(SELECT count(*) from mf_unit_info),(select TRIM(to_char(count(control_no)+1, '00000')) FROM rf_endorsement_for_house_turnover)), *\n" + 
					 "FROM mf_unit_info a\n" + 
					 "LEFT join mf_project b on b.proj_id = a.proj_id \n" + 
					 "WHERE a.unit_id = '"+unit_id+"';";
		db.select(SQL);
		String control_no = "";
		
		if(db.isNotNull()){
			control_no = (String) db.getResult()[0][0];
		}
		
		txtControlNo.setText(control_no);
		
		btnState(false, false, true ,false, true);
		if(UserInfo.Department.equals("02")){
			txtReceivedBy.setEditable(true);
		}else{
			dteIssued.getCalendarButton().setEnabled(true);
			dteIssued.setEditable(true);
			dteValidUntil.getCalendarButton().setEnabled(true);
			dteValidUntil.setEditable(true);
			dteInspectionDate.getCalendarButton().setEnabled(true);
			dteInspectionDate.setEditable(true);
			//txtInspectionTime.setEditable(true);
			spinnerCallStart.setEnabled(true);
			spinnerCallEnd.setEnabled(true);
			dteTurnOver.getCalendarButton().setEnabled(true);
			dteTurnOver.setEditable(true);
			//txtAreaInspectionRemarks.setEditable(true);
			txtInspectionRemarks.setEditable(true);
		}
	}
	
	private void editEndorsement(){
		
		if(UserInfo.Department.equals("02")){
			txtReceivedBy.setEditable(true);
		}else{
			lookupClient.setEditable(false);
			dteIssued.getCalendarButton().setEnabled(true);
			dteIssued.setEditable(true);
			dteValidUntil.getCalendarButton().setEnabled(true);
			dteValidUntil.setEditable(true);
			dteInspectionDate.getCalendarButton().setEnabled(true);
			dteInspectionDate.setEditable(true);
			//txtInspectionTime.setEditable(true);
			spinnerCallStart.setEnabled(true);
			spinnerCallEnd.setEnabled(true);
			dteTurnOver.getCalendarButton().setEnabled(true);
			dteTurnOver.setEditable(true);
			//txtAreaInspectionRemarks.setEditable(true);
			txtInspectionRemarks.setEditable(true);
		}
		
		btnState(false, false, true, false, true);
	}
	
	private void saveEndorsement(){
		
		String entity_id = lookupClient.getValue().trim();
		String proj_id = txtProjID.getText().trim();
		String unit_id = txtUnitID.getText().trim();
		String seq_no = txtSeqNo.getText().trim();	
		String control_no = txtControlNo.getText().trim();
		String pmt_status = txtPmtStatus.getText().trim();
	    //Date last_pmt_made = (Date) dteLastPmt.getDate();
		Date date_issued = (Date) dteIssued.getDate();
		Date validity = (Date) dteValidUntil.getDate();
		Date inspection_date = (Date) dteInspectionDate.getDate();
		Date turnover_date = (Date) dteTurnOver.getDate();
		//String inspection_remarks = txtAreaInspectionRemarks.getText().trim();
		String inspection_remarks = txtInspectionRemarks.getText().trim();
		//String inspection_time = txtInspectionTime.getText().trim();
		String inspection_time = String.format("%s - %s", new SimpleDateFormat("hh:mm aa").format(spinnerCallStart.getValue()), new SimpleDateFormat("hh:mm aa").format(spinnerCallEnd.getValue()));
		String received_by = txtReceivedBy.getText();
		
		//pgSelect db = new pgSelect();
		/*pgUpdate db = new pgUpdate();
		String SQL = "INSERT INTO rf_endorsement_for_house_turnover(rec_id, entity_id, proj_id, pbl_id, seq_no, control_no, pmt_status, \n" + 
					 "last_pmt_made, date_of_issue, valid_until, inspection_date, inspection_time, \n" + 
					 "turnover_date, printed_by, received_by, inspection_remarks, remarks, \n" + 
					 "created_by, date_created, edited_by, date_edited)\n" + 
					 "VALUES ((SELECT COALESCE(max(rec_id), 0) + 1 FROM rf_endorsement_for_house_turnover), '"+entity_id+"', '"+proj_id+"', \n"+
					 "getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '"+control_no+"', '"+pmt_status+"', \n" + 
					 "NULLIF('null', 'null')::TIMESTAMP WITHOUT TIME ZONE, NULLIF('"+date_issued+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, \n"+
					 "NULLIF('"+validity+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, NULLIF('"+inspection_date+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, \n"+
					 "'"+inspection_time+"', NULLIF('"+turnover_date+"', 'null')::TIMESTAMP WITHOUT TIME ZONE, '"+UserInfo.EmployeeCode+"', '"+received_by+"', '"+inspection_remarks+"', 'Sample remarks', \n" + 
					 "'"+UserInfo.EmployeeCode+"', now(), null, null);";
		db.executeUpdate(SQL, true);
		db.commit();*/
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_save_endorsement_for_house_turnover('"+entity_id+"', '"+proj_id+"',\n" + 
					 "getinteger('"+unit_id+"')::VARCHAR, "+seq_no+", '"+control_no+"', \n" + 
					 "'"+pmt_status+"', NULLIF('"+date_issued+"', 'null')::TIMESTAMP, \n" + 
					 "NULLIF('"+validity+"', 'null')::TIMESTAMP, NULLIF('"+inspection_date+"', 'null')::TIMESTAMP, \n" + 
					 "'"+inspection_time+"', NULLIF('"+turnover_date+"', 'null')::TIMESTAMP, '"+received_by+"', \n" + 
					 "'"+inspection_remarks+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		FncSystem.out("Saving of endorsement of turnover", SQL);
		
	}
	
	private void previewEndorsement(){
		
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("entity_id", lookupClient.getValue().trim());
		mapParameters.put("proj_id", txtProjID.getText().trim());
		mapParameters.put("unit_id", txtUnitID.getText().trim());
		mapParameters.put("seq_no", txtSeqNo.getText().trim());
		//mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenqlogo.png")); commented by jari cruz july 25, 2022 because only works on CDC 
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ getCompanyLogo())); // added by jari cruz july 25, 2022 
		
		FncReport.generateReport("/Reports/rptEndorsementForHouseTurnOver.jasper", "Endorsement for House Turn-Over", mapParameters);
	}
	
	private void previewTO_Acceptance() {
Map<String, Object> mapParameters = new HashMap<String, Object>();
		String model_desc = txtModelDesc.getText();

		
		mapParameters.put("entity_id", lookupClient.getValue().trim());
		mapParameters.put("proj_id", txtProjID.getText().trim());
		mapParameters.put("unit_id", txtUnitID.getText().trim());
		mapParameters.put("seq_no", txtSeqNo.getText().trim());
		mapParameters.put("model_desc", model_desc);
		/*if(txtProjID.getText().trim().equals("019")){
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "verdant_logo.bmp"));

		}
		if(txtProjID.getText().trim().equals("015") || txtProjID.getText().trim().equals("017") || txtProjID.getText().trim().equals("018")){
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq-new.png"));

		}
		if(txtProjID.getText().trim().equals("016") ||  txtProjID.getText().trim().equals("020")){
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "acer_logo.bmp"));

		}model_desc
		commented by jari cruz july 25, 2022 because only works on certain projects
		*/
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ getCompanyLogo())); //added by jari cruz july 25, 2022
		
		if(model_desc.toUpperCase().trim().equals("LOT ONLY")) {
		FncReport.generateReport("/Reports/rptProperty_TO_AcceptanceLOT.jasper", "Certification of Satisfactory Acceptance and Turnover", mapParameters);
		}else {
		FncReport.generateReport("/Reports/rptProperty_TO_Acceptance.jasper", "Property Turnover and Acceptance", mapParameters);
		}
		
	}
	
	private void cancelEndorsement(){
		
		lookupClient.setEditable(true);
		lookupClient.setValue(null);
		txtClient.setText("");
		txtProjID.setText("");
		txtProject.setText("");
		txtUnitID.setText("");
		txtUnitDesc.setText("");
		txtSeqNo.setText("");
		//dteLastPmt.setDate(null);
		dteIssued.setDate(null);
		dteValidUntil.setDate(null);
		txtControlNo.setText("");
		dteInspectionDate.setDate(null);
		dteTurnOver.setDate(null);
		//txtAreaInspectionRemarks.setText("");
		txtInspectionRemarks.setText("");
		txtReceivedBy.setText("");
		txtPmtStatus.setText("");
		//txtInspectionTime.setText("");
		
		dteIssued.getCalendarButton().setEnabled(false);
		dteIssued.setEditable(false);
		dteValidUntil.getCalendarButton().setEnabled(false);
		dteValidUntil.setEditable(false);
		dteInspectionDate.getCalendarButton().setEnabled(false);
		dteInspectionDate.setEditable(false);
		spinnerCallStart.setEnabled(false);
		spinnerCallEnd.setEnabled(false);
		
		dteTurnOver.getCalendarButton().setEnabled(false);
		dteTurnOver.setEditable(false);
		//txtAreaInspectionRemarks.setEditable(false);
		txtInspectionRemarks.setEditable(false);
		txtReceivedBy.setEditable(false);
		
	}
	
	public void actionPerformed(ActionEvent act) {
		String actionCommand = act.getActionCommand();
		
		if(actionCommand.equals("New")){
			newEndorsement(txtUnitID.getText());
		}
		
		if(actionCommand.equals("Edit")){
			editEndorsement();
		}
		
		if(actionCommand.equals("Save")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					saveEndorsement();
					previewEndorsement();
					previewTO_Acceptance();
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Save", JOptionPane.INFORMATION_MESSAGE);
					cancelEndorsement();
					btnState(false, false, false ,false, false);
				}
			}
		}
		
		if(actionCommand.equals("Preview")){
			previewEndorsement();
			previewTO_Acceptance();
		}
		
		if(actionCommand.equals("Cancel")){
			cancelEndorsement();
			btnState(false, false, false ,false, false);
		}
		
	}
	
	//added by ajri cruz july 25, 2022 for flexible company logo result based on project id
	private String getCompanyLogo() {
		pgSelect db = new pgSelect();
		String SQL = "select distinct on (a.co_id) company_logo from mf_company a\n" + 
				"left join mf_project b\n" + 
				"on a.co_id = b.co_id\n" + 
				"where b.proj_id = '"+txtProjID.getText().toString()+"'";
		
		System.out.println("Company Logo SQL");
		System.out.println(SQL);
		db.select(SQL);
		return (String) db.Result[0][0];
	}
}