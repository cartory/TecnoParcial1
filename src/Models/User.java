/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Utils.Model;
import java.util.HashMap;

/**
 *
 * @author cartory
 */
public class User extends Model {

  public User() {
    super("user", new HashMap<String, DataType>() {
      {
        put("id", DataType.INTEGER);
        put("email", DataType.STRING);
        put("phone", DataType.INTEGER);
        put("status", DataType.STRING);
        put("password", DataType.STRING);
        put("userType", DataType.STRING);
      }
    });
  }
}
