package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXPanel;

import com.toedter.calendar.JTextFieldDateEditor;

import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Functions.FncSystem;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTableMain;
import components._JXTextField;
import interfaces._GUI;
import tablemodel.modelBFST_LOGReleased;
import tablemodel.modelBFST_LOGReturned;
import tablemodel.modelBFST_LOGSigned;
import tablemodel.modelBFST_PostCompliance;
import tablemodel.modelBFST_TCTForTransfer;
import tablemodel.modelBFST_TCT_BuyersName;
import tablemodel.modelBFST_LOGExt;
import tablemodel.modelBFST_LOGFiled;

/**
 * @author John Lester Fatallo
 */
public class BankFinancingMonitoring extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	private static final long serialVersionUID = 5697915350803376577L;

	Dimension size = new Dimension(800, 600);
	static String title = "Bank Finance Status Monitoring";

	private JPanel pnlNorth;
	private JPanel pnlNorthCenter;
	private JPanel pnlNCLabel;
	private JLabel lblCompany;
	private JLabel lblProject;
	private JLabel lblPhase;
	private JLabel lblStatus;

	private JPanel pnlNCComponent;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	private _JLookup lookupProject;
	private _JXTextField txtProject;
	private _JLookup lookupPhase;
	private _JXTextField txtPhase;
	private JComboBox cmbStatus;

	private JPanel pnlNorthEast;
	private JButton btnGenerate;

	private JButton btnPost;
	private JButton btnPreview;
	private JButton btnExport;
	
	private JPanel pnlCenter;
	private JLabel lblActualDate;
	private _JDateChooser dteActual;
	private JLabel lblBank;
	private _JLookup lookupBank;
	private _JXTextField txtBank;
	
	private _JTableMain tblBF_StatusTagging;
	private JScrollPane scrollStatusTagging;
	private JList rowHeaderStatustagging;
	private modelBFST_LOGFiled modelLOG_Filed;
	private modelBFST_LOGReturned modelLOG_Returned;
	private modelBFST_LOGReleased modelLOG_Released;
	private modelBFST_LOGSigned modelLOG_Signed;
	private modelBFST_LOGExt modelLOG_Ext;
	private modelBFST_TCTForTransfer modelLOG_TCTForTransfer;
	private modelBFST_TCT_BuyersName modelLOG_TCTBuyersName;
	private modelBFST_PostCompliance modelPost_Compliance;
	
	private _JDateChooser dteEffectivity;

	public BankFinancingMonitoring() {
		super(title, true, true, false, false);
		initGUI();
	}

	public BankFinancingMonitoring(String title) {
		super(title);
		initGUI();
	}

	public BankFinancingMonitoring(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, false, true, true, true);
		initGUI();
	}

	public void initGUI(){
		this.setTitle(title);
		this.setSize(size);
		this.setPreferredSize(size);
		{
			JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setPreferredSize(new Dimension(0, 130));
				{
					pnlNorthCenter = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthCenter, BorderLayout.CENTER);
					pnlNorthCenter.setBorder(JTBorderFactory.createTitleBorder(""));
					{
						pnlNCLabel = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlNorthCenter.add(pnlNCLabel, BorderLayout.WEST);
						{
							lblCompany = new JLabel("Company");
							pnlNCLabel.add(lblCompany);
						}
						{
							lblProject = new JLabel("Project");
							pnlNCLabel.add(lblProject);
						}
						{
							lblPhase = new JLabel("Phase");
							pnlNCLabel.add(lblPhase);
						}
						{
							lblStatus = new JLabel("Status");
							pnlNCLabel.add(lblStatus);
						}
					}
					{
						pnlNCComponent = new JPanel(new GridLayout(4, 1, 3, 3));
						pnlNorthCenter.add(pnlNCComponent, BorderLayout.CENTER);
						{
							JPanel pnlCompany = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlCompany);
							{
								lookupCompany = new _JLookup();
								pnlCompany.add(lookupCompany, BorderLayout.WEST);
								lookupCompany.setPreferredSize(new Dimension(100, 0));
								lookupCompany.setLookupSQL(_BankFinancingMonitoring.sqlCompany());
								//lookupCompany.setValue("02");
								lookupCompany.addLookupListener(new LookupListener() {
									
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											String co_id = (String) data[0];
											String company_name = (String) data[1];
											lookupCompany.setValue(co_id);
											txtCompany.setText(company_name);
											
											lookupProject.setLookupSQL(_BankFinancingMonitoring.sqlProject(co_id));
										}
									}
								});
							}
							{
								txtCompany = new _JXTextField();
								pnlCompany.add(txtCompany, BorderLayout.CENTER);
								//txtCompany.setText("CENQHOMES DEVELOPMENT CORPORATION");
							}
						}
						{
							JPanel pnlProject = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlProject);
							{
								lookupProject = new _JLookup();
								pnlProject.add(lookupProject, BorderLayout.WEST);
								lookupProject.setPreferredSize(new Dimension(100, 0));
								//lookupProject.setValue("015");
								lookupProject.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup) event.getSource()).getDataSet();
										if(data != null){
											String proj_id = (String) data[0];
											String proj_name = (String) data[1];
											
											lookupProject.setValue(proj_id);
											txtProject.setText(proj_name);
											lookupPhase.setLookupSQL(_BankFinancingMonitoring.sqlPhase(proj_id));
										}
									}
								});
							}
							{
								txtProject = new _JXTextField();
								pnlProject.add(txtProject, BorderLayout.CENTER);
								//txtProject.setText("TERRAVERDE RESIDENCES");
							}
						}
						{
							JPanel pnlPhase = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlPhase);
							{
								lookupPhase = new _JLookup();
								pnlPhase.add(lookupPhase, BorderLayout.WEST);
								lookupPhase.setPreferredSize(new Dimension(100, 0));
								//lookupPhase.setValue("1");
								lookupPhase.addLookupListener(new LookupListener() {
									
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup)event.getSource()).getDataSet();
										
										if(data != null){
											String phase = (String) data[1];
											String sub_proj_name = (String) data[2];
											lookupPhase.setValue(phase);
											txtPhase.setText(sub_proj_name);
											
										}
									}
								});
							}
							{
								txtPhase = new _JXTextField();
								pnlPhase.add(txtPhase, BorderLayout.CENTER);
								//txtPhase.setText("Phase 1");
							}
						}
						{
							JPanel pnlStatus = new JPanel(new BorderLayout(5, 5));
							pnlNCComponent.add(pnlStatus);
							{
								cmbStatus = new JComboBox(new String [] {
										"Filed", "Returned from Bank","LOG Released", "LOG Signed", "LOG Ext", "TCT for trans under buyer's name", "TCT under buyer's name", "Post Compliance"});
								pnlStatus.add(cmbStatus, BorderLayout.WEST);
								cmbStatus.setPreferredSize(new Dimension(250, 0));
								cmbStatus.addItemListener(new ItemListener() {
									
									@Override
									public void itemStateChanged(ItemEvent e) {
										int selected_item = cmbStatus.getSelectedIndex();
										
										lookupBank.setValue(null);
										txtBank.setText("");
										lookupBank.setEditable(false);
										dteActual.setDate(null);
										rowHeaderStatustagging.setModel(new DefaultListModel());
										scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
										
										if(selected_item == 0){
											modelLOG_Filed = new modelBFST_LOGFiled();
											modelLOG_Filed.clear();
											tblBF_StatusTagging = new _JTableMain(modelLOG_Filed);
											scrollStatusTagging.setViewportView(tblBF_StatusTagging);
											scrollStatusTagging.setRowHeaderView(rowHeaderStatustagging);
											tblBF_StatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											
											lblActualDate.setText("Date Filed");
											lookupBank.setEditable(true);
										}
										
										if(selected_item == 1){
											modelLOG_Returned = new modelBFST_LOGReturned();
											modelLOG_Returned.clear();
											tblBF_StatusTagging = new _JTableMain(modelLOG_Returned);
											scrollStatusTagging.setViewportView(tblBF_StatusTagging);
											scrollStatusTagging.setRowHeaderView(rowHeaderStatustagging);
											tblBF_StatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											
											lblActualDate.setText("Returned Date");
										}
										
										if(selected_item == 2){
											modelLOG_Released = new modelBFST_LOGReleased();
											//modelLOG_Released.clear();
											tblBF_StatusTagging = new _JTableMain(modelLOG_Released);
											scrollStatusTagging.setViewportView(tblBF_StatusTagging);
											scrollStatusTagging.setRowHeaderView(rowHeaderStatustagging);
											tblBF_StatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											
											lblActualDate.setText("Log Date");
											tblBF_StatusTagging.addMouseListener(BankFinancingMonitoring.this);
										}
										
										if(selected_item == 3){
											modelLOG_Signed = new modelBFST_LOGSigned();
											modelLOG_Signed.clear();
											tblBF_StatusTagging = new _JTableMain(modelLOG_Signed);
											scrollStatusTagging.setViewportView(tblBF_StatusTagging);
											scrollStatusTagging.setRowHeaderView(rowHeaderStatustagging);
											tblBF_StatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											
											lblActualDate.setText("LOG Signed Date");
										}
										
										if(selected_item == 4){
											modelLOG_Ext = new modelBFST_LOGExt();
											modelLOG_Ext.clear();
											
											tblBF_StatusTagging = new _JTableMain(modelLOG_Ext);
											scrollStatusTagging.setViewportView(tblBF_StatusTagging);
											scrollStatusTagging.setRowHeaderView(rowHeaderStatustagging);
											tblBF_StatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											
											lblActualDate.setText("LOG Ext");
										}
										
										if(selected_item == 5){
											modelLOG_TCTForTransfer = new modelBFST_TCTForTransfer();
											modelLOG_TCTForTransfer.clear();
											tblBF_StatusTagging = new _JTableMain(modelLOG_TCTForTransfer);
											scrollStatusTagging.setViewportView(tblBF_StatusTagging);
											scrollStatusTagging.setRowHeaderView(rowHeaderStatustagging);
											tblBF_StatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											
											lblActualDate.setText("TCT For transfer Date");
										}
										
										if(selected_item == 6){
											modelLOG_TCTBuyersName = new modelBFST_TCT_BuyersName();
											modelLOG_TCTBuyersName.clear();
											tblBF_StatusTagging = new _JTableMain(modelLOG_TCTBuyersName);
											scrollStatusTagging.setViewportView(tblBF_StatusTagging);
											scrollStatusTagging.setRowHeaderView(rowHeaderStatustagging);
											tblBF_StatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											
											lblActualDate.setText("TCT Date");
										}
										
										if(selected_item == 7){
											modelPost_Compliance = new modelBFST_PostCompliance();
											modelPost_Compliance.clear();
											tblBF_StatusTagging = new _JTableMain(modelPost_Compliance);
											scrollStatusTagging.setViewportView(tblBF_StatusTagging);
											scrollStatusTagging.setRowHeaderView(rowHeaderStatustagging);
											tblBF_StatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
											
											lblActualDate.setText("Post Compliance Date");
										}
										
										rowHeaderStatustagging = tblBF_StatusTagging.getRowHeader();
										rowHeaderStatustagging.setModel(new DefaultListModel());
										scrollStatusTagging.setRowHeaderView(rowHeaderStatustagging);
										scrollStatusTagging.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
									}
								});
							}
						}
					}
				}
				{
					pnlNorthEast = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthEast, BorderLayout.EAST);
					pnlNorthEast.setPreferredSize(new Dimension(200, 0));
					{
						btnGenerate = new JButton("Generate");
						pnlNorthEast.add(btnGenerate);
						btnGenerate.setActionCommand("Generate");
						btnGenerate.addActionListener(this);
					}
				}
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					JPanel pnlCenterTop = new JPanel(new GridLayout(1, 2, 5, 5));
					pnlCenter.add(pnlCenterTop, BorderLayout.NORTH);
					pnlCenterTop.setBorder(JTBorderFactory.createTitleBorder("Details"));
					{
						JPanel pnlActualDate = new JPanel(new BorderLayout(5, 5));
						pnlCenterTop.add(pnlActualDate);
						{
							lblActualDate = new JLabel("Date Filed", JLabel.TRAILING);
							pnlActualDate.add(lblActualDate, BorderLayout.WEST);
						}
						{
							JPanel pnlDate = new JPanel(new BorderLayout(3, 3));
							pnlActualDate.add(pnlDate, BorderLayout.CENTER);
							pnlDate.setPreferredSize(new Dimension(150, 0));
							{
								dteActual = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
								pnlDate.add(dteActual);
								dteActual.setPreferredSize(new Dimension(150, 0));
							}
						}
					}
					{
						JPanel pnlBank = new JPanel(new BorderLayout(5, 5));
						pnlCenterTop.add(pnlBank);
						{
							JPanel pnlBankLabel = new JPanel(new BorderLayout(5, 5));
							pnlBank.add(pnlBankLabel, BorderLayout.WEST);
							{
								lblBank = new JLabel("Bank Name");
								pnlBankLabel.add(lblBank);
							}
						}
						{
							JPanel pnlBankComponent = new JPanel(new BorderLayout(5, 5));
							pnlBank.add(pnlBankComponent, BorderLayout.CENTER);
							{
								lookupBank = new _JLookup(null, "Bank", 0);
								pnlBankComponent.add(lookupBank, BorderLayout.WEST);
								lookupBank.setPreferredSize(new Dimension(50, 0));
								lookupBank.setLookupSQL(_BankFinancingMonitoring.sqlBankName());
								lookupBank.setFilterName(true);
								lookupBank.addLookupListener(new LookupListener() {
									
									public void lookupPerformed(LookupEvent event) {
										Object [] data = ((_JLookup)event.getSource()).getDataSet();
										
										FncSystem.out("Display SQL for Bank Name", lookupBank.getLookupSQL());
										
										if (data != null){
											String bank_id = (String) data[0];
											String bank_name = (String) data[1];
											txtBank.setText(bank_name);
										}
									}
								});
							}
							{
								txtBank = new _JXTextField();
								pnlBankComponent.add(txtBank, BorderLayout.CENTER);
							}
						}
						
					}
				}
				{

					dteEffectivity = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
					pnlCenter.add(dteEffectivity);
					dteEffectivity.setDate(null);
					((JTextFieldDateEditor)dteEffectivity.getDateEditor()).setEditable(false);
					dteEffectivity.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					((JTextFieldDateEditor)dteEffectivity.getDateEditor()).getDocument().addDocumentListener(new DocumentListener() {
						public void insertUpdate(DocumentEvent evt) {
							SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
							if (modelLOG_Released.getValueAt(tblBF_StatusTagging.getSelectedRow(),0).equals(true)) {
								modelLOG_Released.setValueAt(""+sdf.format(dteEffectivity.getDate()),tblBF_StatusTagging.getSelectedRow(),7);
							}
						}
						public void changedUpdate(DocumentEvent e) {}
						public void removeUpdate(DocumentEvent e) {}
					});
				}
				{
					scrollStatusTagging = new JScrollPane();
					pnlCenter.add(scrollStatusTagging, BorderLayout.CENTER);
					{

						modelLOG_Filed = new modelBFST_LOGFiled();
						modelLOG_Returned = new modelBFST_LOGReturned();
						modelLOG_Released = new modelBFST_LOGReleased();
						modelLOG_Signed = new modelBFST_LOGSigned();
						modelLOG_TCTForTransfer = new modelBFST_TCTForTransfer();
						
						tblBF_StatusTagging = new _JTableMain(modelLOG_Filed);
						scrollStatusTagging.setViewportView(tblBF_StatusTagging);
						scrollStatusTagging.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						tblBF_StatusTagging.setSortable(false);
						tblBF_StatusTagging.hideColumns("Entity ID", "Proj. ID", "PBL ID", "Seq. No");
					}
					{
						rowHeaderStatustagging = tblBF_StatusTagging.getRowHeader();
						rowHeaderStatustagging.setModel(new DefaultListModel());
						scrollStatusTagging.setRowHeaderView(rowHeaderStatustagging);
						scrollStatusTagging.setCorner(JScrollPane.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
				}
				{
					JPanel pnlCenterBottom = new JPanel(new GridLayout(1, 3, 3, 3));
					pnlCenter.add(pnlCenterBottom, BorderLayout.SOUTH);
					pnlCenterBottom.setPreferredSize(new Dimension(0, 40));
					{
						btnPost = new JButton("Post");
						pnlCenterBottom.add(btnPost);
						btnPost.setActionCommand(btnPost.getText());
						btnPost.addActionListener(this);
					}
					{
						btnPreview = new JButton("Preview");
						pnlCenterBottom.add(btnPreview);
						btnPreview.setActionCommand(btnPreview.getText());
						btnPreview.addActionListener(this);
					}
					{
						btnExport = new JButton("Export");
						pnlCenterBottom.add(btnExport);
						btnExport.setActionCommand(btnExport.getText());
						btnExport.addActionListener(this);
					}
				}
			}
		}
		btnState(true, false, false, false);
	}//XXX END OF INIT GUI
	
	private void btnState(Boolean sGenerate, Boolean sPost, Boolean sPreview, Boolean sExport){
		btnGenerate.setEnabled(sGenerate);
		btnPost.setEnabled(sPost);
		btnPreview.setEnabled(sPreview);
		btnExport.setEnabled(sExport);
	}
	
	private void previewLOG_Filing(){
		ArrayList<String> listEntityID = new ArrayList<String>();
		ArrayList<String> listProjID = new ArrayList<String>();
		ArrayList<String> listPBL = new ArrayList<String>();
		ArrayList<String> listSeq = new ArrayList<String>();
		
		
	}
	
	public boolean toSave(){
		
		if(dteActual.getDate() == null){
			JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please input %s", lblActualDate.getText()), "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		if(cmbStatus.getSelectedIndex() == 0){
			if(modelLOG_Filed.getRowCount() == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please generate accounts", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			
			if(lookupBank.getValue() == null){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), String.format("Please select bank"), "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		
		if(cmbStatus.getSelectedIndex() == 1){
			if(modelLOG_Returned.getRowCount() == 0){
				JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Please generate accounts", "Save", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		
		return true;
	}

	public void actionPerformed(ActionEvent e) {//XXX Action
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Generate")){
			new Thread(new Runnable() {
				
				public void run() {
					FncGlobal.startProgress("Generating Qualified Accounts");
					if(cmbStatus.getSelectedIndex() == 0){
						_BankFinancingMonitoring.displayLOG_Filed(modelLOG_Filed, rowHeaderStatustagging, lookupProject.getValue(), lookupPhase.getValue());
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBF_StatusTagging.getRowCount())));
						tblBF_StatusTagging.packAll();
						btnState(true, true, false, true);
					}
					if(cmbStatus.getSelectedIndex() == 1){
						_BankFinancingMonitoring.displayLOG_Returned(modelLOG_Returned, rowHeaderStatustagging, lookupProject.getValue(), lookupPhase.getValue());
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBF_StatusTagging.getRowCount())));
						tblBF_StatusTagging.packAll();
						btnState(true, true, false, true);
					}
					if(cmbStatus.getSelectedIndex() == 2){
						_BankFinancingMonitoring.displayLOG_Released(modelLOG_Released, rowHeaderStatustagging, lookupCompany.getValue() ,lookupProject.getValue(), lookupPhase.getValue());
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBF_StatusTagging.getRowCount())));
						tblBF_StatusTagging.packAll();
						btnState(true, true, true, false);
					}
					if(cmbStatus.getSelectedIndex() == 3){
						_BankFinancingMonitoring.displayLOG_Signed(modelLOG_Signed, rowHeaderStatustagging, lookupProject.getValue(), lookupPhase.getValue());
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBF_StatusTagging.getRowCount())));
						tblBF_StatusTagging.packAll();
						btnState(true, true, true, false);
					}
					if(cmbStatus.getSelectedIndex() == 4){
						_BankFinancingMonitoring.displayLOG_Ext(modelLOG_Ext, rowHeaderStatustagging, lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue());
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBF_StatusTagging.getRowCount())));
						tblBF_StatusTagging.packAll();
						btnState(true, true, true, false);
					}
					if(cmbStatus.getSelectedIndex() == 5){
						_BankFinancingMonitoring.displayTCTforTransferBuyer(modelLOG_TCTForTransfer, rowHeaderStatustagging, lookupProject.getValue(), lookupPhase.getValue());
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBF_StatusTagging.getRowCount())));
						tblBF_StatusTagging.packAll();
						btnState(true, true, true, false);
					}
					
					if(cmbStatus.getSelectedIndex() == 6){
						_BankFinancingMonitoring.displayTCT_UnderBuyersName(modelLOG_TCTBuyersName, rowHeaderStatustagging, lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue());
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBF_StatusTagging.getRowCount())));
						tblBF_StatusTagging.packAll();
						btnState(true, true, true, false);
					}
					
					if(cmbStatus.getSelectedIndex() == 7){
						_BankFinancingMonitoring.displayPost_Compliance_Qualified(modelPost_Compliance, rowHeaderStatustagging, lookupCompany.getValue(), lookupProject.getValue(), lookupPhase.getValue());
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(Integer.toString(tblBF_StatusTagging.getRowCount())));
						tblBF_StatusTagging.packAll();
						btnState(true, true, true, false);
					}
					
					FncGlobal.stopProgress();
				}
			}).start();
		}

		if(actionCommand.equals("Post")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Tag selected accounts?", actionCommand, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					if(cmbStatus.getSelectedIndex() == 0){
						_BankFinancingMonitoring.saveLOG_FiledAccts(modelLOG_Filed, dteActual.getDate(), lookupBank.getValue());
						modelLOG_Filed.clear();
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
						rowHeaderStatustagging.setModel(new DefaultListModel());
					}
					if(cmbStatus.getSelectedIndex() == 1){
						_BankFinancingMonitoring.saveLOG_ReturnedAccts(modelLOG_Returned, dteActual.getDate());
						modelLOG_Returned.clear();
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
						rowHeaderStatustagging.setModel(new DefaultListModel());
					}
					if(cmbStatus.getSelectedIndex() == 2){
						_BankFinancingMonitoring.saveLOG_ReleasedAccts(modelLOG_Released, dteActual.getDate());
						modelLOG_Released.clear();
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
						rowHeaderStatustagging.setModel(new DefaultListModel());
					}
					if(cmbStatus.getSelectedIndex() == 3){
						_BankFinancingMonitoring.saveLOG_SignedAccts(modelLOG_Signed, dteActual.getDate());
						modelLOG_Signed.clear();
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
						rowHeaderStatustagging.setModel(new DefaultListModel());
					}
					if(cmbStatus.getSelectedIndex() == 4){
						_BankFinancingMonitoring.saveLOG_ExtAccts(modelLOG_Ext, dteActual.getDate());
						modelLOG_Ext.clear();
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
						rowHeaderStatustagging.setModel(new DefaultListModel());
					}
					if(cmbStatus.getSelectedIndex() == 5){
						_BankFinancingMonitoring.saveTCT_for_Transfer(modelLOG_TCTForTransfer, dteActual.getDate());
						modelLOG_TCTForTransfer.clear();
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
						rowHeaderStatustagging.setModel(new DefaultListModel());
					}
					
					if(cmbStatus.getSelectedIndex() == 6){
						_BankFinancingMonitoring.saveTCT_UnderBuyersName(modelLOG_TCTBuyersName, dteActual.getDate());
						modelLOG_TCTBuyersName.clear();
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
						rowHeaderStatustagging.setModel(new DefaultListModel());
					}
					
					if(cmbStatus.getSelectedIndex() == 7){
						_BankFinancingMonitoring.savePost_Compliance(modelPost_Compliance, dteActual.getDate());
						modelPost_Compliance.clear();
						scrollStatusTagging.setCorner(JScrollPane.LOWER_LEFT_CORNER, FncTables.getRowHeader_Footer(""));
						rowHeaderStatustagging.setModel(new DefaultListModel());
					}
					
					
					btnState(true, false, false, false);
					JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Accounts succesfully posted", "Save", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
		if(actionCommand.equals("Preview")){
			if(cmbStatus.getSelectedIndex() == 0){
				
			}
			
			if(cmbStatus.getSelectedIndex() == 1){
				
			}
			
			if(cmbStatus.getSelectedIndex() == 2){
				
			}
		}
		
		if(actionCommand.equals("Export")){
			
			if(cmbStatus.getSelectedIndex() == 0){
				ArrayList<String> listEntityID = new ArrayList<String>();
				ArrayList<String> listProjID = new ArrayList<String>();
				ArrayList<String> listPBL = new ArrayList<String>();
				ArrayList<String> listSeq = new ArrayList<String>();
				//ArrayList<Integer> listSeq = new ArrayList<Integer>();
				
				for(int x= 0; x<modelLOG_Filed.getRowCount(); x++){
					Boolean isSelected = (Boolean) modelLOG_Filed.getValueAt(x, 0);
					
					if(isSelected){
						String entity_id = (String) modelLOG_Filed.getValueAt(x, 6);
						String proj_id = (String) modelLOG_Filed.getValueAt(x, 7);
						String pbl_id = (String) modelLOG_Filed.getValueAt(x, 8);
						Integer seq_no = (Integer) modelLOG_Filed.getValueAt(x, 9);
						
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
				
				mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
				mapParameters.put("entity_id", entity_id);
				mapParameters.put("proj_id", proj_id);
				mapParameters.put("pbl_id", pbl_id);
				mapParameters.put("seq_no", seq_no);
				
				FncReport.generateReport("/Reports/rptBankFinance_Filed_Transmittal.jasper", "Bank Filed Accounts", mapParameters);
			}
			
			if(cmbStatus.getSelectedIndex() == 1){
				
			}
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(tblBF_StatusTagging)){
			int selected_row = tblBF_StatusTagging.convertRowIndexToModel(tblBF_StatusTagging.getSelectedRow());
			
			Boolean isSelected = (Boolean) modelLOG_Released.getValueAt(selected_row, 0);
			
			if(isSelected){
				//System.out.printf("Display value of selected column: %s%n", tblBF_StatusTagging.getSelectedColumn());
				if (tblBF_StatusTagging.getSelectedColumn() == 7) {
					if (e.getClickCount() == 2) {
						System.out.println("Dumaan dito sa Double Click");
						dteEffectivity.setBounds((int)pnlCenter.getMousePosition().getX(), (int)pnlCenter.getMousePosition().getY(), 0, 0);
						dteEffectivity.getCalendarButton().doClick();
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
