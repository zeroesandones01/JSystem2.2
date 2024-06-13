/*
 * Copyright 2002 and later by MH Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */

package components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Functions.FncLookAndFeel;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * @author Michael Hagen
 */
public class JTBorderFactory {
	public static String s_title = "";

	private JTBorderFactory()
	{ }

	public static Border createTitleBorder(Icon icon)
	{ return new TitleBorder(icon, "", 3, 4, null); }

	public static Border createTitleBorder(String title) {
		s_title = title;
		return new TitleBorder(null, title, 3, 4, null);
	}

	public static Border createTitleBorder(String title, Font font) {
		s_title = title;
		return new TitleBorder(null, title, 3, 4, font);
	}

	public static Border createTitleBorder(String title, Font font, Color color) {
		s_title = title;
		return new TitledBorder(null, title, 3, 4, font, color);
	}

	public static Border createTitleBorder(Icon icon, String title)
	{ return new TitleBorder(icon, title, 3, 4, null);  }

	public static Border createTitleBorder(Icon icon, String title, int shadowSize)
	{ return new TitleBorder(icon, title, Math.max(Math.min(shadowSize, 32), 0), 4, null);  }

	public static Border createTitleBorder(Icon icon, String title, int shadowSize, int innerSpace)
	{ return new TitleBorder(icon, title, Math.max(Math.min(shadowSize, 32), 0), Math.max(Math.min(innerSpace, 32), 0), null);  }

	public static Border createTitleComponentBorder(JRadioButton radioButton, JPanel proxyPanel) {
		return new ComponentTitledBorder(radioButton, proxyPanel, BorderFactory.createLineBorder(Color.GRAY));
	}

	public static Border createTitleComponentBorder(JCheckBox checkButton, JPanel proxyPanel) {
		return new ComponentTitledBorder(checkButton, proxyPanel, BorderFactory.createLineBorder(Color.GRAY));
	}

	public String getTitle() {
		return s_title;
	}

	public void setTitle(String title) {
		s_title = title;
	}

	//------------------------------------------------------------------------------------------------------------
	// Border classes
	//------------------------------------------------------------------------------------------------------------
	public static class TitleBorder extends AbstractBorder {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5522033614022662133L;

		private Icon icon = null;
		private String title = null;
		private int shadowSize = 3;
		private int innerSpace = 4;

		private Font font = FncLookAndFeel.systemFont_Bold;

		/*private boolean blinkState;
		private int blinkInterval;
		int xx;
		int yy;*/

		public TitleBorder(Icon aIcon, String aTitle, int aShadowSize, int aInnerSpace, Font font) {
			icon = aIcon;
			setTitle(aTitle);
			shadowSize = aShadowSize;
			innerSpace = aInnerSpace;

			if(font == null){
				this.font = FncLookAndFeel.systemFont_Bold;
			}else{
				this.font = font;
			}
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			Graphics2D g2D = (Graphics2D)g;
			Composite composite = g2D.getComposite();

			Color hiFrameColor = null;
			Color loFrameColor = null;
			Color hiBackColor = null;
			Color loBackColor = null;
			Color textColor = null;

			if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
				//System.out.println("Abstract");
				hiFrameColor = AbstractLookAndFeel.getControlHighlight();
				loFrameColor = AbstractLookAndFeel.getControlShadow();
				hiBackColor = ColorHelper.brighter(AbstractLookAndFeel.getBackgroundColor(), 20);
				loBackColor = ColorHelper.darker(AbstractLookAndFeel.getBackgroundColor(), 5);
				textColor = AbstractLookAndFeel.getForegroundColor();
			}
			else {
				//System.out.println("Not Abstract");
				hiFrameColor = Color.white;
				loFrameColor = Color.gray;
				hiBackColor = ColorHelper.brighter(c.getBackground(), 30.0f);
				loBackColor = ColorHelper.darker(c.getBackground(), 10.0f);
				textColor = c.getForeground();
			}

			int titleHeight = getBorderInsets(c).top - 3 - innerSpace;
			g.setColor(loFrameColor);
			g.drawRect(x, y, w - shadowSize - 1, h - shadowSize - 1);
			g.setColor(hiFrameColor);
			g.drawRect(x + 1, y + 1, w - shadowSize - 3, h - shadowSize - 3);

			g.setColor(loFrameColor);
			g.drawLine(x + 2, y + getBorderInsets(c).top - innerSpace - 1, x + w - shadowSize - 3, y + getBorderInsets(c).top - innerSpace - 1);

			// paint the shadow
			if (shadowSize > 0) {
				g2D.setColor(new Color(0, 16, 0));
				float alphaValue = 0.4f;
				for (int i = 0; i < shadowSize; i++) {
					AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue);
					g2D.setComposite(alpha);
					g.drawLine(x + w - shadowSize + i, y + shadowSize + 4, x + w - shadowSize + i, y + h - shadowSize - 1 + i);
					g.drawLine(x + shadowSize + 2, y + h - shadowSize + i, x + w - shadowSize + i, y + h - shadowSize + i);
					alphaValue -= (alphaValue / 2);
				}
			}

			g2D.setComposite(composite);
			Color[] colors = ColorHelper.createColorArr(hiBackColor, loBackColor, 48);
			JTattooUtilities.fillVerGradient(g, colors, x + 2, y + 2, w - shadowSize - 4, titleHeight);

			paintText(c, g, x, y, w, h, textColor, null);
			/*try {
				paintText(c, g, x, y, w, h, textColor, null);
			} catch (NullPointerException e) { }*/
		}

		private void paintText(final Component c, final Graphics g, int x, int y, int w, int h, final Color textColor, Color shadowColor) {
			/*blinkInterval = 1000;
			blinkState = false;*/

			boolean leftToRight = JTattooUtilities.isLeftToRight(c);
			int sw = w - 8 - (2 * innerSpace);
			if (leftToRight)
				x += 4 + innerSpace;
			else
				x = w - 4 - innerSpace;
			int titleHeight = getBorderInsets(c).top - 3 - innerSpace;

			// paint the icon
			if (icon != null) {
				int yc = y + 2 + ((titleHeight - icon.getIconHeight()) / 2);
				if (leftToRight) {
					icon.paintIcon(c, g, x, yc);
					x += icon.getIconWidth() + 4;
				}
				else {
					icon.paintIcon(c, g, x - icon.getIconWidth(), yc);
					x -= icon.getIconWidth() + 4;
				}
				sw -= icon.getIconWidth();
			}

			// paint the text
			if ((getTitle() != null) && (getTitle().trim().length() > 0)) {
				//g.setFont(c.getFont()); --ORIGINAL 
				g.setFont(font);

				FontMetrics fm = g.getFontMetrics();
				final String theTitle = JTattooUtilities.getClippedText(getTitle(), fm, sw);
				if (!leftToRight)
					x -= fm.getStringBounds(theTitle, g).getWidth();
				y += fm.getHeight();
				if (shadowColor != null) {
					g.setColor(shadowColor);
					g.drawString(theTitle, x + 1, y + 1);
				}
				g.setColor(textColor);



				/**
				 * 
				 */
				/*xx = x;
				yy = y;
				Timer makeItBlink = new Timer(blinkInterval, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						blinkState = !blinkState;
						if (blinkState) {
							//setBlinkTextColor();
							System.out.printf("Blink%n", "");
							g.setColor(Color.WHITE);
						} else {
							//setNormalTextColor();
							System.out.printf("Normal%n", "");
							g.setColor(Color.BLACK);
						}
					}
				});
				// start it up
				makeItBlink.start();*/

				if (c instanceof JComponent) {
					//System.out.printf("IF%n", "");
					try {
						JTattooUtilities.drawString((JComponent)c, g, theTitle, x, y);
					} catch (NullPointerException e) { }
				} else {
					//System.out.printf("ELSE%n", "");
					g.drawString(theTitle, x, y);
				}
			}
		}

		public Insets getBorderInsets(Component c) {
			Graphics g = c.getGraphics();
			if (g != null) {
				FontMetrics fm = g.getFontMetrics(c.getFont());
				int frameWidth = 2 + innerSpace;
				int titleHeight = fm.getHeight() + (fm.getHeight() / 4);
				if (icon != null)
					titleHeight = Math.max(titleHeight, icon.getIconHeight() + (icon.getIconHeight() / 4));
				return new Insets(titleHeight + frameWidth, frameWidth, frameWidth + shadowSize, frameWidth + shadowSize);
			}
			return new Insets(0, 0, 0, 0);
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	public static class ComponentTitledBorder implements Border, MouseListener, SwingConstants {
		int offset = 5;

		Component comp;
		JComponent container;
		Rectangle rect;
		Border border;

		public ComponentTitledBorder(Component comp, JComponent container,
				Border border) {
			this.comp = comp;
			this.container = container;
			this.border = border;
			container.addMouseListener(this);
		}

		public boolean isBorderOpaque() {
			return true;
		}

		public void paintBorder(Component c, Graphics g, int x, int y,
				int width, int height) {
			Insets borderInsets = border.getBorderInsets(c);
			Insets insets = getBorderInsets(c);
			int temp = (insets.top - borderInsets.top) / 2;
			border.paintBorder(c, g, x, y + temp, width, height - temp);
			Dimension size = comp.getPreferredSize();
			rect = new Rectangle(offset, 0, size.width, size.height);
			SwingUtilities.paintComponent(g, comp, (Container) c, rect);
		}

		public Insets getBorderInsets(Component c) {
			Dimension size = comp.getPreferredSize();
			Insets insets = border.getBorderInsets(c);
			insets.top = Math.max(insets.top, size.height);
			return insets;
		}

		private void dispatchEvent(MouseEvent me) {
			if (rect != null && rect.contains(me.getX(), me.getY())) {
				Point pt = me.getPoint();
				pt.translate(-offset, 0);
				comp.setBounds(rect);
				comp.dispatchEvent(new MouseEvent(comp, me.getID(), me
						.getWhen(), me.getModifiers(), pt.x, pt.y, me
						.getClickCount(), me.isPopupTrigger(), me.getButton()));
				if (!comp.isValid())
					container.repaint();
			}
		}

		public void mouseClicked(MouseEvent me) {
			dispatchEvent(me);
		}

		public void mouseEntered(MouseEvent me) {
			dispatchEvent(me);
		}

		public void mouseExited(MouseEvent me) {
			dispatchEvent(me);
		}

		public void mousePressed(MouseEvent me) {
			dispatchEvent(me);
		}

		public void mouseReleased(MouseEvent me) {
			dispatchEvent(me);
		}
	}

}
