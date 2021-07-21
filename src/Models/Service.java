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
public class Service extends Model {

  public Service() {
    super("service", new HashMap<String, DataType>() {
      {
        put("id", DataType.INTEGER);
        put("price", DataType.FLOAT);
        put("name", DataType.INTEGER);
        put("status", DataType.INTEGER);
        put("serviceType", DataType.STRING);
      }
    });
  }
}
