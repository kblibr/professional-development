/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.familysearch.professional.development.application.EmbeddedServer;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 9/23/14.
 */
public class TestBase {
  private EmbeddedServer server;
  private String host;
  private boolean runServer;

  protected String getHost() {
    return this.host;
  }

  @BeforeClass
  public void beforeClass() {
    this.runServer = Boolean.valueOf(System.getProperty("run.server", Boolean.FALSE.toString()));
    if (this.runServer) {
      this.server = new EmbeddedServer();
      int port = this.server.startOnRandomAvailablePort();
      this.host = System.getProperty("host") + ":" + port;
    }
    else {
      this.host = System.getProperty("host");
    }
  }

  @AfterClass
  public void afterClass() {
    if (this.runServer) {
      this.server.stop();
    }
  }

}
