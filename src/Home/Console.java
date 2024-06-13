package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;

import Functions.FncActionMap;
import Functions.FncGlobal;
import Functions.FncLookAndFeel;
import Functions.FncRightClick;
import Functions.RGBGrayFilter;

public class Console extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollCenter;
	private JLabel lblCount;
	private JXPanel pnlSouthCenterCenter;
	private JXTextField txtFind;
	private JButton btnClose;
	private JLabel lblFind;
	private JXPanel pnlSouthCenter;
	private JXPanel pnlSouth;

	private Dimension size = new Dimension(800, 600);
	private Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
	private ImageIcon CLOSE_TAB_ICON = new ImageIcon(this.getClass().getClassLoader().getResource("Images/closeTabButton.png"));

	private Color ERROR_COLOR = Color.PINK;
	private Color NORMAL_COLOR = Color.WHITE;

	private int highlightIndex = -1;
	private Highlighter highlighterAll;

	private Highlighter.HighlightPainter painterAll = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
	private Highlighter.HighlightPainter painterSelected = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);

	private List<Highlight> listHighlight = new ArrayList<Highlight>();

	public Console() {
		initThis();
	}

	public Console(Frame owner) {
		super(owner);
		initThis();
	}

	public Console(Dialog owner) {
		super(owner);
		initThis();
	}

	public Console(Window owner) {
		super(owner);
		initThis();
	}

	public Console(Frame owner, boolean modal) {
		super(owner, modal);
		initThis();
	}

	public Console(Frame owner, String title) {
		super(owner, title);
		initThis();
	}

	public Console(Dialog owner, boolean modal) {
		super(owner, modal);
		initThis();
	}

	public Console(Dialog owner, String title) {
		super(owner, title);
		initThis();
	}

	public Console(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		initThis();
	}

	public Console(Window owner, String title) {
		super(owner, title);
		initThis();
	}

	public Console(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initThis();
	}

	public Console(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initThis();
	}

	public Console(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		initThis();
	}

	public Console(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initThis();
	}

	public Console(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		initThis();
	}

	public Console(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		initThis();
	}

	private void initThis() {
		this.setPreferredSize(size);
		this.setSize(size);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		{
			scrollCenter = new JScrollPane();
			this.getContentPane().add(scrollCenter, BorderLayout.CENTER);
			{
				scrollCenter.setViewportView(FncGlobal.textArea);
				FncGlobal.textArea.setText(FncGlobal.lpsOut.getBuf().toString());
				FncGlobal.textArea.setEditable(false);
				FncGlobal.textArea.setFont(FncLookAndFeel.fontConsole.deriveFont(13.0f));
				FncGlobal.textArea.addMouseListener(new FncRightClick());

				FncActionMap.print(FncGlobal.textArea);
				highlighterAll = FncGlobal.textArea.getHighlighter();
			}
		}
		{
			pnlSouth = new JXPanel();
			this.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setVisible(false);
			pnlSouth.setBorder(lineBorder);
			pnlSouth.setLayout(new BorderLayout());
			pnlSouth.setPreferredSize(new Dimension(790, 30));
			{
				pnlSouthCenter = new JXPanel();
				pnlSouth.add(pnlSouthCenter, BorderLayout.CENTER);
				pnlSouthCenter.setLayout(new BorderLayout(5, 5));
				pnlSouthCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				{
					lblFind = new JLabel("Find");
					pnlSouthCenter.add(lblFind, BorderLayout.WEST);
				}
				{
					pnlSouthCenterCenter = new JXPanel();
					pnlSouthCenter.add(pnlSouthCenterCenter, BorderLayout.CENTER);
					pnlSouthCenterCenter.setLayout(new BorderLayout());
					pnlSouthCenterCenter.setBorder(UIManager.getBorder("TextField.border"));
					{
						txtFind = new JXTextField("Find");
						pnlSouthCenterCenter.add(txtFind, BorderLayout.CENTER);
						txtFind.setBorder(BorderFactory.createEmptyBorder());
						txtFind.getDocument().addDocumentListener(new DocumentListener() {
							public void removeUpdate(DocumentEvent e) {
								searchText();
							}
							public void insertUpdate(DocumentEvent e) {
								searchText();
							}
							public void changedUpdate(DocumentEvent e) {

							}
						});

						txtFind.registerKeyboardAction(this, "escape-find", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
						txtFind.registerKeyboardAction(this, "scroll-up", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
						txtFind.registerKeyboardAction(this, "scroll-down", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
					
						txtFind.registerKeyboardAction(this, "scroll-up", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
						txtFind.registerKeyboardAction(this, "scroll-down", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
					}
					{
						lblCount = new JLabel();
						pnlSouthCenterCenter.add(lblCount, BorderLayout.EAST);
						lblCount.setOpaque(true);
						lblCount.setBackground(UIManager.getColor("TextField.background"));
						lblCount.setFont(UIManager.getFont("TextField.font").deriveFont(Font.BOLD).deriveFont(10.0f));
					}
				}
				{
					btnClose = new JButton();
					pnlSouthCenter.add(btnClose, BorderLayout.EAST);
					btnClose.setActionCommand("close");

					btnClose.setBorderPainted(false); 
					btnClose.setContentAreaFilled(false); 
					btnClose.setOpaque(false);
					btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

					btnClose.setRolloverIcon(CLOSE_TAB_ICON);
					btnClose.setRolloverEnabled(true);
					btnClose.setIcon(RGBGrayFilter.getDisabledIcon(btnClose, CLOSE_TAB_ICON));

					btnClose.setBorder(null);
					btnClose.addActionListener(this);
				}
			}
		}
		this.getRootPane().registerKeyboardAction(this, "escape", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		this.getRootPane().registerKeyboardAction(this, "find", KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if(actionCommand.equals("escape")){
			dispose();
		}
		if(actionCommand.equals("find")){
			pnlSouth.setVisible(true);
			txtFind.requestFocus();
		}
		if(actionCommand.equals("close") || actionCommand.equals("escape-find")){
			pnlSouth.setVisible(false);
		}
		
		if(actionCommand.equals("scroll-up")){
			highlightIndex--;

			if(highlightIndex < 0){
				highlightIndex = highlighterAll.getHighlights().length -1;
			}
			highlightSelected(highlightIndex);
		}
		if(actionCommand.equals("scroll-down")){
			highlightIndex++;

			if(highlightIndex == highlighterAll.getHighlights().length){
				highlightIndex = 0;
			}
			highlightSelected(highlightIndex);
		}
	}

	private void searchText() {
		highlightIndex = -1;
		highlighterAll.removeAllHighlights();

		if(!txtFind.getText().trim().equals("")){
			highlightSearchedText(FncGlobal.textArea, txtFind);
		}else{
			txtFind.setBackground(NORMAL_COLOR);
			lblCount.setText("");
		}
	}

	private void highlightSearchedText(JTextComponent textComp, JXTextField textField) {
		try {
			listHighlight = new ArrayList<Highlight>();

			String pattern = textField.getText();

			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;

			// Search for pattern
			while ((pos = text.indexOf(pattern, pos)) >= 0) {
				// Create highlighter using private painter and apply around
				highlighterAll.addHighlight(pos, pos + pattern.length(), painterAll);
				pos += pattern.length();
			}

			for(int x=0; x < highlighterAll.getHighlights().length; x++){
				listHighlight.add(highlighterAll.getHighlights()[x]);
			}

			if(listHighlight.size() == 0){
				textField.setBackground(ERROR_COLOR);
				lblCount.setText("");
			}else{
				textField.setBackground(NORMAL_COLOR);
				highlightSelected(0);
				highlightIndex = 0;
			}
		} catch (BadLocationException e) { }
	}

	private void highlightSelected(Integer index) {
		highlighterAll.removeAllHighlights();

		Highlight selected = null;
		for(int x=0; x < listHighlight.size(); x++){
			Highlight h1 = listHighlight.get(x);

			if(x == index){
				selected = h1;
			}

			try {
				highlighterAll.addHighlight(h1.getStartOffset(), h1.getEndOffset(), (x == index ? painterSelected:painterAll));
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}

		int pos = selected.getStartOffset();
		try {
			Rectangle rect = FncGlobal.textArea.modelToView(pos);
			FncGlobal.textArea.scrollRectToVisible(rect);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		lblCount.setText((index +1) + "/" + listHighlight.size());
	}

}
