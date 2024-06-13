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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import com.lowagie.text.ExceptionConverter;

import Accounting.Disbursements.RequestForPayment;
import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;
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

public class PagibigClientStatusWithNoaNotLR extends _JInternalFrame implements
		ActionListener, _GUI {

	private static final long serialVersionUID = 7597568564393294700L;
	static String title = "PAGIBIG Clients With NOA(Not Loan Released)";
	Dimension frameSize = new Dimension(650, 350);

	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	
	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtPhaseID;
			
	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;
	
	private _JDateChooser dteDateFrom;
	
	private JButton btnPreview;
	private JButton btnExport;
	
	private JComboBox cboStatus;
	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public PagibigClientStatusWithNoaNotLR() {
		super(title, true, true, true, true);
		initGUI();
	}

	public PagibigClientStatusWithNoaNotLR(String title) {
		super(title);
		initGUI();
	}

	public PagibigClientStatusWithNoaNotLR(String title, boolean resizable,
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
		
		Setdefaults();
	}

	private static String CompanySQL() {
		return "SELECT TRIM(co_id)::VARCHAR as \"ID\", TRIM(company_name) as \"Company Name\", " +
			   "TRIM(company_alias)::VARCHAR as \"Alias\", company_logo as \"Logo\" FROM mf_company order by co_id ";
	}
	
	public static String ProjectSQL(String strCo){
		return "SELECT proj_id, proj_alias, proj_name\n" +
			   "FROM mf_project\n" +
			   "WHERE (co_id = '"+strCo+"' OR '"+strCo+"' = '')\n" +
			   "ORDER BY proj_name";
	}

	private String sqlPhase(String strProject) {
		return "select x.phase, x.sub_proj_name \n" +
			"from mf_sub_project x \n" +
			"where x.phase in (select distinct a.phase from mf_unit_info a) \n" +
			"and (proj_id = '"+strProject+"' or '"+strProject+"' = 'null') \n" +
			"and status_id = 'A' \n" +
			"order by getinteger(x.phase)::int";
	}
	
	private void Setdefaults() {
		txtCoID.setValue("02");
		txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
		
		txtProID.setValue("");
		txtProject.setText("All Projects");
		
		txtPhaseID.setValue("");
		txtPhase.setText("All Phase");
	}
	
    public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Preview")) {
			generate_list gen_list = new generate_list();
			Thread thr_gen = new Thread(gen_list);
			thr_gen.run();
			try {
				thr_gen.join();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			preview_detailed prev_det = new preview_detailed();
			Thread thr_det = new Thread(prev_det);
			thr_det.start();
			try {
				thr_det.join();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			preview_summary prev_sum = new preview_summary();
			Thread thr_sum = new Thread(prev_sum);
			thr_sum.start();
			try {
				thr_sum.join();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (e.getActionCommand().equals("Export")) {
			String[] strHead = {
					"DEVELOPER'S NAME: CEQHOMES DEVELOPMENT CORPORATION", 
					"PROJECT NAME: TERRAVERDE RESIDENCES",
					"STATUS OF ACCOUNTS WITH NOA(Not Loan Released)", 
					"DATE: " + FncGlobal.getDateSQL().toString()
			};

			String col_names[] = 
				{
					"Project", "Phase", "Block", "Lot", "Client Name", "Aging", "Model", "Loanable Amount", "EPEB No.", "EPEB Date", "EPEB EXT Date", "NOA Released Date", 
					"NOA Signed", "NOA EXT Date", "Birth Year", "TCT No.", "TCT forwarded", "TCT Annotation", "BVS", "Passed Inspection", "Failed Inspection", "MSVS Reverification", 
					"MSVS Reverified", "Findings", "Compliance", "First Notice", "Final Notice", "Post Approval", "Buyer Type", "Full DP", "alpha_group", "bravo_group", "charlie_group"
				};
			
			String strSQL = "SELECT project, phase, block, lot, name, aging, model_alias, loanable_amount, epeb_no, epeb_date, epeb_ext_date, noa_released_date, \n" +
				"noa_signed, noa_ext_date, birth_year, tct_no, tct_fwd, tct_annot, bvs, insp_passed, insp_failed, msvs_reverification, msvs_reverified, \n" +
				"findings, compliance, first_notice, final_notice, post_approval, type_desc, full_dp, alpha_group, bravo_group, charlie_group \n" +
				"FROM view_hdmf_noanotloanreleased('"+txtCoID.getText()+"', '"+txtProID.getText()+"', '"+FncGlobal.getDateSQL().toString()+"') \n" +
				"ORDER BY alpha_group, bravo_group, charlie_group, name, phase, block, lot";
			

			FncGlobal.startProgress("Creating spreadsheet.");
			FncGlobal.CreateXLSwithHeaders(col_names, strSQL, "Status of Accounts with NOA(Not yet Loan Released)", 4, strHead);
			FncGlobal.stopProgress();
		}
    }
    
    private class generate_list extends Thread {
    	
    	public void run() {
    		FncGlobal.startProgress("Generating list...");
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			String strFrom = (String) sdf.format(dteDateFrom.getDate());
			
    		String strProject = "";
    		String strPhase = "";
    		
    		if (txtProject.getText()=="All Projects") {
    			strProject = "";
    		} else {
    			strProject = txtProID.getValue();
    		}
    		
    		if (txtPhase.getText()=="All Projects") {
    			strPhase = "";
    		} else {
    			strPhase = txtPhaseID.getValue();
    		}
    		
    		pgUpdate db = new pgUpdate();
    		db.executeUpdate("delete from tmpnoanotlr;", true);
    		db.commit();
    		
    		db = new pgUpdate();
    		db.executeUpdate("insert into tmpnoanotlr\n" + 
    				"SELECT a.*, 1::int as count\n" + 
    				"FROM view_hdmf_noanotloanreleased('"+txtCoID.getValue()+"', '"+strProject+"', '"+strPhase+"', '"+strFrom+"') a\n" + 
    				"ORDER BY a.alpha_group, a.bravo_group, a.charlie_group, a.name, a.phase, a.block, a.lot;", true);
    		db.commit();
    		FncGlobal.stopProgress();
    	}
    	
    }
    
	private class preview_detailed extends Thread {
		
		public void run() {
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			String strFrom = (String) sdf.format(dteDateFrom.getDate());
			String company_logo = sql_getCompanyLogo(txtCoID.getValue());
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", txtCompany.getText());
			mapParameters.put("02_AsOfDate", strFrom);
			mapParameters.put("03_CoID", txtCoID.getText());
			mapParameters.put("04_ProID", txtProID.getText());
			mapParameters.put("05_Project", txtProject.getText());
			mapParameters.put("06_User",  UserInfo.FullName);
			mapParameters.put("07_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
			FncReport.generateReport("/Reports/rpt_hdmf_withNoaNotLR.jasper", "Not Yet Loan Released Accounts with NOA Detailed", "", mapParameters);

		}
		
	}
	
	private class preview_summary extends Thread {
		
		public void run() {
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			String strFrom = (String) sdf.format(dteDateFrom.getDate());
			String company_logo = sql_getCompanyLogo(txtCoID.getValue());
			
			Map<String, Object> mapParameters2 = new HashMap<String, Object>();
			mapParameters2.put("01_Company", txtCompany.getText());
			mapParameters2.put("02_Project", txtProject.getText());
			mapParameters2.put("03_Date", strFrom);
			mapParameters2.put("04_Logo", this.getClass().getClassLoader().getResourceAsStream("Images/" + company_logo));
			mapParameters2.put("05_User", UserInfo.FullName);
			
			FncReport.generateReport("/Reports/rpt_hdmf_withNoaNotLR_summary.jasper", "Not Yet Loan Released Accounts with NOA Summary", "", mapParameters2);

		}
		
	}
	
	public static String sql_getCompanyLogo(String co_id) {

		String a = "";

		String SQL = "select company_logo from mf_company \n" + "where co_id = '" + co_id + "' ";

		System.out.printf("SQL #1: sql_getCompanyLogo", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")) {
				a = "";
			} else {
				a = (String) db.getResult()[0][0];
			}

		} else {
			a = "";
		}

		return a;
	}
}
