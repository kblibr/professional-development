/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.application.api.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.familysearch.professional.development.application.api.FsHttpServletRequestWrapper;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public class FsHttpServletRequestWrapperFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    filterChain.doFilter(new FsHttpServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
  }

  @Override
  public void destroy() {
  }
}
