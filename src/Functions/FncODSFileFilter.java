package Functions;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Alvin Gonzales - 2015-01-03
 *
 */
public class FncODSFileFilter extends FileFilter {

	String[] extensions;

	String description;

	/**
	 * 
	 */
	public FncODSFileFilter(String ext) {
		this(new String[] { ext }, null);
	}

	/**
	 * 
	 */
	public FncODSFileFilter(String[] exts, String descr) {
		// Clone and lowercase the extensions
		extensions = new String[exts.length];
		for (int i = exts.length - 1; i >= 0; i--) {
			extensions[i] = exts[i].toLowerCase();
		}
		// Make sure we have a valid (if simplistic) description
		description = (descr == null ? exts[0] + " files" : descr);
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File f) {
		// We always allow directories, regardless of their extension
		if (f.isDirectory()) {
			return true;
		}

		// Ok, it's a regular file, so check the extension
		String name = f.getName().toLowerCase();
		for (int i = extensions.length - 1; i >= 0; i--) {
			if (name.endsWith(extensions[i])) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

}
