package Functions;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public final class RGBGrayFilter extends RGBImageFilter {

	/**
	 * Overrides default constructor; prevents instantiation.
	 */
	private RGBGrayFilter() {
		canFilterIndexColorModel = true;
	}

	/**
	 * Returns an icon with a disabled appearance. This method is used to
	 * generate a disabled icon when one has not been specified.
	 * 
	 * @param component
	 *            the component that will display the icon, may be null.
	 * @param icon
	 *            the icon to generate disabled icon from.
	 * @return disabled icon, or null if a suitable icon can not be generated.
	 */
	public static Icon getDisabledIcon(JComponent component, Icon icon) {
		if ((icon == null) || (component == null) || (icon.getIconWidth() == 0) || (icon.getIconHeight() == 0)) {
			return null;
		}

		Image img;
		if (icon instanceof ImageIcon) {
			img = ((ImageIcon) icon).getImage();
		} else {
			img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			icon.paintIcon(component, img.getGraphics(), 0, 0);
		}

		/*if (!Options.isHiResGrayFilterEnabled() || (Boolean.FALSE.equals(component.getClientProperty(Options.HI_RES_DISABLED_ICON_CLIENT_KEY)))) {
			return new ImageIcon(GrayFilter.createDisabledImage(img));
		}*/

		ImageProducer producer = new FilteredImageSource(img.getSource(), new RGBGrayFilter());

		return new ImageIcon(component.createImage(producer));
	}

	/**
	 * Converts a single input pixel in the default RGB ColorModel to a single
	 * gray pixel.
	 * 
	 * @param x
	 *            the horizontal pixel coordinate
	 * @param y
	 *            the vertical pixel coordinate
	 * @param rgb
	 *            the integer pixel representation in the default RGB color
	 *            model
	 * @return a gray pixel in the default RGB color model.
	 * 
	 * @see ColorModel#getRGBdefault
	 * @see #filterRGBPixels
	 */
	public int filterRGB(int x, int y, int rgb) {
		// Find the average of red, green, and blue.
		float avg = (((rgb >> 16) & 0xff) / 255f + ((rgb >> 8) & 0xff) / 255f + (rgb & 0xff) / 255f) / 3;
		// Pull out the alpha channel.
		float alpha = (((rgb >> 24) & 0xff) / 255f);

		// Calculate the average.
		// Sun's formula: Math.min(1.0f, (1f - avg) / (100.0f / 35.0f) + avg);
		// Andrej: the following formula uses less operations and hence is
		// faster.
		avg = Math.min(1.0f, 0.35f + 0.65f * avg);
		// Turn back into RGB.
		int rgbval = (int) (alpha * 255f) << 24 | (int) (avg * 255f) << 16
				| (int) (avg * 255f) << 8 | (int) (avg * 255f);
		return rgbval;
	}

}