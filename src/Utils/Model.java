package Utils;

import java.sql.*;
import java.util.regex.*;
import java.util.HashMap;

/**
 *
 * @author cartory
 */
public abstract class Model {

  public static enum DataType {
    DATE, TIME, FLOAT, STRING, INTEGER,
  }

  protected String TABLE;
  protected String[] COLUMNS;

  protected DataType[] dataTypes;
  protected final DBConnection connection;

  public Model() {
    this.connection = new DBConnection();
  }

  public Model(String TABLE, HashMap<String, DataType> ATTRIBS) {
    this.COLUMNS = ATTRIBS.keySet().toArray(new String[0]);
    this.dataTypes = ATTRIBS.values().toArray(new DataType[0]);

    this.connection = new DBConnection();
  }

  public Model(String TABLE, String[] COLUMNS) {
    this.TABLE = TABLE;
    this.COLUMNS = COLUMNS;
    this.connection = new DBConnection();
  }

  public DataType[] getDataTypes() {
    return this.dataTypes;
  }

  public String[] getCOLUMNS() {
    return this.COLUMNS;
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
    String sql = "SELECT * FROM " + this.TABLE + "WHERE id = " + id;
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
      COLS += COLUMNS[i];
      if (i < args.length) {
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
      VALUES += this.COLUMNS[i];
      VALUES += (i == this.COLUMNS.length - 1) ? "= %s " : "= %s, ";
    }

    String sql = String.format("UPDATE " + this.TABLE + " SET " + VALUES + "WHERE id = %s", args);

    return (boolean) this.connection.query(sql);
  }

  public boolean delete(String id) {
    String sql = "DELETE FROM " + this.TABLE + " WHERE id = " + id;
    return (boolean) this.connection.query(sql);
  }
}
