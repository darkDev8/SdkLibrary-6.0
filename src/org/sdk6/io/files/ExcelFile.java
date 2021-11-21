package org.sdk6.io.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sdk6.tools.OSTools;

public class ExcelFile extends FileUtils {
	private int rowHeight;
	private int columnWidth;

	private boolean rightToLeft;

	public ExcelFile(String path) {
		super(path);
		this.rowHeight = 20;
		this.columnWidth = 30;
	}

	public ExcelFile(String path, int rowHeight, int columnWidth) {
		super(path);
		this.rowHeight = rowHeight;
		this.columnWidth = columnWidth;
	}

	public boolean export(JTable table, String path, String sheetTitle, String[] headers, boolean start)
			throws IOException {
		FileOutputStream fos = null;

		try {
			int rowID = 0;
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet ws = wb.createSheet(sheetTitle);

			ws.setRightToLeft(rightToLeft);
			TreeMap<String, Object[]> data = new TreeMap<>();

			List<Object> titles = new ArrayList<>();

			for (int i = 0; i < headers.length; i++) {
				titles.add(headers[i]);
			}

			data.put("0", titles.toArray());

			for (int i = 0; i < dtm.getRowCount(); i++) {
				List<Object> objects = new ArrayList<>();
				for (int j = 0; j < dtm.getColumnCount(); j++) {
					Object obj = dtm.getValueAt(i, j);

					if (obj instanceof JLabel) {
						objects.add(((JLabel) obj).getText());
					} else if (obj instanceof JButton) {
						objects.add(((JButton) obj).getText());
					} else if (obj instanceof JTextField) {
						objects.add(((JTextField) obj).getText());
					} else if (obj instanceof JTextField) {
						objects.add(((JTextField) obj).getText());
					} else {
						objects.add(obj);
					}
				}

				data.put(String.valueOf(i + 1), objects.toArray());
			}

			Set<String> ids = data.keySet();
			XSSFRow row;

			for (int i = 0; i < data.size(); i++) {
				ws.setColumnWidth(i, columnWidth * 282);
			}

			for (String key : ids) {
				row = ws.createRow(rowID++);

				row.setHeight((short) (rowHeight + 300));

				Object[] values = data.get(key);

				int cellID = 0;
				for (Object o : values) {
					Cell cell = row.createCell(cellID++);
					cell.setCellValue(o.toString());
				}
			}

			fos = new FileOutputStream(path);
			wb.write(fos);

			if (start) {
				new OSTools().openFile(path);
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (!Objects.isNull(fos)) {
				fos.close();
			}
		}

		return false;
	}

	public void printInformation() {

	}

	public int countSheets() {
		return -1;
	}

	public long countWords(int sheetIndex) {
		return -1;
	}

	public boolean search(String key, boolean match) {
		return false;
	}

	public boolean convertToPdf(String destinationPath, boolean openFile) {
		return false;
	}

	public boolean replace(String key, String replace, boolean match) {
		return false;
	}

	public int getRowHeight() {
		return rowHeight;
	}

	public ExcelFile setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
		return this;
	}

	public int getColumnWidth() {
		return columnWidth;
	}

	public ExcelFile setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
		return this;
	}

	public boolean isRightToLeft() {
		return rightToLeft;
	}

	public ExcelFile setRightToLeft(boolean rightToLeft) {
		this.rightToLeft = rightToLeft;
		return this;
	}
}