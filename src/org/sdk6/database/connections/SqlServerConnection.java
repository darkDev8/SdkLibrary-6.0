package org.sdk6.database.connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlServerConnection {

	private Connection connection;
	private String connectionString;

	private int port;
	private int loginTime;

	private String host;
	private String instance;
	private String username;
	private String password;
	private String databaseName;

	/**
	 * Constructor of SqlServerConnection class.
	 * 
	 * @param host         The database host(IP).
	 * @param instance     The sql server instance.
	 * @param port         The database port number.
	 * @param username     The username for connection.
	 * @param password     The password for connection.
	 * @param databaseName The database name.
	 */
	public SqlServerConnection(String host, String instance, String databaseName, String username, String password,
			int port) {
		this.host = host;
		this.instance = instance;
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		this.port = port;

		this.loginTime = 90;
		connect();
	}

	/**
	 * Constructor of SqlServerConnection class.
	 * 
	 * @param host         The database host(IP).
	 * @param instance     The sql server instance.
	 * @param port         The database port number.
	 * @param username     The username for connection.
	 * @param password     The password for connection.
	 * @param databaseName The database name.
	 * @param loginTime    The time to wait until connection established.
	 */
	public SqlServerConnection(String host, String instance, String databaseName, String username, String password,
			int port, int loginTime) {
		this.host = host;
		this.instance = instance;
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		this.port = port;
		this.loginTime = loginTime;

		connect();
	}

	/**
	 * Connect to the database and establish connection.
	 * 
	 * @return True if connection was successful and false if not.
	 */
	public boolean connect() {
		try {
			connectionString = "jdbc:sqlserver://" + host + "\\" + instance + ":" + port + ";database=" + databaseName
					+ ";loginTimeout=" + loginTime + ";";

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

			this.connection = DriverManager.getConnection(connectionString, username, password);

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
	public String getHost() {
		return host;
	}

	/**
	 * Set the server IP.
	 * 
	 * @param ip The new server IP.
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Get the database instance name.
	 * 
	 * @return The database instance name.
	 */
	public String getInstance() {
		return instance;
	}

	/**
	 * Set the database instance name.
	 * 
	 * @param instance The database new instance name.
	 */
	public void setInstance(String instance) {
		this.instance = instance;
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

	/**
	 * Get the database login time.
	 * @return The database login time.
	 */
	public int getLoginTime() {
		return loginTime;
	}

	/**
	 * Set the database login time.
	 * @param loginTime The new database login time.
	 */
	public void setLoginTime(int loginTime) {
		this.loginTime = loginTime;
	}
}
