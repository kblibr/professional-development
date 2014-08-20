/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.healthcheck.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.familysearch.professional.development.application.domain.BaseDao;
import org.familysearch.professional.development.application.domain.prepared.statements.Statements;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public class HealthCheckDao extends BaseDao {

  public int getDatabaseVersion() throws SQLException {
    ResultSet resultSet = Statements.GET_VERSION.executeQuery();
    if (resultSet.next()) {
      int result = resultSet.getInt("number");
      resultSet.close();
      return result;
    }
    else {
      throw new IllegalStateException("Database schema is not deployed");
    }
  }
}
