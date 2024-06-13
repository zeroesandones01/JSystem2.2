package Functions;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;

import Buyers.CreditandCollections._RealTimeDebit;
import Database.pgSelect;

public class FncExport {

	public static void createDBF_FromModel(String strCol[], String strHiddenCols[], DefaultTableModel model,
			String file_name, String font_name) {
		String strFileNo = "";
		String strFileName = "";
		String strDate = "";

		Integer selected_rows = 0;

		for (int z = 0; z < model.getRowCount(); z++) {
			Boolean isSelected = (Boolean) model.getValueAt(z, 0);
			if (isSelected) {
				selected_rows = selected_rows + 1;
			}
		}

		// Integer row = model.getRowCount() - selected_rows;
		Integer row = selected_rows;
		// Integer col = model.getColumnCount();
		Integer col = model.getColumnCount() - strHiddenCols.length;

		/*
		 * System.out.printf("Display value of selected rows: %s%n", row);
		 * System.out.printf("Display VALUE of Model Column Count: %s%n",
		 * model.getColumnCount());
		 * System.out.printf("Display Value of Model Row Count: %s%n",
		 * model.getRowCount());
		 * System.out.printf("Display Value of Column Count: %s%n", col);
		 * System.out.printf("Display Length of Hidden Columns: %s%n",
		 * strHiddenCols.length);
		 */

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		sheet.setColumnWidth(0, 4400); // 1.33
		sheet.setColumnWidth(2, 8000);
		System.out.printf("Display Value of Column Width: %s%n", sheet.getColumnWidth(0));

		HSSFCellStyle cell_style = (HSSFCellStyle) wb.createCellStyle();
		HSSFFont font_style = (HSSFFont) wb.createFont();
		font_style.setFontName(font_name);
		cell_style.setFont(font_style);
		// ColumnStyle columnStyle1 = new ColumnStyle("CO1");

		// cell_style.setFont(font_name);

		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		strDate = (String) sdfTo.format(dateObj);

		/** Set Directory **/
		String strDir = _RealTimeDebit.OpenDir("Folder");

		/** Check if file of the same name exists within the directory **/
		Integer intSeq = 1;
		strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
		File f = new File(strDir + "/" + file_name + strDate + "(" + strFileNo + ")" + ".DBF");

		System.out.println("");
		System.out.println("File Name: " + file_name + strDate + "(" + strFileNo + ")" + ".DBF");

		while (f.exists() && f.isFile()) {
			System.out.println("");
			System.out.println("File exists! Number: " + strFileNo);

			intSeq++;
			strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
			f = new File(strDir + "/" + file_name + strDate + "(" + strFileNo + ")" + ".DBF");
			System.out.println("New Name: " + file_name + strDate + "(" + strFileNo + ")" + ".DBF");
		}

		strFileName = file_name + strDate + "(" + strFileNo + ")" + ".DBF";

		/** Begin Creating Excel Content **/
		// System.out.println("");
		Row rowRow = sheet.createRow((short) 0);
		// System.out.printf("Display Array Count: %s%n", strHiddenCols.length);

		for (int x = 0; x < col; x++) {
			for (int y = 0; y < strHiddenCols.length; y++) {
				if ((strHiddenCols[y]).toString().trim().equals(model.getColumnName(x).toString()
						.trim()) == false /* && model.getColumnName(x).toString().trim().equals("Select") == false */) {
					if (model.getColumnName(x).toString().trim().equals("Select")) {
						System.out.println("Dumaan dito sa Added select");
					}
					System.out.printf("Display Setvalue Data: %s%n", strCol[x]);
					System.out.printf("Display value of x: %s%n", x);
					System.out.println("");
					// rowRow.createCell(x).setCellValue(strCol[x]);
					Cell cell = (Cell) rowRow.createCell(x);
					cell.setCellValue(strCol[x]);
					cell.setCellStyle(cell_style);
				}
			}
		}

		System.out.printf("Display Model Row Count: %s%n", model.getRowCount());

		if (model.getRowCount() != 0) {
			System.out.println("");
			System.out.println("Total number of rows: " + row);

			String strValue = "";
			Integer row_count = 0;

			for (Integer intRow = 0; intRow < model.getRowCount(); intRow++) {

				Boolean isSelected = (Boolean) model.getValueAt(intRow, 0);
				System.out.println("");

				if (isSelected) {
					// if(intRow != 0){\
					row_count = row_count + 1;

					Integer shortInt = intRow;

					// Short shortRow = Short.parseShort(shortInt.toString());
					Short shortRow = Short.parseShort(row_count.toString());
					System.out.printf("Display Value of int Row: %s%n", intRow);
					System.out.printf("Display value of Short Row: %s%n", shortRow);

					Row item_row = sheet.createRow((short) shortRow);
					/*
					 * System.out.println(""); System.out.println("");
					 */

					for (Integer x = 0; x < col + 1; x++) {
						// for(int y = 0; y <strHiddenCols.length; y ++){

						if (x != 0) {
							/*
							 * System.out.printf("Display Column Name: %s%n",
							 * model.getColumnName(x).toString().trim());
							 * System.out.printf("Display Data set value: %s%n", model.getValueAt(intRow,
							 * x)); System.out.printf("Display value of x: %s%n", x);
							 * System.out.println(""); System.out.println("");
							 */

							try {
								item_row.createCell(x - 1).setCellValue((String) model.getValueAt(intRow, x));
							} catch (ClassCastException ex) {
								try {
									BigDecimal bdValue = (BigDecimal) model.getValueAt(intRow, x);
									strValue = bdValue.toString();
									item_row.createCell(x - 1).setCellValue(strValue);
								} catch (ClassCastException ey) {
									try {
										Integer intValue = (Integer) model.getValueAt(intRow, x);
										strValue = intValue.toString();
										item_row.createCell(x - 1).setCellValue(strValue);
									} catch (ClassCastException ez) {
										try {
											Timestamp tmeValue = (Timestamp) model.getValueAt(intRow, x);
											strValue = tmeValue.toString();
											item_row.createCell(x - 1).setCellValue(strValue);
										} catch (ClassCastException ea) {
											try {
												Date dteValue = (Date) model.getValueAt(intRow, x);
												strValue = dteValue.toString();
												item_row.createCell(x - 1).setCellValue(strValue);
											} catch (ClassCastException eb) {

											}
										}
									}
								}
							}
						}
						// if(model.getColumnName(x).toString().trim().equals("Select") == false){

						// }
						// }
					}
				}
			}

			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(strDir + "/" + strFileName);
			} catch (FileNotFoundException e2) {
				System.out.println("");
				System.out.println("Error Line -- " + strFileName);
			}

			try {
				wb.write(fileOut);
			} catch (IOException e1) {
				System.out.println("Error Line -- wb.write(fileOut)");
			}

			try {
				fileOut.close();
			} catch (IOException e1) {
				System.out.println("Error Line -- fileOut.close();");
			}

			JOptionPane.showMessageDialog(null, "Export to file successful!");

			Desktop dt = Desktop.getDesktop();
			f = new File(strDir + "/" + strFileName);

			try {
				dt.open(f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Spreadsheet creation halted.");
		}
	}

	public static void createDBF_FromModelWithQuery(String columns[], Integer string_columns[], Integer text_columns[],
			Integer int_columns[], Integer bool_columns[], Integer date_columns[], DefaultTableModel model,
			String file_name, String font_name) {
		String strFileNo = "";
		String strFileName = "";
		String strDate = "";

		Integer selected_rows = 0;

		for (int z = 0; z < model.getRowCount(); z++) {
			Boolean isSelected = (Boolean) model.getValueAt(z, 0);
			if (isSelected) {
				selected_rows = selected_rows + 1;
			}
		}

		// Integer row = model.getRowCount() - selected_rows;
		Integer row = selected_rows;
		// Integer col = model.getColumnCount();
		Integer col = columns.length;

		Workbook wb = new HSSFWorkbook();

		Sheet sheet = wb.createSheet("Sheet1");
		HSSFDataFormat df = (HSSFDataFormat) wb.createDataFormat();

		sheet.setColumnWidth(0, 4400); // 1.33
		sheet.setColumnWidth(2, 8000);

		HSSFCellStyle cell_style = (HSSFCellStyle) wb.createCellStyle();
		HSSFFont font_style = (HSSFFont) wb.createFont();
		font_style.setFontName(font_name);
		cell_style.setFont(font_style);

		/** Set Directory **/
		String strDir = _RealTimeDebit.OpenDir("Folder");

		/** Check if file of the same name exists within the directory **/
		Integer intSeq = 1;
		strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
		File f = new File(strDir + "/" + file_name + "(" + strFileNo + ")" + ".dbf");

		System.out.println("");
		System.out.println("File Name: " + file_name + "(" + strFileNo + ")" + ".dbf");

		while (f.exists() && f.isFile()) {
			System.out.println("");
			System.out.println("File exists! Number: " + strFileNo);

			intSeq++;
			strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
			f = new File(strDir + "/" + file_name + "(" + strFileNo + ")" + ".dbf");
			System.out.println("New Name: " + file_name + "(" + strFileNo + ")" + ".dbf");
		}

		strFileName = file_name + "(" + strFileNo + ")" + ".DBF";

		/** Begin Creating Excel Content **/
		// System.out.println("");
		Row rowRow = sheet.createRow((short) 0);
		// System.out.printf("Display Array Count: %s%n", strHiddenCols.length);

		for (int x = 0; x < col; x++) {
			// rowRow.createCell(x).setCellValue(strCol[x]);
			Cell cell = (Cell) rowRow.createCell(x);
			cell.setCellValue(columns[x]);
			cell.setCellStyle(cell_style);
		}

		System.out.printf("Display Model Row Count: %s%n", model.getRowCount());

		if (model.getRowCount() != 0) {
			System.out.println("");
			System.out.println("Total number of rows: " + row);

			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
			ArrayList<Integer> listSeq = new ArrayList<Integer>();

			for (Integer intRow = 0; intRow < model.getRowCount(); intRow++) {

				Boolean isSelected = (Boolean) model.getValueAt(intRow, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(intRow, 7);
					String proj_id = (String) model.getValueAt(intRow, 8);
					String pbl_id = (String) model.getValueAt(intRow, 9);
					Integer seq_no = (Integer) model.getValueAt(intRow, 10);

					listEntityID.add(String.format("'%s'", entity_id));
					listProjID.add(String.format("'%S'", proj_id));
					listPBL.add(String.format("'%s'", pbl_id));
					listSeq.add(seq_no);

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
			String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
			String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_apmaster_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[], ARRAY[" + proj_id
					+ "]::VARCHAR[], ARRAY[" + pbl_id + "]::VARCHAR[], ARRAY[" + seq_no + "]::INTEGER[])";
			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			if (db.isNotNull()) {

				String strValue = "";
				Integer row_count = 0;

				for (Integer a = 0; a < db.getRowCount(); a++) {
					Integer shortInt = a + 1;
					Short shortRow = Short.parseShort(shortInt.toString());
					Row item_row = sheet.createRow((short) shortRow);

					for (Integer x = 0; x <= col - 1; x++) {
						Object result = db.getResult()[a][x];
						cell_style = (HSSFCellStyle) wb.createCellStyle();

						System.out.printf("Display Value of result: (%s)%n", db.getResult()[a][x]);

						try {
							// if(db.getResult()[a][x] instanceof String){
							Cell cell = (Cell) item_row.createCell(x);
							cell.setCellValue(((String) db.getResult()[a][x]));
							cell.setCellStyle(cell_style);
							if (Arrays.asList(text_columns).contains(x)) {
								cell_style.setDataFormat(df.getFormat("@"));
							}
							System.out.println("Instance of String");
							System.out.printf("Display Value of x:(%s)%n", x);
							System.out.println("");
							// }

						} catch (ClassCastException ex) {

							if (result instanceof Integer) {
								if (Arrays.asList(int_columns).contains(x)) {
									Integer intValue = (Integer) db.getResult()[a][x];
									strValue = intValue.toString();
									Cell cell = (Cell) item_row.createCell(x);
									cell.setCellValue(intValue);
									cell_style.setDataFormat(df.getFormat("0"));
									cell.setCellStyle(cell_style);
									System.out.println("Instance of Integer");
									System.out.println("");
									System.out.println("");
								}
							}

							if (result instanceof Date) {
								System.out.printf("Display value of x: (%s)%n", x);
								if (Arrays.asList(date_columns).contains(x)) {
									Date dteValue = (Date) db.getResult()[a][x];
									strValue = dteValue.toString();
									Cell cell = (Cell) item_row.createCell(x);
									cell.setCellValue(dteValue);
									CreationHelper createHelper = wb.getCreationHelper();
									cell_style.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yy"));
									cell.setCellStyle(cell_style);
									System.out.printf("Display Value of Data Format: (%s)%n",
											cell_style.getDataFormat());
									System.out.println("Instance of Date");
									System.out.println("");
									System.out.println("");
								}
							}

							if (result instanceof BigDecimal) {
								// if(x == 13){
								BigDecimal bdValue = (BigDecimal) db.getResult()[a][x];
								strValue = bdValue.toString();
								Cell cell = (Cell) item_row.createCell(x);
								cell.setCellValue(Double.valueOf(strValue));
								cell_style.setDataFormat(df.getFormat("0.00"));
								cell.setCellStyle(cell_style);
								System.out.println("Instance of BigDecimal");
								System.out.println("");
								System.out.println("");
								// }
							}

							if (result instanceof Boolean) {
								if (Arrays.asList(bool_columns).contains(x)) {
									Boolean boolValue = (Boolean) db.getResult()[a][x];
									// Boolean boolValue = (Boolean) db.getResult()[a][x];
									// strValue = boolValue.toString();
									Cell cell = (Cell) item_row.createCell(x);
									/*
									 * CreationHelper createHelper = wb.getCreationHelper();
									 * cell_style.setDataFormat(createHelper.createDataFormat().getFormat("TRUE"));
									 */
									// cell.setCellValue(boolValue);
									cell.setCellValue(Boolean.valueOf("TRUE"));
									// cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
									System.out.printf("Display value of x:(%s)%n", x);
									System.out.println("Instance of Boolean");
									System.out.printf("Display Cell Type: (%s)%n", cell.getCellType());
									System.out.printf("Display cell Value: (%s)%n", cell.getBooleanCellValue());
									System.out.println("");
									System.out.println("");

								}
							}
						}
					}
				}
			}

			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(strDir + "/" + strFileName);
			} catch (FileNotFoundException e2) {
				System.out.println("");
				System.out.println("Error Line -- " + strFileName);
			}

			try {
				wb.write(fileOut);
			} catch (IOException e1) {
				System.out.println("Error Line -- wb.write(fileOut)");
			}

			try {
				fileOut.close();
			} catch (IOException e1) {
				System.out.println("Error Line -- fileOut.close();");
			}

			JOptionPane.showMessageDialog(null, "Export to file successful!");

			Desktop dt = Desktop.getDesktop();
			f = new File(strDir + "/" + strFileName);

			try {
				dt.open(f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Spreadsheet creation halted.");
		}
	}

	@SuppressWarnings("deprecation")
	public static void createDBF_FromModelWithQuery(String columns[], Integer char_columns[], Integer bool_columns[],
			Integer date_columns[], Integer numeric_columns[], DefaultTableModel model, String file_name,
			String directory) throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
			ArrayList<Integer> listSeq = new ArrayList<Integer>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 7);
					String proj_id = (String) model.getValueAt(x, 8);
					String pbl_id = (String) model.getValueAt(x, 9);
					Integer seq_no = (Integer) model.getValueAt(x, 10);

					listEntityID.add(String.format("'%s'", entity_id));
					listProjID.add(String.format("'%S'", proj_id));
					listPBL.add(String.format("'%s'", pbl_id));
					listSeq.add(seq_no);

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
			String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
			String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_apmaster_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[], ARRAY[" + proj_id
					+ "]::VARCHAR[], ARRAY[" + pbl_id + "]::VARCHAR[], ARRAY[" + seq_no + "]::INTEGER[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 2 || w == 38 || w == 39) {
						fields[w].setFieldLength(15);
					}
					if (w == 4 || w == 5 || w == 22 || w == 23 || w == 24 || w == 25 || w == 26 || w == 28 || w == 32
							|| w == 37 || w == 40 || w == 5) {
						fields[w].setFieldLength(20);
					}
					if (w == 6) {
						fields[w].setFieldLength(9);
					}
					if (w == 7 || w == 8 || w == 9) {
						fields[w].setFieldLength(25);
					}
					if (w == 10 || w == 11) {
						fields[w].setFieldLength(10);
					}
					if (w == 12 || w == 30 || w == 31 || w == 34) {
						fields[w].setFieldLength(1);
					}
					if (w == 15 || w == 41 || w == 42) {
						fields[w].setFieldLength(40);
					}
				}
				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}
				if (Arrays.asList(bool_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_L);
					fields[w].setFieldLength(1);
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 0) {
						fields[w].setFieldLength(7);
						fields[w].setDecimalCount(0);
					}
					if (w == 1) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
					if (w == 13) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(2);
					}
					if (w == 14) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
					if (w == 17) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(7);
					}
					if (w == 18) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
					if (w == 19) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
					if (w == 20) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(7);
					}
					if (w == 21) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(7);
					}
					if (w == 27) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
					if (w == 35) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(2);
					}
					if (w == 36) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
					if (w == 43) {
						fields[w].setFieldLength(7);
						fields[w].setDecimalCount(0);
					}
					if (w == 45) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}

						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}

						if (Arrays.asList(bool_columns).contains(z)) {
							Boolean boolValue = (Boolean) db.getResult()[y][z];

							/*
							 * if(z == 44){ rowData[z] = ""; }else{
							 */
							rowData[z] = boolValue;
							// }
						}
					}
					writer.addRecord(rowData);
				}
			}

			String strDir = _RealTimeDebit.OpenDir("Folder");

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createADDRESSB_DBF_FromModelWithQuery(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String directory)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 7);
					String proj_id = (String) model.getValueAt(x, 8);

					listEntityID.add(String.format("'%s'", entity_id));
					listProjID.add(String.format("'%S'", proj_id));

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_addressb_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[], ARRAY[" + proj_id
					+ "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 7 || w == 8 || w == 10 || w == 11) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(25);
					}
					if (w == 6 || w == 12) {
						fields[w].setFieldLength(20);
					}
					if (w == 9) {
						fields[w].setFieldLength(35);
					}
					if (w == 13) {
						fields[w].setFieldLength(7);
					}

				}
				/*
				 * if(Arrays.asList(date_columns).contains(w)){
				 * fields[w].setDataType(DBFField.FIELD_TYPE_D); }
				 * if(Arrays.asList(bool_columns).contains(w)){
				 * fields[w].setDataType(DBFField.FIELD_TYPE_L); fields[w].setFieldLength(1); }
				 */
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}

						/*
						 * if(Arrays.asList(date_columns).contains(z)){ Date dteValue = (Date)
						 * db.getResult()[y][z]; rowData[z] = dteValue; }
						 * 
						 * if(Arrays.asList(bool_columns).contains(z)){ Boolean boolValue = (Boolean)
						 * db.getResult()[y][z];
						 * 
						 * if(z == 44){ rowData[z] = ""; }else{ rowData[z] = boolValue; } }
						 */
					}
					writer.addRecord(rowData);
				}
			}

			String strDir = _RealTimeDebit.OpenDir("Folder");

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createADDRESSE_DBF_FromModelWithQuery(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String directory)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 7);
					String proj_id = (String) model.getValueAt(x, 8);

					listEntityID.add(String.format("'%s'", entity_id));

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_addresse_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 7 || w == 8 || w == 10 || w == 11) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(25);
					}
					if (w == 6 || w == 12) {
						fields[w].setFieldLength(20);
					}
					if (w == 9) {
						fields[w].setFieldLength(35);
					}
					if (w == 13) {
						fields[w].setFieldLength(7);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
					}
					writer.addRecord(rowData);
				}
			}

			String strDir = _RealTimeDebit.OpenDir("Folder");

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createCHAR_REF_DBF_FromModelWithQuery(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String directory)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 7);

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_char_ref_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0) {
						fields[w].setFieldLength(15);
					}
					if (w == 3) {
						fields[w].setFieldLength(35);
					}
					if (w == 4) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(20);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1 || w == 2) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
					}
					writer.addRecord(rowData);
				}
			}

			String strDir = _RealTimeDebit.OpenDir("Folder");

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createDOADATA_DBF_FromModelWithQuery(String columns[], Integer char_columns[],
			Integer date_columns[], DefaultTableModel model, String file_name, String directory)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listPblID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 7);

					listEntityID.add(String.format("'%s'", entity_id));

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String pbl_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_doadata_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0) {
						fields[w].setFieldLength(15);
					}
					if (w == 2 || w == 3 | w == 4 || w == 5) {
						fields[w].setFieldLength(5);
					}
					if (w == 6) {
						fields[w].setFieldLength(50);
					}
				}
				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}

			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}
					}
					writer.addRecord(rowData);
				}
			}

			String strDir = _RealTimeDebit.OpenDir("Folder");

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createINSURE_DBF_FromModelWithQuery(String columns[], Integer char_columns[],
			Integer date_columns[], Integer numeric_columns[], DefaultTableModel model, String file_name,
			String directory) throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 7);

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_insure_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(4);
					}
				}
				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(9);
						fields[w].setDecimalCount(0);
					}
				}

			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}
					}
					writer.addRecord(rowData);
				}
			}

			String strDir = _RealTimeDebit.OpenDir("Folder");

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createADDRESSA_DBF_FromModelWithQuery(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String directory)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 7);

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_addressa_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3 || w == 10) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 5 || w == 7 || w == 8) {
						fields[w].setFieldLength(50);
					}
					if (w == 6) {
						fields[w].setFieldLength(35);
					}
					if (w == 9) {
						fields[w].setFieldLength(20);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}
					}
					writer.addRecord(rowData);
				}
			}

			String strDir = _RealTimeDebit.OpenDir("Folder");

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createADDRESSB_DBF_FromModelWithQuery_FirstFiling_temp(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 1);
				System.out.println("");

				if (isSelected) {
					/*
					 * String entity_id =
					 * FncGlobal.GetString("select entity_id from rf_entity where entity_name ~* '"+
					 * (String) model.getValueAt(x, 0)+"'"); String proj_id =
					 * FncGlobal.GetString("select proj_id from mf_project where proj_name = '"+(
					 * String) model.getValueAt(x, 2)+"'");
					 */

					String entity_id = (String) model.getValueAt(x, 19);
					String proj_id = (String) model.getValueAt(x, 20);

					listEntityID.add(String.format("'%s'", entity_id));
					listProjID.add(String.format("'%S'", proj_id));

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_addressb_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[], ARRAY[" + proj_id
					+ "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				fields[w].setFieldName(columns[w]);

				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 7 || w == 8 || w == 10 || w == 11) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(25);
					}
					if (w == 6 || w == 12) {
						fields[w].setFieldLength(20);
					}
					if (w == 9) {
						fields[w].setFieldLength(35);
					}
					if (w == 13) {
						fields[w].setFieldLength(7);
					}

				}

				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							try {
								rowData[z] = strValue.trim();
							} catch (Exception e) {
								rowData[z] = "";
							}
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			e.printStackTrace();
		}
	}

	public static void createADDRESSE_DBF_FromModelWithQuery_FirstFiling(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 1);
				System.out.println("");

				if (isSelected) {

					String entity_id = FncGlobal.GetString("select entity_id from rf_entity where entity_name ~* '"
							+ (String) model.getValueAt(x, 0) + "'");
					String proj_id = FncGlobal.GetString("select proj_id from mf_project where proj_name = '"
							+ (String) model.getValueAt(x, 2) + "'");

					listEntityID.add(String.format("'%s'", entity_id));

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_addresse_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 7 || w == 8 || w == 10 || w == 11) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(25);
					}
					if (w == 6 || w == 12) {
						fields[w].setFieldLength(20);
					}
					if (w == 9) {
						fields[w].setFieldLength(35);
					}
					if (w == 13) {
						fields[w].setFieldLength(7);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			e.printStackTrace();
		}
	}

	public static void createCHAR_REF_DBF_FromModelWithQuery_FirstFiling(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 1);
				System.out.println("");

				if (isSelected) {

					String entity_id = FncGlobal.GetString("select entity_id from rf_entity where entity_name ~* '"
							+ (String) model.getValueAt(x, 0) + "'");

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_char_ref_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0) {
						fields[w].setFieldLength(15);
					}
					if (w == 3) {
						fields[w].setFieldLength(35);
					}
					if (w == 4) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(20);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1 || w == 2) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createDOADATA_DBF_FromModelWithQuery_FirstFiling(String columns[], Integer char_columns[],
			Integer date_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listPblID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 1);
				System.out.println("");

				if (isSelected) {

					String entity_id = FncGlobal.GetString("select entity_id from rf_entity where entity_name ~* '"
							+ (String) model.getValueAt(x, 0) + "'");
					String pbl_id = (String) model.getValueAt(x, 20);

					listEntityID.add(String.format("'%s'", entity_id));
					listPblID.add(String.format("'%s'", pbl_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String pbl_id = listPblID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_doadata_dbf_export2(ARRAY[" + entity_id + "]::VARCHAR[],ARRAY[" + pbl_id
					+ "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0) {
						fields[w].setFieldLength(15);
					}
					if (w == 2 || w == 3 | w == 4 || w == 5) {
						fields[w].setFieldLength(5);
					}
					if (w == 6) {
						fields[w].setFieldLength(50);
					}
				}
				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}

			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createINSURE_DBF_FromModelWithQuery_FirstFiling(String columns[], Integer char_columns[],
			Integer date_columns[], Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 1);
				System.out.println("");

				if (isSelected) {

					String entity_id = FncGlobal.GetString("select entity_id from rf_entity where entity_name ~* '"
							+ (String) model.getValueAt(x, 0) + "'");

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_insure_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(4);
					}
				}
				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(9);
						fields[w].setDecimalCount(0);
					}
				}

			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createADDRESSA_DBF_FromModelWithQuery_FirstFiling(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {
		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 1);
				System.out.println("");

				if (isSelected) {

					String entity_id = FncGlobal.GetString("select entity_id from rf_entity where entity_name ~* '"
							+ (String) model.getValueAt(x, 0) + "'");

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_addressa_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3 || w == 10) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 5 || w == 7 || w == 8) {
						fields[w].setFieldLength(50);
					}
					if (w == 6) {
						fields[w].setFieldLength(35);
					}
					if (w == 9) {
						fields[w].setFieldLength(20);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createDBF_FromModelWithQuery_MSVS(String columns[], Integer char_columns[],
			Integer bool_columns[], Integer date_columns[], Integer numeric_columns[], DefaultTableModel model,
			String file_name, String strDir) throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
			ArrayList<Integer> listSeq = new ArrayList<Integer>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 18).toString();
					String proj_id = (String) model.getValueAt(x, 19).toString();
					String pbl_id = (String) model.getValueAt(x, 20).toString();
					Integer seq_no = (Integer) model.getValueAt(x, 21);

					listEntityID.add(String.format("'%s'", entity_id));
					listProjID.add(String.format("'%S'", proj_id));
					listPBL.add(String.format("'%s'", pbl_id));
					listSeq.add(seq_no);

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
			String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
			String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_dbf_apmaster_msvs(ARRAY[" + entity_id + "]::VARCHAR[], ARRAY[" + proj_id
					+ "]::VARCHAR[], ARRAY[" + pbl_id + "]::VARCHAR[], ARRAY[" + seq_no + "]::INTEGER[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 2 || w == 38 || w == 39) {
						fields[w].setFieldLength(15);
					}
					if (w == 4 || w == 5 || w == 22 || w == 23 || w == 24 || w == 25 || w == 26 || w == 28 || w == 32
							|| w == 37 || w == 40 || w == 5) {
						fields[w].setFieldLength(20);
					}
					if (w == 6) {
						fields[w].setFieldLength(9);
					}
					if (w == 7 || w == 8 || w == 9) {
						fields[w].setFieldLength(25);
					}
					if (w == 10 || w == 11) {
						fields[w].setFieldLength(10);
					}
					if (w == 12 || w == 30 || w == 31 || w == 34) {
						fields[w].setFieldLength(1);
					}
					if (w == 15 || w == 41 || w == 42) {
						fields[w].setFieldLength(40);
					}
				}
				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}
				if (Arrays.asList(bool_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_L);
					fields[w].setFieldLength(1);
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 0) {
						fields[w].setFieldLength(7);
						fields[w].setDecimalCount(0);
					}
					if (w == 1) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
					if (w == 13) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(2);
					}
					if (w == 14) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
					if (w == 17) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(7);
					}
					if (w == 18) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
					if (w == 19) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
					if (w == 20) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(7);
					}
					if (w == 21) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(7);
					}
					if (w == 27) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
					if (w == 35) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(2);
					}
					if (w == 36) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
					if (w == 43) {
						fields[w].setFieldLength(7);
						fields[w].setDecimalCount(0);
					}
					if (w == 45) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}

						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}

						if (Arrays.asList(bool_columns).contains(z)) {
							Boolean boolValue = (Boolean) db.getResult()[y][z];

							/*
							 * if(z == 44){ rowData[z] = ""; }else{
							 */
							rowData[z] = boolValue;
							// }
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createADDRESSB_DBF_FromModelWithQuery_MSVS(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {
					String entity_id = (String) model.getValueAt(x, 18).toString();
					String proj_id = (String) model.getValueAt(x, 19).toString();

					listEntityID.add(String.format("'%s'", entity_id));
					listProjID.add(String.format("'%S'", proj_id));

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_dbf_addressb_msvs(ARRAY[" + entity_id + "]::VARCHAR[], ARRAY[" + proj_id
					+ "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				fields[w].setFieldName(columns[w]);

				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 7 || w == 8 || w == 10 || w == 11) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(25);
					}
					if (w == 6 || w == 12) {
						fields[w].setFieldLength(20);
					}
					if (w == 9) {
						fields[w].setFieldLength(35);
					}
					if (w == 13) {
						fields[w].setFieldLength(7);
					}

				}

				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			e.printStackTrace();
		}
	}

	public static void createADDRESSE_DBF_FromModelWithQuery_MSVS(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {
					String entity_id = (String) model.getValueAt(x, 18).toString();
					String proj_id = (String) model.getValueAt(x, 19).toString();

					listEntityID.add(String.format("'%s'", entity_id));

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_dbf_addresse_msvs(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 7 || w == 8 || w == 10 || w == 11) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(25);
					}
					if (w == 6 || w == 12) {
						fields[w].setFieldLength(20);
					}
					if (w == 9) {
						fields[w].setFieldLength(35);
					}
					if (w == 13) {
						fields[w].setFieldLength(7);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			e.printStackTrace();
		}
	}

	public static void createCHAR_REF_DBF_FromModelWithQuery_MSVS(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {
					String entity_id = (String) model.getValueAt(x, 18).toString();
					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_dbf_charref_msvs(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0) {
						fields[w].setFieldLength(15);
					}
					if (w == 3) {
						fields[w].setFieldLength(35);
					}
					if (w == 4) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(20);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1 || w == 2) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createDOADATA_DBF_FromModelWithQuery_MSVS(String columns[], Integer char_columns[],
			Integer date_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {
					String entity_id = (String) model.getValueAt(x, 18).toString();

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_dbf_doadata_msvs(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0) {
						fields[w].setFieldLength(15);
					}
					if (w == 2 || w == 3 | w == 4 || w == 5) {
						fields[w].setFieldLength(5);
					}
					if (w == 6) {
						fields[w].setFieldLength(50);
					}
				}
				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}

			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createINSURE_DBF_FromModelWithQuery_MSVS(String columns[], Integer char_columns[],
			Integer date_columns[], Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {
					String entity_id = (String) model.getValueAt(x, 18).toString();

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_dbf_insure_msvs(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(4);
					}
				}
				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(9);
						fields[w].setDecimalCount(0);
					}
				}

			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createADDRESSA_DBF_FromModelWithQuery_MSVS(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {
		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {
					String entity_id = (String) model.getValueAt(x, 18).toString();

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_dbf_addressa_msvs(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3 || w == 10) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 5 || w == 7 || w == 8) {
						fields[w].setFieldLength(50);
					}
					if (w == 6) {
						fields[w].setFieldLength(35);
					}
					if (w == 9) {
						fields[w].setFieldLength(20);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void CreateXLSwithGroup(String strCol[], String strSQL, String strFile, Integer intHead,
			String strHead[], Integer intGroupColIndex) {
		String strFileNo = "";
		String strFileName = "";
		String strDate = "";
		Cell cell = null;

		System.out.println("");
		System.out.println("Creating Spreadsheet...");

		pgSelect db = new pgSelect();
		db.select(strSQL);

		Integer row = db.getRowCount();
		Integer col = db.getColumnCount();
		Integer hd = intHead;

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		strDate = (String) sdfTo.format(dateObj);

		/*** Cell Styles ***/
		HSSFFont font = (HSSFFont) wb.createFont();
		font.setBoldweight(font.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = (HSSFCellStyle) wb.createCellStyle();
		style1.setFont(font);

		HSSFCellStyle style2 = (HSSFCellStyle) wb.createCellStyle();
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setFont(font);

		HSSFCreationHelper createHelper = (HSSFCreationHelper) wb.getCreationHelper();
		HSSFCellStyle cs_date = (HSSFCellStyle) wb.createCellStyle();
		cs_date.setDataFormat(createHelper.createDataFormat().getFormat("MM/DD/YYYY"));

		HSSFCellStyle cs_double = (HSSFCellStyle) wb.createCellStyle();
		cs_double.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		/*** Cell Styles ***/

		/** Set Directory **/
		String strDir = _RealTimeDebit.OpenDir("Folder");

		/** Check if file of the same name exists within the directory **/
		Integer intSeq = 1;
		strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
		File f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");

		while (f.exists() && f.isFile()) {
			System.out.println("");
			System.out.println("File exists! Number: " + strFileNo);

			intSeq++;
			strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
			f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");
		}

		strFileName = strFile + strDate + "(" + strFileNo + ")" + ".xls";

		/** Begin Creating Page Header **/
		System.out.println("");
		for (int x = 0; x < hd; x++) {
			Row rowHead = sheet.createRow((short) x);
			rowHead.createCell(0).setCellValue(strHead[x]);
			rowHead.getCell(0).setCellStyle(style1);
			System.out.println(strHead[x]);
		}

		/** Begin Creating Column Header **/
		System.out.println("");
		Row rowRow = sheet.createRow((short) (hd + 1));
		rowRow.setHeightInPoints(3 * sheet.getDefaultRowHeightInPoints());
		for (int x = 0; x < col; x++) {
			rowRow.createCell(x).setCellValue(strCol[x]);
			rowRow.getCell(x).setCellStyle(style2);
			System.out.println(strCol[x]);
		}

		String strAlpha = "";
		String strBravo = "";

		/** Begin Creating Excel Content **/
		if (db.isNotNull()) {
			System.out.println("");
			System.out.println("Total number of rows: " + row);

			String strValue = "";
			Integer shortInt = 0;
			Boolean blnPass = false;
			Integer intCell = 0;

			for (Integer intRow = 0; intRow < row; intRow++) {
				if (blnPass && intRow == 1) {
					intRow = 0;
				}

				shortInt = intCell + hd + 3;

				System.out.println("");
				System.out.println("shortInt.toString(): " + shortInt.toString());

				Short shortRow = Short.parseShort(shortInt.toString());
				Row item_row = sheet.createRow((short) shortRow);

				System.out.println("");
				System.out.println("strAlpha: " + db.getResult()[intRow][intGroupColIndex]);
				System.out.println("Value: " + (String) db.getResult()[intRow][1]);
				System.out.println("shortInt: " + shortInt);
				System.out.println("intRow: " + intRow);
				System.out.println("name: " + (String) db.getResult()[intRow][2]);

				if (strAlpha.equals("")
						|| !(Boolean) strAlpha.equals((String) db.getResult()[intRow][intGroupColIndex])) {
					item_row.createCell(0).setCellValue((String) db.getResult()[intRow][intGroupColIndex]);
					item_row.getCell(0).setCellStyle(style1);
					blnPass = true;
				} else {
					blnPass = false;
				}

				System.out.println("blnPass: " + blnPass);

				strAlpha = (String) db.getResult()[intRow][intGroupColIndex];

				if (!blnPass) {
					for (Integer x = 0; x <= col - 1; x++) {
						cell = item_row.createCell(x);

						try {
							cell = item_row.createCell(x);
							cell.setCellValue((String) db.getResult()[intRow][x]);
						} catch (ClassCastException ex) {
							try {
								BigDecimal bdValue = (BigDecimal) db.getResult()[intRow][x];
								cell.setCellValue(new BigDecimal(bdValue.toString()).doubleValue());
								cell.setCellStyle(cs_double);
							} catch (ClassCastException ey) {
								try {
									Integer intValue = (Integer) db.getResult()[intRow][x];
									strValue = intValue.toString();
									item_row.createCell(x).setCellValue(strValue);
								} catch (ClassCastException ez) {
									try {
										Timestamp tmeValue = (Timestamp) db.getResult()[intRow][x];
										strValue = tmeValue.toString();
										item_row.createCell(x).setCellValue(strValue);
									} catch (ClassCastException ea) {
										try {
											Date dteValue = (Date) db.getResult()[intRow][x];
											cell.setCellValue(dteValue);
											cell.setCellStyle(cs_date);
										} catch (ClassCastException eb) {

										}
									}
								}

							}
						}
					}
				} else {
					if (intRow > 0) {
						intRow = intRow - 1;
					} else {
						intRow = 0;
					}
				}
				intCell++;
				System.out.println("");
				System.out.println("intRow: " + intRow);
				System.out.println("row: " + row);
			}

			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(strDir + "/" + strFileName);
			} catch (FileNotFoundException e2) {
				System.out.println("");
				System.out.println("Error Line -- " + strFileName);
			}

			try {
				wb.write(fileOut);
			} catch (IOException e1) {
				System.out.println("Error Line -- wb.write(fileOut)");
			}

			try {
				fileOut.close();
			} catch (IOException e1) {
				System.out.println("Error Line -- fileOut.close();");
			}

			JOptionPane.showMessageDialog(null, "Export to file successful!");

			Desktop dt = Desktop.getDesktop();
			f = new File(strDir + "/" + strFileName);

			try {
				dt.open(f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Spreadsheet creation halted.");
		}
	}

	/*
	 * Created by Mann2x; Created Date: January 24, 2016; For the direct creation of
	 * spreadsheets with headers.
	 */
	public static void CreateXLSwithHeaders(String strCol[], String strSQL, String strFile, Integer intHead,
			String strHead[]) {
		String strFileNo = "";
		String strFileName = "";
		String strDate = "";
		Cell cell = null;

		System.out.println("");
		System.out.println("Creating Spreadsheet...");
		System.out.println(strSQL);

		pgSelect db = new pgSelect();
		db.select(strSQL);

		Integer row = db.getRowCount();
		Integer col = db.getColumnCount();
		Integer hd = intHead;

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		SimpleDateFormat sdfTo = new SimpleDateFormat("MMddyy");
		Date dateObj = new Date();
		strDate = (String) sdfTo.format(dateObj);

		/*** Cell Styles ***/
		HSSFFont font = (HSSFFont) wb.createFont();
		font.setBoldweight(font.BOLDWEIGHT_BOLD);

		HSSFCellStyle style1 = (HSSFCellStyle) wb.createCellStyle();
		style1.setFont(font);

		HSSFCellStyle style2 = (HSSFCellStyle) wb.createCellStyle();
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setFont(font);

		HSSFCellStyle style3 = (HSSFCellStyle) wb.createCellStyle();
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		HSSFCreationHelper createHelper = (HSSFCreationHelper) wb.getCreationHelper();
		HSSFCellStyle cs_date = (HSSFCellStyle) wb.createCellStyle();
		cs_date.setDataFormat(createHelper.createDataFormat().getFormat("MM/DD/YYYY"));
		cs_date.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs_date.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cs_date.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cs_date.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		HSSFCellStyle cs_double = (HSSFCellStyle) wb.createCellStyle();
		cs_double.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		cs_double.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs_double.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cs_double.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cs_double.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		/*** Cell Styles ***/

		/** Set Directory **/
		String strDir = _RealTimeDebit.OpenDir("Folder");

		/** Check if file of the same name exists within the directory **/
		Integer intSeq = 1;
		strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
		File f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");

		System.out.println("");
		System.out.println("File Name: " + strFile + strDate + "(" + strFileNo + ")" + ".xls");

		while (f.exists() && f.isFile()) {
			System.out.println("");
			System.out.println("File exists! Number: " + strFileNo);

			intSeq++;
			strFileNo = _RealTimeDebit.Padme(intSeq.toString(), 2);
			f = new File(strDir + "/" + strFile + strDate + "(" + strFileNo + ")" + ".xls");
			System.out.println("New Name: " + strFile + strDate + "(" + strFileNo + ")" + ".xls");
		}

		strFileName = strFile + strDate + "(" + strFileNo + ")" + ".xls";

		/** Begin Creating Page Header **/
		System.out.println("");
		for (int x = 0; x < hd; x++) {
			Row rowHead = sheet.createRow((short) x);
			rowHead.createCell(0).setCellValue(strHead[x]);
			rowHead.getCell(0).setCellStyle(style1);
			System.out.println(strHead[x]);
		}

		/** Begin Creating Column Header **/
		System.out.println("");
		Row rowRow = sheet.createRow((short) (hd + 1));
		rowRow.setHeightInPoints(3 * sheet.getDefaultRowHeightInPoints());
		for (int x = 0; x < col; x++) {
			rowRow.createCell(x).setCellValue(strCol[x]);
			rowRow.getCell(x).setCellStyle(style2);
			System.out.println(strCol[x]);
		}

		/** Begin Creating Excel Content **/
		if (db.isNotNull()) {
			System.out.println("");
			System.out.println("Total number of rows: " + row);

			String strValue = "";

			for (Integer intRow = 0; intRow < row; intRow++) {
				Integer shortInt = intRow + hd + 3;
				Short shortRow = Short.parseShort(shortInt.toString());
				Row item_row = sheet.createRow((short) shortRow);

				for (Integer x = 0; x <= col - 1; x++) {
					cell = item_row.createCell(x);

					try {
						item_row.createCell(x).setCellValue((String) db.getResult()[intRow][x]);
						cell.setCellValue((String) db.getResult()[intRow][x]);
						item_row.getCell(x).setCellStyle(style3);
						cell.setCellStyle(style3);
					} catch (ClassCastException ex) {
						try {
							BigDecimal bdValue = (BigDecimal) db.getResult()[intRow][x];
							cell.setCellValue(new BigDecimal(bdValue.toString()).doubleValue());
							cell.setCellStyle(cs_double);
						} catch (ClassCastException ey) {
							try {
								Integer intValue = (Integer) db.getResult()[intRow][x];
								strValue = intValue.toString();
								item_row.createCell(x).setCellValue(strValue);

								item_row.getCell(x).setCellStyle(style3);
								cell.setCellStyle(style3);
							} catch (ClassCastException ez) {
								try {
									Timestamp tmeValue = (Timestamp) db.getResult()[intRow][x];
									strValue = tmeValue.toString();
									item_row.createCell(x).setCellValue(strValue);

									System.out.println("");
									System.out.println("Timestamp Cell");
								} catch (ClassCastException ea) {
									try {
										Date dteValue = (Date) db.getResult()[intRow][x];
										cell.setCellValue(dteValue);
										cell.setCellStyle(cs_date);

										System.out.println("");
										System.out.println("Date Cell");
									} catch (ClassCastException eb) {

									}
								}
							}

						}
					}
					/*
					 * cell.setCellStyle(style3); item_row.getCell(x).setCellStyle(style3);
					 */
				}

				for (Integer x = 0; x <= col - 1; x++) {
					wb.getSheetAt(0).autoSizeColumn(x);
				}
			}

			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(strDir + "/" + strFileName);
			} catch (FileNotFoundException e2) {
				System.out.println("");
				System.out.println("Error Line -- " + strFileName);
			}

			try {
				wb.write(fileOut);
			} catch (IOException e1) {
				System.out.println("Error Line -- wb.write(fileOut)");
			}

			try {
				fileOut.close();
			} catch (IOException e1) {
				System.out.println("Error Line -- fileOut.close();");
			}

			JOptionPane.showMessageDialog(null, "Export to file successful!");

			Desktop dt = Desktop.getDesktop();
			f = new File(strDir + "/" + strFileName);

			try {
				dt.open(f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Cannot open file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Spreadsheet creation halted.");
		}
	}

	public static void createADDRESSB_DBF_FromModelWithQuery_FirstFiling(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String directory)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 1);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 19);
					String proj_id = (String) model.getValueAt(x, 20);

					listEntityID.add(String.format("'%s'", entity_id));
					listProjID.add(String.format("'%S'", proj_id));

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_addressb_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[], ARRAY[" + proj_id
					+ "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 4) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 5 || w == 6 || w == 8 || w == 9) {
						fields[w].setFieldLength(50);
					}
					if (w == 3) {
						fields[w].setFieldLength(25);
					}
					if (w == 10) {
						fields[w].setFieldLength(20);
					}
					if (w == 7) {
						fields[w].setFieldLength(35);
					}
					if (w == 11) {
						fields[w].setFieldLength(7);
					}

				}
				/*
				 * if(Arrays.asList(date_columns).contains(w)){
				 * fields[w].setDataType(DBFField.FIELD_TYPE_D); }
				 * if(Arrays.asList(bool_columns).contains(w)){
				 * fields[w].setDataType(DBFField.FIELD_TYPE_L); fields[w].setFieldLength(1); }
				 */
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						try {
							if (Arrays.asList(char_columns).contains(z)) {
								String strValue = (String) db.getResult()[y][z];
								rowData[z] = strValue.trim();
							}
						} catch (Exception e) {
							rowData[z] = "";
						}

						/*
						 * if(Arrays.asList(date_columns).contains(z)){ Date dteValue = (Date)
						 * db.getResult()[y][z]; rowData[z] = dteValue; }
						 * 
						 * if(Arrays.asList(bool_columns).contains(z)){ Boolean boolValue = (Boolean)
						 * db.getResult()[y][z];
						 * 
						 * if(z == 44){ rowData[z] = ""; }else{ rowData[z] = boolValue; } }
						 */
					}
					writer.addRecord(rowData);
				}
			}

			String strDir = directory;

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createDBF_FromModelWithQuery_FirstFiling(String columns[], Integer char_columns[],
			Integer bool_columns[], Integer date_columns[], Integer numeric_columns[], DefaultTableModel model,
			String file_name, String strDir, String strCircular) throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
			ArrayList<Integer> listSeq = new ArrayList<Integer>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 1);
				System.out.println("");

				if (isSelected) {
					String strLot = FncGlobal
							.GetString("select left('" + (String) model.getValueAt(x, 5).toString() + "', 2)");

					String entity_id = FncGlobal.GetString("select entity_id from rf_entity where entity_name ~* '"
							+ (String) model.getValueAt(x, 0) + "' and status_id = 'A'");
					String proj_id = FncGlobal.GetString("select proj_id from mf_project where proj_name = '"
							+ (String) model.getValueAt(x, 2) + "'");
					String pbl_id = FncGlobal.GetString("select pbl_id from mf_unit_info where proj_id = '" + proj_id
							+ "' and pbl_id = '" + (String) model.getValueAt(x, 20) + "'");
					Integer seq_no = FncGlobal.GetInteger("select seq_no::int from rf_sold_unit where entity_id = '"
							+ entity_id + "' and projcode = '" + proj_id + "' and pbl_id = '" + pbl_id
							+ "' and status_id != 'I'");

					listEntityID.add(String.format("'%s'", entity_id));
					listProjID.add(String.format("'%S'", proj_id));
					listPBL.add(String.format("'%s'", pbl_id));
					listSeq.add(seq_no);

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
			String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
			String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_apmaster_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[], ARRAY[" + proj_id
					+ "]::VARCHAR[], ARRAY[" + pbl_id + "]::VARCHAR[], ARRAY[" + seq_no + "]::INTEGER[], '"
					+ strCircular + "')";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);

				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					// if(w == 2 || w == 38 || w == 39){
					if (w == 2 || w == 40 || w == 41) {
						fields[w].setFieldLength(15);
					}
					// if(w == 4 || w == 5 || w == 22 || w == 23 || w == 24 || w == 25 || w == 26 ||
					// w == 28 || w == 32 || w == 37 || w == 40 || w == 5){
					if (w == 5 || w == 6 || w == 23 || w == 24 || w == 25 || w == 26 || w == 27 || w == 30 || w == 34
							|| w == 39 || w == 42) {
						fields[w].setFieldLength(20);
					}
					if (w == 7) {
						fields[w].setFieldLength(9);
					}
					if (w == 8 || w == 9 || w == 10) {
						fields[w].setFieldLength(25);
					}
					if (w == 11 || w == 12) {
						fields[w].setFieldLength(10);
					}
					// if(w == 12 || w == 30 || w == 31 || w == 34){
					if (w == 13 || w == 32 || w == 33 || w == 36) {
						fields[w].setFieldLength(1);
					}
					if (w == 16 || w == 43 || w == 44) {
//						if(proj_id.contains("019")) {
//							fields[w].setFieldLength(55);// new pag ibig lenght for er-1b
//
//						}
//						else
//						{
							fields[w].setFieldLength(40);

//						}
					}
					if (w == 35) {
						fields[w].setFieldLength(50);
					}
					if (w == 28) {
						fields[w].setFieldLength(30);
					}
				}

				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}

				if (Arrays.asList(bool_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_L);
					fields[w].setFieldLength(1);
				}

				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 0) {
						
//						if(proj_id.contains("019")) {
//							fields[w].setFieldLength(9);// new pag ibig lenght for er-1b
//							fields[w].setDecimalCount(0);
//						}
//						else
//						{
							fields[w].setFieldLength(7);
							fields[w].setDecimalCount(0);
//						}
						
					}
					if (w == 1 || w == 19 || w == 20 || w == 29) {
//						if(proj_id.contains("019")) {
//							fields[w].setFieldLength(9);
//							fields[w].setDecimalCount(0);
//						}
//						else 
//						{
							fields[w].setFieldLength(10);
							fields[w].setDecimalCount(0);
//						}
						
					}
					if (w == 14) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(2);
					}
					if (w == 15) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
					if (w == 18) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(7);
					}
					if (w == 21 || w == 22) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(7);
					}
					// if(w == 35){
					if (w == 37) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(2);
					}
					// if(w == 36){
					if (w == 38) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
					// if(w == 43){
					if (w == 45) {
						fields[w].setFieldLength(7);
						fields[w].setDecimalCount(0);
					}
					// if(w == 45){
					if (w == 47) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						try {
							if (Arrays.asList(numeric_columns).contains(z)) {
								String strValue = (String) db.getResult()[y][z];
								rowData[z] = Double.valueOf(strValue);
							}
						} catch (NumberFormatException ex) {
							System.out.println("number exception caught.");
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}

						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}

						if (Arrays.asList(bool_columns).contains(z)) {
							Boolean boolValue = (Boolean) db.getResult()[y][z];

							/*
							 * if(z == 44){ rowData[z] = ""; }else{
							 */
							rowData[z] = boolValue;
							// }
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createADDRESSB_DBF_FromModelWithQuery_AdvancedCI(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String directory)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 12);
					String proj_id = (String) model.getValueAt(x, 13);

					listEntityID.add(String.format("'%s'", entity_id));
					listProjID.add(String.format("'%S'", proj_id));

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_addressb_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[], ARRAY[" + proj_id
					+ "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 4) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 5 || w == 6 || w == 8 || w == 9) {
						fields[w].setFieldLength(50);
					}
					if (w == 3) {
						fields[w].setFieldLength(25);
					}
					if (w == 10) {
						fields[w].setFieldLength(20);
					}
					if (w == 7) {
						fields[w].setFieldLength(35);
					}
					if (w == 11) {
						fields[w].setFieldLength(7);
					}

				}

				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						try {
							if (Arrays.asList(char_columns).contains(z)) {
								String strValue = (String) db.getResult()[y][z];
								rowData[z] = strValue.trim();
							}
						} catch (Exception e) {
							rowData[z] = "";
						}
					}
					writer.addRecord(rowData);
				}
			}

			String strDir = directory;

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			e.printStackTrace();
		}
	}

	public static void createADDRESSA_DBF_FromModelWithQuery_AdvancedCI(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {
		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 12);

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_addressa_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);
			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3 || w == 10) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 5 || w == 7 || w == 8) {
						fields[w].setFieldLength(50);
					}
					if (w == 6) {
						fields[w].setFieldLength(35);
					}
					if (w == 9) {
						fields[w].setFieldLength(20);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			e.printStackTrace();
		}
	}

	public static void createADDRESSE_DBF_FromModelWithQuery_AdvancedCI(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 12);
					String proj_id = (String) model.getValueAt(x, 13);

					listEntityID.add(String.format("'%s'", entity_id));

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_addresse_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(1);
					}
					if (w == 4 || w == 7 || w == 8 || w == 10 || w == 11) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(25);
					}
					if (w == 6 || w == 12) {
						fields[w].setFieldLength(20);
					}
					if (w == 9) {
						fields[w].setFieldLength(35);
					}
					if (w == 13) {
						fields[w].setFieldLength(7);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			e.printStackTrace();
		}
	}

	public static void createDBF_FromModelWithQuery_AdvancedCI(String columns[], Integer char_columns[],
			Integer bool_columns[], Integer date_columns[], Integer numeric_columns[], DefaultTableModel model,
			String file_name, String strDir, String strCircular) throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();
			ArrayList<String> listProjID = new ArrayList<String>();
			ArrayList<String> listPBL = new ArrayList<String>();
			ArrayList<Integer> listSeq = new ArrayList<Integer>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {
					String strLot = FncGlobal
							.GetString("select left('" + (String) model.getValueAt(x, 7).toString() + "', 2)");

					String entity_id = (String) model.getValueAt(x, 12);
					String proj_id = (String) model.getValueAt(x, 13);
					String pbl_id = (String) model.getValueAt(x, 14);
					Integer seq_no = Integer.parseInt(model.getValueAt(x, 15).toString());

					listEntityID.add(String.format("'%s'", entity_id));
					listProjID.add(String.format("'%S'", proj_id));
					listPBL.add(String.format("'%s'", pbl_id));
					listSeq.add(seq_no);

				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");
			String proj_id = listProjID.toString().replaceAll("\\[|\\]", "");
			String pbl_id = listPBL.toString().replaceAll("\\[|\\]", "");
			String seq_no = listSeq.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_apmaster_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[], ARRAY[" + proj_id
					+ "]::VARCHAR[], ARRAY[" + pbl_id + "]::VARCHAR[], ARRAY[" + seq_no + "]::INTEGER[], '"
					+ strCircular + "')";

			System.out.println("");
			System.out.println("SQL: " + SQL);

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);

				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					// if(w == 2 || w == 38 || w == 39){
					if (w == 2 || w == 40 || w == 41) {
						fields[w].setFieldLength(15);
					}
					// if(w == 4 || w == 5 || w == 22 || w == 23 || w == 24 || w == 25 || w == 26 ||
					// w == 28 || w == 32 || w == 37 || w == 40 || w == 5){
					if (w == 5 || w == 6 || w == 23 || w == 24 || w == 25 || w == 26 || w == 27 || w == 30 || w == 34
							|| w == 39 || w == 42) {
						fields[w].setFieldLength(20);
					}
					if (w == 7) {
						fields[w].setFieldLength(9);
					}
					if (w == 8 || w == 9 || w == 10) {
						fields[w].setFieldLength(25);
					}
					if (w == 11 || w == 12) {
						fields[w].setFieldLength(10);
					}
					// if(w == 12 || w == 30 || w == 31 || w == 34){
					if (w == 13 || w == 32 || w == 33 || w == 36) {
						fields[w].setFieldLength(1);
					}
					if (w == 16 || w == 43 || w == 44) {
						fields[w].setFieldLength(40);
					}
					if (w == 35) {
						fields[w].setFieldLength(50);
					}
					if (w == 28) {
						fields[w].setFieldLength(30);
					}
				}

				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}

				if (Arrays.asList(bool_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_L);
					fields[w].setFieldLength(1);
				}

				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 0) {
						fields[w].setFieldLength(7);
						fields[w].setDecimalCount(0);
					}
					if (w == 1 || w == 19 || w == 20 || w == 29) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
					if (w == 14) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(2);
					}
					if (w == 15) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
					if (w == 18) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(7);
					}
					if (w == 21 || w == 22) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(7);
					}
					// if(w == 35){
					if (w == 37) {
						fields[w].setFieldLength(15);
						fields[w].setDecimalCount(2);
					}
					// if(w == 36){
					if (w == 38) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
					// if(w == 43){
					if (w == 45) {
						fields[w].setFieldLength(7);
						fields[w].setDecimalCount(0);
					}
					// if(w == 45){
					if (w == 47) {
						fields[w].setFieldLength(10);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						try {
							if (Arrays.asList(numeric_columns).contains(z)) {
								String strValue = (String) db.getResult()[y][z];
								rowData[z] = Double.valueOf(strValue);
							}
						} catch (NumberFormatException ex) {
							System.out.println("number exception caught.");
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}

						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}

						if (Arrays.asList(bool_columns).contains(z)) {
							Boolean boolValue = (Boolean) db.getResult()[y][z];

							/*
							 * if(z == 44){ rowData[z] = ""; }else{
							 */
							rowData[z] = boolValue;
							// }
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createCHAR_REF_DBF_FromModelWithQuery_AdvancedCI(String columns[], Integer char_columns[],
			Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 12);

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_char_ref_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0) {
						fields[w].setFieldLength(15);
					}
					if (w == 3) {
						fields[w].setFieldLength(35);
					}
					if (w == 4) {
						fields[w].setFieldLength(50);
					}
					if (w == 5) {
						fields[w].setFieldLength(20);
					}
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1 || w == 2) {
						fields[w].setFieldLength(5);
						fields[w].setDecimalCount(0);
					}
				}
			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
			JOptionPane.showMessageDialog(null, "Export to file successful!");

		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createDOADATA_DBF_FromModelWithQuery_AdvancedCI(String columns[], Integer char_columns[],
			Integer date_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 12);

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_doadata_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0) {
						fields[w].setFieldLength(15);
					}
					if (w == 2 || w == 3 | w == 4 || w == 5) {
						fields[w].setFieldLength(5);
					}
					if (w == 6) {
						fields[w].setFieldLength(50);
					}
				}
				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}

			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createINSURE_DBF_FromModelWithQuery_AdvancedCI(String columns[], Integer char_columns[],
			Integer date_columns[], Integer numeric_columns[], DefaultTableModel model, String file_name, String strDir)
			throws DBFException, IOException {

		try {
			ArrayList<String> listEntityID = new ArrayList<String>();

			for (Integer x = 0; x < model.getRowCount(); x++) {

				Boolean isSelected = (Boolean) model.getValueAt(x, 0);
				System.out.println("");

				if (isSelected) {

					String entity_id = (String) model.getValueAt(x, 12);

					listEntityID.add(String.format("'%s'", entity_id));
				}
			}

			String entity_id = listEntityID.toString().replaceAll("\\[|\\]", "");

			pgSelect db = new pgSelect();
			String SQL = "SELECT * FROM view_insure_dbf_export(ARRAY[" + entity_id + "]::VARCHAR[])";

			db.select(SQL);
			FncSystem.out("Display SQL for apmaster_dbf_export", SQL);

			/*
			 * System.out.printf("Display Column Count: %s%n", db.getColumnCount());
			 * System.out.printf("Display COlumn Count: %s%n", columns.length);
			 */

			DBFField fields[] = new DBFField[db.getColumnCount()];

			for (int w = 0; w <= db.getColumnCount() - 1; w++) {
				fields[w] = new DBFField();
				// System.out.printf("Display Value of Field Name: (%s)%n", columns[w]);
				fields[w].setFieldName(columns[w]);
				if (Arrays.asList(char_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_C);
					if (w == 0 || w == 3) {
						fields[w].setFieldLength(15);
					}
					if (w == 2) {
						fields[w].setFieldLength(4);
					}
				}
				if (Arrays.asList(date_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_D);
				}
				if (Arrays.asList(numeric_columns).contains(w)) {
					fields[w].setDataType(DBFField.FIELD_TYPE_N);
					if (w == 1) {
						fields[w].setFieldLength(9);
						fields[w].setDecimalCount(0);
					}
				}

			}

			DBFWriter writer = new DBFWriter();

			writer.setFields(fields);

			if (db.isNotNull()) {
				Object rowData[] = new Object[db.getColumnCount()];

				for (int y = 0; y < db.getRowCount(); y++) {
					rowData = new Object[db.getColumnCount()];

					// for(int z=0; z<=columns.length-1; z++){
					for (int z = 0; z <= db.getColumnCount() - 1; z++) {
						System.out.printf("Display Value of Result: row:%s-column:%s (%s)%n%n", y, z,
								db.getResult()[y][z]);

						if (Arrays.asList(char_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = strValue.trim();
						}
						if (Arrays.asList(date_columns).contains(z)) {
							Date dteValue = (Date) db.getResult()[y][z];
							rowData[z] = dteValue;
						}
						if (Arrays.asList(numeric_columns).contains(z)) {
							String strValue = (String) db.getResult()[y][z];
							rowData[z] = Double.valueOf(strValue);
						}
					}
					writer.addRecord(rowData);
				}
			}

			FileOutputStream fos = new FileOutputStream(new File(strDir + "/" + file_name));
			writer.write(fos);
			fos.close();
		} catch (DBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
