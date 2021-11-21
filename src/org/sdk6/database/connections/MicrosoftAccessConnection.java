package org.sdk6.database.connections;

import java.sql.Connection;
import java.sql.DriverManager;

import org.sdk6.io.files.FileUtils;

import com.healthmarketscience.jackcess.Database;

public class MicrosoftAccessConnection {

	private Connection connection;
	private String connectionString;

	private String path;
	private String databaseName;

	/**
	 * Constructor of MicrosoftAccessConnection class.
	 * 
	 * @param path The database path.
	 * @param path
	 */
	public MicrosoftAccessConnection(String path) {
		this.path = path;
		connect();
	}

	/**
	 * Connect to the database and establish connection.
	 * 
	 * @return True if connection was successful and false if not.
	 */
	public boolean connect() {
		try {
			FileUtils fp = new FileUtils(path);

			if (fp.exists()) {
				connectionString = "jdbc:ucanaccess://" + path;
				databaseName = fp.getName();

				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				connection = DriverManager.getConnection(connectionString);

				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Get Connection object.
	 * 
	 * @return The connection object.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Get connection string.
	 * 
	 * @return The database connection string.
	 */
	public String getConnectionString() {
		return connectionString;
	}

	/**
	 * Get the database file name.
	 * 
	 * @return The database file name.
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * Get the database file path.
	 * 
	 * @return The database file path.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Set the database file path.
	 * 
	 * @param path The database file path.
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
