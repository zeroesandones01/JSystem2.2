package Home;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;

import Functions.FncLookAndFeel;
import Functions.FncPanelPainter;
import Window.DesktopScrollPane;

import com.cloudgarden.layout.AnchorLayout;

public class Sample extends JXFrame implements ActionListener, WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JXPanel pnlMain;
	private JDesktopPane desktopMain;
	private DesktopScrollPane scrollMain;
	
	private JMenuBar menuBar;

	private JMenu menuFile;
	private JMenuItem menuitemLogout;
	private JMenuItem menuitemExit;
	private JMenuItem menuitemOptions;

	private JMenu menuAccounting;

	private JMenu menuTransaction;

	private JMenu menuUtilities;

	private JMenu menuReports;

	private JMenu menuSystem;
	private JMenuItem menuitemTransferJournalEntries;
	private JMenuItem menuitemPreferences;

	private JMenu menuHelp;
	private JMenuItem menuitemConsole;
	private JMenuItem menuitemAboutBOIModule;

	public Sample() {
		initGUI();
	}

	public Sample(String title) {
		super(title);
		initGUI();
	}

	public Sample(GraphicsConfiguration gc) {
		super(gc);
		initGUI();
	}

	public Sample(String title, GraphicsConfiguration gc) {
		super(title, gc);
		initGUI();
	}

	public Sample(String title, boolean exitOnClose) {
		super(title, exitOnClose);
		initGUI();
	}

	public Sample(String title, GraphicsConfiguration gc, boolean exitOnClose) {
		super(title, gc, exitOnClose);
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.getContentPane().setLayout(new BorderLayout());
			this.setIconImage(FncLookAndFeel.iconSystem);
			this.setExtendedState(MAXIMIZED_BOTH);
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(800, 600);

			this.addWindowListener(this);
			this.getRootPane().registerKeyboardAction(this, "Console", KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			{
				pnlMain = new JXPanel();
				getContentPane().add(pnlMain, BorderLayout.CENTER);
				pnlMain.setLayout(new BorderLayout());
				//pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				pnlMain.setBackgroundPainter(FncPanelPainter.paint(FncLookAndFeel.grayColor));
				{
					desktopMain = new JDesktopPane();
					desktopMain.setLayout(new AnchorLayout());
					desktopMain.setOpaque(false);
					desktopMain.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
					
					scrollMain = new DesktopScrollPane(desktopMain);
					pnlMain.add(scrollMain, BorderLayout.CENTER);
					scrollMain.setOpaque(false);
					scrollMain.getViewport().setOpaque(false);
				}
			}
			{
				menuBar = new JMenuBar();//XXX JMenuBar
				setJMenuBar(menuBar);
				{
					menuFile = new JMenu("File");
					menuBar.add(menuFile);
					menuFile.setMnemonic(KeyEvent.VK_F);
					{
						menuitemOptions = new JMenuItem("Options");
						menuFile.add(menuitemOptions);
						menuitemOptions.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
							}
						});
					}
					{
						menuFile.add(new JSeparator());
					}
					{
						menuitemLogout = new JMenuItem("Logout");
						menuFile.add(menuitemLogout);
						menuitemLogout.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
							}
						});
					}
					{
						menuitemExit = new JMenuItem("Exit");
						menuFile.add(menuitemExit);
						menuitemExit.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								System.exit(0);
							}
						});
					}
				}
				{
					menuAccounting = new JMenu("Accounting");
					//menuBar.add(menuEdit);
					menuAccounting.setMnemonic(KeyEvent.VK_E);
				}
				{
					menuTransaction = new JMenu("Transaction");
					menuBar.add(menuTransaction);
					menuTransaction.setMnemonic(KeyEvent.VK_T);
				}
				{
					menuReports = new JMenu("Reports");
					menuBar.add(menuReports);
					menuReports.setMnemonic(KeyEvent.VK_R);
				}
				{
					menuUtilities = new JMenu("Utilities");//XXX Utilities
					menuBar.add(menuUtilities);
					menuUtilities.setMnemonic(KeyEvent.VK_U);
				}
				{
					menuSystem = new JMenu("System");
					menuBar.add(menuSystem);
					menuSystem.setMnemonic(KeyEvent.VK_S);
					{
						menuitemTransferJournalEntries = new JMenuItem("Transfer Journal Entries (Postbook)");
						menuSystem.add(menuitemTransferJournalEntries);
						menuitemTransferJournalEntries.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								//if(isNotExisting("TransferJournalEntries")){
									//TransferJournalEntries tje = new TransferJournalEntries();
									addWindow(new JInternalFrame("Sample", true, true, true, true));
								//}
							}
						});
					}
					{
						menuSystem.add(new JSeparator());
					}
					{
						menuitemPreferences = new JMenuItem("Preferences");
						menuSystem.add(menuitemPreferences);
						menuitemPreferences.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
							}
						});
					}
				}
				{
					menuHelp = new JMenu("Help");
					menuBar.add(menuHelp);
					menuHelp.setMnemonic(KeyEvent.VK_H);
					{
						menuitemConsole = new JMenuItem("Console");
						menuHelp.add(menuitemConsole);
						menuitemConsole.addActionListener(this);
					}
					{
						menuitemAboutBOIModule = new JMenuItem("About BOI Module");
						menuHelp.add(menuitemAboutBOIModule);
					}
				}
				{
					menuBar.add(Box.createGlue());
				}
				/*{
					btnMinimize = new _JMenuToolbarButton(MINIMIZE_ICON);
					menuBar.add(btnMinimize);
					btnMinimize.setActionCommand("Minimize");
					btnMinimize.setToolTipText("Minimize");
					btnMinimize.setFocusable(false);
					btnMinimize.addActionListener(this);
				}
				{
					btnMaximize = new _JMenuToolbarButton(MAXIMIZE_ICON);
					menuBar.add(btnMaximize);
					btnMaximize.setActionCommand("Maximize");
					btnMaximize.setToolTipText("Restore Window");
					btnMaximize.setFocusable(false);
					btnMaximize.addActionListener(this);
				}
				{
					btnClose = new _JMenuToolbarButton(CLOSE_ICON);
					menuBar.add(btnClose);
					btnClose.setActionCommand("Close");
					btnClose.setToolTipText("Close");
					btnClose.setFocusable(false);
					btnClose.addActionListener(this);
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addWindow(JInternalFrame internalFrame) {
		desktopMain.add(internalFrame);
		
		internalFrame.setBounds(25, 25, 200, 100);
		internalFrame.setVisible(true);
		
		/*internalFrame.moveToFront();
		try {
			internalFrame.setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION){
			return;
		}
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
