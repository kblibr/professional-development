/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.helpers;

import java.io.IOException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

import static org.testng.Assert.assertTrue;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 9/21/14.
 */
public class Client {

  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
  private static final JsonFactory JSON_FACTORY = new GsonFactory();

  public <T> T request(GenericUrl genericUrl, Class<T> t) throws IOException {
    HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
          @Override
          public void initialize(HttpRequest request) {
            request.setParser(new JsonObjectParser(JSON_FACTORY));
          }
        });
    HttpRequest request = requestFactory.buildGetRequest(genericUrl);
    HttpResponse httpResponse = request.execute();
    assertTrue(httpResponse.isSuccessStatusCode());
    return httpResponse.parseAs(t);
  }
}
