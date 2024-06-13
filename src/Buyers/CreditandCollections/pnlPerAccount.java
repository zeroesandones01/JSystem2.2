package Buyers.CreditandCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;

import FormattedTextField._JXFormattedTextField;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;

import components._JButton;
import components._JXTextField;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class pnlPerAccount extends JXPanel implements _GUI, LookupListener,ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private _FPromissoryNoteCommintment methods = new _FPromissoryNoteCommintment();

	private JXPanel pnlMain;
	private pnlCancellation_Active cb;
	private JPanel pnlNorth;
	private JXPanel pnlWest;
	private JLabel lblProj_Pa;
	private _JLookup lookupProj_Pa;
	private _JXTextField txtProj_Pa;
	private JXPanel pnlEast;
	private JLabel lblNote1;
	private JXPanel pnlCenter;
	private JLabel lblClientID;
	private JLabel lblPBL;
	private JLabel lblStage;
	private JLabel lblMonthPD;
	private JLabel lblDefaultDate;
	private JLabel lblCanclType;
	private JLabel lblAmountDue;
	private JLabel lblReason;
	private JXPanel pnlSouth;
	private _JLookup lookupClientName;
	private _JXTextField txtClientName;
	private JTextField txtPBLID;
	private JTextField txtPBLDesc;
	private JTextField txtStage;
	private JTextField txtMonthPD;
	private JTextField txtDefaultDate;
	private DefaultComboBoxModel cmbCancelTypeModel_Pa;
	private JComboBox cmbCancelType_Pa;
	private _JXFormattedTextField txtAmountDue;
	private _JLookup lookupReason;
	private JTextField txtReason;
	private _FCancellationProcessing functions= new _FCancellationProcessing();
	private ButtonGroup grpButton = new ButtonGroup();
	private JPanel pnlSouth_Center;
	private JPanel pnlCenter_Approved;
	private JLabel lblApproveBy_Active;
	private _JLookup lookupApproveby;
	private JTextField txtApprovedBy;
	private JPanel pnlSouth_East;
	private _JButton btnNew;
	private _JButton btnEdit;
	private _JButton btnSave;
	private _JButton btnPost;
	private _JButton btnClear;
	private JLabel lblRemarks;
	private JXPanel pnlEast_C;

	public pnlPerAccount(pnlCancellation_Active cb) {
		this.cb = cb;
		initGUI();
	}

	public pnlPerAccount(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public pnlPerAccount(LayoutManager layout) {
		super(layout);
	}

	public pnlPerAccount(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		{

			pnlMain = new JXPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			{
				{
					pnlNorth = new  JPanel(new BorderLayout(5, 5));
					this.add(pnlNorth, BorderLayout.NORTH);
					pnlNorth.setPreferredSize(new Dimension(this.getWidth(),35));
					{

						{
							JPanel pnlSpace = new  JPanel(new BorderLayout(5, 5));
							pnlNorth.add(pnlSpace,BorderLayout.NORTH);
						}
						{
							pnlWest = new JXPanel(new BorderLayout(5,5));
							pnlNorth.add(pnlWest,BorderLayout.WEST);
							pnlWest.setPreferredSize(new Dimension(500, 30));	
							{
								lblProj_Pa = new JLabel(" Project :");
								pnlWest.add(lblProj_Pa,BorderLayout.WEST);
								lblProj_Pa.setPreferredSize(new Dimension(114, 20));

							}
							{
								JPanel pnlProj = new JPanel(new BorderLayout(3,3));
								pnlWest.add(pnlProj,BorderLayout.CENTER);
								pnlProj.setPreferredSize(new Dimension(74, 20));
								{
									{

										lookupProj_Pa = new _JLookup("ID", "Search Project", 0) ;  
										pnlProj.add(lookupProj_Pa,BorderLayout.WEST);
										lookupProj_Pa.addLookupListener(this);
										lookupProj_Pa.setReturnColumn(0);
										lookupProj_Pa.setLookupSQL(methods.getCompany());
										lookupProj_Pa.setPreferredSize(new Dimension(100, 20));
									}
									{
										txtProj_Pa = new _JXTextField();
										pnlProj.add(txtProj_Pa,BorderLayout.CENTER);
										txtProj_Pa.setEditable(false);
										txtProj_Pa.setPrompt("Project Name");
										txtProj_Pa.setPreferredSize(new Dimension(100, 20));
									}
								}
								{

									pnlEast = new JXPanel(new BorderLayout());
									pnlNorth.add(pnlEast,BorderLayout.EAST);
									pnlEast.setPreferredSize(new Dimension(400, 30));
									{

										lblNote1 = new JLabel("<html><font color=red><i>Note :    Selected Client is not Recommended for Cancellation</i></font></html>");
										pnlEast.add(lblNote1,BorderLayout.EAST);
										lblNote1.setVisible(false);

									}
								}
							}
						}
					}
				}
				{
					pnlCenter = new JXPanel(new BorderLayout(5,5));
					pnlMain.add(pnlCenter,BorderLayout.CENTER);
					{
						JPanel pnlLabel = new JPanel(new GridLayout(8, 1, 3, 3));
						pnlCenter.add(pnlLabel,BorderLayout.WEST);
						{
							{
								lblClientID = new JLabel("Client ID");
								pnlLabel.add(lblClientID);
							}
							{
								lblPBL = new JLabel(" Ph/Bl/Lt");
								pnlLabel.add(lblPBL);
								lblPBL.setPreferredSize(new Dimension(84, 25));
							}
							{ 
								lblStage= new JLabel(" Stage");
								pnlLabel.add(lblStage);
							}
							{
								lblMonthPD = new JLabel(" Months PD");
								pnlLabel.add(lblMonthPD);
							}
							{
								lblDefaultDate= new JLabel(" Default Date");
								pnlLabel.add(lblDefaultDate);
							}
							{
								lblCanclType = new JLabel(" Cancellation Type");
								pnlLabel.add(lblCanclType);
							}
							{
								lblAmountDue= new JLabel(" Amount Due");
								pnlLabel.add(lblAmountDue);
							}
							{
								lblReason= new JLabel(" Reason");
								pnlLabel.add(lblReason);
							}
						}
					}
					{

						JPanel pnlCenter_Field = new JPanel(new GridLayout(8, 1, 3, 3));
						pnlCenter.add(pnlCenter_Field,BorderLayout.CENTER);	
						{

							{
								JPanel pnlLookUp = new JPanel(new BorderLayout(3,3));
								pnlCenter_Field.add(pnlLookUp,BorderLayout.CENTER);
								{
									lookupClientName = new _JLookup("ID", "Search Client", 0);
									pnlLookUp.add(lookupClientName,BorderLayout.WEST);
									lookupClientName.setPreferredSize(new Dimension(100, 0));
									lookupClientName.addLookupListener(this);
								}
								{
									txtClientName = new _JXTextField();
									pnlLookUp.add(txtClientName,BorderLayout.CENTER);
									txtClientName.setEditable(false);
									txtClientName.setPrompt("Client Name");
								}
							}
							{
								JPanel pnlPBL = new JPanel(new BorderLayout(3,3));
								pnlCenter_Field.add( pnlPBL,BorderLayout.CENTER);			
								{
									{ 
										txtPBLID = new JTextField();
										pnlPBL.add(txtPBLID, BorderLayout.WEST);
										txtPBLID.setPreferredSize(new Dimension(100, 0));
										txtPBLID.setEditable(false);
										txtPBLID.setHorizontalAlignment(JTextField.CENTER);
									}
									{
										txtPBLDesc = new JTextField();
										pnlPBL.add(txtPBLDesc);
										txtPBLDesc.setEditable(false);
									}
								}
							}
							{
								JPanel pnlStage = new JPanel(new BorderLayout(3, 3));
								pnlCenter_Field.add(pnlStage);
								{
									txtStage = new JTextField();
									pnlStage.add(txtStage);
									txtStage.setPreferredSize(new Dimension(500, 0));
									txtStage.setEditable(false);
								}
							}
							{
								txtMonthPD = new JTextField();
								pnlCenter_Field.add(txtMonthPD, BorderLayout.WEST);
								txtMonthPD.setEditable(false);
							}
							{
								txtDefaultDate = new JTextField();
								pnlCenter_Field.add(txtDefaultDate, BorderLayout.WEST);
								txtDefaultDate.setEditable(false);
							}
							{
								cmbCancelTypeModel_Pa = new DefaultComboBoxModel(new String[] {"","Company Initiated", "Buyer Initiated"});
								cmbCancelType_Pa = new JComboBox();
								pnlCenter_Field.add(cmbCancelType_Pa, BorderLayout.WEST);
								cmbCancelType_Pa.setModel(cmbCancelTypeModel_Pa);
								cmbCancelType_Pa.addActionListener(this);

							}
							{

								txtAmountDue = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
								pnlCenter_Field.add(txtAmountDue, BorderLayout.WEST);
								txtAmountDue.setFormatterFactory(_JXFormattedTextField.DECIMAL);
								txtAmountDue.setEditable(false);
							}
							{ 
								JPanel pnlLookUp_Reason = new JPanel(new BorderLayout(3,3));
								pnlCenter_Field.add(pnlLookUp_Reason,BorderLayout.CENTER);
								{
									lookupReason = new _JLookup("Reason ID ", "Reason Desc.", 0);
									pnlLookUp_Reason.add(lookupReason,BorderLayout.WEST);
									lookupReason.setLookupSQL(functions.getRemarks());
									lookupReason.setPreferredSize(new Dimension(100, 0));
									lookupReason.addLookupListener(this);

								}
								{
									txtReason = new JTextField();
									pnlLookUp_Reason.add(txtReason,BorderLayout.CENTER);
									txtReason.setEditable(false); 
								}
							}
						}
					}
					{
						pnlEast_C = new JXPanel(new BorderLayout(5,5));
						pnlCenter.add(pnlEast_C,BorderLayout.EAST);
						pnlEast_C.setPreferredSize(new Dimension(200, 0));
					}
					{
						JPanel pnlRemark = new JPanel(new BorderLayout(5, 5));
						pnlCenter.add(pnlRemark,BorderLayout.SOUTH);
						pnlRemark.setPreferredSize(new Dimension(this.getWidth(),90));
						pnlRemark.setBorder(lineBorder);
						{
							{
								JPanel pnlLabel = new JPanel(new GridLayout(1, 1, 3, 3));
								pnlRemark.add(pnlLabel,BorderLayout.WEST);
								
								{
									 lblRemarks = new JLabel(" Remarks");
									 pnlLabel.add(lblRemarks);
								}
							}
							{
								JPanel pnlCenter_Field = new JPanel(new GridLayout(1, 1, 3, 3));
								pnlRemark.add(pnlCenter_Field,BorderLayout.CENTER);
							}
						}
					}
				}//pnlCenter
				{

					pnlSouth = new JXPanel(new BorderLayout(4,4));
					pnlMain.add(pnlSouth, BorderLayout.SOUTH);
					pnlSouth.setPreferredSize(new Dimension(900, 55));
					{
						JPanel pnlNorth = new  JPanel(new BorderLayout(5, 5));
						pnlSouth.add(pnlNorth,BorderLayout.NORTH);
					}
					{
						pnlSouth_Center = new  JPanel(new BorderLayout(5, 5));
						pnlSouth.add(pnlSouth_Center,BorderLayout.CENTER);
						{
							JPanel pnlCenter_Approved = new JPanel(new BorderLayout(5, 5));
							pnlSouth_Center.add(pnlCenter_Approved,BorderLayout.NORTH);
							pnlCenter_Approved.setPreferredSize(new Dimension(0, 5));
							pnlCenter_Approved.setVisible(true);

						}
						{
							pnlCenter_Approved= new JPanel(new BorderLayout(5, 5));
							pnlSouth_Center.add(pnlCenter_Approved,BorderLayout.CENTER);
							pnlCenter_Approved.setVisible(true);
							{
								lblApproveBy_Active = new JLabel(" Approved By :");
								pnlCenter_Approved.add(lblApproveBy_Active,BorderLayout.WEST);
							}
							{ 
								JPanel pnlApproved = new JPanel(new BorderLayout(3, 3));
								pnlCenter_Approved.add(pnlApproved,BorderLayout.CENTER);
								pnlApproved.setVisible(true);
								{
									lookupApproveby = new _JLookup("Emp. ID", "Search Employee", 0);
									pnlApproved.add(lookupApproveby,BorderLayout.WEST);
									lookupApproveby.setLookupSQL(functions.getApprovedby());
									lookupApproveby.setPreferredSize(new Dimension(60, 50));
									lookupApproveby.addLookupListener(this); 

								}
								{
									txtApprovedBy = new JTextField();
									pnlApproved.add(txtApprovedBy,BorderLayout.CENTER);
									txtApprovedBy.setEditable(false);
								}
							}
						}
						{
							JPanel pnlCenter_Approved_S = new JPanel(new BorderLayout(5, 5));
							pnlSouth_Center.add(pnlCenter_Approved_S,BorderLayout.SOUTH);
							pnlCenter_Approved_S.setPreferredSize(new Dimension(0, 5)); 
						}	
					}
					{
						pnlSouth_East = new  JPanel(new GridLayout(1, 5, 3, 3));
						pnlSouth.add(pnlSouth_East,BorderLayout.EAST); 
						pnlSouth_East.setPreferredSize(new Dimension(400, 0));

						{
							btnNew = new _JButton("New");
							pnlSouth_East.add(btnNew);
							btnNew.setActionCommand("New");
							btnNew.addActionListener(this);
							grpButton.add(btnNew);
						}
						{
							btnEdit = new _JButton("Edit");
							pnlSouth_East.add(btnEdit);
							btnEdit.setActionCommand("Edit");
							btnEdit.addActionListener(this);
							grpButton.add(btnEdit);
						}
						{
							btnSave = new _JButton("Save");
							pnlSouth_East.add(btnSave);
							btnSave.addActionListener(this);
						}
						{
							btnPost = new _JButton("Post");
							pnlSouth_East.add(btnPost);
							btnPost.setActionCommand("Post");
							btnPost.addActionListener(this);
						}
						{
							btnClear = new _JButton("Clear");
							pnlSouth_East.add(btnClear);
							btnClear.setActionCommand("Clear_Active");
							btnClear.addActionListener(this);
							grpButton.add(btnClear);

						}
					}

					JPanel _pnlSouth = new  JPanel(new BorderLayout(5, 5));
					pnlSouth.add(_pnlSouth,BorderLayout.SOUTH);
				
				}
			}
		}
	}

	@Override
	public void lookupPerformed(LookupEvent event) {}
	@Override 
	public void actionPerformed(ActionEvent e) {}

}
