package Utils;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

/**
 * QUERY SELECT DataTable
 */

public class DataTable {

  public String[] names;
  public List<List<String>> table = new ArrayList<>();

  public DataTable(ResultSet result) {
    try {
      ResultSetMetaData metadata = result.getMetaData();
      this.names = new String[metadata.getColumnCount()];

      for (int i = 1; i < names.length + 1; i++) {
        names[i - 1] = metadata.getColumnName(i);
      }

      while (result.next()) {
        List<String> cols = new ArrayList<>();

        for (int i = 1; i <= names.length; i++) {
          cols.add(result.getString(i));
        }

        table.add(cols);
      }
    } catch (SQLException ex) {
      Logger.getLogger(DataTable.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public int getColCount() {
    return table.get(0).size();
  }

  public int getRowCount() {
    return table.size();
  }

  public String getData(int i, int j) {
    return table.get(i).get(j);
  }
}
