package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.jdesktop.swingx.JXPanel;

import Database.pgSelect;
import Database.pgUpdate;
import Dialogs.dlg_dcrf_attachment_viewer;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import System.DataChangeRequest;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.model_DivHeadDCRF_Checker;

public class DivHead_Dcrf_Checker extends JXPanel implements _GUI,ActionListener {
	
	private static final long serialVersionUID = 4270524386661751581L;
	private JPanel pnlMain,pnlMainCenter,pnlMainSouth; 
	private JButton btnPreview,btnApproved,btnDisApproved,btnAttach;
	static JPanel pnlTrueMain ;
	private JScrollPane scrolldivHead;
	private _JTableMain tbldivHeadDcrfChecker;
	private JList rowdivHeadDep;
	private model_DivHeadDCRF_Checker model_DivHead;

	Integer dcrf_no;
	String department;
	String emp_name;

	private TimerTask tmr;
	private Timer timerExecute;
	
	private Font font11 = FncLookAndFeel.systemFont_Plain.deriveFont(11f);
	private Font font11bold = FncLookAndFeel.systemFont_Bold.deriveFont(11f);

	public DivHead_Dcrf_Checker (){
		initGUI();
	}
	public DivHead_Dcrf_Checker(boolean isDoubleBuffered){
		super(isDoubleBuffered);
		initGUI();

	}
	public DivHead_Dcrf_Checker(LayoutManager layout){
		super (layout);

	}
	public DivHead_Dcrf_Checker(LayoutManager layout,boolean isDoubleBuffered){
		super(layout,isDoubleBuffered);
		initGUI();
	}

	public void initGUI() {
		this.setLayout(new BorderLayout(1,1));
		this.setBackground(new Color(0,0,0,0));

		{
			pnlTrueMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlTrueMain);
			pnlTrueMain.setBorder(JTBorderFactory.createTitleBorder("DCR Monitoring", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			pnlTrueMain.setPreferredSize(new Dimension(400, 200));
			pnlTrueMain.setOpaque(false);
			{
				{
					pnlMain = new JPanel(new BorderLayout(5, 5));
					pnlTrueMain.add(pnlMain,BorderLayout.CENTER);
					pnlMain.setOpaque(false);
					pnlMain.removeAll();
					{
						{
							scrolldivHead = new JScrollPane();
							pnlMain.add(scrolldivHead,BorderLayout.CENTER);
							scrolldivHead.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						}
						{
							model_DivHead = new model_DivHeadDCRF_Checker();
							tbldivHeadDcrfChecker = new _JTableMain(model_DivHead);
							scrolldivHead.setViewportView(tbldivHeadDcrfChecker);
							tbldivHeadDcrfChecker.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							
							tbldivHeadDcrfChecker.getColumnModel().getColumn(0).setPreferredWidth(70);
							tbldivHeadDcrfChecker.getColumnModel().getColumn(1).setPreferredWidth(70);
							tbldivHeadDcrfChecker.getColumnModel().getColumn(2).setPreferredWidth(256);
							tbldivHeadDcrfChecker.getColumnModel().getColumn(3).setPreferredWidth(225);
							
							tbldivHeadDcrfChecker.setEnabled(true);
							tbldivHeadDcrfChecker.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								public void valueChanged(ListSelectionEvent e) {
									try {
										System.out.println("Row: "+tbldivHeadDcrfChecker.getSelectedRow());
										String strStatus = (String) tbldivHeadDcrfChecker.getValueAt(tbldivHeadDcrfChecker.getSelectedRow(), 2);
										
										switch (strStatus) {
											case "PENDING DIV. HEAD APPROVAL":
												Controls(true, true, true, true);
												break;
											case "PENDING DEPT. HEAD APPROVAL":
												Controls(true, true, true, true);
												break;
											default:
												Controls(true, false, false, true);
												break; 
										}	
									} catch (IndexOutOfBoundsException ex) {
										Controls(false, false, false, false);
									}
								}
							});
						}
						{
							rowdivHeadDep = tbldivHeadDcrfChecker.getRowHeader();
							rowdivHeadDep.setModel(new DefaultListModel());
							scrolldivHead.setRowHeaderView(rowdivHeadDep);
							scrolldivHead.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}	
				}
				{
					pnlMainSouth = new JPanel(new GridLayout(1, 5, 5, 5));
					pnlMain.add(pnlMainSouth,BorderLayout.SOUTH);
					pnlMainSouth.setPreferredSize(new Dimension(0, 30));
					{
						{
							btnPreview = new JButton("Preview");
							pnlMainSouth.add(btnPreview);
							btnPreview.setActionCommand("Preview");
							btnPreview.addActionListener(this);	
							btnPreview.setFont(font11);
							btnPreview.setEnabled(false);
						}
						{
							btnApproved = new JButton("Approve");
							pnlMainSouth.add(btnApproved);
							btnApproved.setActionCommand("Approved");
							btnApproved.addActionListener(this);
							btnApproved.setFont(font11);
							btnApproved.setEnabled(false);
						}
						{
							btnDisApproved = new JButton("Disapprove");
							pnlMainSouth.add(btnDisApproved);
							btnDisApproved.setActionCommand("Disapproved");
							btnDisApproved.addActionListener(this);
							btnDisApproved.setFont(font11);
							btnDisApproved.setEnabled(false);
						}
						{
							btnAttach = new JButton("Attachment");
							pnlMainSouth.add(btnAttach);
							btnAttach.setActionCommand("Attachment");
							btnAttach.addActionListener(this);
							btnAttach.setFont(font11);
							btnAttach.setEnabled(false);
						}
						{
							final JLabel lblToggle = new JLabel("Active");
							pnlMainSouth.add(lblToggle, BorderLayout.LINE_END);
							lblToggle.setOpaque(true);
							lblToggle.setFont(font11bold);
							lblToggle.setPreferredSize(new Dimension(100, 0));
							lblToggle.setToolTipText("DCRs Approval Preference. Press to change.");
							if (FncGlobal.GetBoolean("select require_approval \n" + 
									"from mf_division_dcrf_preference\n" + 
									"where emp_code = '"+UserInfo.EmployeeCode+"'")) {
								toggle(lblToggle, true);
							} else {
								toggle(lblToggle, false);
							} 
							lblToggle.setHorizontalAlignment(JLabel.CENTER);
							lblToggle.addMouseListener(new MouseListener() {
								public void mouseReleased(MouseEvent e) {

								}

								public void mousePressed(MouseEvent e) {

								}

								public void mouseExited(MouseEvent e) {

								}

								public void mouseEntered(MouseEvent e) {

								}

								public void mouseClicked(MouseEvent e) {
									pgUpdate dbUpdate = new pgUpdate(); 
									if (lblToggle.getBorder()==BorderFactory.createLoweredSoftBevelBorder()) {
										toggle(lblToggle, false);
										
										dbUpdate.executeUpdate("update mf_division_dcrf_preference \n" + 
												"set require_approval = false \n" + 
												"where emp_code = '"+UserInfo.EmployeeCode+"'", true); 
									} else {
										toggle(lblToggle, true);
										dbUpdate.executeUpdate("update mf_division_dcrf_preference \n" + 
												"set require_approval = true \n" + 
												"where emp_code = '"+UserInfo.EmployeeCode+"'", true);
									}

									dbUpdate.executeUpdate("update rf_dcrf_header f \n" + 
											"set require_div_head = "+(lblToggle.getText().equals("ON")?"true":"false")+" \n" + 
											"from rf_dcrf_header a \n" + 
											"left join mf_department b on a.dept_code = b.dept_code \n" + 
											"left join em_employee c on b.dept_code = c.dept_code and a.created_by = c.emp_code \n" + 
											"left join rf_entity d on c.entity_id = d.entity_id \n" + 
											"left join mf_division e on b.division_code = e.division_code and c.div_code = e.division_code \n" + 
											"where a.app_dept_head_date is not null and a.disapp_dept_head_date is null \n" + 
											"and a.app_div_head_date is null and a.disapp_div_head_date is null \n" +
											"and a.app_vcoo_date is null and a.disapp_vcoo_date is null \n" + 
											"and e.div_head_code = '"+UserInfo.EmployeeCode+"' \n" +
											"and f.dcrf_no = a.dcrf_no; ", true);
									dbUpdate.executeUpdate("insert into mf_audit_trail (system_id, activity, user_code, date_upd, remarks) \n" + 
											"values ('HOME', 'EDIT-DCR APPROVAL PREFERENCE', '"+UserInfo.EmployeeCode+"', now(), 'preference: "+(lblToggle.getText().equals("ON")?"true":"false")+"; ')", true);
									dbUpdate.commit();	
									
									displayValue(model_DivHead, rowdivHeadDep);
								}
							});
						}
					}
				}
				pnlMain.repaint();
				pnlMain.revalidate();
			}
		}
		
		displayValue(model_DivHead, rowdivHeadDep);
		ExecuteUSB();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Preview")){
			preview();
		} else if (e.getActionCommand().equals("Approved")){
			approved();
			this.removeAll();
			initGUI();
		} else if (e.getActionCommand().equals("Disapproved")){
			disapproved();
			this.removeAll();
			initGUI();
		} else if (e.getActionCommand().equals("Attachment")){
			attachment();
		}
	}

	private void displayValue(DefaultTableModel model_DivHead,JList rowdivHeadDep) {
		FncTables.clearTable(model_DivHead);
		DefaultListModel listmod = new DefaultListModel();
		rowdivHeadDep.setModel(listmod);

		String query = "select * \n" + 
				"from view_dcrf_monitoring_div('"+UserInfo.EmployeeCode+"')";
		pgSelect db = new pgSelect();
		db.select(query);
		if(db.isNotNull()){
			for(int x = 0;x<db.getRowCount();x++){
				model_DivHead.addRow(db.getResult()[x]);
				listmod.addElement(model_DivHead.getRowCount());
			}
		}

	}

	private void preview(){	
		System.out.println("dumaan dito");

		if (tbldivHeadDcrfChecker.getSelectedRows().length==1) {
			int row = tbldivHeadDcrfChecker.getSelectedRow();
			dcrf_no= (Integer)model_DivHead.getValueAt(row, 0);
			department = (String)model_DivHead.getValueAt(row, 1);
			emp_name = (String)model_DivHead.getValueAt(row, 2);
			System.out.println("dumaan dito");

			String criteria = "DCRF";		
			String reportTitle = String.format("%s (%s)", DataChangeRequest.title.replace(" Report", ""), criteria.toUpperCase());
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("dcrf_no",dcrf_no);
			FncReport.generateReport("/Reports/rptDCRF_preview_paperless.jasper", reportTitle,DataChangeRequest.company, mapParameters);
		} else {
			JOptionPane.showMessageDialog(null, "Please select a row","Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void ExecuteUSB() {
		tmr = new TimerTask() {
			public void run() {
				displayValue(model_DivHead, rowdivHeadDep);
			}
		};

		timerExecute = new Timer();
		long delay = 0;
		long intevalPeriod = 20 * 1000; 

		timerExecute.scheduleAtFixedRate(tmr, delay, intevalPeriod);
	}

	private void disapproved() {
		if(tbldivHeadDcrfChecker.getSelectedRows().length==1){
			int row = tbldivHeadDcrfChecker.getSelectedRow();
			dcrf_no= (Integer) model_DivHead.getValueAt(row, 0);
			department = (String) model_DivHead.getValueAt(row, 1);
			emp_name = (String) model_DivHead.getValueAt(row, 2);
			System.out.println("dumaan dito");

			pgUpdate db = new pgUpdate();
			String query = "update rf_dcrf_header \n" +
				"set disapp_div_head = '"+UserInfo.EmployeeCode+"', \n" +
				"disapp_div_head_date = now(), \n" +
				"status = 'INACTIVE' \n" +
				"where dcrf_no = '"+dcrf_no+"'";
			db.executeUpdate(query, true);
			db.commit();
			JOptionPane.showMessageDialog(null, "Dcrf has been disapproved", "Success",JOptionPane.INFORMATION_MESSAGE );
		} else {
			JOptionPane.showMessageDialog(null, "Please select a row","Warning", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void approved(){
		
		if(tbldivHeadDcrfChecker.getSelectedRows().length==1){
			int row = tbldivHeadDcrfChecker.getSelectedRow();
			dcrf_no= (Integer)model_DivHead.getValueAt(row, 0);
			department = (String)model_DivHead.getValueAt(row, 1);
			emp_name = (String)model_DivHead.getValueAt(row, 2);
			System.out.println("dumaan dito");
			if(UserInfo.EmployeeCode.equals("900028")) {
				pgUpdate db = new pgUpdate();
				String query = "update rf_dcrf_header \n" +
						"set app_div_head = '"+UserInfo.EmployeeCode+"', app_dept_head = '"+UserInfo.EmployeeCode+"', \n" +
						"app_div_head_date = now(),app_dept_head_date = now(),approved_by ='"+UserInfo.EmployeeCode+"' \n"+
						",date_approved = now() \n" +
						"where dcrf_no = '"+dcrf_no+"'";
				db.executeUpdate(query, true);
				db.commit();
				
				JOptionPane.showMessageDialog(null, "Dcrf has been approved", "Success",JOptionPane.INFORMATION_MESSAGE );
			}
			else {
				pgUpdate db = new pgUpdate();
				String query = "update rf_dcrf_header \n" +
						"set app_div_head = '"+UserInfo.EmployeeCode+"', \n" +
						"app_div_head_date = now() \n" +
						"where dcrf_no = '"+dcrf_no+"'";
				db.executeUpdate(query, true);
				db.commit();
				
				JOptionPane.showMessageDialog(null, "Dcrf has been approved", "Success",JOptionPane.INFORMATION_MESSAGE );
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "Please select a row","Warning", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void attachment() {
		if (tbldivHeadDcrfChecker.getSelectedRows().length==1) {
			int row = tbldivHeadDcrfChecker.getSelectedRow();
			dcrf_no= (Integer)model_DivHead.getValueAt(row, 0);
			department = (String)model_DivHead.getValueAt(row, 1);
			emp_name = (String)model_DivHead.getValueAt(row, 2);
			pgSelect db = new pgSelect();
			
			String query = "select attachment_img from rf_dcrf_attachments where dcrf_no ='"+dcrf_no+"'";
			db.select(query);
			
			if (db.isNotNull()) {
				dlg_dcrf_attachment_viewer dialog = new dlg_dcrf_attachment_viewer(FncGlobal.homeMDI, "Attachments", false,dcrf_no.toString());
				final Toolkit toolkit = Toolkit.getDefaultToolkit();
				final Dimension screenSize = toolkit.getScreenSize();
				
				dialog.setSize(screenSize.width-100, screenSize.height-50);
				dialog.setResizable(false);
				dialog.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "There are no attachments for this request.", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please select a row", "Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void Controls(Boolean blnPreview, Boolean blnApprove, Boolean blnDisapprove, Boolean blnAttachments) {
		btnPreview.setEnabled(blnPreview);
		btnApproved.setEnabled(blnApprove);
		btnDisApproved.setEnabled(blnDisapprove);
		btnAttach.setEnabled(blnAttachments);
	}
	
	private void toggle (JLabel lblTog, Boolean blnSet) {
		if (blnSet) {
			lblTog.setBorder(BorderFactory.createLoweredSoftBevelBorder());
			lblTog.setBackground(Color.GREEN);
			lblTog.setForeground(Color.BLACK);
			lblTog.setText("ON");	
		} else {
			lblTog.setBorder(BorderFactory.createRaisedBevelBorder());
			lblTog.setBackground(Color.RED);
			lblTog.setForeground(Color.WHITE);
			lblTog.setText("OFF");	
		}
	}
}
