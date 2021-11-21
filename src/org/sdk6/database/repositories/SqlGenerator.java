package org.sdk6.database.repositories;

import java.util.Map;

public interface SqlGenerator {

	/**
	 * Generate select sql from database with specific items.
	 * 
	 * @param table     The table name.
	 * @param columns   The columns names.
	 * @param condition The condition for selection.
	 * @param orderby   Sort the table.
	 * @return The generated query.
	 */
	String getSelectSQL(String table, String[] columns, String condition, String orderby);

	/**
	 * Generate insert sql from database with specific items.
	 * 
	 * @param table  The table name.
	 * @param values The columns want to insert into table.
	 * @return The generated query.
	 */
	String getInsertSQL(String table, String[] values);

	/**
	 * Generate insert sql from database with specific items.
	 * 
	 * @param table     The table name.
	 * @param colValues The columns with values want to insert into table.
	 * @return The generated query.
	 */
	String getInsertSQL(String table, Map<String, String> colValues);

	/**
	 * Generate delete sql from database with specific values.
	 * 
	 * @param table     The table name.
	 * @param condition The condition want to delete from table.
	 * @return The generated query.
	 */
	String getDeleteSQL(String table, String condition);

	/**
	 * Generate update sql from database with specific values.
	 * @param table The table name.
	 * @param colValues The columns with values want to insert into table.
	 * @param condition The condition want to delete from table.
	 * @return The generated query.
	 */
	String getUpdateSQL(String table, Map<String, String> colValues, String condition);
}
