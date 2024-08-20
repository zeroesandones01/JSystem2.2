package Reports.LoansAndReceivable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._RealTimeDebit;
import Buyers.LoansManagement._PagibigStatusMonitoring;
import Buyers.LoansManagement.hdmfMon_qualifyAccounts;
import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelSpecialNotices;

public class PagibigNotices extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = -1834829101657201605L;
	static String title = "PAGIBIG Notices";
	Dimension frameSize = new Dimension(750, 600);

	static Border lineRed = BorderFactory.createLineBorder(Color.RED);

	private JPanel pnlNorth;
	private JPanel pnlCenter;
	private JPanel pnlSouth;

	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblNotice;
	private JLabel lblORDateBelow;

	private _JLookup txtCoID;
	private _JLookup txtProID;
	private _JLookup txtPhaseID;
	private _JLookup txtNoticeID;
	private _JLookup txtBatchID;
	private _JLookup txtClientID;

	private JCheckBox chkBatch;

	public JTextField txtCompany;
	private JTextField txtProject;
	private JTextField txtPhase;
	private JTextField txtClient;
	private JTextField txtNotice;
	private JRadioButton rbtnORDateBelow;

	private _JTableMain tblPagibigNotices;
	private modelSpecialNotices modelPagibigNotices;
	private JScrollPane scrollPagibigNotices;
	private JList rowHeaderPagibigNotices;

	private JButton btnTag;
	private JButton btnPreview;
	private JButton btnCancel;

	private String strUnit;

	private _PagibigNotices hdmfNot;

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public PagibigNotices() {
		super(title, true, true, true, true);
		initGUI();
	}

	public PagibigNotices(String title) {
		super(title);
		initGUI();
	}

	public PagibigNotices(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);

		hdmfNot = new _PagibigNotices();
		{
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
						gbc.weighty = 0.25;
						
						gbc.gridx = 0;
						gbc.gridy = 0;

						

						pnlNorth = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlNorth,gbc);
						pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Notice Details"));
						{
							GridBagConstraints bagOne = new GridBagConstraints();
							bagOne.fill = GridBagConstraints.BOTH;
							bagOne.insets = new Insets(5,5,5,5);
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;

								bagOne.gridx = 0;
								bagOne.gridy = 0;

								lblCompany = new JLabel("Company");
								pnlNorth.add(lblCompany,bagOne);
								lblCompany.setFont(FncGlobal.sizeLabel);

							}
							{
								bagOne.weightx = 0.5;
								bagOne.weighty = 1;

								bagOne.gridx = 1;
								bagOne.gridy = 0;

								txtCoID = new _JLookup("");
								pnlNorth.add(txtCoID,bagOne);
								txtCoID.setHorizontalAlignment(JTextField.CENTER);
								txtCoID.setLookupSQL(_PagibigStatusMonitoring.sqlCompany());
								txtCoID.setReturnColumn(0);
								txtCoID.setValue("02");
								txtCoID.setFont(FncGlobal.sizeTextValue);
								txtCoID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup) e.getSource()).getDataSet();
										if (data != null) {
											txtCompany.setText(data[1].toString());
											txtProID.setLookupSQL(_PagibigStatusMonitoring.sqlProject(txtCoID.getValue()));

											//btnPreview.setEnabled(true);
										} else {
											//btnPreview.setEnabled(false);									
										}
									}
								});

							}
							{
								bagOne.weightx = 1.25;
								bagOne.weighty = 1;

								bagOne.gridx = 2;
								bagOne.gridy = 0;

								txtCompany = new JTextField("");
								pnlNorth.add(txtCompany,bagOne);
								txtCompany.setHorizontalAlignment(JTextField.CENTER);
								txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
								txtCompany.setFont(FncGlobal.sizeTextValue);

							}
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;

								bagOne.gridx = 0;
								bagOne.gridy = 1;

								lblProject = new JLabel("Project");
								pnlNorth.add(lblProject,bagOne);
								lblProject.setFont(FncGlobal.sizeLabel);

							}
							{
								bagOne.weightx = 0.5;
								bagOne.weighty = 1;

								bagOne.gridx = 1;
								bagOne.gridy = 1;

								txtProID = new _JLookup("");
								pnlNorth.add(txtProID,bagOne);
								txtProID.setHorizontalAlignment(JTextField.CENTER);
								txtProID.setLookupSQL(_PagibigStatusMonitoring.sqlProject(""));
								txtProID.setReturnColumn(0);
								txtProID.setValue("015");
								txtProID.setFont(FncGlobal.sizeTextValue);
								txtProID.addFocusListener(new FocusListener() {
									
									@Override
									public void focusLost(FocusEvent e) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void focusGained(FocusEvent e) {
										txtProID.setLookupSQL(_PagibigStatusMonitoring.sqlProject(txtCoID.getValue()));		
									}
								});
								txtProID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup) e.getSource()).getDataSet();
										if (data != null) {
											String proj_id = (String) data[0];
											txtProject.setText(data[1].toString());

											txtPhaseID.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(proj_id));
										}
									}
								});

							}
							{
								bagOne.weightx = 1.25;
								bagOne.weighty = 1;

								bagOne.gridx = 2;
								bagOne.gridy = 1;

								txtProject = new JTextField("");
								pnlNorth.add(txtProject,bagOne);
								txtProject.setHorizontalAlignment(JTextField.CENTER);
								txtProject.setText("TERRAVERDE RESIDENCES");
								txtProject.setFont(FncGlobal.sizeTextValue);

							}
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;

								bagOne.gridx = 0;
								bagOne.gridy = 2;

								lblPhase = new JLabel("Phase");
								pnlNorth.add(lblPhase,bagOne);
								lblPhase.setFont(FncGlobal.sizeLabel);

							}
							{
								bagOne.weightx = 0.5;
								bagOne.weighty = 1;

								bagOne.gridx = 1;
								bagOne.gridy = 2;

								txtPhaseID = new _JLookup("");
								pnlNorth.add(txtPhaseID,bagOne);
								txtPhaseID.setHorizontalAlignment(JTextField.CENTER);
								txtPhaseID.setLookupSQL(_PagibigStatusMonitoring.sqlPhase(txtProID.getValue()));
								txtPhaseID.setReturnColumn(1);
								txtPhaseID.setFont(FncGlobal.sizeTextValue);
								txtPhaseID.addLookupListener(new LookupListener() {

									public void lookupPerformed(LookupEvent event) {
										Object[] data = ((_JLookup) event.getSource()).getDataSet();
										if (data != null) {
											txtPhase.setText(data[2].toString());

										}

									}
								});
							}
							{
								bagOne.weightx = 1.25;
								bagOne.weighty = 1;

								bagOne.gridx = 2;
								bagOne.gridy = 2;

								txtPhase = new JTextField("");
								pnlNorth.add(txtPhase,bagOne);
								txtPhase.setHorizontalAlignment(JTextField.CENTER);
								txtPhase.setFont(FncGlobal.sizeTextValue);

							}
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;

								bagOne.gridx = 0;
								bagOne.gridy = 3;
								
								rbtnORDateBelow = new JRadioButton("OR Date July 15, 2020 and Below");
								pnlNorth.add(rbtnORDateBelow, bagOne);
								
							}
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;

								bagOne.gridx = 0;
								bagOne.gridy = 4;

								lblNotice = new JLabel("Notice Type");
								pnlNorth.add(lblNotice,bagOne);
								lblNotice.setFont(FncGlobal.sizeLabel);

							}
							{
								bagOne.weightx = 0.5;
								bagOne.weighty = 1;

								bagOne.gridx = 1;
								bagOne.gridy = 4;

								txtNoticeID = new _JLookup();
								pnlNorth.add(txtNoticeID,bagOne );
								txtNoticeID.setHorizontalAlignment(JTextField.CENTER);
								txtNoticeID.setLookupSQL(hdmfNot.NoticeSQL());
								txtNoticeID.setReturnColumn(0);
								txtNoticeID.setFont(FncGlobal.sizeTextValue);
								txtNoticeID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup) e.getSource()).getDataSet();

										if (data!=null) {
											chkBatch.setSelected(false);
											txtBatchID.setValue(null);

											txtNotice.setText(data[1].toString());
											txtBatchID.setLookupSQL(hdmfNot.Batch(txtNoticeID.getValue()));

											displayQualifiedAccts();
										}
									}
								});

							}
							{
								bagOne.weightx = 1.25;
								bagOne.weighty = 1;

								bagOne.gridx = 2;
								bagOne.gridy = 4;

								txtNotice = new JTextField("");
								pnlNorth.add(txtNotice,bagOne);
								txtNotice.setHorizontalAlignment(JTextField.CENTER);
								txtNotice.setFont(FncGlobal.sizeTextValue);

							}
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;

								bagOne.gridx = 0;
								bagOne.gridy = 5;

								chkBatch = new JCheckBox("Batch No");
								pnlNorth.add(chkBatch,bagOne);
								chkBatch.setHorizontalAlignment(JTextField.LEFT);
								chkBatch.setSelected(false);
								chkBatch.setFont(FncGlobal.sizeLabel);
								chkBatch.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent evChk) {
										Boolean blnDo = evChk.getStateChange() == ItemEvent.SELECTED;
										txtBatchID.setValue(null);
										txtBatchID.setEnabled(blnDo);

										modelPagibigNotices.clear();
										scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
										rowHeaderPagibigNotices.setModel(new DefaultListModel());
										//chkClient.setSelected(!blnDo);
										/*txtClientID.setEnabled(!blnDo);
										txtClientID.setValue("");
										txtClient.setText("");
										btnPreview.setText("Preview");*/
									}
								});

							}
							{
								bagOne.weightx = 0.5;
								bagOne.weighty = 1;

								bagOne.gridx = 1;
								bagOne.gridy = 5;
								

								txtBatchID = new _JLookup();
								pnlNorth.add(txtBatchID,bagOne);
								txtBatchID.setHorizontalAlignment(JTextField.CENTER);
								txtBatchID.setReturnColumn(0);
								txtBatchID.setLookupSQL(hdmfNot.Batch(""));
								txtBatchID.setEnabled(false);
								txtBatchID.setFont(FncGlobal.sizeTextValue);
								txtBatchID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										if(txtNoticeID.getValue().equals("105")){
											_PagibigNotices.displayNTC_Qualified_First_Notice(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue(), false);
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("111")){
											_PagibigNotices.displayNTC_Qualified_Final_Notice(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue(), false);
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}
										
										if(txtNoticeID.getValue().equals("137")){
											_PagibigNotices.displayQualifiedNTC_RequiredCompletion(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("94")){
											_PagibigNotices.displayOffREMAccts(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("127")){
											_PagibigNotices.displayQualifiedForFirstFiling_Notice(modelPagibigNotices, rowHeaderPagibigNotices, txtNoticeID.getValue(), txtBatchID.getValue(), txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("128")){
											_PagibigNotices.displayQualifiedForFirstFiling_Notice(modelPagibigNotices, rowHeaderPagibigNotices, txtNoticeID.getValue(), txtBatchID.getValue(), txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("122")){
											_PagibigNotices.displayNOA_Qualified_FirstNotice(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("123")){
											_PagibigNotices.displayNOA_Qualified_FinalNotice(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("63")){
											_PagibigNotices.displayNoticeTurnover_HDMF_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("118")){
											_PagibigNotices.displayNoticePostFiling_FirstNotice_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("119")){
											_PagibigNotices.displayNoticePostFiling_FinalNotice_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("134")){
											_PagibigNotices.displayNoticeTurnover_Cash_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("69")){
											_PagibigNotices.displayNoticeTurnover_LotOnly_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("101")){
											_PagibigNotices.displayNoticeAssumedTurnover(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("130")){
											_PagibigNotices.displayNoticeLoanReturned_FirstNotice(modelPagibigNotices, rowHeaderPagibigNotices, txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}


										if(txtNoticeID.getValue().equals("131")){
											_PagibigNotices.displayNoticeLoanReturned_FinalNotice(modelPagibigNotices, rowHeaderPagibigNotices, txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}

										if(txtNoticeID.getValue().equals("136")){
											_PagibigNotices.displayQualifiedPmt_Moratorium_Notice(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}
										if(txtNoticeID.getValue().equals("138")){
											_PagibigNotices.displayNOA_Loan_Approval(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}
										if(txtNoticeID.getValue().equals("139")){
											_PagibigNotices.displayNotice_Filling_Hdmf(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}
										if(txtNoticeID.getValue().equals("141")){
											_PagibigNotices.displayNotice_Post_Filing_Hdmf(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
											scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
											tblPagibigNotices.packAll();
										}


										
										btnState(false, true, true);

										if (txtNoticeID.getValue().equals("127") && !txtBatchID.getValue().equals("")) {
											btnState(false, true, true);
										} else if (txtNoticeID.getValue().equals("127") && txtBatchID.getValue().equals("")) {
											btnState(false, true, true);
										} else if (txtNoticeID.getValue().equals("128") && !txtBatchID.getValue().equals("")) {
											btnState(false, true, true);
										} else if (txtNoticeID.getValue().equals("128") && txtBatchID.getValue().equals("")) {
											btnState(true, true, true);
										}


									}
								});

							}

						}

					}
					{
						gbc.weightx = 1;
						gbc.weighty = 1.5;

						gbc.gridx = 0;
						gbc.gridy = 1;
						gbc.gridheight = 2;
						
						
						JXPanel pnlTbl = new JXPanel(new GridBagLayout());
						pnlCenter.add(pnlTbl,gbc);
						{
							GridBagConstraints bagTwo = new  GridBagConstraints();
							bagTwo.fill = GridBagConstraints.BOTH;
							bagTwo.insets = new Insets(5, 5, 5, 5);
							{
								bagTwo.weightx = 1;
								bagTwo.weighty = 1;
								
								bagTwo.gridx = 0;
								bagTwo.gridy = 0;
								
								scrollPagibigNotices = new JScrollPane();
								pnlTbl.add(scrollPagibigNotices,bagTwo);
								{

									modelPagibigNotices = new modelSpecialNotices();

									tblPagibigNotices = new _JTableMain(modelPagibigNotices);
									scrollPagibigNotices.setViewportView(tblPagibigNotices);
									scrollPagibigNotices.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									tblPagibigNotices.setSortable(false);
									tblPagibigNotices.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq No");
									tblPagibigNotices.setFont(FncGlobal.sizeTextValue);
									
							
								}
								{
									rowHeaderPagibigNotices = tblPagibigNotices.getRowHeader();
									rowHeaderPagibigNotices.setModel(new DefaultListModel());
									scrollPagibigNotices.setRowHeaderView(rowHeaderPagibigNotices);
									scrollPagibigNotices.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}
								
							}
						}
					}
				}

			
				
			}
		
			{
				pnlSouth = new JPanel(new GridLayout(1, 5, 3, 3));
				panMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(0, 30));
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					//pnlSouth.add(Box.createHorizontalBox());
				}
				{
					btnTag = new JButton("Tag");
					pnlSouth.add(btnTag);
					btnTag.setActionCommand(btnTag.getText());
					btnTag.addActionListener(this);
					btnTag.setEnabled(false);
					btnTag.setFont(FncGlobal.sizeControls);
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
					btnPreview.addActionListener(this);
					btnPreview.setEnabled(false);
					btnPreview.setFont(FncGlobal.sizeControls);

				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
					btnCancel.addActionListener(this);
					btnCancel.setEnabled(false);
					btnCancel.setFont(FncGlobal.sizeControls);

				}
			}
		}
		btnState(false, false, false);
	}

	private void btnState(Boolean sTag, Boolean sPreview, Boolean sCancel){
		btnTag.setEnabled(sTag);
		btnPreview.setEnabled(sPreview);
		btnCancel.setEnabled(sCancel);
	}

	private void previewTransmittal(String batch_no){

		if(txtNoticeID.getValue().equals("105") || txtNoticeID.getValue().equals("111") || txtNoticeID.getValue().equals("122") || txtNoticeID.getValue().equals("123") 
				|| txtNoticeID.getValue().equals("63") || txtNoticeID.getValue().equals("118") || txtNoticeID.getValue().equals("134") || txtNoticeID.getValue().equals("69")
				|| txtNoticeID.getValue().equals("101") || txtNoticeID.getValue().equals("94") || txtNoticeID.getValue().equals("137") || txtNoticeID.getValue().equals("138")){

			/*Map<String, Object> mapParameters = new HashMap<String, Object>();

			mapParameters.put("company_name", txtCompany.getText());
			mapParameters.put("prepared_by", UserInfo.FullName2);
			mapParameters.put("batch_no", batch_no);*/

			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
			ArrayList<String> listSeq = new ArrayList<String>();
			//ArrayList<Integer> listSeq = new ArrayList<Integer>();

			for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
				Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

				if(isSelected){
					String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
					String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
					String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
					Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

					listEntityID.add(String.format("%s", entity_id));
					listProjID.add(String.format("%s", proj_id));
					listPBL.add(String.format("%s", pbl_id));
					listSeq.add(String.format("%s", seq_no));
					//listSeq.add(seq_no);
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
			String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

			Map<String, Object> mapParameters = new HashMap<String, Object>();

			//mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
			mapParameters.put("company_name", txtCompany.getText());
			mapParameters.put("prepared_by", UserInfo.FullName2);
			//mapParameters.put(company_alias, UserInfo.Alias);
			mapParameters.put("entity_id", entity_id);
			mapParameters.put("proj_id", proj_id);
			mapParameters.put("pbl_id", pbl_id);
			mapParameters.put("seq_no", seq_no);
			mapParameters.put("batch_no", batch_no);


			FncReport.generateReport("/Reports/rptNTCList_IncompleteDocs_First_Notice.jasper", "List of Clients (Transmittal)", mapParameters);
		}

		if(txtNoticeID.getValue().equals("111")){

		}
	}

	private void previewTransmittal2(String batch_no){

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("batch_no", batch_no);
		FncReport.generateReport("/Reports/subrptTransmittalForPostOffice.jasper", "Notices Transmittal", mapParameters);

	}

	private void cancelPagibigNotices(){
		modelPagibigNotices.clear();
		rowHeaderPagibigNotices.setModel(new DefaultListModel());
		scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
		btnState(false, false, false);
	}

	public void actionPerformed(ActionEvent act) {
		String actionCommand = act.getActionCommand();

		if(actionCommand.equals("Tag")){
			if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Are you sure you want to save?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

				if(txtNoticeID.getValue().equals("105")){
					String batch_no = _PagibigNotices.saveNTC_IncompleteDocs_FirstNotice(modelPagibigNotices);
					if(_PagibigNotices.checkEmptyNTCRemarks(batch_no, "105")) {
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Some accounts have no remarks on this batch no. Please Contact JST", "Tag", JOptionPane.WARNING_MESSAGE);
					}else {
						previewTransmittal(batch_no);
						previewTransmittal2(batch_no);
						generateNTC_First_Notice(batch_no);
					}
					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				if(txtNoticeID.getValue().equals("111")){
					String batch_no = _PagibigNotices.saveNTC_IncompleteDocs_FinalNotice(modelPagibigNotices);
					if(_PagibigNotices.checkEmptyNTCRemarks(batch_no, "111")) {
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Some accounts have no remarks on this batch no. Please Contact JST", "Tag", JOptionPane.WARNING_MESSAGE);
					}else {
						previewTransmittal(batch_no);
						previewTransmittal2(batch_no);
						generateNTC_FinalNotice(batch_no);
					}
					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}
				
				if(txtNoticeID.getValue().equals("137")){
					String batch_no = _PagibigNotices.saveNTC_Required_Completion(modelPagibigNotices);
					if(_PagibigNotices.checkEmptyNTCRemarks(batch_no, "137")) {
						JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Some accounts have no remarks on this batch no. Please Contact JST", "Tag", JOptionPane.WARNING_MESSAGE);
					}else {
						previewTransmittal(batch_no);
						previewTransmittal2(batch_no);
						generateNTC_RequiredCompletion(batch_no);
					}
					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}
				
				if(txtNoticeID.getValue().equals("94")) {
					String batch_no = _PagibigNotices.saveOff_Rem_Accts(modelPagibigNotices);
					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateOff_Rem_Accts();

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				if (txtNoticeID.getValue().equals("128")) {
					txtBatchID.setValue(_PagibigNotices.save_QualifiedForFirstFiling_FinalNotice(modelPagibigNotices));

					System.out.println("");
					System.out.println("txtBatchID: " + txtBatchID.getValue()); 

					if (!txtBatchID.equals("")) {
						txtBatchID.setEnabled(true);
					} else {
						txtBatchID.setEnabled(false);
					}

					displayQualifiedAccts();
					/*Preview();*/
				}

				if (txtNoticeID.getValue().equals("127")) {
					btnState(false, true, false);
					txtBatchID.setValue(_PagibigNotices.save_QualifiedForFirstFiling_FirstNotice(modelPagibigNotices));

					System.out.println("");
					System.out.println("txtBatchID: " + txtBatchID.getValue()); 

					if (!txtBatchID.equals("")) {
						txtBatchID.setEnabled(true);
					} else {
						txtBatchID.setEnabled(false);
					}

					displayQualifiedAccts();
					//Preview();
				}

				if(txtNoticeID.getValue().equals("122")){
					String batch_no = _PagibigNotices.saveNOA_FirstNotice(modelPagibigNotices);
					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNOA_FirstNotice();

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}
				
				if(txtNoticeID.getValue().equals("138")){
					String batch_no = _PagibigNotices.saveNOA_LoanApproval(modelPagibigNotices);
					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNOA_LoanApproval();
					
					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}
				if(txtNoticeID.getValue().equals("139")){
					String batch_no = _PagibigNotices.saveNotice_Filing_Hdmf(modelPagibigNotices);
					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNotice_Filing_Hdmf();
					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}
				if(txtNoticeID.getValue().equals("141")){
					String batch_no = _PagibigNotices.saveNotice_Post_Filing_Hdmf(modelPagibigNotices);
					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNotice_Post_Filing_Hdmf();
					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				if(txtNoticeID.getValue().equals("123")){
					String batch_no = _PagibigNotices.saveNOA_FinalNotice(modelPagibigNotices);

					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNOA_FinalNotice();

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				if(txtNoticeID.getValue().equals("63")){
					String batch_no = _PagibigNotices.saveNoticeOfTurnover_HDMF(modelPagibigNotices);

					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNoticeTurnover_HDMF();

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				if(txtNoticeID.getValue().equals("118")){
					String batch_no = _PagibigNotices.savePostFiling_FirstNotice(modelPagibigNotices);

					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generatePostFiling_FirstNotice();

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				if(txtNoticeID.getValue().equals("119")){
					String batch_no = _PagibigNotices.savePostFiling_FinalNotice(modelPagibigNotices);

					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generatePostFiling_FinalNotice();

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				if(txtNoticeID.getValue().equals("134")){
					String batch_no = _PagibigNotices.saveNoticeTurnover_Cash(modelPagibigNotices);

					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNoticeTurnover_Cash();

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				if(txtNoticeID.getValue().equals("69")){
					String batch_no = _PagibigNotices.saveNoticeTurnover_LotOnly(modelPagibigNotices);

					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNoticeTurnover_LotOnly();

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				if(txtNoticeID.getValue().equals("101")){
					String batch_no = _PagibigNotices.saveNoticeAssumed_Turnover(modelPagibigNotices);

					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generateNoticeAssumedTurnover();

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				if (txtNoticeID.getValue().equals("131")) {

					txtBatchID.setValue(_PagibigNotices.save_LoanReturned_FinalNotice(modelPagibigNotices));

					System.out.println("");
					System.out.println("txtBatchID: " + txtBatchID.getValue()); 

					if (!txtBatchID.equals("")) {
						txtBatchID.setEnabled(true);
					} else {
						txtBatchID.setEnabled(false);
					}

					displayQualifiedAccts();
					btnState(false, true, false);
				}

				if(txtNoticeID.getValue().equals("136")) {
					String batch_no = _PagibigNotices.savePmt_Moratorium_Notice(modelPagibigNotices);
					previewTransmittal(batch_no);
					previewTransmittal2(batch_no);
					generatePaymentMoratorium_Notice();;

					modelPagibigNotices.clear();
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
					rowHeaderPagibigNotices.setModel(new DefaultListModel());
				}

				btnState(false, true, false);
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Save", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if(actionCommand.equals("Preview")){
			System.out.println("noticeid:"+ txtNoticeID.getValue());
			if(txtNoticeID.getValue().equals("105")){
				previewTransmittal(txtBatchID.getValue());
				generateNTC_First_Notice(txtBatchID.getValue());
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
				System.out.printf("Display value of batch no: %s%n", txtBatchID.getText());
			}

			if(txtNoticeID.getValue().equals("111")){
				previewTransmittal(txtBatchID.getValue());
				generateNTC_FinalNotice(txtBatchID.getValue());
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}
			
			if(txtNoticeID.getValue().equals("137")){
				previewTransmittal(txtBatchID.getValue());
				generateNTC_RequiredCompletion(txtBatchID.getValue());
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("94")){
				previewTransmittal(txtBatchID.getValue());
				generateNotice_Off_Rem();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("122")){
				previewTransmittal(txtBatchID.getValue());
				generateNOA_FirstNotice();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("123")){
				previewTransmittal(txtBatchID.getValue());
				generateNOA_FinalNotice();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("63")){
				previewTransmittal(txtBatchID.getValue());
				generateNoticeTurnover_HDMF();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("118")){
				previewTransmittal(txtBatchID.getValue());
				generatePostFiling_FirstNotice();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("119")){
				generatePostFiling_FinalNotice();
				if(txtBatchID.getValue() != null){
					previewTransmittal(txtBatchID.getValue());
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("127") || txtNoticeID.getValue().equals("128")){
				Preview();
			}

			if(txtNoticeID.getValue().equals("134")){
				previewTransmittal(txtBatchID.getValue());
				generateNoticeTurnover_Cash();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("136")) {
				previewTransmittal(txtBatchID.getValue());
				generatePaymentMoratorium_Notice();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("69")){
				previewTransmittal(txtBatchID.getValue());
				generateNoticeTurnover_LotOnly();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("101")){
				previewTransmittal(txtBatchID.getValue());
				generateNoticeAssumedTurnover();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}

			if(txtNoticeID.getValue().equals("130")){
				GenerateLoanReturnedFirstNotice(); 
			}

			if(txtNoticeID.getValue().equals("131")){
				GenerateLoanReturnedFinalNotice(); 
			}

			if(txtNoticeID.getValue().equals("138")){
				previewTransmittal(txtBatchID.getValue());
				generateNOA_LoanApproval();
				if(txtBatchID.getValue() != null){
					previewTransmittal2(txtBatchID.getValue());
				}
			}
			if(txtNoticeID.getValue().equals("139")){
				preview_filing();
//				generateNotice_Filing_Hdmf();
//				GenerateFirstFilingNoticeTransmittal(txtBatchID.getValue());
//				GenerateFirstFilingNoticePhilPostTransmittal(txtBatchID.getValue());
//				if(txtBatchID.getValue() != null){
//					previewTransmittal2(txtBatchID.getValue());
//				}
			}
			if(txtNoticeID.getValue().equals("141")){
				preview_post_filing();
			}

			/*
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", strBat);
		mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("company_address", "");
		mapParameters.put("notice_type_id", "130");
		mapParameters.put("prepared_by", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("prepared_by_code", UserInfo.EmployeeCode);
		FncReport.generateReport("/Reports/rpt_hdmf_ReturnedHDMF_FirstNotice.jasper", "Returned from HDMF First Notice", "", mapParameters);
			 */
		}

		if(actionCommand.equals("Cancel")){
			cancelPagibigNotices();
		}


		/*if (actionCommand.equals("gENERATE")) {
			if (chkClient.isSelected()) {
				Generate();	
			} else {
				Preview();
			}
		}*/
	}

	private void displayQualifiedAccts() {
		FncGlobal.startProgress("Generating Qualified Accounts");

		new Thread(new Runnable() {
			public void run() {

				if(txtNoticeID.getValue().equals("94")) {
					_PagibigNotices.displayOffREMAccts(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("105")){
					_PagibigNotices.displayNTC_Qualified_First_Notice(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue(), rbtnORDateBelow.isSelected());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("111")){
					_PagibigNotices.displayNTC_Qualified_Final_Notice(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue(), rbtnORDateBelow.isSelected());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}
				
				if(txtNoticeID.getValue().equals("137")) {
					_PagibigNotices.displayQualifiedNTC_RequiredCompletion(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue() ,txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("127")){
					_PagibigNotices.displayQualifiedForFirstFiling_Notice(modelPagibigNotices, rowHeaderPagibigNotices, txtNoticeID.getValue(), txtBatchID.getValue(), txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("128")){
					_PagibigNotices.displayQualifiedForFirstFiling_Notice(modelPagibigNotices, rowHeaderPagibigNotices, txtNoticeID.getValue(), txtBatchID.getValue(), txtCoID.getValue() ,txtProID.getValue(), txtPhaseID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("122")){
					_PagibigNotices.displayNOA_Qualified_FirstNotice(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("123")){
					_PagibigNotices.displayNOA_Qualified_FinalNotice(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("62")){
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("63")){
					_PagibigNotices.displayNoticeTurnover_HDMF_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("69")){
					_PagibigNotices.displayNoticeTurnover_LotOnly_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("118")){
					_PagibigNotices.displayNoticePostFiling_FirstNotice_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("119")){
					_PagibigNotices.displayNoticePostFiling_FinalNotice_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("134")){
					_PagibigNotices.displayNoticeTurnover_Cash_Qualified(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("101")){
					_PagibigNotices.displayNoticeAssumedTurnover(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("131")){
					_PagibigNotices.displayNoticeLoanReturned_FinalNotice(modelPagibigNotices, rowHeaderPagibigNotices, txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}

				if(txtNoticeID.getValue().equals("136")){
					_PagibigNotices.displayQualifiedPmt_Moratorium_Notice(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}
				
				if(txtNoticeID.getValue().equals("137")) {
					_PagibigNotices.displayQualifiedNTC_RequiredCompletion(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtPhaseID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}
				if(txtNoticeID.getValue().equals("138")) {
					_PagibigNotices.displayNOA_Loan_Approval(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}
				if(txtNoticeID.getValue().equals("139")) {
					_PagibigNotices.displayNotice_Filling_Hdmf(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}
				if(txtNoticeID.getValue().equals("141")) {
					_PagibigNotices.displayNotice_Post_Filing_Hdmf(modelPagibigNotices, rowHeaderPagibigNotices, txtCoID.getValue(), txtProID.getValue(), txtBatchID.getValue());
					scrollPagibigNotices.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblPagibigNotices.getRowCount())));
					tblPagibigNotices.packAll();
				}
				if (!chkBatch.isSelected()) {
					btnState(true, true, true);
				} else {
					btnState(false, true, false);
				}

				FncGlobal.stopProgress();
			}
		}).start();
		
	}

	private void Preview() {
		String strSQL = "";

		pgUpdate db_delete = new pgUpdate();
		db_delete.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
		db_delete.commit();

		System.out.println("");
		System.out.println("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'");		

		for (int x = 0; x < modelPagibigNotices.getRowCount(); x++) {
			if ((Boolean) modelPagibigNotices.getValueAt(x, 0)) {
				pgUpdate dbInsert = new pgUpdate();
				strSQL = "insert into tmp_hdmf (client_name, emp_code) values ('"+modelPagibigNotices.getValueAt(x, 1)+"', '"+UserInfo.EmployeeCode+"')";

				System.out.println("");
				System.out.println(strSQL);

				dbInsert.executeUpdate(strSQL, false);
				dbInsert.commit();
			}
		}

		try {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("batch_no", txtBatchID.getValue());
			mapParameters.put("company", txtCompany.getText());
			mapParameters.put("company_address", "");
			mapParameters.put("notice_type_id", txtNoticeID.getValue());
			mapParameters.put("prepared_by", _RealTimeDebit.GetName(UserInfo.EmployeeCode));

			if (txtNoticeID.getValue().equals("127")) {
				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingFirstNotice.jasper", "Qualified for First Filing First Notice", "", mapParameters);
			} else if (txtNoticeID.getValue().equals("128")) {
				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingFinalNotice.jasper", "Qualified for First Filing Final Notice", "", mapParameters);					
			}

			if(txtNoticeID.getValue().equals("104") || txtNoticeID.getValue().equals("105")){

			}

			if (!txtBatchID.getValue().equals("")) {
				hdmfNot.GenerateFirstFilingNoticeTransmittal(txtBatchID.getValue(), txtCompany.getText(), txtNotice.getText());
				hdmfNot.GenerateFirstFilingNoticePhilPostTransmittal(txtBatchID.getValue(), txtCompany.getText(), txtNotice.getText());	
			}
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(null, "Transmittals preview will only be available after tagging.");
		}
	}
	private void preview_filing() {
		String strSQL = "";

		pgUpdate db_delete = new pgUpdate();
		db_delete.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
		db_delete.commit();

		System.out.println("");
		System.out.println("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'");		

		for (int x = 0; x < modelPagibigNotices.getRowCount(); x++) {
			if ((Boolean) modelPagibigNotices.getValueAt(x, 0)) {
				pgUpdate dbInsert = new pgUpdate();
				strSQL = "insert into tmp_hdmf (client_name, emp_code) values ('"+modelPagibigNotices.getValueAt(x, 1)+"', '"+UserInfo.EmployeeCode+"')";

				System.out.println("");
				System.out.println(strSQL);

				dbInsert.executeUpdate(strSQL, false);
				dbInsert.commit();
			}
		}

		try {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("batch_no", txtBatchID.getValue());
			mapParameters.put("company", txtCompany.getText());
			mapParameters.put("company_address", "");
			mapParameters.put("notice_type_id", txtNoticeID.getValue());
			mapParameters.put("prepared_by", _RealTimeDebit.GetName(UserInfo.EmployeeCode));

		
			FncReport.generateReport("/Reports/rpt_NoticeonFillingatHDMF.jasper","Notice on Filing", "", mapParameters);
			if (!txtBatchID.getValue().equals("")) {
				hdmfNot.GenerateFirstFilingNoticeTransmittal(txtBatchID.getValue(), txtCompany.getText(), txtNotice.getText());
				hdmfNot.GenerateFirstFilingNoticePhilPostTransmittal(txtBatchID.getValue(), txtCompany.getText(), txtNotice.getText());	
			}
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(null, "Transmittals preview will only be available after tagging.");
		}
	}
	private void preview_post_filing() {

		String strSQL = "";

		pgUpdate db_delete = new pgUpdate();
		db_delete.executeUpdate("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'", false);
		db_delete.commit();

		System.out.println("");
		System.out.println("delete from tmp_hdmf where emp_code = '"+UserInfo.EmployeeCode+"'");		

		for (int x = 0; x < modelPagibigNotices.getRowCount(); x++) {
			if ((Boolean) modelPagibigNotices.getValueAt(x, 0)) {
				pgUpdate dbInsert = new pgUpdate();
				strSQL = "insert into tmp_hdmf (client_name, emp_code) values ('"+modelPagibigNotices.getValueAt(x, 1)+"', '"+UserInfo.EmployeeCode+"')";

				System.out.println("");
				System.out.println(strSQL);

				dbInsert.executeUpdate(strSQL, false);
				dbInsert.commit();
			}
		}

		try {
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("batch_no", txtBatchID.getValue());
			mapParameters.put("company", txtCompany.getText());
			mapParameters.put("company_address", "");
			mapParameters.put("notice_type_id", txtNoticeID.getValue());
			mapParameters.put("prepared_by", UserInfo.FullName);

		
			FncReport.generateReport("/Reports/rpt_NoticeonPostFillingatHDMF.jasper","Notice on Post Filing", "", mapParameters);
			if (!txtBatchID.getValue().equals("")) {
				hdmfNot.GenerateFirstFilingNoticeTransmittal(txtBatchID.getValue(), txtCompany.getText(), txtNotice.getText());
				hdmfNot.GenerateFirstFilingNoticePhilPostTransmittal(txtBatchID.getValue(), txtCompany.getText(), txtNotice.getText());	
			}
		} catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(null, "Transmittals preview will only be available after tagging.");
		}
	
	}
	private void generateNTC_First_Notice(String batch_no){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		/*System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);*/

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", batch_no);

		//System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeToComply_IncompleteDocs_FirstNotice.jasper", "Notice to Comply First Notice", mapParameters);
	}
	
	private void generateNTC_RequiredCompletion(String batch_no){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		/*System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);*/

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", batch_no);

		//System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());
		if(txtCoID.getValue().equals("01") && txtProID.getValue().equals("019")) {
			FncReport.generateReport("/Reports/rptNTC_Required_Completion_ER.jasper", "Notice to Comply Requirements for Completion", mapParameters);
		}
		if(txtCoID.getValue().equals("02") && txtProID.getValue().equals("018")) {
			FncReport.generateReport("/Reports/rptNTC_Required_Completion_EVE.jasper", "Notice to Comply Requirements for Completion", mapParameters);
		}
		else {
			FncReport.generateReport("/Reports/rptNTC_Required_Completion.jasper", "Notice to Comply Requirements for Completion", mapParameters);
		}
	}

	private void generateOff_Rem_Accts() {
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptOff_Rem_Accts.jasper", "First Notice - Post Filing at HDMF", mapParameters);
	}

	private void generatePostFiling_FirstNotice(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeForPostFiling_FirstNotice.jasper", "First Notice - Post Filing at HDMF", mapParameters);
	}

	private void generatePostFiling_FinalNotice(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeForPostFiling_FinalNotice.jasper", "Final Notice - Post Filing at HDMF", mapParameters);
	}

	private void generateNTC_FinalNotice(String batch_no){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put("company_alias", UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", batch_no);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeToComply_IncompleteDocs_FinalNotice.jasper", "Notice to Comply First Notice", mapParameters);
	}


	private void generateNotice_Off_Rem(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put("company_alias", UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptOff_Rem_Accts.jasper", "NOTICE FOR OFFICIALLY REM ACCTS TITLE TRANSFER", mapParameters);
	}

	private void generateNOA_FirstNotice(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeOfApproval_FirstNotice.jasper", "Notice of Approval-First Notice", mapParameters);
	}
	
	private void generateNOA_LoanApproval(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

	
		
		System.out.printf("Display value of Batch No55: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeOfLoanApproval.jasper", "Notice of Loan Approval", mapParameters);
	}
	private void generateNotice_Filing_Hdmf(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("company_address", "");
		mapParameters.put("notice_type_id", "139");
		mapParameters.put("prepared_by",
				FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
						+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
						+ UserInfo.EmployeeCode + "'"));
		mapParameters.put("prepared_by_code", UserInfo.EmployeeCode);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		
		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());
		System.out.printf("company: %s%n", txtCompany.getText());
		System.out.printf("prepared_by: %s%n", FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
				+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
				+ UserInfo.EmployeeCode + "'"));


		FncReport.generateReport("/Reports/rpt_NoticeonFillingatHDMF.jasper","Notice on Filing", "", mapParameters);
	}
	private void generateNotice_Post_Filing_Hdmf(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("company_address", "");
		mapParameters.put("notice_type_id", "141");
		mapParameters.put("prepared_by",
				FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
						+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
						+ UserInfo.EmployeeCode + "'"));
		mapParameters.put("prepared_by_code", UserInfo.EmployeeCode);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		
		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());
		System.out.printf("company: %s%n", txtCompany.getText());
		System.out.printf("prepared_by: %s%n", FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
				+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
				+ UserInfo.EmployeeCode + "'"));


		FncReport.generateReport("/Reports/rpt_NoticeonPostFillingatHDMF.jasper","Notice on Post Filing", "", mapParameters);
	}
	private  void GenerateFirstFilingNoticeTransmittal(String strBat) {
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company",txtCompany.getText());
		mapParameters.put("02_BatchNo", strBat);
		mapParameters.put("03_UserName",

				FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
						+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
						+ UserInfo.EmployeeCode + "'")

		);
		System.out.println("usernm:" + FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
				+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
				+ UserInfo.EmployeeCode + "'"));
		mapParameters.put("04_UserCode", FncGlobal
				.GetString("select login_id from rf_system_user where emp_code = '" + UserInfo.EmployeeCode + "'"));
		mapParameters.put("05_NoticeType", "First Notice - Filing at HDMF");
//		FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticeTransmittal.jasper",
//				"Qualified for First Filing Notice Transmittal", "", mapParameters);
		FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticeTransmittal_v3.jasper",
				"Qualified for First Filing Notice Transmittal", "", mapParameters);
	}
	private void GenerateFirstFilingNoticePhilPostTransmittal(String strBat) {
		pgSelect dbExec = new pgSelect();
		String strSQL = "select distinct region::varchar(155) from view_notice_transmittal('" + strBat + "')";
		dbExec.select(strSQL);

		System.out.println("");
		System.out.println("I went here!");

		if (dbExec.getRowCount() > 1) {
			for (int x = 0; x < dbExec.getRowCount(); x++) {
				System.out.println("");
				System.out.println("dbExec.getResult()[x]: " + dbExec.getResult()[x][0]);

				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", txtCompany.getText());
				mapParameters.put("02_BatchNo", strBat);
				mapParameters.put("03_UserName", GetName(UserInfo.EmployeeCode));
				mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
				mapParameters.put("05_NoticeType", "Notice on Filing at HDMF");
				mapParameters.put("06_region", dbExec.getResult()[x][0]);

				System.out.println("co_i:" + txtCompany.getText());
				System.out.println("co_i:" + txtCompany.getText());
				System.out.println("02_BatchNo:" + strBat);
				System.out.println("03_UserName:" + GetName(UserInfo.EmployeeCode));
				System.out.println("05_NoticeType:" + "Notice on Filing at HDMF");
				System.out.println("02_Bat06_regionchNo:" + dbExec.getResult()[x][0]);

//				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticePhilPostTransmittal.jasper",
//						"Qualified for First Filing Notice PhilPost Transmittal - " + dbExec.getResult()[x][0], "",
//						mapParameters);
				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticePhilPostTransmittal_v3.jasper",
						"Qualified for First Filing Notice PhilPost Transmittal - " + dbExec.getResult()[x][0], "",
						mapParameters);
			}
		} else {

			try {
				Map<String, Object> mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", txtCompany);
				mapParameters.put("02_BatchNo", strBat);
				mapParameters.put("03_UserName",
						FncGlobal.GetString("SELECT B.entity_name\n" + "FROM em_employee A\n"
								+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '"
								+ UserInfo.EmployeeCode + "'")

				);
				mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
				mapParameters.put("05_NoticeType", "First Notice - Filing at HDMF");
				mapParameters.put("06_region", dbExec.getResult()[0][0]);
//				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticePhilPostTransmittal.jasper",
//						"Qualified for First Filing Notice PhilPost Transmittal - " + dbExec.getResult()[0][0], "",
//						mapParameters);
				FncReport.generateReport("/Reports/rpt_QualifiedforFirstFilingNoticePhilPostTransmittal_v3.jasper",
						"Qualified for First Filing Notice PhilPost Transmittal - " + dbExec.getResult()[0][0], "",
						mapParameters);

			} catch (NullPointerException e) {

			}

		}
	}
	private void generateNOA_FinalNotice(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeOfApproval_FinalNotice.jasper", "Notice of Approval-First Notice", mapParameters);
	}

	private void generateNoticeTurnover_HDMF(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeOfTurnover_HDMF.jasper", "Notice of Turn-over (HDMF Financing)", mapParameters);
	}

	private void generateNoticeTurnover_Cash(){

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenqlogo.png"));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeOfTurnOver_Cash.jasper", "Notice of Turn-over (Cash)", mapParameters);
	}

	private void generateNoticeTurnover_LotOnly(){

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenqlogo.png"));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeOfTurnover_LotOnly.jasper", "Notice of Turn-over (Cash)", mapParameters);
	}

	private void generateNoticeAssumedTurnover(){

		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenqlogo.png"));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());
		mapParameters.put("from_card", false);

		System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptNoticeofAssumedTurnover.jasper", "Notice of Turn-over (Cash)", mapParameters);
	}

	private void GenerateNotice() {
		Boolean blnProceed = false;
		String strNotice = txtNoticeID.getValue().toString();
		String strBatch = FncGlobal.GetString("select trim(to_char((max(batch_no::int) + 1), '0000000000')) from rf_client_notices");

		String entity_id = txtClientID.getValue();
		String proj_id = txtProID.getValue();
		String pbl_id = strUnit;
		String strSeq = _RealTimeDebit.GetValue("select trim(seq_no::char(5)) from rf_sold_unit where entity_id = '"+entity_id+"' and projcode = '"+proj_id+"' and pbl_id = '"+pbl_id+"' and status_id = 'A'");
		Integer intRec = FncGlobal.GetInteger("select (max(rec_id::int) + 1) from rf_client_notices");

		if (hdmfNot.NoticeExists(entity_id, proj_id, strUnit, strSeq, strNotice)) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "A notice of the same type that is yet to be sent exists\nwithin the client's notice records. Do you wish to proceed?", 
					"Confirm", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				blnProceed = true;
			} else {
				blnProceed = false;
			}
		} else {
			blnProceed = true;
		}

		if (blnProceed) {
			if (strNotice.equals("127")) {
				blnProceed = hdmfNot.QualifiedForFirstFilingFirstNotice(entity_id, proj_id, pbl_id, strSeq, intRec);
			} else if (strNotice.equals("128")) {
				blnProceed = hdmfNot.QualifiedForFirstFilingFinalNotice(entity_id, proj_id, pbl_id, strSeq, intRec);
			}
		}

		if (blnProceed) {
			if (JOptionPane.showConfirmDialog(getContentPane(), "Generate " + txtNotice.getText() + " for " + txtClient.getText() + "?", 
					"Confirm", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				blnProceed = true;
			} else {
				blnProceed = false;
			}
		}

		if (blnProceed) {
			pgUpdate db = new pgUpdate();
			String sql = "insert into rf_client_notices (rec_id, entity_id, projcode, pbl_id, seq_no, part_id, notice_id, stage_id, dateprep, preparedby, batch_no, status_id) \n" +
					"values ('"+intRec+"'::int, '"+entity_id+"', '"+proj_id+"', '"+pbl_id+"', "+strSeq+"::int, '013', \n" +
					"'"+strNotice+"', 1, Now(), '"+UserInfo.EmployeeCode+"', '"+strBatch+"', 'A');";

			System.out.println(sql);
			db.executeUpdate(sql, false);
			db.commit();
			txtBatchID.setValue(strBatch);
			JOptionPane.showMessageDialog(getContentPane(), txtNotice.getText() + " created.");
			Preview();
		} else {
			strBatch = "";
			JOptionPane.showMessageDialog(getContentPane(), "Notice generation cancelled.");
		}
	}

	private void GenerateLoanReturnedFirstNotice() {
		pgUpdate dbExec = new pgUpdate();

		dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_hdmf", true);
		dbExec.commit();

		for (int x = 0; x < modelPagibigNotices.getRowCount(); x++) {
			if ((Boolean) modelPagibigNotices.getValueAt(x, 0)) {
				dbExec = new pgUpdate();
				dbExec.executeUpdate("insert into tmp_hdmf values ('"+modelPagibigNotices.getValueAt(x, 1)+"', '"+UserInfo.EmployeeCode+"')", true);
				dbExec.commit();
			}
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", txtBatchID.getValue().toString());
		mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("company_address", "");
		mapParameters.put("notice_type_id", "130");
		mapParameters.put("prepared_by", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("prepared_by_code", UserInfo.EmployeeCode);
		FncReport.generateReport("/Reports/rpt_hdmf_ReturnedHDMF_FirstNotice.jasper", "Returned from HDMF First Notice", "", mapParameters);

		String strLoanRetBatch = FncGlobal.GetString("select trans_no\n" + 
				"from rf_buyer_status x\n" + 
				"where x.byrstatus_id = '43' and \n" + 
				"exists(select * from rf_client_notices y where y.batch_no = '0000000413' and y.entity_id = x.entity_id \n" + 
				"and y.projcode = x.proj_id and y.pbl_id = x.pbl_id and y.seq_no = x.seq_no and y.status_id = 'A')\n" + 
				"limit 1"); 

		mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("02_BatchNo", txtBatchID.getValue().toString());
		mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("04_UserCode", FncGlobal.GetString("select login_id from rf_system_user where emp_code = '"+UserInfo.EmployeeCode+"'"));
		mapParameters.put("05_NoticeType", "First Notice - Returned from HDMF");
		FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticeTransmittal.jasper", "Returned from HDMF Notice Transmittal", "", mapParameters);

		pgSelect dbSel = new pgSelect();
		String strSQL = "select distinct region::varchar(155) from view_notice_transmittal('"+txtBatchID.getValue().toString()+"')";
		dbSel.select(strSQL);

		if (dbSel.getRowCount() > 1) {
			for (int x = 0; x < dbSel.getRowCount(); x++) {
				System.out.println("");
				System.out.println("dbExec.getResult()[x]: " + dbSel.getResult()[x][0]);

				mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
				mapParameters.put("02_BatchNo", txtBatchID.getValue().toString());
				mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
				mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
				mapParameters.put("05_NoticeType", "First Notice - Returned from HDMF");
				mapParameters.put("06_region", dbSel.getResult()[x][0]);
				FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticePhilPostTransmittal.jasper", "Returned from HDMF Notice PhilPost Transmittal - " + dbSel.getResult()[x][0], "", mapParameters);		
			}
		} else {
			mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
			mapParameters.put("02_BatchNo", txtBatchID.getValue().toString());
			mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
			mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
			mapParameters.put("05_NoticeType", "First Notice - Returned from HDMF");
			mapParameters.put("06_region", dbSel.getResult()[0][0]);
			FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticePhilPostTransmittal.jasper", "Returned from HDMF Notice PhilPost Transmittal - " + dbSel.getResult()[0][0], "", mapParameters);
		}
	}

	private void GenerateLoanReturnedFinalNotice() {
		pgUpdate dbExec = new pgUpdate();

		dbExec = new pgUpdate();
		dbExec.executeUpdate("delete from tmp_hdmf", true);
		dbExec.commit();

		for (int x = 0; x < modelPagibigNotices.getRowCount(); x++) {
			if ((Boolean) modelPagibigNotices.getValueAt(x, 0)) {
				dbExec = new pgUpdate();
				dbExec.executeUpdate("insert into tmp_hdmf values ('"+modelPagibigNotices.getValueAt(x, 1)+"', '"+UserInfo.EmployeeCode+"')", true);
				dbExec.commit();
			}
		}

		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("batch_no", txtBatchID.getValue().toString());
		mapParameters.put("company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("company_address", "");
		mapParameters.put("notice_type_id", "131");
		mapParameters.put("prepared_by", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("prepared_by_code", UserInfo.EmployeeCode);
		FncReport.generateReport("/Reports/rpt_hdmf_ReturnedHDMF_FinalNotice.jasper", "Returned from HDMF Final Notice", "", mapParameters);

		String strLoanRetBatch = FncGlobal.GetString("select trans_no\n" + 
				"from rf_buyer_status x\n" + 
				"where x.byrstatus_id = '43' and \n" + 
				"exists(select * from rf_client_notices y where y.batch_no = '0000000413' and y.entity_id = x.entity_id \n" + 
				"and y.projcode = x.proj_id and y.pbl_id = x.pbl_id and y.seq_no = x.seq_no and y.status_id = 'A')\n" + 
				"limit 1"); 

		mapParameters = new HashMap<String, Object>();
		mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
		mapParameters.put("02_BatchNo", txtBatchID.getValue().toString());
		mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
		mapParameters.put("04_UserCode", FncGlobal.GetString("select login_id from rf_system_user where emp_code = '"+UserInfo.EmployeeCode+"'"));
		mapParameters.put("05_NoticeType", txtNotice.getText());
		FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticeTransmittal.jasper", "Returned from HDMF Notice Transmittal", "", mapParameters);

		pgSelect dbSel = new pgSelect();
		String strSQL = "select distinct region::varchar(155) from view_notice_transmittal('"+txtBatchID.getValue().toString()+"')";
		dbSel.select(strSQL);

		if (dbSel.getRowCount() > 1) {
			for (int x = 0; x < dbSel.getRowCount(); x++) {
				System.out.println("");
				System.out.println("dbExec.getResult()[x]: " + dbSel.getResult()[x][0]);

				mapParameters = new HashMap<String, Object>();
				mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
				mapParameters.put("02_BatchNo", txtBatchID.getValue().toString());
				mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
				mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
				mapParameters.put("05_NoticeType", txtNotice.getText());
				mapParameters.put("06_region", dbSel.getResult()[x][0]);
				FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticePhilPostTransmittal.jasper", "Returned from HDMF Notice PhilPost Transmittal - " + dbSel.getResult()[x][0], "", mapParameters);		
			}
		} else {
			mapParameters = new HashMap<String, Object>();
			mapParameters.put("01_Company", "CENQHOMES DEVELOPMENT CORPORATION");
			mapParameters.put("02_BatchNo", txtBatchID.getValue().toString());
			mapParameters.put("03_UserName", _RealTimeDebit.GetName(UserInfo.EmployeeCode));
			mapParameters.put("04_AsOfDate", FncGlobal.getDateSQL().toString());
			mapParameters.put("05_NoticeType", txtNotice.getText());
			mapParameters.put("06_region", dbSel.getResult()[0][0]);
			FncReport.generateReport("/Reports/rpt_HDMFReturnedNoticePhilPostTransmittal.jasper", "Returned from HDMF Notice PhilPost Transmittal - " + dbSel.getResult()[0][0], "", mapParameters);
		}
	}

	private void generatePaymentMoratorium_Notice(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		//ArrayList<Integer> listSeq = new ArrayList<Integer>();

		for(int x= 0; x<modelPagibigNotices.getRowCount(); x++){
			Boolean isSelected = (Boolean) modelPagibigNotices.getValueAt(x, 0);

			if(isSelected){
				String entity_id = (String) modelPagibigNotices.getValueAt(x, 7);
				String proj_id = (String) modelPagibigNotices.getValueAt(x, 8);
				String pbl_id = (String) modelPagibigNotices.getValueAt(x, 9);
				Integer seq_no = (Integer) modelPagibigNotices.getValueAt(x, 10);

				listEntityID.add(String.format("%s", entity_id));
				listProjID.add(String.format("%s", proj_id));
				listPBL.add(String.format("%s", pbl_id));
				listSeq.add(String.format("%s", seq_no));
				//listSeq.add(seq_no);
			}
		}

		String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String proj_id = listProjID.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");
		String seq_no = listSeq.toString().replaceAll("\\[|\\]", "").replaceAll(" ", "");

		/*System.out.printf("Display value of entity_id: (%s)%n", entity_id);
		System.out.printf("Display value of proj_id: (%s)%n", proj_id);
		System.out.printf("Display value of pbl id: (%s)%n", pbl_id);
		System.out.printf("Display value of seq_no: (%s)%n", seq_no);*/

		Map<String, Object> mapParameters = new HashMap<String, Object>();

		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ sql_getCompanyLogo(txtCoID.getValue())));
		mapParameters.put("company", txtCompany.getText());
		mapParameters.put("prepared_by", UserInfo.Alias);
		//mapParameters.put(company_alias, UserInfo.Alias);
		mapParameters.put("entity_id", entity_id);
		mapParameters.put("proj_id", proj_id);
		mapParameters.put("pbl_id", pbl_id);
		mapParameters.put("seq_no", seq_no);
		mapParameters.put("batch_no", txtBatchID.getValue());

		//System.out.printf("Display value of Batch No: %s%n", txtBatchID.getValue());

		FncReport.generateReport("/Reports/rptPayment_Moratorium_Notice.jasper", "Payment Moratorium Notice", mapParameters);
	}
	
	
	private String sql_getCompanyLogo(String co_id) {

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
	
	
	public static String GetName(String emp_code) {
		String entityid = "";

		String SQL = "SELECT B.entity_name\n" + "FROM em_employee A\n"
				+ "INNER JOIN rf_entity B ON A.entity_id = B.entity_id\n" + "WHERE a.emp_code = '" + emp_code + "'";

		pgSelect sqlExec = new pgSelect();
		sqlExec.select(SQL);

		if (sqlExec.isNotNull()) {
			entityid = (String) sqlExec.getResult()[0][0];
		} else {
			entityid = "";
		}

		return entityid;
	}
}
