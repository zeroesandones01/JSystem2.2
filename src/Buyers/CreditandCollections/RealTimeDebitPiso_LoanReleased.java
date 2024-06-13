package Buyers.CreditandCollections;

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

import components._JInternalFrame;
import components._JTabbedPane;
import interfaces._GUI;

public class RealTimeDebitPiso_LoanReleased extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = -4760977611587287652L;
	Dimension size = new Dimension(810, 500);
	static String title = "MBTC Piso Debit(Loan Released)";
	Border lineRed = BorderFactory.createLineBorder(Color.RED);
	Border lineGray = BorderFactory.createLineBorder(Color.GRAY);
	
	panMBTCLoanReleased_Credit panCredit = new panMBTCLoanReleased_Credit(this);
	panMBTCLoanReleased_Debit panDebit = new panMBTCLoanReleased_Debit(this);
	
	private _JTabbedPane tabrtd;
	public static boolean blnWithDir = true;
	
	public RealTimeDebitPiso_LoanReleased() {
		super(title, true, true, false, false);
		initGUI();
	}

	public RealTimeDebitPiso_LoanReleased(String title) {
		super(title);
		initGUI();
	}

	public RealTimeDebitPiso_LoanReleased(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
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
					panCredit = new panMBTCLoanReleased_Credit(this);
					panDebit = new panMBTCLoanReleased_Debit(this);
					
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
