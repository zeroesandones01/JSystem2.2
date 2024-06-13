package Buyers.LoansManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Buyers.CreditandCollections._RealTimeDebit;
import Functions.FncLookAndFeel;
import Functions.FncTables;
import Functions.UserInfo;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JTableMain;
import interfaces._GUI;
import tablemodel.modelMBTC;

public class mbtcLoanReleased extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = -7525593686536955561L;
	static String title = "Loan Released MBTC Accounts";
	Dimension frameSize = new Dimension(750, 600);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	static Border lineBlue = BorderFactory.createLineBorder(Color.BLUE);
	
	private JLabel lblCo;
	private JLabel lblProject;

	public _JLookup txtCoID;
	public _JLookup txtProjectID;
	
	private JTextField txtCo;
	private JTextField txtProject;

	private _JTabbedPane tabMBTCLR;
	
	private mbtcLoanReleased_panel1 panel1;
	private mbtcLoanReleased_panel2 panel2;
	
	public mbtcLoanReleased() {
		super(title, true, true, false, true);
		initGUI();
	}

	public mbtcLoanReleased(String title) {
		super(title);
		initGUI();
	}

	public mbtcLoanReleased(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}
	
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			JXPanel panPageStart = new JXPanel(new BorderLayout(5, 5));
			panMain.add(panPageStart, BorderLayout.PAGE_START);
			panPageStart.setPreferredSize(new Dimension(0, 96));
			{
				JXPanel panLabelBox = new JXPanel(new GridLayout(2, 1, 5, 5));
				panPageStart.add(panLabelBox, BorderLayout.CENTER);
				panLabelBox.setBorder(JTBorderFactory.createTitleBorder("Parameters", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					JXPanel panCo = new JXPanel(new BorderLayout(5, 5));
					panLabelBox.add(panCo, BorderLayout.CENTER);
					{
						lblCo = new JLabel("Company");
						panCo.add(lblCo, BorderLayout.LINE_START);
						lblCo.setPreferredSize(new Dimension(80, 0));
						lblCo.setHorizontalAlignment(JTextField.LEFT);
					}
					{
						JXPanel panIDName = new JXPanel(new BorderLayout(5, 5));
						panCo.add(panIDName, BorderLayout.CENTER);
						{
							txtCoID = new _JLookup("");
							panIDName.add(txtCoID, BorderLayout.LINE_START);
							txtCoID.setHorizontalAlignment(JTextField.CENTER);
							txtCoID.setPreferredSize(new Dimension(60, 0));
							txtCoID.setLookupSQL(_RealTimeDebit.sqlCo());
							txtCoID.setReturnColumn(0);
							txtCoID.addLookupListener(new LookupListener() {
								public void lookupPerformed(LookupEvent e) {
									Object[] data = ((_JLookup) e.getSource()).getDataSet();
									if (data != null) {
										txtCo.setText(data[1].toString());
										txtProjectID.setLookupSQL(_RealTimeDebit.sqlProject(txtCoID.getText()));
									}
								}
							});
						}
						{
							txtCo = new JTextField("---");
							panIDName.add(txtCo, BorderLayout.CENTER);
							txtCo.setHorizontalAlignment(JTextField.CENTER);
							txtCo.setEditable(false);
						}
					}
					JXPanel panProject = new JXPanel(new BorderLayout(5, 5));
					panLabelBox.add(panProject, BorderLayout.CENTER);
					{
						{
							lblProject = new JLabel("Project");
							panProject.add(lblProject, BorderLayout.LINE_START);
							lblProject.setPreferredSize(new Dimension(80, 0));
							lblProject.setHorizontalAlignment(JTextField.LEFT);
						}
						{
							JXPanel panIDName = new JXPanel(new BorderLayout(5, 5));
							panProject.add(panIDName, BorderLayout.CENTER);
							{
								txtProjectID = new _JLookup("");
								panIDName.add(txtProjectID, BorderLayout.LINE_START);
								txtProjectID.setHorizontalAlignment(JTextField.CENTER);
								txtProjectID.setPreferredSize(new Dimension(60, 0));
								txtProjectID.setReturnColumn(0);
								txtProjectID.addLookupListener(new LookupListener() {
									public void lookupPerformed(LookupEvent e) {
										Object[] data = ((_JLookup) e.getSource()).getDataSet();
										if (data != null) {
											txtProject.setText(data[2].toString());
										}
									}
								});
							}
							{
								txtProject = new JTextField("---");
								panIDName.add(txtProject, BorderLayout.CENTER);
								txtProject.setHorizontalAlignment(JTextField.CENTER);
								txtProject.setEditable(false);
							}
						}
					}
				}
				{
					Setdefaults();
				}
			}
			{
				JXPanel panCenter = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panCenter, BorderLayout.CENTER);
				{
					tabMBTCLR = new _JTabbedPane();
					panCenter.add(tabMBTCLR, BorderLayout.CENTER);
					{
						{
							panel1 = new mbtcLoanReleased_panel1(this);
							tabMBTCLR.addTab("         Qualify Accounts         ", null, panel1, null);
						}
						{
							panel2 = new mbtcLoanReleased_panel2(this);
							tabMBTCLR.addTab("         Account Details          ", null, panel2, null);
						}
					}
				}
			}
		}
	}

	private void Setdefaults() {
		String strCount = "";
		Integer intCount = 0;
		
		/*	Company Default	*/
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_company");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtCoID.setValue("02");
			txtCo.setText("CENQHOMES DEVELOPMENT CORPORATION");
		} else {
			txtCoID.setValue(_RealTimeDebit.GetValue("SELECT co_id FROM mf_company LIMIT 1"));
			txtCo.setText(_RealTimeDebit.GetValue("SELECT company_name FROM mf_company WHERE co_id = '"+txtCoID.getText()+"'"));
		}
		
		/*	Project Default	*/
		strCount = _RealTimeDebit.GetValue("SELECT TRIM(COUNT(*)::CHAR(1)) FROM mf_project");
		intCount = Integer.parseInt(strCount);
		
		if (intCount > 1) {
			txtProjectID.setValue("015");
			txtProject.setText("TERRAVERDE RESIDENCES");
		} else {
			txtProjectID.setValue(_RealTimeDebit.GetValue("SELECT proj_id FROM mf_project LIMIT 1"));
			txtProject.setText(_RealTimeDebit.GetValue("SELECT proj_name FROM mf_project WHERE proj_id = '"+txtProjectID.getText()+"'"));
		}
	}
}
