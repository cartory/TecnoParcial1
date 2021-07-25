/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Utils.Model;

/**
 *
 * @author cartory
 */
public class Service extends Model {

  public Service() {
    super("servicio", new Object[][] {

        { "id", DataType.INTEGER },

        { "nombre", DataType.STRING },

        { "precio", DataType.FLOAT },

        { "tipo_servicio", DataType.STRING },

    });
  }
}
