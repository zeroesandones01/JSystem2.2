package Functions;

import java.awt.Color;

import javax.swing.UIManager;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.PinstripePainter;

public class FncPanelPainter {

	public static CompoundPainter paint() {
		GlossPainter gloss = new GlossPainter();

		PinstripePainter stripes = new PinstripePainter();
		stripes.setPaint(new Color(1.0f, 1.0f, 1.0f, 0.17f));
		stripes.setSpacing(4.0);

		MattePainter matte = new MattePainter(UIManager.getColor("Table.selectionBackground"));

		return new CompoundPainter(matte, stripes, gloss);
	}

	public static CompoundPainter paint(Color cl) {
		GlossPainter gloss = new GlossPainter();

		PinstripePainter stripes = new PinstripePainter();
		stripes.setPaint(new Color(1.0f, 1.0f, 1.0f, 0.17f));
		stripes.setSpacing(4.0);

		MattePainter matte = new MattePainter(cl);

		return new CompoundPainter(matte, stripes, gloss);
	}

}
