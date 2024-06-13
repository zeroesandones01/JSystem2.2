/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package jasper;

import java.awt.Image;
import java.io.InputStream;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.util.JRLoader;


/**
 * Facade class for the printing functionality exposed by the JasperReports library.
 * <p>
 * After having filled a report, you have the option of viewing it, exporting it to a different
 * format, or (most commonly) printing it.
 * <p>
 * In JasperReports, you can print reports using this manager class. It contains various methods that
 * can send entire documents or portions of them to the printer. It also allows people to choose
 * whether to display the print dialog. one can display the content of a page from a
 * JasperReports document by generating a <code>java.awt.Image</code> object for it using this
 * manager class.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: _JasperPrintManager.java,v 1.1 2019/04/10 00:30:01 jfatallo Exp $
 */
public final class _JasperPrintManager {
	
	private JasperReportsContext jasperReportsContext;


	/**
	 *
	 */
	private _JasperPrintManager(JasperReportsContext jasperReportsContext) {
		this.jasperReportsContext = jasperReportsContext;
	}
	
	
	/**
	 *
	 */
	private static _JasperPrintManager getDefaultInstance() {
		return new _JasperPrintManager(
				DefaultJasperReportsContext.getInstance());
	}
	
	
	/**
	 *
	 */
	public static _JasperPrintManager getInstance(JasperReportsContext jasperReportsContext) {
		return new _JasperPrintManager(jasperReportsContext);
	}
	
	
	/**
	 *
	 */
	public boolean print(String sourceFileName, boolean withPrintDialog) throws JRException {
		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObjectFromFile(sourceFileName);

		return print(jasperPrint, withPrintDialog);
	}


	/**
	 *
	 */
	public boolean print(InputStream inputStream, boolean withPrintDialog) throws JRException {
		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(inputStream);

		return print(jasperPrint, withPrintDialog);
	}


	/**
	 *
	 */
	public boolean print(JasperPrint jasperPrint, boolean withPrintDialog) throws JRException {//XXX #1
		// artf1936
		boolean checkAvailablePrinters = JRPropertiesUtil.getInstance(jasperReportsContext).getBooleanProperty(jasperPrint, PROPERTY_CHECK_AVAILABLE_PRINTERS, true);
		if (checkAvailablePrinters && !(unixSunJDK || JRPrintServiceExporter.checkAvailablePrinters())) {
			throw new JRException("No printer available.");
		}
		// END - artf1936

		return print(jasperPrint, 0, jasperPrint.getPages().size() - 1, withPrintDialog);
	}


	/**
	 * Added by Alvin Gonzales (2015-10-12)
	 */
	public boolean print(JasperPrint jasperPrint, boolean withPrintDialog, String doc_id, String entity_id, String proj_id, String pbl_id, Integer seq_no) throws JRException {//XXX #1
		// artf1936
		boolean checkAvailablePrinters = JRPropertiesUtil.getInstance(jasperReportsContext).getBooleanProperty(jasperPrint, PROPERTY_CHECK_AVAILABLE_PRINTERS, true);
		if (checkAvailablePrinters && !(unixSunJDK || JRPrintServiceExporter.checkAvailablePrinters())) {
			throw new JRException("No printer available.");
		}
		// END - artf1936

		return print(jasperPrint, 0, jasperPrint.getPages().size() - 1, withPrintDialog, doc_id, entity_id, proj_id, pbl_id, seq_no);
	}


	/**
	 *
	 */
	public boolean print(String sourceFileName, int pageIndex, boolean withPrintDialog) throws JRException {
		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObjectFromFile(sourceFileName);

		return print(jasperPrint, pageIndex, withPrintDialog);
	}


	/**
	 *
	 */
	public boolean print(InputStream inputStream, int pageIndex, boolean withPrintDialog) throws JRException {
		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(inputStream);

		return print(jasperPrint, pageIndex, withPrintDialog);
	}


	/**
	 *
	 */
	public boolean print(JasperPrint jasperPrint, int pageIndex, boolean withPrintDialog) throws JRException {
		return print(jasperPrint, pageIndex, pageIndex, withPrintDialog);
	}


	/**
	 *
	 */
	public boolean print(String sourceFileName, int firstPageIndex, int lastPageIndex, boolean withPrintDialog) throws JRException {
		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObjectFromFile(sourceFileName);

		return print(jasperPrint, firstPageIndex, lastPageIndex, withPrintDialog);
	}


	/**
	 *
	 */
	public boolean print(InputStream inputStream, int firstPageIndex, int lastPageIndex, boolean withPrintDialog) throws JRException {
		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(inputStream);

		return print(jasperPrint, firstPageIndex, lastPageIndex, withPrintDialog);
	}


	/**
	 *
	 */
	public boolean print(JasperPrint jasperPrint, int firstPageIndex, int lastPageIndex, boolean withPrintDialog) throws JRException {//XXX #2
		return new _JRPrinterAWT(jasperReportsContext, jasperPrint).printPages(firstPageIndex, lastPageIndex, withPrintDialog);
	}


	/**
	 * Added by Alvin Gonzales (2015-10-12)
	 */
	public boolean print(JasperPrint jasperPrint, int firstPageIndex, int lastPageIndex, boolean withPrintDialog, String doc_id, String entity_id, String proj_id, String pbl_id, Integer seq_no) throws JRException {//XXX #2
		return new _JRPrinterAWT(jasperReportsContext, jasperPrint).printPages(firstPageIndex, lastPageIndex, withPrintDialog, doc_id, entity_id, proj_id, pbl_id, seq_no);
	}


	/**
	 *
	 */
	public Image printToImage(String sourceFileName, int pageIndex, float zoom) throws JRException {
		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObjectFromFile(sourceFileName);

		return printToImage(jasperPrint, pageIndex, zoom);
	}

	/**
	 *
	 */
	public Image printToImage(InputStream inputStream, int pageIndex, float zoom) throws JRException {
		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(inputStream);

		return printToImage(jasperPrint, pageIndex, zoom);
	}

	/**
	 *
	 */
	public Image printToImage(JasperPrint jasperPrint, int pageIndex, float zoom) throws JRException {
		return new _JRPrinterAWT(jasperReportsContext, jasperPrint).printPageToImage(pageIndex, zoom);
	}

	/**
	 * @see #print(String, boolean)
	 */
	public static boolean printReport(String sourceFileName, boolean withPrintDialog) throws JRException {
		return getDefaultInstance().print(sourceFileName, withPrintDialog);
	}

	/**
	 * @see #print(InputStream, boolean)
	 */
	public static boolean printReport(InputStream inputStream, boolean withPrintDialog) throws JRException {
		return getDefaultInstance().print(inputStream, withPrintDialog);
	}

	/**
	 * @see #print(JasperPrint, boolean)
	 */
	public static boolean printReport(JasperPrint jasperPrint, boolean withPrintDialog) throws JRException {
		return getDefaultInstance().print(jasperPrint, withPrintDialog);
	}

	/**
	 * @see #print(String, int, boolean)
	 */
	public static boolean printPage(String sourceFileName, int pageIndex, boolean withPrintDialog) throws JRException {
		return getDefaultInstance().print(sourceFileName, pageIndex,
				withPrintDialog);
	}

	/**
	 * @see #print(InputStream, int, boolean)
	 */
	public static boolean printPage(InputStream inputStream, int pageIndex, boolean withPrintDialog) throws JRException {
		return getDefaultInstance().print(inputStream, pageIndex,
				withPrintDialog);
	}

	/**
	 * @see #print(JasperPrint, int, boolean)
	 */
	public static boolean printPage(JasperPrint jasperPrint, int pageIndex, boolean withPrintDialog) throws JRException {
		return getDefaultInstance().print(jasperPrint, pageIndex,
				withPrintDialog);
	}

	/**
	 * @see #print(String, int, int, boolean)
	 */
	public static boolean printPages(String sourceFileName, int firstPageIndex, int lastPageIndex, boolean withPrintDialog) throws JRException {
		return getDefaultInstance().print(sourceFileName, firstPageIndex,
				lastPageIndex, withPrintDialog);
	}

	/**
	 * @see #print(InputStream, int, int, boolean)
	 */
	public static boolean printPages(InputStream inputStream, int firstPageIndex, int lastPageIndex, boolean withPrintDialog) throws JRException {
		return getDefaultInstance().print(inputStream, firstPageIndex,
				lastPageIndex, withPrintDialog);
	}

	/**
	 * @see #print(JasperPrint, int, int, boolean)
	 */
	public static boolean printPages(JasperPrint jasperPrint, int firstPageIndex, int lastPageIndex, boolean withPrintDialog) throws JRException {
		return getDefaultInstance().print(jasperPrint, firstPageIndex,
				lastPageIndex, withPrintDialog);
	}

	/**
	 * @see #printToImage(String, int, float)
	 */
	public static Image printPageToImage(String sourceFileName, int pageIndex, float zoom) throws JRException {
		return getDefaultInstance().printToImage(sourceFileName, pageIndex,
				zoom);
	}

	/**
	 * @see #printToImage(InputStream, int, float)
	 */
	public static Image printPageToImage(InputStream inputStream, int pageIndex, float zoom) throws JRException {
		return getDefaultInstance().printToImage(inputStream, pageIndex, zoom);
	}

	/**
	 * @see #printToImage(JasperPrint, int, float)
	 */
	public static Image printPageToImage(JasperPrint jasperPrint, int pageIndex, float zoom) throws JRException {
		return getDefaultInstance().printToImage(jasperPrint, pageIndex, zoom);
	}

	/**
	 * Property whose value is used to check the availability of printers
	 * accepting jobs.
	 * <p/>
	 * This property is by default set to <code>true</code>.
	 */
	public static final String PROPERTY_CHECK_AVAILABLE_PRINTERS = JRPropertiesUtil.PROPERTY_PREFIX + "awt.check.available.printers";

	/*
	 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6604109 (artf2423)
	 * workaround
	 */
	protected static final boolean unixSunJDK;
	static {
		boolean found = false;
		try {
			Class.forName("sun.print.UnixPrintServiceLookup");
			found = true;
		} catch (ClassNotFoundException e) {
			found = false;
		}
		unixSunJDK = found;
	}
}
