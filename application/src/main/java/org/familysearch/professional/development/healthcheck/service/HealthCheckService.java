/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.healthcheck.service;

import java.sql.SQLException;

import org.familysearch.professional.development.healthcheck.domain.HealthCheckDao;
import org.familysearch.professional.development.healthcheck.model.HealthCheckStatus;
import org.familysearch.professional.development.healthcheck.model.factories.HealthCheckStatusFactory;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public class HealthCheckService {

  private HealthCheckDao healthCheckDao = new HealthCheckDao();

  public HealthCheckStatus getStatus() {
    try {
      int version = healthCheckDao.getDatabaseVersion();
      return HealthCheckStatusFactory.getInstance(true, true, version);
    }
    catch (SQLException e) {
      return HealthCheckStatusFactory.getInstance(true, false);
    }
  }
}
