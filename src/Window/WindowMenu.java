package Window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.jdesktop.swingx.JXPanel;

/**
 * Menu component that handles the functionality expected of a standard
 * "Windows" menu for MDI applications.
 */
public class WindowMenu extends JMenu {
	private static final long serialVersionUID = 7380875831381432574L;

	private MDIDesktopPane desktop;

	private JMenuItem cascade = new JMenuItem("Cascade");

	private JMenuItem tile = new JMenuItem("Tile");

	private JMenuItem showhidetoolbar;
	private JXPanel pnlNorthz;

	public WindowMenu(MDIDesktopPane desktop, JXPanel pnlNorth) {
		this.desktop = desktop;
		this.pnlNorthz = pnlNorth;
		setText("Window");
		setMnemonic(KeyEvent.VK_W);

		cascade.setMnemonic(KeyEvent.VK_C);
		cascade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				WindowMenu.this.desktop.cascadeFrames();
			}
		});

		tile.setMnemonic(KeyEvent.VK_T);
		tile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				WindowMenu.this.desktop.tileFrames();
			}
		});

		showhidetoolbar = new JMenuItem((pnlNorth.isVisible() ? "Hide":"Show") + " Toolbar");
		showhidetoolbar.setMnemonic(showhidetoolbar.getText().charAt(0));
		showhidetoolbar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JMenuItem menuitem = (JMenuItem) e.getSource();
				
				pnlNorthz.setVisible(!pnlNorthz.isVisible());
				menuitem.setText((pnlNorthz.isVisible() ? "Hide":"Show") + " Toolbar");
				menuitem.setMnemonic(menuitem.getText().charAt(0));
			}
		});

		addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) {
			}
			public void menuDeselected(MenuEvent e) {
				removeAll();
			}
			public void menuSelected(MenuEvent e) {
				buildChildMenus();
			}
		});
	} // WindowMenu

	/* Sets up the children menus depending on the current desktop state */
	private void buildChildMenus() {
		int i;
		ChildMenuItem menu;
		JInternalFrame[] array = desktop.getAllFrames();

		add(showhidetoolbar);
		addSeparator();

		add(cascade);
		add(tile);
		
		
		if (array.length > 0)
			addSeparator();

		cascade.setEnabled(array.length > 0);
		tile.setEnabled(array.length > 0);

		for (i = 0; i < array.length; i++) {
			menu = new ChildMenuItem(array[i]);
			menu.setState(i == 0);
			menu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					JInternalFrame frame = ((ChildMenuItem) ae.getSource()).getFrame();
					try {
						if ( frame.isIcon() ) // De-Iconify/Restore
							frame.setIcon( false );
						frame.moveToFront();
						frame.setSelected(true);
					} catch (PropertyVetoException e) {
						e.printStackTrace();
					}
				}
			});
			menu.setIcon(array[i].getFrameIcon());
			add(menu);
		}
	} // buildChildMenus

	/*
	 * This JCheckBoxMenuItem descendant is used to track the child frame that
	 * corresponds to a give menu.
	 */
	class ChildMenuItem extends JCheckBoxMenuItem {
		private static final long serialVersionUID = -1831927193374437812L;
		private JInternalFrame frame;

		public ChildMenuItem(JInternalFrame frame) {
			super(frame.getTitle());
			this.frame = frame;
		}

		public JInternalFrame getFrame() {
			return frame;
		}
	} // ChildMenuItem


} // WindowMenu