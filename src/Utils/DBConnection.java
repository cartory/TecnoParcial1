package Utils;

import java.sql.*;
import java.util.logging.*;

public class DBConnection {

	private static DBConnection instance = null;

	private static final String DB_NAME = "tecno";
	private static final String DB_USER = "postgres";
	private static final String DB_PASS = "";
	private static final String DB_PORT = "5432";
	private static final String DB_HOST = "localhost";
	private static final String DB_DIAL = "postgresql";
	private static final String DB_DRIVER = "org.postgresql.Driver";

	private String DB_URL;
	private Connection connection = null;

	private DBConnection() {
		this.DB_URL = "jdbc:" + DB_DIAL + "://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}

		return instance;
	}

	public Connection getConnection() {
		try {
			Class.forName(DB_DRIVER);
			connection = (Connection) DriverManager.getConnection(this.DB_URL, DB_USER, DB_PASS);
		} catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
			System.err.println("ERROR ON CONNECTING!");
		}

		return connection;
	}

	public void connect() {
		try {
			Class.forName(DB_DRIVER);
			this.connection = (Connection) DriverManager.getConnection(this.DB_URL, DB_USER, DB_PASS);
			System.out.println("\033[32;1;2mCONNECTION OPENED!\033[0m");
		} catch (ClassNotFoundException | SQLException ex) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
			System.err.println("ERROR ON CONNECTING!");
		}
	}

	public void close() {
		try {
			this.connection.close();
			System.out.println("\033[32;1;2mCONNECTION CLOSED!!\033[0m");
		} catch (SQLException ex) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
			System.err.println("ERROR ON CLOSING!");
		}
	}

	public Object query(String query) {
		Statement statement;
		Object resultSet = null;
		try {
			this.connect();
			statement = (Statement) this.connection.createStatement();
			if (query.contains("SELECT")) {
				resultSet = statement.executeQuery(query.toLowerCase());
			} else {
				resultSet = (statement.executeUpdate(query) >= 0);
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			this.close();
		}
		return resultSet;
	}
}
