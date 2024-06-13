package Functions;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FncFileChooser {

	public static File[] ImageFileChooser() {
		
		JFileChooser chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Folder");
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", ImageIO.getReaderFileSuffixes()); 
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(true);
		chooser.setAcceptAllFileFilterUsed(false);
    
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){ 
			return chooser.getSelectedFiles(); 
		}
		else{
			System.out.println("No Selection ");
			return null; 
		}
	}

}
