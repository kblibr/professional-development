/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.healthcheck.api.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.MimeTypes;

import org.familysearch.professional.development.healthcheck.model.HealthCheckStatus;
import org.familysearch.professional.development.healthcheck.service.HealthCheckService;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public class HealthCheckServlet extends HttpServlet {

  private HealthCheckService healthCheckService = new HealthCheckService();
  private Gson gson = new Gson();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HealthCheckStatus healthCheckStatus = this.healthCheckService.getStatus();
    resp.setStatus(healthCheckStatus.getStatusCode());
    resp.setHeader(HttpHeader.CONTENT_TYPE.toString(), MimeTypes.Type.APPLICATION_JSON.toString());
    ServletOutputStream out = resp.getOutputStream();
    out.println(gson.toJson(healthCheckStatus));
  }
}
