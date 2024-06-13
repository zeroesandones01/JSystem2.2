package components;

import java.awt.Color;

import interfaces._GUI;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class _JTagLabel extends JTextField implements _GUI, DocumentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8716485829538290780L;

	public _JTagLabel() {
		initGUI();
	}

	public _JTagLabel(String text) {
		super(text);
		initGUI();
	}

	public _JTagLabel(int columns) {
		super(columns);
		initGUI();
	}

	public _JTagLabel(String text, int columns) {
		super(text, columns);
		initGUI();
	}

	public _JTagLabel(Document doc, String text, int columns) {
		super(doc, text, columns);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setOpaque(false);
		this.setBorder(null);
		this.setFocusable(false);
		this.setForeground(Color.BLUE);
	}
	
	public void setTag(String text) {
		if (text != null){
			if(text.trim().equals("")){
				setText("[ ]");
			}else{
				setText("[ " + text + " ]");
			}
		}
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		printIt(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		//printIt(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		//printIt(e);
	}
	
	private void printIt(final DocumentEvent documentEvent) {
		DocumentEvent.EventType type = documentEvent.getType();
		//System.out.printf("Type: %s%n", type);

		if(type.equals(EventType.INSERT)){
			/*Runnable doAssist = new Runnable() {
                @Override
                public void run() {
                	// when input "2013",will add to "2013-";when
                	// input "2013-10",will add to "2013-10-"
                	String input = getText();
                	
                	if (input.matches("^[0-9]{4}")) {
                        setText(input + "-");
                    } else if (input.matches("^[0-9]{4}-[0-9]{2}")) {
                        setText(input + "-");
                    }
                }
            };
            SwingUtilities.invokeLater(doAssist);*/
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Document doc = documentEvent.getDocument();
					try {
						SimpleAttributeSet attributes = new SimpleAttributeSet();
						StyleConstants.setBold(attributes, true);
						StyleConstants.setItalic(attributes, true);

						doc.insertString(0, "[ ", attributes);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
