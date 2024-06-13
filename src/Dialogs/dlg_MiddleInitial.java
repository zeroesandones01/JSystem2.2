package Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import Database.pgSelect;
import Functions.FncSystem;
import Functions.UserInfo;
import components._JDialog;
import interfaces._GUI;

public class dlg_MiddleInitial extends _JDialog implements _GUI, ActionListener {

	private static final long serialVersionUID = 3655730446339794369L;
	private Dimension SIZE = new Dimension(300, 100);
	private Border lineBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
	private String title = "Middle Initial";
	
	private JPanel pnlMain;
	private JPanel pnlMainLabel;
	private JLabel lblMiddleInitial;
	private JPanel pnlMainComponent;
	private JTextField txtMiddleInitial;
	private JButton btnSave;
	
	private String entity_id = "";
	
	public dlg_MiddleInitial(String entity_id) {
		this.entity_id = entity_id;
		initGUI();
		displayMiddleInitial(entity_id);
		//initializeComponents();
	}

	public dlg_MiddleInitial(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_MiddleInitial(Dialog owner) {
		super(owner);
		initGUI();
	}

	public dlg_MiddleInitial(Window owner, String entity_name, String entity_id, Integer pbl_id, Integer seq_no,
			List<Integer> listRecIDs) {
		super(owner, entity_name, entity_id, pbl_id, seq_no, listRecIDs);
		initGUI();
	}

	public dlg_MiddleInitial(Frame owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_MiddleInitial(Frame owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_MiddleInitial(Dialog owner, boolean modal) {
		super(owner, modal);
		initGUI();
	}

	public dlg_MiddleInitial(Dialog owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_MiddleInitial(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initGUI();
	}

	public dlg_MiddleInitial(Window owner, String title) {
		super(owner, title);
		initGUI();
	}

	public dlg_MiddleInitial(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_MiddleInitial(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_MiddleInitial(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initGUI();
	}

	public dlg_MiddleInitial(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_MiddleInitial(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initGUI();
	}

	public dlg_MiddleInitial(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setTitle(title);
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setMinimumSize(SIZE);
		{
			pnlMain = new JPanel(new BorderLayout(5, 5));
			this.add(pnlMain, BorderLayout.CENTER);
			pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				pnlMainLabel = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlMainLabel, BorderLayout.WEST);
				{
					lblMiddleInitial = new JLabel("Middle Initial");
					pnlMainLabel.add(lblMiddleInitial);
				}
			}
			{
				pnlMainComponent = new JPanel(new BorderLayout(5, 5));
				pnlMain.add(pnlMainComponent, BorderLayout.CENTER);
				{
					txtMiddleInitial = new JTextField();
					pnlMainComponent.add(txtMiddleInitial, BorderLayout.CENTER);
				}
			}
		}
		{
			JPanel pnlSouth = new JPanel(new BorderLayout(5, 5));
			this.add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				btnSave = new JButton("Save");
				pnlSouth.add(btnSave);
				btnSave.setActionCommand(btnSave.getText());
				btnSave.addActionListener(this);
			}
		}
		this.pack();
	}//XXX END OF INIT GUI
	
	private void initializeComponents(){
		txtMiddleInitial.setText("");
	}
	
	private Boolean withMiddleInitial(String entity_id){
		pgSelect db = new pgSelect();
		String SQL = "SELECT EXISTS (SELECT * FROM rf_entity_middle_initial WHERE entity_id = '"+entity_id+"')";
		db.select(SQL);
		
		if(db.isNotNull()){
			return (Boolean) db.getResult()[0][0];
		}else{
			return false;
		}
	}
	
	private void displayMiddleInitial(String entity_id){
		pgSelect db = new pgSelect();
		
		String SQL = "SELECT middle_initial FROM rf_entity_middle_initial WHERE entity_id = '"+entity_id+"'";
		db.select(SQL);
		
		if(db.isNotNull()){
			txtMiddleInitial.setText((String) db.getResult()[0][0]);
		}else{
			txtMiddleInitial.setText("");
		}
	}
	
	private void saveMiddleInitial(String entity_id){
		String middle_initial = txtMiddleInitial.getText().toUpperCase().trim();
		
		pgSelect db = new pgSelect();
		String SQL = "SELECT sp_save_ci_middle_initial('"+entity_id+"', '"+middle_initial+"', '"+UserInfo.EmployeeCode+"')";
		db.select(SQL);
		
		FncSystem.out("Display SQL for Middle Initial Saving", SQL);
	}
	
	private void exit(){
		this.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Save")){
			//if(toSave()){
				if(JOptionPane.showConfirmDialog(this, "Save entry?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					saveMiddleInitial(entity_id);;
					JOptionPane.showMessageDialog(this, "Entry saved", "Save", JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				}
			//}
		}
	}
	
}
