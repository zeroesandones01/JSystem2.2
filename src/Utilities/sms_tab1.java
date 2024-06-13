package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Functions.FncLookAndFeel;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import interfaces._GUI;

public class sms_tab1 extends JXPanel implements _GUI {

	private static final long serialVersionUID = 6633182168553639103L;
	private jSMS sms_main; 
	
	private _JLookup txtID; 
	private JTextField txtName; 
	private JTextField txtProjID; 
	private JTextField txtProject;
	private JTextField txtUnitID; 
	private JTextField txtUnit;
	private JTextField txtSeqNo; 
	
	private JTextField txtMobileNumber; 
	private JTextField txtMessage;
	private JButton btnQ;
	
	private JLabel lblRemChar; 
	
	private Font fontPlainSanSerNine = new Font("SansSerif", Font.PLAIN, 9);
	
	public sms_tab1(jSMS sms) {
		this.sms_main = sms; 
		initGUI(); 
	}

	public sms_tab1(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI(); 
	}

	public sms_tab1(LayoutManager layout) {
		super(layout);
		initGUI(); 
	}

	public sms_tab1(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI(); 
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		this.add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panPage = new JXPanel(new BorderLayout(5, 5)); 
			panMain.add(panPage, BorderLayout.PAGE_START); 
			panPage.setPreferredSize(new Dimension(0, 125));
			{
				{
					JXPanel panClient = new JXPanel(new BorderLayout(5, 5)); 
					panPage.add(panClient, BorderLayout.CENTER);
					panClient.setPreferredSize(new Dimension(500, 0));
					panClient.setBorder(JTBorderFactory.createTitleBorder("Client Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
					{
						JXPanel pnlPSLabel = new JXPanel(new GridLayout(3, 2, 5, 5));
						panClient.add(pnlPSLabel, BorderLayout.LINE_START);
						pnlPSLabel.setPreferredSize(new Dimension(200, 0));
						{
							{
								JLabel lblName = new JLabel("Name");
								pnlPSLabel.add(lblName);
								lblName.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								txtID = new _JLookup("");
								pnlPSLabel.add(txtID);
								txtID.setHorizontalAlignment(JTextField.CENTER);
								txtID.setReturnColumn(0);
								txtID.setLookupSQL(Client());
								txtID.setFilterName(true);
								txtID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										try {
											Object[] data = ((_JLookup) event.getSource()).getDataSet();
											if (data != null) {
												txtName.setText(data[1].toString());
												txtProjID.setText(data[4].toString());
												txtProject.setText(data[2].toString());
												txtUnitID.setText(data[5].toString());
												txtUnit.setText(data[3].toString());
												txtSeqNo.setText(data[6].toString());
											}
										} catch(NullPointerException e) {
											System.out.println("Null pointer caught during lookup.");
										}
									}
								});
							}
							{
								JLabel lblProject = new JLabel("Project");
								pnlPSLabel.add(lblProject);
								lblProject.setHorizontalAlignment(JTextField.LEADING);
							}
							{
								txtProjID = new JTextField("");
								pnlPSLabel.add(txtProjID);
								txtProjID.setHorizontalAlignment(JTextField.CENTER);
								txtProjID.setEditable(false);	
							}
							{
								JLabel lblUnit = new JLabel("Unit");
								pnlPSLabel.add(lblUnit);
								lblUnit.setHorizontalAlignment(JTextField.LEADING);	
							}
							{
								txtUnitID = new JTextField("");
								pnlPSLabel.add(txtUnitID);
								txtUnitID.setHorizontalAlignment(JTextField.CENTER);
								txtUnitID.setEditable(false);
							}
						}
					}
					{
						JXPanel pnlBox = new JXPanel(new GridLayout(3, 1, 5, 5));
						panClient.add(pnlBox, BorderLayout.CENTER);
						{
							{
								txtName = new JTextField("");
								pnlBox.add(txtName);
								txtName.setHorizontalAlignment(JTextField.CENTER);
								txtName.setEditable(false);
							}
							{
								JXPanel panProjectMail = new JXPanel(new BorderLayout(5, 5));
								pnlBox.add(panProjectMail, BorderLayout.CENTER);
								{
									txtProject = new JTextField("");
									panProjectMail.add(txtProject, BorderLayout.CENTER);
									txtProject.setHorizontalAlignment(JTextField.CENTER);
									txtProject.setEditable(false);
								}
							}
							{
								JXPanel pnlUnitSeq = new JXPanel(new BorderLayout(5, 5));
								pnlBox.add(pnlUnitSeq, BorderLayout.CENTER);
								{
									JXPanel pnlUnitDesc = new JXPanel(new BorderLayout(5, 5));
									pnlUnitSeq.add(pnlUnitDesc, BorderLayout.CENTER);
									{
										txtUnit = new JTextField("");
										pnlUnitDesc.add(txtUnit);
										txtUnit.setHorizontalAlignment(JTextField.CENTER);
										txtUnit.setHorizontalAlignment(JTextField.CENTER);
										txtUnit.setEditable(false);
									}
									JXPanel pnlSeq = new JXPanel(new BorderLayout(5, 5));
									pnlUnitSeq.add(pnlSeq, BorderLayout.LINE_END);
									pnlSeq.setPreferredSize(new Dimension(125, 0));
									{
										JLabel lblSeq = new JLabel("Sequence");
										pnlSeq.add(lblSeq, BorderLayout.CENTER);
										lblSeq.setHorizontalAlignment(JTextField.CENTER);
									}
									{
										txtSeqNo = new JTextField("");
										pnlSeq.add(txtSeqNo, BorderLayout.LINE_END);
										txtSeqNo.setPreferredSize(new Dimension(50, 0));
										txtSeqNo.setHorizontalAlignment(JTextField.CENTER);
										txtSeqNo.setEditable(false);
									}
								}
							}	
						}
					}
				}
				{
					btnQ = new JButton("Send"); 
					//panPage.add(btnQ, BorderLayout.CENTER); 
					btnQ.setEnabled(false);
					btnQ.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
						}
					});
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				panCenter.setBorder(JTBorderFactory.createTitleBorder("Message for Sending", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					{
						JXPanel panLeft = new JXPanel(new BorderLayout(5, 5)); 
						panCenter.add(panLeft);
						panLeft.setPreferredSize(new Dimension(300, 0));
						{
							{
								JXPanel panNumber = new JXPanel(new BorderLayout(5, 5)); 
								panLeft.add(panNumber, BorderLayout.PAGE_START); 
								panNumber.setPreferredSize(new Dimension(0, 27));
								{
									{
										JLabel lblMobileNo = new JLabel("Mobile No(s)"); 
										panNumber.add(lblMobileNo, BorderLayout.LINE_START); 
										lblMobileNo.setPreferredSize(new Dimension(100, 0));
									}
									{
										txtMobileNumber = new JTextField(""); 
										panNumber.add(txtMobileNumber, BorderLayout.CENTER); 
										txtMobileNumber.setHorizontalAlignment(JTextField.LEADING); 
										txtMobileNumber.setEditable(false);
									}
								}
							}
							{
								JXPanel panMessage = new JXPanel(new BorderLayout(5, 5)); 
								panLeft.add(panMessage, BorderLayout.CENTER); 
								{
									{
										JLabel lblMobileNo = new JLabel(""); 
										panMessage.add(lblMobileNo, BorderLayout.LINE_START); 
										lblMobileNo.setPreferredSize(new Dimension(100, 0));
									}
									{
										txtMessage = new JTextField(""); 
										panMessage.add(txtMessage, BorderLayout.CENTER); 
										txtMessage.setHorizontalAlignment(JTextField.LEADING); 
										txtMessage.setEnabled(false);
									}
								}
							}
							{
								JXPanel panButton = new JXPanel(new GridLayout(1, 2, 5, 5)); 
								panLeft.add(panButton, BorderLayout.PAGE_END); 
								panButton.setPreferredSize(new Dimension(0, 30));
								{
									{
										lblRemChar = new JLabel("Remaining characters (0/4000)"); 
										panButton.add(lblRemChar); 
										lblRemChar.setHorizontalAlignment(JLabel.TRAILING);
										lblRemChar.setFont(fontPlainSanSerNine);
									}
									{
										btnQ = new JButton("Send"); 
										panButton.add(btnQ); 
										btnQ.setEnabled(false);
										btnQ.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												
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
	}
	
	private String Client() {
		return "SELECT a.entity_id, a.entity_name, c.proj_name, d.description, c.proj_id, d.pbl_id, b.seq_no, E.batch_no \n" +
			"FROM rf_entity A \n" +
			"INNER JOIN rf_sold_unit B ON a.entity_id = b.entity_id \n" +
			"INNER JOIN mf_project C ON b.projcode = c.proj_id \n" +
			"INNER JOIN mf_unit_info D ON b.projcode = d.proj_id and b.pbl_id = d.pbl_id \n" +
			"INNER JOIN rf_loan_released_mbtc_account E ON b.entity_id = e.entity_id AND b.projcode = e.proj_id AND b.pbl_id = e.pbl_id AND b.seq_no::INT = e.seq_no::INT \n" +
			"WHERE b.status_id = 'A' AND COALESCE(E.batch_no, '') <> '' \n" +
			"ORDER BY a.entity_name";
	}

}
