/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.application.api;

import javax.servlet.Servlet;

import org.familysearch.professional.development.application.api.servlets.HelloServlet;
import org.familysearch.professional.development.healthcheck.api.servlets.HealthCheckServlet;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public enum ServletMapping {
  HEALTH_CHECK(HealthCheckServlet.class, "/healthcheck"),
  HELLO(HelloServlet.class, "/hello"),;

  private Class<? extends Servlet> servletClass;
  private String path;

  private ServletMapping(Class<? extends Servlet> servletClass, String path) {
    this.servletClass = servletClass;
    this.path = path;
  }

  public Class<? extends Servlet> getServletClass() {
    return servletClass;
  }

  public String getPath() {
    return path;
  }
}
