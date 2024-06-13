package Reports.LoansAndReceivable;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;

public class LoanProcessingAgingReport extends _JInternalFrame implements
		ActionListener, _GUI {

	private static final long serialVersionUID = -2644763640170031658L;
	static String title = "Loan Processing Aging";
	Dimension frameSize = new Dimension(650, 350);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	
	private static _JLookup txtCoID;
	private static _JLookup txtProID;
	private static _JLookup txtPhaseID;
			
	private static JTextField txtCompany;
	private static JTextField txtProject;
	private static JTextField txtPhase;
	
	private static _JDateChooser dteDateFrom;
	
	private static JButton btnPreview;
	private JButton btnExport;
	private JComboBox cboStatus;
	
	private static JProgressBar proBar;
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	private static String strComName = getHostName();
	
	public LoanProcessingAgingReport() {
		super(title, true, true, true, true);
		initGUI();
	}

	public LoanProcessingAgingReport(String title) {
		super(title);
		initGUI();
	}

	public LoanProcessingAgingReport(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
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
			JXPanel pnlCenter = new JXPanel(new GridBagLayout());
			panMain.add(pnlCenter,BorderLayout.CENTER);
			{
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;
				{
					gbc.weightx = 1;
					gbc.weighty = 2;

					gbc.gridx = 0;
					gbc.gridy = 0;
					

					JXPanel panNorth1 = new JXPanel(new GridBagLayout());
					pnlCenter.add(panNorth1, gbc);
					panNorth1.setBorder(JTBorderFactory.createTitleBorder("Project and Company", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagOne = new GridBagConstraints();
						bagOne.fill = GridBagConstraints.BOTH;
						bagOne.insets = new Insets(5,3,5,3);
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 0;

							lblCompany = new JLabel("Company:");
							panNorth1.add(lblCompany,bagOne);
							lblCompany.setHorizontalAlignment(JTextField.LEFT);
							lblCompany.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 0;

							txtCoID = new _JLookup("");
							panNorth1.add(txtCoID,bagOne);
							txtCoID.setHorizontalAlignment(JTextField.CENTER);
							txtCoID.setLookupSQL(CompanySQL());
							txtCoID.setReturnColumn(0);
							txtCoID.setFont(FncGlobal.sizeTextValue);
							txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtCompany.setText(data[1].toString());
										txtProID.setLookupSQL(ProjectSQL(txtCoID.getValue()));
										
										btnPreview.setEnabled(true);
										btnExport.setEnabled(true);
									} else {
										btnPreview.setEnabled(false);
										btnExport.setEnabled(false);									
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
							bagOne.weightx = 1;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 0;

							txtCompany = new JTextField("");
							panNorth1.add(txtCompany,bagOne);
							txtCompany.setHorizontalAlignment(JTextField.CENTER);
							txtCompany.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 1;

							lblProject = new JLabel("Project:");
							panNorth1.add(lblProject,bagOne);
							lblProject.setHorizontalAlignment(JTextField.LEFT);
							lblProject.setFont(FncGlobal.sizeLabel);
							
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 1;

							txtProID = new _JLookup("");
							panNorth1.add(txtProID,bagOne);
							txtProID.setHorizontalAlignment(JTextField.CENTER);
							txtProID.setLookupSQL(ProjectSQL(""));
							txtProID.setReturnColumn(0);
							txtProID.setFont(FncGlobal.sizeTextValue);
							txtProID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtProject.setText(data[2].toString());
										txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
									}
								}
							});
							txtProID.addFocusListener(new FocusListener() {
								
								@Override
								public void focusLost(FocusEvent e) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void focusGained(FocusEvent e) {
									txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
									
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
						{
							bagOne.weightx = 1;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 1;

							txtProject = new JTextField("");
							panNorth1.add(txtProject, bagOne);
							txtProject.setHorizontalAlignment(JTextField.CENTER);
							txtProject.setFont(FncGlobal.sizeTextValue);
						}
						{
							bagOne.weightx = 0;
							bagOne.weighty = 1;

							bagOne.gridx = 0;
							bagOne.gridy = 2;

							lblPhase = new JLabel("Phase:");
							panNorth1.add(lblPhase,bagOne);
							lblPhase.setHorizontalAlignment(JTextField.LEFT);
							lblPhase.setFont(FncGlobal.sizeLabel);
						}
						{
							bagOne.weightx = 0.5;
							bagOne.weighty = 1;

							bagOne.gridx = 1;
							bagOne.gridy = 2;

							txtPhaseID = new _JLookup("");
							panNorth1.add(txtPhaseID,bagOne);
							txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
							txtPhaseID.setLookupSQL(sqlPhase(txtProID.getValue()));
							txtPhaseID.setReturnColumn(0);
							txtPhaseID.setFont(FncGlobal.sizeTextValue);
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
						{
							bagOne.weightx = 1;
							bagOne.weighty = 1;

							bagOne.gridx = 2;
							bagOne.gridy = 2;

							txtPhase = new JTextField("");
							panNorth1.add(txtPhase, bagOne);
							txtPhase.setHorizontalAlignment(JTextField.CENTER);
							txtPhase.setFont(FncGlobal.sizeTextValue);
						}
					}
				}
				{
					gbc.weightx = 1;
					gbc.weighty = 0.25;

					gbc.gridx = 0;
					gbc.gridy = 1;
					
					JXPanel panNorth2 = new JXPanel(new GridBagLayout());
					pnlCenter.add(panNorth2,gbc);
					panNorth2.setBorder(JTBorderFactory.createTitleBorder("Cut-Off Date", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						GridBagConstraints bagTwo = new GridBagConstraints();
						bagTwo.fill = GridBagConstraints.BOTH;
						bagTwo.insets = new Insets(5,5,5,5);
						{
							bagTwo.weightx = 1;
							bagTwo.weighty = 1;
							
							bagTwo.gridx = 0;
							bagTwo.gridy = 0;
							
							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							panNorth2.add(dteDateFrom, bagTwo);
							dteDateFrom.setDate(null);
							dteDateFrom.setEnabled(true);
							dteDateFrom.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							
						}
					}
				}
			}
		}
		{
			
			
				
				
					
					{
						
						
						JXPanel panStatus = new JXPanel(new BorderLayout(5, 5));
						/*	panNorth2.add(panStatus, BorderLayout.CENTER);	*/
						panStatus.setBorder(JTBorderFactory.createTitleBorder("Status", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
						{
							String strCombo[] =	{"All", "56 - TCT Forwarded to RD for Annotation", "17 - Temporary Reserved", "01 - Officially Reserved", "18 - Documents Complete", 
									"31 - Loan Filed (PagIbig)", "43 - Loan Rtnd (First Filing w/ Findings)", "35 - Notice Of Approval (NOA) Released", 
									"56 - TCT Forwarded to RD for Annotation", "46 - TCT Annotated", "48 - Post Approval (Refiling)", "32 - Loan Released"};
							
							cboStatus = new JComboBox(strCombo);
							panStatus.add(cboStatus, BorderLayout.CENTER);
						}
					}
				
			
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.SOUTH);
				{
					JXPanel panButton = new JXPanel(new GridLayout(1, 4, 5, 5));
					panCenter.add(panButton, BorderLayout.CENTER);
					panButton.setPreferredSize(new Dimension(0,30));
					{
						panButton.add(Box.createHorizontalBox());
						panButton.add(Box.createHorizontalBox());
						panButton.add(Box.createHorizontalBox());
					}
					{
						btnPreview = new JButton("Preview");
						panButton.add(btnPreview);
						btnPreview.setActionCommand("Preview");;
						btnPreview.addActionListener(this);
						btnPreview.setEnabled(true);
						btnPreview.setFont(FncGlobal.sizeControls);
					}
					{
						proBar = new JProgressBar();
						//panButton.add(proBar, BorderLayout.CENTER); 
						proBar.setStringPainted(true);
						proBar.setMaximum(1);
						proBar.setValue(0);
					}
					{
						btnExport = new JButton("Export");
						//panButton.add(btnExport);
						btnExport.setActionCommand("Export");;
						btnExport.addActionListener(this);
						btnExport.setEnabled(true);
					}
				}
			}
		}
		{
			Setdefaults();
		}
	}

	private static String CompanySQL() {
		return "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
			   "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
	}
	
	public static String ProjectSQL(String strCo){
		return "SELECT proj_id, proj_alias, proj_name\n" +
			   "FROM mf_project\n" +
			   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '"+strCo+"') \n"+
			   "and proj_id NOT IN ('020', '016') \n" +
			   "ORDER BY proj_name";
	}
	
	private void Setdefaults() {
		txtCoID.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		
		txtProID.setValue("");
		txtProject.setText("All Projects");
		
		txtPhaseID.setValue("");
		txtPhase.setText("All Phase");
	}
	
    public void actionPerformed(final ActionEvent e) {
    	btnPreview.setEnabled(false);
    	
		if (e.getActionCommand().equals("Preview")) {
			Load(); 
		} else if (e.getActionCommand().equals("Export")) {
			Export();
		}
		
		btnPreview.setEnabled(true);
    }
    
	private String sqlPhase(String strProject) {
		return "select x.phase, x.sub_proj_name \n" +
			"from mf_sub_project x \n" +
			"where x.phase in (select distinct a.phase from mf_unit_info a) \n" +
			"and (proj_id = '"+strProject+"' or '"+strProject+"' = 'null') \n" +
			"and status_id = 'A' \n" +
			"order by x.phase";
	}
	
	private void Export() {
		String[] strHead = {
				"DEVELOPER'S NAME: CEQHOMES DEVELOPMENT CORPORATION", 
				"PROJECT NAME: TERRAVERDE RESIDENCES", 
				"DATE: " + FncGlobal.getDateSQL().toString(), 
				"LOAN PROCESSING AGING REPORT"
		};

		String col_names[] = {
				"Phase", "Block", "Lot", "Buyer's Name", "Aging", "No. of Days", "% House Cons", "50% House Completion Date", 
				"68% house completion date", "75% house completion date", "95% house completion date", 
				"House Model", "Loanable Amount", "TCT Date", "Taxdec Lot Date", "Taxdec House Date", "Date Completed", "Circular No.", "MSVS Reverified", 
				"Filed HDMF", "BVS", "NOA Released", "NOA Signed", "DOA Signed", "TCT Forwarded for Annotation", "TCT Annotated", "Post Filing", "Alpha Category", "Beta Category"
		};
		
		String strSQL = "select c_phase, c_block, c_lot, c_name, c_aging, c_noofdays, c_housepctg, c_date50pctg, \n" + 
				"c_date68pctg, c_date75pctg, c_date95pctg, c_housemodel, c_loanable, c_tctdate, c_taxdeclot, c_taxdechouse, c_complete, c_circular, \n" +
				"c_msvs_reverified, c_filed_hdmf, c_bvs, c_noa_released, c_noa_signed, c_doa_signed, c_tct_fwd, c_tct_annotated, c_post_filing, c_alpha_cat, c_beta_cat \n" +
				"from rf_hdmf_loanprocessing_aging_detailed \n" +
				"order by c_alpha_cat, c_beta_cat, c_aging desc, c_name";

		FncGlobal.startProgress("Creating spreadsheet.");
		FncGlobal.CreateXLSwithGroup(col_names, strSQL, "Loan Processing Aging", 4, strHead);
		FncGlobal.stopProgress();
	}
	
    
    private static void prime_report() {
		String strSQL = "";
		pgUpdate dbExec1 = new pgUpdate();
		pgUpdate dbExec2 = new pgUpdate();
		
		strSQL = "delete from rf_hdmf_loanprocessing_aging_detailed where host_name = '"+strComName+"'";
		
		System.out.println("");
		System.out.println(strSQL);
		
		dbExec1.executeUpdate(strSQL, false);
		dbExec1.commit();

		strSQL = "insert into rf_hdmf_loanprocessing_aging_detailed \n" +
				"select *, '"+strComName+"'  \n" +
				"from view_hdmf_loanprocessing_aging_detailed('"+txtCoID.getValue()+"', '"+txtProID.getValue()+"', '"+txtPhaseID.getValue()+"', '"+dteDateFrom.getDate()+"'::date) a \n" + 
				"where (case when c_phase = '3' or c_phase = '1-B' then true else a.c_remaining_dp <= 5 end)";
		
		System.out.println("");
		System.out.println(strSQL);
		
		dbExec2.executeUpdate(strSQL, false);
		dbExec2.commit();
    }
	
	private static void preview_detailed() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strFrom = (String) sdf.format(dteDateFrom.getDate());
		
		@SuppressWarnings("unused")
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", txtCompany.getText());
		mapParameters.put("02_Project", txtProject.getText());
		mapParameters.put("03_Date", strFrom);
		//mapParameters.put("04_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters.put("05_User", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("06_host", strComName); 
		FncReport.generateReport("/Reports/rpt_hdmf_loanProcessing_aging.jasper", "Loan Processing Aging", "", mapParameters);
		
	}
	
	private static void preview_summary() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String strFrom = (String) sdf.format(dteDateFrom.getDate());
		
		@SuppressWarnings("unused")
		String company_logo = RequestForPayment.sql_getCompanyLogo();
		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("01_Company", txtCompany.getText());
		mapParameters2.put("02_AsOfDate", strFrom);
		mapParameters2.put("03_CoID", txtCoID.getValue());
		mapParameters2.put("04_ProID", txtProID.getValue());
		mapParameters2.put("05_Project", txtProject.getText());
		mapParameters2.put("06_User", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		//mapParameters2.put("07_Logo", super.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
		mapParameters2.put("08_phase", txtPhaseID.getValue());
		mapParameters2.put("09_date", dteDateFrom.getDate());
		mapParameters2.put("10_host", strComName); 
		FncReport.generateReport("/Reports/rpt_hdmf_LoanProcessingAging_detailed.jasper", "Loan Processing Aging Detailed", "", mapParameters2);
		
	}
	
    private static void Load() {
    	
    	SwingWorker sw = new SwingWorker() {

			protected Object doInBackground() throws FileNotFoundException, IOException, InterruptedException {
				Controls(false);
				FncGlobal.startProgress("Priming report...");
				
				prime_report(); 
				preview_detailed(); 
				preview_summary(); 
				
				Controls(true);
				FncGlobal.stopProgress();
				return null;
			}
			
    	}; 
    	
    	sw.execute(); 
    }

    private static void Controls(Boolean blnRev) {
    	btnPreview.setEnabled(blnRev);
    	txtCoID.setEnabled(blnRev);
    	txtProID.setEnabled(blnRev);
    	txtPhaseID.setEnabled(blnRev);
    	dteDateFrom.setEnabled(blnRev);
    }
    
	private static String getHostName() {
        InetAddress ip;
        String hostname = null;
        
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);
 
        } catch (UnknownHostException e) {
 
            e.printStackTrace();
        }
        
		return hostname;
	}
}
