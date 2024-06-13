/**
 * 
 */
package Admin;

import interfaces._GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import Functions.SpringUtilities;
import Lookup._JLookup;
import components._JInternalFrame;
import components._JXTextField;

/**
 * @author John Lester Fatallo
 */
public class AddEditPF_Entries extends _JInternalFrame implements _GUI, ActionListener {

	private static final long serialVersionUID = 485242873096106295L;
	Dimension frameSize = new Dimension(770, 500);
	static String title = "Add/Edit PF Entries";
	Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY);
	Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	private JPanel pnlMain;

	private JPanel pnlNorth;
	
	private JPanel pnlPFlabel;
	private JLabel lblPFNo;
	private JPanel pnlPFNo;
	private _JLookup lookupPF;
	private JLabel lblPFDesc;
	private JPanel pnlPFDesc;
	private _JXTextField txtPFDesc;
	private JLabel lblStatus;
	private JPanel pnlStatus;
	private JComboBox cmbStatus;

	private JPanel pnlCenter;
	private JScrollPane scrollPFTable;

	private JPanel pnlSouth;
	private JButton btnAddNew;
	private JButton btnEdit;
	private JButton btnAddNewLine;
	private JButton btnRemoveLine;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnPreviewPF;

	public AddEditPF_Entries() {
		super(title, false, true, false, true);
		initGUI();
	}

	public AddEditPF_Entries(String title) {
		super(title, false, true, false, true);;
		initGUI();
	}

	public AddEditPF_Entries(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, false, true, false, true);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(frameSize);
		this.setLayout(new BorderLayout());
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			pnlMain.setBorder(EMPTY_BORDER);
			this.add(pnlMain, BorderLayout.CENTER);
			{
				pnlNorth = new JPanel();
				pnlMain.add(pnlNorth, BorderLayout.NORTH);
				pnlNorth.setBorder(components.JTBorderFactory.createTitleBorder("PF Details"));
				//pnlNorth.setLayout(new GridLayout(1, 4, 3, 3));
				pnlNorth.setLayout(new SpringLayout());
				//pnlNorth.setPreferredSize(new Dimension(0, 20));
				{
					lblPFNo = new JLabel("PF No.", JLabel.TRAILING);
					pnlNorth.add(lblPFNo);
				}
				{
					pnlPFNo = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlPFNo);
					{
						lookupPF = new _JLookup(null, "Select", 0);
						pnlPFNo.add(lookupPF);
						lookupPF.setPreferredSize(new Dimension(20, 20));
					}
				}
				{
					lblPFDesc = new JLabel("PF Desc");
					pnlNorth.add(lblPFDesc);
				}
				{
					pnlPFDesc = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlPFDesc);
					{
						txtPFDesc = new _JXTextField("PF Description");
						pnlPFDesc.add(txtPFDesc);
						txtPFDesc.setPreferredSize(new Dimension(200, 0));
					}
				}
				{
					lblStatus = new JLabel("Status");
					pnlNorth.add(lblStatus);
				}
				{
					pnlStatus = new JPanel(new BorderLayout(5, 5));
					pnlNorth.add(pnlStatus);
					{
						cmbStatus = new JComboBox();
						pnlStatus.add(cmbStatus);
						cmbStatus.setPreferredSize(new Dimension(50, 0));
					}
				}
				SpringUtilities.makeCompactGrid(pnlNorth, 1, 6, 3, 3, 3, 3, false);
				//SpringUtilities.makeCompactGrid(pnlNorthUpper, 1, 2, 2, 2, 2, 2, false);
			}
			{
				pnlCenter = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlCenter, BorderLayout.CENTER);
				{
					scrollPFTable = new JScrollPane();
				}
			}
			{
				pnlSouth = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlSouth, BorderLayout.SOUTH);
				pnlSouth.setLayout(new GridLayout(1, 7, 3, 3));
				//pnlSouth.setBorder(EMPTY_BORDER);
				{
					btnAddNew = new JButton("Add New");
					pnlSouth.add(btnAddNew);
					btnAddNew.setActionCommand(btnAddNew.getText());
					btnAddNew.setMnemonic(KeyEvent.VK_N);
					btnAddNew.addActionListener(this);
				}
				{
					btnEdit = new JButton("Edit");
					pnlSouth.add(btnEdit);
					btnEdit.setActionCommand(btnEdit.getText());
					btnEdit.setMnemonic(KeyEvent.VK_E);
					btnEdit.addActionListener(this);
				}
				{
					btnAddNewLine = new JButton("Add New Line");
					pnlSouth.add(btnAddNewLine);
					btnAddNewLine.setActionCommand(btnAddNewLine.getText());
					btnAddNewLine.setMnemonic(KeyEvent.VK_A);
					btnAddNewLine.addActionListener(this);
				}
				{
					btnRemoveLine = new JButton("Remove Line");
					pnlSouth.add(btnRemoveLine);
					btnRemoveLine.setActionCommand(btnRemoveLine.getText());
					btnRemoveLine.setMnemonic(KeyEvent.VK_R);
					btnRemoveLine.addActionListener(this);
				}
				{
					btnSave = new JButton("Save");
					pnlSouth.add(btnSave);
					btnSave.setActionCommand(btnSave.getText());
					btnSave.setMnemonic(KeyEvent.VK_S);
					btnSave.addActionListener(this);
				}
				{
					btnCancel = new JButton("Cancel");
					pnlSouth.add(btnCancel);
					btnCancel.setActionCommand(btnCancel.getText());
					btnCancel.setMnemonic(KeyEvent.VK_C);
					btnCancel.addActionListener(this);
				}
				{
					btnPreviewPF = new JButton("Preview PF");
					pnlSouth.add(btnPreviewPF);
					btnPreviewPF.setActionCommand(btnPreviewPF.getText());
					btnPreviewPF.setMnemonic(KeyEvent.VK_R);
					btnPreviewPF.addActionListener(this);
				}
			}
		}
		btnState(true, true, false, false, false, false, true);
	}//END OF INIT GUI

	
	private void btnState(Boolean sAddNew, Boolean sEdit, Boolean sAddNewLine, Boolean sRemoveLine, Boolean sSave, Boolean sCancel, Boolean sPreview){
		btnAddNew.setEnabled(sAddNew);
		btnEdit.setEnabled(sEdit);
		btnAddNewLine.setEnabled(sAddNewLine);
		btnRemoveLine.setEnabled(sRemoveLine);
		btnSave.setEnabled(sSave);
		btnCancel.setEnabled(sCancel);
		btnPreviewPF.setEnabled(sPreview);
	}
	
	
	
	private String sqlPF(){
		return "";
	}
	
	
	
}
