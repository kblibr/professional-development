/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.application.api.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.familysearch.professional.development.application.api.FsHttpServletRequestWrapper;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public class HelloServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletOutputStream out = resp.getOutputStream();

    out.write("Hello".getBytes());
    out.flush();
    out.close();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse rest) throws IOException {
    System.out.println(((FsHttpServletRequestWrapper) req).getBody());
  }

}
