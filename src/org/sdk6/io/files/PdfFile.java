package org.sdk6.io.files;

import java.io.IOException;

import javax.swing.JTable;

import org.sdk6.io.directories.DirectoryUtils;
import org.sdk6.tools.OSTools;

import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;

public class PdfFile extends FileUtils {
	private int rowHeight;
	private int columnWidth;

	private boolean rightToLeft;

	public PdfFile(String path) {
		super(path);

		this.rowHeight = 20;
		this.columnWidth = 30;
	}

	public PdfFile(String path, int rowHeight, int columnWidth) {
		super(path);

		this.rowHeight = rowHeight;
		this.columnWidth = columnWidth;
	}

	public boolean export(JTable table, String path, String sheetTitle, String[] headers, boolean start)
			throws IOException {

		ExcelFile file = new ExcelFile(path, rowHeight, columnWidth).setRightToLeft(rightToLeft);

		if (file.export(table, path, sheetTitle, headers, false)) {
			Workbook workbook = new Workbook();

			workbook.loadFromFile(path);
			workbook.getConverterSetting().setSheetFitToPage(true);

			FileUtils fu = new FileUtils(path);
			DirectoryUtils du = new DirectoryUtils(path);
			String pdfFilePath = du.getDirectoryPath() + "/" + fu.getBaseName() + ".pdf";

			workbook.saveToFile(pdfFilePath, FileFormat.PDF);
			fu.delete();

			if (start) {
				new OSTools().openFile(pdfFilePath);
			}

			return true;
		}

		return false;
	}

	public int getRowHeight() {
		return rowHeight;
	}

	public PdfFile setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
		return this;
	}

	public int getColumnWidth() {
		return columnWidth;
	}

	public PdfFile setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
		return this;
	}

	public boolean isRightToLeft() {
		return rightToLeft;
	}

	public PdfFile setRightToLeft(boolean rightToLeft) {
		this.rightToLeft = rightToLeft;
		return this;
	}
}