package org.sdk6.tools.export;

public class TableExportFactory {

	private int rowHeight;
	private int columnWidth;

	private boolean nextLine;

	public TableExportFactory() {
		rowHeight = 0;
		columnWidth = 0;
		
		nextLine = false;
	}

	public boolean isNextLine() {
		return nextLine;
	}

	public TableExportFactory setNextLine(boolean nextLine) {
		this.nextLine = nextLine;
		return this;
	}

	public int getRowHeight() {
		return rowHeight;
	}

	public TableExportFactory setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
		return this;
	}

	public int getColumnWidth() {
		return columnWidth;
	}

	public TableExportFactory setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
		return this;
	}

	public Exporter getInstance(String name) {
		switch (name.trim().toLowerCase()) {
		case "pdf":
			return new PdfFile(rowHeight, columnWidth);

		case "excel":
			return new ExcelFile(rowHeight, columnWidth);

		case "json":
			return new JsonFile().setNextLine(nextLine);

		default:
			return null;
		}
	}
}
