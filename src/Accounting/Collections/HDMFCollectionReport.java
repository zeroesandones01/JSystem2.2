package Accounting.Collections;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdesktop.swingx.JXPanel;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncExport;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
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
import tablemodel.modelPagibigStatusMonitoring_MSVSMonitoring;
import tablemodel.model_hdmf_collection;

public class HDMFCollectionReport extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = -5087395535078195597L;
	static String title = "PAGIBIG Collection Report";
	Dimension frameSize = new Dimension(700, 500);
	
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	
	private static _JLookup txtCoID;
	private static _JLookup txtProID;
	private static _JLookup txtPhaseID;
	private static _JLookup txtRPLF;
			
	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;
	
	private static _JDateChooser dteFrom;
	private static _JDateChooser dteTo;
	
	private JButton btnGen;
	private JButton btnPrev;
	private JButton btnPost;
	
	private static JCheckBox chkAll; 
	
	JXPanel panGrid = new JXPanel();

	private JScrollPane scrollHDMFCollection;
	public static JList rowHDMFCollection; 
	private _JTableMain tblHDMFCollection; 
	private static model_hdmf_collection modelHDMFCollection; 
	
	SimpleDateFormat dfFrom = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat dfTo = new SimpleDateFormat("MM/dd/yyyy");
	
	private JComboBox cboReport;
	
	public HDMFCollectionReport() {
		super(title, false, true, false, false);
		initGUI(); 
	}

	public HDMFCollectionReport(String title) {
		super(title);
		initGUI();
	}

	public HDMFCollectionReport(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setPreferredSize(frameSize);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panNorth = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panNorth, BorderLayout.PAGE_START);
			panNorth.setPreferredSize(new Dimension(0, 189));
			{
				JXPanel panNorth1 = new JXPanel(new BorderLayout(5, 5));
				panNorth.add(panNorth1, BorderLayout.PAGE_START);
				panNorth1.setPreferredSize(new Dimension(0, 124));
				{
					{
						JXPanel panComDetGen = new JXPanel(new BorderLayout(3, 3)); 
						panNorth1.add(panComDetGen, BorderLayout.CENTER); 
						panComDetGen.setBorder(JTBorderFactory.createTitleBorder("Project and Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));	
						{
							{
								JXPanel panLabel = new JXPanel(new GridLayout(3, 2, 5, 5));
								panComDetGen.add(panLabel, BorderLayout.LINE_START);
								panLabel.setPreferredSize(new Dimension(160, 90));
								{
									{
										lblCompany = new JLabel("Company");
										panLabel.add(lblCompany);
										lblCompany.setHorizontalAlignment(JTextField.LEFT);
									}
									{
										txtCoID = new _JLookup("");
										panLabel.add(txtCoID);
										txtCoID.setHorizontalAlignment(JTextField.CENTER);
										txtCoID.setLookupSQL(CompanySQL());
										txtCoID.setReturnColumn(0);
										txtCoID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent e) {
												Object[] data = ((_JLookup) e.getSource()).getDataSet();
												if (data != null) {
													txtCompany.setText(data[1].toString());
													txtProID.setLookupSQL(ProjectSQL(txtCoID.getValue()));
													
													btnPrev.setEnabled(true);
													btnPost.setEnabled(true);
												} else {
													btnPrev.setEnabled(false);
													btnPost.setEnabled(false);									
												}
											}
										});
										txtCoID.addKeyListener(new KeyListener() {
											public void keyTyped(KeyEvent e) {
												if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
													txtCoID.setValue("");
													txtCompany.setText("All Companies");
												}
											}
											
											public void keyReleased(KeyEvent e) {
												
											}
											
											public void keyPressed(KeyEvent e) {

											}
										});
									}
								}
								{
									{
										lblProject = new JLabel("Project");
										panLabel.add(lblProject);
										lblProject.setHorizontalAlignment(JTextField.LEFT);
									}
									{
										txtProID = new _JLookup("");
										panLabel.add(txtProID);
										txtProID.setHorizontalAlignment(JTextField.CENTER);
										txtProID.setLookupSQL(ProjectSQL(""));
										txtProID.setReturnColumn(0);
										txtProID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent e) {
												Object[] data = ((_JLookup) e.getSource()).getDataSet();
												if (data != null) {
													txtProject.setText(data[2].toString());
													txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
												}
											}
										});
										txtProID.addKeyListener(new KeyListener() {
											public void keyTyped(KeyEvent e) {
												if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
													txtProID.setValue("");
													txtProject.setText("All Projects");
												}
											}

											public void keyReleased(KeyEvent e) {
																					
											}
											
											public void keyPressed(KeyEvent e) {
																					
											}
										});
									}
								}
								{
									{
										lblPhase = new JLabel("Phase");
										panLabel.add(lblPhase);
										lblPhase.setHorizontalAlignment(JTextField.LEFT);
									}
									{
										txtPhaseID = new _JLookup("");
										panLabel.add(txtPhaseID);
										txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
										txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
										txtPhaseID.setReturnColumn(0);
										txtPhaseID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent e) {
												Object[] data = ((_JLookup) e.getSource()).getDataSet();
												if (data != null) {
													txtPhase.setText(data[1].toString());
												}
											}
										});
										txtPhaseID.addKeyListener(new KeyListener() {
											public void keyTyped(KeyEvent e) {
												if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
													txtPhaseID.setValue("");
													txtPhase.setText("All Phase");
												}
											}
											
											public void keyReleased(KeyEvent e) {
												
											}
											
											public void keyPressed(KeyEvent e) {
												
											}
										});
									}
								}
							}
							{
								JXPanel panBox = new JXPanel(new GridLayout(3, 1, 5, 5));
								panComDetGen.add(panBox, BorderLayout.CENTER);
								{
									txtCompany = new JTextField("");
									panBox.add(txtCompany, BorderLayout.CENTER);
									txtCompany.setHorizontalAlignment(JTextField.CENTER);
								}
								{
									txtProject = new JTextField("");
									panBox.add(txtProject, BorderLayout.CENTER);
									txtProject.setHorizontalAlignment(JTextField.CENTER);
								}
								{
									txtPhase = new JTextField("");
									panBox.add(txtPhase, BorderLayout.CENTER);
									txtPhase.setHorizontalAlignment(JTextField.CENTER);
								}
							}
							{
								JXPanel panLabel = new JXPanel(new GridLayout(3, 2, 5, 5));
								panComDetGen.add(panLabel, BorderLayout.LINE_START);
								panLabel.setPreferredSize(new Dimension(160, 90));
								{
									{
										lblCompany = new JLabel("Company");
										panLabel.add(lblCompany);
										lblCompany.setHorizontalAlignment(JTextField.LEFT);
									}
									{
										txtCoID = new _JLookup("");
										panLabel.add(txtCoID);
										txtCoID.setHorizontalAlignment(JTextField.CENTER);
										txtCoID.setLookupSQL(CompanySQL());
										txtCoID.setReturnColumn(0);
										txtCoID.addLookupListener(new LookupListener() {
											public void lookupPerformed(LookupEvent e) {
												Object[] data = ((_JLookup) e.getSource()).getDataSet();
												if (data != null) {
													txtCompany.setText(data[1].toString());
													txtProID.setLookupSQL(ProjectSQL(txtCoID.getValue()));
													
													btnPrev.setEnabled(true);
													btnPost.setEnabled(true);
												} else {
													btnPrev.setEnabled(false);
													btnPost.setEnabled(false);									
												}
											}
										});
										txtCoID.addKeyListener(new KeyListener() {
											public void keyTyped(KeyEvent e) {
												if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
													txtCoID.setValue("");
													txtCompany.setText("All Companies");
												}
											}
											
											public void keyReleased(KeyEvent e) {
												
											}
											
											public void keyPressed(KeyEvent e) {

											}
										});
									}
									{
										{
											lblProject = new JLabel("Project");
											panLabel.add(lblProject);
											lblProject.setHorizontalAlignment(JTextField.LEFT);
										}
										{
											txtProID = new _JLookup("");
											panLabel.add(txtProID);
											txtProID.setHorizontalAlignment(JTextField.CENTER);
											txtProID.setLookupSQL(ProjectSQL(""));
											txtProID.setReturnColumn(0);
											txtProID.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent e) {
													Object[] data = ((_JLookup) e.getSource()).getDataSet();
													if (data != null) {
														txtProject.setText(data[2].toString());
														txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
													}
												}
											});
											txtProID.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
														txtProID.setValue("");
														txtProject.setText("All Projects");
													}
												}

												public void keyReleased(KeyEvent e) {
																						
												}
												
												public void keyPressed(KeyEvent e) {
																						
												}
											});
										}
									}
									{
										{
											lblPhase = new JLabel("Phase");
											panLabel.add(lblPhase);
											lblPhase.setHorizontalAlignment(JTextField.LEFT);
										}
										{
											txtPhaseID = new _JLookup("");
											panLabel.add(txtPhaseID);
											txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
											txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
											txtPhaseID.setReturnColumn(0);
											txtPhaseID.addLookupListener(new LookupListener() {
												public void lookupPerformed(LookupEvent e) {
													Object[] data = ((_JLookup) e.getSource()).getDataSet();
													if (data != null) {
														txtPhase.setText(data[1].toString());
													}
												}
											});
											txtPhaseID.addKeyListener(new KeyListener() {
												public void keyTyped(KeyEvent e) {
													if (e.getKeyChar()==KeyEvent.VK_BACK_SPACE) {
														txtPhaseID.setValue("");
														txtPhase.setText("All Phase");
													}
												}
												
												public void keyReleased(KeyEvent e) {
													
												}
												
												public void keyPressed(KeyEvent e) {
													
												}
											});
										}
									}
								}
							}
							{
								JXPanel panBox = new JXPanel(new GridLayout(3, 1, 5, 5));
								panComDetGen.add(panBox, BorderLayout.CENTER);
								{
									txtCompany = new JTextField("");
									panBox.add(txtCompany, BorderLayout.CENTER);
									txtCompany.setHorizontalAlignment(JTextField.CENTER);
								}
								{
									txtProject = new JTextField("");
									panBox.add(txtProject, BorderLayout.CENTER);
									txtProject.setHorizontalAlignment(JTextField.CENTER);
								}
								{
									txtPhase = new JTextField("");
									panBox.add(txtPhase, BorderLayout.CENTER);
									txtPhase.setHorizontalAlignment(JTextField.CENTER);
								}
							}	
						}	
					}
					{
						btnGen = new JButton("Generate"); 
						panNorth1.add(btnGen, BorderLayout.LINE_END); 
						btnGen.setPreferredSize(new Dimension(150, 0));
						btnGen.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								txtRPLF.setValue("");
								if (displayCollection(modelHDMFCollection, rowHDMFCollection, "")) {
									bState(true, true, true);
								} else {
									bState(true, false, false);
								}
							}
						});
					}
				}
				{
					JXPanel panNorth2 = new JXPanel(new BorderLayout(5, 5));
					panNorth.add(panNorth2, BorderLayout.CENTER);
					{
						{
							JXPanel panReport = new JXPanel(new BorderLayout(5, 5));
							panNorth2.add(panReport, BorderLayout.LINE_START);
							panReport.setPreferredSize(new Dimension(200, 0));
							panReport.setBorder(JTBorderFactory.createTitleBorder("Report Option", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								{
									chkAll = new JCheckBox("All PIMA Payment"); 
									panReport.add(chkAll, BorderLayout.CENTER);
									chkAll.setHorizontalAlignment(JTextField.LEADING);
									chkAll.addItemListener(new ItemListener() {
										public void itemStateChanged(ItemEvent e) {
											txtRPLF.setValue("");
											if (chkAll.isSelected()) {
												displayCollection(modelHDMFCollection, rowHDMFCollection, "");
												bState(false, true, false);	 
											} else {
												FncTables.clearTable(modelHDMFCollection);
												rowHDMFCollection.setModel(new DefaultListModel()); 
												bState(true, false, false);
											}
										}
									});									
								}
								{
									
									String[] arr = {"All HDMF Payments", 
											"HDMF Collection Report", 
											}; 
									cboReport = new JComboBox(arr); 
									//panReport.add(cboReport, BorderLayout.CENTER);
									cboReport.setEnabled(false);
								}
							}
						}
						{
							JXPanel panDate = new JXPanel(new GridLayout(1, 2, 3, 3));
							panNorth2.add(panDate, BorderLayout.CENTER);
							panDate.setBorder(JTBorderFactory.createTitleBorder("Collection Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
							{
								{
									JXPanel panFrom = new JXPanel(new BorderLayout(3, 3)); 
									panDate.add(panFrom, BorderLayout.CENTER);
									{
										{
											JLabel lblFrom = new JLabel("From"); 
											panFrom.add(lblFrom, BorderLayout.LINE_START); 
											lblFrom.setPreferredSize(new Dimension(40, 0));
										}
										{
											dteFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
											panFrom.add(dteFrom, BorderLayout.CENTER);
											dteFrom.setDate(null);
											dteFrom.setEnabled(true);
											dteFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
											
											dteFrom.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
												public void propertyChange(PropertyChangeEvent evt) {
													if (chkAll.isSelected()) {
														displayCollection(modelHDMFCollection, rowHDMFCollection, "");
													}
												}
											});
										}
									}
								}
								{
									JXPanel panTo = new JXPanel(new BorderLayout(3, 3)); 
									panDate.add(panTo, BorderLayout.CENTER);
									{
										{
											JLabel lblFrom = new JLabel("To"); 
											panTo.add(lblFrom, BorderLayout.LINE_START); 
											lblFrom.setPreferredSize(new Dimension(40, 0));
											lblFrom.setHorizontalAlignment(JLabel.CENTER);
										}
										{
											dteTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
											panTo.add(dteTo, BorderLayout.CENTER);
											dteTo.setDate(null);
											dteTo.setEnabled(true);
											dteTo.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
											dteTo.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
												public void propertyChange(PropertyChangeEvent evt) {
													if (chkAll.isSelected()) {
														displayCollection(modelHDMFCollection, rowHDMFCollection, "");
													}
												}
											});
										}
									}

								}	
							}
						}
					}
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					CreateGrid(); 
					panCenter.add(panGrid); 
				}
			}
			{
				JXPanel panEnd = new JXPanel(new GridLayout(1, 3, 3, 3)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					{
						btnPrev = new JButton("Preview");
						panEnd.add(btnPrev, BorderLayout.CENTER);
						btnPrev.setEnabled(false);
						btnPrev.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								pgUpdate dbExec = new pgUpdate();
								dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'; ", false);
								dbExec.commit();
								
								dbExec = new pgUpdate();
								for(int x = 0; x < modelHDMFCollection.getRowCount(); x++) {
									if ((Boolean) modelHDMFCollection.getValueAt(x, 0)) {
										dbExec.executeUpdate("insert into tmp_hdmf values ('"+modelHDMFCollection.getValueAt(x, 2)+"', '"+UserInfo.EmployeeCode+"')", true);	
									}
								}
								dbExec.commit();
								
								if (txtRPLF.getValue()==null || txtRPLF.getValue()=="null" || txtRPLF.getValue()=="" || chkAll.isSelected()) {
									GenerateReport1();
								} else {
									try {
										createFile();
									} catch (DBFException e1) {
										e1.printStackTrace();
									} 
									
									GenerateReport1();
									GenerateReport2();
								}	 
							}
						});
					}
					{
						btnPost = new JButton("Post");
						panEnd.add(btnPost, BorderLayout.CENTER);
						btnPost.setEnabled(true);
						btnPost.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (JOptionPane.showConfirmDialog(getContentPane(), "Post selected transactions?", "Post", JOptionPane.YES_NO_OPTION)==
										JOptionPane.YES_OPTION) {
									pgUpdate dbExec = new pgUpdate();
									dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'; ", false);
									dbExec.commit();
									
									dbExec = new pgUpdate();
									for(int x = 0; x < modelHDMFCollection.getRowCount(); x++) {
										if ((Boolean) modelHDMFCollection.getValueAt(x, 0)) {
											dbExec.executeUpdate("insert into tmp_hdmf values ('"+modelHDMFCollection.getValueAt(x, 2)+"', '"+UserInfo.EmployeeCode+"')", true);	
										}
									}
									
									dbExec.commit();
									
									if (Post()) {
										JOptionPane.showMessageDialog(getContentPane(), "The payments were successfully posted!");
									} else {
										JOptionPane.showMessageDialog(getContentPane(), "Something went wrong. Check the details and values.");
									}
								} 
							}
						});
					}
					{
						JXPanel panRPLF = new JXPanel(new BorderLayout(3, 3));
						panEnd.add(panRPLF, BorderLayout.CENTER);
						{
							{
								JLabel lblRPLF = new JLabel("RPLF No."); 
								panRPLF.add(lblRPLF, BorderLayout.LINE_START); 
								lblRPLF.setHorizontalAlignment(JLabel.CENTER);
								lblRPLF.setPreferredSize(new Dimension(75, 0));
							}
							{
								txtRPLF = new _JLookup("");
								panRPLF.add(txtRPLF, BorderLayout.CENTER); 
								txtRPLF.setReturnColumn(0);
								txtRPLF.setLookupSQL(RPLFSQL());
								txtRPLF.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										dteFrom.setDate(FncGlobal.GetDate("select min(trans_date) from rf_hdmf_payments where rplf_no = '"+txtRPLF.getValue().toString()+"'"));
										dteTo.setDate(FncGlobal.GetDate("select max(trans_date) from rf_hdmf_payments where rplf_no = '"+txtRPLF.getValue().toString()+"'"));
										
										displayCollection(modelHDMFCollection, rowHDMFCollection, txtRPLF.getValue().toString()); 
										bState(true, true, false);
									}
								});
							}
						}	
					}
				}
			}
		}
		Setdefaults();
		bState(true, false, false);
	}

	private String sqlCompanyLogo(String co_id){
		String SQL = "SELECT company_logo FROM mf_company WHERE co_id = '"+co_id+"'";
		pgSelect db = new pgSelect();
		db.select(SQL);
		
		if(db.isNotNull()){
			return (String) db.getResult()[0][0];
		}else{
			return "";
		}
	}
	
	private static String CompanySQL() {
		return "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
			   "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
	}
	
	public static String ProjectSQL(String strCo){
		return "SELECT proj_id, proj_alias, proj_name\n" +
			   "FROM mf_project\n" +
			   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"')\n" +
			   "ORDER BY proj_name";
	}
	
	public static String RPLFSQL(){
		return "select a.rplf_no, a.date_remitted::date, sum(a.amount) as amount, b.branch_alias \n" + 
				"from rf_hdmf_payments a \n" + 
				"inner join mf_office_branch b on a.branch_id = b.branch_id \n" +
				"where coalesce(a.rplf_no, '') != '' \n" + 
				"group by a.rplf_no, a.date_remitted::date, b.branch_alias \n" + 
				"order by a.date_remitted::date desc, a.rplf_no; ";
	}
	
	private void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;

		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtCoID.setText("");
			txtCompany.setText("All Companies");
		} else {
			txtCoID.setValue(_RealTimeDebit.GetValue("SELECT co_id FROM mf_company LIMIT 1"));
			txtCompany.setText(_RealTimeDebit.GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getText()+"'"));
		}

		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtProID.setText("");
			txtProject.setText("All Projects");
		} else {
			txtProID.setValue(_RealTimeDebit.GetValue("SELECT proj_id FROM mf_project LIMIT 1"));
			txtProject.setText(_RealTimeDebit.GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProID.getText()+"'"));
		}
		
		txtCoID.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		
		txtProID.setValue("015");
		txtProject.setText("TERRAVERDE RESIDENCES");
		
		txtPhaseID.setValue("");
		txtPhase.setText("");
	}
	
	private String sqlPhase(String strProject) {
		return "select distinct x.phase, 'PHASE ' || phase \n" +
			"from mf_unit_info x \n" +
			"inner join mf_project y on x.proj_id = y.proj_id \n" +
			"where (x.proj_id = '"+strProject+"' or '"+strProject+"' = '') \n" +
			"order by x.phase";
	}
	
	private void CreateGrid() {
		panGrid = new JXPanel(new GridLayout(1, 1, 0, 0));
		panGrid.setOpaque(isOpaque());
		{
			scrollHDMFCollection = new JScrollPane();
			panGrid.add(scrollHDMFCollection, BorderLayout.CENTER);
			{
				modelHDMFCollection = new model_hdmf_collection();
				tblHDMFCollection = new _JTableMain(modelHDMFCollection);
				scrollHDMFCollection.setViewportView(tblHDMFCollection);
				scrollHDMFCollection.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				
				tblHDMFCollection.getColumnModel().getColumn(0).setPreferredWidth(20);
				tblHDMFCollection.getColumnModel().getColumn(1).setPreferredWidth(100);
				tblHDMFCollection.getColumnModel().getColumn(2).setPreferredWidth(210);
				tblHDMFCollection.getColumnModel().getColumn(3).setPreferredWidth(100);
				tblHDMFCollection.getColumnModel().getColumn(4).setPreferredWidth(90);
				tblHDMFCollection.getColumnModel().getColumn(5).setPreferredWidth(90);
				tblHDMFCollection.getColumnModel().getColumn(6).setPreferredWidth(100);
				
				tblHDMFCollection.hideColumns("entity_id", "proj_id", "pbl_id", "seq_no", "hlid_no", "client_seqno");
			}
			{
				rowHDMFCollection = tblHDMFCollection.getRowHeader();
				rowHDMFCollection.setModel(new DefaultListModel());
				scrollHDMFCollection.setRowHeaderView(rowHDMFCollection);
				scrollHDMFCollection.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
			}
		}
	}
	
	public static Boolean displayCollection(DefaultTableModel modelMain, JList rowHeader, String strRPLF) {
		Boolean blnRev = false;
		FncTables.clearTable(modelMain);
		DefaultListModel listModel = new DefaultListModel();
		rowHeader.setModel(listModel); 
		
		System.out.println("");
		System.out.println("strRPLF: " + strRPLF); 
		
		String SQL = "";
		
		if (chkAll.isSelected()) {
			SQL = "select * \n" +
				"from view_hdmf_collection('"+txtCoID.getValue()+"', '"+txtProID.getValue()+"', '"+txtPhaseID.getValue()+"', '"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"') \n" +
				"order by c_entity_name; ";			
		} else {
			SQL = "select * \n" +
				"from view_hdmf_collection('"+txtCoID.getValue()+"', '"+txtProID.getValue()+"', '"+txtPhaseID.getValue()+"', '"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"', '"+strRPLF+"') \n" +
				"order by c_entity_name; ";
		}

		System.out.println(SQL);

		pgSelect db = new pgSelect();
		db.select(SQL);
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}
			blnRev = true;
		} else {
			JOptionPane.showMessageDialog(null, "No records were returned for posting.");
			blnRev = false;
		};
		
		return blnRev;
	};
	
	private void bState(Boolean blnGen, Boolean blnPreview, Boolean blnPost) {
		btnGen.setEnabled(blnGen);
		btnPrev.setEnabled(blnPreview);
		btnPost.setEnabled(blnPost);
	}
	
	private void GenerateReport1() {
		SimpleDateFormat dfFrom = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dfTo = new SimpleDateFormat("MM/dd/yyyy");
		
		String strDate = "";
		String strFrom = dfFrom.format(dteFrom.getDate()); 
		String strTo = dfTo.format(dteTo.getDate()); 
		
		if (strFrom.equals(strTo)) {
			strDate = strFrom;
		} else {
			strDate = strFrom + " to " + strTo;
		}

		String strCoID = "";
		String strProID = "";
		String strPhaseID = "";
		String strRPLF = "";
		
		if (txtCompany.getText()=="All Companies") {
			strCoID = "";
		} else {
			strCoID = txtCoID.getValue();
		}
		
		if (txtProject.getText()=="All Projects") {
			strProID = ""; 
		} else {
			strProID = txtProID.getValue(); 
		}
		
		if (txtPhase.getText()=="All Phase") {
			strPhaseID = "";
		} else {
			strPhaseID = txtPhaseID.getValue(); 
		}

		if (txtRPLF.getValue()==null || txtRPLF.getValue()=="null" || txtRPLF.getValue()=="") {
			strRPLF = "";
		} else {
			strRPLF = txtRPLF.getValue(); 
		}
		
		String company_logo = sqlCompanyLogo(txtCoID.getValue().toString());
		Map<String, Object> mapParameters1 = new HashMap<String, Object>();
		mapParameters1.put("01_Company", txtCompany.getText());
		mapParameters1.put("02_AsOfDate", strDate);
		mapParameters1.put("03_CoID", strCoID);
		mapParameters1.put("04_ProID", strProID);
		mapParameters1.put("06_Project", txtProject.getText());
		mapParameters1.put("07_User", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters1.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters1.put("09_dteFrom", dteFrom.getDate().toString());
		mapParameters1.put("10_dteTo", dteTo.getDate().toString());
		mapParameters1.put("11_Phase", strPhaseID);
		mapParameters1.put("12_RPLF", strRPLF); 
		
		if (chkAll.isSelected()) {
			FncReport.generateReport("/Reports/rpt_hdmf_collection_all.jasper", "HDMF Collection Report", "", mapParameters1);
		} else {
			FncReport.generateReport("/Reports/rpt_hdmf_collection.jasper", "HDMF Collection Report", "", mapParameters1);
		}
		
	}
	
	private void GenerateReport2() {
		SimpleDateFormat dfFrom = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dfTo = new SimpleDateFormat("MM/dd/yyyy");
		
		String strDate = "";
		String strFrom = dfFrom.format(dteFrom.getDate()); 
		String strTo = dfTo.format(dteTo.getDate()); 
		
		if (strFrom.equals(strTo)) {
			strDate = strFrom;
		} else {
			strDate = strFrom + " to " + strTo;
		}

		String strCoID = "";
		String strProID = "";
		String strPhaseID = "";
		String strRPLF = "";
		
		if (txtCompany.getText()=="All Companies") {
			strCoID = "";
		} else {
			strCoID = txtCoID.getValue();
		}
		
		if (txtProject.getText()=="All Projects") {
			strProID = ""; 
		} else {
			strProID = txtProID.getValue(); 
		}
		
		if (txtPhase.getText()=="All Phase") {
			strPhaseID = "";
		} else {
			strPhaseID = txtPhaseID.getValue(); 
		}

		if (txtRPLF.getValue()==null || txtRPLF.getValue()=="null" || txtRPLF.getValue()=="") {
			strRPLF = "";
		} else {
			strRPLF = txtRPLF.getValue(); 
		}
		
		String company_logo = sqlCompanyLogo(txtCoID.getValue().toString());
		Map<String, Object> mapParameters1 = new HashMap<String, Object>();
		mapParameters1.put("01_Company", txtCompany.getText());
		mapParameters1.put("02_AsOfDate", strDate);
		mapParameters1.put("03_CoID", strCoID);
		mapParameters1.put("04_ProID", strProID);
		mapParameters1.put("06_Project", txtProject.getText());
		mapParameters1.put("07_User", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters1.put("08_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters1.put("09_dteFrom", dteFrom.getDate().toString());
		mapParameters1.put("10_dteTo", dteTo.getDate().toString());
		mapParameters1.put("11_Phase", strPhaseID);
		mapParameters1.put("12_RPLF", strRPLF); 
		FncReport.generateReport("/Reports/rpt_hdmf_ma_payments.jasper", "HDMF MA Payments", "", mapParameters1);
	}
	
	private Boolean Post() {
		Boolean blnSucceed = false;
		
		if (CreateRPLF()) {
			displayCollection(modelHDMFCollection, rowHDMFCollection, txtRPLF.getValue().toString()); 
			bState(true, true, false);
			blnSucceed = true;
		} else {
			bState(true, true, true);
		}
		
		pgUpdate dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'; ", false);
		dbExec.commit();
		
		return blnSucceed; 
	}
	
	private Boolean CreateRPLF() {
		Boolean retValue = false; 
		
		retValue = true; 
		pgUpdate dbExec = new pgUpdate(); 
		String strFrom = dfFrom.format(dteFrom.getDate()); 
		String strTo = dfTo.format(dteTo.getDate()); 

		String strRPLF = FncGlobal.GetString("select trim(to_char(max(coalesce(rplf_no::int,0))+1,'000000000')) from rf_request_header where co_id = '"+txtCoID.getValue()+"'"); 
		String strRemarks = "MA Remittance for the period "+strFrom+" to "+strTo+"; ";
		
		String strExec = "INSERT INTO rf_request_header VALUES ("
				+ "'"+txtCoID.getValue()+"', '"+txtCoID.getValue()+"', '"+strRPLF+"', now(), now(), NULL, '01', "
				+ "'5153446583', '5153446583', '13', NULL, '09', 1, NULL, NULL, "
				+ "'', 'A', '"+UserInfo.EmployeeCode+"', now(), NULL, NULL, 'B')"; 

		System.out.println(""); 
		System.out.println(strExec); 
		dbExec.executeUpdate(strExec, false);
		dbExec.commit();

		strExec = "INSERT INTO rf_request_detail "
				+ "SELECT '"+txtCoID.getValue()+"', '"+txtCoID.getValue()+"', '"+strRPLF+"', ROW_NUMBER() OVER(order by a.c_proj_id, c.sub_proj_id), NULL, NULL, now(), FALSE, '"+strRemarks+"', '03-02-10-000', \n" 
				+ "'', sum(a.c_amount)::NUMERIC(19, 2) as amount, '5153446583', '13', a.c_proj_id, c.sub_proj_id, NULL, NULL, NULL, NULL, NULL, false, false, false, false, \n" 
				+ "'18', 0::NUMERIC(19, 2), 0::NUMERIC(19, 2), NULL, 0::NUMERIC(19, 2), 0::NUMERIC(19, 2), sum(a.c_amount)::NUMERIC(19, 2) as amount, sum(a.c_amount)::NUMERIC(19, 2) as amount, \n" 
				+ "NULL, NULL, NULL, NULL, 'A', '900748', now(), NULL, NULL, NULL, NULL, NULL, NULL \n" 
				+ "FROM view_hdmf_collection('"+txtCoID.getValue()+"', '"+txtProID.getValue()+"', '"+txtPhaseID.getValue()+"', '"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"', '') a \n"
				+ "INNER JOIN mf_unit_info b ON a.c_proj_id = b.proj_id AND a.c_pbl_id = b.pbl_id \n"
				+ "INNER JOIN mf_sub_project c ON a.c_proj_id = c.proj_id AND b.phase = c.phase AND c.status_id = 'A' \n"  
				+ "WHERE a.c_entity_name IN (SELECT x.client_name FROM tmp_hdmf x WHERE x.emp_code = '"+UserInfo.EmployeeCode+"') \n"  
				+ "AND NOT EXISTS(select * from rf_hdmf_payments x where x.client_seqno = a.c_client_seqno and x.remarks = 'Payment from MBTC' and x.status_id = 'A') \n"
				+ "GROUP BY a.c_proj_id, c.sub_proj_id \n" 
				+ "ORDER BY a.c_proj_id, c.sub_proj_id; ";

		System.out.println(""); 
		System.out.println(strExec);
		
		dbExec = new pgUpdate();
		dbExec.executeUpdate(strExec, false);
		dbExec.commit();
		
		for(int x=0; x < modelHDMFCollection.getRowCount(); x++) {
			System.out.println(""); 
			System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="); 

			if ((Boolean) modelHDMFCollection.getValueAt(x, 0)) {
				strExec = "update rf_hdmf_payments \n" + 
						"set rplf_no = '"+strRPLF+"' \n" + 
						"where entity_id = '"+modelHDMFCollection.getValueAt(x, 7)+"' and proj_id = '"+modelHDMFCollection.getValueAt(x, 8).toString()+"' and pbl_id = '"+modelHDMFCollection.getValueAt(x, 9).toString()+"' and seq_no::int = '"+modelHDMFCollection.getValueAt(x, 10).toString()+"'::int \n" + 
						"and receipt_no = '"+modelHDMFCollection.getValueAt(x, 4).toString()+"' and client_seqno = '"+modelHDMFCollection.getValueAt(x, 12).toString()+"' and amount::numeric(19, 2) = '"+modelHDMFCollection.getValueAt(x, 5).toString()+"'::numeric(19, 2) \n" +
						"and (rplf_no = '' or coalesce(rplf_no, '') = '' or rplf_no is null);";
				
				System.out.println(""); 
				System.out.println(strExec);
				
				dbExec = new pgUpdate();
				dbExec.executeUpdate(strExec, false);
				dbExec.commit();
			}
		}
		
		txtRPLF.setValue(strRPLF);
		return retValue; 
	}
	
	private void createFile() throws DBFException {
		String strDir = FncGlobal.OpenDir("Folder"); 
		
	    Workbook wb = new HSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet = wb.createSheet("new sheet");
	    HSSFFont hSSFFont1 = (HSSFFont) wb.createFont();
	    hSSFFont1.setFontName("Calibri");
	    hSSFFont1.setBoldweight(hSSFFont1.BOLDWEIGHT_BOLD);
	    HSSFFont hSSFFont2 = (HSSFFont) wb.createFont();
	    hSSFFont2.setFontName("Calibri");
	    
	    HSSFCellStyle styleVertCenter = (HSSFCellStyle) wb.createCellStyle();
	    styleVertCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    styleVertCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertCenter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	    styleVertCenter.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    HSSFCellStyle styleVertCenter_1 = (HSSFCellStyle) wb.createCellStyle();
	    styleVertCenter_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    styleVertCenter_1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertCenter_1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertCenter_1.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    styleVertCenter_1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    HSSFCellStyle styleHoriCenter = (HSSFCellStyle) wb.createCellStyle();
	    styleHoriCenter.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    
	    HSSFCellStyle styleVertTopBordered = (HSSFCellStyle) wb.createCellStyle();
	    styleVertTopBordered.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
	    styleVertTopBordered.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertTopBordered.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertTopBordered.setFont(hSSFFont1);
	    
	    HSSFCellStyle styleVertCenterBordered = (HSSFCellStyle) wb.createCellStyle();
	    styleVertCenterBordered.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    styleVertCenterBordered.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleVertCenterBordered.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    styleVertCenterBordered.setFont(hSSFFont2);
	   
	    String strName = "";
	    String strNameSequence = FncGlobal.GetString("select company_alias from mf_company where co_id = '"+txtCoID.getText()+"'");
		
		File f = new File(strDir + "/" + strNameSequence);
		strName = strNameSequence;
		
		pgSelect sqlExec = new pgSelect();
		
		System.out.println("");
		System.out.println("select row_number() over(order by a.c_receipt_no)::int::varchar, coalescE(a.c_hdlidno, '')::varchar(12) as hlid_no, \n" + 
			"(select x.last_name from rf_entity x where x.entity_id = a.c_entity_id) as last_name, \n" + 
			"(select x.first_name from rf_entity x where x.entity_id = a.c_entity_id) as first_name, \n" + 
			"(select left(x.middle_name, 1) from rf_entity x where x.entity_id = a.c_entity_id) as middle_name, \n" + 
			"a.c_receipt_no as pfr_no, a.c_trans_date::date::varchar as pfr_date, a.c_amount::numeric(19, 2)::varchar as amount, '', '' \n" + 
			"from view_hdmf_collection('"+txtCoID.getValue().toString()+"', '"+txtProID.getValue().toString()+"', '"+txtPhaseID.getValue().toString()+"', \n" +
			"'"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"', '"+txtRPLF.getValue().toString()+"') a \n" + 
			"order by a.c_receipt_no; ");

		sqlExec.select("select row_number() over(order by a.c_receipt_no)::int::varchar, coalescE(a.c_hdlidno, '')::varchar(12) as hlid_no, \n" + 
				"(select x.last_name from rf_entity x where x.entity_id = a.c_entity_id) as last_name, \n" + 
				"(select x.first_name from rf_entity x where x.entity_id = a.c_entity_id) as first_name, \n" + 
				"(select left(x.middle_name, 1) from rf_entity x where x.entity_id = a.c_entity_id) as middle_name, \n" + 
				"a.c_receipt_no as pfr_no, a.c_trans_date::date::varchar as pfr_date, a.c_amount::numeric(19, 2)::varchar as amount, '', '' \n" + 
				"from view_hdmf_collection('"+txtCoID.getValue().toString()+"', '"+txtProID.getValue().toString()+"', '"+txtPhaseID.getValue().toString()+"', \n" +
				"'"+dteFrom.getDate().toString()+"', '"+dteTo.getDate().toString()+"', '"+txtRPLF.getValue().toString()+"') a \n" + 
				"order by a.c_receipt_no; ");
		
	    Row row = sheet.createRow((short)0);
	    row.setHeightInPoints(3 * sheet.getDefaultRowHeightInPoints());
	    
	    /*	This is the Original Style	*/
	    
	    row.createCell(0).setCellValue("NO"); 
	    row.createCell(1).setCellValue("HLID NO"); 
	    row.createCell(2).setCellValue("LAST NAME");
	    row.createCell(3).setCellValue("FIRST NAME"); 
	    row.createCell(4).setCellValue("M. I");
	    row.createCell(5).setCellValue("PFR NO.");
		row.createCell(6).setCellValue("PFR DATE");
		row.createCell(7).setCellValue("AMOUNT");
		row.createCell(8).setCellValue("");
		row.createCell(9).setCellValue("");
	    
	    row.getCell(0).setCellStyle(styleVertCenter);
	    row.getCell(1).setCellStyle(styleVertCenter);
	    row.getCell(2).setCellStyle(styleVertCenter);
	    row.getCell(3).setCellStyle(styleVertCenter);
	    row.getCell(4).setCellStyle(styleVertCenter);
	    row.getCell(5).setCellStyle(styleVertCenter);
	    row.getCell(6).setCellStyle(styleVertCenter);
	    row.getCell(7).setCellStyle(styleVertCenter);
	    row.getCell(8).setCellStyle(styleVertCenter);
	    row.getCell(9).setCellStyle(styleVertCenter);
	    
		if (sqlExec.isNotNull()) {
			Integer intCount = sqlExec.getRowCount();
			System.out.println("");
			System.out.println("Total number of rows: " + intCount);
			
		    for (Integer intRow = 0; intRow < intCount; intRow++) {
		    	Integer shortInt = intRow + 1;
		    	Short shortRow = Short.parseShort(shortInt.toString());
			    Row item_row = sheet.createRow((short)shortRow);
			    
			    for (Integer intCol = 0; intCol < 10; intCol ++) {
			    	item_row.createCell(intCol).setCellValue((String)sqlExec.getResult()[intRow][intCol]);
			    }
		    }
		    
		    for (Integer intCol = 0; intCol <= row.getLastCellNum(); intCol++) {
		    	wb.getSheetAt(0).autoSizeColumn(intCol);
		    }
		} else {
			System.out.print("");
			System.out.print("Procedure halted.");
		}
		
	    FileOutputStream fileOut = null;
	    try {
			fileOut = new FileOutputStream(strDir + "/" + strName);
		} catch (FileNotFoundException e2) {
			System.out.println("");
			System.out.println("Error Line -- " + strName);
		}
		
	    try {
			wb.write(fileOut);
		} catch (Exception e1) {
			System.out.println("Error Line -- wb.write(fileOut)");
		}
	    
	    try {
			fileOut.close();
			JOptionPane.showMessageDialog(getContentPane(), "Export Successful");
		} catch (Exception e1) {
			System.out.println("Error Line -- fileOut.close();");
			JOptionPane.showMessageDialog(getContentPane(), "Export Failed");
		}
	    
	    
	}
}
