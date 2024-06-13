package Utilities;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;

import Database.pgSelect;
import Database.pgUpdate;
import tablemodel.modelComm_Scheme_condition;
import tablemodel.modelComm_Scheme_function;
import tablemodel.modelComm_scheme_main;
import FormattedTextField._JXFormattedTextField;
import Functions.FncGlobal;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JScrollPaneMain;
import components._JScrollPaneTotal;
import components._JTableMain;
import components._JTableTotal;

public class Comm_Scheme extends _JInternalFrame implements _GUI, ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061284418918863916L;
	static String title = "Commission Scheme";
	static Dimension SIZE = new Dimension(1000, 600);

	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlSchemeList;
	private JPanel pnlTable;
	private JPanel pnlCondition;
	private JPanel pnlDesc;
	private JPanel pnlCondition_sub;
	private JPanel pnlSchemeList_tbl;
	private JPanel pnlTblBC;
	private JPanel pnlSub;	
	private JPanel pnlCommScheme;
	private JPanel pnlCommScheme_a;
	private JPanel pnlCommScheme_a1;
	private JPanel pnlCommRate;
	private JPanel pnlCommScheme_c;
	private JPanel pnlNorth_a;
	private JPanel pnlNorth_b;
	private JPanel pnlNorth_c;
	private JPanel pnlCondition_popup;
	private JPanel pnlCondition_popup_a;
	private JPanel pnlCondition_popup_a1;
	private JPanel pnlCondition_popup_a2;
	private JPanel pnlCondition_popup_c;	
	private JPanel pnlFunction_popup;
	private JPanel pnlFunction_popup_a;
	private JPanel pnlFunction_popup_a1;
	private JPanel pnlFunction_popup_a2;	
	private JPanel pnlFunction_popup_c;

	private JLabel lblAgentType;
	private JLabel lblBuyerType;	
	private JLabel labelTerms;
	private JLabel lblStatus;
	private JLabel lblSchemeType;
	private JLabel lblAgentType2;
	private JLabel lblSchemeID;
	private JLabel lblSchemeName;
	private JLabel lblConditionID;
	private JLabel lblConditionName;
	private JLabel lblCommType;
	private JLabel lblCommAmount;
	private JLabel lblCommPerc;
	private JLabel labelOverrideAmt;
	private JLabel lblOverridePerc;
	private JLabel lblDaysFromOR;
	private JLabel lblFunctionID;
	private JLabel lblFunctionName;
	private JLabel lblFunctionDB;
	private JLabel lblPmtStage;
	private JLabel lblByrStat;
	private JLabel labelPmtParticular;
	private JLabel lblFuncStatus;
	private JLabel lblCondStatus;

	private _JScrollPaneMain scrollSchemeList;	
	private _JScrollPaneMain scrollCondition;	
	private _JScrollPaneMain scrollFunction;
	private JScrollPane scpSchemeName;	
	private JScrollPane scpFunctionName;
	private JScrollPane scpConditionName;	
	private static _JScrollPaneTotal scrollSchemeList_total;
	private static _JScrollPaneTotal scrollConditionList_total;
	private _JScrollPaneTotal scrollFunction_total;

	private static modelComm_scheme_main modelSchemeList;
	private static modelComm_scheme_main modelSchemeList_total;
	private static modelComm_Scheme_condition modelCondition;
	private static modelComm_Scheme_condition modelConditionList_total;
	private static modelComm_Scheme_function modelFunction;
	private static modelComm_Scheme_function modelFunction_total;
	
	private static _JTableMain tblSchemeList;
	private static _JTableMain tblCondition;
	private static _JTableMain tblFunction;
	private static _JTableTotal tblConditionList_total;
	private static _JTableTotal tblSchemeList_total;
	private _JTableTotal tblFunction_total;

	static _JLookup lookupCompany;	
	private _JLookup lookupBuyerType;
	private _JLookup lookupPmtStage;
	private _JLookup lookupBuyerStatus;
	private _JLookup lookupPmtParticular;
	private _JLookup lookupFuncID;	
	
	private static JList rowHeaderSchemeList;
	private static JList rowHeaderCondition;
	private JList rowHeaderFunction;

	private JButton btnSave;
	private JButton btnCondSave;
	private JButton btnFuncSave;
	
	private JComboBox cmbStatus;
	private JComboBox cmbAgentType;
	private JComboBox cmbSchemeType;
	private JComboBox cmbAgentType2;
	private JComboBox cmbCommType;
	private JComboBox cmbCondStatus;
	private JComboBox cmbFuncStatus;	

	private JXTextField txtSchemeID;
	private JXTextField txtTerms;
	private JXTextField txtConditionID;
	private JXTextField txtDaysFrOR;	
	private JXTextField txtFunctionDB;	
	private _JXFormattedTextField txtCommAmt;
	private _JXFormattedTextField txtCommPerc;
	private _JXFormattedTextField txtOverrideAmt;
	private _JXFormattedTextField txtOverridePerc;	
	
	private JTextArea txtConditionDesc;		
	private JTextArea txtFunctionDesc;		
	private static JTextArea txtDescription;
	
	private JPopupMenu menu2;
	private JPopupMenu menu;
	private JPopupMenu menu3;
	
	private JMenuItem mniAddCommSch;
	private JMenuItem mniEditCommSch;
	private JMenuItem mniActCommSch;
	private JMenuItem mniInactCommSch;
	private JMenuItem mniAddCondition;
	private JMenuItem mniEditCondition;		
	private JMenuItem mniRemoveFunction;
	private JMenuItem mniActivateFunction;
	private JMenuItem mniAddNewFunction;
	private JMenuItem mniAddfunction;	
	
	
	protected static Font dialog11Bold = new Font("DIALOG", Font.BOLD, 11);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	static NumberFormat nf = new DecimalFormat("###,###,###,##0.00"); 	
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private String to_do = "addnewcommscheme";
	private JMenuItem mniRemoveCondition;
	private JMenuItem mniActivateCondition;
	private JCheckBox chkCanceldScheme;	

	private static String scheme_id = "";	
	private static String agent_type = "001";	
	private static String include_inactive = "A" ;	

	public Comm_Scheme() {
		super(title, true, true, true, true);
		initGUI();
	}

	public Comm_Scheme(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Comm_Scheme(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	/* (non-Javadoc)
	 * @see interfaces._GUI#initGUI()
	 */
	@Override
	public void initGUI() {
		this.setLayout(new BorderLayout(5,5));
		this.setSize(SIZE);
		this.setPreferredSize(new java.awt.Dimension(935, 604));
		this.setBounds(0, 0, 935, 604);

		{
			menu2 = new JPopupMenu("Popup");
			mniAddCommSch = new JMenuItem("Add New Commission Scheme");		
			mniEditCommSch = new JMenuItem("Edit Commission Scheme");	
			mniActCommSch = new JMenuItem("Activate Commission Scheme");
			mniInactCommSch = new JMenuItem("Inactivate Commission Scheme");
			mniAddCondition = new JMenuItem("Add New Schedule");		
			menu2.add(mniAddCommSch);
			menu2.add(mniEditCommSch);
			JSeparator sp2 = new JSeparator();
			menu2.add(sp2);	
			menu2.add(mniActCommSch);
			menu2.add(mniInactCommSch);
			JSeparator sp1 = new JSeparator();
			menu2.add(sp1);	
			menu2.add(mniAddCondition);

			mniAddCommSch.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					to_do = "addnewcommscheme";
					btnSave.setText("Save New Comm. Scheme");
					txtSchemeID.setText(sql_getNewCommSchNo());

					int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlCommScheme, "Add New Commission Scheme",
							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);			

					if ( scanOption == JOptionPane.CLOSED_OPTION ) {
						
					} // CLOSED_OPTION
				}
			});

			mniEditCommSch.addActionListener(new ActionListener(){				
				public void	actionPerformed(ActionEvent evt){

					to_do = "editcommscheme";		
					int row = tblSchemeList.getSelectedRow();	
					scheme_id = tblSchemeList.getValueAt(row,0).toString().trim();	
					String scheme_name = tblSchemeList.getValueAt(row,1).toString().trim();	
					String buyer_type = tblSchemeList.getValueAt(row,3).toString().trim();	
					String agent_type = tblSchemeList.getValueAt(row,2).toString().trim();	
					String terms = tblSchemeList.getValueAt(row,4).toString().trim();	
					String scheme_type = tblSchemeList.getValueAt(row,5).toString().trim();	
					String status = tblSchemeList.getValueAt(row,6).toString().trim();	

					txtSchemeID.setText(scheme_id);
					txtDescription.setText(scheme_name);
					lookupBuyerType.setText(buyer_type);
					if(agent_type.equals("In-House")){cmbAgentType2.setSelectedIndex(0);} else {cmbAgentType2.setSelectedIndex(1);}
					if(scheme_type.equals("R")){cmbSchemeType.setSelectedIndex(0);} else {cmbSchemeType.setSelectedIndex(1);}
					txtTerms.setText(terms);
					if(status.equals("A")){cmbStatus.setSelectedIndex(0);} else {cmbStatus.setSelectedIndex(1);}
					btnSave.setText("Edit Comm. Scheme");

					int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlCommScheme, "Edit Commission Scheme",
							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);						
					if ( scanOption == JOptionPane.CLOSED_OPTION ) {						
					} // CLOSED_OPTION
				}
			});

			mniActCommSch.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					int row = tblSchemeList.getSelectedRow();	
					scheme_id = tblSchemeList.getValueAt(row,0).toString().trim();	
					activateCommScheme();
				}
			});

			mniInactCommSch.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					int row = tblSchemeList.getSelectedRow();	
					scheme_id = tblSchemeList.getValueAt(row,0).toString().trim();	
					inactivateCommScheme();
				}
			});

			mniAddCondition.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					to_do = "addnewcondition";
					btnCondSave.setText("Save New Schedule");
					txtConditionID.setText(sql_getNewCondNo());
					int row = tblSchemeList.getSelectedRow();	
					scheme_id = tblSchemeList.getValueAt(row,0).toString().trim();	

					int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlCondition_popup, "Add New Schedule",
							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);			

					if ( scanOption == JOptionPane.CLOSED_OPTION ) {
					} // CLOSED_OPTION
				}
			});

		}
		{
			menu = new JPopupMenu("Popup");			
			mniEditCondition = new JMenuItem("Edit Schedule");	
			mniAddfunction = new JMenuItem("Add Qualifier");
			mniRemoveCondition = new JMenuItem("Remove Schedule");
			mniActivateCondition = new JMenuItem("Activate Schedule");

			menu.add(mniEditCondition);		
			menu.add(mniAddfunction);		
			JSeparator sp3 = new JSeparator();
			menu.add(sp3);	
			menu.add(mniRemoveCondition);		
			menu.add(mniActivateCondition);		

			mniEditCondition.addActionListener(new ActionListener(){				
				public void	actionPerformed(ActionEvent evt){

					to_do = "editcondition";		
					int row = tblSchemeList.getSelectedRow();	
					scheme_id = tblSchemeList.getValueAt(row,0).toString().trim();	

					int row2 = tblCondition.getSelectedRow();	
					String cond_id = tblCondition.getValueAt(row2,0).toString().trim();	
					String cond_name = tblCondition.getValueAt(row2,1).toString().trim();	
					String comm_type = tblCondition.getValueAt(row2,2).toString().trim();	
					String days_frOR = tblCondition.getValueAt(row2,7).toString().trim();	
					String status    = tblCondition.getValueAt(row2,8).toString().trim();

					if (tblCondition.getValueAt(row2,3)==null||tblCondition.getValueAt(row2,3).equals("")) {txtCommAmt.setText("0.00");} 
					else {txtCommAmt.setText(nf.format(Double.parseDouble(tblCondition.getValueAt(row2,3).toString())));}
					if (tblCondition.getValueAt(row2,4)==null||tblCondition.getValueAt(row2,4).equals("")) {txtCommPerc.setText("0.00");} 
					else {txtCommPerc.setText(nf.format(Double.parseDouble(tblCondition.getValueAt(row2,4).toString())));}
					if (tblCondition.getValueAt(row2,5)==null||tblCondition.getValueAt(row2,5).equals("")) {txtOverrideAmt.setText("0.00");} 
					else {txtOverrideAmt.setText(nf.format(Double.parseDouble(tblCondition.getValueAt(row2,5).toString())));}
					if (tblCondition.getValueAt(row2,6)==null||tblCondition.getValueAt(row2,6).equals("")) {txtOverridePerc.setText("0.00");} 
					else {txtOverridePerc.setText(nf.format(Double.parseDouble(tblCondition.getValueAt(row2,6).toString())));}

					txtConditionID.setText(cond_id);
					txtConditionDesc.setText(cond_name);
					txtDaysFrOR.setText(days_frOR);

					if(comm_type.equals("1")) {cmbCommType.setSelectedIndex(0);}
					else if(comm_type.equals("2")) {cmbCommType.setSelectedIndex(1);}
					else if(comm_type.equals("3")) {cmbCommType.setSelectedIndex(2);}
					else if(comm_type.equals("4")) {cmbCommType.setSelectedIndex(3);}
					else if(comm_type.equals("FC")) {cmbCommType.setSelectedIndex(4);}
					else {cmbCommType.setSelectedIndex(0);}

					if(status.equals("A")){cmbCondStatus.setSelectedIndex(0);} else {cmbCondStatus.setSelectedIndex(1);}
					btnCondSave.setText("Edit Schedule");

					int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlCondition_popup, "Edit Schedule",
							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);						
					if ( scanOption == JOptionPane.CLOSED_OPTION ) {
						
					} // CLOSED_OPTION
				}
			});

			mniAddfunction.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){

					to_do = "addfunction";
					btnFuncSave.setText("Save Qualifier");
					int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlFunction_popup, "Add Qualifier",
							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);			

					if ( scanOption == JOptionPane.CLOSED_OPTION ) {

					} // CLOSED_OPTION
				}
			});
			mniRemoveCondition.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					updateConditionStatus("I");
				}
			});
			mniActivateCondition.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					updateConditionStatus("A");
				}
			});

		}
		{
			menu3 = new JPopupMenu("Popup");			
			mniRemoveFunction = new JMenuItem("Remove Qualifier");	
			mniActivateFunction = new JMenuItem("Activate Qualifier");	
			mniAddNewFunction = new JMenuItem("Add New Qualifier");	

			menu3.add(mniRemoveFunction);
			menu3.add(mniActivateFunction);
			JSeparator sp = new JSeparator();
			menu3.add(sp);	
			menu3.add(mniAddNewFunction);

			mniRemoveFunction.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					updateFunctionStatus("I");
				}
			});
			
			mniActivateFunction.addActionListener(new ActionListener(){
				public void	actionPerformed(ActionEvent evt){
					updateFunctionStatus("A");
				}
			});
			
			mniAddNewFunction.addActionListener(new ActionListener(){				
				public void	actionPerformed(ActionEvent evt){
					
					lookupFuncID.setValue(sql_getNewFuncNo());
					btnFuncSave.setText("Save New Qualifier");
					to_do = "addnewfunction";
					
					int scanOption = JOptionPane.showOptionDialog(getContentPane(), pnlFunction_popup, "Add New Qualifier",
							JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);						
					if ( scanOption == JOptionPane.CLOSED_OPTION ) {
						
					} // CLOSED_OPTION
				}
			});

		}


		pnlMain = new JPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(5, 5));
		pnlMain.setPreferredSize(new java.awt.Dimension(1134, 645));

		{
			pnlNorth = new JPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			pnlNorth.setLayout(new BorderLayout(5, 5));
			pnlNorth.setBorder(lineBorder);
			pnlNorth.setPreferredSize(new java.awt.Dimension(923, 40));		

			{
				pnlNorth_a = new JPanel(new GridLayout(1, 1, 5, 5));
				pnlNorth.add(pnlNorth_a, BorderLayout.WEST);
				pnlNorth_a.setPreferredSize(new java.awt.Dimension(90, 38));
				pnlNorth_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,5));
				{
					lblAgentType = new JLabel("Selling Type :", JLabel.TRAILING);
					pnlNorth_a.add(lblAgentType);
					lblAgentType.setEnabled(true);	
					lblAgentType.setPreferredSize(new java.awt.Dimension(92, 25));
					lblAgentType.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
				}					
			}
			{
				pnlNorth_b = new JPanel(new GridLayout(1, 1, 0, 5));
				pnlNorth.add(pnlNorth_b, BorderLayout.CENTER);
				pnlNorth_b.setPreferredSize(new java.awt.Dimension(159, 38));
				pnlNorth_b.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));


				{
					String status[] = {"In-House Broker","External Broker"};					
					cmbAgentType = new JComboBox(status);
					pnlNorth_b.add(cmbAgentType);
					cmbAgentType.setSelectedItem(0);
					cmbAgentType.setBounds(537, 15, 160, 21);	
					cmbAgentType.setEnabled(true);
					cmbAgentType.setSelectedIndex(0);	
					cmbAgentType.setPreferredSize(new java.awt.Dimension(89, 26));
					cmbAgentType.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent evt) 
						{									
							generateSchemeList();
							refresh_tables_function();
							refresh_tables_condition();
						}
					});		
				}

			}
			{
				pnlNorth_c = new JPanel(new GridLayout(1, 1, 0, 5));
				pnlNorth.add(pnlNorth_c, BorderLayout.EAST);
				pnlNorth_c.setPreferredSize(new java.awt.Dimension(662, 38));
				pnlNorth_c.setBorder(BorderFactory.createEmptyBorder(5, 0, 5,5));
				
				{

					chkCanceldScheme = new JCheckBox("Show Inactive Commission Scheme(s)");
					pnlNorth_c.add(chkCanceldScheme);
					chkCanceldScheme.setEnabled(true);
					chkCanceldScheme.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent arg0) {
							boolean selected = arg0.getStateChange() == ItemEvent.SELECTED;

							if (selected) {
								include_inactive = "I";
								generateSchemeList();
								refresh_tables_condition();
								refresh_tables_function();
								
							} else {
								include_inactive = "A";
								generateSchemeList();
								refresh_tables_condition();
								refresh_tables_function();
								
							}
						}
					});

				}	

			}
		}
		{
			pnlCommScheme = new JPanel();
			pnlCommScheme.setLayout(new BorderLayout(5, 5));
			pnlCommScheme.setBorder(lineBorder);		
			pnlCommScheme.setPreferredSize(new java.awt.Dimension(382, 300));		

			{		
				pnlCommScheme_a = new JPanel(new BorderLayout(5, 5));
				pnlCommScheme.add(pnlCommScheme_a, BorderLayout.NORTH);				
				pnlCommScheme_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlCommScheme_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlCommScheme_a.setPreferredSize(new java.awt.Dimension(380, 260));		

				{
					pnlCommScheme_a1 = new JPanel(new GridLayout(7, 1, 5, 5));
					pnlCommScheme_a.add(pnlCommScheme_a1, BorderLayout.WEST);				
					pnlCommScheme_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlCommScheme_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlCommScheme_a1.setPreferredSize(new java.awt.Dimension(107, 145));		


					{
						lblSchemeID = new JLabel("Scheme ID", JLabel.TRAILING);
						pnlCommScheme_a1.add(lblSchemeID);
						lblSchemeID.setEnabled(true);	
						lblSchemeID.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblSchemeName = new JLabel("Scheme Name", JLabel.TRAILING);
						pnlCommScheme_a1.add(lblSchemeName);
						lblSchemeName.setEnabled(true);	
						lblSchemeName.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblBuyerType = new JLabel("Buyer Type", JLabel.TRAILING);
						pnlCommScheme_a1.add(lblBuyerType);
						lblBuyerType.setEnabled(true);	
						lblBuyerType.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblAgentType2 = new JLabel("Agent Type", JLabel.TRAILING);
						pnlCommScheme_a1.add(lblAgentType2);
						lblAgentType2.setEnabled(true);	
						lblAgentType2.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblSchemeType = new JLabel("Scheme Type", JLabel.TRAILING);
						pnlCommScheme_a1.add(lblSchemeType);
						lblSchemeType.setEnabled(true);	
						lblSchemeType.setPreferredSize(new java.awt.Dimension(136, 24));
					}					
					{
						labelTerms = new JLabel("Terms", JLabel.TRAILING);
						pnlCommScheme_a1.add(labelTerms);
						labelTerms.setEnabled(true);	
						labelTerms.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblStatus = new JLabel("Status", JLabel.TRAILING);
						pnlCommScheme_a1.add(lblStatus);
						lblStatus.setEnabled(true);	
						lblStatus.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
				}
				{
					pnlCommRate = new JPanel(new GridLayout(7, 1, 5, 5));
					pnlCommScheme_a.add(pnlCommRate, BorderLayout.CENTER);				
					pnlCommRate.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlCommRate.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlCommRate.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						txtSchemeID = new JXTextField("");
						pnlCommRate.add(txtSchemeID);
						txtSchemeID.setEnabled(true);	
						txtSchemeID.setEditable(false);
						txtSchemeID.setBounds(120, 25, 300, 22);	
						txtSchemeID.setHorizontalAlignment(JTextField.CENTER);
						txtSchemeID.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));						
					}	
					{
						scpSchemeName = new JScrollPane();
						pnlCommRate.add(scpSchemeName);
						scpSchemeName.setBounds(82, 7, 309, 61);
						scpSchemeName.setOpaque(true);
						scpSchemeName.setPreferredSize(new java.awt.Dimension(375, 159));

						{
							txtDescription = new JTextArea();
							scpSchemeName.add(txtDescription);
							scpSchemeName.setViewportView(txtDescription);
							txtDescription.setBounds(77, 3, 250, 81);
							txtDescription.setLineWrap(true);
							txtDescription.setPreferredSize(new java.awt.Dimension(366, 133));
							txtDescription.setEditable(true);
							txtDescription.setEnabled(true);
							txtDescription.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent e) {	

								}});	
						}	
					}
					{
						lookupBuyerType = new _JLookup(null, "Buyer Type", 2, 2);
						pnlCommRate.add(lookupBuyerType);
						lookupBuyerType.setBounds(20, 27, 20, 25);
						lookupBuyerType.setReturnColumn(0);
						lookupBuyerType.setEnabled(true);	
						lookupBuyerType.setLookupSQL(getBuyertype());
						lookupBuyerType.setPreferredSize(new java.awt.Dimension(157, 22));
						lookupBuyerType.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){

								}
							}
						});	
					}		
					{
						String type[] = {"001 - In-House Broker","002 - External Broker"};					
						cmbAgentType2 = new JComboBox(type);
						pnlCommRate.add(cmbAgentType2);
						cmbAgentType2.setBounds(537, 15, 160, 21);	
						cmbAgentType2.setEnabled(true);
						cmbAgentType2.setSelectedIndex(0);	
						cmbAgentType2.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbAgentType2.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{

							}
						});		
					}		
					{
						String type[] = {"Regular","Special"};					
						cmbSchemeType = new JComboBox(type);
						pnlCommRate.add(cmbSchemeType);
						cmbSchemeType.setBounds(537, 15, 160, 21);	
						cmbSchemeType.setEnabled(true);
						cmbSchemeType.setSelectedIndex(0);	
						cmbSchemeType.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbSchemeType.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{

							}
						});		
					}
					{
						txtTerms = new JXTextField("");
						pnlCommRate.add(txtTerms);
						txtTerms.setEnabled(true);	
						txtTerms.setEditable(true);
						txtTerms.setBounds(120, 25, 300, 22);	
						txtTerms.setHorizontalAlignment(JTextField.CENTER);
						txtTerms.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}	
					{
						String status[] = {"Active","Inactive"};					
						cmbStatus = new JComboBox(status);
						pnlCommRate.add(cmbStatus);
						cmbStatus.setBounds(537, 15, 160, 21);	
						cmbStatus.setEnabled(true);
						cmbStatus.setSelectedIndex(0);	
						cmbStatus.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbStatus.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{

							}
						});		
					}
				}

				pnlCommScheme_c = new JPanel(new BorderLayout(5, 5));
				pnlCommScheme.add(pnlCommScheme_c, BorderLayout.CENTER);				
				pnlCommScheme_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlCommScheme_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlCommScheme_c.setPreferredSize(new java.awt.Dimension(500, 40));	

				{
					btnSave = new JButton("Save New Comm. Scheme");
					pnlCommScheme_c.add(btnSave);
					btnSave.setActionCommand("SaveNewCommScheme");
					btnSave.addActionListener(this);
					btnSave.setEnabled(true);
					btnSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {							
							saveCommScheme();
						}
					});
				}

			}
		}
		{
			pnlCondition_popup = new JPanel();
			pnlCondition_popup.setLayout(new BorderLayout(5, 5));
			pnlCondition_popup.setBorder(lineBorder);		
			pnlCondition_popup.setPreferredSize(new java.awt.Dimension(382, 360));		

			{		
				pnlCondition_popup_a = new JPanel(new BorderLayout(5, 5));
				pnlCondition_popup.add(pnlCondition_popup_a, BorderLayout.NORTH);				
				pnlCondition_popup_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlCondition_popup_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlCondition_popup_a.setPreferredSize(new java.awt.Dimension(380, 320));		

				{
					pnlCondition_popup_a1 = new JPanel(new GridLayout(9, 1, 5, 5));
					pnlCondition_popup_a.add(pnlCondition_popup_a1, BorderLayout.WEST);				
					pnlCondition_popup_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlCondition_popup_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlCondition_popup_a1.setPreferredSize(new java.awt.Dimension(107, 145));		


					{
						lblConditionID = new JLabel("Condition ID", JLabel.TRAILING);
						pnlCondition_popup_a1.add(lblConditionID);
						lblConditionID.setEnabled(true);	
						lblConditionID.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblConditionName = new JLabel("Condition Name", JLabel.TRAILING);
						pnlCondition_popup_a1.add(lblConditionName);
						lblConditionName.setEnabled(true);	
						lblConditionName.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblCommType = new JLabel("Comm. Type", JLabel.TRAILING);
						pnlCondition_popup_a1.add(lblCommType);
						lblCommType.setEnabled(true);	
						lblCommType.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblCommAmount = new JLabel("Comm. Amt.", JLabel.TRAILING);
						pnlCondition_popup_a1.add(lblCommAmount);
						lblCommAmount.setEnabled(true);	
						lblCommAmount.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblCommPerc = new JLabel("Comm. Perc.", JLabel.TRAILING);
						pnlCondition_popup_a1.add(lblCommPerc);
						lblCommPerc.setEnabled(true);	
						lblCommPerc.setPreferredSize(new java.awt.Dimension(136, 24));
					}					
					{
						labelOverrideAmt = new JLabel("Override Amt.", JLabel.TRAILING);
						pnlCondition_popup_a1.add(labelOverrideAmt);
						labelOverrideAmt.setEnabled(true);	
						labelOverrideAmt.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblOverridePerc = new JLabel("Override Perc.", JLabel.TRAILING);
						pnlCondition_popup_a1.add(lblOverridePerc);
						lblOverridePerc.setEnabled(true);	
						lblOverridePerc.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblDaysFromOR = new JLabel("Days from OR", JLabel.TRAILING);
						pnlCondition_popup_a1.add(lblDaysFromOR);
						lblDaysFromOR.setEnabled(true);	
						lblDaysFromOR.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblCondStatus = new JLabel("Status", JLabel.TRAILING);
						pnlCondition_popup_a1.add(lblCondStatus);
						lblCondStatus.setEnabled(true);	
						lblCondStatus.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
				}
				{
					pnlCondition_popup_a2 = new JPanel(new GridLayout(9, 1, 5, 5));
					pnlCondition_popup_a.add(pnlCondition_popup_a2, BorderLayout.CENTER);				
					pnlCondition_popup_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlCondition_popup_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlCondition_popup_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						txtConditionID = new JXTextField("");
						pnlCondition_popup_a2.add(txtConditionID);
						txtConditionID.setEnabled(true);	
						txtConditionID.setEditable(false);
						txtConditionID.setBounds(120, 25, 300, 22);	
						txtConditionID.setHorizontalAlignment(JTextField.CENTER);
						txtConditionID.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));						
					}	
					{
						scpConditionName = new JScrollPane();
						pnlCondition_popup_a2.add(scpConditionName);
						scpConditionName.setBounds(82, 7, 309, 61);
						scpConditionName.setOpaque(true);
						scpConditionName.setPreferredSize(new java.awt.Dimension(375, 159));

						{
							txtConditionDesc = new JTextArea();
							scpConditionName.add(txtConditionDesc);
							scpConditionName.setViewportView(txtConditionDesc);
							txtConditionDesc.setBounds(77, 3, 250, 81);
							txtConditionDesc.setLineWrap(true);
							txtConditionDesc.setPreferredSize(new java.awt.Dimension(366, 133));
							txtConditionDesc.setEditable(true);
							txtConditionDesc.setEnabled(true);
							txtConditionDesc.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent e) {	

								}});	
						}	
					}
					{
						String type[] = {"1","2","3","4","FC"};					
						cmbCommType = new JComboBox(type);
						pnlCondition_popup_a2.add(cmbCommType);
						cmbCommType.setBounds(537, 15, 160, 21);	
						cmbCommType.setEnabled(true);
						cmbCommType.setSelectedIndex(0);	
						cmbCommType.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbCommType.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{

							}
						});		
					}		
					{
						txtCommAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlCondition_popup_a2.add(txtCommAmt);
						txtCommAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtCommAmt.setText("0.00");
						txtCommAmt.setEnabled(true);
						txtCommAmt.setEditable(true);
						txtCommAmt.setBounds(120, 0, 72, 22);	
					}		
					{
						txtCommPerc = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlCondition_popup_a2.add(txtCommPerc);
						txtCommPerc.setFormatterFactory(_JXFormattedTextField.PERCENT);
						txtCommPerc.setText("0.00");
						txtCommPerc.setEnabled(true);
						txtCommPerc.setEditable(true);
						txtCommPerc.setBounds(120, 0, 72, 22);	
					}	
					{
						txtOverrideAmt = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlCondition_popup_a2.add(txtOverrideAmt);
						txtOverrideAmt.setFormatterFactory(_JXFormattedTextField.DECIMAL);
						txtOverrideAmt.setText("0.00");
						txtOverrideAmt.setEnabled(true);
						txtOverrideAmt.setEditable(true);
						txtOverrideAmt.setBounds(120, 0, 72, 22);	
					}	
					{
						txtOverridePerc = new _JXFormattedTextField(JXFormattedTextField.CENTER);
						pnlCondition_popup_a2.add(txtOverridePerc);
						txtOverridePerc.setFormatterFactory(_JXFormattedTextField.PERCENT);
						txtOverridePerc.setText("0.00");
						txtOverridePerc.setEnabled(true);
						txtOverridePerc.setEditable(true);
						txtOverridePerc.setBounds(120, 0, 72, 22);	
					}	
					{
						txtDaysFrOR = new JXTextField("");
						pnlCondition_popup_a2.add(txtDaysFrOR);
						txtDaysFrOR.setEnabled(true);	
						txtDaysFrOR.setEditable(true);
						txtDaysFrOR.setBounds(120, 25, 300, 22);	
						txtDaysFrOR.setHorizontalAlignment(JTextField.CENTER);
						txtDaysFrOR.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));
					}	
					{
						String status[] = {"Active","Inactive"};					
						cmbCondStatus = new JComboBox(status);
						pnlCondition_popup_a2.add(cmbCondStatus);
						cmbCondStatus.setBounds(537, 15, 160, 21);	
						cmbCondStatus.setEnabled(true);
						cmbCondStatus.setSelectedIndex(0);	
						cmbCondStatus.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbCondStatus.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{

							}
						});		
					}
				}

				pnlCondition_popup_c = new JPanel(new BorderLayout(5, 5));
				pnlCondition_popup.add(pnlCondition_popup_c, BorderLayout.CENTER);				
				pnlCondition_popup_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlCondition_popup_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlCondition_popup_c.setPreferredSize(new java.awt.Dimension(500, 40));	

				{
					btnCondSave = new JButton("Save New Schedule");
					pnlCondition_popup_c.add(btnCondSave);
					btnCondSave.setActionCommand("SaveNewCondition");
					btnCondSave.addActionListener(this);
					btnCondSave.setEnabled(true);
					btnCondSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {							
							saveCondition();
						}
					});
				}

			}
		}
		{
			pnlFunction_popup = new JPanel();
			pnlFunction_popup.setLayout(new BorderLayout(5, 5));
			pnlFunction_popup.setBorder(lineBorder);		
			pnlFunction_popup.setPreferredSize(new java.awt.Dimension(400, 330));		

			{		
				pnlFunction_popup_a = new JPanel(new BorderLayout(5, 5));
				pnlFunction_popup.add(pnlFunction_popup_a, BorderLayout.NORTH);				
				pnlFunction_popup_a.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlFunction_popup_a.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlFunction_popup_a.setPreferredSize(new java.awt.Dimension(400, 280));		

				{
					pnlFunction_popup_a1 = new JPanel(new GridLayout(7, 1, 5, 5));
					pnlFunction_popup_a.add(pnlFunction_popup_a1, BorderLayout.WEST);				
					pnlFunction_popup_a1.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlFunction_popup_a1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlFunction_popup_a1.setPreferredSize(new java.awt.Dimension(115, 145));	
					{
						lblFunctionID = new JLabel("Function ID", JLabel.TRAILING);
						pnlFunction_popup_a1.add(lblFunctionID);
						lblFunctionID.setEnabled(true);	
						lblFunctionID.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblFunctionName = new JLabel("Function Name", JLabel.TRAILING);
						pnlFunction_popup_a1.add(lblFunctionName);
						lblFunctionName.setEnabled(true);	
						lblFunctionName.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblFunctionDB = new JLabel("Database Function", JLabel.TRAILING);
						pnlFunction_popup_a1.add(lblFunctionDB);
						lblFunctionDB.setEnabled(true);	
						lblFunctionDB.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblPmtStage = new JLabel("Payment Stage", JLabel.TRAILING);
						pnlFunction_popup_a1.add(lblPmtStage);
						lblPmtStage.setEnabled(true);	
						lblPmtStage.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblByrStat = new JLabel("Buyer Status", JLabel.TRAILING);
						pnlFunction_popup_a1.add(lblByrStat);
						lblByrStat.setEnabled(true);	
						lblByrStat.setPreferredSize(new java.awt.Dimension(136, 24));
					}					
					{
						labelPmtParticular = new JLabel("Payment Part.", JLabel.TRAILING);
						pnlFunction_popup_a1.add(labelPmtParticular);
						labelPmtParticular.setEnabled(true);	
						labelPmtParticular.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
					{
						lblFuncStatus = new JLabel("Status", JLabel.TRAILING);
						pnlFunction_popup_a1.add(lblFuncStatus);
						lblFuncStatus.setEnabled(true);	
						lblFuncStatus.setPreferredSize(new java.awt.Dimension(136, 24));
					}	
				}
				{
					pnlFunction_popup_a2 = new JPanel(new GridLayout(7, 1, 5, 5));
					pnlFunction_popup_a.add(pnlFunction_popup_a2, BorderLayout.CENTER);				
					pnlFunction_popup_a2.setPreferredSize(new java.awt.Dimension(921, 41));
					pnlFunction_popup_a2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					pnlFunction_popup_a2.setPreferredSize(new java.awt.Dimension(200, 150));

					{
						lookupFuncID = new _JLookup(null, "Function", 2, 2);
						pnlFunction_popup_a2.add(lookupFuncID);
						lookupFuncID.setBounds(20, 27, 20, 25);
						lookupFuncID.setReturnColumn(0);
						lookupFuncID.setEnabled(true);	
						lookupFuncID.setEditable(true);	
						lookupFuncID.setLookupSQL(getFunction());
						lookupFuncID.setPreferredSize(new java.awt.Dimension(126, 26));
						lookupFuncID.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	
									String func_name 	= "";
									String func_db 		= "";	
									String pmt_stage 	= "";
									String byr_status 	= "";
									String pmt_part 	= "";	
									String status 		= "";

									try { func_name 	= data[1].toString();} catch (NullPointerException e) {  func_name = ""; }
									try { func_db 		= data[2].toString();} catch (NullPointerException e) {  func_db = ""; }
									try { pmt_stage 	= data[3].toString();} catch (NullPointerException e) {  pmt_stage = ""; }
									try { byr_status 	= data[4].toString();} catch (NullPointerException e) {  byr_status = ""; }
									try { pmt_part 		= data[5].toString();} catch (NullPointerException e) {  pmt_part = ""; }
									try { status 		= data[6].toString();} catch (NullPointerException e) {  status = ""; }

									if (status.equals("A")) {cmbFuncStatus.setSelectedIndex(0);	} else {cmbFuncStatus.setSelectedIndex(1);}


									txtFunctionDesc.setText(func_name);
									txtFunctionDB.setText(func_db);
									lookupPmtStage.setValue(pmt_stage);
									lookupBuyerStatus.setValue(byr_status);
									lookupPmtParticular.setValue(pmt_part);
								}
							}
						});				
					}	
					{
						scpFunctionName = new JScrollPane();
						pnlFunction_popup_a2.add(scpFunctionName);
						scpFunctionName.setBounds(82, 7, 309, 61);
						scpFunctionName.setOpaque(true);
						scpFunctionName.setPreferredSize(new java.awt.Dimension(375, 159));

						{
							txtFunctionDesc = new JTextArea();
							scpFunctionName.add(txtFunctionDesc);
							scpFunctionName.setViewportView(txtFunctionDesc);
							txtFunctionDesc.setBounds(77, 3, 250, 81);
							txtFunctionDesc.setLineWrap(true);
							txtFunctionDesc.setPreferredSize(new java.awt.Dimension(366, 133));
							txtFunctionDesc.setEditable(true);
							txtFunctionDesc.setEnabled(true);
							txtFunctionDesc.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent e) {	

								}});	
						}	
					}
					{
						txtFunctionDB = new JXTextField("");
						pnlFunction_popup_a2.add(txtFunctionDB);
						txtFunctionDB.setEnabled(false);	
						txtFunctionDB.setEditable(false);
						txtFunctionDB.setBounds(120, 25, 300, 22);	
						txtFunctionDB.setHorizontalAlignment(JTextField.CENTER);
						txtFunctionDB.setFont(new java.awt.Font("Segoe UI",Font.BOLD,12));	
					}		
					{
						lookupPmtStage = new _JLookup(null, "Payment Stage", 2, 2);
						pnlFunction_popup_a2.add(lookupPmtStage);
						lookupPmtStage.setBounds(20, 27, 20, 25);
						lookupPmtStage.setReturnColumn(0);
						lookupPmtStage.setEnabled(true);	
						lookupPmtStage.setEditable(true);	
						lookupPmtStage.setLookupSQL(getPaymentStage());
						lookupPmtStage.setPreferredSize(new java.awt.Dimension(126, 26));
						lookupPmtStage.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	

								}
							}
						});
					}		
					{
						lookupBuyerStatus = new _JLookup(null, "Buyer Status", 2, 2);
						pnlFunction_popup_a2.add(lookupBuyerStatus);
						lookupBuyerStatus.setBounds(20, 27, 20, 25);
						lookupBuyerStatus.setReturnColumn(0);
						lookupBuyerStatus.setEnabled(true);	
						lookupBuyerStatus.setEditable(true);	
						lookupBuyerStatus.setLookupSQL(getBuyerStatus());
						lookupBuyerStatus.setPreferredSize(new java.awt.Dimension(126, 26));
						lookupBuyerStatus.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	

								}
							}
						});
					}	
					{
						lookupPmtParticular = new _JLookup(null, "Payment Particular", 2, 2);
						pnlFunction_popup_a2.add(lookupPmtParticular);
						lookupPmtParticular.setBounds(20, 27, 20, 25);
						lookupPmtParticular.setReturnColumn(1);
						lookupPmtParticular.setEnabled(true);	
						lookupPmtParticular.setEditable(true);	
						lookupPmtParticular.setLookupSQL(getPayParticular());
						lookupPmtParticular.setPreferredSize(new java.awt.Dimension(126, 26));
						lookupPmtParticular.addLookupListener(new LookupListener() {
							public void lookupPerformed(LookupEvent event) {
								Object[] data = ((_JLookup)event.getSource()).getDataSet();
								if(data != null){	

								}
							}
						});
					}	
					{
						String status[] = {"Active","Inactive"};					
						cmbFuncStatus = new JComboBox(status);
						pnlFunction_popup_a2.add(cmbFuncStatus);
						cmbFuncStatus.setBounds(537, 15, 160, 21);	
						cmbFuncStatus.setEnabled(true);
						cmbFuncStatus.setSelectedIndex(0);	
						cmbFuncStatus.setPreferredSize(new java.awt.Dimension(89, 26));
						cmbFuncStatus.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent evt) 
							{

							}
						});		
					}
				}

				pnlFunction_popup_c = new JPanel(new BorderLayout(5, 5));
				pnlFunction_popup.add(pnlFunction_popup_c, BorderLayout.CENTER);				
				pnlFunction_popup_c.setPreferredSize(new java.awt.Dimension(921, 41));
				pnlFunction_popup_c.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
				pnlFunction_popup_c.setPreferredSize(new java.awt.Dimension(500, 40));	

				{
					btnFuncSave = new JButton("Save Qualifier");
					pnlFunction_popup_c.add(btnFuncSave);
					btnFuncSave.addActionListener(this);
					btnFuncSave.setEnabled(true);
					btnFuncSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {							
							saveFunction();
						}
					});
				}

			}
		}
		{
			pnlTable = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlTable, BorderLayout.CENTER);	
			pnlTable.setPreferredSize(new java.awt.Dimension(610, 30));				

			pnlSchemeList = new JPanel();
			pnlTable.add(pnlSchemeList, BorderLayout.CENTER);
			pnlSchemeList.setLayout(new BorderLayout(5, 5));
			pnlSchemeList.setPreferredSize(new java.awt.Dimension(923, 321));
			pnlSchemeList.setBorder(lineBorder);	
			pnlSchemeList.setBorder(JTBorderFactory.createTitleBorder("Commission Scheme List"));

			//Main Commission Scheme
			{
				pnlSchemeList_tbl = new JPanel(new BorderLayout());
				pnlSchemeList.add(pnlSchemeList_tbl, BorderLayout.CENTER);
				pnlSchemeList_tbl.setPreferredSize(new java.awt.Dimension(921, 178));
				{
					scrollSchemeList = new _JScrollPaneMain();
					pnlSchemeList_tbl.add(scrollSchemeList, BorderLayout.CENTER);
					{
						modelSchemeList = new modelComm_scheme_main();

						tblSchemeList = new _JTableMain(modelSchemeList);
						scrollSchemeList.setViewportView(tblSchemeList);
						tblSchemeList.addMouseListener(new PopupTriggerListener_panel2());
						tblSchemeList.addMouseListener(this);
						tblSchemeList.setSortable(false);
						tblSchemeList.addKeyListener(new KeyAdapter() {
							public void keyReleased(KeyEvent evt) {
								Integer row = tblSchemeList.getSelectedRow();
								scheme_id = tblSchemeList.getValueAt(row,0).toString().trim();	
								displayConditionList(modelCondition, rowHeaderCondition);	
								refresh_tables_function();
							}

							public void keyPressed(KeyEvent e) {
								Integer row = tblSchemeList.getSelectedRow();
								scheme_id = tblSchemeList.getValueAt(row,0).toString().trim();	
								displayConditionList(modelCondition, rowHeaderCondition);	
								refresh_tables_function();
							}

						}); 


						tblSchemeList.addMouseListener(new MouseAdapter() {
							public void mousePressed(MouseEvent e) {
								Integer row = tblSchemeList.getSelectedRow();
								scheme_id = tblSchemeList.getValueAt(row,0).toString().trim();	
								displayConditionList(modelCondition, rowHeaderCondition);	
								refresh_tables_function();
							}
							public void mouseReleased(MouseEvent e) {
								Integer row = tblSchemeList.getSelectedRow();
								scheme_id = tblSchemeList.getValueAt(row,0).toString().trim();	
								displayConditionList(modelCondition, rowHeaderCondition);	
								refresh_tables_function();
							}
						});

					}
					{
						rowHeaderSchemeList = tblSchemeList.getRowHeader22();
						scrollSchemeList.setRowHeaderView(rowHeaderSchemeList);
						scrollSchemeList.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
					}
					{
						scrollSchemeList_total = new _JScrollPaneTotal(scrollSchemeList);
						pnlSchemeList_tbl.add(scrollSchemeList_total, BorderLayout.SOUTH);
						{
							modelSchemeList_total = new modelComm_scheme_main();
							modelSchemeList_total.addRow(new Object[] { "Total", null });

							tblSchemeList_total = new _JTableTotal(modelSchemeList_total, tblSchemeList);
							tblSchemeList_total.setFont(dialog11Bold);
							scrollSchemeList_total.setViewportView(tblSchemeList_total);
							((_JTableTotal) tblSchemeList_total).setTotalLabel(0);
						}
					}
				}	

				displayCommSchemeList(modelSchemeList,rowHeaderSchemeList);
			}


			pnlCondition = new JPanel(new BorderLayout(5, 5));
			pnlTable.add(pnlCondition, BorderLayout.SOUTH);
			pnlCondition.setPreferredSize(new java.awt.Dimension(923, 221));
			pnlCondition.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

			{
				//Backcharges

				pnlCondition_sub = new JPanel(new GridLayout(1, 1, 0, 0));
				pnlCondition.add(pnlCondition_sub, BorderLayout.CENTER);
				pnlCondition_sub.setPreferredSize(new java.awt.Dimension(400, 255));	
				pnlCondition_sub.setBorder(lineBorder);
				pnlCondition_sub.setBorder(JTBorderFactory.createTitleBorder("Commission Schedule"));

				//Deduction
				{			
					pnlSub = new JPanel(new BorderLayout());
					pnlCondition_sub.add(pnlSub, BorderLayout.CENTER);
					pnlSub.setPreferredSize(new java.awt.Dimension(401, 253));				

					{
						scrollCondition = new _JScrollPaneMain();
						pnlSub.add(scrollCondition, BorderLayout.CENTER);
						{
							modelCondition = new modelComm_Scheme_condition();

							tblCondition = new _JTableMain(modelCondition);
							scrollCondition.setViewportView(tblCondition);
							tblCondition.addMouseListener(new PopupTriggerListener_panel());			

							tblCondition.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {									
									displayFunctionList(modelFunction, rowHeaderFunction);	
								}
								public void mouseReleased(MouseEvent e) {
									displayFunctionList(modelFunction, rowHeaderFunction);	
								}
							});

							tblCondition.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {
									displayFunctionList(modelFunction, rowHeaderFunction);	
								}						
								public void keyPressed(KeyEvent e) {
									displayFunctionList(modelFunction, rowHeaderFunction);		
								}

							});
						}
						{
							rowHeaderCondition = tblCondition.getRowHeader22();
							scrollCondition.setRowHeaderView(rowHeaderCondition);
							scrollCondition.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{
							scrollConditionList_total = new _JScrollPaneTotal(scrollCondition);
							pnlSub.add(scrollConditionList_total, BorderLayout.SOUTH);
							{
								modelConditionList_total = new modelComm_Scheme_condition();
								modelConditionList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

								tblConditionList_total = new _JTableTotal(modelConditionList_total, tblCondition);
								tblConditionList_total.setFont(dialog11Bold);
								scrollConditionList_total.setViewportView(tblConditionList_total);
								((_JTableTotal) tblConditionList_total).setTotalLabel(0);
							}
						}
					}
				}
			}
			{
				pnlDesc = new JPanel(new GridLayout(1, 1, 0, 0));
				pnlCondition.add(pnlDesc, BorderLayout.EAST);
				pnlDesc.setPreferredSize(new java.awt.Dimension(456, 221));
				pnlDesc.setBorder(JTBorderFactory.createTitleBorder("Qualifiers"));
				pnlDesc.setBorder(lineBorder);
				{			
					pnlTblBC = new JPanel(new BorderLayout());
					pnlDesc.add(pnlTblBC, "Center");
					pnlTblBC.setPreferredSize(new java.awt.Dimension(444, 219));	

					{
						scrollFunction = new _JScrollPaneMain();
						pnlTblBC.add(scrollFunction, BorderLayout.CENTER);

						{
							modelFunction = new modelComm_Scheme_function();

							tblFunction = new _JTableMain(modelFunction);
							scrollFunction.setViewportView(tblFunction);
							tblFunction.addMouseListener(new PopupTriggerListener_panel3());					

							tblFunction.addMouseListener(new MouseAdapter() {
								public void mousePressed(MouseEvent e) {
									if ((e.getClickCount() >= 2)) {

									}
								}
								public void mouseReleased(MouseEvent e) {
									if ((e.getClickCount() >= 2)) {

									}
								}
							});

							tblFunction.addKeyListener(new KeyAdapter() {
								public void keyReleased(KeyEvent evt) {

								}						
								public void keyPressed(KeyEvent e) {

								}

							});
						}
						{
							rowHeaderFunction = tblFunction.getRowHeader22();
							scrollFunction.setRowHeaderView(rowHeaderFunction);
							scrollFunction.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());
						}
						{
							scrollFunction_total = new _JScrollPaneTotal(scrollFunction);
							pnlTblBC.add(scrollFunction_total, BorderLayout.SOUTH);
							{
								modelFunction_total = new modelComm_Scheme_function();
								modelFunction_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

								tblFunction_total = new _JTableTotal(modelFunction_total, tblFunction);
								tblFunction_total.setFont(dialog11Bold);
								scrollFunction_total.setViewportView(tblFunction_total);
								((_JTableTotal) tblFunction_total).setTotalLabel(0);
							}
						}
					}
				}
				

			}
		} 		
	}


	//display
	public static void displayCommSchemeList(DefaultTableModel modelMain, JList rowHeader) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select \r\n" + 
			"\r\n" + 
			"a.scheme_id,\r\n" + 
			"a.scheme_desc, \r\n" + 
			"(case when a.agent_type = '001' then 'In-House' else 'External' end),\r\n" + 
			"a.buyer_type,\r\n" + 
			"a.terms,\r\n" + 
			"a.scheme_type,\r\n" + 
			"a.status_id,\r\n" + 
			"upper(trim(c.entity_name)),\r\n" + 
			"created_date,\r\n" + 
			"upper(trim(e.entity_name)),\r\n" + 
			"edited_date\r\n" + 
			"\r\n" + 
			"from cm_scheme_hd a\r\n" + 
			"left join em_employee b on a.created_by = b.emp_code\r\n" + 
			"left join rf_entity c on b.entity_id = c.entity_id\r\n" + 
			"left join em_employee d on a.edited_by = d.emp_code\r\n" + 
			"left join rf_entity e on d.entity_id = e.entity_id " +
			"where a.agent_type = '"+agent_type+"' \n" +
			"and a.status_id = '"+include_inactive+"' \n" +
			"order by a.scheme_id ";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}	

		tblSchemeList.packAll();	
		adjustRowHeight(tblSchemeList);

		int row = tblSchemeList.getRowCount();			
		modelSchemeList_total.setValueAt(new BigDecimal(row), 0, 1);

	}	

	public static void displayConditionList(DefaultTableModel modelMain, JList rowHeader) {

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select \r\n" + 
			"\r\n" + 
			"a.cond_id,\r\n" + 
			"b.cond_desc, \r\n" + 
			"a.comm_type," +
			"a.comm_amt," +
			"a.comm_prcnt," +
			"a.override_amt," +
			"a.override_prcnt," +
			"a.daysnumfromor,  \r\n" + 
			"a.status_id,\r\n" + 
			"upper(trim(d.entity_name)),\r\n" + 
			"a.created_date,\r\n" + 
			"upper(trim(f.entity_name)),\r\n" + 
			"a.edited_date\r\n" + 
			"\r\n" + 
			"from cm_scheme_dl a\r\n" + 
			"left join cm_conditions_hd b on a.cond_id = b.cond_id\r\n" + 
			"left join em_employee c on a.created_by = c.emp_code\r\n" + 
			"left join rf_entity d on c.entity_id = d.entity_id\r\n" + 
			"left join em_employee e on a.edited_by = e.emp_code\r\n" + 
			"left join rf_entity f on e.entity_id = f.entity_id \n" +
			"where scheme_id = '"+scheme_id+"' \n" +
			"order by comm_type" ;

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}	

		tblCondition.packAll();	
		adjustRowHeight(tblCondition);

		int row_tot = tblCondition.getRowCount();	
		modelConditionList_total.setValueAt(new BigDecimal(row_tot), 0, 1);		

	}	

	public static void displayFunctionList(DefaultTableModel modelMain, JList rowHeader) {

		int row = tblCondition.getSelectedRow();		
		String cond_id = tblCondition.getValueAt(row,0).toString().trim();

		FncTables.clearTable(modelMain);//Code to clear modelMain.
		DefaultListModel listModel = new DefaultListModel();//Creating DefaultListModel for rowHeader.
		rowHeader.setModel(listModel);//Setting of DefaultListModel into rowHeader.

		String sql = 				
			"select \r\n" + 
			"\r\n" + 
			"a.func_id,\r\n" + 
			"trim(b.func_desc)," +
			"trim(b.sp_name)," +
			"a.status_id \r\n" + 
			"\r\n" + 
			"from cm_conditions_dl a\r\n" + 
			"left join cm_functions b on a.func_id = b.func_id\r\n" + 
			"\r\n" + 
			"where trim(cond_id) = '"+cond_id+"' \r\n" + 
			"\r\n" + 
			"order by a.func_id";

		System.out.printf("SQL #1: %s", sql);
		pgSelect db = new pgSelect();
		db.select(sql);
		if(db.isNotNull()){
			for(int x=0; x < db.getRowCount(); x++){
				// Adding of row in table
				modelMain.addRow(db.getResult()[x]);
				listModel.addElement(modelMain.getRowCount());
			}			
		}	

		tblFunction.packAll();	
		adjustRowHeight(tblFunction);

		int row_tot = tblFunction.getRowCount();	
		modelFunction_total.setValueAt(new BigDecimal(row_tot), 0, 1);	

	}	


	//Enable/Disable all components inside JPanel
	public void setComponentEnabled(JPanel panel, boolean enable) {
		for(Component comp : panel.getComponents()){
			comp.setEnabled(enable);
		}
	}

	public void refresh_fieldsAddNew_scheme(){

		txtSchemeID.setText(sql_getNewCommSchNo());
		txtDescription.setText("");
		lookupBuyerType.setValue("");
		cmbAgentType2.setSelectedIndex(0);	
		cmbSchemeType.setSelectedIndex(0);	
		txtTerms.setText("");
		cmbStatus.setSelectedIndex(0);	

	}

	public void refresh_fieldsAddNew_condition(){

		txtConditionID.setText(sql_getNewCondNo());
		txtConditionDesc.setText("");
		cmbCommType.setSelectedIndex(0);	
		txtCommAmt.setText("0.00");
		txtCommPerc.setText("0.00");
		txtOverrideAmt.setText("0.00");
		txtOverridePerc.setText("0.00");
		txtDaysFrOR.setText("");
		cmbCondStatus.setSelectedIndex(0);	

	}

	public void refresh_tables_function(){

		//reset table 1
		FncTables.clearTable(modelFunction);
		FncTables.clearTable(modelFunction_total);			
		rowHeaderFunction = tblFunction.getRowHeader22();
		scrollFunction.setRowHeaderView(rowHeaderFunction);	
		modelFunction_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

	}
	
	public void refresh_tables_condition(){

		//reset table 3
		FncTables.clearTable(modelCondition);
		FncTables.clearTable(modelConditionList_total);			
		rowHeaderCondition = tblCondition.getRowHeader();
		scrollCondition.setRowHeaderView(rowHeaderCondition);	
		modelConditionList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

	}

	public void refresh_tables_scheme(){		

		//reset table 2
		FncTables.clearTable(modelSchemeList);
		FncTables.clearTable(modelSchemeList_total);			
		rowHeaderSchemeList = tblSchemeList.getRowHeader();
		scrollSchemeList.setRowHeaderView(rowHeaderSchemeList);	
		modelSchemeList_total.addRow(new Object[] { "Total", new BigDecimal(0.00) });

		
	}



	//action performed
	@Override
	public void actionPerformed(ActionEvent e) {	

	}

	public void mouseClicked(MouseEvent evt) {
		if ((evt.getClickCount() >= 2)) {

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public class PopupTriggerListener_panel extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int row = tblCondition.getSelectedRow();	
				String status = tblCondition.getValueAt(row,8).toString().trim();	
				if (status.equals("A")) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniRemoveCondition.setEnabled(true); mniActivateCondition.setEnabled(false); }
				else  {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniRemoveCondition.setEnabled(false); mniActivateCondition.setEnabled(true); }
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int row = tblCondition.getSelectedRow();	
				String status = tblCondition.getValueAt(row,8).toString().trim();	
				if (status.equals("A")) {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniRemoveCondition.setEnabled(true); mniActivateCondition.setEnabled(false); }
				else  {menu.show(ev.getComponent(), ev.getX(), ev.getY()); mniRemoveCondition.setEnabled(false); mniActivateCondition.setEnabled(true); }
			}
		}
	}

	public class PopupTriggerListener_panel2 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int row = tblSchemeList.getSelectedRow();	
				String status = tblSchemeList.getValueAt(row,6).toString().trim();	
				if (status.equals("A")){ menu2.show(ev.getComponent(), ev.getX(), ev.getY()); mniActCommSch.setEnabled(false);mniInactCommSch.setEnabled(true);}
				else { menu2.show(ev.getComponent(), ev.getX(), ev.getY()); mniActCommSch.setEnabled(true);mniInactCommSch.setEnabled(false);}
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int row = tblSchemeList.getSelectedRow();	
				String status = tblSchemeList.getValueAt(row,6).toString().trim();	
				if (status.equals("A")){ menu2.show(ev.getComponent(), ev.getX(), ev.getY()); mniActCommSch.setEnabled(false);mniInactCommSch.setEnabled(true);}
				else { menu2.show(ev.getComponent(), ev.getX(), ev.getY()); mniActCommSch.setEnabled(true);mniInactCommSch.setEnabled(false);}
			}
		}
	}

	public class PopupTriggerListener_panel3 extends MouseAdapter {
		public void mousePressed(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int row = tblFunction.getSelectedRow();	
				String status = tblFunction.getValueAt(row,3).toString().trim();	
				if (status.equals("A")) {menu3.show(ev.getComponent(), ev.getX(), ev.getY()); mniRemoveFunction.setEnabled(true); mniActivateFunction.setEnabled(false); }
				else  {menu3.show(ev.getComponent(), ev.getX(), ev.getY()); mniRemoveFunction.setEnabled(false); mniActivateFunction.setEnabled(true); }
			}
		}
		public void mouseReleased(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				int row = tblFunction.getSelectedRow();	
				String status = tblFunction.getValueAt(row,3).toString().trim();	
				if (status.equals("A")) {menu3.show(ev.getComponent(), ev.getX(), ev.getY()); mniRemoveFunction.setEnabled(true); mniActivateFunction.setEnabled(false); }
				else  {menu3.show(ev.getComponent(), ev.getX(), ev.getY()); mniRemoveFunction.setEnabled(false); mniActivateFunction.setEnabled(true); }
			}
		}
	}
	
	
    //
	public void saveCommScheme(){

		if(checkCompleteDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete scheme details.", "Incomplete Detail", 
				JOptionPane.WARNING_MESSAGE);}
		else {			

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				pgUpdate db = new pgUpdate();	
				if(to_do.equals("addnewcommscheme")) 
				{		
					insertNewCommScheme(db);						
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"New commission scheme saved.","Information",JOptionPane.INFORMATION_MESSAGE);
					refresh_fieldsAddNew_scheme();
					generateSchemeList();
				}
				else if(to_do.equals("editcommscheme")) 
				{
					updateCommScheme(db);
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"Commission scheme updated.","Information",JOptionPane.INFORMATION_MESSAGE);
					refresh_fieldsAddNew_scheme();
					generateSchemeList();
				}				
			}
		}	
	}

	public void saveCondition(){

		if(checkCompleteConditionDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete condition details.", "Incomplete Detail", 
				JOptionPane.WARNING_MESSAGE);}
		else {			

			if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				pgUpdate db = new pgUpdate();	
				if(to_do.equals("addnewcondition")) 
				{		
					insertNewCondition_toSchemeDetail(db);		
					insertNewCondition(db);	
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"New commission scheme condition saved.","Information",JOptionPane.INFORMATION_MESSAGE);
					refresh_fieldsAddNew_condition();
					displayConditionList(modelCondition, rowHeaderCondition);	
					refresh_tables_function();
				}
				else if(to_do.equals("editcondition")) 
				{
					updateCondition_toSchemeDetail(db);
					updateCondition(db);
					db.commit();	
					JOptionPane.showMessageDialog(getContentPane(),"Commission scheme condition updated.","Information",JOptionPane.INFORMATION_MESSAGE);
					refresh_fieldsAddNew_condition();
					displayConditionList(modelCondition, rowHeaderCondition);	
					refresh_tables_function();
				}				
			}
		}	
	}

	public void saveFunction(){

		if(checkCompleteFunctionDetails()==false)
		{JOptionPane.showMessageDialog(getContentPane(), "Please enter complete function details.", "Incomplete Detail", 
				JOptionPane.WARNING_MESSAGE);}
		else {

			if(checkFunctionIfAlreadyExist()==true)
			{JOptionPane.showMessageDialog(getContentPane(), "Function already exists.", "Error", 
					JOptionPane.ERROR_MESSAGE);}
			else {

				if (JOptionPane.showConfirmDialog(getContentPane(), "Are all entries correct?", "Confirmation", 
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					pgUpdate db = new pgUpdate();	
					if(to_do.equals("addfunction")) 
					{		
						insertNewConditionFunction(db);	
						db.commit();	
						JOptionPane.showMessageDialog(getContentPane(),"New commission scheme function saved.","Information",JOptionPane.INFORMATION_MESSAGE);
						displayFunctionList(modelFunction, rowHeaderFunction);	
					}
					else if(to_do.equals("editcondition")) 
					{
						updateCondition_toSchemeDetail(db);
						updateCondition(db);
						db.commit();	
						JOptionPane.showMessageDialog(getContentPane(),"Commission scheme function updated.","Information",JOptionPane.INFORMATION_MESSAGE);
						displayFunctionList(modelFunction, rowHeaderFunction);	
					}	
					else if(to_do.equals("addnewfunction")) 
					{
						insertNewFunction(db);
						db.commit();	
						JOptionPane.showMessageDialog(getContentPane(),"New commission scheme function added.","Information",JOptionPane.INFORMATION_MESSAGE);
						displayFunctionList(modelFunction, rowHeaderFunction);	
					}	
				}
			}
		}	
	}
	
	public void updateConditionStatus(String status){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to remove this condition?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			pgUpdate db = new pgUpdate();					
			updateConditionStatus(db, status);
			db.commit();	
			JOptionPane.showMessageDialog(getContentPane(),"Commission scheme condition removed.","Information",JOptionPane.INFORMATION_MESSAGE);
			refresh_fieldsAddNew_condition();
			displayConditionList(modelCondition, rowHeaderCondition);	
			refresh_tables_function();
		}
	}
	
	public void updateFunctionStatus(String status){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to remove this function?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			pgUpdate db = new pgUpdate();					
			updateFunctionStatus(db, status);
			db.commit();	
			JOptionPane.showMessageDialog(getContentPane(),"Commission scheme function removed.","Information",JOptionPane.INFORMATION_MESSAGE);
			displayFunctionList(modelFunction, rowHeaderFunction);	

		}
	}

	public void activateCommScheme(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to \n" +
				"reactivate this commission scheme?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			pgUpdate db = new pgUpdate();						
			updateParentAccountWSub(db, "A");						
			db.commit();	
			JOptionPane.showMessageDialog(getContentPane(),"Commission scheme status - active.","Information",JOptionPane.INFORMATION_MESSAGE);
			generateSchemeList();

		}			
	}

	public void inactivateCommScheme(){

		if (JOptionPane.showConfirmDialog(getContentPane(), "Are you sure you want to set \n" +
				"this commission scheme inactive?", "Confirmation", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			pgUpdate db = new pgUpdate();						
			updateParentAccountWSub(db, "I");						
			db.commit();	
			JOptionPane.showMessageDialog(getContentPane(),"Commission scheme status - inactive.","Information",JOptionPane.INFORMATION_MESSAGE);
			generateSchemeList();

		}			
	}

	private void generateSchemeList(){
		if (cmbAgentType.getSelectedIndex()==0) 
		{
			agent_type = "001";
			displayCommSchemeList(modelSchemeList,rowHeaderSchemeList);
		}
		else
		{
			agent_type = "002";
			displayCommSchemeList(modelSchemeList,rowHeaderSchemeList);
		}
	}

	

	//select, lookup and get statements	
	public String getCommScheme(){ //ok

		String sql = 
			"select \r\n" + 
			"\r\n" + 
			"trim(scheme_id) as \"Scheme ID\" ,\r\n" + 
			"trim(scheme_desc) as \"Scheme Desc.\" ,\r\n" + 
			"status_id as \"Status\" \r\n" + 
			"\r\n" + 
			"from cm_scheme_hd \n" +
			"order by scheme_id ";		
		return sql;

	}	

	public String getBuyertype(){ //ok

		String sql = 
			"select \r\n" + 
			"\r\n" + 
			"trim(type_id) as \"Type ID\" ,\r\n" + 
			"trim(type_desc) as \"Type Desc.\" ,\r\n" + 
			"status_id as \"Status\" \r\n" + 
			"\r\n" + 
			"from mf_buyer_type \n" +
			"order by type_id ";		
		return sql;

	}	

	public String getFunction(){ //ok

		String sql = 
			"select\r\n" + 
			"\r\n" + 
			"trim(func_id) as \"Function ID\" ,\r\n" + 
			"trim(func_desc) as \"Description\"," +
			"trim(sp_name) as \"DB Function\"," +
			"trim(pmtstage_id) as \"Pmt. Stage\"," +
			"trim(byrstat_id) as \"Buyer Status\", " +
			"trim(payment_part) as \"Pmt. Particular\"," +
			"status_id \r\n" + 
			"\r\n" + 
			"from cm_functions \r\n" + 
			"\r\n" + 
			"order by func_id";		
		return sql;

	}	

	public String getPaymentStage(){ //ok

		String sql = 
			"select\r\n" + 
			"\r\n" + 
			"trim(part_id) as \"Part ID\",\r\n" + 
			"trim(part_desc) as \"Description\" \r\n" + 
			"\r\n" + 
			"from mf_client_ledger_part \r\n" + 
			"\r\n" + 
			"where part_id in ('012','013','014')\r\n" + 
			"\r\n" + 
			"order by part_id";		
		return sql;

	}	
	
	public String getBuyerStatus(){ //ok

		String sql = 
			"select\r\n" + 
			"\r\n" + 
			"trim(byrstatus_id) as \"Buyer Status ID\" ,\r\n" + 
			"trim(status_desc) as \"Description\" \r\n" + 
			"\r\n" + 
			"from mf_buyer_status \r\n" + 
			"\r\n" + 
			"order by byrstatus_id\r\n" + 
			"";		
		return sql;

	}	

	public String getPayParticular(){ //ok

		String sql = 
			"select\r\n" + 
			"\r\n" + 
			"trim(pay_part_id) as \"Pay Part ID\",\r\n" + 
			"trim(particulars) as \"Particulars\",\r\n" + 
			"trim(partdesc) as \"Description\" \r\n" + 
			"\r\n" + 
			"from mf_pay_particular \r\n" + 
			"\r\n" + 
			"order by pay_part_id" + 
			"";		
		return sql;

	}	

	public static String sql_getNewCommSchNo() {//ok	

		String new_scheme_id = "";

		String SQL = 
			"select trim(to_char(max(coalesce(scheme_id::int,0))+1,'000000')) from cm_scheme_hd " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			new_scheme_id = (String) db.getResult()[0][0];
		}else{
			new_scheme_id = null;
		}

		return new_scheme_id;
	}

	public static String sql_getNewCondNo() {//ok	

		String new_cond_id = "";

		String SQL = 
			"select trim(to_char(max(coalesce(cond_id::int,0))+1,'000')) from cm_conditions_hd " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			new_cond_id = (String) db.getResult()[0][0];
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {new_cond_id = "001";}
			else {new_cond_id = (String) db.getResult()[0][0]; }
		}else{
			new_cond_id = null;
		}

		return new_cond_id;
	}
	
	public static String sql_getNewFuncNo() {//ok	

		String new_func_id = "";

		String SQL = 
			"select trim(to_char(max(coalesce(func_id::int,0))+1,'000')) from cm_functions " ;

		pgSelect db = new pgSelect();
		db.select(SQL);

		if(db.isNotNull()){
			new_func_id = (String) db.getResult()[0][0];
			if((String) db.getResult()[0][0]==null||db.getResult()[0][0].equals("null")) {new_func_id = "001";}
			else {new_func_id = (String) db.getResult()[0][0]; }
		}else{
			new_func_id = "001";
		}

		return new_func_id;
	}


	//table operations	
	private static void adjustRowHeight(_JTableMain tbl){

		int rw = tbl.getModel().getRowCount();
		int x = 0;

		while (x<rw){			
			tbl.setRowHeight(x, 22);			
			x++;
		}
	}


	//save and insert
	public void insertNewCommScheme(pgUpdate db) {

		String sqlDetail = 
			"INSERT into cm_scheme_hd values (" +
			"'"+txtSchemeID.getText().trim()+"',  \n" +  	//1
			"'"+txtDescription.getText().trim().replace("'", "''")+"',  \n" +  	//2
			"'"+txtTerms.getText().trim()+"',  \n" ;  	//3

		//agent type
		if(cmbAgentType2.getSelectedIndex()==0) {sqlDetail = sqlDetail + "'001',  \n";} 
		else{sqlDetail = sqlDetail + "'002',  \n";} 

		sqlDetail = sqlDetail +			

		"'"+lookupBuyerType.getText()+"', \n" ;

		//status
		if(cmbStatus.getSelectedIndex()==0) {sqlDetail = sqlDetail + "'A',  \n";} 
		else{sqlDetail = sqlDetail + "'I',  \n";} 

		sqlDetail = sqlDetail +		

		"'"+dateFormat.format(FncGlobal.dateFormat(FncGlobal.getDateSQL()))+"',  \n" ;	

		//scheme type
		if(cmbSchemeType.getSelectedIndex()==0) {sqlDetail = sqlDetail + "'R',  \n";} 
		else{sqlDetail = sqlDetail + "'S',  \n";} 

		sqlDetail = sqlDetail +			

		"'"+UserInfo.EmployeeCode+"', \n" +		
		"now(),  \n" +
		"null, \n" +
		"null \n" +
		")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	public void updateParentAccountWSub(pgUpdate db, String status){

		String sqlDetail = 
			"update cm_scheme_hd set status_id = '"+status+"' where trim(scheme_id) = '"+scheme_id+"'  " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	public void updateCommScheme(pgUpdate db) {

		String sqlDetail = 
			"update cm_scheme_hd set " +		
			"scheme_desc = '"+txtDescription.getText().trim().replace("'", "''")+"',  \n" +  	
			"terms = "+txtTerms.getText().trim()+",  \n" ;  	

		//agent type
		if(cmbAgentType2.getSelectedIndex()==0) {sqlDetail = sqlDetail + "agent_type = '001',  \n";} 
		else{sqlDetail = sqlDetail + "agent_type = '002',  \n";} 

		sqlDetail = sqlDetail +			

		"buyer_type = '"+lookupBuyerType.getText()+"', \n" ;

		//status
		if(cmbStatus.getSelectedIndex()==0) {sqlDetail = sqlDetail + "status_id = 'A',  \n";} 
		else{sqlDetail = sqlDetail + "status_id = 'I',  \n";} 

		//scheme type
		if(cmbSchemeType.getSelectedIndex()==0) {sqlDetail = sqlDetail + "scheme_type = 'R',  \n";} 
		else{sqlDetail = sqlDetail + "scheme_type = 'S',  \n";} 

		sqlDetail = sqlDetail +		

		"edited_by = '"+UserInfo.EmployeeCode+"', \n" +		
		"edited_date = now()  \n" +		
		"where scheme_id = '"+txtSchemeID.getText().trim()+"'  " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	public void insertNewCondition_toSchemeDetail(pgUpdate db) {

		Double comm_amt = null;
		Double comm_perc = null;
		Double overide_amt = null;
		Double overide_perc = null;
		String status = "";

		if (txtCommAmt.getText().equals(".00")||txtCommAmt.getText().equals("")||txtCommAmt.getText().equals("0.00")) {comm_amt=null;} 
		else {comm_amt = Double.parseDouble(txtCommAmt.getText().replace(",",""));}
		if (txtCommPerc.getText().equals("0.00")||txtCommPerc.getText().equals("")||txtCommPerc.getText().equals(".00")) {comm_perc=null;} 
		else {comm_perc = Double.parseDouble(txtCommPerc.getText().replace(",",""));}
		if (txtOverrideAmt.getText().equals(".00")||txtOverrideAmt.getText().equals("")||txtOverrideAmt.getText().equals("0.00")) {overide_amt=null;} 
		else {overide_amt = Double.parseDouble(txtOverrideAmt.getText().replace(",",""));}
		if (txtOverridePerc.getText().equals("0.00")||txtOverridePerc.getText().equals("")||txtOverridePerc.getText().equals(".00")) {overide_perc=null;} 
		else {overide_perc = Double.parseDouble(txtOverridePerc.getText().replace(",",""));}
		if (cmbCondStatus.getSelectedIndex()==0) {status = "A";} else {status = "I";}

		String sqlDetail = 
			"INSERT into cm_scheme_dl values (" +
			"'"+scheme_id+"',  \n" +  						//1
			"'"+cmbCommType.getSelectedItem()+"',  \n" +  	//2
			""+comm_amt+",  \n" +  							//3
			""+comm_perc+",  \n" + 							//4
			""+overide_amt+",  \n" +  						//5
			""+overide_perc+",  \n" +  						//6			
			"'"+txtConditionID.getText().trim()+"',  \n" + 	//7
			""+txtDaysFrOR.getText().trim()+",  \n" +  		//8	
			"'"+UserInfo.EmployeeCode+"', \n" +				//9
			"now(),  \n" + 									//10
			"null, \n" +									//11
			"null," +										//12
			"'"+status+"' \n" +								//13
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	public void insertNewCondition(pgUpdate db) {

		String sqlDetail = 
			"INSERT into cm_conditions_hd values (" +			
			"'"+txtConditionID.getText().trim()+"',  \n" + 	//1
			"'"+txtConditionDesc.getText().trim().replace("'","''")+"',  \n" ; 	//2
		//status
		if(cmbCondStatus.getSelectedIndex()==0) {sqlDetail = sqlDetail + "'A',  \n";} //3
		else{sqlDetail = sqlDetail + "'I',  \n";} 

		sqlDetail = sqlDetail +		

		"'"+UserInfo.EmployeeCode+"', \n" +				//4
		"now(),  \n" + 									//5
		"null, \n" +									//6
		"null \n" +										//7
		")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	public void updateCondition_toSchemeDetail(pgUpdate db) {

		Double comm_amt = null;
		Double comm_perc = null;
		Double overide_amt = null;
		Double overide_perc = null;
		String status = "";

		if (txtCommAmt.getText().equals(".00")||txtCommAmt.getText().equals("")||txtCommAmt.getText().equals("0.00")) {comm_amt=null;} 
		else {comm_amt = Double.parseDouble(txtCommAmt.getText().replace(",",""));}
		if (txtCommPerc.getText().equals("0.00")||txtCommPerc.getText().equals("")||txtCommPerc.getText().equals(".00")) {comm_perc=null;} 
		else {comm_perc = Double.parseDouble(txtCommPerc.getText().replace(",",""));}
		if (txtOverrideAmt.getText().equals(".00")||txtOverrideAmt.getText().equals("")||txtOverrideAmt.getText().equals("0.00")) {overide_amt=null;} 
		else {overide_amt = Double.parseDouble(txtOverrideAmt.getText().replace(",",""));}
		if (txtOverridePerc.getText().equals("0.00")||txtOverridePerc.getText().equals("")||txtOverridePerc.getText().equals(".00")) {overide_perc=null;} 
		else {overide_perc = Double.parseDouble(txtOverridePerc.getText().replace(",",""));}
		if (cmbCondStatus.getSelectedIndex()==0) {status = "A";} else {status = "I";}

		String sqlDetail = 
			"update cm_scheme_dl set " +
			"comm_type = '"+cmbCommType.getSelectedItem()+"',  \n" +  	//1
			"comm_amt = "+comm_amt+",  \n" +  							//2
			"comm_prcnt = "+comm_perc+",  \n" + 						//3
			"override_amt = "+overide_amt+",  \n" +  					//4	
			"override_prcnt = "+overide_perc+",  \n" +  				//5	
			"daysnumfromor = "+txtDaysFrOR.getText().trim()+",  \n" +  	//7	
			"edited_by = '"+UserInfo.EmployeeCode+"'," +				//8
			"edited_date = now()," +									//9
			"status_id = '"+status+"'  \n" +							//10
			"where scheme_id = '"+scheme_id+"' \n" +					//11
			"and cond_id = '"+txtConditionID.getText().trim()+"'  " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	public void updateCondition(pgUpdate db) {

		String status = "";
		if (cmbCondStatus.getSelectedIndex()==0) {status = "A";} else {status = "I";}

		String sqlDetail = 
			"update cm_conditions_hd set " +			
			"cond_desc = '"+txtConditionDesc.getText().trim()+"',  \n" + 	
			"status_id = '"+status+"',  \n" +		
			"edited_by = '"+UserInfo.EmployeeCode+"'," +	
			"edited_date = now() " +	
			"where cond_id = '"+txtConditionID.getText().trim()+"'  " ;			

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	public void insertNewConditionFunction(pgUpdate db) {

		int row = tblCondition.getSelectedRow();	
		String cond_id = tblCondition.getValueAt(row,0).toString().trim();	

		String sqlDetail = 
			"INSERT into cm_conditions_dl values (" +			
			"'"+cond_id+"',  \n" + 	//1
			"'"+lookupFuncID.getText().trim().replace("'","''")+"',  " +
			"'A'  " +
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}

	public void updateFunctionStatus(pgUpdate db, String status){

		int row = tblFunction.getSelectedRow();	
		String func_id = tblFunction.getValueAt(row,0).toString().trim();	
		
		int row2 = tblCondition.getSelectedRow();	
		String cond_id = tblCondition.getValueAt(row2,0).toString().trim();	
				
		String sqlDetail = 
			"update cm_conditions_dl set status_id = '"+status+"' " +
			"where trim(cond_id) = '"+cond_id+"'  " +
			"and func_id = '"+func_id+"' " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}
	
	public void updateConditionStatus(pgUpdate db, String status){

		int row = tblSchemeList.getSelectedRow();	
		scheme_id = tblSchemeList.getValueAt(row,0).toString().trim();	
		
		int row2 = tblCondition.getSelectedRow();	
		String cond_id = tblCondition.getValueAt(row2,0).toString().trim();	
				
		String sqlDetail = 
			"update cm_scheme_dl set status_id = '"+status+"', " +
			"edited_by = '"+UserInfo.EmployeeCode+"'," +	
			"edited_date = now() " +	
			"where trim(scheme_id) = '"+scheme_id+"'  " +
			"and cond_id = '"+cond_id+"' " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);		

	}

	public void insertNewFunction(pgUpdate db) {

		String pmt_stage  = "";
		String byr_status = "";
		String pmt_part   = "";

		if (lookupPmtStage.getText()==null) {pmt_stage=null;} else {pmt_stage = lookupPmtStage.getText().trim();}
		if (lookupBuyerStatus.getText()==null) {byr_status=null;} else {byr_status = lookupBuyerStatus.getText().trim();}
		if (lookupPmtParticular.getText()==null) {pmt_part=null;} else {pmt_part = lookupPmtParticular.getText().trim();}

		String sqlDetail = 
			"INSERT into cm_functions values (" +
			"'"+lookupFuncID.getText().trim()+"',  \n" +  	//1
			"'"+txtFunctionDesc.getText().trim()+"',  \n" + //2
			"'',  \n" + 			//3
			"null, " +				//4							
			"'A',  \n" + 			//5
			"'P',  \n" + 			//6 ???	
			"'"+pmt_stage+"',  \n" +//7		
			"null, " +				//8
			"null, " +				//9
			"'"+byr_status+"',  \n" +  //10	
			"null, " +				//11
			"'"+pmt_part+"',  \n" + //12	
			"null, " +				//13
			"null, " +				//14
			"false, " +				//15
			"null, " +				//16
			"'"+UserInfo.EmployeeCode+"', \n" +				//17
			"now(),  \n" + //18
			"null, \n" +									//19
			"null" +										//20
			")   " ;

		System.out.printf("SQL #1: %s", sqlDetail);
		db.executeUpdate(sqlDetail, false);	
	}
	
	
	//validation
	private Boolean checkCompleteDetails(){

		boolean x = false;		

		String acctid, acctname;
		String agent_type, terms, buyer_type, scheme_type = "";

		if (cmbAgentType2.getSelectedIndex()==0) {agent_type = "001";}
		else {agent_type = "002";}

		if (cmbSchemeType.getSelectedIndex()==0) {scheme_type = "R";}
		else {scheme_type = "S";}

		try { terms = txtTerms.getText().toString();} catch (NullPointerException e) { terms 	= ""; }
		try { buyer_type = lookupBuyerType.getText().toString();} catch (NullPointerException e) { buyer_type	= ""; }

		acctid 		= txtSchemeID.getText();
		acctname 	= txtDescription.getText();

		if (acctid.equals("") || acctname.equals("")||agent_type.equals("")||terms.equals("")||buyer_type.equals("")||scheme_type.equals("")) {x=false;} 
		else {x=true;}		

		return x;
	}

	private Boolean checkCompleteConditionDetails(){

		boolean x = false;		

		String condid, condname;
		String daysfror;

		condid 		= txtConditionID.getText();
		condname 	= txtConditionDesc.getText();
		daysfror 	= txtDaysFrOR.getText();

		if (condid.equals("") || condname.equals("")||daysfror.equals("")) {x=false;} 
		else {x=true;}		

		return x;
	}

	private Boolean checkCompleteFunctionDetails(){

		boolean x = false;		

		String funcid, funcname;

		funcid 		= lookupFuncID.getText();
		funcname 	= txtFunctionDesc.getText();

		if (funcid.equals("") || funcname.equals("")) {x=false;} 
		else {x=true;}		

		return x;
	}

	private Boolean checkFunctionIfAlreadyExist(){

		boolean alreadyexist = false;		

		for(int x=0; x < tblFunction.getRowCount(); x++){

			String func_id_new = lookupFuncID.getText().trim();
			String func_id     = (String)tblFunction.getValueAt(x,0);

			if(func_id_new.equals(func_id)){		
				alreadyexist = true;	
				break;
			}		
			else {}
		}

		return alreadyexist;
	}

}
