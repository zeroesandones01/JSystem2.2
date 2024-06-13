package Functions;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.plaf.PainterUIResource;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

import Database.pgSelect;
import components.BaseSplitPaneUI;

public class FncLookAndFeel {

	//public static Color systemColor = new Color(0, 83, 158);//default color
	public static Color systemColor = new Color(20, 148, 148);
	//public static Color systemColor = new Color(255, 80, 80);
	public static Color grayColor = new Color(209, 208, 206);
	//public static Color grayColor = new Color(255, 80, 80);
	public static Color defaultColor = new Color(0, 93, 168);
	//public static Color defaultColor = new Color(255, 80, 80);

	//public static Color colorTeal = new Color(0, 133, 208);//0, 128, 128//default
	public static Color colorTeal = new Color(0, 128, 128);//0, 128, 128//default
	public static Color colorBlueIvy = new Color(48, 144, 199);
	public static Color Turquoise =  new Color(67, 198, 219);
	public static Color DarkTurquoise =  new Color( 27, 158, 179);
	
	//Teal - 0, 128, 128
	//Blue Ivy - 48, 144, 199

	// 102, 125, 149
	//Blue Gray - 152, 175, 199
	// 172, 195, 219

	public static Color windowColor = colorTeal;
	//public static String windowString_Light = "0 113 188";//0 113 188
	public static String windowString_Light = "20 148 148";//0 113 188
	
	//public static String windowString_Dark = "0 93 168";//0 93 168
	public static String windowString_Dark = "0 128 128";//0 93 168
	 

	public static String windowInactiveString_Light = "0 63 138";
	public static String windowInactiveString_Dark = "0 43 118";

	//public static String font_name = "Tahoma";
	public static String font_name = "SansSerif";
	public static Integer font_size = 12;
	
	//public static Integer font_size = 10;

	public static Font systemFont_Plain = new Font(font_name, Font.PLAIN, font_size);//SansSerif
	public static Font systemFont_Bold = new Font(font_name, Font.BOLD, font_size);//SansSerif

	public static Image iconSystem = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/blue-home-icon.png")).getImage();
	public static Image iconSearch = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/blue-search-icon.png")).getImage();
	
	public static Image refreshbutton = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/Refresh-icon.png")).getImage();
	public static Image savebutton = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/save_.png")).getImage();

	
	public static Image lefthbutton = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/arrow-left-icon.png")).getImage();
	public static Image rightbutton = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/arrow-right-icon.png")).getImage();
	public static ImageIcon iconCARD = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/card.png"));
	public static ImageIcon iconSMS = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/conversation.png"));
	public static ImageIcon iconDCRF = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/clipboard.png"));
	public static ImageIcon iconDCRF2 = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/clipboard_notice.png"));
	public static ImageIcon iconRemove = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource("Images/Apps-Dialog-Remove-icon.png"));
	/*
	 * Plain = n022003l
	 * Bold = n022004l
	 * Italic = n022023l
	 * Bold Italic = n022024l
	 */
	public static InputStream is = FncLookAndFeel.class.getClassLoader().getResourceAsStream("Resources/n022004l.pfb");
	//public static Font font = Font.createFont(Font.TRUETYPE_FONT, is);
	public static Font fontConsole = null;


	public static void initialize() {
		//Set Look & Feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			Properties props = FncGlobal.properties;//XXX
			props.put("logoString", "");

			props.put("controlColor", windowString_Light);
			props.put("controlColorLight", windowString_Light);
			props.put("controlColorDark", windowString_Dark);

			/*props.put("buttonColor", windowString_Light);
			props.put("buttonColorLight", windowString_Light);
			props.put("buttonColorDark", windowString_Dark);*/
			//props.put("buttonForegroundColor", "255 255 255");

			props.put("rolloverColor", windowString_Light); 
			props.put("rolloverColorLight", windowString_Light); 
			props.put("rolloverColorDark", windowString_Dark);

			props.put("menuColorLight", "219 218 216");
			props.put("menuColorDark", "209 208 206");

			props.put("menuSelectionBackgroundColor", windowString_Light);
			props.put("menuSelectionBackgroundColorLight", windowString_Light);
			props.put("menuSelectionBackgroundColorDark", windowString_Light);

			//props.put("toolbarBackgroundColor", "0 78 78");
			props.put("menuBackgroundColor", "219 218 216");

			props.put("selectionForegroundColor", "255 255 255");
			props.put("selectionBackgroundColor", windowString_Dark);

			props.put("windowTitleForegroundColor", "255 255 255");
			props.put("windowTitleBackgroundColor", windowString_Light); 
			props.put("windowTitleColorLight", windowString_Light); 
			props.put("windowTitleColorDark", windowString_Dark); 
			props.put("windowBorderColor", windowString_Dark);

			props.put("windowInactiveTitleColorLight", windowInactiveString_Light);
			props.put("windowInactiveTitleColorDark", windowInactiveString_Dark);
			props.put("windowInactiveBorderColor", windowInactiveString_Dark);

			props.put("controlTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
			props.put("systemTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
			props.put("userTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
			props.put("menuTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
			props.put("windowTitleFont", String.format("%s %s %s", font_name, "BOLD", font_size));
			props.put("subTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));

			props.put("foregroundColor", "0 83 158");

			AcrylLookAndFeel.setCurrentTheme(props);

			// select the Look and Feel
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			//UIManager.setLookAndFeel("joxy.JoxyLookAndFeel");

			Image iconInternalFrame = iconSystem;
			iconInternalFrame = iconInternalFrame.getScaledInstance(19, 19, Image.SCALE_DEFAULT);
			UIManager.put("InternalFrame.icon", new ImageIcon(iconInternalFrame));

			// Font
			UIManager.getLookAndFeelDefaults().put("defaultFont", systemFont_Bold);

			// JMenuBar
			UIManager.put("MenuBar.background", systemColor);

			// JMenu
			UIManager.put("Menu.font", systemFont_Plain);
			UIManager.put("Menu.acceleratorFont", systemFont_Plain);

			// JMenuItem
			UIManager.put("MenuItem.font", systemFont_Plain);
			UIManager.put("MenuItem.acceleratorFont", systemFont_Plain);
			UIManager.put("MenuItem.selectionBackground", systemColor);

			// JButton
			//UIManager.put("Button.defaultButtonFollowsFocus", false);
			UIManager.put("Button.font", systemFont_Bold);
			UIManager.put("Button.foreground", Color.WHITE);
			UIManager.put("Button.background", windowColor);
			UIManager.put("Button.disabledText", Color.RED);
			UIManager.put("Button.disabledForeground", Color.RED);
			//UIManager.put("Button.disabledGrayRange", Color.RED);

			UIManager.put("Button.toolBarBorderBackground", Color.RED);

			// JToggleButton
			//UIManager.put("Button.defaultButtonFollowsFocus", false);
			UIManager.put("ToggleButton.font", systemFont_Bold);
			UIManager.put("ToggleButton.foreground", Color.WHITE);
			UIManager.put("ToggleButton.background", windowColor);
			UIManager.put("ToggleButton.border", UIManager.getBorder("Button.border"));

			// JLabel
			UIManager.put("Label.font", systemFont_Plain);
			//UIManager.put("Label.foreground", systemColor);

			//JCheckBox
			//UIManager.put("CheckBox.foreground", systemColor);

			// JSpinner
			//UIManager.put("Spinner.background", systemColor);

			// JTextField
			//UIManager.put("TextField.disabledBackground", new Color(255, 255, 255));
			//UIManager.put("TextField.border", FncGlobal.lineBorder);

			// JFormattedTextField
			UIManager.put("FormattedTextField.background", new Color(152, 255, 152));

			// JSplitPane
			UIManager.put("SplitPaneUI", BaseSplitPaneUI.class.getName());
			UIManager.put("SplitPane.supportsOneTouchButtons", true);
			UIManager.put("SplitPane.centerOneTouchButtons", true);
			UIManager.put("SplitPane.oneTouchExpandable", true);
			UIManager.put("SplitPane.dividerSize", 5);
			UIManager.put("SplitPane.dividerFocusColor", windowColor);
			UIManager.put("SplitPaneDivider.draggingColor", windowColor);

			// JTable
			UIManager.put("TableHeader.font", new Font(font_name, Font.BOLD, (font_size >= 12 ? (font_size - 1):11)));
			//UIManagerExt.put("TableHeader.font", new Font(font_name, Font.BOLD, (font_size >= 12 ? (font_size - 1):11)));

			//UIManager.put("Table.focusCellHighlightBorder", BorderFactory.createEmptyBorder(1, 1, 1, 1));
			//UIManager.put("Table.selectionBackground", new Color(173, 223, 255));
			//UIManager.put("Table.selectionForeground", Color.BLACK);
			//UIManager.put("Table.shadow", new Color(21, 105, 199));
			UIManager.put("Table.font", new Font(font_name, Font.PLAIN, (font_size >= 12 ? (font_size - 1):11)));
			UIManager.put("Table.rowHeight", 20);

			Image img = new ImageIcon("images/Portfolio.png").getImage();
			img = img.getScaledInstance(19, 19, Image.SCALE_SMOOTH);

			//XXX JTree
			//UIManager.put("Tree.font", new Font("SansSerif", Font.PLAIN, 12));
			//UIManager.put("Tree.leafIcon", null);
			//UIManager.put("Tree.openIcon", null);
			UIManager.put("Tree.rowHeight", 22);
			//UIManager.put("Tree.padding", 5);
			//UIManager.put("Tree.expanderSize", 10);
			//UIManager.put("Tree.selectionBackground", Color.WHITE);
			//UIManager.put("Tree.selectionBorderColor", Color.WHITE);
			//UIManager.put("Tree.selectionForeground", systemColor);
			UIManager.put("Tree.paintLines", false);
			UIManager.put("Tree.showsRootHandles", true);
			//UIManager.put("Tree.leftChildIndent", -10);

			// JTabbedPane
			UIManager.put("TabbedPane.tabAreaBackground", UIManager.getColor("JXPanel"));
			//UIManager.put("TabbedPane.tabAreaInsets", new Insets(5, 35, 5, 35));
			UIManager.put("TabbedPane.tabInsets", new Insets(2, 20, 1, 20));
			UIManager.put("TabbedPane.unselectedTabForeground", Color.MAGENTA);

			/*
			UIManager.put("TabbedPane.background", Color.MAGENTA);
			UIManager.put("TabbedPane.borderHightlightColor", Color.MAGENTA);
			UIManager.put("TabbedPane.contentAreaColor", Color.MAGENTA);
			UIManager.put("TabbedPane.darkShadow", Color.MAGENTA);
			UIManager.put("TabbedPane.focus", Color.MAGENTA);
			UIManager.put("TabbedPane.foreground", Color.MAGENTA);
			UIManager.put("TabbedPane.highlight", Color.MAGENTA);
			UIManager.put("TabbedPane.light", Color.MAGENTA);
			UIManager.put("TabbedPane.selected", Color.MAGENTA);
			UIManager.put("TabbedPane.selectedForeground", Color.MAGENTA);
			UIManager.put("TabbedPane.selectHighlight", Color.MAGENTA);
			UIManager.put("TabbedPane.shadow", Color.MAGENTA);
			UIManager.put("TabbedPane.tabAreaBackground", Color.MAGENTA);
			UIManager.put("TabbedPane.unselectedBackground", Color.MAGENTA);
			UIManager.put("TabbedPane.unselectedTabForeground", Color.MAGENTA);
			UIManager.put("TabbedPane.unselectedTabHighlight", Color.MAGENTA);
			UIManager.put("TabbedPane.unselectedTabShadow", Color.MAGENTA);
			 */

			// JXTaskPane
			//UIManager.put("TaskPaneUI.titleBackgroundGradientStart", Color.WHITE);
			//UIManager.put("TaskPaneUI.titleBackgroundGradientEnd", Color.GREEN);

			// JXLoginPane
			//UIManager.put("JXLoginPane.bannerForeground", new ColorUIResource(Color.BLACK));
			UIManager.put("JXLoginPane.bannerDarkBackground", systemColor);
			UIManager.put("JXLoginPane.bannerLightBackground", colorTeal);

			// JXTitledPanel
			UIManager.put("JXTitledPanel.titleFont", systemFont_Bold.deriveFont(16.0f));
			//UIManager.put("JXTitledPanel.titleForeground", systemColor);
			UIManager.put("JXTitledPanel.titlePainter", new PainterUIResource<JXTitledPanel>(new MattePainter(new GradientPaint(new Point(30, 30), new Color(82, 105, 129), new Point(30, 5), new Color(152, 175, 199)))));

			// JFormattedTextField
			//UIManager.put("JXTextField.background", new Color(255, 255, 255));
			UIManager.put("JXTextField.font", systemFont_Plain);

			/*UIManager.put("InternalFrame.iconifyIcon", FncGlobal.iconSystem);
			UIManager.put("InternalFrame.minimizeIcon", FncGlobal.iconSystem);
			UIManager.put("InternalFrame.maximizeIcon", FncGlobal.iconSystem);

			UIManager.put("InternalFrameTitlePane.closeIcon", FncGlobal.iconSystem);
			UIManager.put("InternalFrameTitlePane.iconifyIcon", FncGlobal.iconSystem);
			UIManager.put("InternalFrameTitlePane.maximizeIcon", FncGlobal.iconSystem);
			UIManager.put("InternalFrameTitlePane.minimizeIcon", FncGlobal.iconSystem);*/
			
			//XXX Console Font
			fontConsole = Font.createFont(Font.TYPE1_FONT, is);

			//listAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static ImageIcon systemIcon() {
		Image iconInternalFrame = iconSystem;
		iconInternalFrame = iconInternalFrame.getScaledInstance(19, 19, Image.SCALE_DEFAULT);
		return new ImageIcon(iconInternalFrame);
	}

	public static ImageIcon systemIcon(String url) {
		Image iconInternalFrame = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource(url)).getImage();
		iconInternalFrame = iconInternalFrame.getScaledInstance(19, 19, Image.SCALE_DEFAULT);
		return new ImageIcon(iconInternalFrame);
	}

	public static ImageIcon systemIcon(String url, int width, int height) {
		Image iconInternalFrame = new ImageIcon(FncLookAndFeel.class.getClassLoader().getResource(url)).getImage();
		iconInternalFrame = iconInternalFrame.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		return new ImageIcon(iconInternalFrame);
	}

	public static void setColor(Integer red, Integer green, Integer blue) {//String windowString_Light, String windowString_Dark
		String windowString_Dark = String.format("%s %s %s", red, green, blue);

		/*int darkRed = (red + 20 > 255 ? 255:red + 20);
		int darkGreen = (green + 20 > 255 ? 255:green + 20);
		int darkBlue = (blue + 20 > 255 ? 255:blue + 20);
		String windowString_Light = String.format("%s %s %s", darkRed, darkGreen, darkBlue);*/

		Integer darkInactiveLightRed = (red + 50 > 255 ? 255:red + 50);
		Integer darkInactiveLightGreen = (green + 50 > 255 ? 255:green + 50);
		Integer darkInactiveLightBlue = (blue + 50 > 255 ? 255:blue + 50);
		String windowInactiveString_Light = String.format("%s %s %s", darkInactiveLightRed, darkInactiveLightGreen, darkInactiveLightBlue);
		String windowString_Light = String.format("%s %s %s", darkInactiveLightRed, darkInactiveLightGreen, darkInactiveLightBlue);

		//System.out.printf("Light: (%s %s %s)%n", darkInactiveLightRed, darkInactiveLightGreen, darkInactiveLightBlue);
		//System.out.printf("Inactive Light: (%s %s %s)%n", darkInactiveLightRed, darkInactiveLightGreen, darkInactiveLightBlue);

		Integer darkInactiveDarkRed = (red + 70 > 255 ? 255:red + 70);
		Integer darkInactiveDarkGreen = (green + 70 > 255 ? 255:green + 70);
		Integer darkInactiveDarkBlue = (blue + 70 > 255 ? 255:blue + 70);
		String windowInactiveString_Dark = String.format("%s %s %s", darkInactiveDarkRed, darkInactiveDarkGreen, darkInactiveDarkBlue);
		System.out.printf("Inactive Dark: (%s %s %s)%n", darkInactiveDarkRed, darkInactiveDarkGreen, darkInactiveDarkBlue);

		Properties props = FncGlobal.properties;
		props.put("logoString", "");

		props.put("controlColor", windowString_Light);
		props.put("controlColorLight", windowString_Light);
		props.put("controlColorDark", windowString_Dark);

		/*props.put("buttonColor", windowString_Light);
		props.put("buttonColorLight", windowString_Light);
		props.put("buttonColorDark", windowString_Dark);*/
		//props.put("buttonForegroundColor", "255 255 255");

		props.put("rolloverColor", windowString_Light); 
		props.put("rolloverColorLight", windowString_Light); 
		props.put("rolloverColorDark", windowString_Dark);

		props.put("menuColorLight", "219 218 216");
		props.put("menuColorDark", "209 208 206");

		props.put("menuSelectionBackgroundColor", windowString_Light);
		props.put("menuSelectionBackgroundColorLight", windowString_Light);
		props.put("menuSelectionBackgroundColorDark", windowString_Light);

		//props.put("toolbarBackgroundColor", "0 78 78");
		props.put("menuBackgroundColor", "219 218 216");

		props.put("selectionForegroundColor", "255 255 255");
		props.put("selectionBackgroundColor", windowString_Dark);

		props.put("windowTitleForegroundColor", "255 255 255");
		props.put("windowTitleBackgroundColor", windowString_Light); 
		props.put("windowTitleColorLight", windowString_Light); 
		props.put("windowTitleColorDark", windowString_Dark); 
		props.put("windowBorderColor", windowString_Dark);

		props.put("windowInactiveTitleColorLight", windowInactiveString_Light);
		props.put("windowInactiveTitleColorDark", windowInactiveString_Dark);
		props.put("windowInactiveBorderColor", windowInactiveString_Dark);

		props.put("controlTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
		props.put("systemTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
		props.put("userTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
		props.put("menuTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
		props.put("windowTitleFont", String.format("%s %s %s", font_name, "BOLD", font_size));
		props.put("subTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));

		AcrylLookAndFeel.setCurrentTheme(props);

		Integer darkButtonRed = (red + 40 > 255 ? 255:red + 40);
		Integer darkButtonGreen = (green + 40 > 255 ? 255:green + 40);
		Integer darkButtonBlue = (blue + 40 > 255 ? 255:blue + 40);

		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.background", new Color(darkButtonRed, darkButtonGreen, darkButtonBlue));

		//Save
		saveSelectedLookAndFeel(red, green, blue);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static void setDefaultColor() {//String windowString_Light, String windowString_Dark
		Properties props = FncGlobal.properties;
		props.put("logoString", "");

		props.put("controlColor", windowString_Light);
		props.put("controlColorLight", windowString_Light);
		props.put("controlColorDark", windowString_Dark);

		/*props.put("buttonColor", windowString_Light);
		props.put("buttonColorLight", windowString_Light);
		props.put("buttonColorDark", windowString_Dark);*/
		//props.put("buttonForegroundColor", "255 255 255");

		props.put("rolloverColor", windowString_Light); 
		props.put("rolloverColorLight", windowString_Light); 
		props.put("rolloverColorDark", windowString_Dark);

		props.put("menuColorLight", "219 218 216");
		props.put("menuColorDark", "209 208 206");

		props.put("menuSelectionBackgroundColor", windowString_Light);
		props.put("menuSelectionBackgroundColorLight", windowString_Light);
		props.put("menuSelectionBackgroundColorDark", windowString_Light);

		//props.put("toolbarBackgroundColor", "0 78 78");
		props.put("menuBackgroundColor", "219 218 216");

		props.put("selectionForegroundColor", "255 255 255");
		props.put("selectionBackgroundColor", windowString_Dark);

		props.put("windowTitleForegroundColor", "255 255 255");
		props.put("windowTitleBackgroundColor", windowString_Light); 
		props.put("windowTitleColorLight", windowString_Light); 
		props.put("windowTitleColorDark", windowString_Dark); 
		props.put("windowBorderColor", windowString_Dark);

		props.put("windowInactiveTitleColorLight", windowInactiveString_Light);
		props.put("windowInactiveTitleColorDark", windowInactiveString_Dark);
		props.put("windowInactiveBorderColor", windowInactiveString_Dark);

		props.put("controlTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
		props.put("systemTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
		props.put("userTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
		props.put("menuTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));
		props.put("windowTitleFont", String.format("%s %s %s", font_name, "BOLD", font_size));
		props.put("subTextFont", String.format("%s %s %s", font_name, "PLAIN", font_size));

		AcrylLookAndFeel.setCurrentTheme(props);

		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.background", windowColor);

		//Save
		saveSelectedLookAndFeel(0, 93, 168);

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static void saveSelectedLookAndFeel(Integer red, Integer green, Integer blue) {
		pgSelect db = new pgSelect();
		db.select("SELECT sp_save_look_and_feel('"+ UserInfo.EmployeeCode +"', "+ red +", "+ green +", "+ blue +");");
	}

	public static void listAll() {
		UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();

		for (UIManager.LookAndFeelInfo info : looks) {
			try {
				UIManager.setLookAndFeel(info.getClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

			UIDefaults defaults = UIManager.getDefaults();
			Enumeration newKeys = defaults.keys();

			System.out.println("******************************* Look and Feel!");
			while (newKeys.hasMoreElements()) {
				Object obj = newKeys.nextElement();

				if(obj.toString().contains("Table")){
					System.out.printf("%50s : %s\n", obj.toString(), UIManager.get(obj));
				}

				if(obj.toString().contains("Button")){
					//System.out.printf("%50s : %s\n", obj.toString(), UIManager.get(obj));
				}

				if(obj.toString().contains("OptionPane")){
					System.out.printf("%50s : %s\n", obj.toString(), UIManager.get(obj));
				}
			}
			System.out.println();
			System.out.println();
		}
	}

}
