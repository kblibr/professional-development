/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.application.domain.connection;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.familysearch.professional.development.application.ApplicationProperties;
import org.familysearch.professional.development.application.domain.prepared.statements.Statements;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public final class SQLConnection {

  private static Connection connection = null;

  private SQLConnection() {
  }

  public static synchronized Connection getConnection() {
    if (connection == null) {
      initializeConnection();
      updateDatabase();
    }
    return connection;
  }

  private static void initializeConnection() {
    try {
      connection = DriverManager.getConnection(getDatabaseURL());
    }
    catch (SQLException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static String getDatabaseURL() {
    String host = ApplicationProperties.getDatabaseHost();
    String port = ApplicationProperties.getDatabasePort();
    String name = ApplicationProperties.getDatabaseName();
    String username = ApplicationProperties.getDatabaseUsername();
    String password = ApplicationProperties.getDatabasePassword();
    return String.format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s", host, port, name, username, password);
  }

  private static void initializeDatabase() {
    File[] files = getFiles(ApplicationProperties.getDatabaseVersionLocation());
    for (File file : files) {
      try {
        executeSQLFile(file);
      }
      catch (SQLException e) {
        e.printStackTrace();
        System.exit(5);
      }
    }
    updateDatabase();
  }

  private static void updateDatabase() {
    ResultSet resultSet;
    try {
      resultSet = Statements.GET_VERSION.executeQuery();
      if (resultSet.next()) {
        int index = resultSet.getInt(1);
        File[] files = getFiles(ApplicationProperties.getDatabaseSchemaLocation());
        for (; index < files.length; index++) {
          try {
            executeSQLFile(files[index]);
            Statements.UPDATE_VERSION.execute();
          }
          catch (SQLException e) {
            e.printStackTrace();
            System.exit(5);
          }
        }
      }
      resultSet.close();
    }
    catch (SQLException e) {
      initializeDatabase();
    }
  }

  private static String getStatementAsString(File file) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        stringBuilder.append(scanner.nextLine());
      }
      scanner.close();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(4);
    }
    return stringBuilder.toString();
  }

  private static File[] getFiles(String directory) {
    File folder = new File(directory);
    return folder.listFiles();
  }

  private static void executeSQLFile(File file) throws SQLException {
    String statementString = getStatementAsString(file);
    Statement statement = connection.createStatement();
    statement.execute(statementString);
    statement.close();
  }
}
