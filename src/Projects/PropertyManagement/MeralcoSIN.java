package Projects.PropertyManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXTextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Projects.BiddingandAwarding._NoticeToProceed;
import components.JTBorderFactory;
import components._ButtonGroup;
import components._JInternalFrame;

public class MeralcoSIN extends _JInternalFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1696231374073396297L;

	static String title = "Assign Meralco Service ID No. (SIN)";
	Dimension frameSize = new Dimension(550, 300);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;

	private JPanel pnlNorth;

	static _JLookup lookupPBL;
	private _JLookup lookupCompany;
	private _JLookup lookupProject;

	private JTextField txtPBL;
	private JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtHouseModel;

	private _JLookup lookupHouseModel;

	private JXTextField txtSin;

	private JButton btnSave;
	private JButton btnCancel;

	//private _ButtonGroup grpNewEdit = new _ButtonGroup();
	
	private JLabel lblClientID;
	private JLabel lblClient;
	private JLabel lblSeq;
	
	public MeralcoSIN() {
		super(title, true, true, true, true);
		initGUI();

	}

	public MeralcoSIN(String title) {
		super(title);
		initGUI();
	}

	public MeralcoSIN(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	private void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(new BorderLayout(5, 5));
				pnlNorth.setBorder(lineBorder);
				pnlNorth.setPreferredSize(new Dimension(550, 200));// XXX
				{
					JPanel pnlCenter = new JPanel();
					pnlNorth.add(pnlCenter, BorderLayout.CENTER);
					pnlCenter.setLayout(new BorderLayout(3, 3));
					pnlCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					{
						JPanel pnlCenterWest = new JPanel();
						pnlCenter.add(pnlCenterWest, BorderLayout.WEST);
						pnlCenterWest.setLayout(null);
						// pnlCenterWest.setBorder(lineBorder);
						pnlCenterWest.setPreferredSize(new Dimension(220, 160));
						{
							JLabel lblCompany = new JLabel("Company");
							pnlCenterWest.add(lblCompany);
							lblCompany.setBounds(0, 0, 120, 22);
						}
						{
							lookupCompany = new _JLookup(null, "Company");
							pnlCenterWest.add(lookupCompany);
							lookupCompany.setReturnColumn(0);
							lookupCompany.setLookupSQL(SQL_COMPANY());
							lookupCompany.setBounds(120, 0, 100, 22);
							lookupCompany.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										txtCompany.setText((String) data[1]);
										lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));

										lookupProject.setValue(null);
										txtProject.setText("");
										lookupPBL.setValue(null);
										lookupHouseModel.setValue(null);
										txtHouseModel.setText("");

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							JLabel lblProject = new JLabel("Project");
							pnlCenterWest.add(lblProject);
							lblProject.setBounds(0, 25, 120, 22);
						}
						{
							lookupProject = new _JLookup(null, "Project", "Please select company.");
							pnlCenterWest.add(lookupProject);
							lookupProject.setReturnColumn(0);
							lookupProject.setBounds(120, 25, 100, 22);
							lookupProject.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {

										String project_name = (String) data[1];
										String project_alias = (String) data[2];

										txtProject.setText(String.format("%s (%s)", project_name, project_alias));

										lookupPBL.setValue(null);
										txtPBL.setText("");
										lblSeq.setText("");
										lookupHouseModel.setValue(null);
										txtHouseModel.setText("");

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
						{
							JLabel lblPBL = new JLabel("PBL");
							pnlCenterWest.add(lblPBL);
							lblPBL.setBounds(0, 50, 120, 22);
						}
						{
							lookupPBL = new _JLookup(null, "PBL");
							pnlCenterWest.add(lookupPBL);
							lookupPBL.setReturnColumn(0);
							lookupPBL.setLookupSQL(sqlClients());
							lookupPBL.setBounds(120, 50, 100, 22);
							lookupPBL.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									
									txtPBL.setText((String) data[1]);
									lblClientID.setText((String) String.format("<html><b>Client ID: %s</b></html>", data[2]));
									lblClient.setText((String) String.format("<html><b>Client Name: %s</b></html>", data[3]));
									lookupHouseModel.setText((String) data[4]);
									txtHouseModel.setText((String) data[5]);
									lblSeq.setText((String) data[7]);
									
									
								}
							});
						}
						{
							JLabel lblHouseModel = new JLabel("House Model");
							pnlCenterWest.add(lblHouseModel);
							lblHouseModel.setBounds(0, 75, 120, 22);
						}
						{
							lookupHouseModel = new _JLookup(null, "House Model");
							pnlCenterWest.add(lookupHouseModel);
							lookupHouseModel.setLookupSQL(_NoticeToProceed.Contractor());
							lookupHouseModel.setReturnColumn(0);
							lookupHouseModel.setEnabled(false);
							lookupHouseModel.setBounds(120, 75, 100, 22);
							lookupHouseModel.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup) event.getSource()).getDataSet();
									if (data != null) {
										txtHouseModel.setText((String) data[1]);

										KEYBOARD_MANAGER.focusNextComponent();
									}
								}
							});
						}
					}
					{
						JPanel pnlCenterCenter = new JPanel();
						pnlCenter.add(pnlCenterCenter, BorderLayout.CENTER);
						pnlCenterCenter.setLayout(new GridLayout(4, 1, 3, 3));
						{
							txtCompany = new JTextField();
							pnlCenterCenter.add(txtCompany);
							txtCompany.setEditable(false);
						}
						{
							txtProject = new JTextField();
							pnlCenterCenter.add(txtProject);
							txtProject.setEditable(false);
						}
						{
							txtPBL = new JTextField();
							pnlCenterCenter.add(txtPBL);
							txtPBL.setEditable(false);
						}
						{
							txtHouseModel = new JTextField();
							pnlCenterCenter.add(txtHouseModel);
							txtHouseModel.setEditable(false);
						}
					}
					{
						JPanel pnlCenterSouth = new JPanel();
						pnlCenter.add(pnlCenterSouth, BorderLayout.SOUTH);
						pnlCenterSouth.setLayout(null);
						pnlCenterSouth.setPreferredSize(new Dimension(600, 87));
						{
							lblClientID = new JLabel("");
							pnlCenterSouth.add(lblClientID);
							lblClientID.setBounds(0, 0, 200, 22);
						}
						{
							lblClient = new JLabel("");
							pnlCenterSouth.add(lblClient);
							lblClient.setBounds(160, 0, 400, 22);
						}
						{
							JPanel pnlSearch = new JPanel(new BorderLayout(5, 5));
							pnlCenterSouth.add(pnlSearch);
							pnlSearch.setBorder(JTBorderFactory.createTitleBorder("MERALCO Service ID No:"));
							pnlSearch.setBounds(0, 25, 520, 60);
							{
								JPanel pnlUploading = new JPanel(new GridLayout(1, 1, 10, 0));
								pnlSearch.add(pnlUploading, BorderLayout.WEST);
								{
									txtSin = new JXTextField();
									pnlUploading.add(txtSin);
									txtSin.setPreferredSize(new java.awt.Dimension(505, 0));
									txtSin.setHorizontalAlignment(JTextField.CENTER);	
								}
								
							}
						}
					}
				}
			}
			{
				JPanel pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
				// pnlSouth.setBorder(lineBorder);
				pnlSouth.setPreferredSize(new Dimension(500, 35));
				{
					lblSeq = new JLabel("");
					pnlSouth.add(lblSeq);
					lblSeq.setVisible(false);
				}
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
			}
		}

		//setComponentsEnabled(false);
		this.grabFocus();

	}// XXX initGUI

	private boolean validateSaving() {
		if (lookupCompany.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Company", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupProject.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select Project", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (lookupPBL.getValue() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select PBL ID.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}if (txtSin.getText() == null) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select SIN No.", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {// XXX action
		String actionCommand = arg0.getActionCommand();

		if (actionCommand.equals("Save")) {
			
			String entity = (String) lblClientID.getText().replace("<html><b>Client ID: ", "").replace("</b></html>", "");
			String seq = (String) lblSeq.getText();
			String pbl = (String) lookupPBL.getValue();

			if (validateSaving()) {
				
				String strSQL = "SELECT sp_save_tag_sin('"+entity+"', "+seq+"::integer, '"+lookupProject.getValue()+"', '"+pbl+"', '"+lookupCompany.getValue()+"', '"+txtSin.getText()+"',  '"+UserInfo.EmployeeCode +"');" ; 
				
				pgSelect db = new pgSelect();
				db.select(strSQL);
					
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Saves.", "Meralco SIN", JOptionPane.INFORMATION_MESSAGE);
					btnCancel.doClick();
			}
		}

		if (actionCommand.equals("Cancel")) {
/*			String selection = grpNewEdit.getActionCommand();
			Document doc = Jsoup.parse(selection);
			selection = doc.body().text();
*/
			lblClient.setText("");
			lblClientID.setText("");
			lblSeq.setText("");
			
			lookupPBL.setText("");
			lookupCompany.setText("");
			lookupProject.setText("");

			txtPBL.setText("");
			txtCompany.setText("");
			txtProject.setText("");
			txtHouseModel.setText("");

			lookupHouseModel.setText("");

			txtSin.setText("");
		}
	}
	private static String sqlClients() {
		return "SELECT a.pbl_id as \"PBL\", a.description as \"Description\", (case when a.pbl_id = c.oth_pbl_id then c.entity_id else b.entity_id end) as \"Client ID\", \n"
				+ "(case when a.pbl_id = c.oth_pbl_id then get_client_name(c.entity_id) else get_client_name(b.entity_id) end) as \"Client\","
				+ "(case when a.pbl_id = c.oth_pbl_id then a.model_id else b.model_id end) as \"Model ID\", (case when a.pbl_id = c.oth_pbl_id then get_model_name(a.model_id) else get_model_name(b.model_id) end) as \"Model Desc\", format('%s-%s-%s'::text, btrim(a.phase::text),"
				+ "btrim(a.block::text), btrim(a.lot::text))::character varying AS \"Ph-Bl-Lt\", b.seq_no::varchar as \"Seq No.\" \n"
				+ "FROM mf_unit_info a \n"
				+ "LEFT JOIN rf_sold_unit b ON a.proj_id = b.projcode AND a.pbl_id = b.pbl_id and b.status_id = 'A'\n"
				+ "LEFT JOIN hs_sold_other_lots c ON a.proj_id = c.proj_id and a.pbl_id = c.oth_pbl_id and c.status_id = 'A' \n"
				+ "WHERE sin_no is null \n"
				+ "ORDER BY get_client_name(b.entity_id), getinteger(a.phase), gettext(a.phase), getinteger(a.block), \n"
				+ "gettext(a.block), getinteger(a.lot), gettext(a.lot), b.seq_no;";
	}
}
