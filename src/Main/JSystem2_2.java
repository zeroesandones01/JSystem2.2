package Main;

import javax.swing.JOptionPane;

import Functions.FncGlobal;
import Functions.FncLookAndFeel;

import components._JXLoginPane;

public class JSystem2_2 {

	public static void main(String[] args) {
		//FncGlobal.lpsOut = Functions.LoggedPrintStream.create(System.out);
		//System.setOut(FncGlobal.lpsOut);

		FncLookAndFeel.initialize();
		FncGlobal.initialize(false);

		/*UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		for (Enumeration enumm = defaults.keys(); enumm.hasMoreElements();) {
			Object key = enumm.nextElement();
			//Object value = defaults.get(key);

			if(key.toString().contains("Table")){
				//System.out.printf("%s - %s%n", key, UIManager.get(key));
			}
		}*/
		
		//JOptionPane.showMessageDialog(null, "Cannot login system is under maintenance", "Login", JOptionPane.WARNING_MESSAGE);

		FncGlobal.parentFrame = _JXLoginPane.showLoginFrame(FncGlobal.login);
//		//FncGlobal.parentFrame = JXLoginPane.showLoginFrame(FncGlobal.login);
		FncGlobal.parentFrame.setTitle(FncGlobal.ORIGINAL_TITLE);
		FncGlobal.parentFrame.setIconImage(FncLookAndFeel.iconSystem);
		FncGlobal.parentFrame.pack();
		FncGlobal.parentFrame.setVisible(true);
	}
}