package Reports.BiddingandAwarding;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Projects.BiddingandAwarding._NoticeToProceed;
import components._JCheckListItem;
import components._JCheckListRenderer;
import components._JInternalFrame;
import components._JTagLabel;
public class ContractorsANDF extends _JInternalFrame implements ActionListener, MouseListener, KeyListener, AncestorListener  {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1696231374073396297L;

	static String title = "Contractor's ANDF Monitoring";
	Dimension frameSize = new Dimension(450, 175);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth, pnlCenter;
	private JPanel pnlSouth;

	private JLabel lblNTPNo, lblCompany, lblProject;

	private _JLookup lookupCompany, lookupProject, lookupNTPNo, lookupNTPType, lookupContractor;

	private JTextField txtCompany, txtProject, txtContractNo, txtNTPType;

	private JButton btnPreview, btnCancel;

	String company;
	String company_logo;	
	String co_id 		= "";
	String proj_id 		= "";	
	String proj_name 	= "";
	
	public ContractorsANDF() {
		super(title, true, true, true, true);
		initGUI();

		pgSelect db = new pgSelect();
		db.select("SELECT MAX(ntp_no) FROM co_ntp_header;");

		/*if(db.isNotNull() && !((String)db.getResult()[0][0]).trim().equals("")){
			String ntp_no = (String) db.getResult()[0][0];
			System.out.printf("NTP #: %s", ntp_no);
			if(ntp_no != null){
				lookupNTPNo.setValue(ntp_no);

				//displayContractInfo(ntp_no);
				//btnState(true, true, false, false, false);
			}
		}*/
		
	}

	public ContractorsANDF(String title) {
		super(title);
		initGUI();
	}

	public ContractorsANDF(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	private void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new Dimension(450, 175));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			//pnlMain.setBackground(Color.gray);
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(5, 5));
				{
					pnlCenter = new JPanel();
					pnlNorth.add(pnlCenter, BorderLayout.CENTER);
					pnlCenter.setLayout(new BorderLayout(3, 3));
					pnlCenter.setPreferredSize(new Dimension(450, 115));
					{
						JPanel pnlCenterNorth = new JPanel();
						pnlCenter.add(pnlCenterNorth, BorderLayout.NORTH);
						pnlCenterNorth.setLayout(null);
						pnlCenterNorth.setBorder(components.JTBorderFactory.createTitleBorder("Company"));// XXX
						pnlCenterNorth.setPreferredSize(new Dimension(445, 115));
						{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
							{
								lblCompany = new JLabel("Company");
								pnlCenterNorth.add(lblCompany);
								lblCompany.setBounds(10, 30, 100, 22);
							}
							{
								lookupCompany = new _JLookup(null, "Company");
								pnlCenterNorth.add(lookupCompany);
								lookupCompany.setReturnColumn(0);
								lookupCompany.setLookupSQL(SQL_COMPANY());
								lookupCompany.setBounds(75, 30, 80, 22);
								lookupCompany.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											
											String co_id = lookupCompany.getValue();
											String company = (String) data[1];
 
											txtCompany.setText((String) company);
											lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));

											lookupProject.setValue(null);
											txtProject.setText("");
										
											//generateContractNo();
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								txtCompany = new JTextField();
								pnlCenterNorth.add(txtCompany);
								txtCompany.setBounds(160, 30, 270, 22);
								txtCompany.setEditable(false);
							}
							{
								lblProject = new JLabel("Project");
								pnlCenterNorth.add(lblProject);
								lblProject.setBounds(10, 55, 100, 22);
							}
							{
								lookupProject = new _JLookup(null, "Project", "Please select company.");
								pnlCenterNorth.add(lookupProject);
								lookupProject.setReturnColumn(0);
								lookupProject.setBounds(75, 55, 80, 22);
								lookupProject.setLookupSQL(SQL_PROJECT());
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											
											String co_id = lookupCompany.getValue();
											String project_id = (String) data[0];
											String project_name = (String) data[1];
											String project_alias = (String) data[2];

											txtProject.setText(String.format("%s (%s)", project_name, project_alias));


											//generateContractNo();
											KEYBOARD_MANAGER.focusNextComponent();
										}
									}
								});
							}
							{
								txtProject = new JTextField();
								pnlCenterNorth.add(txtProject);
								txtProject.setBounds(160, 55, 270, 22);
								txtProject.setEditable(false);
							}
							/*{
								lblNTPNo = new JLabel("NTP No.");
								pnlCenterNorth.add(lblNTPNo);
								lblNTPNo.setName("lblNTPNo");
								lblNTPNo.setBounds(10, 80, 100, 22);
							}
							{
								lookupNTPNo = new _JLookup(null, "NTP No.");
								pnlCenterNorth.add(lookupNTPNo);
								lookupNTPNo.setReturnColumn(0);
								lookupNTPNo.setName("lookupNTPNo");
								lookupNTPNo.setBounds(75, 80, 80, 22);
								lookupNTPNo.setLookupSQL(_NoticeToProceed.NTPNo());
								lookupNTPNo.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {

											String ntp_no = lookupNTPNo.getValue();
											String ntp = (String) data[0];
											String contractor = (String) data[1];
											String contract_no = (String) data[2];

											txtContractNo.setText(String.format("%s (%s)", contractor, contract_no));

											
										}
									}
								});
							}
							{
								txtContractNo = new JTextField();
								pnlCenterNorth.add(txtContractNo);
								txtContractNo.setBounds(160, 80, 300, 22);
								txtContractNo.setEditable(false);
							}*/
						}
					}
				}//end of pnlNorth

				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 2, 5, 5));
				pnlSouth.setPreferredSize(new Dimension(450, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_P);
					btnCancel.addActionListener(this);
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("Preview")){
			pgSelect db = new pgSelect();
			db.select("select company_logo from mf_company where co_id = '02';");
			
			String company_logo = null;
			if(db.isNotNull()){
				company_logo = (String) db.getResult()[0][0];
			}
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");	
			mapParameters.put("proj_id", lookupProject.getText());
			mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ company_logo));
			mapParameters.put("prepared_by", UserInfo.Alias);

			FncReport.generateReport("/Reports/rptContractorsANDF.jasper", "ANDF per Contractor per House Model", mapParameters);
		}

		if (e.getActionCommand().equals("Cancel")){ cancel(); }
	}
	
	private void cancel(){		
		lookupCompany.setValue("");
		txtCompany.setText("");
		
		lookupProject.setValue("");
		txtProject.setText("");
			
		btnPreview.setEnabled(true);	
		
		co_id 	= "";
		proj_id 	= "";	
		proj_name 	= "";
	}
	
	@Override
	public void ancestorAdded(AncestorEvent event) {
		lookupCompany.requestFocus();
	}

	@Override
	public void ancestorMoved(AncestorEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ancestorRemoved(AncestorEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	//end
}
