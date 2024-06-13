package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;

import DateChooser._JDateChooser;
import Functions.FncLookAndFeel;
import components.JTBorderFactory;
import interfaces._GUI;

public class panCard_unitinfostatus extends JXPanel implements _GUI {

	private static final long serialVersionUID = -890199439713368668L;
	@SuppressWarnings("unused")
	private CARD main_card;
	static Dimension size = new Dimension(738, 351);
	
	public JTextArea txtTechDesc; 
	
	public JTextField txtLotArea; 
	public JTextField txtFloorArea; 
	public JTextField txtPreassignedColor; 
	public JTextField txtPreferredColor;  
	public JTextField txtTCT; 
	public JTextField txtBOIStatus; 
	
	public _JDateChooser dteHouseConstructed; 
	public _JDateChooser dteTurnOver; 
	public _JDateChooser dteWithNTC; 
	public _JDateChooser dteMovedIn; 
	public _JDateChooser dteMovedOut;
	
	private Font fontPlainSanSer11 = new Font("SansSerif", Font.PLAIN, 10);
	
	public panCard_unitinfostatus(CARD card) {
		this.main_card = card;
		initGUI(); 
	}

	public panCard_unitinfostatus(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		initGUI();
	}

	public panCard_unitinfostatus(LayoutManager layout) {
		super(layout);
		initGUI();
	}

	public panCard_unitinfostatus(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		initGUI();
	}

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	public void initGUI() {
		this.setLayout(new BorderLayout(5, 5));
		this.setPreferredSize(size);
		
		JXPanel panMain = new JXPanel(new GridLayout(1, 2, 5, 5));
		this.add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			{
				JXPanel panDiv1 = new JXPanel(new BorderLayout(5, 5)); 
				panMain.add(panDiv1, BorderLayout.CENTER); 
				panDiv1.setBorder(JTBorderFactory.createTitleBorder("", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
				{
					txtTechDesc = new JTextArea(""); 
					txtTechDesc.setEditable(true); 
					txtTechDesc.setFont(fontPlainSanSer11);
					txtTechDesc.setLineWrap(true);
					txtTechDesc.setWrapStyleWord(true);
					txtTechDesc.setEditable(false);
					
					JScrollPane scrTechDesc = new JScrollPane(txtTechDesc);
					panDiv1.add(scrTechDesc, BorderLayout.CENTER); 
				}
			}
			{
				JXPanel panDiv2 = new JXPanel(new BorderLayout(5, 5));
				panMain.add(panDiv2, BorderLayout.CENTER);
				{
					{
						JXPanel panLabel = new JXPanel(new GridLayout(11, 1, 20, 5)); 
						panDiv2.add(panLabel, BorderLayout.LINE_START); 
						panLabel.setPreferredSize(new Dimension(175, 0));
						{
							{
								JLabel lblLotArea = new JLabel("Lot Area"); 
								panLabel.add(lblLotArea); 
								lblLotArea.setHorizontalAlignment(JLabel.RIGHT);
							}
							{
								JLabel lblFloorArea = new JLabel("Floor Area"); 
								panLabel.add(lblFloorArea); 
								lblFloorArea.setHorizontalAlignment(JLabel.RIGHT);
							}
							{
								JLabel lblPreAssignedColorScheme = new JLabel("Pre-assigned Color Scheme"); 
								panLabel.add(lblPreAssignedColorScheme); 
								lblPreAssignedColorScheme.setHorizontalAlignment(JLabel.RIGHT);
							}
							{
								JLabel lblPreferredColorScheme = new JLabel("Preferred Color Scheme"); 
								panLabel.add(lblPreferredColorScheme); 
								lblPreferredColorScheme.setHorizontalAlignment(JLabel.RIGHT);
							}
							{
								JLabel lblHouseConstructed = new JLabel("House Constructed"); 
								panLabel.add(lblHouseConstructed); 
								lblHouseConstructed.setHorizontalAlignment(JLabel.RIGHT);
							}
							{
								JLabel lblTurnOver = new JLabel("Turn Over"); 
								panLabel.add(lblTurnOver); 
								lblTurnOver.setHorizontalAlignment(JLabel.RIGHT);
							}
							{
								JLabel lblWithNTC = new JLabel("With NTC"); 
								panLabel.add(lblWithNTC); 
								lblWithNTC.setHorizontalAlignment(JLabel.RIGHT);
							}
							{
								JLabel lblMovedIn = new JLabel("Moved in"); 
								panLabel.add(lblMovedIn); 
								lblMovedIn.setHorizontalAlignment(JLabel.RIGHT);
							}
							{
								JLabel lblMovedOut = new JLabel("Moved out"); 
								panLabel.add(lblMovedOut); 
								lblMovedOut.setHorizontalAlignment(JLabel.RIGHT);
							}
							{
								JLabel lblTCTNo = new JLabel("TCT No."); 
								panLabel.add(lblTCTNo); 
								lblTCTNo.setHorizontalAlignment(JLabel.RIGHT);
							}
							{
								JLabel lblBOIStatus = new JLabel("BOI Status"); 
								panLabel.add(lblBOIStatus); 
								lblBOIStatus.setHorizontalAlignment(JLabel.RIGHT);
							}
						}
					}
					{
						JXPanel panValue = new JXPanel(new GridLayout(11, 1, 5, 5)); 
						panDiv2.add(panValue, BorderLayout.CENTER); 
						{
							{
								txtLotArea = new JTextField();
								panValue.add(txtLotArea);
								txtLotArea.setEditable(false);
								txtLotArea.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								txtFloorArea = new JTextField();
								panValue.add(txtFloorArea);
								txtFloorArea.setEditable(false);
								txtFloorArea.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								txtPreassignedColor = new JTextField();
								panValue.add(txtPreassignedColor);
								txtPreassignedColor.setEditable(false);
								txtPreassignedColor.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								txtPreferredColor = new JTextField();
								panValue.add(txtPreferredColor);
								txtPreferredColor.setEditable(false);
								txtPreferredColor.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								dteHouseConstructed = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panValue.add(dteHouseConstructed);
								dteHouseConstructed.getCalendarButton().setVisible(false);
							}
							{
								dteTurnOver = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panValue.add(dteTurnOver);
								dteTurnOver.getCalendarButton().setVisible(false);
							}
							{
								dteWithNTC = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panValue.add(dteWithNTC);
								dteWithNTC.getCalendarButton().setVisible(false);
							}
							{
								dteMovedIn = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panValue.add(dteMovedIn);
								dteMovedIn.getCalendarButton().setVisible(false);
							}
							{
								dteMovedOut = new _JDateChooser("MM/dd/yyyy", "##/##/####", '_');
								panValue.add(dteMovedOut);
								dteMovedOut.getCalendarButton().setVisible(false);
							}
							{
								txtTCT = new JTextField();
								panValue.add(txtTCT);
								txtTCT.setEditable(false);
								txtTCT.setHorizontalAlignment(JTextField.CENTER);
							}
							{
								txtBOIStatus = new JTextField();
								panValue.add(txtBOIStatus);
								txtBOIStatus.setEditable(false);
								txtBOIStatus.setHorizontalAlignment(JTextField.CENTER);
							}
						}
					}
				}
			}
		}
	}
}
