package org.sdk6.database.repositories;

import net.proteanit.sql.DbUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.sdk6.data.types.Strings;
import org.sdk6.database.connections.MicrosoftAccessConnection;
import org.sdk6.database.connections.MysqlConnection;
import org.sdk6.database.connections.SqlServerConnection;
import org.sdk6.database.connections.SqliteConnection;
import org.sdk6.swing.Swing;
import org.sdk6.swing.Tables;
import org.sdk6.tools.OSTools;

public class DatabaseRepository implements TableListener, SqlGenerator {

	private Connection connection;

	/**
	 * The DatabaseRepository class object.
	 * 
	 * @param microsoftAccessConnection The MicrosoftAccessConnection object.
	 */
	public DatabaseRepository(MicrosoftAccessConnection microsoftAccessConnection) {
		connection = microsoftAccessConnection.getConnection();
	}

	/**
	 * The DatabaseRepository class object.
	 * 
	 * @param sqLiteConnection The SqliteConnection object.
	 */
	public DatabaseRepository(SqliteConnection sqLiteConnection) {
		connection = sqLiteConnection.getConnection();
	}

	/**
	 * The DatabaseRepository class object.
	 * 
	 * @param mySQLConnection The MysqlConnection object.
	 */
	public DatabaseRepository(MysqlConnection mySQLConnection) {
		connection = mySQLConnection.getConnection();
	}

	/**
	 * The DatabaseRepository class object.
	 * 
	 * @param sqlServerConnection The SqlServerConnection object.
	 */
	public DatabaseRepository(SqlServerConnection sqlServerConnection) {
		connection = sqlServerConnection.getConnection();
	}

	/**
	 * The DatabaseRepository class object.
	 * 
	 * @param connection The Connection object.
	 */
	public DatabaseRepository(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean searchTable(String table, String column, String key) {
		try {
			String sql = getSelectSQL(table, new String[] { column }, column + " = ?", "");
			Object data = null;

			try (PreparedStatement prstmt = connection.prepareStatement(sql)) {
				prstmt.setString(1, key);

				ResultSet rs = prstmt.executeQuery();
				while (rs.next()) {
					data = rs.getString(column);
				}
			}

			return Objects.equals(data, key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeTable(String table) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE " + table);
			return true;
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean createTable(String table, Map<String, String> columns, String id) {
		StringBuilder sql = new StringBuilder("CREATE TABLE ").append(table).append(" (");

		if (Objects.isNull(columns)) {
			return false;
		} else {
			if (columns.isEmpty()) {
				return false;
			}
		}

		if (tableExists(table)) {
			return true;
		}

		for (Map.Entry m : columns.entrySet()) {
			if (Objects.equals(m.getKey(), id)) {
				sql.append(m.getKey()).append(" ").append(m.getValue()).append(" PRIMARY KEY,");
			} else {
				sql.append(m.getKey()).append(" ").append(m.getValue()).append(",");
			}
		}

		char[] arr = sql.toString().toCharArray();
		new Strings().clearStringBuilder(sql);

		for (int i = 0; i < arr.length - 1; i++) {
			sql.append(arr[i]);
		}

		sql.append(");");

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql.toString());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean tableExists(String table) {
		try {
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet tables = dbm.getTables(null, null, table, null);
			return tables.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Check the table to see the column exists or not.
	 * 
	 * @param table  The table want to search in it.
	 * @param column The column want to search in the table.
	 * @return True if column exists and false if not.
	 */
	public boolean columnExists(String table, String column) {
		try {
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet columns = dbm.getColumns(null, null, table, column);
			return columns.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public long countRecords(String table) {
		try {
			int count = 0;
			ResultSet rs = connection.createStatement().executeQuery("SELECT COUNT(*) FROM " + table);

			while (rs.next()) {
				count = rs.getInt(1);
			}

			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public String getSelectSQL(String table, String[] columns, String condition, String orderby) {
		try {
			Strings strings = new Strings();

			if (strings.isNullOrEmpty(columns)) {
				return null;
			}

			StringBuilder sql = new StringBuilder("SELECT ");

			for (int i = 0; i < columns.length; i++) {
				sql.append(columns[i]);

				if ((i + 1) < columns.length) {
					sql.append(",");
				}
			}

			sql.append(" FROM ").append(table);

			if (!strings.isNullOrEmpty(condition)) {
				sql.append(" WHERE ").append(condition);
			}

			if (!strings.isNullOrEmpty(orderby)) {
				sql.append(" ORDER BY ").append(orderby);
			}

			return sql.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getInsertSQL(String table, String[] values) {
		try {
			Strings strings = new Strings();
			StringBuilder sql = new StringBuilder("INSERT INTO ").append(table).append(" (");

			for (int i = 0; i < values.length; i++) {
				sql.append(values[i]);

				if ((i + 1) < values.length) {
					sql.append(",");
				}
			}

			sql.append(") VALUES (");

			for (int i = 0; i < values.length; i++) {
				sql.append("?");

				if ((i + 1) < values.length) {
					sql.append(",");
				}
			}

			return sql.append(")").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getInsertSQL(String table, Map<String, String> colValues) {
		try {
			Strings strings = new Strings();
			StringBuilder sql = new StringBuilder("INSERT INTO ").append(table).append(" (");
			int count = 0;

			if (Objects.isNull(colValues)) {
				return null;
			}

			for (Map.Entry<String, String> entry : colValues.entrySet()) {
				sql.append(entry.getKey());

				if ((count + 1) < colValues.size()) {
					sql.append(",");
				}

				count++;
			}

			sql.append(") VALUES (");
			count = 0;

			for (Map.Entry<String, String> entry : colValues.entrySet()) {
				sql.append(entry.getValue());

				if ((count + 1) < colValues.size()) {
					sql.append(",");
				}

				count++;
			}

			return sql.append(")").toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getDeleteSQL(String table, String condition) {
		try {
			Strings strings = new Strings();

			StringBuilder sql = new StringBuilder("DELETE FROM ").append(table);

			if (strings.isNullOrEmpty(condition)) {
				return sql.toString();
			}

			return sql.append(" WHERE ").append(condition).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getUpdateSQL(String table, Map<String, String> colValues, String condition) {
		try {
			Strings strings = new Strings();
			StringBuilder sql = new StringBuilder("UPDATE ").append(table).append(" SET ");
			int count = 0;

			if (Objects.isNull(colValues)) {
				return null;
			}

			for (Map.Entry<String, String> entry : colValues.entrySet()) {
				sql.append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");

				count++;
				if ((count + 1) < colValues.size()) {
					sql.append(",");
				}
			}

			if (!strings.isNullOrEmpty(condition)) {
				sql.append(" WHERE ").append(condition);
			}

			return sql.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get entire specific column data from table.
	 * 
	 * @param table  The table want to search in it.
	 * @param column The column want to get the data from.
	 * @param type   The data type of the column.
	 * @return The column data from table.
	 */
	public ArrayList<Object> getColumnData(String table, String column, String type) {
		try {
			ArrayList<Object> values = new ArrayList<>();

			String sql = "SELECT " + column + " FROM " + table;
			try (PreparedStatement prstmt = connection.prepareStatement(sql)) {

				ResultSet rs = prstmt.executeQuery();
				while (rs.next()) {

					switch (type.toLowerCase()) {
					case "int":
						values.add(rs.getInt(column));
						break;

					case "float":
						values.add(rs.getFloat(column));
						break;

					case "bytes":
						values.add(rs.getBytes(column));
						break;

					case "date":
						values.add(rs.getDate(column));
						break;

					default:
						values.add(rs.getString(column));
						break;
					}
				}
			}

			return values;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Clears JTable and load data from table.
	 * 
	 * @param table   The table name.
	 * @param columns The table columns want to select from table.
	 * @param jTable  The Swing table.
	 */
	public void loadJTable(String table, String[] columns, JTable jTable) {
		try {
			new Tables().clear(jTable, true);

			PreparedStatement st = connection.prepareStatement(getSelectSQL(table, columns, "", ""));
			ResultSet rs = st.executeQuery();
			jTable.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clears JTable and load data from table.
	 * 
	 * @param table   The table name.
	 * @param columns The table columns want to select from table.
	 * @param jTable  The Swing table.
	 * @param rows    The number of rows in the JTable.
	 */
	public void loadJTable(String table, String[] columns, JTable jTable, int rows) {
		try {
			Tables tables = new Tables();
			tables.clear(jTable, true);

			PreparedStatement st = connection.prepareStatement(getSelectSQL(table, columns, "", ""));
			ResultSet rs = st.executeQuery();
			jTable.setModel(DbUtils.resultSetToTableModel(rs));

			DefaultTableModel model = (DefaultTableModel) jTable.getModel();
			if (rows == model.getRowCount()) {
				tables.clear(jTable, false);
			} else {
				if (rows > tables.countRowsColumns(jTable, "r")) {
					rows = model.getRowCount();
				}
			}

			for (int i = model.getRowCount() - 1; i >= rows; i--) {
				model.removeRow(i);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Export JTable data(rows,columns) into Microsoft excel file.
	 * 
	 * @param table      The swing JTable.
	 * @param title      Column titles from table.
	 * @param sheetTitle The tile of sheet.
	 * @param path       The excel file path.
	 * @param start      If true it will open file after export completed.
	 * 
	 * @throws Exception Throws exception if operation was not successful.
	 */
	public void exportJTableToExcel(JTable table, String[] title, String sheetTitle, String path, boolean start)
			throws IOException {
		FileOutputStream fos = null;

		try {
			int rowID = 0;
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet ws = wb.createSheet(sheetTitle);
			
			TreeMap<String, Object[]> data = new TreeMap<>();
			List<Object> titles = new ArrayList<>();

			for (int i = 0; i < title.length; i++) {
				titles.add(title[i]);
			}

			data.put("0", titles.toArray());

			for (int i = 0; i < dtm.getRowCount(); i++) {
				List<Object> objects = new ArrayList<>();
				for (int j = 0; j < dtm.getColumnCount(); j++) {
					objects.add(dtm.getValueAt(i, j));

				}

				data.put(String.valueOf(i + 1), objects.toArray());
			}

			Set<String> ids = data.keySet();
			XSSFRow row;

			for (String key : ids) {
				row = ws.createRow(rowID++);
				Object[] values = data.get(key);

				int cellID = 0;
				for (Object o : values) {
					Cell cell = row.createCell(cellID++);
					cell.setCellValue(o.toString());
				}
			}

			if (!path.endsWith(".xlsx")) {
				path = path.concat(".xlsx");
			}

			fos = new FileOutputStream(path);
			wb.write(fos);

			if (start) {
				new OSTools().openFile(path);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (!Objects.isNull(fos)) {
				fos.close();
			}
		}
	}

	@Override
	public boolean clearTable(String table) {
		try {
			Statement st = connection.createStatement();
			st.execute("DELETE FROM " + table);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
