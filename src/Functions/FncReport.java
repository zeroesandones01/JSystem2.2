package Functions;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.springframework.util.ResourceUtils;

import com.lowagie.text.pdf.PdfWriter;

import Database.pgSelect;
import jasper._JasperViewer;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

public class FncReport {

	public FncReport() {
		// TODO Auto-generated constructor stub
	}

	public static void generateReport(final String report, final String title, final Map mapParameters) {
		Runnable runPreview = new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating " + title + " Report");

				try {
					JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, FncGlobal.connection);
					if(previewSalesReport.getPages().size() > 0){
						if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title, proj_id, phase));
							FncGlobal.homeMDI.addWindow(printAssetSticker);
							printAssetSticker.setMaximum(true);*/

							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title));
							printAssetSticker.setVisible(true);*/

							JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
							printAssetSticker.setTitle(FncGlobal.getReportTitle(title));
							printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
							printAssetSticker.setLocationRelativeTo(null);
							printAssetSticker.setVisible(true);
							//printAssetSticker.requestFocusInWindow();
							FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
						}
					}else{
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}/* catch (PropertyVetoException e) {
					e.printStackTrace();
				}*/
				FncGlobal.stopProgress();
			}
		};

		FncGlobal.loadThread(title, runPreview);
	}

	/**
	 * Added Christian for notices not show on top the report
	 * @param report
	 * @param title
	 * @param mapParameters
	 */
	public static void generateReports(final String report, final String title, final Map mapParameters) {
		Runnable runPreview = new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating " + title + " Report");

				try {
					JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, FncGlobal.connection);
					if(previewSalesReport.getPages().size() > 0){
						if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title, proj_id, phase));
							FncGlobal.homeMDI.addWindow(printAssetSticker);
							printAssetSticker.setMaximum(true);*/

							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title));
							printAssetSticker.setVisible(true);*/

							JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
							printAssetSticker.setTitle(FncGlobal.getReportTitle(title));
							printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
							printAssetSticker.setLocationRelativeTo(FncGlobal.homeMDI);
							printAssetSticker.setVisible(true);

							JDialog dialog = new JDialog(FncGlobal.homeMDI);//the owner
							dialog.setContentPane(printAssetSticker.getContentPane());
							dialog.setSize(printAssetSticker.getSize());
							dialog.setTitle(title);
							dialog.setResizable(true);
							dialog.setVisible(true);
							
							FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");

						}
					}else{
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}/* catch (PropertyVetoException e) {
					e.printStackTrace();
				}*/
				FncGlobal.stopProgress();
			}
		};

		FncGlobal.loadThread(title, runPreview);
	}
	public static void generateReport(final String report, final String title, final String company, final Map mapParameters) {

		Runnable runPreview = new Runnable() {
			public void run() {
				System.out.println("report: " + report);

				FncGlobal.startProgress("Generating " + title + " Report");

				try {
					JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, FncGlobal.connection);
					if(previewSalesReport.getPages().size() > 0){
						if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title, proj_id, phase));
							FncGlobal.homeMDI.addWindow(printAssetSticker);
							printAssetSticker.setMaximum(true);*/

							JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
							printAssetSticker.setTitle(FncGlobal.getReportTitle(title, company));
							printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
							printAssetSticker.setLocationRelativeTo(null);
							printAssetSticker.setVisible(true);
							
							FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
						}
					}else{
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}/* catch (PropertyVetoException e) {
					e.printStackTrace();
				}*/
				FncGlobal.stopProgress();
			}
		};

		FncGlobal.loadThread(title, runPreview);
	}

	public static void generateReport(final String report, final String title, final String project, final String phase, final Map mapParameters) {
		Runnable runPreview = new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating " + title + " Report");

				try {
					JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, FncGlobal.connection);

					if(previewSalesReport.getPages().size() > 0){
						if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title, proj_id, phase));
							FncGlobal.homeMDI.addWindow(printAssetSticker);
							printAssetSticker.setMaximum(true);*/

							JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
							printAssetSticker.setTitle(FncGlobal.getReportTitle(title, project, phase));
							printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
							printAssetSticker.setLocationRelativeTo(null);
							printAssetSticker.setVisible(true);
							
							FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
						}
					}else{
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}/* catch (PropertyVetoException e) {
					e.printStackTrace();
				}*/

				FncGlobal.stopProgress();
			}
		};

		FncGlobal.loadThread(title, runPreview);
	}

	public static void generateReport(final String report, final String title, final String project, final String phase, final Map mapParameters, final Connection conn) {
		Runnable runPreview = new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating " + title + " Report");

				try {
					JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, conn);

					if(previewSalesReport.getPages().size() > 0){
						if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title, proj_id, phase));
							FncGlobal.homeMDI.addWindow(printAssetSticker);
							printAssetSticker.setMaximum(true);*/

							JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
							printAssetSticker.setTitle(FncGlobal.getReportTitle(title, project, phase));
							printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
							printAssetSticker.setLocationRelativeTo(null);
							printAssetSticker.setVisible(true);
							
							FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
						}
					}else{
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}/* catch (PropertyVetoException e) {
					e.printStackTrace();
				}*/

				FncGlobal.stopProgress();
			}
		};

		FncGlobal.loadThread(title, runPreview);
	}

	public static void generateReport(final String report, final String title, final Map mapParameters, final DefaultTableModel model) {
		Runnable runPreview = new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating " + title + " Report");

				try {
					JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, new JRTableModelDataSource(model));

					if(previewSalesReport.getPages().size() > 0){
						if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title, proj_id, phase));
							FncGlobal.homeMDI.addWindow(printAssetSticker);
							printAssetSticker.setMaximum(true);*/

							JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
							printAssetSticker.setTitle(FncGlobal.getReportTitle(title));
							printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
							printAssetSticker.setLocationRelativeTo(null);
							printAssetSticker.setVisible(true);
							
							FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
						}
					}else{
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}/* catch (PropertyVetoException e) {
					e.printStackTrace();
				}*/

				FncGlobal.stopProgress();
			}
		};

		FncGlobal.loadThread(title, runPreview);
	}

	public static void generateReport(final String report, final String title, final Map mapParameters, final TableModel model) {
		Runnable runPreview = new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating " + title + " Report");

				try {
					JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, new JRTableModelDataSource(model));

					if(previewSalesReport.getPages().size() > 0){
						if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title, proj_id, phase));
							FncGlobal.homeMDI.addWindow(printAssetSticker);
							printAssetSticker.setMaximum(true);*/

							JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
							printAssetSticker.setTitle(FncGlobal.getReportTitle(title));
							printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
							printAssetSticker.setLocationRelativeTo(null);
							printAssetSticker.setVisible(true);
							
							FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
						}
					}else{
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}/* catch (PropertyVetoException e) {
					e.printStackTrace();
				}*/

				FncGlobal.stopProgress();
			}
		};

		FncGlobal.loadThread(title, runPreview);
	}

	public static void generateReport(final String report, final String title, final String project, final String phase, final Map mapParameters, final DefaultTableModel model) {
		Runnable runPreview = new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating " + title + " Report");

				try {
					JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, new JRTableModelDataSource(model));

					if(previewSalesReport.getPages().size() > 0){
						if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title, proj_id, phase));
							FncGlobal.homeMDI.addWindow(printAssetSticker);
							printAssetSticker.setMaximum(true);*/

							JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
							printAssetSticker.setTitle(FncGlobal.getReportTitle(title, project, phase));
							printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
							printAssetSticker.setLocationRelativeTo(null);
							printAssetSticker.setVisible(true);
							
							FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
						}
					}else{
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}/* catch (PropertyVetoException e) {
					e.printStackTrace();
				}*/

				FncGlobal.stopProgress();
			}
		};

		FncGlobal.loadThread(title, runPreview);
	}

	/**
	 * Added by Alvin Gonzales (2015-02-09)
	 * 
	 * for multiple reports
	 * 
	 */
	public static void generateReport(final Object reports[], final Object titles[], final Object mapParameterss[], String titleThread) {
		Runnable runPreview = new Runnable() {
			public void run() {

				for(int x=0; x < reports.length; x++){
					String report = (String) reports[x];
					String title = (String) titles[x];
					Map mapParameters = (Map) mapParameterss[x];

					FncGlobal.startProgress("Generating " + title + " Report");

					try {
						JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, FncGlobal.connection);
						if(previewSalesReport.getPages().size() > 0){
							if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
								/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title, proj_id, phase));
								FncGlobal.homeMDI.addWindow(printAssetSticker);
								printAssetSticker.setMaximum(true);*/

								JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
								printAssetSticker.setTitle(FncGlobal.getReportTitle(title));
								printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
								printAssetSticker.setLocationRelativeTo(null);
								printAssetSticker.setVisible(true);
								
								FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
							}
						}else{
							JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (HeadlessException e) {
						e.printStackTrace();
					} catch (JRException e) {
						e.printStackTrace();
					}/* catch (PropertyVetoException e) {
						e.printStackTrace();
					}*/
					FncGlobal.stopProgress();

				}
			}
		};

		FncGlobal.loadThread(titleThread, runPreview);
	}

	/**
	 * Added by Alvin Gonzales (2015-07-29) for Docs for Printing only in Documents Monitoring Module
	 * 
	 * to restrict closing of report
	 * 
	 */
	public static void generateReport(final Object[] doc_ids, final Object[] reports, final Object[] titles, final Object[] printables, final Object[] mapParameterss, String titleThread, final Boolean restrict, final String entity_id, final String proj_id, final String pbl_id, final Integer seq_no, final Boolean individual) {//XXX Custom
		Runnable runPreview = new Runnable() {
			public void run() {

				for(int x=0; x < reports.length; x++){
					String doc_id = (String) doc_ids[x];
					String report = (String) reports[x];
					String title = (String) titles[x];
					Boolean printable = (Boolean) printables[x];
					Map mapParameters = (Map) mapParameterss[x];

					FncGlobal.startProgress("Generating " + title + " Report");

					try {
						JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, FncGlobal.connection);
						if(previewSalesReport.getPages().size() > 0){
							if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){

								_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title), doc_id, entity_id, proj_id, pbl_id, seq_no, printable);
								printAssetSticker.setVisible(true);

								/*JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
								printAssetSticker.setTitle(FncGlobal.getReportTitle(title));
								printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
								printAssetSticker.setLocationRelativeTo(null);
								printAssetSticker.setVisible(true);*/
								
								FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
							}
						}else{
							JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (HeadlessException e) {
						e.printStackTrace();
					} catch (JRException e) {
						e.printStackTrace();
					}/* catch (PropertyVetoException e) {
						e.printStackTrace();
					}*/
					FncGlobal.stopProgress();
				}
			}
		};
		FncGlobal.loadThread(titleThread, runPreview);
	}

	/**
	 * Added by Christian Paquibot (2015-04-20)
	 * 
	 * for multiple reports
	 * 
	 */
	public static void generateReport(final String report, final String title, final Map mapParameters,final String sql) {
		Runnable runPreview = new Runnable() {
			public void run() {
				FncGlobal.startProgress("Generating " + title + " Report");
				pgSelect db = new pgSelect();

				try {
					//JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, FncGlobal.connection);


					JRResultSetDataSource sqlresult = new JRResultSetDataSource(db.printQuery(sql));

					JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, sqlresult);
					if(previewSalesReport.getPages().size() > 0){
						if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
							/*_JasperViewer printAssetSticker = new _JasperViewer(previewSalesReport, FncGlobal.getReportTitle(title, proj_id, phase));
							FncGlobal.homeMDI.addWindow(printAssetSticker);
							printAssetSticker.setMaximum(true);*/

							JasperViewer printAssetSticker = new JasperViewer(previewSalesReport, false);
							printAssetSticker.setTitle(FncGlobal.getReportTitle(title));
							printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
							printAssetSticker.setLocationRelativeTo(null);
							printAssetSticker.setVisible(true);
							
							FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
						}
					}else{
						JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (JRException e) {
					e.printStackTrace();
				}/* catch (PropertyVetoException e) {
					e.printStackTrace();
				}*/
				FncGlobal.stopProgress();
			}
		};

		FncGlobal.loadThread(title, runPreview);
	}

	public static void generatePDF(final String report, final String title, final Map mapParameters, final String strDir) {
		try {
			JasperPrint previewSalesReport = JasperFillManager.fillReport(FncReport.class.getResourceAsStream(report), mapParameters, FncGlobal.connection);
			if(previewSalesReport.getPages().size() > 0){
				if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
					try {
						/*
						@SuppressWarnings("deprecation")
						JRExporter exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT, previewSalesReport);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(strDir));
						exporter.exportReport();
						 */

						JRPdfExporter exporter = new JRPdfExporter();
						exporter.setExporterInput(new SimpleExporterInput(previewSalesReport));
						exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(strDir));

						SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
						configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting);
						exporter.setConfiguration(configuration);
						exporter.exportReport();
						
						FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Generate PDF");
					} catch (Exception e) {
						throw new RuntimeException("It's not possible to generate the pdf report.", e);
					} 
				}
			}else{
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", title, JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	//START OF NEW REPORT VIEWER

	public static void reportViewer(final String report, final String title, final String company, final Map mapParameters) {
		System.out.println( "generating jasper report..." );

		// 1. compile template ".jrxml" file

		try {
			File template = ResourceUtils.getFile("/home/jfatallo/eclipse-workspace/JSystem2.2/src/Reports/sample.jrxml");
			
			//JasperReport jasperReport = JasperCompileManager.compileReport(template.getAbsolutePath());
			
			// 2. parameters "empty"

			// 3. datasource "java object"

			//JasperDesign jasperDesign = JRXmlLoader.load(new File("/home/jfatallo/eclipse-workspace/JSystem2.2/src/Reports/sample.jrxml"));
			InputStream is = FncReport.class.getResourceAsStream(report);

			JasperDesign jasperDesign = JRXmlLoader.load(is);
			//JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			//jasperPrint = JasperFillManager.fillReport(jasperReport, mapParameters, FncGlobal.connection);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapParameters, FncGlobal.connection);
			
			if(jasperPrint.getPages().size() > 0){
				if(FncGlobal.homeMDI.isNotExisting("iJasperViewer")){
					

					JasperViewer printAssetSticker = new JasperViewer(jasperPrint, false);
					printAssetSticker.setTitle(FncGlobal.getReportTitle("test", "test"));
					printAssetSticker.setExtendedState(JFrame.MAXIMIZED_BOTH);
					printAssetSticker.setLocationRelativeTo(null);
					printAssetSticker.setVisible(true);
					FncGlobal.AuditLogs(UserInfo.EmployeeCode, report, "Preview Report");
			}
			}else{
				JOptionPane.showMessageDialog(FncGlobal.homeMDI, "No Reports Generated!", "test", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (JRException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	

}
