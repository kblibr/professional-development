package org.familysearch.professional.development.healthcheck.model;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class HealthCheckStatusTest {

  private HealthCheckStatus healthCheckStatus;

  @BeforeMethod
  public void setup() {
    this.healthCheckStatus = new HealthCheckStatus();
  }

  @Test
  public void testSetGetApplicationStatus() {
    String applicationStatus = "applicationStatus";
    this.healthCheckStatus.setApplicationStatus(applicationStatus);
    assertEquals(this.healthCheckStatus.getApplicationStatus(), applicationStatus);
  }

  @Test
  public void testSetGetDatabaseStatus() {
    String databaseStatus = "databaseStatus";
    this.healthCheckStatus.setDatabaseStatus(databaseStatus);
    assertEquals(this.healthCheckStatus.getDatabaseStatus(), databaseStatus);
  }

  @Test
  public void testSetGetDatabaseVersion() {
    int databaseVersion = 1;
    this.healthCheckStatus.setDatabaseVersion(databaseVersion);
    assertEquals(this.healthCheckStatus.getDatabaseVersion(), databaseVersion);
  }

  @Test
  public void testSetGetStatusCode() {
    int statusCode = 1;
    this.healthCheckStatus.setStatusCode(statusCode);
    assertEquals(this.healthCheckStatus.getStatusCode(), statusCode);
  }
}