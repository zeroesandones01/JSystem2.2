package Main;

import Functions.FncGlobal;
import Functions.FncLookAndFeel;

import components._JXLoginPane;

public class _JSystem2_2 {

	public static void main(String[] args) {
		FncLookAndFeel.initialize();
		FncGlobal.initialize(true);

		FncGlobal.parentFrame = _JXLoginPane.showLoginFrame(FncGlobal.login);
		FncGlobal.parentFrame.setTitle(FncGlobal.ORIGINAL_TITLE);
		FncGlobal.parentFrame.setIconImage(FncLookAndFeel.iconSystem);
		FncGlobal.parentFrame.pack();
		FncGlobal.parentFrame.setVisible(true);
	}
}