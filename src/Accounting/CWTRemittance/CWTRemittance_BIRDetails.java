package Accounting.CWTRemittance;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Database.pgSelect;
import Database.pgUpdate;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import Renderer.DateRenderer;
import components.JTBorderFactory;
import components._JTableMain;
import interfaces.GUIBuilder;
import interfaces.Interface_Report;
import tablemodel.model_cwt_bir;
import tablemodel.model_hdmf_schedule;
import tablemodel.model_hdmfinfo_loanfilingstatus;

public class CWTRemittance_BIRDetails extends JPanel implements Interface_Report {

	private static final long serialVersionUID = 8263818487032935122L;
	
	private JTextField txtRDO; 
	private JTextField txtBank;
	private JTextField txtBankBranch;
	private JTextField txtAccount;
	 
	private _JLookup txtBankID;
	private _JLookup txtBankBranchID;	

	private JButton btnSave; 
	
	private JScrollPane scrollBIR;
	public static JList rowBIR;
	private _JTableMain tblBIR;
	private static model_cwt_bir modelBIR;
	
	public String getRDO() {
		return txtRDO.getText(); 
	}
	
	public String getBankID() {
		return txtBankID.getValue();  
	}

	public String getBankBranchID() {
		return txtBankBranchID.getValue();  
	}
	
	public String getAccount() {
		return txtAccount.getText();   
	}
	
	public CWTRemittance_BIRDetails() {
		initGUI(); 
	}

	public CWTRemittance_BIRDetails(LayoutManager arg0) {
		super(arg0);
		initGUI(); 
	}

	public CWTRemittance_BIRDetails(boolean arg0) {
		super(arg0);
		initGUI(); 
	}

	public CWTRemittance_BIRDetails(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		initGUI(); 
	}

	@Override
	public void initGUI() {
		this.setSize(new Dimension(600, 400));
		this.setLayout(new BorderLayout(5, 5));
		
		JPanel panMain = new JPanel(new BorderLayout(5, 5)); 
		add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(10, 10, 10, 10));
		{
			{
				JPanel panCenter = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					initializeInputFields();
					{
						JPanel panDetails = new JPanel(new GridLayout(3, 1, 5, 5));
						panCenter.add(panDetails, BorderLayout.PAGE_START); 
						panDetails.setPreferredSize(new Dimension(0, 100));
						{
							panDetails.add(GUIBuilder.createLabelLookupText(200, "Bank", JLabel.LEADING, txtBankID, lookupMethods.getBank(), txtBank)); 
							panDetails.add(GUIBuilder.createLabelLookupText(200, "Bank Branch", JLabel.LEADING, txtBankBranchID, lookupMethods.getBankBranch(""), txtBankBranch)); 
							panDetails.add(GUIBuilder.createLabelText("Account Number", JLabel.LEADING, txtAccount));	
						}	
					}
					{
						JPanel panTables = new JPanel(new BorderLayout(5, 5)); 
						panCenter.add(panTables, BorderLayout.CENTER);
						{
							scrollBIR = new JScrollPane();
							panTables.add(scrollBIR, BorderLayout.CENTER);
							scrollBIR.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
							scrollBIR.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
							{
								{
									modelBIR = new model_cwt_bir();
									tblBIR = new _JTableMain(modelBIR);
									tblBIR.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
										
										@Override
										public void valueChanged(ListSelectionEvent e) {
											txtBankID.setValue(modelBIR.getValueAt(tblBIR.getSelectedRow(), 3).toString());
											txtBankBranchID.setValue(modelBIR.getValueAt(tblBIR.getSelectedRow(), 4).toString());
											txtBank.setText(FncGlobal.GetString("select bank_name from mf_bank where bank_id = '"+modelBIR.getValueAt(tblBIR.getSelectedRow(), 3).toString()+"'; "));
											txtBankBranch.setText(modelBIR.getValueAt(tblBIR.getSelectedRow(), 1).toString());
											
											try {
												txtAccount.setText(modelBIR.getValueAt(tblBIR.getSelectedRow(), 2).toString());												
											} catch (NullPointerException ex) {
												txtAccount.setText("");
											}
											
										}
									});
									
									rowBIR = tblBIR.getRowHeader();
									scrollBIR.setViewportView(tblBIR);
									tblBIR.getColumnModel().getColumn(0).setMaxWidth(150);
									tblBIR.getColumnModel().getColumn(1).setMinWidth(100);
									tblBIR.getColumnModel().getColumn(2).setMaxWidth(150);
									tblBIR.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
									tblBIR.setSortable(false);
									
									tblBIR.hideColumns("bank_id", "bank_branch_id");
								}
								{
									rowBIR = tblBIR.getRowHeader();
									rowBIR.setModel(new DefaultListModel());
									scrollBIR.setRowHeaderView(rowBIR);
									scrollBIR.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
								}	
							}
						}
					}

				}
			}
			{
				JPanel panEnd = new JPanel(new GridLayout(1, 3, 5, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 30));
				{
					
					final JCheckBox chkWithAcct = new JCheckBox("With Account"); 
					panEnd.add(chkWithAcct); 
					chkWithAcct.setHorizontalAlignment(JCheckBox.LEADING);
					chkWithAcct.addItemListener(new ItemListener() {
						
						@Override
						public void itemStateChanged(ItemEvent e) {
							display(!chkWithAcct.isSelected());
						}
					});
					
					panEnd.add(new JLabel(""));

					btnSave = new JButton("Save");
					panEnd.add(btnSave);
					btnSave.setEnabled(false);
					btnSave.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							save();
						}
					});
				}
			}
		}
		
		display(true);
		controlState(); 
	}

	@Override
	public void preview() {
		
	}

	@Override
	public void export() {
		
	}

	@Override
	public void generate() {
		
	}

	@Override
	public void cancel() {
		
	}

	@Override
	public void refresh() {
		
	}

	@Override
	public void initializeInputFields() {
		txtRDO = new JTextField(""); 
		txtBank = new JTextField("");
		txtBankBranch = new JTextField("");
		
		txtBankID = new _JLookup(""); 
		txtBankID.addLookupListener(new LookupListener() {

			@Override
			public void lookupPerformed(LookupEvent event) {
				Object[] data = ((_JLookup) event.getSource()).getDataSet();

				if (data != null) {
					txtBankID.setValue(data[0].toString());
					txtBank.setText(data[1].toString());
					txtBankBranchID.setLookupSQL(lookupMethods.getBankBranch(txtBankID.getValue()));
				}
			}
		});
		
		txtBankBranchID = new _JLookup("");
		txtBankBranchID.addLookupListener(new LookupListener() {

			@Override
			public void lookupPerformed(LookupEvent event) {
				Object[] data = ((_JLookup) event.getSource()).getDataSet();

				if (data != null) {
					txtBankBranchID.setValue(data[0].toString());
					txtBankBranch.setText(data[1].toString());
				}
				
				if (!txtBankID.getValue().toString().equals("") && !txtBankBranchID.getValue().toString().equals("")) {
					btnSave.setEnabled(true);
					
					System.out.println(FncGlobal.GetString("select acct_no from mf_bank_account_bir where bank_id = '"+txtBankID.getValue()+"' and bank_branch_id = '"+txtBankBranchID.getValue()+"'; "));
					
					txtAccount.setText(FncGlobal.GetString("select acct_no from mf_bank_account_bir where bank_id = '"+txtBankID.getValue()+"' and bank_branch_id = '"+txtBankBranchID.getValue()+"'; "));
				}
			}
		});	
		
		txtAccount = new JTextField(""); 
	}

	@Override
	public void defaultValues() {
		
	}

	@Override
	public void buttonState() {
		
	}

	@Override
	public void controlState() {
		txtBankID.setEnabled(true);
		txtBankBranchID.setEnabled(true);
		txtRDO.setEnabled(true);
		txtAccount.setEnabled(true);
		txtAccount.setEditable(true);
		btnSave.setEnabled(true);
	}

	@Override
	public void createButton() {
		
	}

	@Override
	public void disableControls() {
		
	}

	private void save() {
		pgUpdate dbExec = new pgUpdate(); 
		if (FncGlobal.GetBoolean("select exists(select * from mf_bank_account_bir where bank_id = '"+txtBankID.getValue()+"' and bank_branch_id = '"+txtBankBranchID.getText()+"' and status_id = 'A'); ")) {
			dbExec.executeUpdate("update mf_bank_account_bir set acct_no = '"+txtAccount.getText()+"' where bank_id = '"+txtBankID.getValue()+"' and bank_branch_id = '"+txtBankBranchID.getText()+"' and status_id = 'A'; ", false);
		} else {
			dbExec.executeUpdate("insert into mf_bank_account_bir (bank_id, bank_branch_id, acct_no, created_by, date_created, status_id)\n" + 
					"values ('"+txtBankID.getValue()+"', '"+txtBankBranchID.getValue()+"', '"+txtAccount.getText()+"', '"+UserInfo.EmployeeCode+"', now(), 'A'); ", false);
		}
		dbExec.commit();
	}
	
	private static void display(boolean blnDo) {
		FncTables.clearTable(modelBIR);
		DefaultListModel listModel = new DefaultListModel();
		rowBIR.setModel(listModel); 
	
		pgSelect db = new pgSelect();
		db.select("select * \n" + 
				"from view_cwt_bir_bank_details \n" + 
				"where (case when "+blnDo+" then true else acct_no is not null end); ");
		if (db.isNotNull()){
			for (int x = 0; x < db.getRowCount(); x++) {
				modelBIR.addRow(db.getResult()[x]);
				listModel.addElement(modelBIR.getRowCount());
			}
		} else{
		};
	}
}
