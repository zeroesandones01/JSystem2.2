package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncReport;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JInternalFrame;

public class DeletePostedNTCAccount extends _JInternalFrame implements _GUI {
	
	private static final long serialVersionUID = 1162406788303915011L;
	
	JPanel pnlMain;
	JPanel pnlNorth;
	JPanel pnlWest;
	JPanel pnlEast;
	JPanel pnlCenter;
	JPanel pnlSouth;
	
	JLabel lblNorth;
	JLabel lblCenter;
	JLabel lblProject;
	JLabel lblBatch;
	JLabel lblPhase;
	JLabel lblCompany;
	JLabel lblQualificationBatch;
	JLabel lblListFormat;
	JLabel lblLotOnly;
	
	_JLookup lookupProject;
	_JLookup lookupBatch;
	_JLookup lookupPhase;
	_JLookup lookupCompany;
	_JLookup lookupQualificationBatch;
	
	JCheckBox chkboxListFormat;
	JCheckBox chkboxLotOnly;
	
	static String title = "Delete Posted NTC Account";
	Dimension frameSize = new Dimension(700, 195);// 510, 250
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	public DeletePostedNTCAccount() {
		super(title, false, true, false, true);
		initGUI();
	}

	public DeletePostedNTCAccount(String title) {
		super(title, false, true, false, true);
		initGUI();
	}

	public DeletePostedNTCAccount(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		{
			pnlMain = new JPanel();
			this.getContentPane().add(pnlMain, BorderLayout.CENTER);
			pnlMain.setLayout(new BorderLayout(5, 5));
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setLayout(null);
				pnlNorth.setBorder(lineBorder);
				pnlNorth.setPreferredSize(new Dimension(750, 120));// XXX
				{
					lblNorth = new JLabel("Company");
					pnlNorth.add(lblNorth);
					lblNorth.setHorizontalAlignment(JLabel.LEFT);
					lblNorth.setBounds(10, 10, 120, 22);
				}
				{
					lookupCompany = new _JLookup(null, "Company");
					pnlNorth.add(lookupCompany);
					lookupCompany.setReturnColumn(0);
					lookupCompany.setLookupSQL(SQL_COMPANY());
					lookupCompany.setBounds(90, 10, 120, 22);
					lookupCompany.addLookupListener(new LookupListener() {
						public void lookupPerformed(LookupEvent event) {
							Object[] data = ((_JLookup) event.getSource()).getDataSet();
							if (data != null) {
								lblCompany.setText(String.format("[ %s ]", (String) data[1]));
								lookupProject.setLookupSQL(SQL_PROJECT((String) data[0]));

								//lookupProject.setValue(null);
								//txtProject.setText("");
								//listPhase.setModel(new DefaultComboBoxModel(new ArrayList<_JCheckListItem>().toArray()));

								//generateContractNo();
								KEYBOARD_MANAGER.focusNextComponent();
							}
						}
					});
				}
				{
					lblCompany = new JLabel("[ ]");
					pnlNorth.add(lblCompany);
					lblCompany.setHorizontalAlignment(JLabel.LEFT);
					lblCompany.setBounds(215, 10, 300, 22);
				}
					{
						lblNorth = new JLabel("Project");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 35, 120, 22);
					}
					{
						lookupProject = new _JLookup(null, "Project", "Please select company.");
						pnlNorth.add(lookupProject);
						lookupProject.setReturnColumn(0);
						lookupProject.setBounds(90, 35, 120, 22);
						lookupProject.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									lblProject.setText(String.format("[ %s ]", (String) data[1]));
									lookupPhase.setLookupSQL(SQL_PHASE((String) data[0]));

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblProject = new JLabel("[ ]");
						pnlNorth.add(lblProject);
						lblProject.setHorizontalAlignment(JLabel.LEFT);
						lblProject.setBounds(215, 35, 250, 22);
					}
					{
						lblNorth = new JLabel("Phase");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 60, 120, 22);
					}
					{
						lookupPhase = new _JLookup(null, "Phase", "Please select project.");
						pnlNorth.add(lookupPhase);
						lookupPhase.setReturnColumn(0);
						lookupPhase.setBounds(90, 60, 120, 22);
						lookupPhase.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {

									lblPhase.setText(String.format("[ %s ]", (String) data[1]));
									lookupBatch.setLookupSQL(SQL_NTCBATCHID());

									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
					{
						lblPhase = new JLabel("[ ]");
						pnlNorth.add(lblPhase);
						lblPhase.setHorizontalAlignment(JLabel.LEFT);
						lblPhase.setBounds(215, 60, 250, 22);
					}
					{
						lblNorth = new JLabel("NTC Batch #");
						pnlNorth.add(lblNorth);
						lblNorth.setHorizontalAlignment(JLabel.LEFT);
						lblNorth.setBounds(10, 85, 120, 22);
					}
					{
						
						lookupBatch = new _JLookup(null, "NTC Batch #");
						pnlNorth.add(lookupBatch);
						lookupBatch.setReturnColumn(0);
						lookupBatch.setBounds(90, 85, 120, 22);
						lookupBatch.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup) event.getSource()).getDataSet();
								if (data != null) {
									KEYBOARD_MANAGER.focusNextComponent();
								}
							}
						});
					}
				}
				{
					pnlSouth = new JPanel();
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setLayout(new GridLayout(1, 10, 3, 3));
					//pnlSouth.setBorder(lineBorder);
					pnlSouth.setPreferredSize(new Dimension(500, 30));// XXX	
					{
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
						pnlSouth.add(Box.createHorizontalBox());
					}
					{
						JButton btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview);
						btnPreview.setActionCommand("Preview");
						btnPreview.setMnemonic(KeyEvent.VK_P);
						btnPreview.addActionListener(this);
					}
					{
						JButton btnDelete = new JButton("Delete");
						pnlSouth.add(btnDelete);
						btnDelete.setActionCommand("Delete");
						btnDelete.setMnemonic(KeyEvent.VK_P);
						btnDelete.addActionListener(this);
					}
					{
						JButton btnCancel = new JButton("Cancel");
						pnlSouth.add(btnCancel);
						btnCancel.setActionCommand("Cancel");
						btnCancel.setMnemonic(KeyEvent.VK_C);
						btnCancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								int response = JOptionPane.showConfirmDialog(DeletePostedNTCAccount.this.getTopLevelAncestor(),"Are you sure you want to Clear all fields?   ",
										"Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
								if (response == JOptionPane.YES_OPTION) {
									lookupProject.setText("...");
									refreshTO();
								}
							}
						});
					}
				}
		}

	}
	private void refreshTO() {
		
		if (lookupProject.getText().equals(" ")) {
			lookupCompany.setValue("");
			lookupProject.setValue("");
			lookupPhase.setValue("");
			lblCompany.setText("[ ]");
			lblProject.setText("[ ]");
			lblPhase.setText("[ ]");
			lookupBatch.setValue("");
			
		}
	} // refreshTO()
	public void actionPerformed(ActionEvent arg0) {
		String actionCommand = arg0.getActionCommand();
		
		if(actionCommand.equals("Preview")){

			Map<String, Object> mapParameters = new HashMap<String, Object>();
			
			mapParameters.put("projcode", lookupProject.getValue());
			mapParameters.put("phase", lookupPhase.getValue());
			mapParameters.put("batch_no", lookupBatch.getValue());
			mapParameters.put("proj_name", lblProject.getText());
			
					
			FncReport.generateReport("/Reports/rptNoticeToConstruct_selected.jasper", "Notice to Construct List", mapParameters);
		}
		
		if(actionCommand.equals("Delete")){
			String batchno = (String) lookupBatch.getValue();
			String emp_code = (String) UserInfo.EmployeeCode;
			
			int response = JOptionPane.showConfirmDialog(DeletePostedNTCAccount.this.getTopLevelAncestor(),"Are you sure you?   ",
					"Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
			
				String strSQL = "SELECT sp_update_tagaccounts_ntc('"+emp_code+"', '"+batchno+"');" ; 
			
				pgSelect db = new pgSelect();
				db.select(strSQL);
			
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Delete.", "DELETED", JOptionPane.INFORMATION_MESSAGE);
			
				lookupProject.setText(" ");
				refreshTO();
			} else {
				
			}
		}
	}
	
	
	
	public static String SQL_NTCBATCHID() {//XXX Batch ID
		String SQL = "SELECT ntc_batchno as \"Batch No.\"\n" + 
				"FROM rf_buyer_status \n" + 
				"WHERE status_id = 'A'" +
				"GROUP BY ntc_batchno\n" + 
				"ORDER BY ntc_batchno DESC;";
		return SQL;
	}
}
