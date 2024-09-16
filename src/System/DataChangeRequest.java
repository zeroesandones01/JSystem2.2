package System;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import tablemodel.modelDCRF_change_list;
import Database.pgSelect;
import Database.pgUpdate;
import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup._JLookupTable;

import com.toedter.calendar.JTextFieldDateEditor;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;
import components._JTagLabel;

public class DataChangeRequest extends _JInternalFrame implements _GUI, ActionListener, MouseListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	public static String title = "Data Change Request";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlTable;
	private JPanel pnlSubTable;
	private JPanel pnlChangeDetails;
	private JPanel pnlSouth;
	private JPanel pnlSouthCenterb;
	private JPanel pnlDRF;
	private JPanel pnlDRF_a;
	private JPanel pnlDRF_a1;
	private JPanel pnlDRF_a2;
	private JPanel pnlOrigin_a;
	private JPanel pnlOrigin_b;
	private JPanel pnlOrigin_b1;
	private JPanel pnlOrigin_b2;
	private JPanel pnlOrigin;
	private JPanel pnlOriginTime;
	private JPanel pnlEval;
	private JPanel pnlIT;
	private JPanel pnlIT_a;
	private JPanel pnlIT_b;
	private JPanel pnlOriginTime_a;
	private JPanel pnlOriginTime_a1;
	private JPanel pnlOriginTime_a2;
	private JPanel pnlOriginTime_a2_1;
	private JPanel pnlIT_b1;
	private JPanel pnlIT_b2;
	private JPanel pnlIT_b3;
	private JPanel pnlIT_b3_a;
	private JPanel pnlIT_b3_a1;
	private JPanel pnlIT_b3_a2;
	private JPanel pnlIT_b3_a2_1;
	private JPanel pnlError;
	private JPanel pnlError_a;
	private JPanel pnlError_a_a1;
	private JPanel pnlError_a2;
	private JPanel pnlError_b;
	private JPanel pnlError_sub1;
	private JPanel pnlError_sub2;
	private JPanel pnlError_sub2a;
	private JPanel pnlError_sub2b;
	private JPanel pnlEval_sub1;
	private JPanel pnlEval_sub2;

	private _JLookup lookupDCRF;
	private _JLookup lookupDept;

	private JButton btnCancel;

	private JLabel lblDCRF_no;
	private JLabel lblDept;
	private JLabel lblDateNeeded;
	private JLabel lblErrorType;
	private JLabel lblDateReceived;
	private JLabel lblApprovedBy;
	private JLabel lblDtePrepared;
	private JLabel lblInCharge;
	private JLabel lblReceivedBy;
	private JLabel lblPreparedBy;
	private JLabel lblDateFixed;
	private JLabel lblDateReviewed;
	private JLabel lblReason;
	private JLabel lblReviewedBy;

	private _JTagLabel tagDCRF_status;
	private _JTagLabel tagPreparedBy;	
	private _JTagLabel tagReceivedBy;
	private _JTagLabel tagDept;
	private _JTagLabel tagApprovedBy;
	private _JTagLabel tagFixedby;
	private _JTagLabel tagConformedBy;

	private _JScrollPaneMain scrollChangeDetails;
	private _JScrollPaneTotal scrollChangeDetailstotal;
	private _JTableMain tblChangeDetails;
	private JList rowHeaderChangeDetails;
	private _JTableTotal tblChangeDetails_total;
	private JComboBox cmbError;
	private JScrollPane scpEval;
	private JScrollPane scpAdminRemarks;
	private modelDCRF_change_list modelChangeDetails;
	private modelDCRF_change_list modelChangeDetails_total;

	private JTextArea txtUserRemark;
	private JTextArea txtAdminRemarks;	

	private JXTextField txtOtherError;
	private JXTextField txtPreparedBy;
	private JXTextField txtReceivedBy;
	private JXTextField txtApproveBy;
	private JXTextField txtFixedBy;
	private JXTextField txtConformedBy;	
	private JXTextField txtReason;

	private JButton btnCreateNew;
	private JButton btnReceive;
	private JButton btnApprove;
	private JButton btnFix;
	private JButton btnDelete;
	private JButton btnPreview;
	private JButton btnSave;

	private _JDateChooser dtePrepared;
	private _JDateChooser dteNeeded;
	private _JDateChooser dteReceived;
	private _JDateChooser dteApproved;	
	private _JDateChooser dteFixed;
	private _JDateChooser dteConformed;
	private String Head;

	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	String co_id 	= "";		
	public static String company 	= "";
	String pbl_id 	= "";
	public static String proj_id 	= "";
	String status   = "";
	String entity_id = "";
	String appVcoo = "";
	static Integer dcrf_no = null;
	String dept_id  = "";
	private JButton btnConform;
	private JPopupMenu menu;
	private JMenuItem mniViewAllPendingRequest;
	String prep_by = "";
	private JSplitPane splitPanel_B;
	private JPanel pnlChangeDetails_split;
	private JPopupMenu menu2;
	private JMenuItem mniRemoveLine;
	private JMenuItem mniEditDatePrep;
	private JMenuItem mniReturn;

	private JXPanel panSurvey;
	private JCheckBox[] chkDept; 
	String strDept = ""; 

	private Font fontPlainSanSerNine = new Font("SansSerif", Font.PLAIN, 9);

	private JLabel lblAttachment; 
	private JButton btnAttach; 
	private ArrayList<String> arrAttachment = new ArrayList<String>();
	public static JDialog dialog; 

	public DataChangeRequest() {
		super(title, true, true, true, true);
		initGUI();
	}

	public DataChangeRequest(String title) {
		super(title);

	}

	public DataChangeRequest(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);

	}


	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(940, 567));
		this.setBounds(0, 0, 940, 567);

		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			menu = new JPopupMenu("Popup");
			mniViewAllPendingRequest = new JMenuItem("View All Pending Requests");
			mniEditDatePrep = new JMenuItem("Edit Date Prepared");
			mniReturn = new JMenuItem("Return to Previous Status");
			menu.add(mniViewAllPendingRequest);
			menu.add(mniEditDatePrep);
			menu.add(mniReturn);
			mniViewAllPendingRequest.setEnabled(true);
			mniViewAllPendingRequest.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
			mniViewAllPendingRequest.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					previewViewAllPendingRequests();			
				}
			});			
			mniReturn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){					

					String m = null;
					String n = null;
					if (status.equals("COMPLETED"))	{
						m = sql_getUserID("conformed_by");	n = "conformed"; 
					}else if (status.equals("FIXED")){
						m = sql_getUserID("fixed_by");		
						n = "fixed";
					}else if (status.equals("APPROVED")){
						m = sql_getUserID("approved_by");	
						n = "approved";
					}else if (status.equals("TAGGED")){
						m = sql_getUserID("noted_by");		
						n = "checked";
					}else if (status.equals("RECEIVED")){
						m = sql_getUserID("received_by");	
						n = "checked";
					}else{
						m = "x";
						n = "created";
					}
					
					System.out.printf("Value of m: %s%n", m);
					System.out.printf("value of UserInfo: %s%n", UserInfo.EmployeeCode);
					System.out.printf("Value of comparison! : %s%n", !UserInfo.EmployeeCode.equals(m));
					System.out.printf("Value of comparison false: %s%n", UserInfo.EmployeeCode.equals(m)== false);
					System.out.printf("Value of is Admin: %s%n", UserInfo.ADMIN);
					
					if (UserInfo.EmployeeCode.equals(m) || UserInfo.ADMIN){
							String new_status = "";

							if(!status.equals("DELETED")||!status.equals("INACTIVE")||!status.equals("COMPLETED")||!status.equals("ACTIVE"))
							{
								if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to return the previous DCRF?", "Confirmation", 
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

									if (status.equals("COMPLETED")){new_status = "FIXED";}
									if (status.equals("FIXED")){new_status = "RECEIVED";}
									if (status.equals("APPROVED")){new_status = "ACTIVE";}
									if (status.equals("TAGGED")){new_status = "ACTIVE";}
									if (status.equals("RECEIVED")){new_status = "APPROVED";}

									pgUpdate db = new pgUpdate();				
									returnDCRFstatus(db, new_status);					
									db.commit();

									JOptionPane.showMessageDialog(getContentPane(),"DCRF successfully returned to previous status.","Information",JOptionPane.INFORMATION_MESSAGE);
									setDCRF_header();			
								}
							}
					}else{
						JOptionPane.showMessageDialog(getContentPane(), "Only the person who " + n + " this DCRF \n" +
								"is allowed to return.", "Warning", 
								JOptionPane.WARNING_MESSAGE);
					}
				}
			});
			mniEditDatePrep.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					String user = UserInfo.EmployeeCode;

					if (!prep_by.equals(user))
					{
						{JOptionPane.showMessageDialog(getContentPane(), "Only the person who prepared this DCRF \n" +
								"is allowed to edit.", "Warning", 
								JOptionPane.WARNING_MESSAGE);}	
					}
					else 
					{
						lblDCRF_no.setEnabled(false);
						lookupDCRF.setEnabled(false);
						tagDCRF_status.setEnabled(false);	

						lblDept.setEnabled(true);	
						lookupDept.setEnabled(false);	
						tagDept.setEnabled(true);	

						enableButtons(false, true, false, false, false, false, false, true, true);
						btnSave.setText("Save");
						btnSave.setActionCommand("Update_user");

						dtePrepared.setDate(Calendar.getInstance().getTime());
						dtePrepared.setEnabled(true);
						dteNeeded.setEnabled(true);				
					}					
				}
			});
		}	
		{
			menu2 = new JPopupMenu("Popup");
			mniRemoveLine = new JMenuItem("Remove DCRF line");
			menu2.add(mniRemoveLine);
			mniRemoveLine.setEnabled(true);
			mniRemoveLine.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
			mniRemoveLine.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					deleteDCRF_row();			
				}
			});
		}	
		{ //start of Company

			pnlDRF = new JPanel(new BorderLayout(0, 0));
			pnlMain.add(pnlDRF, BorderLayout.NORTH);			
			pnlDRF.setPreferredSize(new java.awt.Dimension(928, 181));	
			pnlDRF.setBorder(lineBorder);
			pnlDRF.addMouseListener(new PopupTriggerListener_panel2());

			{
				//company
				pnlDRF_a = new JPanel(new BorderLayout(5, 5));
				pnlDRF.add(pnlDRF_a, BorderLayout.NORTH);	
				pnlDRF_a.setPreferredSize(new java.awt.Dimension(926, 36));	
				pnlDRF_a.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));	
				pnlDRF_a.setBorder(lineBorder);
				{
					{
						pnlDRF_a1 = new JPanel(new GridLayout(1, 2, 5, 5));
						pnlDRF_a.add(pnlDRF_a1, BorderLayout.WEST);	
						pnlDRF_a1.setPreferredSize(new java.awt.Dimension(207, 30));
						pnlDRF_a1.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
						{
							{
								lblDCRF_no = new JLabel("DCRF No.", JLabel.TRAILING);
								pnlDRF_a1.add(lblDCRF_no);
								lblDCRF_no.setBounds(8, 11, 62, 12);
								lblDCRF_no.setPreferredSize(new java.awt.Dimension(115, 25));
								lblDCRF_no.setFont(fontPlainSanSerNine);
							}
							{
								lookupDCRF = new _JLookup(null, "DCRF No.",0,2);
								pnlDRF_a1.add(lookupDCRF);
								lookupDCRF.setLookupSQL(getDCRF_no());
								lookupDCRF.setReturnColumn(0);
								lookupDCRF.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup)event.getSource()).getDataSet();

										if (data != null) {
											FncSystem.out("Display SQL for lookup DCRF", lookupDCRF.getLookupSQL());

											dcrf_no = Integer.parseInt(data[0].toString());	
											String status = (String) data[4];		
											tagDCRF_status.setTag(status);

											Head = (String) data[12];

											setDCRF_header();

											btnAttach.setEnabled(true);
										}
									}
								});
							}
						}
					}
					{
						pnlDRF_a2 = new JPanel(new GridLayout(1, 1, 5, 5));
						pnlDRF_a.add(pnlDRF_a2, BorderLayout.CENTER);	
						pnlDRF_a2.setPreferredSize(new java.awt.Dimension(423, 20));	
						pnlDRF_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
						pnlDRF_a2.addMouseListener(new PopupTriggerListener_panel2());
						{
							tagDCRF_status = new _JTagLabel("[ ]");
							pnlDRF_a2.add(tagDCRF_status);
							tagDCRF_status.setBounds(209, 27, 700, 22);
							tagDCRF_status.setEnabled(false);	
							tagDCRF_status.setPreferredSize(new java.awt.Dimension(27, 33));
							tagDCRF_status.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}	
					}
					{
						JXPanel panAttachment = new JXPanel(new BorderLayout(5, 5)); 
						pnlDRF_a.add(panAttachment, BorderLayout.LINE_END); 
						panAttachment.setPreferredSize(new Dimension(100, 0));
						panAttachment.setBorder(new EmptyBorder(5, 0, 5, 0));
						{
							btnAttach = new JButton("Attachments");
							panAttachment.add(btnAttach, BorderLayout.CENTER); 
							btnAttach.setFont(fontPlainSanSerNine);
							btnAttach.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									dialog = new JDialog(FncGlobal.homeMDI, "DCRF Monitoring", false); 
									dialog.setLayout(new BorderLayout(0, 0));
									dialog.setSize(350, 300);
									dialog.setResizable(false);

									DCRFAttachments dcrfMon = new DCRFAttachments(lookupDCRF.getValue());
									dialog.add(dcrfMon, BorderLayout.CENTER);

									final Toolkit toolkit = Toolkit.getDefaultToolkit();
									final Dimension screenSize = toolkit.getScreenSize();
									int x = 0;
									int y = 0;
									
									if(UserInfo.EmployeeCode.equals("900876") || UserInfo.EmployeeCode.equals("900562")) {
										x = (screenSize.width - dialog.getWidth()) / 4;
										y = (screenSize.height - dialog.getHeight()) / 4;
									}else {
										x = (screenSize.width - dialog.getWidth()) / 2;
										y = (screenSize.height - dialog.getHeight()) / 2;
									}
									

									dialog.setLocation(x, y);
									dialog.setVisible(true);

								}
							});
							btnAttach.setEnabled(false);
						}

					}
				}

			}			
			{
				pnlOrigin = new JPanel(new BorderLayout(5, 5));
				pnlDRF.add(pnlOrigin, BorderLayout.CENTER);	
				pnlOrigin.setPreferredSize(new java.awt.Dimension(926, 103));	
				pnlOrigin.setBorder(lineBorder);

				{
					pnlOrigin_a = new JPanel(new GridLayout(4, 1, 0,0));
					pnlOrigin.add(pnlOrigin_a, BorderLayout.WEST);	
					pnlOrigin_a.setPreferredSize(new java.awt.Dimension(95, 40));	
					pnlOrigin_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblDept = new JLabel("Department", JLabel.TRAILING);
						pnlOrigin_a.add(lblDept);
						lblDept.setEnabled(false);	
						lblDept.setPreferredSize(new java.awt.Dimension(100, 40));
						lblDept.setFont(fontPlainSanSerNine);
					}	
					{
						lblPreparedBy = new JLabel("Prepared by", JLabel.TRAILING);
						pnlOrigin_a.add(lblPreparedBy);
						lblPreparedBy.setEnabled(false);	
						lblPreparedBy.setPreferredSize(new java.awt.Dimension(100, 40));
						lblPreparedBy.setFont(fontPlainSanSerNine);
					}
					{
						lblApprovedBy = new JLabel("Approved by", JLabel.TRAILING);
						pnlOrigin_a.add(lblApprovedBy);
						lblApprovedBy.setEnabled(false);	
						lblApprovedBy.setPreferredSize(new java.awt.Dimension(100, 40));
						lblApprovedBy.setFont(fontPlainSanSerNine);
					}	
					{
						lblReceivedBy = new JLabel("Received by", JLabel.TRAILING);
						pnlOrigin_a.add(lblReceivedBy);
						lblReceivedBy.setEnabled(false);	
						lblReceivedBy.setPreferredSize(new java.awt.Dimension(100, 40));
						lblReceivedBy.setFont(fontPlainSanSerNine);
					}	

					pnlOrigin_b = new JPanel(new BorderLayout(5, 5));
					pnlOrigin.add(pnlOrigin_b, BorderLayout.CENTER);	
					pnlOrigin_b.setPreferredSize(new java.awt.Dimension(814, 40));	
					pnlOrigin_b.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

					{
						pnlOrigin_b1 = new JPanel(new GridLayout(4, 1, 4,4));
						pnlOrigin_b.add(pnlOrigin_b1, BorderLayout.WEST);	
						pnlOrigin_b1.setPreferredSize(new java.awt.Dimension(101, 101));					

						{
							lookupDept = new _JLookup(null, "Department", 2, 2);
							pnlOrigin_b1.add(lookupDept);
							lookupDept.setBounds(20, 27, 20, 25);
							lookupDept.setReturnColumn(0);
							lookupDept.setEnabled(false);	
							lookupDept.setFilterName(true);	
							lookupDept.setPreferredSize(new java.awt.Dimension(137, 27));

							lookupDept.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent event) {
									Object[] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){	

										String dept_name = (String) data[1];
										tagDept.setTag(dept_name);
										lblErrorType.setEnabled(true);
										lblReason.setEnabled(true);	
										cmbError.setEnabled(true);
										txtUserRemark.setEnabled(true);
										txtUserRemark.setEditable(true);
										txtReason.setEnabled(true);	
										modelChangeDetails.setEditable(true);

									}
								}
							});

						}

						{
							txtPreparedBy = new JXTextField("");
							pnlOrigin_b1.add(txtPreparedBy);
							txtPreparedBy.setEnabled(false);	
							txtPreparedBy.setBounds(120, 25, 300, 22);	
							txtPreparedBy.setHorizontalAlignment(JTextField.CENTER);
						}
						{
							txtApproveBy = new JXTextField("");
							pnlOrigin_b1.add(txtApproveBy);
							txtApproveBy.setEnabled(false);	
							txtApproveBy.setBounds(120, 25, 300, 22);	
							txtApproveBy.setHorizontalAlignment(JTextField.CENTER);
						}
						{
							txtReceivedBy = new JXTextField("");
							pnlOrigin_b1.add(txtReceivedBy);
							txtReceivedBy.setEnabled(false);	
							txtReceivedBy.setBounds(120, 25, 300, 22);	
							txtReceivedBy.setHorizontalAlignment(JTextField.CENTER);
						}

					}	
					{
						pnlOrigin_b2 = new JPanel(new GridLayout(4, 1, 5, 5));
						pnlOrigin_b.add(pnlOrigin_b2, BorderLayout.CENTER);	
						pnlOrigin_b2.setPreferredSize(new java.awt.Dimension(412, 123));	
						pnlOrigin_b2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

						{
							tagDept = new _JTagLabel("[ ]");
							pnlOrigin_b2.add(tagDept);
							tagDept.setBounds(209, 27, 700, 22);
							tagDept.setEnabled(false);	
							tagDept.setPreferredSize(new java.awt.Dimension(27, 33));
							tagDept.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}
						{
							tagPreparedBy = new _JTagLabel("[ ]");
							pnlOrigin_b2.add(tagPreparedBy);
							tagPreparedBy.setBounds(209, 27, 700, 22);
							tagPreparedBy.setEnabled(false);	
							tagPreparedBy.setPreferredSize(new java.awt.Dimension(27, 33));
							tagPreparedBy.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}
						{
							tagApprovedBy = new _JTagLabel("[ ]");
							pnlOrigin_b2.add(tagApprovedBy);
							tagApprovedBy.setBounds(209, 27, 700, 22);
							tagApprovedBy.setEnabled(false);	
							tagApprovedBy.setPreferredSize(new java.awt.Dimension(27, 33));
							tagApprovedBy.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}
						{
							tagReceivedBy = new _JTagLabel("[ ]");
							pnlOrigin_b2.add(tagReceivedBy);
							tagReceivedBy.setBounds(209, 27, 700, 22);
							tagReceivedBy.setEnabled(false);	
							tagReceivedBy.setPreferredSize(new java.awt.Dimension(27, 33));
							tagReceivedBy.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
						}


						pnlOriginTime = new JPanel(new BorderLayout(5, 5));
						pnlOrigin_b.add(pnlOriginTime, BorderLayout.EAST);	
						pnlOriginTime.setPreferredSize(new java.awt.Dimension(304, 123));	
						pnlOriginTime.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						pnlOriginTime.addMouseListener(new PopupTriggerListener_panel2());

						{
							pnlOriginTime_a = new JPanel(new BorderLayout(0,0));
							pnlOriginTime.add(pnlOriginTime_a, BorderLayout.EAST);	
							pnlOriginTime_a.setPreferredSize(new java.awt.Dimension(289, 119));								

							{
								pnlOriginTime_a1 = new JPanel(new GridLayout(4, 1, 0, 0));
								pnlOriginTime_a.add(pnlOriginTime_a1, BorderLayout.WEST);	
								pnlOriginTime_a1.setPreferredSize(new java.awt.Dimension(95, 40));	
								pnlOriginTime_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
								pnlOriginTime_a1.addMouseListener(new PopupTriggerListener_panel2());

								{
									lblDtePrepared = new JLabel("Date Prepared", JLabel.TRAILING);
									pnlOriginTime_a1.add(lblDtePrepared);
									lblDtePrepared.setEnabled(false);	
									lblDtePrepared.setPreferredSize(new java.awt.Dimension(100, 40));
									lblDtePrepared.setFont(fontPlainSanSerNine);
								}	
								{
									lblDateNeeded = new JLabel("Date Needed", JLabel.TRAILING);
									pnlOriginTime_a1.add(lblDateNeeded);
									lblDateNeeded.setEnabled(false);	
									lblDateNeeded.setPreferredSize(new java.awt.Dimension(100, 40));
									lblDateNeeded.setFont(fontPlainSanSerNine);
								}	
								{
									lblApprovedBy = new JLabel("Date Approved", JLabel.TRAILING);
									pnlOriginTime_a1.add(lblApprovedBy);
									lblApprovedBy.setEnabled(false);	
									lblApprovedBy.setPreferredSize(new java.awt.Dimension(100, 40));
									lblApprovedBy.setFont(fontPlainSanSerNine);
								}	
								{
									lblDateReceived = new JLabel("Date Received", JLabel.TRAILING);
									pnlOriginTime_a1.add(lblDateReceived);
									lblDateReceived.setEnabled(false);	
									lblDateReceived.setPreferredSize(new java.awt.Dimension(100, 40));
									lblDateReceived.setFont(fontPlainSanSerNine);
								}	


								pnlOriginTime_a2 = new JPanel(new BorderLayout(5, 5));
								pnlOriginTime_a.add(pnlOriginTime_a2, BorderLayout.CENTER);	
								pnlOriginTime_a2.setPreferredSize(new java.awt.Dimension(199, 113));	
								pnlOriginTime_a2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
								pnlOriginTime_a2.addMouseListener(new PopupTriggerListener_panel2());

								{
									pnlOriginTime_a2_1 = new JPanel(new GridLayout(4, 1, 4,4));
									pnlOriginTime_a2.add(pnlOriginTime_a2_1, BorderLayout.WEST);	
									pnlOriginTime_a2_1.setPreferredSize(new java.awt.Dimension(137, 29));										

									{
										dtePrepared = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlOriginTime_a2_1.add(dtePrepared);
										dtePrepared.setBounds(485, 7, 125, 21);
										dtePrepared.setDate(null);
										dtePrepared.setEnabled(false);
										dtePrepared.setDateFormatString("yyyy-MM-dd");
										((JTextFieldDateEditor)dtePrepared.getDateEditor()).setEditable(false);										
									}		
									{
										dteNeeded = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlOriginTime_a2_1.add(dteNeeded);
										dteNeeded.setBounds(485, 7, 125, 21);
										dteNeeded.setDate(null);
										dteNeeded.setEnabled(false);
										dteNeeded.setDateFormatString("yyyy-MM-dd");
										((JTextFieldDateEditor)dteNeeded.getDateEditor()).setEditable(false);										
									}	
									{
										dteApproved = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlOriginTime_a2_1.add(dteApproved);
										dteApproved.setBounds(485, 7, 125, 21);
										dteApproved.setDate(null);
										dteApproved.setEnabled(false);
										dteApproved.setDateFormatString("yyyy-MM-dd");
										((JTextFieldDateEditor)dteApproved.getDateEditor()).setEditable(false);										
									}	
									{
										dteReceived = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlOriginTime_a2_1.add(dteReceived);
										dteReceived.setBounds(485, 7, 125, 21);
										dteReceived.setDate(null);
										dteReceived.setEnabled(false);
										dteReceived.setDateFormatString("yyyy-MM-dd");
										((JTextFieldDateEditor)dteReceived.getDateEditor()).setEditable(false);										
									}		

								}	
							}
						}			
					}		
				}
			}
			{
				pnlIT = new JPanel(new BorderLayout(5, 5));
				pnlDRF.add(pnlIT, BorderLayout.SOUTH);	
				pnlIT.setPreferredSize(new java.awt.Dimension(926, 48));
				pnlIT.setBorder(lineBorder);
				pnlIT.addMouseListener(new PopupTriggerListener_panel2());

				{
					pnlIT_a = new JPanel(new GridLayout(2, 1, 0,0));
					pnlIT.add(pnlIT_a, BorderLayout.WEST);	
					pnlIT_a.setPreferredSize(new java.awt.Dimension(95, 40));	
					pnlIT_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						lblInCharge = new JLabel("Fixed by", JLabel.TRAILING);
						pnlIT_a.add(lblInCharge);
						lblInCharge.setEnabled(false);	
						lblInCharge.setPreferredSize(new java.awt.Dimension(100, 40));
						lblInCharge.setFont(fontPlainSanSerNine);
					}	
					{
						lblReviewedBy = new JLabel("Conformed by", JLabel.TRAILING);
						pnlIT_a.add(lblReviewedBy);
						lblReviewedBy.setEnabled(false);	
						lblReviewedBy.setPreferredSize(new java.awt.Dimension(100, 40));
						lblReviewedBy.setFont(fontPlainSanSerNine);
					}

					pnlIT_b = new JPanel(new BorderLayout(5, 5));
					pnlIT.add(pnlIT_b, BorderLayout.CENTER);	
					pnlIT_b.setPreferredSize(new java.awt.Dimension(814, 40));	
					pnlIT_b.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));

					{
						pnlIT_b1 = new JPanel(new GridLayout(2, 1, 4,4));
						pnlIT_b.add(pnlIT_b1, BorderLayout.WEST);	
						pnlIT_b1.setPreferredSize(new java.awt.Dimension(101, 45));					

						{
							txtFixedBy = new JXTextField("");
							pnlIT_b1.add(txtFixedBy);
							txtFixedBy.setEnabled(false);	
							txtFixedBy.setBounds(120, 25, 300, 22);	
							txtFixedBy.setHorizontalAlignment(JTextField.CENTER);
						}
						{
							txtConformedBy = new JXTextField("");
							pnlIT_b1.add(txtConformedBy);
							txtConformedBy.setEnabled(false);	
							txtConformedBy.setBounds(120, 25, 300, 22);	
							txtConformedBy.setHorizontalAlignment(JTextField.CENTER);
						}
					}	
					{
						pnlIT_b2 = new JPanel(new GridLayout(2, 1, 5, 5));
						pnlIT_b.add(pnlIT_b2, BorderLayout.CENTER);	
						pnlIT_b2.setPreferredSize(new java.awt.Dimension(412, 123));	
						pnlIT_b2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

						{
							tagFixedby = new _JTagLabel("[ ]");
							pnlIT_b2.add(tagFixedby);
							tagFixedby.setBounds(209, 27, 700, 22);
							tagFixedby.setEnabled(false);	
							tagFixedby.setPreferredSize(new java.awt.Dimension(27, 33));
						}
						{
							tagConformedBy = new _JTagLabel("[ ]");
							pnlIT_b2.add(tagConformedBy);
							tagConformedBy.setBounds(209, 27, 700, 22);
							tagConformedBy.setEnabled(false);	
							tagConformedBy.setPreferredSize(new java.awt.Dimension(27, 33));
						}

						pnlIT_b3 = new JPanel(new BorderLayout(5, 5));
						pnlIT_b.add(pnlIT_b3, BorderLayout.EAST);	
						pnlIT_b3.setPreferredSize(new java.awt.Dimension(304, 123));	
						pnlIT_b3.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
						pnlIT_b3.addMouseListener(new PopupTriggerListener_panel2());
						pnlIT_b3.addMouseListener(new PopupTriggerListener_panel2());

						{
							pnlIT_b3_a = new JPanel(new BorderLayout(0,0));
							pnlIT_b3.add(pnlIT_b3_a, BorderLayout.EAST);	
							pnlIT_b3_a.setPreferredSize(new java.awt.Dimension(289, 119));	
							pnlIT_b3_a.addMouseListener(new PopupTriggerListener_panel2());						

							{
								pnlIT_b3_a1 = new JPanel(new GridLayout(2, 1, 0, 0));
								pnlIT_b3_a.add(pnlIT_b3_a1, BorderLayout.WEST);	
								pnlIT_b3_a1.setPreferredSize(new java.awt.Dimension(95, 40));	
								pnlIT_b3_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));

								{
									lblDateFixed = new JLabel("Date Fixed", JLabel.TRAILING);
									pnlIT_b3_a1.add(lblDateFixed);
									lblDateFixed.setEnabled(false);	
									lblDateFixed.setPreferredSize(new java.awt.Dimension(100, 40));
									lblDateFixed.setFont(fontPlainSanSerNine);
								}	
								{
									lblDateReviewed = new JLabel("Date Conformed", JLabel.TRAILING);
									pnlIT_b3_a1.add(lblDateReviewed);
									lblDateReviewed.setEnabled(false);	
									lblDateReviewed.setPreferredSize(new java.awt.Dimension(100, 40));
									lblDateReviewed.setFont(fontPlainSanSerNine);
								}	

								pnlIT_b3_a2 = new JPanel(new BorderLayout(5, 5));
								pnlIT_b3_a.add(pnlIT_b3_a2, BorderLayout.CENTER);	
								pnlIT_b3_a2.setPreferredSize(new java.awt.Dimension(199, 113));	
								pnlIT_b3_a2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

								{
									pnlIT_b3_a2_1 = new JPanel(new GridLayout(2, 1, 4,4));
									pnlIT_b3_a2.add(pnlIT_b3_a2_1, BorderLayout.WEST);	
									pnlIT_b3_a2_1.setPreferredSize(new java.awt.Dimension(137, 29));					

									{
										dteFixed = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlIT_b3_a2_1.add(dteFixed);
										dteFixed.setBounds(485, 7, 125, 21);
										dteFixed.setDate(null);
										dteFixed.setEnabled(false);
										dteFixed.setDateFormatString("yyyy-MM-dd");
										((JTextFieldDateEditor)dteFixed.getDateEditor()).setEditable(false);
									}		
									{
										dteConformed = new _JDateChooser("MM/dd/yy", "##/##/##", '_');
										pnlIT_b3_a2_1.add(dteConformed);
										dteConformed.setBounds(485, 7, 125, 21);
										dteConformed.setDate(null);
										dteConformed.setEnabled(false);
										dteConformed.setDateFormatString("yyyy-MM-dd");
										((JTextFieldDateEditor)dteConformed.getDateEditor()).setEditable(false);
									}											
								}	
							}
						}				
					}		
				}
			}
		} //end of Company

		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));	

			pnlSubTable = new JPanel();
			pnlTable.add(pnlSubTable, BorderLayout.CENTER);
			pnlSubTable.setLayout(new BorderLayout(5, 5));
			pnlSubTable.setPreferredSize(new java.awt.Dimension(923, 199));
			pnlSubTable.setBorder(lineBorder);
			{
				pnlError = new JPanel(new BorderLayout(5, 5));
				pnlSubTable.add(pnlError, BorderLayout.NORTH);	
				pnlError.setPreferredSize(new java.awt.Dimension(928, 54));	
				pnlError.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));

				{
					pnlError_sub1= new JPanel(new BorderLayout(5, 5));
					pnlError.add(pnlError_sub1, BorderLayout.NORTH);	
					pnlError_sub1.setPreferredSize(new java.awt.Dimension(926, 23));	
					pnlError_sub1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						pnlError_a = new JPanel(new BorderLayout(5, 5));
						pnlError_sub1.add(pnlError_a, BorderLayout.WEST);	
						pnlError_a.setPreferredSize(new java.awt.Dimension(253, 29));	
						pnlError_a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						pnlError_a_a1 = new JPanel(new GridLayout(1, 1, 0,0));
						pnlError_a.add(pnlError_a_a1, BorderLayout.WEST);	
						pnlError_a_a1.setPreferredSize(new java.awt.Dimension(101, 29));	
						pnlError_a_a1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							lblErrorType = new JLabel("Type", JLabel.TRAILING);
							pnlError_a_a1.add(lblErrorType);
							lblErrorType.setEnabled(false);	
							lblErrorType.setPreferredSize(new java.awt.Dimension(107, 40));
							lblErrorType.setFont(fontPlainSanSerNine);
						}	

						pnlError_a2 = new JPanel(new GridLayout(1, 1, 0,0));
						pnlError_a.add(pnlError_a2, BorderLayout.CENTER);	
						pnlError_a2.setPreferredSize(new java.awt.Dimension(95, 40));	
						pnlError_a2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							String type[] = {"Systems Error","User Error","Systems Change"};					
							cmbError = new JComboBox(type);
							pnlError_a2.add(cmbError) ;
							cmbError.setFont(fontPlainSanSerNine);
							cmbError.setBounds(537, 15, 190, 21);	
							cmbError.setEnabled(false);
							cmbError.setEditable(false);
							cmbError.setPreferredSize(new java.awt.Dimension(217, 60));
							cmbError.setSelectedIndex(1);	
							cmbError.addItemListener(new ItemListener() {
								public void itemStateChanged(ItemEvent evt) 
								{
									if (cmbError.getSelectedIndex()==0||cmbError.getSelectedIndex()==1)
									{
										txtOtherError.setEnabled(false);	
										txtOtherError.setText("[ ]");
									}

									else if (cmbError.getSelectedIndex()==2)
									{
										txtOtherError.setEnabled(true);	
										txtOtherError.setText("[ System Change Description ]");
									}
								}
							});
						}	

						pnlError_b = new JPanel(new BorderLayout(5, 5));
						pnlError_sub1.add(pnlError_b, BorderLayout.CENTER);	
						pnlError_b.setPreferredSize(new java.awt.Dimension(814, 40));	
						pnlError_b.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));

						{
							txtOtherError = new JXTextField("");
							pnlError_b.add(txtOtherError);
							txtOtherError.setEnabled(false);	
							txtOtherError.setBounds(120, 25, 300, 22);	
							txtOtherError.setHorizontalAlignment(JTextField.LEFT);
							txtOtherError.setFont(new java.awt.Font("Segoe UI",Font.ITALIC,12));
							txtOtherError.setText("[ Other Error Description ]");
						}			
					}
				}
				{
					pnlError_sub2 = new JPanel(new BorderLayout(5, 5));
					pnlError.add(pnlError_sub2, BorderLayout.CENTER);	
					pnlError_sub2.setPreferredSize(new java.awt.Dimension(926, 19));	
					pnlError_sub2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

					{
						pnlError_sub2a = new JPanel(new BorderLayout(5, 5));
						pnlError_sub2.add(pnlError_sub2a, BorderLayout.WEST);	
						pnlError_sub2a.setPreferredSize(new java.awt.Dimension(101, 25));	
						pnlError_sub2a.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							lblReason = new JLabel("Reason", JLabel.TRAILING);
							pnlError_sub2a.add(lblReason);
							lblReason.setEnabled(false);	
							lblReason.setPreferredSize(new java.awt.Dimension(100, 29));
							lblReason.setFont(fontPlainSanSerNine);
						}	
					}
					{
						pnlError_sub2b = new JPanel(new BorderLayout(5, 5));
						pnlError_sub2.add(pnlError_sub2b, BorderLayout.CENTER);	
						pnlError_sub2b.setPreferredSize(new java.awt.Dimension(101, 29));	
						pnlError_sub2b.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

						{
							txtReason = new JXTextField("");
							pnlError_sub2b.add(txtReason);
							txtReason.setEnabled(false);	
							txtReason.setBounds(120, 25, 300, 22);	
							txtReason.setHorizontalAlignment(JTextField.LEFT);
							txtReason.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							txtReason.setPreferredSize(new java.awt.Dimension(820, 25));
							txtReason.setText("");
						}		
					}
				}
			}



			pnlChangeDetails_split = new JPanel();
			pnlSubTable.add(pnlChangeDetails_split, BorderLayout.CENTER);
			pnlChangeDetails_split.setLayout(new BorderLayout(0,0));
			pnlChangeDetails_split.setBorder(lineBorder);		
			pnlChangeDetails_split.setPreferredSize(new java.awt.Dimension(923, 177));

			splitPanel_B = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			pnlChangeDetails_split.add(splitPanel_B);
			splitPanel_B.setOneTouchExpandable(true);
			splitPanel_B.setResizeWeight(.7d);	

			{	
				pnlChangeDetails = new JPanel();
				//pnlSubTable.add(pnlChangeDetails, BorderLayout.CENTER);
				splitPanel_B.add(pnlChangeDetails, JSplitPane.LEFT);
				pnlChangeDetails.setLayout(new BorderLayout(0,0));
				pnlChangeDetails.setBorder(lineBorder);		
				pnlChangeDetails.setPreferredSize(new java.awt.Dimension(923, 177));
				//pnlChangeDetails.setBorder(JTBorderFactory.createTitleBorder("List of Data to be Changed"));

				{			
					{
						scrollChangeDetails = new _JScrollPaneMain();
						pnlChangeDetails.add(scrollChangeDetails, BorderLayout.CENTER);

						{
							modelChangeDetails = new modelDCRF_change_list();

							tblChangeDetails = new _JTableMain(modelChangeDetails);
							scrollChangeDetails.setViewportView(tblChangeDetails);
							tblChangeDetails.addMouseListener(this);	
							tblChangeDetails.addMouseListener(new PopupTriggerListener_panel1());
							tblChangeDetails.setSortable(false);						
							tblChangeDetails.getColumnModel().getColumn(0).setPreferredWidth(80);
							tblChangeDetails.getColumnModel().getColumn(1).setPreferredWidth(150);
							tblChangeDetails.getColumnModel().getColumn(2).setPreferredWidth(150);
							tblChangeDetails.getColumnModel().getColumn(3).setPreferredWidth(150);
							tblChangeDetails.getColumnModel().getColumn(4).setPreferredWidth(150);
							tblChangeDetails.getColumnModel().getColumn(5).setPreferredWidth(150);
							tblChangeDetails.getColumnModel().getColumn(6).setPreferredWidth(150);
							tblChangeDetails.getColumnModel().getColumn(7).setPreferredWidth(150);
							tblChangeDetails.getColumnModel().getColumn(8).setPreferredWidth(150);


							tblChangeDetails.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if(tblChangeDetails.rowAtPoint(e.getPoint()) == -1) {

									}else {
										tblChangeDetails.setCellSelectionEnabled(true);
									}	
								}
							});


							tblChangeDetails.addKeyListener(this);

						}
						{
							rowHeaderChangeDetails = tblChangeDetails.getRowHeader22();
							scrollChangeDetails.setRowHeaderView(rowHeaderChangeDetails);
							scrollChangeDetails.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
					}
					{
						scrollChangeDetailstotal = new _JScrollPaneTotal(scrollChangeDetails);
						pnlChangeDetails.add(scrollChangeDetailstotal, BorderLayout.SOUTH);
						{
							modelChangeDetails_total = new modelDCRF_change_list();
							modelChangeDetails_total.addRow(new Object[] { null, "Total", null, null, null, null});

							tblChangeDetails_total = new _JTableTotal(modelChangeDetails_total, tblChangeDetails);
							tblChangeDetails_total.setFont(dialog11Bold);
							scrollChangeDetailstotal.setViewportView(tblChangeDetails_total);
							((_JTableTotal) tblChangeDetails_total).setTotalLabel(1);
						}
					}
				} 
			}
			{
				pnlEval = new JPanel(new BorderLayout(5, 5));
				//pnlSubTable.add(pnlEval, BorderLayout.SOUTH);	
				splitPanel_B.add(pnlEval, JSplitPane.RIGHT);
				pnlEval.setPreferredSize(new java.awt.Dimension(926, 66));	
				pnlEval.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

				{
					pnlEval_sub1 = new JPanel(new BorderLayout(5, 5));
					pnlEval.add(pnlEval_sub1, BorderLayout.WEST);	
					pnlEval_sub1.setPreferredSize(new java.awt.Dimension(435, 66));	
					pnlEval_sub1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
					pnlEval_sub1.setBorder(JTBorderFactory.createTitleBorder("User Remarks"));

					{
						scpEval = new JScrollPane();
						pnlEval_sub1.add(scpEval);
						scpEval.setBounds(82, 7, 150, 61);
						scpEval.setOpaque(true);
						scpEval.setPreferredSize(new java.awt.Dimension(434, 66));

						{
							txtUserRemark = new JTextArea();
							scpEval.add(txtUserRemark);
							scpEval.setViewportView(txtUserRemark);
							txtUserRemark.setBounds(77, 3, 250, 81);
							txtUserRemark.setLineWrap(true);
							txtUserRemark.setPreferredSize(new java.awt.Dimension(908, 109));
							txtUserRemark.setEditable(false);
							//txtUserRemark.setEnabled(false);	
							txtUserRemark.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							txtUserRemark.setText("");	
						}	
					}
				}
				{
					pnlEval_sub2 = new JPanel(new BorderLayout(5, 5));
					pnlEval.add(pnlEval_sub2, BorderLayout.CENTER);	
					pnlEval_sub2.setPreferredSize(new java.awt.Dimension(926, 66));	
					pnlEval_sub2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
					pnlEval_sub2.setBorder(JTBorderFactory.createTitleBorder("Admin Remarks"));

					{
						scpAdminRemarks = new JScrollPane();
						pnlEval_sub2.add(scpAdminRemarks);
						scpAdminRemarks.setBounds(82, 7, 150, 61);
						scpAdminRemarks.setOpaque(true);
						scpAdminRemarks.setPreferredSize(new java.awt.Dimension(926, 66));

						{
							txtAdminRemarks = new JTextArea();
							scpAdminRemarks.add(txtAdminRemarks);
							scpAdminRemarks.setViewportView(txtAdminRemarks);
							txtAdminRemarks.setBounds(77, 3, 250, 81);
							txtAdminRemarks.setLineWrap(true);
							txtAdminRemarks.setPreferredSize(new java.awt.Dimension(908, 109));
							txtAdminRemarks.setEditable(false);
							//txtAdminRemarks.setEnabled(false);	
							txtAdminRemarks.setFont(new java.awt.Font("Segoe UI",Font.PLAIN,12));
							txtAdminRemarks.setText("");	
						}	
					}
				}
			}

		} 
		{
			pnlSouth = new JPanel();
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new BorderLayout());
			//pnlSouth.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			pnlSouth.setPreferredSize(new java.awt.Dimension(1014, 33));

			pnlSouthCenterb = new JPanel(new GridLayout(1, 2, 5, 5));
			pnlSouth.add(pnlSouthCenterb, BorderLayout.NORTH);
			pnlSouthCenterb.setPreferredSize(new java.awt.Dimension(921, 31));
			{
				{
					btnCreateNew = new JButton("Create New");
					pnlSouthCenterb.add(btnCreateNew);
					btnCreateNew.setActionCommand("New");
					btnCreateNew.addActionListener(this);
					btnCreateNew.setEnabled(false);
					btnCreateNew.setFont(fontPlainSanSerNine);
				}
				{
					btnSave = new JButton("Save");
					pnlSouthCenterb.add(btnSave);
					btnSave.setActionCommand("Save");
					btnSave.addActionListener(this);
					btnSave.setEnabled(false);
					btnSave.setFont(fontPlainSanSerNine);
				}
				{
					btnApprove = new JButton("Approve");
					pnlSouthCenterb.add(btnApprove);
					btnApprove.setActionCommand("Approve");
					btnApprove.addActionListener(this);
					btnApprove.setEnabled(false);
					btnApprove.setFont(fontPlainSanSerNine);
				}
				{
					btnReceive = new JButton("Receive");
					pnlSouthCenterb.add(btnReceive);
					btnReceive.setActionCommand("Receive");
					btnReceive.addActionListener(this);
					btnReceive.setEnabled(false);
					btnReceive.setFont(fontPlainSanSerNine);
				}				
				{
					btnFix = new JButton("Mark as Fix");
					pnlSouthCenterb.add(btnFix);
					btnFix.setActionCommand("Fix");
					btnFix.setText("Fix");
					btnFix.addActionListener(this);
					btnFix.setEnabled(false);
					btnFix.setFont(fontPlainSanSerNine);
				}
				{
					btnConform = new JButton("Conform");
					pnlSouthCenterb.add(btnConform);
					btnConform.setActionCommand("Conform");
					btnConform.setText("Conform");
					btnConform.addActionListener(this);
					btnConform.setEnabled(false);
					btnConform.setFont(fontPlainSanSerNine);
				}
				{
					btnDelete = new JButton("Delete");
					pnlSouthCenterb.add(btnDelete);
					btnDelete.setActionCommand("Delete");
					btnDelete.setText("Delete");
					btnDelete.addActionListener(this);
					btnDelete.setEnabled(false);
					btnDelete.setFont(fontPlainSanSerNine);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouthCenterb.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
					btnPreview.setFont(fontPlainSanSerNine);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouthCenterb.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
					btnCancel.setFont(fontPlainSanSerNine);
				}
			}
		}

		initialize_comp(); 
		Notification(); 
	}

	//display tables
	private void displayDCRFdetails(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {//

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
				"\r\n" + 
						"select \r\n" + 
						"a.line_no, \n"  +
						"trim(a.module) as module,\r\n" + 
						"trim(a.doc_name) as doc_name,\r\n" + 
						"trim(b.entity_name) as entity,\r\n" + 
						"trim(c.company_alias) as comp,\r\n" + 
						"trim(d.proj_alias) as proj,\r\n" + 
						"trim(e.description) as unit,\r\n" + 
						"trim(a.change_from) as change_from,\r\n" + 
						"trim(a.change_to) as change_to\r\n," +
						"(case when trim(a.module) = 'NEW MODULE' then 'EDELLO' else upper(h.first_name) end) \r\n" + 
						"\r\n" + 
						"\r\n" + 
						"from rf_dcrf_detail a\r\n" + 
						"left join rf_entity b on a.entity_id = b.entity_id\r\n" + 
						"left join mf_company c on a.co_id = c.co_id\r\n" + 
						"left join mf_project d on a.proj_id = d.proj_id\r\n" + 
						"left join mf_unit_info e on a.pbl_id = e.pbl_id and d.proj_id = e.proj_id\r\n" +
						"left join (SELECT module_name, in_charge from mf_jsystem_modules where status_id = 'A') f on a.module = f.module_name \n" + 
						"left join em_employee g on f.in_charge = g.emp_code  \n" + 
						"left join rf_entity h on g.entity_id = h.entity_id " + 
						"\r\n" + 
						"where a.status_id = 'A'\r\n" +
						"and a.dcrf_no = '"+dcrf_no+"' " + 
						" " ;  

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}				
		}

		else {
			modelChangeDetails_total = new modelDCRF_change_list();
			modelChangeDetails_total.addRow(new Object[] { "Total", null, null, null, new BigDecimal(0.00) });

			tblChangeDetails_total = new _JTableTotal(modelChangeDetails_total, tblChangeDetails);
			tblChangeDetails_total.setFont(dialog11Bold);
			scrollChangeDetailstotal.setViewportView(tblChangeDetails_total);
			((_JTableTotal) tblChangeDetails_total).setTotalLabel(0);}

		tblChangeDetails.packAll();	

		adjustRowHeight_account(tblChangeDetails);
	}	

	private void setDCRF_header(){
		refresh_fields();
		Object[] dcrf_hdr = getDCRFdetails();

		lookupDept.setText((String) dcrf_hdr[0]);
		tagDept.setTag((String) dcrf_hdr[1]);		

		prep_by = (String) dcrf_hdr[2];
		txtPreparedBy.setText(prep_by);	
		tagPreparedBy.setTag((String) dcrf_hdr[3]);
		dteNeeded.setDate((Date) dcrf_hdr[17]);
		dtePrepared.setDate((Date) dcrf_hdr[16]);

		txtReceivedBy.setText((String) dcrf_hdr[4]);
		tagReceivedBy.setTag((String) dcrf_hdr[5]);
		dteReceived.setDate((Date) dcrf_hdr[18]);

		txtApproveBy.setText((String) dcrf_hdr[6]);
		tagApprovedBy.setTag((String) dcrf_hdr[7]);
		dteApproved.setDate((Date) dcrf_hdr[19]);

		txtFixedBy.setText((String) dcrf_hdr[8]);
		tagFixedby.setTag((String) dcrf_hdr[9]);
		dteFixed.setDate((Date) dcrf_hdr[20]);

		txtConformedBy.setText((String) dcrf_hdr[10]);
		tagConformedBy.setTag((String) dcrf_hdr[11]);
		dteConformed.setDate((Date) dcrf_hdr[21]);

		cmbError.setSelectedIndex(Integer.parseInt(dcrf_hdr[12].toString()));
		txtOtherError.setText((String) dcrf_hdr[13]);
		txtUserRemark.setText((String) dcrf_hdr[14]);

		txtReason.setText((String) dcrf_hdr[23]);
		txtAdminRemarks.setText((String) dcrf_hdr[24]);

		status = (String) dcrf_hdr[22];



		tagDCRF_status.setTag(status);
		System.out.printf("Status DCRF: %s%n",status);
		System.out.printf("DCRF No: %s%n",lookupDCRF.getValue());


		if (status.equals("ACTIVE")) {
			if (UserInfo.EmployeeCode.equals(sql_getCreatedBy())  || UserInfo.Department.equals("98" ) 
					||UserInfo.EmployeeCode.equals("900449") //Del Gonzales
					||UserInfo.EmployeeCode.equals("900395") //Net Cuaresma
					||UserInfo.EmployeeCode.equals("900028") //Rhea Lim
					||UserInfo.EmployeeCode.equals("900267") //Jope Roxas
					||UserInfo.EmployeeCode.equals("900054") //Ela Puyo
					||UserInfo.EmployeeCode.equals("900041")//Allan Matias
					||UserInfo.EmployeeCode.equals("987110")//Jonel Lopez
					||UserInfo.EmployeeCode.equals("900606")//Jesica cumal
					||UserInfo.EmployeeCode.equals("900298")
					||UserInfo.EmployeeCode.equals("987120")
					||UserInfo.EmployeeCode.equals(Head)
					/*||UserInfo.EmployeeCode.equals(sql_getDeptHead(UserInfo.EmployeeCode))*/) {
					if(UserInfo.ADMIN) {
						enableButtons(false, true, false, false, false, false, true, true, true);
					}else {
						enableButtons(false, true, true, false, false, false, true, true, true);
					}
			}else {
				enableButtons(false, true, false, false, false, false, false, true, true);
			}
			//				enableButtons(false, true, false, false, false, false, true, true, true);
			//Added by: Jervin Vilog ---Date: 2019-12-09 --- Reason: Pwede Iapproved ng ibang head ang dcrf na hindi sa department nila pag tinangal ito  

			/*if(UserInfo.EmployeeCode.equals(Head)) {
					enableButtons(false, true, true, false, false, false, true, true, true);
				}else {
					enableButtons(false, true, false, false, false, false, false, true, true);
				}*/
			//			}else if(UserInfo.EmployeeCode.equals(Head)){
			//				enableButtons(false, true, true, false, false, false, true, true, true);
			//			}else {
			//				enableButtons(false, false, false, false, false, false, false, true, true);
			//			}

			btnSave.setActionCommand("Edit");
			btnSave.setText("Edit");
			//txtAdminRemarks.setEnabled(false);
			txtAdminRemarks.setEditable(false);
		} else if (status.equals("APPROVED")) {
			System.out.println("Approved");

			if(vcooApproved()) {
				if (sql_isSPT_emp()==true) {
					enableButtons(false, false, false, true, false, false, false, true, true);
				} else {
					enableButtons(false, false, false, false, false, false, false, true, true);	
				}
			}else {
				if(UserInfo.ADMIN) {
					enableButtons(false, false, false, false, false, false, true, true, true);	
				}else {
					enableButtons(false, false, false, false, false, false, false, true, true);	
				}
			}



			btnSave.setActionCommand("Save");
			btnSave.setText("Save");
			//txtAdminRemarks.setEnabled(true);
			txtAdminRemarks.setEditable(true);
			dtePrepared.setEnabled(false);
			dteNeeded.setEnabled(false);


		} else if (status.equals("RECEIVED")) {
			if (sql_isSPT_emp()==true) {
				enableButtons(false, false, false, false, true, false, true, true, true);	
			} else {
				enableButtons(false, false, false, false, false, false, false, true, true);				
			}

			btnSave.setActionCommand("Save");
			btnSave.setText("Save");
			//txtAdminRemarks.setEnabled(false);
			txtAdminRemarks.setEditable(false);
		} else if (status.equals("FIXED")) {
			if (UserInfo.EmployeeCode.equals(sql_getCreatedBy())) {
				enableButtons(false, false, false, false, false, true, false, true, true);
			} else {
				enableButtons(false, false, false, false, false, false, false, true, true);	
			}

			btnSave.setActionCommand("Save");
			btnSave.setText("Save");
			//txtAdminRemarks.setEnabled(true);
			txtAdminRemarks.setEditable(true);
			dtePrepared.setEnabled(false);
			dteNeeded.setEnabled(false);
		} else if (status.equals("INACTIVE")) {
			enableButtons(false, false, false, false, false, false, false, true, true);
			btnSave.setActionCommand("Save");
			btnSave.setText("Save");
			//txtAdminRemarks.setEnabled(false);
			txtAdminRemarks.setEditable(false);
			dtePrepared.setEnabled(false);
			dteNeeded.setEnabled(false);
		} else if (status.equals("COMPLETED")) {
			enableButtons(false, false, false, false, false, false, false, true, true);
			btnSave.setActionCommand("Save");
			btnSave.setText("Save");
			//txtAdminRemarks.setEnabled(false);
			txtAdminRemarks.setEditable(false);
			dtePrepared.setEnabled(false);
			dteNeeded.setEnabled(false);
		}

		displayDCRFdetails(modelChangeDetails, rowHeaderChangeDetails, modelChangeDetails_total);
		modelChangeDetails.setEditable(false);

		lblDept.setEnabled(false);	
		lookupDept.setEnabled(false);	
		tagDept.setEnabled(false);	
		cmbError.setEnabled(false);	
		btnAttach.setEnabled(false);
	}

	public void createDCRFtable(DefaultTableModel modelMain, JList rowHeader, DefaultTableModel modelTotal) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 			
				"select 1, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 2, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 3, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 4, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 5, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 6, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 7, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 8, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 9, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 10, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 11, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 12, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 13, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 14, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 15, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 16, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 17, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 18, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 19, '', '', '', '', '', '', '', ''  union all    \r\n" + 
						"select 20, '', '', '', '', '', '', '', ''  \r\n" + 
						" " ;

		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());				
			}				
		}		

		else {
			modelChangeDetails_total = new modelDCRF_change_list();
			modelChangeDetails_total.addRow(new Object[] { null, "Total", null, null, null, null});

			tblChangeDetails_total = new _JTableTotal(modelChangeDetails_total, tblChangeDetails);
			tblChangeDetails_total.setFont(dialog11Bold);
			scrollChangeDetailstotal.setViewportView(tblChangeDetails_total);
			((_JTableTotal) tblChangeDetails_total).setTotalLabel(1);
		}

		tblChangeDetails.packAll();
		modelChangeDetails.setEditable(false);
		adjustRowHeight_account(tblChangeDetails);
	}	


	//refresh, disable, enable
	private void refresh_variables(){			
		pbl_id 	= "";
		proj_id = "";
		status   = "";
		entity_id = "";
	}

	private void refresh_fields(){			
		lookupDept.setValue("");
		txtPreparedBy.setText("");
		txtReceivedBy.setText("");
		txtReceivedBy.setText("");
		txtApproveBy.setText("");
		tagDept.setText("");
		tagPreparedBy.setText("");
		tagReceivedBy.setText("");
		tagApprovedBy.setText("");
		txtFixedBy.setText("");
		txtConformedBy.setText("");
		tagFixedby.setText("");
		tagConformedBy.setText("");
	}

	private void enableButtons(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e, Boolean f, Boolean g, Boolean h, Boolean i){

		btnCreateNew.setEnabled(a);
		btnSave.setEnabled(b);
//		btnAttach.setEnabled(b);
		btnApprove.setEnabled(c);
		btnReceive.setEnabled(d);  //check
		btnFix.setEnabled(e);
		btnConform.setEnabled(f);
		btnDelete.setEnabled(g);		
		btnPreview.setEnabled(h);
		btnCancel.setEnabled(i);		

	}


	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand().equals("New")){addNew();}

		if(e.getActionCommand().equals("Cancel")){cancel();}

		if(e.getActionCommand().equals("Save")){

			if(tblChangeDetails.getSelectedRows().length==1) {
				int row = tblChangeDetails.getSelectedRow();

				String module = (String) modelChangeDetails.getValueAt(row, 1);

				System.out.println("mdole:"+ module);
				if(module.equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Please Select a Module", "Warning",JOptionPane.WARNING_MESSAGE);
				}else {
					save();
					Notification(); 
					InitializeSurvey(); 
					attachment_Func();
					
					
					
				}
			}

		}

		if(e.getActionCommand().equals("Edit")){edit();}

		if(e.getActionCommand().equals("Update_user")){update_user();}

		if(e.getActionCommand().equals("Update_admin")){update_admin();}

		if(e.getActionCommand().equals("Receive")){receive();}

		if(e.getActionCommand().equals("Approve")){

			System.out.println("");
			System.out.println("select get_client_name(y.entity_id) \n" + 
					"from mf_department x\n" + 
					"inner join em_employee y on x.dept_head_code = y.emp_code\n" + 
					"where x.dept_code = '"+lookupDept.getText()+"'");
			System.out.println("Only "+FncGlobal.GetString("select get_client_name(y.entity_id)\n" + 
					"from mf_department x\n" + 
					"inner join em_employee y on x.dept_head_code = y.emp_code\n" + 
					"where x.dept_code = '"+lookupDept.getText()+"'")+" is allowed to approve this request.");

			if (
					//					UserInfo.EmployeeCode.equals(FncGlobal.GetString("select y.emp_code \n" + 
					//							"from mf_department x\n" + 
					//							"inner join em_employee y on x.dept_head_code = y.emp_code\n" + 
					//							"where x.dept_code = '"+lookupDept.getText()+"'")) || FncGlobal.GetBoolean("select exists(select *\n" + 
					//									"from em_employee x\n" + 
					//									"inner join mf_rank_level y on x.emp_rank = y.level_code\n" + 
					//									"where x.emp_code = '"+UserInfo.EmployeeCode+"' and y.level_no <= 5)") || UserInfo.EmployeeCode.equals("900606")//Jesica Cumal
					//  Modified by : Jervin Vilog /change of level no. due to request of sir garret 
					UserInfo.EmployeeCode.equals(FncGlobal.GetString("select y.emp_code \n" + 
							"from mf_department x\n" + 
							"inner join em_employee y on x.dept_head_code = y.emp_code\n" + 
							"where x.dept_code = '"+lookupDept.getText()+"'")) || FncGlobal.GetBoolean("select exists(select *\n" + 
									"from em_employee x\n" + 
									"inner join mf_rank_level y on x.emp_rank = y.level_code\n" + 
									"where x.emp_code = '"+UserInfo.EmployeeCode+"' and y.level_no >= 12)") || UserInfo.EmployeeCode.equals("900606")//Jesica Cumal
					) {
				approve();
			} else {
				JOptionPane.showMessageDialog(getContentPane(), "Only "+FncGlobal.GetString("select get_client_name(y.entity_id)\n" + 
						"from mf_department x\n" + 
						"inner join em_employee y on x.dept_head_code = y.emp_code\n" + 
						"where x.dept_code = '"+lookupDept.getText()+"'")+" is allowed to approve this request.");
			}
		}

		if(e.getActionCommand().equals("Fix")){
			fix();
		}

		if(e.getActionCommand().equals("Conform")){
			conform();
		}

		if(e.getActionCommand().equals("Delete")){
			delete();
		}

		if(e.getActionCommand().equals("Preview")){
			preview();
		}

		/*if(e.getActionCommand().equals("Inactivate")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==true){save("I");}
		else if(e.getActionCommand().equals("Inactivate")&& FncAcounting.EmpCanAddNew(UserInfo.EmployeeCode, "8")==false) 
		{JOptionPane.showMessageDialog(getContentPane(),"Sorry, you are not authorized to cancel commission.","Warning",JOptionPane.WARNING_MESSAGE); }*/
	}

	public void mouseClicked(MouseEvent evt) {

		if ((evt.getClickCount() >= 2)) {
			clickTableColumn();
		}
		else if ((evt.getClickCount() == 1)) {}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	private void cancel(){

		if (btnSave.isEnabled()==true) {

			if (JOptionPane.showConfirmDialog(getContentPane(), "Cancel unsaved data?", "Cancel", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				execute_cancel();
			}

		}

		else {	execute_cancel(); 	}
	}

	private void execute_cancel(){

		FncTables.clearTable(modelChangeDetails);//Code to clear modelMain.		
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderChangeDetails.setModel(listModel);//Setting of DefaultListModel into rowHeader.	

		lblDCRF_no.setEnabled(true);
		lookupDCRF.setEnabled(true);
		tagDCRF_status.setEnabled(true);		

		lblDept.setEnabled(false);	
		lookupDept.setEnabled(false);	
		tagDept.setEnabled(false);	
		
		btnAttach.setEnabled(false);
		
		enableButtons(true, false, false, false, false, false, false, false, false);
		btnSave.setText("Save");
		btnSave.setActionCommand("Save");

		modelChangeDetails.setEditable(false);

		lblErrorType.setEnabled(false);	
		cmbError.setEnabled(false);
		cmbError.setSelectedIndex(0);
		//txtUserRemark.setEnabled(false);
		txtUserRemark.setEditable(false);
		txtOtherError.setEnabled(false);
		txtUserRemark.setEnabled(false);	
		txtAdminRemarks.setEnabled(false);		

		lblReason.setEnabled(false);	
		txtReason.setEnabled(false);	

		refresh_variables();

		dtePrepared.setEnabled(false);
		dteNeeded.setEnabled(false);
		lblPreparedBy.setEnabled(false);	
		tagPreparedBy.setEnabled(false);	
		lblDtePrepared.setEnabled(false);	
		lblDateNeeded.setEnabled(false);		

		refreshFields();
		
	}

	private void save(){



		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to save this DCRF?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			dcrf_no = sql_getNextDCRFno();

			pgUpdate db = new pgUpdate();				
			insertDCRF_header(db);				
			insertDCRF_detail(db);			
			db.commit();

			JOptionPane.showMessageDialog(getContentPane(),"DCRF successfully created.","Information",JOptionPane.INFORMATION_MESSAGE);
			setDCRF_header();	
			lookupDCRF.setValue(dcrf_no.toString());
		}


	}


	private void update_user(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to edit this DCRF?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();				
			updateDCRF_header_user(db);	
			updateDCRF_detail(db);
			insertDCRF_detail(db);			
			db.commit();

			JOptionPane.showMessageDialog(getContentPane(),"DCRF successfully edited by the user.","Information",JOptionPane.INFORMATION_MESSAGE);
			setDCRF_header();			
		}

		InitializeSurvey();
	}

	private void update_admin(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to edit this DCRF?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();				
			updateDCRF_header_admin(db);					
			db.commit();

			JOptionPane.showMessageDialog(getContentPane(),"DCRF successfully edited by the admin.","Information",JOptionPane.INFORMATION_MESSAGE);
			setDCRF_header();	
		}
	}

	private void receive(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to receive this DCRF?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();				
			tagDCRF_header(db);			
			db.commit();

			JOptionPane.showMessageDialog(getContentPane(),"DCRF successfully received.","Information",JOptionPane.INFORMATION_MESSAGE);
			setDCRF_header();
		}
	}

	private void approve(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to approve this DCRF?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();				
			approveDCRF_header(db);			
			db.commit();

			JOptionPane.showMessageDialog(getContentPane(),"DCRF successfully approved.","Information",JOptionPane.INFORMATION_MESSAGE);
			setDCRF_header();

			InitializeSurvey();
		}
	}

	private void fix(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to mark this DCRF fixed?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();				
			fixDCRF_header(db);			
			db.commit();

			JOptionPane.showMessageDialog(getContentPane(),"DCRF marked as fixed.","Information",JOptionPane.INFORMATION_MESSAGE);
			setDCRF_header();
		}
	}

	private void conform(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to mark this DCRF conformed?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();				
			conformDCRF_header(db);			
			db.commit();

			JOptionPane.showMessageDialog(getContentPane(),"DCRF marked as conformed.","Information",JOptionPane.INFORMATION_MESSAGE);
			setDCRF_header();
		}
	}

	private void delete(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to delete this DCRF?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {

			pgUpdate db = new pgUpdate();				
			deleteDCRF_header(db);			
			db.commit();

			JOptionPane.showMessageDialog(getContentPane(),"DCRF deleted.","Information",JOptionPane.INFORMATION_MESSAGE);
			setDCRF_header();
		}
	}

	private void initialize_comp(){		

		tagDCRF_status.setTag("");
		lblDCRF_no.setEnabled(true);
		lookupDCRF.setEnabled(true);
		tagDCRF_status.setEnabled(true);
		enableButtons(true, false, false, false, false, false, false, false, false);
	}

	private void addNew(){
//		dcrf_no = sql_getNextDCRFno();
//		lookupDCRF.setValue(dcrf_no.toString());
		btnAttach.setEnabled(false);
		lblDCRF_no.setEnabled(false);
		lookupDCRF.setEnabled(false);
		tagDCRF_status.setEnabled(false);		

		lblDept.setEnabled(true);	
		lookupDept.setEnabled(false);	
		tagDept.setEnabled(true);	
		
		

		createDCRFtable(modelChangeDetails, rowHeaderChangeDetails, modelChangeDetails_total);

		lookupDept.setLookupSQL(getDepartment());

		enableButtons(false, true, false, false, false, false, false, false, true);
		btnSave.setText("Save");
		btnSave.setActionCommand("Save");

		dtePrepared.setDate(Calendar.getInstance().getTime());
		dteNeeded.setDate(Calendar.getInstance().getTime());
		dteNeeded.setEnabled(true);

		Object[] dept_dtl = sql_getDeptDtls();
		dept_id = "";
		String dept_name = "";	
		try { dept_id = (String) dept_dtl[0];} catch (NullPointerException e) { dept_id = ""; }
		try { dept_name = (String) dept_dtl[1];} catch (NullPointerException e) { dept_name = ""; }	

		if (dept_id.equals(""))
		{
			lblErrorType.setEnabled(false);
			lblReason.setEnabled(false);	
			cmbError.setEnabled(false);
			txtUserRemark.setEnabled(false);
			txtUserRemark.setEditable(true);
			txtReason.setEnabled(false);	
			modelChangeDetails.setEditable(false);
		}

		else 
		{
			lookupDept.setText(dept_id);
			tagDept.setTag(dept_name);
			lblErrorType.setEnabled(true);
			lblReason.setEnabled(true);	
			cmbError.setEnabled(true);
			txtUserRemark.setEnabled(true);
			txtUserRemark.setEditable(true);
			txtReason.setEnabled(true);	
			modelChangeDetails.setEditable(true);
		}

		txtPreparedBy.setText(UserInfo.EmployeeCode);
		tagPreparedBy.setTag(UserInfo.FullName);
		lblPreparedBy.setEnabled(true);	
		tagPreparedBy.setEnabled(true);	
		lblDtePrepared.setEnabled(true);	
		lblDateNeeded.setEnabled(true);	
	}

	private void edit(){

		String user = UserInfo.EmployeeCode;

		if (
				!prep_by.equals(user) 
				&& 
				isSPT_emp(user)==false
				&&
				!(
						UserInfo.EmployeeCode.equals(FncGlobal.GetString("select y.emp_code \n" + 
								"from mf_department x\n" + 
								"inner join em_employee y on x.dept_head_code = y.emp_code\n" + 
								"where x.dept_code = '"+lookupDept.getText()+"'")) 
						|| 
						//						FncGlobal.GetBoolean("select exists(select *\n" + 
						//								"from em_employee x\n" + 
						//								"inner join mf_rank_level y on x.emp_rank = y.level_code\n" + 
						//								"where x.emp_code = '"+UserInfo.EmployeeCode+"' and y.level_no <= 5)")
						//  Modified by : Jervin Vilog /change of level no. due to request of sir garret 
						FncGlobal.GetBoolean("select exists(select *\n" + 
								"from em_employee x\n" + 
								"inner join mf_rank_level y on x.emp_rank = y.level_code\n" + 
								"where x.emp_code = '"+UserInfo.EmployeeCode+"' and y.level_no >= 12)")
						)
				) {
			{JOptionPane.showMessageDialog(getContentPane(), "Sorry, you are not authorized to edit this DCRF.", "Error", 
					JOptionPane.ERROR_MESSAGE);}	
		} else if (isSPT_emp(user)==true&&!prep_by.equals(user)) {
			//txtAdminRemarks.setEnabled(true);
			btnAttach.setEnabled(true);
			txtAdminRemarks.setEditable(true);
			enableButtons(false, true, false, false, false, false, false, false, true);
			btnSave.setText("Save");
			btnSave.setActionCommand("Update_admin");
		}
		else
		{
			btnAttach.setEnabled(true);
			lblDCRF_no.setEnabled(false);
			lookupDCRF.setEnabled(false);
			tagDCRF_status.setEnabled(false);	

			lblDept.setEnabled(true);	
			lookupDept.setEnabled(false);	
			tagDept.setEnabled(true);	

			enableButtons(false, true, false, false, false, false, false, false, true);
			btnSave.setText("Save");
			btnSave.setActionCommand("Update_user");

			dtePrepared.setDate(Calendar.getInstance().getTime());
			dtePrepared.setEnabled(true);
			dteNeeded.setEnabled(true);

			Object[] dept_dtl = sql_getDeptDtls();
			dept_id = "";
			String dept_name = "";	
			try { dept_id = (String) dept_dtl[0];} catch (NullPointerException e) { dept_id = ""; }
			try { dept_name = (String) dept_dtl[1];} catch (NullPointerException e) { dept_name = ""; }	

			lookupDept.setText(dept_id);
			tagDept.setTag(dept_name);
			lblErrorType.setEnabled(true);
			lblReason.setEnabled(true);	
			cmbError.setEnabled(true);
			txtUserRemark.setEnabled(true);
			txtUserRemark.setEditable(true);
			//txtAdminRemarks.setEnabled(true);
			txtReason.setEnabled(true);	
			modelChangeDetails.setEditable(true);

			txtPreparedBy.setText(UserInfo.EmployeeCode);
			tagPreparedBy.setTag(UserInfo.FullName);
			lblPreparedBy.setEnabled(true);	
			tagPreparedBy.setEnabled(true);	
			lblDtePrepared.setEnabled(true);	
			lblDateNeeded.setEnabled(true);	

			AddExtraRows();	
		}
	}

	private void refreshFields(){

		lookupDCRF.setText("");
		tagDCRF_status.setTag("");

		lookupDept.setText("");
		txtPreparedBy.setText("");
		txtReceivedBy.setText("");
		txtApproveBy.setText("");
		txtFixedBy.setText("");
		txtConformedBy.setText("");

		tagDept.setTag("");
		tagPreparedBy.setTag("");
		tagReceivedBy.setTag("");
		tagApprovedBy.setTag("");
		tagFixedby.setTag("");
		tagConformedBy.setTag("");

		txtOtherError.setText("[ Other Error Description ]");
		txtUserRemark.setText("");
		txtReason.setText("");
		txtAdminRemarks.setText("");	

		dtePrepared.setDate(Calendar.getInstance().getTime());
		dteNeeded.setDate(null);
		dteReceived.setDate(null);
		dteApproved.setDate(null);
		dteFixed.setDate(null);
		dteConformed.setDate(null);
	}

	public static void preview(){	

		String criteria = "DCRF";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("dcrf_no", dcrf_no);
		FncReport.generateReport("/Reports/rptDCRF_preview_paperless.jasper", reportTitle, company, mapParameters);
		
//		if(UserInfo.EmployeeCode.equals("900876")) {
//			FncReport.reportViewer("/Reports/sample.jrxml", "Testing", "Testing", mapParameters);
//		}
	}

	private void previewViewAllPendingRequests(){	

		String criteria = "All Pending Requests";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("prepared_by", UserInfo.FullName );		
		mapParameters.put("emp_id", "");
		mapParameters.put("emp_name", "All");
		mapParameters.put("div_id", "");
		mapParameters.put("div_name", "All");		
		mapParameters.put("dept_id", "");
		mapParameters.put("dept_name", "All");
		mapParameters.put("incharge_id", "");
		mapParameters.put("incharge_name", "All");
		mapParameters.put("module", "All");
		mapParameters.put("error_no", 0);
		mapParameters.put("error_name", "All");
		mapParameters.put("status_no", 0);
		mapParameters.put("status_name", "All");
		mapParameters.put("report_no", 0);
		mapParameters.put("report_name", "Pending Requests");	
		mapParameters.put("prepared_by", UserInfo.FullName );
		mapParameters.put("date_from",FncGlobal.dateFormat("2016-01-01"));
		mapParameters.put("date_to",FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		FncReport.generateReport("/Reports/rptDCRF_pending_all_requests.jasper", reportTitle, company, mapParameters);

		/*
		String criteria2 = "All Pending Requests Details";		
		String reportTitle2 = String.format("%s (%s)", title.replace(" Report", ""), criteria2.toUpperCase());
		Map<String, Object> mapParameters2 = new HashMap<String, Object>();
		mapParameters2.put("prepared_by", UserInfo.FullName );		
		mapParameters2.put("emp_id", "");
		mapParameters2.put("emp_name", "All");
		mapParameters2.put("div_id", "");
		mapParameters2.put("div_name", "All");		
		mapParameters2.put("dept_id", "");
		mapParameters2.put("dept_name", "All");
		mapParameters2.put("incharge_id", "");
		mapParameters2.put("incharge_name", "All");
		mapParameters2.put("module", "All");
		mapParameters2.put("error_no", 0);
		mapParameters2.put("error_name", "All");
		mapParameters2.put("status_no", 0);
		mapParameters2.put("status_name", "All");
		mapParameters2.put("report_no", 0);
		mapParameters2.put("report_name", "Pending Requests");	
		mapParameters2.put("prepared_by", UserInfo.FullName );
		mapParameters2.put("date_from",FncGlobal.dateFormat("2016-01-01"));
		mapParameters2.put("date_to",FncGlobal.dateFormat(FncGlobal.getDateSQL()));
		FncReport.generateReport("/Reports/rptDCRF_pending_all_requests_details.jasper", reportTitle2, company, mapParameters2);
		 */
	}


	//select, lookup and get statements		
	private String getDepartment(){

		String sql = "select dept_code as \"Dept Code\", " +
				"trim(dept_name) as \"Dept Name\", " +
				"dept_alias as \"Dept Alias\" " +
				"from mf_department " ;

		return sql;

	}	

	private String getModule(){

		//CHANGED 2023-04-
		String sql = 
				"select 'NEW MODULE' as \"Module\", 'FATALLO' as \"In-Charge\" \n\n" +
						"UNION ALL \n\n" +
						"(select  module_name as \"Module\", upper(c.first_name) as \"In-Charge\"  \n" +
						"from (select in_charge, module_name from mf_jsystem_modules where module_name in (select privileges from mf_privileges where emp_code =  '"+UserInfo.EmployeeCode+"' ) and status_id = 'A') a \n" +
						"left join em_employee b on a.in_charge = b.emp_code \n" + 
						"left join rf_entity c on b.entity_id = c.entity_id \n" +
						"order by module_name)" ;

		return sql;

	}	

	private String getEntityList(){

		String sql = 
				"select entity_id, trim(entity_name) " +
						"from rf_entity order by entity_name";		
		return sql;

	}

	private String getProject(){

		String sql = "select a.proj_id as \"Project ID\", " +
				"trim(a.proj_name) as \"Project Name\", " +
				"a.proj_alias as \"Project Alias\", " +
				"b.sub_proj_id as \"SubProject ID\", " +
				"a.vatable as \"Vatable\" " +
				"from mf_project a " +
				"left join ( select distinct on (proj_id) proj_id, sub_proj_id from mf_unit_info ) b  on a.proj_id=b.proj_id " +
				"where case when '"+co_id+"' = '' then a.co_id is not null else  " +
				"a.co_id = '"+co_id+"' end  " ;		
		return sql;

	}	

	private String getUnitList(){

		String sql = 			
				"select a.pbl_id, trim(a.description)  " +
						"from mf_unit_info a\r\n" + 
						"left join mf_project b on a.proj_id = b.proj_id \r\n" + 
						//"where a.status_id = 'A' \r\n" + 
						"WHERE case when '"+co_id+"' = '' then b.co_id is not null else b.co_id = '"+co_id+"' end  \r\n" + 
						"order by pbl_id::int\r\n" + 
						"" ;		

		return sql;

	}

	private Integer sql_getNextDCRFno() {

		Integer dcrf = null;

		String SQL = 
				"select max(coalesce(dcrf_no,0))+1 from rf_dcrf_header " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((Integer) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {dcrf = 1;}
			else {dcrf = Integer.parseInt(db.getResult()[0][0].toString()); }

		}else{
			dcrf = 1;
		}

		return dcrf;
	}

	private Object [] getDCRFdetails() {

		String strSQL = 
				"\r\n" + 
						"select \r\n" + 
						"\r\n" + 
						"a.dept_code,\r\n" + 					//0
						"trim(b.dept_name) as dept_name,\r\n" + //1
						"(case when a.edited_by = '' or a.edited_by is null then a.created_by else a.edited_by end) as prepared_by,\r\n" +  //2
						"trim(cc.entity_name) as prepared_name,\r\n" +  //3
						"a.received_by,\r\n" + 					//4
						"trim(dd.entity_name) as received_name,\r\n" +	//5 
						"a.approved_by,\r\n" + 					//6
						"trim(ee.entity_name) as approved_name,\r\n" +	//7 
						"a.fixed_by,\r\n" + 					//8
						"trim(ff.entity_name) as fixed_name,\r\n" +//9 
						"a.conformed_by,\r\n" + 					//10
						"trim(gg.entity_name) as conformed_name,\r\n" +  //11 
						"error_type,\r\n" + 					//12
						"trim(error_desc) as error_desc,\r\n" + //13
						"trim(user_remarks) as user_remarks,\r\n" +  //14 
						"trim(a.status) as status, \n" +			//15
						"(case when a.date_edited is not null then a.date_edited else a.date_created end) as prepared_date, \r\n" +  //16
						"a.date_needed," +						//17
						"a.date_received, "  + 					//18
						"a.date_approved, "  + 					//19
						"a.date_fixed, "  + 					//20
						"a.date_conformed, " +					//21
						"a.status,  "  + 						//22
						"trim(a.change_reason),  "  + 			//23
						"trim(a.admin_remarks)  "  + 			//24
						"\r\n" + 
						"from rf_dcrf_header a\r\n" + 
						"left join mf_department b on a.dept_code = b.dept_code\r\n" + 
						"left join em_employee c on (case when a.edited_by = '' or a.edited_by is null \n" +
						"		then a.created_by else a.edited_by end) = c.emp_code\r\n" + 
						"left join rf_entity cc on c.entity_id = cc.entity_id\r\n" + 
						"left join em_employee d on a.received_by = d.emp_code\r\n" + 
						"left join rf_entity dd on d.entity_id = dd.entity_id\r\n" + 
						"left join em_employee e on a.approved_by = e.emp_code\r\n" + 
						"left join rf_entity ee on e.entity_id = ee.entity_id\r\n" + 
						"left join em_employee f on a.fixed_by = f.emp_code\r\n" + 
						"left join rf_entity ff on f.entity_id = ff.entity_id\r\n" + 
						"left join em_employee g on a.conformed_by = g.emp_code\r\n" + 
						"left join rf_entity gg on g.entity_id = gg.entity_id\r\n" +
						"where a.dcrf_no = '"+dcrf_no+"' \n" +
						"" ;

		System.out.printf("SQL #1 getDRFdetails: %s", strSQL);
		pgSelect db = new pgSelect();
		db.select(strSQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}	

	private String getDCRF_no(){

		String sql = "\r\n" + 
				"select \r\n" + 
				"a.dcrf_no as\"DCRF No.\", \r\n" + 
				"trim(b.dept_alias) as \"Dept.\", \r\n" + 
				"upper(trim(cc.last_name)) as \"Prepared By\",\r\n" + 
				"to_char((case when a.date_edited is not null then a.date_edited else a.date_created end),'MM-dd-yyyy') as \"Prepared Date\" ,\r\n" + 
				"a.status  as \"Status\"," +
				"upper(trim(e.last_name)) as \"In-Charge\"," +
				"(case when a.date_conformed is not null then null else\n" + 
				"		case when a.date_conformed is null and a.dcrf_no::int < 54 and a.date_fixed is not null then null else \n" + 
				"		(EXTRACT(epoch FROM (now()-a.date_created))/86400)::int  end end) as \"Days Lapsed\",\n" + 
				"(case when a.date_conformed is not null then (EXTRACT(epoch FROM (a.date_conformed - a.date_created))/86400)::int else\n" + 
				"		case when a.date_conformed is null and a.dcrf_no::int < 54 and a.date_fixed is not null " +
				"		then (EXTRACT(epoch FROM (a.date_fixed - a.date_created))/86400)::int else null end end) as \"Days to Finish\", \r\n" + 
				"a.error_desc as \"Change Desc\", a.user_remarks as \"Remarks\", b.dept_code as \"Dept Code\", a.created_by as \"Requester\", \r\n"+ 
				"b.dept_head_code as \"Head\" \r\n" + 
				"from rf_dcrf_header a\r\n" + 
				"left join mf_department b on a.dept_code = b.dept_code\r\n" + 
				"left join em_employee c on (case when a.edited_by = '' then a.edited_by else a.created_by end) = c.emp_code\r\n" + 
				"left join rf_entity cc on c.entity_id = cc.entity_id\r\n" +
				"left join (select distinct on (dcrf_no) dcrf_no, module from rf_dcrf_detail where status_id != 'I') d on a.dcrf_no =d.dcrf_no \n" +
				"left join (select \n" + 
				"	a.module, a.privileges, a.parent, c.last_name \n" + 
				"	from ( select distinct on (privileges) * from mf_privileges where in_charge is not null ) a\n" + 
				"	left join em_employee b on a.in_charge = b.emp_code \n" + 
				"	left join rf_entity c on b.entity_id = c.entity_id\n" + 
				"	) e on upper(d.module) = upper(e.privileges)   " + 
				"order by a.dcrf_no desc \n\n" ;

		return sql;

	}	

	private Object [] sql_getDeptDtls() {

		String SQL = 
				"select " +
						"a.dept_code, " +
						"trim(b.dept_name) as dept_name  " +
						"from em_employee a \n" +
						"left join mf_department b on a.dept_code = b.dept_code " +
						"where a.emp_code = '"+UserInfo.EmployeeCode+"' " ;

		System.out.printf("SQL #1 sql_getDivId: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);		

		if(db.isNotNull()){
			return db.getResult()[0];
		}else{
			return null;
		}
	}

	private String sql_getCreatedBy() {

		String created_by = null;

		String SQL = 
				"select created_by from rf_dcrf_header where dcrf_no = "+dcrf_no+" " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {created_by = "";}
			else {created_by = db.getResult()[0][0].toString(); }

		}else{
			created_by = "";
		}

		return created_by;
	}

	private String sql_getUserID(String x) {

		String created_by = null;

		String SQL = 
				"select "+x+" from rf_dcrf_header where dcrf_no = "+dcrf_no+" " ;

		System.out.printf("SQL #1 sql_getUserID: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {created_by = "";}
			else {created_by = db.getResult()[0][0].toString(); }}
		else {created_by = "";}

		return created_by;
	}

	private Boolean isSPT_emp(String name){

		Boolean isSPT_emp = false;		

		String SQL = 
				"select distinct on (in_charge) in_charge, get_employee_name(in_charge) " +
						"from mf_privileges where in_charge is not null and in_charge = '"+name+"' " ;

		System.out.printf("SQL #1: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			isSPT_emp = true;
		}else{
			isSPT_emp = false;
		}

		return isSPT_emp;

	}

	private Boolean sql_isSPT_emp() {
		Boolean spt_emp = null;

		/*	Added by Mann2x; Date Added: June 11, 2019; Special bypass to allow Sir Del to receive DCRFs;
		String SQL = "select emp_code from em_employee where dept_code = '98' and terminate_date is null and emp_code = '"+UserInfo.EmployeeCode+"' \n" ;
		 */

		String SQL = "select emp_code \n" +
				"from em_employee \n" +
				"where (dept_code = '98' or emp_code in  ('900449','901101', '900562')  ) and terminate_date is null \n" +
				"and emp_code = '"+UserInfo.EmployeeCode+"' \n" ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if (db.isNotNull()) {
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {spt_emp = false;}
			else {spt_emp = true; }
		} else {
			spt_emp = false;
		}

		return spt_emp;
	}

	@SuppressWarnings("unused")
	private String sql_getDeptHead(String x) {

		String deptHead = null;

		String SQL = 
				"select \n" + 
						"b.dept_head_code\n" + 
						"from \n" + 
						"(select * from em_employee where emp_code = '"+x+"') a\n" + 
						"left join mf_department b on a.dept_code = b.dept_code " ;

		System.out.printf("SQL #1 sql_getDeptHead: %s", SQL);
		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {deptHead = "";}
			else {deptHead = db.getResult()[0][0].toString(); }}
		else {deptHead = "";}

		return deptHead;
	}


	//table operations	
	private void adjustRowHeight_account(_JTableMain table){//
		int rw = table.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			table.setRowHeight(x, 22);			
			x++;
		}

	}

	private void clickTableColumn() {

		int column = tblChangeDetails.getSelectedColumn();
		int row = tblChangeDetails.getSelectedRow();		

		System.out.printf("column : " + column);
		System.out.printf("row : " + row);

		Integer x[] = {0,1,2,3,4,5,6,7,8};
		String sql[] = {"",getModule(),"",getEntityList(),SQL_COMPANY(),getProject(),getUnitList(),"",""};
		String lookup_name[] = {"Module","","Entity","","Company","Project","Unit","",""};			

		if (column == x[column] && modelChangeDetails.isEditable() && sql[column]!="") {  
			_JLookupTable dlg = new _JLookupTable(FncGlobal.homeMDI, null, lookup_name[column], sql[column], false);
			dlg.setLocationRelativeTo(FncGlobal.homeMDI);			
			dlg.setVisible(true);

			if (column == 1){dlg.setFilterClientName(false);} else {dlg.setFilterClientName(true);}

			Object[] data = dlg.getReturnDataSet();
			if (data != null && column == 1) {		

				String prev_incharge = "";
				if (row==0)
				{
					modelChangeDetails.setValueAt(data[0], row, column);
					modelChangeDetails.setValueAt(data[1], row, 9);
				}		
				else if (row>0)
				{
					prev_incharge = modelChangeDetails.getValueAt(row-1, 9).toString();
					if (!prev_incharge.equals(data[1].toString()))
					{
						JOptionPane.showMessageDialog(getContentPane(),"DCRF cannot have multiple in-charge, \n" +
								"Please select different module or create another DCRF.","Error",JOptionPane.ERROR_MESSAGE);
						modelChangeDetails.setValueAt("", row, column);
						modelChangeDetails.setValueAt("", row, 9);
					}
					else
					{
						modelChangeDetails.setValueAt(data[0], row, column);
						modelChangeDetails.setValueAt(data[1], row, 9);
					}
				}				
			} 
			else if (data != null && column == 3)  {	
				entity_id = data[0].toString();
				modelChangeDetails.setValueAt(data[1], row, column);
			}
			else if (data != null && column == 4)  {	
				co_id = data[0].toString();
				modelChangeDetails.setValueAt(data[2], row, column);
				modelChangeDetails.setValueAt("", row, column+1);
				modelChangeDetails.setValueAt("", row, column+2);
			}
			else if (data != null && column == 5)  {	
				proj_id = data[0].toString();
				modelChangeDetails.setValueAt(data[2], row, column);
			}
			else if (data != null && column == 6)  {	
				pbl_id = data[0].toString();
				modelChangeDetails.setValueAt(data[1], row, column);
			}
		}

		else {}	

		tblChangeDetails.packAll();
	}

	private void AddExtraRows(){//used		

		int w = tblChangeDetails.getRowCount() + 1;
		int x = 0;
		int line_no = 0;

		while (x<=20)
		{
			line_no = w + x;
			modelChangeDetails.addRow(new Object[] { line_no, "","","","","","","",""});

			x++;
		}

		adjustRowModel();		
	}

	private void adjustRowModel(){
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeaderChangeDetails.setModel(listModel);
		Integer r = tblChangeDetails.getRowCount();
		Integer s = 1;
		while (s<=r)
		{
			listModel.addElement(s);		
			s++;
		}		
		adjustRowHeight_account(tblChangeDetails);
	}

	private void insertDCRF_header(pgUpdate db){

		String other_error = "";
		if (txtOtherError.getText().trim().equals("[ Other Error Description ]")) {other_error = "";} 
		else {other_error = txtOtherError.getText().trim().replace("'", "''");}

		String sqlDetail = 
				"INSERT into rf_dcrf_header values (" +
						""+dcrf_no+",  \n" +  						//1	dcrf_no					
						"'"+dept_id+"',  \n" +						//2 dept_code
						""+cmbError.getSelectedIndex()+",  \n" +	//3 error_type
						"'"+other_error+"',  \n" +					//4 error_desc
						"'"+txtReason.getText().replace("'", "''")+"',  \n" +			//5 change_reason
						"'"+txtUserRemark.getText().replace("'", "''")+"',  \n" +		//6 user_remarks
						"'',  \n" +									//7 admin_remarks
						"'"+UserInfo.EmployeeCode+"',  \n" +		//8 created_by
						"now(),  \n" +  							//9 date_created
						"'"+dteNeeded.getDate()+"',  \n" +  		//10 date_needed
						"null,  \n" +  								//11 edited_by
						"null,  \n" +  								//12 date_edited
						"null,  \n" +  								//13 noted_by
						"null,  \n" +  								//14 date_noted
						"null,  \n" +  								//15 approved_by
						"null,  \n" +  								//16 date_approved
						"null,  \n" +  								//17 fixed_by
						"null,  \n" +  								//18 date_fixed
						"null,  \n" +  								//19 reviewed_by
						"null,  \n" +  								//20 date_reviewed
						"'ACTIVE'  \n" +  							//21 status						
						")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void updateDCRF_header_user(pgUpdate db){

		String other_error = "";
		if (txtOtherError.getText().trim().equals("[ Other Error Description ]")) {other_error = "";}
		else {other_error = txtOtherError.getText().trim().replace("'", "''");}

		String sqlDetail = 
				"update rf_dcrf_header set \n" +	
						"error_type = "+cmbError.getSelectedIndex()+",  \n" + //3 error_type
						"error_desc = '"+other_error+"',  \n" +					//4 error_desc
						"change_reason = '"+txtReason.getText().replace("'", "''")+"',  \n" +		//5 change_reason
						"user_remarks = '"+txtUserRemark.getText().replace("'", "''")+"',  \n" +	//6 user_remarks
						"edited_by = '"+UserInfo.EmployeeCode+"',  \n" +		//8 created_by
						"date_edited = '"+dtePrepared.getDate()+"',  \n" +  	//9 date_created
						"date_needed = '"+dteNeeded.getDate()+"'  \n" +  		//10 date_needed
						"where dcrf_no = "+dcrf_no+"  \n"  ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void updateDCRF_header_admin(pgUpdate db){

		String sqlDetail = 
				"update rf_dcrf_header set \n" +	
						"admin_remarks = '"+txtAdminRemarks.getText().replace("'", "''")+"'  \n" +	//6 admin_remarks
						"where dcrf_no = "+dcrf_no+"  \n"  ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void returnDCRFstatus(pgUpdate db, String new_status){

		String sqlDetail = 
				"update rf_dcrf_header set \n" +	
						"status = '"+new_status+"',  \n" ;
		if (status.equals("COMPLETED")){sqlDetail = sqlDetail + "conformed_by = null, date_conformed = null \n";}
		if (status.equals("FIXED")){sqlDetail = sqlDetail + "fixed_by = null, date_fixed = null \n";}
		if (status.equals("APPROVED")){sqlDetail = sqlDetail + "approved_by = null, date_approved = null \n";}
		if (status.equals("TAGGED")){sqlDetail = sqlDetail + "noted_by = null, date_noted = null \n";}
		if (status.equals("RECEIVED")){sqlDetail = sqlDetail + "received_by = null, date_received = null \n";}
		sqlDetail = sqlDetail +			
				"where dcrf_no = "+dcrf_no+"  \n"  ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private static String GetIDs(String strType, String strVal){
		String strRet = "";
		String SQL = "";


		if (strType.equals("ID")) {SQL = "SELECT entity_id FROM rf_entity WHERE entity_name = '"+strVal+"'";}
		else if (strType.equals("Company")) {SQL = "SELECT co_id FROM mf_company WHERE company_alias = '"+strVal+"'";}
		else if (strType.equals("Project")) {SQL = "SELECT proj_id FROM mf_project WHERE proj_alias = '"+strVal+"'";}
		else if (strType.equals("Unit")) {SQL = "SELECT pbl_id FROM mf_unit_info WHERE description = '"+strVal+"'";}		

		/*switch (strType) {
		case "ID":

		case "Company":
			SQL = "SELECT co_id FROM mf_company WHERE company_alias = '"+strVal+"'";
			break;
		case "Project":
			SQL = "SELECT proj_id FROM mf_project WHERE proj_alias = '"+strVal+"'";
			break;
		case "Unit":
			SQL = "SELECT pbl_id FROM mf_unit_info WHERE description = '"+strVal+"'";
			break;
		}*/

		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if(sqlExec.isNotNull()){
			strRet = (String) sqlExec.getResult()[0][0];
		}
		else{
			strRet = "";
		}

		return strRet;
	}

	private void insertDCRF_detail(pgUpdate db){
		int rw = tblChangeDetails.getModel().getRowCount();	
		int x = 0;
		int line_no = 0;

		System.out.println("");
		System.out.println("-insertDCRF_detail- Method");
		System.out.println("rw Value	-	" + rw);

		while (x < rw) {		
			String module = "";
			try{
				module = modelChangeDetails.getValueAt(x, 1).toString();
				System.out.println("");
				System.out.println("Module		-	" + modelChangeDetails.getValueAt(x, 1).toString());
			}
			catch(NullPointerException e){
				System.out.println("");
				System.out.println("Module name cannot be blank.");
			}

			System.out.println("");
			System.out.println("module		-	" + module);

			if (module.equals("")){
				System.out.println("");
				System.out.println("Module name cannot be blank.");
			}
			else {							
				String doc_name 	= "";
				String change_from  = "";
				String change_to  	= "";
				line_no = x + 1;

				String strID = "";
				String strCoID = "";
				String strProject = "";
				String strUnit = "";

				/*	
				 *	Modified by Mann2x.
				 *	Modification date: September 1, 2016; 
				 *	Upon modification, all variable assignment procedures are retained
				 *	except for the inclusion of the "try" and "catch" to handle exceptions
				 *	such as when the user decides to leave some columns blank.
				 */

				/**	Modification Mark Begin	**/
				try{
					strID = GetIDs("ID", modelChangeDetails.getValueAt(x, 3).toString());
				}
				catch(NullPointerException e){
					System.out.println("");
					System.out.println("Entity name cannot be blank.");
					strID = "";
				}

				try{
					strCoID = GetIDs("Company", modelChangeDetails.getValueAt(x, 4).toString());
				}
				catch(NullPointerException e){
					System.out.println("");
					System.out.println("Company cannot be blank.");
					//strCoID = "";
				}

				try{
					strProject = GetIDs("Project", modelChangeDetails.getValueAt(x, 5).toString());
				}
				catch(NullPointerException e){
					System.out.println("");
					System.out.println("Project cannot be blank.");
					//strProject = "";
				}

				//				try{
				//					strUnit = GetIDs("Unit", modelChangeDetails.getValueAt(x, 6).toString());
				//				}
				//				catch(NullPointerException e){
				//					System.out.println("");
				//					System.out.println("Unit cannot be blank.");
				//					strUnit = "";
				//				}
				try{
					strUnit = unit_pbl(strProject,modelChangeDetails.getValueAt(x, 6).toString());
				}
				catch(NullPointerException e){
					System.out.println("");
					System.out.println("Unit cannot be blank.");
					strUnit = "";
				}

				try{
					doc_name = modelChangeDetails.getValueAt(x,2).toString().replace("'", "''");
				} 
				catch(NullPointerException e){
					doc_name = "";
				}	

				try{
					change_from	= modelChangeDetails.getValueAt(x,7).toString().replace("'", "''");
				}
				catch (NullPointerException e){
					change_from	= "";
				}	

				try{
					change_to = modelChangeDetails.getValueAt(x,8).toString().replace("'", "''");
				}
				catch (NullPointerException e){
					change_to = "";
				}				
				/**	Modification Mark End	**/

				/*	
				 * Modified by Mann2x in the event that
				 * each row has distinct values for the company, 
				 * project, client and unit columns.
				 * 
					String sqlDetail = 
					"INSERT into rf_dcrf_detail values (" +
					""+line_no+",  \n" +  			//0 	line_no
					""+dcrf_no+",  \n" +  			//1 	dcrf_no
					"'"+module+"',  \n" +			//2 	module
					"'"+doc_name+"',  \n" +			//3 	doc_name
					"'"+entity_id+"',  \n" + 		//4		entity_id				
					"'"+co_id+"',  \n" + 			//5		co_id	 		
					"'"+proj_id+"',  \n" + 		//6		proj_id	 	
					"'"+strUnit+"',  \n" + 			//7		pbl_id	 	
					"'"+change_from+"',  \n" + 		//8		change_from	
					"'"+change_to+"',  \n" + 		//9		change_to
					"'A'  \n" + 					//10	status_id
					")   " ;
				 */
				//				System.out.printf("UNIT:" + unit_pbl(strProject,modelChangeDetails.getValueAt(x, 6).toString()));

				String sqlDetail = 
						"INSERT into rf_dcrf_detail values (" +
								""+line_no+",  \n" +  		//0 line_no
								""+dcrf_no+",  \n" +  		//1 dcrf_no
								"'"+module+"',  \n" +		//2 module
								"'"+doc_name+"',  \n" +		//3 doc_name
								"'"+strID+"',  \n" + 		//4	entity_id				
								//								"'"+co_id+"',  \n" + 			//5	co_id	 		
								//								"'"+proj_id+"',  \n" + 		//6	proj_id	 	
								"'"+strCoID+"',  \n" + 			//5	co_id	 		
								"'"+strProject+"',  \n" + 		//6	proj_id	 
								"'"+strUnit+"',\n" + //7	pbl_id	 	
								//								"'"+unit_pbl(strProject,modelChangeDetails.getValueAt(x, 6).toString())+"',\n" + //7	pbl_id	 	
								"'"+change_from+"',  \n" + 	//8	change_from	
								"'"+change_to+"',  \n" + 	//9	change_to
								"'A'  \n" + 				//10	status_id
								")   " ;

				System.out.println("");
				System.out.printf("SQL #1: %s", sqlDetail);
				db.executeUpdate(sqlDetail, false);	
			}
			x++;
		}
	}		

	private void updateDCRF_detail(pgUpdate db){

		String sqlDetail = 
				"update rf_dcrf_detail set " +	
						"status_id = 'I' \n" + 
						"where dcrf_no = "+dcrf_no+"  \n" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	private void tagDCRF_header(pgUpdate db){

		String sqlDetail = 
				"update rf_dcrf_header set " +
						"status = 'RECEIVED',  \n" +		
						"received_by = '"+UserInfo.EmployeeCode+"' , \n" +	
						"date_received = now() \n" +	
						"where dcrf_no = '"+dcrf_no+"'" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	private void approveDCRF_header(pgUpdate db){

		Boolean blnDiv = FncGlobal.GetBoolean("select require_approval \n" + 
				"from mf_division_dcrf_preference \n" + 
				"where div_code = '"+UserInfo.Division+"'");  

		String sqlDetail = "update rf_dcrf_header \n" +
				"set status = 'APPROVED', \n" +		
				"approved_by = '"+UserInfo.EmployeeCode+"', \n" +	
				"app_dept_head_date = now(), \n" +
				"date_approved = now(), \n" +
				"require_div_head = "+blnDiv+" \n" +
				"where dcrf_no = '"+dcrf_no+"'" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);
		
		if(UserInfo.Division.equals("31")) {
			String sqlDetail2 = "update rf_dcrf_header \n"
					+ "set app_div_head = '900267', \n"
					+ "app_div_head_date = now() \n"
					+ "where dcrf_no = '"+dcrf_no+"'" ;
			
			db.executeUpdate(sqlDetail2, false);
		}
	}

	private void fixDCRF_header(pgUpdate db){

		String sqlDetail = 
				"update rf_dcrf_header set " +
						"status = 'FIXED',  \n" +		
						"fixed_by = '"+UserInfo.EmployeeCode+"' , \n" +	
						"admin_remarks = '"+txtAdminRemarks.getText().replace("'", "''")+"' , \n" +	
						"date_fixed = now() \n" +	
						"where dcrf_no = '"+dcrf_no+"'" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	private void conformDCRF_header(pgUpdate db){

		String sqlDetail = 
				"update rf_dcrf_header set " +
						"status = 'COMPLETED',  \n" +		
						"conformed_by = '"+UserInfo.EmployeeCode+"' , \n" +	
						"admin_remarks = '"+txtAdminRemarks.getText().replace("'", "''")+"' , \n" +	
						"date_conformed = '"+Calendar.getInstance().getTime()+"' \n" +	
						"where dcrf_no = '"+dcrf_no+"'" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	private void deleteDCRF_header(pgUpdate db){

		String sqlDetail = 
				"update rf_dcrf_header set " +
						"status = 'INACTIVE',  \n" +	
						"date_deleted = now() \n" +	
						"where dcrf_no = '"+dcrf_no+"'" ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	private void deleteDCRF_row(){//used

		int[] selectedRows = tblChangeDetails.getSelectedRows();

		if (selectedRows.length == 0) {
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please select row in Work Item(s).", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		for (int x = selectedRows.length - 1; x > -1; x--) {
			int row = selectedRows[x];

			modelChangeDetails.removeRow(row);
			((DefaultListModel) rowHeaderChangeDetails.getModel()).removeElementAt(row);			
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_F2){
			clickTableColumn();
		}		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	public class PopupTriggerListener_panel1 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				if(btnSave.getText().equals("Save")){mniRemoveLine.setEnabled(true);					
				} else {mniRemoveLine.setEnabled(false);}
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu2.show(ev.getComponent(), ev.getX(), ev.getY());
				if(btnSave.getText().equals("Save")){mniRemoveLine.setEnabled(true);					
				} else {mniRemoveLine.setEnabled(false);}
			}
		}
	}

	public class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				if (status.equals("ACTIVE")||status.equals("TAGGED")||status.equals("APPROVED"))
				{
					mniEditDatePrep.setEnabled(true);					
				}
				else
				{
					mniEditDatePrep.setEnabled(false);			
				}
				if (status.equals("DELETED")||status.equals("INACTIVE")||status.equals("ACTIVE"))
				{
					mniReturn.setEnabled(false);	
				}
				else
				{
					mniReturn.setEnabled(true);	
				}
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				if (status.equals("ACTIVE")||status.equals("TAGGED")||status.equals("APPROVED"))
				{
					mniEditDatePrep.setEnabled(true);					
				}
				else
				{
					mniEditDatePrep.setEnabled(false);			
				}
				if (status.equals("DELETED")||status.equals("INACTIVE")||status.equals("ACTIVE"))
				{
					mniReturn.setEnabled(false);	
				}
				else
				{
					mniReturn.setEnabled(true);	
				}
			}
		}
	}

	private void Notification() {
		/*	Added by Mann2x; Date Added: November 23, 2018; To remind user's that a DCRF should be APPROVED first before submitting to the SPT;	*/
		String strMsg = "Please approve or have the DCRF approved by \n"+
				"clicking the `APPROVE` button in the `Data Change Request` facility \n"+
				"before submitting to the SPT. Unapproved DCRFs will not be \n"+
				"executed. Thank you!";
		JOptionPane.showMessageDialog(getContentPane(), strMsg, "Notice", JOptionPane.INFORMATION_MESSAGE);
	}

	static MouseListener mListener = new MouseListener() {
		@SuppressWarnings("static-access")
		public void mouseClicked(MouseEvent e) {
			if (e.getComponent() instanceof JXPanel) {
				if (((JXPanel) e.getComponent()).getToolTipText().equals("Boss Frame")) {

				} else if (((JXPanel) e.getComponent()).getToolTipText().equals("Help")) {

				} else if (((JXPanel) e.getComponent()).getToolTipText().equals("Exit")) {
					if (JOptionPane.showConfirmDialog(null, "Exit JRequest?", "Exit", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} else if (((JXPanel) e.getComponent()).getToolTipText().equals("Boss Frame")) {
					JOptionPane.showMessageDialog(null, "No function yet");
				}
			}
		}

		public void mouseExited(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}
	};

	private void InitializeSurvey() {
		if (!FncGlobal.GetBoolean("select exists(select * \n" +
				"from rf_department_module_relation \n" + 
				"where emp_code = '"+UserInfo.EmployeeCode+"' and module = '"+modelChangeDetails.getValueAt(0, 1).toString()+"')")) {
			Integer intRow = 0;
			pgUpdate dbInsert = new pgUpdate();

			pgSelect dbExec = new pgSelect(); 
			dbExec.select("select a.dept_code, a.dept_name \n" + 
					"from mf_department a \n" + 
					"where a.division_code not in ('06', '08', '09', '29', '07') \n" + 
					"and not exists(select * from em_employee x where x.emp_code = '"+UserInfo.EmployeeCode+"' and x.dept_code = a.dept_code) \n" + 
					"order by a.dept_code || ' - ' || a.dept_name");

			chkDept = new JCheckBox[dbExec.getRowCount()];

			panSurvey = new JXPanel(new BorderLayout(5, 5)); 
			panSurvey.setPreferredSize(new Dimension(750, 350));
			{
				{
					JXPanel panPage = new JXPanel(new BorderLayout(5, 5)); 
					panSurvey.add(panPage, BorderLayout.PAGE_START); 
					panPage.setPreferredSize(new Dimension(0, 60));
					{
						JLabel lblSurvey = new JLabel("<html>Which among the departments listed below do you think are also concerned or would be affected by the changes<br>indicated in this DCRF? Please answer to the best of your knowledge. Multiple selection is allowed.</html>");
						panPage.add(lblSurvey, BorderLayout.CENTER); 
					}
				}
				{
					JXPanel panBorder = new JXPanel(new BorderLayout(5, 5)); 
					panSurvey.add(panBorder, BorderLayout.CENTER);
					panBorder.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Departments"));
					{
						JXPanel panDiv = new JXPanel(new GridLayout(1, 2, 5, 5)); 
						panBorder.add(panDiv, BorderLayout.CENTER); 
						panDiv.setBorder(new EmptyBorder(5, 5, 5, 5));
						{
							{
								JXPanel panDiv1 = new JXPanel(new GridLayout((dbExec.getRowCount()/2)+1, 1, 5, 5)); 
								panDiv.add(panDiv1); 
								{
									while (intRow<((dbExec.getRowCount()/2)+1)) {
										chkDept[intRow] = new JCheckBox(dbExec.getResult()[intRow][1].toString()); 
										panDiv1.add(chkDept[intRow], BorderLayout.CENTER);
										chkDept[intRow].setName(dbExec.getResult()[intRow][1].toString());
										intRow++;
									}
								}
							}
							{
								JXPanel panDiv2 = new JXPanel(new GridLayout((dbExec.getRowCount()/2)+1, 1, 5, 5)); 
								panDiv.add(panDiv2); 
								{
									while (intRow<dbExec.getRowCount()) {
										chkDept[intRow] = new JCheckBox(dbExec.getResult()[intRow][1].toString()); 
										panDiv2.add(chkDept[intRow]); 
										chkDept[intRow].setName(dbExec.getResult()[intRow][1].toString());
										intRow++;
									}
								}
							}
						}	
					}
				}

				for (int x=0; x<dbExec.getRowCount(); x++) {
				}
			}

			JOptionPane.showOptionDialog(pnlMain, panSurvey, "Quick Survey", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {"			CONFIRM			"}, JOptionPane.OK_OPTION);
			GetDept(panSurvey); 
//			strDept = strDept.trim().substring(0, strDept.trim().length()-1); 

			for (int x=0; x<modelChangeDetails.getRowCount(); x++) {
				dbInsert = new pgUpdate();

				if (!modelChangeDetails.getValueAt(x, 1).toString().equals("")) {
					if (FncGlobal.GetBoolean("select exists(select * \n" +
							"from rf_department_module_relation \n" + 
							"where emp_code = '"+UserInfo.EmployeeCode+"' and module = '"+modelChangeDetails.getValueAt(x, 1).toString()+"')")) {
						dbInsert.executeUpdate("update rf_department_module_relation\n" + 
								"set department = ('{'|| '"+strDept+"' || '}')::varchar[] \n" + 
								"where emp_code = '"+UserInfo.EmployeeCode+"' and module = '"+modelChangeDetails.getValueAt(x, 1).toString()+"'", true);
					} else {
						dbInsert.executeUpdate("insert into rf_department_module_relation values \n" +
								"((select coalesce(max(rec_id), 0)+1 from rf_department_module_relation), \n" +
								"'"+UserInfo.EmployeeCode+"', \n" + 
								"'"+modelChangeDetails.getValueAt(x, 1).toString()+"', \n" +
								"('{'||'"+strDept+"'||'}')::varchar[]); ", true);
						dbInsert.commit();	
					}
				}
			}

			strDept = "";	
		}
	}

	private void GetDept(JXPanel panel) {
		for (Component compo : panel.getComponents()) {
			if (compo instanceof JCheckBox) { 
				if (((JCheckBox) compo).isSelected()) {
					System.out.println("Dept.: "+compo.getName());

					strDept = strDept + FncGlobal.GetString("select dept_code from mf_department where dept_name = '"+compo.getName()+"'") + ","; 
				}
			} else if (compo instanceof JXPanel) {
				GetDept((JXPanel) compo); 
			}
		}
	}

	@SuppressWarnings("unused")
	private String filterDept(String emp_code){
		String dept_code = "";
		pgSelect db = new pgSelect();

		String query = "SELECT b.dept_head_code,b.dept_name FROM em_employee a LEFT JOIN mf_department b ON a.dept_code = b.dept_code WHERE emp_code = '"+emp_code+"'";
		db.select(query);
		FncSystem.out("Display SQL for filter dept", query);

		if (db.isNotNull()){
			if ((String) db.getResult()[0][0] == null || db.getResult()[0][0].equals("null")){
			}else{
				dept_code = (String) db.getResult()[0][0].toString();
				System.out.printf("The employee code is: %s",dept_code);
			}
		}else{
			dept_code = "";
		}
		return dept_code;


	}

	public void dialogDispose() {
		dialog.dispose();
	}
	private Boolean vcooApproved(){
		pgSelect db = new pgSelect();
		String dcrf_no = lookupDCRF.getValue();
		System.out.println("dcrf noj"+dcrf_no);

		String query = "SELECT * FROM rf_dcrf_header where app_vcoo_date IS NOT NULL AND dcrf_no = '"+dcrf_no+"'";

		FncSystem.out("jervin", query);;

		db.select(query);

		if(db.isNotNull()) {	
			return true;
		}
		else {
			return false;
		}

	}
	private String unit_pbl(String proj_id,String description){
		String pbl_id = "";
		pgSelect db = new pgSelect();

		String query = "SELECT pbl_id FROM mf_unit_info WHERE description = '"+description+"' and proj_id = '"+proj_id+"'";
		db.select(query);
		FncSystem.out("Display SQL for filter dept", query);

		if (db.isNotNull()){

			pbl_id = (String) db.getResult()[0][0].toString();
			System.out.printf("The employee code is: %s",pbl_id);

		}
		else {	

			pbl_id = "";
		}

		return pbl_id;


	}
	private void attachment_Func(){
		if (JOptionPane.showConfirmDialog(getContentPane(), "Do you have attachment/s?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			dialog = new JDialog(FncGlobal.homeMDI, "DCRF Monitoring", false); 
			dialog.setLayout(new BorderLayout(0, 0));
			dialog.setSize(350, 300);
			dialog.setResizable(false);

			DCRFAttachments dcrfMon = new DCRFAttachments(lookupDCRF.getValue());
			dialog.add(dcrfMon, BorderLayout.CENTER);

			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final Dimension screenSize = toolkit.getScreenSize();

			final int x = (screenSize.width - dialog.getWidth()) / 2;
			final int y = (screenSize.height - dialog.getHeight()) / 2;

			dialog.setLocation(x, y);
			dialog.setVisible(true);
		}
			
		
//			JOptionPane.showMessageDialog(null, "Attachments successfully added!");
		
	}
}
