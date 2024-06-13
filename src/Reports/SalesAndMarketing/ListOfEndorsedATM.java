package Reports.SalesAndMarketing;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import DateChooser._JDateChooser;
import Functions.FncReport;
import Functions.UserInfo;

import components._JInternalFrame;

public class ListOfEndorsedATM extends _JInternalFrame implements ActionListener, AncestorListener, _GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162406788303915011L;

	static String title = "List of Endorsed ATM For Processing";
	static Dimension frameSize = new Dimension(500, 250);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JPanel pnlMain;
	private JPanel pnlCriteria2;
	
	private JLabel lblDateFrom;
	private JLabel lblDateTo;
	
	private JPanel pnlSouth;
	private JButton btnPreview;
	
	String company;
	String company_logo;		

	private _JDateChooser dteDateFrom;
	private _JDateChooser dateDateTo;	

	

	public ListOfEndorsedATM() {
		super(title, false, true, false, true);
		initGUI();
	}

	public ListOfEndorsedATM(String title) {
		super(title);
		initGUI();
	}

	public ListOfEndorsedATM(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(560, 137));
		this.setLayout(new BorderLayout());
		this.addAncestorListener(this);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain, BorderLayout.WEST);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setPreferredSize(new java.awt.Dimension(557, 246));
			{				
				{
					pnlCriteria2 = new JPanel(new GridLayout(1, 2, 3, 3));
					pnlMain.add(pnlCriteria2, BorderLayout.NORTH);					
					pnlCriteria2.setPreferredSize(new java.awt.Dimension(546, 66));
					pnlCriteria2.setBorder(components.JTBorderFactory.createTitleBorder("Endorsement Date"));

					{
						lblDateFrom = new JLabel("Date From   ", JLabel.TRAILING);
						pnlCriteria2.add(lblDateFrom, BorderLayout.CENTER);
						lblDateFrom.setEnabled(true);							
					}
					{
						dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria2.add(dteDateFrom, BorderLayout.CENTER);						
						dteDateFrom.setDate(null);
						dteDateFrom.setEnabled(true);
						dteDateFrom.setDate(Calendar.getInstance().getTime());
					}		
								
						lblDateTo = new JLabel("Date To   ", JLabel.TRAILING);
						pnlCriteria2.add(lblDateTo);
						lblDateTo.setEnabled(true);	
					}
					{
						dateDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
						pnlCriteria2.add(dateDateTo, BorderLayout.EAST);
						dateDateTo.setBounds(485, 7, 125, 21);
						dateDateTo.setDate(null);
						dateDateTo.setEnabled(true);
						dateDateTo.setDate(Calendar.getInstance().getTime());
					}
			}				
			{				
				pnlSouth = new JPanel(new GridLayout(1, 2,5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setPreferredSize(new Dimension(300, 30));
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setAlignmentX(0.5f);
					btnPreview.setAlignmentY(0.5f);
					btnPreview.setMaximumSize(new Dimension(100, 30));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.addActionListener(this);
				}
			}
		}
		/*this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(lookupCompany, lookupProject,  
				 dteDateFrom, dateDateTo, btnPreview));*/
		this.setBounds(0, 0, 560, 137);  //174
	}
	
	@Override
	public void ancestorAdded(AncestorEvent event) {
		
	}

	@Override
	public void ancestorMoved(AncestorEvent event) { }

	@Override
	public void ancestorRemoved(AncestorEvent event) { }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Preview")){		
			String criteria = "";		
			String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());		
		
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			mapParameters.put("prepared_name", UserInfo.FullName);
			mapParameters.put("date_from", dteDateFrom.getDate());	
			mapParameters.put("date_to", dateDateTo.getDate());	

			FncReport.generateReport("/Reports/rptListOfEndorsedATM.jasper", reportTitle, "", mapParameters);
		}
		
		if(e.getActionCommand().equals("Cancel")){		
			dteDateFrom.setDate(Calendar.getInstance().getTime());
			dateDateTo.setDate(Calendar.getInstance().getTime());
		}
		
	}
	
	

	
}
