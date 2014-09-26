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

import ch.qos.logback.classic.Logger;

import org.familysearch.professional.development.application.ApplicationProperties;
import org.familysearch.professional.development.application.LoggerFactory;
import org.familysearch.professional.development.application.domain.prepared.statements.Statements;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public final class SQLConnection {
  private static Logger LOGGER = LoggerFactory.createLogger(SQLConnection.class);

  private static Connection connection = null;

  private SQLConnection() {
  }

  public static synchronized Connection getConnection() {
    if (connection == null) {
      LOGGER.debug("msg=\"initializing connection\"");
      initializeConnection();
      LOGGER.debug("msg=\"updating database\"");
      updateDatabase();
    }
    return connection;
  }

  private static void initializeConnection() {
    try {
      LOGGER.debug("msg=\"getting connection\"");
      connection = DriverManager.getConnection(getDatabaseURL());
    }
    catch (SQLException e) {
      LOGGER.error("Failed to get connection", e);
      System.exit(1);
    }
  }

  private static String getDatabaseURL() {
    String host = ApplicationProperties.getDatabaseHost();
    LOGGER.debug("databaseHost=" + host);
    String port = ApplicationProperties.getDatabasePort();
    LOGGER.debug("databasePort=" + port);
    String name = ApplicationProperties.getDatabaseName();
    LOGGER.debug("databaseName=" + name);
    String username = ApplicationProperties.getDatabaseUsername();
    LOGGER.debug("databaseUsername=" + username);
    String password = ApplicationProperties.getDatabasePassword();
    LOGGER.debug("databasePassword=" + password);
    return String.format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s", host, port, name, username, password);
  }

  private static void initializeDatabase() {
    LOGGER.debug("msg=\"getting files to initialize database");
    File[] files = getFiles(ApplicationProperties.getDatabaseVersionLocation());
    for (File file : files) {
      LOGGER.debug("fileName=" + file.getName());
      try {
        LOGGER.debug("msg=\"executing file\"");
        executeSQLFile(file);
      }
      catch (SQLException e) {
        LOGGER.error("Failed to execute sql file:" + file.getName(), e);
        System.exit(5);
      }
    }
    LOGGER.debug("msg=\"updating database\"");
    updateDatabase();
  }

  private static void updateDatabase() {
    ResultSet resultSet;
    try {
      LOGGER.debug("msg=\"getting database version\"");
      resultSet = Statements.GET_VERSION.executeQuery();
      if (resultSet.next()) {
        int index = resultSet.getInt(1);
        LOGGER.debug("databaseVersion=" + index);
        LOGGER.debug("msg=\"getting files to update database");
        File[] files = getFiles(ApplicationProperties.getDatabaseSchemaLocation());
        for (; index < files.length; index++) {
          LOGGER.debug("fileName=" + files[index].getName());
          try {
            LOGGER.debug("msg=\"executing file\"");
            executeSQLFile(files[index]);
            LOGGER.debug("msg=\"updating version\"");
            Statements.UPDATE_VERSION.execute();
          }
          catch (SQLException e) {
            LOGGER.error("Failed to execute sql file:" + files[index].getName(), e);
            System.exit(5);
          }
        }
      }
      resultSet.close();
    }
    catch (SQLException e) {
      LOGGER.debug("msg=\"need to initialize the database\"", e);
      initializeDatabase();
    }
  }

  private static String getStatementAsString(File file) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      LOGGER.debug("msg=\"creating scanner\"");
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        LOGGER.debug("msg=\"reading line\"");
        String line = scanner.nextLine();
        LOGGER.debug("line=\"" + line + "\"");
        stringBuilder.append(line);
      }
      scanner.close();
    }
    catch (FileNotFoundException e) {
      LOGGER.error("Failed to create scanner", e);
      System.exit(4);
    }
    return stringBuilder.toString();
  }

  private static File[] getFiles(String directory) {
    LOGGER.debug("msg=\"getting files in " + directory + "\"");
    File folder = new File(directory);
    return folder.listFiles();
  }

  private static void executeSQLFile(File file) throws SQLException {
    LOGGER.debug("msg=\"getting sql as string\"");
    String statementString = getStatementAsString(file);
    LOGGER.debug("msg=\"creating statement\"");
    Statement statement = connection.createStatement();
    LOGGER.debug("msg=\"executing statement\"");
    statement.execute(statementString);
    LOGGER.debug("msg=\"closing statement\"");
    statement.close();
  }
}
