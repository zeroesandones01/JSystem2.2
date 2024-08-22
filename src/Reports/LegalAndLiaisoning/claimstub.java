package Reports.LegalAndLiaisoning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXFormattedTextField;

import DateChooser._JDateChooser;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import interfaces._GUI;

public class claimstub extends _JInternalFrame implements _GUI, ActionListener {
	
	private static final long serialVersionUID = -908806475924399890L;

	static String title = "Claim Stub Form";

	Dimension frameSize = new Dimension(600, 300);

	private JPanel pnlMain;

	private JButton btnpreview;

	private _JLookup lookupclient;

	private JTextField txtproj_id;

	private JTextField txtpbl;

	private JTextField txtseqno;

	private _JXFormattedTextField txtamount;

	private JTextField txtclientname;

	private JTextField txtproj;

	private JTextField txtpbl_name;

	private JButton btncancel;
	String civil_stat;
	String company;

	private JTextField txtor_no;

	private _JDateChooser ordate;


	
	
	public claimstub() {
		super(title, true, true, true, true);
		initGUI();
	}

	public claimstub(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public claimstub(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(frameSize);
		this.setMinimumSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5,5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			{
				JPanel pnlnorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlnorth, BorderLayout.CENTER);
				pnlnorth.setBorder(components.JTBorderFactory.createTitleBorder("Client Details" ));
				{
					JPanel pnlwest = new JPanel(new GridLayout(5, 1, 5, 5));
					pnlnorth.add(pnlwest, BorderLayout.WEST);
					pnlwest.setPreferredSize(new Dimension(100, 0));
					{
						JLabel lblclient = new JLabel("Client Name");
						pnlwest.add(lblclient);
					}
					{
						JLabel lblproject = new JLabel("Project");
						pnlwest.add(lblproject);
					}
					{
						JLabel lblpbl = new JLabel("Phase/Block/Lot");
						pnlwest.add(lblpbl);
					}
					{
						JLabel lblseqno = new JLabel("Seq. No.");
						pnlwest.add(lblseqno);
					}
//					{
//						JLabel lblamount = new JLabel("Amount");
//						pnlwest.add(lblamount);
//					}
					{
						JLabel lblor_no = new JLabel("Or No.");
						pnlwest.add(lblor_no);
					}
				}
				{
					JPanel pnlcenter = new JPanel(new GridLayout(5, 1, 5, 5));
					pnlnorth.add(pnlcenter, BorderLayout.CENTER);	
					{
						lookupclient = new _JLookup(null, "Client", 0);
						pnlcenter.add(lookupclient);
						lookupclient.setLookupSQL(sqlClients());
						lookupclient.addLookupListener(new LookupListener() {
							@Override
							public void lookupPerformed(LookupEvent event) {
								Object [] data = ((_JLookup) event.getSource()).getDataSet();

								if(data != null){
									String entity_id = (String) data[0];
									String entity_name = (String) data[1];
									String unit_desc = (String) data[2];
									String unit_id = (String) data[3];
									Integer seq_no = (Integer) data[4];
									String proj_id = (String) data[5];
									String proj_name = (String) data[6];											
									//String tin_no = (String) data[8];
									
									 civil_stat = FncGlobal.GetString("select b.civil_status_desc\n"
											+ "from rf_entity a\n"
											+ "left join mf_civil_status b on a.civil_status_code = b.civil_status_code\n"
											+ "where entity_id = '"+entity_id+"'" );
									 
									 company = FncGlobal.GetString("\n"
									 		+ "select b.company_name from mf_project a\n"
									 		+ "left join mf_company b on a.co_id = b.co_id\n"
									 		+ "where a.proj_id = '"+proj_id+"' and a.status_id = 'A'");
									
									System.out.println("civil_status: "+civil_stat);
									txtclientname.setText(entity_name);
									txtproj_id.setText(proj_id);
									txtproj.setText(proj_name);
									txtpbl.setText(unit_id);
									txtpbl_name.setText(unit_desc);
									txtseqno.setText(seq_no.toString());
									
								}
							}
						});
					}
					{
						txtproj_id = new JTextField();
						pnlcenter.add(txtproj_id);
						txtproj_id.setEditable(false);
					}
					{
						txtpbl = new JTextField();
						pnlcenter.add(txtpbl);
						txtpbl.setEditable(false);
					}
					{
						txtseqno = new JTextField();
						pnlcenter.add(txtseqno);
						txtseqno.setEditable(false);
					}
//					{//Replaced with txtor_no as requested by jonjon
//						txtamount = new _JXFormattedTextField( JXFormattedTextField.LEFT );
//						pnlcenter.add(txtamount);
//						txtamount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
//						txtamount.setHorizontalAlignment(JXFormattedTextField.CENTER);
//					}
					{
						txtor_no = new JTextField();
						pnlcenter.add(txtor_no);
					}
				}
				{
					JPanel pnleast = new JPanel(new GridLayout(5, 1, 5, 5));
					pnlnorth.add(pnleast, BorderLayout.EAST);
					pnleast.setPreferredSize(new Dimension(350, 0));
					{
						txtclientname = new JTextField();
						pnleast.add(txtclientname);
						txtclientname.setEditable(false);
					}
					{
						txtproj = new JTextField();
						pnleast.add(txtproj);
						txtproj.setEditable(false);
					}
					{
						txtpbl_name = new JTextField();
						pnleast.add(txtpbl_name);
						txtpbl_name.setEditable(false);
					}
					{
						JLabel lblextra = new JLabel("");
						pnleast.add(lblextra);
					}
					{
						JPanel pnldate = new JPanel(new BorderLayout(5, 5));
						pnleast.add(pnldate);
						{
							JPanel pnldate1 = new JPanel(new BorderLayout(5, 5));
							pnldate.add(pnldate1, BorderLayout.WEST);
							pnldate1.setPreferredSize(new Dimension(200, 0));
							{
								JLabel lblor_date = new JLabel("OR Date");
								pnldate1.add(lblor_date, BorderLayout.WEST);
								lblor_date.setPreferredSize(new Dimension(60, 0));
							}
							{
								ordate = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
								pnldate1.add(ordate, BorderLayout.CENTER);
								ordate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
								ordate.setDateFormatString("yyyy-MM-dd");
							}
							{
								JPanel pnlxtra = new JPanel(new BorderLayout());
								pnldate.add(pnlxtra, BorderLayout.EAST);
								pnlxtra.setPreferredSize(new Dimension(50, 0));
							}
						}
						
					}
				}
			}
			{
				JPanel pnlsouth = new JPanel();
				pnlMain.add(pnlsouth,BorderLayout.SOUTH);
				pnlsouth.setPreferredSize(new Dimension(0, 30));
				{
					btnpreview = new JButton("Preview");
					pnlsouth.add(btnpreview);
					btnpreview.setActionCommand("preview");
					btnpreview.addActionListener(this);
					//btnpreview.setPreferredSize(new Dimension(100, 0));
				}
				{
					btncancel = new JButton("Cancel");
					pnlsouth.add(btncancel);
					btncancel.setActionCommand("cancel");
					btncancel.addActionListener(this);
					//btncancel.setPreferredSize(new Dimension(100, 0));
				}
			}
		}
		
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")) {
			lookupclient.setValue("");
			txtclientname.setText("");
			txtproj.setText("");
			txtproj_id.setText("");
			txtpbl.setText("");
			txtpbl_name.setText("");
			txtseqno.setText("");
			txtor_no.setText("");
			ordate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
			//txtamount.setValue(null);
			
		}
		if(e.getActionCommand().equals("preview")) {
			preview();
		}
	}
	private String sqlClients(){
		
		String SQL = "select * from view_sql_clients()";

		FncSystem.out("Clients", SQL);
		return SQL;
	}
	private void preview() {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		
		mapParameters.put("client", company.concat(" - ".concat("Evebrett Vargas Domanais"))); 
		//mapParameters.put("amount", txtamount.getValued());
		mapParameters.put("or_no", txtor_no.getText());
		mapParameters.put("or_date", ordate.getDate());
		mapParameters.put("owner", txtclientname.getText().concat(" / ").concat(civil_stat));
		
		
		System.out.println("company: "+company);
		System.out.println("owner: "+txtclientname.getText().concat(" / ").concat(civil_stat));
		System.out.println("or_no: "+txtor_no.getText());
		System.out.println("or_date: "+ordate.getDate());
		
		mapParameters.put("bckgrnd", this.getClass().getClassLoader().getResourceAsStream("Images/" + "claimstub.jpg" ));
		FncReport.generateReport("/Reports/claimstab.jasper", "Claim Stub", mapParameters);
	}
}
