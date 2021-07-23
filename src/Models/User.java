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
public class User extends Model {

  public User() {
    super("usuario", new Object[][] {

        { "id", DataType.STRING },

        { "nombre", DataType.STRING },

        { "email", DataType.STRING },

        { "password", DataType.STRING },

        { "telefono", DataType.STRING },

        { "tipo_usuario", DataType.STRING },

    });
  }
}