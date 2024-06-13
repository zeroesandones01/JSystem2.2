/**
 * 
 */
package Buyers.CreditandCollections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import tablemodel.model_PastDue;
import Functions.FncTables;
import Lookup._JLookup;
import components._JButton;
import components._JInternalFrame;
import components._JRadioButton;
import components._JTableMain;

/**
 * @author PC-112l
 *
 */
public class _PastDueProcessing extends _JInternalFrame {

	/**
	 * 
	 */
	
	public static String title = "Past Due Processing";
	public static Dimension frameSize = new Dimension(600, 500);
	Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private JPanel pnlMain;
	private JPanel pnlNorth;
	private JPanel pnlNorth_North;
	private JPanel pnlNorth_Center;
	private _JRadioButton rbtnPastDue;
	private JLabel lblProject;
	private _JLookup lookupProject;
	private JTextField txtProject;
	private JLabel lblBuyerType;
	private DefaultComboBoxModel cmbBuyerTypeModel;
	private JComboBox cmbBuyerType;
	private DefaultComboBoxModel cmbMonthPDModel;
	private JComboBox cmbMonthPD;
	private JLabel lblMonthPD;
	private _JRadioButton rbtnBuyBack;
	private _JRadioButton rbtnPCancel;
	private JPanel pnlNorth_South;
	private _JButton btnGenerate;
	private ButtonGroup btngrpProcess = new ButtonGroup();
	private JPanel pnlCenter;
	private model_PastDue modelPastDue;
	private _JTableMain tblPastDue;
	private JScrollPane scrollPastDue;
	private JList rowHeadePastDue;
	private JPanel pnlSouth;
	private _JButton btnPost;
	private _JButton btnPreview;
	private _JButton btnClear;
	public _PastDueProcessing() {
		super(title, true, true, true, true);
		initGUi();
	}

	/**
	 * @param title
	 */
	public _PastDueProcessing(String title) {
		super(title);
		initGUi();
	}

	/**
	 * @param title
	 * @param resizable
	 * @param closable
	 * @param maximizable
	 * @param iconifiable
	 */
	public _PastDueProcessing(String title, boolean resizable,
			boolean closable, boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
		initGUi();
	}
 
	
	private void initGUi(){
		this.setTitle(title);
		this.setSize(frameSize);
		this.setPreferredSize(new java.awt.Dimension(600, 500));
		getContentPane().setLayout(new BorderLayout());
		{
			pnlMain = new JPanel();
			getContentPane().add(pnlMain,BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			pnlMain.setLayout(new BorderLayout(5,5));
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("Process"));// XXX
				pnlNorth.setPreferredSize(new Dimension(pnlMain.getWidth(), 200));
				pnlNorth.setLayout(new BorderLayout(5,5));
				{
					pnlNorth_North = new JPanel();
					pnlNorth.add(pnlNorth_North, BorderLayout.NORTH);
					pnlNorth_North.setPreferredSize(new Dimension(pnlMain.getWidth(), 30));
					pnlNorth_North.setLayout(new GridLayout(0, 3, 2, 2));
					pnlNorth_North.setBorder(lineBorder);
					
					{
						rbtnPastDue = new _JRadioButton("Past Due");
						pnlNorth_North.add(rbtnPastDue);
						btngrpProcess.add(rbtnPastDue);
					}
					{
						rbtnBuyBack = new _JRadioButton("Buyback from Bank");
						pnlNorth_North.add(rbtnBuyBack);
						btngrpProcess.add(rbtnBuyBack);
						
					}
					{
						rbtnPCancel= new _JRadioButton("Projected for Cancellation");
						pnlNorth_North.add(rbtnPCancel);
						btngrpProcess.add(rbtnPCancel);
					}
					
				}
				{
					pnlNorth_Center = new JPanel();
					pnlNorth.add(pnlNorth_Center, BorderLayout.CENTER);
					pnlNorth_Center.setPreferredSize(new Dimension(pnlMain.getWidth(), 180));
					pnlNorth_Center.setLayout(null);
					pnlNorth_Center.setBorder(lineBorder);
					{
						lblProject = new JLabel("Projects");
						pnlNorth_Center.add(lblProject);
						lblProject.setBounds(4, 10, 100, 22);
						
					}
					{
						lookupProject = new _JLookup("", "Search Projects", 0);
						pnlNorth_Center.add(lookupProject);
						lookupProject.setBounds(122, 10, 80, 22);
						
					}
					{
						txtProject  = new JTextField();
						pnlNorth_Center.add(txtProject);
						txtProject.setBounds(207, 10, 310, 22);
						
					}
					{
						lblBuyerType = new JLabel("Buyer Type");
						pnlNorth_Center.add(lblBuyerType);
						lblBuyerType.setBounds(4, 35, 100, 22);
						
					}
					{
						cmbBuyerTypeModel = new DefaultComboBoxModel(
									new String[] { " In-House Accounts", " Pag-ibig Accounts" });
						cmbBuyerType = new JComboBox();
						pnlNorth_Center.add(cmbBuyerType);
						cmbBuyerType.setModel(cmbBuyerTypeModel);
						cmbBuyerType.setBounds(122, 34, 202, 22);
						cmbBuyerType.setSelectedItem(null);
						
					}
				
					{
						lblMonthPD = new JLabel("Month Past Due");
						pnlNorth_Center.add(lblMonthPD);
						lblMonthPD.setBounds(4, 60, 100, 22);
						
					}
					{
						cmbMonthPDModel = new DefaultComboBoxModel(
									new String[] { " All", " 1", " 2", " > 2", " TR" });
						cmbMonthPD = new JComboBox();
						pnlNorth_Center.add(cmbMonthPD);
						cmbMonthPD.setModel(cmbMonthPDModel);
						cmbMonthPD.setBounds(122,59, 202, 22);
						cmbBuyerType.setSelectedItem(null);
						
					}
				}
				{
					pnlNorth_South = new JPanel();
					pnlNorth.add(pnlNorth_South,BorderLayout.SOUTH);
					pnlNorth_South.setLayout(new BorderLayout());
					pnlNorth_South.setPreferredSize(new Dimension(pnlMain.getWidth(), 40));
					{
						btnGenerate = new _JButton("Generate");
						pnlNorth_South.add(btnGenerate,BorderLayout.CENTER);
						
						
					}
				}
			}
			{
				pnlCenter = new JPanel();
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				pnlCenter.setBorder(components.JTBorderFactory.createTitleBorder("List of "));// XXX
				pnlCenter.setPreferredSize(new Dimension(pnlMain.getWidth(), 200));
				pnlCenter.setLayout(new BorderLayout(5,5));
				{
					scrollPastDue = new JScrollPane();
					pnlCenter.add(scrollPastDue, BorderLayout.CENTER);
					scrollPastDue.setPreferredSize(new Dimension(700, 350));
					scrollPastDue.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					scrollPastDue.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							tblPastDue.clearSelection();
						}
					});
					{

						modelPastDue = new model_PastDue();
						tblPastDue = new _JTableMain(modelPastDue);
						scrollPastDue.setViewportView(tblPastDue);
						tblPastDue.setFillsViewportHeight(false);
						modelPastDue.setEditable(true);
						tblPastDue.packColumns("Policy No.");
						tblPastDue.packColumns("Policy Title");
						tblPastDue.packColumns("Department");
						tblPastDue.getColumnModel().getColumn(3).setPreferredWidth(100);
						tblPastDue.setRowHeight(22);
						tblPastDue.setHorizontalScrollEnabled(true);
					
					}
					{
						rowHeadePastDue = tblPastDue.getRowHeader();
						scrollPastDue.setRowHeaderView(rowHeadePastDue);
						scrollPastDue.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, FncTables.getRowHeader_Header());

					}
				}
				
			}
			{
				pnlSouth = new JPanel();
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setBorder(lineBorder);
				pnlSouth.setPreferredSize(new Dimension(pnlMain.getWidth(), 50));
				pnlSouth.setLayout(new GridLayout(0, 3, 2, 2));
				{
					btnPost = new _JButton("Post");
					pnlSouth.add(btnPost);
					
				}
				{
					btnPreview = new _JButton("Preview");
					pnlSouth.add(btnPreview);
					
				}
							{
					btnClear = new _JButton("Clear");
					pnlSouth.add(btnClear);
					
				}
			}
			
			
		}
	}
}
