package Functions;

import java.awt.Color;

import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;

public class FncHighlighter extends ColorHighlighter {

	public FncHighlighter() {

	}

	public FncHighlighter(HighlightPredicate predicate) {
		super(predicate);
	}

	public FncHighlighter(Color cellBackground, Color cellForeground) {
		super(cellBackground, cellForeground);
	}

	public FncHighlighter(HighlightPredicate predicate, Color cellBackground, Color cellForeground) {
		super(predicate, cellBackground, cellForeground);
	}

	public FncHighlighter(Color cellBackground, Color cellForeground, Color selectedBackground, Color selectedForeground) {
		super(cellBackground, cellForeground, selectedBackground, selectedForeground);
	}

	public FncHighlighter(HighlightPredicate predicate, Color cellBackground, Color cellForeground, Color selectedBackground, Color selectedForeground) {
		super(predicate, cellBackground, cellForeground, selectedBackground, selectedForeground);
	}

	@Override
	public Color getBackground() {
		return super.getBackground();
	}

}
