package Buyers.LegalandLiaisoning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.acl.Owner;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import components._JDialog;
import interfaces._GUI;

public class RevolvingFundsDialog extends _JDialog implements _GUI{

	private static final long serialVersionUID = 6073115292640077480L;
	private String title = "Pcost Description";
	static JTextArea txtAreaDescription;
	
	
	public RevolvingFundsDialog(){
		initGUI();
	}
	public RevolvingFundsDialog(Frame owner,String title){
		super(owner,title);
		initGUI();
		
	}
	
	public void initGUI() {
		
		this.setTitle(title);
		this.setLayout(new BorderLayout(5,5));
		this.setPreferredSize(new Dimension(300,300));
		this.setSize(new Dimension(300,300));
	
		
		
		
		{
			JPanel pnlMain = new JPanel(new BorderLayout(5,5));
			pnlMain.setPreferredSize(new Dimension(20,20));
			this.add(pnlMain,BorderLayout.CENTER);
			txtAreaDescription = new JTextArea();
			pnlMain.add(txtAreaDescription,BorderLayout.CENTER);
			
			JButton btnOk = new JButton("OK");
			pnlMain.add(btnOk,BorderLayout.SOUTH);
			btnOk.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					
				}
			});
		}
	this.pack();	
		
	}
	
	
	public static String descriptionValue(){
		return txtAreaDescription.getText();
		
		
	}
	
	

	
	

}
