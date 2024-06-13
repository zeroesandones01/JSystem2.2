package Buyers.LoansManagement;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.postgresql.util.PSQLException;

import Database.pgUpdate;
import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;

public class HouseAppraisal extends _JInternalFrame implements ActionListener,
		_GUI {

	private static final long serialVersionUID = -5066768211338962797L;
	private static String title = "House Appraisal";
	static Dimension SIZE = new Dimension(500, 260);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Border lineBorder_red = BorderFactory.createLineBorder(Color.RED);

	private JButton btnModify;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnPreview;
	
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblHouseModel;
	
	private _JLookup txtProjectID;
	private _JLookup txtPhaseID;
	private _JLookup txtHouseModelID;
	
	private JTextField txtProject;
	private JTextField txtPhase;
	private JTextField txtHouseModel;
	
	private _JDateChooser dteAsOfDate; 
	
	private _JXFormattedTextField txtHouseApp;
	
	public HouseAppraisal() {
		super(title, false, true, false, true);
		initGUI();
	}

	public HouseAppraisal(String title) {
		super(title);
		initGUI();
	}

	public HouseAppraisal(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		panMain.setPreferredSize(new Dimension(5, 5));
		{
			{
				JXPanel panCenter = new JXPanel(new GridLayout(3, 1, 5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				panCenter.setBorder(JTBorderFactory.createTitleBorder("Main Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JXPanel panProject = new JXPanel(new BorderLayout(5, 5));
						panCenter.add(panProject, BorderLayout.CENTER);
						{
							{
								JXPanel panProjectLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
								panProject.add(panProjectLabel, BorderLayout.LINE_START);
								panProjectLabel.setPreferredSize(new Dimension(175, 0));
								{
									{
										lblProject = new JLabel("Project");
										panProjectLabel.add(lblProject, BorderLayout.CENTER);
									}
									{
										txtProjectID = new _JLookup("");
										panProjectLabel.add(txtProjectID, BorderLayout.CENTER);
										txtProjectID.setReturnColumn(0);
										txtProjectID.setLookupSQL("select proj_id as id, proj_name as project, proj_alias as alias from mf_project order by project");
										txtProjectID.setHorizontalAlignment(JTextField.CENTER);
										txtProjectID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												
												if (data!=null) {
													txtProject.setText(data[1].toString());
													txtPhaseID.setLookupSQL("select distinct b.phase as id, 'Phase ' || b.phase as phase \n" +
															"from rf_sold_unit a \n" +
															"inner join mf_unit_info b on a.projcode = b.proj_id and a.pbl_id =  b.pbl_id \n" +
															"where a.projcode = '" + txtProjectID.getValue() + "' or '" + txtProjectID.getValue() + "' = ''");
//													txtHouseModelID.setLookupSQL("select distinct a.model_id as id, a.model_desc as model, a.model_alias as alias \n" +
//															"from mf_product_model a \n" +
//															"inner join rf_sold_unit b on a.model_id = b.model_id \n" +
//															"inner join mf_unit_info c on b.projcode = c.proj_id and b.pbl_id = c.pbl_id \n" + 
//															"where (b.projcode = '"+txtProjectID.getValue()+"' or '"+txtProjectID.getValue()+"' = '') \n" +
//															"and (c.phase = '"+txtPhaseID.getValue()+"' or '"+txtPhaseID.getValue()+"' = '') and a.status_id = 'A' \n" +
//															"order by a.model_desc");
//													txtHouseModelID.setLookupSQL("select distinct a.model_id as id, a.model_desc as model, a.model_alias as alias \n" + 
//															"from mf_product_model a \n" + 
//															"inner join mf_unit_info c on a.model_id = c.model_id  \n" + 
//															"where (c.proj_id = '"+txtProjectID.getValue()+"' or '"+txtProjectID.getValue()+"' = '')\n" + 
//															"and (c.phase = '"+txtPhaseID.getValue()+"' or '"+txtPhaseID.getValue()+"' = 'null') and a.status_id = 'A' \n" + 
//															"order by a.model_desc");
													if(data[0].toString().equals("018")) {
														txtHouseModelID.setLookupSQL("select model_id as id, model_desc as model, model_alias from mf_product_model where model_id in ('022','021')");
													}
													else {
														txtHouseModelID.setLookupSQL("select distinct a.model_id as id, a.model_desc as model, a.model_alias as alias \n" + 
																"from mf_product_model a \n" + 
																"inner join mf_unit_info c on a.model_id = c.model_id  \n" + 
																"where (c.proj_id = '"+txtProjectID.getValue()+"' or '"+txtProjectID.getValue()+"' = '')\n" + 
																"and (c.phase = '"+txtPhaseID.getValue()+"' or '"+txtPhaseID.getValue()+"' = 'null') and a.status_id = 'A' \n" + 
																"order by a.model_desc");
													}
												
													txtPhaseID.setValue("");
													txtPhase.setText("");
													txtHouseModelID.setValue("");
													txtHouseModel.setText("");
												}
												
												setHouseAppraisal(); 
											}
										});
									}
								}
							}
							{
								txtProject = new JTextField("");
								panProject.add(txtProject, BorderLayout.CENTER);
								txtProject.setEditable(false);
								txtProject.setHorizontalAlignment(JTextField.CENTER);
							}
						}
					}
					{
						JXPanel panPhase = new JXPanel(new BorderLayout(5, 5));
						panCenter.add(panPhase, BorderLayout.CENTER);
						{
							{
								JXPanel panPhaseLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
								panPhase.add(panPhaseLabel, BorderLayout.LINE_START);
								panPhaseLabel.setPreferredSize(new Dimension(175, 0));
								{
									{
										lblPhase = new JLabel("Phase");
										panPhaseLabel.add(lblPhase, BorderLayout.CENTER);
									}
									{
										txtPhaseID = new _JLookup("");
										panPhaseLabel.add(txtPhaseID, BorderLayout.CENTER);
										txtPhaseID.setReturnColumn(0);
										txtPhaseID.setLookupSQL("select distinct b.phase as id, 'Phase ' || b.phase as phase \n" +
												"from rf_sold_unit a \n" +
												"inner join mf_unit_info b on a.projcode = b.proj_id and a.pbl_id =  b.pbl_id \n" +
												"where a.projcode = '" + txtProjectID.getValue() + "' or '" + txtProjectID.getValue() + "' = ''");
										txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
										txtPhaseID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												
												if (data!=null) {
													txtPhase.setText(data[1].toString());
//													txtHouseModelID.setLookupSQL("select distinct a.model_id as id, a.model_desc as model, a.model_alias as alias \n" +
//															"from mf_product_model a \n" +
//															"inner join rf_sold_unit b on a.model_id = b.model_id \n" +
//															"inner join mf_unit_info c on b.projcode = c.proj_id and b.pbl_id = c.pbl_id \n" + 
//															"where (b.projcode = '"+txtProjectID.getValue()+"' or '"+txtProjectID.getValue()+"' = '') \n" +
//															"and (c.phase = '"+txtPhaseID.getValue()+"' or '"+txtPhaseID.getValue()+"' = '') and a.status_id = 'A' \n" +
//															"order by a.model_desc");
													txtHouseModelID.setLookupSQL("select distinct a.model_id as id, a.model_desc as model, a.model_alias as alias \n" + 
															"from mf_product_model a \n" + 
															"inner join mf_unit_info c on a.model_id = c.model_id  \n" + 
															"where (c.proj_id = '"+txtProjectID.getValue()+"' or '"+txtProjectID.getValue()+"' = '')\n" + 
															"and (c.phase = '"+txtPhaseID.getValue()+"' or '"+txtPhaseID.getValue()+"' = 'null') and a.status_id = 'A' \n" + 
															"order by a.model_desc");
													txtHouseModelID.setValue("");
													txtHouseModel.setText("");
												}
												
												setHouseAppraisal(); 
											}
										});
									}
								}
							}
							{
								txtPhase = new JTextField("");
								panPhase.add(txtPhase, BorderLayout.CENTER);
								txtPhase.setEditable(false);
								txtPhase.setHorizontalAlignment(JTextField.CENTER);
							}
						}
					}
					{
						JXPanel panHouseModel = new JXPanel(new BorderLayout(5, 5));
						panCenter.add(panHouseModel, BorderLayout.CENTER);
						{
							{
								JXPanel panHouseModelLabel = new JXPanel(new GridLayout(1, 2, 5, 5));
								panHouseModel.add(panHouseModelLabel, BorderLayout.LINE_START);
								panHouseModelLabel.setPreferredSize(new Dimension(175, 0));
								{
									{
										lblHouseModel = new JLabel("House Model");
										panHouseModelLabel.add(lblHouseModel, BorderLayout.CENTER);
									}
									{
										txtHouseModelID = new _JLookup("");
										panHouseModelLabel.add(txtHouseModelID, BorderLayout.CENTER);
										txtHouseModelID.setReturnColumn(0);
//										txtHouseModelID.setLookupSQL("select distinct a.model_id as id, a.model_desc as model, a.model_alias as alias \n" +
//												"from mf_product_model a \n" +
//												"inner join rf_sold_unit b on a.model_id = b.model_id \n" +
//												"inner join mf_unit_info c on b.projcode = c.proj_id and b.pbl_id = c.pbl_id \n" + 
//												"where (b.projcode = '"+txtProjectID.getValue()+"' or '"+txtProjectID.getValue()+"' = '') \n" +
//												"and (c.phase = '"+txtPhaseID.getValue()+"' or '"+txtPhaseID.getValue()+"' = '') and a.status_id = 'A' \n" +
//												"order by a.model_desc");
//										txtHouseModelID.setLookupSQL("select distinct a.model_id as id, a.model_desc as model, a.model_alias as alias \n" + 
//												"from mf_product_model a \n" + 
//												"inner join mf_unit_info c on a.model_id = c.model_id  \n" + 
//												"where (c.proj_id = '"+txtProjectID.getValue()+"' or '"+txtProjectID.getValue()+"' = '')\n" + 
//												"and (c.phase = '"+txtPhaseID.getValue()+"' or '"+txtPhaseID.getValue()+"' = 'null') and a.status_id = 'A' \n" + 
//												"order by a.model_desc");
										txtHouseModelID.setHorizontalAlignment(JTextField.CENTER);
										txtHouseModelID.addFocusListener(new FocusListener() {
											
											@Override
											public void focusLost(FocusEvent e) {
												// TODO Auto-generated method stub
												
											}
											
											@Override
											public void focusGained(FocusEvent e) {
											if(txtProjectID.getValue().equals("018")) {
												txtHouseModelID.setLookupSQL("select model_id as id, model_desc as model, model_alias from mf_product_model where model_id in ('022','021')");
											} 
											else {
												txtHouseModelID.setLookupSQL("select distinct a.model_id as id, a.model_desc as model, a.model_alias as alias \n" + 
														"from mf_product_model a \n" + 
														"inner join mf_unit_info c on a.model_id = c.model_id  \n" + 
														"where (c.proj_id = '"+txtProjectID.getValue()+"' or '"+txtProjectID.getValue()+"' = '')\n" + 
														"and (c.phase = '"+txtPhaseID.getValue()+"' or '"+txtPhaseID.getValue()+"' = 'null') and a.status_id = 'A' \n" + 
														"order by a.model_desc");
											}
											}
										});
										txtHouseModelID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent event) {
												Object[] data = ((_JLookup) event.getSource()).getDataSet();
												
												if (data!=null) {
													txtHouseModel.setText(data[1].toString());
													txtHouseApp.setText(FncGlobal.GetString("select fire_appraisal::varchar \n" + 
															"from mf_hdmf_appraisal \n" + 
															"where projcode = '"+txtProjectID.getValue()+"' and phase = '"+txtPhaseID.getValue()+"' and model_id = '"+txtHouseModelID.getValue()+"' \n" + 
															"order by date_created desc limit 1"));
													buttonstate(true);
												}
												
												setHouseAppraisal(); 
											}
										});
									}
								}
							}
							{
								txtHouseModel = new JTextField("");
								panHouseModel.add(txtHouseModel, BorderLayout.CENTER);
								txtHouseModel.setEditable(false);
								txtHouseModel.setHorizontalAlignment(JTextField.CENTER);
							}
						}
					}
				}
			}
			{
				JXPanel panEnd = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panEnd, BorderLayout.PAGE_END);
				panEnd.setPreferredSize(new Dimension(0, 93));
				{
					{
						JXPanel panEndCenter = new JXPanel(new GridLayout(1, 1, 5, 5));
						panEnd.add(panEndCenter, BorderLayout.PAGE_START); 
						panEndCenter.setPreferredSize(new Dimension(0, 63));
						{
							{
								JXPanel panAmt = new JXPanel(new BorderLayout(5, 5));
								panEndCenter.add(panAmt, BorderLayout.LINE_START);
								panAmt.setPreferredSize(new Dimension(200, 0));
								panAmt.setBorder(JTBorderFactory.createTitleBorder("House Appraisal", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									txtHouseApp = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
									panAmt.add(txtHouseApp, BorderLayout.CENTER);
									txtHouseApp.setFormatterFactory(_JXFormattedTextField.DECIMAL);
									txtHouseApp.setEditable(false);
									txtHouseApp.setHorizontalAlignment(JTextField.RIGHT);
								}
							}
							{
								JXPanel panDate = new JXPanel(new BorderLayout(5, 5));
								//panEndCenter.add(panDate, BorderLayout.LINE_START);
								panDate.setPreferredSize(new Dimension(200, 0));
								panDate.setBorder(JTBorderFactory.createTitleBorder("As Of Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
								{
									dteAsOfDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
									panDate.add(dteAsOfDate, BorderLayout.CENTER); 
									dteAsOfDate.getCalendarButton().setVisible(true);
									dteAsOfDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
									dteAsOfDate.setPreferredSize(new Dimension(0, 30));
								}
							}
						}
					}
					{
						JXPanel panEndEnd = new JXPanel(new GridLayout(1, 3, 5, 5)); 
						panEnd.add(panEndEnd, BorderLayout.PAGE_END); 
						panEndEnd.setPreferredSize(new Dimension(0, 30));
						{
							{
								btnModify = new JButton("Modify");
								panEndEnd.add(btnModify, BorderLayout.CENTER);
								btnModify.setActionCommand("Modify");
								btnModify.addActionListener(this);
								btnModify.setEnabled(false);
							}
							{
								btnSave = new JButton("Save");
								panEndEnd.add(btnSave, BorderLayout.CENTER);
								btnSave.setActionCommand("Save");
								btnSave.addActionListener(this);
								btnSave.setEnabled(false);
							}
							{
								btnCancel = new JButton("Cancel");
								panEndEnd.add(btnCancel, BorderLayout.CENTER);
								btnCancel.setActionCommand("Cancel");
								btnCancel.addActionListener(this);
								btnCancel.setEnabled(false);
							}
							{
								btnPreview = new JButton("Preview");
								panEndEnd.add(btnPreview, BorderLayout.CENTER);
								btnPreview.setActionCommand("Preview");
								btnPreview.addActionListener(this);
								btnPreview.setEnabled(true);
							}	
						}
					}
				}
			}	
		}
	}
	
	public void actionPerformed(ActionEvent act) {
		if (act.getActionCommand().equals("Modify")) {
			buttonstate(false);
		} else if (act.getActionCommand().equals("Save")) {
			if (txtHouseModelID.getValue().equals("") || txtHouseModelID.getValue()==null) {
				JOptionPane.showMessageDialog(getContentPane(), "Please select house model!");
			} else {
				String strSQL = ""; 
				BigDecimal bd = new BigDecimal(txtHouseApp.getText().replace(",", "")); 
				
				if (FncGlobal.GetBoolean("select exists(select * \n" +
						"from mf_hdmf_appraisal \n" +
						"where projcode = '"+txtProjectID.getValue()+"' and phase = '"+txtPhaseID.getValue()+"' \n" +
						"and model_id = '"+txtHouseModelID.getValue()+"')")) {

					strSQL = "update mf_hdmf_appraisal \n" + 
							"set fire_appraisal = '"+bd+"'::numeric(19, 2), date_modified = now(), modified_by = '"+UserInfo.EmployeeCode+"' \n" + 
							"where projcode = '"+txtProjectID.getValue()+"' and phase = '"+txtPhaseID.getValue()+"' \n" +
							"and model_id = '"+txtHouseModelID.getValue()+"'";

				} else {

					strSQL = "insert into mf_hdmf_appraisal (row_number, projcode, phase, model_id, \n" + 
							"fire_appraisal, date_created, created_by, date_modified, modified_by)\n" + 
							"values ((select max(x.row_number)+1 from mf_hdmf_appraisal x), '"+txtProjectID.getValue()+"', '"+txtPhaseID.getValue()+"', '"+txtHouseModelID.getValue()+"', \n" +
							"'"+bd+"'::numeric(19, 2), ('"+dteAsOfDate.getDate().toString()+"'::date || ' ' || now()::time::varchar)::timestamp, '"+UserInfo.EmployeeCode+"', null, null)";

				}
				
				pgUpdate dbExec = new pgUpdate();
				dbExec.executeUpdate(strSQL, true);
				dbExec.commit();
				
				setHouseAppraisal(); 
				
				JOptionPane.showMessageDialog(getContentPane(), "House appraisal amount successfully modified!", strSQL, JOptionPane.INFORMATION_MESSAGE);
				
				buttonstate(true);	
			}
		} else if (act.getActionCommand().equals("Preview")) {
			FncReport.generateReport("/Reports/rpt_fire_appraisal.jasper", "House Appraisal Report", "", null);
		} else {
			buttonstate(true);
		}
	}
	
	private void buttonstate(Boolean blnDo) {
		btnModify.setEnabled(blnDo);
		btnSave.setEnabled(!blnDo);
		btnCancel.setEnabled(!blnDo);
		txtHouseApp.setEditable(!blnDo);
	}
	
	private void setHouseAppraisal() {
		if (FncGlobal.GetBoolean("select exists(select * \n" +
				"from mf_hdmf_appraisal \n" +
				"where projcode = '"+txtProjectID.getValue()+"' and phase = '"+txtPhaseID.getValue()+"' \n" +
				"and model_id = '"+txtHouseModelID.getValue()+"')")) {
			txtHouseApp.setValue(new BigDecimal(FncGlobal.GetString("select fire_appraisal::varchar \n" + 
					"from mf_hdmf_appraisal \n" + 
					"where projcode = '"+txtProjectID.getValue()+"' and phase = '"+txtPhaseID.getValue()+"' and model_id = '"+txtHouseModelID.getValue()+"' \n" + 
					"order by date_created desc limit 1")));	
		} else {
			txtHouseApp.setValue(new BigDecimal("0.00")); 
		}
	}
}
