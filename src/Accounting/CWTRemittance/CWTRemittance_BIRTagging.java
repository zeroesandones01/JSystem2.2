package Accounting.CWTRemittance;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;

import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncTables;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import Lookup.lookupMethods;
import components._JTableMain;
import interfaces.GUIBuilder;
import interfaces.Interface_Report;
import tablemodel.model_cwt_bir;

public class CWTRemittance_BIRTagging extends JPanel implements Interface_Report {

	private static final long serialVersionUID = -371199385100290859L;
	
	private static JTextField txtRDO; 
	private JTextField txtBank;
	private JTextField txtBankBranch;
	private static JTextField txtOR;
	 
	private static _JLookup txtBankID;
	private static _JLookup txtBankBranchID;
	
	private JButton btnSave; 
	private JButton btnSkip; 
	
	public static String strRDO;
	public static String strBank;
	public static String strBranch;
	private static _JDateChooser dteRefDate;
	
	public static _JDateChooser getReleasedDate() {
		return dteRefDate;
	}
	
	public static String getRDO() {
		return txtRDO.getText();  
	}
	
	public static String getORNo() {
		return txtOR.getText();  
	}
	
	public void setORNo(String or) {
		txtOR.setText(or);
	}
	
	public static String getBankID() {
		return txtBankID.getValue(); 
	}

	public static String getBankBranchID() {
		return txtBankBranchID.getValue(); 
	}
	
	public void setRDO(String rdo) {
		txtRDO.setText(rdo);
	}
	
	public void setBankID(String bank) {
		txtBankID.setValue(bank);
	}

	public void setBankBranchID(String branch) {
		txtBankBranchID.setValue(branch);
	}
	
	public CWTRemittance_BIRTagging() {
		initGUI(); 
	}

	public CWTRemittance_BIRTagging(LayoutManager arg0) {
		super(arg0);
		initGUI(); 
	}

	public CWTRemittance_BIRTagging(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI(); 
	}

	public CWTRemittance_BIRTagging(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI(); 
	}
	
	@Override
	public void initGUI() {
		this.setPreferredSize(new Dimension(500, 150));
		this.setLayout(new BorderLayout(5, 5));
		
		JPanel panMain = new JPanel(new BorderLayout(5, 5)); 
		add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(10, 10, 10, 10));
		{
			{
				JPanel panCenter = new JPanel(new GridLayout(4, 1, 10, 10)); 
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					initializeInputFields();
					{
						
						String strBank = "SELECT trim(bank_id) as \"ID\", trim(bank_name) as \"Name\", trim(bank_alias) as \"Alias\" \n" +
								"FROM mf_bank a \n" +
								"WHERE status_id = 'A' \n" +
								"and exists(select * from view_cwt_bir_bank_details x where x.acct_no is not null and x.bank_id = a.bank_id) \n" +
								"ORDER BY bank_name;";
						
						String strBranch = "select trim(bank_branch_id) as \"ID\", trim(bank_branch_location) as \"Name\" \n" +
								"from mf_bank_branch a \n" +
								"where (bank_id = '' or '' = '') and status_id = 'A' \n" +
								"and exists(select * from view_cwt_bir_bank_details x where x.acct_no is not null and x.bank_id = a.bank_id) \n" +
								"order by bank_branch_location;";
						
						panCenter.add(GUIBuilder.createLabelText(97, "RDO", JLabel.LEADING, txtRDO));
						panCenter.add(GUIBuilder.createLabelLookupText(200, "Bank", JLabel.LEADING, txtBankID, strBank, txtBank)); 
						panCenter.add(GUIBuilder.createLabelLookupText(200, "Bank Branch", JLabel.LEADING, txtBankBranchID, strBranch, txtBankBranch));

						
						JXPanel panDate = new JXPanel(new BorderLayout(5, 5));
						//panDate.setPreferredSize(new Dimension(100, 30));
						{
							dteRefDate = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
							JLabel lblRelDate = new JLabel("Released Date");
							lblRelDate.setPreferredSize(new Dimension(97, 0));
							panDate.add(lblRelDate, BorderLayout.WEST);
							panDate.add(dteRefDate, BorderLayout.CENTER);
							//String msg = "Name: '"+clientName+"'\nLot:'"+clientLot+"'\n";
							dteRefDate.getCalendarButton().setVisible(true);
							dteRefDate.setDate(FncGlobal.dateFormat(FncGlobal.getDateSQL()));
							dteRefDate.setPreferredSize(new Dimension(0, 30));
						}
						panCenter.add(panDate);
					}
				}
			}
		}
		
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
		txtOR = new JTextField("");
		
		txtBankID = new _JLookup(""); 
		txtBankID.addLookupListener(new LookupListener() {

			@Override
			public void lookupPerformed(LookupEvent event) {
				Object[] data = ((_JLookup) event.getSource()).getDataSet();

				if (data != null) {
					txtBankID.setValue(data[0].toString());
					txtBank.setText(data[1].toString());
					
					String strBranch = "select trim(bank_branch_id) as \"ID\", trim(bank_branch_location) as \"Name\" \n" +
							"from mf_bank_branch a \n" +
							"where (bank_id = '"+txtBankID.getValue()+"' or '"+txtBankID.getValue()+"' = '') and status_id = 'A' \n" +
							"and exists(select * from view_cwt_bir_bank_details x where x.acct_no is not null and x.bank_id = a.bank_id and x.bank_branch_id = a.bank_branch_id) \n" +
							"order by bank_branch_location;";
					
					txtBankBranchID.setLookupSQL(strBranch);
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
			}
		});			
	}

	@Override
	public void defaultValues() {
		
	}

	@Override
	public void buttonState() {
		
	}

	@Override
	public void controlState() {
		
	}

	@Override
	public void createButton() {
		
	}

	@Override
	public void disableControls() {
		
	}

}
