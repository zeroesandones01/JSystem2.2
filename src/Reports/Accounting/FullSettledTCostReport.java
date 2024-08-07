package Reports.Accounting;


import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;


import DateChooser._JDateChooser;
import Functions.FncFocusTraversalPolicy;
import Functions.FncGlobal;
import Functions.FncReport;


import components._JInternalFrame;

public class FullSettledTCostReport extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 5159650933602942626L;

	static String title = "Full Settled TCost Report";
	Dimension frameSize = new Dimension(400, 160);
	Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
	Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

	private JXPanel pnlMain;


	private JXPanel pnlSouth;
	private JButton btnPreview;

	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	private _JDateChooser dteDateFrom;
	private _JDateChooser dteDateTo;

	private JXPanel pnlNorth;
	private JXPanel pnlNorthLabel;
	private JXPanel pnlNorthComponents;
	private JXPanel pnlNorthLookUp;



	public FullSettledTCostReport() {
		super(title, false, true, true, true);
		initGUI();
	}

	public FullSettledTCostReport(String title) {
		super(title);
		initGUI();
	}

	public FullSettledTCostReport(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		//this.setPreferredSize(frameSize);
		
		{
			pnlMain = new JXPanel(new BorderLayout(5, 5));
			getContentPane().add(pnlMain);
			pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
			{
				pnlNorth = new JXPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(FncGlobal.lineBorder);
				pnlNorth.setPreferredSize(new Dimension(0, 80));
				{
					pnlNorthLabel = new JXPanel (new GridLayout(2, 1, 3, 3));
					pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
					pnlNorthLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
					pnlNorthLabel.setPreferredSize(new Dimension(100, 0));
					
					{
						JXLabel lblDateFrom = new JXLabel("Date From");
						pnlNorthLabel.add(lblDateFrom);
					}
					{
						JXLabel lblDateTo = new JXLabel("Date To");
						pnlNorthLabel.add(lblDateTo);
					}
				}
				{
					pnlNorthComponents = new JXPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlNorthComponents, BorderLayout.CENTER);
					{
						pnlNorthLookUp = new JXPanel(new GridLayout(2, 1, 3, 3));
						pnlNorthComponents.add(pnlNorthLookUp, BorderLayout.CENTER);
						pnlNorthLookUp.setBorder(new EmptyBorder(5, 5, 5, 5));
						pnlNorthLookUp.setPreferredSize(new Dimension(50, 0));
						
						{
							dteDateFrom = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlNorthLookUp.add(dteDateFrom);	
							dteDateFrom.setDate(null);
							dteDateFrom.setEnabled(true);
							dteDateFrom.setDate(null);
						}
						{
							dteDateTo = new _JDateChooser("MM/dd/yyyy", "##/##/##", '_');
							pnlNorthLookUp.add(dteDateTo);	
							dteDateTo.setDate(null);
							dteDateTo.setEnabled(true);
							dteDateTo.setDate(null);
						}
						
					}
					
				}	
			}
			{
				pnlSouth = new JXPanel(new GridLayout(1,7,3,3));
				pnlSouth = new JXPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setBorder(new EmptyBorder(5, 5, 5, 5));
				pnlSouth.setPreferredSize(new Dimension (0,40));
				
				{
					JPanel pnlSouthWest = new JPanel(new BorderLayout(5, 5));   
					pnlSouth.add(pnlSouthWest, BorderLayout.WEST); 
					pnlSouthWest.setPreferredSize(new Dimension(140, 0)); 
				}
				{
					JPanel pnlSouthCenter = new JPanel(new BorderLayout(5, 5)); 
					pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER); 
					
					{
						btnPreview = new JButton("Preview");
						pnlSouth.add(btnPreview);
						btnPreview.setActionCommand("preview");
						btnPreview.setAlignmentX(0.5f);
						btnPreview.setAlignmentY(0.5f);
						btnPreview.setMaximumSize(new Dimension(180, 0));
						btnPreview.setMnemonic(KeyEvent.VK_P);
						btnPreview.addActionListener(this);
					}
				}
				{
					JPanel pnlSoutEast = new JPanel(new BorderLayout(5, 5)); 
					pnlSouth.add(pnlSoutEast, BorderLayout.EAST); 
					pnlSoutEast.setPreferredSize(new Dimension(140, 0)); 
				}
			}
		}

		this.setFocusTraversalPolicy(new FncFocusTraversalPolicy(dteDateFrom, dteDateTo, btnPreview));
	}

	
	private void previewMonthly() {
		
		String criteria = "Full Settled TCost Report";		
		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
		Map<String, Object> mapFullSettledTCostMonthly = new HashMap<String, Object>();
		
		
		mapFullSettledTCostMonthly.put("date_from", dteDateFrom.getDate());
		mapFullSettledTCostMonthly.put("date_to", dteDateTo.getDate());
		
		
		FncReport.generateReport("/Reports/rptFullSettledTCostMonthly.jasper", reportTitle, null, mapFullSettledTCostMonthly);
		
	}
	
//	private void previewWithTCostPayment() {
//		
//		String criteria = "Full Settled TCost Report";		
//		String reportTitle = String.format("%s (%s)", title.replace(" Report", ""), criteria.toUpperCase());
//		Map<String, Object> mapFullSettledWithTCostPayment = new HashMap<String, Object>();
//		
//
//		mapFullSettledWithTCostPayment.put("date_from", dteDateFrom.getDate());
//		mapFullSettledWithTCostPayment.put("date_to", dteDateTo.getDate());
//
//		
//		FncReport.generateReport("/Reports/rptFullSettledWithTCostPayment.jasper", reportTitle, null, mapFullSettledWithTCostPayment);
//		
//	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if(action.equals("preview")){
			
			previewMonthly();
//			previewWithTCostPayment();
			
		}
	}
}
