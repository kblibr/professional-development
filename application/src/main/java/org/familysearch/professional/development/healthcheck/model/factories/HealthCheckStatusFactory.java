/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.healthcheck.model.factories;

import org.familysearch.professional.development.healthcheck.model.HealthCheckStatus;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 9/22/14.
 */
public class HealthCheckStatusFactory {

  private static final transient int statusCodeOk = 200;
  private static final transient int statusCodeInternalServerError = 503;


  private HealthCheckStatusFactory() {
  }

  public static HealthCheckStatus getInstance(boolean application, boolean database) {
    HealthCheckStatus healthCheckStatus = new HealthCheckStatus();
    healthCheckStatus.setApplicationStatus(getStatusValue(application));
    healthCheckStatus.setDatabaseStatus(getStatusValue(database));
    healthCheckStatus.setStatusCode(getStatusCode(application, database));
    return healthCheckStatus;
  }

  public static HealthCheckStatus getInstance(boolean application, boolean database, int databaseVersion) {
    HealthCheckStatus healthCheckStatus = getInstance(application, database);
    healthCheckStatus.setDatabaseVersion(databaseVersion);
    return healthCheckStatus;
  }

  private static int getStatusCode(boolean application, boolean database) {
    if (application && database) {
      return statusCodeOk;
    }
    else {
      return statusCodeInternalServerError;
    }
  }

  private static String getStatusValue(boolean status) {
    if (status) {
      return "OK";
    }
    else {
      return "Down";
    }
  }
}
