package org.sdk6.database.repositories;

import java.util.Map;

public interface TableListener {
	/**
	 * Search the table in the database.
	 * 
	 * @param table  The table in the database.
	 * @param column The column want to search.
	 * @param key    The key want to find in table.
	 * @return True if key was found and false if not.
	 */
	boolean searchTable(String table, String column, String key);

	/**
	 * Remove table from database.
	 * 
	 * @param table The table want to remove from database.
	 * @return True if remove was successful and false if not.
	 */
	boolean removeTable(String table);

	/**
	 * Create table in the database.
	 * 
	 * @param table   The table name want to create.
	 * @param columns The table columns with specific types.
	 * @param id      The table primary key.
	 * @return True if table creation was successful and false if not.
	 */
	boolean createTable(String table, Map<String, String> columns, String id);

	/**
	 * Check the database to see the table exists or not.
	 * 
	 * @param table The table name from database.
	 * @return True if table exists and false if not.
	 */
	boolean tableExists(String table);

	/**
	 * Count the table records.
	 * 
	 * @param table The table name.
	 * @return Number of records in the table.
	 */
	long countRecords(String table);

	/**
	 * Clears the table.
	 * 
	 * @param table The table name.
	 * @return True if clear was successful and false if not.
	 */
	boolean clearTable(String table);
}
