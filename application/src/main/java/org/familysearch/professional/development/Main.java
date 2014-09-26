/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development;

import ch.qos.logback.classic.Logger;

import org.familysearch.professional.development.application.EmbeddedServer;
import org.familysearch.professional.development.application.LoggerFactory;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public class Main {

  private final static Logger LOGGER = LoggerFactory.createLogger(Main.class);

  public static void main(String[] args) throws Exception {
    LOGGER.info("msg=\"creating embedded server\"");
    EmbeddedServer embeddedServer = new EmbeddedServer();
    embeddedServer.start();
  }
}
