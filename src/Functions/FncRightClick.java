package Functions;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.DefaultEditorKit;

public class FncRightClick extends JPopupMenu implements MouseListener {

	private static final long serialVersionUID = 1L;

	JMenuItem menuSelectAll;
	JMenuItem menuCut;
	JMenuItem menuCopy;
	JMenuItem menuPaste;
	JMenuItem menuReset;

	Component componentInvoker;
	JTextField componentText;

	public FncRightClick() {
		initThis();
	}

	public FncRightClick(String label) {
		super(label);
		initThis();
	}

	private void initThis() {
		componentText = new JTextField();
		
		this.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				checkInvoker(e);
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				
			}
		});
		
		menuSelectAll = new JMenuItem(componentText.getActionMap().get("select-all"));
		this.add(menuSelectAll);
		menuSelectAll.setText("Select All");
		menuSelectAll.setFont(getFont().deriveFont(10.0f));

		this.add(new JSeparator());

		menuCut = new JMenuItem(new DefaultEditorKit.CutAction());
		this.add(menuCut);
		menuCut.setText("Cut");
		menuCut.setFont(getFont().deriveFont(10.0f));

		menuCopy = new JMenuItem(new DefaultEditorKit.CopyAction());
		this.add(menuCopy);
		menuCopy.setText("Copy");
		menuCopy.setFont(getFont().deriveFont(10.0f));

		menuPaste = new JMenuItem(new DefaultEditorKit.PasteAction());
		this.add(menuPaste);
		menuPaste.setText("Paste");
		menuPaste.setFont(getFont().deriveFont(10.0f));

		//this.add(new JSeparator());

		menuReset = new JMenuItem(new DefaultEditorKit.BeepAction());
		//this.add(menuReset);
		menuReset.setText("Reset");
		menuReset.setFont(getFont().deriveFont(10.0f));
	}
	
	private void checkInvoker(PopupMenuEvent e) {
		if(((FncRightClick)e.getSource()).getInvoker() instanceof JTextArea){
			JTextArea comp = (JTextArea) ((FncRightClick)e.getSource()).getInvoker();
			isEditable(comp.isEditable());
		}
	}
	
	private void isEditable(Boolean editable) {
		menuCut.setEnabled(editable);
		menuPaste.setEnabled(editable);
		menuReset.setEnabled(editable);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.isPopupTrigger()){
			this.show((Component)e.getSource(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.isPopupTrigger()){
			this.show((Component)e.getSource(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
