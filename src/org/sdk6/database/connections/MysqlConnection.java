package org.sdk6.database.connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConnection {

	private Connection connection;
	private String connectionString;

	private int port;

	private String ip;
	private String username;
	private String password;
	private String databaseName;

	/**
	 * Constructor of MysqlConnection class.
	 * 
	 * @param ip           The database IP.
	 * @param port         The database port number.
	 * @param username     The username for connection.
	 * @param password     The password for connection.
	 * @param databaseName The database name.
	 */
	public MysqlConnection(String ip, int port, String username, String password, String databaseName) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.databaseName = databaseName;

		connect();
	}

	/**
	 * Constructor of MysqlConnection class.
	 * 
	 * @param databaseName The database name.
	 */
	public MysqlConnection(String databaseName) {
		this.ip = "localhost";
		this.port = 3306;
		this.username = "root";
		this.password = "";
		this.databaseName = databaseName;

		connect();
	}

	/**
	 * Connect to the database and establish connection.
	 * 
	 * @return True if connection was successful and false if not.
	 */
	public boolean connect() {
		try {
			connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + databaseName;
			Class.forName("com.mysql.cj.jdbc.Driver");

			connection = DriverManager.getConnection(connectionString, username, password);
			return true;
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
	 * Get the server port number.
	 * 
	 * @return Server port number.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Set the server port number.
	 * 
	 * @param port Server port number.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Get the server IP.
	 * 
	 * @return The server IP.
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Set the server IP.
	 * 
	 * @param ip The new server IP.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Get the server username.
	 * 
	 * @return The server username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the server new username.
	 * 
	 * @param username The new username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get the server password.
	 * 
	 * @return The server password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the server new password.
	 * 
	 * @param password The new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the database name.
	 * 
	 * @return The database name.
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * Set the database name.
	 * 
	 * @param The new database name.
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
}
