package Utils;

import java.sql.*;
import java.util.regex.*;

/**
 *
 * @author cartory
 */
public abstract class Model {

	public static enum DataType {
		DATE, TIME, FLOAT, STRING, INTEGER,
	}

	protected String TABLE;
	protected Object ATTRIBS[][];
	protected final DBConnection connection;

	public Model() {
		this.connection = DBConnection.getInstance();
	}

	public Model(String TABLE, Object ATTRIBS[][]) {
		this.TABLE = TABLE;
		this.ATTRIBS = ATTRIBS;
		this.connection = DBConnection.getInstance();
	}

	public Object[][] getATTRIBS() {
		return this.ATTRIBS;
	}

	public DataType[] getDataTypes() {
		DataType datatypes[] = new DataType[this.ATTRIBS.length];

		for (int i = 0; i < datatypes.length; i++) {
			datatypes[i] = (DataType) this.ATTRIBS[i][1];
		}

		return datatypes;
	}

	public String[] getCOLUMNS() {
		String columns[] = new String[this.ATTRIBS.length];

		for (int i = 0; i < columns.length; i++) {
			columns[i] = (String) this.ATTRIBS[i][0];
		}

		return columns;
	}

	private static boolean isNumber(Object arg) {
		String regex = "^(\\d+(\\.\\d+)?)$";
		return Pattern.matches(regex, String.valueOf(arg));
	}

	public DataTable selectAll() {
		String sql = "SELECT * FROM " + this.TABLE;
		return new DataTable((ResultSet) this.connection.query(sql));
	}

	public DataTable selectOne(String id) {
		String sql = "SELECT * FROM " + this.TABLE + " WHERE id = " + id;
		return new DataTable((ResultSet) this.connection.query(sql));
	}

	public boolean create(Object args[]) {
		String COLS = "";
		String VALUES = "";

		for (int i = 0; i < args.length; i++) {
			if (!isNumber(args[i])) {
				args[i] = "'" + args[i] + "'";
			}

			VALUES += "%s";
			COLS += ATTRIBS[i + 1][0];
			if (i < args.length - 1) {
				COLS += ", ";
				VALUES += ", ";
			}
		}

		String sql = String.format("INSERT INTO " + this.TABLE + "(" + COLS + ") VALUES (" + VALUES + ")", args);

		return (boolean) this.connection.query(sql);
	}

	public boolean update(Object args[]) {
		String VALUES = "";

		for (int i = 0; i < args.length; i++) {
			if (!isNumber(args[i])) {
				args[i] = "'" + args[i] + "'";
			}

			VALUES += this.ATTRIBS[i][0] + "=%s";
			VALUES += (i < this.ATTRIBS.length - 1) ? ", " : " ";
		}

		String sql = String.format("UPDATE " + this.TABLE + " SET " + VALUES + "WHERE id = " + args[0], args);

		return (boolean) this.connection.query(sql);
	}

	public boolean delete(String id) {
		String sql = "DELETE FROM " + this.TABLE + " WHERE id = " + id;
		return (boolean) this.connection.query(sql);
	}
}
