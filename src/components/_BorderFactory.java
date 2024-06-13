package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

public class _BorderFactory extends AbstractBorder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Color borderColor = Color.GRAY;
	protected int arch;

	public _BorderFactory() {
		this(Color.GRAY, 10);
	}

	public _BorderFactory(Color borderColor, int arch) {
		super();
		this.borderColor = borderColor;
		this.arch = arch;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		RoundRectangle2D round = new RoundRectangle2D.Float(x, y, width-1, height-1, arch, arch);
		Container parent = c.getParent();
		if(parent!=null) {
			g2.setColor(parent.getBackground());
			Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
			corner.subtract(new Area(round));
			g2.fill(corner);
		}
		g2.setColor(borderColor);
		g2.draw(round);
		g2.dispose();
	}
	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(4, 8, 4, 8);
	}
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = insets.right = 8;
		insets.top = insets.bottom = 4;
		return insets;
	}
}
