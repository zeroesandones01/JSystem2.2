package Utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.Base64.OutputStream;

import Database.pgSelect;
import Dialogs.dlg_ImageViewer;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncImageFileChooser;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JXTextField;
import interfaces._GUI;


public class LoanReleasedDetails extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = 5505784414639445865L;

	static String title = "Loan Released Details";

	static Dimension SIZE = new Dimension(1000, 600);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlWest;
	private JPanel pnlClientDetails;

	private JPanel pnlCDLabel;
	private JLabel lblClient;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSeqNo;

	private JPanel pnlCDComponents;
	private _JLookup lookupClient;
	private _JXTextField txtClient;
	private _JXTextField txtProjID;
	private _JXTextField txtProjName;
	private _JXTextField txtUnitID;
	private _JXTextField txtUnitDesc;
	private _JXTextField txtSeqNo;
	private _JXTextField txtModelDesc;

	private JPanel pnlDetails;
	private JPanel pnlDetailsLabel;
	private JLabel lblLoanableAmt;
	private JLabel lblLess;
	private JLabel lblSRI_DocsStamps;
	private JLabel lblFire;
	private JLabel lblProcessingFee;
	private JLabel lblInterimMRI;
	private JLabel lblFirstMA;
	private JLabel lblRefilingFee;
	private JLabel lblUpgradedContribution;
	private JLabel lblAppraisalFee;
	private JLabel lblRetentionFee7pt5;
	private JLabel lblRetentionUndertaking;
	private JLabel lblEPEB_3pct;
	private JLabel lblNetProceeds;
	private JLabel lblEPEB;
	private JLabel lblETD;
	private JLabel lblCOC;
	private JLabel lblMeralco;
	private JLabel lblComfac_3pct;
	private JLabel lblComfac_5pct;
	private JLabel lblTotalNetProceeds;

	private JPanel pnlDetailsComponent;
	private _JXFormattedTextField txtLoanableAmt;
	private _JXFormattedTextField txtSRI_DocsStamps;
	private _JXFormattedTextField txtFire;
	private _JXFormattedTextField txtProcessingFee;
	private _JXFormattedTextField txtInterimMRI;
	private _JXFormattedTextField txtFirstMA;
	private _JXFormattedTextField txtRefilingFee;
	private _JXFormattedTextField txtUpgradedContribution;
	private _JXFormattedTextField txtAppraisalFee;
	private _JXFormattedTextField txtRetentionFee7pt5;
	private _JXFormattedTextField txtRetentionFeeUndertaking;
	private _JXFormattedTextField txtEPEB_3pct;
	private _JXFormattedTextField txtSubTotalNetProceeds;
	private _JXFormattedTextField txtNetProceeds;
	private _JXFormattedTextField txtEPEB;
	private _JXFormattedTextField txtETD;
	private _JXFormattedTextField txtCOC;
	private _JXFormattedTextField txtMeralco;
	private _JXFormattedTextField txtComfac_3pct;
	private _JXFormattedTextField txtComfac_5pct;
	private _JXFormattedTextField txtTotalNetProceeds;

	private JScrollPane scrollDetails;
	private JTextArea txtAreaDetails;

	private JPanel pnlCenter;
	protected FncImageFileChooser lblDocument;
	private JButton btnEditUpload;
	private JButton btnPreviewPDF;

	private String temp_folder;
	private String file_name;
	private File uploaded_file = null;
	private FileOutputStream fos;

	public LoanReleasedDetails() {
		super(title, true, true, true, true);
		initGUI();
	}

	public LoanReleasedDetails(String title) {
		super(title, true, true, true, true);
		initGUI();
	}

	public LoanReleasedDetails(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, true, true, true, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);

		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		{
			pnlWest = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlWest, BorderLayout.WEST);
			pnlWest.setPreferredSize(new Dimension(500, 0));
			{
				pnlClientDetails = new JPanel(new BorderLayout(5, 5));
				pnlWest.add(pnlClientDetails, BorderLayout.NORTH);
				pnlClientDetails.setBorder(JTBorderFactory.createTitleBorder("Client Details"));
				pnlClientDetails.setPreferredSize(new Dimension(0, 120));
				{
					pnlCDLabel = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlClientDetails.add(pnlCDLabel, BorderLayout.WEST);
					{
						lblClient = new JLabel("Client");
						pnlCDLabel.add(lblClient);
					}
					{
						lblProject = new JLabel("Project");
						pnlCDLabel.add(lblProject);
					}
					{
						lblUnit = new JLabel("Ph/Bk/Lt");
						pnlCDLabel.add(lblUnit);
					}
					{
						lblSeqNo = new JLabel("Seq/Model");
						pnlCDLabel.add(lblSeqNo);
					}
				}
				{
					pnlCDComponents = new JPanel(new GridLayout(4, 1, 3, 3));
					pnlClientDetails.add(pnlCDComponents, BorderLayout.CENTER);
					{
						JPanel pnlClient = new JPanel(new BorderLayout(5, 5));
						pnlCDComponents.add(pnlClient);
						{
							lookupClient = new _JLookup(null, "Client", 0);
							pnlClient.add(lookupClient, BorderLayout.WEST);
							lookupClient.setPreferredSize(new Dimension(100, 0));
							lookupClient.setLookupSQL(sqlClient());
							lookupClient.setFilterName(true);
							lookupClient.addLookupListener(new LookupListener() {

								public void lookupPerformed(LookupEvent event) {
									Object [] data = ((_JLookup)event.getSource()).getDataSet();
									if(data != null){
										if(uploaded_file != null){
											if(uploaded_file.exists()){
												uploaded_file.delete();
												uploaded_file = null;
											}
										}

										System.out.println("Uploaded file is closed so display");

										String entity_id = (String) data[0];
										String entity_name = (String) data[1];
										String proj_id = (String) data[2];
										String proj_name = (String) data[3];
										String pbl_id = (String) data[4];
										String unit_desc = (String) data[5];
										String seq_no = String.valueOf(data[6]);
										String model_desc = (String) data[7];

										txtClient.setText(entity_name);
										txtProjID.setText(proj_id);
										txtProjName.setText(proj_name);
										txtUnitID.setText(pbl_id);
										txtUnitDesc.setText(unit_desc);
										txtSeqNo.setText(seq_no);
										txtModelDesc.setText(model_desc);

										displayDetails(entity_id, proj_id, pbl_id, seq_no);
										btnEditUpload.setEnabled(true);
										//btnPreviewPDF.setEnabled(true);
										lblDocument.setClickable(false);
										btnEditUpload.setText("Edit");
										btnEditUpload.setActionCommand("Edit");

										try {
											displayScannedDocument(lblDocument, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText());
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								}
							});
						}
						{
							txtClient = new _JXTextField();
							pnlClient.add(txtClient, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
						pnlCDComponents.add(pnlProject);
						{
							txtProjID = new _JXTextField();
							pnlProject.add(txtProjID, BorderLayout.WEST);
							txtProjID.setPreferredSize(new Dimension(50, 0));
							txtProjID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtProjName = new _JXTextField();
							pnlProject.add(txtProjName, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlUnit = new JPanel(new BorderLayout(5, 5));
						pnlCDComponents.add(pnlUnit);
						{
							txtUnitID = new _JXTextField();
							pnlUnit.add(txtUnitID, BorderLayout.WEST);
							txtUnitID.setPreferredSize(new Dimension(100, 0));
							txtUnitID.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtUnitDesc = new _JXTextField();
							pnlUnit.add(txtUnitDesc, BorderLayout.CENTER);
						}
					}
					{
						JPanel pnlSeqNo = new JPanel(new BorderLayout(5, 5));
						pnlCDComponents.add(pnlSeqNo);
						{
							txtSeqNo = new _JXTextField();
							pnlSeqNo.add(txtSeqNo, BorderLayout.WEST);
							txtSeqNo.setPreferredSize(new Dimension(50, 0));
							txtSeqNo.setHorizontalAlignment(JXTextField.CENTER);
						}
						{
							txtModelDesc = new _JXTextField();
							pnlSeqNo.add(txtModelDesc, BorderLayout.CENTER);
						}
					}
				}
			}
			{
				pnlDetails = new JPanel(new BorderLayout(5, 5));
				pnlWest.add(pnlDetails, BorderLayout.CENTER);
				pnlDetails.setBorder(JTBorderFactory.createTitleBorder("Details"));
				{
					pnlDetailsLabel = new JPanel(new GridLayout(23, 1));
					pnlDetails.add(pnlDetailsLabel, BorderLayout.WEST);
					{
						lblLoanableAmt = new JLabel("Loanable Amount");
						pnlDetailsLabel.add(lblLoanableAmt);
					}
					{
						lblLess = new JLabel("Less");
						pnlDetailsLabel.add(lblLess);
					}
					{
						lblSRI_DocsStamps = new JLabel("SRI & Doc. Stamps");
						pnlDetailsLabel.add(lblSRI_DocsStamps);
					}
					{
						lblFire = new JLabel("Fire");
						pnlDetailsLabel.add(lblFire);
					}
					{
						lblProcessingFee = new JLabel("Processing fee");
						pnlDetailsLabel.add(lblProcessingFee);
					}
					{
						lblInterimMRI = new JLabel("Interim MRI");
						pnlDetailsLabel.add(lblInterimMRI);
					}
					{
						lblFirstMA = new JLabel("First MA");
						pnlDetailsLabel.add(lblFirstMA);
					}
					{
						lblRefilingFee = new JLabel("Refiling Fee");
						pnlDetailsLabel.add(lblRefilingFee);
					}
					{
						lblUpgradedContribution = new JLabel("Upgraded Contribution");
						pnlDetailsLabel.add(lblUpgradedContribution);
					}
					{
						lblAppraisalFee = new JLabel("Appraisal Fee");
						pnlDetailsLabel.add(lblAppraisalFee);
					}
					{
						lblRetentionFee7pt5 = new JLabel("7.50% Retention Fee");
						pnlDetailsLabel.add(lblRetentionFee7pt5);
					}
					{
						lblRetentionUndertaking = new JLabel("Retention for Undertaking");
						pnlDetailsLabel.add(lblRetentionUndertaking);
					}
					{
						lblEPEB_3pct = new JLabel("3.00 % Electronic Primary Entry Book");
						pnlDetailsLabel.add(lblEPEB_3pct);
					}
					{
						pnlDetailsLabel.add(Box.createHorizontalBox());
					}
					{
						lblNetProceeds = new JLabel("Net Proceeds");
						pnlDetailsLabel.add(lblNetProceeds);
					}
					{
						lblEPEB = new JLabel("Electronic Primary Entry Book");
						pnlDetailsLabel.add(lblEPEB);
					}
					{
						lblETD = new JLabel("ETD");
						pnlDetailsLabel.add(lblETD);
					}
					{
						lblCOC = new JLabel("COC");
						pnlDetailsLabel.add(lblCOC);
					}
					{
						lblMeralco = new JLabel("MERALCO");
						pnlDetailsLabel.add(lblMeralco);
					}
					{
						lblComfac_3pct = new JLabel("COMFAC 3%");
						pnlDetailsLabel.add(lblComfac_3pct);
					}
					{
						lblComfac_5pct = new JLabel("COMFAC 5%");
						pnlDetailsLabel.add(lblComfac_5pct);
					}
					{
						pnlDetailsLabel.add(Box.createHorizontalBox());
					}
					{
						lblTotalNetProceeds = new JLabel("TOTAL NET PROCEEDS");
						pnlDetailsLabel.add(lblTotalNetProceeds);
					}
				}
				{
					pnlDetailsComponent = new JPanel(new GridLayout(0, 2, 3, 3));
					pnlDetails.add(pnlDetailsComponent, BorderLayout.CENTER);
					{
						JPanel pnlComponentWest = new JPanel(new GridLayout(23, 1, 3, 3));
						pnlDetailsComponent.add(pnlComponentWest);
						{
							pnlComponentWest.add(Box.createHorizontalBox());
							pnlComponentWest.add(Box.createHorizontalBox());
						}
						{
							txtSRI_DocsStamps = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtSRI_DocsStamps);
							txtSRI_DocsStamps.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtSRI_DocsStamps.setValue(new BigDecimal("0.00"));
							txtSRI_DocsStamps.setEditable(false);
						}
						{
							txtFire = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtFire);
							txtFire.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtFire.setValue(new BigDecimal("0.00"));
							txtFire.setEditable(false);
						}
						{
							txtProcessingFee = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtProcessingFee);
							txtProcessingFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtProcessingFee.setValue(new BigDecimal("0.00"));
							txtProcessingFee.setEditable(false);
						}
						{
							txtInterimMRI = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtInterimMRI);
							txtInterimMRI.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtInterimMRI.setValue(new BigDecimal("0.00"));
							txtInterimMRI.setEditable(false);
						}
						{
							txtFirstMA = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtFirstMA);
							txtFirstMA.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtFirstMA.setValue(new BigDecimal("0.00"));
							txtFirstMA.setEditable(false);
						}
						{
							txtRefilingFee = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtRefilingFee);
							txtRefilingFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtRefilingFee.setValue(new BigDecimal("0.00"));
							txtRefilingFee.setEditable(false);
						}
						{
							txtUpgradedContribution = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtUpgradedContribution);
							txtUpgradedContribution.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtUpgradedContribution.setValue(new BigDecimal("0.00"));
							txtUpgradedContribution.setEditable(false);
						}
						{
							txtAppraisalFee = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtAppraisalFee);
							txtAppraisalFee.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtAppraisalFee.setValue(new BigDecimal("0.00"));
							txtAppraisalFee.setEditable(false);
						}
						{
							txtRetentionFee7pt5 = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtRetentionFee7pt5);
							txtRetentionFee7pt5.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtRetentionFee7pt5.setValue(new BigDecimal("0.00"));
							txtRetentionFee7pt5.setEditable(false);
						}
						{
							txtRetentionFeeUndertaking = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtRetentionFeeUndertaking);
							txtRetentionFeeUndertaking.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtRetentionFeeUndertaking.setValue(new BigDecimal("0.00"));
							txtRetentionFeeUndertaking.setEditable(false);
						}
						{
							txtEPEB_3pct = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentWest.add(txtEPEB_3pct);
							txtEPEB_3pct.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtEPEB_3pct.setValue(new BigDecimal("0.00"));
							txtEPEB_3pct.setEditable(false);
						}
						{
							pnlComponentWest.add(new JSeparator());
						}
						{
							pnlComponentWest.add(Box.createHorizontalBox());
							pnlComponentWest.add(Box.createHorizontalBox());
							pnlComponentWest.add(Box.createHorizontalBox());
							pnlComponentWest.add(Box.createHorizontalBox());
						}
					}
					{
						JPanel pnlComponentEast = new JPanel(new GridLayout(23, 1, 3, 3));
						pnlDetailsComponent.add(pnlComponentEast);
						{
							txtLoanableAmt = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentEast.add(txtLoanableAmt);
							txtLoanableAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtLoanableAmt.setValue(new BigDecimal("0.00"));
							txtLoanableAmt.setEditable(false);
						}
						{
							pnlComponentEast.add(Box.createHorizontalBox());
							pnlComponentEast.add(Box.createHorizontalBox());
							pnlComponentEast.add(Box.createHorizontalBox());
							pnlComponentEast.add(Box.createHorizontalBox());
							pnlComponentEast.add(Box.createHorizontalBox());
							pnlComponentEast.add(Box.createHorizontalBox());
							pnlComponentEast.add(Box.createHorizontalBox());
							pnlComponentEast.add(Box.createHorizontalBox());
							pnlComponentEast.add(Box.createHorizontalBox());
							pnlComponentEast.add(Box.createHorizontalBox());
							pnlComponentEast.add(Box.createHorizontalBox());
						}
						{
							txtSubTotalNetProceeds = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentEast.add(txtSubTotalNetProceeds);
							txtSubTotalNetProceeds.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtSubTotalNetProceeds.setValue(new BigDecimal("0.00"));
							txtSubTotalNetProceeds.setEditable(false);
						}
						{
							pnlComponentEast.add(new JSeparator());
						}
						{
							txtNetProceeds = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentEast.add(txtNetProceeds);
							txtNetProceeds.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtNetProceeds.setValue(new BigDecimal("0.00"));
							txtNetProceeds.setEditable(false);
						}
						{
							txtEPEB = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentEast.add(txtEPEB);
							txtEPEB.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtEPEB.setValue(new BigDecimal("0.00"));
							txtEPEB.setEditable(false);
						}
						{
							txtETD = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentEast.add(txtETD);
							txtETD.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtETD.setValue(new BigDecimal("0.00"));
							txtETD.setEditable(false);
						}
						{
							txtCOC = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentEast.add(txtCOC);
							txtCOC.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtCOC.setValue(new BigDecimal("0.00"));
							txtCOC.setEditable(false);
						}
						{
							txtMeralco = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentEast.add(txtMeralco);
							txtMeralco.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtMeralco.setValue(new BigDecimal("0.00"));
							txtMeralco.setEditable(false);
						}
						{
							txtComfac_3pct = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentEast.add(txtComfac_3pct);
							txtComfac_3pct.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtComfac_3pct.setValue(new BigDecimal("0.00"));
							txtComfac_3pct.setEditable(false);
						}
						{
							txtComfac_5pct = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentEast.add(txtComfac_5pct);
							txtComfac_5pct.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtComfac_5pct.setValue(new BigDecimal("0.00"));
							txtComfac_5pct.setEditable(false);
						}
						{
							pnlComponentEast.add(new JSeparator());
						}
						{
							txtTotalNetProceeds = new _JXFormattedTextField(JXFormattedTextField.RIGHT);
							pnlComponentEast.add(txtTotalNetProceeds);
							txtTotalNetProceeds.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtTotalNetProceeds.setValue(new BigDecimal("0.00"));
							txtTotalNetProceeds.setEditable(false);
						}
					}
				}
			}
		}
		{
			pnlCenter = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlCenter, BorderLayout.CENTER);
			//pnlCenter.setBorder(JTBorderFactory.createTitleBorder("Scanned"));
			{
				JPanel pnlCenterNorth = new JPanel(new BorderLayout(3, 3));
				pnlCenter.add(pnlCenterNorth, BorderLayout.CENTER);
				pnlCenterNorth.setBorder(JTBorderFactory.createTitleBorder("Scanned"));
				{
					lblDocument = new FncImageFileChooser(500, 500);
					pnlCenterNorth.add(lblDocument);
					lblDocument.setClickable(false);
					lblDocument.setDefaultFileImage();
					lblDocument.addMouseListener(new MouseListener() {

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseClicked(MouseEvent e) {
							if(lblDocument.isClickable() == false && lookupClient.getValue() != null){
								if(e.getClickCount() == 2){
									try{
									byte bt[]=null;	
									bt = displayScannedDocument(lblDocument, lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText());
									
									InputStream is = new BufferedInputStream(new ByteArrayInputStream(bt));
									String mimeType;
									
									mimeType = URLConnection.guessContentTypeFromStream(is);
									
									if(mimeType == null){
										openPDF(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), Integer.valueOf(txtSeqNo.getText()));
									}else{
										dlg_ImageViewer img_dlg = new dlg_ImageViewer(FncGlobal.homeMDI, "File Image", bt, new Dimension(1000, 700));
										img_dlg.setLocationRelativeTo(null);
										img_dlg.setVisible(true);
										img_dlg.setSize(700, 700);
										img_dlg.setPreferredSize(new Dimension(700, 700));
										img_dlg.setMinimumSize(new Dimension(700, 700));
									}

									} catch (IOException a) {
										a.printStackTrace();
									} catch (OutOfMemoryError er){
										JOptionPane.showMessageDialog(getContentPane(), "Out of memory.", "Loading Image.", JOptionPane.WARNING_MESSAGE);

									}

									
								}
							}

							if(lblDocument.isClickable()){
								if(e.getClickCount() >= 2 && lblDocument.getIsOk()){

								}
							}
						}
					});
				}
			}
			{
				JPanel pnlCenterSouth = new JPanel(new GridLayout(1, 4));
				pnlCenter.add(pnlCenterSouth, BorderLayout.SOUTH);
				pnlCenterSouth.setPreferredSize(new Dimension(0, 30));
				{
					pnlCenterSouth.add(Box.createHorizontalBox());
					pnlCenterSouth.add(Box.createHorizontalBox());
					pnlCenterSouth.add(Box.createHorizontalBox());
				}
				/*{
					btnPreviewPDF = new JButton("Preview PDF");
					pnlCenterSouth.add(btnPreviewPDF);
					btnPreviewPDF.setActionCommand("Preview PDF");
					btnPreviewPDF.addActionListener(this);
					btnPreviewPDF.setEnabled(false);
				}*/
				{
					btnEditUpload = new JButton("Edit");
					pnlCenterSouth.add(btnEditUpload);
					btnEditUpload.setActionCommand("Edit");
					btnEditUpload.addActionListener(this);
					btnEditUpload.setPreferredSize(new Dimension(150, 0));
					btnEditUpload.setEnabled(false);
				}
			}
		}

	}//XXX END OF INIT GUI

	private String sqlClient(){
		return "select a.entity_id as \"Entity ID\",b.entity_name as \"Client\", \n" + 
				"a.projcode as \"Proj. ID\", d.proj_name \"Project\", \n" + 
				"a.pbl_id as \"PBL\", (CASE WHEN e.oth_pbl_id IS null then c.description else FORMAT('%s/%s', c.description, f.lot) end) as \"Unit\", \n" + 
				"a.seq_no as \"Seq. No\", g.model_desc as \"Model\"\n" + 
				"from rf_sold_unit a\n" + 
				"left join rf_entity b on b.entity_id = a.entity_id \n" + 
				"LEFT JOIN mf_unit_info c on c.proj_id = a.projcode and c.pbl_id = a.pbl_id and coalesce(a.server_id, '') = coalesce(c.server_id, '') and coalesce(a.proj_server, '') = coalesce(c.proj_server, '')\n" + 
				"LEFT JOIN mf_project d on d.proj_id = a.projcode\n" + 
				"LEFT JOIN hs_sold_other_lots e on e.entity_id = a.entity_id and e.proj_id = a.projcode and e.pbl_id = a.pbl_id and e.seq_no = a.seq_no and coalesce(a.server_id, '') = coalesce(e.server_id, '') and coalesce(a.proj_server, '') = coalesce(e.proj_server, '')\n" + 
				"LEFT JOIN mf_unit_info f on f.proj_id = a.projcode and f.pbl_id = e.oth_pbl_id and coalesce(a.server_id, '') = coalesce(f.server_id, '') and coalesce(a.proj_server, '') = coalesce(f.proj_server, '')\n" + 
				"LEFT JOIN mf_product_model g on g.model_id = a.model_id and coalesce(a.server_id, '') = coalesce(g.server_id, '') and coalesce(a.proj_server, '') = coalesce(g.proj_server, '')\n" + 
				"where get_group_id(a.buyertype) = '04'\n" + 
				"and a.currentstatus != '02'\n" + 
				"and a.status_id != 'I' \n" +
				"AND EXISTS (SELECT * \n"+
				"			 FROM rf_buyer_status \n"+
				"			 WHERE entity_id = a.entity_id \n"+
				" 			 AND proj_id = a.projcode \n"+
				"			 AND pbl_id = a.pbl_id \n"+
				"			 AND seq_no = a.seq_no \n"+
				"			 AND byrstatus_id = '32' \n"+
				"			 AND status_id = 'A') \n"+
				"			 ORDER BY b.entity_name";
		/*"AND EXISTS (SELECT * \n"+
				"			 FROM rf_pagibig_lnrel \n"+
				"            WHERE entity_id = a.entity_id \n"+
				"			 AND proj_id = a.projcode \n"+
				"			 AND pbl_id = a.pbl_id \n"+
				"			 AND seq_no = a.seq_no \n"+
				"			 AND status_id = 'A') \n"+ 
				"ORDER BY b.entity_name;";*/
	};

	private void displayDetails(String entity_id, String proj_id, String pbl_id, String seq_no){
		pgSelect db = new pgSelect();
		String SQL = "SELECT * FROM view_loan_released_details('"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+seq_no+");";
		db.select(SQL);

		if(db.isNotNull()){
			txtLoanableAmt.setValue(db.getResult()[0][0]);
			txtSRI_DocsStamps.setValue(db.getResult()[0][1]);
			txtFire.setValue(db.getResult()[0][2]);
			txtProcessingFee.setValue(db.getResult()[0][3]);
			txtInterimMRI.setValue(db.getResult()[0][4]);
			txtFirstMA.setValue(db.getResult()[0][5]);
			txtRefilingFee.setValue(db.getResult()[0][6]);
			txtUpgradedContribution.setValue(db.getResult()[0][7]);
			txtAppraisalFee.setValue(db.getResult()[0][8]);
			txtRetentionFee7pt5.setValue(db.getResult()[0][9]);
			txtRetentionFeeUndertaking.setValue(db.getResult()[0][10]);
			txtEPEB_3pct.setValue(db.getResult()[0][11]);
			txtSubTotalNetProceeds.setValue(db.getResult()[0][12]);
			txtNetProceeds.setValue(db.getResult()[0][13]);
			txtEPEB.setValue(db.getResult()[0][14]);
			txtETD.setValue(db.getResult()[0][15]);
			txtCOC.setValue(db.getResult()[0][16]);
			txtMeralco.setValue(db.getResult()[0][17]);
			txtComfac_3pct.setValue(db.getResult()[0][18]);
			txtComfac_5pct.setValue(db.getResult()[0][19]);
			txtTotalNetProceeds.setValue(db.getResult()[0][20]);
		}else{
			txtLoanableAmt.setValue(new BigDecimal("0.00"));
			txtSRI_DocsStamps.setValue(new BigDecimal("0.00"));
			txtFire.setValue(new BigDecimal("0.00"));
			txtProcessingFee.setValue(new BigDecimal("0.00"));
			txtInterimMRI.setValue(new BigDecimal("0.00"));
			txtFirstMA.setValue(new BigDecimal("0.00"));
			txtRefilingFee.setValue(new BigDecimal("0.00"));
			txtUpgradedContribution.setValue(new BigDecimal("0.00"));
			txtAppraisalFee.setValue(new BigDecimal("0.00"));
			txtRetentionFee7pt5.setValue(new BigDecimal("0.00"));
			txtRetentionFeeUndertaking.setValue(new BigDecimal("0.00"));
			txtEPEB_3pct.setValue(new BigDecimal("0.00"));
			txtSubTotalNetProceeds.setValue(new BigDecimal("0.00"));
			txtNetProceeds.setValue(new BigDecimal("0.00"));
			txtEPEB.setValue(new BigDecimal("0.00"));
			txtETD.setValue(new BigDecimal("0.00"));
			txtCOC.setValue(new BigDecimal("0.00"));
			txtMeralco.setValue(new BigDecimal("0.00"));
			txtComfac_3pct.setValue(new BigDecimal("0.00"));
			txtComfac_5pct.setValue(new BigDecimal("0.00"));
			txtTotalNetProceeds.setValue(new BigDecimal("0.00"));
		}
	}
	
	public String getMimeType(byte data[]) throws IOException {		
		InputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
		String mimeType;
		
		mimeType = URLConnection.guessContentTypeFromStream(is);
		return mimeType;
	}

	public static byte[] displayScannedDocument(FncImageFileChooser label, String entity_id, String proj_id, String pbl_id, String seq_no) throws IOException{
		byte bt[]=null;
		
		String strSQL = "SELECT scanned_document FROM rf_pagibig_lnrel WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = "+seq_no+" AND status_id = 'A'";
		label.setDefaultFileImage();
		System.out.printf("Value of SQL: %s%n", strSQL);
		
		try{

			Functions.FncSelectRecords.selectV2(strSQL, false);
			Functions.FncSelectRecords.rs.last();
			Functions.FncSelectRecords.rs.beforeFirst();
			//int x = 0;
			
			while (Functions.FncSelectRecords.rs.next()) {
				
				//System.out.println("While Loop");
				bt = Functions.FncSelectRecords.rs.getBytes(1);
				
				InputStream is = new BufferedInputStream(new ByteArrayInputStream(bt));
				String mimeType;
				
				mimeType = URLConnection.guessContentTypeFromStream(is);
				System.out.printf("Display Value of Byte: %s%n", bt);
				System.out.printf("Display value of Mime Type: %s%n", mimeType);
				
				if(bt!=null) {
					if(mimeType == null){
						label.setDefaultPreviewPDF();
					}else{
						label.setByteImageIcon(bt);
					}
				}else{
					System.out.println("Dumaan dito sa Default Image");;
					label.setDefaultFileImage();
					
				}
				//x++;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Functions.FncSelectRecords.disconnect();

		return bt;
	}
	
	
	/*private void saveLoanDetails(){
		String entity_id = lookupClient.getValue();
		String proj_id = txtProjID.getText();
		String pbl_id = txtUnitID.getText();
		String seq_no = txtSeqNo.getText();
		//String details = txtAreaDetails.getText().trim().replace("'", "''");

		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_save_loan_released_details('"+entity_id+"','"+proj_id+"', '"+pbl_id+"', \n" + 
					 ""+seq_no+", '"+details+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
	}*/

	private void saveDocument(byte[] document ,String entity_id, String proj_id, String pbl_id, String seq_no){
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			connection.setAutoCommit(true);

			PreparedStatement ps = connection.prepareStatement("UPDATE rf_pagibig_lnrel SET scanned_document = ? WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = '"+pbl_id+"' AND seq_no = "+seq_no+"");
			ps.setBytes(1, document);
			ps.executeUpdate();
			ps.close();
			System.out.println("Dumaan dito sa saving ng Document");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	/*private void createNewPDF_Viewer() throws InvalidPasswordException, IOException{


		//FileFilter fJavaFilter = new FileFilter();
	    JFileChooser fc = new JFileChooser();
	    fc.setDialogTitle("Open your file");
	    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    fc.setCurrentDirectory(new File("."));
	    //fc.setFileFilter(fJavaFilter);

	    int result = fc.showOpenDialog(this);

	    if (result == JFileChooser.CANCEL_OPTION) {
	        //cancel action
	    } else if (result == JFileChooser.APPROVE_OPTION) {
	        //open file using 
	        File selectedFile = fc.getSelectedFile();
	        String path = selectedFile.getAbsolutePath();

	        File myFile = new File(path);
	        Desktop.getDesktop().open(myFile);
	    }
	}*/

	private void openPDF(String entity_id, String proj_id, String unit_id, Integer seq_no) throws IOException{
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(FncGlobal.connectionURL, FncGlobal.connectionUsername, FncGlobal.connectionPassword);
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT scanned_document FROM rf_pagibig_lnrel WHERE entity_id = '"+entity_id+"' AND proj_id = '"+proj_id+"' AND pbl_id = getinteger('"+unit_id+"')::VARCHAR AND seq_no = "+seq_no+" AND status_id = 'A'");

			//FileOutputStream fos = new FileOutputStream(System.getProperty("java.io.tmpdir").toString());
			//temp_folder = System.getProperty("java.io.tmpdir");
			temp_folder = System.getProperty("user.home");
			file_name = String.format("%s - %s.pdf", "Loan Details", txtClient.getText());
			String output_stream = String.format("%s/%s", temp_folder, file_name);
			fos = new FileOutputStream(output_stream);
			rs.next();
			byte[] fileBytes = rs.getBytes(1);
			fos.write(fileBytes);
			rs.close();
			fos.close();
			
			uploaded_file = new File(output_stream);
			
			//uploaded_file.setReadOnly();
			//uploaded_file.
			
			
			/*try {
			FileOutputStream file2 = new FileOutputStream(uploaded_file);
			
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, file2);
			 
			
				writer.setEncryption("".getBytes(), "".getBytes(),
				        PdfWriter.ALLOW_PRINTING , //Only printing allowed; Try to copy text !!
				        PdfWriter.ENCRYPTION_AES_128);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	        
	        Desktop.getDesktop().open(uploaded_file);
	        
	        //Start of Test Code
	        
	        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void actionPerformed(ActionEvent act) {
		String actionCommand = act.getActionCommand();

		if(actionCommand.equals("Edit")){
			btnEditUpload.setText("Upload");
			btnEditUpload.setActionCommand("Upload");
			lblDocument.setClickable(true);
		}

		if(actionCommand.equals("Upload")){
			//saveLoanDetails();
			saveDocument(lblDocument.getImageByte(), lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), txtSeqNo.getText());
			btnEditUpload.setText("Edit");
			btnEditUpload.setActionCommand("Edit");
			lblDocument.setClickable(false);
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Data successfully updated", "Save", JOptionPane.INFORMATION_MESSAGE);
		}
		
		/*if(actionCommand.equals("Preview PDF")){
			try {
				//createNewPDF_Viewer();
				openPDF(lookupClient.getValue(), txtProjID.getText(), txtUnitID.getText(), Integer.valueOf(txtSeqNo.getText()));
			} catch (InvalidPasswordException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}*/
		
	}
}
