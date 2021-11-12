package org.sdk6.tools.export;

import java.io.IOException;

import javax.swing.JTable;

public interface Exporter {

	boolean export(JTable table, String path, String sheetTitle, String[] headers, boolean start) throws IOException;
}
