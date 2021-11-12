package org.sdk6.swing;

import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.sdk6.data.types.Strings;

public class Tables {

	/**
	 * Counts JTable rows or columns.
	 *
	 * @param table     The JTable object.
	 * @param selection Select the rows or columns to count, r for rows and c for
	 *                  columns.
	 * @return Number of table rows or columns.
	 */
	public int countRowsColumns(JTable table, String selection) {
		if (selection.equalsIgnoreCase("r")) {
			return table.getModel().getRowCount();
		} else if (selection.equalsIgnoreCase("c")) {
			return table.getModel().getColumnCount();
		}

		return -1;
	}

	/**
	 * Clear all JTable object rows including headers(columns).
	 *
	 * @param table   The JTable.
	 * @param columns Clear columns or not.
	 */
	public void clear(JTable table, boolean columns) {
		try {
			if (columns) {
				table.setModel(new DefaultTableModel());
			} else {
				DefaultTableModel dm = (DefaultTableModel) table.getModel();
				while (dm.getRowCount() > 0) {
					dm.removeRow(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints the JTable with specified title and format.
	 *
	 * @param table  The JTable object.
	 * @param title  The page title.
	 * @param format Specified format.
	 * @return True if print was successful and false if not.
	 */
	public void print(JTable table, String title, String format) throws PrinterException {
		if (!new Strings().isNullOrEmpty(format)) {
			MessageFormat footer = new MessageFormat(format);
			table.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat(title), footer);
		} else {
			table.print(JTable.PrintMode.FIT_WIDTH);
		}
	}

	/**
	 * Sort JTable object alphabetically.
	 * 
	 * @param table       The JTable object.
	 * @param sortType    The sort type (ascending/descending).
	 * @param columnIndex The index of column in JTable.
	 */
	public void sort(JTable table, String sortType, int columnIndex) {
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();

		switch (sortType.toLowerCase()) {
		case "asc":
			sortKeys.add(new RowSorter.SortKey(columnIndex, SortOrder.ASCENDING));
			break;

		case "des":
			sortKeys.add(new RowSorter.SortKey(columnIndex, SortOrder.DESCENDING));
			break;

		}

		sorter.setSortKeys(sortKeys);
	}

	/**
	 * change the column header text position.
	 * 
	 * @param table    The JTable object.
	 * @param position The position of header(right, left, center).
	 */
	public void setColumnHeaderPosition(JTable table, String position) {
		switch (position.toLowerCase()) {
		case "right":
			((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
					.setHorizontalAlignment(JLabel.RIGHT);
			break;

		case "left":
			((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
					.setHorizontalAlignment(JLabel.LEFT);
			break;

		case "center":
			((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
					.setHorizontalAlignment(JLabel.CENTER);
			break;
		}
	}
	
	/**
	 * Disables a JTable edit option.
	 *
	 * @param table The JTable object.
	 */
	public void disableTableEdit(JTable table) {
		table.setDefaultEditor(Object.class, null);
	}
}
