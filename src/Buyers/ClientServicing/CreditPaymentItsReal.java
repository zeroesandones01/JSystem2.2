package Buyers.ClientServicing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import FormattedTextField._JXFormattedTextField;
import Functions.FncLookAndFeel;
import Lookup.LookupEvent;
import Lookup.LookupListener;
import Lookup._JLookup;
import components.JTBorderFactory;
import components._JInternalFrame;
import interfaces._GUI;

public class CreditPaymentItsReal extends _JInternalFrame implements _GUI, ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2744337878646005023L;
	static String title = "Credit Payment (Its Real)";
	Dimension frameSize = new Dimension(500, 250);
	
	static Border lineRed = BorderFactory.createLineBorder(Color.RED);
	static Border lineGray = BorderFactory.createLineBorder(Color.GRAY);

	private JPanel pnlNorth;
	private JPanel pnlNorthLabel;
	private JLabel lblName;
	private JLabel lblProject;
	private JLabel lblUnit;
	private JLabel lblSeq;
	private JLabel lblAmount;
	private JLabel lblParticular;
	
	private JPanel pnlNorthComp;
	private _JLookup txtID;
	
	private JTextField txtProjID;
	private JTextField txtUnitID;
	private JTextField txtName;
	private JTextField txtProject;
	private JTextField txtUnit;
	private JTextField txtSeq;
	private _JLookup lookupParticular;
	private JTextField txtPartDesc;
	private _JXFormattedTextField txtAmount;
	
	private JPanel pnlSouth;
	private JButton btnSave;
	private JButton btnCancel;
	
	public CreditPaymentItsReal() {
		super(title, true, true, false, true);
		initGUI();
	}

	public CreditPaymentItsReal(String title) {
		super(title, true, true, false, true);
		initGUI();
	}

	public CreditPaymentItsReal(String title, boolean resizable, boolean closable, boolean maximizable,
			boolean iconifiable) {
		super(title, true, true, false, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		
		JXPanel pnlMain = new JXPanel(new BorderLayout(5, 5));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			pnlNorth = new JPanel(new BorderLayout(5, 5));
			pnlMain.add(pnlNorth, BorderLayout.CENTER);
			//pnlNorth.setPreferredSize(new Dimension(0, 120));
			pnlNorth.setBorder(JTBorderFactory.createTitleBorder("Client Details", FncLookAndFeel.systemFont_Bold.deriveFont(10f)));
			{
				pnlNorthLabel = new JPanel(new GridLayout(5, 1, 0, 0));
				pnlNorth.add(pnlNorthLabel, BorderLayout.WEST);
				pnlNorthLabel.setPreferredSize(new Dimension(100, 0));
				{
					lblName = new JLabel("Client");
					pnlNorthLabel.add(lblName);
				}
				{
					lblProject = new JLabel("Proj.");
					pnlNorthLabel.add(lblProject);
				}
				{
					lblUnit = new JLabel("Unit");
					pnlNorthLabel.add(lblUnit);
				}
				{
					lblSeq = new JLabel("Seq. No");
					pnlNorthLabel.add(lblSeq);
				}
				{
					lblParticular = new JLabel("Particular");
					pnlNorthLabel.add(lblParticular);
				}
			}
			{
				pnlNorthComp = new JPanel(new GridLayout(5, 1, 0, 0));
				pnlNorth.add(pnlNorthComp, BorderLayout.CENTER);
				{
					JPanel pnlClient = new JPanel(new BorderLayout(5, 5));
					pnlNorthComp.add(pnlClient);
					{
						txtID = new _JLookup(null, "Client", 0);
						pnlClient.add(txtID, BorderLayout.WEST);
						txtID.setPreferredSize(new Dimension(70, 0));
						txtID.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								// TODO Auto-generated method stub
								
							}
						});
					}
					{
						txtName = new JTextField();
						pnlClient.add(txtName, BorderLayout.CENTER);
						txtName.setEditable(false);
					}
				}
				{
					JPanel pnlProj = new JPanel(new BorderLayout(5, 5));
					pnlNorthComp.add(pnlProj);
					{
						txtProjID = new JTextField();
						pnlProj.add(txtProjID, BorderLayout.WEST);
						txtProjID.setPreferredSize(new Dimension(50, 0));
						txtProjID.setEditable(false);
					}
					{
						txtProject = new JTextField();
						pnlProj.add(txtProject, BorderLayout.CENTER);
						txtProject.setEditable(false);
					}
				}
				{
					JPanel pnlUnit = new JPanel(new BorderLayout(5, 5));
					pnlNorthComp.add(pnlUnit);
					{
						txtUnitID = new JTextField();
						pnlUnit.add(txtUnitID, BorderLayout.WEST);
						txtUnitID.setPreferredSize(new Dimension(50, 0));
						txtUnitID.setEditable(false);
					}
					{
						txtUnit = new JTextField();
						pnlUnit.add(txtUnit, BorderLayout.CENTER);
						txtUnit.setEditable(false);
					}
				}
				{
					JPanel pnlSeq = new JPanel(new BorderLayout(5, 5));
					pnlNorthComp.add(pnlSeq);
					{
						txtSeq = new JTextField();
						pnlSeq.add(txtSeq, BorderLayout.WEST);
						txtSeq.setPreferredSize(new Dimension(50, 0));
						txtSeq.setEditable(false);
					}
					{
						JPanel pnlAmount = new JPanel(new BorderLayout(5, 5));
						pnlSeq.add(pnlAmount, BorderLayout.EAST);
						pnlAmount.setPreferredSize(new Dimension(300, 0));
						{
							lblAmount = new JLabel("Amount");
							pnlAmount.add(lblAmount, BorderLayout.WEST);
							lblAmount.setPreferredSize(new Dimension(100, 0));
						}
						{
							txtAmount = new _JXFormattedTextField(JXTextField.RIGHT);
							pnlAmount.add(txtAmount, BorderLayout.CENTER);
							txtAmount.setFormatterFactory(_JXFormattedTextField.DECIMAL);
							txtAmount.setEditable(false);
							txtAmount.setValue(new BigDecimal("0.00"));
						}
					}
				}
				{
					JPanel pnlParticular = new JPanel(new BorderLayout(5, 5));
					pnlNorthComp.add(pnlParticular);
					{
						lookupParticular = new _JLookup(null, "Particular", 0);
						pnlParticular.add(lookupParticular, BorderLayout.WEST);
						lookupParticular.setPreferredSize(new Dimension(50, 0));
						lookupParticular.addLookupListener(new LookupListener() {
							
							@Override
							public void lookupPerformed(LookupEvent event) {
								// TODO Auto-generated method stub
								
							}
						});
					}
					{
						txtPartDesc = new JTextField();
						pnlParticular.add(txtPartDesc, BorderLayout.CENTER);
						txtPartDesc.setEditable(false);
					}
				}
			}
			
		}
		{
			pnlSouth = new JPanel(new GridLayout(1, 7, 3, 3));
			pnlMain.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setPreferredSize(new Dimension(0, 30));
			{
				pnlSouth.add(Box.createHorizontalBox());
				pnlSouth.add(Box.createHorizontalBox());
				pnlSouth.add(Box.createHorizontalBox());
				pnlSouth.add(Box.createHorizontalBox());
				pnlSouth.add(Box.createHorizontalBox());
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand("Save");
					
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand("Cancel");
				}
				
			}
		}
	}
	
	

}
