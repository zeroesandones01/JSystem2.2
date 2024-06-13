package Dialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.JTBorderFactory;
import interfaces._GUI;

public class dlg_dcrf_attachment extends JDialog implements _GUI {

	private static final long serialVersionUID = 245689007719525324L;

	private JButton btnAdd; 
	private JButton btnRemove;
	
	private JPanel panCenter; 
	
	public dlg_dcrf_attachment() {

	}

	public dlg_dcrf_attachment(Frame owner) {
		super(owner);
		initGUI();
	}

	public dlg_dcrf_attachment(Dialog owner) {
		super(owner);

	}

	public dlg_dcrf_attachment(Window owner) {
		super(owner);
		
	}

	public dlg_dcrf_attachment(Frame owner, boolean modal) {
		super(owner, modal);

	}

	public dlg_dcrf_attachment(Frame owner, String title) {
		super(owner, title);

	}

	public dlg_dcrf_attachment(Dialog owner, boolean modal) {
		super(owner, modal);

	}

	public dlg_dcrf_attachment(Dialog owner, String title) {
		super(owner, title);

	}

	public dlg_dcrf_attachment(Window owner, ModalityType modalityType) {
		super(owner, modalityType);

	}

	public dlg_dcrf_attachment(Window owner, String title) {
		super(owner, title);

	}

	public dlg_dcrf_attachment(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initGUI();
	}

	public dlg_dcrf_attachment(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);

	}

	public dlg_dcrf_attachment(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);

	}

	public dlg_dcrf_attachment(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);

	}

	public dlg_dcrf_attachment(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);

	}

	public dlg_dcrf_attachment(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);

	}

	public void initGUI() {
		System.out.println("Builder");
		
		JPanel panMain = new JPanel(new BorderLayout(5, 5)); 
		this.getContentPane().add(panMain, BorderLayout.CENTER); 
		panMain.setBorder(new EmptyBorder(2, 2, 2, 2));
		panMain.setOpaque(true);
		{
			{
				panCenter = new JPanel(new BorderLayout(5, 5)); 
				panMain.add(panCenter, BorderLayout.CENTER);
				panCenter.setBorder(JTBorderFactory.createTitleBorder("0 Attachments"));
				{
					
				}
			}
			{
				JPanel panEnd = new JPanel(new GridLayout(1, 3, 5, 5)); 
				panMain.add(panEnd, BorderLayout.PAGE_END); 
				panEnd.setPreferredSize(new Dimension(0, 30));
				panEnd.setBorder(_LINE_BORDER);
				{
					
				}
			}
		}
	}

}
