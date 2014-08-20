/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.healthcheck.model;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public class HealthCheckStatus {
  private String applicationStatus;
  private String databaseStatus;
  private int databaseVersion;
  private transient int statusCode;

  public HealthCheckStatus() {
  }

  public void setApplicationStatus(String applicationStatus) {
    this.applicationStatus = applicationStatus;
  }

  public String getApplicationStatus() {
    return applicationStatus;
  }

  public void setDatabaseStatus(String databaseStatus) {
    this.databaseStatus = databaseStatus;
  }

  public String getDatabaseStatus() {
    return databaseStatus;
  }

  public void setDatabaseVersion(int databaseVersion) {
    this.databaseVersion = databaseVersion;
  }

  public int getDatabaseVersion() {
    return databaseVersion;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return this.statusCode;
  }
}
