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

/*
 * Contributors:
 * Ryan Johnson - delscovich@users.sourceforge.net
 * Carlton Moore - cmoore79@users.sourceforge.net
 *  Petr Michalek - pmichalek@users.sourceforge.net
 */
package jasper;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.swing.JRViewerController;
import net.sf.jasperreports.swing.JRViewerEvent;
import net.sf.jasperreports.swing.JRViewerListener;
import net.sf.jasperreports.swing.JRViewerPanel;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: _JRViewer.java,v 1.1 2019/04/10 00:30:01 jfatallo Exp $
 */
public class _JRViewer extends javax.swing.JPanel implements JRViewerListener {

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 * Maximum size (in pixels) of a buffered image that would be used by {@link JRViewer JRViewer} to render a report page.
	 * <p>
	 * If rendering a report page would require an image larger than this threshold
	 * (i.e. image width x image height > maximum size), the report page will be rendered directly on the viewer component.
	 * </p>
	 * <p>
	 * If this property is zero or negative, buffered images will never be user to render a report page.
	 * By default, this property is set to 0.
	 * </p>
	 */
	public static final String VIEWER_RENDER_BUFFER_MAX_SIZE = JRPropertiesUtil.PROPERTY_PREFIX + "viewer.render.buffer.max.size";

	protected JRViewerController viewerContext;

	/**
	 * @see #JRViewer(JasperReportsContext, String, boolean, Locale, ResourceBundle)
	 */
	public _JRViewer(String fileName, boolean isXML) throws JRException {
		this(fileName, isXML, null);
	}


	/**
	 * @see #JRViewer(JasperReportsContext, InputStream, boolean, Locale, ResourceBundle)
	 */
	public _JRViewer(InputStream is, boolean isXML) throws JRException {
		this(is, isXML, null);
	}


	/**
	 * @see #JRViewer(JasperReportsContext, JasperPrint, Locale, ResourceBundle)
	 */
	public _JRViewer(JasperPrint jrPrint) {
		this(jrPrint, null);
	}


	/**
	 * @see #JRViewer(JasperReportsContext, String, boolean, Locale, ResourceBundle)
	 */
	public _JRViewer(String fileName, boolean isXML, Locale locale) throws JRException {
		this(fileName, isXML, locale, null);
	}


	/**
	 * @see #JRViewer(InputStream, boolean, Locale, ResourceBundle)
	 */
	public _JRViewer(InputStream is, boolean isXML, Locale locale) throws JRException {
		this(is, isXML, locale, null);
	}


	/**
	 * @see #JRViewer(JasperPrint, Locale, ResourceBundle)
	 */
	public _JRViewer(JasperPrint jrPrint, Locale locale) {
		this(jrPrint, locale, null);
	}


	/**
	 * @see #JRViewer(JasperPrint, Locale, ResourceBundle) Added by Alvin Gonzales (2015-10-12)
	 */
	public _JRViewer(JasperPrint jrPrint, Locale locale, String doc_id, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean printable) {
		this(jrPrint, locale, null, doc_id, entity_id, proj_id, pbl_id, seq_no, printable);
	}


	/**
	 * @see #JRViewer(String, boolean, Locale, ResourceBundle)
	 */
	public _JRViewer(String fileName, boolean isXML, Locale locale, ResourceBundle resBundle) throws JRException {
		this(DefaultJasperReportsContext.getInstance(), fileName, isXML, locale, resBundle);
	}


	/**
	 * @see #JRViewer(InputStream, boolean, Locale, ResourceBundle)
	 */
	public _JRViewer(InputStream is, boolean isXML, Locale locale, ResourceBundle resBundle) throws JRException {
		this(DefaultJasperReportsContext.getInstance(), is, isXML, locale, resBundle);
	}


	/**
	 * @see #JRViewer(JasperReportsContext, JasperPrint, Locale, ResourceBundle)
	 */
	public _JRViewer(JasperPrint jrPrint, Locale locale, ResourceBundle resBundle) {
		this(DefaultJasperReportsContext.getInstance(), jrPrint, locale, resBundle);
	}


	/**
	 * @see #JRViewer(JasperReportsContext, JasperPrint, Locale, ResourceBundle) Added by Alvin Gonzales (2015-10-12)
	 */
	public _JRViewer(JasperPrint jrPrint, Locale locale, ResourceBundle resBundle, String doc_id, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean printable) {
		this(DefaultJasperReportsContext.getInstance(), jrPrint, locale, resBundle, doc_id, entity_id, proj_id, pbl_id, seq_no, printable);
	}


	/**
	 * 
	 */
	public _JRViewer(JasperReportsContext jasperReportsContext, String fileName, boolean isXML, Locale locale, ResourceBundle resBundle) throws JRException {
		initViewerContext(jasperReportsContext, locale, resBundle);

		initComponents(null, null, null, null, null, true);

		viewerContext.loadReport(fileName, isXML);

		tlbToolBar.init();
	}


	/**
	 * 
	 */
	public _JRViewer(JasperReportsContext jasperReportsContext, InputStream is, boolean isXML, Locale locale, ResourceBundle resBundle) throws JRException {
		initViewerContext(jasperReportsContext, locale, resBundle);

		initComponents(null, null, null, null, null, true);

		viewerContext.loadReport(is, isXML);

		tlbToolBar.init();
	}


	/**
	 * 
	 */
	public _JRViewer(JasperReportsContext jasperReportsContext, JasperPrint jrPrint, Locale locale, ResourceBundle resBundle) {
		initViewerContext(jasperReportsContext, locale, resBundle);

		initComponents(null, null, null, null, null, true);

		viewerContext.loadReport(jrPrint);

		tlbToolBar.init();
	}


	/**
	 * Added by Alvin Gonzales (2015-10-12)
	 */
	public _JRViewer(JasperReportsContext jasperReportsContext, JasperPrint jrPrint, Locale locale, ResourceBundle resBundle, String doc_id, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean printable) {
		initViewerContext(jasperReportsContext, locale, resBundle);

		initComponents(doc_id, entity_id, proj_id, pbl_id, seq_no, printable);

		viewerContext.loadReport(jrPrint);

		tlbToolBar.init();
	}


	/**
	 * @deprecated Replaced by {@link #initViewerContext(JasperReportsContext, Locale, ResourceBundle)}.
	 */
	protected void initViewerContext(Locale locale, ResourceBundle resBundle) {
		initViewerContext(DefaultJasperReportsContext.getInstance(), locale,
				resBundle);
	}


	/**
	 *
	 */
	protected void initViewerContext(JasperReportsContext jasperReportsContext, Locale locale, ResourceBundle resBundle) {
		viewerContext = new JRViewerController(jasperReportsContext, locale, resBundle);
		setLocale(viewerContext.getLocale());
		viewerContext.addListener(this);
	}


	/**
	 *
	 */
	public void clear() {
		emptyContainer(this);
		viewerContext.clear();
	}


	/**
	 *
	 */
	protected String getBundleString(String key) {
		return viewerContext.getBundleString(key);
	}


	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents(String doc_id, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean printable) {
		tlbToolBar = createToolbar(doc_id, entity_id, proj_id, pbl_id, seq_no, printable);

		pnlMain = createViewerPanel();

		lblStatus = new javax.swing.JLabel();
		pnlStatus = new javax.swing.JPanel();

		pnlStatus.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

		lblStatus.setFont(new java.awt.Font("Dialog", 1, 10));
		lblStatus.setText("Page i of n");
		pnlStatus.add(lblStatus);

		setLayout(new java.awt.BorderLayout());

		add(tlbToolBar, java.awt.BorderLayout.NORTH);
		add(pnlMain, java.awt.BorderLayout.CENTER);
		add(pnlStatus, java.awt.BorderLayout.SOUTH);

		KeyListener keyNavigationListener = pnlMain.getKeyNavigationListener();
		addKeyListener(keyNavigationListener);
		tlbToolBar.addComponentKeyListener(keyNavigationListener);
	}
	// </editor-fold>//GEN-END:initComponents

	protected _JRViewerToolbar createToolbar(String doc_id, String entity_id, String proj_id, String pbl_id, Integer seq_no, Boolean printable) {
		return new _JRViewerToolbar(viewerContext, doc_id, entity_id, proj_id, pbl_id, seq_no, printable);
	}

	//FIXME add a method to do addHyperlinkListener without subclassing
	protected JRViewerPanel createViewerPanel() {
		return new JRViewerPanel(viewerContext);
	}

	public void setFitWidthZoomRatio() {
		pnlMain.setFitWidthZoomRatio();
	}

	public void setFitPageZoomRatio() {
		pnlMain.setFitPageZoomRatio();
	}

	/**
	 */
	public int getPageIndex() {
		return viewerContext.getPageIndex();
	}


	/**
	 */
	private void emptyContainer(Container container) {
		Component[] components = container.getComponents();

		if (components != null) {
			for (int i = 0; i < components.length; i++) {
				if (components[i] instanceof Container) {
					emptyContainer((Container) components[i]);
				}
			}
		}

		components = null;
		container.removeAll();
		container = null;
	}

	public void setZoomRatio(float zoomRatio) {
		viewerContext.setZoomRatio(zoomRatio);
	}

	public void pageChanged() {
		if (viewerContext.hasPages()) {
			lblStatus.setText(MessageFormat.format(getBundleString("page"), new Object[] { Integer.valueOf(viewerContext.getPageIndex() + 1), Integer.valueOf(viewerContext.getPageCount()) }));
		} else {
			lblStatus.setText("");
		}
	}

	public void viewerEvent(JRViewerEvent event) {
		switch (event.getCode()) {
		case JRViewerEvent.EVENT_PAGE_CHANGED:
			pageChanged();
			break;
		case JRViewerEvent.EVENT_REPORT_LOAD_FAILED:
			JOptionPane.showMessageDialog(this, getBundleString("error.loading"));
			break;
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	protected javax.swing.JLabel lblStatus;
	private JRViewerPanel pnlMain;
	protected javax.swing.JPanel pnlStatus;
	protected _JRViewerToolbar tlbToolBar;
	// End of variables declaration//GEN-END:variables

}
