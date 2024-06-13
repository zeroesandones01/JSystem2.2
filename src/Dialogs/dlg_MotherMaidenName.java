/**
 * 
 */
package Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import Buyers.ClientServicing.pnlClientInformation;
import Database.pgSelect;
import Functions.FncSystem;
import components.JTBorderFactory;
import components._JDialog;
import interfaces._GUI;

/**
 * @author pc-114l
 */

public class dlg_MotherMaidenName extends _JDialog implements _GUI, ActionListener {

	private static final long serialVersionUID = -1489591348455853039L;
	
	private Dimension SIZE = new Dimension(300, 180);
	private Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
	
	private JPanel pnlMain;
	private JPanel pnlMainLabel;
	private JPanel pnlMainComponent;
	private JTextField txtFirstName;
	private JTextField txtMiddleName;
	private JTextField txtLastName;
	private JButton btnSave;
	private String entity_id;
	private pnlClientInformation pnlCI;
	
	public dlg_MotherMaidenName() {
		initGUI();
	}

	public dlg_MotherMaidenName(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_MotherMaidenName(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_MotherMaidenName(Window owner, String entity_name, String entity_id,
			Integer pbl_id, Integer seq_no, List<Integer> listRecIDs) {
		super(owner, entity_name, entity_id, pbl_id, seq_no, listRecIDs);
		initGUI();
	}

	public dlg_MotherMaidenName(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_MotherMaidenName(Frame owner, String title, pnlClientInformation pnlCI, String entity_id) {
		super(owner, title);
		this.pnlCI = pnlCI;
		initGUI();
		displayMotherMaidenName(entity_id);
	}

	public dlg_MotherMaidenName(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_MotherMaidenName(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_MotherMaidenName(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_MotherMaidenName(Window owner, String title, pnlClientInformation pnlCI) {
		super(owner, title);
		this.pnlCI = pnlCI;
		initGUI();
	}

	public dlg_MotherMaidenName(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_MotherMaidenName(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_MotherMaidenName(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_MotherMaidenName(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_MotherMaidenName(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_MotherMaidenName(Window owner, String title,
			ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}
	
	@Override
	public void initGUI() {
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setMinimumSize(SIZE);
		
		this.getRootPane().registerKeyboardAction(this, "Add", KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		{
			pnlMain = new JPanel(new BorderLayout(3, 3));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlMainLabel = new JPanel(new GridLayout(3, 1, 3, 3));
				pnlMain.add(pnlMainLabel, BorderLayout.WEST);
				{
					JLabel lblFirstName = new JLabel("First Name");
					pnlMainLabel.add(lblFirstName);
				}
				{
					JLabel lblMiddleName = new JLabel("Middle Name");
					pnlMainLabel.add(lblMiddleName);
				}
				{
					JLabel lblLastName = new JLabel("Last Name");
					pnlMainLabel.add(lblLastName);
				}
			}
			{
				pnlMainComponent = new JPanel(new GridLayout(3, 1, 3, 3));
				pnlMain.add(pnlMainComponent, BorderLayout.CENTER);
				{
					txtFirstName = new JTextField();
					pnlMainComponent.add(txtFirstName);
				}
				{
					txtMiddleName = new JTextField();
					pnlMainComponent.add(txtMiddleName);
				}
				{
					txtLastName = new JTextField();
					pnlMainComponent.add(txtLastName);
				}
			}
		}
		{
			JPanel pnlSouth = new JPanel(new BorderLayout(3, 3));
			this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			{
				btnSave = new JButton("Save");
				this.add(btnSave, BorderLayout.SOUTH);
				btnSave.setActionCommand(btnSave.getText());
				btnSave.addActionListener(this);
			}
		}
		this.pack();
	}//XXX END OF INIT GUI
	
	private Boolean toSave(){
		if(txtFirstName.getText().equals("")){
			JOptionPane.showMessageDialog(this, "Please input First name", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(txtLastName.getText().equals("")){
			JOptionPane.showMessageDialog(this, "Please input Last Name", "Save", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private void saveMotherMaidenName(){
		String first_name = txtFirstName.getText().toUpperCase();
		String middle_name = txtMiddleName.getText().toUpperCase();
		String last_name = txtLastName.getText().toUpperCase();
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT sp_save_ci_mother_maiden_name('"+entity_id+"', '"+first_name+"', '"+middle_name+"', '"+last_name+"')";
		db.select(SQL);
		
		FncSystem.out("Display saving of MOther's Maiden Name", SQL);
	}
	
	private void displayMotherMaidenName(String entity_id){
		this.entity_id = entity_id;
		
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT mother_maiden_fname, mother_maiden_mname, mother_maiden_lname FROM rf_entity_mother_maiden_name WHERE entity_id = '"+entity_id+"'";
		db.select(SQL);
		
		FncSystem.out("Display Mother's Maiden Name", SQL);
		
		if(db.isNotNull()){
			txtFirstName.setText((String) db.getResult()[0][0]);
			txtMiddleName.setText((String) db.getResult()[0][1]);
			txtLastName.setText((String) db.getResult()[0][2]);
			
		}else{
			txtFirstName.setText("");
			txtMiddleName.setText("");
			txtLastName.setText("");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Save")){
			if(toSave()){
				if(JOptionPane.showConfirmDialog(this, "Save entry?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					saveMotherMaidenName();
					JOptionPane.showMessageDialog(this, "Entry saved", "Save", JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				}
			}
		}
	}
}
