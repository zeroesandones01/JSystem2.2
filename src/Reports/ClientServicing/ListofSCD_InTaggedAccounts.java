package Reports.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import DateChooser._JDateChooser;
import Functions.FncGlobal;
import Functions.FncReport;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class ListofSCD_InTaggedAccounts extends _JInternalFrame implements ActionListener, _GUI {

	private static final long serialVersionUID = 6802352057413166089L;

	static String title = "List of SCD In Tagged Accounts";
	static Dimension frameSize = new Dimension(370, 175);
	static Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JPanel pnlLabel;
	private JLabel lblSCDInFrom;
	private JLabel lblSCDInTo;
	private JLabel lblSortBy;
	private JPanel pnlComponents;
	private _JDateChooser dteSCDInFrom;
	private _JDateChooser dteSCDInTo;
	private JComboBox cmbSortBy;

	private JPanel pnlCenterMain;

	private JPanel pnlSouth;
	private JButton btnPreview;
	private JButton btnCancel;

	public ListofSCD_InTaggedAccounts() {
		super(title, true, true, true, true);
		initGUI();
	}

	public ListofSCD_InTaggedAccounts(String title) {
		super(title);
		initGUI();
	}

	public ListofSCD_InTaggedAccounts(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				JXPanel pnlCenter = new JXPanel(new GridBagLayout());
				pnlMain.add(pnlCenter,BorderLayout.CENTER);
				{
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.fill = GridBagConstraints.BOTH;
					gbc.ipady = 40;
					{
						gbc.weightx = 1;
						gbc.weighty = 1.5;
						
						gbc.gridx = 0; 
						gbc.gridy = 0; 
						
						JPanel pnlBagOne = new JPanel(new GridBagLayout());
						pnlCenter.add(pnlBagOne, gbc);
						pnlBagOne.setBorder(JTBorderFactory.createTitleBorder(""));
						{
							GridBagConstraints bagOne = new GridBagConstraints();
							bagOne.fill = GridBagConstraints.BOTH;
							bagOne.insets = new Insets(5, 1, 5, 1);
							bagOne.ipady = 20;
							{
								bagOne.weightx = 0.1;
								bagOne.weighty = 1;
								
								bagOne.gridx = 0;
								bagOne.gridy = 0;
								
								lblSCDInFrom = new JLabel("Date From:");
								pnlBagOne.add(lblSCDInFrom,bagOne);
								lblSCDInFrom.setFont(FncGlobal.sizeLabel);
							}
							
							{
								bagOne.weightx = 1;
								bagOne.weighty = 1;
								
								bagOne.gridx = 1;
								bagOne.gridy = 0;
								
								dteSCDInFrom = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlBagOne.add(dteSCDInFrom, bagOne);
								dteSCDInFrom.setFont(FncGlobal.sizeTextValue);
							}
							
							{
								bagOne.weightx = 0.1;
								bagOne.weighty = 1;
								
								bagOne.gridx = 0;
								bagOne.gridy = 1;
								
								lblSCDInTo = new JLabel("Date To:");
								pnlBagOne.add(lblSCDInTo,bagOne);
								lblSCDInTo.setFont(FncGlobal.sizeLabel);
							}
							
							{
								bagOne.weightx = 0;
								bagOne.weighty = 1;
								
								bagOne.gridx = 1;
								bagOne.gridy = 1;
								
								dteSCDInTo = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								pnlBagOne.add(dteSCDInTo, bagOne);
								dteSCDInTo.setFont(FncGlobal.sizeTextValue);
							}
						}
					}
				}
			}
			
			{
				pnlSouth = new JPanel(new GridLayout(1, 3, 5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				{
					pnlSouth.add(Box.createHorizontalBox());
					pnlSouth.add(Box.createHorizontalBox());
					
					
				}
				{
					btnPreview = new JButton("Preview");
					pnlSouth.add(btnPreview);
					btnPreview.setActionCommand("Preview");
//					btnPreview.setAlignmentX(0.5f);
//					btnPreview.setAlignmentY(0.5f);
//					btnPreview.setPreferredSize(new Dimension(150, 25));
					btnPreview.setMnemonic(KeyEvent.VK_P);
					btnPreview.setFont(FncGlobal.sizeControls);
					btnPreview.addActionListener(this);
				}
				
			}
		}
	}

	private void previewSCDInTagged(){
		Map<String, Object> mapParameters = new HashMap<String, Object>();
		mapParameters.put("logo", this.getClass().getClassLoader().getResourceAsStream("Images/"+ "cenq_logo.jpg"));
		mapParameters.put("date_from", dteSCDInFrom.getTimestamp());
		mapParameters.put("date_to", dteSCDInTo.getTimestamp());

		FncReport.generateReport("/Reports/rptListofClientsTaggedSCDIn.jasper", "List of Clients Tagged SCD In", mapParameters);
	}

	private void cancel(){
		dteSCDInFrom.setDate(null);
		dteSCDInTo.setDate(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("Preview")){
			previewSCDInTagged();
		}

		if(actionCommand.equals("Cancel")){
			cancel();
		}
	}

}
