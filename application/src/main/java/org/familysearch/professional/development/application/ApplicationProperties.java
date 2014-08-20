/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 9/19/14.
 */
public class ApplicationProperties {

  private static final String EMPTY_STRING = "";
  private static final String PORT = "PORT";
  private static final String DATABASE_HOST = "database.host";
  private static final String DATABASE_PORT = "database.port";
  private static final String DATABASE_NAME = "database.name";
  private static final String DATABASE_USERNAME = "database.username";
  private static final String DATABASE_PASSWORD = "database.password";
  private static final String STATIC_CONTENT = "static.content";
  private static final String WELCOME_FILE = "welcome.file";
  private static final String LOGGING_DEFAULT_PATTERN = "logging.pattern.default";
  private static final String LOGGING_USE_CONSOLE_APPENDER = "logging.use.console.appender";
  private static final String LOGGING_USE_FILE_APPENDER = "logging.use.file.appender";
  private static final String LOGGING_USE_ROLLING_FILE_APPENDER = "logging.use.rolling.file.appender";
  private static final String LOGGING_FILE = "logging.file";
  private static final String DATABASE_SCHEMA_LOCATION = "database.schema.location";
  private static final String DATABASE_VERSION_LOCATION = "database.version.location";
  private static final String LOGGING_LEVEL = "logging.level";
  private static final String LOGGING_FILES_TO_KEEP = "logging.files.to.keep";
  private static final String LOGGING_FILE_SIZE = "logging.file.size";

  public static String getProperty(String key) {
    return getProperty(key, EMPTY_STRING);
  }

  public static String getProperty(String key, String defaultValue) {
    String property = getProperties().getProperty(key, defaultValue);
    property = System.getProperty(key, property);
    String env = System.getenv(key);
    if (env != null && !env.equals(EMPTY_STRING)) {
      property = env;
    }
    return property;
  }

  public static int getPropertyAsInt(String key) {
    return Integer.valueOf(getProperty(key));
  }

  public static boolean getPropertyAsBool(String key) {
    return Boolean.valueOf(getProperty(key));
  }

  /* Application Properties */
  public static int getApplicationPort() {
    return getPropertyAsInt(PORT);
  }

  public static String getStaticContentLocation() {
    return getProperty(STATIC_CONTENT);
  }

  public static String getWelcomeFile() {
    return getProperty(WELCOME_FILE);
  }

  /* Logging Properties */
  public static String getLoggingDefaultPattern() {
    return getProperty(LOGGING_DEFAULT_PATTERN);
  }

  public static boolean useLoggingConsoleAppender() {
    return getPropertyAsBool(LOGGING_USE_CONSOLE_APPENDER);
  }

  public static boolean useLoggingFileAppender() {
    return getPropertyAsBool(LOGGING_USE_FILE_APPENDER);
  }

  public static boolean useLoggingRollingFileAppender() {
    return getPropertyAsBool(LOGGING_USE_ROLLING_FILE_APPENDER);
  }

  public static String getLoggingFile() {
    return getProperty(LOGGING_FILE);
  }

  public static String getLoggingLevel() {
    return getProperty(LOGGING_LEVEL);
  }

  public static int getLogFilesToKeep() {
    return getPropertyAsInt(LOGGING_FILES_TO_KEEP);
  }

  public static String getLoggingFileSize() {
    return getProperty(LOGGING_FILE_SIZE);
  }

  /* Database Properties */
  public static String getDatabaseHost() {
    return getProperty(DATABASE_HOST);
  }

  public static String getDatabasePort() {
    return getProperty(DATABASE_PORT);
  }

  public static String getDatabaseName() {
    return getProperty(DATABASE_NAME);
  }

  public static String getDatabaseUsername() {
    return getProperty(DATABASE_USERNAME);
  }

  public static String getDatabasePassword() {
    return getProperty(DATABASE_PASSWORD);
  }

  public static String getDatabaseSchemaLocation() {
    return getProperty(DATABASE_SCHEMA_LOCATION);
  }

  public static String getDatabaseVersionLocation() {
    return getProperty(DATABASE_VERSION_LOCATION);
  }

  private static Properties getProperties() {
    Properties properties = new Properties();
    String result = System.getProperty("properties.file", "application/src/main/resources/properties.xml");
    try {
      properties.loadFromXML(new FileInputStream(result));
    }
    catch (IOException e) {
      e.printStackTrace();
      System.exit(6);
    }
    return properties;
  }
}
