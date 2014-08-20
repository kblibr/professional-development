/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.application.domain.prepared.statements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.familysearch.professional.development.application.domain.connection.SQLConnection;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public final class Statements {

  public static PreparedStatement GET_VERSION;
  public static PreparedStatement UPDATE_VERSION;

  private static Connection connection = SQLConnection.getConnection();

  static {
    try {
      GET_VERSION = connection.prepareStatement("SELECT number FROM version");
      UPDATE_VERSION = connection.prepareStatement("UPDATE version SET number = (number + 1)");
    }
    catch (SQLException e) {
      e.printStackTrace();
      System.exit(3);
    }
  }
}
