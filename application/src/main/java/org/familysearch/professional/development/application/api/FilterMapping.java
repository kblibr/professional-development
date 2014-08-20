/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.application.api;

import javax.servlet.Filter;

import org.familysearch.professional.development.application.api.filters.FsHttpServletRequestWrapperFilter;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 8/18/14.
 */
public enum FilterMapping {
  FS_HTTP_SERVLET_REQUEST_WRAPPER_FILTER(FsHttpServletRequestWrapperFilter.class, "/*");

  private Class<? extends Filter> filterClass;
  private String path;

  private FilterMapping(Class<? extends Filter> filterClass, String path) {
    this.filterClass = filterClass;
    this.path = path;
  }

  public Class<? extends Filter> getFilterClass() {
    return filterClass;
  }

  public String getPath() {
    return path;
  }
}
