/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.application;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.EnumSet;
import javax.servlet.DispatcherType;

import ch.qos.logback.classic.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.familysearch.professional.development.application.api.FilterMapping;
import org.familysearch.professional.development.application.api.ServletMapping;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 9/23/14.
 */
public class EmbeddedServer {

  private static final Logger LOGGER = LoggerFactory.createLogger(EmbeddedServer.class);
  private Server server;
  private int port = 0;

  public void start() {
    try {
      initialize();
      server.join();
    }
    catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public int startOnRandomAvailablePort() {
    try {
      this.port = findAvailablePort();
      this.initialize();
    }
    catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    return port;
  }

  public void stop() {
    try {
      server.stop();
    }
    catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void initialize() throws Exception {
    server = new Server();
    server.setConnectors(getConnectors(server));
    server.setHandler(getHandlers());
    server.start();
  }

  private Connector[] getConnectors(org.eclipse.jetty.server.Server server) {
    HttpConfiguration httpConfiguration = new HttpConfiguration();
    httpConfiguration.setSendServerVersion(false);
    HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfiguration);
    ServerConnector httpConnector = new ServerConnector(server, httpConnectionFactory);
    httpConnector.setPort(this.getPort());
    return new Connector[]{httpConnector};
  }

  private HandlerList getHandlers() throws InstantiationException, IllegalAccessException {
    HandlerList handlers = new HandlerList();
    handlers.setHandlers(new Handler[]{this.getStaticContentHandler(), this.getAPIHandlers()});
    return handlers;
  }

  private Handler getAPIHandlers() throws IllegalAccessException, InstantiationException {
    ServletHandler handler = new ServletHandler();

    FilterMapping[] filterMappings = FilterMapping.values();
    ServletMapping[] servletMappings = ServletMapping.values();

    for (FilterMapping filterMapping : filterMappings) {
      FilterHolder filterHolder = new FilterHolder(filterMapping.getFilterClass().newInstance());
      handler.addFilterWithMapping(filterHolder, filterMapping.getPath(), EnumSet.of(DispatcherType.REQUEST));
    }
    for (ServletMapping servletMapping : servletMappings) {
      ServletHolder servletHolder = new ServletHolder(servletMapping.getServletClass().newInstance());
      handler.addServletWithMapping(servletHolder, servletMapping.getPath());
    }
    return handler;
  }

  private Handler getStaticContentHandler() {
    ResourceHandler handler = new ResourceHandler();
    handler.setDirectoriesListed(true);
    handler.setWelcomeFiles(new String[]{ApplicationProperties.getWelcomeFile()});
    handler.setResourceBase(ApplicationProperties.getStaticContentLocation());
    return handler;
  }

  private int getPort() {
    if (this.port == 0) {
      return ApplicationProperties.getApplicationPort();
    }
    else {
      return this.port;
    }
  }

  private int findAvailablePort() throws IOException {
    ServerSocket server = new ServerSocket(0);
    int port = server.getLocalPort();
    server.close();
    return port;
  }
}
