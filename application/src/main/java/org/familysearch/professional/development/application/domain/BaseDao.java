/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.application.domain;

import java.sql.Connection;

import ch.qos.logback.classic.Logger;

import org.familysearch.professional.development.application.LoggerFactory;
import org.familysearch.professional.development.application.domain.connection.SQLConnection;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public abstract class BaseDao {

  private static Logger LOGGER = LoggerFactory.createLogger(BaseDao.class);

  protected Connection connection;

  static {
    try {
      Class.forName("org.postgresql.Driver");
    }
    catch (ClassNotFoundException e) {
      LOGGER.error("loading database driver failed", e);
      System.exit(8);
    }
  }

  public BaseDao() {
    LOGGER.debug("msg=\"getting connection\"");
    this.connection = SQLConnection.getConnection();
  }

}
