package interfaces;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;


public interface Interface_Report {

	public void initGUI(); 
	public void preview();
	public void export();
	public void generate();
	public void cancel();
	public void refresh();
	
	public void initializeInputFields();
	
	public void defaultValues();
	public void buttonState(); 
	public void controlState();
	public void createButton();
	public void disableControls();
	
	public static final BorderLayout borderLayout = new BorderLayout(5, 5);  
	//public static final Border borderDetail = JTBorderFactory.createTitleBorder("Details", _LookAndFeel.systemFont_Bold.deriveFont(10f));
	public static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
}
