package Buyers.CreditandCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import Buyers.LoansManagement.hdmfMon_accountQuery;
import Buyers.LoansManagement.hdmfMon_borrowerValidation;
import Buyers.LoansManagement.hdmfMon_houseInspection;
import Buyers.LoansManagement.hdmfMon_msvsMonitoring;
import Buyers.LoansManagement.hdmfMon_qualifyAccounts;
import Buyers.LoansManagement.hdmfMon_statusTagging;
import components._JInternalFrame;
import components._JTabbedPane;

public class RealTimeDebitPiso extends _JInternalFrame implements
		ActionListener, _GUI {

	private static final long serialVersionUID = 328854711741761018L;
	Dimension size = new Dimension(810, 500);
	static String title = "MBTC Piso Debit";
	Border lineRed = BorderFactory.createLineBorder(Color.RED);
	Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	
	panMBTC_Credit panCredit = new panMBTC_Credit(this);
	panMBTC_Debit panDebit = new panMBTC_Debit(this);
	
	private _JTabbedPane tabrtd;
	public static boolean blnWithDir = true;
	
	public RealTimeDebitPiso() {
		super(title, true, true, false, false);
		initGUI();
	}

	public RealTimeDebitPiso(String title) {
		super(title);
		initGUI();
	}

	public RealTimeDebitPiso(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	public void initGUI() {
		this.setTitle(title);
		this.setSize(size);
		{
			JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(panMain, BorderLayout.CENTER);
			panMain.setBorder(new EmptyBorder(0, 5, 5, 5));
			{
				tabrtd = new _JTabbedPane();
				panMain.add(tabrtd, BorderLayout.CENTER);
				tabrtd.setBorder(new EmptyBorder(5, 5, 5, 5));
				{
					panCredit = new panMBTC_Credit(this);
					panDebit = new panMBTC_Debit(this);
					
					tabrtd.addTab("					Piso Credit					", null, panCredit, null);
					tabrtd.addTab("					Piso Debit					", null, panDebit, null);
				}
				tabrtd.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						panMBTC_Credit.autoDir();
						panMBTC_Credit.CheckDir();
						
						panMBTC_Debit.autoDir();
						panMBTC_Debit.CheckDir();
					}
				});
			}
		}
		{
			if (!blnWithDir) {
				JOptionPane.showMessageDialog(getContentPane(), "No directory was set.", "Directory", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
