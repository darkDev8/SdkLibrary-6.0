package org.sdk6.tools.export;

import java.io.IOException;

import javax.swing.JTable;

import org.sdk6.io.tool.DirectoryUtils;
import org.sdk6.io.tool.FileUtils;
import org.sdk6.tools.OSTools;

import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;

public class PdfFile implements Exporter {
	private int rowHeight;
	private int columnWidth;

	private boolean rightToLeft;

	public PdfFile(int rowHeight, int columnWidth) {
		super();
		this.rowHeight = rowHeight;
		this.columnWidth = columnWidth;
	}

	@Override
	public boolean export(JTable table, String path, String sheetTitle, String[] headers, boolean start)
			throws IOException {
		ExcelFile file = new ExcelFile(rowHeight, columnWidth).setRightToLeft(rightToLeft);

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
