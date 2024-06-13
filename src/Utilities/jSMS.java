package Utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

import Buyers.LoansManagement.mbtcLoanReleased_panel1;
import Buyers.LoansManagement.mbtcLoanReleased_panel2;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import components._JTabbedPane;
import interfaces._GUI;

public class jSMS extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 3845633426741188141L;
	static String title = "Terraverde SMS";
	Dimension frameSize = new Dimension(600, 600);

	private _JTabbedPane tabSMS;
	
	private sms_tab0 panTab0;
	private sms_tab2 panTab2;
	private sms_tab3 panTab3;
	private sms_tab4 panTab4; 
	
	private Boolean blnQuick = false; 
	
	public jSMS() {
		super(title, true, true, false, true);
		initGUI(); 
	}

	public jSMS(String title) {
		super(title, true, true, false, true);
		blnQuick = true; 
		initGUI(); 
	}

	public jSMS(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI(); 
	}

	public void initGUI() {
		this.setSize(frameSize);
		this.setTitle(title);
		
		JXPanel panMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(panMain, BorderLayout.CENTER);
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			tabSMS = new _JTabbedPane(); 
			panMain.add(tabSMS, BorderLayout.CENTER); 
			{
				{
					panTab0 = new sms_tab0(this);
					panTab2 = new sms_tab2(this);
					panTab3 = new sms_tab3(this);
					panTab4 = new sms_tab4(this);
				}
				{
					tabSMS.addTab(" Create Batch  ", null, panTab0, null);
					tabSMS.addTab(" Batch Message ", null, panTab2, null);
					tabSMS.addTab(" New Message   ", null, panTab3, null);
					tabSMS.addTab(" Monitoring    ", null, panTab4, null);
				}	
			}
			
			if (blnQuick) {
				tabSMS.setSelectedIndex(2);				
			}
		}
	}
}
