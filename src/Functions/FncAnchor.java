package Functions;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;

public class FncAnchor implements ComponentListener{

	// XXX Minumum size is set on the Main JFrame/JDesktopPane
	// XXX container.addComponentListener(new FncAnchor(componentToAnchor, containerHeight, containerWidth, heightWillAdjust, widthWillAdjust, xLocationWillAdjust, yLocationWillAdjust, xLocationHasRatio, yLocationHasRatio))
	
	private JComponent comp;
	private boolean htWillAdjust, wdWillAdjust, xWillAdjust, yWillAdjust, xHasRatio, yHasRatio;
	private int origMainHt, origMainWd, origCompHt, origCompWd, origX, origY;
	
	public FncAnchor(
			JComponent componentToAnchor,
			int containerHeight,
			int containerWidth,
			boolean heightWillAdjust,
			boolean widthWillAdjust,
			boolean xLocationWillAdjust,
			boolean yLocationWillAdjust,
			boolean xLocationHasRatio,
			boolean yLocationHasRatio ) {
		comp = componentToAnchor;
		htWillAdjust = heightWillAdjust;
		wdWillAdjust = widthWillAdjust;
		xWillAdjust = xLocationWillAdjust;
		yWillAdjust = yLocationWillAdjust;
		xHasRatio = xLocationHasRatio;
		yHasRatio = yLocationHasRatio;
		
		origMainHt = containerHeight;
		origMainWd = containerWidth;
		origCompHt = comp.getHeight();
		origCompWd = comp.getWidth();
		origX = comp.getX();
		origY = comp.getY();
	}
	
	public void componentResized(ComponentEvent evt) {
		int x = origX;
		int y = origY;
		int ht = origCompHt;
		int wd = origCompWd;

		if ( htWillAdjust ) {
			ht = origCompHt + (evt.getComponent().getHeight() - origMainHt);
		//	System.out.println( "ht:" + ht );
		}
		if ( wdWillAdjust ) {
			wd = origCompWd + (evt.getComponent().getWidth() - origMainWd);
		//	System.out.println( "wd:" + wd );
		}
		if ( xWillAdjust ) {
			if ( xHasRatio )
				x = (evt.getComponent().getWidth()/2) +  (origX-(origMainWd/2));
			else
				x = origX + (evt.getComponent().getWidth() - origMainWd);
		//	System.out.println( "x :" + x );
		}
		if ( yWillAdjust ) {
			if ( yHasRatio )
				y = (evt.getComponent().getHeight()/2) +  (origY-(origMainHt/2));
			else
				y = origY + (evt.getComponent().getHeight() - origMainHt);
		//	System.out.println( "y :" + y );
		}
		comp.setBounds(x, y, wd, ht);
	} // componentResized
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}

}
