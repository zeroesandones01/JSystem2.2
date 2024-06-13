package Buyers.CreditandCollections;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXPanel;

import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JTabbedPane;
import components._JXTextField;

public class _CancellationProcessing_New extends _JInternalFrame  implements _GUI, ActionListener,LookupListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String title = "Cancellation Processing";
	public static Dimension frameSize = new Dimension(900, 650);
	//private Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JXPanel pnlNorth;
	private JLabel lblCompany;
	private _JLookup lookupCompany;
	private _JXTextField txtCompany;
	
	private _FPromissoryNoteCommintment methods = new _FPromissoryNoteCommintment();
	
	private _JTabbedPane tabCenter;
	public static pnlCancellation_Active pnlCA;
	public static String co_id;
	public static String company;
	public static String company_logo;
	
	public static pnlPerBatch_Cancel pnlPerBatch;
	
	public _CancellationProcessing_New() { 
	super(title, true, true, true, true);
		initGUI(); 
	}

	public _CancellationProcessing_New(String title) {
		super(title);
	}

	public _CancellationProcessing_New(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) { 
		super(title, resizable, closable, maximizable, iconifiable);
	}
	
	@Override
	public void initGUI() {
		this.setSize(frameSize);
		this.setPreferredSize(frameSize);
		
		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		{
			/*
			JXPanel pnlNorth = new JXPanel();
			pnlMain.add(pnlNorth, BorderLayout.NORTH);
			//pnlNorth.setBorder(LINE_BORDER); 
			pnlNorth.setPreferredSize(new Dimension(778, 100));
		   */
		}
		{

			pnlNorth = new JXPanel(new BorderLayout());
			pnlMain.add(pnlNorth,BorderLayout.NORTH);
			pnlNorth.setPreferredSize(new Dimension(pnlMain.getWidth(), 30));
	
			{
				{

					lblCompany = new JLabel("Company");
					pnlNorth.add(lblCompany,BorderLayout.WEST);
					lblCompany.setPreferredSize(new Dimension(74, 20));
				}
				{
					JPanel pnlCompany = new JPanel(new BorderLayout(3,3)); 
					pnlNorth.add(pnlCompany,BorderLayout.CENTER);
					pnlCompany.setPreferredSize(new Dimension(74, 20));
		
					{
						{

							lookupCompany = new _JLookup("ID", "Company", 0) ; /// XXX lookupCompany 
							pnlCompany.add(lookupCompany,BorderLayout.WEST);
							lookupCompany.addLookupListener(this);
							lookupCompany.setReturnColumn(0);
							lookupCompany.setLookupSQL(methods.getCompany());
							lookupCompany.setPreferredSize(new Dimension(100, 20));
						}
						{
							txtCompany = new _JXTextField();
							pnlCompany.add(txtCompany,BorderLayout.CENTER);
							txtCompany.setEditable(false);
							txtCompany.setPrompt("Company Name");
							txtCompany.setPreferredSize(new Dimension(100, 20)); 
						} 
					}
				}
			} 
			 
			tabCenter = new _JTabbedPane();
			pnlMain.add(tabCenter, BorderLayout.CENTER);
			{
				{
					pnlCA = new pnlCancellation_Active(this); 
					tabCenter.addTab("Cancellation of Active Accounts", pnlCA);
				} 
			}
		}
	}
	
	@Override 
	public void actionPerformed(ActionEvent e) {}
    
	@Override
	public void lookupPerformed(LookupEvent e) { /// XXX lookupPerformed 
		if( e.getSource().equals(lookupCompany)){
			Object[] data = ((_JLookup)e.getSource()).getDataSet();

			if(data != null){

				co_id = data[0].toString();
				company = data[1].toString();
				company_logo = data[3].toString(); 
				txtCompany.setText(company); 
 
				pnlCA.lookupProjectID();
				pnlCA._setEnableComponents(true);
				pnlCA.btnState_PerBatch();
		

			}
		}
	}
}
